package com.android.server.pm;

import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.os.SystemProperties;

import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;

import dalvik.system.DexFile;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public abstract class PackageManagerServiceCompilerMapping {
    public static final int REASON_SHARED_INDEX;
    public static final String[] REASON_STRINGS;

    static {
        String[] strArr = {
            "first-boot",
            "boot-after-ota",
            "post-boot",
            "install",
            "install-fast",
            "install-bulk",
            "install-bulk-secondary",
            "install-bulk-downgraded",
            "install-bulk-secondary-downgraded",
            "bg-dexopt",
            "ab-ota",
            "inactive",
            "cmdline",
            "boot-after-mainline-update",
            "shared"
        };
        REASON_STRINGS = strArr;
        int length = strArr.length - 1;
        REASON_SHARED_INDEX = length;
        if (15 != strArr.length) {
            throw new IllegalStateException("REASON_STRINGS not correct");
        }
        if (!"shared".equals(strArr[length])) {
            throw new IllegalStateException("REASON_STRINGS not correct because of shared index");
        }
    }

    public static String getAndCheckValidity(int i) {
        String str = SystemProperties.get(getSystemPropertyName(i));
        String[] strArr = REASON_STRINGS;
        if (str == null
                || str.isEmpty()
                || !(str.equals("skip") || DexFile.isValidCompilerFilter(str))) {
            throw new IllegalStateException(
                    AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                            DumpUtils$$ExternalSyntheticOutline0.m(
                                    "Value \"", str, "\" not valid (reason "),
                            strArr[i],
                            ")"));
        }
        if (i == REASON_SHARED_INDEX && DexFile.isProfileGuidedCompilerFilter(str)) {
            throw new IllegalStateException(
                    AudioOffloadInfo$$ExternalSyntheticOutline0.m(
                            DumpUtils$$ExternalSyntheticOutline0.m(
                                    "Value \"", str, "\" not allowed (reason "),
                            strArr[i],
                            ")"));
        }
        return str;
    }

    public static String getSystemPropertyName(int i) {
        String[] strArr = REASON_STRINGS;
        if (i == 21) {
            return "pm.dexopt." + strArr[3];
        }
        if (i == 22) {
            return "pm.dexopt." + strArr[3];
        }
        if (i == 23) {
            return "pm.dexopt." + strArr[3];
        }
        if (i == 24) {
            return "pm.dexopt." + strArr[3];
        }
        if (i == 25) {
            return "pm.dexopt." + strArr[9];
        }
        if (i < 0 || i >= 15) {
            throw new IllegalArgumentException(
                    BinaryTransparencyService$$ExternalSyntheticOutline0.m(
                            i, "reason ", " invalid"));
        }
        return "pm.dexopt." + strArr[i];
    }
}
