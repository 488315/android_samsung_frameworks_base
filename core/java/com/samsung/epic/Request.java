package com.samsung.epic;

import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.epic.IEpicManager;
import android.os.epic.IEpicObject;

public class Request {
    private static final String TAG = "EpicRequest";
    private static IEpicManager mEpicManager;
    private static boolean mHasLoad = false;
    private IEpicObject mEpicObject;

    private Request() {
        get_service();
    }

    public Request(int scenario_id) {
        this();
        try {
            this.mEpicObject = mEpicManager.Create(scenario_id);
        } catch (Exception e) {
            this.mEpicObject = null;
        }
    }

    public Request(int[] scenario_id_list) {
        this();
        try {
            this.mEpicObject = mEpicManager.Creates(scenario_id_list);
        } catch (Exception e) {
            this.mEpicObject = null;
        }
    }

    public boolean acquire_lock() {
        if (this.mEpicObject == null) {
            return false;
        }
        try {
            boolean ret = this.mEpicObject.acquire_lock();
            return ret;
        } catch (RemoteException e) {
            return false;
        }
    }

    public boolean release_lock() {
        if (this.mEpicObject == null) {
            return false;
        }
        try {
            boolean ret = this.mEpicObject.release_lock();
            return ret;
        } catch (RemoteException e) {
            return false;
        }
    }

    public boolean acquire_lock(int value, int usec) {
        if (this.mEpicObject == null) {
            return false;
        }
        try {
            boolean ret = this.mEpicObject.acquire_lock_option(value, usec);
            return ret;
        } catch (RemoteException e) {
            return false;
        }
    }

    public boolean acquire_lock(int[] value_list, int[] usec_list) {
        if (this.mEpicObject == null) {
            return false;
        }
        try {
            boolean ret = this.mEpicObject.acquire_lock_option_multi(value_list, usec_list);
            return ret;
        } catch (RemoteException e) {
            return false;
        }
    }

    public boolean perf_hint(String name) {
        if (this.mEpicObject == null) {
            return false;
        }
        try {
            boolean ret = this.mEpicObject.perf_hint(name);
            return ret;
        } catch (RemoteException e) {
            return false;
        }
    }

    public boolean hint_release(String name) {
        if (this.mEpicObject == null) {
            return false;
        }
        try {
            boolean ret = this.mEpicObject.hint_release(name);
            return ret;
        } catch (RemoteException e) {
            return false;
        }
    }

    private void get_service() {
        synchronized (Request.class) {
            try {
            } catch (Exception e) {
                mEpicManager = null;
            }
            if (mHasLoad) {
                return;
            }
            mEpicManager =
                    IEpicManager.Stub.asInterface(ServiceManager.getService(Context.EPIC_SERVICE));
            mHasLoad = true;
        }
    }
}
