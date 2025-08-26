package android.audio.policy.configuration.V7_0;

import android.content.Context;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class MixPorts {
    private List<MixPort> mixPort;

    public static class MixPort {
        private List<AudioInOutFlag> flags;
        private Gains gains;
        private Long maxActiveCount;
        private Long maxOpenCount;
        private String name;
        private List<AudioUsage> preferredUsage;
        private List<Profile> profile;
        private Role role;

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

        public String getName() {
            return this.name;
        }

        boolean hasName() {
            return this.name != null;
        }

        public void setName(String str) {
            this.name = str;
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

        public List<AudioInOutFlag> getFlags() {
            if (this.flags == null) {
                this.flags = new ArrayList();
            }
            return this.flags;
        }

        boolean hasFlags() {
            return this.flags != null;
        }

        public void setFlags(List<AudioInOutFlag> list) {
            this.flags = list;
        }

        public long getMaxOpenCount() {
            Long l = this.maxOpenCount;
            if (l == null) {
                return 0L;
            }
            return l.longValue();
        }

        boolean hasMaxOpenCount() {
            return this.maxOpenCount != null;
        }

        public void setMaxOpenCount(long j) {
            this.maxOpenCount = Long.valueOf(j);
        }

        public long getMaxActiveCount() {
            Long l = this.maxActiveCount;
            if (l == null) {
                return 0L;
            }
            return l.longValue();
        }

        boolean hasMaxActiveCount() {
            return this.maxActiveCount != null;
        }

        public void setMaxActiveCount(long j) {
            this.maxActiveCount = Long.valueOf(j);
        }

        public List<AudioUsage> getPreferredUsage() {
            if (this.preferredUsage == null) {
                this.preferredUsage = new ArrayList();
            }
            return this.preferredUsage;
        }

        boolean hasPreferredUsage() {
            return this.preferredUsage != null;
        }

        public void setPreferredUsage(List<AudioUsage> list) {
            this.preferredUsage = list;
        }

        static MixPort read(XmlPullParser xmlPullParser) throws XmlPullParserException, DatatypeConfigurationException, IOException {
            int next;
            MixPort mixPort = new MixPort();
            String attributeValue = xmlPullParser.getAttributeValue(null, "name");
            if (attributeValue != null) {
                mixPort.setName(attributeValue);
            }
            String attributeValue2 = xmlPullParser.getAttributeValue(null, Context.ROLE_SERVICE);
            if (attributeValue2 != null) {
                mixPort.setRole(Role.fromString(attributeValue2));
            }
            String attributeValue3 = xmlPullParser.getAttributeValue(null, "flags");
            if (attributeValue3 != null) {
                ArrayList arrayList = new ArrayList();
                for (String str : attributeValue3.split("\\s+")) {
                    arrayList.add(AudioInOutFlag.fromString(str));
                }
                mixPort.setFlags(arrayList);
            }
            String attributeValue4 = xmlPullParser.getAttributeValue(null, "maxOpenCount");
            if (attributeValue4 != null) {
                mixPort.setMaxOpenCount(Long.parseLong(attributeValue4));
            }
            String attributeValue5 = xmlPullParser.getAttributeValue(null, "maxActiveCount");
            if (attributeValue5 != null) {
                mixPort.setMaxActiveCount(Long.parseLong(attributeValue5));
            }
            String attributeValue6 = xmlPullParser.getAttributeValue(null, "preferredUsage");
            if (attributeValue6 != null) {
                ArrayList arrayList2 = new ArrayList();
                for (String str2 : attributeValue6.split("\\s+")) {
                    arrayList2.add(AudioUsage.fromString(str2));
                }
                mixPort.setPreferredUsage(arrayList2);
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
                        mixPort.getProfile().add(Profile.read(xmlPullParser));
                    } else if (name.equals("gains")) {
                        mixPort.setGains(Gains.read(xmlPullParser));
                    } else {
                        XmlParser.skip(xmlPullParser);
                    }
                }
            }
            if (next == 3) {
                return mixPort;
            }
            throw new DatatypeConfigurationException("MixPorts.MixPort is not closed");
        }
    }

    public List<MixPort> getMixPort() {
        if (this.mixPort == null) {
            this.mixPort = new ArrayList();
        }
        return this.mixPort;
    }

    static MixPorts read(XmlPullParser xmlPullParser) throws XmlPullParserException, DatatypeConfigurationException, IOException {
        int next;
        MixPorts mixPorts = new MixPorts();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("mixPort")) {
                    mixPorts.getMixPort().add(MixPort.read(xmlPullParser));
                } else {
                    XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next == 3) {
            return mixPorts;
        }
        throw new DatatypeConfigurationException("MixPorts is not closed");
    }
}
