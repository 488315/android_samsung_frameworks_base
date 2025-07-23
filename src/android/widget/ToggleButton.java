package android.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.util.AttributeSet;
import android.view.inspector.InspectionCompanion;
import android.view.inspector.PropertyMapper;
import android.view.inspector.PropertyReader;
import com.android.internal.R;

/* loaded from: classes5.dex */
public class ToggleButton extends CompoundButton {
    private static final int NO_ALPHA = 255;
    private float mDisabledAlpha;
    private Drawable mIndicatorDrawable;
    private CharSequence mTextOff;
    private CharSequence mTextOn;

    public final class InspectionCompanion implements android.view.inspector.InspectionCompanion<ToggleButton> {
        private int mDisabledAlphaId;
        private boolean mPropertiesMapped = false;
        private int mTextOffId;
        private int mTextOnId;

        @Override // android.view.inspector.InspectionCompanion
        public void mapProperties(PropertyMapper propertyMapper) {
            this.mDisabledAlphaId = propertyMapper.mapFloat("disabledAlpha", 16842803);
            this.mTextOffId = propertyMapper.mapObject("textOff", 16843045);
            this.mTextOnId = propertyMapper.mapObject("textOn", 16843044);
            this.mPropertiesMapped = true;
        }

        @Override // android.view.inspector.InspectionCompanion
        public void readProperties(ToggleButton toggleButton, PropertyReader propertyReader) {
            if (!this.mPropertiesMapped) {
                throw new InspectionCompanion.UninitializedPropertyMapException();
            }
            propertyReader.readFloat(this.mDisabledAlphaId, toggleButton.getDisabledAlpha());
            propertyReader.readObject(this.mTextOffId, toggleButton.getTextOff());
            propertyReader.readObject(this.mTextOnId, toggleButton.getTextOn());
        }
    }

    public ToggleButton(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R.styleable.ToggleButton, i, i2);
        saveAttributeDataForStyleable(context, R.styleable.ToggleButton, attributeSet, obtainStyledAttributes, i, i2);
        this.mTextOn = obtainStyledAttributes.getText(1);
        this.mTextOff = obtainStyledAttributes.getText(2);
        this.mDisabledAlpha = obtainStyledAttributes.getFloat(0, 0.5f);
        syncTextState();
        setDefaultStateDescription();
        obtainStyledAttributes.recycle();
    }

    public ToggleButton(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public ToggleButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 16842827);
    }

    public ToggleButton(Context context) {
        this(context, null);
    }

    @Override // android.widget.CompoundButton, android.widget.Checkable
    public void setChecked(boolean z) {
        super.setChecked(z);
        syncTextState();
    }

    private void syncTextState() {
        CharSequence charSequence;
        CharSequence charSequence2;
        boolean isChecked = isChecked();
        if (isChecked && (charSequence2 = this.mTextOn) != null) {
            lambda$setTextAsync$0(charSequence2);
        } else {
            if (isChecked || (charSequence = this.mTextOff) == null) {
                return;
            }
            lambda$setTextAsync$0(charSequence);
        }
    }

    public CharSequence getTextOn() {
        return this.mTextOn;
    }

    public void setTextOn(CharSequence charSequence) {
        this.mTextOn = charSequence;
        setDefaultStateDescription();
    }

    public CharSequence getTextOff() {
        return this.mTextOff;
    }

    public void setTextOff(CharSequence charSequence) {
        this.mTextOff = charSequence;
        setDefaultStateDescription();
    }

    public float getDisabledAlpha() {
        return this.mDisabledAlpha;
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        updateReferenceToIndicatorDrawable(getBackground());
    }

    @Override // android.view.View
    public void setBackgroundDrawable(Drawable drawable) {
        super.setBackgroundDrawable(drawable);
        updateReferenceToIndicatorDrawable(drawable);
    }

    private void updateReferenceToIndicatorDrawable(Drawable drawable) {
        if (drawable instanceof LayerDrawable) {
            this.mIndicatorDrawable = ((LayerDrawable) drawable).findDrawableByLayerId(16908311);
        } else {
            this.mIndicatorDrawable = null;
        }
    }

    @Override // android.widget.CompoundButton, android.widget.TextView, android.view.View
    protected void drawableStateChanged() {
        super.drawableStateChanged();
        Drawable drawable = this.mIndicatorDrawable;
        if (drawable != null) {
            drawable.setAlpha(isEnabled() ? 255 : (int) (this.mDisabledAlpha * 255.0f));
        }
    }

    @Override // android.widget.CompoundButton, android.widget.Button, android.widget.TextView, android.view.View
    public CharSequence getAccessibilityClassName() {
        return ToggleButton.class.getName();
    }

    @Override // android.widget.CompoundButton
    protected CharSequence getButtonStateDescription() {
        if (isChecked()) {
            CharSequence charSequence = this.mTextOn;
            return charSequence == null ? getResources().getString(R.string.capital_on) : charSequence;
        }
        CharSequence charSequence2 = this.mTextOff;
        return charSequence2 == null ? getResources().getString(R.string.capital_off) : charSequence2;
    }
}
