package com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0;
import com.android.systemui.settings.multisim.MultiSIMController;
import com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.ISimCardManagerService;
import com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.ISimCardManagerServiceCallback;
import com.samsung.systemui.splugins.volume.VolumePanelState;
import java.util.Iterator;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class SimCardManagerServiceProvider {
    public static Context mContext;
    public static volatile SimCardManagerServiceProvider sInstance;
    public static volatile ServiceBindHelper sServiceBindHelper;
    public static final Uri INTERNAL_URI = Uri.parse("content://com.samsung.android.app.telephonyui.internal");
    public static MultiSIMController.C240513 sSimCardManagerServiceCallback = null;
    public static boolean mIsServiceClose = false;
    public static boolean mIsRemainCallbackCall = false;
    public static final int INVALID_VARIABLE = -1;
    public static final HandlerC45511 mHandler = new Handler() { // from class: com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.SimCardManagerServiceProvider.1
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            if (message.what != 0) {
                Log.d("SimCardManagerServiceProvider", "MESSAGE_EMPTY");
                return;
            }
            Log.d("SimCardManagerServiceProvider", "SIMCARD_MANAGER_SERVICE_CLOSE" + message);
            try {
                Log.d("SimCardManagerServiceProvider", "SimCardManagerProcessService Close !!!");
                SimCardManagerServiceProvider.sSimCardManagerServiceCallback = null;
                ServiceBindHelper.access$300(SimCardManagerServiceProvider.sServiceBindHelper, SimCardManagerServiceProvider.mContext);
                if (SimCardManagerServiceProvider.isServiceRunningCheck(SimCardManagerServiceProvider.mContext)) {
                    Intent intent = new Intent();
                    intent.setClassName("com.samsung.android.app.telephonyui", "com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.SimCardManagerProcessService");
                    intent.setPackage("com.samsung.android.app.telephonyui");
                    SimCardManagerServiceProvider.mContext.stopService(intent);
                }
                SimCardManagerServiceProvider.sServiceBindHelper.setServiceStatus(0);
                SimCardManagerServiceProvider.sServiceBindHelper = null;
                SimCardManagerServiceProvider.sInstance = null;
                SimCardManagerServiceProvider.mIsServiceClose = true;
                SimCardManagerServiceProvider.mIsRemainCallbackCall = false;
            } catch (Exception e) {
                e.printStackTrace();
            }
            HandlerC45511 handlerC45511 = SimCardManagerServiceProvider.mHandler;
            if (handlerC45511 != null) {
                handlerC45511.removeMessages(0);
            }
        }
    };

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class ServiceBindHelper {
        public static boolean mIsBound = false;
        public final ServiceConnectionC45521 mServiceConnection;
        public volatile int mServiceStatus = 0;
        public ISimCardManagerService mSimCardManagerService;
        public BinderC45532 mSimCardManagerServiceCallback;

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v1, types: [android.content.ServiceConnection, com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.SimCardManagerServiceProvider$ServiceBindHelper$1] */
        public ServiceBindHelper(Context context) {
            ?? r0 = new ServiceConnection() { // from class: com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.SimCardManagerServiceProvider.ServiceBindHelper.1
                /* JADX WARN: Multi-variable type inference failed */
                /* JADX WARN: Type inference failed for: r4v5, types: [com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.ISimCardManagerServiceCallback, com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.SimCardManagerServiceProvider$ServiceBindHelper$2] */
                @Override // android.content.ServiceConnection
                public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
                    ISimCardManagerService proxy;
                    Log.d("SimCardManagerServiceProvider$ServiceBindHelper", "connected");
                    SimCardManagerServiceProvider.mIsServiceClose = false;
                    ServiceBindHelper.this.setServiceStatus(2);
                    ServiceBindHelper serviceBindHelper = ServiceBindHelper.this;
                    int i = ISimCardManagerService.Stub.$r8$clinit;
                    if (iBinder == null) {
                        proxy = null;
                    } else {
                        IInterface queryLocalInterface = iBinder.queryLocalInterface("com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.ISimCardManagerService");
                        proxy = (queryLocalInterface == null || !(queryLocalInterface instanceof ISimCardManagerService)) ? new ISimCardManagerService.Stub.Proxy(iBinder) : (ISimCardManagerService) queryLocalInterface;
                    }
                    serviceBindHelper.mSimCardManagerService = proxy;
                    try {
                        ServiceBindHelper serviceBindHelper2 = ServiceBindHelper.this;
                        if (serviceBindHelper2.mSimCardManagerServiceCallback == null) {
                            ISimCardManagerService iSimCardManagerService = serviceBindHelper2.mSimCardManagerService;
                            ?? r4 = new ISimCardManagerServiceCallback.Stub(serviceBindHelper2) { // from class: com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.SimCardManagerServiceProvider.ServiceBindHelper.2
                            };
                            serviceBindHelper2.mSimCardManagerServiceCallback = r4;
                            ((ISimCardManagerService.Stub.Proxy) iSimCardManagerService).registerSimCardManagerServiceCallback(r4);
                        }
                    } catch (RemoteException e) {
                        e.printStackTrace();
                    }
                }

                @Override // android.content.ServiceConnection
                public final void onServiceDisconnected(ComponentName componentName) {
                    Log.d("SimCardManagerServiceProvider$ServiceBindHelper", "disconnected");
                    ServiceBindHelper serviceBindHelper = ServiceBindHelper.this;
                    serviceBindHelper.mSimCardManagerService = null;
                    serviceBindHelper.setServiceStatus(0);
                    ServiceBindHelper.mIsBound = false;
                }
            };
            this.mServiceConnection = r0;
            setServiceStatus(1);
            mIsBound = true;
            Log.d("SimCardManagerServiceProvider$ServiceBindHelper", "bindService : SimCardManagerProcessService");
            Intent intent = new Intent();
            intent.setClassName("com.samsung.android.app.telephonyui", "com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.SimCardManagerProcessService");
            intent.setPackage("com.samsung.android.app.telephonyui");
            context.bindService(intent, (ServiceConnection) r0, 1);
        }

        public static void access$300(ServiceBindHelper serviceBindHelper, Context context) {
            ServiceConnectionC45521 serviceConnectionC45521;
            serviceBindHelper.getClass();
            try {
                if (mIsBound) {
                    if (serviceBindHelper.mSimCardManagerService != null && (serviceConnectionC45521 = serviceBindHelper.mServiceConnection) != null) {
                        if (serviceBindHelper.mSimCardManagerServiceCallback != null) {
                            try {
                                ((ISimCardManagerService.Stub.Proxy) serviceBindHelper.getServiceApi()).unregisterSimCardManagerServiceCallback(serviceBindHelper.mSimCardManagerServiceCallback);
                                serviceBindHelper.mSimCardManagerServiceCallback = null;
                                Log.d("SimCardManagerServiceProvider$ServiceBindHelper", "unbindService : mSimCardManagerServiceCallback is unregister");
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.d("SimCardManagerServiceProvider$ServiceBindHelper", "unbindService : SimCardManagerProcessService");
                        context.unbindService(serviceConnectionC45521);
                    }
                    mIsBound = false;
                }
            } catch (Exception unused) {
                Log.e("SimCardManagerServiceProvider$ServiceBindHelper", "Caught Exception:");
            }
        }

        public final ISimCardManagerService getServiceApi() {
            ActionBarContextView$$ExternalSyntheticOutline0.m9m(new StringBuilder("getServiceApi : "), this.mSimCardManagerService != null, "SimCardManagerServiceProvider$ServiceBindHelper");
            return this.mSimCardManagerService;
        }

        public final void setServiceStatus(int i) {
            KeyguardSecUpdateMonitorImpl$$ExternalSyntheticOutline0.m79m(new StringBuilder("serviceStatus : "), this.mServiceStatus, " -> ", i, "SimCardManagerServiceProvider$ServiceBindHelper");
            this.mServiceStatus = i;
        }
    }

    private SimCardManagerServiceProvider() {
        getServiceApi();
    }

    public static void CloseService() {
        if (sServiceBindHelper != null) {
            int i = (mIsRemainCallbackCall && isServiceRunningCheck(mContext)) ? VolumePanelState.DIALOG_TIMEOUT_SET_SAFE_MEDIA_VOLUME_MILLIS : 0;
            HandlerC45511 handlerC45511 = mHandler;
            if (handlerC45511 != null) {
                Log.d("SimCardManagerServiceProvider", "CloseService : mIsRemainCallbackCall = " + mIsRemainCallbackCall + ", delayTime = " + i);
                if (!(sServiceBindHelper.mServiceStatus == 0)) {
                    handlerC45511.sendMessageDelayed(handlerC45511.obtainMessage(0), i);
                    return;
                }
                Log.d("SimCardManagerServiceProvider", "CloseService : already disconnected so initial value");
                sSimCardManagerServiceCallback = null;
                sServiceBindHelper = null;
                sInstance = null;
                mIsServiceClose = true;
                mIsRemainCallbackCall = false;
            }
        }
    }

    public static SimCardManagerServiceProvider getService(Context context) {
        Context applicationContext = context.getApplicationContext();
        mContext = applicationContext;
        if (!isServiceRunningCheck(applicationContext)) {
            Intent intent = new Intent();
            intent.setClassName("com.samsung.android.app.telephonyui", "com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.SimCardManagerProcessService");
            intent.setPackage("com.samsung.android.app.telephonyui");
            intent.putExtra("sticky_value", 2);
            Log.d("SimCardManagerServiceProvider", "getServiceApi - startService !!");
            if (mContext.startService(intent) == null) {
                Log.d("SimCardManagerServiceProvider", "getServiceApi - startService Fail !!");
                return null;
            }
        }
        if (sInstance == null) {
            synchronized (SimCardManagerServiceProvider.class) {
                if (sInstance == null) {
                    sInstance = new SimCardManagerServiceProvider();
                }
            }
        }
        HandlerC45511 handlerC45511 = mHandler;
        if (handlerC45511 != null) {
            handlerC45511.removeMessages(0);
            mIsRemainCallbackCall = false;
        }
        return sInstance;
    }

    /* JADX WARN: Code restructure failed: missing block: B:17:0x0027, code lost:
    
        if ((com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.SimCardManagerServiceProvider.sServiceBindHelper.mServiceStatus == 0) != false) goto L22;
     */
    /* JADX WARN: Removed duplicated region for block: B:9:0x0017  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static ISimCardManagerService getServiceApi() {
        boolean z;
        boolean z2 = false;
        if (sServiceBindHelper != null) {
            if (!(sServiceBindHelper.mServiceStatus == 0)) {
                z = false;
                if (z) {
                    synchronized (SimCardManagerServiceProvider.class) {
                        if (sServiceBindHelper != null) {
                        }
                        z2 = true;
                        if (z2) {
                            sServiceBindHelper = new ServiceBindHelper(mContext);
                        }
                    }
                }
                return sServiceBindHelper.getServiceApi();
            }
        }
        z = true;
        if (z) {
        }
        return sServiceBindHelper.getServiceApi();
    }

    public static boolean isServiceRunningCheck(Context context) {
        Iterator<ActivityManager.RunningServiceInfo> it = ((ActivityManager) context.getApplicationContext().getSystemService("activity")).getRunningServices(Integer.MAX_VALUE).iterator();
        while (it.hasNext()) {
            if ("com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.SimCardManagerProcessService".equals(it.next().service.getClassName())) {
                Log.d("SimCardManagerServiceProvider", "SimCardManagerProcessService already is Running !!! ");
                return true;
            }
        }
        return false;
    }

    public final int GetCurrentVoiceCall() {
        int i = INVALID_VARIABLE;
        try {
            int GetCurrentVoiceCall = ((ISimCardManagerService.Stub.Proxy) getServiceApi()).GetCurrentVoiceCall();
            Log.d("SimCardManagerServiceProvider", "GetCurrentVoiceCall: = " + GetCurrentVoiceCall);
            return GetCurrentVoiceCall;
        } catch (RemoteException unused) {
            Log.e("SimCardManagerServiceProvider", "GetCurrentVoiceCall: exception occurred.");
            return i;
        } catch (NullPointerException unused2) {
            Log.e("SimCardManagerServiceProvider", "GetCurrentVoiceCall: service is not running.");
            try {
                Bundle call = mContext.getContentResolver().call(INTERNAL_URI, "getCurrentVoiceCall", (String) null, new Bundle());
                if (call == null) {
                    Log.d("SimCardManagerServiceProvider", "bundle is null : getCurrentVoiceCall");
                } else {
                    i = call.getInt("result");
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
            Integer valueOf = Integer.valueOf(i);
            Log.d("SimCardManagerServiceProvider", "GetCurrentVoiceCall: = " + valueOf);
            return valueOf.intValue();
        }
    }

    public final boolean isDefaultDataSlotAllowed(int i) {
        boolean z = false;
        try {
            boolean isDefaultDataSlotAllowed = ((ISimCardManagerService.Stub.Proxy) getServiceApi()).isDefaultDataSlotAllowed(i);
            Log.d("SimCardManagerServiceProvider", "isDefaultDataSlotAllowed: = " + isDefaultDataSlotAllowed);
            return isDefaultDataSlotAllowed;
        } catch (RemoteException unused) {
            Log.e("SimCardManagerServiceProvider", "isDefaultDataSlotAllowed: exception occurred.");
            return false;
        } catch (NullPointerException unused2) {
            Log.e("SimCardManagerServiceProvider", "isDefaultDataSlotAllowed: service is not running.");
            Context context = mContext;
            try {
                Bundle bundle = new Bundle();
                if (i != INVALID_VARIABLE) {
                    bundle.putInt("selectItem", i);
                }
                Bundle call = context.getContentResolver().call(INTERNAL_URI, "isDefaultDataSlotAllowed", (String) null, bundle);
                if (call == null) {
                    Log.d("SimCardManagerServiceProvider", "bundle is null : isDefaultDataSlotAllowed");
                } else {
                    z = call.getBoolean("result");
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
            KeyguardCarrierPasswordViewController$$ExternalSyntheticOutline0.m62m("isDefaultDataSlotAllowed: = ", z, "SimCardManagerServiceProvider");
            return z;
        }
    }
}
