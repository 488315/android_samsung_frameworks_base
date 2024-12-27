package com.android.server.location.injector;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import com.android.internal.util.Preconditions;
import com.android.server.DualAppManagerService$$ExternalSyntheticOutline0;
import com.android.server.location.LocationServiceThread;
import com.android.server.location.injector.SystemPackageResetHelper;
import java.util.Iterator;
import java.util.concurrent.CopyOnWriteArrayList;

public final class SystemPackageResetHelper {
    public final Context mContext;
    public Receiver mReceiver;
    public final CopyOnWriteArrayList mResponders = new CopyOnWriteArrayList();

    public final class Receiver extends BroadcastReceiver {
        public Receiver() {
        }

        @Override // android.content.BroadcastReceiver
        public final void onReceive(Context context, Intent intent) {
            Uri data;
            final String schemeSpecificPart;
            char c;
            final int i = 0;
            final int i2 = 1;
            String action = intent.getAction();
            if (action == null || (data = intent.getData()) == null || (schemeSpecificPart = data.getSchemeSpecificPart()) == null) {
                return;
            }
            switch (action.hashCode()) {
                case -1072806502:
                    if (action.equals("android.intent.action.QUERY_PACKAGE_RESTART")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case -757780528:
                    if (action.equals("android.intent.action.PACKAGE_RESTARTED")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 172491798:
                    if (action.equals("android.intent.action.PACKAGE_CHANGED")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case 525384130:
                    if (action.equals("android.intent.action.PACKAGE_REMOVED")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    String[] stringArrayExtra = intent.getStringArrayExtra("android.intent.extra.PACKAGES");
                    if (stringArrayExtra != null) {
                        int length = stringArrayExtra.length;
                        while (i < length) {
                            String str = stringArrayExtra[i];
                            Iterator it = SystemPackageResetHelper.this.mResponders.iterator();
                            while (it.hasNext()) {
                                if (((PackageResetHelper$Responder) it.next()).isResetableForPackage(str)) {
                                    setResultCode(-1);
                                    break;
                                }
                            }
                            i++;
                        }
                        break;
                    }
                    break;
                case 1:
                case 3:
                    LocationServiceThread.getExecutor().execute(new Runnable(this) { // from class: com.android.server.location.injector.SystemPackageResetHelper$Receiver$$ExternalSyntheticLambda0
                        public final /* synthetic */ SystemPackageResetHelper.Receiver f$0;

                        {
                            this.f$0 = this;
                        }

                        @Override // java.lang.Runnable
                        public final void run() {
                            switch (i2) {
                                case 0:
                                    SystemPackageResetHelper.Receiver receiver = this.f$0;
                                    SystemPackageResetHelper.this.notifyPackageReset(schemeSpecificPart);
                                    break;
                                default:
                                    SystemPackageResetHelper.Receiver receiver2 = this.f$0;
                                    SystemPackageResetHelper.this.notifyPackageReset(schemeSpecificPart);
                                    break;
                            }
                        }
                    });
                    break;
                case 2:
                    String[] stringArrayExtra2 = intent.getStringArrayExtra("android.intent.extra.changed_component_name_list");
                    if (stringArrayExtra2 != null) {
                        for (String str2 : stringArrayExtra2) {
                            if (schemeSpecificPart.equals(str2)) {
                                try {
                                    if (!context.getPackageManager().getApplicationInfo(schemeSpecificPart, PackageManager.ApplicationInfoFlags.of(0L)).enabled) {
                                        LocationServiceThread.getExecutor().execute(new Runnable(this) { // from class: com.android.server.location.injector.SystemPackageResetHelper$Receiver$$ExternalSyntheticLambda0
                                            public final /* synthetic */ SystemPackageResetHelper.Receiver f$0;

                                            {
                                                this.f$0 = this;
                                            }

                                            @Override // java.lang.Runnable
                                            public final void run() {
                                                switch (i) {
                                                    case 0:
                                                        SystemPackageResetHelper.Receiver receiver = this.f$0;
                                                        SystemPackageResetHelper.this.notifyPackageReset(schemeSpecificPart);
                                                        break;
                                                    default:
                                                        SystemPackageResetHelper.Receiver receiver2 = this.f$0;
                                                        SystemPackageResetHelper.this.notifyPackageReset(schemeSpecificPart);
                                                        break;
                                                }
                                            }
                                        });
                                        break;
                                    }
                                }
                            }
                        }
                        break;
                    }
                    break;
            }
        }
    }

    public SystemPackageResetHelper(Context context) {
        this.mContext = context;
    }

    public final void notifyPackageReset(String str) {
        DualAppManagerService$$ExternalSyntheticOutline0.m("package ", str, " reset", "LocationManagerService");
        Iterator it = this.mResponders.iterator();
        while (it.hasNext()) {
            ((PackageResetHelper$Responder) it.next()).onPackageReset(str);
        }
    }

    public final void onRegister() {
        Preconditions.checkState(this.mReceiver == null);
        this.mReceiver = new Receiver();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_CHANGED");
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_RESTARTED");
        intentFilter.addAction("android.intent.action.QUERY_PACKAGE_RESTART");
        intentFilter.addDataScheme("package");
        this.mContext.registerReceiver(this.mReceiver, intentFilter);
    }

    public final synchronized void register(PackageResetHelper$Responder packageResetHelper$Responder) {
        boolean isEmpty = this.mResponders.isEmpty();
        this.mResponders.add(packageResetHelper$Responder);
        if (isEmpty) {
            onRegister();
        }
    }

    public final synchronized void unregister(PackageResetHelper$Responder packageResetHelper$Responder) {
        this.mResponders.remove(packageResetHelper$Responder);
        if (this.mResponders.isEmpty()) {
            Preconditions.checkState(this.mReceiver != null);
            this.mContext.unregisterReceiver(this.mReceiver);
            this.mReceiver = null;
        }
    }
}
