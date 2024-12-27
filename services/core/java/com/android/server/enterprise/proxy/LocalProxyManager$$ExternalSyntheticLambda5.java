package com.android.server.enterprise.proxy;

import android.R;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.net.ProxyInfo;
import android.os.UserHandle;
import com.android.internal.notification.SystemNotificationChannels;
import com.android.internal.util.FunctionalUtils;
import com.samsung.android.knox.custom.KnoxCustomManagerService;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class LocalProxyManager$$ExternalSyntheticLambda5 implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ LocalProxyManager f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ LocalProxyManager$$ExternalSyntheticLambda5(LocalProxyManager localProxyManager, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = localProxyManager;
        this.f$1 = obj;
    }

    public final void runOrThrow() {
        switch (this.$r8$classId) {
            case 0:
                LocalProxyManager localProxyManager = this.f$0;
                localProxyManager.getConnectivityManagerService().setGlobalProxy((ProxyInfo) this.f$1);
                localProxyManager.clearProxyServerCache();
                break;
            default:
                LocalProxyManager localProxyManager2 = this.f$0;
                String str = (String) this.f$1;
                localProxyManager2.getClass();
                Intent intent = new Intent();
                intent.addFlags(1350565888);
                intent.setClassName(KnoxCustomManagerService.KNOX_PP_AGENT_PKG_NAME, "com.samsung.android.mdm.EnterpriseProxyAuthDialog");
                intent.putExtra("proxy", str);
                localProxyManager2.mNotificationManager.notifyAsUser("LocalProxyManager", 993, new Notification.Builder(localProxyManager2.mContext, SystemNotificationChannels.NETWORK_ALERTS).setContentTitle(localProxyManager2.mContext.getResources().getString(17042267)).setSmallIcon(R.drawable.stat_sys_warning).setOngoing(true).setOnlyAlertOnce(true).setContentIntent(PendingIntent.getActivity(localProxyManager2.mContext, 0, intent, 335544320)).build(), UserHandle.ALL);
                break;
        }
    }
}
