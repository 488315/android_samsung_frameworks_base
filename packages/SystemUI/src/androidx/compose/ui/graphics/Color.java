package androidx.compose.ui.graphics;

import androidx.collection.MutableIntObjectMap;
import androidx.compose.runtime.OpaqueKey$$ExternalSyntheticOutline0;
import androidx.compose.ui.graphics.colorspace.ColorSpace;
import androidx.compose.ui.graphics.colorspace.ColorSpaceKt;
import androidx.compose.ui.graphics.colorspace.ColorSpaces;
import androidx.compose.ui.graphics.colorspace.Connector;
import androidx.compose.ui.graphics.colorspace.ConnectorKt;
import androidx.compose.ui.graphics.colorspace.RenderIntent;
import kotlin.ULong;
import kotlin.UnsignedKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class Color {
    public static final long Blue;
    public static final long Red;
    public static final long Transparent;
    public static final long Unspecified;
    public static final long White;
    public final long value;
    public static final Companion Companion = new Companion(null);
    public static final long Black = ColorKt.Color(4278190080L);

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        ColorKt.Color(4282664004L);
        ColorKt.Color(4287137928L);
        ColorKt.Color(4291611852L);
        White = ColorKt.Color(4294967295L);
        Red = ColorKt.Color(4294901760L);
        ColorKt.Color(4278255360L);
        Blue = ColorKt.Color(4278190335L);
        ColorKt.Color(4294967040L);
        ColorKt.Color(4278255615L);
        ColorKt.Color(4294902015L);
        Transparent = ColorKt.Color(0);
        ColorSpaces.INSTANCE.getClass();
        Unspecified = ColorKt.Color(0.0f, 0.0f, 0.0f, 0.0f, ColorSpaces.Unspecified);
    }

    private /* synthetic */ Color(long j) {
        this.value = j;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ Color m456boximpl(long j) {
        return new Color(j);
    }

    /* renamed from: convert-vNxB06k, reason: not valid java name */
    public static final long m457convertvNxB06k(long j, ColorSpace colorSpace) {
        Connector connectorM513createConnectorYBCOT_4;
        ColorSpace colorSpaceM461getColorSpaceimpl = m461getColorSpaceimpl(j);
        RenderIntent.Companion.getClass();
        int i = colorSpaceM461getColorSpaceimpl.id;
        int i2 = colorSpace.id;
        if ((i | i2) < 0) {
            connectorM513createConnectorYBCOT_4 = ColorSpaceKt.m513createConnectorYBCOT_4(colorSpaceM461getColorSpaceimpl, colorSpace);
        } else {
            MutableIntObjectMap mutableIntObjectMap = ConnectorKt.Connectors;
            int i3 = i | (i2 << 6);
            Object objM513createConnectorYBCOT_4 = mutableIntObjectMap.get(i3);
            if (objM513createConnectorYBCOT_4 == null) {
                objM513createConnectorYBCOT_4 = ColorSpaceKt.m513createConnectorYBCOT_4(colorSpaceM461getColorSpaceimpl, colorSpace);
                mutableIntObjectMap.set(i3, objM513createConnectorYBCOT_4);
            }
            connectorM513createConnectorYBCOT_4 = (Connector) objM513createConnectorYBCOT_4;
        }
        return connectorM513createConnectorYBCOT_4.mo514transformToColorl2rxGTc$ui_graphics_release(j);
    }

    /* renamed from: getAlpha-impl, reason: not valid java name */
    public static final float m459getAlphaimpl(long j) {
        float fUlongToDouble;
        float f;
        long j2 = 63 & j;
        int i = ULong.$r8$clinit;
        if (j2 == 0) {
            fUlongToDouble = (float) UnsignedKt.ulongToDouble((j >>> 56) & 255);
            f = 255.0f;
        } else {
            fUlongToDouble = (float) UnsignedKt.ulongToDouble((j >>> 6) & 1023);
            f = 1023.0f;
        }
        return fUlongToDouble / f;
    }

    /* renamed from: getBlue-impl, reason: not valid java name */
    public static final float m460getBlueimpl(long j) {
        int i;
        int i2;
        int i3;
        long j2 = 63 & j;
        int i4 = ULong.$r8$clinit;
        if (j2 == 0) {
            return ((float) UnsignedKt.ulongToDouble((j >>> 32) & 255)) / 255.0f;
        }
        short s = (short) ((j >>> 16) & 65535);
        int i5 = 32768 & s;
        int i6 = ((65535 & s) >>> 10) & 31;
        int i7 = s & 1023;
        if (i6 != 0) {
            int i8 = i7 << 13;
            if (i6 == 31) {
                i = 255;
                if (i8 != 0) {
                    i8 |= 4194304;
                }
            } else {
                i = i6 + 112;
            }
            int i9 = i;
            i2 = i8;
            i3 = i9;
        } else {
            if (i7 != 0) {
                float fIntBitsToFloat = Float.intBitsToFloat(i7 + 1056964608) - Float16Kt.Fp32DenormalFloat;
                return i5 == 0 ? fIntBitsToFloat : -fIntBitsToFloat;
            }
            i3 = 0;
            i2 = 0;
        }
        return Float.intBitsToFloat((i3 << 23) | (i5 << 16) | i2);
    }

    /* renamed from: getColorSpace-impl, reason: not valid java name */
    public static final ColorSpace m461getColorSpaceimpl(long j) {
        ColorSpaces colorSpaces = ColorSpaces.INSTANCE;
        int i = ULong.$r8$clinit;
        colorSpaces.getClass();
        return ColorSpaces.ColorSpacesArray[(int) (j & 63)];
    }

    /* renamed from: getGreen-impl, reason: not valid java name */
    public static final float m462getGreenimpl(long j) {
        int i;
        int i2;
        int i3;
        long j2 = 63 & j;
        int i4 = ULong.$r8$clinit;
        if (j2 == 0) {
            return ((float) UnsignedKt.ulongToDouble((j >>> 40) & 255)) / 255.0f;
        }
        short s = (short) ((j >>> 32) & 65535);
        int i5 = 32768 & s;
        int i6 = ((65535 & s) >>> 10) & 31;
        int i7 = s & 1023;
        if (i6 != 0) {
            int i8 = i7 << 13;
            if (i6 == 31) {
                i = 255;
                if (i8 != 0) {
                    i8 |= 4194304;
                }
            } else {
                i = i6 + 112;
            }
            int i9 = i;
            i2 = i8;
            i3 = i9;
        } else {
            if (i7 != 0) {
                float fIntBitsToFloat = Float.intBitsToFloat(i7 + 1056964608) - Float16Kt.Fp32DenormalFloat;
                return i5 == 0 ? fIntBitsToFloat : -fIntBitsToFloat;
            }
            i3 = 0;
            i2 = 0;
        }
        return Float.intBitsToFloat((i3 << 23) | (i5 << 16) | i2);
    }

    /* renamed from: getRed-impl, reason: not valid java name */
    public static final float m463getRedimpl(long j) {
        int i;
        int i2;
        int i3;
        long j2 = 63 & j;
        int i4 = ULong.$r8$clinit;
        if (j2 == 0) {
            return ((float) UnsignedKt.ulongToDouble((j >>> 48) & 255)) / 255.0f;
        }
        short s = (short) ((j >>> 48) & 65535);
        int i5 = 32768 & s;
        int i6 = ((65535 & s) >>> 10) & 31;
        int i7 = s & 1023;
        if (i6 != 0) {
            int i8 = i7 << 13;
            if (i6 == 31) {
                i = 255;
                if (i8 != 0) {
                    i8 |= 4194304;
                }
            } else {
                i = i6 + 112;
            }
            int i9 = i;
            i2 = i8;
            i3 = i9;
        } else {
            if (i7 != 0) {
                float fIntBitsToFloat = Float.intBitsToFloat(i7 + 1056964608) - Float16Kt.Fp32DenormalFloat;
                return i5 == 0 ? fIntBitsToFloat : -fIntBitsToFloat;
            }
            i3 = 0;
            i2 = 0;
        }
        return Float.intBitsToFloat((i3 << 23) | (i5 << 16) | i2);
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m464toStringimpl(long j) {
        StringBuilder sb = new StringBuilder("Color(");
        sb.append(m463getRedimpl(j));
        sb.append(", ");
        sb.append(m462getGreenimpl(j));
        sb.append(", ");
        sb.append(m460getBlueimpl(j));
        sb.append(", ");
        sb.append(m459getAlphaimpl(j));
        sb.append(", ");
        return OpaqueKey$$ExternalSyntheticOutline0.m(sb, m461getColorSpaceimpl(j).name, ')');
    }

    public final boolean equals(Object obj) {
        if (obj instanceof Color) {
            return this.value == ((Color) obj).value;
        }
        return false;
    }

    public final int hashCode() {
        int i = ULong.$r8$clinit;
        return Long.hashCode(this.value);
    }

    public final String toString() {
        return m464toStringimpl(this.value);
    }
}
