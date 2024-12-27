package com.android.server.location.gnss;

import android.content.Context;
import android.database.ContentObserver;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class GnssSatelliteBlocklistHelper {
    public final GnssSatelliteBlocklistCallback mCallback;
    public final Context mContext;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public interface GnssSatelliteBlocklistCallback {}

    public GnssSatelliteBlocklistHelper(
            Context context, Looper looper, GnssLocationProviderSec gnssLocationProviderSec) {
        this.mContext = context;
        this.mCallback = gnssLocationProviderSec;
        context.getContentResolver()
                .registerContentObserver(
                        Settings.Global.getUriFor("gnss_satellite_blocklist"),
                        true,
                        new ContentObserver(
                                new Handler(
                                        looper)) { // from class:
                                                   // com.android.server.location.gnss.GnssSatelliteBlocklistHelper.1
                            @Override // android.database.ContentObserver
                            public final void onChange(boolean z) {
                                GnssSatelliteBlocklistHelper.this.updateSatelliteBlocklist();
                            }
                        },
                        -1);
    }

    public static List parseSatelliteBlocklist(String str) throws NumberFormatException {
        String[] split = str.split(",");
        ArrayList arrayList = new ArrayList(split.length);
        for (String str2 : split) {
            String trim = str2.trim();
            if (!"".equals(trim)) {
                int parseInt = Integer.parseInt(trim);
                if (parseInt < 0) {
                    throw new NumberFormatException("Negative value is invalid.");
                }
                arrayList.add(Integer.valueOf(parseInt));
            }
        }
        return arrayList;
    }

    public final void updateSatelliteBlocklist() {
        String string =
                Settings.Global.getString(
                        this.mContext.getContentResolver(), "gnss_satellite_blocklist");
        if (string == null) {
            string = "";
        }
        Log.i("GnssBlocklistHelper", "Update GNSS satellite blocklist: ".concat(string));
        try {
            List parseSatelliteBlocklist = parseSatelliteBlocklist(string);
            if (parseSatelliteBlocklist.size() % 2 != 0) {
                Log.e(
                        "GnssBlocklistHelper",
                        "blocklist string has odd number of values.Aborting"
                            + " updateSatelliteBlocklist");
                return;
            }
            int size = parseSatelliteBlocklist.size() / 2;
            final int[] iArr = new int[size];
            final int[] iArr2 = new int[size];
            for (int i = 0; i < size; i++) {
                int i2 = i * 2;
                iArr[i] = ((Integer) parseSatelliteBlocklist.get(i2)).intValue();
                iArr2[i] = ((Integer) parseSatelliteBlocklist.get(i2 + 1)).intValue();
            }
            final GnssLocationProvider gnssLocationProvider = (GnssLocationProvider) this.mCallback;
            gnssLocationProvider.getClass();
            gnssLocationProvider.mHandler.post(
                    new Runnable() { // from class:
                                     // com.android.server.location.gnss.GnssLocationProvider$$ExternalSyntheticLambda21
                        @Override // java.lang.Runnable
                        public final void run() {
                            GnssLocationProvider gnssLocationProvider2 = GnssLocationProvider.this;
                            int[] iArr3 = iArr;
                            int[] iArr4 = iArr2;
                            gnssLocationProvider2.mGnssConfiguration.getClass();
                            GnssConfiguration.setSatelliteBlocklist(iArr3, iArr4);
                        }
                    });
            gnssLocationProvider.mGnssMetrics.mConstellationTypes = new boolean[8];
        } catch (NumberFormatException e) {
            Log.e("GnssBlocklistHelper", "Exception thrown when parsing blocklist string.", e);
        }
    }
}
