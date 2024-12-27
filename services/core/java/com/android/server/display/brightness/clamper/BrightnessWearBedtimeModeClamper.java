package com.android.server.display.brightness.clamper;

import android.content.ContentResolver;
import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.provider.Settings;
import com.android.server.display.brightness.clamper.BrightnessClamper;
import com.android.server.display.brightness.clamper.BrightnessClamperController;
import com.android.server.display.brightness.clamper.BrightnessWearBedtimeModeClamper;

public final class BrightnessWearBedtimeModeClamper extends BrightnessClamper {
    public final Context mContext;
    public final AnonymousClass1 mSettingsObserver;

    class Injector {
    }

    public interface WearBedtimeModeData {
    }

    public BrightnessWearBedtimeModeClamper(Injector injector, Handler handler, Context context, BrightnessClamperController.ClamperChangeListener clamperChangeListener, WearBedtimeModeData wearBedtimeModeData) {
        super(handler, clamperChangeListener);
        this.mContext = context;
        this.mBrightnessCap = ((BrightnessClamperController.DisplayDeviceData) wearBedtimeModeData).mDisplayDeviceConfig.mBrightnessCapForWearBedtimeMode;
        ?? r4 = new ContentObserver(handler) { // from class: com.android.server.display.brightness.clamper.BrightnessWearBedtimeModeClamper.1
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                int i = Settings.Global.getInt(BrightnessWearBedtimeModeClamper.this.mContext.getContentResolver(), "bedtime_mode", 0);
                BrightnessWearBedtimeModeClamper brightnessWearBedtimeModeClamper = BrightnessWearBedtimeModeClamper.this;
                brightnessWearBedtimeModeClamper.mIsActive = i == 1;
                brightnessWearBedtimeModeClamper.mChangeListener.onChanged();
            }
        };
        this.mSettingsObserver = r4;
        ContentResolver contentResolver = context.getContentResolver();
        injector.getClass();
        contentResolver.registerContentObserver(Settings.Global.getUriFor("bedtime_mode"), false, r4, -1);
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessClamper
    public final BrightnessClamper.Type getType() {
        return BrightnessClamper.Type.WEAR_BEDTIME_MODE;
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessClamper
    public final void onDeviceConfigChanged() {
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessClamper
    public final void onDisplayChanged(Object obj) {
        final WearBedtimeModeData wearBedtimeModeData = (WearBedtimeModeData) obj;
        this.mHandler.post(new Runnable() { // from class: com.android.server.display.brightness.clamper.BrightnessWearBedtimeModeClamper$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                BrightnessWearBedtimeModeClamper brightnessWearBedtimeModeClamper = BrightnessWearBedtimeModeClamper.this;
                BrightnessWearBedtimeModeClamper.WearBedtimeModeData wearBedtimeModeData2 = wearBedtimeModeData;
                brightnessWearBedtimeModeClamper.getClass();
                brightnessWearBedtimeModeClamper.mBrightnessCap = ((BrightnessClamperController.DisplayDeviceData) wearBedtimeModeData2).mDisplayDeviceConfig.mBrightnessCapForWearBedtimeMode;
                brightnessWearBedtimeModeClamper.mChangeListener.onChanged();
            }
        });
    }

    @Override // com.android.server.display.brightness.clamper.BrightnessClamper
    public final void stop() {
        this.mContext.getContentResolver().unregisterContentObserver(this.mSettingsObserver);
    }
}
