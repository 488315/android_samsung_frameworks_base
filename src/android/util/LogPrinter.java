package android.util;

/* loaded from: classes4.dex */
public class LogPrinter implements Printer {
    private final int mBuffer;
    private final int mPriority;
    private final String mTag;

    public LogPrinter(int i, String str) {
        this.mPriority = i;
        this.mTag = str;
        this.mBuffer = 0;
    }

    public LogPrinter(int i, String str, int i2) {
        this.mPriority = i;
        this.mTag = str;
        this.mBuffer = i2;
    }

    @Override // android.util.Printer
    public void println(String str) {
        Log.println_native(this.mBuffer, this.mPriority, this.mTag, str);
    }
}
