package com.android.server.blob;

import android.os.Environment;
import android.util.DataUnit;
import android.util.Log;
import android.util.Slog;

import com.android.internal.util.FrameworkStatsLog;

import java.io.File;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public abstract class BlobStoreConfig {
    public static final boolean LOGV = Log.isLoggable("BlobStore", 2);

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class DeviceConfigProperties {
        public static long COMMIT_COOL_OFF_DURATION_MS;
        public static final long DEFAULT_COMMIT_COOL_OFF_DURATION_MS;
        public static final long DEFAULT_DELETE_ON_LAST_LEASE_DELAY_MS;
        public static final long DEFAULT_IDLE_JOB_PERIOD_MS;
        public static final long DEFAULT_LEASE_ACQUISITION_WAIT_DURATION_MS;
        public static final int DEFAULT_LEASE_DESC_CHAR_LIMIT;
        public static final int DEFAULT_MAX_ACTIVE_SESSIONS;
        public static final int DEFAULT_MAX_BLOB_ACCESS_PERMITTED_PACKAGES;
        public static final int DEFAULT_MAX_COMMITTED_BLOBS;
        public static final int DEFAULT_MAX_LEASED_BLOBS;
        public static final long DEFAULT_SESSION_EXPIRY_TIMEOUT_MS;
        public static final long DEFAULT_TOTAL_BYTES_PER_APP_LIMIT_FLOOR;
        public static long DELETE_ON_LAST_LEASE_DELAY_MS;
        public static long IDLE_JOB_PERIOD_MS;
        public static long LEASE_ACQUISITION_WAIT_DURATION_MS;
        public static int LEASE_DESC_CHAR_LIMIT;
        public static int MAX_ACTIVE_SESSIONS;
        public static int MAX_BLOB_ACCESS_PERMITTED_PACKAGES;
        public static int MAX_COMMITTED_BLOBS;
        public static int MAX_LEASED_BLOBS;
        public static long SESSION_EXPIRY_TIMEOUT_MS;
        public static long TOTAL_BYTES_PER_APP_LIMIT_FLOOR;
        public static float TOTAL_BYTES_PER_APP_LIMIT_FRACTION;
        public static boolean USE_REVOCABLE_FD_FOR_READS;

        static {
            TimeUnit timeUnit = TimeUnit.DAYS;
            long millis = timeUnit.toMillis(1L);
            DEFAULT_IDLE_JOB_PERIOD_MS = millis;
            IDLE_JOB_PERIOD_MS = millis;
            long millis2 = timeUnit.toMillis(7L);
            DEFAULT_SESSION_EXPIRY_TIMEOUT_MS = millis2;
            SESSION_EXPIRY_TIMEOUT_MS = millis2;
            long bytes = DataUnit.MEBIBYTES.toBytes(300L);
            DEFAULT_TOTAL_BYTES_PER_APP_LIMIT_FLOOR = bytes;
            TOTAL_BYTES_PER_APP_LIMIT_FLOOR = bytes;
            TOTAL_BYTES_PER_APP_LIMIT_FRACTION = 0.01f;
            TimeUnit timeUnit2 = TimeUnit.HOURS;
            long millis3 = timeUnit2.toMillis(6L);
            DEFAULT_LEASE_ACQUISITION_WAIT_DURATION_MS = millis3;
            LEASE_ACQUISITION_WAIT_DURATION_MS = millis3;
            long millis4 = timeUnit2.toMillis(48L);
            DEFAULT_COMMIT_COOL_OFF_DURATION_MS = millis4;
            COMMIT_COOL_OFF_DURATION_MS = millis4;
            USE_REVOCABLE_FD_FOR_READS = false;
            long millis5 = timeUnit2.toMillis(6L);
            DEFAULT_DELETE_ON_LAST_LEASE_DELAY_MS = millis5;
            DELETE_ON_LAST_LEASE_DELAY_MS = millis5;
            DEFAULT_MAX_ACTIVE_SESSIONS =
                    FrameworkStatsLog
                            .CAMERA_SHOT_LATENCY_REPORTED__MODE__CONTROL_DS_MODE_MACRO_RAW_SR_MERGE;
            MAX_ACTIVE_SESSIONS =
                    FrameworkStatsLog
                            .CAMERA_SHOT_LATENCY_REPORTED__MODE__CONTROL_DS_MODE_MACRO_RAW_SR_MERGE;
            DEFAULT_MAX_COMMITTED_BLOBS = 1000;
            MAX_COMMITTED_BLOBS = 1000;
            DEFAULT_MAX_LEASED_BLOBS = 500;
            MAX_LEASED_BLOBS = 500;
            DEFAULT_MAX_BLOB_ACCESS_PERMITTED_PACKAGES = 300;
            MAX_BLOB_ACCESS_PERMITTED_PACKAGES = 300;
            DEFAULT_LEASE_DESC_CHAR_LIMIT = 300;
            LEASE_DESC_CHAR_LIMIT = 300;
        }
    }

    public static File prepareBlobStoreRootDir() {
        File file = new File(Environment.getDataSystemDirectory(), "blobstore");
        if (file.exists() || file.mkdir()) {
            return file;
        }
        Slog.e("BlobStore", "Failed to mkdir(): " + file);
        return null;
    }
}
