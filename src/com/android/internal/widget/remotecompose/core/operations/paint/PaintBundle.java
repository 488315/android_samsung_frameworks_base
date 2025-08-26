package com.android.internal.widget.remotecompose.core.operations.paint;

import android.graphics.FontListParser;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.protolog.PerfettoProtoLogImpl;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.operations.Utils;
import com.android.internal.widget.remotecompose.core.serialize.MapSerializer;
import com.android.internal.widget.remotecompose.core.serialize.Serializable;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import com.samsung.android.knox.analytics.database.Contract;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/* loaded from: classes6.dex */
public class PaintBundle implements Serializable {
    public static final int ALPHA = 12;
    public static final int ANTI_ALIAS = 14;
    public static final int BLEND_MODE = 18;
    public static final int BLEND_MODE_CLEAR = 0;
    public static final int BLEND_MODE_COLOR = 27;
    public static final int BLEND_MODE_COLOR_BURN = 19;
    public static final int BLEND_MODE_COLOR_DODGE = 18;
    public static final int BLEND_MODE_DARKEN = 16;
    public static final int BLEND_MODE_DIFFERENCE = 22;
    public static final int BLEND_MODE_DST = 2;
    public static final int BLEND_MODE_DST_ATOP = 10;
    public static final int BLEND_MODE_DST_IN = 6;
    public static final int BLEND_MODE_DST_OUT = 8;
    public static final int BLEND_MODE_DST_OVER = 4;
    public static final int BLEND_MODE_EXCLUSION = 23;
    public static final int BLEND_MODE_HARD_LIGHT = 20;
    public static final int BLEND_MODE_HUE = 25;
    public static final int BLEND_MODE_LIGHTEN = 17;
    public static final int BLEND_MODE_LUMINOSITY = 28;
    public static final int BLEND_MODE_MODULATE = 13;
    public static final int BLEND_MODE_MULTIPLY = 24;
    public static final int BLEND_MODE_NULL = 29;
    public static final int BLEND_MODE_OVERLAY = 15;
    public static final int BLEND_MODE_PLUS = 12;
    public static final int BLEND_MODE_SATURATION = 26;
    public static final int BLEND_MODE_SCREEN = 14;
    public static final int BLEND_MODE_SOFT_LIGHT = 21;
    public static final int BLEND_MODE_SRC = 1;
    public static final int BLEND_MODE_SRC_ATOP = 9;
    public static final int BLEND_MODE_SRC_IN = 5;
    public static final int BLEND_MODE_SRC_OUT = 7;
    public static final int BLEND_MODE_SRC_OVER = 3;
    public static final int BLEND_MODE_XOR = 11;
    public static final int CLEAR_COLOR_FILTER = 21;
    public static final int COLOR = 4;
    public static final int COLOR_FILTER = 13;
    public static final int COLOR_FILTER_ID = 20;
    public static final int COLOR_ID = 19;
    public static final int FILTER_BITMAP = 17;
    public static final int FONT_BOLD = 1;
    public static final int FONT_BOLD_ITALIC = 3;
    public static final int FONT_ITALIC = 2;
    public static final int FONT_NORMAL = 0;
    public static final int FONT_TYPE_DEFAULT = 0;
    public static final int FONT_TYPE_MONOSPACE = 3;
    public static final int FONT_TYPE_SANS_SERIF = 1;
    public static final int FONT_TYPE_SERIF = 2;
    public static final int GRADIENT = 11;
    public static final int IMAGE_FILTER_QUALITY = 10;
    public static final int LINEAR_GRADIENT = 0;
    public static final int PORTER_MODE_ADD = 30;
    public static final int RADIAL_GRADIENT = 1;
    public static final int SHADER = 9;
    public static final int STROKE_CAP = 7;
    public static final int STROKE_JOIN = 15;
    public static final int STROKE_MITER = 6;
    public static final int STROKE_WIDTH = 5;
    public static final int STYLE = 8;
    public static final int STYLE_FILL = 0;
    public static final int STYLE_FILL_AND_STROKE = 2;
    public static final int STYLE_STROKE = 1;
    public static final int SWEEP_GRADIENT = 2;
    public static final int TEXT_SIZE = 1;
    public static final int TYPEFACE = 16;
    int[] mArray = new int[200];
    int[] mOutArray = null;
    int mPos = 0;
    private int mLastShaderSet = -1;
    private boolean mColorFilterSet = false;

    public void clear(long j) {
    }

    public void applyPaintChange(PaintContext paintContext, PaintChanges paintChanges) {
        if (this.mOutArray == null) {
            this.mOutArray = this.mArray;
        }
        int iCallSetGradient = 0;
        while (iCallSetGradient < this.mPos) {
            int[] iArr = this.mOutArray;
            int i = iCallSetGradient + 1;
            int i2 = iArr[iCallSetGradient];
            switch (65535 & i2) {
                case 1:
                    iCallSetGradient += 2;
                    paintChanges.setTextSize(Float.intBitsToFloat(iArr[i]));
                    continue;
                case 4:
                case 19:
                    iCallSetGradient += 2;
                    paintChanges.setColor(iArr[i]);
                    continue;
                case 5:
                    iCallSetGradient += 2;
                    paintChanges.setStrokeWidth(Float.intBitsToFloat(iArr[i]));
                    continue;
                case 6:
                    iCallSetGradient += 2;
                    paintChanges.setStrokeMiter(Float.intBitsToFloat(iArr[i]));
                    continue;
                case 7:
                    paintChanges.setStrokeCap(i2 >> 16);
                    break;
                case 8:
                    paintChanges.setStyle(i2 >> 16);
                    break;
                case 9:
                    iCallSetGradient += 2;
                    paintChanges.setShader(iArr[i]);
                    continue;
                case 10:
                    paintChanges.setImageFilterQuality(i2 >> 16);
                    break;
                case 11:
                    iCallSetGradient = callSetGradient(i2, iArr, i, paintChanges);
                    continue;
                case 12:
                    iCallSetGradient += 2;
                    paintChanges.setAlpha(Float.intBitsToFloat(iArr[i]));
                    continue;
                case 13:
                case 20:
                    iCallSetGradient += 2;
                    paintChanges.setColorFilter(iArr[i], i2 >> 16);
                    continue;
                case 15:
                    paintChanges.setStrokeJoin(i2 >> 16);
                    break;
                case 16:
                    iCallSetGradient += 2;
                    paintChanges.setTypeFace(iArr[i], (i2 >> 16) & 1023, (i2 >> 26) > 0);
                    continue;
                case 17:
                    paintChanges.setFilterBitmap((i2 >> 16) != 0);
                    break;
                case 18:
                    paintChanges.setBlendMode(i2 >> 16);
                    break;
                case 21:
                    paintChanges.clear(8192L);
                    break;
            }
            iCallSetGradient = i;
        }
    }

    private static String colorInt(int i) {
        return "0x" + ("000000000000" + Integer.toHexString(i)).substring(r2.length() - 8);
    }

    private static String colorInt(int[] iArr) {
        String str = NavigationBarInflaterView.SIZE_MOD_START;
        for (int i = 0; i < iArr.length; i++) {
            if (i > 0) {
                str = str + ", ";
            }
            str = str + colorInt(iArr[i]);
        }
        return str + NavigationBarInflaterView.SIZE_MOD_END;
    }

    private static String asFloatStr(int i) {
        float fIntBitsToFloat = Float.intBitsToFloat(i);
        if (Float.isNaN(fIntBitsToFloat)) {
            return NavigationBarInflaterView.SIZE_MOD_START + Utils.idFromNan(fIntBitsToFloat) + NavigationBarInflaterView.SIZE_MOD_END;
        }
        return Float.toString(fIntBitsToFloat);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder(ShaderAssembler.NEWLINE);
        int iCallPrintGradient = 0;
        while (iCallPrintGradient < this.mPos) {
            int[] iArr = this.mArray;
            int i = iCallPrintGradient + 1;
            int i2 = iArr[iCallPrintGradient];
            int i3 = 65535 & i2;
            if (i3 == 1) {
                StringBuilder sb2 = new StringBuilder("    TextSize(");
                iCallPrintGradient += 2;
                sb2.append(asFloatStr(this.mArray[i]));
                sb.append(sb2.toString());
            } else {
                switch (i3) {
                    case 4:
                        StringBuilder sb3 = new StringBuilder("    Color(");
                        iCallPrintGradient += 2;
                        sb3.append(colorInt(this.mArray[i]));
                        sb.append(sb3.toString());
                        continue;
                    case 5:
                        StringBuilder sb4 = new StringBuilder("    StrokeWidth(");
                        iCallPrintGradient += 2;
                        sb4.append(asFloatStr(this.mArray[i]));
                        sb.append(sb4.toString());
                        continue;
                    case 6:
                        StringBuilder sb5 = new StringBuilder("    StrokeMiter(");
                        iCallPrintGradient += 2;
                        sb5.append(asFloatStr(this.mArray[i]));
                        sb.append(sb5.toString());
                        continue;
                    case 7:
                        sb.append("    StrokeCap(" + (i2 >> 16));
                        break;
                    case 8:
                        sb.append("    Style(" + (i2 >> 16));
                        break;
                    case 9:
                        StringBuilder sb6 = new StringBuilder("    Shader(");
                        iCallPrintGradient += 2;
                        sb6.append(this.mArray[i]);
                        sb.append(sb6.toString());
                        continue;
                    case 10:
                        sb.append("    ImageFilterQuality(" + (i2 >> 16));
                        break;
                    case 11:
                        iCallPrintGradient = callPrintGradient(i2, iArr, i, sb);
                        continue;
                    case 12:
                        StringBuilder sb7 = new StringBuilder("    Alpha(");
                        iCallPrintGradient += 2;
                        sb7.append(asFloatStr(this.mArray[i]));
                        sb.append(sb7.toString());
                        continue;
                    case 13:
                        StringBuilder sb8 = new StringBuilder("    ColorFilter(color=");
                        iCallPrintGradient += 2;
                        sb8.append(colorInt(this.mArray[i]));
                        sb8.append(", mode=");
                        sb8.append(blendModeString(i2 >> 16));
                        sb.append(sb8.toString());
                        continue;
                    case 14:
                        sb.append("    AntiAlias(" + (i2 >> 16));
                        break;
                    case 15:
                        sb.append("    StrokeJoin(" + (i2 >> 16));
                        break;
                    case 16:
                        int i4 = (i2 >> 16) & 1023;
                        boolean z = (i2 >> 26) > 0;
                        iCallPrintGradient += 2;
                        sb.append("    TypeFace(" + iArr[i] + ", " + i4 + ", " + z);
                        continue;
                    case 17:
                        StringBuilder sb9 = new StringBuilder("    FilterBitmap(");
                        sb9.append((i2 >> 16) != 0);
                        sb.append(sb9.toString());
                        break;
                    case 18:
                        sb.append("    BlendMode(" + blendModeString(i2 >> 16));
                        break;
                    case 19:
                        StringBuilder sb10 = new StringBuilder("    ColorId([");
                        iCallPrintGradient += 2;
                        sb10.append(this.mArray[i]);
                        sb10.append(NavigationBarInflaterView.SIZE_MOD_END);
                        sb.append(sb10.toString());
                        continue;
                    case 20:
                        StringBuilder sb11 = new StringBuilder("    ColorFilterID(color=[");
                        iCallPrintGradient += 2;
                        sb11.append(this.mArray[i]);
                        sb11.append("], mode=");
                        sb11.append(blendModeString(i2 >> 16));
                        sb.append(sb11.toString());
                        continue;
                    case 21:
                        sb.append("    clearColorFilter");
                        break;
                }
                iCallPrintGradient = i;
            }
            sb.append("),\n");
        }
        return sb.toString();
    }

    private void registerFloat(int i, RemoteContext remoteContext, VariableSupport variableSupport) {
        float fIntBitsToFloat = Float.intBitsToFloat(i);
        if (Float.isNaN(fIntBitsToFloat)) {
            remoteContext.listensTo(Utils.idFromNan(fIntBitsToFloat), variableSupport);
        }
    }

    int callRegisterGradient(int i, int[] iArr, int i2, RemoteContext remoteContext, VariableSupport variableSupport) {
        int i3 = i >> 16;
        int i4 = i2 + 1;
        int i5 = iArr[i2];
        int i6 = i5 & 255;
        int i7 = (i5 >> 16) & 65535;
        int i8 = 0;
        if (i3 == 0) {
            if (i6 > 0) {
                int i9 = 0;
                while (i9 < i6) {
                    int i10 = i4 + 1;
                    int i11 = iArr[i4];
                    if (((1 << i9) & i7) != 0) {
                        remoteContext.listensTo(i11, variableSupport);
                    }
                    i9++;
                    i4 = i10;
                }
            }
            int i12 = i4 + 1;
            int i13 = iArr[i4];
            if (i13 > 0) {
                while (i8 < i13) {
                    registerFloat(iArr[i12], remoteContext, variableSupport);
                    i8++;
                    i12++;
                }
            }
            registerFloat(iArr[i12], remoteContext, variableSupport);
            registerFloat(iArr[i12 + 1], remoteContext, variableSupport);
            registerFloat(iArr[i12 + 2], remoteContext, variableSupport);
            int i14 = i12 + 4;
            registerFloat(iArr[i12 + 3], remoteContext, variableSupport);
            int i15 = i12 + 5;
            int i16 = iArr[i14];
            return i15;
        }
        if (i3 == 1) {
            if (i6 > 0) {
                int i17 = 0;
                while (i17 < i6) {
                    int i18 = i4 + 1;
                    int i19 = iArr[i4];
                    if (((1 << i17) & i7) != 0) {
                        remoteContext.listensTo(i19, variableSupport);
                    }
                    i17++;
                    i4 = i18;
                }
            }
            int i20 = i4 + 1;
            int i21 = iArr[i4];
            while (i8 < i21) {
                registerFloat(iArr[i20], remoteContext, variableSupport);
                i8++;
                i20++;
            }
            registerFloat(iArr[i20], remoteContext, variableSupport);
            registerFloat(iArr[i20 + 1], remoteContext, variableSupport);
            int i22 = i20 + 3;
            registerFloat(iArr[i20 + 2], remoteContext, variableSupport);
            int i23 = i20 + 4;
            int i24 = iArr[i22];
            return i23;
        }
        if (i3 == 2) {
            if (i6 > 0) {
                int i25 = 0;
                while (i25 < i6) {
                    int i26 = i4 + 1;
                    int i27 = iArr[i4];
                    if (((1 << i25) & i7) != 0) {
                        remoteContext.listensTo(i27, variableSupport);
                    }
                    i25++;
                    i4 = i26;
                }
            }
            int i28 = i4 + 1;
            int i29 = iArr[i4];
            while (i8 < i29) {
                registerFloat(iArr[i28], remoteContext, variableSupport);
                i8++;
                i28++;
            }
            int i30 = i28 + 1;
            registerFloat(iArr[i28], remoteContext, variableSupport);
            int i31 = i28 + 2;
            registerFloat(iArr[i30], remoteContext, variableSupport);
            return i31;
        }
        System.out.println("error ");
        return i4;
    }

    int callPrintGradient(int i, int[] iArr, int i2, StringBuilder sb) {
        int[] iArr2;
        int[] iArr3;
        int[] iArr4;
        int i3 = i >> 16;
        int i4 = i2 + 1;
        int i5 = iArr[i2];
        int i6 = 0;
        String[] strArr = null;
        if (i3 == 0) {
            sb.append("    LinearGradient(\n");
            if (i5 > 0) {
                iArr2 = new int[i5];
                int i7 = 0;
                while (i7 < i5) {
                    iArr2[i7] = iArr[i4];
                    i7++;
                    i4++;
                }
            } else {
                iArr2 = null;
            }
            int i8 = i4 + 1;
            int i9 = iArr[i4];
            if (i9 > 0) {
                strArr = new String[i9];
                while (i6 < i9) {
                    strArr[i6] = asFloatStr(iArr[i8]);
                    i6++;
                    i8++;
                }
            }
            sb.append("      colors = " + colorInt(iArr2) + ",\n");
            sb.append("      stops = " + Arrays.toString(strArr) + ",\n");
            sb.append("      start = ");
            sb.append(NavigationBarInflaterView.SIZE_MOD_START + asFloatStr(iArr[i8]));
            sb.append(", " + asFloatStr(iArr[i8 + 1]) + "],\n");
            sb.append("      end = ");
            sb.append(NavigationBarInflaterView.SIZE_MOD_START + asFloatStr(iArr[i8 + 2]));
            StringBuilder sb2 = new StringBuilder(", ");
            int i10 = i8 + 4;
            sb2.append(asFloatStr(iArr[i8 + 3]));
            sb2.append("],\n");
            sb.append(sb2.toString());
            int i11 = i8 + 5;
            sb.append("      tileMode = " + iArr[i10] + "\n    ");
            return i11;
        }
        if (i3 != 1) {
            if (i3 == 2) {
                sb.append("    SweepGradient(\n");
                if (i5 > 0) {
                    iArr4 = new int[i5];
                    int i12 = 0;
                    while (i12 < i5) {
                        iArr4[i12] = iArr[i4];
                        i12++;
                        i4++;
                    }
                } else {
                    iArr4 = null;
                }
                int i13 = i4 + 1;
                int i14 = iArr[i4];
                if (i14 > 0) {
                    strArr = new String[i14];
                    while (i6 < i14) {
                        strArr[i6] = asFloatStr(iArr[i13]);
                        i6++;
                        i13++;
                    }
                }
                sb.append("      colors = " + colorInt(iArr4) + ",\n");
                sb.append("      stops = " + Arrays.toString(strArr) + ",\n");
                sb.append("      center = ");
                StringBuilder sb3 = new StringBuilder(NavigationBarInflaterView.SIZE_MOD_START);
                int i15 = i13 + 1;
                sb3.append(asFloatStr(iArr[i13]));
                sb.append(sb3.toString());
                StringBuilder sb4 = new StringBuilder(", ");
                int i16 = i13 + 2;
                sb4.append(asFloatStr(iArr[i15]));
                sb4.append("],\n    ");
                sb.append(sb4.toString());
                return i16;
            }
            sb.append("GRADIENT_??????!!!!");
            return i4;
        }
        sb.append("    RadialGradient(\n");
        if (i5 > 0) {
            iArr3 = new int[i5];
            int i17 = 0;
            while (i17 < i5) {
                iArr3[i17] = iArr[i4];
                i17++;
                i4++;
            }
        } else {
            iArr3 = null;
        }
        int i18 = i4 + 1;
        int i19 = iArr[i4];
        if (i19 > 0) {
            strArr = new String[i19];
            while (i6 < i19) {
                strArr[i6] = asFloatStr(iArr[i18]);
                i6++;
                i18++;
            }
        }
        sb.append("      colors = " + colorInt(iArr3) + ",\n");
        sb.append("      stops = " + Arrays.toString(strArr) + ",\n");
        sb.append("      center = ");
        sb.append(NavigationBarInflaterView.SIZE_MOD_START + asFloatStr(iArr[i18]));
        sb.append(", " + asFloatStr(iArr[i18 + 1]) + "],\n");
        sb.append("      radius =");
        StringBuilder sb5 = new StringBuilder(" ");
        int i20 = i18 + 3;
        sb5.append(asFloatStr(iArr[i18 + 2]));
        sb5.append(",\n");
        sb.append(sb5.toString());
        int i21 = i18 + 4;
        sb.append("      tileMode = " + iArr[i20] + "\n    ");
        return i21;
    }

    int callSetGradient(int i, int[] iArr, int i2, PaintChanges paintChanges) {
        int[] iArr2;
        int i3 = i >> 16;
        int i4 = i2 + 1;
        int i5 = iArr[i2] & 255;
        int i6 = 0;
        float[] fArr = null;
        if (i5 > 0) {
            int[] iArr3 = new int[i5];
            int i7 = 0;
            while (i7 < i5) {
                iArr3[i7] = iArr[i4];
                i7++;
                i4++;
            }
            iArr2 = iArr3;
        } else {
            iArr2 = null;
        }
        int i8 = i4 + 1;
        int i9 = iArr[i4];
        if (i9 > 0) {
            fArr = new float[i9];
            while (i6 < iArr2.length) {
                fArr[i6] = Float.intBitsToFloat(iArr[i8]);
                i6++;
                i8++;
            }
        }
        float[] fArr2 = fArr;
        if (iArr2 != null) {
            if (i3 == 0) {
                float fIntBitsToFloat = Float.intBitsToFloat(iArr[i8]);
                float fIntBitsToFloat2 = Float.intBitsToFloat(iArr[i8 + 1]);
                float fIntBitsToFloat3 = Float.intBitsToFloat(iArr[i8 + 2]);
                int i10 = i8 + 4;
                float fIntBitsToFloat4 = Float.intBitsToFloat(iArr[i8 + 3]);
                int i11 = i8 + 5;
                paintChanges.setLinearGradient(iArr2, fArr2, fIntBitsToFloat, fIntBitsToFloat2, fIntBitsToFloat3, fIntBitsToFloat4, iArr[i10]);
                return i11;
            }
            if (i3 == 1) {
                float fIntBitsToFloat5 = Float.intBitsToFloat(iArr[i8]);
                float fIntBitsToFloat6 = Float.intBitsToFloat(iArr[i8 + 1]);
                int i12 = i8 + 3;
                float fIntBitsToFloat7 = Float.intBitsToFloat(iArr[i8 + 2]);
                int i13 = i8 + 4;
                paintChanges.setRadialGradient(iArr2, fArr2, fIntBitsToFloat5, fIntBitsToFloat6, fIntBitsToFloat7, iArr[i12]);
                return i13;
            }
            if (i3 == 2) {
                int i14 = i8 + 1;
                float fIntBitsToFloat8 = Float.intBitsToFloat(iArr[i8]);
                int i15 = i8 + 2;
                paintChanges.setSweepGradient(iArr2, fArr2, fIntBitsToFloat8, Float.intBitsToFloat(iArr[i14]));
                return i15;
            }
        }
        return i8;
    }

    public void writeBundle(WireBuffer wireBuffer) {
        wireBuffer.writeInt(this.mPos);
        for (int i = 0; i < this.mPos; i++) {
            wireBuffer.writeInt(this.mArray[i]);
        }
    }

    public void readBundle(WireBuffer wireBuffer) {
        int i = wireBuffer.readInt();
        if (i <= 0 || i > 1024) {
            throw new RuntimeException("buffer corrupt paint len = " + i);
        }
        this.mArray = new int[i];
        int i2 = 0;
        while (true) {
            int[] iArr = this.mArray;
            if (i2 < iArr.length) {
                iArr[i2] = wireBuffer.readInt();
                i2++;
            } else {
                this.mPos = i;
                return;
            }
        }
    }

    public void setLinearGradient(int[] iArr, int i, float[] fArr, float f, float f2, float f3, float f4, int i2) {
        int[] iArr2 = this.mArray;
        int i3 = this.mPos;
        int i4 = i3 + 1;
        this.mPos = i4;
        iArr2[i3] = 11;
        this.mPos = i3 + 2;
        iArr2[i4] = (i << 16) | iArr.length;
        for (int i5 : iArr) {
            int[] iArr3 = this.mArray;
            int i6 = this.mPos;
            this.mPos = i6 + 1;
            iArr3[i6] = i5;
        }
        int[] iArr4 = this.mArray;
        int i7 = this.mPos;
        this.mPos = i7 + 1;
        int length = fArr == null ? 0 : fArr.length;
        iArr4[i7] = length;
        for (int i8 = 0; i8 < length; i8++) {
            int[] iArr5 = this.mArray;
            int i9 = this.mPos;
            this.mPos = i9 + 1;
            iArr5[i9] = Float.floatToRawIntBits(fArr[i8]);
        }
        int[] iArr6 = this.mArray;
        int i10 = this.mPos;
        this.mPos = i10 + 1;
        iArr6[i10] = Float.floatToRawIntBits(f);
        int[] iArr7 = this.mArray;
        int i11 = this.mPos;
        this.mPos = i11 + 1;
        iArr7[i11] = Float.floatToRawIntBits(f2);
        int[] iArr8 = this.mArray;
        int i12 = this.mPos;
        this.mPos = i12 + 1;
        iArr8[i12] = Float.floatToRawIntBits(f3);
        int[] iArr9 = this.mArray;
        int i13 = this.mPos;
        this.mPos = i13 + 1;
        iArr9[i13] = Float.floatToRawIntBits(f4);
        int[] iArr10 = this.mArray;
        int i14 = this.mPos;
        this.mPos = i14 + 1;
        iArr10[i14] = i2;
    }

    public void setSweepGradient(int[] iArr, int i, float[] fArr, float f, float f2) {
        int[] iArr2 = this.mArray;
        int i2 = this.mPos;
        int i3 = i2 + 1;
        this.mPos = i3;
        iArr2[i2] = 131083;
        this.mPos = i2 + 2;
        int i4 = i << 16;
        int length = iArr == null ? 0 : iArr.length;
        iArr2[i3] = i4 | length;
        for (int i5 = 0; i5 < length; i5++) {
            int[] iArr3 = this.mArray;
            int i6 = this.mPos;
            this.mPos = i6 + 1;
            iArr3[i6] = iArr[i5];
        }
        int[] iArr4 = this.mArray;
        int i7 = this.mPos;
        this.mPos = i7 + 1;
        int length2 = fArr == null ? 0 : fArr.length;
        iArr4[i7] = length2;
        for (int i8 = 0; i8 < length2; i8++) {
            int[] iArr5 = this.mArray;
            int i9 = this.mPos;
            this.mPos = i9 + 1;
            iArr5[i9] = Float.floatToRawIntBits(fArr[i8]);
        }
        int[] iArr6 = this.mArray;
        int i10 = this.mPos;
        this.mPos = i10 + 1;
        iArr6[i10] = Float.floatToRawIntBits(f);
        int[] iArr7 = this.mArray;
        int i11 = this.mPos;
        this.mPos = i11 + 1;
        iArr7[i11] = Float.floatToRawIntBits(f2);
    }

    public void setRadialGradient(int[] iArr, int i, float[] fArr, float f, float f2, float f3, int i2) {
        int[] iArr2 = this.mArray;
        int i3 = this.mPos;
        int i4 = i3 + 1;
        this.mPos = i4;
        iArr2[i3] = 65547;
        this.mPos = i3 + 2;
        int i5 = i << 16;
        int length = iArr == null ? 0 : iArr.length;
        iArr2[i4] = i5 | length;
        for (int i6 = 0; i6 < length; i6++) {
            int[] iArr3 = this.mArray;
            int i7 = this.mPos;
            this.mPos = i7 + 1;
            iArr3[i7] = iArr[i6];
        }
        int[] iArr4 = this.mArray;
        int i8 = this.mPos;
        this.mPos = i8 + 1;
        int length2 = fArr == null ? 0 : fArr.length;
        iArr4[i8] = length2;
        for (int i9 = 0; i9 < length2; i9++) {
            int[] iArr5 = this.mArray;
            int i10 = this.mPos;
            this.mPos = i10 + 1;
            iArr5[i10] = Float.floatToRawIntBits(fArr[i9]);
        }
        int[] iArr6 = this.mArray;
        int i11 = this.mPos;
        this.mPos = i11 + 1;
        iArr6[i11] = Float.floatToRawIntBits(f);
        int[] iArr7 = this.mArray;
        int i12 = this.mPos;
        this.mPos = i12 + 1;
        iArr7[i12] = Float.floatToRawIntBits(f2);
        int[] iArr8 = this.mArray;
        int i13 = this.mPos;
        this.mPos = i13 + 1;
        iArr8[i13] = Float.floatToRawIntBits(f3);
        int[] iArr9 = this.mArray;
        int i14 = this.mPos;
        this.mPos = i14 + 1;
        iArr9[i14] = i2;
    }

    public void setColorFilter(int i, int i2) {
        int[] iArr = this.mArray;
        int i3 = this.mPos;
        iArr[i3] = (i2 << 16) | 13;
        this.mPos = i3 + 2;
        iArr[i3 + 1] = i;
    }

    public void setColorFilterId(int i, int i2) {
        int[] iArr = this.mArray;
        int i3 = this.mPos;
        iArr[i3] = (i2 << 16) | 20;
        this.mPos = i3 + 2;
        iArr[i3 + 1] = i;
        this.mColorFilterSet = true;
    }

    public void clearColorFilter() {
        int[] iArr = this.mArray;
        int i = this.mPos;
        iArr[i] = 21;
        this.mPos = i + 1;
        this.mColorFilterSet = false;
    }

    public void setTextSize(float f) {
        int[] iArr = this.mArray;
        int i = this.mPos;
        iArr[i] = 1;
        int i2 = i + 1;
        this.mPos = i2;
        iArr[i2] = Float.floatToRawIntBits(f);
        this.mPos++;
    }

    public void setTextStyle(int i, int i2, boolean z) {
        int i3 = (i2 & 1023) | (z ? 2048 : 0);
        int[] iArr = this.mArray;
        int i4 = this.mPos;
        int i5 = i4 + 1;
        this.mPos = i5;
        iArr[i4] = (i3 << 16) | 16;
        this.mPos = i4 + 2;
        iArr[i5] = i;
    }

    public void setStrokeWidth(float f) {
        int[] iArr = this.mArray;
        int i = this.mPos;
        iArr[i] = 5;
        int i2 = i + 1;
        this.mPos = i2;
        iArr[i2] = Float.floatToRawIntBits(f);
        this.mPos++;
    }

    public void setColor(int i) {
        int[] iArr = this.mArray;
        int i2 = this.mPos;
        iArr[i2] = 4;
        int i3 = i2 + 1;
        this.mPos = i3;
        iArr[i3] = i;
        this.mPos = i2 + 2;
    }

    public void setColor(int i, int i2, int i3, int i4) {
        setColor((i << 16) | (i4 << 24) | (i2 << 8) | i3);
    }

    public void setColor(float f, float f2, float f3, float f4) {
        setColor(Utils.toARGB(f4, f, f2, f3));
    }

    public void setColorId(int i) {
        int[] iArr = this.mArray;
        int i2 = this.mPos;
        iArr[i2] = 19;
        int i3 = i2 + 1;
        this.mPos = i3;
        iArr[i3] = i;
        this.mPos = i2 + 2;
    }

    public void setStrokeCap(int i) {
        int[] iArr = this.mArray;
        int i2 = this.mPos;
        iArr[i2] = (i << 16) | 7;
        this.mPos = i2 + 1;
    }

    public void setStyle(int i) {
        int[] iArr = this.mArray;
        int i2 = this.mPos;
        iArr[i2] = (i << 16) | 8;
        this.mPos = i2 + 1;
    }

    public void setShader(int i) {
        this.mLastShaderSet = i;
        int[] iArr = this.mArray;
        int i2 = this.mPos;
        iArr[i2] = 9;
        int i3 = i2 + 1;
        this.mPos = i3;
        iArr[i3] = i;
        this.mPos = i2 + 2;
    }

    public void setAlpha(float f) {
        int[] iArr = this.mArray;
        int i = this.mPos;
        iArr[i] = 12;
        int i2 = i + 1;
        this.mPos = i2;
        iArr[i2] = Float.floatToRawIntBits(f);
        this.mPos++;
    }

    public void setStrokeMiter(float f) {
        int[] iArr = this.mArray;
        int i = this.mPos;
        iArr[i] = 6;
        int i2 = i + 1;
        this.mPos = i2;
        iArr[i2] = Float.floatToRawIntBits(f);
        this.mPos++;
    }

    public void setStrokeJoin(int i) {
        int[] iArr = this.mArray;
        int i2 = this.mPos;
        iArr[i2] = (i << 16) | 15;
        this.mPos = i2 + 1;
    }

    public void setFilterBitmap(boolean z) {
        int[] iArr = this.mArray;
        int i = this.mPos;
        iArr[i] = (z ? 65536 : 0) | 17;
        this.mPos = i + 1;
    }

    public void setBlendMode(int i) {
        int[] iArr = this.mArray;
        int i2 = this.mPos;
        iArr[i2] = (i << 16) | 18;
        this.mPos = i2 + 1;
    }

    public void setAntiAlias(boolean z) {
        int[] iArr = this.mArray;
        int i = this.mPos;
        iArr[i] = ((z ? 1 : 0) << 16) | 14;
        this.mPos = i + 1;
    }

    public void reset() {
        this.mPos = 0;
        if (this.mColorFilterSet) {
            clearColorFilter();
        }
        int i = this.mLastShaderSet;
        if (i == -1 || i == 0) {
            return;
        }
        setShader(0);
    }

    public static String blendModeString(int i) {
        switch (i) {
            case 0:
                return "CLEAR";
            case 1:
                return "SRC";
            case 2:
                return "DST";
            case 3:
                return "SRC_OVER";
            case 4:
                return "DST_OVER";
            case 5:
                return "SRC_IN";
            case 6:
                return "DST_IN";
            case 7:
                return "SRC_OUT";
            case 8:
                return "DST_OUT";
            case 9:
                return "SRC_ATOP";
            case 10:
                return "DST_ATOP";
            case 11:
                return "XOR";
            case 12:
                return "PLUS";
            case 13:
                return "MODULATE";
            case 14:
                return "SCREEN";
            case 15:
                return "OVERLAY";
            case 16:
                return "DARKEN";
            case 17:
                return "LIGHTEN";
            case 18:
                return "COLOR_DODGE";
            case 19:
                return "COLOR_BURN";
            case 20:
                return "HARD_LIGHT";
            case 21:
                return "SOFT_LIGHT";
            case 22:
                return "DIFFERENCE";
            case 23:
                return "EXCLUSION";
            case 24:
                return "MULTIPLY";
            case 25:
                return "HUE";
            case 26:
                return "SATURATION";
            case 27:
                return "COLOR";
            case 28:
                return "LUMINOSITY";
            case 29:
            default:
                return PerfettoProtoLogImpl.NULL_STRING;
            case 30:
                return "ADD";
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:23:0x0033  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0037  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void registerVars(RemoteContext remoteContext, VariableSupport variableSupport) {
        PaintBundle paintBundle;
        RemoteContext remoteContext2;
        VariableSupport variableSupport2;
        int iCallRegisterGradient = 0;
        while (iCallRegisterGradient < this.mPos) {
            int[] iArr = this.mArray;
            int i = iCallRegisterGradient + 1;
            int i2 = iArr[iCallRegisterGradient];
            int i3 = 65535 & i2;
            if (i3 != 1) {
                if (i3 != 9 && i3 != 16 && i3 != 4) {
                    if (i3 != 5 && i3 != 6) {
                        if (i3 == 19 || i3 == 20) {
                            paintBundle = this;
                            remoteContext2 = remoteContext;
                            variableSupport2 = variableSupport;
                            iCallRegisterGradient += 2;
                            remoteContext2.listensTo(iArr[i], variableSupport2);
                        } else {
                            switch (i3) {
                                case 11:
                                    paintBundle = this;
                                    remoteContext2 = remoteContext;
                                    variableSupport2 = variableSupport;
                                    iCallRegisterGradient = paintBundle.callRegisterGradient(i2, iArr, i, remoteContext2, variableSupport2);
                                    break;
                                case 12:
                                    break;
                                case 13:
                                    break;
                                default:
                                    paintBundle = this;
                                    remoteContext2 = remoteContext;
                                    variableSupport2 = variableSupport;
                                    iCallRegisterGradient = i;
                                    break;
                            }
                        }
                    }
                } else {
                    paintBundle = this;
                    remoteContext2 = remoteContext;
                    variableSupport2 = variableSupport;
                    iCallRegisterGradient += 2;
                }
            } else {
                paintBundle = this;
                remoteContext2 = remoteContext;
                variableSupport2 = variableSupport;
                iCallRegisterGradient += 2;
                float fIntBitsToFloat = Float.intBitsToFloat(iArr[i]);
                if (Float.isNaN(fIntBitsToFloat)) {
                    remoteContext2.listensTo(Utils.idFromNan(fIntBitsToFloat), variableSupport2);
                }
            }
            this = paintBundle;
            remoteContext = remoteContext2;
            variableSupport = variableSupport2;
        }
    }

    /* JADX WARN: Failed to find 'out' block for switch in B:24:0x003f. Please report as an issue. */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0049  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public void updateVariables(RemoteContext remoteContext) {
        PaintBundle paintBundle;
        RemoteContext remoteContext2;
        int[] iArr = this.mOutArray;
        int iUpdateFloatsInGradient = 0;
        if (iArr == null) {
            int[] iArr2 = this.mArray;
            this.mOutArray = Arrays.copyOf(iArr2, iArr2.length);
        } else {
            int[] iArr3 = this.mArray;
            System.arraycopy(iArr3, 0, iArr, 0, iArr3.length);
        }
        while (iUpdateFloatsInGradient < this.mPos) {
            int[] iArr4 = this.mArray;
            int i = iUpdateFloatsInGradient + 1;
            int i2 = iArr4[iUpdateFloatsInGradient];
            int i3 = 65535 & i2;
            if (i3 != 1) {
                if (i3 != 9 && i3 != 16 && i3 != 4) {
                    if (i3 != 5 && i3 != 6) {
                        if (i3 == 19 || i3 == 20) {
                            paintBundle = this;
                            remoteContext2 = remoteContext;
                            paintBundle.mOutArray[i] = paintBundle.fixColor(iArr4[i], remoteContext2);
                            iUpdateFloatsInGradient += 2;
                        } else {
                            switch (i3) {
                                case 11:
                                    paintBundle = this;
                                    remoteContext2 = remoteContext;
                                    iUpdateFloatsInGradient = paintBundle.updateFloatsInGradient(i2, this.mOutArray, iArr4, i, remoteContext2);
                                    break;
                                case 12:
                                    break;
                                case 13:
                                    break;
                                default:
                                    paintBundle = this;
                                    remoteContext2 = remoteContext;
                                    iUpdateFloatsInGradient = i;
                                    break;
                            }
                        }
                    }
                } else {
                    paintBundle = this;
                    remoteContext2 = remoteContext;
                    iUpdateFloatsInGradient += 2;
                }
            } else {
                paintBundle = this;
                remoteContext2 = remoteContext;
                paintBundle.mOutArray[i] = paintBundle.fixFloatVar(iArr4[i], remoteContext2);
                iUpdateFloatsInGradient += 2;
            }
            this = paintBundle;
            remoteContext = remoteContext2;
        }
    }

    private int fixFloatVar(int i, RemoteContext remoteContext) {
        float fIntBitsToFloat = Float.intBitsToFloat(i);
        return Float.isNaN(fIntBitsToFloat) ? Float.floatToRawIntBits(remoteContext.getFloat(Utils.idFromNan(fIntBitsToFloat))) : i;
    }

    private int fixColor(int i, RemoteContext remoteContext) {
        return remoteContext.getColor(i);
    }

    int updateFloatsInGradient(int i, int[] iArr, int[] iArr2, int i2, RemoteContext remoteContext) {
        int i3 = i >> 16;
        int i4 = i2 + 1;
        int i5 = iArr2[i2];
        int i6 = i5 & 255;
        int i7 = (i5 >> 16) & 65535;
        int i8 = 0;
        if (i3 == 0) {
            if (i6 > 0) {
                for (int i9 = 0; i9 < i6; i9++) {
                    int i10 = iArr2[i4];
                    if (((1 << i9) & i7) != 0) {
                        iArr[i4] = fixColor(i10, remoteContext);
                    }
                    i4++;
                }
            }
            int i11 = i4 + 1;
            int i12 = iArr2[i4];
            if (i12 > 0) {
                while (i8 < i12) {
                    iArr[i11] = fixFloatVar(iArr2[i11], remoteContext);
                    i11++;
                    i8++;
                }
            }
            iArr[i11] = fixFloatVar(iArr2[i11], remoteContext);
            int i13 = i11 + 1;
            iArr[i13] = fixFloatVar(iArr2[i13], remoteContext);
            int i14 = i11 + 2;
            iArr[i14] = fixFloatVar(iArr2[i14], remoteContext);
            int i15 = i11 + 3;
            iArr[i15] = fixFloatVar(iArr2[i15], remoteContext);
            return i11 + 5;
        }
        if (i3 == 1) {
            if (i6 > 0) {
                for (int i16 = 0; i16 < i6; i16++) {
                    int i17 = iArr2[i4];
                    if (((1 << i16) & i7) != 0) {
                        iArr[i4] = fixColor(i17, remoteContext);
                    }
                    i4++;
                }
            }
            int i18 = i4 + 1;
            int i19 = iArr2[i4];
            if (i19 > 0) {
                while (i8 < i19) {
                    iArr[i18] = fixFloatVar(iArr2[i18], remoteContext);
                    i18++;
                    i8++;
                }
            }
            iArr[i18] = fixFloatVar(iArr2[i18], remoteContext);
            int i20 = i18 + 1;
            iArr[i20] = fixFloatVar(iArr2[i20], remoteContext);
            int i21 = i18 + 2;
            iArr[i21] = fixFloatVar(iArr2[i21], remoteContext);
            return i18 + 4;
        }
        if (i3 == 2) {
            if (i6 > 0) {
                for (int i22 = 0; i22 < i6; i22++) {
                    int i23 = iArr2[i4];
                    if (((1 << i22) & i7) != 0) {
                        iArr[i4] = fixColor(i23, remoteContext);
                    }
                    i4++;
                }
            }
            int i24 = i4 + 1;
            int i25 = iArr2[i4];
            if (i25 > 0) {
                float[] fArr = new float[i25];
                while (i8 < i25) {
                    iArr[i24] = fixFloatVar(iArr2[i24], remoteContext);
                    i24++;
                    i8++;
                }
            }
            iArr[i24] = fixFloatVar(iArr2[i24], remoteContext);
            int i26 = i24 + 1;
            iArr[i26] = fixFloatVar(iArr2[i26], remoteContext);
            return i24 + 2;
        }
        System.err.println("gradient type unknown");
        return i4;
    }

    @Override // com.android.internal.widget.remotecompose.core.serialize.Serializable
    public void serialize(MapSerializer mapSerializer) {
        mapSerializer.addType("PaintBundle");
        ArrayList arrayList = new ArrayList();
        int iSerializeGradient = 0;
        while (iSerializeGradient < this.mPos) {
            int[] iArr = this.mArray;
            int i = iSerializeGradient + 1;
            int i2 = iArr[iSerializeGradient];
            int i3 = 65535 & i2;
            if (i3 != 1) {
                switch (i3) {
                    case 4:
                        iSerializeGradient += 2;
                        arrayList.add(MapSerializer.orderedOf("type", "Color", "color", colorInt(iArr[i])));
                        continue;
                    case 5:
                        iSerializeGradient += 2;
                        arrayList.add(MapSerializer.orderedOf("type", "StrokeWidth", "width", getVariable(iArr[i])));
                        continue;
                    case 6:
                        iSerializeGradient += 2;
                        arrayList.add(MapSerializer.orderedOf("type", "StrokeMiter", "miter", getVariable(iArr[i])));
                        continue;
                    case 7:
                        arrayList.add(MapSerializer.orderedOf("type", "StrokeCap", "cap", Integer.valueOf(i2 >> 16)));
                        break;
                    case 8:
                        arrayList.add(MapSerializer.orderedOf("type", "Style", "style", Integer.valueOf(i2 >> 16)));
                        break;
                    case 9:
                        iSerializeGradient += 2;
                        arrayList.add(MapSerializer.orderedOf("type", "Shader", "id", Integer.valueOf(iArr[i])));
                        continue;
                    case 10:
                        arrayList.add(MapSerializer.orderedOf("type", "ImageFilterQuality", "quality", Integer.valueOf(i2 >> 16)));
                        break;
                    case 11:
                        iSerializeGradient = serializeGradient(i2, iArr, i, arrayList);
                        continue;
                    case 12:
                        iSerializeGradient += 2;
                        arrayList.add(MapSerializer.orderedOf("type", "Alpha", "alpha", getVariable(iArr[i])));
                        continue;
                    case 13:
                        iSerializeGradient += 2;
                        arrayList.add(MapSerializer.orderedOf("type", "ColorFilter", "color", colorInt(iArr[i]), "mode", blendModeString(i2 >> 16)));
                        continue;
                    case 14:
                        arrayList.add(MapSerializer.orderedOf("type", "AntiAlias", "enabled", Boolean.valueOf((i2 >> 16) != 0)));
                        break;
                    case 15:
                        arrayList.add(MapSerializer.orderedOf("type", "StrokeJoin", "strokeJoin", Integer.valueOf(i2 >> 16)));
                        break;
                    case 16:
                        float f = (i2 >> 16) & 1023;
                        boolean z = (i2 >> 26) > 0;
                        iSerializeGradient += 2;
                        arrayList.add(MapSerializer.orderedOf("type", "FontFamily", "fontFamily", Integer.valueOf(iArr[i])));
                        arrayList.add(MapSerializer.orderedOf("type", "FontWeight", "weight", Float.valueOf(f)));
                        arrayList.add(MapSerializer.orderedOf("type", "TypeFace", FontListParser.STYLE_ITALIC, Boolean.valueOf(z)));
                        continue;
                    case 17:
                        arrayList.add(MapSerializer.orderedOf("type", "FilterBitmap", "enabled", Boolean.valueOf((i2 >> 16) != 0)));
                        break;
                    case 18:
                        arrayList.add(MapSerializer.orderedOf("type", "BlendMode", "mode", blendModeString(i2 >> 16)));
                        break;
                    case 19:
                        iSerializeGradient += 2;
                        arrayList.add(MapSerializer.orderedOf("type", "ColorId", "id", Integer.valueOf(iArr[i])));
                        continue;
                    case 20:
                        iSerializeGradient += 2;
                        arrayList.add(MapSerializer.orderedOf("type", "ColorFilterID", "id", Integer.valueOf(iArr[i]), "mode", blendModeString(i2 >> 16)));
                        continue;
                    case 21:
                        arrayList.add(MapSerializer.orderedOf("type", "ClearColorFilter"));
                        break;
                }
                iSerializeGradient = i;
            } else {
                iSerializeGradient += 2;
                arrayList.add(MapSerializer.orderedOf("type", "TextSize", Contract.DatabaseSize.PATH, getVariable(iArr[i])));
            }
        }
        mapSerializer.add("operations", arrayList);
    }

    private static Map<String, Object> getVariable(int i) {
        float fIntBitsToFloat = Float.intBitsToFloat(i);
        if (Float.isNaN(fIntBitsToFloat)) {
            return MapSerializer.orderedOf("type", "Variable", "id", Integer.valueOf(Utils.idFromNan(fIntBitsToFloat)));
        }
        return MapSerializer.orderedOf("type", "Value", "value", Float.valueOf(fIntBitsToFloat));
    }

    private static int serializeGradient(int i, int[] iArr, int i2, List<Map<String, Object>> list) {
        String[] strArr;
        int i3 = i >> 16;
        int i4 = i2 + 1;
        int i5 = iArr[i2] & 255;
        int i6 = 0;
        float[] fArr = null;
        if (i5 > 0) {
            String[] strArr2 = new String[i5];
            int i7 = 0;
            while (i7 < i5) {
                strArr2[i7] = colorInt(iArr[i4]);
                i7++;
                i4++;
            }
            strArr = strArr2;
        } else {
            strArr = null;
        }
        int i8 = i4 + 1;
        int i9 = iArr[i4];
        if (i9 > 0) {
            fArr = new float[i9];
            while (i6 < strArr.length) {
                fArr[i6] = Float.intBitsToFloat(iArr[i8]);
                i6++;
                i8++;
            }
        }
        if (strArr != null) {
            if (i3 == 0) {
                int i10 = iArr[i8];
                int i11 = iArr[i8 + 1];
                int i12 = iArr[i8 + 2];
                int i13 = i8 + 4;
                int i14 = iArr[i8 + 3];
                int i15 = i8 + 5;
                int i16 = iArr[i13];
                Object obj = fArr;
                if (fArr == null) {
                    obj = Collections.EMPTY_LIST;
                }
                list.add(MapSerializer.orderedOf("type", "LinearGradient", "colors", strArr, "stops", obj, "startX", getVariable(i10), "startY", getVariable(i11), "endX", getVariable(i12), "endY", getVariable(i14), "tileMode", Integer.valueOf(i16)));
                return i15;
            }
            if (i3 == 1) {
                int i17 = iArr[i8];
                int i18 = iArr[i8 + 1];
                int i19 = i8 + 3;
                int i20 = iArr[i8 + 2];
                int i21 = i8 + 4;
                int i22 = iArr[i19];
                Object obj2 = fArr;
                if (fArr == null) {
                    obj2 = Collections.EMPTY_LIST;
                }
                list.add(MapSerializer.orderedOf("type", "RadialGradient", "colors", strArr, "stops", obj2, "centerX", getVariable(i17), "centerY", getVariable(i18), "radius", getVariable(i20), "tileMode", Integer.valueOf(i22)));
                return i21;
            }
            if (i3 == 2) {
                int i23 = i8 + 1;
                int i24 = iArr[i8];
                int i25 = i8 + 2;
                int i26 = iArr[i23];
                Object obj3 = fArr;
                if (fArr == null) {
                    obj3 = Collections.EMPTY_LIST;
                }
                list.add(MapSerializer.orderedOf("type", "SweepGradient", "colors", strArr, "stops", obj3, "centerX", getVariable(i24), "centerY", getVariable(i26)));
                return i25;
            }
        }
        return i8;
    }
}
