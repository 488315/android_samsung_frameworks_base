package com.android.server.display;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Handler;
import android.util.IndentingPrintWriter;

import com.android.server.desktopmode.DesktopModeService$$ExternalSyntheticOutline0;

import java.io.PrintWriter;

public final class ScreenOffBrightnessSensorController implements SensorEventListener {
    public final BrightnessMappingStrategy mBrightnessMapper;
    public final DisplayPowerController$$ExternalSyntheticLambda11 mClock;
    public final Handler mHandler;
    public final Sensor mLightSensor;
    public boolean mRegistered;
    public final SensorManager mSensorManager;
    public final int[] mSensorValueToLux;
    public int mLastSensorValue = -1;
    public long mSensorDisableTime = -1;

    public ScreenOffBrightnessSensorController(
            SensorManager sensorManager,
            Sensor sensor,
            Handler handler,
            DisplayPowerController$$ExternalSyntheticLambda11
                    displayPowerController$$ExternalSyntheticLambda11,
            int[] iArr,
            BrightnessMappingStrategy brightnessMappingStrategy) {
        this.mSensorManager = sensorManager;
        this.mLightSensor = sensor;
        this.mHandler = handler;
        this.mClock = displayPowerController$$ExternalSyntheticLambda11;
        this.mSensorValueToLux = iArr;
        this.mBrightnessMapper = brightnessMappingStrategy;
    }

    public final void dump(PrintWriter printWriter) {
        printWriter.println("Screen Off Brightness Sensor Controller:");
        IndentingPrintWriter indentingPrintWriter = new IndentingPrintWriter(printWriter);
        indentingPrintWriter.increaseIndent();
        StringBuilder m =
                DesktopModeService$$ExternalSyntheticOutline0.m(
                        new StringBuilder("registered="),
                        this.mRegistered,
                        indentingPrintWriter,
                        "lastSensorValue=");
        m.append(this.mLastSensorValue);
        indentingPrintWriter.println(m.toString());
    }

    public final float getAutomaticScreenBrightness() {
        int i;
        int i2 = this.mLastSensorValue;
        if (i2 < 0
                || i2 >= this.mSensorValueToLux.length
                || ((!this.mRegistered
                                && this.mClock.uptimeMillis() - this.mSensorDisableTime > 1500)
                        || (i = this.mSensorValueToLux[this.mLastSensorValue]) < 0)) {
            return Float.NaN;
        }
        return this.mBrightnessMapper.getBrightness(null, i, -1);
    }

    @Override // android.hardware.SensorEventListener
    public final void onAccuracyChanged(Sensor sensor, int i) {}

    @Override // android.hardware.SensorEventListener
    public final void onSensorChanged(SensorEvent sensorEvent) {
        if (this.mRegistered) {
            this.mLastSensorValue = (int) sensorEvent.values[0];
        }
    }

    public final void setLightSensorEnabled(boolean z) {
        if (z && !this.mRegistered) {
            this.mRegistered =
                    this.mSensorManager.registerListener(this, this.mLightSensor, 3, this.mHandler);
            this.mLastSensorValue = -1;
        } else {
            if (z || !this.mRegistered) {
                return;
            }
            this.mSensorManager.unregisterListener(this);
            this.mRegistered = false;
            this.mSensorDisableTime = this.mClock.uptimeMillis();
        }
    }
}
