package androidx.core.content.res;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.StateSet;
import android.util.TypedValue;
import android.util.Xml;
import androidx.core.R$styleable;
import androidx.core.math.MathUtils;
import com.android.systemui.R;
import java.lang.reflect.Array;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ColorStateListInflaterCompat {
    public static final ThreadLocal sTempTypedValue = new ThreadLocal();

    private ColorStateListInflaterCompat() {
    }

    public static ColorStateList createFromXml(Resources resources, XmlPullParser xmlPullParser, Resources.Theme theme) {
        int next;
        AttributeSet asAttributeSet = Xml.asAttributeSet(xmlPullParser);
        do {
            next = xmlPullParser.next();
            if (next == 2) {
                break;
            }
        } while (next != 1);
        if (next == 2) {
            return createFromXmlInner(resources, theme, asAttributeSet, xmlPullParser);
        }
        throw new XmlPullParserException("No start tag found");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:152:0x02d3  */
    /* JADX WARN: Removed duplicated region for block: B:154:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x00a1  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x009c  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00b6  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x02e7  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x02fa  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x012e  */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v2, types: [android.content.res.Resources] */
    /* JADX WARN: Type inference failed for: r0v21 */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r1v22, types: [java.lang.Object, java.lang.Object[]] */
    /* JADX WARN: Type inference failed for: r4v2 */
    /* JADX WARN: Type inference failed for: r4v3, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r4v4 */
    /* JADX WARN: Type inference failed for: r4v5 */
    /* JADX WARN: Type inference failed for: r4v9 */
    /* JADX WARN: Type inference failed for: r9v28 */
    /* JADX WARN: Type inference failed for: r9v29 */
    /* JADX WARN: Type inference failed for: r9v5, types: [android.content.res.TypedArray] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static ColorStateList createFromXmlInner(Resources resources, Resources.Theme theme, AttributeSet attributeSet, XmlPullParser xmlPullParser) {
        int depth;
        int color;
        float f;
        int attributeCount;
        int i;
        boolean z;
        int[] iArr;
        int i2;
        boolean z2;
        float f2;
        float f3;
        float f4;
        float cbrt;
        float f5;
        int i3;
        ?? r0 = resources;
        Resources.Theme theme2 = theme;
        AttributeSet attributeSet2 = attributeSet;
        String name = xmlPullParser.getName();
        if (!name.equals("selector")) {
            throw new XmlPullParserException(xmlPullParser.getPositionDescription() + ": invalid color state list tag " + name);
        }
        boolean z3 = 1;
        int depth2 = xmlPullParser.getDepth() + 1;
        int[][] iArr2 = new int[20][];
        int[] iArr3 = new int[20];
        int i4 = 0;
        int i5 = 0;
        while (true) {
            int next = xmlPullParser.next();
            if (next == z3 || ((depth = xmlPullParser.getDepth()) < depth2 && next == 3)) {
                break;
            }
            if (next == 2 && depth <= depth2 && xmlPullParser.getName().equals("item")) {
                int[] iArr4 = R$styleable.ColorStateListItem;
                ?? obtainAttributes = theme2 == null ? r0.obtainAttributes(attributeSet2, iArr4) : theme2.obtainStyledAttributes(attributeSet2, iArr4, i4, i4);
                int resourceId = obtainAttributes.getResourceId(i4, -1);
                if (resourceId != -1) {
                    ThreadLocal threadLocal = sTempTypedValue;
                    TypedValue typedValue = (TypedValue) threadLocal.get();
                    if (typedValue == null) {
                        typedValue = new TypedValue();
                        threadLocal.set(typedValue);
                    }
                    r0.getValue(resourceId, typedValue, z3);
                    int i6 = typedValue.type;
                    if (((i6 < 28 || i6 > 31) ? i4 : z3) == 0) {
                        try {
                            color = createFromXml(r0, r0.getXml(resourceId), theme2).getDefaultColor();
                        } catch (Exception unused) {
                            color = obtainAttributes.getColor(i4, -65281);
                        }
                        f = !obtainAttributes.hasValue(z3) ? obtainAttributes.getFloat(z3, 1.0f) : obtainAttributes.hasValue(3) ? obtainAttributes.getFloat(3, 1.0f) : 1.0f;
                        float f6 = !obtainAttributes.hasValue(2) ? obtainAttributes.getFloat(2, -1.0f) : obtainAttributes.getFloat(4, -1.0f);
                        obtainAttributes.recycle();
                        attributeCount = attributeSet.getAttributeCount();
                        int[] iArr5 = new int[attributeCount];
                        int i7 = i4;
                        for (i = i7; i < attributeCount; i++) {
                            int attributeNameResource = attributeSet2.getAttributeNameResource(i);
                            if (attributeNameResource != 16843173 && attributeNameResource != 16843551 && attributeNameResource != R.attr.alpha && attributeNameResource != R.attr.lStar) {
                                int i8 = i7 + 1;
                                if (!attributeSet2.getAttributeBooleanValue(i, false)) {
                                    attributeNameResource = -attributeNameResource;
                                }
                                iArr5[i7] = attributeNameResource;
                                i7 = i8;
                            }
                        }
                        int[] trimStateSet = StateSet.trimStateSet(iArr5, i7);
                        float f7 = 0.0f;
                        float f8 = 100.0f;
                        z = f6 < 0.0f && f6 <= 100.0f;
                        if (f == 1.0f || z) {
                            int clamp = MathUtils.clamp((int) ((Color.alpha(color) * f) + 0.5f), 0, 255);
                            if (z) {
                                iArr = trimStateSet;
                                i2 = depth2;
                                z2 = true;
                            } else {
                                CamColor fromColor = CamColor.fromColor(color);
                                ViewingConditions viewingConditions = ViewingConditions.DEFAULT;
                                float f9 = fromColor.mChroma;
                                if (f9 < 1.0d || Math.round(f6) <= 0.0d || Math.round(f6) >= 100.0d) {
                                    iArr = trimStateSet;
                                    i2 = depth2;
                                    z2 = true;
                                    color = CamUtils.intFromLStar(f6);
                                } else {
                                    float f10 = fromColor.mHue;
                                    float min = f10 < 0.0f ? 0.0f : Math.min(360.0f, f10);
                                    float f11 = 0.0f;
                                    float f12 = f9;
                                    CamColor camColor = null;
                                    boolean z4 = true;
                                    while (true) {
                                        if (Math.abs(f11 - f9) >= 0.4f) {
                                            float f13 = 1000.0f;
                                            float f14 = f7;
                                            float f15 = f8;
                                            float f16 = 1000.0f;
                                            CamColor camColor2 = null;
                                            while (true) {
                                                if (Math.abs(f14 - f15) <= 0.01f) {
                                                    i2 = depth2;
                                                    f2 = min;
                                                    f3 = f8;
                                                    z2 = true;
                                                    float f17 = f7;
                                                    iArr = trimStateSet;
                                                    f4 = f17;
                                                    break;
                                                }
                                                float f18 = ((f15 - f14) / 2.0f) + f14;
                                                int viewed = CamColor.fromJch(f18, f12, min).viewed(ViewingConditions.DEFAULT);
                                                float linearized = CamUtils.linearized(Color.red(viewed));
                                                float linearized2 = CamUtils.linearized(Color.green(viewed));
                                                float linearized3 = CamUtils.linearized(Color.blue(viewed));
                                                z2 = true;
                                                float[] fArr = CamUtils.SRGB_TO_XYZ[1];
                                                f3 = 100.0f;
                                                float f19 = ((linearized3 * fArr[2]) + ((linearized2 * fArr[1]) + (linearized * fArr[0]))) / 100.0f;
                                                if (f19 <= 0.008856452f) {
                                                    cbrt = f19 * 903.2963f;
                                                    i2 = depth2;
                                                } else {
                                                    i2 = depth2;
                                                    cbrt = (((float) Math.cbrt(f19)) * 116.0f) - 16.0f;
                                                }
                                                float abs = Math.abs(f6 - cbrt);
                                                if (abs < 0.2f) {
                                                    CamColor fromColor2 = CamColor.fromColor(viewed);
                                                    CamColor fromJch = CamColor.fromJch(fromColor2.f33mJ, fromColor2.mChroma, min);
                                                    f5 = f18;
                                                    float f20 = fromColor2.mJstar - fromJch.mJstar;
                                                    f2 = min;
                                                    float f21 = fromColor2.mAstar - fromJch.mAstar;
                                                    float f22 = fromColor2.mBstar - fromJch.mBstar;
                                                    double sqrt = Math.sqrt((f22 * f22) + (f21 * f21) + (f20 * f20));
                                                    iArr = trimStateSet;
                                                    float pow = (float) (Math.pow(sqrt, 0.63d) * 1.41d);
                                                    if (pow <= 1.0f) {
                                                        f16 = pow;
                                                        camColor2 = fromColor2;
                                                        f13 = abs;
                                                    }
                                                } else {
                                                    f5 = f18;
                                                    f2 = min;
                                                    iArr = trimStateSet;
                                                }
                                                f4 = 0.0f;
                                                if (f13 == 0.0f && f16 == 0.0f) {
                                                    break;
                                                }
                                                if (cbrt < f6) {
                                                    f14 = f5;
                                                } else {
                                                    f15 = f5;
                                                }
                                                f8 = 100.0f;
                                                depth2 = i2;
                                                min = f2;
                                                int[] iArr6 = iArr;
                                                f7 = 0.0f;
                                                trimStateSet = iArr6;
                                            }
                                            CamColor camColor3 = camColor2;
                                            if (!z4) {
                                                if (camColor3 == null) {
                                                    f9 = f12;
                                                    f12 = f11;
                                                } else {
                                                    camColor = camColor3;
                                                }
                                                f11 = f12;
                                                f12 = ((f9 - f12) / 2.0f) + f12;
                                            } else {
                                                if (camColor3 != null) {
                                                    color = camColor3.viewed(viewingConditions);
                                                    break;
                                                }
                                                f12 = ((f9 - f11) / 2.0f) + f11;
                                                z4 = false;
                                            }
                                            f8 = f3;
                                            depth2 = i2;
                                            min = f2;
                                            int[] iArr7 = iArr;
                                            f7 = f4;
                                            trimStateSet = iArr7;
                                        } else {
                                            iArr = trimStateSet;
                                            i2 = depth2;
                                            z2 = true;
                                            color = camColor == null ? CamUtils.intFromLStar(f6) : camColor.viewed(viewingConditions);
                                        }
                                    }
                                }
                            }
                            color = (16777215 & color) | (clamp << 24);
                        } else {
                            iArr = trimStateSet;
                            i2 = depth2;
                            z2 = true;
                        }
                        i3 = i5 + 1;
                        if (i3 > iArr3.length) {
                            int[] iArr8 = new int[i5 <= 4 ? 8 : i5 * 2];
                            System.arraycopy(iArr3, 0, iArr8, 0, i5);
                            iArr3 = iArr8;
                        }
                        iArr3[i5] = color;
                        if (i3 > iArr2.length) {
                            ?? r1 = (Object[]) Array.newInstance(iArr2.getClass().getComponentType(), i5 > 4 ? i5 * 2 : 8);
                            System.arraycopy(iArr2, 0, r1, 0, i5);
                            iArr2 = r1;
                        }
                        iArr2[i5] = iArr;
                        iArr2 = iArr2;
                        theme2 = theme;
                        attributeSet2 = attributeSet;
                        i5 = i3;
                        z3 = z2;
                        depth2 = i2;
                        i4 = 0;
                        r0 = resources;
                    }
                }
                color = obtainAttributes.getColor(i4, -65281);
                if (!obtainAttributes.hasValue(z3)) {
                }
                if (!obtainAttributes.hasValue(2)) {
                }
                obtainAttributes.recycle();
                attributeCount = attributeSet.getAttributeCount();
                int[] iArr52 = new int[attributeCount];
                int i72 = i4;
                while (i < attributeCount) {
                }
                int[] trimStateSet2 = StateSet.trimStateSet(iArr52, i72);
                float f72 = 0.0f;
                float f82 = 100.0f;
                if (f6 < 0.0f) {
                }
                if (f == 1.0f) {
                }
                int clamp2 = MathUtils.clamp((int) ((Color.alpha(color) * f) + 0.5f), 0, 255);
                if (z) {
                }
                color = (16777215 & color) | (clamp2 << 24);
                i3 = i5 + 1;
                if (i3 > iArr3.length) {
                }
                iArr3[i5] = color;
                if (i3 > iArr2.length) {
                }
                iArr2[i5] = iArr;
                iArr2 = iArr2;
                theme2 = theme;
                attributeSet2 = attributeSet;
                i5 = i3;
                z3 = z2;
                depth2 = i2;
                i4 = 0;
                r0 = resources;
            } else {
                r0 = resources;
                theme2 = theme;
                attributeSet2 = attributeSet;
                z3 = z3;
                depth2 = depth2;
                i4 = 0;
            }
        }
        int[] iArr9 = new int[i5];
        int[][] iArr10 = new int[i5][];
        System.arraycopy(iArr3, 0, iArr9, 0, i5);
        System.arraycopy(iArr2, 0, iArr10, 0, i5);
        return new ColorStateList(iArr10, iArr9);
    }
}
