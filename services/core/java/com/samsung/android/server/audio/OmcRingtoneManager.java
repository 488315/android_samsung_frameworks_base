package com.samsung.android.server.audio;

import android.os.SemSystemProperties;
import android.os.Trace;
import android.text.TextUtils;
import android.util.Log;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes2.dex */
public class OmcRingtoneManager {
    public static OmcRingtoneManager sInstance;
    public final List mExcludeRingtones = new ArrayList();
    public final List mExcludeNotifications = new ArrayList();

    public OmcRingtoneManager() {
        Trace.traceBegin(256L, "OmcRingtoneManager_Init");
        try {
            loadExcludedSoundsFromCsc();
        } finally {
            Trace.endSection();
        }
    }

    public static OmcRingtoneManager getInstance() {
        if (sInstance == null) {
            sInstance = new OmcRingtoneManager();
        }
        return sInstance;
    }

    public List getExcludedRingtones() {
        return this.mExcludeRingtones;
    }

    public List getExcludedNotifications() {
        return this.mExcludeNotifications;
    }

    public void loadExcludedSoundsFromCsc() {
        String str = SemSystemProperties.get("persist.sys.omc_respath");
        if (TextUtils.isEmpty(str)) {
            return;
        }
        getExcludedRingtonesFromCsc(str, 1);
        getExcludedRingtonesFromCsc(str, 2);
    }

    public final void getExcludedRingtonesFromCsc(String str, int i) {
        String str2;
        if (i == 1) {
            str2 = str + "/media/audio/ringtones/exclude_ringtones";
            if (this.mExcludeRingtones.size() != 0) {
                this.mExcludeRingtones.clear();
            }
        } else {
            str2 = str + "/media/audio/notifications/exclude_notifications";
            if (this.mExcludeNotifications.size() != 0) {
                this.mExcludeNotifications.clear();
            }
        }
        File file = new File(str2);
        if (file.exists()) {
            try {
                BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
                while (true) {
                    try {
                        String readLine = bufferedReader.readLine();
                        if (readLine == null) {
                            bufferedReader.close();
                            return;
                        } else if (i == 1) {
                            this.mExcludeRingtones.add(readLine);
                        } else {
                            this.mExcludeNotifications.add(readLine);
                        }
                    } finally {
                    }
                }
            } catch (Exception e) {
                Log.e("AS.OmcRingtoneManager", "File exception", e);
            }
        } else {
            Log.d("AS.OmcRingtoneManager", "There is no sounds(type:" + i + ") list to remove");
        }
    }
}
