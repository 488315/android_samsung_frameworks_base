package com.android.server;


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
