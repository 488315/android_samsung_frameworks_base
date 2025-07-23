package android.audio.policy.configuration.V7_0;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class Volumes {
    private List<Reference> reference;
    private List<Volume> volume;

    public List<Volume> getVolume() {
        if (this.volume == null) {
            this.volume = new ArrayList();
        }
        return this.volume;
    }

    public List<Reference> getReference() {
        if (this.reference == null) {
            this.reference = new ArrayList();
        }
        return this.reference;
    }

    static Volumes read(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException, DatatypeConfigurationException {
        int next;
        Volumes volumes = new Volumes();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                String name = xmlPullParser.getName();
                if (name.equals("volume")) {
                    volumes.getVolume().add(Volume.read(xmlPullParser));
                } else if (name.equals("reference")) {
                    volumes.getReference().add(Reference.read(xmlPullParser));
                } else {
                    XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next == 3) {
            return volumes;
        }
        throw new DatatypeConfigurationException("Volumes is not closed");
    }
}
