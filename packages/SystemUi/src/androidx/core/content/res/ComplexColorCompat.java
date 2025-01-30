package androidx.core.content.res;

import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.content.res.XmlResourceParser;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.util.Xml;
import androidx.core.R$styleable;
import java.util.ArrayList;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ComplexColorCompat {
    public int mColor;
    public final ColorStateList mColorStateList;
    public final Shader mShader;

    private ComplexColorCompat(Shader shader, ColorStateList colorStateList, int i) {
        this.mShader = shader;
        this.mColorStateList = colorStateList;
        this.mColor = i;
    }

    /* JADX WARN: Code restructure failed: missing block: B:36:0x0184, code lost:
    
        if (r8.size() <= 0) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0186, code lost:
    
        r0 = new androidx.core.content.res.GradientColorInflaterCompat$ColorStops(r8, r15);
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x018d, code lost:
    
        if (r0 == null) goto L58;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x0190, code lost:
    
        if (r19 == false) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:40:0x0192, code lost:
    
        r0 = new androidx.core.content.res.GradientColorInflaterCompat$ColorStops(r5, r10, r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x0198, code lost:
    
        r0 = new androidx.core.content.res.GradientColorInflaterCompat$ColorStops(r5, r6);
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x019e, code lost:
    
        if (r11 == 1) goto L73;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x01a1, code lost:
    
        if (r11 == 2) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x01a3, code lost:
    
        r4 = r0.mColors;
        r0 = r0.mOffsets;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x01a9, code lost:
    
        if (r7 == 1) goto L70;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x01ab, code lost:
    
        if (r7 == 2) goto L69;
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x01ad, code lost:
    
        r1 = android.graphics.Shader.TileMode.CLAMP;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x01b5, code lost:
    
        r3 = new android.graphics.LinearGradient(r12, r26, r25, r15, r4, r0, r1);
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0209, code lost:
    
        return new androidx.core.content.res.ComplexColorCompat(r3, null, 0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x01b0, code lost:
    
        r1 = android.graphics.Shader.TileMode.MIRROR;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x01b3, code lost:
    
        r1 = android.graphics.Shader.TileMode.REPEAT;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x01c6, code lost:
    
        r3 = new android.graphics.SweepGradient(r7, r9, r0.mColors, r0.mOffsets);
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x01db, code lost:
    
        if (r8 <= 0.0f) goto L85;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x01dd, code lost:
    
        r1 = r0.mColors;
        r0 = r0.mOffsets;
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x01e4, code lost:
    
        if (r7 == 1) goto L81;
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x01e7, code lost:
    
        if (r7 == 2) goto L80;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x01e9, code lost:
    
        r5 = android.graphics.Shader.TileMode.CLAMP;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x01f1, code lost:
    
        r3 = new android.graphics.RadialGradient(r7, r9, r8, r1, r0, r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x01ec, code lost:
    
        r5 = android.graphics.Shader.TileMode.MIRROR;
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x01ef, code lost:
    
        r5 = android.graphics.Shader.TileMode.REPEAT;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0211, code lost:
    
        throw new org.xmlpull.v1.XmlPullParserException("<gradient> tag requires 'gradientRadius' attribute with radial type");
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x018c, code lost:
    
        r0 = null;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static ComplexColorCompat createFromXml(Resources resources, int i, Resources.Theme theme) {
        int next;
        float f;
        XmlResourceParser xml = resources.getXml(i);
        AttributeSet asAttributeSet = Xml.asAttributeSet(xml);
        do {
            next = xml.next();
            if (next == 2) {
                break;
            }
        } while (next != 1);
        if (next != 2) {
            throw new XmlPullParserException("No start tag found");
        }
        String name = xml.getName();
        name.getClass();
        if (!name.equals("gradient")) {
            if (name.equals("selector")) {
                ColorStateList createFromXmlInner = ColorStateListInflaterCompat.createFromXmlInner(resources, theme, asAttributeSet, xml);
                return new ComplexColorCompat(null, createFromXmlInner, createFromXmlInner.getDefaultColor());
            }
            throw new XmlPullParserException(xml.getPositionDescription() + ": unsupported complex color tag " + name);
        }
        String name2 = xml.getName();
        if (!name2.equals("gradient")) {
            throw new XmlPullParserException(xml.getPositionDescription() + ": invalid gradient color tag " + name2);
        }
        TypedArray obtainAttributes = TypedArrayUtils.obtainAttributes(resources, theme, asAttributeSet, R$styleable.GradientColor);
        float namedFloat = TypedArrayUtils.getNamedFloat(obtainAttributes, xml, "startX", 8, 0.0f);
        float namedFloat2 = TypedArrayUtils.getNamedFloat(obtainAttributes, xml, "startY", 9, 0.0f);
        float namedFloat3 = TypedArrayUtils.getNamedFloat(obtainAttributes, xml, "endX", 10, 0.0f);
        float namedFloat4 = TypedArrayUtils.getNamedFloat(obtainAttributes, xml, "endY", 11, 0.0f);
        float namedFloat5 = TypedArrayUtils.getNamedFloat(obtainAttributes, xml, "centerX", 3, 0.0f);
        float namedFloat6 = TypedArrayUtils.getNamedFloat(obtainAttributes, xml, "centerY", 4, 0.0f);
        int namedInt = TypedArrayUtils.getNamedInt(obtainAttributes, xml, "type", 2, 0);
        int color = !TypedArrayUtils.hasAttribute(xml, "startColor") ? 0 : obtainAttributes.getColor(0, 0);
        boolean hasAttribute = TypedArrayUtils.hasAttribute(xml, "centerColor");
        int color2 = !TypedArrayUtils.hasAttribute(xml, "centerColor") ? 0 : obtainAttributes.getColor(7, 0);
        int color3 = !TypedArrayUtils.hasAttribute(xml, "endColor") ? 0 : obtainAttributes.getColor(1, 0);
        int namedInt2 = TypedArrayUtils.getNamedInt(obtainAttributes, xml, "tileMode", 6, 0);
        float namedFloat7 = TypedArrayUtils.getNamedFloat(obtainAttributes, xml, "gradientRadius", 5, 0.0f);
        obtainAttributes.recycle();
        int depth = xml.getDepth() + 1;
        ArrayList arrayList = new ArrayList(20);
        ArrayList arrayList2 = new ArrayList(20);
        while (true) {
            int next2 = xml.next();
            float f2 = namedFloat3;
            if (next2 == 1) {
                f = namedFloat2;
                break;
            }
            int depth2 = xml.getDepth();
            f = namedFloat2;
            if (depth2 < depth && next2 == 3) {
                break;
            }
            if (next2 == 2 && depth2 <= depth && xml.getName().equals("item")) {
                TypedArray obtainAttributes2 = TypedArrayUtils.obtainAttributes(resources, theme, asAttributeSet, R$styleable.GradientColorItem);
                boolean hasValue = obtainAttributes2.hasValue(0);
                boolean hasValue2 = obtainAttributes2.hasValue(1);
                if (!hasValue || !hasValue2) {
                    break;
                }
                int color4 = obtainAttributes2.getColor(0, 0);
                float f3 = obtainAttributes2.getFloat(1, 0.0f);
                obtainAttributes2.recycle();
                arrayList2.add(Integer.valueOf(color4));
                arrayList.add(Float.valueOf(f3));
            }
            namedFloat3 = f2;
            namedFloat2 = f;
        }
        throw new XmlPullParserException(xml.getPositionDescription() + ": <item> tag requires a 'color' attribute and a 'offset' attribute!");
    }

    public static ComplexColorCompat from(int i) {
        return new ComplexColorCompat(null, null, i);
    }

    public final boolean isStateful() {
        ColorStateList colorStateList;
        return this.mShader == null && (colorStateList = this.mColorStateList) != null && colorStateList.isStateful();
    }
}
