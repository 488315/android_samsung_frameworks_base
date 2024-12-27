package android.os.epic;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class EpicChromeDetector {
    public native boolean CheckChromeBrowser(String str);

    public native void Initialize();

    public boolean LinkLibrary() {
        try {
            System.loadLibrary("epicsvc");
            return true;
        } catch (UnsatisfiedLinkError unused) {
            return false;
        }
    }

    public native void RemoveUid(String str);
}
