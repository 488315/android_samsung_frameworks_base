package com.android.server.display.color;

import android.util.ArrayMap;
import android.util.SparseArray;
import com.android.internal.util.jobs.XmlUtils;
import com.android.server.display.DisplayPowerController2;
import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* loaded from: classes2.dex */
public class AppSaturationController {
  static final float[] TRANSLATION_VECTOR = {
    DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
    DisplayPowerController2.RATE_FROM_DOZE_TO_ON,
    DisplayPowerController2.RATE_FROM_DOZE_TO_ON
  };
  public final Object mLock = new Object();
  public final Map mAppsMap = new HashMap();

  public boolean addColorTransformController(String str, int i, WeakReference weakReference) {
    boolean addColorTransformController;
    synchronized (this.mLock) {
      addColorTransformController =
          getSaturationControllerLocked(str, i).addColorTransformController(weakReference);
    }
    return addColorTransformController;
  }

  public boolean setSaturationLevel(String str, String str2, int i, int i2) {
    boolean saturationLevel;
    synchronized (this.mLock) {
      saturationLevel = getSaturationControllerLocked(str2, i).setSaturationLevel(str, i2);
    }
    return saturationLevel;
  }

  public void dump(PrintWriter printWriter) {
    synchronized (this.mLock) {
      printWriter.println("App Saturation: ");
      if (this.mAppsMap.size() == 0) {
        printWriter.println("    No packages");
        return;
      }
      ArrayList<String> arrayList = new ArrayList(this.mAppsMap.keySet());
      Collections.sort(arrayList);
      for (String str : arrayList) {
        printWriter.println("    " + str + XmlUtils.STRING_ARRAY_SEPARATOR);
        SparseArray sparseArray = (SparseArray) this.mAppsMap.get(str);
        for (int i = 0; i < sparseArray.size(); i++) {
          printWriter.println("        " + sparseArray.keyAt(i) + XmlUtils.STRING_ARRAY_SEPARATOR);
          ((SaturationController) sparseArray.valueAt(i)).dump(printWriter);
        }
      }
    }
  }

  public final SaturationController getSaturationControllerLocked(String str, int i) {
    return getOrCreateSaturationControllerLocked(getOrCreateUserIdMapLocked(str), i);
  }

  public final SparseArray getOrCreateUserIdMapLocked(String str) {
    if (this.mAppsMap.get(str) != null) {
      return (SparseArray) this.mAppsMap.get(str);
    }
    SparseArray sparseArray = new SparseArray();
    this.mAppsMap.put(str, sparseArray);
    return sparseArray;
  }

  public final SaturationController getOrCreateSaturationControllerLocked(
      SparseArray sparseArray, int i) {
    if (sparseArray.get(i) != null) {
      return (SaturationController) sparseArray.get(i);
    }
    SaturationController saturationController = new SaturationController();
    sparseArray.put(i, saturationController);
    return saturationController;
  }

  public static void computeGrayscaleTransformMatrix(float f, float[] fArr) {
    float f2 = 1.0f - f;
    float f3 = 0.231f * f2;
    float[] fArr2 = {f3, 0.715f * f2, f2 * 0.072f};
    fArr[0] = f3 + f;
    float f4 = fArr2[0];
    fArr[1] = f4;
    fArr[2] = f4;
    float f5 = fArr2[1];
    fArr[3] = f5;
    fArr[4] = f5 + f;
    fArr[5] = f5;
    float f6 = fArr2[2];
    fArr[6] = f6;
    fArr[7] = f6;
    fArr[8] = f6 + f;
  }

  public class SaturationController {
    public final List mControllerRefs;
    public final ArrayMap mSaturationLevels;
    public float[] mTransformMatrix;

    public SaturationController() {
      this.mControllerRefs = new ArrayList();
      this.mSaturationLevels = new ArrayMap();
      this.mTransformMatrix = new float[9];
    }

    public final boolean setSaturationLevel(String str, int i) {
      if (i == 100) {
        this.mSaturationLevels.remove(str);
      } else {
        this.mSaturationLevels.put(str, Integer.valueOf(i));
      }
      if (this.mControllerRefs.isEmpty()) {
        return false;
      }
      return updateState();
    }

    public final boolean addColorTransformController(WeakReference weakReference) {
      clearExpiredReferences();
      this.mControllerRefs.add(weakReference);
      if (this.mSaturationLevels.isEmpty()) {
        return false;
      }
      return updateState();
    }

    public final int calculateSaturationLevel() {
      int i = 100;
      for (int i2 = 0; i2 < this.mSaturationLevels.size(); i2++) {
        int intValue = ((Integer) this.mSaturationLevels.valueAt(i2)).intValue();
        if (intValue < i) {
          i = intValue;
        }
      }
      return i;
    }

    public final boolean updateState() {
      AppSaturationController.computeGrayscaleTransformMatrix(
          calculateSaturationLevel() / 100.0f, this.mTransformMatrix);
      Iterator it = this.mControllerRefs.iterator();
      boolean z = false;
      while (it.hasNext()) {
        ColorDisplayService.ColorTransformController colorTransformController =
            (ColorDisplayService.ColorTransformController) ((WeakReference) it.next()).get();
        if (colorTransformController != null) {
          colorTransformController.applyAppSaturation(
              this.mTransformMatrix, AppSaturationController.TRANSLATION_VECTOR);
          z = true;
        } else {
          it.remove();
        }
      }
      return z;
    }

    public final void clearExpiredReferences() {
      Iterator it = this.mControllerRefs.iterator();
      while (it.hasNext()) {
        if (((ColorDisplayService.ColorTransformController) ((WeakReference) it.next()).get())
            == null) {
          it.remove();
        }
      }
    }

    public final void dump(PrintWriter printWriter) {
      printWriter.println("            mSaturationLevels: " + this.mSaturationLevels);
      printWriter.println("            mControllerRefs count: " + this.mControllerRefs.size());
    }
  }
}
