package com.samsung.android.displayaiqe;

import android.content.Context;
import android.os.IBinder;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Slog;

import com.samsung.android.displaysolution.SemDisplaySolutionManager;

import vendor.qti.hardware.display.aiqe.IDisplayAiqe;

import java.util.NoSuchElementException;

/* loaded from: classes.dex */
public final class DisplayAiqeAidl implements DisplayAiqeHal {
    private static final String ANDROID_UI_ISURFACE_COMPOSER = "android.ui.ISurfaceComposer";
    private static final String DISPLAY_AIQE_AIDL_SERVICE =
            "vendor.qti.hardware.display.aiqe.IDisplayAiqe/default";
    private static final String SURFACE_FLINGER_SERVICE = "SurfaceFlinger";
    private static final String TAG = "DisplayAiqeAidl";
    private static final int TRANSACT_CODE_SET_MDNIE_MODE = 1144;
    private SemDisplaySolutionManager displaySolutionManager;
    private int mASCR_SKIN;
    private IBinder mBinder;
    private int mBlueLightFilterLevel;
    private Context mContext;
    private DisplayAiqeManagerService mDisplayAiqeManager;
    private int mGC_HIGH;
    private int mGC_LOW;
    private int mHighBrightnessLevel;
    private int mMainBlueOffset;
    private int mMainGreeOffset;
    private int mMainRedOffset;
    private IDisplayAiqe mProxy;
    private int mSubBlueOffset;
    private int mSubGreenOffset;
    private int mSubRedOffset;
    private final Object mLock = new Object();
    private boolean mQCMdnieOn = false;
    private boolean mByPassMode = false;
    private boolean mExtraDimMode = false;
    private boolean mHighDynamicRangeMode = false;
    private boolean mBlueLightFilterMode = false;
    private boolean mEnvironmentAdaptiveDisplayMode = false;
    private boolean mHighBrightnessMode = false;
    private boolean mVividnessMode = false;
    private boolean mLowPowerMode = false;
    private int mEnvironmentAdaptiveDisplayLevel = -1;
    private int mInternalDimmingFrame = 37;
    private int mCurInternalDimmingFrame = 0;
    private String mScreenMode = "VIVID";
    private String mContentMode = "UI";
    private String mNaturalMode = "DM";
    private String mPrevmDNIeMode = null;

    public void serviceDied() {
        Slog.e(TAG, "Display AIQE AIDL hal service died");
        synchronized (this.mLock) {
            this.mProxy = null;
        }
        connectToProxy();
    }

    private void connectToProxy() {
        Slog.d(TAG, "connectToProxy: start");
        synchronized (this.mLock) {
            if (this.mProxy != null) {
                return;
            }
            Slog.d(TAG, "connectToProxy: mProxy is null.");
            try {
                try {
                    this.mBinder = ServiceManager.waitForService(DISPLAY_AIQE_AIDL_SERVICE);
                    if (this.mBinder != null) {
                        Slog.d(TAG, "connectToProxy: get mBinder successfully");
                        this.mProxy = IDisplayAiqe.Stub.asInterface(this.mBinder);
                        this.mBinder.linkToDeath(
                                new IBinder
                                        .DeathRecipient() { // from class:
                                                            // com.samsung.android.displayaiqe.DisplayAiqeAidl$$ExternalSyntheticLambda0
                                    @Override // android.os.IBinder.DeathRecipient
                                    public final void binderDied() {
                                        DisplayAiqeAidl.this.serviceDied();
                                    }
                                },
                                0);
                    }
                } catch (NoSuchElementException e) {
                    Slog.e(
                            TAG,
                            "connectToProxy: Display AIQE AIDL hal service not found. Did the"
                                + " service fail to start?",
                            e);
                }
            } catch (RemoteException e2) {
                Slog.e(TAG, "connectToProxy: Display AIQE AIDL hal service not responding.", e2);
            }
        }
    }

    static boolean isServicePresent() {
        try {
            return ServiceManager.isDeclared(DISPLAY_AIQE_AIDL_SERVICE);
        } catch (NoSuchElementException e) {
            Slog.e(TAG, "connectToProxy: Display AIQE Aidl hal service not found.", e);
            return false;
        }
    }

    public DisplayAiqeAidl(DisplayAiqeManagerService displayAiqeManager, Context context) {
        this.mDisplayAiqeManager = displayAiqeManager;
        this.mContext = context;
    }

    @Override // com.samsung.android.displayaiqe.DisplayAiqeHal
    public boolean setByPassMode(boolean enable) {
        Slog.d(TAG, "setByPassMode : " + enable);
        this.mByPassMode = enable;
        mdnieUpdate("ByPassMode");
        return false;
    }

    @Override // com.samsung.android.displayaiqe.DisplayAiqeHal
    public boolean setExtraDimMode(int level) {
        Slog.d(TAG, "setExtraDimMode : level - " + level);
        this.mExtraDimMode = level > 0;
        mdnieUpdate("ExtraDimMode");
        return false;
    }

    @Override // com.samsung.android.displayaiqe.DisplayAiqeHal
    public boolean setHighDynamicRangeMode(boolean enable) {
        Slog.d(TAG, "setHighDynamicRangeMode : " + enable);
        this.mHighDynamicRangeMode = enable;
        mdnieUpdate("HighDynamicRangeMode");
        return false;
    }

    @Override // com.samsung.android.displayaiqe.DisplayAiqeHal
    public boolean setScreenMode(int mode) {
        this.mScreenMode = mode == 0 ? "NATURAL" : "VIVID";
        Slog.d(TAG, "setScreenMode : " + this.mScreenMode);
        mdnieUpdate("ScreenMode");
        return false;
    }

    @Override // com.samsung.android.displayaiqe.DisplayAiqeHal
    public boolean setBlueLightFilterMode(boolean enable, int level) {
        Slog.d(TAG, "setBlueLightFilterMode : " + enable + ", level - " + level);
        this.mBlueLightFilterMode = enable;
        this.mBlueLightFilterLevel = level;
        mdnieUpdate("BlueLightFilterMode");
        return false;
    }

    @Override // com.samsung.android.displayaiqe.DisplayAiqeHal
    public boolean setContentMode(int mode) {
        switch (mode) {
            case 0:
                this.mContentMode = "UI";
                break;
            case 1:
                this.mContentMode = "VIDEO";
                break;
            case 2:
            case IDisplayAiqe.VERSION /* 3 */:
            case 5:
            case 7:
            case 11:
            case 12:
            case 13:
            default:
                this.mContentMode = "UI";
                break;
            case 4:
                this.mContentMode = "CAMERA";
                break;
            case 6:
                this.mContentMode = "GALLERY";
                break;
            case 8:
                this.mContentMode = "BROWSER";
                break;
            case 9:
                this.mContentMode = "EBOOK";
                break;
            case 10:
                this.mContentMode = "EMAIL";
                break;
            case 14:
                this.mContentMode = "VIDEO_BRIGHTNESS";
                break;
            case 15:
                this.mContentMode = "VIDEO_BRIGHTNESS_THIRD";
                break;
        }
        Slog.d(TAG, "setContentMode : " + this.mContentMode);
        mdnieUpdate("ContentMode");
        return false;
    }

    @Override // com.samsung.android.displayaiqe.DisplayAiqeHal
    public String getContentMode() {
        Slog.d(TAG, "getContentMode : " + this.mContentMode);
        return this.mContentMode;
    }

    @Override // com.samsung.android.displayaiqe.DisplayAiqeHal
    public boolean setVividnessMode(int index) {
        this.mASCR_SKIN = index + 1;
        this.mGC_HIGH = index + 1;
        this.mGC_LOW = this.mGC_HIGH + 3;
        Slog.d(
                TAG,
                "setVividnessMode : index - "
                        + index
                        + " ascr_skin("
                        + this.mASCR_SKIN
                        + ") gc_high("
                        + this.mGC_HIGH
                        + ") gc_low("
                        + this.mGC_LOW
                        + ")");
        mdnieUpdate("VividnessMode");
        return false;
    }

    @Override // com.samsung.android.displayaiqe.DisplayAiqeHal
    public boolean setWhiteBalanceMode(int m_r, int m_g, int m_b, int s_r, int s_g, int s_b) {
        this.mMainRedOffset = m_r;
        this.mMainGreeOffset = m_g;
        this.mMainBlueOffset = m_b;
        this.mSubRedOffset = s_r;
        this.mSubGreenOffset = s_g;
        this.mSubBlueOffset = s_b;
        Slog.d(
                TAG,
                "setWhiteBalanceMode : Main RGB offset ("
                        + this.mMainRedOffset
                        + ","
                        + this.mMainGreeOffset
                        + ","
                        + this.mMainBlueOffset
                        + ") , Sub RGB offset ("
                        + this.mSubRedOffset
                        + ","
                        + this.mSubGreenOffset
                        + ","
                        + this.mSubBlueOffset
                        + ")");
        mdnieUpdate("WhiteBalanceMode");
        return false;
    }

    @Override // com.samsung.android.displayaiqe.DisplayAiqeHal
    public boolean setEnvironmentAdaptiveDisplayMode(int mode) {
        this.mEnvironmentAdaptiveDisplayMode = mode == 1;
        if (!this.mEnvironmentAdaptiveDisplayMode) {
            this.mEnvironmentAdaptiveDisplayLevel = -1;
        }
        Slog.d(TAG, "setEnvironmentAdaptiveDisplayMode : " + this.mEnvironmentAdaptiveDisplayMode);
        mdnieUpdate("EnvironmentAdaptiveDisplayMode");
        return false;
    }

    @Override // com.samsung.android.displayaiqe.DisplayAiqeHal
    public boolean setEnvironmentAdaptiveDisplayLevel(int level) {
        Slog.d(TAG, "setEnvironmentAdaptiveDisplayLevel : " + level);
        this.mEnvironmentAdaptiveDisplayLevel = level;
        mdnieUpdate("setEnvironmentAdaptiveDisplayLevel");
        return false;
    }

    @Override // com.samsung.android.displayaiqe.DisplayAiqeHal
    public boolean setHighBrightnessMode(int index) {
        Slog.d(TAG, "setHighBrightnessMode : index - " + index);
        this.mHighBrightnessMode = index > 0;
        this.mHighBrightnessLevel = index;
        mdnieUpdate("HighBrightnessMode");
        return false;
    }

    public void mdnieUpdate(String mode) {
        Slog.d(TAG, "mdnieUpdate function -> " + mode);
        if (this.mByPassMode) {
            setMdnieMode("Primary", "SET_MDNIE_MODE:BYPASS_MODE");
            return;
        }
        if (this.mHighBrightnessMode && !this.mExtraDimMode) {
            if (!this.mBlueLightFilterMode && !this.mEnvironmentAdaptiveDisplayMode) {
                setMdnieMode(
                        "Primary",
                        "SET_MDNIE_MODE:HBM_MODE:"
                                + this.mHighBrightnessLevel
                                + ";SET_MDNIE_WRGB:"
                                + this.mMainRedOffset
                                + ":"
                                + this.mMainGreeOffset
                                + ":"
                                + this.mMainBlueOffset
                                + ";SET_MDNIE_ASCR_SKIN:"
                                + this.mASCR_SKIN
                                + ";SET_MDNIE_GC:"
                                + this.mGC_HIGH);
                return;
            }
            if (this.mBlueLightFilterMode) {
                if (this.mScreenMode == "VIVID") {
                    setMdnieMode(
                            "Primary",
                            "SET_MDNIE_MODE:HBM_MODE:"
                                    + this.mHighBrightnessLevel
                                    + ";SET_MDNIE_ASCR_WIDE:BLF_MODE:VIVID:"
                                    + this.mBlueLightFilterLevel
                                    + ";SET_MDNIE_GC:"
                                    + this.mGC_LOW);
                    return;
                } else {
                    if (this.mScreenMode == "NATURAL") {
                        setMdnieMode(
                                "Primary",
                                "SET_MDNIE_MODE:HBM_MODE:"
                                        + this.mHighBrightnessLevel
                                        + ";SET_MDNIE_ASCR_WIDE:BLF_MODE:NATURAL:"
                                        + this.mBlueLightFilterLevel);
                        return;
                    }
                    return;
                }
            }
            if (this.mEnvironmentAdaptiveDisplayLevel != -1) {
                if (this.mScreenMode == "VIVID") {
                    setMdnieMode(
                            "Primary",
                            "SET_MDNIE_MODE:HBM_MODE:"
                                    + this.mHighBrightnessLevel
                                    + ";SET_MDNIE_ASCR_WIDE:EAD_MODE:VIVID:"
                                    + this.mEnvironmentAdaptiveDisplayLevel
                                    + ";SET_MDNIE_GC:"
                                    + this.mGC_LOW);
                    return;
                } else {
                    if (this.mScreenMode == "NATURAL") {
                        setMdnieMode(
                                "Primary",
                                "SET_MDNIE_MODE:HBM_MODE:"
                                        + this.mHighBrightnessLevel
                                        + ";SET_MDNIE_ASCR_WIDE:EAD_MODE:NATURAL:"
                                        + this.mEnvironmentAdaptiveDisplayLevel);
                        return;
                    }
                    return;
                }
            }
            return;
        }
        if (this.mBlueLightFilterMode) {
            if (this.mScreenMode == "VIVID") {
                setMdnieMode(
                        "Primary",
                        "SET_MDNIE_MODE:BLF_MODE;SET_MDNIE_ASCR_WIDE:BLF_MODE:VIVID:"
                                + this.mBlueLightFilterLevel
                                + ";SET_MDNIE_GC:"
                                + this.mGC_LOW);
                return;
            } else {
                if (this.mScreenMode == "NATURAL") {
                    setMdnieMode(
                            "Primary",
                            "SET_MDNIE_MODE:BLF_MODE;SET_MDNIE_ASCR_WIDE:BLF_MODE:NATURAL:"
                                    + this.mBlueLightFilterLevel);
                    return;
                }
                return;
            }
        }
        if (this.mHighDynamicRangeMode) {
            setMdnieMode("Primary", "SET_MDNIE_MODE:HDR_MODE");
            return;
        }
        if (this.mScreenMode == "VIVID") {
            if (this.mEnvironmentAdaptiveDisplayMode
                    && this.mEnvironmentAdaptiveDisplayLevel != -1) {
                setMdnieMode(
                        "Primary",
                        "SET_MDNIE_MODE:"
                                + this.mContentMode
                                + ":"
                                + this.mScreenMode
                                + ";SET_MDNIE_ASCR_WIDE:EAD_MODE:VIVID:"
                                + this.mEnvironmentAdaptiveDisplayLevel
                                + ";SET_MDNIE_GC:"
                                + this.mGC_LOW
                                + ";SET_MDNIE_LINEAR_SKIN:OFF_OFF");
                return;
            } else {
                setMdnieMode(
                        "Primary",
                        "SET_MDNIE_MODE:"
                                + this.mContentMode
                                + ":"
                                + this.mScreenMode
                                + ";SET_MDNIE_WRGB:"
                                + this.mMainRedOffset
                                + ":"
                                + this.mMainGreeOffset
                                + ":"
                                + this.mMainBlueOffset
                                + ";SET_MDNIE_ASCR_SKIN:"
                                + this.mASCR_SKIN
                                + ";SET_MDNIE_GC:"
                                + this.mGC_HIGH);
                return;
            }
        }
        if (this.mScreenMode == "NATURAL") {
            if (this.mEnvironmentAdaptiveDisplayMode
                    && this.mEnvironmentAdaptiveDisplayLevel != -1) {
                setMdnieMode(
                        "Primary",
                        "SET_MDNIE_MODE:"
                                + this.mContentMode
                                + ":"
                                + this.mScreenMode
                                + "_"
                                + this.mNaturalMode
                                + ";SET_MDNIE_ASCR_WIDE:EAD_MODE:NATURAL:"
                                + this.mEnvironmentAdaptiveDisplayLevel
                                + ";SET_MDNIE_LINEAR_SKIN:OFF_OFF");
            } else {
                setMdnieMode(
                        "Primary",
                        "SET_MDNIE_MODE:"
                                + this.mContentMode
                                + ":"
                                + this.mScreenMode
                                + "_"
                                + this.mNaturalMode);
            }
        }
    }

    public boolean setMdnieMode(String display, String mode) {
        connectToProxy();
        Slog.d(TAG, "setMdnieMode : mode - " + mode);
        if (mode != null && !mode.equals(this.mPrevmDNIeMode)) {
            Slog.d(TAG, "mDNIeModeUpdate -> " + mode);
            this.mPrevmDNIeMode = mode;
            if (mode.contains("HBM_MODE")) {
                if (this.mCurInternalDimmingFrame != this.mInternalDimmingFrame) {
                    sendMdnieModeToSurfaceFlinger(this.mInternalDimmingFrame);
                    Slog.d(
                            TAG,
                            "sendMdnieModeToSurfaceFlinger(" + this.mInternalDimmingFrame + ")");
                    this.mCurInternalDimmingFrame = this.mInternalDimmingFrame;
                }
            } else if (this.mCurInternalDimmingFrame != 0) {
                sendMdnieModeToSurfaceFlinger(0);
                Slog.d(TAG, "sendMdnieModeToSurfaceFlinger(0)");
                this.mCurInternalDimmingFrame = 0;
            }
            if (this.mProxy != null) {
                try {
                    if (!this.mQCMdnieOn) {
                        Slog.d(TAG, "setMdnieMode : first time after booting. mdnie on");
                        this.mProxy.setMdnieMode("Mdnie:On:" + display);
                        this.mQCMdnieOn = true;
                    }
                    this.mProxy.setMdnieMode("Mdnie:SetParams:" + display + ":" + mode);
                    if (this.displaySolutionManager != null) {
                        this.displaySolutionManager.setmDNIeModeState(mode);
                    }
                    return true;
                } catch (Exception e) {
                    Slog.e(TAG, "setMdnieMode: Exception occured.", e);
                }
            }
        }
        return false;
    }

    public boolean sendMdnieModeToSurfaceFlinger(int mode) {
        try {
            IBinder surfaceFlinger = ServiceManager.getService(SURFACE_FLINGER_SERVICE);
            if (surfaceFlinger == null) {
                Slog.d(TAG, "sendMdnieModeToSurfaceFlinger: Failed to get SurfaceFlinger service.");
                return false;
            }
            Parcel data = Parcel.obtain();
            data.writeInterfaceToken(ANDROID_UI_ISURFACE_COMPOSER);
            data.writeInt(mode);
            if (!surfaceFlinger.transact(TRANSACT_CODE_SET_MDNIE_MODE, data, null, 0)) {
                Slog.d(TAG, "sendMdnieModeToSurfaceFlinger: Failed to transact SurfaceFlinger");
                data.recycle();
                return false;
            }
            Slog.d(
                    TAG,
                    "sendMdnieModeToSurfaceFlinger: send mdnie mode to SurfaceFlinger"
                        + " successfully.");
            return true;
        } catch (Exception e) {
            Slog.e(TAG, "sendMdnieModeToSurfaceFlinger: Exception occured.", e);
            return false;
        }
    }

    @Override // com.samsung.android.displayaiqe.DisplayAiqeHal
    public boolean setNaturalMode(String mode) {
        Slog.d(TAG, "setNaturalMode : " + mode);
        this.mNaturalMode = mode;
        return false;
    }

    @Override // com.samsung.android.displayaiqe.DisplayAiqeHal
    public boolean getDisplayService() {
        Slog.d(TAG, "getDisplayService");
        this.displaySolutionManager =
                (SemDisplaySolutionManager) this.mContext.getSystemService("DisplaySolution");
        return false;
    }

    @Override // com.samsung.android.displayaiqe.DisplayAiqeHal
    public int getCoprValue() {
        connectToProxy();
        if (this.mProxy != null) {
            try {
                Slog.d(TAG, "getCoprValue : ");
                int[] arr = this.mProxy.getCoprStats(0);
                for (int i = 0; i < arr.length; i++) {
                    Slog.d(TAG, "arr[" + i + "] - " + arr[i] + " ");
                }
                int i2 = arr.length;
                if (i2 > 4) {
                    return Integer.parseUnsignedInt(
                            String.format("%08x", Integer.valueOf(arr[3])).substring(0, 4), 16);
                }
                return 0;
            } catch (Exception e) {
                Slog.e(TAG, "getCoprValue: Exception occured.", e);
            }
        }
        return 0;
    }
}
