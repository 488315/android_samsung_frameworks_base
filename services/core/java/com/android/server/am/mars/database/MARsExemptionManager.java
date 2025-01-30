package com.android.server.am.mars.database;

import android.util.Slog;
import com.samsung.android.knox.custom.KnoxCustomManagerService;
import java.com.android.server.p028am.mars.database.MARsListManager;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

/* loaded from: classes.dex */
public class MARsExemptionManager {
    public static final Lock MARsExemptionManagerLock = new Lock();
    public ArrayList mExemptionList;

    public class Lock {
        public Lock() {
        }
    }

    public abstract class MARsExemptionManagerHolder {
        public static final MARsExemptionManager INSTANCE = new MARsExemptionManager();
    }

    public MARsExemptionManager() {
        this.mExemptionList = new ArrayList(Arrays.asList("com.sec.facatfunction", "com.samsung.android.aircommandmanager", "com.sec.facuifunction", "com.samsung.android.providers.factory", "com.sec.android.daemonapp", "com.samsung.android.smartsuggestions:core", "com.sec.phone", "com.sec.android.app.factorykeystring", "com.samsung.android.spay", "com.samsung.android.authfw", "com.samsung.android.samsungpass", "com.samsung.android.vtcamerasettings", "android.process.acore", "com.google.process.gservices", "com.samsung.android.bixby.agent", "com.samsung.android.bixby.wakeup", "com.samsung.android.knox.kpu", "com.samsung.android.knox.kpu.beta", "com.samsung.android.knox.kpu.demo", "com.samsung.android.knox.kpu.poc", "com.ftat", "com.sec.bcservice", "com.sec.dolbom", KnoxCustomManagerService.SCPM_V2_PROVIDER_PACKAGE_NAME, "com.samsung.android.scloud", "com.samsung.android.scs", "com.samsung.android.mobileservice", "com.samsung.cmh", "android.process.media", "com.samsung.android.bluelightfilter", "com.samsung.vvm", "com.google.android.apps.scone", "com.android.systemui", "com.android.systemui:screenshot", "android:ui", "com.samsung.android.dialer", "com.samsung.android.app.contacts", "com.samsung.android.sead", "com.samsung.android.sidegesturepad", "com.samsung.android.app.dressroom:accessory", "com.samsung.android.app.dressroom", "com.samsung.android.wallpaper.magician", "com.samsung.android.wallpaper.live", "com.samsung.android.messaging", "com.samsung.android.incallui", "com.samsung.android.app.aodservice:provider", "com.skt.skaf.OA00199800", "com.samsung.android.video", "com.android.vending", "com.xfinitymobile.cometcarrierservice", "com.samsung.android.app.telephonyui"));
    }

    public static MARsExemptionManager getInstance() {
        return MARsExemptionManagerHolder.INSTANCE;
    }

    public ArrayList getExemptionList() {
        return this.mExemptionList;
    }

    public boolean isFgExemptedFromMars(String str) {
        Iterator it = MARsListManager.getInstance().getFgsExemptionPackages().iterator();
        while (it.hasNext()) {
            if (((String) it.next()).equals(str)) {
                Slog.i("MARsExemptionManager", str + " is exempted from MARs");
                return true;
            }
        }
        return false;
    }

    public void setExemptionList(ArrayList arrayList) {
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            synchronized (MARsExemptionManagerLock) {
                if (!this.mExemptionList.contains(str)) {
                    this.mExemptionList.add(str);
                }
            }
        }
    }
}
