package android.audio.policy.configuration.V7_0;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class Reference {
    private String name;
    private List<String> point;

    public List<String> getPoint() {
        if (this.point == null) {
            this.point = new ArrayList();
        }
        return this.point;
    }

    public String getName() {
        return this.name;
    }

    boolean hasName() {
        return this.name != null;
    }

    public void setName(String str) {
        this.name = str;
    }

    static Reference read(XmlPullParser xmlPullParser) throws XmlPullParserException, DatatypeConfigurationException, IOException {
        int next;
        Reference reference = new Reference();
        String attributeValue = xmlPullParser.getAttributeValue(null, "name");
        if (attributeValue != null) {
            reference.setName(attributeValue);
        }
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("point")) {
                    reference.getPoint().add(XmlParser.readText(xmlPullParser));
                } else {
                    XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next == 3) {
            return reference;
        }
        throw new DatatypeConfigurationException("Reference is not closed");
    }
}
