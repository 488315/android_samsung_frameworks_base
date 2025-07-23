package android.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.RemotableViewMethod;
import android.view.View;
import android.view.ViewDebug;
import android.view.ViewHierarchyEncoder;
import android.view.ViewStructure;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.Flags;
import android.view.autofill.AutofillManager;
import android.view.autofill.AutofillValue;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import com.android.internal.R;

/* loaded from: classes5.dex */
public abstract class CompoundButton extends Button implements Checkable {
    private static final int[] CHECKED_STATE_SET = {16842912};
    private static final String LOG_TAG = "CompoundButton";
    private boolean mBroadcasting;
    private BlendMode mButtonBlendMode;
    private Drawable mButtonDrawable;
    private ColorStateList mButtonTintList;
    private boolean mChecked;
    private boolean mCheckedFromResource;
    private CharSequence mCustomStateDescription;
    private boolean mHasButtonBlendMode;
    private boolean mHasButtonTint;
    private OnCheckedChangeListener mOnCheckedChangeListener;
    private OnCheckedChangeListener mOnCheckedChangeWidgetListener;

    public interface OnCheckedChangeListener {
        void onCheckedChanged(CompoundButton compoundButton, boolean z);
    }

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<CompoundButton> {
        private int mButtonBlendModeId;
        private int mButtonId;
        private int mButtonTintId;
        private int mButtonTintModeId;
        private int mCheckedId;
        private boolean mPropertiesMapped = false;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(PropertyMapper propertyMapper) {
            this.mButtonId = propertyMapper.mapObject("button", 16843015);
            this.mButtonBlendModeId = propertyMapper.mapObject("buttonBlendMode", 3);
            this.mButtonTintId = propertyMapper.mapObject("buttonTint", 16843887);
            this.mButtonTintModeId = propertyMapper.mapObject("buttonTintMode", 16843888);
            this.mCheckedId = propertyMapper.mapBoolean("checked", 16843014);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(CompoundButton compoundButton, PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readObject(this.mButtonId, compoundButton.getButtonDrawable());
            propertyReader.readObject(this.mButtonBlendModeId, compoundButton.getButtonTintBlendMode());
            propertyReader.readObject(this.mButtonTintId, compoundButton.getButtonTintList());
            propertyReader.readObject(this.mButtonTintModeId, compoundButton.getButtonTintMode());
            propertyReader.readBoolean(this.mCheckedId, compoundButton.isChecked());
        }
    }

    public CompoundButton(Context context) {
        this(context, null);
    }

    public CompoundButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public CompoundButton(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public CompoundButton(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mButtonTintList = null;
        this.mButtonBlendMode = null;
        this.mHasButtonTint = false;
        this.mHasButtonBlendMode = false;
        this.mCheckedFromResource = false;
        this.mCustomStateDescription = null;
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.CompoundButton, i, i2);
        saveAttributeDataForStyleable(context, R.styleable.CompoundButton, attributeSet, obtainStyledAttributes, i, i2);
        Drawable drawable = obtainStyledAttributes.getDrawable(1);
        if (drawable != null) {
            lambda$setButtonIconAsync$1(drawable);
        }
        if (obtainStyledAttributes.hasValue(3)) {
            this.mButtonBlendMode = Drawable.parseBlendMode(obtainStyledAttributes.getInt(3, -1), this.mButtonBlendMode);
            this.mHasButtonBlendMode = true;
        }
        if (obtainStyledAttributes.hasValue(2)) {
            this.mButtonTintList = obtainStyledAttributes.getColorStateList(2);
            this.mHasButtonTint = true;
        }
        setChecked(obtainStyledAttributes.getBoolean(0, false));
        this.mCheckedFromResource = true;
        obtainStyledAttributes.recycle();
        applyButtonTint();
    }

    @Override // android.widget.Checkable
    public void toggle() {
        setChecked(!this.mChecked);
    }

    @Override // android.widget.TextView, android.view.View
    public boolean performClick() {
        toggle();
        boolean performClick = super.performClick();
        if (!performClick) {
            playSoundEffect(0);
        }
        return performClick;
    }

    @Override // android.widget.Checkable
    @ViewDebug.ExportedProperty
    public boolean isChecked() {
        return this.mChecked;
    }

    protected CharSequence getButtonStateDescription() {
        if (isChecked()) {
            return getResources().getString(R.string.checked);
        }
        return getResources().getString(R.string.not_checked);
    }

    @Override // android.view.View
    public void setStateDescription(CharSequence charSequence) {
        this.mCustomStateDescription = charSequence;
        if (charSequence == null) {
            setDefaultStateDescription();
        } else {
            super.setStateDescription(charSequence);
        }
    }

    protected void setDefaultStateDescription() {
        if (this.mCustomStateDescription == null) {
            super.setStateDescription(getButtonStateDescription());
        }
    }

    @Override // android.widget.Checkable
    public void setChecked(boolean z) {
        if (this.mChecked != z) {
            this.mCheckedFromResource = false;
            this.mChecked = z;
            refreshDrawableState();
            if (Flags.triStateChecked()) {
                notifyViewAccessibilityStateChangedIfNeeded(8192);
            }
            if (this.mBroadcasting) {
                setDefaultStateDescription();
                return;
            }
            this.mBroadcasting = true;
            OnCheckedChangeListener onCheckedChangeListener = this.mOnCheckedChangeListener;
            if (onCheckedChangeListener != null) {
                onCheckedChangeListener.onCheckedChanged(this, this.mChecked);
            }
            OnCheckedChangeListener onCheckedChangeListener2 = this.mOnCheckedChangeWidgetListener;
            if (onCheckedChangeListener2 != null) {
                onCheckedChangeListener2.onCheckedChanged(this, this.mChecked);
            }
            AutofillManager autofillManager = (AutofillManager) this.mContext.getSystemService(AutofillManager.class);
            if (autofillManager != null) {
                autofillManager.notifyValueChanged(this);
            }
            this.mBroadcasting = false;
        }
        setDefaultStateDescription();
    }

    public void setOnCheckedChangeListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.mOnCheckedChangeListener = onCheckedChangeListener;
    }

    void setOnCheckedChangeWidgetListener(OnCheckedChangeListener onCheckedChangeListener) {
        this.mOnCheckedChangeWidgetListener = onCheckedChangeListener;
    }

    @RemotableViewMethod(asyncImpl = "setButtonDrawableAsync")
    public void setButtonDrawable(int i) {
        lambda$setButtonIconAsync$1(i != 0 ? getContext().getDrawable(i) : null);
    }

    public Runnable setButtonDrawableAsync(int i) {
        final Drawable drawable = i == 0 ? null : getContext().getDrawable(i);
        return new Runnable() { // from class: android.widget.CompoundButton$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                CompoundButton.this.lambda$setButtonDrawableAsync$0(drawable);
            }
        };
    }

    /* renamed from: setButtonDrawable, reason: merged with bridge method [inline-methods] and merged with bridge method [inline-methods] */
    public void lambda$setButtonIconAsync$1(Drawable drawable) {
        Drawable drawable2 = this.mButtonDrawable;
        if (drawable2 != drawable) {
            if (drawable2 != null) {
                drawable2.setCallback(null);
                unscheduleDrawable(this.mButtonDrawable);
            }
            this.mButtonDrawable = drawable;
            if (drawable != null) {
                drawable.setCallback(this);
                drawable.setLayoutDirection(getLayoutDirection());
                if (drawable.isStateful()) {
                    drawable.setState(getDrawableState());
                }
                drawable.setVisible(getVisibility() == 0, false);
                setMinHeight(drawable.getIntrinsicHeight());
                applyButtonTint();
            }
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void onResolveDrawables(int i) {
        super.onResolveDrawables(i);
        Drawable drawable = this.mButtonDrawable;
        if (drawable != null) {
            drawable.setLayoutDirection(i);
        }
    }

    public Drawable getButtonDrawable() {
        return this.mButtonDrawable;
    }

    @RemotableViewMethod(asyncImpl = "setButtonIconAsync")
    public void setButtonIcon(Icon icon) {
        lambda$setButtonIconAsync$1(icon == null ? null : icon.loadDrawable(getContext()));
    }

    public Runnable setButtonIconAsync(Icon icon) {
        final Drawable loadDrawable = icon == null ? null : icon.loadDrawable(getContext());
        return new Runnable() { // from class: android.widget.CompoundButton$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                CompoundButton.this.lambda$setButtonIconAsync$1(loadDrawable);
            }
        };
    }

    @RemotableViewMethod
    public void setButtonTintList(ColorStateList colorStateList) {
        this.mButtonTintList = colorStateList;
        this.mHasButtonTint = true;
        applyButtonTint();
    }

    public ColorStateList getButtonTintList() {
        return this.mButtonTintList;
    }

    public void setButtonTintMode(PorterDuff.Mode mode) {
        setButtonTintBlendMode(mode != null ? BlendMode.fromValue(mode.nativeInt) : null);
    }

    @RemotableViewMethod
    public void setButtonTintBlendMode(BlendMode blendMode) {
        this.mButtonBlendMode = blendMode;
        this.mHasButtonBlendMode = true;
        applyButtonTint();
    }

    public PorterDuff.Mode getButtonTintMode() {
        BlendMode blendMode = this.mButtonBlendMode;
        if (blendMode != null) {
            return BlendMode.blendModeToPorterDuffMode(blendMode);
        }
        return null;
    }

    public BlendMode getButtonTintBlendMode() {
        return this.mButtonBlendMode;
    }

    private void applyButtonTint() {
        Drawable drawable = this.mButtonDrawable;
        if (drawable != null) {
            if (this.mHasButtonTint || this.mHasButtonBlendMode) {
                Drawable mutate = drawable.mutate();
                this.mButtonDrawable = mutate;
                if (this.mHasButtonTint) {
                    mutate.setTintList(this.mButtonTintList);
                }
                if (this.mHasButtonBlendMode) {
                    this.mButtonDrawable.setTintBlendMode(this.mButtonBlendMode);
                }
                if (this.mButtonDrawable.isStateful()) {
                    this.mButtonDrawable.setState(getDrawableState());
                }
            }
        }
    }

    @Override // android.widget.Button, android.widget.TextView, android.view.View
    public CharSequence getAccessibilityClassName() {
        return CompoundButton.class.getName();
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

    @Override // android.widget.TextView
    public int getCompoundPaddingLeft() {
        Drawable drawable;
        int compoundPaddingLeft = super.getCompoundPaddingLeft();
        return (isLayoutRtl() || (drawable = this.mButtonDrawable) == null) ? compoundPaddingLeft : compoundPaddingLeft + drawable.getIntrinsicWidth();
    }

    @Override // android.widget.TextView
    public int getCompoundPaddingRight() {
        Drawable drawable;
        int compoundPaddingRight = super.getCompoundPaddingRight();
        return (!isLayoutRtl() || (drawable = this.mButtonDrawable) == null) ? compoundPaddingRight : compoundPaddingRight + drawable.getIntrinsicWidth();
    }

    @Override // android.widget.TextView
    public int getHorizontalOffsetForDrawables() {
        Drawable drawable = this.mButtonDrawable;
        if (drawable != null) {
            return drawable.getIntrinsicWidth();
        }
        return 0;
    }

    @Override // android.widget.TextView, android.view.View
    protected void onDraw(Canvas canvas) {
        int height;
        Drawable drawable = this.mButtonDrawable;
        if (drawable != null) {
            int gravity = getGravity() & 112;
            int intrinsicHeight = drawable.getIntrinsicHeight();
            int intrinsicWidth = drawable.getIntrinsicWidth();
            if (gravity != 16) {
                height = gravity != 80 ? 0 : getHeight() - intrinsicHeight;
            } else {
                height = (getHeight() - intrinsicHeight) / 2;
            }
            int i = intrinsicHeight + height;
            int width = isLayoutRtl() ? getWidth() - intrinsicWidth : 0;
            if (isLayoutRtl()) {
                intrinsicWidth = getWidth();
            }
            drawable.setBounds(width, height, intrinsicWidth, i);
            Drawable background = getBackground();
            if (background != null) {
                background.setHotspotBounds(width, height, intrinsicWidth, i);
            }
        }
        super.onDraw(canvas);
        if (drawable != null) {
            int i2 = this.mScrollX;
            int i3 = this.mScrollY;
            if (i2 == 0 && i3 == 0) {
                drawable.draw(canvas);
                return;
            }
            canvas.translate(i2, i3);
            drawable.draw(canvas);
            canvas.translate(-i2, -i3);
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
        Drawable drawable = this.mButtonDrawable;
        if (drawable != null && drawable.isStateful() && drawable.setState(getDrawableState())) {
            invalidateDrawable(drawable);
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void drawableHotspotChanged(float f, float f2) {
        super.drawableHotspotChanged(f, f2);
        Drawable drawable = this.mButtonDrawable;
        if (drawable != null) {
            drawable.setHotspot(f, f2);
        }
    }

    @Override // android.widget.TextView, android.view.View
    protected boolean verifyDrawable(Drawable drawable) {
        return super.verifyDrawable(drawable) || drawable == this.mButtonDrawable;
    }

    @Override // android.widget.TextView, android.view.View
    public void jumpDrawablesToCurrentState() {
        super.jumpDrawablesToCurrentState();
        Drawable drawable = this.mButtonDrawable;
        if (drawable != null) {
            drawable.jumpToCurrentState();
        }
    }

    static class SavedState extends View.BaseSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.Creator<SavedState>() { // from class: android.widget.CompoundButton.SavedState.1
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
            return "CompoundButton.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " checked=" + this.checked + "}";
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
    protected void encodeProperties(ViewHierarchyEncoder viewHierarchyEncoder) {
        super.encodeProperties(viewHierarchyEncoder);
        viewHierarchyEncoder.addProperty("checked", isChecked());
    }

    @Override // android.widget.TextView, android.view.View
    protected void onProvideStructure(ViewStructure viewStructure, int i, int i2) {
        super.onProvideStructure(viewStructure, i, i2);
        if (i == 1) {
            viewStructure.setDataIsSensitive(!this.mCheckedFromResource);
        }
    }

    @Override // android.widget.TextView, android.view.View
    public void autofill(AutofillValue autofillValue) {
        if (isEnabled()) {
            if (!autofillValue.isToggle()) {
                Log.w(LOG_TAG, autofillValue + " could not be autofilled into " + this);
                return;
            }
            setChecked(autofillValue.getToggleValue());
        }
    }

    @Override // android.widget.TextView, android.view.View
    public int getAutofillType() {
        return isEnabled() ? 2 : 0;
    }

    @Override // android.widget.TextView, android.view.View
    public AutofillValue getAutofillValue() {
        if (isEnabled()) {
            return AutofillValue.forToggle(isChecked());
        }
        return null;
    }
}
