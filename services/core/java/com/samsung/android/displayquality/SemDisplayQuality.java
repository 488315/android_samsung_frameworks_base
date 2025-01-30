package com.samsung.android.displayquality;

import android.content.Context;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.Settings;
import android.util.Slog;
import com.samsung.android.displayport.DisplayportHelper;

/* loaded from: classes2.dex */
public class SemDisplayQuality extends SemDisplayQualityAP {
    private static final int DISPLAY_COLOR_MANAGED = 0;
    private static final int DISPLAY_COLOR_MODE_RGB = 7;
    private static final int DISPLAY_COLOR_UNMANAGED = 1;
    private static final int INDOOR_MODE = -1;
    private static final int OUTDOOR1_LUX = 5000;
    private static final int OUTDOOR1_MODE = 281;
    private static final int OUTDOOR2_LUX = 10000;
    private static final int OUTDOOR2_MODE = 282;
    private static final int OUTDOOR3_LUX = 50000;
    private static final int OUTDOOR3_MODE = 283;
    private static final int SCREEN_MODE_ADAPTIVE = 4;
    private static final int SCREEN_MODE_AMOLED_CINEMA = 0;
    private static final int SCREEN_MODE_AMOLED_PHOTO = 1;
    private static final int SCREEN_MODE_BASIC = 2;
    private static final int SCREEN_MODE_NATURAL = 3;
    private static final int SCREEN_MODE_READING = 5;
    private static final int SURFACE_FLINGER_TRANSACTION_ADAPTIVE_SYNC_EN = 20003;
    private static final int SURFACE_FLINGER_TRANSACTION_SET_NATIVE_MODE = 1023;
    private static final String TAG = "SemDisplayQualityQct";
    private DisplayportHelper dpHelper;
    private SemDisplayQualityAidlClient mHalClient;
    private int mOutdoorMode;
    private int mSettingScreenMode;
    private static final boolean mSupportLtm = SemDisplayQualityFeature.LTM_SUPPORT;
    private static final boolean mSupportHal = SemDisplayQualityFeature.HAL_SUPPORT;
    private static final boolean mSupportDpRatio = SemDisplayQualityFeature.DP_RATIO_SUPPORT;
    private static final boolean mSupportDPDebug = SemDisplayQualityFeature.DP_DEBUG_SUPPORT;
    private static final boolean mSupportDpBackOff = SemDisplayQualityFeature.DP_BACKOFF_SUPPORT;

    @Override // com.samsung.android.displayquality.SemDisplayQualityAP
    public void handleScreenModeChanged(int i) {
    }

    @Override // com.samsung.android.displayquality.SemDisplayQualityAP
    public void handleScreenOff() {
    }

    @Override // com.samsung.android.displayquality.SemDisplayQualityAP
    public void handleScreenOffAsync() {
    }

    @Override // com.samsung.android.displayquality.SemDisplayQualityAP
    public void handleScreenOn() {
    }

    @Override // com.samsung.android.displayquality.SemDisplayQualityAP
    public void handleScreenOnAsync() {
    }

    public SemDisplayQuality(Context context) {
        super(context);
        this.mOutdoorMode = -1;
        this.mHalClient = null;
        this.dpHelper = null;
        if (mSupportLtm && mSupportHal) {
            Slog.d(TAG, "LTM support!");
            this.mHalClient = new SemDisplayQualityAidlClient();
            startSettingObserver(SemDisplayQualityAP.SCREEN_BRIGHTNESS_MODE_URI);
            new Thread(new Runnable() { // from class: com.samsung.android.displayquality.SemDisplayQuality$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    SemDisplayQuality.this.lambda$new$0();
                }
            }).start();
            if (this.DEBUG) {
                Slog.d(TAG, "BrightnessModeAuto : " + this.mIsBrightnessModeAuto);
            }
        }
        boolean z = mSupportDpRatio;
        if (z || mSupportDPDebug || mSupportDpBackOff) {
            DisplayportHelper displayportHelper = DisplayportHelper.getInstance(context);
            this.dpHelper = displayportHelper;
            if (displayportHelper != null && mSupportDPDebug) {
                displayportHelper.startDPDebug();
            }
            DisplayportHelper displayportHelper2 = this.dpHelper;
            if (displayportHelper2 != null && z) {
                displayportHelper2.startDPRatioChanger();
            }
            DisplayportHelper displayportHelper3 = this.dpHelper;
            if (displayportHelper3 == null || !mSupportDpBackOff) {
                return;
            }
            displayportHelper3.startDPBackOff();
        }
    }

    private void setOutdoorMode(int i) {
        Slog.d(TAG, "setOutdoorMode: " + i);
        IBinder service = ServiceManager.getService("SurfaceFlinger");
        if (service == null) {
            Slog.e(TAG, "Failed to get service SurfaceFlinger");
            return;
        }
        Parcel obtain = Parcel.obtain();
        obtain.writeInterfaceToken("android.ui.ISurfaceComposer");
        if (i == -1) {
            this.mSettingScreenMode = Settings.System.getInt(this.mContext.getContentResolver(), "screen_mode_setting", 4);
            Slog.d(TAG, "getSettingScreenMode: " + this.mSettingScreenMode);
            if (this.mSettingScreenMode == 3) {
                obtain.writeInt(0);
            } else {
                obtain.writeInt(1);
            }
        } else {
            obtain.writeInt(0);
            obtain.writeInt(7);
            obtain.writeInt(i);
        }
        try {
            try {
                service.transact(SURFACE_FLINGER_TRANSACTION_SET_NATIVE_MODE, obtain, null, 0);
            } catch (RemoteException e) {
                Slog.e(TAG, "Failed to set display color: " + e);
            }
        } finally {
            obtain.recycle();
        }
    }

    private void setAmbientLux(int i) {
        if (i >= OUTDOOR3_LUX) {
            if (this.mOutdoorMode == OUTDOOR3_MODE) {
                return;
            } else {
                this.mOutdoorMode = OUTDOOR3_MODE;
            }
        } else if (i >= 10000) {
            if (this.mOutdoorMode == 282) {
                return;
            } else {
                this.mOutdoorMode = 282;
            }
        } else if (i >= 5000) {
            if (this.mOutdoorMode == 281) {
                return;
            } else {
                this.mOutdoorMode = 281;
            }
        } else if (i <= 0 || this.mOutdoorMode == -1) {
            return;
        } else {
            this.mOutdoorMode = -1;
        }
        setOutdoorMode(this.mOutdoorMode);
    }

    @Override // com.samsung.android.displayquality.SemDisplayQualityAP
    public void enhanceOutdoorVisibilityByLux(int i) {
        Slog.d(TAG, "enhanceOutdoorVisibilityByLux: " + i);
        setAmbientLux(i);
    }

    private void enableAdaptiveSync(boolean z) {
        Slog.d(TAG, "enableAdaptiveSync: " + z);
        IBinder service = ServiceManager.getService("SurfaceFlinger");
        if (service == null) {
            Slog.e(TAG, "Failed to get service SurfaceFlinger");
            return;
        }
        Parcel obtain = Parcel.obtain();
        obtain.writeInterfaceToken("android.ui.ISurfaceComposer");
        obtain.writeInt(z ? 1001 : 1000);
        try {
            try {
                service.transact(SURFACE_FLINGER_TRANSACTION_ADAPTIVE_SYNC_EN, obtain, null, 0);
            } catch (RemoteException e) {
                Slog.e(TAG, "Failed to set AdaptiveSync: " + e);
            }
        } finally {
            obtain.recycle();
        }
    }

    @Override // com.samsung.android.displayquality.SemDisplayQualityAP
    public void setAdaptiveSync(boolean z) {
        Slog.d(TAG, "setAdaptiveSync: " + z);
        enableAdaptiveSync(z);
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: checkBrightnessModeAndRunLtm, reason: merged with bridge method [inline-methods] */
    public void lambda$new$0() {
        synchronized (this.mBrightnessModeLock) {
            int i = 10;
            while (true) {
                boolean isBrightnessModeAuto = isBrightnessModeAuto(this.mContentResolver);
                this.mIsBrightnessModeAuto = isBrightnessModeAuto;
                if (this.mHalClient.setOutdoorVisibilityEnhancerEnabled(isBrightnessModeAuto) != 2) {
                    break;
                }
                Slog.d(TAG, "checkBrightnessModeAndRunLtm: setOutdoorVisibilityEnhancerEnabled failed");
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                int i2 = i - 1;
                if (i <= 0) {
                    break;
                } else {
                    i = i2;
                }
            }
        }
    }

    @Override // com.samsung.android.displayquality.SemDisplayQualityAP
    public void handleAutoBrightnessModeOn() {
        this.mHalClient.setOutdoorVisibilityEnhancerEnabled(true);
    }

    @Override // com.samsung.android.displayquality.SemDisplayQualityAP
    public void handleAutoBrightnessModeOff() {
        this.mHalClient.setOutdoorVisibilityEnhancerEnabled(false);
    }
}
