package com.android.internal.pm.parsing;

/* loaded from: classes5.dex */
public class PackageParserException extends Exception {
    public final int error;

    public PackageParserException(int i, String str) {
        super(str);
        this.error = i;
    }

    public PackageParserException(int i, String str, Throwable th) {
        super(str, th);
        this.error = i;
    }
}
