package com.samsung.vekit.Common.Object;

import com.samsung.vekit.Common.Type.FpsType;
import com.samsung.vekit.Common.Type.SpeedType;

/* loaded from: classes6.dex */
public class FrcSupportInfo {
  private boolean[][] frcSupportInfo;

  public FrcSupportInfo() {
    int fpsSize = FpsType.values().length;
    int speedSize = SpeedType.values().length;
    this.frcSupportInfo = new boolean[fpsSize][];
    for (int i = 0; i < fpsSize; i++) {
      this.frcSupportInfo[i] = new boolean[speedSize];
      for (int j = 0; j < speedSize; j++) {
        this.frcSupportInfo[i][j] = false;
      }
    }
  }

  public boolean checkFrcAvailable(FpsType fpsType, SpeedType speedType) {
    return this.frcSupportInfo[fpsType.ordinal()][speedType.ordinal()];
  }

  public void setFrcAvailable(FpsType fpsType, SpeedType speedType, boolean frcAvailable) {
    this.frcSupportInfo[fpsType.ordinal()][speedType.ordinal()] = frcAvailable;
  }

  public void setFrcAvailable(int fpsType, int speedType, boolean frcAvailable) {
    this.frcSupportInfo[fpsType][speedType] = frcAvailable;
  }
}
