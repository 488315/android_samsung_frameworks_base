package android.graphics.fonts;

import android.graphics.fonts.FontFamilyUpdateRequest;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import android.text.FontConfig;
import com.android.modules.utils.TypedXmlSerializer;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public final class FontUpdateRequest implements Parcelable {
    public static final Parcelable.Creator<FontUpdateRequest> CREATOR = new Parcelable.Creator<FontUpdateRequest>() { // from class: android.graphics.fonts.FontUpdateRequest.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FontUpdateRequest createFromParcel(Parcel parcel) {
            return new FontUpdateRequest(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FontUpdateRequest[] newArray(int i) {
            return new FontUpdateRequest[i];
        }
    };
    public static final int TYPE_UPDATE_FONT_FAMILY = 1;
    public static final int TYPE_UPDATE_FONT_FILE = 0;
    private final ParcelFileDescriptor mFd;
    private final Family mFontFamily;
    private final byte[] mSignature;
    private final int mType;

    @Retention(RetentionPolicy.SOURCE)
    public @interface Type {
    }

    public static final class Font implements Parcelable {
        private static final String ATTR_AXIS = "axis";
        private static final String ATTR_INDEX = "index";
        private static final String ATTR_POSTSCRIPT_NAME = "name";
        private static final String ATTR_SLANT = "slant";
        private static final String ATTR_WEIGHT = "weight";
        public static final Parcelable.Creator<Font> CREATOR = new Parcelable.Creator<Font>() { // from class: android.graphics.fonts.FontUpdateRequest.Font.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Font createFromParcel(Parcel parcel) {
                String string8 = parcel.readString8();
                int i = parcel.readInt();
                int i2 = parcel.readInt();
                return new Font(string8, new FontStyle(i, i2), parcel.readInt(), parcel.readString8());
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Font[] newArray(int i) {
                return new Font[i];
            }
        };
        private final FontStyle mFontStyle;
        private final String mFontVariationSettings;
        private final int mIndex;
        private final String mPostScriptName;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public Font(String str, FontStyle fontStyle, int i, String str2) {
            this.mPostScriptName = str;
            this.mFontStyle = fontStyle;
            this.mIndex = i;
            this.mFontVariationSettings = str2;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString8(this.mPostScriptName);
            parcel.writeInt(this.mFontStyle.getWeight());
            parcel.writeInt(this.mFontStyle.getSlant());
            parcel.writeInt(this.mIndex);
            parcel.writeString8(this.mFontVariationSettings);
        }

        public static void writeToXml(TypedXmlSerializer typedXmlSerializer, Font font) throws IOException {
            typedXmlSerializer.attribute(null, "name", font.getPostScriptName());
            typedXmlSerializer.attributeInt(null, "index", font.getIndex());
            typedXmlSerializer.attributeInt(null, "weight", font.getFontStyle().getWeight());
            typedXmlSerializer.attributeInt(null, ATTR_SLANT, font.getFontStyle().getSlant());
            typedXmlSerializer.attribute(null, "axis", font.getFontVariationSettings());
        }

        public static Font readFromXml(XmlPullParser xmlPullParser) throws IOException {
            String attributeValue = xmlPullParser.getAttributeValue(null, "name");
            if (attributeValue == null) {
                throw new IOException("name attribute is missing in font tag.");
            }
            int attributeValueInt = FontUpdateRequest.getAttributeValueInt(xmlPullParser, "index", 0);
            int attributeValueInt2 = FontUpdateRequest.getAttributeValueInt(xmlPullParser, "weight", 400);
            int attributeValueInt3 = FontUpdateRequest.getAttributeValueInt(xmlPullParser, ATTR_SLANT, 0);
            String attributeValue2 = xmlPullParser.getAttributeValue(null, "axis");
            if (attributeValue2 == null) {
                attributeValue2 = "";
            }
            return new Font(attributeValue, new FontStyle(attributeValueInt2, attributeValueInt3), attributeValueInt, attributeValue2);
        }

        public String getPostScriptName() {
            return this.mPostScriptName;
        }

        public FontStyle getFontStyle() {
            return this.mFontStyle;
        }

        public int getIndex() {
            return this.mIndex;
        }

        public String getFontVariationSettings() {
            return this.mFontVariationSettings;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                Font font = (Font) obj;
                if (this.mIndex == font.mIndex && this.mPostScriptName.equals(font.mPostScriptName) && this.mFontStyle.equals(font.mFontStyle) && this.mFontVariationSettings.equals(font.mFontVariationSettings)) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(this.mPostScriptName, this.mFontStyle, Integer.valueOf(this.mIndex), this.mFontVariationSettings);
        }

        public String toString() {
            return "Font{mPostScriptName='" + this.mPostScriptName + "', mFontStyle=" + this.mFontStyle + ", mIndex=" + this.mIndex + ", mFontVariationSettings='" + this.mFontVariationSettings + "'}";
        }
    }

    public static final class Family implements Parcelable {
        private static final String ATTR_NAME = "name";
        public static final Parcelable.Creator<Family> CREATOR = new Parcelable.Creator<Family>() { // from class: android.graphics.fonts.FontUpdateRequest.Family.1
            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Family createFromParcel(Parcel parcel) {
                return new Family(parcel.readString8(), parcel.readParcelableList(new ArrayList(), Font.class.getClassLoader(), Font.class));
            }

            /* JADX WARN: Can't rename method to resolve collision */
            @Override // android.os.Parcelable.Creator
            public Family[] newArray(int i) {
                return new Family[i];
            }
        };
        private static final String TAG_FAMILY = "family";
        private static final String TAG_FONT = "font";
        private final List<Font> mFonts;
        private final String mName;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        public Family(String str, List<Font> list) {
            this.mName = str;
            this.mFonts = list;
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeString8(this.mName);
            parcel.writeParcelableList(this.mFonts, i);
        }

        public static void writeFamilyToXml(TypedXmlSerializer typedXmlSerializer, Family family) throws IOException {
            typedXmlSerializer.attribute(null, "name", family.getName());
            List<Font> fonts = family.getFonts();
            for (int i = 0; i < fonts.size(); i++) {
                Font font = fonts.get(i);
                typedXmlSerializer.startTag(null, "font");
                Font.writeToXml(typedXmlSerializer, font);
                typedXmlSerializer.endTag(null, "font");
            }
        }

        public static Family readFromXml(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
            ArrayList arrayList = new ArrayList();
            if (xmlPullParser.getEventType() != 2 || !xmlPullParser.getName().equals(TAG_FAMILY)) {
                throw new IOException("Unexpected parser state: must be START_TAG with family");
            }
            String attributeValue = xmlPullParser.getAttributeValue(null, "name");
            if (attributeValue == null) {
                throw new IOException("name attribute is missing in family tag.");
            }
            while (true) {
                int next = xmlPullParser.next();
                if (next == 1) {
                    break;
                }
                if (next == 2 && xmlPullParser.getName().equals("font")) {
                    arrayList.add(Font.readFromXml(xmlPullParser));
                } else if (next == 3 && xmlPullParser.getName().equals(TAG_FAMILY)) {
                    break;
                }
            }
            return new Family(attributeValue, arrayList);
        }

        public String getName() {
            return this.mName;
        }

        public List<Font> getFonts() {
            return this.mFonts;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj != null && getClass() == obj.getClass()) {
                Family family = (Family) obj;
                if (this.mName.equals(family.mName) && this.mFonts.equals(family.mFonts)) {
                    return true;
                }
            }
            return false;
        }

        public int hashCode() {
            return Objects.hash(this.mName, this.mFonts);
        }

        public String toString() {
            return "Family{mName='" + this.mName + "', mFonts=" + this.mFonts + '}';
        }
    }

    public FontUpdateRequest(ParcelFileDescriptor parcelFileDescriptor, byte[] bArr) {
        this.mType = 0;
        this.mFd = parcelFileDescriptor;
        this.mSignature = bArr;
        this.mFontFamily = null;
    }

    public FontUpdateRequest(Family family) {
        this.mType = 1;
        this.mFd = null;
        this.mSignature = null;
        this.mFontFamily = family;
    }

    public FontUpdateRequest(String str, List<FontFamilyUpdateRequest.Font> list) {
        this(createFontFamily(str, list));
    }

    private static Family createFontFamily(String str, List<FontFamilyUpdateRequest.Font> list) {
        ArrayList arrayList = new ArrayList(list.size());
        for (FontFamilyUpdateRequest.Font font : list) {
            arrayList.add(new Font(font.getPostScriptName(), font.getStyle(), font.getIndex(), FontVariationAxis.toFontVariationSettings(font.getAxes())));
        }
        return new Family(str, arrayList);
    }

    protected FontUpdateRequest(Parcel parcel) {
        this.mType = parcel.readInt();
        this.mFd = (ParcelFileDescriptor) parcel.readParcelable(ParcelFileDescriptor.class.getClassLoader(), ParcelFileDescriptor.class);
        this.mSignature = parcel.readBlob();
        this.mFontFamily = (Family) parcel.readParcelable(FontConfig.FontFamily.class.getClassLoader(), Family.class);
    }

    public int getType() {
        return this.mType;
    }

    public ParcelFileDescriptor getFd() {
        return this.mFd;
    }

    public byte[] getSignature() {
        return this.mSignature;
    }

    public Family getFontFamily() {
        return this.mFontFamily;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        ParcelFileDescriptor parcelFileDescriptor = this.mFd;
        if (parcelFileDescriptor != null) {
            return parcelFileDescriptor.describeContents();
        }
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mType);
        parcel.writeParcelable(this.mFd, i);
        parcel.writeBlob(this.mSignature);
        parcel.writeParcelable(this.mFontFamily, i);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int getAttributeValueInt(XmlPullParser xmlPullParser, String str, int i) {
        String attributeValue;
        try {
            attributeValue = xmlPullParser.getAttributeValue(null, str);
        } catch (NumberFormatException unused) {
        }
        return attributeValue == null ? i : Integer.parseInt(attributeValue);
    }
}
