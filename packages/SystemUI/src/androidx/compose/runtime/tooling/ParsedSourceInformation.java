package androidx.compose.runtime.tooling;

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
