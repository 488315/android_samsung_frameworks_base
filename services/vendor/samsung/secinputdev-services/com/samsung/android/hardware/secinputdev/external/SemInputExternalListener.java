package com.samsung.android.hardware.secinputdev.external;

import android.content.Context;
import android.os.Handler;
import android.util.Log;

import com.samsung.android.hardware.secinputdev.SemInputFeatures;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class SemInputExternalListener implements SemInputExternal.IExternalEventRegister {
    private static final String DEX_LISTENER = "DexListenerWrapper";
    private static final String DISPLAY_LISTENER = "DisplayListenerWrapper";
    private static final String DISPLAY_STATE_LISTENER = "DisplayStateListenerWrapper";
    private static final String EXTERNAL_PACKAGE_NAME =
            "com.samsung.android.hardware.secinputdev.external.";
    private static final String FOLD_STATE_LISTENER = "FoldStateListenerWrapper";
    private static final String SEM_UEVENT_OBSERVER = "SemUEventObserverWrapper";
    private static final String SENSOR_PROX_LP_LISTENER = "SensorProxLpScanListenerWrapper";
    private static final String SENSOR_WATCH_LISTENER = "SensorListenerWatchWrapper";
    private static final String TAG = "SemInputExternalListener";
    private Context context;
    private Handler mainHandler;
    private final Map<SemInputExternal.Event, String> supportList =
            new HashMap() { // from class:
                            // com.samsung.android.hardware.secinputdev.external.SemInputExternalListener.1
                {
                    put(
                            SemInputExternal.Event.LISTENER_DISPLAY_STATE,
                            SemInputExternalListener.DISPLAY_STATE_LISTENER);
                }
            };
    private final HashMap<SemInputExternal.Event, ArrayList> registeredList = new HashMap<>();
    private StringBuilder dumpLog = new StringBuilder();
    private final SemInputExternal.IServiceListener serviceListener =
            new SemInputExternal
                    .IServiceListener() { // from class:
                                          // com.samsung.android.hardware.secinputdev.external.SemInputExternalListener.2
                @Override // com.samsung.android.hardware.secinputdev.external.SemInputExternal.IServiceListener
                public void onSemUEvent(String result) {
                    ArrayList<SemInputExternal.IServiceListener> list =
                            (ArrayList)
                                    SemInputExternalListener.this.registeredList.get(
                                            SemInputExternal.Event.OBSERVER_UEVENT);
                    if (list == null) {
                        return;
                    }
                    Iterator<SemInputExternal.IServiceListener> it = list.iterator();
                    while (it.hasNext()) {
                        SemInputExternal.IServiceListener element = it.next();
                        element.onSemUEvent(result);
                    }
                }

                @Override // com.samsung.android.hardware.secinputdev.external.SemInputExternal.IServiceListener
                public void onDisplayStateChanged(
                        boolean isEarly, int stateLogical, int statePhysical, int displayType) {
                    ArrayList<SemInputExternal.IServiceListener> list =
                            (ArrayList)
                                    SemInputExternalListener.this.registeredList.get(
                                            SemInputExternal.Event.LISTENER_DISPLAY_STATE);
                    if (list == null) {
                        return;
                    }
                    Iterator<SemInputExternal.IServiceListener> it = list.iterator();
                    while (it.hasNext()) {
                        SemInputExternal.IServiceListener element = it.next();
                        element.onDisplayStateChanged(
                                isEarly, stateLogical, statePhysical, displayType);
                    }
                }

                @Override // com.samsung.android.hardware.secinputdev.external.SemInputExternal.IServiceListener
                public void onDisplayChanged(int displayRotation) {
                    ArrayList<SemInputExternal.IServiceListener> list =
                            (ArrayList)
                                    SemInputExternalListener.this.registeredList.get(
                                            SemInputExternal.Event.LISTENER_DISPLAY);
                    if (list == null) {
                        return;
                    }
                    Iterator<SemInputExternal.IServiceListener> it = list.iterator();
                    while (it.hasNext()) {
                        SemInputExternal.IServiceListener element = it.next();
                        element.onDisplayChanged(displayRotation);
                    }
                }

                @Override // com.samsung.android.hardware.secinputdev.external.SemInputExternal.IServiceListener
                public void onFoldStateChanged(boolean folded) {
                    ArrayList<SemInputExternal.IServiceListener> list =
                            (ArrayList)
                                    SemInputExternalListener.this.registeredList.get(
                                            SemInputExternal.Event.LISTENER_FOLD_STATE);
                    if (list == null) {
                        return;
                    }
                    Iterator<SemInputExternal.IServiceListener> it = list.iterator();
                    while (it.hasNext()) {
                        SemInputExternal.IServiceListener element = it.next();
                        element.onFoldStateChanged(folded);
                    }
                }

                @Override // com.samsung.android.hardware.secinputdev.external.SemInputExternal.IServiceListener
                public void onLpScanSensorChanged(int mode) {
                    ArrayList<SemInputExternal.IServiceListener> list =
                            (ArrayList)
                                    SemInputExternalListener.this.registeredList.get(
                                            SemInputExternal.Event.LISTENER_PROX_LP_SCAN);
                    if (list == null) {
                        return;
                    }
                    Iterator<SemInputExternal.IServiceListener> it = list.iterator();
                    while (it.hasNext()) {
                        SemInputExternal.IServiceListener element = it.next();
                        element.onLpScanSensorChanged(mode);
                    }
                }

                @Override // com.samsung.android.hardware.secinputdev.external.SemInputExternal.IServiceListener
                public void onDesktopModeStateChanged(int mode) {
                    ArrayList<SemInputExternal.IServiceListener> list =
                            (ArrayList)
                                    SemInputExternalListener.this.registeredList.get(
                                            SemInputExternal.Event.LISTENER_DEX);
                    if (list == null) {
                        return;
                    }
                    Iterator<SemInputExternal.IServiceListener> it = list.iterator();
                    while (it.hasNext()) {
                        SemInputExternal.IServiceListener element = it.next();
                        element.onDesktopModeStateChanged(mode);
                    }
                }

                @Override // com.samsung.android.hardware.secinputdev.external.SemInputExternal.IServiceListener
                public void onBodyDetected(int mode) {
                    ArrayList<SemInputExternal.IServiceListener> list =
                            (ArrayList)
                                    SemInputExternalListener.this.registeredList.get(
                                            SemInputExternal.Event.LISTENER_SENSOR_WATCH);
                    if (list == null) {
                        return;
                    }
                    Iterator<SemInputExternal.IServiceListener> it = list.iterator();
                    while (it.hasNext()) {
                        SemInputExternal.IServiceListener element = it.next();
                        element.onBodyDetected(mode);
                    }
                }
            };

    public SemInputExternalListener(Context context, Handler handler) {
        this.context = null;
        this.mainHandler = null;
        this.context = context;
        this.mainHandler = handler;
        if (SemInputFeatures.IS_WEAROS && SemInputFeatures.SUPPORT_AWD) {
            this.supportList.put(
                    SemInputExternal.Event.LISTENER_SENSOR_WATCH, SENSOR_WATCH_LISTENER);
            return;
        }
        this.supportList.put(SemInputExternal.Event.LISTENER_DISPLAY, DISPLAY_LISTENER);
        this.supportList.put(SemInputExternal.Event.OBSERVER_UEVENT, SEM_UEVENT_OBSERVER);
        this.supportList.put(SemInputExternal.Event.LISTENER_FOLD_STATE, FOLD_STATE_LISTENER);
        this.supportList.put(SemInputExternal.Event.LISTENER_DEX, DEX_LISTENER);
        this.supportList.put(SemInputExternal.Event.LISTENER_PROX_LP_SCAN, SENSOR_PROX_LP_LISTENER);
    }

    public Map<SemInputExternal.Event, String> getSupportList() {
        return this.supportList;
    }

    public String getLog() {
        String log = this.dumpLog.toString();
        this.dumpLog.delete(0, this.dumpLog.length());
        return log;
    }

    @Override // com.samsung.android.hardware.secinputdev.external.SemInputExternal.IExternalEventRegister
    public boolean registerBroadcastReceiver(
            SemInputExternal.Event event, SemInputExternal.IBroadcastReceiver receiver) {
        return false;
    }

    @Override // com.samsung.android.hardware.secinputdev.external.SemInputExternal.IExternalEventRegister
    public boolean registerServiceListener(
            SemInputExternal.Event event, SemInputExternal.IServiceListener listener) {
        boolean created;
        if (!this.supportList.containsKey(event)) {
            Log.d(TAG, " Not Supported: " + event);
            return false;
        }
        ArrayList<SemInputExternal.IServiceListener> listenerList = this.registeredList.get(event);
        if (listenerList == null) {
            listenerList = new ArrayList<>();
            created = createListener(this.supportList.get(event));
        } else {
            created = true;
        }
        if (created && listenerList != null) {
            listenerList.add(listener);
        }
        if (!listenerList.isEmpty()) {
            this.registeredList.put(event, listenerList);
        }
        Log.d(TAG, "registerServiceListener: " + event + " " + created);
        return created;
    }

    private boolean createListener(String className) {
        if (className == null) {
            Log.d(TAG, "createListener: className is null");
            return false;
        }
        StringBuilder logBuffer = new StringBuilder();
        boolean result = false;
        try {
            Class classObject = Class.forName(EXTERNAL_PACKAGE_NAME + className);
            Constructor constructor =
                    classObject.getConstructor(
                            Context.class, SemInputExternal.IServiceListener.class, Handler.class);
            ExternalService instance =
                    (ExternalService)
                            constructor.newInstance(
                                    this.context, this.serviceListener, this.mainHandler);
            Method method = classObject.getMethod("register", new Class[0]);
            String ret = (String) method.invoke(instance, new Object[0]);
            if (!ret.isEmpty()) {
                logBuffer.append(className + " NOT registered: " + ret);
            }
            result = true;
        } catch (ClassNotFoundException e) {
            logBuffer.append(e.toString());
        } catch (InvocationTargetException e2) {
            Throwable throwable = e2.getCause();
            logBuffer.append("Exception at " + className + ": " + throwable.toString());
        } catch (Exception e3) {
            logBuffer.append("Exception at " + className + ": " + e3);
        }
        if (logBuffer.length() != 0) {
            Log.d(TAG, logBuffer.toString());
            this.dumpLog.append("- " + logBuffer.toString() + "\n");
        }
        return result;
    }
}
