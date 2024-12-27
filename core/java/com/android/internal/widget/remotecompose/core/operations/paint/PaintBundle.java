package com.android.internal.widget.remotecompose.core.operations.paint;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import com.android.internal.widget.remotecompose.core.PaintContext;
import com.android.internal.widget.remotecompose.core.RemoteContext;
import com.android.internal.widget.remotecompose.core.VariableSupport;
import com.android.internal.widget.remotecompose.core.WireBuffer;
import com.android.internal.widget.remotecompose.core.operations.Utils;
import java.util.Arrays;

/* loaded from: classes5.dex */
public class PaintBundle {
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
    public static final int COLOR = 4;
    public static final int COLOR_FILTER = 13;
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

    public void applyPaintChange(PaintContext paintContext, PaintChanges p) {
        boolean italic;
        int cmd = 0;
        int mask = 0;
        if (this.mOutArray == null) {
            this.mOutArray = this.mArray;
        }
        while (cmd < this.mPos) {
            int i = cmd + 1;
            int cmd2 = this.mOutArray[cmd];
            mask |= 1 << (cmd2 - 1);
            switch (65535 & cmd2) {
                case 1:
                    p.setTextSize(Float.intBitsToFloat(this.mOutArray[i]));
                    cmd = i + 1;
                    continue;
                case 4:
                case 19:
                    p.setColor(this.mOutArray[i]);
                    cmd = i + 1;
                    continue;
                case 5:
                    p.setStrokeWidth(Float.intBitsToFloat(this.mOutArray[i]));
                    cmd = i + 1;
                    continue;
                case 6:
                    p.setStrokeMiter(Float.intBitsToFloat(this.mOutArray[i]));
                    cmd = i + 1;
                    continue;
                case 7:
                    p.setStrokeCap(cmd2 >> 16);
                    break;
                case 8:
                    p.setStyle(cmd2 >> 16);
                    break;
                case 9:
                    p.setShader(this.mOutArray[i]);
                    cmd = i + 1;
                    continue;
                case 10:
                    p.setImageFilterQuality(cmd2 >> 16);
                    break;
                case 11:
                    cmd = callSetGradient(cmd2, this.mOutArray, i, p);
                    continue;
                case 12:
                    p.setAlpha(Float.intBitsToFloat(this.mOutArray[i]));
                    cmd = i + 1;
                    continue;
                case 13:
                    p.setColorFilter(this.mOutArray[i], cmd2 >> 16);
                    cmd = i + 1;
                    continue;
                case 15:
                    p.setStrokeJoin(cmd2 >> 16);
                    break;
                case 16:
                    int style = cmd2 >> 16;
                    int weight = style & 1023;
                    italic = (style >> 10) > 0;
                    int i2 = i + 1;
                    int font_type = this.mOutArray[i];
                    p.setTypeFace(font_type, weight, italic);
                    cmd = i2;
                    continue;
                case 17:
                    italic = (cmd2 >> 16) != 0;
                    p.setFilterBitmap(italic);
                    break;
                case 18:
                    p.setBlendMode(cmd2 >> 16);
                    break;
            }
            cmd = i;
        }
        p.clear((~mask) & 8191);
    }

    private String toName(int id) {
        switch (id) {
            case 1:
                return "TEXT_SIZE";
            case 2:
            case 3:
            case 14:
            case 15:
            default:
                return "????" + id + "????";
            case 4:
                return "COLOR";
            case 5:
                return "STROKE_WIDTH";
            case 6:
                return "STROKE_MITER";
            case 7:
                return "CAP";
            case 8:
                return "STYLE";
            case 9:
                return "SHADER";
            case 10:
                return "IMAGE_FILTER_QUALITY";
            case 11:
                return "GRADIENT_LINEAR";
            case 12:
                return "ALPHA";
            case 13:
                return "COLOR_FILTER";
            case 16:
                return "TYPEFACE";
            case 17:
                return "FILTER_BITMAP";
            case 18:
                return "BLEND_MODE";
        }
    }

    private static String colorInt(int color) {
        String str = "000000000000" + Integer.toHexString(color);
        return "0x" + str.substring(str.length() - 8);
    }

    private static String colorInt(int[] color) {
        String str = NavigationBarInflaterView.SIZE_MOD_START;
        for (int i = 0; i < color.length; i++) {
            if (i > 0) {
                str = str + ", ";
            }
            str = str + colorInt(color[i]);
        }
        return str + NavigationBarInflaterView.SIZE_MOD_END;
    }

    private static String asFloatStr(int value) {
        float fValue = Float.intBitsToFloat(value);
        if (Float.isNaN(fValue)) {
            return NavigationBarInflaterView.SIZE_MOD_START + Utils.idFromNan(fValue) + NavigationBarInflaterView.SIZE_MOD_END;
        }
        return Float.toString(fValue);
    }

    public String toString() {
        int i;
        boolean italic;
        StringBuilder ret = new StringBuilder("\n");
        int cmd = 0;
        while (cmd < this.mPos) {
            int i2 = cmd + 1;
            int cmd2 = this.mArray[cmd];
            int type = 65535 & cmd2;
            switch (type) {
                case 1:
                    i = i2 + 1;
                    ret.append("    TextSize(" + asFloatStr(this.mArray[i2]));
                    continue;
                case 4:
                    i = i2 + 1;
                    ret.append("    Color(" + colorInt(this.mArray[i2]));
                    continue;
                case 5:
                    i = i2 + 1;
                    ret.append("    StrokeWidth(" + asFloatStr(this.mArray[i2]));
                    continue;
                case 6:
                    i = i2 + 1;
                    ret.append("    StrokeMiter(" + asFloatStr(this.mArray[i2]));
                    continue;
                case 7:
                    ret.append("    StrokeCap(" + (cmd2 >> 16));
                    break;
                case 8:
                    ret.append("    Style(" + (cmd2 >> 16));
                    break;
                case 9:
                    i = i2 + 1;
                    ret.append("    Shader(" + this.mArray[i2]);
                    continue;
                case 10:
                    ret.append("    ImageFilterQuality(" + (cmd2 >> 16));
                    break;
                case 11:
                    i = callPrintGradient(cmd2, this.mArray, i2, ret);
                    continue;
                case 12:
                    i = i2 + 1;
                    ret.append("    Alpha(" + asFloatStr(this.mArray[i2]));
                    continue;
                case 13:
                    i = i2 + 1;
                    ret.append("    ColorFilter(color=" + colorInt(this.mArray[i2]) + ", mode=" + blendModeString(cmd2 >> 16));
                    continue;
                case 14:
                    ret.append("    AntiAlias(" + (cmd2 >> 16));
                    break;
                case 15:
                    ret.append("    StrokeJoin(" + (cmd2 >> 16));
                    break;
                case 16:
                    int style = cmd2 >> 16;
                    int weight = style & 1023;
                    italic = (style >> 10) > 0;
                    int i3 = i2 + 1;
                    int font_type = this.mArray[i2];
                    ret.append("    TypeFace(" + font_type + ", " + weight + ", " + italic);
                    i = i3;
                    continue;
                case 17:
                    StringBuilder append = new StringBuilder().append("    FilterBitmap(");
                    italic = (cmd2 >> 16) != 0;
                    ret.append(append.append(italic).toString());
                    break;
                case 18:
                    ret.append("    BlendMode(" + blendModeString(cmd2 >> 16));
                    break;
                case 19:
                    i = i2 + 1;
                    ret.append("    ColorId([" + this.mArray[i2] + NavigationBarInflaterView.SIZE_MOD_END);
                    continue;
            }
            i = i2;
            ret.append("),\n");
            cmd = i;
        }
        return ret.toString();
    }

    int callPrintGradient(int cmd, int[] array, int i, StringBuilder p) {
        int type = cmd >> 16;
        switch (type) {
            case 0:
                p.append("    LinearGradient(\n");
                int ret = i + 1;
                int len = array[i];
                int[] colors = null;
                if (len > 0) {
                    colors = new int[len];
                    int j = 0;
                    while (j < colors.length) {
                        colors[j] = array[ret];
                        j++;
                        ret++;
                    }
                }
                int ret2 = ret + 1;
                int len2 = array[ret];
                String[] stops = null;
                if (len2 > 0) {
                    stops = new String[len2];
                    int j2 = 0;
                    while (j2 < stops.length) {
                        stops[j2] = asFloatStr(array[ret2]);
                        j2++;
                        ret2++;
                    }
                }
                p.append("      colors = " + colorInt(colors) + ",\n");
                p.append("      stops = " + Arrays.toString(stops) + ",\n");
                p.append("      start = ");
                int ret3 = ret2 + 1;
                p.append(NavigationBarInflaterView.SIZE_MOD_START + asFloatStr(array[ret2]));
                int ret4 = ret3 + 1;
                p.append(", " + asFloatStr(array[ret3]) + "],\n");
                p.append("      end = ");
                int ret5 = ret4 + 1;
                p.append(NavigationBarInflaterView.SIZE_MOD_START + asFloatStr(array[ret4]));
                int ret6 = ret5 + 1;
                p.append(", " + asFloatStr(array[ret5]) + "],\n");
                int ret7 = ret6 + 1;
                int tileMode = array[ret6];
                p.append("      tileMode = " + tileMode + "\n    ");
                return ret7;
            case 1:
                p.append("    RadialGradient(\n");
                int ret8 = i + 1;
                int len3 = array[i];
                int[] colors2 = null;
                if (len3 > 0) {
                    colors2 = new int[len3];
                    int j3 = 0;
                    while (j3 < colors2.length) {
                        colors2[j3] = array[ret8];
                        j3++;
                        ret8++;
                    }
                }
                int ret9 = ret8 + 1;
                int ret10 = array[ret8];
                String[] stops2 = null;
                if (ret10 > 0) {
                    stops2 = new String[ret10];
                    int j4 = 0;
                    while (true) {
                        int len4 = ret10;
                        if (j4 < stops2.length) {
                            stops2[j4] = asFloatStr(array[ret9]);
                            j4++;
                            ret9++;
                            ret10 = len4;
                        }
                    }
                }
                p.append("      colors = " + colorInt(colors2) + ",\n");
                p.append("      stops = " + Arrays.toString(stops2) + ",\n");
                p.append("      center = ");
                int ret11 = ret9 + 1;
                p.append(NavigationBarInflaterView.SIZE_MOD_START + asFloatStr(array[ret9]));
                int ret12 = ret11 + 1;
                p.append(", " + asFloatStr(array[ret11]) + "],\n");
                p.append("      radius =");
                int ret13 = ret12 + 1;
                p.append(" " + asFloatStr(array[ret12]) + ",\n");
                int ret14 = ret13 + 1;
                int tileMode2 = array[ret13];
                p.append("      tileMode = " + tileMode2 + "\n    ");
                return ret14;
            case 2:
                p.append("    SweepGradient(\n");
                int ret15 = i + 1;
                int len5 = array[i];
                int[] colors3 = null;
                if (len5 > 0) {
                    colors3 = new int[len5];
                    int j5 = 0;
                    while (j5 < colors3.length) {
                        colors3[j5] = array[ret15];
                        j5++;
                        ret15++;
                    }
                }
                int ret16 = ret15 + 1;
                int len6 = array[ret15];
                String[] stops3 = null;
                if (len6 > 0) {
                    stops3 = new String[len6];
                    int j6 = 0;
                    while (j6 < stops3.length) {
                        stops3[j6] = asFloatStr(array[ret16]);
                        j6++;
                        ret16++;
                    }
                }
                p.append("      colors = " + colorInt(colors3) + ",\n");
                p.append("      stops = " + Arrays.toString(stops3) + ",\n");
                p.append("      center = ");
                int ret17 = ret16 + 1;
                p.append(NavigationBarInflaterView.SIZE_MOD_START + asFloatStr(array[ret16]));
                int ret18 = ret17 + 1;
                p.append(", " + asFloatStr(array[ret17]) + "],\n    ");
                return ret18;
            default:
                p.append("GRADIENT_??????!!!!");
                return i;
        }
    }

    int callSetGradient(int cmd, int[] array, int i, PaintChanges p) {
        int[] colors;
        int gradientType = cmd >> 16;
        int ret = i + 1;
        int len = array[i];
        if (len <= 0) {
            colors = null;
        } else {
            int[] colors2 = new int[len];
            int j = 0;
            while (j < colors2.length) {
                colors2[j] = array[ret];
                j++;
                ret++;
            }
            colors = colors2;
        }
        int ret2 = ret + 1;
        int len2 = array[ret];
        float[] stops = null;
        if (len2 > 0) {
            stops = new float[len2];
            int j2 = 0;
            while (j2 < colors.length) {
                stops[j2] = Float.intBitsToFloat(array[ret2]);
                j2++;
                ret2++;
            }
        }
        if (colors == null) {
            return ret2;
        }
        switch (gradientType) {
            case 0:
                int ret3 = ret2 + 1;
                float startX = Float.intBitsToFloat(array[ret2]);
                int ret4 = ret3 + 1;
                float startY = Float.intBitsToFloat(array[ret3]);
                int ret5 = ret4 + 1;
                float endX = Float.intBitsToFloat(array[ret4]);
                int ret6 = ret5 + 1;
                float endY = Float.intBitsToFloat(array[ret5]);
                int ret7 = ret6 + 1;
                int tileMode = array[ret6];
                p.setLinearGradient(colors, stops, startX, startY, endX, endY, tileMode);
                return ret7;
            case 1:
                int ret8 = ret2 + 1;
                float centerX = Float.intBitsToFloat(array[ret2]);
                int ret9 = ret8 + 1;
                float centerY = Float.intBitsToFloat(array[ret8]);
                int ret10 = ret9 + 1;
                float radius = Float.intBitsToFloat(array[ret9]);
                int ret11 = ret10 + 1;
                int tileMode2 = array[ret10];
                p.setRadialGradient(colors, stops, centerX, centerY, radius, tileMode2);
                return ret11;
            case 2:
                int ret12 = ret2 + 1;
                float centerX2 = Float.intBitsToFloat(array[ret2]);
                int ret13 = ret12 + 1;
                float centerY2 = Float.intBitsToFloat(array[ret12]);
                p.setSweepGradient(colors, stops, centerX2, centerY2);
                return ret13;
            default:
                return ret2;
        }
    }

    public void writeBundle(WireBuffer buffer) {
        buffer.writeInt(this.mPos);
        for (int index = 0; index < this.mPos; index++) {
            buffer.writeInt(this.mArray[index]);
        }
    }

    public void readBundle(WireBuffer buffer) {
        int len = buffer.readInt();
        if (len <= 0 || len > 1024) {
            throw new RuntimeException("buffer corrupt paint len = " + len);
        }
        this.mArray = new int[len];
        for (int i = 0; i < this.mArray.length; i++) {
            this.mArray[i] = buffer.readInt();
        }
        this.mPos = len;
    }

    public void setLinearGradient(int[] colors, float[] stops, float startX, float startY, float endX, float endY, int tileMode) {
        int i = this.mPos;
        int[] iArr = this.mArray;
        int i2 = this.mPos;
        this.mPos = i2 + 1;
        iArr[i2] = 11;
        int[] iArr2 = this.mArray;
        int i3 = this.mPos;
        this.mPos = i3 + 1;
        int length = colors == null ? 0 : colors.length;
        int len = length;
        iArr2[i3] = length;
        for (int i4 = 0; i4 < len; i4++) {
            int[] iArr3 = this.mArray;
            int i5 = this.mPos;
            this.mPos = i5 + 1;
            iArr3[i5] = colors[i4];
        }
        int[] iArr4 = this.mArray;
        int i6 = this.mPos;
        this.mPos = i6 + 1;
        int length2 = stops != null ? stops.length : 0;
        int len2 = length2;
        iArr4[i6] = length2;
        for (int i7 = 0; i7 < len2; i7++) {
            int[] iArr5 = this.mArray;
            int i8 = this.mPos;
            this.mPos = i8 + 1;
            iArr5[i8] = Float.floatToRawIntBits(stops[i7]);
        }
        int[] iArr6 = this.mArray;
        int i9 = this.mPos;
        this.mPos = i9 + 1;
        iArr6[i9] = Float.floatToRawIntBits(startX);
        int[] iArr7 = this.mArray;
        int i10 = this.mPos;
        this.mPos = i10 + 1;
        iArr7[i10] = Float.floatToRawIntBits(startY);
        int[] iArr8 = this.mArray;
        int i11 = this.mPos;
        this.mPos = i11 + 1;
        iArr8[i11] = Float.floatToRawIntBits(endX);
        int[] iArr9 = this.mArray;
        int i12 = this.mPos;
        this.mPos = i12 + 1;
        iArr9[i12] = Float.floatToRawIntBits(endY);
        int[] iArr10 = this.mArray;
        int i13 = this.mPos;
        this.mPos = i13 + 1;
        iArr10[i13] = tileMode;
    }

    public void setSweepGradient(int[] colors, float[] stops, float centerX, float centerY) {
        int i = this.mPos;
        int[] iArr = this.mArray;
        int i2 = this.mPos;
        this.mPos = i2 + 1;
        iArr[i2] = 131083;
        int[] iArr2 = this.mArray;
        int i3 = this.mPos;
        this.mPos = i3 + 1;
        int length = colors == null ? 0 : colors.length;
        int len = length;
        iArr2[i3] = length;
        for (int i4 = 0; i4 < len; i4++) {
            int[] iArr3 = this.mArray;
            int i5 = this.mPos;
            this.mPos = i5 + 1;
            iArr3[i5] = colors[i4];
        }
        int[] iArr4 = this.mArray;
        int i6 = this.mPos;
        this.mPos = i6 + 1;
        int length2 = stops != null ? stops.length : 0;
        int len2 = length2;
        iArr4[i6] = length2;
        for (int i7 = 0; i7 < len2; i7++) {
            int[] iArr5 = this.mArray;
            int i8 = this.mPos;
            this.mPos = i8 + 1;
            iArr5[i8] = Float.floatToRawIntBits(stops[i7]);
        }
        int[] iArr6 = this.mArray;
        int i9 = this.mPos;
        this.mPos = i9 + 1;
        iArr6[i9] = Float.floatToRawIntBits(centerX);
        int[] iArr7 = this.mArray;
        int i10 = this.mPos;
        this.mPos = i10 + 1;
        iArr7[i10] = Float.floatToRawIntBits(centerY);
    }

    public void setRadialGradient(int[] colors, float[] stops, float centerX, float centerY, float radius, int tileMode) {
        int i = this.mPos;
        int[] iArr = this.mArray;
        int i2 = this.mPos;
        this.mPos = i2 + 1;
        iArr[i2] = 65547;
        int[] iArr2 = this.mArray;
        int i3 = this.mPos;
        this.mPos = i3 + 1;
        int length = colors == null ? 0 : colors.length;
        int len = length;
        iArr2[i3] = length;
        for (int i4 = 0; i4 < len; i4++) {
            int[] iArr3 = this.mArray;
            int i5 = this.mPos;
            this.mPos = i5 + 1;
            iArr3[i5] = colors[i4];
        }
        int[] iArr4 = this.mArray;
        int i6 = this.mPos;
        this.mPos = i6 + 1;
        int length2 = stops != null ? stops.length : 0;
        int len2 = length2;
        iArr4[i6] = length2;
        for (int i7 = 0; i7 < len2; i7++) {
            int[] iArr5 = this.mArray;
            int i8 = this.mPos;
            this.mPos = i8 + 1;
            iArr5[i8] = Float.floatToRawIntBits(stops[i7]);
        }
        int[] iArr6 = this.mArray;
        int i9 = this.mPos;
        this.mPos = i9 + 1;
        iArr6[i9] = Float.floatToRawIntBits(centerX);
        int[] iArr7 = this.mArray;
        int i10 = this.mPos;
        this.mPos = i10 + 1;
        iArr7[i10] = Float.floatToRawIntBits(centerY);
        int[] iArr8 = this.mArray;
        int i11 = this.mPos;
        this.mPos = i11 + 1;
        iArr8[i11] = Float.floatToRawIntBits(radius);
        int[] iArr9 = this.mArray;
        int i12 = this.mPos;
        this.mPos = i12 + 1;
        iArr9[i12] = tileMode;
    }

    public void setColorFilter(int color, int mode) {
        this.mArray[this.mPos] = (mode << 16) | 13;
        this.mPos++;
        int[] iArr = this.mArray;
        int i = this.mPos;
        this.mPos = i + 1;
        iArr[i] = color;
    }

    public void setTextSize(float size) {
        int i = this.mPos;
        this.mArray[this.mPos] = 1;
        this.mPos++;
        this.mArray[this.mPos] = Float.floatToRawIntBits(size);
        this.mPos++;
    }

    public void setTextStyle(int fontType, int weight, boolean italic) {
        int style = (weight & 1023) | (italic ? 2048 : 0);
        int[] iArr = this.mArray;
        int i = this.mPos;
        this.mPos = i + 1;
        iArr[i] = (style << 16) | 16;
        int[] iArr2 = this.mArray;
        int i2 = this.mPos;
        this.mPos = i2 + 1;
        iArr2[i2] = fontType;
    }

    public void setStrokeWidth(float width) {
        this.mArray[this.mPos] = 5;
        this.mPos++;
        this.mArray[this.mPos] = Float.floatToRawIntBits(width);
        this.mPos++;
    }

    public void setColor(int color) {
        this.mArray[this.mPos] = 4;
        this.mPos++;
        this.mArray[this.mPos] = color;
        this.mPos++;
    }

    public void setColorId(int color) {
        this.mArray[this.mPos] = 19;
        this.mPos++;
        this.mArray[this.mPos] = color;
        this.mPos++;
    }

    public void setStrokeCap(int cap) {
        this.mArray[this.mPos] = (cap << 16) | 7;
        this.mPos++;
    }

    public void setStyle(int style) {
        this.mArray[this.mPos] = (style << 16) | 8;
        this.mPos++;
    }

    public void setShader(int shaderId) {
        this.mArray[this.mPos] = 9;
        this.mPos++;
        this.mArray[this.mPos] = shaderId;
        this.mPos++;
    }

    public void setAlpha(float alpha) {
        this.mArray[this.mPos] = 12;
        this.mPos++;
        this.mArray[this.mPos] = Float.floatToRawIntBits(alpha);
        this.mPos++;
    }

    public void setStrokeMiter(float miter) {
        this.mArray[this.mPos] = 6;
        this.mPos++;
        this.mArray[this.mPos] = Float.floatToRawIntBits(miter);
        this.mPos++;
    }

    public void setStrokeJoin(int join) {
        this.mArray[this.mPos] = (join << 16) | 15;
        this.mPos++;
    }

    public void setFilterBitmap(boolean filter) {
        this.mArray[this.mPos] = (filter ? 65536 : 0) | 17;
        this.mPos++;
    }

    public void setBlendMode(int blendmode) {
        this.mArray[this.mPos] = (blendmode << 16) | 18;
        this.mPos++;
    }

    public void setAntiAlias(boolean z) {
        this.mArray[this.mPos] = ((z ? 1 : 0) << 16) | 14;
        this.mPos++;
    }

    public void clear(long mask) {
    }

    public void reset() {
        this.mPos = 0;
    }

    public static String blendModeString(int mode) {
        switch (mode) {
        }
        return "null";
    }

    public void registerVars(RemoteContext context, VariableSupport support) {
        int cmd = 0;
        while (cmd < this.mPos) {
            int i = cmd + 1;
            int cmd2 = this.mArray[cmd];
            int type = 65535 & cmd2;
            switch (type) {
                case 1:
                case 5:
                case 6:
                case 12:
                    int i2 = i + 1;
                    float v = Float.intBitsToFloat(this.mArray[i]);
                    if (Float.isNaN(v)) {
                        context.listensTo(Utils.idFromNan(v), support);
                    }
                    cmd = i2;
                    break;
                case 2:
                case 3:
                case 7:
                case 8:
                case 10:
                case 14:
                case 15:
                case 17:
                case 18:
                default:
                    cmd = i;
                    break;
                case 4:
                case 9:
                case 13:
                case 16:
                    cmd = i + 1;
                    break;
                case 11:
                    cmd = callPrintGradient(cmd2, this.mArray, i, new StringBuilder());
                    break;
                case 19:
                    context.listensTo(this.mArray[i], support);
                    cmd = i + 1;
                    break;
            }
        }
    }

    public void updateVariables(RemoteContext context) {
        if (this.mOutArray == null) {
            this.mOutArray = Arrays.copyOf(this.mArray, this.mArray.length);
        } else {
            System.arraycopy(this.mArray, 0, this.mOutArray, 0, this.mArray.length);
        }
        int cmd = 0;
        while (cmd < this.mPos) {
            int i = cmd + 1;
            int cmd2 = this.mArray[cmd];
            int type = 65535 & cmd2;
            switch (type) {
                case 1:
                case 5:
                case 6:
                case 12:
                    this.mOutArray[i] = fixFloatVar(this.mArray[i], context);
                    cmd = i + 1;
                    break;
                case 2:
                case 3:
                case 7:
                case 8:
                case 10:
                case 14:
                case 15:
                case 17:
                case 18:
                default:
                    cmd = i;
                    break;
                case 4:
                case 9:
                case 13:
                case 16:
                    cmd = i + 1;
                    break;
                case 11:
                    cmd = updateFloatsInGradient(cmd2, this.mOutArray, this.mArray, i, context);
                    break;
                case 19:
                    this.mOutArray[i] = fixColor(this.mArray[i], context);
                    cmd = i + 1;
                    break;
            }
        }
    }

    private int fixFloatVar(int val, RemoteContext context) {
        float v = Float.intBitsToFloat(val);
        if (Float.isNaN(v)) {
            int id = Utils.idFromNan(v);
            return Float.floatToRawIntBits(context.getFloat(id));
        }
        return val;
    }

    private int fixColor(int colorId, RemoteContext context) {
        int n = context.getColor(colorId);
        return n;
    }

    int updateFloatsInGradient(int cmd, int[] out, int[] array, int i, RemoteContext context) {
        int type = cmd >> 16;
        switch (type) {
            case 0:
                int ret = i + 1;
                int len = array[i];
                if (len > 0) {
                    for (int j = 0; j < len; j++) {
                        ret++;
                    }
                }
                int ret2 = ret + 1;
                int len2 = array[ret];
                if (len2 > 0) {
                    for (int j2 = 0; j2 < len2; j2++) {
                        out[ret2] = fixFloatVar(array[ret2], context);
                        ret2++;
                    }
                }
                int j3 = array[ret2];
                out[ret2] = fixFloatVar(j3, context);
                int ret3 = ret2 + 1;
                out[ret3] = fixFloatVar(array[ret3], context);
                int ret4 = ret3 + 1;
                out[ret4] = fixFloatVar(array[ret4], context);
                int ret5 = ret4 + 1;
                out[ret5] = fixFloatVar(array[ret5], context);
                return ret5 + 1 + 1;
            case 1:
                int ret6 = i + 1;
                int len3 = array[i];
                if (len3 > 0) {
                    for (int j4 = 0; j4 < len3; j4++) {
                        ret6++;
                    }
                }
                int ret7 = ret6 + 1;
                int len4 = array[ret6];
                if (len4 > 0) {
                    for (int j5 = 0; j5 < len4; j5++) {
                        out[ret7] = fixFloatVar(array[ret7], context);
                        ret7++;
                    }
                }
                int j6 = array[ret7];
                out[ret7] = fixFloatVar(j6, context);
                int ret8 = ret7 + 1;
                out[ret8] = fixFloatVar(array[ret8], context);
                int ret9 = ret8 + 1;
                out[ret9] = fixFloatVar(array[ret9], context);
                return ret9 + 1 + 1;
            case 2:
                int ret10 = i + 1;
                int len5 = array[i];
                if (len5 > 0) {
                    int[] colors = new int[len5];
                    int j7 = 0;
                    while (j7 < colors.length) {
                        colors[j7] = array[ret10];
                        j7++;
                        ret10++;
                    }
                }
                int ret11 = ret10 + 1;
                int len6 = array[ret10];
                if (len6 > 0) {
                    float[] stops = new float[len6];
                    for (int j8 = 0; j8 < stops.length; j8++) {
                        out[ret11] = fixFloatVar(array[ret11], context);
                        ret11++;
                    }
                }
                int j9 = array[ret11];
                out[ret11] = fixFloatVar(j9, context);
                int ret12 = ret11 + 1;
                out[ret12] = fixFloatVar(array[ret12], context);
                return ret12 + 1;
            default:
                System.err.println("gradient type unknown");
                return i;
        }
    }
}
