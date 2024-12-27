package com.android.server.notification.edgelighting;

import android.content.ComponentName;
import android.hardware.broadcastradio.V2_0.AmFmBandRange$$ExternalSyntheticOutline0;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Slog;

import com.samsung.android.edge.IEdgeLightingCallback;

import java.util.ArrayList;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class EdgeLightingListenerManager {
    public static final boolean DEBUG = Debug.semIsProductDev();
    public final ArrayList mListeners = new ArrayList();
    public final Looper mLooper = new Handler().getLooper();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class EdgeLightingListener implements IBinder.DeathRecipient {
        public final ComponentName component;
        public boolean isEdgeLighting;
        public final AnonymousClass1 mHandler;
        public final int pid;
        public final IBinder token;
        public final int uid;

        /* JADX WARN: Type inference failed for: r0v0, types: [com.android.server.notification.edgelighting.EdgeLightingListenerManager$EdgeLightingListener$1] */
        public EdgeLightingListener(IBinder iBinder, ComponentName componentName, int i, int i2) {
            this.mHandler =
                    new Handler(
                            EdgeLightingListenerManager.this
                                    .mLooper) { // from class:
                                                // com.android.server.notification.edgelighting.EdgeLightingListenerManager.EdgeLightingListener.1
                        @Override // android.os.Handler
                        public final void handleMessage(Message message) {
                            super.handleMessage(message);
                            int i3 = message.what;
                            EdgeLightingListener edgeLightingListener = EdgeLightingListener.this;
                            if (i3 == 1) {
                                edgeLightingListener.getClass();
                                try {
                                    IEdgeLightingCallback asInterface =
                                            IEdgeLightingCallback.Stub.asInterface(
                                                    edgeLightingListener.token);
                                    if (asInterface != null) {
                                        asInterface.onEdgeLightingStarted();
                                        return;
                                    }
                                    return;
                                } catch (RemoteException e) {
                                    boolean z = EdgeLightingListenerManager.DEBUG;
                                    Slog.e(
                                            "EdgeLightingListenerManager",
                                            "_onEdgeLightingStarted : RemoteException : ",
                                            e);
                                    return;
                                }
                            }
                            if (i3 != 2) {
                                return;
                            }
                            edgeLightingListener.getClass();
                            try {
                                IEdgeLightingCallback asInterface2 =
                                        IEdgeLightingCallback.Stub.asInterface(
                                                edgeLightingListener.token);
                                if (asInterface2 != null) {
                                    asInterface2.onEdgeLightingStopped();
                                }
                            } catch (RemoteException e2) {
                                boolean z2 = EdgeLightingListenerManager.DEBUG;
                                Slog.e(
                                        "EdgeLightingListenerManager",
                                        "_onEdgeLightingStopped : RemoteException : ",
                                        e2);
                            }
                        }
                    };
            this.token = iBinder;
            this.component = componentName;
            this.pid = i;
            this.uid = i2;
            if (iBinder != null) {
                try {
                    iBinder.linkToDeath(this, 0);
                } catch (RemoteException unused) {
                    boolean z = EdgeLightingListenerManager.DEBUG;
                    Slog.e(
                            "EdgeLightingListenerManager",
                            "EdgeLightingListener : linkToDeath error");
                }
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            boolean z = EdgeLightingListenerManager.DEBUG;
            Slog.v(
                    "EdgeLightingListenerManager",
                    "binderDied : binder = " + this.component.toShortString());
            EdgeLightingHistory.getInstance()
                    .updateListenerHistory(this.component.getPackageName(), "binderDied.");
            removeCallbacksAndMessages(null);
            synchronized (EdgeLightingListenerManager.this.mListeners) {
                EdgeLightingListenerManager.this.mListeners.remove(this);
            }
            this.token.unlinkToDeath(this, 0);
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("  [Listener: component:(");
            sb.append(this.component.toString());
            sb.append(") pid:(");
            sb.append(this.pid);
            sb.append(") uid:(");
            return AmFmBandRange$$ExternalSyntheticOutline0.m(this.uid, sb, ")]");
        }
    }

    public final void startEdgeLighting() {
        synchronized (this.mListeners) {
            try {
                if (this.mListeners.size() < 1) {
                    return;
                }
                Iterator it = this.mListeners.iterator();
                while (it.hasNext()) {
                    EdgeLightingListener edgeLightingListener = (EdgeLightingListener) it.next();
                    if (edgeLightingListener != null && !edgeLightingListener.isEdgeLighting) {
                        edgeLightingListener.isEdgeLighting = true;
                        if (EdgeLightingHistory.IS_DEV_DEBUG || DEBUG) {
                            Slog.d("EdgeLightingListenerManager", "onEdgeLightingStarted");
                        }
                        if (edgeLightingListener.token == null) {
                            Slog.w(
                                    "EdgeLightingListenerManager",
                                    "onEdgeLightingStarted : token is null");
                        } else {
                            edgeLightingListener.mHandler.sendEmptyMessage(1);
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void stopEdgeLighting() {
        synchronized (this.mListeners) {
            try {
                if (this.mListeners.size() < 1) {
                    return;
                }
                Iterator it = this.mListeners.iterator();
                while (it.hasNext()) {
                    EdgeLightingListener edgeLightingListener = (EdgeLightingListener) it.next();
                    if (edgeLightingListener != null && edgeLightingListener.isEdgeLighting) {
                        edgeLightingListener.isEdgeLighting = false;
                        if (EdgeLightingHistory.IS_DEV_DEBUG || DEBUG) {
                            Slog.d("EdgeLightingListenerManager", "onEdgeLightingStopped");
                        }
                        if (edgeLightingListener.token == null) {
                            Slog.w(
                                    "EdgeLightingListenerManager",
                                    "onEdgeLightingStopped : token is null");
                        } else {
                            edgeLightingListener.mHandler.sendEmptyMessage(2);
                        }
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:12:0x003d A[Catch: all -> 0x000f, TryCatch #0 {all -> 0x000f, blocks: (B:4:0x0006, B:6:0x000a, B:9:0x0030, B:10:0x0037, B:12:0x003d, B:15:0x0045, B:25:0x0051, B:26:0x0059, B:29:0x005b, B:31:0x0063, B:32:0x0071, B:33:0x0075, B:35:0x0011), top: B:3:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:25:0x0051 A[Catch: all -> 0x000f, TryCatch #0 {all -> 0x000f, blocks: (B:4:0x0006, B:6:0x000a, B:9:0x0030, B:10:0x0037, B:12:0x003d, B:15:0x0045, B:25:0x0051, B:26:0x0059, B:29:0x005b, B:31:0x0063, B:32:0x0071, B:33:0x0075, B:35:0x0011), top: B:3:0x0006 }] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x005b A[Catch: all -> 0x000f, TryCatch #0 {all -> 0x000f, blocks: (B:4:0x0006, B:6:0x000a, B:9:0x0030, B:10:0x0037, B:12:0x003d, B:15:0x0045, B:25:0x0051, B:26:0x0059, B:29:0x005b, B:31:0x0063, B:32:0x0071, B:33:0x0075, B:35:0x0011), top: B:3:0x0006 }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void unregister(android.os.IBinder r6, java.lang.String r7) {
        /*
            r5 = this;
            java.lang.String r0 = "unregister : pkg = "
            java.util.ArrayList r1 = r5.mListeners
            monitor-enter(r1)
            boolean r2 = com.android.server.notification.edgelighting.EdgeLightingHistory.IS_DEV_DEBUG     // Catch: java.lang.Throwable -> Lf
            if (r2 != 0) goto L11
            boolean r2 = com.android.server.notification.edgelighting.EdgeLightingListenerManager.DEBUG     // Catch: java.lang.Throwable -> Lf
            if (r2 == 0) goto L30
            goto L11
        Lf:
            r5 = move-exception
            goto L77
        L11:
            java.lang.String r2 = "EdgeLightingListenerManager"
            java.lang.StringBuilder r3 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> Lf
            r3.<init>(r0)     // Catch: java.lang.Throwable -> Lf
            r3.append(r7)     // Catch: java.lang.Throwable -> Lf
            java.lang.String r0 = ", mListeners = "
            r3.append(r0)     // Catch: java.lang.Throwable -> Lf
            java.util.ArrayList r0 = r5.mListeners     // Catch: java.lang.Throwable -> Lf
            int r0 = r0.size()     // Catch: java.lang.Throwable -> Lf
            r3.append(r0)     // Catch: java.lang.Throwable -> Lf
            java.lang.String r0 = r3.toString()     // Catch: java.lang.Throwable -> Lf
            android.util.Slog.d(r2, r0)     // Catch: java.lang.Throwable -> Lf
        L30:
            java.util.ArrayList r0 = r5.mListeners     // Catch: java.lang.Throwable -> Lf
            java.util.Iterator r0 = r0.iterator()     // Catch: java.lang.Throwable -> Lf
            r2 = 0
        L37:
            boolean r3 = r0.hasNext()     // Catch: java.lang.Throwable -> Lf
            if (r3 == 0) goto L4f
            java.lang.Object r3 = r0.next()     // Catch: java.lang.Throwable -> Lf
            com.android.server.notification.edgelighting.EdgeLightingListenerManager$EdgeLightingListener r3 = (com.android.server.notification.edgelighting.EdgeLightingListenerManager.EdgeLightingListener) r3     // Catch: java.lang.Throwable -> Lf
            if (r3 == 0) goto L37
            android.os.IBinder r4 = r3.token     // Catch: java.lang.Throwable -> Lf
            boolean r4 = r6.equals(r4)     // Catch: java.lang.Throwable -> Lf
            if (r4 == 0) goto L37
            r2 = r3
            goto L37
        L4f:
            if (r2 != 0) goto L5b
            java.lang.String r5 = "EdgeLightingListenerManager"
            java.lang.String r6 = "unregister : cannot find the matched host"
            android.util.Slog.e(r5, r6)     // Catch: java.lang.Throwable -> Lf
            monitor-exit(r1)     // Catch: java.lang.Throwable -> Lf
            return
        L5b:
            java.util.ArrayList r0 = r5.mListeners     // Catch: java.lang.Throwable -> Lf
            boolean r0 = r0.isEmpty()     // Catch: java.lang.Throwable -> Lf
            if (r0 != 0) goto L71
            java.util.ArrayList r5 = r5.mListeners     // Catch: java.lang.Throwable -> Lf
            r5.remove(r2)     // Catch: java.lang.Throwable -> Lf
            com.android.server.notification.edgelighting.EdgeLightingHistory r5 = com.android.server.notification.edgelighting.EdgeLightingHistory.getInstance()     // Catch: java.lang.Throwable -> Lf
            java.lang.String r0 = "UR listener"
            r5.updateListenerHistory(r7, r0)     // Catch: java.lang.Throwable -> Lf
        L71:
            r5 = 0
            r6.unlinkToDeath(r2, r5)     // Catch: java.lang.Throwable -> Lf
            monitor-exit(r1)     // Catch: java.lang.Throwable -> Lf
            return
        L77:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> Lf
            throw r5
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.notification.edgelighting.EdgeLightingListenerManager.unregister(android.os.IBinder,"
                    + " java.lang.String):void");
    }
}
