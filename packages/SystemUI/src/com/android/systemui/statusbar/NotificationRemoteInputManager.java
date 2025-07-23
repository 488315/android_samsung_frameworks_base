package com.android.systemui.statusbar;

import android.app.KeyguardManager;
import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.os.UserManager;
import android.util.IndentingPrintWriter;
import android.util.Pair;
import android.widget.RemoteViews;
import com.android.internal.statusbar.IStatusBarService;
import com.android.systemui.CoreStartable;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.power.domain.interactor.PowerInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractor;
import com.android.systemui.shade.domain.interactor.ShadeInteractorImpl;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.RemoteInputControllerLogger;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinator;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.RemoteInputUriController;
import com.android.systemui.util.DumpUtilsKt;
import com.android.systemui.util.ListenerSet;
import com.android.systemui.util.kotlin.JavaAdapter;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    /* renamed from: com.android.systemui.statusbar.NotificationRemoteInputManager$1, reason: invalid class name */
    public class AnonymousClass1 implements RemoteViews.InteractionHandler {
        public AnonymousClass1() {
        }

        /* JADX WARN: Can't wrap try/catch for region: R(15:48|(1:50)|51|(3:87|(1:89)(1:101)|(1:91)(2:92|(1:100)(2:96|(1:98)(11:99|54|(4:56|(1:64)(1:61)|62|63)|65|66|67|(2:71|(3:73|(1:75)(1:79)|(2:77|78)))|80|(1:82)|83|84))))|53|54|(0)|65|66|67|(3:69|71|(0))|80|(0)|83|84) */
        /* JADX WARN: Removed duplicated region for block: B:56:0x0197  */
        /* JADX WARN: Removed duplicated region for block: B:73:0x0202  */
        /* JADX WARN: Removed duplicated region for block: B:82:0x023a  */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final boolean onInteraction(final android.view.View r22, android.app.PendingIntent r23, final android.widget.RemoteViews.RemoteResponse r24) {
            /*
                Method dump skipped, instructions count: 590
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.NotificationRemoteInputManager.AnonymousClass1.onInteraction(android.view.View, android.app.PendingIntent, android.widget.RemoteViews$RemoteResponse):boolean");
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Callback {
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Code restructure failed: missing block: B:83:0x0093, code lost:
    
        if (r5.mSecure == false) goto L40;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean activateRemoteInputOnExpanded(android.view.View r12, android.app.RemoteInput[] r13, android.app.RemoteInput r14, android.app.PendingIntent r15, com.android.systemui.statusbar.notification.collection.NotificationEntry.EditedSuggestionInfo r16) {
        /*
            Method dump skipped, instructions count: 536
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.statusbar.NotificationRemoteInputManager.activateRemoteInputOnExpanded(android.view.View, android.app.RemoteInput[], android.app.RemoteInput, android.app.PendingIntent, com.android.systemui.statusbar.notification.collection.NotificationEntry$EditedSuggestionInfo):boolean");
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
                            IndentingPrintWriter indentingPrintWriter2 = asIndenting;
                            Iterator it = remoteInputController3.mSpinning.keySet().iterator();
                            while (it.hasNext()) {
                                indentingPrintWriter2.println((String) it.next());
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
                            IndentingPrintWriter indentingPrintWriter2 = asIndenting;
                            Iterator it = remoteInputController3.mSpinning.keySet().iterator();
                            while (it.hasNext()) {
                                indentingPrintWriter2.println((String) it.next());
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
        if (remoteInputCoordinator != null) {
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

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        this.mJavaAdapter.alwaysCollectFlow(((ShadeInteractorImpl) this.mShadeInteractor).baseShadeInteractor.isAnyExpanded(), new Consumer() { // from class: com.android.systemui.statusbar.NotificationRemoteInputManager$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                NotificationRemoteInputManager notificationRemoteInputManager = NotificationRemoteInputManager.this;
                boolean booleanValue = ((Boolean) obj).booleanValue();
                if (!booleanValue) {
                    boolean z = NotificationRemoteInputManager.ENABLE_REMOTE_INPUT;
                } else if (notificationRemoteInputManager.mStatusBarStateController.getState() != 1) {
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
}
