package com.google.android.material.textfield;

import android.R;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.TextPaint;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.util.Log;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStructure;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.DrawableUtils;
import androidx.appcompat.widget.ResourceManagerInternal;
import androidx.appcompat.widget.TintTypedArray;
import androidx.constraintlayout.core.widgets.ConstraintWidget$$ExternalSyntheticOutline0;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import androidx.core.text.BidiFormatter;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.ViewCompat$$ExternalSyntheticLambda0;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.view.AbsSavedState;
import androidx.transition.Fade;
import androidx.transition.TransitionManager;
import com.google.android.material.R$styleable;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.CheckableImageButton;
import com.google.android.material.internal.CollapsingTextHelper;
import com.google.android.material.internal.DescendantOffsetUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.RelativeCornerSize;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.textfield.EndCompoundLayout;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.WeakHashMap;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class TextInputLayout extends LinearLayout {
    public static final int[][] EDIT_TEXT_BACKGROUND_RIPPLE_STATE = {new int[]{R.attr.state_pressed}, new int[0]};
    public ValueAnimator animator;
    public boolean areCornerRadiiRtl;
    public MaterialShapeDrawable boxBackground;
    public boolean boxBackgroundApplied;
    public int boxBackgroundColor;
    public int boxBackgroundMode;
    public int boxCollapsedPaddingTopPx;
    public final int boxLabelCutoutPaddingPx;
    public int boxStrokeColor;
    public final int boxStrokeWidthDefaultPx;
    public final int boxStrokeWidthFocusedPx;
    public int boxStrokeWidthPx;
    public MaterialShapeDrawable boxUnderlineDefault;
    public MaterialShapeDrawable boxUnderlineFocused;
    public final CollapsingTextHelper collapsingTextHelper;
    public boolean counterEnabled;
    public int counterMaxLength;
    public int counterOverflowTextAppearance;
    public ColorStateList counterOverflowTextColor;
    public boolean counterOverflowed;
    public int counterTextAppearance;
    public ColorStateList counterTextColor;
    public AppCompatTextView counterView;
    public final int defaultFilledBackgroundColor;
    public ColorStateList defaultHintTextColor;
    public int defaultStrokeColor;
    public int disabledColor;
    public final int disabledFilledBackgroundColor;
    public EditText editText;
    public final LinkedHashSet editTextAttachedListeners;
    public Drawable endDummyDrawable;
    public int endDummyDrawableWidth;
    public final EndCompoundLayout endLayout;
    public final boolean expandedHintEnabled;
    public StateListDrawable filledDropDownMenuBackground;
    public final int focusedFilledBackgroundColor;
    public int focusedStrokeColor;
    public ColorStateList focusedTextColor;
    public CharSequence hint;
    public final boolean hintAnimationEnabled;
    public final boolean hintEnabled;
    public boolean hintExpanded;
    public final int hoveredFilledBackgroundColor;
    public int hoveredStrokeColor;
    public boolean inDrawableStateChanged;
    public final IndicatorViewController indicatorViewController;
    public final FrameLayout inputFrame;
    public boolean isProvidingHint;
    public final ViewCompat$$ExternalSyntheticLambda0 lengthCounter;
    public int maxEms;
    public int maxWidth;
    public int minEms;
    public int minWidth;
    public Drawable originalEditTextEndDrawable;
    public CharSequence originalHint;
    public MaterialShapeDrawable outlinedDropDownMenuBackground;
    public boolean placeholderEnabled;
    public Fade placeholderFadeIn;
    public Fade placeholderFadeOut;
    public CharSequence placeholderText;
    public int placeholderTextAppearance;
    public ColorStateList placeholderTextColor;
    public AppCompatTextView placeholderTextView;
    public boolean restoringSavedState;
    public ShapeAppearanceModel shapeAppearanceModel;
    public Drawable startDummyDrawable;
    public int startDummyDrawableWidth;
    public final StartCompoundLayout startLayout;
    public ColorStateList strokeErrorColor;
    public final Rect tmpBoundsRect;
    public final Rect tmpRect;
    public final RectF tmpRectF;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AccessibilityDelegate extends AccessibilityDelegateCompat {
        public final TextInputLayout layout;

        public AccessibilityDelegate(TextInputLayout textInputLayout) {
            this.layout = textInputLayout;
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            AppCompatTextView appCompatTextView;
            View.AccessibilityDelegate accessibilityDelegate = this.mOriginalDelegate;
            AccessibilityNodeInfo accessibilityNodeInfo = accessibilityNodeInfoCompat.mInfo;
            accessibilityDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
            TextInputLayout textInputLayout = this.layout;
            EditText editText = textInputLayout.editText;
            CharSequence charSequence = null;
            CharSequence text = editText != null ? editText.getText() : null;
            CharSequence charSequence2 = textInputLayout.hintEnabled ? textInputLayout.hint : null;
            IndicatorViewController indicatorViewController = textInputLayout.indicatorViewController;
            CharSequence charSequence3 = indicatorViewController.errorEnabled ? indicatorViewController.errorText : null;
            CharSequence charSequence4 = textInputLayout.placeholderEnabled ? textInputLayout.placeholderText : null;
            int i = textInputLayout.counterMaxLength;
            if (textInputLayout.counterEnabled && textInputLayout.counterOverflowed && (appCompatTextView = textInputLayout.counterView) != null) {
                charSequence = appCompatTextView.getContentDescription();
            }
            boolean z = !TextUtils.isEmpty(text);
            boolean z2 = !TextUtils.isEmpty(charSequence2);
            boolean z3 = !textInputLayout.hintExpanded;
            boolean z4 = !TextUtils.isEmpty(charSequence3);
            boolean z5 = z4 || !TextUtils.isEmpty(charSequence);
            String charSequence5 = z2 ? charSequence2.toString() : "";
            StartCompoundLayout startCompoundLayout = textInputLayout.startLayout;
            if (startCompoundLayout.prefixTextView.getVisibility() == 0) {
                accessibilityNodeInfo.setLabelFor(startCompoundLayout.prefixTextView);
                accessibilityNodeInfo.setTraversalAfter(startCompoundLayout.prefixTextView);
            } else {
                accessibilityNodeInfo.setTraversalAfter(startCompoundLayout.startIconView);
            }
            if (z) {
                accessibilityNodeInfoCompat.setText(text);
            } else if (!TextUtils.isEmpty(charSequence5)) {
                accessibilityNodeInfoCompat.setText(charSequence5);
                if (z3 && charSequence4 != null) {
                    accessibilityNodeInfoCompat.setText(charSequence5 + ", " + ((Object) charSequence4));
                }
            } else if (charSequence4 != null) {
                accessibilityNodeInfoCompat.setText(charSequence4);
            }
            if (!TextUtils.isEmpty(charSequence5)) {
                accessibilityNodeInfo.setHintText(charSequence5);
                accessibilityNodeInfo.setShowingHintText(!z);
            }
            if (text == null || text.length() != i) {
                i = -1;
            }
            accessibilityNodeInfo.setMaxTextLength(i);
            if (z5) {
                if (!z4) {
                    charSequence3 = charSequence;
                }
                accessibilityNodeInfo.setError(charSequence3);
            }
            AppCompatTextView appCompatTextView2 = textInputLayout.indicatorViewController.helperTextView;
            if (appCompatTextView2 != null) {
                accessibilityNodeInfo.setLabelFor(appCompatTextView2);
            }
            textInputLayout.endLayout.getEndIconDelegate().onInitializeAccessibilityNodeInfo(accessibilityNodeInfoCompat);
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public final void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            super.onPopulateAccessibilityEvent(view, accessibilityEvent);
            this.layout.endLayout.getEndIconDelegate().onPopulateAccessibilityEvent(accessibilityEvent);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class SavedState extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator() { // from class: com.google.android.material.textfield.TextInputLayout.SavedState.1
            @Override // android.os.Parcelable.ClassLoaderCreator
            public final Object createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new SavedState[i];
            }

            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                return new SavedState(parcel, null);
            }
        };
        public CharSequence error;
        public boolean isEndIconChecked;

        public SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public final String toString() {
            return "TextInputLayout.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " error=" + ((Object) this.error) + "}";
        }

        @Override // androidx.customview.view.AbsSavedState, android.os.Parcelable
        public final void writeToParcel(Parcel parcel, int i) {
            parcel.writeParcelable(this.mSuperState, i);
            TextUtils.writeToParcel(this.error, parcel, i);
            parcel.writeInt(this.isEndIconChecked ? 1 : 0);
        }

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.error = (CharSequence) TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.isEndIconChecked = parcel.readInt() == 1;
        }
    }

    public TextInputLayout(Context context) {
        this(context, null);
    }

    public static void recursiveSetEnabled(ViewGroup viewGroup, boolean z) {
        int childCount = viewGroup.getChildCount();
        for (int i = 0; i < childCount; i++) {
            View childAt = viewGroup.getChildAt(i);
            childAt.setEnabled(z);
            if (childAt instanceof ViewGroup) {
                recursiveSetEnabled((ViewGroup) childAt, z);
            }
        }
    }

    @Override // android.view.ViewGroup
    public final void addView(View view, int i, ViewGroup.LayoutParams layoutParams) {
        if (!(view instanceof EditText)) {
            super.addView(view, i, layoutParams);
            return;
        }
        FrameLayout.LayoutParams layoutParams2 = new FrameLayout.LayoutParams(layoutParams);
        layoutParams2.gravity = (layoutParams2.gravity & (-113)) | 16;
        this.inputFrame.addView(view, layoutParams2);
        this.inputFrame.setLayoutParams(layoutParams);
        updateInputLayoutMargins();
        EditText editText = (EditText) view;
        if (this.editText != null) {
            throw new IllegalArgumentException("We already have an EditText, can only have one");
        }
        if (this.endLayout.endIconMode != 3 && !(editText instanceof TextInputEditText)) {
            Log.i("TextInputLayout", "EditText added is not a TextInputEditText. Please switch to using that class instead.");
        }
        this.editText = editText;
        int i2 = this.minEms;
        if (i2 != -1) {
            this.minEms = i2;
            if (editText != null && i2 != -1) {
                editText.setMinEms(i2);
            }
        } else {
            int i3 = this.minWidth;
            this.minWidth = i3;
            if (editText != null && i3 != -1) {
                editText.setMinWidth(i3);
            }
        }
        int i4 = this.maxEms;
        if (i4 != -1) {
            this.maxEms = i4;
            EditText editText2 = this.editText;
            if (editText2 != null && i4 != -1) {
                editText2.setMaxEms(i4);
            }
        } else {
            int i5 = this.maxWidth;
            this.maxWidth = i5;
            EditText editText3 = this.editText;
            if (editText3 != null && i5 != -1) {
                editText3.setMaxWidth(i5);
            }
        }
        this.boxBackgroundApplied = false;
        onApplyBoxBackgroundMode();
        AccessibilityDelegate accessibilityDelegate = new AccessibilityDelegate(this);
        EditText editText4 = this.editText;
        if (editText4 != null) {
            ViewCompat.setAccessibilityDelegate(editText4, accessibilityDelegate);
        }
        CollapsingTextHelper collapsingTextHelper = this.collapsingTextHelper;
        Typeface typeface = this.editText.getTypeface();
        boolean collapsedTypefaceInternal = collapsingTextHelper.setCollapsedTypefaceInternal(typeface);
        boolean expandedTypefaceInternal = collapsingTextHelper.setExpandedTypefaceInternal(typeface);
        if (collapsedTypefaceInternal || expandedTypefaceInternal) {
            collapsingTextHelper.recalculate(false);
        }
        CollapsingTextHelper collapsingTextHelper2 = this.collapsingTextHelper;
        float textSize = this.editText.getTextSize();
        if (collapsingTextHelper2.expandedTextSize != textSize) {
            collapsingTextHelper2.expandedTextSize = textSize;
            collapsingTextHelper2.recalculate(false);
        }
        CollapsingTextHelper collapsingTextHelper3 = this.collapsingTextHelper;
        float letterSpacing = this.editText.getLetterSpacing();
        if (collapsingTextHelper3.expandedLetterSpacing != letterSpacing) {
            collapsingTextHelper3.expandedLetterSpacing = letterSpacing;
            collapsingTextHelper3.recalculate(false);
        }
        int gravity = this.editText.getGravity();
        CollapsingTextHelper collapsingTextHelper4 = this.collapsingTextHelper;
        int i6 = (gravity & (-113)) | 48;
        if (collapsingTextHelper4.collapsedTextGravity != i6) {
            collapsingTextHelper4.collapsedTextGravity = i6;
            collapsingTextHelper4.recalculate(false);
        }
        CollapsingTextHelper collapsingTextHelper5 = this.collapsingTextHelper;
        if (collapsingTextHelper5.expandedTextGravity != gravity) {
            collapsingTextHelper5.expandedTextGravity = gravity;
            collapsingTextHelper5.recalculate(false);
        }
        this.editText.addTextChangedListener(new TextWatcher() { // from class: com.google.android.material.textfield.TextInputLayout.1
            @Override // android.text.TextWatcher
            public final void afterTextChanged(Editable editable) {
                TextInputLayout.this.updateLabelState(!r0.restoringSavedState, false);
                TextInputLayout textInputLayout = TextInputLayout.this;
                if (textInputLayout.counterEnabled) {
                    textInputLayout.updateCounter(editable);
                }
                TextInputLayout textInputLayout2 = TextInputLayout.this;
                if (textInputLayout2.placeholderEnabled) {
                    textInputLayout2.updatePlaceholderText(editable);
                }
            }

            @Override // android.text.TextWatcher
            public final void beforeTextChanged(CharSequence charSequence, int i7, int i8, int i9) {
            }

            @Override // android.text.TextWatcher
            public final void onTextChanged(CharSequence charSequence, int i7, int i8, int i9) {
            }
        });
        if (this.defaultHintTextColor == null) {
            this.defaultHintTextColor = this.editText.getHintTextColors();
        }
        if (this.hintEnabled) {
            if (TextUtils.isEmpty(this.hint)) {
                CharSequence hint = this.editText.getHint();
                this.originalHint = hint;
                setHint(hint);
                this.editText.setHint((CharSequence) null);
            }
            this.isProvidingHint = true;
        }
        if (this.counterView != null) {
            updateCounter(this.editText.getText());
        }
        updateEditTextBackground();
        this.indicatorViewController.adjustIndicatorPadding();
        this.startLayout.bringToFront();
        this.endLayout.bringToFront();
        Iterator it = this.editTextAttachedListeners.iterator();
        while (it.hasNext()) {
            ((EndCompoundLayout.C43862) it.next()).onEditTextAttached(this);
        }
        this.endLayout.updateSuffixTextViewPadding();
        if (!isEnabled()) {
            editText.setEnabled(false);
        }
        updateLabelState(false, true);
    }

    public void animateToExpansionFraction(float f) {
        if (this.collapsingTextHelper.expandedFraction == f) {
            return;
        }
        if (this.animator == null) {
            ValueAnimator valueAnimator = new ValueAnimator();
            this.animator = valueAnimator;
            valueAnimator.setInterpolator(AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR);
            this.animator.setDuration(167L);
            this.animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.textfield.TextInputLayout.4
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    TextInputLayout.this.collapsingTextHelper.setExpansionFraction(((Float) valueAnimator2.getAnimatedValue()).floatValue());
                }
            });
        }
        this.animator.setFloatValues(this.collapsingTextHelper.expandedFraction, f);
        this.animator.start();
    }

    /* JADX WARN: Removed duplicated region for block: B:18:0x002a  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0075  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void applyBoxAttributes() {
        boolean z;
        MaterialShapeDrawable materialShapeDrawable;
        MaterialShapeDrawable materialShapeDrawable2 = this.boxBackground;
        if (materialShapeDrawable2 == null) {
            return;
        }
        ShapeAppearanceModel shapeAppearanceModel = materialShapeDrawable2.drawableState.shapeAppearanceModel;
        ShapeAppearanceModel shapeAppearanceModel2 = this.shapeAppearanceModel;
        if (shapeAppearanceModel != shapeAppearanceModel2) {
            materialShapeDrawable2.setShapeAppearanceModel(shapeAppearanceModel2);
        }
        boolean z2 = false;
        if (this.boxBackgroundMode == 2) {
            if (this.boxStrokeWidthPx > -1 && this.boxStrokeColor != 0) {
                z = true;
                if (z) {
                    MaterialShapeDrawable materialShapeDrawable3 = this.boxBackground;
                    float f = this.boxStrokeWidthPx;
                    int i = this.boxStrokeColor;
                    materialShapeDrawable3.drawableState.strokeWidth = f;
                    materialShapeDrawable3.invalidateSelf();
                    materialShapeDrawable3.setStrokeColor(ColorStateList.valueOf(i));
                }
                int i2 = this.boxBackgroundColor;
                if (this.boxBackgroundMode == 1) {
                    i2 = ColorUtils.compositeColors(this.boxBackgroundColor, MaterialColors.getColor(com.android.systemui.R.attr.colorSurface, getContext(), 0));
                }
                this.boxBackgroundColor = i2;
                this.boxBackground.setFillColor(ColorStateList.valueOf(i2));
                materialShapeDrawable = this.boxUnderlineDefault;
                if (materialShapeDrawable != null && this.boxUnderlineFocused != null) {
                    if (this.boxStrokeWidthPx > -1 && this.boxStrokeColor != 0) {
                        z2 = true;
                    }
                    if (z2) {
                        materialShapeDrawable.setFillColor(this.editText.isFocused() ? ColorStateList.valueOf(this.defaultStrokeColor) : ColorStateList.valueOf(this.boxStrokeColor));
                        this.boxUnderlineFocused.setFillColor(ColorStateList.valueOf(this.boxStrokeColor));
                    }
                    invalidate();
                }
                updateEditTextBoxBackgroundIfNeeded();
            }
        }
        z = false;
        if (z) {
        }
        int i22 = this.boxBackgroundColor;
        if (this.boxBackgroundMode == 1) {
        }
        this.boxBackgroundColor = i22;
        this.boxBackground.setFillColor(ColorStateList.valueOf(i22));
        materialShapeDrawable = this.boxUnderlineDefault;
        if (materialShapeDrawable != null) {
            if (this.boxStrokeWidthPx > -1) {
                z2 = true;
            }
            if (z2) {
            }
            invalidate();
        }
        updateEditTextBoxBackgroundIfNeeded();
    }

    public final int calculateLabelMarginTop() {
        float collapsedTextHeight;
        if (!this.hintEnabled) {
            return 0;
        }
        int i = this.boxBackgroundMode;
        if (i == 0) {
            collapsedTextHeight = this.collapsingTextHelper.getCollapsedTextHeight();
        } else {
            if (i != 2) {
                return 0;
            }
            collapsedTextHeight = this.collapsingTextHelper.getCollapsedTextHeight() / 2.0f;
        }
        return (int) collapsedTextHeight;
    }

    public final boolean cutoutEnabled() {
        return this.hintEnabled && !TextUtils.isEmpty(this.hint) && (this.boxBackground instanceof CutoutDrawable);
    }

    public boolean cutoutIsOpen() {
        return cutoutEnabled() && (((CutoutDrawable) this.boxBackground).cutoutBounds.isEmpty() ^ true);
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchProvideAutofillStructure(ViewStructure viewStructure, int i) {
        EditText editText = this.editText;
        if (editText == null) {
            super.dispatchProvideAutofillStructure(viewStructure, i);
            return;
        }
        if (this.originalHint != null) {
            boolean z = this.isProvidingHint;
            this.isProvidingHint = false;
            CharSequence hint = editText.getHint();
            this.editText.setHint(this.originalHint);
            try {
                super.dispatchProvideAutofillStructure(viewStructure, i);
                return;
            } finally {
                this.editText.setHint(hint);
                this.isProvidingHint = z;
            }
        }
        viewStructure.setAutofillId(getAutofillId());
        onProvideAutofillStructure(viewStructure, i);
        onProvideAutofillVirtualStructure(viewStructure, i);
        viewStructure.setChildCount(this.inputFrame.getChildCount());
        for (int i2 = 0; i2 < this.inputFrame.getChildCount(); i2++) {
            View childAt = this.inputFrame.getChildAt(i2);
            ViewStructure newChild = viewStructure.newChild(i2);
            childAt.dispatchProvideAutofillStructure(newChild, i);
            if (childAt == this.editText) {
                newChild.setHint(this.hintEnabled ? this.hint : null);
            }
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void dispatchRestoreInstanceState(SparseArray sparseArray) {
        this.restoringSavedState = true;
        super.dispatchRestoreInstanceState(sparseArray);
        this.restoringSavedState = false;
    }

    @Override // android.view.View
    public final void draw(Canvas canvas) {
        MaterialShapeDrawable materialShapeDrawable;
        super.draw(canvas);
        if (this.hintEnabled) {
            this.collapsingTextHelper.draw(canvas);
        }
        if (this.boxUnderlineFocused == null || (materialShapeDrawable = this.boxUnderlineDefault) == null) {
            return;
        }
        materialShapeDrawable.draw(canvas);
        if (this.editText.isFocused()) {
            Rect bounds = this.boxUnderlineFocused.getBounds();
            Rect bounds2 = this.boxUnderlineDefault.getBounds();
            float f = this.collapsingTextHelper.expandedFraction;
            int centerX = bounds2.centerX();
            bounds.left = AnimationUtils.lerp(f, centerX, bounds2.left);
            bounds.right = AnimationUtils.lerp(f, centerX, bounds2.right);
            this.boxUnderlineFocused.draw(canvas);
        }
    }

    @Override // android.view.ViewGroup, android.view.View
    public final void drawableStateChanged() {
        if (this.inDrawableStateChanged) {
            return;
        }
        this.inDrawableStateChanged = true;
        super.drawableStateChanged();
        int[] drawableState = getDrawableState();
        CollapsingTextHelper collapsingTextHelper = this.collapsingTextHelper;
        boolean state = collapsingTextHelper != null ? collapsingTextHelper.setState(drawableState) | false : false;
        if (this.editText != null) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            updateLabelState(ViewCompat.Api19Impl.isLaidOut(this) && isEnabled(), false);
        }
        updateEditTextBackground();
        updateTextInputBoxState();
        if (state) {
            invalidate();
        }
        this.inDrawableStateChanged = false;
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final int getBaseline() {
        EditText editText = this.editText;
        if (editText == null) {
            return super.getBaseline();
        }
        return calculateLabelMarginTop() + getPaddingTop() + editText.getBaseline();
    }

    public final MaterialShapeDrawable getDropDownMaterialShapeDrawable(boolean z) {
        int i;
        float dimensionPixelOffset = getResources().getDimensionPixelOffset(com.android.systemui.R.dimen.mtrl_shape_corner_size_small_component);
        float f = z ? dimensionPixelOffset : 0.0f;
        EditText editText = this.editText;
        float dimensionPixelOffset2 = editText instanceof MaterialAutoCompleteTextView ? ((MaterialAutoCompleteTextView) editText).popupElevation : getResources().getDimensionPixelOffset(com.android.systemui.R.dimen.mtrl_exposed_dropdown_menu_popup_elevation);
        int dimensionPixelOffset3 = getResources().getDimensionPixelOffset(com.android.systemui.R.dimen.mtrl_exposed_dropdown_menu_popup_vertical_padding);
        RelativeCornerSize relativeCornerSize = ShapeAppearanceModel.PILL;
        ShapeAppearanceModel.Builder builder = new ShapeAppearanceModel.Builder();
        builder.setTopLeftCornerSize(f);
        builder.setTopRightCornerSize(f);
        builder.setBottomLeftCornerSize(dimensionPixelOffset);
        builder.setBottomRightCornerSize(dimensionPixelOffset);
        ShapeAppearanceModel build = builder.build();
        Context context = getContext();
        Paint paint = MaterialShapeDrawable.clearPaint;
        TypedValue resolveTypedValueOrThrow = MaterialAttributes.resolveTypedValueOrThrow(context, "MaterialShapeDrawable", com.android.systemui.R.attr.colorSurface);
        int i2 = resolveTypedValueOrThrow.resourceId;
        if (i2 != 0) {
            Object obj = ContextCompat.sLock;
            i = context.getColor(i2);
        } else {
            i = resolveTypedValueOrThrow.data;
        }
        MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable();
        materialShapeDrawable.initializeElevationOverlay(context);
        materialShapeDrawable.setFillColor(ColorStateList.valueOf(i));
        materialShapeDrawable.setElevation(dimensionPixelOffset2);
        materialShapeDrawable.setShapeAppearanceModel(build);
        MaterialShapeDrawable.MaterialShapeDrawableState materialShapeDrawableState = materialShapeDrawable.drawableState;
        if (materialShapeDrawableState.padding == null) {
            materialShapeDrawableState.padding = new Rect();
        }
        materialShapeDrawable.drawableState.padding.set(0, dimensionPixelOffset3, 0, dimensionPixelOffset3);
        materialShapeDrawable.invalidateSelf();
        return materialShapeDrawable;
    }

    public final float getHintCollapsedTextHeight() {
        return this.collapsingTextHelper.getCollapsedTextHeight();
    }

    public final int getHintCurrentCollapsedTextColor() {
        CollapsingTextHelper collapsingTextHelper = this.collapsingTextHelper;
        return collapsingTextHelper.getCurrentColor(collapsingTextHelper.collapsedTextColor);
    }

    public final int getLabelLeftBoundAlightWithPrefix(int i, boolean z) {
        int compoundPaddingLeft = this.editText.getCompoundPaddingLeft() + i;
        StartCompoundLayout startCompoundLayout = this.startLayout;
        return (startCompoundLayout.prefixText == null || z) ? compoundPaddingLeft : (compoundPaddingLeft - startCompoundLayout.prefixTextView.getMeasuredWidth()) + this.startLayout.prefixTextView.getPaddingLeft();
    }

    public final int getLabelRightBoundAlignedWithSuffix(int i, boolean z) {
        int compoundPaddingRight = i - this.editText.getCompoundPaddingRight();
        StartCompoundLayout startCompoundLayout = this.startLayout;
        return (startCompoundLayout.prefixText == null || !z) ? compoundPaddingRight : compoundPaddingRight + (startCompoundLayout.prefixTextView.getMeasuredWidth() - this.startLayout.prefixTextView.getPaddingRight());
    }

    public final boolean isHelperTextDisplayed() {
        IndicatorViewController indicatorViewController = this.indicatorViewController;
        return (indicatorViewController.captionDisplayed != 2 || indicatorViewController.helperTextView == null || TextUtils.isEmpty(indicatorViewController.helperText)) ? false : true;
    }

    public final void onApplyBoxBackgroundMode() {
        int i = this.boxBackgroundMode;
        if (i == 0) {
            this.boxBackground = null;
            this.boxUnderlineDefault = null;
            this.boxUnderlineFocused = null;
        } else if (i == 1) {
            this.boxBackground = new MaterialShapeDrawable(this.shapeAppearanceModel);
            this.boxUnderlineDefault = new MaterialShapeDrawable();
            this.boxUnderlineFocused = new MaterialShapeDrawable();
        } else {
            if (i != 2) {
                throw new IllegalArgumentException(ConstraintWidget$$ExternalSyntheticOutline0.m19m(new StringBuilder(), this.boxBackgroundMode, " is illegal; only @BoxBackgroundMode constants are supported."));
            }
            if (!this.hintEnabled || (this.boxBackground instanceof CutoutDrawable)) {
                this.boxBackground = new MaterialShapeDrawable(this.shapeAppearanceModel);
            } else {
                this.boxBackground = new CutoutDrawable(this.shapeAppearanceModel);
            }
            this.boxUnderlineDefault = null;
            this.boxUnderlineFocused = null;
        }
        updateEditTextBoxBackgroundIfNeeded();
        updateTextInputBoxState();
        if (this.boxBackgroundMode == 1) {
            if (getContext().getResources().getConfiguration().fontScale >= 2.0f) {
                this.boxCollapsedPaddingTopPx = getResources().getDimensionPixelSize(com.android.systemui.R.dimen.material_font_2_0_box_collapsed_padding_top);
            } else if (MaterialResources.isFontScaleAtLeast1_3(getContext())) {
                this.boxCollapsedPaddingTopPx = getResources().getDimensionPixelSize(com.android.systemui.R.dimen.material_font_1_3_box_collapsed_padding_top);
            }
        }
        if (this.editText != null && this.boxBackgroundMode == 1) {
            if (getContext().getResources().getConfiguration().fontScale >= 2.0f) {
                EditText editText = this.editText;
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api17Impl.setPaddingRelative(editText, ViewCompat.Api17Impl.getPaddingStart(editText), getResources().getDimensionPixelSize(com.android.systemui.R.dimen.material_filled_edittext_font_2_0_padding_top), ViewCompat.Api17Impl.getPaddingEnd(this.editText), getResources().getDimensionPixelSize(com.android.systemui.R.dimen.material_filled_edittext_font_2_0_padding_bottom));
            } else if (MaterialResources.isFontScaleAtLeast1_3(getContext())) {
                EditText editText2 = this.editText;
                WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                ViewCompat.Api17Impl.setPaddingRelative(editText2, ViewCompat.Api17Impl.getPaddingStart(editText2), getResources().getDimensionPixelSize(com.android.systemui.R.dimen.material_filled_edittext_font_1_3_padding_top), ViewCompat.Api17Impl.getPaddingEnd(this.editText), getResources().getDimensionPixelSize(com.android.systemui.R.dimen.material_filled_edittext_font_1_3_padding_bottom));
            }
        }
        if (this.boxBackgroundMode != 0) {
            updateInputLayoutMargins();
        }
        EditText editText3 = this.editText;
        if (editText3 instanceof AutoCompleteTextView) {
            AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) editText3;
            if (autoCompleteTextView.getDropDownBackground() == null) {
                int i2 = this.boxBackgroundMode;
                if (i2 == 2) {
                    if (this.outlinedDropDownMenuBackground == null) {
                        this.outlinedDropDownMenuBackground = getDropDownMaterialShapeDrawable(true);
                    }
                    autoCompleteTextView.setDropDownBackgroundDrawable(this.outlinedDropDownMenuBackground);
                } else if (i2 == 1) {
                    if (this.filledDropDownMenuBackground == null) {
                        StateListDrawable stateListDrawable = new StateListDrawable();
                        this.filledDropDownMenuBackground = stateListDrawable;
                        int[] iArr = {R.attr.state_above_anchor};
                        if (this.outlinedDropDownMenuBackground == null) {
                            this.outlinedDropDownMenuBackground = getDropDownMaterialShapeDrawable(true);
                        }
                        stateListDrawable.addState(iArr, this.outlinedDropDownMenuBackground);
                        this.filledDropDownMenuBackground.addState(new int[0], getDropDownMaterialShapeDrawable(false));
                    }
                    autoCompleteTextView.setDropDownBackgroundDrawable(this.filledDropDownMenuBackground);
                }
            }
        }
    }

    @Override // android.view.View
    public final void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.collapsingTextHelper.maybeUpdateFontWeightAdjustment(configuration);
    }

    @Override // android.widget.LinearLayout, android.view.ViewGroup, android.view.View
    public final void onLayout(boolean z, int i, int i2, int i3, int i4) {
        super.onLayout(z, i, i2, i3, i4);
        EditText editText = this.editText;
        if (editText != null) {
            Rect rect = this.tmpRect;
            DescendantOffsetUtils.getDescendantRect(this, editText, rect);
            MaterialShapeDrawable materialShapeDrawable = this.boxUnderlineDefault;
            if (materialShapeDrawable != null) {
                int i5 = rect.bottom;
                materialShapeDrawable.setBounds(rect.left, i5 - this.boxStrokeWidthDefaultPx, rect.right, i5);
            }
            MaterialShapeDrawable materialShapeDrawable2 = this.boxUnderlineFocused;
            if (materialShapeDrawable2 != null) {
                int i6 = rect.bottom;
                materialShapeDrawable2.setBounds(rect.left, i6 - this.boxStrokeWidthFocusedPx, rect.right, i6);
            }
            if (this.hintEnabled) {
                CollapsingTextHelper collapsingTextHelper = this.collapsingTextHelper;
                float textSize = this.editText.getTextSize();
                if (collapsingTextHelper.expandedTextSize != textSize) {
                    collapsingTextHelper.expandedTextSize = textSize;
                    collapsingTextHelper.recalculate(false);
                }
                int gravity = this.editText.getGravity();
                CollapsingTextHelper collapsingTextHelper2 = this.collapsingTextHelper;
                int i7 = (gravity & (-113)) | 48;
                if (collapsingTextHelper2.collapsedTextGravity != i7) {
                    collapsingTextHelper2.collapsedTextGravity = i7;
                    collapsingTextHelper2.recalculate(false);
                }
                CollapsingTextHelper collapsingTextHelper3 = this.collapsingTextHelper;
                if (collapsingTextHelper3.expandedTextGravity != gravity) {
                    collapsingTextHelper3.expandedTextGravity = gravity;
                    collapsingTextHelper3.recalculate(false);
                }
                CollapsingTextHelper collapsingTextHelper4 = this.collapsingTextHelper;
                if (this.editText == null) {
                    throw new IllegalStateException();
                }
                Rect rect2 = this.tmpBoundsRect;
                boolean isLayoutRtl = ViewUtils.isLayoutRtl(this);
                rect2.bottom = rect.bottom;
                int i8 = this.boxBackgroundMode;
                if (i8 == 1) {
                    rect2.left = getLabelLeftBoundAlightWithPrefix(rect.left, isLayoutRtl);
                    rect2.top = rect.top + this.boxCollapsedPaddingTopPx;
                    rect2.right = getLabelRightBoundAlignedWithSuffix(rect.right, isLayoutRtl);
                } else if (i8 != 2) {
                    rect2.left = getLabelLeftBoundAlightWithPrefix(rect.left, isLayoutRtl);
                    rect2.top = getPaddingTop();
                    rect2.right = getLabelRightBoundAlignedWithSuffix(rect.right, isLayoutRtl);
                } else {
                    rect2.left = this.editText.getPaddingLeft() + rect.left;
                    rect2.top = rect.top - calculateLabelMarginTop();
                    rect2.right = rect.right - this.editText.getPaddingRight();
                }
                collapsingTextHelper4.getClass();
                int i9 = rect2.left;
                int i10 = rect2.top;
                int i11 = rect2.right;
                int i12 = rect2.bottom;
                Rect rect3 = collapsingTextHelper4.collapsedBounds;
                if (!(rect3.left == i9 && rect3.top == i10 && rect3.right == i11 && rect3.bottom == i12)) {
                    rect3.set(i9, i10, i11, i12);
                    collapsingTextHelper4.boundsChanged = true;
                }
                CollapsingTextHelper collapsingTextHelper5 = this.collapsingTextHelper;
                if (this.editText == null) {
                    throw new IllegalStateException();
                }
                Rect rect4 = this.tmpBoundsRect;
                TextPaint textPaint = collapsingTextHelper5.tmpPaint;
                textPaint.setTextSize(collapsingTextHelper5.expandedTextSize);
                textPaint.setTypeface(collapsingTextHelper5.expandedTypeface);
                textPaint.setLetterSpacing(collapsingTextHelper5.expandedLetterSpacing);
                float f = -textPaint.ascent();
                rect4.left = this.editText.getCompoundPaddingLeft() + rect.left;
                rect4.top = this.boxBackgroundMode == 1 && this.editText.getMinLines() <= 1 ? (int) (rect.centerY() - (f / 2.0f)) : rect.top + this.editText.getCompoundPaddingTop();
                rect4.right = rect.right - this.editText.getCompoundPaddingRight();
                int compoundPaddingBottom = this.boxBackgroundMode == 1 && this.editText.getMinLines() <= 1 ? (int) (rect4.top + f) : rect.bottom - this.editText.getCompoundPaddingBottom();
                rect4.bottom = compoundPaddingBottom;
                int i13 = rect4.left;
                int i14 = rect4.top;
                int i15 = rect4.right;
                Rect rect5 = collapsingTextHelper5.expandedBounds;
                if (!(rect5.left == i13 && rect5.top == i14 && rect5.right == i15 && rect5.bottom == compoundPaddingBottom)) {
                    rect5.set(i13, i14, i15, compoundPaddingBottom);
                    collapsingTextHelper5.boundsChanged = true;
                }
                this.collapsingTextHelper.recalculate(false);
                if (!cutoutEnabled() || this.hintExpanded) {
                    return;
                }
                openCutout();
            }
        }
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onMeasure(int i, int i2) {
        boolean z;
        EditText editText;
        int max;
        super.onMeasure(i, i2);
        if (this.editText != null && this.editText.getMeasuredHeight() < (max = Math.max(this.endLayout.getMeasuredHeight(), this.startLayout.getMeasuredHeight()))) {
            this.editText.setMinimumHeight(max);
            z = true;
        } else {
            z = false;
        }
        boolean updateDummyDrawables = updateDummyDrawables();
        if (z || updateDummyDrawables) {
            this.editText.post(new Runnable() { // from class: com.google.android.material.textfield.TextInputLayout.3
                @Override // java.lang.Runnable
                public final void run() {
                    TextInputLayout.this.editText.requestLayout();
                }
            });
        }
        if (this.placeholderTextView != null && (editText = this.editText) != null) {
            this.placeholderTextView.setGravity(editText.getGravity());
            this.placeholderTextView.setPadding(this.editText.getCompoundPaddingLeft(), this.editText.getCompoundPaddingTop(), this.editText.getCompoundPaddingRight(), this.editText.getCompoundPaddingBottom());
        }
        this.endLayout.updateSuffixTextViewPadding();
    }

    @Override // android.view.View
    public final void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        SavedState savedState = (SavedState) parcelable;
        super.onRestoreInstanceState(savedState.mSuperState);
        setError(savedState.error);
        if (savedState.isEndIconChecked) {
            post(new Runnable() { // from class: com.google.android.material.textfield.TextInputLayout.2
                @Override // java.lang.Runnable
                public final void run() {
                    EndCompoundLayout endCompoundLayout = TextInputLayout.this.endLayout;
                    endCompoundLayout.endIconView.performClick();
                    endCompoundLayout.endIconView.jumpDrawablesToCurrentState();
                }
            });
        }
        requestLayout();
    }

    @Override // android.widget.LinearLayout, android.view.View
    public final void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        boolean z = false;
        boolean z2 = i == 1;
        boolean z3 = this.areCornerRadiiRtl;
        if (z2 != z3) {
            if (z2 && !z3) {
                z = true;
            }
            float cornerSize = this.shapeAppearanceModel.topLeftCornerSize.getCornerSize(this.tmpRectF);
            float cornerSize2 = this.shapeAppearanceModel.topRightCornerSize.getCornerSize(this.tmpRectF);
            float cornerSize3 = this.shapeAppearanceModel.bottomLeftCornerSize.getCornerSize(this.tmpRectF);
            float cornerSize4 = this.shapeAppearanceModel.bottomRightCornerSize.getCornerSize(this.tmpRectF);
            float f = z ? cornerSize : cornerSize2;
            if (z) {
                cornerSize = cornerSize2;
            }
            float f2 = z ? cornerSize3 : cornerSize4;
            if (z) {
                cornerSize3 = cornerSize4;
            }
            boolean isLayoutRtl = ViewUtils.isLayoutRtl(this);
            this.areCornerRadiiRtl = isLayoutRtl;
            float f3 = isLayoutRtl ? cornerSize : f;
            if (!isLayoutRtl) {
                f = cornerSize;
            }
            float f4 = isLayoutRtl ? cornerSize3 : f2;
            if (!isLayoutRtl) {
                f2 = cornerSize3;
            }
            MaterialShapeDrawable materialShapeDrawable = this.boxBackground;
            if (materialShapeDrawable != null && materialShapeDrawable.getTopLeftCornerResolvedSize() == f3) {
                MaterialShapeDrawable materialShapeDrawable2 = this.boxBackground;
                if (materialShapeDrawable2.drawableState.shapeAppearanceModel.topRightCornerSize.getCornerSize(materialShapeDrawable2.getBoundsAsRectF()) == f) {
                    MaterialShapeDrawable materialShapeDrawable3 = this.boxBackground;
                    if (materialShapeDrawable3.drawableState.shapeAppearanceModel.bottomLeftCornerSize.getCornerSize(materialShapeDrawable3.getBoundsAsRectF()) == f4) {
                        MaterialShapeDrawable materialShapeDrawable4 = this.boxBackground;
                        if (materialShapeDrawable4.drawableState.shapeAppearanceModel.bottomRightCornerSize.getCornerSize(materialShapeDrawable4.getBoundsAsRectF()) == f2) {
                            return;
                        }
                    }
                }
            }
            ShapeAppearanceModel shapeAppearanceModel = this.shapeAppearanceModel;
            shapeAppearanceModel.getClass();
            ShapeAppearanceModel.Builder builder = new ShapeAppearanceModel.Builder(shapeAppearanceModel);
            builder.setTopLeftCornerSize(f3);
            builder.setTopRightCornerSize(f);
            builder.setBottomLeftCornerSize(f4);
            builder.setBottomRightCornerSize(f2);
            this.shapeAppearanceModel = builder.build();
            applyBoxAttributes();
        }
    }

    @Override // android.view.View
    public final Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        if (shouldShowError()) {
            IndicatorViewController indicatorViewController = this.indicatorViewController;
            savedState.error = indicatorViewController.errorEnabled ? indicatorViewController.errorText : null;
        }
        EndCompoundLayout endCompoundLayout = this.endLayout;
        savedState.isEndIconChecked = (endCompoundLayout.endIconMode != 0) && endCompoundLayout.endIconView.isChecked();
        return savedState;
    }

    public final void openCutout() {
        float f;
        float f2;
        float f3;
        float f4;
        int i;
        int i2;
        if (cutoutEnabled()) {
            RectF rectF = this.tmpRectF;
            CollapsingTextHelper collapsingTextHelper = this.collapsingTextHelper;
            int width = this.editText.getWidth();
            int gravity = this.editText.getGravity();
            boolean calculateIsRtl = collapsingTextHelper.calculateIsRtl(collapsingTextHelper.text);
            collapsingTextHelper.isRtl = calculateIsRtl;
            Rect rect = collapsingTextHelper.collapsedBounds;
            if (gravity != 17 && (gravity & 7) != 1) {
                if ((gravity & 8388613) == 8388613 || (gravity & 5) == 5) {
                    if (calculateIsRtl) {
                        i2 = rect.left;
                        f3 = i2;
                    } else {
                        f = rect.right;
                        f2 = collapsingTextHelper.collapsedTextWidth;
                    }
                } else if (calculateIsRtl) {
                    f = rect.right;
                    f2 = collapsingTextHelper.collapsedTextWidth;
                } else {
                    i2 = rect.left;
                    f3 = i2;
                }
                float max = Math.max(f3, rect.left);
                rectF.left = max;
                rectF.top = rect.top;
                if (gravity != 17 || (gravity & 7) == 1) {
                    f4 = (width / 2.0f) + (collapsingTextHelper.collapsedTextWidth / 2.0f);
                } else if ((gravity & 8388613) == 8388613 || (gravity & 5) == 5) {
                    if (collapsingTextHelper.isRtl) {
                        f4 = collapsingTextHelper.collapsedTextWidth + max;
                    } else {
                        i = rect.right;
                        f4 = i;
                    }
                } else if (collapsingTextHelper.isRtl) {
                    i = rect.right;
                    f4 = i;
                } else {
                    f4 = collapsingTextHelper.collapsedTextWidth + max;
                }
                rectF.right = Math.min(f4, rect.right);
                rectF.bottom = collapsingTextHelper.getCollapsedTextHeight() + rect.top;
                if (rectF.width() > 0.0f || rectF.height() <= 0.0f) {
                }
                float f5 = rectF.left;
                float f6 = this.boxLabelCutoutPaddingPx;
                rectF.left = f5 - f6;
                rectF.right += f6;
                rectF.offset(-getPaddingLeft(), ((-getPaddingTop()) - (rectF.height() / 2.0f)) + this.boxStrokeWidthPx);
                CutoutDrawable cutoutDrawable = (CutoutDrawable) this.boxBackground;
                cutoutDrawable.getClass();
                cutoutDrawable.setCutout(rectF.left, rectF.top, rectF.right, rectF.bottom);
                return;
            }
            f = width / 2.0f;
            f2 = collapsingTextHelper.collapsedTextWidth / 2.0f;
            f3 = f - f2;
            float max2 = Math.max(f3, rect.left);
            rectF.left = max2;
            rectF.top = rect.top;
            if (gravity != 17) {
            }
            f4 = (width / 2.0f) + (collapsingTextHelper.collapsedTextWidth / 2.0f);
            rectF.right = Math.min(f4, rect.right);
            rectF.bottom = collapsingTextHelper.getCollapsedTextHeight() + rect.top;
            if (rectF.width() > 0.0f) {
            }
        }
    }

    @Override // android.view.View
    public final void setEnabled(boolean z) {
        recursiveSetEnabled(this, z);
        super.setEnabled(z);
    }

    public final void setError(CharSequence charSequence) {
        if (!this.indicatorViewController.errorEnabled) {
            if (TextUtils.isEmpty(charSequence)) {
                return;
            } else {
                setErrorEnabled(true);
            }
        }
        if (TextUtils.isEmpty(charSequence)) {
            this.indicatorViewController.hideError();
            return;
        }
        IndicatorViewController indicatorViewController = this.indicatorViewController;
        indicatorViewController.cancelCaptionAnimator();
        indicatorViewController.errorText = charSequence;
        indicatorViewController.errorView.setText(charSequence);
        int i = indicatorViewController.captionDisplayed;
        if (i != 1) {
            indicatorViewController.captionToShow = 1;
        }
        indicatorViewController.updateCaptionViewsVisibility(i, indicatorViewController.captionToShow, indicatorViewController.shouldAnimateCaptionView(indicatorViewController.errorView, charSequence));
    }

    public final void setErrorEnabled(boolean z) {
        IndicatorViewController indicatorViewController = this.indicatorViewController;
        if (indicatorViewController.errorEnabled == z) {
            return;
        }
        indicatorViewController.cancelCaptionAnimator();
        TextInputLayout textInputLayout = indicatorViewController.textInputView;
        if (z) {
            AppCompatTextView appCompatTextView = new AppCompatTextView(indicatorViewController.context);
            indicatorViewController.errorView = appCompatTextView;
            appCompatTextView.setId(com.android.systemui.R.id.textinput_error);
            indicatorViewController.errorView.setTextAlignment(5);
            int i = indicatorViewController.errorTextAppearance;
            indicatorViewController.errorTextAppearance = i;
            AppCompatTextView appCompatTextView2 = indicatorViewController.errorView;
            if (appCompatTextView2 != null) {
                textInputLayout.setTextAppearanceCompatWithErrorFallback(i, appCompatTextView2);
            }
            ColorStateList colorStateList = indicatorViewController.errorViewTextColor;
            indicatorViewController.errorViewTextColor = colorStateList;
            AppCompatTextView appCompatTextView3 = indicatorViewController.errorView;
            if (appCompatTextView3 != null && colorStateList != null) {
                appCompatTextView3.setTextColor(colorStateList);
            }
            CharSequence charSequence = indicatorViewController.errorViewContentDescription;
            indicatorViewController.errorViewContentDescription = charSequence;
            AppCompatTextView appCompatTextView4 = indicatorViewController.errorView;
            if (appCompatTextView4 != null) {
                appCompatTextView4.setContentDescription(charSequence);
            }
            indicatorViewController.errorView.setVisibility(4);
            AppCompatTextView appCompatTextView5 = indicatorViewController.errorView;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api19Impl.setAccessibilityLiveRegion(appCompatTextView5, 1);
            indicatorViewController.addIndicator(0, indicatorViewController.errorView);
        } else {
            indicatorViewController.hideError();
            indicatorViewController.removeIndicator(0, indicatorViewController.errorView);
            indicatorViewController.errorView = null;
            textInputLayout.updateEditTextBackground();
            textInputLayout.updateTextInputBoxState();
        }
        indicatorViewController.errorEnabled = z;
    }

    public final void setHelperTextEnabled(boolean z) {
        final IndicatorViewController indicatorViewController = this.indicatorViewController;
        if (indicatorViewController.helperTextEnabled == z) {
            return;
        }
        indicatorViewController.cancelCaptionAnimator();
        if (z) {
            AppCompatTextView appCompatTextView = new AppCompatTextView(indicatorViewController.context);
            indicatorViewController.helperTextView = appCompatTextView;
            appCompatTextView.setId(com.android.systemui.R.id.textinput_helper_text);
            indicatorViewController.helperTextView.setTextAlignment(5);
            indicatorViewController.helperTextView.setVisibility(4);
            AppCompatTextView appCompatTextView2 = indicatorViewController.helperTextView;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api19Impl.setAccessibilityLiveRegion(appCompatTextView2, 1);
            int i = indicatorViewController.helperTextTextAppearance;
            indicatorViewController.helperTextTextAppearance = i;
            AppCompatTextView appCompatTextView3 = indicatorViewController.helperTextView;
            if (appCompatTextView3 != null) {
                appCompatTextView3.setTextAppearance(i);
            }
            ColorStateList colorStateList = indicatorViewController.helperTextViewTextColor;
            indicatorViewController.helperTextViewTextColor = colorStateList;
            AppCompatTextView appCompatTextView4 = indicatorViewController.helperTextView;
            if (appCompatTextView4 != null && colorStateList != null) {
                appCompatTextView4.setTextColor(colorStateList);
            }
            indicatorViewController.addIndicator(1, indicatorViewController.helperTextView);
            indicatorViewController.helperTextView.setAccessibilityDelegate(new View.AccessibilityDelegate() { // from class: com.google.android.material.textfield.IndicatorViewController.2
                @Override // android.view.View.AccessibilityDelegate
                public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfo accessibilityNodeInfo) {
                    super.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfo);
                    EditText editText = IndicatorViewController.this.textInputView.editText;
                    if (editText != null) {
                        accessibilityNodeInfo.setLabeledBy(editText);
                    }
                }
            });
        } else {
            indicatorViewController.cancelCaptionAnimator();
            int i2 = indicatorViewController.captionDisplayed;
            if (i2 == 2) {
                indicatorViewController.captionToShow = 0;
            }
            indicatorViewController.updateCaptionViewsVisibility(i2, indicatorViewController.captionToShow, indicatorViewController.shouldAnimateCaptionView(indicatorViewController.helperTextView, ""));
            indicatorViewController.removeIndicator(1, indicatorViewController.helperTextView);
            indicatorViewController.helperTextView = null;
            TextInputLayout textInputLayout = indicatorViewController.textInputView;
            textInputLayout.updateEditTextBackground();
            textInputLayout.updateTextInputBoxState();
        }
        indicatorViewController.helperTextEnabled = z;
    }

    public final void setHint(CharSequence charSequence) {
        if (this.hintEnabled) {
            if (!TextUtils.equals(charSequence, this.hint)) {
                this.hint = charSequence;
                CollapsingTextHelper collapsingTextHelper = this.collapsingTextHelper;
                if (charSequence == null || !TextUtils.equals(collapsingTextHelper.text, charSequence)) {
                    collapsingTextHelper.text = charSequence;
                    collapsingTextHelper.textToDraw = null;
                    Bitmap bitmap = collapsingTextHelper.expandedTitleTexture;
                    if (bitmap != null) {
                        bitmap.recycle();
                        collapsingTextHelper.expandedTitleTexture = null;
                    }
                    collapsingTextHelper.recalculate(false);
                }
                if (!this.hintExpanded) {
                    openCutout();
                }
            }
            sendAccessibilityEvent(2048);
        }
    }

    public final void setPlaceholderTextEnabled(boolean z) {
        if (this.placeholderEnabled == z) {
            return;
        }
        if (z) {
            AppCompatTextView appCompatTextView = this.placeholderTextView;
            if (appCompatTextView != null) {
                this.inputFrame.addView(appCompatTextView);
                this.placeholderTextView.setVisibility(0);
            }
        } else {
            AppCompatTextView appCompatTextView2 = this.placeholderTextView;
            if (appCompatTextView2 != null) {
                appCompatTextView2.setVisibility(8);
            }
            this.placeholderTextView = null;
        }
        this.placeholderEnabled = z;
    }

    public final void setTextAppearanceCompatWithErrorFallback(int i, TextView textView) {
        boolean z = true;
        try {
            textView.setTextAppearance(i);
            if (textView.getTextColors().getDefaultColor() != -65281) {
                z = false;
            }
        } catch (Exception unused) {
        }
        if (z) {
            textView.setTextAppearance(2132018033);
            Context context = getContext();
            Object obj = ContextCompat.sLock;
            textView.setTextColor(context.getColor(com.android.systemui.R.color.design_error));
        }
    }

    public final boolean shouldShowError() {
        IndicatorViewController indicatorViewController = this.indicatorViewController;
        return (indicatorViewController.captionToShow != 1 || indicatorViewController.errorView == null || TextUtils.isEmpty(indicatorViewController.errorText)) ? false : true;
    }

    public final void updateCounter(Editable editable) {
        getClass();
        int length = editable != null ? editable.length() : 0;
        boolean z = this.counterOverflowed;
        int i = this.counterMaxLength;
        if (i == -1) {
            this.counterView.setText(String.valueOf(length));
            this.counterView.setContentDescription(null);
            this.counterOverflowed = false;
        } else {
            this.counterOverflowed = length > i;
            Context context = getContext();
            this.counterView.setContentDescription(context.getString(this.counterOverflowed ? com.android.systemui.R.string.character_counter_overflowed_content_description : com.android.systemui.R.string.character_counter_content_description, Integer.valueOf(length), Integer.valueOf(this.counterMaxLength)));
            if (z != this.counterOverflowed) {
                updateCounterTextAppearanceAndColor();
            }
            this.counterView.setText(BidiFormatter.getInstance().unicodeWrap(getContext().getString(com.android.systemui.R.string.character_counter_pattern, Integer.valueOf(length), Integer.valueOf(this.counterMaxLength))));
        }
        if (this.editText == null || z == this.counterOverflowed) {
            return;
        }
        updateLabelState(false, false);
        updateTextInputBoxState();
        updateEditTextBackground();
    }

    public final void updateCounterTextAppearanceAndColor() {
        ColorStateList colorStateList;
        ColorStateList colorStateList2;
        AppCompatTextView appCompatTextView = this.counterView;
        if (appCompatTextView != null) {
            setTextAppearanceCompatWithErrorFallback(this.counterOverflowed ? this.counterOverflowTextAppearance : this.counterTextAppearance, appCompatTextView);
            if (!this.counterOverflowed && (colorStateList2 = this.counterTextColor) != null) {
                this.counterView.setTextColor(colorStateList2);
            }
            if (!this.counterOverflowed || (colorStateList = this.counterOverflowTextColor) == null) {
                return;
            }
            this.counterView.setTextColor(colorStateList);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x001d, code lost:
    
        if (r0.prefixTextView.getVisibility() == 0) goto L11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0099, code lost:
    
        if (r6.isEndIconVisible() != false) goto L41;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x009f, code lost:
    
        if (r10.endLayout.suffixText != null) goto L41;
     */
    /* JADX WARN: Removed duplicated region for block: B:14:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x008a  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00ae  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0131  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x00a9  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0068  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean updateDummyDrawables() {
        boolean z;
        boolean z2;
        boolean z3;
        if (this.editText == null) {
            return false;
        }
        boolean z4 = true;
        if (this.startLayout.startIconView.getDrawable() == null) {
            StartCompoundLayout startCompoundLayout = this.startLayout;
            if (startCompoundLayout.prefixText != null) {
            }
            z = false;
            CheckableImageButton checkableImageButton = null;
            if (z) {
                int measuredWidth = this.startLayout.getMeasuredWidth() - this.editText.getPaddingLeft();
                if (this.startDummyDrawable == null || this.startDummyDrawableWidth != measuredWidth) {
                    ColorDrawable colorDrawable = new ColorDrawable();
                    this.startDummyDrawable = colorDrawable;
                    this.startDummyDrawableWidth = measuredWidth;
                    colorDrawable.setBounds(0, 0, measuredWidth, 1);
                }
                Drawable[] compoundDrawablesRelative = this.editText.getCompoundDrawablesRelative();
                Drawable drawable = compoundDrawablesRelative[0];
                Drawable drawable2 = this.startDummyDrawable;
                if (drawable != drawable2) {
                    this.editText.setCompoundDrawablesRelative(drawable2, compoundDrawablesRelative[1], compoundDrawablesRelative[2], compoundDrawablesRelative[3]);
                    z2 = true;
                }
                z2 = false;
            } else {
                if (this.startDummyDrawable != null) {
                    Drawable[] compoundDrawablesRelative2 = this.editText.getCompoundDrawablesRelative();
                    this.editText.setCompoundDrawablesRelative(null, compoundDrawablesRelative2[1], compoundDrawablesRelative2[2], compoundDrawablesRelative2[3]);
                    this.startDummyDrawable = null;
                    z2 = true;
                }
                z2 = false;
            }
            if (!this.endLayout.isErrorIconVisible()) {
                EndCompoundLayout endCompoundLayout = this.endLayout;
                if (endCompoundLayout.endIconMode != 0) {
                }
            }
            if (this.endLayout.getMeasuredWidth() > 0) {
                z3 = true;
                if (!z3) {
                    int measuredWidth2 = this.endLayout.suffixTextView.getMeasuredWidth() - this.editText.getPaddingRight();
                    EndCompoundLayout endCompoundLayout2 = this.endLayout;
                    if (endCompoundLayout2.isErrorIconVisible()) {
                        checkableImageButton = endCompoundLayout2.errorIconView;
                    } else {
                        if ((endCompoundLayout2.endIconMode != 0) && endCompoundLayout2.isEndIconVisible()) {
                            checkableImageButton = endCompoundLayout2.endIconView;
                        }
                    }
                    if (checkableImageButton != null) {
                        measuredWidth2 = ((ViewGroup.MarginLayoutParams) checkableImageButton.getLayoutParams()).getMarginStart() + checkableImageButton.getMeasuredWidth() + measuredWidth2;
                    }
                    Drawable[] compoundDrawablesRelative3 = this.editText.getCompoundDrawablesRelative();
                    Drawable drawable3 = this.endDummyDrawable;
                    if (drawable3 == null || this.endDummyDrawableWidth == measuredWidth2) {
                        if (drawable3 == null) {
                            ColorDrawable colorDrawable2 = new ColorDrawable();
                            this.endDummyDrawable = colorDrawable2;
                            this.endDummyDrawableWidth = measuredWidth2;
                            colorDrawable2.setBounds(0, 0, measuredWidth2, 1);
                        }
                        Drawable drawable4 = compoundDrawablesRelative3[2];
                        Drawable drawable5 = this.endDummyDrawable;
                        if (drawable4 != drawable5) {
                            this.originalEditTextEndDrawable = drawable4;
                            this.editText.setCompoundDrawablesRelative(compoundDrawablesRelative3[0], compoundDrawablesRelative3[1], drawable5, compoundDrawablesRelative3[3]);
                        } else {
                            z4 = z2;
                        }
                    } else {
                        this.endDummyDrawableWidth = measuredWidth2;
                        drawable3.setBounds(0, 0, measuredWidth2, 1);
                        this.editText.setCompoundDrawablesRelative(compoundDrawablesRelative3[0], compoundDrawablesRelative3[1], this.endDummyDrawable, compoundDrawablesRelative3[3]);
                    }
                } else {
                    if (this.endDummyDrawable == null) {
                        return z2;
                    }
                    Drawable[] compoundDrawablesRelative4 = this.editText.getCompoundDrawablesRelative();
                    if (compoundDrawablesRelative4[2] == this.endDummyDrawable) {
                        this.editText.setCompoundDrawablesRelative(compoundDrawablesRelative4[0], compoundDrawablesRelative4[1], this.originalEditTextEndDrawable, compoundDrawablesRelative4[3]);
                    } else {
                        z4 = z2;
                    }
                    this.endDummyDrawable = null;
                }
                return z4;
            }
            z3 = false;
            if (!z3) {
            }
            return z4;
        }
        if (this.startLayout.getMeasuredWidth() > 0) {
            z = true;
            CheckableImageButton checkableImageButton2 = null;
            if (z) {
            }
            if (!this.endLayout.isErrorIconVisible()) {
            }
            if (this.endLayout.getMeasuredWidth() > 0) {
            }
            z3 = false;
            if (!z3) {
            }
            return z4;
        }
        z = false;
        CheckableImageButton checkableImageButton22 = null;
        if (z) {
        }
        if (!this.endLayout.isErrorIconVisible()) {
        }
        if (this.endLayout.getMeasuredWidth() > 0) {
        }
        z3 = false;
        if (!z3) {
        }
        return z4;
    }

    public final void updateEditTextBackground() {
        Drawable background;
        AppCompatTextView appCompatTextView;
        PorterDuffColorFilter porterDuffColorFilter;
        PorterDuffColorFilter porterDuffColorFilter2;
        EditText editText = this.editText;
        if (editText == null || this.boxBackgroundMode != 0 || (background = editText.getBackground()) == null) {
            return;
        }
        Rect rect = DrawableUtils.INSETS_NONE;
        Drawable mutate = background.mutate();
        if (shouldShowError()) {
            AppCompatTextView appCompatTextView2 = this.indicatorViewController.errorView;
            int currentTextColor = appCompatTextView2 != null ? appCompatTextView2.getCurrentTextColor() : -1;
            PorterDuff.Mode mode = PorterDuff.Mode.SRC_IN;
            AppCompatDrawableManager appCompatDrawableManager = AppCompatDrawableManager.INSTANCE;
            synchronized (AppCompatDrawableManager.class) {
                porterDuffColorFilter2 = ResourceManagerInternal.getPorterDuffColorFilter(currentTextColor, mode);
            }
            mutate.setColorFilter(porterDuffColorFilter2);
            return;
        }
        if (!this.counterOverflowed || (appCompatTextView = this.counterView) == null) {
            mutate.clearColorFilter();
            this.editText.refreshDrawableState();
            return;
        }
        int currentTextColor2 = appCompatTextView.getCurrentTextColor();
        PorterDuff.Mode mode2 = PorterDuff.Mode.SRC_IN;
        AppCompatDrawableManager appCompatDrawableManager2 = AppCompatDrawableManager.INSTANCE;
        synchronized (AppCompatDrawableManager.class) {
            porterDuffColorFilter = ResourceManagerInternal.getPorterDuffColorFilter(currentTextColor2, mode2);
        }
        mutate.setColorFilter(porterDuffColorFilter);
    }

    public final void updateEditTextBoxBackgroundIfNeeded() {
        Drawable drawable;
        int i;
        EditText editText = this.editText;
        if (editText == null || this.boxBackground == null) {
            return;
        }
        if ((this.boxBackgroundApplied || editText.getBackground() == null) && this.boxBackgroundMode != 0) {
            EditText editText2 = this.editText;
            if (editText2 instanceof AutoCompleteTextView) {
                if (!(editText2.getInputType() != 0)) {
                    int color = MaterialColors.getColor(this.editText, com.android.systemui.R.attr.colorControlHighlight);
                    int i2 = this.boxBackgroundMode;
                    int[][] iArr = EDIT_TEXT_BACKGROUND_RIPPLE_STATE;
                    if (i2 == 2) {
                        Context context = getContext();
                        MaterialShapeDrawable materialShapeDrawable = this.boxBackground;
                        TypedValue resolveTypedValueOrThrow = MaterialAttributes.resolveTypedValueOrThrow(context, "TextInputLayout", com.android.systemui.R.attr.colorSurface);
                        int i3 = resolveTypedValueOrThrow.resourceId;
                        if (i3 != 0) {
                            Object obj = ContextCompat.sLock;
                            i = context.getColor(i3);
                        } else {
                            i = resolveTypedValueOrThrow.data;
                        }
                        MaterialShapeDrawable materialShapeDrawable2 = new MaterialShapeDrawable(materialShapeDrawable.drawableState.shapeAppearanceModel);
                        int layer = MaterialColors.layer(0.1f, color, i);
                        materialShapeDrawable2.setFillColor(new ColorStateList(iArr, new int[]{layer, 0}));
                        materialShapeDrawable2.setTint(i);
                        ColorStateList colorStateList = new ColorStateList(iArr, new int[]{layer, i});
                        MaterialShapeDrawable materialShapeDrawable3 = new MaterialShapeDrawable(materialShapeDrawable.drawableState.shapeAppearanceModel);
                        materialShapeDrawable3.setTint(-1);
                        drawable = new LayerDrawable(new Drawable[]{new RippleDrawable(colorStateList, materialShapeDrawable2, materialShapeDrawable3), materialShapeDrawable});
                    } else if (i2 == 1) {
                        MaterialShapeDrawable materialShapeDrawable4 = this.boxBackground;
                        int i4 = this.boxBackgroundColor;
                        drawable = new RippleDrawable(new ColorStateList(iArr, new int[]{MaterialColors.layer(0.1f, color, i4), i4}), materialShapeDrawable4, materialShapeDrawable4);
                    } else {
                        drawable = null;
                    }
                    WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                    ViewCompat.Api16Impl.setBackground(editText2, drawable);
                    this.boxBackgroundApplied = true;
                }
            }
            drawable = this.boxBackground;
            WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api16Impl.setBackground(editText2, drawable);
            this.boxBackgroundApplied = true;
        }
    }

    public final void updateInputLayoutMargins() {
        if (this.boxBackgroundMode != 1) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) this.inputFrame.getLayoutParams();
            int calculateLabelMarginTop = calculateLabelMarginTop();
            if (calculateLabelMarginTop != layoutParams.topMargin) {
                layoutParams.topMargin = calculateLabelMarginTop;
                this.inputFrame.requestLayout();
            }
        }
    }

    public final void updateLabelState(boolean z, boolean z2) {
        ColorStateList colorStateList;
        AppCompatTextView appCompatTextView;
        boolean isEnabled = isEnabled();
        EditText editText = this.editText;
        boolean z3 = (editText == null || TextUtils.isEmpty(editText.getText())) ? false : true;
        EditText editText2 = this.editText;
        boolean z4 = editText2 != null && editText2.hasFocus();
        ColorStateList colorStateList2 = this.defaultHintTextColor;
        if (colorStateList2 != null) {
            this.collapsingTextHelper.setCollapsedTextColor(colorStateList2);
            CollapsingTextHelper collapsingTextHelper = this.collapsingTextHelper;
            ColorStateList colorStateList3 = this.defaultHintTextColor;
            if (collapsingTextHelper.expandedTextColor != colorStateList3) {
                collapsingTextHelper.expandedTextColor = colorStateList3;
                collapsingTextHelper.recalculate(false);
            }
        }
        if (!isEnabled) {
            ColorStateList colorStateList4 = this.defaultHintTextColor;
            int colorForState = colorStateList4 != null ? colorStateList4.getColorForState(new int[]{-16842910}, this.disabledColor) : this.disabledColor;
            this.collapsingTextHelper.setCollapsedTextColor(ColorStateList.valueOf(colorForState));
            CollapsingTextHelper collapsingTextHelper2 = this.collapsingTextHelper;
            ColorStateList valueOf = ColorStateList.valueOf(colorForState);
            if (collapsingTextHelper2.expandedTextColor != valueOf) {
                collapsingTextHelper2.expandedTextColor = valueOf;
                collapsingTextHelper2.recalculate(false);
            }
        } else if (shouldShowError()) {
            CollapsingTextHelper collapsingTextHelper3 = this.collapsingTextHelper;
            AppCompatTextView appCompatTextView2 = this.indicatorViewController.errorView;
            collapsingTextHelper3.setCollapsedTextColor(appCompatTextView2 != null ? appCompatTextView2.getTextColors() : null);
        } else if (this.counterOverflowed && (appCompatTextView = this.counterView) != null) {
            this.collapsingTextHelper.setCollapsedTextColor(appCompatTextView.getTextColors());
        } else if (z4 && (colorStateList = this.focusedTextColor) != null) {
            this.collapsingTextHelper.setCollapsedTextColor(colorStateList);
        }
        if (z3 || !this.expandedHintEnabled || (isEnabled() && z4)) {
            if (z2 || this.hintExpanded) {
                ValueAnimator valueAnimator = this.animator;
                if (valueAnimator != null && valueAnimator.isRunning()) {
                    this.animator.cancel();
                }
                if (z && this.hintAnimationEnabled) {
                    animateToExpansionFraction(1.0f);
                } else {
                    this.collapsingTextHelper.setExpansionFraction(1.0f);
                }
                this.hintExpanded = false;
                if (cutoutEnabled()) {
                    openCutout();
                }
                EditText editText3 = this.editText;
                updatePlaceholderText(editText3 != null ? editText3.getText() : null);
                StartCompoundLayout startCompoundLayout = this.startLayout;
                startCompoundLayout.hintExpanded = false;
                startCompoundLayout.updateVisibility();
                EndCompoundLayout endCompoundLayout = this.endLayout;
                endCompoundLayout.hintExpanded = false;
                endCompoundLayout.updateSuffixTextVisibility();
                return;
            }
            return;
        }
        if (z2 || !this.hintExpanded) {
            ValueAnimator valueAnimator2 = this.animator;
            if (valueAnimator2 != null && valueAnimator2.isRunning()) {
                this.animator.cancel();
            }
            if (z && this.hintAnimationEnabled) {
                animateToExpansionFraction(0.0f);
            } else {
                this.collapsingTextHelper.setExpansionFraction(0.0f);
            }
            if (cutoutEnabled() && (!((CutoutDrawable) this.boxBackground).cutoutBounds.isEmpty()) && cutoutEnabled()) {
                ((CutoutDrawable) this.boxBackground).setCutout(0.0f, 0.0f, 0.0f, 0.0f);
            }
            this.hintExpanded = true;
            AppCompatTextView appCompatTextView3 = this.placeholderTextView;
            if (appCompatTextView3 != null && this.placeholderEnabled) {
                appCompatTextView3.setText((CharSequence) null);
                TransitionManager.beginDelayedTransition(this.placeholderFadeOut, this.inputFrame);
                this.placeholderTextView.setVisibility(4);
            }
            StartCompoundLayout startCompoundLayout2 = this.startLayout;
            startCompoundLayout2.hintExpanded = true;
            startCompoundLayout2.updateVisibility();
            EndCompoundLayout endCompoundLayout2 = this.endLayout;
            endCompoundLayout2.hintExpanded = true;
            endCompoundLayout2.updateSuffixTextVisibility();
        }
    }

    public final void updatePlaceholderText(Editable editable) {
        getClass();
        if ((editable != null ? editable.length() : 0) != 0 || this.hintExpanded) {
            AppCompatTextView appCompatTextView = this.placeholderTextView;
            if (appCompatTextView == null || !this.placeholderEnabled) {
                return;
            }
            appCompatTextView.setText((CharSequence) null);
            TransitionManager.beginDelayedTransition(this.placeholderFadeOut, this.inputFrame);
            this.placeholderTextView.setVisibility(4);
            return;
        }
        if (this.placeholderTextView == null || !this.placeholderEnabled || TextUtils.isEmpty(this.placeholderText)) {
            return;
        }
        this.placeholderTextView.setText(this.placeholderText);
        TransitionManager.beginDelayedTransition(this.placeholderFadeIn, this.inputFrame);
        this.placeholderTextView.setVisibility(0);
        this.placeholderTextView.bringToFront();
        announceForAccessibility(this.placeholderText);
    }

    public final void updateStrokeErrorColor(boolean z, boolean z2) {
        int defaultColor = this.strokeErrorColor.getDefaultColor();
        int colorForState = this.strokeErrorColor.getColorForState(new int[]{R.attr.state_hovered, R.attr.state_enabled}, defaultColor);
        int colorForState2 = this.strokeErrorColor.getColorForState(new int[]{R.attr.state_activated, R.attr.state_enabled}, defaultColor);
        if (z) {
            this.boxStrokeColor = colorForState2;
        } else if (z2) {
            this.boxStrokeColor = colorForState;
        } else {
            this.boxStrokeColor = defaultColor;
        }
    }

    public final void updateTextInputBoxState() {
        AppCompatTextView appCompatTextView;
        EditText editText;
        EditText editText2;
        if (this.boxBackground == null || this.boxBackgroundMode == 0) {
            return;
        }
        boolean z = false;
        boolean z2 = isFocused() || ((editText2 = this.editText) != null && editText2.hasFocus());
        if (isHovered() || ((editText = this.editText) != null && editText.isHovered())) {
            z = true;
        }
        if (!isEnabled()) {
            this.boxStrokeColor = this.disabledColor;
        } else if (shouldShowError()) {
            if (this.strokeErrorColor != null) {
                updateStrokeErrorColor(z2, z);
            } else {
                AppCompatTextView appCompatTextView2 = this.indicatorViewController.errorView;
                this.boxStrokeColor = appCompatTextView2 != null ? appCompatTextView2.getCurrentTextColor() : -1;
            }
        } else if (!this.counterOverflowed || (appCompatTextView = this.counterView) == null) {
            if (z2) {
                this.boxStrokeColor = this.focusedStrokeColor;
            } else if (z) {
                this.boxStrokeColor = this.hoveredStrokeColor;
            } else {
                this.boxStrokeColor = this.defaultStrokeColor;
            }
        } else if (this.strokeErrorColor != null) {
            updateStrokeErrorColor(z2, z);
        } else {
            this.boxStrokeColor = appCompatTextView.getCurrentTextColor();
        }
        EndCompoundLayout endCompoundLayout = this.endLayout;
        endCompoundLayout.updateErrorIconVisibility();
        IconHelper.refreshIconDrawableState(endCompoundLayout.textInputLayout, endCompoundLayout.errorIconView, endCompoundLayout.errorIconTintList);
        IconHelper.refreshIconDrawableState(endCompoundLayout.textInputLayout, endCompoundLayout.endIconView, endCompoundLayout.endIconTintList);
        if (endCompoundLayout.getEndIconDelegate() instanceof DropdownMenuEndIconDelegate) {
            if (!endCompoundLayout.textInputLayout.shouldShowError() || endCompoundLayout.endIconView.getDrawable() == null) {
                IconHelper.applyIconTint(endCompoundLayout.textInputLayout, endCompoundLayout.endIconView, endCompoundLayout.endIconTintList, endCompoundLayout.endIconTintMode);
            } else {
                Drawable mutate = endCompoundLayout.endIconView.getDrawable().mutate();
                AppCompatTextView appCompatTextView3 = endCompoundLayout.textInputLayout.indicatorViewController.errorView;
                mutate.setTint(appCompatTextView3 != null ? appCompatTextView3.getCurrentTextColor() : -1);
                endCompoundLayout.endIconView.setImageDrawable(mutate);
            }
        }
        StartCompoundLayout startCompoundLayout = this.startLayout;
        IconHelper.refreshIconDrawableState(startCompoundLayout.textInputLayout, startCompoundLayout.startIconView, startCompoundLayout.startIconTintList);
        if (this.boxBackgroundMode == 2) {
            int i = this.boxStrokeWidthPx;
            if (z2 && isEnabled()) {
                this.boxStrokeWidthPx = this.boxStrokeWidthFocusedPx;
            } else {
                this.boxStrokeWidthPx = this.boxStrokeWidthDefaultPx;
            }
            if (this.boxStrokeWidthPx != i && cutoutEnabled() && !this.hintExpanded) {
                if (cutoutEnabled()) {
                    ((CutoutDrawable) this.boxBackground).setCutout(0.0f, 0.0f, 0.0f, 0.0f);
                }
                openCutout();
            }
        }
        if (this.boxBackgroundMode == 1) {
            if (!isEnabled()) {
                this.boxBackgroundColor = this.disabledFilledBackgroundColor;
            } else if (z && !z2) {
                this.boxBackgroundColor = this.hoveredFilledBackgroundColor;
            } else if (z2) {
                this.boxBackgroundColor = this.focusedFilledBackgroundColor;
            } else {
                this.boxBackgroundColor = this.defaultFilledBackgroundColor;
            }
        }
        applyBoxAttributes();
    }

    public TextInputLayout(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, com.android.systemui.R.attr.textInputStyle);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r3v103 */
    /* JADX WARN: Type inference failed for: r3v36 */
    /* JADX WARN: Type inference failed for: r3v37, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r3v89 */
    public TextInputLayout(Context context, AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, 2132018831), attributeSet, i);
        ?? r3;
        ColorStateList colorStateList;
        ColorStateList colorStateList2;
        ColorStateList colorStateList3;
        ColorStateList colorStateList4;
        ColorStateList colorStateList5;
        this.minEms = -1;
        this.maxEms = -1;
        this.minWidth = -1;
        this.maxWidth = -1;
        IndicatorViewController indicatorViewController = new IndicatorViewController(this);
        this.indicatorViewController = indicatorViewController;
        this.lengthCounter = new ViewCompat$$ExternalSyntheticLambda0();
        this.tmpRect = new Rect();
        this.tmpBoundsRect = new Rect();
        this.tmpRectF = new RectF();
        this.editTextAttachedListeners = new LinkedHashSet();
        CollapsingTextHelper collapsingTextHelper = new CollapsingTextHelper(this);
        this.collapsingTextHelper = collapsingTextHelper;
        Context context2 = getContext();
        setOrientation(1);
        setWillNotDraw(false);
        setAddStatesFromChildren(true);
        FrameLayout frameLayout = new FrameLayout(context2);
        this.inputFrame = frameLayout;
        frameLayout.setAddStatesFromChildren(true);
        TimeInterpolator timeInterpolator = AnimationUtils.LINEAR_INTERPOLATOR;
        collapsingTextHelper.textSizeInterpolator = timeInterpolator;
        collapsingTextHelper.recalculate(false);
        collapsingTextHelper.positionInterpolator = timeInterpolator;
        collapsingTextHelper.recalculate(false);
        if (collapsingTextHelper.collapsedTextGravity != 8388659) {
            collapsingTextHelper.collapsedTextGravity = 8388659;
            collapsingTextHelper.recalculate(false);
        }
        TintTypedArray obtainTintedStyledAttributes = ThemeEnforcement.obtainTintedStyledAttributes(context2, attributeSet, R$styleable.TextInputLayout, i, 2132018831, 22, 20, 35, 40, 44);
        StartCompoundLayout startCompoundLayout = new StartCompoundLayout(this, obtainTintedStyledAttributes);
        this.startLayout = startCompoundLayout;
        this.hintEnabled = obtainTintedStyledAttributes.getBoolean(43, true);
        setHint(obtainTintedStyledAttributes.getText(4));
        this.hintAnimationEnabled = obtainTintedStyledAttributes.getBoolean(42, true);
        this.expandedHintEnabled = obtainTintedStyledAttributes.getBoolean(37, true);
        if (obtainTintedStyledAttributes.hasValue(6)) {
            int i2 = obtainTintedStyledAttributes.getInt(6, -1);
            this.minEms = i2;
            EditText editText = this.editText;
            if (editText != null && i2 != -1) {
                editText.setMinEms(i2);
            }
        } else if (obtainTintedStyledAttributes.hasValue(3)) {
            int dimensionPixelSize = obtainTintedStyledAttributes.getDimensionPixelSize(3, -1);
            this.minWidth = dimensionPixelSize;
            EditText editText2 = this.editText;
            if (editText2 != null && dimensionPixelSize != -1) {
                editText2.setMinWidth(dimensionPixelSize);
            }
        }
        if (obtainTintedStyledAttributes.hasValue(5)) {
            int i3 = obtainTintedStyledAttributes.getInt(5, -1);
            this.maxEms = i3;
            EditText editText3 = this.editText;
            if (editText3 != null && i3 != -1) {
                editText3.setMaxEms(i3);
            }
        } else if (obtainTintedStyledAttributes.hasValue(2)) {
            int dimensionPixelSize2 = obtainTintedStyledAttributes.getDimensionPixelSize(2, -1);
            this.maxWidth = dimensionPixelSize2;
            EditText editText4 = this.editText;
            if (editText4 != null && dimensionPixelSize2 != -1) {
                editText4.setMaxWidth(dimensionPixelSize2);
            }
        }
        this.shapeAppearanceModel = ShapeAppearanceModel.builder(context2, attributeSet, i, 2132018831).build();
        this.boxLabelCutoutPaddingPx = context2.getResources().getDimensionPixelOffset(com.android.systemui.R.dimen.mtrl_textinput_box_label_cutout_padding);
        this.boxCollapsedPaddingTopPx = obtainTintedStyledAttributes.getDimensionPixelOffset(9, 0);
        int dimensionPixelSize3 = obtainTintedStyledAttributes.getDimensionPixelSize(16, context2.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.mtrl_textinput_box_stroke_width_default));
        this.boxStrokeWidthDefaultPx = dimensionPixelSize3;
        this.boxStrokeWidthFocusedPx = obtainTintedStyledAttributes.getDimensionPixelSize(17, context2.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.mtrl_textinput_box_stroke_width_focused));
        this.boxStrokeWidthPx = dimensionPixelSize3;
        TypedArray typedArray = obtainTintedStyledAttributes.mWrapped;
        float dimension = typedArray.getDimension(13, -1.0f);
        float dimension2 = typedArray.getDimension(12, -1.0f);
        float dimension3 = typedArray.getDimension(10, -1.0f);
        float dimension4 = typedArray.getDimension(11, -1.0f);
        ShapeAppearanceModel shapeAppearanceModel = this.shapeAppearanceModel;
        shapeAppearanceModel.getClass();
        ShapeAppearanceModel.Builder builder = new ShapeAppearanceModel.Builder(shapeAppearanceModel);
        if (dimension >= 0.0f) {
            builder.setTopLeftCornerSize(dimension);
        }
        if (dimension2 >= 0.0f) {
            builder.setTopRightCornerSize(dimension2);
        }
        if (dimension3 >= 0.0f) {
            builder.setBottomRightCornerSize(dimension3);
        }
        if (dimension4 >= 0.0f) {
            builder.setBottomLeftCornerSize(dimension4);
        }
        this.shapeAppearanceModel = builder.build();
        ColorStateList colorStateList6 = MaterialResources.getColorStateList(context2, obtainTintedStyledAttributes, 7);
        if (colorStateList6 != null) {
            int defaultColor = colorStateList6.getDefaultColor();
            this.defaultFilledBackgroundColor = defaultColor;
            this.boxBackgroundColor = defaultColor;
            if (colorStateList6.isStateful()) {
                this.disabledFilledBackgroundColor = colorStateList6.getColorForState(new int[]{-16842910}, -1);
                this.focusedFilledBackgroundColor = colorStateList6.getColorForState(new int[]{R.attr.state_focused, R.attr.state_enabled}, -1);
                this.hoveredFilledBackgroundColor = colorStateList6.getColorForState(new int[]{R.attr.state_hovered, R.attr.state_enabled}, -1);
            } else {
                this.focusedFilledBackgroundColor = defaultColor;
                ColorStateList colorStateList7 = ContextCompat.getColorStateList(com.android.systemui.R.color.mtrl_filled_background_color, context2);
                this.disabledFilledBackgroundColor = colorStateList7.getColorForState(new int[]{-16842910}, -1);
                this.hoveredFilledBackgroundColor = colorStateList7.getColorForState(new int[]{R.attr.state_hovered}, -1);
            }
        } else {
            this.boxBackgroundColor = 0;
            this.defaultFilledBackgroundColor = 0;
            this.disabledFilledBackgroundColor = 0;
            this.focusedFilledBackgroundColor = 0;
            this.hoveredFilledBackgroundColor = 0;
        }
        if (obtainTintedStyledAttributes.hasValue(1)) {
            ColorStateList colorStateList8 = obtainTintedStyledAttributes.getColorStateList(1);
            this.focusedTextColor = colorStateList8;
            this.defaultHintTextColor = colorStateList8;
        }
        ColorStateList colorStateList9 = MaterialResources.getColorStateList(context2, obtainTintedStyledAttributes, 14);
        this.focusedStrokeColor = typedArray.getColor(14, 0);
        Object obj = ContextCompat.sLock;
        this.defaultStrokeColor = context2.getColor(com.android.systemui.R.color.mtrl_textinput_default_box_stroke_color);
        this.disabledColor = context2.getColor(com.android.systemui.R.color.mtrl_textinput_disabled_color);
        this.hoveredStrokeColor = context2.getColor(com.android.systemui.R.color.mtrl_textinput_hovered_box_stroke_color);
        if (colorStateList9 != null) {
            if (colorStateList9.isStateful()) {
                this.defaultStrokeColor = colorStateList9.getDefaultColor();
                this.disabledColor = colorStateList9.getColorForState(new int[]{-16842910}, -1);
                this.hoveredStrokeColor = colorStateList9.getColorForState(new int[]{R.attr.state_hovered, R.attr.state_enabled}, -1);
                this.focusedStrokeColor = colorStateList9.getColorForState(new int[]{R.attr.state_focused, R.attr.state_enabled}, -1);
            } else if (this.focusedStrokeColor != colorStateList9.getDefaultColor()) {
                this.focusedStrokeColor = colorStateList9.getDefaultColor();
            }
            updateTextInputBoxState();
        }
        if (obtainTintedStyledAttributes.hasValue(15) && this.strokeErrorColor != (colorStateList5 = MaterialResources.getColorStateList(context2, obtainTintedStyledAttributes, 15))) {
            this.strokeErrorColor = colorStateList5;
            updateTextInputBoxState();
        }
        if (obtainTintedStyledAttributes.getResourceId(44, -1) != -1) {
            r3 = 0;
            r3 = 0;
            collapsingTextHelper.setCollapsedTextAppearance(obtainTintedStyledAttributes.getResourceId(44, 0));
            this.focusedTextColor = collapsingTextHelper.collapsedTextColor;
            if (this.editText != null) {
                updateLabelState(false, false);
                updateInputLayoutMargins();
            }
        } else {
            r3 = 0;
        }
        int resourceId = obtainTintedStyledAttributes.getResourceId(35, r3);
        CharSequence text = obtainTintedStyledAttributes.getText(30);
        boolean z = obtainTintedStyledAttributes.getBoolean(31, r3);
        int resourceId2 = obtainTintedStyledAttributes.getResourceId(40, r3);
        boolean z2 = obtainTintedStyledAttributes.getBoolean(39, r3);
        CharSequence text2 = obtainTintedStyledAttributes.getText(38);
        int resourceId3 = obtainTintedStyledAttributes.getResourceId(52, r3);
        CharSequence text3 = obtainTintedStyledAttributes.getText(51);
        boolean z3 = obtainTintedStyledAttributes.getBoolean(18, r3);
        int i4 = obtainTintedStyledAttributes.getInt(19, -1);
        if (this.counterMaxLength != i4) {
            if (i4 > 0) {
                this.counterMaxLength = i4;
            } else {
                this.counterMaxLength = -1;
            }
            if (this.counterEnabled && this.counterView != null) {
                EditText editText5 = this.editText;
                updateCounter(editText5 == null ? null : editText5.getText());
            }
        }
        this.counterTextAppearance = obtainTintedStyledAttributes.getResourceId(22, 0);
        this.counterOverflowTextAppearance = obtainTintedStyledAttributes.getResourceId(20, 0);
        int i5 = obtainTintedStyledAttributes.getInt(8, 0);
        if (i5 != this.boxBackgroundMode) {
            this.boxBackgroundMode = i5;
            if (this.editText != null) {
                onApplyBoxBackgroundMode();
            }
        }
        indicatorViewController.errorViewContentDescription = text;
        AppCompatTextView appCompatTextView = indicatorViewController.errorView;
        if (appCompatTextView != null) {
            appCompatTextView.setContentDescription(text);
        }
        indicatorViewController.helperTextTextAppearance = resourceId2;
        AppCompatTextView appCompatTextView2 = indicatorViewController.helperTextView;
        if (appCompatTextView2 != null) {
            appCompatTextView2.setTextAppearance(resourceId2);
        }
        indicatorViewController.errorTextAppearance = resourceId;
        AppCompatTextView appCompatTextView3 = indicatorViewController.errorView;
        if (appCompatTextView3 != null) {
            indicatorViewController.textInputView.setTextAppearanceCompatWithErrorFallback(resourceId, appCompatTextView3);
        }
        if (this.placeholderTextView == null) {
            AppCompatTextView appCompatTextView4 = new AppCompatTextView(getContext());
            this.placeholderTextView = appCompatTextView4;
            appCompatTextView4.setId(com.android.systemui.R.id.textinput_placeholder);
            AppCompatTextView appCompatTextView5 = this.placeholderTextView;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            ViewCompat.Api16Impl.setImportantForAccessibility(appCompatTextView5, 2);
            Fade fade = new Fade();
            fade.mDuration = 87L;
            fade.mInterpolator = timeInterpolator;
            this.placeholderFadeIn = fade;
            fade.mStartDelay = 67L;
            Fade fade2 = new Fade();
            fade2.mDuration = 87L;
            fade2.mInterpolator = timeInterpolator;
            this.placeholderFadeOut = fade2;
            int i6 = this.placeholderTextAppearance;
            this.placeholderTextAppearance = i6;
            AppCompatTextView appCompatTextView6 = this.placeholderTextView;
            if (appCompatTextView6 != null) {
                appCompatTextView6.setTextAppearance(i6);
            }
        }
        if (TextUtils.isEmpty(text3)) {
            setPlaceholderTextEnabled(false);
        } else {
            if (!this.placeholderEnabled) {
                setPlaceholderTextEnabled(true);
            }
            this.placeholderText = text3;
        }
        EditText editText6 = this.editText;
        updatePlaceholderText(editText6 == null ? null : editText6.getText());
        this.placeholderTextAppearance = resourceId3;
        AppCompatTextView appCompatTextView7 = this.placeholderTextView;
        if (appCompatTextView7 != null) {
            appCompatTextView7.setTextAppearance(resourceId3);
        }
        if (obtainTintedStyledAttributes.hasValue(36)) {
            ColorStateList colorStateList10 = obtainTintedStyledAttributes.getColorStateList(36);
            indicatorViewController.errorViewTextColor = colorStateList10;
            AppCompatTextView appCompatTextView8 = indicatorViewController.errorView;
            if (appCompatTextView8 != null && colorStateList10 != null) {
                appCompatTextView8.setTextColor(colorStateList10);
            }
        }
        if (obtainTintedStyledAttributes.hasValue(41)) {
            ColorStateList colorStateList11 = obtainTintedStyledAttributes.getColorStateList(41);
            indicatorViewController.helperTextViewTextColor = colorStateList11;
            AppCompatTextView appCompatTextView9 = indicatorViewController.helperTextView;
            if (appCompatTextView9 != null && colorStateList11 != null) {
                appCompatTextView9.setTextColor(colorStateList11);
            }
        }
        if (obtainTintedStyledAttributes.hasValue(45) && this.focusedTextColor != (colorStateList4 = obtainTintedStyledAttributes.getColorStateList(45))) {
            if (this.defaultHintTextColor == null) {
                collapsingTextHelper.setCollapsedTextColor(colorStateList4);
            }
            this.focusedTextColor = colorStateList4;
            if (this.editText != null) {
                updateLabelState(false, false);
            }
        }
        if (obtainTintedStyledAttributes.hasValue(23) && this.counterTextColor != (colorStateList3 = obtainTintedStyledAttributes.getColorStateList(23))) {
            this.counterTextColor = colorStateList3;
            updateCounterTextAppearanceAndColor();
        }
        if (obtainTintedStyledAttributes.hasValue(21) && this.counterOverflowTextColor != (colorStateList2 = obtainTintedStyledAttributes.getColorStateList(21))) {
            this.counterOverflowTextColor = colorStateList2;
            updateCounterTextAppearanceAndColor();
        }
        if (obtainTintedStyledAttributes.hasValue(53) && this.placeholderTextColor != (colorStateList = obtainTintedStyledAttributes.getColorStateList(53))) {
            this.placeholderTextColor = colorStateList;
            AppCompatTextView appCompatTextView10 = this.placeholderTextView;
            if (appCompatTextView10 != null && colorStateList != null) {
                appCompatTextView10.setTextColor(colorStateList);
            }
        }
        EndCompoundLayout endCompoundLayout = new EndCompoundLayout(this, obtainTintedStyledAttributes);
        this.endLayout = endCompoundLayout;
        boolean z4 = obtainTintedStyledAttributes.getBoolean(0, true);
        obtainTintedStyledAttributes.recycle();
        WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
        ViewCompat.Api16Impl.setImportantForAccessibility(this, 2);
        ViewCompat.Api26Impl.setImportantForAutofill(this, 1);
        frameLayout.addView(startCompoundLayout);
        frameLayout.addView(endCompoundLayout);
        addView(frameLayout);
        setEnabled(z4);
        setHelperTextEnabled(z2);
        setErrorEnabled(z);
        if (this.counterEnabled != z3) {
            if (z3) {
                AppCompatTextView appCompatTextView11 = new AppCompatTextView(getContext());
                this.counterView = appCompatTextView11;
                appCompatTextView11.setId(com.android.systemui.R.id.textinput_counter);
                this.counterView.setMaxLines(1);
                indicatorViewController.addIndicator(2, this.counterView);
                ((ViewGroup.MarginLayoutParams) this.counterView.getLayoutParams()).setMarginStart(getResources().getDimensionPixelOffset(com.android.systemui.R.dimen.mtrl_textinput_counter_margin_start));
                updateCounterTextAppearanceAndColor();
                if (this.counterView != null) {
                    EditText editText7 = this.editText;
                    updateCounter(editText7 == null ? null : editText7.getText());
                }
            } else {
                indicatorViewController.removeIndicator(2, this.counterView);
                this.counterView = null;
            }
            this.counterEnabled = z3;
        }
        if (TextUtils.isEmpty(text2)) {
            if (indicatorViewController.helperTextEnabled) {
                setHelperTextEnabled(false);
                return;
            }
            return;
        }
        if (!indicatorViewController.helperTextEnabled) {
            setHelperTextEnabled(true);
        }
        indicatorViewController.cancelCaptionAnimator();
        indicatorViewController.helperText = text2;
        indicatorViewController.helperTextView.setText(text2);
        int i7 = indicatorViewController.captionDisplayed;
        if (i7 != 2) {
            indicatorViewController.captionToShow = 2;
        }
        indicatorViewController.updateCaptionViewsVisibility(i7, indicatorViewController.captionToShow, indicatorViewController.shouldAnimateCaptionView(indicatorViewController.helperTextView, text2));
    }
}
