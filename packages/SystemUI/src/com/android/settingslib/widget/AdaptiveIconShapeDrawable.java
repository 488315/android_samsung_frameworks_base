package com.android.settingslib.widget;

import android.R;
import android.content.res.Resources;
import android.graphics.Path;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.util.AttributeSet;
import androidx.core.graphics.PathParser;
import org.xmlpull.v1.XmlPullParser;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class AdaptiveIconShapeDrawable extends ShapeDrawable {
    public AdaptiveIconShapeDrawable() {
    }

    @Override // android.graphics.drawable.ShapeDrawable, android.graphics.drawable.Drawable
    public final void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        setShape(new PathShape(new Path(PathParser.createPathFromPathData(resources.getString(R.string.eventTypeAnniversary))), 100.0f, 100.0f));
    }

    public AdaptiveIconShapeDrawable(Resources resources) {
        setShape(new PathShape(new Path(PathParser.createPathFromPathData(resources.getString(R.string.eventTypeAnniversary))), 100.0f, 100.0f));
    }
}
