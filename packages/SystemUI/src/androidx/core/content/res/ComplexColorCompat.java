package androidx.core.content.res;

import android.content.res.ColorStateList;
import android.graphics.Shader;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* JADX WARN: Code restructure failed: missing block: B:122:0x01e6, code lost:
    
        throw new org.xmlpull.v1.XmlPullParserException(r3.getPositionDescription() + ": <item> tag requires a 'color' attribute and a 'offset' attribute!");
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static androidx.core.content.res.ComplexColorCompat createFromXml(int r29, android.content.res.Resources.Theme r30, android.content.res.Resources r31) {
        /*
            Method dump skipped, instructions count: 670
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.core.content.res.ComplexColorCompat.createFromXml(int, android.content.res.Resources$Theme, android.content.res.Resources):androidx.core.content.res.ComplexColorCompat");
    }

    public static ComplexColorCompat from(int i) {
        return new ComplexColorCompat(null, null, i);
    }

    public final boolean isStateful() {
        ColorStateList colorStateList;
        return this.mShader == null && (colorStateList = this.mColorStateList) != null && colorStateList.isStateful();
    }
}
