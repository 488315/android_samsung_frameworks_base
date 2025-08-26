package com.android.systemui.statusbar.notification.collection.coordinator;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.UserHandle;
import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.util.Log;
import androidx.recyclerview.widget.RecyclerView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecPinBasedInputViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.Dependency;
import com.android.systemui.NotiRune;
import com.android.systemui.R;
import com.android.systemui.media.controls.domain.pipeline.MediaDataManager;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.KeyguardBatteryStatus;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.StatusBarStateControllerImpl;
import com.android.systemui.statusbar.SysuiStatusBarStateController;
import com.android.systemui.statusbar.notification.SubscreenNotificationController;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataStoreImpl;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.PipelineEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeFinalizeFilterListener;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifComparator;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.collection.notifcollection.UpdateSource;
import com.android.systemui.statusbar.notification.collection.provider.DebugModeFilterProvider;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderController;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManager;
import com.android.systemui.statusbar.notification.headsup.HeadsUpManagerImpl;
import com.android.systemui.statusbar.notification.headsup.OnHeadsUpChangedListener;
import com.android.systemui.statusbar.notification.interruption.KeyguardNotificationVisibilityProvider;
import com.android.systemui.statusbar.notification.interruption.KeyguardNotificationVisibilityProviderImpl;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotifInflationErrorManager;
import com.android.systemui.util.NotificationChannels;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.cover.CoverState;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

@CoordinatorScope
/* loaded from: classes3.dex */
public class InsignificantCoordinator implements Coordinator {
    private int mBarState;
    private BubbleCoordinator mBubbleCoordinator;
    private final Lazy mCommonNotifCollectionLazy;
    private Context mContext;
    private DebugModeFilterProvider mDebugModeFilterProvider;
    private String mFirstChildKey;
    private HeadsUpManager mHeadsUpManager;
    private final NotifInflationErrorManager.NotifInflationErrorListener mInflationErrorListener;
    private final Handler mInsiginificantHandler;
    private final NotifSectioner mInsignificantNotifSectioner;
    private final KeyguardNotificationVisibilityProvider mKeyguardNotificationVisibilityProvider;
    private KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    private final KeyguardUpdateMonitorCallback mKeyguardUpdateMonitorCallback;
    private final NotificationLockscreenUserManager mLockscreenUserManager;
    private final NotifCollectionListener mNotifCollectionListener;
    private final NotifInflationErrorManager mNotifErrorManager;
    private final NotifFilter mNotifFilter;
    private NotifLiveDataStoreImpl mNotifLiveDataStoreImpl;
    private NotifPipeline mNotifPipeline;
    private NotificationManager mNotificationManager;
    private final OnBeforeFinalizeFilterListener mOnBeforeFinalizeFilterListener;
    private final OnHeadsUpChangedListener mOnHeadsUpChangedListener;
    private final SettingsHelper.OnChangedCallback mSettingsChangedListener;
    private final Uri[] mSettingsValueList;
    private final SectionHeaderController mSilentHeaderController;
    private final NodeController mSilentNodeController;
    private SysuiStatusBarStateController mStatusBarStateController;
    private final StatusBarStateController.StateListener mStatusBarStateListener;
    private SubscreenNotificationController mSubscreenController;
    NotifTimeSortCoordnator mTimeSortCoordinator;
    private String TAG = "InsignificantCoordinator";
    private boolean DEBUG = true;
    private final List<NotificationEntry> mChildren = new ArrayList();
    private final String CHANNEL_ID = "INSIGNIFICANT";
    private final int UPDATE_INSIGNIFICANT_SUMMARY_VISIBLE = 0;
    private final int DISMISS_INSIGNIFICANT_CHILD = 1;
    private final int UPDATE_INSIGNIFICANT_SUMMARY_ALL = 2;
    private boolean mWaitingForGroupSummary = false;
    private int mGroupCount = 0;

    public InsignificantCoordinator(SectionHeaderController sectionHeaderController, NodeController nodeController, NotificationManager notificationManager, NotifTimeSortCoordnator notifTimeSortCoordnator, NotifInflationErrorManager notifInflationErrorManager, Lazy lazy, StatusBarStateController statusBarStateController, NotifLiveDataStoreImpl notifLiveDataStoreImpl, HeadsUpManager headsUpManager, NotifPipeline notifPipeline, NotificationLockscreenUserManager notificationLockscreenUserManager, KeyguardUpdateMonitor keyguardUpdateMonitor, KeyguardNotificationVisibilityProvider keyguardNotificationVisibilityProvider, DebugModeFilterProvider debugModeFilterProvider, SubscreenNotificationController subscreenNotificationController, BubbleCoordinator bubbleCoordinator, Context context) {
        Uri[] uriArr = {Settings.System.getUriFor(SettingsHelper.INDEX_NOTI_SETTINGS_INSIGNIFICANT_PROMOTION), Settings.System.getUriFor(SettingsHelper.INDEX_NOTI_SETTINGS_INSIGNIFICANT_BG_ACTIVITIES), Settings.System.getUriFor(SettingsHelper.INDEX_NOTI_SETTINGS_INSIGNIFICANT_MINIMIZED_NOTIFICATIONS), Settings.System.getUriFor(SettingsHelper.INDEX_NOTI_SETTINGS_INSIGNIFICANT_OLD_NOTIFICATIONS)};
        this.mSettingsValueList = uriArr;
        SettingsHelper.OnChangedCallback onChangedCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.InsignificantCoordinator.1
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public void onChanged(Uri uri) throws Resources.NotFoundException {
                InsignificantCoordinator.this.resetInsignificant();
            }
        };
        this.mSettingsChangedListener = onChangedCallback;
        KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.InsignificantCoordinator.2
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onUserSwitchComplete(int i) throws Resources.NotFoundException {
                KeyguardSecPinBasedInputViewController$$ExternalSyntheticOutline0.m(new StringBuilder("onUserSwitchComplete user changed : "), ((NotificationLockscreenUserManagerImpl) InsignificantCoordinator.this.mLockscreenUserManager).mCurrentUserId, " userID : ", i, InsignificantCoordinator.this.TAG);
                InsignificantCoordinator.this.resetInsignificant();
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onBiometricLockoutChanged(boolean z) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onDlsViewModeChanged(int i) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onDualDARInnerLockscreenRequirementChanged(int i) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onEmergencyStateChanged(int i) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onFaceWidgetFullscreenModeChanged(boolean z) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onLockDisabledChanged(boolean z) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onPackageAdded(String str) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onPackageChanged(String str) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onPackageDataCleared(String str) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onPrimaryBouncerVisibilityChanged(boolean z) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onRefreshBatteryInfo(KeyguardBatteryStatus keyguardBatteryStatus) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onSecurityViewChanged(KeyguardSecurityModel.SecurityMode securityMode) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onSimulationFailToUnlock(int i) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onTableModeChanged(boolean z) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onUSBRestrictionChanged(boolean z) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onUpdateCoverState(CoverState coverState) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onFailedUnlockAttemptChanged() {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onLocaleChanged() {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onLockModeChanged() {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onOfflineStateChanged() {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onOwnerInfoChanged() {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onRemoteLockInfoChanged() {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onSystemDialogsShowing() {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onUdfpsFingerDown() {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onUdfpsFingerUp() {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onUnlocking() {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onDualDarInnerLockScreenStateChanged(int i, boolean z) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onPackageRemoved(String str, boolean z) {
            }
        };
        this.mKeyguardUpdateMonitorCallback = keyguardUpdateMonitorCallback;
        this.mNotifFilter = new NotifFilter(this.TAG) { // from class: com.android.systemui.statusbar.notification.collection.coordinator.InsignificantCoordinator.3
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
            public boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
                StatusBarNotification statusBarNotification = notificationEntry.mSbn;
                if (!statusBarNotification.getGroupKey().contains("INSIGNIFICANT") || !InsignificantCoordinator.this.mChildren.isEmpty() || InsignificantCoordinator.this.mWaitingForGroupSummary) {
                    if (statusBarNotification.getNotification().isGroupSummary()) {
                        String groupKey = statusBarNotification.getGroupKey();
                        char c = 0;
                        for (NotificationEntry notificationEntry2 : ((NotifPipeline) ((CommonNotifCollection) InsignificantCoordinator.this.mCommonNotifCollectionLazy.get())).getAllNotifs()) {
                            if (((NotificationLockscreenUserManagerImpl) InsignificantCoordinator.this.mLockscreenUserManager).isCurrentProfile(notificationEntry2.mSbn.getUser().getIdentifier()) && !notificationEntry2.mKey.equals(notificationEntry.mKey) && groupKey.equals(notificationEntry2.mSbn.getGroupKey())) {
                                if (!notificationEntry2.isInsignificant()) {
                                    c = 2;
                                } else if (c != 2) {
                                    c = 1;
                                }
                            }
                        }
                        if (c == 1) {
                        }
                    }
                    return false;
                }
                return true;
            }
        };
        this.mNotifCollectionListener = new NotifCollectionListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.InsignificantCoordinator.4
            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public void onEntryAdded(NotificationEntry notificationEntry) {
                if (notificationEntry.mSbn.getGroupKey().contains("INSIGNIFICANT") || notificationEntry.mSbn.getNotification().isGroupSummary()) {
                    return;
                }
                int size = InsignificantCoordinator.this.mChildren.size();
                InsignificantCoordinator.this.mChildren.remove(notificationEntry);
                if (notificationEntry.isInsignificant()) {
                    InsignificantCoordinator.this.mChildren.add(notificationEntry);
                }
                if (size != InsignificantCoordinator.this.mChildren.size() || notificationEntry.isInsignificant()) {
                    Log.d(InsignificantCoordinator.this.TAG, "onEntryAdded :" + notificationEntry.mKey + " flags: " + Integer.toHexString(notificationEntry.mSbn.getNotification().semFlags) + " updateSummary entrySize:" + InsignificantCoordinator.this.mChildren.size());
                    Message messageObtain = Message.obtain();
                    messageObtain.what = 2;
                    InsignificantCoordinator.this.mInsiginificantHandler.removeMessages(2);
                    InsignificantCoordinator.this.mInsiginificantHandler.sendMessageDelayed(messageObtain, 0L);
                }
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public void onEntryRemoved(NotificationEntry notificationEntry, int i) {
                if (InsignificantCoordinator.this.DEBUG) {
                    Log.d(InsignificantCoordinator.this.TAG, "onEntryRemoved :" + notificationEntry.mKey + " flags: " + Integer.toHexString(notificationEntry.mSbn.getNotification().semFlags) + " entrySize:" + InsignificantCoordinator.this.mChildren.size());
                }
                if (notificationEntry.mSbn.getGroupKey().contains("INSIGNIFICANT") && !InsignificantCoordinator.this.mChildren.isEmpty()) {
                    Message messageObtain = Message.obtain();
                    messageObtain.what = 1;
                    InsignificantCoordinator.this.mInsiginificantHandler.sendMessage(messageObtain);
                    return;
                }
                InsignificantCoordinator.this.mChildren.remove(notificationEntry);
                if (notificationEntry.isInsignificant()) {
                    Message messageObtain2 = Message.obtain();
                    messageObtain2.what = 2;
                    InsignificantCoordinator.this.mInsiginificantHandler.removeMessages(2);
                    InsignificantCoordinator.this.mInsiginificantHandler.sendMessageDelayed(messageObtain2, 0L);
                }
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public void onEntryUpdated(NotificationEntry notificationEntry, UpdateSource updateSource) {
                onEntryUpdated(notificationEntry);
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public void onEntryUpdated(NotificationEntry notificationEntry) {
                if (notificationEntry.mSbn.getGroupKey().contains("INSIGNIFICANT") || notificationEntry.mSbn.getNotification().isGroupSummary()) {
                    return;
                }
                int size = InsignificantCoordinator.this.mChildren.size();
                InsignificantCoordinator.this.mChildren.remove(notificationEntry);
                if (notificationEntry.isInsignificant()) {
                    InsignificantCoordinator.this.mChildren.add(notificationEntry);
                }
                if (size != InsignificantCoordinator.this.mChildren.size() || notificationEntry.isInsignificant()) {
                    Log.d(InsignificantCoordinator.this.TAG, "onEntryUpdated :" + notificationEntry.mKey + " flags: " + Integer.toHexString(notificationEntry.mSbn.getNotification().semFlags) + " updateSummary entrySize:" + InsignificantCoordinator.this.mChildren.size());
                    Message messageObtain = Message.obtain();
                    messageObtain.what = 2;
                    InsignificantCoordinator.this.mInsiginificantHandler.removeMessages(2);
                    InsignificantCoordinator.this.mInsiginificantHandler.sendMessageDelayed(messageObtain, 0L);
                }
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public /* bridge */ /* synthetic */ void onEntryCleanUp(NotificationEntry notificationEntry) {
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public /* bridge */ /* synthetic */ void onEntryInit(NotificationEntry notificationEntry) {
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            @Deprecated
            public /* bridge */ /* synthetic */ void onRankingUpdate(NotificationListenerService.RankingMap rankingMap) {
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public /* bridge */ /* synthetic */ void onRankingApplied() {
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public /* bridge */ /* synthetic */ void onEntryBind(NotificationEntry notificationEntry, StatusBarNotification statusBarNotification) {
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public /* bridge */ /* synthetic */ void onNotificationChannelModified(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) {
            }
        };
        this.mOnBeforeFinalizeFilterListener = new OnBeforeFinalizeFilterListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.InsignificantCoordinator.5
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.OnBeforeFinalizeFilterListener
            public void onBeforeFinalizeFilter(List<PipelineEntry> list) {
                Iterator<PipelineEntry> it = list.iterator();
                while (it.hasNext()) {
                    if (it.next().getKey().equals("INSIGNIFICANT")) {
                        Message messageObtain = Message.obtain();
                        messageObtain.what = 2;
                        InsignificantCoordinator.this.mInsiginificantHandler.removeMessages(2);
                        InsignificantCoordinator.this.mInsiginificantHandler.sendMessageDelayed(messageObtain, 200L);
                    }
                }
            }
        };
        this.mInsignificantNotifSectioner = new NotifSectioner("Insignificant", 20) { // from class: com.android.systemui.statusbar.notification.collection.coordinator.InsignificantCoordinator.6
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
            public NotifComparator getComparator() {
                return InsignificantCoordinator.this.mTimeSortCoordinator.getTimeComparator();
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
            public NodeController getHeaderNodeController() {
                return null;
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
            public boolean isInSection(PipelineEntry pipelineEntry) {
                NotificationEntry representativeEntry;
                if (((HeadsUpManagerImpl) InsignificantCoordinator.this.mHeadsUpManager).isHeadsUpEntry(pipelineEntry.getKey()) || (representativeEntry = pipelineEntry.getRepresentativeEntry()) == null) {
                    return false;
                }
                boolean zIsInsignificant = representativeEntry.isInsignificant();
                if (zIsInsignificant && (pipelineEntry instanceof GroupEntry)) {
                    ((ArrayList) ((GroupEntry) pipelineEntry).mChildren).sort(getComparator());
                }
                return zIsInsignificant;
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
            public void onEntriesUpdated(List<PipelineEntry> list) {
            }
        };
        StatusBarStateController.StateListener stateListener = new StatusBarStateController.StateListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.InsignificantCoordinator.7
            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public void onStatePostChange() {
                RecyclerView$$ExternalSyntheticOutline0.m(((StatusBarStateControllerImpl) InsignificantCoordinator.this.mStatusBarStateController).mUpcomingState, InsignificantCoordinator.this.TAG, new StringBuilder("onStatePostChange :  "));
                if ((InsignificantCoordinator.this.mBarState == 0 || ((StatusBarStateControllerImpl) InsignificantCoordinator.this.mStatusBarStateController).mUpcomingState != 0) && (InsignificantCoordinator.this.mBarState != 0 || ((StatusBarStateControllerImpl) InsignificantCoordinator.this.mStatusBarStateController).mUpcomingState == 0)) {
                    return;
                }
                InsignificantCoordinator insignificantCoordinator = InsignificantCoordinator.this;
                insignificantCoordinator.mBarState = ((StatusBarStateControllerImpl) insignificantCoordinator.mStatusBarStateController).mUpcomingState;
                Message messageObtain = Message.obtain();
                messageObtain.what = 2;
                InsignificantCoordinator.this.mInsiginificantHandler.removeMessages(2);
                InsignificantCoordinator.this.mInsiginificantHandler.sendMessageDelayed(messageObtain, 200L);
            }
        };
        this.mStatusBarStateListener = stateListener;
        OnHeadsUpChangedListener onHeadsUpChangedListener = new OnHeadsUpChangedListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.InsignificantCoordinator.8
            @Override // com.android.systemui.statusbar.notification.headsup.OnHeadsUpChangedListener
            public void onHeadsUpStateChanged(NotificationEntry notificationEntry, boolean z) {
                Log.d(InsignificantCoordinator.this.TAG, "onHeadsUpStateChanged : " + notificationEntry.mKey + " isHeadsUP :" + z + " entry? " + notificationEntry.mIsHeadsUpEntry + " headsupManager? " + ((HeadsUpManagerImpl) InsignificantCoordinator.this.mHeadsUpManager).isHeadsUpEntry(notificationEntry.mKey));
                if (notificationEntry.isInsignificant()) {
                    Message messageObtain = Message.obtain();
                    messageObtain.what = 2;
                    InsignificantCoordinator.this.mInsiginificantHandler.removeMessages(2);
                    InsignificantCoordinator.this.mInsiginificantHandler.sendMessageDelayed(messageObtain, 0L);
                }
            }

            @Override // com.android.systemui.statusbar.notification.headsup.OnHeadsUpChangedListener
            public /* bridge */ /* synthetic */ void onHeadsUpAnimatingAwayEnded(NotificationEntry notificationEntry) {
            }

            @Override // com.android.systemui.statusbar.notification.headsup.OnHeadsUpChangedListener
            public /* bridge */ /* synthetic */ void onHeadsUpPinned(NotificationEntry notificationEntry) {
            }

            @Override // com.android.systemui.statusbar.notification.headsup.OnHeadsUpChangedListener
            public /* bridge */ /* synthetic */ void onHeadsUpPinnedModeChanged(boolean z) {
            }

            @Override // com.android.systemui.statusbar.notification.headsup.OnHeadsUpChangedListener
            public /* bridge */ /* synthetic */ void onHeadsUpUnPinned(NotificationEntry notificationEntry) {
            }
        };
        this.mOnHeadsUpChangedListener = onHeadsUpChangedListener;
        this.mInsiginificantHandler = new Handler() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.InsignificantCoordinator.9
            @Override // android.os.Handler
            public void handleMessage(Message message) throws Resources.NotFoundException {
                int i = message.what;
                if (i == 0) {
                    InsignificantCoordinator.this.updateInsignificantSummary(true);
                } else {
                    if (i != 2) {
                        return;
                    }
                    InsignificantCoordinator.this.updateInsignificantSummary(false);
                }
            }
        };
        this.mInflationErrorListener = new NotifInflationErrorManager.NotifInflationErrorListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.InsignificantCoordinator.10
            @Override // com.android.systemui.statusbar.notification.row.NotifInflationErrorManager.NotifInflationErrorListener
            public void onNotifInflationError(NotificationEntry notificationEntry, Exception exc) throws Resources.NotFoundException {
                if (notificationEntry.mSbn.getGroupKey().contains("INSIGNIFICANT")) {
                    Log.d(InsignificantCoordinator.this.TAG, "onNotifInflationError mWaitingForGroupSummary = false ");
                    InsignificantCoordinator.this.mWaitingForGroupSummary = false;
                    InsignificantCoordinator.this.mNotificationManager.cancelAsUser("INSIGNIFICANT", 123, UserHandle.CURRENT);
                    InsignificantCoordinator.this.updateInsignificantSummary(false);
                }
            }

            @Override // com.android.systemui.statusbar.notification.row.NotifInflationErrorManager.NotifInflationErrorListener
            public /* bridge */ /* synthetic */ void onNotifInflationErrorCleared(NotificationEntry notificationEntry) {
            }
        };
        this.mNotificationManager = notificationManager;
        this.mContext = context;
        this.mSilentNodeController = nodeController;
        this.mSilentHeaderController = sectionHeaderController;
        this.mTimeSortCoordinator = notifTimeSortCoordnator;
        this.mNotifErrorManager = notifInflationErrorManager;
        this.mCommonNotifCollectionLazy = lazy;
        this.mNotifLiveDataStoreImpl = notifLiveDataStoreImpl;
        this.mLockscreenUserManager = notificationLockscreenUserManager;
        this.mKeyguardNotificationVisibilityProvider = keyguardNotificationVisibilityProvider;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.mDebugModeFilterProvider = debugModeFilterProvider;
        SysuiStatusBarStateController sysuiStatusBarStateController = (SysuiStatusBarStateController) statusBarStateController;
        this.mStatusBarStateController = sysuiStatusBarStateController;
        sysuiStatusBarStateController.addCallback(stateListener);
        this.mHeadsUpManager = headsUpManager;
        ((HeadsUpManagerImpl) headsUpManager).addListener(onHeadsUpChangedListener);
        this.mNotifPipeline = notifPipeline;
        ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).registerCallback(onChangedCallback, uriArr);
        ((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).registerCallback(keyguardUpdateMonitorCallback);
        if (NotiRune.NOTI_SUBSCREEN_ALL) {
            this.mSubscreenController = subscreenNotificationController;
        }
        this.mBubbleCoordinator = bubbleCoordinator;
        bubbleCoordinator.setUpdateInsignificantGroupRunnable(new Runnable() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.InsignificantCoordinator$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                this.f$0.lambda$new$0();
            }
        });
    }

    private boolean isDesktopLauncherFilterOut(NotificationEntry notificationEntry) {
        StatusBarNotification statusBarNotification = notificationEntry.mSbn;
        return "com.sec.android.app.desktoplauncher".equals(statusBarNotification.getPackageName()) && notificationEntry.mRanking.getChannel() != null && notificationEntry.mRanking.getChannel().getId().equals("desktop_launcher_chnnel_id") && (statusBarNotification.getNotification().flags & 64) != 0;
    }

    private boolean isDeviceProvisionedFilterOut(StatusBarNotification statusBarNotification) {
        return (this.mKeyguardUpdateMonitor.mDeviceProvisioned || statusBarNotification.getNotification().extras.getBoolean("android.allowDuringSetup")) ? false : true;
    }

    private boolean isEdgeLightFilterOut(NotificationEntry notificationEntry) {
        StatusBarNotification statusBarNotification = notificationEntry.mSbn;
        return ("com.android.systemui".equals(statusBarNotification.getPackageName()) || "com.samsung.android.app.cocktailbarservice".equals(statusBarNotification.getPackageName())) && notificationEntry.mRanking.getChannel() != null && notificationEntry.mRanking.getChannel().getId().equals("edge_lighting_chnnel_id") && (statusBarNotification.getNotification().flags & 64) != 0;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$new$0() {
        Message messageObtain = Message.obtain();
        messageObtain.what = 2;
        this.mInsiginificantHandler.removeMessages(2);
        this.mInsiginificantHandler.sendMessageDelayed(messageObtain, 0L);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetInsignificant() throws Resources.NotFoundException {
        Log.d(this.TAG, "SettingValue changed Promotion : " + ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isEnableInsignificantPromotion() + " BG activities : " + ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isEnableInsignificantBgActivities() + " Minimized : " + ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isEnableInsignificantMinimized() + " Old : " + ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isEnableInsignificantOld());
        Collection<NotificationEntry> allNotifs = ((NotifPipeline) ((CommonNotifCollection) this.mCommonNotifCollectionLazy.get())).getAllNotifs();
        this.mChildren.clear();
        for (NotificationEntry notificationEntry : allNotifs) {
            if ((notificationEntry.mSbn.getNotification().semFlags & NetworkAnalyticsConstants.DataPoints.FLAG_INTERFACE_NAME) != 0 || (notificationEntry.mSbn.getNotification().semFlags & 1048576) != 0 || (notificationEntry.mSbn.getNotification().semFlags & 2097152) != 0 || (notificationEntry.mSbn.getNotification().semFlags & 4194304) != 0) {
                ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
                if (expandableNotificationRow != null) {
                    expandableNotificationRow.updateBackgroundColors();
                }
                if (notificationEntry.isInsignificant()) {
                    this.mChildren.add(notificationEntry);
                }
            }
        }
        NotifPipeline notifPipeline = this.mNotifPipeline;
        if (notifPipeline != null) {
            notifPipeline.mShadeListBuilder.buildList();
        }
        if (this.mChildren.isEmpty()) {
            this.mNotificationManager.cancelAsUser("INSIGNIFICANT", 123, UserHandle.CURRENT);
        } else {
            updateInsignificantSummary(false);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateInsignificantSummary(boolean z) throws Resources.NotFoundException {
        ExpandableNotificationRow expandableNotificationRow;
        ExpandableNotificationRow expandableNotificationRow2;
        SubscreenNotificationController subscreenNotificationController;
        boolean z2;
        SubscreenNotificationController subscreenNotificationController2;
        Collection<NotificationEntry> allNotifs = ((NotifPipeline) ((CommonNotifCollection) this.mCommonNotifCollectionLazy.get())).getAllNotifs();
        ArrayList arrayList = new ArrayList();
        NotificationEntry notificationEntry = null;
        boolean z3 = false;
        for (NotificationEntry notificationEntry2 : allNotifs) {
            if (((NotificationLockscreenUserManagerImpl) this.mLockscreenUserManager).isCurrentProfile(notificationEntry2.mSbn.getUser().getIdentifier())) {
                if (notificationEntry2.mSbn.getGroupKey().contains("INSIGNIFICANT")) {
                    notificationEntry = notificationEntry2;
                }
                if (notificationEntry2.isInsignificant()) {
                    if (!notificationEntry2.mSbn.getGroupKey().contains("INSIGNIFICANT")) {
                        z2 = false;
                    } else if (((StatusBarStateControllerImpl) this.mStatusBarStateController).mUpcomingState != 1) {
                        z2 = true;
                    } else if (NotiRune.NOTI_SUBSCREEN_ALL && (subscreenNotificationController2 = this.mSubscreenController) != null && subscreenNotificationController2.mDeviceModel.isSubScreen()) {
                        z3 = false;
                        z2 = true;
                    } else {
                        z3 = true;
                        z2 = true;
                    }
                    if (!notificationEntry2.mSbn.getNotification().isGroupSummary() && !((HeadsUpManagerImpl) this.mHeadsUpManager).isHeadsUpEntry(notificationEntry2.mKey)) {
                        if (this.mDebugModeFilterProvider.shouldFilterOut(notificationEntry2)) {
                            if (z2) {
                                z3 = true;
                            }
                        } else if (this.mKeyguardUpdateMonitor.mDeviceProvisioned || isDeviceProvisionedFilterOut(notificationEntry2.mSbn)) {
                            if (this.mStatusBarStateController.isDozing() && notificationEntry2.shouldSuppressVisualEffect(128)) {
                                if (z2) {
                                    z3 = true;
                                }
                            } else if (((KeyguardNotificationVisibilityProviderImpl) this.mKeyguardNotificationVisibilityProvider).shouldHideNotification(notificationEntry2)) {
                                if (z2) {
                                    z3 = true;
                                }
                            } else if (notificationEntry2.shouldSuppressVisualEffect(256)) {
                                if (z2) {
                                    z3 = true;
                                }
                            } else if (notificationEntry2.mRanking.isSuspended()) {
                                if (z2) {
                                    z3 = true;
                                }
                            } else if (!isDesktopLauncherFilterOut(notificationEntry2) && !isEdgeLightFilterOut(notificationEntry2) && !this.mBubbleCoordinator.isBubbleNotificationSuppressed(notificationEntry2)) {
                                StatusBarNotification statusBarNotification = notificationEntry2.mSbn;
                                MediaDataManager.Companion.getClass();
                                if (!MediaDataManager.Companion.isMediaNotification(statusBarNotification) && !z2) {
                                    arrayList.add(notificationEntry2);
                                }
                            }
                        } else if (z2) {
                            z3 = true;
                        }
                    }
                }
            }
        }
        if (z3) {
            Log.d(this.TAG, "Insignificant summary filtered out by other condition ");
            return;
        }
        if (arrayList.isEmpty() && !this.mWaitingForGroupSummary) {
            Log.d(this.TAG, "no update for Insignificant summary, there is no notifications ");
            this.mNotificationManager.cancelAsUser("INSIGNIFICANT", 123, UserHandle.CURRENT);
            return;
        }
        int size = this.mChildren.size();
        if (arrayList.isEmpty() || this.mWaitingForGroupSummary) {
            Log.d(this.TAG, "update summary without child");
            this.mGroupCount = 0;
            this.mFirstChildKey = null;
        } else {
            arrayList.sort(this.mTimeSortCoordinator.getTimeComparator());
            NotificationEntry notificationEntry3 = (NotificationEntry) arrayList.getFirst();
            int size2 = arrayList.size();
            if (NotiRune.NOTI_SUBSCREEN_ALL && (subscreenNotificationController = this.mSubscreenController) != null) {
                subscreenNotificationController.mDeviceModel.mMoreNotificationCount = size2;
            }
            if (size2 == this.mGroupCount && notificationEntry3.mKey.equals(this.mFirstChildKey) && (expandableNotificationRow2 = notificationEntry3.row) != null && expandableNotificationRow2.isChildInGroup()) {
                Log.d(this.TAG, "do not update summary : " + notificationEntry3.mKey + " size: " + size2);
                return;
            }
            this.mGroupCount = size2;
            this.mFirstChildKey = notificationEntry3.mKey;
            if (notificationEntry != null && (expandableNotificationRow = notificationEntry.row) != null) {
                ((ArrayList) expandableNotificationRow.mInsigificantChildrenList).clear();
                int size3 = arrayList.size();
                int i = 0;
                while (i < size3) {
                    Object obj = arrayList.get(i);
                    i++;
                    ((ArrayList) expandableNotificationRow.mInsigificantChildrenList).add((NotificationEntry) obj);
                }
            }
            Log.d(this.TAG, "update summary with firstEntry : " + notificationEntry3.mKey + " size: " + size2);
            size = size2;
        }
        String quantityString = this.mContext.getResources().getQuantityString(R.plurals.notification_insignificant_title, size, Integer.valueOf(size));
        String string = this.mContext.getResources().getString(R.string.notification_inginificatn_header_text);
        Notification.Builder builder = new Notification.Builder(this.mContext, NotificationChannels.INSIGNIFICANT);
        builder.setContentTitle(quantityString).setVisibility(1).setGroup("INSIGNIFICANT").setSmallIcon(R.drawable.ic_info).setOngoing(false).setShowWhen(false);
        Bundle bundle = new Bundle();
        bundle.putString("android.substName", string);
        builder.addExtras(bundle);
        this.mNotificationManager.notifyAsUser("INSIGNIFICANT", 123, builder.build(), UserHandle.CURRENT);
        if (this.mWaitingForGroupSummary) {
            Log.d(this.TAG, "mWaitingForGroupSummary = false updateSummary again for children count");
            this.mWaitingForGroupSummary = false;
            Message messageObtain = Message.obtain();
            messageObtain.what = 2;
            this.mInsiginificantHandler.removeMessages(2);
            this.mInsiginificantHandler.sendMessageDelayed(messageObtain, 0L);
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        this.mNotifPipeline = notifPipeline;
        notifPipeline.addFinalizeFilter(this.mNotifFilter);
        notifPipeline.addCollectionListener(this.mNotifCollectionListener);
        notifPipeline.addOnBeforeFinalizeFilterListener(this.mOnBeforeFinalizeFilterListener);
        NotifInflationErrorManager notifInflationErrorManager = this.mNotifErrorManager;
        ((ArrayList) notifInflationErrorManager.mListeners).add(this.mInflationErrorListener);
    }

    public NotifSectioner getInsignificantSectioner() {
        return this.mInsignificantNotifSectioner;
    }
}
