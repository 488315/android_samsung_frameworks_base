package androidx.compose.ui.graphics;

import android.graphics.Bitmap;
import android.graphics.ColorSpace;
import android.util.DisplayMetrics;
import androidx.compose.ui.graphics.colorspace.ColorSpaces;
import androidx.compose.ui.graphics.colorspace.Rgb;
import androidx.compose.ui.graphics.colorspace.TransferParameters;
import java.util.function.DoubleUnaryOperator;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public abstract class ImageBitmapKt {
    /* renamed from: ImageBitmap-x__-hDU$default, reason: not valid java name */
    public static AndroidImageBitmap m479ImageBitmapx__hDU$default(int i, int i2, int i3) {
        ColorSpace colorSpace;
        ColorSpace rgb;
        ColorSpaces colorSpaces = ColorSpaces.INSTANCE;
        colorSpaces.getClass();
        Rgb rgb2 = ColorSpaces.Srgb;
        AndroidImageBitmap_androidKt.m431toBitmapConfig1JJdX4A(i3);
        int i4 = Api26Bitmap.$r8$clinit;
        Bitmap.Config m431toBitmapConfig1JJdX4A = AndroidImageBitmap_androidKt.m431toBitmapConfig1JJdX4A(i3);
        int i5 = ColorSpaceVerificationHelper.$r8$clinit;
        colorSpaces.getClass();
        if (Intrinsics.areEqual(rgb2, rgb2)) {
            colorSpace = ColorSpace.get(ColorSpace.Named.SRGB);
        } else if (Intrinsics.areEqual(rgb2, ColorSpaces.Aces)) {
            colorSpace = ColorSpace.get(ColorSpace.Named.ACES);
        } else if (Intrinsics.areEqual(rgb2, ColorSpaces.Acescg)) {
            colorSpace = ColorSpace.get(ColorSpace.Named.ACESCG);
        } else if (Intrinsics.areEqual(rgb2, ColorSpaces.AdobeRgb)) {
            colorSpace = ColorSpace.get(ColorSpace.Named.ADOBE_RGB);
        } else if (Intrinsics.areEqual(rgb2, ColorSpaces.Bt2020)) {
            colorSpace = ColorSpace.get(ColorSpace.Named.BT2020);
        } else if (Intrinsics.areEqual(rgb2, ColorSpaces.Bt709)) {
            colorSpace = ColorSpace.get(ColorSpace.Named.BT709);
        } else if (Intrinsics.areEqual(rgb2, ColorSpaces.CieLab)) {
            colorSpace = ColorSpace.get(ColorSpace.Named.CIE_LAB);
        } else if (Intrinsics.areEqual(rgb2, ColorSpaces.CieXyz)) {
            colorSpace = ColorSpace.get(ColorSpace.Named.CIE_XYZ);
        } else if (Intrinsics.areEqual(rgb2, ColorSpaces.DciP3)) {
            colorSpace = ColorSpace.get(ColorSpace.Named.DCI_P3);
        } else if (Intrinsics.areEqual(rgb2, ColorSpaces.DisplayP3)) {
            colorSpace = ColorSpace.get(ColorSpace.Named.DISPLAY_P3);
        } else if (Intrinsics.areEqual(rgb2, ColorSpaces.ExtendedSrgb)) {
            colorSpace = ColorSpace.get(ColorSpace.Named.EXTENDED_SRGB);
        } else if (Intrinsics.areEqual(rgb2, ColorSpaces.LinearExtendedSrgb)) {
            colorSpace = ColorSpace.get(ColorSpace.Named.LINEAR_EXTENDED_SRGB);
        } else if (Intrinsics.areEqual(rgb2, ColorSpaces.LinearSrgb)) {
            colorSpace = ColorSpace.get(ColorSpace.Named.LINEAR_SRGB);
        } else if (Intrinsics.areEqual(rgb2, ColorSpaces.Ntsc1953)) {
            colorSpace = ColorSpace.get(ColorSpace.Named.NTSC_1953);
        } else if (Intrinsics.areEqual(rgb2, ColorSpaces.ProPhotoRgb)) {
            colorSpace = ColorSpace.get(ColorSpace.Named.PRO_PHOTO_RGB);
        } else if (Intrinsics.areEqual(rgb2, ColorSpaces.SmpteC)) {
            colorSpace = ColorSpace.get(ColorSpace.Named.SMPTE_C);
        } else {
            int i6 = ColorSpaceVerificationHelperV34.$r8$clinit;
            colorSpace = Intrinsics.areEqual(rgb2, ColorSpaces.Bt2020Hlg) ? ColorSpace.get(ColorSpace.Named.BT2020_HLG) : Intrinsics.areEqual(rgb2, ColorSpaces.Bt2020Pq) ? ColorSpace.get(ColorSpace.Named.BT2020_PQ) : null;
            if (colorSpace == null) {
                if (rgb2 != null) {
                    float[] xyz$ui_graphics_release = rgb2.whitePoint.toXyz$ui_graphics_release();
                    TransferParameters transferParameters = rgb2.transferParameters;
                    ColorSpace.Rgb.TransferParameters transferParameters2 = transferParameters != null ? new ColorSpace.Rgb.TransferParameters(transferParameters.a, transferParameters.b, transferParameters.c, transferParameters.d, transferParameters.e, transferParameters.f, transferParameters.gamma) : null;
                    if (transferParameters2 == null) {
                        String str = rgb2.name;
                        final Function1 function1 = rgb2.oetf;
                        final int i7 = 0;
                        DoubleUnaryOperator doubleUnaryOperator = new DoubleUnaryOperator() { // from class: androidx.compose.ui.graphics.ColorSpaceVerificationHelper$$ExternalSyntheticLambda0
                            @Override // java.util.function.DoubleUnaryOperator
                            public final double applyAsDouble(double d) {
                                int i8 = i7;
                                Function1 function12 = function1;
                                switch (i8) {
                                    case 0:
                                        int i9 = ColorSpaceVerificationHelper.$r8$clinit;
                                        break;
                                    default:
                                        int i10 = ColorSpaceVerificationHelper.$r8$clinit;
                                        break;
                                }
                                return ((Number) function12.mo779invoke(Double.valueOf(d))).doubleValue();
                            }
                        };
                        final Function1 function12 = rgb2.eotf;
                        final int i8 = 1;
                        rgb = new ColorSpace.Rgb(str, rgb2.primaries, xyz$ui_graphics_release, doubleUnaryOperator, new DoubleUnaryOperator() { // from class: androidx.compose.ui.graphics.ColorSpaceVerificationHelper$$ExternalSyntheticLambda0
                            @Override // java.util.function.DoubleUnaryOperator
                            public final double applyAsDouble(double d) {
                                int i82 = i8;
                                Function1 function122 = function12;
                                switch (i82) {
                                    case 0:
                                        int i9 = ColorSpaceVerificationHelper.$r8$clinit;
                                        break;
                                    default:
                                        int i10 = ColorSpaceVerificationHelper.$r8$clinit;
                                        break;
                                }
                                return ((Number) function122.mo779invoke(Double.valueOf(d))).doubleValue();
                            }
                        }, rgb2.min, rgb2.max);
                        return new AndroidImageBitmap(Bitmap.createBitmap((DisplayMetrics) null, i, i2, m431toBitmapConfig1JJdX4A, true, rgb));
                    }
                    colorSpace = new ColorSpace.Rgb(rgb2.name, rgb2.primaries, xyz$ui_graphics_release, transferParameters2);
                } else {
                    colorSpace = ColorSpace.get(ColorSpace.Named.SRGB);
                }
            }
        }
        rgb = colorSpace;
        return new AndroidImageBitmap(Bitmap.createBitmap((DisplayMetrics) null, i, i2, m431toBitmapConfig1JJdX4A, true, rgb));
    }
}
