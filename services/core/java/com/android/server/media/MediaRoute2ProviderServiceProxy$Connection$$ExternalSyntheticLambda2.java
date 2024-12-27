package com.android.server.media;

import android.content.ComponentName;
import android.media.MediaRoute2ProviderInfo;
import android.media.RoutingSessionInfo;
import android.os.Handler;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Slog;

import com.android.internal.util.function.TriConsumer;
import com.android.internal.util.function.pooled.PooledLambda;
import com.android.media.flags.Flags;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */
class MediaRoute2ProviderServiceProxy$Connection$$ExternalSyntheticLambda2 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ MediaRoute2ProviderServiceProxy$Connection$$ExternalSyntheticLambda2(
            int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    public /* synthetic */ MediaRoute2ProviderServiceProxy$Connection$$ExternalSyntheticLambda2(
            MediaRoute2ProviderServiceProxy.Connection connection,
            RoutingSessionInfo routingSessionInfo) {
        this.$r8$classId = 1;
        this.f$0 = connection;
        this.f$1 = routingSessionInfo;
    }

    @Override // java.lang.Runnable
    public final void run() {
        boolean z;
        switch (this.$r8$classId) {
            case 0:
                MediaRoute2ProviderServiceProxy.Connection connection =
                        (MediaRoute2ProviderServiceProxy.Connection) this.f$0;
                MediaRoute2ProviderInfo mediaRoute2ProviderInfo =
                        (MediaRoute2ProviderInfo) this.f$1;
                MediaRoute2ProviderServiceProxy mediaRoute2ProviderServiceProxy =
                        MediaRoute2ProviderServiceProxy.this;
                if (mediaRoute2ProviderServiceProxy.mActiveConnection != connection) {
                    return;
                }
                if (MediaRoute2ProviderServiceProxy.DEBUG) {
                    Slog.d("MR2ProviderSvcProxy", mediaRoute2ProviderServiceProxy + ": updated");
                }
                mediaRoute2ProviderServiceProxy.setProviderState(mediaRoute2ProviderInfo);
                mediaRoute2ProviderServiceProxy.notifyProviderState();
                return;
            case 1:
                MediaRoute2ProviderServiceProxy.Connection connection2 =
                        (MediaRoute2ProviderServiceProxy.Connection) this.f$0;
                RoutingSessionInfo routingSessionInfo = (RoutingSessionInfo) this.f$1;
                MediaRoute2ProviderServiceProxy mediaRoute2ProviderServiceProxy2 =
                        MediaRoute2ProviderServiceProxy.this;
                if (mediaRoute2ProviderServiceProxy2.mActiveConnection != connection2) {
                    return;
                }
                if (routingSessionInfo == null) {
                    Slog.w(
                            "MR2ProviderSvcProxy",
                            "onSessionReleased: Ignoring null session sent from "
                                    + mediaRoute2ProviderServiceProxy2.mComponentName);
                    return;
                }
                RoutingSessionInfo assignProviderIdForSession =
                        mediaRoute2ProviderServiceProxy2.assignProviderIdForSession(
                                routingSessionInfo);
                synchronized (mediaRoute2ProviderServiceProxy2.mLock) {
                    try {
                        ((HashMap)
                                        mediaRoute2ProviderServiceProxy2
                                                .mSessionOriginalIdToTransferRequest)
                                .remove(assignProviderIdForSession.getId());
                        Iterator it =
                                ((ArrayList) mediaRoute2ProviderServiceProxy2.mSessionInfos)
                                        .iterator();
                        while (true) {
                            if (it.hasNext()) {
                                RoutingSessionInfo routingSessionInfo2 =
                                        (RoutingSessionInfo) it.next();
                                if (TextUtils.equals(
                                        routingSessionInfo2.getId(),
                                        assignProviderIdForSession.getId())) {
                                    ((ArrayList) mediaRoute2ProviderServiceProxy2.mSessionInfos)
                                            .remove(routingSessionInfo2);
                                    z = true;
                                }
                            } else {
                                z = false;
                            }
                        }
                        if (!z) {
                            Iterator it2 =
                                    ((ArrayList)
                                                    mediaRoute2ProviderServiceProxy2
                                                            .mReleasingSessions)
                                            .iterator();
                            while (it2.hasNext()) {
                                RoutingSessionInfo routingSessionInfo3 =
                                        (RoutingSessionInfo) it2.next();
                                if (TextUtils.equals(
                                        routingSessionInfo3.getId(),
                                        assignProviderIdForSession.getId())) {
                                    ((ArrayList)
                                                    mediaRoute2ProviderServiceProxy2
                                                            .mReleasingSessions)
                                            .remove(routingSessionInfo3);
                                    return;
                                }
                            }
                        }
                        if (!z) {
                            Slog.w(
                                    "MR2ProviderSvcProxy",
                                    "onSessionReleased: Matching session info not found");
                            return;
                        }
                        MediaRouter2ServiceImpl.UserHandler userHandler =
                                (MediaRouter2ServiceImpl.UserHandler)
                                        mediaRoute2ProviderServiceProxy2.mCallback;
                        userHandler.getClass();
                        userHandler.sendMessage(
                                PooledLambda.obtainMessage(
                                        new MediaRouter2ServiceImpl$$ExternalSyntheticLambda3(4),
                                        userHandler,
                                        mediaRoute2ProviderServiceProxy2,
                                        assignProviderIdForSession));
                        return;
                    } finally {
                    }
                }
            case 2:
                MediaRoute2ProviderServiceProxy.Connection connection3 =
                        (MediaRoute2ProviderServiceProxy.Connection) this.f$0;
                List<RoutingSessionInfo> list = (List) this.f$1;
                MediaRoute2ProviderServiceProxy mediaRoute2ProviderServiceProxy3 =
                        MediaRoute2ProviderServiceProxy.this;
                if (mediaRoute2ProviderServiceProxy3.mActiveConnection != connection3) {
                    return;
                }
                synchronized (mediaRoute2ProviderServiceProxy3.mLock) {
                    try {
                        int i = 0;
                        for (RoutingSessionInfo routingSessionInfo4 : list) {
                            if (routingSessionInfo4 != null) {
                                RoutingSessionInfo assignProviderIdForSession2 =
                                        mediaRoute2ProviderServiceProxy3.assignProviderIdForSession(
                                                routingSessionInfo4);
                                int i2 = 0;
                                while (true) {
                                    if (i2
                                            >= ((ArrayList)
                                                            mediaRoute2ProviderServiceProxy3
                                                                    .mSessionInfos)
                                                    .size()) {
                                        i2 = -1;
                                    } else if (!TextUtils.equals(
                                            ((RoutingSessionInfo)
                                                            ((ArrayList)
                                                                            mediaRoute2ProviderServiceProxy3
                                                                                    .mSessionInfos)
                                                                    .get(i2))
                                                    .getId(),
                                            assignProviderIdForSession2.getId())) {
                                        i2++;
                                    }
                                }
                                if (i2 < 0) {
                                    int i3 = i + 1;
                                    ((ArrayList) mediaRoute2ProviderServiceProxy3.mSessionInfos)
                                            .add(i, assignProviderIdForSession2);
                                    Handler handler = mediaRoute2ProviderServiceProxy3.mHandler;
                                    final MediaRoute2Provider.Callback callback =
                                            mediaRoute2ProviderServiceProxy3.mCallback;
                                    Objects.requireNonNull(callback);
                                    handler.sendMessage(
                                            PooledLambda.obtainMessage(
                                                    new TriConsumer() { // from class:
                                                                        // com.android.server.media.MediaRoute2ProviderServiceProxy$$ExternalSyntheticLambda4
                                                        public final void accept(
                                                                Object obj,
                                                                Object obj2,
                                                                Object obj3) {
                                                            ((MediaRouter2ServiceImpl.UserHandler)
                                                                            MediaRoute2Provider
                                                                                    .Callback.this)
                                                                    .onSessionCreated(
                                                                            (MediaRoute2ProviderServiceProxy)
                                                                                    obj,
                                                                            ((Long) obj2)
                                                                                    .longValue(),
                                                                            (RoutingSessionInfo)
                                                                                    obj3);
                                                        }
                                                    },
                                                    mediaRoute2ProviderServiceProxy3,
                                                    0L,
                                                    assignProviderIdForSession2));
                                    i = i3;
                                } else if (i2 < i) {
                                    Slog.w(
                                            "MR2ProviderSvcProxy",
                                            "Ignoring duplicate session ID: "
                                                    + assignProviderIdForSession2.getId());
                                } else {
                                    if (Flags.enableBuiltInSpeakerRouteSuitabilityStatuses()) {
                                        assignProviderIdForSession2 =
                                                mediaRoute2ProviderServiceProxy3
                                                        .createSessionWithPopulatedTransferInitiationDataLocked(
                                                                0L,
                                                                (RoutingSessionInfo)
                                                                        ((ArrayList)
                                                                                        mediaRoute2ProviderServiceProxy3
                                                                                                .mSessionInfos)
                                                                                .get(i2),
                                                                assignProviderIdForSession2);
                                    }
                                    ((ArrayList) mediaRoute2ProviderServiceProxy3.mSessionInfos)
                                            .set(i2, assignProviderIdForSession2);
                                    int i4 = i + 1;
                                    Collections.swap(
                                            mediaRoute2ProviderServiceProxy3.mSessionInfos, i2, i);
                                    Handler handler2 = mediaRoute2ProviderServiceProxy3.mHandler;
                                    final MediaRoute2Provider.Callback callback2 =
                                            mediaRoute2ProviderServiceProxy3.mCallback;
                                    Objects.requireNonNull(callback2);
                                    final int i5 = 0;
                                    handler2.sendMessage(
                                            PooledLambda.obtainMessage(
                                                    new BiConsumer() { // from class:
                                                                       // com.android.server.media.MediaRoute2ProviderServiceProxy$$ExternalSyntheticLambda2
                                                        @Override // java.util.function.BiConsumer
                                                        public final void accept(
                                                                Object obj, Object obj2) {
                                                            int i6 = i5;
                                                            MediaRoute2ProviderServiceProxy
                                                                    mediaRoute2ProviderServiceProxy4 =
                                                                            (MediaRoute2ProviderServiceProxy)
                                                                                    obj;
                                                            RoutingSessionInfo routingSessionInfo5 =
                                                                    (RoutingSessionInfo) obj2;
                                                            MediaRouter2ServiceImpl.UserHandler
                                                                    userHandler2 =
                                                                            (MediaRouter2ServiceImpl
                                                                                            .UserHandler)
                                                                                    callback2;
                                                            userHandler2.getClass();
                                                            switch (i6) {
                                                                case 0:
                                                                    userHandler2.sendMessage(
                                                                            PooledLambda
                                                                                    .obtainMessage(
                                                                                            new MediaRouter2ServiceImpl$$ExternalSyntheticLambda3(
                                                                                                    5),
                                                                                            userHandler2,
                                                                                            mediaRoute2ProviderServiceProxy4,
                                                                                            routingSessionInfo5));
                                                                    break;
                                                                default:
                                                                    userHandler2.sendMessage(
                                                                            PooledLambda
                                                                                    .obtainMessage(
                                                                                            new MediaRouter2ServiceImpl$$ExternalSyntheticLambda3(
                                                                                                    4),
                                                                                            userHandler2,
                                                                                            mediaRoute2ProviderServiceProxy4,
                                                                                            routingSessionInfo5));
                                                                    break;
                                                            }
                                                        }
                                                    },
                                                    mediaRoute2ProviderServiceProxy3,
                                                    assignProviderIdForSession2));
                                    i = i4;
                                }
                            }
                        }
                        for (int size =
                                        ((ArrayList) mediaRoute2ProviderServiceProxy3.mSessionInfos)
                                                        .size()
                                                - 1;
                                size >= i;
                                size--) {
                            RoutingSessionInfo routingSessionInfo5 =
                                    (RoutingSessionInfo)
                                            ((ArrayList)
                                                            mediaRoute2ProviderServiceProxy3
                                                                    .mSessionInfos)
                                                    .remove(size);
                            ((HashMap)
                                            mediaRoute2ProviderServiceProxy3
                                                    .mSessionOriginalIdToTransferRequest)
                                    .remove(routingSessionInfo5.getId());
                            Handler handler3 = mediaRoute2ProviderServiceProxy3.mHandler;
                            final MediaRoute2Provider.Callback callback3 =
                                    mediaRoute2ProviderServiceProxy3.mCallback;
                            Objects.requireNonNull(callback3);
                            final int i6 = 1;
                            handler3.sendMessage(
                                    PooledLambda.obtainMessage(
                                            new BiConsumer() { // from class:
                                                               // com.android.server.media.MediaRoute2ProviderServiceProxy$$ExternalSyntheticLambda2
                                                @Override // java.util.function.BiConsumer
                                                public final void accept(Object obj, Object obj2) {
                                                    int i62 = i6;
                                                    MediaRoute2ProviderServiceProxy
                                                            mediaRoute2ProviderServiceProxy4 =
                                                                    (MediaRoute2ProviderServiceProxy)
                                                                            obj;
                                                    RoutingSessionInfo routingSessionInfo52 =
                                                            (RoutingSessionInfo) obj2;
                                                    MediaRouter2ServiceImpl.UserHandler
                                                            userHandler2 =
                                                                    (MediaRouter2ServiceImpl
                                                                                    .UserHandler)
                                                                            callback3;
                                                    userHandler2.getClass();
                                                    switch (i62) {
                                                        case 0:
                                                            userHandler2.sendMessage(
                                                                    PooledLambda.obtainMessage(
                                                                            new MediaRouter2ServiceImpl$$ExternalSyntheticLambda3(
                                                                                    5),
                                                                            userHandler2,
                                                                            mediaRoute2ProviderServiceProxy4,
                                                                            routingSessionInfo52));
                                                            break;
                                                        default:
                                                            userHandler2.sendMessage(
                                                                    PooledLambda.obtainMessage(
                                                                            new MediaRouter2ServiceImpl$$ExternalSyntheticLambda3(
                                                                                    4),
                                                                            userHandler2,
                                                                            mediaRoute2ProviderServiceProxy4,
                                                                            routingSessionInfo52));
                                                            break;
                                                    }
                                                }
                                            },
                                            mediaRoute2ProviderServiceProxy3,
                                            routingSessionInfo5));
                        }
                    } finally {
                    }
                }
                return;
            case 3:
                MediaRoute2ProviderServiceProxy.m650$$Nest$monServiceConnectedInternal(
                        MediaRoute2ProviderServiceProxy.this, (IBinder) this.f$1);
                return;
            default:
                MediaRoute2ProviderServiceProxy.m649$$Nest$monBindingDiedInternal(
                        MediaRoute2ProviderServiceProxy.this, (ComponentName) this.f$1);
                return;
        }
    }
}
