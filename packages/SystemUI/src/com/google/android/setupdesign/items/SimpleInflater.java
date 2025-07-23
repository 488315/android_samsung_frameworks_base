package com.google.android.setupdesign.items;

import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.InflateException;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public abstract class SimpleInflater {
    public final Resources resources;

    public SimpleInflater(Resources resources) {
        this.resources = resources;
    }

    public final Object createItemFromTag(String str, AttributeSet attributeSet) {
        try {
            return onCreateItem(str, attributeSet);
        } catch (InflateException e) {
            throw e;
        } catch (Exception e2) {
            throw new InflateException(attributeSet.getPositionDescription() + ": Error inflating class " + str, e2);
        }
    }

    public final Object inflate(XmlPullParser xmlPullParser) {
        int next;
        int next2;
        AttributeSet asAttributeSet = Xml.asAttributeSet(xmlPullParser);
        do {
            try {
                next = xmlPullParser.next();
                if (next == 2) {
                    break;
                }
            } catch (IOException e) {
                throw new InflateException(xmlPullParser.getPositionDescription() + ": " + e.getMessage(), e);
            } catch (XmlPullParserException e2) {
                throw new InflateException(e2.getMessage(), e2);
            }
        } while (next != 1);
        if (next != 2) {
            throw new InflateException(xmlPullParser.getPositionDescription() + ": No start tag found!");
        }
        Object createItemFromTag = createItemFromTag(xmlPullParser.getName(), asAttributeSet);
        int depth = xmlPullParser.getDepth();
        do {
            next2 = xmlPullParser.next();
            if ((next2 == 3 && xmlPullParser.getDepth() <= depth) || next2 == 1) {
                return createItemFromTag;
            }
        } while (next2 != 2);
        onAddChildItem(createItemFromTag, createItemFromTag(xmlPullParser.getName(), asAttributeSet));
        throw null;
    }

    public abstract void onAddChildItem(Object obj, Object obj2);

    public abstract Object onCreateItem(String str, AttributeSet attributeSet);
}
