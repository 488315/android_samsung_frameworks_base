package com.android.server.enterprise.proxy;

import android.text.TextUtils;
import android.util.ArrayMap;

import com.samsung.android.knox.net.AuthConfig;
import com.samsung.android.knox.net.ProxyProperties;

import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class LocalProxyManager$$ExternalSyntheticLambda1
        implements BiConsumer {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        String str = (String) obj;
        switch (this.$r8$classId) {
            case 0:
                ProxyProperties proxyProperties = (ProxyProperties) obj2;
                if (str == null) {
                    synchronized (LocalProxyManager.mProxyLock) {
                        ((ArrayMap) LocalProxyManager.sWifiProxyInfoMapCache).remove(str);
                    }
                    return;
                } else {
                    synchronized (LocalProxyManager.mProxyLock) {
                        ((ArrayMap) LocalProxyManager.sWifiProxyInfoMapCache)
                                .put(str, proxyProperties);
                    }
                    return;
                }
            default:
                AuthConfig authConfig = (AuthConfig) obj2;
                if (TextUtils.isEmpty(str)) {
                    return;
                }
                ((ArrayMap) LocalProxyManager.sWifiBackCompatCredentialsMapCache)
                        .put(str, authConfig);
                return;
        }
    }
}
