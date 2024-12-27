package com.android.server.knox.dar.ddar.fsm;

import android.content.Context;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.os.Bundle;
import android.os.Process;
import android.os.UserHandle;

import com.android.server.knox.dar.ddar.DDLog;
import com.android.server.pm.PersonaServiceHelper;

import com.samsung.android.knox.dar.ddar.DualDARController;
import com.samsung.android.knox.dar.ddar.fsm.Event;
import com.samsung.android.knox.dar.ddar.fsm.State;
import com.samsung.android.knox.dar.ddar.proxy.IProxyAgentService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class StateMachineProxy extends IProxyAgentService
        implements StateMachineImpl.StateChangeListener {
    public static StateMachineProxy mInstance;
    public final Context context;
    public boolean initiateState = false;
    public final StateMachineImpl stateMachine;

    public StateMachineProxy(Context context) {
        this.context = context;
        StateMachineImpl stateMachineImpl = new StateMachineImpl();
        stateMachineImpl.currentStateMap = new HashMap();
        stateMachineImpl.previousStateMap = new HashMap();
        ArrayList arrayList = new ArrayList();
        stateMachineImpl.stateChangeListeners = arrayList;
        stateMachineImpl.stateLock = new Object();
        Object obj = new Object();
        stateMachineImpl.listenerLock = obj;
        this.stateMachine = stateMachineImpl;
        setInitialState();
        synchronized (obj) {
            if (!arrayList.contains(this)) {
                arrayList.add(this);
            }
        }
    }

    public static void enforceCallingUser(int i) {
        if (UserHandle.getAppId(i) != 5250
                && UserHandle.getAppId(i) != 1000
                && UserHandle.getAppId(i) != Process.myUid()) {
            throw new SecurityException(
                    VibrationParam$1$$ExternalSyntheticOutline0.m(
                            i, "Can only be called by system user. callingUid: "));
        }
    }

    public final Bundle onMessage(int i, String str, Bundle bundle) {
        char c;
        boolean z;
        try {
            Bundle bundle2 = new Bundle();
            switch (str.hashCode()) {
                case 53895362:
                    if (str.equals("GET_CURRENT_STATE")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 1012702809:
                    if (str.equals("SET_INITIAL_STATE")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 1389252746:
                    if (str.equals("PROCESS_EVENT")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 1725058386:
                    if (str.equals("GET_PREVIOUS_STATE")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            if (c == 0) {
                enforceCallingUser(i);
                bundle2.putBoolean("dual_dar_response", setInitialState());
            } else if (c == 1) {
                enforceCallingUser(i);
                bundle2.putBoolean("dual_dar_response", processEvent(bundle));
            } else if (c == 2) {
                State currentState =
                        this.stateMachine.getCurrentState(bundle.getInt("KEY_DUAL_DAR_USER_ID"));
                z = currentState != null;
                bundle2.putString("KEY_STATE", currentState.name());
                bundle2.putBoolean("dual_dar_response", z);
            } else if (c == 3) {
                int i2 = bundle.getInt("KEY_DUAL_DAR_USER_ID");
                State state = this.stateMachine;
                state.getClass();
                try {
                    try {
                    } catch (Throwable th) {
                        th = th;
                    }
                } catch (Exception unused) {
                    state = null;
                }
                synchronized (state.stateLock) {
                    try {
                        try {
                            state =
                                    (State)
                                            ((HashMap) state.previousStateMap)
                                                    .get(Integer.valueOf(i2));
                            if (state == null) {
                                state = State.IDLE;
                            }
                            z = state != null;
                            bundle2.putString("KEY_STATE", state.name());
                            bundle2.putBoolean("dual_dar_response", z);
                        } catch (Throwable th2) {
                            th = th2;
                            state = null;
                            try {
                                throw th;
                            } catch (Exception unused2) {
                            }
                        }
                    } catch (Throwable th3) {
                        th = th3;
                    }
                }
            }
            return bundle2;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public final boolean processEvent(Bundle bundle) {
        State currentState;
        State state;
        boolean z = false;
        if (!bundle.containsKey("KEY_EVENT") || !bundle.containsKey("KEY_DUAL_DAR_USER_ID")) {
            return false;
        }
        Event valueOf = Event.valueOf(bundle.getString("KEY_EVENT"));
        int i = bundle.getInt("KEY_DUAL_DAR_USER_ID");
        StateMachineImpl stateMachineImpl = this.stateMachine;
        synchronized (stateMachineImpl.stateLock) {
            try {
                currentState = stateMachineImpl.getCurrentState(i);
                Map map = (Map) ((HashMap) StateMachineImpl.validTransitions).get(currentState);
                if (valueOf == null || !map.containsKey(valueOf)) {
                    DDLog.e(
                            "DualDAR::StateMachine",
                            "Invalid event "
                                    + valueOf
                                    + " in state: "
                                    + currentState
                                    + " for user "
                                    + i,
                            new Object[0]);
                    state = null;
                } else {
                    state = (State) map.get(valueOf);
                    ((HashMap) stateMachineImpl.previousStateMap)
                            .put(Integer.valueOf(i), currentState);
                    ((HashMap) stateMachineImpl.currentStateMap).put(Integer.valueOf(i), state);
                    DDLog.d(
                            "DualDAR::StateMachine",
                            "Transition: ("
                                    + currentState
                                    + ") --> ("
                                    + state
                                    + ") because ("
                                    + valueOf
                                    + ") for user "
                                    + i,
                            new Object[0]);
                    z = true;
                }
                if (state == State.IDLE) {
                    ((HashMap) stateMachineImpl.currentStateMap).remove(Integer.valueOf(i));
                    ((HashMap) stateMachineImpl.previousStateMap).remove(Integer.valueOf(i));
                }
            } finally {
            }
        }
        if (z) {
            synchronized (stateMachineImpl.listenerLock) {
                try {
                    Iterator it = ((ArrayList) stateMachineImpl.stateChangeListeners).iterator();
                    while (it.hasNext()) {
                        DualDARController.getInstance(
                                        ((StateMachineProxy)
                                                        ((StateMachineImpl.StateChangeListener)
                                                                it.next()))
                                                .context)
                                .onDualDarStateChanged(currentState, state, valueOf, i);
                    }
                } finally {
                }
            }
        }
        return z;
    }

    public final boolean setInitialState() {
        int dualDARUser = PersonaServiceHelper.getDualDARUser();
        DDLog.d(
                "StateMachineProxy",
                VibrationParam$1$$ExternalSyntheticOutline0.m(
                        dualDARUser, "Set initial state for DualDAR User "),
                new Object[0]);
        if (this.initiateState) {
            DDLog.d("StateMachineProxy", "DualDAR User has been already initiated", new Object[0]);
            return true;
        }
        if (dualDARUser == -1) {
            DDLog.e(
                    "StateMachineProxy",
                    VibrationParam$1$$ExternalSyntheticOutline0.m(
                            dualDARUser, "Not Active user for DualDAR : "),
                    new Object[0]);
            return false;
        }
        DDLog.d("StateMachineProxy", "DualDAR User set initial State.", new Object[0]);
        StateMachineImpl stateMachineImpl = this.stateMachine;
        State state = State.DEVICE_LOCK_DATA_LOCK;
        stateMachineImpl.getClass();
        DDLog.d(
                "DualDAR::StateMachine",
                "setInitialState() initialState: " + state + " for user " + dualDARUser,
                new Object[0]);
        synchronized (stateMachineImpl.stateLock) {
            ((HashMap) stateMachineImpl.currentStateMap).put(Integer.valueOf(dualDARUser), state);
            ((HashMap) stateMachineImpl.previousStateMap).put(Integer.valueOf(dualDARUser), null);
        }
        this.initiateState = true;
        return true;
    }
}
