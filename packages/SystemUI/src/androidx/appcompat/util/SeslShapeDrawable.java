package androidx.appcompat.util;

import android.content.res.Resources;
import android.graphics.drawable.GradientDrawable;
import android.util.AttributeSet;
import android.util.Log;
import androidx.reflect.SeslBaseReflector;
import java.io.IOException;
import java.lang.reflect.Method;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class SeslShapeDrawable extends GradientDrawable {
    @Override // android.graphics.drawable.GradientDrawable, android.graphics.drawable.Drawable
    public final void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        Method declaredMethod = SeslBaseReflector.getDeclaredMethod(GradientDrawable.class, "setSmoothCorner", Boolean.TYPE);
        if (declaredMethod == null) {
            Log.w("SeslShapeDrawable", "This API is not supported by the platform.");
        } else {
            SeslBaseReflector.invoke(this, declaredMethod, Boolean.TRUE);
        }
    }
}
