package com.samsung.android.lock;

public enum LsLogType {
    SUMMARY("Summary", 515, 100),
    ENROLL("Enroll", 259, 0),
    VERIFY("Verify", 259, 20),
    KEYERR("KeystoreErr", 259, 10),
    RESTORE("Restore", 259, 0),
    UNKNOWN("Unknown", 259, 0);

    private final String mErrorCode;
    private final long mMaxSize;
    private final int mProperty;
    public static final LsLogType[] LIST = {SUMMARY, ENROLL, VERIFY, KEYERR, RESTORE, UNKNOWN};

    LsLogType(String errCode, int property, int size) {
        this.mErrorCode = errCode;
        this.mProperty = property;
        this.mMaxSize = size * 1024;
    }

    String getErrorCode() {
        return this.mErrorCode;
    }

    boolean containsProperty(int property) {
        return (this.mProperty & property) != 0;
    }

    long getMaxSize() {
        return this.mMaxSize;
    }
}
