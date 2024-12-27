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

    public void validate(XmlPullParser parser) throws XmlPullParserException {
        int eventType = parser.getEventType();
        int depth = parser.getDepth();
        if (depth > this.mElements.size() + 1) {
            return;
        }
        if (eventType != 2) {
            if (eventType == 3 && depth == this.mElements.size()) {
                this.mElements.pop().recycle();
                return;
            } else {
                if (eventType == 1) {
                    cleanUp();
                    return;
                }
                return;
            }
        }
        String tag = parser.getName();
        if (Element.shouldValidate(tag)) {
            Element element = Element.obtain(tag);
            Element parent = this.mElements.peek();
            if (parent != null && parent.hasChild(tag)) {
                try {
                    parent.seen(element);
                } catch (SecurityException e) {
                    cleanUp();
                    throw e;
                }
            }
            this.mElements.push(element);
        }
    }

    public void validateResStrAttr(XmlPullParser parser, int index, CharSequence stringValue) {
        if (parser.getDepth() > this.mElements.size()) {
            return;
        }
        this.mElements.peek().validateResStrAttr(index, stringValue);
        if (index == 1) {
            validateComponentMetadata(stringValue.toString());
        }
    }

    public void validateStrAttr(XmlPullParser parser, String attrName, String attrValue) {
        if (parser.getDepth() > this.mElements.size()) {
            return;
        }
        this.mElements.peek().validateStrAttr(attrName, attrValue);
        if (attrName.equals("value")) {
            validateComponentMetadata(attrValue);
        }
    }

    private void validateComponentMetadata(String attrValue) {
        Element element = this.mElements.peek();
        if (element.mTag.equals("meta-data") && this.mElements.size() > 1) {
            Element element2 = this.mElements.pop();
            this.mElements.peek().validateComponentMetadata(attrValue);
            this.mElements.push(element2);
        }
    }
}
