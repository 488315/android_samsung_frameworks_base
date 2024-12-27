package com.samsung.android.allshare;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.sec.clipboard.util.HtmlUtils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class Caption implements Parcelable {
    public static final Parcelable.Creator<Caption> CREATOR =
            new Parcelable.Creator<
                    Caption>() { // from class: com.samsung.android.allshare.Caption.1
                @Override // android.os.Parcelable.Creator
                public Caption[] newArray(int size) {
                    return new Caption[size];
                }

                @Override // android.os.Parcelable.Creator
                public Caption createFromParcel(Parcel source) {
                    return new Caption(source);
                }
            };
    private static final String TAG = "Caption";
    private String mCaptionType;
    private String mCaptionUri;
    private String mEncoding;
    private String mLanguage;
    private String mName;
    private String mResourceUri;

    public enum CaptionOperation {
        ENABLE("Enable"),
        DISABLE("Disable"),
        UNKNOWN("UNKNOWN");

        private final String enumString;

        CaptionOperation(String enumStr) {
            this.enumString = enumStr;
        }

        public String enumToString() {
            return this.enumString;
        }

        public static CaptionOperation stringToEnum(String enumStr) {
            if (enumStr == null) {
                return UNKNOWN;
            }
            if (enumStr.equals("Enable")) {
                return ENABLE;
            }
            if (enumStr.equals("Disable")) {
                return DISABLE;
            }
            if (enumStr.equals("UNKNOWN")) {
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

        CaptionType(String enumStr) {
            this.enumString = enumStr;
        }

        public String enumToString() {
            return this.enumString;
        }

        public static CaptionType stringToEnum(String enumStr) {
            if (enumStr == null) {
                return UNKNOWN;
            }
            if (enumStr.equals("SMI")) {
                return SMI;
            }
            if (enumStr.equals("SRT")) {
                return SRT;
            }
            if (enumStr.equals("SSA")) {
                return SSA;
            }
            if (enumStr.equals("SUB")) {
                return SUB;
            }
            if (enumStr.equals("TTXT")) {
                return TTXT;
            }
            if (enumStr.equals("TXT")) {
                return TXT;
            }
            if (enumStr.equals("UNKNOWN")) {
                return UNKNOWN;
            }
            return UNKNOWN;
        }
    }

    public Caption() {}

    public void setName(String name) {
        if (name == null) {
            DLog.d_api(TAG, "[setName] name is null - set empty string");
            name = "";
        }
        DLog.d_api(TAG, "[setName] name is " + name);
        this.mName = name;
    }

    public void setResourceUri(String resourceUri) {
        if (resourceUri == null) {
            DLog.d_api(TAG, "[setResourceUri] resourceUri is null - set empty string");
            resourceUri = "";
        }
        DLog.d_api(TAG, "[setResourceUri] resourceUri is " + resourceUri);
        this.mResourceUri = resourceUri;
    }

    public void setCaptionUri(String captionUri) {
        if (captionUri == null) {
            DLog.d_api(TAG, "[setCaptionUri] captionUri is null - set empty string");
            captionUri = "";
        }
        DLog.d_api(TAG, "[setCaptionUri] captionUri is " + captionUri);
        this.mCaptionUri = captionUri;
    }

    public void setCaptionType(CaptionType captionType) {
        if (captionType == null) {
            DLog.d_api(TAG, "[setCaptionType] captionType is null - set UNKNOWN");
            captionType = CaptionType.UNKNOWN;
        }
        DLog.d_api(TAG, "[setCaptionType] captionType is " + captionType.enumString);
        this.mCaptionType = captionType.enumString;
    }

    public void setLanguage(String language) {
        if (language == null) {
            DLog.d_api(TAG, "[setLanguage] language is null - set empty string");
            language = "";
        }
        DLog.d_api(TAG, "[setLanguage] language is " + language);
        this.mLanguage = language;
    }

    public void setEncoding(String encoding) {
        if (encoding == null) {
            DLog.d_api(TAG, "[setEncoding] encoding is null - set empty string");
            encoding = "";
        }
        DLog.d_api(TAG, "[setEncoding] encoding is " + encoding);
        this.mEncoding = encoding;
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
        List<String> languageList = new ArrayList<>();
        if (this.mLanguage.contains(",")) {
            String[] languageArray = this.mLanguage.split(",");
            for (String language : languageArray) {
                languageList.add(language.trim());
                DLog.d_api(TAG, "getLanguageList [add language]" + language.trim());
            }
        } else {
            languageList.add(this.mLanguage.trim());
            DLog.d_api(TAG, "getLanguageList [add language]" + this.mLanguage.trim());
        }
        return languageList;
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

    public static List<Caption> parseCaption(String captions) {
        if (captions == null) {
            DLog.w_api(TAG, "parseCaption caption is null");
            return null;
        }
        List<Caption> captionList = new ArrayList<>();
        try {
            XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
            XmlPullParser parser = factory.newPullParser();
            String captions2 = HTMLStringToTEXTString(captions);
            if (captions2 == null) {
                DLog.e_api("parseCaption", "captions is null");
                return null;
            }
            parser.setInput(new StringReader(captions2.trim()));
            String resourceURI = "";
            for (int eventType = parser.getEventType(); eventType != 1; eventType = parser.next()) {
                switch (eventType) {
                    case 2:
                        if ("sec:ResCaptionInfo".equals(parser.getName())) {
                            if ("resUri".equals(parser.getAttributeName(0))) {
                                resourceURI = parser.getAttributeValue(0);
                            }
                            break;
                        } else {
                            if (!"Captions".equals(parser.getName())) {
                                if (parser.getName().equals("captionFileInfo")) {
                                    Caption caption = new Caption();
                                    for (int i = 0; i < parser.getAttributeCount(); i++) {
                                        String attributeName = parser.getAttributeName(i);
                                        if (attributeName != null) {
                                            if (attributeName.equals("uri")) {
                                                caption.setCaptionUri(parser.getAttributeValue(i));
                                            } else if (attributeName.equals("name")) {
                                                caption.setName(parser.getAttributeValue(i));
                                            } else if (attributeName.equals("captionType")) {
                                                caption.setCaptionType(
                                                        CaptionType.stringToEnum(
                                                                parser.getAttributeValue(i)));
                                            } else if (attributeName.equals("language")) {
                                                String language = parser.getAttributeValue(i);
                                                caption.setLanguage(language);
                                            } else if (attributeName.equals("encoding")) {
                                                caption.setEncoding(parser.getAttributeValue(i));
                                            }
                                        }
                                    }
                                    caption.setResourceUri(resourceURI);
                                    DLog.d_api(TAG, "[parseCaption] - " + caption.toString());
                                    captionList.add(caption);
                                    break;
                                }
                            } else if ("resUri".equals(parser.getAttributeName(0))) {
                                resourceURI = parser.getAttributeValue(0);
                            }
                            break;
                        }
                    case 3:
                        if (parser.getName().equals("sec:ResCaptionInfo")) {
                            resourceURI = "";
                            break;
                        }
                        break;
                }
            }
            return captionList;
        } catch (Exception e) {
            DLog.e_api("parseCaption", "Exception - " + e);
            e.printStackTrace();
            return null;
        }
    }

    private static String HTMLStringToTEXTString(String htmlString) {
        if (htmlString == null) {
            DLog.e_api("HTMLStringToTEXTString", "string is null");
            return null;
        }
        String textString = htmlString.replaceAll("<br>", "\n");
        return textString
                .replaceAll("&gt;", ">")
                .replaceAll("&lt;", "<")
                .replaceAll("&quot;", "\"")
                .replaceAll("&nbsp;", " ")
                .replaceAll("&amp;", "&")
                .replaceAll(HtmlUtils.HTML_LINE_FEED, "\n");
    }

    public String toString() {
        return "Caption ResourceURI["
                + this.mResourceUri
                + "] Name["
                + this.mName
                + "] CaptionURI["
                + this.mCaptionUri
                + "] CaptionType["
                + this.mCaptionType
                + "] Language["
                + this.mLanguage
                + "] encoding["
                + this.mEncoding
                + NavigationBarInflaterView.SIZE_MOD_END;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mName);
        dest.writeString(this.mResourceUri);
        dest.writeString(this.mCaptionUri);
        dest.writeString(this.mCaptionType);
        dest.writeString(this.mLanguage);
        dest.writeString(this.mEncoding);
    }

    private void readFromParcel(Parcel src) {
        this.mName = src.readString();
        this.mResourceUri = src.readString();
        this.mCaptionUri = src.readString();
        this.mCaptionType = src.readString();
        this.mLanguage = src.readString();
        this.mEncoding = src.readString();
    }

    private Caption(Parcel src) {
        readFromParcel(src);
    }
}
