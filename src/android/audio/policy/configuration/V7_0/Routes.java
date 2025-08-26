package android.audio.policy.configuration.V7_0;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.xml.datatype.DatatypeConfigurationException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class Routes {
    private List<Route> route;

    public static class Route {
        private String sink;
        private String sources;
        private MixType type;

        public MixType getType() {
            return this.type;
        }

        boolean hasType() {
            return this.type != null;
        }

        public void setType(MixType mixType) {
            this.type = mixType;
        }

        public String getSink() {
            return this.sink;
        }

        boolean hasSink() {
            return this.sink != null;
        }

        public void setSink(String str) {
            this.sink = str;
        }

        public String getSources() {
            return this.sources;
        }

        boolean hasSources() {
            return this.sources != null;
        }

        public void setSources(String str) {
            this.sources = str;
        }

        static Route read(XmlPullParser xmlPullParser) throws XmlPullParserException, DatatypeConfigurationException, IOException {
            Route route = new Route();
            String attributeValue = xmlPullParser.getAttributeValue(null, "type");
            if (attributeValue != null) {
                route.setType(MixType.fromString(attributeValue));
            }
            String attributeValue2 = xmlPullParser.getAttributeValue(null, "sink");
            if (attributeValue2 != null) {
                route.setSink(attributeValue2);
            }
            String attributeValue3 = xmlPullParser.getAttributeValue(null, "sources");
            if (attributeValue3 != null) {
                route.setSources(attributeValue3);
            }
            XmlParser.skip(xmlPullParser);
            return route;
        }
    }

    public List<Route> getRoute() {
        if (this.route == null) {
            this.route = new ArrayList();
        }
        return this.route;
    }

    static Routes read(XmlPullParser xmlPullParser) throws XmlPullParserException, DatatypeConfigurationException, IOException {
        int next;
        Routes routes = new Routes();
        xmlPullParser.getDepth();
        while (true) {
            next = xmlPullParser.next();
            if (next == 1 || next == 3) {
                break;
            }
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals("route")) {
                    routes.getRoute().add(Route.read(xmlPullParser));
                } else {
                    XmlParser.skip(xmlPullParser);
                }
            }
        }
        if (next == 3) {
            return routes;
        }
        throw new DatatypeConfigurationException("Routes is not closed");
    }
}
