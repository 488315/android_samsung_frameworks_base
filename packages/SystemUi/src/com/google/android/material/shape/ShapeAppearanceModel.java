package com.google.android.material.shape;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.ContextThemeWrapper;
import com.google.android.material.R$styleable;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class ShapeAppearanceModel {
    public static final RelativeCornerSize PILL = new RelativeCornerSize(0.5f);
    public final EdgeTreatment bottomEdge;
    public final CornerTreatment bottomLeftCorner;
    public final CornerSize bottomLeftCornerSize;
    public final CornerTreatment bottomRightCorner;
    public final CornerSize bottomRightCornerSize;
    public final EdgeTreatment leftEdge;
    public final EdgeTreatment rightEdge;
    public final EdgeTreatment topEdge;
    public final CornerTreatment topLeftCorner;
    public final CornerSize topLeftCornerSize;
    public final CornerTreatment topRightCorner;
    public final CornerSize topRightCornerSize;

    public static Builder builder(Context context, AttributeSet attributeSet, int i, int i2) {
        return builder(context, attributeSet, i, i2, new AbsoluteCornerSize(0));
    }

    public static CornerSize getCornerSize(TypedArray typedArray, int i, CornerSize cornerSize) {
        TypedValue peekValue = typedArray.peekValue(i);
        if (peekValue == null) {
            return cornerSize;
        }
        int i2 = peekValue.type;
        return i2 == 5 ? new AbsoluteCornerSize(TypedValue.complexToDimensionPixelSize(peekValue.data, typedArray.getResources().getDisplayMetrics())) : i2 == 6 ? new RelativeCornerSize(peekValue.getFraction(1.0f, 1.0f)) : cornerSize;
    }

    public final boolean isRoundRect(RectF rectF) {
        boolean z = this.leftEdge.getClass().equals(EdgeTreatment.class) && this.rightEdge.getClass().equals(EdgeTreatment.class) && this.topEdge.getClass().equals(EdgeTreatment.class) && this.bottomEdge.getClass().equals(EdgeTreatment.class);
        float cornerSize = this.topLeftCornerSize.getCornerSize(rectF);
        return z && ((this.topRightCornerSize.getCornerSize(rectF) > cornerSize ? 1 : (this.topRightCornerSize.getCornerSize(rectF) == cornerSize ? 0 : -1)) == 0 && (this.bottomLeftCornerSize.getCornerSize(rectF) > cornerSize ? 1 : (this.bottomLeftCornerSize.getCornerSize(rectF) == cornerSize ? 0 : -1)) == 0 && (this.bottomRightCornerSize.getCornerSize(rectF) > cornerSize ? 1 : (this.bottomRightCornerSize.getCornerSize(rectF) == cornerSize ? 0 : -1)) == 0) && ((this.topRightCorner instanceof RoundedCornerTreatment) && (this.topLeftCorner instanceof RoundedCornerTreatment) && (this.bottomRightCorner instanceof RoundedCornerTreatment) && (this.bottomLeftCorner instanceof RoundedCornerTreatment));
    }

    public final ShapeAppearanceModel withCornerSize(float f) {
        Builder builder = new Builder(this);
        builder.setAllCornerSizes(f);
        return builder.build();
    }

    private ShapeAppearanceModel(Builder builder) {
        this.topLeftCorner = builder.topLeftCorner;
        this.topRightCorner = builder.topRightCorner;
        this.bottomRightCorner = builder.bottomRightCorner;
        this.bottomLeftCorner = builder.bottomLeftCorner;
        this.topLeftCornerSize = builder.topLeftCornerSize;
        this.topRightCornerSize = builder.topRightCornerSize;
        this.bottomRightCornerSize = builder.bottomRightCornerSize;
        this.bottomLeftCornerSize = builder.bottomLeftCornerSize;
        this.topEdge = builder.topEdge;
        this.rightEdge = builder.rightEdge;
        this.bottomEdge = builder.bottomEdge;
        this.leftEdge = builder.leftEdge;
    }

    public static Builder builder(Context context, AttributeSet attributeSet, int i, int i2, CornerSize cornerSize) {
        TypedArray obtainStyledAttributes = context.obtainStyledAttributes(attributeSet, R$styleable.MaterialShape, i, i2);
        int resourceId = obtainStyledAttributes.getResourceId(0, 0);
        int resourceId2 = obtainStyledAttributes.getResourceId(1, 0);
        obtainStyledAttributes.recycle();
        return builder(context, resourceId, resourceId2, cornerSize);
    }

    public static Builder builder(Context context, int i, int i2, CornerSize cornerSize) {
        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(context, i);
        if (i2 != 0) {
            contextThemeWrapper = new ContextThemeWrapper(contextThemeWrapper, i2);
        }
        TypedArray obtainStyledAttributes = contextThemeWrapper.obtainStyledAttributes(R$styleable.ShapeAppearance);
        try {
            int i3 = obtainStyledAttributes.getInt(0, 0);
            int i4 = obtainStyledAttributes.getInt(3, i3);
            int i5 = obtainStyledAttributes.getInt(4, i3);
            int i6 = obtainStyledAttributes.getInt(2, i3);
            int i7 = obtainStyledAttributes.getInt(1, i3);
            CornerSize cornerSize2 = getCornerSize(obtainStyledAttributes, 5, cornerSize);
            CornerSize cornerSize3 = getCornerSize(obtainStyledAttributes, 8, cornerSize2);
            CornerSize cornerSize4 = getCornerSize(obtainStyledAttributes, 9, cornerSize2);
            CornerSize cornerSize5 = getCornerSize(obtainStyledAttributes, 7, cornerSize2);
            CornerSize cornerSize6 = getCornerSize(obtainStyledAttributes, 6, cornerSize2);
            Builder builder = new Builder();
            CornerTreatment createCornerTreatment = MaterialShapeUtils.createCornerTreatment(i4);
            builder.topLeftCorner = createCornerTreatment;
            float compatCornerTreatmentSize = Builder.compatCornerTreatmentSize(createCornerTreatment);
            if (compatCornerTreatmentSize != -1.0f) {
                builder.setTopLeftCornerSize(compatCornerTreatmentSize);
            }
            builder.topLeftCornerSize = cornerSize3;
            CornerTreatment createCornerTreatment2 = MaterialShapeUtils.createCornerTreatment(i5);
            builder.topRightCorner = createCornerTreatment2;
            float compatCornerTreatmentSize2 = Builder.compatCornerTreatmentSize(createCornerTreatment2);
            if (compatCornerTreatmentSize2 != -1.0f) {
                builder.setTopRightCornerSize(compatCornerTreatmentSize2);
            }
            builder.topRightCornerSize = cornerSize4;
            CornerTreatment createCornerTreatment3 = MaterialShapeUtils.createCornerTreatment(i6);
            builder.bottomRightCorner = createCornerTreatment3;
            float compatCornerTreatmentSize3 = Builder.compatCornerTreatmentSize(createCornerTreatment3);
            if (compatCornerTreatmentSize3 != -1.0f) {
                builder.setBottomRightCornerSize(compatCornerTreatmentSize3);
            }
            builder.bottomRightCornerSize = cornerSize5;
            CornerTreatment createCornerTreatment4 = MaterialShapeUtils.createCornerTreatment(i7);
            builder.bottomLeftCorner = createCornerTreatment4;
            float compatCornerTreatmentSize4 = Builder.compatCornerTreatmentSize(createCornerTreatment4);
            if (compatCornerTreatmentSize4 != -1.0f) {
                builder.setBottomLeftCornerSize(compatCornerTreatmentSize4);
            }
            builder.bottomLeftCornerSize = cornerSize6;
            return builder;
        } finally {
            obtainStyledAttributes.recycle();
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Builder {
        public EdgeTreatment bottomEdge;
        public CornerTreatment bottomLeftCorner;
        public CornerSize bottomLeftCornerSize;
        public CornerTreatment bottomRightCorner;
        public CornerSize bottomRightCornerSize;
        public final EdgeTreatment leftEdge;
        public final EdgeTreatment rightEdge;
        public EdgeTreatment topEdge;
        public CornerTreatment topLeftCorner;
        public CornerSize topLeftCornerSize;
        public CornerTreatment topRightCorner;
        public CornerSize topRightCornerSize;

        public Builder() {
            this.topLeftCorner = new RoundedCornerTreatment();
            this.topRightCorner = new RoundedCornerTreatment();
            this.bottomRightCorner = new RoundedCornerTreatment();
            this.bottomLeftCorner = new RoundedCornerTreatment();
            this.topLeftCornerSize = new AbsoluteCornerSize(0.0f);
            this.topRightCornerSize = new AbsoluteCornerSize(0.0f);
            this.bottomRightCornerSize = new AbsoluteCornerSize(0.0f);
            this.bottomLeftCornerSize = new AbsoluteCornerSize(0.0f);
            this.topEdge = new EdgeTreatment();
            this.rightEdge = new EdgeTreatment();
            this.bottomEdge = new EdgeTreatment();
            this.leftEdge = new EdgeTreatment();
        }

        public static float compatCornerTreatmentSize(CornerTreatment cornerTreatment) {
            if (cornerTreatment instanceof RoundedCornerTreatment) {
                return ((RoundedCornerTreatment) cornerTreatment).radius;
            }
            if (cornerTreatment instanceof CutCornerTreatment) {
                return ((CutCornerTreatment) cornerTreatment).size;
            }
            return -1.0f;
        }

        public final ShapeAppearanceModel build() {
            return new ShapeAppearanceModel(this);
        }

        public final void setAllCornerSizes(float f) {
            setTopLeftCornerSize(f);
            setTopRightCornerSize(f);
            setBottomRightCornerSize(f);
            setBottomLeftCornerSize(f);
        }

        public final void setBottomLeftCornerSize(float f) {
            this.bottomLeftCornerSize = new AbsoluteCornerSize(f);
        }

        public final void setBottomRightCornerSize(float f) {
            this.bottomRightCornerSize = new AbsoluteCornerSize(f);
        }

        public final void setTopLeftCornerSize(float f) {
            this.topLeftCornerSize = new AbsoluteCornerSize(f);
        }

        public final void setTopRightCornerSize(float f) {
            this.topRightCornerSize = new AbsoluteCornerSize(f);
        }

        public Builder(ShapeAppearanceModel shapeAppearanceModel) {
            this.topLeftCorner = new RoundedCornerTreatment();
            this.topRightCorner = new RoundedCornerTreatment();
            this.bottomRightCorner = new RoundedCornerTreatment();
            this.bottomLeftCorner = new RoundedCornerTreatment();
            this.topLeftCornerSize = new AbsoluteCornerSize(0.0f);
            this.topRightCornerSize = new AbsoluteCornerSize(0.0f);
            this.bottomRightCornerSize = new AbsoluteCornerSize(0.0f);
            this.bottomLeftCornerSize = new AbsoluteCornerSize(0.0f);
            this.topEdge = new EdgeTreatment();
            this.rightEdge = new EdgeTreatment();
            this.bottomEdge = new EdgeTreatment();
            this.leftEdge = new EdgeTreatment();
            this.topLeftCorner = shapeAppearanceModel.topLeftCorner;
            this.topRightCorner = shapeAppearanceModel.topRightCorner;
            this.bottomRightCorner = shapeAppearanceModel.bottomRightCorner;
            this.bottomLeftCorner = shapeAppearanceModel.bottomLeftCorner;
            this.topLeftCornerSize = shapeAppearanceModel.topLeftCornerSize;
            this.topRightCornerSize = shapeAppearanceModel.topRightCornerSize;
            this.bottomRightCornerSize = shapeAppearanceModel.bottomRightCornerSize;
            this.bottomLeftCornerSize = shapeAppearanceModel.bottomLeftCornerSize;
            this.topEdge = shapeAppearanceModel.topEdge;
            this.rightEdge = shapeAppearanceModel.rightEdge;
            this.bottomEdge = shapeAppearanceModel.bottomEdge;
            this.leftEdge = shapeAppearanceModel.leftEdge;
        }
    }

    public ShapeAppearanceModel() {
        this.topLeftCorner = new RoundedCornerTreatment();
        this.topRightCorner = new RoundedCornerTreatment();
        this.bottomRightCorner = new RoundedCornerTreatment();
        this.bottomLeftCorner = new RoundedCornerTreatment();
        this.topLeftCornerSize = new AbsoluteCornerSize(0.0f);
        this.topRightCornerSize = new AbsoluteCornerSize(0.0f);
        this.bottomRightCornerSize = new AbsoluteCornerSize(0.0f);
        this.bottomLeftCornerSize = new AbsoluteCornerSize(0.0f);
        this.topEdge = new EdgeTreatment();
        this.rightEdge = new EdgeTreatment();
        this.bottomEdge = new EdgeTreatment();
        this.leftEdge = new EdgeTreatment();
    }
}
