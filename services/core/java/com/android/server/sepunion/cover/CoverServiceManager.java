package com.android.server.sepunion.cover;

import android.app.ActivityManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.content.pm.ResolveInfo;
import android.content.pm.ServiceInfo;
import android.os.Binder;
import android.os.Bundle;
import android.os.Debug;
import android.os.Handler;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Looper;
import android.os.Message;
import android.os.PowerManager;
import android.os.RemoteException;
import android.os.SemSystemProperties;
import android.os.SystemProperties;
import android.os.UserHandle;
import android.text.TextUtils;
import android.util.SparseArray;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.am.ActiveServices$$ExternalSyntheticOutline0;
import com.samsung.android.cover.CoverState;
import com.samsung.android.cover.ICoverService;
import com.samsung.android.cover.ISViewCoverBaseService;
import com.samsung.android.knoxguard.service.utils.Constants;
import com.samsung.android.sepunion.Log;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public final class CoverServiceManager {
    public static final boolean IS_B6_DEVICE;
    public static final boolean SUPPORT_FLIP_SUITCASE;
    public final Context mContext;
    public final SparseArray mCoverServices;
    public final OnCoverStateProvider mCoverStateProvider;
    public final PowerManager.WakeLock mCoverWakeLock;
    public final H mHandler;
    public AnonymousClass3 mWakeLockRunnable;
    public static final ComponentName LED_COVER = new ComponentName("com.sec.android.cover.ledcover", "com.sec.android.cover.ledcover.LedCoverService");
    public static final ComponentName LED_SUIT = new ComponentName("com.samsung.android.ledcasesuit", "com.samsung.android.ledcasesuit.LedCaseSuitService");
    public static final ComponentName SYSTEM_UI_COVER = new ComponentName(Constants.SYSTEMUI_PACKAGE_NAME, "com.android.systemui.cover.SysUICoverService");
    public static final ComponentName GAMEPACK_COVER = new ComponentName("com.sec.android.usb.fancontrol", "com.sec.android.usb.fancontrol.FanControlService");
    public final ArrayList mActiveServices = new ArrayList();
    public final HashMap mBindingTimestamp = new HashMap();
    public final Object mLock = new Object();
    public final String[] mBindHistory = new String[10];
    public int mBindHistoryIdx = 0;
    public boolean mRegisterBroadcast = false;
    public final AnonymousClass1 mBroadcastReceiver = new BroadcastReceiver() { // from class: com.android.server.sepunion.cover.CoverServiceManager.1
        /* JADX WARN: Removed duplicated region for block: B:49:0x011e  */
        /* JADX WARN: Removed duplicated region for block: B:50:0x0128  */
        @Override // android.content.BroadcastReceiver
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public final void onReceive(android.content.Context r14, android.content.Intent r15) {
            /*
                Method dump skipped, instructions count: 386
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.sepunion.cover.CoverServiceManager.AnonymousClass1.onReceive(android.content.Context, android.content.Intent):void");
        }
    };

    public abstract class CoverServiceInfo implements IBinder.DeathRecipient {
        public final IBinder binder;
        public final ComponentName component;
        public final ServiceConnection connection;
        public int disconnectionCount = 0;
        public final IInterface service;
        public final int type;
        public final UserHandle user;

        public CoverServiceInfo(ComponentName componentName, int i, IBinder iBinder, ServiceConnection serviceConnection, UserHandle userHandle) {
            this.component = componentName;
            this.type = i;
            this.binder = iBinder;
            this.service = asInterface(iBinder);
            this.connection = serviceConnection;
            this.user = userHandle;
        }

        public abstract IInterface asInterface(IBinder iBinder);

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            Log.d("CoverServiceInfo", "binderDied : ".concat(toString()));
            CoverServiceManager coverServiceManager = CoverServiceManager.this;
            ComponentName componentName = CoverServiceManager.LED_COVER;
            coverServiceManager.removeCoverServiceLocked(this);
        }

        public abstract int onCoverAppStateChanged(boolean z);

        public abstract void systemReady();

        public final String toString() {
            return String.format("CoverServiceInfo[component=%s, type=%s, service=%s, user=%s]", this.component, Integer.valueOf(this.type), this.service, this.user);
        }

        public abstract void updateCoverState(CoverState coverState);
    }

    public final class H extends Handler {
        public H(Looper looper) {
            super(looper, null, true);
        }

        /* JADX WARN: Multi-variable type inference failed */
        /* JADX WARN: Type inference failed for: r4v4, types: [com.android.server.sepunion.cover.CoverServiceManager$3, java.lang.Runnable] */
        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            if (i != 1) {
                if (i != 2) {
                    return;
                }
                CoverServiceInfo coverServiceInfo = (CoverServiceInfo) message.obj;
                CoverServiceManager coverServiceManager = CoverServiceManager.this;
                ComponentName componentName = coverServiceInfo.component;
                int i2 = coverServiceInfo.type;
                UserHandle userHandle = coverServiceInfo.user;
                ComponentName componentName2 = CoverServiceManager.LED_COVER;
                coverServiceManager.bindCoverServiceLocked(componentName, i2, userHandle);
                return;
            }
            final CoverServiceManager coverServiceManager2 = CoverServiceManager.this;
            CoverState coverState = (CoverState) message.obj;
            synchronized (coverServiceManager2.mLock) {
                try {
                    Iterator it = coverServiceManager2.mActiveServices.iterator();
                    while (it.hasNext()) {
                        ((CoverServiceInfo) it.next()).updateCoverState(coverState);
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            AnonymousClass3 anonymousClass3 = coverServiceManager2.mWakeLockRunnable;
            if (anonymousClass3 != null) {
                coverServiceManager2.mHandler.removeCallbacks(anonymousClass3);
            }
            ?? r4 = new Runnable() { // from class: com.android.server.sepunion.cover.CoverServiceManager.3
                @Override // java.lang.Runnable
                public final void run() {
                    if (CoverServiceManager.this.mCoverWakeLock.isHeld()) {
                        CoverServiceManager.this.mCoverWakeLock.release();
                    }
                }
            };
            coverServiceManager2.mWakeLockRunnable = r4;
            coverServiceManager2.mHandler.postDelayed(r4, 1000L);
        }
    }

    public interface OnCoverStateProvider {
        CoverState getCoverStateFromCoverServiceManager();
    }

    public final class SViewCoverBaseServiceInfo extends CoverServiceInfo {
        @Override // com.android.server.sepunion.cover.CoverServiceManager.CoverServiceInfo
        public final IInterface asInterface(IBinder iBinder) {
            return ISViewCoverBaseService.Stub.asInterface(iBinder);
        }

        @Override // com.android.server.sepunion.cover.CoverServiceManager.CoverServiceInfo
        public final int onCoverAppStateChanged(boolean z) {
            try {
                ISViewCoverBaseService iSViewCoverBaseService = this.service;
                if (iSViewCoverBaseService != null) {
                    return iSViewCoverBaseService.onCoverAppCovered(z);
                }
                return 0;
            } catch (RemoteException e) {
                e.printStackTrace();
                return 0;
            }
        }

        @Override // com.android.server.sepunion.cover.CoverServiceManager.CoverServiceInfo
        public final void systemReady() {
            try {
                ISViewCoverBaseService iSViewCoverBaseService = this.service;
                if (iSViewCoverBaseService != null) {
                    iSViewCoverBaseService.onSystemReady();
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override // com.android.server.sepunion.cover.CoverServiceManager.CoverServiceInfo
        public final void updateCoverState(CoverState coverState) {
            try {
                ISViewCoverBaseService iSViewCoverBaseService = this.service;
                if (iSViewCoverBaseService != null) {
                    iSViewCoverBaseService.updateCoverState(coverState);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    public final class SemCoverServiceInfo extends CoverServiceInfo {
        @Override // com.android.server.sepunion.cover.CoverServiceManager.CoverServiceInfo
        public final IInterface asInterface(IBinder iBinder) {
            return ICoverService.Stub.asInterface(iBinder);
        }

        @Override // com.android.server.sepunion.cover.CoverServiceManager.CoverServiceInfo
        public final int onCoverAppStateChanged(boolean z) {
            try {
                ICoverService iCoverService = this.service;
                if (iCoverService != null) {
                    return iCoverService.onCoverAppCovered(z);
                }
                return 0;
            } catch (RemoteException e) {
                e.printStackTrace();
                return 0;
            }
        }

        @Override // com.android.server.sepunion.cover.CoverServiceManager.CoverServiceInfo
        public final void systemReady() {
            try {
                ICoverService iCoverService = this.service;
                if (iCoverService != null) {
                    iCoverService.onSystemReady();
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }

        @Override // com.android.server.sepunion.cover.CoverServiceManager.CoverServiceInfo
        public final void updateCoverState(CoverState coverState) {
            try {
                ICoverService iCoverService = this.service;
                if (iCoverService != null) {
                    iCoverService.onUpdateCoverState(coverState);
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        }
    }

    /* renamed from: -$$Nest$mfindCoverServiceByComponentLocked, reason: not valid java name */
    public static int m876$$Nest$mfindCoverServiceByComponentLocked(CoverServiceManager coverServiceManager, ComponentName componentName) {
        synchronized (coverServiceManager.mLock) {
            try {
                int size = coverServiceManager.mCoverServices.size();
                for (int i = 0; i < size; i++) {
                    if (((ComponentName) coverServiceManager.mCoverServices.valueAt(i)).equals(componentName)) {
                        return coverServiceManager.mCoverServices.keyAt(i);
                    }
                }
                return 2;
            } finally {
            }
        }
    }

    static {
        SUPPORT_FLIP_SUITCASE = SystemProperties.getInt("ro.build.version.oneui", 0) >= 60101;
        IS_B6_DEVICE = TextUtils.equals("b6q", SemSystemProperties.get("ro.product.vendor.device", "NONE"));
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [com.android.server.sepunion.cover.CoverServiceManager$1] */
    public CoverServiceManager(Context context, Looper looper, OnCoverStateProvider onCoverStateProvider) {
        this.mContext = context;
        this.mCoverStateProvider = onCoverStateProvider;
        this.mHandler = new H(looper);
        PowerManager.WakeLock newWakeLock = ((PowerManager) context.getSystemService("power")).newWakeLock(1, "CoverServiceManager");
        this.mCoverWakeLock = newWakeLock;
        newWakeLock.setReferenceCounted(false);
        this.mCoverServices = queryInstalledCoverServices();
    }

    public static boolean verifySystemFeature(Context context, int i) {
        if (i == 0) {
            Feature.getInstance(context).getClass();
            return Feature.sIsSupportFlipCover;
        }
        if (i == 11) {
            Feature.getInstance(context).getClass();
            return Feature.sIsSupportNeonCover;
        }
        if (i == 7) {
            Feature.getInstance(context).getClass();
            return Feature.sIsSupportNfcLedCover && Feature.sIsNfcAuthSystemFeatureEnabled;
        }
        if (i == 8) {
            Feature.getInstance(context).getClass();
            return Feature.sIsSupportClearCover;
        }
        switch (i) {
            case 13:
                Feature.getInstance(context).getClass();
                return Feature.sIsSupportGamePackCover;
            case 14:
                Feature.getInstance(context).getClass();
                return Feature.sIsSupportLEDBackCover;
            case 15:
                Feature.getInstance(context).getClass();
                return Feature.sIsSupportClearSideViewCover;
            case 16:
                Feature.getInstance(context).getClass();
                return Feature.sIsSupportMiniSviewWalletCover;
            case 17:
                Feature.getInstance(context).getClass();
                return Feature.sIsSupportClearCameraViewCover;
            default:
                return false;
        }
    }

    public final void addBindingServiceLocked(ComponentName componentName) {
        synchronized (this.mLock) {
            this.mBindingTimestamp.put(componentName, Long.valueOf(System.currentTimeMillis()));
        }
    }

    public final void bindCoverService(int i, boolean z) {
        UserHandle userHandle;
        ComponentName componentName;
        Log.d("CoverManager_CoverServiceManager", "bindCoverService : type = " + i);
        if (isShouldNotBindCoverService(i, z)) {
            return;
        }
        UserHandle userHandle2 = UserHandle.SYSTEM;
        ComponentName coverServiceNameLocked = getCoverServiceNameLocked(i, false, true);
        if (coverServiceNameLocked != null) {
            userHandle = new UserHandle(ActivityManager.getCurrentUser());
        } else {
            coverServiceNameLocked = getPredefinedCoverServiceNameLocked(i);
            userHandle = userHandle2;
        }
        if (coverServiceNameLocked == null) {
            return;
        }
        if (bindCoverServiceLocked(coverServiceNameLocked, i, userHandle)) {
            registerBroadcastReceiver(coverServiceNameLocked.getPackageName());
        } else if (i == 13) {
            registerBroadcastReceiver("com.sec.android.usb.fancontrol");
            Intent intent = new Intent("com.sec.android.app.applinker.GAME_PACK_ADDED");
            intent.setPackage("com.sec.android.app.applinker");
            this.mContext.sendBroadcast(intent);
            return;
        }
        if (i == 255) {
            Feature.getInstance(this.mContext).getClass();
            if (Feature.sIsNfcAuthSystemFeatureEnabled) {
                Log.d("CoverManager_CoverServiceManager", "getSystemUICoverService : return because of nfc smart cover supporting nfc authentication");
                componentName = null;
                if (componentName != null || coverServiceNameLocked.equals(componentName)) {
                }
                bindCoverServiceLocked(componentName, i, userHandle2);
                return;
            }
        }
        componentName = SYSTEM_UI_COVER;
        if (componentName != null) {
        }
    }

    public final boolean bindCoverServiceLocked(ComponentName componentName, final int i, UserHandle userHandle) {
        boolean contains;
        if (componentName == null) {
            Log.e("CoverManager_CoverServiceManager", "bindCoverServiceLocked : component is null");
            return false;
        }
        synchronized (this.mLock) {
            contains = this.mBindingTimestamp.keySet().contains(componentName);
        }
        if (contains) {
            return false;
        }
        Intent intent = new Intent();
        intent.setComponent(componentName);
        try {
            ServiceConnection serviceConnection = new ServiceConnection() { // from class: com.android.server.sepunion.cover.CoverServiceManager.2
                @Override // android.content.ServiceConnection
                public final void onServiceConnected(ComponentName componentName2, IBinder iBinder) {
                    CoverServiceInfo semCoverServiceInfo;
                    ComponentName componentName3 = CoverServiceManager.LED_COVER;
                    Log.d("CoverManager_CoverServiceManager", "onServiceConnected : name = " + componentName2 + ", binder = " + iBinder);
                    synchronized (CoverServiceManager.this.mLock) {
                        try {
                            CoverServiceInfo findActiveServiceByComponentLocked = CoverServiceManager.this.findActiveServiceByComponentLocked(componentName2);
                            if (findActiveServiceByComponentLocked != null) {
                                CoverServiceManager.this.removeCoverServiceLocked(findActiveServiceByComponentLocked);
                            }
                            if (!CoverServiceManager.SYSTEM_UI_COVER.equals(componentName2) && CoverServiceManager.m876$$Nest$mfindCoverServiceByComponentLocked(CoverServiceManager.this, componentName2) == 2) {
                                semCoverServiceInfo = CoverServiceManager.this.new SViewCoverBaseServiceInfo(componentName2, i, iBinder, this, UserHandle.SYSTEM);
                                CoverServiceManager.this.updateBindHistoryLocked("bound:cn=" + componentName2 + ",type=" + i);
                                CoverServiceManager.this.mActiveServices.add(semCoverServiceInfo);
                                CoverServiceManager.this.addBindingServiceLocked(semCoverServiceInfo.component);
                                CoverServiceManager.this.getClass();
                            }
                            semCoverServiceInfo = CoverServiceManager.this.new SemCoverServiceInfo(componentName2, i, iBinder, this, new UserHandle(ActivityManager.getCurrentUser()));
                            CoverServiceManager.this.updateBindHistoryLocked("bound:cn=" + componentName2 + ",type=" + i);
                            CoverServiceManager.this.mActiveServices.add(semCoverServiceInfo);
                            CoverServiceManager.this.addBindingServiceLocked(semCoverServiceInfo.component);
                            CoverServiceManager.this.getClass();
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                    semCoverServiceInfo.systemReady();
                    semCoverServiceInfo.updateCoverState(CoverServiceManager.this.mCoverStateProvider.getCoverStateFromCoverServiceManager());
                }

                /* JADX WARN: Code restructure failed: missing block: B:11:0x0072, code lost:
                
                    if (com.android.server.sepunion.cover.CoverServiceManager.LED_COVER.equals(r4.component) == false) goto L21;
                 */
                /* JADX WARN: Code restructure failed: missing block: B:13:0x007e, code lost:
                
                    if (com.android.server.sepunion.cover.CoverServiceManager.m876$$Nest$mfindCoverServiceByComponentLocked(r7.this$0, r4.component) == r4.type) goto L23;
                 */
                @Override // android.content.ServiceConnection
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final void onServiceDisconnected(android.content.ComponentName r8) {
                    /*
                        r7 = this;
                        java.lang.String r0 = "onServiceDisconnected : give up to connect cover service, "
                        java.lang.String r1 = "onServiceDisconnected : retry to connect cover service, "
                        java.lang.String r2 = "unbound:cn="
                        android.content.ComponentName r3 = com.android.server.sepunion.cover.CoverServiceManager.LED_COVER
                        java.lang.String r3 = "CoverManager_CoverServiceManager"
                        java.lang.StringBuilder r4 = new java.lang.StringBuilder
                        java.lang.String r5 = "onServiceDisconnected : name = "
                        r4.<init>(r5)
                        r4.append(r8)
                        java.lang.String r4 = r4.toString()
                        com.samsung.android.sepunion.Log.d(r3, r4)
                        com.android.server.sepunion.cover.CoverServiceManager r3 = com.android.server.sepunion.cover.CoverServiceManager.this
                        java.lang.Object r3 = r3.mLock
                        monitor-enter(r3)
                        com.android.server.sepunion.cover.CoverServiceManager r4 = com.android.server.sepunion.cover.CoverServiceManager.this     // Catch: java.lang.Throwable -> L68
                        com.android.server.sepunion.cover.CoverServiceManager$CoverServiceInfo r4 = r4.findActiveServiceByComponentLocked(r8)     // Catch: java.lang.Throwable -> L68
                        if (r4 == 0) goto Lc2
                        com.android.server.sepunion.cover.CoverServiceManager r5 = com.android.server.sepunion.cover.CoverServiceManager.this     // Catch: java.lang.Throwable -> L68
                        r5.removeCoverServiceLocked(r4)     // Catch: java.lang.Throwable -> L68
                        com.android.server.sepunion.cover.CoverServiceManager r5 = com.android.server.sepunion.cover.CoverServiceManager.this     // Catch: java.lang.Throwable -> L68
                        java.lang.StringBuilder r6 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L68
                        r6.<init>(r2)     // Catch: java.lang.Throwable -> L68
                        r6.append(r8)     // Catch: java.lang.Throwable -> L68
                        java.lang.String r8 = ",type="
                        r6.append(r8)     // Catch: java.lang.Throwable -> L68
                        int r8 = r2     // Catch: java.lang.Throwable -> L68
                        r6.append(r8)     // Catch: java.lang.Throwable -> L68
                        java.lang.String r8 = r6.toString()     // Catch: java.lang.Throwable -> L68
                        r5.updateBindHistoryLocked(r8)     // Catch: java.lang.Throwable -> L68
                        int r8 = r4.type     // Catch: java.lang.Throwable -> L68
                        r2 = 7
                        if (r8 == r2) goto L55
                        r2 = 14
                        if (r8 != r2) goto L6a
                    L55:
                        boolean r8 = com.android.server.sepunion.cover.CoverServiceManager.IS_B6_DEVICE     // Catch: java.lang.Throwable -> L68
                        if (r8 == 0) goto L6a
                        boolean r8 = com.android.server.sepunion.cover.CoverServiceManager.SUPPORT_FLIP_SUITCASE     // Catch: java.lang.Throwable -> L68
                        if (r8 == 0) goto L6a
                        android.content.ComponentName r8 = com.android.server.sepunion.cover.CoverServiceManager.LED_SUIT     // Catch: java.lang.Throwable -> L68
                        android.content.ComponentName r2 = r4.component     // Catch: java.lang.Throwable -> L68
                        boolean r8 = r8.equals(r2)     // Catch: java.lang.Throwable -> L68
                        if (r8 == 0) goto L74
                        goto L80
                    L68:
                        r7 = move-exception
                        goto Lc4
                    L6a:
                        android.content.ComponentName r8 = com.android.server.sepunion.cover.CoverServiceManager.LED_COVER     // Catch: java.lang.Throwable -> L68
                        android.content.ComponentName r2 = r4.component     // Catch: java.lang.Throwable -> L68
                        boolean r8 = r8.equals(r2)     // Catch: java.lang.Throwable -> L68
                        if (r8 != 0) goto L80
                    L74:
                        com.android.server.sepunion.cover.CoverServiceManager r8 = com.android.server.sepunion.cover.CoverServiceManager.this     // Catch: java.lang.Throwable -> L68
                        android.content.ComponentName r2 = r4.component     // Catch: java.lang.Throwable -> L68
                        int r8 = com.android.server.sepunion.cover.CoverServiceManager.m876$$Nest$mfindCoverServiceByComponentLocked(r8, r2)     // Catch: java.lang.Throwable -> L68
                        int r2 = r4.type     // Catch: java.lang.Throwable -> L68
                        if (r8 != r2) goto Lc2
                    L80:
                        int r8 = r4.disconnectionCount     // Catch: java.lang.Throwable -> L68
                        int r8 = r8 + 1
                        r4.disconnectionCount = r8     // Catch: java.lang.Throwable -> L68
                        r2 = 5
                        if (r8 >= r2) goto Laf
                        java.lang.String r8 = "CoverManager_CoverServiceManager"
                        java.lang.StringBuilder r0 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L68
                        r0.<init>(r1)     // Catch: java.lang.Throwable -> L68
                        int r1 = r4.type     // Catch: java.lang.Throwable -> L68
                        r0.append(r1)     // Catch: java.lang.Throwable -> L68
                        java.lang.String r0 = r0.toString()     // Catch: java.lang.Throwable -> L68
                        com.samsung.android.sepunion.Log.d(r8, r0)     // Catch: java.lang.Throwable -> L68
                        com.android.server.sepunion.cover.CoverServiceManager r7 = com.android.server.sepunion.cover.CoverServiceManager.this     // Catch: java.lang.Throwable -> L68
                        com.android.server.sepunion.cover.CoverServiceManager$H r7 = r7.mHandler     // Catch: java.lang.Throwable -> L68
                        r8 = 2
                        r7.removeMessages(r8)     // Catch: java.lang.Throwable -> L68
                        r0 = 0
                        android.os.Message r8 = android.os.Message.obtain(r7, r8, r0, r0, r4)     // Catch: java.lang.Throwable -> L68
                        r0 = 1000(0x3e8, double:4.94E-321)
                        r7.sendMessageDelayed(r8, r0)     // Catch: java.lang.Throwable -> L68
                        goto Lc2
                    Laf:
                        java.lang.String r7 = "CoverManager_CoverServiceManager"
                        java.lang.StringBuilder r8 = new java.lang.StringBuilder     // Catch: java.lang.Throwable -> L68
                        r8.<init>(r0)     // Catch: java.lang.Throwable -> L68
                        int r0 = r4.type     // Catch: java.lang.Throwable -> L68
                        r8.append(r0)     // Catch: java.lang.Throwable -> L68
                        java.lang.String r8 = r8.toString()     // Catch: java.lang.Throwable -> L68
                        com.samsung.android.sepunion.Log.d(r7, r8)     // Catch: java.lang.Throwable -> L68
                    Lc2:
                        monitor-exit(r3)     // Catch: java.lang.Throwable -> L68
                        return
                    Lc4:
                        monitor-exit(r3)     // Catch: java.lang.Throwable -> L68
                        throw r7
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.server.sepunion.cover.CoverServiceManager.AnonymousClass2.onServiceDisconnected(android.content.ComponentName):void");
                }
            };
            Log.d("CoverManager_CoverServiceManager", "bindCoverServiceLocked : type = " + i);
            if (!this.mContext.bindServiceAsUser(intent, serviceConnection, 16777221, userHandle)) {
                Log.e("CoverManager_CoverServiceManager", "Unable to bind service: " + intent);
                return false;
            }
            addBindingServiceLocked(componentName);
            updateBindHistoryLocked("binding:cn=" + componentName + ",type=" + i);
            return true;
        } catch (SecurityException e) {
            Log.e("CoverManager_CoverServiceManager", "Unable to bind service: " + intent, e);
            return false;
        }
    }

    public final CoverServiceInfo findActiveServiceByComponentLocked(ComponentName componentName) {
        synchronized (this.mLock) {
            try {
                Iterator it = this.mActiveServices.iterator();
                while (it.hasNext()) {
                    CoverServiceInfo coverServiceInfo = (CoverServiceInfo) it.next();
                    if (componentName.equals(coverServiceInfo.component)) {
                        return coverServiceInfo;
                    }
                }
                return null;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final ComponentName getCoverServiceNameLocked(int i, boolean z, boolean z2) {
        synchronized (this.mLock) {
            if (i == 11 && z2) {
                try {
                    if (this.mCoverServices.size() == 0) {
                        this.mCoverServices.clear();
                        SparseArray queryInstalledCoverServices = queryInstalledCoverServices();
                        int size = queryInstalledCoverServices.size();
                        for (int i2 = 0; i2 < size; i2++) {
                            this.mCoverServices.put(queryInstalledCoverServices.keyAt(i2), (ComponentName) queryInstalledCoverServices.valueAt(i2));
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
            ComponentName componentName = (ComponentName) this.mCoverServices.get(i);
            if (componentName != null) {
                return componentName;
            }
            return z ? getPredefinedCoverServiceNameLocked(i) : null;
        }
    }

    public final ComponentName getPredefinedCoverServiceNameLocked(int i) {
        if (i != 7) {
            if (i == 255) {
                Feature.getInstance(this.mContext).getClass();
                if (!Feature.sIsNfcAuthSystemFeatureEnabled) {
                    return SYSTEM_UI_COVER;
                }
                Log.d("CoverManager_CoverServiceManager", "getPredefinedCoverServiceNameLocked : return because of nfc smart cover supporting nfc authentication");
                return null;
            }
            if (i == 13) {
                return GAMEPACK_COVER;
            }
            if (i != 14) {
                return SYSTEM_UI_COVER;
            }
        }
        return (IS_B6_DEVICE && SUPPORT_FLIP_SUITCASE) ? LED_SUIT : LED_COVER;
    }

    public final SparseArray getVerifiedCoverService(List list) {
        SparseArray sparseArray = new SparseArray();
        if (list != null && list.size() > 0) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                ServiceInfo serviceInfo = ((ResolveInfo) it.next()).serviceInfo;
                ComponentName componentName = new ComponentName(serviceInfo.packageName, serviceInfo.name);
                if ("com.samsung.android.permission.BIND_COVER_SERVICE".equals(serviceInfo.permission)) {
                    Bundle bundle = serviceInfo.metaData;
                    if (bundle == null) {
                        Log.w("CoverManager_CoverServiceManager", "service(" + serviceInfo.packageName + "/" + serviceInfo.name + ") has no meta data");
                    } else {
                        int i = bundle.getInt("com.samsung.android.cover.service", -1);
                        if (verifySystemFeature(this.mContext, i)) {
                            ComponentName componentName2 = (ComponentName) sparseArray.get(i);
                            if (componentName2 != null) {
                                StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "type(", ") of service(");
                                m.append(serviceInfo.packageName);
                                m.append("/");
                                m.append(serviceInfo.name);
                                m.append(") is duplicated  with ");
                                m.append(componentName2);
                                m.append(")");
                                Log.w("CoverManager_CoverServiceManager", m.toString());
                            } else {
                                sparseArray.put(i, componentName);
                            }
                        } else {
                            StringBuilder sb = new StringBuilder("service(");
                            sb.append(serviceInfo.packageName);
                            sb.append("/");
                            Log.w("CoverManager_CoverServiceManager", ActiveServices$$ExternalSyntheticOutline0.m(i, serviceInfo.name, ") has wrong cover type(", ")", sb));
                        }
                    }
                } else {
                    Log.w("CoverManager_CoverServiceManager", "service(" + serviceInfo.packageName + "/" + serviceInfo.name + ") has no permission");
                }
            }
        }
        return sparseArray;
    }

    public final boolean isShouldNotBindCoverService(int i, boolean z) {
        if (z || verifySystemFeature(this.mContext, i)) {
            if ((CoverTestModeUtils.SHIPPED ? -1 : CoverTestModeUtils.sCurrentTestMode) != 255) {
                return false;
            }
            Log.d("CoverManager_CoverServiceManager", "isShouldNotBindCoverService : return because of test mode for nfc smart cover");
            return true;
        }
        Log.d("CoverManager_CoverServiceManager", "isShouldNotBindCoverService : not support cover type(" + i + ")");
        return true;
    }

    public final SparseArray queryInstalledCoverServices() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            return getVerifiedCoverService(this.mContext.getPackageManager().queryIntentServicesAsUser(new Intent("com.samsung.android.cover.CoverService"), 786564, 0));
        } finally {
            Binder.restoreCallingIdentity(clearCallingIdentity);
        }
    }

    public final void registerBroadcastReceiver(String str) {
        if (this.mRegisterBroadcast) {
            return;
        }
        IntentFilter m = DirEncryptServiceHelper$$ExternalSyntheticOutline0.m("android.intent.action.PACKAGE_ADDED", "android.intent.action.PACKAGE_CHANGED");
        if (Debug.semIsProductDev()) {
            m.addAction("android.intent.action.PACKAGE_REPLACED");
        }
        m.addDataScheme("package");
        m.addDataSchemeSpecificPart(str, 0);
        this.mContext.registerReceiverAsUser(this.mBroadcastReceiver, UserHandle.ALL, m, null, null);
        this.mRegisterBroadcast = true;
    }

    public final void removeBindingServiceLocked(ComponentName componentName) {
        synchronized (this.mLock) {
            this.mBindingTimestamp.remove(componentName);
        }
    }

    public final void removeCoverServiceLocked(CoverServiceInfo coverServiceInfo) {
        synchronized (this.mLock) {
            this.mActiveServices.remove(coverServiceInfo);
        }
        removeBindingServiceLocked(coverServiceInfo.component);
    }

    public final void switchCoverService(int i, int i2) {
        CoverServiceInfo findActiveServiceByComponentLocked;
        Log.d("CoverManager_CoverServiceManager", "switchCoverService : type = " + i + " userId = " + i2);
        synchronized (this.mLock) {
            try {
                ComponentName componentName = (ComponentName) this.mCoverServices.get(i);
                if (componentName != null && (findActiveServiceByComponentLocked = findActiveServiceByComponentLocked(componentName)) != null) {
                    removeCoverServiceLocked(findActiveServiceByComponentLocked);
                    bindCoverServiceLocked(componentName, i, new UserHandle(ActivityManager.getCurrentUser()));
                    unbindCoverServiceLocked(findActiveServiceByComponentLocked);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void unbindActiveCoverService(int i) {
        CoverServiceInfo findActiveServiceByComponentLocked;
        Log.d("CoverManager_CoverServiceManager", "unbindActiveCoverService : type = " + i);
        synchronized (this.mLock) {
            try {
                ComponentName componentName = (ComponentName) this.mCoverServices.get(i);
                if (componentName != null && (findActiveServiceByComponentLocked = findActiveServiceByComponentLocked(componentName)) != null) {
                    removeCoverServiceLocked(findActiveServiceByComponentLocked);
                    unbindCoverServiceLocked(findActiveServiceByComponentLocked);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void unbindCoverServiceLocked(CoverServiceInfo coverServiceInfo) {
        if (coverServiceInfo == null) {
            Log.d("CoverManager_CoverServiceManager", "unbindCoverServiceLocked : info is null");
            return;
        }
        try {
            this.mContext.unbindService(coverServiceInfo.connection);
            updateBindHistoryLocked("unbinding:cn=" + coverServiceInfo.component + ",type=" + coverServiceInfo.type);
        } catch (IllegalArgumentException e) {
            Log.e("CoverManager_CoverServiceManager", " could not be unbound: " + coverServiceInfo.component + ", " + e);
        }
    }

    public final void updateBindHistoryLocked(String str) {
        Calendar calendar = Calendar.getInstance();
        String format = String.format(Locale.US, "[%02d-%02d %02d:%02d:%02d.%03d] %s", Integer.valueOf(calendar.get(2) + 1), Integer.valueOf(calendar.get(5)), Integer.valueOf(calendar.get(11)), Integer.valueOf(calendar.get(12)), Integer.valueOf(calendar.get(13)), Integer.valueOf(calendar.get(14)), str);
        String[] strArr = this.mBindHistory;
        int length = strArr.length;
        int i = this.mBindHistoryIdx;
        if (i >= 0 && i < length) {
            this.mBindHistoryIdx = i + 1;
            strArr[i] = format;
        }
        if (this.mBindHistoryIdx >= length) {
            this.mBindHistoryIdx = 0;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x005a  */
    /* JADX WARN: Removed duplicated region for block: B:5:0x004d  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0052  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void updateCoverState(com.samsung.android.cover.CoverState r10) {
        /*
            r9 = this;
            int r0 = r10.getType()
            r1 = 0
            android.content.ComponentName r0 = r9.getCoverServiceNameLocked(r0, r1, r1)
            r2 = 1
            if (r0 != 0) goto Le
        Lc:
            r4 = r1
            goto L4b
        Le:
            boolean r3 = r10.getAttachState()
            if (r3 != 0) goto L15
            goto Lc
        L15:
            int r3 = r10.getType()
            boolean r3 = r9.isShouldNotBindCoverService(r3, r1)
            if (r3 == 0) goto L20
            goto Lc
        L20:
            java.lang.Object r3 = r9.mLock
            monitor-enter(r3)
            com.android.server.sepunion.cover.CoverServiceManager$CoverServiceInfo r4 = r9.findActiveServiceByComponentLocked(r0)     // Catch: java.lang.Throwable -> L47
            if (r4 != 0) goto L49
            java.util.HashMap r4 = r9.mBindingTimestamp     // Catch: java.lang.Throwable -> L47
            java.lang.Object r4 = r4.get(r0)     // Catch: java.lang.Throwable -> L47
            java.lang.Long r4 = (java.lang.Long) r4     // Catch: java.lang.Throwable -> L47
            if (r4 == 0) goto L49
            long r5 = java.lang.System.currentTimeMillis()     // Catch: java.lang.Throwable -> L47
            long r7 = r4.longValue()     // Catch: java.lang.Throwable -> L47
            long r5 = r5 - r7
            r7 = 30000(0x7530, double:1.4822E-319)
            int r4 = (r5 > r7 ? 1 : (r5 == r7 ? 0 : -1))
            if (r4 <= 0) goto L44
            r4 = r2
            goto L45
        L44:
            r4 = r1
        L45:
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L47
            goto L4b
        L47:
            r9 = move-exception
            goto L71
        L49:
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L47
            goto Lc
        L4b:
            if (r4 == 0) goto L50
            r9.removeBindingServiceLocked(r0)
        L50:
            if (r4 == 0) goto L5a
            int r10 = r10.getType()
            r9.bindCoverService(r10, r1)
            goto L70
        L5a:
            android.os.PowerManager$WakeLock r0 = r9.mCoverWakeLock
            boolean r0 = r0.isHeld()
            if (r0 != 0) goto L67
            android.os.PowerManager$WakeLock r0 = r9.mCoverWakeLock
            r0.acquire()
        L67:
            com.android.server.sepunion.cover.CoverServiceManager$H r9 = r9.mHandler
            android.os.Message r9 = r9.obtainMessage(r2, r10)
            r9.sendToTarget()
        L70:
            return
        L71:
            monitor-exit(r3)     // Catch: java.lang.Throwable -> L47
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.sepunion.cover.CoverServiceManager.updateCoverState(com.samsung.android.cover.CoverState):void");
    }
}
