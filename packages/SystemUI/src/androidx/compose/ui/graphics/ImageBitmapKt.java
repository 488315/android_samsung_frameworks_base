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

/* loaded from: classes.dex */
public abstract class ImageBitmapKt {
    /* renamed from: ImageBitmap-x__-hDU$default, reason: not valid java name */
    public static AndroidImageBitmap m481ImageBitmapx__hDU$default(int i, int i2, int i3) {
        ColorSpace rgb;
        ColorSpace rgb2;
        ColorSpaces colorSpaces = ColorSpaces.INSTANCE;
        colorSpaces.getClass();
        Rgb rgb3 = ColorSpaces.Srgb;
        AndroidImageBitmap_androidKt.m433toBitmapConfig1JJdX4A(i3);
        int i4 = Api26Bitmap.$r8$clinit;
        Bitmap.Config configM433toBitmapConfig1JJdX4A = AndroidImageBitmap_androidKt.m433toBitmapConfig1JJdX4A(i3);
        int i5 = ColorSpaceVerificationHelper.$r8$clinit;
        colorSpaces.getClass();
        if (Intrinsics.areEqual(rgb3, rgb3)) {
            rgb = ColorSpace.get(ColorSpace.Named.SRGB);
        } else if (Intrinsics.areEqual(rgb3, ColorSpaces.Aces)) {
            rgb = ColorSpace.get(ColorSpace.Named.ACES);
        } else if (Intrinsics.areEqual(rgb3, ColorSpaces.Acescg)) {
            rgb = ColorSpace.get(ColorSpace.Named.ACESCG);
        } else if (Intrinsics.areEqual(rgb3, ColorSpaces.AdobeRgb)) {
            rgb = ColorSpace.get(ColorSpace.Named.ADOBE_RGB);
        } else if (Intrinsics.areEqual(rgb3, ColorSpaces.Bt2020)) {
            rgb = ColorSpace.get(ColorSpace.Named.BT2020);
        } else if (Intrinsics.areEqual(rgb3, ColorSpaces.Bt709)) {
            rgb = ColorSpace.get(ColorSpace.Named.BT709);
        } else if (Intrinsics.areEqual(rgb3, ColorSpaces.CieLab)) {
            rgb = ColorSpace.get(ColorSpace.Named.CIE_LAB);
        } else if (Intrinsics.areEqual(rgb3, ColorSpaces.CieXyz)) {
            rgb = ColorSpace.get(ColorSpace.Named.CIE_XYZ);
        } else if (Intrinsics.areEqual(rgb3, ColorSpaces.DciP3)) {
            rgb = ColorSpace.get(ColorSpace.Named.DCI_P3);
        } else if (Intrinsics.areEqual(rgb3, ColorSpaces.DisplayP3)) {
            rgb = ColorSpace.get(ColorSpace.Named.DISPLAY_P3);
        } else if (Intrinsics.areEqual(rgb3, ColorSpaces.ExtendedSrgb)) {
            rgb = ColorSpace.get(ColorSpace.Named.EXTENDED_SRGB);
        } else if (Intrinsics.areEqual(rgb3, ColorSpaces.LinearExtendedSrgb)) {
            rgb = ColorSpace.get(ColorSpace.Named.LINEAR_EXTENDED_SRGB);
        } else if (Intrinsics.areEqual(rgb3, ColorSpaces.LinearSrgb)) {
            rgb = ColorSpace.get(ColorSpace.Named.LINEAR_SRGB);
        } else if (Intrinsics.areEqual(rgb3, ColorSpaces.Ntsc1953)) {
            rgb = ColorSpace.get(ColorSpace.Named.NTSC_1953);
        } else if (Intrinsics.areEqual(rgb3, ColorSpaces.ProPhotoRgb)) {
            rgb = ColorSpace.get(ColorSpace.Named.PRO_PHOTO_RGB);
        } else {
            if (!Intrinsics.areEqual(rgb3, ColorSpaces.SmpteC)) {
                int i6 = ColorSpaceVerificationHelperV34.$r8$clinit;
                rgb = Intrinsics.areEqual(rgb3, ColorSpaces.Bt2020Hlg) ? ColorSpace.get(ColorSpace.Named.BT2020_HLG) : Intrinsics.areEqual(rgb3, ColorSpaces.Bt2020Pq) ? ColorSpace.get(ColorSpace.Named.BT2020_PQ) : null;
                if (rgb == null) {
                    if (rgb3 != null) {
                        float[] xyz$ui_graphics_release = rgb3.whitePoint.toXyz$ui_graphics_release();
                        TransferParameters transferParameters = rgb3.transferParameters;
                        ColorSpace.Rgb.TransferParameters transferParameters2 = transferParameters != null ? new ColorSpace.Rgb.TransferParameters(transferParameters.a, transferParameters.b, transferParameters.c, transferParameters.d, transferParameters.e, transferParameters.f, transferParameters.gamma) : null;
                        if (transferParameters2 != null) {
                            rgb = new ColorSpace.Rgb(rgb3.name, rgb3.primaries, xyz$ui_graphics_release, transferParameters2);
                        } else {
                            String str = rgb3.name;
                            final Function1 function1 = rgb3.oetf;
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
                                    return ((Number) function12.mo781invoke(Double.valueOf(d))).doubleValue();
                                }
                            };
                            final Function1 function12 = rgb3.eotf;
                            final int i8 = 1;
                            rgb2 = new ColorSpace.Rgb(str, rgb3.primaries, xyz$ui_graphics_release, doubleUnaryOperator, new DoubleUnaryOperator() { // from class: androidx.compose.ui.graphics.ColorSpaceVerificationHelper$$ExternalSyntheticLambda0
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
                                    return ((Number) function122.mo781invoke(Double.valueOf(d))).doubleValue();
                                }
                            }, rgb3.min, rgb3.max);
                        }
                    } else {
                        rgb = ColorSpace.get(ColorSpace.Named.SRGB);
                    }
                }
                return new AndroidImageBitmap(Bitmap.createBitmap((DisplayMetrics) null, i, i2, configM433toBitmapConfig1JJdX4A, true, rgb2));
            }
            rgb = ColorSpace.get(ColorSpace.Named.SMPTE_C);
        }
        rgb2 = rgb;
        return new AndroidImageBitmap(Bitmap.createBitmap((DisplayMetrics) null, i, i2, configM433toBitmapConfig1JJdX4A, true, rgb2));
    }
}
