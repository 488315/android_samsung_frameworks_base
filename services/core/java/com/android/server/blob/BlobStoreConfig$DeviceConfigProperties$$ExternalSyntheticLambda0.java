package com.android.server.blob;

import android.provider.DeviceConfig;
import android.util.Slog;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class BlobStoreConfig$DeviceConfigProperties$$ExternalSyntheticLambda0
        implements Consumer {
    public final /* synthetic */ DeviceConfig.Properties f$0;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        DeviceConfig.Properties properties;
        String str;
        properties = this.f$0;
        str = (String) obj;
        str.getClass();
        switch (str) {
            case "max_active_sessions":
                BlobStoreConfig.DeviceConfigProperties.MAX_ACTIVE_SESSIONS =
                        properties.getInt(
                                str,
                                BlobStoreConfig.DeviceConfigProperties.DEFAULT_MAX_ACTIVE_SESSIONS);
                break;
            case "use_revocable_fd_for_reads":
                BlobStoreConfig.DeviceConfigProperties.USE_REVOCABLE_FD_FOR_READS =
                        properties.getBoolean(str, false);
                break;
            case "lease_acquisition_wait_time_ms":
                BlobStoreConfig.DeviceConfigProperties.LEASE_ACQUISITION_WAIT_DURATION_MS =
                        properties.getLong(
                                str,
                                BlobStoreConfig.DeviceConfigProperties
                                        .DEFAULT_LEASE_ACQUISITION_WAIT_DURATION_MS);
                break;
            case "max_leased_blobs":
                BlobStoreConfig.DeviceConfigProperties.MAX_LEASED_BLOBS =
                        properties.getInt(
                                str,
                                BlobStoreConfig.DeviceConfigProperties.DEFAULT_MAX_LEASED_BLOBS);
                break;
            case "max_permitted_pks":
                BlobStoreConfig.DeviceConfigProperties.MAX_BLOB_ACCESS_PERMITTED_PACKAGES =
                        properties.getInt(
                                str,
                                BlobStoreConfig.DeviceConfigProperties
                                        .DEFAULT_MAX_BLOB_ACCESS_PERMITTED_PACKAGES);
                break;
            case "commit_cool_off_duration_ms":
                BlobStoreConfig.DeviceConfigProperties.COMMIT_COOL_OFF_DURATION_MS =
                        properties.getLong(
                                str,
                                BlobStoreConfig.DeviceConfigProperties
                                        .DEFAULT_COMMIT_COOL_OFF_DURATION_MS);
                break;
            case "total_bytes_per_app_limit_floor":
                BlobStoreConfig.DeviceConfigProperties.TOTAL_BYTES_PER_APP_LIMIT_FLOOR =
                        properties.getLong(
                                str,
                                BlobStoreConfig.DeviceConfigProperties
                                        .DEFAULT_TOTAL_BYTES_PER_APP_LIMIT_FLOOR);
                break;
            case "lease_desc_char_limit":
                BlobStoreConfig.DeviceConfigProperties.LEASE_DESC_CHAR_LIMIT =
                        properties.getInt(
                                str,
                                BlobStoreConfig.DeviceConfigProperties
                                        .DEFAULT_LEASE_DESC_CHAR_LIMIT);
                break;
            case "max_committed_blobs":
                BlobStoreConfig.DeviceConfigProperties.MAX_COMMITTED_BLOBS =
                        properties.getInt(
                                str,
                                BlobStoreConfig.DeviceConfigProperties.DEFAULT_MAX_COMMITTED_BLOBS);
                break;
            case "total_bytes_per_app_limit_fraction":
                BlobStoreConfig.DeviceConfigProperties.TOTAL_BYTES_PER_APP_LIMIT_FRACTION =
                        properties.getFloat(str, 0.01f);
                break;
            case "delete_on_last_lease_delay_ms":
                BlobStoreConfig.DeviceConfigProperties.DELETE_ON_LAST_LEASE_DELAY_MS =
                        properties.getLong(
                                str,
                                BlobStoreConfig.DeviceConfigProperties
                                        .DEFAULT_DELETE_ON_LAST_LEASE_DELAY_MS);
                break;
            case "idle_job_period_ms":
                BlobStoreConfig.DeviceConfigProperties.IDLE_JOB_PERIOD_MS =
                        properties.getLong(
                                str,
                                BlobStoreConfig.DeviceConfigProperties.DEFAULT_IDLE_JOB_PERIOD_MS);
                break;
            case "session_expiry_timeout_ms":
                BlobStoreConfig.DeviceConfigProperties.SESSION_EXPIRY_TIMEOUT_MS =
                        properties.getLong(
                                str,
                                BlobStoreConfig.DeviceConfigProperties
                                        .DEFAULT_SESSION_EXPIRY_TIMEOUT_MS);
                break;
            default:
                Slog.wtf("BlobStore", "Unknown key in device config properties: ".concat(str));
                break;
        }
    }
}
