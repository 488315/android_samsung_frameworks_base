package android.hardware.camera2.utils;

import android.hardware.CameraExtensionSessionStats;
import android.hardware.camera2.CameraManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/* loaded from: classes2.dex */
public class ExtensionSessionStatsAggregator {
    private static final boolean DEBUG = false;
    private static final String TAG = ExtensionSessionStatsAggregator.class.getSimpleName();
    private final ExecutorService mExecutor = Executors.newSingleThreadExecutor();
    private final Object mLock = new Object();
    private boolean mIsDone = false;
    private final CameraExtensionSessionStats mStats = new CameraExtensionSessionStats();

    public ExtensionSessionStatsAggregator(String cameraId, boolean isAdvanced) {
        this.mStats.key = "";
        this.mStats.cameraId = cameraId;
        this.mStats.isAdvanced = isAdvanced;
    }

    public void setClientName(String clientName) {
        synchronized (this.mLock) {
            if (this.mIsDone) {
                return;
            }
            this.mStats.clientName = clientName;
        }
    }

    public void setCaptureFormat(int format) {
        synchronized (this.mLock) {
            if (this.mIsDone) {
                return;
            }
            this.mStats.captureFormat = format;
        }
    }

    public void setExtensionType(int extensionType) {
        synchronized (this.mLock) {
            if (this.mIsDone) {
                return;
            }
            this.mStats.type = extensionType;
        }
    }

    public void commit(final boolean isFinal) {
        this.mExecutor.execute(
                new Runnable() { // from class:
                    // android.hardware.camera2.utils.ExtensionSessionStatsAggregator$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        ExtensionSessionStatsAggregator.this.lambda$commit$0(isFinal);
                    }
                });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$commit$0(boolean isFinal) {
        synchronized (this.mLock) {
            if (this.mIsDone) {
                return;
            }
            this.mIsDone = isFinal;
            this.mStats.key = CameraManager.reportExtensionSessionStats(this.mStats);
        }
    }

    private static String prettyPrintStats(CameraExtensionSessionStats stats) {
        return CameraExtensionSessionStats.class.getSimpleName()
                + ":\n  key: '"
                + stats.key
                + "'\n  cameraId: '"
                + stats.cameraId
                + "'\n  clientName: '"
                + stats.clientName
                + "'\n  type: '"
                + stats.type
                + "'\n  isAdvanced: '"
                + stats.isAdvanced
                + "'\n  captureFormat: '"
                + stats.captureFormat
                + "'\n";
    }

    public String getStatsKey() {
        return this.mStats.key;
    }
}
