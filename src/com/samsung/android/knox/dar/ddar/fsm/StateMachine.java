package com.samsung.android.knox.dar.ddar.fsm;

import android.os.Bundle;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import com.samsung.android.knox.dar.ddar.DualDarConstants;
import com.samsung.android.knox.dar.ddar.proxy.IProxyService;
import com.samsung.android.knox.dar.ddar.proxy.KnoxProxyManager;

/* loaded from: classes6.dex */
public class StateMachine {
    private static final boolean DEBUG = false;
    private static final String GET_CURRENT_STATE = "GET_CURRENT_STATE";
    private static final String GET_PREVIOUS_STATE = "GET_PREVIOUS_STATE";
    private static final String KEY_DUAL_DAR_USER_ID = "KEY_DUAL_DAR_USER_ID";
    private static final String KEY_EVENT = "KEY_EVENT";
    private static final String KEY_STATE = "KEY_STATE";
    private static final String PROCESS_EVENT = "PROCESS_EVENT";
    private static final String SET_INITIAL_STATE = "SET_INITIAL_STATE";
    private static final String STATE_MACHINE_SERVICE = "STATE_MACHINE_SERVICE";
    private static final String SYSTEM_PROXY_AGENT = "SYSTEM_PROXY_AGENT";
    private static final String TAG = "DDAR::StateMachine";
    private static IProxyService _instance;

    public static void setInitialState() throws Exception {
        Log.d(TAG, "Set initial state DualDAR");
        Bundle bundleSendCommand = sendCommand(SET_INITIAL_STATE, null);
        if (bundleSendCommand == null || !bundleSendCommand.getBoolean(DualDarConstants.DUAL_DAR_RESPONSE)) {
            throw new Exception("DualDAR initiate State failed!!");
        }
    }

    public static boolean processEvent(int i, Event event) {
        if (event == null) {
            return false;
        }
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_DUAL_DAR_USER_ID, i);
        bundle.putString(KEY_EVENT, event.name());
        Bundle bundleSendCommand = sendCommand(PROCESS_EVENT, bundle);
        if (bundleSendCommand == null) {
            return false;
        }
        return bundleSendCommand.getBoolean(DualDarConstants.DUAL_DAR_RESPONSE);
    }

    public static State getPreviousState(int i) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_DUAL_DAR_USER_ID, i);
        Bundle bundleSendCommand = sendCommand(GET_PREVIOUS_STATE, bundle);
        if (bundleSendCommand == null) {
            return null;
        }
        return State.valueOf(bundleSendCommand.getString(KEY_STATE));
    }

    public static State getCurrentState(int i) {
        Bundle bundle = new Bundle();
        bundle.putInt(KEY_DUAL_DAR_USER_ID, i);
        Bundle bundleSendCommand = sendCommand(GET_CURRENT_STATE, bundle);
        if (bundleSendCommand == null) {
            return null;
        }
        return State.valueOf(bundleSendCommand.getString(KEY_STATE));
    }

    private static Bundle sendCommand(String str, Bundle bundle) {
        try {
            IProxyService service = getService();
            if (service == null) {
                Log.e(TAG, "sendCommand() : Error: Service Not found, command = " + str);
                return null;
            }
            return service.relay("SYSTEM_PROXY_AGENT", STATE_MACHINE_SERVICE, str, bundle);
        } catch (RemoteException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static IProxyService getService() {
        if (_instance == null) {
            _instance = IProxyService.Stub.asInterface(ServiceManager.getService(KnoxProxyManager.PROXY_SERVICE));
        }
        return _instance;
    }
}
