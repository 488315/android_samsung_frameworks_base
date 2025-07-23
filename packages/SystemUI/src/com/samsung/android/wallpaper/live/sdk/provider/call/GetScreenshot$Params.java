package com.samsung.android.wallpaper.live.sdk.provider.call;

import android.os.Bundle;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl;
import com.samsung.android.wallpaper.live.sdk.provider.ProviderCallParams;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class GetScreenshot$Params extends ProviderCallParams {
    public final String purpose;
    public final String requiredImageFormat;
    public final int which;

    public GetScreenshot$Params(Bundle bundle) {
        super(bundle);
        this.which = bundle.getInt("which");
        bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID);
        bundle.getString("wallpaper_service_class_name");
        this.purpose = bundle.getString("purpose");
        this.requiredImageFormat = bundle.getString("image_format");
    }
}
