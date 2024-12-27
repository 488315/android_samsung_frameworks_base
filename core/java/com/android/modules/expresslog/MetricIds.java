package com.android.modules.expresslog;

import android.util.ArrayMap;

import java.util.InputMismatchException;

public final class MetricIds {
    public static final int METRIC_TYPE_COUNTER = 1;
    public static final int METRIC_TYPE_COUNTER_WITH_UID = 3;
    public static final int METRIC_TYPE_HISTOGRAM = 2;
    public static final int METRIC_TYPE_HISTOGRAM_WITH_UID = 4;
    public static final int METRIC_TYPE_UNKNOWN = 0;
    private static ArrayMap<String, MetricInfo> metricIds = new ArrayMap<>();

    private static final class MetricInfo {
        public long mHash;
        public int mType;

        MetricInfo(long hash, int type) {
            this.mHash = hash;
            this.mType = type;
        }
    }

    static {
        metricIds.put(
                "accessibility.value_fab_shortcut_dismiss",
                new MetricInfo(-6072243364828125290L, 1));
        metricIds.put(
                "accessibility.value_fab_shortcut_edit", new MetricInfo(4192923649436966820L, 1));
        metricIds.put(
                "accessibility.value_qs_shortcut_add", new MetricInfo(2834124234205782040L, 3));
        metricIds.put(
                "accessibility.value_qs_shortcut_remove", new MetricInfo(593334822861300811L, 3));
        metricIds.put(
                "app.value_force_stop_cancelled_pi_sent_from_top_per_caller",
                new MetricInfo(8623497374021313549L, 3));
        metricIds.put(
                "app.value_force_stop_cancelled_pi_sent_from_top_per_owner",
                new MetricInfo(-8555181207850502954L, 3));
        metricIds.put(
                "app.value_high_authenticator_response_latency",
                new MetricInfo(-7517345590305216078L, 4));
        metricIds.put(
                "automotive_os.value_concurrent_sync_operations",
                new MetricInfo(-4400160310807362695L, 2));
        metricIds.put(
                "automotive_os.value_get_async_end_to_end_latency",
                new MetricInfo(-8272866773037756707L, 2));
        metricIds.put(
                "automotive_os.value_get_async_latency", new MetricInfo(-8894500157990623512L, 2));
        metricIds.put(
                "automotive_os.value_set_async_end_to_end_latency",
                new MetricInfo(156946757820491208L, 2));
        metricIds.put(
                "automotive_os.value_set_async_latency", new MetricInfo(-4331235063088608305L, 2));
        metricIds.put(
                "automotive_os.value_subscription_update_rate",
                new MetricInfo(-4229550097093086307L, 2));
        metricIds.put(
                "automotive_os.value_sync_get_property_latency",
                new MetricInfo(-5307712280607833154L, 2));
        metricIds.put(
                "automotive_os.value_sync_hal_get_property_latency",
                new MetricInfo(4878890158497689193L, 2));
        metricIds.put(
                "automotive_os.value_sync_hal_set_property_latency",
                new MetricInfo(-772366165941106467L, 2));
        metricIds.put(
                "automotive_os.value_sync_set_property_latency",
                new MetricInfo(5217707327207935689L, 2));
        metricIds.put(
                "battery.value_app_added_to_power_allowlist",
                new MetricInfo(4707695847309057012L, 1));
        metricIds.put(
                "battery.value_app_background_restricted", new MetricInfo(2048093366999806818L, 3));
        metricIds.put(
                "battery.value_app_removed_from_power_allowlist",
                new MetricInfo(-4988440224544870744L, 1));
        metricIds.put(
                "binary_transparency.value_digest_all_packages_latency_uniform",
                new MetricInfo(-1663388052249034620L, 2));
        metricIds.put(
                "biometric.value_scheduler_watchdog_triggered_count",
                new MetricInfo(-8949614549819407840L, 1));
        metricIds.put(
                "bluetooth.value_aptx_codec_usage_over_hfp",
                new MetricInfo(7003564244080163944L, 1));
        metricIds.put("bluetooth.value_auto_on_disabled", new MetricInfo(38262175154558217L, 1));
        metricIds.put("bluetooth.value_auto_on_enabled", new MetricInfo(4153375556285078717L, 1));
        metricIds.put(
                "bluetooth.value_auto_on_supported", new MetricInfo(-3270875478117966326L, 1));
        metricIds.put("bluetooth.value_auto_on_triggered", new MetricInfo(2657249641697133856L, 1));
        metricIds.put(
                "bluetooth.value_close_profile_proxy_adapter_mismatch",
                new MetricInfo(5174922474613897731L, 3));
        metricIds.put(
                "bluetooth.value_cvsd_codec_usage_over_hfp",
                new MetricInfo(2237290434176331321L, 1));
        metricIds.put(
                "bluetooth.value_kill_from_binder_thread", new MetricInfo(1240857845875200167L, 1));
        metricIds.put(
                "bluetooth.value_lc3_codec_usage_over_hfp",
                new MetricInfo(8778544908497736848L, 1));
        metricIds.put(
                "bluetooth.value_msbc_codec_usage_over_hfp",
                new MetricInfo(-3563813040444045278L, 1));
        metricIds.put(
                "content_capture.value_content_capture_wrong_thread_count",
                new MetricInfo(-2722617273353133449L, 1));
        metricIds.put(
                "cpu.value_aggregated_thread_tracking_start_failure_count",
                new MetricInfo(-6863812985036080576L, 1));
        metricIds.put(
                "cpu.value_process_tracking_start_failure_count",
                new MetricInfo(7879492844327619097L, 1));
        metricIds.put(
                "device_lock.value_resets_unsuccessful_provisioning_deferred",
                new MetricInfo(1115524515723454059L, 1));
        metricIds.put(
                "device_lock.value_resets_unsuccessful_provisioning_mandatory",
                new MetricInfo(241691752144394174L, 1));
        metricIds.put(
                "device_lock.value_successful_check_in_response_count",
                new MetricInfo(4953020919956346487L, 1));
        metricIds.put(
                "device_lock.value_successful_locking_count",
                new MetricInfo(8449007043296996467L, 1));
        metricIds.put(
                "device_lock.value_successful_provisioning_count",
                new MetricInfo(2831700733284693731L, 1));
        metricIds.put(
                "device_lock.value_successful_unlocking_count",
                new MetricInfo(-2324248776576908586L, 1));
        metricIds.put(
                "health_services.value_hal_crash_counter", new MetricInfo(1163143892454419526L, 1));
        metricIds.put(
                "input.value_app_handled_stem_primary_key_gestures_count",
                new MetricInfo(1523110716521952632L, 3));
        metricIds.put(
                "job_scheduler.value_cntr_w_uid_estimated_network_bytes_updated",
                new MetricInfo(-2040690094286153484L, 3));
        metricIds.put(
                "job_scheduler.value_cntr_w_uid_estimated_network_download_bytes_decreased",
                new MetricInfo(7559830738664114070L, 3));
        metricIds.put(
                "job_scheduler.value_cntr_w_uid_estimated_network_download_bytes_increased",
                new MetricInfo(4307374164472954573L, 3));
        metricIds.put(
                "job_scheduler.value_cntr_w_uid_estimated_network_upload_bytes_decreased",
                new MetricInfo(7746759860871419241L, 3));
        metricIds.put(
                "job_scheduler.value_cntr_w_uid_estimated_network_upload_bytes_increased",
                new MetricInfo(1203926515126858145L, 3));
        metricIds.put(
                "job_scheduler.value_cntr_w_uid_initial_set_notification_call_optional",
                new MetricInfo(6572724005091391785L, 3));
        metricIds.put(
                "job_scheduler.value_cntr_w_uid_initial_set_notification_call_required",
                new MetricInfo(4062401757479629526L, 3));
        metricIds.put(
                "job_scheduler.value_cntr_w_uid_job_work_items_enqueued",
                new MetricInfo(-7467641086093308410L, 3));
        metricIds.put(
                "job_scheduler.value_cntr_w_uid_max_scheduling_limit_hit",
                new MetricInfo(-9136864684089221786L, 3));
        metricIds.put(
                "job_scheduler.value_cntr_w_uid_schedule_failure_app_start_mode_disabled",
                new MetricInfo(7917680363610375490L, 3));
        metricIds.put(
                "job_scheduler.value_cntr_w_uid_schedule_failure_ej_out_of_quota",
                new MetricInfo(-7117224395995325645L, 3));
        metricIds.put(
                "job_scheduler.value_cntr_w_uid_schedule_failure_uij_invalid_state",
                new MetricInfo(-5320416248652252935L, 3));
        metricIds.put(
                "job_scheduler.value_cntr_w_uid_schedule_failure_uij_no_permission",
                new MetricInfo(-6831752108807384315L, 3));
        metricIds.put(
                "job_scheduler.value_cntr_w_uid_set_notification_changed_notification_ids",
                new MetricInfo(-5987874878060478878L, 3));
        metricIds.put(
                "job_scheduler.value_cntr_w_uid_slow_app_response_binding",
                new MetricInfo(5259558322414450046L, 3));
        metricIds.put(
                "job_scheduler.value_cntr_w_uid_slow_app_response_on_start_job",
                new MetricInfo(-3883908790761732574L, 3));
        metricIds.put(
                "job_scheduler.value_cntr_w_uid_slow_app_response_on_stop_job",
                new MetricInfo(6916219943787278753L, 3));
        metricIds.put(
                "job_scheduler.value_cntr_w_uid_slow_app_response_set_notification",
                new MetricInfo(-3311018727502583721L, 3));
        metricIds.put(
                "job_scheduler.value_cntr_w_uid_subsequent_set_notification_call_optional",
                new MetricInfo(9145630948768787150L, 3));
        metricIds.put(
                "job_scheduler.value_cntr_w_uid_subsequent_set_notification_call_required",
                new MetricInfo(3663977039218515109L, 3));
        metricIds.put(
                "job_scheduler.value_cntr_w_uid_transferred_network_bytes_updated",
                new MetricInfo(-3822161589539396120L, 3));
        metricIds.put(
                "job_scheduler.value_cntr_w_uid_transferred_network_download_bytes_decreased",
                new MetricInfo(-5904338831025314582L, 3));
        metricIds.put(
                "job_scheduler.value_cntr_w_uid_transferred_network_download_bytes_increased",
                new MetricInfo(-2671783057200009454L, 3));
        metricIds.put(
                "job_scheduler.value_cntr_w_uid_transferred_network_upload_bytes_decreased",
                new MetricInfo(6063356280586548670L, 3));
        metricIds.put(
                "job_scheduler.value_cntr_w_uid_transferred_network_upload_bytes_increased",
                new MetricInfo(-7671154881427569796L, 3));
        metricIds.put(
                "job_scheduler.value_cntr_w_uid_unexpected_service_disconnects",
                new MetricInfo(-1299427322302314244L, 3));
        metricIds.put(
                "job_scheduler.value_hist_initial_job_estimated_network_download_kilobytes",
                new MetricInfo(-785066281506175156L, 2));
        metricIds.put(
                "job_scheduler.value_hist_initial_job_estimated_network_upload_kilobytes",
                new MetricInfo(-5363480020640374054L, 2));
        metricIds.put(
                "job_scheduler.value_hist_initial_jwi_estimated_network_download_kilobytes",
                new MetricInfo(3599293282843442428L, 2));
        metricIds.put(
                "job_scheduler.value_hist_initial_jwi_estimated_network_upload_kilobytes",
                new MetricInfo(1703249122821912364L, 2));
        metricIds.put(
                "job_scheduler.value_hist_job_concurrency",
                new MetricInfo(7022862059858276673L, 2));
        metricIds.put(
                "job_scheduler.value_hist_scheduled_job_30_min_high_water_mark",
                new MetricInfo(-6580875291944158750L, 2));
        metricIds.put(
                "job_scheduler.value_hist_transferred_network_download_kilobytes_high_water_mark",
                new MetricInfo(-8626132665475984961L, 2));
        metricIds.put(
                "job_scheduler.value_hist_transferred_network_upload_kilobytes_high_water_mark",
                new MetricInfo(-6725069608641059563L, 2));
        metricIds.put(
                "job_scheduler.value_hist_updated_estimated_network_download_kilobytes",
                new MetricInfo(-1643579595435861571L, 2));
        metricIds.put(
                "job_scheduler.value_hist_updated_estimated_network_upload_kilobytes",
                new MetricInfo(-7398595476439352536L, 2));
        metricIds.put(
                "job_scheduler.value_hist_w_uid_enqueued_work_items_at_job_start",
                new MetricInfo(-6217913081941281407L, 4));
        metricIds.put(
                "job_scheduler.value_hist_w_uid_enqueued_work_items_high_water_mark",
                new MetricInfo(6347971958352818350L, 4));
        metricIds.put(
                "job_scheduler.value_hist_w_uid_job_minimum_chunk_kilobytes",
                new MetricInfo(924428326049431555L, 4));
        metricIds.put(
                "job_scheduler.value_hist_w_uid_jwi_minimum_chunk_kilobytes",
                new MetricInfo(3314117374945417107L, 4));
        metricIds.put(
                "job_scheduler.value_job_quota_reduced_due_to_buggy_uid",
                new MetricInfo(2090030468725226029L, 3));
        metricIds.put(
                "job_scheduler.value_job_scheduler_job_deadline_expired_counter",
                new MetricInfo(-4904009711020652546L, 1));
        metricIds.put("screen.value_dim_to_screen_off", new MetricInfo(-8437355397311467341L, 1));
        metricIds.put("screen.value_undim", new MetricInfo(673292744997347922L, 1));
        metricIds.put(
                "speech_recognition.value_exceed_service_connections_count",
                new MetricInfo(4137901124994142032L, 3));
        metricIds.put(
                "speech_recognition.value_exceed_session_count",
                new MetricInfo(-2798805470646689619L, 3));
        metricIds.put("stability_anr.value_skipped_anrs", new MetricInfo(-1686340313630649786L, 1));
        metricIds.put("stability_anr.value_total_anrs", new MetricInfo(-4448413402479670208L, 1));
        metricIds.put(
                "stability_errors.value_dropbox_buffer_expired_count",
                new MetricInfo(-6509578091451690387L, 1));
        metricIds.put(
                "statsd_errors.value_report_vendor_atom_errors_count",
                new MetricInfo(6758996126291769555L, 1));
        metricIds.put(
                "tex_test.value_telemetry_express_fixed_range_histogram",
                new MetricInfo(-8323169799906496731L, 2));
        metricIds.put(
                "tex_test.value_telemetry_express_fixed_range_histogram_with_uid",
                new MetricInfo(6023834309724625512L, 4));
        metricIds.put(
                "tex_test.value_telemetry_express_scaled_factor_histogram",
                new MetricInfo(3864259057208837246L, 2));
        metricIds.put(
                "tex_test.value_telemetry_express_scaled_range_histogram_with_uid",
                new MetricInfo(7629488784689754667L, 4));
        metricIds.put(
                "tex_test.value_telemetry_express_test_counter",
                new MetricInfo(-2003432401640472195L, 1));
        metricIds.put(
                "tex_test.value_telemetry_express_test_counter_with_uid",
                new MetricInfo(4877884967786456322L, 3));
        metricIds.put(
                "vibrator.value_perform_haptic_feedback_keyboard",
                new MetricInfo(-6578167211361419381L, 3));
        metricIds.put(
                "vibrator.value_vibration_adaptive_haptic_scale",
                new MetricInfo(-924995737492265107L, 4));
        metricIds.put(
                "vibrator.value_vibration_param_request_latency",
                new MetricInfo(4189860041196854104L, 4));
        metricIds.put(
                "vibrator.value_vibration_param_request_timeout",
                new MetricInfo(8795177979007841024L, 3));
        metricIds.put(
                "vibrator.value_vibration_param_response_ignored",
                new MetricInfo(7429769541893516361L, 1));
        metricIds.put(
                "vibrator.value_vibration_param_scale", new MetricInfo(-3257976114455951225L, 2));
        metricIds.put(
                "virtual_devices.value_activity_blocked_count",
                new MetricInfo(-7079715898906024559L, 3));
        metricIds.put(
                "virtual_devices.value_secure_window_blocked_count",
                new MetricInfo(381600291587716893L, 3));
        metricIds.put(
                "virtual_devices.value_virtual_audio_created_count",
                new MetricInfo(216469278413724750L, 3));
        metricIds.put(
                "virtual_devices.value_virtual_camera_created_count",
                new MetricInfo(2669713370281136655L, 3));
        metricIds.put(
                "virtual_devices.value_virtual_devices_created_count",
                new MetricInfo(849190108476587824L, 1));
        metricIds.put(
                "virtual_devices.value_virtual_devices_created_with_uid_count",
                new MetricInfo(-8043783159667542734L, 3));
        metricIds.put(
                "virtual_devices.value_virtual_display_created_count",
                new MetricInfo(-8328274662193080522L, 3));
        metricIds.put(
                "virtual_devices.value_virtual_dpad_created_count",
                new MetricInfo(-7554867509149945123L, 3));
        metricIds.put(
                "virtual_devices.value_virtual_keyboard_created_count",
                new MetricInfo(-4281936962231424349L, 3));
        metricIds.put(
                "virtual_devices.value_virtual_mouse_created_count",
                new MetricInfo(1498259102425665167L, 3));
        metricIds.put(
                "virtual_devices.value_virtual_navigationtouchpad_created_count",
                new MetricInfo(-8314108585791227748L, 3));
        metricIds.put(
                "virtual_devices.value_virtual_sensors_created_count",
                new MetricInfo(6487611638632272886L, 3));
        metricIds.put(
                "virtual_devices.value_virtual_stylus_created_count",
                new MetricInfo(-7217071694244830849L, 3));
        metricIds.put(
                "virtual_devices.value_virtual_touchscreen_created_count",
                new MetricInfo(8525699099567515279L, 3));
        metricIds.put(
                "wear_notifications.value_hist_image_asset_open_latency",
                new MetricInfo(8781758719844294982L, 2));
        metricIds.put(
                "webview.value_app_waiting_for_relro_completion_delay",
                new MetricInfo(5446477756832762351L, 2));
        metricIds.put(
                "webview.value_default_webview_package_invalid_counter",
                new MetricInfo(-1261551960233354386L, 1));
        metricIds.put(
                "webview.value_find_preferred_webview_package_counter",
                new MetricInfo(-7372420611648388468L, 1));
        metricIds.put(
                "webview.value_on_webview_provider_changed_counter",
                new MetricInfo(-6655239826469964574L, 1));
        metricIds.put(
                "webview.value_on_webview_provider_changed_with_default_package_counter",
                new MetricInfo(2655302963671938552L, 1));
        metricIds.put(
                "webview.value_prepare_webview_in_system_server_latency",
                new MetricInfo(-7071029018909574343L, 2));
        metricIds.put(
                "webview.value_webview_not_usable_for_all_users_counter",
                new MetricInfo(-8269417493541516036L, 1));
    }

    static long getMetricIdHash(String metricId, int type) {
        MetricInfo info = metricIds.get(metricId);
        if (info == null) {
            throw new IllegalArgumentException("Metric is undefined " + metricId);
        }
        if (info.mType != type) {
            throw new InputMismatchException("Metric type is not " + type);
        }
        return info.mHash;
    }
}
