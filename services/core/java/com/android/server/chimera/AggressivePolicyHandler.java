package com.android.server.chimera;

import android.os.Handler;
import android.os.Message;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import java.io.PrintWriter;
import java.math.BigDecimal;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AggressivePolicyHandler extends PolicyHandler {
    public long mAdjustTargetFreeEndTime;
    public double mAdjustTargetFreeFactor;
    public ProtectLevel mCurProtectLevel;
    public int mHeavyLaunchBufferSize;
    public HeavyLaunchCounter mHeavyLaunchCounter;
    public int mHeavyLaunchPackageLimit;
    public boolean mIsAdjustTargetFree;
    public boolean mIsHeavyLaunchOn;
    public int mPkgKillIntervalDefault;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    final class ProtectLevel {
        public static final /* synthetic */ ProtectLevel[] $VALUES;
        public static final ProtectLevel HEAVY;
        public static final ProtectLevel NORMAL;

        static {
            ProtectLevel protectLevel = new ProtectLevel("NORMAL", 0);
            NORMAL = protectLevel;
            ProtectLevel protectLevel2 = new ProtectLevel("HEAVY", 1);
            HEAVY = protectLevel2;
            $VALUES = new ProtectLevel[]{protectLevel, protectLevel2};
        }

        public static ProtectLevel valueOf(String str) {
            return (ProtectLevel) Enum.valueOf(ProtectLevel.class, str);
        }

        public static ProtectLevel[] values() {
            return (ProtectLevel[]) $VALUES.clone();
        }
    }

    @Override // com.android.server.chimera.PolicyHandler
    public final void dump(PrintWriter printWriter, String[] strArr) {
        if (strArr == null || strArr.length == 0) {
            return;
        }
        if ("-a".equals(strArr[0])) {
            dumpCommonInfo(printWriter);
            dumpInfo(printWriter);
            printWriter.println();
            if (this.mIsHeavyLaunchOn) {
                HeavyLaunchCounter heavyLaunchCounter = this.mHeavyLaunchCounter;
                if (heavyLaunchCounter.isHeavyLaunch()) {
                    printWriter.println("HeavyLaunch mode from " + heavyLaunchCounter.getLastStartedUpTime());
                }
            }
            printWriter.println("************** adjinfo ****************");
            dumpAdjInfo(printWriter);
            printWriter.println("\n************** appinfo ****************");
            dumpAppInfo(printWriter);
            printWriter.println("\n************** kill history ****************");
            dumpHistoryBuffer(printWriter);
            return;
        }
        if (strArr.length > 0) {
            String str = strArr[0];
            if (str.equals("weight") && strArr.length > 2) {
                try {
                    String str2 = strArr[1];
                    String str3 = strArr[2];
                    float floatValue = Float.valueOf(str2).floatValue();
                    float floatValue2 = Float.valueOf(str3).floatValue();
                    setWeight(floatValue, floatValue2);
                    printWriter.println("wLru: " + floatValue + " wStandbyBucket: " + floatValue2);
                    return;
                } catch (NumberFormatException e) {
                    printWriter.println(e.getMessage());
                    return;
                }
            }
            if (str.equals("set_normal_boost_mode") && strArr.length > 1) {
                boolean equals = "on".equals(strArr[1].toLowerCase());
                this.mIsKillBoostModeOnNormal = equals;
                printWriter.println("Normal kill boost : ".concat(equals ? "on" : "off"));
                return;
            }
            if (str.equals("set_heavy_boost_mode") && strArr.length > 1) {
                boolean equals2 = "on".equals(strArr[1].toLowerCase());
                this.mIsKillBoostModeOnHeavy = equals2;
                printWriter.println("Heavy kill boost : ".concat(equals2 ? "on" : "off"));
                return;
            }
            if (str.equals("set_normal_kill_interval") && strArr.length > 1) {
                try {
                    this.mPkgProtectedParameters[0][2] = Integer.parseInt(strArr[1]) * 1000;
                } catch (NumberFormatException e2) {
                    printWriter.println(e2.getMessage());
                }
                AccessibilityManagerService$$ExternalSyntheticOutline0.m(new StringBuilder("Normal kill interval : "), this.mPkgProtectedParameters[0][2], printWriter);
                return;
            }
            if (str.equals("set_heavy_kill_interval") && strArr.length > 1) {
                try {
                    this.mPkgProtectedParameters[1][2] = Integer.parseInt(strArr[1]) * 1000;
                } catch (NumberFormatException e3) {
                    printWriter.println(e3.getMessage());
                }
                AccessibilityManagerService$$ExternalSyntheticOutline0.m(new StringBuilder("Heavy kill interval : "), this.mPkgProtectedParameters[1][2], printWriter);
                return;
            }
            if (str.equals("info")) {
                dumpCommonInfo(printWriter);
                dumpInfo(printWriter);
                return;
            }
            if (str.equals("appinfo")) {
                dumpAppInfo(printWriter);
                return;
            }
            if (str.equals("adjinfo")) {
                dumpAdjInfo(printWriter);
                return;
            }
            if (str.equals("history")) {
                printWriter.println("Chimera Kill History:");
                dumpHistoryBuffer(printWriter);
                return;
            }
            if (str.equals("quotakill")) {
                SystemRepository systemRepository = this.mSystemRepository;
                systemRepository.getClass();
                if (SystemRepository.IS_SHIP_BUILD) {
                    printWriter.println("No support in ship build");
                    return;
                }
                Message obtain = Message.obtain();
                obtain.what = 16;
                obtain.arg1 = 1;
                Handler handler = systemRepository.mSystemEventListenerHandler;
                if (handler != null) {
                    handler.sendMessage(obtain);
                }
            }
        }
    }

    public final void dumpInfo(PrintWriter printWriter) {
        StringBuilder sb = new StringBuilder("ScoreWeight: lru=");
        sb.append(this.mWeightLru);
        sb.append(" standbybucket=");
        sb.append(this.mWeightStandbyBucket);
        sb.append(" mem=");
        AggressivePolicyHandler$$ExternalSyntheticOutline0.m(sb, this.mWeightMem, printWriter);
        printWriter.println("Normal kill interval : ".concat(PolicyHandler.toHHmmss(this.mPkgProtectedParameters[0][2])));
        printWriter.println("Heavy kill interval : ".concat(PolicyHandler.toHHmmss(this.mPkgProtectedParameters[1][2])));
        if (!this.mIsKillBoostModeOnNormal && !this.mIsKillBoostModeOnHeavy) {
            printWriter.println("Kill boost mode: off");
            return;
        }
        StringBuilder sb2 = new StringBuilder("Kill boost mode:");
        sb2.append(this.mIsKillBoostModeOnNormal ? " Normal" : "");
        BinaryTransparencyService$$ExternalSyntheticOutline0.m(sb2, this.mIsKillBoostModeOnHeavy ? " Heavy" : "", printWriter);
    }

    /* JADX WARN: Code restructure failed: missing block: B:318:0x0255, code lost:
    
        if (r6 == 0) goto L128;
     */
    /* JADX WARN: Code restructure failed: missing block: B:319:0x027c, code lost:
    
        r2.logSkip(r9, "freeze binder failed.");
     */
    /* JADX WARN: Code restructure failed: missing block: B:324:0x0279, code lost:
    
        if (r6 != 0) goto L124;
     */
    /* JADX WARN: Removed duplicated region for block: B:107:0x0567  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x056a  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x05e6  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x0693 A[LOOP:9: B:136:0x068d->B:138:0x0693, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:154:0x06f4 A[EDGE_INSN: B:154:0x06f4->B:155:0x06f4 BREAK  A[LOOP:5: B:64:0x0464->B:72:0x062b], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:167:0x062b A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:168:0x06db  */
    /* JADX WARN: Removed duplicated region for block: B:172:0x062b A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:173:0x0628  */
    /* JADX WARN: Removed duplicated region for block: B:187:0x0630  */
    /* JADX WARN: Removed duplicated region for block: B:304:0x020b  */
    /* JADX WARN: Removed duplicated region for block: B:305:0x0212  */
    @Override // com.android.server.chimera.PolicyHandler
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final int executePolicy(com.android.server.chimera.ChimeraCommonUtil.TriggerSource r31, int r32) {
        /*
            Method dump skipped, instructions count: 1929
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.chimera.AggressivePolicyHandler.executePolicy(com.android.server.chimera.ChimeraCommonUtil$TriggerSource, int):int");
    }

    @Override // com.android.server.chimera.PolicyHandler, com.android.server.chimera.SystemRepository.ChimeraProcessObserver
    public final void onForegroundActivitiesChanged(int i, int i2, boolean z, int i3, String[] strArr, boolean z2) {
        HeavyLaunchCounter heavyLaunchCounter;
        super.onForegroundActivitiesChanged(i, i2, z, i3, strArr, z2);
        if (!this.mIsHeavyLaunchOn || z2 || !z || (heavyLaunchCounter = this.mHeavyLaunchCounter) == null) {
            return;
        }
        heavyLaunchCounter.addLaunch(i2, System.currentTimeMillis());
    }

    public final void setWeight(float f, float f2) {
        this.mWeightLru = f;
        this.mWeightStandbyBucket = f2;
        this.mWeightMem = new BigDecimal(Float.toString(1.0f)).subtract(new BigDecimal(Float.toString(this.mWeightLru)).add(new BigDecimal(Float.toString(this.mWeightStandbyBucket)))).floatValue();
    }
}
