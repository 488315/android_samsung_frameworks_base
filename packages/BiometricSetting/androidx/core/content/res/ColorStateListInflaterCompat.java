package androidx.core.content.res;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.StateSet;
import android.util.TypedValue;
import android.util.Xml;
import androidx.core.R$styleable;
import com.samsung.android.biometrics.app.setting.R;
import java.io.IOException;
import java.lang.reflect.Array;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public final class ColorStateListInflaterCompat {
    private static final ThreadLocal<TypedValue> sTempTypedValue = new ThreadLocal<>();

    public static ColorStateList createFromXml(Resources resources, XmlPullParser xmlPullParser, Resources.Theme theme) throws XmlPullParserException, IOException {
        int next;
        AttributeSet asAttributeSet = Xml.asAttributeSet(xmlPullParser);
        do {
            next = xmlPullParser.next();
            if (next == 2) {
                break;
            }
        } while (next != 1);
        if (next == 2) {
            return createFromXmlInner(resources, xmlPullParser, asAttributeSet, theme);
        }
        throw new XmlPullParserException("No start tag found");
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:36:0x009b  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00b5  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00cb  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0148  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x015b  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0120  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0129  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0122  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x00ba  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x00a0  */
    /* JADX WARN: Type inference failed for: r0v0 */
    /* JADX WARN: Type inference failed for: r0v2, types: [android.content.res.Resources] */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r4v2 */
    /* JADX WARN: Type inference failed for: r4v3, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r4v4 */
    /* JADX WARN: Type inference failed for: r7v15, types: [java.lang.Object, java.lang.Object[]] */
    /* JADX WARN: Type inference failed for: r9v22 */
    /* JADX WARN: Type inference failed for: r9v23 */
    /* JADX WARN: Type inference failed for: r9v5, types: [android.content.res.TypedArray] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static ColorStateList createFromXmlInner(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        int depth;
        int color;
        float f;
        int attributeCount;
        int i;
        boolean z;
        int alpha;
        int i2;
        ?? r0 = resources;
        String name = xmlPullParser.getName();
        if (!name.equals("selector")) {
            throw new XmlPullParserException(xmlPullParser.getPositionDescription() + ": invalid color state list tag " + name);
        }
        ?? r4 = 1;
        int depth2 = xmlPullParser.getDepth() + 1;
        int[][] iArr = new int[20][];
        int[] iArr2 = new int[20];
        int i3 = 0;
        int i4 = 0;
        while (true) {
            int next = xmlPullParser.next();
            if (next == r4 || ((depth = xmlPullParser.getDepth()) < depth2 && next == 3)) {
                break;
            }
            if (next == 2 && depth <= depth2 && xmlPullParser.getName().equals("item")) {
                int[] iArr3 = R$styleable.ColorStateListItem;
                ?? obtainAttributes = theme == null ? r0.obtainAttributes(attributeSet, iArr3) : theme.obtainStyledAttributes(attributeSet, iArr3, i3, i3);
                int resourceId = obtainAttributes.getResourceId(i3, -1);
                if (resourceId != -1) {
                    ThreadLocal<TypedValue> threadLocal = sTempTypedValue;
                    TypedValue typedValue = threadLocal.get();
                    if (typedValue == null) {
                        typedValue = new TypedValue();
                        threadLocal.set(typedValue);
                    }
                    r0.getValue(resourceId, typedValue, r4);
                    int i5 = typedValue.type;
                    if (((i5 < 28 || i5 > 31) ? i3 : r4) == 0) {
                        try {
                            color = createFromXml(r0, r0.getXml(resourceId), theme).getDefaultColor();
                        } catch (Exception unused) {
                            color = obtainAttributes.getColor(i3, -65281);
                        }
                        f = !obtainAttributes.hasValue(r4) ? obtainAttributes.getFloat(r4, 1.0f) : obtainAttributes.hasValue(3) ? obtainAttributes.getFloat(3, 1.0f) : 1.0f;
                        float f2 = !obtainAttributes.hasValue(2) ? obtainAttributes.getFloat(2, -1.0f) : obtainAttributes.getFloat(4, -1.0f);
                        obtainAttributes.recycle();
                        attributeCount = attributeSet.getAttributeCount();
                        int[] iArr4 = new int[attributeCount];
                        i = i3;
                        int i6 = i;
                        while (i < attributeCount) {
                            int attributeNameResource = attributeSet.getAttributeNameResource(i);
                            if (attributeNameResource != 16843173 && attributeNameResource != 16843551 && attributeNameResource != R.attr.alpha && attributeNameResource != R.attr.lStar) {
                                int i7 = i6 + 1;
                                if (!attributeSet.getAttributeBooleanValue(i, false)) {
                                    attributeNameResource = -attributeNameResource;
                                }
                                iArr4[i6] = attributeNameResource;
                                i6 = i7;
                            }
                            i++;
                        }
                        int[] trimStateSet = StateSet.trimStateSet(iArr4, i6);
                        z = f2 < 0.0f && f2 <= 100.0f;
                        if (f == 1.0f || z) {
                            alpha = (int) ((Color.alpha(color) * f) + 0.5f);
                            if (alpha >= 0) {
                                alpha = 0;
                            } else if (alpha > 255) {
                                alpha = 255;
                            }
                            if (z) {
                                CamColor fromColor = CamColor.fromColor(color);
                                color = CamColor.toColor(fromColor.getHue(), fromColor.getChroma(), f2);
                            }
                            color = (16777215 & color) | (alpha << 24);
                        }
                        i2 = i4 + 1;
                        if (i2 > iArr2.length) {
                            int[] iArr5 = new int[i4 <= 4 ? 8 : i4 * 2];
                            System.arraycopy(iArr2, 0, iArr5, 0, i4);
                            iArr2 = iArr5;
                        }
                        iArr2[i4] = color;
                        if (i2 > iArr.length) {
                            ?? r7 = (Object[]) Array.newInstance(iArr.getClass().getComponentType(), i4 > 4 ? i4 * 2 : 8);
                            System.arraycopy(iArr, 0, r7, 0, i4);
                            iArr = r7;
                        }
                        iArr[i4] = trimStateSet;
                        iArr = iArr;
                        i4 = i2;
                    }
                }
                color = obtainAttributes.getColor(i3, -65281);
                if (!obtainAttributes.hasValue(r4)) {
                }
                if (!obtainAttributes.hasValue(2)) {
                }
                obtainAttributes.recycle();
                attributeCount = attributeSet.getAttributeCount();
                int[] iArr42 = new int[attributeCount];
                i = i3;
                int i62 = i;
                while (i < attributeCount) {
                }
                int[] trimStateSet2 = StateSet.trimStateSet(iArr42, i62);
                if (f2 < 0.0f) {
                }
                if (f == 1.0f) {
                }
                alpha = (int) ((Color.alpha(color) * f) + 0.5f);
                if (alpha >= 0) {
                }
                if (z) {
                }
                color = (16777215 & color) | (alpha << 24);
                i2 = i4 + 1;
                if (i2 > iArr2.length) {
                }
                iArr2[i4] = color;
                if (i2 > iArr.length) {
                }
                iArr[i4] = trimStateSet2;
                iArr = iArr;
                i4 = i2;
            }
            r0 = resources;
            r4 = 1;
            i3 = 0;
        }
        int[] iArr6 = new int[i4];
        int[][] iArr7 = new int[i4][];
        System.arraycopy(iArr2, 0, iArr6, 0, i4);
        System.arraycopy(iArr, 0, iArr7, 0, i4);
        return new ColorStateList(iArr7, iArr6);
    }
}
