package com.android.server.media;

import android.media.MediaRoute2Info;
import android.media.RoutingSessionInfo;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Slog;

import com.android.internal.util.function.QuadConsumer;

import java.util.Iterator;
import java.util.Map;

public final /* synthetic */ class MediaRouter2ServiceImpl$$ExternalSyntheticLambda1
        implements QuadConsumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ MediaRouter2ServiceImpl$$ExternalSyntheticLambda1(int i) {
        this.$r8$classId = i;
    }

    public final void accept(Object obj, Object obj2, Object obj3, Object obj4) {
        MediaRouter2ServiceImpl.SessionCreationRequest sessionCreationRequest = null;
        MediaRouter2ServiceImpl.UserHandler userHandler = (MediaRouter2ServiceImpl.UserHandler) obj;
        switch (this.$r8$classId) {
            case 0:
                MediaRouter2ServiceImpl.m653$r8$lambda$e9Sk6TFecCbShGZjiI_FsGfvEs(
                        userHandler,
                        ((Long) obj2).longValue(),
                        (MediaRoute2Info) obj3,
                        ((Integer) obj4).intValue());
                break;
            case 1:
                MediaRouter2ServiceImpl.$r8$lambda$fFTtstVLACy5dqiCA6mMwECkaiQ(
                        userHandler,
                        ((Long) obj2).longValue(),
                        (MediaRouter2ServiceImpl.RouterRecord) obj3,
                        (String) obj4);
                break;
            case 2:
                MediaRouter2ServiceImpl.$r8$lambda$gfBNjj_qEN5gYbuwjbOFgMrSMpU(
                        userHandler,
                        ((Long) obj2).longValue(),
                        (String) obj3,
                        ((Integer) obj4).intValue());
                break;
            case 3:
                MediaRoute2Provider mediaRoute2Provider = (MediaRoute2Provider) obj2;
                long longValue = ((Long) obj3).longValue();
                RoutingSessionInfo routingSessionInfo = (RoutingSessionInfo) obj4;
                Iterator it = userHandler.mSessionCreationRequests.iterator();
                while (true) {
                    if (it.hasNext()) {
                        MediaRouter2ServiceImpl.SessionCreationRequest sessionCreationRequest2 =
                                (MediaRouter2ServiceImpl.SessionCreationRequest) it.next();
                        if (sessionCreationRequest2.mUniqueRequestId == longValue
                                && TextUtils.equals(
                                        sessionCreationRequest2.mRoute.getProviderId(),
                                        mediaRoute2Provider.mUniqueId)) {
                            sessionCreationRequest = sessionCreationRequest2;
                        }
                    }
                }
                long j =
                        sessionCreationRequest != null
                                ? sessionCreationRequest.mManagerRequestId
                                : 0L;
                boolean z = MediaRouter2ServiceImpl.DEBUG;
                int i = (int) (j >> 32);
                int i2 = (int) j;
                for (MediaRouter2ServiceImpl.ManagerRecord managerRecord :
                        userHandler.getManagerRecords()) {
                    try {
                        managerRecord.mManager.notifySessionCreated(
                                managerRecord.mManagerId == i ? i2 : 0, routingSessionInfo);
                    } catch (RemoteException e) {
                        Slog.w(
                                "MR2ServiceImpl",
                                "notifySessionCreatedToManagers: Failed to notify. Manager probably"
                                    + " died.",
                                e);
                    }
                }
                if (sessionCreationRequest == null) {
                    Slog.w(
                            "MR2ServiceImpl",
                            "Ignoring session creation result for unknown request. uniqueRequestId="
                                    + longValue
                                    + ", sessionInfo="
                                    + routingSessionInfo);
                    break;
                } else {
                    userHandler.mSessionCreationRequests.remove(sessionCreationRequest);
                    MediaRoute2Provider findProvider =
                            userHandler.findProvider(
                                    sessionCreationRequest.mOldSession.getProviderId());
                    if (findProvider != null) {
                        findProvider.prepareReleaseSession(
                                sessionCreationRequest.mOldSession.getId());
                    } else {
                        Slog.w(
                                "MR2ServiceImpl",
                                "onSessionCreatedOnHandler: Can't find provider for an old session."
                                    + " session="
                                        + sessionCreationRequest.mOldSession);
                    }
                    Map map = userHandler.mSessionToRouterMap;
                    String id = routingSessionInfo.getId();
                    MediaRouter2ServiceImpl.RouterRecord routerRecord =
                            sessionCreationRequest.mRouterRecord;
                    ((ArrayMap) map).put(id, routerRecord);
                    if (routingSessionInfo.isSystemSession()
                            && !routerRecord.hasSystemRoutingPermission()) {
                        routingSessionInfo = userHandler.mSystemProvider.mDefaultSessionInfo;
                    }
                    int i3 = (int) longValue;
                    routerRecord.getClass();
                    try {
                        routerRecord.mRouter.notifySessionCreated(
                                i3,
                                routerRecord.maybeClearTransferInitiatorIdentity(
                                        routingSessionInfo));
                        break;
                    } catch (RemoteException e2) {
                        Slog.w(
                                "MR2ServiceImpl",
                                "Failed to notify router of the session creation. Router probably"
                                    + " died.",
                                e2);
                        return;
                    }
                }
                break;
            default:
                MediaRoute2Provider mediaRoute2Provider2 = (MediaRoute2Provider) obj2;
                Long l = (Long) obj3;
                long longValue2 = l.longValue();
                Integer num = (Integer) obj4;
                int intValue = num.intValue();
                Iterator it2 = userHandler.mSessionCreationRequests.iterator();
                while (true) {
                    if (it2.hasNext()) {
                        MediaRouter2ServiceImpl.SessionCreationRequest sessionCreationRequest3 =
                                (MediaRouter2ServiceImpl.SessionCreationRequest) it2.next();
                        if (sessionCreationRequest3.mUniqueRequestId == longValue2
                                && TextUtils.equals(
                                        sessionCreationRequest3.mRoute.getProviderId(),
                                        mediaRoute2Provider2.mUniqueId)) {
                            sessionCreationRequest = sessionCreationRequest3;
                        }
                    }
                }
                if (sessionCreationRequest == null) {
                    Slog.w(
                            "MR2ServiceImpl",
                            TextUtils.formatSimple(
                                    "handleSessionCreationRequestFailed | No matching request found"
                                        + " for provider: %s, uniqueRequestId: %d, reason: %d",
                                    new Object[] {mediaRoute2Provider2.mUniqueId, l, num}));
                    boolean z2 = MediaRouter2ServiceImpl.DEBUG;
                    MediaRouter2ServiceImpl.ManagerRecord findManagerWithId =
                            userHandler.findManagerWithId((int) (longValue2 >> 32));
                    if (findManagerWithId != null) {
                        MediaRouter2ServiceImpl.UserHandler.notifyRequestFailedToManager(
                                findManagerWithId.mManager, (int) longValue2, intValue);
                        break;
                    }
                } else {
                    userHandler.mSessionCreationRequests.remove(sessionCreationRequest);
                    long j2 = sessionCreationRequest.mManagerRequestId;
                    if (j2 == 0) {
                        boolean z3 = MediaRouter2ServiceImpl.DEBUG;
                        MediaRouter2ServiceImpl.UserHandler.notifySessionCreationFailedToRouter(
                                sessionCreationRequest.mRouterRecord, (int) longValue2);
                    } else {
                        boolean z4 = MediaRouter2ServiceImpl.DEBUG;
                        MediaRouter2ServiceImpl.ManagerRecord findManagerWithId2 =
                                userHandler.findManagerWithId((int) (j2 >> 32));
                        if (findManagerWithId2 != null) {
                            MediaRouter2ServiceImpl.UserHandler.notifyRequestFailedToManager(
                                    findManagerWithId2.mManager, (int) j2, intValue);
                        }
                    }
                    Slog.w(
                            "MR2ServiceImpl",
                            TextUtils.formatSimple(
                                    "onRequestFailedOnHandler | Finished handling session creation"
                                        + " request failed for provider: %s, uniqueRequestId: %d,"
                                        + " reason: %d",
                                    new Object[] {mediaRoute2Provider2.mUniqueId, l, num}));
                    break;
                }
                break;
        }
    }
}
