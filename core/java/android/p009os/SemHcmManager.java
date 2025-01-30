package android.p009os;

/* loaded from: classes3.dex */
public class SemHcmManager {
  Handler mHandler;
  ISemHcmManager mService;
  private static final String TAG = SemHcmManager.class.getSimpleName();
  private static final boolean DEBUG = "eng".equals(Build.TYPE);

  public SemHcmManager(ISemHcmManager service, Handler handler) {
    this.mService = null;
    this.mService = service;
    this.mHandler = handler;
  }
}
