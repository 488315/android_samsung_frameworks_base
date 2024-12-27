package android.content;

import android.annotation.NonNull;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.net.Uri;
import android.os.Parcel;
import android.os.PatternMatcher;
import android.util.proto.ProtoOutputStream;

import com.android.internal.util.AnnotationValidations;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

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
    public @interface UriPart {}

    public UriRelativeFilter(int uriPart, int patternType, String filter) {
        this.mUriPart = uriPart;
        AnnotationValidations.validate(
                (Class<? extends Annotation>) UriPart.class, (Annotation) null, this.mUriPart);
        this.mPatternType = patternType;
        AnnotationValidations.validate(
                (Class<? extends Annotation>) PatternMatcher.PatternType.class,
                (Annotation) null,
                this.mPatternType);
        this.mFilter = filter;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mFilter);
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

    public boolean matchData(Uri data) {
        PatternMatcher pe = new PatternMatcher(this.mFilter, this.mPatternType);
        switch (getUriPart()) {
            case 0:
                return pe.match(data.getPath());
            case 1:
                return matchQuery(pe, data.getQuery());
            case 2:
                return pe.match(data.getFragment());
            default:
                return false;
        }
    }

    private boolean matchQuery(PatternMatcher pe, String query) {
        if (query != null) {
            String[] params = query.split("&");
            if (params.length == 1) {
                params = query.split(NavigationBarInflaterView.GRAVITY_SEPARATOR);
            }
            for (String str : params) {
                if (pe.match(str)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public void dumpDebug(ProtoOutputStream proto, long fieldId) {
        long token = proto.start(fieldId);
        proto.write(1120986464257L, this.mUriPart);
        proto.write(1120986464258L, this.mPatternType);
        proto.write(1138166333443L, this.mFilter);
        proto.end(token);
    }

    public void writeToXml(XmlSerializer serializer) throws IOException {
        serializer.startTag(null, URI_RELATIVE_FILTER_STR);
        serializer.attribute(null, PATTERN_STR, Integer.toString(this.mPatternType));
        serializer.attribute(null, PART_STR, Integer.toString(this.mUriPart));
        serializer.attribute(null, FILTER_STR, this.mFilter);
        serializer.endTag(null, URI_RELATIVE_FILTER_STR);
    }

    private String uriPartToString() {
        switch (this.mUriPart) {
            case 0:
                return "PATH";
            case 1:
                return "QUERY";
            case 2:
                return "FRAGMENT";
            default:
                return "UNKNOWN";
        }
    }

    private String patternTypeToString() {
        switch (this.mPatternType) {
            case 0:
                return "LITERAL";
            case 1:
                return "PREFIX";
            case 2:
                return "GLOB";
            case 3:
                return "ADVANCED_GLOB";
            case 4:
                return "SUFFIX";
            default:
                return "UNKNOWN";
        }
    }

    public String toString() {
        return "UriRelativeFilter { uriPart = "
                + uriPartToString()
                + ", patternType = "
                + patternTypeToString()
                + ", filter = "
                + this.mFilter
                + " }";
    }

    public UriRelativeFilterParcel toParcel() {
        UriRelativeFilterParcel parcel = new UriRelativeFilterParcel();
        parcel.uriPart = this.mUriPart;
        parcel.patternType = this.mPatternType;
        parcel.filter = this.mFilter;
        return parcel;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UriRelativeFilter that = (UriRelativeFilter) o;
        if (this.mUriPart == that.mUriPart
                && this.mPatternType == that.mPatternType
                && Objects.equals(this.mFilter, that.mFilter)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int _hash = (1 * 31) + this.mUriPart;
        return (((_hash * 31) + this.mPatternType) * 31) + Objects.hashCode(this.mFilter);
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mUriPart);
        dest.writeInt(this.mPatternType);
        dest.writeString(this.mFilter);
    }

    UriRelativeFilter(Parcel in) {
        this.mUriPart = in.readInt();
        this.mPatternType = in.readInt();
        this.mFilter = in.readString();
    }

    public UriRelativeFilter(XmlPullParser parser) throws XmlPullParserException, IOException {
        this.mUriPart = Integer.parseInt(parser.getAttributeValue(null, PART_STR));
        this.mPatternType = Integer.parseInt(parser.getAttributeValue(null, PATTERN_STR));
        this.mFilter = parser.getAttributeValue(null, FILTER_STR);
    }

    public UriRelativeFilter(UriRelativeFilterParcel parcel) {
        this.mUriPart = parcel.uriPart;
        this.mPatternType = parcel.patternType;
        this.mFilter = parcel.filter;
    }
}
