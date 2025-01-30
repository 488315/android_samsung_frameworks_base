package com.android.server.knox.dar.ddar.fsm;

import android.content.Context;
import com.android.server.knox.dar.ddar.DDLog;
import com.samsung.android.knox.dar.ddar.fsm.Event;
import com.samsung.android.knox.dar.ddar.fsm.State;
import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class StateMachineImpl {
    public static Map validTransitions = new HashMap();
    public Context context;
    public Map currentStateMap = new HashMap();
    public Map previousStateMap = new HashMap();
    public List stateChangeListeners = new ArrayList();
    public Object stateLock = new Object();
    public Object listenerLock = new Object();

    public interface StateChangeListener {
        void onStateChanged(State state, State state2, Event event, int i);
    }

    static {
        EnumMap enumMap = new EnumMap(Event.class);
        enumMap.put((EnumMap) Event.DDAR_WORKSPACE_CREATED, (Event) State.DEVICE_UNLOCK_DATA_UNLOCK);
        enumMap.put((EnumMap) Event.DDAR_WORKSPACE_REMOVED, (Event) State.IDLE);
        validTransitions.put(State.IDLE, enumMap);
        EnumMap enumMap2 = new EnumMap(Event.class);
        enumMap2.put((EnumMap) Event.DEVICE_LOCKED, (Event) State.DEVICE_LOCK_DATA_UNLOCK);
        enumMap2.put((EnumMap) Event.DATALOCK_TIMEOUT, (Event) State.DEVICE_UNLOCK_DATA_LOCK);
        enumMap2.put((EnumMap) Event.DDAR_WORKSPACE_REMOVED, (Event) State.IDLE);
        validTransitions.put(State.DEVICE_UNLOCK_DATA_UNLOCK, enumMap2);
        EnumMap enumMap3 = new EnumMap(Event.class);
        enumMap3.put((EnumMap) Event.DEVICE_AUTH_SUCCESS, (Event) State.DEVICE_UNLOCK_DATA_UNLOCK);
        enumMap3.put((EnumMap) Event.DATALOCK_TIMEOUT, (Event) State.DEVICE_LOCK_DATA_LOCK);
        enumMap3.put((EnumMap) Event.DDAR_WORKSPACE_REMOVED, (Event) State.IDLE);
        validTransitions.put(State.DEVICE_LOCK_DATA_UNLOCK, enumMap3);
        EnumMap enumMap4 = new EnumMap(Event.class);
        enumMap4.put((EnumMap) Event.DEVICE_AUTH_SUCCESS, (Event) State.DEVICE_UNLOCK_DATA_LOCK);
        enumMap4.put((EnumMap) Event.DDAR_WORKSPACE_REMOVED, (Event) State.IDLE);
        validTransitions.put(State.DEVICE_LOCK_DATA_LOCK, enumMap4);
        EnumMap enumMap5 = new EnumMap(Event.class);
        enumMap5.put((EnumMap) Event.DDAR_WORKSPACE_AUTH_SUCCESS, (Event) State.DEVICE_UNLOCK_DATA_UNLOCK);
        enumMap5.put((EnumMap) Event.DEVICE_AUTH_SUCCESS, (Event) State.DEVICE_UNLOCK_DATA_LOCK);
        enumMap5.put((EnumMap) Event.DEVICE_LOCKED, (Event) State.DEVICE_LOCK_DATA_LOCK);
        enumMap5.put((EnumMap) Event.DDAR_WORKSPACE_REMOVED, (Event) State.IDLE);
        validTransitions.put(State.DEVICE_UNLOCK_DATA_LOCK, enumMap5);
    }

    public StateMachineImpl(Context context) {
        this.context = context;
    }

    public void setInitialState(int i, State state) {
        DDLog.m33d("DualDAR::StateMachine", "setInitialState() initialState: " + state + " for user " + i, new Object[0]);
        synchronized (this.stateLock) {
            this.currentStateMap.put(Integer.valueOf(i), state);
            this.previousStateMap.put(Integer.valueOf(i), null);
        }
    }

    public void addStateChangeListener(StateChangeListener stateChangeListener) {
        synchronized (this.listenerLock) {
            if (!this.stateChangeListeners.contains(stateChangeListener)) {
                this.stateChangeListeners.add(stateChangeListener);
            }
        }
    }

    public boolean processEvent(Event event, int i) {
        State currentState;
        boolean z;
        State state;
        synchronized (this.stateLock) {
            currentState = getCurrentState(i);
            Map map = (Map) validTransitions.get(currentState);
            z = false;
            if (event != null && map.containsKey(event)) {
                state = (State) map.get(event);
                this.previousStateMap.put(Integer.valueOf(i), currentState);
                this.currentStateMap.put(Integer.valueOf(i), state);
                DDLog.m33d("DualDAR::StateMachine", "Transition: (" + currentState + ") --> (" + state + ") because (" + event + ") for user " + i, new Object[0]);
                z = true;
            } else {
                DDLog.m34e("DualDAR::StateMachine", "Invalid event " + event + " in state: " + currentState + " for user " + i, new Object[0]);
                state = null;
            }
            if (state == State.IDLE) {
                this.currentStateMap.remove(Integer.valueOf(i));
                this.previousStateMap.remove(Integer.valueOf(i));
            }
        }
        if (z) {
            synchronized (this.listenerLock) {
                Iterator it = this.stateChangeListeners.iterator();
                while (it.hasNext()) {
                    ((StateChangeListener) it.next()).onStateChanged(currentState, state, event, i);
                }
            }
        }
        return z;
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:20:0x0015
        	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1179)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
        */
    public com.samsung.android.knox.dar.ddar.fsm.State getCurrentState(int r3) {
        /*
            r2 = this;
            r0 = 0
            java.lang.Object r1 = r2.stateLock     // Catch: java.lang.Exception -> L18
            monitor-enter(r1)     // Catch: java.lang.Exception -> L18
            java.util.Map r2 = r2.currentStateMap     // Catch: java.lang.Throwable -> L15
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch: java.lang.Throwable -> L15
            java.lang.Object r2 = r2.get(r3)     // Catch: java.lang.Throwable -> L15
            com.samsung.android.knox.dar.ddar.fsm.State r2 = (com.samsung.android.knox.dar.ddar.fsm.State) r2     // Catch: java.lang.Throwable -> L15
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L12
            goto L19
        L12:
            r3 = move-exception
            r0 = r2
            goto L16
        L15:
            r3 = move-exception
        L16:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L15
            throw r3     // Catch: java.lang.Exception -> L18
        L18:
            r2 = r0
        L19:
            if (r2 != 0) goto L1d
            com.samsung.android.knox.dar.ddar.fsm.State r2 = com.samsung.android.knox.dar.ddar.fsm.State.IDLE
        L1d:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.knox.dar.ddar.fsm.StateMachineImpl.getCurrentState(int):com.samsung.android.knox.dar.ddar.fsm.State");
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:20:0x0015
        	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1179)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
        */
    public com.samsung.android.knox.dar.ddar.fsm.State getPreviousState(int r3) {
        /*
            r2 = this;
            r0 = 0
            java.lang.Object r1 = r2.stateLock     // Catch: java.lang.Exception -> L18
            monitor-enter(r1)     // Catch: java.lang.Exception -> L18
            java.util.Map r2 = r2.previousStateMap     // Catch: java.lang.Throwable -> L15
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch: java.lang.Throwable -> L15
            java.lang.Object r2 = r2.get(r3)     // Catch: java.lang.Throwable -> L15
            com.samsung.android.knox.dar.ddar.fsm.State r2 = (com.samsung.android.knox.dar.ddar.fsm.State) r2     // Catch: java.lang.Throwable -> L15
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L12
            goto L19
        L12:
            r3 = move-exception
            r0 = r2
            goto L16
        L15:
            r3 = move-exception
        L16:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L15
            throw r3     // Catch: java.lang.Exception -> L18
        L18:
            r2 = r0
        L19:
            if (r2 != 0) goto L1d
            com.samsung.android.knox.dar.ddar.fsm.State r2 = com.samsung.android.knox.dar.ddar.fsm.State.IDLE
        L1d:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.knox.dar.ddar.fsm.StateMachineImpl.getPreviousState(int):com.samsung.android.knox.dar.ddar.fsm.State");
    }
}
