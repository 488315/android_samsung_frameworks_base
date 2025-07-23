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
import com.samsung.android.hardware.context.ISemContextCallback;
import com.samsung.android.hardware.context.ISemContextService;
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

    public SemContextManager(Context context, Looper looper) {
        this.mListenerDelegates = new CopyOnWriteArrayList<>();
        this.mSemContextService = null;
        this.mAvailableServiceMap = null;
        this.mClientInfo = "";
        this.mSemContextService = ISemContextService.Stub.asInterface(ServiceManager.getService("scontext"));
        this.mMainLooper = looper;
        this.mPackageName = context.getPackageName();
    }

    public SemContextManager(Looper looper) {
        this.mListenerDelegates = new CopyOnWriteArrayList<>();
        this.mSemContextService = null;
        this.mAvailableServiceMap = null;
        this.mClientInfo = "";
        this.mSemContextService = ISemContextService.Stub.asInterface(ServiceManager.getService("scontext"));
        this.mMainLooper = looper;
        this.mPackageName = " ";
    }

    private synchronized boolean recoverService() {
        if (this.mSemContextService == null) {
            this.mSemContextService = ISemContextService.Stub.asInterface(ServiceManager.getService("scontext"));
        }
        return this.mSemContextService != null;
    }

    public boolean registerListener(SemContextListener semContextListener, int i) {
        return registerListener(semContextListener, i, SemContextAttribute.getDefaultAttribute(i));
    }

    public boolean registerListener(SemContextListener semContextListener, int i, Looper looper) {
        return registerListener(semContextListener, i, SemContextAttribute.getDefaultAttribute(i), looper);
    }

    public boolean registerListener(SemContextListener semContextListener, int i, SemContextAttribute semContextAttribute) {
        if (this.mSemContextService == null) {
            Log.e(TAG, "SemContextService is null");
            if (!recoverService()) {
                return false;
            }
        }
        if (i == 48) {
            return setReferenceData(i, semContextAttribute);
        }
        if (semContextAttribute == null || !semContextAttribute.checkAttribute() || !checkListenerAndService(semContextListener, i)) {
            return false;
        }
        ListenerDelegate listenerDelegate = getListenerDelegate(semContextListener);
        if (listenerDelegate == null) {
            listenerDelegate = new ListenerDelegate(semContextListener, (Looper) null);
            this.mListenerDelegates.add(listenerDelegate);
        }
        try {
            this.mSemContextService.registerCallback(listenerDelegate, i, semContextAttribute, this.mPackageName);
            Log.d(TAG, "  .registerListener : listener = " + semContextListener + ", service=" + SemContext.getServiceName(i));
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in registerListener: ", e);
            return true;
        }
    }

    public boolean registerListener(SemContextListener semContextListener, int i, SemContextAttribute semContextAttribute, Looper looper) {
        if (this.mSemContextService == null) {
            Log.e(TAG, "SemContextService is null");
            if (!recoverService()) {
                return false;
            }
        }
        if (semContextAttribute == null || !semContextAttribute.checkAttribute() || !checkListenerAndService(semContextListener, i)) {
            return false;
        }
        ListenerDelegate listenerDelegate = getListenerDelegate(semContextListener);
        if (listenerDelegate == null) {
            listenerDelegate = new ListenerDelegate(semContextListener, looper);
            this.mListenerDelegates.add(listenerDelegate);
        }
        try {
            this.mSemContextService.registerCallback(listenerDelegate, i, semContextAttribute, this.mPackageName);
            Log.d(TAG, "  .registerListener : listener = " + semContextListener + ", service=" + SemContext.getServiceName(i));
            return true;
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in registerListener: ", e);
            return true;
        }
    }

    @Deprecated
    public boolean registerListener(SemContextListener semContextListener, int i, int i2) {
        SemContextAttribute semContextFlatMotionForTableModeAttribute;
        if (i == 3) {
            semContextFlatMotionForTableModeAttribute = new SemContextStepCountAlertAttribute(i2);
        } else if (i == 6) {
            semContextFlatMotionForTableModeAttribute = new SemContextAutoRotationAttribute(i2);
        } else if (i == 16) {
            semContextFlatMotionForTableModeAttribute = new SemContextWakeUpVoiceAttribute(i2);
        } else if (i == 33) {
            semContextFlatMotionForTableModeAttribute = new SemContextStepLevelMonitorAttribute(i2);
        } else {
            semContextFlatMotionForTableModeAttribute = i == 36 ? new SemContextFlatMotionForTableModeAttribute(i2) : null;
        }
        return registerListener(semContextListener, i, semContextFlatMotionForTableModeAttribute);
    }

    @Deprecated
    public boolean registerListener(SemContextListener semContextListener, int i, int[] iArr) {
        return registerListener(semContextListener, i, i == 27 ? new SemContextActivityNotificationAttribute(iArr) : null);
    }

    @Deprecated
    public boolean registerListener(SemContextListener semContextListener, int i, int[] iArr, int i2) {
        return registerListener(semContextListener, i, i == 30 ? new SemContextActivityNotificationExAttribute(iArr, i2) : null);
    }

    @Deprecated
    public boolean registerListener(SemContextListener semContextListener, int i, int i2, int i3) {
        return registerListener(semContextListener, i, i == 12 ? new SemContextShakeMotionAttribute(i2, i3) : null);
    }

    @Deprecated
    public boolean registerListener(SemContextListener semContextListener, int i, int i2, int i3, int i4) {
        return registerListener(semContextListener, i, i == 35 ? new SemContextSedentaryTimerAttribute(i2, i3, i4, 1500, 1500) : null);
    }

    @Deprecated
    public boolean registerListener(SemContextListener semContextListener, int i, int i2, double d, double d2) {
        return registerListener(semContextListener, i, i == 2 ? new SemContextPedometerAttribute(i2, d, d2) : null);
    }

    @Deprecated
    public boolean registerListener(SemContextListener semContextListener, int i, int i2, int i3, int i4, int i5) {
        return registerListener(semContextListener, i, i == 28 ? new SemContextSpecificPoseAlertAttribute(i2, i3, i4, i5) : null);
    }

    @Deprecated
    public boolean registerListener(SemContextListener semContextListener, int i, int i2, int i3, int i4, int i5, int i6) {
        SemContextAttribute semContextSedentaryTimerAttribute;
        if (i == 24) {
            semContextSedentaryTimerAttribute = new SemContextActivityLocationLoggingAttribute(i2, i3, i4, i5, i6);
        } else {
            semContextSedentaryTimerAttribute = i == 35 ? new SemContextSedentaryTimerAttribute(i2, i3, i4, i5, i6) : null;
        }
        return registerListener(semContextListener, i, semContextSedentaryTimerAttribute);
    }

    public void unregisterListener(SemContextListener semContextListener) {
        unregisterListener(semContextListener, -1);
    }

    public void unregisterListener(SemContextListener semContextListener, int i) {
        if (checkListenerAndService(semContextListener, i)) {
            if (this.mSemContextService == null) {
                Log.e(TAG, "SemContextService is null");
                if (!recoverService()) {
                    return;
                }
            }
            ListenerDelegate listenerDelegate = getListenerDelegate(semContextListener);
            if (listenerDelegate == null) {
                Log.e(TAG, "  .unregisterListener : SemContextListener is null!, manager = " + toString() + ", listener = " + semContextListener.toString() + ", service = " + i);
                return;
            }
            try {
                if (this.mSemContextService.unregisterCallback(listenerDelegate, i)) {
                    listenerDelegate.clear();
                    this.mListenerDelegates.remove(listenerDelegate);
                }
                Log.d(TAG, "  .unregisterListener : listener = " + semContextListener + ", service=" + SemContext.getServiceName(i));
            } catch (RemoteException e) {
                Log.e(TAG, "RemoteException in unregisterListener: ", e);
            }
        }
    }

    public void initializeSemContextService(SemContextListener semContextListener, int i) {
        if (this.mSemContextService == null) {
            Log.e(TAG, "SemContextService is null");
            if (!recoverService()) {
                return;
            }
        }
        if (isAvailableService(i) && i == 3) {
            ListenerDelegate listenerDelegate = getListenerDelegate(semContextListener);
            if (listenerDelegate == null) {
                Log.e(TAG, "  .initializeSemContextService : SemContextListener is null!");
                return;
            }
            try {
                this.mSemContextService.initializeService(listenerDelegate, i);
                Log.d(TAG, "  .initializeSemContextService : listener = " + semContextListener + ", service=" + SemContext.getServiceName(i));
            } catch (RemoteException e) {
                Log.e(TAG, "RemoteException in initializeSemContextService: ", e);
            }
        }
    }

    public boolean changeParameters(SemContextListener semContextListener, int i, SemContextAttribute semContextAttribute) {
        if (this.mSemContextService == null) {
            Log.e(TAG, "SemContextService is null");
            if (!recoverService()) {
                return false;
            }
        }
        if (semContextAttribute == null || !semContextAttribute.checkAttribute() || !checkListenerAndService(semContextListener, i)) {
            return false;
        }
        if (i != 1 && i != 2 && i != 33 && i != 35 && i != 39 && i != 47 && i != 51 && i != 53 && i != 54 && i != 56) {
            return false;
        }
        ListenerDelegate listenerDelegate = getListenerDelegate(semContextListener);
        if (listenerDelegate == null) {
            Log.e(TAG, "  .changeParameters : SemContextListener is null!");
            return false;
        }
        try {
            if (this.mSemContextService.changeParameters(listenerDelegate, i, semContextAttribute)) {
                Log.d(TAG, "  .changeParameters : listener = " + semContextListener + ", service=" + SemContext.getServiceName(i));
            }
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in changeParameters: ", e);
        }
        return true;
    }

    @Deprecated
    public boolean changeParameters(SemContextListener semContextListener, int i, int i2, int i3, int i4, int i5) {
        return changeParameters(semContextListener, i, i == 35 ? new SemContextSedentaryTimerAttribute(1, i2, i3, i4, i5) : null);
    }

    @Deprecated
    public boolean changeParameters(SemContextListener semContextListener, int i, int i2, double d, double d2) {
        return changeParameters(semContextListener, i, i == 2 ? new SemContextPedometerAttribute(i2, d, d2) : null);
    }

    @Deprecated
    public boolean changeParameters(SemContextListener semContextListener, int i, int i2) {
        SemContextAttribute semContextStepLevelMonitorAttribute;
        if (i == 2) {
            semContextStepLevelMonitorAttribute = new SemContextPedometerAttribute(i2);
        } else {
            semContextStepLevelMonitorAttribute = i == 33 ? new SemContextStepLevelMonitorAttribute(i2) : null;
        }
        return changeParameters(semContextListener, i, semContextStepLevelMonitorAttribute);
    }

    public void requestToUpdate(SemContextListener semContextListener, int i) {
        if (this.mSemContextService == null) {
            Log.e(TAG, "SemContextService is null");
            if (!recoverService()) {
                return;
            }
        }
        if (isAvailableService(i)) {
            if (i != 2 && i != 25 && i != 26 && i != 50 && i != 51 && i != 52 && i != 54) {
                Log.e(TAG, "  .requestToUpdate : This service is not supported!");
                return;
            }
            ListenerDelegate listenerDelegate = getListenerDelegate(semContextListener);
            if (listenerDelegate == null) {
                Log.e(TAG, "  .requestToUpdate : SemContextListener is null!");
                return;
            }
            try {
                this.mSemContextService.requestToUpdate(listenerDelegate, i, this.mPackageName);
                Log.d(TAG, "  .requestToUpdate : listener = " + semContextListener + ", service=" + SemContext.getServiceName(i));
            } catch (RemoteException e) {
                Log.e(TAG, "RemoteException in requestToUpdate: ", e);
            }
        }
    }

    public void requestHistoryData(SemContextListener semContextListener, int i) {
        if (this.mSemContextService == null) {
            Log.e(TAG, "SemContextService is null");
            if (!recoverService()) {
                return;
            }
        }
        if (isAvailableService(i)) {
            if (i != 2 && i != 33 && i != 26) {
                Log.e(TAG, "  .requestHistoryData : This service is not supported!");
                return;
            }
            if (checkListenerAndService(semContextListener, i)) {
                if (getListenerDelegate(semContextListener) != null) {
                    Log.e(TAG, "  .requestHistoryData : This listener is currently using other services. You should create a new listener to request history data.");
                    return;
                }
                ListenerDelegate listenerDelegate = new ListenerDelegate(semContextListener, true);
                this.mListenerDelegates.add(listenerDelegate);
                try {
                    this.mSemContextService.requestHistoryData(listenerDelegate, i, this.mPackageName);
                    Log.d(TAG, "  .requestHistoryData : listener = " + semContextListener + ", service=" + SemContext.getServiceName(i));
                } catch (RemoteException e) {
                    Log.e(TAG, "RemoteException in requestHistoryData: ", e);
                }
            }
        }
    }

    public boolean isAvailableService(int i) {
        if (i == -1) {
            return true;
        }
        if (this.mAvailableServiceMap == null) {
            this.mAvailableServiceMap = getAvailableServiceMap();
        }
        Map<Integer, Integer> map = this.mAvailableServiceMap;
        if (map == null) {
            return false;
        }
        boolean containsKey = map.containsKey(Integer.valueOf(i));
        if (i == 47 && "BCM4773_SLOCATION_CORE".equals(SystemProperties.get("ro.gps.chip.vendor.slocation"))) {
            return false;
        }
        return containsKey;
    }

    public int getFeatureLevel(int i) {
        if (isAvailableService(i)) {
            return this.mAvailableServiceMap.get(Integer.valueOf(i)).intValue();
        }
        return 0;
    }

    @Deprecated
    public boolean setReferenceData(int i, byte[] bArr, byte[] bArr2) {
        if (bArr == null || bArr2 == null) {
            return false;
        }
        return setReferenceData(i, i == 16 ? new SemContextWakeUpVoiceAttribute(bArr, bArr2) : null);
    }

    public boolean setReferenceData(int i, SemContextAttribute semContextAttribute) {
        byte[] byteArray;
        if (this.mSemContextService == null) {
            Log.e(TAG, "SemContextService is null");
            if (!recoverService()) {
                return false;
            }
        }
        if (semContextAttribute == null) {
            return false;
        }
        Bundle attribute = i == 48 ? semContextAttribute.getAttribute(48) : semContextAttribute.getAttribute(i);
        if (attribute == null) {
            return false;
        }
        try {
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in initializeSemContextService: ", e);
        }
        if (i == 16) {
            if (attribute.containsKey("net_data") && attribute.containsKey("gram_data")) {
                byte[] byteArray2 = attribute.getByteArray("net_data");
                byte[] byteArray3 = attribute.getByteArray("gram_data");
                if (byteArray2 != null && byteArray3 != null && this.mSemContextService.setReferenceData(i, 1, byteArray2) && this.mSemContextService.setReferenceData(i, 2, byteArray3)) {
                    return true;
                }
            }
            return false;
        }
        if (i == 39) {
            if (attribute.containsKey("luminance_config_data") && (byteArray = attribute.getByteArray("luminance_config_data")) != null) {
                return this.mSemContextService.setReferenceData(i, 0, byteArray);
            }
            return false;
        }
        if (i == 43) {
            if (!attribute.containsKey("display_status")) {
                Log.d(TAG, "Bundle is not contained key data");
                return false;
            }
            byte[] bArr = {(byte) attribute.getInt("display_status")};
            Log.d(TAG, "Hall Sensor Data : " + String.valueOf((int) bArr[0]));
            return this.mSemContextService.setReferenceData(i, 43, bArr);
        }
        if (i == 48) {
            if (!attribute.containsKey("interrupt_gyro")) {
                Log.d(TAG, "Bundle is not contained key data");
                return false;
            }
            byte[] bArr2 = {(byte) attribute.getInt("interrupt_gyro")};
            Log.d(TAG, "sysfs data : " + String.valueOf((int) bArr2[0]));
            return this.mSemContextService.setReferenceData(i, 48, bArr2);
        }
        return false;
    }

    public void setClientInfo(String str) {
        this.mClientInfo = str;
    }

    public String getCurrentServiceList() {
        if (this.mSemContextService == null) {
            Log.e(TAG, "SemContextService is null");
            if (!recoverService()) {
                return "";
            }
        }
        try {
            return this.mSemContextService.getCurrentServiceList();
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
            return (HashMap) this.mSemContextService.getAvailableServiceMap();
        } catch (RemoteException e) {
            Log.e(TAG, "RemoteException in getAvailableServiceMap: ", e);
            return null;
        }
    }

    private ListenerDelegate getListenerDelegate(SemContextListener semContextListener) {
        if (semContextListener != null && !this.mListenerDelegates.isEmpty()) {
            Iterator<ListenerDelegate> it = this.mListenerDelegates.iterator();
            while (it.hasNext()) {
                ListenerDelegate next = it.next();
                if (next.getListener().equals(semContextListener)) {
                    return next;
                }
            }
        }
        return null;
    }

    private boolean checkListenerAndService(SemContextListener semContextListener, int i) {
        if (semContextListener == null) {
            Log.d(TAG, "Listener is null!");
            return false;
        }
        return isAvailableService(i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkHistoryMode(SemContextEvent semContextEvent) {
        Boolean bool = false;
        StringBuilder sb = new StringBuilder();
        int type = semContextEvent.semContext.getType();
        sb.append("onSemContextChanged() : event = " + SemContext.getServiceName(type));
        if (type == 2) {
            bool = Boolean.valueOf(semContextEvent.getPedometerContext().getMode() == 2);
        } else if (type == 6) {
            SemContextAutoRotation autoRotationContext = semContextEvent.getAutoRotationContext();
            sb.append(" Angle : ");
            sb.append(autoRotationContext.getAngle());
        } else if (type == 26) {
            bool = Boolean.valueOf(semContextEvent.getActivityBatchContext().getMode() == 1);
        } else if (type == 33) {
            bool = Boolean.valueOf(semContextEvent.getStepLevelMonitorContext().getMode() == 1);
        }
        Log.d(TAG, sb.toString());
        return bool.booleanValue();
    }

    private class ListenerDelegate extends ISemContextCallback.Stub {
        private boolean mDereisgeredListener;
        private Handler mHandler;
        private boolean mIsHistoryData;
        private SemContextListener mListener;

        ListenerDelegate(SemContextListener semContextListener, Looper looper) {
            SemContextManager.this.mClientInfo = "";
            this.mDereisgeredListener = false;
            set(semContextListener, looper, false);
        }

        ListenerDelegate(SemContextListener semContextListener, boolean z) {
            this.mDereisgeredListener = false;
            set(semContextListener, null, z);
        }

        private void set(SemContextListener semContextListener, Looper looper, boolean z) {
            this.mListener = semContextListener;
            if (looper == null) {
                looper = SemContextManager.this.mMainLooper;
            }
            this.mIsHistoryData = z;
            this.mHandler = new Handler(looper) { // from class: com.samsung.android.hardware.context.SemContextManager.ListenerDelegate.1
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    SemContextEvent semContextEvent;
                    SemContext semContext;
                    if (ListenerDelegate.this.mListener == null || (semContextEvent = (SemContextEvent) message.obj) == null || (semContext = semContextEvent.semContext) == null) {
                        return;
                    }
                    int type = semContext.getType();
                    if (ListenerDelegate.this.mIsHistoryData) {
                        Log.d(SemContextManager.TAG, "History data is received. : type = " + SemContext.getServiceName(type));
                        ListenerDelegate.this.mListener.onSemContextChanged(semContextEvent);
                        SemContextManager.this.unregisterListener(ListenerDelegate.this.mListener, type);
                        ListenerDelegate.this.mIsHistoryData = false;
                        return;
                    }
                    if (SemContextManager.this.checkHistoryMode(semContextEvent)) {
                        return;
                    }
                    if (type == 6) {
                        Log.d(SemContextManager.TAG, "AutoRotationEvent : Angle = " + semContextEvent.getAutoRotationContext().getAngle());
                    } else if (type == 2) {
                        SemContextPedometer pedometerContext = semContextEvent.getPedometerContext();
                        Log.d(SemContextManager.TAG, "[2] : " + pedometerContext.getTotalStepCount() + ", " + pedometerContext.getWalkStepCount() + ", " + pedometerContext.getRunStepCount());
                    }
                    if (ListenerDelegate.this.mDereisgeredListener) {
                        return;
                    }
                    ListenerDelegate.this.mListener.onSemContextChanged(semContextEvent);
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
        public synchronized void semContextCallback(SemContextEvent semContextEvent) throws RemoteException {
            Message obtain = Message.obtain();
            obtain.what = 0;
            obtain.obj = semContextEvent;
            this.mHandler.sendMessage(obtain);
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
