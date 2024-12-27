package com.android.server;

import android.content.ComponentName;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class ContainerServiceInfo {
    public String category;
    public ComponentName name;
    public int userid;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null) {
            try {
                ContainerServiceInfo containerServiceInfo = (ContainerServiceInfo) obj;
                if (this.userid == containerServiceInfo.userid) {
                    if (this.name
                            .flattenToString()
                            .equals(containerServiceInfo.name.flattenToString())) {
                        return true;
                    }
                }
                return false;
            } catch (ClassCastException unused) {
            }
        }
        return false;
    }

    public final int hashCode() {
        return 0;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("ContainerServiceInfo [");
        sb.append(this.userid);
        sb.append(", name:");
        sb.append(this.name.flattenToShortString());
        sb.append(", category:");
        return AudioOffloadInfo$$ExternalSyntheticOutline0.m(sb, this.category, "]");
    }
}
