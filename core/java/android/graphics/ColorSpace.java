package android.graphics;

import android.hardware.scontext.SContextConstants;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.util.SparseIntArray;

import com.android.graphics.flags.Flags;

import libcore.util.NativeAllocationRegistry;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.function.DoubleUnaryOperator;

/* loaded from: classes.dex */
public abstract class ColorSpace {
    public static final int MAX_ID = 63;
    public static final int MIN_ID = -1;
    private final int mId;
    private final Model mModel;
    private final String mName;
    public static final float[] ILLUMINANT_A = {0.44757f, 0.40745f};
    public static final float[] ILLUMINANT_B = {0.34842f, 0.35161f};
    public static final float[] ILLUMINANT_C = {0.31006f, 0.31616f};
    public static final float[] ILLUMINANT_D50 = {0.34567f, 0.3585f};
    public static final float[] ILLUMINANT_D55 = {0.33242f, 0.34743f};
    public static final float[] ILLUMINANT_D60 = {0.32168f, 0.33767f};
    public static final float[] ILLUMINANT_D65 = {0.31271f, 0.32902f};
    public static final float[] ILLUMINANT_D75 = {0.29902f, 0.31485f};
    public static final float[] ILLUMINANT_E = {0.33333f, 0.33333f};
    private static final float[] SRGB_PRIMARIES = {0.64f, 0.33f, 0.3f, 0.6f, 0.15f, 0.06f};
    private static final float[] NTSC_1953_PRIMARIES = {0.67f, 0.33f, 0.21f, 0.71f, 0.14f, 0.08f};
    private static final float[] DCI_P3_PRIMARIES = {0.68f, 0.32f, 0.265f, 0.69f, 0.15f, 0.06f};
    private static final float[] BT2020_PRIMARIES = {0.708f, 0.292f, 0.17f, 0.797f, 0.131f, 0.046f};
    private static final float[] GRAY_PRIMARIES = {1.0f, 1.0f, 1.0f, 1.0f, 1.0f, 1.0f};
    private static final float[] ILLUMINANT_D50_XYZ = {0.964212f, 1.0f, 0.825188f};
    private static final Rgb.TransferParameters SRGB_TRANSFER_PARAMETERS =
            new Rgb.TransferParameters(
                    0.9478672985781991d,
                    0.05213270142180095d,
                    0.07739938080495357d,
                    0.04045d,
                    2.4d);
    private static final Rgb.TransferParameters SMPTE_170M_TRANSFER_PARAMETERS =
            new Rgb.TransferParameters(
                    0.9099181073703367d,
                    0.09008189262966333d,
                    0.2222222222222222d,
                    0.081d,
                    2.2222222222222223d);
    private static final Rgb.TransferParameters BT2020_HLG_TRANSFER_PARAMETERS =
            new Rgb.TransferParameters(
                    2.0d, 2.0d, 5.591816309728916d, 0.28466892d, 0.55991073d, -0.685490157d, -3.0d);
    private static final Rgb.TransferParameters BT2020_PQ_TRANSFER_PARAMETERS =
            new Rgb.TransferParameters(
                    -1.555223d,
                    1.860454d,
                    0.012683313515655966d,
                    18.8515625d,
                    -18.6875d,
                    6.277394636015326d,
                    -2.0d);
    private static final HashMap<Integer, ColorSpace> sNamedColorSpaceMap = new HashMap<>();
    private static final SparseIntArray sDataToColorSpaces = new SparseIntArray();

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
        OK_LAB
    }

    public enum RenderIntent {
        PERCEPTUAL,
        RELATIVE,
        SATURATION,
        ABSOLUTE
    }

    public abstract float[] fromXyz(float[] fArr);

    public abstract float getMaxValue(int i);

    public abstract float getMinValue(int i);

    public abstract boolean isWideGamut();

    public abstract float[] toXyz(float[] fArr);

    static {
        sNamedColorSpaceMap.put(
                Integer.valueOf(Named.SRGB.ordinal()),
                new Rgb(
                        "sRGB IEC61966-2.1",
                        SRGB_PRIMARIES,
                        ILLUMINANT_D65,
                        (float[]) null,
                        SRGB_TRANSFER_PARAMETERS,
                        Named.SRGB.ordinal()));
        sDataToColorSpaces.put(142671872, Named.SRGB.ordinal());
        sNamedColorSpaceMap.put(
                Integer.valueOf(Named.LINEAR_SRGB.ordinal()),
                new Rgb(
                        "sRGB IEC61966-2.1 (Linear)",
                        SRGB_PRIMARIES,
                        ILLUMINANT_D65,
                        1.0d,
                        0.0f,
                        1.0f,
                        Named.LINEAR_SRGB.ordinal()));
        sDataToColorSpaces.put(138477568, Named.LINEAR_SRGB.ordinal());
        sNamedColorSpaceMap.put(
                Integer.valueOf(Named.EXTENDED_SRGB.ordinal()),
                new Rgb(
                        "scRGB-nl IEC 61966-2-2:2003",
                        SRGB_PRIMARIES,
                        ILLUMINANT_D65,
                        null,
                        new DoubleUnaryOperator() { // from class:
                            // android.graphics.ColorSpace$$ExternalSyntheticLambda0
                            @Override // java.util.function.DoubleUnaryOperator
                            public final double applyAsDouble(double d) {
                                double absRcpResponse;
                                absRcpResponse =
                                        ColorSpace.absRcpResponse(
                                                d,
                                                0.9478672985781991d,
                                                0.05213270142180095d,
                                                0.07739938080495357d,
                                                0.04045d,
                                                2.4d);
                                return absRcpResponse;
                            }
                        },
                        new DoubleUnaryOperator() { // from class:
                            // android.graphics.ColorSpace$$ExternalSyntheticLambda1
                            @Override // java.util.function.DoubleUnaryOperator
                            public final double applyAsDouble(double d) {
                                double absResponse;
                                absResponse =
                                        ColorSpace.absResponse(
                                                d,
                                                0.9478672985781991d,
                                                0.05213270142180095d,
                                                0.07739938080495357d,
                                                0.04045d,
                                                2.4d);
                                return absResponse;
                            }
                        },
                        -0.799f,
                        2.399f,
                        SRGB_TRANSFER_PARAMETERS,
                        Named.EXTENDED_SRGB.ordinal()));
        sDataToColorSpaces.put(411107328, Named.EXTENDED_SRGB.ordinal());
        sNamedColorSpaceMap.put(
                Integer.valueOf(Named.LINEAR_EXTENDED_SRGB.ordinal()),
                new Rgb(
                        "scRGB IEC 61966-2-2:2003",
                        SRGB_PRIMARIES,
                        ILLUMINANT_D65,
                        1.0d,
                        -0.5f,
                        7.499f,
                        Named.LINEAR_EXTENDED_SRGB.ordinal()));
        sDataToColorSpaces.put(406913024, Named.LINEAR_EXTENDED_SRGB.ordinal());
        float[] fArr = null;
        sNamedColorSpaceMap.put(
                Integer.valueOf(Named.BT709.ordinal()),
                new Rgb(
                        "Rec. ITU-R BT.709-5",
                        SRGB_PRIMARIES,
                        ILLUMINANT_D65,
                        fArr,
                        SMPTE_170M_TRANSFER_PARAMETERS,
                        Named.BT709.ordinal()));
        sDataToColorSpaces.put(281083904, Named.BT709.ordinal());
        sNamedColorSpaceMap.put(
                Integer.valueOf(Named.BT2020.ordinal()),
                new Rgb(
                        "Rec. ITU-R BT.2020-1",
                        BT2020_PRIMARIES,
                        ILLUMINANT_D65,
                        fArr,
                        new Rgb.TransferParameters(
                                0.9096697898662786d,
                                0.09033021013372146d,
                                0.2222222222222222d,
                                0.08145d,
                                2.2222222222222223d),
                        Named.BT2020.ordinal()));
        sDataToColorSpaces.put(147193856, Named.BT2020.ordinal());
        sNamedColorSpaceMap.put(
                Integer.valueOf(Named.DCI_P3.ordinal()),
                new Rgb(
                        "SMPTE RP 431-2-2007 DCI (P3)",
                        DCI_P3_PRIMARIES,
                        new float[] {0.314f, 0.351f},
                        2.6d,
                        0.0f,
                        1.0f,
                        Named.DCI_P3.ordinal()));
        sDataToColorSpaces.put(155844608, Named.DCI_P3.ordinal());
        float[] fArr2 = null;
        sNamedColorSpaceMap.put(
                Integer.valueOf(Named.DISPLAY_P3.ordinal()),
                new Rgb(
                        "Display P3",
                        DCI_P3_PRIMARIES,
                        ILLUMINANT_D65,
                        fArr2,
                        SRGB_TRANSFER_PARAMETERS,
                        Named.DISPLAY_P3.ordinal()));
        sDataToColorSpaces.put(143261696, Named.DISPLAY_P3.ordinal());
        sNamedColorSpaceMap.put(
                Integer.valueOf(Named.NTSC_1953.ordinal()),
                new Rgb(
                        "NTSC (1953)",
                        NTSC_1953_PRIMARIES,
                        ILLUMINANT_C,
                        fArr2,
                        SMPTE_170M_TRANSFER_PARAMETERS,
                        Named.NTSC_1953.ordinal()));
        sNamedColorSpaceMap.put(
                Integer.valueOf(Named.SMPTE_C.ordinal()),
                new Rgb(
                        "SMPTE-C RGB",
                        new float[] {0.63f, 0.34f, 0.31f, 0.595f, 0.155f, 0.07f},
                        ILLUMINANT_D65,
                        fArr2,
                        SMPTE_170M_TRANSFER_PARAMETERS,
                        Named.SMPTE_C.ordinal()));
        sNamedColorSpaceMap.put(
                Integer.valueOf(Named.ADOBE_RGB.ordinal()),
                new Rgb(
                        "Adobe RGB (1998)",
                        new float[] {0.64f, 0.33f, 0.21f, 0.71f, 0.15f, 0.06f},
                        ILLUMINANT_D65,
                        2.2d,
                        0.0f,
                        1.0f,
                        Named.ADOBE_RGB.ordinal()));
        sDataToColorSpaces.put(151715840, Named.ADOBE_RGB.ordinal());
        sNamedColorSpaceMap.put(
                Integer.valueOf(Named.PRO_PHOTO_RGB.ordinal()),
                new Rgb(
                        "ROMM RGB ISO 22028-2:2013",
                        new float[] {0.7347f, 0.2653f, 0.1596f, 0.8404f, 0.0366f, 1.0E-4f},
                        ILLUMINANT_D50,
                        (float[]) null,
                        new Rgb.TransferParameters(
                                1.0d,
                                SContextConstants.ENVIRONMENT_VALUE_UNKNOWN,
                                0.0625d,
                                0.031248d,
                                1.8d),
                        Named.PRO_PHOTO_RGB.ordinal()));
        double d = 1.0d;
        float f = -65504.0f;
        float f2 = 65504.0f;
        sNamedColorSpaceMap.put(
                Integer.valueOf(Named.ACES.ordinal()),
                new Rgb(
                        "SMPTE ST 2065-1:2012 ACES",
                        new float[] {0.7347f, 0.2653f, 0.0f, 1.0f, 1.0E-4f, -0.077f},
                        ILLUMINANT_D60,
                        d,
                        f,
                        f2,
                        Named.ACES.ordinal()));
        sNamedColorSpaceMap.put(
                Integer.valueOf(Named.ACESCG.ordinal()),
                new Rgb(
                        "Academy S-2014-004 ACEScg",
                        new float[] {0.713f, 0.293f, 0.165f, 0.83f, 0.128f, 0.044f},
                        ILLUMINANT_D60,
                        d,
                        f,
                        f2,
                        Named.ACESCG.ordinal()));
        byte b = 0;
        sNamedColorSpaceMap.put(
                Integer.valueOf(Named.CIE_XYZ.ordinal()),
                new Xyz("Generic XYZ", Named.CIE_XYZ.ordinal()));
        sNamedColorSpaceMap.put(
                Integer.valueOf(Named.CIE_LAB.ordinal()),
                new Lab("Generic L*a*b*", Named.CIE_LAB.ordinal()));
        float[] fArr3 = null;
        float f3 = 0.0f;
        float f4 = 1.0f;
        sNamedColorSpaceMap.put(
                Integer.valueOf(Named.BT2020_HLG.ordinal()),
                new Rgb(
                        "Hybrid Log Gamma encoding",
                        BT2020_PRIMARIES,
                        ILLUMINANT_D65,
                        fArr3,
                        new DoubleUnaryOperator() { // from class:
                            // android.graphics.ColorSpace$$ExternalSyntheticLambda2
                            @Override // java.util.function.DoubleUnaryOperator
                            public final double applyAsDouble(double d2) {
                                double transferHLGOETF;
                                transferHLGOETF =
                                        ColorSpace.transferHLGOETF(
                                                ColorSpace.BT2020_HLG_TRANSFER_PARAMETERS, d2);
                                return transferHLGOETF;
                            }
                        },
                        new DoubleUnaryOperator() { // from class:
                            // android.graphics.ColorSpace$$ExternalSyntheticLambda3
                            @Override // java.util.function.DoubleUnaryOperator
                            public final double applyAsDouble(double d2) {
                                double transferHLGEOTF;
                                transferHLGEOTF =
                                        ColorSpace.transferHLGEOTF(
                                                ColorSpace.BT2020_HLG_TRANSFER_PARAMETERS, d2);
                                return transferHLGEOTF;
                            }
                        },
                        f3,
                        f4,
                        BT2020_HLG_TRANSFER_PARAMETERS,
                        Named.BT2020_HLG.ordinal()));
        sDataToColorSpaces.put(168165376, Named.BT2020_HLG.ordinal());
        sNamedColorSpaceMap.put(
                Integer.valueOf(Named.BT2020_PQ.ordinal()),
                new Rgb(
                        "Perceptual Quantizer encoding",
                        BT2020_PRIMARIES,
                        ILLUMINANT_D65,
                        fArr3,
                        new DoubleUnaryOperator() { // from class:
                            // android.graphics.ColorSpace$$ExternalSyntheticLambda4
                            @Override // java.util.function.DoubleUnaryOperator
                            public final double applyAsDouble(double d2) {
                                double transferST2048OETF;
                                transferST2048OETF =
                                        ColorSpace.transferST2048OETF(
                                                ColorSpace.BT2020_PQ_TRANSFER_PARAMETERS, d2);
                                return transferST2048OETF;
                            }
                        },
                        new DoubleUnaryOperator() { // from class:
                            // android.graphics.ColorSpace$$ExternalSyntheticLambda5
                            @Override // java.util.function.DoubleUnaryOperator
                            public final double applyAsDouble(double d2) {
                                double transferST2048EOTF;
                                transferST2048EOTF =
                                        ColorSpace.transferST2048EOTF(
                                                ColorSpace.BT2020_PQ_TRANSFER_PARAMETERS, d2);
                                return transferST2048EOTF;
                            }
                        },
                        f3,
                        f4,
                        BT2020_PQ_TRANSFER_PARAMETERS,
                        Named.BT2020_PQ.ordinal()));
        sDataToColorSpaces.put(163971072, Named.BT2020_PQ.ordinal());
        if (Flags.okLabColorspace()) {
            sNamedColorSpaceMap.put(
                    Integer.valueOf(Named.OK_LAB.ordinal()),
                    new OkLab("Oklab", Named.OK_LAB.ordinal()));
        }
    }

    public enum Adaptation {
        BRADFORD(
                new float[] {
                    0.8951f, -0.7502f, 0.0389f, 0.2664f, 1.7135f, -0.0685f, -0.1614f, 0.0367f,
                    1.0296f
                }),
        VON_KRIES(
                new float[] {
                    0.40024f, -0.2263f, 0.0f, 0.7076f, 1.16532f, 0.0f, -0.08081f, 0.0457f, 0.91822f
                }),
        CIECAT02(
                new float[] {
                    0.7328f, -0.7036f, 0.003f, 0.4296f, 1.6975f, 0.0136f, -0.1624f, 0.0061f, 0.9834f
                });

        final float[] mTransform;

        Adaptation(float[] transform) {
            this.mTransform = transform;
        }
    }

    public enum Model {
        RGB(3),
        XYZ(3),
        LAB(3),
        CMYK(4);

        private final int mComponentCount;

        Model(int componentCount) {
            this.mComponentCount = componentCount;
        }

        public int getComponentCount() {
            return this.mComponentCount;
        }
    }

    ColorSpace(String name, Model model, int id) {
        if (name == null || name.length() < 1) {
            throw new IllegalArgumentException(
                    "The name of a color space cannot be null and must contain at least 1"
                            + " character");
        }
        if (model == null) {
            throw new IllegalArgumentException("A color space must have a model");
        }
        if (id < -1 || id > 63) {
            throw new IllegalArgumentException("The id must be between -1 and 63");
        }
        this.mName = name;
        this.mModel = model;
        this.mId = id;
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

    public boolean isSrgb() {
        return false;
    }

    public float[] toXyz(float r, float g, float b) {
        return toXyz(new float[] {r, g, b});
    }

    public float[] fromXyz(float x, float y, float z) {
        float[] xyz = new float[this.mModel.getComponentCount()];
        xyz[0] = x;
        xyz[1] = y;
        xyz[2] = z;
        return fromXyz(xyz);
    }

    public String toString() {
        return this.mName
                + " (id="
                + this.mId
                + ", model="
                + this.mModel
                + NavigationBarInflaterView.KEY_CODE_END;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ColorSpace that = (ColorSpace) o;
        if (this.mId == that.mId && this.mName.equals(that.mName) && this.mModel == that.mModel) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int result = this.mName.hashCode();
        return (((result * 31) + this.mModel.hashCode()) * 31) + this.mId;
    }

    public static Connector connect(ColorSpace source, ColorSpace destination) {
        return connect(source, destination, RenderIntent.PERCEPTUAL);
    }

    public static Connector connect(
            ColorSpace source, ColorSpace destination, RenderIntent intent) {
        if (source.equals(destination)) {
            return Connector.identity(source);
        }
        if (source.getModel() == Model.RGB && destination.getModel() == Model.RGB) {
            return new Connector.Rgb((Rgb) source, (Rgb) destination, intent);
        }
        return new Connector(source, destination, intent);
    }

    public static Connector connect(ColorSpace source) {
        return connect(source, RenderIntent.PERCEPTUAL);
    }

    public static Connector connect(ColorSpace source, RenderIntent intent) {
        if (source.isSrgb()) {
            return Connector.identity(source);
        }
        if (source.getModel() == Model.RGB) {
            return new Connector.Rgb((Rgb) source, (Rgb) get(Named.SRGB), intent);
        }
        return new Connector(source, get(Named.SRGB), intent);
    }

    public static ColorSpace adapt(ColorSpace colorSpace, float[] whitePoint) {
        return adapt(colorSpace, whitePoint, Adaptation.BRADFORD);
    }

    public static ColorSpace adapt(
            ColorSpace colorSpace, float[] whitePoint, Adaptation adaptation) {
        if (colorSpace.getModel() == Model.RGB) {
            Rgb rgb = (Rgb) colorSpace;
            if (compare(rgb.mWhitePoint, whitePoint)) {
                return colorSpace;
            }
            float[] xyz =
                    whitePoint.length == 3 ? Arrays.copyOf(whitePoint, 3) : xyYToXyz(whitePoint);
            float[] adaptationTransform =
                    chromaticAdaptation(adaptation.mTransform, xyYToXyz(rgb.getWhitePoint()), xyz);
            float[] transform = mul3x3(adaptationTransform, rgb.mTransform);
            return new Rgb(rgb, transform, whitePoint);
        }
        return colorSpace;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static float[] adaptToIlluminantD50(float[] origWhitePoint, float[] origTransform) {
        float[] desired = ILLUMINANT_D50;
        if (compare(origWhitePoint, desired)) {
            return origTransform;
        }
        float[] xyz = xyYToXyz(desired);
        float[] adaptationTransform =
                chromaticAdaptation(Adaptation.BRADFORD.mTransform, xyYToXyz(origWhitePoint), xyz);
        return mul3x3(adaptationTransform, origTransform);
    }

    static ColorSpace get(int index) {
        ColorSpace colorspace = sNamedColorSpaceMap.get(Integer.valueOf(index));
        if (colorspace == null) {
            throw new IllegalArgumentException("Invalid ID: " + index);
        }
        return colorspace;
    }

    public static ColorSpace getFromDataSpace(int dataSpace) {
        int index = sDataToColorSpaces.get(dataSpace, -1);
        if (index != -1) {
            return get(index);
        }
        return null;
    }

    public int getDataSpace() {
        int index = sDataToColorSpaces.indexOfValue(getId());
        if (index != -1) {
            return sDataToColorSpaces.keyAt(index);
        }
        return 0;
    }

    public static ColorSpace get(Named name) {
        ColorSpace colorSpace = sNamedColorSpaceMap.get(Integer.valueOf(name.ordinal()));
        if (colorSpace == null) {
            return sNamedColorSpaceMap.get(Integer.valueOf(Named.SRGB.ordinal()));
        }
        return colorSpace;
    }

    public static ColorSpace match(float[] toXYZD50, Rgb.TransferParameters function) {
        Collection<ColorSpace> colorspaces = sNamedColorSpaceMap.values();
        for (ColorSpace colorSpace : colorspaces) {
            if (colorSpace.getModel() == Model.RGB) {
                Rgb rgb = (Rgb) adapt(colorSpace, ILLUMINANT_D50_XYZ);
                if (compare(toXYZD50, rgb.mTransform)
                        && compare(function, rgb.mTransferParameters)) {
                    return colorSpace;
                }
            }
        }
        return null;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static double transferHLGOETF(Rgb.TransferParameters params, double x) {
        double sign = x < SContextConstants.ENVIRONMENT_VALUE_UNKNOWN ? -1.0d : 1.0d;
        double x2 = x * sign;
        double R = 1.0d / params.a;
        double G = 1.0d / params.b;
        double a = 1.0d / params.c;
        double b = params.d;
        double c = params.e;
        double sign2 = sign;
        double K = params.f + 1.0d;
        double x3 = x2 / K;
        return (x3 <= 1.0d ? Math.pow(x3, G) * R : (Math.log(x3 - b) * a) + c) * sign2;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static double transferHLGEOTF(Rgb.TransferParameters params, double x) {
        double sign = x < SContextConstants.ENVIRONMENT_VALUE_UNKNOWN ? -1.0d : 1.0d;
        double x2 = x * sign;
        double R = params.a;
        double G = params.b;
        double a = params.c;
        double b = params.d;
        double c = params.e;
        double b2 = params.f;
        double K = b2 + 1.0d;
        return K * sign * (x2 * R <= 1.0d ? Math.pow(x2 * R, G) : Math.exp((x2 - c) * a) + b);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static double transferST2048OETF(Rgb.TransferParameters params, double x) {
        double sign = x < SContextConstants.ENVIRONMENT_VALUE_UNKNOWN ? -1.0d : 1.0d;
        double x2 = x * sign;
        double a = -params.a;
        double b = params.d;
        double c = 1.0d / params.f;
        double d = params.b;
        double e = -params.e;
        double f = 1.0d / params.c;
        double tmp =
                Math.max(a + (Math.pow(x2, c) * b), SContextConstants.ENVIRONMENT_VALUE_UNKNOWN);
        return Math.pow(tmp / ((Math.pow(x2, c) * e) + d), f) * sign;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static double transferST2048EOTF(Rgb.TransferParameters pq, double x) {
        double sign = x < SContextConstants.ENVIRONMENT_VALUE_UNKNOWN ? -1.0d : 1.0d;
        double x2 = x * sign;
        double tmp =
                Math.max(
                        pq.a + (pq.b * Math.pow(x2, pq.c)),
                        SContextConstants.ENVIRONMENT_VALUE_UNKNOWN);
        return Math.pow(tmp / (pq.d + (pq.e * Math.pow(x2, pq.c))), pq.f) * sign;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static double rcpResponse(double x, double a, double b, double c, double d, double g) {
        return x >= d * c ? (Math.pow(x, 1.0d / g) - b) / a : x / c;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static double response(double x, double a, double b, double c, double d, double g) {
        return x >= d ? Math.pow((a * x) + b, g) : c * x;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static double rcpResponse(
            double x, double a, double b, double c, double d, double e, double f, double g) {
        return x >= d * c ? (Math.pow(x - e, 1.0d / g) - b) / a : (x - f) / c;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static double response(
            double x, double a, double b, double c, double d, double e, double f, double g) {
        return x >= d ? Math.pow((a * x) + b, g) + e : (c * x) + f;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static double absRcpResponse(
            double x, double a, double b, double c, double d, double g) {
        return Math.copySign(
                rcpResponse(
                        x < SContextConstants.ENVIRONMENT_VALUE_UNKNOWN ? -x : x, a, b, c, d, g),
                x);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static double absResponse(double x, double a, double b, double c, double d, double g) {
        return Math.copySign(
                response(x < SContextConstants.ENVIRONMENT_VALUE_UNKNOWN ? -x : x, a, b, c, d, g),
                x);
    }

    private static boolean compare(Rgb.TransferParameters a, Rgb.TransferParameters b) {
        if (a == null && b == null) {
            return true;
        }
        return a != null
                && b != null
                && Math.abs(a.a - b.a) < 0.001d
                && Math.abs(a.b - b.b) < 0.001d
                && Math.abs(a.c - b.c) < 0.001d
                && Math.abs(a.d - b.d) < 0.002d
                && Math.abs(a.e - b.e) < 0.001d
                && Math.abs(a.f - b.f) < 0.001d
                && Math.abs(a.g - b.g) < 0.001d;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static boolean compare(float[] a, float[] b) {
        if (a == b) {
            return true;
        }
        for (int i = 0; i < a.length; i++) {
            if (Float.compare(a[i], b[i]) != 0 && Math.abs(a[i] - b[i]) > 0.001f) {
                return false;
            }
        }
        return true;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static float[] inverse3x3(float[] m) {
        float a = m[0];
        float b = m[3];
        float c = m[6];
        float d = m[1];
        float e = m[4];
        float f = m[7];
        float g = m[2];
        float h = m[5];
        float i = m[8];
        float A = (e * i) - (f * h);
        float B = (f * g) - (d * i);
        float C = (d * h) - (e * g);
        float det = (a * A) + (b * B) + (c * C);
        float[] inverted = new float[m.length];
        inverted[0] = A / det;
        inverted[1] = B / det;
        inverted[2] = C / det;
        inverted[3] = ((c * h) - (b * i)) / det;
        inverted[4] = ((a * i) - (c * g)) / det;
        inverted[5] = ((b * g) - (a * h)) / det;
        inverted[6] = ((b * f) - (c * e)) / det;
        inverted[7] = ((c * d) - (a * f)) / det;
        inverted[8] = ((a * e) - (b * d)) / det;
        return inverted;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static float[] mul3x3(float[] lhs, float[] rhs) {
        float[] r = {
            (lhs[0] * rhs[0]) + (lhs[3] * rhs[1]) + (lhs[6] * rhs[2]),
            (lhs[1] * rhs[0]) + (lhs[4] * rhs[1]) + (lhs[7] * rhs[2]),
            (lhs[2] * rhs[0]) + (lhs[5] * rhs[1]) + (lhs[8] * rhs[2]),
            (lhs[0] * rhs[3]) + (lhs[3] * rhs[4]) + (lhs[6] * rhs[5]),
            (lhs[1] * rhs[3]) + (lhs[4] * rhs[4]) + (lhs[7] * rhs[5]),
            (lhs[2] * rhs[3]) + (lhs[5] * rhs[4]) + (lhs[8] * rhs[5]),
            (lhs[0] * rhs[6]) + (lhs[3] * rhs[7]) + (lhs[6] * rhs[8]),
            (lhs[1] * rhs[6]) + (lhs[4] * rhs[7]) + (lhs[7] * rhs[8]),
            (lhs[2] * rhs[6]) + (lhs[5] * rhs[7]) + (lhs[8] * rhs[8])
        };
        return r;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static float[] mul3x3Float3(float[] lhs, float[] rhs) {
        float r0 = rhs[0];
        float r1 = rhs[1];
        float r2 = rhs[2];
        rhs[0] = (lhs[0] * r0) + (lhs[3] * r1) + (lhs[6] * r2);
        rhs[1] = (lhs[1] * r0) + (lhs[4] * r1) + (lhs[7] * r2);
        rhs[2] = (lhs[2] * r0) + (lhs[5] * r1) + (lhs[8] * r2);
        return rhs;
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static float[] mul3x3Diag(float[] lhs, float[] rhs) {
        return new float[] {
            lhs[0] * rhs[0],
            lhs[1] * rhs[1],
            lhs[2] * rhs[2],
            lhs[0] * rhs[3],
            lhs[1] * rhs[4],
            lhs[2] * rhs[5],
            lhs[0] * rhs[6],
            lhs[1] * rhs[7],
            lhs[2] * rhs[8]
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static float[] xyYToXyz(float[] xyY) {
        return new float[] {xyY[0] / xyY[1], 1.0f, ((1.0f - xyY[0]) - xyY[1]) / xyY[1]};
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static float[] chromaticAdaptation(
            float[] matrix, float[] srcWhitePoint, float[] dstWhitePoint) {
        float[] srcLMS = mul3x3Float3(matrix, srcWhitePoint);
        float[] dstLMS = mul3x3Float3(matrix, dstWhitePoint);
        float[] LMS = {dstLMS[0] / srcLMS[0], dstLMS[1] / srcLMS[1], dstLMS[2] / srcLMS[2]};
        return mul3x3(inverse3x3(matrix), mul3x3Diag(LMS, matrix));
    }

    public static float[] cctToXyz(int cct) {
        float x;
        float y;
        if (cct < 1) {
            throw new IllegalArgumentException("Temperature must be greater than 0");
        }
        float icct = 1000.0f / cct;
        float icct2 = icct * icct;
        if (cct <= 4000.0f) {
            x =
                    (((0.8776956f * icct) + 0.17991f) - (0.2343589f * icct2))
                            - ((0.2661239f * icct2) * icct);
        } else {
            x =
                    (((0.2226347f * icct) + 0.24039f) + (2.1070378f * icct2))
                            - ((3.025847f * icct2) * icct);
        }
        float x2 = x * x;
        if (cct <= 2222.0f) {
            y = (((2.1855583f * x) - 0.20219684f) - (1.3481102f * x2)) - ((1.1063814f * x2) * x);
        } else if (cct <= 4000.0f) {
            y = (((2.09137f * x) - 0.16748866f) - (1.3741859f * x2)) - ((0.9549476f * x2) * x);
        } else {
            y = (((3.7511299f * x) - 0.37001482f) - (5.873387f * x2)) + (3.081758f * x2 * x);
        }
        return xyYToXyz(new float[] {x, y});
    }

    public static float[] chromaticAdaptation(
            Adaptation adaptation, float[] srcWhitePoint, float[] dstWhitePoint) {
        if ((srcWhitePoint.length != 2 && srcWhitePoint.length != 3)
                || (dstWhitePoint.length != 2 && dstWhitePoint.length != 3)) {
            throw new IllegalArgumentException("A white point array must have 2 or 3 floats");
        }
        float[] srcXyz =
                srcWhitePoint.length == 3
                        ? Arrays.copyOf(srcWhitePoint, 3)
                        : xyYToXyz(srcWhitePoint);
        float[] dstXyz =
                dstWhitePoint.length == 3
                        ? Arrays.copyOf(dstWhitePoint, 3)
                        : xyYToXyz(dstWhitePoint);
        if (compare(srcXyz, dstXyz)) {
            return new float[] {1.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 1.0f};
        }
        return chromaticAdaptation(adaptation.mTransform, srcXyz, dstXyz);
    }

    private static final class Xyz extends ColorSpace {
        private Xyz(String name, int id) {
            super(name, Model.XYZ, id);
        }

        @Override // android.graphics.ColorSpace
        public boolean isWideGamut() {
            return true;
        }

        @Override // android.graphics.ColorSpace
        public float getMinValue(int component) {
            return -2.0f;
        }

        @Override // android.graphics.ColorSpace
        public float getMaxValue(int component) {
            return 2.0f;
        }

        @Override // android.graphics.ColorSpace
        public float[] toXyz(float[] v) {
            v[0] = clamp(v[0]);
            v[1] = clamp(v[1]);
            v[2] = clamp(v[2]);
            return v;
        }

        @Override // android.graphics.ColorSpace
        public float[] fromXyz(float[] v) {
            v[0] = clamp(v[0]);
            v[1] = clamp(v[1]);
            v[2] = clamp(v[2]);
            return v;
        }

        private static float clamp(float x) {
            if (x < -2.0f) {
                return -2.0f;
            }
            if (x > 2.0f) {
                return 2.0f;
            }
            return x;
        }
    }

    private static final class Lab extends ColorSpace {
        private static final float A = 0.008856452f;
        private static final float B = 7.787037f;
        private static final float C = 0.13793103f;
        private static final float D = 0.20689656f;

        private Lab(String name, int id) {
            super(name, Model.LAB, id);
        }

        @Override // android.graphics.ColorSpace
        public boolean isWideGamut() {
            return true;
        }

        @Override // android.graphics.ColorSpace
        public float getMinValue(int component) {
            return component == 0 ? 0.0f : -128.0f;
        }

        @Override // android.graphics.ColorSpace
        public float getMaxValue(int component) {
            return component == 0 ? 100.0f : 128.0f;
        }

        @Override // android.graphics.ColorSpace
        public float[] toXyz(float[] v) {
            v[0] = ColorSpace.clamp(v[0], 0.0f, 100.0f);
            v[1] = ColorSpace.clamp(v[1], -128.0f, 128.0f);
            v[2] = ColorSpace.clamp(v[2], -128.0f, 128.0f);
            float fy = (v[0] + 16.0f) / 116.0f;
            float fx = (v[1] * 0.002f) + fy;
            float fz = fy - (v[2] * 0.005f);
            float X = fx > D ? fx * fx * fx : (fx - C) * 0.12841855f;
            float Y = fy > D ? fy * fy * fy : (fy - C) * 0.12841855f;
            float Z = fz > D ? fz * fz * fz : (fz - C) * 0.12841855f;
            v[0] = ColorSpace.ILLUMINANT_D50_XYZ[0] * X;
            v[1] = ColorSpace.ILLUMINANT_D50_XYZ[1] * Y;
            v[2] = ColorSpace.ILLUMINANT_D50_XYZ[2] * Z;
            return v;
        }

        @Override // android.graphics.ColorSpace
        public float[] fromXyz(float[] v) {
            float X = v[0] / ColorSpace.ILLUMINANT_D50_XYZ[0];
            float Y = v[1] / ColorSpace.ILLUMINANT_D50_XYZ[1];
            float Z = v[2] / ColorSpace.ILLUMINANT_D50_XYZ[2];
            float fx = X > A ? (float) Math.pow(X, 0.3333333333333333d) : (X * B) + C;
            float fy = Y > A ? (float) Math.pow(Y, 0.3333333333333333d) : (Y * B) + C;
            float fz = Z > A ? (float) Math.pow(Z, 0.3333333333333333d) : (B * Z) + C;
            float L = (116.0f * fy) - 16.0f;
            float a = (fx - fy) * 500.0f;
            float b = (fy - fz) * 200.0f;
            v[0] = ColorSpace.clamp(L, 0.0f, 100.0f);
            v[1] = ColorSpace.clamp(a, -128.0f, 128.0f);
            v[2] = ColorSpace.clamp(b, -128.0f, 128.0f);
            return v;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static float clamp(float x, float min, float max) {
        return x < min ? min : x > max ? max : x;
    }

    private static final class OkLab extends ColorSpace {
        private static final float[] M1TMP = {
            0.818933f,
            0.032984544f,
            0.0482003f,
            0.36186674f,
            0.9293119f,
            0.26436627f,
            -0.12885971f,
            0.03614564f,
            0.6338517f
        };
        private static final float[] M1 =
                ColorSpace.mul3x3(
                        M1TMP,
                        chromaticAdaptation(Adaptation.BRADFORD, ILLUMINANT_D50, ILLUMINANT_D65));
        private static final float[] M2 = {
            0.21045426f,
            1.9779985f,
            0.025904037f,
            0.7936178f,
            -2.4285922f,
            0.78277177f,
            -0.004072047f,
            0.4505937f,
            -0.80867577f
        };
        private static final float[] INVERSE_M1 = ColorSpace.inverse3x3(M1);
        private static final float[] INVERSE_M2 = ColorSpace.inverse3x3(M2);

        private OkLab(String name, int id) {
            super(name, Model.LAB, id);
        }

        @Override // android.graphics.ColorSpace
        public boolean isWideGamut() {
            return true;
        }

        @Override // android.graphics.ColorSpace
        public float getMinValue(int component) {
            return component == 0 ? 0.0f : -0.5f;
        }

        @Override // android.graphics.ColorSpace
        public float getMaxValue(int component) {
            return component == 0 ? 1.0f : 0.5f;
        }

        @Override // android.graphics.ColorSpace
        public float[] toXyz(float[] v) {
            v[0] = ColorSpace.clamp(v[0], 0.0f, 1.0f);
            v[1] = ColorSpace.clamp(v[1], -0.5f, 0.5f);
            v[2] = ColorSpace.clamp(v[2], -0.5f, 0.5f);
            ColorSpace.mul3x3Float3(INVERSE_M2, v);
            v[0] = v[0] * v[0] * v[0];
            v[1] = v[1] * v[1] * v[1];
            v[2] = v[2] * v[2] * v[2];
            ColorSpace.mul3x3Float3(INVERSE_M1, v);
            return v;
        }

        @Override // android.graphics.ColorSpace
        public float[] fromXyz(float[] v) {
            ColorSpace.mul3x3Float3(M1, v);
            v[0] = (float) Math.cbrt(v[0]);
            v[1] = (float) Math.cbrt(v[1]);
            v[2] = (float) Math.cbrt(v[2]);
            ColorSpace.mul3x3Float3(M2, v);
            return v;
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

            private static boolean isSpecialG(double g) {
                return g == TYPE_PQish || g == TYPE_HLGish;
            }

            public TransferParameters(double a, double b, double c, double d, double g) {
                this(
                        a,
                        b,
                        c,
                        d,
                        SContextConstants.ENVIRONMENT_VALUE_UNKNOWN,
                        SContextConstants.ENVIRONMENT_VALUE_UNKNOWN,
                        g);
            }

            public TransferParameters(
                    double a, double b, double c, double d, double e, double f, double g) {
                if (!Double.isNaN(a) && !Double.isNaN(b) && !Double.isNaN(c)) {
                    if (!Double.isNaN(d) && !Double.isNaN(e) && !Double.isNaN(f)) {
                        if (!Double.isNaN(g)) {
                            if (!isSpecialG(g)) {
                                if (d < SContextConstants.ENVIRONMENT_VALUE_UNKNOWN
                                        || d > Math.ulp(1.0f) + 1.0f) {
                                    throw new IllegalArgumentException(
                                            "Parameter d must be in the range [0..1], was " + d);
                                }
                                if (d == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN
                                        && (a == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN
                                                || g
                                                        == SContextConstants
                                                                .ENVIRONMENT_VALUE_UNKNOWN)) {
                                    throw new IllegalArgumentException(
                                            "Parameter a or g is zero, the transfer function is"
                                                    + " constant");
                                }
                                if (d >= 1.0d && c == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                                    throw new IllegalArgumentException(
                                            "Parameter c is zero, the transfer function is"
                                                    + " constant");
                                }
                                if ((a == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN
                                                || g == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN)
                                        && c == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                                    throw new IllegalArgumentException(
                                            "Parameter a or g is zero, and c is zero, the transfer"
                                                    + " function is constant");
                                }
                                if (c < SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                                    throw new IllegalArgumentException(
                                            "The transfer function must be increasing");
                                }
                                if (a < SContextConstants.ENVIRONMENT_VALUE_UNKNOWN
                                        || g < SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                                    throw new IllegalArgumentException(
                                            "The transfer function must be positive or increasing");
                                }
                            }
                            this.a = a;
                            this.b = b;
                            this.c = c;
                            this.d = d;
                            this.e = e;
                            this.f = f;
                            this.g = g;
                            return;
                        }
                    }
                }
                throw new IllegalArgumentException("Parameters cannot be NaN");
            }

            public boolean equals(Object o) {
                if (this == o) {
                    return true;
                }
                if (o == null || getClass() != o.getClass()) {
                    return false;
                }
                TransferParameters that = (TransferParameters) o;
                if (Double.compare(that.a, this.a) == 0
                        && Double.compare(that.b, this.b) == 0
                        && Double.compare(that.c, this.c) == 0
                        && Double.compare(that.d, this.d) == 0
                        && Double.compare(that.e, this.e) == 0
                        && Double.compare(that.f, this.f) == 0
                        && Double.compare(that.g, this.g) == 0) {
                    return true;
                }
                return false;
            }

            public int hashCode() {
                long temp = Double.doubleToLongBits(this.a);
                int result = (int) ((temp >>> 32) ^ temp);
                long temp2 = Double.doubleToLongBits(this.b);
                int result2 = (result * 31) + ((int) ((temp2 >>> 32) ^ temp2));
                long temp3 = Double.doubleToLongBits(this.c);
                int result3 = (result2 * 31) + ((int) ((temp3 >>> 32) ^ temp3));
                long temp4 = Double.doubleToLongBits(this.d);
                int result4 = (result3 * 31) + ((int) ((temp4 >>> 32) ^ temp4));
                long temp5 = Double.doubleToLongBits(this.e);
                int result5 = (result4 * 31) + ((int) ((temp5 >>> 32) ^ temp5));
                long temp6 = Double.doubleToLongBits(this.f);
                int result6 = (result5 * 31) + ((int) ((temp6 >>> 32) ^ temp6));
                long temp7 = Double.doubleToLongBits(this.g);
                return (result6 * 31) + ((int) ((temp7 >>> 32) ^ temp7));
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
            if (this.mNativePtr == 0) {
                throw new IllegalArgumentException(
                        "ColorSpace must use an ICC parametric transfer function! used " + this);
            }
            return this.mNativePtr;
        }

        static class Native {
            static native long nativeCreate(
                    float f,
                    float f2,
                    float f3,
                    float f4,
                    float f5,
                    float f6,
                    float f7,
                    float[] fArr);

            static native long nativeGetNativeFinalizer();

            Native() {}
        }

        private static DoubleUnaryOperator generateOETF(final TransferParameters function) {
            if (function.isHLGish()) {
                return new DoubleUnaryOperator() { // from class:
                    // android.graphics.ColorSpace$Rgb$$ExternalSyntheticLambda2
                    @Override // java.util.function.DoubleUnaryOperator
                    public final double applyAsDouble(double d) {
                        double transferHLGOETF;
                        transferHLGOETF =
                                ColorSpace.transferHLGOETF(
                                        ColorSpace.Rgb.TransferParameters.this, d);
                        return transferHLGOETF;
                    }
                };
            }
            if (function.isPQish()) {
                return new DoubleUnaryOperator() { // from class:
                    // android.graphics.ColorSpace$Rgb$$ExternalSyntheticLambda3
                    @Override // java.util.function.DoubleUnaryOperator
                    public final double applyAsDouble(double d) {
                        double transferST2048OETF;
                        transferST2048OETF =
                                ColorSpace.transferST2048OETF(
                                        ColorSpace.Rgb.TransferParameters.this, d);
                        return transferST2048OETF;
                    }
                };
            }
            if (function.e == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN
                    && function.f == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                return new DoubleUnaryOperator() { // from class:
                    // android.graphics.ColorSpace$Rgb$$ExternalSyntheticLambda4
                    @Override // java.util.function.DoubleUnaryOperator
                    public final double applyAsDouble(double d) {
                        double rcpResponse;
                        rcpResponse =
                                ColorSpace.rcpResponse(
                                        d,
                                        r0.a,
                                        r0.b,
                                        r0.c,
                                        r0.d,
                                        ColorSpace.Rgb.TransferParameters.this.g);
                        return rcpResponse;
                    }
                };
            }
            return new DoubleUnaryOperator() { // from class:
                // android.graphics.ColorSpace$Rgb$$ExternalSyntheticLambda5
                @Override // java.util.function.DoubleUnaryOperator
                public final double applyAsDouble(double d) {
                    double rcpResponse;
                    rcpResponse =
                            ColorSpace.rcpResponse(
                                    d,
                                    r0.a,
                                    r0.b,
                                    r0.c,
                                    r0.d,
                                    r0.e,
                                    r0.f,
                                    ColorSpace.Rgb.TransferParameters.this.g);
                    return rcpResponse;
                }
            };
        }

        private static DoubleUnaryOperator generateEOTF(final TransferParameters function) {
            if (function.isHLGish()) {
                return new DoubleUnaryOperator() { // from class:
                    // android.graphics.ColorSpace$Rgb$$ExternalSyntheticLambda7
                    @Override // java.util.function.DoubleUnaryOperator
                    public final double applyAsDouble(double d) {
                        double transferHLGEOTF;
                        transferHLGEOTF =
                                ColorSpace.transferHLGEOTF(
                                        ColorSpace.Rgb.TransferParameters.this, d);
                        return transferHLGEOTF;
                    }
                };
            }
            if (function.isPQish()) {
                return new DoubleUnaryOperator() { // from class:
                    // android.graphics.ColorSpace$Rgb$$ExternalSyntheticLambda8
                    @Override // java.util.function.DoubleUnaryOperator
                    public final double applyAsDouble(double d) {
                        double transferST2048OETF;
                        transferST2048OETF =
                                ColorSpace.transferST2048OETF(
                                        ColorSpace.Rgb.TransferParameters.this, d);
                        return transferST2048OETF;
                    }
                };
            }
            if (function.e == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN
                    && function.f == SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                return new DoubleUnaryOperator() { // from class:
                    // android.graphics.ColorSpace$Rgb$$ExternalSyntheticLambda9
                    @Override // java.util.function.DoubleUnaryOperator
                    public final double applyAsDouble(double d) {
                        double response;
                        response =
                                ColorSpace.response(
                                        d,
                                        r0.a,
                                        r0.b,
                                        r0.c,
                                        r0.d,
                                        ColorSpace.Rgb.TransferParameters.this.g);
                        return response;
                    }
                };
            }
            return new DoubleUnaryOperator() { // from class:
                // android.graphics.ColorSpace$Rgb$$ExternalSyntheticLambda10
                @Override // java.util.function.DoubleUnaryOperator
                public final double applyAsDouble(double d) {
                    double response;
                    response =
                            ColorSpace.response(
                                    d,
                                    r0.a,
                                    r0.b,
                                    r0.c,
                                    r0.d,
                                    r0.e,
                                    r0.f,
                                    ColorSpace.Rgb.TransferParameters.this.g);
                    return response;
                }
            };
        }

        public Rgb(String name, float[] toXYZ, DoubleUnaryOperator oetf, DoubleUnaryOperator eotf) {
            this(
                    name,
                    computePrimaries(toXYZ),
                    computeWhitePoint(toXYZ),
                    null,
                    oetf,
                    eotf,
                    0.0f,
                    1.0f,
                    null,
                    -1);
        }

        public Rgb(
                String name,
                float[] primaries,
                float[] whitePoint,
                DoubleUnaryOperator oetf,
                DoubleUnaryOperator eotf,
                float min,
                float max) {
            this(name, primaries, whitePoint, null, oetf, eotf, min, max, null, -1);
        }

        public Rgb(String name, float[] toXYZ, TransferParameters function) {
            this(
                    name,
                    isGray(toXYZ) ? ColorSpace.GRAY_PRIMARIES : computePrimaries(toXYZ),
                    computeWhitePoint(toXYZ),
                    isGray(toXYZ) ? toXYZ : null,
                    function,
                    -1);
        }

        public Rgb(
                String name, float[] primaries, float[] whitePoint, TransferParameters function) {
            this(name, primaries, whitePoint, null, function, -1);
        }

        private Rgb(
                String name,
                float[] primaries,
                float[] whitePoint,
                float[] transform,
                TransferParameters function,
                int id) {
            this(
                    name,
                    primaries,
                    whitePoint,
                    transform,
                    generateOETF(function),
                    generateEOTF(function),
                    0.0f,
                    1.0f,
                    function,
                    id);
        }

        public Rgb(String name, float[] toXYZ, double gamma) {
            this(name, computePrimaries(toXYZ), computeWhitePoint(toXYZ), gamma, 0.0f, 1.0f, -1);
        }

        public Rgb(String name, float[] primaries, float[] whitePoint, double gamma) {
            this(name, primaries, whitePoint, gamma, 0.0f, 1.0f, -1);
        }

        private Rgb(
                String name,
                float[] primaries,
                float[] whitePoint,
                final double gamma,
                float min,
                float max,
                int id) {
            this(
                    name,
                    primaries,
                    whitePoint,
                    null,
                    gamma == 1.0d
                            ? DoubleUnaryOperator.identity()
                            : new DoubleUnaryOperator() { // from class:
                                // android.graphics.ColorSpace$Rgb$$ExternalSyntheticLambda0
                                @Override // java.util.function.DoubleUnaryOperator
                                public final double applyAsDouble(double d) {
                                    return ColorSpace.Rgb.lambda$new$8(gamma, d);
                                }
                            },
                    gamma == 1.0d
                            ? DoubleUnaryOperator.identity()
                            : new DoubleUnaryOperator() { // from class:
                                // android.graphics.ColorSpace$Rgb$$ExternalSyntheticLambda1
                                @Override // java.util.function.DoubleUnaryOperator
                                public final double applyAsDouble(double d) {
                                    return ColorSpace.Rgb.lambda$new$9(gamma, d);
                                }
                            },
                    min,
                    max,
                    new TransferParameters(
                            1.0d,
                            SContextConstants.ENVIRONMENT_VALUE_UNKNOWN,
                            SContextConstants.ENVIRONMENT_VALUE_UNKNOWN,
                            SContextConstants.ENVIRONMENT_VALUE_UNKNOWN,
                            gamma),
                    id);
        }

        static /* synthetic */ double lambda$new$8(double gamma, double x) {
            double d = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
            if (x >= SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                d = x;
            }
            return Math.pow(d, 1.0d / gamma);
        }

        static /* synthetic */ double lambda$new$9(double gamma, double x) {
            double d = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
            if (x >= SContextConstants.ENVIRONMENT_VALUE_UNKNOWN) {
                d = x;
            }
            return Math.pow(d, gamma);
        }

        private Rgb(
                String name,
                float[] primaries,
                float[] whitePoint,
                float[] transform,
                DoubleUnaryOperator oetf,
                DoubleUnaryOperator eotf,
                float min,
                float max,
                TransferParameters transferParameters,
                int id) {
            super(name, Model.RGB, id);
            if (primaries == null || (primaries.length != 6 && primaries.length != 9)) {
                throw new IllegalArgumentException(
                        "The color space's primaries must be defined as an array of 6 floats in xyY"
                                + " or 9 floats in XYZ");
            }
            if (whitePoint == null || (whitePoint.length != 2 && whitePoint.length != 3)) {
                throw new IllegalArgumentException(
                        "The color space's white point must be defined as an array of 2 floats in"
                                + " xyY or 3 float in XYZ");
            }
            if (oetf == null || eotf == null) {
                throw new IllegalArgumentException(
                        "The transfer functions of a color space cannot be null");
            }
            if (min < max) {
                this.mWhitePoint = xyWhitePoint(whitePoint);
                this.mPrimaries = xyPrimaries(primaries);
                if (transform == null) {
                    this.mTransform = computeXYZMatrix(this.mPrimaries, this.mWhitePoint);
                } else {
                    if (transform.length != 9) {
                        throw new IllegalArgumentException(
                                "Transform must have 9 entries! Has " + transform.length);
                    }
                    this.mTransform = transform;
                }
                this.mInverseTransform = ColorSpace.inverse3x3(this.mTransform);
                this.mOetf = oetf;
                this.mEotf = eotf;
                this.mMin = min;
                this.mMax = max;
                DoubleUnaryOperator clamp = new DoubleUnaryOperator() { // from class:
                            // android.graphics.ColorSpace$Rgb$$ExternalSyntheticLambda6
                            @Override // java.util.function.DoubleUnaryOperator
                            public final double applyAsDouble(double d) {
                                double clamp2;
                                clamp2 = ColorSpace.Rgb.this.clamp(d);
                                return clamp2;
                            }
                        };
                this.mClampedOetf = oetf.andThen(clamp);
                this.mClampedEotf = clamp.andThen(eotf);
                this.mTransferParameters = transferParameters;
                this.mIsWideGamut = isWideGamut(this.mPrimaries, min, max);
                this.mIsSrgb = isSrgb(this.mPrimaries, this.mWhitePoint, oetf, eotf, min, max, id);
                if (this.mTransferParameters == null) {
                    this.mNativePtr = 0L;
                    return;
                } else {
                    if (this.mWhitePoint != null && this.mTransform != null) {
                        float[] nativeTransform =
                                ColorSpace.adaptToIlluminantD50(this.mWhitePoint, this.mTransform);
                        this.mNativePtr =
                                Native.nativeCreate(
                                        (float) this.mTransferParameters.a,
                                        (float) this.mTransferParameters.b,
                                        (float) this.mTransferParameters.c,
                                        (float) this.mTransferParameters.d,
                                        (float) this.mTransferParameters.e,
                                        (float) this.mTransferParameters.f,
                                        (float) this.mTransferParameters.g,
                                        nativeTransform);
                        NoImagePreloadHolder.sRegistry.registerNativeAllocation(
                                this, this.mNativePtr);
                        return;
                    }
                    throw new IllegalStateException(
                            "ColorSpace ("
                                    + this
                                    + ") cannot create native object! mWhitePoint: "
                                    + Arrays.toString(this.mWhitePoint)
                                    + " mTransform: "
                                    + Arrays.toString(this.mTransform));
                }
            }
            throw new IllegalArgumentException(
                    "Invalid range: min=" + min + ", max=" + max + "; min must be strictly < max");
        }

        private static class NoImagePreloadHolder {
            public static final NativeAllocationRegistry sRegistry =
                    new NativeAllocationRegistry(
                            Rgb.class.getClassLoader(), Native.nativeGetNativeFinalizer(), 0);

            private NoImagePreloadHolder() {}
        }

        private Rgb(Rgb colorSpace, float[] transform, float[] whitePoint) {
            this(
                    colorSpace.getName(),
                    colorSpace.mPrimaries,
                    whitePoint,
                    transform,
                    colorSpace.mOetf,
                    colorSpace.mEotf,
                    colorSpace.mMin,
                    colorSpace.mMax,
                    colorSpace.mTransferParameters,
                    -1);
        }

        public float[] getWhitePoint(float[] whitePoint) {
            whitePoint[0] = this.mWhitePoint[0];
            whitePoint[1] = this.mWhitePoint[1];
            return whitePoint;
        }

        public float[] getWhitePoint() {
            return Arrays.copyOf(this.mWhitePoint, this.mWhitePoint.length);
        }

        public float[] getPrimaries(float[] primaries) {
            System.arraycopy(this.mPrimaries, 0, primaries, 0, this.mPrimaries.length);
            return primaries;
        }

        public float[] getPrimaries() {
            return Arrays.copyOf(this.mPrimaries, this.mPrimaries.length);
        }

        public float[] getTransform(float[] transform) {
            System.arraycopy(this.mTransform, 0, transform, 0, this.mTransform.length);
            return transform;
        }

        public float[] getTransform() {
            return Arrays.copyOf(this.mTransform, this.mTransform.length);
        }

        public float[] getInverseTransform(float[] inverseTransform) {
            System.arraycopy(
                    this.mInverseTransform, 0, inverseTransform, 0, this.mInverseTransform.length);
            return inverseTransform;
        }

        public float[] getInverseTransform() {
            return Arrays.copyOf(this.mInverseTransform, this.mInverseTransform.length);
        }

        public DoubleUnaryOperator getOetf() {
            return this.mClampedOetf;
        }

        public DoubleUnaryOperator getEotf() {
            return this.mClampedEotf;
        }

        public TransferParameters getTransferParameters() {
            if (this.mTransferParameters != null
                    && !this.mTransferParameters.equals(ColorSpace.BT2020_PQ_TRANSFER_PARAMETERS)
                    && !this.mTransferParameters.equals(
                            ColorSpace.BT2020_HLG_TRANSFER_PARAMETERS)) {
                return this.mTransferParameters;
            }
            return null;
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
        public float getMinValue(int component) {
            return this.mMin;
        }

        @Override // android.graphics.ColorSpace
        public float getMaxValue(int component) {
            return this.mMax;
        }

        public float[] toLinear(float r, float g, float b) {
            return toLinear(new float[] {r, g, b});
        }

        public float[] toLinear(float[] v) {
            v[0] = (float) this.mClampedEotf.applyAsDouble(v[0]);
            v[1] = (float) this.mClampedEotf.applyAsDouble(v[1]);
            v[2] = (float) this.mClampedEotf.applyAsDouble(v[2]);
            return v;
        }

        public float[] fromLinear(float r, float g, float b) {
            return fromLinear(new float[] {r, g, b});
        }

        public float[] fromLinear(float[] v) {
            v[0] = (float) this.mClampedOetf.applyAsDouble(v[0]);
            v[1] = (float) this.mClampedOetf.applyAsDouble(v[1]);
            v[2] = (float) this.mClampedOetf.applyAsDouble(v[2]);
            return v;
        }

        @Override // android.graphics.ColorSpace
        public float[] toXyz(float[] v) {
            v[0] = (float) this.mClampedEotf.applyAsDouble(v[0]);
            v[1] = (float) this.mClampedEotf.applyAsDouble(v[1]);
            v[2] = (float) this.mClampedEotf.applyAsDouble(v[2]);
            return ColorSpace.mul3x3Float3(this.mTransform, v);
        }

        @Override // android.graphics.ColorSpace
        public float[] fromXyz(float[] v) {
            ColorSpace.mul3x3Float3(this.mInverseTransform, v);
            v[0] = (float) this.mClampedOetf.applyAsDouble(v[0]);
            v[1] = (float) this.mClampedOetf.applyAsDouble(v[1]);
            v[2] = (float) this.mClampedOetf.applyAsDouble(v[2]);
            return v;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public double clamp(double x) {
            float f;
            if (x < this.mMin) {
                f = this.mMin;
            } else {
                if (x <= this.mMax) {
                    return x;
                }
                f = this.mMax;
            }
            return f;
        }

        @Override // android.graphics.ColorSpace
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass() || !super.equals(o)) {
                return false;
            }
            Rgb rgb = (Rgb) o;
            if (Float.compare(rgb.mMin, this.mMin) != 0
                    || Float.compare(rgb.mMax, this.mMax) != 0
                    || !Arrays.equals(this.mWhitePoint, rgb.mWhitePoint)
                    || !Arrays.equals(this.mPrimaries, rgb.mPrimaries)) {
                return false;
            }
            if (this.mTransferParameters != null) {
                return this.mTransferParameters.equals(rgb.mTransferParameters);
            }
            if (rgb.mTransferParameters == null) {
                return true;
            }
            if (!this.mOetf.equals(rgb.mOetf)) {
                return false;
            }
            return this.mEotf.equals(rgb.mEotf);
        }

        @Override // android.graphics.ColorSpace
        public int hashCode() {
            int result =
                    (((((((((super.hashCode() * 31) + Arrays.hashCode(this.mWhitePoint)) * 31)
                                                                            + Arrays.hashCode(
                                                                                    this
                                                                                            .mPrimaries))
                                                                    * 31)
                                                            + (this.mMin != 0.0f
                                                                    ? Float.floatToIntBits(
                                                                            this.mMin)
                                                                    : 0))
                                                    * 31)
                                            + (this.mMax != 0.0f
                                                    ? Float.floatToIntBits(this.mMax)
                                                    : 0))
                                    * 31)
                            + (this.mTransferParameters != null
                                    ? this.mTransferParameters.hashCode()
                                    : 0);
            if (this.mTransferParameters == null) {
                return (((result * 31) + this.mOetf.hashCode()) * 31) + this.mEotf.hashCode();
            }
            return result;
        }

        private static boolean isSrgb(
                float[] primaries,
                float[] whitePoint,
                DoubleUnaryOperator OETF,
                DoubleUnaryOperator EOTF,
                float min,
                float max,
                int id) {
            if (id == 0) {
                return true;
            }
            if (!ColorSpace.compare(primaries, ColorSpace.SRGB_PRIMARIES)
                    || !ColorSpace.compare(whitePoint, ILLUMINANT_D65)
                    || min != 0.0f
                    || max != 1.0f) {
                return false;
            }
            Rgb srgb = (Rgb) get(Named.SRGB);
            for (double x = SContextConstants.ENVIRONMENT_VALUE_UNKNOWN;
                    x <= 1.0d;
                    x += 0.00392156862745098d) {
                if (!compare(x, OETF, srgb.mOetf) || !compare(x, EOTF, srgb.mEotf)) {
                    return false;
                }
            }
            return true;
        }

        private static boolean isGray(float[] toXYZ) {
            return toXYZ.length == 9
                    && toXYZ[1] == 0.0f
                    && toXYZ[2] == 0.0f
                    && toXYZ[3] == 0.0f
                    && toXYZ[5] == 0.0f
                    && toXYZ[6] == 0.0f
                    && toXYZ[7] == 0.0f;
        }

        private static boolean compare(double point, DoubleUnaryOperator a, DoubleUnaryOperator b) {
            double rA = a.applyAsDouble(point);
            double rB = b.applyAsDouble(point);
            return Math.abs(rA - rB) <= 0.001d;
        }

        private static boolean isWideGamut(float[] primaries, float min, float max) {
            return (area(primaries) / area(ColorSpace.NTSC_1953_PRIMARIES) > 0.9f
                            && contains(primaries, ColorSpace.SRGB_PRIMARIES))
                    || (min < 0.0f && max > 1.0f);
        }

        private static float area(float[] primaries) {
            float Rx = primaries[0];
            float Ry = primaries[1];
            float Gx = primaries[2];
            float Gy = primaries[3];
            float Bx = primaries[4];
            float By = primaries[5];
            float det =
                    (((((Rx * Gy) + (Ry * Bx)) + (Gx * By)) - (Gy * Bx)) - (Ry * Gx)) - (Rx * By);
            float r = 0.5f * det;
            return r < 0.0f ? -r : r;
        }

        private static float cross(float ax, float ay, float bx, float by) {
            return (ax * by) - (ay * bx);
        }

        private static boolean contains(float[] p1, float[] p2) {
            float[] p0 = {
                p1[0] - p2[0],
                p1[1] - p2[1],
                p1[2] - p2[2],
                p1[3] - p2[3],
                p1[4] - p2[4],
                p1[5] - p2[5]
            };
            return cross(p0[0], p0[1], p2[0] - p2[4], p2[1] - p2[5]) >= 0.0f
                    && cross(p2[0] - p2[2], p2[1] - p2[3], p0[0], p0[1]) >= 0.0f
                    && cross(p0[2], p0[3], p2[2] - p2[0], p2[3] - p2[1]) >= 0.0f
                    && cross(p2[2] - p2[4], p2[3] - p2[5], p0[2], p0[3]) >= 0.0f
                    && cross(p0[4], p0[5], p2[4] - p2[2], p2[5] - p2[3]) >= 0.0f
                    && cross(p2[4] - p2[0], p2[5] - p2[1], p0[4], p0[5]) >= 0.0f;
        }

        private static float[] computePrimaries(float[] toXYZ) {
            float[] r = ColorSpace.mul3x3Float3(toXYZ, new float[] {1.0f, 0.0f, 0.0f});
            float[] g = ColorSpace.mul3x3Float3(toXYZ, new float[] {0.0f, 1.0f, 0.0f});
            float[] b = ColorSpace.mul3x3Float3(toXYZ, new float[] {0.0f, 0.0f, 1.0f});
            float rSum = r[0] + r[1] + r[2];
            float gSum = g[0] + g[1] + g[2];
            float bSum = b[0] + b[1] + b[2];
            return new float[] {
                r[0] / rSum, r[1] / rSum, g[0] / gSum, g[1] / gSum, b[0] / bSum, b[1] / bSum
            };
        }

        private static float[] computeWhitePoint(float[] toXYZ) {
            float[] w = ColorSpace.mul3x3Float3(toXYZ, new float[] {1.0f, 1.0f, 1.0f});
            float sum = w[0] + w[1] + w[2];
            return new float[] {w[0] / sum, w[1] / sum};
        }

        private static float[] xyPrimaries(float[] primaries) {
            float[] xyPrimaries = new float[6];
            if (primaries.length == 9) {
                float sum = primaries[0] + primaries[1] + primaries[2];
                xyPrimaries[0] = primaries[0] / sum;
                xyPrimaries[1] = primaries[1] / sum;
                float sum2 = primaries[3] + primaries[4] + primaries[5];
                xyPrimaries[2] = primaries[3] / sum2;
                xyPrimaries[3] = primaries[4] / sum2;
                float sum3 = primaries[6] + primaries[7] + primaries[8];
                xyPrimaries[4] = primaries[6] / sum3;
                xyPrimaries[5] = primaries[7] / sum3;
            } else {
                System.arraycopy(primaries, 0, xyPrimaries, 0, 6);
            }
            return xyPrimaries;
        }

        private static float[] xyWhitePoint(float[] whitePoint) {
            float[] xyWhitePoint = new float[2];
            if (whitePoint.length == 3) {
                float sum = whitePoint[0] + whitePoint[1] + whitePoint[2];
                xyWhitePoint[0] = whitePoint[0] / sum;
                xyWhitePoint[1] = whitePoint[1] / sum;
            } else {
                System.arraycopy(whitePoint, 0, xyWhitePoint, 0, 2);
            }
            return xyWhitePoint;
        }

        private static float[] computeXYZMatrix(float[] primaries, float[] whitePoint) {
            float Rx = primaries[0];
            float Ry = primaries[1];
            float Gx = primaries[2];
            float Gy = primaries[3];
            float Bx = primaries[4];
            float By = primaries[5];
            float Wx = whitePoint[0];
            float Wy = whitePoint[1];
            float oneRxRy = (1.0f - Rx) / Ry;
            float oneGxGy = (1.0f - Gx) / Gy;
            float oneBxBy = (1.0f - Bx) / By;
            float oneWxWy = (1.0f - Wx) / Wy;
            float RxRy = Rx / Ry;
            float GxGy = Gx / Gy;
            float BxBy = Bx / By;
            float WxWy = Wx / Wy;
            float BY =
                    (((oneWxWy - oneRxRy) * (GxGy - RxRy)) - ((WxWy - RxRy) * (oneGxGy - oneRxRy)))
                            / (((oneBxBy - oneRxRy) * (GxGy - RxRy))
                                    - ((BxBy - RxRy) * (oneGxGy - oneRxRy)));
            float GY = ((WxWy - RxRy) - ((BxBy - RxRy) * BY)) / (GxGy - RxRy);
            float RY = (1.0f - GY) - BY;
            float RYRy = RY / Ry;
            float GYGy = GY / Gy;
            float BYBy = BY / By;
            return new float[] {
                RYRy * Rx,
                RY,
                ((1.0f - Rx) - Ry) * RYRy,
                GYGy * Gx,
                GY,
                ((1.0f - Gx) - Gy) * GYGy,
                BYBy * Bx,
                BY,
                ((1.0f - Bx) - By) * BYBy
            };
        }
    }

    public static class Connector {
        private final ColorSpace mDestination;
        private final RenderIntent mIntent;
        private final ColorSpace mSource;
        private final float[] mTransform;
        private final ColorSpace mTransformDestination;
        private final ColorSpace mTransformSource;

        Connector(ColorSpace source, ColorSpace destination, RenderIntent intent) {
            this(
                    source,
                    destination,
                    source.getModel() == Model.RGB
                            ? ColorSpace.adapt(source, ColorSpace.ILLUMINANT_D50_XYZ)
                            : source,
                    destination.getModel() == Model.RGB
                            ? ColorSpace.adapt(destination, ColorSpace.ILLUMINANT_D50_XYZ)
                            : destination,
                    intent,
                    computeTransform(source, destination, intent));
        }

        private Connector(
                ColorSpace source,
                ColorSpace destination,
                ColorSpace transformSource,
                ColorSpace transformDestination,
                RenderIntent intent,
                float[] transform) {
            this.mSource = source;
            this.mDestination = destination;
            this.mTransformSource = transformSource;
            this.mTransformDestination = transformDestination;
            this.mIntent = intent;
            this.mTransform = transform;
        }

        private static float[] computeTransform(
                ColorSpace source, ColorSpace destination, RenderIntent intent) {
            if (intent != RenderIntent.ABSOLUTE) {
                return null;
            }
            boolean srcRGB = source.getModel() == Model.RGB;
            boolean dstRGB = destination.getModel() == Model.RGB;
            if (srcRGB && dstRGB) {
                return null;
            }
            if (!srcRGB && !dstRGB) {
                return null;
            }
            Rgb rgb = (Rgb) (srcRGB ? source : destination);
            float[] srcXYZ =
                    srcRGB ? ColorSpace.xyYToXyz(rgb.mWhitePoint) : ColorSpace.ILLUMINANT_D50_XYZ;
            float[] dstXYZ =
                    dstRGB ? ColorSpace.xyYToXyz(rgb.mWhitePoint) : ColorSpace.ILLUMINANT_D50_XYZ;
            return new float[] {
                srcXYZ[0] / dstXYZ[0], srcXYZ[1] / dstXYZ[1], srcXYZ[2] / dstXYZ[2]
            };
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

        public float[] transform(float r, float g, float b) {
            return transform(new float[] {r, g, b});
        }

        public float[] transform(float[] v) {
            float[] xyz = this.mTransformSource.toXyz(v);
            if (this.mTransform != null) {
                xyz[0] = xyz[0] * this.mTransform[0];
                xyz[1] = xyz[1] * this.mTransform[1];
                xyz[2] = xyz[2] * this.mTransform[2];
            }
            return this.mTransformDestination.fromXyz(xyz);
        }

        private static class Rgb extends Connector {
            private final Rgb mDestination;
            private final Rgb mSource;
            private final float[] mTransform;

            Rgb(Rgb source, Rgb destination, RenderIntent intent) {
                super(destination, source, destination, intent, null);
                this.mSource = source;
                this.mDestination = destination;
                this.mTransform = computeTransform(source, destination, intent);
            }

            @Override // android.graphics.ColorSpace.Connector
            public float[] transform(float[] rgb) {
                rgb[0] = (float) this.mSource.mClampedEotf.applyAsDouble(rgb[0]);
                rgb[1] = (float) this.mSource.mClampedEotf.applyAsDouble(rgb[1]);
                rgb[2] = (float) this.mSource.mClampedEotf.applyAsDouble(rgb[2]);
                ColorSpace.mul3x3Float3(this.mTransform, rgb);
                rgb[0] = (float) this.mDestination.mClampedOetf.applyAsDouble(rgb[0]);
                rgb[1] = (float) this.mDestination.mClampedOetf.applyAsDouble(rgb[1]);
                rgb[2] = (float) this.mDestination.mClampedOetf.applyAsDouble(rgb[2]);
                return rgb;
            }

            private static float[] computeTransform(
                    Rgb source, Rgb destination, RenderIntent intent) {
                if (ColorSpace.compare(source.mWhitePoint, destination.mWhitePoint)) {
                    return ColorSpace.mul3x3(destination.mInverseTransform, source.mTransform);
                }
                float[] transform = source.mTransform;
                float[] inverseTransform = destination.mInverseTransform;
                float[] srcXYZ = ColorSpace.xyYToXyz(source.mWhitePoint);
                float[] dstXYZ = ColorSpace.xyYToXyz(destination.mWhitePoint);
                if (!ColorSpace.compare(source.mWhitePoint, ColorSpace.ILLUMINANT_D50)) {
                    float[] srcAdaptation =
                            ColorSpace.chromaticAdaptation(
                                    Adaptation.BRADFORD.mTransform,
                                    srcXYZ,
                                    Arrays.copyOf(ColorSpace.ILLUMINANT_D50_XYZ, 3));
                    transform = ColorSpace.mul3x3(srcAdaptation, source.mTransform);
                }
                float[] srcAdaptation2 = destination.mWhitePoint;
                if (!ColorSpace.compare(srcAdaptation2, ColorSpace.ILLUMINANT_D50)) {
                    float[] dstAdaptation =
                            ColorSpace.chromaticAdaptation(
                                    Adaptation.BRADFORD.mTransform,
                                    dstXYZ,
                                    Arrays.copyOf(ColorSpace.ILLUMINANT_D50_XYZ, 3));
                    inverseTransform =
                            ColorSpace.inverse3x3(
                                    ColorSpace.mul3x3(dstAdaptation, destination.mTransform));
                }
                if (intent == RenderIntent.ABSOLUTE) {
                    transform =
                            ColorSpace.mul3x3Diag(
                                    new float[] {
                                        srcXYZ[0] / dstXYZ[0],
                                        srcXYZ[1] / dstXYZ[1],
                                        srcXYZ[2] / dstXYZ[2]
                                    },
                                    transform);
                }
                return ColorSpace.mul3x3(inverseTransform, transform);
            }
        }

        static Connector identity(ColorSpace source) {
            return new Connector(
                    source,
                    source,
                    RenderIntent.RELATIVE) { // from class: android.graphics.ColorSpace.Connector.1
                @Override // android.graphics.ColorSpace.Connector
                public float[] transform(float[] v) {
                    return v;
                }
            };
        }
    }
}
