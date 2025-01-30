package com.android.systemui.wmshell;

import android.app.INotificationManager;
import android.app.NotificationChannel;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.provider.Settings;
import android.service.dreams.IDreamManager;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import androidx.appcompat.widget.ListPopupWindow$$ExternalSyntheticOutline0;
import androidx.exifinterface.media.ExifInterface$$ExternalSyntheticOutline0;
import com.android.internal.config.sysui.SystemUiSystemPropertiesFlags;
import com.android.internal.statusbar.IStatusBarService;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.p038wm.shell.bubbles.BubbleController;
import com.android.p038wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda11;
import com.android.p038wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda15;
import com.android.p038wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda19;
import com.android.p038wm.shell.bubbles.BubbleController$BubblesImpl$$ExternalSyntheticLambda2;
import com.android.p038wm.shell.bubbles.BubbleController$BubblesImpl$$ExternalSyntheticLambda3;
import com.android.p038wm.shell.bubbles.BubbleController$BubblesImpl$$ExternalSyntheticLambda6;
import com.android.p038wm.shell.bubbles.BubbleEntry;
import com.android.p038wm.shell.bubbles.Bubbles;
import com.android.p038wm.shell.common.HandlerExecutor;
import com.android.systemui.Dependency;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.model.SysUiState;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.shade.ShadeControllerImpl;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.NotificationChannelHelper;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.interruption.NotificationInterruptStateProviderWrapper;
import com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.phone.StatusBarWindowCallback;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.statusbar.policy.ZenModeControllerImpl;
import com.android.systemui.util.DesktopManager;
import com.android.systemui.util.DesktopManagerImpl;
import com.samsung.android.desktopmode.SemDesktopModeState;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class BubblesManager {
    public final IStatusBarService mBarService;
    public final C37427 mBroadcastReceiver;
    public final Bubbles mBubbles;
    public final CommonNotifCollection mCommonNotifCollection;
    public final Context mContext;
    public final NotifPipelineFlags mNotifPipelineFlags;
    public final NotificationLockscreenUserManager mNotifUserManager;
    public final INotificationManager mNotificationManager;
    public final NotificationShadeWindowController mNotificationShadeWindowController;
    public final ShadeController mShadeController;
    public final StatusBarWindowCallback mStatusBarWindowCallback;
    public final Executor mSysuiMainExecutor;
    public final NotificationVisibilityProvider mVisibilityProvider;
    public final VisualInterruptionDecisionProvider mVisualInterruptionDecisionProvider;
    public final List mCallbacks = new ArrayList();
    public boolean mIsScreenUnlocked = true;
    public final HashMap mShouldBubbleUpEntry = new HashMap();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    /* renamed from: com.android.systemui.wmshell.BubblesManager$4 */
    public final class C37394 {
        public final /* synthetic */ SysUiState val$sysUiState;
        public final /* synthetic */ Executor val$sysuiMainExecutor;

        public C37394(Executor executor, SysUiState sysUiState) {
            this.val$sysuiMainExecutor = executor;
            this.val$sysUiState = sysUiState;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r6v3, types: [android.content.BroadcastReceiver, com.android.systemui.wmshell.BubblesManager$7] */
    public BubblesManager(Context context, final Bubbles bubbles, NotificationShadeWindowController notificationShadeWindowController, final KeyguardStateController keyguardStateController, ShadeController shadeController, IStatusBarService iStatusBarService, INotificationManager iNotificationManager, IDreamManager iDreamManager, NotificationVisibilityProvider notificationVisibilityProvider, VisualInterruptionDecisionProvider visualInterruptionDecisionProvider, ZenModeController zenModeController, NotificationLockscreenUserManager notificationLockscreenUserManager, CommonNotifCollection commonNotifCollection, NotifPipeline notifPipeline, SysUiState sysUiState, FeatureFlags featureFlags, NotifPipelineFlags notifPipelineFlags, Executor executor, BroadcastDispatcher broadcastDispatcher) {
        ?? r6 = new BroadcastReceiver() { // from class: com.android.systemui.wmshell.BubblesManager.7
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                if (!"com.samsung.intent.action.EMERGENCY_STATE_CHANGED".equals(action)) {
                    if ("com.samsung.android.action.LOCK_TASK_MODE".equals(action)) {
                        Log.d("BubblesManager", "Dismiss all bubbles - reason : PIN mode detected.");
                        BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) BubblesManager.this.mBubbles;
                        ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new BubbleController$BubblesImpl$$ExternalSyntheticLambda6(bubblesImpl, r1));
                        return;
                    }
                    return;
                }
                int i = 1;
                if ((intent.getIntExtra("reason", 0) == 5 ? 1 : 0) == 0) {
                    Log.d("BubblesManager", "Dismiss bubble because MW disabled");
                    BubbleController.BubblesImpl bubblesImpl2 = (BubbleController.BubblesImpl) BubblesManager.this.mBubbles;
                    ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new BubbleController$BubblesImpl$$ExternalSyntheticLambda6(bubblesImpl2, i));
                }
                Iterator it = ((NotifPipeline) BubblesManager.this.mCommonNotifCollection).getAllNotifs().iterator();
                while (it.hasNext()) {
                    ExpandableNotificationRow expandableNotificationRow = ((NotificationEntry) it.next()).row;
                    if (expandableNotificationRow != null) {
                        expandableNotificationRow.updateBubbleButton();
                    }
                }
            }
        };
        this.mBroadcastReceiver = r6;
        this.mContext = context;
        this.mBubbles = bubbles;
        this.mNotificationShadeWindowController = notificationShadeWindowController;
        this.mShadeController = shadeController;
        this.mNotificationManager = iNotificationManager;
        this.mVisibilityProvider = notificationVisibilityProvider;
        this.mVisualInterruptionDecisionProvider = visualInterruptionDecisionProvider;
        this.mNotifUserManager = notificationLockscreenUserManager;
        this.mCommonNotifCollection = commonNotifCollection;
        this.mNotifPipelineFlags = notifPipelineFlags;
        this.mSysuiMainExecutor = executor;
        this.mBarService = iStatusBarService == null ? IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar")) : iStatusBarService;
        notifPipeline.addCollectionListener(new NotifCollectionListener() { // from class: com.android.systemui.wmshell.BubblesManager.6
            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public final void onEntryAdded(NotificationEntry notificationEntry) {
                BubblesManager bubblesManager = BubblesManager.this;
                int i = 1;
                boolean z = bubblesManager.interruptProviderShouldBubbleUp(notificationEntry) && notificationEntry.isBubble();
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("onEntryAdded : bubbleEntryAdded=", z, "BubblesManager");
                if (z) {
                    BubbleEntry notifToBubbleEntry = bubblesManager.notifToBubbleEntry(notificationEntry);
                    BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) bubblesManager.mBubbles;
                    ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new BubbleController$BubblesImpl$$ExternalSyntheticLambda2(bubblesImpl, notifToBubbleEntry, i));
                }
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public final void onEntryRemoved(NotificationEntry notificationEntry, int i) {
                ListPopupWindow$$ExternalSyntheticOutline0.m10m("setupNotifPipeline(), onEntryRemoved : reason=", i, "BubblesManager");
                if (i == 8 || i == 9) {
                    BubblesManager bubblesManager = BubblesManager.this;
                    BubbleEntry notifToBubbleEntry = bubblesManager.notifToBubbleEntry(notificationEntry);
                    BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) bubblesManager.mBubbles;
                    ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new BubbleController$BubblesImpl$$ExternalSyntheticLambda2(bubblesImpl, notifToBubbleEntry, 0));
                }
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public final void onEntryUpdated(NotificationEntry notificationEntry, final boolean z) {
                BubblesManager bubblesManager = BubblesManager.this;
                final boolean interruptProviderShouldBubbleUp = bubblesManager.interruptProviderShouldBubbleUp(notificationEntry);
                String str = notificationEntry.mKey;
                boolean z2 = bubblesManager.mIsScreenUnlocked;
                HashMap hashMap = bubblesManager.mShouldBubbleUpEntry;
                if (z2) {
                    hashMap.put(str, Boolean.valueOf(interruptProviderShouldBubbleUp));
                } else if (!z2 && hashMap.containsKey(str)) {
                    interruptProviderShouldBubbleUp = true;
                }
                Log.d("BubblesManager", "onEntryUpdated : shouldBubbleUp=" + interruptProviderShouldBubbleUp + " ,key=" + str);
                final BubbleEntry notifToBubbleEntry = bubblesManager.notifToBubbleEntry(notificationEntry);
                final BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) bubblesManager.mBubbles;
                ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.bubbles.BubbleController$BubblesImpl$$ExternalSyntheticLambda4
                    @Override // java.lang.Runnable
                    public final void run() {
                        BubbleController.BubblesImpl bubblesImpl2 = BubbleController.BubblesImpl.this;
                        BubbleController.this.onEntryUpdated(notifToBubbleEntry, interruptProviderShouldBubbleUp, z);
                    }
                });
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public final void onNotificationChannelModified(final String str, final UserHandle userHandle, final NotificationChannel notificationChannel, final int i) {
                BubblesManager bubblesManager = BubblesManager.this;
                bubblesManager.getClass();
                Log.d("BubblesManager", "onNotificationChannelModified : pkg=" + str + " ,modificationType:" + i);
                final BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) bubblesManager.mBubbles;
                bubblesImpl.getClass();
                if (i == 2 || i == 3) {
                    ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.bubbles.BubbleController$BubblesImpl$$ExternalSyntheticLambda5
                        @Override // java.lang.Runnable
                        public final void run() {
                            BubbleController.BubblesImpl bubblesImpl2 = BubbleController.BubblesImpl.this;
                            BubbleController.this.onNotificationChannelModified(str, userHandle, notificationChannel, i);
                        }
                    });
                }
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public final void onRankingUpdate(NotificationListenerService.RankingMap rankingMap) {
                BubblesManager bubblesManager = BubblesManager.this;
                bubblesManager.getClass();
                String[] orderedKeys = rankingMap.getOrderedKeys();
                HashMap hashMap = new HashMap();
                for (String str : orderedKeys) {
                    NotificationEntry entry = ((NotifPipeline) bubblesManager.mCommonNotifCollection).getEntry(str);
                    BubbleEntry notifToBubbleEntry = entry != null ? bubblesManager.notifToBubbleEntry(entry) : null;
                    boolean interruptProviderShouldBubbleUp = entry != null ? bubblesManager.interruptProviderShouldBubbleUp(entry) : false;
                    String packageName = entry != null ? entry.mSbn.getPackageName() : "null";
                    boolean z = bubblesManager.mIsScreenUnlocked;
                    HashMap hashMap2 = bubblesManager.mShouldBubbleUpEntry;
                    if (z) {
                        hashMap2.put(str, Boolean.valueOf(interruptProviderShouldBubbleUp));
                    } else if (!z && hashMap2.containsKey(str)) {
                        interruptProviderShouldBubbleUp = true;
                    }
                    if (Build.IS_ENG || Build.IS_USERDEBUG) {
                        StringBuilder sb = new StringBuilder("onRankingUpdate : shouldBubbleUp=");
                        sb.append(interruptProviderShouldBubbleUp);
                        sb.append(" ,pkg=");
                        sb.append(packageName);
                        sb.append(" ,key=");
                        ExifInterface$$ExternalSyntheticOutline0.m35m(sb, str, "BubblesManager");
                    }
                    hashMap.put(str, new Pair(notifToBubbleEntry, Boolean.valueOf(interruptProviderShouldBubbleUp)));
                }
                BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) bubblesManager.mBubbles;
                ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new BubbleController$$ExternalSyntheticLambda11(bubblesImpl, rankingMap, 2, hashMap));
            }
        });
        ((KeyguardStateControllerImpl) keyguardStateController).addCallback(new KeyguardStateController.Callback() { // from class: com.android.systemui.wmshell.BubblesManager.1
            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onKeyguardShowingChanged() {
                boolean z = !((KeyguardStateControllerImpl) keyguardStateController).mShowing;
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("onKeyguardShowingChanged: isUnlockedShade=", z, "BubblesManager");
                BubblesManager.this.mIsScreenUnlocked = z;
                BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) bubbles;
                ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new BubbleController$$ExternalSyntheticLambda19(2, bubblesImpl, z));
            }
        });
        ((ZenModeControllerImpl) zenModeController).addCallback(new ZenModeController.Callback() { // from class: com.android.systemui.wmshell.BubblesManager.2
            @Override // com.android.systemui.statusbar.policy.ZenModeController.Callback
            public final void onConfigChanged$1() {
                BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) BubblesManager.this.mBubbles;
                ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new BubbleController$BubblesImpl$$ExternalSyntheticLambda6(bubblesImpl, 2));
            }

            @Override // com.android.systemui.statusbar.policy.ZenModeController.Callback
            public final void onZenChanged(int i) {
                BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) BubblesManager.this.mBubbles;
                ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new BubbleController$BubblesImpl$$ExternalSyntheticLambda6(bubblesImpl, 2));
            }
        });
        ((ArrayList) ((NotificationLockscreenUserManagerImpl) notificationLockscreenUserManager).mListeners).add(new NotificationLockscreenUserManager.UserChangedListener() { // from class: com.android.systemui.wmshell.BubblesManager.3
            @Override // com.android.systemui.statusbar.NotificationLockscreenUserManager.UserChangedListener
            public final void onCurrentProfilesChanged(SparseArray sparseArray) {
                BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) BubblesManager.this.mBubbles;
                ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new BubbleController$$ExternalSyntheticLambda15(4, bubblesImpl, sparseArray));
            }

            @Override // com.android.systemui.statusbar.NotificationLockscreenUserManager.UserChangedListener
            public final void onUserChanged(int i) {
                BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) BubblesManager.this.mBubbles;
                ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new BubbleController$BubblesImpl$$ExternalSyntheticLambda3(bubblesImpl, i, 1));
            }

            @Override // com.android.systemui.statusbar.NotificationLockscreenUserManager.UserChangedListener
            public final void onUserRemoved(int i) {
                BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) BubblesManager.this.mBubbles;
                ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new BubbleController$BubblesImpl$$ExternalSyntheticLambda3(bubblesImpl, i, 0));
            }
        });
        StatusBarWindowCallback statusBarWindowCallback = new StatusBarWindowCallback() { // from class: com.android.systemui.wmshell.BubblesManager$$ExternalSyntheticLambda0
            @Override // com.android.systemui.statusbar.phone.StatusBarWindowCallback
            public final void onStateChanged(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7) {
                BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) BubblesManager.this.mBubbles;
                ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new BubbleController$$ExternalSyntheticLambda19(3, bubblesImpl, z6));
            }
        };
        this.mStatusBarWindowCallback = statusBarWindowCallback;
        ((NotificationShadeWindowControllerImpl) notificationShadeWindowController).registerCallback(statusBarWindowCallback);
        BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) bubbles;
        ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new BubbleController$$ExternalSyntheticLambda15(3, bubblesImpl, new C37394(executor, sysUiState)));
        ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new BubbleController$$ExternalSyntheticLambda15(2, bubblesImpl, new BubblesManager$$ExternalSyntheticLambda1(executor)));
        IntentFilter intentFilter = new IntentFilter("com.samsung.intent.action.EMERGENCY_STATE_CHANGED");
        intentFilter.addAction("com.samsung.android.action.LOCK_TASK_MODE");
        broadcastDispatcher.registerReceiver(r6, intentFilter, null, UserHandle.ALL);
        ((DesktopManagerImpl) ((DesktopManager) Dependency.get(DesktopManager.class))).registerCallback(new DesktopManager.Callback() { // from class: com.android.systemui.wmshell.BubblesManager.5
            @Override // com.android.systemui.util.DesktopManager.Callback
            public final void onDesktopModeStateChanged(SemDesktopModeState semDesktopModeState) {
                Log.d("BubblesManager", "Dismiss all bubbles - reason : DEX mode changed.");
                BubbleController.BubblesImpl bubblesImpl2 = (BubbleController.BubblesImpl) BubblesManager.this.mBubbles;
                ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new BubbleController$BubblesImpl$$ExternalSyntheticLambda6(bubblesImpl2, 0));
            }
        });
    }

    public static boolean areBubblesEnabled(Context context, UserHandle userHandle) {
        return userHandle.getIdentifier() < 0 ? Settings.Secure.getInt(context.getContentResolver(), "notification_bubbles", 0) == 1 : Settings.Secure.getIntForUser(context.getContentResolver(), "notification_bubbles", 0, userHandle.getIdentifier()) == 1;
    }

    public final boolean interruptProviderShouldBubbleUp(NotificationEntry notificationEntry) {
        return ((NotificationInterruptStateProviderWrapper) this.mVisualInterruptionDecisionProvider).makeAndLogBubbleDecision(notificationEntry).getShouldInterrupt();
    }

    public BubbleEntry notifToBubbleEntry(NotificationEntry notificationEntry) {
        boolean legacyIsDismissableRecursive;
        StatusBarNotification statusBarNotification = notificationEntry.mSbn;
        NotificationListenerService.Ranking ranking = notificationEntry.mRanking;
        if (!this.mNotifPipelineFlags.sysPropFlags.isEnabled(SystemUiSystemPropertiesFlags.NotificationFlags.ALLOW_DISMISS_ONGOING)) {
            legacyIsDismissableRecursive = notificationEntry.legacyIsDismissableRecursive();
        } else if (notificationEntry.mSbn.isNonDismissable()) {
            legacyIsDismissableRecursive = false;
        } else {
            notificationEntry.mSbn.isOngoing();
            legacyIsDismissableRecursive = true;
        }
        return new BubbleEntry(statusBarNotification, ranking, legacyIsDismissableRecursive, notificationEntry.shouldSuppressVisualEffect(64), notificationEntry.shouldSuppressVisualEffect(256), notificationEntry.shouldSuppressVisualEffect(16));
    }

    public final void onUserChangedBubble(NotificationEntry notificationEntry, boolean z) {
        NotificationChannel channel = notificationEntry.getChannel();
        String packageName = notificationEntry.mSbn.getPackageName();
        int uid = notificationEntry.mSbn.getUid();
        if (channel == null || packageName == null) {
            return;
        }
        notificationEntry.isBubble();
        if (!z) {
            notificationEntry.mSbn.getNotification().flags &= -4097;
        } else if (notificationEntry.mBubbleMetadata != null && notificationEntry.canBubble()) {
            notificationEntry.mSbn.getNotification().flags |= 4096;
        }
        notificationEntry.isBubble();
        try {
            this.mBarService.onNotificationBubbleChanged(notificationEntry.mKey, z, 3);
        } catch (RemoteException unused) {
        }
        Context context = this.mContext;
        INotificationManager iNotificationManager = this.mNotificationManager;
        NotificationChannel createConversationChannelIfNeeded = NotificationChannelHelper.createConversationChannelIfNeeded(context, iNotificationManager, notificationEntry, channel);
        createConversationChannelIfNeeded.setAllowBubbles(z);
        try {
            int bubblePreferenceForPackage = iNotificationManager.getBubblePreferenceForPackage(packageName, uid);
            if (z && bubblePreferenceForPackage == 0) {
                iNotificationManager.setBubblesAllowed(packageName, uid, 2);
            }
            iNotificationManager.updateNotificationChannelForPackage(packageName, uid, createConversationChannelIfNeeded);
        } catch (RemoteException e) {
            Log.e("BubblesManager", e.getMessage());
        }
        if (z) {
            ((ShadeControllerImpl) this.mShadeController).collapseShade(true);
            ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
            if (expandableNotificationRow != null) {
                expandableNotificationRow.updateBubbleButton();
            }
        }
    }
}
