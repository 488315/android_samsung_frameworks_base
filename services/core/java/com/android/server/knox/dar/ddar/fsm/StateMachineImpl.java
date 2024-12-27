package com.android.server.knox.dar.ddar.fsm;

import com.samsung.android.knox.dar.ddar.fsm.Event;
import com.samsung.android.knox.dar.ddar.fsm.State;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class StateMachineImpl {
    public static final Map validTransitions;
    public Map currentStateMap;
    public Object listenerLock;
    public Map previousStateMap;
    public List stateChangeListeners;
    public Object stateLock;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface StateChangeListener {
    }

    static {
        HashMap hashMap = new HashMap();
        validTransitions = hashMap;
        EnumMap enumMap = new EnumMap(Event.class);
        Event event = Event.DDAR_WORKSPACE_CREATED;
        State state = State.DEVICE_UNLOCK_DATA_UNLOCK;
        enumMap.put((EnumMap) event, (Event) state);
        Event event2 = Event.DDAR_WORKSPACE_REMOVED;
        State state2 = State.IDLE;
        enumMap.put((EnumMap) event2, (Event) state2);
        hashMap.put(state2, enumMap);
        EnumMap enumMap2 = new EnumMap(Event.class);
        Event event3 = Event.DEVICE_LOCKED;
        State state3 = State.DEVICE_LOCK_DATA_UNLOCK;
        enumMap2.put((EnumMap) event3, (Event) state3);
        Event event4 = Event.DATALOCK_TIMEOUT;
        State state4 = State.DEVICE_UNLOCK_DATA_LOCK;
        enumMap2.put((EnumMap) event4, (Event) state4);
        enumMap2.put((EnumMap) event2, (Event) state2);
        hashMap.put(state, enumMap2);
        EnumMap enumMap3 = new EnumMap(Event.class);
        Event event5 = Event.DEVICE_AUTH_SUCCESS;
        enumMap3.put((EnumMap) event5, (Event) state);
        State state5 = State.DEVICE_LOCK_DATA_LOCK;
        enumMap3.put((EnumMap) event4, (Event) state5);
        enumMap3.put((EnumMap) event2, (Event) state2);
        hashMap.put(state3, enumMap3);
        EnumMap enumMap4 = new EnumMap(Event.class);
        enumMap4.put((EnumMap) event5, (Event) state4);
        enumMap4.put((EnumMap) event2, (Event) state2);
        hashMap.put(state5, enumMap4);
        EnumMap enumMap5 = new EnumMap(Event.class);
        enumMap5.put((EnumMap) Event.DDAR_WORKSPACE_AUTH_SUCCESS, (Event) state);
        enumMap5.put((EnumMap) event5, (Event) state4);
        enumMap5.put((EnumMap) event3, (Event) state5);
        enumMap5.put((EnumMap) event2, (Event) state2);
        hashMap.put(state4, enumMap5);
    }

    /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
        jadx.core.utils.exceptions.JadxRuntimeException: Can't find top splitter block for handler:B:23:0x0017
        	at jadx.core.utils.BlockUtils.getTopSplitterForHandler(BlockUtils.java:1179)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.collectHandlerRegions(ExcHandlersRegionMaker.java:53)
        	at jadx.core.dex.visitors.regions.maker.ExcHandlersRegionMaker.process(ExcHandlersRegionMaker.java:38)
        	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:27)
        */
    public final com.samsung.android.knox.dar.ddar.fsm.State getCurrentState(int r3) {
        /*
            r2 = this;
            r0 = 0
            java.lang.Object r1 = r2.stateLock     // Catch: java.lang.Exception -> L1d
            monitor-enter(r1)     // Catch: java.lang.Exception -> L1d
            java.util.Map r2 = r2.currentStateMap     // Catch: java.lang.Throwable -> L17
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)     // Catch: java.lang.Throwable -> L17
            java.util.HashMap r2 = (java.util.HashMap) r2     // Catch: java.lang.Throwable -> L19
            java.lang.Object r2 = r2.get(r3)     // Catch: java.lang.Throwable -> L19
            com.samsung.android.knox.dar.ddar.fsm.State r2 = (com.samsung.android.knox.dar.ddar.fsm.State) r2     // Catch: java.lang.Throwable -> L17
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L14
            goto L1e
        L14:
            r3 = move-exception
            r0 = r2
            goto L1b
        L17:
            r3 = move-exception
            goto L1b
        L19:
            r2 = move-exception
            r3 = r2
        L1b:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L17
            throw r3     // Catch: java.lang.Exception -> L1d
        L1d:
            r2 = r0
        L1e:
            if (r2 != 0) goto L22
            com.samsung.android.knox.dar.ddar.fsm.State r2 = com.samsung.android.knox.dar.ddar.fsm.State.IDLE
        L22:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.knox.dar.ddar.fsm.StateMachineImpl.getCurrentState(int):com.samsung.android.knox.dar.ddar.fsm.State");
    }
}
