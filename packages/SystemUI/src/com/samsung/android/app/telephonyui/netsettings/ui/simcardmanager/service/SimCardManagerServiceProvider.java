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
import com.android.keyguard.EmergencyButtonController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardSecPinBasedInputViewController$$ExternalSyntheticOutline0;
import com.android.systemui.settings.multisim.data.repository.prod.SimInfoRepositoryImpl$simCardCallback$1;
import com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.ISimCardManagerService;
import com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.ISimCardManagerServiceCallback;
import java.util.Iterator;

/* loaded from: classes4.dex */
public class SimCardManagerServiceProvider {
    public static Context mContext;
    public static volatile SimCardManagerServiceProvider sInstance;
    public static volatile ServiceBindHelper sServiceBindHelper;
    public static final Uri INTERNAL_URI = Uri.parse("content://com.samsung.android.app.telephonyui.internal");
    public static SimInfoRepositoryImpl$simCardCallback$1 sSimCardManagerServiceCallback = null;
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
                        IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface("com.samsung.android.app.telephonyui.netsettings.ui.simcardmanager.service.ISimCardManagerService");
                        proxy = (iInterfaceQueryLocalInterface == null || !(iInterfaceQueryLocalInterface instanceof ISimCardManagerService)) ? new ISimCardManagerService.Stub.Proxy(iBinder) : (ISimCardManagerService) iInterfaceQueryLocalInterface;
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

    public static ISimCardManagerService getServiceApi() {
        if (sServiceBindHelper == null || sServiceBindHelper.mServiceStatus == 0) {
            synchronized (SimCardManagerServiceProvider.class) {
                try {
                    if (sServiceBindHelper == null || sServiceBindHelper.mServiceStatus == 0) {
                        sServiceBindHelper = new ServiceBindHelper(mContext);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
        ServiceBindHelper serviceBindHelper = sServiceBindHelper;
        ActionBarContextView$$ExternalSyntheticOutline0.m(new StringBuilder("getServiceApi : "), serviceBindHelper.mSimCardManagerService != null, "SimCardManagerServiceProvider$ServiceBindHelper");
        return serviceBindHelper.mSimCardManagerService;
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
            int iGetCurrentVoiceCall = ((ISimCardManagerService.Stub.Proxy) getServiceApi()).GetCurrentVoiceCall();
            Log.d("SimCardManagerServiceProvider", "GetCurrentVoiceCall: = " + iGetCurrentVoiceCall);
            return iGetCurrentVoiceCall;
        } catch (RemoteException unused) {
            Log.e("SimCardManagerServiceProvider", "GetCurrentVoiceCall: exception occurred.");
            return i;
        } catch (NullPointerException unused2) {
            Log.e("SimCardManagerServiceProvider", "GetCurrentVoiceCall: service is not running.");
            try {
                Bundle bundleCall = mContext.getContentResolver().call(INTERNAL_URI, "getCurrentVoiceCall", (String) null, new Bundle());
                if (bundleCall == null) {
                    Log.d("SimCardManagerServiceProvider", "bundle is null : getCurrentVoiceCall");
                } else {
                    i = bundleCall.getInt("result");
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
            boolean zIsDefaultDataSlotAllowed = ((ISimCardManagerService.Stub.Proxy) getServiceApi()).isDefaultDataSlotAllowed(i);
            Log.d("SimCardManagerServiceProvider", "isDefaultDataSlotAllowed: = " + zIsDefaultDataSlotAllowed);
            return zIsDefaultDataSlotAllowed;
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
                Bundle bundleCall = context.getContentResolver().call(INTERNAL_URI, "isDefaultDataSlotAllowed", (String) null, bundle);
                if (bundleCall == null) {
                    Log.d("SimCardManagerServiceProvider", "bundle is null : isDefaultDataSlotAllowed");
                } else {
                    z = bundleCall.getBoolean("result");
                }
            } catch (Throwable th) {
                th.printStackTrace();
            }
            EmergencyButtonController$$ExternalSyntheticOutline0.m("isDefaultDataSlotAllowed: = ", "SimCardManagerServiceProvider", z);
            return z;
        }
    }
}
