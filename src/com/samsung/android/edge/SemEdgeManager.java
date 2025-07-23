package com.samsung.android.edge;

import android.app.INotificationManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PowerManager;
import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.provider.DocumentsContract;
import android.provider.Settings;
import android.util.Log;
import com.samsung.android.edge.IEdgeLightingCallback;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.util.SemLog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes6.dex */
public class SemEdgeManager {
    public static final int DISABLE_EDGE_LIGHTING = 1;
    public static final int DISABLE_NONE_EDGE_LIGHTING = 0;
    private static final String EDGE_LIGHTING = "edge_lighting";
    private static final int EDGE_LIGHTING_ALWAYS = 0;
    private static final String EDGE_LIGHTING_EDGE_NOTIFICATIONS = "edge_lighting_edge_notifications";
    public static final boolean EDGE_LIGHTING_ENABLED;
    private static final int EDGE_LIGHTING_SCREEN_OFF = 2;
    private static final int EDGE_LIGHTING_SCREEN_ON = 1;
    private static final String EDGE_LIGHTING_SHOW_CONDITION = "edge_lighting_show_condition";
    public static final int EDGE_LIGHTING_STATE_NONE = 0;
    public static final int EDGE_LIGHTING_STATE_RUNNING = 1;
    public static final boolean SUPPORT_EDGE_LIGHTING = true;
    private static final String TAG = "SemEdgeManager";
    private Context mContext;
    private final String mPackageName;
    private INotificationManager mService;
    private Object mEdgeLightingDelegatesLock = new Object();
    private final CopyOnWriteArrayList<EdgeLightingCallbackDelegate> mEdgeLightingCallbackDelegates = new CopyOnWriteArrayList<>();
    private final Binder mToken = new Binder();

    static {
        String string = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_COMMON_CONFIG_EDGE");
        EDGE_LIGHTING_ENABLED = string != null && string.contains("edgelighting_v2");
    }

    public SemEdgeManager(Context context, INotificationManager iNotificationManager) {
        this.mContext = context;
        this.mPackageName = context.getOpPackageName();
        this.mService = iNotificationManager;
    }

    private INotificationManager getService() {
        if (this.mService == null) {
            this.mService = INotificationManager.Stub.asInterface(ServiceManager.getService("notification"));
        }
        return this.mService;
    }

    public void updateEdgeLightingPolicy(EdgeLightingPolicy edgeLightingPolicy) {
        if (getService() == null) {
            return;
        }
        if (edgeLightingPolicy == null) {
            SemLog.w(TAG, "updateEdgeLightingPolicy : policy is null");
            return;
        }
        try {
            this.mService.updateEdgeLightingPolicy(this.mPackageName, edgeLightingPolicy);
        } catch (RemoteException e) {
            SemLog.e(TAG, "updateEdgeLightingPolicy : RemoteException : ", e);
        }
    }

    public void bindEdgeLightingService(OnEdgeLightingCallback onEdgeLightingCallback, int i) {
        EdgeLightingCallbackDelegate edgeLightingCallbackDelegate;
        if (getService() == null) {
            return;
        }
        if (onEdgeLightingCallback == null) {
            SemLog.w(TAG, "bindEdgeLightingService : callback is null");
            return;
        }
        synchronized (this.mEdgeLightingDelegatesLock) {
            Iterator<EdgeLightingCallbackDelegate> it = this.mEdgeLightingCallbackDelegates.iterator();
            while (true) {
                if (!it.hasNext()) {
                    edgeLightingCallbackDelegate = null;
                    break;
                }
                edgeLightingCallbackDelegate = it.next();
                if (edgeLightingCallbackDelegate.getCallback() != null && edgeLightingCallbackDelegate.getCallback().equals(onEdgeLightingCallback)) {
                    break;
                }
            }
            if (edgeLightingCallbackDelegate == null) {
                edgeLightingCallbackDelegate = new EdgeLightingCallbackDelegate(this, onEdgeLightingCallback);
                this.mEdgeLightingCallbackDelegates.add(edgeLightingCallbackDelegate);
            }
            ComponentName componentName = new ComponentName(this.mContext.getPackageName(), getClass().getCanonicalName());
            if (edgeLightingCallbackDelegate != null) {
                try {
                    this.mService.bindEdgeLightingService(edgeLightingCallbackDelegate, i, componentName);
                } catch (RemoteException e) {
                    SemLog.e(TAG, "bindEdgeLightingService : RemoteException : ", e);
                }
            }
        }
    }

    public void unbindEdgeLightingService(OnEdgeLightingCallback onEdgeLightingCallback) {
        EdgeLightingCallbackDelegate edgeLightingCallbackDelegate;
        if (getService() == null) {
            return;
        }
        if (onEdgeLightingCallback == null) {
            SemLog.w(TAG, "unbindEdgeLightingService : callback is null");
            return;
        }
        synchronized (this.mEdgeLightingDelegatesLock) {
            Iterator<EdgeLightingCallbackDelegate> it = this.mEdgeLightingCallbackDelegates.iterator();
            while (true) {
                if (!it.hasNext()) {
                    edgeLightingCallbackDelegate = null;
                    break;
                }
                edgeLightingCallbackDelegate = it.next();
                if (edgeLightingCallbackDelegate.getCallback() != null && edgeLightingCallbackDelegate.getCallback().equals(onEdgeLightingCallback)) {
                    break;
                }
            }
            if (edgeLightingCallbackDelegate == null) {
                SemLog.w(TAG, "unbindEdgeLightingService : cannot find the callback");
                return;
            }
            try {
                this.mService.unbindEdgeLightingService(edgeLightingCallbackDelegate, this.mPackageName);
                this.mEdgeLightingCallbackDelegates.remove(edgeLightingCallbackDelegate);
            } catch (RemoteException e) {
                SemLog.e(TAG, "unbindEdgeLightingService : RemoteException : ", e);
            }
        }
    }

    public void registerEdgeLightingListener(OnEdgeLightingListener onEdgeLightingListener) {
        EdgeLightingCallbackDelegate edgeLightingCallbackDelegate;
        if (getService() == null) {
            return;
        }
        if (onEdgeLightingListener == null) {
            SemLog.w(TAG, "registerEdgeLightingListener : listener is null");
            return;
        }
        synchronized (this.mEdgeLightingDelegatesLock) {
            Iterator<EdgeLightingCallbackDelegate> it = this.mEdgeLightingCallbackDelegates.iterator();
            while (true) {
                if (!it.hasNext()) {
                    edgeLightingCallbackDelegate = null;
                    break;
                }
                edgeLightingCallbackDelegate = it.next();
                if (edgeLightingCallbackDelegate.getListener() != null && edgeLightingCallbackDelegate.getListener().equals(onEdgeLightingListener)) {
                    break;
                }
            }
            if (edgeLightingCallbackDelegate == null) {
                edgeLightingCallbackDelegate = new EdgeLightingCallbackDelegate(this, onEdgeLightingListener);
                this.mEdgeLightingCallbackDelegates.add(edgeLightingCallbackDelegate);
            }
            ComponentName componentName = new ComponentName(this.mContext.getPackageName(), getClass().getCanonicalName());
            if (edgeLightingCallbackDelegate != null) {
                try {
                    this.mService.registerEdgeLightingListener(edgeLightingCallbackDelegate, componentName);
                } catch (RemoteException e) {
                    SemLog.e(TAG, "registerEdgeLightingListener : RemoteException : ", e);
                }
            }
        }
    }

    public void unregisterEdgeLightingListener(OnEdgeLightingListener onEdgeLightingListener) {
        EdgeLightingCallbackDelegate edgeLightingCallbackDelegate;
        if (getService() == null) {
            return;
        }
        if (onEdgeLightingListener == null) {
            SemLog.w(TAG, "unregisterEdgeLightingListener : listener is null");
            return;
        }
        synchronized (this.mEdgeLightingDelegatesLock) {
            Iterator<EdgeLightingCallbackDelegate> it = this.mEdgeLightingCallbackDelegates.iterator();
            while (true) {
                if (!it.hasNext()) {
                    edgeLightingCallbackDelegate = null;
                    break;
                }
                edgeLightingCallbackDelegate = it.next();
                if (edgeLightingCallbackDelegate.getListener() != null && edgeLightingCallbackDelegate.getListener().equals(onEdgeLightingListener)) {
                    break;
                }
            }
            if (edgeLightingCallbackDelegate == null) {
                SemLog.w(TAG, "unregisterEdgeLightingListener : cannot find the listener");
                return;
            }
            try {
                this.mService.unregisterEdgeLightingListener(edgeLightingCallbackDelegate, this.mPackageName);
                this.mEdgeLightingCallbackDelegates.remove(edgeLightingCallbackDelegate);
            } catch (RemoteException e) {
                SemLog.e(TAG, "unbindEdgeLightingService : RemoteException : ", e);
            }
        }
    }

    public void updateEdgeLightingPackageList(ArrayList<String> arrayList) {
        if (getService() == null) {
            return;
        }
        if (arrayList == null) {
            SemLog.w(TAG, "updateEdgeLightingPackageList : list is null");
            return;
        }
        try {
            this.mService.updateEdgeLightingPackageList(this.mPackageName, arrayList);
        } catch (RemoteException e) {
            throw new RuntimeException("EdgeService dead?", e);
        }
    }

    public void startEdgeLighting(SemEdgeLightingInfo semEdgeLightingInfo) {
        if (getService() == null) {
            return;
        }
        if (semEdgeLightingInfo == null) {
            throw new IllegalArgumentException("info is null.");
        }
        try {
            this.mService.startEdgeLighting(this.mPackageName, semEdgeLightingInfo, this.mToken);
        } catch (RemoteException e) {
            throw new RuntimeException("EdgeService dead?", e);
        }
    }

    public void stopEdgeLighting() {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.stopEdgeLighting(this.mPackageName, this.mToken);
        } catch (RemoteException e) {
            throw new RuntimeException("EdgeService dead?", e);
        }
    }

    public int getEdgeLightingState() {
        if (getService() == null) {
            return 0;
        }
        try {
            return this.mService.getEdgeLightingState();
        } catch (RemoteException e) {
            throw new RuntimeException("EdgeService dead?", e);
        }
    }

    public void cancelNotification(String str, String str2, int i, int i2, String str3) {
        if (getService() == null) {
            Log.e(TAG, " cancelNotification : service is null");
            return;
        }
        try {
            this.mService.cancelNotificationByEdge(str, str2, i, i2, str3);
        } catch (RemoteException e) {
            throw new RuntimeException("EdgeService dead?", e);
        }
    }

    public void cancelNotificationByGroupKey(String str, String str2, int i, int i2, String str3, String str4) {
        if (getService() == null) {
            Log.e(TAG, " cancelNotificationByNotification : service is null");
            return;
        }
        try {
            this.mService.cancelNotificationByGroupKey(str, str2, i, i2, str3, str4);
        } catch (RemoteException e) {
            throw new RuntimeException("EdgeService dead?", e);
        }
    }

    public boolean isEdgeLightingNotificationAllowed() {
        if (getService() == null) {
            return false;
        }
        ContentResolver contentResolver = this.mContext.getContentResolver();
        if (!isEdgeLightingEnabled(contentResolver)) {
            return false;
        }
        int callingUid = Binder.getCallingUid();
        if (callingUid != 1000 && callingUid == Process.myUid() && !isEdgeLightingEnabledByScreen(contentResolver, ((PowerManager) this.mContext.getSystemService("power")).isInteractive())) {
            return false;
        }
        try {
            return this.mService.isEdgeLightingNotificationAllowed(this.mPackageName);
        } catch (RemoteException e) {
            throw new RuntimeException("EdgeService dead?", e);
        }
    }

    public void disable(int i) {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.disable(i, this.mPackageName, this.mToken);
        } catch (RemoteException e) {
            throw new RuntimeException("EdgeService dead?", e);
        }
    }

    public void disableEdgeLightingNotification(boolean z) {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.disableEdgeLightingNotification(this.mPackageName, z);
        } catch (RemoteException e) {
            throw new RuntimeException("EdgeService dead?", e);
        }
    }

    public boolean isPackageEnabled(String str, int i) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.isPackageEnabled(str, i);
        } catch (RemoteException e) {
            throw new RuntimeException("EdgeService dead?", e);
        }
    }

    private boolean isEdgeLightingEnabled(ContentResolver contentResolver) {
        return Settings.System.getInt(contentResolver, EDGE_LIGHTING, 1) == 1;
    }

    private boolean isEdgeLightingEnabledByScreen(ContentResolver contentResolver, boolean z) {
        int i = Settings.System.getInt(contentResolver, EDGE_LIGHTING_SHOW_CONDITION, 0);
        if (i != 0) {
            if (!((i != 1) ^ z)) {
                return false;
            }
        }
        return true;
    }

    private class EdgeLightingCallbackDelegate extends IEdgeLightingCallback.Stub {
        private static final int MSG_EDGE_LIGHTING_START = 0;
        private static final int MSG_EDGE_LIGHTING_STARTED = 2;
        private static final int MSG_EDGE_LIGHTING_STOP = 1;
        private static final int MSG_EDGE_LIGHTING_STOPPED = 3;
        private static final int MSG_SCREEN_CHANGED = 4;
        private final OnEdgeLightingCallback mCallback;
        private Handler mHandler;
        private final OnEdgeLightingListener mListener;

        EdgeLightingCallbackDelegate(final SemEdgeManager semEdgeManager, OnEdgeLightingCallback onEdgeLightingCallback) {
            this.mCallback = onEdgeLightingCallback;
            this.mListener = null;
            this.mHandler = new Handler(semEdgeManager.mContext.getMainLooper()) { // from class: com.samsung.android.edge.SemEdgeManager.EdgeLightingCallbackDelegate.1
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    if (EdgeLightingCallbackDelegate.this.mCallback != null) {
                        int i = message.what;
                        if (i == 0) {
                            Bundle bundle = (Bundle) message.obj;
                            EdgeLightingCallbackDelegate.this.mCallback.onStartEdgeLighting(bundle.getString("packageName"), (SemEdgeLightingInfo) bundle.getParcelable(DocumentsContract.EXTRA_INFO), message.arg1);
                            return;
                        }
                        if (i == 1) {
                            EdgeLightingCallbackDelegate.this.mCallback.onStopEdgeLighting((String) message.obj, message.arg1);
                        } else {
                            if (i != 4) {
                                return;
                            }
                            EdgeLightingCallbackDelegate.this.mCallback.onScreenChanged(message.arg1 == 1);
                        }
                    }
                }
            };
        }

        EdgeLightingCallbackDelegate(final SemEdgeManager semEdgeManager, OnEdgeLightingListener onEdgeLightingListener) {
            this.mCallback = null;
            this.mListener = onEdgeLightingListener;
            this.mHandler = new Handler(semEdgeManager.mContext.getMainLooper()) { // from class: com.samsung.android.edge.SemEdgeManager.EdgeLightingCallbackDelegate.2
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    if (EdgeLightingCallbackDelegate.this.mListener != null) {
                        int i = message.what;
                        if (i == 2) {
                            EdgeLightingCallbackDelegate.this.mListener.onEdgeLightingStarted();
                        } else {
                            if (i != 3) {
                                return;
                            }
                            EdgeLightingCallbackDelegate.this.mListener.onEdgeLightingStopped();
                        }
                    }
                }
            };
        }

        OnEdgeLightingCallback getCallback() {
            return this.mCallback;
        }

        OnEdgeLightingListener getListener() {
            return this.mListener;
        }

        @Override // com.samsung.android.edge.IEdgeLightingCallback
        public void onStartEdgeLighting(String str, SemEdgeLightingInfo semEdgeLightingInfo, int i) throws RemoteException {
            Message obtain = Message.obtain(this.mHandler, 0, i, 0);
            Bundle bundle = new Bundle();
            bundle.putString("packageName", str);
            bundle.putParcelable(DocumentsContract.EXTRA_INFO, semEdgeLightingInfo);
            obtain.obj = bundle;
            obtain.sendToTarget();
        }

        @Override // com.samsung.android.edge.IEdgeLightingCallback
        public void onStopEdgeLighting(String str, int i) throws RemoteException {
            Message obtain = Message.obtain(this.mHandler, 1, i, 0);
            obtain.obj = str;
            obtain.sendToTarget();
        }

        @Override // com.samsung.android.edge.IEdgeLightingCallback
        public void onEdgeLightingStarted() throws RemoteException {
            this.mHandler.sendEmptyMessage(2);
        }

        @Override // com.samsung.android.edge.IEdgeLightingCallback
        public void onEdgeLightingStopped() throws RemoteException {
            this.mHandler.sendEmptyMessage(3);
        }

        @Override // com.samsung.android.edge.IEdgeLightingCallback
        public void onScreenChanged(boolean z) throws RemoteException {
            Message.obtain(this.mHandler, 4, z ? 1 : 0, 0).sendToTarget();
        }
    }
}
