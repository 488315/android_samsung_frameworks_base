package com.samsung.epic;

import android.content.Context;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.os.epic.IEpicManager;
import android.os.epic.IEpicObject;

/* loaded from: classes6.dex */
public class Request {
    private static final String TAG = "EpicRequest";
    private static IEpicManager mEpicManager = null;
    private static boolean mHasLoad = false;
    private IEpicObject mEpicObject;

    private Request() {
        get_service();
    }

    public Request(int i) {
        this();
        try {
            this.mEpicObject = mEpicManager.Create(i);
        } catch (Exception unused) {
            this.mEpicObject = null;
        }
    }

    public Request(int[] iArr) {
        this();
        try {
            this.mEpicObject = mEpicManager.Creates(iArr);
        } catch (Exception unused) {
            this.mEpicObject = null;
        }
    }

    public boolean acquire_lock() {
        IEpicObject iEpicObject = this.mEpicObject;
        if (iEpicObject == null) {
            return false;
        }
        try {
            return iEpicObject.acquire_lock();
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean release_lock() {
        IEpicObject iEpicObject = this.mEpicObject;
        if (iEpicObject == null) {
            return false;
        }
        try {
            return iEpicObject.release_lock();
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean acquire_lock(int i, int i2) {
        IEpicObject iEpicObject = this.mEpicObject;
        if (iEpicObject == null) {
            return false;
        }
        try {
            return iEpicObject.acquire_lock_option(i, i2);
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean acquire_lock(int[] iArr, int[] iArr2) {
        IEpicObject iEpicObject = this.mEpicObject;
        if (iEpicObject == null) {
            return false;
        }
        try {
            return iEpicObject.acquire_lock_option_multi(iArr, iArr2);
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean perf_hint(String str) {
        IEpicObject iEpicObject = this.mEpicObject;
        if (iEpicObject == null) {
            return false;
        }
        try {
            return iEpicObject.perf_hint(str);
        } catch (RemoteException unused) {
            return false;
        }
    }

    public boolean hint_release(String str) {
        IEpicObject iEpicObject = this.mEpicObject;
        if (iEpicObject == null) {
            return false;
        }
        try {
            return iEpicObject.hint_release(str);
        } catch (RemoteException unused) {
            return false;
        }
    }

    private void get_service() {
        synchronized (Request.class) {
            try {
            } catch (Exception unused) {
                mEpicManager = null;
            }
            if (mHasLoad) {
                return;
            }
            mEpicManager = IEpicManager.Stub.asInterface(ServiceManager.getService(Context.EPIC_SERVICE));
            mHasLoad = true;
        }
    }
}
