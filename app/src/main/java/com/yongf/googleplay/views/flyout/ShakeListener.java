/*
 * Copyright (C) 1996-2016 YONGF Inc.All Rights Reserved.
 * Scott Wang blog.54yongf.com | blog.csdn.net/yongf2014
 * 文件名: ShakeListener.java
 * 描述:
 * 修改历史:
 * 版本号    作者                日期              简要介绍相关操作
 *  1.0         Scott Wang     2016/4/13       Create
 */

package com.yongf.googleplay.views.flyout;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;

/**
 * 摇一摇监听器
 *
 * @author Scott Wang
 * @version 1.0, 2016/4/13
 * @see
 * @since GooglePlay1.0
 */
public class ShakeListener implements SensorEventListener {
    private static final int FORCE_THRESHOLD = 250;
    private static final int TIME_THRESHOLD = 100;
    private static final int SHAKE_TIMEOUT = 500;
    private static final int SHAKE_DURATION = 1000;
    private static final int SHAKE_COUNT = 2;

    private SensorManager mSensorMgr;
    private float mLastX = -1.0f, mLastY = -1.0f, mLastZ = -1.0f;
    private long mLastTime;
    private OnShakeListener mShakeListener;
    private Context mContext;
    private int mShakeCount = 0;
    private long mLastShake;
    private long mLastForce;

    public ShakeListener(Context context) {
        mContext = context;
        resume();
    }

    public void setOnShakeListener(OnShakeListener listener) {
        mShakeListener = listener;
    }

    /**
     * 界面可见时候才监听摇晃
     */
    public void resume() {
        mSensorMgr = (SensorManager) mContext.getSystemService(Context.SENSOR_SERVICE);
        if (mSensorMgr == null) {
            throw new UnsupportedOperationException("Sensors not supported");
        }

        boolean supported = mSensorMgr.registerListener(this, mSensorMgr.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_UI);
        if (!supported) {
            mSensorMgr.unregisterListener(this);
            return;
        }
    }

    /**
     * 界面不可见时，需要关闭监听
     */
    public void pause() {
        if (mSensorMgr != null) {
            mSensorMgr.unregisterListener(this);
            mSensorMgr = null;
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {
        System.out.println("accuracy:" + accuracy);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {

        System.out.println("x:" + event.values[SensorManager.DATA_X] + "  y:" + event.values[SensorManager.DATA_Y]
                + "  z:" + event.values[SensorManager.DATA_Z]);

        if (event.sensor.getType() != Sensor.TYPE_ACCELEROMETER) {
            return;
        }

        long now = System.currentTimeMillis();

        if ((now - mLastForce) > SHAKE_TIMEOUT) {
            mShakeCount = 0;
        }

        if ((now - mLastTime) > TIME_THRESHOLD) {
            long diff = now - mLastTime;
            // 把X,Y,Z方向的距离除以时间，得出速度
            float speed = Math.abs(event.values[SensorManager.DATA_X] + event.values[SensorManager.DATA_Y]
                    + event.values[SensorManager.DATA_Z] - mLastX - mLastY - mLastZ)
                    / diff * 10000;
            if (speed > FORCE_THRESHOLD) {// 如果速度大于某个值
                // 先把摇晃的次数+1，再判断是否超过了要换的次数，并且间隙大于特定的值
                if ((++mShakeCount >= SHAKE_COUNT) && (now - mLastShake > SHAKE_DURATION)) {
                    mLastShake = now;
                    mShakeCount = 0;
                    if (mShakeListener != null) {// 回调我们的listener
                        mShakeListener.onShake();
                    }
                }
                mLastForce = now;
            }
            mLastTime = now;
            mLastX = event.values[SensorManager.DATA_X];
            mLastY = event.values[SensorManager.DATA_Y];
            mLastZ = event.values[SensorManager.DATA_Z];
        }
    }

    public interface OnShakeListener {
        public void onShake();
    }
}
