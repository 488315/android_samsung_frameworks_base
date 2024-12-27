package com.android.server.wm;

import android.content.ContentResolver;
import android.content.Context;
import android.content.res.Configuration;
import android.database.ContentObserver;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Slog;
import android.view.WindowManager;

import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;

import com.samsung.android.game.SemGameManager;
import com.samsung.android.hardware.secinputdev.SemInputDeviceManager;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import com.samsung.android.rune.CoreRune;
import com.samsung.android.sepunion.Log;
import com.samsung.android.sepunion.SemCustomDumpManager;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class TspStateController {
    public final TspStateController$$ExternalSyntheticLambda1 clearDeadzoneHole;
    public final TspGripCommand m3rdPartyGameTspCommand;
    public final TspGripCommand m3rdPartyTspCommand;
    public final TspGripCommand m3rdPartyTspCommandForIme;
    public boolean mAwake;
    public final Context mContext;
    public final TspGripCommand mCurrentCommand;
    public final TspGripCommand mCustomTspCommand;
    public final ArrayMap mDeadzoneHoleMap;
    public final TspGripCommand mDeviceDefaultTspCommand;
    public final DeviceSize mDeviceSize;
    public String mFocusedWindow;
    public SemGameManager mGameManager;
    public final H mH;
    public WindowState mImeTargetWindow;
    public WindowState mImeWindow;
    public boolean mImeWindowVisible;
    public boolean mIsEnabledCustomSetting;
    public boolean mIsImmShowing;
    public boolean mIsPortrait;
    public String mLastDeadzoneHole;
    public WindowState mLastFocusedWindow;
    public String mLastLandCmd;
    public boolean mLastNoteMode;
    public String mLastPortCmd;
    public String mReserveLandCmd;
    public String mReservePortCmd;
    public SemInputDeviceManager mSemInputDeviceManager;
    public final TspDebug mTspDebug;
    public String mTspThresholdSetting;
    public static final Uri URI_SETTING_TSP_THRESHOLD =
            Settings.Secure.getUriFor("setting_tsp_threshold");
    public static final Uri URI_SETTING_TSP_DEBUG = Settings.Secure.getUriFor("setting_tsp_debug");

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DeviceSize {
        public int height;
        public int initHeight;
        public int initWidth;
        public int width;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class H extends Handler {
        public H() {}

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            WindowState windowState;
            boolean z;
            Bundle bundle;
            int i = message.what;
            boolean z2 = true;
            boolean z3 = false;
            TspStateController tspStateController = TspStateController.this;
            if (i != 1) {
                if (i != 2) {
                    if (i != 3) {
                        return;
                    }
                    if (tspStateController.mIsPortrait
                            && tspStateController.mReservePortCmd != null) {
                        Log.addLogString(
                                "tspstatemanager", "finishScreenTurningOn TSP_COMMAND_TYPE_PORT");
                        tspStateController.writeTspCommandToSysfsInner(
                                1, tspStateController.mReservePortCmd, true);
                        tspStateController.mLastPortCmd = tspStateController.mReservePortCmd;
                        return;
                    } else {
                        if (tspStateController.mReserveLandCmd != null) {
                            Log.addLogString(
                                    "tspstatemanager",
                                    "finishScreenTurningOn TSP_COMMAND_TYPE_LAND");
                            tspStateController.writeTspCommandToSysfsInner(
                                    2, tspStateController.mReserveLandCmd, true);
                            tspStateController.mLastLandCmd = tspStateController.mReserveLandCmd;
                            return;
                        }
                        return;
                    }
                }
                Object obj = message.obj;
                if (obj instanceof WindowState) {
                    WindowState windowState2 = (WindowState) obj;
                    tspStateController.getClass();
                    if (CoreRune.FW_TSP_SIP_MODE && !tspStateController.mIsImmShowing) {
                        tspStateController.mIsImmShowing = true;
                        tspStateController.writeTspCommandToSysfsInner(5, "1", false);
                    }
                    if (CoreRune.FW_TSP_DEADZONE) {
                        boolean z4 = CoreRune.IS_TABLET_DEVICE;
                        TspGripCommand tspGripCommand = tspStateController.mCurrentCommand;
                        if (!z4) {
                            tspGripCommand.reset();
                        }
                        String string =
                                Settings.Secure.getString(
                                        tspStateController.mContext.getContentResolver(),
                                        "default_input_method");
                        if (TextUtils.isEmpty(string)
                                || (!string.contains("SamsungKeypad")
                                        && !string.contains("honeyboard"))) {
                            tspGripCommand.set(tspStateController.m3rdPartyTspCommandForIme);
                        }
                        if (windowState2 != null && (bundle = windowState2.mTspDeadzone) != null) {
                            tspGripCommand.parse(tspStateController.mDeviceSize, bundle);
                        }
                        tspStateController.mImeWindow = windowState2;
                        tspStateController.updateTspCommand(tspGripCommand);
                        return;
                    }
                    return;
                }
                return;
            }
            Object obj2 = message.obj;
            if (obj2 instanceof WindowState) {
                WindowState windowState3 = (WindowState) obj2;
                tspStateController.getClass();
                if (CoreRune.FW_TSP_SIP_MODE) {
                    boolean z5 = tspStateController.mIsImmShowing;
                    if (z5
                            && (windowState3 != tspStateController.mImeTargetWindow
                                    || !tspStateController.mImeWindowVisible)) {
                        tspStateController.mIsImmShowing = false;
                        tspStateController.writeTspCommandToSysfsInner(5, "0", false);
                    } else if (!z5
                            && tspStateController.mImeWindowVisible
                            && windowState3 == tspStateController.mImeTargetWindow) {
                        tspStateController.mIsImmShowing = true;
                        tspStateController.writeTspCommandToSysfsInner(5, "1", false);
                    }
                }
                if (CoreRune.FW_TSP_NOTE_MODE
                        && windowState3 != null
                        && tspStateController.mLastNoteMode != (z = windowState3.mIsTspNoteMode)) {
                    tspStateController.mLastNoteMode = z;
                    tspStateController.writeTspCommandToSysfsInner(6, z ? "1" : "0", false);
                }
                if (CoreRune.FW_TSP_DEADZONE) {
                    if (windowState3 != null
                            && tspStateController.mImeTargetWindow == windowState3
                            && (windowState = tspStateController.mImeWindow) != null
                            && tspStateController.mImeWindowVisible) {
                        H h = tspStateController.mH;
                        if (h.hasMessages(1)) {
                            h.removeMessages(1);
                        }
                        if (h.hasMessages(2)) {
                            h.removeMessages(2);
                        }
                        h.sendMessage(Message.obtain(h, 2, windowState));
                        return;
                    }
                    TspGripCommand tspGripCommand2 = tspStateController.mCurrentCommand;
                    tspGripCommand2.set(tspStateController.mDeviceDefaultTspCommand);
                    if (windowState3 != null) {
                        WindowManager.LayoutParams layoutParams = windowState3.mAttrs;
                        int i2 = layoutParams.type;
                        boolean z6 = i2 >= 2000 && i2 <= 2999;
                        if (!z6) {
                            String str = layoutParams.packageName;
                            if (str == null
                                    || (!str.startsWith("com.sec.android.")
                                            && !str.startsWith("com.samsung.")
                                            && !str.equals(
                                                    KnoxCustomManagerService.SETTING_PKG_NAME))) {
                                z2 = false;
                            }
                            z6 = z2;
                        }
                        if (!z6) {
                            if (CoreRune.FW_USE_SMALLER_GRIPZONE_ON_GAME) {
                                if (tspStateController.mGameManager == null) {
                                    tspStateController.mGameManager = new SemGameManager();
                                }
                                try {
                                    SemGameManager semGameManager = tspStateController.mGameManager;
                                    if (semGameManager != null) {
                                        z3 = semGameManager.isForegroundGame();
                                    }
                                } catch (Exception e) {
                                    Slog.e(
                                            "TspStateManager",
                                            "Exception in checking isForegroundGame, "
                                                    + e.toString());
                                }
                                if (z3) {
                                    tspGripCommand2.set(tspStateController.m3rdPartyGameTspCommand);
                                }
                            }
                            tspGripCommand2.set(tspStateController.m3rdPartyTspCommand);
                        }
                        tspStateController.mFocusedWindow = windowState3.toString();
                        tspGripCommand2.parse(
                                tspStateController.mDeviceSize, windowState3.mTspDeadzone);
                    }
                    tspStateController.updateTspCommand(tspGripCommand2);
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class HoleInfo {
        public int direction;
        public int endY;
        public int startY;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SettingsObserver extends ContentObserver {
        public SettingsObserver(H h) {
            super(h);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            if (uri == null) {
                return;
            }
            if (!TspStateController.URI_SETTING_TSP_THRESHOLD.equals(uri)) {
                if (TspStateController.URI_SETTING_TSP_DEBUG.equals(uri)) {
                    Settings.Secure.getInt(
                            TspStateController.this.mContext.getContentResolver(),
                            "setting_tsp_debug",
                            0);
                    TspStateController tspStateController = TspStateController.this;
                    tspStateController.mTspDebug.mEnabled =
                            Settings.Secure.getInt(
                                            tspStateController.mContext.getContentResolver(),
                                            "setting_tsp_debug",
                                            0)
                                    == 1;
                    return;
                }
                return;
            }
            TspStateController tspStateController2 = TspStateController.this;
            tspStateController2.mTspThresholdSetting =
                    Settings.Secure.getString(
                            tspStateController2.mContext.getContentResolver(),
                            "setting_tsp_threshold");
            TspStateController tspStateController3 = TspStateController.this;
            tspStateController3.initDefaultValues();
            tspStateController3.updateCustomValue();
            Log.addLogString(
                    "tspstatemanager", "onChange " + tspStateController3.mCustomTspCommand);
            tspStateController3.updateWindowPolicy(tspStateController3.mLastFocusedWindow);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class TspDebug {
        public boolean mEnabled;
        public String mHoleCommand;
        public int mInitDisplayHeight;
        public int mInitDisplayWidth;
        public String mLandCommand;
        public String mLastCommand;
        public String mLastNoteMode;
        public String mPortCommand;

        public final StringBuilder updateDebugString() {
            StringBuilder sb = new StringBuilder();
            sb.append(this.mInitDisplayWidth);
            sb.append(',');
            sb.append(this.mInitDisplayHeight);
            sb.append('/');
            sb.append(this.mPortCommand);
            sb.append('/');
            sb.append(this.mLandCommand);
            sb.append('/');
            sb.append(this.mHoleCommand);
            sb.append('/');
            sb.append(this.mLastCommand);
            return sb;
        }
    }

    /* JADX WARN: Type inference failed for: r5v0, types: [com.android.server.wm.TspStateController$$ExternalSyntheticLambda1] */
    public TspStateController(Context context) {
        boolean z = CoreRune.IS_TABLET_DEVICE;
        this.mDeviceDefaultTspCommand = new TspGripCommand(z ? 3 : 1);
        this.m3rdPartyTspCommand = new TspGripCommand(z ? 6 : 4);
        this.m3rdPartyTspCommandForIme = new TspGripCommand(z ? 9 : 7);
        this.m3rdPartyGameTspCommand = new TspGripCommand(10);
        this.mCustomTspCommand = new TspGripCommand(13);
        this.mCurrentCommand = new TspGripCommand(13);
        this.mLastNoteMode = true;
        this.mIsPortrait = true;
        this.mIsEnabledCustomSetting = false;
        this.mDeadzoneHoleMap = new ArrayMap();
        this.mTspThresholdSetting = "";
        H h = new H();
        this.mH = h;
        this.mSemInputDeviceManager = null;
        this.clearDeadzoneHole =
                new Runnable() { // from class:
                                 // com.android.server.wm.TspStateController$$ExternalSyntheticLambda1
                    @Override // java.lang.Runnable
                    public final void run() {
                        TspStateController.this.writeDeadzoneHoleCmd(0, 0, 0);
                    }
                };
        this.mAwake = true;
        this.mContext = context;
        SettingsObserver settingsObserver = new SettingsObserver(h);
        ContentResolver contentResolver = context.getContentResolver();
        contentResolver.registerContentObserver(
                URI_SETTING_TSP_THRESHOLD, false, settingsObserver, -1);
        contentResolver.registerContentObserver(URI_SETTING_TSP_DEBUG, false, settingsObserver, -1);
        this.mTspThresholdSetting =
                Settings.Secure.getString(context.getContentResolver(), "setting_tsp_threshold");
        Settings.Secure.getInt(context.getContentResolver(), "setting_tsp_debug", 0);
        DeviceSize deviceSize = new DeviceSize();
        deviceSize.initWidth = -1;
        deviceSize.initHeight = -1;
        deviceSize.width = -1;
        deviceSize.height = -1;
        this.mDeviceSize = deviceSize;
        initDefaultValues();
        updateCustomValue();
        int i = deviceSize.initWidth;
        int i2 = deviceSize.initHeight;
        TspDebug tspDebug = new TspDebug();
        tspDebug.mInitDisplayWidth = i;
        tspDebug.mInitDisplayHeight = i2;
        this.mTspDebug = tspDebug;
        this.mTspDebug.mEnabled =
                Settings.Secure.getInt(this.mContext.getContentResolver(), "setting_tsp_debug", 0)
                        == 1;
        Log.addLogString("tspstatemanager", "init");
        writeTspCommandToSysfsInner(3, "0,0,0,0", false);
        ((SemCustomDumpManager) context.getSystemService("semcustomdump"))
                .setDumpState("tspstatemanager", (String) null);
    }

    public final void initDefaultValues() {
        TspGripCommand tspGripCommand = this.mDeviceDefaultTspCommand;
        DeviceSize deviceSize = this.mDeviceSize;
        tspGripCommand.parse(deviceSize, null, false);
        this.m3rdPartyTspCommand.parse(deviceSize, null, false);
        this.m3rdPartyTspCommandForIme.parse(deviceSize, null, false);
        if (CoreRune.FW_USE_SMALLER_GRIPZONE_ON_GAME) {
            this.m3rdPartyGameTspCommand.parse(deviceSize, null, false);
        }
    }

    public final void setOrientation(boolean z, boolean z2) {
        if (this.mIsPortrait != z || z2) {
            this.mIsPortrait = z;
            synchronized (this.mDeadzoneHoleMap) {
                this.mDeadzoneHoleMap.clear();
            }
            if (!this.mH.hasCallbacks(this.clearDeadzoneHole)) {
                this.mH.postDelayed(this.clearDeadzoneHole, 150L);
            }
            if (!this.mIsPortrait) {
                if (this.mReserveLandCmd == null) {
                    Slog.e("TspStateManager", "setOrientation mReserveLandCmd is null.");
                    return;
                }
                Log.addLogString(
                        "tspstatemanager",
                        "setOrientation TSP_COMMAND_TYPE_LAND : " + this.mReserveLandCmd);
                writeTspCommandToSysfsInner(2, this.mReserveLandCmd, false);
                this.mLastLandCmd = this.mReserveLandCmd;
                return;
            }
            String str = this.mReservePortCmd;
            if (str == null) {
                Slog.e("TspStateManager", "setOrientation mReservePortCmd is null.");
                return;
            }
            if (str.equals(this.mLastPortCmd)) {
                Log.addLogString("tspstatemanager", "setOrientation TSP_COMMAND_TYPE_SAME");
                writeTspCommandToSysfsInner(4, "2,0", false);
                return;
            }
            Log.addLogString(
                    "tspstatemanager",
                    "setOrientation TSP_COMMAND_TYPE_PORT : " + this.mReservePortCmd);
            writeTspCommandToSysfsInner(1, this.mReservePortCmd, false);
            this.mLastPortCmd = this.mReservePortCmd;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x004d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateCustomValue() {
        /*
            r7 = this;
            java.lang.String r0 = r7.mTspThresholdSetting
            boolean r1 = android.text.TextUtils.isEmpty(r0)
            if (r1 == 0) goto L15
            android.content.Context r0 = r7.mContext
            android.content.res.Resources r0 = r0.getResources()
            r1 = 17040327(0x10403c7, float:2.424728E-38)
            java.lang.String r0 = r0.getString(r1)
        L15:
            java.lang.String r1 = "updateCustomValue customSetting="
            java.lang.String r2 = "TspStateManager"
            com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0.m(r1, r0, r2)
            r1 = 0
            r7.mIsEnabledCustomSetting = r1
            boolean r2 = android.text.TextUtils.isEmpty(r0)
            if (r2 == 0) goto L27
            return
        L27:
            boolean r2 = android.text.TextUtils.isEmpty(r0)
            java.lang.String r3 = ";"
            r4 = 0
            if (r2 == 0) goto L32
        L30:
            r2 = r4
            goto L3c
        L32:
            java.lang.String[] r2 = r0.split(r3)
            int r5 = r2.length
            if (r5 > 0) goto L3a
            goto L30
        L3a:
            r2 = r2[r1]
        L3c:
            com.android.server.wm.TspGripCommand r5 = r7.mCustomTspCommand
            com.android.server.wm.TspStateController$DeviceSize r6 = r7.mDeviceSize
            boolean r2 = r5.parse(r6, r2, r1)
            r7.mIsEnabledCustomSetting = r2
            boolean r2 = android.text.TextUtils.isEmpty(r0)
            if (r2 == 0) goto L4d
            goto L58
        L4d:
            java.lang.String[] r0 = r0.split(r3)
            int r2 = r0.length
            r3 = 1
            if (r2 > r3) goto L56
            goto L58
        L56:
            r4 = r0[r3]
        L58:
            com.android.server.wm.TspGripCommand r0 = r7.m3rdPartyTspCommand
            r0.parse(r6, r4, r1)
            com.android.server.wm.TspGripCommand r7 = r7.m3rdPartyTspCommandForIme
            r7.parse(r6, r4, r1)
            return
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wm.TspStateController.updateCustomValue():void");
    }

    public final void updateTspCommand(TspGripCommand tspGripCommand) {
        if (this.mIsEnabledCustomSetting) {
            tspGripCommand.set(this.mCustomTspCommand);
        }
        StringBuilder sb = new StringBuilder("1,");
        sb.append(tspGripCommand.mPortEdgeZone);
        sb.append(',');
        sb.append(tspGripCommand.mPortX1);
        sb.append(',');
        sb.append(tspGripCommand.mPortX2);
        sb.append(',');
        sb.append(tspGripCommand.mPortY1);
        if (CoreRune.FW_TSP_DEADZONE_V3) {
            sb.append(',');
            sb.append(tspGripCommand.mPortX3);
            sb.append(',');
            sb.append(tspGripCommand.mPortY2);
        }
        String sb2 = sb.toString();
        if (!sb2.equals(this.mLastPortCmd) && this.mIsPortrait) {
            writeTspCommandToSysfsInner(1, sb2, false);
            this.mLastPortCmd = sb2;
        }
        this.mReservePortCmd = sb2;
        String str =
                "2,1,"
                        + tspGripCommand.mLandEdgeZone
                        + ','
                        + tspGripCommand.mLandX1
                        + ','
                        + tspGripCommand.mLandTopRejectWidth
                        + ','
                        + tspGripCommand.mLandBottomRejectWidth
                        + ','
                        + tspGripCommand.mLandTopGripWidth
                        + ','
                        + tspGripCommand.mLandBottomGripWidth;
        if (!str.equals(this.mLastLandCmd) && !this.mIsPortrait) {
            writeTspCommandToSysfsInner(2, str, false);
            this.mLastLandCmd = str;
        }
        this.mReserveLandCmd = str;
    }

    public final void updateWindowPolicy(WindowState windowState) {
        this.mLastFocusedWindow = windowState;
        H h = this.mH;
        if (h.hasMessages(1)) {
            h.removeMessages(1);
        }
        if (h.hasMessages(2)) {
            h.removeMessages(2);
        }
        h.sendMessageDelayed(Message.obtain(h, 1, windowState), 500L);
    }

    public final void writeDeadzoneHoleCmd(int i, int i2, int i3) {
        String str = "0," + i + ',' + i2 + ',' + i3;
        if (str.equals(this.mLastDeadzoneHole)) {
            return;
        }
        this.mLastDeadzoneHole = str;
        Log.addLogString("tspstatemanager", "setDeadzoneHole");
        writeTspCommandToSysfsInner(3, this.mLastDeadzoneHole, false);
    }

    public final void writeTspCommandToSysfsInner(final int i, final String str, final boolean z) {
        new Thread(
                        new Runnable() { // from class:
                            // com.android.server.wm.TspStateController$$ExternalSyntheticLambda0
                            @Override // java.lang.Runnable
                            public final void run() {
                                TspStateController tspStateController = TspStateController.this;
                                boolean z2 = z;
                                int i2 = i;
                                String str2 = str;
                                if (tspStateController.mSemInputDeviceManager == null) {
                                    Slog.d("TspStateManager", "mSemInputDeviceManager is null");
                                    return;
                                }
                                if (tspStateController.mAwake || z2) {
                                    if (i2 == 5) {
                                        if (CoreRune.IS_TABLET_DEVICE && "1".equals(str2)) {
                                            Configuration configuration =
                                                    tspStateController
                                                            .mContext
                                                            .getResources()
                                                            .getConfiguration();
                                            if (configuration.hardKeyboardHidden == 1
                                                    && configuration.keyboard == 2) {
                                                Slog.d(
                                                        "TspStateManager",
                                                        "physical keyboard being exposed. ignore"
                                                            + " it.");
                                                return;
                                            }
                                        }
                                        Log.addLogString("tspstatemanager", "[" + i2 + "]" + str2);
                                        tspStateController.mSemInputDeviceManager.setSipMode(
                                                Integer.parseInt(str2));
                                    } else if (i2 != 6) {
                                        if (tspStateController.mFocusedWindow != null) {
                                            StringBuilder m =
                                                    DirEncryptService$$ExternalSyntheticOutline0.m(
                                                            i2, "[", "]window : ", str2, ": ");
                                            m.append(tspStateController.mFocusedWindow);
                                            Log.addLogString("tspstatemanager", m.toString());
                                            tspStateController.mFocusedWindow = null;
                                        } else {
                                            Log.addLogString(
                                                    "tspstatemanager", "[" + i2 + "]" + str2);
                                        }
                                        tspStateController.mSemInputDeviceManager.setGripData(str2);
                                    } else {
                                        Log.addLogString("tspstatemanager", "[" + i2 + "]" + str2);
                                        tspStateController.mSemInputDeviceManager.setNoteMode(
                                                Integer.parseInt(str2));
                                    }
                                    Slog.d(
                                            "TspStateManager",
                                            "wrote command: cmd=" + str2 + ", type=" + i2);
                                    if (i2 != 5) {
                                        Context context = tspStateController.mContext;
                                        TspStateController.TspDebug tspDebug =
                                                tspStateController.mTspDebug;
                                        if (i2 == 1) {
                                            tspDebug.mPortCommand = str2;
                                            tspDebug.mLastCommand = str2;
                                        } else if (i2 == 2) {
                                            tspDebug.mLandCommand = str2;
                                            tspDebug.mLastCommand = str2;
                                        } else if (i2 == 3) {
                                            tspDebug.mHoleCommand = str2;
                                        } else if (i2 == 6) {
                                            tspDebug.mLastNoteMode = str2;
                                        }
                                        StringBuilder updateDebugString =
                                                tspDebug.updateDebugString();
                                        if (tspDebug.mEnabled) {
                                            Settings.Secure.putString(
                                                    context.getContentResolver(),
                                                    "setting_last_grip_cmd",
                                                    updateDebugString.toString());
                                            Settings.Secure.putString(
                                                    context.getContentResolver(),
                                                    "setting_last_note_mode",
                                                    tspDebug.mLastNoteMode);
                                        }
                                    }
                                }
                            }
                        },
                        "tspStateManager")
                .start();
    }
}
