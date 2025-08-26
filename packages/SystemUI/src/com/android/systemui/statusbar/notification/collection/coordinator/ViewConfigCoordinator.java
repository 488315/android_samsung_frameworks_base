package com.android.systemui.statusbar.notification.collection.coordinator;

import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Handler;
import android.os.Trace;
import android.util.Log;
import android.util.SparseArray;
import androidx.core.app.NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0;
import com.android.app.tracing.TraceUtilsKt;
import com.android.internal.widget.MessagingGroup;
import com.android.internal.widget.MessagingMessage;
import com.android.keyguard.KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecurityModel;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.statusbar.KeyguardBatteryStatus;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.notification.ColorUpdateLogger;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.collection.coordinator.dagger.CoordinatorScope;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotificationGutsManager;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityData;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityDataHelper;
import com.android.systemui.statusbar.phone.ongoingactivity.OngoingActivityLayoutUtil;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.samsung.android.cover.CoverState;
import com.samsung.android.view.SemWindowManager;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedList;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;

@CoordinatorScope
/* loaded from: classes3.dex */
public final class ViewConfigCoordinator implements Coordinator, ConfigurationController.ConfigurationListener {
    private static final String TAG = "ViewConfigCoordinator";
    private final ColorUpdateLogger colorUpdateLogger;
    private boolean isFoldStateChanged;
    private final ConfigurationController mConfigurationController;
    private boolean mDispatchUiModeChangeOnUserSwitched;
    private final NotificationGutsManager mGutsManager;
    private boolean mIsSwitchingUser;
    private final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    private final NotificationLockscreenUserManager mLockscreenUserManager;
    private NotifPipeline mPipeline;
    private boolean mReinflateNotificationsOnUserSwitched;
    private int mThemeSeq;
    public static final Companion Companion = new Companion(null);
    public static final int $stable = 8;
    private static final boolean DEBUG = false;
    private final ViewConfigCoordinator$mKeyguardUpdateCallback$1 mKeyguardUpdateCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.ViewConfigCoordinator$mKeyguardUpdateCallback$1
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onUserSwitchComplete(int i) throws Resources.NotFoundException, PackageManager.NameNotFoundException {
            ColorUpdateLogger colorUpdateLogger = this.this$0.colorUpdateLogger;
            ColorUpdateLogger.Companion companion = ColorUpdateLogger.Companion;
            colorUpdateLogger.getClass();
            if (ViewConfigCoordinator.DEBUG) {
                NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m(i, "ViewConfigCoordinator.onUserSwitchComplete(userId=", ")", "ViewConfigCoordinator");
            }
            this.this$0.mIsSwitchingUser = false;
            this.this$0.applyChangesOnUserSwitched();
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public void onUserSwitching(int i) {
            ColorUpdateLogger colorUpdateLogger = this.this$0.colorUpdateLogger;
            ColorUpdateLogger.Companion companion = ColorUpdateLogger.Companion;
            colorUpdateLogger.getClass();
            if (ViewConfigCoordinator.DEBUG) {
                NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m(i, "ViewConfigCoordinator.onUserSwitching(userId=", ")", "ViewConfigCoordinator");
            }
            this.this$0.mIsSwitchingUser = true;
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
    private final ViewConfigCoordinator$mUserChangedListener$1 mUserChangedListener = new NotificationLockscreenUserManager.UserChangedListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.ViewConfigCoordinator$mUserChangedListener$1
        @Override // com.android.systemui.statusbar.NotificationLockscreenUserManager.UserChangedListener
        public void onUserChanged(int i) throws Resources.NotFoundException, PackageManager.NameNotFoundException {
            ColorUpdateLogger colorUpdateLogger = this.this$0.colorUpdateLogger;
            ColorUpdateLogger.Companion companion = ColorUpdateLogger.Companion;
            colorUpdateLogger.getClass();
            if (ViewConfigCoordinator.DEBUG) {
                NotificationManagerCompat$SideChannelManager$$ExternalSyntheticOutline0.m(i, "ViewConfigCoordinator.onUserChanged(userId=", ")", "ViewConfigCoordinator");
            }
            this.this$0.applyChangesOnUserSwitched();
        }

        @Override // com.android.systemui.statusbar.NotificationLockscreenUserManager.UserChangedListener
        public /* bridge */ /* synthetic */ void onCurrentProfilesChanged(SparseArray sparseArray) {
        }

        @Override // com.android.systemui.statusbar.NotificationLockscreenUserManager.UserChangedListener
        public /* bridge */ /* synthetic */ void onUserRemoved(int i) {
        }
    };
    private final SemWindowManager.FoldStateListener foldStateListener = new SemWindowManager.FoldStateListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.ViewConfigCoordinator$foldStateListener$1
        public void onFoldStateChanged(boolean z) {
            this.this$0.isFoldStateChanged = true;
        }

        public void onTableModeChanged(boolean z) {
        }
    };

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.notification.collection.coordinator.ViewConfigCoordinator$mKeyguardUpdateCallback$1] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.statusbar.notification.collection.coordinator.ViewConfigCoordinator$mUserChangedListener$1] */
    public ViewConfigCoordinator(ConfigurationController configurationController, NotificationLockscreenUserManager notificationLockscreenUserManager, NotificationGutsManager notificationGutsManager, KeyguardUpdateMonitor keyguardUpdateMonitor, ColorUpdateLogger colorUpdateLogger) {
        this.mConfigurationController = configurationController;
        this.mLockscreenUserManager = notificationLockscreenUserManager;
        this.mGutsManager = notificationGutsManager;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
        this.colorUpdateLogger = colorUpdateLogger;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public final void applyChangesOnUserSwitched() throws Resources.NotFoundException, PackageManager.NameNotFoundException {
        ColorUpdateLogger colorUpdateLogger = this.colorUpdateLogger;
        ColorUpdateLogger.Companion companion = ColorUpdateLogger.Companion;
        colorUpdateLogger.getClass();
        if (this.mReinflateNotificationsOnUserSwitched) {
            updateNotificationsOnDensityOrFontScaleChanged();
            this.mReinflateNotificationsOnUserSwitched = false;
        }
        if (this.mDispatchUiModeChangeOnUserSwitched) {
            updateNotificationsOnUiModeChanged();
            this.mDispatchUiModeChangeOnUserSwitched = false;
        }
    }

    private final void log(Function0 function0) {
        if (DEBUG) {
            Log.d(TAG, (String) function0.invoke());
        }
    }

    private final void updateNotificationsOnDensityOrFontScaleChanged() throws Resources.NotFoundException, PackageManager.NameNotFoundException {
        Collection allNotifs;
        ColorUpdateLogger colorUpdateLogger = this.colorUpdateLogger;
        ColorUpdateLogger.Companion companion = ColorUpdateLogger.Companion;
        colorUpdateLogger.getClass();
        OngoingActivityDataHelper.INSTANCE.getClass();
        OngoingActivityDataHelper.reinflateOngoingActivity();
        NotifPipeline notifPipeline = this.mPipeline;
        if (notifPipeline == null || (allNotifs = notifPipeline.getAllNotifs()) == null) {
            return;
        }
        Iterator it = allNotifs.iterator();
        while (it.hasNext()) {
            ExpandableNotificationRow expandableNotificationRow = ((NotificationEntry) it.next()).row;
            if (expandableNotificationRow != null) {
                expandableNotificationRow.initDimens$3();
                expandableNotificationRow.applyRoundnessAndInvalidate();
                expandableNotificationRow.initDimens$1();
                expandableNotificationRow.initBackground();
                expandableNotificationRow.reInflateViews$1();
            }
            this.mGutsManager.closeAndUndoGuts();
        }
    }

    private final void updateNotificationsOnUiModeChanged() {
        Collection allNotifs;
        ColorUpdateLogger colorUpdateLogger = this.colorUpdateLogger;
        ((ConfigurationControllerImpl) this.mConfigurationController).getClass();
        colorUpdateLogger.getClass();
        if (DEBUG) {
            Log.d(TAG, "ViewConfigCoordinator.updateNotificationsOnUiModeChanged()");
        }
        boolean zIsEnabled = Trace.isEnabled();
        if (zIsEnabled) {
            TraceUtilsKt.beginSlice("updateNotifOnUiModeChanged");
        }
        try {
            OngoingActivityDataHelper.INSTANCE.getClass();
            OngoingActivityDataHelper.reinflateOngoingActivity();
            NotifPipeline notifPipeline = this.mPipeline;
            if (notifPipeline != null && (allNotifs = notifPipeline.getAllNotifs()) != null) {
                Iterator it = allNotifs.iterator();
                while (it.hasNext()) {
                    ExpandableNotificationRow expandableNotificationRow = ((NotificationEntry) it.next()).row;
                    if (expandableNotificationRow != null) {
                        expandableNotificationRow.onUiModeChanged();
                    }
                    this.mGutsManager.closeAndUndoGuts();
                }
                Unit unit = Unit.INSTANCE;
            }
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
        } catch (Throwable th) {
            if (zIsEnabled) {
                TraceUtilsKt.endSlice();
            }
            throw th;
        }
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public void attach(NotifPipeline notifPipeline) {
        this.mPipeline = notifPipeline;
        ((NotificationLockscreenUserManagerImpl) this.mLockscreenUserManager).addUserChangedListener(this.mUserChangedListener);
        ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this);
        this.mKeyguardUpdateMonitor.registerCallback(this.mKeyguardUpdateCallback);
        SemWindowManager.getInstance().registerFoldStateListener(this.foldStateListener, (Handler) null);
    }

    public final SemWindowManager.FoldStateListener getFoldStateListener() {
        return this.foldStateListener;
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public void onConfigChanged(Configuration configuration) {
        int i = this.mThemeSeq;
        int i2 = configuration.themeSeq;
        if (i != i2) {
            this.mThemeSeq = i2;
            onUiModeChanged();
        }
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public void onDensityOrFontScaleChanged() throws Resources.NotFoundException, PackageManager.NameNotFoundException {
        ColorUpdateLogger colorUpdateLogger = this.colorUpdateLogger;
        ColorUpdateLogger.Companion companion = ColorUpdateLogger.Companion;
        colorUpdateLogger.getClass();
        if (DEBUG) {
            KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("ViewConfigCoordinator.onDensityOrFontScaleChanged() isSwitchingUser=", " keyguardUpdateMonitor.isSwitchingUser=", TAG, this.mIsSwitchingUser, this.mKeyguardUpdateMonitor.mSwitchingUser);
        }
        MessagingMessage.dropCache();
        MessagingGroup.dropCache();
        if (this.mIsSwitchingUser) {
            this.mReinflateNotificationsOnUserSwitched = true;
        } else {
            updateNotificationsOnDensityOrFontScaleChanged();
        }
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public void onMaxBoundsChanged() throws Resources.NotFoundException, PackageManager.NameNotFoundException {
        if (this.isFoldStateChanged) {
            OngoingActivityDataHelper.INSTANCE.getClass();
            OngoingActivityDataHelper.reinflateOngoingActivity();
            LinkedList<OngoingActivityData> linkedList = new LinkedList();
            linkedList.addAll(OngoingActivityDataHelper.mOngoingActivityLists);
            linkedList.addAll(OngoingActivityDataHelper.hiddenOngoingActivityDataList);
            for (OngoingActivityData ongoingActivityData : linkedList) {
                OngoingActivityLayoutUtil.INSTANCE.getClass();
                if (OngoingActivityLayoutUtil.isActionStyle(ongoingActivityData)) {
                    ExpandableNotificationRow expandableNotificationRow = ongoingActivityData.mNotificationEntry.row;
                    if (expandableNotificationRow != null) {
                        expandableNotificationRow.reInflateViews$1();
                    }
                    this.mGutsManager.closeAndUndoGuts();
                }
            }
            this.isFoldStateChanged = false;
        }
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public void onThemeChanged() throws Resources.NotFoundException, PackageManager.NameNotFoundException {
        ColorUpdateLogger colorUpdateLogger = this.colorUpdateLogger;
        ColorUpdateLogger.Companion companion = ColorUpdateLogger.Companion;
        colorUpdateLogger.getClass();
        onDensityOrFontScaleChanged();
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public void onUiModeChanged() {
        ColorUpdateLogger colorUpdateLogger = this.colorUpdateLogger;
        ColorUpdateLogger.Companion companion = ColorUpdateLogger.Companion;
        colorUpdateLogger.getClass();
        if (DEBUG) {
            KeyguardKnoxGuardViewController$$ExternalSyntheticOutline0.m("ViewConfigCoordinator.onUiModeChanged() isSwitchingUser=", " keyguardUpdateMonitor.isSwitchingUser=", TAG, this.mIsSwitchingUser, this.mKeyguardUpdateMonitor.mSwitchingUser);
        }
        if (this.mIsSwitchingUser) {
            this.mDispatchUiModeChangeOnUserSwitched = true;
        } else {
            updateNotificationsOnUiModeChanged();
        }
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public /* bridge */ /* synthetic */ void onLayoutDirectionChanged(boolean z) {
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public /* bridge */ /* synthetic */ void onOrientationChanged(int i) {
    }

    public static /* synthetic */ void getFoldStateListener$annotations() {
    }

    private static /* synthetic */ void getMThemeSeq$annotations() {
    }

    private static /* synthetic */ void isFoldStateChanged$annotations() {
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public /* bridge */ /* synthetic */ void onDisplayDeviceTypeChanged() {
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public /* bridge */ /* synthetic */ void onLocaleListChanged() {
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public /* bridge */ /* synthetic */ void onSmallestScreenWidthChanged() {
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public /* bridge */ /* synthetic */ void onMovedToDisplay(int i, Configuration configuration) {
    }
}
