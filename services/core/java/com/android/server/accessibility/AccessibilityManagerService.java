package com.android.server.accessibility;

import android.R;
import android.accessibilityservice.AccessibilityGestureEvent;
import android.accessibilityservice.AccessibilityServiceInfo;
import android.accessibilityservice.AccessibilityShortcutInfo;
import android.accessibilityservice.IAccessibilityServiceClient;
import android.accessibilityservice.MagnificationConfig;
import android.app.ActivityManager;
import android.app.ActivityOptions;
import android.app.AlertDialog;
import android.app.AppOpsManager;
import android.app.RemoteAction;
import android.app.admin.DevicePolicyManager;
import android.app.ecm.EnhancedConfirmationManager;
import android.appwidget.AppWidgetManagerInternal;
import android.companion.virtual.VirtualDevice;
import android.companion.virtual.VirtualDeviceManager;
import android.content.ActivityNotFoundException;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.PackageManagerInternal;
import android.content.pm.ParceledListSlice;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.database.ContentObserver;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.graphics.Matrix;
import android.graphics.Point;
import android.graphics.PointF;
import android.graphics.Rect;
import android.graphics.Region;
import android.hardware.display.DisplayManager;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.Messenger;
import android.os.Parcelable;
import android.os.PermissionEnforcer;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.os.ServiceManager;
import android.os.ShellCallback;
import android.os.SystemClock;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.provider.SettingsStringUtil;
import android.safetycenter.SafetyCenterManager;
import android.telephony.TelephonyManager;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.IntArray;
import android.util.Log;
import android.util.Pair;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import android.view.Display;
import android.view.IWindow;
import android.view.IWindowManager;
import android.view.InputEvent;
import android.view.KeyEvent;
import android.view.MagnificationSpec;
import android.view.MotionEvent;
import android.view.SurfaceControl;
import android.view.View;
import android.view.WindowInfo;
import android.view.WindowManager;
import android.view.accessibility.A11yLogger;
import android.view.accessibility.A11yRune;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityInteractionClient;
import android.view.accessibility.AccessibilityManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowAttributes;
import android.view.accessibility.AccessibilityWindowInfo;
import android.view.accessibility.IAccessibilityInteractionConnection;
import android.view.accessibility.IAccessibilityManager;
import android.view.accessibility.IAccessibilityManagerClient;
import android.view.accessibility.IMagnificationConnection;
import android.view.accessibility.MagnificationAnimationCallback;
import android.view.inputmethod.EditorInfo;
import android.widget.FrameLayout;
import com.android.internal.accessibility.AccessibilityDirectAccessController;
import com.android.internal.accessibility.AccessibilityShortcutController;
import com.android.internal.accessibility.common.ShortcutConstants;
import com.android.internal.accessibility.dialog.AccessibilitySamsungShortcutChooserActivity;
import com.android.internal.accessibility.dialog.AccessibilityTarget;
import com.android.internal.accessibility.dialog.AccessibilityTargetHelper;
import com.android.internal.accessibility.util.AccessibilityStatsLogUtils;
import com.android.internal.accessibility.util.AccessibilityUtils;
import com.android.internal.accessibility.util.ShortcutUtils;
import com.android.internal.content.PackageMonitor;
import com.android.internal.graphics.ColorUtils;
import com.android.internal.inputmethod.IRemoteAccessibilityInputConnection;
import com.android.internal.os.BackgroundThread;
import com.android.internal.os.SomeArgs;
import com.android.internal.util.ArrayUtils;
import com.android.internal.util.DumpUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.internal.util.FunctionalUtils;
import com.android.internal.util.IntPair;
import com.android.internal.util.Preconditions;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.internal.widget.LockPatternUtils;
import com.android.modules.expresslog.Counter;
import com.android.server.AccessibilityManagerInternal;
import com.android.server.AnyMotionDetector$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DeviceIdleController$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.HeimdAllFsService$$ExternalSyntheticOutline0;
import com.android.server.LocalServices;
import com.android.server.SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0;
import com.android.server.SystemService;
import com.android.server.accessibility.AbstractAccessibilityServiceConnection;
import com.android.server.accessibility.AccessibilityManagerService;
import com.android.server.accessibility.AccessibilitySecurityPolicy;
import com.android.server.accessibility.AccessibilityUserState;
import com.android.server.accessibility.AccessibilityWindowManager;
import com.android.server.accessibility.AccessibilityWindowManager.RemoteAccessibilityConnection;
import com.android.server.accessibility.FingerprintGestureDispatcher;
import com.android.server.accessibility.FlashNotificationsController;
import com.android.server.accessibility.PolicyWarningUIController;
import com.android.server.accessibility.ProxyManager;
import com.android.server.accessibility.SystemActionPerformer;
import com.android.server.accessibility.UiAutomationManager;
import com.android.server.accessibility.magnification.FullScreenMagnificationController;
import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.accessibility.magnification.MagnificationConnectionManager;
import com.android.server.accessibility.magnification.MagnificationController;
import com.android.server.accessibility.magnification.MagnificationController.DisableMagnificationCallback;
import com.android.server.accessibility.magnification.MagnificationGestureHandler;
import com.android.server.accessibility.magnification.MagnificationProcessor;
import com.android.server.accessibility.magnification.MagnificationScaleProvider;
import com.android.server.companion.virtual.VirtualDeviceManagerInternal;
import com.android.server.display.color.DisplayTransformManager;
import com.android.server.pm.UserManagerInternal;
import com.android.server.utils.Slogf;
import com.android.server.wm.ActivityTaskManagerInternal;
import com.android.server.wm.WindowManagerInternal;
import com.android.server.wm.WindowManagerService;
import com.android.settingslib.RestrictedLockUtils;
import com.samsung.android.cover.CoverState;
import com.samsung.android.desktopmode.SemDesktopModeManager;
import com.samsung.android.emergencymode.SemEmergencyManager;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.hardware.context.SemContextApproach;
import com.samsung.android.hardware.context.SemContextEvent;
import com.samsung.android.hardware.context.SemContextListener;
import com.samsung.android.hardware.display.SemMdnieManager;
import com.samsung.android.knox.custom.LauncherConfigurationInternal;
import com.samsung.android.knox.zt.devicetrust.EndpointMonitorConst;
import com.samsung.android.widget.SemLockPatternUtils;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AccessibilityManagerService extends IAccessibilityManager.Stub implements AbstractAccessibilityServiceConnection.SystemSupport, AccessibilityUserState.ServiceInfoChangeListener, AccessibilityWindowManager.AccessibilityEventSender, AccessibilitySecurityPolicy.AccessibilityUserManager, SystemActionPerformer.SystemActionsChangedListener, SystemActionPerformer.DisplayUpdateCallBack, ProxyManager.SystemSupport {
    static final String ACTION_LAUNCH_HEARING_DEVICES_DIALOG = "com.android.systemui.action.LAUNCH_HEARING_DEVICES_DIALOG";
    public static boolean SEC_DEBUG;
    public CVDCalculator cvdCalculator;
    public final AccessibilityDisplayListener mA11yDisplayListener;
    public final SparseArray mA11yOverlayLayers;
    public final AccessibilityWindowManager mA11yWindowManager;
    public final ActivityTaskManagerInternal mActivityTaskManagerService;
    public final Uri mAodShowStateUri;
    public Messenger mAssistantMenuMsgnr;
    public float mCVDSeverity;
    public int mCVDType;
    public final ArrayList mCallStack;
    public final CaptioningManagerImpl mCaptioningManagerImpl;
    public AODStateContentObserver mColorInversionStateContentObserver;
    public final Uri mColorInversionStateUri;
    public View mColorLensView;
    public final Context mContext;
    public int mCurrentUserId;
    public boolean mCurtainModeIsRunning;
    public AODStateContentObserver mDaltonizerEnabledStateContentObserver;
    public AODStateContentObserver mDaltonizerStateContentObserver;
    public final Uri mDisplayDaltonizerEnabledUri;
    public final Uri mDisplayDaltonizerSaturationLevelUri;
    public final Uri mDisplayDaltonizerUri;
    public EditorInfo mEditorInfo;
    public AlertDialog mEnableTouchExplorationDialog;
    public FingerprintGestureDispatcher mFingerprintGestureDispatcher;
    public final FlashNotificationsController mFlashNotificationsController;
    public GestureWakeup mGesturewakeup;
    public final RemoteCallbackList mGlobalClients;
    public boolean mHasInputFilter;
    public boolean mInitialized;
    public boolean mInputBound;
    public AccessibilityInputFilter mInputFilter;
    public boolean mInputFilterInstalled;
    public boolean mInputSessionRequested;
    public InteractionBridge mInteractionBridge;
    public boolean mIsAccessibilityButtonShown;
    public KeyEventDispatcher mKeyEventDispatcher;
    public final Object mLock;
    public final MagnificationController mMagnificationController;
    public final MagnificationProcessor mMagnificationProcessor;
    public final Handler mMainHandler;
    public SparseArray mMotionEventInjectors;
    public final PackageManager mPackageManager;
    public PackageMonitor mPackageMonitor;
    public final PowerManager mPowerManager;
    public final ProxyManager mProxyManager;
    public int mRealCurrentUserId;
    public IRemoteAccessibilityInputConnection mRemoteInputConnection;
    public boolean mRestarting;
    public final AccessibilitySecurityPolicy mSecurityPolicy;
    public final List mSendWindowStateChangedEventRunnables;
    public AODStateContentObserver mSetupCompleteStateContentObserver;
    public final Uri mSetupCompleteUri;
    public final TextUtils.SimpleStringSplitter mStringColonSplitter;
    public SystemActionPerformer mSystemActionPerformer;
    public final Set mTempComponentNameSet;
    public final IntArray mTempIntArray;
    public final Point mTempPoint;
    public final Rect mTempRect;
    public final Rect mTempRect1;
    public final AccessibilityTraceManager mTraceManager;
    public final UiAutomationManager mUiAutomationManager;
    public final UserManagerInternal mUmi;
    final SparseArray mUserStates;
    public final SparseBooleanArray mVisibleBgUserIds;
    public final WindowManagerInternal mWindowManagerService;
    public boolean shouldRecogniseTwoFingerGestures;
    public static final int OWN_PROCESS_ID = Process.myPid();
    public static int sIdCounter = 1;
    public static final float[] MATRIX_INVERT_COLOR = {0.402f, -0.598f, -0.599f, FullScreenMagnificationGestureHandler.MAX_SCALE, -1.174f, -0.174f, -1.175f, FullScreenMagnificationGestureHandler.MAX_SCALE, -0.228f, -0.228f, 0.772f, FullScreenMagnificationGestureHandler.MAX_SCALE, 1.0f, 1.0f, 1.0f, 1.0f};
    public static final float[] MATRIX_GRAYSCALE = {0.2126f, 0.2126f, 0.2126f, FullScreenMagnificationGestureHandler.MAX_SCALE, 0.7152f, 0.7152f, 0.7152f, FullScreenMagnificationGestureHandler.MAX_SCALE, 0.0722f, 0.0722f, 0.0722f, FullScreenMagnificationGestureHandler.MAX_SCALE, FullScreenMagnificationGestureHandler.MAX_SCALE, FullScreenMagnificationGestureHandler.MAX_SCALE, FullScreenMagnificationGestureHandler.MAX_SCALE, 1.0f};

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.accessibility.AccessibilityManagerService$5, reason: invalid class name */
    public final class AnonymousClass5 implements DialogInterface.OnClickListener {
        @Override // android.content.DialogInterface.OnClickListener
        public final void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AODStateContentObserver extends ContentObserver {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ AccessibilityManagerService this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AODStateContentObserver(int i, AccessibilityManagerService accessibilityManagerService) {
            super(new Handler());
            this.$r8$classId = i;
            switch (i) {
                case 1:
                    this.this$0 = accessibilityManagerService;
                    super(new Handler());
                    break;
                case 2:
                    this.this$0 = accessibilityManagerService;
                    super(new Handler());
                    break;
                case 3:
                    this.this$0 = accessibilityManagerService;
                    super(new Handler());
                    break;
                case 4:
                    this.this$0 = accessibilityManagerService;
                    super(new Handler());
                    break;
                default:
                    this.this$0 = accessibilityManagerService;
                    break;
            }
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z) {
            switch (this.$r8$classId) {
                case 0:
                    super.onChange(z);
                    AccessibilityManagerService.m116$$Nest$mapplyColorinversion(this.this$0);
                    break;
                case 1:
                    super.onChange(z);
                    AccessibilityManagerService accessibilityManagerService = this.this$0;
                    AccessibilityUserState userStateLocked = accessibilityManagerService.getUserStateLocked(accessibilityManagerService.mCurrentUserId);
                    DisplayTransformManager displayTransformManager = (DisplayTransformManager) LocalServices.getService(DisplayTransformManager.class);
                    ContentResolver contentResolver = this.this$0.mContext.getContentResolver();
                    int i = userStateLocked.mUserId;
                    boolean z2 = Settings.Secure.getIntForUser(contentResolver, "user_setup_complete", 0, i) != 0;
                    boolean z3 = Settings.Secure.getIntForUser(this.this$0.mContext.getContentResolver(), "accessibility_display_inversion_enabled", 0, i) != 0;
                    if (!z2) {
                        displayTransformManager.setColorMatrix(300, z3 ? AccessibilityManagerService.MATRIX_INVERT_COLOR : null);
                        break;
                    }
                    break;
                case 2:
                    super.onChange(z);
                    AccessibilityManagerService.m117$$Nest$mapplyDaltonizerSettings(this.this$0);
                    break;
                case 3:
                    super.onChange(z);
                    AccessibilityManagerService.m117$$Nest$mapplyDaltonizerSettings(this.this$0);
                    break;
                default:
                    super.onChange(z);
                    AccessibilityManagerService accessibilityManagerService2 = this.this$0;
                    if (Settings.Secure.getIntForUser(this.this$0.mContext.getContentResolver(), "user_setup_complete", 0, accessibilityManagerService2.getUserStateLocked(accessibilityManagerService2.mCurrentUserId).mUserId) != 0) {
                        this.this$0.mContext.getContentResolver().unregisterContentObserver(this.this$0.mColorInversionStateContentObserver);
                        this.this$0.mContext.getContentResolver().unregisterContentObserver(this.this$0.mDaltonizerEnabledStateContentObserver);
                        this.this$0.mContext.getContentResolver().unregisterContentObserver(this.this$0.mDaltonizerStateContentObserver);
                        this.this$0.mContext.getContentResolver().unregisterContentObserver(this.this$0.mSetupCompleteStateContentObserver);
                        break;
                    }
                    break;
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AccessibilityContentObserver extends ContentObserver {
        public final Uri mA11yAMEnableUri;
        public final Uri mA11yAMMagnificationEnableUri;
        public final Uri mAccessibilityButtonComponentIdUri;
        public final Uri mAccessibilityButtonTargetsUri;
        public final Uri mAccessibilityDirectAccessServiceIdUri;
        public final Uri mAccessibilityShortcutServiceIdUri;
        public final Uri mAccessibilitySoftKeyboardModeUri;
        public final Uri mAlwaysOnMagnificationUri;
        public final Uri mAmplifyAmbientSountEnableUri;
        public final Uri mAudioDescriptionByDefaultUri;
        public final Uri mAutoActionEnabledUri;
        public final Uri mAutoclickEnabledUri;
        public final Uri mBounceKeysEnabledUri;
        public final Uri mBounceKeysThresholdUri;
        public final Uri mColorLensEnableUri;
        public final Uri mColorLensOpacityUri;
        public final Uri mColorLensTypeUri;
        public final Uri mColorReluminoEdgeThinknessUri;
        public final Uri mColorReluminoEnableUri;
        public final Uri mColorReluminoTypeUri;
        public final Uri mCornerActionEnabledUri;
        public final Uri mEnabledAccessibilityServicesUri;
        public final Uri mHighContrastTextEnableUri;
        public final Uri mHighTextContrastUri;
        public final Uri mMagnificationCapabilityUri;
        public final Uri mMagnificationFollowTypingUri;
        public final Uri mMagnificationModeUri;
        public final Uri mMagnificationmSingleFingerTripleTapEnabledUri;
        public final Uri mNaviBarModeUri;
        public final Uri mOneHandModeActivateUri;
        public final Uri mScreenCurtainEnabledUri;
        public final Uri mShowImeWithHardKeyboardUri;
        public final Uri mSlowKeysEnabledUri;
        public final Uri mSlowKeysThresholdUri;
        public final Uri mStickyKeysEnabledUri;
        public final Uri mStickyKeysUri;
        public final Uri mTapDurationEnabledUri;
        public final Uri mTouchBlockingEnabledUri;
        public final Uri mTouchExplorationEnabledUri;
        public final Uri mTouchExplorationGrantedAccessibilityServicesUri;
        public final Uri mUserInteractiveUiTimeoutUri;
        public final Uri mUserNonInteractiveUiTimeoutUri;

        public AccessibilityContentObserver(Handler handler) {
            super(handler);
            this.mTouchExplorationEnabledUri = Settings.Secure.getUriFor("touch_exploration_enabled");
            this.mMagnificationmSingleFingerTripleTapEnabledUri = Settings.Secure.getUriFor("accessibility_display_magnification_enabled");
            Settings.Secure.getUriFor("accessibility_magnification_two_finger_triple_tap_enabled");
            this.mAutoclickEnabledUri = Settings.Secure.getUriFor("accessibility_autoclick_enabled");
            this.mAutoActionEnabledUri = Settings.Secure.getUriFor("accessibility_auto_action_type");
            this.mCornerActionEnabledUri = Settings.Secure.getUriFor("accessibility_corner_action_enabled");
            this.mEnabledAccessibilityServicesUri = Settings.Secure.getUriFor("enabled_accessibility_services");
            this.mTouchExplorationGrantedAccessibilityServicesUri = Settings.Secure.getUriFor("touch_exploration_granted_accessibility_services");
            this.mHighTextContrastUri = Settings.Secure.getUriFor("high_text_contrast_enabled");
            this.mAudioDescriptionByDefaultUri = Settings.Secure.getUriFor("enabled_accessibility_audio_description_by_default");
            this.mAccessibilitySoftKeyboardModeUri = Settings.Secure.getUriFor("accessibility_soft_keyboard_mode");
            this.mShowImeWithHardKeyboardUri = Settings.Secure.getUriFor("show_ime_with_hard_keyboard");
            this.mScreenCurtainEnabledUri = Settings.System.getUriFor("lcd_curtain");
            this.mAccessibilityShortcutServiceIdUri = Settings.Secure.getUriFor("accessibility_shortcut_target_service");
            this.mAccessibilityDirectAccessServiceIdUri = Settings.Secure.getUriFor("accessibility_direct_access_target_service");
            this.mAccessibilityButtonComponentIdUri = Settings.Secure.getUriFor("accessibility_button_target_component");
            this.mAccessibilityButtonTargetsUri = Settings.Secure.getUriFor("accessibility_button_targets");
            this.mUserNonInteractiveUiTimeoutUri = Settings.Secure.getUriFor("accessibility_non_interactive_ui_timeout_ms");
            this.mUserInteractiveUiTimeoutUri = Settings.Secure.getUriFor("accessibility_interactive_ui_timeout_ms");
            this.mMagnificationModeUri = Settings.Secure.getUriFor("accessibility_magnification_mode");
            this.mMagnificationCapabilityUri = Settings.Secure.getUriFor("accessibility_magnification_capability");
            this.mMagnificationFollowTypingUri = Settings.Secure.getUriFor("accessibility_magnification_follow_typing_enabled");
            this.mAlwaysOnMagnificationUri = Settings.Secure.getUriFor("accessibility_magnification_always_on_enabled");
            this.mTapDurationEnabledUri = Settings.Secure.getUriFor("tap_duration_enabled");
            this.mTouchBlockingEnabledUri = Settings.Secure.getUriFor("touch_blocking_enabled");
            this.mAmplifyAmbientSountEnableUri = Settings.System.getUriFor("amplify_ambient_sound");
            this.mHighContrastTextEnableUri = Settings.Secure.getUriFor("high_text_contrast_enabled");
            this.mColorLensEnableUri = Settings.Secure.getUriFor("color_lens_switch");
            this.mColorLensTypeUri = Settings.Secure.getUriFor("color_lens_type");
            this.mColorLensOpacityUri = Settings.Secure.getUriFor("color_lens_opacity");
            this.mColorReluminoEnableUri = Settings.Secure.getUriFor("relumino_switch");
            this.mColorReluminoTypeUri = Settings.Secure.getUriFor("relumino_type");
            this.mColorReluminoEdgeThinknessUri = Settings.Secure.getUriFor("relumino_edge_thickness");
            this.mStickyKeysEnabledUri = Settings.Secure.getUriFor("sticky_keys_enabled");
            this.mBounceKeysEnabledUri = Settings.Secure.getUriFor("bounce_keys_enabled");
            this.mSlowKeysEnabledUri = Settings.Secure.getUriFor("slow_keys_enabled");
            this.mStickyKeysUri = Settings.Secure.getUriFor("accessibility_sticky_keys");
            this.mBounceKeysThresholdUri = Settings.Secure.getUriFor("accessibility_bounce_keys");
            this.mSlowKeysThresholdUri = Settings.Secure.getUriFor("accessibility_slow_keys");
            this.mNaviBarModeUri = Settings.Secure.getUriFor("navigation_mode");
            this.mA11yAMEnableUri = Settings.System.getUriFor("assistant_menu");
            this.mOneHandModeActivateUri = Settings.System.getUriFor("any_screen_running");
            this.mA11yAMMagnificationEnableUri = A11yRune.getUriFor("assistant_menu_option_upperapps");
        }

        @Override // android.database.ContentObserver
        public final void onChange(boolean z, Uri uri) {
            synchronized (AccessibilityManagerService.this.mLock) {
                try {
                    AccessibilityManagerService accessibilityManagerService = AccessibilityManagerService.this;
                    AccessibilityUserState userStateLocked = accessibilityManagerService.getUserStateLocked(accessibilityManagerService.mCurrentUserId);
                    boolean z2 = true;
                    if (this.mTouchExplorationEnabledUri.equals(uri)) {
                        if (Settings.Secure.getIntForUser(AccessibilityManagerService.this.mContext.getContentResolver(), "touch_exploration_enabled", 0, userStateLocked.mUserId) != 1) {
                            z2 = false;
                        }
                        if (z2 != userStateLocked.mIsTouchExplorationEnabled) {
                            userStateLocked.mIsTouchExplorationEnabled = z2;
                            AccessibilityManagerService.this.onUserStateChangedLocked(userStateLocked, false);
                        }
                    } else if (this.mMagnificationmSingleFingerTripleTapEnabledUri.equals(uri)) {
                        if (Settings.Secure.getIntForUser(AccessibilityManagerService.this.mContext.getContentResolver(), "accessibility_display_magnification_enabled", 0, userStateLocked.mUserId) != 1) {
                            z2 = false;
                        }
                        if (z2 != userStateLocked.mIsMagnificationSingleFingerTripleTapEnabled) {
                            userStateLocked.mIsMagnificationSingleFingerTripleTapEnabled = z2;
                            AccessibilityManagerService.this.onUserStateChangedLocked(userStateLocked, false);
                        }
                    } else {
                        Flags.enableMagnificationMultipleFingerMultipleTapGesture();
                        if (this.mAutoclickEnabledUri.equals(uri)) {
                            if (Settings.Secure.getIntForUser(AccessibilityManagerService.this.mContext.getContentResolver(), "accessibility_autoclick_enabled", 0, userStateLocked.mUserId) != 1) {
                                z2 = false;
                            }
                            if (z2 != userStateLocked.mIsAutoclickEnabled) {
                                userStateLocked.mIsAutoclickEnabled = z2;
                                AccessibilityManagerService.this.onUserStateChangedLocked(userStateLocked, false);
                            }
                        } else if (this.mAutoActionEnabledUri.equals(uri)) {
                            if (Settings.Secure.getIntForUser(AccessibilityManagerService.this.mContext.getContentResolver(), "accessibility_auto_action_type", 0, userStateLocked.mUserId) == 0) {
                                z2 = false;
                            }
                            if (z2 != userStateLocked.mIsAutoActionEnabled) {
                                userStateLocked.mIsAutoActionEnabled = z2;
                                AccessibilityManagerService.this.onUserStateChangedLocked(userStateLocked, false);
                            }
                        } else if (this.mCornerActionEnabledUri.equals(uri)) {
                            if (Settings.Secure.getIntForUser(AccessibilityManagerService.this.mContext.getContentResolver(), "accessibility_corner_action_enabled", 0, userStateLocked.mUserId) == 0) {
                                z2 = false;
                            }
                            if (z2 != userStateLocked.mIsCornerActionEnabled) {
                                userStateLocked.mIsCornerActionEnabled = z2;
                                AccessibilityManagerService.this.onUserStateChangedLocked(userStateLocked, false);
                            }
                        } else if (this.mEnabledAccessibilityServicesUri.equals(uri)) {
                            if (AccessibilityManagerService.this.readEnabledAccessibilityServicesLocked(userStateLocked)) {
                                AccessibilitySecurityPolicy accessibilitySecurityPolicy = AccessibilityManagerService.this.mSecurityPolicy;
                                int i = userStateLocked.mUserId;
                                Set set = userStateLocked.mEnabledServices;
                                if (((AccessibilityManagerService) accessibilitySecurityPolicy.mAccessibilityUserManager).mCurrentUserId == i) {
                                    ArraySet arraySet = new ArraySet(set);
                                    PolicyWarningUIController policyWarningUIController = accessibilitySecurityPolicy.mPolicyWarningUIController;
                                    policyWarningUIController.getClass();
                                    policyWarningUIController.mMainHandler.sendMessage(PooledLambda.obtainMessage(new PolicyWarningUIController$$ExternalSyntheticLambda2(2, policyWarningUIController), Integer.valueOf(i), arraySet));
                                }
                                userStateLocked.removeDisabledServicesFromTemporaryStatesLocked();
                                AccessibilityManagerService.this.onUserStateChangedLocked(userStateLocked, false);
                                if (!AccessibilityManagerService.this.isScreenReaderEnabled()) {
                                    AccessibilityManagerService accessibilityManagerService2 = AccessibilityManagerService.this;
                                    if (accessibilityManagerService2.mCurtainModeIsRunning) {
                                        accessibilityManagerService2.semToggleDarkScreenMode();
                                    }
                                }
                            }
                        } else if (this.mTouchExplorationGrantedAccessibilityServicesUri.equals(uri)) {
                            if (AccessibilityManagerService.this.readTouchExplorationGrantedAccessibilityServicesLocked(userStateLocked)) {
                                AccessibilityManagerService.this.onUserStateChangedLocked(userStateLocked, false);
                            }
                        } else if (this.mScreenCurtainEnabledUri.equals(uri)) {
                            if (Settings.System.getIntForUser(AccessibilityManagerService.this.mContext.getContentResolver(), "lcd_curtain", 0, -2) != 1) {
                                AccessibilityManagerService accessibilityManagerService3 = AccessibilityManagerService.this;
                                if (accessibilityManagerService3.mCurtainModeIsRunning) {
                                    accessibilityManagerService3.semToggleDarkScreenMode();
                                }
                            }
                        } else if (this.mHighTextContrastUri.equals(uri)) {
                            if (Settings.Secure.getIntForUser(AccessibilityManagerService.this.mContext.getContentResolver(), "high_text_contrast_enabled", 0, userStateLocked.mUserId) != 1) {
                                z2 = false;
                            }
                            if (z2 != userStateLocked.mIsTextHighContrastEnabled) {
                                userStateLocked.mIsTextHighContrastEnabled = z2;
                                AccessibilityManagerService.this.onUserStateChangedLocked(userStateLocked, false);
                            }
                        } else if (this.mAudioDescriptionByDefaultUri.equals(uri)) {
                            if (Settings.Secure.getIntForUser(AccessibilityManagerService.this.mContext.getContentResolver(), "enabled_accessibility_audio_description_by_default", 0, userStateLocked.mUserId) != 1) {
                                z2 = false;
                            }
                            if (z2 != userStateLocked.mIsAudioDescriptionByDefaultRequested) {
                                userStateLocked.mIsAudioDescriptionByDefaultRequested = z2;
                                AccessibilityManagerService.this.onUserStateChangedLocked(userStateLocked, false);
                            }
                        } else {
                            if (!this.mAccessibilitySoftKeyboardModeUri.equals(uri) && !this.mShowImeWithHardKeyboardUri.equals(uri)) {
                                if (this.mAccessibilityShortcutServiceIdUri.equals(uri)) {
                                    if (AccessibilityManagerService.this.readAccessibilityShortcutKeySettingLocked(userStateLocked)) {
                                        AccessibilityManagerService.this.onUserStateChangedLocked(userStateLocked, false);
                                    }
                                } else if (this.mAccessibilityButtonComponentIdUri.equals(uri)) {
                                    if (AccessibilityManagerService.this.readAccessibilityButtonTargetComponentLocked(userStateLocked)) {
                                        AccessibilityManagerService.this.onUserStateChangedLocked(userStateLocked, false);
                                    }
                                } else if (!this.mAccessibilityButtonTargetsUri.equals(uri)) {
                                    if (!this.mUserNonInteractiveUiTimeoutUri.equals(uri) && !this.mUserInteractiveUiTimeoutUri.equals(uri)) {
                                        if (this.mMagnificationModeUri.equals(uri)) {
                                            if (AccessibilityManagerService.this.readMagnificationModeForDefaultDisplayLocked(userStateLocked)) {
                                                AccessibilityManagerService accessibilityManagerService4 = AccessibilityManagerService.this;
                                                accessibilityManagerService4.updateMagnificationModeChangeSettingsLocked(userStateLocked, accessibilityManagerService4.getDisplayId());
                                            }
                                        } else if (this.mMagnificationCapabilityUri.equals(uri)) {
                                            AccessibilityManagerService accessibilityManagerService5 = AccessibilityManagerService.this;
                                            int intForUser = Settings.Secure.getIntForUser(accessibilityManagerService5.mContext.getContentResolver(), "accessibility_magnification_capability", 1, userStateLocked.mUserId);
                                            if (intForUser != userStateLocked.mMagnificationCapabilities) {
                                                userStateLocked.mMagnificationCapabilities = intForUser;
                                                accessibilityManagerService5.mMagnificationController.mMagnificationCapabilities = intForUser;
                                                AccessibilityManagerService.this.updateMagnificationCapabilitiesSettingsChangeLocked(userStateLocked);
                                            }
                                        } else if (this.mMagnificationFollowTypingUri.equals(uri)) {
                                            AccessibilityManagerService.this.readMagnificationFollowTypingLocked(userStateLocked);
                                        } else if (this.mAlwaysOnMagnificationUri.equals(uri)) {
                                            AccessibilityManagerService.this.readAlwaysOnMagnificationLocked(userStateLocked);
                                        } else if (this.mTapDurationEnabledUri.equals(uri)) {
                                            if (Settings.Secure.getIntForUser(AccessibilityManagerService.this.mContext.getContentResolver(), "tap_duration_enabled", 0, userStateLocked.mUserId) != 1) {
                                                z2 = false;
                                            }
                                            if (z2 != userStateLocked.mIsTapDurationEnabled) {
                                                userStateLocked.mIsTapDurationEnabled = z2;
                                                AccessibilityManagerService.this.onUserStateChangedLocked(userStateLocked, false);
                                            }
                                        } else if (this.mTouchBlockingEnabledUri.equals(uri)) {
                                            if (Settings.Secure.getIntForUser(AccessibilityManagerService.this.mContext.getContentResolver(), "touch_blocking_enabled", 0, userStateLocked.mUserId) != 1) {
                                                z2 = false;
                                            }
                                            if (z2 != userStateLocked.mIsTouchBlockingEnabled) {
                                                userStateLocked.mIsTouchBlockingEnabled = z2;
                                                AccessibilityManagerService.this.onUserStateChangedLocked(userStateLocked, false);
                                            }
                                        } else if (this.mStickyKeysEnabledUri.equals(uri)) {
                                            if (Settings.Secure.getIntForUser(AccessibilityManagerService.this.mContext.getContentResolver(), "accessibility_sticky_keys", 0, userStateLocked.mUserId) != 1) {
                                                z2 = false;
                                            }
                                            if (z2 != userStateLocked.mIsStickyKeysEnabled) {
                                                userStateLocked.mIsStickyKeysEnabled = z2;
                                                AccessibilityManagerService.this.onUserStateChangedLocked(userStateLocked, false);
                                            }
                                        } else if (this.mBounceKeysEnabledUri.equals(uri)) {
                                            if (Settings.Secure.getIntForUser(AccessibilityManagerService.this.mContext.getContentResolver(), "accessibility_bounce_keys", 0, userStateLocked.mUserId) == 0) {
                                                z2 = false;
                                            }
                                            if (z2 != userStateLocked.mIsBounceKeysEnabled) {
                                                userStateLocked.mIsBounceKeysEnabled = z2;
                                                AccessibilityManagerService.this.onUserStateChangedLocked(userStateLocked, false);
                                            }
                                        } else if (this.mSlowKeysEnabledUri.equals(uri)) {
                                            if (Settings.Secure.getIntForUser(AccessibilityManagerService.this.mContext.getContentResolver(), "accessibility_slow_keys", 0, userStateLocked.mUserId) == 0) {
                                                z2 = false;
                                            }
                                            if (z2 != userStateLocked.mIsSlowKeysEnabled) {
                                                userStateLocked.mIsSlowKeysEnabled = z2;
                                                AccessibilityManagerService.this.onUserStateChangedLocked(userStateLocked, false);
                                            }
                                        } else if (this.mNaviBarModeUri.equals(uri)) {
                                            if (AccessibilityManagerService.this.readGestureNaviBarModeSettingsLocked(userStateLocked)) {
                                                AccessibilityManagerService.this.onUserStateChangedLocked(userStateLocked, false);
                                            }
                                        } else if (!this.mAccessibilityDirectAccessServiceIdUri.equals(uri)) {
                                            if (!this.mA11yAMEnableUri.equals(uri) && !this.mA11yAMMagnificationEnableUri.equals(uri)) {
                                                if (!this.mOneHandModeActivateUri.equals(uri)) {
                                                    if (!this.mColorReluminoEnableUri.equals(uri) && !this.mColorReluminoTypeUri.equals(uri) && !this.mColorReluminoEdgeThinknessUri.equals(uri)) {
                                                        if (!this.mColorLensEnableUri.equals(uri)) {
                                                            if (!this.mColorLensTypeUri.equals(uri) && !this.mColorLensOpacityUri.equals(uri)) {
                                                                Slog.d("AccessibilityManagerService", "onChange():handle nothing - " + uri);
                                                            }
                                                            if (A11yRune.A11Y_COLOR_BOOL_SUPPORT_COLOR_FILTER_MDNIE_HW) {
                                                                AccessibilityManagerService accessibilityManagerService6 = AccessibilityManagerService.this;
                                                                AccessibilityManagerService.m125$$Nest$mseMdnieHWColorLensState(accessibilityManagerService6.mContext, accessibilityManagerService6);
                                                            } else {
                                                                AccessibilityManagerService.this.updateColorLensValue();
                                                            }
                                                        } else if (A11yRune.A11Y_COLOR_BOOL_SUPPORT_COLOR_FILTER_MDNIE_HW) {
                                                            AccessibilityManagerService accessibilityManagerService7 = AccessibilityManagerService.this;
                                                            AccessibilityManagerService.m125$$Nest$mseMdnieHWColorLensState(accessibilityManagerService7.mContext, accessibilityManagerService7);
                                                        } else {
                                                            AccessibilityManagerService.m118$$Nest$menableColorLens(AccessibilityManagerService.this);
                                                        }
                                                    }
                                                    AccessibilityManagerService.m119$$Nest$menableColorRelumino(AccessibilityManagerService.this);
                                                } else if (Settings.System.getIntForUser(AccessibilityManagerService.this.mContext.getContentResolver(), "any_screen_running", 0, userStateLocked.mUserId) == 0) {
                                                    AccessibilityManagerService accessibilityManagerService8 = AccessibilityManagerService.this;
                                                    accessibilityManagerService8.mHasInputFilter = false;
                                                    accessibilityManagerService8.mMainHandler.sendMessage(PooledLambda.obtainMessage(new AccessibilityManagerService$$ExternalSyntheticLambda9(8), accessibilityManagerService8, userStateLocked));
                                                }
                                            }
                                            if (AccessibilityManagerService.this.readAMEnabledSettingLocked(userStateLocked)) {
                                                AccessibilityManagerService.this.onUserStateChangedLocked(userStateLocked, false);
                                            }
                                        } else if (AccessibilityManagerService.this.readAccessibilityDirectAccessSettingLocked(userStateLocked)) {
                                            AccessibilityManagerService.this.onUserStateChangedLocked(userStateLocked, false);
                                        }
                                    }
                                    AccessibilityManagerService.this.readUserRecommendedUiTimeoutSettingsLocked(userStateLocked);
                                } else if (AccessibilityManagerService.this.readAccessibilityButtonTargetsLocked(userStateLocked)) {
                                    AccessibilityManagerService.this.onUserStateChangedLocked(userStateLocked, false);
                                }
                            }
                            userStateLocked.reconcileSoftKeyboardModeWithSettingsLocked();
                        }
                    }
                } finally {
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AccessibilityDisplayListener implements DisplayManager.DisplayListener {
        public final DisplayManager mDisplayManager;
        public final ArrayList mDisplaysList;
        public final int mSystemUiUid;

        public AccessibilityDisplayListener(Context context, MainHandler mainHandler) {
            ArrayList arrayList = new ArrayList();
            this.mDisplaysList = arrayList;
            this.mSystemUiUid = 0;
            boolean z = mainHandler.getLooper() == Looper.getMainLooper();
            if (Build.IS_USERDEBUG || Build.IS_ENG) {
                Preconditions.checkArgument(z, "AccessibilityDisplayListener must use the main handler");
            } else if (!z) {
                Slog.e("AccessibilityManagerService", "AccessibilityDisplayListener must use the main handler");
            }
            DisplayManager displayManager = (DisplayManager) context.getSystemService("display");
            this.mDisplayManager = displayManager;
            displayManager.registerDisplayListener(this, mainHandler);
            Display[] displays = displayManager.getDisplays("com.samsung.android.hardware.display.category.BUILTIN");
            synchronized (AccessibilityManagerService.this.mLock) {
                try {
                    arrayList.clear();
                    for (Display display : displays) {
                        if (isValidDisplay(display)) {
                            this.mDisplaysList.add(display);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            PackageManagerInternal packageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
            if (packageManagerInternal != null) {
                this.mSystemUiUid = packageManagerInternal.getPackageUid(packageManagerInternal.getSystemUiServiceComponent().getPackageName(), 1048576L, AccessibilityManagerService.this.mCurrentUserId);
            }
        }

        public final void dump(PrintWriter printWriter) {
            AccessibilityManagerService$$ExternalSyntheticOutline0.m(BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(printWriter, "Accessibility Display Listener:", "    SystemUI uid: "), this.mSystemUiUid, printWriter);
            int size = this.mDisplaysList.size();
            printWriter.printf("    %d valid display%s: ", Integer.valueOf(size), size == 1 ? "" : "s");
            for (int i = 0; i < size; i++) {
                printWriter.print(((Display) this.mDisplaysList.get(i)).getDisplayId());
                if (i < size - 1) {
                    printWriter.print(", ");
                }
            }
            printWriter.println();
        }

        public final boolean isValidDisplay(Display display) {
            if (display == null || display.getType() == 4) {
                return false;
            }
            return display.getType() != 5 || (display.getFlags() & 4) == 0 || display.getOwnerUid() == this.mSystemUiUid;
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayAdded(int i) {
            ArrayList arrayList;
            boolean isCurrentThread = Looper.getMainLooper().isCurrentThread();
            if (Build.IS_USERDEBUG || Build.IS_ENG) {
                Preconditions.checkArgument(isCurrentThread, "onDisplayAdded must be called from the main thread");
            } else if (!isCurrentThread) {
                Slog.e("AccessibilityManagerService", "onDisplayAdded must be called from the main thread");
            }
            Display display = this.mDisplayManager.getDisplay(i);
            if (isValidDisplay(display)) {
                synchronized (AccessibilityManagerService.this.mLock) {
                    this.mDisplaysList.add(display);
                    AccessibilityManagerService accessibilityManagerService = AccessibilityManagerService.this;
                    accessibilityManagerService.mA11yOverlayLayers.put(i, accessibilityManagerService.mWindowManagerService.getA11yOverlayLayer(i));
                    AccessibilityInputFilter accessibilityInputFilter = AccessibilityManagerService.this.mInputFilter;
                    if (accessibilityInputFilter != null && accessibilityInputFilter.mInstalled) {
                        accessibilityInputFilter.resetStreamStateForDisplay(display.getDisplayId());
                        accessibilityInputFilter.enableFeaturesForDisplay(display);
                    }
                    AccessibilityManagerService accessibilityManagerService2 = AccessibilityManagerService.this;
                    AccessibilityUserState userStateLocked = accessibilityManagerService2.getUserStateLocked(accessibilityManagerService2.mCurrentUserId);
                    arrayList = new ArrayList(userStateLocked.mBoundServices);
                    AccessibilityManagerService.this.updateMagnificationLocked(userStateLocked);
                    AccessibilityManagerService.this.updateWindowsForAccessibilityCallbackLocked(userStateLocked);
                    AccessibilityManagerService.this.notifyClearAccessibilityCacheLocked();
                }
                if (i != 0) {
                    for (int i2 = 0; i2 < arrayList.size(); i2++) {
                        ((AccessibilityServiceConnection) arrayList.get(i2)).addWindowTokenForDisplay(i);
                    }
                }
            }
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayChanged(int i) {
            AccessibilityManagerService.m116$$Nest$mapplyColorinversion(AccessibilityManagerService.this);
        }

        @Override // android.hardware.display.DisplayManager.DisplayListener
        public final void onDisplayRemoved(int i) {
            boolean isCurrentThread = Looper.getMainLooper().isCurrentThread();
            if (Build.IS_USERDEBUG || Build.IS_ENG) {
                Preconditions.checkArgument(isCurrentThread, "onDisplayRemoved must be called from the main thread");
            } else if (!isCurrentThread) {
                Slog.e("AccessibilityManagerService", "onDisplayRemoved must be called from the main thread");
            }
            synchronized (AccessibilityManagerService.this.mLock) {
                for (int i2 = 0; i2 < this.mDisplaysList.size(); i2++) {
                    try {
                        if (((Display) this.mDisplaysList.get(i2)).getDisplayId() == i) {
                            this.mDisplaysList.remove(i2);
                            AccessibilityManagerService.this.mA11yOverlayLayers.remove(i);
                            AccessibilityInputFilter accessibilityInputFilter = AccessibilityManagerService.this.mInputFilter;
                            if (accessibilityInputFilter != null && accessibilityInputFilter.mInstalled) {
                                accessibilityInputFilter.disableFeaturesForDisplay(i);
                                accessibilityInputFilter.resetStreamStateForDisplay(i);
                            }
                            AccessibilityManagerService accessibilityManagerService = AccessibilityManagerService.this;
                            AccessibilityUserState userStateLocked = accessibilityManagerService.getUserStateLocked(accessibilityManagerService.mCurrentUserId);
                            if (i != 0) {
                                ArrayList arrayList = userStateLocked.mBoundServices;
                                for (int i3 = 0; i3 < arrayList.size(); i3++) {
                                    ((AccessibilityServiceConnection) arrayList.get(i3)).onDisplayRemoved(i);
                                }
                            }
                            MagnificationController magnificationController = AccessibilityManagerService.this.mMagnificationController;
                            synchronized (magnificationController.mLock) {
                                FullScreenMagnificationController fullScreenMagnificationController = magnificationController.mFullScreenMagnificationController;
                                if (fullScreenMagnificationController != null) {
                                    synchronized (fullScreenMagnificationController.mLock) {
                                        fullScreenMagnificationController.unregisterLocked(i, true);
                                    }
                                }
                                MagnificationConnectionManager magnificationConnectionManager = magnificationController.mMagnificationConnectionManager;
                                if (magnificationConnectionManager != null) {
                                    magnificationConnectionManager.disableWindowMagnification(i, true);
                                }
                                magnificationController.mAccessibilityCallbacksDelegateArray.delete(i);
                                magnificationController.mCurrentMagnificationModeArray.delete(i);
                                magnificationController.mLastMagnificationActivatedModeArray.delete(i);
                                magnificationController.mIsImeVisibleArray.delete(i);
                            }
                            MagnificationScaleProvider magnificationScaleProvider = magnificationController.mScaleProvider;
                            synchronized (magnificationScaleProvider.mLock) {
                                try {
                                    for (int size = magnificationScaleProvider.mUsersScales.size() - 1; size >= 0; size--) {
                                        ((SparseArray) magnificationScaleProvider.mUsersScales.get(size)).remove(i);
                                    }
                                } finally {
                                }
                            }
                            AccessibilityManagerService.this.mA11yWindowManager.stopTrackingWindows(i);
                            return;
                        }
                    } finally {
                    }
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Client {
        public final IAccessibilityManagerClient mCallback;
        public int mDeviceId;
        public int mLastSentRelevantEventTypes;
        public final String[] mPackageNames;
        public final int mUid;

        public Client(AccessibilityManagerService accessibilityManagerService, IAccessibilityManagerClient iAccessibilityManagerClient, int i, AccessibilityUserState accessibilityUserState, int i2) {
            this.mDeviceId = 0;
            this.mCallback = iAccessibilityManagerClient;
            this.mPackageNames = accessibilityManagerService.mPackageManager.getPackagesForUid(i);
            this.mUid = i;
            this.mDeviceId = i2;
            synchronized (accessibilityManagerService.mLock) {
                try {
                    if (accessibilityManagerService.mProxyManager.isProxyedDeviceId(i2)) {
                        this.mLastSentRelevantEventTypes = accessibilityManagerService.mProxyManager.computeRelevantEventTypesLocked(this);
                    } else {
                        this.mLastSentRelevantEventTypes = accessibilityManagerService.computeRelevantEventTypesLocked(accessibilityUserState, this);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class InteractionBridge {
        public final AccessibilityInteractionClient mClient;
        public final int mConnectionId;
        public final Display mDefaultDisplay;

        /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
        /* renamed from: com.android.server.accessibility.AccessibilityManagerService$InteractionBridge$1, reason: invalid class name */
        public final class AnonymousClass1 extends AccessibilityServiceConnection {
            @Override // com.android.server.accessibility.AbstractAccessibilityServiceConnection
            public final boolean supportsFlagForNotImportantViews(AccessibilityServiceInfo accessibilityServiceInfo) {
                return true;
            }
        }

        public InteractionBridge() {
            AccessibilityUserState userStateLocked;
            ComponentName componentName = new ComponentName("com.android.server.accessibility", "InteractionBridge");
            AccessibilityServiceInfo accessibilityServiceInfo = new AccessibilityServiceInfo();
            accessibilityServiceInfo.setCapabilities(1);
            accessibilityServiceInfo.flags |= 66;
            accessibilityServiceInfo.setAccessibilityTool(true);
            synchronized (AccessibilityManagerService.this.mLock) {
                userStateLocked = AccessibilityManagerService.this.getUserStateLocked(AccessibilityManagerService.this.mCurrentUserId);
            }
            Context context = AccessibilityManagerService.this.mContext;
            int i = AccessibilityManagerService.sIdCounter;
            AccessibilityManagerService.sIdCounter = i + 1;
            AnonymousClass1 anonymousClass1 = new AnonymousClass1(userStateLocked, context, componentName, accessibilityServiceInfo, i, AccessibilityManagerService.this.mMainHandler, AccessibilityManagerService.this.mLock, AccessibilityManagerService.this.mSecurityPolicy, AccessibilityManagerService.this, AccessibilityManagerService.this.mTraceManager, AccessibilityManagerService.this.mWindowManagerService, AccessibilityManagerService.this.getSystemActionPerformer(), AccessibilityManagerService.this.mA11yWindowManager, AccessibilityManagerService.this.mActivityTaskManagerService);
            int i2 = anonymousClass1.mId;
            this.mConnectionId = i2;
            this.mClient = AccessibilityInteractionClient.getInstance(AccessibilityManagerService.this.mContext);
            AccessibilityInteractionClient.addConnection(i2, anonymousClass1, false);
            this.mDefaultDisplay = ((DisplayManager) AccessibilityManagerService.this.mContext.getSystemService("display")).getDisplay(0);
        }

        public final AccessibilityNodeInfo getAccessibilityFocusNotLocked() {
            synchronized (AccessibilityManagerService.this.mLock) {
                try {
                    int defaultFocus = AccessibilityManagerService.this.mA11yWindowManager.getDefaultFocus(2);
                    if (defaultFocus == -1) {
                        return null;
                    }
                    return this.mClient.findFocus(this.mConnectionId, defaultFocus, AccessibilityNodeInfo.ROOT_NODE_ID, 2);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Lifecycle extends SystemService {
        public final AccessibilityManagerService mService;

        public Lifecycle(Context context) {
            super(context);
            this.mService = new AccessibilityManagerService(context);
        }

        @Override // com.android.server.SystemService
        public final void onBootPhase(int i) {
            AccessibilityManagerService accessibilityManagerService = this.mService;
            if (i != 500) {
                boolean z = AccessibilityManagerService.SEC_DEBUG;
            } else if (accessibilityManagerService.mPackageManager.hasSystemFeature("android.software.app_widgets")) {
                accessibilityManagerService.mSecurityPolicy.mAppWidgetService = (AppWidgetManagerInternal) LocalServices.getService(AppWidgetManagerInternal.class);
            }
            if (i == 600) {
                accessibilityManagerService.setNonA11yToolNotificationToMatchSafetyCenter();
            } else {
                accessibilityManagerService.getClass();
            }
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r1v0, types: [android.os.IBinder, com.android.server.accessibility.AccessibilityManagerService] */
        @Override // com.android.server.SystemService
        public final void onStart() {
            ?? r1 = this.mService;
            LocalServices.addService(AccessibilityManagerInternal.class, new LocalServiceImpl(r1));
            publishBinderService("accessibility", r1);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class LocalServiceImpl extends AccessibilityManagerInternal {
        public final AccessibilityManagerService mService;

        public LocalServiceImpl(AccessibilityManagerService accessibilityManagerService) {
            this.mService = accessibilityManagerService;
        }

        @Override // com.android.server.AccessibilityManagerInternal
        public final void bindInput() {
            AccessibilityManagerService accessibilityManagerService = this.mService;
            accessibilityManagerService.mMainHandler.sendMessage(PooledLambda.obtainMessage(new AccessibilityManagerService$$ExternalSyntheticLambda38(1), accessibilityManagerService));
        }

        @Override // com.android.server.AccessibilityManagerInternal
        public final void createImeSession(ArraySet arraySet) {
            AccessibilityManagerService accessibilityManagerService = this.mService;
            accessibilityManagerService.mMainHandler.sendMessage(PooledLambda.obtainMessage(new AccessibilityManagerService$$ExternalSyntheticLambda9(12), accessibilityManagerService, arraySet));
        }

        @Override // com.android.server.AccessibilityManagerInternal
        public final boolean isTouchExplorationEnabled(int i) {
            boolean z;
            synchronized (this.mService.mLock) {
                z = this.mService.getUserStateLocked(i).mIsTouchExplorationEnabled;
            }
            return z;
        }

        @Override // com.android.server.AccessibilityManagerInternal
        public final void performSystemAction() {
            boolean z = AccessibilityManagerService.SEC_DEBUG;
            this.mService.getSystemActionPerformer().performSystemAction(14);
        }

        @Override // com.android.server.AccessibilityManagerInternal
        public final void setImeSessionEnabled(SparseArray sparseArray, boolean z) {
            AccessibilityManagerService accessibilityManagerService = this.mService;
            accessibilityManagerService.mMainHandler.sendMessage(PooledLambda.obtainMessage(new AccessibilityManagerService$$ExternalSyntheticLambda12(8), accessibilityManagerService, sparseArray, Boolean.valueOf(z)));
        }

        @Override // com.android.server.AccessibilityManagerInternal
        public final void startInput(IRemoteAccessibilityInputConnection iRemoteAccessibilityInputConnection, EditorInfo editorInfo, boolean z) {
            AccessibilityManagerService accessibilityManagerService = this.mService;
            accessibilityManagerService.mMainHandler.sendMessage(PooledLambda.obtainMessage(new AccessibilityManagerService$$ExternalSyntheticLambda14(2), accessibilityManagerService, iRemoteAccessibilityInputConnection, editorInfo, Boolean.valueOf(z)));
        }

        @Override // com.android.server.AccessibilityManagerInternal
        public final void unbindInput() {
            AccessibilityManagerService accessibilityManagerService = this.mService;
            accessibilityManagerService.mMainHandler.sendMessage(PooledLambda.obtainMessage(new AccessibilityManagerService$$ExternalSyntheticLambda38(2), accessibilityManagerService));
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class MainHandler extends Handler {
        public MainHandler(Looper looper) {
            super(looper);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            AccessibilityInputFilter accessibilityInputFilter;
            if (message.what == 8) {
                KeyEvent keyEvent = (KeyEvent) message.obj;
                int i = message.arg1;
                synchronized (AccessibilityManagerService.this.mLock) {
                    try {
                        AccessibilityManagerService accessibilityManagerService = AccessibilityManagerService.this;
                        if (accessibilityManagerService.mHasInputFilter && (accessibilityInputFilter = accessibilityManagerService.mInputFilter) != null) {
                            accessibilityInputFilter.sendInputEvent(keyEvent, i);
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                keyEvent.recycle();
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public class ManagerPackageMonitor extends PackageMonitor {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final AccessibilityManagerService mManagerService;

        public ManagerPackageMonitor(AccessibilityManagerService accessibilityManagerService) {
            super(true);
            this.mManagerService = accessibilityManagerService;
        }

        public final boolean onHandleForceStop(Intent intent, String[] strArr, int i, boolean z) {
            if (this.mManagerService.mTraceManager.isA11yTracingEnabledForTypes(32768L)) {
                AccessibilityTraceManager accessibilityTraceManager = this.mManagerService.mTraceManager;
                StringBuilder sb = new StringBuilder("intent=");
                sb.append(intent);
                sb.append(";packages=");
                AccessibilityManagerService$$ExternalSyntheticOutline0.m(i, Arrays.toString(strArr), ";uid=", ";doit=", sb);
                sb.append(z);
                accessibilityTraceManager.logTrace("AccessibilityManagerService.PM.onHandleForceStop", 32768L, sb.toString());
            }
            synchronized (this.mManagerService.getLock()) {
                try {
                    int changingUserId = getChangingUserId();
                    AccessibilityManagerService accessibilityManagerService = this.mManagerService;
                    if (changingUserId != accessibilityManagerService.mCurrentUserId) {
                        return false;
                    }
                    AccessibilityUserState userStateLocked = accessibilityManagerService.getUserStateLocked(changingUserId);
                    Flags.managerPackageMonitorLogicFix();
                    if (!z) {
                        return userStateLocked.mEnabledServices.stream().anyMatch(new AccessibilityManagerService$$ExternalSyntheticLambda17(3, strArr));
                    }
                    if (this.mManagerService.onPackagesForceStoppedLocked(strArr, userStateLocked)) {
                        this.mManagerService.onUserStateChangedLocked(userStateLocked, false);
                    }
                    return false;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final boolean onPackageChanged(String str, int i, String[] strArr) {
            return true;
        }

        public final void onPackageRemoved(String str, int i) {
            if (this.mManagerService.mTraceManager.isA11yTracingEnabledForTypes(32768L)) {
                this.mManagerService.mTraceManager.logTrace("AccessibilityManagerService.PM.onPackageRemoved", 32768L, SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0.m(i, "packageName=", str, ";uid="));
            }
            synchronized (this.mManagerService.getLock()) {
                try {
                    int changingUserId = getChangingUserId();
                    AccessibilityManagerService accessibilityManagerService = this.mManagerService;
                    if (changingUserId != accessibilityManagerService.mCurrentUserId) {
                        return;
                    }
                    AccessibilityManagerService.m120$$Nest$monPackageRemovedLocked(accessibilityManagerService, str);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void onPackageUpdateFinished(String str, int i) {
            boolean z;
            if (this.mManagerService.mTraceManager.isA11yTracingEnabledForTypes(32768L)) {
                this.mManagerService.mTraceManager.logTrace("AccessibilityManagerService.PM.onPackageUpdateFinished", 32768L, SensitiveContentProtectionManagerService$SensitiveContentProtectionManagerServiceBinder$$ExternalSyntheticOutline0.m(i, "packageName=", str, ";uid="));
            }
            int changingUserId = getChangingUserId();
            List parseAccessibilityServiceInfos = this.mManagerService.parseAccessibilityServiceInfos(changingUserId);
            AccessibilityManagerService accessibilityManagerService = this.mManagerService;
            List installedAccessibilityShortcutListAsUser = AccessibilityManager.getInstance(accessibilityManagerService.mContext).getInstalledAccessibilityShortcutListAsUser(accessibilityManagerService.mContext, changingUserId);
            synchronized (this.mManagerService.getLock()) {
                try {
                    AccessibilityManagerService accessibilityManagerService2 = this.mManagerService;
                    if (changingUserId != accessibilityManagerService2.mCurrentUserId) {
                        return;
                    }
                    AccessibilityUserState userStateLocked = accessibilityManagerService2.getUserStateLocked(changingUserId);
                    if (!userStateLocked.mBindingServices.removeIf(new AccessibilityManagerService$$ExternalSyntheticLambda7(str, 2)) && !userStateLocked.mCrashedServices.removeIf(new AccessibilityManagerService$$ExternalSyntheticLambda7(str, 3))) {
                        z = false;
                        ((ArrayList) userStateLocked.mInstalledServices).clear();
                        boolean readConfigurationForUserStateLocked = this.mManagerService.readConfigurationForUserStateLocked(userStateLocked, parseAccessibilityServiceInfos, installedAccessibilityShortcutListAsUser);
                        if (!z || readConfigurationForUserStateLocked) {
                            this.mManagerService.onUserStateChangedLocked(userStateLocked, false);
                        }
                        this.mManagerService.migrateAccessibilityButtonSettingsIfNecessaryLocked(userStateLocked, str, 0);
                    }
                    z = true;
                    ((ArrayList) userStateLocked.mInstalledServices).clear();
                    boolean readConfigurationForUserStateLocked2 = this.mManagerService.readConfigurationForUserStateLocked(userStateLocked, parseAccessibilityServiceInfos, installedAccessibilityShortcutListAsUser);
                    if (!z) {
                    }
                    this.mManagerService.onUserStateChangedLocked(userStateLocked, false);
                    this.mManagerService.migrateAccessibilityButtonSettingsIfNecessaryLocked(userStateLocked, str, 0);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }

        public final void onSomePackagesChanged() {
            if (this.mManagerService.mTraceManager.isA11yTracingEnabledForTypes(32768L)) {
                this.mManagerService.mTraceManager.logTrace("AccessibilityManagerService.PM.onSomePackagesChanged", 32768L);
            }
            int changingUserId = getChangingUserId();
            List parseAccessibilityServiceInfos = this.mManagerService.parseAccessibilityServiceInfos(changingUserId);
            AccessibilityManagerService accessibilityManagerService = this.mManagerService;
            List installedAccessibilityShortcutListAsUser = AccessibilityManager.getInstance(accessibilityManagerService.mContext).getInstalledAccessibilityShortcutListAsUser(accessibilityManagerService.mContext, changingUserId);
            synchronized (this.mManagerService.getLock()) {
                try {
                    if (changingUserId != this.mManagerService.mCurrentUserId) {
                        return;
                    }
                    Flags.skipPackageChangeBeforeUserSwitch();
                    AccessibilityManagerService accessibilityManagerService2 = this.mManagerService;
                    AccessibilityUserState userStateLocked = accessibilityManagerService2.getUserStateLocked(accessibilityManagerService2.mCurrentUserId);
                    ((ArrayList) userStateLocked.mInstalledServices).clear();
                    if (accessibilityManagerService2.readConfigurationForUserStateLocked(userStateLocked, parseAccessibilityServiceInfos, installedAccessibilityShortcutListAsUser)) {
                        accessibilityManagerService2.onUserStateChangedLocked(userStateLocked, false);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SendWindowStateChangedEventRunnable implements Runnable {
        public final AccessibilityEvent mPendingEvent;
        public final int mWindowId;

        public SendWindowStateChangedEventRunnable(AccessibilityEvent accessibilityEvent) {
            this.mPendingEvent = accessibilityEvent;
            this.mWindowId = accessibilityEvent.getWindowId();
        }

        @Override // java.lang.Runnable
        public final void run() {
            synchronized (AccessibilityManagerService.this.mLock) {
                Slog.w("AccessibilityManagerService", " wait for adding window timeout: " + this.mWindowId);
                ((ArrayList) AccessibilityManagerService.this.mSendWindowStateChangedEventRunnables).remove(this);
                AccessibilityManagerService.this.dispatchAccessibilityEventLocked(this.mPendingEvent);
            }
        }
    }

    /* renamed from: $r8$lambda$qVXwVz5kwbGQqyOhKn52nbOV-WI, reason: not valid java name */
    public static void m115$r8$lambda$qVXwVz5kwbGQqyOhKn52nbOVWI(AccessibilityManagerService accessibilityManagerService, int i, int i2, String str) {
        List accessibilityShortcutTargetsInternal = accessibilityManagerService.getAccessibilityShortcutTargetsInternal(i2);
        ArrayList arrayList = (ArrayList) accessibilityShortcutTargetsInternal;
        if (arrayList.isEmpty()) {
            AnyMotionDetector$$ExternalSyntheticOutline0.m(i2, "No target to perform shortcut, shortcutType=", "AccessibilityManagerService");
            return;
        }
        if (str != null && !AccessibilityUserState.doesShortcutTargetsStringContain(str, accessibilityShortcutTargetsInternal)) {
            Slog.v("AccessibilityManagerService", "Perform shortcut failed, invalid target name:".concat(str));
            str = null;
        }
        if (Settings.Secure.getIntForUser(accessibilityManagerService.mContext.getContentResolver(), "accessibility_button_mode", 0, -2) != 1 || AccessibilityUtils.isDexMode(accessibilityManagerService.mContext)) {
            str = (String) arrayList.get(0);
            if (arrayList.size() == 1 && AccessibilityUtils.needToShowToast(accessibilityManagerService.mContext, str, accessibilityManagerService.getAccessibilityTargetLabel(i2, str))) {
                return;
            }
            if (arrayList.size() > 1) {
                accessibilityManagerService.showAccessibilityTargetsSelection(i, i2);
                return;
            }
        } else {
            if (str == null) {
                if (arrayList.size() > 1) {
                    accessibilityManagerService.showAccessibilityTargetsSelection(i, i2);
                    return;
                }
                str = (String) arrayList.get(0);
            }
            if (AccessibilityUtils.needToShowToast(accessibilityManagerService.mContext, str, accessibilityManagerService.getAccessibilityTargetLabel(i2, str))) {
                return;
            }
        }
        A11yLogger.insertShortcutSaLog(accessibilityManagerService.mContext, i2, str);
        if (str.equals("com.android.server.accessibility.MagnificationController")) {
            AccessibilityStatsLogUtils.logAccessibilityShortcutActivated(accessibilityManagerService.mContext, AccessibilityShortcutController.MAGNIFICATION_COMPONENT_NAME, i2, !accessibilityManagerService.mMagnificationController.getFullScreenMagnificationController().isActivated(i));
            accessibilityManagerService.sendAccessibilityButtonToInputFilter(i);
            return;
        }
        ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
        if (unflattenFromString == null) {
            Slog.d("AccessibilityManagerService", "Perform shortcut failed, invalid target name:".concat(str));
            return;
        }
        if (accessibilityManagerService.performAccessibilityFrameworkFeature(i2, unflattenFromString)) {
            AccessibilityUtils.updateProfile(accessibilityManagerService.mContext, str);
            return;
        }
        if (accessibilityManagerService.performAccessibilityShortcutTargetActivity(i, unflattenFromString)) {
            AccessibilityUtils.updateProfile(accessibilityManagerService.mContext, str);
            AccessibilityStatsLogUtils.logAccessibilityShortcutActivated(accessibilityManagerService.mContext, unflattenFromString, i2);
        } else if (accessibilityManagerService.performAccessibilityShortcutTargetService(i, i2, unflattenFromString)) {
            AccessibilityUtils.updateProfile(accessibilityManagerService.mContext, str);
        }
    }

    /* renamed from: -$$Nest$mapplyColorinversion, reason: not valid java name */
    public static void m116$$Nest$mapplyColorinversion(AccessibilityManagerService accessibilityManagerService) {
        if (Settings.Secure.getIntForUser(accessibilityManagerService.mContext.getContentResolver(), "accessibility_display_inversion_enabled", 0, accessibilityManagerService.mRealCurrentUserId) != 0) {
            ArrayList validDisplayList = accessibilityManagerService.getValidDisplayList();
            DisplayTransformManager displayTransformManager = (DisplayTransformManager) LocalServices.getService(DisplayTransformManager.class);
            boolean z = Settings.System.getIntForUser(accessibilityManagerService.mContext.getContentResolver(), "aod_show_state", 0, accessibilityManagerService.mRealCurrentUserId) != 0;
            Display display = (Display) validDisplayList.get(0);
            int state = display != null ? display.getState() : 1;
            Log.d("AccessibilityManagerService", "applyColorinversion aodShowStateEnabled : " + z + " displayState : " + state);
            if (z || state == 3 || state == 4 || state == 1) {
                displayTransformManager.setColorMatrix(300, null);
            } else {
                displayTransformManager.setColorMatrix(300, MATRIX_INVERT_COLOR);
            }
        }
    }

    /* renamed from: -$$Nest$mapplyDaltonizerSettings, reason: not valid java name */
    public static void m117$$Nest$mapplyDaltonizerSettings(AccessibilityManagerService accessibilityManagerService) {
        float[] fArr;
        accessibilityManagerService.getClass();
        DisplayTransformManager displayTransformManager = (DisplayTransformManager) LocalServices.getService(DisplayTransformManager.class);
        AccessibilityUserState userStateLocked = accessibilityManagerService.getUserStateLocked(accessibilityManagerService.mCurrentUserId);
        ContentResolver contentResolver = accessibilityManagerService.mContext.getContentResolver();
        int i = userStateLocked.mUserId;
        boolean z = Settings.Secure.getIntForUser(contentResolver, "user_setup_complete", 0, i) != 0;
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            int i2 = -1;
            int intForUser = Settings.Secure.getIntForUser(accessibilityManagerService.mContext.getContentResolver(), "accessibility_display_daltonizer_enabled", 0, i) != 0 ? Settings.Secure.getIntForUser(accessibilityManagerService.mContext.getContentResolver(), "accessibility_display_daltonizer", 12, i) : -1;
            Binder.restoreCallingIdentity(clearCallingIdentity);
            int intForUser2 = Flags.enableColorCorrectionSaturation() ? Settings.Secure.getIntForUser(accessibilityManagerService.mContext.getContentResolver(), "accessibility_display_daltonizer_saturation_level", -1, i) : -1;
            if (intForUser == 0) {
                fArr = MATRIX_GRAYSCALE;
            } else {
                fArr = null;
                i2 = intForUser;
            }
            if (z) {
                return;
            }
            displayTransformManager.setColorMatrix(200, fArr);
            displayTransformManager.setDaltonizerMode(i2, intForUser2);
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* renamed from: -$$Nest$menableColorLens, reason: not valid java name */
    public static void m118$$Nest$menableColorLens(AccessibilityManagerService accessibilityManagerService) {
        boolean z = Settings.Secure.getIntForUser(accessibilityManagerService.mContext.getContentResolver(), "color_lens_switch", 0, -2) != 0;
        AccessibilityManagerService$$ExternalSyntheticOutline0.m("enableColorLens colorLensEnable : ", "AccessibilityManagerService", z);
        if (!z) {
            View view = accessibilityManagerService.mColorLensView;
            if (view != null) {
                view.setVisibility(4);
                ((WindowManager) accessibilityManagerService.mContext.getSystemService("window")).removeView(accessibilityManagerService.mColorLensView);
                accessibilityManagerService.mColorLensView = null;
                return;
            }
            return;
        }
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams(-1, -1, 2015, 1336, -3);
        layoutParams.privateFlags |= 16;
        layoutParams.layoutInDisplayCutoutMode = 3;
        if (accessibilityManagerService.mColorLensView == null) {
            FrameLayout frameLayout = new FrameLayout(accessibilityManagerService.mContext);
            accessibilityManagerService.mColorLensView = frameLayout;
            frameLayout.setSystemUiVisibility(512);
            accessibilityManagerService.updateColorLensValue();
            ((WindowManager) accessibilityManagerService.mContext.getSystemService("window")).addView(accessibilityManagerService.mColorLensView, layoutParams);
        }
    }

    /* renamed from: -$$Nest$menableColorRelumino, reason: not valid java name */
    public static void m119$$Nest$menableColorRelumino(AccessibilityManagerService accessibilityManagerService) {
        AccessibilityUserState userStateLocked = accessibilityManagerService.getUserStateLocked(accessibilityManagerService.mCurrentUserId);
        ContentResolver contentResolver = accessibilityManagerService.mContext.getContentResolver();
        int i = userStateLocked.mUserId;
        boolean z = Settings.Secure.getIntForUser(contentResolver, "relumino_switch", 0, i) != 0;
        int intForUser = Settings.Secure.getIntForUser(accessibilityManagerService.mContext.getContentResolver(), "relumino_type", 0, i);
        float floatForUser = Settings.Secure.getFloatForUser(accessibilityManagerService.mContext.getContentResolver(), "relumino_edge_thickness", 2.0f, i);
        long[] physicalDisplayIds = SurfaceControl.getPhysicalDisplayIds();
        int i2 = z ? intForUser + 1 : 0;
        StringBuilder m = AccessibilityManagerService$$ExternalSyntheticOutline0.m(i2, "enableColorRelumino colorReluminoEnable : ", " colorReluminotype : ", " colorReluminoEdgeThickness : ", z);
        m.append(floatForUser);
        m.append(" displayIds[0] : ");
        m.append(physicalDisplayIds[0]);
        Log.d("AccessibilityManagerService", m.toString());
        SurfaceControl.Transaction transaction = new SurfaceControl.Transaction();
        transaction.setDisplayReluminoEffect(physicalDisplayIds[0], floatForUser, i2);
        transaction.apply();
    }

    /* renamed from: -$$Nest$monPackageRemovedLocked, reason: not valid java name */
    public static void m120$$Nest$monPackageRemovedLocked(AccessibilityManagerService accessibilityManagerService, String str) {
        AccessibilityUserState currentUserState = accessibilityManagerService.getCurrentUserState();
        AccessibilityManagerService$$ExternalSyntheticLambda7 accessibilityManagerService$$ExternalSyntheticLambda7 = new AccessibilityManagerService$$ExternalSyntheticLambda7(str, 0);
        currentUserState.mBindingServices.removeIf(accessibilityManagerService$$ExternalSyntheticLambda7);
        currentUserState.mCrashedServices.removeIf(accessibilityManagerService$$ExternalSyntheticLambda7);
        Iterator it = ((HashSet) currentUserState.mEnabledServices).iterator();
        boolean z = false;
        while (it.hasNext()) {
            ComponentName componentName = (ComponentName) it.next();
            if (componentName.getPackageName().equals(str)) {
                it.remove();
                ((HashSet) currentUserState.mTouchExplorationGrantedServices).remove(componentName);
                z = true;
            }
        }
        if (z) {
            accessibilityManagerService.persistComponentNamesToSettingLocked(accessibilityManagerService.mCurrentUserId, "enabled_accessibility_services", currentUserState.mEnabledServices);
            accessibilityManagerService.persistComponentNamesToSettingLocked(accessibilityManagerService.mCurrentUserId, "touch_exploration_granted_accessibility_services", currentUserState.mTouchExplorationGrantedServices);
            accessibilityManagerService.onUserStateChangedLocked(currentUserState, false);
        }
    }

    /* renamed from: -$$Nest$mremoveUser, reason: not valid java name */
    public static void m121$$Nest$mremoveUser(int i, AccessibilityManagerService accessibilityManagerService) {
        synchronized (accessibilityManagerService.mLock) {
            accessibilityManagerService.mUserStates.remove(i);
        }
        MagnificationScaleProvider magnificationScaleProvider = accessibilityManagerService.mMagnificationController.mScaleProvider;
        synchronized (magnificationScaleProvider.mLock) {
            magnificationScaleProvider.mUsersScales.remove(i);
        }
    }

    /* renamed from: -$$Nest$mrestoreAccessibilityQsTargets, reason: not valid java name */
    public static void m122$$Nest$mrestoreAccessibilityQsTargets(AccessibilityManagerService accessibilityManagerService, String str) {
        synchronized (accessibilityManagerService.mLock) {
            AccessibilityUserState userStateLocked = accessibilityManagerService.getUserStateLocked(0);
            LinkedHashSet linkedHashSet = new LinkedHashSet(userStateLocked.mAccessibilityQsTargets);
            accessibilityManagerService.readColonDelimitedStringToSet(str, new AccessibilityManagerService$$ExternalSyntheticLambda5(0), linkedHashSet, true);
            userStateLocked.updateA11yQsTargetLocked(linkedHashSet);
            accessibilityManagerService.persistColonDelimitedSetToSettingLocked("accessibility_qs_targets", 0, linkedHashSet, new AccessibilityManagerService$$ExternalSyntheticLambda5(0), null);
            accessibilityManagerService.scheduleNotifyClientsOfServicesStateChangeLocked(userStateLocked);
            accessibilityManagerService.onUserStateChangedLocked(userStateLocked, false);
        }
    }

    /* renamed from: -$$Nest$mrestoreAccessibilityShortcutTargetService, reason: not valid java name */
    public static void m123$$Nest$mrestoreAccessibilityShortcutTargetService(AccessibilityManagerService accessibilityManagerService, String str, String str2) {
        accessibilityManagerService.getClass();
        ArraySet arraySet = new ArraySet();
        accessibilityManagerService.readColonDelimitedStringToSet(str, new AccessibilityManagerService$$ExternalSyntheticLambda5(0), arraySet, false);
        String string = accessibilityManagerService.mContext.getString(R.string.data_usage_warning_title);
        ComponentName unflattenFromString = TextUtils.isEmpty(string) ? null : ComponentName.unflattenFromString(string);
        boolean z = (unflattenFromString == null || arraySet.stream().map(new AccessibilityManagerService$$ExternalSyntheticLambda5(3)).anyMatch(new AccessibilityManagerService$$ExternalSyntheticLambda42(0, unflattenFromString))) ? false : true;
        accessibilityManagerService.readColonDelimitedStringToSet(str2, new AccessibilityManagerService$$ExternalSyntheticLambda5(0), arraySet, true);
        Flags.clearDefaultFromA11yShortcutTargetServiceRestore();
        if (z && unflattenFromString != null && arraySet.stream().map(new AccessibilityManagerService$$ExternalSyntheticLambda5(3)).anyMatch(new AccessibilityManagerService$$ExternalSyntheticLambda42(0, unflattenFromString))) {
            Slog.i("AccessibilityManagerService", "Removing default service " + string + " from restore of accessibility_shortcut_target_service");
            arraySet.removeIf(new AccessibilityManagerService$$ExternalSyntheticLambda42(1, unflattenFromString));
        }
        if (arraySet.isEmpty()) {
            return;
        }
        synchronized (accessibilityManagerService.mLock) {
            AccessibilityUserState userStateLocked = accessibilityManagerService.getUserStateLocked(0);
            LinkedHashSet linkedHashSet = userStateLocked.mAccessibilityShortcutKeyTargets;
            linkedHashSet.clear();
            linkedHashSet.addAll(arraySet);
            accessibilityManagerService.persistColonDelimitedSetToSettingLocked("accessibility_shortcut_target_service", 0, arraySet, new AccessibilityManagerService$$ExternalSyntheticLambda5(0), null);
            accessibilityManagerService.scheduleNotifyClientsOfServicesStateChangeLocked(userStateLocked);
            accessibilityManagerService.onUserStateChangedLocked(userStateLocked, false);
        }
    }

    /* renamed from: -$$Nest$mrestoreLegacyDisplayMagnificationNavBarIfNeededLocked, reason: not valid java name */
    public static void m124$$Nest$mrestoreLegacyDisplayMagnificationNavBarIfNeededLocked(AccessibilityManagerService accessibilityManagerService, String str, int i) {
        accessibilityManagerService.getClass();
        if (i >= 30) {
            return;
        }
        try {
            boolean z = Integer.parseInt(str) == 1;
            AccessibilityUserState userStateLocked = accessibilityManagerService.getUserStateLocked(0);
            ArraySet arraySet = new ArraySet();
            accessibilityManagerService.readColonDelimitedSettingToSet("accessibility_button_targets", userStateLocked.mUserId, new AccessibilityManagerService$$ExternalSyntheticLambda5(0), arraySet);
            if (arraySet.contains("com.android.server.accessibility.MagnificationController") == z) {
                return;
            }
            if (z) {
                arraySet.add("com.android.server.accessibility.MagnificationController");
            } else {
                arraySet.remove("com.android.server.accessibility.MagnificationController");
            }
            accessibilityManagerService.persistColonDelimitedSetToSettingLocked("accessibility_button_targets", userStateLocked.mUserId, arraySet, new AccessibilityManagerService$$ExternalSyntheticLambda5(0), null);
            accessibilityManagerService.readAccessibilityButtonTargetsLocked(userStateLocked);
            accessibilityManagerService.onUserStateChangedLocked(userStateLocked, false);
        } catch (NumberFormatException e) {
            Slog.w("AccessibilityManagerService", "number format is incorrect" + e);
        }
    }

    /* renamed from: -$$Nest$mseMdnieHWColorLensState, reason: not valid java name */
    public static void m125$$Nest$mseMdnieHWColorLensState(Context context, AccessibilityManagerService accessibilityManagerService) {
        accessibilityManagerService.getClass();
        if (A11yRune.A11Y_COLOR_BOOL_SUPPORT_COLOR_FILTER_MDNIE_HW) {
            boolean z = Settings.Secure.getIntForUser(context.getContentResolver(), "color_lens_switch", 0, -2) == 1;
            int intForUser = Settings.Secure.getIntForUser(context.getContentResolver(), "color_lens_type", 0, -2);
            int intForUser2 = Settings.Secure.getIntForUser(context.getContentResolver(), "color_lens_opacity", 2, -2);
            if (z) {
                accessibilityManagerService.semEnableMdnieColorFilter(intForUser, intForUser2);
            } else {
                accessibilityManagerService.semDisableMdnieColorFilter();
            }
        }
    }

    /* renamed from: -$$Nest$msetColorAdjustment, reason: not valid java name */
    public static void m126$$Nest$msetColorAdjustment(Context context, AccessibilityManagerService accessibilityManagerService) {
        accessibilityManagerService.getClass();
        boolean z = Settings.System.getIntForUser(context.getContentResolver(), "color_blind", 0, -2) == 1;
        if (Settings.Secure.getIntForUser(context.getContentResolver(), "color_adjustment_type", 2, -2) == 0) {
            if (z) {
                accessibilityManagerService.semSetMdnieAccessibilityMode(4, true);
                return;
            } else {
                accessibilityManagerService.semSetMdnieAccessibilityMode(1, false);
                return;
            }
        }
        float floatForUser = Settings.Secure.getFloatForUser(accessibilityManagerService.mContext.getContentResolver(), "color_blind_user_parameter", FullScreenMagnificationGestureHandler.MAX_SCALE, -2) * 10.0f;
        accessibilityManagerService.semSetMdnieAccessibilityMode(1, false);
        try {
            accessibilityManagerService.semSetColorBlind(z, floatForUser);
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    public AccessibilityManagerService(Context context) {
        super(PermissionEnforcer.fromContext(context));
        Object obj = new Object();
        this.mLock = obj;
        this.mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');
        this.mTempRect = new Rect();
        this.mTempRect1 = new Rect();
        this.mTempComponentNameSet = new HashSet();
        this.mTempIntArray = new IntArray(0);
        this.mGlobalClients = new RemoteCallbackList();
        this.mUserStates = new SparseArray();
        UiAutomationManager uiAutomationManager = new UiAutomationManager(obj);
        this.mUiAutomationManager = uiAutomationManager;
        this.mSendWindowStateChangedEventRunnables = new ArrayList();
        this.mCurrentUserId = 0;
        this.mRealCurrentUserId = -2;
        this.mTempPoint = new Point();
        this.mA11yOverlayLayers = new SparseArray();
        this.mAssistantMenuMsgnr = null;
        this.mCurtainModeIsRunning = false;
        this.shouldRecogniseTwoFingerGestures = false;
        this.mCVDType = 3;
        this.mCVDSeverity = FullScreenMagnificationGestureHandler.MAX_SCALE;
        this.mCallStack = new ArrayList();
        this.mAodShowStateUri = Settings.System.getUriFor("aod_show_state");
        this.mColorInversionStateUri = Settings.Secure.getUriFor("accessibility_display_inversion_enabled");
        this.mSetupCompleteUri = Settings.Secure.getUriFor("user_setup_complete");
        this.mDisplayDaltonizerEnabledUri = Settings.Secure.getUriFor("accessibility_display_daltonizer_enabled");
        this.mDisplayDaltonizerUri = Settings.Secure.getUriFor("accessibility_display_daltonizer");
        this.mDisplayDaltonizerSaturationLevelUri = Settings.Secure.getUriFor("accessibility_display_daltonizer_saturation_level");
        this.mContext = context;
        this.mPowerManager = (PowerManager) context.getSystemService(PowerManager.class);
        WindowManagerInternal windowManagerInternal = (WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class);
        this.mWindowManagerService = windowManagerInternal;
        WindowManagerInternal.AccessibilityControllerInternal accessibilityController = windowManagerInternal.getAccessibilityController();
        if (AccessibilityTraceManager.sInstance == null) {
            AccessibilityTraceManager.sInstance = new AccessibilityTraceManager(accessibilityController, this, obj);
        }
        AccessibilityTraceManager accessibilityTraceManager = AccessibilityTraceManager.sInstance;
        this.mTraceManager = accessibilityTraceManager;
        MainHandler mainHandler = new MainHandler(context.getMainLooper());
        this.mMainHandler = mainHandler;
        this.mActivityTaskManagerService = (ActivityTaskManagerInternal) LocalServices.getService(ActivityTaskManagerInternal.class);
        this.mPackageManager = context.getPackageManager();
        AccessibilitySecurityPolicy accessibilitySecurityPolicy = new AccessibilitySecurityPolicy(new PolicyWarningUIController(mainHandler, context, new PolicyWarningUIController.NotificationController(context)), context, this, (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class));
        this.mSecurityPolicy = accessibilitySecurityPolicy;
        AccessibilityWindowManager accessibilityWindowManager = new AccessibilityWindowManager(obj, mainHandler, windowManagerInternal, this, accessibilitySecurityPolicy, this, accessibilityTraceManager);
        this.mA11yWindowManager = accessibilityWindowManager;
        this.mA11yDisplayListener = new AccessibilityDisplayListener(context, mainHandler);
        MagnificationController magnificationController = new MagnificationController(this, obj, context, new MagnificationScaleProvider(context), Executors.newSingleThreadExecutor());
        this.mMagnificationController = magnificationController;
        this.mMagnificationProcessor = new MagnificationProcessor(magnificationController);
        this.mCaptioningManagerImpl = new CaptioningManagerImpl(context);
        this.mProxyManager = new ProxyManager(obj, accessibilityWindowManager, context, mainHandler, uiAutomationManager, this);
        this.mFlashNotificationsController = new FlashNotificationsController(context);
        UserManagerInternal userManagerInternal = (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
        this.mUmi = userManagerInternal;
        if (UserManager.isVisibleBackgroundUsersEnabled()) {
            this.mVisibleBgUserIds = new SparseBooleanArray();
            userManagerInternal.addUserVisibilityListener(new UserManagerInternal.UserVisibilityListener() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda60
                @Override // com.android.server.pm.UserManagerInternal.UserVisibilityListener
                public final void onUserVisibilityChanged(int i, boolean z) {
                    AccessibilityManagerService accessibilityManagerService = AccessibilityManagerService.this;
                    synchronized (accessibilityManagerService.mLock) {
                        try {
                            if (z) {
                                accessibilityManagerService.mVisibleBgUserIds.put(i, z);
                            } else {
                                accessibilityManagerService.mVisibleBgUserIds.delete(i);
                            }
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            });
        } else {
            this.mVisibleBgUserIds = null;
        }
        init();
    }

    public AccessibilityManagerService(Context context, Handler handler, PackageManager packageManager, AccessibilitySecurityPolicy accessibilitySecurityPolicy, SystemActionPerformer systemActionPerformer, AccessibilityWindowManager accessibilityWindowManager, AccessibilityDisplayListener accessibilityDisplayListener, MagnificationController magnificationController, AccessibilityInputFilter accessibilityInputFilter, ProxyManager proxyManager, PermissionEnforcer permissionEnforcer) {
        super(permissionEnforcer);
        Object obj = new Object();
        this.mLock = obj;
        this.mStringColonSplitter = new TextUtils.SimpleStringSplitter(':');
        this.mTempRect = new Rect();
        this.mTempRect1 = new Rect();
        this.mTempComponentNameSet = new HashSet();
        this.mTempIntArray = new IntArray(0);
        this.mGlobalClients = new RemoteCallbackList();
        this.mUserStates = new SparseArray();
        this.mUiAutomationManager = new UiAutomationManager(obj);
        this.mSendWindowStateChangedEventRunnables = new ArrayList();
        this.mCurrentUserId = 0;
        this.mRealCurrentUserId = -2;
        this.mTempPoint = new Point();
        this.mA11yOverlayLayers = new SparseArray();
        this.mAssistantMenuMsgnr = null;
        this.mCurtainModeIsRunning = false;
        this.shouldRecogniseTwoFingerGestures = false;
        this.mCVDType = 3;
        this.mCVDSeverity = FullScreenMagnificationGestureHandler.MAX_SCALE;
        this.mCallStack = new ArrayList();
        this.mAodShowStateUri = Settings.System.getUriFor("aod_show_state");
        this.mColorInversionStateUri = Settings.Secure.getUriFor("accessibility_display_inversion_enabled");
        this.mSetupCompleteUri = Settings.Secure.getUriFor("user_setup_complete");
        this.mDisplayDaltonizerEnabledUri = Settings.Secure.getUriFor("accessibility_display_daltonizer_enabled");
        this.mDisplayDaltonizerUri = Settings.Secure.getUriFor("accessibility_display_daltonizer");
        this.mDisplayDaltonizerSaturationLevelUri = Settings.Secure.getUriFor("accessibility_display_daltonizer_saturation_level");
        this.mContext = context;
        this.mPowerManager = (PowerManager) context.getSystemService("power");
        WindowManagerInternal windowManagerInternal = (WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class);
        this.mWindowManagerService = windowManagerInternal;
        WindowManagerInternal.AccessibilityControllerInternal accessibilityController = windowManagerInternal.getAccessibilityController();
        if (AccessibilityTraceManager.sInstance == null) {
            AccessibilityTraceManager.sInstance = new AccessibilityTraceManager(accessibilityController, this, obj);
        }
        this.mTraceManager = AccessibilityTraceManager.sInstance;
        this.mMainHandler = handler;
        this.mActivityTaskManagerService = (ActivityTaskManagerInternal) LocalServices.getService(ActivityTaskManagerInternal.class);
        this.mPackageManager = packageManager;
        this.mSecurityPolicy = accessibilitySecurityPolicy;
        this.mSystemActionPerformer = systemActionPerformer;
        this.mA11yWindowManager = accessibilityWindowManager;
        this.mA11yDisplayListener = accessibilityDisplayListener;
        this.mMagnificationController = magnificationController;
        this.mMagnificationProcessor = new MagnificationProcessor(magnificationController);
        this.mCaptioningManagerImpl = new CaptioningManagerImpl(context);
        this.mProxyManager = proxyManager;
        if (accessibilityInputFilter != null) {
            this.mInputFilter = accessibilityInputFilter;
            this.mHasInputFilter = true;
        }
        this.mFlashNotificationsController = new FlashNotificationsController(context);
        this.mUmi = (UserManagerInternal) LocalServices.getService(UserManagerInternal.class);
        this.mVisibleBgUserIds = null;
        init();
    }

    public static boolean isClientInPackageAllowlist(AccessibilityServiceInfo accessibilityServiceInfo, Client client) {
        if (accessibilityServiceInfo == null) {
            return false;
        }
        String[] strArr = client.mPackageNames;
        boolean isEmpty = ArrayUtils.isEmpty(accessibilityServiceInfo.packageNames);
        if (isEmpty || strArr == null) {
            return isEmpty;
        }
        for (String str : strArr) {
            if (ArrayUtils.contains(accessibilityServiceInfo.packageNames, str)) {
                return true;
            }
        }
        return isEmpty;
    }

    public final boolean OnStartGestureWakeup() {
        try {
            return this.mGesturewakeup.StartGestureWakeup();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public final boolean OnStopGestureWakeup() {
        try {
            this.mGesturewakeup.StopGestureWakeup();
            return false;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public final int addAccessibilityInteractionConnection(IWindow iWindow, IBinder iBinder, IAccessibilityInteractionConnection iAccessibilityInteractionConnection, String str, int i) {
        int i2;
        boolean isTrackingWindowsLocked;
        if (this.mTraceManager.isA11yTracingEnabledForTypes(4L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.addAccessibilityInteractionConnection", 4L, "windowToken=" + iWindow + "leashToken=" + iBinder + ";connection=" + iAccessibilityInteractionConnection + "; packageName=" + str + ";userId=" + i);
        }
        AccessibilityWindowManager accessibilityWindowManager = this.mA11yWindowManager;
        accessibilityWindowManager.getClass();
        IBinder asBinder = iWindow.asBinder();
        if (accessibilityWindowManager.traceWMEnabled()) {
            accessibilityWindowManager.logTraceWM("getDisplayIdForWindow", "token=" + asBinder);
        }
        int displayIdForWindow = accessibilityWindowManager.mWindowManagerInternal.getDisplayIdForWindow(asBinder);
        synchronized (accessibilityWindowManager.mLock) {
            try {
                int resolveCallingUserIdEnforcingPermissionsLocked = accessibilityWindowManager.mSecurityPolicy.resolveCallingUserIdEnforcingPermissionsLocked(i);
                int uid = UserHandle.getUid(resolveCallingUserIdEnforcingPermissionsLocked, UserHandle.getCallingAppId());
                String resolveValidReportedPackageLocked = accessibilityWindowManager.mSecurityPolicy.resolveValidReportedPackageLocked(str, UserHandle.getCallingAppId(), resolveCallingUserIdEnforcingPermissionsLocked, Binder.getCallingPid());
                i2 = AccessibilityWindowManager.sNextWindowId;
                AccessibilityWindowManager.sNextWindowId = i2 + 1;
                accessibilityWindowManager.mSecurityPolicy.getClass();
                if (AccessibilitySecurityPolicy.isCallerInteractingAcrossUsers(i)) {
                    AccessibilityWindowManager.RemoteAccessibilityConnection remoteAccessibilityConnection = accessibilityWindowManager.new RemoteAccessibilityConnection(i2, iAccessibilityInteractionConnection, resolveValidReportedPackageLocked, uid, -1);
                    iAccessibilityInteractionConnection.asBinder().linkToDeath(remoteAccessibilityConnection, 0);
                    accessibilityWindowManager.mGlobalInteractionConnections.put(i2, remoteAccessibilityConnection);
                    accessibilityWindowManager.mGlobalWindowTokens.put(i2, asBinder);
                } else {
                    AccessibilityWindowManager.RemoteAccessibilityConnection remoteAccessibilityConnection2 = accessibilityWindowManager.new RemoteAccessibilityConnection(i2, iAccessibilityInteractionConnection, resolveValidReportedPackageLocked, uid, resolveCallingUserIdEnforcingPermissionsLocked);
                    iAccessibilityInteractionConnection.asBinder().linkToDeath(remoteAccessibilityConnection2, 0);
                    accessibilityWindowManager.getInteractionConnectionsForUserLocked(resolveCallingUserIdEnforcingPermissionsLocked).put(i2, remoteAccessibilityConnection2);
                    accessibilityWindowManager.getWindowTokensForUserLocked(resolveCallingUserIdEnforcingPermissionsLocked).put(i2, asBinder);
                }
                isTrackingWindowsLocked = accessibilityWindowManager.isTrackingWindowsLocked(displayIdForWindow);
                accessibilityWindowManager.mWindowIdMap.put(i2, iBinder);
            } catch (Throwable th) {
                throw th;
            }
        }
        if (isTrackingWindowsLocked) {
            if (accessibilityWindowManager.traceWMEnabled()) {
                accessibilityWindowManager.logTraceWM("computeWindowsForAccessibility", "displayId=" + displayIdForWindow);
            }
            accessibilityWindowManager.mWindowManagerInternal.computeWindowsForAccessibility(displayIdForWindow);
        }
        if (accessibilityWindowManager.traceWMEnabled()) {
            accessibilityWindowManager.logTraceWM("setAccessibilityIdToSurfaceMetadata", "token=" + asBinder + ";windowId=" + i2);
        }
        accessibilityWindowManager.mWindowManagerInternal.setAccessibilityIdToSurfaceMetadata(asBinder, i2);
        return i2;
    }

    public final long addClient(IAccessibilityManagerClient iAccessibilityManagerClient, int i) {
        if (this.mTraceManager.isA11yTracingEnabledForTypes(4L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.addClient", 4L, "callback=" + iAccessibilityManagerClient + ";userId=" + i);
        }
        synchronized (this.mLock) {
            try {
                int resolveCallingUserIdEnforcingPermissionsLocked = this.mSecurityPolicy.resolveCallingUserIdEnforcingPermissionsLocked(i);
                AccessibilityUserState userStateLocked = getUserStateLocked(resolveCallingUserIdEnforcingPermissionsLocked);
                int firstDeviceIdForUidLocked = this.mProxyManager.getFirstDeviceIdForUidLocked(Binder.getCallingUid());
                Client client = new Client(this, iAccessibilityManagerClient, Binder.getCallingUid(), userStateLocked, firstDeviceIdForUidLocked);
                this.mSecurityPolicy.getClass();
                if (AccessibilitySecurityPolicy.isCallerInteractingAcrossUsers(i)) {
                    if (this.mProxyManager.isProxyedDeviceId(firstDeviceIdForUidLocked)) {
                        return IntPair.of(this.mProxyManager.getStateLocked(firstDeviceIdForUidLocked), client.mLastSentRelevantEventTypes);
                    }
                    this.mGlobalClients.register(iAccessibilityManagerClient, client);
                } else {
                    if (this.mProxyManager.isProxyedDeviceId(firstDeviceIdForUidLocked)) {
                        return IntPair.of(this.mProxyManager.getStateLocked(firstDeviceIdForUidLocked), client.mLastSentRelevantEventTypes);
                    }
                    userStateLocked.mUserClients.register(iAccessibilityManagerClient, client);
                }
                return IntPair.of(resolveCallingUserIdEnforcingPermissionsLocked == this.mCurrentUserId ? getClientStateLocked(userStateLocked) : 0, client.mLastSentRelevantEventTypes);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void associateEmbeddedHierarchy(IBinder iBinder, IBinder iBinder2) {
        if (this.mTraceManager.isA11yTracingEnabledForTypes(4L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.associateEmbeddedHierarchy", 4L, "host=" + iBinder + ";embedded=" + iBinder2);
        }
        synchronized (this.mLock) {
            this.mA11yWindowManager.mHostEmbeddedMap.put(iBinder2, iBinder);
        }
    }

    public final void attachAccessibilityOverlayToDisplay(int i, SurfaceControl surfaceControl) {
        attachAccessibilityOverlayToDisplay_enforcePermission();
        this.mMainHandler.sendMessage(PooledLambda.obtainMessage(new AccessibilityManagerService$$ExternalSyntheticLambda21(), this, -1, Integer.valueOf(i), surfaceControl, (Object) null));
    }

    public final void changeCurrentUserForTestAutomationIfNeededLocked(int i) {
        SparseBooleanArray sparseBooleanArray = this.mVisibleBgUserIds;
        if (sparseBooleanArray == null) {
            Slogf.d("AccessibilityManagerService", "changeCurrentUserForTestAutomationIfNeededLocked(%d): ignoring because device doesn't support visible background users", Integer.valueOf(i));
            return;
        }
        if (!sparseBooleanArray.get(i)) {
            Slogf.wtf("AccessibilityManagerService", "changeCurrentUserForTestAutomationIfNeededLocked(): cannot change current user to %d as it's not visible (mVisibleUsers=%s)", Integer.valueOf(i), this.mVisibleBgUserIds);
            return;
        }
        int i2 = this.mCurrentUserId;
        if (i2 == i) {
            Slogf.d("AccessibilityManagerService", "changeCurrentUserForTestAutomationIfNeededLocked(): NOT changing current user for test automation purposes as it is already %d", Integer.valueOf(i2));
            return;
        }
        Slogf.i("AccessibilityManagerService", "changeCurrentUserForTestAutomationIfNeededLocked(): changing current user from %d to %d for test automation purposes", Integer.valueOf(i2), Integer.valueOf(i));
        this.mRealCurrentUserId = this.mCurrentUserId;
        switchUser(i);
    }

    public final void changeMagnificationMode(int i, int i2) {
        synchronized (this.mLock) {
            try {
                if (i == getDisplayId()) {
                    BackgroundThread.getHandler().post(new AccessibilityManagerService$$ExternalSyntheticLambda6(i2, this));
                } else {
                    AccessibilityUserState userStateLocked = getUserStateLocked(this.mCurrentUserId);
                    if (i2 != userStateLocked.getMagnificationModeLocked(i)) {
                        userStateLocked.mMagnificationModes.put(i, i2);
                        updateMagnificationModeChangeSettingsLocked(userStateLocked, i);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int computeRelevantEventTypesLocked(AccessibilityUserState accessibilityUserState, Client client) {
        UiAutomationManager.UiAutomationService uiAutomationService;
        UiAutomationManager.UiAutomationService uiAutomationService2;
        int i;
        int size = accessibilityUserState.mBoundServices.size();
        int i2 = 0;
        int i3 = 0;
        int i4 = 0;
        while (true) {
            if (i3 >= size) {
                break;
            }
            AccessibilityServiceConnection accessibilityServiceConnection = (AccessibilityServiceConnection) accessibilityUserState.mBoundServices.get(i3);
            if (isClientInPackageAllowlist(accessibilityServiceConnection.mAccessibilityServiceInfo, client)) {
                i = (accessibilityServiceConnection.mUsesAccessibilityCache ? 4307005 : 32) | accessibilityServiceConnection.mEventTypes;
            } else {
                i = 0;
            }
            i4 |= i;
            i3++;
        }
        UiAutomationManager uiAutomationManager = this.mUiAutomationManager;
        synchronized (uiAutomationManager.mLock) {
            uiAutomationService = uiAutomationManager.mUiAutomationService;
        }
        if (isClientInPackageAllowlist(uiAutomationService == null ? null : uiAutomationService.getServiceInfo(), client)) {
            UiAutomationManager uiAutomationManager2 = this.mUiAutomationManager;
            synchronized (uiAutomationManager2.mLock) {
                uiAutomationService2 = uiAutomationManager2.mUiAutomationService;
            }
            if (uiAutomationService2 != null) {
                i2 = (uiAutomationService2.mUsesAccessibilityCache ? 4307005 : 32) | uiAutomationService2.mEventTypes;
            }
        }
        return i4 | i2;
    }

    public final int convertPixelToDpi(float f) {
        return (int) (f / (this.mContext.getResources().getDisplayMetrics().densityDpi / 160.0f));
    }

    public final void disableAccessibilityMenuToMigrateIfNeeded() {
        int i;
        synchronized (this.mLock) {
            i = this.mCurrentUserId;
        }
        ComponentName accessibilityMenuComponentToMigrate = AccessibilityUtils.getAccessibilityMenuComponentToMigrate(this.mPackageManager, i);
        if (accessibilityMenuComponentToMigrate != null) {
            this.mContext.createContextAsUser(UserHandle.of(i), 0).getPackageManager().setComponentEnabledSetting(accessibilityMenuComponentToMigrate, 2, 1);
        }
    }

    public final void disableAccessibilityServiceLocked(int i, ComponentName componentName) {
        ((HashSet) this.mTempComponentNameSet).clear();
        readComponentNamesFromSettingLocked("enabled_accessibility_services", i, this.mTempComponentNameSet);
        ((HashSet) this.mTempComponentNameSet).remove(componentName);
        persistComponentNamesToSettingLocked(i, "enabled_accessibility_services", this.mTempComponentNameSet);
        AccessibilityUserState userStateLocked = getUserStateLocked(i);
        if (((HashSet) userStateLocked.mEnabledServices).remove(componentName)) {
            onUserStateChangedLocked(userStateLocked, false);
        }
    }

    public final void disassociateEmbeddedHierarchy(IBinder iBinder) {
        if (this.mTraceManager.isA11yTracingEnabledForTypes(4L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.disassociateEmbeddedHierarchy", 4L, "token=" + iBinder);
        }
        synchronized (this.mLock) {
            this.mA11yWindowManager.disassociateLocked(iBinder);
        }
    }

    public final void dispatchAccessibilityEventLocked(AccessibilityEvent accessibilityEvent) {
        if (this.mProxyManager.isProxyedDisplay(accessibilityEvent.getDisplayId())) {
            ProxyAccessibilityServiceConnection proxyAccessibilityServiceConnection = (ProxyAccessibilityServiceConnection) this.mProxyManager.mProxyA11yServiceConnections.get(accessibilityEvent.getDisplayId());
            if (proxyAccessibilityServiceConnection != null) {
                if (ProxyManager.DEBUG) {
                    Slog.v("ProxyManager", "Send proxy event " + accessibilityEvent + " for display id " + accessibilityEvent.getDisplayId());
                }
                proxyAccessibilityServiceConnection.notifyAccessibilityEvent(accessibilityEvent);
            }
        } else {
            notifyAccessibilityServicesDelayedLocked(accessibilityEvent, false);
            notifyAccessibilityServicesDelayedLocked(accessibilityEvent, true);
        }
        UiAutomationManager.UiAutomationService uiAutomationService = this.mUiAutomationManager.mUiAutomationService;
        if (uiAutomationService != null) {
            uiAutomationService.notifyAccessibilityEvent(accessibilityEvent);
        }
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        UiAutomationManager.UiAutomationService uiAutomationService;
        AccessibilityInputFilter accessibilityInputFilter;
        if (DumpUtils.checkDumpPermission(this.mContext, "AccessibilityManagerService", printWriter)) {
            synchronized (this.mLock) {
                try {
                    printWriter.println("ACCESSIBILITY MANAGER (dumpsys accessibility)");
                    printWriter.println();
                    String str = Build.TYPE;
                    int i = 0;
                    if ((!"eng".equals(str) && !"userdebug".equals(str)) || (!SamsungWindowDumpUtils.hasMatchedArgument("print-accessibilitywindowinfo", strArr) && !SamsungWindowDumpUtils.hasMatchedArgument("print-accessibilitynodeinfo", strArr))) {
                        printWriter.append("currentUserId=").append((CharSequence) String.valueOf(this.mCurrentUserId));
                        int i2 = this.mRealCurrentUserId;
                        if (i2 != -2 && this.mCurrentUserId != i2) {
                            printWriter.append(" (set for UiAutomation purposes; \"real\" current user is ").append((CharSequence) String.valueOf(this.mRealCurrentUserId)).append(")");
                        }
                        printWriter.println();
                        if (this.mVisibleBgUserIds != null) {
                            printWriter.append("visibleBgUserIds=").append((CharSequence) this.mVisibleBgUserIds.toString());
                            printWriter.println();
                        }
                        printWriter.append("hasMagnificationConnection=").append((CharSequence) String.valueOf(getMagnificationConnectionManager().isConnected()));
                        printWriter.println();
                        this.mMagnificationProcessor.dump(printWriter, getValidDisplayList());
                        int size = this.mUserStates.size();
                        for (int i3 = 0; i3 < size; i3++) {
                            ((AccessibilityUserState) this.mUserStates.valueAt(i3)).dump(fileDescriptor, printWriter, strArr);
                            printWriter.append("  AccessibilityInputFilter:{");
                            AccessibilityInputFilter accessibilityInputFilter2 = this.mInputFilter;
                            if (accessibilityInputFilter2 != null) {
                                accessibilityInputFilter2.dump(fileDescriptor, printWriter, strArr);
                            }
                            printWriter.println();
                            printWriter.println("  }]");
                            printWriter.println();
                            if (this.mCallStack.size() > 0) {
                                printWriter.append("**** CallStack History ****\n");
                                for (int i4 = 0; i4 < this.mCallStack.size(); i4++) {
                                    printWriter.append((CharSequence) ("history [" + i4 + "] : \n" + ((String) this.mCallStack.get(i4))));
                                }
                                printWriter.append("**** End of CallStack History ****\n");
                            }
                            printWriter.println();
                        }
                        UiAutomationManager uiAutomationManager = this.mUiAutomationManager;
                        if (uiAutomationManager.mUiAutomationService != null || (uiAutomationManager.mUiAutomationFlags & 2) != 0) {
                            synchronized (uiAutomationManager.mLock) {
                                uiAutomationService = uiAutomationManager.mUiAutomationService;
                            }
                            if (uiAutomationService != null) {
                                uiAutomationService.dump(fileDescriptor, printWriter, strArr);
                            }
                            printWriter.println();
                        }
                        this.mA11yWindowManager.dump(printWriter, strArr);
                        if (this.mHasInputFilter && (accessibilityInputFilter = this.mInputFilter) != null) {
                            accessibilityInputFilter.dump(fileDescriptor, printWriter, strArr);
                        }
                        printWriter.println("Global client list info:{");
                        this.mGlobalClients.dump(printWriter, "    Client list ");
                        printWriter.println("    Registered clients:{");
                        while (i < this.mGlobalClients.getRegisteredCallbackCount()) {
                            printWriter.append((CharSequence) Arrays.toString(((Client) this.mGlobalClients.getRegisteredCallbackCookie(i)).mPackageNames));
                            i++;
                        }
                        printWriter.println();
                        this.mProxyManager.dump(fileDescriptor, printWriter, strArr);
                        this.mA11yDisplayListener.dump(printWriter);
                        return;
                    }
                    if (SamsungWindowDumpUtils.hasMatchedArgument("print-accessibilitywindowinfo", strArr)) {
                        this.mA11yWindowManager.dump(printWriter, strArr);
                    } else if (SamsungWindowDumpUtils.hasMatchedArgument("print-accessibilitynodeinfo", strArr)) {
                        InteractionBridge interactionBridge = getInteractionBridge();
                        if (interactionBridge == null) {
                            printWriter.println("There are no enabled AccessibilityService");
                        } else {
                            AccessibilityWindowManager accessibilityWindowManager = this.mA11yWindowManager;
                            int i5 = interactionBridge.mConnectionId;
                            String[] strArr2 = new String[5];
                            int length = strArr.length < 4 ? strArr.length : 4;
                            while (i < length) {
                                strArr2[i] = strArr[i];
                                i++;
                            }
                            strArr2[4] = Integer.toString(i5);
                            accessibilityWindowManager.dump(printWriter, strArr2);
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void enableAccessibilityServiceLocked(int i, ComponentName componentName) {
        ((HashSet) this.mTempComponentNameSet).clear();
        readComponentNamesFromSettingLocked("enabled_accessibility_services", i, this.mTempComponentNameSet);
        ((HashSet) this.mTempComponentNameSet).add(componentName);
        persistComponentNamesToSettingLocked(i, "enabled_accessibility_services", this.mTempComponentNameSet);
        AccessibilityUserState userStateLocked = getUserStateLocked(i);
        if (((HashSet) userStateLocked.mEnabledServices).add(componentName)) {
            onUserStateChangedLocked(userStateLocked, false);
        }
    }

    public final void enableShortcutForTargets(List list, int i, boolean z, int i2) {
        String convertToKey = ShortcutUtils.convertToKey(i);
        if (i == 4 || i == 8) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                String str = (String) it.next();
                if ("com.android.server.accessibility.MagnificationController".equals(str)) {
                    persistIntToSetting(i2, z ? 1 : 0, convertToKey);
                } else {
                    HeimdAllFsService$$ExternalSyntheticOutline0.m("Triple tap or two-fingers double-tap is not supported for ", str, "AccessibilityManagerService");
                }
            }
            return;
        }
        Map a11yFeatureToTileMapInternal = getA11yFeatureToTileMapInternal(i2);
        synchronized (this.mLock) {
            try {
                AccessibilityUserState userStateLocked = getUserStateLocked(i2);
                Set shortcutTargetsFromSettings = ShortcutUtils.getShortcutTargetsFromSettings(this.mContext, i, i2);
                LinkedHashSet linkedHashSet = new LinkedHashSet(shortcutTargetsFromSettings);
                if (z) {
                    linkedHashSet.addAll(list);
                } else {
                    linkedHashSet.removeAll(list);
                }
                if (i == 16) {
                    linkedHashSet = new LinkedHashSet((List) linkedHashSet.stream().filter(new AccessibilityManagerService$$ExternalSyntheticLambda17(0, a11yFeatureToTileMapInternal)).collect(Collectors.toUnmodifiableList()));
                }
                LinkedHashSet linkedHashSet2 = linkedHashSet;
                if (shortcutTargetsFromSettings.equals(linkedHashSet2)) {
                    return;
                }
                persistColonDelimitedSetToSettingLocked(convertToKey, i2, linkedHashSet2, new AccessibilityManagerService$$ExternalSyntheticLambda5(0), "");
                if (i == 16) {
                    int abs = Math.abs(shortcutTargetsFromSettings.size() - linkedHashSet2.size());
                    if (abs > 0) {
                        Counter.logIncrementWithUid(z ? "accessibility.value_qs_shortcut_add" : "accessibility.value_qs_shortcut_remove", Binder.getCallingUid(), abs);
                    }
                    userStateLocked.updateA11yQsTargetLocked(linkedHashSet2);
                    scheduleNotifyClientsOfServicesStateChangeLocked(userStateLocked);
                    onUserStateChangedLocked(userStateLocked, false);
                }
                long clearCallingIdentity = Binder.clearCallingIdentity();
                try {
                    ShortcutUtils.updateInvisibleToggleAccessibilityServiceEnableState(this.mContext, new ArraySet(list), i2);
                    if (i == 16) {
                        this.mMainHandler.sendMessage(PooledLambda.obtainMessage(new AccessibilityManagerService$$ExternalSyntheticLambda14(1), this, linkedHashSet2, shortcutTargetsFromSettings, Integer.valueOf(i2)));
                    }
                    if (z) {
                        if (i == 2) {
                            persistIntToSetting(i2, 1, "skip_accessibility_shortcut_dialog_timeout_restriction");
                            Flags.enableHardwareShortcutDisablesWarning();
                        } else if (i == 1 && list.contains("com.android.server.accessibility.MagnificationController") && Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "accessibility_floating_menu_size", -1, i2) == -1) {
                            persistIntToSetting(i2, 1, "accessibility_floating_menu_size");
                        }
                    }
                } finally {
                    Binder.restoreCallingIdentity(clearCallingIdentity);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void enableShortcutsForTargets(boolean z, int i, List list, int i2) {
        enableShortcutsForTargets_enforcePermission();
        for (int i3 : ShortcutConstants.USER_SHORTCUT_TYPES) {
            if ((i & i3) == i3) {
                enableShortcutForTargets(list, i3, z, i2);
            }
        }
    }

    public final boolean fallBackMagnificationModeSettingsLocked(AccessibilityUserState accessibilityUserState, int i) {
        int magnificationModeLocked = accessibilityUserState.getMagnificationModeLocked(i);
        if ((accessibilityUserState.mSupportWindowMagnification || magnificationModeLocked != 2) && (magnificationModeLocked & accessibilityUserState.mMagnificationCapabilities) != 0) {
            return false;
        }
        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "displayId ", ", invalid magnification mode:");
        m.append(accessibilityUserState.getMagnificationModeLocked(i));
        Slog.w("AccessibilityManagerService", m.toString());
        int i2 = accessibilityUserState.mMagnificationCapabilities;
        accessibilityUserState.mMagnificationModes.put(i, i2);
        if (i != getDisplayId()) {
            return true;
        }
        BackgroundThread.getHandler().post(new AccessibilityManagerService$$ExternalSyntheticLambda6(i2, this));
        return true;
    }

    public final Bundle getA11yFeatureToTileMap(int i) {
        getA11yFeatureToTileMap_enforcePermission();
        Bundle bundle = new Bundle();
        for (Map.Entry entry : ((ArrayMap) getA11yFeatureToTileMapInternal(i)).entrySet()) {
            bundle.putParcelable(((ComponentName) entry.getKey()).flattenToString(), (Parcelable) entry.getValue());
        }
        return bundle;
    }

    public final Map getA11yFeatureToTileMapInternal(int i) {
        int resolveCallingUserIdEnforcingPermissionsLocked;
        Map a11yFeatureToTileService;
        ArrayMap arrayMap = new ArrayMap();
        synchronized (this.mLock) {
            resolveCallingUserIdEnforcingPermissionsLocked = this.mSecurityPolicy.resolveCallingUserIdEnforcingPermissionsLocked(i);
            a11yFeatureToTileService = getUserStateLocked(resolveCallingUserIdEnforcingPermissionsLocked).getA11yFeatureToTileService();
        }
        boolean z = Binder.getCallingPid() != OWN_PROCESS_ID;
        int callingUid = Binder.getCallingUid();
        PackageManagerInternal packageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        for (Map.Entry entry : ((ArrayMap) a11yFeatureToTileService).entrySet()) {
            if (!z || !packageManagerInternal.filterAppAccess(callingUid, resolveCallingUserIdEnforcingPermissionsLocked, ((ComponentName) entry.getKey()).getPackageName(), true)) {
                arrayMap.put((ComponentName) entry.getKey(), (ComponentName) entry.getValue());
            }
        }
        arrayMap.putAll(ShortcutConstants.A11Y_FEATURE_TO_FRAMEWORK_TILE);
        return arrayMap;
    }

    public final boolean getAccessibilityFocusClickPointInScreen(Point point) {
        MagnificationSpec magnificationSpec;
        IBinder windowToken;
        InteractionBridge interactionBridge = getInteractionBridge();
        AccessibilityNodeInfo accessibilityFocusNotLocked = interactionBridge.getAccessibilityFocusNotLocked();
        if (accessibilityFocusNotLocked == null) {
            return false;
        }
        synchronized (AccessibilityManagerService.this.mLock) {
            try {
                Rect rect = AccessibilityManagerService.this.mTempRect;
                accessibilityFocusNotLocked.getBoundsInScreen(rect);
                Point point2 = new Point(rect.centerX(), rect.centerY());
                Pair windowTransformationMatrixAndMagnificationSpec = AccessibilityManagerService.this.getWindowTransformationMatrixAndMagnificationSpec(accessibilityFocusNotLocked.getWindowId());
                if (windowTransformationMatrixAndMagnificationSpec.second != null) {
                    magnificationSpec = new MagnificationSpec();
                    magnificationSpec.setTo((MagnificationSpec) windowTransformationMatrixAndMagnificationSpec.second);
                } else {
                    magnificationSpec = null;
                }
                if (magnificationSpec != null && !magnificationSpec.isNop()) {
                    rect.offset((int) (-magnificationSpec.offsetX), (int) (-magnificationSpec.offsetY));
                    rect.scale(1.0f / magnificationSpec.scale);
                }
                Rect rect2 = AccessibilityManagerService.this.mTempRect1;
                AccessibilityWindowInfo window = accessibilityFocusNotLocked.getWindow();
                if (window != null) {
                    window.getBoundsInScreen(rect2);
                }
                AccessibilityManagerService accessibilityManagerService = AccessibilityManagerService.this;
                int windowId = accessibilityFocusNotLocked.getWindowId();
                synchronized (accessibilityManagerService.mLock) {
                    windowToken = accessibilityManagerService.getWindowToken(windowId, accessibilityManagerService.mCurrentUserId);
                }
                boolean isEmbeddedWindowType = accessibilityManagerService.mWindowManagerService.isEmbeddedWindowType(windowToken);
                if (isEmbeddedWindowType) {
                    AnyMotionDetector$$ExternalSyntheticOutline0.m(windowId, "skip checking window bound for ", "AccessibilityManagerService");
                }
                if (!isEmbeddedWindowType && !rect.intersect(rect2)) {
                    return false;
                }
                Point point3 = AccessibilityManagerService.this.mTempPoint;
                interactionBridge.mDefaultDisplay.getRealSize(point3);
                if (!rect.intersect(0, 0, point3.x, point3.y)) {
                    return false;
                }
                point.set(point2.x, point2.y);
                return true;
            } finally {
            }
        }
    }

    public final List getAccessibilityShortcutTargets(int i) {
        getAccessibilityShortcutTargets_enforcePermission();
        if (this.mTraceManager.isA11yTracingEnabledForTypes(4L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.getAccessibilityShortcutTargets", 4L, VibrationParam$1$$ExternalSyntheticOutline0.m(i, "shortcutType="));
        }
        return getAccessibilityShortcutTargetsInternal(i);
    }

    public final List getAccessibilityShortcutTargetsInternal(int i) {
        synchronized (this.mLock) {
            try {
                AccessibilityUserState userStateLocked = getUserStateLocked(this.mCurrentUserId);
                ArrayList arrayList = new ArrayList(userStateLocked.getShortcutTargetsLocked(i));
                if (i != 1) {
                    return arrayList;
                }
                for (int size = userStateLocked.mBoundServices.size() - 1; size >= 0; size--) {
                    AccessibilityServiceConnection accessibilityServiceConnection = (AccessibilityServiceConnection) userStateLocked.mBoundServices.get(size);
                    if (accessibilityServiceConnection.mRequestAccessibilityButton && accessibilityServiceConnection.mAccessibilityServiceInfo.getResolveInfo().serviceInfo.applicationInfo.targetSdkVersion <= 29) {
                        String flattenToString = accessibilityServiceConnection.mComponentName.flattenToString();
                        if (!TextUtils.isEmpty(flattenToString)) {
                            arrayList.add(flattenToString);
                        }
                    }
                }
                return arrayList;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final String getAccessibilityTargetLabel(int i, String str) {
        List targets = AccessibilityTargetHelper.getTargets(this.mContext, i);
        for (int i2 = 0; i2 < targets.size(); i2++) {
            if (str.equals(((AccessibilityTarget) targets.get(i2)).getId())) {
                return ((AccessibilityTarget) targets.get(i2)).getLabel().toString();
            }
        }
        return "";
    }

    public final int getAccessibilityWindowId(IBinder iBinder) {
        int findWindowIdLocked;
        if (this.mTraceManager.isA11yTracingEnabledForTypes(4L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.getAccessibilityWindowId", 4L, "windowToken=" + iBinder);
        }
        synchronized (this.mLock) {
            try {
                if (UserHandle.getAppId(Binder.getCallingUid()) != 1000) {
                    throw new SecurityException("Only SYSTEM can call getAccessibilityWindowId");
                }
                findWindowIdLocked = this.mA11yWindowManager.findWindowIdLocked(this.mCurrentUserId, iBinder);
            } catch (Throwable th) {
                throw th;
            }
        }
        return findWindowIdLocked;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x0048  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x004e  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int getClientStateLocked(com.android.server.accessibility.AccessibilityUserState r7) {
        /*
            r6 = this;
            com.android.server.accessibility.UiAutomationManager r0 = r6.mUiAutomationManager
            com.android.server.accessibility.UiAutomationManager$UiAutomationService r0 = r0.mUiAutomationService
            r1 = 0
            r2 = 1
            if (r0 == 0) goto La
            r0 = r2
            goto Lb
        La:
            r0 = r1
        Lb:
            com.android.server.accessibility.AccessibilityTraceManager r6 = r6.mTraceManager
            int r6 = r6.getTraceStateForAccessibilityManagerClientState()
            if (r0 != 0) goto L1b
            boolean r0 = r7.isHandlingAccessibilityEventsLocked()
            if (r0 == 0) goto L1a
            goto L1b
        L1a:
            r2 = r1
        L1b:
            if (r2 == 0) goto L21
            boolean r0 = r7.mIsTouchExplorationEnabled
            if (r0 != 0) goto L3e
        L21:
            java.util.ArrayList r0 = r7.mBoundServices
            int r3 = r0.size()
        L27:
            if (r1 >= r3) goto L44
            java.lang.Object r4 = r0.get(r1)
            com.android.server.accessibility.AccessibilityServiceConnection r4 = (com.android.server.accessibility.AccessibilityServiceConnection) r4
            android.content.ComponentName r4 = r4.mComponentName
            java.lang.String r4 = r4.toString()
            java.lang.String r5 = "com.samsung.android.app.a11yhelper"
            boolean r4 = r4.contains(r5)
            if (r4 == 0) goto L41
        L3e:
            r2 = r2 | 26
            goto L44
        L41:
            int r1 = r1 + 1
            goto L27
        L44:
            boolean r0 = r7.mIsTextHighContrastEnabled
            if (r0 == 0) goto L4a
            r2 = r2 | 4
        L4a:
            boolean r7 = r7.mIsAudioDescriptionByDefaultRequested
            if (r7 == 0) goto L50
            r2 = r2 | 4096(0x1000, float:5.74E-42)
        L50:
            r6 = r6 | r2
            return r6
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.accessibility.AccessibilityManagerService.getClientStateLocked(com.android.server.accessibility.AccessibilityUserState):int");
    }

    public final AccessibilityUserState getCurrentUserState() {
        AccessibilityUserState userStateLocked;
        synchronized (this.mLock) {
            userStateLocked = getUserStateLocked(this.mCurrentUserId);
        }
        return userStateLocked;
    }

    public final int getDisplayId() {
        if (AccessibilityUtils.isFoldedLargeCoverScreen()) {
            return 1;
        }
        return AccessibilityUtils.isDexDualMonitorDisplay(this.mContext) ? 2 : 0;
    }

    public final List getEnabledAccessibilityServiceList(int i, int i2) {
        if (this.mTraceManager.isA11yTracingEnabledForTypes(4L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.getEnabledAccessibilityServiceList", 4L, ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "feedbackType=", ";userId="));
        }
        synchronized (this.mLock) {
            try {
                int firstDeviceIdForUidLocked = this.mProxyManager.getFirstDeviceIdForUidLocked(Binder.getCallingUid());
                if (this.mProxyManager.isProxyedDeviceId(firstDeviceIdForUidLocked)) {
                    return this.mProxyManager.getInstalledAndEnabledServiceInfosLocked(i, firstDeviceIdForUidLocked);
                }
                AccessibilityUserState userStateLocked = getUserStateLocked(this.mSecurityPolicy.resolveCallingUserIdEnforcingPermissionsLocked(i2));
                if (this.mUiAutomationManager.suppressingAccessibilityServicesLocked()) {
                    return Collections.emptyList();
                }
                ArrayList arrayList = userStateLocked.mBoundServices;
                int size = arrayList.size();
                ArrayList arrayList2 = new ArrayList(size);
                for (int i3 = 0; i3 < size; i3++) {
                    AccessibilityServiceConnection accessibilityServiceConnection = (AccessibilityServiceConnection) arrayList.get(i3);
                    if ((accessibilityServiceConnection.mFeedbackType & i) != 0 || i == -1) {
                        arrayList2.add(accessibilityServiceConnection.mAccessibilityServiceInfo);
                    }
                }
                return arrayList2;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int getFocusColor() {
        if (this.mTraceManager.isA11yTracingEnabledForTypes(4L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.getFocusColor", 4L);
        }
        synchronized (this.mLock) {
            try {
                int firstDeviceIdForUidLocked = this.mProxyManager.getFirstDeviceIdForUidLocked(Binder.getCallingUid());
                if (!this.mProxyManager.isProxyedDeviceId(firstDeviceIdForUidLocked)) {
                    return getUserStateLocked(this.mCurrentUserId).mFocusColor;
                }
                ProxyAccessibilityServiceConnection firstProxyForDeviceIdLocked = this.mProxyManager.getFirstProxyForDeviceIdLocked(firstDeviceIdForUidLocked);
                return firstProxyForDeviceIdLocked != null ? firstProxyForDeviceIdLocked.mFocusColor : 0;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int getFocusStrokeWidth() {
        if (this.mTraceManager.isA11yTracingEnabledForTypes(4L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.getFocusStrokeWidth", 4L);
        }
        synchronized (this.mLock) {
            try {
                int firstDeviceIdForUidLocked = this.mProxyManager.getFirstDeviceIdForUidLocked(Binder.getCallingUid());
                if (!this.mProxyManager.isProxyedDeviceId(firstDeviceIdForUidLocked)) {
                    return getUserStateLocked(this.mCurrentUserId).mFocusStrokeWidth;
                }
                ProxyAccessibilityServiceConnection firstProxyForDeviceIdLocked = this.mProxyManager.getFirstProxyForDeviceIdLocked(firstDeviceIdForUidLocked);
                return firstProxyForDeviceIdLocked != null ? firstProxyForDeviceIdLocked.mFocusStrokeWidth : 0;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final ParceledListSlice getInstalledAccessibilityServiceList(int i) {
        if (this.mTraceManager.isA11yTracingEnabledForTypes(4L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.getInstalledAccessibilityServiceList", 4L, VibrationParam$1$$ExternalSyntheticOutline0.m(i, "userId="));
        }
        synchronized (this.mLock) {
            try {
                int firstDeviceIdForUidLocked = this.mProxyManager.getFirstDeviceIdForUidLocked(Binder.getCallingUid());
                if (this.mProxyManager.isProxyedDeviceId(firstDeviceIdForUidLocked)) {
                    return new ParceledListSlice(this.mProxyManager.getInstalledAndEnabledServiceInfosLocked(-1, firstDeviceIdForUidLocked));
                }
                int resolveCallingUserIdEnforcingPermissionsLocked = this.mSecurityPolicy.resolveCallingUserIdEnforcingPermissionsLocked(i);
                ArrayList arrayList = new ArrayList(getUserStateLocked(resolveCallingUserIdEnforcingPermissionsLocked).mInstalledServices);
                if (Binder.getCallingPid() == OWN_PROCESS_ID) {
                    return new ParceledListSlice(arrayList);
                }
                PackageManagerInternal packageManagerInternal = (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
                int callingUid = Binder.getCallingUid();
                for (int size = arrayList.size() - 1; size >= 0; size--) {
                    if (packageManagerInternal.filterAppAccess(callingUid, resolveCallingUserIdEnforcingPermissionsLocked, ((AccessibilityServiceInfo) arrayList.get(size)).getComponentName().getPackageName(), true)) {
                        arrayList.remove(size);
                    }
                }
                return new ParceledListSlice(arrayList);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final InteractionBridge getInteractionBridge() {
        InteractionBridge interactionBridge;
        synchronized (this.mLock) {
            try {
                if (this.mInteractionBridge == null) {
                    this.mInteractionBridge = new InteractionBridge();
                }
                interactionBridge = this.mInteractionBridge;
            } catch (Throwable th) {
                throw th;
            }
        }
        return interactionBridge;
    }

    public final KeyEventDispatcher getKeyEventDispatcher() {
        if (this.mKeyEventDispatcher == null) {
            this.mKeyEventDispatcher = new KeyEventDispatcher(this.mMainHandler, this.mLock, this.mPowerManager);
        }
        return this.mKeyEventDispatcher;
    }

    public Object getLock() {
        return this.mLock;
    }

    public final MagnificationConnectionManager getMagnificationConnectionManager() {
        MagnificationConnectionManager magnificationConnectionManager;
        synchronized (this.mLock) {
            magnificationConnectionManager = this.mMagnificationController.getMagnificationConnectionManager();
        }
        return magnificationConnectionManager;
    }

    public PackageMonitor getPackageMonitor() {
        return this.mPackageMonitor;
    }

    public final long getRecommendedTimeoutMillis() {
        if (this.mTraceManager.isA11yTracingEnabledForTypes(4L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.getRecommendedTimeoutMillis", 4L);
        }
        synchronized (this.mLock) {
            try {
                int firstDeviceIdForUidLocked = this.mProxyManager.getFirstDeviceIdForUidLocked(Binder.getCallingUid());
                if (this.mProxyManager.isProxyedDeviceId(firstDeviceIdForUidLocked)) {
                    return this.mProxyManager.getRecommendedTimeoutMillisLocked(firstDeviceIdForUidLocked);
                }
                AccessibilityUserState userStateLocked = getUserStateLocked(this.mCurrentUserId);
                return IntPair.of(userStateLocked.mInteractiveUiTimeout, userStateLocked.mNonInteractiveUiTimeout);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final String getScreenReaderName() {
        List<AccessibilityServiceInfo> enabledAccessibilityServiceList = ((AccessibilityManager) this.mContext.getSystemService(AccessibilityManager.class)).getEnabledAccessibilityServiceList(1);
        String str = "";
        for (int i = 0; i < enabledAccessibilityServiceList.size(); i++) {
            AccessibilityServiceInfo accessibilityServiceInfo = enabledAccessibilityServiceList.get(i);
            ComponentName unflattenFromString = ComponentName.unflattenFromString(accessibilityServiceInfo.getId());
            if (isScreenReaderEnabled() && unflattenFromString != null && (unflattenFromString.equals(ComponentName.unflattenFromString("com.samsung.accessibility/com.samsung.android.app.talkback.TalkBackService")) || unflattenFromString.equals(ComponentName.unflattenFromString("com.google.android.marvin.talkback/.TalkBackService")) || unflattenFromString.equals(ComponentName.unflattenFromString("com.samsung.android.accessibility.talkback/com.samsung.android.marvin.talkback.TalkBackService")))) {
                str = accessibilityServiceInfo.getResolveInfo().loadLabel(this.mPackageManager).toString();
            }
        }
        Slog.i("AccessibilityManagerService", "getScreenReaderName : " + str);
        return str;
    }

    public final SystemActionPerformer getSystemActionPerformer() {
        if (this.mSystemActionPerformer == null) {
            this.mSystemActionPerformer = new SystemActionPerformer(this.mContext, this.mWindowManagerService, null, this, this);
        }
        return this.mSystemActionPerformer;
    }

    public final AccessibilityUserState getUserStateLocked(int i) {
        AccessibilityUserState accessibilityUserState = (AccessibilityUserState) this.mUserStates.get(i);
        if (accessibilityUserState != null) {
            return accessibilityUserState;
        }
        AccessibilityUserState accessibilityUserState2 = new AccessibilityUserState(i, this.mContext, this);
        this.mUserStates.put(i, accessibilityUserState2);
        return accessibilityUserState2;
    }

    public final ArrayList getValidDisplayList() {
        ArrayList arrayList;
        AccessibilityDisplayListener accessibilityDisplayListener = this.mA11yDisplayListener;
        synchronized (AccessibilityManagerService.this.mLock) {
            arrayList = accessibilityDisplayListener.mDisplaysList;
        }
        return arrayList;
    }

    public final IBinder getWindowToken(int i, int i2) {
        getWindowToken_enforcePermission();
        if (this.mTraceManager.isA11yTracingEnabledForTypes(4L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.getWindowToken", 4L, ArrayUtils$$ExternalSyntheticOutline0.m(i, i2, "windowId=", ";userId="));
        }
        synchronized (this.mLock) {
            try {
                if (this.mSecurityPolicy.resolveCallingUserIdEnforcingPermissionsLocked(i2) != this.mCurrentUserId) {
                    return null;
                }
                AccessibilityWindowInfo findA11yWindowInfoByIdLocked = this.mA11yWindowManager.findA11yWindowInfoByIdLocked(i);
                if (findA11yWindowInfoByIdLocked == null) {
                    return null;
                }
                return this.mA11yWindowManager.getWindowTokenForUserAndWindowIdLocked(i2, findA11yWindowInfoByIdLocked.getId());
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final Pair getWindowTransformationMatrixAndMagnificationSpec(int i) {
        WindowInfo windowInfo;
        IBinder windowTokenForUserAndWindowIdLocked;
        synchronized (this.mLock) {
            AccessibilityWindowManager accessibilityWindowManager = this.mA11yWindowManager;
            int resolveParentWindowIdLocked = accessibilityWindowManager.resolveParentWindowIdLocked(i);
            AccessibilityWindowManager.DisplayWindowsObserver displayWindowObserverByWindowIdLocked = accessibilityWindowManager.getDisplayWindowObserverByWindowIdLocked(resolveParentWindowIdLocked);
            windowInfo = displayWindowObserverByWindowIdLocked != null ? (WindowInfo) displayWindowObserverByWindowIdLocked.mWindowInfoById.get(resolveParentWindowIdLocked) : null;
        }
        if (windowInfo != null) {
            MagnificationSpec magnificationSpec = new MagnificationSpec();
            magnificationSpec.setTo(windowInfo.mMagnificationSpec);
            return new Pair(windowInfo.mTransformMatrix, magnificationSpec);
        }
        synchronized (this.mLock) {
            windowTokenForUserAndWindowIdLocked = this.mA11yWindowManager.getWindowTokenForUserAndWindowIdLocked(this.mCurrentUserId, i);
        }
        Pair windowTransformationMatrixAndMagnificationSpec = this.mWindowManagerService.getWindowTransformationMatrixAndMagnificationSpec(windowTokenForUserAndWindowIdLocked);
        float[] fArr = new float[9];
        Matrix matrix = (Matrix) windowTransformationMatrixAndMagnificationSpec.first;
        MagnificationSpec magnificationSpec2 = (MagnificationSpec) windowTransformationMatrixAndMagnificationSpec.second;
        if (!magnificationSpec2.isNop()) {
            float f = magnificationSpec2.scale;
            matrix.postScale(f, f);
            matrix.postTranslate(magnificationSpec2.offsetX, magnificationSpec2.offsetY);
        }
        matrix.getValues(fArr);
        return new Pair(fArr, (MagnificationSpec) windowTransformationMatrixAndMagnificationSpec.second);
    }

    public final IAccessibilityManager.WindowTransformationSpec getWindowTransformationSpec(int i) {
        IAccessibilityManager.WindowTransformationSpec windowTransformationSpec = new IAccessibilityManager.WindowTransformationSpec();
        Pair windowTransformationMatrixAndMagnificationSpec = getWindowTransformationMatrixAndMagnificationSpec(i);
        windowTransformationSpec.transformationMatrix = (float[]) windowTransformationMatrixAndMagnificationSpec.first;
        windowTransformationSpec.magnificationSpec = (MagnificationSpec) windowTransformationMatrixAndMagnificationSpec.second;
        return windowTransformationSpec;
    }

    public final void init() {
        GestureWakeup gestureWakeup;
        boolean z;
        boolean z2;
        boolean z3;
        final int i = 2;
        final int i2 = 3;
        final int i3 = 0;
        final int i4 = 1;
        this.mSecurityPolicy.mAccessibilityWindowManager = this.mA11yWindowManager;
        ManagerPackageMonitor managerPackageMonitor = new ManagerPackageMonitor(this);
        this.mPackageMonitor = managerPackageMonitor;
        Context context = this.mContext;
        UserHandle userHandle = UserHandle.ALL;
        managerPackageMonitor.register(context, (Looper) null, userHandle, true);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.USER_SWITCHED");
        intentFilter.addAction("android.intent.action.USER_UNLOCKED");
        intentFilter.addAction("android.intent.action.USER_REMOVED");
        intentFilter.addAction("android.os.action.SETTING_RESTORED");
        Flags.managerAvoidReceiverTimeout();
        this.mContext.registerReceiverAsUser(new BroadcastReceiver() { // from class: com.android.server.accessibility.AccessibilityManagerService.2
            /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
            /* JADX WARN: Code restructure failed: missing block: B:125:0x0204, code lost:
            
                if (r13.equals("accessibility_button_targets") == false) goto L68;
             */
            @Override // android.content.BroadcastReceiver
            /*
                Code decompiled incorrectly, please refer to instructions dump.
                To view partially-correct code enable 'Show inconsistent code' option in preferences
            */
            public final void onReceive(android.content.Context r13, android.content.Intent r14) {
                /*
                    Method dump skipped, instructions count: 674
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.server.accessibility.AccessibilityManagerService.AnonymousClass2.onReceive(android.content.Context, android.content.Intent):void");
            }
        }, userHandle, intentFilter, null, BackgroundThread.getHandler());
        this.mContext.registerReceiverAsUser(new BroadcastReceiver() { // from class: com.android.server.accessibility.AccessibilityManagerService.2
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                /*
                    Method dump skipped, instructions count: 674
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.server.accessibility.AccessibilityManagerService.AnonymousClass2.onReceive(android.content.Context, android.content.Intent):void");
            }
        }, userHandle, BatteryService$$ExternalSyntheticOutline0.m("android.safetycenter.action.SAFETY_CENTER_ENABLED_CHANGED"), null, this.mMainHandler, 2);
        if (!android.companion.virtual.flags.Flags.vdmPublicApis()) {
            this.mContext.registerReceiver(new BroadcastReceiver() { // from class: com.android.server.accessibility.AccessibilityManagerService.2
                @Override // android.content.BroadcastReceiver
                public final void onReceive(Context context2, Intent intent) {
                    /*
                        Method dump skipped, instructions count: 674
                        To view this dump change 'Code comments level' option to 'DEBUG'
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.server.accessibility.AccessibilityManagerService.AnonymousClass2.onReceive(android.content.Context, android.content.Intent):void");
                }
            }, new IntentFilter("android.companion.virtual.action.VIRTUAL_DEVICE_REMOVED"), 4);
        }
        AccessibilityContentObserver accessibilityContentObserver = new AccessibilityContentObserver(this.mMainHandler);
        ContentResolver contentResolver = this.mContext.getContentResolver();
        contentResolver.registerContentObserver(accessibilityContentObserver.mTouchExplorationEnabledUri, false, accessibilityContentObserver, -1);
        contentResolver.registerContentObserver(accessibilityContentObserver.mMagnificationmSingleFingerTripleTapEnabledUri, false, accessibilityContentObserver, -1);
        Flags.enableMagnificationMultipleFingerMultipleTapGesture();
        contentResolver.registerContentObserver(accessibilityContentObserver.mAutoclickEnabledUri, false, accessibilityContentObserver, -1);
        contentResolver.registerContentObserver(accessibilityContentObserver.mEnabledAccessibilityServicesUri, false, accessibilityContentObserver, -1);
        contentResolver.registerContentObserver(accessibilityContentObserver.mTouchExplorationGrantedAccessibilityServicesUri, false, accessibilityContentObserver, -1);
        contentResolver.registerContentObserver(accessibilityContentObserver.mHighTextContrastUri, false, accessibilityContentObserver, -1);
        contentResolver.registerContentObserver(accessibilityContentObserver.mAudioDescriptionByDefaultUri, false, accessibilityContentObserver, -1);
        contentResolver.registerContentObserver(accessibilityContentObserver.mAccessibilitySoftKeyboardModeUri, false, accessibilityContentObserver, -1);
        contentResolver.registerContentObserver(accessibilityContentObserver.mShowImeWithHardKeyboardUri, false, accessibilityContentObserver, -1);
        contentResolver.registerContentObserver(accessibilityContentObserver.mAccessibilityShortcutServiceIdUri, false, accessibilityContentObserver, -1);
        contentResolver.registerContentObserver(accessibilityContentObserver.mAccessibilityButtonComponentIdUri, false, accessibilityContentObserver, -1);
        contentResolver.registerContentObserver(accessibilityContentObserver.mAccessibilityButtonTargetsUri, false, accessibilityContentObserver, -1);
        contentResolver.registerContentObserver(accessibilityContentObserver.mUserNonInteractiveUiTimeoutUri, false, accessibilityContentObserver, -1);
        contentResolver.registerContentObserver(accessibilityContentObserver.mUserInteractiveUiTimeoutUri, false, accessibilityContentObserver, -1);
        contentResolver.registerContentObserver(accessibilityContentObserver.mMagnificationModeUri, false, accessibilityContentObserver, -1);
        contentResolver.registerContentObserver(accessibilityContentObserver.mMagnificationCapabilityUri, false, accessibilityContentObserver, -1);
        contentResolver.registerContentObserver(accessibilityContentObserver.mMagnificationFollowTypingUri, false, accessibilityContentObserver, -1);
        contentResolver.registerContentObserver(accessibilityContentObserver.mAlwaysOnMagnificationUri, false, accessibilityContentObserver, -1);
        contentResolver.registerContentObserver(accessibilityContentObserver.mAutoActionEnabledUri, false, accessibilityContentObserver, -1);
        contentResolver.registerContentObserver(accessibilityContentObserver.mCornerActionEnabledUri, false, accessibilityContentObserver, -1);
        contentResolver.registerContentObserver(accessibilityContentObserver.mScreenCurtainEnabledUri, false, accessibilityContentObserver, -1);
        contentResolver.registerContentObserver(accessibilityContentObserver.mTapDurationEnabledUri, false, accessibilityContentObserver, -1);
        contentResolver.registerContentObserver(accessibilityContentObserver.mTouchBlockingEnabledUri, false, accessibilityContentObserver, -1);
        contentResolver.registerContentObserver(accessibilityContentObserver.mBounceKeysThresholdUri, false, accessibilityContentObserver, -1);
        contentResolver.registerContentObserver(accessibilityContentObserver.mSlowKeysThresholdUri, false, accessibilityContentObserver, -1);
        contentResolver.registerContentObserver(accessibilityContentObserver.mStickyKeysUri, false, accessibilityContentObserver, -1);
        contentResolver.registerContentObserver(accessibilityContentObserver.mNaviBarModeUri, false, accessibilityContentObserver, -1);
        contentResolver.registerContentObserver(accessibilityContentObserver.mAmplifyAmbientSountEnableUri, false, accessibilityContentObserver, -1);
        contentResolver.registerContentObserver(accessibilityContentObserver.mHighContrastTextEnableUri, false, accessibilityContentObserver, -1);
        contentResolver.registerContentObserver(accessibilityContentObserver.mColorLensEnableUri, false, accessibilityContentObserver, -1);
        contentResolver.registerContentObserver(accessibilityContentObserver.mColorLensTypeUri, false, accessibilityContentObserver, -1);
        contentResolver.registerContentObserver(accessibilityContentObserver.mColorLensOpacityUri, false, accessibilityContentObserver, -1);
        contentResolver.registerContentObserver(accessibilityContentObserver.mColorReluminoEnableUri, false, accessibilityContentObserver, -1);
        contentResolver.registerContentObserver(accessibilityContentObserver.mColorReluminoTypeUri, false, accessibilityContentObserver, -1);
        contentResolver.registerContentObserver(accessibilityContentObserver.mColorReluminoEdgeThinknessUri, false, accessibilityContentObserver, -1);
        contentResolver.registerContentObserver(accessibilityContentObserver.mAccessibilityDirectAccessServiceIdUri, false, accessibilityContentObserver, -1);
        contentResolver.registerContentObserver(accessibilityContentObserver.mA11yAMEnableUri, false, accessibilityContentObserver, -1);
        contentResolver.registerContentObserver(accessibilityContentObserver.mA11yAMMagnificationEnableUri, false, accessibilityContentObserver, -1);
        contentResolver.registerContentObserver(accessibilityContentObserver.mOneHandModeActivateUri, false, accessibilityContentObserver, -1);
        disableAccessibilityMenuToMigrateIfNeeded();
        CVDCalculator cVDCalculator = new CVDCalculator();
        cVDCalculator.u = new double[]{-21.54d, -23.26d, -22.41d, -23.11d, -22.45d, -21.76d, -14.08d, -2.72d, 14.84d, 23.87d, 31.82d, 31.42d, 29.79d, 26.64d, 22.92d, 11.2d};
        cVDCalculator.v = new double[]{-38.39d, -25.56d, -15.53d, -7.45d, 1.1d, 7.35d, 18.74d, 28.13d, 31.13d, 26.35d, 14.76d, 6.99d, 0.1d, -9.38d, -18.65d, -24.61d};
        cVDCalculator.SpotsU = new double[16];
        cVDCalculator.SpotsV = new double[16];
        cVDCalculator.mInputNums = null;
        cVDCalculator.mColorTransferTable = new ColorTransferTable();
        cVDCalculator.CVDType = 3;
        this.cvdCalculator = cVDCalculator;
        Context context2 = this.mContext;
        synchronized (GestureWakeup.class) {
            try {
                if (GestureWakeup.sGesturewakeup == null) {
                    GestureWakeup gestureWakeup2 = new GestureWakeup();
                    gestureWakeup2.mIsSettingGestureWakeUp = false;
                    gestureWakeup2.mCoverManager = null;
                    gestureWakeup2.mSemContextManager = null;
                    gestureWakeup2.mSemContextListener = new SemContextListener() { // from class: com.android.server.accessibility.GestureWakeup.1
                        public AnonymousClass1() {
                        }

                        public final void onSemContextChanged(SemContextEvent semContextEvent) {
                            SemContextApproach approachContext = semContextEvent.getApproachContext();
                            int currentUser = ActivityManager.getCurrentUser();
                            int userID = approachContext.getUserID();
                            if (semContextEvent.semContext.getType() == 1) {
                                StringBuilder m = ArrayUtils$$ExternalSyntheticOutline0.m(userID, currentUser, "onSContextChanged() Approach, userId : ", " , currentUser : ", ", setting : ");
                                GestureWakeup gestureWakeup3 = GestureWakeup.this;
                                m.append(gestureWakeup3.checkSettingCondition(gestureWakeup3.mContext));
                                Log.secD("GestureWakeup", m.toString());
                                GestureWakeup gestureWakeup4 = GestureWakeup.this;
                                if (gestureWakeup4.checkSettingCondition(gestureWakeup4.mContext) && userID == currentUser) {
                                    GestureWakeup gestureWakeup5 = GestureWakeup.this;
                                    gestureWakeup5.getClass();
                                    Log.d("GestureWakeup", "launchGestureWakeup()+");
                                    if (((SemDesktopModeManager) gestureWakeup5.mContext.getSystemService("desktopmode")) != null && SemDesktopModeManager.isDesktopMode()) {
                                        Log.d("GestureWakeup", "launchGestureWakeup() :: No action in Desktop mode+");
                                        return;
                                    }
                                    TelephonyManager telephonyManager = (TelephonyManager) gestureWakeup5.mContext.getSystemService("phone");
                                    if (telephonyManager.getCallState() == 2 || telephonyManager.getCallState() == 1) {
                                        Log.d("GestureWakeup", "CALL_STATE_OFFHOOK or CALL_STATE_RINGING : " + telephonyManager.getCallState());
                                        return;
                                    }
                                    CoverState coverState = gestureWakeup5.mCoverManager.getCoverState();
                                    if (coverState != null) {
                                        try {
                                            if (!coverState.getSwitchState()) {
                                                Log.d("GestureWakeup", "..Cover is closed ..");
                                                return;
                                            }
                                        } catch (Exception e) {
                                            e.printStackTrace();
                                        }
                                    }
                                    if (gestureWakeup5.mPM == null) {
                                        gestureWakeup5.mPM = (PowerManager) gestureWakeup5.mContext.getSystemService("power");
                                    }
                                    gestureWakeup5.mPM.semWakeUp(SystemClock.uptimeMillis(), 7, "WAKE_UP_REASON_GESTURE");
                                    Context context3 = gestureWakeup5.mContext;
                                    if (SemFloatingFeature.getInstance().getBoolean("SEC_FLOATING_FEATURE_CONTEXTSERVICE_ENABLE_SURVEY_MODE")) {
                                        ContentValues contentValues = new ContentValues();
                                        contentValues.put("app_id", "com.samsung.android.app.airwakeupview");
                                        contentValues.put(LauncherConfigurationInternal.KEY_FEATURE_INT, "ACC3");
                                        Intent intent = new Intent();
                                        intent.setAction("com.samsung.android.providers.context.log.action.USE_APP_FEATURE_SURVEY");
                                        intent.putExtra("data", contentValues);
                                        intent.setPackage("com.samsung.android.providers.context");
                                        context3.sendBroadcast(intent);
                                    }
                                }
                            }
                        }
                    };
                    gestureWakeup2.mContext = context2;
                    GestureWakeup.sGesturewakeup = gestureWakeup2;
                }
                gestureWakeup = GestureWakeup.sGesturewakeup;
            } catch (Throwable th) {
                throw th;
            }
        }
        this.mGesturewakeup = gestureWakeup;
        if (gestureWakeup.checkSettingCondition(this.mContext)) {
            this.mGesturewakeup.StartGestureWakeup();
        }
        if (Settings.System.getIntForUser(this.mContext.getContentResolver(), "show_a11y_button", 0, -2) == 1) {
            Settings.Global.putInt(this.mContext.getContentResolver(), "navigation_bar_gesture_while_hidden", 0);
            Settings.System.putIntForUser(this.mContext.getContentResolver(), "show_a11y_button", 0, -2);
        }
        this.mContext.registerReceiver(new BroadcastReceiver() { // from class: com.android.server.accessibility.AccessibilityManagerService.2
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context3, Intent intent) {
                /*
                    Method dump skipped, instructions count: 674
                    To view this dump change 'Code comments level' option to 'DEBUG'
                */
                throw new UnsupportedOperationException("Method not decompiled: com.android.server.accessibility.AccessibilityManagerService.AnonymousClass2.onReceive(android.content.Context, android.content.Intent):void");
            }
        }, DirEncryptServiceHelper$$ExternalSyntheticOutline0.m("android.intent.action.USER_SWITCHED", "android.intent.action.BOOT_COMPLETED"));
        try {
            this.mContext.getPackageManager().getApplicationInfo("com.samsung.android.accessibility.talkback", 128);
            z = true;
        } catch (PackageManager.NameNotFoundException unused) {
            z = false;
        }
        if (z) {
            String stringForUser = Settings.Secure.getStringForUser(this.mContext.getContentResolver(), "enabled_accessibility_services", -2);
            String stringForUser2 = Settings.Secure.getStringForUser(this.mContext.getContentResolver(), "accessibility_shortcut_target_service", -2);
            if (stringForUser != null && !stringForUser.isEmpty()) {
                try {
                    if (stringForUser.matches("(?i).*com.samsung.accessibility/com.samsung.android.app.talkback.TalkBackService.*")) {
                        stringForUser = stringForUser.replace("com.samsung.accessibility/com.samsung.android.app.talkback.TalkBackService", "com.samsung.android.accessibility.talkback/com.samsung.android.marvin.talkback.TalkBackService");
                        z3 = true;
                    } else {
                        z3 = false;
                    }
                    if (stringForUser.matches("(?i).*com.google.android.marvin.talkback/com.google.android.marvin.talkback.TalkBackService.*")) {
                        stringForUser = stringForUser.replace("com.google.android.marvin.talkback/com.google.android.marvin.talkback.TalkBackService", "com.samsung.android.accessibility.talkback/com.samsung.android.marvin.talkback.TalkBackService");
                        z3 = true;
                    }
                    if (z3) {
                        Log.d("AccessibilityManagerService", "Change Accessibility ComponentName of ENABLED_ACCESSIBILITY_SERVICES");
                        Settings.Secure.putStringForUser(this.mContext.getContentResolver(), "enabled_accessibility_services", stringForUser, -2);
                    }
                } catch (Exception e) {
                    Log.d("AccessibilityManagerService", "changeAccessibilityComponentNameIfNeed() exception : " + e);
                }
            }
            if (stringForUser2 != null && !stringForUser2.isEmpty()) {
                try {
                    if (stringForUser2.matches("(?i).*com.samsung.accessibility/com.samsung.android.app.talkback.TalkBackService.*")) {
                        stringForUser2 = stringForUser2.replace("com.samsung.accessibility/com.samsung.android.app.talkback.TalkBackService", "com.samsung.android.accessibility.talkback/com.samsung.android.marvin.talkback.TalkBackService");
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    if (stringForUser2.matches("(?i).*com.google.android.marvin.talkback/com.google.android.marvin.talkback.TalkBackService.*")) {
                        stringForUser2 = stringForUser2.replace("com.google.android.marvin.talkback/com.google.android.marvin.talkback.TalkBackService", "com.samsung.android.accessibility.talkback/com.samsung.android.marvin.talkback.TalkBackService");
                        z2 = true;
                    }
                    if (z2) {
                        Log.d("AccessibilityManagerService", "Change Accessibility ComponentName of ACCESSIBILITY_SHORTCUT_TARGET_SERVICE");
                        Settings.Secure.putString(this.mContext.getContentResolver(), "accessibility_shortcut_target_service", stringForUser2);
                    }
                } catch (Exception unused2) {
                }
            }
        } else {
            Log.d("AccessibilityManagerService", "Package com.samsung.android.accessibility.talkback is not installed");
        }
        Settings.Secure.putIntForUser(this.mContext.getContentResolver(), "accessibility_magnification_activated", 0, -2);
        this.mContext.getContentResolver().registerContentObserver(this.mAodShowStateUri, false, new AODStateContentObserver(0, this), -1);
        Slog.i("AccessibilityManagerService", "register AODStateContentObserver");
        if (Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "user_setup_complete", 0, -2) == 0) {
            this.mColorInversionStateContentObserver = new AODStateContentObserver(1, this);
            this.mContext.getContentResolver().registerContentObserver(this.mColorInversionStateUri, false, this.mColorInversionStateContentObserver, -1);
            this.mSetupCompleteStateContentObserver = new AODStateContentObserver(4, this);
            this.mContext.getContentResolver().registerContentObserver(this.mSetupCompleteUri, false, this.mSetupCompleteStateContentObserver, -1);
            this.mDaltonizerEnabledStateContentObserver = new AODStateContentObserver(2, this);
            this.mContext.getContentResolver().registerContentObserver(this.mDisplayDaltonizerEnabledUri, false, this.mDaltonizerEnabledStateContentObserver, -1);
            this.mDaltonizerStateContentObserver = new AODStateContentObserver(3, this);
            this.mContext.getContentResolver().registerContentObserver(this.mDisplayDaltonizerUri, false, this.mDaltonizerStateContentObserver, -1);
            if (Flags.enableColorCorrectionSaturation()) {
                this.mContext.getContentResolver().registerContentObserver(this.mDisplayDaltonizerSaturationLevelUri, false, this.mDaltonizerStateContentObserver, -1);
            }
        }
    }

    public final void injectInputEventToInputFilter(InputEvent inputEvent) {
        AccessibilityInputFilter accessibilityInputFilter;
        injectInputEventToInputFilter_enforcePermission();
        synchronized (this.mLock) {
            long uptimeMillis = SystemClock.uptimeMillis() + 1000;
            while (!this.mInputFilterInstalled && SystemClock.uptimeMillis() < uptimeMillis) {
                try {
                    this.mLock.wait(uptimeMillis - SystemClock.uptimeMillis());
                } catch (InterruptedException unused) {
                }
            }
        }
        if (!this.mInputFilterInstalled || (accessibilityInputFilter = this.mInputFilter) == null) {
            Slog.w("AccessibilityManagerService", "Cannot injectInputEventToInputFilter because the AccessibilityInputFilter is not installed.");
        } else {
            accessibilityInputFilter.onInputEvent(inputEvent, 1090519040);
        }
    }

    public final void interrupt(int i) {
        ArrayList arrayList;
        if (this.mTraceManager.isA11yTracingEnabledForTypes(4L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.interrupt", 4L, VibrationParam$1$$ExternalSyntheticOutline0.m(i, "userId="));
        }
        synchronized (this.mLock) {
            try {
                int resolveCallingUserIdEnforcingPermissionsLocked = this.mSecurityPolicy.resolveCallingUserIdEnforcingPermissionsLocked(i);
                if (resolveCallingUserIdEnforcingPermissionsLocked != this.mCurrentUserId) {
                    return;
                }
                int firstDeviceIdForUidLocked = this.mProxyManager.getFirstDeviceIdForUidLocked(Binder.getCallingUid());
                if (this.mProxyManager.isProxyedDeviceId(firstDeviceIdForUidLocked)) {
                    arrayList = new ArrayList();
                    ProxyManager proxyManager = this.mProxyManager;
                    for (int i2 = 0; i2 < proxyManager.mProxyA11yServiceConnections.size(); i2++) {
                        ProxyAccessibilityServiceConnection proxyAccessibilityServiceConnection = (ProxyAccessibilityServiceConnection) proxyManager.mProxyA11yServiceConnections.valueAt(i2);
                        if (proxyAccessibilityServiceConnection != null && proxyAccessibilityServiceConnection.mDeviceId == firstDeviceIdForUidLocked) {
                            IBinder iBinder = proxyAccessibilityServiceConnection.mService;
                            IAccessibilityServiceClient iAccessibilityServiceClient = proxyAccessibilityServiceConnection.mServiceInterface;
                            if (iBinder != null && iAccessibilityServiceClient != null) {
                                arrayList.add(iAccessibilityServiceClient);
                            }
                        }
                    }
                } else {
                    ArrayList arrayList2 = getUserStateLocked(resolveCallingUserIdEnforcingPermissionsLocked).mBoundServices;
                    ArrayList arrayList3 = new ArrayList(arrayList2.size());
                    for (int i3 = 0; i3 < arrayList2.size(); i3++) {
                        AccessibilityServiceConnection accessibilityServiceConnection = (AccessibilityServiceConnection) arrayList2.get(i3);
                        IBinder iBinder2 = accessibilityServiceConnection.mService;
                        IAccessibilityServiceClient iAccessibilityServiceClient2 = accessibilityServiceConnection.mServiceInterface;
                        if (iBinder2 != null && iAccessibilityServiceClient2 != null) {
                            arrayList3.add(iAccessibilityServiceClient2);
                        }
                    }
                    arrayList = arrayList3;
                }
                int size = arrayList.size();
                for (int i4 = 0; i4 < size; i4++) {
                    try {
                        if (this.mTraceManager.isA11yTracingEnabledForTypes(2L)) {
                            this.mTraceManager.logTrace("AccessibilityManagerService.IAccessibilityServiceClient.onInterrupt", 2L);
                        }
                        ((IAccessibilityServiceClient) arrayList.get(i4)).onInterrupt();
                    } catch (RemoteException e) {
                        Slog.e("AccessibilityManagerService", "Error sending interrupt request to " + arrayList.get(i4), e);
                    }
                }
            } finally {
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x0084 A[RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean isAccessibilityServiceWarningRequired(android.accessibilityservice.AccessibilityServiceInfo r8) {
        /*
            r7 = this;
            r7.isAccessibilityServiceWarningRequired_enforcePermission()
            android.content.ComponentName r0 = r8.getComponentName()
            java.lang.Object r1 = r7.mLock
            monitor-enter(r1)
            int r2 = r7.mCurrentUserId     // Catch: java.lang.Throwable -> L1d
            com.android.server.accessibility.AccessibilityUserState r2 = r7.getUserStateLocked(r2)     // Catch: java.lang.Throwable -> L1d
            java.util.Set r2 = r2.mEnabledServices     // Catch: java.lang.Throwable -> L1d
            java.util.HashSet r2 = (java.util.HashSet) r2     // Catch: java.lang.Throwable -> L1d
            boolean r2 = r2.contains(r0)     // Catch: java.lang.Throwable -> L1d
            r3 = 0
            if (r2 == 0) goto L1f
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L1d
            return r3
        L1d:
            r7 = move-exception
            goto L86
        L1f:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L1d
            int[] r1 = com.android.internal.accessibility.common.ShortcutConstants.USER_SHORTCUT_TYPES
            int r2 = r1.length
            r4 = r3
        L24:
            if (r4 >= r2) goto L3c
            r5 = r1[r4]
            java.util.List r5 = r7.getAccessibilityShortcutTargets(r5)
            java.lang.String r6 = r0.flattenToString()
            java.util.ArrayList r5 = (java.util.ArrayList) r5
            boolean r5 = r5.contains(r6)
            if (r5 == 0) goto L39
            return r3
        L39:
            int r4 = r4 + 1
            goto L24
        L3c:
            boolean r0 = android.view.accessibility.Flags.skipAccessibilityWarningDialogForTrustedServices()
            r1 = 1
            if (r0 == 0) goto L85
            android.content.ComponentName r0 = r8.getComponentName()
            android.content.pm.ResolveInfo r8 = r8.getResolveInfo()
            android.content.pm.ServiceInfo r8 = r8.serviceInfo
            android.content.pm.ApplicationInfo r8 = r8.applicationInfo
            boolean r8 = r8.isSystemApp()
            if (r8 == 0) goto L81
            android.content.Context r7 = r7.mContext
            android.content.res.Resources r7 = r7.getResources()
            r8 = 17236336(0x1070170, float:2.4796615E-38)
            java.lang.String[] r7 = r7.getStringArray(r8)
            java.util.stream.Stream r7 = java.util.Arrays.stream(r7)
            com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda5 r8 = new com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda5
            r2 = 3
            r8.<init>(r2)
            java.util.stream.Stream r7 = r7.map(r8)
            java.util.Objects.requireNonNull(r0)
            com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda42 r8 = new com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda42
            r2 = 0
            r8.<init>(r2, r0)
            boolean r7 = r7.anyMatch(r8)
            if (r7 == 0) goto L81
            r7 = r1
            goto L82
        L81:
            r7 = r3
        L82:
            if (r7 == 0) goto L85
            return r3
        L85:
            return r1
        L86:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L1d
            throw r7
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.accessibility.AccessibilityManagerService.isAccessibilityServiceWarningRequired(android.accessibilityservice.AccessibilityServiceInfo):boolean");
    }

    public final boolean isAccessibilityTargetAllowed(String str, int i, int i2) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            List permittedAccessibilityServices = ((DevicePolicyManager) this.mContext.getSystemService(DevicePolicyManager.class)).getPermittedAccessibilityServices(i2);
            if (permittedAccessibilityServices != null && !permittedAccessibilityServices.contains(str)) {
                return false;
            }
            if (com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags.enhancedConfirmationModeApisEnabled() && android.security.Flags.extendEcmToAllSettings()) {
                return !((EnhancedConfirmationManager) this.mContext.getSystemService(EnhancedConfirmationManager.class)).isRestricted(str, "android:bind_accessibility_service");
            }
            try {
                int noteOpNoThrow = ((AppOpsManager) this.mContext.getSystemService(AppOpsManager.class)).noteOpNoThrow(119, i, str);
                return !this.mContext.getResources().getBoolean(R.bool.config_focusScrollContainersInTouchMode) || noteOpNoThrow == 0 || noteOpNoThrow == 3;
            } catch (Exception unused) {
                return false;
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("AccessibilityManagerService", "Exception when retrieving package:" + str, e);
            return false;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isActivatedMagnification() {
        isActivatedMagnification_enforcePermission();
        return this.mMagnificationController.isActivated(getDisplayId(), getUserStateLocked(this.mCurrentUserId).getMagnificationModeLocked(getDisplayId()));
    }

    public final boolean isAudioDescriptionByDefaultEnabled() {
        boolean z;
        if (this.mTraceManager.isA11yTracingEnabledForTypes(4L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.isAudioDescriptionByDefaultEnabled", 4L);
        }
        synchronized (this.mLock) {
            z = getUserStateLocked(this.mCurrentUserId).mIsAudioDescriptionByDefaultRequested;
        }
        return z;
    }

    public final boolean isCameraFlashNotificationRunning() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mFlashNotificationsController.mIsCameraFlashNotificationRunning;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean isScreenReaderEnabled() {
        String stringForUser = Settings.Secure.getStringForUser(this.mContext.getContentResolver(), "enabled_accessibility_services", this.mCurrentUserId);
        if (stringForUser != null) {
            r0 = stringForUser.matches("(?i).*com.samsung.accessibility/com.samsung.android.app.talkback.TalkBackService.*") || stringForUser.matches("(?i).*com.google.android.marvin.talkback.TalkBackService.*") || stringForUser.matches("(?i).*com.google.android.marvin.talkback/.TalkBackService.*") || stringForUser.matches("(?i).*com.samsung.android.accessibility.talkback/com.samsung.android.marvin.talkback.TalkBackService.*");
            Slog.i("AccessibilityManagerService", "isScreenReaderEnable : " + r0 + ", accesibilityService : " + stringForUser);
        }
        return r0;
    }

    public final boolean isSystemAudioCaptioningUiEnabled(int i) {
        return this.mCaptioningManagerImpl.isSystemAudioCaptioningUiEnabled(i);
    }

    public final boolean isTwoFingerGestureRecognitionEnabled() {
        return this.shouldRecogniseTwoFingerGestures;
    }

    public final void migrateAccessibilityButtonSettingsIfNecessaryLocked(final AccessibilityUserState accessibilityUserState, final String str, int i) {
        if (i > 29) {
            return;
        }
        final LinkedHashSet linkedHashSet = accessibilityUserState.mAccessibilityButtonTargets;
        int size = linkedHashSet.size();
        linkedHashSet.removeIf(new Predicate() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda10
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                ComponentName unflattenFromString;
                AccessibilityServiceInfo installedServiceInfoLocked;
                String str2 = str;
                AccessibilityUserState accessibilityUserState2 = accessibilityUserState;
                String str3 = (String) obj;
                if ((str2 != null && str3 != null && !str3.contains(str2)) || (unflattenFromString = ComponentName.unflattenFromString(str3)) == null || (installedServiceInfoLocked = accessibilityUserState2.getInstalledServiceInfoLocked(unflattenFromString)) == null) {
                    return false;
                }
                if (installedServiceInfoLocked.getResolveInfo().serviceInfo.applicationInfo.targetSdkVersion <= 29) {
                    Slog.v("AccessibilityManagerService", "Legacy service " + unflattenFromString + " should not in the button");
                } else {
                    if ((installedServiceInfoLocked.flags & 256) == 0 || ((HashSet) accessibilityUserState2.mEnabledServices).contains(unflattenFromString)) {
                        return false;
                    }
                    Slog.v("AccessibilityManagerService", "Service requesting a11y button and be assigned to the button" + unflattenFromString + " should be enabled state");
                }
                return true;
            }
        });
        boolean z = size != linkedHashSet.size();
        int size2 = linkedHashSet.size();
        final LinkedHashSet linkedHashSet2 = accessibilityUserState.mAccessibilityShortcutKeyTargets;
        final LinkedHashSet linkedHashSet3 = accessibilityUserState.mAccessibilityDirectAccessTargets;
        final LinkedHashSet linkedHashSet4 = new LinkedHashSet(accessibilityUserState.mAccessibilityQsTargets);
        accessibilityUserState.mEnabledServices.forEach(new Consumer() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda11
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                AccessibilityServiceInfo installedServiceInfoLocked;
                String str2 = str;
                AccessibilityUserState accessibilityUserState2 = accessibilityUserState;
                Set set = linkedHashSet;
                Set set2 = linkedHashSet2;
                Set set3 = linkedHashSet3;
                Set set4 = linkedHashSet4;
                ComponentName componentName = (ComponentName) obj;
                if ((str2 == null || componentName == null || str2.equals(componentName.getPackageName())) && (installedServiceInfoLocked = accessibilityUserState2.getInstalledServiceInfoLocked(componentName)) != null) {
                    boolean z2 = (installedServiceInfoLocked.flags & 256) != 0;
                    if (installedServiceInfoLocked.getResolveInfo().serviceInfo.applicationInfo.targetSdkVersion <= 29 || !z2) {
                        return;
                    }
                    String flattenToString = componentName.flattenToString();
                    if (TextUtils.isEmpty(flattenToString) || AccessibilityUserState.doesShortcutTargetsStringContain(flattenToString, set) || AccessibilityUserState.doesShortcutTargetsStringContain(flattenToString, set2) || AccessibilityUserState.doesShortcutTargetsStringContain(flattenToString, set3) || AccessibilityUserState.doesShortcutTargetsStringContain(flattenToString, set4)) {
                        return;
                    }
                    Slog.v("AccessibilityManagerService", "A enabled service requesting a11y button " + componentName + " should be assign to the button or shortcut.");
                    set.add(flattenToString);
                }
            }
        });
        if (!z && !(size2 != linkedHashSet.size())) {
            return;
        }
        persistColonDelimitedSetToSettingLocked("accessibility_button_targets", accessibilityUserState.mUserId, linkedHashSet, new AccessibilityManagerService$$ExternalSyntheticLambda5(0), null);
        scheduleNotifyClientsOfServicesStateChangeLocked(accessibilityUserState);
    }

    public final void notifyAccessibilityButtonClicked(int i, String str) {
        notifyAccessibilityButtonClicked_enforcePermission();
        if (this.mTraceManager.isA11yTracingEnabledForTypes(4L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.notifyAccessibilityButtonClicked", 4L, AccessibilityManagerService$$ExternalSyntheticOutline0.m(i, "displayId=", ";targetName=", str));
        }
        if (Settings.System.getIntForUser(this.mContext.getContentResolver(), "access_control_enabled", 0, -2) == 1) {
            return;
        }
        if (str == null) {
            synchronized (this.mLock) {
                str = getUserStateLocked(this.mCurrentUserId).mTargetAssignedToAccessibilityButton;
            }
        }
        this.mMainHandler.sendMessage(PooledLambda.obtainMessage(new AccessibilityManagerService$$ExternalSyntheticLambda14(0), this, Integer.valueOf(i), 1, str));
    }

    public final void notifyAccessibilityButtonVisibilityChanged(boolean z) {
        notifyAccessibilityButtonVisibilityChanged_enforcePermission();
        if (this.mTraceManager.isA11yTracingEnabledForTypes(4L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.notifyAccessibilityButtonVisibilityChanged", 4L, "shown=" + z);
        }
        synchronized (this.mLock) {
            AccessibilityUserState userStateLocked = getUserStateLocked(this.mCurrentUserId);
            this.mIsAccessibilityButtonShown = z;
            for (int size = userStateLocked.mBoundServices.size() - 1; size >= 0; size--) {
                AccessibilityServiceConnection accessibilityServiceConnection = (AccessibilityServiceConnection) userStateLocked.mBoundServices.get(size);
                if (accessibilityServiceConnection.mRequestAccessibilityButton) {
                    accessibilityServiceConnection.mInvocationHandler.obtainMessage(8, accessibilityServiceConnection.isAccessibilityButtonAvailableLocked() ? 1 : 0, 0).sendToTarget();
                }
            }
        }
    }

    public final void notifyAccessibilityServicesDelayedLocked(AccessibilityEvent accessibilityEvent, boolean z) {
        try {
            AccessibilityUserState userStateLocked = getUserStateLocked(this.mCurrentUserId);
            int size = userStateLocked.mBoundServices.size();
            for (int i = 0; i < size; i++) {
                AccessibilityServiceConnection accessibilityServiceConnection = (AccessibilityServiceConnection) userStateLocked.mBoundServices.get(i);
                if (accessibilityServiceConnection.mIsDefault == z) {
                    accessibilityServiceConnection.notifyAccessibilityEvent(accessibilityEvent);
                }
            }
        } catch (IndexOutOfBoundsException unused) {
        }
    }

    public final void notifyClearAccessibilityCacheLocked() {
        AccessibilityUserState userStateLocked = getUserStateLocked(this.mCurrentUserId);
        int size = userStateLocked.mBoundServices.size();
        while (true) {
            size--;
            if (size < 0) {
                break;
            } else {
                ((AccessibilityServiceConnection) userStateLocked.mBoundServices.get(size)).mInvocationHandler.sendEmptyMessage(2);
            }
        }
        ProxyManager proxyManager = this.mProxyManager;
        for (int i = 0; i < proxyManager.mProxyA11yServiceConnections.size(); i++) {
            ((ProxyAccessibilityServiceConnection) proxyManager.mProxyA11yServiceConnections.valueAt(i)).mInvocationHandler.sendEmptyMessage(2);
        }
    }

    public final void notifyClientsOfServicesStateChange(RemoteCallbackList remoteCallbackList, final long j) {
        if (this.mTraceManager.isA11yTracingEnabledForTypes(8L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.notifyClientsOfServicesStateChange", 8L, DeviceIdleController$$ExternalSyntheticOutline0.m(j, "uiTimeout="));
        }
        remoteCallbackList.broadcastForEachCookie(FunctionalUtils.ignoreRemoteException(new FunctionalUtils.RemoteExceptionIgnoringConsumer() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda53
            public final void acceptOrThrow(Object obj) {
                AccessibilityManagerService accessibilityManagerService = AccessibilityManagerService.this;
                long j2 = j;
                accessibilityManagerService.getClass();
                AccessibilityManagerService.Client client = (AccessibilityManagerService.Client) obj;
                if (accessibilityManagerService.mProxyManager.isProxyedDeviceId(client.mDeviceId)) {
                    return;
                }
                client.mCallback.notifyServicesStateChanged(j2);
            }
        }));
    }

    public final boolean notifyGestureLocked(AccessibilityGestureEvent accessibilityGestureEvent, boolean z) {
        AccessibilityUserState userStateLocked = getUserStateLocked(this.mCurrentUserId);
        for (int size = userStateLocked.mBoundServices.size() - 1; size >= 0; size--) {
            AccessibilityServiceConnection accessibilityServiceConnection = (AccessibilityServiceConnection) userStateLocked.mBoundServices.get(size);
            if (accessibilityServiceConnection.mRequestTouchExplorationMode && accessibilityServiceConnection.mIsDefault == z) {
                if (android.view.accessibility.Flags.copyEventsForGestureDetection()) {
                    accessibilityServiceConnection.mInvocationHandler.obtainMessage(1, accessibilityGestureEvent.copyForAsync()).sendToTarget();
                } else {
                    accessibilityServiceConnection.mInvocationHandler.obtainMessage(1, accessibilityGestureEvent).sendToTarget();
                }
                return true;
            }
        }
        return false;
    }

    public boolean notifyKeyEvent(KeyEvent keyEvent, int i) {
        synchronized (this.mLock) {
            try {
                ArrayList arrayList = getUserStateLocked(this.mCurrentUserId).mBoundServices;
                if (arrayList.isEmpty()) {
                    return false;
                }
                return getKeyEventDispatcher().notifyKeyEventLocked(keyEvent, i, arrayList);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void notifyMagnificationChanged(int i, Region region, MagnificationConfig magnificationConfig) {
        synchronized (this.mLock) {
            notifyClearAccessibilityCacheLocked();
            notifyMagnificationChangedLocked(i, region, magnificationConfig);
        }
    }

    public final void notifyMagnificationChangedLocked(int i, Region region, MagnificationConfig magnificationConfig) {
        AccessibilityUserState userStateLocked = getUserStateLocked(this.mCurrentUserId);
        for (int size = userStateLocked.mBoundServices.size() - 1; size >= 0; size--) {
            AbstractAccessibilityServiceConnection.InvocationHandler invocationHandler = ((AccessibilityServiceConnection) userStateLocked.mBoundServices.get(size)).mInvocationHandler;
            synchronized (AbstractAccessibilityServiceConnection.this.mLock) {
                try {
                    if (invocationHandler.mMagnificationCallbackState.get(i) != null) {
                        SomeArgs obtain = SomeArgs.obtain();
                        obtain.arg1 = region;
                        obtain.arg2 = magnificationConfig;
                        obtain.argi1 = i;
                        invocationHandler.obtainMessage(5, obtain).sendToTarget();
                    }
                } finally {
                }
            }
        }
    }

    public final void notifyQuickSettingsTilesChanged(int i, List list) {
        notifyQuickSettingsTilesChanged_enforcePermission();
        if (android.view.accessibility.Flags.a11yQsShortcut()) {
            ArraySet arraySet = new ArraySet(list);
            synchronized (this.mLock) {
                try {
                    AccessibilityUserState userStateLocked = getUserStateLocked(i);
                    Map tileServiceToA11yServiceInfoMapLocked = userStateLocked.getTileServiceToA11yServiceInfoMapLocked();
                    Map a11yFeatureToTileService = userStateLocked.getA11yFeatureToTileService();
                    ArraySet arraySet2 = new ArraySet(userStateLocked.mA11yTilesInQsPanel);
                    Set set = (Set) arraySet.stream().filter(new AccessibilityManagerService$$ExternalSyntheticLambda17(2, arraySet2)).collect(Collectors.toSet());
                    Set set2 = (Set) arraySet2.stream().filter(new AccessibilityManagerService$$ExternalSyntheticLambda50(0, arraySet)).collect(Collectors.toSet());
                    if (set.isEmpty() && set2.isEmpty()) {
                        return;
                    }
                    userStateLocked.mA11yTilesInQsPanel.clear();
                    userStateLocked.mA11yTilesInQsPanel.addAll((Collection) arraySet);
                    ArrayList arrayList = new ArrayList();
                    ArrayList arrayList2 = new ArrayList();
                    for (Map.Entry entry : ShortcutConstants.A11Y_FEATURE_TO_FRAMEWORK_TILE.entrySet()) {
                        String flattenToString = ((ComponentName) entry.getKey()).flattenToString();
                        ComponentName componentName = (ComponentName) entry.getValue();
                        if (set.contains(componentName)) {
                            arrayList.add(flattenToString);
                        } else if (set2.contains(componentName)) {
                            arrayList2.add(flattenToString);
                        }
                    }
                    for (Map.Entry entry2 : ((ArrayMap) a11yFeatureToTileService).entrySet()) {
                        String flattenToString2 = ((ComponentName) entry2.getKey()).flattenToString();
                        Object obj = (ComponentName) entry2.getValue();
                        if (set.contains(obj)) {
                            AccessibilityServiceInfo accessibilityServiceInfo = (AccessibilityServiceInfo) tileServiceToA11yServiceInfoMapLocked.getOrDefault(obj, null);
                            if (accessibilityServiceInfo == null || !isAccessibilityServiceWarningRequired(accessibilityServiceInfo)) {
                                arrayList.add(flattenToString2);
                            } else {
                                Counter.logIncrementWithUid("accessibility.value_qs_shortcut_add", Binder.getCallingUid(), 1);
                            }
                        } else if (set2.contains(obj)) {
                            arrayList2.add(flattenToString2);
                        }
                    }
                    if (!arrayList.isEmpty()) {
                        enableShortcutForTargets(arrayList, 16, true, i);
                    }
                    if (arrayList2.isEmpty()) {
                        return;
                    }
                    enableShortcutForTargets(arrayList2, 16, false, i);
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public void notifySystemActionsChangedLocked(AccessibilityUserState accessibilityUserState) {
        for (int size = accessibilityUserState.mBoundServices.size() - 1; size >= 0; size--) {
            ((AccessibilityServiceConnection) accessibilityUserState.mBoundServices.get(size)).mInvocationHandler.sendEmptyMessage(9);
        }
    }

    public final void onClientChangeLocked(boolean z, boolean z2) {
        AccessibilityUserState userStateLocked = getUserStateLocked(this.mCurrentUserId);
        onUserStateChangedLocked(userStateLocked, z2);
        if (z) {
            scheduleNotifyClientsOfServicesStateChangeLocked(userStateLocked);
        }
    }

    public boolean onPackagesForceStoppedLocked(String[] strArr, AccessibilityUserState accessibilityUserState) {
        List list = accessibilityUserState.mInstalledServices.stream().filter(new AccessibilityManagerService$$ExternalSyntheticLambda30()).map(new AccessibilityManagerService$$ExternalSyntheticLambda5(2)).toList();
        Iterator it = ((HashSet) accessibilityUserState.mEnabledServices).iterator();
        boolean z = false;
        while (it.hasNext()) {
            ComponentName componentName = (ComponentName) it.next();
            String packageName = componentName.getPackageName();
            int length = strArr.length;
            int i = 0;
            while (true) {
                if (i >= length) {
                    break;
                }
                if (packageName.equals(strArr[i])) {
                    it.remove();
                    ((HashSet) accessibilityUserState.mBindingServices).remove(componentName);
                    ((HashSet) accessibilityUserState.mCrashedServices).remove(componentName);
                    z = true;
                    break;
                }
                i++;
            }
        }
        if (z) {
            persistComponentNamesToSettingLocked(accessibilityUserState.mUserId, "enabled_accessibility_services", accessibilityUserState.mEnabledServices);
        }
        boolean removeIf = accessibilityUserState.mAccessibilityButtonTargets.removeIf(new AccessibilityManagerService$$ExternalSyntheticLambda17(1, list));
        if (removeIf) {
            persistColonDelimitedSetToSettingLocked("accessibility_button_targets", accessibilityUserState.mUserId, accessibilityUserState.mAccessibilityButtonTargets, new AccessibilityManagerService$$ExternalSyntheticLambda5(0), null);
        }
        return z || removeIf;
    }

    public final void onServiceInfoChangedLocked(AccessibilityUserState accessibilityUserState) {
        PolicyWarningUIController policyWarningUIController;
        AccessibilitySecurityPolicy accessibilitySecurityPolicy = this.mSecurityPolicy;
        int i = accessibilityUserState.mUserId;
        ArrayList arrayList = accessibilityUserState.mBoundServices;
        if (((AccessibilityManagerService) accessibilitySecurityPolicy.mAccessibilityUserManager).mCurrentUserId == i) {
            ArraySet arraySet = new ArraySet();
            int i2 = 0;
            while (true) {
                int size = arrayList.size();
                policyWarningUIController = accessibilitySecurityPolicy.mPolicyWarningUIController;
                if (i2 >= size) {
                    break;
                }
                AccessibilityServiceInfo accessibilityServiceInfo = ((AccessibilityServiceConnection) arrayList.get(i2)).mAccessibilityServiceInfo;
                ComponentName clone = accessibilityServiceInfo.getComponentName().clone();
                if (!accessibilityServiceInfo.isAccessibilityTool()) {
                    arraySet.add(clone);
                    if (accessibilitySecurityPolicy.mNonA11yCategoryServices.contains(clone)) {
                        accessibilitySecurityPolicy.mNonA11yCategoryServices.remove(clone);
                    } else if (accessibilitySecurityPolicy.mSendNonA11yToolNotificationEnabled) {
                        policyWarningUIController.getClass();
                        policyWarningUIController.mMainHandler.sendMessage(PooledLambda.obtainMessage(new PolicyWarningUIController$$ExternalSyntheticLambda2(0, policyWarningUIController), Integer.valueOf(i), clone));
                    }
                }
                i2++;
            }
            for (int i3 = 0; i3 < accessibilitySecurityPolicy.mNonA11yCategoryServices.size(); i3++) {
                ComponentName componentName = (ComponentName) accessibilitySecurityPolicy.mNonA11yCategoryServices.valueAt(i3);
                policyWarningUIController.getClass();
                policyWarningUIController.mMainHandler.sendMessage(PooledLambda.obtainMessage(new PolicyWarningUIController$$ExternalSyntheticLambda2(1, policyWarningUIController), Integer.valueOf(i), componentName));
            }
            accessibilitySecurityPolicy.mNonA11yCategoryServices.clear();
            accessibilitySecurityPolicy.mNonA11yCategoryServices.addAll(arraySet);
        }
        scheduleNotifyClientsOfServicesStateChangeLocked(accessibilityUserState);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final void onShellCommand(FileDescriptor fileDescriptor, FileDescriptor fileDescriptor2, FileDescriptor fileDescriptor3, String[] strArr, ShellCallback shellCallback, ResultReceiver resultReceiver) {
        new AccessibilityShellCommand(this.mContext, this, this.mSystemActionPerformer).exec(this, fileDescriptor, fileDescriptor2, fileDescriptor3, strArr, shellCallback, resultReceiver);
    }

    public final void onTouchInteractionEnd() {
        AccessibilityWindowManager accessibilityWindowManager = this.mA11yWindowManager;
        synchronized (accessibilityWindowManager.mLock) {
            try {
                accessibilityWindowManager.mTouchInteractionInProgress = false;
                int i = accessibilityWindowManager.mActiveWindowId;
                accessibilityWindowManager.setActiveWindowLocked(accessibilityWindowManager.mTopFocusedWindowId);
                if (i != accessibilityWindowManager.mActiveWindowId && accessibilityWindowManager.mAccessibilityFocusedWindowId == i) {
                    if (!(accessibilityWindowManager.mDisplayWindowsObservers.size() > 0)) {
                        accessibilityWindowManager.clearAccessibilityFocusLocked(i);
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:125:0x0324, code lost:
    
        if (r11.mTouchExplorationGrantedServices.removeIf(new com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda25(r3, r0)) != false) goto L108;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0160, code lost:
    
        if (r0.mBoundServices.contains(r1) != false) goto L41;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v2 */
    /* JADX WARN: Type inference failed for: r10v3, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r10v6 */
    /* JADX WARN: Type inference failed for: r14v11 */
    /* JADX WARN: Type inference failed for: r14v16 */
    /* JADX WARN: Type inference failed for: r14v6, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r9v10 */
    /* JADX WARN: Type inference failed for: r9v2 */
    /* JADX WARN: Type inference failed for: r9v3, types: [boolean, int] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void onUserStateChangedLocked(com.android.server.accessibility.AccessibilityUserState r26, boolean r27) {
        /*
            Method dump skipped, instructions count: 1620
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.accessibility.AccessibilityManagerService.onUserStateChangedLocked(com.android.server.accessibility.AccessibilityUserState, boolean):void");
    }

    public final List parseAccessibilityServiceInfos(int i) {
        int i2;
        ArrayList arrayList = new ArrayList();
        synchronized (this.mLock) {
            i2 = getUserStateLocked(i).mBindInstantServiceAllowed ? 9207940 : 819332;
        }
        List queryIntentServicesAsUser = this.mPackageManager.queryIntentServicesAsUser(new Intent("android.accessibilityservice.AccessibilityService"), i2, i);
        int size = queryIntentServicesAsUser.size();
        for (int i3 = 0; i3 < size; i3++) {
            ResolveInfo resolveInfo = (ResolveInfo) queryIntentServicesAsUser.get(i3);
            ServiceInfo serviceInfo = resolveInfo.serviceInfo;
            AccessibilitySecurityPolicy accessibilitySecurityPolicy = this.mSecurityPolicy;
            accessibilitySecurityPolicy.getClass();
            if (!"android.permission.BIND_ACCESSIBILITY_SERVICE".equals(serviceInfo.permission)) {
                Slog.w("AccessibilitySecurityPolicy", "Skipping accessibility service " + new ComponentName(serviceInfo.packageName, serviceInfo.name).flattenToShortString() + ": it does not require the permission android.permission.BIND_ACCESSIBILITY_SERVICE");
            } else if ((serviceInfo.flags & 4) != 0) {
                Slog.w("AccessibilitySecurityPolicy", "Skipping accessibility service " + new ComponentName(serviceInfo.packageName, serviceInfo.name).flattenToShortString() + ": the service is the external one and doesn't allow to register as an accessibility service ");
            } else if (accessibilitySecurityPolicy.mAppOpsManager.noteOpNoThrow("android:bind_accessibility_service", serviceInfo.applicationInfo.uid, serviceInfo.packageName, null, null) != 0) {
                Slog.w("AccessibilitySecurityPolicy", "Skipping accessibility service " + new ComponentName(serviceInfo.packageName, serviceInfo.name).flattenToShortString() + ": disallowed by AppOps");
            } else {
                try {
                    AccessibilityServiceInfo accessibilityServiceInfo = new AccessibilityServiceInfo(resolveInfo, this.mContext);
                    if (accessibilityServiceInfo.isWithinParcelableSize()) {
                        arrayList.add(accessibilityServiceInfo);
                    } else {
                        Slog.e("AccessibilityManagerService", "Skipping service " + accessibilityServiceInfo.getResolveInfo().getComponentInfo() + " because service info size is larger than safe parcelable limits.");
                    }
                } catch (IOException | XmlPullParserException e) {
                    Slog.e("AccessibilityManagerService", "Error while initializing AccessibilityServiceInfo", e);
                }
            }
        }
        return arrayList;
    }

    public final void performAccessibilityDirectAccess(String str) {
        performAccessibilityDirectAccess_enforcePermission();
        this.mMainHandler.sendMessage(PooledLambda.obtainMessage(new AccessibilityManagerService$$ExternalSyntheticLambda14(0), this, Integer.valueOf(getDisplayId()), 512, str));
    }

    public final boolean performAccessibilityFrameworkFeature(int i, ComponentName componentName) {
        Map frameworkShortcutFeaturesMap = AccessibilityShortcutController.getFrameworkShortcutFeaturesMap();
        if (!frameworkShortcutFeaturesMap.containsKey(componentName)) {
            return false;
        }
        AccessibilityShortcutController.FrameworkFeatureInfo frameworkFeatureInfo = (AccessibilityShortcutController.FrameworkFeatureInfo) frameworkShortcutFeaturesMap.get(componentName);
        SettingsStringUtil.SettingStringHelper settingStringHelper = new SettingsStringUtil.SettingStringHelper(this.mContext.getContentResolver(), frameworkFeatureInfo.getSettingKey(), this.mCurrentUserId);
        if (!(frameworkFeatureInfo instanceof AccessibilityShortcutController.LaunchableFrameworkFeatureInfo)) {
            if (TextUtils.equals(frameworkFeatureInfo.getSettingOnValue(), settingStringHelper.read())) {
                AccessibilityStatsLogUtils.logAccessibilityShortcutActivated(this.mContext, componentName, i, false);
                settingStringHelper.write(frameworkFeatureInfo.getSettingOffValue());
            } else {
                AccessibilityStatsLogUtils.logAccessibilityShortcutActivated(this.mContext, componentName, i, true);
                settingStringHelper.write(frameworkFeatureInfo.getSettingOnValue());
            }
            return true;
        }
        AccessibilityStatsLogUtils.logAccessibilityShortcutActivated(this.mContext, componentName, i, true);
        if (componentName.equals(AccessibilityShortcutController.ACCESSIBILITY_HEARING_AIDS_COMPONENT_NAME)) {
            com.android.systemui.Flags.hearingAidsQsTileDialog();
            Intent intent = new Intent(ACTION_LAUNCH_HEARING_DEVICES_DIALOG);
            intent.setFlags(268435456);
            intent.setPackage(this.mContext.getString(R.string.config_systemUi));
            this.mContext.sendBroadcastAsUser(intent, UserHandle.SYSTEM);
        }
        return true;
    }

    public final void performAccessibilityShortcut(String str) {
        performAccessibilityShortcut_enforcePermission();
        if (this.mTraceManager.isA11yTracingEnabledForTypes(4L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.performAccessibilityShortcut", 4L, ConnectivityModuleConnector$$ExternalSyntheticOutline0.m("targetName=", str));
        }
        this.mMainHandler.sendMessage(PooledLambda.obtainMessage(new AccessibilityManagerService$$ExternalSyntheticLambda14(0), this, Integer.valueOf(getDisplayId()), 2, str));
    }

    public final boolean performAccessibilityShortcutTargetActivity(int i, ComponentName componentName) {
        synchronized (this.mLock) {
            try {
                AccessibilityUserState userStateLocked = getUserStateLocked(this.mCurrentUserId);
                for (int i2 = 0; i2 < ((ArrayList) userStateLocked.mInstalledShortcuts).size(); i2++) {
                    if (((AccessibilityShortcutInfo) ((ArrayList) userStateLocked.mInstalledShortcuts).get(i2)).getComponentName().equals(componentName)) {
                        Intent intent = new Intent();
                        Bundle bundle = ActivityOptions.makeBasic().setLaunchDisplayId(i).toBundle();
                        intent.setComponent(componentName);
                        intent.addFlags(268435456);
                        try {
                            this.mContext.startActivityAsUser(intent, bundle, UserHandle.of(this.mCurrentUserId));
                        } catch (ActivityNotFoundException unused) {
                        }
                        return true;
                    }
                }
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean performAccessibilityShortcutTargetService(int i, int i2, ComponentName componentName) {
        synchronized (this.mLock) {
            try {
                AccessibilityUserState userStateLocked = getUserStateLocked(this.mCurrentUserId);
                AccessibilityServiceInfo installedServiceInfoLocked = userStateLocked.getInstalledServiceInfoLocked(componentName);
                if (installedServiceInfoLocked == null) {
                    Slog.d("AccessibilityManagerService", "Perform shortcut failed, invalid component name:" + componentName);
                    return false;
                }
                if (ComponentName.unflattenFromString("com.samsung.accessibility/com.samsung.accessibility.universalswitch.UniversalSwitchService").equals(componentName) && new SemLockPatternUtils(this.mContext).getKeyguardStoredPasswordQuality(UserHandle.semGetMyUserId()) == 65536) {
                    new Handler(Looper.getMainLooper()).postDelayed(new AccessibilityManagerService$$ExternalSyntheticLambda8(this, this.mContext.getString(17043247), 2), 0L);
                    return false;
                }
                AccessibilityServiceConnection accessibilityServiceConnection = (AccessibilityServiceConnection) ((HashMap) userStateLocked.mComponentNameToServiceMap).get(componentName);
                int i3 = installedServiceInfoLocked.getResolveInfo().serviceInfo.applicationInfo.targetSdkVersion;
                boolean z = (installedServiceInfoLocked.flags & 256) != 0;
                Slog.d("AccessibilityManagerService", "performAccessibilityShortcutTargetService assignedTarget : " + componentName + ", targetSdk : " + i3 + ", requestA11yButton : " + z);
                if ((i3 <= 29 && (i2 == 2 || i2 == 512)) || (i3 > 29 && !z)) {
                    if (accessibilityServiceConnection != null) {
                        AccessibilityStatsLogUtils.logAccessibilityShortcutActivated(this.mContext, componentName, i2, false);
                        disableAccessibilityServiceLocked(this.mCurrentUserId, componentName);
                    } else if (((HashSet) userStateLocked.mEnabledServices).contains(componentName)) {
                        Slog.d("AccessibilityManagerService", "serviceConnection is null, but included in mEnabledServices : " + componentName);
                        AccessibilityStatsLogUtils.logAccessibilityShortcutActivated(this.mContext, componentName, i2, false);
                        disableAccessibilityServiceLocked(this.mCurrentUserId, componentName);
                    } else {
                        AccessibilityStatsLogUtils.logAccessibilityShortcutActivated(this.mContext, componentName, i2, true);
                        enableAccessibilityServiceLocked(this.mCurrentUserId, componentName);
                    }
                    return true;
                }
                if ((i2 == 2 || i2 == 512) && i3 > 29 && z && !((HashSet) userStateLocked.mEnabledServices).contains(componentName)) {
                    enableAccessibilityServiceLocked(this.mCurrentUserId, componentName);
                    return true;
                }
                if (accessibilityServiceConnection != null && userStateLocked.mBoundServices.contains(accessibilityServiceConnection) && accessibilityServiceConnection.mRequestAccessibilityButton) {
                    AccessibilityStatsLogUtils.logAccessibilityShortcutActivated(this.mContext, componentName, i2, true);
                    accessibilityServiceConnection.mInvocationHandler.obtainMessage(7, i, 0).sendToTarget();
                    return true;
                }
                Slog.d("AccessibilityManagerService", "Perform shortcut failed, service is not ready:" + componentName);
                return false;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void persistColonDelimitedSetToSettingLocked(String str, int i, Set set, Function function, String str2) {
        StringBuilder sb = new StringBuilder();
        Iterator it = set.iterator();
        while (it.hasNext()) {
            Object next = it.next();
            String str3 = next != null ? (String) function.apply(next) : null;
            if (!TextUtils.isEmpty(str3)) {
                if (sb.length() > 0) {
                    sb.append(':');
                }
                sb.append(str3);
            }
        }
        String sb2 = sb.toString();
        if (!TextUtils.isEmpty(sb2)) {
            str2 = sb2;
        }
        if (android.view.accessibility.Flags.restoreA11yShortcutTargetService() && Objects.equals(str2, Settings.Secure.getStringForUser(this.mContext.getContentResolver(), str, i))) {
            return;
        }
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Settings.Secure.putStringForUser(this.mContext.getContentResolver(), str, str2, i);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void persistComponentNamesToSettingLocked(int i, String str, Set set) {
        persistColonDelimitedSetToSettingLocked(str, i, set, new AccessibilityManagerService$$ExternalSyntheticLambda5(4), null);
    }

    public final void persistIntToSetting(int i, int i2, String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            Settings.Secure.putIntForUser(this.mContext.getContentResolver(), str, i2, i);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean readAMEnabledSettingLocked(AccessibilityUserState accessibilityUserState) {
        boolean z = Settings.System.getIntForUser(this.mContext.getContentResolver(), "assistant_menu", 0, accessibilityUserState.mUserId) != 0;
        if (z) {
            try {
                String readDataFromAccessibilityProvider = A11yRune.readDataFromAccessibilityProvider(this.mContext, "assistant_menu_option_upperapps");
                boolean z2 = !TextUtils.isEmpty(readDataFromAccessibilityProvider) && (readDataFromAccessibilityProvider.contains("FingerMouse") || readDataFromAccessibilityProvider.contains("MagnifierWindow"));
                if ((z && z2) != accessibilityUserState.mIsAMEnabled) {
                    accessibilityUserState.mIsAMEnabled = z && z2;
                    return true;
                }
            } catch (IllegalArgumentException unused) {
            }
        }
        return false;
    }

    public final boolean readAccessibilityButtonTargetComponentLocked(AccessibilityUserState accessibilityUserState) {
        String stringForUser = Settings.Secure.getStringForUser(this.mContext.getContentResolver(), "accessibility_button_target_component", accessibilityUserState.mUserId);
        StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("componentId : ", stringForUser, " userState.getTargetAssignedToAccessibilityButton() : ");
        m.append(accessibilityUserState.mTargetAssignedToAccessibilityButton);
        Slog.d("AccessibilityManagerService", m.toString());
        if (TextUtils.isEmpty(stringForUser)) {
            if (accessibilityUserState.mTargetAssignedToAccessibilityButton == null) {
                return false;
            }
            accessibilityUserState.mTargetAssignedToAccessibilityButton = null;
            return true;
        }
        if (stringForUser.equals(accessibilityUserState.mTargetAssignedToAccessibilityButton)) {
            return false;
        }
        accessibilityUserState.mTargetAssignedToAccessibilityButton = stringForUser;
        return true;
    }

    public final boolean readAccessibilityButtonTargetsLocked(AccessibilityUserState accessibilityUserState) {
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        readColonDelimitedSettingToSet("accessibility_button_targets", accessibilityUserState.mUserId, new AccessibilityManagerService$$ExternalSyntheticLambda5(0), linkedHashSet);
        LinkedHashSet linkedHashSet2 = accessibilityUserState.mAccessibilityButtonTargets;
        Slog.d("AccessibilityManagerService", "currentTargets : " + linkedHashSet2 + " targetsFromSetting : " + linkedHashSet);
        if (linkedHashSet.equals(linkedHashSet2)) {
            return false;
        }
        linkedHashSet2.clear();
        linkedHashSet2.addAll(linkedHashSet);
        scheduleNotifyClientsOfServicesStateChangeLocked(accessibilityUserState);
        return true;
    }

    public final boolean readAccessibilityDirectAccessSettingLocked(AccessibilityUserState accessibilityUserState) {
        String stringForUser = Settings.Secure.getStringForUser(this.mContext.getContentResolver(), "accessibility_direct_access_target_service", accessibilityUserState.mUserId);
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        readColonDelimitedStringToSet(stringForUser, new AccessibilityManagerService$$ExternalSyntheticLambda5(0), linkedHashSet, false);
        LinkedHashSet linkedHashSet2 = accessibilityUserState.mAccessibilityDirectAccessTargets;
        Slog.d("AccessibilityManagerService", "readAccessibilityDirectAccessSettingLocked currentTargets : " + linkedHashSet2 + " targetsFromSetting : " + linkedHashSet);
        if (linkedHashSet.equals(linkedHashSet2)) {
            return false;
        }
        linkedHashSet2.clear();
        linkedHashSet2.addAll(linkedHashSet);
        scheduleNotifyClientsOfServicesStateChangeLocked(accessibilityUserState);
        return true;
    }

    public final boolean readAccessibilityShortcutKeySettingLocked(AccessibilityUserState accessibilityUserState) {
        String stringForUser = Settings.Secure.getStringForUser(this.mContext.getContentResolver(), "accessibility_shortcut_target_service", accessibilityUserState.mUserId);
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        readColonDelimitedStringToSet(stringForUser, new AccessibilityManagerService$$ExternalSyntheticLambda5(0), linkedHashSet, false);
        LinkedHashSet linkedHashSet2 = accessibilityUserState.mAccessibilityShortcutKeyTargets;
        if (linkedHashSet.equals(linkedHashSet2)) {
            return false;
        }
        linkedHashSet2.clear();
        linkedHashSet2.addAll(linkedHashSet);
        scheduleNotifyClientsOfServicesStateChangeLocked(accessibilityUserState);
        return true;
    }

    public final boolean readAlwaysOnMagnificationLocked(AccessibilityUserState accessibilityUserState) {
        boolean z = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "accessibility_magnification_always_on_enabled", 1, accessibilityUserState.mUserId) == 1;
        this.mMagnificationController.mAlwaysOnMagnificationFeatureFlag.getClass();
        if (z == accessibilityUserState.mAlwaysOnMagnificationEnabled) {
            return false;
        }
        accessibilityUserState.mAlwaysOnMagnificationEnabled = z;
        this.mMagnificationController.getFullScreenMagnificationController().mAlwaysOnMagnificationEnabled = z;
        return true;
    }

    public void readColonDelimitedSettingToSet(String str, int i, Function function, Set set) {
        readColonDelimitedStringToSet(Settings.Secure.getStringForUser(this.mContext.getContentResolver(), str, i), function, set, false);
    }

    public final void readColonDelimitedStringToSet(String str, Function function, Set set, boolean z) {
        Object apply;
        if (!z) {
            set.clear();
        }
        if (TextUtils.isEmpty(str)) {
            return;
        }
        TextUtils.SimpleStringSplitter simpleStringSplitter = this.mStringColonSplitter;
        simpleStringSplitter.setString(str);
        while (simpleStringSplitter.hasNext()) {
            String next = simpleStringSplitter.next();
            if (!TextUtils.isEmpty(next) && (apply = function.apply(next)) != null) {
                set.add(apply);
            }
        }
    }

    public void readComponentNamesFromSettingLocked(String str, int i, Set set) {
        readColonDelimitedSettingToSet(str, i, new AccessibilityManagerService$$ExternalSyntheticLambda5(1), set);
    }

    public final boolean readConfigurationForUserStateLocked(final AccessibilityUserState accessibilityUserState, List list, List list2) {
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
        boolean z11;
        boolean z12;
        boolean z13;
        boolean z14;
        boolean z15;
        ArrayList arrayList = (ArrayList) list;
        int size = arrayList.size();
        boolean z16 = false;
        for (int i = 0; i < size; i++) {
            AccessibilityServiceInfo accessibilityServiceInfo = (AccessibilityServiceInfo) arrayList.get(i);
            if (((HashSet) accessibilityUserState.mCrashedServices).contains(accessibilityServiceInfo.getComponentName())) {
                accessibilityServiceInfo.crashed = true;
            }
        }
        if (arrayList.equals(accessibilityUserState.mInstalledServices)) {
            z = false;
        } else {
            ((ArrayList) accessibilityUserState.mInstalledServices).clear();
            ((ArrayList) accessibilityUserState.mInstalledServices).addAll(list);
            ((ArrayMap) accessibilityUserState.mA11yServiceToTileService).clear();
            final int i2 = 0;
            ((ArrayList) accessibilityUserState.mInstalledServices).forEach(new Consumer() { // from class: com.android.server.accessibility.AccessibilityUserState$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    int i3 = i2;
                    AccessibilityUserState accessibilityUserState2 = accessibilityUserState;
                    switch (i3) {
                        case 0:
                            AccessibilityServiceInfo accessibilityServiceInfo2 = (AccessibilityServiceInfo) obj;
                            accessibilityUserState2.getClass();
                            String tileServiceName = accessibilityServiceInfo2.getTileServiceName();
                            if (!TextUtils.isEmpty(tileServiceName)) {
                                ServiceInfo serviceInfo = accessibilityServiceInfo2.getResolveInfo().serviceInfo;
                                ComponentName componentName = new ComponentName(serviceInfo.packageName, serviceInfo.name);
                                ((ArrayMap) accessibilityUserState2.mA11yServiceToTileService).put(componentName, new ComponentName(componentName.getPackageName(), tileServiceName));
                                break;
                            }
                            break;
                        default:
                            AccessibilityShortcutInfo accessibilityShortcutInfo = (AccessibilityShortcutInfo) obj;
                            accessibilityUserState2.getClass();
                            String tileServiceName2 = accessibilityShortcutInfo.getTileServiceName();
                            if (!TextUtils.isEmpty(tileServiceName2)) {
                                ComponentName componentName2 = accessibilityShortcutInfo.getComponentName();
                                ((ArrayMap) accessibilityUserState2.mA11yActivityToTileService).put(componentName2, new ComponentName(componentName2.getPackageName(), tileServiceName2));
                                break;
                            }
                            break;
                    }
                }
            });
            z = true;
        }
        if (list2.equals(accessibilityUserState.mInstalledShortcuts)) {
            z2 = false;
        } else {
            ((ArrayList) accessibilityUserState.mInstalledShortcuts).clear();
            ((ArrayList) accessibilityUserState.mInstalledShortcuts).addAll(list2);
            ((ArrayMap) accessibilityUserState.mA11yActivityToTileService).clear();
            final int i3 = 1;
            ((ArrayList) accessibilityUserState.mInstalledShortcuts).forEach(new Consumer() { // from class: com.android.server.accessibility.AccessibilityUserState$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    int i32 = i3;
                    AccessibilityUserState accessibilityUserState2 = accessibilityUserState;
                    switch (i32) {
                        case 0:
                            AccessibilityServiceInfo accessibilityServiceInfo2 = (AccessibilityServiceInfo) obj;
                            accessibilityUserState2.getClass();
                            String tileServiceName = accessibilityServiceInfo2.getTileServiceName();
                            if (!TextUtils.isEmpty(tileServiceName)) {
                                ServiceInfo serviceInfo = accessibilityServiceInfo2.getResolveInfo().serviceInfo;
                                ComponentName componentName = new ComponentName(serviceInfo.packageName, serviceInfo.name);
                                ((ArrayMap) accessibilityUserState2.mA11yServiceToTileService).put(componentName, new ComponentName(componentName.getPackageName(), tileServiceName));
                                break;
                            }
                            break;
                        default:
                            AccessibilityShortcutInfo accessibilityShortcutInfo = (AccessibilityShortcutInfo) obj;
                            accessibilityUserState2.getClass();
                            String tileServiceName2 = accessibilityShortcutInfo.getTileServiceName();
                            if (!TextUtils.isEmpty(tileServiceName2)) {
                                ComponentName componentName2 = accessibilityShortcutInfo.getComponentName();
                                ((ArrayMap) accessibilityUserState2.mA11yActivityToTileService).put(componentName2, new ComponentName(componentName2.getPackageName(), tileServiceName2));
                                break;
                            }
                            break;
                    }
                }
            });
            z2 = true;
        }
        boolean readEnabledAccessibilityServicesLocked = z | z2 | readEnabledAccessibilityServicesLocked(accessibilityUserState) | readTouchExplorationGrantedAccessibilityServicesLocked(accessibilityUserState);
        ContentResolver contentResolver = this.mContext.getContentResolver();
        int i4 = accessibilityUserState.mUserId;
        boolean z17 = Settings.Secure.getIntForUser(contentResolver, "touch_exploration_enabled", 0, i4) == 1;
        if (z17 != accessibilityUserState.mIsTouchExplorationEnabled) {
            accessibilityUserState.mIsTouchExplorationEnabled = z17;
            z3 = true;
        } else {
            z3 = false;
        }
        boolean z18 = readEnabledAccessibilityServicesLocked | z3;
        boolean z19 = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "high_text_contrast_enabled", 0, i4) == 1;
        if (z19 != accessibilityUserState.mIsTextHighContrastEnabled) {
            accessibilityUserState.mIsTextHighContrastEnabled = z19;
            z4 = true;
        } else {
            z4 = false;
        }
        boolean z20 = z18 | z4;
        boolean z21 = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "enabled_accessibility_audio_description_by_default", 0, i4) == 1;
        if (z21 != accessibilityUserState.mIsAudioDescriptionByDefaultRequested) {
            accessibilityUserState.mIsAudioDescriptionByDefaultRequested = z21;
            z5 = true;
        } else {
            z5 = false;
        }
        boolean z22 = z20 | z5;
        boolean z23 = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "accessibility_display_magnification_enabled", 0, i4) == 1;
        if (z23 != accessibilityUserState.mIsMagnificationSingleFingerTripleTapEnabled) {
            accessibilityUserState.mIsMagnificationSingleFingerTripleTapEnabled = z23;
            z6 = true;
        } else {
            z6 = false;
        }
        boolean z24 = z22 | z6;
        boolean z25 = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "accessibility_autoclick_enabled", 0, i4) == 1;
        if (z25 != accessibilityUserState.mIsAutoclickEnabled) {
            accessibilityUserState.mIsAutoclickEnabled = z25;
            z7 = true;
        } else {
            z7 = false;
        }
        boolean readAccessibilityShortcutKeySettingLocked = z24 | z7 | readAccessibilityShortcutKeySettingLocked(accessibilityUserState);
        ArraySet arraySet = new ArraySet();
        readColonDelimitedSettingToSet("accessibility_qs_targets", i4, new AccessibilityManagerService$$ExternalSyntheticLambda5(0), arraySet);
        if (arraySet.equals(new LinkedHashSet(accessibilityUserState.mAccessibilityQsTargets))) {
            z8 = false;
        } else {
            accessibilityUserState.updateA11yQsTargetLocked(arraySet);
            scheduleNotifyClientsOfServicesStateChangeLocked(accessibilityUserState);
            z8 = true;
        }
        boolean readAccessibilityButtonTargetsLocked = readAccessibilityShortcutKeySettingLocked | z8 | readAccessibilityButtonTargetsLocked(accessibilityUserState) | readAccessibilityButtonTargetComponentLocked(accessibilityUserState) | readUserRecommendedUiTimeoutSettingsLocked(accessibilityUserState) | readMagnificationModeForDefaultDisplayLocked(accessibilityUserState);
        int intForUser = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "accessibility_magnification_capability", 1, i4);
        if (intForUser != accessibilityUserState.mMagnificationCapabilities) {
            accessibilityUserState.mMagnificationCapabilities = intForUser;
            this.mMagnificationController.mMagnificationCapabilities = intForUser;
            z9 = true;
        } else {
            z9 = false;
        }
        boolean readMagnificationFollowTypingLocked = readAccessibilityButtonTargetsLocked | z9 | readMagnificationFollowTypingLocked(accessibilityUserState) | readAlwaysOnMagnificationLocked(accessibilityUserState);
        boolean z26 = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "accessibility_auto_action_type", 0, i4) != 0;
        if (z26 != accessibilityUserState.mIsAutoActionEnabled) {
            accessibilityUserState.mIsAutoActionEnabled = z26;
            z10 = true;
        } else {
            z10 = false;
        }
        boolean z27 = readMagnificationFollowTypingLocked | z10;
        boolean z28 = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "accessibility_corner_action_enabled", 0, i4) != 0;
        if (z28 != accessibilityUserState.mIsCornerActionEnabled) {
            accessibilityUserState.mIsCornerActionEnabled = z28;
            z11 = true;
        } else {
            z11 = false;
        }
        boolean z29 = z27 | z11;
        boolean z30 = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "tap_duration_enabled", 0, i4) == 1;
        if (z30 != accessibilityUserState.mIsTapDurationEnabled) {
            accessibilityUserState.mIsTapDurationEnabled = z30;
            z12 = true;
        } else {
            z12 = false;
        }
        boolean z31 = z29 | z12;
        boolean z32 = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "touch_blocking_enabled", 0, i4) == 1;
        if (z32 != accessibilityUserState.mIsTouchBlockingEnabled) {
            accessibilityUserState.mIsTouchBlockingEnabled = z32;
            z13 = true;
        } else {
            z13 = false;
        }
        boolean z33 = z31 | z13;
        boolean z34 = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "accessibility_sticky_keys", 0, i4) == 1;
        if (z34 != accessibilityUserState.mIsStickyKeysEnabled) {
            accessibilityUserState.mIsStickyKeysEnabled = z34;
            z14 = true;
        } else {
            z14 = false;
        }
        boolean z35 = z33 | z14;
        boolean z36 = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "accessibility_bounce_keys", 0, i4) != 0;
        if (z36 != accessibilityUserState.mIsBounceKeysEnabled) {
            accessibilityUserState.mIsBounceKeysEnabled = z36;
            z15 = true;
        } else {
            z15 = false;
        }
        boolean z37 = z35 | z15;
        boolean z38 = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "accessibility_slow_keys", 0, i4) != 0;
        if (z38 != accessibilityUserState.mIsSlowKeysEnabled) {
            accessibilityUserState.mIsSlowKeysEnabled = z38;
            z16 = true;
        }
        return readAMEnabledSettingLocked(accessibilityUserState) | z37 | z16 | readGestureNaviBarModeSettingsLocked(accessibilityUserState) | readAccessibilityDirectAccessSettingLocked(accessibilityUserState);
    }

    public final boolean readEnabledAccessibilityServicesLocked(AccessibilityUserState accessibilityUserState) {
        ((HashSet) this.mTempComponentNameSet).clear();
        readComponentNamesFromSettingLocked("enabled_accessibility_services", accessibilityUserState.mUserId, this.mTempComponentNameSet);
        if (this.mTempComponentNameSet.equals(accessibilityUserState.mEnabledServices)) {
            ((HashSet) this.mTempComponentNameSet).clear();
            return false;
        }
        ((HashSet) accessibilityUserState.mEnabledServices).clear();
        accessibilityUserState.mEnabledServices.addAll(this.mTempComponentNameSet);
        ((HashSet) this.mTempComponentNameSet).clear();
        return true;
    }

    public final boolean readGestureNaviBarModeSettingsLocked(AccessibilityUserState accessibilityUserState) {
        int intForUser = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "navigation_mode", 0, accessibilityUserState.mUserId);
        boolean z = intForUser == 2 || intForUser == 3;
        if (z == accessibilityUserState.mIsGestureNaviBar) {
            return false;
        }
        accessibilityUserState.mIsGestureNaviBar = z;
        return true;
    }

    public final boolean readMagnificationFollowTypingLocked(AccessibilityUserState accessibilityUserState) {
        boolean z = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "accessibility_magnification_follow_typing_enabled", 1, accessibilityUserState.mUserId) == 1;
        if (z == accessibilityUserState.mMagnificationFollowTypingEnabled) {
            return false;
        }
        accessibilityUserState.mMagnificationFollowTypingEnabled = z;
        MagnificationController magnificationController = this.mMagnificationController;
        magnificationController.getMagnificationConnectionManager().mMagnificationFollowTypingEnabled = z;
        magnificationController.getFullScreenMagnificationController().mMagnificationFollowTypingEnabled = z;
        return true;
    }

    public final boolean readMagnificationModeForDefaultDisplayLocked(AccessibilityUserState accessibilityUserState) {
        int intForUser = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "accessibility_magnification_mode", 1, accessibilityUserState.mUserId);
        if (intForUser == accessibilityUserState.getMagnificationModeLocked(getDisplayId())) {
            return false;
        }
        accessibilityUserState.mMagnificationModes.put(getDisplayId(), intForUser);
        return true;
    }

    public final boolean readTouchExplorationGrantedAccessibilityServicesLocked(AccessibilityUserState accessibilityUserState) {
        ((HashSet) this.mTempComponentNameSet).clear();
        readComponentNamesFromSettingLocked("touch_exploration_granted_accessibility_services", accessibilityUserState.mUserId, this.mTempComponentNameSet);
        if (this.mTempComponentNameSet.equals(accessibilityUserState.mTouchExplorationGrantedServices)) {
            ((HashSet) this.mTempComponentNameSet).clear();
            return false;
        }
        ((HashSet) accessibilityUserState.mTouchExplorationGrantedServices).clear();
        accessibilityUserState.mTouchExplorationGrantedServices.addAll(this.mTempComponentNameSet);
        ((HashSet) this.mTempComponentNameSet).clear();
        return true;
    }

    public final boolean readUserRecommendedUiTimeoutSettingsLocked(AccessibilityUserState accessibilityUserState) {
        boolean z;
        int intForUser = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "accessibility_non_interactive_ui_timeout_ms", 0, accessibilityUserState.mUserId);
        int intForUser2 = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "accessibility_interactive_ui_timeout_ms", 0, accessibilityUserState.mUserId);
        ProxyManager proxyManager = this.mProxyManager;
        synchronized (proxyManager.mLock) {
            int i = 0;
            while (true) {
                try {
                    boolean z2 = true;
                    if (i >= proxyManager.mProxyA11yServiceConnections.size()) {
                        break;
                    }
                    ProxyAccessibilityServiceConnection proxyAccessibilityServiceConnection = (ProxyAccessibilityServiceConnection) proxyManager.mProxyA11yServiceConnections.valueAt(i);
                    if (proxyAccessibilityServiceConnection != null) {
                        int interactiveUiTimeoutMillis = intForUser2 != 0 ? intForUser2 : proxyAccessibilityServiceConnection.mAccessibilityServiceInfo.getInteractiveUiTimeoutMillis();
                        int nonInteractiveUiTimeoutMillis = intForUser != 0 ? intForUser : proxyAccessibilityServiceConnection.mAccessibilityServiceInfo.getNonInteractiveUiTimeoutMillis();
                        if (proxyAccessibilityServiceConnection.mInteractiveTimeout != interactiveUiTimeoutMillis) {
                            proxyAccessibilityServiceConnection.mInteractiveTimeout = interactiveUiTimeoutMillis;
                            z = true;
                        } else {
                            z = false;
                        }
                        if (proxyAccessibilityServiceConnection.mNonInteractiveTimeout != nonInteractiveUiTimeoutMillis) {
                            proxyAccessibilityServiceConnection.mNonInteractiveTimeout = nonInteractiveUiTimeoutMillis;
                        } else {
                            z2 = z;
                        }
                        if (z2) {
                            proxyManager.scheduleNotifyProxyClientsOfServicesStateChangeLocked(proxyAccessibilityServiceConnection.mDeviceId);
                        }
                    }
                    i++;
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        if (intForUser == accessibilityUserState.mUserNonInteractiveUiTimeout && intForUser2 == accessibilityUserState.mUserInteractiveUiTimeout) {
            return false;
        }
        accessibilityUserState.mUserNonInteractiveUiTimeout = intForUser;
        accessibilityUserState.mUserInteractiveUiTimeout = intForUser2;
        scheduleNotifyClientsOfServicesStateChangeLocked(accessibilityUserState);
        return true;
    }

    public final boolean registerProxyForDisplay(IAccessibilityServiceClient iAccessibilityServiceClient, int i) {
        registerProxyForDisplay_enforcePermission();
        this.mSecurityPolicy.checkForAccessibilityPermissionOrRole();
        if (iAccessibilityServiceClient == null) {
            return false;
        }
        if (i < 0) {
            throw new IllegalArgumentException(BinaryTransparencyService$$ExternalSyntheticOutline0.m(i, "The display id ", " is invalid."));
        }
        Iterator it = getValidDisplayList().iterator();
        while (it.hasNext()) {
            if (((Display) it.next()).getDisplayId() == i) {
                if (this.mProxyManager.isProxyedDisplay(i)) {
                    throw new IllegalArgumentException(BinaryTransparencyService$$ExternalSyntheticOutline0.m(i, "The display ", " is already being proxy-ed"));
                }
                ProxyManager proxyManager = this.mProxyManager;
                int callingUid = Binder.getCallingUid();
                VirtualDeviceManager virtualDeviceManager = (VirtualDeviceManager) proxyManager.mContext.getSystemService(VirtualDeviceManager.class);
                VirtualDeviceManagerInternal localVdm = proxyManager.getLocalVdm();
                if (virtualDeviceManager != null && localVdm != null) {
                    for (VirtualDevice virtualDevice : virtualDeviceManager.getVirtualDevices()) {
                        if (localVdm.getDisplayIdsForDevice(virtualDevice.getDeviceId()).contains(Integer.valueOf(i)) && callingUid == localVdm.getDeviceOwnerUid(virtualDevice.getDeviceId())) {
                            long clearCallingIdentity = Binder.clearCallingIdentity();
                            try {
                                ProxyManager proxyManager2 = this.mProxyManager;
                                int i2 = sIdCounter;
                                sIdCounter = i2 + 1;
                                proxyManager2.registerProxy(iAccessibilityServiceClient, i, i2, this.mSecurityPolicy, this, this.mTraceManager, this.mWindowManagerService);
                                synchronized (this.mLock) {
                                    notifyClearAccessibilityCacheLocked();
                                }
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                return true;
                            } catch (Throwable th) {
                                Binder.restoreCallingIdentity(clearCallingIdentity);
                                throw th;
                            }
                        }
                    }
                }
                throw new SecurityException(BinaryTransparencyService$$ExternalSyntheticOutline0.m(i, "The display ", " does not belong to the caller."));
            }
        }
        throw new IllegalArgumentException(BinaryTransparencyService$$ExternalSyntheticOutline0.m(i, "The display ", " does not exist or is not tracked by accessibility."));
    }

    public final void registerSystemAction(RemoteAction remoteAction, int i) {
        registerSystemAction_enforcePermission();
        if (this.mTraceManager.isA11yTracingEnabledForTypes(4L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.registerSystemAction", 4L, "action=" + remoteAction + ";actionId=" + i);
        }
        getSystemActionPerformer().registerSystemAction(i, remoteAction);
    }

    public final void registerUiTestAutomationService(IBinder iBinder, IAccessibilityServiceClient iAccessibilityServiceClient, AccessibilityServiceInfo accessibilityServiceInfo, int i, int i2) {
        registerUiTestAutomationService_enforcePermission();
        if (this.mTraceManager.isA11yTracingEnabledForTypes(4L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.registerUiTestAutomationService", 4L, "owner=" + iBinder + ";serviceClient=" + iAccessibilityServiceClient + ";accessibilityServiceInfo=" + accessibilityServiceInfo + ";flags=" + i2);
        }
        synchronized (this.mLock) {
            changeCurrentUserForTestAutomationIfNeededLocked(i);
            UiAutomationManager uiAutomationManager = this.mUiAutomationManager;
            Context context = this.mContext;
            int i3 = sIdCounter;
            sIdCounter = i3 + 1;
            uiAutomationManager.registerUiTestAutomationServiceLocked(iBinder, iAccessibilityServiceClient, context, accessibilityServiceInfo, i3, this.mMainHandler, this.mSecurityPolicy, this, this.mTraceManager, this.mWindowManagerService, getSystemActionPerformer(), this.mA11yWindowManager, i2);
            onUserStateChangedLocked(getUserStateLocked(this.mCurrentUserId), false);
        }
    }

    public final void removeAccessibilityInteractionConnection(IWindow iWindow) {
        if (this.mTraceManager.isA11yTracingEnabledForTypes(4L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.removeAccessibilityInteractionConnection", 4L, "window=" + iWindow);
        }
        AccessibilityWindowManager accessibilityWindowManager = this.mA11yWindowManager;
        synchronized (accessibilityWindowManager.mLock) {
            try {
                accessibilityWindowManager.mSecurityPolicy.resolveCallingUserIdEnforcingPermissionsLocked(UserHandle.getCallingUserId());
                IBinder asBinder = iWindow.asBinder();
                int removeAccessibilityInteractionConnectionInternalLocked = AccessibilityWindowManager.removeAccessibilityInteractionConnectionInternalLocked(asBinder, accessibilityWindowManager.mGlobalWindowTokens, accessibilityWindowManager.mGlobalInteractionConnections);
                if (removeAccessibilityInteractionConnectionInternalLocked >= 0) {
                    accessibilityWindowManager.onAccessibilityInteractionConnectionRemovedLocked(removeAccessibilityInteractionConnectionInternalLocked, asBinder);
                    return;
                }
                int size = accessibilityWindowManager.mWindowTokens.size();
                for (int i = 0; i < size; i++) {
                    int keyAt = accessibilityWindowManager.mWindowTokens.keyAt(i);
                    int removeAccessibilityInteractionConnectionInternalLocked2 = AccessibilityWindowManager.removeAccessibilityInteractionConnectionInternalLocked(asBinder, accessibilityWindowManager.getWindowTokensForUserLocked(keyAt), accessibilityWindowManager.getInteractionConnectionsForUserLocked(keyAt));
                    if (removeAccessibilityInteractionConnectionInternalLocked2 >= 0) {
                        accessibilityWindowManager.onAccessibilityInteractionConnectionRemovedLocked(removeAccessibilityInteractionConnectionInternalLocked2, asBinder);
                        return;
                    }
                }
            } finally {
            }
        }
    }

    public final boolean removeClient(IAccessibilityManagerClient iAccessibilityManagerClient, int i) {
        synchronized (this.mLock) {
            try {
                AccessibilityUserState userStateLocked = getUserStateLocked(this.mSecurityPolicy.resolveCallingUserIdEnforcingPermissionsLocked(i));
                this.mSecurityPolicy.getClass();
                if (AccessibilitySecurityPolicy.isCallerInteractingAcrossUsers(i)) {
                    return this.mGlobalClients.unregister(iAccessibilityManagerClient);
                }
                return userStateLocked.mUserClients.unregister(iAccessibilityManagerClient);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void requestImeLocked(AbstractAccessibilityServiceConnection abstractAccessibilityServiceConnection) {
        if (!(abstractAccessibilityServiceConnection instanceof AccessibilityServiceConnection) || (abstractAccessibilityServiceConnection instanceof ProxyAccessibilityServiceConnection)) {
            return;
        }
        AccessibilityServiceConnection accessibilityServiceConnection = (AccessibilityServiceConnection) abstractAccessibilityServiceConnection;
        this.mMainHandler.sendMessage(PooledLambda.obtainMessage(new AccessibilityManagerService$$ExternalSyntheticLambda9(3), this, accessibilityServiceConnection));
        this.mMainHandler.sendMessage(PooledLambda.obtainMessage(new AccessibilityManagerService$$ExternalSyntheticLambda9(4), this, accessibilityServiceConnection));
    }

    public final void resetClientsLocked(int i, RemoteCallbackList remoteCallbackList) {
        if (remoteCallbackList == null || remoteCallbackList.getRegisteredCallbackCount() == 0) {
            return;
        }
        synchronized (this.mLock) {
            for (int i2 = 0; i2 < remoteCallbackList.getRegisteredCallbackCount(); i2++) {
                try {
                    Client client = (Client) remoteCallbackList.getRegisteredCallbackCookie(i2);
                    if (client.mDeviceId == i) {
                        client.mDeviceId = 0;
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    public final void restoreAccessibilityButtonTargetsLocked(String str, String str2) {
        ArraySet arraySet = new ArraySet();
        readColonDelimitedStringToSet(str, new AccessibilityManagerService$$ExternalSyntheticLambda5(0), arraySet, false);
        readColonDelimitedStringToSet(str2, new AccessibilityManagerService$$ExternalSyntheticLambda5(0), arraySet, true);
        AccessibilityUserState userStateLocked = getUserStateLocked(0);
        userStateLocked.mAccessibilityButtonTargets.clear();
        userStateLocked.mAccessibilityButtonTargets.addAll(arraySet);
        persistColonDelimitedSetToSettingLocked("accessibility_button_targets", 0, userStateLocked.mAccessibilityButtonTargets, new AccessibilityManagerService$$ExternalSyntheticLambda5(0), null);
        scheduleNotifyClientsOfServicesStateChangeLocked(userStateLocked);
        onUserStateChangedLocked(userStateLocked, false);
    }

    public final void restoreCurrentUserAfterTestAutomationIfNeededLocked() {
        if (this.mVisibleBgUserIds == null) {
            Slogf.d("AccessibilityManagerService", "restoreCurrentUserForTestAutomationIfNeededLocked(): ignoring because device doesn't support visible background users");
            return;
        }
        int i = this.mRealCurrentUserId;
        if (i == -2) {
            Slogf.d("AccessibilityManagerService", "restoreCurrentUserForTestAutomationIfNeededLocked(): ignoring because mRealCurrentUserId is already USER_CURRENT");
            return;
        }
        Slogf.i("AccessibilityManagerService", "restoreCurrentUserForTestAutomationIfNeededLocked(): restoring current user to %d after using %d for test automation purposes", Integer.valueOf(i), Integer.valueOf(this.mCurrentUserId));
        int i2 = this.mRealCurrentUserId;
        this.mRealCurrentUserId = -2;
        switchUser(i2);
    }

    public final void restoreEnabledAccessibilityServicesLocked(int i, String str, String str2) {
        readColonDelimitedStringToSet(str, new AccessibilityManagerService$$ExternalSyntheticLambda5(5), this.mTempComponentNameSet, false);
        readColonDelimitedStringToSet(str2, new AccessibilityManagerService$$ExternalSyntheticLambda5(5), this.mTempComponentNameSet, true);
        AccessibilityUserState userStateLocked = getUserStateLocked(0);
        ((HashSet) userStateLocked.mEnabledServices).clear();
        userStateLocked.mEnabledServices.addAll(this.mTempComponentNameSet);
        persistComponentNamesToSettingLocked(0, "enabled_accessibility_services", userStateLocked.mEnabledServices);
        onUserStateChangedLocked(userStateLocked, false);
        migrateAccessibilityButtonSettingsIfNecessaryLocked(userStateLocked, null, i);
    }

    public final void scheduleNotifyClientsOfServicesStateChangeLocked(AccessibilityUserState accessibilityUserState) {
        int i = accessibilityUserState.mUserNonInteractiveUiTimeout;
        int i2 = accessibilityUserState.mUserInteractiveUiTimeout;
        if (i == 0 || i2 == 0) {
            ArrayList arrayList = accessibilityUserState.mBoundServices;
            int i3 = 0;
            int i4 = 0;
            for (int i5 = 0; i5 < arrayList.size(); i5++) {
                int interactiveUiTimeoutMillis = ((AccessibilityServiceConnection) arrayList.get(i5)).mAccessibilityServiceInfo.getInteractiveUiTimeoutMillis();
                if (i3 < interactiveUiTimeoutMillis) {
                    i3 = interactiveUiTimeoutMillis;
                }
                int nonInteractiveUiTimeoutMillis = ((AccessibilityServiceConnection) arrayList.get(i5)).mAccessibilityServiceInfo.getNonInteractiveUiTimeoutMillis();
                if (i4 < nonInteractiveUiTimeoutMillis) {
                    i4 = nonInteractiveUiTimeoutMillis;
                }
            }
            if (i == 0) {
                i = i4;
            }
            if (i2 == 0) {
                i2 = i3;
            }
        }
        accessibilityUserState.mNonInteractiveUiTimeout = i;
        accessibilityUserState.mInteractiveUiTimeout = i2;
        this.mMainHandler.sendMessage(PooledLambda.obtainMessage(new AccessibilityManagerService$$ExternalSyntheticLambda12(4), this, accessibilityUserState.mUserClients, Long.valueOf(IntPair.of(i2, i))));
    }

    public final void scheduleUpdateClientsIfNeededLocked(AccessibilityUserState accessibilityUserState, boolean z) {
        int clientStateLocked = getClientStateLocked(accessibilityUserState);
        if (accessibilityUserState.mLastSentClientState != clientStateLocked || z) {
            if (this.mGlobalClients.getRegisteredCallbackCount() > 0 || accessibilityUserState.mUserClients.getRegisteredCallbackCount() > 0) {
                accessibilityUserState.mLastSentClientState = clientStateLocked;
                this.mMainHandler.sendMessage(PooledLambda.obtainMessage(new AccessibilityManagerService$$ExternalSyntheticLambda12(6), this, Integer.valueOf(clientStateLocked), Integer.valueOf(accessibilityUserState.mUserId)));
            }
        }
    }

    public final boolean semCheckMdnieColorBlind(int[] iArr) {
        int i;
        boolean z;
        CVDCalculator cVDCalculator = this.cvdCalculator;
        cVDCalculator.mInputNums = iArr;
        double[] dArr = cVDCalculator.u;
        char c = 0;
        double d = dArr[0];
        double[] dArr2 = cVDCalculator.SpotsU;
        dArr2[0] = d;
        double[] dArr3 = cVDCalculator.v;
        double d2 = dArr3[0];
        double[] dArr4 = cVDCalculator.SpotsV;
        dArr4[0] = d2;
        int i2 = 0;
        while (true) {
            if (i2 >= 15) {
                break;
            }
            try {
                int i3 = cVDCalculator.mInputNums[i2];
                i2++;
                dArr2[i2] = dArr[i3];
                dArr4[i2] = dArr3[i3];
            } catch (ArrayIndexOutOfBoundsException unused) {
                throw new UnsupportedOperationException();
            }
        }
        CVDCalculator cVDCalculator2 = this.cvdCalculator;
        double[] dArr5 = new double[15];
        double[] dArr6 = new double[15];
        int i4 = 0;
        while (i4 < 15) {
            int i5 = i4 + 1;
            double[] dArr7 = cVDCalculator2.SpotsU;
            dArr5[i4] = dArr7[i5] - dArr7[i4];
            double[] dArr8 = cVDCalculator2.SpotsV;
            dArr6[i4] = dArr8[i5] - dArr8[i4];
            i4 = i5;
        }
        cVDCalculator2.getClass();
        int i6 = 0;
        double d3 = 0.0d;
        double d4 = 0.0d;
        for (i = 15; i6 < i; i = 15) {
            double d5 = dArr5[i6];
            double d6 = (d5 * 2.0d * dArr6[i6]) + d3;
            d4 += Math.pow(d5, 2.0d) - Math.pow(dArr6[i6], 2.0d);
            i6++;
            d3 = d6;
        }
        double atan = Math.atan(d3 / d4) / 2.0d;
        double[] dArr9 = new double[2];
        dArr9[0] = 0.0d;
        dArr9[1] = 0.0d;
        double d7 = atan < 0.0d ? 1.5707963267948966d + atan : atan - 1.5707963267948966d;
        int i7 = 0;
        while (i7 < 15) {
            int i8 = i7;
            dArr9[c] = Math.pow((Math.cos(atan) * dArr6[i7]) - (Math.sin(atan) * dArr5[i7]), 2.0d) + dArr9[c];
            dArr9[1] = Math.pow((Math.cos(d7) * dArr6[i8]) - (Math.sin(d7) * dArr5[i8]), 2.0d) + dArr9[1];
            i7 = i8 + 1;
            dArr6 = dArr6;
            c = 0;
        }
        dArr9[c] = Math.sqrt(dArr9[c] / 15.0d);
        double sqrt = Math.sqrt(dArr9[1] / 15.0d);
        dArr9[1] = sqrt;
        double d8 = dArr9[c];
        if (d8 > sqrt) {
            cVDCalculator2.majorRadius = d8;
            cVDCalculator2.minorRadius = sqrt;
            cVDCalculator2.majorAngle = (d7 * 180.0d) / 3.141592653589793d;
        } else {
            cVDCalculator2.majorRadius = sqrt;
            cVDCalculator2.minorRadius = d8;
            cVDCalculator2.majorAngle = (atan * 180.0d) / 3.141592653589793d;
        }
        double d9 = cVDCalculator2.majorRadius;
        cVDCalculator2.c_index = d9 / 9.23705d;
        Math.sqrt(Math.pow(cVDCalculator2.minorRadius, 2.0d) + Math.pow(d9, 2.0d));
        double d10 = cVDCalculator2.c_index;
        if (d10 <= 1.6d) {
            cVDCalculator2.CVDType = 3;
            z = false;
        } else {
            double d11 = cVDCalculator2.majorAngle;
            if (d11 >= 0.7d) {
                z = false;
                cVDCalculator2.CVDType = 0;
            } else {
                z = false;
                if (d11 < -65.0d) {
                    cVDCalculator2.CVDType = 2;
                } else {
                    cVDCalculator2.CVDType = 1;
                }
            }
        }
        if (d10 < 1.6d) {
            d10 = 1.6d;
        }
        if (d10 > 4.2d) {
            d10 = 4.2d;
        }
        double d12 = (d10 - 1.6d) / 2.6d;
        cVDCalculator2.CVDSeverity = d12 < 0.1d ? d12 * 5.0d : (((d12 - 0.1d) * 5.0d) / 9.0d) + 0.5d;
        this.mCVDType = this.cvdCalculator.CVDType;
        this.mCVDSeverity = (float) (((int) Math.round(r1.CVDSeverity * 10.0d)) / 10.0d);
        Settings.Secure.putIntForUser(this.mContext.getContentResolver(), "color_blind_cvdtype", this.mCVDType, ActivityManager.getCurrentUser());
        Settings.Secure.putFloatForUser(this.mContext.getContentResolver(), "color_blind_cvdseverity", this.mCVDSeverity, ActivityManager.getCurrentUser());
        Settings.Secure.putFloatForUser(this.mContext.getContentResolver(), "color_blind_user_parameter", 0.5f, ActivityManager.getCurrentUser());
        if (!Settings.System.putIntForUser(this.mContext.getContentResolver(), "color_blind_test", 1, ActivityManager.getCurrentUser())) {
            throw new UnsupportedOperationException();
        }
        if (this.mCVDType != 3) {
            return true;
        }
        return z;
    }

    public final boolean semDisableMdnieColorFilter() {
        if (!A11yRune.A11Y_COLOR_BOOL_SUPPORT_COLOR_FILTER_MDNIE_HW) {
            return true;
        }
        Log.d("AccessibilityManagerService", "semDisableMdnieColorFilter ");
        semDisableMdnieColorFilter_enforcePermission();
        SemMdnieManager semMdnieManager = (SemMdnieManager) this.mContext.getSystemService("mDNIe");
        if (semMdnieManager != null) {
            return semMdnieManager.setColorVision(false, 0, 0);
        }
        Log.w("AccessibilityManagerService", "SemMdnieManager is null.");
        return false;
    }

    public final void semDisableWindowMagnification() {
        semDisableWindowMagnification_enforcePermission();
        getMagnificationConnectionManager().disableWindowMagnification(getDisplayId(), true);
        getMagnificationConnectionManager().setCursorVisible(getDisplayId(), false);
    }

    public final void semDumpCallStack(String str) {
        if (this.mCallStack.size() >= 20) {
            this.mCallStack.remove(0);
        }
        this.mCallStack.add(str);
    }

    public final boolean semEnableMdnieColorFilter(int i, int i2) {
        if (i2 < 0) {
            return false;
        }
        if (!A11yRune.A11Y_COLOR_BOOL_SUPPORT_COLOR_FILTER_MDNIE_HW) {
            return true;
        }
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(i, i2, "semEnableMdnieColorFilter ", ", ", "AccessibilityManagerService");
        semEnableMdnieColorFilter_enforcePermission();
        SemMdnieManager semMdnieManager = (SemMdnieManager) this.mContext.getSystemService("mDNIe");
        if (semMdnieManager != null) {
            return semMdnieManager.setColorVision(true, i, i2);
        }
        Log.w("AccessibilityManagerService", "SemMdnieManager is null.");
        return false;
    }

    public final void semEnableWindowMagnification(int i, int i2) {
        semEnableWindowMagnification_enforcePermission();
        if (TextUtils.equals(MagnificationConnectionManager.connectionStateToString(getMagnificationConnectionManager().mConnectionState), "DISCONNECTED")) {
            getMagnificationConnectionManager().requestConnection(true);
        }
        Settings.Secure.putIntForUser(this.mContext.getContentResolver(), "accessibility_magnification_mode", 2, -2);
        Settings.System.putIntForUser(this.mContext.getContentResolver(), "accessibility_am_magnification_mode", 1, -2);
        if (Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "accessibility_magnification_capability", 3, -2) == 1) {
            Settings.Secure.putIntForUser(this.mContext.getContentResolver(), "accessibility_magnification_capability", 2, -2);
        }
        float floatForUser = Settings.Secure.getFloatForUser(this.mContext.getContentResolver(), "accessibility_display_magnification_scale", 1.0f, -2);
        getMagnificationConnectionManager().enableWindowMagnification(getDisplayId(), floatForUser < 1.0f ? 1.0f : floatForUser, i, i2, MagnificationAnimationCallback.STUB_ANIMATION_CALLBACK, 0, 0);
        getMagnificationConnectionManager().setCursorVisible(getDisplayId(), true);
    }

    public final Rect semGetWindowMagnificationBounds() {
        semGetWindowMagnificationBounds_enforcePermission();
        MagnificationConnectionManager magnificationConnectionManager = getMagnificationConnectionManager();
        int displayId = getDisplayId();
        synchronized (magnificationConnectionManager.mLock) {
            try {
                MagnificationConnectionManager.WindowMagnifier windowMagnifier = (MagnificationConnectionManager.WindowMagnifier) magnificationConnectionManager.mWindowMagnifiers.get(displayId);
                if (windowMagnifier == null) {
                    return null;
                }
                return windowMagnifier.mBounds;
            } finally {
            }
        }
    }

    public final float semGetWindowMagnificationScale() {
        semGetWindowMagnificationScale_enforcePermission();
        return getMagnificationConnectionManager().getScale(getDisplayId());
    }

    public final void semInjectInputEventToInputFilter(InputEvent inputEvent, int i) {
        AccessibilityInputFilter accessibilityInputFilter;
        semInjectInputEventToInputFilter_enforcePermission();
        synchronized (this.mLock) {
            try {
                if (this.mHasInputFilter && (accessibilityInputFilter = this.mInputFilter) != null) {
                    accessibilityInputFilter.onInputEvent(inputEvent, i);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final boolean semIsAccessibilityButtonShown() {
        AccessibilityUserState userStateLocked = getUserStateLocked(this.mCurrentUserId);
        for (int size = userStateLocked.mBoundServices.size() - 1; size >= 0; size--) {
            if (((AccessibilityServiceConnection) userStateLocked.mBoundServices.get(size)).mRequestAccessibilityButton) {
                return true;
            }
        }
        return false;
    }

    public final boolean semIsAccessibilityServiceEnabled(int i) {
        Log.i("AccessibilityManagerService", "semIsAccessibilityServiceEnabled()");
        boolean z = (i & 16) != 0;
        boolean z2 = (i & 32) != 0;
        boolean z3 = (i & 64) != 0;
        boolean z4 = (i & 2048) != 0;
        boolean z5 = (i & 4096) != 0;
        boolean z6 = (i & 8192) != 0;
        String stringForUser = Settings.Secure.getStringForUser(this.mContext.getContentResolver(), "enabled_accessibility_services", this.mCurrentUserId);
        if (stringForUser == null) {
            Log.i("AccessibilityManagerService", "accesibilityService is null");
            return false;
        }
        if (z && stringForUser.matches("(?i).*com.google.android.marvin.talkback.TalkBackService.*")) {
            return true;
        }
        if (z2 && stringForUser.matches("(?i).*com.samsung.android.accessibility.talkback/com.samsung.android.marvin.talkback.TalkBackService.*")) {
            return true;
        }
        if (z3 && stringForUser.matches("(?i).*com.samsung.accessibility/com.samsung.accessibility.universalswitch.UniversalSwitchService.*")) {
            return true;
        }
        if (z4 && stringForUser.matches("(?i).*com.google.android.accessibility.accessibilitymenu.AccessibilityMenuService.*")) {
            return true;
        }
        if (z5 && stringForUser.matches("(?i).*com.google.android.accessibility.selecttospeak.SelectToSpeakService.*")) {
            return true;
        }
        return z6 && stringForUser.matches("(?i).*com.samsung.accessibility/com.samsung.accessibility.assistantmenu.serviceframework.AssistantMenuService.*");
    }

    public final boolean semIsDarkScreenMode() {
        semIsDarkScreenMode_enforcePermission();
        if (isScreenReaderEnabled()) {
            return this.mCurtainModeIsRunning;
        }
        return false;
    }

    public final boolean semIsWindowMagnificationEnabled() {
        semIsWindowMagnificationEnabled_enforcePermission();
        return getMagnificationConnectionManager().isWindowMagnifierEnabled(getDisplayId());
    }

    public final void semLockNow() {
        WindowManagerService asInterface = IWindowManager.Stub.asInterface(ServiceManager.getService("window"));
        asInterface.lockNow(null);
        asInterface.dismissKeyguard(null, null);
        new LockPatternUtils(this.mContext).requireCredentialEntry(-1);
    }

    public final void semMoveWindowMagnification(float f, float f2) {
        semMoveWindowMagnification_enforcePermission();
        getMagnificationConnectionManager().moveWindowMagnification(getDisplayId(), f, f2);
    }

    public final void semOpenDeviceOptions() {
        this.mWindowManagerService.showGlobalActions();
    }

    public final void semPerformAccessibilityButtonClick(int i, int i2, String str) {
        if (str.equals("com.android.server.accessibility.MagnificationController")) {
            AccessibilityStatsLogUtils.logAccessibilityShortcutActivated(this.mContext, AccessibilityShortcutController.MAGNIFICATION_COMPONENT_NAME, i2, !this.mMagnificationController.getFullScreenMagnificationController().isActivated(i));
            sendAccessibilityButtonToInputFilter(i);
            return;
        }
        ComponentName unflattenFromString = ComponentName.unflattenFromString(str);
        if (unflattenFromString == null) {
            Slog.d("AccessibilityManagerService", "Perform shortcut failed, invalid target name:".concat(str));
            return;
        }
        if (performAccessibilityFrameworkFeature(i2, unflattenFromString)) {
            AccessibilityUtils.updateProfile(this.mContext, str);
            return;
        }
        if (performAccessibilityShortcutTargetActivity(i, unflattenFromString)) {
            AccessibilityStatsLogUtils.logAccessibilityShortcutActivated(this.mContext, unflattenFromString, i2);
            AccessibilityUtils.updateProfile(this.mContext, str);
        } else if (performAccessibilityShortcutTargetService(i, i2, unflattenFromString)) {
            AccessibilityUtils.updateProfile(this.mContext, str);
        }
    }

    public final void semRegisterAssistantMenu(IBinder iBinder) {
        Slog.e("AccessibilityManagerService", "semRegisterAssistantMenu Set the listener from AMS");
        if (iBinder != null) {
            this.mAssistantMenuMsgnr = new Messenger(iBinder);
        } else {
            this.mAssistantMenuMsgnr = null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x00f1  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x011f  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0122 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0123  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0111  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean semSetColorBlind(boolean r11, float r12) {
        /*
            Method dump skipped, instructions count: 338
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.accessibility.AccessibilityManagerService.semSetColorBlind(boolean, float):boolean");
    }

    public final boolean semSetMdnieAccessibilityMode(int i, boolean z) {
        Log.d("AccessibilityManagerService", "semSetMdnieAccessibilityMode " + i + ", " + z);
        SemMdnieManager semMdnieManager = (SemMdnieManager) this.mContext.getSystemService("mDNIe");
        if (semMdnieManager != null) {
            return semMdnieManager.setmDNIeAccessibilityMode(i, z);
        }
        Log.w("AccessibilityManagerService", "SemMdnieManager is null.");
        return false;
    }

    public final void semSetTwoFingerGestureRecognitionEnabled(boolean z) {
        this.shouldRecogniseTwoFingerGestures = z;
    }

    public final void semToggleDarkScreenMode() {
        semToggleDarkScreenMode_enforcePermission();
        Log.i("AccessibilityManagerService", "semToggleDarkScreenMode()");
        if (isScreenReaderEnabled() || this.mCurtainModeIsRunning) {
            this.mCurtainModeIsRunning = !this.mCurtainModeIsRunning;
            String string = this.mContext.getString(17042720);
            String string2 = this.mCurtainModeIsRunning ? this.mContext.getString(17042719, string) : this.mContext.getString(17042718, string);
            AccessibilityManager accessibilityManager = AccessibilityManager.getInstance(this.mContext);
            AccessibilityEvent obtain = AccessibilityEvent.obtain(32);
            obtain.getText().add(string2);
            if (accessibilityManager != null) {
                accessibilityManager.sendAccessibilityEvent(obtain);
            }
            SemMdnieManager semMdnieManager = (SemMdnieManager) this.mContext.getSystemService("mDNIe");
            if (semMdnieManager == null) {
                Log.w("AccessibilityManagerService", "SemMdnieManager is null.");
                return;
            }
            semMdnieManager.setmDNIeScreenCurtain(this.mCurtainModeIsRunning);
            if (this.mCurtainModeIsRunning) {
                return;
            }
            boolean z = Settings.System.getIntForUser(this.mContext.getContentResolver(), "color_blind", 0, -2) == 1;
            boolean z2 = Settings.System.getIntForUser(this.mContext.getContentResolver(), "greyscale_mode", 0, -2) == 1;
            if (SemEmergencyManager.isEmergencyMode(this.mContext)) {
                return;
            }
            if (z2) {
                semMdnieManager.setmDNIeAccessibilityMode(4, true);
            } else if (z) {
                try {
                    semSetColorBlind(true, Settings.Secure.getFloatForUser(this.mContext.getContentResolver(), "color_blind_user_parameter", FullScreenMagnificationGestureHandler.MAX_SCALE, -2));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public final void semTurnOffAccessibilityService(int i) {
        Log.i("AccessibilityManagerService", "semTurnOffAccessibilityService()");
        TextUtils.SimpleStringSplitter simpleStringSplitter = new TextUtils.SimpleStringSplitter(':');
        String stringForUser = Settings.Secure.getStringForUser(this.mContext.getContentResolver(), "enabled_accessibility_services", this.mCurrentUserId);
        if (stringForUser == null) {
            Log.i("AccessibilityManagerService", "enabledServicesSetting is null");
            return;
        }
        HashSet hashSet = new HashSet();
        simpleStringSplitter.setString(stringForUser);
        while (simpleStringSplitter.hasNext()) {
            ComponentName unflattenFromString = ComponentName.unflattenFromString(simpleStringSplitter.next());
            if (unflattenFromString != null) {
                hashSet.add(unflattenFromString);
            }
        }
        boolean z = (i & 16) != 0;
        boolean z2 = (i & 32) != 0;
        boolean z3 = (i & 64) != 0;
        boolean z4 = (i & 2048) != 0;
        boolean z5 = (i & 4096) != 0;
        boolean z6 = (i & 8192) != 0;
        if (z) {
            hashSet.remove(ComponentName.unflattenFromString("com.google.android.marvin.talkback/com.google.android.marvin.talkback.TalkBackService"));
        }
        if (z2) {
            hashSet.remove(ComponentName.unflattenFromString("com.samsung.android.accessibility.talkback/com.samsung.android.marvin.talkback.TalkBackService"));
        }
        if (z3) {
            hashSet.remove(ComponentName.unflattenFromString("com.samsung.accessibility/com.samsung.accessibility.universalswitch.UniversalSwitchService"));
        }
        if (z4) {
            hashSet.remove(ComponentName.unflattenFromString("com.google.android.marvin.talkback/com.google.android.accessibility.accessibilitymenu.AccessibilityMenuService"));
        }
        if (z5) {
            hashSet.remove(ComponentName.unflattenFromString("com.google.android.marvin.talkback/com.google.android.accessibility.selecttospeak.SelectToSpeakService"));
        }
        if (z6) {
            hashSet.remove(ComponentName.unflattenFromString("com.samsung.accessibility/com.samsung.accessibility.assistantmenu.serviceframework.AssistantMenuService"));
        }
        StringBuilder sb = new StringBuilder();
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            sb.append(((ComponentName) it.next()).flattenToString());
            sb.append(':');
        }
        int length = sb.length();
        if (length > 0) {
            sb.deleteCharAt(length - 1);
        }
        Settings.Secure.putStringForUser(this.mContext.getContentResolver(), "enabled_accessibility_services", sb.toString(), this.mCurrentUserId);
        if (hashSet.isEmpty()) {
            Settings.Secure.putIntForUser(this.mContext.getContentResolver(), "accessibility_enabled", 0, this.mCurrentUserId);
        }
    }

    public final void semTurnOnAccessibilityService(int i) {
        ComponentName unflattenFromString;
        Log.i("AccessibilityManagerService", "semTurnOnAccessibilityService()");
        TextUtils.SimpleStringSplitter simpleStringSplitter = new TextUtils.SimpleStringSplitter(':');
        String stringForUser = Settings.Secure.getStringForUser(this.mContext.getContentResolver(), "enabled_accessibility_services", this.mCurrentUserId);
        if (stringForUser == null) {
            Log.i("AccessibilityManagerService", "enabledServicesSetting == null");
            Settings.Secure.putStringForUser(this.mContext.getContentResolver(), "enabled_accessibility_services", "", this.mCurrentUserId);
            stringForUser = Settings.Secure.getStringForUser(this.mContext.getContentResolver(), "enabled_accessibility_services", this.mCurrentUserId);
        }
        if (semIsAccessibilityServiceEnabled(i)) {
            Log.i("AccessibilityManagerService", "The service is already enabled");
            return;
        }
        HashSet hashSet = new HashSet();
        if (stringForUser != null) {
            simpleStringSplitter.setString(stringForUser);
            while (simpleStringSplitter.hasNext()) {
                ComponentName unflattenFromString2 = ComponentName.unflattenFromString(simpleStringSplitter.next());
                if (unflattenFromString2 != null) {
                    hashSet.add(unflattenFromString2);
                }
            }
        }
        if (i == 16) {
            Log.i("AccessibilityManagerService", "SEM_STATE_FLAG_GOOGLE_TALKBACK");
            unflattenFromString = ComponentName.unflattenFromString("com.google.android.marvin.talkback/com.google.android.marvin.talkback.TalkBackService");
        } else if (i == 32) {
            Log.i("AccessibilityManagerService", "SEM_STATE_FLAG_VOICE_ASSISTANT");
            unflattenFromString = ComponentName.unflattenFromString("com.samsung.android.accessibility.talkback/com.samsung.android.marvin.talkback.TalkBackService");
        } else if (i == 64) {
            Log.i("AccessibilityManagerService", "SEM_STATE_FLAG_UNIVERSAL_SWITCH");
            unflattenFromString = ComponentName.unflattenFromString("com.samsung.accessibility/com.samsung.accessibility.universalswitch.UniversalSwitchService");
        } else if (i == 2048) {
            Log.i("AccessibilityManagerService", "SEM_STATE_FLAG_ACCESSIBILITY_MENU");
            unflattenFromString = ComponentName.unflattenFromString("com.google.android.marvin.talkback/com.google.android.accessibility.accessibilitymenu.AccessibilityMenuService");
        } else if (i == 4096) {
            Log.i("AccessibilityManagerService", "SEM_STATE_FLAG_SELECT_TO_SPEAK");
            unflattenFromString = ComponentName.unflattenFromString("com.google.android.marvin.talkback/com.google.android.accessibility.selecttospeak.SelectToSpeakService");
        } else {
            if (i != 8192) {
                return;
            }
            Log.i("AccessibilityManagerService", "SEM_STATE_FLAG_ASSISTANT_MENU");
            unflattenFromString = ComponentName.unflattenFromString("com.samsung.accessibility/com.samsung.accessibility.assistantmenu.serviceframework.AssistantMenuService");
        }
        hashSet.add(unflattenFromString);
        StringBuilder sb = new StringBuilder();
        Iterator it = hashSet.iterator();
        while (it.hasNext()) {
            sb.append(((ComponentName) it.next()).flattenToString());
            sb.append(':');
        }
        int length = sb.length();
        if (length > 0) {
            sb.deleteCharAt(length - 1);
        }
        Settings.Secure.putStringForUser(this.mContext.getContentResolver(), "enabled_accessibility_services", sb.toString(), this.mCurrentUserId);
        Settings.Secure.putIntForUser(this.mContext.getContentResolver(), "accessibility_enabled", 1, this.mCurrentUserId);
    }

    public final void semUpdateAssitantMenu(Bundle bundle) {
        if (this.mAssistantMenuMsgnr != null) {
            Message obtain = Message.obtain(null, 1, bundle);
            try {
                obtain.setData(bundle);
                Slog.e("AccessibilityManagerService", "Thread name:" + Thread.currentThread().getName() + "Thread ID:" + Thread.currentThread().getId());
                Slog.e("AccessibilityManagerService", bundle.getString("ActivityName") + "process ID:" + Process.myPid());
                this.mAssistantMenuMsgnr.send(obtain);
            } catch (RemoteException unused) {
                Slog.e("AccessibilityManagerService", "semUpdateAssitantMenu: err during get currentThread");
            }
        }
    }

    public final void sendAccessibilityButtonToInputFilter(int i) {
        AccessibilityInputFilter accessibilityInputFilter;
        MagnificationGestureHandler magnificationGestureHandler;
        synchronized (this.mLock) {
            try {
                if (this.mHasInputFilter && (accessibilityInputFilter = this.mInputFilter) != null && accessibilityInputFilter.mMagnificationGestureHandler.size() != 0 && (magnificationGestureHandler = (MagnificationGestureHandler) accessibilityInputFilter.mMagnificationGestureHandler.get(i)) != null) {
                    if (MagnificationGestureHandler.DEBUG_ALL) {
                        Slog.i(magnificationGestureHandler.mLogTag, "notifyShortcutTriggered():");
                    }
                    if (magnificationGestureHandler.mDetectShortcutTrigger) {
                        magnificationGestureHandler.handleShortcutTriggered();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void sendAccessibilityEvent(AccessibilityEvent accessibilityEvent, int i) {
        boolean z;
        int resolveCallingUserIdEnforcingPermissionsLocked;
        boolean z2;
        boolean z3;
        boolean z4;
        if (this.mTraceManager.isA11yTracingEnabledForTypes(4L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.sendAccessibilityEvent", 4L, "event=" + accessibilityEvent + ";userId=" + i);
        }
        if (SEC_DEBUG) {
            Slog.d("AccessibilityManagerService", "sendAccessibilityEvent " + accessibilityEvent.toString());
        }
        synchronized (this.mLock) {
            try {
                z = false;
                if (accessibilityEvent.getWindowId() == -3) {
                    AccessibilityWindowManager accessibilityWindowManager = this.mA11yWindowManager;
                    int size = accessibilityWindowManager.mDisplayWindowsObservers.size();
                    AccessibilityWindowInfo accessibilityWindowInfo = null;
                    for (int i2 = 0; i2 < size; i2++) {
                        AccessibilityWindowManager.DisplayWindowsObserver displayWindowsObserver = (AccessibilityWindowManager.DisplayWindowsObserver) accessibilityWindowManager.mDisplayWindowsObservers.valueAt(i2);
                        if (displayWindowsObserver != null) {
                            List list = displayWindowsObserver.mWindows;
                            if (list != null) {
                                int size2 = ((ArrayList) list).size();
                                for (int i3 = 0; i3 < size2; i3++) {
                                    AccessibilityWindowInfo accessibilityWindowInfo2 = (AccessibilityWindowInfo) ((ArrayList) displayWindowsObserver.mWindows).get(i3);
                                    if (accessibilityWindowInfo2.isInPictureInPictureMode()) {
                                        accessibilityWindowInfo = accessibilityWindowInfo2;
                                        break;
                                    }
                                }
                            }
                            accessibilityWindowInfo = null;
                            if (accessibilityWindowInfo != null) {
                                break;
                            }
                        }
                    }
                    if (accessibilityWindowInfo != null) {
                        accessibilityEvent.setWindowId(accessibilityWindowInfo.getId());
                    }
                }
                resolveCallingUserIdEnforcingPermissionsLocked = this.mSecurityPolicy.resolveCallingUserIdEnforcingPermissionsLocked(i);
                accessibilityEvent.setPackageName(this.mSecurityPolicy.resolveValidReportedPackageLocked(accessibilityEvent.getPackageName(), UserHandle.getCallingAppId(), resolveCallingUserIdEnforcingPermissionsLocked, IAccessibilityManager.Stub.getCallingPid()));
                int i4 = this.mCurrentUserId;
                if (resolveCallingUserIdEnforcingPermissionsLocked == i4) {
                    AccessibilitySecurityPolicy accessibilitySecurityPolicy = this.mSecurityPolicy;
                    accessibilitySecurityPolicy.getClass();
                    int eventType = accessibilityEvent.getEventType();
                    switch (eventType) {
                        default:
                            if (eventType != 16 && eventType != 4096 && eventType != 8192) {
                                z4 = accessibilitySecurityPolicy.isRetrievalAllowingWindowLocked(i4, accessibilityEvent.getWindowId());
                                break;
                            }
                            break;
                        case 32:
                        case 64:
                        case 128:
                        case 256:
                        case 512:
                        case 1024:
                        case EndpointMonitorConst.FLAG_TRACING_PROCESS_PERMISSIONS_MODIFICATION /* 16384 */:
                        case 262144:
                        case 524288:
                        case 1048576:
                        case 2097152:
                        case 4194304:
                        case 16777216:
                            z4 = true;
                            break;
                    }
                    if (z4) {
                        this.mA11yWindowManager.updateActiveAndAccessibilityFocusedWindowLocked(this.mCurrentUserId, accessibilityEvent.getWindowId(), accessibilityEvent.getEventType(), accessibilityEvent.getAction(), accessibilityEvent.getSourceNodeId());
                        this.mSecurityPolicy.getClass();
                        if ((accessibilityEvent.getEventType() & 71547327) == 0) {
                            accessibilityEvent.setSource(null);
                        }
                        z2 = true;
                    } else {
                        z2 = false;
                    }
                    if (this.mHasInputFilter && this.mInputFilter != null) {
                        this.mMainHandler.sendMessage(PooledLambda.obtainMessage(new AccessibilityManagerService$$ExternalSyntheticLambda9(0), this, AccessibilityEvent.obtain(accessibilityEvent)));
                    }
                } else {
                    z2 = false;
                }
            } finally {
            }
        }
        if (z2) {
            int displayId = accessibilityEvent.getDisplayId();
            int windowId = accessibilityEvent.getWindowId();
            if (windowId != -1 && displayId == -1) {
                displayId = this.mA11yWindowManager.getDisplayIdByUserIdAndWindowId(resolveCallingUserIdEnforcingPermissionsLocked, windowId);
                accessibilityEvent.setDisplayId(displayId);
            }
            synchronized (this.mLock) {
                try {
                    z3 = accessibilityEvent.getEventType() == 32 && displayId != -1 && this.mA11yWindowManager.isTrackingWindowsLocked(displayId);
                } finally {
                }
            }
            if (z3) {
                if (this.mTraceManager.isA11yTracingEnabledForTypes(512L)) {
                    this.mTraceManager.logTrace("WindowManagerInternal.computeWindowsForAccessibility", 512L, VibrationParam$1$$ExternalSyntheticOutline0.m(displayId, "display="));
                }
                ((WindowManagerInternal) LocalServices.getService(WindowManagerInternal.class)).computeWindowsForAccessibility(displayId);
                synchronized (this.mLock) {
                    try {
                        int resolveParentWindowIdLocked = this.mA11yWindowManager.resolveParentWindowIdLocked(accessibilityEvent.getWindowId());
                        AccessibilityWindowManager accessibilityWindowManager2 = this.mA11yWindowManager;
                        int resolveParentWindowIdLocked2 = accessibilityWindowManager2.resolveParentWindowIdLocked(resolveParentWindowIdLocked);
                        AccessibilityWindowManager.DisplayWindowsObserver displayWindowObserverByWindowIdLocked = accessibilityWindowManager2.getDisplayWindowObserverByWindowIdLocked(resolveParentWindowIdLocked2);
                        if ((displayWindowObserverByWindowIdLocked != null ? (WindowInfo) displayWindowObserverByWindowIdLocked.mWindowInfoById.get(resolveParentWindowIdLocked2) : null) == null) {
                            SendWindowStateChangedEventRunnable sendWindowStateChangedEventRunnable = new SendWindowStateChangedEventRunnable(new AccessibilityEvent(accessibilityEvent));
                            this.mMainHandler.postDelayed(sendWindowStateChangedEventRunnable, 500L);
                            ((ArrayList) this.mSendWindowStateChangedEventRunnables).add(sendWindowStateChangedEventRunnable);
                            z = true;
                        }
                    } finally {
                    }
                }
                if (z) {
                    return;
                }
            }
            synchronized (this.mLock) {
                dispatchAccessibilityEventLocked(accessibilityEvent);
            }
        }
        if (OWN_PROCESS_ID != Binder.getCallingPid()) {
            accessibilityEvent.recycle();
        }
    }

    public final void sendAccessibilityEventForCurrentUserLocked(AccessibilityEvent accessibilityEvent) {
        if (accessibilityEvent.getWindowChanges() == 1) {
            int windowId = accessibilityEvent.getWindowId();
            for (int size = ((ArrayList) this.mSendWindowStateChangedEventRunnables).size() - 1; size >= 0; size--) {
                SendWindowStateChangedEventRunnable sendWindowStateChangedEventRunnable = (SendWindowStateChangedEventRunnable) ((ArrayList) this.mSendWindowStateChangedEventRunnables).get(size);
                if (sendWindowStateChangedEventRunnable.mWindowId == windowId) {
                    this.mMainHandler.removeCallbacks(sendWindowStateChangedEventRunnable);
                    ((ArrayList) AccessibilityManagerService.this.mSendWindowStateChangedEventRunnables).remove(sendWindowStateChangedEventRunnable);
                    AccessibilityManagerService.this.dispatchAccessibilityEventLocked(sendWindowStateChangedEventRunnable.mPendingEvent);
                }
            }
        }
        int i = this.mCurrentUserId;
        accessibilityEvent.setEventTime(SystemClock.uptimeMillis());
        this.mMainHandler.sendMessage(PooledLambda.obtainMessage(new AccessibilityManagerService$$ExternalSyntheticLambda12(5), this, accessibilityEvent, Integer.valueOf(i)));
    }

    public final boolean sendFingerprintGesture(int i) {
        int i2;
        if (this.mTraceManager.isA11yTracingEnabledForTypes(131076L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.sendFingerprintGesture", 131076L, VibrationParam$1$$ExternalSyntheticOutline0.m(i, "gestureKeyCode="));
        }
        synchronized (this.mLock) {
            if (UserHandle.getAppId(Binder.getCallingUid()) != 1000) {
                throw new SecurityException("Only SYSTEM can call sendFingerprintGesture");
            }
        }
        FingerprintGestureDispatcher fingerprintGestureDispatcher = this.mFingerprintGestureDispatcher;
        if (fingerprintGestureDispatcher == null) {
            return false;
        }
        synchronized (fingerprintGestureDispatcher.mLock) {
            try {
                if (((ArrayList) fingerprintGestureDispatcher.mCapturingClients).isEmpty()) {
                    return false;
                }
                switch (i) {
                    case 280:
                        i2 = 4;
                        break;
                    case FrameworkStatsLog.ASSISTANT_INVOCATION_REPORTED /* 281 */:
                        i2 = 8;
                        break;
                    case FrameworkStatsLog.DISPLAY_WAKE_REPORTED /* 282 */:
                        i2 = 2;
                        break;
                    case 283:
                        i2 = 1;
                        break;
                    default:
                        return false;
                }
                ArrayList arrayList = new ArrayList(fingerprintGestureDispatcher.mCapturingClients);
                for (int i3 = 0; i3 < arrayList.size(); i3++) {
                    ((FingerprintGestureDispatcher.FingerprintGestureClient) arrayList.get(i3)).onFingerprintGesture(i2);
                }
                return true;
            } finally {
            }
        }
    }

    public final boolean sendMotionEventToListeningServices(MotionEvent motionEvent) {
        boolean z;
        MotionEvent obtain = MotionEvent.obtain(motionEvent);
        int displayId = obtain.getDisplayId();
        synchronized (this.mLock) {
            try {
                AccessibilityUserState userStateLocked = getUserStateLocked(this.mCurrentUserId);
                z = false;
                for (int size = userStateLocked.mBoundServices.size() - 1; size >= 0; size--) {
                    AccessibilityServiceConnection accessibilityServiceConnection = (AccessibilityServiceConnection) userStateLocked.mBoundServices.get(size);
                    accessibilityServiceConnection.getClass();
                    if ((obtain.getSource() & (-256) & accessibilityServiceConnection.mGenericMotionEventSources) == 0) {
                        if (obtain.isFromSource(4098)) {
                            if (!accessibilityServiceConnection.isServiceDetectsGesturesEnabled(displayId)) {
                            }
                        }
                    }
                    accessibilityServiceConnection.mMainHandler.sendMessage(PooledLambda.obtainMessage(new AccessibilityServiceConnection$$ExternalSyntheticLambda0(), accessibilityServiceConnection, obtain));
                    z = true;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
        return z;
    }

    public final boolean sendRestrictedDialogIntent(String str, int i, int i2) {
        if (isAccessibilityTargetAllowed(str, i, i2)) {
            return false;
        }
        Context context = this.mContext;
        DevicePolicyManager devicePolicyManager = (DevicePolicyManager) context.getSystemService(DevicePolicyManager.class);
        RestrictedLockUtils.EnforcedAdmin enforcedAdmin = null;
        if (devicePolicyManager != null) {
            RestrictedLockUtils.EnforcedAdmin profileOrDeviceOwner = RestrictedLockUtils.getProfileOrDeviceOwner(context, i2 == -10000 ? null : UserHandle.of(i2));
            boolean isAccessibilityServicePermittedByAdmin = profileOrDeviceOwner != null ? devicePolicyManager.isAccessibilityServicePermittedByAdmin(profileOrDeviceOwner.component, str, i2) : true;
            int managedProfileId = RestrictedLockUtilsInternal.getManagedProfileId(context, i2);
            RestrictedLockUtils.EnforcedAdmin profileOrDeviceOwner2 = RestrictedLockUtils.getProfileOrDeviceOwner(context, managedProfileId == -10000 ? null : UserHandle.of(managedProfileId));
            boolean isAccessibilityServicePermittedByAdmin2 = profileOrDeviceOwner2 != null ? devicePolicyManager.isAccessibilityServicePermittedByAdmin(profileOrDeviceOwner2.component, str, managedProfileId) : true;
            if (!isAccessibilityServicePermittedByAdmin && !isAccessibilityServicePermittedByAdmin2) {
                enforcedAdmin = RestrictedLockUtils.EnforcedAdmin.MULTIPLE_ENFORCED_ADMIN;
            } else if (!isAccessibilityServicePermittedByAdmin) {
                enforcedAdmin = profileOrDeviceOwner;
            } else if (!isAccessibilityServicePermittedByAdmin2) {
                enforcedAdmin = profileOrDeviceOwner2;
            }
        }
        if (enforcedAdmin == null) {
            if (com.android.internal.hidden_from_bootclasspath.android.permission.flags.Flags.enhancedConfirmationModeApisEnabled() && android.security.Flags.extendEcmToAllSettings()) {
                try {
                    this.mContext.startActivity(((EnhancedConfirmationManager) this.mContext.getSystemService(EnhancedConfirmationManager.class)).createRestrictedSettingDialogIntent(str, "android:bind_accessibility_service"));
                } catch (PackageManager.NameNotFoundException e) {
                    Log.e("AccessibilityManagerService", "Exception when retrieving package:" + str, e);
                }
            } else {
                Context context2 = this.mContext;
                Intent intent = new Intent("android.settings.SHOW_RESTRICTED_SETTING_DIALOG");
                intent.putExtra("android.intent.extra.PACKAGE_NAME", str);
                intent.putExtra("android.intent.extra.UID", i);
                context2.startActivity(intent);
            }
            return true;
        }
        Context context3 = this.mContext;
        Intent intent2 = new Intent("android.settings.SHOW_ADMIN_SUPPORT_DETAILS");
        ComponentName componentName = enforcedAdmin.component;
        if (componentName != null) {
            intent2.putExtra("android.app.extra.DEVICE_ADMIN", componentName);
        }
        intent2.putExtra("android.intent.extra.USER", enforcedAdmin.user);
        int myUserId = UserHandle.myUserId();
        UserHandle userHandle = enforcedAdmin.user;
        if (userHandle != null) {
            if (((UserManager) context3.getSystemService(UserManager.class)).getUserProfiles().contains(UserHandle.of(userHandle.getIdentifier()))) {
                myUserId = enforcedAdmin.user.getIdentifier();
            }
        }
        intent2.putExtra("android.app.extra.RESTRICTION", enforcedAdmin.enforcedRestriction);
        context3.startActivityAsUser(intent2, UserHandle.of(myUserId));
        return true;
    }

    public final void sendStateToClients(final int i, RemoteCallbackList remoteCallbackList) {
        if (this.mTraceManager.isA11yTracingEnabledForTypes(8L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.sendStateToClients", 8L, VibrationParam$1$$ExternalSyntheticOutline0.m(i, "clientState="));
        }
        remoteCallbackList.broadcastForEachCookie(FunctionalUtils.ignoreRemoteException(new FunctionalUtils.RemoteExceptionIgnoringConsumer() { // from class: com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticLambda51
            public final void acceptOrThrow(Object obj) {
                AccessibilityManagerService accessibilityManagerService = this;
                int i2 = i;
                accessibilityManagerService.getClass();
                AccessibilityManagerService.Client client = (AccessibilityManagerService.Client) obj;
                if (accessibilityManagerService.mProxyManager.isProxyedDeviceId(client.mDeviceId)) {
                    return;
                }
                client.mCallback.setState(i2);
            }
        }));
    }

    public final void setAccessibilityWindowAttributes(int i, int i2, int i3, AccessibilityWindowAttributes accessibilityWindowAttributes) {
        if (this.mTraceManager.isA11yTracingEnabledForTypes(4L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.setAccessibilityWindowAttributes", 4L);
        }
        AccessibilityWindowManager accessibilityWindowManager = this.mA11yWindowManager;
        synchronized (accessibilityWindowManager.mLock) {
            try {
                if (accessibilityWindowManager.getWindowTokenForUserAndWindowIdLocked(accessibilityWindowManager.mSecurityPolicy.resolveCallingUserIdEnforcingPermissionsLocked(i3), i2) == null) {
                    return;
                }
                accessibilityWindowManager.mWindowAttributes.put(i2, accessibilityWindowAttributes);
                int resolveParentWindowIdLocked = accessibilityWindowManager.resolveParentWindowIdLocked(i2);
                AccessibilityWindowManager.DisplayWindowsObserver displayWindowObserverByWindowIdLocked = accessibilityWindowManager.getDisplayWindowObserverByWindowIdLocked(resolveParentWindowIdLocked);
                boolean z = (displayWindowObserverByWindowIdLocked != null ? (WindowInfo) displayWindowObserverByWindowIdLocked.mWindowInfoById.get(resolveParentWindowIdLocked) : null) != null;
                if (z) {
                    accessibilityWindowManager.mWindowManagerInternal.computeWindowsForAccessibility(i);
                }
            } finally {
            }
        }
    }

    public final void setMagnificationConnection(IMagnificationConnection iMagnificationConnection) {
        setMagnificationConnection_enforcePermission();
        if (this.mTraceManager.isA11yTracingEnabledForTypes(132L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.setMagnificationConnection", 132L, "connection=" + iMagnificationConnection);
        }
        getMagnificationConnectionManager().setConnection(iMagnificationConnection);
        if (com.android.window.flags.Flags.alwaysDrawMagnificationFullscreenBorder() && iMagnificationConnection == null && this.mMagnificationController.isFullScreenMagnificationControllerInitialized()) {
            ArrayList validDisplayList = getValidDisplayList();
            for (int i = 0; i < validDisplayList.size(); i++) {
                Display display = (Display) validDisplayList.get(i);
                FullScreenMagnificationController fullScreenMagnificationController = this.mMagnificationController.getFullScreenMagnificationController();
                int displayId = display.getDisplayId();
                fullScreenMagnificationController.getClass();
                fullScreenMagnificationController.reset(displayId, (MagnificationAnimationCallback) null);
            }
        }
    }

    public final void setMagnificationDisactivate() {
        AccessibilityInputFilter accessibilityInputFilter;
        MagnificationGestureHandler magnificationGestureHandler;
        setMagnificationDisactivate_enforcePermission();
        Slog.d("AccessibilityManagerService", "setMagnificationDisactivate");
        synchronized (this.mLock) {
            try {
                if (this.mHasInputFilter && (accessibilityInputFilter = this.mInputFilter) != null) {
                    int displayId = getDisplayId();
                    if (accessibilityInputFilter.mMagnificationGestureHandler.size() != 0 && (magnificationGestureHandler = (MagnificationGestureHandler) accessibilityInputFilter.mMagnificationGestureHandler.get(displayId)) != null) {
                        magnificationGestureHandler.magnificationDisactivate$1();
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setNonA11yToolNotificationToMatchSafetyCenter() {
        boolean z = !((SafetyCenterManager) this.mContext.getSystemService(SafetyCenterManager.class)).isSafetyCenterEnabled();
        synchronized (this.mLock) {
            this.mSecurityPolicy.setSendingNonA11yToolNotificationLocked(z);
        }
    }

    public void setPackageMonitor(PackageMonitor packageMonitor) {
        this.mPackageMonitor = packageMonitor;
    }

    public final void setPictureInPictureActionReplacingConnection(IAccessibilityInteractionConnection iAccessibilityInteractionConnection) {
        setPictureInPictureActionReplacingConnection_enforcePermission();
        if (this.mTraceManager.isA11yTracingEnabledForTypes(4L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.setPictureInPictureActionReplacingConnection", 4L, "connection=" + iAccessibilityInteractionConnection);
        }
        AccessibilityWindowManager accessibilityWindowManager = this.mA11yWindowManager;
        synchronized (accessibilityWindowManager.mLock) {
            try {
                AccessibilityWindowManager.RemoteAccessibilityConnection remoteAccessibilityConnection = accessibilityWindowManager.mPictureInPictureActionReplacingConnection;
                if (remoteAccessibilityConnection != null) {
                    remoteAccessibilityConnection.mConnection.asBinder().unlinkToDeath(remoteAccessibilityConnection, 0);
                    accessibilityWindowManager.mPictureInPictureActionReplacingConnection = null;
                }
                if (iAccessibilityInteractionConnection != null) {
                    AccessibilityWindowManager.RemoteAccessibilityConnection remoteAccessibilityConnection2 = accessibilityWindowManager.new RemoteAccessibilityConnection(-3, iAccessibilityInteractionConnection, "foo.bar.baz", 1000, -1);
                    accessibilityWindowManager.mPictureInPictureActionReplacingConnection = remoteAccessibilityConnection2;
                    iAccessibilityInteractionConnection.asBinder().linkToDeath(remoteAccessibilityConnection2, 0);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void setScreenReaderEnabled(boolean z) {
        DeviceIdleController$$ExternalSyntheticOutline0.m("Called AccessibilityManagerService setScreenReaderEnabled enable : ", "AccessibilityManagerService", z);
        setScreenReaderEnabled_enforcePermission();
        try {
            this.mContext.getPackageManager().getApplicationInfo("com.samsung.android.accessibility.talkback", 128);
            if (z) {
                semTurnOnAccessibilityService(32);
            } else {
                semTurnOffAccessibilityService(32);
            }
        } catch (PackageManager.NameNotFoundException unused) {
            Slog.d("AccessibilityManagerService", "talkback package not installed");
        }
    }

    public final void setSystemAudioCaptioningEnabled(boolean z, int i) {
        setSystemAudioCaptioningEnabled_enforcePermission();
        this.mCaptioningManagerImpl.setSystemAudioCaptioningEnabled(z, i);
    }

    public final void setSystemAudioCaptioningUiEnabled(boolean z, int i) {
        setSystemAudioCaptioningUiEnabled_enforcePermission();
        this.mCaptioningManagerImpl.setSystemAudioCaptioningUiEnabled(z, i);
    }

    public final void setTalkbackMode() {
        Slog.d("AccessibilityManagerService", "Called AccessibilityManagerService setTalkbackMode()");
        new AccessibilityDirectAccessController(this.mContext).performAccessibilityDirectAccess();
    }

    public final void showAccessibilityTargetsSelection(int i, int i2) {
        Intent intent = new Intent("com.android.internal.intent.action.CHOOSE_ACCESSIBILITY_BUTTON");
        intent.setClassName("android", AccessibilitySamsungShortcutChooserActivity.class.getName());
        intent.putExtra("shortcutType", i2);
        intent.addFlags(805306368);
        AccessibilityUtils.setVisibilityShortcutDialog(true);
        this.mContext.startActivityAsUser(intent, ActivityOptions.makeBasic().setLaunchDisplayId(i).toBundle(), UserHandle.of(this.mCurrentUserId));
    }

    public final boolean startFlashNotificationEvent(String str, int i, String str2) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            FlashNotificationsController flashNotificationsController = this.mFlashNotificationsController;
            flashNotificationsController.requestStartFlashNotification(new FlashNotificationsController.FlashNotification(1, flashNotificationsController.getScreenFlashColorPreference$2(), flashNotificationsController.mContext, str, str2));
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return true;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v0, types: [android.os.IBinder$DeathRecipient, com.android.server.accessibility.FlashNotificationsController$$ExternalSyntheticLambda1] */
    public final boolean startFlashNotificationSequence(final String str, int i, IBinder iBinder) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            final FlashNotificationsController flashNotificationsController = this.mFlashNotificationsController;
            Context context = flashNotificationsController.mContext;
            int screenFlashColorPreference$2 = flashNotificationsController.getScreenFlashColorPreference$2();
            ?? r10 = new IBinder.DeathRecipient() { // from class: com.android.server.accessibility.FlashNotificationsController$$ExternalSyntheticLambda1
                @Override // android.os.IBinder.DeathRecipient
                public final void binderDied() {
                    FlashNotificationsController.this.stopFlashNotification(str);
                }
            };
            FlashNotificationsController.FlashNotification flashNotification = new FlashNotificationsController.FlashNotification(context, str, str, 2, screenFlashColorPreference$2, iBinder, r10);
            boolean z = false;
            if (iBinder != 0) {
                try {
                    iBinder.linkToDeath(r10, 0);
                    flashNotificationsController.requestStartFlashNotification(flashNotification);
                    z = true;
                } catch (RemoteException e) {
                    Log.e("FlashNotifController", "RemoteException", e);
                }
            }
            return z;
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final boolean stopFlashNotificationSequence(String str) {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            this.mFlashNotificationsController.stopFlashNotification(str);
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return true;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public void switchUser(int i) {
        FullScreenMagnificationController fullScreenMagnificationController;
        MagnificationConnectionManager magnificationConnectionManager;
        MagnificationController magnificationController = this.mMagnificationController;
        if (magnificationController.mUserId != i) {
            magnificationController.mUserId = i;
            synchronized (magnificationController.mLock) {
                fullScreenMagnificationController = magnificationController.mFullScreenMagnificationController;
                magnificationConnectionManager = magnificationController.mMagnificationConnectionManager;
                magnificationController.mAccessibilityCallbacksDelegateArray.clear();
                magnificationController.mCurrentMagnificationModeArray.clear();
                magnificationController.mLastMagnificationActivatedModeArray.clear();
                magnificationController.mIsImeVisibleArray.clear();
            }
            MagnificationScaleProvider magnificationScaleProvider = magnificationController.mScaleProvider;
            synchronized (magnificationScaleProvider.mLock) {
                magnificationScaleProvider.mCurrentUserId = i;
            }
            if (fullScreenMagnificationController != null) {
                fullScreenMagnificationController.resetAllIfNeeded(false);
            }
            if (magnificationConnectionManager != null) {
                magnificationConnectionManager.disableAllWindowMagnifiers();
            }
        }
        List parseAccessibilityServiceInfos = parseAccessibilityServiceInfos(i);
        List installedAccessibilityShortcutListAsUser = AccessibilityManager.getInstance(this.mContext).getInstalledAccessibilityShortcutListAsUser(this.mContext, i);
        synchronized (this.mLock) {
            try {
                int i2 = this.mCurrentUserId;
                if (i2 == i && this.mInitialized) {
                    return;
                }
                AccessibilityUserState userStateLocked = getUserStateLocked(i2);
                userStateLocked.onSwitchToAnotherUserLocked();
                if (userStateLocked.mUserClients.getRegisteredCallbackCount() > 0) {
                    this.mMainHandler.sendMessage(PooledLambda.obtainMessage(new AccessibilityManagerService$$ExternalSyntheticLambda12(7), this, 0, Integer.valueOf(userStateLocked.mUserId)));
                }
                boolean z = true;
                if (((UserManager) this.mContext.getSystemService("user")).getUsers().size() <= 1) {
                    z = false;
                }
                this.mCurrentUserId = i;
                AccessibilityUserState userStateLocked2 = getUserStateLocked(i);
                readConfigurationForUserStateLocked(userStateLocked2, parseAccessibilityServiceInfos, installedAccessibilityShortcutListAsUser);
                this.mSecurityPolicy.onSwitchUserLocked(this.mCurrentUserId, userStateLocked2.mEnabledServices);
                onUserStateChangedLocked(userStateLocked2, false);
                migrateAccessibilityButtonSettingsIfNecessaryLocked(userStateLocked2, null, 0);
                disableAccessibilityMenuToMigrateIfNeeded();
                if (z) {
                    this.mMainHandler.sendMessageDelayed(PooledLambda.obtainMessage(new AccessibilityManagerService$$ExternalSyntheticLambda38(0), this), 3000L);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void unbindImeLocked(AbstractAccessibilityServiceConnection abstractAccessibilityServiceConnection) {
        if (!(abstractAccessibilityServiceConnection instanceof AccessibilityServiceConnection) || (abstractAccessibilityServiceConnection instanceof ProxyAccessibilityServiceConnection)) {
            return;
        }
        this.mMainHandler.sendMessage(PooledLambda.obtainMessage(new AccessibilityManagerService$$ExternalSyntheticLambda9(6), this, (AccessibilityServiceConnection) abstractAccessibilityServiceConnection));
    }

    public final boolean unregisterProxyForDisplay(int i) {
        unregisterProxyForDisplay_enforcePermission();
        this.mSecurityPolicy.checkForAccessibilityPermissionOrRole();
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return this.mProxyManager.clearConnectionAndUpdateState(i);
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void unregisterSystemAction(int i) {
        unregisterSystemAction_enforcePermission();
        if (this.mTraceManager.isA11yTracingEnabledForTypes(4L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.unregisterSystemAction", 4L, VibrationParam$1$$ExternalSyntheticOutline0.m(i, "actionId="));
        }
        getSystemActionPerformer().unregisterSystemAction(i);
    }

    public final void unregisterUiTestAutomationService(IAccessibilityServiceClient iAccessibilityServiceClient) {
        if (this.mTraceManager.isA11yTracingEnabledForTypes(4L)) {
            this.mTraceManager.logTrace("AccessibilityManagerService.unregisterUiTestAutomationService", 4L, "serviceClient=" + iAccessibilityServiceClient);
        }
        synchronized (this.mLock) {
            this.mUiAutomationManager.unregisterUiTestAutomationServiceLocked(iAccessibilityServiceClient);
            restoreCurrentUserAfterTestAutomationIfNeededLocked();
        }
    }

    public boolean unsafeIsLockHeld() {
        return Thread.holdsLock(this.mLock);
    }

    public final void updateColorLensValue() {
        int intForUser = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "color_lens_type", 0, -2);
        int intForUser2 = Settings.Secure.getIntForUser(this.mContext.getContentResolver(), "color_lens_opacity", 2, -2);
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(intForUser, intForUser2, "updateColorLensValue colorLensType : ", " colorLensOpacity : ", "AccessibilityManagerService");
        int[] intArray = this.mContext.getResources().getIntArray(R.array.config_defaultNotificationVibeWaveform);
        if (this.mColorLensView != null) {
            this.mColorLensView.setBackgroundColor(ColorUtils.setAlphaComponent(intArray[intForUser], ((new int[][]{new int[]{2, 3, 5, 6, 8, 9, 11, 12, 13}, new int[]{1, 3, 4, 6, 7, 8, 9, 11, 12}, new int[]{1, 3, 4, 5, 7, 8, 9, 10, 11}, new int[]{2, 3, 5, 6, 7, 8, 9, 11, 12}, new int[]{2, 3, 4, 5, 7, 8, 9, 10, 12}, new int[]{2, 3, 4, 6, 7, 8, 10, 11, 12}, new int[]{2, 3, 4, 6, 7, 8, 9, 10, 11}, new int[]{2, 4, 5, 7, 8, 9, 10, 11, 12}, new int[]{2, 4, 5, 6, 7, 9, 10, 11, 12}, new int[]{2, 3, 4, 6, 7, 9, 10, 11, 12}, new int[]{1, 3, 4, 5, 6, 7, 9, 10, 11}, new int[]{1, 2, 4, 5, 7, 8, 9, 10, 12}}[intForUser][intForUser2] * 5) + 20) * 2));
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:46:0x0091, code lost:
    
        if (r9.mIsAMEnabled == false) goto L58;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateMagnificationCapabilitiesSettingsChangeLocked(com.android.server.accessibility.AccessibilityUserState r9) {
        /*
            r8 = this;
            java.util.ArrayList r0 = r8.getValidDisplayList()
            r1 = 0
            r2 = r1
        L6:
            int r3 = r0.size()
            if (r2 >= r3) goto L22
            java.lang.Object r3 = r0.get(r2)
            android.view.Display r3 = (android.view.Display) r3
            int r3 = r3.getDisplayId()
            boolean r4 = r8.fallBackMagnificationModeSettingsLocked(r9, r3)
            if (r4 == 0) goto L1f
            r8.updateMagnificationModeChangeSettingsLocked(r9, r3)
        L1f:
            int r2 = r2 + 1
            goto L6
        L22:
            com.android.server.accessibility.magnification.MagnificationController r2 = r8.mMagnificationController
            boolean r2 = r2.mSupportWindowMagnification
            if (r2 != 0) goto L29
            goto L82
        L29:
            boolean r2 = r9.isShortcutMagnificationEnabledLocked()
            r3 = 1
            if (r2 != 0) goto L3d
            boolean r2 = r9.mIsMagnificationSingleFingerTripleTapEnabled
            if (r2 != 0) goto L3d
            boolean r2 = r9.mIsAMEnabled
            if (r2 != 0) goto L3d
            com.android.server.accessibility.Flags.enableMagnificationMultipleFingerMultipleTapGesture()
            r2 = r1
            goto L3e
        L3d:
            r2 = r3
        L3e:
            boolean r4 = com.android.window.flags.Flags.alwaysDrawMagnificationFullscreenBorder()
            if (r4 != 0) goto L4b
            int r4 = r9.mMagnificationCapabilities
            if (r4 == r3) goto L49
            goto L4b
        L49:
            r4 = r1
            goto L4c
        L4b:
            r4 = r3
        L4c:
            if (r2 == 0) goto L50
            if (r4 != 0) goto L6f
        L50:
            java.util.ArrayList r2 = r9.mBoundServices
            int r4 = r2.size()
            r5 = r1
        L57:
            if (r5 >= r4) goto L6e
            java.lang.Object r6 = r2.get(r5)
            com.android.server.accessibility.AccessibilityServiceConnection r6 = (com.android.server.accessibility.AccessibilityServiceConnection) r6
            com.android.server.accessibility.AccessibilitySecurityPolicy r7 = r8.mSecurityPolicy
            r7.getClass()
            boolean r6 = com.android.server.accessibility.AccessibilitySecurityPolicy.canControlMagnification(r6)
            if (r6 == 0) goto L6b
            goto L6f
        L6b:
            int r5 = r5 + 1
            goto L57
        L6e:
            r3 = r1
        L6f:
            boolean r2 = com.android.server.accessibility.AccessibilityManagerService.SEC_DEBUG
            if (r2 == 0) goto L7b
            java.lang.String r2 = "updateWindowMagnificationConnectionIfNeeded connect : "
            java.lang.String r4 = "AccessibilityManagerService"
            com.android.server.DeviceIdleController$$ExternalSyntheticOutline0.m(r2, r4, r3)
        L7b:
            com.android.server.accessibility.magnification.MagnificationConnectionManager r2 = r8.getMagnificationConnectionManager()
            r2.requestConnection(r3)
        L82:
            boolean r2 = r9.mIsMagnificationSingleFingerTripleTapEnabled
            if (r2 != 0) goto L93
            com.android.server.accessibility.Flags.enableMagnificationMultipleFingerMultipleTapGesture()
            boolean r2 = r9.isShortcutMagnificationEnabledLocked()
            if (r2 != 0) goto L93
            boolean r2 = r9.mIsAMEnabled
            if (r2 == 0) goto L98
        L93:
            int r9 = r9.mMagnificationCapabilities
            r2 = 3
            if (r9 == r2) goto Lb2
        L98:
            int r9 = r0.size()
            if (r1 >= r9) goto Lb2
            java.lang.Object r9 = r0.get(r1)
            android.view.Display r9 = (android.view.Display) r9
            int r9 = r9.getDisplayId()
            com.android.server.accessibility.magnification.MagnificationConnectionManager r2 = r8.getMagnificationConnectionManager()
            r2.removeMagnificationButton(r9)
            int r1 = r1 + 1
            goto L98
        Lb2:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.accessibility.AccessibilityManagerService.updateMagnificationCapabilitiesSettingsChangeLocked(com.android.server.accessibility.AccessibilityUserState):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:46:0x0094, code lost:
    
        if (r9.mMagnificationController.isFullScreenMagnificationControllerInitialized() == false) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x0096, code lost:
    
        r4 = r9.mMagnificationController.getFullScreenMagnificationController();
        r5 = r4.mLock;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x009e, code lost:
    
        monitor-enter(r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x009f, code lost:
    
        r4.unregisterLocked(r3, false);
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x00a2, code lost:
    
        monitor-exit(r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x00a7, code lost:
    
        continue;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateMagnificationLocked(com.android.server.accessibility.AccessibilityUserState r10) {
        /*
            r9 = this;
            int r0 = r10.mUserId
            int r1 = r9.mCurrentUserId
            if (r0 == r1) goto L7
            return
        L7:
            com.android.server.accessibility.UiAutomationManager r0 = r9.mUiAutomationManager
            boolean r0 = r0.suppressingAccessibilityServicesLocked()
            r1 = 0
            if (r0 == 0) goto L3e
            com.android.server.accessibility.magnification.MagnificationController r0 = r9.mMagnificationController
            boolean r0 = r0.isFullScreenMagnificationControllerInitialized()
            if (r0 == 0) goto L3e
            com.android.server.accessibility.magnification.MagnificationController r9 = r9.mMagnificationController
            com.android.server.accessibility.magnification.FullScreenMagnificationController r9 = r9.getFullScreenMagnificationController()
            java.lang.Object r0 = r9.mLock
            monitor-enter(r0)
            android.util.SparseArray r10 = r9.mDisplays     // Catch: java.lang.Throwable -> L38
            android.util.SparseArray r10 = r10.clone()     // Catch: java.lang.Throwable -> L38
            r2 = r1
        L28:
            int r3 = r10.size()     // Catch: java.lang.Throwable -> L38
            if (r2 >= r3) goto L3a
            int r3 = r10.keyAt(r2)     // Catch: java.lang.Throwable -> L38
            r9.unregisterLocked(r3, r1)     // Catch: java.lang.Throwable -> L38
            int r2 = r2 + 1
            goto L28
        L38:
            r9 = move-exception
            goto L3c
        L3a:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L38
            return
        L3c:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L38
            throw r9
        L3e:
            java.util.ArrayList r0 = r9.getValidDisplayList()
            boolean r2 = r10.mIsMagnificationSingleFingerTripleTapEnabled
            if (r2 != 0) goto Lab
            com.android.server.accessibility.Flags.enableMagnificationMultipleFingerMultipleTapGesture()
            boolean r2 = r10.isShortcutMagnificationEnabledLocked()
            if (r2 == 0) goto L50
            goto Lab
        L50:
            r2 = r1
        L51:
            int r3 = r0.size()
            if (r2 >= r3) goto Laa
            java.lang.Object r3 = r0.get(r2)
            android.view.Display r3 = (android.view.Display) r3
            int r3 = r3.getDisplayId()
            java.util.ArrayList r4 = r10.mBoundServices
            int r5 = r4.size()
            r6 = r1
        L68:
            if (r6 >= r5) goto L8e
            java.lang.Object r7 = r4.get(r6)
            com.android.server.accessibility.AccessibilityServiceConnection r7 = (com.android.server.accessibility.AccessibilityServiceConnection) r7
            com.android.server.accessibility.AccessibilitySecurityPolicy r8 = r9.mSecurityPolicy
            r8.getClass()
            boolean r8 = com.android.server.accessibility.AccessibilitySecurityPolicy.canControlMagnification(r7)
            if (r8 == 0) goto L8b
            boolean r7 = r7.isMagnificationCallbackEnabled(r3)
            if (r7 == 0) goto L8b
            com.android.server.accessibility.magnification.MagnificationController r4 = r9.mMagnificationController
            com.android.server.accessibility.magnification.FullScreenMagnificationController r4 = r4.getFullScreenMagnificationController()
            r4.register(r3)
            goto La7
        L8b:
            int r6 = r6 + 1
            goto L68
        L8e:
            com.android.server.accessibility.magnification.MagnificationController r4 = r9.mMagnificationController
            boolean r4 = r4.isFullScreenMagnificationControllerInitialized()
            if (r4 == 0) goto La7
            com.android.server.accessibility.magnification.MagnificationController r4 = r9.mMagnificationController
            com.android.server.accessibility.magnification.FullScreenMagnificationController r4 = r4.getFullScreenMagnificationController()
            java.lang.Object r5 = r4.mLock
            monitor-enter(r5)
            r4.unregisterLocked(r3, r1)     // Catch: java.lang.Throwable -> La4
            monitor-exit(r5)     // Catch: java.lang.Throwable -> La4
            goto La7
        La4:
            r9 = move-exception
            monitor-exit(r5)     // Catch: java.lang.Throwable -> La4
            throw r9
        La7:
            int r2 = r2 + 1
            goto L51
        Laa:
            return
        Lab:
            int r10 = r0.size()
            if (r1 >= r10) goto Lc7
            java.lang.Object r10 = r0.get(r1)
            android.view.Display r10 = (android.view.Display) r10
            com.android.server.accessibility.magnification.MagnificationController r2 = r9.mMagnificationController
            com.android.server.accessibility.magnification.FullScreenMagnificationController r2 = r2.getFullScreenMagnificationController()
            int r10 = r10.getDisplayId()
            r2.register(r10)
            int r1 = r1 + 1
            goto Lab
        Lc7:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.accessibility.AccessibilityManagerService.updateMagnificationLocked(com.android.server.accessibility.AccessibilityUserState):void");
    }

    public final void updateMagnificationModeChangeSettingsLocked(AccessibilityUserState accessibilityUserState, int i) {
        if (accessibilityUserState.mUserId == this.mCurrentUserId && !fallBackMagnificationModeSettingsLocked(accessibilityUserState, i)) {
            MagnificationController magnificationController = this.mMagnificationController;
            int magnificationModeLocked = accessibilityUserState.getMagnificationModeLocked(i);
            AccessibilityManagerService$$ExternalSyntheticLambda27 accessibilityManagerService$$ExternalSyntheticLambda27 = new AccessibilityManagerService$$ExternalSyntheticLambda27(this);
            if (magnificationController.isActivated(i, magnificationModeLocked)) {
                accessibilityManagerService$$ExternalSyntheticLambda27.onResult(i, true);
                return;
            }
            PointF currentMagnificationCenterLocked = magnificationController.getCurrentMagnificationCenterLocked(i, magnificationModeLocked);
            MagnificationController.DisableMagnificationCallback disableMagnificationCallback = (MagnificationController.DisableMagnificationCallback) magnificationController.mMagnificationEndRunnableSparseArray.get(i);
            if (currentMagnificationCenterLocked == null && disableMagnificationCallback == null) {
                accessibilityManagerService$$ExternalSyntheticLambda27.onResult(i, true);
                return;
            }
            if (disableMagnificationCallback == null) {
                if (currentMagnificationCenterLocked == null) {
                    Slog.w("MagnificationController", "Invalid center, ignore it");
                    accessibilityManagerService$$ExternalSyntheticLambda27.onResult(i, true);
                    return;
                }
                magnificationController.setTransitionState(Integer.valueOf(i), Integer.valueOf(magnificationModeLocked));
                FullScreenMagnificationController fullScreenMagnificationController = magnificationController.getFullScreenMagnificationController();
                MagnificationConnectionManager magnificationConnectionManager = magnificationController.getMagnificationConnectionManager();
                MagnificationController.DisableMagnificationCallback disableMagnificationCallback2 = magnificationController.new DisableMagnificationCallback(accessibilityManagerService$$ExternalSyntheticLambda27, i, magnificationModeLocked, magnificationModeLocked == 2 ? magnificationController.getFullScreenMagnificationController().getScale(i) : magnificationController.getMagnificationConnectionManager().getScale(i), currentMagnificationCenterLocked);
                magnificationController.setDisableMagnificationCallbackLocked(i, disableMagnificationCallback2);
                if (magnificationModeLocked == 2) {
                    fullScreenMagnificationController.reset(i, disableMagnificationCallback2);
                    return;
                } else {
                    magnificationConnectionManager.disableWindowMagnification(i, false, disableMagnificationCallback2);
                    return;
                }
            }
            if (disableMagnificationCallback.mCurrentMode != magnificationModeLocked) {
                Slog.w("MagnificationController", "discard duplicate request");
                return;
            }
            synchronized (MagnificationController.this.mLock) {
                try {
                    if (disableMagnificationCallback.mExpired) {
                        return;
                    }
                    disableMagnificationCallback.mExpired = true;
                    MagnificationController.this.setDisableMagnificationCallbackLocked(disableMagnificationCallback.mDisplayId, null);
                    MagnificationController.this.setTransitionState(Integer.valueOf(disableMagnificationCallback.mDisplayId), null);
                    disableMagnificationCallback.applyMagnificationModeLocked(disableMagnificationCallback.mCurrentMode);
                    MagnificationController.this.updateMagnificationUIControls(disableMagnificationCallback.mDisplayId, disableMagnificationCallback.mCurrentMode);
                    AccessibilityManagerService$$ExternalSyntheticLambda27 accessibilityManagerService$$ExternalSyntheticLambda272 = disableMagnificationCallback.mTransitionCallBack;
                    if (accessibilityManagerService$$ExternalSyntheticLambda272 != null) {
                        accessibilityManagerService$$ExternalSyntheticLambda272.onResult(disableMagnificationCallback.mDisplayId, true);
                    }
                } finally {
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:22:0x0075  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateWindowsForAccessibilityCallbackLocked(com.android.server.accessibility.AccessibilityUserState r9) {
        /*
            r8 = this;
            com.android.server.accessibility.UiAutomationManager r0 = r8.mUiAutomationManager
            com.android.server.accessibility.UiAutomationManager$UiAutomationService r0 = r0.mUiAutomationService
            r1 = 1
            r2 = 0
            if (r0 == 0) goto Ld
            boolean r0 = r0.mRetrieveInteractiveWindows
            if (r0 == 0) goto Ld
            goto L43
        Ld:
            com.android.server.accessibility.ProxyManager r0 = r8.mProxyManager
            r3 = r2
        L10:
            android.util.SparseArray r4 = r0.mProxyA11yServiceConnections
            int r4 = r4.size()
            if (r3 >= r4) goto L29
            android.util.SparseArray r4 = r0.mProxyA11yServiceConnections
            java.lang.Object r4 = r4.valueAt(r3)
            com.android.server.accessibility.ProxyAccessibilityServiceConnection r4 = (com.android.server.accessibility.ProxyAccessibilityServiceConnection) r4
            boolean r4 = r4.mRetrieveInteractiveWindows
            if (r4 == 0) goto L26
            r0 = r1
            goto L2a
        L26:
            int r3 = r3 + 1
            goto L10
        L29:
            r0 = r2
        L2a:
            boolean r3 = com.android.server.accessibility.ProxyManager.DEBUG
            if (r3 == 0) goto L41
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "At least one proxy can retrieve windows: "
            r3.<init>(r4)
            r3.append(r0)
            java.lang.String r3 = r3.toString()
            java.lang.String r4 = "ProxyManager"
            android.util.Slog.v(r4, r3)
        L41:
            if (r0 == 0) goto L45
        L43:
            r0 = r1
            goto L46
        L45:
            r0 = r2
        L46:
            java.util.ArrayList r9 = r9.mBoundServices
            int r3 = r9.size()
            r4 = r2
        L4d:
            if (r0 != 0) goto L6b
            if (r4 >= r3) goto L6b
            java.lang.Object r5 = r9.get(r4)
            com.android.server.accessibility.AccessibilityServiceConnection r5 = (com.android.server.accessibility.AccessibilityServiceConnection) r5
            com.android.server.accessibility.AccessibilitySecurityPolicy r6 = r5.mSecurityPolicy
            r6.getClass()
            int r6 = r5.getCapabilities()
            r6 = r6 & r1
            if (r6 == 0) goto L68
            boolean r5 = r5.mRetrieveInteractiveWindows
            if (r5 == 0) goto L68
            r0 = r1
        L68:
            int r4 = r4 + 1
            goto L4d
        L6b:
            java.util.ArrayList r9 = r8.getValidDisplayList()
        L6f:
            int r3 = r9.size()
            if (r2 >= r3) goto Lcc
            java.lang.Object r3 = r9.get(r2)
            android.view.Display r3 = (android.view.Display) r3
            if (r3 == 0) goto Lc9
            if (r0 == 0) goto Lc0
            com.android.server.accessibility.AccessibilityWindowManager r4 = r8.mA11yWindowManager
            int r5 = r3.getDisplayId()
            com.android.server.accessibility.ProxyManager r6 = r8.mProxyManager
            int r3 = r3.getDisplayId()
            boolean r3 = r6.isProxyedDisplay(r3)
            java.lang.Object r6 = r4.mLock
            monitor-enter(r6)
            android.util.SparseArray r7 = r4.mDisplayWindowsObservers     // Catch: java.lang.Throwable -> La2
            java.lang.Object r7 = r7.get(r5)     // Catch: java.lang.Throwable -> La2
            com.android.server.accessibility.AccessibilityWindowManager$DisplayWindowsObserver r7 = (com.android.server.accessibility.AccessibilityWindowManager.DisplayWindowsObserver) r7     // Catch: java.lang.Throwable -> La2
            if (r7 != 0) goto La4
            com.android.server.accessibility.AccessibilityWindowManager$DisplayWindowsObserver r7 = new com.android.server.accessibility.AccessibilityWindowManager$DisplayWindowsObserver     // Catch: java.lang.Throwable -> La2
            r7.<init>(r5)     // Catch: java.lang.Throwable -> La2
            goto La4
        La2:
            r8 = move-exception
            goto Lbe
        La4:
            if (r3 == 0) goto Lae
            boolean r3 = r7.mIsProxy     // Catch: java.lang.Throwable -> La2
            if (r3 != 0) goto Lae
            r7.mIsProxy = r1     // Catch: java.lang.Throwable -> La2
            r4.mHasProxy = r1     // Catch: java.lang.Throwable -> La2
        Lae:
            boolean r3 = r7.mTrackingWindows     // Catch: java.lang.Throwable -> La2
            if (r3 == 0) goto Lb4
            monitor-exit(r6)     // Catch: java.lang.Throwable -> La2
            goto Lc9
        Lb4:
            r7.startTrackingWindowsLocked()     // Catch: java.lang.Throwable -> La2
            android.util.SparseArray r3 = r4.mDisplayWindowsObservers     // Catch: java.lang.Throwable -> La2
            r3.put(r5, r7)     // Catch: java.lang.Throwable -> La2
            monitor-exit(r6)     // Catch: java.lang.Throwable -> La2
            goto Lc9
        Lbe:
            monitor-exit(r6)     // Catch: java.lang.Throwable -> La2
            throw r8
        Lc0:
            com.android.server.accessibility.AccessibilityWindowManager r4 = r8.mA11yWindowManager
            int r3 = r3.getDisplayId()
            r4.stopTrackingWindows(r3)
        Lc9:
            int r2 = r2 + 1
            goto L6f
        Lcc:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.accessibility.AccessibilityManagerService.updateWindowsForAccessibilityCallbackLocked(com.android.server.accessibility.AccessibilityUserState):void");
    }
}
