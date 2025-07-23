package androidx.compose.material3;

import androidx.compose.foundation.shape.CornerBasedShape;
import androidx.compose.foundation.shape.CornerSize;
import androidx.compose.foundation.shape.RoundedCornerShapeKt;
import androidx.compose.material3.tokens.ShapeKeyTokens;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.ui.graphics.RectangleShapeKt;
import androidx.compose.ui.graphics.Shape;
import kotlin.NoWhenBranchMatchedException;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ShapesKt {
    public static final StaticProvidableCompositionLocal LocalShapes = new StaticProvidableCompositionLocal(new Function0() { // from class: androidx.compose.material3.ShapesKt$LocalShapes$1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return new Shapes(null, null, null, null, null, 31, null);
        }
    });

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[ShapeKeyTokens.values().length];
            try {
                iArr[ShapeKeyTokens.CornerExtraLarge.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[ShapeKeyTokens.CornerExtraLargeTop.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[ShapeKeyTokens.CornerExtraSmall.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[ShapeKeyTokens.CornerExtraSmallTop.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[ShapeKeyTokens.CornerFull.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[ShapeKeyTokens.CornerLarge.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[ShapeKeyTokens.CornerLargeEnd.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[ShapeKeyTokens.CornerLargeTop.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[ShapeKeyTokens.CornerMedium.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[ShapeKeyTokens.CornerNone.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr[ShapeKeyTokens.CornerSmall.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    public static final Shape fromToken(Shapes shapes, ShapeKeyTokens shapeKeyTokens) {
        switch (WhenMappings.$EnumSwitchMapping$0[shapeKeyTokens.ordinal()]) {
            case 1:
                return shapes.extraLarge;
            case 2:
                return top$default(shapes.extraLarge);
            case 3:
                return shapes.extraSmall;
            case 4:
                return top$default(shapes.extraSmall);
            case 5:
                return RoundedCornerShapeKt.CircleShape;
            case 6:
                return shapes.large;
            case 7:
                CornerBasedShape cornerBasedShape = shapes.large;
                ShapeDefaults.INSTANCE.getClass();
                CornerSize cornerSize = ShapeDefaults.CornerNone;
                return CornerBasedShape.copy$default(cornerBasedShape, cornerSize, null, cornerSize, 6);
            case 8:
                return top$default(shapes.large);
            case 9:
                return shapes.medium;
            case 10:
                return RectangleShapeKt.RectangleShape;
            case 11:
                return shapes.small;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    public static final Shape getValue(ShapeKeyTokens shapeKeyTokens, Composer composer) {
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.material3.<get-value> (Shapes.kt:398)");
        }
        MaterialTheme.INSTANCE.getClass();
        Shape fromToken = fromToken(MaterialTheme.getShapes(composer), shapeKeyTokens);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        return fromToken;
    }

    public static CornerBasedShape top$default(CornerBasedShape cornerBasedShape) {
        ShapeDefaults.INSTANCE.getClass();
        CornerSize cornerSize = ShapeDefaults.CornerNone;
        return CornerBasedShape.copy$default(cornerBasedShape, null, cornerSize, cornerSize, 3);
    }
}
