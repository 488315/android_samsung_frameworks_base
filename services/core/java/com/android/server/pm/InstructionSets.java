package com.android.server.pm;

import android.os.Build;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.ArraySet;

import dalvik.system.VMRuntime;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class InstructionSets {
    public static final String PREFERRED_INSTRUCTION_SET =
            VMRuntime.getInstructionSet(Build.SUPPORTED_ABIS[0]);

    public static String[] getAppDexInstructionSets(String str, String str2) {
        return str != null
                ? str2 != null
                        ? new String[] {
                            VMRuntime.getInstructionSet(str), VMRuntime.getInstructionSet(str2)
                        }
                        : new String[] {VMRuntime.getInstructionSet(str)}
                : new String[] {PREFERRED_INSTRUCTION_SET};
    }

    public static String[] getDexCodeInstructionSets(String[] strArr) {
        ArraySet arraySet = new ArraySet(strArr.length);
        for (String str : strArr) {
            String str2 = SystemProperties.get("ro.dalvik.vm.isa." + str);
            if (!TextUtils.isEmpty(str2)) {
                str = str2;
            }
            arraySet.add(str);
        }
        return (String[]) arraySet.toArray(new String[arraySet.size()]);
    }
}
