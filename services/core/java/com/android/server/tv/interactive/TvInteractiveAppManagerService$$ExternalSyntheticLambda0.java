package com.android.server.tv.interactive;

import android.media.tv.ad.TvAdServiceInfo;
import android.media.tv.interactive.AppLinkInfo;
import android.media.tv.interactive.TvInteractiveAppServiceInfo;

import java.util.function.Function;

public final /* synthetic */ class TvInteractiveAppManagerService$$ExternalSyntheticLambda0
        implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return ((TvAdServiceInfo) obj).getId();
            case 1:
                return ((TvInteractiveAppServiceInfo) obj).getId();
            default:
                return ((AppLinkInfo) obj).getComponentName();
        }
    }
}
