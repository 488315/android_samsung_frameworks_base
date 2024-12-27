package com.android.server.chimera.psitracker;

import android.hardware.audio.common.V2_0.AudioConfig$$ExternalSyntheticOutline0;

public final class PSIAvailableMemRecord {
    public long availMem;
    public long cached;
    public long checkTime;
    public long running;

    public final String toString() {
        StringBuilder sb = new StringBuilder("<availMem:");
        sb.append(this.availMem);
        sb.append(" running:");
        sb.append(this.running);
        sb.append(" cached:");
        sb.append(this.cached);
        sb.append(" checkTime:");
        return AudioConfig$$ExternalSyntheticOutline0.m(sb, this.checkTime, ">");
    }
}
