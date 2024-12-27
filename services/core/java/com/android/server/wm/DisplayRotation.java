package com.android.server.wm;

import android.R;
import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.pm.ActivityInfo;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Handler;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.provider.Settings;
import android.util.ArraySet;
import android.util.Slog;
import android.util.TimeUtils;
import android.view.DisplayAddress;
import android.view.DisplayInfo;
import android.view.Surface;
import com.android.internal.protolog.ProtoLogGroup;
import com.android.internal.protolog.ProtoLogImpl_54989576;
import com.android.internal.util.jobs.Preconditions$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.UiThread;
import com.android.server.media.MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0;
import com.android.server.policy.PhoneWindowManager;
import com.android.server.statusbar.StatusBarManagerInternal;
import com.android.server.wm.DeviceStateController;
import com.android.server.wm.WindowOrientationListener;
import java.io.PrintWriter;
import java.util.ArrayDeque;
import java.util.Iterator;
import java.util.Set;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class DisplayRotation {
    public final boolean isDefaultDisplay;
    public final boolean mAllowRotationResolver;
    public boolean mAllowSeamlessRotationDespiteNavBarMoving;
    public final int mCarDockRotation;
    public final DisplayRotationImmersiveAppCompatPolicy mCompatPolicyForImmersiveApps;
    public final Context mContext;
    final Runnable mDefaultDisplayRotationChangedCallback;
    public boolean mDefaultFixedToUserRotation;
    public int mDeferredRotationPauseCount;
    public int mDemoHdmiRotation;
    public boolean mDemoHdmiRotationLock;
    public int mDemoRotation;
    public boolean mDemoRotationLock;
    public final int mDeskDockRotation;
    public final DeviceStateController mDeviceStateController;
    public final DisplayContent mDisplayContent;
    public final DisplayPolicy mDisplayPolicy;
    public final DisplayRotationCoordinator mDisplayRotationCoordinator;
    public final DisplayWindowSettings mDisplayWindowSettings;
    public final FoldController mFoldController;
    int mLandscapeRotation;
    public String mLastRotationForOrientationInfo;
    public final int mLidOpenRotation;
    public final Object mLock;
    public final OrientationListener mOrientationListener;
    int mPortraitRotation;
    public boolean mRotatingSeamlessly;
    public int mRotation;
    public int mSeamlessRotationCount;
    int mSeascapeRotation;
    public final WindowManagerService mService;
    public final SettingsObserver mSettingsObserver;
    public int mShowRotationSuggestions;
    public StatusBarManagerInternal mStatusBarManagerInternal;
    public final boolean mSupportAutoRotation;
    public final int mUndockedHdmiRotation;
    int mUpsideDownRotation;
    public final RotationAnimationPair mTmpRotationAnim = new RotationAnimationPair();
    public final RotationHistory mRotationHistory = new RotationHistory(0);
    public final RotationHistory mRotationLockHistory = new RotationHistory(1);
    public int mCurrentAppOrientation = -1;
    public int mLastOrientation = -1;
    public int mLastSensorRotation = -1;
    public int mRotationChoiceShownToUserForConfirmation = -1;
    public int mAllowAllRotations = -1;
    public int mUserRotationMode = 0;
    public int mUserRotation = 0;
    public int mCameraRotationMode = 0;
    public int mFixedToUserRotation = 0;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class FoldController {
        public final AnonymousClass1 mActivityBoundsUpdateCallback;
        public final boolean mAllowHalfFoldAutoRotationOverride;
        public final int mDisplaySwitchRotationBlockTimeMs;
        public final int mHingeAngleRotationBlockTimeMs;
        public final AnonymousClass2 mHingeAngleSensorEventListener;
        public final boolean mIsDisplayAlwaysSeparatingHinge;
        public final int mMaxHingeAngle;
        public final boolean mPauseAutorotationDuringUnfolding;
        public final SensorManager mSensorManager;
        public boolean mShouldDisableRotationSensor;
        public boolean mShouldIgnoreSensorRotation;
        public int mHalfFoldSavedRotation = -1;
        public DeviceStateController.DeviceState mDeviceState = DeviceStateController.DeviceState.UNKNOWN;
        public long mLastHingeAngleEventTime = 0;
        public long mLastDisplaySwitchTime = 0;
        public boolean mInHalfFoldTransition = false;
        public final Set mTabletopRotations = new ArraySet();

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v11, types: [com.android.server.wm.DisplayRotation$FoldController$1] */
        /* JADX WARN: Type inference failed for: r2v10, types: [android.hardware.SensorEventListener, com.android.server.wm.DisplayRotation$FoldController$2] */
        public FoldController() {
            Sensor defaultSensor;
            this.mAllowHalfFoldAutoRotationOverride = DisplayRotation.this.mContext.getResources().getBoolean(R.bool.kg_sim_puk_account_full_screen);
            int[] intArray = DisplayRotation.this.mContext.getResources().getIntArray(R.array.config_testLocationProviders);
            boolean[] zArr = ProtoLogImpl_54989576.Cache.WM_DEBUG_ORIENTATION_enabled;
            if (intArray != null) {
                for (int i : intArray) {
                    if (i == 0) {
                        ((ArraySet) this.mTabletopRotations).add(0);
                    } else if (i == 90) {
                        ((ArraySet) this.mTabletopRotations).add(1);
                    } else if (i == 180) {
                        ((ArraySet) this.mTabletopRotations).add(2);
                    } else if (i == 270) {
                        ((ArraySet) this.mTabletopRotations).add(3);
                    } else if (zArr[4]) {
                        ProtoLogImpl_54989576.e(ProtoLogGroup.WM_DEBUG_ORIENTATION, 5325136615007859122L, 1, null, Long.valueOf(i));
                    }
                }
            } else if (zArr[3]) {
                ProtoLogImpl_54989576.w(ProtoLogGroup.WM_DEBUG_ORIENTATION, 4616480353797749295L, 0, null, null);
            }
            this.mIsDisplayAlwaysSeparatingHinge = DisplayRotation.this.mContext.getResources().getBoolean(R.bool.config_letterboxIsAutomaticReachabilityInBookModeEnabled);
            this.mActivityBoundsUpdateCallback = new Runnable() { // from class: com.android.server.wm.DisplayRotation.FoldController.1
                @Override // java.lang.Runnable
                public final void run() {
                    ActivityRecord activityRecord;
                    FoldController foldController = FoldController.this;
                    DeviceStateController.DeviceState deviceState = foldController.mDeviceState;
                    if (deviceState == DeviceStateController.DeviceState.OPEN || deviceState == DeviceStateController.DeviceState.HALF_FOLDED) {
                        synchronized (DisplayRotation.this.mLock) {
                            try {
                                Task task = DisplayRotation.this.mDisplayContent.getTask(new DisplayRotation$$ExternalSyntheticLambda3(1));
                                if (task != null && (activityRecord = task.topRunningActivity(false)) != null) {
                                    activityRecord.recomputeConfiguration();
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                }
            };
            boolean z = DisplayRotation.this.mContext.getResources().getBoolean(R.bool.kg_top_align_page_shrink_on_bouncer_visible);
            this.mPauseAutorotationDuringUnfolding = z;
            if (z) {
                this.mDisplaySwitchRotationBlockTimeMs = DisplayRotation.this.mContext.getResources().getInteger(R.integer.config_screen_rotation_fade_in);
                this.mHingeAngleRotationBlockTimeMs = DisplayRotation.this.mContext.getResources().getInteger(R.integer.config_screen_rotation_fade_in_delay);
                this.mMaxHingeAngle = DisplayRotation.this.mContext.getResources().getInteger(R.integer.config_screen_rotation_fade_out);
                DisplayRotation displayRotation = DisplayRotation.this;
                SensorManager sensorManager = (SensorManager) displayRotation.mContext.getSystemService(SensorManager.class);
                this.mSensorManager = sensorManager;
                if (sensorManager == null || (defaultSensor = sensorManager.getDefaultSensor(36)) == null) {
                    return;
                }
                ?? r2 = new SensorEventListener() { // from class: com.android.server.wm.DisplayRotation.FoldController.2
                    @Override // android.hardware.SensorEventListener
                    public final void onAccuracyChanged(Sensor sensor, int i2) {
                    }

                    @Override // android.hardware.SensorEventListener
                    public final void onSensorChanged(SensorEvent sensorEvent) {
                        FoldController foldController = FoldController.this;
                        if (sensorEvent.values[0] < foldController.mMaxHingeAngle) {
                            DisplayRotation displayRotation2 = DisplayRotation.this;
                            foldController.mLastHingeAngleEventTime = displayRotation2.uptimeMillis();
                            foldController.updateSensorRotationBlockIfNeeded();
                            displayRotation2.getHandler().postDelayed(new DisplayRotation$$ExternalSyntheticLambda0(1, foldController), foldController.mHingeAngleRotationBlockTimeMs);
                        }
                    }
                };
                this.mHingeAngleSensorEventListener = r2;
                this.mSensorManager.registerListener((SensorEventListener) r2, defaultSensor, 0, displayRotation.getHandler());
            }
        }

        public final void foldStateChanged(DeviceStateController.DeviceState deviceState) {
            boolean z = ProtoLogImpl_54989576.Cache.WM_DEBUG_ORIENTATION_enabled[1];
            DisplayRotation displayRotation = DisplayRotation.this;
            if (z) {
                ProtoLogImpl_54989576.v(ProtoLogGroup.WM_DEBUG_ORIENTATION, 8852346340572084230L, 5457, null, Long.valueOf(displayRotation.mDisplayContent.mDisplayId), String.valueOf(deviceState.name()), Long.valueOf(this.mHalfFoldSavedRotation), Long.valueOf(displayRotation.mUserRotation), Long.valueOf(displayRotation.mLastSensorRotation), Long.valueOf(displayRotation.mLastOrientation), Long.valueOf(displayRotation.mRotation));
            }
            DeviceStateController.DeviceState deviceState2 = this.mDeviceState;
            if (deviceState2 == DeviceStateController.DeviceState.UNKNOWN) {
                this.mDeviceState = deviceState;
                return;
            }
            DeviceStateController.DeviceState deviceState3 = DeviceStateController.DeviceState.HALF_FOLDED;
            if (deviceState != deviceState3 || deviceState2 == deviceState3) {
                this.mInHalfFoldTransition = true;
                this.mDeviceState = deviceState;
                displayRotation.mService.updateRotationUnchecked(false, false);
            } else {
                displayRotation.mDisplayContent.mRotationReversionController.beforeOverrideApplied(2);
                this.mHalfFoldSavedRotation = displayRotation.mRotation;
                this.mDeviceState = deviceState;
                displayRotation.mService.updateRotationUnchecked(false, false);
            }
            Handler handler = UiThread.getHandler();
            AnonymousClass1 anonymousClass1 = this.mActivityBoundsUpdateCallback;
            handler.removeCallbacks(anonymousClass1);
            UiThread.getHandler().postDelayed(anonymousClass1, 800L);
        }

        public final void updateSensorRotationBlockIfNeeded() {
            DisplayRotation displayRotation = DisplayRotation.this;
            long uptimeMillis = displayRotation.uptimeMillis();
            boolean z = uptimeMillis - this.mLastDisplaySwitchTime < ((long) this.mDisplaySwitchRotationBlockTimeMs) || uptimeMillis - this.mLastHingeAngleEventTime < ((long) this.mHingeAngleRotationBlockTimeMs);
            if (z != this.mShouldIgnoreSensorRotation) {
                this.mShouldIgnoreSensorRotation = z;
                if (z) {
                    return;
                }
                if (!this.mShouldDisableRotationSensor) {
                    displayRotation.updateRotationAndSendNewConfigIfChanged();
                } else {
                    this.mShouldDisableRotationSensor = false;
                    displayRotation.updateOrientationListenerLw();
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class OrientationListener extends WindowOrientationListener implements Runnable {
        public transient boolean mEnabled;

        public OrientationListener(Context context, Handler handler, int i) {
            super(context, handler, i);
        }

        @Override // com.android.server.wm.WindowOrientationListener
        public final boolean isRotationResolverEnabled() {
            DisplayRotation displayRotation = DisplayRotation.this;
            return displayRotation.mAllowRotationResolver && displayRotation.mUserRotationMode == 0 && displayRotation.mCameraRotationMode == 1 && !displayRotation.mService.mPowerManager.isPowerSaveMode();
        }

        /* JADX WARN: Code restructure failed: missing block: B:37:0x00fb, code lost:
        
            if (r11 >= 0) goto L103;
         */
        /* JADX WARN: Code restructure failed: missing block: B:41:0x0103, code lost:
        
            if (r11 == r0.mPortraitRotation) goto L103;
         */
        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Removed duplicated region for block: B:106:0x00e4 A[ADDED_TO_REGION] */
        /* JADX WARN: Removed duplicated region for block: B:33:0x00ed  */
        /* JADX WARN: Removed duplicated region for block: B:66:0x0143  */
        @Override // com.android.server.wm.WindowOrientationListener
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onProposedRotationChanged(int r11) {
            /*
                Method dump skipped, instructions count: 354
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.DisplayRotation.OrientationListener.onProposedRotationChanged(int):void");
        }

        @Override // java.lang.Runnable
        public final void run() {
            if (this.mEnabled) {
                enable$1();
                return;
            }
            synchronized (this.mLock) {
                try {
                    if (this.mSensor == null) {
                        Slog.w("WindowOrientationListener", "Cannot detect sensors. Invalid disable");
                        return;
                    }
                    if (super.mEnabled) {
                        if (WindowOrientationListener.LOG) {
                            Slog.d("WindowOrientationListener", "WindowOrientationListener disabled");
                        }
                        this.mSensorManager.unregisterListener(this.mOrientationJudge);
                        super.mEnabled = false;
                    }
                } finally {
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RotationAnimationPair {
        public int mEnter;
        public int mExit;
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RotationHistory {
        public final ArrayDeque mRecords;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        public final class Record {
            public final DeviceStateController.DeviceState mDeviceState;
            public final String mDisplayRotationCompatPolicySummary;
            public final int mFromRotation;
            public final int mHalfFoldSavedRotation;
            public final boolean mIgnoreOrientationRequest;
            public final boolean mInHalfFoldTransition;
            public final String mLastOrientationSource;
            public final String mNonDefaultRequestingTaskDisplayArea;
            public final boolean[] mRotationReversionSlots;
            public final int mSensorRotation;
            public final int mSourceOrientation;
            public final long mTimestamp = System.currentTimeMillis();
            public final int mToRotation;
            public final int mUserRotation;
            public final int mUserRotationMode;

            public Record(DisplayRotation displayRotation, int i, int i2) {
                String str;
                String str2;
                this.mFromRotation = i;
                this.mToRotation = i2;
                this.mUserRotation = displayRotation.mUserRotation;
                this.mUserRotationMode = displayRotation.mUserRotationMode;
                OrientationListener orientationListener = displayRotation.mOrientationListener;
                this.mSensorRotation = (orientationListener == null || !orientationListener.mEnabled) ? -2 : displayRotation.mLastSensorRotation;
                DisplayContent displayContent = displayRotation.mDisplayContent;
                this.mIgnoreOrientationRequest = displayContent.getIgnoreOrientationRequest();
                TaskDisplayArea taskDisplayArea = displayContent.mOrientationRequestingTaskDisplayArea;
                this.mNonDefaultRequestingTaskDisplayArea = taskDisplayArea == null ? "none" : taskDisplayArea != displayContent.getDefaultTaskDisplayArea() ? taskDisplayArea.toString() : null;
                WindowContainer lastOrientationSource = displayContent.getLastOrientationSource();
                if (lastOrientationSource != null) {
                    this.mLastOrientationSource = lastOrientationSource.toString();
                    WindowState asWindowState = lastOrientationSource.asWindowState();
                    this.mSourceOrientation = asWindowState != null ? asWindowState.mAttrs.screenOrientation : lastOrientationSource.getOverrideOrientation();
                } else {
                    this.mLastOrientationSource = null;
                    this.mSourceOrientation = -2;
                }
                FoldController foldController = displayRotation.mFoldController;
                if (foldController != null) {
                    this.mHalfFoldSavedRotation = foldController.mHalfFoldSavedRotation;
                    this.mInHalfFoldTransition = foldController.mInHalfFoldTransition;
                    this.mDeviceState = foldController.mDeviceState;
                } else {
                    this.mHalfFoldSavedRotation = -2;
                    this.mInHalfFoldTransition = false;
                    this.mDeviceState = DeviceStateController.DeviceState.UNKNOWN;
                }
                DisplayRotationCompatPolicy displayRotationCompatPolicy = displayContent.mAppCompatCameraPolicy.mDisplayRotationCompatPolicy;
                if (displayRotationCompatPolicy != null) {
                    if (displayRotationCompatPolicy.isTreatmentEnabledForDisplay()) {
                        ActivityRecord activityRecord = displayRotationCompatPolicy.mDisplayContent.topRunningActivity(true);
                        StringBuilder sb = new StringBuilder(" mLastReportedOrientation=");
                        sb.append(ActivityInfo.screenOrientationToString(displayRotationCompatPolicy.mLastReportedOrientation));
                        sb.append(" topActivity=");
                        sb.append(activityRecord == null ? "null" : activityRecord.shortComponentName);
                        sb.append(" isTreatmentEnabledForActivity=");
                        sb.append(displayRotationCompatPolicy.isTreatmentEnabledForActivity(true, activityRecord));
                        sb.append("mCameraStateMonitor=");
                        StringBuilder sb2 = new StringBuilder(" CameraIdPackageNameBiMapping=");
                        sb2.append("{ mPackageToCameraIdMap=" + displayRotationCompatPolicy.mCameraStateMonitor.mCameraIdPackageBiMapping.mPackageToCameraIdMap + " }");
                        sb.append(sb2.toString());
                        str2 = sb.toString();
                    } else {
                        str2 = "";
                    }
                    str = "DisplayRotationCompatPolicy{ isTreatmentEnabledForDisplay=" + displayRotationCompatPolicy.isTreatmentEnabledForDisplay() + str2 + " }";
                } else {
                    str = null;
                }
                this.mDisplayRotationCompatPolicySummary = str;
                DisplayRotationReversionController displayRotationReversionController = displayContent.mRotationReversionController;
                DisplayContent displayContent2 = displayRotationReversionController.mDisplayContent;
                this.mRotationReversionSlots = (displayContent2.mAppCompatCameraPolicy.mDisplayRotationCompatPolicy == null && displayContent2.mDisplayRotation.mFoldController == null && !displayContent2.getIgnoreOrientationRequest()) ? null : (boolean[]) displayRotationReversionController.mSlots.clone();
            }
        }

        public RotationHistory(int i) {
            switch (i) {
                case 1:
                    this.mRecords = new ArrayDeque(8);
                    break;
                default:
                    this.mRecords = new ArrayDeque(8);
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SettingsObserver extends ContentObserver {
        public SettingsObserver(Handler handler) {
            super(handler);
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            if (DisplayRotation.m1059$$Nest$mupdateSettings(DisplayRotation.this)) {
                DisplayRotation.this.mService.updateRotationUnchecked(false, false);
            }
        }
    }

    /* renamed from: -$$Nest$mupdateSettings, reason: not valid java name */
    public static boolean m1059$$Nest$mupdateSettings(DisplayRotation displayRotation) {
        boolean z;
        boolean z2;
        boolean z3;
        ContentResolver contentResolver = displayRotation.mContext.getContentResolver();
        synchronized (displayRotation.mLock) {
            try {
                z = true;
                int intForUser = ActivityManager.isLowRamDeviceStatic() ? 0 : Settings.Secure.getIntForUser(contentResolver, "show_rotation_suggestions", 1, -2);
                if (displayRotation.mShowRotationSuggestions != intForUser) {
                    displayRotation.mShowRotationSuggestions = intForUser;
                    z2 = true;
                } else {
                    z2 = false;
                }
                int intForUser2 = Settings.System.getIntForUser(contentResolver, "user_rotation", 0, -2);
                if (displayRotation.mUserRotation != intForUser2) {
                    displayRotation.mUserRotation = intForUser2;
                    z3 = true;
                } else {
                    z3 = false;
                }
                int i = Settings.System.getIntForUser(contentResolver, "accelerometer_rotation", 0, -2) != 0 ? 0 : 1;
                if (displayRotation.mUserRotationMode != i) {
                    displayRotation.mUserRotationMode = i;
                    z2 = true;
                    z3 = true;
                }
                if (z2) {
                    displayRotation.updateOrientationListenerLw();
                }
                int intForUser3 = Settings.Secure.getIntForUser(contentResolver, "camera_autorotate", 0, -2);
                if (displayRotation.mCameraRotationMode != intForUser3) {
                    displayRotation.mCameraRotationMode = intForUser3;
                } else {
                    z = z3;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return z;
    }

    public DisplayRotation(WindowManagerService windowManagerService, DisplayContent displayContent, DisplayAddress displayAddress, DisplayPolicy displayPolicy, DisplayWindowSettings displayWindowSettings, Context context, Object obj, DeviceStateController deviceStateController, DisplayRotationCoordinator displayRotationCoordinator) {
        String str;
        this.mService = windowManagerService;
        this.mDisplayContent = displayContent;
        this.mDisplayPolicy = displayPolicy;
        this.mDisplayWindowSettings = displayWindowSettings;
        this.mContext = context;
        this.mLock = obj;
        this.mDeviceStateController = deviceStateController;
        boolean z = displayContent.isDefaultDisplay;
        this.isDefaultDisplay = z;
        this.mCompatPolicyForImmersiveApps = initImmersiveAppCompatPolicy(windowManagerService, displayContent);
        boolean z2 = context.getResources().getBoolean(R.bool.config_supportsMultiWindow);
        this.mSupportAutoRotation = z2;
        this.mAllowRotationResolver = context.getResources().getBoolean(R.bool.config_allowTheaterModeWakeFromCameraLens);
        this.mLidOpenRotation = readRotation(R.integer.config_navBarOpacityMode);
        this.mCarDockRotation = readRotation(R.integer.config_defaultPictureInPictureGravity);
        this.mDeskDockRotation = readRotation(R.integer.config_dropboxLowPriorityBroadcastRateLimitPeriod);
        this.mUndockedHdmiRotation = readRotation(R.integer.device_idle_mms_temp_app_allowlist_duration_ms);
        if (displayAddress instanceof DisplayAddress.Physical) {
            str = SystemProperties.get("ro.bootanim.set_orientation_" + ((DisplayAddress.Physical) displayAddress).getPhysicalDisplayId(), "");
        } else {
            str = "";
        }
        if ("".equals(str) && displayContent.isDefaultDisplay) {
            str = SystemProperties.get("ro.bootanim.set_orientation_logical_" + displayContent.mDisplayId, "");
        }
        int i = str.equals("ORIENTATION_90") ? 1 : str.equals("ORIENTATION_180") ? 2 : str.equals("ORIENTATION_270") ? 3 : 0;
        this.mRotation = i;
        this.mDisplayRotationCoordinator = displayRotationCoordinator;
        if (z) {
            displayRotationCoordinator.mDefaultDisplayDefaultRotation = i;
        }
        DisplayRotation$$ExternalSyntheticLambda0 displayRotation$$ExternalSyntheticLambda0 = new DisplayRotation$$ExternalSyntheticLambda0(0, this);
        this.mDefaultDisplayRotationChangedCallback = displayRotation$$ExternalSyntheticLambda0;
        if (DisplayRotationCoordinator.isSecondaryInternalDisplay(displayContent) && deviceStateController.mMatchBuiltInDisplayOrientationToDefaultDisplay) {
            if (displayRotationCoordinator.mDefaultDisplayRotationChangedCallback != null) {
                throw new UnsupportedOperationException("Multiple clients unsupported");
            }
            displayRotationCoordinator.mDefaultDisplayRotationChangedCallback = displayRotation$$ExternalSyntheticLambda0;
            if (displayRotationCoordinator.mDefaultDisplayCurrentRotation != displayRotationCoordinator.mDefaultDisplayDefaultRotation) {
                displayRotation$$ExternalSyntheticLambda0.run();
            }
        }
        if (!z) {
            this.mFoldController = null;
            return;
        }
        Handler handler = UiThread.getHandler();
        OrientationListener orientationListener = new OrientationListener(context, handler, i);
        this.mOrientationListener = orientationListener;
        int i2 = this.mRotation;
        synchronized (orientationListener.mLock) {
            orientationListener.mCurrentRotation = i2;
        }
        SettingsObserver settingsObserver = new SettingsObserver(handler);
        this.mSettingsObserver = settingsObserver;
        ContentResolver contentResolver = context.getContentResolver();
        contentResolver.registerContentObserver(Settings.Secure.getUriFor("show_rotation_suggestions"), false, settingsObserver, -1);
        contentResolver.registerContentObserver(Settings.System.getUriFor("accelerometer_rotation"), false, settingsObserver, -1);
        contentResolver.registerContentObserver(Settings.System.getUriFor("user_rotation"), false, settingsObserver, -1);
        contentResolver.registerContentObserver(Settings.Secure.getUriFor("camera_autorotate"), false, settingsObserver, -1);
        m1059$$Nest$mupdateSettings(this);
        if (!z2 || context.getResources().getIntArray(R.array.preloaded_freeform_multi_window_drawables).length <= 0) {
            this.mFoldController = null;
        } else {
            this.mFoldController = new FoldController();
        }
    }

    public final void cancelSeamlessRotation() {
        if (this.mRotatingSeamlessly) {
            DisplayRotation$$ExternalSyntheticLambda2 displayRotation$$ExternalSyntheticLambda2 = new DisplayRotation$$ExternalSyntheticLambda2();
            DisplayContent displayContent = this.mDisplayContent;
            displayContent.forAllWindows((Consumer) displayRotation$$ExternalSyntheticLambda2, true);
            this.mSeamlessRotationCount = 0;
            this.mRotatingSeamlessly = false;
            displayContent.finishAsyncRotationIfPossible();
        }
    }

    public final void dump(String str, PrintWriter printWriter) {
        printWriter.println(str + "DisplayRotation");
        printWriter.println(str + "  mCurrentAppOrientation=" + ActivityInfo.screenOrientationToString(this.mCurrentAppOrientation));
        StringBuilder sb = new StringBuilder();
        sb.append(str);
        sb.append("  mLastOrientation=");
        StringBuilder m = MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0.m(sb, this.mLastOrientation, printWriter, str, "  mRotation=");
        m.append(this.mRotation);
        printWriter.print(m.toString());
        StringBuilder m2 = MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0.m(new StringBuilder(" mDeferredRotationPauseCount="), this.mDeferredRotationPauseCount, printWriter, str, "  mLandscapeRotation=");
        m2.append(Surface.rotationToString(this.mLandscapeRotation));
        printWriter.print(m2.toString());
        printWriter.println(" mSeascapeRotation=" + Surface.rotationToString(this.mSeascapeRotation));
        printWriter.print(str + "  mPortraitRotation=" + Surface.rotationToString(this.mPortraitRotation));
        StringBuilder sb2 = new StringBuilder(" mUpsideDownRotation=");
        sb2.append(Surface.rotationToString(this.mUpsideDownRotation));
        printWriter.println(sb2.toString());
        StringBuilder sb3 = new StringBuilder();
        sb3.append(str);
        sb3.append("  mSupportAutoRotation=");
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(sb3, this.mSupportAutoRotation, printWriter);
        OrientationListener orientationListener = this.mOrientationListener;
        if (orientationListener != null) {
            String m$1 = ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(str, "  ");
            synchronized (orientationListener.mLock) {
                try {
                    printWriter.println(m$1 + "WindowOrientationListener");
                    String str2 = m$1 + "  ";
                    printWriter.println(str2 + "mEnabled=" + ((WindowOrientationListener) orientationListener).mEnabled);
                    printWriter.println(str2 + "mCurrentRotation=" + Surface.rotationToString(orientationListener.mCurrentRotation));
                    StringBuilder sb4 = new StringBuilder();
                    sb4.append(str2);
                    sb4.append("mSensorType=null");
                    printWriter.println(sb4.toString());
                    printWriter.println(str2 + "mSensor=" + orientationListener.mSensor);
                    printWriter.println(str2 + "mRate=" + orientationListener.mRate);
                    WindowOrientationListener.OrientationJudge orientationJudge = orientationListener.mOrientationJudge;
                    if (orientationJudge != null) {
                        orientationJudge.dumpLocked(printWriter, str2);
                    }
                } finally {
                }
            }
        }
        printWriter.println();
        printWriter.print(str + "  mCarDockRotation=" + Surface.rotationToString(this.mCarDockRotation));
        StringBuilder sb5 = new StringBuilder(" mDeskDockRotation=");
        sb5.append(Surface.rotationToString(this.mDeskDockRotation));
        printWriter.println(sb5.toString());
        StringBuilder sb6 = new StringBuilder();
        sb6.append(str);
        sb6.append("  mUserRotationMode=");
        int i = this.mUserRotationMode;
        sb6.append(i != 0 ? i != 1 ? Integer.toString(i) : "USER_ROTATION_LOCKED" : "USER_ROTATION_FREE");
        printWriter.print(sb6.toString());
        printWriter.print(" mUserRotation=" + Surface.rotationToString(this.mUserRotation));
        printWriter.print(" mCameraRotationMode=" + this.mCameraRotationMode);
        StringBuilder sb7 = new StringBuilder(" mAllowAllRotations=");
        int i2 = this.mAllowAllRotations;
        sb7.append(i2 != -1 ? i2 != 0 ? i2 != 1 ? Integer.toString(i2) : "true" : "false" : "unknown");
        printWriter.println(sb7.toString());
        printWriter.print(str + "  mDemoHdmiRotation=" + Surface.rotationToString(this.mDemoHdmiRotation));
        StringBuilder sb8 = new StringBuilder(" mDemoHdmiRotationLock=");
        sb8.append(this.mDemoHdmiRotationLock);
        printWriter.print(sb8.toString());
        printWriter.println(" mUndockedHdmiRotation=" + Surface.rotationToString(this.mUndockedHdmiRotation));
        printWriter.println(str + "  mLidOpenRotation=" + Surface.rotationToString(this.mLidOpenRotation));
        printWriter.println(str + "  mFixedToUserRotation=" + isFixedToUserRotation());
        if (this.mFoldController != null) {
            printWriter.println(str + "FoldController");
            StringBuilder sb9 = new StringBuilder();
            sb9.append(str);
            sb9.append("  mPauseAutorotationDuringUnfolding=");
            StringBuilder m3 = MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0.m(MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0.m(MediaRouter2ServiceImpl$UserRecord$$ExternalSyntheticOutline0.m(sb9, this.mFoldController.mPauseAutorotationDuringUnfolding, printWriter, str, "  mShouldDisableRotationSensor="), this.mFoldController.mShouldDisableRotationSensor, printWriter, str, "  mShouldIgnoreSensorRotation="), this.mFoldController.mShouldIgnoreSensorRotation, printWriter, str, "  mLastDisplaySwitchTime=");
            m3.append(this.mFoldController.mLastDisplaySwitchTime);
            printWriter.println(m3.toString());
            printWriter.println(str + "  mLastHingeAngleEventTime=" + this.mFoldController.mLastHingeAngleEventTime);
            printWriter.println(str + "  mDeviceState=" + this.mFoldController.mDeviceState);
        }
        if (!this.mRotationHistory.mRecords.isEmpty()) {
            printWriter.println();
            printWriter.println(str + "  RotationHistory");
            StringBuilder sb10 = new StringBuilder("    ");
            sb10.append(str);
            str = sb10.toString();
            Iterator it = this.mRotationHistory.mRecords.iterator();
            while (it.hasNext()) {
                RotationHistory.Record record = (RotationHistory.Record) it.next();
                record.getClass();
                printWriter.println(str + TimeUtils.logTimeOfDay(record.mTimestamp) + " " + Surface.rotationToString(record.mFromRotation) + " to " + Surface.rotationToString(record.mToRotation));
                StringBuilder sb11 = new StringBuilder();
                sb11.append(str);
                sb11.append("  source=");
                sb11.append(record.mLastOrientationSource);
                sb11.append(" ");
                sb11.append(ActivityInfo.screenOrientationToString(record.mSourceOrientation));
                printWriter.println(sb11.toString());
                StringBuilder sb12 = new StringBuilder();
                sb12.append(str);
                sb12.append("  mode=");
                int i3 = record.mUserRotationMode;
                sb12.append(i3 != 0 ? i3 != 1 ? Integer.toString(i3) : "USER_ROTATION_LOCKED" : "USER_ROTATION_FREE");
                sb12.append(" user=");
                sb12.append(Surface.rotationToString(record.mUserRotation));
                sb12.append(" sensor=");
                sb12.append(Surface.rotationToString(record.mSensorRotation));
                printWriter.println(sb12.toString());
                if (record.mIgnoreOrientationRequest) {
                    printWriter.println(str + "  ignoreRequest=true");
                }
                String str3 = record.mNonDefaultRequestingTaskDisplayArea;
                if (str3 != null) {
                    printWriter.println(str + "  requestingTda=" + str3);
                }
                int i4 = record.mHalfFoldSavedRotation;
                if (i4 != -2) {
                    printWriter.println(str + " halfFoldSavedRotation=" + i4 + " mInHalfFoldTransition=" + record.mInHalfFoldTransition + " mFoldState=" + record.mDeviceState);
                }
                String str4 = record.mDisplayRotationCompatPolicySummary;
                if (str4 != null) {
                    printWriter.println(str + str4);
                }
                boolean[] zArr = record.mRotationReversionSlots;
                if (zArr != null) {
                    StringBuilder m4 = Preconditions$$ExternalSyntheticOutline0.m(str, " reversionSlots= NOSENSOR ");
                    m4.append(zArr[0]);
                    m4.append(", CAMERA ");
                    m4.append(zArr[1]);
                    m4.append(" HALF_FOLD ");
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(m4, zArr[2], printWriter);
                }
            }
        }
        if (this.mRotationLockHistory.mRecords.isEmpty()) {
            return;
        }
        printWriter.println();
        printWriter.println(str + "  RotationLockHistory");
        StringBuilder sb13 = new StringBuilder("    ");
        sb13.append(str);
        String sb14 = sb13.toString();
        Iterator it2 = this.mRotationLockHistory.mRecords.iterator();
        while (it2.hasNext()) {
            DisplayRotation$RotationLockHistory$Record displayRotation$RotationLockHistory$Record = (DisplayRotation$RotationLockHistory$Record) it2.next();
            displayRotation$RotationLockHistory$Record.getClass();
            StringBuilder sb15 = new StringBuilder();
            sb15.append(sb14);
            sb15.append(TimeUtils.logTimeOfDay(displayRotation$RotationLockHistory$Record.mTimestamp));
            sb15.append(": mode=");
            int i5 = displayRotation$RotationLockHistory$Record.mUserRotationMode;
            sb15.append(i5 != 0 ? i5 != 1 ? Integer.toString(i5) : "USER_ROTATION_LOCKED" : "USER_ROTATION_FREE");
            sb15.append(", rotation=");
            sb15.append(Surface.rotationToString(displayRotation$RotationLockHistory$Record.mUserRotation));
            sb15.append(", caller=");
            BinaryTransparencyService$$ExternalSyntheticOutline0.m(sb15, displayRotation$RotationLockHistory$Record.mCaller, printWriter);
        }
    }

    public int getDemoUserRotationOverride() {
        return SystemProperties.getInt("persist.demo.userrotation", 0);
    }

    public String getDemoUserRotationPackage() {
        return SystemProperties.get("persist.demo.userrotation.package_name");
    }

    public Handler getHandler() {
        return this.mService.mH;
    }

    public DisplayRotationImmersiveAppCompatPolicy initImmersiveAppCompatPolicy(WindowManagerService windowManagerService, DisplayContent displayContent) {
        AppCompatConfiguration appCompatConfiguration = windowManagerService.mAppCompatConfiguration;
        if (appCompatConfiguration.mDeviceConfig.isBuildTimeFlagEnabled("enable_display_rotation_immersive_app_compat_policy")) {
            return new DisplayRotationImmersiveAppCompatPolicy(appCompatConfiguration, this, displayContent);
        }
        return null;
    }

    public final boolean isAnyPortrait(int i) {
        return i == this.mPortraitRotation || i == this.mUpsideDownRotation;
    }

    public final boolean isFixedToUserRotation() {
        int i = this.mFixedToUserRotation;
        if (i == 1) {
            return false;
        }
        if (i == 2) {
            return true;
        }
        if (i != 3) {
            return this.mDefaultFixedToUserRotation;
        }
        return false;
    }

    public final boolean isLandscapeOrSeascape(int i) {
        return i == this.mLandscapeRotation || i == this.mSeascapeRotation;
    }

    public final boolean isRotationFrozen() {
        return !this.isDefaultDisplay ? this.mUserRotationMode == 1 : Settings.System.getIntForUser(this.mContext.getContentResolver(), "accelerometer_rotation", 0, -2) == 0;
    }

    public final void markForSeamlessRotation(WindowState windowState, boolean z) {
        if (z == windowState.mSeamlesslyRotated || windowState.mForceSeamlesslyRotate) {
            return;
        }
        windowState.mSeamlesslyRotated = z;
        if (z) {
            this.mSeamlessRotationCount++;
        } else {
            this.mSeamlessRotationCount--;
        }
        if (this.mSeamlessRotationCount == 0) {
            if (ProtoLogImpl_54989576.Cache.WM_DEBUG_ORIENTATION_enabled[2]) {
                ProtoLogImpl_54989576.i(ProtoLogGroup.WM_DEBUG_ORIENTATION, -1216224951455892544L, 0, null, null);
            }
            this.mRotatingSeamlessly = false;
            this.mDisplayContent.finishAsyncRotationIfPossible();
            updateRotationAndSendNewConfigIfChanged();
        }
    }

    public final int readRotation(int i) {
        try {
            int integer = this.mContext.getResources().getInteger(i);
            if (integer == 0) {
                return 0;
            }
            if (integer == 90) {
                return 1;
            }
            if (integer != 180) {
                return integer != 270 ? -1 : 3;
            }
            return 2;
        } catch (Resources.NotFoundException unused) {
            return -1;
        }
    }

    public int rotationForOrientation(int i, int i2) {
        return rotationForOrientation(i, i2, true);
    }

    /* JADX WARN: Code restructure failed: missing block: B:121:0x0187, code lost:
    
        if ((r10 != null && r10.mAllowHalfFoldAutoRotationOverride && r10.mDeviceState == com.android.server.wm.DeviceStateController.DeviceState.HALF_FOLDED) != false) goto L100;
     */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x0195, code lost:
    
        if (r23 == 4) goto L123;
     */
    /* JADX WARN: Code restructure failed: missing block: B:123:0x0197, code lost:
    
        if (r23 == 10) goto L123;
     */
    /* JADX WARN: Code restructure failed: missing block: B:125:0x019a, code lost:
    
        if (r23 == 6) goto L123;
     */
    /* JADX WARN: Code restructure failed: missing block: B:127:0x019d, code lost:
    
        if (r23 != 7) goto L113;
     */
    /* JADX WARN: Code restructure failed: missing block: B:129:0x01a1, code lost:
    
        if (r4 != 1) goto L140;
     */
    /* JADX WARN: Code restructure failed: missing block: B:130:0x01a3, code lost:
    
        if (r23 == 5) goto L140;
     */
    /* JADX WARN: Code restructure failed: missing block: B:131:0x01a5, code lost:
    
        if (r23 == 0) goto L140;
     */
    /* JADX WARN: Code restructure failed: missing block: B:132:0x01a7, code lost:
    
        if (r23 == 1) goto L140;
     */
    /* JADX WARN: Code restructure failed: missing block: B:134:0x01ab, code lost:
    
        if (r23 == 8) goto L140;
     */
    /* JADX WARN: Code restructure failed: missing block: B:136:0x01af, code lost:
    
        if (r23 == 9) goto L140;
     */
    /* JADX WARN: Code restructure failed: missing block: B:137:0x01b1, code lost:
    
        r5 = r22.mUserRotation;
     */
    /* JADX WARN: Code restructure failed: missing block: B:149:0x01dd, code lost:
    
        if (r22.mService.mAtmService.mMultiTaskingAppCompatController.mOrientationPolicy.shouldIgnoreOrientationRequest(-1, r3.getLastOrientationSource()) != false) goto L139;
     */
    /* JADX WARN: Code restructure failed: missing block: B:151:0x01e1, code lost:
    
        if (r23 != 13) goto L138;
     */
    /* JADX WARN: Code restructure failed: missing block: B:158:0x0193, code lost:
    
        if (r23 != 13) goto L106;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int rotationForOrientation(int r23, int r24, boolean r25) {
        /*
            Method dump skipped, instructions count: 598
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.DisplayRotation.rotationForOrientation(int, int, boolean):int");
    }

    public final RotationAnimationPair selectRotationAnimation() {
        boolean z = (this.mDisplayPolicy.mScreenOnFully && ((PhoneWindowManager) this.mService.mPolicy).okToAnimate(false)) ? false : true;
        WindowState windowState = this.mDisplayPolicy.mTopFullscreenOpaqueWindowState;
        if (ProtoLogImpl_54989576.Cache.WM_DEBUG_ANIM_enabled[2]) {
            ProtoLogImpl_54989576.i(ProtoLogGroup.WM_DEBUG_ANIM, -7672508047849737424L, 52, null, String.valueOf(windowState), Long.valueOf(windowState == null ? 0L : windowState.mAttrs.rotationAnimation), Boolean.valueOf(z));
        }
        if (z) {
            RotationAnimationPair rotationAnimationPair = this.mTmpRotationAnim;
            rotationAnimationPair.mExit = R.anim.slide_out_micro;
            rotationAnimationPair.mEnter = R.anim.slide_out_left;
            return rotationAnimationPair;
        }
        if (windowState != null) {
            ActivityRecord activityRecord = windowState.mActivityRecord;
            int i = activityRecord != null ? activityRecord.mRotationAnimationHint : -1;
            if (i < 0 && this.mDisplayPolicy.mTopIsFullscreen) {
                i = windowState.mAttrs.rotationAnimation;
            }
            if (i != 1) {
                if (i == 2) {
                    RotationAnimationPair rotationAnimationPair2 = this.mTmpRotationAnim;
                    rotationAnimationPair2.mExit = R.anim.slide_out_micro;
                    rotationAnimationPair2.mEnter = R.anim.slide_out_left;
                } else if (i != 3) {
                    RotationAnimationPair rotationAnimationPair3 = this.mTmpRotationAnim;
                    rotationAnimationPair3.mEnter = 0;
                    rotationAnimationPair3.mExit = 0;
                }
            }
            RotationAnimationPair rotationAnimationPair4 = this.mTmpRotationAnim;
            rotationAnimationPair4.mExit = R.anim.slow_fade_in;
            rotationAnimationPair4.mEnter = R.anim.slide_out_left;
        } else {
            RotationAnimationPair rotationAnimationPair5 = this.mTmpRotationAnim;
            rotationAnimationPair5.mEnter = 0;
            rotationAnimationPair5.mExit = 0;
        }
        return this.mTmpRotationAnim;
    }

    public final void setFixedToUserRotation(int i) {
        if (this.mFixedToUserRotation == i) {
            return;
        }
        this.mFixedToUserRotation = i;
        DisplayWindowSettings displayWindowSettings = this.mDisplayWindowSettings;
        displayWindowSettings.getClass();
        DisplayContent displayContent = this.mDisplayContent;
        DisplayInfo displayInfo = displayContent.mDisplayInfo;
        DisplayWindowSettingsProvider displayWindowSettingsProvider = displayWindowSettings.mSettingsProvider;
        DisplayWindowSettings$SettingsProvider$SettingsEntry overrideSettings = displayWindowSettingsProvider.getOverrideSettings(displayInfo);
        overrideSettings.mFixedToUserRotation = Integer.valueOf(i);
        displayWindowSettingsProvider.updateOverrideSettings(displayInfo, overrideSettings);
        ActivityRecord activityRecord = displayContent.mFocusedApp;
        if (activityRecord != null) {
            displayContent.mOrientationRequestingTaskDisplayArea = activityRecord.getDisplayArea();
        }
        displayContent.updateOrientation(false);
    }

    public void setRotation(int i) {
        this.mRotation = i;
    }

    public void setUserRotation(int i, int i2, String str) {
        boolean z;
        RotationHistory rotationHistory = this.mRotationLockHistory;
        if (rotationHistory.mRecords.size() >= 8) {
            rotationHistory.mRecords.removeFirst();
        }
        rotationHistory.mRecords.addLast(new DisplayRotation$RotationLockHistory$Record(i, i2, str));
        this.mRotationChoiceShownToUserForConfirmation = -1;
        boolean z2 = true;
        if (useDefaultSettingsProvider()) {
            ContentResolver contentResolver = this.mContext.getContentResolver();
            Settings.System.putIntForUser(contentResolver, "accelerometer_rotation", i != 1 ? 1 : 0, -2);
            Settings.System.putIntForUser(contentResolver, "user_rotation", i2, -2);
            return;
        }
        if (this.mUserRotationMode != i) {
            this.mUserRotationMode = i;
            z = true;
        } else {
            z = false;
        }
        if (this.mUserRotation != i2) {
            this.mUserRotation = i2;
        } else {
            z2 = z;
        }
        DisplayWindowSettings displayWindowSettings = this.mDisplayWindowSettings;
        displayWindowSettings.getClass();
        DisplayContent displayContent = this.mDisplayContent;
        DisplayInfo displayInfo = displayContent.mDisplayInfo;
        DisplayWindowSettingsProvider displayWindowSettingsProvider = displayWindowSettings.mSettingsProvider;
        DisplayWindowSettings$SettingsProvider$SettingsEntry overrideSettings = displayWindowSettingsProvider.getOverrideSettings(displayInfo);
        overrideSettings.mUserRotationMode = Integer.valueOf(i);
        overrideSettings.mUserRotation = Integer.valueOf(i2);
        displayWindowSettingsProvider.updateOverrideSettings(displayInfo, overrideSettings);
        if (z2) {
            this.mService.updateRotationUnchecked(false, false);
            ContentRecorder contentRecorder = displayContent.mContentRecorder;
            if (contentRecorder != null) {
                contentRecorder.onConfigurationChanged(contentRecorder.mLastOrientation, contentRecorder.mLastWindowingMode);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:20:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0051  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public boolean shouldRotateSeamlessly(int r7, int r8, boolean r9) {
        /*
            r6 = this;
            boolean r0 = com.samsung.android.rune.CoreRune.FW_FLEXIBLE_DUAL_MODE
            com.android.server.wm.DisplayContent r1 = r6.mDisplayContent
            r2 = 1
            if (r0 == 0) goto L15
            int r0 = r1.mDisplayId
            if (r0 == r2) goto Lc
            goto L15
        Lc:
            com.android.server.wm.WindowManagerService r6 = r6.mService
            com.android.server.wm.WindowManagerServiceExt r6 = r6.mExt
            r6.getClass()
            r6 = 0
            throw r6
        L15:
            boolean r0 = r6.isDefaultDisplay
            r3 = 0
            if (r0 == 0) goto L40
            com.android.server.wm.TaskDisplayArea r0 = r1.getDefaultTaskDisplayArea()
            boolean r4 = r0.isSplitScreenVisible()
            boolean r0 = r0.isSplitScreenStarting()
            if (r4 != 0) goto L36
            if (r0 != 0) goto L36
            com.android.server.wm.DisplayContent$$ExternalSyntheticLambda7 r4 = new com.android.server.wm.DisplayContent$$ExternalSyntheticLambda7
            r5 = 1
            r4.<init>(r5)
            com.android.server.wm.Task r4 = r1.getRootTask(r4)
            if (r4 == 0) goto L40
        L36:
            if (r0 == 0) goto L3e
            java.lang.String r0 = "reject_seamless_rot(starting)"
            r1.setFadeInOutAnimationNeeded(r0, r2)
        L3e:
            r0 = r2
            goto L41
        L40:
            r0 = r3
        L41:
            boolean r4 = r1.hasTopFixedRotationLaunchingApp()
            if (r4 == 0) goto L51
            if (r0 == 0) goto L50
            java.lang.String r6 = "reject_seamless_rot"
            r1.setFadeInOutAnimationNeeded(r6, r2)
            return r3
        L50:
            return r2
        L51:
            if (r0 == 0) goto L54
            return r3
        L54:
            com.android.server.wm.DisplayPolicy r0 = r6.mDisplayPolicy
            com.android.server.wm.WindowState r0 = r0.mTopFullscreenOpaqueWindowState
            if (r0 == 0) goto Lc3
            com.android.server.wm.WindowState r4 = r1.mCurrentFocus
            if (r0 == r4) goto L60
            goto Lc3
        L60:
            android.view.WindowManager$LayoutParams r4 = r0.mAttrs
            int r4 = r4.rotationAnimation
            r5 = 3
            if (r4 == r5) goto L6f
            com.android.server.wm.ActivityRecord r4 = r0.mActivityRecord
            if (r4 == 0) goto Lc3
            boolean r4 = r4.mIsAllowedSeamlessRotation
            if (r4 == 0) goto Lc3
        L6f:
            boolean r4 = r0.inMultiWindowMode()
            if (r4 != 0) goto Lc3
            boolean r4 = r0.isAnimating(r5)
            if (r4 == 0) goto L7c
            goto Lc3
        L7c:
            boolean r4 = r6.mAllowSeamlessRotationDespiteNavBarMoving
            if (r4 != 0) goto L97
            com.android.server.wm.DisplayPolicy r6 = r6.mDisplayPolicy
            boolean r6 = r6.mNavigationBarCanMove
            if (r6 == 0) goto L87
            goto L97
        L87:
            r6 = 2
            if (r7 == r6) goto L8d
            if (r8 == r6) goto L8d
            goto L97
        L8d:
            com.android.server.wm.ActivityRecord r6 = r0.mActivityRecord
            if (r6 == 0) goto L96
            boolean r6 = r6.mIsAllowedSeamlessRotation
            if (r6 == 0) goto L96
            goto L97
        L96:
            return r3
        L97:
            com.android.server.wm.ActivityRecord r6 = r0.mActivityRecord
            if (r6 == 0) goto La2
            boolean r6 = r6.matchParentBounds()
            if (r6 != 0) goto La2
            return r3
        La2:
            com.android.server.wm.TaskDisplayArea r6 = r1.getDefaultTaskDisplayArea()
            boolean r6 = r6.hasPinnedTask()
            if (r6 != 0) goto Lc3
            boolean r6 = r1.hasAlertWindowSurfaces()
            if (r6 == 0) goto Lb3
            goto Lc3
        Lb3:
            if (r9 != 0) goto Lc2
            com.android.server.wm.DisplayRotation$$ExternalSyntheticLambda3 r6 = new com.android.server.wm.DisplayRotation$$ExternalSyntheticLambda3
            r7 = 0
            r6.<init>(r7)
            com.android.server.wm.WindowState r6 = r1.getWindow(r6)
            if (r6 == 0) goto Lc2
            return r3
        Lc2:
            return r2
        Lc3:
            return r3
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.DisplayRotation.shouldRotateSeamlessly(int, int, boolean):boolean");
    }

    public final boolean updateOrientation(int i, boolean z) {
        if (i == this.mLastOrientation && !z) {
            return false;
        }
        this.mLastOrientation = i;
        if (i != this.mCurrentAppOrientation) {
            this.mCurrentAppOrientation = i;
            if (this.isDefaultDisplay) {
                updateOrientationListenerLw();
            }
        }
        return updateRotationUnchecked(z);
    }

    public final void updateOrientationListener() {
        synchronized (this.mLock) {
            updateOrientationListenerLw();
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0083, code lost:
    
        if (r1.mSensor.isWakeUpSensor() != false) goto L25;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x010a, code lost:
    
        if (r18.mShowRotationSuggestions == 1) goto L58;
     */
    /* JADX WARN: Removed duplicated region for block: B:28:0x0111  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0134  */
    /* JADX WARN: Removed duplicated region for block: B:41:? A[RETURN, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateOrientationListenerLw() {
        /*
            Method dump skipped, instructions count: 341
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.DisplayRotation.updateOrientationListenerLw():void");
    }

    public final boolean updateRotationAndSendNewConfigIfChanged() {
        boolean updateRotationUnchecked = updateRotationUnchecked(false);
        if (updateRotationUnchecked) {
            this.mDisplayContent.sendNewConfiguration();
        }
        return updateRotationUnchecked;
    }

    /* JADX WARN: Removed duplicated region for block: B:150:0x0329  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x038d  */
    /* JADX WARN: Removed duplicated region for block: B:173:0x02d4  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0097  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00ac  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x00ff  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean updateRotationUnchecked(boolean r27) {
        /*
            Method dump skipped, instructions count: 981
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.wm.DisplayRotation.updateRotationUnchecked(boolean):boolean");
    }

    public long uptimeMillis() {
        return SystemClock.uptimeMillis();
    }

    public boolean useDefaultSettingsProvider() {
        return this.isDefaultDisplay;
    }
}
