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

    private HashMap<Integer, Integer> getAvailableServiceMap() {
        return null;
    }

    @Deprecated
    public SContextManager(Looper looper) {
        super(looper);
        this.mListenerDelegates = new CopyOnWriteArrayList<>();
        this.mAvailableServiceMap = null;
        this.mIsHistoryDataListener = null;
        this.mMainLooper = looper;
    }

    @Deprecated
    public SContextManager(Context context, Looper looper) {
        super(context, looper);
        this.mListenerDelegates = new CopyOnWriteArrayList<>();
        this.mAvailableServiceMap = null;
        this.mIsHistoryDataListener = null;
        this.mMainLooper = looper;
    }

    @Deprecated
    public boolean registerListener(SContextListener sContextListener, int i) {
        return registerListener(sContextListener, i, addListenerAttribute(i));
    }

    @Deprecated
    public boolean registerListener(SContextListener sContextListener, int i, Looper looper) {
        return registerListener(sContextListener, i, addListenerAttribute(i), looper);
    }

    @Deprecated
    public boolean registerListener(SContextListener sContextListener, int i, SContextAttribute sContextAttribute) {
        if (i == 48) {
            return setReferenceData(i, sContextAttribute);
        }
        if (sContextAttribute == null || !sContextAttribute.checkAttribute() || !checkListenerAndService(sContextListener, i)) {
            return false;
        }
        SContextListenerDelegate listenerDelegate = getListenerDelegate(sContextListener);
        if (listenerDelegate == null) {
            listenerDelegate = new SContextListenerDelegate(sContextListener, null, false);
            this.mListenerDelegates.add(listenerDelegate);
        }
        super.setClientInfo(sContextListener.toString());
        if (!super.registerListener(listenerDelegate, i, sContextAttribute)) {
            return false;
        }
        Log.d(TAG, "  .registerListener : listener = " + sContextListener + ", service=" + SContext.getServiceName(i));
        return true;
    }

    @Deprecated
    public boolean registerListener(SContextListener sContextListener, int i, SContextAttribute sContextAttribute, Looper looper) {
        if (sContextAttribute == null || !sContextAttribute.checkAttribute() || !checkListenerAndService(sContextListener, i)) {
            return false;
        }
        SContextListenerDelegate listenerDelegate = getListenerDelegate(sContextListener);
        if (listenerDelegate == null) {
            listenerDelegate = new SContextListenerDelegate(sContextListener, looper, false);
            this.mListenerDelegates.add(listenerDelegate);
        }
        super.setClientInfo(sContextListener.toString());
        if (!super.registerListener(listenerDelegate, i, sContextAttribute, looper)) {
            return false;
        }
        Log.d(TAG, "  .registerListener : listener = " + sContextListener + ", service=" + SContext.getServiceName(i));
        return true;
    }

    @Deprecated
    public boolean registerListener(SContextListener sContextListener, int i, int i2) {
        SContextAttribute sContextFlatMotionForTableModeAttribute;
        if (i == 3) {
            sContextFlatMotionForTableModeAttribute = new SContextStepCountAlertAttribute(i2);
        } else if (i == 6) {
            sContextFlatMotionForTableModeAttribute = new SContextAutoRotationAttribute(i2);
        } else if (i == 16) {
            sContextFlatMotionForTableModeAttribute = new SContextWakeUpVoiceAttribute(i2);
        } else if (i == 33) {
            sContextFlatMotionForTableModeAttribute = new SContextStepLevelMonitorAttribute(i2);
        } else {
            sContextFlatMotionForTableModeAttribute = i == 36 ? new SContextFlatMotionForTableModeAttribute(i2) : null;
        }
        return registerListener(sContextListener, i, sContextFlatMotionForTableModeAttribute);
    }

    @Deprecated
    public boolean registerListener(SContextListener sContextListener, int i, int[] iArr) {
        return registerListener(sContextListener, i, i == 27 ? new SContextActivityNotificationAttribute(iArr) : null);
    }

    @Deprecated
    public boolean registerListener(SContextListener sContextListener, int i, int[] iArr, int i2) {
        return registerListener(sContextListener, i, i == 30 ? new SContextActivityNotificationExAttribute(iArr, i2) : null);
    }

    @Deprecated
    public boolean registerListener(SContextListener sContextListener, int i, int i2, int i3) {
        SContextAttribute sContextSleepMonitorAttribute;
        if (i == 8) {
            sContextSleepMonitorAttribute = new SContextEnvironmentAttribute(i2, i3);
        } else if (i == 12) {
            sContextSleepMonitorAttribute = new SContextShakeMotionAttribute(i2, i3);
        } else {
            sContextSleepMonitorAttribute = i == 29 ? new SContextSleepMonitorAttribute(i2, i3) : null;
        }
        return registerListener(sContextListener, i, sContextSleepMonitorAttribute);
    }

    @Deprecated
    public boolean registerListener(SContextListener sContextListener, int i, int i2, int i3, int i4) {
        return registerListener(sContextListener, i, i == 35 ? new SContextInactiveTimerAttribute(i2, i3, i4, 1500, 1500) : null);
    }

    @Deprecated
    public boolean registerListener(SContextListener sContextListener, int i, int i2, double d, double d2) {
        return registerListener(sContextListener, i, i == 2 ? new SContextPedometerAttribute(i2, d, d2) : null);
    }

    @Deprecated
    public boolean registerListener(SContextListener sContextListener, int i, int i2, int i3, boolean z) {
        return registerListener(sContextListener, i, i == 23 ? new SContextTemperatureAlertAttribute(i2, i3, z) : null);
    }

    @Deprecated
    public boolean registerListener(SContextListener sContextListener, int i, int i2, int i3, double d, int i4) {
        return registerListener(sContextListener, i, i == 9 ? new SContextMovementForPositioningAttribute(i2, i3, d, i4) : null);
    }

    @Deprecated
    public boolean registerListener(SContextListener sContextListener, int i, int i2, int i3, int i4, int i5) {
        return registerListener(sContextListener, i, i == 28 ? new SContextSpecificPoseAlertAttribute(i2, i3, i4, i5) : null);
    }

    @Deprecated
    public boolean registerListener(SContextListener sContextListener, int i, int i2, int i3, int i4, int i5, int i6) {
        SContextAttribute sContextInactiveTimerAttribute;
        if (i == 24) {
            sContextInactiveTimerAttribute = new SContextActivityLocationLoggingAttribute(i2, i3, i4, i5, i6);
        } else {
            sContextInactiveTimerAttribute = i == 35 ? new SContextInactiveTimerAttribute(i2, i3, i4, i5, i6) : null;
        }
        return registerListener(sContextListener, i, sContextInactiveTimerAttribute);
    }

    @Deprecated
    public void unregisterListener(SContextListener sContextListener) {
        HashMap<Integer, Integer> map;
        if (sContextListener == null || (map = this.mAvailableServiceMap) == null) {
            return;
        }
        Iterator<Integer> it = map.keySet().iterator();
        if (it.hasNext()) {
            int iIntValue = it.next().intValue();
            SContextListenerDelegate listenerDelegate = getListenerDelegate(sContextListener);
            if (listenerDelegate == null) {
                return;
            }
            super.unregisterListener(listenerDelegate, iIntValue);
            listenerDelegate.clear();
            this.mListenerDelegates.remove(listenerDelegate);
            Log.d(TAG, "  .unregisterListener : listener = " + sContextListener);
        }
    }

    @Deprecated
    public void unregisterListener(SContextListener sContextListener, int i) {
        if (checkListenerAndService(sContextListener, i)) {
            SContextListenerDelegate listenerDelegate = getListenerDelegate(sContextListener);
            if (listenerDelegate == null) {
                Log.e(TAG, "  .unregisterListener : SContextListener is null!");
                return;
            }
            super.unregisterListener(listenerDelegate, i);
            Log.d(TAG, "  .unregisterListener : listener = " + sContextListener + ", service=" + SContext.getServiceName(i));
        }
    }

    @Deprecated
    public void initializeSContextService(SContextListener sContextListener, int i) {
        if (isAvailableService(i) && i == 3) {
            SContextListenerDelegate listenerDelegate = getListenerDelegate(sContextListener);
            if (listenerDelegate == null) {
                Log.e(TAG, "  .initializeSContextService : SContextListener is null!");
            } else {
                super.initializeSemContextService(listenerDelegate, i);
            }
        }
    }

    @Deprecated
    public boolean changeParameters(SContextListener sContextListener, int i, SContextAttribute sContextAttribute) {
        if (sContextAttribute == null || !sContextAttribute.checkAttribute() || !checkListenerAndService(sContextListener, i)) {
            return false;
        }
        if (i != 1 && i != 2 && i != 33 && i != 35 && i != 39 && i != 47 && i != 51 && i != 53) {
            return false;
        }
        SContextListenerDelegate listenerDelegate = getListenerDelegate(sContextListener);
        if (listenerDelegate != null) {
            return super.changeParameters(listenerDelegate, i, sContextAttribute);
        }
        Log.e(TAG, "  .changeParameters : SContextListener is null!");
        return false;
    }

    @Deprecated
    public boolean changeParameters(SContextListener sContextListener, int i, int i2, int i3, int i4, int i5) {
        return changeParameters(sContextListener, i, i == 35 ? new SContextInactiveTimerAttribute(1, i2, i3, i4, i5) : null);
    }

    @Deprecated
    public boolean changeParameters(SContextListener sContextListener, int i, int i2, double d, double d2) {
        return changeParameters(sContextListener, i, i == 2 ? new SContextPedometerAttribute(i2, d, d2) : null);
    }

    @Deprecated
    public boolean changeParameters(SContextListener sContextListener, int i, int i2) {
        SContextAttribute sContextStepLevelMonitorAttribute;
        if (i == 2) {
            sContextStepLevelMonitorAttribute = new SContextPedometerAttribute(i2);
        } else {
            sContextStepLevelMonitorAttribute = i == 33 ? new SContextStepLevelMonitorAttribute(i2) : null;
        }
        return changeParameters(sContextListener, i, sContextStepLevelMonitorAttribute);
    }

    @Deprecated
    public void requestToUpdate(SContextListener sContextListener, int i) {
        if (isAvailableService(i)) {
            if (i != 2 && i != 25 && i != 26 && i != 29 && i != 40 && i != 50 && i != 51 && i != 52) {
                Log.e(TAG, "  .requestToUpdate : This service is not supported!");
                return;
            }
            SContextListenerDelegate listenerDelegate = getListenerDelegate(sContextListener);
            if (listenerDelegate == null) {
                Log.e(TAG, "  .requestToUpdate : SContextListener is null!");
            } else {
                super.requestToUpdate(listenerDelegate, i);
            }
        }
    }

    @Deprecated
    public void requestHistoryData(SContextListener sContextListener, int i) {
        if (isAvailableService(i)) {
            if (i != 2 && i != 33 && i != 26) {
                Log.e(TAG, "  .requestHistoryData : This service is not supported!");
                return;
            }
            if (addListenerAttribute(i).checkAttribute() && checkListenerAndService(sContextListener, i)) {
                SContextListenerDelegate listenerDelegate = getListenerDelegate(sContextListener);
                this.mIsHistoryDataListener = sContextListener;
                if (listenerDelegate == null) {
                    listenerDelegate = new SContextListenerDelegate(sContextListener, null, true);
                    this.mListenerDelegates.add(listenerDelegate);
                }
                super.setClientInfo(sContextListener.toString());
                super.requestHistoryData(listenerDelegate, i);
            }
        }
    }

    @Override // com.samsung.android.hardware.context.SemContextManager
    @Deprecated
    public boolean isAvailableService(int i) {
        return super.isAvailableService(i);
    }

    @Override // com.samsung.android.hardware.context.SemContextManager
    @Deprecated
    public int getFeatureLevel(int i) {
        if (isAvailableService(i)) {
            return super.getFeatureLevel(i);
        }
        return 0;
    }

    @Override // com.samsung.android.hardware.context.SemContextManager
    @Deprecated
    public boolean setReferenceData(int i, byte[] bArr, byte[] bArr2) {
        if (bArr == null || bArr2 == null) {
            return false;
        }
        return setReferenceData(i, (SContextAttribute) (i == 16 ? new SContextWakeUpVoiceAttribute(bArr, bArr2) : null));
    }

    @Deprecated
    public boolean setReferenceData(int i, SContextAttribute sContextAttribute) {
        if (sContextAttribute == null) {
            return false;
        }
        return super.setReferenceData(i, (SemContextAttribute) sContextAttribute);
    }

    private SContextListenerDelegate getListenerDelegate(SContextListener sContextListener) {
        if (sContextListener != null && !this.mListenerDelegates.isEmpty()) {
            Iterator<SContextListenerDelegate> it = this.mListenerDelegates.iterator();
            while (it.hasNext()) {
                SContextListenerDelegate next = it.next();
                if (next.getListener().equals(sContextListener)) {
                    return next;
                }
            }
        }
        return null;
    }

    private boolean checkListenerAndService(SContextListener sContextListener, int i) {
        if (sContextListener == null) {
            Log.d(TAG, "Listener is null!");
            return false;
        }
        return isAvailableService(i);
    }

    private SContextAttribute addListenerAttribute(int i) {
        if (i == 1) {
            return new SContextApproachAttribute();
        }
        if (i == 2) {
            return new SContextPedometerAttribute();
        }
        if (i == 3) {
            return new SContextStepCountAlertAttribute();
        }
        if (i == 6) {
            return new SContextAutoRotationAttribute();
        }
        if (i == 12) {
            return new SContextShakeMotionAttribute();
        }
        if (i == 33) {
            return new SContextStepLevelMonitorAttribute();
        }
        if (i == 47) {
            return new SContextSLocationCoreAttribute();
        }
        if (i == 51) {
            return new SContextDevicePhysicalContextMonitorAttribute();
        }
        if (i == 53) {
            return new SContextActivityCalibrationAttribute();
        }
        if (i == 8) {
            return new SContextEnvironmentAttribute();
        }
        if (i == 9) {
            return new SContextMovementForPositioningAttribute();
        }
        if (i == 23) {
            return new SContextTemperatureAlertAttribute();
        }
        if (i == 24) {
            return new SContextActivityLocationLoggingAttribute();
        }
        if (i == 35) {
            return new SContextInactiveTimerAttribute();
        }
        if (i == 36) {
            return new SContextFlatMotionForTableModeAttribute();
        }
        if (i == 39) {
            return new SContextAutoBrightnessAttribute();
        }
        if (i != 40) {
            switch (i) {
                case 27:
                    return new SContextActivityNotificationAttribute();
                case 28:
                    return new SContextSpecificPoseAlertAttribute();
                case 29:
                    return new SContextSleepMonitorAttribute();
                case 30:
                    return new SContextActivityNotificationExAttribute();
                default:
                    return new SContextAttribute();
            }
        }
        return new SContextExerciseAttribute();
    }

    /* JADX INFO: Access modifiers changed from: private */
    public boolean checkHistoryMode(SContextEvent sContextEvent) {
        StringBuffer stringBuffer = new StringBuffer();
        int type = sContextEvent.scontext.getType();
        stringBuffer.append("onSContextChanged() : event = " + SContext.getServiceName(type));
        boolean z = true;
        if (type != 2) {
            if (type != 6) {
                if (type == 26 ? sContextEvent.getActivityBatchContext().getMode() != 1 : type != 33 || sContextEvent.getStepLevelMonitorContext().getMode() != 1) {
                }
            } else {
                stringBuffer.append(" Angle : " + sContextEvent.getAutoRotationContext().getAngle());
            }
            z = false;
        } else if (sContextEvent.getPedometerContext().getMode() != 2) {
            z = false;
        }
        Log.d(TAG, stringBuffer.toString());
        return z;
    }

    private class SContextListenerDelegate implements SemContextListener {
        private boolean mDereisgeredListener = false;
        private final Handler mHandler;
        private final boolean mIsHistoryData;
        private SContextListener mListener;

        SContextListenerDelegate(SContextListener sContextListener, Looper looper, boolean z) {
            this.mListener = sContextListener;
            looper = looper == null ? SContextManager.this.mMainLooper : looper;
            this.mIsHistoryData = z;
            this.mHandler = new Handler(looper) { // from class: android.hardware.scontext.SContextManager.SContextListenerDelegate.1
                @Override // android.os.Handler
                public void handleMessage(Message message) {
                    SContextEvent sContextEvent;
                    SContext sContext;
                    if (SContextListenerDelegate.this.mDereisgeredListener || SContextListenerDelegate.this.mListener == null || (sContextEvent = (SContextEvent) message.obj) == null || (sContext = sContextEvent.scontext) == null) {
                        return;
                    }
                    int type = sContext.getType();
                    if (SContextListenerDelegate.this.mIsHistoryData) {
                        Log.d(SContextManager.TAG, "Data is received so remove listener related HistoryData");
                        SContextListenerDelegate.this.mListener.onSContextChanged(sContextEvent);
                        SContextManager.this.unregisterListener(SContextListenerDelegate.this.mListener, type);
                    } else if (!SContextManager.this.checkHistoryMode(sContextEvent)) {
                        SContextListenerDelegate.this.mListener.onSContextChanged(sContextEvent);
                    } else {
                        if (SContextManager.this.mIsHistoryDataListener == null || !SContextManager.this.mIsHistoryDataListener.equals(SContextListenerDelegate.this.mListener)) {
                            return;
                        }
                        Log.d(SContextManager.TAG, "Listener is already registered and history data is sent to Application");
                        SContextManager.this.mIsHistoryDataListener.onSContextChanged(sContextEvent);
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
            SContextListener sContextListener = this.mListener;
            if (sContextListener != null) {
                return sContextListener.toString();
            }
            return "";
        }

        @Override // com.samsung.android.hardware.context.SemContextListener
        public synchronized void onSemContextChanged(SemContextEvent semContextEvent) {
            if (semContextEvent != null) {
                int type = semContextEvent.semContext.getType();
                Bundle bundle = semContextEvent.context;
                Message message = new Message();
                SContextEvent sContextEvent = new SContextEvent();
                sContextEvent.setSContextEvent(type, bundle);
                message.obj = sContextEvent;
                this.mHandler.sendMessage(message);
                notifyAll();
            }
        }
    }
}
