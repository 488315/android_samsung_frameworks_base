package com.android.server.pm;

import android.content.pm.PackagePartitions;
import com.android.internal.util.jobs.XmlUtils;
import java.io.File;

/* loaded from: classes3.dex */
public class ScanPartition extends PackagePartitions.SystemPartition {
  public final ApexManager.ActiveApexInfo apexInfo;
  public final int scanFlag;

  public ScanPartition(PackagePartitions.SystemPartition systemPartition) {
    super(systemPartition);
    this.scanFlag = scanFlagForPartition(systemPartition);
    this.apexInfo = null;
  }

  public ScanPartition(
      File file, ScanPartition scanPartition, ApexManager.ActiveApexInfo activeApexInfo) {
    super(file, scanPartition);
    int i = scanPartition.scanFlag;
    this.apexInfo = activeApexInfo;
    if (activeApexInfo != null) {
      i |= 8388608;
      i = activeApexInfo.isFactory ? i | 33554432 : i;
      if (activeApexInfo.activeApexChanged) {
        i |= 16777216;
      }
    }
    this.scanFlag = i;
  }

  public static int scanFlagForPartition(PackagePartitions.SystemPartition systemPartition) {
    int i = systemPartition.type;
    if (i == 0) {
      return 0;
    }
    if (i == 1) {
      return 524288;
    }
    if (i == 2) {
      return 4194304;
    }
    if (i == 3) {
      return 262144;
    }
    if (i == 4) {
      return 1048576;
    }
    if (i == 5) {
      return 2097152;
    }
    throw new IllegalStateException(
        "Unable to determine scan flag for " + systemPartition.getFolder());
  }

  public String toString() {
    return getFolder().getAbsolutePath() + XmlUtils.STRING_ARRAY_SEPARATOR + this.scanFlag;
  }
}
