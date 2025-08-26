package android.graphics;

import android.graphics.ColorSpace;
import android.hardware.scontext.SContextConstants;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.util.SparseIntArray;
import com.android.graphics.flags.Flags;
import java.util.Arrays;
import java.util.HashMap;
import java.util.function.DoubleUnaryOperator;
import libcore.util.NativeAllocationRegistry;

/* loaded from: classes.dex */
public abstract class ColorSpace {
    private static final Rgb.TransferParameters BT2020_HLG_TRANSFER_PARAMETERS;
    private static final Rgb.TransferParameters BT2020_PQ_TRANSFER_PARAMETERS;
    private static final float[] BT2020_PRIMARIES;
    private static final float[] DCI_P3_PRIMARIES;
    public static final float[] ILLUMINANT_C;
    public static final float[] ILLUMINANT_D50;
    public static final float[] ILLUMINANT_D60;
    public static final float[] ILLUMINANT_D65;
    public static final int MAX_ID = 63;
    public static final int MIN_ID = -1;
    private static final float[] NTSC_1953_PRIMARIES;
    private static final Rgb.TransferParameters SMPTE_170M_TRANSFER_PARAMETERS;
    private static final float[] SRGB_PRIMARIES;
    private static final Rgb.TransferParameters SRGB_TRANSFER_PARAMETERS;
    private static final SparseIntArray sDataToColorSpaces;
    private static final HashMap<Integer, ColorSpace> sNamedColorSpaceMap;
    private final int mId;
    private final Model mModel;
    private final String mName;
    public static final float[] ILLUMINANT_A = {0.44757f, 0.40745f};
    public static final float[] ILLUMINANT_B = {0.34842f, 0.35161f};
    public static final float[] ILLUMINANT_D55 = {0.33242f, 0.34743f};
    public static final float[] ILLUMINANT_D75 = {0.29902f, 0.31485f};
    public static final float[] ILLUMINANT_E = {0.33333f, 0.33333f};
    private static final float[] GRAY_PRIMARIES = {1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f};
    private static final float[] ILLUMINANT_D50_XYZ = {0.964212f, 1.0f, 0.825188f};

    public enum Named {
        SRGB,
        LINEAR_SRGB,
        EXTENDED_SRGB,
        LINEAR_EXTENDED_SRGB,
        BT709,
        BT2020,
        DCI_P3,
        DISPLAY_P3,
        NTSC_1953,
        SMPTE_C,
        ADOBE_RGB,
        PRO_PHOTO_RGB,
        ACES,
        ACESCG,
        CIE_XYZ,
        CIE_LAB,
        BT2020_HLG,
        BT2020_PQ,
        OK_LAB,
        DISPLAY_BT2020
    }

    public enum RenderIntent {
        PERCEPTUAL,
        RELATIVE,
        SATURATION,
        ABSOLUTE
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static float clamp(float f, float f2, float f3) {
        return f < f2 ? f2 : f > f3 ? f3 : f;
    }

    public abstract float[] fromXyz(float[] fArr);

    public abstract float getMaxValue(int i);

    public abstract float getMinValue(int i);

    public boolean isSrgb() {
        return false;
    }

    public abstract boolean isWideGamut();

    public abstract float[] toXyz(float[] fArr);

    static {
        float[] fArr = {0.31006f, 0.31616f};
        ILLUMINANT_C = fArr;
        float[] fArr2 = {0.34567f, 0.3585f};
        ILLUMINANT_D50 = fArr2;
        float[] fArr3 = {0.32168f, 0.33767f};
        ILLUMINANT_D60 = fArr3;
        float[] fArr4 = {0.31271f, 0.32902f};
        ILLUMINANT_D65 = fArr4;
        float[] fArr5 = {0.64f, 0.33f, 0.3f, 0.6f, 0.15f, 0.06f};
        SRGB_PRIMARIES = fArr5;
        float[] fArr6 = {0.67f, 0.33f, 0.21f, 0.71f, 0.14f, 0.08f};
        NTSC_1953_PRIMARIES = fArr6;
        float[] fArr7 = {0.68f, 0.32f, 0.265f, 0.69f, 0.15f, 0.06f};
        DCI_P3_PRIMARIES = fArr7;
        float[] fArr8 = {0.708f, 0.292f, 0.17f, 0.797f, 0.131f, 0.046f};
        BT2020_PRIMARIES = fArr8;
        Rgb.TransferParameters transferParameters = new Rgb.TransferParameters(0.9478672985781991d, 0.05213270142180095d, 0.07739938080495357d, 0.04045d, 2.4d);
        SRGB_TRANSFER_PARAMETERS = transferParameters;
        Rgb.TransferParameters transferParameters2 = new Rgb.TransferParameters(0.9099181073703367d, 0.09008189262966333d, 0.2222222222222222d, 0.081d, 2.2222222222222223d);
        SMPTE_170M_TRANSFER_PARAMETERS = transferParameters2;
        Rgb.TransferParameters transferParameters3 = new Rgb.TransferParameters(2.0d, 2.0d, 5.591816309728916d, 0.28466892d, 0.55991073d, -0.685490157d, -3.0d);
        BT2020_HLG_TRANSFER_PARAMETERS = transferParameters3;
        Rgb.TransferParameters transferParameters4 = new Rgb.TransferParameters(-1.555223d, 1.860454d, 0.012683313515655966d, 18.8515625d, -18.6875d, 6.277394636015326d, -2.0d);
        BT2020_PQ_TRANSFER_PARAMETERS = transferParameters4;
        HashMap<Integer, ColorSpace> map = new HashMap<>();
        sNamedColorSpaceMap = map;
        SparseIntArray sparseIntArray = new SparseIntArray();
        sDataToColorSpaces = sparseIntArray;
        float[] fArr9 = null;
        map.put(Integer.valueOf(Named.SRGB.ordinal()), new Rgb("sRGB IEC61966-2.1", fArr5, fArr4, fArr9, transferParameters, Named.SRGB.ordinal()));
        sparseIntArray.put(142671872, Named.SRGB.ordinal());
        map.put(Integer.valueOf(Named.LINEAR_SRGB.ordinal()), new Rgb("sRGB IEC61966-2.1 (Linear)", fArr5, fArr4, 1.0d, 0.0f, 1.0f, Named.LINEAR_SRGB.ordinal()));
        sparseIntArray.put(138477568, Named.LINEAR_SRGB.ordinal());
        map.put(Integer.valueOf(Named.EXTENDED_SRGB.ordinal()), new Rgb("scRGB-nl IEC 61966-2-2:2003", fArr5, fArr4, null, new DoubleUnaryOperator() { // from class: android.graphics.ColorSpace$$ExternalSyntheticLambda0
            @Override // java.util.function.DoubleUnaryOperator
            public final double applyAsDouble(double d) {
                return ColorSpace.absRcpResponse(d, 0.9478672985781991d, 0.05213270142180095d, 0.07739938080495357d, 0.04045d, 2.4d);
            }
        }, new DoubleUnaryOperator() { // from class: android.graphics.ColorSpace$$ExternalSyntheticLambda1
            @Override // java.util.function.DoubleUnaryOperator
            public final double applyAsDouble(double d) {
                return ColorSpace.absResponse(d, 0.9478672985781991d, 0.05213270142180095d, 0.07739938080495357d, 0.04045d, 2.4d);
            }
        }, -0.799f, 2.399f, transferParameters, Named.EXTENDED_SRGB.ordinal()));
        sparseIntArray.put(411107328, Named.EXTENDED_SRGB.ordinal());
        map.put(Integer.valueOf(Named.LINEAR_EXTENDED_SRGB.ordinal()), new Rgb("scRGB IEC 61966-2-2:2003", fArr5, fArr4, 1.0d, -0.5f, 7.499f, Named.LINEAR_EXTENDED_SRGB.ordinal()));
        sparseIntArray.put(406913024, Named.LINEAR_EXTENDED_SRGB.ordinal());
        map.put(Integer.valueOf(Named.BT709.ordinal()), new Rgb("Rec. ITU-R BT.709-5", fArr5, fArr4, fArr9, transferParameters2, Named.BT709.ordinal()));
        sparseIntArray.put(281083904, Named.BT709.ordinal());
        map.put(Integer.valueOf(Named.BT2020.ordinal()), new Rgb("Rec. ITU-R BT.2020-1", fArr8, fArr4, fArr9, new Rgb.TransferParameters(0.9096697898662786d, 0.09033021013372146d, 0.2222222222222222d, 0.08145d, 2.2222222222222223d), Named.BT2020.ordinal()));
        sparseIntArray.put(147193856, Named.BT2020.ordinal());
        float f = 0.0f;
        float f2 = 1.0f;
        map.put(Integer.valueOf(Named.DCI_P3.ordinal()), new Rgb("SMPTE RP 431-2-2007 DCI (P3)", fArr7, new float[]{0.314f, 0.351f}, 2.6d, f, f2, Named.DCI_P3.ordinal()));
        sparseIntArray.put(155844608, Named.DCI_P3.ordinal());
        map.put(Integer.valueOf(Named.DISPLAY_P3.ordinal()), new Rgb("Display P3", fArr7, fArr4, fArr9, transferParameters, Named.DISPLAY_P3.ordinal()));
        sparseIntArray.put(143261696, Named.DISPLAY_P3.ordinal());
        map.put(Integer.valueOf(Named.NTSC_1953.ordinal()), new Rgb("NTSC (1953)", fArr6, fArr, (float[]) null, transferParameters2, Named.NTSC_1953.ordinal()));
        float[] fArr10 = null;
        map.put(Integer.valueOf(Named.SMPTE_C.ordinal()), new Rgb("SMPTE-C RGB", new float[]{0.63f, 0.34f, 0.31f, 0.595f, 0.155f, 0.07f}, fArr4, fArr10, transferParameters2, Named.SMPTE_C.ordinal()));
        map.put(Integer.valueOf(Named.ADOBE_RGB.ordinal()), new Rgb("Adobe RGB (1998)", new float[]{0.64f, 0.33f, 0.21f, 0.71f, 0.15f, 0.06f}, fArr4, 2.2d, f, f2, Named.ADOBE_RGB.ordinal()));
        sparseIntArray.put(151715840, Named.ADOBE_RGB.ordinal());
        map.put(Integer.valueOf(Named.PRO_PHOTO_RGB.ordinal()), new Rgb("ROMM RGB ISO 22028-2:2013", new float[]{0.7347f, 0.2653f, 0.1596f, 0.8404f, 0.0366f, 1.0E-4f}, fArr2, fArr10, new Rgb.TransferParameters(1.0d, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, 0.0625d, 0.031248d, 1.8d), Named.PRO_PHOTO_RGB.ordinal()));
        double d = 1.0d;
        float f3 = -65504.0f;
        float f4 = 65504.0f;
        map.put(Integer.valueOf(Named.ACES.ordinal()), new Rgb("SMPTE ST 2065-1:2012 ACES", new float[]{0.7347f, 0.2653f, 0.0f, 1.0f, 1.0E-4f, -0.077f}, fArr3, d, f3, f4, Named.ACES.ordinal()));
        map.put(Integer.valueOf(Named.ACESCG.ordinal()), new Rgb("Academy S-2014-004 ACEScg", new float[]{0.713f, 0.293f, 0.165f, 0.83f, 0.128f, 0.044f}, fArr3, d, f3, f4, Named.ACESCG.ordinal()));
        map.put(Integer.valueOf(Named.CIE_XYZ.ordinal()), new Xyz("Generic XYZ", Named.CIE_XYZ.ordinal()));
        map.put(Integer.valueOf(Named.CIE_LAB.ordinal()), new Lab("Generic L*a*b*", Named.CIE_LAB.ordinal()));
        float[] fArr11 = null;
        float f5 = 0.0f;
        float f6 = 1.0f;
        map.put(Integer.valueOf(Named.BT2020_HLG.ordinal()), new Rgb("Hybrid Log Gamma encoding", fArr8, fArr4, fArr11, new DoubleUnaryOperator() { // from class: android.graphics.ColorSpace$$ExternalSyntheticLambda2
            @Override // java.util.function.DoubleUnaryOperator
            public final double applyAsDouble(double d2) {
                return ColorSpace.transferHLGOETF(ColorSpace.BT2020_HLG_TRANSFER_PARAMETERS, d2);
            }
        }, new DoubleUnaryOperator() { // from class: android.graphics.ColorSpace$$ExternalSyntheticLambda3
            @Override // java.util.function.DoubleUnaryOperator
            public final double applyAsDouble(double d2) {
                return ColorSpace.transferHLGEOTF(ColorSpace.BT2020_HLG_TRANSFER_PARAMETERS, d2);
            }
        }, f5, f6, transferParameters3, Named.BT2020_HLG.ordinal()));
        sparseIntArray.put(168165376, Named.BT2020_HLG.ordinal());
        map.put(Integer.valueOf(Named.BT2020_PQ.ordinal()), new Rgb("Perceptual Quantizer encoding", fArr8, fArr4, fArr11, new DoubleUnaryOperator() { // from class: android.graphics.ColorSpace$$ExternalSyntheticLambda4
            @Override // java.util.function.DoubleUnaryOperator
            public final double applyAsDouble(double d2) {
                return ColorSpace.transferST2048OETF(ColorSpace.BT2020_PQ_TRANSFER_PARAMETERS, d2);
            }
        }, new DoubleUnaryOperator() { // from class: android.graphics.ColorSpace$$ExternalSyntheticLambda5
            @Override // java.util.function.DoubleUnaryOperator
            public final double applyAsDouble(double d2) {
                return ColorSpace.transferST2048EOTF(ColorSpace.BT2020_PQ_TRANSFER_PARAMETERS, d2);
            }
        }, f5, f6, transferParameters4, Named.BT2020_PQ.ordinal()));
        sparseIntArray.put(163971072, Named.BT2020_PQ.ordinal());
        if (Flags.okLabColorspace()) {
            map.put(Integer.valueOf(Named.OK_LAB.ordinal()), new OkLab("Oklab", Named.OK_LAB.ordinal()));
        }
        if (Flags.displayBt2020Colorspace()) {
            map.put(Integer.valueOf(Named.DISPLAY_BT2020.ordinal()), new Rgb("Display BT. 2020", fArr8, fArr4, (float[]) null, transferParameters, Named.DISPLAY_BT2020.ordinal()));
            sparseIntArray.put(142999552, Named.DISPLAY_BT2020.ordinal());
        }
    }

    public enum Adaptation {
        BRADFORD(new float[]{0.8951f, -0.7502f, 0.0389f, 0.2664f, 1.7135f, -0.0685f, -0.1614f, 0.0367f, 1.0296f}),
        VON_KRIES(new float[]{0.40024f, -0.2263f, 0.0f, 0.7076f, 1.16532f, 0.0f, -0.08081f, 0.0457f, 0.91822f}),
        CIECAT02(new float[]{0.7328f, -0.7036f, 0.003f, 0.4296f, 1.6975f, 0.0136f, -0.1624f, 0.0061f, 0.9834f});

        final float[] mTransform;

        Adaptation(float[] fArr) {
            this.mTransform = fArr;
        }
    }

    public enum Model {
        RGB(3),
        XYZ(3),
        LAB(3),
        CMYK(4);

        private final int mComponentCount;

        Model(int i) {
            this.mComponentCount = i;
        }

        public int getComponentCount() {
            return this.mComponentCount;
        }
    }

    ColorSpace(String str, Model model, int i) {
        if (str == null || str.length() < 1) {
            throw new IllegalArgumentException("The name of a color space cannot be null and must contain at least 1 character");
        }
        if (model == null) {
            throw new IllegalArgumentException("A color space must have a model");
        }
        if (i < -1 || i > 63) {
            throw new IllegalArgumentException("The id must be between -1 and 63");
        }
        this.mName = str;
        this.mModel = model;
        this.mId = i;
    }

    public String getName() {
        return this.mName;
    }

    public int getId() {
        return this.mId;
    }

    public Model getModel() {
        return this.mModel;
    }

    public int getComponentCount() {
        return this.mModel.getComponentCount();
    }

    public float[] toXyz(float f, float f2, float f3) {
        return toXyz(new float[]{f, f2, f3});
    }

    public float[] fromXyz(float f, float f2, float f3) {
        float[] fArr = new float[this.mModel.getComponentCount()];
        fArr[0] = f;
        fArr[1] = f2;
        fArr[2] = f3;
        return fromXyz(fArr);
    }

    public String toString() {
        return this.mName + " (id=" + this.mId + ", model=" + this.mModel + NavigationBarInflaterView.KEY_CODE_END;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            ColorSpace colorSpace = (ColorSpace) obj;
            if (this.mId == colorSpace.mId && this.mName.equals(colorSpace.mName) && this.mModel == colorSpace.mModel) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return (((this.mName.hashCode() * 31) + this.mModel.hashCode()) * 31) + this.mId;
    }

    public static Connector connect(ColorSpace colorSpace, ColorSpace colorSpace2) {
        return connect(colorSpace, colorSpace2, RenderIntent.PERCEPTUAL);
    }

    public static Connector connect(ColorSpace colorSpace, ColorSpace colorSpace2, RenderIntent renderIntent) {
        if (colorSpace.equals(colorSpace2)) {
            return Connector.identity(colorSpace);
        }
        if (colorSpace.getModel() == Model.RGB && colorSpace2.getModel() == Model.RGB) {
            return new Connector.Rgb((Rgb) colorSpace, (Rgb) colorSpace2, renderIntent);
        }
        return new Connector(colorSpace, colorSpace2, renderIntent);
    }

    public static Connector connect(ColorSpace colorSpace) {
        return connect(colorSpace, RenderIntent.PERCEPTUAL);
    }

    public static Connector connect(ColorSpace colorSpace, RenderIntent renderIntent) {
        if (colorSpace.isSrgb()) {
            return Connector.identity(colorSpace);
        }
        if (colorSpace.getModel() == Model.RGB) {
            return new Connector.Rgb((Rgb) colorSpace, (Rgb) get(Named.SRGB), renderIntent);
        }
        return new Connector(colorSpace, get(Named.SRGB), renderIntent);
    }

    public static ColorSpace adapt(ColorSpace colorSpace, float[] fArr) {
        return adapt(colorSpace, fArr, Adaptation.BRADFORD);
    }

    public static ColorSpace adapt(ColorSpace colorSpace, float[] fArr, Adaptation adaptation) {
        if (colorSpace.getModel() == Model.RGB) {
            Rgb rgb = (Rgb) colorSpace;
            if (!compare(rgb.mWhitePoint, fArr)) {
                return new Rgb(rgb, mul3x3(chromaticAdaptation(adaptation.mTransform, xyYToXyz(rgb.getWhitePoint()), fArr.length == 3 ? Arrays.copyOf(fArr, 3) : xyYToXyz(fArr)), rgb.mTransform), fArr);
            }
        }
        return colorSpace;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static float[] adaptToIlluminantD50(float[] fArr, float[] fArr2) {
        float[] fArr3 = ILLUMINANT_D50;
        if (compare(fArr, fArr3)) {
            return fArr2;
        }
        return mul3x3(chromaticAdaptation(Adaptation.BRADFORD.mTransform, xyYToXyz(fArr), xyYToXyz(fArr3)), fArr2);
    }

    static ColorSpace get(int i) {
        ColorSpace colorSpace = sNamedColorSpaceMap.get(Integer.valueOf(i));
        if (colorSpace != null) {
            return colorSpace;
        }
        throw new IllegalArgumentException("Invalid ID: " + i);
    }

    public static ColorSpace getFromDataSpace(int i) {
        int i2 = sDataToColorSpaces.get(i, -1);
        if (i2 != -1) {
            return get(i2);
        }
        return null;
    }

    public int getDataSpace() {
        SparseIntArray sparseIntArray = sDataToColorSpaces;
        int iIndexOfValue = sparseIntArray.indexOfValue(getId());
        if (iIndexOfValue != -1) {
            return sparseIntArray.keyAt(iIndexOfValue);
        }
        return 0;
    }

    public static ColorSpace get(Named named) {
        HashMap<Integer, ColorSpace> map = sNamedColorSpaceMap;
        ColorSpace colorSpace = map.get(Integer.valueOf(named.ordinal()));
        return colorSpace == null ? map.get(Integer.valueOf(Named.SRGB.ordinal())) : colorSpace;
    }

    public static ColorSpace match(float[] fArr, Rgb.TransferParameters transferParameters) {
        for (ColorSpace colorSpace : sNamedColorSpaceMap.values()) {
            if (colorSpace.getModel() == Model.RGB) {
                Rgb rgb = (Rgb) adapt(colorSpace, ILLUMINANT_D50_XYZ);
                if (compare(fArr, rgb.mTransform) && compare(transferParameters, rgb.mTransferParameters)) {
                    return colorSpace;
                }
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static double transferHLGOETF(Rgb.TransferParameters transferParameters, double d) {
        double d2 = d < SContextConstants.ENVIRONMENT_VALUE_UNKNOWN ? -1.0d : 1.0d;
        double d3 = 1.0d / transferParameters.a;
        double d4 = 1.0d / transferParameters.b;
        double d5 = 1.0d / transferParameters.c;
        double d6 = transferParameters.d;
        double d7 = transferParameters.e;
        double d8 = (d * d2) / (transferParameters.f + 1.0d);
        return d2 * (d8 <= 1.0d ? d3 * Math.pow(d8, d4) : (d5 * Math.log(d8 - d6)) + d7);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static double transferHLGEOTF(Rgb.TransferParameters transferParameters, double d) {
        double d2 = d < SContextConstants.ENVIRONMENT_VALUE_UNKNOWN ? -1.0d : 1.0d;
        double d3 = d * d2;
        double d4 = transferParameters.a;
        double d5 = transferParameters.b;
        double d6 = transferParameters.c;
        double d7 = d4 * d3;
        return (transferParameters.f + 1.0d) * d2 * (d7 <= 1.0d ? Math.pow(d7, d5) : Math.exp((d3 - transferParameters.e) * d6) + transferParameters.d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static double transferST2048OETF(Rgb.TransferParameters transferParameters, double d) {
        double d2 = d < SContextConstants.ENVIRONMENT_VALUE_UNKNOWN ? -1.0d : 1.0d;
        double d3 = d * d2;
        double d4 = -transferParameters.a;
        double d5 = transferParameters.d;
        double d6 = 1.0d / transferParameters.f;
        return d2 * Math.pow(Math.max(d4 + (d5 * Math.pow(d3, d6)), SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) / (transferParameters.b + (Math.pow(d3, d6) * (-transferParameters.e))), 1.0d / transferParameters.c);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static double transferST2048EOTF(Rgb.TransferParameters transferParameters, double d) {
        double d2 = d < SContextConstants.ENVIRONMENT_VALUE_UNKNOWN ? -1.0d : 1.0d;
        double d3 = d * d2;
        return d2 * Math.pow(Math.max(transferParameters.a + (transferParameters.b * Math.pow(d3, transferParameters.c)), SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) / (transferParameters.d + (transferParameters.e * Math.pow(d3, transferParameters.c))), transferParameters.f);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static double rcpResponse(double d, double d2, double d3, double d4, double d5, double d6) {
        return d >= d5 * d4 ? (Math.pow(d, 1.0d / d6) - d3) / d2 : d / d4;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static double response(double d, double d2, double d3, double d4, double d5, double d6) {
        return d >= d5 ? Math.pow((d2 * d) + d3, d6) : d4 * d;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static double rcpResponse(double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8) {
        return d >= d5 * d4 ? (Math.pow(d - d6, 1.0d / d8) - d3) / d2 : (d - d7) / d4;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static double response(double d, double d2, double d3, double d4, double d5, double d6, double d7, double d8) {
        return d >= d5 ? Math.pow((d2 * d) + d3, d8) + d6 : (d4 * d) + d7;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static double absRcpResponse(double d, double d2, double d3, double d4, double d5, double d6) {
        return Math.copySign(rcpResponse(d < SContextConstants.ENVIRONMENT_VALUE_UNKNOWN ? -d : d, d2, d3, d4, d5, d6), d);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static double absResponse(double d, double d2, double d3, double d4, double d5, double d6) {
        return Math.copySign(response(d < SContextConstants.ENVIRONMENT_VALUE_UNKNOWN ? -d : d, d2, d3, d4, d5, d6), d);
    }

    private static boolean compare(Rgb.TransferParameters transferParameters, Rgb.TransferParameters transferParameters2) {
        if (transferParameters == null && transferParameters2 == null) {
            return true;
        }
        return transferParameters != null && transferParameters2 != null && Math.abs(transferParameters.a - transferParameters2.a) < 0.001d && Math.abs(transferParameters.b - transferParameters2.b) < 0.001d && Math.abs(transferParameters.c - transferParameters2.c) < 0.001d && Math.abs(transferParameters.d - transferParameters2.d) < 0.002d && Math.abs(transferParameters.e - transferParameters2.e) < 0.001d && Math.abs(transferParameters.f - transferParameters2.f) < 0.001d && Math.abs(transferParameters.g - transferParameters2.g) < 0.001d;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean compare(float[] fArr, float[] fArr2) {
        if (fArr == fArr2) {
            return true;
        }
        for (int i = 0; i < fArr.length; i++) {
            if (Float.compare(fArr[i], fArr2[i]) != 0 && Math.abs(fArr[i] - fArr2[i]) > 0.001f) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static float[] inverse3x3(float[] fArr) {
        float f = fArr[0];
        float f2 = fArr[3];
        float f3 = fArr[6];
        float f4 = fArr[1];
        float f5 = fArr[4];
        float f6 = fArr[7];
        float f7 = fArr[2];
        float f8 = fArr[5];
        float f9 = fArr[8];
        float f10 = (f5 * f9) - (f6 * f8);
        float f11 = (f6 * f7) - (f4 * f9);
        float f12 = (f4 * f8) - (f5 * f7);
        float f13 = (f * f10) + (f2 * f11) + (f3 * f12);
        float[] fArr2 = new float[fArr.length];
        fArr2[0] = f10 / f13;
        fArr2[1] = f11 / f13;
        fArr2[2] = f12 / f13;
        fArr2[3] = ((f3 * f8) - (f2 * f9)) / f13;
        fArr2[4] = ((f9 * f) - (f3 * f7)) / f13;
        fArr2[5] = ((f7 * f2) - (f8 * f)) / f13;
        fArr2[6] = ((f2 * f6) - (f3 * f5)) / f13;
        fArr2[7] = ((f3 * f4) - (f6 * f)) / f13;
        fArr2[8] = ((f * f5) - (f2 * f4)) / f13;
        return fArr2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static float[] mul3x3(float[] fArr, float[] fArr2) {
        float f = fArr[0];
        float f2 = fArr2[0];
        float f3 = fArr[3];
        float f4 = fArr2[1];
        float f5 = fArr[6];
        float f6 = fArr2[2];
        float f7 = (f * f2) + (f3 * f4) + (f5 * f6);
        float f8 = fArr[1];
        float f9 = fArr[4];
        float f10 = fArr[7];
        float f11 = (f8 * f2) + (f9 * f4) + (f10 * f6);
        float f12 = fArr[2];
        float f13 = fArr[5];
        float f14 = fArr[8];
        float f15 = (f2 * f12) + (f4 * f13) + (f6 * f14);
        float f16 = fArr2[3];
        float f17 = fArr2[4];
        float f18 = fArr2[5];
        float f19 = (f * f16) + (f3 * f17) + (f5 * f18);
        float f20 = (f8 * f16) + (f9 * f17) + (f10 * f18);
        float f21 = (f16 * f12) + (f17 * f13) + (f18 * f14);
        float f22 = fArr2[6];
        float f23 = fArr2[7];
        float f24 = (f * f22) + (f3 * f23);
        float f25 = fArr2[8];
        return new float[]{f7, f11, f15, f19, f20, f21, f24 + (f5 * f25), (f8 * f22) + (f9 * f23) + (f10 * f25), (f12 * f22) + (f13 * f23) + (f14 * f25)};
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static float[] mul3x3Float3(float[] fArr, float[] fArr2) {
        float f = fArr2[0];
        float f2 = fArr2[1];
        float f3 = fArr2[2];
        fArr2[0] = (fArr[0] * f) + (fArr[3] * f2) + (fArr[6] * f3);
        fArr2[1] = (fArr[1] * f) + (fArr[4] * f2) + (fArr[7] * f3);
        fArr2[2] = (fArr[2] * f) + (fArr[5] * f2) + (fArr[8] * f3);
        return fArr2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static float[] mul3x3Diag(float[] fArr, float[] fArr2) {
        float f = fArr[0];
        float f2 = fArr2[0] * f;
        float f3 = fArr[1];
        float f4 = fArr2[1] * f3;
        float f5 = fArr[2];
        return new float[]{f2, f4, fArr2[2] * f5, fArr2[3] * f, fArr2[4] * f3, fArr2[5] * f5, f * fArr2[6], f3 * fArr2[7], f5 * fArr2[8]};
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static float[] xyYToXyz(float[] fArr) {
        float f = fArr[0];
        float f2 = fArr[1];
        return new float[]{f / f2, 1.0f, ((1.0f - f) - f2) / f2};
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static float[] chromaticAdaptation(float[] fArr, float[] fArr2, float[] fArr3) {
        float[] fArrMul3x3Float3 = mul3x3Float3(fArr, fArr2);
        float[] fArrMul3x3Float32 = mul3x3Float3(fArr, fArr3);
        return mul3x3(inverse3x3(fArr), mul3x3Diag(new float[]{fArrMul3x3Float32[0] / fArrMul3x3Float3[0], fArrMul3x3Float32[1] / fArrMul3x3Float3[1], fArrMul3x3Float32[2] / fArrMul3x3Float3[2]}, fArr));
    }

    public static float[] cctToXyz(int i) {
        float f;
        float f2;
        float f3;
        float f4;
        float f5;
        if (i < 1) {
            throw new IllegalArgumentException("Temperature must be greater than 0");
        }
        float f6 = i;
        float f7 = 1000.0f / f6;
        float f8 = f7 * f7;
        if (f6 <= 4000.0f) {
            f = ((0.8776956f * f7) + 0.17991f) - (0.2343589f * f8);
            f2 = 0.2661239f;
        } else {
            f = (0.2226347f * f7) + 0.24039f + (2.1070378f * f8);
            f2 = 3.025847f;
        }
        float f9 = f - ((f8 * f2) * f7);
        float f10 = f9 * f9;
        if (f6 <= 2222.0f) {
            f4 = ((2.1855583f * f9) - 0.20219684f) - (1.3481102f * f10);
            f5 = 1.1063814f;
        } else {
            if (f6 > 4000.0f) {
                f3 = (((3.7511299f * f9) - 0.37001482f) - (5.873387f * f10)) + (f10 * 3.081758f * f9);
                return xyYToXyz(new float[]{f9, f3});
            }
            f4 = ((2.09137f * f9) - 0.16748866f) - (1.3741859f * f10);
            f5 = 0.9549476f;
        }
        f3 = f4 - ((f10 * f5) * f9);
        return xyYToXyz(new float[]{f9, f3});
    }

    public static float[] chromaticAdaptation(Adaptation adaptation, float[] fArr, float[] fArr2) {
        if ((fArr.length != 2 && fArr.length != 3) || (fArr2.length != 2 && fArr2.length != 3)) {
            throw new IllegalArgumentException("A white point array must have 2 or 3 floats");
        }
        float[] fArrCopyOf = fArr.length == 3 ? Arrays.copyOf(fArr, 3) : xyYToXyz(fArr);
        float[] fArrCopyOf2 = fArr2.length == 3 ? Arrays.copyOf(fArr2, 3) : xyYToXyz(fArr2);
        if (compare(fArrCopyOf, fArrCopyOf2)) {
            return new float[]{1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f};
        }
        return chromaticAdaptation(adaptation.mTransform, fArrCopyOf, fArrCopyOf2);
    }

    private static final class Xyz extends ColorSpace {
        private static float clamp(float f) {
            if (f < -2.0f) {
                return -2.0f;
            }
            if (f > 2.0f) {
                return 2.0f;
            }
            return f;
        }

        @Override // android.graphics.ColorSpace
        public float getMaxValue(int i) {
            return 2.0f;
        }

        @Override // android.graphics.ColorSpace
        public float getMinValue(int i) {
            return -2.0f;
        }

        @Override // android.graphics.ColorSpace
        public boolean isWideGamut() {
            return true;
        }

        private Xyz(String str, int i) {
            super(str, Model.XYZ, i);
        }

        @Override // android.graphics.ColorSpace
        public float[] toXyz(float[] fArr) {
            fArr[0] = clamp(fArr[0]);
            fArr[1] = clamp(fArr[1]);
            fArr[2] = clamp(fArr[2]);
            return fArr;
        }

        @Override // android.graphics.ColorSpace
        public float[] fromXyz(float[] fArr) {
            fArr[0] = clamp(fArr[0]);
            fArr[1] = clamp(fArr[1]);
            fArr[2] = clamp(fArr[2]);
            return fArr;
        }
    }

    private static final class Lab extends ColorSpace {
        private static final float A = 0.008856452f;
        private static final float B = 7.787037f;
        private static final float C = 0.13793103f;
        private static final float D = 0.20689656f;

        @Override // android.graphics.ColorSpace
        public float getMaxValue(int i) {
            return i == 0 ? 100.0f : 128.0f;
        }

        @Override // android.graphics.ColorSpace
        public float getMinValue(int i) {
            return i == 0 ? 0.0f : -128.0f;
        }

        @Override // android.graphics.ColorSpace
        public boolean isWideGamut() {
            return true;
        }

        private Lab(String str, int i) {
            super(str, Model.LAB, i);
        }

        @Override // android.graphics.ColorSpace
        public float[] toXyz(float[] fArr) {
            fArr[0] = ColorSpace.clamp(fArr[0], 0.0f, 100.0f);
            fArr[1] = ColorSpace.clamp(fArr[1], -128.0f, 128.0f);
            float fClamp = ColorSpace.clamp(fArr[2], -128.0f, 128.0f);
            fArr[2] = fClamp;
            float f = (fArr[0] + 16.0f) / 116.0f;
            float f2 = (fArr[1] * 0.002f) + f;
            float f3 = f - (fClamp * 0.005f);
            float f4 = f2 > D ? f2 * f2 * f2 : (f2 - C) * 0.12841855f;
            float f5 = f > D ? f * f * f : (f - C) * 0.12841855f;
            float f6 = f3 > D ? f3 * f3 * f3 : (f3 - C) * 0.12841855f;
            fArr[0] = f4 * ColorSpace.ILLUMINANT_D50_XYZ[0];
            fArr[1] = f5 * ColorSpace.ILLUMINANT_D50_XYZ[1];
            fArr[2] = f6 * ColorSpace.ILLUMINANT_D50_XYZ[2];
            return fArr;
        }

        @Override // android.graphics.ColorSpace
        public float[] fromXyz(float[] fArr) {
            float f = fArr[0] / ColorSpace.ILLUMINANT_D50_XYZ[0];
            float f2 = fArr[1] / ColorSpace.ILLUMINANT_D50_XYZ[1];
            float f3 = fArr[2] / ColorSpace.ILLUMINANT_D50_XYZ[2];
            float fPow = f > A ? (float) Math.pow(f, 0.3333333333333333d) : (f * B) + C;
            float fPow2 = f2 > A ? (float) Math.pow(f2, 0.3333333333333333d) : (f2 * B) + C;
            float f4 = (116.0f * fPow2) - 16.0f;
            float f5 = (fPow - fPow2) * 500.0f;
            float fPow3 = (fPow2 - (f3 > A ? (float) Math.pow(f3, 0.3333333333333333d) : (f3 * B) + C)) * 200.0f;
            fArr[0] = ColorSpace.clamp(f4, 0.0f, 100.0f);
            fArr[1] = ColorSpace.clamp(f5, -128.0f, 128.0f);
            fArr[2] = ColorSpace.clamp(fPow3, -128.0f, 128.0f);
            return fArr;
        }
    }

    private static final class OkLab extends ColorSpace {
        private static final float[] INVERSE_M1;
        private static final float[] INVERSE_M2;
        private static final float[] M1;
        private static final float[] M1TMP;
        private static final float[] M2;

        @Override // android.graphics.ColorSpace
        public float getMaxValue(int i) {
            return i == 0 ? 1.0f : 0.5f;
        }

        @Override // android.graphics.ColorSpace
        public float getMinValue(int i) {
            return i == 0 ? 0.0f : -0.5f;
        }

        @Override // android.graphics.ColorSpace
        public boolean isWideGamut() {
            return true;
        }

        private OkLab(String str, int i) {
            super(str, Model.LAB, i);
        }

        @Override // android.graphics.ColorSpace
        public float[] toXyz(float[] fArr) {
            fArr[0] = ColorSpace.clamp(fArr[0], 0.0f, 1.0f);
            fArr[1] = ColorSpace.clamp(fArr[1], -0.5f, 0.5f);
            fArr[2] = ColorSpace.clamp(fArr[2], -0.5f, 0.5f);
            ColorSpace.mul3x3Float3(INVERSE_M2, fArr);
            float f = fArr[0];
            fArr[0] = f * f * f;
            float f2 = fArr[1];
            fArr[1] = f2 * f2 * f2;
            float f3 = fArr[2];
            fArr[2] = f3 * f3 * f3;
            ColorSpace.mul3x3Float3(INVERSE_M1, fArr);
            return fArr;
        }

        @Override // android.graphics.ColorSpace
        public float[] fromXyz(float[] fArr) {
            ColorSpace.mul3x3Float3(M1, fArr);
            fArr[0] = (float) Math.cbrt(fArr[0]);
            fArr[1] = (float) Math.cbrt(fArr[1]);
            fArr[2] = (float) Math.cbrt(fArr[2]);
            ColorSpace.mul3x3Float3(M2, fArr);
            return fArr;
        }

        static {
            float[] fArr = {0.818933f, 0.032984544f, 0.0482003f, 0.36186674f, 0.9293119f, 0.26436627f, -0.12885971f, 0.03614564f, 0.6338517f};
            M1TMP = fArr;
            float[] fArrMul3x3 = ColorSpace.mul3x3(fArr, chromaticAdaptation(Adaptation.BRADFORD, ILLUMINANT_D50, ILLUMINANT_D65));
            M1 = fArrMul3x3;
            float[] fArr2 = {0.21045426f, 1.9779985f, 0.025904037f, 0.7936178f, -2.4285922f, 0.78277177f, -0.004072047f, 0.4505937f, -0.80867577f};
            M2 = fArr2;
            INVERSE_M1 = ColorSpace.inverse3x3(fArrMul3x3);
            INVERSE_M2 = ColorSpace.inverse3x3(fArr2);
        }
    }

    long getNativeInstance() {
        throw new IllegalArgumentException("colorSpace must be an RGB color space");
    }

    public static class Rgb extends ColorSpace {
        private final DoubleUnaryOperator mClampedEotf;
        private final DoubleUnaryOperator mClampedOetf;
        private final DoubleUnaryOperator mEotf;
        private final float[] mInverseTransform;
        private final boolean mIsSrgb;
        private final boolean mIsWideGamut;
        private final float mMax;
        private final float mMin;
        private final long mNativePtr;
        private final DoubleUnaryOperator mOetf;
        private final float[] mPrimaries;
        private final TransferParameters mTransferParameters;
        private final float[] mTransform;
        private final float[] mWhitePoint;

        private static float cross(float f, float f2, float f3, float f4) {
            return (f * f4) - (f2 * f3);
        }

        public static class TransferParameters {
            private static final double TYPE_HLGish = -3.0d;
            private static final double TYPE_PQish = -2.0d;
            public final double a;
            public final double b;
            public final double c;
            public final double d;
            public final double e;
            public final double f;
            public final double g;

            private static boolean isSpecialG(double d) {
                return d == TYPE_PQish || d == TYPE_HLGish;
            }

            public TransferParameters(double d, double d2, double d3, double d4, double d5) {
                this(d, d2, d3, d4, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, d5);
            }

            public TransferParameters(double d, double d2, double d3, double d4, double d5, double d6, double d7) {
                if (Double.isNaN(d) || Double.isNaN(d2) || Double.isNaN(d3) || Double.isNaN(d4) || Double.isNaN(d5) || Double.isNaN(d6) || Double.isNaN(d7)) {
                    throw new IllegalArgumentException("Parameters cannot be NaN");
                }
                if (!isSpecialG(d7)) {
                    if (d4 < SContextConstants.ENVIRONMENT_VALUE_UNKNOWN || d4 > Math.ulp(1.0f) + 1.0f) {
                        throw new IllegalArgumentException("Parameter d must be in the range [0..1], was " + d4);
                    }
                    if (d4 == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN && (d == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN || d7 == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN)) {
                        throw new IllegalArgumentException("Parameter a or g is zero, the transfer function is constant");
                    }
                    if (d4 >= 1.0d && d3 == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                        throw new IllegalArgumentException("Parameter c is zero, the transfer function is constant");
                    }
                    if ((d == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN || d7 == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) && d3 == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                        throw new IllegalArgumentException("Parameter a or g is zero, and c is zero, the transfer function is constant");
                    }
                    if (d3 < SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                        throw new IllegalArgumentException("The transfer function must be increasing");
                    }
                    if (d < SContextConstants.ENVIRONMENT_VALUE_UNKNOWN || d7 < SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                        throw new IllegalArgumentException("The transfer function must be positive or increasing");
                    }
                }
                this.a = d;
                this.b = d2;
                this.c = d3;
                this.d = d4;
                this.e = d5;
                this.f = d6;
                this.g = d7;
            }

            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj != null && getClass() == obj.getClass()) {
                    TransferParameters transferParameters = (TransferParameters) obj;
                    if (Double.compare(transferParameters.a, this.a) == 0 && Double.compare(transferParameters.b, this.b) == 0 && Double.compare(transferParameters.c, this.c) == 0 && Double.compare(transferParameters.d, this.d) == 0 && Double.compare(transferParameters.e, this.e) == 0 && Double.compare(transferParameters.f, this.f) == 0 && Double.compare(transferParameters.g, this.g) == 0) {
                        return true;
                    }
                }
                return false;
            }

            public int hashCode() {
                long jDoubleToLongBits = Double.doubleToLongBits(this.a);
                long jDoubleToLongBits2 = Double.doubleToLongBits(this.b);
                int i = (((int) (jDoubleToLongBits ^ (jDoubleToLongBits >>> 32))) * 31) + ((int) (jDoubleToLongBits2 ^ (jDoubleToLongBits2 >>> 32)));
                long jDoubleToLongBits3 = Double.doubleToLongBits(this.c);
                int i2 = (i * 31) + ((int) (jDoubleToLongBits3 ^ (jDoubleToLongBits3 >>> 32)));
                long jDoubleToLongBits4 = Double.doubleToLongBits(this.d);
                int i3 = (i2 * 31) + ((int) (jDoubleToLongBits4 ^ (jDoubleToLongBits4 >>> 32)));
                long jDoubleToLongBits5 = Double.doubleToLongBits(this.e);
                int i4 = (i3 * 31) + ((int) (jDoubleToLongBits5 ^ (jDoubleToLongBits5 >>> 32)));
                long jDoubleToLongBits6 = Double.doubleToLongBits(this.f);
                int i5 = (i4 * 31) + ((int) (jDoubleToLongBits6 ^ (jDoubleToLongBits6 >>> 32)));
                long jDoubleToLongBits7 = Double.doubleToLongBits(this.g);
                return (i5 * 31) + ((int) ((jDoubleToLongBits7 >>> 32) ^ jDoubleToLongBits7));
            }

            /* JADX INFO: Access modifiers changed from: private */
            public boolean isHLGish() {
                return this.g == TYPE_HLGish;
            }

            /* JADX INFO: Access modifiers changed from: private */
            public boolean isPQish() {
                return this.g == TYPE_PQish;
            }
        }

        @Override // android.graphics.ColorSpace
        long getNativeInstance() {
            long j = this.mNativePtr;
            if (j != 0) {
                return j;
            }
            throw new IllegalArgumentException("ColorSpace must use an ICC parametric transfer function! used " + this);
        }

        static class Native {
            static native long nativeCreate(float f, float f2, float f3, float f4, float f5, float f6, float f7, float[] fArr);

            static native long nativeGetNativeFinalizer();

            Native() {
            }
        }

        private static DoubleUnaryOperator generateOETF(final TransferParameters transferParameters) {
            if (transferParameters.isHLGish()) {
                return new DoubleUnaryOperator() { // from class: android.graphics.ColorSpace$Rgb$$ExternalSyntheticLambda2
                    @Override // java.util.function.DoubleUnaryOperator
                    public final double applyAsDouble(double d) {
                        return ColorSpace.transferHLGOETF(transferParameters, d);
                    }
                };
            }
            if (transferParameters.isPQish()) {
                return new DoubleUnaryOperator() { // from class: android.graphics.ColorSpace$Rgb$$ExternalSyntheticLambda3
                    @Override // java.util.function.DoubleUnaryOperator
                    public final double applyAsDouble(double d) {
                        return ColorSpace.transferST2048OETF(transferParameters, d);
                    }
                };
            }
            if (transferParameters.e == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN && transferParameters.f == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                return new DoubleUnaryOperator() { // from class: android.graphics.ColorSpace$Rgb$$ExternalSyntheticLambda4
                    @Override // java.util.function.DoubleUnaryOperator
                    public final double applyAsDouble(double d) {
                        ColorSpace.Rgb.TransferParameters transferParameters2 = transferParameters;
                        return ColorSpace.rcpResponse(d, transferParameters2.a, transferParameters2.b, transferParameters2.c, transferParameters2.d, transferParameters2.g);
                    }
                };
            }
            return new DoubleUnaryOperator() { // from class: android.graphics.ColorSpace$Rgb$$ExternalSyntheticLambda5
                @Override // java.util.function.DoubleUnaryOperator
                public final double applyAsDouble(double d) {
                    ColorSpace.Rgb.TransferParameters transferParameters2 = transferParameters;
                    return ColorSpace.rcpResponse(d, transferParameters2.a, transferParameters2.b, transferParameters2.c, transferParameters2.d, transferParameters2.e, transferParameters2.f, transferParameters2.g);
                }
            };
        }

        private static DoubleUnaryOperator generateEOTF(final TransferParameters transferParameters) {
            if (transferParameters.isHLGish()) {
                return new DoubleUnaryOperator() { // from class: android.graphics.ColorSpace$Rgb$$ExternalSyntheticLambda7
                    @Override // java.util.function.DoubleUnaryOperator
                    public final double applyAsDouble(double d) {
                        return ColorSpace.transferHLGEOTF(transferParameters, d);
                    }
                };
            }
            if (transferParameters.isPQish()) {
                return new DoubleUnaryOperator() { // from class: android.graphics.ColorSpace$Rgb$$ExternalSyntheticLambda8
                    @Override // java.util.function.DoubleUnaryOperator
                    public final double applyAsDouble(double d) {
                        return ColorSpace.transferST2048OETF(transferParameters, d);
                    }
                };
            }
            if (transferParameters.e == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN && transferParameters.f == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                return new DoubleUnaryOperator() { // from class: android.graphics.ColorSpace$Rgb$$ExternalSyntheticLambda9
                    @Override // java.util.function.DoubleUnaryOperator
                    public final double applyAsDouble(double d) {
                        ColorSpace.Rgb.TransferParameters transferParameters2 = transferParameters;
                        return ColorSpace.response(d, transferParameters2.a, transferParameters2.b, transferParameters2.c, transferParameters2.d, transferParameters2.g);
                    }
                };
            }
            return new DoubleUnaryOperator() { // from class: android.graphics.ColorSpace$Rgb$$ExternalSyntheticLambda10
                @Override // java.util.function.DoubleUnaryOperator
                public final double applyAsDouble(double d) {
                    ColorSpace.Rgb.TransferParameters transferParameters2 = transferParameters;
                    return ColorSpace.response(d, transferParameters2.a, transferParameters2.b, transferParameters2.c, transferParameters2.d, transferParameters2.e, transferParameters2.f, transferParameters2.g);
                }
            };
        }

        public Rgb(String str, float[] fArr, DoubleUnaryOperator doubleUnaryOperator, DoubleUnaryOperator doubleUnaryOperator2) {
            this(str, computePrimaries(fArr), computeWhitePoint(fArr), null, doubleUnaryOperator, doubleUnaryOperator2, 0.0f, 1.0f, null, -1);
        }

        public Rgb(String str, float[] fArr, float[] fArr2, DoubleUnaryOperator doubleUnaryOperator, DoubleUnaryOperator doubleUnaryOperator2, float f, float f2) {
            this(str, fArr, fArr2, null, doubleUnaryOperator, doubleUnaryOperator2, f, f2, null, -1);
        }

        public Rgb(String str, float[] fArr, TransferParameters transferParameters) {
            this(str, isGray(fArr) ? ColorSpace.GRAY_PRIMARIES : computePrimaries(fArr), computeWhitePoint(fArr), isGray(fArr) ? fArr : null, transferParameters, -1);
        }

        public Rgb(String str, float[] fArr, float[] fArr2, TransferParameters transferParameters) {
            this(str, fArr, fArr2, null, transferParameters, -1);
        }

        private Rgb(String str, float[] fArr, float[] fArr2, float[] fArr3, TransferParameters transferParameters, int i) {
            this(str, fArr, fArr2, fArr3, generateOETF(transferParameters), generateEOTF(transferParameters), 0.0f, 1.0f, transferParameters, i);
        }

        public Rgb(String str, float[] fArr, double d) {
            this(str, computePrimaries(fArr), computeWhitePoint(fArr), d, 0.0f, 1.0f, -1);
        }

        public Rgb(String str, float[] fArr, float[] fArr2, double d) {
            this(str, fArr, fArr2, d, 0.0f, 1.0f, -1);
        }

        /* JADX WARN: Illegal instructions before constructor call */
        private Rgb(String str, float[] fArr, float[] fArr2, final double d, float f, float f2, int i) {
            DoubleUnaryOperator doubleUnaryOperatorIdentity;
            DoubleUnaryOperator doubleUnaryOperatorIdentity2;
            if (d == 1.0d) {
                doubleUnaryOperatorIdentity = DoubleUnaryOperator.identity();
            } else {
                doubleUnaryOperatorIdentity = new DoubleUnaryOperator() { // from class: android.graphics.ColorSpace$Rgb$$ExternalSyntheticLambda0
                    @Override // java.util.function.DoubleUnaryOperator
                    public final double applyAsDouble(double d2) {
                        return ColorSpace.Rgb.lambda$new$8(d, d2);
                    }
                };
            }
            DoubleUnaryOperator doubleUnaryOperator = doubleUnaryOperatorIdentity;
            if (d == 1.0d) {
                doubleUnaryOperatorIdentity2 = DoubleUnaryOperator.identity();
            } else {
                doubleUnaryOperatorIdentity2 = new DoubleUnaryOperator() { // from class: android.graphics.ColorSpace$Rgb$$ExternalSyntheticLambda1
                    @Override // java.util.function.DoubleUnaryOperator
                    public final double applyAsDouble(double d2) {
                        return ColorSpace.Rgb.lambda$new$9(d, d2);
                    }
                };
            }
            this(str, fArr, fArr2, null, doubleUnaryOperator, doubleUnaryOperatorIdentity2, f, f2, new TransferParameters(1.0d, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, d), i);
        }

        static /* synthetic */ double lambda$new$8(double d, double d2) {
            if (d2 < SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                d2 = 0.0d;
            }
            return Math.pow(d2, 1.0d / d);
        }

        static /* synthetic */ double lambda$new$9(double d, double d2) {
            if (d2 < SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                d2 = 0.0d;
            }
            return Math.pow(d2, d);
        }

        private Rgb(String str, float[] fArr, float[] fArr2, float[] fArr3, DoubleUnaryOperator doubleUnaryOperator, DoubleUnaryOperator doubleUnaryOperator2, float f, float f2, TransferParameters transferParameters, int i) {
            float[] fArr4;
            super(str, Model.RGB, i);
            if (fArr == null || (fArr.length != 6 && fArr.length != 9)) {
                throw new IllegalArgumentException("The color space's primaries must be defined as an array of 6 floats in xyY or 9 floats in XYZ");
            }
            if (fArr2 == null || !(fArr2.length == 2 || fArr2.length == 3)) {
                throw new IllegalArgumentException("The color space's white point must be defined as an array of 2 floats in xyY or 3 float in XYZ");
            }
            if (doubleUnaryOperator == null || doubleUnaryOperator2 == null) {
                throw new IllegalArgumentException("The transfer functions of a color space cannot be null");
            }
            if (f >= f2) {
                throw new IllegalArgumentException("Invalid range: min=" + f + ", max=" + f2 + "; min must be strictly < max");
            }
            float[] fArrXyWhitePoint = xyWhitePoint(fArr2);
            this.mWhitePoint = fArrXyWhitePoint;
            float[] fArrXyPrimaries = xyPrimaries(fArr);
            this.mPrimaries = fArrXyPrimaries;
            if (fArr3 == null) {
                this.mTransform = computeXYZMatrix(fArrXyPrimaries, fArrXyWhitePoint);
            } else {
                if (fArr3.length != 9) {
                    throw new IllegalArgumentException("Transform must have 9 entries! Has " + fArr3.length);
                }
                this.mTransform = fArr3;
            }
            this.mInverseTransform = ColorSpace.inverse3x3(this.mTransform);
            this.mOetf = doubleUnaryOperator;
            this.mEotf = doubleUnaryOperator2;
            this.mMin = f;
            this.mMax = f2;
            DoubleUnaryOperator doubleUnaryOperator3 = new DoubleUnaryOperator() { // from class: android.graphics.ColorSpace$Rgb$$ExternalSyntheticLambda6
                @Override // java.util.function.DoubleUnaryOperator
                public final double applyAsDouble(double d) {
                    return this.f$0.clamp(d);
                }
            };
            this.mClampedOetf = doubleUnaryOperator.andThen(doubleUnaryOperator3);
            this.mClampedEotf = doubleUnaryOperator3.andThen(doubleUnaryOperator2);
            this.mTransferParameters = transferParameters;
            this.mIsWideGamut = isWideGamut(fArrXyPrimaries, f, f2);
            this.mIsSrgb = isSrgb(fArrXyPrimaries, fArrXyWhitePoint, doubleUnaryOperator, doubleUnaryOperator2, f, f2, i);
            if (transferParameters == null) {
                this.mNativePtr = 0L;
                return;
            }
            if (fArrXyWhitePoint == null || (fArr4 = this.mTransform) == null) {
                throw new IllegalStateException("ColorSpace (" + this + ") cannot create native object! mWhitePoint: " + Arrays.toString(fArrXyWhitePoint) + " mTransform: " + Arrays.toString(this.mTransform));
            }
            long jNativeCreate = Native.nativeCreate((float) transferParameters.a, (float) transferParameters.b, (float) transferParameters.c, (float) transferParameters.d, (float) transferParameters.e, (float) transferParameters.f, (float) transferParameters.g, ColorSpace.adaptToIlluminantD50(fArrXyWhitePoint, fArr4));
            this.mNativePtr = jNativeCreate;
            NoImagePreloadHolder.sRegistry.registerNativeAllocation(this, jNativeCreate);
        }

        private static class NoImagePreloadHolder {
            public static final NativeAllocationRegistry sRegistry = new NativeAllocationRegistry(Rgb.class.getClassLoader(), Native.nativeGetNativeFinalizer(), 0);

            private NoImagePreloadHolder() {
            }
        }

        private Rgb(Rgb rgb, float[] fArr, float[] fArr2) {
            this(rgb.getName(), rgb.mPrimaries, fArr2, fArr, rgb.mOetf, rgb.mEotf, rgb.mMin, rgb.mMax, rgb.mTransferParameters, -1);
        }

        public float[] getWhitePoint(float[] fArr) {
            float[] fArr2 = this.mWhitePoint;
            fArr[0] = fArr2[0];
            fArr[1] = fArr2[1];
            return fArr;
        }

        public float[] getWhitePoint() {
            float[] fArr = this.mWhitePoint;
            return Arrays.copyOf(fArr, fArr.length);
        }

        public float[] getPrimaries(float[] fArr) {
            float[] fArr2 = this.mPrimaries;
            System.arraycopy(fArr2, 0, fArr, 0, fArr2.length);
            return fArr;
        }

        public float[] getPrimaries() {
            float[] fArr = this.mPrimaries;
            return Arrays.copyOf(fArr, fArr.length);
        }

        public float[] getTransform(float[] fArr) {
            float[] fArr2 = this.mTransform;
            System.arraycopy(fArr2, 0, fArr, 0, fArr2.length);
            return fArr;
        }

        public float[] getTransform() {
            float[] fArr = this.mTransform;
            return Arrays.copyOf(fArr, fArr.length);
        }

        public float[] getInverseTransform(float[] fArr) {
            float[] fArr2 = this.mInverseTransform;
            System.arraycopy(fArr2, 0, fArr, 0, fArr2.length);
            return fArr;
        }

        public float[] getInverseTransform() {
            float[] fArr = this.mInverseTransform;
            return Arrays.copyOf(fArr, fArr.length);
        }

        public DoubleUnaryOperator getOetf() {
            return this.mClampedOetf;
        }

        public DoubleUnaryOperator getEotf() {
            return this.mClampedEotf;
        }

        public TransferParameters getTransferParameters() {
            TransferParameters transferParameters = this.mTransferParameters;
            if (transferParameters == null || transferParameters.equals(ColorSpace.BT2020_PQ_TRANSFER_PARAMETERS) || this.mTransferParameters.equals(ColorSpace.BT2020_HLG_TRANSFER_PARAMETERS)) {
                return null;
            }
            return this.mTransferParameters;
        }

        @Override // android.graphics.ColorSpace
        public boolean isSrgb() {
            return this.mIsSrgb;
        }

        @Override // android.graphics.ColorSpace
        public boolean isWideGamut() {
            return this.mIsWideGamut;
        }

        @Override // android.graphics.ColorSpace
        public float getMinValue(int i) {
            return this.mMin;
        }

        @Override // android.graphics.ColorSpace
        public float getMaxValue(int i) {
            return this.mMax;
        }

        public float[] toLinear(float f, float f2, float f3) {
            return toLinear(new float[]{f, f2, f3});
        }

        public float[] toLinear(float[] fArr) {
            fArr[0] = (float) this.mClampedEotf.applyAsDouble(fArr[0]);
            fArr[1] = (float) this.mClampedEotf.applyAsDouble(fArr[1]);
            fArr[2] = (float) this.mClampedEotf.applyAsDouble(fArr[2]);
            return fArr;
        }

        public float[] fromLinear(float f, float f2, float f3) {
            return fromLinear(new float[]{f, f2, f3});
        }

        public float[] fromLinear(float[] fArr) {
            fArr[0] = (float) this.mClampedOetf.applyAsDouble(fArr[0]);
            fArr[1] = (float) this.mClampedOetf.applyAsDouble(fArr[1]);
            fArr[2] = (float) this.mClampedOetf.applyAsDouble(fArr[2]);
            return fArr;
        }

        @Override // android.graphics.ColorSpace
        public float[] toXyz(float[] fArr) {
            fArr[0] = (float) this.mClampedEotf.applyAsDouble(fArr[0]);
            fArr[1] = (float) this.mClampedEotf.applyAsDouble(fArr[1]);
            fArr[2] = (float) this.mClampedEotf.applyAsDouble(fArr[2]);
            return ColorSpace.mul3x3Float3(this.mTransform, fArr);
        }

        @Override // android.graphics.ColorSpace
        public float[] fromXyz(float[] fArr) {
            ColorSpace.mul3x3Float3(this.mInverseTransform, fArr);
            fArr[0] = (float) this.mClampedOetf.applyAsDouble(fArr[0]);
            fArr[1] = (float) this.mClampedOetf.applyAsDouble(fArr[1]);
            fArr[2] = (float) this.mClampedOetf.applyAsDouble(fArr[2]);
            return fArr;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public double clamp(double d) {
            float f = this.mMin;
            if (d < f) {
                return f;
            }
            float f2 = this.mMax;
            return d > ((double) f2) ? f2 : d;
        }

        @Override // android.graphics.ColorSpace
        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || getClass() != obj.getClass() || !super.equals(obj)) {
                return false;
            }
            Rgb rgb = (Rgb) obj;
            if (Float.compare(rgb.mMin, this.mMin) != 0 || Float.compare(rgb.mMax, this.mMax) != 0 || !Arrays.equals(this.mWhitePoint, rgb.mWhitePoint) || !Arrays.equals(this.mPrimaries, rgb.mPrimaries)) {
                return false;
            }
            TransferParameters transferParameters = this.mTransferParameters;
            if (transferParameters != null) {
                return transferParameters.equals(rgb.mTransferParameters);
            }
            if (rgb.mTransferParameters == null) {
                return true;
            }
            if (this.mOetf.equals(rgb.mOetf)) {
                return this.mEotf.equals(rgb.mEotf);
            }
            return false;
        }

        @Override // android.graphics.ColorSpace
        public int hashCode() {
            int iHashCode = ((((super.hashCode() * 31) + Arrays.hashCode(this.mWhitePoint)) * 31) + Arrays.hashCode(this.mPrimaries)) * 31;
            float f = this.mMin;
            int iFloatToIntBits = (iHashCode + (f != 0.0f ? Float.floatToIntBits(f) : 0)) * 31;
            float f2 = this.mMax;
            int iFloatToIntBits2 = (iFloatToIntBits + (f2 != 0.0f ? Float.floatToIntBits(f2) : 0)) * 31;
            TransferParameters transferParameters = this.mTransferParameters;
            int iHashCode2 = iFloatToIntBits2 + (transferParameters != null ? transferParameters.hashCode() : 0);
            return this.mTransferParameters == null ? (((iHashCode2 * 31) + this.mOetf.hashCode()) * 31) + this.mEotf.hashCode() : iHashCode2;
        }

        private static boolean isSrgb(float[] fArr, float[] fArr2, DoubleUnaryOperator doubleUnaryOperator, DoubleUnaryOperator doubleUnaryOperator2, float f, float f2, int i) {
            if (i == 0) {
                return true;
            }
            if (!ColorSpace.compare(fArr, ColorSpace.SRGB_PRIMARIES) || !ColorSpace.compare(fArr2, ILLUMINANT_D65) || f != 0.0f || f2 != 1.0f) {
                return false;
            }
            Rgb rgb = (Rgb) get(Named.SRGB);
            for (double d = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN; d <= 1.0d; d += 0.00392156862745098d) {
                if (!compare(d, doubleUnaryOperator, rgb.mOetf) || !compare(d, doubleUnaryOperator2, rgb.mEotf)) {
                    return false;
                }
            }
            return true;
        }

        private static boolean isGray(float[] fArr) {
            return fArr.length == 9 && fArr[1] == 0.0f && fArr[2] == 0.0f && fArr[3] == 0.0f && fArr[5] == 0.0f && fArr[6] == 0.0f && fArr[7] == 0.0f;
        }

        private static boolean compare(double d, DoubleUnaryOperator doubleUnaryOperator, DoubleUnaryOperator doubleUnaryOperator2) {
            return Math.abs(doubleUnaryOperator.applyAsDouble(d) - doubleUnaryOperator2.applyAsDouble(d)) <= 0.001d;
        }

        private static boolean isWideGamut(float[] fArr, float f, float f2) {
            if (area(fArr) / area(ColorSpace.NTSC_1953_PRIMARIES) <= 0.9f || !contains(fArr, ColorSpace.SRGB_PRIMARIES)) {
                return f < 0.0f && f2 > 1.0f;
            }
            return true;
        }

        private static float area(float[] fArr) {
            float f = fArr[0];
            float f2 = fArr[1];
            float f3 = fArr[2];
            float f4 = fArr[3];
            float f5 = fArr[4];
            float f6 = fArr[5];
            float f7 = ((((((f * f4) + (f2 * f5)) + (f3 * f6)) - (f4 * f5)) - (f2 * f3)) - (f * f6)) * 0.5f;
            return f7 < 0.0f ? -f7 : f7;
        }

        private static boolean contains(float[] fArr, float[] fArr2) {
            float f = fArr[0];
            float f2 = fArr2[0];
            float f3 = fArr[1];
            float f4 = fArr2[1];
            float f5 = fArr[2] - fArr2[2];
            float f6 = fArr[3] - fArr2[3];
            float f7 = fArr[4];
            float f8 = fArr2[4];
            float f9 = fArr[5];
            float f10 = fArr2[5];
            float[] fArr3 = {f - f2, f3 - f4, f5, f6, f7 - f8, f9 - f10};
            return cross(fArr3[0], fArr3[1], f2 - f8, f4 - f10) >= 0.0f && cross(fArr2[0] - fArr2[2], fArr2[1] - fArr2[3], fArr3[0], fArr3[1]) >= 0.0f && cross(fArr3[2], fArr3[3], fArr2[2] - fArr2[0], fArr2[3] - fArr2[1]) >= 0.0f && cross(fArr2[2] - fArr2[4], fArr2[3] - fArr2[5], fArr3[2], fArr3[3]) >= 0.0f && cross(fArr3[4], fArr3[5], fArr2[4] - fArr2[2], fArr2[5] - fArr2[3]) >= 0.0f && cross(fArr2[4] - fArr2[0], fArr2[5] - fArr2[1], fArr3[4], fArr3[5]) >= 0.0f;
        }

        private static float[] computePrimaries(float[] fArr) {
            float[] fArrMul3x3Float3 = ColorSpace.mul3x3Float3(fArr, new float[]{1.0f, 0.0f, 0.0f});
            float[] fArrMul3x3Float32 = ColorSpace.mul3x3Float3(fArr, new float[]{0.0f, 1.0f, 0.0f});
            float[] fArrMul3x3Float33 = ColorSpace.mul3x3Float3(fArr, new float[]{0.0f, 0.0f, 1.0f});
            float f = fArrMul3x3Float3[0];
            float f2 = fArrMul3x3Float3[1];
            float f3 = f + f2 + fArrMul3x3Float3[2];
            float f4 = fArrMul3x3Float32[0];
            float f5 = fArrMul3x3Float32[1];
            float f6 = f4 + f5 + fArrMul3x3Float32[2];
            float f7 = fArrMul3x3Float33[0];
            float f8 = fArrMul3x3Float33[1];
            float f9 = f7 + f8 + fArrMul3x3Float33[2];
            return new float[]{f / f3, f2 / f3, f4 / f6, f5 / f6, f7 / f9, f8 / f9};
        }

        private static float[] computeWhitePoint(float[] fArr) {
            float[] fArrMul3x3Float3 = ColorSpace.mul3x3Float3(fArr, new float[]{1.0f, 1.0f, 1.0f});
            float f = fArrMul3x3Float3[0];
            float f2 = fArrMul3x3Float3[1];
            float f3 = f + f2 + fArrMul3x3Float3[2];
            return new float[]{f / f3, f2 / f3};
        }

        private static float[] xyPrimaries(float[] fArr) {
            float[] fArr2 = new float[6];
            if (fArr.length == 9) {
                float f = fArr[0];
                float f2 = fArr[1];
                float f3 = f + f2 + fArr[2];
                fArr2[0] = f / f3;
                fArr2[1] = f2 / f3;
                float f4 = fArr[3];
                float f5 = fArr[4];
                float f6 = f4 + f5 + fArr[5];
                fArr2[2] = f4 / f6;
                fArr2[3] = f5 / f6;
                float f7 = fArr[6];
                float f8 = fArr[7];
                float f9 = f7 + f8 + fArr[8];
                fArr2[4] = f7 / f9;
                fArr2[5] = f8 / f9;
                return fArr2;
            }
            System.arraycopy(fArr, 0, fArr2, 0, 6);
            return fArr2;
        }

        private static float[] xyWhitePoint(float[] fArr) {
            float[] fArr2 = new float[2];
            if (fArr.length == 3) {
                float f = fArr[0];
                float f2 = fArr[1];
                float f3 = f + f2 + fArr[2];
                fArr2[0] = f / f3;
                fArr2[1] = f2 / f3;
                return fArr2;
            }
            System.arraycopy(fArr, 0, fArr2, 0, 2);
            return fArr2;
        }

        private static float[] computeXYZMatrix(float[] fArr, float[] fArr2) {
            float f = fArr[0];
            float f2 = fArr[1];
            float f3 = fArr[2];
            float f4 = fArr[3];
            float f5 = fArr[4];
            float f6 = fArr[5];
            float f7 = fArr2[0];
            float f8 = fArr2[1];
            float f9 = 1.0f - f;
            float f10 = f9 / f2;
            float f11 = 1.0f - f3;
            float f12 = 1.0f - f5;
            float f13 = (1.0f - f7) / f8;
            float f14 = f / f2;
            float f15 = (f3 / f4) - f14;
            float f16 = (f7 / f8) - f14;
            float f17 = (f11 / f4) - f10;
            float f18 = (f5 / f6) - f14;
            float f19 = (((f13 - f10) * f15) - (f16 * f17)) / ((((f12 / f6) - f10) * f15) - (f17 * f18));
            float f20 = (f16 - (f18 * f19)) / f15;
            float f21 = (1.0f - f20) - f19;
            float f22 = f21 / f2;
            float f23 = f20 / f4;
            float f24 = f19 / f6;
            return new float[]{f * f22, f21, f22 * (f9 - f2), f3 * f23, f20, f23 * (f11 - f4), f5 * f24, f19, f24 * (f12 - f6)};
        }
    }

    public static class Connector {
        private final ColorSpace mDestination;
        private final RenderIntent mIntent;
        private final ColorSpace mSource;
        private final float[] mTransform;
        private final ColorSpace mTransformDestination;
        private final ColorSpace mTransformSource;

        Connector(ColorSpace colorSpace, ColorSpace colorSpace2, RenderIntent renderIntent) {
            this(colorSpace, colorSpace2, colorSpace.getModel() == Model.RGB ? ColorSpace.adapt(colorSpace, ColorSpace.ILLUMINANT_D50_XYZ) : colorSpace, colorSpace2.getModel() == Model.RGB ? ColorSpace.adapt(colorSpace2, ColorSpace.ILLUMINANT_D50_XYZ) : colorSpace2, renderIntent, computeTransform(colorSpace, colorSpace2, renderIntent));
        }

        private Connector(ColorSpace colorSpace, ColorSpace colorSpace2, ColorSpace colorSpace3, ColorSpace colorSpace4, RenderIntent renderIntent, float[] fArr) {
            this.mSource = colorSpace;
            this.mDestination = colorSpace2;
            this.mTransformSource = colorSpace3;
            this.mTransformDestination = colorSpace4;
            this.mIntent = renderIntent;
            this.mTransform = fArr;
        }

        private static float[] computeTransform(ColorSpace colorSpace, ColorSpace colorSpace2, RenderIntent renderIntent) {
            if (renderIntent != RenderIntent.ABSOLUTE) {
                return null;
            }
            boolean z = colorSpace.getModel() == Model.RGB;
            boolean z2 = colorSpace2.getModel() == Model.RGB;
            if (z && z2) {
                return null;
            }
            if (!z && !z2) {
                return null;
            }
            if (!z) {
                colorSpace = colorSpace2;
            }
            Rgb rgb = (Rgb) colorSpace;
            float[] fArrXyYToXyz = z ? ColorSpace.xyYToXyz(rgb.mWhitePoint) : ColorSpace.ILLUMINANT_D50_XYZ;
            float[] fArrXyYToXyz2 = z2 ? ColorSpace.xyYToXyz(rgb.mWhitePoint) : ColorSpace.ILLUMINANT_D50_XYZ;
            return new float[]{fArrXyYToXyz[0] / fArrXyYToXyz2[0], fArrXyYToXyz[1] / fArrXyYToXyz2[1], fArrXyYToXyz[2] / fArrXyYToXyz2[2]};
        }

        public ColorSpace getSource() {
            return this.mSource;
        }

        public ColorSpace getDestination() {
            return this.mDestination;
        }

        public RenderIntent getRenderIntent() {
            return this.mIntent;
        }

        public float[] transform(float f, float f2, float f3) {
            return transform(new float[]{f, f2, f3});
        }

        public float[] transform(float[] fArr) {
            float[] xyz = this.mTransformSource.toXyz(fArr);
            float[] fArr2 = this.mTransform;
            if (fArr2 != null) {
                xyz[0] = xyz[0] * fArr2[0];
                xyz[1] = xyz[1] * fArr2[1];
                xyz[2] = xyz[2] * fArr2[2];
            }
            return this.mTransformDestination.fromXyz(xyz);
        }

        private static class Rgb extends Connector {
            private final Rgb mDestination;
            private final Rgb mSource;
            private final float[] mTransform;

            Rgb(Rgb rgb, Rgb rgb2, RenderIntent renderIntent) {
                super(rgb2, rgb, rgb2, renderIntent, null);
                this.mSource = rgb;
                this.mDestination = rgb2;
                this.mTransform = computeTransform(rgb, rgb2, renderIntent);
            }

            @Override // android.graphics.ColorSpace.Connector
            public float[] transform(float[] fArr) {
                fArr[0] = (float) this.mSource.mClampedEotf.applyAsDouble(fArr[0]);
                fArr[1] = (float) this.mSource.mClampedEotf.applyAsDouble(fArr[1]);
                fArr[2] = (float) this.mSource.mClampedEotf.applyAsDouble(fArr[2]);
                ColorSpace.mul3x3Float3(this.mTransform, fArr);
                fArr[0] = (float) this.mDestination.mClampedOetf.applyAsDouble(fArr[0]);
                fArr[1] = (float) this.mDestination.mClampedOetf.applyAsDouble(fArr[1]);
                fArr[2] = (float) this.mDestination.mClampedOetf.applyAsDouble(fArr[2]);
                return fArr;
            }

            private static float[] computeTransform(Rgb rgb, Rgb rgb2, RenderIntent renderIntent) {
                if (ColorSpace.compare(rgb.mWhitePoint, rgb2.mWhitePoint)) {
                    return ColorSpace.mul3x3(rgb2.mInverseTransform, rgb.mTransform);
                }
                float[] fArrMul3x3Diag = rgb.mTransform;
                float[] fArrInverse3x3 = rgb2.mInverseTransform;
                float[] fArrXyYToXyz = ColorSpace.xyYToXyz(rgb.mWhitePoint);
                float[] fArrXyYToXyz2 = ColorSpace.xyYToXyz(rgb2.mWhitePoint);
                if (!ColorSpace.compare(rgb.mWhitePoint, ColorSpace.ILLUMINANT_D50)) {
                    fArrMul3x3Diag = ColorSpace.mul3x3(ColorSpace.chromaticAdaptation(Adaptation.BRADFORD.mTransform, fArrXyYToXyz, Arrays.copyOf(ColorSpace.ILLUMINANT_D50_XYZ, 3)), rgb.mTransform);
                }
                if (!ColorSpace.compare(rgb2.mWhitePoint, ColorSpace.ILLUMINANT_D50)) {
                    fArrInverse3x3 = ColorSpace.inverse3x3(ColorSpace.mul3x3(ColorSpace.chromaticAdaptation(Adaptation.BRADFORD.mTransform, fArrXyYToXyz2, Arrays.copyOf(ColorSpace.ILLUMINANT_D50_XYZ, 3)), rgb2.mTransform));
                }
                if (renderIntent == RenderIntent.ABSOLUTE) {
                    fArrMul3x3Diag = ColorSpace.mul3x3Diag(new float[]{fArrXyYToXyz[0] / fArrXyYToXyz2[0], fArrXyYToXyz[1] / fArrXyYToXyz2[1], fArrXyYToXyz[2] / fArrXyYToXyz2[2]}, fArrMul3x3Diag);
                }
                return ColorSpace.mul3x3(fArrInverse3x3, fArrMul3x3Diag);
            }
        }

        static Connector identity(ColorSpace colorSpace) {
            return new Connector(colorSpace, colorSpace, RenderIntent.RELATIVE) { // from class: android.graphics.ColorSpace.Connector.1
                @Override // android.graphics.ColorSpace.Connector
                public float[] transform(float[] fArr) {
                    return fArr;
                }
            };
        }
    }
}
