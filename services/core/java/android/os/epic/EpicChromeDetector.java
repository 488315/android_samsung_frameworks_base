package android.os.epic;

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
