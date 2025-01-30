package com.android.server.knox.dar.ddar.proxy;

import android.content.ComponentName;

/* loaded from: classes2.dex */
public class ProxyAgentInfo {
    public ComponentName mCompName;
    public String mName;
    public int mUserId;

    public ProxyAgentInfo(String str, int i, ComponentName componentName) {
        this.mName = str;
        this.mUserId = i;
        this.mCompName = componentName;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null) {
            try {
                ProxyAgentInfo proxyAgentInfo = (ProxyAgentInfo) obj;
                if (this.mName.equalsIgnoreCase(proxyAgentInfo.mName) && this.mUserId == proxyAgentInfo.mUserId) {
                    if (this.mCompName.flattenToString().equals(proxyAgentInfo.mCompName.flattenToString())) {
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

    public String toString() {
        return String.format("ProxyAgentInfo {mName:%s mCompName:%s mUserId:%d}", this.mName, this.mCompName, Integer.valueOf(this.mUserId));
    }
}
