package com.android.systemui.statusbar.tv;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import com.android.internal.net.VpnConfig;
import com.android.systemui.CoreStartable;
import com.android.systemui.R;
import com.android.systemui.statusbar.policy.SecurityController;
import com.android.systemui.statusbar.policy.SecurityControllerImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class VpnStatusObserver implements CoreStartable, SecurityController.SecurityControllerCallback {
    public static final String NOTIFICATION_TAG;
    public final Context context;
    public final NotificationManager notificationManager;
    public final SecurityController securityController;
    public boolean vpnConnected;
    public final Notification.Builder vpnConnectedNotificationBuilder;
    public final Notification vpnDisconnectedNotification;

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
        NOTIFICATION_TAG = "VpnStatusObserver";
    }

    public VpnStatusObserver(Context context, SecurityController securityController) {
        this.context = context;
        this.securityController = securityController;
        NotificationManager from = NotificationManager.from(context);
        this.notificationManager = from;
        from.createNotificationChannel(new NotificationChannel("VPN Status", "VPN Status", 4));
        this.vpnConnectedNotificationBuilder = new Notification.Builder(context, "VPN Status").setSmallIcon(getVpnIconId()).setVisibility(1).setCategory("sys").extend(new Notification.TvExtender()).setOngoing(true).setContentTitle(context.getString(R.string.notification_vpn_connected)).setContentIntent(VpnConfig.getIntentForStatusPanel(context));
        this.vpnDisconnectedNotification = new Notification.Builder(context, "VPN Status").setSmallIcon(getVpnIconId()).setVisibility(1).setCategory("sys").extend(new Notification.TvExtender()).setTimeoutAfter(5000L).setContentTitle(context.getString(R.string.notification_vpn_disconnected)).build();
    }

    public final int getVpnIconId() {
        SecurityController securityController = this.securityController;
        return ((SecurityControllerImpl) securityController).isVpnBranded() ? R.drawable.stat_sys_branded_vpn : ((SecurityControllerImpl) securityController).isSecureWifiEnabled() ? R.drawable.stat_sys_securewifi_ic : R.drawable.stat_sys_vpn_ic;
    }

    @Override // com.android.systemui.statusbar.policy.SecurityController.SecurityControllerCallback
    public final void onStateChanged() {
        SecurityController securityController = this.securityController;
        boolean isVpnEnabled = ((SecurityControllerImpl) securityController).isVpnEnabled();
        if (this.vpnConnected != isVpnEnabled) {
            String str = NOTIFICATION_TAG;
            NotificationManager notificationManager = this.notificationManager;
            if (isVpnEnabled) {
                SecurityControllerImpl securityControllerImpl = (SecurityControllerImpl) securityController;
                String primaryVpnName = securityControllerImpl.getPrimaryVpnName();
                if (primaryVpnName == null) {
                    primaryVpnName = securityControllerImpl.getWorkProfileVpnName();
                }
                Notification.Builder builder = this.vpnConnectedNotificationBuilder;
                if (primaryVpnName != null) {
                    builder.setContentText(this.context.getString(R.string.notification_disclosure_vpn_text, primaryVpnName));
                }
                notificationManager.notify(str, 20, builder.build());
            } else {
                notificationManager.cancel(str, 20);
                notificationManager.notify(str, 17, this.vpnDisconnectedNotification);
            }
            this.vpnConnected = isVpnEnabled;
        }
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        ((SecurityControllerImpl) this.securityController).addCallback(this);
    }
}
