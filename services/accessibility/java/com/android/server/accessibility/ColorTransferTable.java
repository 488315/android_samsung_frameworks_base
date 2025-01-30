package com.android.server.accessibility;

import android.net.resolv.aidl.IDnsResolverUnsolicitedEventListener;
import com.android.internal.util.FrameworkStatsLog;

/* loaded from: classes.dex */
public class ColorTransferTable {
  public static final double[] Protan_severity = {
    0.1d, 0.5d, 0.5d, 0.2d, 0.5d, 0.2d, 0.5d, 0.2d, 0.5d, 0.2d, 0.5d, 0.2d, 0.5d, 0.2d, 0.5d, 1.0d,
    1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d, 1.0d
  };
  public static final double[] Protan_userParameter = {
    0.1d, 0.2d, 0.4d, 0.5d, 0.5d, 0.6d, 0.6d, 0.7d, 0.7d, 0.8d, 0.8d, 0.9d, 0.9d, 1.0d, 1.0d, 0.1d,
    0.2d, 0.3d, 0.4d, 0.5d, 0.6d, 0.7d, 0.8d, 0.9d, 1.0d
  };
  public static final double[] Deutan_severity = {
    0.1d, 0.1d, 0.1d, 0.2d, 0.3d, 0.4d, 0.5d, 0.5d, 0.5d, 0.5d, 0.5d, 0.5d, 0.6d, 0.6d, 0.6d, 0.6d,
    0.7d, 0.7d, 0.8d, 0.9d, 0.9d, 0.9d, 1.0d, 1.0d, 1.0d
  };
  public static final double[] Deutan_userParameter = {
    0.0d, 0.1d, 0.2d, 0.3d, 0.4d, 0.5d, 0.5d, 0.6d, 0.7d, 0.8d, 0.9d, 1.0d, 0.0d, 0.1d, 0.2d, 0.3d,
    0.4d, 0.5d, 0.6d, 0.6d, 0.7d, 0.8d, 0.8d, 0.9d, 1.0d
  };
  public static final double[] Tritan_severity = {
    0.1d, 0.1d, 0.1d, 0.1d, 0.2d, 0.2d, 0.2d, 0.3d, 0.4d, 0.4d, 0.5d, 0.5d, 0.6d, 0.6d, 0.7d, 0.7d,
    0.8d, 0.8d, 0.9d, 0.9d, 0.9d, 0.9d, 0.9d, 1.0d, 1.0d
  };
  public static final double[] Tritan_userParameter = {
    0.0d, 0.1d, 0.2d, 0.3d, 0.3d, 0.4d, 0.5d, 0.6d, 0.8d, 0.9d, 0.9d, 1.0d, 0.0d, 0.3d, 0.5d, 0.7d,
    0.7d, 1.0d, 0.2d, 0.3d, 0.5d, 0.6d, 1.0d, 0.9d, 1.0d
  };

  public int getColorTransferValue_DMC(int i, int i2, int i3, double d, double d2) {
    int[] iArr;
    int i4;
    if (i == 1) {
      if (i2 == 1) {
        iArr = getMaxMinColorTrnasferValue_RR_DMC(i3, d, d2);
      } else if (i2 == 3) {
        iArr = getMaxMinColorTrnasferValue_RG_DMC(i3, d, d2);
      } else {
        if (i2 == 5) {
          iArr = getMaxMinColorTrnasferValue_RB_DMC(i3, d, d2);
        }
        iArr = null;
      }
    } else if (i == 2) {
      if (i2 == 1) {
        iArr = getMaxMinColorTrnasferValue_YR_DMC(i3, d, d2);
      } else if (i2 == 3) {
        iArr = getMaxMinColorTrnasferValue_YG_DMC(i3, d, d2);
      } else {
        if (i2 == 5) {
          iArr = getMaxMinColorTrnasferValue_YB_DMC(i3, d, d2);
        }
        iArr = null;
      }
    } else if (i == 3) {
      if (i2 == 1) {
        iArr = getMaxMinColorTrnasferValue_GR_DMC(i3, d, d2);
      } else if (i2 == 3) {
        iArr = getMaxMinColorTrnasferValue_GG_DMC(i3, d, d2);
      } else {
        if (i2 == 5) {
          iArr = getMaxMinColorTrnasferValue_GB_DMC(i3, d, d2);
        }
        iArr = null;
      }
    } else if (i == 4) {
      if (i2 == 1) {
        iArr = getMaxMinColorTrnasferValue_CR_DMC(i3, d, d2);
      } else if (i2 == 3) {
        iArr = getMaxMinColorTrnasferValue_CG_DMC(i3, d, d2);
      } else {
        if (i2 == 5) {
          iArr = getMaxMinColorTrnasferValue_CB_DMC(i3, d, d2);
        }
        iArr = null;
      }
    } else if (i == 5) {
      if (i2 == 1) {
        iArr = getMaxMinColorTrnasferValue_BR_DMC(i3, d, d2);
      } else if (i2 == 3) {
        iArr = getMaxMinColorTrnasferValue_BG_DMC(i3, d, d2);
      } else {
        if (i2 == 5) {
          iArr = getMaxMinColorTrnasferValue_BB_DMC(i3, d, d2);
        }
        iArr = null;
      }
    } else if (i != 6) {
      iArr = new int[] {-1, -1};
    } else if (i2 == 1) {
      iArr = getMaxMinColorTrnasferValue_MR_DMC(i3, d, d2);
    } else if (i2 == 3) {
      iArr = getMaxMinColorTrnasferValue_MG_DMC(i3, d, d2);
    } else {
      if (i2 == 5) {
        iArr = getMaxMinColorTrnasferValue_MB_DMC(i3, d, d2);
      }
      iArr = null;
    }
    if (iArr == null || ((i4 = iArr[0]) == -1 && iArr[1] == -1)) {
      return -1;
    }
    return (int) (((i4 - iArr[1]) * (roundHalfUp(d2) / 10.0d)) + iArr[1]);
  }

  /* JADX WARN: Removed duplicated region for block: B:4:0x0012  */
  /* JADX WARN: Removed duplicated region for block: B:5:0x0014  */
  /* JADX WARN: Removed duplicated region for block: B:9:0x0029 A[FALL_THROUGH, PHI: r5
  0x0029: PHI (r5v3 int) = (r5v1 int), (r5v0 int), (r5v6 int), (r5v0 int) binds: [B:17:0x0028, B:11:0x0018, B:4:0x0012, B:3:0x000f] A[DONT_GENERATE, DONT_INLINE]] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final int[] getMaxMinColorTrnasferValue_RR_DMC(int i, double d, double d2) {
    int i2;
    int[] iArr = new int[2];
    int roundHalfUp = roundHalfUp(d);
    int i3 = FrameworkStatsLog.APP_FREEZE_CHANGED;
    int i4 = IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT;
    if (i == 0) {
      switch (roundHalfUp) {
        case 0:
          i3 = 255;
          break;
        default:
          i3 = 0;
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
          i4 = i3;
          break;
      }
    } else if (i == 1) {
      switch (roundHalfUp) {
      }
    } else if (i == 2) {
      switch (roundHalfUp) {
        case 0:
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
          i2 = 255;
          break;
        default:
          i2 = 0;
          i4 = 0;
          break;
      }
      i3 = i4;
      i4 = i2;
    } else {
      i3 = -1;
      i4 = i3;
    }
    iArr[0] = i4;
    iArr[1] = i3;
    return iArr;
  }

  public final int[] getMaxMinColorTrnasferValue_RG_DMC(int i, double d, double d2) {
    int i2;
    int i3;
    int[] iArr = new int[2];
    int roundHalfUp = roundHalfUp(d);
    if (i == 0) {
      switch (roundHalfUp) {
        case 0:
        case 1:
        case 2:
        case 3:
        case 5:
        case 6:
        case 8:
        case 9:
        case 10:
        default:
          i2 = 0;
          break;
        case 4:
        case 7:
          i2 = 1;
          break;
      }
      i3 = 0;
    } else if (i == 1) {
      switch (roundHalfUp) {
        case 0:
        default:
          i2 = 0;
          break;
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
          i2 = 1;
          break;
      }
      i3 = i2;
    } else {
      if (i != 2) {
        i2 = -1;
        i3 = i2;
      }
      i2 = 0;
      i3 = i2;
    }
    iArr[0] = i2;
    iArr[1] = i3;
    return iArr;
  }

  /* JADX WARN: Removed duplicated region for block: B:10:0x002e  */
  /* JADX WARN: Removed duplicated region for block: B:11:0x0030  */
  /* JADX WARN: Removed duplicated region for block: B:12:0x0032  */
  /* JADX WARN: Removed duplicated region for block: B:13:0x0034  */
  /* JADX WARN: Removed duplicated region for block: B:4:0x0022  */
  /* JADX WARN: Removed duplicated region for block: B:5:0x0024  */
  /* JADX WARN: Removed duplicated region for block: B:6:0x0026  */
  /* JADX WARN: Removed duplicated region for block: B:7:0x0028  */
  /* JADX WARN: Removed duplicated region for block: B:8:0x002a  */
  /* JADX WARN: Removed duplicated region for block: B:9:0x002c  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final int[] getMaxMinColorTrnasferValue_RB_DMC(int i, double d, double d2) {
    int i2;
    int[] iArr = new int[2];
    int roundHalfUp = roundHalfUp(d);
    int i3 = FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__SET_MTE_POLICY;
    if (i == 0) {
      switch (roundHalfUp) {
        case 0:
        default:
          i3 = 0;
          break;
        case 1:
          i3 = 36;
          break;
        case 2:
          i3 = 66;
          break;
        case 3:
          i3 = 92;
          break;
        case 4:
          i3 = 117;
          break;
        case 5:
          i3 = 136;
          break;
        case 6:
          i3 = 156;
          break;
        case 7:
          i3 = 172;
          break;
        case 8:
          i3 = 188;
          break;
        case 9:
          i3 = 202;
          break;
        case 10:
          break;
      }
      i2 = 0;
    } else if (i == 1) {
      switch (roundHalfUp) {
      }
      i2 = 0;
    } else if (i == 2) {
      i2 = 0;
      i3 = 0;
    } else {
      i3 = -1;
      i2 = -1;
    }
    iArr[0] = i3;
    iArr[1] = i2;
    return iArr;
  }

  /* JADX WARN: Removed duplicated region for block: B:8:0x0010  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final int[] getMaxMinColorTrnasferValue_GR_DMC(int i, double d, double d2) {
    int i2;
    int[] iArr = new int[2];
    int roundHalfUp = roundHalfUp(d);
    if (i == 0) {
      switch (roundHalfUp) {
        case 0:
        default:
          i2 = 0;
          break;
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
          i2 = 1;
          break;
      }
    } else {
      if (i == 1) {
        switch (roundHalfUp) {
        }
      } else if (i == 2) {
        switch (roundHalfUp) {
          case 5:
            i2 = 21;
            break;
          case 6:
            i2 = 38;
            break;
          case 7:
            i2 = 51;
            break;
          case 8:
            i2 = 63;
            break;
          case 9:
            i2 = 73;
            break;
          case 10:
            i2 = 81;
            break;
        }
      } else {
        i2 = -1;
      }
      i2 = 0;
    }
    iArr[0] = i2;
    iArr[1] = i2;
    return iArr;
  }

  /* JADX WARN: Removed duplicated region for block: B:4:0x0012  */
  /* JADX WARN: Removed duplicated region for block: B:5:0x0014  */
  /* JADX WARN: Removed duplicated region for block: B:9:0x0029 A[FALL_THROUGH, PHI: r5
  0x0029: PHI (r5v3 int) = (r5v1 int), (r5v0 int), (r5v6 int), (r5v0 int) binds: [B:17:0x0028, B:11:0x0018, B:4:0x0012, B:3:0x000f] A[DONT_GENERATE, DONT_INLINE]] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final int[] getMaxMinColorTrnasferValue_GG_DMC(int i, double d, double d2) {
    int i2;
    int[] iArr = new int[2];
    int roundHalfUp = roundHalfUp(d);
    int i3 = FrameworkStatsLog.APP_FREEZE_CHANGED;
    int i4 = IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT;
    if (i == 0) {
      switch (roundHalfUp) {
        case 0:
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 9:
        case 10:
          i3 = 255;
          break;
        default:
          i3 = 0;
        case 7:
        case 8:
          i4 = i3;
          break;
      }
    } else {
      if (i == 1) {
        switch (roundHalfUp) {
        }
      } else if (i == 2) {
        switch (roundHalfUp) {
          case 0:
          case 1:
          case 2:
          case 3:
          case 4:
          case 5:
          case 6:
          case 7:
          case 8:
          case 9:
          case 10:
            i2 = 255;
            break;
          default:
            i2 = 0;
            i4 = 0;
            break;
        }
        i3 = i4;
        i4 = i2;
      } else {
        i3 = -1;
      }
      i4 = i3;
    }
    iArr[0] = i4;
    iArr[1] = i3;
    return iArr;
  }

  /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
  /* JADX WARN: Removed duplicated region for block: B:12:0x002b  */
  /* JADX WARN: Removed duplicated region for block: B:16:0x004d  */
  /* JADX WARN: Removed duplicated region for block: B:17:0x0050 A[PHI: r5
   0x0050: PHI (r5v10 int) =
   (r5v1 int)
   (r5v0 int)
   (r5v2 int)
   (r5v3 int)
   (r5v4 int)
   (r5v5 int)
   (r5v6 int)
   (r5v7 int)
   (r5v8 int)
   (r5v9 int)
   (r5v11 int)
   (r5v0 int)
   (r5v14 int)
   (r5v15 int)
   (r5v16 int)
   (r5v17 int)
   (r5v18 int)
   (r5v19 int)
   (r5v20 int)
   (r5v21 int)
  binds: [B:29:0x004f, B:19:0x002f, B:27:0x0048, B:26:0x0045, B:25:0x0042, B:24:0x003f, B:23:0x003c, B:22:0x0039, B:21:0x0036, B:20:0x0033, B:16:0x004d, B:3:0x000f, B:11:0x0028, B:10:0x0025, B:9:0x0022, B:8:0x001f, B:7:0x001c, B:6:0x0019, B:5:0x0016, B:4:0x0013] A[DONT_GENERATE, DONT_INLINE]] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final int[] getMaxMinColorTrnasferValue_GB_DMC(int i, double d, double d2) {
    int[] iArr = new int[2];
    int roundHalfUp = roundHalfUp(d);
    int i2 = 85;
    int i3 = 19;
    if (i == 0) {
      switch (roundHalfUp) {
        case 0:
        default:
          i2 = 0;
          i3 = i2;
          break;
        case 1:
          i2 = 19;
          break;
        case 2:
          i2 = 37;
          i3 = i2;
          break;
        case 3:
          i2 = 51;
          i3 = i2;
          break;
        case 4:
          i2 = 63;
          i3 = i2;
          break;
        case 5:
          i2 = 75;
          i3 = i2;
          break;
        case 6:
          i3 = i2;
          break;
        case 7:
          i2 = 93;
          i3 = i2;
          break;
        case 8:
          i2 = 103;
          i3 = i2;
          break;
        case 9:
          i2 = 109;
          i3 = i2;
          break;
        case 10:
          i2 = 115;
          i3 = i2;
          break;
      }
    } else if (i == 1) {
      switch (roundHalfUp) {
        case 2:
          i2 = 35;
          i3 = i2;
          break;
        case 3:
          i2 = 47;
          i3 = i2;
          break;
        case 4:
          i2 = 57;
          i3 = i2;
          break;
        case 5:
          i2 = 65;
          i3 = i2;
          break;
        case 6:
          i2 = 73;
          i3 = i2;
          break;
        case 7:
          i2 = 79;
          i3 = i2;
          break;
        case 9:
          i2 = 89;
          i3 = i2;
          break;
        case 10:
          i2 = 94;
          i3 = i2;
          break;
      }
    } else {
      if (i != 2) {
        i2 = -1;
        i3 = i2;
      }
      i2 = 0;
      i3 = i2;
    }
    iArr[0] = i3;
    iArr[1] = i2;
    return iArr;
  }

  /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
  public final int[] getMaxMinColorTrnasferValue_MR_DMC(int i, double d, double d2) {
    int i2;
    int[] iArr = new int[2];
    int roundHalfUp = roundHalfUp(d);
    int i3 = FrameworkStatsLog.APP_FREEZE_CHANGED;
    int i4 = IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT;
    if (i == 0) {
      switch (roundHalfUp) {
        case 0:
          i3 = 255;
          break;
        default:
          i3 = 0;
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
          i4 = i3;
          break;
      }
    } else if (i == 1) {
      switch (roundHalfUp) {
        case 0:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
          i3 = 255;
          break;
        case 1:
          break;
        default:
          i3 = 0;
          i4 = 0;
          break;
      }
      int i5 = i4;
      i4 = i3;
      i3 = i5;
    } else if (i == 2) {
      switch (roundHalfUp) {
        case 0:
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
          i2 = i4;
          break;
        case 7:
          i4 = 240;
          i2 = i4;
          break;
        case 8:
          i4 = FrameworkStatsLog.EXCLUSION_RECT_STATE_CHANGED;
          i2 = i4;
          break;
        case 9:
          i4 = 204;
          i2 = i4;
          break;
        case 10:
          i4 = 182;
          i2 = i4;
          break;
        default:
          i2 = 0;
          i4 = 0;
          break;
      }
      i3 = i4;
      i4 = i2;
    } else {
      i3 = -1;
      i4 = i3;
    }
    iArr[0] = i4;
    iArr[1] = i3;
    return iArr;
  }

  public final int[] getMaxMinColorTrnasferValue_MG_DMC(int i, double d, double d2) {
    int i2;
    int i3;
    int i4;
    int[] iArr = new int[2];
    int roundHalfUp = roundHalfUp(d);
    if (i == 0) {
      switch (roundHalfUp) {
        case 0:
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 9:
        case 10:
        default:
          i2 = 0;
          break;
        case 7:
        case 8:
          i2 = 1;
          break;
      }
      i4 = 0;
    } else {
      if (i == 1) {
        switch (roundHalfUp) {
          case 0:
          default:
            i2 = 0;
            break;
          case 1:
          case 2:
          case 3:
          case 4:
          case 5:
          case 6:
          case 7:
          case 8:
          case 9:
          case 10:
            i2 = 1;
            break;
        }
      } else if (i == 2) {
        switch (roundHalfUp) {
          case 0:
          case 6:
          case 7:
          case 8:
          case 9:
          case 10:
          default:
            i4 = 0;
            break;
          case 1:
            i3 = 5;
            i4 = i3;
            break;
          case 2:
            i3 = 11;
            i4 = i3;
            break;
          case 3:
            i3 = 13;
            i4 = i3;
            break;
          case 4:
            i3 = 10;
            i4 = i3;
            break;
          case 5:
            i3 = 4;
            i4 = i3;
            break;
        }
        i2 = 0;
      } else {
        i2 = -1;
      }
      i4 = i2;
    }
    iArr[0] = i4;
    iArr[1] = i2;
    return iArr;
  }

  /* JADX WARN: Removed duplicated region for block: B:10:0x003f  */
  /* JADX WARN: Removed duplicated region for block: B:11:0x0043  */
  /* JADX WARN: Removed duplicated region for block: B:12:0x0047  */
  /* JADX WARN: Removed duplicated region for block: B:13:0x004b  */
  /* JADX WARN: Removed duplicated region for block: B:14:0x004f  */
  /* JADX WARN: Removed duplicated region for block: B:15:0x0051  */
  /* JADX WARN: Removed duplicated region for block: B:16:0x0053  */
  /* JADX WARN: Removed duplicated region for block: B:17:0x0058  */
  /* JADX WARN: Removed duplicated region for block: B:18:0x005b  */
  /* JADX WARN: Removed duplicated region for block: B:19:0x0060  */
  /* JADX WARN: Removed duplicated region for block: B:4:0x0037  */
  /* JADX WARN: Removed duplicated region for block: B:9:0x003c  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final int[] getMaxMinColorTrnasferValue_MB_DMC(int i, double d, double d2) {
    int i2;
    int[] iArr = new int[2];
    int roundHalfUp = roundHalfUp(d);
    int i3 = 138;
    int i4 = 228;
    int i5 = IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT;
    if (i == 0) {
      switch (roundHalfUp) {
        case 0:
          i3 = 255;
          i4 = i3;
          break;
        case 1:
          i4 = 238;
          i3 = 234;
          break;
        case 2:
          i3 = 218;
          break;
        case 3:
          i4 = 226;
          i3 = 204;
          break;
        case 4:
          i3 = 190;
          break;
        case 5:
          i3 = 179;
          break;
        case 6:
          i3 = 169;
          i4 = 234;
          break;
        case 7:
          i4 = 240;
          i3 = 161;
          break;
        case 8:
          i4 = 246;
          i3 = 152;
          break;
        case 9:
          i4 = 250;
          i3 = 144;
          break;
        case 10:
          i4 = 252;
          break;
        default:
          i3 = 0;
          i4 = i3;
          break;
      }
    } else if (i == 1) {
      switch (roundHalfUp) {
      }
    } else if (i == 2) {
      switch (roundHalfUp) {
        case 0:
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
          i2 = 255;
          break;
        default:
          i2 = 0;
          i5 = 0;
          break;
      }
      i4 = i2;
      i3 = i5;
    } else {
      i4 = -1;
      i3 = -1;
    }
    iArr[0] = i4;
    iArr[1] = i3;
    return iArr;
  }

  /* JADX WARN: Removed duplicated region for block: B:13:0x001a  */
  /* JADX WARN: Removed duplicated region for block: B:15:0x001d  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final int[] getMaxMinColorTrnasferValue_YR_DMC(int i, double d, double d2) {
    int i2;
    int i3;
    int[] iArr = new int[2];
    int roundHalfUp = roundHalfUp(d);
    int i4 = IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT;
    if (i == 0) {
      i3 = FrameworkStatsLog.APP_FREEZE_CHANGED;
      switch (roundHalfUp) {
        case 0:
          i3 = i4;
          break;
        default:
          i3 = 0;
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
          i4 = i3;
          break;
      }
    } else {
      if (i == 1) {
        switch (roundHalfUp) {
          case 0:
          case 1:
          case 2:
          case 3:
          case 4:
          case 5:
          case 6:
          case 7:
          case 8:
          case 9:
          case 10:
            i2 = 255;
            break;
          default:
            i2 = 0;
            i4 = 0;
            break;
        }
      } else if (i == 2) {
        switch (roundHalfUp) {
        }
      } else {
        i4 = -1;
        i3 = i4;
      }
      i3 = i2;
    }
    iArr[0] = i3;
    iArr[1] = i4;
    return iArr;
  }

  /* JADX WARN: Removed duplicated region for block: B:4:0x0010  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final int[] getMaxMinColorTrnasferValue_YG_DMC(int i, double d, double d2) {
    int i2;
    int[] iArr = new int[2];
    int roundHalfUp = roundHalfUp(d);
    int i3 = IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT;
    if (i == 0) {
      switch (roundHalfUp) {
        case 0:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
          i2 = i3;
          break;
        case 1:
          i3 = 204;
          i2 = 207;
          break;
        default:
          i2 = 0;
          i3 = 0;
          break;
      }
    } else if (i == 1) {
      int i4 = FrameworkStatsLog.APP_FREEZE_CHANGED;
      switch (roundHalfUp) {
        case 0:
          i4 = 255;
          break;
        default:
          i4 = 0;
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
          i3 = i4;
          break;
      }
      i2 = i4;
    } else {
      if (i == 2) {
        switch (roundHalfUp) {
        }
      } else {
        i3 = -1;
      }
      i2 = i3;
    }
    iArr[0] = i2;
    iArr[1] = i3;
    return iArr;
  }

  public final int[] getMaxMinColorTrnasferValue_YB_DMC(int i, double d, double d2) {
    int[] iArr = new int[2];
    roundHalfUp(d);
    int i2 = (i == 0 || i == 1 || i == 2) ? 0 : -1;
    int i3 = i2;
    iArr[0] = i2;
    iArr[1] = i3;
    return iArr;
  }

  public final int[] getMaxMinColorTrnasferValue_CR_DMC(int i, double d, double d2) {
    int i2;
    int[] iArr = new int[2];
    int roundHalfUp = roundHalfUp(d);
    if (i == 0) {
      switch (roundHalfUp) {
        case 0:
        default:
          i2 = 0;
          break;
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
          i2 = 1;
          break;
      }
    } else {
      if (i != 1 && i != 2) {
        i2 = -1;
      }
      i2 = 0;
    }
    iArr[0] = i2;
    iArr[1] = i2;
    return iArr;
  }

  /* JADX WARN: Removed duplicated region for block: B:4:0x0010  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final int[] getMaxMinColorTrnasferValue_CG_DMC(int i, double d, double d2) {
    int i2;
    int[] iArr = new int[2];
    int roundHalfUp = roundHalfUp(d);
    int i3 = IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT;
    if (i == 0) {
      switch (roundHalfUp) {
        case 0:
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
          i2 = i3;
          break;
        default:
          i2 = 0;
          i3 = 0;
          break;
      }
    } else if (i == 1) {
      int i4 = FrameworkStatsLog.APP_FREEZE_CHANGED;
      switch (roundHalfUp) {
        case 0:
          i4 = 255;
          break;
        default:
          i4 = 0;
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
          i3 = i4;
          break;
      }
      i2 = i4;
    } else {
      if (i == 2) {
        switch (roundHalfUp) {
        }
      } else {
        i3 = -1;
      }
      i2 = i3;
    }
    iArr[0] = i2;
    iArr[1] = i3;
    return iArr;
  }

  /* JADX WARN: Removed duplicated region for block: B:4:0x0010  */
  /* JADX WARN: Removed duplicated region for block: B:8:0x0020 A[PHI: r4
  0x0020: PHI (r4v2 int) = (r4v1 int), (r4v0 int), (r4v0 int), (r4v0 int) binds: [B:13:0x001f, B:12:0x001b, B:10:0x0015, B:3:0x000d] A[DONT_GENERATE, DONT_INLINE]] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final int[] getMaxMinColorTrnasferValue_CB_DMC(int i, double d, double d2) {
    int i2;
    int[] iArr = new int[2];
    int roundHalfUp = roundHalfUp(d);
    int i3 = IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT;
    if (i == 0) {
      switch (roundHalfUp) {
        case 0:
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
          i2 = i3;
          break;
        default:
          i2 = 0;
          i3 = 0;
          break;
      }
    } else if (i == 1) {
      switch (roundHalfUp) {
      }
    } else {
      if (i == 2) {
        switch (roundHalfUp) {
        }
      } else {
        i3 = -1;
      }
      i2 = i3;
    }
    iArr[0] = i2;
    iArr[1] = i3;
    return iArr;
  }

  public final int[] getMaxMinColorTrnasferValue_BR_DMC(int i, double d, double d2) {
    int i2;
    int i3;
    int i4;
    int[] iArr = new int[2];
    int roundHalfUp = roundHalfUp(d);
    if (i == 0) {
      switch (roundHalfUp) {
        case 0:
        default:
          i2 = 0;
          break;
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
          i2 = 1;
          break;
      }
      i3 = i2;
    } else {
      if (i != 1) {
        if (i == 2) {
          i3 = 41;
          switch (roundHalfUp) {
            case 1:
              i4 = 6;
              i3 = i4;
              i2 = 0;
              break;
            case 2:
              i4 = 12;
              i3 = i4;
              i2 = 0;
              break;
            case 3:
              i4 = 16;
              i3 = i4;
              i2 = 0;
              break;
            case 4:
              i4 = 21;
              i3 = i4;
              i2 = 0;
              break;
            case 5:
              i4 = 26;
              i3 = i4;
              i2 = 0;
              break;
            case 6:
              i2 = 0;
              i3 = 32;
              break;
            case 7:
              i2 = 0;
              break;
            case 8:
              i3 = 83;
              i2 = 32;
              break;
            case 9:
              i3 = 102;
              i2 = 41;
              break;
            case 10:
              i2 = 47;
              i3 = 118;
              break;
          }
        } else {
          i2 = -1;
          i3 = i2;
        }
      }
      i2 = 0;
      i3 = i2;
    }
    iArr[0] = i3;
    iArr[1] = i2;
    return iArr;
  }

  public final int[] getMaxMinColorTrnasferValue_BG_DMC(int i, double d, double d2) {
    int i2;
    int i3;
    int i4;
    int[] iArr = new int[2];
    int roundHalfUp = roundHalfUp(d);
    if (i != 0) {
      if (i == 1) {
        switch (roundHalfUp) {
          case 1:
          case 2:
          case 3:
          case 4:
          case 5:
          case 6:
          case 7:
          case 8:
          case 9:
          case 10:
            i2 = 1;
            break;
        }
      } else {
        if (i == 2) {
          switch (roundHalfUp) {
            case 0:
            default:
              i4 = 0;
              break;
            case 1:
              i3 = 6;
              i4 = i3;
              break;
            case 2:
              i3 = 12;
              i4 = i3;
              break;
            case 3:
              i3 = 16;
              i4 = i3;
              break;
            case 4:
              i3 = 21;
              i4 = i3;
              break;
            case 5:
              i3 = 26;
              i4 = i3;
              break;
            case 6:
              i3 = 32;
              i4 = i3;
              break;
            case 7:
              i3 = 41;
              i4 = i3;
              break;
            case 8:
              i3 = 51;
              i4 = i3;
              break;
            case 9:
              i3 = 61;
              i4 = i3;
              break;
            case 10:
              i3 = 71;
              i4 = i3;
              break;
          }
          i2 = 0;
          iArr[0] = i4;
          iArr[1] = i2;
          return iArr;
        }
        i2 = -1;
      }
      i4 = i2;
      iArr[0] = i4;
      iArr[1] = i2;
      return iArr;
    }
    i2 = 0;
    i4 = i2;
    iArr[0] = i4;
    iArr[1] = i2;
    return iArr;
  }

  /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
  /* JADX WARN: Removed duplicated region for block: B:4:0x0012  */
  /* JADX WARN: Removed duplicated region for block: B:5:0x0014  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final int[] getMaxMinColorTrnasferValue_BB_DMC(int i, double d, double d2) {
    int i2;
    int[] iArr = new int[2];
    int roundHalfUp = roundHalfUp(d);
    int i3 = IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT;
    int i4 = FrameworkStatsLog.APP_FREEZE_CHANGED;
    if (i == 0) {
      switch (roundHalfUp) {
        case 0:
          i4 = i3;
          break;
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
          i3 = 254;
          break;
        default:
          i3 = 0;
          i4 = i3;
          break;
      }
    } else {
      if (i == 1) {
        switch (roundHalfUp) {
        }
      } else if (i == 2) {
        switch (roundHalfUp) {
          case 0:
          case 1:
          case 2:
          case 3:
          case 4:
          case 5:
          case 6:
          case 7:
          case 8:
          case 9:
          case 10:
            i2 = 255;
            break;
          default:
            i2 = 0;
            i3 = 0;
            break;
        }
        i4 = i2;
      } else {
        i3 = -1;
      }
      i4 = i3;
    }
    iArr[0] = i4;
    iArr[1] = i3;
    return iArr;
  }

  public int getColorTransferValue_Hybrid(int i, int i2, int i3, double d, double d2) {
    int[] iArr;
    int i4;
    if (i == 1) {
      if (i2 == 1) {
        iArr = getMaxMinColorTrnasferValue_RR_Hybrid(i3, d, d2);
      } else if (i2 == 3) {
        iArr = getMaxMinColorTrnasferValue_RG_Hybrid(i3, d, d2);
      } else {
        if (i2 == 5) {
          iArr = getMaxMinColorTrnasferValue_RB_Hybrid(i3, d, d2);
        }
        iArr = null;
      }
    } else if (i == 2) {
      if (i2 == 1) {
        iArr = getMaxMinColorTrnasferValue_YR_Hybrid(i3, d, d2);
      } else if (i2 == 3) {
        iArr = getMaxMinColorTrnasferValue_YG_Hybrid(i3, d, d2);
      } else {
        if (i2 == 5) {
          iArr = getMaxMinColorTrnasferValue_YB_Hybrid(i3, d, d2);
        }
        iArr = null;
      }
    } else if (i == 3) {
      if (i2 == 1) {
        iArr = getMaxMinColorTrnasferValue_GR_Hybrid(i3, d, d2);
      } else if (i2 == 3) {
        iArr = getMaxMinColorTrnasferValue_GG_Hybrid(i3, d, d2);
      } else {
        if (i2 == 5) {
          iArr = getMaxMinColorTrnasferValue_GB_Hybrid(i3, d, d2);
        }
        iArr = null;
      }
    } else if (i == 4) {
      if (i2 == 1) {
        iArr = getMaxMinColorTrnasferValue_CR_Hybrid(i3, d, d2);
      } else if (i2 == 3) {
        iArr = getMaxMinColorTrnasferValue_CG_Hybrid(i3, d, d2);
      } else {
        if (i2 == 5) {
          iArr = getMaxMinColorTrnasferValue_CB_Hybrid(i3, d, d2);
        }
        iArr = null;
      }
    } else if (i == 5) {
      if (i2 == 1) {
        iArr = getMaxMinColorTrnasferValue_BR_Hybrid(i3, d, d2);
      } else if (i2 == 3) {
        iArr = getMaxMinColorTrnasferValue_BG_Hybrid(i3, d, d2);
      } else {
        if (i2 == 5) {
          iArr = getMaxMinColorTrnasferValue_BB_Hybrid(i3, d, d2);
        }
        iArr = null;
      }
    } else if (i != 6) {
      iArr = new int[] {-1, -1};
    } else if (i2 == 1) {
      iArr = getMaxMinColorTrnasferValue_MR_Hybrid(i3, d, d2);
    } else if (i2 == 3) {
      iArr = getMaxMinColorTrnasferValue_MG_Hybrid(i3, d, d2);
    } else {
      if (i2 == 5) {
        iArr = getMaxMinColorTrnasferValue_MB_Hybrid(i3, d, d2);
      }
      iArr = null;
    }
    if (iArr == null || ((i4 = iArr[0]) == -1 && iArr[1] == -1)) {
      return -1;
    }
    return (int) (((i4 - iArr[1]) * (roundHalfUp(d2) / 10.0d)) + iArr[1]);
  }

  /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
  jadx.core.utils.exceptions.JadxRuntimeException: Failed to find switch 'out' block (already processed)
  	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.calcSwitchOut(SwitchRegionMaker.java:202)
  	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:61)
  	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:115)
  	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
  	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:94)
  	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
  	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
  	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:100)
  	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
  	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
  	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:100)
  	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
  	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
  	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:49)
  	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
  */
  /* JADX WARN: Removed duplicated region for block: B:11:0x001b  */
  /* JADX WARN: Removed duplicated region for block: B:12:0x003c A[FALL_THROUGH, PHI: r1 r6
  0x003c: PHI (r1v5 int) = (r1v0 int), (r1v1 int), (r1v2 int), (r1v3 int), (r1v4 int), (r1v0 int), (r1v0 int), (r1v0 int) binds: [B:24:0x003b, B:23:0x0038, B:22:0x0036, B:21:0x0034, B:20:0x0031, B:14:0x001f, B:15:0x0023, B:3:0x0011] A[DONT_GENERATE, DONT_INLINE]
  0x003c: PHI (r6v5 int) = (r6v2 int), (r6v0 int), (r6v0 int), (r6v0 int), (r6v3 int), (r6v0 int), (r6v4 int), (r6v0 int) binds: [B:24:0x003b, B:23:0x0038, B:22:0x0036, B:21:0x0034, B:20:0x0031, B:14:0x001f, B:15:0x0023, B:3:0x0011] A[DONT_GENERATE, DONT_INLINE]] */
  /* JADX WARN: Removed duplicated region for block: B:4:0x0014  */
  /* JADX WARN: Removed duplicated region for block: B:9:0x0016  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final int[] getMaxMinColorTrnasferValue_RR_Hybrid(int r4, double r5, double r7) {
    /*
        r3 = this;
        r7 = 2
        int[] r8 = new int[r7]
        int r3 = r3.roundHalfUp(r5)
        r5 = 1
        r6 = 204(0xcc, float:2.86E-43)
        r0 = 254(0xfe, float:3.56E-43)
        r1 = 255(0xff, float:3.57E-43)
        r2 = 0
        if (r4 != 0) goto L1d
        switch(r3) {
            case 0: goto L1b;
            case 1: goto L18;
            case 2: goto L3c;
            case 3: goto L3c;
            case 4: goto L3c;
            case 5: goto L3c;
            case 6: goto L16;
            case 7: goto L16;
            case 8: goto L16;
            case 9: goto L16;
            case 10: goto L16;
            default: goto L14;
        }
    L14:
        r6 = r2
        goto L3f
    L16:
        r6 = r0
        goto L40
    L18:
        r0 = 239(0xef, float:3.35E-43)
        goto L40
    L1b:
        r6 = r1
        goto L3f
    L1d:
        if (r4 != r5) goto L28
        switch(r3) {
            case 0: goto L1b;
            case 1: goto L25;
            case 2: goto L3c;
            case 3: goto L3c;
            case 4: goto L3c;
            case 5: goto L3c;
            case 6: goto L23;
            case 7: goto L16;
            case 8: goto L23;
            case 9: goto L23;
            case 10: goto L23;
            default: goto L22;
        }
    L22:
        goto L14
    L23:
        r6 = r0
        goto L3c
    L25:
        r0 = 238(0xee, float:3.34E-43)
        goto L40
    L28:
        if (r4 != r7) goto L3e
        r4 = 228(0xe4, float:3.2E-43)
        r7 = 226(0xe2, float:3.17E-43)
        switch(r3) {
            case 0: goto L3b;
            case 1: goto L38;
            case 2: goto L36;
            case 3: goto L34;
            case 4: goto L34;
            case 5: goto L36;
            case 6: goto L3b;
            case 7: goto L3b;
            case 8: goto L3b;
            case 9: goto L3b;
            case 10: goto L3b;
            default: goto L31;
        }
    L31:
        r6 = r2
        r1 = r6
        goto L3c
    L34:
        r1 = r4
        goto L3c
    L36:
        r1 = r7
        goto L3c
    L38:
        r1 = 220(0xdc, float:3.08E-43)
        goto L3c
    L3b:
        r6 = r1
    L3c:
        r0 = r1
        goto L40
    L3e:
        r6 = -1
    L3f:
        r0 = r6
    L40:
        r8[r2] = r0
        r8[r5] = r6
        return r8
    */
    throw new UnsupportedOperationException(
        "Method not decompiled:"
            + " com.android.server.accessibility.ColorTransferTable.getMaxMinColorTrnasferValue_RR_Hybrid(int,"
            + " double, double):int[]");
  }

  public final int[] getMaxMinColorTrnasferValue_RG_Hybrid(int i, double d, double d2) {
    int i2;
    int i3;
    int[] iArr = new int[2];
    int roundHalfUp = roundHalfUp(d);
    if (i == 0) {
      switch (roundHalfUp) {
        case 0:
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 8:
        case 9:
        case 10:
        default:
          i2 = 0;
          break;
        case 7:
          i2 = 1;
          break;
      }
      i3 = 0;
    } else if (i == 1) {
      switch (roundHalfUp) {
        case 0:
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        default:
          i2 = 0;
          break;
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
          i2 = 1;
          break;
      }
      i3 = i2;
    } else {
      if (i != 2) {
        i2 = -1;
        i3 = i2;
      }
      i2 = 0;
      i3 = i2;
    }
    iArr[0] = i2;
    iArr[1] = i3;
    return iArr;
  }

  public final int[] getMaxMinColorTrnasferValue_RB_Hybrid(int i, double d, double d2) {
    int i2;
    int i3 = 2;
    int[] iArr = new int[2];
    int roundHalfUp = roundHalfUp(d);
    int i4 = FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__SET_MTE_POLICY;
    if (i == 0) {
      switch (roundHalfUp) {
        case 0:
        case 1:
        default:
          i4 = 0;
          break;
        case 2:
        case 3:
        case 4:
        case 5:
          i4 = 1;
          break;
        case 6:
          i4 = 156;
          break;
        case 7:
          i4 = 172;
          break;
        case 8:
          i4 = 188;
          break;
        case 9:
          i4 = 202;
          break;
        case 10:
          break;
      }
    } else {
      if (i != 1) {
        if (i == 2) {
          i2 = 0;
          i4 = 0;
        } else {
          i4 = -1;
          i2 = -1;
        }
        iArr[0] = i4;
        iArr[1] = i2;
        return iArr;
      }
      switch (roundHalfUp) {
        case 0:
        case 1:
        default:
          i3 = 0;
          break;
        case 2:
          i3 = 1;
          break;
        case 3:
        case 4:
          break;
        case 5:
          i3 = 3;
          break;
        case 6:
          i3 = 156;
          break;
        case 7:
          i3 = 172;
          break;
        case 8:
          i3 = 188;
          break;
        case 9:
          i3 = 202;
          break;
        case 10:
          i3 = 216;
          break;
      }
      i4 = i3;
    }
    i2 = 0;
    iArr[0] = i4;
    iArr[1] = i2;
    return iArr;
  }

  /* JADX WARN: Removed duplicated region for block: B:8:0x0010  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final int[] getMaxMinColorTrnasferValue_GR_Hybrid(int i, double d, double d2) {
    int i2;
    int[] iArr = new int[2];
    int roundHalfUp = roundHalfUp(d);
    if (i == 0) {
      switch (roundHalfUp) {
        case 0:
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        default:
          i2 = 0;
          break;
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
          i2 = 1;
          break;
      }
    } else {
      if (i == 1) {
        switch (roundHalfUp) {
        }
      } else if (i == 2) {
        switch (roundHalfUp) {
          case 6:
            i2 = 38;
            break;
          case 7:
            i2 = 51;
            break;
          case 8:
            i2 = 63;
            break;
          case 9:
            i2 = 73;
            break;
          case 10:
            i2 = 81;
            break;
        }
      } else {
        i2 = -1;
      }
      i2 = 0;
    }
    iArr[0] = i2;
    iArr[1] = i2;
    return iArr;
  }

  /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
  jadx.core.utils.exceptions.JadxRuntimeException: Failed to find switch 'out' block (already processed)
  	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.calcSwitchOut(SwitchRegionMaker.java:202)
  	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:61)
  	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:115)
  	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
  	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:94)
  	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
  	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
  	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:100)
  	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
  	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
  	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:100)
  	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
  	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
  	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:49)
  	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
  */
  /* JADX WARN: Removed duplicated region for block: B:15:0x0026  */
  /* JADX WARN: Removed duplicated region for block: B:16:0x0053 A[FALL_THROUGH, PHI: r6
  0x0053: PHI (r6v6 int) = (r6v1 int), (r6v0 int), (r6v11 int), (r6v0 int) binds: [B:32:0x0052, B:18:0x002a, B:4:0x0014, B:3:0x0011] A[DONT_GENERATE, DONT_INLINE]] */
  /* JADX WARN: Removed duplicated region for block: B:4:0x0014  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final int[] getMaxMinColorTrnasferValue_GG_Hybrid(int r4, double r5, double r7) {
    /*
        r3 = this;
        r7 = 2
        int[] r8 = new int[r7]
        int r3 = r3.roundHalfUp(r5)
        r5 = 1
        r6 = 254(0xfe, float:3.56E-43)
        r0 = 204(0xcc, float:2.86E-43)
        r1 = 255(0xff, float:3.57E-43)
        r2 = 0
        if (r4 != 0) goto L28
        switch(r3) {
            case 0: goto L26;
            case 1: goto L22;
            case 2: goto L1f;
            case 3: goto L1c;
            case 4: goto L19;
            case 5: goto L16;
            case 6: goto L26;
            case 7: goto L53;
            case 8: goto L53;
            case 9: goto L26;
            case 10: goto L26;
            default: goto L14;
        }
    L14:
        r6 = r2
        goto L53
    L16:
        r6 = 243(0xf3, float:3.4E-43)
        goto L24
    L19:
        r6 = 235(0xeb, float:3.3E-43)
        goto L24
    L1c:
        r6 = 227(0xe3, float:3.18E-43)
        goto L24
    L1f:
        r6 = 221(0xdd, float:3.1E-43)
        goto L24
    L22:
        r6 = 214(0xd6, float:3.0E-43)
    L24:
        r1 = r6
        goto L50
    L26:
        r6 = r1
        goto L54
    L28:
        if (r4 != r5) goto L3a
        switch(r3) {
            case 0: goto L26;
            case 1: goto L37;
            case 2: goto L34;
            case 3: goto L31;
            case 4: goto L2e;
            case 5: goto L50;
            case 6: goto L53;
            case 7: goto L53;
            case 8: goto L53;
            case 9: goto L53;
            case 10: goto L53;
            default: goto L2d;
        }
    L2d:
        goto L14
    L2e:
        r6 = 251(0xfb, float:3.52E-43)
        goto L24
    L31:
        r6 = 239(0xef, float:3.35E-43)
        goto L24
    L34:
        r6 = 229(0xe5, float:3.21E-43)
        goto L24
    L37:
        r6 = 220(0xdc, float:3.08E-43)
        goto L24
    L3a:
        if (r4 != r7) goto L52
        r4 = 218(0xda, float:3.05E-43)
        switch(r3) {
            case 0: goto L4f;
            case 1: goto L4c;
            case 2: goto L49;
            case 3: goto L46;
            case 4: goto L44;
            case 5: goto L44;
            case 6: goto L4f;
            case 7: goto L4f;
            case 8: goto L4f;
            case 9: goto L4f;
            case 10: goto L4f;
            default: goto L41;
        }
    L41:
        r0 = r2
        r1 = r0
        goto L50
    L44:
        r1 = r4
        goto L50
    L46:
        r1 = 217(0xd9, float:3.04E-43)
        goto L50
    L49:
        r1 = 215(0xd7, float:3.01E-43)
        goto L50
    L4c:
        r1 = 212(0xd4, float:2.97E-43)
        goto L50
    L4f:
        r0 = r1
    L50:
        r6 = r0
        goto L54
    L52:
        r6 = -1
    L53:
        r1 = r6
    L54:
        r8[r2] = r1
        r8[r5] = r6
        return r8
    */
    throw new UnsupportedOperationException(
        "Method not decompiled:"
            + " com.android.server.accessibility.ColorTransferTable.getMaxMinColorTrnasferValue_GG_Hybrid(int,"
            + " double, double):int[]");
  }

  /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
  /* JADX WARN: Removed duplicated region for block: B:12:0x0035 A[PHI: r4
   0x0035: PHI (r4v6 int) =
   (r4v1 int)
   (r4v0 int)
   (r4v2 int)
   (r4v3 int)
   (r4v4 int)
   (r4v5 int)
   (r4v0 int)
   (r4v9 int)
   (r4v10 int)
   (r4v11 int)
   (r4v12 int)
  binds: [B:20:0x0034, B:14:0x001f, B:18:0x002c, B:17:0x0029, B:16:0x0026, B:15:0x0023, B:3:0x000d, B:7:0x001a, B:6:0x0017, B:5:0x0014, B:4:0x0011] A[DONT_GENERATE, DONT_INLINE]] */
  /* JADX WARN: Removed duplicated region for block: B:8:0x0031  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final int[] getMaxMinColorTrnasferValue_GB_Hybrid(int i, double d, double d2) {
    int i2;
    int[] iArr = new int[2];
    int roundHalfUp = roundHalfUp(d);
    int i3 = 85;
    if (i == 0) {
      switch (roundHalfUp) {
        case 0:
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        default:
          i2 = 0;
          i3 = 0;
          break;
        case 6:
          i2 = i3;
          break;
        case 7:
          i3 = 93;
          i2 = i3;
          break;
        case 8:
          i3 = 103;
          i2 = i3;
          break;
        case 9:
          i3 = 109;
          i2 = i3;
          break;
        case 10:
          i3 = 115;
          i2 = i3;
          break;
      }
    } else if (i == 1) {
      switch (roundHalfUp) {
        case 6:
          i3 = 73;
          i2 = i3;
          break;
        case 7:
          i3 = 79;
          i2 = i3;
          break;
        case 9:
          i3 = 89;
          i2 = i3;
          break;
        case 10:
          i3 = 94;
          i2 = i3;
          break;
      }
    } else {
      if (i != 2) {
        i3 = -1;
        i2 = i3;
      }
      i2 = 0;
      i3 = 0;
    }
    iArr[0] = i2;
    iArr[1] = i3;
    return iArr;
  }

  /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
  /* JADX WARN: Removed duplicated region for block: B:5:0x0014  */
  /* JADX WARN: Removed duplicated region for block: B:9:0x001b  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final int[] getMaxMinColorTrnasferValue_MR_Hybrid(int i, double d, double d2) {
    int i2;
    int[] iArr = new int[2];
    int roundHalfUp = roundHalfUp(d);
    int i3 = 204;
    int i4 = IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT;
    if (i == 0) {
      switch (roundHalfUp) {
        case 0:
          i3 = 255;
          break;
        case 1:
          i4 = 249;
          break;
        case 2:
        case 3:
        case 4:
        case 5:
          break;
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
          i3 = 254;
          i4 = i3;
          break;
        default:
          i3 = 0;
          i4 = i3;
          break;
      }
    } else if (i == 1) {
      switch (roundHalfUp) {
        case 1:
          i4 = FrameworkStatsLog.f446x58c6c32;
          break;
      }
    } else if (i == 2) {
      switch (roundHalfUp) {
        case 0:
        case 6:
          i2 = 255;
          i3 = i2;
          break;
        case 1:
          i2 = 225;
          break;
        case 2:
          i2 = 232;
          break;
        case 3:
          i2 = 237;
          break;
        case 4:
          i2 = FrameworkStatsLog.REBOOT_ESCROW_RECOVERY_REPORTED;
          break;
        case 5:
          i2 = 236;
          break;
        case 7:
          i3 = 240;
          i2 = i3;
          break;
        case 8:
          i3 = FrameworkStatsLog.EXCLUSION_RECT_STATE_CHANGED;
          i2 = i3;
          break;
        case 9:
          i2 = i3;
          break;
        case 10:
          i3 = 182;
          i2 = i3;
          break;
        default:
          i2 = 0;
          i3 = i2;
          break;
      }
      i4 = i2;
    } else {
      i3 = -1;
      i4 = i3;
    }
    iArr[0] = i4;
    iArr[1] = i3;
    return iArr;
  }

  public final int[] getMaxMinColorTrnasferValue_MG_Hybrid(int i, double d, double d2) {
    int i2;
    int i3;
    int[] iArr = new int[2];
    int roundHalfUp = roundHalfUp(d);
    if (i == 0) {
      switch (roundHalfUp) {
        case 0:
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        case 6:
        case 9:
        case 10:
        default:
          i2 = 0;
          break;
        case 7:
        case 8:
          i2 = 1;
          break;
      }
      i3 = 0;
    } else if (i == 1) {
      switch (roundHalfUp) {
        case 0:
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        default:
          i2 = 0;
          break;
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
          i2 = 1;
          break;
      }
      i3 = i2;
    } else {
      if (i != 2) {
        i2 = -1;
        i3 = i2;
      }
      i2 = 0;
      i3 = i2;
    }
    iArr[0] = i3;
    iArr[1] = i2;
    return iArr;
  }

  /* JADX WARN: Removed duplicated region for block: B:10:0x003d  */
  /* JADX WARN: Removed duplicated region for block: B:13:0x0043  */
  /* JADX WARN: Removed duplicated region for block: B:5:0x002f  */
  /* JADX WARN: Removed duplicated region for block: B:7:0x0033  */
  /* JADX WARN: Removed duplicated region for block: B:8:0x0037  */
  /* JADX WARN: Removed duplicated region for block: B:9:0x003a  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final int[] getMaxMinColorTrnasferValue_MB_Hybrid(int i, double d, double d2) {
    int[] iArr = new int[2];
    int roundHalfUp = roundHalfUp(d);
    int i2 = 252;
    int i3 = 138;
    int i4 = 204;
    int i5 = IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT;
    if (i == 0) {
      switch (roundHalfUp) {
        case 0:
          i2 = 255;
          i3 = i2;
          break;
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
          i2 = 203;
          i3 = 204;
          break;
        case 6:
          i2 = 234;
          i3 = 169;
          break;
        case 7:
          i2 = 240;
          i3 = 161;
          break;
        case 8:
          i2 = 246;
          i3 = 152;
          break;
        case 9:
          i2 = 250;
          i3 = 144;
          break;
        case 10:
          break;
        default:
          i2 = 0;
          i3 = i2;
          break;
      }
    } else if (i == 1) {
      switch (roundHalfUp) {
        case 1:
          i2 = 205;
          i3 = 204;
          break;
        case 2:
          i2 = 206;
          i3 = 204;
          break;
        case 3:
          i2 = 207;
          i3 = 204;
          break;
        case 4:
          i2 = 209;
          i3 = 204;
          break;
        case 5:
          i2 = 210;
          i3 = 204;
          break;
      }
    } else if (i == 2) {
      switch (roundHalfUp) {
        case 0:
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
          i4 = 255;
          break;
        case 1:
          i5 = FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__SET_MTE_POLICY;
          break;
        case 2:
          i5 = 222;
          break;
        case 3:
          i5 = 228;
          break;
        case 4:
          i5 = 235;
          break;
        case 5:
          i5 = FrameworkStatsLog.f434xe7bba16c;
          break;
        default:
          i4 = 0;
          i5 = 0;
          break;
      }
      i3 = i4;
      i2 = i5;
    } else {
      i2 = -1;
      i3 = i2;
    }
    iArr[0] = i2;
    iArr[1] = i3;
    return iArr;
  }

  /* JADX WARN: Removed duplicated region for block: B:12:0x0026  */
  /* JADX WARN: Removed duplicated region for block: B:5:0x0016  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final int[] getMaxMinColorTrnasferValue_YR_Hybrid(int i, double d, double d2) {
    int[] iArr = new int[2];
    int roundHalfUp = roundHalfUp(d);
    int i2 = FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__PLATFORM_PROVISIONING_ERROR;
    int i3 = 204;
    int i4 = IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT;
    if (i == 0) {
      switch (roundHalfUp) {
        case 0:
          i2 = 255;
          i3 = i2;
          break;
        case 1:
          break;
        case 2:
          i2 = FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__SET_ORGANIZATION_ID;
          break;
        case 3:
          i2 = 181;
          break;
        case 4:
          i2 = 173;
          break;
        case 5:
          i2 = FrameworkStatsLog.f636xd07885aa;
          break;
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
          i2 = 254;
          i3 = i2;
          break;
        default:
          i2 = 0;
          i3 = i2;
          break;
      }
    } else if (i == 1) {
      switch (roundHalfUp) {
        case 0:
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
          i3 = 255;
          break;
        case 1:
          i4 = 192;
          break;
        case 2:
          i4 = 186;
          break;
        case 3:
          i4 = FrameworkStatsLog.f633xa546c0ca;
          break;
        case 4:
          i4 = FrameworkStatsLog.f646xd1d8c3fe;
          break;
        case 5:
          i4 = 162;
          break;
        default:
          i3 = 0;
          i4 = 0;
          break;
      }
      i2 = i4;
    } else if (i == 2) {
      switch (roundHalfUp) {
        case 1:
          i2 = FrameworkStatsLog.f690xa4f1b8b3;
          break;
        case 2:
          i2 = 197;
          break;
        case 3:
          i2 = 195;
          break;
        case 5:
          i2 = 193;
          break;
      }
    } else {
      i2 = -1;
      i3 = i2;
    }
    iArr[0] = i2;
    iArr[1] = i3;
    return iArr;
  }

  /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
  jadx.core.utils.exceptions.JadxRuntimeException: Failed to find switch 'out' block (already processed)
  	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.calcSwitchOut(SwitchRegionMaker.java:202)
  	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:61)
  	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:115)
  	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
  	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:94)
  	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
  	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
  	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:100)
  	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
  	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
  	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:100)
  	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
  	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
  	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:49)
  	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
  */
  /* JADX WARN: Removed duplicated region for block: B:10:0x0047 A[PHI: r1 r3
  0x0047: PHI (r1v8 int) = (r1v0 int), (r1v2 int), (r1v3 int), (r1v4 int), (r1v5 int), (r1v6 int), (r1v0 int), (r1v0 int) binds: [B:21:0x0035, B:27:0x0043, B:26:0x0041, B:25:0x003e, B:24:0x003b, B:23:0x0039, B:17:0x002b, B:3:0x0017] A[DONT_GENERATE, DONT_INLINE]
  0x0047: PHI (r3v3 int) = (r3v0 int), (r3v0 int), (r3v0 int), (r3v0 int), (r3v0 int), (r3v1 int), (r3v0 int), (r3v0 int) binds: [B:21:0x0035, B:27:0x0043, B:26:0x0041, B:25:0x003e, B:24:0x003b, B:23:0x0039, B:17:0x002b, B:3:0x0017] A[DONT_GENERATE, DONT_INLINE]] */
  /* JADX WARN: Removed duplicated region for block: B:4:0x001a  */
  /* JADX WARN: Removed duplicated region for block: B:6:0x001c  */
  /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
  /* JADX WARN: Removed duplicated region for block: B:9:0x0023  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final int[] getMaxMinColorTrnasferValue_YG_Hybrid(int r8, double r9, double r11) {
    /*
        r7 = this;
        r11 = 2
        int[] r12 = new int[r11]
        int r7 = r7.roundHalfUp(r9)
        r9 = 216(0xd8, float:3.03E-43)
        r10 = 213(0xd5, float:2.98E-43)
        r0 = 1
        r1 = 211(0xd3, float:2.96E-43)
        r2 = 207(0xcf, float:2.9E-43)
        r3 = 204(0xcc, float:2.86E-43)
        r4 = 255(0xff, float:3.57E-43)
        r5 = 0
        if (r8 != 0) goto L25
        switch(r7) {
            case 0: goto L23;
            case 1: goto L21;
            case 2: goto L1e;
            case 3: goto L47;
            case 4: goto L1c;
            case 5: goto L4b;
            case 6: goto L23;
            case 7: goto L23;
            case 8: goto L23;
            case 9: goto L23;
            case 10: goto L23;
            default: goto L1a;
        }
    L1a:
        r9 = r5
        goto L4a
    L1c:
        r9 = r10
        goto L4b
    L1e:
        r9 = 209(0xd1, float:2.93E-43)
        goto L4b
    L21:
        r9 = r2
        goto L4b
    L23:
        r9 = r4
        goto L4a
    L25:
        r6 = 208(0xd0, float:2.91E-43)
        if (r8 != r0) goto L33
        r8 = 254(0xfe, float:3.56E-43)
        switch(r7) {
            case 0: goto L23;
            case 1: goto L21;
            case 2: goto L31;
            case 3: goto L47;
            case 4: goto L1c;
            case 5: goto L4b;
            case 6: goto L2f;
            case 7: goto L2f;
            case 8: goto L2f;
            case 9: goto L2f;
            case 10: goto L2f;
            default: goto L2e;
        }
    L2e:
        goto L1a
    L2f:
        r9 = r8
        goto L4a
    L31:
        r9 = r6
        goto L4b
    L33:
        if (r8 != r11) goto L49
        switch(r7) {
            case 0: goto L45;
            case 1: goto L43;
            case 2: goto L41;
            case 3: goto L3e;
            case 4: goto L47;
            case 5: goto L3b;
            case 6: goto L45;
            case 7: goto L45;
            case 8: goto L45;
            case 9: goto L45;
            case 10: goto L45;
            default: goto L38;
        }
    L38:
        r1 = r5
    L39:
        r3 = r1
        goto L47
    L3b:
        r1 = 212(0xd4, float:2.97E-43)
        goto L47
    L3e:
        r1 = 210(0xd2, float:2.94E-43)
        goto L47
    L41:
        r1 = r6
        goto L47
    L43:
        r1 = r2
        goto L47
    L45:
        r1 = r4
        goto L39
    L47:
        r9 = r1
        goto L4b
    L49:
        r9 = -1
    L4a:
        r3 = r9
    L4b:
        r12[r5] = r9
        r12[r0] = r3
        return r12
    */
    throw new UnsupportedOperationException(
        "Method not decompiled:"
            + " com.android.server.accessibility.ColorTransferTable.getMaxMinColorTrnasferValue_YG_Hybrid(int,"
            + " double, double):int[]");
  }

  public final int[] getMaxMinColorTrnasferValue_YB_Hybrid(int i, double d, double d2) {
    int i2;
    int i3 = 2;
    int[] iArr = new int[2];
    int roundHalfUp = roundHalfUp(d);
    if (i == 0) {
      switch (roundHalfUp) {
        case 0:
        case 1:
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
        default:
          i3 = 0;
          break;
        case 2:
        case 3:
          i3 = 1;
          break;
        case 4:
        case 5:
          break;
      }
      i2 = 0;
    } else if (i == 1 || i == 2) {
      i2 = 0;
      i3 = 0;
    } else {
      i3 = -1;
      i2 = -1;
    }
    iArr[0] = i3;
    iArr[1] = i2;
    return iArr;
  }

  public final int[] getMaxMinColorTrnasferValue_CR_Hybrid(int i, double d, double d2) {
    int i2;
    int[] iArr = new int[2];
    int roundHalfUp = roundHalfUp(d);
    if (i == 0) {
      switch (roundHalfUp) {
        case 0:
        case 1:
        case 2:
        case 3:
        case 4:
        case 5:
        default:
          i2 = 0;
          break;
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
          i2 = 1;
          break;
      }
    } else {
      if (i != 1 && i != 2) {
        i2 = -1;
      }
      i2 = 0;
    }
    iArr[0] = i2;
    iArr[1] = i2;
    return iArr;
  }

  /* JADX WARN: Removed duplicated region for block: B:11:0x0022  */
  /* JADX WARN: Removed duplicated region for block: B:5:0x0016  */
  /* JADX WARN: Removed duplicated region for block: B:8:0x001a A[FALL_THROUGH, PHI: r0 r4
  0x001a: PHI (r0v7 int) = (r0v0 int), (r0v0 int), (r0v9 int) binds: [B:30:0x0046, B:4:0x0013, B:7:0x0018] A[DONT_GENERATE, DONT_INLINE]
  0x001a: PHI (r4v3 int) = (r4v1 int), (r4v4 int), (r4v4 int) binds: [B:30:0x0046, B:4:0x0013, B:7:0x0018] A[DONT_GENERATE, DONT_INLINE]] */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final int[] getMaxMinColorTrnasferValue_CG_Hybrid(int i, double d, double d2) {
    int i2;
    int[] iArr = new int[2];
    int roundHalfUp = roundHalfUp(d);
    int i3 = 211;
    int i4 = 204;
    int i5 = IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT;
    if (i == 0) {
      i2 = 230;
      switch (roundHalfUp) {
        case 0:
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
          i3 = 255;
          i4 = i3;
          break;
        case 1:
          break;
        case 2:
          i3 = FrameworkStatsLog.f653x532b6133;
          break;
        case 3:
          i3 = 225;
          break;
        case 5:
          i4 = 207;
        case 4:
          i3 = i2;
          break;
        default:
          i3 = 0;
          i4 = i3;
          break;
      }
    } else if (i == 1) {
      switch (roundHalfUp) {
        case 0:
          i4 = 255;
          break;
        case 1:
          i5 = FrameworkStatsLog.DEVICE_POLICY_EVENT__EVENT_ID__SET_MTE_POLICY;
          break;
        case 2:
          i5 = 224;
          break;
        case 3:
          i5 = 232;
          break;
        case 4:
          i5 = 242;
          break;
        case 5:
          i5 = 253;
          break;
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
          i4 = 254;
          i5 = i4;
          break;
        default:
          i4 = 0;
          i5 = i4;
          break;
      }
      i3 = i5;
    } else if (i == 2) {
      i2 = 210;
      switch (roundHalfUp) {
        case 1:
          i3 = 208;
          break;
      }
    } else {
      i3 = -1;
      i4 = i3;
    }
    iArr[0] = i3;
    iArr[1] = i4;
    return iArr;
  }

  /* JADX WARN: Removed duplicated region for block: B:4:0x0016  */
  /* JADX WARN: Removed duplicated region for block: B:6:0x0018  */
  /* JADX WARN: Removed duplicated region for block: B:7:0x001a  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final int[] getMaxMinColorTrnasferValue_CB_Hybrid(int i, double d, double d2) {
    int[] iArr = new int[2];
    int roundHalfUp = roundHalfUp(d);
    int i2 = 203;
    int i3 = 204;
    int i4 = IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT;
    if (i == 0) {
      switch (roundHalfUp) {
        case 0:
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
          i2 = 255;
          i3 = i2;
          break;
        case 1:
          break;
        case 2:
        case 3:
        case 4:
        case 5:
          i2 = 202;
          break;
        default:
          i2 = 0;
          i3 = i2;
          break;
      }
    } else if (i == 1) {
      switch (roundHalfUp) {
        case 3:
        case 4:
          i2 = 201;
          break;
        case 5:
          i2 = 200;
          break;
      }
    } else if (i == 2) {
      switch (roundHalfUp) {
        case 0:
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
          i3 = 255;
          break;
        case 1:
          i4 = 205;
          break;
        case 2:
        case 3:
        case 4:
        case 5:
          i4 = 206;
          break;
        default:
          i3 = 0;
          i4 = 0;
          break;
      }
      i2 = i4;
    } else {
      i2 = -1;
      i3 = i2;
    }
    iArr[0] = i2;
    iArr[1] = i3;
    return iArr;
  }

  /*  JADX ERROR: JadxRuntimeException in pass: RegionMakerVisitor
  jadx.core.utils.exceptions.JadxRuntimeException: Failed to find switch 'out' block (already processed)
  	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.calcSwitchOut(SwitchRegionMaker.java:202)
  	at jadx.core.dex.visitors.regions.maker.SwitchRegionMaker.process(SwitchRegionMaker.java:61)
  	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:115)
  	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
  	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:94)
  	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
  	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
  	at jadx.core.dex.visitors.regions.maker.IfRegionMaker.process(IfRegionMaker.java:100)
  	at jadx.core.dex.visitors.regions.maker.RegionMaker.traverse(RegionMaker.java:109)
  	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeRegion(RegionMaker.java:69)
  	at jadx.core.dex.visitors.regions.maker.RegionMaker.makeMthRegion(RegionMaker.java:49)
  	at jadx.core.dex.visitors.regions.RegionMakerVisitor.visit(RegionMakerVisitor.java:25)
  */
  /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
  public final int[] getMaxMinColorTrnasferValue_BR_Hybrid(int r4, double r5, double r7) {
    /*
        r3 = this;
        r7 = 2
        int[] r8 = new int[r7]
        int r3 = r3.roundHalfUp(r5)
        r5 = 9
        r6 = 1
        r0 = 0
        if (r4 != 0) goto L22
        switch(r3) {
            case 0: goto L10;
            case 1: goto L3a;
            case 2: goto L1f;
            case 3: goto L1c;
            case 4: goto L19;
            case 5: goto L16;
            case 6: goto L14;
            case 7: goto L14;
            case 8: goto L14;
            case 9: goto L14;
            case 10: goto L14;
            default: goto L10;
        }
    L10:
        r3 = r0
    L11:
        r5 = r3
        goto L61
    L14:
        r3 = r6
        goto L11
    L16:
        r5 = 39
        goto L3a
    L19:
        r5 = 30
        goto L3a
    L1c:
        r5 = 22
        goto L3a
    L1f:
        r5 = 15
        goto L3a
    L22:
        r1 = 41
        r2 = 32
        if (r4 != r6) goto L3c
        switch(r3) {
            case 0: goto L2b;
            case 1: goto L37;
            case 2: goto L34;
            case 3: goto L31;
            case 4: goto L2f;
            case 5: goto L2d;
            case 6: goto L2b;
            case 7: goto L2b;
            case 8: goto L2b;
            case 9: goto L2b;
            case 10: goto L2b;
            default: goto L2b;
        }
    L2b:
        r5 = r0
        goto L3a
    L2d:
        r5 = r1
        goto L3a
    L2f:
        r5 = r2
        goto L3a
    L31:
        r3 = 24
        goto L39
    L34:
        r3 = 17
        goto L39
    L37:
        r3 = 11
    L39:
        r5 = r3
    L3a:
        r3 = r0
        goto L61
    L3c:
        if (r4 != r7) goto L5f
        switch(r3) {
            case 0: goto L10;
            case 1: goto L5d;
            case 2: goto L5b;
            case 3: goto L58;
            case 4: goto L3a;
            case 5: goto L55;
            case 6: goto L52;
            case 7: goto L4f;
            case 8: goto L4b;
            case 9: goto L47;
            case 10: goto L42;
            default: goto L41;
        }
    L41:
        goto L10
    L42:
        r3 = 47
        r5 = 118(0x76, float:1.65E-43)
        goto L61
    L47:
        r5 = 102(0x66, float:1.43E-43)
        r3 = r1
        goto L61
    L4b:
        r5 = 83
        r3 = r2
        goto L61
    L4f:
        r3 = r0
        r5 = r1
        goto L61
    L52:
        r3 = r0
        r5 = r2
        goto L61
    L55:
        r5 = 10
        goto L3a
    L58:
        r5 = 8
        goto L3a
    L5b:
        r5 = 6
        goto L3a
    L5d:
        r5 = 4
        goto L3a
    L5f:
        r5 = -1
        r3 = r5
    L61:
        r8[r0] = r5
        r8[r6] = r3
        return r8
    */
    throw new UnsupportedOperationException(
        "Method not decompiled:"
            + " com.android.server.accessibility.ColorTransferTable.getMaxMinColorTrnasferValue_BR_Hybrid(int,"
            + " double, double):int[]");
  }

  public final int[] getMaxMinColorTrnasferValue_BG_Hybrid(int i, double d, double d2) {
    int i2;
    int i3;
    int i4;
    int[] iArr = new int[2];
    int roundHalfUp = roundHalfUp(d);
    if (i != 0) {
      if (i == 1) {
        switch (roundHalfUp) {
          case 6:
          case 7:
          case 8:
          case 9:
          case 10:
            i2 = 1;
            break;
        }
      } else {
        if (i == 2) {
          switch (roundHalfUp) {
            case 0:
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            default:
              i4 = 0;
              break;
            case 6:
              i3 = 32;
              i4 = i3;
              break;
            case 7:
              i3 = 41;
              i4 = i3;
              break;
            case 8:
              i3 = 51;
              i4 = i3;
              break;
            case 9:
              i3 = 61;
              i4 = i3;
              break;
            case 10:
              i3 = 71;
              i4 = i3;
              break;
          }
          i2 = 0;
          iArr[0] = i4;
          iArr[1] = i2;
          return iArr;
        }
        i2 = -1;
      }
      i4 = i2;
      iArr[0] = i4;
      iArr[1] = i2;
      return iArr;
    }
    i2 = 0;
    i4 = i2;
    iArr[0] = i4;
    iArr[1] = i2;
    return iArr;
  }

  /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
  /* JADX WARN: Removed duplicated region for block: B:14:0x0023  */
  /* JADX WARN: Removed duplicated region for block: B:5:0x0018  */
  /*
      Code decompiled incorrectly, please refer to instructions dump.
  */
  public final int[] getMaxMinColorTrnasferValue_BB_Hybrid(int i, double d, double d2) {
    int[] iArr = new int[2];
    int roundHalfUp = roundHalfUp(d);
    int i2 = 204;
    int i3 = IDnsResolverUnsolicitedEventListener.DNS_HEALTH_RESULT_TIMEOUT;
    int i4 = FrameworkStatsLog.APP_FREEZE_CHANGED;
    if (i == 0) {
      switch (roundHalfUp) {
        case 0:
          i2 = 255;
          break;
        case 1:
          i3 = 203;
          break;
        case 2:
        case 3:
          i3 = 202;
          break;
        case 4:
        case 5:
          i3 = 201;
          break;
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
          i2 = 254;
          i3 = i2;
          break;
        default:
          i2 = 0;
          i3 = i2;
          break;
      }
    } else if (i == 1) {
      switch (roundHalfUp) {
        case 0:
          i2 = 255;
          i4 = i2;
          break;
        case 1:
          i4 = i2;
          break;
        case 2:
        case 3:
          i4 = 205;
          break;
        case 4:
        case 5:
          i4 = 206;
          break;
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
          i2 = 254;
          break;
        default:
          i2 = 0;
          i4 = i2;
          break;
      }
      i3 = i4;
    } else if (i == 2) {
      switch (roundHalfUp) {
        case 1:
          i3 = 218;
          break;
        case 2:
          i3 = 225;
          break;
        case 3:
          i3 = 231;
          break;
        case 4:
          i3 = 237;
          break;
        case 5:
          i3 = FrameworkStatsLog.NOTIFICATION_REPORTED;
          break;
      }
    } else {
      i2 = -1;
      i3 = i2;
    }
    iArr[0] = i3;
    iArr[1] = i2;
    return iArr;
  }

  public double[] getPredefinedValueForEachType(int i, int i2) {
    double[] dArr = new double[2];
    int i3 = i - 1;
    if (i3 == 0) {
      dArr[0] = Protan_severity[i2];
      dArr[1] = Protan_userParameter[i2];
    } else if (i3 == 1) {
      dArr[0] = Deutan_severity[i2];
      dArr[1] = Deutan_userParameter[i2];
    } else {
      if (i3 != 2) {
        return null;
      }
      dArr[0] = Tritan_severity[i2];
      dArr[1] = Tritan_userParameter[i2];
    }
    return dArr;
  }

  public final int roundHalfUp(double d) {
    return (int) Math.round(d * 10.0d);
  }
}
