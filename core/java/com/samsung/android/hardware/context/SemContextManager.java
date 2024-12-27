package com.samsung.android.hardware.context;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.SystemProperties;
import android.util.Log;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

/* loaded from: classes6.dex */
public class SemContextManager {
    private static final String TAG = "SemContextManager";
    private Map<Integer, Integer> mAvailableServiceMap;
    private String mClientInfo;
    private final CopyOnWriteArrayList<ListenerDelegate> mListenerDelegates;
    private final Looper mMainLooper;
    private String mPackageName;
    private ISemContextService mSemContextService;

    public SemContextManager(Context ctx, Looper mainLooper) {
        this.mListenerDelegates = new CopyOnWriteArrayList<>();
        this.mSemContextService = null;
        this.mAvailableServiceMap = null;
        this.mClientInfo = "";
        this.mSemContextService =
                ISemContextService.Stub.asInterface(ServiceManager.getService("scontext"));
        this.mMainLooper = mainLooper;
        this.mPackageName = ctx.getPackageName();
    }

    public SemContextManager(Looper mainLooper) {
        this.mListenerDelegates = new CopyOnWriteArrayList<>();
        this.mSemContextService = null;
        this.mAvailableServiceMap = null;
        this.mClientInfo = "";
        this.mSemContextService =
                ISemContextService.Stub.asInterface(ServiceManager.getService("scontext"));
        this.mMainLooper = mainLooper;
        this.mPackageName = " ";
    }

    private synchronized boolean recoverService() {
        if (this.mSemContextService == null) {
            this.mSemContextService =
                    ISemContextService.Stub.asInterface(ServiceManager.getService("scontext"));
        }
        return this.mSemContextService != null;
    }

    public boolean registerListener(SemContextListener listener, int service) {
        SemContextAttribute attribute = SemContextAttribute.getDefaultAttribute(service);
        return registerListener(listener, service, attribute);
    }

    public boolean registerListener(SemContextListener listener, int service, Looper looper) {
        SemContextAttribute attribute = SemContextAttribute.getDefaultAttribute(service);
        return registerListener(listener, service, attribute, looper);
    }

    public boolean registerListener(
            SemContextListener listener, int service, SemContextAttribute attribute) {
        if (this.mSemContextService == null) {
            Log.e(TAG, "SemContextService is null");
            if (!recoverService()) {
                return false;
            }
        }
        if (service == 48) {
            return setReferenceData(service, attribute);
        }
        if (attribute == null
                || !attribute.checkAttribute()
                || !checkListenerAndService(listener, service)) {
            return false;
        }
        ListenerDelegate listenerDelegate = getListenerDelegate(listener);
        if (listenerDelegate == null) {
            listenerDelegate = new ListenerDelegate(listener, (Looper) null);
            this.mListenerDelegates.add(listenerDelegate);
        }
        try {
            this.mSemContextService.registerCallback(
                    listenerDelegate, service, attribute, this.mPackageName);
            Log.d(
                    TAG,
                    "  .registerListener : listener = "
                            + listener
                            + ", service="
                            + SemContext.getServiceName(service));
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in registerListener: ", e);
            return true;
        }
    }

    public boolean registerListener(
            SemContextListener listener,
            int service,
            SemContextAttribute attribute,
            Looper looper) {
        if (this.mSemContextService == null) {
            Log.e(TAG, "SemContextService is null");
            if (!recoverService()) {
                return false;
            }
        }
        if (attribute == null
                || !attribute.checkAttribute()
                || !checkListenerAndService(listener, service)) {
            return false;
        }
        ListenerDelegate listenerDelegate = getListenerDelegate(listener);
        if (listenerDelegate == null) {
            listenerDelegate = new ListenerDelegate(listener, looper);
            this.mListenerDelegates.add(listenerDelegate);
        }
        try {
            this.mSemContextService.registerCallback(
                    listenerDelegate, service, attribute, this.mPackageName);
            Log.d(
                    TAG,
                    "  .registerListener : listener = "
                            + listener
                            + ", service="
                            + SemContext.getServiceName(service));
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in registerListener: ", e);
            return true;
        }
    }

    @Deprecated
    public boolean registerListener(SemContextListener listener, int service, int arg) {
        SemContextAttribute attribute = null;
        if (service == 3) {
            attribute = new SemContextStepCountAlertAttribute(arg);
        } else if (service == 6) {
            attribute = new SemContextAutoRotationAttribute(arg);
        } else if (service == 16) {
            attribute = new SemContextWakeUpVoiceAttribute(arg);
        } else if (service == 33) {
            attribute = new SemContextStepLevelMonitorAttribute(arg);
        } else if (service == 36) {
            attribute = new SemContextFlatMotionForTableModeAttribute(arg);
        }
        return registerListener(listener, service, attribute);
    }

    @Deprecated
    public boolean registerListener(SemContextListener listener, int service, int[] arg) {
        SemContextAttribute attribute = null;
        if (service == 27) {
            attribute = new SemContextActivityNotificationAttribute(arg);
        }
        return registerListener(listener, service, attribute);
    }

    @Deprecated
    public boolean registerListener(
            SemContextListener listener, int service, int[] arg1, int arg2) {
        SemContextAttribute attribute = null;
        if (service == 30) {
            attribute = new SemContextActivityNotificationExAttribute(arg1, arg2);
        }
        return registerListener(listener, service, attribute);
    }

    @Deprecated
    public boolean registerListener(SemContextListener listener, int service, int arg1, int arg2) {
        SemContextAttribute attribute = null;
        if (service == 12) {
            attribute = new SemContextShakeMotionAttribute(arg1, arg2);
        }
        return registerListener(listener, service, attribute);
    }

    @Deprecated
    public boolean registerListener(
            SemContextListener listener, int service, int arg1, int arg2, int arg3) {
        SemContextAttribute attribute = null;
        if (service == 35) {
            attribute = new SemContextSedentaryTimerAttribute(arg1, arg2, arg3, 1500, 1500);
        }
        return registerListener(listener, service, attribute);
    }

    @Deprecated
    public boolean registerListener(
            SemContextListener listener, int service, int arg1, double arg2, double arg3) {
        SemContextAttribute attribute = null;
        if (service == 2) {
            attribute = new SemContextPedometerAttribute(arg1, arg2, arg3);
        }
        return registerListener(listener, service, attribute);
    }

    @Deprecated
    public boolean registerListener(
            SemContextListener listener, int service, int arg1, int arg2, int arg3, int arg4) {
        SemContextAttribute attribute = null;
        if (service == 28) {
            attribute = new SemContextSpecificPoseAlertAttribute(arg1, arg2, arg3, arg4);
        }
        return registerListener(listener, service, attribute);
    }

    @Deprecated
    public boolean registerListener(
            SemContextListener listener,
            int service,
            int arg1,
            int arg2,
            int arg3,
            int arg4,
            int arg5) {
        SemContextAttribute attribute = null;
        if (service == 24) {
            attribute =
                    new SemContextActivityLocationLoggingAttribute(arg1, arg2, arg3, arg4, arg5);
        } else if (service == 35) {
            attribute = new SemContextSedentaryTimerAttribute(arg1, arg2, arg3, arg4, arg5);
        }
        return registerListener(listener, service, attribute);
    }

    public void unregisterListener(SemContextListener listener) {
        unregisterListener(listener, -1);
    }

    public void unregisterListener(SemContextListener listener, int service) {
        if (checkListenerAndService(listener, service)) {
            if (this.mSemContextService == null) {
                Log.e(TAG, "SemContextService is null");
                if (!recoverService()) {
                    return;
                }
            }
            ListenerDelegate listenerDelegate = getListenerDelegate(listener);
            if (listenerDelegate == null) {
                Log.e(
                        TAG,
                        "  .unregisterListener : SemContextListener is null!, manager = "
                                + toString()
                                + ", listener = "
                                + listener.toString()
                                + ", service = "
                                + service);
                return;
            }
            try {
                if (this.mSemContextService.unregisterCallback(listenerDelegate, service)) {
                    listenerDelegate.clear();
                    this.mListenerDelegates.remove(listenerDelegate);
                }
                Log.d(
                        TAG,
                        "  .unregisterListener : listener = "
                                + listener
                                + ", service="
                                + SemContext.getServiceName(service));
            } catch (RemoteException e) {
                Log.e(TAG, "RemoteException in unregisterListener: ", e);
            }
        }
    }

    @Deprecated
    public void initializeSemContextService(SemContextListener listener, int service) {
        if (this.mSemContextService == null) {
            Log.e(TAG, "SemContextService is null");
            if (!recoverService()) {
                return;
            }
        }
        if (!isAvailableService(service) || service != 3) {
            return;
        }
        ListenerDelegate listenerDelegate = getListenerDelegate(listener);
        if (listenerDelegate == null) {
            Log.e(TAG, "  .initializeSemContextService : SemContextListener is null!");
            return;
        }
        try {
            this.mSemContextService.initializeService(listenerDelegate, service);
            Log.d(
                    TAG,
                    "  .initializeSemContextService : listener = "
                            + listener
                            + ", service="
                            + SemContext.getServiceName(service));
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in initializeSemContextService: ", e);
        }
    }

    public boolean changeParameters(
            SemContextListener listener, int service, SemContextAttribute attribute) {
        if (this.mSemContextService == null) {
            Log.e(TAG, "SemContextService is null");
            if (!recoverService()) {
                return false;
            }
        }
        if (attribute == null
                || !attribute.checkAttribute()
                || !checkListenerAndService(listener, service)) {
            return false;
        }
        if (service != 1
                && service != 2
                && service != 33
                && service != 35
                && service != 39
                && service != 47
                && service != 51
                && service != 53
                && service != 54
                && service != 56) {
            return false;
        }
        ListenerDelegate listenerDelegate = getListenerDelegate(listener);
        if (listenerDelegate == null) {
            Log.e(TAG, "  .changeParameters : SemContextListener is null!");
            return false;
        }
        try {
            if (this.mSemContextService.changeParameters(listenerDelegate, service, attribute)) {
                Log.d(
                        TAG,
                        "  .changeParameters : listener = "
                                + listener
                                + ", service="
                                + SemContext.getServiceName(service));
            }
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in changeParameters: ", e);
        }
        return true;
    }

    @Deprecated
    public boolean changeParameters(
            SemContextListener listener, int service, int arg1, int arg2, int arg3, int arg4) {
        SemContextAttribute attribute = null;
        if (service == 35) {
            attribute = new SemContextSedentaryTimerAttribute(1, arg1, arg2, arg3, arg4);
        }
        return changeParameters(listener, service, attribute);
    }

    @Deprecated
    public boolean changeParameters(
            SemContextListener listener, int service, int arg1, double arg2, double arg3) {
        SemContextAttribute attribute = null;
        if (service == 2) {
            attribute = new SemContextPedometerAttribute(arg1, arg2, arg3);
        }
        return changeParameters(listener, service, attribute);
    }

    @Deprecated
    public boolean changeParameters(SemContextListener listener, int service, int arg1) {
        SemContextAttribute attribute = null;
        if (service == 2) {
            attribute = new SemContextPedometerAttribute(arg1);
        } else if (service == 33) {
            attribute = new SemContextStepLevelMonitorAttribute(arg1);
        }
        return changeParameters(listener, service, attribute);
    }

    public void requestToUpdate(SemContextListener listener, int service) {
        if (this.mSemContextService == null) {
            Log.e(TAG, "SemContextService is null");
            if (!recoverService()) {
                return;
            }
        }
        if (isAvailableService(service)) {
            if (service != 2
                    && service != 25
                    && service != 26
                    && service != 50
                    && service != 51
                    && service != 52
                    && service != 54) {
                Log.e(TAG, "  .requestToUpdate : This service is not supported!");
                return;
            }
            ListenerDelegate listenerDelegate = getListenerDelegate(listener);
            if (listenerDelegate == null) {
                Log.e(TAG, "  .requestToUpdate : SemContextListener is null!");
                return;
            }
            try {
                this.mSemContextService.requestToUpdate(
                        listenerDelegate, service, this.mPackageName);
                Log.d(
                        TAG,
                        "  .requestToUpdate : listener = "
                                + listener
                                + ", service="
                                + SemContext.getServiceName(service));
            } catch (RemoteException e) {
                Log.e(TAG, "RemoteException in requestToUpdate: ", e);
            }
        }
    }

    public void requestHistoryData(SemContextListener listener, int service) {
        if (this.mSemContextService == null) {
            Log.e(TAG, "SemContextService is null");
            if (!recoverService()) {
                return;
            }
        }
        if (isAvailableService(service)) {
            if (service != 2 && service != 33 && service != 26) {
                Log.e(TAG, "  .requestHistoryData : This service is not supported!");
                return;
            }
            if (checkListenerAndService(listener, service)) {
                if (getListenerDelegate(listener) != null) {
                    Log.e(
                            TAG,
                            "  .requestHistoryData : This listener is currently using other"
                                + " services. You should create a new listener to request history"
                                + " data.");
                    return;
                }
                ListenerDelegate listenerDelegate = new ListenerDelegate(listener, true);
                this.mListenerDelegates.add(listenerDelegate);
                try {
                    this.mSemContextService.requestHistoryData(
                            listenerDelegate, service, this.mPackageName);
                    Log.d(
                            TAG,
                            "  .requestHistoryData : listener = "
                                    + listener
                                    + ", service="
                                    + SemContext.getServiceName(service));
                } catch (RemoteException e) {
                    Log.e(TAG, "RemoteException in requestHistoryData: ", e);
                }
            }
        }
    }

    public boolean isAvailableService(int service) {
        if (service == -1) {
            return true;
        }
        if (this.mAvailableServiceMap == null) {
            this.mAvailableServiceMap = getAvailableServiceMap();
        }
        if (this.mAvailableServiceMap == null) {
            return false;
        }
        boolean res = this.mAvailableServiceMap.containsKey(Integer.valueOf(service));
        if (service == 47
                && "BCM4773_SLOCATION_CORE"
                        .equals(SystemProperties.get("ro.gps.chip.vendor.slocation"))) {
            return false;
        }
        return res;
    }

    public int getFeatureLevel(int service) {
        if (isAvailableService(service)) {
            return this.mAvailableServiceMap.get(Integer.valueOf(service)).intValue();
        }
        return 0;
    }

    @Deprecated
    public boolean setReferenceData(int service, byte[] data1, byte[] data2) {
        SemContextAttribute attribute = null;
        if (data1 == null || data2 == null) {
            return false;
        }
        if (service == 16) {
            attribute = new SemContextWakeUpVoiceAttribute(data1, data2);
        }
        return setReferenceData(service, attribute);
    }

    public boolean setReferenceData(int service, SemContextAttribute attribute) {
        Bundle bundle;
        byte[] luminanceConfigData;
        boolean z = false;
        if (this.mSemContextService == null) {
            Log.e(TAG, "SemContextService is null");
            if (!recoverService()) {
                return false;
            }
        }
        if (attribute == null) {
            return false;
        }
        if (service == 48) {
            bundle = attribute.getAttribute(48);
        } else {
            bundle = attribute.getAttribute(service);
        }
        if (bundle == null) {
            return false;
        }
        try {
            switch (service) {
                case 16:
                    boolean res = bundle.containsKey("net_data");
                    if (res && bundle.containsKey("gram_data")) {
                        byte[] netData = bundle.getByteArray("net_data");
                        byte[] gramData = bundle.getByteArray("gram_data");
                        if (netData != null && gramData != null) {
                            if (this.mSemContextService.setReferenceData(service, 1, netData)
                                    && this.mSemContextService.setReferenceData(
                                            service, 2, gramData)) {
                                z = true;
                            }
                            boolean res2 = z;
                            break;
                        }
                    }
                    break;
                case 39:
                    if (bundle.containsKey("luminance_config_data")
                            && (luminanceConfigData = bundle.getByteArray("luminance_config_data"))
                                    != null) {
                        boolean res3 =
                                this.mSemContextService.setReferenceData(
                                        service, 0, luminanceConfigData);
                        break;
                    }
                    break;
                case 43:
                    if (!bundle.containsKey("display_status")) {
                        Log.d(TAG, "Bundle is not contained key data");
                        break;
                    } else {
                        byte[] hallSensorStatus = {(byte) bundle.getInt("display_status")};
                        Log.d(
                                TAG,
                                "Hall Sensor Data : " + String.valueOf((int) hallSensorStatus[0]));
                        boolean res4 =
                                this.mSemContextService.setReferenceData(
                                        service, 43, hallSensorStatus);
                        break;
                    }
                case 48:
                    if (!bundle.containsKey("interrupt_gyro")) {
                        Log.d(TAG, "Bundle is not contained key data");
                        break;
                    } else {
                        byte[] sysfsMode = {(byte) bundle.getInt("interrupt_gyro")};
                        Log.d(TAG, "sysfs data : " + String.valueOf((int) sysfsMode[0]));
                        boolean res5 =
                                this.mSemContextService.setReferenceData(service, 48, sysfsMode);
                        break;
                    }
            }
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in initializeSemContextService: ", e);
            return false;
        }
        return false;
    }

    public void setClientInfo(String clientInfo) {
        this.mClientInfo = clientInfo;
    }

    public String getCurrentServiceList() {
        if (this.mSemContextService == null) {
            Log.e(TAG, "SemContextService is null");
            if (!recoverService()) {
                return "";
            }
        }
        try {
            String result = this.mSemContextService.getCurrentServiceList();
            return result;
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in getCurrentServiceList: ", e);
            return "";
        }
    }

    private Map<Integer, Integer> getAvailableServiceMap() {
        if (this.mSemContextService == null) {
            Log.e(TAG, "SemContextService is null");
            if (!recoverService()) {
                return null;
            }
        }
        try {
            HashMap<Integer, Integer> serviceMap =
                    (HashMap) this.mSemContextService.getAvailableServiceMap();
            return serviceMap;
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in getAvailableServiceMap: ", e);
            return null;
        }
    }

    private ListenerDelegate getListenerDelegate(SemContextListener listener) {
        if (listener == null || this.mListenerDelegates.isEmpty()) {
            return null;
        }
        Iterator<ListenerDelegate> it = this.mListenerDelegates.iterator();
        while (it.hasNext()) {
            ListenerDelegate delegate = it.next();
            if (delegate.getListener().equals(listener)) {
                return delegate;
            }
        }
        return null;
    }

    private boolean checkListenerAndService(SemContextListener listener, int service) {
        if (listener == null) {
            Log.d(TAG, "Listener is null!");
            return false;
        }
        return isAvailableService(service);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkHistoryMode(SemContextEvent event) {
        boolean z = false;
        Boolean res = false;
        StringBuilder sb = new StringBuilder();
        int type = event.semContext.getType();
        sb.append("onSemContextChanged() : event = " + SemContext.getServiceName(type));
        switch (type) {
            case 2:
                SemContextPedometer pedometerContext = event.getPedometerContext();
                if (pedometerContext.getMode() == 2) {
                    z = true;
                }
                res = Boolean.valueOf(z);
                break;
            case 6:
                SemContextAutoRotation autoRotation = event.getAutoRotationContext();
                sb.append(" Angle : ").append(autoRotation.getAngle());
                break;
            case 26:
                SemContextActivityBatch activityBatchContext = event.getActivityBatchContext();
                if (activityBatchContext.getMode() == 1) {
                    z = true;
                }
                res = Boolean.valueOf(z);
                break;
            case 33:
                SemContextStepLevelMonitor stepLevelMonitorContext =
                        event.getStepLevelMonitorContext();
                if (stepLevelMonitorContext.getMode() == 1) {
                    z = true;
                }
                res = Boolean.valueOf(z);
                break;
        }
        Log.d(TAG, sb.toString());
        return res.booleanValue();
    }

    private class ListenerDelegate extends ISemContextCallback.Stub {
        private boolean mDereisgeredListener;
        private Handler mHandler;
        private boolean mIsHistoryData;
        private SemContextListener mListener;

        ListenerDelegate(SemContextListener listener, Looper looper) {
            SemContextManager.this.mClientInfo = "";
            this.mDereisgeredListener = false;
            set(listener, looper, false);
        }

        ListenerDelegate(SemContextListener listener, boolean isHistoryData) {
            this.mDereisgeredListener = false;
            set(listener, null, isHistoryData);
        }

        private void set(SemContextListener listener, Looper looper, boolean isHistoryData) {
            this.mListener = listener;
            Looper mainLooper = looper != null ? looper : SemContextManager.this.mMainLooper;
            this.mIsHistoryData = isHistoryData;
            this.mHandler =
                    new Handler(
                            mainLooper) { // from class:
                                          // com.samsung.android.hardware.context.SemContextManager.ListenerDelegate.1
                        @Override // android.os.Handler
                        public void handleMessage(Message msg) {
                            SemContextEvent event;
                            SemContext context;
                            if (ListenerDelegate.this.mListener == null
                                    || (event = (SemContextEvent) msg.obj) == null
                                    || (context = event.semContext) == null) {
                                return;
                            }
                            int type = context.getType();
                            if (ListenerDelegate.this.mIsHistoryData) {
                                Log.d(
                                        SemContextManager.TAG,
                                        "History data is received. : type = "
                                                + SemContext.getServiceName(type));
                                ListenerDelegate.this.mListener.onSemContextChanged(event);
                                SemContextManager.this.unregisterListener(
                                        ListenerDelegate.this.mListener, type);
                                ListenerDelegate.this.mIsHistoryData = false;
                                return;
                            }
                            if (!SemContextManager.this.checkHistoryMode(event)) {
                                if (type == 6) {
                                    SemContextAutoRotation autoRotation =
                                            event.getAutoRotationContext();
                                    Log.d(
                                            SemContextManager.TAG,
                                            "AutoRotationEvent : Angle = "
                                                    + autoRotation.getAngle());
                                } else if (type == 2) {
                                    SemContextPedometer pedometer = event.getPedometerContext();
                                    Log.d(
                                            SemContextManager.TAG,
                                            "[2] : "
                                                    + pedometer.getTotalStepCount()
                                                    + ", "
                                                    + pedometer.getWalkStepCount()
                                                    + ", "
                                                    + pedometer.getRunStepCount());
                                }
                                if (!ListenerDelegate.this.mDereisgeredListener) {
                                    ListenerDelegate.this.mListener.onSemContextChanged(event);
                                }
                            }
                        }
                    };
        }

        public void clear() {
            this.mDereisgeredListener = true;
        }

        public SemContextListener getListener() {
            return this.mListener;
        }

        @Override // com.samsung.android.hardware.context.ISemContextCallback
        public synchronized void semContextCallback(SemContextEvent event) throws RemoteException {
            Message msg = Message.obtain();
            msg.what = 0;
            msg.obj = event;
            this.mHandler.sendMessage(msg);
            notifyAll();
        }

        @Override // com.samsung.android.hardware.context.ISemContextCallback
        public String getListenerInfo() throws RemoteException {
            if (this.mListener != null && "".equals(SemContextManager.this.mClientInfo)) {
                return this.mListener.toString();
            }
            return SemContextManager.this.mClientInfo;
        }
    }
}
