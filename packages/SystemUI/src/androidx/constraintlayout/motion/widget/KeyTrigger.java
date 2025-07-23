package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.R;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class KeyTrigger extends Key {
    public float mFireLastPos;
    public float mTriggerSlack = 0.1f;
    public int mViewTransitionOnNegativeCross = -1;
    public int mViewTransitionOnPositiveCross = -1;
    public int mViewTransitionOnCross = -1;
    public RectF mCollisionRect = new RectF();
    public RectF mTargetRect = new RectF();
    public HashMap mMethodHashMap = new HashMap();
    public String mCross = null;
    public int mTriggerReceiver = -1;
    public String mNegativeCross = null;
    public String mPositiveCross = null;
    public int mTriggerID = -1;
    public int mTriggerCollisionId = -1;
    public View mTriggerCollisionView = null;
    public boolean mFireCrossReset = true;
    public boolean mFireNegativeReset = true;
    public boolean mFirePositiveReset = true;
    public float mFireThreshold = Float.NaN;
    public boolean mPostLayout = false;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Loader {
        public static final SparseIntArray sAttrMap;

        static {
            SparseIntArray sparseIntArray = new SparseIntArray();
            sAttrMap = sparseIntArray;
            sparseIntArray.append(R.styleable.KeyTrigger_framePosition, 8);
            sparseIntArray.append(R.styleable.KeyTrigger_onCross, 4);
            sparseIntArray.append(R.styleable.KeyTrigger_onNegativeCross, 1);
            sparseIntArray.append(R.styleable.KeyTrigger_onPositiveCross, 2);
            sparseIntArray.append(R.styleable.KeyTrigger_motionTarget, 7);
            sparseIntArray.append(R.styleable.KeyTrigger_triggerId, 6);
            sparseIntArray.append(R.styleable.KeyTrigger_triggerSlack, 5);
            sparseIntArray.append(R.styleable.KeyTrigger_motion_triggerOnCollision, 9);
            sparseIntArray.append(R.styleable.KeyTrigger_motion_postLayoutCollision, 10);
            sparseIntArray.append(R.styleable.KeyTrigger_triggerReceiver, 11);
            sparseIntArray.append(R.styleable.KeyTrigger_viewTransitionOnCross, 12);
            sparseIntArray.append(R.styleable.KeyTrigger_viewTransitionOnNegativeCross, 13);
            sparseIntArray.append(R.styleable.KeyTrigger_viewTransitionOnPositiveCross, 14);
        }

        private Loader() {
        }
    }

    public KeyTrigger() {
        this.mCustomConstraints = new HashMap();
    }

    public static void setUpRect(RectF rectF, View view, boolean z) {
        rectF.top = view.getTop();
        rectF.bottom = view.getBottom();
        rectF.left = view.getLeft();
        rectF.right = view.getRight();
        if (z) {
            view.getMatrix().mapRect(rectF);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:68:0x008b  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x00cd  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x009f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void conditionallyFire(float r11, android.view.View r12) {
        /*
            Method dump skipped, instructions count: 345
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.constraintlayout.motion.widget.KeyTrigger.conditionallyFire(float, android.view.View):void");
    }

    public final void fire(View view, String str) {
        Method method;
        if (str == null) {
            return;
        }
        if (str.startsWith(".")) {
            boolean z = str.length() == 1;
            if (!z) {
                str = str.substring(1).toLowerCase(Locale.ROOT);
            }
            for (String str2 : this.mCustomConstraints.keySet()) {
                String lowerCase = str2.toLowerCase(Locale.ROOT);
                if (z || lowerCase.matches(str)) {
                    ConstraintAttribute constraintAttribute = (ConstraintAttribute) this.mCustomConstraints.get(str2);
                    if (constraintAttribute != null) {
                        constraintAttribute.applyCustom(view);
                    }
                }
            }
            return;
        }
        if (this.mMethodHashMap.containsKey(str)) {
            method = (Method) this.mMethodHashMap.get(str);
            if (method == null) {
                return;
            }
        } else {
            method = null;
        }
        if (method == null) {
            try {
                Class[] clsArr = new Class[0];
                method = view.getClass().getMethod(str, null);
                this.mMethodHashMap.put(str, method);
            } catch (NoSuchMethodException unused) {
                this.mMethodHashMap.put(str, null);
                Log.e("KeyTrigger", "Could not find method \"" + str + "\"on class " + view.getClass().getSimpleName() + " " + Debug.getName(view));
                return;
            }
        }
        try {
            method.invoke(view, null);
        } catch (Exception unused2) {
            Log.e("KeyTrigger", "Exception in call \"" + this.mCross + "\"on class " + view.getClass().getSimpleName() + " " + Debug.getName(view));
        }
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public final void load(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.KeyTrigger);
        SparseIntArray sparseIntArray = Loader.sAttrMap;
        int indexCount = obtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = obtainStyledAttributes.getIndex(i);
            SparseIntArray sparseIntArray2 = Loader.sAttrMap;
            switch (sparseIntArray2.get(index)) {
                case 1:
                    this.mNegativeCross = obtainStyledAttributes.getString(index);
                    break;
                case 2:
                    this.mPositiveCross = obtainStyledAttributes.getString(index);
                    break;
                case 3:
                default:
                    Log.e("KeyTrigger", "unused attribute 0x" + Integer.toHexString(index) + "   " + sparseIntArray2.get(index));
                    break;
                case 4:
                    this.mCross = obtainStyledAttributes.getString(index);
                    break;
                case 5:
                    this.mTriggerSlack = obtainStyledAttributes.getFloat(index, this.mTriggerSlack);
                    break;
                case 6:
                    this.mTriggerID = obtainStyledAttributes.getResourceId(index, this.mTriggerID);
                    break;
                case 7:
                    if (MotionLayout.IS_IN_EDIT_MODE) {
                        int resourceId = obtainStyledAttributes.getResourceId(index, this.mTargetId);
                        this.mTargetId = resourceId;
                        if (resourceId == -1) {
                            this.mTargetString = obtainStyledAttributes.getString(index);
                            break;
                        } else {
                            break;
                        }
                    } else if (obtainStyledAttributes.peekValue(index).type == 3) {
                        this.mTargetString = obtainStyledAttributes.getString(index);
                        break;
                    } else {
                        this.mTargetId = obtainStyledAttributes.getResourceId(index, this.mTargetId);
                        break;
                    }
                case 8:
                    int integer = obtainStyledAttributes.getInteger(index, this.mFramePosition);
                    this.mFramePosition = integer;
                    this.mFireThreshold = (integer + 0.5f) / 100.0f;
                    break;
                case 9:
                    this.mTriggerCollisionId = obtainStyledAttributes.getResourceId(index, this.mTriggerCollisionId);
                    break;
                case 10:
                    this.mPostLayout = obtainStyledAttributes.getBoolean(index, this.mPostLayout);
                    break;
                case 11:
                    this.mTriggerReceiver = obtainStyledAttributes.getResourceId(index, this.mTriggerReceiver);
                    break;
                case 12:
                    this.mViewTransitionOnCross = obtainStyledAttributes.getResourceId(index, this.mViewTransitionOnCross);
                    break;
                case 13:
                    this.mViewTransitionOnNegativeCross = obtainStyledAttributes.getResourceId(index, this.mViewTransitionOnNegativeCross);
                    break;
                case 14:
                    this.mViewTransitionOnPositiveCross = obtainStyledAttributes.getResourceId(index, this.mViewTransitionOnPositiveCross);
                    break;
            }
        }
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    /* renamed from: clone */
    public final Key mo886clone() {
        KeyTrigger keyTrigger = new KeyTrigger();
        super.copy(this);
        keyTrigger.mCross = this.mCross;
        keyTrigger.mTriggerReceiver = this.mTriggerReceiver;
        keyTrigger.mNegativeCross = this.mNegativeCross;
        keyTrigger.mPositiveCross = this.mPositiveCross;
        keyTrigger.mTriggerID = this.mTriggerID;
        keyTrigger.mTriggerCollisionId = this.mTriggerCollisionId;
        keyTrigger.mTriggerCollisionView = this.mTriggerCollisionView;
        keyTrigger.mTriggerSlack = this.mTriggerSlack;
        keyTrigger.mFireCrossReset = this.mFireCrossReset;
        keyTrigger.mFireNegativeReset = this.mFireNegativeReset;
        keyTrigger.mFirePositiveReset = this.mFirePositiveReset;
        keyTrigger.mFireThreshold = this.mFireThreshold;
        keyTrigger.mFireLastPos = this.mFireLastPos;
        keyTrigger.mPostLayout = this.mPostLayout;
        keyTrigger.mCollisionRect = this.mCollisionRect;
        keyTrigger.mTargetRect = this.mTargetRect;
        keyTrigger.mMethodHashMap = this.mMethodHashMap;
        return keyTrigger;
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public final void getAttributeNames(HashSet hashSet) {
    }
}
