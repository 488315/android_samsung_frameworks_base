package com.google.android.setupdesign.items;

import android.content.res.Resources;
import android.util.AttributeSet;
import android.util.Xml;
import android.view.InflateException;
import java.io.IOException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

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

    public final Object inflate(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        int next;
        int next2;
        AttributeSet attributeSetAsAttributeSet = Xml.asAttributeSet(xmlPullParser);
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
        Object objCreateItemFromTag = createItemFromTag(xmlPullParser.getName(), attributeSetAsAttributeSet);
        int depth = xmlPullParser.getDepth();
        do {
            next2 = xmlPullParser.next();
            if ((next2 == 3 && xmlPullParser.getDepth() <= depth) || next2 == 1) {
                return objCreateItemFromTag;
            }
        } while (next2 != 2);
        onAddChildItem(objCreateItemFromTag, createItemFromTag(xmlPullParser.getName(), attributeSetAsAttributeSet));
        throw null;
    }

    public abstract void onAddChildItem(Object obj, Object obj2);

    public abstract Object onCreateItem(String str, AttributeSet attributeSet);
}
