package com.android.server.media;

import android.media.MediaRoute2Info;
import android.media.MediaRouter2Utils;

import com.android.internal.util.function.QuintConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class MediaRouter2ServiceImpl$$ExternalSyntheticLambda0
        implements QuintConsumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ MediaRouter2ServiceImpl$$ExternalSyntheticLambda0(int i) {
        this.$r8$classId = i;
    }

    public final void accept(Object obj, Object obj2, Object obj3, Object obj4, Object obj5) {
        MediaRoute2Provider findProvider;
        MediaRoute2Provider findProvider2;
        int i = this.$r8$classId;
        MediaRouter2ServiceImpl.UserHandler userHandler = (MediaRouter2ServiceImpl.UserHandler) obj;
        long longValue = ((Long) obj2).longValue();
        MediaRouter2ServiceImpl.RouterRecord routerRecord =
                (MediaRouter2ServiceImpl.RouterRecord) obj3;
        String str = (String) obj4;
        MediaRoute2Info mediaRoute2Info = (MediaRoute2Info) obj5;
        switch (i) {
            case 0:
                int i2 = MediaRouter2ServiceImpl.UserHandler.$r8$clinit;
                if (userHandler.checkArgumentsForSessionControl(
                                routerRecord, str, mediaRoute2Info, "selecting")
                        && (findProvider =
                                        userHandler.findProvider(mediaRoute2Info.getProviderId()))
                                != null) {
                    findProvider.selectRoute(
                            MediaRouter2Utils.getOriginalId(str),
                            longValue,
                            mediaRoute2Info.getOriginalId());
                    break;
                }
                break;
            default:
                int i3 = MediaRouter2ServiceImpl.UserHandler.$r8$clinit;
                if (userHandler.checkArgumentsForSessionControl(
                                routerRecord, str, mediaRoute2Info, "deselecting")
                        && (findProvider2 =
                                        userHandler.findProvider(mediaRoute2Info.getProviderId()))
                                != null) {
                    findProvider2.deselectRoute(
                            MediaRouter2Utils.getOriginalId(str),
                            longValue,
                            mediaRoute2Info.getOriginalId());
                    break;
                }
                break;
        }
    }
}
