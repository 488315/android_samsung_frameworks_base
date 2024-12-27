package com.samsung.android.displaysolution;

import android.R;
import android.app.ActivityManager;
import android.app.ActivityManagerNative;
import android.app.IProcessObserver;
import android.app.KeyguardManager;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.hardware.display.BrightnessInfo;
import android.hardware.display.DisplayManager;
import android.hardware.input.InputManager;
import android.net.Uri;
import android.os.Build;
import android.os.FactoryTest;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Parcel;
import android.os.RemoteException;
import android.os.SemSystemProperties;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.UserHandle;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Slog;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.KnoxCaptureInputFilter$$ExternalSyntheticOutline0;
import com.android.server.SystemServiceManager$$ExternalSyntheticOutline0;
import com.android.server.accessibility.ProxyManager$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.alarm.GmsAlarmManager$$ExternalSyntheticOutline0;
import com.android.server.am.ActivityManagerService$$ExternalSyntheticOutline0;
import com.samsung.android.app.SemMultiWindowManager;
import com.samsung.android.audio.AudioManagerHelper;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.displayaiqe.DisplayAiqeManager;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.hardware.display.SemMdnieManager;
import com.samsung.android.knox.SemPersonaManager;
import com.samsung.android.knoxguard.service.utils.Constants;
import com.samsung.android.media.SemMediaResourceHelper;
import com.samsung.android.view.SemWindowManager;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class MdnieScenarioControlService {
    public final String[] ACL_CONTROL_GALLERY_APP_LIST;
    public final int ACTION_BROWSER_BRIGHTNESS_DECREASE_FIRST_MILLIS;
    public final int ACTION_BROWSER_BRIGHTNESS_DECREASE_MILLIS;
    public final int ACTION_DETAIL_VIEW_STATE_IN_DEBOUNCE_MILLIS;
    public final int ACTION_MOVIE_PLAYER_STATE_IN_DEBOUNCE_MILLIS;
    public final int ACTION_MOVIE_PLAYER_STATE_OUT_DEBOUNCE_MILLIS;
    public final int ACTION_SET_UI_MODE_DEBOUNCE_MILLIS;
    public final int ACTION_VIDEO_APP_STATE_IN_DEBOUNCE_MILLIS;
    public final String ADAPTIVE_CONTROL_PATH;
    public final String[] ANDROID_APP_LAUNCHER;
    public final int AUTO_CURRENT_LIMIT_VERSION;
    public int BRIGHTNESS_DECREASE_STEP;
    public final String[] CAMERA_APP_LAUNCHER;
    public final String[] CHROMEBROWSER_APP_LAUNCHER;
    public final String[] DAY_OF_USE_SUPPORT_APP_LIST;
    public final int DOU_BRIGHTNESS_STANDARD_VALUE;
    public final String[] EBOOK_APP_LAUNCHER;
    public final String[] EMAIL_APP_LAUNCHER;
    public final String[] EYE_COMFORT_1_05_APP_LIST;
    public final String[] EYE_COMFORT_1_10_APP_LIST;
    public final String[] EYE_COMFORT_1_15_APP_LIST;
    public final String[] EYE_COMFORT_BLACKLIST_APP_LIST;
    public final int FOREGROUND_RESCAN_DEBOUNCE_MILLIS;
    public String FrontPackageName;
    public final String[] GALLERY_APP_LAUNCHER;
    public final String GALLERY_APP_PACKAGENAME;
    public final int GET_SYSTEM_SERVICES_MILLIS;
    public final int IS_CAMERA_APP_DEBOUNCE_MILLIS;
    public String LUX_VALUE;
    public final String ON_PIXEL_RATIO_PATH;
    public final String[] OVERHEAT_CONTROL_SUPPORT_APP_LIST;
    public final String READING_LUX_PATH;
    public final String READING_LUX_SUB_PATH;
    public final String READING_SCENARIO_PATH;
    public final String[] SBROWSER_APP_LAUNCHER;
    public String SCENARIO_VALUE;
    public String SUB_LUX_VALUE;
    public final String[] SVIDEO_APP_LAUNCHER;
    public final String[] SVIDEO_APP_OPTION_LAUNCHER;
    public final String[] VIDEO_APP_LAUNCHER;
    public final AnonymousClass2 eventListener;
    public boolean isLockScreenOn;
    public final boolean mAclDimmingFunction;
    public boolean mAclOffEnabled;
    public boolean mAclState;
    public ActivityManager mActivityManager;
    public boolean mAntiGlareEnable;
    public int mAppLaunchStateInDatabase;
    public boolean mAutoBrightnessMode;
    public boolean mBrowserAppLauncher;
    public final int[] mBrowserBrightnessArray;
    public boolean mBrowserBrightnessDecreaseEnabled;
    public int mBrowserBrightnessDefault;
    public final String[] mBrowserBrightnessStringArray;
    public boolean mBrowserScenarioEnabled;
    public boolean mCameraScenarioEnabled;
    public boolean mColorBlindEnabled;
    public final Context mContext;
    public int mCurAclValue;
    public final DisplaySolutionDataBase mDBHelper;
    public boolean mDayOfUseSupportAppState;
    public SemDesktopModeManager mDesktopModeManager;
    public boolean mDexModeState;
    public DisplayAiqeManager mDisplayAiqeManager;
    public final AnonymousClass4 mDisplayListener;
    public DisplayManager mDisplayManager;
    public final SQLiteDatabase mDisplaySolutionDataBase;
    public boolean mEbookScenarioEnabled;
    public boolean mEmailScenarioEnabled;
    public boolean mEyeComfortScaleAppEnabled;
    public final AnonymousClass3 mFoldStateListener;
    public boolean mGalleryAppState;
    public boolean mGalleryScenarioEnabled;
    public final MSCSControlHandler mHandler;
    public boolean mHdrEffectEnabled;
    public boolean mHighBrightnessModeEnabled;
    public boolean mHighDynamicRangeEnabled;
    public InputManager mInputManager;
    public boolean mIsFolded;
    public KeyguardManager mKeyGuardManager;
    public long mLastUserInputDuration;
    public final Object mLock;
    public SemMdnieManager mMdnieManager;
    public boolean mMediaResourceUsed;
    public boolean mMultiWindowOn;
    public final boolean mNaturalGammaScreenModeSupported;
    public boolean mOverheatControlSupportAppState;
    public int mPrevAclValue;
    public int mPrevPropertyValue;
    public int mPrevRenderIntentValue;
    public String mPrevmDNIeMode;
    public final AnonymousClass1 mProcessObserver;
    public int mQuickPanelState;
    public boolean mReduceBrightColorsActivatedEnabled;
    public int mReduceBrightColorsLevel;
    public boolean mSVideoOptionScenarioEnabled;
    public boolean mSVideoScenarioEnabled;
    public boolean mScreenCurtainEnabled;
    public int mScreenModeSetting;
    public boolean mScreenOffTomeoutAbnormal;
    public boolean mScreenStateOn;
    public SemDisplaySolutionManager mSemDisplaySolutionManager;
    public SemMediaResourceHelper mSemMediaResourceHelper;
    public final SemMultiWindowManager mSemMultiWindowManager;
    public boolean mSettingCondition;
    public final boolean mSupportAPmDNIe;
    public boolean mUIScenarioEnabled;
    public final boolean mUseEyeComfortSolutionServiceConfig;
    public final boolean mUseMdnieScenarioControlConfig;
    public boolean mUseScaleFactorState;
    public int mUserActivityStatus;
    public boolean mVideoEnd;
    public boolean mVideoModeCheck;
    public boolean mVideoScenarioEnabled;
    public final String[] mVisionBoosterStringArray;
    public int mVividnessIndex;
    public boolean mWorkingCondition;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.samsung.android.displaysolution.MdnieScenarioControlService$1, reason: invalid class name */
    public final class AnonymousClass1 extends IProcessObserver.Stub {
        public AnonymousClass1() {
        }

        public final void onForegroundActivitiesChanged(int i, int i2, boolean z) {
            String str;
            MdnieScenarioControlService mdnieScenarioControlService = MdnieScenarioControlService.this;
            if (mdnieScenarioControlService.mHandler == null) {
                return;
            }
            mdnieScenarioControlService.mActivityManager = (ActivityManager) mdnieScenarioControlService.mContext.getSystemService("activity");
            if (i != -1) {
                long uptimeMillis = SystemClock.uptimeMillis();
                MdnieScenarioControlService.this.mHandler.removeMessages(1);
                MdnieScenarioControlService.this.mHandler.sendEmptyMessageAtTime(1, uptimeMillis + r5.FOREGROUND_RESCAN_DEBOUNCE_MILLIS);
                return;
            }
            ActivityManager activityManager = MdnieScenarioControlService.this.mActivityManager;
            if (activityManager == null || activityManager.getRunningTasks(1) == null) {
                str = null;
            } else {
                List<ActivityManager.RunningTaskInfo> runningTasks = MdnieScenarioControlService.this.mActivityManager.getRunningTasks(1);
                if (runningTasks.size() <= 0) {
                    return;
                }
                String packageName = runningTasks.get(0).topActivity.getPackageName() != null ? runningTasks.get(0).topActivity.getPackageName() : null;
                r1 = runningTasks.get(0).topActivity.getClassName() != null ? runningTasks.get(0).topActivity.getClassName() : null;
                if (packageName != null && !packageName.contains("com.att.iqi")) {
                    GmsAlarmManager$$ExternalSyntheticOutline0.m(" packageName : ", packageName, "    className : ", r1, "MdnieScenarioControlService");
                }
                MdnieScenarioControlService.this.FrontPackageName = packageName;
                str = r1;
                r1 = packageName;
            }
            if (r1 == null || str == null) {
                return;
            }
            Message obtainMessage = MdnieScenarioControlService.this.mHandler.obtainMessage();
            obtainMessage.what = 0;
            obtainMessage.obj = r1.concat(str);
            obtainMessage.arg1 = i;
            obtainMessage.arg2 = i2;
            MdnieScenarioControlService.this.mHandler.sendMessage(obtainMessage);
        }

        public final void onForegroundServicesChanged(int i, int i2, int i3) {
        }

        public final void onProcessDied(int i, int i2) {
        }

        public final void onProcessStarted(int i, int i2, int i3, String str, String str2) {
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MSCSControlHandler extends Handler {
        public MSCSControlHandler(Looper looper) {
            super(looper, null);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            boolean z;
            boolean z2;
            boolean z3;
            boolean z4;
            boolean z5;
            boolean z6;
            boolean z7;
            boolean z8;
            boolean z9;
            boolean z10;
            SemDisplaySolutionManager semDisplaySolutionManager;
            String str;
            int i;
            int i2 = message.what;
            int i3 = 0;
            MdnieScenarioControlService mdnieScenarioControlService = MdnieScenarioControlService.this;
            switch (i2) {
                case 0:
                    String str2 = (String) message.obj;
                    mdnieScenarioControlService.mEyeComfortScaleAppEnabled = false;
                    if (mdnieScenarioControlService.mSemMultiWindowManager.getMode() == 0) {
                        mdnieScenarioControlService.mMultiWindowOn = false;
                    } else {
                        mdnieScenarioControlService.mMultiWindowOn = true;
                    }
                    int i4 = 0;
                    while (true) {
                        String[] strArr = mdnieScenarioControlService.ANDROID_APP_LAUNCHER;
                        if (i4 >= strArr.length) {
                            z = false;
                        } else if (str2.contains(strArr[i4])) {
                            z = true;
                        } else {
                            i4++;
                        }
                    }
                    int i5 = 0;
                    while (true) {
                        String[] strArr2 = mdnieScenarioControlService.SBROWSER_APP_LAUNCHER;
                        if (i5 >= strArr2.length) {
                            z2 = false;
                        } else if (str2.contains(strArr2[i5])) {
                            z2 = true;
                        } else {
                            i5++;
                        }
                    }
                    int i6 = 0;
                    while (true) {
                        String[] strArr3 = mdnieScenarioControlService.CHROMEBROWSER_APP_LAUNCHER;
                        if (i6 < strArr3.length) {
                            if (str2.contains(strArr3[i6])) {
                                z2 = true;
                            } else {
                                i6++;
                            }
                        }
                    }
                    int i7 = 0;
                    while (true) {
                        String[] strArr4 = mdnieScenarioControlService.GALLERY_APP_LAUNCHER;
                        if (i7 >= strArr4.length) {
                            z3 = false;
                        } else if (str2.contains(strArr4[i7])) {
                            z3 = true;
                        } else {
                            i7++;
                        }
                    }
                    int i8 = 0;
                    while (true) {
                        String[] strArr5 = mdnieScenarioControlService.CAMERA_APP_LAUNCHER;
                        if (i8 >= strArr5.length) {
                            z4 = false;
                        } else if (str2.contains(strArr5[i8])) {
                            z4 = true;
                        } else {
                            i8++;
                        }
                    }
                    int i9 = 0;
                    while (true) {
                        String[] strArr6 = mdnieScenarioControlService.SVIDEO_APP_LAUNCHER;
                        if (i9 >= strArr6.length) {
                            z5 = false;
                        } else if (str2.contains(strArr6[i9])) {
                            z5 = true;
                        } else {
                            i9++;
                        }
                    }
                    int i10 = 0;
                    while (true) {
                        String[] strArr7 = mdnieScenarioControlService.SVIDEO_APP_OPTION_LAUNCHER;
                        if (i10 >= strArr7.length) {
                            z6 = false;
                        } else if (str2.contains(strArr7[i10])) {
                            z6 = true;
                        } else {
                            i10++;
                        }
                    }
                    int i11 = 0;
                    while (true) {
                        String[] strArr8 = mdnieScenarioControlService.VIDEO_APP_LAUNCHER;
                        if (i11 >= strArr8.length) {
                            z7 = false;
                        } else if (str2.contains(strArr8[i11])) {
                            z7 = true;
                        } else {
                            i11++;
                        }
                    }
                    int i12 = 0;
                    while (true) {
                        String[] strArr9 = mdnieScenarioControlService.EMAIL_APP_LAUNCHER;
                        if (i12 >= strArr9.length) {
                            z8 = false;
                        } else if (str2.contains(strArr9[i12])) {
                            z8 = true;
                        } else {
                            i12++;
                        }
                    }
                    int i13 = 0;
                    while (true) {
                        String[] strArr10 = mdnieScenarioControlService.EBOOK_APP_LAUNCHER;
                        if (i13 >= strArr10.length) {
                            z9 = false;
                        } else if (str2.contains(strArr10[i13])) {
                            z9 = true;
                        } else {
                            i13++;
                        }
                    }
                    int i14 = 0;
                    while (true) {
                        String[] strArr11 = mdnieScenarioControlService.EYE_COMFORT_BLACKLIST_APP_LIST;
                        int length = strArr11.length;
                        z10 = mdnieScenarioControlService.mUseEyeComfortSolutionServiceConfig;
                        if (i14 < length) {
                            if (!str2.contains(strArr11[i14])) {
                                i14++;
                            } else if (z10) {
                                mdnieScenarioControlService.mEyeComfortScaleAppEnabled = true;
                                SemDisplaySolutionManager semDisplaySolutionManager2 = mdnieScenarioControlService.mSemDisplaySolutionManager;
                                if (semDisplaySolutionManager2 != null) {
                                    semDisplaySolutionManager2.setEyeComfortWeightingFactor(0.5f);
                                }
                            }
                        }
                    }
                    int i15 = 0;
                    while (true) {
                        String[] strArr12 = mdnieScenarioControlService.EYE_COMFORT_1_05_APP_LIST;
                        if (i15 < strArr12.length) {
                            if (!str2.contains(strArr12[i15])) {
                                i15++;
                            } else if (z10) {
                                mdnieScenarioControlService.mEyeComfortScaleAppEnabled = true;
                                SemDisplaySolutionManager semDisplaySolutionManager3 = mdnieScenarioControlService.mSemDisplaySolutionManager;
                                if (semDisplaySolutionManager3 != null) {
                                    semDisplaySolutionManager3.setEyeComfortWeightingFactor(1.05f);
                                }
                            }
                        }
                    }
                    int i16 = 0;
                    while (true) {
                        String[] strArr13 = mdnieScenarioControlService.EYE_COMFORT_1_10_APP_LIST;
                        if (i16 < strArr13.length) {
                            if (!str2.contains(strArr13[i16])) {
                                i16++;
                            } else if (z10) {
                                mdnieScenarioControlService.mEyeComfortScaleAppEnabled = true;
                                SemDisplaySolutionManager semDisplaySolutionManager4 = mdnieScenarioControlService.mSemDisplaySolutionManager;
                                if (semDisplaySolutionManager4 != null) {
                                    semDisplaySolutionManager4.setEyeComfortWeightingFactor(1.1f);
                                }
                            }
                        }
                    }
                    int i17 = 0;
                    while (true) {
                        String[] strArr14 = mdnieScenarioControlService.EYE_COMFORT_1_15_APP_LIST;
                        if (i17 < strArr14.length) {
                            if (!str2.contains(strArr14[i17])) {
                                i17++;
                            } else if (z10) {
                                mdnieScenarioControlService.mEyeComfortScaleAppEnabled = true;
                                SemDisplaySolutionManager semDisplaySolutionManager5 = mdnieScenarioControlService.mSemDisplaySolutionManager;
                                if (semDisplaySolutionManager5 != null) {
                                    semDisplaySolutionManager5.setEyeComfortWeightingFactor(1.15f);
                                }
                            }
                        }
                    }
                    if (!mdnieScenarioControlService.mEyeComfortScaleAppEnabled && z10 && (semDisplaySolutionManager = mdnieScenarioControlService.mSemDisplaySolutionManager) != null) {
                        semDisplaySolutionManager.setEyeComfortWeightingFactor(1.0f);
                    }
                    boolean z11 = mdnieScenarioControlService.mMultiWindowOn;
                    MSCSControlHandler mSCSControlHandler = mdnieScenarioControlService.mHandler;
                    if (z11 || !z2) {
                        if (z11 || !z3) {
                            if (z11 || !z4) {
                                if (z11 || !z5) {
                                    if (z11 || !z6) {
                                        if (z11 || !z7) {
                                            if (z11 || !z8) {
                                                if (z11 || !z9) {
                                                    if (z11 || !z) {
                                                        long uptimeMillis = SystemClock.uptimeMillis();
                                                        mdnieScenarioControlService.scenario_enable_reset();
                                                        Slog.v("MdnieScenarioControlService", "setUIMode from UI function(3)");
                                                        mSCSControlHandler.removeMessages(2);
                                                        mSCSControlHandler.sendEmptyMessageAtTime(2, uptimeMillis + mdnieScenarioControlService.ACTION_SET_UI_MODE_DEBOUNCE_MILLIS);
                                                        break;
                                                    } else if (!mdnieScenarioControlService.mUIScenarioEnabled) {
                                                        mdnieScenarioControlService.scenario_enable_reset();
                                                        mdnieScenarioControlService.mUIScenarioEnabled = true;
                                                        Slog.v("MdnieScenarioControlService", "setUIMode from UI function(2)");
                                                        mSCSControlHandler.removeMessages(2);
                                                        mSCSControlHandler.sendEmptyMessage(2);
                                                        break;
                                                    }
                                                } else if (!mdnieScenarioControlService.mEbookScenarioEnabled) {
                                                    mdnieScenarioControlService.scenario_enable_reset();
                                                    mdnieScenarioControlService.mEbookScenarioEnabled = true;
                                                    mSCSControlHandler.removeMessages(10);
                                                    mSCSControlHandler.sendEmptyMessage(10);
                                                    break;
                                                }
                                            } else if (!mdnieScenarioControlService.mEmailScenarioEnabled) {
                                                mdnieScenarioControlService.scenario_enable_reset();
                                                mdnieScenarioControlService.mEmailScenarioEnabled = true;
                                                mSCSControlHandler.removeMessages(9);
                                                mSCSControlHandler.sendEmptyMessage(9);
                                                break;
                                            }
                                        } else if (!mdnieScenarioControlService.mVideoScenarioEnabled) {
                                            mdnieScenarioControlService.scenario_enable_reset();
                                            mdnieScenarioControlService.mVideoScenarioEnabled = true;
                                            long uptimeMillis2 = SystemClock.uptimeMillis();
                                            mSCSControlHandler.removeMessages(6);
                                            mSCSControlHandler.sendEmptyMessageAtTime(6, uptimeMillis2 + mdnieScenarioControlService.ACTION_VIDEO_APP_STATE_IN_DEBOUNCE_MILLIS);
                                            break;
                                        }
                                    } else if (!mdnieScenarioControlService.mSVideoOptionScenarioEnabled) {
                                        mdnieScenarioControlService.scenario_enable_reset();
                                        mdnieScenarioControlService.mSVideoOptionScenarioEnabled = true;
                                        mSCSControlHandler.removeMessages(14);
                                        mSCSControlHandler.sendEmptyMessage(14);
                                        break;
                                    }
                                } else if (!mdnieScenarioControlService.mSVideoScenarioEnabled) {
                                    mdnieScenarioControlService.scenario_enable_reset();
                                    mdnieScenarioControlService.mSVideoScenarioEnabled = true;
                                    long uptimeMillis3 = SystemClock.uptimeMillis();
                                    AnyMotionDetector$$ExternalSyntheticOutline0.m("MdnieScenarioControlService", new StringBuilder("in video Real Multi Window State : "), mdnieScenarioControlService.mMultiWindowOn);
                                    if (mdnieScenarioControlService.mMultiWindowOn) {
                                        mSCSControlHandler.removeMessages(8);
                                        mSCSControlHandler.sendEmptyMessageAtTime(8, uptimeMillis3 + mdnieScenarioControlService.ACTION_MOVIE_PLAYER_STATE_OUT_DEBOUNCE_MILLIS);
                                        break;
                                    } else {
                                        mSCSControlHandler.removeMessages(7);
                                        mSCSControlHandler.sendEmptyMessageAtTime(7, uptimeMillis3 + mdnieScenarioControlService.ACTION_MOVIE_PLAYER_STATE_IN_DEBOUNCE_MILLIS);
                                        break;
                                    }
                                }
                            } else if (!mdnieScenarioControlService.mCameraScenarioEnabled) {
                                mdnieScenarioControlService.scenario_enable_reset();
                                mdnieScenarioControlService.mCameraScenarioEnabled = true;
                                long uptimeMillis4 = SystemClock.uptimeMillis();
                                mSCSControlHandler.removeMessages(5);
                                mSCSControlHandler.sendEmptyMessageAtTime(5, uptimeMillis4 + mdnieScenarioControlService.IS_CAMERA_APP_DEBOUNCE_MILLIS);
                                break;
                            }
                        } else if (!mdnieScenarioControlService.mGalleryScenarioEnabled) {
                            mdnieScenarioControlService.scenario_enable_reset();
                            mdnieScenarioControlService.mGalleryScenarioEnabled = true;
                            long uptimeMillis5 = SystemClock.uptimeMillis();
                            mSCSControlHandler.removeMessages(4);
                            mSCSControlHandler.sendEmptyMessageAtTime(4, uptimeMillis5 + mdnieScenarioControlService.ACTION_DETAIL_VIEW_STATE_IN_DEBOUNCE_MILLIS);
                            break;
                        }
                    } else if (!mdnieScenarioControlService.mBrowserScenarioEnabled) {
                        mdnieScenarioControlService.scenario_enable_reset();
                        mdnieScenarioControlService.mBrowserScenarioEnabled = true;
                        mSCSControlHandler.removeMessages(3);
                        mSCSControlHandler.sendEmptyMessage(3);
                        break;
                    }
                    break;
                case 1:
                    try {
                        mdnieScenarioControlService.mProcessObserver.onForegroundActivitiesChanged(-1, 0, false);
                        break;
                    } catch (RemoteException unused) {
                        return;
                    }
                case 2:
                    mdnieScenarioControlService.setUIMode(mdnieScenarioControlService.FrontPackageName);
                    break;
                case 3:
                    String str3 = mdnieScenarioControlService.FrontPackageName;
                    mdnieScenarioControlService.getScenarioMode();
                    mdnieScenarioControlService.mAclOffEnabled = mdnieScenarioControlService.getting_autocurrentlimit_state();
                    int i18 = 0;
                    while (true) {
                        String[] strArr15 = mdnieScenarioControlService.DAY_OF_USE_SUPPORT_APP_LIST;
                        if (i18 < strArr15.length) {
                            if (strArr15[i18].contains(str3)) {
                                mdnieScenarioControlService.setDouAppLaunch(true);
                                mdnieScenarioControlService.mDayOfUseSupportAppState = true;
                            } else {
                                mdnieScenarioControlService.setDouAppLaunch(false);
                                mdnieScenarioControlService.mDayOfUseSupportAppState = false;
                                i18++;
                            }
                        }
                    }
                    mdnieScenarioControlService.mdnie_reset();
                    String str4 = mdnieScenarioControlService.SCENARIO_VALUE;
                    if (str4 != null && !str4.equals("8") && !mdnieScenarioControlService.SCENARIO_VALUE.contains("BROWSER") && !mdnieScenarioControlService.isLockScreenOn) {
                        ProxyManager$$ExternalSyntheticOutline0.m("MdnieScenarioControlService", new StringBuilder("mAclOffEnabled : "), mdnieScenarioControlService.mAclOffEnabled);
                        if (mdnieScenarioControlService.mAclOffEnabled) {
                            mdnieScenarioControlService.setAclModeScenario(1, false);
                        }
                        mdnieScenarioControlService.setMdnieScenarioMode(8);
                        if (mdnieScenarioControlService.DOU_BRIGHTNESS_STANDARD_VALUE == 255 || !mdnieScenarioControlService.mDayOfUseSupportAppState || mdnieScenarioControlService.mAutoBrightnessMode || mdnieScenarioControlService.mHighBrightnessModeEnabled) {
                            mdnieScenarioControlService.setBrightnessScaleFactor(0);
                        } else {
                            mdnieScenarioControlService.setBrightnessScaleFactor(6);
                        }
                        AudioManagerHelper.semSetAudioHDR(false);
                        mdnieScenarioControlService.setVideoAppLaunch(false);
                        mdnieScenarioControlService.setCameraAppLaunch(false);
                        mdnieScenarioControlService.mGalleryAppState = false;
                        mdnieScenarioControlService.mOverheatControlSupportAppState = false;
                        Slog.v("MdnieScenarioControlService", "setBrowserMode from Browser function");
                        break;
                    }
                    break;
                case 4:
                    String str5 = mdnieScenarioControlService.FrontPackageName;
                    mdnieScenarioControlService.getScenarioMode();
                    int i19 = 0;
                    boolean z12 = false;
                    while (true) {
                        String[] strArr16 = mdnieScenarioControlService.ACL_CONTROL_GALLERY_APP_LIST;
                        if (i19 >= strArr16.length) {
                            mdnieScenarioControlService.mdnie_reset();
                            String str6 = mdnieScenarioControlService.SCENARIO_VALUE;
                            if (str6 != null && !str6.equals("6") && !mdnieScenarioControlService.SCENARIO_VALUE.contains("GALLERY") && !mdnieScenarioControlService.isLockScreenOn) {
                                if (z12) {
                                    mdnieScenarioControlService.setAclModeScenario(0, true);
                                }
                                mdnieScenarioControlService.setMdnieScenarioMode(6);
                                if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_LARGE_COVER_SCREEN", false)) {
                                    mdnieScenarioControlService.setBrightnessScaleFactor(3);
                                } else {
                                    mdnieScenarioControlService.setBrightnessScaleFactor(0);
                                }
                                AudioManagerHelper.semSetAudioHDR(false);
                                mdnieScenarioControlService.setVideoAppLaunch(false);
                                mdnieScenarioControlService.setCameraAppLaunch(false);
                                mdnieScenarioControlService.setDouAppLaunch(false);
                                mdnieScenarioControlService.mGalleryAppState = true;
                                mdnieScenarioControlService.mDayOfUseSupportAppState = false;
                                mdnieScenarioControlService.mOverheatControlSupportAppState = false;
                                Slog.v("MdnieScenarioControlService", "setGalleryMode from Gallery function");
                                break;
                            }
                        } else {
                            if (strArr16[i19].contains(str5)) {
                                z12 = true;
                            }
                            i19++;
                        }
                    }
                    break;
                case 5:
                    String str7 = mdnieScenarioControlService.FrontPackageName;
                    mdnieScenarioControlService.getScenarioMode();
                    mdnieScenarioControlService.mAclOffEnabled = mdnieScenarioControlService.getting_autocurrentlimit_state();
                    mdnieScenarioControlService.mdnie_reset();
                    if (mdnieScenarioControlService.mVideoEnd) {
                        mdnieScenarioControlService.mVideoEnd = false;
                        break;
                    } else {
                        String str8 = mdnieScenarioControlService.SCENARIO_VALUE;
                        if (str8 != null && !str8.equals("4") && !mdnieScenarioControlService.SCENARIO_VALUE.contains("CAMERA") && !mdnieScenarioControlService.isLockScreenOn) {
                            ProxyManager$$ExternalSyntheticOutline0.m("MdnieScenarioControlService", new StringBuilder("mAclOffEnabled : "), mdnieScenarioControlService.mAclOffEnabled);
                            if (mdnieScenarioControlService.mAclOffEnabled) {
                                mdnieScenarioControlService.setAclModeScenario(1, false);
                            }
                            mdnieScenarioControlService.setMdnieScenarioMode(4);
                            if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_FRAMEWORK_SUPPORT_LARGE_COVER_SCREEN", false)) {
                                mdnieScenarioControlService.setBrightnessScaleFactor(4);
                            } else {
                                mdnieScenarioControlService.setBrightnessScaleFactor(0);
                            }
                            AudioManagerHelper.semSetAudioHDR(false);
                            mdnieScenarioControlService.setVideoAppLaunch(false);
                            mdnieScenarioControlService.setCameraAppLaunch(true);
                            mdnieScenarioControlService.setDouAppLaunch(false);
                            mdnieScenarioControlService.mGalleryAppState = false;
                            mdnieScenarioControlService.mDayOfUseSupportAppState = false;
                            Slog.v("MdnieScenarioControlService", "setCameraMode from Camera function");
                            break;
                        }
                    }
                    break;
                case 6:
                    String str9 = mdnieScenarioControlService.FrontPackageName;
                    mdnieScenarioControlService.getScenarioMode();
                    int i20 = 0;
                    while (true) {
                        String[] strArr17 = mdnieScenarioControlService.DAY_OF_USE_SUPPORT_APP_LIST;
                        if (i20 < strArr17.length) {
                            if (strArr17[i20].contains(str9)) {
                                mdnieScenarioControlService.setDouAppLaunch(true);
                                mdnieScenarioControlService.mDayOfUseSupportAppState = true;
                            } else {
                                mdnieScenarioControlService.setDouAppLaunch(false);
                                mdnieScenarioControlService.mDayOfUseSupportAppState = false;
                                i20++;
                            }
                        }
                    }
                    boolean z13 = mdnieScenarioControlService.getting_setting_value();
                    mdnieScenarioControlService.getting_knox_mode_enabled();
                    mdnieScenarioControlService.mAclOffEnabled = mdnieScenarioControlService.getting_autocurrentlimit_state();
                    mdnieScenarioControlService.mdnie_reset();
                    StringBuilder sb = new StringBuilder("hdr_effect_enable : ");
                    sb.append(z13);
                    sb.append(" , app_setting_value : ");
                    ProxyManager$$ExternalSyntheticOutline0.m("MdnieScenarioControlService", sb, mdnieScenarioControlService.getAppSettingState(str9) == 1);
                    int i21 = mdnieScenarioControlService.DOU_BRIGHTNESS_STANDARD_VALUE;
                    if (z13 && mdnieScenarioControlService.getAppSettingState(str9) == 1) {
                        boolean z14 = mdnieScenarioControlService.mMultiWindowOn;
                        if (!z14) {
                            String str10 = mdnieScenarioControlService.SCENARIO_VALUE;
                            if (str10 != null && !str10.equals("15") && !mdnieScenarioControlService.SCENARIO_VALUE.contains("VIDEO_ENHANCER_THIRD") && !mdnieScenarioControlService.SCENARIO_VALUE.contains("VIDEO_BRIGHTNESS_THIRD") && !mdnieScenarioControlService.isLockScreenOn) {
                                StringBuilder sb2 = new StringBuilder("mAclOffEnabled : ");
                                sb2.append(mdnieScenarioControlService.mAclOffEnabled);
                                sb2.append(" mHighDynamicRangeEnabled : ");
                                ProxyManager$$ExternalSyntheticOutline0.m("MdnieScenarioControlService", sb2, mdnieScenarioControlService.mHighDynamicRangeEnabled);
                                if (mdnieScenarioControlService.mAclOffEnabled && !mdnieScenarioControlService.mHighDynamicRangeEnabled) {
                                    mdnieScenarioControlService.setAclModeScenario(1, false);
                                }
                                mdnieScenarioControlService.setMdnieScenarioMode(15);
                                ProxyManager$$ExternalSyntheticOutline0.m("MdnieScenarioControlService", new StringBuilder("mDexModeState : "), mdnieScenarioControlService.mDexModeState);
                                if (!mdnieScenarioControlService.mDexModeState) {
                                    mdnieScenarioControlService.setBrightnessScaleFactor(2);
                                } else if (i21 == 255 || !mdnieScenarioControlService.mDayOfUseSupportAppState || mdnieScenarioControlService.mAutoBrightnessMode || mdnieScenarioControlService.mHighBrightnessModeEnabled) {
                                    mdnieScenarioControlService.setBrightnessScaleFactor(0);
                                } else {
                                    mdnieScenarioControlService.setBrightnessScaleFactor(6);
                                }
                                AudioManagerHelper.semSetAudioHDR(true);
                                mdnieScenarioControlService.setVideoAppLaunch(true);
                                mdnieScenarioControlService.setCameraAppLaunch(false);
                                mdnieScenarioControlService.mGalleryAppState = false;
                                mdnieScenarioControlService.mOverheatControlSupportAppState = false;
                                Slog.v("MdnieScenarioControlService", "setVideoMode from Video function(VIDEO_ENHANCER_THIRD)");
                                break;
                            }
                        } else if (z14 && (str = mdnieScenarioControlService.SCENARIO_VALUE) != null && !str.equals("1") && !mdnieScenarioControlService.SCENARIO_VALUE.contains("VIDEO") && !mdnieScenarioControlService.isLockScreenOn) {
                            StringBuilder sb3 = new StringBuilder("mAclOffEnabled : ");
                            sb3.append(mdnieScenarioControlService.mAclOffEnabled);
                            sb3.append(" mHighDynamicRangeEnabled : ");
                            ProxyManager$$ExternalSyntheticOutline0.m("MdnieScenarioControlService", sb3, mdnieScenarioControlService.mHighDynamicRangeEnabled);
                            if (mdnieScenarioControlService.mAclOffEnabled && !mdnieScenarioControlService.mHighDynamicRangeEnabled) {
                                mdnieScenarioControlService.setAclModeScenario(1, false);
                            }
                            mdnieScenarioControlService.setMdnieScenarioMode(1);
                            if (i21 == 255 || !mdnieScenarioControlService.mDayOfUseSupportAppState || mdnieScenarioControlService.mAutoBrightnessMode || mdnieScenarioControlService.mHighBrightnessModeEnabled) {
                                mdnieScenarioControlService.setBrightnessScaleFactor(0);
                            } else {
                                mdnieScenarioControlService.setBrightnessScaleFactor(6);
                            }
                            AudioManagerHelper.semSetAudioHDR(false);
                            mdnieScenarioControlService.setVideoAppLaunch(true);
                            mdnieScenarioControlService.setCameraAppLaunch(false);
                            mdnieScenarioControlService.mGalleryAppState = false;
                            mdnieScenarioControlService.mOverheatControlSupportAppState = false;
                            Slog.v("MdnieScenarioControlService", "setVideoMode from Video function");
                            break;
                        }
                    } else {
                        String str11 = mdnieScenarioControlService.SCENARIO_VALUE;
                        if (str11 != null && !str11.equals("1") && !mdnieScenarioControlService.SCENARIO_VALUE.contains("VIDEO") && !mdnieScenarioControlService.isLockScreenOn) {
                            StringBuilder sb4 = new StringBuilder("mAclOffEnabled : ");
                            sb4.append(mdnieScenarioControlService.mAclOffEnabled);
                            sb4.append(" mHighDynamicRangeEnabled : ");
                            ProxyManager$$ExternalSyntheticOutline0.m("MdnieScenarioControlService", sb4, mdnieScenarioControlService.mHighDynamicRangeEnabled);
                            if (mdnieScenarioControlService.mAclOffEnabled && !mdnieScenarioControlService.mHighDynamicRangeEnabled) {
                                mdnieScenarioControlService.setAclModeScenario(1, false);
                            }
                            mdnieScenarioControlService.setMdnieScenarioMode(1);
                            if (i21 == 255 || !mdnieScenarioControlService.mDayOfUseSupportAppState || mdnieScenarioControlService.mAutoBrightnessMode || mdnieScenarioControlService.mHighBrightnessModeEnabled) {
                                mdnieScenarioControlService.setBrightnessScaleFactor(0);
                            } else {
                                mdnieScenarioControlService.setBrightnessScaleFactor(6);
                            }
                            AudioManagerHelper.semSetAudioHDR(false);
                            mdnieScenarioControlService.setVideoAppLaunch(true);
                            mdnieScenarioControlService.setCameraAppLaunch(false);
                            mdnieScenarioControlService.mGalleryAppState = false;
                            mdnieScenarioControlService.mOverheatControlSupportAppState = false;
                            Slog.v("MdnieScenarioControlService", "setVideoMode from Video function");
                            break;
                        }
                    }
                    break;
                case 7:
                    MdnieScenarioControlService.m1140$$Nest$msetSVideoMode(mdnieScenarioControlService, true, mdnieScenarioControlService.FrontPackageName);
                    break;
                case 8:
                    MdnieScenarioControlService.m1140$$Nest$msetSVideoMode(mdnieScenarioControlService, false, mdnieScenarioControlService.FrontPackageName);
                    break;
                case 9:
                    mdnieScenarioControlService.getScenarioMode();
                    mdnieScenarioControlService.mAclOffEnabled = mdnieScenarioControlService.getting_autocurrentlimit_state();
                    mdnieScenarioControlService.mdnie_reset();
                    String str12 = mdnieScenarioControlService.SCENARIO_VALUE;
                    if (str12 != null && !str12.equals("10") && !mdnieScenarioControlService.SCENARIO_VALUE.contains("EMAIL") && !mdnieScenarioControlService.isLockScreenOn) {
                        ProxyManager$$ExternalSyntheticOutline0.m("MdnieScenarioControlService", new StringBuilder("mAclOffEnabled : "), mdnieScenarioControlService.mAclOffEnabled);
                        if (mdnieScenarioControlService.mAclOffEnabled) {
                            mdnieScenarioControlService.setAclModeScenario(1, false);
                        }
                        mdnieScenarioControlService.setMdnieScenarioMode(10);
                        mdnieScenarioControlService.setBrightnessScaleFactor(0);
                        AudioManagerHelper.semSetAudioHDR(false);
                        mdnieScenarioControlService.setVideoAppLaunch(false);
                        mdnieScenarioControlService.setCameraAppLaunch(false);
                        mdnieScenarioControlService.setDouAppLaunch(false);
                        mdnieScenarioControlService.mGalleryAppState = false;
                        mdnieScenarioControlService.mDayOfUseSupportAppState = false;
                        mdnieScenarioControlService.mOverheatControlSupportAppState = false;
                        Slog.v("MdnieScenarioControlService", "setEmailMode from Email function");
                        break;
                    }
                    break;
                case 10:
                    mdnieScenarioControlService.getScenarioMode();
                    mdnieScenarioControlService.mAclOffEnabled = mdnieScenarioControlService.getting_autocurrentlimit_state();
                    mdnieScenarioControlService.mdnie_reset();
                    String str13 = mdnieScenarioControlService.SCENARIO_VALUE;
                    if (str13 != null && !str13.equals("9") && !mdnieScenarioControlService.SCENARIO_VALUE.contains("eBOOK") && !mdnieScenarioControlService.SCENARIO_VALUE.contains("EBOOK") && !mdnieScenarioControlService.isLockScreenOn) {
                        ProxyManager$$ExternalSyntheticOutline0.m("MdnieScenarioControlService", new StringBuilder("mAclOffEnabled : "), mdnieScenarioControlService.mAclOffEnabled);
                        if (mdnieScenarioControlService.mAclOffEnabled) {
                            mdnieScenarioControlService.setAclModeScenario(1, false);
                        }
                        mdnieScenarioControlService.setMdnieScenarioMode(9);
                        mdnieScenarioControlService.setBrightnessScaleFactor(0);
                        AudioManagerHelper.semSetAudioHDR(false);
                        mdnieScenarioControlService.setVideoAppLaunch(false);
                        mdnieScenarioControlService.setCameraAppLaunch(false);
                        mdnieScenarioControlService.setDouAppLaunch(false);
                        mdnieScenarioControlService.mGalleryAppState = false;
                        mdnieScenarioControlService.mDayOfUseSupportAppState = false;
                        mdnieScenarioControlService.mOverheatControlSupportAppState = false;
                        Slog.v("MdnieScenarioControlService", "setEbookMode from Ebook function");
                        break;
                    }
                    break;
                case 14:
                    mdnieScenarioControlService.getScenarioMode();
                    mdnieScenarioControlService.mAclOffEnabled = mdnieScenarioControlService.getting_autocurrentlimit_state();
                    mdnieScenarioControlService.mdnie_reset();
                    String str14 = mdnieScenarioControlService.SCENARIO_VALUE;
                    if (str14 != null && !str14.equals("1") && !mdnieScenarioControlService.SCENARIO_VALUE.contains("VIDEO") && !mdnieScenarioControlService.isLockScreenOn) {
                        StringBuilder sb5 = new StringBuilder("mAclOffEnabled : ");
                        sb5.append(mdnieScenarioControlService.mAclOffEnabled);
                        sb5.append(" mHighDynamicRangeEnabled : ");
                        ProxyManager$$ExternalSyntheticOutline0.m("MdnieScenarioControlService", sb5, mdnieScenarioControlService.mHighDynamicRangeEnabled);
                        if (mdnieScenarioControlService.mAclOffEnabled && !mdnieScenarioControlService.mHighDynamicRangeEnabled) {
                            mdnieScenarioControlService.setAclModeScenario(1, false);
                        }
                        mdnieScenarioControlService.setMdnieScenarioMode(1);
                        mdnieScenarioControlService.setBrightnessScaleFactor(0);
                        AudioManagerHelper.semSetAudioHDR(false);
                        mdnieScenarioControlService.setVideoAppLaunch(true);
                        mdnieScenarioControlService.setCameraAppLaunch(false);
                        mdnieScenarioControlService.setDouAppLaunch(false);
                        mdnieScenarioControlService.mGalleryAppState = false;
                        mdnieScenarioControlService.mDayOfUseSupportAppState = false;
                        mdnieScenarioControlService.mOverheatControlSupportAppState = false;
                        Slog.v("MdnieScenarioControlService", "setVideoMode from SVideoOption function");
                        break;
                    }
                    break;
                case 15:
                    mdnieScenarioControlService.mActivityManager = (ActivityManager) mdnieScenarioControlService.mContext.getSystemService("activity");
                    Slog.v("MdnieScenarioControlService", "mActivityManager : " + mdnieScenarioControlService.mActivityManager);
                    mdnieScenarioControlService.mMdnieManager = (SemMdnieManager) mdnieScenarioControlService.mContext.getSystemService("mDNIe");
                    Slog.v("MdnieScenarioControlService", "mMdnieManager : " + mdnieScenarioControlService.mMdnieManager);
                    mdnieScenarioControlService.mInputManager = (InputManager) mdnieScenarioControlService.mContext.getSystemService("input");
                    Slog.v("MdnieScenarioControlService", "mInputManager : " + mdnieScenarioControlService.mInputManager);
                    mdnieScenarioControlService.mSemDisplaySolutionManager = (SemDisplaySolutionManager) mdnieScenarioControlService.mContext.getSystemService("DisplaySolution");
                    Slog.v("MdnieScenarioControlService", "mSemDisplaySolutionManager : " + mdnieScenarioControlService.mSemDisplaySolutionManager);
                    mdnieScenarioControlService.mDisplayManager = (DisplayManager) mdnieScenarioControlService.mContext.getSystemService("display");
                    Slog.v("MdnieScenarioControlService", "mDisplayManager : " + mdnieScenarioControlService.mDisplayManager);
                    mdnieScenarioControlService.mDisplayAiqeManager = (DisplayAiqeManager) mdnieScenarioControlService.mContext.getSystemService("display_aiqe");
                    Slog.v("MdnieScenarioControlService", "mDisplayAiqeManager : " + mdnieScenarioControlService.mDisplayAiqeManager);
                    ActivityManager activityManager = mdnieScenarioControlService.mActivityManager;
                    MSCSControlHandler mSCSControlHandler2 = mdnieScenarioControlService.mHandler;
                    if (activityManager != null && mdnieScenarioControlService.mMdnieManager != null && mdnieScenarioControlService.mInputManager != null && mdnieScenarioControlService.mSemDisplaySolutionManager != null && mdnieScenarioControlService.mDisplayManager != null && mdnieScenarioControlService.mDisplayAiqeManager != null) {
                        try {
                            ActivityManagerNative.getDefault().registerProcessObserver(mdnieScenarioControlService.mProcessObserver);
                            if (mdnieScenarioControlService.mDesktopModeManager == null) {
                                mdnieScenarioControlService.mDesktopModeManager = (SemDesktopModeManager) mdnieScenarioControlService.mContext.getSystemService("desktopmode");
                                Slog.v("MdnieScenarioControlService", "mDesktopModeManager : " + mdnieScenarioControlService.mDesktopModeManager);
                            }
                            if (mdnieScenarioControlService.mDesktopModeManager != null) {
                                SemDesktopModeManager.registerListener(mdnieScenarioControlService.eventListener);
                            }
                            DisplayManager displayManager = mdnieScenarioControlService.mDisplayManager;
                            if (displayManager != null) {
                                displayManager.registerDisplayListener(mdnieScenarioControlService.mDisplayListener, mSCSControlHandler2, 8L);
                            }
                            mdnieScenarioControlService.setting_is_changed();
                        } catch (Exception unused2) {
                            Slog.d("MdnieScenarioControlService", "failed to registerProcessObserver");
                        }
                        mdnieScenarioControlService.mWorkingCondition = true;
                        Slog.v("MdnieScenarioControlService", "Success to register all of the services system.");
                        break;
                    } else {
                        long uptimeMillis6 = SystemClock.uptimeMillis();
                        mSCSControlHandler2.removeMessages(15);
                        mSCSControlHandler2.sendEmptyMessageAtTime(15, uptimeMillis6 + mdnieScenarioControlService.GET_SYSTEM_SERVICES_MILLIS);
                        Slog.v("MdnieScenarioControlService", "Failure to register all of the services system.");
                        break;
                    }
                    break;
                case 17:
                    mdnieScenarioControlService.browser_brightness_decrease_mode(true);
                    break;
                case 18:
                    mdnieScenarioControlService.browser_brightness_decrease_mode(false);
                    break;
                case 19:
                    mdnieScenarioControlService.getClass();
                    String str15 = mdnieScenarioControlService.ON_PIXEL_RATIO_PATH;
                    if (new File(str15).exists()) {
                        try {
                            String stringFromFile = MdnieScenarioControlService.getStringFromFile(str15);
                            if (stringFromFile != null) {
                                String[] split = stringFromFile.trim().split(",");
                                try {
                                    if (split.length == 12) {
                                        int i22 = 0;
                                        for (int i23 = 3; i23 < 6; i23++) {
                                            i22 += Integer.parseInt(split[i23]);
                                        }
                                        if (i22 > 0) {
                                            i3 = i22 / 3;
                                        }
                                    }
                                } catch (NumberFormatException e) {
                                    Slog.e("MdnieScenarioControlService", "NumberFormatException : " + e);
                                }
                            }
                        } catch (Exception e2) {
                            e2.printStackTrace();
                        }
                        i = i3;
                    } else {
                        i = -1;
                    }
                    Slog.d("MdnieScenarioControlService", "currentOpr : " + String.valueOf(i) + ", mQuickPanelState : " + String.valueOf(mdnieScenarioControlService.mQuickPanelState));
                    SemDisplaySolutionManager semDisplaySolutionManager6 = mdnieScenarioControlService.mSemDisplaySolutionManager;
                    if (semDisplaySolutionManager6 != null) {
                        semDisplaySolutionManager6.setOnPixelRatioValueForPMS(String.valueOf(i) + "," + String.valueOf(mdnieScenarioControlService.mQuickPanelState));
                        break;
                    }
                    break;
                case 20:
                    MdnieScenarioControlService.m1138$$Nest$manti_glare_state(mdnieScenarioControlService);
                    break;
                case 21:
                    try {
                        MdnieScenarioControlService.m1142$$Nest$mwriteVideoEnhancerListInDataBase2(mdnieScenarioControlService);
                        break;
                    } catch (Exception e3) {
                        e3.printStackTrace();
                        int i24 = 0;
                        while (true) {
                            String[] strArr18 = mdnieScenarioControlService.VIDEO_APP_LAUNCHER;
                            if (i24 < strArr18.length) {
                                mdnieScenarioControlService.insertDataUsage(strArr18[i24]);
                                i24++;
                            } else {
                                while (true) {
                                    String[] strArr19 = mdnieScenarioControlService.SVIDEO_APP_LAUNCHER;
                                    if (i3 >= strArr19.length) {
                                        return;
                                    }
                                    mdnieScenarioControlService.insertDataUsage(strArr19[i3]);
                                    i3++;
                                }
                            }
                        }
                    }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ScreenWatchingReceiver extends BroadcastReceiver {
        public ScreenWatchingReceiver() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Slog.i("MdnieScenarioControlService", "action  :  " + action);
            if ("android.intent.action.BOOT_COMPLETED".equals(action)) {
                Slog.i("MdnieScenarioControlService", "mActivityManager  :  " + MdnieScenarioControlService.this.mActivityManager + ", mMdnieManager  :  " + MdnieScenarioControlService.this.mMdnieManager + ", mSemDisplaySolutionManager  :  " + MdnieScenarioControlService.this.mSemDisplaySolutionManager + ", DesktopModeFeature  :  true");
                MdnieScenarioControlService mdnieScenarioControlService = MdnieScenarioControlService.this;
                if (mdnieScenarioControlService.mActivityManager == null || mdnieScenarioControlService.mMdnieManager == null || mdnieScenarioControlService.mSemDisplaySolutionManager == null) {
                    long uptimeMillis = SystemClock.uptimeMillis();
                    MdnieScenarioControlService.this.mHandler.removeMessages(15);
                    MdnieScenarioControlService.this.mHandler.sendEmptyMessageAtTime(15, uptimeMillis + r0.GET_SYSTEM_SERVICES_MILLIS);
                    Slog.v("MdnieScenarioControlService", "Failure to register all of the services system.");
                }
                MdnieScenarioControlService mdnieScenarioControlService2 = MdnieScenarioControlService.this;
                if (mdnieScenarioControlService2.mActivityManager != null) {
                    mdnieScenarioControlService2.setting_is_changed();
                }
                if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_LCD_SUPPORT_WIDE_COLOR_GAMUT", false)) {
                    MdnieScenarioControlService.m1141$$Nest$mset_wcg_property(MdnieScenarioControlService.this);
                    return;
                }
                return;
            }
            if ("android.intent.action.SCREEN_ON".equals(action)) {
                MdnieScenarioControlService mdnieScenarioControlService3 = MdnieScenarioControlService.this;
                if (mdnieScenarioControlService3.mKeyGuardManager == null) {
                    mdnieScenarioControlService3.mKeyGuardManager = (KeyguardManager) mdnieScenarioControlService3.mContext.getSystemService("keyguard");
                    Slog.v("MdnieScenarioControlService", "mKeyGuardManager : " + MdnieScenarioControlService.this.mKeyGuardManager);
                }
                KeyguardManager keyguardManager = MdnieScenarioControlService.this.mKeyGuardManager;
                if (keyguardManager != null && keyguardManager.isKeyguardLocked()) {
                    MdnieScenarioControlService.this.isLockScreenOn = true;
                }
                Slog.d("MdnieScenarioControlService", "isLockScreenOn : " + MdnieScenarioControlService.this.isLockScreenOn);
                SystemClock.uptimeMillis();
                MdnieScenarioControlService mdnieScenarioControlService4 = MdnieScenarioControlService.this;
                mdnieScenarioControlService4.mScreenStateOn = true;
                if (mdnieScenarioControlService4.isLockScreenOn) {
                    mdnieScenarioControlService4.mBrowserScenarioEnabled = false;
                    mdnieScenarioControlService4.mScreenStateOn = true;
                    if (mdnieScenarioControlService4.mSettingCondition) {
                        boolean z = mdnieScenarioControlService4.mWorkingCondition;
                    }
                    String str = mdnieScenarioControlService4.FrontPackageName;
                    if (str != null) {
                        mdnieScenarioControlService4.setUIMode(str);
                        return;
                    }
                    return;
                }
                return;
            }
            if ("android.intent.action.SCREEN_OFF".equals(action)) {
                MdnieScenarioControlService mdnieScenarioControlService5 = MdnieScenarioControlService.this;
                mdnieScenarioControlService5.mScreenStateOn = false;
                boolean z2 = mdnieScenarioControlService5.mBrowserBrightnessDecreaseEnabled;
                MSCSControlHandler mSCSControlHandler = mdnieScenarioControlService5.mHandler;
                if (z2) {
                    mSCSControlHandler.removeMessages(17);
                    mSCSControlHandler.removeMessages(18);
                    mSCSControlHandler.sendEmptyMessage(18);
                    return;
                } else {
                    if (z2) {
                        return;
                    }
                    mSCSControlHandler.removeMessages(17);
                    return;
                }
            }
            if ("android.intent.action.USER_PRESENT".equals(action)) {
                MdnieScenarioControlService mdnieScenarioControlService6 = MdnieScenarioControlService.this;
                mdnieScenarioControlService6.isLockScreenOn = false;
                mdnieScenarioControlService6.mScreenStateOn = true;
                try {
                    if (mdnieScenarioControlService6.mActivityManager != null) {
                        mdnieScenarioControlService6.mProcessObserver.onForegroundActivitiesChanged(-1, 0, false);
                        return;
                    }
                    return;
                } catch (RemoteException unused) {
                    Slog.d("MdnieScenarioControlService", "failed to onForegroundActivitiesChanged");
                    return;
                }
            }
            if ("ACTION_MOVIE_PLAYER_STATE_IN".equals(action)) {
                MdnieScenarioControlService mdnieScenarioControlService7 = MdnieScenarioControlService.this;
                mdnieScenarioControlService7.mVideoModeCheck = true;
                mdnieScenarioControlService7.mGalleryAppState = false;
                long uptimeMillis2 = SystemClock.uptimeMillis();
                MdnieScenarioControlService.this.mHandler.removeMessages(7);
                MdnieScenarioControlService.this.mHandler.sendEmptyMessageAtTime(7, uptimeMillis2 + r7.ACTION_MOVIE_PLAYER_STATE_IN_DEBOUNCE_MILLIS);
                return;
            }
            if ("ACTION_MOVIE_PLAYER_STATE_OUT".equals(action)) {
                MdnieScenarioControlService mdnieScenarioControlService8 = MdnieScenarioControlService.this;
                mdnieScenarioControlService8.mVideoModeCheck = false;
                mdnieScenarioControlService8.mVideoEnd = true;
                long uptimeMillis3 = SystemClock.uptimeMillis();
                MdnieScenarioControlService.this.mHandler.removeMessages(8);
                MdnieScenarioControlService.this.mHandler.sendEmptyMessageAtTime(8, uptimeMillis3 + r7.ACTION_MOVIE_PLAYER_STATE_OUT_DEBOUNCE_MILLIS);
                return;
            }
            if ("com.samsung.server.PowerManagerService.action.USER_ACTIVITY".equals(action)) {
                MdnieScenarioControlService.this.mUserActivityStatus = intent.getIntExtra(Constants.JSON_CLIENT_DATA_STATUS, 1);
                return;
            }
            if ("com.samsung.systemui.statusbar.EXPANDED".equals(action)) {
                MdnieScenarioControlService.this.mQuickPanelState = 1;
                return;
            }
            if ("com.samsung.systemui.statusbar.COLLAPSED".equals(action)) {
                MdnieScenarioControlService mdnieScenarioControlService9 = MdnieScenarioControlService.this;
                mdnieScenarioControlService9.mQuickPanelState = 0;
                if (mdnieScenarioControlService9.mAutoBrightnessMode) {
                    mdnieScenarioControlService9.mHandler.removeMessages(19);
                    MdnieScenarioControlService.this.mHandler.sendEmptyMessage(19);
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SettingsObserver extends ContentObserver {
        public final Uri BLUE_LIGHT_FILTER_ADAPTIVE_MODE_URI;
        public final Uri BLUE_LIGHT_FILTER_ANTI_GLARE_URI;
        public final Uri BLUE_LIGHT_FILTER_URI;
        public final Uri BRIGHTNESS_ADJ_URI;
        public final Uri BRIGHTNESS_MODE_URI;
        public final Uri BRIGHTNESS_URI;
        public final Uri REDUCE_BRIGHT_COLORS_ACTIVATED_URI;
        public final Uri REDUCE_BRIGHT_COLORS_LEVEL_URI;
        public final Uri SCREEN_MODE_SETTING_URI;
        public final Uri VIVIDNESS_INTENSITY_URI;
        public final ContentResolver resolver;

        public SettingsObserver(Handler handler) {
            super(handler);
            this.resolver = MdnieScenarioControlService.this.mContext.getContentResolver();
            this.BRIGHTNESS_URI = Settings.System.getUriFor("screen_brightness");
            this.BRIGHTNESS_MODE_URI = Settings.System.getUriFor("screen_brightness_mode");
            this.BRIGHTNESS_ADJ_URI = Settings.System.getUriFor("screen_auto_brightness_adj");
            this.BLUE_LIGHT_FILTER_URI = Settings.System.getUriFor("blue_light_filter");
            this.BLUE_LIGHT_FILTER_ADAPTIVE_MODE_URI = Settings.System.getUriFor("blue_light_filter_adaptive_mode");
            this.BLUE_LIGHT_FILTER_ANTI_GLARE_URI = Settings.System.getUriFor("blue_light_filter_anti_glare");
            this.REDUCE_BRIGHT_COLORS_ACTIVATED_URI = Settings.Secure.getUriFor("reduce_bright_colors_activated");
            this.REDUCE_BRIGHT_COLORS_LEVEL_URI = Settings.Secure.getUriFor("reduce_bright_colors_level");
            this.SCREEN_MODE_SETTING_URI = Settings.System.getUriFor("screen_mode_setting");
            this.VIVIDNESS_INTENSITY_URI = Settings.System.getUriFor("vividness_intensity");
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            SemMdnieManager semMdnieManager;
            MdnieScenarioControlService.this.setting_is_changed();
            if (MdnieScenarioControlService.this.AUTO_CURRENT_LIMIT_VERSION == 4) {
                if (this.BRIGHTNESS_MODE_URI.equals(uri) && Settings.System.getIntForUser(this.resolver, "screen_brightness_mode", 0, -2) == 0) {
                    MdnieScenarioControlService mdnieScenarioControlService = MdnieScenarioControlService.this;
                    mdnieScenarioControlService.mPrevAclValue = 3;
                    mdnieScenarioControlService.mAntiGlareEnable = false;
                }
                if (this.BRIGHTNESS_URI.equals(uri) && Settings.System.getIntForUser(this.resolver, "screen_brightness_mode", 0, -2) == 1) {
                    MdnieScenarioControlService mdnieScenarioControlService2 = MdnieScenarioControlService.this;
                    if (!mdnieScenarioControlService2.mHighBrightnessModeEnabled) {
                        MdnieScenarioControlService.m1138$$Nest$manti_glare_state(mdnieScenarioControlService2);
                        MdnieScenarioControlService.m1139$$Nest$msetAclModeSettings(MdnieScenarioControlService.this);
                    }
                }
                if (this.BLUE_LIGHT_FILTER_URI.equals(uri) || this.BLUE_LIGHT_FILTER_ADAPTIVE_MODE_URI.equals(uri) || this.BLUE_LIGHT_FILTER_ANTI_GLARE_URI.equals(uri)) {
                    if (Settings.System.getIntForUser(this.resolver, "screen_brightness_mode", 0, -2) == 1) {
                        MdnieScenarioControlService mdnieScenarioControlService3 = MdnieScenarioControlService.this;
                        if (!mdnieScenarioControlService3.mHighBrightnessModeEnabled) {
                            MdnieScenarioControlService.m1138$$Nest$manti_glare_state(mdnieScenarioControlService3);
                            MdnieScenarioControlService.m1139$$Nest$msetAclModeSettings(MdnieScenarioControlService.this);
                        }
                    }
                    MdnieScenarioControlService.this.mAntiGlareEnable = false;
                }
            }
            if (this.BRIGHTNESS_ADJ_URI.equals(uri)) {
                MdnieScenarioControlService mdnieScenarioControlService4 = MdnieScenarioControlService.this;
                if (mdnieScenarioControlService4.mAutoBrightnessMode) {
                    mdnieScenarioControlService4.mHandler.removeMessages(19);
                    MdnieScenarioControlService.this.mHandler.sendEmptyMessage(19);
                }
            }
            if ((this.REDUCE_BRIGHT_COLORS_ACTIVATED_URI.equals(uri) || this.REDUCE_BRIGHT_COLORS_LEVEL_URI.equals(uri)) && SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_VIVIDPLUS", 0) == 1) {
                StringBuilder sb = new StringBuilder("mReduceBrightColorsActivatedEnabled(");
                sb.append(MdnieScenarioControlService.this.mReduceBrightColorsActivatedEnabled);
                sb.append(") - level : ");
                DeviceIdleController$$ExternalSyntheticOutline0.m(sb, MdnieScenarioControlService.this.mReduceBrightColorsLevel, "MdnieScenarioControlService");
                MdnieScenarioControlService mdnieScenarioControlService5 = MdnieScenarioControlService.this;
                boolean z2 = mdnieScenarioControlService5.mReduceBrightColorsActivatedEnabled;
                if (z2) {
                    SemMdnieManager semMdnieManager2 = mdnieScenarioControlService5.mMdnieManager;
                    if (semMdnieManager2 != null) {
                        semMdnieManager2.setExtraDimMode(mdnieScenarioControlService5.mReduceBrightColorsLevel);
                    }
                } else if (!z2 && (semMdnieManager = mdnieScenarioControlService5.mMdnieManager) != null) {
                    semMdnieManager.setExtraDimMode(0);
                }
            }
            if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_LCD_SUPPORT_WIDE_COLOR_GAMUT", false)) {
                if (this.VIVIDNESS_INTENSITY_URI.equals(uri) || this.SCREEN_MODE_SETTING_URI.equals(uri)) {
                    MdnieScenarioControlService.m1141$$Nest$mset_wcg_property(MdnieScenarioControlService.this);
                }
            }
        }
    }

    /* renamed from: -$$Nest$manti_glare_state, reason: not valid java name */
    public static void m1138$$Nest$manti_glare_state(MdnieScenarioControlService mdnieScenarioControlService) {
        mdnieScenarioControlService.getClass();
        try {
            mdnieScenarioControlService.LUX_VALUE = getStringFromFile(mdnieScenarioControlService.READING_LUX_PATH);
            String stringFromFile = getStringFromFile(mdnieScenarioControlService.READING_LUX_SUB_PATH);
            mdnieScenarioControlService.SUB_LUX_VALUE = stringFromFile;
            boolean z = mdnieScenarioControlService.mIsFolded;
            if (z) {
                if (z && stringFromFile != null) {
                    Slog.d("MdnieScenarioControlService", "anti_glare_state SUB_LUX_VALUE : " + mdnieScenarioControlService.SUB_LUX_VALUE);
                    if (Integer.parseInt(mdnieScenarioControlService.SUB_LUX_VALUE) >= 0 && 10 > Integer.parseInt(mdnieScenarioControlService.SUB_LUX_VALUE)) {
                        mdnieScenarioControlService.mAntiGlareEnable = false;
                    } else if (10 <= Integer.parseInt(mdnieScenarioControlService.SUB_LUX_VALUE)) {
                        mdnieScenarioControlService.mAntiGlareEnable = false;
                    }
                }
            } else if (mdnieScenarioControlService.LUX_VALUE != null) {
                Slog.d("MdnieScenarioControlService", "anti_glare_state LUX_VALUE : " + mdnieScenarioControlService.LUX_VALUE);
                if (Integer.parseInt(mdnieScenarioControlService.LUX_VALUE) >= 0 && 10 > Integer.parseInt(mdnieScenarioControlService.LUX_VALUE)) {
                    mdnieScenarioControlService.mAntiGlareEnable = false;
                } else if (10 <= Integer.parseInt(mdnieScenarioControlService.LUX_VALUE)) {
                    mdnieScenarioControlService.mAntiGlareEnable = false;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* renamed from: -$$Nest$msetAclModeSettings, reason: not valid java name */
    public static void m1139$$Nest$msetAclModeSettings(MdnieScenarioControlService mdnieScenarioControlService) {
        if (mdnieScenarioControlService.mSemDisplaySolutionManager == null || !mdnieScenarioControlService.mAutoBrightnessMode) {
            return;
        }
        if (mdnieScenarioControlService.mAntiGlareEnable) {
            int i = mdnieScenarioControlService.mPrevAclValue;
            if (i == 0) {
                mdnieScenarioControlService.mCurAclValue = 2;
            } else if (i == 1 || i == 3) {
                mdnieScenarioControlService.mCurAclValue = 5;
            }
        } else {
            int i2 = mdnieScenarioControlService.mPrevAclValue;
            if (i2 == 2) {
                mdnieScenarioControlService.mCurAclValue = 0;
            } else if (i2 == 5 || i2 == 4) {
                mdnieScenarioControlService.mCurAclValue = 1;
            }
        }
        Slog.v("MdnieScenarioControlService", "setAclModeSettings() ACL mPrevAclValue (" + mdnieScenarioControlService.mPrevAclValue + ") - mCurrentValue (" + mdnieScenarioControlService.mCurAclValue + ")");
        mdnieScenarioControlService.mSemDisplaySolutionManager.onAutoCurrentLimitStateChangedInt(mdnieScenarioControlService.mCurAclValue);
        mdnieScenarioControlService.mPrevAclValue = mdnieScenarioControlService.mCurAclValue;
    }

    /* renamed from: -$$Nest$msetSVideoMode, reason: not valid java name */
    public static void m1140$$Nest$msetSVideoMode(MdnieScenarioControlService mdnieScenarioControlService, boolean z, String str) {
        String str2;
        String str3;
        mdnieScenarioControlService.getScenarioMode();
        if (!z) {
            mdnieScenarioControlService.mdnie_reset();
            if (mdnieScenarioControlService.GALLERY_APP_PACKAGENAME.equalsIgnoreCase(mdnieScenarioControlService.FrontPackageName) || (str2 = mdnieScenarioControlService.SCENARIO_VALUE) == null || str2.equals("0") || mdnieScenarioControlService.SCENARIO_VALUE.contains("UI") || mdnieScenarioControlService.isLockScreenOn) {
                return;
            }
            StringBuilder sb = new StringBuilder("mAclOffEnabled : ");
            sb.append(mdnieScenarioControlService.mAclOffEnabled);
            sb.append(" mHighDynamicRangeEnabled : ");
            ProxyManager$$ExternalSyntheticOutline0.m("MdnieScenarioControlService", sb, mdnieScenarioControlService.mHighDynamicRangeEnabled);
            if (mdnieScenarioControlService.mAclOffEnabled && !mdnieScenarioControlService.mHighDynamicRangeEnabled) {
                mdnieScenarioControlService.setAclModeScenario(1, false);
            }
            mdnieScenarioControlService.setMdnieScenarioMode(0);
            mdnieScenarioControlService.setBrightnessScaleFactor(0);
            AudioManagerHelper.semSetAudioHDR(false);
            mdnieScenarioControlService.setVideoAppLaunch(false);
            mdnieScenarioControlService.setCameraAppLaunch(false);
            mdnieScenarioControlService.setDouAppLaunch(false);
            mdnieScenarioControlService.mGalleryAppState = false;
            mdnieScenarioControlService.mDayOfUseSupportAppState = false;
            mdnieScenarioControlService.mOverheatControlSupportAppState = false;
            Slog.v("MdnieScenarioControlService", "setUIMode from SVideo function");
            return;
        }
        boolean z2 = mdnieScenarioControlService.getting_setting_value();
        mdnieScenarioControlService.getting_knox_mode_enabled();
        mdnieScenarioControlService.mAclOffEnabled = mdnieScenarioControlService.getting_autocurrentlimit_state();
        mdnieScenarioControlService.mdnie_reset();
        StringBuilder sb2 = new StringBuilder("hdr_effect_enable : ");
        sb2.append(z2);
        sb2.append(" , app_setting_value : ");
        sb2.append(mdnieScenarioControlService.getAppSettingState(str) == 1);
        sb2.append(" , mVideoModeCheck : ");
        ProxyManager$$ExternalSyntheticOutline0.m("MdnieScenarioControlService", sb2, mdnieScenarioControlService.mVideoModeCheck);
        if (!z2 || mdnieScenarioControlService.getAppSettingState(str) != 1) {
            String str4 = mdnieScenarioControlService.SCENARIO_VALUE;
            if (str4 == null || str4.equals("1") || mdnieScenarioControlService.SCENARIO_VALUE.contains("VIDEO") || mdnieScenarioControlService.isLockScreenOn) {
                return;
            }
            StringBuilder sb3 = new StringBuilder("mAclOffEnabled : ");
            sb3.append(mdnieScenarioControlService.mAclOffEnabled);
            sb3.append(" mHighDynamicRangeEnabled : ");
            ProxyManager$$ExternalSyntheticOutline0.m("MdnieScenarioControlService", sb3, mdnieScenarioControlService.mHighDynamicRangeEnabled);
            if (mdnieScenarioControlService.mAclOffEnabled && !mdnieScenarioControlService.mHighDynamicRangeEnabled) {
                mdnieScenarioControlService.setAclModeScenario(1, false);
            }
            mdnieScenarioControlService.setMdnieScenarioMode(1);
            mdnieScenarioControlService.setBrightnessScaleFactor(0);
            AudioManagerHelper.semSetAudioHDR(false);
            mdnieScenarioControlService.setVideoAppLaunch(true);
            mdnieScenarioControlService.setCameraAppLaunch(false);
            mdnieScenarioControlService.setDouAppLaunch(false);
            mdnieScenarioControlService.mGalleryAppState = false;
            mdnieScenarioControlService.mDayOfUseSupportAppState = false;
            mdnieScenarioControlService.mOverheatControlSupportAppState = false;
            Slog.v("MdnieScenarioControlService", "setVideoMode from SVideo function");
            return;
        }
        boolean z3 = mdnieScenarioControlService.mMultiWindowOn;
        if (z3 || !mdnieScenarioControlService.mVideoModeCheck) {
            if ((!z3 && mdnieScenarioControlService.mVideoModeCheck) || (str3 = mdnieScenarioControlService.SCENARIO_VALUE) == null || str3.equals("1") || mdnieScenarioControlService.SCENARIO_VALUE.contains("VIDEO") || mdnieScenarioControlService.isLockScreenOn) {
                return;
            }
            StringBuilder sb4 = new StringBuilder("mAclOffEnabled : ");
            sb4.append(mdnieScenarioControlService.mAclOffEnabled);
            sb4.append(" mHighDynamicRangeEnabled : ");
            ProxyManager$$ExternalSyntheticOutline0.m("MdnieScenarioControlService", sb4, mdnieScenarioControlService.mHighDynamicRangeEnabled);
            if (mdnieScenarioControlService.mAclOffEnabled && !mdnieScenarioControlService.mHighDynamicRangeEnabled) {
                mdnieScenarioControlService.setAclModeScenario(1, false);
            }
            mdnieScenarioControlService.setMdnieScenarioMode(1);
            mdnieScenarioControlService.setBrightnessScaleFactor(0);
            AudioManagerHelper.semSetAudioHDR(false);
            mdnieScenarioControlService.setVideoAppLaunch(true);
            mdnieScenarioControlService.setCameraAppLaunch(false);
            mdnieScenarioControlService.setDouAppLaunch(false);
            mdnieScenarioControlService.mGalleryAppState = false;
            mdnieScenarioControlService.mDayOfUseSupportAppState = false;
            mdnieScenarioControlService.mOverheatControlSupportAppState = false;
            Slog.v("MdnieScenarioControlService", "setVideoMode from SVideo function");
            return;
        }
        String str5 = mdnieScenarioControlService.SCENARIO_VALUE;
        if (str5 == null || str5.equals("14") || mdnieScenarioControlService.SCENARIO_VALUE.contains("VIDEO_ENHANCER") || mdnieScenarioControlService.SCENARIO_VALUE.contains("VIDEO_BRIGHTNESS") || mdnieScenarioControlService.isLockScreenOn) {
            return;
        }
        StringBuilder sb5 = new StringBuilder("mAclOffEnabled : ");
        sb5.append(mdnieScenarioControlService.mAclOffEnabled);
        sb5.append(" mHighDynamicRangeEnabled : ");
        ProxyManager$$ExternalSyntheticOutline0.m("MdnieScenarioControlService", sb5, mdnieScenarioControlService.mHighDynamicRangeEnabled);
        if (mdnieScenarioControlService.mAclOffEnabled && !mdnieScenarioControlService.mHighDynamicRangeEnabled) {
            mdnieScenarioControlService.setAclModeScenario(1, false);
        }
        mdnieScenarioControlService.setMdnieScenarioMode(14);
        ProxyManager$$ExternalSyntheticOutline0.m("MdnieScenarioControlService", new StringBuilder("mDexModeState : "), mdnieScenarioControlService.mDexModeState);
        if (mdnieScenarioControlService.mDexModeState) {
            mdnieScenarioControlService.setBrightnessScaleFactor(0);
        } else {
            mdnieScenarioControlService.setBrightnessScaleFactor(1);
        }
        AudioManagerHelper.semSetAudioHDR(true);
        mdnieScenarioControlService.setVideoAppLaunch(true);
        mdnieScenarioControlService.setCameraAppLaunch(false);
        mdnieScenarioControlService.setDouAppLaunch(false);
        mdnieScenarioControlService.mGalleryAppState = false;
        mdnieScenarioControlService.mDayOfUseSupportAppState = false;
        mdnieScenarioControlService.mOverheatControlSupportAppState = false;
        Slog.v("MdnieScenarioControlService", "setVideoMode from Video function(VIDEO_ENHANCER)");
    }

    /* renamed from: -$$Nest$mset_wcg_property, reason: not valid java name */
    public static void m1141$$Nest$mset_wcg_property(MdnieScenarioControlService mdnieScenarioControlService) {
        if (mdnieScenarioControlService.mNaturalGammaScreenModeSupported) {
            return;
        }
        int i = mdnieScenarioControlService.mScreenModeSetting;
        if (i == 4) {
            if (SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_VIVIDPLUS", 0) == 0) {
                mdnieScenarioControlService.wcg_property_changed(1, 1);
            } else if (SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_VIVIDPLUS", 0) == 1) {
                mdnieScenarioControlService.wcg_property_changed(0, 0);
            } else if (SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_VIVIDPLUS", 0) >= 2) {
                mdnieScenarioControlService.wcg_property_changed(0, mdnieScenarioControlService.mVividnessIndex + 256);
            }
        } else if (i == 3) {
            mdnieScenarioControlService.wcg_property_changed(0, 0);
        }
        if (FactoryTest.isFactoryBinary()) {
            mdnieScenarioControlService.wcg_property_changed(1, 1);
        }
    }

    /* renamed from: -$$Nest$mwriteVideoEnhancerListInDataBase2, reason: not valid java name */
    public static void m1142$$Nest$mwriteVideoEnhancerListInDataBase2(MdnieScenarioControlService mdnieScenarioControlService) {
        mdnieScenarioControlService.getClass();
        try {
            ArrayList arrayList = new ArrayList();
            Collections.addAll(arrayList, mdnieScenarioControlService.VIDEO_APP_LAUNCHER);
            Collections.addAll(arrayList, mdnieScenarioControlService.SVIDEO_APP_LAUNCHER);
            HashMap appListRegistState = mdnieScenarioControlService.getAppListRegistState(arrayList);
            ArrayList arrayList2 = new ArrayList();
            for (int i = 0; i < arrayList.size(); i++) {
                if (appListRegistState.get(arrayList.get(i)) == null) {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put("packagename", (String) arrayList.get(i));
                    contentValues.put("settingstate", (Integer) 1);
                    arrayList2.add(contentValues);
                }
            }
            DisplaySolutionDataBase displaySolutionDataBase = mdnieScenarioControlService.mDBHelper;
            SQLiteDatabase sQLiteDatabase = mdnieScenarioControlService.mDisplaySolutionDataBase;
            displaySolutionDataBase.getClass();
            if (sQLiteDatabase == null) {
                try {
                    sQLiteDatabase = displaySolutionDataBase.getWritableDatabase();
                } catch (Exception e) {
                    e.printStackTrace();
                    return;
                }
            }
            sQLiteDatabase.beginTransaction();
            for (int i2 = 0; i2 < arrayList2.size(); i2++) {
                sQLiteDatabase.insert("MSCS_APP_LIST", "", (ContentValues) arrayList2.get(i2));
            }
            sQLiteDatabase.setTransactionSuccessful();
            sQLiteDatabase.endTransaction();
        } catch (Exception e2) {
            e2.printStackTrace();
        }
    }

    /* JADX WARN: Type inference failed for: r1v9, types: [com.samsung.android.displaysolution.MdnieScenarioControlService$3] */
    /* JADX WARN: Type inference failed for: r6v18, types: [com.samsung.android.displaysolution.MdnieScenarioControlService$2] */
    /* JADX WARN: Type inference failed for: r6v19, types: [com.samsung.android.displaysolution.MdnieScenarioControlService$4] */
    public MdnieScenarioControlService(Context context) {
        "eng".equals(Build.TYPE);
        this.mLock = new Object();
        this.mAppLaunchStateInDatabase = 0;
        this.DOU_BRIGHTNESS_STANDARD_VALUE = 0;
        this.AUTO_CURRENT_LIMIT_VERSION = 0;
        this.FOREGROUND_RESCAN_DEBOUNCE_MILLIS = 0;
        this.ACTION_DETAIL_VIEW_STATE_IN_DEBOUNCE_MILLIS = 0;
        this.ACTION_MOVIE_PLAYER_STATE_IN_DEBOUNCE_MILLIS = 0;
        this.ACTION_MOVIE_PLAYER_STATE_OUT_DEBOUNCE_MILLIS = 0;
        this.ACTION_VIDEO_APP_STATE_IN_DEBOUNCE_MILLIS = 0;
        this.ACTION_SET_UI_MODE_DEBOUNCE_MILLIS = 0;
        this.ACTION_BROWSER_BRIGHTNESS_DECREASE_FIRST_MILLIS = 1200000;
        this.ACTION_BROWSER_BRIGHTNESS_DECREASE_MILLIS = 120000;
        this.IS_CAMERA_APP_DEBOUNCE_MILLIS = 0;
        this.GET_SYSTEM_SERVICES_MILLIS = 8000;
        this.BRIGHTNESS_DECREASE_STEP = 1;
        this.mBrowserBrightnessDefault = 0;
        this.mUserActivityStatus = 1;
        this.mQuickPanelState = 0;
        this.mPrevAclValue = 1;
        this.mCurAclValue = 1;
        this.mReduceBrightColorsLevel = 50;
        this.mScreenModeSetting = 4;
        this.mVividnessIndex = 0;
        this.mPrevPropertyValue = -1;
        this.mPrevRenderIntentValue = -1;
        this.mLastUserInputDuration = 0L;
        this.READING_SCENARIO_PATH = "/sys/class/mdnie/mdnie/scenario";
        this.READING_LUX_PATH = "/sys/class/lcd/panel/lux";
        this.READING_LUX_SUB_PATH = "/sys/class/lcd/panel1/lux";
        this.ADAPTIVE_CONTROL_PATH = "/sys/class/lcd/panel/adaptive_control";
        this.ON_PIXEL_RATIO_PATH = "/sys/class/sensors/light_sensor/copr_roix";
        this.GALLERY_APP_PACKAGENAME = "com.sec.android.gallery3d";
        this.SCENARIO_VALUE = null;
        this.LUX_VALUE = null;
        this.SUB_LUX_VALUE = null;
        this.FrontPackageName = null;
        this.mPrevmDNIeMode = null;
        this.ANDROID_APP_LAUNCHER = new String[0];
        this.SBROWSER_APP_LAUNCHER = new String[0];
        this.CHROMEBROWSER_APP_LAUNCHER = new String[0];
        this.GALLERY_APP_LAUNCHER = new String[0];
        this.CAMERA_APP_LAUNCHER = new String[0];
        this.SVIDEO_APP_LAUNCHER = new String[0];
        this.SVIDEO_APP_OPTION_LAUNCHER = new String[0];
        this.VIDEO_APP_LAUNCHER = new String[0];
        this.EMAIL_APP_LAUNCHER = new String[0];
        this.EBOOK_APP_LAUNCHER = new String[0];
        this.ACL_CONTROL_GALLERY_APP_LIST = new String[0];
        this.DAY_OF_USE_SUPPORT_APP_LIST = new String[0];
        this.OVERHEAT_CONTROL_SUPPORT_APP_LIST = new String[0];
        this.EYE_COMFORT_BLACKLIST_APP_LIST = new String[0];
        this.EYE_COMFORT_1_05_APP_LIST = new String[0];
        this.EYE_COMFORT_1_10_APP_LIST = new String[0];
        this.EYE_COMFORT_1_15_APP_LIST = new String[0];
        this.mDisplaySolutionDataBase = null;
        this.mDBHelper = null;
        this.mSupportAPmDNIe = false;
        this.mWorkingCondition = false;
        this.mUseMdnieScenarioControlConfig = false;
        this.mUseEyeComfortSolutionServiceConfig = false;
        this.mScreenStateOn = false;
        this.isLockScreenOn = false;
        this.mMultiWindowOn = false;
        this.mHdrEffectEnabled = false;
        this.mHighBrightnessModeEnabled = false;
        this.mScreenCurtainEnabled = false;
        this.mColorBlindEnabled = false;
        this.mUIScenarioEnabled = false;
        this.mBrowserScenarioEnabled = false;
        this.mGalleryScenarioEnabled = false;
        this.mCameraScenarioEnabled = false;
        this.mSVideoScenarioEnabled = false;
        this.mSVideoOptionScenarioEnabled = false;
        this.mVideoScenarioEnabled = false;
        this.mEmailScenarioEnabled = false;
        this.mEbookScenarioEnabled = false;
        this.mGalleryAppState = false;
        this.mDayOfUseSupportAppState = false;
        this.mOverheatControlSupportAppState = false;
        this.mVideoEnd = false;
        this.mSettingCondition = false;
        this.mDexModeState = false;
        this.mAclState = false;
        this.mVideoModeCheck = false;
        this.mHighDynamicRangeEnabled = false;
        this.mBrowserBrightnessDecreaseEnabled = false;
        this.mBrowserAppLauncher = false;
        this.mAclOffEnabled = false;
        this.mEyeComfortScaleAppEnabled = false;
        this.mAutoBrightnessMode = false;
        this.mScreenOffTomeoutAbnormal = false;
        this.mMediaResourceUsed = false;
        this.mUseScaleFactorState = false;
        this.mAclDimmingFunction = false;
        this.mIsFolded = false;
        this.mAntiGlareEnable = false;
        this.mReduceBrightColorsActivatedEnabled = false;
        this.mNaturalGammaScreenModeSupported = false;
        this.mProcessObserver = new AnonymousClass1();
        this.eventListener = new SemDesktopModeManager.EventListener() { // from class: com.samsung.android.displaysolution.MdnieScenarioControlService.2
            public final void onDesktopDockConnectionChanged(boolean z) {
            }

            public final void onDesktopModeChanged(boolean z) {
                if (z) {
                    Slog.d("MdnieScenarioControlService", "Dex Mode Connected");
                    MdnieScenarioControlService.this.mDexModeState = true;
                } else {
                    Slog.d("MdnieScenarioControlService", "Dex Mode Disconnected");
                    MdnieScenarioControlService.this.mDexModeState = false;
                }
            }
        };
        this.mDisplayListener = new DisplayManager.DisplayListener() { // from class: com.samsung.android.displaysolution.MdnieScenarioControlService.4
            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayAdded(int i) {
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayChanged(int i) {
                BrightnessInfo brightnessInfo;
                if (i != 0 || (brightnessInfo = MdnieScenarioControlService.this.mDisplayManager.getDisplay(i).getBrightnessInfo()) == null) {
                    return;
                }
                MdnieScenarioControlService mdnieScenarioControlService = MdnieScenarioControlService.this;
                boolean z = brightnessInfo.isAnimating;
                mdnieScenarioControlService.getClass();
                if (!z && mdnieScenarioControlService.mPrevAclValue == 5 && mdnieScenarioControlService.mAutoBrightnessMode) {
                    Slog.v("MdnieScenarioControlService", "BrightnessAnimating() ACL mPrevAclValue (" + MdnieScenarioControlService.this.mPrevAclValue + ") - mCurrentValue (4)");
                    SemDisplaySolutionManager semDisplaySolutionManager = MdnieScenarioControlService.this.mSemDisplaySolutionManager;
                    if (semDisplaySolutionManager != null) {
                        semDisplaySolutionManager.onAutoCurrentLimitStateChangedInt(4);
                    }
                    MdnieScenarioControlService.this.mPrevAclValue = 4;
                }
            }

            @Override // android.hardware.display.DisplayManager.DisplayListener
            public final void onDisplayRemoved(int i) {
            }
        };
        this.mContext = context;
        DisplaySolutionDataBase displaySolutionDataBase = new DisplaySolutionDataBase(context, "displaysolution_setting.db", null, 1);
        this.mDBHelper = displaySolutionDataBase;
        this.mDisplaySolutionDataBase = displaySolutionDataBase.getWritableDatabase();
        MSCSControlHandler mSCSControlHandler = new MSCSControlHandler(KnoxCaptureInputFilter$$ExternalSyntheticOutline0.m("MdnieScenarioControlServiceThread").getLooper());
        this.mHandler = mSCSControlHandler;
        this.mUseMdnieScenarioControlConfig = context.getResources().getBoolean(R.bool.config_noHomeScreen);
        if (SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_LCD_SUPPORT_BLUE_FILTER_ADAPTIVE_MODE", 0) > 0) {
            this.mUseEyeComfortSolutionServiceConfig = true;
        }
        if (!"DDI".equals(SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_LCD_CONFIG_HW_MDNIE"))) {
            this.mSupportAPmDNIe = true;
        }
        this.ANDROID_APP_LAUNCHER = context.getResources().getStringArray(R.array.config_deviceTabletopRotations);
        this.SBROWSER_APP_LAUNCHER = context.getResources().getStringArray(R.array.config_halfFoldedDeviceStates);
        this.CHROMEBROWSER_APP_LAUNCHER = context.getResources().getStringArray(R.array.config_disabledUntilUsedPreinstalledImes);
        this.GALLERY_APP_LAUNCHER = context.getResources().getStringArray(R.array.config_enabledCredentialProviderService);
        this.CAMERA_APP_LAUNCHER = context.getResources().getStringArray(R.array.config_disabledDreamComponents);
        this.SVIDEO_APP_LAUNCHER = context.getResources().getStringArray(R.array.config_highAmbientBrightnessThresholdsOfFixedRefreshRate);
        this.SVIDEO_APP_OPTION_LAUNCHER = context.getResources().getStringArray(R.array.config_highDisplayBrightnessThresholdsOfFixedRefreshRate);
        this.VIDEO_APP_LAUNCHER = context.getResources().getStringArray(R.array.config_integrityRuleProviderPackages);
        this.EMAIL_APP_LAUNCHER = context.getResources().getStringArray(R.array.config_displayWhiteBalanceStrongDisplayColorTemperatures);
        this.EBOOK_APP_LAUNCHER = context.getResources().getStringArray(R.array.config_displayWhiteBalanceStrongAmbientColorTemperatures);
        this.ACL_CONTROL_GALLERY_APP_LIST = context.getResources().getStringArray(R.array.config_deviceStatesAvailableForAppRequests);
        context.getResources().getStringArray(R.array.config_deviceSpecificSystemServices);
        this.DAY_OF_USE_SUPPORT_APP_LIST = context.getResources().getStringArray(R.array.config_displayCutoutPathArray);
        this.OVERHEAT_CONTROL_SUPPORT_APP_LIST = context.getResources().getStringArray(R.array.config_globalActionsList);
        this.EYE_COMFORT_BLACKLIST_APP_LIST = context.getResources().getStringArray(R.array.config_dropboxLowPriorityTags);
        this.EYE_COMFORT_1_05_APP_LIST = context.getResources().getStringArray(R.array.config_dockExtconStateMapping);
        this.EYE_COMFORT_1_10_APP_LIST = context.getResources().getStringArray(R.array.config_doubleClickVibePattern);
        this.EYE_COMFORT_1_15_APP_LIST = context.getResources().getStringArray(R.array.config_dozeTapSensorPostureMapping);
        this.DOU_BRIGHTNESS_STANDARD_VALUE = context.getResources().getInteger(R.integer.config_deskDockKeepsScreenOn);
        this.AUTO_CURRENT_LIMIT_VERSION = context.getResources().getInteger(R.integer.config_audio_ring_vol_default);
        this.FOREGROUND_RESCAN_DEBOUNCE_MILLIS = context.getResources().getInteger(R.integer.config_maxNumVisibleRecentTasks_lowRam);
        this.ACTION_DETAIL_VIEW_STATE_IN_DEBOUNCE_MILLIS = context.getResources().getInteger(R.integer.config_autoBrightnessBrighteningLightDebounce);
        context.getResources().getInteger(R.integer.config_autoBrightnessDarkeningLightDebounce);
        this.ACTION_MOVIE_PLAYER_STATE_IN_DEBOUNCE_MILLIS = context.getResources().getInteger(R.integer.config_autoBrightnessShortTermModelTimeout);
        this.ACTION_MOVIE_PLAYER_STATE_OUT_DEBOUNCE_MILLIS = context.getResources().getInteger(R.integer.config_autoGroupAtCount);
        this.ACTION_VIDEO_APP_STATE_IN_DEBOUNCE_MILLIS = context.getResources().getInteger(R.integer.config_autoPowerModeThresholdAngle);
        context.getResources().getInteger(R.integer.config_backgroundUserScheduledStopTimeSecs);
        this.ACTION_SET_UI_MODE_DEBOUNCE_MILLIS = context.getResources().getInteger(R.integer.default_reserved_data_coding_scheme);
        context.getResources().getInteger(R.integer.config_autoPowerModeAnyMotionSensor);
        context.getResources().getInteger(R.integer.config_maximumScreenDimDuration);
        this.IS_CAMERA_APP_DEBOUNCE_MILLIS = context.getResources().getInteger(R.integer.config_maxUiWidth);
        mSCSControlHandler.removeMessages(21);
        mSCSControlHandler.sendEmptyMessage(21);
        this.mBrowserBrightnessStringArray = context.getResources().getStringArray(R.array.config_reduceBrightColorsCoefficientsNonlinear);
        this.mBrowserBrightnessArray = new int[2];
        int i = 0;
        while (true) {
            String[] strArr = this.mBrowserBrightnessStringArray;
            if (i >= strArr.length) {
                break;
            }
            this.mBrowserBrightnessArray[i] = Integer.parseInt(strArr[i]);
            i++;
        }
        this.mVisionBoosterStringArray = this.mContext.getResources().getStringArray(R.array.config_displayWhiteBalanceAmbientColorTemperatures);
        if (new File(this.ADAPTIVE_CONTROL_PATH).exists()) {
            String[] stringArray = this.mContext.getResources().getStringArray(R.array.config_locationDriverAssistancePackageNames);
            if (stringArray.length == 2) {
                this.mAclDimmingFunction = true;
            }
            StringBuilder sb = new StringBuilder("mAclDimmingFunction ");
            sb.append(this.mAclDimmingFunction);
            sb.append(" , array lenght : ");
            sb.append(stringArray.length);
            sb.append(" , AclVersion : ");
            SystemServiceManager$$ExternalSyntheticOutline0.m(sb, this.AUTO_CURRENT_LIMIT_VERSION, "MdnieScenarioControlService");
        }
        Slog.i("MdnieScenarioControlService", "mEnvironmentAdaptiveDisplaySupported true , mGlareReductionSupported : false");
        if (SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_NATURAL_MODE_TYPE", 0) == 1) {
            this.mNaturalGammaScreenModeSupported = true;
        }
        SettingsObserver settingsObserver = new SettingsObserver(this.mHandler);
        this.mSemMultiWindowManager = new SemMultiWindowManager();
        ContentResolver contentResolver = this.mContext.getContentResolver();
        long uptimeMillis = SystemClock.uptimeMillis();
        contentResolver.registerContentObserver(Settings.Global.getUriFor("low_power"), false, settingsObserver);
        contentResolver.registerContentObserver(Settings.System.getUriFor("screen_mode_setting"), false, settingsObserver, -1);
        contentResolver.registerContentObserver(Settings.System.getUriFor("vividness_intensity"), false, settingsObserver, -1);
        contentResolver.registerContentObserver(Settings.System.getUriFor("screen_brightness"), false, settingsObserver, -1);
        contentResolver.registerContentObserver(Settings.System.getUriFor("screen_auto_brightness_adj"), false, settingsObserver, -1);
        contentResolver.registerContentObserver(Settings.System.getUriFor("screen_brightness_mode"), false, settingsObserver, -1);
        contentResolver.registerContentObserver(Settings.System.getUriFor("lcd_curtain"), false, settingsObserver, -1);
        contentResolver.registerContentObserver(Settings.System.getUriFor("color_blind"), false, settingsObserver, -1);
        contentResolver.registerContentObserver(Settings.System.getUriFor("screen_off_timeout"), false, settingsObserver, -1);
        contentResolver.registerContentObserver(Settings.System.getUriFor("blue_light_filter"), false, settingsObserver, -1);
        contentResolver.registerContentObserver(Settings.System.getUriFor("blue_light_filter_adaptive_mode"), false, settingsObserver, -1);
        contentResolver.registerContentObserver(Settings.System.getUriFor("blue_light_filter_anti_glare"), false, settingsObserver, -1);
        contentResolver.registerContentObserver(Settings.System.getUriFor("blue_light_filter_type"), false, settingsObserver, -1);
        contentResolver.registerContentObserver(Settings.Secure.getUriFor("reduce_bright_colors_activated"), false, settingsObserver, -1);
        contentResolver.registerContentObserver(Settings.Secure.getUriFor("reduce_bright_colors_level"), false, settingsObserver, -1);
        contentResolver.registerContentObserver(Settings.System.getUriFor("high_brightness_mode_pms_enter"), false, settingsObserver, -1);
        Slog.d("MdnieScenarioControlService", "registerDisplayStateListener");
        this.mFoldStateListener = new SemWindowManager.FoldStateListener() { // from class: com.samsung.android.displaysolution.MdnieScenarioControlService.3
            public final void onFoldStateChanged(boolean z) {
                synchronized (MdnieScenarioControlService.this.mLock) {
                    MdnieScenarioControlService.this.mIsFolded = z;
                }
            }

            public final void onTableModeChanged(boolean z) {
            }
        };
        SemWindowManager.getInstance().registerFoldStateListener(this.mFoldStateListener, (Handler) null);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
        intentFilter.addAction("android.intent.action.SCREEN_ON");
        ActivityManagerService$$ExternalSyntheticOutline0.m(intentFilter, "android.intent.action.SCREEN_OFF", "android.intent.action.USER_PRESENT", "ACTION_MOVIE_PLAYER_STATE_IN", "ACTION_MOVIE_PLAYER_STATE_OUT");
        intentFilter.addAction("com.samsung.server.PowerManagerService.action.USER_ACTIVITY");
        intentFilter.addAction("com.samsung.systemui.statusbar.EXPANDED");
        intentFilter.addAction("com.samsung.systemui.statusbar.COLLAPSED");
        this.mContext.registerReceiverAsUser(new ScreenWatchingReceiver(), UserHandle.ALL, intentFilter, null, null, 2);
        this.mHandler.removeMessages(15);
        this.mHandler.sendEmptyMessageAtTime(15, uptimeMillis + this.GET_SYSTEM_SERVICES_MILLIS);
        SemSystemProperties.set("sys.mdniecontrolservice.mscon", "false");
        if (this.mUseMdnieScenarioControlConfig) {
            SemSystemProperties.set("sys.mdniecontrolservice.mscon", "true");
        }
        this.mScreenStateOn = true;
        if ("500".equals(SemSystemProperties.get("persist.dm.passive.ambient_brightness", "")) || "1000".equals(SemSystemProperties.get("persist.dm.passive.ambient_brightness", ""))) {
            SemSystemProperties.set("persist.dm.passive.ambient_brightness", "1000,1500");
        }
        if ("172".equals(SemSystemProperties.get("persist.dm.passive.display_brightness", "")) || "255".equals(SemSystemProperties.get("persist.dm.passive.display_brightness", ""))) {
            SemSystemProperties.set("persist.dm.passive.display_brightness", "255,85");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0081 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:35:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:40:0x008b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v2, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v6 */
    /* JADX WARN: Type inference failed for: r1v8, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r2v1, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r6v4, types: [java.lang.String] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static java.lang.String getStringFromFile(java.lang.String r10) {
        /*
            r0 = 128(0x80, float:1.794E-43)
            byte[] r1 = new byte[r0]
            java.io.File r2 = new java.io.File
            r2.<init>(r10)
            r3 = 0
            r4 = r3
        Lb:
            if (r4 >= r0) goto L12
            r1[r4] = r3
            int r4 = r4 + 1
            goto Lb
        L12:
            boolean r0 = r2.exists()
            r4 = 0
            if (r0 == 0) goto L9e
            java.lang.String r0 = "File Close error"
            java.lang.String r5 = "MdnieScenarioControlService"
            if (r10 == 0) goto L2b
            java.io.FileInputStream r10 = new java.io.FileInputStream     // Catch: java.lang.Throwable -> L25 java.lang.Exception -> L28
            r10.<init>(r2)     // Catch: java.lang.Throwable -> L25 java.lang.Exception -> L28
            goto L2c
        L25:
            r10 = move-exception
            goto L89
        L28:
            r10 = move-exception
            r1 = r4
            goto L50
        L2b:
            r10 = r4
        L2c:
            if (r10 == 0) goto L93
            int r2 = r10.read(r1)     // Catch: java.lang.Throwable -> L3f java.lang.Exception -> L4e
            if (r2 <= 0) goto L4a
            java.lang.String r6 = new java.lang.String     // Catch: java.lang.Throwable -> L3f java.lang.Exception -> L43
            int r7 = r2 + (-1)
            java.nio.charset.Charset r8 = java.nio.charset.StandardCharsets.UTF_8     // Catch: java.lang.Throwable -> L3f java.lang.Exception -> L43
            r6.<init>(r1, r3, r7, r8)     // Catch: java.lang.Throwable -> L3f java.lang.Exception -> L43
            r4 = r6
            goto L4a
        L3f:
            r1 = move-exception
            r4 = r10
            r10 = r1
            goto L89
        L43:
            r1 = move-exception
            r3 = r2
        L45:
            r9 = r4
            r4 = r10
            r10 = r1
            r1 = r9
            goto L50
        L4a:
            r10.close()     // Catch: java.lang.Throwable -> L3f java.lang.Exception -> L43
            goto L93
        L4e:
            r1 = move-exception
            goto L45
        L50:
            java.lang.StringBuilder r2 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L25
            r2.<init>()     // Catch: java.lang.Throwable -> L25
            java.lang.String r6 = "Exception : "
            r2.append(r6)     // Catch: java.lang.Throwable -> L25
            r2.append(r10)     // Catch: java.lang.Throwable -> L25
            java.lang.String r6 = " , in : "
            r2.append(r6)     // Catch: java.lang.Throwable -> L25
            r2.append(r4)     // Catch: java.lang.Throwable -> L25
            java.lang.String r6 = " , value : "
            r2.append(r6)     // Catch: java.lang.Throwable -> L25
            r2.append(r1)     // Catch: java.lang.Throwable -> L25
            java.lang.String r6 = " , length : "
            r2.append(r6)     // Catch: java.lang.Throwable -> L25
            r2.append(r3)     // Catch: java.lang.Throwable -> L25
            java.lang.String r2 = r2.toString()     // Catch: java.lang.Throwable -> L25
            android.util.Slog.e(r5, r2)     // Catch: java.lang.Throwable -> L25
            r10.printStackTrace()     // Catch: java.lang.Throwable -> L25
            if (r4 == 0) goto L9d
            r4.close()     // Catch: java.lang.Exception -> L85
            goto L9d
        L85:
            android.util.Slog.e(r5, r0)
            goto L9d
        L89:
            if (r4 == 0) goto L92
            r4.close()     // Catch: java.lang.Exception -> L8f
            goto L92
        L8f:
            android.util.Slog.e(r5, r0)
        L92:
            throw r10
        L93:
            if (r10 == 0) goto L9c
            r10.close()     // Catch: java.lang.Exception -> L99
            goto L9c
        L99:
            android.util.Slog.e(r5, r0)
        L9c:
            r1 = r4
        L9d:
            return r1
        L9e:
            return r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.displaysolution.MdnieScenarioControlService.getStringFromFile(java.lang.String):java.lang.String");
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x0044  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void browser_brightness_decrease_mode(boolean r13) {
        /*
            Method dump skipped, instructions count: 337
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.displaysolution.MdnieScenarioControlService.browser_brightness_decrease_mode(boolean):void");
    }

    /* JADX WARN: Finally extract failed */
    public final HashMap getAppListRegistState(List list) {
        HashMap hashMap = new HashMap();
        try {
            String str = "packagename IN('" + TextUtils.join("','", list) + "')";
            String[] strArr = {"packagename"};
            Cursor cursor = null;
            try {
                DisplaySolutionDataBase displaySolutionDataBase = this.mDBHelper;
                SQLiteDatabase sQLiteDatabase = this.mDisplaySolutionDataBase;
                displaySolutionDataBase.getClass();
                SQLiteQueryBuilder sQLiteQueryBuilder = new SQLiteQueryBuilder();
                sQLiteQueryBuilder.setTables("MSCS_APP_LIST");
                cursor = sQLiteQueryBuilder.query(sQLiteDatabase, strArr, str, null, null, null, null);
                if (cursor != null) {
                    while (cursor.moveToNext()) {
                        hashMap.put(cursor.getString(0), Boolean.TRUE);
                    }
                }
                if (cursor != null) {
                    cursor.close();
                }
            } catch (Throwable th) {
                if (cursor != null) {
                    cursor.close();
                }
                throw th;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return hashMap;
    }

    public final int getAppSettingState(String str) {
        Cursor cursor = null;
        try {
            DisplaySolutionDataBase displaySolutionDataBase = this.mDBHelper;
            displaySolutionDataBase.getClass();
            SQLiteQueryBuilder sQLiteQueryBuilder = new SQLiteQueryBuilder();
            sQLiteQueryBuilder.setTables("MSCS_APP_LIST");
            cursor = sQLiteQueryBuilder.query(this.mDisplaySolutionDataBase, null, "packagename = '" + str + "' ", null, null, null, null);
            if (cursor != null && cursor.moveToFirst()) {
                this.mAppLaunchStateInDatabase = cursor.getInt(cursor.getColumnIndex("settingstate"));
            }
            return this.mAppLaunchStateInDatabase;
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public final void getScenarioMode() {
        if (this.mSupportAPmDNIe) {
            this.SCENARIO_VALUE = this.mDisplayAiqeManager.getContentMode();
            return;
        }
        try {
            this.SCENARIO_VALUE = getStringFromFile(this.READING_SCENARIO_PATH);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public final boolean getting_autocurrentlimit_state() {
        SemDisplaySolutionManager semDisplaySolutionManager = this.mSemDisplaySolutionManager;
        if (semDisplaySolutionManager != null) {
            this.mAclState = semDisplaySolutionManager.getAutoCurrentLimitOffModeEnabled();
        }
        return this.mAclState;
    }

    public final boolean getting_knox_mode_enabled() {
        return SemPersonaManager.isKnoxId(((SemPersonaManager) this.mContext.getSystemService("persona")).getFocusedUser());
    }

    public final boolean getting_setting_value() {
        boolean z = getting_knox_mode_enabled();
        ContentResolver contentResolver = this.mContext.getContentResolver();
        if (z) {
            this.mHdrEffectEnabled = Settings.System.getIntForUser(contentResolver, "hdr_effect", 0, 0) == 1;
            Slog.d("MdnieScenarioControlService", "Use Owner User");
        } else {
            this.mHdrEffectEnabled = Settings.System.getIntForUser(contentResolver, "hdr_effect", 0, -2) == 1;
            Slog.d("MdnieScenarioControlService", "Use Current User");
        }
        return this.mHdrEffectEnabled;
    }

    public final void insertDataUsage(String str) {
        ContentValues m = AccountManagerService$$ExternalSyntheticOutline0.m("packagename", str);
        m.put("settingstate", (Integer) 1);
        Cursor cursor = null;
        try {
            DisplaySolutionDataBase displaySolutionDataBase = this.mDBHelper;
            displaySolutionDataBase.getClass();
            SQLiteQueryBuilder sQLiteQueryBuilder = new SQLiteQueryBuilder();
            sQLiteQueryBuilder.setTables("MSCS_APP_LIST");
            cursor = sQLiteQueryBuilder.query(this.mDisplaySolutionDataBase, null, "packagename = '" + str + "' ", null, null, null, null);
            if ((cursor != null ? cursor.getCount() : 0) == 0) {
                SQLiteDatabase sQLiteDatabase = this.mDisplaySolutionDataBase;
                DisplaySolutionDataBase displaySolutionDataBase2 = this.mDBHelper;
                if (sQLiteDatabase == null) {
                    sQLiteDatabase = displaySolutionDataBase2.getWritableDatabase();
                } else {
                    displaySolutionDataBase2.getClass();
                }
                sQLiteDatabase.insert("MSCS_APP_LIST", "", m);
            }
        } finally {
            if (cursor != null) {
                cursor.close();
            }
        }
    }

    public final void mdnie_reset() {
        this.mUIScenarioEnabled = false;
        this.mEmailScenarioEnabled = false;
        this.mVideoScenarioEnabled = false;
        this.mEbookScenarioEnabled = false;
        this.mGalleryScenarioEnabled = false;
        this.mSVideoScenarioEnabled = false;
        this.mCameraScenarioEnabled = false;
        this.mBrowserScenarioEnabled = false;
        this.mSVideoOptionScenarioEnabled = false;
    }

    public final void scenario_enable_reset() {
        this.mUIScenarioEnabled = false;
        this.mEmailScenarioEnabled = false;
        this.mVideoScenarioEnabled = false;
        this.mEbookScenarioEnabled = false;
        this.mGalleryScenarioEnabled = false;
        this.mSVideoScenarioEnabled = false;
        this.mCameraScenarioEnabled = false;
        this.mBrowserScenarioEnabled = false;
        this.mSVideoOptionScenarioEnabled = false;
    }

    public final void setAclModeScenario(int i, boolean z) {
        this.mCurAclValue = i;
        SemDisplaySolutionManager semDisplaySolutionManager = this.mSemDisplaySolutionManager;
        if (semDisplaySolutionManager != null) {
            boolean z2 = this.mAclDimmingFunction;
            if (!z2) {
                semDisplaySolutionManager.onAutoCurrentLimitStateChanged(z);
                return;
            }
            if (z2) {
                boolean z3 = this.mAutoBrightnessMode;
                if (!z3) {
                    if (z3) {
                        return;
                    }
                    if (i == 1) {
                        i = 3;
                    }
                    Slog.v("MdnieScenarioControlService", "setAclModeScenario() ACL mPrevAclValue (" + this.mPrevAclValue + ") - mCurrentValue (" + this.mCurAclValue + ")");
                    this.mSemDisplaySolutionManager.onAutoCurrentLimitStateChangedInt(i);
                    this.mPrevAclValue = this.mCurAclValue;
                    return;
                }
                if (this.mAntiGlareEnable) {
                    if (i == 0) {
                        i = 2;
                    } else if (i == 1) {
                        i = 5;
                    }
                }
                Slog.v("MdnieScenarioControlService", "setAclModeScenario() ACL mPrevAclValue (" + this.mPrevAclValue + ") - mCurrentValue (" + this.mCurAclValue + ")");
                this.mSemDisplaySolutionManager.onAutoCurrentLimitStateChangedInt(i);
                this.mPrevAclValue = this.mCurAclValue;
            }
        }
    }

    public final void setBrightnessScaleFactor(int i) {
        SemDisplaySolutionManager semDisplaySolutionManager = this.mSemDisplaySolutionManager;
        if (semDisplaySolutionManager != null) {
            if (i == 0) {
                this.mUseScaleFactorState = false;
                semDisplaySolutionManager.setMultipleScreenBrightness("brightness_scale_off");
                Slog.v("MdnieScenarioControlService", "Calling SemDisplaySolutionManager API(setMultipleScreenBrightness(" + i + ")");
                return;
            }
            if (i == 1 && !this.isLockScreenOn) {
                this.mUseScaleFactorState = true;
                semDisplaySolutionManager.setMultipleScreenBrightness("brightness_scale_on_1");
                Slog.v("MdnieScenarioControlService", "Calling SemDisplaySolutionManager API(setMultipleScreenBrightness(" + i + ")");
                return;
            }
            if (i == 2 && !this.isLockScreenOn) {
                this.mUseScaleFactorState = true;
                semDisplaySolutionManager.setMultipleScreenBrightness("brightness_scale_on_2");
                Slog.v("MdnieScenarioControlService", "Calling SemDisplaySolutionManager API(setMultipleScreenBrightness(" + i + ")");
                return;
            }
            if (i == 3 && !this.isLockScreenOn) {
                this.mUseScaleFactorState = true;
                semDisplaySolutionManager.setMultipleScreenBrightness("brightness_scale_on_3");
                Slog.v("MdnieScenarioControlService", "Calling SemDisplaySolutionManager API(setMultipleScreenBrightness(" + i + ")");
                return;
            }
            if (i == 4 && !this.isLockScreenOn) {
                this.mUseScaleFactorState = true;
                semDisplaySolutionManager.setMultipleScreenBrightness("brightness_scale_on_4");
                Slog.v("MdnieScenarioControlService", "Calling SemDisplaySolutionManager API(setMultipleScreenBrightness(" + i + ")");
                return;
            }
            if (i == 5 && !this.isLockScreenOn) {
                this.mUseScaleFactorState = true;
                semDisplaySolutionManager.setMultipleScreenBrightness("brightness_scale_on_5");
                Slog.v("MdnieScenarioControlService", "Calling SemDisplaySolutionManager API(setMultipleScreenBrightness(" + i + ")");
                return;
            }
            if (i != 6 || this.isLockScreenOn) {
                if (i != 7 || this.isLockScreenOn) {
                    return;
                }
                this.mUseScaleFactorState = true;
                semDisplaySolutionManager.setMultipleScreenBrightness("brightness_scale_on_7");
                Slog.v("MdnieScenarioControlService", "Calling SemDisplaySolutionManager API(setMultipleScreenBrightness(" + i + ")");
                return;
            }
            this.mUseScaleFactorState = true;
            if (this.DOU_BRIGHTNESS_STANDARD_VALUE == 255) {
                semDisplaySolutionManager.setMultipleScreenBrightness("brightness_scale_off");
                Slog.v("MdnieScenarioControlService", "Calling SemDisplaySolutionManager API(setMultipleScreenBrightness(" + i + ")");
                return;
            }
            semDisplaySolutionManager.setMultipleScreenBrightness("brightness_scale_on_6");
            Slog.v("MdnieScenarioControlService", "Calling SemDisplaySolutionManager API(setMultipleScreenBrightness(" + i + ")");
        }
    }

    public final void setCameraAppLaunch(boolean z) {
        if (this.mSemDisplaySolutionManager == null) {
            this.mSemDisplaySolutionManager = (SemDisplaySolutionManager) this.mContext.getSystemService("DisplaySolution");
        }
        SemDisplaySolutionManager semDisplaySolutionManager = this.mSemDisplaySolutionManager;
        if (semDisplaySolutionManager != null) {
            semDisplaySolutionManager.setCameraModeEnable(z);
        }
    }

    public final void setDouAppLaunch(boolean z) {
        if (this.mSemDisplaySolutionManager == null) {
            this.mSemDisplaySolutionManager = (SemDisplaySolutionManager) this.mContext.getSystemService("DisplaySolution");
        }
        SemDisplaySolutionManager semDisplaySolutionManager = this.mSemDisplaySolutionManager;
        if (semDisplaySolutionManager != null) {
            semDisplaySolutionManager.setDouAppModeEnable(z);
        }
    }

    public final void setMdnieScenarioMode(int i) {
        long uptimeMillis = SystemClock.uptimeMillis();
        SemMdnieManager semMdnieManager = this.mMdnieManager;
        if (semMdnieManager != null) {
            semMdnieManager.setContentMode(i);
            Slog.v("MdnieScenarioControlService", "Calling MdnieManager API(setContentMode(" + i + "))");
            MSCSControlHandler mSCSControlHandler = this.mHandler;
            if (i == 8) {
                this.mBrowserAppLauncher = true;
                Slog.d("MdnieScenarioControlService", "Start Browser Brightness Decrease Timer");
                mSCSControlHandler.removeMessages(17);
                mSCSControlHandler.sendEmptyMessageAtTime(17, uptimeMillis + this.ACTION_BROWSER_BRIGHTNESS_DECREASE_FIRST_MILLIS);
                return;
            }
            this.mBrowserAppLauncher = false;
            boolean z = this.mBrowserBrightnessDecreaseEnabled;
            if (z) {
                mSCSControlHandler.removeMessages(17);
                mSCSControlHandler.removeMessages(18);
                mSCSControlHandler.sendEmptyMessage(18);
            } else {
                if (z) {
                    return;
                }
                mSCSControlHandler.removeMessages(17);
            }
        }
    }

    public final void setUIMode(String str) {
        getScenarioMode();
        this.mAclOffEnabled = getting_autocurrentlimit_state();
        int i = 0;
        while (true) {
            String[] strArr = this.OVERHEAT_CONTROL_SUPPORT_APP_LIST;
            if (i >= strArr.length) {
                break;
            }
            if (strArr[i].contains(str)) {
                this.mOverheatControlSupportAppState = true;
                break;
            } else {
                this.mOverheatControlSupportAppState = false;
                i++;
            }
        }
        mdnie_reset();
        ProxyManager$$ExternalSyntheticOutline0.m("MdnieScenarioControlService", new StringBuilder("mAclOffEnabled : "), this.mAclOffEnabled);
        if (this.mAclOffEnabled && !this.isLockScreenOn) {
            setAclModeScenario(1, false);
        }
        if (this.mOverheatControlSupportAppState && !this.mHighBrightnessModeEnabled && !this.mMultiWindowOn) {
            setBrightnessScaleFactor(7);
        } else if (this.mUseScaleFactorState) {
            setBrightnessScaleFactor(0);
        }
        String str2 = this.SCENARIO_VALUE;
        if (str2 == null || str2.equals("0") || this.SCENARIO_VALUE.contains("UI")) {
            return;
        }
        setMdnieScenarioMode(0);
        AudioManagerHelper.semSetAudioHDR(false);
        setVideoAppLaunch(false);
        setCameraAppLaunch(false);
        setDouAppLaunch(false);
        this.mGalleryAppState = false;
        this.mDayOfUseSupportAppState = false;
        Slog.v("MdnieScenarioControlService", "setUIMode from UI function");
    }

    public final void setVideoAppLaunch(boolean z) {
        if (this.mSemDisplaySolutionManager == null) {
            this.mSemDisplaySolutionManager = (SemDisplaySolutionManager) this.mContext.getSystemService("DisplaySolution");
        }
        SemDisplaySolutionManager semDisplaySolutionManager = this.mSemDisplaySolutionManager;
        if (semDisplaySolutionManager != null) {
            semDisplaySolutionManager.setVideoModeEnable(z);
        }
    }

    public final void setVisionBoosterMode(int i, int i2) {
        SemMdnieManager semMdnieManager = this.mMdnieManager;
        if (semMdnieManager != null) {
            int i3 = -1;
            try {
                Slog.d("MdnieScenarioControlService", "getVisionBoosterIndex() mAutoBrightnessMode : " + this.mAutoBrightnessMode + " , mReduceBrightColorsActivatedEnabled : " + this.mReduceBrightColorsActivatedEnabled + " , lux : " + i2);
                int i4 = 0;
                if (this.mAutoBrightnessMode && !this.mReduceBrightColorsActivatedEnabled && i2 != -1) {
                    int i5 = 0;
                    while (true) {
                        String[] strArr = this.mVisionBoosterStringArray;
                        if (i4 > strArr.length - 1 || i2 < Integer.parseInt(strArr[i4])) {
                            break;
                        }
                        i5 = i4 + 1;
                        i4 = i5;
                    }
                    i4 = i5;
                }
                Slog.d("MdnieScenarioControlService", "Vision Booster Index : " + i4);
                i3 = i4;
            } catch (Exception e) {
                e.printStackTrace();
            }
            semMdnieManager.setHighBrightnessMode(i, i2, i3);
        }
    }

    public final void setmDNIeModeState(String str) {
        if (!this.mNaturalGammaScreenModeSupported || str == null || str.equals(this.mPrevmDNIeMode)) {
            return;
        }
        this.mPrevmDNIeMode = str;
        if (str.contains("NATURAL_DS") || (!str.contains("HBM_MODE") && str.contains("EAD_MODE"))) {
            wcg_property_changed(0, 301);
            return;
        }
        int i = this.mScreenModeSetting;
        if (i != 4) {
            if (i == 3) {
                wcg_property_changed(0, 300);
            }
        } else if (SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_VIVIDPLUS", 0) == 1) {
            wcg_property_changed(0, 256);
        } else if (SemFloatingFeature.getInstance().getInt("SEC_FLOATING_FEATURE_LCD_CONFIG_VIVIDPLUS", 0) >= 2) {
            wcg_property_changed(0, this.mVividnessIndex + 256);
        }
    }

    public final void setting_is_changed() {
        ContentResolver contentResolver = this.mContext.getContentResolver();
        Settings.Global.getInt(contentResolver, "low_power", 0);
        this.mScreenCurtainEnabled = Settings.System.getIntForUser(contentResolver, "lcd_curtain", 0, -2) == 1;
        this.mColorBlindEnabled = Settings.System.getIntForUser(contentResolver, "color_blind", 0, -2) == 1;
        this.mHighBrightnessModeEnabled = Settings.System.getIntForUser(contentResolver, "high_brightness_mode_pms_enter", 0, -2) == 1;
        this.mAutoBrightnessMode = Settings.System.getIntForUser(contentResolver, "screen_brightness_mode", 0, -2) == 1;
        this.mScreenOffTomeoutAbnormal = Settings.System.getIntForUser(contentResolver, "screen_off_timeout", 0, -2) > 600000;
        Settings.System.getIntForUser(contentResolver, "blue_light_filter", 0, -2);
        Settings.System.getIntForUser(contentResolver, "blue_light_filter_adaptive_mode", 0, -2);
        Settings.System.getIntForUser(contentResolver, "blue_light_filter_anti_glare", 0, -2);
        Settings.System.getIntForUser(contentResolver, "blue_light_filter_type", 0, -2);
        this.mReduceBrightColorsActivatedEnabled = Settings.Secure.getIntForUser(contentResolver, "reduce_bright_colors_activated", 0, -2) == 1;
        this.mReduceBrightColorsLevel = Settings.Secure.getIntForUser(contentResolver, "reduce_bright_colors_level", 50, -2);
        this.mScreenModeSetting = Settings.System.getIntForUser(contentResolver, "screen_mode_setting", 4, -2);
        this.mVividnessIndex = Settings.System.getIntForUser(contentResolver, "vividness_intensity", 0, -2);
        boolean z = (this.mScreenCurtainEnabled || this.mColorBlindEnabled) ? false : true;
        this.mSettingCondition = z;
        if (this.mScreenStateOn && z && this.mWorkingCondition) {
            try {
                this.mProcessObserver.onForegroundActivitiesChanged(-1, 0, false);
            } catch (RemoteException unused) {
                Slog.d("MdnieScenarioControlService", "failed to onForegroundActivitiesChanged");
            }
        }
    }

    public final void updateVideoEnhancerSettingState(int i, String str) {
        Slog.d("MdnieScenarioControlService", "Update Video Enhancer SubKey state. package : " + str + " , state : " + i);
        ContentValues contentValues = new ContentValues();
        contentValues.put("settingstate", Integer.valueOf(i));
        SQLiteDatabase sQLiteDatabase = this.mDisplaySolutionDataBase;
        String m = XmlUtils$$ExternalSyntheticOutline0.m("packagename = '", str, "' ");
        DisplaySolutionDataBase displaySolutionDataBase = this.mDBHelper;
        if (sQLiteDatabase == null) {
            sQLiteDatabase = displaySolutionDataBase.getWritableDatabase();
        } else {
            displaySolutionDataBase.getClass();
        }
        sQLiteDatabase.update("MSCS_APP_LIST", contentValues, m, null);
    }

    public final void wcg_property_changed(int i, int i2) {
        if (i == this.mPrevPropertyValue && i2 == this.mPrevRenderIntentValue) {
            return;
        }
        this.mPrevPropertyValue = i;
        this.mPrevRenderIntentValue = i2;
        Slog.d("MdnieScenarioControlService", DualAppManagerService$$ExternalSyntheticOutline0.m(i, i2, "GRAPHIC_PROPERTY(", "), SurfaceFlinger_RI(", ")"));
        SemSystemProperties.set("persist.sys.sf.native_mode", Integer.toString(i));
        IBinder service = ServiceManager.getService("SurfaceFlinger");
        if (service != null) {
            Parcel obtain = Parcel.obtain();
            obtain.writeInterfaceToken("android.ui.ISurfaceComposer");
            obtain.writeInt(i2);
            try {
                try {
                    service.transact(1023, obtain, null, 0);
                } catch (RemoteException e) {
                    Slog.e("MdnieScenarioControlService", "Failed to set display color_2", e);
                }
            } finally {
                obtain.recycle();
            }
        }
    }
}
