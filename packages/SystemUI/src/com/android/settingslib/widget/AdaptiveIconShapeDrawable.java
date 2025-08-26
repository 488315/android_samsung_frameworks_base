package com.android.settingslib.widget;

import android.R;
import android.content.res.Resources;
import android.graphics.Path;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.util.AttributeSet;
import androidx.core.graphics.PathParser;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class AdaptiveIconShapeDrawable extends ShapeDrawable {
    public AdaptiveIconShapeDrawable() {
    }

    @Override // android.graphics.drawable.ShapeDrawable, android.graphics.drawable.Drawable
    public final void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) throws XmlPullParserException, IOException {
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        setShape(new PathShape(new Path(PathParser.createPathFromPathData(resources.getString(R.string.eventTypeCustom))), 100.0f, 100.0f));
    }

    public AdaptiveIconShapeDrawable(Resources resources) {
        setShape(new PathShape(new Path(PathParser.createPathFromPathData(resources.getString(R.string.eventTypeCustom))), 100.0f, 100.0f));
    }
}
