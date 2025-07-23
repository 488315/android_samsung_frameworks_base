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
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecPinBasedInputViewController$$ExternalSyntheticOutline0;
import com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$registerSimCardManagerCallback$1;
import com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.ISimCardManagerService;
import com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.ISimCardManagerServiceCallback;
import java.util.Iterator;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class SimCardManagerServiceProvider {
    public static Context mContext;
    public static volatile SimCardManagerServiceProvider sInstance;
    public static volatile ServiceBindHelper sServiceBindHelper;
    public static final Uri INTERNAL_URI = Uri.parse("content://com.samsung.android.app.telephonyui.internal");
    public static SimInfoRepositoryImpl$registerSimCardManagerCallback$1 sSimCardManagerServiceCallback = null;
    public static boolean mIsServiceClose = false;
    public static boolean mIsRemainCallbackCall = false;
    public static final int INVALID_VARIABLE = -1;
    public static final AnonymousClass1 mHandler = new Handler() { // from class: com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.SimCardManagerServiceProvider.1
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
            AnonymousClass1 anonymousClass1 = SimCardManagerServiceProvider.mHandler;
            if (anonymousClass1 != null) {
                anonymousClass1.removeMessages(0);
            }
        }
    };

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ServiceBindHelper {
        public static boolean mIsBound = false;
        public final AnonymousClass1 mServiceConnection;
        public volatile int mServiceStatus = 0;
        public ISimCardManagerService mSimCardManagerService;
        public AnonymousClass2 mSimCardManagerServiceCallback;

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r0v1, types: [android.content.ServiceConnection, com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.SimCardManagerServiceProvider$ServiceBindHelper$1] */
        public ServiceBindHelper(Context context) {
            ?? r0 = new ServiceConnection() { // from class: com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.SimCardManagerServiceProvider.ServiceBindHelper.1
                /* JADX WARN: Multi-variable type inference failed */
                /* JADX WARN: Type inference failed for: r4v5, types: [com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.SimCardManagerServiceProvider$ServiceBindHelper$2] */
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
            AnonymousClass1 anonymousClass1;
            serviceBindHelper.getClass();
            try {
                if (mIsBound) {
                    if (serviceBindHelper.mSimCardManagerService != null && (anonymousClass1 = serviceBindHelper.mServiceConnection) != null) {
                        if (serviceBindHelper.mSimCardManagerServiceCallback != null) {
                            try {
                                StringBuilder sb = new StringBuilder("getServiceApi : ");
                                sb.append(serviceBindHelper.mSimCardManagerService != null);
                                Log.d("SimCardManagerServiceProvider$ServiceBindHelper", sb.toString());
                                ((ISimCardManagerService.Stub.Proxy) serviceBindHelper.mSimCardManagerService).unregisterSimCardManagerServiceCallback(serviceBindHelper.mSimCardManagerServiceCallback);
                                serviceBindHelper.mSimCardManagerServiceCallback = null;
                                Log.d("SimCardManagerServiceProvider$ServiceBindHelper", "unbindService : mSimCardManagerServiceCallback is unregister");
                            } catch (RemoteException e) {
                                e.printStackTrace();
                            }
                        }
                        Log.d("SimCardManagerServiceProvider$ServiceBindHelper", "unbindService : SimCardManagerProcessService");
                        context.unbindService(anonymousClass1);
                    }
                    mIsBound = false;
                }
            } catch (Exception unused) {
                Log.e("SimCardManagerServiceProvider$ServiceBindHelper", "Caught Exception:");
            }
        }

        public final void setServiceStatus(int i) {
            KeyguardSecPinBasedInputViewController$$ExternalSyntheticOutline0.m(new StringBuilder("serviceStatus : "), this.mServiceStatus, " -> ", i, "SimCardManagerServiceProvider$ServiceBindHelper");
            this.mServiceStatus = i;
        }
    }

    private SimCardManagerServiceProvider() {
        getServiceApi();
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
                try {
                    if (sInstance == null) {
                        sInstance = new SimCardManagerServiceProvider();
                    }
                } finally {
                }
            }
        }
        AnonymousClass1 anonymousClass1 = mHandler;
        if (anonymousClass1 != null) {
            anonymousClass1.removeMessages(0);
            mIsRemainCallbackCall = false;
        }
        return sInstance;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x001f A[Catch: all -> 0x0029, TryCatch #0 {all -> 0x0029, blocks: (B:14:0x000f, B:16:0x0013, B:21:0x001f, B:22:0x002b), top: B:13:0x000f }] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.ISimCardManagerService getServiceApi() {
        /*
            com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.SimCardManagerServiceProvider$ServiceBindHelper r0 = com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.SimCardManagerServiceProvider.sServiceBindHelper
            r1 = 1
            r2 = 0
            if (r0 == 0) goto Lc
            com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.SimCardManagerServiceProvider$ServiceBindHelper r0 = com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.SimCardManagerServiceProvider.sServiceBindHelper
            int r0 = r0.mServiceStatus
            if (r0 != 0) goto L2c
        Lc:
            java.lang.Class<com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.SimCardManagerServiceProvider> r0 = com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.SimCardManagerServiceProvider.class
            monitor-enter(r0)
            com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.SimCardManagerServiceProvider$ServiceBindHelper r3 = com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.SimCardManagerServiceProvider.sServiceBindHelper     // Catch: java.lang.Throwable -> L29
            if (r3 == 0) goto L1c
            com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.SimCardManagerServiceProvider$ServiceBindHelper r3 = com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.SimCardManagerServiceProvider.sServiceBindHelper     // Catch: java.lang.Throwable -> L29
            int r3 = r3.mServiceStatus     // Catch: java.lang.Throwable -> L29
            if (r3 != 0) goto L1a
            goto L1c
        L1a:
            r3 = r2
            goto L1d
        L1c:
            r3 = r1
        L1d:
            if (r3 == 0) goto L2b
            com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.SimCardManagerServiceProvider$ServiceBindHelper r3 = new com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.SimCardManagerServiceProvider$ServiceBindHelper     // Catch: java.lang.Throwable -> L29
            android.content.Context r4 = com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.SimCardManagerServiceProvider.mContext     // Catch: java.lang.Throwable -> L29
            r3.<init>(r4)     // Catch: java.lang.Throwable -> L29
            com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.SimCardManagerServiceProvider.sServiceBindHelper = r3     // Catch: java.lang.Throwable -> L29
            goto L2b
        L29:
            r1 = move-exception
            goto L43
        L2b:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L29
        L2c:
            com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.SimCardManagerServiceProvider$ServiceBindHelper r0 = com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.SimCardManagerServiceProvider.sServiceBindHelper
            java.lang.StringBuilder r3 = new java.lang.StringBuilder
            java.lang.String r4 = "getServiceApi : "
            r3.<init>(r4)
            com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.ISimCardManagerService r4 = r0.mSimCardManagerService
            if (r4 == 0) goto L3a
            goto L3b
        L3a:
            r1 = r2
        L3b:
            java.lang.String r2 = "SimCardManagerServiceProvider$ServiceBindHelper"
            androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0.m(r3, r1, r2)
            com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.ISimCardManagerService r0 = r0.mSimCardManagerService
            return r0
        L43:
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L29
            throw r1
        */
        throw new UnsupportedOperationException("Method not decompiled: com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.SimCardManagerServiceProvider.getServiceApi():com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.ISimCardManagerService");
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
            Log.d("SimCardManagerServiceProvider", "GetCurrentVoiceCall: = " + Integer.valueOf(i));
            return i;
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
            EmergencyButtonController$$ExternalSyntheticOutline0.m("isDefaultDataSlotAllowed: = ", "SimCardManagerServiceProvider", z);
            return z;
        }
    }
}
