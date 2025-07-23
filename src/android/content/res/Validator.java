package android.content.res;

import java.util.ArrayDeque;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class Validator {
    private final ArrayDeque<Element> mElements = new ArrayDeque<>();

    private void cleanUp() {
        while (!this.mElements.isEmpty()) {
            this.mElements.pop().recycle();
        }
    }

    public void validate(XmlPullParser xmlPullParser) throws XmlPullParserException {
        int eventType = xmlPullParser.getEventType();
        int depth = xmlPullParser.getDepth();
        if (depth > this.mElements.size() + 1) {
            return;
        }
        if (eventType == 2) {
            String name = xmlPullParser.getName();
            if (Element.shouldValidate(name)) {
                Element obtain = Element.obtain(name);
                Element peek = this.mElements.peek();
                if (peek != null && peek.hasChild(name)) {
                    try {
                        peek.seen(obtain);
                    } catch (SecurityException e) {
                        cleanUp();
                        throw e;
                    }
                }
                this.mElements.push(obtain);
                return;
            }
            return;
        }
        if (eventType == 3 && depth == this.mElements.size()) {
            this.mElements.pop().recycle();
        } else if (eventType == 1) {
            cleanUp();
        }
    }

    public void validateResStrAttr(XmlPullParser xmlPullParser, int i, CharSequence charSequence) {
        if (xmlPullParser.getDepth() > this.mElements.size()) {
            return;
        }
        this.mElements.peek().validateResStrAttr(i, charSequence);
        if (i == 1) {
            validateComponentMetadata(charSequence.toString());
        }
    }

    public void validateStrAttr(XmlPullParser xmlPullParser, String str, String str2) {
        if (xmlPullParser.getDepth() > this.mElements.size()) {
            return;
        }
        this.mElements.peek().validateStrAttr(str, str2);
        if (str.equals("value")) {
            validateComponentMetadata(str2);
        }
    }

    private void validateComponentMetadata(String str) {
        if (!this.mElements.peek().mTag.equals("meta-data") || this.mElements.size() <= 1) {
            return;
        }
        Element pop = this.mElements.pop();
        this.mElements.peek().validateComponentMetadata(str);
        this.mElements.push(pop);
    }
}
