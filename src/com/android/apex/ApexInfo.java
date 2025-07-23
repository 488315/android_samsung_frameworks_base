package com.android.apex;

import com.sec.android.iaft.SmLib_IafdConstant;
import java.io.IOException;
import javax.xml.datatype.DatatypeConfigurationException;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes5.dex */
public class ApexInfo {
    private Boolean isActive;
    private Boolean isFactory;
    private Long lastUpdateMillis;
    private String moduleName;
    private String modulePath;
    private String partition;
    private String preinstalledModulePath;
    private Boolean provideSharedApexLibs;
    private Long versionCode;
    private String versionName;

    public String getModuleName() {
        return this.moduleName;
    }

    boolean hasModuleName() {
        return this.moduleName != null;
    }

    public void setModuleName(String str) {
        this.moduleName = str;
    }

    public String getModulePath() {
        return this.modulePath;
    }

    boolean hasModulePath() {
        return this.modulePath != null;
    }

    public void setModulePath(String str) {
        this.modulePath = str;
    }

    public String getPreinstalledModulePath() {
        return this.preinstalledModulePath;
    }

    boolean hasPreinstalledModulePath() {
        return this.preinstalledModulePath != null;
    }

    public void setPreinstalledModulePath(String str) {
        this.preinstalledModulePath = str;
    }

    public long getVersionCode() {
        Long l = this.versionCode;
        if (l == null) {
            return 0L;
        }
        return l.longValue();
    }

    boolean hasVersionCode() {
        return this.versionCode != null;
    }

    public void setVersionCode(long j) {
        this.versionCode = Long.valueOf(j);
    }

    public String getVersionName() {
        return this.versionName;
    }

    boolean hasVersionName() {
        return this.versionName != null;
    }

    public void setVersionName(String str) {
        this.versionName = str;
    }

    public boolean getIsFactory() {
        Boolean bool = this.isFactory;
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    boolean hasIsFactory() {
        return this.isFactory != null;
    }

    public void setIsFactory(boolean z) {
        this.isFactory = Boolean.valueOf(z);
    }

    public boolean getIsActive() {
        Boolean bool = this.isActive;
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    boolean hasIsActive() {
        return this.isActive != null;
    }

    public void setIsActive(boolean z) {
        this.isActive = Boolean.valueOf(z);
    }

    public long getLastUpdateMillis() {
        Long l = this.lastUpdateMillis;
        if (l == null) {
            return 0L;
        }
        return l.longValue();
    }

    boolean hasLastUpdateMillis() {
        return this.lastUpdateMillis != null;
    }

    public void setLastUpdateMillis(long j) {
        this.lastUpdateMillis = Long.valueOf(j);
    }

    public boolean getProvideSharedApexLibs() {
        Boolean bool = this.provideSharedApexLibs;
        if (bool == null) {
            return false;
        }
        return bool.booleanValue();
    }

    boolean hasProvideSharedApexLibs() {
        return this.provideSharedApexLibs != null;
    }

    public void setProvideSharedApexLibs(boolean z) {
        this.provideSharedApexLibs = Boolean.valueOf(z);
    }

    public String getPartition() {
        return this.partition;
    }

    boolean hasPartition() {
        return this.partition != null;
    }

    public void setPartition(String str) {
        this.partition = str;
    }

    static ApexInfo read(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException, DatatypeConfigurationException {
        ApexInfo apexInfo = new ApexInfo();
        String attributeValue = xmlPullParser.getAttributeValue(null, "moduleName");
        if (attributeValue != null) {
            apexInfo.setModuleName(attributeValue);
        }
        String attributeValue2 = xmlPullParser.getAttributeValue(null, "modulePath");
        if (attributeValue2 != null) {
            apexInfo.setModulePath(attributeValue2);
        }
        String attributeValue3 = xmlPullParser.getAttributeValue(null, "preinstalledModulePath");
        if (attributeValue3 != null) {
            apexInfo.setPreinstalledModulePath(attributeValue3);
        }
        String attributeValue4 = xmlPullParser.getAttributeValue(null, SmLib_IafdConstant.KEY_VERSION_CODE);
        if (attributeValue4 != null) {
            apexInfo.setVersionCode(Long.parseLong(attributeValue4));
        }
        String attributeValue5 = xmlPullParser.getAttributeValue(null, "versionName");
        if (attributeValue5 != null) {
            apexInfo.setVersionName(attributeValue5);
        }
        String attributeValue6 = xmlPullParser.getAttributeValue(null, "isFactory");
        if (attributeValue6 != null) {
            apexInfo.setIsFactory(Boolean.parseBoolean(attributeValue6));
        }
        String attributeValue7 = xmlPullParser.getAttributeValue(null, "isActive");
        if (attributeValue7 != null) {
            apexInfo.setIsActive(Boolean.parseBoolean(attributeValue7));
        }
        String attributeValue8 = xmlPullParser.getAttributeValue(null, "lastUpdateMillis");
        if (attributeValue8 != null) {
            apexInfo.setLastUpdateMillis(Long.parseLong(attributeValue8));
        }
        String attributeValue9 = xmlPullParser.getAttributeValue(null, "provideSharedApexLibs");
        if (attributeValue9 != null) {
            apexInfo.setProvideSharedApexLibs(Boolean.parseBoolean(attributeValue9));
        }
        String attributeValue10 = xmlPullParser.getAttributeValue(null, "partition");
        if (attributeValue10 != null) {
            apexInfo.setPartition(attributeValue10);
        }
        XmlParser.skip(xmlPullParser);
        return apexInfo;
    }

    void write(XmlWriter xmlWriter, String str) throws IOException {
        xmlWriter.print("<" + str);
        if (hasModuleName()) {
            xmlWriter.print(" moduleName=\"");
            xmlWriter.print(getModuleName());
            xmlWriter.print("\"");
        }
        if (hasModulePath()) {
            xmlWriter.print(" modulePath=\"");
            xmlWriter.print(getModulePath());
            xmlWriter.print("\"");
        }
        if (hasPreinstalledModulePath()) {
            xmlWriter.print(" preinstalledModulePath=\"");
            xmlWriter.print(getPreinstalledModulePath());
            xmlWriter.print("\"");
        }
        if (hasVersionCode()) {
            xmlWriter.print(" versionCode=\"");
            xmlWriter.print(Long.toString(getVersionCode()));
            xmlWriter.print("\"");
        }
        if (hasVersionName()) {
            xmlWriter.print(" versionName=\"");
            xmlWriter.print(getVersionName());
            xmlWriter.print("\"");
        }
        if (hasIsFactory()) {
            xmlWriter.print(" isFactory=\"");
            xmlWriter.print(Boolean.toString(getIsFactory()));
            xmlWriter.print("\"");
        }
        if (hasIsActive()) {
            xmlWriter.print(" isActive=\"");
            xmlWriter.print(Boolean.toString(getIsActive()));
            xmlWriter.print("\"");
        }
        if (hasLastUpdateMillis()) {
            xmlWriter.print(" lastUpdateMillis=\"");
            xmlWriter.print(Long.toString(getLastUpdateMillis()));
            xmlWriter.print("\"");
        }
        if (hasProvideSharedApexLibs()) {
            xmlWriter.print(" provideSharedApexLibs=\"");
            xmlWriter.print(Boolean.toString(getProvideSharedApexLibs()));
            xmlWriter.print("\"");
        }
        if (hasPartition()) {
            xmlWriter.print(" partition=\"");
            xmlWriter.print(getPartition());
            xmlWriter.print("\"");
        }
        xmlWriter.print(">\n");
        xmlWriter.print("</" + str + ">\n");
    }
}
