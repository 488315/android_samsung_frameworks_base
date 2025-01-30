package com.samsung.android.edge;

import android.app.INotificationManager;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.p009os.Binder;
import android.p009os.Bundle;
import android.p009os.Handler;
import android.p009os.IBinder;
import android.p009os.Message;
import android.p009os.PowerManager;
import android.p009os.Process;
import android.p009os.RemoteException;
import android.p009os.ServiceManager;
import android.provider.DocumentsContract;
import android.provider.Settings;
import android.util.Log;
import com.samsung.android.edge.IEdgeLightingCallback;
import com.samsung.android.feature.SemFloatingFeature;
import com.samsung.android.util.SemLog;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes5.dex */
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
    private static final String TAG = SemEdgeManager.class.getSimpleName();
    private Context mContext;
    private final String mPackageName;
    private INotificationManager mService;
    private Object mEdgeLightingDelegatesLock = new Object();
    private final CopyOnWriteArrayList<EdgeLightingCallbackDelegate> mEdgeLightingCallbackDelegates = new CopyOnWriteArrayList<>();
    private final Binder mToken = new Binder();

    static {
        String feature = SemFloatingFeature.getInstance().getString("SEC_FLOATING_FEATURE_COMMON_CONFIG_EDGE");
        EDGE_LIGHTING_ENABLED = feature != null && feature.contains("edgelighting_v2");
    }

    public SemEdgeManager(Context context, INotificationManager service) {
        this.mContext = context;
        this.mPackageName = context.getOpPackageName();
        this.mService = service;
    }

    private INotificationManager getService() {
        if (this.mService == null) {
            IBinder b = ServiceManager.getService("notification");
            this.mService = INotificationManager.Stub.asInterface(b);
        }
        return this.mService;
    }

    public void updateEdgeLightingPolicy(EdgeLightingPolicy policy) {
        if (getService() == null) {
            return;
        }
        if (policy == null) {
            SemLog.m486w(TAG, "updateEdgeLightingPolicy : policy is null");
            return;
        }
        try {
            this.mService.updateEdgeLightingPolicy(this.mPackageName, policy);
        } catch (RemoteException e) {
            SemLog.m481e(TAG, "updateEdgeLightingPolicy : RemoteException : ", e);
        }
    }

    public void bindEdgeLightingService(OnEdgeLightingCallback callback, int condition) {
        if (getService() == null) {
            return;
        }
        if (callback == null) {
            SemLog.m486w(TAG, "bindEdgeLightingService : callback is null");
            return;
        }
        synchronized (this.mEdgeLightingDelegatesLock) {
            EdgeLightingCallbackDelegate delegate = null;
            Iterator<EdgeLightingCallbackDelegate> i = this.mEdgeLightingCallbackDelegates.iterator();
            while (true) {
                if (!i.hasNext()) {
                    break;
                }
                EdgeLightingCallbackDelegate temp = i.next();
                if (temp.getCallback() != null && temp.getCallback().equals(callback)) {
                    delegate = temp;
                    break;
                }
            }
            if (delegate == null) {
                delegate = new EdgeLightingCallbackDelegate(callback);
                this.mEdgeLightingCallbackDelegates.add(delegate);
            }
            ComponentName cm = new ComponentName(this.mContext.getPackageName(), getClass().getCanonicalName());
            if (delegate != null) {
                try {
                    this.mService.bindEdgeLightingService(delegate, condition, cm);
                } catch (RemoteException e) {
                    SemLog.m481e(TAG, "bindEdgeLightingService : RemoteException : ", e);
                }
            }
        }
    }

    public void unbindEdgeLightingService(OnEdgeLightingCallback callback) {
        if (getService() == null) {
            return;
        }
        if (callback == null) {
            SemLog.m486w(TAG, "unbindEdgeLightingService : callback is null");
            return;
        }
        synchronized (this.mEdgeLightingDelegatesLock) {
            EdgeLightingCallbackDelegate delegate = null;
            Iterator<EdgeLightingCallbackDelegate> i = this.mEdgeLightingCallbackDelegates.iterator();
            while (true) {
                if (!i.hasNext()) {
                    break;
                }
                EdgeLightingCallbackDelegate temp = i.next();
                if (temp.getCallback() != null && temp.getCallback().equals(callback)) {
                    delegate = temp;
                    break;
                }
            }
            if (delegate == null) {
                SemLog.m486w(TAG, "unbindEdgeLightingService : cannot find the callback");
                return;
            }
            try {
                this.mService.unbindEdgeLightingService(delegate, this.mPackageName);
                this.mEdgeLightingCallbackDelegates.remove(delegate);
            } catch (RemoteException e) {
                SemLog.m481e(TAG, "unbindEdgeLightingService : RemoteException : ", e);
            }
        }
    }

    public void registerEdgeLightingListener(OnEdgeLightingListener listener) {
        if (getService() == null) {
            return;
        }
        if (listener == null) {
            SemLog.m486w(TAG, "registerEdgeLightingListener : listener is null");
            return;
        }
        synchronized (this.mEdgeLightingDelegatesLock) {
            EdgeLightingCallbackDelegate delegate = null;
            Iterator<EdgeLightingCallbackDelegate> i = this.mEdgeLightingCallbackDelegates.iterator();
            while (true) {
                if (!i.hasNext()) {
                    break;
                }
                EdgeLightingCallbackDelegate temp = i.next();
                if (temp.getListener() != null && temp.getListener().equals(listener)) {
                    delegate = temp;
                    break;
                }
            }
            if (delegate == null) {
                delegate = new EdgeLightingCallbackDelegate(listener);
                this.mEdgeLightingCallbackDelegates.add(delegate);
            }
            ComponentName cm = new ComponentName(this.mContext.getPackageName(), getClass().getCanonicalName());
            if (delegate != null) {
                try {
                    this.mService.registerEdgeLightingListener(delegate, cm);
                } catch (RemoteException e) {
                    SemLog.m481e(TAG, "registerEdgeLightingListener : RemoteException : ", e);
                }
            }
        }
    }

    public void unregisterEdgeLightingListener(OnEdgeLightingListener listener) {
        if (getService() == null) {
            return;
        }
        if (listener == null) {
            SemLog.m486w(TAG, "unregisterEdgeLightingListener : listener is null");
            return;
        }
        synchronized (this.mEdgeLightingDelegatesLock) {
            EdgeLightingCallbackDelegate delegate = null;
            Iterator<EdgeLightingCallbackDelegate> i = this.mEdgeLightingCallbackDelegates.iterator();
            while (true) {
                if (!i.hasNext()) {
                    break;
                }
                EdgeLightingCallbackDelegate temp = i.next();
                if (temp.getListener() != null && temp.getListener().equals(listener)) {
                    delegate = temp;
                    break;
                }
            }
            if (delegate == null) {
                SemLog.m486w(TAG, "unregisterEdgeLightingListener : cannot find the listener");
                return;
            }
            try {
                this.mService.unregisterEdgeLightingListener(delegate, this.mPackageName);
                this.mEdgeLightingCallbackDelegates.remove(delegate);
            } catch (RemoteException e) {
                SemLog.m481e(TAG, "unbindEdgeLightingService : RemoteException : ", e);
            }
        }
    }

    public void updateEdgeLightingPackageList(ArrayList<String> list) {
        if (getService() == null) {
            return;
        }
        if (list == null) {
            SemLog.m486w(TAG, "updateEdgeLightingPackageList : list is null");
            return;
        }
        try {
            this.mService.updateEdgeLightingPackageList(this.mPackageName, list);
        } catch (RemoteException e) {
            throw new RuntimeException("EdgeService dead?", e);
        }
    }

    public void startEdgeLighting(SemEdgeLightingInfo info) {
        if (getService() == null) {
            return;
        }
        if (info == null) {
            throw new IllegalArgumentException("info is null.");
        }
        try {
            this.mService.startEdgeLighting(this.mPackageName, info, this.mToken);
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

    public void cancelNotification(String pkg, String tag, int id, int userId, String key) {
        if (getService() == null) {
            Log.m96e(TAG, " cancelNotification : service is null");
            return;
        }
        try {
            this.mService.cancelNotificationByEdge(pkg, tag, id, userId, key);
        } catch (RemoteException e) {
            throw new RuntimeException("EdgeService dead?", e);
        }
    }

    public void cancelNotificationByGroupKey(String pkg, String tag, int id, int userId, String key, String groupKey) {
        if (getService() == null) {
            Log.m96e(TAG, " cancelNotificationByNotification : service is null");
            return;
        }
        try {
            this.mService.cancelNotificationByGroupKey(pkg, tag, id, userId, key, groupKey);
        } catch (RemoteException e) {
            throw new RuntimeException("EdgeService dead?", e);
        }
    }

    public boolean isEdgeLightingNotificationAllowed() {
        if (getService() == null) {
            return false;
        }
        ContentResolver cr = this.mContext.getContentResolver();
        if (!isEdgeLightingEnabled(cr)) {
            return false;
        }
        int uid = Binder.getCallingUid();
        if (uid != 1000 && uid == Process.myUid()) {
            PowerManager pm = (PowerManager) this.mContext.getSystemService("power");
            boolean isInteractive = pm.isInteractive();
            if (!isEdgeLightingEnabledByScreen(cr, isInteractive)) {
                return false;
            }
        }
        try {
            return this.mService.isEdgeLightingNotificationAllowed(this.mPackageName);
        } catch (RemoteException e) {
            throw new RuntimeException("EdgeService dead?", e);
        }
    }

    public void disable(int what) {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.disable(what, this.mPackageName, this.mToken);
        } catch (RemoteException e) {
            throw new RuntimeException("EdgeService dead?", e);
        }
    }

    public void disableEdgeLightingNotification(boolean disable) {
        if (getService() == null) {
            return;
        }
        try {
            this.mService.disableEdgeLightingNotification(this.mPackageName, disable);
        } catch (RemoteException e) {
            throw new RuntimeException("EdgeService dead?", e);
        }
    }

    public boolean isPackageEnabled(String packageName, int userId) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mService.isPackageEnabled(packageName, userId);
        } catch (RemoteException e) {
            throw new RuntimeException("EdgeService dead?", e);
        }
    }

    private boolean isEdgeLightingEnabled(ContentResolver cr) {
        return Settings.System.getInt(cr, EDGE_LIGHTING, 1) == 1;
    }

    private boolean isEdgeLightingEnabledByScreen(ContentResolver cr, boolean isInteractive) {
        int condition = Settings.System.getInt(cr, EDGE_LIGHTING_SHOW_CONDITION, 0);
        if (condition != 0) {
            if (!((condition != 1) ^ isInteractive)) {
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

        EdgeLightingCallbackDelegate(OnEdgeLightingCallback callback) {
            this.mCallback = callback;
            this.mListener = null;
            this.mHandler = new Handler(SemEdgeManager.this.mContext.getMainLooper()) { // from class: com.samsung.android.edge.SemEdgeManager.EdgeLightingCallbackDelegate.1
                @Override // android.p009os.Handler
                public void handleMessage(Message msg) {
                    if (EdgeLightingCallbackDelegate.this.mCallback != null) {
                        switch (msg.what) {
                            case 0:
                                Bundle param = (Bundle) msg.obj;
                                String pn = param.getString("packageName");
                                SemEdgeLightingInfo info = (SemEdgeLightingInfo) param.getParcelable(DocumentsContract.EXTRA_INFO);
                                EdgeLightingCallbackDelegate.this.mCallback.onStartEdgeLighting(pn, info, msg.arg1);
                                break;
                            case 1:
                                String pkg = (String) msg.obj;
                                EdgeLightingCallbackDelegate.this.mCallback.onStopEdgeLighting(pkg, msg.arg1);
                                break;
                            case 4:
                                EdgeLightingCallbackDelegate.this.mCallback.onScreenChanged(msg.arg1 == 1);
                                break;
                        }
                    }
                }
            };
        }

        EdgeLightingCallbackDelegate(OnEdgeLightingListener listener) {
            this.mCallback = null;
            this.mListener = listener;
            this.mHandler = new Handler(SemEdgeManager.this.mContext.getMainLooper()) { // from class: com.samsung.android.edge.SemEdgeManager.EdgeLightingCallbackDelegate.2
                @Override // android.p009os.Handler
                public void handleMessage(Message msg) {
                    if (EdgeLightingCallbackDelegate.this.mListener != null) {
                        switch (msg.what) {
                            case 2:
                                EdgeLightingCallbackDelegate.this.mListener.onEdgeLightingStarted();
                                break;
                            case 3:
                                EdgeLightingCallbackDelegate.this.mListener.onEdgeLightingStopped();
                                break;
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
        public void onStartEdgeLighting(String packageName, SemEdgeLightingInfo info, int reason) throws RemoteException {
            Message msg = Message.obtain(this.mHandler, 0, reason, 0);
            Bundle params = new Bundle();
            params.putString("packageName", packageName);
            params.putParcelable(DocumentsContract.EXTRA_INFO, info);
            msg.obj = params;
            msg.sendToTarget();
        }

        @Override // com.samsung.android.edge.IEdgeLightingCallback
        public void onStopEdgeLighting(String packageName, int reason) throws RemoteException {
            Message msg = Message.obtain(this.mHandler, 1, reason, 0);
            msg.obj = packageName;
            msg.sendToTarget();
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
