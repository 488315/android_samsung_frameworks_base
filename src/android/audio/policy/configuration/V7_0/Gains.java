package android.audio.policy.configuration.V7_0;

import com.samsung.android.media.AudioParameter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class Gains {
    private List<Gain> gain;

    public static class Gain {
        private AudioChannelMask channel_mask;
        private Integer defaultValueMB;
        private Integer maxRampMs;
        private Integer maxValueMB;
        private Integer minRampMs;
        private Integer minValueMB;
        private List<AudioGainMode> mode;
        private String name;
        private Integer stepValueMB;
        private Boolean useForVolume;

        public String getName() {
            return this.name;
        }

        boolean hasName() {
            return this.name != null;
        }

        public void setName(String str) {
            this.name = str;
        }

        public List<AudioGainMode> getMode() {
            if (this.mode == null) {
                this.mode = new ArrayList();
            }
            return this.mode;
        }

        boolean hasMode() {
            return this.mode != null;
        }

        public void setMode(List<AudioGainMode> list) {
            this.mode = list;
        }

        public AudioChannelMask getChannel_mask() {
            return this.channel_mask;
        }

        boolean hasChannel_mask() {
            return this.channel_mask != null;
        }

        public void setChannel_mask(AudioChannelMask audioChannelMask) {
            this.channel_mask = audioChannelMask;
        }

        public int getMinValueMB() {
            Integer num = this.minValueMB;
            if (num == null) {
                return 0;
            }
            return num.intValue();
        }

        boolean hasMinValueMB() {
            return this.minValueMB != null;
        }

        public void setMinValueMB(int i) {
            this.minValueMB = Integer.valueOf(i);
        }

        public int getMaxValueMB() {
            Integer num = this.maxValueMB;
            if (num == null) {
                return 0;
            }
            return num.intValue();
        }

        boolean hasMaxValueMB() {
            return this.maxValueMB != null;
        }

        public void setMaxValueMB(int i) {
            this.maxValueMB = Integer.valueOf(i);
        }

        public int getDefaultValueMB() {
            Integer num = this.defaultValueMB;
            if (num == null) {
                return 0;
            }
            return num.intValue();
        }

        boolean hasDefaultValueMB() {
            return this.defaultValueMB != null;
        }

        public void setDefaultValueMB(int i) {
            this.defaultValueMB = Integer.valueOf(i);
        }

        public int getStepValueMB() {
            Integer num = this.stepValueMB;
            if (num == null) {
                return 0;
            }
            return num.intValue();
        }

        boolean hasStepValueMB() {
            return this.stepValueMB != null;
        }

        public void setStepValueMB(int i) {
            this.stepValueMB = Integer.valueOf(i);
        }

        public int getMinRampMs() {
            Integer num = this.minRampMs;
            if (num == null) {
                return 0;
            }
            return num.intValue();
        }

        boolean hasMinRampMs() {
            return this.minRampMs != null;
        }

        public void setMinRampMs(int i) {
            this.minRampMs = Integer.valueOf(i);
        }

        public int getMaxRampMs() {
            Integer num = this.maxRampMs;
            if (num == null) {
                return 0;
            }
            return num.intValue();
        }

        boolean hasMaxRampMs() {
            return this.maxRampMs != null;
        }

        public void setMaxRampMs(int i) {
            this.maxRampMs = Integer.valueOf(i);
        }

        public boolean getUseForVolume() {
            Boolean bool = this.useForVolume;
            if (bool == null) {
                return false;
            }
            return bool.booleanValue();
        }

        boolean hasUseForVolume() {
            return this.useForVolume != null;
        }

        public void setUseForVolume(boolean z) {
            this.useForVolume = Boolean.valueOf(z);
        }

        static Gain read(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException, DatatypeConfigurationException {
            Gain gain = new Gain();
            String attributeValue = xmlPullParser.getAttributeValue(null, "name");
            if (attributeValue != null) {
                gain.setName(attributeValue);
            }
            String attributeValue2 = xmlPullParser.getAttributeValue(null, "mode");
            if (attributeValue2 != null) {
                ArrayList arrayList = new ArrayList();
                for (String str : attributeValue2.split("\\s+")) {
                    arrayList.add(AudioGainMode.fromString(str));
                }
                gain.setMode(arrayList);
            }
            String attributeValue3 = xmlPullParser.getAttributeValue(null, "channel_mask");
            if (attributeValue3 != null) {
                gain.setChannel_mask(AudioChannelMask.fromString(attributeValue3));
            }
            String attributeValue4 = xmlPullParser.getAttributeValue(null, "minValueMB");
            if (attributeValue4 != null) {
                gain.setMinValueMB(Integer.parseInt(attributeValue4));
            }
            String attributeValue5 = xmlPullParser.getAttributeValue(null, "maxValueMB");
            if (attributeValue5 != null) {
                gain.setMaxValueMB(Integer.parseInt(attributeValue5));
            }
            String attributeValue6 = xmlPullParser.getAttributeValue(null, "defaultValueMB");
            if (attributeValue6 != null) {
                gain.setDefaultValueMB(Integer.parseInt(attributeValue6));
            }
            String attributeValue7 = xmlPullParser.getAttributeValue(null, "stepValueMB");
            if (attributeValue7 != null) {
                gain.setStepValueMB(Integer.parseInt(attributeValue7));
            }
            String attributeValue8 = xmlPullParser.getAttributeValue(null, "minRampMs");
            if (attributeValue8 != null) {
                gain.setMinRampMs(Integer.parseInt(attributeValue8));
            }
            String attributeValue9 = xmlPullParser.getAttributeValue(null, "maxRampMs");
            if (attributeValue9 != null) {
                gain.setMaxRampMs(Integer.parseInt(attributeValue9));
            }
            String attributeValue10 = xmlPullParser.getAttributeValue(null, "useForVolume");
            if (attributeValue10 != null) {
                gain.setUseForVolume(Boolean.parseBoolean(attributeValue10));
            }
            XmlParser.skip(xmlPullParser);
            return gain;
        }
    }

    public List<Gain> getGain() {
        if (this.gain == null) {
            this.gain = new ArrayList();
        }
        return this.gain;
    }

    static Gains read(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException, DatatypeConfigurationException {
        int next;
        Gains gains = new Gains();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals(AudioParameter.SUBKEY_VOLUME_PREVENT_OVERHEAT_GAIN)) {
                    gains.getGain().add(Gain.read(xmlPullParser));
                } else {
                    XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next == 3) {
            return gains;
        }
        throw new DatatypeConfigurationException("Gains is not closed");
    }
}
