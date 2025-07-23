package android.renderscript;

import android.hardware.scontext.SContextConstants;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Deprecated
/* loaded from: classes3.dex */
public final class ScriptIntrinsicBLAS extends ScriptIntrinsic {
    public static final int CONJ_TRANSPOSE = 113;
    public static final int LEFT = 141;
    public static final int LOWER = 122;
    public static final int NON_UNIT = 131;
    public static final int NO_TRANSPOSE = 111;
    public static final int RIGHT = 142;
    private static final int RsBlas_bnnm = 1000;
    private static final int RsBlas_caxpy = 29;
    private static final int RsBlas_ccopy = 28;
    private static final int RsBlas_cdotc_sub = 6;
    private static final int RsBlas_cdotu_sub = 5;
    private static final int RsBlas_cgbmv = 64;
    private static final int RsBlas_cgemm = 125;
    private static final int RsBlas_cgemv = 63;
    private static final int RsBlas_cgerc = 99;
    private static final int RsBlas_cgeru = 98;
    private static final int RsBlas_chbmv = 96;
    private static final int RsBlas_chemm = 137;
    private static final int RsBlas_chemv = 95;
    private static final int RsBlas_cher = 100;
    private static final int RsBlas_cher2 = 102;
    private static final int RsBlas_cher2k = 139;
    private static final int RsBlas_cherk = 138;
    private static final int RsBlas_chpmv = 97;
    private static final int RsBlas_chpr = 101;
    private static final int RsBlas_chpr2 = 103;
    private static final int RsBlas_cscal = 43;
    private static final int RsBlas_csscal = 45;
    private static final int RsBlas_cswap = 27;
    private static final int RsBlas_csymm = 126;
    private static final int RsBlas_csyr2k = 128;
    private static final int RsBlas_csyrk = 127;
    private static final int RsBlas_ctbmv = 66;
    private static final int RsBlas_ctbsv = 69;
    private static final int RsBlas_ctpmv = 67;
    private static final int RsBlas_ctpsv = 70;
    private static final int RsBlas_ctrmm = 129;
    private static final int RsBlas_ctrmv = 65;
    private static final int RsBlas_ctrsm = 130;
    private static final int RsBlas_ctrsv = 68;
    private static final int RsBlas_dasum = 12;
    private static final int RsBlas_daxpy = 26;
    private static final int RsBlas_dcopy = 25;
    private static final int RsBlas_ddot = 4;
    private static final int RsBlas_dgbmv = 56;
    private static final int RsBlas_dgemm = 119;
    private static final int RsBlas_dgemv = 55;
    private static final int RsBlas_dger = 90;
    private static final int RsBlas_dnrm2 = 11;
    private static final int RsBlas_drot = 39;
    private static final int RsBlas_drotg = 37;
    private static final int RsBlas_drotm = 40;
    private static final int RsBlas_drotmg = 38;
    private static final int RsBlas_dsbmv = 88;
    private static final int RsBlas_dscal = 42;
    private static final int RsBlas_dsdot = 2;
    private static final int RsBlas_dspmv = 89;
    private static final int RsBlas_dspr = 92;
    private static final int RsBlas_dspr2 = 94;
    private static final int RsBlas_dswap = 24;
    private static final int RsBlas_dsymm = 120;
    private static final int RsBlas_dsymv = 87;
    private static final int RsBlas_dsyr = 91;
    private static final int RsBlas_dsyr2 = 93;
    private static final int RsBlas_dsyr2k = 122;
    private static final int RsBlas_dsyrk = 121;
    private static final int RsBlas_dtbmv = 58;
    private static final int RsBlas_dtbsv = 61;
    private static final int RsBlas_dtpmv = 59;
    private static final int RsBlas_dtpsv = 62;
    private static final int RsBlas_dtrmm = 123;
    private static final int RsBlas_dtrmv = 57;
    private static final int RsBlas_dtrsm = 124;
    private static final int RsBlas_dtrsv = 60;
    private static final int RsBlas_dzasum = 16;
    private static final int RsBlas_dznrm2 = 15;
    private static final int RsBlas_icamax = 19;
    private static final int RsBlas_idamax = 18;
    private static final int RsBlas_isamax = 17;
    private static final int RsBlas_izamax = 20;
    private static final int RsBlas_sasum = 10;
    private static final int RsBlas_saxpy = 23;
    private static final int RsBlas_scasum = 14;
    private static final int RsBlas_scnrm2 = 13;
    private static final int RsBlas_scopy = 22;
    private static final int RsBlas_sdot = 3;
    private static final int RsBlas_sdsdot = 1;
    private static final int RsBlas_sgbmv = 48;
    private static final int RsBlas_sgemm = 113;
    private static final int RsBlas_sgemv = 47;
    private static final int RsBlas_sger = 82;
    private static final int RsBlas_snrm2 = 9;
    private static final int RsBlas_srot = 35;
    private static final int RsBlas_srotg = 33;
    private static final int RsBlas_srotm = 36;
    private static final int RsBlas_srotmg = 34;
    private static final int RsBlas_ssbmv = 80;
    private static final int RsBlas_sscal = 41;
    private static final int RsBlas_sspmv = 81;
    private static final int RsBlas_sspr = 84;
    private static final int RsBlas_sspr2 = 86;
    private static final int RsBlas_sswap = 21;
    private static final int RsBlas_ssymm = 114;
    private static final int RsBlas_ssymv = 79;
    private static final int RsBlas_ssyr = 83;
    private static final int RsBlas_ssyr2 = 85;
    private static final int RsBlas_ssyr2k = 116;
    private static final int RsBlas_ssyrk = 115;
    private static final int RsBlas_stbmv = 50;
    private static final int RsBlas_stbsv = 53;
    private static final int RsBlas_stpmv = 51;
    private static final int RsBlas_stpsv = 54;
    private static final int RsBlas_strmm = 117;
    private static final int RsBlas_strmv = 49;
    private static final int RsBlas_strsm = 118;
    private static final int RsBlas_strsv = 52;
    private static final int RsBlas_zaxpy = 32;
    private static final int RsBlas_zcopy = 31;
    private static final int RsBlas_zdotc_sub = 8;
    private static final int RsBlas_zdotu_sub = 7;
    private static final int RsBlas_zdscal = 46;
    private static final int RsBlas_zgbmv = 72;
    private static final int RsBlas_zgemm = 131;
    private static final int RsBlas_zgemv = 71;
    private static final int RsBlas_zgerc = 108;
    private static final int RsBlas_zgeru = 107;
    private static final int RsBlas_zhbmv = 105;
    private static final int RsBlas_zhemm = 140;
    private static final int RsBlas_zhemv = 104;
    private static final int RsBlas_zher = 109;
    private static final int RsBlas_zher2 = 111;
    private static final int RsBlas_zher2k = 142;
    private static final int RsBlas_zherk = 141;
    private static final int RsBlas_zhpmv = 106;
    private static final int RsBlas_zhpr = 110;
    private static final int RsBlas_zhpr2 = 112;
    private static final int RsBlas_zscal = 44;
    private static final int RsBlas_zswap = 30;
    private static final int RsBlas_zsymm = 132;
    private static final int RsBlas_zsyr2k = 134;
    private static final int RsBlas_zsyrk = 133;
    private static final int RsBlas_ztbmv = 74;
    private static final int RsBlas_ztbsv = 77;
    private static final int RsBlas_ztpmv = 75;
    private static final int RsBlas_ztpsv = 78;
    private static final int RsBlas_ztrmm = 135;
    private static final int RsBlas_ztrmv = 73;
    private static final int RsBlas_ztrsm = 136;
    private static final int RsBlas_ztrsv = 76;
    public static final int TRANSPOSE = 112;
    public static final int UNIT = 132;
    public static final int UPPER = 121;
    private Allocation mLUT;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Diag {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Side {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Transpose {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Uplo {
    }

    private ScriptIntrinsicBLAS(long j, RenderScript renderScript) {
        super(j, renderScript);
    }

    public static ScriptIntrinsicBLAS create(RenderScript renderScript) {
        return new ScriptIntrinsicBLAS(renderScript.nScriptIntrinsicCreate(13, Element.U32(renderScript).getID(renderScript)), renderScript);
    }

    static void validateSide(int i) {
        if (i != 141 && i != 142) {
            throw new RSRuntimeException("Invalid side passed to BLAS");
        }
    }

    static void validateTranspose(int i) {
        if (i != 111 && i != 112 && i != 113) {
            throw new RSRuntimeException("Invalid transpose passed to BLAS");
        }
    }

    static void validateConjTranspose(int i) {
        if (i != 111 && i != 113) {
            throw new RSRuntimeException("Invalid transpose passed to BLAS");
        }
    }

    static void validateDiag(int i) {
        if (i != 131 && i != 132) {
            throw new RSRuntimeException("Invalid diag passed to BLAS");
        }
    }

    static void validateUplo(int i) {
        if (i != 121 && i != 122) {
            throw new RSRuntimeException("Invalid uplo passed to BLAS");
        }
    }

    static void validateGEMV(Element element, int i, Allocation allocation, Allocation allocation2, int i2, Allocation allocation3, int i3) {
        int i4;
        int i5;
        validateTranspose(i);
        int y = allocation.getType().getY();
        int x = allocation.getType().getX();
        if (!allocation.getType().getElement().isCompatible(element) || !allocation2.getType().getElement().isCompatible(element) || !allocation3.getType().getElement().isCompatible(element)) {
            throw new RSRuntimeException("Called BLAS with wrong Element type");
        }
        if (allocation2.getType().getY() > 1 || allocation3.getType().getY() > 1) {
            throw new RSRuntimeException("BLAS vectors must have Y dimension of 0 or 1");
        }
        if (i2 <= 0 || i3 <= 0) {
            throw new RSRuntimeException("Vector increments must be greater than 0");
        }
        if (i == 111) {
            i5 = ((x - 1) * i2) + 1;
            i4 = ((y - 1) * i3) + 1;
        } else {
            int i6 = ((y - 1) * i2) + 1;
            i4 = ((x - 1) * i3) + 1;
            i5 = i6;
        }
        if (allocation2.getType().getX() != i5 || allocation3.getType().getX() != i4) {
            throw new RSRuntimeException("Incorrect vector dimensions for GEMV");
        }
    }

    public void SGEMV(int i, float f, Allocation allocation, Allocation allocation2, int i2, float f2, Allocation allocation3, int i3) {
        validateGEMV(Element.F32(this.mRS), i, allocation, allocation2, i2, allocation3, i3);
        this.mRS.nScriptIntrinsicBLAS_Single(getID(this.mRS), 47, i, 0, 0, 0, 0, allocation.getType().getY(), allocation.getType().getX(), 0, f, allocation.getID(this.mRS), allocation2.getID(this.mRS), f2, allocation3.getID(this.mRS), i2, i3, 0, 0);
    }

    public void DGEMV(int i, double d, Allocation allocation, Allocation allocation2, int i2, double d2, Allocation allocation3, int i3) {
        validateGEMV(Element.F64(this.mRS), i, allocation, allocation2, i2, allocation3, i3);
        this.mRS.nScriptIntrinsicBLAS_Double(getID(this.mRS), 55, i, 0, 0, 0, 0, allocation.getType().getY(), allocation.getType().getX(), 0, d, allocation.getID(this.mRS), allocation2.getID(this.mRS), d2, allocation3.getID(this.mRS), i2, i3, 0, 0);
    }

    public void CGEMV(int i, Float2 float2, Allocation allocation, Allocation allocation2, int i2, Float2 float22, Allocation allocation3, int i3) {
        validateGEMV(Element.F32_2(this.mRS), i, allocation, allocation2, i2, allocation3, i3);
        this.mRS.nScriptIntrinsicBLAS_Complex(getID(this.mRS), 63, i, 0, 0, 0, 0, allocation.getType().getY(), allocation.getType().getX(), 0, float2.x, float2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), float22.x, float22.y, allocation3.getID(this.mRS), i2, i3, 0, 0);
    }

    public void ZGEMV(int i, Double2 double2, Allocation allocation, Allocation allocation2, int i2, Double2 double22, Allocation allocation3, int i3) {
        validateGEMV(Element.F64_2(this.mRS), i, allocation, allocation2, i2, allocation3, i3);
        this.mRS.nScriptIntrinsicBLAS_Z(getID(this.mRS), 71, i, 0, 0, 0, 0, allocation.getType().getY(), allocation.getType().getX(), 0, double2.x, double2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), double22.x, double22.y, allocation3.getID(this.mRS), i2, i3, 0, 0);
    }

    public void SGBMV(int i, int i2, int i3, float f, Allocation allocation, Allocation allocation2, int i4, float f2, Allocation allocation3, int i5) {
        validateGEMV(Element.F32(this.mRS), i, allocation, allocation2, i4, allocation3, i5);
        if (i2 < 0 || i3 < 0) {
            throw new RSRuntimeException("KL and KU must be greater than or equal to 0");
        }
        this.mRS.nScriptIntrinsicBLAS_Single(getID(this.mRS), 48, i, 0, 0, 0, 0, allocation.getType().getY(), allocation.getType().getX(), 0, f, allocation.getID(this.mRS), allocation2.getID(this.mRS), f2, allocation3.getID(this.mRS), i4, i5, i2, i3);
    }

    public void DGBMV(int i, int i2, int i3, double d, Allocation allocation, Allocation allocation2, int i4, double d2, Allocation allocation3, int i5) {
        validateGEMV(Element.F64(this.mRS), i, allocation, allocation2, i4, allocation3, i5);
        if (i2 < 0 || i3 < 0) {
            throw new RSRuntimeException("KL and KU must be greater than or equal to 0");
        }
        this.mRS.nScriptIntrinsicBLAS_Double(getID(this.mRS), 56, i, 0, 0, 0, 0, allocation.getType().getY(), allocation.getType().getX(), 0, d, allocation.getID(this.mRS), allocation2.getID(this.mRS), d2, allocation3.getID(this.mRS), i4, i5, i2, i3);
    }

    public void CGBMV(int i, int i2, int i3, Float2 float2, Allocation allocation, Allocation allocation2, int i4, Float2 float22, Allocation allocation3, int i5) {
        validateGEMV(Element.F32_2(this.mRS), i, allocation, allocation2, i4, allocation3, i5);
        if (i2 < 0 || i3 < 0) {
            throw new RSRuntimeException("KL and KU must be greater than or equal to 0");
        }
        this.mRS.nScriptIntrinsicBLAS_Complex(getID(this.mRS), 64, i, 0, 0, 0, 0, allocation.getType().getY(), allocation.getType().getX(), 0, float2.x, float2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), float22.x, float22.y, allocation3.getID(this.mRS), i4, i5, i2, i3);
    }

    public void ZGBMV(int i, int i2, int i3, Double2 double2, Allocation allocation, Allocation allocation2, int i4, Double2 double22, Allocation allocation3, int i5) {
        validateGEMV(Element.F64_2(this.mRS), i, allocation, allocation2, i4, allocation3, i5);
        if (i2 < 0 || i3 < 0) {
            throw new RSRuntimeException("KL and KU must be greater than or equal to 0");
        }
        this.mRS.nScriptIntrinsicBLAS_Z(getID(this.mRS), 72, i, 0, 0, 0, 0, allocation.getType().getY(), allocation.getType().getX(), 0, double2.x, double2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), double22.x, double22.y, allocation3.getID(this.mRS), i4, i5, i2, i3);
    }

    static void validateTRMV(Element element, int i, int i2, int i3, Allocation allocation, Allocation allocation2, int i4) {
        validateTranspose(i2);
        validateUplo(i);
        validateDiag(i3);
        int y = allocation.getType().getY();
        if (allocation.getType().getX() != y) {
            throw new RSRuntimeException("A must be a square matrix for TRMV");
        }
        if (!allocation.getType().getElement().isCompatible(element) || !allocation2.getType().getElement().isCompatible(element)) {
            throw new RSRuntimeException("Called BLAS with wrong Element type");
        }
        if (allocation2.getType().getY() > 1) {
            throw new RSRuntimeException("BLAS vectors must have Y dimension of 0 or 1");
        }
        if (i4 <= 0) {
            throw new RSRuntimeException("Vector increments must be greater than 0");
        }
        if (allocation2.getType().getX() != ((y - 1) * i4) + 1) {
            throw new RSRuntimeException("Incorrect vector dimensions for TRMV");
        }
    }

    static int validateTPMV(Element element, int i, int i2, int i3, Allocation allocation, Allocation allocation2, int i4) {
        validateTranspose(i2);
        validateUplo(i);
        validateDiag(i3);
        if (!allocation.getType().getElement().isCompatible(element) || !allocation2.getType().getElement().isCompatible(element)) {
            throw new RSRuntimeException("Called BLAS with wrong Element type");
        }
        if (allocation2.getType().getY() > 1) {
            throw new RSRuntimeException("BLAS vectors must have Y dimension of 0 or 1");
        }
        if (allocation.getType().getY() > 1) {
            throw new RSRuntimeException("Ap must have a Y dimension of 0 or 1");
        }
        int sqrt = (int) Math.sqrt(allocation.getType().getX() * 2.0d);
        if (allocation.getType().getX() != ((sqrt + 1) * sqrt) / 2) {
            throw new RSRuntimeException("Invalid dimension for Ap");
        }
        if (i4 <= 0) {
            throw new RSRuntimeException("Vector increments must be greater than 0");
        }
        if (allocation2.getType().getX() == ((sqrt - 1) * i4) + 1) {
            return sqrt;
        }
        throw new RSRuntimeException("Incorrect vector dimensions for TPMV");
    }

    public void STRMV(int i, int i2, int i3, Allocation allocation, Allocation allocation2, int i4) {
        validateTRMV(Element.F32(this.mRS), i, i2, i3, allocation, allocation2, i4);
        this.mRS.nScriptIntrinsicBLAS_Single(getID(this.mRS), 49, i2, 0, 0, i, i3, 0, allocation.getType().getY(), 0, 0.0f, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0f, 0L, i4, 0, 0, 0);
    }

    public void DTRMV(int i, int i2, int i3, Allocation allocation, Allocation allocation2, int i4) {
        validateTRMV(Element.F64(this.mRS), i, i2, i3, allocation, allocation2, i4);
        this.mRS.nScriptIntrinsicBLAS_Double(getID(this.mRS), 57, i2, 0, 0, i, i3, 0, allocation.getType().getY(), 0, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, allocation.getID(this.mRS), allocation2.getID(this.mRS), SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, 0L, i4, 0, 0, 0);
    }

    public void CTRMV(int i, int i2, int i3, Allocation allocation, Allocation allocation2, int i4) {
        validateTRMV(Element.F32_2(this.mRS), i, i2, i3, allocation, allocation2, i4);
        this.mRS.nScriptIntrinsicBLAS_Complex(getID(this.mRS), 65, i2, 0, 0, i, i3, 0, allocation.getType().getY(), 0, 0.0f, 0.0f, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0f, 0.0f, 0L, i4, 0, 0, 0);
    }

    public void ZTRMV(int i, int i2, int i3, Allocation allocation, Allocation allocation2, int i4) {
        validateTRMV(Element.F64_2(this.mRS), i, i2, i3, allocation, allocation2, i4);
        this.mRS.nScriptIntrinsicBLAS_Z(getID(this.mRS), 73, i2, 0, 0, i, i3, 0, allocation.getType().getY(), 0, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, allocation.getID(this.mRS), allocation2.getID(this.mRS), SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, 0L, i4, 0, 0, 0);
    }

    public void STBMV(int i, int i2, int i3, int i4, Allocation allocation, Allocation allocation2, int i5) {
        if (i4 < 0) {
            throw new RSRuntimeException("K must be greater than or equal to 0");
        }
        validateTRMV(Element.F32(this.mRS), i, i2, i3, allocation, allocation2, i5);
        this.mRS.nScriptIntrinsicBLAS_Single(getID(this.mRS), 50, i2, 0, 0, i, i3, 0, allocation.getType().getY(), i4, 0.0f, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0f, 0L, i5, 0, 0, 0);
    }

    public void DTBMV(int i, int i2, int i3, int i4, Allocation allocation, Allocation allocation2, int i5) {
        if (i4 < 0) {
            throw new RSRuntimeException("K must be greater than or equal to 0");
        }
        validateTRMV(Element.F64(this.mRS), i, i2, i3, allocation, allocation2, i5);
        this.mRS.nScriptIntrinsicBLAS_Double(getID(this.mRS), 58, i2, 0, 0, i, i3, 0, allocation.getType().getY(), i4, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, allocation.getID(this.mRS), allocation2.getID(this.mRS), SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, 0L, i5, 0, 0, 0);
    }

    public void CTBMV(int i, int i2, int i3, int i4, Allocation allocation, Allocation allocation2, int i5) {
        if (i4 < 0) {
            throw new RSRuntimeException("K must be greater than or equal to 0");
        }
        validateTRMV(Element.F32_2(this.mRS), i, i2, i3, allocation, allocation2, i5);
        this.mRS.nScriptIntrinsicBLAS_Complex(getID(this.mRS), 66, i2, 0, 0, i, i3, 0, allocation.getType().getY(), i4, 0.0f, 0.0f, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0f, 0.0f, 0L, i5, 0, 0, 0);
    }

    public void ZTBMV(int i, int i2, int i3, int i4, Allocation allocation, Allocation allocation2, int i5) {
        if (i4 < 0) {
            throw new RSRuntimeException("K must be greater than or equal to 0");
        }
        validateTRMV(Element.F64_2(this.mRS), i, i2, i3, allocation, allocation2, i5);
        this.mRS.nScriptIntrinsicBLAS_Z(getID(this.mRS), 74, i2, 0, 0, i, i3, 0, allocation.getType().getY(), i4, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, allocation.getID(this.mRS), allocation2.getID(this.mRS), SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, 0L, i5, 0, 0, 0);
    }

    public void STPMV(int i, int i2, int i3, Allocation allocation, Allocation allocation2, int i4) {
        this.mRS.nScriptIntrinsicBLAS_Single(getID(this.mRS), 51, i2, 0, 0, i, i3, 0, validateTPMV(Element.F32(this.mRS), i, i2, i3, allocation, allocation2, i4), 0, 0.0f, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0f, 0L, i4, 0, 0, 0);
    }

    public void DTPMV(int i, int i2, int i3, Allocation allocation, Allocation allocation2, int i4) {
        this.mRS.nScriptIntrinsicBLAS_Double(getID(this.mRS), 59, i2, 0, 0, i, i3, 0, validateTPMV(Element.F64(this.mRS), i, i2, i3, allocation, allocation2, i4), 0, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, allocation.getID(this.mRS), allocation2.getID(this.mRS), SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, 0L, i4, 0, 0, 0);
    }

    public void CTPMV(int i, int i2, int i3, Allocation allocation, Allocation allocation2, int i4) {
        this.mRS.nScriptIntrinsicBLAS_Complex(getID(this.mRS), 67, i2, 0, 0, i, i3, 0, validateTPMV(Element.F32_2(this.mRS), i, i2, i3, allocation, allocation2, i4), 0, 0.0f, 0.0f, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0f, 0.0f, 0L, i4, 0, 0, 0);
    }

    public void ZTPMV(int i, int i2, int i3, Allocation allocation, Allocation allocation2, int i4) {
        this.mRS.nScriptIntrinsicBLAS_Z(getID(this.mRS), 75, i2, 0, 0, i, i3, 0, validateTPMV(Element.F64_2(this.mRS), i, i2, i3, allocation, allocation2, i4), 0, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, allocation.getID(this.mRS), allocation2.getID(this.mRS), SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, 0L, i4, 0, 0, 0);
    }

    public void STRSV(int i, int i2, int i3, Allocation allocation, Allocation allocation2, int i4) {
        validateTRMV(Element.F32(this.mRS), i, i2, i3, allocation, allocation2, i4);
        this.mRS.nScriptIntrinsicBLAS_Single(getID(this.mRS), 52, i2, 0, 0, i, i3, 0, allocation.getType().getY(), 0, 0.0f, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0f, 0L, i4, 0, 0, 0);
    }

    public void DTRSV(int i, int i2, int i3, Allocation allocation, Allocation allocation2, int i4) {
        validateTRMV(Element.F64(this.mRS), i, i2, i3, allocation, allocation2, i4);
        this.mRS.nScriptIntrinsicBLAS_Double(getID(this.mRS), 60, i2, 0, 0, i, i3, 0, allocation.getType().getY(), 0, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, allocation.getID(this.mRS), allocation2.getID(this.mRS), SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, 0L, i4, 0, 0, 0);
    }

    public void CTRSV(int i, int i2, int i3, Allocation allocation, Allocation allocation2, int i4) {
        validateTRMV(Element.F32_2(this.mRS), i, i2, i3, allocation, allocation2, i4);
        this.mRS.nScriptIntrinsicBLAS_Complex(getID(this.mRS), 68, i2, 0, 0, i, i3, 0, allocation.getType().getY(), 0, 0.0f, 0.0f, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0f, 0.0f, 0L, i4, 0, 0, 0);
    }

    public void ZTRSV(int i, int i2, int i3, Allocation allocation, Allocation allocation2, int i4) {
        validateTRMV(Element.F64_2(this.mRS), i, i2, i3, allocation, allocation2, i4);
        this.mRS.nScriptIntrinsicBLAS_Z(getID(this.mRS), 76, i2, 0, 0, i, i3, 0, allocation.getType().getY(), 0, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, allocation.getID(this.mRS), allocation2.getID(this.mRS), SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, 0L, i4, 0, 0, 0);
    }

    public void STBSV(int i, int i2, int i3, int i4, Allocation allocation, Allocation allocation2, int i5) {
        validateTRMV(Element.F32(this.mRS), i, i2, i3, allocation, allocation2, i5);
        int y = allocation.getType().getY();
        if (i4 < 0) {
            throw new RSRuntimeException("Number of diagonals must be positive");
        }
        this.mRS.nScriptIntrinsicBLAS_Single(getID(this.mRS), 53, i2, 0, 0, i, i3, 0, y, i4, 0.0f, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0f, 0L, i5, 0, 0, 0);
    }

    public void DTBSV(int i, int i2, int i3, int i4, Allocation allocation, Allocation allocation2, int i5) {
        validateTRMV(Element.F64(this.mRS), i, i2, i3, allocation, allocation2, i5);
        int y = allocation.getType().getY();
        if (i4 < 0) {
            throw new RSRuntimeException("Number of diagonals must be positive");
        }
        this.mRS.nScriptIntrinsicBLAS_Double(getID(this.mRS), 61, i2, 0, 0, i, i3, 0, y, i4, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, allocation.getID(this.mRS), allocation2.getID(this.mRS), SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, 0L, i5, 0, 0, 0);
    }

    public void CTBSV(int i, int i2, int i3, int i4, Allocation allocation, Allocation allocation2, int i5) {
        validateTRMV(Element.F32_2(this.mRS), i, i2, i3, allocation, allocation2, i5);
        int y = allocation.getType().getY();
        if (i4 < 0) {
            throw new RSRuntimeException("Number of diagonals must be positive");
        }
        this.mRS.nScriptIntrinsicBLAS_Complex(getID(this.mRS), 69, i2, 0, 0, i, i3, 0, y, i4, 0.0f, 0.0f, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0f, 0.0f, 0L, i5, 0, 0, 0);
    }

    public void ZTBSV(int i, int i2, int i3, int i4, Allocation allocation, Allocation allocation2, int i5) {
        validateTRMV(Element.F64_2(this.mRS), i, i2, i3, allocation, allocation2, i5);
        int y = allocation.getType().getY();
        if (i4 < 0) {
            throw new RSRuntimeException("Number of diagonals must be positive");
        }
        this.mRS.nScriptIntrinsicBLAS_Z(getID(this.mRS), 77, i2, 0, 0, i, i3, 0, y, i4, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, allocation.getID(this.mRS), allocation2.getID(this.mRS), SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, 0L, i5, 0, 0, 0);
    }

    public void STPSV(int i, int i2, int i3, Allocation allocation, Allocation allocation2, int i4) {
        this.mRS.nScriptIntrinsicBLAS_Single(getID(this.mRS), 54, i2, 0, 0, i, i3, 0, validateTPMV(Element.F32(this.mRS), i, i2, i3, allocation, allocation2, i4), 0, 0.0f, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0f, 0L, i4, 0, 0, 0);
    }

    public void DTPSV(int i, int i2, int i3, Allocation allocation, Allocation allocation2, int i4) {
        this.mRS.nScriptIntrinsicBLAS_Double(getID(this.mRS), 62, i2, 0, 0, i, i3, 0, validateTPMV(Element.F64(this.mRS), i, i2, i3, allocation, allocation2, i4), 0, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, allocation.getID(this.mRS), allocation2.getID(this.mRS), SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, 0L, i4, 0, 0, 0);
    }

    public void CTPSV(int i, int i2, int i3, Allocation allocation, Allocation allocation2, int i4) {
        this.mRS.nScriptIntrinsicBLAS_Complex(getID(this.mRS), 70, i2, 0, 0, i, i3, 0, validateTPMV(Element.F32_2(this.mRS), i, i2, i3, allocation, allocation2, i4), 0, 0.0f, 0.0f, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0f, 0.0f, 0L, i4, 0, 0, 0);
    }

    public void ZTPSV(int i, int i2, int i3, Allocation allocation, Allocation allocation2, int i4) {
        this.mRS.nScriptIntrinsicBLAS_Z(getID(this.mRS), 78, i2, 0, 0, i, i3, 0, validateTPMV(Element.F64_2(this.mRS), i, i2, i3, allocation, allocation2, i4), 0, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, allocation.getID(this.mRS), allocation2.getID(this.mRS), SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, 0L, i4, 0, 0, 0);
    }

    static int validateSYMV(Element element, int i, Allocation allocation, Allocation allocation2, Allocation allocation3, int i2, int i3) {
        validateUplo(i);
        int y = allocation.getType().getY();
        if (allocation.getType().getX() != y) {
            throw new RSRuntimeException("A must be a square matrix for SYMV");
        }
        if (!allocation.getType().getElement().isCompatible(element) || !allocation2.getType().getElement().isCompatible(element) || !allocation3.getType().getElement().isCompatible(element)) {
            throw new RSRuntimeException("Called BLAS with wrong Element type");
        }
        if (allocation2.getType().getY() > 1 || allocation3.getType().getY() > 1) {
            throw new RSRuntimeException("BLAS vectors must have Y dimension of 0 or 1");
        }
        if (i2 <= 0 || i3 <= 0) {
            throw new RSRuntimeException("Vector increments must be greater than 0");
        }
        int i4 = y - 1;
        if (allocation2.getType().getX() != (i2 * i4) + 1) {
            throw new RSRuntimeException("Incorrect vector dimensions for SYMV");
        }
        if (allocation3.getType().getX() == (i4 * i3) + 1) {
            return y;
        }
        throw new RSRuntimeException("Incorrect vector dimensions for SYMV");
    }

    static int validateSPMV(Element element, int i, Allocation allocation, Allocation allocation2, int i2, Allocation allocation3, int i3) {
        validateUplo(i);
        if (!allocation.getType().getElement().isCompatible(element) || !allocation2.getType().getElement().isCompatible(element) || !allocation3.getType().getElement().isCompatible(element)) {
            throw new RSRuntimeException("Called BLAS with wrong Element type");
        }
        if (allocation2.getType().getY() > 1 || allocation3.getType().getY() > 1) {
            throw new RSRuntimeException("BLAS vectors must have Y dimension of 0 or 1");
        }
        if (allocation.getType().getY() > 1) {
            throw new RSRuntimeException("Ap must have a Y dimension of 0 or 1");
        }
        int sqrt = (int) Math.sqrt(allocation.getType().getX() * 2.0d);
        if (allocation.getType().getX() != ((sqrt + 1) * sqrt) / 2) {
            throw new RSRuntimeException("Invalid dimension for Ap");
        }
        if (i2 <= 0 || i3 <= 0) {
            throw new RSRuntimeException("Vector increments must be greater than 0");
        }
        int i4 = sqrt - 1;
        if (allocation2.getType().getX() != (i2 * i4) + 1) {
            throw new RSRuntimeException("Incorrect vector dimensions for SPMV");
        }
        if (allocation3.getType().getX() == (i4 * i3) + 1) {
            return sqrt;
        }
        throw new RSRuntimeException("Incorrect vector dimensions for SPMV");
    }

    static void validateGER(Element element, Allocation allocation, int i, Allocation allocation2, int i2, Allocation allocation3) {
        if (!allocation3.getType().getElement().isCompatible(element) || !allocation.getType().getElement().isCompatible(element) || !allocation2.getType().getElement().isCompatible(element)) {
            throw new RSRuntimeException("Called BLAS with wrong Element type");
        }
        if (allocation.getType().getY() > 1 || allocation2.getType().getY() > 1) {
            throw new RSRuntimeException("BLAS vectors must have Y dimension of 0 or 1");
        }
        int y = allocation3.getType().getY();
        int x = allocation3.getType().getX();
        if (x < 1 || y < 1) {
            throw new RSRuntimeException("M and N must be 1 or greater for GER");
        }
        if (i <= 0 || i2 <= 0) {
            throw new RSRuntimeException("Vector increments must be greater than 0");
        }
        if (allocation.getType().getX() != ((y - 1) * i) + 1) {
            throw new RSRuntimeException("Incorrect vector dimensions for GER");
        }
        if (allocation2.getType().getX() != ((x - 1) * i2) + 1) {
            throw new RSRuntimeException("Incorrect vector dimensions for GER");
        }
    }

    static int validateSYR(Element element, int i, Allocation allocation, int i2, Allocation allocation2) {
        validateUplo(i);
        if (!allocation2.getType().getElement().isCompatible(element) || !allocation.getType().getElement().isCompatible(element)) {
            throw new RSRuntimeException("Called BLAS with wrong Element type");
        }
        int x = allocation2.getType().getX();
        if (allocation.getType().getY() > 1) {
            throw new RSRuntimeException("BLAS vectors must have Y dimension of 0 or 1");
        }
        if (x != allocation2.getType().getY()) {
            throw new RSRuntimeException("A must be a symmetric matrix");
        }
        if (i2 <= 0) {
            throw new RSRuntimeException("Vector increments must be greater than 0");
        }
        if (allocation.getType().getX() == ((x - 1) * i2) + 1) {
            return x;
        }
        throw new RSRuntimeException("Incorrect vector dimensions for SYR");
    }

    static int validateSPR(Element element, int i, Allocation allocation, int i2, Allocation allocation2) {
        validateUplo(i);
        if (!allocation2.getType().getElement().isCompatible(element) || !allocation.getType().getElement().isCompatible(element)) {
            throw new RSRuntimeException("Called BLAS with wrong Element type");
        }
        if (allocation.getType().getY() > 1) {
            throw new RSRuntimeException("BLAS vectors must have Y dimension of 0 or 1");
        }
        if (allocation2.getType().getY() > 1) {
            throw new RSRuntimeException("Ap must have a Y dimension of 0 or 1");
        }
        int sqrt = (int) Math.sqrt(allocation2.getType().getX() * 2.0d);
        if (allocation2.getType().getX() != ((sqrt + 1) * sqrt) / 2) {
            throw new RSRuntimeException("Invalid dimension for Ap");
        }
        if (i2 <= 0) {
            throw new RSRuntimeException("Vector increments must be greater than 0");
        }
        if (allocation.getType().getX() == ((sqrt - 1) * i2) + 1) {
            return sqrt;
        }
        throw new RSRuntimeException("Incorrect vector dimensions for SPR");
    }

    static int validateSYR2(Element element, int i, Allocation allocation, int i2, Allocation allocation2, int i3, Allocation allocation3) {
        validateUplo(i);
        if (!allocation3.getType().getElement().isCompatible(element) || !allocation.getType().getElement().isCompatible(element) || !allocation2.getType().getElement().isCompatible(element)) {
            throw new RSRuntimeException("Called BLAS with wrong Element type");
        }
        if (allocation.getType().getY() > 1 || allocation2.getType().getY() > 1) {
            throw new RSRuntimeException("BLAS vectors must have Y dimension of 0 or 1");
        }
        int x = allocation3.getType().getX();
        if (x != allocation3.getType().getY()) {
            throw new RSRuntimeException("A must be a symmetric matrix");
        }
        if (i2 <= 0 || i3 <= 0) {
            throw new RSRuntimeException("Vector increments must be greater than 0");
        }
        int i4 = x - 1;
        int i5 = (i2 * i4) + 1;
        int i6 = (i4 * i3) + 1;
        if (allocation.getType().getX() == i5 && allocation2.getType().getX() == i6) {
            return x;
        }
        throw new RSRuntimeException("Incorrect vector dimensions for SYR");
    }

    static int validateSPR2(Element element, int i, Allocation allocation, int i2, Allocation allocation2, int i3, Allocation allocation3) {
        validateUplo(i);
        if (!allocation3.getType().getElement().isCompatible(element) || !allocation.getType().getElement().isCompatible(element) || !allocation2.getType().getElement().isCompatible(element)) {
            throw new RSRuntimeException("Called BLAS with wrong Element type");
        }
        if (allocation.getType().getY() > 1 || allocation2.getType().getY() > 1) {
            throw new RSRuntimeException("BLAS vectors must have Y dimension of 0 or 1");
        }
        if (allocation3.getType().getY() > 1) {
            throw new RSRuntimeException("Ap must have a Y dimension of 0 or 1");
        }
        int sqrt = (int) Math.sqrt(allocation3.getType().getX() * 2.0d);
        if (allocation3.getType().getX() != ((sqrt + 1) * sqrt) / 2) {
            throw new RSRuntimeException("Invalid dimension for Ap");
        }
        if (i2 <= 0 || i3 <= 0) {
            throw new RSRuntimeException("Vector increments must be greater than 0");
        }
        int i4 = sqrt - 1;
        int i5 = (i2 * i4) + 1;
        int i6 = (i4 * i3) + 1;
        if (allocation.getType().getX() == i5 && allocation2.getType().getX() == i6) {
            return sqrt;
        }
        throw new RSRuntimeException("Incorrect vector dimensions for SPR2");
    }

    public void SSYMV(int i, float f, Allocation allocation, Allocation allocation2, int i2, float f2, Allocation allocation3, int i3) {
        this.mRS.nScriptIntrinsicBLAS_Single(getID(this.mRS), 79, 0, 0, 0, i, 0, 0, validateSYMV(Element.F32(this.mRS), i, allocation, allocation2, allocation3, i2, i3), 0, f, allocation.getID(this.mRS), allocation2.getID(this.mRS), f2, allocation3.getID(this.mRS), i2, i3, 0, 0);
    }

    public void SSBMV(int i, int i2, float f, Allocation allocation, Allocation allocation2, int i3, float f2, Allocation allocation3, int i4) {
        if (i2 < 0) {
            throw new RSRuntimeException("K must be greater than or equal to 0");
        }
        this.mRS.nScriptIntrinsicBLAS_Single(getID(this.mRS), 80, 0, 0, 0, i, 0, 0, validateSYMV(Element.F32(this.mRS), i, allocation, allocation2, allocation3, i3, i4), i2, f, allocation.getID(this.mRS), allocation2.getID(this.mRS), f2, allocation3.getID(this.mRS), i3, i4, 0, 0);
    }

    public void SSPMV(int i, float f, Allocation allocation, Allocation allocation2, int i2, float f2, Allocation allocation3, int i3) {
        this.mRS.nScriptIntrinsicBLAS_Single(getID(this.mRS), 81, 0, 0, 0, i, 0, 0, validateSPMV(Element.F32(this.mRS), i, allocation, allocation2, i2, allocation3, i3), 0, f, allocation.getID(this.mRS), allocation2.getID(this.mRS), f2, allocation3.getID(this.mRS), i2, i3, 0, 0);
    }

    public void SGER(float f, Allocation allocation, int i, Allocation allocation2, int i2, Allocation allocation3) {
        int y = allocation3.getType().getY();
        int x = allocation3.getType().getX();
        validateGER(Element.F32(this.mRS), allocation, i, allocation2, i2, allocation3);
        this.mRS.nScriptIntrinsicBLAS_Single(getID(this.mRS), 82, 0, 0, 0, 0, 0, y, x, 0, f, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0f, allocation3.getID(this.mRS), i, i2, 0, 0);
    }

    public void SSYR(int i, float f, Allocation allocation, int i2, Allocation allocation2) {
        this.mRS.nScriptIntrinsicBLAS_Single(getID(this.mRS), 83, 0, 0, 0, i, 0, 0, validateSYR(Element.F32(this.mRS), i, allocation, i2, allocation2), 0, f, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0f, 0L, i2, 0, 0, 0);
    }

    public void SSPR(int i, float f, Allocation allocation, int i2, Allocation allocation2) {
        this.mRS.nScriptIntrinsicBLAS_Single(getID(this.mRS), 84, 0, 0, 0, i, 0, 0, validateSPR(Element.F32(this.mRS), i, allocation, i2, allocation2), 0, f, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0f, 0L, i2, 0, 0, 0);
    }

    public void SSYR2(int i, float f, Allocation allocation, int i2, Allocation allocation2, int i3, Allocation allocation3) {
        this.mRS.nScriptIntrinsicBLAS_Single(getID(this.mRS), 85, 0, 0, 0, i, 0, 0, validateSYR2(Element.F32(this.mRS), i, allocation, i2, allocation2, i3, allocation3), 0, f, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0f, allocation3.getID(this.mRS), i2, i3, 0, 0);
    }

    public void SSPR2(int i, float f, Allocation allocation, int i2, Allocation allocation2, int i3, Allocation allocation3) {
        this.mRS.nScriptIntrinsicBLAS_Single(getID(this.mRS), 86, 0, 0, 0, i, 0, 0, validateSPR2(Element.F32(this.mRS), i, allocation, i2, allocation2, i3, allocation3), 0, f, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0f, allocation3.getID(this.mRS), i2, i3, 0, 0);
    }

    public void DSYMV(int i, double d, Allocation allocation, Allocation allocation2, int i2, double d2, Allocation allocation3, int i3) {
        this.mRS.nScriptIntrinsicBLAS_Double(getID(this.mRS), 87, 0, 0, 0, i, 0, 0, validateSYMV(Element.F64(this.mRS), i, allocation, allocation2, allocation3, i2, i3), 0, d, allocation.getID(this.mRS), allocation2.getID(this.mRS), d2, allocation3.getID(this.mRS), i2, i3, 0, 0);
    }

    public void DSBMV(int i, int i2, double d, Allocation allocation, Allocation allocation2, int i3, double d2, Allocation allocation3, int i4) {
        if (i2 < 0) {
            throw new RSRuntimeException("K must be greater than or equal to 0");
        }
        this.mRS.nScriptIntrinsicBLAS_Double(getID(this.mRS), 88, 0, 0, 0, i, 0, 0, validateSYMV(Element.F64(this.mRS), i, allocation, allocation2, allocation3, i3, i4), i2, d, allocation.getID(this.mRS), allocation2.getID(this.mRS), d2, allocation3.getID(this.mRS), i3, i4, 0, 0);
    }

    public void DSPMV(int i, double d, Allocation allocation, Allocation allocation2, int i2, double d2, Allocation allocation3, int i3) {
        this.mRS.nScriptIntrinsicBLAS_Double(getID(this.mRS), 89, 0, 0, 0, i, 0, 0, validateSPMV(Element.F64(this.mRS), i, allocation, allocation2, i2, allocation3, i3), 0, d, allocation.getID(this.mRS), allocation2.getID(this.mRS), d2, allocation3.getID(this.mRS), i2, i3, 0, 0);
    }

    public void DGER(double d, Allocation allocation, int i, Allocation allocation2, int i2, Allocation allocation3) {
        int y = allocation3.getType().getY();
        int x = allocation3.getType().getX();
        validateGER(Element.F64(this.mRS), allocation, i, allocation2, i2, allocation3);
        this.mRS.nScriptIntrinsicBLAS_Double(getID(this.mRS), 90, 0, 0, 0, 0, 0, y, x, 0, d, allocation.getID(this.mRS), allocation2.getID(this.mRS), SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, allocation3.getID(this.mRS), i, i2, 0, 0);
    }

    public void DSYR(int i, double d, Allocation allocation, int i2, Allocation allocation2) {
        this.mRS.nScriptIntrinsicBLAS_Double(getID(this.mRS), 91, 0, 0, 0, i, 0, 0, validateSYR(Element.F64(this.mRS), i, allocation, i2, allocation2), 0, d, allocation.getID(this.mRS), allocation2.getID(this.mRS), SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, 0L, i2, 0, 0, 0);
    }

    public void DSPR(int i, double d, Allocation allocation, int i2, Allocation allocation2) {
        this.mRS.nScriptIntrinsicBLAS_Double(getID(this.mRS), 92, 0, 0, 0, i, 0, 0, validateSPR(Element.F64(this.mRS), i, allocation, i2, allocation2), 0, d, allocation.getID(this.mRS), allocation2.getID(this.mRS), SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, 0L, i2, 0, 0, 0);
    }

    public void DSYR2(int i, double d, Allocation allocation, int i2, Allocation allocation2, int i3, Allocation allocation3) {
        this.mRS.nScriptIntrinsicBLAS_Double(getID(this.mRS), 93, 0, 0, 0, i, 0, 0, validateSYR2(Element.F64(this.mRS), i, allocation, i2, allocation2, i3, allocation3), 0, d, allocation.getID(this.mRS), allocation2.getID(this.mRS), SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, allocation3.getID(this.mRS), i2, i3, 0, 0);
    }

    public void DSPR2(int i, double d, Allocation allocation, int i2, Allocation allocation2, int i3, Allocation allocation3) {
        this.mRS.nScriptIntrinsicBLAS_Double(getID(this.mRS), 94, 0, 0, 0, i, 0, 0, validateSPR2(Element.F64(this.mRS), i, allocation, i2, allocation2, i3, allocation3), 0, d, allocation.getID(this.mRS), allocation2.getID(this.mRS), SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, allocation3.getID(this.mRS), i2, i3, 0, 0);
    }

    static void validateGERU(Element element, Allocation allocation, int i, Allocation allocation2, int i2, Allocation allocation3) {
        if (!allocation3.getType().getElement().isCompatible(element) || !allocation.getType().getElement().isCompatible(element) || !allocation2.getType().getElement().isCompatible(element)) {
            throw new RSRuntimeException("Called BLAS with wrong Element type");
        }
        if (allocation.getType().getY() > 1 || allocation2.getType().getY() > 1) {
            throw new RSRuntimeException("BLAS vectors must have Y dimension of 0 or 1");
        }
        int y = allocation3.getType().getY();
        int x = allocation3.getType().getX();
        if (i <= 0 || i2 <= 0) {
            throw new RSRuntimeException("Vector increments must be greater than 0");
        }
        if (allocation.getType().getX() != ((y - 1) * i) + 1) {
            throw new RSRuntimeException("Incorrect vector dimensions for GERU");
        }
        if (allocation2.getType().getX() != ((x - 1) * i2) + 1) {
            throw new RSRuntimeException("Incorrect vector dimensions for GERU");
        }
    }

    public void CHEMV(int i, Float2 float2, Allocation allocation, Allocation allocation2, int i2, Float2 float22, Allocation allocation3, int i3) {
        this.mRS.nScriptIntrinsicBLAS_Complex(getID(this.mRS), 95, 0, 0, 0, i, 0, 0, validateSYR2(Element.F32_2(this.mRS), i, allocation2, i2, allocation3, i3, allocation), 0, float2.x, float2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), float22.x, float22.y, allocation3.getID(this.mRS), i2, i3, 0, 0);
    }

    public void CHBMV(int i, int i2, Float2 float2, Allocation allocation, Allocation allocation2, int i3, Float2 float22, Allocation allocation3, int i4) {
        int validateSYR2 = validateSYR2(Element.F32_2(this.mRS), i, allocation2, i3, allocation3, i4, allocation);
        if (i2 < 0) {
            throw new RSRuntimeException("K must be 0 or greater for HBMV");
        }
        this.mRS.nScriptIntrinsicBLAS_Complex(getID(this.mRS), 96, 0, 0, 0, i, 0, 0, validateSYR2, i2, float2.x, float2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), float22.x, float22.y, allocation3.getID(this.mRS), i3, i4, 0, 0);
    }

    public void CHPMV(int i, Float2 float2, Allocation allocation, Allocation allocation2, int i2, Float2 float22, Allocation allocation3, int i3) {
        this.mRS.nScriptIntrinsicBLAS_Complex(getID(this.mRS), 97, 0, 0, 0, i, 0, 0, validateSPR2(Element.F32_2(this.mRS), i, allocation2, i2, allocation3, i3, allocation), 0, float2.x, float2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), float22.x, float22.y, allocation3.getID(this.mRS), i2, i3, 0, 0);
    }

    public void CGERU(Float2 float2, Allocation allocation, int i, Allocation allocation2, int i2, Allocation allocation3) {
        validateGERU(Element.F32_2(this.mRS), allocation, i, allocation2, i2, allocation3);
        this.mRS.nScriptIntrinsicBLAS_Complex(getID(this.mRS), 98, 0, 0, 0, 0, 0, allocation3.getType().getY(), allocation3.getType().getX(), 0, float2.x, float2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0f, 0.0f, allocation3.getID(this.mRS), i, i2, 0, 0);
    }

    public void CGERC(Float2 float2, Allocation allocation, int i, Allocation allocation2, int i2, Allocation allocation3) {
        validateGERU(Element.F32_2(this.mRS), allocation, i, allocation2, i2, allocation3);
        this.mRS.nScriptIntrinsicBLAS_Complex(getID(this.mRS), 99, 0, 0, 0, 0, 0, allocation3.getType().getY(), allocation3.getType().getX(), 0, float2.x, float2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0f, 0.0f, allocation3.getID(this.mRS), i, i2, 0, 0);
    }

    public void CHER(int i, float f, Allocation allocation, int i2, Allocation allocation2) {
        this.mRS.nScriptIntrinsicBLAS_Complex(getID(this.mRS), 100, 0, 0, 0, i, 0, 0, validateSYR(Element.F32_2(this.mRS), i, allocation, i2, allocation2), 0, f, 0.0f, allocation.getID(this.mRS), 0L, 0.0f, 0.0f, allocation2.getID(this.mRS), i2, 0, 0, 0);
    }

    public void CHPR(int i, float f, Allocation allocation, int i2, Allocation allocation2) {
        this.mRS.nScriptIntrinsicBLAS_Complex(getID(this.mRS), 101, 0, 0, 0, i, 0, 0, validateSPR(Element.F32_2(this.mRS), i, allocation, i2, allocation2), 0, f, 0.0f, allocation.getID(this.mRS), 0L, 0.0f, 0.0f, allocation2.getID(this.mRS), i2, 0, 0, 0);
    }

    public void CHER2(int i, Float2 float2, Allocation allocation, int i2, Allocation allocation2, int i3, Allocation allocation3) {
        this.mRS.nScriptIntrinsicBLAS_Complex(getID(this.mRS), 102, 0, 0, 0, i, 0, 0, validateSYR2(Element.F32_2(this.mRS), i, allocation, i2, allocation2, i3, allocation3), 0, float2.x, float2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0f, 0.0f, allocation3.getID(this.mRS), i2, i3, 0, 0);
    }

    public void CHPR2(int i, Float2 float2, Allocation allocation, int i2, Allocation allocation2, int i3, Allocation allocation3) {
        this.mRS.nScriptIntrinsicBLAS_Complex(getID(this.mRS), 103, 0, 0, 0, i, 0, 0, validateSPR2(Element.F32_2(this.mRS), i, allocation, i2, allocation2, i3, allocation3), 0, float2.x, float2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0f, 0.0f, allocation3.getID(this.mRS), i2, i3, 0, 0);
    }

    public void ZHEMV(int i, Double2 double2, Allocation allocation, Allocation allocation2, int i2, Double2 double22, Allocation allocation3, int i3) {
        this.mRS.nScriptIntrinsicBLAS_Z(getID(this.mRS), 104, 0, 0, 0, i, 0, 0, validateSYR2(Element.F64_2(this.mRS), i, allocation2, i2, allocation3, i3, allocation), 0, double2.x, double2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), double22.x, double22.y, allocation3.getID(this.mRS), i2, i3, 0, 0);
    }

    public void ZHBMV(int i, int i2, Double2 double2, Allocation allocation, Allocation allocation2, int i3, Double2 double22, Allocation allocation3, int i4) {
        int validateSYR2 = validateSYR2(Element.F64_2(this.mRS), i, allocation2, i3, allocation3, i4, allocation);
        if (i2 < 0) {
            throw new RSRuntimeException("K must be 0 or greater for HBMV");
        }
        this.mRS.nScriptIntrinsicBLAS_Z(getID(this.mRS), 105, 0, 0, 0, i, 0, 0, validateSYR2, i2, double2.x, double2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), double22.x, double22.y, allocation3.getID(this.mRS), i3, i4, 0, 0);
    }

    public void ZHPMV(int i, Double2 double2, Allocation allocation, Allocation allocation2, int i2, Double2 double22, Allocation allocation3, int i3) {
        this.mRS.nScriptIntrinsicBLAS_Z(getID(this.mRS), 106, 0, 0, 0, i, 0, 0, validateSPR2(Element.F64_2(this.mRS), i, allocation2, i2, allocation3, i3, allocation), 0, double2.x, double2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), double22.x, double22.y, allocation3.getID(this.mRS), i2, i3, 0, 0);
    }

    public void ZGERU(Double2 double2, Allocation allocation, int i, Allocation allocation2, int i2, Allocation allocation3) {
        validateGERU(Element.F64_2(this.mRS), allocation, i, allocation2, i2, allocation3);
        this.mRS.nScriptIntrinsicBLAS_Z(getID(this.mRS), 107, 0, 0, 0, 0, 0, allocation3.getType().getY(), allocation3.getType().getX(), 0, double2.x, double2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, allocation3.getID(this.mRS), i, i2, 0, 0);
    }

    public void ZGERC(Double2 double2, Allocation allocation, int i, Allocation allocation2, int i2, Allocation allocation3) {
        validateGERU(Element.F64_2(this.mRS), allocation, i, allocation2, i2, allocation3);
        this.mRS.nScriptIntrinsicBLAS_Z(getID(this.mRS), 108, 0, 0, 0, 0, 0, allocation3.getType().getY(), allocation3.getType().getX(), 0, double2.x, double2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, allocation3.getID(this.mRS), i, i2, 0, 0);
    }

    public void ZHER(int i, double d, Allocation allocation, int i2, Allocation allocation2) {
        this.mRS.nScriptIntrinsicBLAS_Z(getID(this.mRS), 109, 0, 0, 0, i, 0, 0, validateSYR(Element.F64_2(this.mRS), i, allocation, i2, allocation2), 0, d, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, allocation.getID(this.mRS), 0L, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, allocation2.getID(this.mRS), i2, 0, 0, 0);
    }

    public void ZHPR(int i, double d, Allocation allocation, int i2, Allocation allocation2) {
        this.mRS.nScriptIntrinsicBLAS_Z(getID(this.mRS), 110, 0, 0, 0, i, 0, 0, validateSPR(Element.F64_2(this.mRS), i, allocation, i2, allocation2), 0, d, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, allocation.getID(this.mRS), 0L, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, allocation2.getID(this.mRS), i2, 0, 0, 0);
    }

    public void ZHER2(int i, Double2 double2, Allocation allocation, int i2, Allocation allocation2, int i3, Allocation allocation3) {
        this.mRS.nScriptIntrinsicBLAS_Z(getID(this.mRS), 111, 0, 0, 0, i, 0, 0, validateSYR2(Element.F64_2(this.mRS), i, allocation, i2, allocation2, i3, allocation3), 0, double2.x, double2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, allocation3.getID(this.mRS), i2, i3, 0, 0);
    }

    public void ZHPR2(int i, Double2 double2, Allocation allocation, int i2, Allocation allocation2, int i3, Allocation allocation3) {
        this.mRS.nScriptIntrinsicBLAS_Z(getID(this.mRS), 112, 0, 0, 0, i, 0, 0, validateSPR2(Element.F64_2(this.mRS), i, allocation, i2, allocation2, i3, allocation3), 0, double2.x, double2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, allocation3.getID(this.mRS), i2, i3, 0, 0);
    }

    static void validateL3(Element element, int i, int i2, int i3, Allocation allocation, Allocation allocation2, Allocation allocation3) {
        int i4;
        int i5;
        int i6;
        int i7;
        int i8;
        int i9;
        if ((allocation != null && !allocation.getType().getElement().isCompatible(element)) || ((allocation2 != null && !allocation2.getType().getElement().isCompatible(element)) || (allocation3 != null && !allocation3.getType().getElement().isCompatible(element)))) {
            throw new RSRuntimeException("Called BLAS with wrong Element type");
        }
        if (allocation3 == null) {
            throw new RSRuntimeException("Allocation C cannot be null");
        }
        int y = allocation3.getType().getY();
        int x = allocation3.getType().getX();
        int i10 = -1;
        if (i3 != 142) {
            if (allocation == null) {
                i4 = -1;
                i5 = -1;
            } else if (i == 112 || i == 113) {
                i5 = allocation.getType().getY();
                i4 = allocation.getType().getX();
            } else {
                i4 = allocation.getType().getY();
                i5 = allocation.getType().getX();
            }
            if (allocation2 == null) {
                i6 = i4;
                i7 = -1;
                i8 = -1;
            } else if (i2 == 112 || i2 == 113) {
                int y2 = allocation2.getType().getY();
                i6 = i4;
                i7 = allocation2.getType().getX();
                i8 = y2;
            } else {
                int y3 = allocation2.getType().getY();
                i8 = allocation2.getType().getX();
                i6 = i4;
                i7 = y3;
            }
            i10 = i5;
        } else {
            if ((allocation == null && allocation2 != null) || (allocation != null && allocation2 == null)) {
                throw new RSRuntimeException("Provided Matrix A without Matrix B, or vice versa");
            }
            if (allocation2 != null) {
                i7 = allocation.getType().getY();
                i9 = allocation.getType().getX();
            } else {
                i7 = -1;
                i9 = -1;
            }
            if (allocation != null) {
                i8 = i9;
                i6 = allocation2.getType().getY();
                i10 = allocation2.getType().getX();
            } else {
                i8 = i9;
                i6 = -1;
            }
        }
        if (allocation != null && allocation2 != null && allocation3 != null) {
            if (i10 != i7 || i6 != y || i8 != x) {
                throw new RSRuntimeException("Called BLAS with invalid dimensions");
            }
            return;
        }
        if (allocation == null || allocation3 == null) {
            if (allocation != null && allocation2 != null && i10 != i7) {
                throw new RSRuntimeException("Called BLAS with invalid dimensions");
            }
            return;
        }
        if (y != x) {
            throw new RSRuntimeException("Matrix C is not symmetric");
        }
        if (i6 != y) {
            throw new RSRuntimeException("Called BLAS with invalid dimensions");
        }
    }

    public void SGEMM(int i, int i2, float f, Allocation allocation, Allocation allocation2, float f2, Allocation allocation3) {
        int y;
        int x;
        int x2;
        validateTranspose(i);
        validateTranspose(i2);
        validateL3(Element.F32(this.mRS), i, i2, 0, allocation, allocation2, allocation3);
        if (i != 111) {
            y = allocation.getType().getX();
            x = allocation.getType().getY();
        } else {
            y = allocation.getType().getY();
            x = allocation.getType().getX();
        }
        int i3 = y;
        int i4 = x;
        if (i2 != 111) {
            x2 = allocation2.getType().getY();
        } else {
            x2 = allocation2.getType().getX();
        }
        this.mRS.nScriptIntrinsicBLAS_Single(getID(this.mRS), 113, i, i2, 0, 0, 0, i3, x2, i4, f, allocation.getID(this.mRS), allocation2.getID(this.mRS), f2, allocation3.getID(this.mRS), 0, 0, 0, 0);
    }

    public void DGEMM(int i, int i2, double d, Allocation allocation, Allocation allocation2, double d2, Allocation allocation3) {
        int y;
        int x;
        int x2;
        validateTranspose(i);
        validateTranspose(i2);
        validateL3(Element.F64(this.mRS), i, i2, 0, allocation, allocation2, allocation3);
        if (i != 111) {
            y = allocation.getType().getX();
            x = allocation.getType().getY();
        } else {
            y = allocation.getType().getY();
            x = allocation.getType().getX();
        }
        int i3 = y;
        int i4 = x;
        if (i2 != 111) {
            x2 = allocation2.getType().getY();
        } else {
            x2 = allocation2.getType().getX();
        }
        this.mRS.nScriptIntrinsicBLAS_Double(getID(this.mRS), 119, i, i2, 0, 0, 0, i3, x2, i4, d, allocation.getID(this.mRS), allocation2.getID(this.mRS), d2, allocation3.getID(this.mRS), 0, 0, 0, 0);
    }

    public void CGEMM(int i, int i2, Float2 float2, Allocation allocation, Allocation allocation2, Float2 float22, Allocation allocation3) {
        int y;
        int x;
        int x2;
        validateTranspose(i);
        validateTranspose(i2);
        validateL3(Element.F32_2(this.mRS), i, i2, 0, allocation, allocation2, allocation3);
        if (i != 111) {
            y = allocation.getType().getX();
            x = allocation.getType().getY();
        } else {
            y = allocation.getType().getY();
            x = allocation.getType().getX();
        }
        int i3 = y;
        int i4 = x;
        if (i2 != 111) {
            x2 = allocation2.getType().getY();
        } else {
            x2 = allocation2.getType().getX();
        }
        this.mRS.nScriptIntrinsicBLAS_Complex(getID(this.mRS), 125, i, i2, 0, 0, 0, i3, x2, i4, float2.x, float2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), float22.x, float22.y, allocation3.getID(this.mRS), 0, 0, 0, 0);
    }

    public void ZGEMM(int i, int i2, Double2 double2, Allocation allocation, Allocation allocation2, Double2 double22, Allocation allocation3) {
        int y;
        int x;
        int x2;
        validateTranspose(i);
        validateTranspose(i2);
        validateL3(Element.F64_2(this.mRS), i, i2, 0, allocation, allocation2, allocation3);
        if (i != 111) {
            y = allocation.getType().getX();
            x = allocation.getType().getY();
        } else {
            y = allocation.getType().getY();
            x = allocation.getType().getX();
        }
        int i3 = y;
        int i4 = x;
        if (i2 != 111) {
            x2 = allocation2.getType().getY();
        } else {
            x2 = allocation2.getType().getX();
        }
        this.mRS.nScriptIntrinsicBLAS_Z(getID(this.mRS), 131, i, i2, 0, 0, 0, i3, x2, i4, double2.x, double2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), double22.x, double22.y, allocation3.getID(this.mRS), 0, 0, 0, 0);
    }

    public void SSYMM(int i, int i2, float f, Allocation allocation, Allocation allocation2, float f2, Allocation allocation3) {
        validateSide(i);
        validateUplo(i2);
        if (allocation.getType().getX() != allocation.getType().getY()) {
            throw new RSRuntimeException("Matrix A is not symmetric");
        }
        validateL3(Element.F32(this.mRS), 0, 0, i, allocation, allocation2, allocation3);
        this.mRS.nScriptIntrinsicBLAS_Single(getID(this.mRS), 114, 0, 0, i, i2, 0, allocation3.getType().getY(), allocation3.getType().getX(), 0, f, allocation.getID(this.mRS), allocation2.getID(this.mRS), f2, allocation3.getID(this.mRS), 0, 0, 0, 0);
    }

    public void DSYMM(int i, int i2, double d, Allocation allocation, Allocation allocation2, double d2, Allocation allocation3) {
        validateSide(i);
        validateUplo(i2);
        if (allocation.getType().getX() != allocation.getType().getY()) {
            throw new RSRuntimeException("Matrix A is not symmetric");
        }
        validateL3(Element.F64(this.mRS), 0, 0, i, allocation, allocation2, allocation3);
        this.mRS.nScriptIntrinsicBLAS_Double(getID(this.mRS), 120, 0, 0, i, i2, 0, allocation3.getType().getY(), allocation3.getType().getX(), 0, d, allocation.getID(this.mRS), allocation2.getID(this.mRS), d2, allocation3.getID(this.mRS), 0, 0, 0, 0);
    }

    public void CSYMM(int i, int i2, Float2 float2, Allocation allocation, Allocation allocation2, Float2 float22, Allocation allocation3) {
        validateSide(i);
        validateUplo(i2);
        if (allocation.getType().getX() != allocation.getType().getY()) {
            throw new RSRuntimeException("Matrix A is not symmetric");
        }
        validateL3(Element.F32_2(this.mRS), 0, 0, i, allocation, allocation2, allocation3);
        this.mRS.nScriptIntrinsicBLAS_Complex(getID(this.mRS), 126, 0, 0, i, i2, 0, allocation3.getType().getY(), allocation3.getType().getX(), 0, float2.x, float2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), float22.x, float22.y, allocation3.getID(this.mRS), 0, 0, 0, 0);
    }

    public void ZSYMM(int i, int i2, Double2 double2, Allocation allocation, Allocation allocation2, Double2 double22, Allocation allocation3) {
        validateSide(i);
        validateUplo(i2);
        if (allocation.getType().getX() != allocation.getType().getY()) {
            throw new RSRuntimeException("Matrix A is not symmetric");
        }
        validateL3(Element.F64_2(this.mRS), 0, 0, i, allocation, allocation2, allocation3);
        this.mRS.nScriptIntrinsicBLAS_Z(getID(this.mRS), 132, 0, 0, i, i2, 0, allocation3.getType().getY(), allocation3.getType().getX(), 0, double2.x, double2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), double22.x, double22.y, allocation3.getID(this.mRS), 0, 0, 0, 0);
    }

    public void SSYRK(int i, int i2, float f, Allocation allocation, float f2, Allocation allocation2) {
        int x;
        validateTranspose(i2);
        validateUplo(i);
        validateL3(Element.F32(this.mRS), i2, 0, 0, allocation, null, allocation2);
        if (i2 != 111) {
            x = allocation.getType().getY();
        } else {
            x = allocation.getType().getX();
        }
        this.mRS.nScriptIntrinsicBLAS_Single(getID(this.mRS), 115, i2, 0, 0, i, 0, 0, allocation2.getType().getX(), x, f, allocation.getID(this.mRS), 0L, f2, allocation2.getID(this.mRS), 0, 0, 0, 0);
    }

    public void DSYRK(int i, int i2, double d, Allocation allocation, double d2, Allocation allocation2) {
        int x;
        validateTranspose(i2);
        validateUplo(i);
        validateL3(Element.F64(this.mRS), i2, 0, 0, allocation, null, allocation2);
        if (i2 != 111) {
            x = allocation.getType().getY();
        } else {
            x = allocation.getType().getX();
        }
        this.mRS.nScriptIntrinsicBLAS_Double(getID(this.mRS), 121, i2, 0, 0, i, 0, 0, allocation2.getType().getX(), x, d, allocation.getID(this.mRS), 0L, d2, allocation2.getID(this.mRS), 0, 0, 0, 0);
    }

    public void CSYRK(int i, int i2, Float2 float2, Allocation allocation, Float2 float22, Allocation allocation2) {
        int x;
        validateTranspose(i2);
        validateUplo(i);
        validateL3(Element.F32_2(this.mRS), i2, 0, 0, allocation, null, allocation2);
        if (i2 != 111) {
            x = allocation.getType().getY();
        } else {
            x = allocation.getType().getX();
        }
        this.mRS.nScriptIntrinsicBLAS_Complex(getID(this.mRS), 127, i2, 0, 0, i, 0, 0, allocation2.getType().getX(), x, float2.x, float2.y, allocation.getID(this.mRS), 0L, float22.x, float22.y, allocation2.getID(this.mRS), 0, 0, 0, 0);
    }

    public void ZSYRK(int i, int i2, Double2 double2, Allocation allocation, Double2 double22, Allocation allocation2) {
        int x;
        validateTranspose(i2);
        validateUplo(i);
        validateL3(Element.F64_2(this.mRS), i2, 0, 0, allocation, null, allocation2);
        if (i2 != 111) {
            x = allocation.getType().getY();
        } else {
            x = allocation.getType().getX();
        }
        this.mRS.nScriptIntrinsicBLAS_Z(getID(this.mRS), 133, i2, 0, 0, i, 0, 0, allocation2.getType().getX(), x, double2.x, double2.y, allocation.getID(this.mRS), 0L, double22.x, double22.y, allocation2.getID(this.mRS), 0, 0, 0, 0);
    }

    static void validateSYR2K(Element element, int i, Allocation allocation, Allocation allocation2, Allocation allocation3) {
        int y;
        validateTranspose(i);
        if (!allocation.getType().getElement().isCompatible(element) || !allocation2.getType().getElement().isCompatible(element) || !allocation3.getType().getElement().isCompatible(element)) {
            throw new RSRuntimeException("Called BLAS with wrong Element type");
        }
        if (i == 112) {
            y = allocation.getType().getX();
        } else {
            y = allocation.getType().getY();
        }
        if (allocation3.getType().getX() != y || allocation3.getType().getY() != y) {
            throw new RSRuntimeException("Invalid symmetric matrix in SYR2K");
        }
        if (allocation.getType().getX() != allocation2.getType().getX() || allocation.getType().getY() != allocation2.getType().getY()) {
            throw new RSRuntimeException("Invalid A and B in SYR2K");
        }
    }

    public void SSYR2K(int i, int i2, float f, Allocation allocation, Allocation allocation2, float f2, Allocation allocation3) {
        int x;
        validateUplo(i);
        validateSYR2K(Element.F32(this.mRS), i2, allocation, allocation2, allocation3);
        if (i2 != 111) {
            x = allocation.getType().getY();
        } else {
            x = allocation.getType().getX();
        }
        this.mRS.nScriptIntrinsicBLAS_Single(getID(this.mRS), 116, i2, 0, 0, i, 0, 0, allocation3.getType().getX(), x, f, allocation.getID(this.mRS), allocation2.getID(this.mRS), f2, allocation3.getID(this.mRS), 0, 0, 0, 0);
    }

    public void DSYR2K(int i, int i2, double d, Allocation allocation, Allocation allocation2, double d2, Allocation allocation3) {
        int x;
        validateUplo(i);
        validateSYR2K(Element.F64(this.mRS), i2, allocation, allocation2, allocation3);
        if (i2 != 111) {
            x = allocation.getType().getY();
        } else {
            x = allocation.getType().getX();
        }
        this.mRS.nScriptIntrinsicBLAS_Double(getID(this.mRS), 122, i2, 0, 0, i, 0, 0, allocation3.getType().getX(), x, d, allocation.getID(this.mRS), allocation2.getID(this.mRS), d2, allocation3.getID(this.mRS), 0, 0, 0, 0);
    }

    public void CSYR2K(int i, int i2, Float2 float2, Allocation allocation, Allocation allocation2, Float2 float22, Allocation allocation3) {
        int x;
        validateUplo(i);
        validateSYR2K(Element.F32_2(this.mRS), i2, allocation, allocation2, allocation3);
        if (i2 != 111) {
            x = allocation.getType().getY();
        } else {
            x = allocation.getType().getX();
        }
        this.mRS.nScriptIntrinsicBLAS_Complex(getID(this.mRS), 128, i2, 0, 0, i, 0, 0, allocation3.getType().getX(), x, float2.x, float2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), float22.x, float22.y, allocation3.getID(this.mRS), 0, 0, 0, 0);
    }

    public void ZSYR2K(int i, int i2, Double2 double2, Allocation allocation, Allocation allocation2, Double2 double22, Allocation allocation3) {
        int x;
        validateUplo(i);
        validateSYR2K(Element.F64_2(this.mRS), i2, allocation, allocation2, allocation3);
        if (i2 != 111) {
            x = allocation.getType().getY();
        } else {
            x = allocation.getType().getX();
        }
        this.mRS.nScriptIntrinsicBLAS_Z(getID(this.mRS), 134, i2, 0, 0, i, 0, 0, allocation3.getType().getX(), x, double2.x, double2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), double22.x, double22.y, allocation3.getID(this.mRS), 0, 0, 0, 0);
    }

    static void validateTRMM(Element element, int i, int i2, Allocation allocation, Allocation allocation2) {
        validateSide(i);
        validateTranspose(i2);
        if (!allocation.getType().getElement().isCompatible(element) || !allocation2.getType().getElement().isCompatible(element)) {
            throw new RSRuntimeException("Called BLAS with wrong Element type");
        }
        int y = allocation.getType().getY();
        int x = allocation.getType().getX();
        if (y != x) {
            throw new RSRuntimeException("Called TRMM with a non-symmetric matrix A");
        }
        int y2 = allocation2.getType().getY();
        int x2 = allocation2.getType().getX();
        if (i == 141) {
            if (x != y2) {
                throw new RSRuntimeException("Called TRMM with invalid matrices");
            }
        } else if (x2 != y) {
            throw new RSRuntimeException("Called TRMM with invalid matrices");
        }
    }

    public void STRMM(int i, int i2, int i3, int i4, float f, Allocation allocation, Allocation allocation2) {
        validateUplo(i2);
        validateDiag(i4);
        validateTRMM(Element.F32(this.mRS), i, i3, allocation, allocation2);
        this.mRS.nScriptIntrinsicBLAS_Single(getID(this.mRS), 117, i3, 0, i, i2, i4, allocation2.getType().getY(), allocation2.getType().getX(), 0, f, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0f, 0L, 0, 0, 0, 0);
    }

    public void DTRMM(int i, int i2, int i3, int i4, double d, Allocation allocation, Allocation allocation2) {
        validateUplo(i2);
        validateDiag(i4);
        validateTRMM(Element.F64(this.mRS), i, i3, allocation, allocation2);
        this.mRS.nScriptIntrinsicBLAS_Double(getID(this.mRS), 123, i3, 0, i, i2, i4, allocation2.getType().getY(), allocation2.getType().getX(), 0, d, allocation.getID(this.mRS), allocation2.getID(this.mRS), SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, 0L, 0, 0, 0, 0);
    }

    public void CTRMM(int i, int i2, int i3, int i4, Float2 float2, Allocation allocation, Allocation allocation2) {
        validateUplo(i2);
        validateDiag(i4);
        validateTRMM(Element.F32_2(this.mRS), i, i3, allocation, allocation2);
        this.mRS.nScriptIntrinsicBLAS_Complex(getID(this.mRS), 129, i3, 0, i, i2, i4, allocation2.getType().getY(), allocation2.getType().getX(), 0, float2.x, float2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0f, 0.0f, 0L, 0, 0, 0, 0);
    }

    public void ZTRMM(int i, int i2, int i3, int i4, Double2 double2, Allocation allocation, Allocation allocation2) {
        validateUplo(i2);
        validateDiag(i4);
        validateTRMM(Element.F64_2(this.mRS), i, i3, allocation, allocation2);
        this.mRS.nScriptIntrinsicBLAS_Z(getID(this.mRS), 135, i3, 0, i, i2, i4, allocation2.getType().getY(), allocation2.getType().getX(), 0, double2.x, double2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, 0L, 0, 0, 0, 0);
    }

    static void validateTRSM(Element element, int i, int i2, Allocation allocation, Allocation allocation2) {
        validateSide(i);
        validateTranspose(i2);
        if (!allocation.getType().getElement().isCompatible(element) || !allocation2.getType().getElement().isCompatible(element)) {
            throw new RSRuntimeException("Called BLAS with wrong Element type");
        }
        int x = allocation.getType().getX();
        if (x != allocation.getType().getY()) {
            throw new RSRuntimeException("Called TRSM with a non-symmetric matrix A");
        }
        int y = allocation2.getType().getY();
        int x2 = allocation2.getType().getX();
        if (i == 141) {
            if (x != y) {
                throw new RSRuntimeException("Called TRSM with invalid matrix dimensions");
            }
        } else if (x != x2) {
            throw new RSRuntimeException("Called TRSM with invalid matrix dimensions");
        }
    }

    public void STRSM(int i, int i2, int i3, int i4, float f, Allocation allocation, Allocation allocation2) {
        validateUplo(i2);
        validateDiag(i4);
        validateTRSM(Element.F32(this.mRS), i, i3, allocation, allocation2);
        this.mRS.nScriptIntrinsicBLAS_Single(getID(this.mRS), 118, i3, 0, i, i2, i4, allocation2.getType().getY(), allocation2.getType().getX(), 0, f, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0f, 0L, 0, 0, 0, 0);
    }

    public void DTRSM(int i, int i2, int i3, int i4, double d, Allocation allocation, Allocation allocation2) {
        validateUplo(i2);
        validateDiag(i4);
        validateTRSM(Element.F64(this.mRS), i, i3, allocation, allocation2);
        this.mRS.nScriptIntrinsicBLAS_Double(getID(this.mRS), 124, i3, 0, i, i2, i4, allocation2.getType().getY(), allocation2.getType().getX(), 0, d, allocation.getID(this.mRS), allocation2.getID(this.mRS), SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, 0L, 0, 0, 0, 0);
    }

    public void CTRSM(int i, int i2, int i3, int i4, Float2 float2, Allocation allocation, Allocation allocation2) {
        validateUplo(i2);
        validateDiag(i4);
        validateTRSM(Element.F32_2(this.mRS), i, i3, allocation, allocation2);
        this.mRS.nScriptIntrinsicBLAS_Complex(getID(this.mRS), 130, i3, 0, i, i2, i4, allocation2.getType().getY(), allocation2.getType().getX(), 0, float2.x, float2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), 0.0f, 0.0f, 0L, 0, 0, 0, 0);
    }

    public void ZTRSM(int i, int i2, int i3, int i4, Double2 double2, Allocation allocation, Allocation allocation2) {
        validateUplo(i2);
        validateDiag(i4);
        validateTRSM(Element.F64_2(this.mRS), i, i3, allocation, allocation2);
        this.mRS.nScriptIntrinsicBLAS_Z(getID(this.mRS), 136, i3, 0, i, i2, i4, allocation2.getType().getY(), allocation2.getType().getX(), 0, double2.x, double2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, 0L, 0, 0, 0, 0);
    }

    static void validateHEMM(Element element, int i, Allocation allocation, Allocation allocation2, Allocation allocation3) {
        validateSide(i);
        if (!allocation.getType().getElement().isCompatible(element) || !allocation2.getType().getElement().isCompatible(element) || !allocation3.getType().getElement().isCompatible(element)) {
            throw new RSRuntimeException("Called BLAS with wrong Element type");
        }
        int x = allocation.getType().getX();
        if (x != allocation.getType().getY()) {
            throw new RSRuntimeException("Called HEMM with non-square A");
        }
        if ((i == 141 && x != allocation2.getType().getY()) || (i == 142 && x != allocation2.getType().getX())) {
            throw new RSRuntimeException("Called HEMM with invalid B");
        }
        if (allocation2.getType().getX() != allocation3.getType().getX() || allocation2.getType().getY() != allocation3.getType().getY()) {
            throw new RSRuntimeException("Called HEMM with mismatched B and C");
        }
    }

    public void CHEMM(int i, int i2, Float2 float2, Allocation allocation, Allocation allocation2, Float2 float22, Allocation allocation3) {
        validateUplo(i2);
        validateHEMM(Element.F32_2(this.mRS), i, allocation, allocation2, allocation3);
        this.mRS.nScriptIntrinsicBLAS_Complex(getID(this.mRS), 137, 0, 0, i, i2, 0, allocation3.getType().getY(), allocation3.getType().getX(), 0, float2.x, float2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), float22.x, float22.y, allocation3.getID(this.mRS), 0, 0, 0, 0);
    }

    public void ZHEMM(int i, int i2, Double2 double2, Allocation allocation, Allocation allocation2, Double2 double22, Allocation allocation3) {
        validateUplo(i2);
        validateHEMM(Element.F64_2(this.mRS), i, allocation, allocation2, allocation3);
        this.mRS.nScriptIntrinsicBLAS_Z(getID(this.mRS), 140, 0, 0, i, i2, 0, allocation3.getType().getY(), allocation3.getType().getX(), 0, double2.x, double2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), double22.x, double22.y, allocation3.getID(this.mRS), 0, 0, 0, 0);
    }

    static void validateHERK(Element element, int i, Allocation allocation, Allocation allocation2) {
        if (!allocation.getType().getElement().isCompatible(element) || !allocation2.getType().getElement().isCompatible(element)) {
            throw new RSRuntimeException("Called BLAS with wrong Element type");
        }
        validateConjTranspose(i);
        int x = allocation2.getType().getX();
        if (x != allocation2.getType().getY()) {
            throw new RSRuntimeException("Called HERK with non-square C");
        }
        if (i == 111) {
            if (x != allocation.getType().getY()) {
                throw new RSRuntimeException("Called HERK with invalid A");
            }
        } else if (x != allocation.getType().getX()) {
            throw new RSRuntimeException("Called HERK with invalid A");
        }
    }

    public void CHERK(int i, int i2, float f, Allocation allocation, float f2, Allocation allocation2) {
        int x;
        validateUplo(i);
        validateHERK(Element.F32_2(this.mRS), i2, allocation, allocation2);
        if (i2 == 113) {
            x = allocation.getType().getY();
        } else {
            x = allocation.getType().getX();
        }
        this.mRS.nScriptIntrinsicBLAS_Complex(getID(this.mRS), 138, i2, 0, 0, i, 0, 0, allocation2.getType().getX(), x, f, 0.0f, allocation.getID(this.mRS), 0L, f2, 0.0f, allocation2.getID(this.mRS), 0, 0, 0, 0);
    }

    public void ZHERK(int i, int i2, double d, Allocation allocation, double d2, Allocation allocation2) {
        int x;
        validateUplo(i);
        validateHERK(Element.F64_2(this.mRS), i2, allocation, allocation2);
        if (i2 == 113) {
            x = allocation.getType().getY();
        } else {
            x = allocation.getType().getX();
        }
        this.mRS.nScriptIntrinsicBLAS_Z(getID(this.mRS), 141, i2, 0, 0, i, 0, 0, allocation2.getType().getX(), x, d, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, allocation.getID(this.mRS), 0L, d2, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, allocation2.getID(this.mRS), 0, 0, 0, 0);
    }

    static void validateHER2K(Element element, int i, Allocation allocation, Allocation allocation2, Allocation allocation3) {
        if (!allocation.getType().getElement().isCompatible(element) || !allocation2.getType().getElement().isCompatible(element) || !allocation3.getType().getElement().isCompatible(element)) {
            throw new RSRuntimeException("Called BLAS with wrong Element type");
        }
        validateConjTranspose(i);
        int x = allocation3.getType().getX();
        if (x != allocation3.getType().getY()) {
            throw new RSRuntimeException("Called HER2K with non-square C");
        }
        if (i == 111) {
            if (allocation.getType().getY() != x) {
                throw new RSRuntimeException("Called HER2K with invalid matrices");
            }
        } else if (allocation.getType().getX() != x) {
            throw new RSRuntimeException("Called HER2K with invalid matrices");
        }
        if (allocation.getType().getX() != allocation2.getType().getX() || allocation.getType().getY() != allocation2.getType().getY()) {
            throw new RSRuntimeException("Called HER2K with invalid A and B matrices");
        }
    }

    public void CHER2K(int i, int i2, Float2 float2, Allocation allocation, Allocation allocation2, float f, Allocation allocation3) {
        int y;
        validateUplo(i);
        validateHER2K(Element.F32_2(this.mRS), i2, allocation, allocation2, allocation3);
        if (i2 == 111) {
            y = allocation.getType().getX();
        } else {
            y = allocation.getType().getY();
        }
        this.mRS.nScriptIntrinsicBLAS_Complex(getID(this.mRS), 139, i2, 0, 0, i, 0, 0, allocation3.getType().getX(), y, float2.x, float2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), f, 0.0f, allocation3.getID(this.mRS), 0, 0, 0, 0);
    }

    public void ZHER2K(int i, int i2, Double2 double2, Allocation allocation, Allocation allocation2, double d, Allocation allocation3) {
        int y;
        validateUplo(i);
        validateHER2K(Element.F64_2(this.mRS), i2, allocation, allocation2, allocation3);
        if (i2 == 111) {
            y = allocation.getType().getX();
        } else {
            y = allocation.getType().getY();
        }
        this.mRS.nScriptIntrinsicBLAS_Z(getID(this.mRS), 142, i2, 0, 0, i, 0, 0, allocation3.getType().getX(), y, double2.x, double2.y, allocation.getID(this.mRS), allocation2.getID(this.mRS), d, SContextConstants.ENVIRONMENT_VALUE_UNKNOWN, allocation3.getID(this.mRS), 0, 0, 0, 0);
    }

    public void BNNM(Allocation allocation, int i, Allocation allocation2, int i2, Allocation allocation3, int i3, int i4) {
        validateL3(Element.U8(this.mRS), 111, 112, 0, allocation, allocation2, allocation3);
        if (i < 0 || i > 255) {
            throw new RSRuntimeException("Invalid a_offset passed to BNNM");
        }
        if (i2 < 0 || i2 > 255) {
            throw new RSRuntimeException("Invalid b_offset passed to BNNM");
        }
        this.mRS.nScriptIntrinsicBLAS_BNNM(getID(this.mRS), allocation.getType().getY(), allocation2.getType().getY(), allocation.getType().getX(), allocation.getID(this.mRS), i, allocation2.getID(this.mRS), i2, allocation3.getID(this.mRS), i3, i4);
    }
}
