package com.android.server.location.gnss;

import android.location.GnssStatus;
import android.os.RemoteException;
import android.util.Log;
import android.util.Pair;
import com.android.server.audio.AudioDeviceInventory$$ExternalSyntheticOutline0;
import com.android.server.location.gnss.GnssMetrics;
import com.android.server.location.gnss.sec.SLocationProxy;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class GnssLocationProvider$$ExternalSyntheticLambda14 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ GnssLocationProvider f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ GnssLocationProvider$$ExternalSyntheticLambda14(GnssLocationProvider gnssLocationProvider, Object obj, int i) {
        this.$r8$classId = i;
        this.f$0 = gnssLocationProvider;
        this.f$1 = obj;
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                GnssLocationProvider gnssLocationProvider = this.f$0;
                GnssStatus gnssStatus = (GnssStatus) this.f$1;
                GnssMetrics gnssMetrics = gnssLocationProvider.mGnssMetrics;
                gnssMetrics.getClass();
                if (gnssStatus.getSatelliteCount() != 0) {
                    ArrayList arrayList = new ArrayList(gnssStatus.getSatelliteCount());
                    for (int i = 0; i < gnssStatus.getSatelliteCount(); i++) {
                        if (GnssMetrics.isL5Sv(gnssStatus.getCarrierFrequencyHz(i))) {
                            arrayList.add(Float.valueOf(gnssStatus.getCn0DbHz(i)));
                        }
                    }
                    if (arrayList.size() >= 4) {
                        Collections.sort(arrayList);
                        if (((Float) arrayList.get(arrayList.size() - 4)).floatValue() > 0.0d) {
                            double d = 0.0d;
                            for (int size = arrayList.size() - 4; size < arrayList.size(); size++) {
                                d += ((Float) arrayList.get(size)).floatValue();
                            }
                            double d2 = d / 4.0d;
                            gnssMetrics.mTopFourAverageCn0StatisticsL5.addItem(d2);
                            gnssMetrics.mL5TopFourAverageCn0DbmHzReportsStatistics.addItem(d2 * 1000.0d);
                        }
                    }
                }
                int satelliteCount = gnssStatus.getSatelliteCount();
                GnssMetrics.GnssPowerMetrics gnssPowerMetrics = gnssMetrics.mGnssPowerMetrics;
                if (satelliteCount == 0) {
                    gnssPowerMetrics.reportSignalQuality(null);
                } else {
                    int satelliteCount2 = gnssStatus.getSatelliteCount();
                    float[] fArr = new float[satelliteCount2];
                    for (int i2 = 0; i2 < gnssStatus.getSatelliteCount(); i2++) {
                        fArr[i2] = gnssStatus.getCn0DbHz(i2);
                    }
                    Arrays.sort(fArr);
                    gnssPowerMetrics.reportSignalQuality(fArr);
                    if (satelliteCount2 >= 4) {
                        int i3 = satelliteCount2 - 4;
                        double d3 = 0.0d;
                        if (fArr[i3] > 0.0d) {
                            while (i3 < satelliteCount2) {
                                d3 += fArr[i3];
                                i3++;
                            }
                            double d4 = d3 / 4.0d;
                            gnssMetrics.mTopFourAverageCn0Statistics.addItem(d4);
                            gnssMetrics.mTopFourAverageCn0DbmHzReportsStatistics.addItem(d4 * 1000.0d);
                        }
                    }
                }
                if (GnssLocationProvider.VERBOSE) {
                    Log.v("GnssLocationProvider", "SV count: " + gnssStatus.getSatelliteCount());
                }
                HashSet hashSet = new HashSet();
                int i4 = 0;
                int i5 = 0;
                int i6 = 0;
                for (int i7 = 0; i7 < gnssStatus.getSatelliteCount(); i7++) {
                    if (gnssStatus.usedInFix(i7)) {
                        hashSet.add(new Pair(Integer.valueOf(gnssStatus.getConstellationType(i7)), Integer.valueOf(gnssStatus.getSvid(i7))));
                        i4++;
                        if (gnssStatus.getCn0DbHz(i7) > i6) {
                            i6 = (int) gnssStatus.getCn0DbHz(i7);
                        }
                        i5 = (int) (gnssStatus.getCn0DbHz(i7) + i5);
                        int constellationType = gnssStatus.getConstellationType(i7);
                        boolean[] zArr = gnssMetrics.mConstellationTypes;
                        if (constellationType >= zArr.length) {
                            AudioDeviceInventory$$ExternalSyntheticOutline0.m(constellationType, "Constellation type ", " is not valid.", "GnssMetrics");
                        } else {
                            zArr[constellationType] = true;
                        }
                    }
                }
                if (i4 > 0) {
                    i5 /= i4;
                }
                gnssLocationProvider.handleReportSvStatusSec(gnssStatus);
                GnssManagerService$$ExternalSyntheticLambda0 gnssManagerService$$ExternalSyntheticLambda0 = gnssLocationProvider.mSvCallback;
                if (gnssManagerService$$ExternalSyntheticLambda0 != null) {
                    SLocationProxy sLocationProxy = gnssManagerService$$ExternalSyntheticLambda0.f$0;
                    if (sLocationProxy.mSLocationService != null) {
                        int satelliteCount3 = gnssStatus.getSatelliteCount();
                        int[] iArr = new int[satelliteCount3];
                        float[] fArr2 = new float[satelliteCount3];
                        float[] fArr3 = new float[satelliteCount3];
                        float[] fArr4 = new float[satelliteCount3];
                        float[] fArr5 = new float[satelliteCount3];
                        float[] fArr6 = new float[satelliteCount3];
                        for (int i8 = 0; i8 < gnssStatus.getSatelliteCount(); i8++) {
                            iArr[i8] = gnssStatus.getSvid(i8);
                            fArr2[i8] = gnssStatus.getCn0DbHz(i8);
                            fArr3[i8] = gnssStatus.getElevationDegrees(i8);
                            fArr4[i8] = gnssStatus.getAzimuthDegrees(i8);
                            fArr5[i8] = gnssStatus.getCarrierFrequencyHz(i8);
                            fArr6[i8] = gnssStatus.getBasebandCn0DbHz(i8);
                        }
                        try {
                            sLocationProxy.mSLocationService.onSvStatusChanged(satelliteCount3, iArr, fArr2, fArr3, fArr4, fArr5, fArr6);
                        } catch (RemoteException e) {
                            Log.e("SLocationProxy", e.toString());
                        }
                    }
                }
                gnssLocationProvider.mLocationExtras.set(hashSet.size(), i5, i6);
                for (int i9 = 0; i9 < gnssStatus.getSatelliteCount(); i9++) {
                    if (gnssStatus.hasCarrierFrequencyHz(i9)) {
                        gnssMetrics.mNumSvStatus++;
                        gnssMetrics.mSvStatusReports++;
                        boolean isL5Sv = GnssMetrics.isL5Sv(gnssStatus.getCarrierFrequencyHz(i9));
                        if (isL5Sv) {
                            gnssMetrics.mNumL5SvStatus++;
                            gnssMetrics.mL5SvStatusReports++;
                        }
                        if (gnssStatus.usedInFix(i9)) {
                            gnssMetrics.mNumSvStatusUsedInFix++;
                            gnssMetrics.mSvStatusReportsUsedInFix++;
                            if (isL5Sv) {
                                gnssMetrics.mNumL5SvStatusUsedInFix++;
                                gnssMetrics.mL5SvStatusReportsUsedInFix++;
                            }
                        }
                    }
                }
                return;
            default:
                GnssLocationProvider gnssLocationProvider2 = this.f$0;
                Runnable runnable = (Runnable) this.f$1;
                gnssLocationProvider2.getClass();
                try {
                    runnable.run();
                    return;
                } finally {
                    gnssLocationProvider2.mWakeLock.release();
                }
        }
    }
}
