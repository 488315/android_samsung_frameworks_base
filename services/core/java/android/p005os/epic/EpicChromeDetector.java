package android.p005os.epic;

/* loaded from: classes.dex */
public final class EpicChromeDetector {
  public native boolean CheckChromeBrowser(String str);

  public native void Initialize();

  public native void RemoveUid(String str);

  public boolean LinkLibrary() {
    try {
      System.loadLibrary("epicsvc");
      return true;
    } catch (UnsatisfiedLinkError unused) {
      return false;
    }
  }
}
