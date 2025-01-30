package com.android.settingslib.widget;

import android.R;
import android.content.res.Resources;
import android.graphics.Path;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.PathShape;
import android.util.AttributeSet;
import android.util.PathParser;
import org.xmlpull.v1.XmlPullParser;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AdaptiveIconShapeDrawable extends ShapeDrawable {
    public AdaptiveIconShapeDrawable() {
    }

    @Override // android.graphics.drawable.ShapeDrawable, android.graphics.drawable.Drawable
    public final void inflate(Resources resources, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
        super.inflate(resources, xmlPullParser, attributeSet, theme);
        setShape(new PathShape(new Path(PathParser.createPathFromPathData(resources.getString(R.string.dump_heap_ready_text))), 100.0f, 100.0f));
    }

    public AdaptiveIconShapeDrawable(Resources resources) {
        setShape(new PathShape(new Path(PathParser.createPathFromPathData(resources.getString(R.string.dump_heap_ready_text))), 100.0f, 100.0f));
    }
}
