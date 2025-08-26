package com.android.systemui.biometrics.udfps;

import android.graphics.PointF;
import android.util.RotationUtils;
import android.view.MotionEvent;
import com.android.systemui.biometrics.shared.model.UdfpsOverlayParams;
import java.util.ArrayList;
import java.util.Set;
import kotlin.collections.CollectionsKt__IterablesKt;

/* loaded from: classes.dex */
public final class SinglePointerTouchProcessor {
    public final OverlapDetector overlapDetector;

    public SinglePointerTouchProcessor(OverlapDetector overlapDetector) {
        this.overlapDetector = overlapDetector;
    }

    public static final PreprocessedTouch processTouch$preprocess(MotionEvent motionEvent, int i, UdfpsOverlayParams udfpsOverlayParams, SinglePointerTouchProcessor singlePointerTouchProcessor) {
        int pointerCount = motionEvent.getPointerCount();
        ArrayList arrayList = new ArrayList(pointerCount);
        int i2 = 0;
        for (int i3 = 0; i3 < pointerCount; i3++) {
            Set set = SinglePointerTouchProcessorKt.SUPPORTED_ROTATIONS;
            PointF pointF = new PointF(motionEvent.getRawX(i3), motionEvent.getRawY(i3));
            int i4 = udfpsOverlayParams.rotation;
            Set set2 = SinglePointerTouchProcessorKt.SUPPORTED_ROTATIONS;
            if (set2.contains(Integer.valueOf(i4))) {
                RotationUtils.rotatePointF(pointF, RotationUtils.deltaRotation(i4, 0), udfpsOverlayParams.logicalDisplayWidth, udfpsOverlayParams.logicalDisplayHeight);
            }
            float f = pointF.x;
            float f2 = udfpsOverlayParams.scaleFactor;
            float f3 = f / f2;
            float f4 = pointF.y / f2;
            float touchMinor = motionEvent.getTouchMinor(i3) / f2;
            float touchMajor = motionEvent.getTouchMajor(i3) / f2;
            float orientation = motionEvent.getOrientation(i3);
            if (set2.contains(Integer.valueOf(udfpsOverlayParams.rotation))) {
                double d = ((orientation % 3.141592653589793d) + 1.5707963267948966d) % 3.141592653589793d;
                if (d >= 1.5707963267948966d) {
                    d -= 3.141592653589793d;
                }
                orientation = (float) d;
            }
            arrayList.add(new NormalizedTouchData(motionEvent.getPointerId(i3), f3, f4, touchMinor, touchMajor, orientation, motionEvent.getEventTime(), motionEvent.getDownTime()));
        }
        ArrayList arrayList2 = new ArrayList();
        int size = arrayList.size();
        int i5 = 0;
        while (i5 < size) {
            Object obj = arrayList.get(i5);
            i5++;
            if (singlePointerTouchProcessor.overlapDetector.isGoodOverlap((NormalizedTouchData) obj, udfpsOverlayParams.nativeSensorBounds, udfpsOverlayParams.nativeOverlayBounds)) {
                arrayList2.add(obj);
            }
        }
        ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList2, 10));
        int size2 = arrayList2.size();
        while (i2 < size2) {
            Object obj2 = arrayList2.get(i2);
            i2++;
            arrayList3.add(Integer.valueOf(((NormalizedTouchData) obj2).pointerId));
        }
        return new PreprocessedTouch(arrayList, i, arrayList3);
    }
}
