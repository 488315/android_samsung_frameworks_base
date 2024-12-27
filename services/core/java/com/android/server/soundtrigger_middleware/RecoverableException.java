package com.android.server.soundtrigger_middleware;

import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public class RecoverableException extends RuntimeException {
    public final int errorCode;

    public RecoverableException(int i) {
        this.errorCode = i;
    }

    public RecoverableException(int i, String str) {
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
