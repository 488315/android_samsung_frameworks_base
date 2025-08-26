package com.android.systemui.statusbar;

import android.R;
import android.app.ActivityManager;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.content.pm.UserInfo;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserManager;
import android.service.notification.StatusBarNotification;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.Pair;
import android.util.Property;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.widget.RemoteViews;
import androidx.core.animation.Animator;
import androidx.core.animation.AnimatorListenerAdapter;
import androidx.core.animation.AnimatorSet;
import androidx.core.animation.LinearInterpolator;
import androidx.core.animation.ObjectAnimator;
import androidx.core.animation.ValueAnimator;
import com.android.app.animation.InterpolatorsAndroidX;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.statusbar.NotificationVisibility;
import com.android.systemui.CoreStartable;
import com.android.systemui.Dependency;
import com.android.systemui.NotiRune;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.statusbar.NotificationRemoteInputManager;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.RemoteInputControllerLogger;
import com.android.systemui.statusbar.notification.SubscreenDeviceModelParent;
import com.android.systemui.statusbar.notification.SubscreenNotificationController;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinator;
import com.android.systemui.statusbar.notification.collection.provider.NotificationVisibilityProviderImpl;
import com.android.systemui.statusbar.notification.collection.render.GroupExpansionManagerImpl;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotificationContentView;
import com.android.systemui.statusbar.notification.shared.NotificationBundleUi;
import com.android.systemui.statusbar.phone.ExpandHeadsUpOnInlineReply;
import com.android.systemui.statusbar.phone.StatusBarRemoteInputCallback;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityDataHelper;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.RemoteInputUriController;
import com.android.systemui.statusbar.policy.RemoteInputView;
import com.android.systemui.statusbar.policy.RemoteInputView$$ExternalSyntheticLambda2;
import com.android.systemui.statusbar.policy.RemoteInputViewControllerImpl;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.ListenerSet;
import com.android.systemui.util.NotificationSAUtil;
import com.android.systemui.util.SystemUIAnalytics;
import com.android.systemui.util.kotlin.JavaAdapter;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import kotlin.collections.ArraysKt___ArraysKt;

/* loaded from: classes3.dex */
public class NotificationRemoteInputManager implements CoreStartable {
    public static final boolean ENABLE_REMOTE_INPUT = SystemProperties.getBoolean("debug.enable_remote_input", true);
    public static final boolean FORCE_REMOTE_INPUT_HISTORY = SystemProperties.getBoolean("debug.force_remoteinput_history", false);
    public Callback mCallback;
    public final NotificationClickNotifier mClickNotifier;
    public final Context mContext;
    public final JavaAdapter mJavaAdapter;
    public final KeyguardManager mKeyguardManager;
    public final KeyguardStateController mKeyguardStateController;
    public final NotificationLockscreenUserManager mLockscreenUserManager;
    public final ActionClickLogger mLogger;
    public final PowerInteractor mPowerInteractor;
    public RemoteInputController mRemoteInputController;
    public final RemoteInputControllerLogger mRemoteInputControllerLogger;
    public RemoteInputCoordinator mRemoteInputListener;
    public final RemoteInputUriController mRemoteInputUriController;
    public final ShadeInteractor mShadeInteractor;
    public final SmartReplyController mSmartReplyController;
    public final StatusBarStateController mStatusBarStateController;
    public final UserManager mUserManager;
    public final NotificationVisibilityProvider mVisibilityProvider;
    public final List mControllerCallbacks = new ArrayList();
    public final ListenerSet mActionPressListeners = new ListenerSet();
    public final AnonymousClass1 mInteractionHandler = new AnonymousClass1();
    public final IStatusBarService mBarService = IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar"));

    /* renamed from: com.android.systemui.statusbar.NotificationRemoteInputManager$1, reason: invalid class name */
    public class AnonymousClass1 implements RemoteViews.InteractionHandler {
        public AnonymousClass1() {
        }

        public final boolean onInteraction(final View view, PendingIntent pendingIntent, final RemoteViews.RemoteResponse remoteResponse) {
            ExpandableNotificationRow expandableNotificationRow;
            boolean zActivateRemoteInputOnExpanded;
            Notification.Action action;
            StatusBarNotification statusBarNotification;
            final PendingIntent pendingIntent2 = pendingIntent;
            NotificationRemoteInputManager.this.mPowerInteractor.wakeUpIfDozing(4, "NOTIFICATION_CLICK");
            final Integer num = (Integer) view.getTag(R.id.rectangle);
            ViewParent parent = view.getParent();
            while (true) {
                if (parent == null) {
                    expandableNotificationRow = null;
                    break;
                }
                if (parent instanceof ExpandableNotificationRow) {
                    expandableNotificationRow = (ExpandableNotificationRow) parent;
                    break;
                }
                parent = parent.getParent();
            }
            if (expandableNotificationRow == null) {
                NotificationRemoteInputManager.this.mLogger.logInitialClick(pendingIntent2, null, "OA");
                OngoingActivityDataHelper.INSTANCE.getClass();
                if (OngoingActivityDataHelper.mOngoingActivityLists.size() > 0) {
                    NotificationSAUtil.sendOALog(SystemUIAnalytics.OAID_ONGOING_ACTION_BUTTONS, OngoingActivityDataHelper.getDataByIndex(0).mNotificationEntry);
                }
                if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_COMMON && pendingIntent2.isActivity()) {
                    SubscreenNotificationController subscreenNotificationController = (SubscreenNotificationController) Dependency.sDependency.getDependencyInner(SubscreenNotificationController.class);
                    String creatorPackage = pendingIntent2.getCreatorPackage();
                    SubscreenDeviceModelParent subscreenDeviceModelParent = subscreenNotificationController.mDeviceModel;
                    if (!(subscreenDeviceModelParent != null ? Boolean.valueOf(subscreenDeviceModelParent.clickLiveNotificationActionButtonForActivity(creatorPackage, pendingIntent2)) : null).booleanValue()) {
                        pendingIntent2.getCreatorPackage();
                        return false;
                    }
                }
                try {
                    ActivityManager.getService().resumeAppSwitches();
                } catch (RemoteException unused) {
                }
                return ((StatusBarRemoteInputCallback) NotificationRemoteInputManager.this.mCallback).handleRemoteViewClick(pendingIntent2, false, null, new ClickHandler() { // from class: com.android.systemui.statusbar.NotificationRemoteInputManager$1$$ExternalSyntheticLambda1
                    @Override // com.android.systemui.statusbar.NotificationRemoteInputManager.ClickHandler
                    public final boolean handleClick() {
                        RemoteViews.RemoteResponse remoteResponse2 = remoteResponse;
                        View view2 = view;
                        PendingIntent pendingIntent3 = pendingIntent2;
                        NotificationRemoteInputManager.AnonymousClass1 anonymousClass1 = this.f$0;
                        anonymousClass1.getClass();
                        Pair launchOptions = remoteResponse2.getLaunchOptions(view2);
                        NotificationRemoteInputManager.this.mLogger.logStartingIntentWithDefaultHandler(pendingIntent3, null, "OA");
                        return RemoteViews.startPendingIntent(view2, pendingIntent3, launchOptions);
                    }
                });
            }
            NotificationRemoteInputManager.this.mLogger.logInitialClick(pendingIntent2, num, expandableNotificationRow.mLoggingKey);
            if ((((StatusBarRemoteInputCallback) NotificationRemoteInputManager.this.mCallback).mDisabled2 & 4) != 0) {
                zActivateRemoteInputOnExpanded = true;
            } else {
                Object tag = view.getTag(R.id.status);
                RemoteInput[] remoteInputArr = tag instanceof RemoteInput[] ? (RemoteInput[]) tag : null;
                if (remoteInputArr == null) {
                    zActivateRemoteInputOnExpanded = false;
                } else {
                    RemoteInput remoteInput = null;
                    for (RemoteInput remoteInput2 : remoteInputArr) {
                        if (remoteInput2.getAllowFreeFormInput()) {
                            remoteInput = remoteInput2;
                        }
                    }
                    if (remoteInput == null) {
                        pendingIntent2 = pendingIntent;
                        zActivateRemoteInputOnExpanded = false;
                    } else {
                        NotificationRemoteInputManager notificationRemoteInputManager = NotificationRemoteInputManager.this;
                        notificationRemoteInputManager.getClass();
                        int i = ExpandHeadsUpOnInlineReply.$r8$clinit;
                        zActivateRemoteInputOnExpanded = notificationRemoteInputManager.activateRemoteInputOnExpanded(view, remoteInputArr, remoteInput, pendingIntent, null);
                        pendingIntent2 = pendingIntent;
                    }
                }
            }
            if (zActivateRemoteInputOnExpanded) {
                NotificationEntry notificationEntry = expandableNotificationRow.mEntry;
                if (notificationEntry != null && (statusBarNotification = notificationEntry.mSbn) != null) {
                    SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.SID_QUICKPANEL_OPENED, SystemUIAnalytics.EID_QPNE_REPLY_BUTTON, SystemUIAnalytics.QPNE_KEY_APP, statusBarNotification.getPackageName());
                }
                ActionClickLogger actionClickLogger = NotificationRemoteInputManager.this.mLogger;
                String str = expandableNotificationRow.mLoggingKey;
                actionClickLogger.getClass();
                LogLevel logLevel = LogLevel.DEBUG;
                ActionClickLogger$$ExternalSyntheticLambda0 actionClickLogger$$ExternalSyntheticLambda0 = new ActionClickLogger$$ExternalSyntheticLambda0(1);
                LogBuffer logBuffer = actionClickLogger.buffer;
                LogMessage logMessageObtain = logBuffer.obtain("ActionClickLogger", logLevel, actionClickLogger$$ExternalSyntheticLambda0, null);
                LogMessageImpl logMessageImpl = (LogMessageImpl) logMessageObtain;
                logMessageImpl.str1 = str;
                logMessageImpl.int1 = num != null ? num.intValue() : Integer.MIN_VALUE;
                logBuffer.commit(logMessageObtain);
                return true;
            }
            NotificationEntry notificationEntry2 = expandableNotificationRow.mEntry;
            if (notificationEntry2 != null) {
                NotificationSAUtil.sendTypeLog(SystemUIAnalytics.EID_QPNE_NOTI_ACTION_BUTTON, notificationEntry2);
            }
            Integer num2 = (Integer) view.getTag(R.id.rectangle);
            if (num2 == null) {
                action = null;
            } else {
                int i2 = NotificationBundleUi.$r8$clinit;
                StatusBarNotification statusBarNotification2 = expandableNotificationRow.getEntryLegacy() != null ? expandableNotificationRow.getEntryLegacy().mSbn : null;
                if (statusBarNotification2 == null) {
                    Log.w("NotifRemoteInputManager", "Couldn't determine notification for click.");
                } else {
                    Notification.Action[] actionArr = statusBarNotification2.getNotification().actions;
                    if (actionArr == null || num2.intValue() >= actionArr.length) {
                        Log.w("NotifRemoteInputManager", "statusBarNotification.getNotification().actions is null or invalid");
                    } else {
                        Notification.Action action2 = statusBarNotification2.getNotification().actions[num2.intValue()];
                        if (Objects.equals(action2.actionIntent, pendingIntent2)) {
                            action = action2;
                        } else {
                            Log.w("NotifRemoteInputManager", "actionIntent does not match");
                        }
                    }
                }
                action = null;
            }
            String key = expandableNotificationRow.getKey();
            if (action != null) {
                ViewParent parent2 = view.getParent();
                int iIndexOfChild = (view.getId() == 16908759 && parent2 != null && (parent2 instanceof ViewGroup)) ? ((ViewGroup) parent2).indexOfChild(view) : -1;
                NotificationVisibility notificationVisibilityObtain = ((NotificationVisibilityProviderImpl) NotificationRemoteInputManager.this.mVisibilityProvider).obtain(key);
                NotificationClickNotifier notificationClickNotifier = NotificationRemoteInputManager.this.mClickNotifier;
                notificationClickNotifier.backgroundExecutor.execute(new NotificationClickNotifier$onNotificationActionClick$1(notificationClickNotifier, key, iIndexOfChild, action, notificationVisibilityObtain, false));
                notificationClickNotifier.mainExecutor.execute(new NotificationClickNotifier$onNotificationActionClick$2(notificationClickNotifier, key));
            }
            try {
                ActivityManager.getService().resumeAppSwitches();
            } catch (RemoteException unused2) {
            }
            if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_FIFTH && pendingIntent2.isActivity()) {
                SubscreenNotificationController subscreenNotificationController2 = (SubscreenNotificationController) Dependency.sDependency.getDependencyInner(SubscreenNotificationController.class);
                String creatorPackage2 = pendingIntent2.getCreatorPackage();
                if (subscreenNotificationController2.mDeviceModel.isSubScreen()) {
                    subscreenNotificationController2.mDeviceModel.getClass();
                    if (ArraysKt___ArraysKt.indexOf(new String[]{"com.skt.prod.dialer", "com.samsung.android.incallui"}, creatorPackage2) >= 0) {
                        Log.d("NotifRemoteInputManager", "handle call notification clicked. start activity directly on subscreen. pkg: " + creatorPackage2);
                        RemoteViews.startPendingIntent(view, pendingIntent2, remoteResponse.getLaunchOptions(view));
                        return true;
                    }
                }
            }
            final ExpandableNotificationRow expandableNotificationRow2 = expandableNotificationRow;
            return ((StatusBarRemoteInputCallback) NotificationRemoteInputManager.this.mCallback).handleRemoteViewClick(pendingIntent2, action != null ? action.isAuthenticationRequired() : false, num, new ClickHandler() { // from class: com.android.systemui.statusbar.NotificationRemoteInputManager$1$$ExternalSyntheticLambda0
                @Override // com.android.systemui.statusbar.NotificationRemoteInputManager.ClickHandler
                public final boolean handleClick() {
                    RemoteViews.RemoteResponse remoteResponse2 = remoteResponse;
                    View view2 = view;
                    PendingIntent pendingIntent3 = pendingIntent2;
                    NotificationRemoteInputManager.AnonymousClass1 anonymousClass1 = this.f$0;
                    Pair launchOptions = remoteResponse2.getLaunchOptions(view2);
                    ActionClickLogger actionClickLogger2 = NotificationRemoteInputManager.this.mLogger;
                    ExpandableNotificationRow expandableNotificationRow3 = expandableNotificationRow2;
                    actionClickLogger2.logStartingIntentWithDefaultHandler(pendingIntent3, num, expandableNotificationRow3.mLoggingKey);
                    boolean zStartPendingIntent = RemoteViews.startPendingIntent(view2, pendingIntent3, launchOptions);
                    if (zStartPendingIntent) {
                        int i3 = NotificationBundleUi.$r8$clinit;
                        NotificationRemoteInputManager notificationRemoteInputManager2 = NotificationRemoteInputManager.this;
                        NotificationEntry entryLegacy = expandableNotificationRow3.getEntryLegacy();
                        notificationRemoteInputManager2.getClass();
                        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                        if (entryLegacy != null) {
                            RemoteInputCoordinator remoteInputCoordinator = notificationRemoteInputManager2.mRemoteInputListener;
                            if (remoteInputCoordinator != null) {
                                remoteInputCoordinator.releaseNotificationIfKeptForRemoteInputHistory(entryLegacy.mKey);
                            }
                            Iterator it = notificationRemoteInputManager2.mActionPressListeners.iterator();
                            while (it.hasNext()) {
                                ((Consumer) it.next()).accept(entryLegacy);
                            }
                        }
                    }
                    return zStartPendingIntent;
                }
            });
        }
    }

    public interface Callback {
    }

    public interface ClickHandler {
        boolean handleClick();
    }

    public NotificationRemoteInputManager(Context context, NotifPipelineFlags notifPipelineFlags, NotificationLockscreenUserManager notificationLockscreenUserManager, SmartReplyController smartReplyController, NotificationVisibilityProvider notificationVisibilityProvider, PowerInteractor powerInteractor, StatusBarStateController statusBarStateController, RemoteInputUriController remoteInputUriController, RemoteInputControllerLogger remoteInputControllerLogger, NotificationClickNotifier notificationClickNotifier, ActionClickLogger actionClickLogger, JavaAdapter javaAdapter, ShadeInteractor shadeInteractor, KeyguardStateController keyguardStateController) {
        this.mContext = context;
        this.mLockscreenUserManager = notificationLockscreenUserManager;
        this.mSmartReplyController = smartReplyController;
        this.mVisibilityProvider = notificationVisibilityProvider;
        this.mPowerInteractor = powerInteractor;
        this.mLogger = actionClickLogger;
        this.mJavaAdapter = javaAdapter;
        this.mShadeInteractor = shadeInteractor;
        this.mUserManager = (UserManager) context.getSystemService("user");
        this.mKeyguardManager = (KeyguardManager) context.getSystemService(KeyguardManager.class);
        this.mStatusBarStateController = statusBarStateController;
        this.mRemoteInputUriController = remoteInputUriController;
        this.mRemoteInputControllerLogger = remoteInputControllerLogger;
        this.mClickNotifier = notificationClickNotifier;
        this.mKeyguardStateController = keyguardStateController;
    }

    /* JADX WARN: Code restructure failed: missing block: B:39:0x0093, code lost:
    
        if (r5.mSecure == false) goto L40;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean activateRemoteInputOnExpanded(View view, RemoteInput[] remoteInputArr, RemoteInput remoteInput, PendingIntent pendingIntent, NotificationEntry.EditedSuggestionInfo editedSuggestionInfo) {
        ExpandableNotificationRow expandableNotificationRow;
        UserInfo profileParent;
        ViewParent parent = view.getParent();
        while (true) {
            if (parent == null) {
                expandableNotificationRow = null;
                break;
            }
            if (parent instanceof View) {
                View view2 = (View) parent;
                if (view2.getId() == 16909885) {
                    expandableNotificationRow = (ExpandableNotificationRow) view2.getTag(com.android.systemui.R.id.row_tag_for_content_view);
                    break;
                }
            }
            parent = parent.getParent();
        }
        if (expandableNotificationRow != null) {
            int identifier = pendingIntent.getCreatorUserHandle().getIdentifier();
            boolean z = (this.mUserManager.getUserInfo(identifier).isManagedProfile() || this.mUserManager.getUserInfo(identifier).isPrivateProfile()) && this.mKeyguardManager.isDeviceLocked(identifier);
            boolean z2 = z && (profileParent = this.mUserManager.getProfileParent(identifier)) != null && this.mKeyguardManager.isDeviceLocked(profileParent.id);
            NotificationLockscreenUserManagerImpl notificationLockscreenUserManagerImpl = (NotificationLockscreenUserManagerImpl) this.mLockscreenUserManager;
            if (notificationLockscreenUserManagerImpl.isLockscreenPublicMode(identifier) || this.mStatusBarStateController.getState() == 1) {
                if (notificationLockscreenUserManagerImpl.isLockscreenPublicMode(identifier)) {
                    KeyguardStateControllerImpl keyguardStateControllerImpl = (KeyguardStateControllerImpl) this.mKeyguardStateController;
                    if (keyguardStateControllerImpl.mOccluded) {
                    }
                }
                if (!z || z2) {
                    ((StatusBarRemoteInputCallback) this.mCallback).onLockedRemoteInput(view, expandableNotificationRow);
                    return true;
                }
                StatusBarRemoteInputCallback statusBarRemoteInputCallback = (StatusBarRemoteInputCallback) this.mCallback;
                statusBarRemoteInputCallback.mCommandQueue.animateCollapsePanels();
                statusBarRemoteInputCallback.startWorkChallengeIfNecessary(identifier, null, null);
                statusBarRemoteInputCallback.mPendingWorkRemoteInputView = view;
                return true;
            }
            if (z) {
                StatusBarRemoteInputCallback statusBarRemoteInputCallback2 = (StatusBarRemoteInputCallback) this.mCallback;
                statusBarRemoteInputCallback2.mCommandQueue.animateCollapsePanels();
                statusBarRemoteInputCallback2.startWorkChallengeIfNecessary(identifier, null, null);
                statusBarRemoteInputCallback2.mPendingWorkRemoteInputView = view;
                return true;
            }
            if (expandableNotificationRow.mPrivateLayout.mExpandedChild.isShown()) {
                View view3 = expandableNotificationRow.mPrivateLayout.mExpandedChild;
                RemoteInputView remoteInputView = view3 == null ? null : (RemoteInputView) view3.findViewWithTag(RemoteInputView.VIEW_TAG);
                if (remoteInputView != null && remoteInputView.isAttachedToWindow()) {
                    RemoteInputViewControllerImpl remoteInputViewControllerImpl = (RemoteInputViewControllerImpl) remoteInputView.mViewController;
                    remoteInputViewControllerImpl.pendingIntent = pendingIntent;
                    remoteInputViewControllerImpl.setRemoteInput(remoteInput);
                    RemoteInputViewControllerImpl remoteInputViewControllerImpl2 = (RemoteInputViewControllerImpl) remoteInputView.mViewController;
                    remoteInputViewControllerImpl2.remoteInputs = remoteInputArr;
                    NotificationEntry notificationEntry = remoteInputViewControllerImpl2.entry;
                    notificationEntry.editedSuggestionInfo = editedSuggestionInfo;
                    if (editedSuggestionInfo != null) {
                        notificationEntry.remoteInputText = editedSuggestionInfo.originalText;
                        notificationEntry.remoteInputAttachment = null;
                    }
                    if (remoteInputView.getVisibility() != 0) {
                        remoteInputView.setAlpha(0.0f);
                        ViewGroup viewGroup = (ViewGroup) remoteInputView.getParent();
                        View viewFindViewById = viewGroup != null ? viewGroup.findViewById(R.id.anyRtl) : null;
                        AnimatorSet animatorSet = new AnimatorSet();
                        Property property = View.ALPHA;
                        ObjectAnimator objectAnimatorOfFloat = ObjectAnimator.ofFloat(remoteInputView, property, 0.0f, 1.0f);
                        objectAnimatorOfFloat.setStartDelay(33L);
                        objectAnimatorOfFloat.setDuration(83L);
                        LinearInterpolator linearInterpolator = InterpolatorsAndroidX.LINEAR;
                        objectAnimatorOfFloat.setInterpolator(linearInterpolator);
                        ValueAnimator valueAnimatorOfFloat = ValueAnimator.ofFloat(0.5f, 1.0f);
                        valueAnimatorOfFloat.addUpdateListener(new RemoteInputView$$ExternalSyntheticLambda2(remoteInputView, valueAnimatorOfFloat, 1));
                        valueAnimatorOfFloat.setDuration(360L);
                        valueAnimatorOfFloat.setInterpolator(InterpolatorsAndroidX.FAST_OUT_SLOW_IN);
                        if (viewFindViewById == null) {
                            animatorSet.playTogether(objectAnimatorOfFloat, valueAnimatorOfFloat);
                        } else {
                            ObjectAnimator objectAnimatorOfFloat2 = ObjectAnimator.ofFloat(viewFindViewById, property, 1.0f, 0.0f);
                            objectAnimatorOfFloat2.setDuration(50L);
                            objectAnimatorOfFloat2.setInterpolator(linearInterpolator);
                            animatorSet.addListener(new AnimatorListenerAdapter(remoteInputView, viewFindViewById) { // from class: com.android.systemui.statusbar.policy.RemoteInputView.4
                                public final /* synthetic */ View val$fadeOutView;

                                public AnonymousClass4(RemoteInputView remoteInputView2, View viewFindViewById2) {
                                    this.val$fadeOutView = viewFindViewById2;
                                }

                                @Override // androidx.core.animation.Animator.AnimatorListener
                                public final void onAnimationEnd(Animator animator, boolean z3) {
                                    this.val$fadeOutView.setAlpha(1.0f);
                                }
                            });
                            animatorSet.playTogether(objectAnimatorOfFloat, valueAnimatorOfFloat, objectAnimatorOfFloat2);
                        }
                        animatorSet.addListener(new AnimatorListenerAdapter() { // from class: com.android.systemui.statusbar.policy.RemoteInputView.3
                            public AnonymousClass3() {
                            }

                            @Override // androidx.core.animation.Animator.AnimatorListener
                            public final void onAnimationEnd(Animator animator, boolean z3) {
                                RemoteInputView.this.getClass();
                            }
                        });
                        animatorSet.start();
                    }
                    remoteInputView2.focus();
                }
            } else {
                Callback callback = this.mCallback;
                NotificationRemoteInputManager$$ExternalSyntheticLambda0 notificationRemoteInputManager$$ExternalSyntheticLambda0 = new NotificationRemoteInputManager$$ExternalSyntheticLambda0(this, view, remoteInputArr, remoteInput, pendingIntent, editedSuggestionInfo);
                StatusBarRemoteInputCallback statusBarRemoteInputCallback3 = (StatusBarRemoteInputCallback) callback;
                if (((KeyguardStateControllerImpl) statusBarRemoteInputCallback3.mKeyguardStateController).mShowing) {
                    statusBarRemoteInputCallback3.onLockedRemoteInput(view, expandableNotificationRow);
                    return true;
                }
                int i = ExpandHeadsUpOnInlineReply.$r8$clinit;
                if (expandableNotificationRow.isChildInGroup() && !expandableNotificationRow.mChildrenExpanded) {
                    int i2 = NotificationBundleUi.$r8$clinit;
                    NotificationEntry entryLegacy = expandableNotificationRow.getEntryLegacy();
                    GroupExpansionManagerImpl groupExpansionManagerImpl = (GroupExpansionManagerImpl) statusBarRemoteInputCallback3.mGroupExpansionManager;
                    groupExpansionManagerImpl.getClass();
                    RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
                    groupExpansionManagerImpl.setGroupExpanded(entryLegacy, !groupExpansionManagerImpl.isGroupExpanded(entryLegacy));
                    groupExpansionManagerImpl.isGroupExpanded(entryLegacy);
                    return true;
                }
                if (!expandableNotificationRow.isChildInGroup()) {
                    if (!(expandableNotificationRow.mPinnedStatus.isPinned() ? !expandableNotificationRow.mPinnedStatus.isPinned() ? false : expandableNotificationRow.mExpandedWhenPinned : expandableNotificationRow.isExpanded(false))) {
                        expandableNotificationRow.toggleExpansionState(expandableNotificationRow, false);
                        NotificationContentView notificationContentView = expandableNotificationRow.mPrivateLayout;
                        notificationContentView.mExpandedVisibleListener = notificationRemoteInputManager$$ExternalSyntheticLambda0;
                        notificationContentView.fireExpandedVisibleListenerIfVisible();
                        return true;
                    }
                }
            }
            return true;
        }
        return false;
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

    @Override // com.android.systemui.CoreStartable, com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        final PrintWriter printWriterAsIndenting = DumpUtilsKt.asIndenting(printWriter);
        if (this.mRemoteInputController != null) {
            printWriterAsIndenting.println("mRemoteInputController: " + this.mRemoteInputController);
            printWriterAsIndenting.increaseIndent();
            final RemoteInputController remoteInputController = this.mRemoteInputController;
            remoteInputController.getClass();
            printWriterAsIndenting.print("mLastAppliedRemoteInputActive: ");
            printWriterAsIndenting.println(remoteInputController.mLastAppliedRemoteInputActive);
            printWriterAsIndenting.print("isRemoteInputActive: ");
            printWriterAsIndenting.println(remoteInputController.isRemoteInputActive$1());
            printWriterAsIndenting.println("mOpen: " + remoteInputController.mOpen.size());
            final int i = 0;
            DumpUtilsKt.withIncreasedIndent((IndentingPrintWriter) printWriterAsIndenting, new Runnable() { // from class: com.android.systemui.statusbar.RemoteInputController$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    switch (i) {
                        case 0:
                            RemoteInputController remoteInputController2 = remoteInputController;
                            IndentingPrintWriter indentingPrintWriter = printWriterAsIndenting;
                            ArrayList arrayList = remoteInputController2.mOpen;
                            int size = arrayList.size();
                            int i2 = 0;
                            while (i2 < size) {
                                Object obj = arrayList.get(i2);
                                i2++;
                                NotificationEntry notificationEntry = (NotificationEntry) ((WeakReference) ((Pair) obj).first).get();
                                indentingPrintWriter.println(notificationEntry == null ? "???" : notificationEntry.mKey);
                            }
                            break;
                        default:
                            RemoteInputController remoteInputController3 = remoteInputController;
                            IndentingPrintWriter indentingPrintWriter2 = printWriterAsIndenting;
                            Iterator it = remoteInputController3.mSpinning.keySet().iterator();
                            while (it.hasNext()) {
                                indentingPrintWriter2.println((String) it.next());
                            }
                            break;
                    }
                }
            });
            printWriterAsIndenting.println("mSpinning: " + remoteInputController.mSpinning.size());
            final int i2 = 1;
            DumpUtilsKt.withIncreasedIndent((IndentingPrintWriter) printWriterAsIndenting, new Runnable() { // from class: com.android.systemui.statusbar.RemoteInputController$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    switch (i2) {
                        case 0:
                            RemoteInputController remoteInputController2 = remoteInputController;
                            IndentingPrintWriter indentingPrintWriter = printWriterAsIndenting;
                            ArrayList arrayList = remoteInputController2.mOpen;
                            int size = arrayList.size();
                            int i22 = 0;
                            while (i22 < size) {
                                Object obj = arrayList.get(i22);
                                i22++;
                                NotificationEntry notificationEntry = (NotificationEntry) ((WeakReference) ((Pair) obj).first).get();
                                indentingPrintWriter.println(notificationEntry == null ? "???" : notificationEntry.mKey);
                            }
                            break;
                        default:
                            RemoteInputController remoteInputController3 = remoteInputController;
                            IndentingPrintWriter indentingPrintWriter2 = printWriterAsIndenting;
                            Iterator it = remoteInputController3.mSpinning.keySet().iterator();
                            while (it.hasNext()) {
                                indentingPrintWriter2.println((String) it.next());
                            }
                            break;
                    }
                }
            });
            printWriterAsIndenting.println(remoteInputController.mSpinning);
            printWriterAsIndenting.print("mDelegate: ");
            printWriterAsIndenting.println(remoteInputController.mDelegate);
            printWriterAsIndenting.decreaseIndent();
        }
        RemoteInputCoordinator remoteInputCoordinator = this.mRemoteInputListener;
        if (remoteInputCoordinator != null) {
            printWriterAsIndenting.println("mRemoteInputListener: ".concat(remoteInputCoordinator.getClass().getSimpleName()));
            printWriterAsIndenting.increaseIndent();
            this.mRemoteInputListener.dump(printWriterAsIndenting, strArr);
            printWriterAsIndenting.decreaseIndent();
        }
    }

    public final boolean isRemoteInputActive() {
        RemoteInputController remoteInputController = this.mRemoteInputController;
        return remoteInputController != null && remoteInputController.isRemoteInputActive$1();
    }

    public final boolean isSpinning(String str) {
        RemoteInputController remoteInputController = this.mRemoteInputController;
        return remoteInputController != null && remoteInputController.mSpinning.containsKey(str);
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        this.mJavaAdapter.alwaysCollectFlow(((ShadeInteractorImpl) this.mShadeInteractor).baseShadeInteractor.isAnyExpanded(), new Consumer() { // from class: com.android.systemui.statusbar.NotificationRemoteInputManager$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                NotificationRemoteInputManager notificationRemoteInputManager = this.f$0;
                boolean zBooleanValue = ((Boolean) obj).booleanValue();
                if (!zBooleanValue) {
                    boolean z = NotificationRemoteInputManager.ENABLE_REMOTE_INPUT;
                } else if (notificationRemoteInputManager.mStatusBarStateController.getState() != 1) {
                    try {
                        notificationRemoteInputManager.mBarService.clearNotificationEffects();
                    } catch (RemoteException unused) {
                    }
                }
                if (zBooleanValue) {
                    notificationRemoteInputManager.getClass();
                    return;
                }
                RemoteInputCoordinator remoteInputCoordinator = notificationRemoteInputManager.mRemoteInputListener;
                if (remoteInputCoordinator != null) {
                    remoteInputCoordinator.onPanelCollapsed();
                }
            }
        });
    }
}
