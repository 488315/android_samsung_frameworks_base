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

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
    public static final /* synthetic */ Color m454boximpl(long j) {
        return new Color(j);
    }

    /* renamed from: convert-vNxB06k, reason: not valid java name */
    public static final long m455convertvNxB06k(long j, ColorSpace colorSpace) {
        Connector connector;
        ColorSpace m459getColorSpaceimpl = m459getColorSpaceimpl(j);
        RenderIntent.Companion.getClass();
        int i = m459getColorSpaceimpl.id;
        int i2 = colorSpace.id;
        if ((i | i2) < 0) {
            connector = ColorSpaceKt.m511createConnectorYBCOT_4(m459getColorSpaceimpl, colorSpace);
        } else {
            MutableIntObjectMap mutableIntObjectMap = ConnectorKt.Connectors;
            int i3 = i | (i2 << 6);
            Object obj = mutableIntObjectMap.get(i3);
            if (obj == null) {
                obj = ColorSpaceKt.m511createConnectorYBCOT_4(m459getColorSpaceimpl, colorSpace);
                mutableIntObjectMap.set(i3, obj);
            }
            connector = (Connector) obj;
        }
        return connector.mo512transformToColorl2rxGTc$ui_graphics_release(j);
    }

    /* renamed from: getAlpha-impl, reason: not valid java name */
    public static final float m457getAlphaimpl(long j) {
        float ulongToDouble;
        float f;
        long j2 = 63 & j;
        int i = ULong.$r8$clinit;
        if (j2 == 0) {
            ulongToDouble = (float) UnsignedKt.ulongToDouble((j >>> 56) & 255);
            f = 255.0f;
        } else {
            ulongToDouble = (float) UnsignedKt.ulongToDouble((j >>> 6) & 1023);
            f = 1023.0f;
        }
        return ulongToDouble / f;
    }

    /* renamed from: getBlue-impl, reason: not valid java name */
    public static final float m458getBlueimpl(long j) {
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
                float intBitsToFloat = Float.intBitsToFloat(i7 + 1056964608) - Float16Kt.Fp32DenormalFloat;
                return i5 == 0 ? intBitsToFloat : -intBitsToFloat;
            }
            i3 = 0;
            i2 = 0;
        }
        return Float.intBitsToFloat((i3 << 23) | (i5 << 16) | i2);
    }

    /* renamed from: getColorSpace-impl, reason: not valid java name */
    public static final ColorSpace m459getColorSpaceimpl(long j) {
        ColorSpaces colorSpaces = ColorSpaces.INSTANCE;
        int i = ULong.$r8$clinit;
        colorSpaces.getClass();
        return ColorSpaces.ColorSpacesArray[(int) (j & 63)];
    }

    /* renamed from: getGreen-impl, reason: not valid java name */
    public static final float m460getGreenimpl(long j) {
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
                float intBitsToFloat = Float.intBitsToFloat(i7 + 1056964608) - Float16Kt.Fp32DenormalFloat;
                return i5 == 0 ? intBitsToFloat : -intBitsToFloat;
            }
            i3 = 0;
            i2 = 0;
        }
        return Float.intBitsToFloat((i3 << 23) | (i5 << 16) | i2);
    }

    /* renamed from: getRed-impl, reason: not valid java name */
    public static final float m461getRedimpl(long j) {
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
                float intBitsToFloat = Float.intBitsToFloat(i7 + 1056964608) - Float16Kt.Fp32DenormalFloat;
                return i5 == 0 ? intBitsToFloat : -intBitsToFloat;
            }
            i3 = 0;
            i2 = 0;
        }
        return Float.intBitsToFloat((i3 << 23) | (i5 << 16) | i2);
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m462toStringimpl(long j) {
        StringBuilder sb = new StringBuilder("Color(");
        sb.append(m461getRedimpl(j));
        sb.append(", ");
        sb.append(m460getGreenimpl(j));
        sb.append(", ");
        sb.append(m458getBlueimpl(j));
        sb.append(", ");
        sb.append(m457getAlphaimpl(j));
        sb.append(", ");
        return OpaqueKey$$ExternalSyntheticOutline0.m(sb, m459getColorSpaceimpl(j).name, ')');
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
        return m462toStringimpl(this.value);
    }
}
