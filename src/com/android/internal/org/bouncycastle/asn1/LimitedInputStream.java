package com.android.internal.org.bouncycastle.asn1;

import java.io.InputStream;

/* loaded from: classes5.dex */
abstract class LimitedInputStream extends InputStream {
    protected final InputStream _in;
    private int _limit;

    LimitedInputStream(InputStream inputStream, int i) {
        this._in = inputStream;
        this._limit = i;
    }

    int getLimit() {
        return this._limit;
    }

    protected void setParentEofDetect(boolean z) {
        InputStream inputStream = this._in;
        if (inputStream instanceof IndefiniteLengthInputStream) {
            ((IndefiniteLengthInputStream) inputStream).setEofOn00(z);
        }
    }
}
