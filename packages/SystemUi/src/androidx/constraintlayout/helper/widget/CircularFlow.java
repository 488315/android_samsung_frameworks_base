package androidx.constraintlayout.helper.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.R$styleable;
import androidx.constraintlayout.widget.VirtualLayout;
import java.util.Arrays;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class CircularFlow extends VirtualLayout {
    public static float DEFAULT_ANGLE;
    public static int DEFAULT_RADIUS;
    public float[] mAngles;
    public ConstraintLayout mContainer;
    public int mCountAngle;
    public int mCountRadius;
    public int[] mRadius;
    public String mReferenceAngles;
    public Float mReferenceDefaultAngle;
    public Integer mReferenceDefaultRadius;
    public String mReferenceRadius;
    public int mViewCenter;

    public CircularFlow(Context context) {
        super(context);
    }

    public final void addAngle(String str) {
        float[] fArr;
        if (str == null || str.length() == 0 || this.myContext == null || (fArr = this.mAngles) == null) {
            return;
        }
        if (this.mCountAngle + 1 > fArr.length) {
            this.mAngles = Arrays.copyOf(fArr, fArr.length + 1);
        }
        this.mAngles[this.mCountAngle] = Integer.parseInt(str);
        this.mCountAngle++;
    }

    public final void addRadius(String str) {
        int[] iArr;
        if (str == null || str.length() == 0 || this.myContext == null || (iArr = this.mRadius) == null) {
            return;
        }
        if (this.mCountRadius + 1 > iArr.length) {
            this.mRadius = Arrays.copyOf(iArr, iArr.length + 1);
        }
        this.mRadius[this.mCountRadius] = (int) (Integer.parseInt(str) * this.myContext.getResources().getDisplayMetrics().density);
        this.mCountRadius++;
    }

    @Override // androidx.constraintlayout.widget.VirtualLayout, androidx.constraintlayout.widget.ConstraintHelper
    public final void init(AttributeSet attributeSet) {
        super.init(attributeSet);
        if (attributeSet != null) {
            TypedArray obtainStyledAttributes = getContext().obtainStyledAttributes(attributeSet, R$styleable.ConstraintLayout_Layout);
            int indexCount = obtainStyledAttributes.getIndexCount();
            for (int i = 0; i < indexCount; i++) {
                int index = obtainStyledAttributes.getIndex(i);
                if (index == 33) {
                    this.mViewCenter = obtainStyledAttributes.getResourceId(index, 0);
                } else if (index == 29) {
                    String string = obtainStyledAttributes.getString(index);
                    this.mReferenceAngles = string;
                    setAngles(string);
                } else if (index == 32) {
                    String string2 = obtainStyledAttributes.getString(index);
                    this.mReferenceRadius = string2;
                    setRadius(string2);
                } else if (index == 30) {
                    Float valueOf = Float.valueOf(obtainStyledAttributes.getFloat(index, DEFAULT_ANGLE));
                    this.mReferenceDefaultAngle = valueOf;
                    DEFAULT_ANGLE = valueOf.floatValue();
                } else if (index == 31) {
                    Integer valueOf2 = Integer.valueOf(obtainStyledAttributes.getDimensionPixelSize(index, DEFAULT_RADIUS));
                    this.mReferenceDefaultRadius = valueOf2;
                    DEFAULT_RADIUS = valueOf2.intValue();
                }
            }
            obtainStyledAttributes.recycle();
        }
    }

    @Override // androidx.constraintlayout.widget.VirtualLayout, androidx.constraintlayout.widget.ConstraintHelper, android.view.View
    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        String str = this.mReferenceAngles;
        if (str != null) {
            this.mAngles = new float[1];
            setAngles(str);
        }
        String str2 = this.mReferenceRadius;
        if (str2 != null) {
            this.mRadius = new int[1];
            setRadius(str2);
        }
        Float f = this.mReferenceDefaultAngle;
        if (f != null) {
            DEFAULT_ANGLE = f.floatValue();
        }
        Integer num = this.mReferenceDefaultRadius;
        if (num != null) {
            DEFAULT_RADIUS = num.intValue();
        }
        this.mContainer = (ConstraintLayout) getParent();
        for (int i = 0; i < this.mCount; i++) {
            View viewById = this.mContainer.getViewById(this.mIds[i]);
            if (viewById != null) {
                int i2 = DEFAULT_RADIUS;
                float f2 = DEFAULT_ANGLE;
                int[] iArr = this.mRadius;
                if (iArr == null || i >= iArr.length) {
                    Integer num2 = this.mReferenceDefaultRadius;
                    if (num2 == null || num2.intValue() == -1) {
                        Log.e("CircularFlow", "Added radius to view with id: " + ((String) this.mMap.get(Integer.valueOf(viewById.getId()))));
                    } else {
                        int i3 = this.mCountRadius + 1;
                        this.mCountRadius = i3;
                        if (this.mRadius == null) {
                            this.mRadius = new int[1];
                        }
                        int[] copyOf = Arrays.copyOf(this.mRadius, i3);
                        this.mRadius = copyOf;
                        copyOf[this.mCountRadius - 1] = i2;
                    }
                } else {
                    i2 = iArr[i];
                }
                float[] fArr = this.mAngles;
                if (fArr == null || i >= fArr.length) {
                    Float f3 = this.mReferenceDefaultAngle;
                    if (f3 == null || f3.floatValue() == -1.0f) {
                        Log.e("CircularFlow", "Added angle to view with id: " + ((String) this.mMap.get(Integer.valueOf(viewById.getId()))));
                    } else {
                        int i4 = this.mCountAngle + 1;
                        this.mCountAngle = i4;
                        if (this.mAngles == null) {
                            this.mAngles = new float[1];
                        }
                        float[] copyOf2 = Arrays.copyOf(this.mAngles, i4);
                        this.mAngles = copyOf2;
                        copyOf2[this.mCountAngle - 1] = f2;
                    }
                } else {
                    f2 = fArr[i];
                }
                ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) viewById.getLayoutParams();
                layoutParams.circleAngle = f2;
                layoutParams.circleConstraint = this.mViewCenter;
                layoutParams.circleRadius = i2;
                viewById.setLayoutParams(layoutParams);
            }
        }
        applyLayoutFeatures();
    }

    public final void setAngles(String str) {
        if (str == null) {
            return;
        }
        int i = 0;
        this.mCountAngle = 0;
        while (true) {
            int indexOf = str.indexOf(44, i);
            if (indexOf == -1) {
                addAngle(str.substring(i).trim());
                return;
            } else {
                addAngle(str.substring(i, indexOf).trim());
                i = indexOf + 1;
            }
        }
    }

    public final void setRadius(String str) {
        if (str == null) {
            return;
        }
        int i = 0;
        this.mCountRadius = 0;
        while (true) {
            int indexOf = str.indexOf(44, i);
            if (indexOf == -1) {
                addRadius(str.substring(i).trim());
                return;
            } else {
                addRadius(str.substring(i, indexOf).trim());
                i = indexOf + 1;
            }
        }
    }

    public CircularFlow(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CircularFlow(Context context, AttributeSet attributeSet, int i) {
        super(context, attributeSet, i);
    }
}
