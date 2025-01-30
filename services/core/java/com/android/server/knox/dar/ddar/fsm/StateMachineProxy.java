package com.android.server.knox.dar.ddar.fsm;

import android.content.Context;
import android.os.Bundle;
import android.os.Process;
import android.os.UserHandle;
import com.android.server.knox.dar.ddar.DDLog;
import com.android.server.pm.PersonaServiceHelper;
import com.samsung.android.knox.dar.ddar.DualDARController;
import com.samsung.android.knox.dar.ddar.fsm.Event;
import com.samsung.android.knox.dar.ddar.fsm.State;
import com.samsung.android.knox.dar.ddar.proxy.IProxyAgentService;

/* loaded from: classes2.dex */
public class StateMachineProxy extends IProxyAgentService
    implements StateMachineImpl.StateChangeListener {
  public static StateMachineProxy mInstance;
  public Context context;
  public boolean initiateState = false;
  public StateMachineImpl stateMachine;

  public static synchronized StateMachineProxy getInstance(Context context) {
    StateMachineProxy stateMachineProxy;
    synchronized (StateMachineProxy.class) {
      if (mInstance == null) {
        mInstance = new StateMachineProxy(context);
      }
      stateMachineProxy = mInstance;
    }
    return stateMachineProxy;
  }

  public StateMachineProxy(Context context) {
    this.context = context;
    this.stateMachine = new StateMachineImpl(context);
    setInitialState();
    this.stateMachine.addStateChangeListener(this);
  }

  public final boolean setInitialState() {
    int dualDARUser = PersonaServiceHelper.getDualDARUser();
    DDLog.m33d(
        "StateMachineProxy", "Set initial state for DualDAR User " + dualDARUser, new Object[0]);
    if (this.initiateState) {
      DDLog.m33d("StateMachineProxy", "DualDAR User has been already initiated", new Object[0]);
      return true;
    }
    if (dualDARUser != -1) {
      DDLog.m33d("StateMachineProxy", "DualDAR User set initial State.", new Object[0]);
      this.stateMachine.setInitialState(dualDARUser, State.DEVICE_LOCK_DATA_LOCK);
      this.initiateState = true;
      return true;
    }
    DDLog.m34e("StateMachineProxy", "Not Active user for DualDAR : " + dualDARUser, new Object[0]);
    return false;
  }

  @Override // com.android.server.knox.dar.ddar.fsm.StateMachineImpl.StateChangeListener
  public void onStateChanged(State state, State state2, Event event, int i) {
    DualDARController.getInstance(this.context).onDualDarStateChanged(state, state2, event, i);
  }

  public Bundle onMessage(int i, String str, Bundle bundle) {
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
        State previousState =
            this.stateMachine.getPreviousState(bundle.getInt("KEY_DUAL_DAR_USER_ID"));
        z = previousState != null;
        bundle2.putString("KEY_STATE", previousState.name());
        bundle2.putBoolean("dual_dar_response", z);
      }
      return bundle2;
    } catch (Exception e) {
      e.printStackTrace();
      return null;
    }
  }

  public final boolean processEvent(Bundle bundle) {
    if (!bundle.containsKey("KEY_EVENT") || !bundle.containsKey("KEY_DUAL_DAR_USER_ID")) {
      return false;
    }
    return this.stateMachine.processEvent(
        Event.valueOf(bundle.getString("KEY_EVENT")), bundle.getInt("KEY_DUAL_DAR_USER_ID"));
  }

  public final void enforceCallingUser(int i) {
    if (UserHandle.getAppId(i) == 5250
        || UserHandle.getAppId(i) == 1000
        || UserHandle.getAppId(i) == Process.myUid()) {
      return;
    }
    throw new SecurityException("Can only be called by system user. callingUid: " + i);
  }
}
