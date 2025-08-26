package android.audio.policy.configuration.V7_0;

import android.content.Context;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class DevicePorts {
    private List<DevicePort> devicePort;

    public static class DevicePort {
        private Boolean _default;
        private String address;
        private List<String> encodedFormats;
        private Gains gains;
        private List<Profile> profile;
        private Role role;
        private String tagName;
        private String type;

        public List<Profile> getProfile() {
            if (this.profile == null) {
                this.profile = new ArrayList();
            }
            return this.profile;
        }

        public Gains getGains() {
            return this.gains;
        }

        boolean hasGains() {
            return this.gains != null;
        }

        public void setGains(Gains gains) {
            this.gains = gains;
        }

        public String getTagName() {
            return this.tagName;
        }

        boolean hasTagName() {
            return this.tagName != null;
        }

        public void setTagName(String str) {
            this.tagName = str;
        }

        public String getType() {
            return this.type;
        }

        boolean hasType() {
            return this.type != null;
        }

        public void setType(String str) {
            this.type = str;
        }

        public Role getRole() {
            return this.role;
        }

        boolean hasRole() {
            return this.role != null;
        }

        public void setRole(Role role) {
            this.role = role;
        }

        public String getAddress() {
            return this.address;
        }

        boolean hasAddress() {
            return this.address != null;
        }

        public void setAddress(String str) {
            this.address = str;
        }

        public boolean get_default() {
            Boolean bool = this._default;
            if (bool == null) {
                return false;
            }
            return bool.booleanValue();
        }

        boolean has_default() {
            return this._default != null;
        }

        public void set_default(boolean z) {
            this._default = Boolean.valueOf(z);
        }

        public List<String> getEncodedFormats() {
            if (this.encodedFormats == null) {
                this.encodedFormats = new ArrayList();
            }
            return this.encodedFormats;
        }

        boolean hasEncodedFormats() {
            return this.encodedFormats != null;
        }

        public void setEncodedFormats(List<String> list) {
            this.encodedFormats = list;
        }

        static DevicePort read(XmlPullParser xmlPullParser) throws XmlPullParserException, DatatypeConfigurationException, IOException {
            int next;
            DevicePort devicePort = new DevicePort();
            String attributeValue = xmlPullParser.getAttributeValue(null, "tagName");
            if (attributeValue != null) {
                devicePort.setTagName(attributeValue);
            }
            String attributeValue2 = xmlPullParser.getAttributeValue(null, "type");
            if (attributeValue2 != null) {
                devicePort.setType(attributeValue2);
            }
            String attributeValue3 = xmlPullParser.getAttributeValue(null, Context.ROLE_SERVICE);
            if (attributeValue3 != null) {
                devicePort.setRole(Role.fromString(attributeValue3));
            }
            String attributeValue4 = xmlPullParser.getAttributeValue(null, "address");
            if (attributeValue4 != null) {
                devicePort.setAddress(attributeValue4);
            }
            String attributeValue5 = xmlPullParser.getAttributeValue(null, "default");
            if (attributeValue5 != null) {
                devicePort.set_default(Boolean.parseBoolean(attributeValue5));
            }
            String attributeValue6 = xmlPullParser.getAttributeValue(null, "encodedFormats");
            if (attributeValue6 != null) {
                ArrayList arrayList = new ArrayList();
                for (String str : attributeValue6.split("\\s+")) {
                    arrayList.add(str);
                }
                devicePort.setEncodedFormats(arrayList);
            }
            xmlPullParser.getDepth();
            while (true) {
                next = xmlPullParser.next();
                if (next == 1 || next == 3) {
                    break;
                }
                if (xmlPullParser.getEventType() == 2) {
                    String name = xmlPullParser.getName();
                    if (name.equals("profile")) {
                        devicePort.getProfile().add(Profile.read(xmlPullParser));
                    } else if (name.equals("gains")) {
                        devicePort.setGains(Gains.read(xmlPullParser));
                    } else {
                        XmlParser.skip(xmlPullParser);
                    }
                }
            }
            if (next == 3) {
                return devicePort;
            }
            throw new DatatypeConfigurationException("DevicePorts.DevicePort is not closed");
        }
    }

    public List<DevicePort> getDevicePort() {
        if (this.devicePort == null) {
            this.devicePort = new ArrayList();
        }
        return this.devicePort;
    }

    static DevicePorts read(XmlPullParser xmlPullParser) throws XmlPullParserException, DatatypeConfigurationException, IOException {
        int next;
        DevicePorts devicePorts = new DevicePorts();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("devicePort")) {
                    devicePorts.getDevicePort().add(DevicePort.read(xmlPullParser));
                } else {
                    XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next == 3) {
            return devicePorts;
        }
        throw new DatatypeConfigurationException("DevicePorts is not closed");
    }
}
