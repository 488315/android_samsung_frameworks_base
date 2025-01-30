package android.p005os.epic;

import android.app.ActivityManager;
import android.app.IActivityManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.SystemProperties;
import android.os.epic.IEpicManager;
import android.util.Log;
import com.android.server.SystemService;

/* loaded from: classes.dex */
public final class EpicManagerService extends SystemService {
    public static final long FEATURE_ACTIVITY = 1;
    public static final String FEATURE_PROPERTY_KEY = "vendor.epic.feature";
    public static final long FEATURE_WEB = 2;
    public static final String TAG = "EpicManagerService";
    public IActivityManager mActivityManager;
    public EpicChromeDetector mChromeDetector;
    public final Context mContext;
    public HandlerThread mDisplayHandlerThread;
    public DisplayManager.DisplayListener mDisplayListener;
    public DisplayManager mDisplayManager;
    public PackageManager mPackageManager;
    public EpicUidObserver mUidObserver;

    @Override // com.android.server.SystemService
    public void onBootPhase(int i) {
    }

    public EpicManagerService(Context context) {
        super(context);
        this.mChromeDetector = null;
        this.mUidObserver = null;
        this.mDisplayListener = null;
        this.mDisplayHandlerThread = null;
        this.mContext = context;
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("epic", new BinderService());
    }

    public void systemReady() {
        try {
            long j = SystemProperties.getLong(FEATURE_PROPERTY_KEY, 0L);
            if ((j & 1) == 1) {
                this.mActivityManager = ActivityManager.getService();
                this.mPackageManager = this.mContext.getPackageManager();
                if ((j & 2) == 2) {
                    this.mChromeDetector = new EpicChromeDetector();
                    this.mDisplayManager = (DisplayManager) this.mContext.getSystemService("display");
                    HandlerThread handlerThread = new HandlerThread("DisplayChange");
                    this.mDisplayHandlerThread = handlerThread;
                    handlerThread.start();
                    EpicDisplayListener epicDisplayListener = new EpicDisplayListener(this.mDisplayManager);
                    this.mDisplayListener = epicDisplayListener;
                    this.mDisplayManager.registerDisplayListener(epicDisplayListener, new Handler(this.mDisplayHandlerThread.getLooper()));
                }
                EpicUidObserver epicUidObserver = new EpicUidObserver(this.mContext, this.mPackageManager, this.mActivityManager, this.mChromeDetector);
                this.mUidObserver = epicUidObserver;
                this.mActivityManager.registerUidObserver(epicUidObserver, 3, 3, (String) null);
            }
        } catch (Exception e) {
            Log.i("EPICSVC", e.getMessage());
        }
    }

    public final class BinderService extends IEpicManager.Stub {
        public BinderService() {
        }

        public IEpicObject Create(int i) {
            return ObjectFactory.create(i);
        }

        public IEpicObject Creates(int[] iArr) {
            return ObjectFactory.create(iArr);
        }
    }
}
