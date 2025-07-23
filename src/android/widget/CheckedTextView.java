package android.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.RemotableViewMethod;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewHierarchyEncoder;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.Flags;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import com.android.internal.R;

/* loaded from: classes5.dex */
public class CheckedTextView extends TextView implements Checkable {
    private static final int[] CHECKED_STATE_SET = {16842912};
    private int mBasePadding;
    private BlendMode mCheckMarkBlendMode;
    private Drawable mCheckMarkDrawable;
    private int mCheckMarkGravity;
    private int mCheckMarkHeight;
    private int mCheckMarkResource;
    private ColorStateList mCheckMarkTintList;
    private int mCheckMarkWidth;
    private boolean mChecked;
    private int mDrawablePadding;
    private boolean mHasCheckMarkTint;
    private boolean mHasCheckMarkTintMode;
    private boolean mIsDeviceDefault;
    private boolean mIsSetCheckMark;
    private boolean mNeedRequestlayout;

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<CheckedTextView> {
        private int mCheckMarkId;
        private int mCheckMarkTintBlendModeId;
        private int mCheckMarkTintId;
        private int mCheckMarkTintModeId;
        private int mCheckedId;
        private boolean mPropertiesMapped = false;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(PropertyMapper propertyMapper) {
            this.mCheckMarkId = propertyMapper.mapObject("checkMark", 16843016);
            this.mCheckMarkTintId = propertyMapper.mapObject("checkMarkTint", 16843943);
            this.mCheckMarkTintBlendModeId = propertyMapper.mapObject("checkMarkTintBlendMode", 3);
            this.mCheckMarkTintModeId = propertyMapper.mapObject("checkMarkTintMode", 16843944);
            this.mCheckedId = propertyMapper.mapBoolean("checked", 16843014);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(CheckedTextView checkedTextView, PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readObject(this.mCheckMarkId, checkedTextView.getCheckMarkDrawable());
            propertyReader.readObject(this.mCheckMarkTintId, checkedTextView.getCheckMarkTintList());
            propertyReader.readObject(this.mCheckMarkTintBlendModeId, checkedTextView.getCheckMarkTintBlendMode());
            propertyReader.readObject(this.mCheckMarkTintModeId, checkedTextView.getCheckMarkTintMode());
            propertyReader.readBoolean(this.mCheckedId, checkedTextView.isChecked());
        }
    }

    public CheckedTextView(Context context) {
        this(context, null);
    }

    public CheckedTextView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16843720);
    }

    public CheckedTextView(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public CheckedTextView(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mCheckMarkTintList = null;
        this.mCheckMarkBlendMode = null;
        this.mHasCheckMarkTint = false;
        this.mHasCheckMarkTintMode = false;
        this.mCheckMarkGravity = Gravity.END;
        this.mIsSetCheckMark = false;
        this.mIsDeviceDefault = false;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.CheckedTextView, i, i2);
        saveAttributeDataForStyleable(context, R.styleable.CheckedTextView, attributeSet, obtainStyledAttributes, i, i2);
        Drawable drawable = obtainStyledAttributes.getDrawable(1);
        TypedValue typedValue = new TypedValue();
        getContext().getTheme().resolveAttribute(R.attr.parentIsDeviceDefault, typedValue, true);
        if (typedValue.data != 0) {
            this.mIsDeviceDefault = true;
        }
        this.mDrawablePadding = getContext().getResources().getDimensionPixelSize(R.dimen.tw_checkedtextview_padding);
        if (drawable != null) {
            setCheckMarkDrawable(drawable);
        }
        if (obtainStyledAttributes.hasValue(3)) {
            this.mCheckMarkBlendMode = Drawable.parseBlendMode(obtainStyledAttributes.getInt(3, -1), this.mCheckMarkBlendMode);
            this.mHasCheckMarkTintMode = true;
        }
        if (obtainStyledAttributes.hasValue(2)) {
            this.mCheckMarkTintList = obtainStyledAttributes.getColorStateList(2);
            this.mHasCheckMarkTint = true;
        }
        if (this.mIsDeviceDefault) {
            this.mIsSetCheckMark = true;
            this.mCheckMarkGravity = obtainStyledAttributes.getInt(4, Gravity.START);
        } else {
            this.mCheckMarkGravity = obtainStyledAttributes.getInt(4, Gravity.END);
        }
        setChecked(obtainStyledAttributes.getBoolean(0, false));
        obtainStyledAttributes.recycle();
        applyCheckMarkTint();
    }

    @Override // android.widget.Checkable
    public void toggle() {
        setChecked(!this.mChecked);
    }

    @Override // android.widget.Checkable
    @ViewDebug.ExportedProperty
    public boolean isChecked() {
        return this.mChecked;
    }

    @Override // android.widget.Checkable
    public void setChecked(boolean z) {
        if (this.mChecked != z) {
            this.mChecked = z;
            refreshDrawableState();
            if (Flags.triStateChecked()) {
                notifyViewAccessibilityStateChangedIfNeeded(8192);
            }
            notifyViewAccessibilityStateChangedIfNeeded(0);
        }
    }

    public void setCheckMarkDrawable(int i) {
        if (i == 0 || i != this.mCheckMarkResource) {
            setCheckMarkDrawableInternal(i != 0 ? getContext().getDrawable(i) : null, i);
        }
    }

    public void setCheckMarkDrawable(Drawable drawable) {
        setCheckMarkDrawableInternal(drawable, 0);
    }

    private void setCheckMarkDrawableInternal(Drawable drawable, int i) {
        Drawable drawable2 = this.mCheckMarkDrawable;
        if (drawable2 != null) {
            drawable2.setCallback(null);
            unscheduleDrawable(this.mCheckMarkDrawable);
        }
        this.mNeedRequestlayout = drawable != this.mCheckMarkDrawable;
        if (drawable != null) {
            drawable.setCallback(this);
            drawable.setVisible(getVisibility() == 0, false);
            drawable.setState(CHECKED_STATE_SET);
            setMinHeight(drawable.getIntrinsicHeight());
            this.mCheckMarkWidth = drawable.getIntrinsicWidth();
            if (this.mIsDeviceDefault) {
                this.mCheckMarkHeight = drawable.getIntrinsicHeight();
            }
            drawable.setState(getDrawableState());
        } else {
            this.mCheckMarkWidth = 0;
        }
        this.mCheckMarkDrawable = drawable;
        this.mCheckMarkResource = i;
        applyCheckMarkTint();
        resolvePadding();
    }

    public void setCheckMarkTintList(ColorStateList colorStateList) {
        this.mCheckMarkTintList = colorStateList;
        this.mHasCheckMarkTint = true;
        applyCheckMarkTint();
    }

    public ColorStateList getCheckMarkTintList() {
        return this.mCheckMarkTintList;
    }

    public void setCheckMarkTintMode(PorterDuff.Mode mode) {
        setCheckMarkTintBlendMode(mode != null ? BlendMode.fromValue(mode.nativeInt) : null);
    }

    public void setCheckMarkTintBlendMode(BlendMode blendMode) {
        this.mCheckMarkBlendMode = blendMode;
        this.mHasCheckMarkTintMode = true;
        applyCheckMarkTint();
    }

    public PorterDuff.Mode getCheckMarkTintMode() {
        BlendMode blendMode = this.mCheckMarkBlendMode;
        if (blendMode != null) {
            return BlendMode.blendModeToPorterDuffMode(blendMode);
        }
        return null;
    }

    public BlendMode getCheckMarkTintBlendMode() {
        return this.mCheckMarkBlendMode;
    }

    private void applyCheckMarkTint() {
        Drawable drawable = this.mCheckMarkDrawable;
        if (drawable != null) {
            if (this.mHasCheckMarkTint || this.mHasCheckMarkTintMode) {
                Drawable mutate = drawable.mutate();
                this.mCheckMarkDrawable = mutate;
                if (this.mHasCheckMarkTint) {
                    mutate.setTintList(this.mCheckMarkTintList);
                }
                if (this.mHasCheckMarkTintMode) {
                    this.mCheckMarkDrawable.setTintBlendMode(this.mCheckMarkBlendMode);
                }
                if (this.mCheckMarkDrawable.isStateful()) {
                    this.mCheckMarkDrawable.setState(getDrawableState());
                }
            }
        }
    }

    @Override // android.view.View
    @RemotableViewMethod
    public void setVisibility(int i) {
        super.setVisibility(i);
        Drawable drawable = this.mCheckMarkDrawable;
        if (drawable != null) {
            drawable.setVisible(i == 0, false);
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable = this.mCheckMarkDrawable;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
    }

    @Override // android.widget.TextView, android.view.View
    protected boolean verifyDrawable(Drawable drawable) {
        return drawable == this.mCheckMarkDrawable || super.verifyDrawable(drawable);
    }

    public Drawable getCheckMarkDrawable() {
        return this.mCheckMarkDrawable;
    }

    @Override // android.view.View
    protected void internalSetPadding(int i, int i2, int i3, int i4) {
        super.internalSetPadding(i, i2, i3, i4);
        setBasePadding(isCheckMarkAtStart());
    }

    @Override // android.widget.TextView, android.view.View
    public void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        updatePadding();
    }

    private void updatePadding() {
        int i;
        int i2;
        int i3;
        resetPaddingToInitialValues();
        if (this.mIsSetCheckMark) {
            if (this.mCheckMarkDrawable != null) {
                i2 = this.mCheckMarkWidth + this.mBasePadding;
                i3 = this.mDrawablePadding;
                i = i2 + i3;
            } else {
                i = this.mBasePadding;
            }
        } else if (this.mCheckMarkDrawable != null) {
            i2 = this.mCheckMarkWidth;
            i3 = this.mBasePadding;
            i = i2 + i3;
        } else {
            i = this.mBasePadding;
        }
        if (isCheckMarkAtStart()) {
            this.mNeedRequestlayout |= this.mPaddingLeft != i;
            this.mPaddingLeft = i;
        } else {
            this.mNeedRequestlayout |= this.mPaddingRight != i;
            this.mPaddingRight = i;
        }
        if (this.mNeedRequestlayout) {
            requestLayout();
            this.mNeedRequestlayout = false;
        }
    }

    private void setBasePadding(boolean z) {
        if (z) {
            this.mBasePadding = this.mPaddingLeft;
        } else {
            this.mBasePadding = this.mPaddingRight;
        }
    }

    private boolean isCheckMarkAtStart() {
        return (Gravity.getAbsoluteGravity(this.mCheckMarkGravity, getLayoutDirection()) & 7) == 3;
    }

    @Override // android.widget.TextView, android.view.View
    protected void onDraw(Canvas canvas) {
        int intrinsicHeight;
        int height;
        int i;
        int i2;
        super.onDraw(canvas);
        Drawable drawable = this.mCheckMarkDrawable;
        if (drawable != null) {
            int gravity = getGravity() & 112;
            if (this.mIsDeviceDefault) {
                intrinsicHeight = this.mCheckMarkHeight;
            } else {
                intrinsicHeight = drawable.getIntrinsicHeight();
            }
            if (gravity != 16) {
                height = gravity != 80 ? 0 : getHeight() - intrinsicHeight;
            } else {
                height = (getHeight() - intrinsicHeight) / 2;
            }
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
            if (isLayoutRtl()) {
                drawable.setBounds(this.mScrollX + i2, height, this.mScrollX + i, i3);
            } else {
                drawable.setBounds(i2, height, i, i3);
            }
            drawable.draw(canvas);
            Drawable background = getBackground();
            if (background != null) {
                background.setHotspotBounds(this.mScrollX + i2, height, this.mScrollX + i, i3);
            }
        }
    }

    @Override // android.widget.TextView, android.view.View
    protected int[] onCreateDrawableState(int i) {
        int[] onCreateDrawableState = super.onCreateDrawableState(i + 1);
        if (isChecked()) {
            mergeDrawableStates(onCreateDrawableState, CHECKED_STATE_SET);
        }
        return onCreateDrawableState;
    }

    @Override // android.widget.TextView, android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable = this.mCheckMarkDrawable;
        if (drawable != null && drawable.isStateful() && drawable.setState(getDrawableState())) {
            invalidateDrawable(drawable);
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void drawableHotspotChanged(float f, float f2) {
        super.drawableHotspotChanged(f, f2);
        Drawable drawable = this.mCheckMarkDrawable;
        if (drawable != null) {
            drawable.setHotspot(f, f2);
        }
    }

    @Override // android.widget.TextView, android.view.View
    public CharSequence getAccessibilityClassName() {
        return CheckedTextView.class.getName();
    }

    static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: android.widget.CheckedTextView.SavedState.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel);
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public SavedState[] newArray(int i) {
                return new SavedState[i];
            }
        };
        boolean checked;

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        private SavedState(Parcel parcel) {
            super(parcel);
            this.checked = ((Boolean) parcel.readValue(null)).booleanValue();
        }

        @Override // android.view.View.BaseSavedState, android.view.AbsSavedState, android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            super.writeToParcel(parcel, i);
            parcel.writeValue(Boolean.valueOf(this.checked));
        }

        public String toString() {
            return "CheckedTextView.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " checked=" + this.checked + "}";
        }
    }

    @Override // android.widget.TextView, android.view.View
    public Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.checked = isChecked();
        return savedState;
    }

    @Override // android.widget.TextView, android.view.View
    public void onRestoreInstanceState(Parcelable parcelable) {
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.getSuperState());
        setChecked(savedState.checked);
        requestLayout();
    }

    @Override // android.widget.TextView, android.view.View
    public void onInitializeAccessibilityEventInternal(AccessibilityEvent accessibilityEvent) {
        super.onInitializeAccessibilityEventInternal(accessibilityEvent);
        accessibilityEvent.setChecked(this.mChecked);
    }

    @Override // android.widget.TextView, android.view.View
    public void onInitializeAccessibilityNodeInfoInternal(AccessibilityNodeInfo accessibilityNodeInfo) {
        super.onInitializeAccessibilityNodeInfoInternal(accessibilityNodeInfo);
        accessibilityNodeInfo.setCheckable(true);
        if (Flags.triStateChecked()) {
            accessibilityNodeInfo.setChecked(this.mChecked ? 1 : 0);
        } else {
            accessibilityNodeInfo.setChecked(this.mChecked);
        }
    }

    @Override // android.widget.TextView, android.view.View
    protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) {
        super.encodeProperties(viewHierarchyEncoder);
        viewHierarchyEncoder.addProperty("text:checked", isChecked());
    }

    @Override // android.widget.TextView, android.view.View, android.graphics.drawable.Drawable.Callback
    public void invalidateDrawable(Drawable drawable) {
        super.invalidateDrawable(drawable);
        if (verifyDrawable(drawable)) {
            Rect bounds = drawable.getBounds();
            if (isLayoutRtl() && isSingleLine()) {
                invalidate(bounds.left, bounds.top, bounds.right, bounds.bottom);
            }
        }
    }
}
