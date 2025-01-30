package com.google.android.material.tooltip;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.text.TextPaint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import androidx.core.content.ContextCompat;
import androidx.core.graphics.ColorUtils;
import com.android.systemui.R;
import com.google.android.material.R$styleable;
import com.google.android.material.internal.TextDrawableHelper;
import com.google.android.material.internal.ThemeEnforcement;
import com.google.android.material.resources.MaterialAttributes;
import com.google.android.material.resources.MaterialResources;
import com.google.android.material.resources.TextAppearance;
import com.google.android.material.shape.MarkerEdgeTreatment;
import com.google.android.material.shape.MaterialShapeDrawable;
import com.google.android.material.shape.OffsetEdgeTreatment;
import com.google.android.material.shape.ShapeAppearanceModel;
import com.samsung.android.knox.custom.IKnoxCustomManager;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class TooltipDrawable extends MaterialShapeDrawable implements TextDrawableHelper.TextDrawableDelegate {
    public static final /* synthetic */ int $r8$clinit = 0;
    public int arrowSize;
    public final ViewOnLayoutChangeListenerC44031 attachedViewLayoutChangeListener;
    public final Context context;
    public final Rect displayFrame;
    public final Paint.FontMetrics fontMetrics;
    public float labelOpacity;
    public int layoutMargin;
    public int locationOnScreenX;
    public int minHeight;
    public int minWidth;
    public int padding;
    public CharSequence text;
    public final TextDrawableHelper textDrawableHelper;
    public float tooltipPivotY;
    public float tooltipScaleX;
    public float tooltipScaleY;

    /* JADX WARN: Type inference failed for: r3v1, types: [com.google.android.material.tooltip.TooltipDrawable$1] */
    private TooltipDrawable(Context context, AttributeSet attributeSet, int i, int i2) {
        super(context, attributeSet, i, i2);
        this.fontMetrics = new Paint.FontMetrics();
        TextDrawableHelper textDrawableHelper = new TextDrawableHelper(this);
        this.textDrawableHelper = textDrawableHelper;
        this.attachedViewLayoutChangeListener = new View.OnLayoutChangeListener() { // from class: com.google.android.material.tooltip.TooltipDrawable.1
            @Override // android.view.View.OnLayoutChangeListener
            public final void onLayoutChange(View view, int i3, int i4, int i5, int i6, int i7, int i8, int i9, int i10) {
                TooltipDrawable tooltipDrawable = TooltipDrawable.this;
                int i11 = TooltipDrawable.$r8$clinit;
                tooltipDrawable.getClass();
                int[] iArr = new int[2];
                view.getLocationOnScreen(iArr);
                tooltipDrawable.locationOnScreenX = iArr[0];
                view.getWindowVisibleDisplayFrame(tooltipDrawable.displayFrame);
            }
        };
        this.displayFrame = new Rect();
        this.tooltipScaleX = 1.0f;
        this.tooltipScaleY = 1.0f;
        this.tooltipPivotY = 0.5f;
        this.labelOpacity = 1.0f;
        this.context = context;
        TextPaint textPaint = textDrawableHelper.textPaint;
        textPaint.density = context.getResources().getDisplayMetrics().density;
        textPaint.setTextAlign(Paint.Align.CENTER);
    }

    public static TooltipDrawable createFromAttributes(Context context, int i) {
        int i2;
        int i3;
        int i4;
        int resourceId;
        TextAppearance textAppearance = null;
        TooltipDrawable tooltipDrawable = new TooltipDrawable(context, null, 0, i);
        TypedArray obtainStyledAttributes = ThemeEnforcement.obtainStyledAttributes(tooltipDrawable.context, null, R$styleable.Tooltip, 0, i, new int[0]);
        tooltipDrawable.arrowSize = tooltipDrawable.context.getResources().getDimensionPixelSize(R.dimen.mtrl_tooltip_arrowSize);
        ShapeAppearanceModel shapeAppearanceModel = tooltipDrawable.drawableState.shapeAppearanceModel;
        shapeAppearanceModel.getClass();
        ShapeAppearanceModel.Builder builder = new ShapeAppearanceModel.Builder(shapeAppearanceModel);
        builder.bottomEdge = tooltipDrawable.createMarkerEdge();
        tooltipDrawable.setShapeAppearanceModel(builder.build());
        CharSequence text = obtainStyledAttributes.getText(6);
        if (!TextUtils.equals(tooltipDrawable.text, text)) {
            tooltipDrawable.text = text;
            tooltipDrawable.textDrawableHelper.textWidthDirty = true;
            tooltipDrawable.invalidateSelf();
        }
        Context context2 = tooltipDrawable.context;
        if (obtainStyledAttributes.hasValue(0) && (resourceId = obtainStyledAttributes.getResourceId(0, 0)) != 0) {
            textAppearance = new TextAppearance(context2, resourceId);
        }
        if (textAppearance != null && obtainStyledAttributes.hasValue(1)) {
            textAppearance.textColor = MaterialResources.getColorStateList(tooltipDrawable.context, obtainStyledAttributes, 1);
        }
        tooltipDrawable.textDrawableHelper.setTextAppearance(textAppearance, tooltipDrawable.context);
        Context context3 = tooltipDrawable.context;
        TypedValue resolveTypedValueOrThrow = MaterialAttributes.resolveTypedValueOrThrow(context3, TooltipDrawable.class.getCanonicalName(), R.attr.colorOnBackground);
        int i5 = resolveTypedValueOrThrow.resourceId;
        if (i5 != 0) {
            Object obj = ContextCompat.sLock;
            i2 = context3.getColor(i5);
        } else {
            i2 = resolveTypedValueOrThrow.data;
        }
        Context context4 = tooltipDrawable.context;
        TypedValue resolveTypedValueOrThrow2 = MaterialAttributes.resolveTypedValueOrThrow(context4, TooltipDrawable.class.getCanonicalName(), android.R.attr.colorBackground);
        int i6 = resolveTypedValueOrThrow2.resourceId;
        if (i6 != 0) {
            Object obj2 = ContextCompat.sLock;
            i3 = context4.getColor(i6);
        } else {
            i3 = resolveTypedValueOrThrow2.data;
        }
        tooltipDrawable.setFillColor(ColorStateList.valueOf(obtainStyledAttributes.getColor(7, ColorUtils.compositeColors(ColorUtils.setAlphaComponent(i2, 153), ColorUtils.setAlphaComponent(i3, IKnoxCustomManager.Stub.TRANSACTION_setBrightness)))));
        Context context5 = tooltipDrawable.context;
        TypedValue resolveTypedValueOrThrow3 = MaterialAttributes.resolveTypedValueOrThrow(context5, TooltipDrawable.class.getCanonicalName(), R.attr.colorSurface);
        int i7 = resolveTypedValueOrThrow3.resourceId;
        if (i7 != 0) {
            Object obj3 = ContextCompat.sLock;
            i4 = context5.getColor(i7);
        } else {
            i4 = resolveTypedValueOrThrow3.data;
        }
        tooltipDrawable.setStrokeColor(ColorStateList.valueOf(i4));
        tooltipDrawable.padding = obtainStyledAttributes.getDimensionPixelSize(2, 0);
        tooltipDrawable.minWidth = obtainStyledAttributes.getDimensionPixelSize(4, 0);
        tooltipDrawable.minHeight = obtainStyledAttributes.getDimensionPixelSize(5, 0);
        tooltipDrawable.layoutMargin = obtainStyledAttributes.getDimensionPixelSize(3, 0);
        obtainStyledAttributes.recycle();
        return tooltipDrawable;
    }

    public final float calculatePointerOffset() {
        int i;
        if (((this.displayFrame.right - getBounds().right) - this.locationOnScreenX) - this.layoutMargin < 0) {
            i = ((this.displayFrame.right - getBounds().right) - this.locationOnScreenX) - this.layoutMargin;
        } else {
            if (((this.displayFrame.left - getBounds().left) - this.locationOnScreenX) + this.layoutMargin <= 0) {
                return 0.0f;
            }
            i = ((this.displayFrame.left - getBounds().left) - this.locationOnScreenX) + this.layoutMargin;
        }
        return i;
    }

    public final OffsetEdgeTreatment createMarkerEdge() {
        float f = -calculatePointerOffset();
        float width = ((float) (getBounds().width() - (Math.sqrt(2.0d) * this.arrowSize))) / 2.0f;
        return new OffsetEdgeTreatment(new MarkerEdgeTreatment(this.arrowSize), Math.min(Math.max(f, -width), width));
    }

    @Override // com.google.android.material.shape.MaterialShapeDrawable, android.graphics.drawable.Drawable
    public final void draw(Canvas canvas) {
        canvas.save();
        float calculatePointerOffset = calculatePointerOffset();
        float f = (float) (-((Math.sqrt(2.0d) * this.arrowSize) - this.arrowSize));
        canvas.scale(this.tooltipScaleX, this.tooltipScaleY, (getBounds().width() * 0.5f) + getBounds().left, (getBounds().height() * this.tooltipPivotY) + getBounds().top);
        canvas.translate(calculatePointerOffset, f);
        super.draw(canvas);
        if (this.text != null) {
            float centerY = getBounds().centerY();
            this.textDrawableHelper.textPaint.getFontMetrics(this.fontMetrics);
            Paint.FontMetrics fontMetrics = this.fontMetrics;
            int i = (int) (centerY - ((fontMetrics.descent + fontMetrics.ascent) / 2.0f));
            TextDrawableHelper textDrawableHelper = this.textDrawableHelper;
            if (textDrawableHelper.textAppearance != null) {
                textDrawableHelper.textPaint.drawableState = getState();
                TextDrawableHelper textDrawableHelper2 = this.textDrawableHelper;
                textDrawableHelper2.textAppearance.updateDrawState(this.context, textDrawableHelper2.textPaint, textDrawableHelper2.fontCallback);
                this.textDrawableHelper.textPaint.setAlpha((int) (this.labelOpacity * 255.0f));
            }
            CharSequence charSequence = this.text;
            canvas.drawText(charSequence, 0, charSequence.length(), r0.centerX(), i, this.textDrawableHelper.textPaint);
        }
        canvas.restore();
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicHeight() {
        return (int) Math.max(this.textDrawableHelper.textPaint.getTextSize(), this.minHeight);
    }

    @Override // android.graphics.drawable.Drawable
    public final int getIntrinsicWidth() {
        float f = this.padding * 2;
        CharSequence charSequence = this.text;
        return (int) Math.max(f + (charSequence == null ? 0.0f : this.textDrawableHelper.getTextWidth(charSequence.toString())), this.minWidth);
    }

    @Override // com.google.android.material.shape.MaterialShapeDrawable, android.graphics.drawable.Drawable
    public final void onBoundsChange(Rect rect) {
        super.onBoundsChange(rect);
        ShapeAppearanceModel shapeAppearanceModel = this.drawableState.shapeAppearanceModel;
        shapeAppearanceModel.getClass();
        ShapeAppearanceModel.Builder builder = new ShapeAppearanceModel.Builder(shapeAppearanceModel);
        builder.bottomEdge = createMarkerEdge();
        setShapeAppearanceModel(builder.build());
    }

    @Override // com.google.android.material.shape.MaterialShapeDrawable, android.graphics.drawable.Drawable, com.google.android.material.internal.TextDrawableHelper.TextDrawableDelegate
    public final boolean onStateChange(int[] iArr) {
        return super.onStateChange(iArr);
    }
}
