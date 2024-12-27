package com.android.server.location.countrydetector;

import android.content.Context;
import android.location.Country;
import android.location.CountryListener;
import android.os.SystemClock;
import android.telephony.PhoneStateListener;
import android.telephony.ServiceState;
import android.telephony.TelephonyManager;
import java.util.Iterator;
import java.util.Timer;
import java.util.concurrent.ConcurrentLinkedQueue;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class ComprehensiveCountryDetector extends CountryDetectorBase {
    public int mCountServiceStateChanges;
    public Country mCountry;
    public Country mCountryFromLocation;
    public final ConcurrentLinkedQueue mDebugLogs;
    public Country mLastCountryAddedToLogs;
    public final AnonymousClass1 mLocationBasedCountryDetectionListener;
    public LocationBasedCountryDetector mLocationBasedCountryDetector;
    public Timer mLocationRefreshTimer;
    public final Object mObject;
    public AnonymousClass4 mPhoneStateListener;
    public long mStartTime;
    public long mStopTime;
    public final boolean mStopped;
    public final TelephonyManager mTelephonyManager;
    public int mTotalCountServiceStateChanges;
    public long mTotalTime;

    /* JADX WARN: Type inference failed for: r0v3, types: [com.android.server.location.countrydetector.ComprehensiveCountryDetector$1] */
    public ComprehensiveCountryDetector(Context context) {
        super(context);
        this.mStopped = false;
        this.mDebugLogs = new ConcurrentLinkedQueue();
        this.mObject = new Object();
        this.mLocationBasedCountryDetectionListener = new CountryListener() { // from class: com.android.server.location.countrydetector.ComprehensiveCountryDetector.1
            public final void onCountryDetected(Country country) {
                ComprehensiveCountryDetector comprehensiveCountryDetector = ComprehensiveCountryDetector.this;
                comprehensiveCountryDetector.mCountryFromLocation = country;
                comprehensiveCountryDetector.detectCountry(true, false);
                ComprehensiveCountryDetector.this.stopLocationBasedDetector();
            }
        };
        this.mTelephonyManager = (TelephonyManager) context.getSystemService("phone");
    }

    public final synchronized void cancelLocationRefresh() {
        Timer timer = this.mLocationRefreshTimer;
        if (timer != null) {
            timer.cancel();
            this.mLocationRefreshTimer = null;
        }
    }

    @Override // com.android.server.location.countrydetector.CountryDetectorBase
    public final Country detectCountry() {
        return detectCountry(false, !this.mStopped);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0024  */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0028  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0054  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0080  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0050  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.location.Country detectCountry(final boolean r10, final boolean r11) {
        /*
            r9 = this;
            android.telephony.TelephonyManager r0 = r9.mTelephonyManager
            int r0 = r0.getPhoneType()
            r1 = 0
            r2 = 1
            if (r0 != r2) goto Lb
            goto Lc
        Lb:
            r2 = r1
        Lc:
            r0 = 0
            if (r2 == 0) goto L21
            android.telephony.TelephonyManager r2 = r9.mTelephonyManager
            java.lang.String r2 = r2.getNetworkCountryIso()
            boolean r3 = android.text.TextUtils.isEmpty(r2)
            if (r3 != 0) goto L21
            android.location.Country r3 = new android.location.Country
            r3.<init>(r2, r1)
            goto L22
        L21:
            r3 = r0
        L22:
            if (r3 != 0) goto L26
            android.location.Country r3 = r9.mCountryFromLocation
        L26:
            if (r3 != 0) goto L3d
            android.telephony.TelephonyManager r1 = r9.mTelephonyManager
            java.lang.String r1 = r1.getSimCountryIso()
            boolean r2 = android.text.TextUtils.isEmpty(r1)
            if (r2 != 0) goto L3c
            android.location.Country r2 = new android.location.Country
            r3 = 2
            r2.<init>(r1, r3)
            r3 = r2
            goto L3d
        L3c:
            r3 = r0
        L3d:
            if (r3 != 0) goto L50
            java.util.Locale r1 = java.util.Locale.getDefault()
            if (r1 == 0) goto L51
            android.location.Country r0 = new android.location.Country
            java.lang.String r1 = r1.getCountry()
            r2 = 3
            r0.<init>(r1, r2)
            goto L51
        L50:
            r0 = r3
        L51:
            if (r0 != 0) goto L54
            goto L7c
        L54:
            java.lang.Object r1 = r9.mObject
            monitor-enter(r1)
            android.location.Country r2 = r9.mLastCountryAddedToLogs     // Catch: java.lang.Throwable -> L63
            if (r2 == 0) goto L65
            boolean r2 = r2.equals(r0)     // Catch: java.lang.Throwable -> L63
            if (r2 == 0) goto L65
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L63
            goto L7c
        L63:
            r9 = move-exception
            goto L9a
        L65:
            r9.mLastCountryAddedToLogs = r0     // Catch: java.lang.Throwable -> L63
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L63
            java.util.concurrent.ConcurrentLinkedQueue r1 = r9.mDebugLogs
            int r1 = r1.size()
            r2 = 20
            if (r1 < r2) goto L77
            java.util.concurrent.ConcurrentLinkedQueue r1 = r9.mDebugLogs
            r1.poll()
        L77:
            java.util.concurrent.ConcurrentLinkedQueue r1 = r9.mDebugLogs
            r1.add(r0)
        L7c:
            android.location.Country r1 = r9.mCountry
            if (r1 == 0) goto L87
            android.location.Country r1 = new android.location.Country
            android.location.Country r2 = r9.mCountry
            r1.<init>(r2)
        L87:
            r3 = r1
            android.os.Handler r7 = r9.mHandler
            com.android.server.location.countrydetector.ComprehensiveCountryDetector$2 r8 = new com.android.server.location.countrydetector.ComprehensiveCountryDetector$2
            r1 = r8
            r2 = r9
            r4 = r0
            r5 = r10
            r6 = r11
            r1.<init>()
            r7.post(r8)
            r9.mCountry = r0
            return r0
        L9a:
            monitor-exit(r1)     // Catch: java.lang.Throwable -> L63
            throw r9
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.location.countrydetector.ComprehensiveCountryDetector.detectCountry(boolean, boolean):android.location.Country");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r5v3, types: [android.telephony.PhoneStateListener, com.android.server.location.countrydetector.ComprehensiveCountryDetector$4] */
    @Override // com.android.server.location.countrydetector.CountryDetectorBase
    public final void setCountryListener(CountryListener countryListener) {
        CountryListener countryListener2 = this.mListener;
        this.mListener = countryListener;
        if (countryListener == null) {
            synchronized (this) {
                AnonymousClass4 anonymousClass4 = this.mPhoneStateListener;
                if (anonymousClass4 != null) {
                    this.mTelephonyManager.listen(anonymousClass4, 0);
                    this.mPhoneStateListener = null;
                }
            }
            stopLocationBasedDetector();
            cancelLocationRefresh();
            long elapsedRealtime = SystemClock.elapsedRealtime();
            this.mStopTime = elapsedRealtime;
            this.mTotalTime += elapsedRealtime;
            return;
        }
        if (countryListener2 == null) {
            synchronized (this) {
                if (this.mPhoneStateListener == null) {
                    ?? r5 = new PhoneStateListener() { // from class: com.android.server.location.countrydetector.ComprehensiveCountryDetector.4
                        @Override // android.telephony.PhoneStateListener
                        public final void onServiceStateChanged(ServiceState serviceState) {
                            ComprehensiveCountryDetector comprehensiveCountryDetector = ComprehensiveCountryDetector.this;
                            comprehensiveCountryDetector.mCountServiceStateChanges++;
                            comprehensiveCountryDetector.mTotalCountServiceStateChanges++;
                            if (comprehensiveCountryDetector.mTelephonyManager.getPhoneType() == 1) {
                                ComprehensiveCountryDetector.this.detectCountry(true, true);
                            }
                        }
                    };
                    this.mPhoneStateListener = r5;
                    this.mTelephonyManager.listen(r5, 1);
                }
            }
            detectCountry(false, true);
            this.mStartTime = SystemClock.elapsedRealtime();
            this.mStopTime = 0L;
            this.mCountServiceStateChanges = 0;
        }
    }

    public final synchronized void stopLocationBasedDetector() {
        LocationBasedCountryDetector locationBasedCountryDetector = this.mLocationBasedCountryDetector;
        if (locationBasedCountryDetector != null) {
            locationBasedCountryDetector.stop();
            this.mLocationBasedCountryDetector = null;
        }
    }

    public final String toString() {
        long elapsedRealtime = SystemClock.elapsedRealtime();
        StringBuilder sb = new StringBuilder("ComprehensiveCountryDetector{");
        long j = 0;
        if (this.mStopTime == 0) {
            j = elapsedRealtime - this.mStartTime;
            sb.append("timeRunning=" + j + ", ");
        } else {
            sb.append("lastRunTimeLength=" + (this.mStopTime - this.mStartTime) + ", ");
        }
        sb.append("totalCountServiceStateChanges=" + this.mTotalCountServiceStateChanges + ", ");
        sb.append("currentCountServiceStateChanges=" + this.mCountServiceStateChanges + ", ");
        sb.append("totalTime=" + (this.mTotalTime + j) + ", ");
        sb.append("currentTime=" + elapsedRealtime + ", ");
        sb.append("countries=");
        Iterator it = this.mDebugLogs.iterator();
        while (it.hasNext()) {
            sb.append("\n   " + ((Country) it.next()).toString());
        }
        sb.append("}");
        return sb.toString();
    }
}
