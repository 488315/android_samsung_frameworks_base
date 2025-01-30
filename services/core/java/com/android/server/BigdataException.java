package com.android.server;

import com.android.server.HermesBigdataFunction;

/* compiled from: HermesBigdataFunction.java */
/* loaded from: classes.dex */
class BigdataException extends Exception {
    private final HermesBigdataFunction.BigdataError err;

    public BigdataException(String str, HermesBigdataFunction.BigdataError bigdataError) {
        super(str);
        this.err = bigdataError;
    }

    public BigdataException(HermesBigdataFunction.BigdataError bigdataError) {
        this(bigdataError.reason(), bigdataError);
    }

    public int getErrCode() {
        return this.err.errCode();
    }
}
