package android.audio.policy.configuration.V7_0;

import com.android.ims.ImsConfig;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class AttachedDevices {
    private List<String> item;

    public List<String> getItem() {
        if (this.item == null) {
            this.item = new ArrayList();
        }
        return this.item;
    }

    static AttachedDevices read(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException, DatatypeConfigurationException {
        int next;
        AttachedDevices attachedDevices = new AttachedDevices();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals(ImsConfig.EXTRA_CHANGED_ITEM)) {
                    attachedDevices.getItem().add(XmlParser.readText(xmlPullParser));
                } else {
                    XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next == 3) {
            return attachedDevices;
        }
        throw new DatatypeConfigurationException("AttachedDevices is not closed");
    }
}
