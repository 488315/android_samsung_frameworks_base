package com.android.server.chimera.heimdall;

import java.util.HashMap;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class HeimdallTriggerManager {
    public boolean ENABLE_GLOBAL_KILL;
    public boolean ENABLE_SPEC_KILL;
    public int mAlwaysRunningGlobalQuotaSpec;
    public int mGlobalKillThresholdKb;
    public HeimdallSpecManager mSpecManager;
    public int mSpecVersion;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class HeimdallProcSpec {
        public boolean allowAlwaysRunning;
        public int alwaysRunningSpecSize;
        public final int alwaysRunningSpecSizeInitial;
        public boolean ignoreAlwaysRunningSpecKill;
        public boolean ignoreGlobalKill;
        public boolean ignoreSpecKill;
        public final String procName;
        public int specSize;
        public int specVersion;

        public HeimdallProcSpec(HeimdallTriggerManager heimdallTriggerManager, String[] strArr) {
            int i;
            int i2;
            int i3;
            int i4;
            String str = strArr[1];
            boolean equals = "1".equals(strArr[2]);
            boolean equals2 = "1".equals(strArr[3]);
            try {
                i = Integer.parseInt(strArr[4]);
            } catch (NumberFormatException unused) {
                i = 0;
            }
            try {
                i2 = Integer.parseInt(strArr[5]);
            } catch (NumberFormatException unused2) {
                i2 = 0;
            }
            try {
                i3 = Integer.parseInt(strArr[6]);
            } catch (NumberFormatException unused3) {
                i3 = 0;
            }
            this.alwaysRunningSpecSizeInitial = i3;
            if (i < 0) {
                equals = true;
            } else if (i == 0) {
                heimdallTriggerManager.getClass();
                i = 262144;
            }
            boolean z = i2 > 0;
            boolean z2 = i2 > 1;
            if (!z2 && i3 < (i4 = heimdallTriggerManager.mAlwaysRunningGlobalQuotaSpec)) {
                i3 = i4;
            }
            this.procName = str;
            this.specSize = i;
            this.ignoreGlobalKill = equals2;
            this.ignoreSpecKill = equals;
            this.allowAlwaysRunning = z;
            this.ignoreAlwaysRunningSpecKill = z2;
            this.alwaysRunningSpecSize = i3;
            this.specVersion = heimdallTriggerManager.mSpecVersion;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class HeimdallSpecManager {
        public final boolean mLoadSuccess;
        public final HashMap mSpecMap;

        /* JADX WARN: Code restructure failed: missing block: B:23:0x007b, code lost:
        
            com.android.server.chimera.heimdall.Heimdall.log("Failed to read spec, process name duplicated: " + r6.procName);
         */
        /* JADX WARN: Code restructure failed: missing block: B:24:0x0091, code lost:
        
            r4.close();
         */
        /* JADX WARN: Code restructure failed: missing block: B:47:0x00ce, code lost:
        
            if (r3 != null) goto L24;
         */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public HeimdallSpecManager() {
            /*
                Method dump skipped, instructions count: 227
                To view this dump change 'Code comments level' option to 'DEBUG'
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.server.chimera.heimdall.HeimdallTriggerManager.HeimdallSpecManager.<init>(com.android.server.chimera.heimdall.HeimdallTriggerManager):void");
        }

        public final int getAlwaysRunningSpecKb(HeimdallProcessData heimdallProcessData) {
            int i = HeimdallTriggerManager.this.mAlwaysRunningGlobalQuotaSpec;
            try {
                List<HeimdallProcSpec> list = (List) this.mSpecMap.get(heimdallProcessData.firstAppPackageName);
                if (list == null) {
                    return i;
                }
                for (HeimdallProcSpec heimdallProcSpec : list) {
                    if (heimdallProcSpec.procName.equals(heimdallProcessData.processName)) {
                        if (heimdallProcSpec.ignoreAlwaysRunningSpecKill) {
                            return 0;
                        }
                        return heimdallProcSpec.alwaysRunningSpecSize;
                    }
                }
                return i;
            } catch (NullPointerException unused) {
                return i;
            }
        }

        public final int getSpecKb(HeimdallProcessData heimdallProcessData) {
            HeimdallTriggerManager.this.getClass();
            int i = (heimdallProcessData.firstAppPackageName.startsWith("com.sec.android") || heimdallProcessData.firstAppPackageName.startsWith("com.samsung.android")) ? 262144 : Integer.MAX_VALUE;
            try {
                List<HeimdallProcSpec> list = (List) this.mSpecMap.get(heimdallProcessData.firstAppPackageName);
                if (list == null) {
                    return i;
                }
                for (HeimdallProcSpec heimdallProcSpec : list) {
                    if (heimdallProcSpec.procName.equals(heimdallProcessData.processName)) {
                        if (heimdallProcSpec.ignoreSpecKill) {
                            return 0;
                        }
                        return heimdallProcSpec.specSize;
                    }
                }
                return i;
            } catch (NullPointerException unused) {
                return i;
            }
        }
    }
}
