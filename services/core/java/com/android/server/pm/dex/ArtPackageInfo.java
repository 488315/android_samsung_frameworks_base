package com.android.server.pm.dex;

import java.util.List;

/* loaded from: classes3.dex */
public class ArtPackageInfo {
  public final List mCodePaths;
  public final List mInstructionSets;
  public final String mOatDir;
  public final String mPackageName;

  public ArtPackageInfo(String str, List list, List list2, String str2) {
    this.mPackageName = str;
    this.mInstructionSets = list;
    this.mCodePaths = list2;
    this.mOatDir = str2;
  }

  public String getPackageName() {
    return this.mPackageName;
  }

  public List getInstructionSets() {
    return this.mInstructionSets;
  }

  public List getCodePaths() {
    return this.mCodePaths;
  }

  public String getOatDir() {
    return this.mOatDir;
  }
}
