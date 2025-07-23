package com.google.android.material.textfield;

import android.R;
import android.animation.TimeInterpolator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Parcel;
import android.os.Parcelable;
import android.text.Editable;
import android.text.SpannableStringBuilder;
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
import android.view.ViewTreeObserver;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import androidx.appcompat.widget.AppCompatDrawableManager;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.appcompat.widget.DrawableUtils;
import androidx.appcompat.widget.ResourceManagerInternal;
import androidx.appcompat.widget.TintTypedArray;
import androidx.core.content.res.ResourcesCompat;
import androidx.core.graphics.ColorUtils;
import androidx.core.text.BidiFormatter;
import androidx.core.view.AccessibilityDelegateCompat;
import androidx.core.view.ViewCompat;
import androidx.core.view.accessibility.AccessibilityNodeInfoCompat;
import androidx.customview.view.AbsSavedState;
import androidx.transition.Fade;
import androidx.transition.TransitionManager;
import com.google.android.material.R$styleable;
import com.google.android.material.animation.AnimationUtils;
import com.google.android.material.color.MaterialColors;
import com.google.android.material.internal.CollapsingTextHelper;
import com.google.android.material.internal.DescendantOffsetUtils;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.internal.ViewUtils;
import com.google.android.material.motion.MotionUtils;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.shape.CornerTreatment;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.RelativeCornerSize;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.google.android.material.textfield.CutoutDrawable;
import com.google.android.material.textfield.EndCompoundLayout;
import com.google.android.material.theme.overlay.MaterialThemeOverlay;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.WeakHashMap;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class TextInputLayout extends LinearLayout implements ViewTreeObserver.OnGlobalLayoutListener {
    public static final int[][] EDIT_TEXT_BACKGROUND_RIPPLE_STATE = {new int[]{R.attr.state_pressed}, new int[0]};
    public ValueAnimator animator;
    public boolean areCornerRadiiRtl;
    public MaterialShapeDrawable boxBackground;
    public boolean boxBackgroundApplied;
    public int boxBackgroundColor;
    public final int boxBackgroundMode;
    public int boxCollapsedPaddingTopPx;
    public final int boxLabelCutoutPaddingPx;
    public int boxStrokeColor;
    public final int boxStrokeWidthDefaultPx;
    public final int boxStrokeWidthFocusedPx;
    public int boxStrokeWidthPx;
    public MaterialShapeDrawable boxUnderlineDefault;
    public MaterialShapeDrawable boxUnderlineFocused;
    public final CollapsingTextHelper collapsingTextHelper;
    public final boolean counterEnabled;
    public final int counterMaxLength;
    public final int counterOverflowTextAppearance;
    public final ColorStateList counterOverflowTextColor;
    public boolean counterOverflowed;
    public final int counterTextAppearance;
    public final ColorStateList counterTextColor;
    public final AppCompatTextView counterView;
    public final ColorStateList cursorColor;
    public final ColorStateList cursorErrorColor;
    public final int defaultFilledBackgroundColor;
    public ColorStateList defaultHintTextColor;
    public final int defaultStrokeColor;
    public final int disabledColor;
    public final int disabledFilledBackgroundColor;
    public EditText editText;
    public final LinkedHashSet editTextAttachedListeners;
    public Drawable endDummyDrawable;
    public int endDummyDrawableWidth;
    public final EndCompoundLayout endLayout;
    public final boolean expandedHintEnabled;
    public StateListDrawable filledDropDownMenuBackground;
    public final int focusedFilledBackgroundColor;
    public final int focusedStrokeColor;
    public final ColorStateList focusedTextColor;
    public boolean globalLayoutListenerAdded;
    public CharSequence hint;
    public final boolean hintAnimationEnabled;
    public final boolean hintEnabled;
    public boolean hintExpanded;
    public final int hoveredFilledBackgroundColor;
    public final int hoveredStrokeColor;
    public boolean inDrawableStateChanged;
    public final IndicatorViewController indicatorViewController;
    public final FrameLayout inputFrame;
    public boolean isProvidingHint;
    public final TextInputLayout$$ExternalSyntheticLambda1 lengthCounter;
    public int maxEms;
    public int maxWidth;
    public int minEms;
    public int minWidth;
    public Drawable originalEditTextEndDrawable;
    public int originalEditTextMinimumHeight;
    public CharSequence originalHint;
    public MaterialShapeDrawable outlinedDropDownMenuBackground;
    public boolean placeholderEnabled;
    public final Fade placeholderFadeIn;
    public final Fade placeholderFadeOut;
    public final CharSequence placeholderText;
    public final int placeholderTextAppearance;
    public final ColorStateList placeholderTextColor;
    public AppCompatTextView placeholderTextView;
    public boolean restoringSavedState;
    public ShapeAppearanceModel shapeAppearanceModel;
    public Drawable startDummyDrawable;
    public int startDummyDrawableWidth;
    public final StartCompoundLayout startLayout;
    public final ColorStateList strokeErrorColor;
    public final Rect tmpBoundsRect;
    public final Rect tmpRect;
    public final RectF tmpRectF;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class AccessibilityDelegate extends AccessibilityDelegateCompat {
        public final TextInputLayout layout;

        public AccessibilityDelegate(TextInputLayout textInputLayout) {
            this.layout = textInputLayout;
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public final void onInitializeAccessibilityNodeInfo(View view, AccessibilityNodeInfoCompat accessibilityNodeInfoCompat) {
            AppCompatTextView appCompatTextView;
            this.mOriginalDelegate.onInitializeAccessibilityNodeInfo(view, accessibilityNodeInfoCompat.mInfo);
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
            boolean isEmpty = TextUtils.isEmpty(text);
            boolean isEmpty2 = TextUtils.isEmpty(charSequence2);
            boolean z = textInputLayout.hintExpanded;
            boolean isEmpty3 = TextUtils.isEmpty(charSequence3);
            boolean z2 = (isEmpty3 && TextUtils.isEmpty(charSequence)) ? false : true;
            String charSequence5 = !isEmpty2 ? charSequence2.toString() : "";
            StartCompoundLayout startCompoundLayout = textInputLayout.startLayout;
            if (startCompoundLayout.prefixTextView.getVisibility() == 0) {
                accessibilityNodeInfoCompat.mInfo.setLabelFor(startCompoundLayout.prefixTextView);
                accessibilityNodeInfoCompat.mInfo.setTraversalAfter(startCompoundLayout.prefixTextView);
            } else {
                accessibilityNodeInfoCompat.mInfo.setTraversalAfter(startCompoundLayout.startIconView);
            }
            if (!isEmpty) {
                accessibilityNodeInfoCompat.setText(text);
            } else if (!TextUtils.isEmpty(charSequence5)) {
                accessibilityNodeInfoCompat.setText(charSequence5);
                if (!z && charSequence4 != null) {
                    accessibilityNodeInfoCompat.setText(charSequence5 + ", " + ((Object) charSequence4));
                }
            } else if (charSequence4 != null) {
                accessibilityNodeInfoCompat.setText(charSequence4);
            }
            if (!TextUtils.isEmpty(charSequence5)) {
                accessibilityNodeInfoCompat.mInfo.setHintText(charSequence5);
                accessibilityNodeInfoCompat.mInfo.setShowingHintText(isEmpty);
            }
            if (text == null || text.length() != i) {
                i = -1;
            }
            accessibilityNodeInfoCompat.mInfo.setMaxTextLength(i);
            if (z2) {
                if (isEmpty3) {
                    charSequence3 = charSequence;
                }
                accessibilityNodeInfoCompat.mInfo.setError(charSequence3);
            }
            AppCompatTextView appCompatTextView2 = textInputLayout.indicatorViewController.helperTextView;
            if (appCompatTextView2 != null) {
                accessibilityNodeInfoCompat.mInfo.setLabelFor(appCompatTextView2);
            }
            textInputLayout.endLayout.getEndIconDelegate().onInitializeAccessibilityNodeInfo(accessibilityNodeInfoCompat);
        }

        @Override // androidx.core.view.AccessibilityDelegateCompat
        public final void onPopulateAccessibilityEvent(View view, AccessibilityEvent accessibilityEvent) {
            super.onPopulateAccessibilityEvent(view, accessibilityEvent);
            this.layout.endLayout.getEndIconDelegate().onPopulateAccessibilityEvent(accessibilityEvent);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SavedState extends AbsSavedState {
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
            super.writeToParcel(parcel, i);
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
        WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
        this.originalEditTextMinimumHeight = editText.getMinimumHeight();
        this.editText.addTextChangedListener(new TextWatcher(editText) { // from class: com.google.android.material.textfield.TextInputLayout.1
            public int previousLineCount;
            public final /* synthetic */ EditText val$editText;

            {
                this.val$editText = editText;
                this.previousLineCount = editText.getLineCount();
            }

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
                int lineCount = this.val$editText.getLineCount();
                int i7 = this.previousLineCount;
                if (lineCount != i7) {
                    if (lineCount < i7) {
                        EditText editText5 = this.val$editText;
                        WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                        int minimumHeight = editText5.getMinimumHeight();
                        int i8 = TextInputLayout.this.originalEditTextMinimumHeight;
                        if (minimumHeight != i8) {
                            this.val$editText.setMinimumHeight(i8);
                        }
                    }
                    this.previousLineCount = lineCount;
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
        updateCursorColor();
        if (this.counterView != null) {
            updateCounter(this.editText.getText());
        }
        updateEditTextBackground();
        this.indicatorViewController.adjustIndicatorPadding();
        this.startLayout.bringToFront();
        this.endLayout.bringToFront();
        Iterator it = this.editTextAttachedListeners.iterator();
        while (it.hasNext()) {
            ((EndCompoundLayout.AnonymousClass2) it.next()).onEditTextAttached(this);
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
            valueAnimator.setInterpolator(MotionUtils.resolveThemeInterpolator(getContext(), com.android.systemui.R.attr.motionEasingEmphasizedInterpolator, AnimationUtils.FAST_OUT_SLOW_IN_INTERPOLATOR));
            this.animator.setDuration(MotionUtils.resolveThemeDuration(getContext(), com.android.systemui.R.attr.motionDurationMedium4, 167));
            this.animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() { // from class: com.google.android.material.textfield.TextInputLayout.3
                @Override // android.animation.ValueAnimator.AnimatorUpdateListener
                public final void onAnimationUpdate(ValueAnimator valueAnimator2) {
                    TextInputLayout.this.collapsingTextHelper.setExpansionFraction(((Float) valueAnimator2.getAnimatedValue()).floatValue());
                }
            });
        }
        this.animator.setFloatValues(this.collapsingTextHelper.expandedFraction, f);
        this.animator.start();
    }

    public final void applyBoxAttributes() {
        int i;
        int i2;
        MaterialShapeDrawable materialShapeDrawable = this.boxBackground;
        if (materialShapeDrawable == null) {
            return;
        }
        ShapeAppearanceModel shapeAppearanceModel = materialShapeDrawable.drawableState.shapeAppearanceModel;
        ShapeAppearanceModel shapeAppearanceModel2 = this.shapeAppearanceModel;
        if (shapeAppearanceModel != shapeAppearanceModel2) {
            materialShapeDrawable.setShapeAppearanceModel(shapeAppearanceModel2);
        }
        if (this.boxBackgroundMode == 2 && (i = this.boxStrokeWidthPx) > -1 && (i2 = this.boxStrokeColor) != 0) {
            MaterialShapeDrawable materialShapeDrawable2 = this.boxBackground;
            materialShapeDrawable2.drawableState.strokeWidth = i;
            materialShapeDrawable2.invalidateSelf();
            materialShapeDrawable2.setStrokeColor(ColorStateList.valueOf(i2));
        }
        int i3 = this.boxBackgroundColor;
        if (this.boxBackgroundMode == 1) {
            i3 = ColorUtils.compositeColors(this.boxBackgroundColor, MaterialColors.getColor(getContext(), com.android.systemui.R.attr.colorSurface, 0));
        }
        this.boxBackgroundColor = i3;
        this.boxBackground.setFillColor(ColorStateList.valueOf(i3));
        MaterialShapeDrawable materialShapeDrawable3 = this.boxUnderlineDefault;
        if (materialShapeDrawable3 != null && this.boxUnderlineFocused != null) {
            if (this.boxStrokeWidthPx > -1 && this.boxStrokeColor != 0) {
                materialShapeDrawable3.setFillColor(this.editText.isFocused() ? ColorStateList.valueOf(this.defaultStrokeColor) : ColorStateList.valueOf(this.boxStrokeColor));
                this.boxUnderlineFocused.setFillColor(ColorStateList.valueOf(this.boxStrokeColor));
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

    public final Fade createPlaceholderFadeTransition() {
        Fade fade = new Fade();
        fade.mDuration = MotionUtils.resolveThemeDuration(getContext(), com.android.systemui.R.attr.motionDurationShort2, 87);
        fade.mInterpolator = MotionUtils.resolveThemeInterpolator(getContext(), com.android.systemui.R.attr.motionEasingLinearInterpolator, AnimationUtils.LINEAR_INTERPOLATOR);
        return fade;
    }

    public final boolean cutoutEnabled() {
        return this.hintEnabled && !TextUtils.isEmpty(this.hint) && (this.boxBackground instanceof CutoutDrawable);
    }

    public boolean cutoutIsOpen() {
        return cutoutEnabled() && !((CutoutDrawable) this.boxBackground).drawableState.cutoutBounds.isEmpty();
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

    /* JADX WARN: Removed duplicated region for block: B:14:0x0034  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x004f  */
    @Override // android.view.ViewGroup, android.view.View
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void drawableStateChanged() {
        /*
            r4 = this;
            boolean r0 = r4.inDrawableStateChanged
            if (r0 == 0) goto L5
            return
        L5:
            r0 = 1
            r4.inDrawableStateChanged = r0
            super.drawableStateChanged()
            int[] r1 = r4.getDrawableState()
            com.google.android.material.internal.CollapsingTextHelper r2 = r4.collapsingTextHelper
            r3 = 0
            if (r2 == 0) goto L2f
            r2.state = r1
            android.content.res.ColorStateList r1 = r2.collapsedTextColor
            if (r1 == 0) goto L20
            boolean r1 = r1.isStateful()
            if (r1 != 0) goto L2a
        L20:
            android.content.res.ColorStateList r1 = r2.expandedTextColor
            if (r1 == 0) goto L2f
            boolean r1 = r1.isStateful()
            if (r1 == 0) goto L2f
        L2a:
            r2.recalculate(r3)
            r1 = r0
            goto L30
        L2f:
            r1 = r3
        L30:
            android.widget.EditText r2 = r4.editText
            if (r2 == 0) goto L47
            java.util.WeakHashMap r2 = androidx.core.view.ViewCompat.sViewPropertyAnimatorMap
            boolean r2 = r4.isLaidOut()
            if (r2 == 0) goto L43
            boolean r2 = r4.isEnabled()
            if (r2 == 0) goto L43
            goto L44
        L43:
            r0 = r3
        L44:
            r4.updateLabelState(r0, r3)
        L47:
            r4.updateEditTextBackground()
            r4.updateTextInputBoxState()
            if (r1 == 0) goto L52
            r4.invalidate()
        L52:
            r4.inDrawableStateChanged = r3
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.textfield.TextInputLayout.drawableStateChanged():void");
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
        float dimensionPixelOffset = getResources().getDimensionPixelOffset(com.android.systemui.R.dimen.mtrl_shape_corner_size_small_component);
        float f = z ? dimensionPixelOffset : 0.0f;
        EditText editText = this.editText;
        float dimensionPixelOffset2 = editText instanceof MaterialAutoCompleteTextView ? ((MaterialAutoCompleteTextView) editText).popupElevation : getResources().getDimensionPixelOffset(com.android.systemui.R.dimen.m3_comp_outlined_autocomplete_menu_container_elevation);
        int dimensionPixelOffset3 = getResources().getDimensionPixelOffset(com.android.systemui.R.dimen.mtrl_exposed_dropdown_menu_popup_vertical_padding);
        RelativeCornerSize relativeCornerSize = ShapeAppearanceModel.PILL;
        ShapeAppearanceModel.Builder builder = new ShapeAppearanceModel.Builder();
        builder.setTopLeftCornerSize(f);
        builder.setTopRightCornerSize(f);
        builder.setBottomLeftCornerSize(dimensionPixelOffset);
        builder.setBottomRightCornerSize(dimensionPixelOffset);
        ShapeAppearanceModel build = builder.build();
        EditText editText2 = this.editText;
        ColorStateList colorStateList = editText2 instanceof MaterialAutoCompleteTextView ? ((MaterialAutoCompleteTextView) editText2).dropDownBackgroundTint : null;
        Context context = getContext();
        if (colorStateList == null) {
            Paint paint = MaterialShapeDrawable.clearPaint;
            TypedValue resolveTypedValueOrThrow = MaterialAttributes.resolveTypedValueOrThrow(context, "MaterialShapeDrawable", com.android.systemui.R.attr.colorSurface);
            int i = resolveTypedValueOrThrow.resourceId;
            colorStateList = ColorStateList.valueOf(i != 0 ? context.getColor(i) : resolveTypedValueOrThrow.data);
        }
        MaterialShapeDrawable materialShapeDrawable = new MaterialShapeDrawable();
        materialShapeDrawable.initializeElevationOverlay(context);
        materialShapeDrawable.setFillColor(colorStateList);
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

    public final int getLabelLeftBoundAlignedWithPrefixAndSuffix(int i, boolean z) {
        int compoundPaddingLeft;
        if (!z) {
            StartCompoundLayout startCompoundLayout = this.startLayout;
            if (startCompoundLayout.prefixText != null) {
                compoundPaddingLeft = startCompoundLayout.getPrefixTextStartOffset();
                return compoundPaddingLeft + i;
            }
        }
        if (z) {
            EndCompoundLayout endCompoundLayout = this.endLayout;
            if (endCompoundLayout.suffixText != null) {
                compoundPaddingLeft = endCompoundLayout.getSuffixTextEndOffset();
                return compoundPaddingLeft + i;
            }
        }
        compoundPaddingLeft = this.editText.getCompoundPaddingLeft();
        return compoundPaddingLeft + i;
    }

    public final int getLabelRightBoundAlignedWithPrefixAndSuffix(int i, boolean z) {
        int compoundPaddingRight;
        if (!z) {
            EndCompoundLayout endCompoundLayout = this.endLayout;
            if (endCompoundLayout.suffixText != null) {
                compoundPaddingRight = endCompoundLayout.getSuffixTextEndOffset();
                return i - compoundPaddingRight;
            }
        }
        if (z) {
            StartCompoundLayout startCompoundLayout = this.startLayout;
            if (startCompoundLayout.prefixText != null) {
                compoundPaddingRight = startCompoundLayout.getPrefixTextStartOffset();
                return i - compoundPaddingRight;
            }
        }
        compoundPaddingRight = this.editText.getCompoundPaddingRight();
        return i - compoundPaddingRight;
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
                throw new IllegalArgumentException(ReorderTile$$ExternalSyntheticOutline0.m(this.boxBackgroundMode, " is illegal; only @BoxBackgroundMode constants are supported.", new StringBuilder()));
            }
            if (!this.hintEnabled || (this.boxBackground instanceof CutoutDrawable)) {
                this.boxBackground = new MaterialShapeDrawable(this.shapeAppearanceModel);
            } else {
                ShapeAppearanceModel shapeAppearanceModel = this.shapeAppearanceModel;
                int i2 = CutoutDrawable.$r8$clinit;
                if (shapeAppearanceModel == null) {
                    shapeAppearanceModel = new ShapeAppearanceModel();
                }
                this.boxBackground = new CutoutDrawable.ImplApi18(new CutoutDrawable.CutoutDrawableState(shapeAppearanceModel, new RectF()));
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
                editText.setPaddingRelative(editText.getPaddingStart(), getResources().getDimensionPixelSize(com.android.systemui.R.dimen.material_filled_edittext_font_2_0_padding_top), this.editText.getPaddingEnd(), getResources().getDimensionPixelSize(com.android.systemui.R.dimen.material_filled_edittext_font_2_0_padding_bottom));
            } else if (MaterialResources.isFontScaleAtLeast1_3(getContext())) {
                EditText editText2 = this.editText;
                WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
                editText2.setPaddingRelative(editText2.getPaddingStart(), getResources().getDimensionPixelSize(com.android.systemui.R.dimen.material_filled_edittext_font_1_3_padding_top), this.editText.getPaddingEnd(), getResources().getDimensionPixelSize(com.android.systemui.R.dimen.material_filled_edittext_font_1_3_padding_bottom));
            }
        }
        if (this.boxBackgroundMode != 0) {
            updateInputLayoutMargins();
        }
        EditText editText3 = this.editText;
        if (editText3 instanceof AutoCompleteTextView) {
            AutoCompleteTextView autoCompleteTextView = (AutoCompleteTextView) editText3;
            if (autoCompleteTextView.getDropDownBackground() == null) {
                int i3 = this.boxBackgroundMode;
                if (i3 == 2) {
                    if (this.outlinedDropDownMenuBackground == null) {
                        this.outlinedDropDownMenuBackground = getDropDownMaterialShapeDrawable(true);
                    }
                    autoCompleteTextView.setDropDownBackgroundDrawable(this.outlinedDropDownMenuBackground);
                } else if (i3 == 1) {
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

    @Override // android.view.ViewTreeObserver.OnGlobalLayoutListener
    public final void onGlobalLayout() {
        int max;
        this.endLayout.getViewTreeObserver().removeOnGlobalLayoutListener(this);
        boolean z = false;
        this.globalLayoutListenerAdded = false;
        if (this.editText != null && this.editText.getMeasuredHeight() < (max = Math.max(this.endLayout.getMeasuredHeight(), this.startLayout.getMeasuredHeight()))) {
            this.editText.setMinimumHeight(max);
            z = true;
        }
        boolean updateDummyDrawables = updateDummyDrawables();
        if (z || updateDummyDrawables) {
            this.editText.post(new Runnable() { // from class: com.google.android.material.textfield.TextInputLayout$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    TextInputLayout.this.editText.requestLayout();
                }
            });
        }
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
                    rect2.left = getLabelLeftBoundAlignedWithPrefixAndSuffix(rect.left, isLayoutRtl);
                    rect2.top = rect.top + this.boxCollapsedPaddingTopPx;
                    rect2.right = getLabelRightBoundAlignedWithPrefixAndSuffix(rect.right, isLayoutRtl);
                } else if (i8 != 2) {
                    rect2.left = getLabelLeftBoundAlignedWithPrefixAndSuffix(rect.left, isLayoutRtl);
                    rect2.top = getPaddingTop();
                    rect2.right = getLabelRightBoundAlignedWithPrefixAndSuffix(rect.right, isLayoutRtl);
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
                if (rect3.left != i9 || rect3.top != i10 || rect3.right != i11 || rect3.bottom != i12) {
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
                float f = -collapsingTextHelper5.tmpPaint.ascent();
                rect4.left = this.editText.getCompoundPaddingLeft() + rect.left;
                rect4.top = (this.boxBackgroundMode != 1 || this.editText.getMinLines() > 1) ? rect.top + this.editText.getCompoundPaddingTop() : (int) (rect.centerY() - (f / 2.0f));
                rect4.right = rect.right - this.editText.getCompoundPaddingRight();
                int compoundPaddingBottom = (this.boxBackgroundMode != 1 || this.editText.getMinLines() > 1) ? rect.bottom - this.editText.getCompoundPaddingBottom() : (int) (rect4.top + f);
                rect4.bottom = compoundPaddingBottom;
                int i13 = rect4.left;
                int i14 = rect4.top;
                int i15 = rect4.right;
                Rect rect5 = collapsingTextHelper5.expandedBounds;
                if (rect5.left != i13 || rect5.top != i14 || rect5.right != i15 || rect5.bottom != compoundPaddingBottom) {
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
        EditText editText;
        super.onMeasure(i, i2);
        if (!this.globalLayoutListenerAdded) {
            this.endLayout.getViewTreeObserver().addOnGlobalLayoutListener(this);
            this.globalLayoutListenerAdded = true;
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
        boolean z = i == 1;
        if (z != this.areCornerRadiiRtl) {
            float cornerSize = this.shapeAppearanceModel.topLeftCornerSize.getCornerSize(this.tmpRectF);
            float cornerSize2 = this.shapeAppearanceModel.topRightCornerSize.getCornerSize(this.tmpRectF);
            float cornerSize3 = this.shapeAppearanceModel.bottomLeftCornerSize.getCornerSize(this.tmpRectF);
            float cornerSize4 = this.shapeAppearanceModel.bottomRightCornerSize.getCornerSize(this.tmpRectF);
            ShapeAppearanceModel shapeAppearanceModel = this.shapeAppearanceModel;
            CornerTreatment cornerTreatment = shapeAppearanceModel.topLeftCorner;
            CornerTreatment cornerTreatment2 = shapeAppearanceModel.topRightCorner;
            CornerTreatment cornerTreatment3 = shapeAppearanceModel.bottomLeftCorner;
            CornerTreatment cornerTreatment4 = shapeAppearanceModel.bottomRightCorner;
            ShapeAppearanceModel.Builder builder = new ShapeAppearanceModel.Builder();
            builder.topLeftCorner = cornerTreatment2;
            float compatCornerTreatmentSize = ShapeAppearanceModel.Builder.compatCornerTreatmentSize(cornerTreatment2);
            if (compatCornerTreatmentSize != -1.0f) {
                builder.setTopLeftCornerSize(compatCornerTreatmentSize);
            }
            builder.topRightCorner = cornerTreatment;
            float compatCornerTreatmentSize2 = ShapeAppearanceModel.Builder.compatCornerTreatmentSize(cornerTreatment);
            if (compatCornerTreatmentSize2 != -1.0f) {
                builder.setTopRightCornerSize(compatCornerTreatmentSize2);
            }
            builder.bottomLeftCorner = cornerTreatment4;
            float compatCornerTreatmentSize3 = ShapeAppearanceModel.Builder.compatCornerTreatmentSize(cornerTreatment4);
            if (compatCornerTreatmentSize3 != -1.0f) {
                builder.setBottomLeftCornerSize(compatCornerTreatmentSize3);
            }
            builder.bottomRightCorner = cornerTreatment3;
            float compatCornerTreatmentSize4 = ShapeAppearanceModel.Builder.compatCornerTreatmentSize(cornerTreatment3);
            if (compatCornerTreatmentSize4 != -1.0f) {
                builder.setBottomRightCornerSize(compatCornerTreatmentSize4);
            }
            builder.setTopLeftCornerSize(cornerSize2);
            builder.setTopRightCornerSize(cornerSize);
            builder.setBottomLeftCornerSize(cornerSize4);
            builder.setBottomRightCornerSize(cornerSize3);
            ShapeAppearanceModel build = builder.build();
            this.areCornerRadiiRtl = z;
            MaterialShapeDrawable materialShapeDrawable = this.boxBackground;
            if (materialShapeDrawable == null || materialShapeDrawable.drawableState.shapeAppearanceModel == build) {
                return;
            }
            this.shapeAppearanceModel = build;
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
        savedState.isEndIconChecked = endCompoundLayout.endIconMode != 0 && endCompoundLayout.endIconView.checked;
        return savedState;
    }

    public final void openCutout() {
        float f;
        float f2;
        float f3;
        float f4;
        int i;
        float f5;
        int i2;
        if (cutoutEnabled()) {
            RectF rectF = this.tmpRectF;
            CollapsingTextHelper collapsingTextHelper = this.collapsingTextHelper;
            int width = this.editText.getWidth();
            int gravity = this.editText.getGravity();
            boolean calculateIsRtl = collapsingTextHelper.calculateIsRtl(collapsingTextHelper.text);
            collapsingTextHelper.isRtl = calculateIsRtl;
            if (gravity != 17 && (gravity & 7) != 1) {
                if ((gravity & 8388613) == 8388613 || (gravity & 5) == 5) {
                    if (calculateIsRtl) {
                        i2 = collapsingTextHelper.collapsedBounds.left;
                        f3 = i2;
                    } else {
                        f = collapsingTextHelper.collapsedBounds.right;
                        f2 = collapsingTextHelper.collapsedTextWidth;
                    }
                } else if (calculateIsRtl) {
                    f = collapsingTextHelper.collapsedBounds.right;
                    f2 = collapsingTextHelper.collapsedTextWidth;
                } else {
                    i2 = collapsingTextHelper.collapsedBounds.left;
                    f3 = i2;
                }
                float max = Math.max(f3, collapsingTextHelper.collapsedBounds.left);
                rectF.left = max;
                Rect rect = collapsingTextHelper.collapsedBounds;
                rectF.top = rect.top;
                if (gravity != 17 || (gravity & 7) == 1) {
                    f4 = (width / 2.0f) + (collapsingTextHelper.collapsedTextWidth / 2.0f);
                } else if ((gravity & 8388613) == 8388613 || (gravity & 5) == 5) {
                    if (collapsingTextHelper.isRtl) {
                        f5 = collapsingTextHelper.collapsedTextWidth;
                        f4 = f5 + max;
                    } else {
                        i = rect.right;
                        f4 = i;
                    }
                } else if (collapsingTextHelper.isRtl) {
                    i = rect.right;
                    f4 = i;
                } else {
                    f5 = collapsingTextHelper.collapsedTextWidth;
                    f4 = f5 + max;
                }
                rectF.right = Math.min(f4, rect.right);
                rectF.bottom = collapsingTextHelper.getCollapsedTextHeight() + collapsingTextHelper.collapsedBounds.top;
                if (rectF.width() > 0.0f || rectF.height() <= 0.0f) {
                }
                float f6 = rectF.left;
                float f7 = this.boxLabelCutoutPaddingPx;
                rectF.left = f6 - f7;
                rectF.right += f7;
                rectF.offset(-getPaddingLeft(), ((-getPaddingTop()) - (rectF.height() / 2.0f)) + this.boxStrokeWidthPx);
                CutoutDrawable cutoutDrawable = (CutoutDrawable) this.boxBackground;
                cutoutDrawable.getClass();
                cutoutDrawable.setCutout(rectF.left, rectF.top, rectF.right, rectF.bottom);
                return;
            }
            f = width / 2.0f;
            f2 = collapsingTextHelper.collapsedTextWidth / 2.0f;
            f3 = f - f2;
            float max2 = Math.max(f3, collapsingTextHelper.collapsedBounds.left);
            rectF.left = max2;
            Rect rect2 = collapsingTextHelper.collapsedBounds;
            rectF.top = rect2.top;
            if (gravity != 17) {
            }
            f4 = (width / 2.0f) + (collapsingTextHelper.collapsedTextWidth / 2.0f);
            rectF.right = Math.min(f4, rect2.right);
            rectF.bottom = collapsingTextHelper.getCollapsedTextHeight() + collapsingTextHelper.collapsedBounds.top;
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
                textInputLayout.setTextAppearanceCompatWithErrorFallback(appCompatTextView2, i);
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
            int i2 = indicatorViewController.errorViewAccessibilityLiveRegion;
            indicatorViewController.errorViewAccessibilityLiveRegion = i2;
            AppCompatTextView appCompatTextView5 = indicatorViewController.errorView;
            if (appCompatTextView5 != null) {
                WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
                appCompatTextView5.setAccessibilityLiveRegion(i2);
            }
            indicatorViewController.errorView.setVisibility(4);
            indicatorViewController.addIndicator(indicatorViewController.errorView, 0);
        } else {
            indicatorViewController.hideError();
            indicatorViewController.removeIndicator(indicatorViewController.errorView, 0);
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
            appCompatTextView2.setAccessibilityLiveRegion(1);
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
            indicatorViewController.addIndicator(indicatorViewController.helperTextView, 1);
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
            indicatorViewController.removeIndicator(indicatorViewController.helperTextView, 1);
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

    public final void setTextAppearanceCompatWithErrorFallback(AppCompatTextView appCompatTextView, int i) {
        try {
            appCompatTextView.setTextAppearance(i);
            if (appCompatTextView.getTextColors().getDefaultColor() != -65281) {
                return;
            }
        } catch (Exception unused) {
        }
        appCompatTextView.setTextAppearance(2132018351);
        appCompatTextView.setTextColor(getContext().getColor(com.android.systemui.R.color.design_error));
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
        String str = null;
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
            BidiFormatter bidiFormatter = BidiFormatter.getInstance();
            AppCompatTextView appCompatTextView = this.counterView;
            String string = getContext().getString(com.android.systemui.R.string.character_counter_pattern, Integer.valueOf(length), Integer.valueOf(this.counterMaxLength));
            if (string == null) {
                bidiFormatter.getClass();
            } else {
                str = ((SpannableStringBuilder) bidiFormatter.unicodeWrap(string, bidiFormatter.mDefaultTextDirectionHeuristicCompat)).toString();
            }
            appCompatTextView.setText(str);
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
            setTextAppearanceCompatWithErrorFallback(appCompatTextView, this.counterOverflowed ? this.counterOverflowTextAppearance : this.counterTextAppearance);
            if (!this.counterOverflowed && (colorStateList2 = this.counterTextColor) != null) {
                this.counterView.setTextColor(colorStateList2);
            }
            if (!this.counterOverflowed || (colorStateList = this.counterOverflowTextColor) == null) {
                return;
            }
            this.counterView.setTextColor(colorStateList);
        }
    }

    public final void updateCursorColor() {
        ColorStateList colorStateList;
        ColorStateList colorStateList2 = this.cursorColor;
        if (colorStateList2 == null) {
            Context context = getContext();
            TypedValue resolve = MaterialAttributes.resolve(com.android.systemui.R.attr.colorControlActivated, context);
            if (resolve != null) {
                int i = resolve.resourceId;
                if (i != 0) {
                    colorStateList2 = ResourcesCompat.getColorStateList(i, context.getTheme(), context.getResources());
                } else {
                    int i2 = resolve.data;
                    if (i2 != 0) {
                        colorStateList2 = ColorStateList.valueOf(i2);
                    }
                }
            }
            colorStateList2 = null;
        }
        EditText editText = this.editText;
        if (editText == null || editText.getTextCursorDrawable() == null) {
            return;
        }
        Drawable mutate = this.editText.getTextCursorDrawable().mutate();
        if ((shouldShowError() || (this.counterView != null && this.counterOverflowed)) && (colorStateList = this.cursorErrorColor) != null) {
            colorStateList2 = colorStateList;
        }
        mutate.setTintList(colorStateList2);
    }

    /* JADX WARN: Code restructure failed: missing block: B:10:0x0020, code lost:
    
        if (r0.prefixTextView.getVisibility() == 0) goto L11;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x008f, code lost:
    
        if (r6.isEndIconVisible() != false) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:22:0x0095, code lost:
    
        if (r10.endLayout.suffixText != null) goto L34;
     */
    /* JADX WARN: Removed duplicated region for block: B:13:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0085  */
    /* JADX WARN: Removed duplicated region for block: B:25:0x011f  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x009f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean updateDummyDrawables() {
        /*
            Method dump skipped, instructions count: 318
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.google.android.material.textfield.TextInputLayout.updateDummyDrawables():boolean");
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
        EditText editText = this.editText;
        if (editText == null || this.boxBackground == null) {
            return;
        }
        if ((this.boxBackgroundApplied || editText.getBackground() == null) && this.boxBackgroundMode != 0) {
            EditText editText2 = this.editText;
            if (!(editText2 instanceof AutoCompleteTextView) || EditTextUtils.isEditable(editText2)) {
                drawable = this.boxBackground;
            } else {
                int color = MaterialColors.getColor(this.editText, com.android.systemui.R.attr.colorControlHighlight);
                int i = this.boxBackgroundMode;
                int[][] iArr = EDIT_TEXT_BACKGROUND_RIPPLE_STATE;
                if (i == 2) {
                    Context context = getContext();
                    MaterialShapeDrawable materialShapeDrawable = this.boxBackground;
                    TypedValue resolveTypedValueOrThrow = MaterialAttributes.resolveTypedValueOrThrow(context, "TextInputLayout", com.android.systemui.R.attr.colorSurface);
                    int i2 = resolveTypedValueOrThrow.resourceId;
                    int color2 = i2 != 0 ? context.getColor(i2) : resolveTypedValueOrThrow.data;
                    MaterialShapeDrawable materialShapeDrawable2 = new MaterialShapeDrawable(materialShapeDrawable.drawableState.shapeAppearanceModel);
                    int layer = MaterialColors.layer(0.1f, color, color2);
                    materialShapeDrawable2.setFillColor(new ColorStateList(iArr, new int[]{layer, 0}));
                    materialShapeDrawable2.setTint(color2);
                    ColorStateList colorStateList = new ColorStateList(iArr, new int[]{layer, color2});
                    MaterialShapeDrawable materialShapeDrawable3 = new MaterialShapeDrawable(materialShapeDrawable.drawableState.shapeAppearanceModel);
                    materialShapeDrawable3.setTint(-1);
                    drawable = new LayerDrawable(new Drawable[]{new RippleDrawable(colorStateList, materialShapeDrawable2, materialShapeDrawable3), materialShapeDrawable});
                } else if (i == 1) {
                    MaterialShapeDrawable materialShapeDrawable4 = this.boxBackground;
                    int i3 = this.boxBackgroundColor;
                    drawable = new RippleDrawable(new ColorStateList(iArr, new int[]{MaterialColors.layer(0.1f, color, i3), i3}), materialShapeDrawable4, materialShapeDrawable4);
                } else {
                    drawable = null;
                }
            }
            EditText editText3 = this.editText;
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            editText3.setBackground(drawable);
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
            this.collapsingTextHelper.setCollapsedAndExpandedTextColor(colorStateList2);
        }
        if (!isEnabled) {
            ColorStateList colorStateList3 = this.defaultHintTextColor;
            this.collapsingTextHelper.setCollapsedAndExpandedTextColor(ColorStateList.valueOf(colorStateList3 != null ? colorStateList3.getColorForState(new int[]{-16842910}, this.disabledColor) : this.disabledColor));
        } else if (shouldShowError()) {
            CollapsingTextHelper collapsingTextHelper = this.collapsingTextHelper;
            AppCompatTextView appCompatTextView2 = this.indicatorViewController.errorView;
            collapsingTextHelper.setCollapsedAndExpandedTextColor(appCompatTextView2 != null ? appCompatTextView2.getTextColors() : null);
        } else if (this.counterOverflowed && (appCompatTextView = this.counterView) != null) {
            this.collapsingTextHelper.setCollapsedAndExpandedTextColor(appCompatTextView.getTextColors());
        } else if (z4 && (colorStateList = this.focusedTextColor) != null) {
            CollapsingTextHelper collapsingTextHelper2 = this.collapsingTextHelper;
            if (collapsingTextHelper2.collapsedTextColor != colorStateList) {
                collapsingTextHelper2.collapsedTextColor = colorStateList;
                collapsingTextHelper2.recalculate(false);
            }
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
            if (cutoutEnabled() && !((CutoutDrawable) this.boxBackground).drawableState.cutoutBounds.isEmpty() && cutoutEnabled()) {
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
        updateCursorColor();
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

    public TextInputLayout(Context context, AttributeSet attributeSet, int i) {
        super(MaterialThemeOverlay.wrap(context, attributeSet, i, com.android.systemui.R.style.Widget_Design_TextInputLayout), attributeSet, i);
        int i2;
        ColorStateList colorStateList;
        ColorStateList colorStateList2;
        ColorStateList colorStateList3;
        ColorStateList colorStateList4;
        boolean z;
        ColorStateList colorStateList5;
        this.minEms = -1;
        this.maxEms = -1;
        this.minWidth = -1;
        this.maxWidth = -1;
        IndicatorViewController indicatorViewController = new IndicatorViewController(this);
        this.indicatorViewController = indicatorViewController;
        this.lengthCounter = new TextInputLayout$$ExternalSyntheticLambda1();
        this.tmpRect = new Rect();
        this.tmpBoundsRect = new Rect();
        this.tmpRectF = new RectF();
        this.editTextAttachedListeners = new LinkedHashSet();
        CollapsingTextHelper collapsingTextHelper = new CollapsingTextHelper(this);
        this.collapsingTextHelper = collapsingTextHelper;
        this.globalLayoutListenerAdded = false;
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
        int[] iArr = R$styleable.TextInputLayout;
        ThemeEnforcement.checkCompatibleTheme(context2, attributeSet, i, com.android.systemui.R.style.Widget_Design_TextInputLayout);
        ThemeEnforcement.checkTextAppearance(context2, attributeSet, iArr, i, com.android.systemui.R.style.Widget_Design_TextInputLayout, 22, 20, 40, 45, 49);
        TintTypedArray obtainStyledAttributes = TintTypedArray.obtainStyledAttributes(context2, attributeSet, iArr, i, com.android.systemui.R.style.Widget_Design_TextInputLayout);
        StartCompoundLayout startCompoundLayout = new StartCompoundLayout(this, obtainStyledAttributes);
        this.startLayout = startCompoundLayout;
        this.hintEnabled = obtainStyledAttributes.mWrapped.getBoolean(48, true);
        setHint(obtainStyledAttributes.mWrapped.getText(4));
        this.hintAnimationEnabled = obtainStyledAttributes.mWrapped.getBoolean(47, true);
        this.expandedHintEnabled = obtainStyledAttributes.mWrapped.getBoolean(42, true);
        if (obtainStyledAttributes.mWrapped.hasValue(6)) {
            int i3 = obtainStyledAttributes.mWrapped.getInt(6, -1);
            this.minEms = i3;
            EditText editText = this.editText;
            if (editText != null && i3 != -1) {
                editText.setMinEms(i3);
            }
        } else if (obtainStyledAttributes.mWrapped.hasValue(3)) {
            int dimensionPixelSize = obtainStyledAttributes.mWrapped.getDimensionPixelSize(3, -1);
            this.minWidth = dimensionPixelSize;
            EditText editText2 = this.editText;
            if (editText2 != null && dimensionPixelSize != -1) {
                editText2.setMinWidth(dimensionPixelSize);
            }
        }
        if (obtainStyledAttributes.mWrapped.hasValue(5)) {
            int i4 = obtainStyledAttributes.mWrapped.getInt(5, -1);
            this.maxEms = i4;
            EditText editText3 = this.editText;
            if (editText3 != null && i4 != -1) {
                editText3.setMaxEms(i4);
            }
        } else if (obtainStyledAttributes.mWrapped.hasValue(2)) {
            int dimensionPixelSize2 = obtainStyledAttributes.mWrapped.getDimensionPixelSize(2, -1);
            this.maxWidth = dimensionPixelSize2;
            EditText editText4 = this.editText;
            if (editText4 != null && dimensionPixelSize2 != -1) {
                editText4.setMaxWidth(dimensionPixelSize2);
            }
        }
        this.shapeAppearanceModel = ShapeAppearanceModel.builder(context2, attributeSet, i, com.android.systemui.R.style.Widget_Design_TextInputLayout).build();
        this.boxLabelCutoutPaddingPx = context2.getResources().getDimensionPixelOffset(com.android.systemui.R.dimen.mtrl_textinput_box_label_cutout_padding);
        this.boxCollapsedPaddingTopPx = obtainStyledAttributes.mWrapped.getDimensionPixelOffset(9, 0);
        int dimensionPixelSize3 = obtainStyledAttributes.mWrapped.getDimensionPixelSize(16, context2.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.mtrl_textinput_box_stroke_width_default));
        this.boxStrokeWidthDefaultPx = dimensionPixelSize3;
        this.boxStrokeWidthFocusedPx = obtainStyledAttributes.mWrapped.getDimensionPixelSize(17, context2.getResources().getDimensionPixelSize(com.android.systemui.R.dimen.mtrl_textinput_box_stroke_width_focused));
        this.boxStrokeWidthPx = dimensionPixelSize3;
        float dimension = obtainStyledAttributes.mWrapped.getDimension(13, -1.0f);
        float dimension2 = obtainStyledAttributes.mWrapped.getDimension(12, -1.0f);
        float dimension3 = obtainStyledAttributes.mWrapped.getDimension(10, -1.0f);
        float dimension4 = obtainStyledAttributes.mWrapped.getDimension(11, -1.0f);
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
        ColorStateList colorStateList6 = MaterialResources.getColorStateList(context2, obtainStyledAttributes, 7);
        if (colorStateList6 != null) {
            int defaultColor = colorStateList6.getDefaultColor();
            this.defaultFilledBackgroundColor = defaultColor;
            this.boxBackgroundColor = defaultColor;
            if (colorStateList6.isStateful()) {
                this.disabledFilledBackgroundColor = colorStateList6.getColorForState(new int[]{-16842910}, -1);
                this.focusedFilledBackgroundColor = colorStateList6.getColorForState(new int[]{R.attr.state_focused, R.attr.state_enabled}, -1);
                this.hoveredFilledBackgroundColor = colorStateList6.getColorForState(new int[]{R.attr.state_hovered, R.attr.state_enabled}, -1);
                i2 = -16842910;
            } else {
                this.focusedFilledBackgroundColor = defaultColor;
                i2 = -16842910;
                ColorStateList colorStateList7 = ResourcesCompat.getColorStateList(com.android.systemui.R.color.mtrl_filled_background_color, context2.getTheme(), context2.getResources());
                this.disabledFilledBackgroundColor = colorStateList7.getColorForState(new int[]{-16842910}, -1);
                this.hoveredFilledBackgroundColor = colorStateList7.getColorForState(new int[]{R.attr.state_hovered}, -1);
            }
        } else {
            i2 = -16842910;
            this.boxBackgroundColor = 0;
            this.defaultFilledBackgroundColor = 0;
            this.disabledFilledBackgroundColor = 0;
            this.focusedFilledBackgroundColor = 0;
            this.hoveredFilledBackgroundColor = 0;
        }
        if (obtainStyledAttributes.mWrapped.hasValue(1)) {
            ColorStateList colorStateList8 = obtainStyledAttributes.getColorStateList(1);
            this.focusedTextColor = colorStateList8;
            this.defaultHintTextColor = colorStateList8;
        }
        ColorStateList colorStateList9 = MaterialResources.getColorStateList(context2, obtainStyledAttributes, 14);
        this.focusedStrokeColor = obtainStyledAttributes.mWrapped.getColor(14, 0);
        this.defaultStrokeColor = context2.getColor(com.android.systemui.R.color.mtrl_textinput_default_box_stroke_color);
        this.disabledColor = context2.getColor(com.android.systemui.R.color.mtrl_textinput_disabled_color);
        this.hoveredStrokeColor = context2.getColor(com.android.systemui.R.color.mtrl_textinput_hovered_box_stroke_color);
        if (colorStateList9 != null) {
            if (colorStateList9.isStateful()) {
                this.defaultStrokeColor = colorStateList9.getDefaultColor();
                this.disabledColor = colorStateList9.getColorForState(new int[]{i2}, -1);
                this.hoveredStrokeColor = colorStateList9.getColorForState(new int[]{R.attr.state_hovered, R.attr.state_enabled}, -1);
                this.focusedStrokeColor = colorStateList9.getColorForState(new int[]{R.attr.state_focused, R.attr.state_enabled}, -1);
            } else if (this.focusedStrokeColor != colorStateList9.getDefaultColor()) {
                this.focusedStrokeColor = colorStateList9.getDefaultColor();
            }
            updateTextInputBoxState();
        }
        if (obtainStyledAttributes.mWrapped.hasValue(15) && this.strokeErrorColor != (colorStateList5 = MaterialResources.getColorStateList(context2, obtainStyledAttributes, 15))) {
            this.strokeErrorColor = colorStateList5;
            updateTextInputBoxState();
        }
        if (obtainStyledAttributes.mWrapped.getResourceId(49, -1) != -1) {
            collapsingTextHelper.setCollapsedTextAppearance(obtainStyledAttributes.mWrapped.getResourceId(49, 0));
            this.focusedTextColor = collapsingTextHelper.collapsedTextColor;
            if (this.editText != null) {
                updateLabelState(false, false);
                updateInputLayoutMargins();
            }
        }
        this.cursorColor = obtainStyledAttributes.getColorStateList(24);
        this.cursorErrorColor = obtainStyledAttributes.getColorStateList(25);
        int resourceId = obtainStyledAttributes.mWrapped.getResourceId(40, 0);
        CharSequence text = obtainStyledAttributes.mWrapped.getText(35);
        int i5 = obtainStyledAttributes.mWrapped.getInt(34, 1);
        boolean z2 = obtainStyledAttributes.mWrapped.getBoolean(36, false);
        int resourceId2 = obtainStyledAttributes.mWrapped.getResourceId(45, 0);
        boolean z3 = obtainStyledAttributes.mWrapped.getBoolean(44, false);
        CharSequence text2 = obtainStyledAttributes.mWrapped.getText(43);
        int resourceId3 = obtainStyledAttributes.mWrapped.getResourceId(57, 0);
        CharSequence text3 = obtainStyledAttributes.mWrapped.getText(56);
        boolean z4 = obtainStyledAttributes.mWrapped.getBoolean(18, false);
        int i6 = obtainStyledAttributes.mWrapped.getInt(19, -1);
        if (this.counterMaxLength != i6) {
            if (i6 > 0) {
                this.counterMaxLength = i6;
            } else {
                this.counterMaxLength = -1;
            }
            if (this.counterEnabled && this.counterView != null) {
                EditText editText5 = this.editText;
                updateCounter(editText5 == null ? null : editText5.getText());
            }
        }
        this.counterTextAppearance = obtainStyledAttributes.mWrapped.getResourceId(22, 0);
        this.counterOverflowTextAppearance = obtainStyledAttributes.mWrapped.getResourceId(20, 0);
        int i7 = obtainStyledAttributes.mWrapped.getInt(8, 0);
        if (i7 != this.boxBackgroundMode) {
            this.boxBackgroundMode = i7;
            if (this.editText != null) {
                onApplyBoxBackgroundMode();
            }
        }
        indicatorViewController.errorViewContentDescription = text;
        AppCompatTextView appCompatTextView = indicatorViewController.errorView;
        if (appCompatTextView != null) {
            appCompatTextView.setContentDescription(text);
        }
        indicatorViewController.errorViewAccessibilityLiveRegion = i5;
        AppCompatTextView appCompatTextView2 = indicatorViewController.errorView;
        if (appCompatTextView2 != null) {
            WeakHashMap weakHashMap = ViewCompat.sViewPropertyAnimatorMap;
            appCompatTextView2.setAccessibilityLiveRegion(i5);
        }
        indicatorViewController.helperTextTextAppearance = resourceId2;
        AppCompatTextView appCompatTextView3 = indicatorViewController.helperTextView;
        if (appCompatTextView3 != null) {
            appCompatTextView3.setTextAppearance(resourceId2);
        }
        indicatorViewController.errorTextAppearance = resourceId;
        AppCompatTextView appCompatTextView4 = indicatorViewController.errorView;
        if (appCompatTextView4 != null) {
            indicatorViewController.textInputView.setTextAppearanceCompatWithErrorFallback(appCompatTextView4, resourceId);
        }
        if (this.placeholderTextView == null) {
            AppCompatTextView appCompatTextView5 = new AppCompatTextView(getContext());
            this.placeholderTextView = appCompatTextView5;
            appCompatTextView5.setId(com.android.systemui.R.id.textinput_placeholder);
            AppCompatTextView appCompatTextView6 = this.placeholderTextView;
            WeakHashMap weakHashMap2 = ViewCompat.sViewPropertyAnimatorMap;
            appCompatTextView6.setImportantForAccessibility(2);
            Fade createPlaceholderFadeTransition = createPlaceholderFadeTransition();
            this.placeholderFadeIn = createPlaceholderFadeTransition;
            createPlaceholderFadeTransition.mStartDelay = 67L;
            this.placeholderFadeOut = createPlaceholderFadeTransition();
            int i8 = this.placeholderTextAppearance;
            this.placeholderTextAppearance = i8;
            AppCompatTextView appCompatTextView7 = this.placeholderTextView;
            if (appCompatTextView7 != null) {
                appCompatTextView7.setTextAppearance(i8);
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
        AppCompatTextView appCompatTextView8 = this.placeholderTextView;
        if (appCompatTextView8 != null) {
            appCompatTextView8.setTextAppearance(resourceId3);
        }
        if (obtainStyledAttributes.mWrapped.hasValue(41)) {
            ColorStateList colorStateList10 = obtainStyledAttributes.getColorStateList(41);
            indicatorViewController.errorViewTextColor = colorStateList10;
            AppCompatTextView appCompatTextView9 = indicatorViewController.errorView;
            if (appCompatTextView9 != null && colorStateList10 != null) {
                appCompatTextView9.setTextColor(colorStateList10);
            }
        }
        if (obtainStyledAttributes.mWrapped.hasValue(46)) {
            ColorStateList colorStateList11 = obtainStyledAttributes.getColorStateList(46);
            indicatorViewController.helperTextViewTextColor = colorStateList11;
            AppCompatTextView appCompatTextView10 = indicatorViewController.helperTextView;
            if (appCompatTextView10 != null && colorStateList11 != null) {
                appCompatTextView10.setTextColor(colorStateList11);
            }
        }
        if (obtainStyledAttributes.mWrapped.hasValue(50) && this.focusedTextColor != (colorStateList4 = obtainStyledAttributes.getColorStateList(50))) {
            if (this.defaultHintTextColor != null || collapsingTextHelper.collapsedTextColor == colorStateList4) {
                z = false;
            } else {
                collapsingTextHelper.collapsedTextColor = colorStateList4;
                z = false;
                collapsingTextHelper.recalculate(false);
            }
            this.focusedTextColor = colorStateList4;
            if (this.editText != null) {
                updateLabelState(z, z);
            }
        }
        if (obtainStyledAttributes.mWrapped.hasValue(23) && this.counterTextColor != (colorStateList3 = obtainStyledAttributes.getColorStateList(23))) {
            this.counterTextColor = colorStateList3;
            updateCounterTextAppearanceAndColor();
        }
        if (obtainStyledAttributes.mWrapped.hasValue(21) && this.counterOverflowTextColor != (colorStateList2 = obtainStyledAttributes.getColorStateList(21))) {
            this.counterOverflowTextColor = colorStateList2;
            updateCounterTextAppearanceAndColor();
        }
        if (obtainStyledAttributes.mWrapped.hasValue(58) && this.placeholderTextColor != (colorStateList = obtainStyledAttributes.getColorStateList(58))) {
            this.placeholderTextColor = colorStateList;
            AppCompatTextView appCompatTextView11 = this.placeholderTextView;
            if (appCompatTextView11 != null && colorStateList != null) {
                appCompatTextView11.setTextColor(colorStateList);
            }
        }
        EndCompoundLayout endCompoundLayout = new EndCompoundLayout(this, obtainStyledAttributes);
        this.endLayout = endCompoundLayout;
        boolean z5 = obtainStyledAttributes.mWrapped.getBoolean(0, true);
        obtainStyledAttributes.recycle();
        WeakHashMap weakHashMap3 = ViewCompat.sViewPropertyAnimatorMap;
        setImportantForAccessibility(2);
        ViewCompat.Api26Impl.setImportantForAutofill(this, 1);
        frameLayout.addView(startCompoundLayout);
        frameLayout.addView(endCompoundLayout);
        addView(frameLayout);
        setEnabled(z5);
        setHelperTextEnabled(z3);
        setErrorEnabled(z2);
        if (this.counterEnabled != z4) {
            if (z4) {
                AppCompatTextView appCompatTextView12 = new AppCompatTextView(getContext());
                this.counterView = appCompatTextView12;
                appCompatTextView12.setId(com.android.systemui.R.id.textinput_counter);
                this.counterView.setMaxLines(1);
                indicatorViewController.addIndicator(this.counterView, 2);
                ((ViewGroup.MarginLayoutParams) this.counterView.getLayoutParams()).setMarginStart(getResources().getDimensionPixelOffset(com.android.systemui.R.dimen.mtrl_textinput_counter_margin_start));
                updateCounterTextAppearanceAndColor();
                if (this.counterView != null) {
                    EditText editText7 = this.editText;
                    updateCounter(editText7 == null ? null : editText7.getText());
                }
            } else {
                indicatorViewController.removeIndicator(this.counterView, 2);
                this.counterView = null;
            }
            this.counterEnabled = z4;
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
        int i9 = indicatorViewController.captionDisplayed;
        if (i9 != 2) {
            indicatorViewController.captionToShow = 2;
        }
        indicatorViewController.updateCaptionViewsVisibility(i9, indicatorViewController.captionToShow, indicatorViewController.shouldAnimateCaptionView(indicatorViewController.helperTextView, text2));
    }
}
