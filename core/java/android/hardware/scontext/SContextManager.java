package android.hardware.scontext;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;

import com.samsung.android.hardware.context.SemContextAttribute;
import com.samsung.android.hardware.context.SemContextEvent;
import com.samsung.android.hardware.context.SemContextListener;
import com.samsung.android.hardware.context.SemContextManager;

import java.util.HashMap;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

@Deprecated
/* loaded from: classes2.dex */
public class SContextManager extends SemContextManager {
    private static final String TAG = "SContextManager";
    private HashMap<Integer, Integer> mAvailableServiceMap;
    private SContextListener mIsHistoryDataListener;
    private final CopyOnWriteArrayList<SContextListenerDelegate> mListenerDelegates;
    Looper mMainLooper;

    @Deprecated
    public SContextManager(Looper mainLooper) {
        super(mainLooper);
        this.mListenerDelegates = new CopyOnWriteArrayList<>();
        this.mAvailableServiceMap = null;
        this.mIsHistoryDataListener = null;
        this.mMainLooper = mainLooper;
    }

    @Deprecated
    public SContextManager(Context ctx, Looper mainLooper) {
        super(ctx, mainLooper);
        this.mListenerDelegates = new CopyOnWriteArrayList<>();
        this.mAvailableServiceMap = null;
        this.mIsHistoryDataListener = null;
        this.mMainLooper = mainLooper;
    }

    @Deprecated
    public boolean registerListener(SContextListener listener, int service) {
        SContextAttribute attribute = addListenerAttribute(service);
        boolean res = registerListener(listener, service, attribute);
        return res;
    }

    @Deprecated
    public boolean registerListener(SContextListener listener, int service, Looper looper) {
        SContextAttribute attribute = addListenerAttribute(service);
        boolean res = registerListener(listener, service, attribute, looper);
        return res;
    }

    @Deprecated
    public boolean registerListener(
            SContextListener listener, int service, SContextAttribute attribute) {
        if (service == 48) {
            boolean res = setReferenceData(service, attribute);
            return res;
        }
        if (attribute == null
                || !attribute.checkAttribute()
                || !checkListenerAndService(listener, service)) {
            return false;
        }
        SContextListenerDelegate scontextListener = getListenerDelegate(listener);
        if (scontextListener == null) {
            scontextListener = new SContextListenerDelegate(listener, null, false);
            this.mListenerDelegates.add(scontextListener);
        }
        super.setClientInfo(listener.toString());
        if (!super.registerListener(scontextListener, service, attribute)) {
            return false;
        }
        Log.d(
                TAG,
                "  .registerListener : listener = "
                        + listener
                        + ", service="
                        + SContext.getServiceName(service));
        return true;
    }

    @Deprecated
    public boolean registerListener(
            SContextListener listener, int service, SContextAttribute attribute, Looper looper) {
        if (attribute == null
                || !attribute.checkAttribute()
                || !checkListenerAndService(listener, service)) {
            return false;
        }
        SContextListenerDelegate scontextListener = getListenerDelegate(listener);
        if (scontextListener == null) {
            scontextListener = new SContextListenerDelegate(listener, looper, false);
            this.mListenerDelegates.add(scontextListener);
        }
        super.setClientInfo(listener.toString());
        if (!super.registerListener(scontextListener, service, attribute, looper)) {
            return false;
        }
        Log.d(
                TAG,
                "  .registerListener : listener = "
                        + listener
                        + ", service="
                        + SContext.getServiceName(service));
        return true;
    }

    @Deprecated
    public boolean registerListener(SContextListener listener, int service, int arg) {
        SContextAttribute attribute = null;
        if (service == 3) {
            attribute = new SContextStepCountAlertAttribute(arg);
        } else if (service == 6) {
            attribute = new SContextAutoRotationAttribute(arg);
        } else if (service == 16) {
            attribute = new SContextWakeUpVoiceAttribute(arg);
        } else if (service == 33) {
            attribute = new SContextStepLevelMonitorAttribute(arg);
        } else if (service == 36) {
            attribute = new SContextFlatMotionForTableModeAttribute(arg);
        }
        boolean res = registerListener(listener, service, attribute);
        return res;
    }

    @Deprecated
    public boolean registerListener(SContextListener listener, int service, int[] arg) {
        SContextAttribute attribute = null;
        if (service == 27) {
            attribute = new SContextActivityNotificationAttribute(arg);
        }
        boolean res = registerListener(listener, service, attribute);
        return res;
    }

    @Deprecated
    public boolean registerListener(SContextListener listener, int service, int[] arg1, int arg2) {
        SContextAttribute attribute = null;
        if (service == 30) {
            attribute = new SContextActivityNotificationExAttribute(arg1, arg2);
        }
        boolean res = registerListener(listener, service, attribute);
        return res;
    }

    @Deprecated
    public boolean registerListener(SContextListener listener, int service, int arg1, int arg2) {
        SContextAttribute attribute = null;
        if (service == 8) {
            attribute = new SContextEnvironmentAttribute(arg1, arg2);
        } else if (service == 12) {
            attribute = new SContextShakeMotionAttribute(arg1, arg2);
        } else if (service == 29) {
            attribute = new SContextSleepMonitorAttribute(arg1, arg2);
        }
        boolean res = registerListener(listener, service, attribute);
        return res;
    }

    @Deprecated
    public boolean registerListener(
            SContextListener listener, int service, int arg1, int arg2, int arg3) {
        SContextAttribute attribute = null;
        if (service == 35) {
            attribute = new SContextInactiveTimerAttribute(arg1, arg2, arg3, 1500, 1500);
        }
        boolean res = registerListener(listener, service, attribute);
        return res;
    }

    @Deprecated
    public boolean registerListener(
            SContextListener listener, int service, int arg1, double arg2, double arg3) {
        SContextAttribute attribute = null;
        if (service == 2) {
            attribute = new SContextPedometerAttribute(arg1, arg2, arg3);
        }
        boolean res = registerListener(listener, service, attribute);
        return res;
    }

    @Deprecated
    public boolean registerListener(
            SContextListener listener, int service, int arg1, int arg2, boolean arg3) {
        SContextAttribute attribute = null;
        if (service == 23) {
            attribute = new SContextTemperatureAlertAttribute(arg1, arg2, arg3);
        }
        boolean res = registerListener(listener, service, attribute);
        return res;
    }

    @Deprecated
    public boolean registerListener(
            SContextListener listener, int service, int arg1, int arg2, double arg3, int arg4) {
        SContextAttribute attribute = null;
        if (service == 9) {
            attribute = new SContextMovementForPositioningAttribute(arg1, arg2, arg3, arg4);
        }
        boolean res = registerListener(listener, service, attribute);
        return res;
    }

    @Deprecated
    public boolean registerListener(
            SContextListener listener, int service, int arg1, int arg2, int arg3, int arg4) {
        SContextAttribute attribute = null;
        if (service == 28) {
            attribute = new SContextSpecificPoseAlertAttribute(arg1, arg2, arg3, arg4);
        }
        boolean res = registerListener(listener, service, attribute);
        return res;
    }

    @Deprecated
    public boolean registerListener(
            SContextListener listener,
            int service,
            int arg1,
            int arg2,
            int arg3,
            int arg4,
            int arg5) {
        SContextAttribute attribute = null;
        if (service == 24) {
            attribute = new SContextActivityLocationLoggingAttribute(arg1, arg2, arg3, arg4, arg5);
        } else if (service == 35) {
            attribute = new SContextInactiveTimerAttribute(arg1, arg2, arg3, arg4, arg5);
        }
        boolean res = registerListener(listener, service, attribute);
        return res;
    }

    @Deprecated
    public void unregisterListener(SContextListener listener) {
        if (listener == null || this.mAvailableServiceMap == null) {
            return;
        }
        Iterator<Integer> i = this.mAvailableServiceMap.keySet().iterator();
        if (i.hasNext()) {
            int service = i.next().intValue();
            SContextListenerDelegate scontextListener = getListenerDelegate(listener);
            if (scontextListener != null) {
                super.unregisterListener(scontextListener, service);
                scontextListener.clear();
                this.mListenerDelegates.remove(scontextListener);
                Log.d(TAG, "  .unregisterListener : listener = " + listener);
            }
        }
    }

    @Deprecated
    public void unregisterListener(SContextListener listener, int service) {
        if (!checkListenerAndService(listener, service)) {
            return;
        }
        SContextListenerDelegate scontextListener = getListenerDelegate(listener);
        if (scontextListener == null) {
            Log.e(TAG, "  .unregisterListener : SContextListener is null!");
        } else {
            super.unregisterListener(scontextListener, service);
            Log.d(
                    TAG,
                    "  .unregisterListener : listener = "
                            + listener
                            + ", service="
                            + SContext.getServiceName(service));
        }
    }

    @Deprecated
    public void initializeSContextService(SContextListener listener, int service) {
        if (!isAvailableService(service) || service != 3) {
            return;
        }
        SContextListenerDelegate scontextListener = getListenerDelegate(listener);
        if (scontextListener == null) {
            Log.e(TAG, "  .initializeSContextService : SContextListener is null!");
        } else {
            super.initializeSemContextService(scontextListener, service);
        }
    }

    @Deprecated
    public boolean changeParameters(
            SContextListener listener, int service, SContextAttribute attribute) {
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
                && service != 53) {
            return false;
        }
        SContextListenerDelegate scontextListener = getListenerDelegate(listener);
        if (scontextListener == null) {
            Log.e(TAG, "  .changeParameters : SContextListener is null!");
            return false;
        }
        if (!super.changeParameters(scontextListener, service, attribute)) {
            return false;
        }
        return true;
    }

    @Deprecated
    public boolean changeParameters(
            SContextListener listener, int service, int arg1, int arg2, int arg3, int arg4) {
        SContextAttribute attribute = null;
        if (service == 35) {
            attribute = new SContextInactiveTimerAttribute(1, arg1, arg2, arg3, arg4);
        }
        boolean res = changeParameters(listener, service, attribute);
        return res;
    }

    @Deprecated
    public boolean changeParameters(
            SContextListener listener, int service, int arg1, double arg2, double arg3) {
        SContextAttribute attribute = null;
        if (service == 2) {
            attribute = new SContextPedometerAttribute(arg1, arg2, arg3);
        }
        boolean res = changeParameters(listener, service, attribute);
        return res;
    }

    @Deprecated
    public boolean changeParameters(SContextListener listener, int service, int arg1) {
        SContextAttribute attribute = null;
        if (service == 2) {
            attribute = new SContextPedometerAttribute(arg1);
        } else if (service == 33) {
            attribute = new SContextStepLevelMonitorAttribute(arg1);
        }
        boolean res = changeParameters(listener, service, attribute);
        return res;
    }

    @Deprecated
    public void requestToUpdate(SContextListener listener, int service) {
        if (!isAvailableService(service)) {
            return;
        }
        if (service != 2
                && service != 25
                && service != 26
                && service != 29
                && service != 40
                && service != 50
                && service != 51
                && service != 52) {
            Log.e(TAG, "  .requestToUpdate : This service is not supported!");
            return;
        }
        SContextListenerDelegate scontextListener = getListenerDelegate(listener);
        if (scontextListener == null) {
            Log.e(TAG, "  .requestToUpdate : SContextListener is null!");
        } else {
            super.requestToUpdate(scontextListener, service);
        }
    }

    @Deprecated
    public void requestHistoryData(SContextListener listener, int service) {
        if (!isAvailableService(service)) {
            return;
        }
        if (service != 2 && service != 33 && service != 26) {
            Log.e(TAG, "  .requestHistoryData : This service is not supported!");
            return;
        }
        SContextAttribute attribute = addListenerAttribute(service);
        if (!attribute.checkAttribute() || !checkListenerAndService(listener, service)) {
            return;
        }
        SContextListenerDelegate scontextListener = getListenerDelegate(listener);
        this.mIsHistoryDataListener = listener;
        if (scontextListener == null) {
            scontextListener = new SContextListenerDelegate(listener, null, true);
            this.mListenerDelegates.add(scontextListener);
        }
        super.setClientInfo(listener.toString());
        super.requestHistoryData(scontextListener, service);
    }

    @Override // com.samsung.android.hardware.context.SemContextManager
    @Deprecated
    public boolean isAvailableService(int service) {
        boolean res = super.isAvailableService(service);
        return res;
    }

    @Override // com.samsung.android.hardware.context.SemContextManager
    @Deprecated
    public int getFeatureLevel(int service) {
        if (isAvailableService(service)) {
            return super.getFeatureLevel(service);
        }
        return 0;
    }

    @Override // com.samsung.android.hardware.context.SemContextManager
    @Deprecated
    public boolean setReferenceData(int service, byte[] data1, byte[] data2) {
        SContextAttribute attribute = null;
        if (data1 == null || data2 == null) {
            return false;
        }
        if (service == 16) {
            attribute = new SContextWakeUpVoiceAttribute(data1, data2);
        }
        boolean res = setReferenceData(service, attribute);
        return res;
    }

    @Deprecated
    public boolean setReferenceData(int service, SContextAttribute attribute) {
        if (attribute == null) {
            return false;
        }
        boolean res = super.setReferenceData(service, (SemContextAttribute) attribute);
        return res;
    }

    private HashMap<Integer, Integer> getAvailableServiceMap() {
        return null;
    }

    private SContextListenerDelegate getListenerDelegate(SContextListener listener) {
        if (listener == null || this.mListenerDelegates.isEmpty()) {
            return null;
        }
        Iterator<SContextListenerDelegate> i = this.mListenerDelegates.iterator();
        while (i.hasNext()) {
            SContextListenerDelegate delegate = i.next();
            if (delegate.getListener().equals(listener)) {
                return delegate;
            }
        }
        return null;
    }

    private boolean checkListenerAndService(SContextListener listener, int service) {
        if (listener == null) {
            Log.d(TAG, "Listener is null!");
            return false;
        }
        boolean res = isAvailableService(service);
        return res;
    }

    private SContextAttribute addListenerAttribute(int service) {
        switch (service) {
            case 1:
                SContextAttribute attribute = new SContextApproachAttribute();
                return attribute;
            case 2:
                SContextAttribute attribute2 = new SContextPedometerAttribute();
                return attribute2;
            case 3:
                SContextAttribute attribute3 = new SContextStepCountAlertAttribute();
                return attribute3;
            case 6:
                SContextAttribute attribute4 = new SContextAutoRotationAttribute();
                return attribute4;
            case 8:
                SContextAttribute attribute5 = new SContextEnvironmentAttribute();
                return attribute5;
            case 9:
                SContextAttribute attribute6 = new SContextMovementForPositioningAttribute();
                return attribute6;
            case 12:
                SContextAttribute attribute7 = new SContextShakeMotionAttribute();
                return attribute7;
            case 23:
                SContextAttribute attribute8 = new SContextTemperatureAlertAttribute();
                return attribute8;
            case 24:
                SContextAttribute attribute9 = new SContextActivityLocationLoggingAttribute();
                return attribute9;
            case 27:
                SContextAttribute attribute10 = new SContextActivityNotificationAttribute();
                return attribute10;
            case 28:
                SContextAttribute attribute11 = new SContextSpecificPoseAlertAttribute();
                return attribute11;
            case 29:
                SContextAttribute attribute12 = new SContextSleepMonitorAttribute();
                return attribute12;
            case 30:
                SContextAttribute attribute13 = new SContextActivityNotificationExAttribute();
                return attribute13;
            case 33:
                SContextAttribute attribute14 = new SContextStepLevelMonitorAttribute();
                return attribute14;
            case 35:
                SContextAttribute attribute15 = new SContextInactiveTimerAttribute();
                return attribute15;
            case 36:
                SContextAttribute attribute16 = new SContextFlatMotionForTableModeAttribute();
                return attribute16;
            case 39:
                SContextAttribute attribute17 = new SContextAutoBrightnessAttribute();
                return attribute17;
            case 40:
                SContextAttribute attribute18 = new SContextExerciseAttribute();
                return attribute18;
            case 47:
                SContextAttribute attribute19 = new SContextSLocationCoreAttribute();
                return attribute19;
            case 51:
                SContextAttribute attribute20 = new SContextDevicePhysicalContextMonitorAttribute();
                return attribute20;
            case 53:
                SContextAttribute attribute21 = new SContextActivityCalibrationAttribute();
                return attribute21;
            default:
                SContextAttribute attribute22 = new SContextAttribute();
                return attribute22;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkHistoryMode(SContextEvent scontextevent) {
        boolean res = false;
        StringBuffer sb = new StringBuffer();
        int type = scontextevent.scontext.getType();
        sb.append("onSContextChanged() : event = " + SContext.getServiceName(type));
        switch (type) {
            case 2:
                SContextPedometer pedometerContext = scontextevent.getPedometerContext();
                if (pedometerContext.getMode() == 2) {
                    res = true;
                    break;
                }
                break;
            case 6:
                SContextAutoRotation autoRotation = scontextevent.getAutoRotationContext();
                sb.append(" Angle : " + autoRotation.getAngle());
                break;
            case 26:
                SContextActivityBatch activityBatchContext =
                        scontextevent.getActivityBatchContext();
                if (activityBatchContext.getMode() == 1) {
                    res = true;
                    break;
                }
                break;
            case 33:
                SContextStepLevelMonitor stepLevelMonitorContext =
                        scontextevent.getStepLevelMonitorContext();
                if (stepLevelMonitorContext.getMode() == 1) {
                    res = true;
                    break;
                }
                break;
        }
        Log.d(TAG, sb.toString());
        return res;
    }

    private class SContextListenerDelegate implements SemContextListener {
        private boolean mDereisgeredListener = false;
        private final Handler mHandler;
        private final boolean mIsHistoryData;
        private SContextListener mListener;

        SContextListenerDelegate(SContextListener listener, Looper looper, boolean isHistoryData) {
            this.mListener = listener;
            Looper mLooper = looper != null ? looper : SContextManager.this.mMainLooper;
            this.mIsHistoryData = isHistoryData;
            this.mHandler =
                    new Handler(mLooper) { // from class:
                        // android.hardware.scontext.SContextManager.SContextListenerDelegate.1
                        @Override // android.os.Handler
                        public void handleMessage(Message msg) {
                            SContextEvent scontextEvent;
                            SContext scontext;
                            if (!SContextListenerDelegate.this.mDereisgeredListener
                                    && SContextListenerDelegate.this.mListener != null
                                    && (scontextEvent = (SContextEvent) msg.obj) != null
                                    && (scontext = scontextEvent.scontext) != null) {
                                int type = scontext.getType();
                                if (SContextListenerDelegate.this.mIsHistoryData) {
                                    Log.d(
                                            SContextManager.TAG,
                                            "Data is received so remove listener related"
                                                    + " HistoryData");
                                    SContextListenerDelegate.this.mListener.onSContextChanged(
                                            scontextEvent);
                                    SContextManager.this.unregisterListener(
                                            SContextListenerDelegate.this.mListener, type);
                                } else if (!SContextManager.this.checkHistoryMode(scontextEvent)) {
                                    SContextListenerDelegate.this.mListener.onSContextChanged(
                                            scontextEvent);
                                } else if (SContextManager.this.mIsHistoryDataListener != null
                                        && SContextManager.this.mIsHistoryDataListener.equals(
                                                SContextListenerDelegate.this.mListener)) {
                                    Log.d(
                                            SContextManager.TAG,
                                            "Listener is already registered and history data is"
                                                    + " sent to Application");
                                    SContextManager.this.mIsHistoryDataListener.onSContextChanged(
                                            scontextEvent);
                                }
                            }
                        }
                    };
        }

        public SContextListener getListener() {
            return this.mListener;
        }

        public void clear() {
            this.mDereisgeredListener = true;
        }

        public String getListenerInfo() {
            if (this.mListener != null) {
                return this.mListener.toString();
            }
            return "";
        }

        @Override // com.samsung.android.hardware.context.SemContextListener
        public synchronized void onSemContextChanged(SemContextEvent semContextEvent) {
            if (semContextEvent != null) {
                int type = semContextEvent.semContext.getType();
                Bundle context = semContextEvent.context;
                Message msg = new Message();
                SContextEvent scontextEvent = new SContextEvent();
                scontextEvent.setSContextEvent(type, context);
                msg.obj = scontextEvent;
                this.mHandler.sendMessage(msg);
                notifyAll();
            }
        }
    }
}
