package com.android.server.appfunctions;

public class AppSearchException extends RuntimeException {
    private final int resultCode;

    public AppSearchException(int i, String str) {
        super(str);
        this.resultCode = i;
    }

    public final int getResultCode() {
        return this.resultCode;
    }
}
