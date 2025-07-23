package android.os;

/* loaded from: classes3.dex */
public class SemHcmManager {
    private static final boolean DEBUG = "eng".equals(Build.TYPE);
    private static final String TAG = "SemHcmManager";
    Handler mHandler;
    ISemHcmManager mService;

    public SemHcmManager(ISemHcmManager iSemHcmManager, Handler handler) {
        this.mService = iSemHcmManager;
        this.mHandler = handler;
    }
}
