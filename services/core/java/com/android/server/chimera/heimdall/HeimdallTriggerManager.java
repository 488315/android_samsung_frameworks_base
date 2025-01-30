package com.android.server.chimera.heimdall;

import android.os.SystemProperties;
import com.android.server.chimera.ChimeraCommonUtil;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

/* loaded from: classes.dex */
public class HeimdallTriggerManager {
    public boolean ENABLE_GLOBAL_KILL;
    public boolean ENABLE_SPEC_KILL;
    public int mGlobalKillThresholdKb;
    public HashMap mSpecMap = new HashMap();
    public HashSet mSpecKillIgnoreSet = new HashSet();
    public HashSet mGlobalKillIgnoreSet = new HashSet();

    public HeimdallTriggerManager() {
        this.ENABLE_GLOBAL_KILL = true;
        this.ENABLE_SPEC_KILL = true;
        boolean equals = "0x4f4c".equals(SystemProperties.get("ro.boot.debug_level", "0x4f4c"));
        boolean equals2 = "eng".equals(SystemProperties.get("ro.build.type", "eng"));
        if (equals || equals2 || !loadSpec()) {
            this.ENABLE_SPEC_KILL = false;
        }
        if (loadGlobal()) {
            return;
        }
        this.ENABLE_GLOBAL_KILL = false;
    }

    public boolean isTriggered(HeimdallProcessData heimdallProcessData) {
        if (heimdallProcessData.currentPhase() == 32) {
            return heimdallProcessData.isGlobalKill() || heimdallProcessData.isSpecKill();
        }
        if (heimdallProcessData.currentPhase() == 8) {
            return heimdallProcessData.shouldKill();
        }
        return false;
    }

    public void trigger(HeimdallProcessData heimdallProcessData) {
        triggerGlobalKill(heimdallProcessData);
        triggerSpecKill(heimdallProcessData);
    }

    public final void triggerGlobalKill(HeimdallProcessData heimdallProcessData) {
        if (!this.ENABLE_GLOBAL_KILL || heimdallProcessData.isSpecKill() || this.mGlobalKillIgnoreSet.contains(heimdallProcessData.firstAppPackageName)) {
            return;
        }
        if (heimdallProcessData.currentPhase() == 32) {
            int i = heimdallProcessData.anonBeforeGc + heimdallProcessData.graphicsBeforeGc;
            int i2 = this.mGlobalKillThresholdKb;
            if (i > i2) {
                heimdallProcessData.setGlobalKill();
                Heimdall.log("Trigger Global kill before GC. (Usage=" + i + "KB Threshold=" + i2 + "KB SCAN_VERSION=" + Heimdall.SCAN_VERSION + ") " + heimdallProcessData.toDumpString());
                return;
            }
            heimdallProcessData.clearGlobalKill();
            return;
        }
        if (heimdallProcessData.currentPhase() == 8) {
            int i3 = heimdallProcessData.anonAfterGc + heimdallProcessData.graphicsAfterGc;
            int i4 = this.mGlobalKillThresholdKb;
            if (heimdallProcessData.isGlobalKill() && i3 > i4) {
                heimdallProcessData.setShouldKill();
                Heimdall.log("Trigger Global kill after GC. (Usage=" + i3 + "KB Threshold=" + i4 + "KB SCAN_VERSION=" + Heimdall.SCAN_VERSION + ") " + heimdallProcessData.toDumpString());
                return;
            }
            heimdallProcessData.clearShouldKill();
        }
    }

    public final void triggerSpecKill(HeimdallProcessData heimdallProcessData) {
        if (!this.ENABLE_SPEC_KILL || heimdallProcessData.isGlobalKill() || this.mSpecKillIgnoreSet.contains(heimdallProcessData.firstAppPackageName) || !heimdallProcessData.firstAppPackageName.equals(heimdallProcessData.processName)) {
            return;
        }
        if (heimdallProcessData.currentPhase() == 32) {
            int i = heimdallProcessData.anonBeforeGc + heimdallProcessData.graphicsBeforeGc;
            int specKb = getSpecKb(heimdallProcessData);
            if (specKb != Integer.MAX_VALUE) {
                specKb = (specKb * 6) / 5;
            }
            if (i > specKb) {
                heimdallProcessData.setSpecKill();
                Heimdall.log("Trigger Spec kill before GC. (Usage=" + i + "KB Threshold=" + specKb + "KB(x1.2) SCAN_VERSION=" + Heimdall.SCAN_VERSION + ") " + heimdallProcessData.toDumpString());
                return;
            }
            heimdallProcessData.clearSpecKill();
            return;
        }
        if (heimdallProcessData.currentPhase() == 8) {
            int i2 = heimdallProcessData.anonAfterGc + heimdallProcessData.graphicsAfterGc;
            int specKb2 = getSpecKb(heimdallProcessData);
            if (heimdallProcessData.isSpecKill() && i2 > specKb2) {
                heimdallProcessData.setShouldKill();
                Heimdall.log("Trigger Spec kill after GC. (Usage=" + i2 + "KB Threshold=" + specKb2 + "KB SCAN_VERSION=" + Heimdall.SCAN_VERSION + ") " + heimdallProcessData.toDumpString());
                return;
            }
            heimdallProcessData.clearShouldKill();
        }
    }

    public final boolean isSamsungProcess(HeimdallProcessData heimdallProcessData) {
        return heimdallProcessData.firstAppPackageName.startsWith("com.sec.android") || heimdallProcessData.firstAppPackageName.startsWith("com.samsung.android");
    }

    public final int getSpecKb(HeimdallProcessData heimdallProcessData) {
        if (isSamsungProcess(heimdallProcessData)) {
            try {
                return ((Integer) this.mSpecMap.getOrDefault(heimdallProcessData.firstAppPackageName, 262144)).intValue();
            } catch (NullPointerException unused) {
                return 262144;
            }
        }
        try {
            return ((Integer) this.mSpecMap.getOrDefault(heimdallProcessData.firstAppPackageName, Integer.MAX_VALUE)).intValue();
        } catch (NullPointerException unused2) {
            return Integer.MAX_VALUE;
        }
    }

    public final boolean loadGlobal() {
        this.mGlobalKillThresholdKb = 0;
        int totalMemorySizeKb = (int) ChimeraCommonUtil.getTotalMemorySizeKb();
        if (totalMemorySizeKb > 12582912) {
            this.mGlobalKillThresholdKb = 8388608;
        } else if (totalMemorySizeKb > 8388608) {
            this.mGlobalKillThresholdKb = 6291456;
        } else if (totalMemorySizeKb > 6291456) {
            this.mGlobalKillThresholdKb = 4194304;
        } else if (totalMemorySizeKb > 4194304) {
            this.mGlobalKillThresholdKb = 3145728;
        }
        return this.mGlobalKillThresholdKb != 0;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v0 */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v16 */
    /* JADX WARN: Type inference failed for: r3v17 */
    /* JADX WARN: Type inference failed for: r3v18 */
    /* JADX WARN: Type inference failed for: r3v19 */
    /* JADX WARN: Type inference failed for: r3v2, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r3v3, types: [java.io.BufferedReader] */
    /* JADX WARN: Type inference failed for: r3v4 */
    /* JADX WARN: Type inference failed for: r3v5 */
    /* JADX WARN: Type inference failed for: r3v6 */
    /* JADX WARN: Type inference failed for: r3v7, types: [java.lang.String] */
    public final boolean loadSpec() {
        FileReader fileReader;
        Integer num;
        boolean z = false;
        ?? r3 = 0;
        r3 = 0;
        r3 = 0;
        r3 = 0;
        r3 = 0;
        try {
            try {
                try {
                    fileReader = new FileReader("/system/heimdallddata/spec.txt", StandardCharsets.UTF_8);
                    try {
                        BufferedReader bufferedReader = new BufferedReader(fileReader);
                        while (true) {
                            try {
                                r3 = bufferedReader.readLine();
                                boolean z2 = true;
                                if (r3 == 0) {
                                    break;
                                }
                                String[] split = r3.split(",");
                                if (split.length >= 4) {
                                    String str = split[0];
                                    boolean equals = "1".equals(split[1]);
                                    boolean equals2 = "1".equals(split[2]);
                                    try {
                                        num = Integer.valueOf(Integer.parseInt(split[3]));
                                    } catch (NumberFormatException unused) {
                                        num = 0;
                                    }
                                    if (num.intValue() > 0) {
                                        z2 = equals;
                                    }
                                    if (z2) {
                                        this.mSpecKillIgnoreSet.add(str);
                                    } else {
                                        this.mSpecMap.put(str, num);
                                    }
                                    if (equals2) {
                                        this.mGlobalKillIgnoreSet.add(str);
                                    }
                                }
                            } catch (Exception e) {
                                e = e;
                                r3 = bufferedReader;
                                Heimdall.log("Failed to read spec from /system/heimdallddata/spec.txt, exception: " + e.getMessage());
                                if (r3 != 0) {
                                    r3.close();
                                }
                                if (fileReader != null) {
                                    fileReader.close();
                                }
                                return z;
                            } catch (Throwable th) {
                                th = th;
                                r3 = bufferedReader;
                                if (r3 != 0) {
                                    try {
                                        r3.close();
                                    } catch (Exception unused2) {
                                        Heimdall.log("Failed to close /system/heimdallddata/spec.txt");
                                        throw th;
                                    }
                                }
                                if (fileReader != null) {
                                    fileReader.close();
                                }
                                throw th;
                            }
                        }
                        bufferedReader.close();
                        fileReader.close();
                        z = true;
                    } catch (Exception e2) {
                        e = e2;
                    }
                } catch (Exception unused3) {
                    Heimdall.log("Failed to close /system/heimdallddata/spec.txt");
                }
            } catch (Exception e3) {
                e = e3;
                fileReader = null;
            } catch (Throwable th2) {
                th = th2;
                fileReader = null;
            }
            return z;
        } catch (Throwable th3) {
            th = th3;
        }
    }

    public void dumpInfo(PrintWriter printWriter) {
        printWriter.println("Total spec count: " + this.mSpecMap.size());
        for (Map.Entry entry : this.mSpecMap.entrySet()) {
            printWriter.println("* " + ((String) entry.getKey()) + " : " + ((Integer) entry.getValue()) + " KB");
        }
    }
}
