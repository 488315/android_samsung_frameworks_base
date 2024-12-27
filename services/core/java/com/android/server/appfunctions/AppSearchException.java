package com.android.server.appfunctions;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
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
