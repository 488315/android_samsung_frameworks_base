package com.android.server.biometrics.sensors.face;

import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.util.Slog;

/* loaded from: classes.dex */
public abstract class SemProximitySensorObserver implements SensorEventListener {
  public boolean mIsRegisterListener = false;
  public Sensor mPrxSensor;
  public HandlerThread mSensorThread;
  public SensorManager mSmgr;
  public Handler mThreadHandler;

  @Override // android.hardware.SensorEventListener
  public void onAccuracyChanged(Sensor sensor, int i) {}

  public SemProximitySensorObserver(Context context) {
    SensorManager sensorManager = (SensorManager) context.getSystemService("sensor");
    this.mSmgr = sensorManager;
    if (sensorManager != null) {
      this.mPrxSensor = sensorManager.getDefaultSensor(8);
    }
  }

  public boolean registerListener() {
    boolean z = false;
    if (!this.mIsRegisterListener) {
      try {
        HandlerThread handlerThread = new HandlerThread("sensor thread", 10);
        this.mSensorThread = handlerThread;
        handlerThread.start();
        Handler threadHandler = this.mSensorThread.getThreadHandler();
        this.mThreadHandler = threadHandler;
        z = this.mSmgr.registerListener(this, this.mPrxSensor, 3, threadHandler);
      } catch (Exception e) {
        Slog.e("FaceService", "registerListener : failed to register sensor listener", e);
        this.mSensorThread.quitSafely();
      }
      this.mIsRegisterListener = true;
    }
    return z;
  }

  public synchronized void unregisterListener() {
    if (this.mIsRegisterListener) {
      try {
        this.mSmgr.unregisterListener(this);
      } catch (Exception e) {
        Slog.w("FaceService", "unregisterListener : failed to unregister sensor listener", e);
      }
      HandlerThread handlerThread = this.mSensorThread;
      if (handlerThread != null) {
        handlerThread.quitSafely();
        this.mSensorThread = null;
      }
      this.mIsRegisterListener = false;
    }
  }
}
