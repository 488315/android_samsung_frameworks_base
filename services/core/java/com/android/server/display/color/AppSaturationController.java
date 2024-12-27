package com.android.server.display.color;

import android.util.ArrayMap;
import android.util.SparseArray;

import com.android.server.accessibility.magnification.FullScreenMagnificationGestureHandler;
import com.android.server.wm.ActivityRecord$$ExternalSyntheticLambda22;

import java.io.PrintWriter;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class AppSaturationController {
    static final float[] TRANSLATION_VECTOR = {
        FullScreenMagnificationGestureHandler.MAX_SCALE,
        FullScreenMagnificationGestureHandler.MAX_SCALE,
        FullScreenMagnificationGestureHandler.MAX_SCALE
    };
    public final Object mLock = new Object();
    public final Map mAppsMap = new HashMap();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SaturationController {
        public final List mControllerRefs = new ArrayList();
        public final ArrayMap mSaturationLevels = new ArrayMap();
        public final float[] mTransformMatrix = new float[9];

        /* renamed from: -$$Nest$maddColorTransformController, reason: not valid java name */
        public static boolean m473$$Nest$maddColorTransformController(
                SaturationController saturationController, WeakReference weakReference) {
            Iterator it = ((ArrayList) saturationController.mControllerRefs).iterator();
            while (it.hasNext()) {
                if (((ColorDisplayService.ColorTransformController)
                                ((WeakReference) it.next()).get())
                        == null) {
                    it.remove();
                }
            }
            ((ArrayList) saturationController.mControllerRefs).add(weakReference);
            if (saturationController.mSaturationLevels.isEmpty()) {
                return false;
            }
            return saturationController.updateState();
        }

        /* renamed from: -$$Nest$mdump, reason: not valid java name */
        public static void m474$$Nest$mdump(
                SaturationController saturationController, PrintWriter printWriter) {
            printWriter.println(
                    "            mSaturationLevels: " + saturationController.mSaturationLevels);
            printWriter.println(
                    "            mControllerRefs count: "
                            + ((ArrayList) saturationController.mControllerRefs).size());
        }

        public final boolean updateState() {
            boolean z = false;
            int i = 100;
            for (int i2 = 0; i2 < this.mSaturationLevels.size(); i2++) {
                int intValue = ((Integer) this.mSaturationLevels.valueAt(i2)).intValue();
                if (intValue < i) {
                    i = intValue;
                }
            }
            float[] fArr = this.mTransformMatrix;
            AppSaturationController.computeGrayscaleTransformMatrix(i / 100.0f, fArr);
            Iterator it = ((ArrayList) this.mControllerRefs).iterator();
            while (it.hasNext()) {
                ColorDisplayService.ColorTransformController colorTransformController =
                        (ColorDisplayService.ColorTransformController)
                                ((WeakReference) it.next()).get();
                if (colorTransformController != null) {
                    ((ActivityRecord$$ExternalSyntheticLambda22) colorTransformController)
                            .applyAppSaturation(fArr, AppSaturationController.TRANSLATION_VECTOR);
                    z = true;
                } else {
                    it.remove();
                }
            }
            return z;
        }
    }

    public static void computeGrayscaleTransformMatrix(float f, float[] fArr) {
        float f2 = 1.0f - f;
        float[] fArr2 = {0.231f * f2, 0.715f * f2, f2 * 0.072f};
        fArr[0] = fArr2[0] + f;
        float f3 = fArr2[0];
        fArr[1] = f3;
        fArr[2] = f3;
        float f4 = fArr2[1];
        fArr[3] = f4;
        fArr[4] = f4 + f;
        fArr[5] = f4;
        float f5 = fArr2[2];
        fArr[6] = f5;
        fArr[7] = f5;
        fArr[8] = f5 + f;
    }

    public final SaturationController getSaturationControllerLocked(int i, String str) {
        SparseArray sparseArray;
        if (((HashMap) this.mAppsMap).get(str) != null) {
            sparseArray = (SparseArray) ((HashMap) this.mAppsMap).get(str);
        } else {
            SparseArray sparseArray2 = new SparseArray();
            ((HashMap) this.mAppsMap).put(str, sparseArray2);
            sparseArray = sparseArray2;
        }
        if (sparseArray.get(i) != null) {
            return (SaturationController) sparseArray.get(i);
        }
        SaturationController saturationController = new SaturationController();
        sparseArray.put(i, saturationController);
        return saturationController;
    }
}
