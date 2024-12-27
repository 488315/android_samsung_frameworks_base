package com.android.server.tv.interactive;

import android.media.tv.ad.TvAdServiceInfo;
import android.media.tv.interactive.AppLinkInfo;
import android.media.tv.interactive.TvInteractiveAppServiceInfo;
import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class TvInteractiveAppManagerService$$ExternalSyntheticLambda0 implements Function {
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
