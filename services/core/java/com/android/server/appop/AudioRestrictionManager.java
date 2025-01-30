package com.android.server.appop;

import android.app.AppOpsManager;
import android.media.AudioAttributes;
import android.util.ArraySet;
import android.util.Slog;
import android.util.SparseArray;
import android.util.SparseBooleanArray;
import java.io.PrintWriter;

/* loaded from: classes.dex */
public class AudioRestrictionManager {
  public static final SparseArray CAMERA_AUDIO_RESTRICTIONS;
  public final SparseArray mZenModeAudioRestrictions = new SparseArray();
  public int mCameraAudioRestriction = 0;

  public static String cameraRestrictionModeToName(int i) {
    return i != 0
        ? i != 1 ? i != 3 ? "Unknown" : "MuteVibrationAndSound" : "MuteVibration"
        : "None";
  }

  static {
    SparseBooleanArray sparseBooleanArray = new SparseBooleanArray();
    SparseBooleanArray sparseBooleanArray2 = new SparseBooleanArray();
    for (int i : AudioAttributes.SDK_USAGES) {
      int i2 = AudioAttributes.SUPPRESSIBLE_USAGES.get(i);
      if (i2 == 1 || i2 == 2 || i2 == 4) {
        sparseBooleanArray.append(i, true);
        sparseBooleanArray2.append(i, true);
      } else if (i2 != 5 && i2 != 6 && i2 != 3) {
        Slog.e("AudioRestriction", "Unknown audio suppression behavior" + i2);
      }
    }
    SparseArray sparseArray = new SparseArray();
    CAMERA_AUDIO_RESTRICTIONS = sparseArray;
    sparseArray.append(28, sparseBooleanArray);
    sparseArray.append(3, sparseBooleanArray2);
  }

  public final class Restriction {
    public static final ArraySet NO_EXCEPTIONS = new ArraySet();
    public ArraySet exceptionPackages;
    public int mode;

    public Restriction() {
      this.exceptionPackages = NO_EXCEPTIONS;
    }
  }

  public int checkAudioOperation(int i, int i2, int i3, String str) {
    SparseBooleanArray sparseBooleanArray;
    synchronized (this) {
      int i4 = this.mCameraAudioRestriction;
      if (i4 != 0
          && ((i == 3 || (i == 28 && i4 == 3))
              && (sparseBooleanArray = (SparseBooleanArray) CAMERA_AUDIO_RESTRICTIONS.get(i))
                  != null
              && sparseBooleanArray.get(i2))) {
        return 1;
      }
      int checkZenModeRestrictionLocked = checkZenModeRestrictionLocked(i, i2, i3, str);
      if (checkZenModeRestrictionLocked != 0) {
        return checkZenModeRestrictionLocked;
      }
      return 0;
    }
  }

  public final int checkZenModeRestrictionLocked(int i, int i2, int i3, String str) {
    Restriction restriction;
    SparseArray sparseArray = (SparseArray) this.mZenModeAudioRestrictions.get(i);
    if (sparseArray == null
        || (restriction = (Restriction) sparseArray.get(i2)) == null
        || restriction.exceptionPackages.contains(str)) {
      return 0;
    }
    return restriction.mode;
  }

  public void setZenModeAudioRestriction(int i, int i2, int i3, int i4, String[] strArr) {
    synchronized (this) {
      SparseArray sparseArray = (SparseArray) this.mZenModeAudioRestrictions.get(i);
      if (sparseArray == null) {
        sparseArray = new SparseArray();
        this.mZenModeAudioRestrictions.put(i, sparseArray);
      }
      sparseArray.remove(i2);
      if (i4 != 0) {
        Restriction restriction = new Restriction();
        restriction.mode = i4;
        if (strArr != null) {
          restriction.exceptionPackages = new ArraySet(strArr.length);
          for (String str : strArr) {
            if (str != null) {
              restriction.exceptionPackages.add(str.trim());
            }
          }
        }
        sparseArray.put(i2, restriction);
      }
    }
  }

  public void setCameraAudioRestriction(int i) {
    synchronized (this) {
      this.mCameraAudioRestriction = i;
    }
  }

  public boolean hasActiveRestrictions() {
    boolean z;
    synchronized (this) {
      z = this.mZenModeAudioRestrictions.size() > 0 || this.mCameraAudioRestriction != 0;
    }
    return z;
  }

  public boolean dump(PrintWriter printWriter) {
    boolean hasActiveRestrictions = hasActiveRestrictions();
    synchronized (this) {
      boolean z = false;
      for (int i = 0; i < this.mZenModeAudioRestrictions.size(); i++) {
        String opToName = AppOpsManager.opToName(this.mZenModeAudioRestrictions.keyAt(i));
        SparseArray sparseArray = (SparseArray) this.mZenModeAudioRestrictions.valueAt(i);
        for (int i2 = 0; i2 < sparseArray.size(); i2++) {
          if (!z) {
            printWriter.println("  Zen Mode Audio Restrictions:");
            z = true;
          }
          int keyAt = sparseArray.keyAt(i2);
          printWriter.print("    ");
          printWriter.print(opToName);
          printWriter.print(" usage=");
          printWriter.print(AudioAttributes.usageToString(keyAt));
          Restriction restriction = (Restriction) sparseArray.valueAt(i2);
          printWriter.print(": mode=");
          printWriter.println(AppOpsManager.modeToName(restriction.mode));
          if (!restriction.exceptionPackages.isEmpty()) {
            printWriter.println("      Exceptions:");
            for (int i3 = 0; i3 < restriction.exceptionPackages.size(); i3++) {
              printWriter.print("        ");
              printWriter.println((String) restriction.exceptionPackages.valueAt(i3));
            }
          }
        }
      }
      if (this.mCameraAudioRestriction != 0) {
        printWriter.println(
            "  Camera Audio Restriction Mode: "
                + cameraRestrictionModeToName(this.mCameraAudioRestriction));
      }
    }
    return hasActiveRestrictions;
  }
}
