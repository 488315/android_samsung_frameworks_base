package com.android.server.knox;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.util.Log;

import dalvik.system.DexClassLoader;

import com.samsung.android.knox.IBasicCommand;

import java.lang.reflect.InvocationTargetException;

public final class KnoxForesightService extends IBasicCommand.Stub {
    public static String KFAgentVersion;
    public static int sClassLoadCount;
    public static KnoxForesightService sInstance;
    public AnonymousClass1 clientThread;
    public DexClassLoader dexClassLoader;
    public Context mContext;

    public final class ForesightPackageReceiver extends BroadcastReceiver {
        public ForesightPackageReceiver() {}

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            Log.d("KnoxForesightService", "ForesightPackageReceiver: " + intent);
            String action = intent.getAction();
            action.getClass();
            if (!action.equals("android.intent.action.PACKAGE_ADDED")) {
                Log.w("KnoxForesightService", "Received unknown intent: " + intent);
            } else {
                KnoxForesightService.this.getClass();
                Uri data = intent.getData();
                if ("com.samsung.android.knox.foresight"
                        .equals(data != null ? data.getSchemeSpecificPart() : null)) {
                    KnoxForesightService.this.initializeKnoxForesight();
                } else {
                    Log.d("KnoxForesightService", "package name not matched");
                }
            }
        }
    }

    public static synchronized KnoxForesightService getInstance(Context context) {
        KnoxForesightService knoxForesightService;
        synchronized (KnoxForesightService.class) {
            try {
                if (sInstance == null) {
                    KnoxForesightService knoxForesightService2 = new KnoxForesightService();
                    knoxForesightService2.mContext = context;
                    IntentFilter intentFilter = new IntentFilter();
                    intentFilter.addAction("android.intent.action.PACKAGE_ADDED");
                    intentFilter.addDataScheme("package");
                    intentFilter.addDataSchemeSpecificPart("com.samsung.android.knox.foresight", 0);
                    context.registerReceiver(
                            knoxForesightService2.new ForesightPackageReceiver(),
                            intentFilter,
                            null,
                            null);
                    sInstance = knoxForesightService2;
                }
                knoxForesightService = sInstance;
            } catch (Throwable th) {
                throw th;
            }
        }
        return knoxForesightService;
    }

    public final void initializeKnoxForesight() {
        String str;
        if (this.mContext
                        .getPackageManager()
                        .checkSignatures("android", "com.samsung.android.knox.foresight")
                != 0) {
            Log.d("KnoxForesightService", "signature is not matched, ignore");
            return;
        }
        try {
            PackageInfo packageInfo =
                    this.mContext
                            .getPackageManager()
                            .getPackageInfo("com.samsung.android.knox.foresight", 8192);
            String str2 = packageInfo.applicationInfo.publicSourceDir;
            this.clientThread =
                    new Thread() { // from class: com.android.server.knox.KnoxForesightService.1
                        @Override // java.lang.Thread, java.lang.Runnable
                        public final void run() {
                            Log.d(
                                    "KnoxForesightService",
                                    "systemclassloader : "
                                            + ClassLoader.getSystemClassLoader().toString());
                            try {
                                getContextClassLoader()
                                        .loadClass(
                                                "com.samsung.android.knox.foresight.framework.system.SystemServiceEntry")
                                        .getMethod("initialize", Context.class)
                                        .invoke(null, KnoxForesightService.this.mContext);
                            } catch (ClassNotFoundException | NoSuchMethodException e) {
                                e.printStackTrace();
                            } catch (IllegalAccessException e2) {
                                e2.printStackTrace();
                            } catch (InvocationTargetException e3) {
                                e3.printStackTrace();
                            } catch (Exception e4) {
                                e4.printStackTrace();
                            }
                        }
                    };
            if (this.dexClassLoader != null && (str = KFAgentVersion) != null) {
                int i = sClassLoadCount;
                sClassLoadCount = i + 1;
                if (i >= 5 || str.equals(packageInfo.versionName)) {
                    Log.d(
                            "KnoxForesightService",
                            "ignore for max load count exceeded "
                                    + sClassLoadCount
                                    + "/"
                                    + KFAgentVersion);
                    setContextClassLoader(this.dexClassLoader);
                    start();
                }
            }
            KFAgentVersion = packageInfo.versionName;
            this.dexClassLoader =
                    new DexClassLoader(str2, null, null, this.mContext.getClass().getClassLoader());
            setContextClassLoader(this.dexClassLoader);
            start();
        } catch (PackageManager.NameNotFoundException unused) {
            Log.d("KnoxForesightService", "KnoxForesight is not installed");
        }
    }

    public final Bundle sendCmd(Bundle bundle) {
        Log.d("KnoxForesightService", "sendCmd " + bundle);
        if (this.mContext.getPackageManager().checkSignatures(1000, Binder.getCallingUid()) != 0) {
            Log.d("KnoxForesightService", "caller not allowed : " + Binder.getCallingUid());
            throw new SecurityException("caller not allowed : " + Binder.getCallingUid());
        }
        String string = bundle.getString("cmd");
        if (string == null) {
            Log.d("KnoxForesightService", "invalid cmd received");
            return null;
        }
        if (string.equals("initialize")) {
            long clearCallingIdentity = Binder.clearCallingIdentity();
            try {
                initializeKnoxForesight();
            } finally {
                Binder.restoreCallingIdentity(clearCallingIdentity);
            }
        } else {
            Log.d("KnoxForesightService", "unknown cmd received");
        }
        return null;
    }

    public final void setCaller(IBasicCommand iBasicCommand) {
        Log.d("KnoxForesightService", "setCaller");
        if (this.mContext.getPackageManager().checkSignatures(1000, Binder.getCallingUid()) == 0) {
            return;
        }
        Log.d("KnoxForesightService", "caller not allowed : " + Binder.getCallingUid());
        throw new SecurityException("caller not allowed : " + Binder.getCallingUid());
    }
}
