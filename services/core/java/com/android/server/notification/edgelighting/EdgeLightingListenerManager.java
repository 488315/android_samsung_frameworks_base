package com.android.server.notification.edgelighting;

import android.content.ComponentName;
import android.content.Context;
import android.os.Binder;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.Slog;
import com.samsung.android.edge.IEdgeLightingCallback;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes2.dex */
public class EdgeLightingListenerManager {
    public static final boolean DEBUG = Debug.semIsProductDev();
    public static final String TAG = "EdgeLightingListenerManager";
    public final ArrayList mListeners = new ArrayList();
    public Looper mLooper = new Handler().getLooper();

    public EdgeLightingListenerManager(Context context) {
    }

    public void register(IBinder iBinder, ComponentName componentName) {
        ArrayList arrayList = new ArrayList();
        synchronized (this.mListeners) {
            if (EdgeLightingHistory.IS_DEV_DEBUG || DEBUG) {
                Slog.d(TAG, "register : pkg = " + componentName.getPackageName() + ", mListeners = " + this.mListeners.size());
            }
            Iterator it = this.mListeners.iterator();
            while (it.hasNext()) {
                EdgeLightingListener edgeLightingListener = (EdgeLightingListener) it.next();
                if (edgeLightingListener != null) {
                    if (iBinder.equals(edgeLightingListener.token)) {
                        Slog.w(TAG, "register : already registered");
                        return;
                    }
                    if (componentName.equals(edgeLightingListener.component)) {
                        if (EdgeLightingHistory.IS_DEV_DEBUG || DEBUG) {
                            Slog.d(TAG, "register : pkg = " + componentName.getPackageName() + " unregister because of same component");
                        }
                        arrayList.add(edgeLightingListener);
                    }
                }
            }
            this.mListeners.add(new EdgeLightingListener(iBinder, componentName, Binder.getCallingPid(), Binder.getCallingUid()));
            EdgeLightingHistory.getInstance().updateListenerHistory(componentName.getPackageName(), "R listener.");
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                EdgeLightingListener edgeLightingListener2 = (EdgeLightingListener) it2.next();
                if (edgeLightingListener2.token != null && edgeLightingListener2.component != null) {
                    unregister(edgeLightingListener2.token, edgeLightingListener2.component.getPackageName());
                    EdgeLightingHistory.getInstance().updateListenerHistory(edgeLightingListener2.component.getPackageName(), "UR listener by same component");
                }
            }
        }
    }

    public void unregister(IBinder iBinder, String str) {
        synchronized (this.mListeners) {
            if (EdgeLightingHistory.IS_DEV_DEBUG || DEBUG) {
                Slog.d(TAG, "unregister : pkg = " + str + ", mListeners = " + this.mListeners.size());
            }
            Iterator it = this.mListeners.iterator();
            EdgeLightingListener edgeLightingListener = null;
            while (it.hasNext()) {
                EdgeLightingListener edgeLightingListener2 = (EdgeLightingListener) it.next();
                if (edgeLightingListener2 != null && iBinder.equals(edgeLightingListener2.token)) {
                    edgeLightingListener = edgeLightingListener2;
                }
            }
            if (edgeLightingListener == null) {
                Slog.e(TAG, "unregister : cannot find the matched host");
                return;
            }
            if (!this.mListeners.isEmpty()) {
                this.mListeners.remove(edgeLightingListener);
                EdgeLightingHistory.getInstance().updateListenerHistory(str, "UR listener");
            }
            iBinder.unlinkToDeath(edgeLightingListener, 0);
        }
    }

    public void startEdgeLighting() {
        synchronized (this.mListeners) {
            if (this.mListeners.size() < 1) {
                return;
            }
            Iterator it = this.mListeners.iterator();
            while (it.hasNext()) {
                EdgeLightingListener edgeLightingListener = (EdgeLightingListener) it.next();
                if (edgeLightingListener != null && !edgeLightingListener.isEdgeLighting) {
                    edgeLightingListener.isEdgeLighting = true;
                    edgeLightingListener.onEdgeLightingStarted();
                }
            }
        }
    }

    public void stopEdgeLighting() {
        synchronized (this.mListeners) {
            if (this.mListeners.size() < 1) {
                return;
            }
            Iterator it = this.mListeners.iterator();
            while (it.hasNext()) {
                EdgeLightingListener edgeLightingListener = (EdgeLightingListener) it.next();
                if (edgeLightingListener != null && edgeLightingListener.isEdgeLighting) {
                    edgeLightingListener.isEdgeLighting = false;
                    edgeLightingListener.onEdgeLightingStopped();
                }
            }
        }
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        printWriter.println("-ListenerManager start");
        synchronized (this.mListeners) {
            Iterator it = this.mListeners.iterator();
            while (it.hasNext()) {
                printWriter.println((EdgeLightingListener) it.next());
            }
        }
        printWriter.println("");
    }

    public class EdgeLightingListener implements IBinder.DeathRecipient {
        public final ComponentName component;
        public boolean isEdgeLighting;
        public Handler mHandler;
        public final int pid;
        public final IBinder token;
        public final int uid;

        public EdgeLightingListener(IBinder iBinder, ComponentName componentName, int i, int i2) {
            this.mHandler = new Handler(EdgeLightingListenerManager.this.mLooper) { // from class: com.android.server.notification.edgelighting.EdgeLightingListenerManager.EdgeLightingListener.1
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    super.handleMessage(message);
                    int i3 = message.what;
                    if (i3 == 1) {
                        EdgeLightingListener.this._onEdgeLightingStarted();
                    } else {
                        if (i3 != 2) {
                            return;
                        }
                        EdgeLightingListener.this._onEdgeLightingStopped();
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
                    Slog.e(EdgeLightingListenerManager.TAG, "EdgeLightingListener : linkToDeath error");
                }
            }
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            Slog.v(EdgeLightingListenerManager.TAG, "binderDied : binder = " + this.component.toShortString());
            EdgeLightingHistory.getInstance().updateListenerHistory(this.component.getPackageName(), "binderDied.");
            this.mHandler.removeCallbacksAndMessages(null);
            synchronized (EdgeLightingListenerManager.this.mListeners) {
                EdgeLightingListenerManager.this.mListeners.remove(this);
            }
            this.token.unlinkToDeath(this, 0);
        }

        public void onEdgeLightingStarted() {
            if (EdgeLightingHistory.IS_DEV_DEBUG || EdgeLightingListenerManager.DEBUG) {
                Slog.d(EdgeLightingListenerManager.TAG, "onEdgeLightingStarted");
            }
            if (this.token == null) {
                Slog.w(EdgeLightingListenerManager.TAG, "onEdgeLightingStarted : token is null");
            } else {
                this.mHandler.sendEmptyMessage(1);
            }
        }

        public void onEdgeLightingStopped() {
            if (EdgeLightingHistory.IS_DEV_DEBUG || EdgeLightingListenerManager.DEBUG) {
                Slog.d(EdgeLightingListenerManager.TAG, "onEdgeLightingStopped");
            }
            if (this.token == null) {
                Slog.w(EdgeLightingListenerManager.TAG, "onEdgeLightingStopped : token is null");
            } else {
                this.mHandler.sendEmptyMessage(2);
            }
        }

        public final void _onEdgeLightingStarted() {
            try {
                IEdgeLightingCallback asInterface = IEdgeLightingCallback.Stub.asInterface(this.token);
                if (asInterface != null) {
                    asInterface.onEdgeLightingStarted();
                }
            } catch (RemoteException e) {
                Slog.e(EdgeLightingListenerManager.TAG, "_onEdgeLightingStarted : RemoteException : ", e);
            }
        }

        public final void _onEdgeLightingStopped() {
            try {
                IEdgeLightingCallback asInterface = IEdgeLightingCallback.Stub.asInterface(this.token);
                if (asInterface != null) {
                    asInterface.onEdgeLightingStopped();
                }
            } catch (RemoteException e) {
                Slog.e(EdgeLightingListenerManager.TAG, "_onEdgeLightingStopped : RemoteException : ", e);
            }
        }

        public String toString() {
            return "  [Listener: component:(" + this.component.toString() + ") pid:(" + this.pid + ") uid:(" + this.uid + ")]";
        }
    }
}
