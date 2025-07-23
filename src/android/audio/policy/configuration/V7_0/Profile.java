package android.audio.policy.configuration.V7_0;

import android.provider.Telephony;
import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class Profile {
    private List<AudioChannelMask> channelMasks;
    private AudioEncapsulationType encapsulationType;
    private String format;
    private String name;
    private List<BigInteger> samplingRates;

    public String getName() {
        return this.name;
    }

    boolean hasName() {
        return this.name != null;
    }

    public void setName(String str) {
        this.name = str;
    }

    public String getFormat() {
        return this.format;
    }

    boolean hasFormat() {
        return this.format != null;
    }

    public void setFormat(String str) {
        this.format = str;
    }

    public List<BigInteger> getSamplingRates() {
        if (this.samplingRates == null) {
            this.samplingRates = new ArrayList();
        }
        return this.samplingRates;
    }

    boolean hasSamplingRates() {
        return this.samplingRates != null;
    }

    public void setSamplingRates(List<BigInteger> list) {
        this.samplingRates = list;
    }

    public List<AudioChannelMask> getChannelMasks() {
        if (this.channelMasks == null) {
            this.channelMasks = new ArrayList();
        }
        return this.channelMasks;
    }

    boolean hasChannelMasks() {
        return this.channelMasks != null;
    }

    public void setChannelMasks(List<AudioChannelMask> list) {
        this.channelMasks = list;
    }

    public AudioEncapsulationType getEncapsulationType() {
        return this.encapsulationType;
    }

    boolean hasEncapsulationType() {
        return this.encapsulationType != null;
    }

    public void setEncapsulationType(AudioEncapsulationType audioEncapsulationType) {
        this.encapsulationType = audioEncapsulationType;
    }

    static Profile read(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException, DatatypeConfigurationException {
        Profile profile = new Profile();
        String attributeValue = xmlPullParser.getAttributeValue(null, "name");
        if (attributeValue != null) {
            profile.setName(attributeValue);
        }
        String attributeValue2 = xmlPullParser.getAttributeValue(null, Telephony.CellBroadcasts.MESSAGE_FORMAT);
        if (attributeValue2 != null) {
            profile.setFormat(attributeValue2);
        }
        String attributeValue3 = xmlPullParser.getAttributeValue(null, "samplingRates");
        if (attributeValue3 != null) {
            ArrayList arrayList = new ArrayList();
            for (String str : attributeValue3.split("\\s+")) {
                arrayList.add(new BigInteger(str));
            }
            profile.setSamplingRates(arrayList);
        }
        String attributeValue4 = xmlPullParser.getAttributeValue(null, "channelMasks");
        if (attributeValue4 != null) {
            ArrayList arrayList2 = new ArrayList();
            for (String str2 : attributeValue4.split("\\s+")) {
                arrayList2.add(AudioChannelMask.fromString(str2));
            }
            profile.setChannelMasks(arrayList2);
        }
        String attributeValue5 = xmlPullParser.getAttributeValue(null, "encapsulationType");
        if (attributeValue5 != null) {
            profile.setEncapsulationType(AudioEncapsulationType.fromString(attributeValue5));
        }
        XmlParser.skip(xmlPullParser);
        return profile;
    }
}
