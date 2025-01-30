package com.android.server;

import android.content.ComponentName;

/* loaded from: classes.dex */
public class ContainerServiceInfo {
    public String category;
    public ComponentName name;
    public int userid;

    public int hashCode() {
        return 0;
    }

    public ContainerServiceInfo(int i, ComponentName componentName, String str) {
        this.userid = i;
        this.name = componentName;
        this.category = str;
    }

    public String toString() {
        return new String("ContainerServiceInfo [" + this.userid + ", name:" + this.name.flattenToShortString() + ", category:" + this.category + "]");
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null) {
            try {
                ContainerServiceInfo containerServiceInfo = (ContainerServiceInfo) obj;
                if (this.userid == containerServiceInfo.userid) {
                    if (this.name.flattenToString().equals(containerServiceInfo.name.flattenToString())) {
                        return true;
                    }
                }
                return false;
            } catch (ClassCastException unused) {
            }
        }
        return false;
    }
}
