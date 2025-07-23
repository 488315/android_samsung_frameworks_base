package android.audio.policy.configuration.V7_0;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class Modules {
    private List<Module> module;

    public static class Module {
        private AttachedDevices attachedDevices;
        private String defaultOutputDevice;
        private DevicePorts devicePorts;
        private HalVersion halVersion;
        private MixPorts mixPorts;
        private String name;
        private Routes routes;

        public AttachedDevices getAttachedDevices() {
            return this.attachedDevices;
        }

        boolean hasAttachedDevices() {
            return this.attachedDevices != null;
        }

        public void setAttachedDevices(AttachedDevices attachedDevices) {
            this.attachedDevices = attachedDevices;
        }

        public String getDefaultOutputDevice() {
            return this.defaultOutputDevice;
        }

        boolean hasDefaultOutputDevice() {
            return this.defaultOutputDevice != null;
        }

        public void setDefaultOutputDevice(String str) {
            this.defaultOutputDevice = str;
        }

        public MixPorts getMixPorts() {
            return this.mixPorts;
        }

        boolean hasMixPorts() {
            return this.mixPorts != null;
        }

        public void setMixPorts(MixPorts mixPorts) {
            this.mixPorts = mixPorts;
        }

        public DevicePorts getDevicePorts() {
            return this.devicePorts;
        }

        boolean hasDevicePorts() {
            return this.devicePorts != null;
        }

        public void setDevicePorts(DevicePorts devicePorts) {
            this.devicePorts = devicePorts;
        }

        public Routes getRoutes() {
            return this.routes;
        }

        boolean hasRoutes() {
            return this.routes != null;
        }

        public void setRoutes(Routes routes) {
            this.routes = routes;
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

        public HalVersion getHalVersion() {
            return this.halVersion;
        }

        boolean hasHalVersion() {
            return this.halVersion != null;
        }

        public void setHalVersion(HalVersion halVersion) {
            this.halVersion = halVersion;
        }

        static Module read(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException, DatatypeConfigurationException {
            int next;
            Module module = new Module();
            String attributeValue = xmlPullParser.getAttributeValue(null, "name");
            if (attributeValue != null) {
                module.setName(attributeValue);
            }
            String attributeValue2 = xmlPullParser.getAttributeValue(null, "halVersion");
            if (attributeValue2 != null) {
                module.setHalVersion(HalVersion.fromString(attributeValue2));
            }
            xmlPullParser.getDepth();
            while (true) {
                next = xmlPullParser.next();
                if (next == 1 || next == 3) {
                    break;
                }
                if (xmlPullParser.getEventType() == 2) {
                    String name = xmlPullParser.getName();
                    if (name.equals("attachedDevices")) {
                        module.setAttachedDevices(AttachedDevices.read(xmlPullParser));
                    } else if (name.equals("defaultOutputDevice")) {
                        module.setDefaultOutputDevice(XmlParser.readText(xmlPullParser));
                    } else if (name.equals("mixPorts")) {
                        module.setMixPorts(MixPorts.read(xmlPullParser));
                    } else if (name.equals("devicePorts")) {
                        module.setDevicePorts(DevicePorts.read(xmlPullParser));
                    } else if (name.equals("routes")) {
                        module.setRoutes(Routes.read(xmlPullParser));
                    } else {
                        XmlParser.skip(xmlPullParser);
                    }
                }
            }
            if (next == 3) {
                return module;
            }
            throw new DatatypeConfigurationException("Modules.Module is not closed");
        }
    }

    public List<Module> getModule() {
        if (this.module == null) {
            this.module = new ArrayList();
        }
        return this.module;
    }

    static Modules read(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException, DatatypeConfigurationException {
        int next;
        Modules modules = new Modules();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("module")) {
                    modules.getModule().add(Module.read(xmlPullParser));
                } else {
                    XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next == 3) {
            return modules;
        }
        throw new DatatypeConfigurationException("Modules is not closed");
    }
}
