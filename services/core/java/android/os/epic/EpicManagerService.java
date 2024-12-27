package android.os.epic;

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

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class EpicManagerService extends SystemService {
    private static final long FEATURE_ACTIVITY = 1;
    private static final String FEATURE_PROPERTY_KEY = "vendor.epic.feature";
    private static final long FEATURE_WEB = 2;
    private static final String TAG = "EpicManagerService";
    private IActivityManager mActivityManager;
    private EpicChromeDetector mChromeDetector;
    private final Context mContext;
    private HandlerThread mDisplayHandlerThread;
    private DisplayManager.DisplayListener mDisplayListener;
    private DisplayManager mDisplayManager;
    private PackageManager mPackageManager;
    private EpicUidObserver mUidObserver;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class BinderService extends IEpicManager.Stub {
        public final IEpicObject Create(int i) {
            return ObjectFactory.create(i);
        }

        public final IEpicObject Creates(int[] iArr) {
            return ObjectFactory.create(iArr);
        }
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
    public void onBootPhase(int i) {
    }

    @Override // com.android.server.SystemService
    public void onStart() {
        publishBinderService("epic", new BinderService());
    }

    public void systemReady() {
        try {
            long j = SystemProperties.getLong(FEATURE_PROPERTY_KEY, 0L);
            if ((j & FEATURE_ACTIVITY) == FEATURE_ACTIVITY) {
                this.mActivityManager = ActivityManager.getService();
                this.mPackageManager = this.mContext.getPackageManager();
                if ((j & FEATURE_WEB) == FEATURE_WEB) {
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
}
