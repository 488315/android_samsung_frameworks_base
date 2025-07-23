package com.android.internal.widget;

import android.app.Flags;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.BlendMode;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.DrawableWrapper;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.Icon;
import android.graphics.drawable.RippleDrawable;
import android.text.SpannableStringBuilder;
import android.text.TextPaint;
import android.text.style.ImageSpan;
import android.text.style.MetricAffectingSpan;
import android.text.style.ReplacementSpan;
import android.util.AttributeSet;
import android.util.Log;
import android.view.RemotableViewMethod;
import android.widget.Button;
import android.widget.RemoteViews;
import com.android.internal.R;

@RemoteViews.RemoteView
/* loaded from: classes6.dex */
public class EmphasizedNotificationButton extends Button {
    private static final String FIRST_STRONG_ISOLATE = "\u2068";
    private static final String IMAGE_SPAN_TEXT = "�";
    private static final String LEFT_TO_RIGHT_ISOLATE = "\u2066";
    private static final String POP_DIRECTIONAL_ISOLATE = "\u2069";
    private static final String RIGHT_TO_LEFT_ISOLATE = "\u2067";
    private static final String SPACER_SPAN_TEXT = " ";
    private static final String TAG = "EmphasizedNotificationButton";
    private final GradientDrawable mBackground;
    private boolean mGluePending;
    private int mGluedLayoutDirection;
    private int mIconSize;
    private Drawable mIconToGlue;
    private int mInitialDrawablePadding;
    private CharSequence mLabelToGlue;
    private boolean mPriority;
    private final RippleDrawable mRipple;

    public EmphasizedNotificationButton(Context context) {
        this(context, null);
    }

    public EmphasizedNotificationButton(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public EmphasizedNotificationButton(Context context, AttributeSet attributeSet, int i) {
        this(context, attributeSet, i, 0);
    }

    public EmphasizedNotificationButton(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.mGluedLayoutDirection = -1;
        RippleDrawable rippleDrawable = (RippleDrawable) getBackground();
        this.mRipple = rippleDrawable;
        rippleDrawable.mutate();
        this.mBackground = (GradientDrawable) ((DrawableWrapper) rippleDrawable.getDrawable(0)).getDrawable();
        this.mIconSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.notification_actions_icon_drawable_size);
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, android.R.styleable.TextView, i, i2);
        try {
            this.mInitialDrawablePadding = obtainStyledAttributes.getDimensionPixelSize(52, 0);
            if (obtainStyledAttributes != null) {
                obtainStyledAttributes.close();
            }
            Log.v(TAG, "iconSize = " + this.mIconSize + "px, initialDrawablePadding = " + this.mInitialDrawablePadding + "px");
        } catch (Throwable th) {
            if (obtainStyledAttributes != null) {
                try {
                    obtainStyledAttributes.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @RemotableViewMethod
    public void setRippleColor(ColorStateList colorStateList) {
        this.mRipple.setColor(colorStateList);
        invalidate();
    }

    @RemotableViewMethod
    public void setButtonBackground(ColorStateList colorStateList) {
        this.mBackground.setColor(colorStateList);
        invalidate();
    }

    @RemotableViewMethod(asyncImpl = "setImageIconAsync")
    public void setImageIcon(Icon icon) {
        lambda$setImageIconAsync$0(icon == null ? null : icon.loadDrawable(this.mContext));
    }

    @RemotableViewMethod
    public Runnable setImageIconAsync(Icon icon) {
        final Drawable loadDrawable = icon == null ? null : icon.loadDrawable(this.mContext);
        return new Runnable() { // from class: com.android.internal.widget.EmphasizedNotificationButton$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                EmphasizedNotificationButton.this.lambda$setImageIconAsync$0(loadDrawable);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: setImageDrawable, reason: merged with bridge method [inline-methods] */
    public void lambda$setImageIconAsync$0(Drawable drawable) {
        if (drawable != null) {
            prepareIcon(drawable);
        }
        setCompoundDrawablesRelative(drawable, null, null, null);
    }

    @RemotableViewMethod(asyncImpl = "glueIconAsync")
    public void glueIcon(Icon icon) {
        lambda$glueIconAsync$1(icon == null ? null : icon.loadDrawable(this.mContext));
    }

    @RemotableViewMethod
    public Runnable glueIconAsync(Icon icon) {
        final Drawable loadDrawable = icon == null ? null : icon.loadDrawable(this.mContext);
        return new Runnable() { // from class: com.android.internal.widget.EmphasizedNotificationButton$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                EmphasizedNotificationButton.this.lambda$glueIconAsync$1(loadDrawable);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: setIconToGlue, reason: merged with bridge method [inline-methods] */
    public void lambda$glueIconAsync$1(Drawable drawable) {
        if (!Flags.evenlyDividedCallStyleActionLayout()) {
            Log.e(TAG, "glueIcon: new action layout disabled; doing nothing");
            return;
        }
        if (drawable != null) {
            prepareIcon(drawable);
        }
        this.mIconToGlue = drawable;
        this.mGluePending = true;
        glueIconAndLabelIfNeeded();
    }

    private void prepareIcon(Drawable drawable) {
        drawable.mutate();
        drawable.setTintList(getTextColors());
        drawable.setTintBlendMode(BlendMode.SRC_IN);
        int i = this.mIconSize;
        drawable.setBounds(0, 0, i, i);
    }

    @RemotableViewMethod(asyncImpl = "glueLabelAsync")
    public void glueLabel(CharSequence charSequence) {
        lambda$glueLabelAsync$2(charSequence);
    }

    @RemotableViewMethod
    public Runnable glueLabelAsync(final CharSequence charSequence) {
        return new Runnable() { // from class: com.android.internal.widget.EmphasizedNotificationButton$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                EmphasizedNotificationButton.this.lambda$glueLabelAsync$2(charSequence);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: setLabelToGlue, reason: merged with bridge method [inline-methods] */
    public void lambda$glueLabelAsync$2(CharSequence charSequence) {
        if (!Flags.evenlyDividedCallStyleActionLayout()) {
            Log.e(TAG, "glueLabel: new action layout disabled; doing nothing");
            return;
        }
        this.mLabelToGlue = charSequence;
        this.mGluePending = true;
        glueIconAndLabelIfNeeded();
    }

    @Override // android.widget.TextView, android.view.View
    public void onRtlPropertiesChanged(int i) {
        super.onRtlPropertiesChanged(i);
        Log.v(TAG, "onRtlPropertiesChanged: layoutDirection = " + i + ", gluedLayoutDirection = " + this.mGluedLayoutDirection);
        int i2 = this.mGluedLayoutDirection;
        if (i2 != -1 && i != i2) {
            Log.d(TAG, "onRtlPropertiesChanged: layout direction changed; regluing");
            this.mGluePending = true;
        }
        glueIconAndLabelIfNeeded();
    }

    private void glueIconAndLabelIfNeeded() {
        if (!this.mGluePending) {
            Log.v(TAG, "glueIconAndLabelIfNeeded: glue not pending; doing nothing");
            return;
        }
        if (!Flags.evenlyDividedCallStyleActionLayout()) {
            Log.e(TAG, "glueIconAndLabelIfNeeded: new action layout disabled; doing nothing");
            return;
        }
        if (!isLayoutDirectionResolved()) {
            Log.v(TAG, "glueIconAndLabelIfNeeded: layout direction not resolved; doing nothing");
            return;
        }
        int layoutDirection = getLayoutDirection();
        if (layoutDirection != 0 && layoutDirection != 1) {
            Log.e(TAG, "glueIconAndLabelIfNeeded: resolved layout direction neither LTR nor RTL; doing nothing");
            return;
        }
        glueIconAndLabel(layoutDirection);
        this.mGluePending = false;
        this.mGluedLayoutDirection = layoutDirection;
    }

    private void glueIconAndLabel(int i) {
        Drawable drawable = this.mIconToGlue;
        if (drawable == null && this.mLabelToGlue == null) {
            Log.d(TAG, "glueIconAndLabel: null icon and label, setting text to empty string");
            lambda$setTextAsync$0("");
            return;
        }
        if (drawable == null) {
            Log.d(TAG, "glueIconAndLabel: null icon, setting text to label");
            lambda$setTextAsync$0(this.mLabelToGlue);
            return;
        }
        if (this.mLabelToGlue == null) {
            Log.d(TAG, "glueIconAndLabel: null label, setting text to ImageSpan with icon");
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            appendSpan(spannableStringBuilder, IMAGE_SPAN_TEXT, new ImageSpan(this.mIconToGlue, 2));
            lambda$setTextAsync$0(spannableStringBuilder);
            return;
        }
        boolean z = i == 1;
        Log.d(TAG, "glueIconAndLabel: icon = " + this.mIconToGlue + ", iconSize = " + this.mIconSize + "px, initialDrawablePadding = " + this.mInitialDrawablePadding + "px, labelToGlue.length = " + this.mLabelToGlue.length() + ", rtlLayout = " + z);
        logIfTextDirectionNotFirstStrong();
        SpannableStringBuilder spannableStringBuilder2 = new SpannableStringBuilder();
        spannableStringBuilder2.append((CharSequence) (z ? RIGHT_TO_LEFT_ISOLATE : LEFT_TO_RIGHT_ISOLATE));
        appendSpan(spannableStringBuilder2, IMAGE_SPAN_TEXT, new ImageSpan(this.mIconToGlue, 2));
        appendSpan(spannableStringBuilder2, SPACER_SPAN_TEXT, new SpacerSpan(this.mInitialDrawablePadding));
        spannableStringBuilder2.append(FIRST_STRONG_ISOLATE);
        appendSpan(spannableStringBuilder2, this.mLabelToGlue, new CenterBesideImageSpan(this.mIconSize));
        spannableStringBuilder2.append(POP_DIRECTIONAL_ISOLATE);
        spannableStringBuilder2.append(POP_DIRECTIONAL_ISOLATE);
        lambda$setTextAsync$0(spannableStringBuilder2);
    }

    private void logIfTextDirectionNotFirstStrong() {
        if (!isTextDirectionResolved()) {
            Log.e(TAG, "glueIconAndLabel: text direction not resolved; letting View assume FIRST STRONG");
        }
        int textDirection = getTextDirection();
        if (textDirection != 1) {
            Log.w(TAG, "glueIconAndLabel: expected text direction TEXT_DIRECTION_FIRST_STRONG but found " + textDirection + "; will use a FIRST STRONG ISOLATE regardless");
        }
    }

    private void appendSpan(SpannableStringBuilder spannableStringBuilder, CharSequence charSequence, Object obj) {
        int length = spannableStringBuilder.length();
        spannableStringBuilder.append(charSequence);
        spannableStringBuilder.setSpan(obj, length, spannableStringBuilder.length(), 0);
    }

    @RemotableViewMethod
    public void setIsPriority(boolean z) {
        this.mPriority = z;
    }

    public boolean isPriority() {
        return this.mPriority;
    }

    private static class SpacerSpan extends ReplacementSpan {
        private static final String TAG = "SpacerSpan";
        private int mWidth;

        SpacerSpan(int i) {
            this.mWidth = i;
            Log.d(TAG, "width = " + this.mWidth + "px");
        }

        @Override // android.text.style.ReplacementSpan
        public int getSize(Paint paint, CharSequence charSequence, int i, int i2, Paint.FontMetricsInt fontMetricsInt) {
            Log.v(TAG, "getSize returning " + this.mWidth + "px");
            return this.mWidth;
        }

        @Override // android.text.style.ReplacementSpan
        public void draw(Canvas canvas, CharSequence charSequence, int i, int i2, float f, int i3, int i4, int i5, Paint paint) {
            Log.v(TAG, "drawing nothing");
        }
    }

    private static class CenterBesideImageSpan extends MetricAffectingSpan {
        private static final String TAG = "CenterBesideImageSpan";
        private int mBaselineShiftOffset;
        private int mImageHeight;
        private boolean mMeasured;

        CenterBesideImageSpan(int i) {
            this.mImageHeight = i;
            Log.d(TAG, "imageHeight = " + this.mImageHeight + "px");
        }

        @Override // android.text.style.MetricAffectingSpan
        public void updateMeasureState(TextPaint textPaint) {
            int i = (int) (-textPaint.ascent());
            int i2 = this.mImageHeight;
            if (i < i2) {
                this.mBaselineShiftOffset = (-(i2 - i)) / 2;
            } else {
                this.mBaselineShiftOffset = 0;
            }
            this.mMeasured = true;
            Log.d(TAG, "updateMeasureState: imageHeight = " + this.mImageHeight + "px, textHeight = " + i + "px, baselineShiftOffset = " + this.mBaselineShiftOffset + "px");
            textPaint.baselineShift = textPaint.baselineShift + this.mBaselineShiftOffset;
        }

        @Override // android.text.style.CharacterStyle
        public void updateDrawState(TextPaint textPaint) {
            if (textPaint == null) {
                Log.e(TAG, "updateDrawState: textPaint is null; doing nothing");
                return;
            }
            if (!this.mMeasured) {
                Log.e(TAG, "updateDrawState: called without measure; doing nothing");
                return;
            }
            Log.v(TAG, "updateDrawState: baselineShiftOffset = " + this.mBaselineShiftOffset + "px");
            textPaint.baselineShift = textPaint.baselineShift + this.mBaselineShiftOffset;
        }
    }
}
