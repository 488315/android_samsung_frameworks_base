package com.samsung.android.wallpaper.live.sdk.provider.call;

import android.os.Bundle;
import com.samsung.android.knox.ucm.plugin.agent.UcmAgentProviderImpl;
import com.samsung.android.wallpaper.live.sdk.provider.ProviderCallParams;

/* loaded from: classes4.dex */
public class GetBackgroundRegion$Params extends ProviderCallParams {
    public final int rotation;
    public final int sourceWhich;
    public final int userId;
    public final int which;

    public GetBackgroundRegion$Params(Bundle bundle) {
        super(bundle);
        this.which = bundle.getInt("which");
        this.sourceWhich = bundle.getInt("source_which");
        this.userId = bundle.getInt(UcmAgentProviderImpl.UcmAgentSpiProperty.KEY_USER_ID);
        bundle.getString("wallpaper_service_class_name");
        this.rotation = bundle.getInt("rotation", 0);
    }
}
