package com.android.systemui.statusbar;

import android.R;
import android.animation.Animator;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.content.Intent;
import android.content.pm.UserInfo;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserManager;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.ArrayMap;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.RemoteViews;
import android.widget.TextView;
import androidx.core.animation.AnimatorListenerAdapter;
import androidx.core.animation.AnimatorSet;
import androidx.core.animation.LinearInterpolator;
import androidx.core.animation.ObjectAnimator;
import androidx.core.animation.ValueAnimator;
import androidx.core.graphics.PathParser$$ExternalSyntheticOutline0;
import com.android.app.animation.InterpolatorsAndroidX;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.statusbar.NotificationVisibility;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.p038wm.shell.animation.Interpolators;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.LsRune;
import com.android.systemui.NotiRune;
import com.android.systemui.Rune;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.keyguard.KeyguardUnlockInfo;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogLevel;
import com.android.systemui.log.LogMessage;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.shade.ShadeControllerImpl;
import com.android.systemui.statusbar.RemoteInputController;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.RemoteInputControllerLogger;
import com.android.systemui.statusbar.notification.SubscreenNotificationController;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinator;
import com.android.systemui.statusbar.notification.collection.provider.NotificationVisibilityProviderImpl;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManagerImpl;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotificationContentView;
import com.android.systemui.statusbar.phone.StatusBarRemoteInputCallback;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.RemoteInputUriController;
import com.android.systemui.statusbar.policy.RemoteInputView;
import com.android.systemui.statusbar.policy.RemoteInputView$$ExternalSyntheticLambda0;
import com.android.systemui.statusbar.policy.RemoteInputViewController;
import com.android.systemui.statusbar.policy.RemoteInputViewControllerImpl;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.ListenerSet;
import com.android.systemui.util.SystemUIAnalytics;
import com.samsung.android.knox.SemPersonaManager;
import dagger.Lazy;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class NotificationRemoteInputManager implements Dumpable {
    public static final boolean ENABLE_REMOTE_INPUT = SystemProperties.getBoolean("debug.enable_remote_input", true);
    public static final boolean FORCE_REMOTE_INPUT_HISTORY = SystemProperties.getBoolean("debug.force_remoteinput_history", false);
    public final ActivityManager mActivityManager;
    public Callback mCallback;
    public final Lazy mCentralSurfacesOptionalLazy;
    public final NotificationClickNotifier mClickNotifier;
    public final Context mContext;
    public final KeyguardManager mKeyguardManager;
    public final NotificationLockscreenUserManager mLockscreenUserManager;
    public final ActionClickLogger mLogger;
    public RemoteInputController mRemoteInputController;
    public final RemoteInputControllerLogger mRemoteInputControllerLogger;
    public RemoteInputCoordinator mRemoteInputListener;
    public final RemoteInputUriController mRemoteInputUriController;
    public final SmartReplyController mSmartReplyController;
    public final StatusBarStateController mStatusBarStateController;
    public final UserManager mUserManager;
    public final NotificationVisibilityProvider mVisibilityProvider;
    public final List mControllerCallbacks = new ArrayList();
    public final ListenerSet mActionPressListeners = new ListenerSet();
    public final C25901 mInteractionHandler = new C25901();
    public final IStatusBarService mBarService = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));
    public final KeyguardStateController mKeyguardStateController = (KeyguardStateController) Dependency.get(KeyguardStateController.class);

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.statusbar.NotificationRemoteInputManager$1 */
    public final class C25901 implements RemoteViews.InteractionHandler {
        public C25901() {
        }

        public static Notification.Action getActionFromView(View view, NotificationEntry notificationEntry, PendingIntent pendingIntent) {
            Integer num = (Integer) view.getTag(R.id.pickers);
            if (num == null) {
                return null;
            }
            if (notificationEntry == null) {
                Log.w("NotifRemoteInputManager", "Couldn't determine notification for click.");
                return null;
            }
            StatusBarNotification statusBarNotification = notificationEntry.mSbn;
            Notification.Action[] actionArr = statusBarNotification.getNotification().actions;
            if (actionArr == null || num.intValue() >= actionArr.length) {
                Log.w("NotifRemoteInputManager", "statusBarNotification.getNotification().actions is null or invalid");
                return null;
            }
            Notification.Action action = statusBarNotification.getNotification().actions[num.intValue()];
            if (Objects.equals(action.actionIntent, pendingIntent)) {
                return action;
            }
            Log.w("NotifRemoteInputManager", "actionIntent does not match");
            return null;
        }

        public final boolean onInteraction(View view, final PendingIntent pendingIntent, RemoteViews.RemoteResponse remoteResponse) {
            NotificationEntry notificationEntry;
            boolean z;
            NotificationListenerService.Ranking ranking;
            NotificationChannel channel;
            ((Optional) NotificationRemoteInputManager.this.mCentralSurfacesOptionalLazy.get()).ifPresent(new NotificationRemoteInputManager$1$$ExternalSyntheticLambda0());
            ViewParent parent = view.getParent();
            while (true) {
                if (parent == null) {
                    notificationEntry = null;
                    break;
                }
                if (parent instanceof ExpandableNotificationRow) {
                    notificationEntry = ((ExpandableNotificationRow) parent).mEntry;
                    break;
                }
                parent = parent.getParent();
            }
            ActionClickLogger actionClickLogger = NotificationRemoteInputManager.this.mLogger;
            actionClickLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            ActionClickLogger$logInitialClick$2 actionClickLogger$logInitialClick$2 = new Function1() { // from class: com.android.systemui.statusbar.ActionClickLogger$logInitialClick$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    LogMessage logMessage = (LogMessage) obj;
                    String str1 = logMessage.getStr1();
                    String str2 = logMessage.getStr2();
                    String str3 = logMessage.getStr3();
                    StringBuilder m87m = AbstractC0866xb1ce8deb.m87m("ACTION CLICK ", str1, " (channel=", str2, ") for pending intent ");
                    m87m.append(str3);
                    return m87m.toString();
                }
            };
            LogBuffer logBuffer = actionClickLogger.buffer;
            LogMessage obtain = logBuffer.obtain("ActionClickLogger", logLevel, actionClickLogger$logInitialClick$2, null);
            obtain.setStr1(notificationEntry != null ? notificationEntry.mKey : null);
            obtain.setStr2((notificationEntry == null || (ranking = notificationEntry.mRanking) == null || (channel = ranking.getChannel()) == null) ? null : channel.getId());
            obtain.setStr3(pendingIntent.getIntent().toString());
            logBuffer.commit(obtain);
            if ((((StatusBarRemoteInputCallback) NotificationRemoteInputManager.this.mCallback).mDisabled2 & 4) != 0) {
                z = true;
            } else {
                Object tag = view.getTag(R.id.showCustom);
                RemoteInput[] remoteInputArr = tag instanceof RemoteInput[] ? (RemoteInput[]) tag : null;
                if (remoteInputArr != null) {
                    RemoteInput remoteInput = null;
                    for (RemoteInput remoteInput2 : remoteInputArr) {
                        if (remoteInput2.getAllowFreeFormInput()) {
                            remoteInput = remoteInput2;
                        }
                    }
                    if (remoteInput != null) {
                        z = NotificationRemoteInputManager.this.activateRemoteInput(view, remoteInputArr, remoteInput, pendingIntent, null, null);
                    }
                }
                z = false;
            }
            if (z) {
                if (notificationEntry != null) {
                    SystemUIAnalytics.sendEventCDLog("QPN001", "QPNE0010", "app", notificationEntry.mSbn.getPackageName());
                }
                ActionClickLogger actionClickLogger2 = NotificationRemoteInputManager.this.mLogger;
                actionClickLogger2.getClass();
                LogLevel logLevel2 = LogLevel.DEBUG;
                ActionClickLogger$logRemoteInputWasHandled$2 actionClickLogger$logRemoteInputWasHandled$2 = new Function1() { // from class: com.android.systemui.statusbar.ActionClickLogger$logRemoteInputWasHandled$2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        return PathParser$$ExternalSyntheticOutline0.m29m("  [Action click] Triggered remote input (for ", ((LogMessage) obj).getStr1(), "))");
                    }
                };
                LogBuffer logBuffer2 = actionClickLogger2.buffer;
                LogMessage obtain2 = logBuffer2.obtain("ActionClickLogger", logLevel2, actionClickLogger$logRemoteInputWasHandled$2, null);
                obtain2.setStr1(notificationEntry != null ? notificationEntry.mKey : null);
                logBuffer2.commit(obtain2);
                return true;
            }
            if (notificationEntry != null) {
                SystemUIAnalytics.sendEventCDLog("QPN001", "QPNE0009", "app", notificationEntry.mSbn.getPackageName());
            }
            Notification.Action actionFromView = getActionFromView(view, notificationEntry, pendingIntent);
            if (actionFromView != null) {
                ViewParent parent2 = view.getParent();
                String key = notificationEntry.mSbn.getKey();
                int indexOfChild = (view.getId() == 16908724 && parent2 != null && (parent2 instanceof ViewGroup)) ? ((ViewGroup) parent2).indexOfChild(view) : -1;
                NotificationVisibility obtain3 = ((NotificationVisibilityProviderImpl) NotificationRemoteInputManager.this.mVisibilityProvider).obtain(notificationEntry);
                NotificationClickNotifier notificationClickNotifier = NotificationRemoteInputManager.this.mClickNotifier;
                notificationClickNotifier.getClass();
                try {
                    notificationClickNotifier.barService.onNotificationActionClick(key, indexOfChild, actionFromView, obtain3, false);
                } catch (RemoteException unused) {
                }
                notificationClickNotifier.mainExecutor.execute(new NotificationClickNotifier$onNotificationActionClick$1(notificationClickNotifier, key));
            }
            try {
                ActivityManager.getService().resumeAppSwitches();
            } catch (RemoteException unused2) {
            }
            if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_FIFTH && pendingIntent.isActivity()) {
                SubscreenNotificationController subscreenNotificationController = (SubscreenNotificationController) Dependency.get(SubscreenNotificationController.class);
                String creatorPackage = pendingIntent.getCreatorPackage();
                if (subscreenNotificationController.mDeviceModel.isSubScreen()) {
                    subscreenNotificationController.mDeviceModel.getClass();
                    if (ArraysKt___ArraysKt.contains(new String[]{"com.skt.prod.dialer", "com.samsung.android.incallui"}, creatorPackage)) {
                        Log.d("NotifRemoteInputManager", "handle call notification clicked. start activity directly on subscreen. pkg: ".concat(creatorPackage));
                        RemoteViews.startPendingIntent(view, pendingIntent, remoteResponse.getLaunchOptions(view));
                        return true;
                    }
                }
            }
            Notification.Action actionFromView2 = getActionFromView(view, notificationEntry, pendingIntent);
            Callback callback = NotificationRemoteInputManager.this.mCallback;
            boolean isAuthenticationRequired = actionFromView2 == null ? false : actionFromView2.isAuthenticationRequired();
            final NotificationRemoteInputManager$$ExternalSyntheticLambda1 notificationRemoteInputManager$$ExternalSyntheticLambda1 = new NotificationRemoteInputManager$$ExternalSyntheticLambda1(this, remoteResponse, view, notificationEntry, pendingIntent);
            final StatusBarRemoteInputCallback statusBarRemoteInputCallback = (StatusBarRemoteInputCallback) callback;
            statusBarRemoteInputCallback.getClass();
            if (!pendingIntent.isActivity() && !isAuthenticationRequired) {
                return notificationRemoteInputManager$$ExternalSyntheticLambda1.handleClick();
            }
            statusBarRemoteInputCallback.mActionClickLogger.logWaitingToCloseKeyguard(pendingIntent);
            boolean z2 = statusBarRemoteInputCallback.mActivityIntentHelper.getPendingTargetActivityInfo(((NotificationLockscreenUserManagerImpl) statusBarRemoteInputCallback.mLockscreenUserManager).mCurrentUserId, pendingIntent) == null;
            if (LsRune.SECURITY_SWIPE_BOUNCER) {
                statusBarRemoteInputCallback.mStatusBarKeyguardViewManager.setShowSwipeBouncer(true);
            }
            statusBarRemoteInputCallback.mActivityStarter.dismissKeyguardThenExecute(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.statusbar.phone.StatusBarRemoteInputCallback$$ExternalSyntheticLambda0
                @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                public final boolean onDismiss() {
                    StatusBarRemoteInputCallback statusBarRemoteInputCallback2 = StatusBarRemoteInputCallback.this;
                    statusBarRemoteInputCallback2.mActionClickLogger.logKeyguardGone(pendingIntent);
                    try {
                        ActivityManager.getService().resumeAppSwitches();
                    } catch (RemoteException unused3) {
                    }
                    if (!notificationRemoteInputManager$$ExternalSyntheticLambda1.handleClick()) {
                        return false;
                    }
                    ((ShadeControllerImpl) statusBarRemoteInputCallback2.mShadeController).closeShadeIfOpen();
                    return false;
                }
            }, null, z2);
            return true;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Callback {
    }

    public NotificationRemoteInputManager(Context context, NotifPipelineFlags notifPipelineFlags, NotificationLockscreenUserManager notificationLockscreenUserManager, SmartReplyController smartReplyController, NotificationVisibilityProvider notificationVisibilityProvider, Lazy lazy, StatusBarStateController statusBarStateController, RemoteInputUriController remoteInputUriController, RemoteInputControllerLogger remoteInputControllerLogger, NotificationClickNotifier notificationClickNotifier, ActionClickLogger actionClickLogger, DumpManager dumpManager) {
        this.mContext = context;
        this.mLockscreenUserManager = notificationLockscreenUserManager;
        this.mSmartReplyController = smartReplyController;
        this.mVisibilityProvider = notificationVisibilityProvider;
        this.mCentralSurfacesOptionalLazy = lazy;
        this.mLogger = actionClickLogger;
        this.mUserManager = (UserManager) context.getSystemService("user");
        this.mActivityManager = (ActivityManager) context.getSystemService("activity");
        this.mKeyguardManager = (KeyguardManager) context.getSystemService(KeyguardManager.class);
        this.mStatusBarStateController = statusBarStateController;
        this.mRemoteInputUriController = remoteInputUriController;
        this.mRemoteInputControllerLogger = remoteInputControllerLogger;
        this.mClickNotifier = notificationClickNotifier;
        dumpManager.registerDumpable(this);
    }

    /* JADX WARN: Code restructure failed: missing block: B:114:0x0097, code lost:
    
        if (r3.mSecure == false) goto L41;
     */
    /* JADX WARN: Removed duplicated region for block: B:18:0x00de A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:19:0x00df  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean activateRemoteInput(final View view, final RemoteInput[] remoteInputArr, final RemoteInput remoteInput, final PendingIntent pendingIntent, final NotificationEntry.EditedSuggestionInfo editedSuggestionInfo, final String str) {
        final RemoteInputView remoteInputView;
        ExpandableNotificationRow expandableNotificationRow;
        boolean z;
        UserInfo profileParent;
        RemoteInputView.RevealParams revealParams;
        ViewParent parent = view.getParent();
        while (true) {
            if (parent == null) {
                remoteInputView = null;
                expandableNotificationRow = null;
                break;
            }
            if (parent instanceof View) {
                View view2 = (View) parent;
                if (view2.isRootNamespace()) {
                    remoteInputView = (RemoteInputView) view2.findViewWithTag(RemoteInputView.VIEW_TAG);
                    expandableNotificationRow = (ExpandableNotificationRow) view2.getTag(com.android.systemui.R.id.row_tag_for_content_view);
                    break;
                }
            }
            parent = parent.getParent();
        }
        if (expandableNotificationRow == null) {
            return false;
        }
        int i = 1;
        expandableNotificationRow.setUserExpanded(true, false);
        NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl = (NotificationLockscreenUserManagerImpl) this.mLockscreenUserManager;
        if (!notificationLockscreenUserManagerImpl.mAllowLockscreenRemoteInput) {
            int identifier = pendingIntent.getCreatorUserHandle().getIdentifier();
            UserManager userManager = this.mUserManager;
            boolean isManagedProfile = userManager.getUserInfo(identifier).isManagedProfile();
            KeyguardManager keyguardManager = this.mKeyguardManager;
            boolean z2 = isManagedProfile && keyguardManager.isDeviceLocked(identifier);
            boolean z3 = z2 && (profileParent = userManager.getProfileParent(identifier)) != null && keyguardManager.isDeviceLocked(profileParent.id);
            if (notificationLockscreenUserManagerImpl.isLockscreenPublicMode(identifier) || this.mStatusBarStateController.getState() == 1) {
                if (notificationLockscreenUserManagerImpl.isLockscreenPublicMode(identifier)) {
                    KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
                    if (keyguardStateControllerImpl.mOccluded) {
                    }
                }
                if (!z2 || z3) {
                    StatusBarRemoteInputCallback statusBarRemoteInputCallback = (StatusBarRemoteInputCallback) this.mCallback;
                    statusBarRemoteInputCallback.getClass();
                    if (!expandableNotificationRow.mIsPinned) {
                        ((StatusBarStateControllerImpl) statusBarRemoteInputCallback.mStatusBarStateController).mLeaveOpenOnKeyguardHide = true;
                    }
                    KeyguardUnlockInfo.setUnlockTrigger(KeyguardUnlockInfo.UnlockTrigger.TRIGGER_REMOTE_INPUT);
                    statusBarRemoteInputCallback.mStatusBarKeyguardViewManager.showBouncer();
                    statusBarRemoteInputCallback.mPendingRemoteInputView = view;
                } else {
                    StatusBarRemoteInputCallback statusBarRemoteInputCallback2 = (StatusBarRemoteInputCallback) this.mCallback;
                    statusBarRemoteInputCallback2.mCommandQueue.animateCollapsePanels();
                    statusBarRemoteInputCallback2.startWorkChallengeIfNecessary(identifier, null, null);
                    statusBarRemoteInputCallback2.mPendingWorkRemoteInputView = view;
                }
                z = true;
                if (z) {
                    return true;
                }
                if (remoteInputView != null && !remoteInputView.isAttachedToWindow()) {
                    remoteInputView = null;
                }
                if (remoteInputView == null) {
                    View view3 = expandableNotificationRow.mPrivateLayout.mExpandedChild;
                    remoteInputView = view3 == null ? null : (RemoteInputView) view3.findViewWithTag(RemoteInputView.VIEW_TAG);
                    if (remoteInputView == null) {
                        return false;
                    }
                }
                NotificationContentView notificationContentView = expandableNotificationRow.mPrivateLayout;
                if (remoteInputView == notificationContentView.mExpandedRemoteInput && !notificationContentView.mExpandedChild.isShown()) {
                    Callback callback = this.mCallback;
                    Runnable runnable = new Runnable() { // from class: com.android.systemui.statusbar.NotificationRemoteInputManager$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            NotificationRemoteInputManager.this.activateRemoteInput(view, remoteInputArr, remoteInput, pendingIntent, editedSuggestionInfo, str);
                        }
                    };
                    StatusBarRemoteInputCallback statusBarRemoteInputCallback3 = (StatusBarRemoteInputCallback) callback;
                    if (((KeyguardStateControllerImpl) statusBarRemoteInputCallback3.mKeyguardStateController).mShowing) {
                        if (!expandableNotificationRow.mIsPinned) {
                            ((StatusBarStateControllerImpl) statusBarRemoteInputCallback3.mStatusBarStateController).mLeaveOpenOnKeyguardHide = true;
                        }
                        KeyguardUnlockInfo.setUnlockTrigger(KeyguardUnlockInfo.UnlockTrigger.TRIGGER_REMOTE_INPUT);
                        statusBarRemoteInputCallback3.mStatusBarKeyguardViewManager.showBouncer();
                        statusBarRemoteInputCallback3.mPendingRemoteInputView = view;
                    } else {
                        if (expandableNotificationRow.isChildInGroup() && !expandableNotificationRow.mChildrenExpanded) {
                            NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
                            GroupExpansionManagerImpl groupExpansionManagerImpl = (GroupExpansionManagerImpl) statusBarRemoteInputCallback3.mGroupExpansionManager;
                            groupExpansionManagerImpl.setGroupExpanded(notificationEntry, !groupExpansionManagerImpl.isGroupExpanded(notificationEntry));
                            groupExpansionManagerImpl.isGroupExpanded(notificationEntry);
                        }
                        expandableNotificationRow.setUserExpanded(true, false);
                        NotificationContentView notificationContentView2 = expandableNotificationRow.mPrivateLayout;
                        notificationContentView2.mExpandedVisibleListener = runnable;
                        notificationContentView2.fireExpandedVisibleListenerIfVisible();
                    }
                    return true;
                }
                if (!remoteInputView.isAttachedToWindow()) {
                    return false;
                }
                if (Rune.SYSUI_APPLOCK) {
                    int identifier2 = pendingIntent.getCreatorUserHandle().getIdentifier();
                    if (this.mActivityManager.isAppLockedPackage(pendingIntent.getTargetPackage()) && !SemPersonaManager.isKnoxId(identifier2)) {
                        startAppLockCheckService(pendingIntent.getTargetPackage());
                    }
                }
                int width = view.getWidth();
                if (view instanceof TextView) {
                    TextView textView = (TextView) view;
                    if (textView.getLayout() != null) {
                        width = Math.min(width, textView.getCompoundPaddingRight() + textView.getCompoundPaddingLeft() + ((int) textView.getLayout().getLineWidth(0)));
                    }
                }
                int left = (width / 2) + view.getLeft();
                int height = (view.getHeight() / 2) + view.getTop();
                int width2 = remoteInputView.getWidth();
                int height2 = remoteInputView.getHeight() - height;
                int i2 = width2 - left;
                int max = Math.max(Math.max(left + height, left + height2), Math.max(i2 + height, i2 + height2));
                RemoteInputViewController remoteInputViewController = remoteInputView.mViewController;
                RemoteInputView.RevealParams revealParams2 = new RemoteInputView.RevealParams(left, height, max);
                RemoteInputViewControllerImpl remoteInputViewControllerImpl = (RemoteInputViewControllerImpl) remoteInputViewController;
                remoteInputViewControllerImpl.revealParams = revealParams2;
                if (remoteInputViewControllerImpl.isBound) {
                    remoteInputViewControllerImpl.view.mRevealParams = revealParams2;
                }
                RemoteInputViewControllerImpl remoteInputViewControllerImpl2 = (RemoteInputViewControllerImpl) remoteInputView.mViewController;
                remoteInputViewControllerImpl2.pendingIntent = pendingIntent;
                remoteInputViewControllerImpl2.setRemoteInput(remoteInput);
                RemoteInputViewControllerImpl remoteInputViewControllerImpl3 = (RemoteInputViewControllerImpl) remoteInputView.mViewController;
                remoteInputViewControllerImpl3.remoteInputs = remoteInputArr;
                NotificationEntry notificationEntry2 = remoteInputViewControllerImpl3.entry;
                notificationEntry2.editedSuggestionInfo = editedSuggestionInfo;
                if (editedSuggestionInfo != null) {
                    notificationEntry2.remoteInputText = editedSuggestionInfo.originalText;
                    notificationEntry2.remoteInputAttachment = null;
                }
                if (!remoteInputView.mIsFocusAnimationFlagActive && remoteInputView.getVisibility() != 0 && (revealParams = remoteInputView.mRevealParams) != null) {
                    Animator createCircularReveal = ViewAnimationUtils.createCircularReveal(remoteInputView, revealParams.centerX, revealParams.centerY, 0.0f, revealParams.radius);
                    createCircularReveal.setDuration(360L);
                    createCircularReveal.setInterpolator(Interpolators.LINEAR_OUT_SLOW_IN);
                    createCircularReveal.start();
                } else if (remoteInputView.mIsFocusAnimationFlagActive && remoteInputView.getVisibility() != 0) {
                    remoteInputView.mIsAnimatingAppearance = true;
                    remoteInputView.setAlpha(0.0f);
                    ViewGroup viewGroup = (ViewGroup) remoteInputView.getParent();
                    final View findViewById = viewGroup != null ? viewGroup.findViewById(R.id.activity_chooser_view_content) : null;
                    AnimatorSet animatorSet = new AnimatorSet();
                    ObjectAnimator ofFloat = ObjectAnimator.ofFloat(remoteInputView, View.ALPHA, 0.0f, 1.0f);
                    ofFloat.setStartDelay(33L);
                    ofFloat.setDuration(83L);
                    LinearInterpolator linearInterpolator = InterpolatorsAndroidX.LINEAR;
                    ofFloat.setInterpolator(linearInterpolator);
                    ValueAnimator ofFloat2 = ValueAnimator.ofFloat(0.5f, 1.0f);
                    ofFloat2.addUpdateListener(new RemoteInputView$$ExternalSyntheticLambda0(remoteInputView, ofFloat2, i));
                    ofFloat2.setDuration(360L);
                    ofFloat2.setInterpolator(InterpolatorsAndroidX.FAST_OUT_SLOW_IN);
                    if (findViewById == null) {
                        animatorSet.playTogether(ofFloat, ofFloat2);
                    } else {
                        ObjectAnimator ofFloat3 = ObjectAnimator.ofFloat(findViewById, View.ALPHA, 1.0f, 0.0f);
                        ofFloat3.setDuration(50L);
                        ofFloat3.setInterpolator(linearInterpolator);
                        animatorSet.addListener(new AnimatorListenerAdapter(remoteInputView, findViewById) { // from class: com.android.systemui.statusbar.policy.RemoteInputView.5
                            public final /* synthetic */ View val$fadeOutView;

                            public C34285(final RemoteInputView remoteInputView2, final View findViewById2) {
                                this.val$fadeOutView = findViewById2;
                            }

                            @Override // androidx.core.animation.Animator.AnimatorListener
                            public final void onAnimationEnd(androidx.core.animation.Animator animator) {
                                this.val$fadeOutView.setAlpha(1.0f);
                            }
                        });
                        animatorSet.playTogether(ofFloat, ofFloat2, ofFloat3);
                    }
                    animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.policy.RemoteInputView.4
                        public C34274() {
                        }

                        @Override // androidx.core.animation.Animator.AnimatorListener
                        public final void onAnimationEnd(androidx.core.animation.Animator animator) {
                            RemoteInputView.this.mIsAnimatingAppearance = false;
                        }
                    });
                    animatorSet.start();
                }
                remoteInputView2.focus();
                if (str != null) {
                    remoteInputView2.mEditText.setText(str);
                }
                return true;
            }
            if (z2) {
                StatusBarRemoteInputCallback statusBarRemoteInputCallback4 = (StatusBarRemoteInputCallback) this.mCallback;
                statusBarRemoteInputCallback4.mCommandQueue.animateCollapsePanels();
                statusBarRemoteInputCallback4.startWorkChallengeIfNecessary(identifier, null, null);
                statusBarRemoteInputCallback4.mPendingWorkRemoteInputView = view;
                z = true;
                if (z) {
                }
            }
        }
        z = false;
        if (z) {
        }
    }

    public final void addControllerCallback(RemoteInputController.Callback callback) {
        RemoteInputController remoteInputController = this.mRemoteInputController;
        if (remoteInputController == null) {
            ((ArrayList) this.mControllerCallbacks).add(callback);
            return;
        }
        remoteInputController.getClass();
        Objects.requireNonNull(callback);
        remoteInputController.mCallbacks.add(callback);
    }

    public final void closeRemoteInputs(boolean z) {
        RemoteInputController remoteInputController = this.mRemoteInputController;
        if (remoteInputController != null) {
            if (z) {
                remoteInputController.closeRemoteInputs(z);
            } else {
                remoteInputController.closeRemoteInputs(false);
            }
        }
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        final PrintWriter asIndenting = DumpUtilsKt.asIndenting(printWriter);
        if (this.mRemoteInputController != null) {
            asIndenting.println("mRemoteInputController: " + this.mRemoteInputController);
            asIndenting.increaseIndent();
            final RemoteInputController remoteInputController = this.mRemoteInputController;
            remoteInputController.getClass();
            asIndenting.print("mLastAppliedRemoteInputActive: ");
            asIndenting.println(remoteInputController.mLastAppliedRemoteInputActive);
            asIndenting.print("isRemoteInputActive: ");
            asIndenting.println(remoteInputController.isRemoteInputActive$1());
            asIndenting.println("mOpen: " + remoteInputController.mOpen.size());
            final int i = 0;
            DumpUtilsKt.withIncreasedIndent(asIndenting, new Runnable() { // from class: com.android.systemui.statusbar.RemoteInputController$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    switch (i) {
                        case 0:
                            RemoteInputController remoteInputController2 = remoteInputController;
                            IndentingPrintWriter indentingPrintWriter = asIndenting;
                            Iterator it = remoteInputController2.mOpen.iterator();
                            while (it.hasNext()) {
                                NotificationEntry notificationEntry = (NotificationEntry) ((WeakReference) ((Pair) it.next()).first).get();
                                indentingPrintWriter.println(notificationEntry == null ? "???" : notificationEntry.mKey);
                            }
                            break;
                        default:
                            RemoteInputController remoteInputController3 = remoteInputController;
                            IndentingPrintWriter indentingPrintWriter2 = asIndenting;
                            Iterator it2 = remoteInputController3.mSpinning.keySet().iterator();
                            while (it2.hasNext()) {
                                indentingPrintWriter2.println((String) it2.next());
                            }
                            break;
                    }
                }
            });
            StringBuilder sb = new StringBuilder("mSpinning: ");
            ArrayMap arrayMap = remoteInputController.mSpinning;
            sb.append(arrayMap.size());
            asIndenting.println(sb.toString());
            final int i2 = 1;
            DumpUtilsKt.withIncreasedIndent(asIndenting, new Runnable() { // from class: com.android.systemui.statusbar.RemoteInputController$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    switch (i2) {
                        case 0:
                            RemoteInputController remoteInputController2 = remoteInputController;
                            IndentingPrintWriter indentingPrintWriter = asIndenting;
                            Iterator it = remoteInputController2.mOpen.iterator();
                            while (it.hasNext()) {
                                NotificationEntry notificationEntry = (NotificationEntry) ((WeakReference) ((Pair) it.next()).first).get();
                                indentingPrintWriter.println(notificationEntry == null ? "???" : notificationEntry.mKey);
                            }
                            break;
                        default:
                            RemoteInputController remoteInputController3 = remoteInputController;
                            IndentingPrintWriter indentingPrintWriter2 = asIndenting;
                            Iterator it2 = remoteInputController3.mSpinning.keySet().iterator();
                            while (it2.hasNext()) {
                                indentingPrintWriter2.println((String) it2.next());
                            }
                            break;
                    }
                }
            });
            asIndenting.println(arrayMap);
            asIndenting.print("mDelegate: ");
            asIndenting.println(remoteInputController.mDelegate);
            asIndenting.decreaseIndent();
        }
        RemoteInputCoordinator remoteInputCoordinator = this.mRemoteInputListener;
        if (remoteInputCoordinator instanceof Dumpable) {
            asIndenting.println("mRemoteInputListener: ".concat(remoteInputCoordinator.getClass().getSimpleName()));
            asIndenting.increaseIndent();
            this.mRemoteInputListener.dump(asIndenting, strArr);
            asIndenting.decreaseIndent();
        }
    }

    public final boolean isNotificationKeptForRemoteInputHistory(String str) {
        RemoteInputCoordinator remoteInputCoordinator = this.mRemoteInputListener;
        if (remoteInputCoordinator != null) {
            return remoteInputCoordinator.mRemoteInputHistoryExtender.isExtending(str) || remoteInputCoordinator.mSmartReplyHistoryExtender.isExtending(str);
        }
        return false;
    }

    public final boolean isRemoteInputActive() {
        RemoteInputController remoteInputController = this.mRemoteInputController;
        return remoteInputController != null && remoteInputController.isRemoteInputActive$1();
    }

    public final void startAppLockCheckService(String str) {
        Intent intent = new Intent("com.samsung.android.intent.action.CHECK_APPLOCK_SERVICE");
        intent.setPackage("com.samsung.android.applock");
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.flags |= 524288;
        intent.putExtra("LOCKED_PACKAGE_WINDOW_ATTRIBUTES", layoutParams);
        intent.putExtra("LAUNCH_FROM_RESUME", true);
        intent.putExtra("LOCKED_APP_CAN_SHOW_WHEN_LOCKED", true);
        intent.putExtra("LOCKED_PACKAGE_NAME", str);
        intent.putExtra("startFromNotification", true);
        intent.putExtra("LOCKED_PACKAGE_DISPLAYID", 0);
        this.mContext.startService(intent);
    }
}
