package com.android.server.backup.keyvalue;

import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;

import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class BackupRequest {
    public String packageName;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof BackupRequest) {
            return Objects.equals(this.packageName, ((BackupRequest) obj).packageName);
        }
        return false;
    }

    public final int hashCode() {
        return Objects.hash(this.packageName);
    }

    public final String toString() {
        return AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                new StringBuilder("BackupRequest{pkg="), this.packageName, "}");
    }
}
