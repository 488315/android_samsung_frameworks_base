package com.android.server.pm.dex;

import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.hardware.audio.common.V2_0.AudioChannelMask$$ExternalSyntheticOutline0;
import android.util.Log;

import com.android.server.art.model.DexoptParams;
import com.android.server.pm.PackageManagerServiceCompilerMapping;

import dalvik.system.DexFile;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class DexoptOptions {
    public final int mCompilationReason;
    public final String mCompilerFilter;
    public final int mFlags;
    public final String mPackageName;
    public final String mSplitName;

    public DexoptOptions(int i, int i2, String str) {
        this(i, i2, str, PackageManagerServiceCompilerMapping.getAndCheckValidity(i), null);
    }

    public DexoptOptions(int i, int i2, String str, String str2, String str3) {
        if ((i2 & (-3696)) != 0) {
            throw new IllegalArgumentException(
                    AudioChannelMask$$ExternalSyntheticOutline0.m(
                            new StringBuilder("Invalid flags : "), i2));
        }
        this.mPackageName = str;
        this.mCompilerFilter = str2;
        this.mFlags = i2;
        this.mSplitName = str3;
        this.mCompilationReason = i;
    }

    public static String convertToArtServiceDexoptReason(int i) {
        switch (i) {
            case 0:
                return "first-boot";
            case 1:
                return "boot-after-ota";
            case 2:
            case 10:
            case 14:
                throw new UnsupportedOperationException(
                        VibrationParam$1$$ExternalSyntheticOutline0.m(
                                i, "ART Service unsupported compilation reason "));
            case 3:
                return "install";
            case 4:
                return "install-fast";
            case 5:
                return "install-bulk";
            case 6:
                return "install-bulk-secondary";
            case 7:
                return "install-bulk-downgraded";
            case 8:
                return "install-bulk-secondary-downgraded";
            case 9:
                return "bg-dexopt";
            case 11:
                return "inactive";
            case 12:
                return "cmdline";
            case 13:
                return "boot-after-mainline-update";
            default:
                switch (i) {
                    case 21:
                        return "install-spqr";
                    case 22:
                        return "install-speg";
                    case 23:
                        return "bg-dexopt";
                    case 24:
                        return "install-repair";
                    case 25:
                        return "labs";
                    default:
                        throw new IllegalArgumentException(
                                VibrationParam$1$$ExternalSyntheticOutline0.m(
                                        i, "Invalid compilation reason "));
                }
        }
    }

    public final DexoptParams convertToDexoptParams(int i) {
        String str = this.mPackageName;
        String str2 = this.mSplitName;
        if (str2 != null) {
            throw new UnsupportedOperationException(
                    "Request to optimize only split " + str2 + " for " + str);
        }
        int i2 = this.mFlags;
        int i3 = i2 & 1;
        String str3 = this.mCompilerFilter;
        if (i3 == 0 && DexFile.isProfileGuidedCompilerFilter(str3)) {
            throw new IllegalArgumentException(
                    "DEXOPT_CHECK_FOR_PROFILES_UPDATES must be set with profile guided filter");
        }
        if ((i2 & 2) != 0) {
            i |= 16;
        }
        int i4 = (i2 & 8) != 0 ? i | 2 : i | 1;
        if ((i2 & 32) != 0) {
            i4 |= 8;
        }
        if ((i2 & 1024) == 0) {
            Log.w(
                    "DexoptOptions",
                    "DEXOPT_INSTALL_WITH_DEX_METADATA_FILE not set in request to optimise "
                            + str
                            + " - ART Service will unconditionally use a DM file if present.");
        }
        return new DexoptParams.Builder(
                        convertToArtServiceDexoptReason(this.mCompilationReason), i4)
                .setCompilerFilter(str3)
                .setPriorityClass(
                        (i2 & 4) != 0 ? (i2 & 2048) != 0 ? 80 : (i2 & 512) != 0 ? 40 : 60 : 100)
                .build();
    }
}
