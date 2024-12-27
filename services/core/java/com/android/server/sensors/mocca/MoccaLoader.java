package com.android.server.sensors.mocca;

import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.os.ServiceManager;
import android.util.Log;
import android.view.IWindowManager;
import android.view.MotionEvent;
import android.view.WindowManagerPolicyConstants;
import com.android.server.wm.WindowManagerService;
import com.samsung.android.mocca.IInternalServiceBridge;
import com.samsung.android.mocca.IInternalServiceBridgeListener;
import com.samsung.android.mocca.IMoccaService;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class MoccaLoader {
    public static final InternalServiceBridge sServiceBridge = new InternalServiceBridge();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class InternalServiceBridge extends IInternalServiceBridge.Stub implements WindowManagerPolicyConstants.PointerEventListener {
        public final String[] SUPPORTED_TYPES = {"touch"};
        public final Map mListeners = new HashMap();
        public WindowManagerService mWms = null;

        public final void clearAllListeners() {
            synchronized (this.mListeners) {
                ((HashMap) this.mListeners).clear();
            }
        }

        public final Bundle getValue(String str) {
            return null;
        }

        public final boolean isAvailable(String str) {
            for (String str2 : this.SUPPORTED_TYPES) {
                if (str2.equals(str)) {
                    return true;
                }
            }
            return false;
        }

        public final void onPointerEvent(MotionEvent motionEvent) {
            if (motionEvent.getAction() == 0) {
                synchronized (this.mListeners) {
                    try {
                        IInternalServiceBridgeListener iInternalServiceBridgeListener = (IInternalServiceBridgeListener) ((HashMap) this.mListeners).get("touch");
                        if (iInternalServiceBridgeListener != null) {
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("event", motionEvent);
                            iInternalServiceBridgeListener.onUpdated("touch", bundle);
                        }
                    } catch (Throwable th) {
                        Log.e("MoccaLoader", "PointerEvent : Failed to invoke method", th);
                    } finally {
                    }
                }
            }
        }

        public final void setListener(String str, IInternalServiceBridgeListener iInternalServiceBridgeListener) {
            synchronized (this.mListeners) {
                ((HashMap) this.mListeners).put(str, iInternalServiceBridgeListener);
            }
        }

        public final void start() {
            WindowManagerService asInterface = IWindowManager.Stub.asInterface(ServiceManager.getService("window"));
            this.mWms = asInterface;
            if (asInterface != null) {
                asInterface.registerPointerEventListener(this, 0);
            }
        }

        public final void stop() {
            WindowManagerService windowManagerService = this.mWms;
            if (windowManagerService != null) {
                windowManagerService.unregisterPointerEventListener(this, 0);
            }
        }
    }

    public static IBinder getMoccaService(Context context) {
        Class<?> loadClass = context.createPackageContext("com.samsung.android.mocca", 3).getClassLoader().loadClass("com.samsung.android.mocca.MoccaSystemService");
        if (loadClass != null) {
            return (IBinder) loadClass.getConstructor(Context.class).newInstance(context);
        }
        Log.e("MoccaLoader", "Why Mocca service class is null?");
        return null;
    }

    public static void systemReady(Context context, IBinder iBinder) {
        Class<?> loadClass = context.createPackageContext("com.samsung.android.mocca", 3).getClassLoader().loadClass("com.samsung.android.mocca.MoccaSystemService");
        if (loadClass == null) {
            Log.e("MoccaLoader", "Why Mocca service class is null?");
            return;
        }
        IMoccaService asInterface = IMoccaService.Stub.asInterface(iBinder);
        if (asInterface != null) {
            try {
                loadClass.getDeclaredMethod("systemReady", IBinder.class).invoke(asInterface, sServiceBridge);
            } catch (NoSuchMethodException unused) {
                Class[] clsArr = new Class[0];
                Method declaredMethod = loadClass.getDeclaredMethod("systemReady", null);
                if (declaredMethod != null) {
                    declaredMethod.invoke(asInterface, null);
                }
            }
        }
    }
}
