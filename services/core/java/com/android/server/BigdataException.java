package com.android.server;

import com.android.server.HermesBigdataFunction;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
class BigdataException extends Exception {
    private final HermesBigdataFunction.BigdataError err;

    public BigdataException(HermesBigdataFunction.BigdataError bigdataError) {
        super(bigdataError.reason());
        this.err = bigdataError;
    }

    public final int getErrCode() {
        return this.err.errCode();
    }
}
