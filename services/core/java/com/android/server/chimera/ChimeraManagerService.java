package com.android.server.chimera;

import android.content.Context;
import android.os.SystemProperties;
import android.text.TextUtils;
import com.android.server.chimera.psitracker.PSITracker;
import com.android.server.chimera.umr.UnifiedMemoryReclaimer;
import com.android.server.am.ActivityManagerService;
import com.samsung.android.chimera.IChimera;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public class ChimeraManagerService extends IChimera.Stub {
    public ChimeraManager mChimeraManager;

    public ChimeraManagerService(Context context, ActivityManagerService activityManagerService) {
        this.mChimeraManager = null;
        String str = SystemProperties.get("persist.config.chimera.enable", "");
        if (TextUtils.isEmpty(str) || str.equals("false")) {
            SystemProperties.set("persist.config.chimera.enable", "true");
            str = "true";
        }
        if (str.startsWith("true")) {
            this.mChimeraManager = new ChimeraManager(context, activityManagerService);
        }
        UnifiedMemoryReclaimer.init(activityManagerService, context);
    }

    public void dump(FileDescriptor fileDescriptor, PrintWriter printWriter, String[] strArr) {
        if (strArr != null && strArr.length != 0 && strArr.length != 0) {
            for (String str : strArr) {
                if ("-enable_chimera".equals(str)) {
                    SystemProperties.set("persist.config.chimera.enable", "true");
                }
                if ("-disable_chimera".equals(str)) {
                    SystemProperties.set("persist.config.chimera.enable", "forcestop");
                }
            }
        }
        ChimeraManager chimeraManager = this.mChimeraManager;
        if (chimeraManager != null) {
            chimeraManager.dump(printWriter, strArr);
        }
    }

    public ChimeraDataInfo getChimeraStat() {
        ChimeraManager chimeraManager = this.mChimeraManager;
        if (chimeraManager != null) {
            return chimeraManager.getChimeraStat();
        }
        return null;
    }

    public Boolean isRescheduleExceptionPackage(String str) {
        return RestartImmediatePackages.getInstance().getPackage(str);
    }

    public void checkProcessInHeimdall(int i, String str, String str2) {
        ChimeraManager chimeraManager = this.mChimeraManager;
        if (chimeraManager != null) {
            chimeraManager.checkProcessInHeimdall(i, str, str2);
        }
    }

    public List getAvailableMemInfo(long j, long j2) {
        PSITracker pSITracker;
        ArrayList arrayList = new ArrayList();
        ChimeraManager chimeraManager = this.mChimeraManager;
        return (chimeraManager == null || (pSITracker = chimeraManager.getPSITracker()) == null) ? arrayList : pSITracker.getAvailableMemInfo(j, j2);
    }

    public ChimeraManager getChimeraManager() {
        return this.mChimeraManager;
    }
}
