package com.android.server.enterprise.proxy;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.sec.enterprise.proxy.EnterpriseProxyConstants;
import android.text.TextUtils;
import android.util.Log;

import com.android.internal.util.FunctionalUtils;
import com.android.server.enterprise.adapterlayer.PackageManagerAdapter;

import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class LocalProxyManager$$ExternalSyntheticLambda0
        implements FunctionalUtils.ThrowingRunnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ LocalProxyManager f$0;
    public final /* synthetic */ List f$1;

    public /* synthetic */ LocalProxyManager$$ExternalSyntheticLambda0(
            LocalProxyManager localProxyManager, List list, int i) {
        this.$r8$classId = i;
        this.f$0 = localProxyManager;
        this.f$1 = list;
    }

    public final void runOrThrow() {
        ApplicationInfo applicationInfo;
        switch (this.$r8$classId) {
            case 0:
                LocalProxyManager localProxyManager = this.f$0;
                List<String> list = this.f$1;
                localProxyManager.getClass();
                for (String str : list) {
                    if (!TextUtils.isEmpty(str)) {
                        localProxyManager.updateProxyInWifiConfig(str, false, null);
                    }
                }
                break;
            default:
                LocalProxyManager localProxyManager2 = this.f$0;
                List list2 = this.f$1;
                localProxyManager2.getClass();
                for (String str2 : EnterpriseProxyConstants.LOCAL_ENTERPRISE_PROXY_WHITELIST) {
                    try {
                        PackageManagerAdapter.getInstance(localProxyManager2.mContext).getClass();
                        PackageInfo packageInfo =
                                PackageManagerAdapter.getPackageInfo(128, 0, str2);
                        if (packageInfo != null
                                && (applicationInfo = packageInfo.applicationInfo) != null) {
                            list2.add(String.valueOf(applicationInfo.uid));
                        }
                    } catch (Exception e) {
                        Log.e("LocalProxyManager", "Failed to get browser uid list");
                        e.printStackTrace();
                    }
                }
                break;
        }
    }
}
