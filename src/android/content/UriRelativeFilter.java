package android.content;

import android.annotation.NonNull;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.os.Parcel;
import android.os.PatternMatcher;
import android.util.proto.ProtoOutputStream;
import com.android.internal.util.AnnotationValidations;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes.dex */
public final class UriRelativeFilter {
    private static final String FILTER_STR = "filter";
    public static final int FRAGMENT = 2;
    private static final String PART_STR = "part";
    public static final int PATH = 0;
    private static final String PATTERN_STR = "pattern";
    public static final int QUERY = 1;
    static final String URI_RELATIVE_FILTER_STR = "uriRelativeFilter";
    private final String mFilter;
    private final int mPatternType;
    private final int mUriPart;

    @Retention(RetentionPolicy.SOURCE)
    public @interface UriPart {
    }

    public UriRelativeFilter(int i, int i2, String str) {
        this.mUriPart = i;
        AnnotationValidations.validate((Class<? extends Annotation>) UriPart.class, (Annotation) null, i);
        this.mPatternType = i2;
        AnnotationValidations.validate((Class<? extends Annotation>) PatternMatcher.PatternType.class, (Annotation) null, i2);
        this.mFilter = str;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) str);
    }

    public int getUriPart() {
        return this.mUriPart;
    }

    public int getPatternType() {
        return this.mPatternType;
    }

    public String getFilter() {
        return this.mFilter;
    }

    public boolean matchData(Uri uri) {
        PatternMatcher patternMatcher = new PatternMatcher(this.mFilter, this.mPatternType);
        int uriPart = getUriPart();
        if (uriPart == 0) {
            return patternMatcher.match(uri.getPath());
        }
        if (uriPart == 1) {
            return matchQuery(patternMatcher, uri.getQuery());
        }
        if (uriPart != 2) {
            return false;
        }
        return patternMatcher.match(uri.getFragment());
    }

    private boolean matchQuery(PatternMatcher patternMatcher, String str) {
        if (str != null) {
            String[] strArrSplit = str.split("&");
            if (strArrSplit.length == 1) {
                strArrSplit = str.split(NavigationBarInflaterView.GRAVITY_SEPARATOR);
            }
            for (String str2 : strArrSplit) {
                if (patternMatcher.match(str2)) {
                    return true;
                }
            }
        }
        return false;
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        long jStart = protoOutputStream.start(j);
        protoOutputStream.write(1120986464257L, this.mUriPart);
        protoOutputStream.write(1120986464258L, this.mPatternType);
        protoOutputStream.write(1138166333443L, this.mFilter);
        protoOutputStream.end(jStart);
    }

    public void writeToXml(XmlSerializer xmlSerializer) throws IOException {
        xmlSerializer.startTag(null, URI_RELATIVE_FILTER_STR);
        xmlSerializer.attribute(null, PATTERN_STR, Integer.toString(this.mPatternType));
        xmlSerializer.attribute(null, PART_STR, Integer.toString(this.mUriPart));
        xmlSerializer.attribute(null, FILTER_STR, this.mFilter);
        xmlSerializer.endTag(null, URI_RELATIVE_FILTER_STR);
    }

    private String uriPartToString() {
        int i = this.mUriPart;
        if (i == 0) {
            return "PATH";
        }
        if (i == 1) {
            return "QUERY";
        }
        if (i == 2) {
            return "FRAGMENT";
        }
        return "UNKNOWN";
    }

    private String patternTypeToString() {
        int i = this.mPatternType;
        if (i == 0) {
            return "LITERAL";
        }
        if (i == 1) {
            return "PREFIX";
        }
        if (i == 2) {
            return "GLOB";
        }
        if (i == 3) {
            return "ADVANCED_GLOB";
        }
        if (i == 4) {
            return "SUFFIX";
        }
        return "UNKNOWN";
    }

    public String toString() {
        return "UriRelativeFilter { uriPart = " + uriPartToString() + ", patternType = " + patternTypeToString() + ", filter = " + this.mFilter + " }";
    }

    public UriRelativeFilterParcel toParcel() {
        UriRelativeFilterParcel uriRelativeFilterParcel = new UriRelativeFilterParcel();
        uriRelativeFilterParcel.uriPart = this.mUriPart;
        uriRelativeFilterParcel.patternType = this.mPatternType;
        uriRelativeFilterParcel.filter = this.mFilter;
        return uriRelativeFilterParcel;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj != null && getClass() == obj.getClass()) {
            UriRelativeFilter uriRelativeFilter = (UriRelativeFilter) obj;
            if (this.mUriPart == uriRelativeFilter.mUriPart && this.mPatternType == uriRelativeFilter.mPatternType && Objects.equals(this.mFilter, uriRelativeFilter.mFilter)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((((this.mUriPart + 31) * 31) + this.mPatternType) * 31) + Objects.hashCode(this.mFilter);
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mUriPart);
        parcel.writeInt(this.mPatternType);
        parcel.writeString(this.mFilter);
    }

    UriRelativeFilter(Parcel parcel) {
        this.mUriPart = parcel.readInt();
        this.mPatternType = parcel.readInt();
        this.mFilter = parcel.readString();
    }

    public UriRelativeFilter(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        this.mUriPart = Integer.parseInt(xmlPullParser.getAttributeValue(null, PART_STR));
        this.mPatternType = Integer.parseInt(xmlPullParser.getAttributeValue(null, PATTERN_STR));
        this.mFilter = xmlPullParser.getAttributeValue(null, FILTER_STR);
    }

    public UriRelativeFilter(UriRelativeFilterParcel uriRelativeFilterParcel) {
        this.mUriPart = uriRelativeFilterParcel.uriPart;
        this.mPatternType = uriRelativeFilterParcel.patternType;
        this.mFilter = uriRelativeFilterParcel.filter;
    }
}
