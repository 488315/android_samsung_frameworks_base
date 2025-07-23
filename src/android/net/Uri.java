package android.net;

import android.annotation.SystemApi;
import android.content.Context;
import android.content.Intent;
import android.os.Environment;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.StrictMode;
import android.telecom.PhoneAccount;
import android.util.Log;
import com.android.internal.accessibility.common.ShortcutConstants;
import com.android.internal.hidden_from_bootclasspath.android.content.pm.Flags;
import com.android.internal.transition.EpicenterTranslateClipReveal;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.RandomAccess;
import java.util.Set;

/* loaded from: classes3.dex */
public abstract class Uri implements Parcelable, Comparable<Uri> {
    private static final String DEFAULT_ENCODING = "UTF-8";
    private static final String LOG = "Uri";
    private static final int NOT_CALCULATED = -2;
    private static final int NOT_FOUND = -1;
    private static final String NOT_HIERARCHICAL = "This isn't a hierarchical URI.";
    private static final int NULL_TYPE_ID = 0;
    public static final Uri EMPTY = new HierarchicalUri(null, Part.NULL, PathPart.EMPTY, Part.NULL, Part.NULL);
    public static final Parcelable.Creator<Uri> CREATOR = new Parcelable.Creator<Uri>() { // from class: android.net.Uri.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Uri createFromParcel(Parcel parcel) {
            int readInt = parcel.readInt();
            if (readInt == 0) {
                return null;
            }
            if (readInt == 1) {
                return StringUri.readFrom(parcel);
            }
            if (readInt == 2) {
                return OpaqueUri.readFrom(parcel);
            }
            if (readInt == 3) {
                return HierarchicalUri.readFrom(parcel);
            }
            throw new IllegalArgumentException("Unknown URI type: " + readInt);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public Uri[] newArray(int i) {
            return new Uri[i];
        }
    };
    private static final char[] HEX_DIGITS = "0123456789ABCDEF".toCharArray();

    public abstract Builder buildUpon();

    public abstract String getAuthority();

    public abstract String getEncodedAuthority();

    public abstract String getEncodedFragment();

    public abstract String getEncodedPath();

    public abstract String getEncodedQuery();

    public abstract String getEncodedSchemeSpecificPart();

    public abstract String getEncodedUserInfo();

    public abstract String getFragment();

    public abstract String getHost();

    public abstract String getLastPathSegment();

    public abstract String getPath();

    public abstract List<String> getPathSegments();

    public abstract int getPort();

    public abstract String getQuery();

    public abstract String getScheme();

    public abstract String getSchemeSpecificPart();

    public abstract String getUserInfo();

    public abstract boolean isHierarchical();

    public abstract boolean isRelative();

    public abstract String toString();

    static class NotCachedHolder {
        static final String NOT_CACHED = new String("NOT CACHED");

        private NotCachedHolder() {
        }
    }

    private Uri() {
    }

    public boolean isOpaque() {
        return !isHierarchical();
    }

    public boolean isAbsolute() {
        return !isRelative();
    }

    public boolean equals(Object obj) {
        if (obj instanceof Uri) {
            return toString().equals(((Uri) obj).toString());
        }
        return false;
    }

    public int hashCode() {
        return toString().hashCode();
    }

    @Override // java.lang.Comparable
    public int compareTo(Uri uri) {
        return toString().compareTo(uri.toString());
    }

    @SystemApi
    public String toSafeString() {
        String scheme = getScheme();
        String schemeSpecificPart = getSchemeSpecificPart();
        StringBuilder sb = new StringBuilder(64);
        if (scheme != null) {
            sb.append(scheme);
            sb.append(":");
            if (!scheme.equalsIgnoreCase(PhoneAccount.SCHEME_TEL) && !scheme.equalsIgnoreCase("sip") && !scheme.equalsIgnoreCase(Context.SMS_SERVICE) && !scheme.equalsIgnoreCase("smsto") && !scheme.equalsIgnoreCase("mailto") && !scheme.equalsIgnoreCase("nfc")) {
                String host = getHost();
                int port = getPort();
                String path = getPath();
                String authority = getAuthority();
                if (authority != null) {
                    sb.append("//");
                }
                if (host != null) {
                    sb.append(host);
                }
                if (port != -1) {
                    sb.append(":");
                    sb.append(port);
                }
                if (authority != null || path != null) {
                    sb.append("/...");
                }
            } else if (schemeSpecificPart != null) {
                for (int i = 0; i < schemeSpecificPart.length(); i++) {
                    char charAt = schemeSpecificPart.charAt(i);
                    if (charAt == '-' || charAt == '@' || charAt == '.') {
                        sb.append(charAt);
                    } else {
                        sb.append(EpicenterTranslateClipReveal.StateProperty.TARGET_X);
                    }
                }
            }
        }
        return sb.toString();
    }

    public static Uri parse(String str) {
        return new StringUri(str);
    }

    public static Uri fromFile(File file) {
        if (file == null) {
            throw new NullPointerException("file");
        }
        return new HierarchicalUri("file", Part.EMPTY, PathPart.fromDecoded(file.getAbsolutePath()), Part.NULL, Part.NULL);
    }

    private static class StringUri extends AbstractHierarchicalUri {
        static final int TYPE_ID = 1;
        private Part authority;
        private volatile int cachedFsi;
        private volatile int cachedSsi;
        private Part fragment;
        private PathPart path;
        private Part query;
        private volatile String scheme;
        private Part ssp;
        private final String uriString;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        private StringUri(String str) {
            super();
            this.cachedSsi = -2;
            this.cachedFsi = -2;
            this.scheme = NotCachedHolder.NOT_CACHED;
            if (str == null) {
                throw new NullPointerException("uriString");
            }
            this.uriString = str;
        }

        static Uri readFrom(Parcel parcel) {
            return new StringUri(parcel.readString8());
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(1);
            parcel.writeString8(this.uriString);
        }

        private int findSchemeSeparator() {
            if (this.cachedSsi == -2) {
                int indexOf = this.uriString.indexOf(58);
                this.cachedSsi = indexOf;
                return indexOf;
            }
            return this.cachedSsi;
        }

        private int findFragmentSeparator() {
            if (this.cachedFsi == -2) {
                int indexOf = this.uriString.indexOf(35, findSchemeSeparator());
                this.cachedFsi = indexOf;
                return indexOf;
            }
            return this.cachedFsi;
        }

        @Override // android.net.Uri
        public boolean isHierarchical() {
            int findSchemeSeparator = findSchemeSeparator();
            if (findSchemeSeparator == -1) {
                return true;
            }
            int i = findSchemeSeparator + 1;
            return this.uriString.length() != i && this.uriString.charAt(i) == '/';
        }

        @Override // android.net.Uri
        public boolean isRelative() {
            return findSchemeSeparator() == -1;
        }

        @Override // android.net.Uri
        public String getScheme() {
            if (this.scheme != NotCachedHolder.NOT_CACHED) {
                return this.scheme;
            }
            String parseScheme = parseScheme();
            this.scheme = parseScheme;
            return parseScheme;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public String parseScheme() {
            int findSchemeSeparator = findSchemeSeparator();
            if (findSchemeSeparator == -1) {
                return null;
            }
            return this.uriString.substring(0, findSchemeSeparator);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Part getSsp() {
            Part part = this.ssp;
            if (part != null) {
                return part;
            }
            Part fromEncoded = Part.fromEncoded(parseSsp());
            this.ssp = fromEncoded;
            return fromEncoded;
        }

        @Override // android.net.Uri
        public String getEncodedSchemeSpecificPart() {
            return getSsp().getEncoded();
        }

        @Override // android.net.Uri
        public String getSchemeSpecificPart() {
            return getSsp().getDecoded();
        }

        private String parseSsp() {
            int findSchemeSeparator = findSchemeSeparator();
            int findFragmentSeparator = findFragmentSeparator();
            if (findFragmentSeparator == -1) {
                return this.uriString.substring(findSchemeSeparator + 1);
            }
            return this.uriString.substring(findSchemeSeparator + 1, findFragmentSeparator);
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Part getAuthorityPart() {
            Part part = this.authority;
            if (part != null) {
                return part;
            }
            Part fromEncoded = Part.fromEncoded(parseAuthority(this.uriString, findSchemeSeparator()));
            this.authority = fromEncoded;
            return fromEncoded;
        }

        @Override // android.net.Uri
        public String getEncodedAuthority() {
            return getAuthorityPart().getEncoded();
        }

        @Override // android.net.Uri
        public String getAuthority() {
            return getAuthorityPart().getDecoded();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public PathPart getPathPart() {
            PathPart pathPart = this.path;
            if (pathPart != null) {
                return pathPart;
            }
            PathPart fromEncoded = PathPart.fromEncoded(parsePath());
            this.path = fromEncoded;
            return fromEncoded;
        }

        @Override // android.net.Uri
        public String getPath() {
            return getPathPart().getDecoded();
        }

        @Override // android.net.Uri
        public String getEncodedPath() {
            return getPathPart().getEncoded();
        }

        @Override // android.net.Uri
        public List<String> getPathSegments() {
            return getPathPart().getPathSegments();
        }

        private String parsePath() {
            int i;
            String str = this.uriString;
            int findSchemeSeparator = findSchemeSeparator();
            if (findSchemeSeparator <= -1 || ((i = findSchemeSeparator + 1) != str.length() && str.charAt(i) == '/')) {
                return parsePath(str, findSchemeSeparator);
            }
            return null;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Part getQueryPart() {
            Part part = this.query;
            if (part != null) {
                return part;
            }
            Part fromEncoded = Part.fromEncoded(parseQuery());
            this.query = fromEncoded;
            return fromEncoded;
        }

        @Override // android.net.Uri
        public String getEncodedQuery() {
            return getQueryPart().getEncoded();
        }

        private String parseQuery() {
            int indexOf = this.uriString.indexOf(63, findSchemeSeparator());
            if (indexOf == -1) {
                return null;
            }
            int findFragmentSeparator = findFragmentSeparator();
            if (findFragmentSeparator == -1) {
                return this.uriString.substring(indexOf + 1);
            }
            if (findFragmentSeparator < indexOf) {
                return null;
            }
            return this.uriString.substring(indexOf + 1, findFragmentSeparator);
        }

        @Override // android.net.Uri
        public String getQuery() {
            return getQueryPart().getDecoded();
        }

        /* JADX INFO: Access modifiers changed from: private */
        public Part getFragmentPart() {
            Part part = this.fragment;
            if (part != null) {
                return part;
            }
            Part fromEncoded = Part.fromEncoded(parseFragment());
            this.fragment = fromEncoded;
            return fromEncoded;
        }

        @Override // android.net.Uri
        public String getEncodedFragment() {
            return getFragmentPart().getEncoded();
        }

        private String parseFragment() {
            int findFragmentSeparator = findFragmentSeparator();
            if (findFragmentSeparator == -1) {
                return null;
            }
            return this.uriString.substring(findFragmentSeparator + 1);
        }

        @Override // android.net.Uri
        public String getFragment() {
            return getFragmentPart().getDecoded();
        }

        @Override // android.net.Uri
        public String toString() {
            return this.uriString;
        }

        static String parseAuthority(String str, int i) {
            int length = str.length();
            int i2 = i + 2;
            if (length <= i2 || str.charAt(i + 1) != '/' || str.charAt(i2) != '/') {
                return null;
            }
            int i3 = i + 3;
            int i4 = i3;
            while (i4 < length) {
                char charAt = str.charAt(i4);
                if (charAt == '#' || charAt == '/' || charAt == '?' || charAt == '\\') {
                    break;
                }
                i4++;
            }
            return str.substring(i3, i4);
        }

        static String parsePath(String str, int i) {
            int i2;
            int length = str.length();
            int i3 = i + 2;
            if (length > i3 && str.charAt(i + 1) == '/' && str.charAt(i3) == '/') {
                i2 = i + 3;
                while (i2 < length) {
                    char charAt = str.charAt(i2);
                    if (charAt == '#') {
                        return "";
                    }
                    if (charAt == '/') {
                        break;
                    }
                    if (charAt == '?') {
                        return "";
                    }
                    if (charAt == '\\') {
                        break;
                    }
                    i2++;
                }
            } else {
                i2 = i + 1;
            }
            int i4 = i2;
            while (i4 < length) {
                char charAt2 = str.charAt(i4);
                if (charAt2 == '#' || charAt2 == '?') {
                    break;
                }
                i4++;
            }
            return str.substring(i2, i4);
        }

        @Override // android.net.Uri
        public Builder buildUpon() {
            if (isHierarchical()) {
                return new Builder().scheme(getScheme()).authority(getAuthorityPart()).path(getPathPart()).query(getQueryPart()).fragment(getFragmentPart());
            }
            return new Builder().scheme(getScheme()).opaquePart(getSsp()).fragment(getFragmentPart());
        }
    }

    public static Uri fromParts(String str, String str2, String str3) {
        if (str == null) {
            throw new NullPointerException("scheme");
        }
        if (str2 == null) {
            throw new NullPointerException("ssp");
        }
        return new OpaqueUri(str, Part.fromDecoded(str2), Part.fromDecoded(str3));
    }

    private static class OpaqueUri extends Uri {
        static final int TYPE_ID = 2;
        private volatile String cachedString;
        private final Part fragment;
        private final String scheme;
        private final Part ssp;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.net.Uri
        public String getAuthority() {
            return null;
        }

        @Override // android.net.Uri
        public String getEncodedAuthority() {
            return null;
        }

        @Override // android.net.Uri
        public String getEncodedPath() {
            return null;
        }

        @Override // android.net.Uri
        public String getEncodedQuery() {
            return null;
        }

        @Override // android.net.Uri
        public String getEncodedUserInfo() {
            return null;
        }

        @Override // android.net.Uri
        public String getHost() {
            return null;
        }

        @Override // android.net.Uri
        public String getLastPathSegment() {
            return null;
        }

        @Override // android.net.Uri
        public String getPath() {
            return null;
        }

        @Override // android.net.Uri
        public int getPort() {
            return -1;
        }

        @Override // android.net.Uri
        public String getQuery() {
            return null;
        }

        @Override // android.net.Uri
        public String getUserInfo() {
            return null;
        }

        @Override // android.net.Uri
        public boolean isHierarchical() {
            return false;
        }

        private OpaqueUri(String str, Part part, Part part2) {
            super();
            this.cachedString = NotCachedHolder.NOT_CACHED;
            this.scheme = str;
            this.ssp = part;
            this.fragment = part2 == null ? Part.NULL : part2;
        }

        static Uri readFrom(Parcel parcel) {
            StringUri stringUri = new StringUri(parcel.readString8());
            return new OpaqueUri(stringUri.parseScheme(), stringUri.getSsp(), stringUri.getFragmentPart());
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(2);
            parcel.writeString8(toString());
        }

        @Override // android.net.Uri
        public boolean isRelative() {
            return this.scheme == null;
        }

        @Override // android.net.Uri
        public String getScheme() {
            return this.scheme;
        }

        @Override // android.net.Uri
        public String getEncodedSchemeSpecificPart() {
            return this.ssp.getEncoded();
        }

        @Override // android.net.Uri
        public String getSchemeSpecificPart() {
            return this.ssp.getDecoded();
        }

        @Override // android.net.Uri
        public String getFragment() {
            return this.fragment.getDecoded();
        }

        @Override // android.net.Uri
        public String getEncodedFragment() {
            return this.fragment.getEncoded();
        }

        @Override // android.net.Uri
        public List<String> getPathSegments() {
            return Collections.EMPTY_LIST;
        }

        @Override // android.net.Uri
        public String toString() {
            if (this.cachedString != NotCachedHolder.NOT_CACHED) {
                return this.cachedString;
            }
            StringBuilder sb = new StringBuilder();
            sb.append(this.scheme);
            sb.append(ShortcutConstants.SERVICES_SEPARATOR);
            sb.append(getEncodedSchemeSpecificPart());
            if (!this.fragment.isEmpty()) {
                sb.append('#');
                sb.append(this.fragment.getEncoded());
            }
            String sb2 = sb.toString();
            this.cachedString = sb2;
            return sb2;
        }

        @Override // android.net.Uri
        public Builder buildUpon() {
            return new Builder().scheme(this.scheme).opaquePart(this.ssp).fragment(this.fragment);
        }
    }

    static class PathSegments extends AbstractList<String> implements RandomAccess {
        static final PathSegments EMPTY = new PathSegments(null, 0);
        final String[] segments;
        final int size;

        PathSegments(String[] strArr, int i) {
            this.segments = strArr;
            this.size = i;
        }

        @Override // java.util.AbstractList, java.util.List
        public String get(int i) {
            if (i >= this.size) {
                throw new IndexOutOfBoundsException();
            }
            return this.segments[i];
        }

        @Override // java.util.AbstractCollection, java.util.Collection, java.util.List
        public int size() {
            return this.size;
        }
    }

    static class PathSegmentsBuilder {
        String[] segments;
        int size = 0;

        PathSegmentsBuilder() {
        }

        void add(String str) {
            String[] strArr = this.segments;
            if (strArr == null) {
                this.segments = new String[4];
            } else if (this.size + 1 == strArr.length) {
                String[] strArr2 = new String[strArr.length * 2];
                System.arraycopy(strArr, 0, strArr2, 0, strArr.length);
                this.segments = strArr2;
            }
            String[] strArr3 = this.segments;
            int i = this.size;
            this.size = i + 1;
            strArr3[i] = str;
        }

        PathSegments build() {
            if (this.segments == null) {
                return PathSegments.EMPTY;
            }
            try {
                return new PathSegments(this.segments, this.size);
            } finally {
                this.segments = null;
            }
        }
    }

    private static abstract class AbstractHierarchicalUri extends Uri {
        private volatile String host;
        private volatile int port;
        private Part userInfo;

        private AbstractHierarchicalUri() {
            super();
            this.host = NotCachedHolder.NOT_CACHED;
            this.port = -2;
        }

        @Override // android.net.Uri
        public String getLastPathSegment() {
            List<String> pathSegments = getPathSegments();
            int size = pathSegments.size();
            if (size == 0) {
                return null;
            }
            return pathSegments.get(size - 1);
        }

        private Part getUserInfoPart() {
            Part part = this.userInfo;
            if (part != null) {
                return part;
            }
            Part fromEncoded = Part.fromEncoded(parseUserInfo());
            this.userInfo = fromEncoded;
            return fromEncoded;
        }

        @Override // android.net.Uri
        public final String getEncodedUserInfo() {
            return getUserInfoPart().getEncoded();
        }

        private String parseUserInfo() {
            int lastIndexOf;
            String encodedAuthority = getEncodedAuthority();
            if (encodedAuthority == null || (lastIndexOf = encodedAuthority.lastIndexOf(64)) == -1) {
                return null;
            }
            return encodedAuthority.substring(0, lastIndexOf);
        }

        @Override // android.net.Uri
        public String getUserInfo() {
            return getUserInfoPart().getDecoded();
        }

        @Override // android.net.Uri
        public String getHost() {
            if (this.host != NotCachedHolder.NOT_CACHED) {
                return this.host;
            }
            String parseHost = parseHost();
            this.host = parseHost;
            return parseHost;
        }

        private String parseHost() {
            String substring;
            String encodedAuthority = getEncodedAuthority();
            if (encodedAuthority == null) {
                return null;
            }
            int lastIndexOf = encodedAuthority.lastIndexOf(64);
            int findPortSeparator = findPortSeparator(encodedAuthority);
            if (findPortSeparator == -1) {
                substring = encodedAuthority.substring(lastIndexOf + 1);
            } else {
                substring = encodedAuthority.substring(lastIndexOf + 1, findPortSeparator);
            }
            return decode(substring);
        }

        @Override // android.net.Uri
        public int getPort() {
            if (this.port == -2) {
                int parsePort = parsePort();
                this.port = parsePort;
                return parsePort;
            }
            return this.port;
        }

        private int parsePort() {
            String encodedAuthority = getEncodedAuthority();
            int findPortSeparator = findPortSeparator(encodedAuthority);
            if (findPortSeparator == -1) {
                return -1;
            }
            try {
                return Integer.parseInt(decode(encodedAuthority.substring(findPortSeparator + 1)));
            } catch (NumberFormatException e) {
                Log.w(Uri.LOG, "Error parsing port string.", e);
                return -1;
            }
        }

        private int findPortSeparator(String str) {
            if (str == null) {
                return -1;
            }
            for (int length = str.length() - 1; length >= 0; length--) {
                char charAt = str.charAt(length);
                if (':' == charAt) {
                    return length;
                }
                if (charAt < '0' || charAt > '9') {
                    break;
                }
            }
            return -1;
        }
    }

    private static class HierarchicalUri extends AbstractHierarchicalUri {
        static final int TYPE_ID = 3;
        private final Part authority;
        private final Part fragment;
        private final PathPart path;
        private final Part query;
        private final String scheme;
        private Part ssp;
        private volatile String uriString;

        @Override // android.os.Parcelable
        public int describeContents() {
            return 0;
        }

        @Override // android.net.Uri
        public boolean isHierarchical() {
            return true;
        }

        private HierarchicalUri(String str, Part part, PathPart pathPart, Part part2, Part part3) {
            super();
            this.uriString = NotCachedHolder.NOT_CACHED;
            this.scheme = str;
            this.authority = Part.nonNull(part);
            this.path = generatePath(pathPart);
            this.query = Part.nonNull(part2);
            this.fragment = Part.nonNull(part3);
        }

        private PathPart generatePath(PathPart pathPart) {
            String str = this.scheme;
            if ((str != null && str.length() > 0) || !this.authority.isEmpty()) {
                pathPart = PathPart.makeAbsolute(pathPart);
            }
            return pathPart == null ? PathPart.NULL : pathPart;
        }

        static Uri readFrom(Parcel parcel) {
            StringUri stringUri = new StringUri(parcel.readString8());
            return new HierarchicalUri(stringUri.getScheme(), stringUri.getAuthorityPart(), stringUri.getPathPart(), stringUri.getQueryPart(), stringUri.getFragmentPart());
        }

        @Override // android.os.Parcelable
        public void writeToParcel(Parcel parcel, int i) {
            parcel.writeInt(3);
            parcel.writeString8(toString());
        }

        @Override // android.net.Uri
        public boolean isRelative() {
            return this.scheme == null;
        }

        @Override // android.net.Uri
        public String getScheme() {
            return this.scheme;
        }

        private Part getSsp() {
            Part part = this.ssp;
            if (part != null) {
                return part;
            }
            Part fromEncoded = Part.fromEncoded(makeSchemeSpecificPart());
            this.ssp = fromEncoded;
            return fromEncoded;
        }

        @Override // android.net.Uri
        public String getEncodedSchemeSpecificPart() {
            return getSsp().getEncoded();
        }

        @Override // android.net.Uri
        public String getSchemeSpecificPart() {
            return getSsp().getDecoded();
        }

        private String makeSchemeSpecificPart() {
            StringBuilder sb = new StringBuilder();
            appendSspTo(sb);
            return sb.toString();
        }

        private void appendSspTo(StringBuilder sb) {
            String encoded = this.authority.getEncoded();
            if (encoded != null) {
                sb.append("//");
                sb.append(encoded);
            }
            String encoded2 = this.path.getEncoded();
            if (encoded2 != null) {
                sb.append(encoded2);
            }
            if (this.query.isEmpty()) {
                return;
            }
            sb.append('?');
            sb.append(this.query.getEncoded());
        }

        @Override // android.net.Uri
        public String getAuthority() {
            return this.authority.getDecoded();
        }

        @Override // android.net.Uri
        public String getEncodedAuthority() {
            return this.authority.getEncoded();
        }

        @Override // android.net.Uri
        public String getEncodedPath() {
            return this.path.getEncoded();
        }

        @Override // android.net.Uri
        public String getPath() {
            return this.path.getDecoded();
        }

        @Override // android.net.Uri
        public String getQuery() {
            return this.query.getDecoded();
        }

        @Override // android.net.Uri
        public String getEncodedQuery() {
            return this.query.getEncoded();
        }

        @Override // android.net.Uri
        public String getFragment() {
            return this.fragment.getDecoded();
        }

        @Override // android.net.Uri
        public String getEncodedFragment() {
            return this.fragment.getEncoded();
        }

        @Override // android.net.Uri
        public List<String> getPathSegments() {
            return this.path.getPathSegments();
        }

        @Override // android.net.Uri
        public String toString() {
            if (this.uriString != NotCachedHolder.NOT_CACHED) {
                return this.uriString;
            }
            String makeUriString = makeUriString();
            this.uriString = makeUriString;
            return makeUriString;
        }

        private String makeUriString() {
            StringBuilder sb = new StringBuilder();
            String str = this.scheme;
            if (str != null) {
                sb.append(str);
                sb.append(ShortcutConstants.SERVICES_SEPARATOR);
            }
            appendSspTo(sb);
            if (!this.fragment.isEmpty()) {
                sb.append('#');
                sb.append(this.fragment.getEncoded());
            }
            return sb.toString();
        }

        @Override // android.net.Uri
        public Builder buildUpon() {
            return new Builder().scheme(this.scheme).authority(this.authority).path(this.path).query(this.query).fragment(this.fragment);
        }
    }

    public static final class Builder {
        private Part authority;
        private Part fragment;
        private Part opaquePart;
        private PathPart path;
        private Part query;
        private String scheme;

        public Builder scheme(String str) {
            if (str != null) {
                this.scheme = str.replace("://", "");
                return this;
            }
            this.scheme = null;
            return this;
        }

        Builder opaquePart(Part part) {
            this.opaquePart = part;
            return this;
        }

        public Builder opaquePart(String str) {
            return opaquePart(Part.fromDecoded(str));
        }

        public Builder encodedOpaquePart(String str) {
            return opaquePart(Part.fromEncoded(str));
        }

        Builder authority(Part part) {
            this.opaquePart = null;
            this.authority = part;
            return this;
        }

        public Builder authority(String str) {
            return authority(Part.fromDecoded(str));
        }

        public Builder encodedAuthority(String str) {
            return authority(Part.fromEncoded(str));
        }

        Builder path(PathPart pathPart) {
            this.opaquePart = null;
            this.path = pathPart;
            return this;
        }

        public Builder path(String str) {
            return path(PathPart.fromDecoded(str));
        }

        public Builder encodedPath(String str) {
            return path(PathPart.fromEncoded(str));
        }

        public Builder appendPath(String str) {
            return path(PathPart.appendDecodedSegment(this.path, str));
        }

        public Builder appendEncodedPath(String str) {
            return path(PathPart.appendEncodedSegment(this.path, str));
        }

        Builder query(Part part) {
            this.opaquePart = null;
            this.query = part;
            return this;
        }

        public Builder query(String str) {
            return query(Part.fromDecoded(str));
        }

        public Builder encodedQuery(String str) {
            return query(Part.fromEncoded(str));
        }

        Builder fragment(Part part) {
            this.fragment = part;
            return this;
        }

        public Builder fragment(String str) {
            return fragment(Part.fromDecoded(str));
        }

        public Builder encodedFragment(String str) {
            return fragment(Part.fromEncoded(str));
        }

        public Builder appendQueryParameter(String str, String str2) {
            this.opaquePart = null;
            String str3 = Uri.encode(str, null) + "=" + Uri.encode(str2, null);
            Part part = this.query;
            if (part == null) {
                this.query = Part.fromEncoded(str3);
                return this;
            }
            String encoded = part.getEncoded();
            if (encoded == null || encoded.length() == 0) {
                this.query = Part.fromEncoded(str3);
                return this;
            }
            this.query = Part.fromEncoded(encoded + "&" + str3);
            return this;
        }

        public Builder clearQuery() {
            return query((Part) null);
        }

        public Uri build() {
            if (this.opaquePart != null) {
                if (this.scheme == null) {
                    throw new UnsupportedOperationException("An opaque URI must have a scheme.");
                }
                return new OpaqueUri(this.scheme, this.opaquePart, this.fragment);
            }
            PathPart pathPart = this.path;
            if (pathPart == null || pathPart == PathPart.NULL) {
                pathPart = PathPart.EMPTY;
            } else if (hasSchemeOrAuthority()) {
                pathPart = PathPart.makeAbsolute(pathPart);
            }
            return new HierarchicalUri(this.scheme, this.authority, pathPart, this.query, this.fragment);
        }

        private boolean hasSchemeOrAuthority() {
            if (this.scheme != null) {
                return true;
            }
            Part part = this.authority;
            return (part == null || part == Part.NULL) ? false : true;
        }

        public String toString() {
            return build().toString();
        }
    }

    public Set<String> getQueryParameterNames() {
        if (isOpaque()) {
            throw new UnsupportedOperationException(NOT_HIERARCHICAL);
        }
        String encodedQuery = getEncodedQuery();
        if (encodedQuery == null) {
            return Collections.EMPTY_SET;
        }
        LinkedHashSet linkedHashSet = new LinkedHashSet();
        int i = 0;
        do {
            int indexOf = encodedQuery.indexOf(38, i);
            if (indexOf == -1) {
                indexOf = encodedQuery.length();
            }
            int indexOf2 = encodedQuery.indexOf(61, i);
            if (indexOf2 > indexOf || indexOf2 == -1) {
                indexOf2 = indexOf;
            }
            linkedHashSet.add(decode(encodedQuery.substring(i, indexOf2)));
            i = indexOf + 1;
        } while (i < encodedQuery.length());
        return Collections.unmodifiableSet(linkedHashSet);
    }

    public List<String> getQueryParameters(String str) {
        if (isOpaque()) {
            throw new UnsupportedOperationException(NOT_HIERARCHICAL);
        }
        if (str == null) {
            throw new NullPointerException("key");
        }
        String encodedQuery = getEncodedQuery();
        if (encodedQuery == null) {
            return Collections.EMPTY_LIST;
        }
        try {
            String encode = URLEncoder.encode(str, "UTF-8");
            ArrayList arrayList = new ArrayList();
            int i = 0;
            while (true) {
                int indexOf = encodedQuery.indexOf(38, i);
                int length = indexOf != -1 ? indexOf : encodedQuery.length();
                int indexOf2 = encodedQuery.indexOf(61, i);
                if (indexOf2 > length || indexOf2 == -1) {
                    indexOf2 = length;
                }
                if (indexOf2 - i == encode.length() && encodedQuery.regionMatches(i, encode, 0, encode.length())) {
                    if (indexOf2 == length) {
                        arrayList.add("");
                    } else {
                        arrayList.add(decode(encodedQuery.substring(indexOf2 + 1, length)));
                    }
                }
                if (indexOf == -1) {
                    return Collections.unmodifiableList(arrayList);
                }
                i = indexOf + 1;
            }
        } catch (UnsupportedEncodingException e) {
            throw new AssertionError(e);
        }
    }

    public String getQueryParameter(String str) {
        if (isOpaque()) {
            throw new UnsupportedOperationException(NOT_HIERARCHICAL);
        }
        if (str == null) {
            throw new NullPointerException("key");
        }
        String encodedQuery = getEncodedQuery();
        if (encodedQuery == null) {
            return null;
        }
        String encode = encode(str, null);
        int length = encodedQuery.length();
        int i = 0;
        while (true) {
            int indexOf = encodedQuery.indexOf(38, i);
            int i2 = indexOf != -1 ? indexOf : length;
            int indexOf2 = encodedQuery.indexOf(61, i);
            if (indexOf2 > i2 || indexOf2 == -1) {
                indexOf2 = i2;
            }
            if (indexOf2 - i == encode.length() && encodedQuery.regionMatches(i, encode, 0, encode.length())) {
                if (indexOf2 == i2) {
                    return "";
                }
                return UriCodec.decode(encodedQuery.substring(indexOf2 + 1, i2), true, StandardCharsets.UTF_8, false);
            }
            if (indexOf == -1) {
                return null;
            }
            i = indexOf + 1;
        }
    }

    public boolean getBooleanQueryParameter(String str, boolean z) {
        String queryParameter = getQueryParameter(str);
        if (queryParameter == null) {
            return z;
        }
        String lowerCase = queryParameter.toLowerCase(Locale.ROOT);
        return ("false".equals(lowerCase) || "0".equals(lowerCase)) ? false : true;
    }

    public Uri normalizeScheme() {
        String scheme = getScheme();
        if (scheme != null) {
            String lowerCase = scheme.toLowerCase(Locale.ROOT);
            if (!scheme.equals(lowerCase)) {
                return buildUpon().scheme(lowerCase).build();
            }
        }
        return this;
    }

    public static void writeToParcel(Parcel parcel, Uri uri) {
        if (uri == null) {
            parcel.writeInt(0);
        } else {
            uri.writeToParcel(parcel, 0);
        }
    }

    public static String encode(String str) {
        return encode(str, null);
    }

    public static String encode(String str, String str2) {
        StringBuilder sb = null;
        if (str == null) {
            return null;
        }
        int length = str.length();
        int i = 0;
        while (true) {
            if (i < length) {
                int i2 = i;
                while (i2 < length && isAllowed(str.charAt(i2), str2)) {
                    i2++;
                }
                if (i2 != length) {
                    if (sb == null) {
                        sb = new StringBuilder();
                    }
                    if (i2 > i) {
                        sb.append((CharSequence) str, i, i2);
                    }
                    i = i2 + 1;
                    while (i < length && !isAllowed(str.charAt(i), str2)) {
                        i++;
                    }
                    try {
                        byte[] bytes = str.substring(i2, i).getBytes("UTF-8");
                        int length2 = bytes.length;
                        for (int i3 = 0; i3 < length2; i3++) {
                            sb.append('%');
                            char[] cArr = HEX_DIGITS;
                            sb.append(cArr[(bytes[i3] & 240) >> 4]);
                            sb.append(cArr[bytes[i3] & 15]);
                        }
                    } catch (UnsupportedEncodingException e) {
                        throw new AssertionError(e);
                    }
                } else if (i != 0) {
                    sb.append((CharSequence) str, i, length);
                    return sb.toString();
                }
            } else if (sb != null) {
                return sb.toString();
            }
        }
        return str;
    }

    private static boolean isAllowed(char c, String str) {
        if (c >= 'A' && c <= 'Z') {
            return true;
        }
        if (c >= 'a' && c <= 'z') {
            return true;
        }
        if ((c < '0' || c > '9') && "_-!.~'()*".indexOf(c) == -1) {
            return (str == null || str.indexOf(c) == -1) ? false : true;
        }
        return true;
    }

    public static String encodeIfNotEncoded(String str, String str2) {
        if (str == null) {
            return null;
        }
        return (!Flags.encodeAppIntent() || isEncoded(str, str2)) ? str : encode(str, str2);
    }

    private static boolean isEncoded(String str, String str2) {
        if (str == null) {
            return true;
        }
        for (int i = 0; i < str.length(); i++) {
            char charAt = str.charAt(i);
            if (!isAllowed(charAt, str2) && charAt != '%') {
                return false;
            }
        }
        return true;
    }

    public static String decode(String str) {
        if (str == null) {
            return null;
        }
        return UriCodec.decode(str, false, StandardCharsets.UTF_8, false);
    }

    public static String decodeIfNeeded(String str) {
        if (str == null) {
            return null;
        }
        return (Flags.encodeAppIntent() && str.contains("%")) ? decode(str) : str;
    }

    static abstract class AbstractPart {
        volatile String decoded;
        volatile String encoded;

        abstract String getEncoded();

        AbstractPart(String str, String str2) {
            if (str != NotCachedHolder.NOT_CACHED) {
                this.encoded = str;
                this.decoded = NotCachedHolder.NOT_CACHED;
            } else {
                if (str2 != NotCachedHolder.NOT_CACHED) {
                    this.encoded = NotCachedHolder.NOT_CACHED;
                    this.decoded = str2;
                    return;
                }
                throw new IllegalArgumentException("Neither encoded nor decoded");
            }
        }

        final String getDecoded() {
            if (this.decoded != NotCachedHolder.NOT_CACHED) {
                return this.decoded;
            }
            String decode = Uri.decode(this.encoded);
            this.decoded = decode;
            return decode;
        }
    }

    static class Part extends AbstractPart {
        static final Part NULL = new EmptyPart(null);
        static final Part EMPTY = new EmptyPart("");

        boolean isEmpty() {
            return false;
        }

        private Part(String str, String str2) {
            super(str, str2);
        }

        @Override // android.net.Uri.AbstractPart
        String getEncoded() {
            if (this.encoded != NotCachedHolder.NOT_CACHED) {
                return this.encoded;
            }
            String encode = Uri.encode(this.decoded);
            this.encoded = encode;
            return encode;
        }

        static Part nonNull(Part part) {
            return part == null ? NULL : part;
        }

        static Part fromEncoded(String str) {
            return from(str, NotCachedHolder.NOT_CACHED);
        }

        static Part fromDecoded(String str) {
            return from(NotCachedHolder.NOT_CACHED, str);
        }

        static Part from(String str, String str2) {
            if (str == null) {
                return NULL;
            }
            if (str.length() == 0) {
                return EMPTY;
            }
            if (str2 == null) {
                return NULL;
            }
            if (str2.length() == 0) {
                return EMPTY;
            }
            return new Part(str, str2);
        }

        private static class EmptyPart extends Part {
            @Override // android.net.Uri.Part
            boolean isEmpty() {
                return true;
            }

            public EmptyPart(String str) {
                super(str, str);
                if (str != null && !str.isEmpty()) {
                    throw new IllegalArgumentException("Expected empty value, got: " + str);
                }
                this.decoded = str;
                this.encoded = str;
            }
        }
    }

    static class PathPart extends AbstractPart {
        private PathSegments pathSegments;
        static final PathPart NULL = new PathPart(null, null);
        static final PathPart EMPTY = new PathPart("", "");

        private PathPart(String str, String str2) {
            super(str, str2);
        }

        @Override // android.net.Uri.AbstractPart
        String getEncoded() {
            if (this.encoded != NotCachedHolder.NOT_CACHED) {
                return this.encoded;
            }
            String encode = Uri.encode(this.decoded, "/");
            this.encoded = encode;
            return encode;
        }

        PathSegments getPathSegments() {
            PathSegments pathSegments = this.pathSegments;
            if (pathSegments != null) {
                return pathSegments;
            }
            String encoded = getEncoded();
            if (encoded == null) {
                PathSegments pathSegments2 = PathSegments.EMPTY;
                this.pathSegments = pathSegments2;
                return pathSegments2;
            }
            PathSegmentsBuilder pathSegmentsBuilder = new PathSegmentsBuilder();
            int i = 0;
            while (true) {
                int indexOf = encoded.indexOf(47, i);
                if (indexOf <= -1) {
                    break;
                }
                if (i < indexOf) {
                    pathSegmentsBuilder.add(Uri.decode(encoded.substring(i, indexOf)));
                }
                i = indexOf + 1;
            }
            if (i < encoded.length()) {
                pathSegmentsBuilder.add(Uri.decode(encoded.substring(i)));
            }
            PathSegments build = pathSegmentsBuilder.build();
            this.pathSegments = build;
            return build;
        }

        static PathPart appendEncodedSegment(PathPart pathPart, String str) {
            String str2;
            if (pathPart == null) {
                return fromEncoded("/" + str);
            }
            String encoded = pathPart.getEncoded();
            if (encoded == null) {
                encoded = "";
            }
            int length = encoded.length();
            if (length == 0) {
                str2 = "/" + str;
            } else if (encoded.charAt(length - 1) == '/') {
                str2 = encoded + str;
            } else {
                str2 = encoded + "/" + str;
            }
            return fromEncoded(str2);
        }

        static PathPart appendDecodedSegment(PathPart pathPart, String str) {
            return appendEncodedSegment(pathPart, Uri.encode(str));
        }

        static PathPart fromEncoded(String str) {
            return from(str, NotCachedHolder.NOT_CACHED);
        }

        static PathPart fromDecoded(String str) {
            return from(NotCachedHolder.NOT_CACHED, str);
        }

        static PathPart from(String str, String str2) {
            if (str == null) {
                return NULL;
            }
            if (str.length() == 0) {
                return EMPTY;
            }
            return new PathPart(str, str2);
        }

        static PathPart makeAbsolute(PathPart pathPart) {
            String str;
            String str2;
            boolean z = pathPart.encoded != NotCachedHolder.NOT_CACHED;
            String str3 = z ? pathPart.encoded : pathPart.decoded;
            if (str3 == null || str3.length() == 0 || str3.startsWith("/")) {
                return pathPart;
            }
            if (z) {
                str = "/" + pathPart.encoded;
            } else {
                str = NotCachedHolder.NOT_CACHED;
            }
            if (pathPart.decoded != NotCachedHolder.NOT_CACHED) {
                str2 = "/" + pathPart.decoded;
            } else {
                str2 = NotCachedHolder.NOT_CACHED;
            }
            return new PathPart(str, str2);
        }
    }

    public static Uri withAppendedPath(Uri uri, String str) {
        return uri.buildUpon().appendEncodedPath(str).build();
    }

    public Uri getCanonicalUri() {
        if (!"file".equals(getScheme())) {
            return this;
        }
        try {
            String canonicalPath = new File(getPath()).getCanonicalPath();
            if (Environment.isExternalStorageEmulated()) {
                String file = Environment.getLegacyExternalStorageDirectory().toString();
                if (canonicalPath.startsWith(file)) {
                    return fromFile(new File(Environment.getExternalStorageDirectory().toString(), canonicalPath.substring(file.length() + 1)));
                }
            }
            return fromFile(new File(canonicalPath));
        } catch (IOException unused) {
            return this;
        }
    }

    public void checkFileUriExposed(String str) {
        if (!"file".equals(getScheme()) || getPath() == null || getPath().startsWith("/system/")) {
            return;
        }
        StrictMode.onFileUriExposed(this, str);
    }

    public void checkContentUriWithoutPermission(String str, int i) {
        if (!"content".equals(getScheme()) || Intent.isAccessUriMode(i)) {
            return;
        }
        StrictMode.onContentUriWithoutPermission(this, str);
    }

    public boolean isPathPrefixMatch(Uri uri) {
        if (!Objects.equals(getScheme(), uri.getScheme()) || !Objects.equals(getAuthority(), uri.getAuthority())) {
            return false;
        }
        List<String> pathSegments = getPathSegments();
        List<String> pathSegments2 = uri.getPathSegments();
        int size = pathSegments2.size();
        if (pathSegments.size() < size) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            if (!Objects.equals(pathSegments.get(i), pathSegments2.get(i))) {
                return false;
            }
        }
        return true;
    }
}
