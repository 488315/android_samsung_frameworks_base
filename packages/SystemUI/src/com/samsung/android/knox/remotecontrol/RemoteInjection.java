package com.samsung.android.knox.remotecontrol;

import android.os.Process;
import android.os.RemoteException;
import android.os.ServiceManager;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import com.samsung.android.knox.ContextInfo;
import com.samsung.android.knox.license.EnterpriseLicenseManager;
import com.samsung.android.knox.remotecontrol.IRemoteInjection;

/* loaded from: classes4.dex */
public class RemoteInjection {
    public static final String TAG = "RemoteInjection";
    public IRemoteInjection mRemoteService;

    public final IRemoteInjection getService() {
        if (this.mRemoteService == null) {
            this.mRemoteService = IRemoteInjection.Stub.asInterface(ServiceManager.getService("remoteinjection"));
        }
        return this.mRemoteService;
    }

    public boolean injectKeyEvent(KeyEvent keyEvent, boolean z) {
        EnterpriseLicenseManager.log(new ContextInfo(Process.myUid()), "RemoteInjection.injectKeyEvent");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mRemoteService.injectKeyEvent(keyEvent, z);
        } catch (RemoteException e) {
            Log.d(TAG, "Error injecting key event : " + e);
            return false;
        }
    }

    public boolean injectKeyEventDex(KeyEvent keyEvent, boolean z) {
        EnterpriseLicenseManager.log(new ContextInfo(Process.myUid()), "RemoteInjection.injectKeyEventDex");
        return false;
    }

    public boolean injectPointerEvent(MotionEvent motionEvent, boolean z) {
        if (getService() == null) {
            return false;
        }
        try {
            return this.mRemoteService.injectPointerEvent(motionEvent, z);
        } catch (RemoteException e) {
            Log.d(TAG, "Error injecting pointer event : " + e);
            return false;
        }
    }

    public boolean injectPointerEventDex(MotionEvent motionEvent, boolean z) {
        EnterpriseLicenseManager.log(new ContextInfo(Process.myUid()), "RemoteInjection.injectPointerEventDex");
        return false;
    }

    public boolean injectTrackballEvent(MotionEvent motionEvent, boolean z) {
        EnterpriseLicenseManager.log(new ContextInfo(Process.myUid()), "RemoteInjection.injectTrackballEvent");
        if (getService() == null) {
            return false;
        }
        try {
            return this.mRemoteService.injectTrackballEvent(motionEvent, z);
        } catch (RemoteException e) {
            Log.d(TAG, "Error injecting trackball event : " + e);
            return false;
        }
    }
}
