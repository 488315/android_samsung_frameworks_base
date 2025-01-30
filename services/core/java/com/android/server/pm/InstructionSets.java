package com.android.server.pm;

import android.os.Build;
import android.os.SystemProperties;
import android.text.TextUtils;
import android.util.ArraySet;
import dalvik.system.VMRuntime;

/* loaded from: classes3.dex */
public abstract class InstructionSets {
  public static final String PREFERRED_INSTRUCTION_SET =
      VMRuntime.getInstructionSet(Build.SUPPORTED_ABIS[0]);

  public static String[] getAppDexInstructionSets(String str, String str2) {
    if (str == null) {
      return new String[] {getPreferredInstructionSet()};
    }
    if (str2 != null) {
      return new String[] {VMRuntime.getInstructionSet(str), VMRuntime.getInstructionSet(str2)};
    }
    return new String[] {VMRuntime.getInstructionSet(str)};
  }

  public static String getPreferredInstructionSet() {
    return PREFERRED_INSTRUCTION_SET;
  }

  public static String getDexCodeInstructionSet(String str) {
    String str2 = SystemProperties.get("ro.dalvik.vm.isa." + str);
    return TextUtils.isEmpty(str2) ? str : str2;
  }

  public static String[] getDexCodeInstructionSets(String[] strArr) {
    ArraySet arraySet = new ArraySet(strArr.length);
    for (String str : strArr) {
      arraySet.add(getDexCodeInstructionSet(str));
    }
    return (String[]) arraySet.toArray(new String[arraySet.size()]);
  }

  public static String[] getAllDexCodeInstructionSets() {
    int length = Build.SUPPORTED_ABIS.length;
    String[] strArr = new String[length];
    for (int i = 0; i < length; i++) {
      strArr[i] = VMRuntime.getInstructionSet(Build.SUPPORTED_ABIS[i]);
    }
    return getDexCodeInstructionSets(strArr);
  }

  public static String getPrimaryInstructionSet(PackageAbiHelper.Abis abis) {
    String str = abis.primary;
    if (str == null) {
      return getPreferredInstructionSet();
    }
    return VMRuntime.getInstructionSet(str);
  }
}
