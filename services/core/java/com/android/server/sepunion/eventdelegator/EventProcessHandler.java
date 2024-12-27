package com.android.server.sepunion.eventdelegator;

import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.os.UserHandle;
import android.telephony.PhoneStateListener;
import android.telephony.TelephonyManager;

import com.android.internal.content.PackageMonitor;
import com.android.server.sepunion.SemDeviceInfoManagerService;

import com.samsung.android.sepunion.Log;

import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class EventProcessHandler extends Handler {
    public static final String TAG;
    public final Context mContext;
    public final AnonymousClass2 mPackagesMonitor;
    public final AnonymousClass1 mPhoneStateListener;
    public final SemDeviceInfoManagerService mService;

    static {
        int i = SemDeviceInfoManagerService.$r8$clinit;
        TAG = "SemDeviceInfoManagerService";
    }

    /* JADX WARN: Type inference failed for: r0v1, types: [com.android.server.sepunion.eventdelegator.EventProcessHandler$2] */
    /* JADX WARN: Type inference failed for: r2v1, types: [com.android.server.sepunion.eventdelegator.EventProcessHandler$1] */
    public EventProcessHandler(
            Context context,
            SemDeviceInfoManagerService semDeviceInfoManagerService,
            HandlerThread handlerThread) {
        super(handlerThread.getLooper());
        this.mPackagesMonitor =
                new PackageMonitor() { // from class:
                                       // com.android.server.sepunion.eventdelegator.EventProcessHandler.2
                    public final void onPackageAdded(String str, int i) {
                        UnionEventListenerItem unionEventListenerItem;
                        Log.d(EventProcessHandler.TAG, "onPackageAdded(): " + str);
                        synchronized (EventProcessHandler.this.mService.mLock) {
                            try {
                                String str2 = "monitor_package_state;" + str;
                                ListenerContainer systemListenerContainer =
                                        EventProcessHandler.this.mService
                                                .getSystemListenerContainer();
                                if (systemListenerContainer.mCustomEventMap.containsKey(str2)
                                        && (unionEventListenerItem =
                                                        (UnionEventListenerItem)
                                                                systemListenerContainer
                                                                        .mCustomEventMap.get(str2))
                                                != null) {
                                    EventProcessHandler.this.mService.reportPackageStateChanged(
                                            unionEventListenerItem, str, "package_added");
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

                    public final void onPackageModified(String str) {
                        UnionEventListenerItem unionEventListenerItem;
                        Log.d(EventProcessHandler.TAG, "onPackageModified(): " + str);
                        synchronized (EventProcessHandler.this.mService.mLock) {
                            try {
                                String str2 = "monitor_package_state;" + str;
                                ListenerContainer systemListenerContainer =
                                        EventProcessHandler.this.mService
                                                .getSystemListenerContainer();
                                if (systemListenerContainer.mCustomEventMap.containsKey(str2)
                                        && (unionEventListenerItem =
                                                        (UnionEventListenerItem)
                                                                systemListenerContainer
                                                                        .mCustomEventMap.get(str2))
                                                != null) {
                                    EventProcessHandler.this.mService.reportPackageStateChanged(
                                            unionEventListenerItem, str, "package_modified");
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }

                    public final void onPackageRemoved(String str, int i) {
                        UnionEventListenerItem unionEventListenerItem;
                        Log.d(EventProcessHandler.TAG, "onPackageRemoved() :" + str);
                        synchronized (EventProcessHandler.this.mService.mLock) {
                            try {
                                String str2 = "monitor_package_state;" + str;
                                ListenerContainer systemListenerContainer =
                                        EventProcessHandler.this.mService
                                                .getSystemListenerContainer();
                                if (systemListenerContainer.mCustomEventMap.containsKey(str2)
                                        && (unionEventListenerItem =
                                                        (UnionEventListenerItem)
                                                                systemListenerContainer
                                                                        .mCustomEventMap.get(str2))
                                                != null) {
                                    EventProcessHandler.this.mService.reportPackageStateChanged(
                                            unionEventListenerItem, str, "package_removed");
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                };
        this.mContext = context;
        this.mService = semDeviceInfoManagerService;
        this.mPhoneStateListener =
                new PhoneStateListener(
                        handlerThread
                                .getLooper()) { // from class:
                                                // com.android.server.sepunion.eventdelegator.EventProcessHandler.1
                    @Override // android.telephony.PhoneStateListener
                    public final void onCallStateChanged(int i, String str) {
                        Log.d(EventProcessHandler.TAG, "onCallStateChanged() state = " + i);
                        synchronized (EventProcessHandler.this.mService.mLock) {
                            try {
                                ListenerContainer systemListenerContainer =
                                        EventProcessHandler.this.mService
                                                .getSystemListenerContainer();
                                if (systemListenerContainer.mCustomEventMap.containsKey(
                                        "monitor_call_state")) {
                                    EventProcessHandler.this.mService.reportCallStateChanged(
                                            (UnionEventListenerItem)
                                                    systemListenerContainer.mCustomEventMap.get(
                                                            "monitor_call_state"),
                                            i,
                                            str);
                                }
                            } catch (Throwable th) {
                                throw th;
                            }
                        }
                    }
                };
    }

    @Override // android.os.Handler
    public final void handleMessage(Message message) {
        String str = TAG;
        Log.d(str, "EventProcessHandler:handleMessage() msg.what = " + message.what);
        int i = message.what;
        if (i == 1) {
            ((TelephonyManager) this.mContext.getSystemService("phone"))
                    .listen(this.mPhoneStateListener, 32);
            return;
        }
        if (i == 2) {
            ((TelephonyManager) this.mContext.getSystemService("phone"))
                    .listen(this.mPhoneStateListener, 0);
            return;
        }
        if (i == 3) {
            try {
                register(this.mContext, (Looper) null, UserHandle.ALL, true);
                return;
            } catch (Exception e) {
                Log.e(TAG, "Error on registering package monitor! " + e);
                return;
            }
        }
        if (i != 4) {
            Log.e(str, "msg.what has invalid value. msg.what = " + message.what);
            return;
        }
        synchronized (this.mService.mLock) {
            try {
                Iterator it =
                        this.mService
                                .getSystemListenerContainer()
                                .mCustomEventMap
                                .keySet()
                                .iterator();
                while (it.hasNext()) {
                    if (((String) it.next()).startsWith("monitor_package_state")) {
                        Log.d(TAG, "PackageMonitor is still in use! DO NOT UNREGISTER!");
                        return;
                    }
                }
                unregister();
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
