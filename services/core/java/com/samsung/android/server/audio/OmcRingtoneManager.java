package com.samsung.android.server.audio;

import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.SemSystemProperties;
import android.os.Trace;
import android.text.TextUtils;
import android.util.Log;

import com.android.server.NetworkScoreService$$ExternalSyntheticOutline0;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public final class OmcRingtoneManager {
    public static OmcRingtoneManager sInstance;
    public final List mExcludeRingtones = new ArrayList();
    public final List mExcludeNotifications = new ArrayList();

    public OmcRingtoneManager() {
        Trace.traceBegin(256L, "OmcRingtoneManager_Init");
        try {
            String str = SemSystemProperties.get("persist.sys.omc_respath");
            if (!TextUtils.isEmpty(str)) {
                getExcludedRingtonesFromCsc(1, str);
                getExcludedRingtonesFromCsc(2, str);
            }
        } finally {
            Trace.endSection();
        }
    }

    public final void getExcludedRingtonesFromCsc(int i, String str) {
        String m$1;
        if (i == 1) {
            m$1 =
                    ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                            str, "/media/audio/ringtones/exclude_ringtones");
            if (((ArrayList) this.mExcludeRingtones).size() != 0) {
                ((ArrayList) this.mExcludeRingtones).clear();
            }
        } else {
            m$1 =
                    ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                            str, "/media/audio/notifications/exclude_notifications");
            if (((ArrayList) this.mExcludeNotifications).size() != 0) {
                ((ArrayList) this.mExcludeNotifications).clear();
            }
        }
        File file = new File(m$1);
        if (!file.exists()) {
            NetworkScoreService$$ExternalSyntheticOutline0.m(
                    i, "There is no sounds(type:", ") list to remove", "AS.OmcRingtoneManager");
            return;
        }
        try {
            BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
            while (true) {
                try {
                    String readLine = bufferedReader.readLine();
                    if (readLine == null) {
                        bufferedReader.close();
                        return;
                    } else if (i == 1) {
                        ((ArrayList) this.mExcludeRingtones).add(readLine);
                    } else {
                        ((ArrayList) this.mExcludeNotifications).add(readLine);
                    }
                } finally {
                }
            }
        } catch (Exception e) {
            Log.e("AS.OmcRingtoneManager", "File exception", e);
        }
    }
}
