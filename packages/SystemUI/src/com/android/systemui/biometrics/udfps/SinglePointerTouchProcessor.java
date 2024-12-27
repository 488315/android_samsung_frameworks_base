package com.android.systemui.biometrics.udfps;

import android.graphics.PointF;
import android.util.RotationUtils;
import android.view.MotionEvent;
import com.android.systemui.biometrics.shared.model.UdfpsOverlayParams;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;
import kotlin.collections.CollectionsKt__IterablesKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class SinglePointerTouchProcessor {
    public final OverlapDetector overlapDetector;

    public SinglePointerTouchProcessor(OverlapDetector overlapDetector) {
        this.overlapDetector = overlapDetector;
    }

    public static final PreprocessedTouch processTouch$preprocess(MotionEvent motionEvent, int i, UdfpsOverlayParams udfpsOverlayParams, SinglePointerTouchProcessor singlePointerTouchProcessor) {
        int pointerCount = motionEvent.getPointerCount();
        ArrayList arrayList = new ArrayList(pointerCount);
        for (int i2 = 0; i2 < pointerCount; i2++) {
            Set set = SinglePointerTouchProcessorKt.SUPPORTED_ROTATIONS;
            PointF pointF = new PointF(motionEvent.getRawX(i2), motionEvent.getRawY(i2));
            int i3 = udfpsOverlayParams.rotation;
            Set set2 = SinglePointerTouchProcessorKt.SUPPORTED_ROTATIONS;
            if (set2.contains(Integer.valueOf(i3))) {
                RotationUtils.rotatePointF(pointF, RotationUtils.deltaRotation(i3, 0), udfpsOverlayParams.logicalDisplayWidth, udfpsOverlayParams.logicalDisplayHeight);
            }
            float f = pointF.x;
            float f2 = udfpsOverlayParams.scaleFactor;
            float f3 = f / f2;
            float f4 = pointF.y / f2;
            float touchMinor = motionEvent.getTouchMinor(i2) / f2;
            float touchMajor = motionEvent.getTouchMajor(i2) / f2;
            float orientation = motionEvent.getOrientation(i2);
            if (set2.contains(Integer.valueOf(udfpsOverlayParams.rotation))) {
                double d = ((orientation % 3.141592653589793d) + 1.5707963267948966d) % 3.141592653589793d;
                if (d >= 1.5707963267948966d) {
                    d -= 3.141592653589793d;
                }
                orientation = (float) d;
            }
            arrayList.add(new NormalizedTouchData(motionEvent.getPointerId(i2), f3, f4, touchMinor, touchMajor, orientation, motionEvent.getEventTime(), motionEvent.getDownTime()));
        }
        ArrayList arrayList2 = new ArrayList();
        Iterator it = arrayList.iterator();
        while (it.hasNext()) {
            Object next = it.next();
            if (singlePointerTouchProcessor.overlapDetector.isGoodOverlap((NormalizedTouchData) next, udfpsOverlayParams.nativeSensorBounds, udfpsOverlayParams.nativeOverlayBounds)) {
                arrayList2.add(next);
            }
        }
        ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList2, 10));
        Iterator it2 = arrayList2.iterator();
        while (it2.hasNext()) {
            arrayList3.add(Integer.valueOf(((NormalizedTouchData) it2.next()).pointerId));
        }
        return new PreprocessedTouch(arrayList, i, arrayList3);
    }
}
