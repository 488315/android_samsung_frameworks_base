package androidx.appcompat.widget;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.Checkable;
import android.widget.CheckedTextView;
import android.widget.TextView;
import androidx.appcompat.R$styleable;
import androidx.core.view.ViewCompat;
import androidx.reflect.SeslBaseReflector;
import androidx.reflect.view.SeslViewReflector;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.WeakHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class SeslCheckedTextView extends TextView implements Checkable {
    public static final int[] CHECKED_STATE_SET = {R.attr.state_checked};
    public int mBasePadding;
    public Drawable mCheckMarkDrawable;
    public final int mCheckMarkGravity;
    public final int mCheckMarkPadding;
    public final ColorStateList mCheckMarkTintList;
    public final PorterDuff.Mode mCheckMarkTintMode;
    public int mCheckMarkWidth;
    public boolean mChecked;
    public final int mDrawablePadding;
    public final boolean mHasCheckMarkTint;
    public final boolean mHasCheckMarkTintMode;
    public boolean mNeedRequestlayout;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator() { // from class: androidx.appcompat.widget.SeslCheckedTextView.SavedState.1
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new SavedState[i];
            }
        };
        public boolean checked;

        public final String toString() {
            StringBuilder sb = new StringBuilder("SeslCheckedTextView.SavedState{");
            sb.append(Integer.toHexString(System.identityHashCode(this)));
            sb.append(" checked=");
            return MoveResult$$ExternalSyntheticOutline0.m(sb, this.checked, "}");
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeValue(Boolean.valueOf(this.checked));
        }

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.checked = ((Boolean) parcel.readValue(null)).booleanValue();
        }
    }

    public SeslCheckedTextView(Context context) {
        this(context, null);
    }

    public final void applyCheckMarkTint() {
        Drawable drawable = this.mCheckMarkDrawable;
        if (drawable != null) {
            if (this.mHasCheckMarkTint || this.mHasCheckMarkTintMode) {
                Drawable mutate = drawable.mutate();
                this.mCheckMarkDrawable = mutate;
                if (this.mHasCheckMarkTint) {
                    mutate.setTintList(this.mCheckMarkTintList);
                }
                if (this.mHasCheckMarkTintMode) {
                    this.mCheckMarkDrawable.setTintMode(this.mCheckMarkTintMode);
                }
                if (this.mCheckMarkDrawable.isStateful()) {
                    this.mCheckMarkDrawable.setState(getDrawableState());
                }
            }
        }
    }

    @Override // android.widget.TextView, android.view.View
    public final void drawableHotspotChanged(float f, float f2) {
        super.drawableHotspotChanged(f, f2);
        Drawable drawable = this.mCheckMarkDrawable;
        if (drawable != null) {
            drawable.setHotspot(f, f2);
        }
    }

    @Override // android.widget.TextView, android.view.View
    public final void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable = this.mCheckMarkDrawable;
        if (drawable != null && drawable.isStateful() && drawable.setState(getDrawableState())) {
            invalidateDrawable(drawable);
        }
    }

    @Override // android.widget.TextView, android.view.View
    public final CharSequence getAccessibilityClassName() {
        return CheckedTextView.class.getName();
    }

    /* JADX WARN: Removed duplicated region for block: B:11:0x0030  */
    /* JADX WARN: Removed duplicated region for block: B:14:? A[RETURN, SYNTHETIC] */
    @Override // android.widget.TextView, android.view.View, android.graphics.drawable.Drawable.Callback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void invalidateDrawable(android.graphics.drawable.Drawable r4) {
        /*
            r3 = this;
            super.invalidateDrawable(r4)
            boolean r0 = r3.verifyDrawable(r4)
            if (r0 == 0) goto L3b
            android.graphics.Rect r4 = r4.getBounds()
            int r0 = r3.getLayoutDirection()
            r1 = 1
            if (r0 != r1) goto L3b
            java.lang.Class r0 = androidx.reflect.widget.SeslTextViewReflector.mClass
            java.lang.String r1 = "mSingleLine"
            java.lang.reflect.Field r0 = androidx.reflect.SeslBaseReflector.getDeclaredField(r0, r1)
            if (r0 == 0) goto L2d
            java.lang.Object r0 = androidx.reflect.SeslBaseReflector.get(r0, r3)
            boolean r1 = r0 instanceof java.lang.Boolean
            if (r1 == 0) goto L2d
            java.lang.Boolean r0 = (java.lang.Boolean) r0
            boolean r0 = r0.booleanValue()
            goto L2e
        L2d:
            r0 = 0
        L2e:
            if (r0 == 0) goto L3b
            int r0 = r4.left
            int r1 = r4.top
            int r2 = r4.right
            int r4 = r4.bottom
            r3.invalidate(r0, r1, r2, r4)
        L3b:
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.appcompat.widget.SeslCheckedTextView.invalidateDrawable(android.graphics.drawable.Drawable):void");
    }

    public final boolean isCheckMarkAtStart() {
        int i = this.mCheckMarkGravity;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        return (Gravity.getAbsoluteGravity(i, getLayoutDirection()) & 7) == 3;
    }

    @Override // android.widget.Checkable
    public final boolean isChecked() {
        return this.mChecked;
    }

    @Override // android.widget.TextView, android.view.View
    public final void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable = this.mCheckMarkDrawable;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
    }

    @Override // android.widget.TextView, android.view.View
    public final int[] onCreateDrawableState(int i) {
        int[] onCreateDrawableState = super.onCreateDrawableState(i + 1);
        if (this.mChecked) {
            TextView.mergeDrawableStates(onCreateDrawableState, CHECKED_STATE_SET);
        }
        return onCreateDrawableState;
    }

    @Override // android.widget.TextView, android.view.View
    public final void onDraw(Canvas canvas) {
        int i;
        int i2;
        super.onDraw(canvas);
        Drawable drawable = this.mCheckMarkDrawable;
        if (drawable != null) {
            int gravity = getGravity() & 112;
            int intrinsicHeight = drawable.getIntrinsicHeight();
            int height = gravity != 16 ? gravity != 80 ? 0 : getHeight() - intrinsicHeight : (getHeight() - intrinsicHeight) / 2;
            boolean isCheckMarkAtStart = isCheckMarkAtStart();
            int width = getWidth();
            int i3 = intrinsicHeight + height;
            if (isCheckMarkAtStart) {
                i2 = this.mBasePadding;
                i = this.mCheckMarkWidth + i2;
            } else {
                i = width - this.mBasePadding;
                i2 = i - this.mCheckMarkWidth;
            }
            int scrollX = getScrollX();
            if (getLayoutDirection() == 1) {
                drawable.setBounds(scrollX + i2, height, scrollX + i, i3);
            } else {
                drawable.setBounds(i2, height, i, i3);
            }
            drawable.draw(canvas);
            Drawable background = getBackground();
            if (background != null) {
                background.setHotspotBounds(i2 + scrollX, height, scrollX + i, i3);
            }
        }
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityEvent(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEvent(accessibilityEvent);
        accessibilityEvent.setChecked(this.mChecked);
    }

    @Override // android.view.View
    public final void onInitializeAccessibilityNodeInfo(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfo(accessibilityNodeInfo);
        accessibilityNodeInfo.setCheckable(true);
        accessibilityNodeInfo.setChecked(this.mChecked);
    }

    @Override // android.widget.TextView, android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setChecked(savedState.checked);
        requestLayout();
    }

    @Override // android.widget.TextView, android.view.View
    public final void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        Class cls = SeslViewReflector.mClass;
        Method declaredMethod = SeslBaseReflector.getDeclaredMethod(cls, "resetPaddingToInitialValues", new Class[0]);
        if (declaredMethod != null) {
            SeslBaseReflector.invoke(this, declaredMethod, new Object[0]);
        }
        int i2 = this.mCheckMarkDrawable != null ? this.mCheckMarkWidth + this.mBasePadding + this.mCheckMarkPadding + this.mDrawablePadding : this.mBasePadding;
        if (isCheckMarkAtStart()) {
            this.mNeedRequestlayout |= SeslViewReflector.getField_mPaddingLeft(this) != i2;
            Field declaredField = SeslBaseReflector.getDeclaredField(cls, "mPaddingLeft");
            if (declaredField != null) {
                SeslBaseReflector.set(this, declaredField, Integer.valueOf(i2));
            }
        } else {
            this.mNeedRequestlayout |= SeslViewReflector.getField_mPaddingRight(this) != i2;
            Field declaredField2 = SeslBaseReflector.getDeclaredField(cls, "mPaddingRight");
            if (declaredField2 != null) {
                SeslBaseReflector.set(this, declaredField2, Integer.valueOf(i2));
            }
        }
        if (this.mNeedRequestlayout) {
            requestLayout();
            this.mNeedRequestlayout = false;
        }
    }

    @Override // android.widget.TextView, android.view.View
    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.checked = this.mChecked;
        return savedState;
    }

    public final void setCheckMarkDrawable(Drawable drawable) {
        Drawable drawable2 = this.mCheckMarkDrawable;
        if (drawable2 != null) {
            drawable2.setCallback(null);
            unscheduleDrawable(this.mCheckMarkDrawable);
        }
        this.mNeedRequestlayout = drawable != this.mCheckMarkDrawable;
        drawable.setCallback(this);
        drawable.setVisible(getVisibility() == 0, false);
        drawable.setState(CHECKED_STATE_SET);
        setMinHeight(drawable.getIntrinsicHeight());
        this.mCheckMarkWidth = drawable.getIntrinsicWidth();
        drawable.setState(getDrawableState());
        this.mCheckMarkDrawable = drawable;
        applyCheckMarkTint();
        Method declaredMethod = SeslBaseReflector.getDeclaredMethod(SeslViewReflector.mClass, "hidden_resolvePadding", new Class[0]);
        if (declaredMethod != null) {
            SeslBaseReflector.invoke(this, declaredMethod, new Object[0]);
        }
        if (isCheckMarkAtStart()) {
            this.mBasePadding = getPaddingLeft();
        } else {
            this.mBasePadding = getPaddingRight();
        }
    }

    @Override // android.widget.Checkable
    public void setChecked(boolean z) {
        if (this.mChecked != z) {
            this.mChecked = z;
            refreshDrawableState();
            Method method = SeslBaseReflector.getMethod(SeslViewReflector.mClass, "hidden_notifyViewAccessibilityStateChangedIfNeeded", Integer.TYPE);
            if (method != null) {
                SeslBaseReflector.invoke(this, method, 0);
            }
        }
    }

    @Override // android.view.View
    public final void setVisibility(int i) {
        super.setVisibility(i);
        Drawable drawable = this.mCheckMarkDrawable;
        if (drawable != null) {
            drawable.setVisible(i == 0, false);
        }
    }

    @Override // android.widget.Checkable
    public final void toggle() {
        setChecked(!this.mChecked);
    }

    @Override // android.widget.TextView, android.view.View
    public final boolean verifyDrawable(Drawable drawable) {
        return drawable == this.mCheckMarkDrawable || super.verifyDrawable(drawable);
    }

    public SeslCheckedTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, com.android.systemui.R.attr.checkedTextViewStyle);
    }

    public SeslCheckedTextView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public SeslCheckedTextView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mCheckMarkTintList = null;
        this.mCheckMarkTintMode = null;
        this.mHasCheckMarkTint = false;
        this.mHasCheckMarkTintMode = false;
        this.mCheckMarkGravity = 8388611;
        int[] iArr = R$styleable.CheckedTextView;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, iArr, i, i2);
        try {
            saveAttributeDataForStyleable(context, iArr, attributeSet, obtainStyledAttributes, i, i2);
            this.mCheckMarkPadding = context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sesl_checked_spinner_padding_end);
            Drawable drawable = obtainStyledAttributes.getDrawable(1);
            if (drawable != null) {
                setCheckMarkDrawable(drawable);
            }
            if (obtainStyledAttributes.hasValue(3)) {
                this.mCheckMarkTintMode = DrawableUtils.parseTintMode(obtainStyledAttributes.getInt(3, -1), null);
                this.mHasCheckMarkTintMode = true;
            }
            if (obtainStyledAttributes.hasValue(2)) {
                this.mCheckMarkTintList = obtainStyledAttributes.getColorStateList(2);
                this.mHasCheckMarkTint = true;
            }
            this.mCheckMarkGravity = obtainStyledAttributes.getInt(5, 8388611);
            setChecked(obtainStyledAttributes.getBoolean(0, false));
            obtainStyledAttributes.recycle();
            this.mDrawablePadding = context.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.sesl_checked_text_padding);
            applyCheckMarkTint();
        } catch (Throwable th) {
            obtainStyledAttributes.recycle();
            throw th;
        }
    }
}
