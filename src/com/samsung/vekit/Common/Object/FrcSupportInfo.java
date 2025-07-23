package com.samsung.vekit.Common.Object;

import com.samsung.vekit.Common.Type.FpsType;
import com.samsung.vekit.Common.Type.SpeedType;

/* loaded from: classes6.dex */
public class FrcSupportInfo {
    private boolean[][] frcSupportInfo;

    public FrcSupportInfo() {
        int length = FpsType.values().length;
        int length2 = SpeedType.values().length;
        this.frcSupportInfo = new boolean[length][];
        for (int i = 0; i < length; i++) {
            this.frcSupportInfo[i] = new boolean[length2];
            for (int i2 = 0; i2 < length2; i2++) {
                this.frcSupportInfo[i][i2] = false;
            }
        }
    }

    public boolean checkFrcAvailable(FpsType fpsType, SpeedType speedType) {
        return this.frcSupportInfo[fpsType.ordinal()][speedType.ordinal()];
    }

    public void setFrcAvailable(FpsType fpsType, SpeedType speedType, boolean z) {
        this.frcSupportInfo[fpsType.ordinal()][speedType.ordinal()] = z;
    }

    public void setFrcAvailable(int i, int i2, boolean z) {
        this.frcSupportInfo[i][i2] = z;
    }
}
