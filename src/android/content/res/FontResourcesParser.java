package android.content.res;

import android.content.Context;
import android.content.res.Resources;
import android.util.Log;
import android.util.Xml;
import com.android.internal.R;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

/* loaded from: classes.dex */
public class FontResourcesParser {
    private static final String TAG = "FontResourcesParser";

    public interface FamilyResourceEntry {
    }

    public static final class ProviderResourceEntry implements FamilyResourceEntry {
        private final List<List<String>> mCerts;
        private final String mProviderAuthority;
        private final String mProviderPackage;
        private final String mQuery;
        private final String mSystemFontFamilyName;

        public ProviderResourceEntry(String str, String str2, String str3, List<List<String>> list, String str4) {
            this.mProviderAuthority = str;
            this.mProviderPackage = str2;
            this.mQuery = str3;
            this.mCerts = list;
            this.mSystemFontFamilyName = str4;
        }

        public String getAuthority() {
            return this.mProviderAuthority;
        }

        public String getPackage() {
            return this.mProviderPackage;
        }

        public String getQuery() {
            return this.mQuery;
        }

        public String getSystemFontFamilyName() {
            return this.mSystemFontFamilyName;
        }

        public List<List<String>> getCerts() {
            return this.mCerts;
        }
    }

    public static final class FontFileResourceEntry {
        public static final int ITALIC = 1;
        public static final int RESOLVE_BY_FONT_TABLE = -1;
        public static final int UPRIGHT = 0;
        private final String mFileName;
        private int mItalic;
        private int mResourceId;
        private int mTtcIndex;
        private String mVariationSettings;
        private int mWeight;

        public FontFileResourceEntry(String str, int i, int i2, String str2, int i3) {
            this.mFileName = str;
            this.mWeight = i;
            this.mItalic = i2;
            this.mVariationSettings = str2;
            this.mTtcIndex = i3;
        }

        public String getFileName() {
            return this.mFileName;
        }

        public int getWeight() {
            return this.mWeight;
        }

        public int getItalic() {
            return this.mItalic;
        }

        public String getVariationSettings() {
            return this.mVariationSettings;
        }

        public int getTtcIndex() {
            return this.mTtcIndex;
        }
    }

    public static final class FontFamilyFilesResourceEntry implements FamilyResourceEntry {
        private final FontFileResourceEntry[] mEntries;

        public FontFamilyFilesResourceEntry(FontFileResourceEntry[] fontFileResourceEntryArr) {
            this.mEntries = fontFileResourceEntryArr;
        }

        public FontFileResourceEntry[] getEntries() {
            return this.mEntries;
        }
    }

    public static FamilyResourceEntry parse(XmlPullParser xmlPullParser, Resources resources) throws XmlPullParserException, IOException {
        int next;
        do {
            next = xmlPullParser.next();
            if (next == 2) {
                break;
            }
        } while (next != 1);
        if (next != 2) {
            throw new XmlPullParserException("No start tag found");
        }
        return readFamilies(xmlPullParser, resources);
    }

    private static FamilyResourceEntry readFamilies(XmlPullParser xmlPullParser, Resources resources) throws XmlPullParserException, IOException {
        xmlPullParser.require(2, null, "font-family");
        if (xmlPullParser.getName().equals("font-family")) {
            return readFamily(xmlPullParser, resources);
        }
        skip(xmlPullParser);
        Log.e(TAG, "Failed to find font-family tag");
        return null;
    }

    private static FamilyResourceEntry readFamily(XmlPullParser xmlPullParser, Resources resources) throws XmlPullParserException, Resources.NotFoundException, IOException {
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(Xml.asAttributeSet(xmlPullParser), R.styleable.FontFamily);
        String string = typedArrayObtainAttributes.getString(0);
        String string2 = typedArrayObtainAttributes.getString(2);
        String string3 = typedArrayObtainAttributes.getString(1);
        int resourceId = typedArrayObtainAttributes.getResourceId(3, 0);
        String string4 = typedArrayObtainAttributes.getString(4);
        typedArrayObtainAttributes.recycle();
        ArrayList arrayList = null;
        if (string != null && string2 != null && string3 != null) {
            while (xmlPullParser.next() != 3) {
                skip(xmlPullParser);
            }
            if (resourceId != 0) {
                TypedArray typedArrayObtainTypedArray = resources.obtainTypedArray(resourceId);
                if (typedArrayObtainTypedArray.length() > 0) {
                    arrayList = new ArrayList();
                    if (typedArrayObtainTypedArray.getResourceId(0, 0) != 0) {
                        for (int i = 0; i < typedArrayObtainTypedArray.length(); i++) {
                            arrayList.add(Arrays.asList(resources.getStringArray(typedArrayObtainTypedArray.getResourceId(i, 0))));
                        }
                    } else {
                        arrayList.add(Arrays.asList(resources.getStringArray(resourceId)));
                    }
                }
                typedArrayObtainTypedArray.recycle();
            }
            return new ProviderResourceEntry(string, string2, string3, arrayList, string4);
        }
        ArrayList arrayList2 = new ArrayList();
        while (xmlPullParser.next() != 3) {
            if (xmlPullParser.getEventType() == 2) {
                if (xmlPullParser.getName().equals(Context.FONT_SERVICE)) {
                    FontFileResourceEntry font = readFont(xmlPullParser, resources);
                    if (font != null) {
                        arrayList2.add(font);
                    }
                } else {
                    skip(xmlPullParser);
                }
            }
        }
        if (arrayList2.isEmpty()) {
            return null;
        }
        return new FontFamilyFilesResourceEntry((FontFileResourceEntry[]) arrayList2.toArray(new FontFileResourceEntry[arrayList2.size()]));
    }

    private static FontFileResourceEntry readFont(XmlPullParser xmlPullParser, Resources resources) throws XmlPullParserException, IOException {
        TypedArray typedArrayObtainAttributes = resources.obtainAttributes(Xml.asAttributeSet(xmlPullParser), R.styleable.FontFamilyFont);
        int i = typedArrayObtainAttributes.getInt(1, -1);
        int i2 = typedArrayObtainAttributes.getInt(2, -1);
        String string = typedArrayObtainAttributes.getString(4);
        int i3 = typedArrayObtainAttributes.getInt(3, 0);
        String string2 = typedArrayObtainAttributes.getString(0);
        typedArrayObtainAttributes.recycle();
        while (xmlPullParser.next() != 3) {
            skip(xmlPullParser);
        }
        if (string2 == null) {
            return null;
        }
        return new FontFileResourceEntry(string2, i, i2, string, i3);
    }

    private static void skip(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        int i = 1;
        while (i > 0) {
            int next = xmlPullParser.next();
            if (next == 2) {
                i++;
            } else if (next == 3) {
                i--;
            }
        }
    }
}
