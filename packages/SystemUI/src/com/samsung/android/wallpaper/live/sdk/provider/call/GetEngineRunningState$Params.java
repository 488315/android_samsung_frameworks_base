package com.samsung.android.wallpaper.live.sdk.provider.call;

import android.os.Bundle;
import com.samsung.android.wallpaper.live.sdk.provider.ProviderCallParams;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class GetEngineRunningState$Params extends ProviderCallParams {
    public final int which;

    public GetEngineRunningState$Params(Bundle bundle) {
        super(bundle);
        this.which = bundle.getInt("which");
    }
}
