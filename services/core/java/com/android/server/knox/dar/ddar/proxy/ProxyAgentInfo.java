package com.android.server.knox.dar.ddar.proxy;

import android.content.ComponentName;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ProxyAgentInfo {
    public final ComponentName mCompName;
    public final String mName;
    public final int mUserId;

    public ProxyAgentInfo(ComponentName componentName, int i, String str) {
        this.mName = str;
        this.mUserId = i;
        this.mCompName = componentName;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null) {
            try {
                ProxyAgentInfo proxyAgentInfo = (ProxyAgentInfo) obj;
                if (this.mName.equalsIgnoreCase(proxyAgentInfo.mName)
                        && this.mUserId == proxyAgentInfo.mUserId) {
                    if (this.mCompName
                            .flattenToString()
                            .equals(proxyAgentInfo.mCompName.flattenToString())) {
                        return true;
                    }
                }
                return false;
            } catch (ClassCastException e) {
                e.printStackTrace();
            }
        }
        return false;
    }

    public final String toString() {
        return String.format(
                "ProxyAgentInfo {mName:%s mCompName:%s mUserId:%d}",
                this.mName, this.mCompName, Integer.valueOf(this.mUserId));
    }
}
