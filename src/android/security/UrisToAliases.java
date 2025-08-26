package android.security;

import android.net.Uri;
import android.os.Parcel;
import android.os.Parcelable;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes3.dex */
public final class UrisToAliases implements Parcelable {
    public static final Parcelable.Creator<UrisToAliases> CREATOR = new Parcelable.Creator<UrisToAliases>() { // from class: android.security.UrisToAliases.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UrisToAliases createFromParcel(Parcel parcel) throws ClassNotFoundException, IOException {
            HashMap map = new HashMap();
            parcel.readMap(map, String.class.getClassLoader());
            return new UrisToAliases(map);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public UrisToAliases[] newArray(int i) {
            return new UrisToAliases[i];
        }
    };
    private static final String KEY_AUTHENTICATION_POLICY_ALIAS = "policy_alias";
    private static final String KEY_AUTHENTICATION_POLICY_URI = "policy_uri";
    private static final String KEY_AUTHENTICATION_POLICY_URI_TO_ALIAS = "authentication_policy_uri_to_alias";
    private final Map<Uri, String> mUrisToAliases;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public UrisToAliases() {
        this.mUrisToAliases = new HashMap();
    }

    private UrisToAliases(Map<Uri, String> map) {
        this.mUrisToAliases = map;
    }

    public Map<Uri, String> getUrisToAliases() {
        return Collections.unmodifiableMap(this.mUrisToAliases);
    }

    public void addUriToAlias(Uri uri, String str) {
        this.mUrisToAliases.put(uri, str);
    }

    public static UrisToAliases readFromXml(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        HashMap map = new HashMap();
        int depth = xmlPullParser.getDepth();
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1 || (next == 3 && xmlPullParser.getDepth() <= depth)) {
                break;
            }
            if (next != 3 && next != 4 && xmlPullParser.getName().equals(KEY_AUTHENTICATION_POLICY_URI_TO_ALIAS)) {
                map.put(Uri.parse(xmlPullParser.getAttributeValue(null, KEY_AUTHENTICATION_POLICY_URI)), xmlPullParser.getAttributeValue(null, KEY_AUTHENTICATION_POLICY_ALIAS));
            }
        }
        return new UrisToAliases(map);
    }

    public void writeToXml(XmlSerializer xmlSerializer) throws IllegalStateException, IOException, IllegalArgumentException {
        for (Map.Entry<Uri, String> entry : this.mUrisToAliases.entrySet()) {
            xmlSerializer.startTag(null, KEY_AUTHENTICATION_POLICY_URI_TO_ALIAS);
            xmlSerializer.attribute(null, KEY_AUTHENTICATION_POLICY_URI, entry.getKey().toString());
            xmlSerializer.attribute(null, KEY_AUTHENTICATION_POLICY_ALIAS, entry.getValue());
            xmlSerializer.endTag(null, KEY_AUTHENTICATION_POLICY_URI_TO_ALIAS);
        }
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeMap(this.mUrisToAliases);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof UrisToAliases) {
            return Objects.equals(this.mUrisToAliases, ((UrisToAliases) obj).mUrisToAliases);
        }
        return false;
    }

    public int hashCode() {
        return this.mUrisToAliases.hashCode();
    }
}
