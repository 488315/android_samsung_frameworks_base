package android.audio.policy.configuration.V7_0;

import java.io.IOException;
import javax.xml.datatype.DatatypeConfigurationException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class GlobalConfiguration {
    private Boolean call_screen_mode_supported;
    private EngineSuffix engine_library;
    private Boolean speaker_drc_enabled;

    public boolean getSpeaker_drc_enabled() {
        Boolean bool = this.speaker_drc_enabled;
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    boolean hasSpeaker_drc_enabled() {
        return this.speaker_drc_enabled != null;
    }

    public void setSpeaker_drc_enabled(boolean z) {
        this.speaker_drc_enabled = Boolean.valueOf(z);
    }

    public boolean getCall_screen_mode_supported() {
        Boolean bool = this.call_screen_mode_supported;
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    boolean hasCall_screen_mode_supported() {
        return this.call_screen_mode_supported != null;
    }

    public void setCall_screen_mode_supported(boolean z) {
        this.call_screen_mode_supported = Boolean.valueOf(z);
    }

    public EngineSuffix getEngine_library() {
        return this.engine_library;
    }

    boolean hasEngine_library() {
        return this.engine_library != null;
    }

    public void setEngine_library(EngineSuffix engineSuffix) {
        this.engine_library = engineSuffix;
    }

    static GlobalConfiguration read(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException, DatatypeConfigurationException {
        GlobalConfiguration globalConfiguration = new GlobalConfiguration();
        String attributeValue = xmlPullParser.getAttributeValue(null, "speaker_drc_enabled");
        if (attributeValue != null) {
            globalConfiguration.setSpeaker_drc_enabled(Boolean.parseBoolean(attributeValue));
        }
        String attributeValue2 = xmlPullParser.getAttributeValue(null, "call_screen_mode_supported");
        if (attributeValue2 != null) {
            globalConfiguration.setCall_screen_mode_supported(Boolean.parseBoolean(attributeValue2));
        }
        String attributeValue3 = xmlPullParser.getAttributeValue(null, "engine_library");
        if (attributeValue3 != null) {
            globalConfiguration.setEngine_library(EngineSuffix.fromString(attributeValue3));
        }
        XmlParser.skip(xmlPullParser);
        return globalConfiguration;
    }
}
