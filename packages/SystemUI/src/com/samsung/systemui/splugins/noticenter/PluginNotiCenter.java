package com.samsung.systemui.splugins.noticenter;

import android.os.Bundle;
import android.service.notification.NotificationListenerService;
import android.service.notification.StatusBarNotification;
import android.widget.RemoteViews;
import com.samsung.systemui.splugins.SPlugin;
import com.samsung.systemui.splugins.annotations.ProvidesInterface;
import com.samsung.systemui.splugins.annotations.Requires;
import com.sec.ims.volte2.data.VolteConstants;
import java.io.FileDescriptor;
import java.io.PrintWriter;

@ProvidesInterface(action = PluginNotiCenter.ACTION, version = PluginNotiCenter.VERSION)
/* loaded from: classes4.dex */
public interface PluginNotiCenter extends SPlugin {
    public static final String ACTION = "com.samsung.systemui.action.PLUGIN_NOTICENTER";
    public static final int MAJOR_VERSION = 9;
    public static final int MINOR_VERSION = 1;
    public static final int VERSION = 9001;

    public interface Callback {
        void onChangedVisibilityOnKeyguard(boolean z);

        void onNoclearAppListUpdate(Bundle bundle);

        void onNoclearUpdate(boolean z);

        void onNotiCenterPanelUpdate(RemoteViews remoteViews);

        void onNotiStarPanelShowOnKeyguard(boolean z);

        void ongoingDismissableUpdate(boolean z);
    }

    @Requires(target = PluginNotiCenter.class, version = VolteConstants.ErrorCode.MDMN_CALL_FORWARDED)
    void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr);

    void enterKeyguard();

    @Override // com.samsung.systemui.splugins.SPlugin
    default int getVersion() {
        return VERSION;
    }

    @Deprecated
    void insert(StatusBarNotification statusBarNotification, NotificationListenerService.RankingMap rankingMap);

    void requestNotify(StatusBarNotification statusBarNotification);

    @Requires(target = PluginNotiCenter.class, version = VolteConstants.ErrorCode.CANCEL_CALL_COMPLETED_ELSEWHERE)
    void requestVocHelp(Bundle bundle, StatusBarNotification statusBarNotification);

    void setCallback(Callback callback);

    @Requires(target = PluginNotiCenter.class, version = VERSION)
    void setNowBarExpandMode(boolean z);

    void unLock();

    void updateSettings(Bundle bundle);
}
