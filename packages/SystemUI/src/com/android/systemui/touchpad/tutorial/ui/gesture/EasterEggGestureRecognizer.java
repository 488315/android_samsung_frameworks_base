package com.android.systemui.touchpad.tutorial.ui.gesture;

import android.view.MotionEvent;
import com.android.systemui.touchpad.tutorial.ui.gesture.GestureState;
import com.android.systemui.touchpad.tutorial.ui.viewmodel.GestureRecognizerAdapter$gestureStateAsFlow$1$$ExternalSyntheticLambda0;
import java.util.ArrayList;
import java.util.List;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class EasterEggGestureRecognizer implements GestureRecognizer {
    public static final /* synthetic */ int $r8$clinit = 0;
    public int circleCount;
    public float cumulativeAngle;
    public Float lastAngle;
    public Function1 gestureStateChangedCallback = new EasterEggGestureRecognizer$$ExternalSyntheticLambda0(0);
    public Point last = new Point(0.0f, 0.0f);
    public final List points = new ArrayList();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Point {
        public final float x;
        public final float y;

        public Point(float f, float f2) {
            this.x = f;
            this.y = f2;
        }
    }

    static {
        new Companion(null);
    }

    public static double distanceBetween(Point point, Point point2) {
        return Math.sqrt(Math.pow(point.y - point2.y, 2.0d) + Math.pow(point.x - point2.x, 2.0d));
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        MotionEvent motionEvent = (MotionEvent) obj;
        if (motionEvent.getClassification() == 3) {
            int action = motionEvent.getAction();
            if (action == 0) {
                reset();
                this.last = new Point(motionEvent.getX(), motionEvent.getY());
                ((ArrayList) this.points).add(new Point(motionEvent.getX(), motionEvent.getY()));
                return;
            }
            if (action != 1) {
                if (action != 2) {
                    if (action != 3) {
                        return;
                    }
                    reset();
                    return;
                }
                Point point = new Point(motionEvent.getX(), motionEvent.getY());
                ((ArrayList) this.points).add(point);
                if (distanceBetween(this.last, point) > 10.0d) {
                    Point point2 = this.last;
                    float atan2 = (float) Math.atan2(point.y - point2.y, point.x - point2.x);
                    Float f = this.lastAngle;
                    if (f == null) {
                        this.lastAngle = Float.valueOf(atan2);
                        return;
                    }
                    float floatValue = atan2 - f.floatValue();
                    float f2 = this.cumulativeAngle;
                    double d = floatValue;
                    if (d > 3.141592653589793d) {
                        floatValue -= 6.2831855f;
                    } else if (d < -3.141592653589793d) {
                        floatValue += 6.2831855f;
                    }
                    this.cumulativeAngle = f2 + floatValue;
                    this.lastAngle = Float.valueOf(atan2);
                    this.last = point;
                    if (this.cumulativeAngle >= 6.283185307179586d) {
                        this.cumulativeAngle = 0.0f;
                        this.circleCount++;
                        return;
                    }
                    return;
                }
                return;
            }
            if (this.circleCount >= 3) {
                List list = this.points;
                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
                ArrayList arrayList2 = (ArrayList) list;
                int size = arrayList2.size();
                int i = 0;
                int i2 = 0;
                while (i2 < size) {
                    Object obj2 = arrayList2.get(i2);
                    i2++;
                    arrayList.add(Float.valueOf(((Point) obj2).x));
                }
                float averageOfFloat = (float) CollectionsKt___CollectionsKt.averageOfFloat(arrayList);
                ArrayList arrayList3 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList2, 10));
                int size2 = arrayList2.size();
                int i3 = 0;
                while (i3 < size2) {
                    Object obj3 = arrayList2.get(i3);
                    i3++;
                    arrayList3.add(Float.valueOf(((Point) obj3).y));
                }
                Point point3 = new Point(averageOfFloat, (float) CollectionsKt___CollectionsKt.averageOfFloat(arrayList3));
                ArrayList arrayList4 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(arrayList2, 10));
                int size3 = arrayList2.size();
                int i4 = 0;
                while (i4 < size3) {
                    Object obj4 = arrayList2.get(i4);
                    i4++;
                    arrayList4.add(Double.valueOf(distanceBetween((Point) obj4, point3)));
                }
                float averageOfDouble = (float) CollectionsKt___CollectionsKt.averageOfDouble(arrayList4);
                int size4 = arrayList2.size();
                while (true) {
                    if (i >= size4) {
                        this.gestureStateChangedCallback.mo779invoke(GestureState.Finished.INSTANCE);
                        break;
                    }
                    Object obj5 = arrayList2.get(i);
                    i++;
                    if (Math.abs(distanceBetween((Point) obj5, point3) - averageOfDouble) > 0.7f * averageOfDouble) {
                        break;
                    }
                }
            }
            reset();
        }
    }

    @Override // com.android.systemui.touchpad.tutorial.ui.gesture.GestureRecognizer
    public final void addGestureStateCallback(GestureRecognizerAdapter$gestureStateAsFlow$1$$ExternalSyntheticLambda0 gestureRecognizerAdapter$gestureStateAsFlow$1$$ExternalSyntheticLambda0) {
        this.gestureStateChangedCallback = gestureRecognizerAdapter$gestureStateAsFlow$1$$ExternalSyntheticLambda0;
    }

    @Override // com.android.systemui.touchpad.tutorial.ui.gesture.GestureRecognizer
    public final void clearGestureStateCallback() {
        this.gestureStateChangedCallback = new EasterEggGestureRecognizer$$ExternalSyntheticLambda0(1);
    }

    public final void reset() {
        this.cumulativeAngle = 0.0f;
        this.lastAngle = null;
        this.circleCount = 0;
        ((ArrayList) this.points).clear();
    }
}
