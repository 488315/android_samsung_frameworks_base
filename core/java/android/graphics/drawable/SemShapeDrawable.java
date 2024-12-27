package android.graphics.drawable;

import android.content.res.Resources;
import android.util.AttributeSet;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;

public class SemShapeDrawable extends GradientDrawable {
    static final String TAG = "SemShapeDrawable";

    @Override // android.graphics.drawable.GradientDrawable, android.graphics.drawable.Drawable
    public void inflate(
            Resources r, XmlPullParser parser, AttributeSet attrs, Resources.Theme theme)
            throws IOException, XmlPullParserException {
        super.inflate(r, parser, attrs, theme);
        setSmoothCorner(true);
    }
}
