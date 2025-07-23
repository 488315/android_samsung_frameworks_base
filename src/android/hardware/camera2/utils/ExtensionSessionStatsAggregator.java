package android.hardware.camera2.utils;

import android.hardware.CameraExtensionSessionStats;
import android.hardware.camera2.CameraManager;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes2.dex */
public class ExtensionSessionStatsAggregator {
    private static final boolean DEBUG = false;
    private static final String TAG = "ExtensionSessionStatsAggregator";
    private final CameraExtensionSessionStats mStats;
    private final ExecutorService mExecutor = Executors.newSingleThreadExecutor();
    private final Object mLock = new Object();
    private boolean mIsDone = false;

    public ExtensionSessionStatsAggregator(String str, boolean z) {
        CameraExtensionSessionStats cameraExtensionSessionStats = new CameraExtensionSessionStats();
        this.mStats = cameraExtensionSessionStats;
        cameraExtensionSessionStats.key = "";
        cameraExtensionSessionStats.cameraId = str;
        cameraExtensionSessionStats.isAdvanced = z;
    }

    public void setClientName(String str) {
        synchronized (this.mLock) {
            if (this.mIsDone) {
                return;
            }
            this.mStats.clientName = str;
        }
    }

    public void setCaptureFormat(int i) {
        synchronized (this.mLock) {
            if (this.mIsDone) {
                return;
            }
            this.mStats.captureFormat = i;
        }
    }

    public void setExtensionType(int i) {
        synchronized (this.mLock) {
            if (this.mIsDone) {
                return;
            }
            this.mStats.type = i;
        }
    }

    public void commit(final boolean z) {
        this.mExecutor.execute(new Runnable() { // from class: android.hardware.camera2.utils.ExtensionSessionStatsAggregator$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                ExtensionSessionStatsAggregator.this.lambda$commit$0(z);
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$commit$0(boolean z) {
        synchronized (this.mLock) {
            if (this.mIsDone) {
                return;
            }
            this.mIsDone = z;
            CameraExtensionSessionStats cameraExtensionSessionStats = this.mStats;
            cameraExtensionSessionStats.key = CameraManager.reportExtensionSessionStats(cameraExtensionSessionStats);
        }
    }

    private static String prettyPrintStats(CameraExtensionSessionStats cameraExtensionSessionStats) {
        return "CameraExtensionSessionStats:\n  key: '" + cameraExtensionSessionStats.key + "'\n  cameraId: '" + cameraExtensionSessionStats.cameraId + "'\n  clientName: '" + cameraExtensionSessionStats.clientName + "'\n  type: '" + cameraExtensionSessionStats.type + "'\n  isAdvanced: '" + cameraExtensionSessionStats.isAdvanced + "'\n  captureFormat: '" + cameraExtensionSessionStats.captureFormat + "'\n";
    }

    public String getStatsKey() {
        return this.mStats.key;
    }
}
