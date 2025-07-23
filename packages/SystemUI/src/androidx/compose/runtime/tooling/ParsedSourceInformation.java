package androidx.compose.runtime.tooling;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ParsedSourceInformation {
    public final String fileName;
    public final String functionName;
    public final boolean isCall;
    public final int[] lineNumbers;
    public final String packageHash;

    public ParsedSourceInformation(boolean z, String str, String str2, String str3, int[] iArr, String str4) {
        this.isCall = z;
        this.functionName = str;
        this.fileName = str2;
        this.packageHash = str3;
        this.lineNumbers = iArr;
    }
}
