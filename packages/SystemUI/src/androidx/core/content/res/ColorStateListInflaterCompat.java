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
import java.io.IOException;
import java.lang.reflect.Array;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public final class ColorStateListInflaterCompat {
    public static final ThreadLocal sTempTypedValue = new ThreadLocal();

    private ColorStateListInflaterCompat() {
    }

    public static ColorStateList createFromXml(Resources resources, XmlPullParser xmlPullParser, Resources.Theme theme) {
        int next;
        AttributeSet attributeSetAsAttributeSet = Xml.asAttributeSet(xmlPullParser);
        do {
            next = xmlPullParser.next();
            if (next == 2) {
                break;
            }
        } while (next != 1);
        if (next == 2) {
            return createFromXmlInner(resources, theme, attributeSetAsAttributeSet, xmlPullParser);
        }
        throw new XmlPullParserException("No start tag found");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:33:0x008f  */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v2, types: [android.content.res.Resources] */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v46 */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r1v23, types: [java.lang.Object, java.lang.Object[]] */
    /* JADX WARN: Type inference failed for: r4v2 */
    /* JADX WARN: Type inference failed for: r4v3, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r4v4 */
    /* JADX WARN: Type inference failed for: r4v5 */
    /* JADX WARN: Type inference failed for: r4v9 */
    /* JADX WARN: Type inference failed for: r9v18 */
    /* JADX WARN: Type inference failed for: r9v19 */
    /* JADX WARN: Type inference failed for: r9v5, types: [android.content.res.TypedArray] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static ColorStateList createFromXmlInner(Resources resources, Resources.Theme theme, AttributeSet attributeSet, XmlPullParser xmlPullParser) throws XmlPullParserException, Resources.NotFoundException, IOException {
        int depth;
        int color;
        int[] iArr;
        int i;
        int iIntFromLStar;
        float f;
        int i2;
        float fCbrt;
        ?? r0 = resources;
        Resources.Theme theme2 = theme;
        AttributeSet attributeSet2 = attributeSet;
        String name = xmlPullParser.getName();
        if (!name.equals("selector")) {
            throw new XmlPullParserException(xmlPullParser.getPositionDescription() + ": invalid color state list tag " + name);
        }
        boolean z = 1;
        int depth2 = xmlPullParser.getDepth() + 1;
        int[][] iArr2 = new int[20][];
        int[] iArr3 = new int[20];
        int i3 = 0;
        int i4 = 0;
        while (true) {
            int next = xmlPullParser.next();
            if (next == z || ((depth = xmlPullParser.getDepth()) < depth2 && next == 3)) {
                break;
            }
            if (next == 2 && depth <= depth2 && xmlPullParser.getName().equals("item")) {
                int[] iArr4 = R$styleable.ColorStateListItem;
                ?? ObtainAttributes = theme2 == null ? r0.obtainAttributes(attributeSet2, iArr4) : theme2.obtainStyledAttributes(attributeSet2, iArr4, i3, i3);
                int resourceId = ObtainAttributes.getResourceId(i3, -1);
                if (resourceId != -1) {
                    ThreadLocal threadLocal = sTempTypedValue;
                    TypedValue typedValue = (TypedValue) threadLocal.get();
                    if (typedValue == null) {
                        typedValue = new TypedValue();
                        threadLocal.set(typedValue);
                    }
                    r0.getValue(resourceId, typedValue, z);
                    int i5 = typedValue.type;
                    if (i5 < 28 || i5 > 31) {
                        try {
                            color = createFromXml(r0, r0.getXml(resourceId), theme2).getDefaultColor();
                        } catch (Exception unused) {
                            color = ObtainAttributes.getColor(i3, -65281);
                        }
                    } else {
                        color = ObtainAttributes.getColor(i3, -65281);
                    }
                    float f2 = ObtainAttributes.hasValue(z) ? ObtainAttributes.getFloat(z, 1.0f) : ObtainAttributes.hasValue(3) ? ObtainAttributes.getFloat(3, 1.0f) : 1.0f;
                    char c = z;
                    float f3 = ObtainAttributes.hasValue(2) ? ObtainAttributes.getFloat(2, -1.0f) : ObtainAttributes.getFloat(4, -1.0f);
                    ObtainAttributes.recycle();
                    int attributeCount = attributeSet2.getAttributeCount();
                    int[] iArr5 = new int[attributeCount];
                    int i6 = i3;
                    int i7 = i6;
                    while (i6 < attributeCount) {
                        int attributeNameResource = attributeSet2.getAttributeNameResource(i6);
                        if (attributeNameResource != 16843173 && attributeNameResource != 16843551 && attributeNameResource != R.attr.alpha && attributeNameResource != R.attr.lStar) {
                            int i8 = i7 + 1;
                            if (!attributeSet2.getAttributeBooleanValue(i6, false)) {
                                attributeNameResource = -attributeNameResource;
                            }
                            iArr5[i7] = attributeNameResource;
                            i7 = i8;
                        }
                        i6++;
                    }
                    int[] iArrTrimStateSet = StateSet.trimStateSet(iArr5, i7);
                    float f4 = 100.0f;
                    char c2 = (f3 < 0.0f || f3 > 100.0f) ? (char) 0 : c;
                    if (f2 == 1.0f && c2 == 0) {
                        iArr = iArrTrimStateSet;
                        i = depth2;
                    } else {
                        int iClamp = MathUtils.clamp((int) ((Color.alpha(color) * f2) + 0.5f), 0, 255);
                        if (c2 != 0) {
                            CamColor camColorFromColor = CamColor.fromColor(color);
                            ViewingConditions viewingConditions = ViewingConditions.DEFAULT;
                            float f5 = camColorFromColor.mChroma;
                            if (f5 < 1.0d || Math.round(f3) <= 0.0d || Math.round(f3) >= 100.0d) {
                                iArr = iArrTrimStateSet;
                                i = depth2;
                                iIntFromLStar = CamUtils.intFromLStar(f3);
                            } else {
                                float f6 = camColorFromColor.mHue;
                                float fMin = f6 < 0.0f ? 0.0f : Math.min(360.0f, f6);
                                float f7 = 0.0f;
                                float f8 = f5;
                                char c3 = c;
                                CamColor camColor = null;
                                while (true) {
                                    if (Math.abs(f7 - f5) >= 0.4f) {
                                        float f9 = 1000.0f;
                                        float f10 = f4;
                                        float f11 = 0.0f;
                                        float f12 = 1000.0f;
                                        CamColor camColor2 = null;
                                        while (true) {
                                            if (Math.abs(f11 - f10) <= 0.01f) {
                                                iArr = iArrTrimStateSet;
                                                i = depth2;
                                                f = f4;
                                                break;
                                            }
                                            f = f4;
                                            float f13 = ((f10 - f11) / 2.0f) + f11;
                                            iArr = iArrTrimStateSet;
                                            int iViewed = CamColor.fromJch(f13, f8, fMin).viewed(ViewingConditions.DEFAULT);
                                            float fLinearized = CamUtils.linearized(Color.red(iViewed));
                                            float fLinearized2 = CamUtils.linearized(Color.green(iViewed));
                                            float fLinearized3 = CamUtils.linearized(Color.blue(iViewed));
                                            float[] fArr = CamUtils.SRGB_TO_XYZ[c];
                                            float f14 = ((fLinearized3 * fArr[2]) + ((fLinearized2 * fArr[c]) + (fLinearized * fArr[0]))) / f;
                                            if (f14 <= 0.008856452f) {
                                                fCbrt = f14 * 903.2963f;
                                                i2 = iViewed;
                                            } else {
                                                i2 = iViewed;
                                                fCbrt = (((float) Math.cbrt(f14)) * 116.0f) - 16.0f;
                                            }
                                            float fAbs = Math.abs(f3 - fCbrt);
                                            if (fAbs < 0.2f) {
                                                CamColor camColorFromColor2 = CamColor.fromColor(i2);
                                                CamColor camColorFromJch = CamColor.fromJch(camColorFromColor2.mJ, camColorFromColor2.mChroma, fMin);
                                                float f15 = camColorFromColor2.mJstar - camColorFromJch.mJstar;
                                                float f16 = camColorFromColor2.mAstar - camColorFromJch.mAstar;
                                                float f17 = camColorFromColor2.mBstar - camColorFromJch.mBstar;
                                                i = depth2;
                                                float fPow = (float) (Math.pow(Math.sqrt((f17 * f17) + (f16 * f16) + (f15 * f15)), 0.63d) * 1.41d);
                                                if (fPow <= 1.0f) {
                                                    f12 = fPow;
                                                    f9 = fAbs;
                                                    camColor2 = camColorFromColor2;
                                                }
                                            } else {
                                                i = depth2;
                                            }
                                            if (f9 == 0.0f && f12 == 0.0f) {
                                                break;
                                            }
                                            if (fCbrt < f3) {
                                                f11 = f13;
                                            } else {
                                                f10 = f13;
                                            }
                                            f4 = f;
                                            iArrTrimStateSet = iArr;
                                            depth2 = i;
                                        }
                                        CamColor camColor3 = camColor2;
                                        if (c3 == 0) {
                                            if (camColor3 == null) {
                                                f5 = f8;
                                            } else {
                                                camColor = camColor3;
                                                f7 = f8;
                                            }
                                            f8 = ((f5 - f7) / 2.0f) + f7;
                                            f4 = f;
                                            iArrTrimStateSet = iArr;
                                            depth2 = i;
                                        } else {
                                            if (camColor3 != null) {
                                                iIntFromLStar = camColor3.viewed(viewingConditions);
                                                break;
                                            }
                                            f8 = ((f5 - f7) / 2.0f) + f7;
                                            f4 = f;
                                            iArrTrimStateSet = iArr;
                                            depth2 = i;
                                            c3 = 0;
                                        }
                                    } else {
                                        iArr = iArrTrimStateSet;
                                        i = depth2;
                                        iIntFromLStar = camColor == null ? CamUtils.intFromLStar(f3) : camColor.viewed(viewingConditions);
                                    }
                                }
                            }
                            color = iIntFromLStar;
                        } else {
                            iArr = iArrTrimStateSet;
                            i = depth2;
                        }
                        color = (16777215 & color) | (iClamp << 24);
                    }
                    int i9 = i4 + 1;
                    if (i9 > iArr3.length) {
                        int[] iArr6 = new int[i4 <= 4 ? 8 : i4 * 2];
                        System.arraycopy(iArr3, 0, iArr6, 0, i4);
                        iArr3 = iArr6;
                    }
                    iArr3[i4] = color;
                    if (i9 > iArr2.length) {
                        ?? r1 = (Object[]) Array.newInstance(iArr2.getClass().getComponentType(), i4 > 4 ? i4 * 2 : 8);
                        System.arraycopy(iArr2, 0, r1, 0, i4);
                        iArr2 = r1;
                    }
                    iArr2[i4] = iArr;
                    iArr2 = iArr2;
                    theme2 = theme;
                    attributeSet2 = attributeSet;
                    i4 = i9;
                    z = c;
                    depth2 = i;
                    i3 = 0;
                    r0 = resources;
                }
            } else {
                r0 = resources;
                theme2 = theme;
                attributeSet2 = attributeSet;
                z = z;
                depth2 = depth2;
                i3 = 0;
            }
        }
        int[] iArr7 = new int[i4];
        int[][] iArr8 = new int[i4][];
        System.arraycopy(iArr3, 0, iArr7, 0, i4);
        System.arraycopy(iArr2, 0, iArr8, 0, i4);
        return new ColorStateList(iArr8, iArr7);
    }
}
