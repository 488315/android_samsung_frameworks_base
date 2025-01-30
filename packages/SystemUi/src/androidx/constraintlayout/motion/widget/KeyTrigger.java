package androidx.constraintlayout.motion.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseIntArray;
import android.view.View;
import android.view.ViewGroup;
import androidx.activity.result.ActivityResultRegistry$$ExternalSyntheticOutline0;
import androidx.constraintlayout.motion.widget.MotionLayout;
import androidx.constraintlayout.widget.ConstraintAttribute;
import androidx.constraintlayout.widget.R$styleable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Locale;
import java.util.concurrent.CopyOnWriteArrayList;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class KeyTrigger extends Key {
    public float mFireLastPos;
    public String mCross = null;
    public int mTriggerReceiver = -1;
    public String mNegativeCross = null;
    public String mPositiveCross = null;
    public int mTriggerID = -1;
    public int mTriggerCollisionId = -1;
    public View mTriggerCollisionView = null;
    public float mTriggerSlack = 0.1f;
    public boolean mFireCrossReset = true;
    public boolean mFireNegativeReset = true;
    public boolean mFirePositiveReset = true;
    public float mFireThreshold = Float.NaN;
    public boolean mPostLayout = false;
    public int mViewTransitionOnNegativeCross = -1;
    public int mViewTransitionOnPositiveCross = -1;
    public int mViewTransitionOnCross = -1;
    public RectF mCollisionRect = new RectF();
    public RectF mTargetRect = new RectF();
    public HashMap mMethodHashMap = new HashMap();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Loader {
        public static final SparseIntArray mAttrMap;

        static {
            SparseIntArray sparseIntArray = new SparseIntArray();
            mAttrMap = sparseIntArray;
            sparseIntArray.append(0, 8);
            sparseIntArray.append(4, 4);
            sparseIntArray.append(5, 1);
            sparseIntArray.append(6, 2);
            sparseIntArray.append(1, 7);
            sparseIntArray.append(7, 6);
            sparseIntArray.append(9, 5);
            sparseIntArray.append(3, 9);
            sparseIntArray.append(2, 10);
            sparseIntArray.append(8, 11);
            sparseIntArray.append(10, 12);
            sparseIntArray.append(11, 13);
            sparseIntArray.append(12, 14);
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

    /* JADX WARN: Removed duplicated region for block: B:73:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x00b5  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x00c8  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x00a0  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void conditionallyFire(View view, float f) {
        boolean z;
        boolean z2;
        boolean z3;
        CopyOnWriteArrayList copyOnWriteArrayList;
        boolean z4;
        boolean z5 = true;
        boolean z6 = false;
        if (this.mTriggerCollisionId != -1) {
            if (this.mTriggerCollisionView == null) {
                this.mTriggerCollisionView = ((ViewGroup) view.getParent()).findViewById(this.mTriggerCollisionId);
            }
            setUpRect(this.mCollisionRect, this.mTriggerCollisionView, this.mPostLayout);
            setUpRect(this.mTargetRect, view, this.mPostLayout);
            if (this.mCollisionRect.intersect(this.mTargetRect)) {
                if (this.mFireCrossReset) {
                    this.mFireCrossReset = false;
                    z = true;
                } else {
                    z = false;
                }
                if (this.mFirePositiveReset) {
                    this.mFirePositiveReset = false;
                    z4 = true;
                } else {
                    z4 = false;
                }
                this.mFireNegativeReset = true;
                boolean z7 = z4;
                z3 = false;
                z6 = z7;
            } else {
                if (this.mFireCrossReset) {
                    z = false;
                } else {
                    this.mFireCrossReset = true;
                    z = true;
                }
                if (this.mFireNegativeReset) {
                    this.mFireNegativeReset = false;
                    z3 = true;
                } else {
                    z3 = false;
                }
                this.mFirePositiveReset = true;
            }
        } else {
            if (this.mFireCrossReset) {
                float f2 = this.mFireThreshold;
                if ((this.mFireLastPos - f2) * (f - f2) < 0.0f) {
                    this.mFireCrossReset = false;
                    z = true;
                    if (!this.mFireNegativeReset) {
                        float f3 = this.mFireThreshold;
                        float f4 = f - f3;
                        if ((this.mFireLastPos - f3) * f4 < 0.0f && f4 < 0.0f) {
                            this.mFireNegativeReset = false;
                            z2 = true;
                            if (this.mFirePositiveReset) {
                                float f5 = this.mFireThreshold;
                                float f6 = f - f5;
                                if ((this.mFireLastPos - f5) * f6 < 0.0f && f6 > 0.0f) {
                                    this.mFirePositiveReset = false;
                                    z6 = z5;
                                    z3 = z2;
                                }
                            } else if (Math.abs(f - this.mFireThreshold) > this.mTriggerSlack) {
                                this.mFirePositiveReset = true;
                            }
                            z5 = false;
                            z6 = z5;
                            z3 = z2;
                        }
                    } else if (Math.abs(f - this.mFireThreshold) > this.mTriggerSlack) {
                        this.mFireNegativeReset = true;
                    }
                    z2 = false;
                    if (this.mFirePositiveReset) {
                    }
                    z5 = false;
                    z6 = z5;
                    z3 = z2;
                }
            } else if (Math.abs(f - this.mFireThreshold) > this.mTriggerSlack) {
                this.mFireCrossReset = true;
            }
            z = false;
            if (!this.mFireNegativeReset) {
            }
            z2 = false;
            if (this.mFirePositiveReset) {
            }
            z5 = false;
            z6 = z5;
            z3 = z2;
        }
        this.mFireLastPos = f;
        if ((z3 || z || z6) && (copyOnWriteArrayList = ((MotionLayout) view.getParent()).mTransitionListeners) != null) {
            Iterator it = copyOnWriteArrayList.iterator();
            while (it.hasNext()) {
                ((MotionLayout.TransitionListener) it.next()).getClass();
            }
        }
        View findViewById = this.mTriggerReceiver == -1 ? view : ((MotionLayout) view.getParent()).findViewById(this.mTriggerReceiver);
        if (z3) {
            String str = this.mNegativeCross;
            if (str != null) {
                fire(findViewById, str);
            }
            if (this.mViewTransitionOnNegativeCross != -1) {
                ((MotionLayout) view.getParent()).viewTransition(this.mViewTransitionOnNegativeCross, findViewById);
            }
        }
        if (z6) {
            String str2 = this.mPositiveCross;
            if (str2 != null) {
                fire(findViewById, str2);
            }
            if (this.mViewTransitionOnPositiveCross != -1) {
                ((MotionLayout) view.getParent()).viewTransition(this.mViewTransitionOnPositiveCross, findViewById);
            }
        }
        if (z) {
            String str3 = this.mCross;
            if (str3 != null) {
                fire(findViewById, str3);
            }
            if (this.mViewTransitionOnCross != -1) {
                ((MotionLayout) view.getParent()).viewTransition(this.mViewTransitionOnCross, findViewById);
            }
        }
    }

    public final void fire(View view, String str) {
        Method method;
        if (str == null) {
            return;
        }
        if (!str.startsWith(".")) {
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
                    method = view.getClass().getMethod(str, new Class[0]);
                    this.mMethodHashMap.put(str, method);
                } catch (NoSuchMethodException unused) {
                    this.mMethodHashMap.put(str, null);
                    Log.e("KeyTrigger", "Could not find method \"" + str + "\"on class " + view.getClass().getSimpleName() + " " + Debug.getName(view));
                    return;
                }
            }
            try {
                method.invoke(view, new Object[0]);
                return;
            } catch (Exception unused2) {
                Log.e("KeyTrigger", "Exception in call \"" + this.mCross + "\"on class " + view.getClass().getSimpleName() + " " + Debug.getName(view));
                return;
            }
        }
        boolean z = str.length() == 1;
        if (!z) {
            str = str.substring(1).toLowerCase(Locale.ROOT);
        }
        for (String str2 : this.mCustomConstraints.keySet()) {
            String lowerCase = str2.toLowerCase(Locale.ROOT);
            if (z || lowerCase.matches(str)) {
                ConstraintAttribute constraintAttribute = (ConstraintAttribute) this.mCustomConstraints.get(str2);
                if (constraintAttribute != null) {
                    Class<?> cls = view.getClass();
                    boolean z2 = constraintAttribute.mMethod;
                    String str3 = constraintAttribute.mName;
                    String m21m = !z2 ? KeyAttributes$$ExternalSyntheticOutline0.m21m("set", str3) : str3;
                    try {
                        switch (ConstraintAttribute.AbstractC01401.f30x66adad53[constraintAttribute.mType.ordinal()]) {
                            case 1:
                            case 6:
                                cls.getMethod(m21m, Integer.TYPE).invoke(view, Integer.valueOf(constraintAttribute.mIntegerValue));
                                break;
                            case 2:
                                cls.getMethod(m21m, Boolean.TYPE).invoke(view, Boolean.valueOf(constraintAttribute.mBooleanValue));
                                break;
                            case 3:
                                cls.getMethod(m21m, CharSequence.class).invoke(view, constraintAttribute.mStringValue);
                                break;
                            case 4:
                                cls.getMethod(m21m, Integer.TYPE).invoke(view, Integer.valueOf(constraintAttribute.mColorValue));
                                break;
                            case 5:
                                Method method2 = cls.getMethod(m21m, Drawable.class);
                                ColorDrawable colorDrawable = new ColorDrawable();
                                colorDrawable.setColor(constraintAttribute.mColorValue);
                                method2.invoke(view, colorDrawable);
                                break;
                            case 7:
                                cls.getMethod(m21m, Float.TYPE).invoke(view, Float.valueOf(constraintAttribute.mFloatValue));
                                break;
                            case 8:
                                cls.getMethod(m21m, Float.TYPE).invoke(view, Float.valueOf(constraintAttribute.mFloatValue));
                                break;
                        }
                    } catch (IllegalAccessException e) {
                        StringBuilder m4m = ActivityResultRegistry$$ExternalSyntheticOutline0.m4m(" Custom Attribute \"", str3, "\" not found on ");
                        m4m.append(cls.getName());
                        Log.e("TransitionLayout", m4m.toString());
                        e.printStackTrace();
                    } catch (NoSuchMethodException e2) {
                        Log.e("TransitionLayout", e2.getMessage());
                        Log.e("TransitionLayout", " Custom Attribute \"" + str3 + "\" not found on " + cls.getName());
                        Log.e("TransitionLayout", cls.getName() + " must have a method " + m21m);
                    } catch (InvocationTargetException e3) {
                        StringBuilder m4m2 = ActivityResultRegistry$$ExternalSyntheticOutline0.m4m(" Custom Attribute \"", str3, "\" not found on ");
                        m4m2.append(cls.getName());
                        Log.e("TransitionLayout", m4m2.toString());
                        e3.printStackTrace();
                    }
                }
            }
        }
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public final void load(Context context, AttributeSet attributeSet) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.KeyTrigger);
        SparseIntArray sparseIntArray = Loader.mAttrMap;
        int indexCount = obtainStyledAttributes.getIndexCount();
        for (int i = 0; i < indexCount; i++) {
            int index = obtainStyledAttributes.getIndex(i);
            SparseIntArray sparseIntArray2 = Loader.mAttrMap;
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
    public final Key mo303clone() {
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
    public final void addValues(HashMap hashMap) {
    }

    @Override // androidx.constraintlayout.motion.widget.Key
    public final void getAttributeNames(HashSet hashSet) {
    }
}
