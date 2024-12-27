package com.android.server.pm;

public final class PackageVerificationResponse {
    public final int callerUid;
    public final int code;

    public PackageVerificationResponse(int i, int i2) {
        this.code = i;
        this.callerUid = i2;
    }
}
