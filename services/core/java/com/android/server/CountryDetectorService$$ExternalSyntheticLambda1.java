package com.android.server;

import android.location.Country;
import android.location.CountryListener;
import android.os.RemoteException;
import android.util.Slog;
import com.android.server.CountryDetectorService;
import java.util.Iterator;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class CountryDetectorService$$ExternalSyntheticLambda1 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ CountryDetectorService f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ CountryDetectorService$$ExternalSyntheticLambda1(CountryDetectorService countryDetectorService, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = countryDetectorService;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                CountryDetectorService countryDetectorService = this.f$0;
                countryDetectorService.mCountryDetector.setCountryListener((CountryListener) this.f$1);
                return;
            default:
                CountryDetectorService countryDetectorService2 = this.f$0;
                Country country = (Country) this.f$1;
                synchronized (countryDetectorService2.mReceivers) {
                    Iterator it = countryDetectorService2.mReceivers.values().iterator();
                    while (it.hasNext()) {
                        try {
                            ((CountryDetectorService.Receiver) it.next()).mListener.onCountryDetected(country);
                        } catch (RemoteException e) {
                            Slog.e("CountryDetector", "notifyReceivers failed:", e);
                        }
                    }
                }
                return;
        }
    }
}
