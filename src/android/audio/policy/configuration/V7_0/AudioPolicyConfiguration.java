package android.audio.policy.configuration.V7_0;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class AudioPolicyConfiguration {
    private GlobalConfiguration globalConfiguration;
    private List<Modules> modules;
    private SurroundSound surroundSound;
    private Version version;
    private List<Volumes> volumes;

    public GlobalConfiguration getGlobalConfiguration() {
        return this.globalConfiguration;
    }

    boolean hasGlobalConfiguration() {
        return this.globalConfiguration != null;
    }

    public void setGlobalConfiguration(GlobalConfiguration globalConfiguration) {
        this.globalConfiguration = globalConfiguration;
    }

    public List<Modules> getModules() {
        if (this.modules == null) {
            this.modules = new ArrayList();
        }
        return this.modules;
    }

    public List<Volumes> getVolumes() {
        if (this.volumes == null) {
            this.volumes = new ArrayList();
        }
        return this.volumes;
    }

    public SurroundSound getSurroundSound() {
        return this.surroundSound;
    }

    boolean hasSurroundSound() {
        return this.surroundSound != null;
    }

    public void setSurroundSound(SurroundSound surroundSound) {
        this.surroundSound = surroundSound;
    }

    public Version getVersion() {
        return this.version;
    }

    boolean hasVersion() {
        return this.version != null;
    }

    public void setVersion(Version version) {
        this.version = version;
    }

    static AudioPolicyConfiguration read(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException, DatatypeConfigurationException {
        int next;
        AudioPolicyConfiguration audioPolicyConfiguration = new AudioPolicyConfiguration();
        String attributeValue = xmlPullParser.getAttributeValue(null, "version");
        if (attributeValue != null) {
            audioPolicyConfiguration.setVersion(Version.fromString(attributeValue));
        }
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                String name = xmlPullParser.getName();
                if (name.equals("globalConfiguration")) {
                    audioPolicyConfiguration.setGlobalConfiguration(GlobalConfiguration.read(xmlPullParser));
                } else if (name.equals("modules")) {
                    audioPolicyConfiguration.getModules().add(Modules.read(xmlPullParser));
                } else if (name.equals("volumes")) {
                    audioPolicyConfiguration.getVolumes().add(Volumes.read(xmlPullParser));
                } else if (name.equals("surroundSound")) {
                    audioPolicyConfiguration.setSurroundSound(SurroundSound.read(xmlPullParser));
                } else {
                    XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next == 3) {
            return audioPolicyConfiguration;
        }
        throw new DatatypeConfigurationException("AudioPolicyConfiguration is not closed");
    }
}
