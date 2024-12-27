package com.android.server.media;

import android.provider.DeviceConfig;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class MediaSessionDeviceConfig$$ExternalSyntheticLambda1 implements Consumer {
    public final /* synthetic */ DeviceConfig.Properties f$0;

    public /* synthetic */ MediaSessionDeviceConfig$$ExternalSyntheticLambda1(DeviceConfig.Properties properties) {
        this.f$0 = properties;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        DeviceConfig.Properties properties;
        String str;
        properties = this.f$0;
        str = (String) obj;
        str.getClass();
        switch (str) {
            case "media_button_receiver_fgs_allowlist_duration_ms":
                MediaSessionDeviceConfig.sMediaButtonReceiverFgsAllowlistDurationMs = properties.getLong(str, 10000L);
                break;
            case "media_session_callback_fgs_while_in_use_temp_allow_duration_ms":
                MediaSessionDeviceConfig.sMediaSessionCallbackFgsWhileInUseTempAllowDurationMs = properties.getLong(str, 10000L);
                break;
            case "media_session_calback_fgs_allowlist_duration_ms":
                MediaSessionDeviceConfig.sMediaSessionCallbackFgsAllowlistDurationMs = properties.getLong(str, 10000L);
                break;
        }
    }
}
