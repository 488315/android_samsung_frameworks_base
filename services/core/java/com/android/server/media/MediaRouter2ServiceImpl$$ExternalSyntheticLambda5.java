package com.android.server.media;

import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class MediaRouter2ServiceImpl$$ExternalSyntheticLambda5
        implements Consumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ MediaRouter2ServiceImpl$$ExternalSyntheticLambda5(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        MediaRouter2ServiceImpl.UserHandler userHandler = (MediaRouter2ServiceImpl.UserHandler) obj;
        switch (this.$r8$classId) {
            case 0:
                int i = MediaRouter2ServiceImpl.UserHandler.$r8$clinit;
                userHandler.updateDiscoveryPreferenceOnHandler();
                break;
            case 1:
                MediaRouter2ServiceImpl.$r8$lambda$QUOfWBbs9bzeEoCNqoDrNBPHovk(userHandler);
                break;
            case 2:
                MediaRouter2ServiceImpl.m652$r8$lambda$_KlJMldyhEHjCtdEMZwMuezeUQ(userHandler);
                break;
            default:
                userHandler.updateDiscoveryPreferenceOnHandler();
                break;
        }
    }
}
