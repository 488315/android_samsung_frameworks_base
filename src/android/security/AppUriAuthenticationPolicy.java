package android.security;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes3.dex */
public final class AppUriAuthenticationPolicy implements Parcelable {
    public static final Parcelable.Creator<AppUriAuthenticationPolicy> CREATOR = new Parcelable.Creator<AppUriAuthenticationPolicy>() { // from class: android.security.AppUriAuthenticationPolicy.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AppUriAuthenticationPolicy createFromParcel(Parcel parcel) throws ClassNotFoundException, IOException {
            HashMap map = new HashMap();
            parcel.readMap(map, UrisToAliases.class.getClassLoader());
            return new AppUriAuthenticationPolicy(map);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AppUriAuthenticationPolicy[] newArray(int i) {
            return new AppUriAuthenticationPolicy[i];
        }
    };
    private static final String KEY_AUTHENTICATION_POLICY_APP = "policy_app";
    private static final String KEY_AUTHENTICATION_POLICY_APP_TO_URIS = "authentication_policy_app_to_uris";
    private final Map<String, UrisToAliases> mAppToUris;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private AppUriAuthenticationPolicy(Map<String, UrisToAliases> map) {
        Objects.requireNonNull(map);
        this.mAppToUris = map;
    }

    public static final class Builder {
        private Map<String, UrisToAliases> mPackageNameToUris = new HashMap();

        public Builder addAppAndUriMapping(String str, Uri uri, String str2) {
            Objects.requireNonNull(str);
            Objects.requireNonNull(uri);
            Objects.requireNonNull(str2);
            UrisToAliases orDefault = this.mPackageNameToUris.getOrDefault(str, new UrisToAliases());
            orDefault.addUriToAlias(uri, str2);
            this.mPackageNameToUris.put(str, orDefault);
            return this;
        }

        public Builder addAppAndUriMapping(String str, UrisToAliases urisToAliases) {
            Objects.requireNonNull(str);
            Objects.requireNonNull(urisToAliases);
            this.mPackageNameToUris.put(str, urisToAliases);
            return this;
        }

        public AppUriAuthenticationPolicy build() {
            return new AppUriAuthenticationPolicy(this.mPackageNameToUris);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeMap(this.mAppToUris);
    }

    public String toString() {
        return "AppUriAuthenticationPolicy{mPackageNameToUris=" + this.mAppToUris + '}';
    }

    public Map<String, Map<Uri, String>> getAppAndUriMappings() {
        HashMap map = new HashMap();
        for (Map.Entry<String, UrisToAliases> entry : this.mAppToUris.entrySet()) {
            map.put(entry.getKey(), entry.getValue().getUrisToAliases());
        }
        return map;
    }

    public static AppUriAuthenticationPolicy readFromXml(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        Builder builder = new Builder();
        int depth = xmlPullParser.getDepth();
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1 || (next == 3 && xmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4 && xmlPullParser.getName().equals(KEY_AUTHENTICATION_POLICY_APP_TO_URIS)) {
                builder.addAppAndUriMapping(xmlPullParser.getAttributeValue(null, KEY_AUTHENTICATION_POLICY_APP), UrisToAliases.readFromXml(xmlPullParser));
            }
        }
        return builder.build();
    }

    public void writeToXml(XmlSerializer xmlSerializer) throws IllegalStateException, IOException, IllegalArgumentException {
        for (Map.Entry<String, UrisToAliases> entry : this.mAppToUris.entrySet()) {
            xmlSerializer.startTag(null, KEY_AUTHENTICATION_POLICY_APP_TO_URIS);
            xmlSerializer.attribute(null, KEY_AUTHENTICATION_POLICY_APP, entry.getKey());
            entry.getValue().writeToXml(xmlSerializer);
            xmlSerializer.endTag(null, KEY_AUTHENTICATION_POLICY_APP_TO_URIS);
        }
    }

    public Set<String> getAliases() {
        HashSet hashSet = new HashSet();
        Iterator<UrisToAliases> it = this.mAppToUris.values().iterator();
        while (it.hasNext()) {
            hashSet.addAll(it.next().getUrisToAliases().values());
        }
        return hashSet;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof AppUriAuthenticationPolicy) {
            return Objects.equals(this.mAppToUris, ((AppUriAuthenticationPolicy) obj).mAppToUris);
        }
        return false;
    }

    public int hashCode() {
        return this.mAppToUris.hashCode();
    }
}
