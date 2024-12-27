package com.android.server.media;

import android.media.MediaRoute2Info;
import android.media.RouteListingPreference;

import java.util.function.Function;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class MediaRouter2ServiceImpl$$ExternalSyntheticLambda14
        implements Function {
    public final /* synthetic */ int $r8$classId;

    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                return ((RouteListingPreference.Item) obj).getRouteId();
            case 1:
                MediaRoute2Info mediaRoute2Info = (MediaRoute2Info) obj;
                return String.format(
                        "%s | %s", mediaRoute2Info.getOriginalId(), mediaRoute2Info.getName());
            default:
                return ((MediaRoute2Info) obj).getId();
        }
    }
}
