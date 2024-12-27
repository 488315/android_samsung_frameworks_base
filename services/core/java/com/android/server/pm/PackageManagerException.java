package com.android.server.pm;

public class PackageManagerException extends Exception {
    public final int error;
    public final int internalErrorCode;
    public String packageName;

    public PackageManagerException(int i, String str) {
        super(str);
        this.error = i;
        this.internalErrorCode = 0;
    }

    public PackageManagerException(int i, String str, String str2, Throwable th) {
        super(str, th);
        this.error = i;
        this.internalErrorCode = 0;
        this.packageName = str2;
    }

    public PackageManagerException(int i, String str, Throwable th) {
        super(str, th);
        this.error = i;
        this.internalErrorCode = 0;
    }

    public PackageManagerException(String str, int i) {
        super(str);
        this.error = -110;
        this.internalErrorCode = i;
    }

    public PackageManagerException(Throwable th) {
        super(th);
        this.error = -110;
        this.internalErrorCode = 0;
    }

    public static PackageManagerException ofInternalError(int i, String str) {
        return new PackageManagerException(str, i);
    }
}
