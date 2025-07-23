package android.content.pm.dex;

import android.annotation.SystemApi;
import android.content.Context;
import android.content.pm.dex.ArtManager;
import android.content.pm.dex.ISnapshotRuntimeProfileCallback;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.Slog;
import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.Executor;

@SystemApi
/* loaded from: classes.dex */
public class ArtManager {
    public static final int PROFILE_APPS = 0;
    public static final int PROFILE_BOOT_IMAGE = 1;
    public static final int SNAPSHOT_FAILED_CODE_PATH_NOT_FOUND = 1;
    public static final int SNAPSHOT_FAILED_INTERNAL_ERROR = 2;
    public static final int SNAPSHOT_FAILED_PACKAGE_NOT_FOUND = 0;
    private static final String TAG = "ArtManager";
    private final IArtManager mArtManager;
    private final Context mContext;

    @Retention(RetentionPolicy.SOURCE)
    public @interface ProfileType {
    }

    public static abstract class SnapshotRuntimeProfileCallback {
        public abstract void onError(int i);

        public abstract void onSuccess(ParcelFileDescriptor parcelFileDescriptor);
    }

    public ArtManager(Context context, IArtManager iArtManager) {
        this.mContext = context;
        this.mArtManager = iArtManager;
    }

    public void snapshotRuntimeProfile(int i, String str, String str2, Executor executor, SnapshotRuntimeProfileCallback snapshotRuntimeProfileCallback) {
        Slog.d(TAG, "Requesting profile snapshot for " + str + ":" + str2);
        try {
            this.mArtManager.snapshotRuntimeProfile(i, str, str2, new SnapshotRuntimeProfileCallbackDelegate(snapshotRuntimeProfileCallback, executor), this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public boolean isRuntimeProfilingEnabled(int i) {
        try {
            return this.mArtManager.isRuntimeProfilingEnabled(i, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    static class SnapshotRuntimeProfileCallbackDelegate extends ISnapshotRuntimeProfileCallback.Stub {
        private final SnapshotRuntimeProfileCallback mCallback;
        private final Executor mExecutor;

        private SnapshotRuntimeProfileCallbackDelegate(SnapshotRuntimeProfileCallback snapshotRuntimeProfileCallback, Executor executor) {
            this.mCallback = snapshotRuntimeProfileCallback;
            this.mExecutor = executor;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onSuccess$0(ParcelFileDescriptor parcelFileDescriptor) {
            this.mCallback.onSuccess(parcelFileDescriptor);
        }

        @Override // android.content.pm.dex.ISnapshotRuntimeProfileCallback
        public void onSuccess(final ParcelFileDescriptor parcelFileDescriptor) {
            this.mExecutor.execute(new Runnable() { // from class: android.content.pm.dex.ArtManager$SnapshotRuntimeProfileCallbackDelegate$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    ArtManager.SnapshotRuntimeProfileCallbackDelegate.this.lambda$onSuccess$0(parcelFileDescriptor);
                }
            });
        }

        /* JADX INFO: Access modifiers changed from: private */
        public /* synthetic */ void lambda$onError$1(int i) {
            this.mCallback.onError(i);
        }

        @Override // android.content.pm.dex.ISnapshotRuntimeProfileCallback
        public void onError(final int i) {
            this.mExecutor.execute(new Runnable() { // from class: android.content.pm.dex.ArtManager$SnapshotRuntimeProfileCallbackDelegate$$ExternalSyntheticLambda1
                @Override // java.lang.Runnable
                public final void run() {
                    ArtManager.SnapshotRuntimeProfileCallbackDelegate.this.lambda$onError$1(i);
                }
            });
        }
    }

    public static String getProfileName(String str) {
        if (str == null) {
            return "primary.prof";
        }
        return str + ".split.prof";
    }

    public static String getCurrentProfilePath(String str, int i, String str2) {
        return new File(Environment.getDataProfilesDePackageDirectory(i, str), getProfileName(str2)).getAbsolutePath();
    }

    public static String getReferenceProfilePath(String str, int i, String str2) {
        return new File(Environment.getDataRefProfilesDePackageDirectory(str), getProfileName(str2)).getAbsolutePath();
    }

    public static File getProfileSnapshotFileForName(String str, String str2) {
        return new File(Environment.getDataRefProfilesDePackageDirectory(str), str2 + ".snapshot");
    }
}
