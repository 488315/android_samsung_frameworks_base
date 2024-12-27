package com.android.server.power;

import android.os.PowerManager;
import android.util.ArrayMap;
import com.android.internal.util.jobs.XmlUtils$$ExternalSyntheticOutline0;
import com.android.server.power.PowerManagerService;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class AdaptiveScreenOffTimeoutController implements Observer {
    public long mAdaptiveScreenOffTimeout = -1;
    public final ArrayMap mAdaptiveScreenOffTimeoutMap = new ArrayMap();
    public final PowerManagerService.AnonymousClass1 mCallbacks;

    public AdaptiveScreenOffTimeoutController(PowerManagerService.AnonymousClass1 anonymousClass1) {
        this.mCallbacks = anonymousClass1;
    }

    public final void addAdaptiveScreenOffTimeoutConfigLocked(List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            PowerManager.AdaptiveScreenOffTimeoutConfig adaptiveScreenOffTimeoutConfig = (PowerManager.AdaptiveScreenOffTimeoutConfig) it.next();
            String packageName = adaptiveScreenOffTimeoutConfig.getPackageName();
            Long valueOf = Long.valueOf(adaptiveScreenOffTimeoutConfig.getScreenOffTimeout());
            Slog.d("AdaptiveScreenOffTimeoutController", "AdaptiveScreenOffTimeoutConfig: " + packageName + ":" + valueOf);
            this.mAdaptiveScreenOffTimeoutMap.put(packageName, valueOf);
        }
        PowerManagerService.AnonymousClass1 anonymousClass1 = this.mCallbacks;
        PowerManagerService.this.mHandler.post(new PowerManagerService$2$$ExternalSyntheticLambda0(anonymousClass1, !this.mAdaptiveScreenOffTimeoutMap.isEmpty()));
    }

    public final List getAdaptiveScreenOffTimeoutConfigLocked() {
        ArrayList arrayList = new ArrayList();
        for (Map.Entry entry : this.mAdaptiveScreenOffTimeoutMap.entrySet()) {
            arrayList.add(new PowerManager.AdaptiveScreenOffTimeoutConfig((String) entry.getKey(), ((Long) entry.getValue()).longValue()));
        }
        return arrayList;
    }

    public final void removeAdaptiveScreenOffTimeoutConfigLocked(List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            String str = (String) it.next();
            Slog.d("AdaptiveScreenOffTimeoutController", "removeAdaptiveScreenOffTimeoutConfigLocked: " + str);
            this.mAdaptiveScreenOffTimeoutMap.remove(str);
        }
        PowerManagerService.AnonymousClass1 anonymousClass1 = this.mCallbacks;
        PowerManagerService.this.mHandler.post(new PowerManagerService$2$$ExternalSyntheticLambda0(anonymousClass1, !this.mAdaptiveScreenOffTimeoutMap.isEmpty()));
    }

    @Override // java.util.Observer
    public final void update(Observable observable, Object obj) {
        if (observable instanceof ForegroundPackageObserver) {
            String valueOf = String.valueOf(obj);
            long longValue = this.mAdaptiveScreenOffTimeoutMap.containsKey(valueOf) ? ((Long) this.mAdaptiveScreenOffTimeoutMap.get(valueOf)).longValue() : -1L;
            if (longValue != this.mAdaptiveScreenOffTimeout) {
                this.mAdaptiveScreenOffTimeout = longValue;
                StringBuilder sb = new StringBuilder("updateAdaptiveScreenOffTimeout: ");
                sb.append(this.mAdaptiveScreenOffTimeout);
                sb.append(longValue != -1 ? XmlUtils$$ExternalSyntheticOutline0.m("(", valueOf, ")") : "");
                Slog.d("AdaptiveScreenOffTimeoutController", sb.toString());
                PowerManagerService.AnonymousClass1 anonymousClass1 = this.mCallbacks;
                synchronized (PowerManagerService.this.mLock) {
                    PowerManagerService powerManagerService = PowerManagerService.this;
                    powerManagerService.mDirty |= 32;
                    powerManagerService.updatePowerStateLocked();
                }
            }
        }
    }
}
