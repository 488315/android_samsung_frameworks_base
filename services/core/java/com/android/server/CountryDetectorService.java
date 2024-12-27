package com.android.server;

import android.R;
import android.content.Context;
import android.location.Country;
import android.location.CountryListener;
import android.location.ICountryDetector;
import android.location.ICountryListener;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.text.TextUtils;
import android.util.Slog;
import com.android.internal.os.BackgroundThread;
import com.android.internal.util.DumpUtils;
import com.android.server.location.countrydetector.ComprehensiveCountryDetector;
import com.android.server.location.countrydetector.CountryDetectorBase;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class CountryDetectorService extends ICountryDetector.Stub {
    public final Context mContext;
    public CountryDetectorBase mCountryDetector;
    public final Handler mHandler;
    public CountryDetectorService$$ExternalSyntheticLambda0 mLocationBasedDetectorListener;
    public final HashMap mReceivers;
    public boolean mSystemReady;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Receiver implements IBinder.DeathRecipient {
        public final IBinder mKey;
        public final ICountryListener mListener;

        public Receiver(ICountryListener iCountryListener) {
            this.mListener = iCountryListener;
            this.mKey = iCountryListener.asBinder();
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            CountryDetectorService.this.removeListener(this.mKey);
        }

        public final boolean equals(Object obj) {
            if (obj instanceof Receiver) {
                return this.mKey.equals(((Receiver) obj).mKey);
            }
            return false;
        }

        public final int hashCode() {
            return this.mKey.hashCode();
        }
    }

    public CountryDetectorService(Context context) {
        this(context, BackgroundThread.getHandler());
    }

    public CountryDetectorService(Context context, Handler handler) {
        this.mReceivers = new HashMap();
        this.mContext = context;
        this.mHandler = handler;
    }

    public final void addCountryListener(ICountryListener iCountryListener) {
        if (!this.mSystemReady) {
            throw new RemoteException();
        }
        synchronized (this.mReceivers) {
            try {
                Receiver receiver = new Receiver(iCountryListener);
                try {
                    iCountryListener.asBinder().linkToDeath(receiver, 0);
                    Country detectCountry = detectCountry();
                    if (detectCountry != null) {
                        iCountryListener.onCountryDetected(detectCountry);
                    }
                    this.mReceivers.put(iCountryListener.asBinder(), receiver);
                    if (this.mReceivers.size() == 1) {
                        Slog.d("CountryDetector", "The first listener is added");
                        this.mHandler.post(new CountryDetectorService$$ExternalSyntheticLambda1(this, this.mLocationBasedDetectorListener, 0));
                    }
                } catch (RemoteException e) {
                    Slog.e("CountryDetector", "linkToDeath failed:", e);
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final Country detectCountry() {
        if (this.mSystemReady) {
            return this.mCountryDetector.detectCountry();
        }
        return null;
    }

    public final void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        DumpUtils.checkDumpPermission(this.mContext, "CountryDetector", printWriter);
    }

    public CountryDetectorBase getCountryDetector() {
        return this.mCountryDetector;
    }

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.server.CountryDetectorService$$ExternalSyntheticLambda0] */
    public void initialize() {
        CountryDetectorBase countryDetectorBase;
        String string = this.mContext.getString(R.string.data_usage_mobile_limit_title);
        if (!TextUtils.isEmpty(string)) {
            BinaryTransparencyService$$ExternalSyntheticOutline0.m("Using custom country detector class: ", string, "CountryDetector");
            try {
                countryDetectorBase = (CountryDetectorBase) Class.forName(string).asSubclass(CountryDetectorBase.class).getConstructor(Context.class).newInstance(this.mContext);
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | NoSuchMethodException | InvocationTargetException unused) {
                Slog.e("CountryDetector", "Could not instantiate the custom country detector class");
                countryDetectorBase = null;
            }
            this.mCountryDetector = countryDetectorBase;
        }
        if (this.mCountryDetector == null) {
            Slog.d("CountryDetector", "Using default country detector");
            this.mCountryDetector = new ComprehensiveCountryDetector(this.mContext);
        }
        this.mLocationBasedDetectorListener = new CountryListener() { // from class: com.android.server.CountryDetectorService$$ExternalSyntheticLambda0
            public final void onCountryDetected(Country country) {
                CountryDetectorService countryDetectorService = CountryDetectorService.this;
                countryDetectorService.mHandler.post(new CountryDetectorService$$ExternalSyntheticLambda1(countryDetectorService, country, 1));
            }
        };
    }

    public boolean isSystemReady() {
        return this.mSystemReady;
    }

    public final void removeCountryListener(ICountryListener iCountryListener) {
        if (!this.mSystemReady) {
            throw new RemoteException();
        }
        removeListener(iCountryListener.asBinder());
    }

    public final void removeListener(IBinder iBinder) {
        synchronized (this.mReceivers) {
            try {
                this.mReceivers.remove(iBinder);
                if (this.mReceivers.isEmpty()) {
                    this.mHandler.post(new CountryDetectorService$$ExternalSyntheticLambda1(this, null, 0));
                    Slog.d("CountryDetector", "No listener is left");
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
