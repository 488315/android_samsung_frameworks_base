package com.android.server.media;

import android.media.MediaRoute2Info;
import android.media.RoutingSessionInfo;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Slog;
import com.android.internal.util.function.OctConsumer;
import com.android.server.media.MediaRouter2ServiceImpl;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class MediaRouter2ServiceImpl$$ExternalSyntheticLambda12 implements OctConsumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ MediaRouter2ServiceImpl$$ExternalSyntheticLambda12(int i) {
        this.$r8$classId = i;
    }

    public final void accept(Object obj, Object obj2, Object obj3, Object obj4, Object obj5, Object obj6, Object obj7, Object obj8) {
        switch (this.$r8$classId) {
            case 0:
                MediaRouter2ServiceImpl.$r8$lambda$VwOkF_wuq7S_wu2mL143qz8S5B4((MediaRouter2ServiceImpl.UserHandler) obj, ((Long) obj2).longValue(), (UserHandle) obj3, (String) obj4, (MediaRouter2ServiceImpl.RouterRecord) obj5, (String) obj6, (MediaRoute2Info) obj7, ((Integer) obj8).intValue());
                break;
            default:
                MediaRouter2ServiceImpl.UserHandler userHandler = (MediaRouter2ServiceImpl.UserHandler) obj;
                long longValue = ((Long) obj2).longValue();
                MediaRouter2ServiceImpl.RouterRecord routerRecord = (MediaRouter2ServiceImpl.RouterRecord) obj3;
                MediaRouter2ServiceImpl.ManagerRecord managerRecord = (MediaRouter2ServiceImpl.ManagerRecord) obj4;
                RoutingSessionInfo routingSessionInfo = (RoutingSessionInfo) obj5;
                MediaRoute2Info mediaRoute2Info = (MediaRoute2Info) obj6;
                int i = MediaRouter2ServiceImpl.UserHandler.$r8$clinit;
                userHandler.getClass();
                try {
                    if (mediaRoute2Info.isSystemRoute() && !routerRecord.hasSystemRoutingPermission()) {
                        mediaRoute2Info = userHandler.mSystemProvider.mDefaultRoute;
                    }
                    routerRecord.mRouter.requestCreateSessionByManager(longValue, routingSessionInfo, mediaRoute2Info);
                    break;
                } catch (RemoteException e) {
                    Slog.w("MR2ServiceImpl", "getSessionHintsForCreatingSessionOnHandler: Failed to request. Router probably died.", e);
                    MediaRouter2ServiceImpl.UserHandler.notifyRequestFailedToManager(managerRecord.mManager, (int) longValue, 0);
                }
        }
    }
}
