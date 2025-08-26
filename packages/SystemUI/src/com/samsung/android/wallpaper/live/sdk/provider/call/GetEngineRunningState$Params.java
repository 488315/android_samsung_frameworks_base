package com.samsung.android.wallpaper.live.sdk.provider.call;

import android.os.Bundle;
import com.samsung.android.wallpaper.live.sdk.provider.ProviderCallParams;

/* loaded from: classes4.dex */
public class GetEngineRunningState$Params extends ProviderCallParams {
    public final int which;

    public GetEngineRunningState$Params(Bundle bundle) {
        super(bundle);
        this.which = bundle.getInt("which");
    }
}
