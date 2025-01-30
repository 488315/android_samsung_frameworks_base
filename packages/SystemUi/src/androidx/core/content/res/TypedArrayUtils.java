package androidx.core.content.res;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import org.xmlpull.v1.XmlPullParser;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class TypedArrayUtils {
    private TypedArrayUtils() {
    }

    public static int getAttr(int i, Context context, int i2) {
        TypedValue typedValue = new TypedValue();
        context.getTheme().resolveAttribute(i, typedValue, true);
        return typedValue.resourceId != 0 ? i : i2;
    }

    public static ComplexColorCompat getNamedComplexColor(TypedArray typedArray, XmlPullParser xmlPullParser, Resources.Theme theme, String str, int i) {
        ComplexColorCompat complexColorCompat;
        if (hasAttribute(xmlPullParser, str)) {
            TypedValue typedValue = new TypedValue();
            typedArray.getValue(i, typedValue);
            int i2 = typedValue.type;
            if (i2 >= 28 && i2 <= 31) {
                return ComplexColorCompat.from(typedValue.data);
            }
            try {
                complexColorCompat = ComplexColorCompat.createFromXml(typedArray.getResources(), typedArray.getResourceId(i, 0), theme);
            } catch (Exception e) {
                Log.e("ComplexColorCompat", "Failed to inflate ComplexColor.", e);
                complexColorCompat = null;
            }
            if (complexColorCompat != null) {
                return complexColorCompat;
            }
        }
        return ComplexColorCompat.from(0);
    }

    public static float getNamedFloat(TypedArray typedArray, XmlPullParser xmlPullParser, String str, int i, float f) {
        return !hasAttribute(xmlPullParser, str) ? f : typedArray.getFloat(i, f);
    }

    public static int getNamedInt(TypedArray typedArray, XmlPullParser xmlPullParser, String str, int i, int i2) {
        return !hasAttribute(xmlPullParser, str) ? i2 : typedArray.getInt(i, i2);
    }

    public static String getNamedString(TypedArray typedArray, XmlPullParser xmlPullParser, String str, int i) {
        if (hasAttribute(xmlPullParser, str)) {
            return typedArray.getString(i);
        }
        return null;
    }

    public static String getString(TypedArray typedArray, int i, int i2) {
        String string = typedArray.getString(i);
        return string == null ? typedArray.getString(i2) : string;
    }

    public static boolean hasAttribute(XmlPullParser xmlPullParser, String str) {
        return xmlPullParser.getAttributeValue("http://schemas.android.com/apk/res/android", str) != null;
    }

    public static TypedArray obtainAttributes(Resources resources, Resources.Theme theme, AttributeSet attributeSet, int[] iArr) {
        return theme == null ? resources.obtainAttributes(attributeSet, iArr) : theme.obtainStyledAttributes(attributeSet, iArr, 0, 0);
    }
}
