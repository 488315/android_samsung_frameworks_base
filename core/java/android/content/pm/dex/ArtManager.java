package android.content.pm.dex;

import android.annotation.SystemApi;
import android.content.Context;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.os.RemoteException;
import android.util.Slog;

import java.io.File;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.concurrent.Executor;

@SystemApi
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
    public @interface ProfileType {}

    public abstract static class SnapshotRuntimeProfileCallback {
        public abstract void onError(int i);

        public abstract void onSuccess(ParcelFileDescriptor parcelFileDescriptor);
    }

    public ArtManager(Context context, IArtManager manager) {
        this.mContext = context;
        this.mArtManager = manager;
    }

    public void snapshotRuntimeProfile(
            int profileType,
            String packageName,
            String codePath,
            Executor executor,
            SnapshotRuntimeProfileCallback callback) {
        Slog.d(TAG, "Requesting profile snapshot for " + packageName + ":" + codePath);
        SnapshotRuntimeProfileCallbackDelegate delegate =
                new SnapshotRuntimeProfileCallbackDelegate(callback, executor);
        try {
            this.mArtManager.snapshotRuntimeProfile(
                    profileType, packageName, codePath, delegate, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    public boolean isRuntimeProfilingEnabled(int profileType) {
        try {
            return this.mArtManager.isRuntimeProfilingEnabled(
                    profileType, this.mContext.getOpPackageName());
        } catch (RemoteException e) {
            throw e.rethrowAsRuntimeException();
        }
    }

    static class SnapshotRuntimeProfileCallbackDelegate
            extends ISnapshotRuntimeProfileCallback.Stub {
        private final SnapshotRuntimeProfileCallback mCallback;
        private final Executor mExecutor;

        private SnapshotRuntimeProfileCallbackDelegate(
                SnapshotRuntimeProfileCallback callback, Executor executor) {
            this.mCallback = callback;
            this.mExecutor = executor;
        }

        public /* synthetic */ void lambda$onSuccess$0(ParcelFileDescriptor profileReadFd) {
            this.mCallback.onSuccess(profileReadFd);
        }

        @Override // android.content.pm.dex.ISnapshotRuntimeProfileCallback
        public void onSuccess(final ParcelFileDescriptor profileReadFd) {
            this.mExecutor.execute(
                    new Runnable() { // from class:
                        // android.content.pm.dex.ArtManager$SnapshotRuntimeProfileCallbackDelegate$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            ArtManager.SnapshotRuntimeProfileCallbackDelegate.this
                                    .lambda$onSuccess$0(profileReadFd);
                        }
                    });
        }

        public /* synthetic */ void lambda$onError$1(int errCode) {
            this.mCallback.onError(errCode);
        }

        @Override // android.content.pm.dex.ISnapshotRuntimeProfileCallback
        public void onError(final int errCode) {
            this.mExecutor.execute(
                    new Runnable() { // from class:
                        // android.content.pm.dex.ArtManager$SnapshotRuntimeProfileCallbackDelegate$$ExternalSyntheticLambda1
                        @Override // java.lang.Runnable
                        public final void run() {
                            ArtManager.SnapshotRuntimeProfileCallbackDelegate.this.lambda$onError$1(
                                    errCode);
                        }
                    });
        }
    }

    public static String getProfileName(String splitName) {
        return splitName == null ? "primary.prof" : splitName + ".split.prof";
    }

    public static String getCurrentProfilePath(String packageName, int userId, String splitName) {
        File profileDir = Environment.getDataProfilesDePackageDirectory(userId, packageName);
        return new File(profileDir, getProfileName(splitName)).getAbsolutePath();
    }

    public static String getReferenceProfilePath(String packageName, int userId, String splitName) {
        File profileDir = Environment.getDataRefProfilesDePackageDirectory(packageName);
        return new File(profileDir, getProfileName(splitName)).getAbsolutePath();
    }

    public static File getProfileSnapshotFileForName(String packageName, String profileName) {
        File profileDir = Environment.getDataRefProfilesDePackageDirectory(packageName);
        return new File(profileDir, profileName + ".snapshot");
    }
}
