package androidx.appcompat.widget;

import android.R;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
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
import androidx.appcompat.app.AppCompatDelegateImpl$$ExternalSyntheticOutline0;
import androidx.core.view.ViewCompat;
import androidx.reflect.SeslBaseReflector;
import androidx.reflect.view.SeslViewReflector;
import androidx.reflect.widget.SeslTextViewReflector;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class SeslCheckedTextView extends TextView implements Checkable {
    public static final int[] CHECKED_STATE_SET = {R.attr.state_checked};
    public int mBasePadding;
    public Drawable mCheckMarkDrawable;
    public final int mCheckMarkGravity;
    public final ColorStateList mCheckMarkTintList;
    public final PorterDuff.Mode mCheckMarkTintMode;
    public int mCheckMarkWidth;
    public boolean mChecked;
    public final int mDrawablePadding;
    public final boolean mHasCheckMarkTint;
    public final boolean mHasCheckMarkTintMode;
    public boolean mNeedRequestlayout;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SavedState extends View.BaseSavedState {
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
            return AppCompatDelegateImpl$$ExternalSyntheticOutline0.m6m(sb, this.checked, "}");
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

    /* JADX WARN: Removed duplicated region for block: B:11:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:14:? A[RETURN, SYNTHETIC] */
    @Override // android.widget.TextView, android.view.View, android.graphics.drawable.Drawable.Callback
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void invalidateDrawable(Drawable drawable) {
        boolean z;
        super.invalidateDrawable(drawable);
        if (!verifyDrawable(drawable)) {
            return;
        }
        Rect bounds = drawable.getBounds();
        if (!ViewUtils.isLayoutRtl(this)) {
            return;
        }
        Field declaredField = SeslBaseReflector.getDeclaredField(SeslTextViewReflector.mClass, "mSingleLine");
        if (declaredField != null) {
            Object obj = SeslBaseReflector.get(declaredField, this);
            if (obj instanceof Boolean) {
                z = ((Boolean) obj).booleanValue();
                if (z) {
                    return;
                }
                invalidate(bounds.left, bounds.top, bounds.right, bounds.bottom);
                return;
            }
        }
        z = false;
        if (z) {
        }
    }

    public final boolean isCheckMarkAtStart() {
        int i = this.mCheckMarkGravity;
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        return (Gravity.getAbsoluteGravity(i, ViewCompat.Api17Impl.getLayoutDirection(this)) & 7) == 3;
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
        if (isChecked()) {
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
            if (ViewUtils.isLayoutRtl(this)) {
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

    /* JADX WARN: Removed duplicated region for block: B:15:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0078  */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0082  */
    @Override // android.widget.TextView, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void onRtlPropertiesChanged(int i) {
        int i2;
        Field declaredField;
        int i3;
        Field declaredField2;
        super.onRtlPropertiesChanged(i);
        Class cls = SeslViewReflector.mClass;
        Method declaredMethod = SeslBaseReflector.getDeclaredMethod(cls, "resetPaddingToInitialValues", new Class[0]);
        if (declaredMethod != null) {
            SeslBaseReflector.invoke(this, declaredMethod, new Object[0]);
        }
        int i4 = this.mCheckMarkDrawable != null ? this.mCheckMarkWidth + this.mBasePadding + this.mDrawablePadding : this.mBasePadding;
        if (isCheckMarkAtStart()) {
            boolean z = this.mNeedRequestlayout;
            Field declaredField3 = SeslBaseReflector.getDeclaredField(cls, "mPaddingLeft");
            if (declaredField3 != null) {
                Object obj = SeslBaseReflector.get(declaredField3, this);
                if (obj instanceof Integer) {
                    i3 = ((Integer) obj).intValue();
                    this.mNeedRequestlayout = z | (i3 != i4);
                    declaredField2 = SeslBaseReflector.getDeclaredField(cls, "mPaddingLeft");
                    if (declaredField2 != null) {
                        SeslBaseReflector.set(declaredField2, this, Integer.valueOf(i4));
                    }
                }
            }
            i3 = 0;
            this.mNeedRequestlayout = z | (i3 != i4);
            declaredField2 = SeslBaseReflector.getDeclaredField(cls, "mPaddingLeft");
            if (declaredField2 != null) {
            }
        } else {
            boolean z2 = this.mNeedRequestlayout;
            Field declaredField4 = SeslBaseReflector.getDeclaredField(cls, "mPaddingRight");
            if (declaredField4 != null) {
                Object obj2 = SeslBaseReflector.get(declaredField4, this);
                if (obj2 instanceof Integer) {
                    i2 = ((Integer) obj2).intValue();
                    this.mNeedRequestlayout = z2 | (i2 != i4);
                    declaredField = SeslBaseReflector.getDeclaredField(cls, "mPaddingRight");
                    if (declaredField != null) {
                        SeslBaseReflector.set(declaredField, this, Integer.valueOf(i4));
                    }
                }
            }
            i2 = 0;
            this.mNeedRequestlayout = z2 | (i2 != i4);
            declaredField = SeslBaseReflector.getDeclaredField(cls, "mPaddingRight");
            if (declaredField != null) {
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
        savedState.checked = isChecked();
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
