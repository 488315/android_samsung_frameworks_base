package com.android.server.media;

import android.media.IMediaRouter2Manager;
import android.media.MediaRoute2Info;
import android.media.MediaRoute2ProviderInfo;
import android.media.RoutingSessionInfo;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.ArrayMap;
import android.util.Slog;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.stream.Collectors;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final /* synthetic */ class MediaRouter2ServiceImpl$$ExternalSyntheticLambda7
        implements BiConsumer {
    public final /* synthetic */ int $r8$classId;

    public /* synthetic */ MediaRouter2ServiceImpl$$ExternalSyntheticLambda7(int i) {
        this.$r8$classId = i;
    }

    @Override // java.util.function.BiConsumer
    public final void accept(Object obj, Object obj2) {
        RoutingSessionInfo routingSessionInfo;
        Set emptySet;
        Collection<MediaRoute2Info> emptySet2;
        MediaRouter2ServiceImpl.UserHandler userHandler = (MediaRouter2ServiceImpl.UserHandler) obj;
        switch (this.$r8$classId) {
            case 0:
                MediaRouter2ServiceImpl.RouterRecord routerRecord =
                        (MediaRouter2ServiceImpl.RouterRecord) obj2;
                int i = MediaRouter2ServiceImpl.UserHandler.$r8$clinit;
                userHandler.getClass();
                ArrayList arrayList = new ArrayList();
                Iterator it = ((ArrayList) userHandler.mLastProviderInfos).iterator();
                while (true) {
                    boolean hasNext = it.hasNext();
                    SystemMediaRoute2Provider systemMediaRoute2Provider =
                            userHandler.mSystemProvider;
                    if (hasNext) {
                        MediaRoute2ProviderInfo mediaRoute2ProviderInfo =
                                (MediaRoute2ProviderInfo) it.next();
                        if (TextUtils.equals(
                                mediaRoute2ProviderInfo.getUniqueId(),
                                systemMediaRoute2Provider.mUniqueId)) {
                            r0 = mediaRoute2ProviderInfo;
                        } else {
                            arrayList.addAll(mediaRoute2ProviderInfo.getRoutes());
                        }
                    } else {
                        if (routerRecord.hasSystemRoutingPermission()) {
                            if (r0 != null) {
                                arrayList.addAll(r0.getRoutes());
                            } else {
                                Slog.wtf("MR2ServiceImpl", "System route provider not found.");
                            }
                            routingSessionInfo =
                                    (RoutingSessionInfo)
                                            ((ArrayList)
                                                            systemMediaRoute2Provider
                                                                    .getSessionInfos())
                                                    .get(0);
                        } else {
                            arrayList.add(systemMediaRoute2Provider.mDefaultRoute);
                            routingSessionInfo = systemMediaRoute2Provider.mDefaultSessionInfo;
                        }
                        if (!arrayList.isEmpty()) {
                            try {
                                routerRecord.mRouter.notifyRouterRegistered(
                                        routerRecord.getVisibleRoutes(arrayList),
                                        routingSessionInfo);
                                break;
                            } catch (RemoteException e) {
                                Slog.w(
                                        "MR2ServiceImpl",
                                        "Failed to notify router registered. Router probably died.",
                                        e);
                                return;
                            }
                        }
                    }
                }
                break;
            case 1:
                IMediaRouter2Manager iMediaRouter2Manager = (IMediaRouter2Manager) obj2;
                if (!((ArrayMap) userHandler.mLastNotifiedRoutesToPrivilegedRouters).isEmpty()) {
                    try {
                        iMediaRouter2Manager.notifyRoutesUpdated(
                                new ArrayList(
                                        ((ArrayMap)
                                                        userHandler
                                                                .mLastNotifiedRoutesToPrivilegedRouters)
                                                .values()));
                        break;
                    } catch (RemoteException e2) {
                        Slog.w(
                                "MR2ServiceImpl",
                                "Failed to notify all routes. Manager probably died.",
                                e2);
                        return;
                    }
                }
                break;
            default:
                MediaRoute2Provider mediaRoute2Provider = (MediaRoute2Provider) obj2;
                userHandler.getClass();
                MediaRoute2ProviderInfo mediaRoute2ProviderInfo2 =
                        mediaRoute2Provider.mProviderInfo;
                String str = mediaRoute2Provider.mUniqueId;
                List list = userHandler.mLastProviderInfos;
                int i2 = 0;
                while (true) {
                    ArrayList arrayList2 = (ArrayList) list;
                    if (i2 >= arrayList2.size()) {
                        i2 = -1;
                    } else if (!TextUtils.equals(
                            ((MediaRoute2ProviderInfo) arrayList2.get(i2)).getUniqueId(), str)) {
                        i2++;
                    }
                }
                r0 =
                        i2 != -1
                                ? (MediaRoute2ProviderInfo)
                                        ((ArrayList) userHandler.mLastProviderInfos).get(i2)
                                : null;
                if (r0 != mediaRoute2ProviderInfo2) {
                    if (mediaRoute2ProviderInfo2 != null) {
                        emptySet2 = mediaRoute2ProviderInfo2.getRoutes();
                        emptySet =
                                (Set)
                                        emptySet2.stream()
                                                .map(
                                                        new MediaRouter2ServiceImpl$$ExternalSyntheticLambda14(
                                                                2))
                                                .collect(Collectors.toSet());
                        if (i2 >= 0) {
                            ((ArrayList) userHandler.mLastProviderInfos)
                                    .set(i2, mediaRoute2ProviderInfo2);
                        } else {
                            ((ArrayList) userHandler.mLastProviderInfos)
                                    .add(mediaRoute2ProviderInfo2);
                        }
                    } else {
                        ((ArrayList) userHandler.mLastProviderInfos).remove(r0);
                        emptySet = Collections.emptySet();
                        emptySet2 = Collections.emptySet();
                    }
                    ArrayList arrayList3 = new ArrayList();
                    boolean z = false;
                    for (MediaRoute2Info mediaRoute2Info : emptySet2) {
                        if (mediaRoute2Info.isValid()) {
                            if (!mediaRoute2Provider.mIsSystemRouteProvider) {
                                ((ArrayMap) userHandler.mLastNotifiedRoutesToNonPrivilegedRouters)
                                        .put(mediaRoute2Info.getId(), mediaRoute2Info);
                            }
                            MediaRoute2Info mediaRoute2Info2 =
                                    (MediaRoute2Info)
                                            ((ArrayMap)
                                                            userHandler
                                                                    .mLastNotifiedRoutesToPrivilegedRouters)
                                                    .put(mediaRoute2Info.getId(), mediaRoute2Info);
                            z |= !mediaRoute2Info.equals(mediaRoute2Info2);
                            if (mediaRoute2Info2 == null) {
                                arrayList3.add(mediaRoute2Info);
                            }
                        } else {
                            Slog.w(
                                    "MR2ServiceImpl",
                                    "onProviderStateChangedOnHandler: Ignoring invalid route : "
                                            + mediaRoute2Info);
                        }
                    }
                    ArrayList arrayList4 = new ArrayList();
                    boolean z2 = false;
                    for (MediaRoute2Info mediaRoute2Info3 :
                            r0 == null ? Collections.emptyList() : r0.getRoutes()) {
                        String id = mediaRoute2Info3.getId();
                        if (!emptySet.contains(id)) {
                            ((ArrayMap) userHandler.mLastNotifiedRoutesToPrivilegedRouters)
                                    .remove(id);
                            ((ArrayMap) userHandler.mLastNotifiedRoutesToNonPrivilegedRouters)
                                    .remove(id);
                            arrayList4.add(mediaRoute2Info3);
                            z2 = true;
                        }
                    }
                    if (!arrayList3.isEmpty()) {
                        Slog.i(
                                "MR2ServiceImpl",
                                MediaRouter2ServiceImpl.UserHandler.toLoggingMessage(
                                        "addProviderRoutes",
                                        mediaRoute2ProviderInfo2.getUniqueId(),
                                        arrayList3));
                    }
                    if (!arrayList4.isEmpty()) {
                        Slog.i(
                                "MR2ServiceImpl",
                                MediaRouter2ServiceImpl.UserHandler.toLoggingMessage(
                                        "removeProviderRoutes", r0.getUniqueId(), arrayList4));
                    }
                    boolean z3 = mediaRoute2Provider.mIsSystemRouteProvider;
                    MediaRoute2Info mediaRoute2Info4 = userHandler.mSystemProvider.mDefaultRoute;
                    if (z || z2) {
                        List routerRecords = userHandler.getRouterRecords(true);
                        List routerRecords2 = userHandler.getRouterRecords(false);
                        List managers = userHandler.getManagers();
                        ArrayList arrayList5 =
                                new ArrayList(
                                        ((ArrayMap)
                                                        userHandler
                                                                .mLastNotifiedRoutesToPrivilegedRouters)
                                                .values());
                        Iterator it2 = ((ArrayList) managers).iterator();
                        while (it2.hasNext()) {
                            try {
                                ((IMediaRouter2Manager) it2.next()).notifyRoutesUpdated(arrayList5);
                            } catch (RemoteException e3) {
                                Slog.w(
                                        "MR2ServiceImpl",
                                        "Failed to notify routes changed. Manager probably died.",
                                        e3);
                            }
                        }
                        MediaRouter2ServiceImpl.UserHandler.notifyRoutesUpdatedToRouterRecords(
                                routerRecords,
                                new ArrayList(
                                        ((ArrayMap)
                                                        userHandler
                                                                .mLastNotifiedRoutesToPrivilegedRouters)
                                                .values()));
                        if (z3) {
                            if (z) {
                                ((ArrayMap) userHandler.mLastNotifiedRoutesToNonPrivilegedRouters)
                                        .put(mediaRoute2Info4.getId(), mediaRoute2Info4);
                                MediaRouter2ServiceImpl.UserHandler
                                        .notifyRoutesUpdatedToRouterRecords(
                                                routerRecords2,
                                                new ArrayList(
                                                        ((ArrayMap)
                                                                        userHandler
                                                                                .mLastNotifiedRoutesToNonPrivilegedRouters)
                                                                .values()));
                                break;
                            }
                        } else {
                            MediaRouter2ServiceImpl.UserHandler.notifyRoutesUpdatedToRouterRecords(
                                    routerRecords2,
                                    new ArrayList(
                                            ((ArrayMap)
                                                            userHandler
                                                                    .mLastNotifiedRoutesToNonPrivilegedRouters)
                                                    .values()));
                            break;
                        }
                    }
                }
                break;
        }
    }
}
