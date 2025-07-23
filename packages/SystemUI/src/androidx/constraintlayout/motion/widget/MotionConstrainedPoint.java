package androidx.constraintlayout.motion.widget;

import android.graphics.Rect;
import android.util.Log;
import android.view.View;
import androidx.constraintlayout.core.motion.utils.Easing;
import androidx.constraintlayout.motion.utils.ViewSpline;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.ConstraintSet;
import java.util.HashMap;
import java.util.LinkedHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class MotionConstrainedPoint implements Comparable {
    public int mVisibility;
    public float rotationY = 0.0f;
    public int mVisibilityMode = 0;
    public final LinkedHashMap mAttributes = new LinkedHashMap();
    public float mAlpha = 1.0f;
    public float mElevation = 0.0f;
    public float mRotation = 0.0f;
    public float mRotationX = 0.0f;
    public float mScaleX = 1.0f;
    public float mScaleY = 1.0f;
    public float mPivotX = Float.NaN;
    public float mPivotY = Float.NaN;
    public float mTranslationX = 0.0f;
    public float mTranslationY = 0.0f;
    public float mTranslationZ = 0.0f;
    public float mPathRotate = Float.NaN;
    public float mProgress = Float.NaN;

    public static boolean diff(float f, float f2) {
        return (Float.isNaN(f) || Float.isNaN(f2)) ? Float.isNaN(f) != Float.isNaN(f2) : Math.abs(f - f2) > 1.0E-6f;
    }

    public final void addValues(int i, HashMap hashMap) {
        for (String str : hashMap.keySet()) {
            ViewSpline viewSpline = (ViewSpline) hashMap.get(str);
            if (viewSpline != null) {
                str.getClass();
                switch (str) {
                    case "rotationX":
                        viewSpline.setPoint(Float.isNaN(this.mRotationX) ? 0.0f : this.mRotationX, i);
                        break;
                    case "rotationY":
                        viewSpline.setPoint(Float.isNaN(this.rotationY) ? 0.0f : this.rotationY, i);
                        break;
                    case "translationX":
                        viewSpline.setPoint(Float.isNaN(this.mTranslationX) ? 0.0f : this.mTranslationX, i);
                        break;
                    case "translationY":
                        viewSpline.setPoint(Float.isNaN(this.mTranslationY) ? 0.0f : this.mTranslationY, i);
                        break;
                    case "translationZ":
                        viewSpline.setPoint(Float.isNaN(this.mTranslationZ) ? 0.0f : this.mTranslationZ, i);
                        break;
                    case "progress":
                        viewSpline.setPoint(Float.isNaN(this.mProgress) ? 0.0f : this.mProgress, i);
                        break;
                    case "scaleX":
                        viewSpline.setPoint(Float.isNaN(this.mScaleX) ? 1.0f : this.mScaleX, i);
                        break;
                    case "scaleY":
                        viewSpline.setPoint(Float.isNaN(this.mScaleY) ? 1.0f : this.mScaleY, i);
                        break;
                    case "transformPivotX":
                        viewSpline.setPoint(Float.isNaN(this.mPivotX) ? 0.0f : this.mPivotX, i);
                        break;
                    case "transformPivotY":
                        viewSpline.setPoint(Float.isNaN(this.mPivotY) ? 0.0f : this.mPivotY, i);
                        break;
                    case "rotation":
                        viewSpline.setPoint(Float.isNaN(this.mRotation) ? 0.0f : this.mRotation, i);
                        break;
                    case "elevation":
                        viewSpline.setPoint(Float.isNaN(this.mElevation) ? 0.0f : this.mElevation, i);
                        break;
                    case "transitionPathRotate":
                        viewSpline.setPoint(Float.isNaN(this.mPathRotate) ? 0.0f : this.mPathRotate, i);
                        break;
                    case "alpha":
                        viewSpline.setPoint(Float.isNaN(this.mAlpha) ? 1.0f : this.mAlpha, i);
                        break;
                    default:
                        if (str.startsWith("CUSTOM")) {
                            String str2 = str.split(",")[1];
                            if (this.mAttributes.containsKey(str2)) {
                                ConstraintAttribute constraintAttribute = (ConstraintAttribute) this.mAttributes.get(str2);
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
    }

    public final void applyParameters(View view) {
        this.mVisibility = view.getVisibility();
        this.mAlpha = view.getVisibility() != 0 ? 0.0f : view.getAlpha();
        this.mElevation = view.getElevation();
        this.mRotation = view.getRotation();
        this.mRotationX = view.getRotationX();
        this.rotationY = view.getRotationY();
        this.mScaleX = view.getScaleX();
        this.mScaleY = view.getScaleY();
        this.mPivotX = view.getPivotX();
        this.mPivotY = view.getPivotY();
        this.mTranslationX = view.getTranslationX();
        this.mTranslationY = view.getTranslationY();
        this.mTranslationZ = view.getTranslationZ();
    }

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        ((MotionConstrainedPoint) obj).getClass();
        return Float.compare(0.0f, 0.0f);
    }

    public final void setState(Rect rect, ConstraintSet constraintSet, int i, int i2) {
        rect.width();
        rect.height();
        ConstraintSet.Constraint parameters = constraintSet.getParameters(i2);
        ConstraintSet.PropertySet propertySet = parameters.propertySet;
        int i3 = propertySet.mVisibilityMode;
        this.mVisibilityMode = i3;
        int i4 = propertySet.visibility;
        this.mVisibility = i4;
        this.mAlpha = (i4 == 0 || i3 != 0) ? propertySet.alpha : 0.0f;
        ConstraintSet.Transform transform = parameters.transform;
        boolean z = transform.applyElevation;
        this.mElevation = transform.elevation;
        this.mRotation = transform.rotation;
        this.mRotationX = transform.rotationX;
        this.rotationY = transform.rotationY;
        this.mScaleX = transform.scaleX;
        this.mScaleY = transform.scaleY;
        this.mPivotX = transform.transformPivotX;
        this.mPivotY = transform.transformPivotY;
        this.mTranslationX = transform.translationX;
        this.mTranslationY = transform.translationY;
        this.mTranslationZ = transform.translationZ;
        Easing.getInterpolator(parameters.motion.mTransitionEasing);
        this.mPathRotate = parameters.motion.mPathRotate;
        this.mProgress = parameters.propertySet.mProgress;
        for (String str : parameters.mCustomConstraints.keySet()) {
            ConstraintAttribute constraintAttribute = parameters.mCustomConstraints.get(str);
            if (constraintAttribute.isContinuous()) {
                this.mAttributes.put(str, constraintAttribute);
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
            float f = this.mRotation + 90.0f;
            this.mRotation = f;
            if (f > 180.0f) {
                this.mRotation = f - 360.0f;
                return;
            }
            return;
        }
        this.mRotation -= 90.0f;
    }
}
