package android.audio.policy.configuration.V7_0;

import java.io.IOException;
import javax.xml.datatype.DatatypeConfigurationException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class SurroundSound {
    private SurroundFormats formats;

    public SurroundFormats getFormats() {
        return this.formats;
    }

    boolean hasFormats() {
        return this.formats != null;
    }

    public void setFormats(SurroundFormats surroundFormats) {
        this.formats = surroundFormats;
    }

    static SurroundSound read(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException, DatatypeConfigurationException {
        int next;
        SurroundSound surroundSound = new SurroundSound();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("formats")) {
                    surroundSound.setFormats(SurroundFormats.read(xmlPullParser));
                } else {
                    XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next == 3) {
            return surroundSound;
        }
        throw new DatatypeConfigurationException("SurroundSound is not closed");
    }
}
