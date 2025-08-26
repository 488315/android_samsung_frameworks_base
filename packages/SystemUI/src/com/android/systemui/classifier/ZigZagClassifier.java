package com.android.systemui.classifier;

import android.graphics.Point;
import android.view.MotionEvent;
import androidx.appcompat.app.AlertController$$ExternalSyntheticOutline0;
import androidx.preference.PreferenceGroupAdapter$$ExternalSyntheticOutline0;
import com.android.systemui.classifier.FalsingClassifier;
import com.android.systemui.util.DeviceConfigProxy;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/* loaded from: classes.dex */
public class ZigZagClassifier extends FalsingClassifier {
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
        double dCos = Math.cos(d);
        double dSin = Math.sin(d);
        MotionEvent motionEvent = (MotionEvent) list.get(0);
        float x = motionEvent.getX();
        float y = motionEvent.getY();
        Iterator it = list.iterator();
        while (it.hasNext()) {
            MotionEvent motionEvent2 = (MotionEvent) it.next();
            double x2 = motionEvent2.getX() - x;
            double y2 = motionEvent2.getY() - y;
            double d2 = dCos;
            arrayList.add(new Point((int) ((dSin * y2) + (dCos * x2) + x), (int) ((y2 * d2) + ((-dSin) * x2) + y)));
            motionEvent = motionEvent;
            dCos = d2;
        }
        MotionEvent motionEvent3 = motionEvent;
        MotionEvent motionEvent4 = (MotionEvent) PreferenceGroupAdapter$$ExternalSyntheticOutline0.m(1, list);
        Point point = (Point) arrayList.get(0);
        Point point2 = (Point) AlertController$$ExternalSyntheticOutline0.m(1, arrayList);
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
        List<Point> listRotateMotionEvents;
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
            listRotateMotionEvents = rotateMotionEvents(falsingDataProvider.getRecentMotionEvents(), atan2LastPoint);
        } else {
            boolean z2 = BrightLineFalsingManager.DEBUG;
            listRotateMotionEvents = rotateMotionEvents(falsingDataProvider.getRecentMotionEvents(), -(1.5707963267948966d - getAtan2LastPoint()));
        }
        boolean z3 = true;
        float fAbs = Math.abs(((Point) listRotateMotionEvents.get(0)).x - ((Point) PreferenceGroupAdapter$$ExternalSyntheticOutline0.m(1, listRotateMotionEvents)).x);
        float fAbs2 = Math.abs(((Point) listRotateMotionEvents.get(0)).y - ((Point) PreferenceGroupAdapter$$ExternalSyntheticOutline0.m(1, listRotateMotionEvents)).y);
        float fAbs3 = 0.0f;
        float fAbs4 = 0.0f;
        float f3 = 0.0f;
        float f4 = 0.0f;
        for (Point point : listRotateMotionEvents) {
            if (z3) {
                f3 = point.x;
                f4 = point.y;
                z3 = false;
            } else {
                fAbs3 += Math.abs(point.x - f3);
                fAbs4 += Math.abs(point.y - f4);
                f3 = point.x;
                f4 = point.y;
                boolean z4 = BrightLineFalsingManager.DEBUG;
            }
        }
        float f5 = fAbs3 - fAbs;
        float f6 = fAbs4 - fAbs2;
        float f7 = falsingDataProvider.mXdpi;
        float f8 = fAbs / f7;
        float f9 = falsingDataProvider.mYdpi;
        float f10 = fAbs2 / f9;
        float fSqrt = (float) Math.sqrt((f10 * f10) + (f8 * f8));
        if (fAbs > fAbs2) {
            f = this.mMaxXPrimaryDeviance * fSqrt * f7;
            f2 = this.mMaxYSecondaryDeviance;
        } else {
            f = this.mMaxXSecondaryDeviance * fSqrt * f7;
            f2 = this.mMaxYPrimaryDeviance;
        }
        float f11 = f2 * fSqrt * f9;
        this.mLastDevianceY = f6;
        this.mLastMaxXDeviance = f;
        this.mLastMaxYDeviance = f11;
        boolean z5 = BrightLineFalsingManager.DEBUG;
        return (f5 > f || f6 > f11) ? falsed(0.5d, String.format(null, "{devianceX=%f, maxDevianceX=%s, devianceY=%s, maxDevianceY=%s}", Float.valueOf(f5), Float.valueOf(this.mLastMaxXDeviance), Float.valueOf(this.mLastDevianceY), Float.valueOf(this.mLastMaxYDeviance))) : FalsingClassifier.Result.passed(0.5d);
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
