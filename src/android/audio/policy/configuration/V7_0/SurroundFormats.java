package android.audio.policy.configuration.V7_0;

import android.provider.Telephony;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class SurroundFormats {
    private List<Format> format;

    public static class Format {
        private String name;
        private List<String> subformats;

        public String getName() {
            return this.name;
        }

        boolean hasName() {
            return this.name != null;
        }

        public void setName(String str) {
            this.name = str;
        }

        public List<String> getSubformats() {
            if (this.subformats == null) {
                this.subformats = new ArrayList();
            }
            return this.subformats;
        }

        boolean hasSubformats() {
            return this.subformats != null;
        }

        public void setSubformats(List<String> list) {
            this.subformats = list;
        }

        static Format read(XmlPullParser xmlPullParser) throws XmlPullParserException, DatatypeConfigurationException, IOException {
            Format format = new Format();
            String attributeValue = xmlPullParser.getAttributeValue(null, "name");
            if (attributeValue != null) {
                format.setName(attributeValue);
            }
            String attributeValue2 = xmlPullParser.getAttributeValue(null, "subformats");
            if (attributeValue2 != null) {
                ArrayList arrayList = new ArrayList();
                for (String str : attributeValue2.split("\\s+")) {
                    arrayList.add(str);
                }
                format.setSubformats(arrayList);
            }
            XmlParser.skip(xmlPullParser);
            return format;
        }
    }

    public List<Format> getFormat() {
        if (this.format == null) {
            this.format = new ArrayList();
        }
        return this.format;
    }

    static SurroundFormats read(XmlPullParser xmlPullParser) throws XmlPullParserException, DatatypeConfigurationException, IOException {
        int next;
        SurroundFormats surroundFormats = new SurroundFormats();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals(Telephony.CellBroadcasts.MESSAGE_FORMAT)) {
                    surroundFormats.getFormat().add(Format.read(xmlPullParser));
                } else {
                    XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next == 3) {
            return surroundFormats;
        }
        throw new DatatypeConfigurationException("SurroundFormats is not closed");
    }
}
