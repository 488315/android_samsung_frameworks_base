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
/* loaded from: classes5.dex */
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

    public EmphasizedNotificationButton(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EmphasizedNotificationButton(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public EmphasizedNotificationButton(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mGluedLayoutDirection = -1;
        this.mRipple = (RippleDrawable) getBackground();
        this.mRipple.mutate();
        DrawableWrapper inset = (DrawableWrapper) this.mRipple.getDrawable(0);
        this.mBackground = (GradientDrawable) inset.getDrawable();
        this.mIconSize = this.mContext.getResources().getDimensionPixelSize(R.dimen.notification_actions_icon_drawable_size);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, android.R.styleable.TextView, defStyleAttr, defStyleRes);
        try {
            this.mInitialDrawablePadding = typedArray.getDimensionPixelSize(52, 0);
            if (typedArray != null) {
                typedArray.close();
            }
            Log.v(TAG, "iconSize = " + this.mIconSize + "px, initialDrawablePadding = " + this.mInitialDrawablePadding + "px");
        } catch (Throwable th) {
            if (typedArray != null) {
                try {
                    typedArray.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    @RemotableViewMethod
    public void setRippleColor(ColorStateList color) {
        this.mRipple.setColor(color);
        invalidate();
    }

    @RemotableViewMethod
    public void setButtonBackground(ColorStateList color) {
        this.mBackground.setColor(color);
        invalidate();
    }

    @RemotableViewMethod(asyncImpl = "setImageIconAsync")
    public void setImageIcon(Icon icon) {
        Drawable drawable = icon == null ? null : icon.loadDrawable(this.mContext);
        lambda$setImageIconAsync$0(drawable);
    }

    @RemotableViewMethod
    public Runnable setImageIconAsync(Icon icon) {
        final Drawable drawable = icon == null ? null : icon.loadDrawable(this.mContext);
        return new Runnable() { // from class: com.android.internal.widget.EmphasizedNotificationButton$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                EmphasizedNotificationButton.this.lambda$setImageIconAsync$0(drawable);
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
        Drawable drawable = icon == null ? null : icon.loadDrawable(this.mContext);
        lambda$glueIconAsync$1(drawable);
    }

    @RemotableViewMethod
    public Runnable glueIconAsync(Icon icon) {
        final Drawable drawable = icon == null ? null : icon.loadDrawable(this.mContext);
        return new Runnable() { // from class: com.android.internal.widget.EmphasizedNotificationButton$$ExternalSyntheticLambda2
            @Override // java.lang.Runnable
            public final void run() {
                EmphasizedNotificationButton.this.lambda$glueIconAsync$1(drawable);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: setIconToGlue, reason: merged with bridge method [inline-methods] */
    public void lambda$glueIconAsync$1(Drawable icon) {
        if (!Flags.evenlyDividedCallStyleActionLayout()) {
            Log.e(TAG, "glueIcon: new action layout disabled; doing nothing");
            return;
        }
        if (icon != null) {
            prepareIcon(icon);
        }
        this.mIconToGlue = icon;
        this.mGluePending = true;
        glueIconAndLabelIfNeeded();
    }

    private void prepareIcon(Drawable drawable) {
        drawable.mutate();
        drawable.setTintList(getTextColors());
        drawable.setTintBlendMode(BlendMode.SRC_IN);
        drawable.setBounds(0, 0, this.mIconSize, this.mIconSize);
    }

    @RemotableViewMethod(asyncImpl = "glueLabelAsync")
    public void glueLabel(CharSequence label) {
        lambda$glueLabelAsync$2(label);
    }

    @RemotableViewMethod
    public Runnable glueLabelAsync(final CharSequence label) {
        return new Runnable() { // from class: com.android.internal.widget.EmphasizedNotificationButton$$ExternalSyntheticLambda1
            @Override // java.lang.Runnable
            public final void run() {
                EmphasizedNotificationButton.this.lambda$glueLabelAsync$2(label);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* renamed from: setLabelToGlue, reason: merged with bridge method [inline-methods] */
    public void lambda$glueLabelAsync$2(CharSequence label) {
        if (!Flags.evenlyDividedCallStyleActionLayout()) {
            Log.e(TAG, "glueLabel: new action layout disabled; doing nothing");
            return;
        }
        this.mLabelToGlue = label;
        this.mGluePending = true;
        glueIconAndLabelIfNeeded();
    }

    @Override // android.widget.TextView, android.view.View
    public void onRtlPropertiesChanged(int layoutDirection) {
        super.onRtlPropertiesChanged(layoutDirection);
        Log.v(TAG, "onRtlPropertiesChanged: layoutDirection = " + layoutDirection + ", gluedLayoutDirection = " + this.mGluedLayoutDirection);
        boolean alreadyGlued = this.mGluedLayoutDirection != -1;
        if (alreadyGlued && layoutDirection != this.mGluedLayoutDirection) {
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

    private void glueIconAndLabel(int layoutDirection) {
        if (this.mIconToGlue == null && this.mLabelToGlue == null) {
            Log.d(TAG, "glueIconAndLabel: null icon and label, setting text to empty string");
            lambda$setTextAsync$0("");
            return;
        }
        if (this.mIconToGlue == null) {
            Log.d(TAG, "glueIconAndLabel: null icon, setting text to label");
            lambda$setTextAsync$0(this.mLabelToGlue);
            return;
        }
        if (this.mLabelToGlue == null) {
            Log.d(TAG, "glueIconAndLabel: null label, setting text to ImageSpan with icon");
            SpannableStringBuilder builder = new SpannableStringBuilder();
            appendSpan(builder, IMAGE_SPAN_TEXT, new ImageSpan(this.mIconToGlue, 2));
            lambda$setTextAsync$0(builder);
            return;
        }
        boolean rtlLayout = layoutDirection == 1;
        Log.d(TAG, "glueIconAndLabel: icon = " + this.mIconToGlue + ", iconSize = " + this.mIconSize + "px, initialDrawablePadding = " + this.mInitialDrawablePadding + "px, labelToGlue.length = " + this.mLabelToGlue.length() + ", rtlLayout = " + rtlLayout);
        logIfTextDirectionNotFirstStrong();
        SpannableStringBuilder builder2 = new SpannableStringBuilder();
        builder2.append((CharSequence) (rtlLayout ? RIGHT_TO_LEFT_ISOLATE : LEFT_TO_RIGHT_ISOLATE));
        appendSpan(builder2, IMAGE_SPAN_TEXT, new ImageSpan(this.mIconToGlue, 2));
        appendSpan(builder2, SPACER_SPAN_TEXT, new SpacerSpan(this.mInitialDrawablePadding));
        builder2.append(FIRST_STRONG_ISOLATE);
        appendSpan(builder2, this.mLabelToGlue, new CenterBesideImageSpan(this.mIconSize));
        builder2.append(POP_DIRECTIONAL_ISOLATE);
        builder2.append(POP_DIRECTIONAL_ISOLATE);
        lambda$setTextAsync$0(builder2);
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

    private void appendSpan(SpannableStringBuilder builder, CharSequence text, Object span) {
        int spanStart = builder.length();
        builder.append(text);
        int spanEnd = builder.length();
        builder.setSpan(span, spanStart, spanEnd, 0);
    }

    @RemotableViewMethod
    public void setIsPriority(boolean priority) {
        this.mPriority = priority;
    }

    public boolean isPriority() {
        return this.mPriority;
    }

    private static class SpacerSpan extends ReplacementSpan {
        private static final String TAG = "SpacerSpan";
        private int mWidth;

        SpacerSpan(int width) {
            this.mWidth = width;
            Log.d(TAG, "width = " + this.mWidth + "px");
        }

        @Override // android.text.style.ReplacementSpan
        public int getSize(Paint paint, CharSequence text, int start, int end, Paint.FontMetricsInt fontMetrics) {
            Log.v(TAG, "getSize returning " + this.mWidth + "px");
            return this.mWidth;
        }

        @Override // android.text.style.ReplacementSpan
        public void draw(Canvas canvas, CharSequence text, int start, int end, float x, int top, int y, int bottom, Paint paint) {
            Log.v(TAG, "drawing nothing");
        }
    }

    private static class CenterBesideImageSpan extends MetricAffectingSpan {
        private static final String TAG = "CenterBesideImageSpan";
        private int mBaselineShiftOffset;
        private int mImageHeight;
        private boolean mMeasured;

        CenterBesideImageSpan(int imageHeight) {
            this.mImageHeight = imageHeight;
            Log.d(TAG, "imageHeight = " + this.mImageHeight + "px");
        }

        @Override // android.text.style.MetricAffectingSpan
        public void updateMeasureState(TextPaint textPaint) {
            int textHeight = (int) (-textPaint.ascent());
            if (textHeight < this.mImageHeight) {
                this.mBaselineShiftOffset = (-(this.mImageHeight - textHeight)) / 2;
            } else {
                this.mBaselineShiftOffset = 0;
            }
            this.mMeasured = true;
            Log.d(TAG, "updateMeasureState: imageHeight = " + this.mImageHeight + "px, textHeight = " + textHeight + "px, baselineShiftOffset = " + this.mBaselineShiftOffset + "px");
            textPaint.baselineShift += this.mBaselineShiftOffset;
        }

        @Override // android.text.style.CharacterStyle
        public void updateDrawState(TextPaint textPaint) {
            if (textPaint == null) {
                Log.e(TAG, "updateDrawState: textPaint is null; doing nothing");
            } else if (!this.mMeasured) {
                Log.e(TAG, "updateDrawState: called without measure; doing nothing");
            } else {
                Log.v(TAG, "updateDrawState: baselineShiftOffset = " + this.mBaselineShiftOffset + "px");
                textPaint.baselineShift += this.mBaselineShiftOffset;
            }
        }
    }
}
