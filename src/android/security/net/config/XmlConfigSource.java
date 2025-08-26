package android.security.net.config;

import android.app.slice.SliceProvider;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.security.net.config.NetworkSecurityConfig;
import android.util.ArraySet;
import android.util.Base64;
import android.util.Pair;
import com.android.internal.org.bouncycastle.cms.CMSAttributeTableGenerator;
import com.android.internal.util.XmlUtils;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes3.dex */
public class XmlConfigSource implements ConfigSource {
    private static final int CONFIG_BASE = 0;
    private static final int CONFIG_DEBUG = 2;
    private static final int CONFIG_DOMAIN = 1;
    private final ApplicationInfo mApplicationInfo;
    private Context mContext;
    private final boolean mDebugBuild;
    private NetworkSecurityConfig mDefaultConfig;
    private Set<Pair<Domain, NetworkSecurityConfig>> mDomainMap;
    private boolean mInitialized;
    private final Object mLock = new Object();
    private final int mResourceId;

    public XmlConfigSource(Context context, int i, ApplicationInfo applicationInfo) {
        this.mContext = context;
        this.mResourceId = i;
        ApplicationInfo applicationInfo2 = new ApplicationInfo(applicationInfo);
        this.mApplicationInfo = applicationInfo2;
        this.mDebugBuild = (applicationInfo2.flags & 2) != 0;
    }

    @Override // android.security.net.config.ConfigSource
    public Set<Pair<Domain, NetworkSecurityConfig>> getPerDomainConfigs() {
        ensureInitialized();
        return this.mDomainMap;
    }

    @Override // android.security.net.config.ConfigSource
    public NetworkSecurityConfig getDefaultConfig() {
        ensureInitialized();
        return this.mDefaultConfig;
    }

    private static final String getConfigString(int i) {
        if (i == 0) {
            return "base-config";
        }
        if (i == 1) {
            return "domain-config";
        }
        if (i == 2) {
            return "debug-overrides";
        }
        throw new IllegalArgumentException("Unknown config type: " + i);
    }

    private void ensureInitialized() {
        synchronized (this.mLock) {
            if (this.mInitialized) {
                return;
            }
            try {
                XmlResourceParser xml = this.mContext.getResources().getXml(this.mResourceId);
                try {
                    parseNetworkSecurityConfig(xml);
                    this.mContext = null;
                    this.mInitialized = true;
                    if (xml != null) {
                        xml.close();
                    }
                } catch (Throwable th) {
                    if (xml != null) {
                        try {
                            xml.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                    }
                    throw th;
                }
            } catch (Resources.NotFoundException | ParserException | IOException | XmlPullParserException e) {
                throw new RuntimeException("Failed to parse XML configuration from " + this.mContext.getResources().getResourceEntryName(this.mResourceId), e);
            }
        }
    }

    private Pin parsePin(XmlResourceParser xmlResourceParser) throws XmlPullParserException, ParserException, IOException {
        String attributeValue = xmlResourceParser.getAttributeValue(null, CMSAttributeTableGenerator.DIGEST);
        if (!Pin.isSupportedDigestAlgorithm(attributeValue)) {
            throw new ParserException(xmlResourceParser, "Unsupported pin digest algorithm: " + attributeValue);
        }
        if (xmlResourceParser.next() != 4) {
            throw new ParserException(xmlResourceParser, "Missing pin digest");
        }
        try {
            byte[] bArrDecode = Base64.decode(xmlResourceParser.getText().trim(), 0);
            int digestLength = Pin.getDigestLength(attributeValue);
            if (bArrDecode.length != digestLength) {
                throw new ParserException(xmlResourceParser, "digest length " + bArrDecode.length + " does not match expected length for " + attributeValue + " of " + digestLength);
            }
            if (xmlResourceParser.next() != 3) {
                throw new ParserException(xmlResourceParser, "pin contains additional elements");
            }
            return new Pin(attributeValue, bArrDecode);
        } catch (IllegalArgumentException e) {
            throw new ParserException(xmlResourceParser, "Invalid pin digest", e);
        }
    }

    private PinSet parsePinSet(XmlResourceParser xmlResourceParser) throws XmlPullParserException, ParserException, IOException {
        long time;
        String attributeValue = xmlResourceParser.getAttributeValue(null, "expiration");
        if (attributeValue != null) {
            try {
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                simpleDateFormat.setLenient(false);
                Date date = simpleDateFormat.parse(attributeValue);
                if (date == null) {
                    throw new ParserException(xmlResourceParser, "Invalid expiration date in pin-set");
                }
                time = date.getTime();
            } catch (ParseException e) {
                throw new ParserException(xmlResourceParser, "Invalid expiration date in pin-set", e);
            }
        } else {
            time = Long.MAX_VALUE;
        }
        int depth = xmlResourceParser.getDepth();
        ArraySet arraySet = new ArraySet();
        while (XmlUtils.nextElementWithin(xmlResourceParser, depth)) {
            if (xmlResourceParser.getName().equals(SliceProvider.METHOD_PIN)) {
                arraySet.add(parsePin(xmlResourceParser));
            } else {
                XmlUtils.skipCurrentTag(xmlResourceParser);
            }
        }
        return new PinSet(arraySet, time);
    }

    private Domain parseDomain(XmlResourceParser xmlResourceParser, Set<String> set) throws XmlPullParserException, ParserException, IOException {
        boolean attributeBooleanValue = xmlResourceParser.getAttributeBooleanValue(null, "includeSubdomains", false);
        if (xmlResourceParser.next() != 4) {
            throw new ParserException(xmlResourceParser, "Domain name missing");
        }
        String lowerCase = xmlResourceParser.getText().trim().toLowerCase(Locale.US);
        if (xmlResourceParser.next() != 3) {
            throw new ParserException(xmlResourceParser, "domain contains additional elements");
        }
        if (!set.add(lowerCase)) {
            throw new ParserException(xmlResourceParser, lowerCase + " has already been specified");
        }
        return new Domain(lowerCase, attributeBooleanValue);
    }

    private boolean parseCertificateTransparency(XmlResourceParser xmlResourceParser) throws XmlPullParserException, ParserException, IOException {
        return xmlResourceParser.getAttributeBooleanValue(null, "enabled", false);
    }

    private CertificatesEntryRef parseCertificatesEntry(XmlResourceParser xmlResourceParser, boolean z) throws XmlPullParserException, ParserException, IOException {
        CertificateSource wfaCertificateSource;
        boolean attributeBooleanValue = xmlResourceParser.getAttributeBooleanValue(null, "overridePins", z);
        int attributeResourceValue = xmlResourceParser.getAttributeResourceValue(null, "src", -1);
        String attributeValue = xmlResourceParser.getAttributeValue(null, "src");
        if (attributeValue == null) {
            throw new ParserException(xmlResourceParser, "certificates element missing src attribute");
        }
        boolean z2 = true;
        if (attributeResourceValue != -1) {
            wfaCertificateSource = new ResourceCertificateSource(attributeResourceValue, this.mContext);
        } else {
            if ("system".equals(attributeValue)) {
                wfaCertificateSource = SystemCertificateSource.getInstance();
            } else if ("user".equals(attributeValue)) {
                wfaCertificateSource = UserCertificateSource.getInstance();
            } else if ("wfa".equals(attributeValue)) {
                wfaCertificateSource = WfaCertificateSource.getInstance();
            } else {
                throw new ParserException(xmlResourceParser, "Unknown certificates src. Should be one of system|user|@resourceVal");
            }
            z2 = false;
        }
        XmlUtils.skipCurrentTag(xmlResourceParser);
        return new CertificatesEntryRef(wfaCertificateSource, attributeBooleanValue, z2);
    }

    private Collection<CertificatesEntryRef> parseTrustAnchors(XmlResourceParser xmlResourceParser, boolean z) throws XmlPullParserException, ParserException, IOException {
        int depth = xmlResourceParser.getDepth();
        ArrayList arrayList = new ArrayList();
        while (XmlUtils.nextElementWithin(xmlResourceParser, depth)) {
            if (xmlResourceParser.getName().equals("certificates")) {
                arrayList.add(parseCertificatesEntry(xmlResourceParser, z));
            } else {
                XmlUtils.skipCurrentTag(xmlResourceParser);
            }
        }
        return arrayList;
    }

    private List<Pair<NetworkSecurityConfig.Builder, Set<Domain>>> parseConfigEntry(XmlResourceParser xmlResourceParser, Set<String> set, NetworkSecurityConfig.Builder builder, int i) throws XmlPullParserException, ParserException, IOException {
        ArrayList arrayList = new ArrayList();
        NetworkSecurityConfig.Builder builder2 = new NetworkSecurityConfig.Builder();
        builder2.setParent(builder);
        ArraySet arraySet = new ArraySet();
        boolean z = false;
        boolean z2 = i == 2;
        int depth = xmlResourceParser.getDepth();
        arrayList.add(new Pair(builder2, arraySet));
        for (int i2 = 0; i2 < xmlResourceParser.getAttributeCount(); i2++) {
            String attributeName = xmlResourceParser.getAttributeName(i2);
            if ("hstsEnforced".equals(attributeName)) {
                builder2.setHstsEnforced(xmlResourceParser.getAttributeBooleanValue(i2, false));
            } else if ("cleartextTrafficPermitted".equals(attributeName)) {
                builder2.setCleartextTrafficPermitted(xmlResourceParser.getAttributeBooleanValue(i2, true));
            }
        }
        boolean z3 = false;
        while (XmlUtils.nextElementWithin(xmlResourceParser, depth)) {
            String name = xmlResourceParser.getName();
            if ("domain".equals(name)) {
                if (i != 1) {
                    throw new ParserException(xmlResourceParser, "domain element not allowed in " + getConfigString(i));
                }
                arraySet.add(parseDomain(xmlResourceParser, set));
            } else if ("trust-anchors".equals(name)) {
                if (z) {
                    throw new ParserException(xmlResourceParser, "Multiple trust-anchor elements not allowed");
                }
                builder2.addCertificatesEntryRefs(parseTrustAnchors(xmlResourceParser, z2));
                z = true;
            } else if ("pin-set".equals(name)) {
                if (i != 1) {
                    throw new ParserException(xmlResourceParser, "pin-set element not allowed in " + getConfigString(i));
                }
                if (z3) {
                    throw new ParserException(xmlResourceParser, "Multiple pin-set elements not allowed");
                }
                builder2.setPinSet(parsePinSet(xmlResourceParser));
                z3 = true;
            } else if ("domain-config".equals(name)) {
                if (i != 1) {
                    throw new ParserException(xmlResourceParser, "Nested domain-config not allowed in " + getConfigString(i));
                }
                arrayList.addAll(parseConfigEntry(xmlResourceParser, set, builder2, i));
            } else if ("certificateTransparency".equals(name)) {
                if (i != 0 && i != 1) {
                    throw new ParserException(xmlResourceParser, "certificateTransparency not allowed in " + getConfigString(i));
                }
                builder2.setCertificateTransparencyVerificationRequired(parseCertificateTransparency(xmlResourceParser));
            } else {
                XmlUtils.skipCurrentTag(xmlResourceParser);
            }
        }
        if (i == 1 && arraySet.isEmpty()) {
            throw new ParserException(xmlResourceParser, "No domain elements in domain-config");
        }
        return arrayList;
    }

    private void addDebugAnchorsIfNeeded(NetworkSecurityConfig.Builder builder, NetworkSecurityConfig.Builder builder2) {
        if (builder != null && builder.hasCertificatesEntryRefs() && builder2.hasCertificatesEntryRefs()) {
            builder2.addCertificatesEntryRefs(builder.getCertificatesEntryRefs());
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    private void parseNetworkSecurityConfig(XmlResourceParser xmlResourceParser) throws XmlPullParserException, ParserException, Resources.NotFoundException, IOException {
        ArraySet arraySet = new ArraySet();
        ArrayList<Pair> arrayList = new ArrayList();
        XmlUtils.beginDocument(xmlResourceParser, "network-security-config");
        int depth = xmlResourceParser.getDepth();
        NetworkSecurityConfig.Builder debugOverridesResource = null;
        NetworkSecurityConfig.Builder builder = null;
        boolean z = false;
        boolean z2 = false;
        while (XmlUtils.nextElementWithin(xmlResourceParser, depth)) {
            if ("base-config".equals(xmlResourceParser.getName())) {
                if (z) {
                    throw new ParserException(xmlResourceParser, "Only one base-config allowed");
                }
                builder = parseConfigEntry(xmlResourceParser, arraySet, null, 0).get(0).first;
                z = true;
            } else if ("domain-config".equals(xmlResourceParser.getName())) {
                arrayList.addAll(parseConfigEntry(xmlResourceParser, arraySet, builder, 1));
            } else if ("debug-overrides".equals(xmlResourceParser.getName())) {
                if (z2) {
                    throw new ParserException(xmlResourceParser, "Only one debug-overrides allowed");
                }
                if (this.mDebugBuild) {
                    debugOverridesResource = parseConfigEntry(xmlResourceParser, null, null, 2).get(0).first;
                } else {
                    XmlUtils.skipCurrentTag(xmlResourceParser);
                }
                z2 = true;
            } else {
                XmlUtils.skipCurrentTag(xmlResourceParser);
            }
        }
        if (this.mDebugBuild && debugOverridesResource == null) {
            debugOverridesResource = parseDebugOverridesResource();
        }
        NetworkSecurityConfig.Builder defaultBuilder = NetworkSecurityConfig.getDefaultBuilder(this.mApplicationInfo);
        addDebugAnchorsIfNeeded(debugOverridesResource, defaultBuilder);
        if (builder != null) {
            builder.setParent(defaultBuilder);
            addDebugAnchorsIfNeeded(debugOverridesResource, builder);
        } else {
            builder = defaultBuilder;
        }
        ArraySet arraySet2 = new ArraySet();
        for (Pair pair : arrayList) {
            NetworkSecurityConfig.Builder builder2 = (NetworkSecurityConfig.Builder) pair.first;
            Set set = (Set) pair.second;
            if (builder2.getParent() == null) {
                builder2.setParent(builder);
            }
            addDebugAnchorsIfNeeded(debugOverridesResource, builder2);
            NetworkSecurityConfig networkSecurityConfigBuild = builder2.build();
            Iterator it = set.iterator();
            while (it.hasNext()) {
                arraySet2.add(new Pair((Domain) it.next(), networkSecurityConfigBuild));
            }
        }
        this.mDefaultConfig = builder.build();
        this.mDomainMap = arraySet2;
    }

    private NetworkSecurityConfig.Builder parseDebugOverridesResource() throws XmlPullParserException, ParserException, Resources.NotFoundException, IOException {
        Resources resources = this.mContext.getResources();
        int identifier = resources.getIdentifier(resources.getResourceEntryName(this.mResourceId) + "_debug", "xml", resources.getResourcePackageName(this.mResourceId));
        if (identifier == 0) {
            return null;
        }
        XmlResourceParser xml = resources.getXml(identifier);
        try {
            XmlUtils.beginDocument(xml, "network-security-config");
            int depth = xml.getDepth();
            NetworkSecurityConfig.Builder builder = null;
            boolean z = false;
            while (XmlUtils.nextElementWithin(xml, depth)) {
                if (!"debug-overrides".equals(xml.getName())) {
                    XmlUtils.skipCurrentTag(xml);
                } else {
                    if (z) {
                        throw new ParserException(xml, "Only one debug-overrides allowed");
                    }
                    if (this.mDebugBuild) {
                        builder = parseConfigEntry(xml, null, null, 2).get(0).first;
                    } else {
                        XmlUtils.skipCurrentTag(xml);
                    }
                    z = true;
                }
            }
            if (xml != null) {
                xml.close();
            }
            return builder;
        } catch (Throwable th) {
            if (xml != null) {
                try {
                    xml.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public static class ParserException extends Exception {
        public ParserException(XmlPullParser xmlPullParser, String str, Throwable th) {
            super(str + " at: " + xmlPullParser.getPositionDescription(), th);
        }

        public ParserException(XmlPullParser xmlPullParser, String str) {
            this(xmlPullParser, str, null);
        }
    }
}
