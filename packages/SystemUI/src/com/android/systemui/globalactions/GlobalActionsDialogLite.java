package com.android.systemui.globalactions;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.app.ActivityManager;
import android.app.Dialog;
import android.app.IActivityManager;
import android.app.trust.TrustManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.content.pm.UserInfo;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.graphics.drawable.Drawable;
import android.media.AudioManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.os.Trace;
import android.os.UserHandle;
import android.os.UserManager;
import android.provider.Settings;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.sysprop.TelephonyProperties;
import android.telecom.TelecomManager;
import android.telephony.ServiceState;
import android.telephony.TelephonyCallback;
import android.util.ArraySet;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.GestureDetector;
import android.view.IWindowManager;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import androidx.compose.foundation.text.input.internal.RecordingInputConnection$$ExternalSyntheticOutline0;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LifecycleRegistry;
import androidx.lifecycle.Observer;
import com.android.app.animation.Interpolators;
import com.android.internal.colorextraction.ColorExtractor;
import com.android.internal.logging.MetricsLogger;
import com.android.internal.logging.UiEventLogger;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.util.EmergencyAffordanceManager;
import com.android.internal.util.ScreenshotHelper;
import com.android.internal.widget.LockPatternUtils;
import com.android.keyguard.ClockEventController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.MultiListLayout;
import com.android.systemui.R;
import com.android.systemui.animation.DialogCuj;
import com.android.systemui.animation.DialogTransitionAnimator;
import com.android.systemui.animation.Expandable;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.colorextraction.SysuiColorExtractor;
import com.android.systemui.display.data.repository.DisplayWindowPropertiesRepository;
import com.android.systemui.display.data.repository.DisplayWindowPropertiesRepositoryImpl;
import com.android.systemui.globalactions.GlobalActionsDialogLite;
import com.android.systemui.globalactions.data.repository.GlobalActionsRepository;
import com.android.systemui.globalactions.domain.interactor.GlobalActionsInteractor;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.plugins.GlobalActions;
import com.android.systemui.plugins.GlobalActionsPanelPlugin;
import com.android.systemui.plugins.subscreen.SubRoom;
import com.android.systemui.popup.util.PopupUIUtil;
import com.android.systemui.scrim.ScrimDrawable;
import com.android.systemui.settings.UserTracker;
import com.android.systemui.settings.UserTrackerImpl;
import com.android.systemui.shade.BaseShadeControllerImpl;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.shade.shared.flag.ShadeWindowGoesAround;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.VibratorHelper;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.LightBarController;
import com.android.systemui.statusbar.phone.LightBarControllerImpl;
import com.android.systemui.statusbar.phone.SystemUIDialog;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.window.StatusBarWindowController;
import com.android.systemui.statusbar.window.StatusBarWindowControllerImpl;
import com.android.systemui.statusbar.window.StatusBarWindowControllerStore;
import com.android.systemui.telephony.TelephonyListenerManager;
import com.android.systemui.user.domain.interactor.SelectedUserInteractor;
import com.android.systemui.user.domain.interactor.UserLogoutInteractor;
import com.android.systemui.util.EmergencyDialerConstants;
import com.android.systemui.util.RingerModeTracker;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.settings.GlobalSettings;
import com.android.systemui.util.settings.SecureSettings;
import com.samsung.android.knox.custom.IKnoxCustomManager;
import com.samsung.android.knox.net.vpn.VpnErrorValues;
import com.samsung.systemui.splugins.volume.VolumePanelValues;
import com.sec.ims.settings.ImsProfile;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.function.Consumer;

/* loaded from: classes2.dex */
public class GlobalActionsDialogLite implements DialogInterface.OnDismissListener, DialogInterface.OnShowListener, ConfigurationController.ConfigurationListener, GlobalActionsPanelPlugin.Callbacks, LifecycleOwner {
    static final String GLOBAL_ACTION_KEY_POWER = "power";
    public final ActivityStarter mActivityStarter;
    public MyAdapter mAdapter;
    public final AnonymousClass9 mAirplaneModeObserver;
    public AirplaneModeAction mAirplaneModeOn;
    public final AudioManager mAudioManager;
    public final Executor mBackgroundExecutor;
    public final BroadcastDispatcher mBroadcastDispatcher;
    public final AnonymousClass7 mBroadcastReceiver;
    public final ConfigurationController mConfigurationController;
    public final Context mContext;
    protected ActionsDialogLite mDialog;
    public final DialogTransitionAnimator mDialogTransitionAnimator;
    public final Lazy mDisplayWindowPropertiesRepositoryLazy;
    public final EmergencyAffordanceManager mEmergencyAffordanceManager;
    public final GlobalSettings mGlobalSettings;
    public final AnonymousClass2 mHandler;
    public final boolean mHasTelephony;
    public final boolean mHasVibrator;
    public final IActivityManager mIActivityManager;
    public final IWindowManager mIWindowManager;
    public final GlobalActionsInteractor mInteractor;
    public final boolean mIsTv;
    public final KeyguardStateController mKeyguardStateController;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final LightBarController mLightBarController;
    public final LockPatternUtils mLockPatternUtils;
    public final UserLogoutInteractor mLogoutInteractor;
    public final Handler mMainHandler;
    public final MetricsLogger mMetricsLogger;
    public final NotificationShadeWindowController mNotificationShadeWindowController;
    public int mOrientation;
    public MyOverflowAdapter mOverflowAdapter;
    public final AnonymousClass8 mPhoneStateListener;
    public MyPowerOptionsAdapter mPowerAdapter;
    public final PowerManager mPowerManager;
    public final Resources mResources;
    public final RingerModeTracker mRingerModeTracker;
    public final ScreenshotHelper mScreenshotHelper;
    public final SecureSettings mSecureSettings;
    public final SelectedUserInteractor mSelectedUserInteractor;
    public final ShadeController mShadeController;
    public final boolean mShowSilentToggle;
    public Action mSilentModeAction;
    public int mSmallestScreenWidthDp;
    public final IStatusBarService mStatusBarService;
    public final StatusBarWindowControllerStore mStatusBarWindowControllerStore;
    public final SysuiColorExtractor mSysuiColorExtractor;
    public final TelecomManager mTelecomManager;
    public final TelephonyListenerManager mTelephonyListenerManager;
    public final TrustManager mTrustManager;
    public final UiEventLogger mUiEventLogger;
    public final UserManager mUserManager;
    public final UserTracker mUserTracker;
    public final GlobalActions.GlobalActionsManager mWindowManagerFuncs;
    public final LifecycleRegistry mLifecycle = new LifecycleRegistry(this);
    protected final ArrayList<Action> mItems = new ArrayList<>();
    protected final ArrayList<Action> mOverflowItems = new ArrayList<>();
    protected final ArrayList<Action> mPowerItems = new ArrayList<>();
    public boolean mDeviceProvisioned = false;
    public ToggleState mAirplaneState = ToggleState.Off;
    public boolean mIsWaitingForEcmExit = false;
    public int mDialogPressDelay = 850;
    public final UserTracker.Callback mOnUserSwitched = new UserTracker.Callback() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite.1
        @Override // com.android.systemui.settings.UserTracker.Callback
        public final void onBeforeUserSwitching(int i) {
            GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
            globalActionsDialogLite.mHandler.removeMessages(0);
            globalActionsDialogLite.mHandler.sendEmptyMessage(0);
        }
    };

    public interface Action {
        View create(Context context, View view, ViewGroup viewGroup, LayoutInflater layoutInflater);

        Drawable getIcon(Context context);

        CharSequence getMessage();

        int getMessageResId();

        boolean isEnabled();

        void onPress();

        default boolean shouldShow() {
            return true;
        }

        boolean showBeforeProvisioning();
    }

    class ActionsDialogLite extends SystemUIDialog implements DialogInterface, ColorExtractor.OnColorsChangedListener {
        public static final /* synthetic */ int $r8$clinit = 0;
        public final MyAdapter mAdapter;
        public ScrimDrawable mBackgroundDrawable;
        public final SysuiColorExtractor mColorExtractor;
        public ViewGroup mContainer;
        public final Context mContext;
        public final GestureDetector mGestureDetector;
        protected GestureDetector.SimpleOnGestureListener mGestureListener;
        public MultiListLayout mGlobalActionsLayout;
        public final boolean mKeyguardShowing;
        public final KeyguardStateController mKeyguardStateController;
        public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
        public final LightBarController mLightBarController;
        public final LockPatternUtils mLockPatternUtils;
        public final NotificationShadeWindowController mNotificationShadeWindowController;
        public final GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda3 mOnBackInvokedCallback;
        public final Runnable mOnRefreshCallback;
        public final MyOverflowAdapter mOverflowAdapter;
        public GlobalActionsPopupMenu mOverflowPopup;
        public OnBackInvokedDispatcher mOverriddenBackDispatcher;
        public final MyPowerOptionsAdapter mPowerOptionsAdapter;
        public Dialog mPowerOptionsDialog;
        public final SelectedUserInteractor mSelectedUserInteractor;
        public final ShadeController mShadeController;
        public final StatusBarWindowController mStatusBarWindowController;
        public final UiEventLogger mUiEventLogger;
        public float mWindowDimAmount;

        /* renamed from: -$$Nest$mopenShadeAndDismiss, reason: not valid java name */
        public static void m2580$$Nest$mopenShadeAndDismiss(ActionsDialogLite actionsDialogLite) {
            actionsDialogLite.mUiEventLogger.log(GlobalActionsEvent.GA_CLOSE_TAP_OUTSIDE);
            if (((KeyguardStateControllerImpl) actionsDialogLite.mKeyguardStateController).mShowing) {
                ((BaseShadeControllerImpl) actionsDialogLite.mShadeController).animateExpandQs();
            } else {
                ((BaseShadeControllerImpl) actionsDialogLite.mShadeController).animateExpandShade();
            }
            actionsDialogLite.dismiss();
        }

        /* JADX WARN: Type inference failed for: r2v2, types: [com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda3] */
        public ActionsDialogLite(Context context, int i, MyAdapter myAdapter, MyOverflowAdapter myOverflowAdapter, SysuiColorExtractor sysuiColorExtractor, IStatusBarService iStatusBarService, LightBarController lightBarController, KeyguardStateController keyguardStateController, NotificationShadeWindowController notificationShadeWindowController, StatusBarWindowController statusBarWindowController, Runnable runnable, boolean z, MyPowerOptionsAdapter myPowerOptionsAdapter, UiEventLogger uiEventLogger, ShadeController shadeController, KeyguardUpdateMonitor keyguardUpdateMonitor, LockPatternUtils lockPatternUtils, SelectedUserInteractor selectedUserInteractor) {
            super(context, i, false);
            new Binder();
            this.mOnBackInvokedCallback = new OnBackInvokedCallback() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda3
                @Override // android.window.OnBackInvokedCallback
                public final void onBackInvoked() {
                    GlobalActionsDialogLite.ActionsDialogLite actionsDialogLite = this.f$0;
                    int i2 = GlobalActionsDialogLite.ActionsDialogLite.$r8$clinit;
                    actionsDialogLite.mUiEventLogger.log(GlobalActionsDialogLite.GlobalActionsEvent.GA_CLOSE_BACK);
                    actionsDialogLite.dismiss();
                }
            };
            this.mGestureListener = new GestureDetector.SimpleOnGestureListener() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite.ActionsDialogLite.1
                @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
                public final boolean onDown(MotionEvent motionEvent) {
                    return true;
                }

                @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
                public final boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                    if (f2 <= 0.0f || Math.abs(f2) <= Math.abs(f) || motionEvent == null) {
                        return false;
                    }
                    float y = motionEvent.getY();
                    ActionsDialogLite actionsDialogLite = ActionsDialogLite.this;
                    if (y > ((StatusBarWindowControllerImpl) actionsDialogLite.mStatusBarWindowController).mBarHeight) {
                        return false;
                    }
                    ActionsDialogLite.m2580$$Nest$mopenShadeAndDismiss(actionsDialogLite);
                    return true;
                }

                @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
                public final boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                    if (f2 >= 0.0f || f2 <= f || motionEvent == null) {
                        return false;
                    }
                    float y = motionEvent.getY();
                    ActionsDialogLite actionsDialogLite = ActionsDialogLite.this;
                    if (y > ((StatusBarWindowControllerImpl) actionsDialogLite.mStatusBarWindowController).mBarHeight) {
                        return false;
                    }
                    ActionsDialogLite.m2580$$Nest$mopenShadeAndDismiss(actionsDialogLite);
                    return true;
                }

                @Override // android.view.GestureDetector.SimpleOnGestureListener, android.view.GestureDetector.OnGestureListener
                public final boolean onSingleTapUp(MotionEvent motionEvent) {
                    ActionsDialogLite.this.mUiEventLogger.log(GlobalActionsEvent.GA_CLOSE_TAP_OUTSIDE);
                    ActionsDialogLite.this.cancel();
                    return false;
                }
            };
            this.mContext = context;
            this.mAdapter = myAdapter;
            this.mOverflowAdapter = myOverflowAdapter;
            this.mPowerOptionsAdapter = myPowerOptionsAdapter;
            this.mColorExtractor = sysuiColorExtractor;
            this.mLightBarController = lightBarController;
            this.mKeyguardStateController = keyguardStateController;
            this.mNotificationShadeWindowController = notificationShadeWindowController;
            this.mStatusBarWindowController = statusBarWindowController;
            this.mOnRefreshCallback = runnable;
            this.mKeyguardShowing = z;
            this.mUiEventLogger = uiEventLogger;
            this.mShadeController = shadeController;
            this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
            this.mLockPatternUtils = lockPatternUtils;
            this.mGestureDetector = new GestureDetector(context, this.mGestureListener);
            this.mSelectedUserInteractor = selectedUserInteractor;
        }

        @Override // android.app.Dialog, android.content.DialogInterface
        public final void dismiss() {
            GlobalActionsPopupMenu globalActionsPopupMenu = this.mOverflowPopup;
            if (globalActionsPopupMenu != null) {
                globalActionsPopupMenu.dismiss();
            }
            Dialog dialog = this.mPowerOptionsDialog;
            if (dialog != null) {
                dialog.dismiss();
            }
            ((NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController).setRequestTopUi("GlobalActionsDialogLite", false);
            super.dismiss();
        }

        @Override // com.android.systemui.statusbar.phone.SystemUIDialog
        public final int getHeight() {
            return -1;
        }

        @Override // android.app.Dialog
        public OnBackInvokedDispatcher getOnBackInvokedDispatcher() {
            OnBackInvokedDispatcher onBackInvokedDispatcher = this.mOverriddenBackDispatcher;
            return onBackInvokedDispatcher != null ? onBackInvokedDispatcher : super.getOnBackInvokedDispatcher();
        }

        @Override // com.android.systemui.statusbar.phone.SystemUIDialog
        public final int getWidth() {
            return -1;
        }

        @Override // android.app.Dialog
        public final void onBackPressed() {
            super.onBackPressed();
            this.mUiEventLogger.log(GlobalActionsEvent.GA_CLOSE_BACK);
        }

        public final void onColorsChanged(ColorExtractor colorExtractor, int i) {
            if (this.mKeyguardShowing) {
                if ((i & 2) != 0) {
                    updateColors(colorExtractor.getColors(2), true);
                }
            } else if ((i & 1) != 0) {
                updateColors(colorExtractor.getColors(1), true);
            }
        }

        @Override // com.android.systemui.statusbar.phone.SystemUIDialog, android.app.AlertDialog, android.app.Dialog
        public final void onCreate(Bundle bundle) {
            super.onCreate(bundle);
            getWindow().setTitle(getContext().getString(R.string.accessibility_quick_settings_power_menu));
            setContentView(R.layout.global_actions_grid_lite);
            ViewGroup viewGroup = (ViewGroup) findViewById(android.R.id.content);
            viewGroup.setClipChildren(false);
            viewGroup.setClipToPadding(false);
            ViewGroup viewGroup2 = (ViewGroup) viewGroup.getParent();
            viewGroup2.setClipChildren(false);
            viewGroup2.setClipToPadding(false);
            MultiListLayout multiListLayout = (MultiListLayout) findViewById(R.id.global_actions_view);
            this.mGlobalActionsLayout = multiListLayout;
            multiListLayout.getListView().setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite.ActionsDialogLite.2
                @Override // android.view.View.AccessibilityDelegate
                public final boolean dispatchPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
                    accessibilityEvent.getText().add(ActionsDialogLite.this.mContext.getString(android.R.string.miniresolver_switch_to_work));
                    return true;
                }
            });
            this.mGlobalActionsLayout.setImportantForAccessibility(2);
            MultiListLayout multiListLayout2 = this.mGlobalActionsLayout;
            multiListLayout2.mRotationListener = new GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda0(this);
            multiListLayout2.mAdapter = this.mAdapter;
            ViewGroup viewGroup3 = (ViewGroup) findViewById(R.id.global_actions_container);
            this.mContainer = viewGroup3;
            viewGroup3.setOnTouchListener(new View.OnTouchListener() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda1
                @Override // android.view.View.OnTouchListener
                public final boolean onTouch(View view, MotionEvent motionEvent) {
                    this.f$0.mGestureDetector.onTouchEvent(motionEvent);
                    return view.onTouchEvent(motionEvent);
                }
            });
            View viewFindViewById = findViewById(R.id.global_actions_overflow_button);
            if (viewFindViewById != null) {
                if (this.mOverflowAdapter.getCount() > 0) {
                    viewFindViewById.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda2
                        /* JADX WARN: Type inference failed for: r0v2, types: [com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda6] */
                        @Override // android.view.View.OnClickListener
                        public final void onClick(View view) {
                            final GlobalActionsDialogLite.ActionsDialogLite actionsDialogLite = this.f$0;
                            int i = GlobalActionsDialogLite.ActionsDialogLite.$r8$clinit;
                            GlobalActionsPopupMenu globalActionsPopupMenu = new GlobalActionsPopupMenu(new ContextThemeWrapper(actionsDialogLite.mContext, R.style.Control_ListPopupWindow), false);
                            globalActionsPopupMenu.setOnItemClickListener(new AdapterView.OnItemClickListener() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda5
                                @Override // android.widget.AdapterView.OnItemClickListener
                                public final void onItemClick(AdapterView adapterView, View view2, int i2, long j) {
                                    GlobalActionsDialogLite.MyOverflowAdapter myOverflowAdapter = actionsDialogLite.mOverflowAdapter;
                                    GlobalActionsDialogLite.Action action = GlobalActionsDialogLite.this.mOverflowItems.get(i2);
                                    if (action instanceof GlobalActionsDialogLite.SilentModeTriStateAction) {
                                        return;
                                    }
                                    GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
                                    if (globalActionsDialogLite.mDialog != null) {
                                        globalActionsDialogLite.mDialogTransitionAnimator.disableAllCurrentDialogsExitAnimations();
                                        GlobalActionsDialogLite.this.mDialog.dismiss();
                                    } else {
                                        Log.w("GlobalActionsDialogLite", "Action clicked while mDialog is null.");
                                    }
                                    action.onPress();
                                }
                            });
                            globalActionsPopupMenu.mOnItemLongClickListener = new AdapterView.OnItemLongClickListener() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda6
                                @Override // android.widget.AdapterView.OnItemLongClickListener
                                public final boolean onItemLongClick(AdapterView adapterView, View view2, int i2, long j) {
                                    GlobalActionsDialogLite.MyOverflowAdapter myOverflowAdapter = actionsDialogLite.mOverflowAdapter;
                                    GlobalActionsDialogLite.Action action = GlobalActionsDialogLite.this.mOverflowItems.get(i2);
                                    if (!(action instanceof GlobalActionsDialogLite.LongPressAction)) {
                                        return false;
                                    }
                                    GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
                                    if (globalActionsDialogLite.mDialog != null) {
                                        globalActionsDialogLite.mDialogTransitionAnimator.disableAllCurrentDialogsExitAnimations();
                                        GlobalActionsDialogLite.this.mDialog.dismiss();
                                    } else {
                                        Log.w("GlobalActionsDialogLite", "Action long-clicked while mDialog is null.");
                                    }
                                    return ((GlobalActionsDialogLite.LongPressAction) action).onLongPress();
                                }
                            };
                            globalActionsPopupMenu.setAnchorView(actionsDialogLite.findViewById(R.id.global_actions_overflow_button));
                            globalActionsPopupMenu.setAdapter(actionsDialogLite.mOverflowAdapter);
                            actionsDialogLite.mOverflowPopup = globalActionsPopupMenu;
                            globalActionsPopupMenu.show();
                        }
                    });
                    LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.mGlobalActionsLayout.getLayoutParams();
                    layoutParams.setMarginEnd(0);
                    this.mGlobalActionsLayout.setLayoutParams(layoutParams);
                } else {
                    viewFindViewById.setVisibility(8);
                    LinearLayout.LayoutParams layoutParams2 = (LinearLayout.LayoutParams) this.mGlobalActionsLayout.getLayoutParams();
                    layoutParams2.setMarginEnd(this.mContext.getResources().getDimensionPixelSize(R.dimen.global_actions_side_margin));
                    this.mGlobalActionsLayout.setLayoutParams(layoutParams2);
                }
            }
            if (this.mBackgroundDrawable == null) {
                this.mBackgroundDrawable = new ScrimDrawable();
            }
            int selectedUserId = this.mSelectedUserInteractor.getSelectedUserId();
            boolean userHasTrust = this.mKeyguardUpdateMonitor.getUserHasTrust(selectedUserId);
            if (this.mKeyguardShowing && userHasTrust) {
                this.mLockPatternUtils.requireCredentialEntry(selectedUserId);
                final View viewInflate = LayoutInflater.from(this.mContext).inflate(R.layout.global_actions_toast, this.mContainer, false);
                final int recommendedTimeoutMillis = ((AccessibilityManager) getContext().getSystemService("accessibility")).getRecommendedTimeoutMillis(3500, 2);
                viewInflate.setVisibility(0);
                viewInflate.setAlpha(0.0f);
                this.mContainer.addView(viewInflate);
                viewInflate.animate().alpha(1.0f).setDuration(333L).setListener(new AnimatorListenerAdapter(this) { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite.ActionsDialogLite.3
                    @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                    public final void onAnimationEnd(Animator animator) {
                        viewInflate.animate().alpha(0.0f).setDuration(333L).setStartDelay(recommendedTimeoutMillis).setListener(null);
                    }
                });
            }
            this.mWindowDimAmount = getWindow().getAttributes().dimAmount;
            getOnBackInvokedDispatcher().registerOnBackInvokedCallback(0, this.mOnBackInvokedCallback);
        }

        @Override // android.app.Dialog, android.view.Window.Callback
        public final void onDetachedFromWindow() {
            getOnBackInvokedDispatcher().unregisterOnBackInvokedCallback(this.mOnBackInvokedCallback);
        }

        @Override // android.app.Dialog
        public final boolean onTouchEvent(MotionEvent motionEvent) {
            return this.mGestureDetector.onTouchEvent(motionEvent) || super.onTouchEvent(motionEvent);
        }

        public void setBackDispatcherOverride(OnBackInvokedDispatcher onBackInvokedDispatcher) {
            this.mOverriddenBackDispatcher = onBackInvokedDispatcher;
        }

        @Override // android.app.Dialog
        public final void show() {
            super.show();
            ((NotificationShadeWindowControllerImpl) this.mNotificationShadeWindowController).setRequestTopUi("GlobalActionsDialogLite", true);
            if (getWindow().getAttributes().windowAnimations == 0) {
                startAnimation(true, null);
                setDismissOverride(new GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda4(this, 0));
            }
        }

        @Override // com.android.systemui.statusbar.phone.SystemUIDialog
        public final void start() {
            MultiListLayout multiListLayout = this.mGlobalActionsLayout;
            if (multiListLayout.mAdapter == null) {
                throw new IllegalStateException("mAdapter must be set before calling updateList");
            }
            multiListLayout.onUpdateList();
            LightBarControllerImpl lightBarControllerImpl = (LightBarControllerImpl) this.mLightBarController;
            if (!lightBarControllerImpl.mGlobalActionsVisible) {
                lightBarControllerImpl.mGlobalActionsVisible = true;
                lightBarControllerImpl.reevaluate();
            }
            if (this.mBackgroundDrawable != null) {
                this.mColorExtractor.addOnColorsChangedListener(this);
                updateColors(this.mColorExtractor.mNeutralColorsLock, false);
            }
        }

        public final void startAnimation(final boolean z, final GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda4 globalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda4) {
            float dimension;
            ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.0f, 1.0f);
            Resources resources = getContext().getResources();
            if (z) {
                dimension = resources.getDimension(17105839);
                valueAnimatorOfFloat.setInterpolator(Interpolators.STANDARD);
                valueAnimatorOfFloat.setDuration(resources.getInteger(android.R.integer.config_bluetooth_tx_cur_ma));
            } else {
                dimension = resources.getDimension(17105840);
                valueAnimatorOfFloat.setInterpolator(Interpolators.STANDARD_ACCELERATE);
                valueAnimatorOfFloat.setDuration(resources.getInteger(android.R.integer.config_brightness_ramp_rate_fast));
            }
            final float f = dimension;
            final Window window = getWindow();
            final int rotation = window.getWindowManager().getDefaultDisplay().getRotation();
            valueAnimatorOfFloat.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda7
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator) {
                    GlobalActionsDialogLite.ActionsDialogLite actionsDialogLite = this.f$0;
                    boolean z2 = z;
                    Window window2 = window;
                    float f2 = f;
                    int i = rotation;
                    int i2 = GlobalActionsDialogLite.ActionsDialogLite.$r8$clinit;
                    actionsDialogLite.getClass();
                    float fFloatValue = ((Float) valueAnimator.getAnimatedValue()).floatValue();
                    float f3 = z2 ? fFloatValue : 1.0f - fFloatValue;
                    actionsDialogLite.mGlobalActionsLayout.setAlpha(f3);
                    window2.setDimAmount(actionsDialogLite.mWindowDimAmount * f3);
                    float f4 = z2 ? (1.0f - fFloatValue) * f2 : f2 * fFloatValue;
                    if (i == 0) {
                        actionsDialogLite.mGlobalActionsLayout.setTranslationX(f4);
                        return;
                    }
                    if (i == 1) {
                        actionsDialogLite.mGlobalActionsLayout.setTranslationY(-f4);
                    } else if (i == 2) {
                        actionsDialogLite.mGlobalActionsLayout.setTranslationX(-f4);
                    } else {
                        if (i != 3) {
                            return;
                        }
                        actionsDialogLite.mGlobalActionsLayout.setTranslationY(f4);
                    }
                }
            });
            valueAnimatorOfFloat.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite.ActionsDialogLite.4
                public int mPreviousLayerType;

                @Override // android.animation.AnimatorListenerAdapter, android.animation.Animator.AnimatorListener
                public final void onAnimationEnd(Animator animator) {
                    ActionsDialogLite.this.mGlobalActionsLayout.setLayerType(this.mPreviousLayerType, null);
                    Runnable runnable = globalActionsDialogLite$ActionsDialogLite$$ExternalSyntheticLambda4;
                    if (runnable != null) {
                        runnable.run();
                    }
                }

                @Override // android.animation.Animator.AnimatorListener
                public final void onAnimationStart(Animator animator, boolean z2) {
                    this.mPreviousLayerType = ActionsDialogLite.this.mGlobalActionsLayout.getLayerType();
                    ActionsDialogLite.this.mGlobalActionsLayout.setLayerType(2, null);
                }
            });
            valueAnimatorOfFloat.start();
        }

        @Override // com.android.systemui.statusbar.phone.SystemUIDialog
        public final void stop() {
            LightBarControllerImpl lightBarControllerImpl = (LightBarControllerImpl) this.mLightBarController;
            if (lightBarControllerImpl.mGlobalActionsVisible) {
                lightBarControllerImpl.mGlobalActionsVisible = false;
                lightBarControllerImpl.reevaluate();
            }
            this.mColorExtractor.removeOnColorsChangedListener(this);
        }

        public final void updateColors(ColorExtractor.GradientColors gradientColors, boolean z) {
            ScrimDrawable scrimDrawable = this.mBackgroundDrawable;
            if (scrimDrawable == null) {
                return;
            }
            scrimDrawable.setColor(-16777216, z);
            View decorView = getWindow().getDecorView();
            if (gradientColors.supportsDarkText()) {
                decorView.setSystemUiVisibility(8208);
            } else {
                decorView.setSystemUiVisibility(0);
            }
        }
    }

    public class AirplaneModeAction extends ToggleAction {
        public AirplaneModeAction() {
            super(GlobalActionsDialogLite.this, android.R.drawable.ic_media_route_connected_light_11_mtrl, android.R.drawable.ic_media_route_connected_light_13_mtrl, android.R.string.minute, android.R.string.miniresolver_use_work_browser, android.R.string.miniresolver_use_personal_browser);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.ToggleAction
        public final void changeStateFromPress(boolean z) {
            GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
            if (globalActionsDialogLite.mHasTelephony && !((Boolean) TelephonyProperties.in_ecm_mode().orElse(Boolean.FALSE)).booleanValue()) {
                ToggleState toggleState = z ? ToggleState.TurningOn : ToggleState.TurningOff;
                this.mState = toggleState;
                globalActionsDialogLite.mAirplaneState = toggleState;
            }
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.ToggleAction
        public final void onToggle(boolean z) {
            GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
            if (!globalActionsDialogLite.mHasTelephony || !((Boolean) TelephonyProperties.in_ecm_mode().orElse(Boolean.FALSE)).booleanValue()) {
                GlobalActionsDialogLite.m2579$$Nest$mchangeAirplaneModeSystemSetting(globalActionsDialogLite, z);
                return;
            }
            globalActionsDialogLite.mIsWaitingForEcmExit = true;
            Intent intent = new Intent("android.telephony.action.SHOW_NOTICE_ECM_BLOCK_OTHERS", (Uri) null);
            intent.addFlags(268435456);
            globalActionsDialogLite.mContext.startActivity(intent);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final boolean showBeforeProvisioning() {
            return false;
        }
    }

    class BugReportAction extends SinglePressAction implements LongPressAction {
        public BugReportAction() {
            super(android.R.drawable.ic_media_route_connected_light_15_mtrl, android.R.string.config_mediaProjectionPermissionDialogComponent);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.LongPressAction
        public final boolean onLongPress() {
            GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
            if (ActivityManager.isUserAMonkey()) {
                return false;
            }
            try {
                globalActionsDialogLite.mMetricsLogger.action(IKnoxCustomManager.Stub.TRANSACTION_stopTcpDump);
                globalActionsDialogLite.mUiEventLogger.log(GlobalActionsEvent.GA_BUGREPORT_LONG_PRESS);
                Trace.instantForTrack(4096L, "bugreport", "BugReportAction#requestingFullBugReport");
                Log.d("GlobalActionsDialogLite", "BugReportAction#requestingFullBugReport");
                globalActionsDialogLite.mIActivityManager.requestFullBugReport();
            } catch (RemoteException unused) {
            }
            return false;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final void onPress() {
            if (ActivityManager.isUserAMonkey()) {
                return;
            }
            Trace.instantForTrack(4096L, "bugreport", "BugReportAction#onPress");
            Log.d("GlobalActionsDialogLite", "BugReportAction#onPress");
            postDelayed(new Runnable() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite.BugReportAction.1
                @Override // java.lang.Runnable
                public final void run() {
                    try {
                        GlobalActionsDialogLite.this.mMetricsLogger.action(IKnoxCustomManager.Stub.TRANSACTION_startTcpDump);
                        GlobalActionsDialogLite.this.mUiEventLogger.log(GlobalActionsEvent.GA_BUGREPORT_PRESS);
                        if (GlobalActionsDialogLite.this.mIActivityManager.launchBugReportHandlerApp()) {
                            return;
                        }
                        Log.w("GlobalActionsDialogLite", "Bugreport handler could not be launched");
                        Trace.instantForTrack(4096L, "bugreport", "BugReportAction#requestingInteractiveBugReport");
                        Log.d("GlobalActionsDialogLite", "BugReportAction#requestingInteractiveBugReport");
                        GlobalActionsDialogLite.this.mIActivityManager.requestInteractiveBugReport();
                    } catch (RemoteException unused) {
                    }
                }
            }, r0.mDialogPressDelay);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final boolean showBeforeProvisioning() {
            if (Build.isDebuggable()) {
                GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
                if (globalActionsDialogLite.mSecureSettings.getIntForUser("bugreport_in_power_menu", 0, ((UserTrackerImpl) globalActionsDialogLite.mUserTracker).getUserInfo().id) != 0 && ((UserTrackerImpl) globalActionsDialogLite.mUserTracker).getUserInfo().isAdmin()) {
                    return true;
                }
            }
            return false;
        }
    }

    public class CurrentUserProvider {
        public boolean mFetched;
        public UserInfo mUserInfo;

        public /* synthetic */ CurrentUserProvider(GlobalActionsDialogLite globalActionsDialogLite, int i) {
            this();
        }

        public final UserInfo get() {
            if (!this.mFetched) {
                this.mFetched = true;
                this.mUserInfo = ((UserTrackerImpl) GlobalActionsDialogLite.this.mUserTracker).getUserInfo();
            }
            return this.mUserInfo;
        }

        private CurrentUserProvider() {
            this.mUserInfo = null;
            this.mFetched = false;
        }
    }

    public abstract class EmergencyAction extends SinglePressAction {
        public EmergencyAction(int i, int i2) {
            super(i, i2);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.SinglePressAction, com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final View create(Context context, View view, ViewGroup viewGroup, LayoutInflater layoutInflater) throws Resources.NotFoundException {
            View viewCreate = super.create(context, view, viewGroup, layoutInflater);
            GlobalActionsDialogLite.this.getClass();
            int color = context.getResources().getColor(R.color.global_actions_lite_text);
            int color2 = context.getResources().getColor(R.color.global_actions_lite_emergency_icon);
            int color3 = context.getResources().getColor(R.color.global_actions_lite_emergency_background);
            TextView textView = (TextView) viewCreate.findViewById(android.R.id.message);
            textView.setTextColor(color);
            textView.setSelected(true);
            ImageView imageView = (ImageView) viewCreate.findViewById(android.R.id.icon);
            imageView.getDrawable().setTint(color2);
            imageView.setBackgroundTintList(ColorStateList.valueOf(color3));
            viewCreate.setBackgroundTintList(ColorStateList.valueOf(color3));
            return viewCreate;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final boolean showBeforeProvisioning() {
            return true;
        }
    }

    public class EmergencyAffordanceAction extends EmergencyAction {
        public EmergencyAffordanceAction() {
            super(android.R.drawable.highlight_disabled, android.R.string.menu_enter_shortcut_label);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final void onPress() {
            GlobalActionsDialogLite.this.mEmergencyAffordanceManager.performEmergencyCall();
        }
    }

    class EmergencyDialerAction extends EmergencyAction {
        public /* synthetic */ EmergencyDialerAction(GlobalActionsDialogLite globalActionsDialogLite, int i) {
            this();
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final void onPress() {
            GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
            globalActionsDialogLite.mMetricsLogger.action(1569);
            globalActionsDialogLite.mUiEventLogger.log(GlobalActionsEvent.GA_EMERGENCY_DIALER_PRESS);
            if (globalActionsDialogLite.mTelecomManager != null) {
                globalActionsDialogLite.mShadeController.cancelExpansionAndCollapseShade();
                Intent intentCreateLaunchEmergencyDialerIntent = globalActionsDialogLite.mTelecomManager.createLaunchEmergencyDialerIntent(null);
                intentCreateLaunchEmergencyDialerIntent.addFlags(343932928);
                intentCreateLaunchEmergencyDialerIntent.putExtra(EmergencyDialerConstants.EXTRA_ENTRY_TYPE, 2);
                globalActionsDialogLite.mContext.startActivityAsUser(intentCreateLaunchEmergencyDialerIntent, ((UserTrackerImpl) globalActionsDialogLite.mUserTracker).getUserHandle());
            }
        }

        private EmergencyDialerAction() {
            super(R.drawable.ic_emergency_star, android.R.string.menu_enter_shortcut_label);
        }
    }

    public enum GlobalActionsEvent implements UiEventLogger.UiEventEnum {
        GA_POWER_MENU_OPEN(337),
        GA_POWER_MENU_CLOSE(471),
        GA_BUGREPORT_PRESS(344),
        GA_BUGREPORT_LONG_PRESS(345),
        GA_EMERGENCY_DIALER_PRESS(346),
        GA_SCREENSHOT_PRESS(347),
        /* JADX INFO: Fake field, exist only in values array */
        GA_SCREENSHOT_LONG_PRESS(348),
        GA_SHUTDOWN_PRESS(802),
        GA_SHUTDOWN_LONG_PRESS(VpnErrorValues.ERROR_STORING_PROXY_PASSWORD),
        GA_REBOOT_PRESS(349),
        GA_REBOOT_LONG_PRESS(VpnErrorValues.ERROR_INVALID_PROXY_CONFIGURATION),
        GA_LOCKDOWN_PRESS(354),
        GA_OPEN_QS(VpnErrorValues.ERROR_INVALID_IPV6_CONFIGURATION),
        /* JADX INFO: Fake field, exist only in values array */
        GA_OPEN_POWER_VOLUP(806),
        /* JADX INFO: Fake field, exist only in values array */
        GA_OPEN_LONG_PRESS_POWER(807),
        /* JADX INFO: Fake field, exist only in values array */
        GA_CLOSE_LONG_PRESS_POWER(808),
        GA_CLOSE_BACK(809),
        GA_CLOSE_TAP_OUTSIDE(810),
        /* JADX INFO: Fake field, exist only in values array */
        GA_CLOSE_POWER_VOLUP(811),
        GA_SYSTEM_UPDATE_PRESS(1716),
        GA_STANDBY_PRESS(2210);

        private final int mId;

        GlobalActionsEvent(int i) {
            this.mId = i;
        }

        public final int getId() {
            return this.mId;
        }
    }

    class LockDownAction extends SinglePressAction {
        public LockDownAction() {
            super(android.R.drawable.ic_media_route_connected_light_18_mtrl, android.R.string.midnight);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final void onPress() {
            GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
            globalActionsDialogLite.mLockPatternUtils.requireStrongAuth(32, -1);
            globalActionsDialogLite.mUiEventLogger.log(GlobalActionsEvent.GA_LOCKDOWN_PRESS);
            try {
                globalActionsDialogLite.mIWindowManager.lockNow((Bundle) null);
                globalActionsDialogLite.mBackgroundExecutor.execute(new GlobalActionsDialogLite$$ExternalSyntheticLambda1(this, 1));
            } catch (RemoteException e) {
                Log.e("GlobalActionsDialogLite", "Error while trying to lock device.", e);
            }
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final boolean showBeforeProvisioning() {
            return false;
        }
    }

    public final class LogoutAction extends SinglePressAction {
        public /* synthetic */ LogoutAction(GlobalActionsDialogLite globalActionsDialogLite, int i) {
            this();
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final void onPress() {
            postDelayed(new GlobalActionsDialogLite$$ExternalSyntheticLambda1(this, 2), r0.mDialogPressDelay);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final boolean showBeforeProvisioning() {
            return false;
        }

        private LogoutAction() {
            super(android.R.drawable.ic_media_route_connecting_holo_light, android.R.string.mime_type_audio);
        }
    }

    public interface LongPressAction extends Action {
        boolean onLongPress();
    }

    public class MyAdapter extends MultiListLayout.MultiListAdapter {
        public MyAdapter() {
        }

        @Override // android.widget.BaseAdapter, android.widget.ListAdapter
        public final boolean areAllItemsEnabled() {
            return false;
        }

        public final int countItems(boolean z) {
            int i = 0;
            for (int i2 = 0; i2 < GlobalActionsDialogLite.this.mItems.size(); i2++) {
                GlobalActionsDialogLite.this.mItems.get(i2).getClass();
                if (!z) {
                    i++;
                }
            }
            return i;
        }

        @Override // com.android.systemui.MultiListLayout.MultiListAdapter
        public final int countListItems() {
            return countItems(false);
        }

        @Override // com.android.systemui.MultiListLayout.MultiListAdapter
        public final int countSeparatedItems() {
            return countItems(true);
        }

        @Override // android.widget.Adapter
        public final int getCount() {
            return countItems(false) + countItems(true);
        }

        @Override // android.widget.Adapter
        public final long getItemId(int i) {
            return i;
        }

        @Override // android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            Action item = getItem(i);
            Context context = GlobalActionsDialogLite.this.mContext;
            View viewCreate = item.create(context, view, viewGroup, LayoutInflater.from(context));
            viewCreate.setOnClickListener(new GlobalActionsDialogLite$MyAdapter$$ExternalSyntheticLambda0(this, i));
            if (item instanceof LongPressAction) {
                viewCreate.setOnLongClickListener(new GlobalActionsDialogLite$MyAdapter$$ExternalSyntheticLambda1(this, i));
            }
            return viewCreate;
        }

        @Override // android.widget.BaseAdapter, android.widget.ListAdapter
        public final boolean isEnabled(int i) {
            return getItem(i).isEnabled();
        }

        @Override // com.android.systemui.MultiListLayout.MultiListAdapter
        public final boolean shouldBeSeparated(int i) {
            getItem(i).getClass();
            return false;
        }

        @Override // android.widget.Adapter
        public final Action getItem(int i) {
            int i2 = 0;
            for (int i3 = 0; i3 < GlobalActionsDialogLite.this.mItems.size(); i3++) {
                Action action = GlobalActionsDialogLite.this.mItems.get(i3);
                if (GlobalActionsDialogLite.this.shouldShowAction(action)) {
                    if (i2 == i) {
                        return action;
                    }
                    i2++;
                }
            }
            StringBuilder sbM = MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "position ", " out of range of showable actions, filtered count=");
            sbM.append(getCount());
            sbM.append(", keyguardshowing=false, provisioned=");
            GlobalActionsDialogLite.this.getClass();
            sbM.append(GlobalActionsDialogLite.this.mDeviceProvisioned);
            throw new IllegalArgumentException(sbM.toString());
        }
    }

    public class MyOverflowAdapter extends BaseAdapter {
        public MyOverflowAdapter() {
        }

        @Override // android.widget.Adapter
        public final int getCount() {
            return GlobalActionsDialogLite.this.mOverflowItems.size();
        }

        @Override // android.widget.Adapter
        public final Object getItem(int i) {
            return GlobalActionsDialogLite.this.mOverflowItems.get(i);
        }

        @Override // android.widget.Adapter
        public final long getItemId(int i) {
            return i;
        }

        @Override // android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            Action action = GlobalActionsDialogLite.this.mOverflowItems.get(i);
            if (action == null) {
                RecordingInputConnection$$ExternalSyntheticOutline0.m(i, "No overflow action found at position: ", "GlobalActionsDialogLite");
                return null;
            }
            if (view == null) {
                view = LayoutInflater.from(GlobalActionsDialogLite.this.mContext).inflate(R.layout.controls_more_item, viewGroup, false);
            }
            TextView textView = (TextView) view;
            if (action.getMessageResId() != 0) {
                textView.setText(action.getMessageResId());
                return textView;
            }
            textView.setText(action.getMessage());
            return textView;
        }
    }

    public class MyPowerOptionsAdapter extends BaseAdapter {
        public static final /* synthetic */ int $r8$clinit = 0;

        public MyPowerOptionsAdapter() {
        }

        @Override // android.widget.Adapter
        public final int getCount() {
            return GlobalActionsDialogLite.this.mPowerItems.size();
        }

        @Override // android.widget.Adapter
        public final Object getItem(int i) {
            return GlobalActionsDialogLite.this.mPowerItems.get(i);
        }

        @Override // android.widget.Adapter
        public final long getItemId(int i) {
            return i;
        }

        @Override // android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            Action action = GlobalActionsDialogLite.this.mPowerItems.get(i);
            if (action == null) {
                RecordingInputConnection$$ExternalSyntheticOutline0.m(i, "No power options action found at position: ", "GlobalActionsDialogLite");
                return null;
            }
            if (view == null) {
                view = LayoutInflater.from(GlobalActionsDialogLite.this.mContext).inflate(R.layout.global_actions_power_item, viewGroup, false);
            }
            view.setOnClickListener(new GlobalActionsDialogLite$MyAdapter$$ExternalSyntheticLambda0(this, i));
            if (action instanceof LongPressAction) {
                view.setOnLongClickListener(new GlobalActionsDialogLite$MyAdapter$$ExternalSyntheticLambda1(this, i));
            }
            ImageView imageView = (ImageView) view.findViewById(android.R.id.icon);
            TextView textView = (TextView) view.findViewById(android.R.id.message);
            textView.setSelected(true);
            imageView.setImageDrawable(action.getIcon(GlobalActionsDialogLite.this.mContext));
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            if (action.getMessage() != null) {
                textView.setText(action.getMessage());
                return view;
            }
            textView.setText(action.getMessageResId());
            return view;
        }
    }

    public final class PowerOptionsAction extends SinglePressAction {
        public /* synthetic */ PowerOptionsAction(GlobalActionsDialogLite globalActionsDialogLite, int i) {
            this();
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final void onPress() {
            ActionsDialogLite actionsDialogLite = GlobalActionsDialogLite.this.mDialog;
            if (actionsDialogLite != null) {
                Context context = actionsDialogLite.mContext;
                MyPowerOptionsAdapter myPowerOptionsAdapter = actionsDialogLite.mPowerOptionsAdapter;
                ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(context).inflate(R.layout.global_actions_power_dialog, (ViewGroup) null);
                for (int i = 0; i < myPowerOptionsAdapter.getCount(); i++) {
                    viewGroup.addView(myPowerOptionsAdapter.getView(i, null, viewGroup));
                }
                Resources resources = context.getResources();
                Dialog dialog = new Dialog(context);
                dialog.requestWindowFeature(1);
                dialog.setContentView(viewGroup);
                Window window = dialog.getWindow();
                window.setType(2020);
                window.setTitle("");
                window.setBackgroundDrawable(resources.getDrawable(R.drawable.control_background, context.getTheme()));
                window.addFlags(131072);
                actionsDialogLite.mPowerOptionsDialog = dialog;
                dialog.show();
            }
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final boolean showBeforeProvisioning() {
            return true;
        }

        private PowerOptionsAction() {
            super(R.drawable.ic_settings_power, android.R.string.mime_type_document_ext);
        }
    }

    final class RestartAction extends SinglePressAction implements LongPressAction {
        public RestartAction() {
            super(android.R.drawable.jog_dial_bg, android.R.string.mime_type_image_ext);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.LongPressAction
        public final boolean onLongPress() {
            if (ActivityManager.isUserAMonkey()) {
                return false;
            }
            GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
            globalActionsDialogLite.mUiEventLogger.log(GlobalActionsEvent.GA_REBOOT_LONG_PRESS);
            if (globalActionsDialogLite.mUserManager.hasUserRestriction("no_safe_boot")) {
                return false;
            }
            globalActionsDialogLite.mWindowManagerFuncs.reboot(true);
            return true;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final void onPress() {
            if (ActivityManager.isUserAMonkey()) {
                return;
            }
            GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
            globalActionsDialogLite.mUiEventLogger.log(GlobalActionsEvent.GA_REBOOT_PRESS);
            globalActionsDialogLite.mWindowManagerFuncs.reboot(false);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final boolean showBeforeProvisioning() {
            return true;
        }
    }

    class ScreenshotAction extends SinglePressAction {
        public ScreenshotAction() {
            super(android.R.drawable.jog_tab_bar_left_end_confirm_gray, android.R.string.mime_type_presentation_ext);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final void onPress() {
            postDelayed(new Runnable() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite.ScreenshotAction.1
                @Override // java.lang.Runnable
                public final void run() {
                    GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
                    globalActionsDialogLite.mScreenshotHelper.takeScreenshot(0, globalActionsDialogLite.mHandler, (Consumer) null);
                    GlobalActionsDialogLite.this.mMetricsLogger.action(1282);
                    GlobalActionsDialogLite.this.mUiEventLogger.log(GlobalActionsEvent.GA_SCREENSHOT_PRESS);
                }
            }, r0.mDialogPressDelay);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final boolean shouldShow() {
            return 1 == GlobalActionsDialogLite.this.mContext.getResources().getInteger(android.R.integer.config_screenTimeoutOverride);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final boolean showBeforeProvisioning() {
            return false;
        }
    }

    final class ShutDownAction extends SinglePressAction implements LongPressAction {
        public ShutDownAction() {
            super(android.R.drawable.ic_lock_power_off, android.R.string.mime_type_compressed);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.LongPressAction
        public final boolean onLongPress() {
            if (ActivityManager.isUserAMonkey()) {
                return false;
            }
            GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
            globalActionsDialogLite.mUiEventLogger.log(GlobalActionsEvent.GA_SHUTDOWN_LONG_PRESS);
            if (globalActionsDialogLite.mUserManager.hasUserRestriction("no_safe_boot")) {
                return false;
            }
            globalActionsDialogLite.mWindowManagerFuncs.reboot(true);
            return true;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final void onPress() {
            if (ActivityManager.isUserAMonkey()) {
                return;
            }
            GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
            globalActionsDialogLite.mUiEventLogger.log(GlobalActionsEvent.GA_SHUTDOWN_PRESS);
            globalActionsDialogLite.mWindowManagerFuncs.shutdown();
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final boolean showBeforeProvisioning() {
            return true;
        }
    }

    public class SilentModeToggleAction extends ToggleAction {
        public SilentModeToggleAction() {
            super(GlobalActionsDialogLite.this, android.R.drawable.ic_doc_compressed, android.R.drawable.ic_doc_codes, android.R.string.miniresolver_sms_information, android.R.string.miniresolver_call_information, android.R.string.miniresolver_call_in_work);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.ToggleAction
        public final void onToggle(boolean z) {
            GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
            if (z) {
                globalActionsDialogLite.mAudioManager.setRingerMode(0);
            } else {
                globalActionsDialogLite.mAudioManager.setRingerMode(2);
            }
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final boolean showBeforeProvisioning() {
            return false;
        }
    }

    class StandbyAction extends SinglePressAction {
        public StandbyAction() {
            super(android.R.drawable.jog_tab_right_sound_off, android.R.string.miniresolver_open_in_personal);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final void onPress() {
            postDelayed(new GlobalActionsDialogLite$StandbyAction$$ExternalSyntheticLambda0(this, 0), r0.mDialogPressDelay);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final boolean showBeforeProvisioning() {
            return true;
        }
    }

    final class SystemUpdateAction extends SinglePressAction {
        public SystemUpdateAction() {
            super(R.drawable.ic_system_update, R.string.system_update_settings_list_item_title);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final void onPress() {
            GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
            globalActionsDialogLite.mUiEventLogger.log(GlobalActionsEvent.GA_SYSTEM_UPDATE_PRESS);
            Intent intent = new Intent("android.settings.SYSTEM_UPDATE_SETTINGS");
            intent.addFlags(270532608);
            globalActionsDialogLite.mActivityStarter.postStartActivityDismissingKeyguard(intent, 0);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final boolean showBeforeProvisioning() {
            return false;
        }
    }

    public abstract class ToggleAction implements Action {
        public final int mDisabledIconResid;
        public final int mDisabledStatusMessageResId;
        public final int mEnabledIconResId;
        public final int mEnabledStatusMessageResId;
        public ToggleState mState = ToggleState.Off;

        public ToggleAction(GlobalActionsDialogLite globalActionsDialogLite, int i, int i2, int i3, int i4, int i5) {
            this.mEnabledIconResId = i;
            this.mDisabledIconResid = i2;
            this.mEnabledStatusMessageResId = i4;
            this.mDisabledStatusMessageResId = i5;
        }

        public void changeStateFromPress(boolean z) {
            this.mState = z ? ToggleState.On : ToggleState.Off;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final View create(Context context, View view, ViewGroup viewGroup, LayoutInflater layoutInflater) {
            View viewInflate = layoutInflater.inflate(R.layout.global_actions_grid_item_v2, viewGroup, false);
            ViewGroup.LayoutParams layoutParams = viewInflate.getLayoutParams();
            layoutParams.width = -2;
            viewInflate.setLayoutParams(layoutParams);
            ImageView imageView = (ImageView) viewInflate.findViewById(android.R.id.icon);
            TextView textView = (TextView) viewInflate.findViewById(android.R.id.message);
            boolean zIsEnabled = isEnabled();
            if (textView != null) {
                textView.setText(getMessageResId());
                textView.setEnabled(zIsEnabled);
                textView.setSelected(true);
            }
            if (imageView != null) {
                ToggleState toggleState = this.mState;
                imageView.setImageDrawable(context.getDrawable((toggleState == ToggleState.On || toggleState == ToggleState.TurningOn) ? this.mEnabledIconResId : this.mDisabledIconResid));
                imageView.setEnabled(zIsEnabled);
            }
            viewInflate.setEnabled(zIsEnabled);
            return viewInflate;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final Drawable getIcon(Context context) {
            ToggleState toggleState = this.mState;
            return context.getDrawable((toggleState == ToggleState.On || toggleState == ToggleState.TurningOn) ? this.mEnabledIconResId : this.mDisabledIconResid);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final CharSequence getMessage() {
            return null;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final int getMessageResId() {
            ToggleState toggleState = this.mState;
            return (toggleState == ToggleState.On || toggleState == ToggleState.TurningOn) ? this.mEnabledStatusMessageResId : this.mDisabledStatusMessageResId;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final boolean isEnabled() {
            return !this.mState.inTransition();
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final void onPress() {
            if (this.mState.inTransition()) {
                Log.w("GlobalActionsDialogLite", "shouldn't be able to toggle when in transition");
                return;
            }
            boolean z = this.mState != ToggleState.On;
            onToggle(z);
            changeStateFromPress(z);
        }

        public abstract void onToggle(boolean z);
    }

    enum ToggleState {
        Off(false),
        TurningOn(true),
        TurningOff(true),
        On(false);

        private final boolean mInTransition;

        ToggleState(boolean z) {
            this.mInTransition = z;
        }

        public final boolean inTransition() {
            return this.mInTransition;
        }
    }

    /* renamed from: -$$Nest$mchangeAirplaneModeSystemSetting, reason: not valid java name */
    public static void m2579$$Nest$mchangeAirplaneModeSystemSetting(GlobalActionsDialogLite globalActionsDialogLite, boolean z) {
        globalActionsDialogLite.mGlobalSettings.putInt(SettingsHelper.INDEX_AIRPLANE_MODE_ON, z ? 1 : 0);
        Intent intent = new Intent("android.intent.action.AIRPLANE_MODE");
        intent.addFlags(VolumePanelValues.FLAG_SHOW_CSD_100_WARNINGS);
        intent.putExtra("state", z);
        globalActionsDialogLite.mContext.sendBroadcastAsUser(intent, UserHandle.ALL);
        if (globalActionsDialogLite.mHasTelephony) {
            return;
        }
        globalActionsDialogLite.mAirplaneState = z ? ToggleState.On : ToggleState.Off;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v12, types: [com.android.systemui.globalactions.GlobalActionsDialogLite$2] */
    /* JADX WARN: Type inference failed for: r6v7, types: [android.content.BroadcastReceiver, com.android.systemui.globalactions.GlobalActionsDialogLite$7] */
    /* JADX WARN: Type inference failed for: r7v1, types: [com.android.systemui.globalactions.GlobalActionsDialogLite$8, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r8v0, types: [android.database.ContentObserver, com.android.systemui.globalactions.GlobalActionsDialogLite$9] */
    public GlobalActionsDialogLite(Context context, GlobalActions.GlobalActionsManager globalActionsManager, AudioManager audioManager, LockPatternUtils lockPatternUtils, BroadcastDispatcher broadcastDispatcher, TelephonyListenerManager telephonyListenerManager, GlobalSettings globalSettings, SecureSettings secureSettings, VibratorHelper vibratorHelper, Resources resources, ConfigurationController configurationController, ActivityStarter activityStarter, UserTracker userTracker, KeyguardStateController keyguardStateController, UserManager userManager, TrustManager trustManager, IActivityManager iActivityManager, TelecomManager telecomManager, MetricsLogger metricsLogger, SysuiColorExtractor sysuiColorExtractor, IStatusBarService iStatusBarService, LightBarController lightBarController, NotificationShadeWindowController notificationShadeWindowController, StatusBarWindowControllerStore statusBarWindowControllerStore, IWindowManager iWindowManager, Executor executor, UiEventLogger uiEventLogger, RingerModeTracker ringerModeTracker, Handler handler, PackageManager packageManager, ShadeController shadeController, KeyguardUpdateMonitor keyguardUpdateMonitor, DialogTransitionAnimator dialogTransitionAnimator, SelectedUserInteractor selectedUserInteractor, UserLogoutInteractor userLogoutInteractor, GlobalActionsInteractor globalActionsInteractor, Lazy lazy, PowerManager powerManager) throws Resources.NotFoundException {
        ?? r6 = new BroadcastReceiver() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite.7
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                if (PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS.equals(action) || "android.intent.action.SCREEN_OFF".equals(action)) {
                    String stringExtra = intent.getStringExtra("reason");
                    if ("globalactions".equals(stringExtra)) {
                        return;
                    }
                    GlobalActionsDialogLite.this.mDialogTransitionAnimator.disableAllCurrentDialogsExitAnimations();
                    AnonymousClass2 anonymousClass2 = GlobalActionsDialogLite.this.mHandler;
                    anonymousClass2.sendMessage(anonymousClass2.obtainMessage(0, stringExtra));
                    return;
                }
                if (!"android.intent.action.EMERGENCY_CALLBACK_MODE_CHANGED".equals(action) || intent.getBooleanExtra("android.telephony.extra.PHONE_IN_ECM_STATE", false)) {
                    return;
                }
                GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
                if (globalActionsDialogLite.mIsWaitingForEcmExit) {
                    globalActionsDialogLite.mIsWaitingForEcmExit = false;
                    GlobalActionsDialogLite.m2579$$Nest$mchangeAirplaneModeSystemSetting(globalActionsDialogLite, true);
                }
            }
        };
        this.mBroadcastReceiver = r6;
        ?? r7 = new TelephonyCallback.ServiceStateListener() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite.8
            @Override // android.telephony.TelephonyCallback.ServiceStateListener
            public final void onServiceStateChanged(ServiceState serviceState) {
                GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
                if (globalActionsDialogLite.mHasTelephony) {
                    if (globalActionsDialogLite.mAirplaneModeOn == null) {
                        Log.d("GlobalActionsDialogLite", "Service changed before actions created");
                        return;
                    }
                    boolean z = serviceState.getState() == 3;
                    GlobalActionsDialogLite globalActionsDialogLite2 = GlobalActionsDialogLite.this;
                    ToggleState toggleState = z ? ToggleState.On : ToggleState.Off;
                    globalActionsDialogLite2.mAirplaneState = toggleState;
                    globalActionsDialogLite2.mAirplaneModeOn.mState = toggleState;
                    globalActionsDialogLite2.mAdapter.notifyDataSetChanged();
                    GlobalActionsDialogLite.this.mOverflowAdapter.notifyDataSetChanged();
                    GlobalActionsDialogLite.this.mPowerAdapter.notifyDataSetChanged();
                }
            }
        };
        this.mPhoneStateListener = r7;
        ?? r8 = new ContentObserver(this.mMainHandler) { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite.9
            @Override // android.database.ContentObserver
            public final void onChange(boolean z) {
                GlobalActionsDialogLite.this.onAirplaneModeChanged();
            }
        };
        this.mAirplaneModeObserver = r8;
        this.mContext = context;
        this.mWindowManagerFuncs = globalActionsManager;
        this.mAudioManager = audioManager;
        this.mLockPatternUtils = lockPatternUtils;
        this.mTelephonyListenerManager = telephonyListenerManager;
        this.mKeyguardStateController = keyguardStateController;
        this.mBroadcastDispatcher = broadcastDispatcher;
        this.mGlobalSettings = globalSettings;
        this.mSecureSettings = secureSettings;
        this.mResources = resources;
        this.mConfigurationController = configurationController;
        this.mActivityStarter = activityStarter;
        this.mUserTracker = userTracker;
        this.mUserManager = userManager;
        this.mTrustManager = trustManager;
        this.mIActivityManager = iActivityManager;
        this.mTelecomManager = telecomManager;
        this.mMetricsLogger = metricsLogger;
        this.mUiEventLogger = uiEventLogger;
        this.mSysuiColorExtractor = sysuiColorExtractor;
        this.mStatusBarService = iStatusBarService;
        this.mLightBarController = lightBarController;
        this.mNotificationShadeWindowController = notificationShadeWindowController;
        this.mStatusBarWindowControllerStore = statusBarWindowControllerStore;
        this.mIWindowManager = iWindowManager;
        this.mBackgroundExecutor = executor;
        this.mRingerModeTracker = ringerModeTracker;
        this.mMainHandler = handler;
        this.mSmallestScreenWidthDp = resources.getConfiguration().smallestScreenWidthDp;
        this.mOrientation = resources.getConfiguration().orientation;
        this.mShadeController = shadeController;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mDialogTransitionAnimator = dialogTransitionAnimator;
        this.mSelectedUserInteractor = selectedUserInteractor;
        this.mLogoutInteractor = userLogoutInteractor;
        this.mInteractor = globalActionsInteractor;
        this.mDisplayWindowPropertiesRepositoryLazy = lazy;
        this.mPowerManager = powerManager;
        this.mHandler = new Handler(handler.getLooper()) { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite.2
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                int i = message.what;
                GlobalActionsDialogLite globalActionsDialogLite = GlobalActionsDialogLite.this;
                if (i != 0) {
                    if (i != 1) {
                        return;
                    }
                    globalActionsDialogLite.refreshSilentMode();
                    globalActionsDialogLite.mAdapter.notifyDataSetChanged();
                    return;
                }
                if (globalActionsDialogLite.mDialog != null) {
                    if (BcSmartspaceDataPlugin.UI_SURFACE_DREAM.equals(message.obj)) {
                        globalActionsDialogLite.mDialog.hide();
                        globalActionsDialogLite.mDialog.dismiss();
                    } else {
                        globalActionsDialogLite.mDialog.dismiss();
                    }
                    globalActionsDialogLite.mDialog = null;
                }
            }
        };
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(PopupUIUtil.ACTION_CLOSE_SYSTEM_DIALOGS);
        intentFilter.addAction("android.intent.action.SCREEN_OFF");
        intentFilter.addAction("android.intent.action.EMERGENCY_CALLBACK_MODE_CHANGED");
        broadcastDispatcher.registerReceiver(intentFilter, r6);
        this.mHasTelephony = packageManager.hasSystemFeature("android.hardware.telephony");
        this.mIsTv = packageManager.hasSystemFeature("android.software.leanback");
        ((ArrayList) telephonyListenerManager.mTelephonyCallback.mServiceStateListeners).add(r7);
        telephonyListenerManager.updateListening();
        globalSettings.registerContentObserverSync(Settings.Global.getUriFor(SettingsHelper.INDEX_AIRPLANE_MODE_ON), true, (ContentObserver) r8);
        this.mHasVibrator = vibratorHelper.hasVibrator();
        boolean z = resources.getBoolean(android.R.bool.show_ongoing_ime_switcher);
        this.mShowSilentToggle = !z;
        if (!z) {
            ringerModeTracker.getRingerMode().observe(this, new Observer() { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite$$ExternalSyntheticLambda0
                @Override // androidx.lifecycle.Observer
                public final void onChanged(Object obj) {
                    sendEmptyMessage(1);
                }
            });
        }
        this.mEmergencyAffordanceManager = new EmergencyAffordanceManager(context);
        this.mScreenshotHelper = new ScreenshotHelper(context);
        ((ConfigurationControllerImpl) configurationController).addCallback(this);
    }

    public final void addIfShouldShowAction(List list, Action action) {
        if (shouldShowAction(action)) {
            ((ArrayList) list).add(action);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public void createActionItems() {
        int i;
        UserInfo userInfo;
        if (this.mHasVibrator) {
            this.mSilentModeAction = new SilentModeTriStateAction(this.mAudioManager, this.mHandler);
        } else {
            this.mSilentModeAction = new SilentModeToggleAction();
        }
        this.mAirplaneModeOn = new AirplaneModeAction();
        onAirplaneModeChanged();
        this.mItems.clear();
        this.mOverflowItems.clear();
        this.mPowerItems.clear();
        String[] defaultActions = getDefaultActions();
        Action shutDownAction = new ShutDownAction();
        Action restartAction = new RestartAction();
        ArraySet arraySet = new ArraySet();
        ArrayList arrayList = new ArrayList();
        boolean z = false;
        CurrentUserProvider currentUserProvider = new CurrentUserProvider(this, 0 == true ? 1 : 0);
        if (this.mEmergencyAffordanceManager.needsEmergencyAffordance()) {
            addIfShouldShowAction(arrayList, new EmergencyAffordanceAction());
            arraySet.add(ImsProfile.PDN_EMERGENCY);
        }
        int i2 = 0;
        while (i2 < defaultActions.length) {
            String str = defaultActions[i2];
            if (!arraySet.contains(str)) {
                if (GLOBAL_ACTION_KEY_POWER.equals(str)) {
                    addIfShouldShowAction(arrayList, shutDownAction);
                } else if (SubRoom.EXTRA_KEY_AIRPLANE_MODE.equals(str)) {
                    addIfShouldShowAction(arrayList, this.mAirplaneModeOn);
                } else if ("bugreport".equals(str)) {
                    if (shouldDisplayBugReport(currentUserProvider.get())) {
                        addIfShouldShowAction(arrayList, new BugReportAction());
                    }
                } else if (SystemUIAnalytics.QPNE_VID_SILENT.equals(str)) {
                    if (this.mShowSilentToggle) {
                        addIfShouldShowAction(arrayList, this.mSilentModeAction);
                    }
                } else if ("users".equals(str)) {
                    if (SystemProperties.getBoolean("fw.power_user_switcher", z)) {
                        UserInfo userInfo2 = currentUserProvider.get();
                        if (this.mUserManager.isUserSwitcherEnabled()) {
                            for (final UserInfo userInfo3 : this.mUserManager.getUsers()) {
                                if (userInfo3.supportsSwitchToByUser()) {
                                    boolean z2 = true;
                                    if (userInfo2 != null ? userInfo2.id != userInfo3.id : userInfo3.id != 0) {
                                        z2 = z;
                                    }
                                    String str2 = userInfo3.iconPath;
                                    Drawable drawableCreateFromPath = str2 != null ? Drawable.createFromPath(str2) : null;
                                    UserInfo userInfo4 = userInfo2;
                                    String str3 = userInfo3.name;
                                    if (str3 == null) {
                                        str3 = "Primary";
                                    }
                                    String strConcat = str3.concat(z2 ? " ✔" : "");
                                    userInfo = userInfo4;
                                    addIfShouldShowAction(arrayList, new SinglePressAction(android.R.drawable.ic_popup_sync_1, drawableCreateFromPath, strConcat) { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite.6
                                        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
                                        public final void onPress() {
                                            try {
                                                GlobalActionsDialogLite.this.mIActivityManager.switchUser(userInfo3.id);
                                            } catch (RemoteException e) {
                                                Log.e("GlobalActionsDialogLite", "Couldn't switch user " + e);
                                            }
                                        }

                                        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
                                        public final boolean showBeforeProvisioning() {
                                            return false;
                                        }
                                    });
                                } else {
                                    userInfo = userInfo2;
                                }
                                userInfo2 = userInfo;
                                z = false;
                            }
                        }
                    }
                } else if ("settings".equals(str)) {
                    addIfShouldShowAction(arrayList, new SinglePressAction(android.R.drawable.jog_tab_bar_left_unlock, android.R.string.miniresolver_call) { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite.3
                        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
                        public final void onPress() {
                            Intent intent = new Intent("android.settings.SETTINGS");
                            intent.addFlags(335544320);
                            GlobalActionsDialogLite.this.mContext.startActivity(intent);
                        }

                        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
                        public final boolean showBeforeProvisioning() {
                            return true;
                        }
                    });
                } else if ("lockdown".equals(str)) {
                    if (shouldDisplayLockdown(currentUserProvider.get())) {
                        addIfShouldShowAction(arrayList, new LockDownAction());
                    }
                } else if ("voiceassist".equals(str)) {
                    addIfShouldShowAction(arrayList, new SinglePressAction(android.R.drawable.list_section_header_holo_dark, android.R.string.miniresolver_switch) { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite.5
                        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
                        public final void onPress() {
                            Intent intent = new Intent("android.intent.action.VOICE_ASSIST");
                            intent.addFlags(335544320);
                            GlobalActionsDialogLite.this.mContext.startActivity(intent);
                        }

                        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
                        public final boolean showBeforeProvisioning() {
                            return true;
                        }
                    });
                } else if ("assist".equals(str)) {
                    addIfShouldShowAction(arrayList, new SinglePressAction(android.R.drawable.ic_corp_badge_off, android.R.string.mediasize_na_legal) { // from class: com.android.systemui.globalactions.GlobalActionsDialogLite.4
                        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
                        public final void onPress() {
                            Intent intent = new Intent("android.intent.action.ASSIST");
                            intent.addFlags(335544320);
                            GlobalActionsDialogLite.this.mContext.startActivity(intent);
                        }

                        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
                        public final boolean showBeforeProvisioning() {
                            return true;
                        }
                    });
                } else if ("restart".equals(str)) {
                    addIfShouldShowAction(arrayList, restartAction);
                } else if ("screenshot".equals(str)) {
                    addIfShouldShowAction(arrayList, new ScreenshotAction());
                } else if (!"logout".equals(str)) {
                    int i3 = 0;
                    if (ImsProfile.PDN_EMERGENCY.equals(str)) {
                        if (shouldDisplayEmergency()) {
                            addIfShouldShowAction(arrayList, new EmergencyDialerAction(this, i3));
                        }
                    } else if ("system_update".equals(str)) {
                        addIfShouldShowAction(arrayList, new SystemUpdateAction());
                    } else if ("standby".equals(str)) {
                        addIfShouldShowAction(arrayList, new StandbyAction());
                    } else {
                        Log.e("GlobalActionsDialogLite", "Invalid global action key " + str);
                    }
                } else if (((Boolean) this.mLogoutInteractor.isLogoutEnabled.$$delegate_0.getValue()).booleanValue()) {
                    addIfShouldShowAction(arrayList, new LogoutAction(this, 0));
                }
                arraySet.add(str);
            }
            i2++;
            z = false;
        }
        if (arrayList.contains(shutDownAction) && arrayList.contains(restartAction) && arrayList.size() > getMaxShownPowerItems()) {
            int iMin = Math.min(arrayList.indexOf(restartAction), arrayList.indexOf(shutDownAction));
            arrayList.remove(shutDownAction);
            arrayList.remove(restartAction);
            this.mPowerItems.add(shutDownAction);
            this.mPowerItems.add(restartAction);
            i = 0;
            arrayList.add(iMin, new PowerOptionsAction(this, i));
        } else {
            i = 0;
        }
        int size = arrayList.size();
        int i4 = i;
        while (i4 < size) {
            Object obj = arrayList.get(i4);
            i4++;
            Action action = (Action) obj;
            if (this.mItems.size() < getMaxShownPowerItems()) {
                this.mItems.add(action);
            } else {
                this.mOverflowItems.add(action);
            }
        }
    }

    public final void destroy() {
        this.mBroadcastDispatcher.unregisterReceiver(this.mBroadcastReceiver);
        TelephonyListenerManager telephonyListenerManager = this.mTelephonyListenerManager;
        ((ArrayList) telephonyListenerManager.mTelephonyCallback.mServiceStateListeners).remove(this.mPhoneStateListener);
        telephonyListenerManager.updateListening();
        this.mGlobalSettings.unregisterContentObserverSync(this.mAirplaneModeObserver);
        ((ConfigurationControllerImpl) this.mConfigurationController).removeCallback(this);
        if (this.mShowSilentToggle) {
            this.mRingerModeTracker.getRingerMode().removeObservers(this);
        }
    }

    @Override // com.android.systemui.plugins.GlobalActionsPanelPlugin.Callbacks
    public final void dismissGlobalActionsMenu() {
        removeMessages(0);
        sendEmptyMessage(0);
    }

    public String[] getDefaultActions() {
        return this.mResources.getStringArray(android.R.array.vendor_disallowed_apps_managed_profile);
    }

    @Override // androidx.lifecycle.LifecycleOwner
    public final Lifecycle getLifecycle() {
        return this.mLifecycle;
    }

    public int getMaxShownPowerItems() throws Resources.NotFoundException {
        return this.mResources.getInteger(R.integer.power_menu_lite_max_rows) * this.mResources.getInteger(R.integer.power_menu_lite_max_columns);
    }

    public boolean isTv() {
        return this.mIsTv;
    }

    public BugReportAction makeBugReportActionForTesting() {
        return new BugReportAction();
    }

    public EmergencyDialerAction makeEmergencyDialerActionForTesting() {
        return new EmergencyDialerAction(this, 0);
    }

    public ScreenshotAction makeScreenshotActionForTesting() {
        return new ScreenshotAction();
    }

    public final void onAirplaneModeChanged() {
        if (this.mHasTelephony || this.mAirplaneModeOn == null) {
            return;
        }
        ToggleState toggleState = this.mGlobalSettings.getInt(SettingsHelper.INDEX_AIRPLANE_MODE_ON, 0) == 1 ? ToggleState.On : ToggleState.Off;
        this.mAirplaneState = toggleState;
        this.mAirplaneModeOn.mState = toggleState;
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(Configuration configuration) {
        ActionsDialogLite actionsDialogLite = this.mDialog;
        if (actionsDialogLite == null || !actionsDialogLite.isShowing()) {
            return;
        }
        int i = configuration.smallestScreenWidthDp;
        if (i == this.mSmallestScreenWidthDp && configuration.orientation == this.mOrientation) {
            return;
        }
        this.mSmallestScreenWidthDp = i;
        this.mOrientation = configuration.orientation;
        ActionsDialogLite actionsDialogLite2 = this.mDialog;
        actionsDialogLite2.mOnRefreshCallback.run();
        GlobalActionsPopupMenu globalActionsPopupMenu = actionsDialogLite2.mOverflowPopup;
        if (globalActionsPopupMenu != null) {
            globalActionsPopupMenu.dismiss();
        }
        Dialog dialog = actionsDialogLite2.mPowerOptionsDialog;
        if (dialog != null) {
            dialog.dismiss();
        }
        MultiListLayout multiListLayout = actionsDialogLite2.mGlobalActionsLayout;
        if (multiListLayout.mAdapter == null) {
            throw new IllegalStateException("mAdapter must be set before calling updateList");
        }
        multiListLayout.onUpdateList();
    }

    @Override // android.content.DialogInterface.OnDismissListener
    public final void onDismiss(DialogInterface dialogInterface) {
        if (this.mDialog == dialogInterface) {
            this.mDialog = null;
        }
        this.mUiEventLogger.log(GlobalActionsEvent.GA_POWER_MENU_CLOSE);
        this.mWindowManagerFuncs.onGlobalActionsHidden();
        this.mLifecycle.setCurrentState(Lifecycle.State.CREATED);
        GlobalActionsRepository globalActionsRepository = this.mInteractor.repository;
        globalActionsRepository._isVisible.updateState(null, Boolean.FALSE);
        ((UserTrackerImpl) this.mUserTracker).removeCallback(this.mOnUserSwitched);
    }

    @Override // android.content.DialogInterface.OnShowListener
    public final void onShow(DialogInterface dialogInterface) {
        this.mMetricsLogger.visible(1568);
        this.mUiEventLogger.log(GlobalActionsEvent.GA_POWER_MENU_OPEN);
        GlobalActionsRepository globalActionsRepository = this.mInteractor.repository;
        globalActionsRepository._isVisible.updateState(null, Boolean.TRUE);
    }

    public final void refreshSilentMode() {
        if (this.mHasVibrator) {
            return;
        }
        Integer num = (Integer) this.mRingerModeTracker.getRingerMode().getValue();
        boolean z = (num == null || num.intValue() == 2) ? false : true;
        ((ToggleAction) this.mSilentModeAction).mState = z ? ToggleState.On : ToggleState.Off;
    }

    public void setZeroDialogPressDelayForTesting() {
        this.mDialogPressDelay = 0;
    }

    public boolean shouldDisplayBugReport(UserInfo userInfo) {
        return (userInfo == null || !userInfo.isAdmin() || this.mSecureSettings.getIntForUser("bugreport_in_power_menu", 0, userInfo.id) == 0) ? false : true;
    }

    public boolean shouldDisplayEmergency() {
        return this.mHasTelephony;
    }

    public boolean shouldDisplayLockdown(UserInfo userInfo) {
        if (userInfo == null) {
            return false;
        }
        int i = userInfo.id;
        if (!((KeyguardStateControllerImpl) this.mKeyguardStateController).mSecure) {
            return false;
        }
        int strongAuthForUser = this.mLockPatternUtils.getStrongAuthForUser(i);
        return strongAuthForUser == 0 || strongAuthForUser == 4;
    }

    public boolean shouldShowAction(Action action) {
        if (this.mDeviceProvisioned || action.showBeforeProvisioning()) {
            return action.shouldShow();
        }
        return false;
    }

    public final void showOrHideDialog(Expandable expandable, int i) {
        Context context;
        this.mDeviceProvisioned = true;
        ActionsDialogLite actionsDialogLite = this.mDialog;
        if (actionsDialogLite != null && actionsDialogLite.isShowing()) {
            this.mWindowManagerFuncs.onGlobalActionsShown();
            this.mDialog.dismiss();
            this.mDialog = null;
            return;
        }
        if (ShadeWindowGoesAround.isEnabled()) {
            try {
                context = ((DisplayWindowPropertiesRepositoryImpl) ((DisplayWindowPropertiesRepository) this.mDisplayWindowPropertiesRepositoryLazy.get())).get(i, 2017).context;
            } catch (Exception unused) {
                ClockEventController$$ExternalSyntheticOutline0.m(i, "Couldn't get context for displayId=", "GlobalActionsDialogLite");
                context = this.mContext;
            }
        } else {
            Log.e("GlobalActionsDialogLite", "Asked for the displayId=" + i + " context but returning default display one as ShadeWindowGoesAround flag is disabled.");
            context = this.mContext;
        }
        Context context2 = context;
        createActionItems();
        this.mAdapter = new MyAdapter();
        this.mOverflowAdapter = new MyOverflowAdapter();
        this.mPowerAdapter = new MyPowerOptionsAdapter();
        ActionsDialogLite actionsDialogLite2 = new ActionsDialogLite(context2, R.style.Theme_SystemUI_Dialog_GlobalActionsLite, this.mAdapter, this.mOverflowAdapter, this.mSysuiColorExtractor, this.mStatusBarService, this.mLightBarController, this.mKeyguardStateController, this.mNotificationShadeWindowController, (StatusBarWindowController) this.mStatusBarWindowControllerStore.forDisplay(context2.getDisplayId()), new GlobalActionsDialogLite$$ExternalSyntheticLambda1(this, 0), false, this.mPowerAdapter, this.mUiEventLogger, this.mShadeController, this.mKeyguardUpdateMonitor, this.mLockPatternUtils, this.mSelectedUserInteractor);
        actionsDialogLite2.setOnDismissListener(this);
        actionsDialogLite2.setOnShowListener(this);
        this.mDialog = actionsDialogLite2;
        refreshSilentMode();
        this.mAirplaneModeOn.mState = this.mAirplaneState;
        this.mAdapter.notifyDataSetChanged();
        this.mLifecycle.setCurrentState(Lifecycle.State.RESUMED);
        WindowManager.LayoutParams attributes = this.mDialog.getWindow().getAttributes();
        attributes.setTitle("ActionsDialog");
        attributes.layoutInDisplayCutoutMode = 3;
        this.mDialog.getWindow().setAttributes(attributes);
        this.mDialog.getWindow().addFlags(131072);
        DialogTransitionAnimator.Controller controllerDialogTransitionController = expandable != null ? expandable.dialogTransitionController(new DialogCuj(58, "global_actions")) : null;
        ((UserTrackerImpl) this.mUserTracker).addCallback(this.mOnUserSwitched, this.mBackgroundExecutor);
        if (controllerDialogTransitionController != null) {
            this.mDialogTransitionAnimator.show(this.mDialog, controllerDialogTransitionController, false);
        } else {
            this.mDialog.show();
        }
        this.mWindowManagerFuncs.onGlobalActionsShown();
    }

    abstract class SinglePressAction implements Action {
        public final Drawable mIcon;
        public final int mIconResId;
        ImageView mIconView;
        public final CharSequence mMessage;
        public final int mMessageResId;

        public SinglePressAction(int i, int i2) {
            this.mIconResId = i;
            this.mMessageResId = i2;
            this.mMessage = null;
            this.mIcon = null;
            this.mIconView = null;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public View create(Context context, View view, ViewGroup viewGroup, LayoutInflater layoutInflater) {
            GlobalActionsDialogLite.this.getClass();
            View viewInflate = layoutInflater.inflate(R.layout.global_actions_grid_item_lite, viewGroup, false);
            viewInflate.setId(View.generateViewId());
            this.mIconView = (ImageView) viewInflate.findViewById(android.R.id.icon);
            TextView textView = (TextView) viewInflate.findViewById(android.R.id.message);
            textView.setSelected(true);
            this.mIconView.setImageDrawable(getIcon(context));
            this.mIconView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            CharSequence charSequence = this.mMessage;
            if (charSequence != null) {
                textView.setText(charSequence);
                return viewInflate;
            }
            textView.setText(this.mMessageResId);
            return viewInflate;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final Drawable getIcon(Context context) {
            Drawable drawable = this.mIcon;
            return drawable != null ? drawable : context.getDrawable(this.mIconResId);
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final CharSequence getMessage() {
            return this.mMessage;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final int getMessageResId() {
            return this.mMessageResId;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final boolean isEnabled() {
            return true;
        }

        public SinglePressAction(int i, Drawable drawable, CharSequence charSequence) {
            this.mIconResId = i;
            this.mMessageResId = 0;
            this.mMessage = charSequence;
            this.mIcon = drawable;
        }
    }

    public class SilentModeTriStateAction implements Action, View.OnClickListener {
        public static final int[] ITEM_IDS = {android.R.id.round, android.R.id.row, android.R.id.rowTypeId};
        public final AudioManager mAudioManager;
        public final Handler mHandler;

        public SilentModeTriStateAction(AudioManager audioManager, Handler handler) {
            this.mAudioManager = audioManager;
            this.mHandler = handler;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final View create(Context context, View view, ViewGroup viewGroup, LayoutInflater layoutInflater) {
            View viewInflate = layoutInflater.inflate(android.R.layout.keyboard_key_preview, viewGroup, false);
            int ringerMode = this.mAudioManager.getRingerMode();
            int i = 0;
            while (i < 3) {
                View viewFindViewById = viewInflate.findViewById(ITEM_IDS[i]);
                viewFindViewById.setSelected(ringerMode == i);
                viewFindViewById.setTag(Integer.valueOf(i));
                viewFindViewById.setOnClickListener(this);
                i++;
            }
            return viewInflate;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final Drawable getIcon(Context context) {
            return null;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final CharSequence getMessage() {
            return null;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final int getMessageResId() {
            return 0;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final boolean isEnabled() {
            return true;
        }

        @Override // android.view.View.OnClickListener
        public final void onClick(View view) {
            if (view.getTag() instanceof Integer) {
                this.mAudioManager.setRingerMode(((Integer) view.getTag()).intValue());
                this.mHandler.sendEmptyMessageDelayed(0, 300L);
            }
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final boolean showBeforeProvisioning() {
            return false;
        }

        @Override // com.android.systemui.globalactions.GlobalActionsDialogLite.Action
        public final void onPress() {
        }
    }
}
