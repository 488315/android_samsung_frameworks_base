package com.android.systemui.wmshell;

import android.app.INotificationManager;
import android.app.NotificationChannel;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.UserHandle;
import android.provider.Settings;
import android.service.dreams.IDreamManager;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.service.notification.ZenModeConfig;
import android.util.Log;
import android.util.Pair;
import android.util.SparseArray;
import com.android.internal.statusbar.IStatusBarService;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.server.notification.Flags;
import com.android.systemui.broadcast.BroadcastDispatcher;
import com.android.systemui.flags.FeatureFlags;
import com.android.systemui.model.SysUiState;
import com.android.systemui.shade.NotificationShadeWindowControllerImpl;
import com.android.systemui.shade.ShadeController;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.NotificationShadeWindowController;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import com.android.systemui.statusbar.notification.NotificationChannelHelper;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.notifcollection.DismissedByUserStats;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.collection.render.NotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.interruption.VisualInterruptionDecisionProvider;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.phone.StatusBarWindowCallback;
import com.android.systemui.statusbar.policy.KeyguardStateController;
import com.android.systemui.statusbar.policy.KeyguardStateControllerImpl;
import com.android.systemui.statusbar.policy.SensitiveNotificationProtectionController;
import com.android.systemui.statusbar.policy.SensitiveNotificationProtectionControllerImpl;
import com.android.systemui.statusbar.policy.ZenModeController;
import com.android.systemui.statusbar.policy.ZenModeControllerImpl;
import com.android.wm.shell.bubbles.BubbleController;
import com.android.wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda14;
import com.android.wm.shell.bubbles.BubbleController$$ExternalSyntheticLambda16;
import com.android.wm.shell.bubbles.BubbleController$BubblesImpl$$ExternalSyntheticLambda10;
import com.android.wm.shell.bubbles.BubbleController$BubblesImpl$$ExternalSyntheticLambda15;
import com.android.wm.shell.bubbles.BubbleController$BubblesImpl$$ExternalSyntheticLambda4;
import com.android.wm.shell.bubbles.BubbleController$BubblesImpl$$ExternalSyntheticLambda5;
import com.android.wm.shell.bubbles.BubbleEntry;
import com.android.wm.shell.bubbles.Bubbles;
import com.android.wm.shell.common.HandlerExecutor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class BubblesManager {
    public final IStatusBarService mBarService;
    public final AnonymousClass7 mBroadcastReceiver;
    public final Bubbles mBubbles;
    public final CommonNotifCollection mCommonNotifCollection;
    public final Context mContext;
    public final IDreamManager mDreamManager;
    public boolean mDreamingOrInPreview;
    public boolean mKeyguardShowing;
    public final KeyguardStateController mKeyguardStateController;
    public final NotificationLockscreenUserManager mNotifUserManager;
    public final INotificationManager mNotificationManager;
    public final NotificationShadeWindowController mNotificationShadeWindowController;
    public boolean mPanelExpanded;
    public final SensitiveNotificationProtectionController mSensitiveNotifProtectionController;
    public final AnonymousClass4 mSensitiveStateChangedListener;
    public final ShadeController mShadeController;
    public final StatusBarWindowCallback mStatusBarWindowCallback;
    public final Executor mSysuiMainExecutor;
    public final Executor mSysuiUiBgExecutor;
    public final NotificationVisibilityProvider mVisibilityProvider;
    public final VisualInterruptionDecisionProvider mVisualInterruptionDecisionProvider;
    public final List mCallbacks = new ArrayList();
    public boolean mIsScreenUnlocked = true;
    public final HashMap mShouldBubbleUpEntry = new HashMap();

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    /* renamed from: com.android.systemui.wmshell.BubblesManager$5, reason: invalid class name */
    public final class AnonymousClass5 {
        public final /* synthetic */ SysUiState val$sysUiState;
        public final /* synthetic */ Executor val$sysuiMainExecutor;

        public AnonymousClass5(Executor executor, SysUiState sysUiState) {
            this.val$sysuiMainExecutor = executor;
            this.val$sysUiState = sysUiState;
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public interface NotifCallback {
        void invalidateNotifications(String str);

        void removeNotification(NotificationEntry notificationEntry, DismissedByUserStats dismissedByUserStats, int i);
    }

    public BubblesManager(Context context, final Bubbles bubbles, NotificationShadeWindowController notificationShadeWindowController, final KeyguardStateController keyguardStateController, ShadeController shadeController, IStatusBarService iStatusBarService, INotificationManager iNotificationManager, IDreamManager iDreamManager, NotificationVisibilityProvider notificationVisibilityProvider, VisualInterruptionDecisionProvider visualInterruptionDecisionProvider, ZenModeController zenModeController, NotificationLockscreenUserManager notificationLockscreenUserManager, SensitiveNotificationProtectionController sensitiveNotificationProtectionController, CommonNotifCollection commonNotifCollection, NotifPipeline notifPipeline, SysUiState sysUiState, FeatureFlags featureFlags, NotifPipelineFlags notifPipelineFlags, Executor executor, Executor executor2, BroadcastDispatcher broadcastDispatcher) {
        BroadcastReceiver broadcastReceiver = new BroadcastReceiver() { // from class: com.android.systemui.wmshell.BubblesManager.7
            @Override // android.content.BroadcastReceiver
            public final void onReceive(Context context2, Intent intent) {
                String action = intent.getAction();
                if (!"com.samsung.intent.action.EMERGENCY_STATE_CHANGED".equals(action)) {
                    if ("com.samsung.android.action.LOCK_TASK_MODE".equals(action)) {
                        Log.d("Bubbles", "Dismiss all bubbles - reason : PIN mode detected.");
                        BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) BubblesManager.this.mBubbles;
                        ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new BubbleController$BubblesImpl$$ExternalSyntheticLambda5(bubblesImpl, 3));
                        return;
                    }
                    return;
                }
                if (intent.getIntExtra("reason", 0) != 5) {
                    Log.d("Bubbles", "Dismiss bubble because MW disabled");
                    BubbleController.BubblesImpl bubblesImpl2 = (BubbleController.BubblesImpl) BubblesManager.this.mBubbles;
                    ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new BubbleController$BubblesImpl$$ExternalSyntheticLambda5(bubblesImpl2, 0));
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
        this.mContext = context;
        this.mBubbles = bubbles;
        this.mNotificationShadeWindowController = notificationShadeWindowController;
        this.mKeyguardStateController = keyguardStateController;
        this.mShadeController = shadeController;
        this.mNotificationManager = iNotificationManager;
        this.mDreamManager = iDreamManager;
        this.mVisibilityProvider = notificationVisibilityProvider;
        this.mVisualInterruptionDecisionProvider = visualInterruptionDecisionProvider;
        this.mNotifUserManager = notificationLockscreenUserManager;
        this.mSensitiveNotifProtectionController = sensitiveNotificationProtectionController;
        this.mCommonNotifCollection = commonNotifCollection;
        this.mSysuiMainExecutor = executor;
        this.mSysuiUiBgExecutor = executor2;
        this.mBarService = iStatusBarService == null ? IStatusBarService.Stub.asInterface(ServiceManager.getService("statusbar")) : iStatusBarService;
        notifPipeline.addCollectionListener(new NotifCollectionListener() { // from class: com.android.systemui.wmshell.BubblesManager.6
            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public final void onEntryAdded(NotificationEntry notificationEntry) {
                BubblesManager bubblesManager = BubblesManager.this;
                if (bubblesManager.shouldBubbleUp(notificationEntry) && notificationEntry.isBubble()) {
                    BubbleEntry notifToBubbleEntry = bubblesManager.notifToBubbleEntry(notificationEntry);
                    BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) bubblesManager.mBubbles;
                    ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new BubbleController$BubblesImpl$$ExternalSyntheticLambda4(bubblesImpl, notifToBubbleEntry, 2));
                }
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public final void onEntryRemoved(NotificationEntry notificationEntry, int i) {
                if (i == 8 || i == 9) {
                    BubblesManager bubblesManager = BubblesManager.this;
                    BubbleEntry notifToBubbleEntry = bubblesManager.notifToBubbleEntry(notificationEntry);
                    BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) bubblesManager.mBubbles;
                    ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new BubbleController$BubblesImpl$$ExternalSyntheticLambda4(bubblesImpl, notifToBubbleEntry, 0));
                }
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public final void onEntryUpdated(NotificationEntry notificationEntry, final boolean z) {
                BubblesManager bubblesManager = BubblesManager.this;
                boolean shouldBubbleUp = bubblesManager.shouldBubbleUp(notificationEntry);
                String str = notificationEntry.mKey;
                boolean z2 = bubblesManager.mIsScreenUnlocked;
                if (z2) {
                    bubblesManager.mShouldBubbleUpEntry.put(str, Boolean.valueOf(shouldBubbleUp));
                } else if (!z2 && bubblesManager.mShouldBubbleUpEntry.containsKey(str)) {
                    shouldBubbleUp = true;
                }
                Log.d("Bubbles", "onEntryUpdated : shouldBubbleUp=" + shouldBubbleUp + " ,key=" + str);
                final BubbleEntry notifToBubbleEntry = bubblesManager.notifToBubbleEntry(notificationEntry);
                final boolean shouldBubbleUp2 = bubblesManager.shouldBubbleUp(notificationEntry);
                final BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) bubblesManager.mBubbles;
                ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.bubbles.BubbleController$BubblesImpl$$ExternalSyntheticLambda17
                    @Override // java.lang.Runnable
                    public final void run() {
                        BubbleController.BubblesImpl bubblesImpl2 = BubbleController.BubblesImpl.this;
                        BubbleController.this.onEntryUpdated(notifToBubbleEntry, shouldBubbleUp2, z);
                    }
                });
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public final void onNotificationChannelModified(final String str, final UserHandle userHandle, final NotificationChannel notificationChannel, final int i) {
                final BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) BubblesManager.this.mBubbles;
                bubblesImpl.getClass();
                if (i == 2 || i == 3) {
                    ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new Runnable() { // from class: com.android.wm.shell.bubbles.BubbleController$BubblesImpl$$ExternalSyntheticLambda7
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
                    NotificationEntry entry = ((NotifPipeline) bubblesManager.mCommonNotifCollection).mNotifCollection.getEntry(str);
                    BubbleEntry notifToBubbleEntry = entry != null ? bubblesManager.notifToBubbleEntry(entry) : null;
                    boolean shouldBubbleUp = entry != null ? bubblesManager.shouldBubbleUp(entry) : false;
                    boolean z = bubblesManager.mIsScreenUnlocked;
                    if (z) {
                        bubblesManager.mShouldBubbleUpEntry.put(str, Boolean.valueOf(shouldBubbleUp));
                    } else if (!z && bubblesManager.mShouldBubbleUpEntry.containsKey(str)) {
                        shouldBubbleUp = true;
                    }
                    hashMap.put(str, new Pair(notifToBubbleEntry, Boolean.valueOf(shouldBubbleUp)));
                }
                BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) bubblesManager.mBubbles;
                ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new BubbleController$$ExternalSyntheticLambda14(bubblesImpl, rankingMap, 1, hashMap));
            }
        });
        ((KeyguardStateControllerImpl) keyguardStateController).addCallback(new KeyguardStateController.Callback() { // from class: com.android.systemui.wmshell.BubblesManager.1
            @Override // com.android.systemui.statusbar.policy.KeyguardStateController.Callback
            public final void onKeyguardShowingChanged() {
                boolean z = !((KeyguardStateControllerImpl) keyguardStateController).mShowing;
                KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m("onKeyguardShowingChanged: isUnlockedShade=", "Bubbles", z);
                BubblesManager bubblesManager = BubblesManager.this;
                bubblesManager.mIsScreenUnlocked = z;
                bubblesManager.mSysuiUiBgExecutor.execute(new BubblesManager$$ExternalSyntheticLambda4(bubblesManager));
            }
        });
        ((ZenModeControllerImpl) zenModeController).addCallback(new ZenModeController.Callback() { // from class: com.android.systemui.wmshell.BubblesManager.2
            @Override // com.android.systemui.statusbar.policy.ZenModeController.Callback
            public final void onConfigChanged(ZenModeConfig zenModeConfig) {
                BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) BubblesManager.this.mBubbles;
                ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new BubbleController$BubblesImpl$$ExternalSyntheticLambda5(bubblesImpl, 1));
            }

            @Override // com.android.systemui.statusbar.policy.ZenModeController.Callback
            public final void onZenChanged(int i) {
                BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) BubblesManager.this.mBubbles;
                ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new BubbleController$BubblesImpl$$ExternalSyntheticLambda5(bubblesImpl, 1));
            }
        });
        ((NotificationLockscreenUserManagerImpl) notificationLockscreenUserManager).addUserChangedListener(new NotificationLockscreenUserManager.UserChangedListener() { // from class: com.android.systemui.wmshell.BubblesManager.3
            @Override // com.android.systemui.statusbar.NotificationLockscreenUserManager.UserChangedListener
            public final void onCurrentProfilesChanged(SparseArray sparseArray) {
                BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) BubblesManager.this.mBubbles;
                ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new BubbleController$$ExternalSyntheticLambda16(4, bubblesImpl, sparseArray));
            }

            @Override // com.android.systemui.statusbar.NotificationLockscreenUserManager.UserChangedListener
            public final void onUserChanged(int i) {
                BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) BubblesManager.this.mBubbles;
                ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new BubbleController$BubblesImpl$$ExternalSyntheticLambda15(bubblesImpl, i, 1));
            }

            @Override // com.android.systemui.statusbar.NotificationLockscreenUserManager.UserChangedListener
            public final void onUserRemoved(int i) {
                BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) BubblesManager.this.mBubbles;
                ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new BubbleController$BubblesImpl$$ExternalSyntheticLambda15(bubblesImpl, i, 0));
            }
        });
        StatusBarWindowCallback statusBarWindowCallback = new StatusBarWindowCallback() { // from class: com.android.systemui.wmshell.BubblesManager$$ExternalSyntheticLambda0
            @Override // com.android.systemui.statusbar.phone.StatusBarWindowCallback
            public final void onStateChanged(boolean z, boolean z2, boolean z3, boolean z4, boolean z5, boolean z6, boolean z7) {
                BubblesManager bubblesManager = BubblesManager.this;
                if (z6 != bubblesManager.mPanelExpanded) {
                    bubblesManager.mPanelExpanded = z6;
                    BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) bubblesManager.mBubbles;
                    ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new BubbleController$BubblesImpl$$ExternalSyntheticLambda10(bubblesImpl, z6, 1));
                }
                if (bubblesManager.mKeyguardShowing || !bubblesManager.mDreamingOrInPreview || z7) {
                    return;
                }
                bubblesManager.mSysuiUiBgExecutor.execute(new BubblesManager$$ExternalSyntheticLambda4(bubblesManager));
            }
        };
        this.mStatusBarWindowCallback = statusBarWindowCallback;
        ((NotificationShadeWindowControllerImpl) notificationShadeWindowController).registerCallback(statusBarWindowCallback);
        Runnable runnable = new Runnable() { // from class: com.android.systemui.wmshell.BubblesManager.4
            @Override // java.lang.Runnable
            public final void run() {
                Flags.screenshareNotificationHiding();
                Bubbles bubbles2 = bubbles;
                boolean isSensitiveStateActive = ((SensitiveNotificationProtectionControllerImpl) BubblesManager.this.mSensitiveNotifProtectionController).isSensitiveStateActive();
                BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) bubbles2;
                ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new BubbleController$BubblesImpl$$ExternalSyntheticLambda10(bubblesImpl, isSensitiveStateActive, 0));
            }
        };
        Flags.screenshareNotificationHiding();
        ((SensitiveNotificationProtectionControllerImpl) sensitiveNotificationProtectionController).mListeners.addIfAbsent(runnable);
        BubbleController.BubblesImpl bubblesImpl = (BubbleController.BubblesImpl) bubbles;
        ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new BubbleController$$ExternalSyntheticLambda16(7, bubblesImpl, new AnonymousClass5(executor, sysUiState)));
        ((HandlerExecutor) BubbleController.this.mMainExecutor).execute(new BubbleController$$ExternalSyntheticLambda16(6, bubblesImpl, new BubblesManager$$ExternalSyntheticLambda1(executor)));
        IntentFilter intentFilter = new IntentFilter("com.samsung.intent.action.EMERGENCY_STATE_CHANGED");
        intentFilter.addAction("com.samsung.android.action.LOCK_TASK_MODE");
        broadcastDispatcher.registerReceiver(broadcastReceiver, intentFilter, null, UserHandle.ALL);
    }

    public static boolean areBubblesEnabled(Context context, UserHandle userHandle) {
        return userHandle.getIdentifier() < 0 ? Settings.Secure.getInt(context.getContentResolver(), "notification_bubbles", 0) == 1 : Settings.Secure.getIntForUser(context.getContentResolver(), "notification_bubbles", 0, userHandle.getIdentifier()) == 1;
    }

    public BubbleEntry notifToBubbleEntry(NotificationEntry notificationEntry) {
        boolean z;
        StatusBarNotification statusBarNotification = notificationEntry.mSbn;
        NotificationListenerService.Ranking ranking = notificationEntry.mRanking;
        if (statusBarNotification.isNonDismissable()) {
            z = false;
        } else {
            notificationEntry.mSbn.isOngoing();
            z = true;
        }
        return new BubbleEntry(statusBarNotification, ranking, z, notificationEntry.shouldSuppressVisualEffect(64), notificationEntry.shouldSuppressVisualEffect(256), notificationEntry.shouldSuppressVisualEffect(16));
    }

    public final void onUserChangedBubble(NotificationEntry notificationEntry, boolean z) {
        NotificationChannel channel = notificationEntry.mRanking.getChannel();
        String packageName = notificationEntry.mSbn.getPackageName();
        int uid = notificationEntry.mSbn.getUid();
        if (channel == null || packageName == null) {
            return;
        }
        notificationEntry.isBubble();
        if (!z) {
            notificationEntry.mSbn.getNotification().flags &= -4097;
        } else if (notificationEntry.mBubbleMetadata != null && notificationEntry.mRanking.canBubble()) {
            notificationEntry.mSbn.getNotification().flags |= 4096;
        }
        notificationEntry.isBubble();
        try {
            this.mBarService.onNotificationBubbleChanged(notificationEntry.mKey, z, 3);
        } catch (RemoteException unused) {
        }
        NotificationChannel createConversationChannelIfNeeded = NotificationChannelHelper.createConversationChannelIfNeeded(this.mContext, this.mNotificationManager, notificationEntry, channel);
        createConversationChannelIfNeeded.setAllowBubbles(z);
        try {
            int bubblePreferenceForPackage = this.mNotificationManager.getBubblePreferenceForPackage(packageName, uid);
            if (z && bubblePreferenceForPackage == 0) {
                this.mNotificationManager.setBubblesAllowed(packageName, uid, 2);
            }
            this.mNotificationManager.updateNotificationChannelForPackage(packageName, uid, createConversationChannelIfNeeded);
        } catch (RemoteException e) {
            Log.e("Bubbles", e.getMessage());
        }
        if (z) {
            this.mShadeController.collapseShade(true);
            ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
            if (expandableNotificationRow != null) {
                expandableNotificationRow.updateBubbleButton();
            }
        }
    }

    public final boolean shouldBubbleUp(NotificationEntry notificationEntry) {
        return this.mVisualInterruptionDecisionProvider.makeAndLogBubbleDecision(notificationEntry).getShouldInterrupt();
    }
}
