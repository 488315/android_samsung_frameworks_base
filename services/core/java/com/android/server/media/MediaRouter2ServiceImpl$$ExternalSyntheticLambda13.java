package com.android.server.media;

import android.media.MediaRoute2Info;
import android.media.RoutingSessionInfo;
import android.os.Bundle;
import android.os.UserHandle;
import android.util.Slog;

import com.android.internal.util.function.HeptConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class MediaRouter2ServiceImpl$$ExternalSyntheticLambda13
        implements HeptConsumer {
    public final void accept(
            Object obj,
            Object obj2,
            Object obj3,
            Object obj4,
            Object obj5,
            Object obj6,
            Object obj7) {
        MediaRouter2ServiceImpl.UserHandler userHandler = (MediaRouter2ServiceImpl.UserHandler) obj;
        long longValue = ((Long) obj2).longValue();
        long longValue2 = ((Long) obj3).longValue();
        MediaRouter2ServiceImpl.RouterRecord routerRecord =
                (MediaRouter2ServiceImpl.RouterRecord) obj4;
        RoutingSessionInfo routingSessionInfo = (RoutingSessionInfo) obj5;
        MediaRoute2Info mediaRoute2Info = (MediaRoute2Info) obj6;
        Bundle bundle = (Bundle) obj7;
        int i = MediaRouter2ServiceImpl.UserHandler.$r8$clinit;
        userHandler.getClass();
        MediaRoute2Provider findProvider =
                userHandler.findProvider(mediaRoute2Info.getProviderId());
        if (findProvider != null) {
            userHandler.mSessionCreationRequests.add(
                    new MediaRouter2ServiceImpl.SessionCreationRequest(
                            routerRecord,
                            longValue,
                            longValue2,
                            routingSessionInfo,
                            mediaRoute2Info));
            findProvider.requestCreateSession(
                    longValue,
                    routerRecord.mPackageName,
                    mediaRoute2Info.getOriginalId(),
                    bundle,
                    longValue2 != 0 ? 1 : 2,
                    UserHandle.of(routerRecord.mUserRecord.mUserId),
                    routerRecord.mPackageName);
        } else {
            Slog.w(
                    "MR2ServiceImpl",
                    "requestCreateSessionWithRouter2OnHandler: Ignoring session creation request"
                        + " since no provider found for given route="
                            + mediaRoute2Info);
            MediaRouter2ServiceImpl.UserHandler.notifySessionCreationFailedToRouter(
                    routerRecord, (int) longValue);
        }
    }
}
