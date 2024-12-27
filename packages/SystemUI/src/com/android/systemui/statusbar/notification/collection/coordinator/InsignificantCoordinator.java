package com.android.systemui.statusbar.notification.collection.coordinator;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.UserHandle;
import android.provider.Settings;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import android.util.Log;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.Dependency;
import com.android.systemui.NotiRune;
import com.android.systemui.R;
import com.android.systemui.plugins.statusbar.StatusBarStateController;
import com.android.systemui.statusbar.KeyguardBatteryStatus;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.notification.collection.GroupEntry;
import com.android.systemui.statusbar.notification.collection.ListEntry;
import com.android.systemui.statusbar.notification.collection.NotifLiveDataStoreImpl;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifComparator;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter;
import com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner;
import com.android.systemui.statusbar.notification.collection.notifcollection.CommonNotifCollection;
import com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener;
import com.android.systemui.statusbar.notification.collection.render.NodeController;
import com.android.systemui.statusbar.notification.collection.render.SectionHeaderController;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotifInflationErrorManager;
import com.android.systemui.statusbar.policy.BaseHeadsUpManager;
import com.android.systemui.statusbar.policy.HeadsUpManager;
import com.android.systemui.statusbar.policy.OnHeadsUpChangedListener;
import com.android.systemui.util.NotificationChannels;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.cover.CoverState;
import dagger.Lazy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

@CoordinatorScope
public class InsignificantCoordinator implements Coordinator {
    private final String CHANNEL_ID;
    private boolean DEBUG;
    private final int DISMISS_INSIGNIFICANT_CHILD;
    private String TAG = "InsignificantCoordinator";
    private final int UPDATE_INSIGNIFICANT_SUMMARY_ALL;
    private final int UPDATE_INSIGNIFICANT_SUMMARY_VISIBLE;
    private int mBarState;
    private final List<NotificationEntry> mChildren;
    private final Lazy mCommonNotifCollectionLazy;
    private Context mContext;
    private HeadsUpManager mHeadsUpManager;
    private final NotifInflationErrorManager.NotifInflationErrorListener mInflationErrorListener;
    private final Handler mInsiginificantHandler;
    private final NotifSectioner mInsignificantNotifSectioner;
    private final KeyguardUpdateMonitorCallback mKeyguardUpdateMonitorCallback;
    private final NotificationLockscreenUserManager mLockscreenUserManager;
    private final NotifCollectionListener mNotifCollectionListener;
    private final NotifInflationErrorManager mNotifErrorManager;
    private final NotifFilter mNotifFilter;
    private NotifLiveDataStoreImpl mNotifLiveDataStoreImpl;
    private NotifPipeline mNotifPipeline;
    private NotificationManager mNotificationManager;
    private final OnHeadsUpChangedListener mOnHeadsUpChangedListener;
    private boolean mSettingEnabled;
    private final SettingsHelper.OnChangedCallback mSettingsChangedListener;
    private final SectionHeaderController mSilentHeaderController;
    private final NodeController mSilentNodeController;
    private StatusBarStateController mStatusBarStateController;
    private final StatusBarStateController.StateListener mStatusBarStateListener;
    NotifTimeSortCoordnator mTimeSortCoordinator;
    private boolean mWaitingForGroupSummary;

    public InsignificantCoordinator(SectionHeaderController sectionHeaderController, NodeController nodeController, NotificationManager notificationManager, NotifTimeSortCoordnator notifTimeSortCoordnator, NotifInflationErrorManager notifInflationErrorManager, Lazy lazy, StatusBarStateController statusBarStateController, NotifLiveDataStoreImpl notifLiveDataStoreImpl, HeadsUpManager headsUpManager, NotifPipeline notifPipeline, NotificationLockscreenUserManager notificationLockscreenUserManager, Context context) {
        boolean z = NotiRune.NOTI_INSIGNIFICANT;
        this.DEBUG = z;
        this.mChildren = new ArrayList();
        this.CHANNEL_ID = "INSIGNIFICANT";
        this.UPDATE_INSIGNIFICANT_SUMMARY_VISIBLE = 0;
        this.DISMISS_INSIGNIFICANT_CHILD = 1;
        this.UPDATE_INSIGNIFICANT_SUMMARY_ALL = 2;
        this.mWaitingForGroupSummary = false;
        SettingsHelper.OnChangedCallback onChangedCallback = new SettingsHelper.OnChangedCallback() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.InsignificantCoordinator.1
            @Override // com.android.systemui.util.SettingsHelper.OnChangedCallback
            public void onChanged(Uri uri) {
                InsignificantCoordinator.this.resetInsignificant();
            }
        };
        this.mSettingsChangedListener = onChangedCallback;
        KeyguardUpdateMonitorCallback keyguardUpdateMonitorCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.InsignificantCoordinator.2
            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public void onUserSwitchComplete(int i) {
                KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m(new StringBuilder("onUserSwitchComplete user changed : "), ((NotificationLockscreenUserManagerImpl) InsignificantCoordinator.this.mLockscreenUserManager).mCurrentUserId, " userID : ", i, InsignificantCoordinator.this.TAG);
                InsignificantCoordinator.this.resetInsignificant();
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
            public /* bridge */ /* synthetic */ void onBiometricLockoutChanged(boolean z2) {
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
            public /* bridge */ /* synthetic */ void onFaceWidgetFullscreenModeChanged(boolean z2) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onLockDisabledChanged(boolean z2) {
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
            public /* bridge */ /* synthetic */ void onPrimaryBouncerVisibilityChanged(boolean z2) {
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
            public /* bridge */ /* synthetic */ void onTableModeChanged(boolean z2) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onUSBRestrictionChanged(boolean z2) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onUpdateCoverState(CoverState coverState) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onDualDarInnerLockScreenStateChanged(int i, boolean z2) {
            }

            @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
            public /* bridge */ /* synthetic */ void onPackageRemoved(String str, boolean z2) {
            }
        };
        this.mKeyguardUpdateMonitorCallback = keyguardUpdateMonitorCallback;
        this.mNotifFilter = new NotifFilter(this.TAG) { // from class: com.android.systemui.statusbar.notification.collection.coordinator.InsignificantCoordinator.3
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifFilter
            public boolean shouldFilterOut(NotificationEntry notificationEntry, long j) {
                String str;
                StatusBarNotification statusBarNotification = notificationEntry.mSbn;
                Objects.requireNonNull(notificationEntry.getParent());
                if (statusBarNotification.getGroupKey().contains("INSIGNIFICANT") && InsignificantCoordinator.this.mChildren.isEmpty()) {
                    if (InsignificantCoordinator.this.DEBUG) {
                        Log.d(InsignificantCoordinator.this.TAG, "filter out : " + statusBarNotification.getKey());
                    }
                    return true;
                }
                if (!statusBarNotification.getNotification().isGroupSummary()) {
                    return false;
                }
                String groupKey = statusBarNotification.getGroupKey();
                Iterator it = ((Collection) InsignificantCoordinator.this.mNotifLiveDataStoreImpl.activeNotifList.atomicValue.get()).iterator();
                char c = 0;
                while (true) {
                    boolean hasNext = it.hasNext();
                    str = notificationEntry.mKey;
                    if (!hasNext) {
                        break;
                    }
                    NotificationEntry notificationEntry2 = (NotificationEntry) it.next();
                    if (!notificationEntry2.mKey.equals(str) && groupKey.equals(notificationEntry2.mSbn.getGroupKey())) {
                        if (!notificationEntry2.isHighlightsStyle() && !notificationEntry2.isInsignificant()) {
                            c = 2;
                        } else if (c != 2) {
                            c = 1;
                        }
                    }
                }
                if (c == 1) {
                    MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("filter out group summary : ", str, InsignificantCoordinator.this.TAG);
                }
                return c == 1;
            }
        };
        this.mNotifCollectionListener = new NotifCollectionListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.InsignificantCoordinator.4
            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public void onEntryAdded(NotificationEntry notificationEntry) {
                if (InsignificantCoordinator.this.DEBUG) {
                    Log.d(InsignificantCoordinator.this.TAG, "onEntryAdded :" + notificationEntry.mKey + " flags: " + Integer.toHexString(notificationEntry.mSbn.getNotification().semFlags) + " entrySize:" + InsignificantCoordinator.this.mChildren.size());
                }
                if (notificationEntry.mSbn.getGroupKey().contains("INSIGNIFICANT") || notificationEntry.mSbn.getNotification().isGroupSummary()) {
                    return;
                }
                int size = InsignificantCoordinator.this.mChildren.size();
                InsignificantCoordinator.this.mChildren.remove(notificationEntry);
                if (notificationEntry.isInsignificant()) {
                    InsignificantCoordinator.this.mChildren.add(notificationEntry);
                }
                if (size != InsignificantCoordinator.this.mChildren.size() || notificationEntry.isInsignificant()) {
                    Log.d(InsignificantCoordinator.this.TAG, "onEntryAdded : updateSummary entrySize:" + InsignificantCoordinator.this.mChildren.size());
                    Message obtain = Message.obtain();
                    obtain.what = 2;
                    InsignificantCoordinator.this.mInsiginificantHandler.removeMessages(2);
                    InsignificantCoordinator.this.mInsiginificantHandler.sendMessageDelayed(obtain, 0L);
                }
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public void onEntryRemoved(NotificationEntry notificationEntry, int i) {
                if (InsignificantCoordinator.this.DEBUG) {
                    Log.d(InsignificantCoordinator.this.TAG, "onEntryRemoved :" + notificationEntry.mKey + " flags: " + Integer.toHexString(notificationEntry.mSbn.getNotification().semFlags) + " entrySize:" + InsignificantCoordinator.this.mChildren.size());
                }
                if (notificationEntry.mSbn.getGroupKey().contains("INSIGNIFICANT") && !InsignificantCoordinator.this.mChildren.isEmpty()) {
                    Message obtain = Message.obtain();
                    obtain.what = 1;
                    InsignificantCoordinator.this.mInsiginificantHandler.sendMessage(obtain);
                    return;
                }
                InsignificantCoordinator.this.mChildren.remove(notificationEntry);
                if (notificationEntry.isInsignificant()) {
                    Message obtain2 = Message.obtain();
                    obtain2.what = 2;
                    InsignificantCoordinator.this.mInsiginificantHandler.removeMessages(2);
                    InsignificantCoordinator.this.mInsiginificantHandler.sendMessageDelayed(obtain2, 0L);
                }
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public void onEntryUpdated(NotificationEntry notificationEntry, boolean z2) {
                onEntryUpdated(notificationEntry);
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public void onEntryUpdated(NotificationEntry notificationEntry) {
                if (InsignificantCoordinator.this.DEBUG) {
                    Log.d(InsignificantCoordinator.this.TAG, "onEntryUpdated :" + notificationEntry.mKey + " flags: " + Integer.toHexString(notificationEntry.mSbn.getNotification().semFlags) + " entrySize:" + InsignificantCoordinator.this.mChildren.size());
                }
                if (notificationEntry.mSbn.getGroupKey().contains("INSIGNIFICANT") || notificationEntry.mSbn.getNotification().isGroupSummary()) {
                    return;
                }
                int size = InsignificantCoordinator.this.mChildren.size();
                InsignificantCoordinator.this.mChildren.remove(notificationEntry);
                if (notificationEntry.isInsignificant()) {
                    InsignificantCoordinator.this.mChildren.add(notificationEntry);
                }
                if (size != InsignificantCoordinator.this.mChildren.size() || notificationEntry.isInsignificant()) {
                    Log.d(InsignificantCoordinator.this.TAG, "onEntryUpdated : updateSummary entrySize:" + InsignificantCoordinator.this.mChildren.size());
                    Message obtain = Message.obtain();
                    obtain.what = 2;
                    InsignificantCoordinator.this.mInsiginificantHandler.removeMessages(2);
                    InsignificantCoordinator.this.mInsiginificantHandler.sendMessageDelayed(obtain, 0L);
                }
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public /* bridge */ /* synthetic */ void onRankingApplied() {
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
            public /* bridge */ /* synthetic */ void onEntryBind(NotificationEntry notificationEntry, StatusBarNotification statusBarNotification) {
            }

            @Override // com.android.systemui.statusbar.notification.collection.notifcollection.NotifCollectionListener
            public /* bridge */ /* synthetic */ void onNotificationChannelModified(String str, UserHandle userHandle, NotificationChannel notificationChannel, int i) {
            }
        };
        this.mInsignificantNotifSectioner = new NotifSectioner("Insignificant", 13) { // from class: com.android.systemui.statusbar.notification.collection.coordinator.InsignificantCoordinator.5
            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
            public NotifComparator getComparator() {
                return InsignificantCoordinator.this.mTimeSortCoordinator.getTimeComparator();
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
            public NodeController getHeaderNodeController() {
                return null;
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
            public boolean isInSection(ListEntry listEntry) {
                NotificationEntry representativeEntry;
                if (((BaseHeadsUpManager) InsignificantCoordinator.this.mHeadsUpManager).isHeadsUpEntry(listEntry.getKey()) || (representativeEntry = listEntry.getRepresentativeEntry()) == null) {
                    return false;
                }
                boolean isInsignificant = representativeEntry.isInsignificant();
                if (isInsignificant && (listEntry instanceof GroupEntry)) {
                    ((ArrayList) ((GroupEntry) listEntry).mChildren).sort(getComparator());
                }
                return isInsignificant;
            }

            @Override // com.android.systemui.statusbar.notification.collection.listbuilder.pluggable.NotifSectioner
            public void onEntriesUpdated(List<ListEntry> list) {
            }
        };
        StatusBarStateController.StateListener stateListener = new StatusBarStateController.StateListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.InsignificantCoordinator.6
            @Override // com.android.systemui.plugins.statusbar.StatusBarStateController.StateListener
            public void onStatePostChange() {
                if (InsignificantCoordinator.this.mBarState != InsignificantCoordinator.this.mStatusBarStateController.getState()) {
                    InsignificantCoordinator insignificantCoordinator = InsignificantCoordinator.this;
                    insignificantCoordinator.mBarState = insignificantCoordinator.mStatusBarStateController.getState();
                    Message obtain = Message.obtain();
                    obtain.what = 0;
                    InsignificantCoordinator.this.mInsiginificantHandler.removeMessages(2);
                    InsignificantCoordinator.this.mInsiginificantHandler.removeMessages(0);
                    InsignificantCoordinator.this.mInsiginificantHandler.sendMessageDelayed(obtain, 100L);
                }
            }
        };
        this.mStatusBarStateListener = stateListener;
        OnHeadsUpChangedListener onHeadsUpChangedListener = new OnHeadsUpChangedListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.InsignificantCoordinator.7
            @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
            public void onHeadsUpStateChanged(NotificationEntry notificationEntry, boolean z2) {
                Log.d(InsignificantCoordinator.this.TAG, "onHeadsUpStateChanged : " + notificationEntry.mKey + " isHeadsUP :" + z2 + " entry? " + notificationEntry.mIsHeadsUpEntry + " headsupManager? " + ((BaseHeadsUpManager) InsignificantCoordinator.this.mHeadsUpManager).isHeadsUpEntry(notificationEntry.mKey));
                if (notificationEntry.isInsignificant()) {
                    InsignificantCoordinator.this.updateInsignificantSummary(false);
                }
            }

            @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
            public /* bridge */ /* synthetic */ void onHeadsUpPinned(NotificationEntry notificationEntry) {
            }

            @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
            public /* bridge */ /* synthetic */ void onHeadsUpPinnedModeChanged(boolean z2) {
            }

            @Override // com.android.systemui.statusbar.policy.OnHeadsUpChangedListener
            public /* bridge */ /* synthetic */ void onHeadsUpUnPinned(NotificationEntry notificationEntry) {
            }
        };
        this.mOnHeadsUpChangedListener = onHeadsUpChangedListener;
        this.mInsiginificantHandler = new Handler() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.InsignificantCoordinator.8
            @Override // android.os.Handler
            public void handleMessage(Message message) {
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
        this.mInflationErrorListener = new NotifInflationErrorManager.NotifInflationErrorListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.InsignificantCoordinator.9
            @Override // com.android.systemui.statusbar.notification.row.NotifInflationErrorManager.NotifInflationErrorListener
            public void onNotifInflationError(NotificationEntry notificationEntry, Exception exc) {
                if (notificationEntry.mSbn.getGroupKey().contains("INSIGNIFICANT")) {
                    Log.d(InsignificantCoordinator.this.TAG, "onNotifInflationError ");
                    InsignificantCoordinator.this.mNotificationManager.cancel("INSIGNIFICANT", 123);
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
        if (z) {
            this.mStatusBarStateController = statusBarStateController;
            statusBarStateController.addCallback(stateListener);
            this.mHeadsUpManager = headsUpManager;
            ((BaseHeadsUpManager) headsUpManager).addListener(onHeadsUpChangedListener);
            this.mNotifPipeline = notifPipeline;
            this.mSettingEnabled = ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isEnableInsignificant();
            ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).registerCallback(onChangedCallback, Settings.System.getUriFor(SettingsHelper.INDEX_NOTI_SETTINGS_INSIGNIFICANT));
            ((KeyguardUpdateMonitor) Dependency.sDependency.getDependencyInner(KeyguardUpdateMonitor.class)).registerCallback(keyguardUpdateMonitorCallback);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void resetInsignificant() {
        this.mSettingEnabled = ((SettingsHelper) Dependency.sDependency.getDependencyInner(SettingsHelper.class)).isEnableInsignificant();
        int intForUser = Settings.System.getIntForUser(this.mContext.getContentResolver(), SettingsHelper.INDEX_NOTI_SETTINGS_INSIGNIFICANT, 1, -2);
        Log.d(this.TAG, "SettingValue changed : " + this.mSettingEnabled + " value : " + intForUser);
        Collection<NotificationEntry> allNotifs = ((NotifPipeline) ((CommonNotifCollection) this.mCommonNotifCollectionLazy.get())).getAllNotifs();
        this.mChildren.clear();
        for (NotificationEntry notificationEntry : allNotifs) {
            if ((notificationEntry.mSbn.getNotification().semFlags & 262144) != 0) {
                ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
                if (expandableNotificationRow != null) {
                    expandableNotificationRow.updateBackgroundColors();
                }
                if (this.mSettingEnabled) {
                    this.mChildren.add(notificationEntry);
                }
            }
        }
        NotifPipeline notifPipeline = this.mNotifPipeline;
        if (notifPipeline != null) {
            notifPipeline.mShadeListBuilder.buildList();
        }
        if (this.mSettingEnabled) {
            updateInsignificantSummary(false);
        } else {
            this.mNotificationManager.cancelAsUser("INSIGNIFICANT", 123, UserHandle.CURRENT);
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public void updateInsignificantSummary(boolean z) {
        Collection<NotificationEntry> allNotifs = z ? (Collection) this.mNotifLiveDataStoreImpl.activeNotifList.atomicValue.get() : ((NotifPipeline) ((CommonNotifCollection) this.mCommonNotifCollectionLazy.get())).getAllNotifs();
        ArrayList arrayList = new ArrayList();
        for (NotificationEntry notificationEntry : allNotifs) {
            boolean isCurrentProfile = ((NotificationLockscreenUserManagerImpl) this.mLockscreenUserManager).isCurrentProfile(notificationEntry.mSbn.getUser().getIdentifier());
            String str = notificationEntry.mKey;
            if (isCurrentProfile) {
                notificationEntry.mSbn.getGroupKey().contains("INSIGNIFICANT");
                if (notificationEntry.isInsignificant() && !notificationEntry.mSbn.getGroupKey().contains("INSIGNIFICANT") && !notificationEntry.mSbn.getNotification().isGroupSummary() && !((BaseHeadsUpManager) this.mHeadsUpManager).isHeadsUpEntry(str)) {
                    arrayList.add(notificationEntry);
                }
            } else {
                MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m("isCurrentProfile ", str, this.TAG);
            }
        }
        if (arrayList.isEmpty()) {
            Log.d(this.TAG, "no update for summary, there is no notifications ");
            this.mNotificationManager.cancel("INSIGNIFICANT", 123);
            return;
        }
        this.mWaitingForGroupSummary = false;
        arrayList.sort(this.mTimeSortCoordinator.getTimeComparator());
        NotificationEntry notificationEntry2 = (NotificationEntry) arrayList.getFirst();
        Icon smallIcon = notificationEntry2.mSbn.getNotification().getSmallIcon();
        Log.d(this.TAG, "firstEntry : " + notificationEntry2.mKey + " size: " + arrayList.size());
        String quantityString = this.mContext.getResources().getQuantityString(R.plurals.notification_insignificant_title, arrayList.size(), Integer.valueOf(arrayList.size()));
        String string = this.mContext.getResources().getString(R.string.notification_inginificatn_header_text);
        Notification.Builder builder = new Notification.Builder(this.mContext, NotificationChannels.INSIGNIFICANT);
        builder.setContentTitle(quantityString).setVisibility(1).setGroup("INSIGNIFICANT").setSmallIcon(smallIcon).setOngoing(false).setWhen(System.currentTimeMillis());
        Bundle bundle = new Bundle();
        bundle.putString("android.substName", string);
        builder.addExtras(bundle);
        this.mNotificationManager.notifyAsUser("INSIGNIFICANT", 123, builder.build(), UserHandle.CURRENT);
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        this.mNotifPipeline = notifPipeline;
        notifPipeline.addFinalizeFilter(this.mNotifFilter);
        notifPipeline.addCollectionListener(this.mNotifCollectionListener);
        NotifInflationErrorManager notifInflationErrorManager = this.mNotifErrorManager;
        ((ArrayList) notifInflationErrorManager.mListeners).add(this.mInflationErrorListener);
    }

    public NotifSectioner getInsignificantSectioner() {
        return this.mInsignificantNotifSectioner;
    }
}
