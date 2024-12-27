package android.app.ondeviceintelligence;

import android.annotation.SystemApi;
import android.os.PersistableBundle;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@SystemApi
public interface DownloadCallback {
    public static final int DOWNLOAD_FAILURE_STATUS_DOWNLOADING = 3;
    public static final int DOWNLOAD_FAILURE_STATUS_NETWORK_FAILURE = 2;
    public static final int DOWNLOAD_FAILURE_STATUS_NOT_ENOUGH_DISK_SPACE = 1;
    public static final int DOWNLOAD_FAILURE_STATUS_UNAVAILABLE = 4;
    public static final int DOWNLOAD_FAILURE_STATUS_UNKNOWN = 0;

    @Retention(RetentionPolicy.SOURCE)
    public @interface DownloadFailureStatus {}

    void onDownloadCompleted(PersistableBundle persistableBundle);

    void onDownloadFailed(int i, String str, PersistableBundle persistableBundle);

    default void onDownloadStarted(long bytesToDownload) {}

    default void onDownloadProgress(long totalBytesDownloaded) {}
}
