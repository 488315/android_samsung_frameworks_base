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
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.provider.Settings;
import android.util.Slog;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;

/* loaded from: classes2.dex */
public class AdaptiveDisplaySolutionService {
    public int AUTO_CURRENT_LIMIT_VERSION;
    public final boolean AVAILABLE_ADAPTIVE_CONTROL;
    public String[] mAdaptiveControlValues;
    public final Context mContext;
    public ADSControlHandler mHandler;
    public HandlerThread mHandlerThread;
    public int mPlatformBrightnessValue;
    public SemDisplaySolutionManager mSemDisplaySolutionManager;
    public SettingsObserver mSettingsObserver;
    public boolean mUseAdaptiveDisplaySolutionServiceConfig;
    public final boolean DEBUG = "eng".equals(Build.TYPE);
    public boolean mADSEnableCondition = false;
    public boolean mDetailViewState = false;
    public int mCurrentAutoCurrentLimitValue = -1;
    public int mPrevAutoCurrentLimitValue = -1;
    public boolean mACLwithBrightness = false;
    public final String ADAPTIVE_CONTROL_PATH = "/sys/class/lcd/panel/adaptive_control";
    public final String ADAPTIVE_CONTROL_SUB_PATH = "/sys/class/lcd/panel1/adaptive_control";

    public final void receive_screen_off_intent() {
    }

    public final void receive_screen_on_intent() {
    }

    public final void receive_user_present_intent() {
    }

    public final void updateAdaptiveControlState(boolean z) {
    }

    public AdaptiveDisplaySolutionService(Context context) {
        this.mUseAdaptiveDisplaySolutionServiceConfig = false;
        this.AUTO_CURRENT_LIMIT_VERSION = 0;
        this.mAdaptiveControlValues = null;
        this.mContext = context;
        HandlerThread handlerThread = new HandlerThread("AdaptiveDisplaySolutionServiceThread");
        this.mHandlerThread = handlerThread;
        handlerThread.start();
        this.mHandler = new ADSControlHandler(this.mHandlerThread.getLooper());
        this.mUseAdaptiveDisplaySolutionServiceConfig = context.getResources().getBoolean(R.bool.config_actionMenuItemAllCaps);
        if (new File("/sys/class/lcd/panel/adaptive_control").exists()) {
            this.AVAILABLE_ADAPTIVE_CONTROL = true;
            this.mAdaptiveControlValues = context.getResources().getStringArray(R.array.config_loggable_dream_prefixes);
            this.AUTO_CURRENT_LIMIT_VERSION = context.getResources().getInteger(R.integer.config_audio_ring_vol_default);
            Slog.i("AdaptiveDisplaySolutionService", "AUTO_CURRENT_LIMIT_VERSION : " + this.AUTO_CURRENT_LIMIT_VERSION);
        } else {
            this.AVAILABLE_ADAPTIVE_CONTROL = false;
        }
        this.mSettingsObserver = new SettingsObserver(this.mHandler);
        ContentResolver contentResolver = context.getContentResolver();
        contentResolver.registerContentObserver(Settings.System.getUriFor("screen_brightness"), false, this.mSettingsObserver, -1);
        contentResolver.registerContentObserver(Settings.System.getUriFor("screen_brightness_mode"), false, this.mSettingsObserver, -1);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.USER_PRESENT");
        context.registerReceiverAsUser(new ScreenWatchingReceiver(), UserHandle.ALL, intentFilter, null, null);
        SystemProperties.set("sys.adaptivedisplaysolution.adson", "false");
        if (this.mUseAdaptiveDisplaySolutionServiceConfig) {
            SystemProperties.set("sys.adaptivedisplaysolution.adson", "true");
        }
        Slog.i("AdaptiveDisplaySolutionService", "AdaptiveDisplaySolutionService Enabled");
    }

    public final class ScreenWatchingReceiver extends BroadcastReceiver {
        public ScreenWatchingReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Slog.i("AdaptiveDisplaySolutionService", "action  :  " + action);
            if ("android.intent.action.BOOT_COMPLETED".equals(action)) {
                AdaptiveDisplaySolutionService.this.receive_boot_completed_intent();
                return;
            }
            if ("android.intent.action.SCREEN_ON".equals(action)) {
                AdaptiveDisplaySolutionService.this.receive_screen_on_intent();
            } else if ("android.intent.action.SCREEN_OFF".equals(action)) {
                AdaptiveDisplaySolutionService.this.receive_screen_off_intent();
            } else if ("android.intent.action.USER_PRESENT".equals(action)) {
                AdaptiveDisplaySolutionService.this.receive_user_present_intent();
            }
        }
    }

    public final class SettingsObserver extends ContentObserver {
        public final Uri BRIGHTNESS_MODE_URI;
        public final Uri BRIGHTNESS_URI;
        public ContentResolver resolver;

        public SettingsObserver(Handler handler) {
            super(handler);
            this.resolver = AdaptiveDisplaySolutionService.this.mContext.getContentResolver();
            this.BRIGHTNESS_URI = Settings.System.getUriFor("screen_brightness");
            this.BRIGHTNESS_MODE_URI = Settings.System.getUriFor("screen_brightness_mode");
        }

        @Override // android.database.ContentObserver
        public void onChange(boolean z, Uri uri) {
            if (this.BRIGHTNESS_URI.equals(uri)) {
                AdaptiveDisplaySolutionService.this.platform_brightness_value_changed();
            }
            if (AdaptiveDisplaySolutionService.this.AUTO_CURRENT_LIMIT_VERSION == 3 && this.BRIGHTNESS_MODE_URI.equals(uri)) {
                if (Settings.System.getIntForUser(this.resolver, "screen_brightness_mode", 0, -2) == 0) {
                    if (AdaptiveDisplaySolutionService.this.mCurrentAutoCurrentLimitValue != 0) {
                        AdaptiveDisplaySolutionService.this.updateAdaptiveControlStateInt(3);
                    }
                } else if (AdaptiveDisplaySolutionService.this.mCurrentAutoCurrentLimitValue != 0) {
                    AdaptiveDisplaySolutionService.this.updateAdaptiveControlStateInt(1);
                }
            }
            if (AdaptiveDisplaySolutionService.this.AUTO_CURRENT_LIMIT_VERSION == 4 && this.BRIGHTNESS_MODE_URI.equals(uri) && Settings.System.getIntForUser(this.resolver, "screen_brightness_mode", 0, -2) == 0 && AdaptiveDisplaySolutionService.this.mCurrentAutoCurrentLimitValue != 0) {
                AdaptiveDisplaySolutionService.this.updateAdaptiveControlStateInt(3);
            }
        }
    }

    public final void display_setting_value_check() {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        if (this.AUTO_CURRENT_LIMIT_VERSION < 3 || Settings.System.getIntForUser(contentResolver, "screen_brightness_mode", 0, -2) != 0) {
            return;
        }
        updateAdaptiveControlStateInt(3);
    }

    public final class ADSControlHandler extends Handler {
        public ADSControlHandler(Looper looper) {
            super(looper, null);
        }

        @Override // android.os.Handler
        public void handleMessage(Message message) {
            int i = message.what;
        }
    }

    public final void receive_boot_completed_intent() {
        this.mADSEnableCondition = true;
        display_setting_value_check();
    }

    public void setGalleryDetailViewMode(boolean z) {
        this.mDetailViewState = z;
        Slog.d("AdaptiveDisplaySolutionService", "setGalleryDetailViewMode() : " + this.mDetailViewState + " , mADSEnableCondition : " + this.mADSEnableCondition);
        updateAdaptiveControlState(this.mDetailViewState);
    }

    public void setAutoCurrentLimitState(boolean z) {
        Slog.d("AdaptiveDisplaySolutionService", "setAutoCurrentLimitState(" + z + ") , mADSEnableCondition : " + this.mADSEnableCondition);
        updateAdaptiveControlState(z);
    }

    public void setAutoCurrentLimitStateInt(int i) {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        if (this.AUTO_CURRENT_LIMIT_VERSION >= 3 && i == 1 && Settings.System.getIntForUser(contentResolver, "screen_brightness_mode", 0, -2) == 0) {
            i += 2;
        }
        Slog.d("AdaptiveDisplaySolutionService", "setAutoCurrentLimitStateInt(" + i + ") , mADSEnableCondition : " + this.mADSEnableCondition);
        updateAdaptiveControlStateInt(i);
    }

    public void setAutoCurrentLimitStateWithBrightness(boolean z) {
        int i = getting_platform_brightness_value();
        this.mACLwithBrightness = true;
        Slog.d("AdaptiveDisplaySolutionService", "setAutoCurrentLimitStateWithBrightness(" + z + ") , mADSEnableCondition : " + this.mADSEnableCondition);
        updateAdaptiveControlStatewithBrightness(z, i);
    }

    public final void platform_brightness_value_changed() {
        int i = getting_platform_brightness_value();
        boolean z = this.mACLwithBrightness;
        if (z) {
            updateAdaptiveControlStatewithBrightness(z, i);
        }
    }

    public final int getting_platform_brightness_value() {
        int intForUser = Settings.System.getIntForUser(this.mContext.getContentResolver(), "screen_brightness", 0, -2);
        this.mPlatformBrightnessValue = intForUser;
        return intForUser;
    }

    public final void updateAdaptiveControlStatewithBrightness(boolean z, int i) {
        Slog.i("AdaptiveDisplaySolutionService", "updateAdaptiveControlStatewithBrightness(" + z + ")");
        this.mSemDisplaySolutionManager = (SemDisplaySolutionManager) this.mContext.getSystemService("DisplaySolution");
        if (!this.AVAILABLE_ADAPTIVE_CONTROL) {
            Slog.d("AdaptiveDisplaySolutionService", "!AVAILABLE_ADAPTIVE_CONTROL");
            return;
        }
        int i2 = 1;
        if (z && i >= 255) {
            Slog.d("AdaptiveDisplaySolutionService", "[AdaptiveControl]: ACL OFF(with brightness)");
            this.mSemDisplaySolutionManager.onAutoCurrentLimitOffMode(true);
            this.mSemDisplaySolutionManager.setAutoCurrentLimitOffModeEnabled(true);
            for (int length = this.mAdaptiveControlValues.length - 1; length >= 0; length--) {
                fileWriteString("/sys/class/lcd/panel/adaptive_control", this.mAdaptiveControlValues[length]);
                if (new File("/sys/class/lcd/panel1/adaptive_control").exists()) {
                    fileWriteString("/sys/class/lcd/panel1/adaptive_control", this.mAdaptiveControlValues[length]);
                }
            }
            return;
        }
        Slog.d("AdaptiveDisplaySolutionService", "[AdaptiveControl]: ACL ON(with brightness)");
        this.mSemDisplaySolutionManager.onAutoCurrentLimitOffMode(false);
        this.mSemDisplaySolutionManager.setAutoCurrentLimitOffModeEnabled(false);
        while (true) {
            String[] strArr = this.mAdaptiveControlValues;
            if (i2 >= strArr.length) {
                return;
            }
            fileWriteString("/sys/class/lcd/panel/adaptive_control", strArr[i2]);
            if (new File("/sys/class/lcd/panel1/adaptive_control").exists()) {
                fileWriteString("/sys/class/lcd/panel1/adaptive_control", this.mAdaptiveControlValues[i2]);
            }
            i2++;
        }
    }

    public final void updateAdaptiveControlStateInt(int i) {
        this.mCurrentAutoCurrentLimitValue = i;
        Slog.i("AdaptiveDisplaySolutionService", "updateAdaptiveControlStateInt(" + this.mCurrentAutoCurrentLimitValue + ")");
        SemDisplaySolutionManager semDisplaySolutionManager = (SemDisplaySolutionManager) this.mContext.getSystemService("DisplaySolution");
        this.mSemDisplaySolutionManager = semDisplaySolutionManager;
        if (!this.AVAILABLE_ADAPTIVE_CONTROL) {
            Slog.d("AdaptiveDisplaySolutionService", "!AVAILABLE_ADAPTIVE_CONTROL");
            return;
        }
        int i2 = this.mCurrentAutoCurrentLimitValue;
        if (i2 == 0 || i2 == 2) {
            this.mPrevAutoCurrentLimitValue = i2;
            semDisplaySolutionManager.onAutoCurrentLimitOffMode(true);
            this.mSemDisplaySolutionManager.setAutoCurrentLimitOffModeEnabled(true);
        } else if (i2 == 1 || i2 == 3 || i2 == 4 || i2 == 5) {
            this.mPrevAutoCurrentLimitValue = i2;
            semDisplaySolutionManager.onAutoCurrentLimitOffMode(false);
            this.mSemDisplaySolutionManager.setAutoCurrentLimitOffModeEnabled(false);
        }
        Slog.d("AdaptiveDisplaySolutionService", "[AdaptiveControl]: ACL VALUE (" + this.mCurrentAutoCurrentLimitValue + ")");
        fileWriteInt("/sys/class/lcd/panel/adaptive_control", this.mCurrentAutoCurrentLimitValue);
        if (new File("/sys/class/lcd/panel1/adaptive_control").exists()) {
            fileWriteInt("/sys/class/lcd/panel1/adaptive_control", this.mCurrentAutoCurrentLimitValue);
        }
        this.mACLwithBrightness = false;
    }

    public static boolean fileWriteInt(String str, int i) {
        FileOutputStream fileOutputStream;
        IOException e;
        File file = new File(str);
        if (file.exists()) {
            try {
                try {
                    fileOutputStream = new FileOutputStream(file);
                } catch (FileNotFoundException e2) {
                    e2.printStackTrace();
                    return false;
                }
            } catch (IOException e3) {
                fileOutputStream = null;
                e = e3;
            }
            try {
                fileOutputStream.write(Integer.toString(i).getBytes(Charset.forName("UTF-8")));
                fileOutputStream.close();
                return true;
            } catch (IOException e4) {
                e = e4;
                e.printStackTrace();
                try {
                    fileOutputStream.close();
                } catch (Exception e5) {
                    e5.printStackTrace();
                }
                return false;
            }
        }
        return false;
    }

    public static void fileWriteString(String str, String str2) {
        FileOutputStream fileOutputStream = null;
        try {
            FileOutputStream fileOutputStream2 = new FileOutputStream(new File(str));
            try {
                fileOutputStream2.write(str2.getBytes());
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
        } catch (FileNotFoundException unused) {
        } catch (IOException e3) {
            e = e3;
        }
    }
}
