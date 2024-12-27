package com.android.server.wifi;

import android.annotation.SystemApi;
import android.os.SystemService;
import android.util.Log;

import java.util.NoSuchElementException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
@SystemApi(client = SystemApi.Client.SYSTEM_SERVER)
public class SupplicantManager {
    public static void start() {
        try {
            SystemService.start("wpa_supplicant");
        } catch (RuntimeException unused) {
            throw new NoSuchElementException("Failed to start Supplicant");
        }
    }

    public static void stop() {
        try {
            SystemService.stop("wpa_supplicant");
        } catch (RuntimeException e) {
            Log.w("SupplicantManager", "Failed to stop Supplicant", e);
        }
    }
}
