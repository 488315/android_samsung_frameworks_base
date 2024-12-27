package com.android.systemui.statusbar;

import android.R;
import android.app.ActivityManager;
import android.app.Flags;
import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.PendingIntent;
import android.app.RemoteInput;
import android.content.Context;
import android.content.Intent;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemClock;
import android.os.SystemProperties;
import android.os.UserManager;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.IndentingPrintWriter;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.widget.RemoteViews;
import androidx.appcompat.util.SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0;
import com.android.app.tracing.coroutines.TraceData$$ExternalSyntheticOutline0;
import com.android.internal.statusbar.IStatusBarService;
import com.android.internal.statusbar.NotificationVisibility;
import com.android.systemui.CoreStartable;
import com.android.systemui.Dependency;
import com.android.systemui.Dumpable;
import com.android.systemui.LsRune;
import com.android.systemui.NotiRune;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.statusbar.RemoteInputController;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.RemoteInputControllerLogger;
import com.android.systemui.statusbar.notification.SubscreenNotificationController;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinator;
import com.android.systemui.statusbar.notification.collection.provider.NotificationVisibilityProviderImpl;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.phone.StatusBarRemoteInputCallback;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityDataHelper;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.RemoteInputUriController;
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
import kotlin.jvm.functions.Function1;

public final class NotificationRemoteInputManager implements CoreStartable {
    public static final boolean ENABLE_REMOTE_INPUT = SystemProperties.getBoolean("debug.enable_remote_input", true);
    public static final boolean FORCE_REMOTE_INPUT_HISTORY = SystemProperties.getBoolean("debug.force_remoteinput_history", false);
    public final ActivityManager mActivityManager;
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
    public final class AnonymousClass1 implements RemoteViews.InteractionHandler {
        public AnonymousClass1() {
        }

        public static Notification.Action getActionFromView(View view, NotificationEntry notificationEntry, PendingIntent pendingIntent) {
            Integer num = (Integer) view.getTag(R.id.pin_new_text);
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
            boolean activateRemoteInput;
            NotificationListenerService.Ranking ranking;
            NotificationChannel channel;
            NotificationRemoteInputManager.this.mPowerInteractor.wakeUpIfDozing(4, "NOTIFICATION_CLICK");
            final Integer num = (Integer) view.getTag(R.id.pin_new_text);
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
                    int int1 = logMessage.getInt1();
                    StringBuilder m = SeslRoundedCorner$SeslRoundedChunkingDrawable$$ExternalSyntheticOutline0.m("ACTION CLICK ", str1, " (channel=", str2, ") for pending intent ");
                    m.append(str3);
                    m.append(" at index ");
                    m.append(int1);
                    return m.toString();
                }
            };
            LogBuffer logBuffer = actionClickLogger.buffer;
            LogMessage obtain = logBuffer.obtain("ActionClickLogger", logLevel, actionClickLogger$logInitialClick$2, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
            logMessageImpl.str1 = notificationEntry != null ? notificationEntry.mKey : null;
            logMessageImpl.str2 = (notificationEntry == null || (ranking = notificationEntry.mRanking) == null || (channel = ranking.getChannel()) == null) ? null : channel.getId();
            logMessageImpl.str3 = pendingIntent.toString();
            logMessageImpl.int1 = num != null ? num.intValue() : Integer.MIN_VALUE;
            logBuffer.commit(obtain);
            if ((((StatusBarRemoteInputCallback) NotificationRemoteInputManager.this.mCallback).mDisabled2 & 4) != 0) {
                activateRemoteInput = true;
            } else {
                Object tag = view.getTag(R.id.simple);
                RemoteInput[] remoteInputArr = tag instanceof RemoteInput[] ? (RemoteInput[]) tag : null;
                if (remoteInputArr != null) {
                    RemoteInput remoteInput = null;
                    for (RemoteInput remoteInput2 : remoteInputArr) {
                        if (remoteInput2.getAllowFreeFormInput()) {
                            remoteInput = remoteInput2;
                        }
                    }
                    if (remoteInput != null) {
                        activateRemoteInput = NotificationRemoteInputManager.this.activateRemoteInput(view, remoteInputArr, remoteInput, pendingIntent, null);
                    }
                }
                activateRemoteInput = false;
            }
            if (activateRemoteInput) {
                if (notificationEntry != null) {
                    SystemUIAnalytics.sendEventCDLog(SystemUIAnalytics.SID_QUICKPANEL_OPENED, SystemUIAnalytics.EID_QPNE_REPLY_BUTTON, SystemUIAnalytics.QPNE_KEY_APP, notificationEntry.mSbn.getPackageName());
                }
                ActionClickLogger actionClickLogger2 = NotificationRemoteInputManager.this.mLogger;
                actionClickLogger2.getClass();
                LogLevel logLevel2 = LogLevel.DEBUG;
                ActionClickLogger$logRemoteInputWasHandled$2 actionClickLogger$logRemoteInputWasHandled$2 = new Function1() { // from class: com.android.systemui.statusbar.ActionClickLogger$logRemoteInputWasHandled$2
                    @Override // kotlin.jvm.functions.Function1
                    public final Object invoke(Object obj) {
                        LogMessage logMessage = (LogMessage) obj;
                        return TraceData$$ExternalSyntheticOutline0.m(logMessage.getInt1(), "  [Action click] Triggered remote input (for ", logMessage.getStr1(), ") at index ");
                    }
                };
                LogBuffer logBuffer2 = actionClickLogger2.buffer;
                LogMessage obtain2 = logBuffer2.obtain("ActionClickLogger", logLevel2, actionClickLogger$logRemoteInputWasHandled$2, null);
                LogMessageImpl logMessageImpl2 = (LogMessageImpl) obtain2;
                logMessageImpl2.str1 = notificationEntry != null ? notificationEntry.mKey : null;
                logMessageImpl2.int1 = num != null ? num.intValue() : Integer.MIN_VALUE;
                logBuffer2.commit(obtain2);
                return true;
            }
            if (notificationEntry == null) {
                Context context = NotificationRemoteInputManager.this.mContext;
                OngoingActivityDataHelper.INSTANCE.getClass();
                NotificationSAUtil.sendOALog(context, SystemUIAnalytics.OAID_ONGOING_ACTION_BUTTONS, OngoingActivityDataHelper.getDataByIndex(0).mNotificationEntry);
            } else {
                NotificationSAUtil.sendTypeLog(SystemUIAnalytics.EID_QPNE_NOTI_ACTION_BUTTON, notificationEntry);
            }
            Notification.Action actionFromView = getActionFromView(view, notificationEntry, pendingIntent);
            if (actionFromView != null) {
                ViewParent parent2 = view.getParent();
                String key = notificationEntry.mSbn.getKey();
                int indexOfChild = (view.getId() == 16908725 && parent2 != null && (parent2 instanceof ViewGroup)) ? ((ViewGroup) parent2).indexOfChild(view) : -1;
                NotificationVisibility obtain3 = ((NotificationVisibilityProviderImpl) NotificationRemoteInputManager.this.mVisibilityProvider).obtain(notificationEntry);
                NotificationClickNotifier notificationClickNotifier = NotificationRemoteInputManager.this.mClickNotifier;
                notificationClickNotifier.backgroundExecutor.execute(new NotificationClickNotifier$onNotificationActionClick$1(notificationClickNotifier, key, indexOfChild, actionFromView, obtain3, false));
                if (!Flags.lifetimeExtensionRefactor()) {
                    notificationClickNotifier.mainExecutor.execute(new NotificationClickNotifier$onNotificationActionClick$2(notificationClickNotifier, key));
                }
            }
            try {
                ActivityManager.getService().resumeAppSwitches();
            } catch (RemoteException unused) {
            }
            if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_FIFTH && pendingIntent.isActivity()) {
                SubscreenNotificationController subscreenNotificationController = (SubscreenNotificationController) Dependency.sDependency.getDependencyInner(SubscreenNotificationController.class);
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
            final NotificationRemoteInputManager$1$$ExternalSyntheticLambda0 notificationRemoteInputManager$1$$ExternalSyntheticLambda0 = new NotificationRemoteInputManager$1$$ExternalSyntheticLambda0(this, remoteResponse, view, notificationEntry, pendingIntent, num);
            final StatusBarRemoteInputCallback statusBarRemoteInputCallback = (StatusBarRemoteInputCallback) callback;
            statusBarRemoteInputCallback.getClass();
            if (!pendingIntent.isActivity() && !isAuthenticationRequired) {
                return notificationRemoteInputManager$1$$ExternalSyntheticLambda0.handleClick();
            }
            ActionClickLogger actionClickLogger3 = statusBarRemoteInputCallback.mActionClickLogger;
            actionClickLogger3.getClass();
            LogLevel logLevel3 = LogLevel.DEBUG;
            ActionClickLogger$logWaitingToCloseKeyguard$2 actionClickLogger$logWaitingToCloseKeyguard$2 = new Function1() { // from class: com.android.systemui.statusbar.ActionClickLogger$logWaitingToCloseKeyguard$2
                @Override // kotlin.jvm.functions.Function1
                public final Object invoke(Object obj) {
                    LogMessage logMessage = (LogMessage) obj;
                    return "  [Action click] Intent " + logMessage.getStr1() + " at index " + logMessage.getInt1() + " launches an activity, dismissing keyguard first...";
                }
            };
            LogBuffer logBuffer3 = actionClickLogger3.buffer;
            LogMessage obtain4 = logBuffer3.obtain("ActionClickLogger", logLevel3, actionClickLogger$logWaitingToCloseKeyguard$2, null);
            ((LogMessageImpl) obtain4).str1 = pendingIntent.toString();
            ((LogMessageImpl) obtain4).int1 = num != null ? num.intValue() : Integer.MIN_VALUE;
            logBuffer3.commit(obtain4);
            boolean z = statusBarRemoteInputCallback.mActivityIntentHelper.getPendingTargetActivityInfo(((NotificationLockscreenUserManagerImpl) statusBarRemoteInputCallback.mLockscreenUserManager).mCurrentUserId, pendingIntent) == null;
            if (LsRune.SECURITY_SWIPE_BOUNCER) {
                statusBarRemoteInputCallback.mStatusBarKeyguardViewManager.setShowSwipeBouncer(true);
            }
            statusBarRemoteInputCallback.mActivityStarter.dismissKeyguardThenExecute(new ActivityStarter.OnDismissAction() { // from class: com.android.systemui.statusbar.phone.StatusBarRemoteInputCallback$$ExternalSyntheticLambda0
                @Override // com.android.systemui.plugins.ActivityStarter.OnDismissAction
                public final boolean onDismiss() {
                    PendingIntent pendingIntent2 = pendingIntent;
                    StatusBarRemoteInputCallback statusBarRemoteInputCallback2 = StatusBarRemoteInputCallback.this;
                    statusBarRemoteInputCallback2.mActionClickLogger.logKeyguardGone(pendingIntent2, num);
                    try {
                        ActivityManager.getService().resumeAppSwitches();
                    } catch (RemoteException unused2) {
                    }
                    if (!notificationRemoteInputManager$1$$ExternalSyntheticLambda0.handleClick()) {
                        return false;
                    }
                    statusBarRemoteInputCallback2.mShadeController.closeShadeIfOpen();
                    return false;
                }
            }, null, z);
            return true;
        }
    }

    public interface Callback {
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
        this.mActivityManager = (ActivityManager) context.getSystemService("activity");
        this.mKeyguardManager = (KeyguardManager) context.getSystemService(KeyguardManager.class);
        this.mStatusBarStateController = statusBarStateController;
        this.mRemoteInputUriController = remoteInputUriController;
        this.mRemoteInputControllerLogger = remoteInputControllerLogger;
        this.mClickNotifier = notificationClickNotifier;
        this.mKeyguardStateController = keyguardStateController;
    }

    /* JADX WARN: Code restructure failed: missing block: B:105:0x0096, code lost:
    
        if (r12.mSecure == false) goto L38;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean activateRemoteInput(final android.view.View r15, final android.app.RemoteInput[] r16, final android.app.RemoteInput r17, final android.app.PendingIntent r18, final com.android.systemui.statusbar.notification.collection.NotificationEntry.EditedSuggestionInfo r19) {
        /*
            Method dump skipped, instructions count: 638
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.NotificationRemoteInputManager.activateRemoteInput(android.view.View, android.app.RemoteInput[], android.app.RemoteInput, android.app.PendingIntent, com.android.systemui.statusbar.notification.collection.NotificationEntry$EditedSuggestionInfo):boolean");
    }

    public final void addControllerCallback(RemoteInputController.Callback callback) {
        RemoteInputController remoteInputController = this.mRemoteInputController;
        if (remoteInputController == null) {
            ((ArrayList) this.mControllerCallbacks).add(callback);
        } else {
            Objects.requireNonNull(callback);
            remoteInputController.mCallbacks.add(callback);
        }
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
            DumpUtilsKt.withIncreasedIndent((IndentingPrintWriter) asIndenting, new Runnable() { // from class: com.android.systemui.statusbar.RemoteInputController$$ExternalSyntheticLambda0
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
            asIndenting.println("mSpinning: " + remoteInputController.mSpinning.size());
            final int i2 = 1;
            DumpUtilsKt.withIncreasedIndent((IndentingPrintWriter) asIndenting, new Runnable() { // from class: com.android.systemui.statusbar.RemoteInputController$$ExternalSyntheticLambda0
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
            asIndenting.println(remoteInputController.mSpinning);
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

    public final boolean isRemoteInputActive() {
        RemoteInputController remoteInputController = this.mRemoteInputController;
        return remoteInputController != null && remoteInputController.isRemoteInputActive$1();
    }

    public final boolean isSpinning(String str) {
        RemoteInputController remoteInputController = this.mRemoteInputController;
        return remoteInputController != null && remoteInputController.mSpinning.containsKey(str);
    }

    public final boolean shouldKeepForRemoteInputHistory(NotificationEntry notificationEntry) {
        if (FORCE_REMOTE_INPUT_HISTORY) {
            return isSpinning(notificationEntry.mKey) || SystemClock.elapsedRealtime() < notificationEntry.lastRemoteInputSent + 500;
        }
        return false;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        this.mJavaAdapter.alwaysCollectFlow(((ShadeInteractorImpl) this.mShadeInteractor).baseShadeInteractor.isAnyExpanded(), new Consumer() { // from class: com.android.systemui.statusbar.NotificationRemoteInputManager$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                NotificationRemoteInputManager notificationRemoteInputManager = NotificationRemoteInputManager.this;
                boolean booleanValue = ((Boolean) obj).booleanValue();
                if (booleanValue && notificationRemoteInputManager.mStatusBarStateController.getState() != 1) {
                    try {
                        notificationRemoteInputManager.mBarService.clearNotificationEffects();
                    } catch (RemoteException unused) {
                    }
                }
                if (booleanValue) {
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
