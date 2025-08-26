package android.audio.policy.configuration.V7_0;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class Volume {
    private DeviceCategory deviceCategory;
    private List<String> point;
    private String ref;
    private AudioStreamType stream;

    public List<String> getPoint() {
        if (this.point == null) {
            this.point = new ArrayList();
        }
        return this.point;
    }

    public AudioStreamType getStream() {
        return this.stream;
    }

    boolean hasStream() {
        return this.stream != null;
    }

    public void setStream(AudioStreamType audioStreamType) {
        this.stream = audioStreamType;
    }

    public DeviceCategory getDeviceCategory() {
        return this.deviceCategory;
    }

    boolean hasDeviceCategory() {
        return this.deviceCategory != null;
    }

    public void setDeviceCategory(DeviceCategory deviceCategory) {
        this.deviceCategory = deviceCategory;
    }

    public String getRef() {
        return this.ref;
    }

    boolean hasRef() {
        return this.ref != null;
    }

    public void setRef(String str) {
        this.ref = str;
    }

    static Volume read(XmlPullParser xmlPullParser) throws XmlPullParserException, DatatypeConfigurationException, IOException {
        int next;
        Volume volume = new Volume();
        String attributeValue = xmlPullParser.getAttributeValue(null, "stream");
        if (attributeValue != null) {
            volume.setStream(AudioStreamType.fromString(attributeValue));
        }
        String attributeValue2 = xmlPullParser.getAttributeValue(null, "deviceCategory");
        if (attributeValue2 != null) {
            volume.setDeviceCategory(DeviceCategory.fromString(attributeValue2));
        }
        String attributeValue3 = xmlPullParser.getAttributeValue(null, "ref");
        if (attributeValue3 != null) {
            volume.setRef(attributeValue3);
        }
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("point")) {
                    volume.getPoint().add(XmlParser.readText(xmlPullParser));
                } else {
                    XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next == 3) {
            return volume;
        }
        throw new DatatypeConfigurationException("Volume is not closed");
    }
}
