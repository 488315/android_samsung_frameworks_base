package com.samsung.android.displayquality;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.provider.Settings;
import android.util.Slog;

public abstract class SemDisplayQualityAP {
    protected static final int SCREEN_MODE_ADAPTIVE = 4;
    protected static final int SCREEN_MODE_AMOLED_CINEMA = 0;
    protected static final int SCREEN_MODE_AMOLED_PHOTO = 1;
    protected static final int SCREEN_MODE_BASIC = 2;
    protected static final int SCREEN_MODE_NATURAL = 3;
    protected static final int SCREEN_MODE_READING = 5;
    private static final String TAG = "SemDisplayQualityAP";
    protected boolean DEBUG;
    protected final Object mBrightnessModeLock;
    protected ContentResolver mContentResolver;
    protected final Context mContext;
    protected boolean mIsBrightnessModeAuto;
    protected SettingsObserver mSettingsObserver;
    protected boolean mUseScreenStatusAsyncHandle;
    protected static final String SCREEN_BRIGHTNESS_MODE = "screen_brightness_mode";
    protected static final Uri SCREEN_BRIGHTNESS_MODE_URI =
            Settings.System.getUriFor(SCREEN_BRIGHTNESS_MODE);
    protected static final String SCREEN_MODE_SETTING = "screen_mode_setting";
    protected static final Uri SCREEN_MODE_SETTING_URI =
            Settings.System.getUriFor(SCREEN_MODE_SETTING);

    final class ScreenStatusReceiver extends BroadcastReceiver {
        private ScreenStatusReceiver() {}

        public /* synthetic */ ScreenStatusReceiver(
                SemDisplayQualityAP semDisplayQualityAP, int i) {
            this();
        }

        public /* synthetic */ void lambda$onReceive$0(
                String str, BroadcastReceiver.PendingResult pendingResult) {
            if ("android.intent.action.SCREEN_ON".equals(str)) {
                SemDisplayQualityAP.this.handleScreenOnAsync();
            } else if ("android.intent.action.SCREEN_OFF".equals(str)) {
                SemDisplayQualityAP.this.handleScreenOffAsync();
            }
            pendingResult.finish();
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            if ("android.intent.action.SCREEN_ON".equals(action)) {
                SemDisplayQualityAP.this.handleScreenOn();
            } else if ("android.intent.action.SCREEN_OFF".equals(action)) {
                SemDisplayQualityAP.this.handleScreenOff();
            }
            if (SemDisplayQualityAP.this.mUseScreenStatusAsyncHandle) {
                final BroadcastReceiver.PendingResult goAsync = goAsync();
                new Thread(
                                new Runnable() { // from class:
                                                 // com.samsung.android.displayquality.SemDisplayQualityAP$ScreenStatusReceiver$$ExternalSyntheticLambda0
                                    @Override // java.lang.Runnable
                                    public final void run() {
                                        SemDisplayQualityAP.ScreenStatusReceiver.this
                                                .lambda$onReceive$0(action, goAsync);
                                    }
                                })
                        .start();
            }
        }
    }

    final class SettingsObserver extends ContentObserver {
        public SettingsObserver(Handler handler) {
            super(handler);
        }

        public void observeByName(String str) {
            SemDisplayQualityAP.this.mContentResolver.registerContentObserver(
                    Settings.System.getUriFor(str), false, this, -1);
        }

        public void observeByUri(Uri uri) {
            SemDisplayQualityAP.this.mContentResolver.registerContentObserver(uri, false, this, -1);
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            if (!SemDisplayQualityAP.SCREEN_BRIGHTNESS_MODE_URI.equals(uri)) {
                if (SemDisplayQualityAP.SCREEN_MODE_SETTING_URI.equals(uri)) {
                    SemDisplayQualityAP.this.handleScreenModeChanged(
                            SemDisplayQualityAP.this.getScreenModeSetting());
                    return;
                }
                return;
            }
            synchronized (SemDisplayQualityAP.this.mBrightnessModeLock) {
                try {
                    SemDisplayQualityAP semDisplayQualityAP = SemDisplayQualityAP.this;
                    semDisplayQualityAP.mIsBrightnessModeAuto =
                            semDisplayQualityAP.isBrightnessModeAuto(
                                    semDisplayQualityAP.mContentResolver);
                    SemDisplayQualityAP semDisplayQualityAP2 = SemDisplayQualityAP.this;
                    if (semDisplayQualityAP2.mIsBrightnessModeAuto) {
                        semDisplayQualityAP2.handleAutoBrightnessModeOn();
                    } else {
                        semDisplayQualityAP2.handleAutoBrightnessModeOff();
                    }
                } finally {
                }
            }
        }
    }

    public SemDisplayQualityAP(Context context) {
        String str = Build.TYPE;
        this.DEBUG = "eng".equals(str) || "userdebug".equals(str);
        this.mIsBrightnessModeAuto = false;
        this.mBrightnessModeLock = new Object();
        this.mUseScreenStatusAsyncHandle = false;
        this.mContext = context;
        this.mContentResolver = context.getContentResolver();
        this.mSettingsObserver = new SettingsObserver(new Handler());
        if (this.DEBUG) {
            Slog.d(TAG, TAG);
        }
    }

    public void enhanceOutdoorVisibilityByLux(int i) {}

    public int getScreenModeSetting() {
        return Settings.System.getInt(this.mContext.getContentResolver(), SCREEN_MODE_SETTING, 4);
    }

    public abstract void handleAutoBrightnessModeOff();

    public abstract void handleAutoBrightnessModeOn();

    public abstract void handleScreenModeChanged(int i);

    public abstract void handleScreenOff();

    public abstract void handleScreenOffAsync();

    public abstract void handleScreenOn();

    public abstract void handleScreenOnAsync();

    public boolean isBrightnessModeAuto(ContentResolver contentResolver) {
        return Settings.System.getIntForUser(contentResolver, SCREEN_BRIGHTNESS_MODE, 0, -2) == 1;
    }

    public void setAdaptiveSync(boolean z) {}

    public void startScreenStatusReceiver() {
        IntentFilter intentFilter = new IntentFilter();
        if (this.mContext == null) {
            Slog.d(TAG, "mContext is null");
            return;
        }
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        this.mContext.registerReceiver(new ScreenStatusReceiver(this, 0), intentFilter);
    }

    public void startSettingObserver(Uri uri) {
        if (this.mContext == null) {
            Slog.d(TAG, "mContext is null");
        } else {
            this.mSettingsObserver.observeByUri(uri);
        }
    }
}
