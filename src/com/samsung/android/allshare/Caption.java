package com.samsung.android.allshare;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.sec.clipboard.util.HtmlUtils;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

/* loaded from: classes6.dex */
public class Caption implements Parcelable {
    public static final Parcelable.Creator<Caption> CREATOR = new Parcelable.Creator<Caption>() { // from class: com.samsung.android.allshare.Caption.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Caption[] newArray(int i) {
            return new Caption[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Caption createFromParcel(Parcel parcel) {
            return new Caption(parcel);
        }
    };
    private static final String TAG = "Caption";
    private String mCaptionType;
    private String mCaptionUri;
    private String mEncoding;
    private String mLanguage;
    private String mName;
    private String mResourceUri;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public enum CaptionOperation {
        ENABLE("Enable"),
        DISABLE("Disable"),
        UNKNOWN("UNKNOWN");

        private final String enumString;

        CaptionOperation(String str) {
            this.enumString = str;
        }

        public String enumToString() {
            return this.enumString;
        }

        public static CaptionOperation stringToEnum(String str) {
            if (str == null) {
                return UNKNOWN;
            }
            if (str.equals("Enable")) {
                return ENABLE;
            }
            if (str.equals("Disable")) {
                return DISABLE;
            }
            if (str.equals("UNKNOWN")) {
                return UNKNOWN;
            }
            return UNKNOWN;
        }
    }

    public enum CaptionType {
        SMI("SMI"),
        SRT("SRT"),
        SSA("SSA"),
        SUB("SUB"),
        TTXT("TTXT"),
        TXT("TXT"),
        UNKNOWN("UNKNOWN");

        private final String enumString;

        CaptionType(String str) {
            this.enumString = str;
        }

        public String enumToString() {
            return this.enumString;
        }

        public static CaptionType stringToEnum(String str) {
            if (str == null) {
                return UNKNOWN;
            }
            if (str.equals("SMI")) {
                return SMI;
            }
            if (str.equals("SRT")) {
                return SRT;
            }
            if (str.equals("SSA")) {
                return SSA;
            }
            if (str.equals("SUB")) {
                return SUB;
            }
            if (str.equals("TTXT")) {
                return TTXT;
            }
            if (str.equals("TXT")) {
                return TXT;
            }
            if (str.equals("UNKNOWN")) {
                return UNKNOWN;
            }
            return UNKNOWN;
        }
    }

    public Caption() {
    }

    public void setName(String str) {
        if (str == null) {
            DLog.d_api(TAG, "[setName] name is null - set empty string");
            str = "";
        }
        DLog.d_api(TAG, "[setName] name is " + str);
        this.mName = str;
    }

    public void setResourceUri(String str) {
        if (str == null) {
            DLog.d_api(TAG, "[setResourceUri] resourceUri is null - set empty string");
            str = "";
        }
        DLog.d_api(TAG, "[setResourceUri] resourceUri is " + str);
        this.mResourceUri = str;
    }

    public void setCaptionUri(String str) {
        if (str == null) {
            DLog.d_api(TAG, "[setCaptionUri] captionUri is null - set empty string");
            str = "";
        }
        DLog.d_api(TAG, "[setCaptionUri] captionUri is " + str);
        this.mCaptionUri = str;
    }

    public void setCaptionType(CaptionType captionType) {
        if (captionType == null) {
            DLog.d_api(TAG, "[setCaptionType] captionType is null - set UNKNOWN");
            captionType = CaptionType.UNKNOWN;
        }
        DLog.d_api(TAG, "[setCaptionType] captionType is " + captionType.enumString);
        this.mCaptionType = captionType.enumString;
    }

    public void setLanguage(String str) {
        if (str == null) {
            DLog.d_api(TAG, "[setLanguage] language is null - set empty string");
            str = "";
        }
        DLog.d_api(TAG, "[setLanguage] language is " + str);
        this.mLanguage = str;
    }

    public void setEncoding(String str) {
        if (str == null) {
            DLog.d_api(TAG, "[setEncoding] encoding is null - set empty string");
            str = "";
        }
        DLog.d_api(TAG, "[setEncoding] encoding is " + str);
        this.mEncoding = str;
    }

    public String getName() {
        if (this.mName == null) {
            DLog.d_api(TAG, "[getName] mName is null - return empty string");
            return "";
        }
        DLog.d_api(TAG, "[getName] mName is " + this.mName);
        return this.mName;
    }

    public String getResourceUri() {
        if (this.mResourceUri == null) {
            DLog.d_api(TAG, "[getResourceUri] mResourceUri is null - return empty string");
            return "";
        }
        DLog.d_api(TAG, "[getResourceUri] mResourceUri is " + this.mResourceUri);
        return this.mResourceUri;
    }

    public String getCaptionUri() {
        if (this.mCaptionUri == null) {
            DLog.d_api(TAG, "[getCaptionUri] CaptionUri is null - return empty string");
            return "";
        }
        DLog.d_api(TAG, "[getCaptionUri] CaptionUri is " + this.mCaptionUri);
        return this.mCaptionUri;
    }

    public CaptionType getCaptionType() {
        if (this.mCaptionType == null) {
            DLog.d_api(TAG, "[getCaptionType] CaptionType is null - return UNKNOWN");
            return CaptionType.UNKNOWN;
        }
        DLog.d_api(TAG, "[getCaptionType] is " + this.mCaptionUri);
        return CaptionType.stringToEnum(this.mCaptionType);
    }

    public List<String> getLanguageList() {
        if (this.mLanguage == null) {
            DLog.w_api(TAG, "getLanguageList language is null");
            return new ArrayList();
        }
        ArrayList arrayList = new ArrayList();
        if (this.mLanguage.contains(",")) {
            for (String str : this.mLanguage.split(",")) {
                arrayList.add(str.trim());
                DLog.d_api(TAG, "getLanguageList [add language]" + str.trim());
            }
            return arrayList;
        }
        arrayList.add(this.mLanguage.trim());
        DLog.d_api(TAG, "getLanguageList [add language]" + this.mLanguage.trim());
        return arrayList;
    }

    public String getLanguageToString() {
        if (this.mLanguage == null) {
            DLog.d_api(TAG, "[getLanguageToString] mLanguage is null - return empty string");
            return "";
        }
        DLog.d_api(TAG, "[getLanguageToString] is " + this.mLanguage);
        return this.mLanguage;
    }

    public String getEncoding() {
        if (this.mEncoding == null) {
            DLog.d_api(TAG, "[getEncoding] mEncoding is null - return empty string");
            return "";
        }
        DLog.d_api(TAG, "[getEncoding] is " + this.mEncoding);
        return this.mEncoding;
    }

    public static List<Caption> parseCaption(String str) throws XmlPullParserException, IOException {
        if (str == null) {
            DLog.w_api(TAG, "parseCaption caption is null");
            return null;
        }
        ArrayList arrayList = new ArrayList();
        try {
            XmlPullParser xmlPullParserNewPullParser = XmlPullParserFactory.newInstance().newPullParser();
            String strHTMLStringToTEXTString = HTMLStringToTEXTString(str);
            if (strHTMLStringToTEXTString == null) {
                DLog.e_api("parseCaption", "captions is null");
                return null;
            }
            xmlPullParserNewPullParser.setInput(new StringReader(strHTMLStringToTEXTString.trim()));
            String attributeValue = "";
            for (int eventType = xmlPullParserNewPullParser.getEventType(); eventType != 1; eventType = xmlPullParserNewPullParser.next()) {
                if (eventType != 2) {
                    if (eventType == 3 && xmlPullParserNewPullParser.getName().equals("sec:ResCaptionInfo")) {
                        attributeValue = "";
                    }
                } else {
                    if ("sec:ResCaptionInfo".equals(xmlPullParserNewPullParser.getName())) {
                        if ("resUri".equals(xmlPullParserNewPullParser.getAttributeName(0))) {
                            attributeValue = xmlPullParserNewPullParser.getAttributeValue(0);
                        }
                    } else if ("Captions".equals(xmlPullParserNewPullParser.getName())) {
                        if ("resUri".equals(xmlPullParserNewPullParser.getAttributeName(0))) {
                            attributeValue = xmlPullParserNewPullParser.getAttributeValue(0);
                        }
                    } else if (xmlPullParserNewPullParser.getName().equals("captionFileInfo")) {
                        Caption caption = new Caption();
                        for (int i = 0; i < xmlPullParserNewPullParser.getAttributeCount(); i++) {
                            String attributeName = xmlPullParserNewPullParser.getAttributeName(i);
                            if (attributeName != null) {
                                if (attributeName.equals("uri")) {
                                    caption.setCaptionUri(xmlPullParserNewPullParser.getAttributeValue(i));
                                } else if (attributeName.equals("name")) {
                                    caption.setName(xmlPullParserNewPullParser.getAttributeValue(i));
                                } else if (attributeName.equals("captionType")) {
                                    caption.setCaptionType(CaptionType.stringToEnum(xmlPullParserNewPullParser.getAttributeValue(i)));
                                } else if (attributeName.equals("language")) {
                                    caption.setLanguage(xmlPullParserNewPullParser.getAttributeValue(i));
                                } else if (attributeName.equals("encoding")) {
                                    caption.setEncoding(xmlPullParserNewPullParser.getAttributeValue(i));
                                }
                            }
                        }
                        caption.setResourceUri(attributeValue);
                        DLog.d_api(TAG, "[parseCaption] - " + caption.toString());
                        arrayList.add(caption);
                    }
                }
            }
            return arrayList;
        } catch (Exception e) {
            DLog.e_api("parseCaption", "Exception - " + e);
            e.printStackTrace();
            return null;
        }
    }

    private static String HTMLStringToTEXTString(String str) {
        if (str == null) {
            DLog.e_api("HTMLStringToTEXTString", "string is null");
            return null;
        }
        return str.replaceAll("<br>", ShaderAssembler.NEWLINE).replaceAll("&gt;", ">").replaceAll("&lt;", "<").replaceAll("&quot;", "\"").replaceAll("&nbsp;", " ").replaceAll("&amp;", "&").replaceAll(HtmlUtils.HTML_LINE_FEED, ShaderAssembler.NEWLINE);
    }

    public String toString() {
        return "Caption ResourceURI[" + this.mResourceUri + "] Name[" + this.mName + "] CaptionURI[" + this.mCaptionUri + "] CaptionType[" + this.mCaptionType + "] Language[" + this.mLanguage + "] encoding[" + this.mEncoding + NavigationBarInflaterView.SIZE_MOD_END;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mName);
        parcel.writeString(this.mResourceUri);
        parcel.writeString(this.mCaptionUri);
        parcel.writeString(this.mCaptionType);
        parcel.writeString(this.mLanguage);
        parcel.writeString(this.mEncoding);
    }

    private void readFromParcel(Parcel parcel) {
        this.mName = parcel.readString();
        this.mResourceUri = parcel.readString();
        this.mCaptionUri = parcel.readString();
        this.mCaptionType = parcel.readString();
        this.mLanguage = parcel.readString();
        this.mEncoding = parcel.readString();
    }

    private Caption(Parcel parcel) {
        readFromParcel(parcel);
    }
}
