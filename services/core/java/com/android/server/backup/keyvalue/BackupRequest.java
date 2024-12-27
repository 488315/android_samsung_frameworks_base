package com.android.server.backup.keyvalue;

import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;

import java.util.Objects;

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
