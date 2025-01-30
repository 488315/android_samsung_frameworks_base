package androidx.constraintlayout.motion.widget;

import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.motion.utils.ViewSpline;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintSet;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class MotionConstrainedPoint implements Comparable {
    public int visibility;
    public float alpha = 1.0f;
    public int mVisibilityMode = 0;
    public float elevation = 0.0f;
    public float rotation = 0.0f;
    public float rotationX = 0.0f;
    public float rotationY = 0.0f;
    public float scaleX = 1.0f;
    public float scaleY = 1.0f;
    public float mPivotX = Float.NaN;
    public float mPivotY = Float.NaN;
    public float translationX = 0.0f;
    public float translationY = 0.0f;
    public float translationZ = 0.0f;
    public float mPathRotate = Float.NaN;
    public float mProgress = Float.NaN;
    public final LinkedHashMap attributes = new LinkedHashMap();

    public static boolean diff(float f, float f2) {
        return (Float.isNaN(f) || Float.isNaN(f2)) ? Float.isNaN(f) != Float.isNaN(f2) : Math.abs(f - f2) > 1.0E-6f;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    public final void addValues(int i, HashMap hashMap) {
        char c;
        for (String str : hashMap.keySet()) {
            ViewSpline viewSpline = (ViewSpline) hashMap.get(str);
            str.getClass();
            switch (str.hashCode()) {
                case -1249320806:
                    if (str.equals("rotationX")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case -1249320805:
                    if (str.equals("rotationY")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case -1225497657:
                    if (str.equals("translationX")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                case -1225497656:
                    if (str.equals("translationY")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case -1225497655:
                    if (str.equals("translationZ")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case -1001078227:
                    if (str.equals("progress")) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case -908189618:
                    if (str.equals("scaleX")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case -908189617:
                    if (str.equals("scaleY")) {
                        c = 7;
                        break;
                    }
                    c = 65535;
                    break;
                case -760884510:
                    if (str.equals("transformPivotX")) {
                        c = '\b';
                        break;
                    }
                    c = 65535;
                    break;
                case -760884509:
                    if (str.equals("transformPivotY")) {
                        c = '\t';
                        break;
                    }
                    c = 65535;
                    break;
                case -40300674:
                    if (str.equals("rotation")) {
                        c = '\n';
                        break;
                    }
                    c = 65535;
                    break;
                case -4379043:
                    if (str.equals("elevation")) {
                        c = 11;
                        break;
                    }
                    c = 65535;
                    break;
                case 37232917:
                    if (str.equals("transitionPathRotate")) {
                        c = '\f';
                        break;
                    }
                    c = 65535;
                    break;
                case 92909918:
                    if (str.equals("alpha")) {
                        c = '\r';
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    viewSpline.setPoint(Float.isNaN(this.rotationX) ? 0.0f : this.rotationX, i);
                    break;
                case 1:
                    viewSpline.setPoint(Float.isNaN(this.rotationY) ? 0.0f : this.rotationY, i);
                    break;
                case 2:
                    viewSpline.setPoint(Float.isNaN(this.translationX) ? 0.0f : this.translationX, i);
                    break;
                case 3:
                    viewSpline.setPoint(Float.isNaN(this.translationY) ? 0.0f : this.translationY, i);
                    break;
                case 4:
                    viewSpline.setPoint(Float.isNaN(this.translationZ) ? 0.0f : this.translationZ, i);
                    break;
                case 5:
                    viewSpline.setPoint(Float.isNaN(this.mProgress) ? 0.0f : this.mProgress, i);
                    break;
                case 6:
                    viewSpline.setPoint(Float.isNaN(this.scaleX) ? 1.0f : this.scaleX, i);
                    break;
                case 7:
                    viewSpline.setPoint(Float.isNaN(this.scaleY) ? 1.0f : this.scaleY, i);
                    break;
                case '\b':
                    viewSpline.setPoint(Float.isNaN(this.mPivotX) ? 0.0f : this.mPivotX, i);
                    break;
                case '\t':
                    viewSpline.setPoint(Float.isNaN(this.mPivotY) ? 0.0f : this.mPivotY, i);
                    break;
                case '\n':
                    viewSpline.setPoint(Float.isNaN(this.rotation) ? 0.0f : this.rotation, i);
                    break;
                case 11:
                    viewSpline.setPoint(Float.isNaN(this.elevation) ? 0.0f : this.elevation, i);
                    break;
                case '\f':
                    viewSpline.setPoint(Float.isNaN(this.mPathRotate) ? 0.0f : this.mPathRotate, i);
                    break;
                case '\r':
                    viewSpline.setPoint(Float.isNaN(this.alpha) ? 1.0f : this.alpha, i);
                    break;
                default:
                    if (str.startsWith("CUSTOM")) {
                        String str2 = str.split(",")[1];
                        if (this.attributes.containsKey(str2)) {
                            ConstraintAttribute constraintAttribute = (ConstraintAttribute) this.attributes.get(str2);
                            if (viewSpline instanceof ViewSpline.CustomSet) {
                                ((ViewSpline.CustomSet) viewSpline).mConstraintAttributeList.append(i, constraintAttribute);
                                break;
                            } else {
                                Log.e("MotionPaths", str + " ViewSpline not a CustomSet frame = " + i + ", value" + constraintAttribute.getValueToInterpolate() + viewSpline);
                                break;
                            }
                        } else {
                            break;
                        }
                    } else {
                        Log.e("MotionPaths", "UNKNOWN spline ".concat(str));
                        break;
                    }
            }
        }
    }

    public final void applyParameters(View view) {
        this.visibility = view.getVisibility();
        this.alpha = view.getVisibility() != 0 ? 0.0f : view.getAlpha();
        this.elevation = view.getElevation();
        this.rotation = view.getRotation();
        this.rotationX = view.getRotationX();
        this.rotationY = view.getRotationY();
        this.scaleX = view.getScaleX();
        this.scaleY = view.getScaleY();
        this.mPivotX = view.getPivotX();
        this.mPivotY = view.getPivotY();
        this.translationX = view.getTranslationX();
        this.translationY = view.getTranslationY();
        this.translationZ = view.getTranslationZ();
    }

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        ((MotionConstrainedPoint) obj).getClass();
        return Float.compare(0.0f, 0.0f);
    }

    public final void setState(Rect rect, ConstraintSet constraintSet, int i, int i2) {
        rect.width();
        rect.height();
        ConstraintSet.Constraint constraint = constraintSet.get(i2);
        ConstraintSet.PropertySet propertySet = constraint.propertySet;
        int i3 = propertySet.mVisibilityMode;
        this.mVisibilityMode = i3;
        int i4 = propertySet.visibility;
        this.visibility = i4;
        this.alpha = (i4 == 0 || i3 != 0) ? propertySet.alpha : 0.0f;
        ConstraintSet.Transform transform = constraint.transform;
        boolean z = transform.applyElevation;
        this.elevation = transform.elevation;
        this.rotation = transform.rotation;
        this.rotationX = transform.rotationX;
        this.rotationY = transform.rotationY;
        this.scaleX = transform.scaleX;
        this.scaleY = transform.scaleY;
        this.mPivotX = transform.transformPivotX;
        this.mPivotY = transform.transformPivotY;
        this.translationX = transform.translationX;
        this.translationY = transform.translationY;
        this.translationZ = transform.translationZ;
        ConstraintSet.Motion motion = constraint.motion;
        Easing.getInterpolator(motion.mTransitionEasing);
        this.mPathRotate = motion.mPathRotate;
        this.mProgress = constraint.propertySet.mProgress;
        Iterator it = constraint.mCustomConstraints.keySet().iterator();
        while (true) {
            if (!it.hasNext()) {
                break;
            }
            String str = (String) it.next();
            ConstraintAttribute constraintAttribute = (ConstraintAttribute) constraint.mCustomConstraints.get(str);
            constraintAttribute.getClass();
            int i5 = ConstraintAttribute.AbstractC01401.f30x66adad53[constraintAttribute.mType.ordinal()];
            if ((i5 == 1 || i5 == 2 || i5 == 3) ? false : true) {
                this.attributes.put(str, constraintAttribute);
            }
        }
        if (i != 1) {
            if (i != 2) {
                if (i != 3) {
                    if (i != 4) {
                        return;
                    }
                }
            }
            float f = this.rotation + 90.0f;
            this.rotation = f;
            if (f > 180.0f) {
                this.rotation = f - 360.0f;
                return;
            }
            return;
        }
        this.rotation -= 90.0f;
    }
}
