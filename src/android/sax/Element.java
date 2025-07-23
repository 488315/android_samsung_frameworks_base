package android.sax;

import android.media.MediaMetrics;
import java.util.ArrayList;
import org.xml.sax.Locator;
import org.xml.sax.SAXParseException;

/* loaded from: classes3.dex */
public class Element {
    Children children;
    final int depth;
    EndElementListener endElementListener;
    EndTextElementListener endTextElementListener;
    final String localName;
    final Element parent;
    ArrayList<Element> requiredChilden;
    StartElementListener startElementListener;
    final String uri;
    boolean visited;

    Element(Element element, String str, String str2, int i) {
        this.parent = element;
        this.uri = str;
        this.localName = str2;
        this.depth = i;
    }

    public Element getChild(String str) {
        return getChild("", str);
    }

    public Element getChild(String str, String str2) {
        if (this.endTextElementListener != null) {
            throw new IllegalStateException("This element already has an end text element listener. It cannot have children.");
        }
        if (this.children == null) {
            this.children = new Children();
        }
        return this.children.getOrCreate(this, str, str2);
    }

    public Element requireChild(String str) {
        return requireChild("", str);
    }

    public Element requireChild(String str, String str2) {
        Element child = getChild(str, str2);
        ArrayList<Element> arrayList = this.requiredChilden;
        if (arrayList == null) {
            ArrayList<Element> arrayList2 = new ArrayList<>();
            this.requiredChilden = arrayList2;
            arrayList2.add(child);
            return child;
        }
        if (!arrayList.contains(child)) {
            this.requiredChilden.add(child);
        }
        return child;
    }

    public void setElementListener(ElementListener elementListener) {
        setStartElementListener(elementListener);
        setEndElementListener(elementListener);
    }

    public void setTextElementListener(TextElementListener textElementListener) {
        setStartElementListener(textElementListener);
        setEndTextElementListener(textElementListener);
    }

    public void setStartElementListener(StartElementListener startElementListener) {
        if (this.startElementListener != null) {
            throw new IllegalStateException("Start element listener has already been set.");
        }
        this.startElementListener = startElementListener;
    }

    public void setEndElementListener(EndElementListener endElementListener) {
        if (this.endElementListener != null) {
            throw new IllegalStateException("End element listener has already been set.");
        }
        this.endElementListener = endElementListener;
    }

    public void setEndTextElementListener(EndTextElementListener endTextElementListener) {
        if (this.endTextElementListener != null) {
            throw new IllegalStateException("End text element listener has already been set.");
        }
        if (this.children != null) {
            throw new IllegalStateException("This element already has children. It cannot have an end text element listener.");
        }
        this.endTextElementListener = endTextElementListener;
    }

    public String toString() {
        return toString(this.uri, this.localName);
    }

    static String toString(String str, String str2) {
        StringBuilder sb = new StringBuilder("'");
        if (!str.equals("")) {
            str2 = str + ":" + str2;
        }
        sb.append(str2);
        sb.append("'");
        return sb.toString();
    }

    void resetRequiredChildren() {
        ArrayList<Element> arrayList = this.requiredChilden;
        if (arrayList != null) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                arrayList.get(size).visited = false;
            }
        }
    }

    void checkRequiredChildren(Locator locator) throws SAXParseException {
        ArrayList<Element> arrayList = this.requiredChilden;
        if (arrayList != null) {
            for (int size = arrayList.size() - 1; size >= 0; size--) {
                Element element = arrayList.get(size);
                if (!element.visited) {
                    throw new BadXmlException("Element named " + this + " is missing required child element named " + element + MediaMetrics.SEPARATOR, locator);
                }
            }
        }
    }
}
