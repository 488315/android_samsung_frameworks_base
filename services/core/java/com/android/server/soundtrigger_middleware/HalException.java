package com.android.server.soundtrigger_middleware;

import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;

public class HalException extends RuntimeException {
    public final int errorCode;

    public HalException(int i, String str) {
        super(str);
        this.errorCode = i;
    }

    @Override // java.lang.Throwable
    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(super.toString());
        sb.append(" (code ");
        return AmFmBandRange$$ExternalSyntheticOutline0.m(this.errorCode, sb, ")");
    }
}
