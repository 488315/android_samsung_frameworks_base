package com.android.systemui.statusbar.notification.collection.coordinator;

import android.content.res.Configuration;
import com.android.internal.widget.MessagingGroup;
import com.android.internal.widget.MessagingMessage;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.keyguard.KeyguardUpdateMonitorCallback;
import com.android.systemui.statusbar.NotificationLockscreenUserManager;
import com.android.systemui.statusbar.NotificationLockscreenUserManagerImpl;
import com.android.systemui.statusbar.notification.collection.NotifPipeline;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotificationGuts;
import com.android.systemui.statusbar.notification.row.NotificationGutsManager;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import java.util.ArrayList;
import java.util.Iterator;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ViewConfigCoordinator implements Coordinator, ConfigurationController.ConfigurationListener {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final ConfigurationController mConfigurationController;
    public boolean mDispatchUiModeChangeOnUserSwitched;
    public final NotificationGutsManager mGutsManager;
    public boolean mIsSwitchingUser;
    public final KeyguardUpdateMonitor mKeyguardUpdateMonitor;
    public final NotificationLockscreenUserManager mLockscreenUserManager;
    public NotifPipeline mPipeline;
    public boolean mReinflateNotificationsOnUserSwitched;
    public int mThemeSeq;
    public final ViewConfigCoordinator$mKeyguardUpdateCallback$1 mKeyguardUpdateCallback = new KeyguardUpdateMonitorCallback() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.ViewConfigCoordinator$mKeyguardUpdateCallback$1
        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onUserSwitchComplete(int i) {
            int i2 = ViewConfigCoordinator.$r8$clinit;
            ViewConfigCoordinator viewConfigCoordinator = ViewConfigCoordinator.this;
            viewConfigCoordinator.mIsSwitchingUser = false;
            if (viewConfigCoordinator.mReinflateNotificationsOnUserSwitched) {
                viewConfigCoordinator.updateNotificationsOnDensityOrFontScaleChanged();
                viewConfigCoordinator.mReinflateNotificationsOnUserSwitched = false;
            }
            if (viewConfigCoordinator.mDispatchUiModeChangeOnUserSwitched) {
                viewConfigCoordinator.updateNotificationsOnUiModeChanged();
                viewConfigCoordinator.mDispatchUiModeChangeOnUserSwitched = false;
            }
        }

        @Override // com.android.keyguard.KeyguardUpdateMonitorCallback
        public final void onUserSwitching(int i) {
            int i2 = ViewConfigCoordinator.$r8$clinit;
            ViewConfigCoordinator.this.mIsSwitchingUser = true;
        }
    };
    public final ViewConfigCoordinator$mUserChangedListener$1 mUserChangedListener = new NotificationLockscreenUserManager.UserChangedListener() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.ViewConfigCoordinator$mUserChangedListener$1
        @Override // com.android.systemui.statusbar.NotificationLockscreenUserManager.UserChangedListener
        public final void onUserChanged(int i) {
            ViewConfigCoordinator viewConfigCoordinator = ViewConfigCoordinator.this;
            if (viewConfigCoordinator.mReinflateNotificationsOnUserSwitched) {
                viewConfigCoordinator.updateNotificationsOnDensityOrFontScaleChanged();
                viewConfigCoordinator.mReinflateNotificationsOnUserSwitched = false;
            }
            if (viewConfigCoordinator.mDispatchUiModeChangeOnUserSwitched) {
                viewConfigCoordinator.updateNotificationsOnUiModeChanged();
                viewConfigCoordinator.mDispatchUiModeChangeOnUserSwitched = false;
            }
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.statusbar.notification.collection.coordinator.ViewConfigCoordinator$mKeyguardUpdateCallback$1] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.statusbar.notification.collection.coordinator.ViewConfigCoordinator$mUserChangedListener$1] */
    public ViewConfigCoordinator(ConfigurationController configurationController, NotificationLockscreenUserManager notificationLockscreenUserManager, NotificationGutsManager notificationGutsManager, KeyguardUpdateMonitor keyguardUpdateMonitor) {
        this.mConfigurationController = configurationController;
        this.mLockscreenUserManager = notificationLockscreenUserManager;
        this.mGutsManager = notificationGutsManager;
        this.mKeyguardUpdateMonitor = keyguardUpdateMonitor;
    }

    @Override // com.android.systemui.statusbar.notification.collection.coordinator.Coordinator
    public final void attach(NotifPipeline notifPipeline) {
        this.mPipeline = notifPipeline;
        ((ArrayList) ((NotificationLockscreenUserManagerImpl) this.mLockscreenUserManager).mListeners).add(this.mUserChangedListener);
        ((ConfigurationControllerImpl) this.mConfigurationController).addCallback(this);
        this.mKeyguardUpdateMonitor.registerCallback(this.mKeyguardUpdateCallback);
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(Configuration configuration) {
        int i = this.mThemeSeq;
        int i2 = configuration.themeSeq;
        if (i != i2) {
            this.mThemeSeq = i2;
            onUiModeChanged();
        }
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onDensityOrFontScaleChanged() {
        MessagingMessage.dropCache();
        MessagingGroup.dropCache();
        if (this.mIsSwitchingUser) {
            this.mReinflateNotificationsOnUserSwitched = true;
        } else {
            updateNotificationsOnDensityOrFontScaleChanged();
        }
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onThemeChanged() {
        onDensityOrFontScaleChanged();
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onUiModeChanged() {
        if (this.mIsSwitchingUser) {
            this.mDispatchUiModeChangeOnUserSwitched = true;
        } else {
            updateNotificationsOnUiModeChanged();
        }
    }

    public final void updateNotificationsOnDensityOrFontScaleChanged() {
        NotificationGuts notificationGuts;
        NotifPipeline notifPipeline = this.mPipeline;
        if (notifPipeline != null) {
            for (NotificationEntry notificationEntry : notifPipeline.getAllNotifs()) {
                ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
                if (expandableNotificationRow != null) {
                    expandableNotificationRow.onDensityOrFontScaleChanged();
                }
                ExpandableNotificationRow expandableNotificationRow2 = notificationEntry.row;
                if ((expandableNotificationRow2 == null || (notificationGuts = expandableNotificationRow2.mGuts) == null || !notificationGuts.mExposed) ? false : true) {
                    NotificationGutsManager notificationGutsManager = this.mGutsManager;
                    notificationGutsManager.getClass();
                    ExpandableNotificationRow expandableNotificationRow3 = notificationEntry.row;
                    notificationGutsManager.mNotificationGutsExposed = expandableNotificationRow3 != null ? expandableNotificationRow3.mGuts : null;
                    if (expandableNotificationRow3.mGuts == null) {
                        expandableNotificationRow3.mGutsStub.inflate();
                    }
                    notificationGutsManager.bindGuts(expandableNotificationRow3, notificationGutsManager.mGutsMenuItem);
                }
            }
        }
    }

    public final void updateNotificationsOnUiModeChanged() {
        NotifPipeline notifPipeline = this.mPipeline;
        if (notifPipeline != null) {
            Iterator it = notifPipeline.getAllNotifs().iterator();
            while (it.hasNext()) {
                ExpandableNotificationRow expandableNotificationRow = ((NotificationEntry) it.next()).row;
                if (expandableNotificationRow != null) {
                    expandableNotificationRow.onUiModeChanged();
                }
            }
        }
    }
}
