package com.android.systemui.classifier;

import android.graphics.Point;
import android.view.MotionEvent;
import androidx.appcompat.app.AlertController$$ExternalSyntheticOutline0;
import androidx.compose.runtime.PrioritySet$$ExternalSyntheticOutline0;
import com.android.systemui.classifier.FalsingClassifier;
import com.android.systemui.util.DeviceConfigProxy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class ZigZagClassifier extends FalsingClassifier {
    public float mLastDevianceY;
    public float mLastMaxXDeviance;
    public float mLastMaxYDeviance;
    public final float mMaxXPrimaryDeviance;
    public final float mMaxXSecondaryDeviance;
    public final float mMaxYPrimaryDeviance;
    public final float mMaxYSecondaryDeviance;

    public ZigZagClassifier(FalsingDataProvider falsingDataProvider, DeviceConfigProxy deviceConfigProxy) {
        super(falsingDataProvider);
        this.mMaxXPrimaryDeviance = deviceConfigProxy.getFloat("systemui", "brightline_falsing_zigzag_x_primary_deviance", 0.05f);
        this.mMaxYPrimaryDeviance = deviceConfigProxy.getFloat("systemui", "brightline_falsing_zigzag_y_primary_deviance", 0.15f);
        this.mMaxXSecondaryDeviance = deviceConfigProxy.getFloat("systemui", "brightline_falsing_zigzag_x_secondary_deviance", 0.4f);
        this.mMaxYSecondaryDeviance = deviceConfigProxy.getFloat("systemui", "brightline_falsing_zigzag_y_secondary_deviance", 0.3f);
    }

    public static List rotateMotionEvents(List list, double d) {
        ArrayList arrayList = new ArrayList();
        double cos = Math.cos(d);
        double sin = Math.sin(d);
        MotionEvent motionEvent = (MotionEvent) list.get(0);
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        for (Iterator it = list.iterator(); it.hasNext(); it = it) {
            MotionEvent motionEvent2 = (MotionEvent) it.next();
            double x2 = motionEvent2.getX() - x;
            double y2 = motionEvent2.getY() - y;
            arrayList.add(new Point((int) ((sin * y2) + (cos * x2) + x), (int) ((y2 * cos) + ((-sin) * x2) + y)));
            motionEvent = motionEvent;
        }
        MotionEvent motionEvent3 = motionEvent;
        MotionEvent motionEvent4 = (MotionEvent) PrioritySet$$ExternalSyntheticOutline0.m(1, list);
        Point point = (Point) arrayList.get(0);
        Point point2 = (Point) AlertController$$ExternalSyntheticOutline0.m(arrayList, 1);
        motionEvent3.getX();
        motionEvent3.getY();
        motionEvent4.getX();
        motionEvent4.getY();
        boolean z = BrightLineFalsingManager.DEBUG;
        int i = point.x;
        int i2 = point2.x;
        return arrayList;
    }

    @Override // com.android.systemui.classifier.FalsingClassifier
    public final FalsingClassifier.Result calculateFalsingResult(int i) {
        List<Point> rotateMotionEvents;
        float f;
        float f2;
        if (i == 10 || i == 18 || i == 11) {
            return FalsingClassifier.Result.passed(0.0d);
        }
        FalsingDataProvider falsingDataProvider = this.mDataProvider;
        if (falsingDataProvider.getRecentMotionEvents().size() < 3) {
            return FalsingClassifier.Result.passed(0.0d);
        }
        if (falsingDataProvider.isHorizontal()) {
            double atan2LastPoint = getAtan2LastPoint();
            boolean z = BrightLineFalsingManager.DEBUG;
            rotateMotionEvents = rotateMotionEvents(falsingDataProvider.getRecentMotionEvents(), atan2LastPoint);
        } else {
            boolean z2 = BrightLineFalsingManager.DEBUG;
            rotateMotionEvents = rotateMotionEvents(falsingDataProvider.getRecentMotionEvents(), -(1.5707963267948966d - getAtan2LastPoint()));
        }
        boolean z3 = true;
        float abs = Math.abs(((Point) rotateMotionEvents.get(0)).x - ((Point) PrioritySet$$ExternalSyntheticOutline0.m(1, rotateMotionEvents)).x);
        float abs2 = Math.abs(((Point) rotateMotionEvents.get(0)).y - ((Point) PrioritySet$$ExternalSyntheticOutline0.m(1, rotateMotionEvents)).y);
        float f3 = 0.0f;
        float f4 = 0.0f;
        float f5 = 0.0f;
        float f6 = 0.0f;
        for (Point point : rotateMotionEvents) {
            if (z3) {
                f5 = point.x;
                f6 = point.y;
                z3 = false;
            } else {
                f3 += Math.abs(point.x - f5);
                f4 += Math.abs(point.y - f6);
                f5 = point.x;
                f6 = point.y;
                boolean z4 = BrightLineFalsingManager.DEBUG;
            }
        }
        float f7 = f3 - abs;
        float f8 = f4 - abs2;
        float f9 = falsingDataProvider.mXdpi;
        float f10 = abs / f9;
        float f11 = falsingDataProvider.mYdpi;
        float f12 = abs2 / f11;
        float sqrt = (float) Math.sqrt((f12 * f12) + (f10 * f10));
        if (abs > abs2) {
            f = this.mMaxXPrimaryDeviance * sqrt * f9;
            f2 = this.mMaxYSecondaryDeviance;
        } else {
            f = this.mMaxXSecondaryDeviance * sqrt * f9;
            f2 = this.mMaxYPrimaryDeviance;
        }
        float f13 = f2 * sqrt * f11;
        this.mLastDevianceY = f8;
        this.mLastMaxXDeviance = f;
        this.mLastMaxYDeviance = f13;
        boolean z5 = BrightLineFalsingManager.DEBUG;
        return (f7 > f || f8 > f13) ? falsed(0.5d, String.format(null, "{devianceX=%f, maxDevianceX=%s, devianceY=%s, maxDevianceY=%s}", Float.valueOf(f7), Float.valueOf(this.mLastMaxXDeviance), Float.valueOf(this.mLastDevianceY), Float.valueOf(this.mLastMaxYDeviance))) : FalsingClassifier.Result.passed(0.5d);
    }

    public final float getAtan2LastPoint() {
        FalsingDataProvider falsingDataProvider = this.mDataProvider;
        falsingDataProvider.recalculateData();
        MotionEvent motionEvent = falsingDataProvider.mFirstRecentMotionEvent;
        falsingDataProvider.recalculateData();
        MotionEvent motionEvent2 = falsingDataProvider.mLastMotionEvent;
        float x = motionEvent.getX();
        return (float) Math.atan2(motionEvent2.getY() - motionEvent.getY(), motionEvent2.getX() - x);
    }
}
