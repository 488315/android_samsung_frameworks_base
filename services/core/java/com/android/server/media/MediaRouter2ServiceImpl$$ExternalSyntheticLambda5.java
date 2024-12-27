package com.android.server.media;

import java.util.function.Consumer;

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
