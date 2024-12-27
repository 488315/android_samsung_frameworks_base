package com.android.server.media;

import android.media.IMediaRouter2Manager;
import android.media.RouteDiscoveryPreference;
import android.media.RouteListingPreference;
import android.media.RoutingSessionInfo;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.Slog;
import com.android.internal.util.function.TriConsumer;
import com.android.server.media.MediaRouter2ServiceImpl;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class MediaRouter2ServiceImpl$$ExternalSyntheticLambda3 implements TriConsumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ MediaRouter2ServiceImpl$$ExternalSyntheticLambda3(int i) {
        this.$r8$classId = i;
    }

    public final void accept(Object obj, Object obj2, Object obj3) {
        MediaRouter2ServiceImpl.UserHandler userHandler = (MediaRouter2ServiceImpl.UserHandler) obj;
        switch (this.$r8$classId) {
            case 0:
                MediaRouter2ServiceImpl.m654$r8$lambda$l439RW2gb6MOGygsuamHEVj0Rk(userHandler, (String) obj2, (RouteDiscoveryPreference) obj3);
                break;
            case 1:
                int intValue = ((Integer) obj3).intValue();
                int i = MediaRouter2ServiceImpl.UserHandler.$r8$clinit;
                userHandler.getClass();
                MediaRouter2ServiceImpl.UserHandler.notifySessionCreationFailedToRouter((MediaRouter2ServiceImpl.RouterRecord) obj2, intValue);
                break;
            case 2:
                MediaRouter2ServiceImpl.RouterRecord routerRecord = (MediaRouter2ServiceImpl.RouterRecord) obj2;
                IMediaRouter2Manager iMediaRouter2Manager = (IMediaRouter2Manager) obj3;
                int i2 = MediaRouter2ServiceImpl.UserHandler.$r8$clinit;
                userHandler.getClass();
                try {
                    iMediaRouter2Manager.notifyDiscoveryPreferenceChanged(routerRecord.mPackageName, routerRecord.mDiscoveryPreference);
                    break;
                } catch (RemoteException e) {
                    Slog.w("MR2ServiceImpl", "Failed to notify preferred features changed. Manager probably died.", e);
                    return;
                }
            case 3:
                MediaRouter2ServiceImpl.$r8$lambda$heOK_4zIzngS8WMr61qD_midgrQ(userHandler, (String) obj2, (RouteListingPreference) obj3);
                break;
            case 4:
                RoutingSessionInfo routingSessionInfo = (RoutingSessionInfo) obj3;
                Iterator it = ((ArrayList) userHandler.getManagers()).iterator();
                while (it.hasNext()) {
                    try {
                        ((IMediaRouter2Manager) it.next()).notifySessionReleased(routingSessionInfo);
                    } catch (RemoteException e2) {
                        Slog.w("MR2ServiceImpl", "notifySessionReleasedToManagers: Failed to notify. Manager probably died.", e2);
                    }
                }
                MediaRouter2ServiceImpl.RouterRecord routerRecord2 = (MediaRouter2ServiceImpl.RouterRecord) ((ArrayMap) userHandler.mSessionToRouterMap).get(routingSessionInfo.getId());
                if (routerRecord2 == null) {
                    Slog.w("MR2ServiceImpl", "onSessionReleasedOnHandler: No matching router found for session=" + routingSessionInfo);
                    break;
                } else {
                    try {
                        routerRecord2.mRouter.notifySessionReleased(routingSessionInfo);
                        break;
                    } catch (RemoteException e3) {
                        Slog.w("MR2ServiceImpl", "Failed to notify router of the session release. Router probably died.", e3);
                        return;
                    }
                }
            default:
                MediaRoute2Provider mediaRoute2Provider = (MediaRoute2Provider) obj2;
                RoutingSessionInfo routingSessionInfo2 = (RoutingSessionInfo) obj3;
                Iterator it2 = ((ArrayList) userHandler.getManagers()).iterator();
                while (it2.hasNext()) {
                    try {
                        ((IMediaRouter2Manager) it2.next()).notifySessionUpdated(routingSessionInfo2);
                    } catch (RemoteException e4) {
                        Slog.w("MR2ServiceImpl", "notifySessionUpdatedToManagers: Failed to notify. Manager probably died.", e4);
                    }
                }
                SystemMediaRoute2Provider systemMediaRoute2Provider = userHandler.mSystemProvider;
                if (mediaRoute2Provider != systemMediaRoute2Provider) {
                    MediaRouter2ServiceImpl.RouterRecord routerRecord3 = (MediaRouter2ServiceImpl.RouterRecord) ((ArrayMap) userHandler.mSessionToRouterMap).get(routingSessionInfo2.getId());
                    if (routerRecord3 != null) {
                        MediaRouter2ServiceImpl.UserHandler.notifySessionInfoChangedToRouters(Arrays.asList(routerRecord3), routingSessionInfo2);
                        break;
                    } else {
                        Slog.w("MR2ServiceImpl", "onSessionInfoChangedOnHandler: No matching router found for session=" + routingSessionInfo2);
                        break;
                    }
                } else if (userHandler.mServiceRef.get() != null) {
                    MediaRouter2ServiceImpl.UserHandler.notifySessionInfoChangedToRouters(userHandler.getRouterRecords(true), routingSessionInfo2);
                    MediaRouter2ServiceImpl.UserHandler.notifySessionInfoChangedToRouters(userHandler.getRouterRecords(false), systemMediaRoute2Provider.mDefaultSessionInfo);
                    break;
                }
                break;
        }
    }
}
