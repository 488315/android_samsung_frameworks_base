package com.samsung.android.displaysolution;

import android.R;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Slog;

import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.CustomizedBinderCallsStatsInternal$$ExternalSyntheticOutline0;
import com.android.server.HermesService$3$$ExternalSyntheticOutline0;
import com.android.server.KnoxCaptureInputFilter$$ExternalSyntheticOutline0;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class AdaptiveDisplaySolutionService {
    public final int AUTO_CURRENT_LIMIT_VERSION;
    public final boolean AVAILABLE_ADAPTIVE_CONTROL;
    public boolean mACLwithBrightness;
    public boolean mADSEnableCondition;
    public final String[] mAdaptiveControlValues;
    public final Context mContext;
    public int mCurrentAutoCurrentLimitValue;
    public boolean mDetailViewState;
    public SemDisplaySolutionManager mSemDisplaySolutionManager;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ADSControlHandler extends Handler {
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ScreenWatchingReceiver extends BroadcastReceiver {
        public ScreenWatchingReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Slog.i("AdaptiveDisplaySolutionService", "action  :  " + action);
            if ("android.intent.action.BOOT_COMPLETED".equals(action)) {
                AdaptiveDisplaySolutionService adaptiveDisplaySolutionService =
                        AdaptiveDisplaySolutionService.this;
                adaptiveDisplaySolutionService.mADSEnableCondition = true;
                ContentResolver contentResolver =
                        adaptiveDisplaySolutionService.mContext.getContentResolver();
                if (adaptiveDisplaySolutionService.AUTO_CURRENT_LIMIT_VERSION < 3
                        || Settings.System.getIntForUser(
                                        contentResolver, "screen_brightness_mode", 0, -2)
                                != 0) {
                    return;
                }
                adaptiveDisplaySolutionService.updateAdaptiveControlStateInt(3);
                return;
            }
            if ("android.intent.action.SCREEN_ON".equals(action)) {
                AdaptiveDisplaySolutionService.this.getClass();
            } else if ("android.intent.action.SCREEN_OFF".equals(action)) {
                AdaptiveDisplaySolutionService.this.getClass();
            } else if ("android.intent.action.USER_PRESENT".equals(action)) {
                AdaptiveDisplaySolutionService.this.getClass();
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SettingsObserver extends ContentObserver {
        public final Uri BRIGHTNESS_MODE_URI;
        public final Uri BRIGHTNESS_URI;
        public final ContentResolver resolver;

        public SettingsObserver(ADSControlHandler aDSControlHandler) {
            super(aDSControlHandler);
            this.resolver = AdaptiveDisplaySolutionService.this.mContext.getContentResolver();
            this.BRIGHTNESS_URI = Settings.System.getUriFor("screen_brightness");
            this.BRIGHTNESS_MODE_URI = Settings.System.getUriFor("screen_brightness_mode");
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            if (this.BRIGHTNESS_URI.equals(uri)) {
                AdaptiveDisplaySolutionService adaptiveDisplaySolutionService =
                        AdaptiveDisplaySolutionService.this;
                int intForUser =
                        Settings.System.getIntForUser(
                                adaptiveDisplaySolutionService.mContext.getContentResolver(),
                                "screen_brightness",
                                0,
                                -2);
                boolean z2 = adaptiveDisplaySolutionService.mACLwithBrightness;
                if (z2) {
                    adaptiveDisplaySolutionService.updateAdaptiveControlStatewithBrightness(
                            intForUser, z2);
                }
            }
            if (AdaptiveDisplaySolutionService.this.AUTO_CURRENT_LIMIT_VERSION == 3
                    && this.BRIGHTNESS_MODE_URI.equals(uri)) {
                if (Settings.System.getIntForUser(this.resolver, "screen_brightness_mode", 0, -2)
                        == 0) {
                    AdaptiveDisplaySolutionService adaptiveDisplaySolutionService2 =
                            AdaptiveDisplaySolutionService.this;
                    if (adaptiveDisplaySolutionService2.mCurrentAutoCurrentLimitValue != 0) {
                        adaptiveDisplaySolutionService2.updateAdaptiveControlStateInt(3);
                    }
                } else {
                    AdaptiveDisplaySolutionService adaptiveDisplaySolutionService3 =
                            AdaptiveDisplaySolutionService.this;
                    if (adaptiveDisplaySolutionService3.mCurrentAutoCurrentLimitValue != 0) {
                        adaptiveDisplaySolutionService3.updateAdaptiveControlStateInt(1);
                    }
                }
            }
            if (AdaptiveDisplaySolutionService.this.AUTO_CURRENT_LIMIT_VERSION == 4
                    && this.BRIGHTNESS_MODE_URI.equals(uri)
                    && Settings.System.getIntForUser(this.resolver, "screen_brightness_mode", 0, -2)
                            == 0) {
                AdaptiveDisplaySolutionService adaptiveDisplaySolutionService4 =
                        AdaptiveDisplaySolutionService.this;
                if (adaptiveDisplaySolutionService4.mCurrentAutoCurrentLimitValue != 0) {
                    adaptiveDisplaySolutionService4.updateAdaptiveControlStateInt(3);
                }
            }
        }
    }

    public AdaptiveDisplaySolutionService(Context context) {
        "eng".equals(Build.TYPE);
        this.mADSEnableCondition = false;
        this.mDetailViewState = false;
        this.AUTO_CURRENT_LIMIT_VERSION = 0;
        this.mCurrentAutoCurrentLimitValue = -1;
        this.mACLwithBrightness = false;
        this.mAdaptiveControlValues = null;
        this.mContext = context;
        ADSControlHandler aDSControlHandler =
                new ADSControlHandler(
                        KnoxCaptureInputFilter$$ExternalSyntheticOutline0.m(
                                        "AdaptiveDisplaySolutionServiceThread")
                                .getLooper(),
                        null);
        boolean z = context.getResources().getBoolean(R.bool.config_allow3rdPartyAppOnInternal);
        if (BatteryService$$ExternalSyntheticOutline0.m45m(
                "/sys/class/lcd/panel/adaptive_control")) {
            this.AVAILABLE_ADAPTIVE_CONTROL = true;
            this.mAdaptiveControlValues =
                    context.getResources()
                            .getStringArray(R.array.config_locationDriverAssistancePackageNames);
            int integer =
                    context.getResources().getInteger(R.integer.config_audio_ring_vol_default);
            this.AUTO_CURRENT_LIMIT_VERSION = integer;
            HermesService$3$$ExternalSyntheticOutline0.m(
                    integer, "AUTO_CURRENT_LIMIT_VERSION : ", "AdaptiveDisplaySolutionService");
        } else {
            this.AVAILABLE_ADAPTIVE_CONTROL = false;
        }
        SettingsObserver settingsObserver = new SettingsObserver(aDSControlHandler);
        ContentResolver contentResolver = context.getContentResolver();
        contentResolver.registerContentObserver(
                Settings.System.getUriFor("screen_brightness"), false, settingsObserver, -1);
        contentResolver.registerContentObserver(
                Settings.System.getUriFor("screen_brightness_mode"), false, settingsObserver, -1);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.USER_PRESENT");
        context.registerReceiverAsUser(
                new ScreenWatchingReceiver(), UserHandle.ALL, intentFilter, null, null);
        SystemProperties.set("sys.adaptivedisplaysolution.adson", "false");
        if (z) {
            SystemProperties.set("sys.adaptivedisplaysolution.adson", "true");
        }
        Slog.i("AdaptiveDisplaySolutionService", "AdaptiveDisplaySolutionService Enabled");
    }

    public static void fileWriteInt(int i, String str) {
        File file = new File(str);
        if (!file.exists()) {
            return;
        }
        FileOutputStream fileOutputStream = null;
        try {
            try {
                FileOutputStream fileOutputStream2 = new FileOutputStream(file);
                try {
                    fileOutputStream2.write(Integer.toString(i).getBytes(Charset.forName("UTF-8")));
                    fileOutputStream2.close();
                } catch (IOException e) {
                    e = e;
                    fileOutputStream = fileOutputStream2;
                    e.printStackTrace();
                    try {
                        fileOutputStream.close();
                    } catch (Exception e2) {
                        e2.printStackTrace();
                    }
                }
            } catch (FileNotFoundException e3) {
                e3.printStackTrace();
            }
        } catch (IOException e4) {
            e = e4;
        }
    }

    public static void fileWriteString(String str, String str2) {
        FileOutputStream fileOutputStream;
        FileOutputStream fileOutputStream2 = null;
        try {
            fileOutputStream = new FileOutputStream(new File(str));
        } catch (FileNotFoundException unused) {
            return;
        } catch (IOException e) {
            e = e;
        }
        try {
            fileOutputStream.write(str2.getBytes());
            fileOutputStream.close();
        } catch (IOException e2) {
            e = e2;
            fileOutputStream2 = fileOutputStream;
            e.printStackTrace();
            try {
                fileOutputStream2.close();
            } catch (Exception e3) {
                e3.printStackTrace();
            }
        }
    }

    public final void setAutoCurrentLimitState(boolean z) {
        AnyMotionDetector$$ExternalSyntheticOutline0.m(
                "AdaptiveDisplaySolutionService",
                BatteryService$$ExternalSyntheticOutline0.m(
                        "setAutoCurrentLimitState(", ") , mADSEnableCondition : ", z),
                this.mADSEnableCondition);
    }

    public final void setAutoCurrentLimitStateWithBrightness(boolean z) {
        int intForUser =
                Settings.System.getIntForUser(
                        this.mContext.getContentResolver(), "screen_brightness", 0, -2);
        this.mACLwithBrightness = true;
        AnyMotionDetector$$ExternalSyntheticOutline0.m(
                "AdaptiveDisplaySolutionService",
                BatteryService$$ExternalSyntheticOutline0.m(
                        "setAutoCurrentLimitStateWithBrightness(", ") , mADSEnableCondition : ", z),
                this.mADSEnableCondition);
        updateAdaptiveControlStatewithBrightness(intForUser, z);
    }

    public final void setGalleryDetailViewMode(boolean z) {
        this.mDetailViewState = z;
        StringBuilder sb = new StringBuilder("setGalleryDetailViewMode() : ");
        sb.append(this.mDetailViewState);
        sb.append(" , mADSEnableCondition : ");
        AnyMotionDetector$$ExternalSyntheticOutline0.m(
                "AdaptiveDisplaySolutionService", sb, this.mADSEnableCondition);
    }

    public final void updateAdaptiveControlStateInt(int i) {
        this.mCurrentAutoCurrentLimitValue = i;
        CustomizedBinderCallsStatsInternal$$ExternalSyntheticOutline0.m(
                new StringBuilder("updateAdaptiveControlStateInt("),
                this.mCurrentAutoCurrentLimitValue,
                ")",
                "AdaptiveDisplaySolutionService");
        SemDisplaySolutionManager semDisplaySolutionManager =
                (SemDisplaySolutionManager) this.mContext.getSystemService("DisplaySolution");
        this.mSemDisplaySolutionManager = semDisplaySolutionManager;
        if (!this.AVAILABLE_ADAPTIVE_CONTROL) {
            Slog.d("AdaptiveDisplaySolutionService", "!AVAILABLE_ADAPTIVE_CONTROL");
            return;
        }
        int i2 = this.mCurrentAutoCurrentLimitValue;
        if (i2 == 0 || i2 == 2) {
            semDisplaySolutionManager.onAutoCurrentLimitOffMode(true);
            this.mSemDisplaySolutionManager.setAutoCurrentLimitOffModeEnabled(true);
        } else if (i2 == 1 || i2 == 3 || i2 == 4 || i2 == 5) {
            semDisplaySolutionManager.onAutoCurrentLimitOffMode(false);
            this.mSemDisplaySolutionManager.setAutoCurrentLimitOffModeEnabled(false);
        }
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                new StringBuilder("[AdaptiveControl]: ACL VALUE ("),
                this.mCurrentAutoCurrentLimitValue,
                ")",
                "AdaptiveDisplaySolutionService");
        fileWriteInt(this.mCurrentAutoCurrentLimitValue, "/sys/class/lcd/panel/adaptive_control");
        if (new File("/sys/class/lcd/panel1/adaptive_control").exists()) {
            fileWriteInt(
                    this.mCurrentAutoCurrentLimitValue, "/sys/class/lcd/panel1/adaptive_control");
        }
        this.mACLwithBrightness = false;
    }

    public final void updateAdaptiveControlStatewithBrightness(int i, boolean z) {
        Slog.i(
                "AdaptiveDisplaySolutionService",
                "updateAdaptiveControlStatewithBrightness(" + z + ")");
        this.mSemDisplaySolutionManager =
                (SemDisplaySolutionManager) this.mContext.getSystemService("DisplaySolution");
        if (!this.AVAILABLE_ADAPTIVE_CONTROL) {
            Slog.d("AdaptiveDisplaySolutionService", "!AVAILABLE_ADAPTIVE_CONTROL");
            return;
        }
        String[] strArr = this.mAdaptiveControlValues;
        if (!z || i < 255) {
            Slog.d("AdaptiveDisplaySolutionService", "[AdaptiveControl]: ACL ON(with brightness)");
            this.mSemDisplaySolutionManager.onAutoCurrentLimitOffMode(false);
            this.mSemDisplaySolutionManager.setAutoCurrentLimitOffModeEnabled(false);
            for (int i2 = 1; i2 < strArr.length; i2++) {
                fileWriteString("/sys/class/lcd/panel/adaptive_control", strArr[i2]);
                if (new File("/sys/class/lcd/panel1/adaptive_control").exists()) {
                    fileWriteString("/sys/class/lcd/panel1/adaptive_control", strArr[i2]);
                }
            }
            return;
        }
        Slog.d("AdaptiveDisplaySolutionService", "[AdaptiveControl]: ACL OFF(with brightness)");
        this.mSemDisplaySolutionManager.onAutoCurrentLimitOffMode(true);
        this.mSemDisplaySolutionManager.setAutoCurrentLimitOffModeEnabled(true);
        for (int length = strArr.length - 1; length >= 0; length--) {
            fileWriteString("/sys/class/lcd/panel/adaptive_control", strArr[length]);
            if (new File("/sys/class/lcd/panel1/adaptive_control").exists()) {
                fileWriteString("/sys/class/lcd/panel1/adaptive_control", strArr[length]);
            }
        }
    }
}
