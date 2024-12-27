package com.android.server.display;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Handler;
import android.provider.Settings;
import android.util.MathUtils;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.display.DisplayPowerController;
import com.android.server.power.Slog;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AdaptiveBrightnessAdvancedSettings {
    public final Context mContext;
    public int mDarkeningRateRatio = 1;
    public final Handler mHandler;
    public final Object mLock;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class PackageRemovedReceiver extends BroadcastReceiver {
        public PackageRemovedReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            if ("com.samsung.android.displayassistant".equals(intent.getData().getSchemeSpecificPart())) {
                Settings.Global.putInt(AdaptiveBrightnessAdvancedSettings.this.mContext.getContentResolver(), "darkening_rate_ratio", 1);
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SettingsObserver extends ContentObserver {
        public SettingsObserver(DisplayPowerController.DisplayControllerHandler displayControllerHandler) {
            super(displayControllerHandler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            Slog.d("AdaptiveBrightnessAdvancedSettings", "[api] SettingsObserver: onChange: " + uri);
            synchronized (AdaptiveBrightnessAdvancedSettings.this.mLock) {
                AdaptiveBrightnessAdvancedSettings.this.handleSettingsChangedLocked();
            }
        }
    }

    public AdaptiveBrightnessAdvancedSettings(Context context, Object obj, DisplayPowerController.DisplayControllerHandler displayControllerHandler) {
        this.mContext = context;
        this.mLock = obj;
        this.mHandler = displayControllerHandler;
    }

    public final void handleSettingsChangedLocked() {
        this.mDarkeningRateRatio = MathUtils.constrain(Settings.Global.getInt(this.mContext.getContentResolver(), "darkening_rate_ratio", 1), 1, 4);
        BatteryService$$ExternalSyntheticOutline0.m(new StringBuilder("handleSettingsChangedLocked: mDarkeningRateRatio="), this.mDarkeningRateRatio, "AdaptiveBrightnessAdvancedSettings");
    }
}
