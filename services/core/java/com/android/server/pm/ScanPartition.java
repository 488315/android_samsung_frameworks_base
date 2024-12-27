package com.android.server.pm;

import android.content.pm.PackagePartitions;

import java.io.File;

public class ScanPartition extends PackagePartitions.SystemPartition {
    public final ApexManager.ActiveApexInfo apexInfo;
    public final int scanFlag;

    public ScanPartition(PackagePartitions.SystemPartition systemPartition) {
        super(systemPartition);
        int i;
        int i2 = systemPartition.type;
        if (i2 == 0) {
            i = 0;
        } else if (i2 == 1) {
            i = 524288;
        } else if (i2 == 2) {
            i = 4194304;
        } else if (i2 == 3) {
            i = 262144;
        } else if (i2 == 4) {
            i = 1048576;
        } else {
            if (i2 != 5) {
                throw new IllegalStateException(
                        "Unable to determine scan flag for " + systemPartition.getFolder());
            }
            i = 2097152;
        }
        this.scanFlag = i;
        this.apexInfo = null;
    }

    public ScanPartition(
            File file, ScanPartition scanPartition, ApexManager.ActiveApexInfo activeApexInfo) {
        super(file, scanPartition);
        int i = scanPartition.scanFlag;
        this.apexInfo = activeApexInfo;
        int i2 = activeApexInfo.isFactory ? 41943040 | i : 8388608 | i;
        this.scanFlag = activeApexInfo.activeApexChanged ? i2 | 16777216 : i2;
    }

    public final String toString() {
        return getFolder().getAbsolutePath() + ":" + this.scanFlag;
    }
}
