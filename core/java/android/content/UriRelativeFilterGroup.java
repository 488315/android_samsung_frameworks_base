package android.content;

import android.net.Uri;
import android.os.Parcel;
import android.util.ArraySet;
import android.util.Log;
import android.util.proto.ProtoOutputStream;

import com.android.internal.util.CollectionUtils;
import com.android.internal.util.XmlUtils;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

public final class UriRelativeFilterGroup {
    public static final int ACTION_ALLOW = 0;
    public static final int ACTION_BLOCK = 1;
    private static final String ALLOW_STR = "allow";
    private static final String URI_RELATIVE_FILTER_GROUP_STR = "uriRelativeFilterGroup";
    private final int mAction;
    private final ArraySet<UriRelativeFilter> mUriRelativeFilters = new ArraySet<>();

    @Retention(RetentionPolicy.SOURCE)
    public @interface Action {}

    public static boolean matchGroupsToUri(List<UriRelativeFilterGroup> groups, Uri uri) {
        for (int i = 0; i < groups.size(); i++) {
            if (groups.get(i).matchData(uri)) {
                return groups.get(i).getAction() == 0;
            }
        }
        return false;
    }

    public static List<UriRelativeFilterGroup> parcelsToGroups(
            List<UriRelativeFilterGroupParcel> parcels) {
        List<UriRelativeFilterGroup> groups = new ArrayList<>();
        if (parcels != null) {
            for (int i = 0; i < parcels.size(); i++) {
                groups.add(new UriRelativeFilterGroup(parcels.get(i)));
            }
        }
        return groups;
    }

    public static List<UriRelativeFilterGroupParcel> groupsToParcels(
            List<UriRelativeFilterGroup> groups) {
        List<UriRelativeFilterGroupParcel> parcels = new ArrayList<>();
        if (groups != null) {
            for (int i = 0; i < groups.size(); i++) {
                parcels.add(groups.get(i).toParcel());
            }
        }
        return parcels;
    }

    public UriRelativeFilterGroup(int action) {
        this.mAction = action;
    }

    public UriRelativeFilterGroup(XmlPullParser parser) throws XmlPullParserException, IOException {
        this.mAction = Integer.parseInt(parser.getAttributeValue(null, ALLOW_STR));
        int outerDepth = parser.getDepth();
        while (true) {
            int type = parser.next();
            if (type != 1) {
                if (type != 3 || parser.getDepth() > outerDepth) {
                    if (type != 3 && type != 4) {
                        String tagName = parser.getName();
                        if (tagName.equals("uriRelativeFilter")) {
                            addUriRelativeFilter(new UriRelativeFilter(parser));
                        } else {
                            Log.w("IntentFilter", "Unknown tag parsing IntentFilter: " + tagName);
                        }
                        XmlUtils.skipCurrentTag(parser);
                    }
                } else {
                    return;
                }
            } else {
                return;
            }
        }
    }

    public int getAction() {
        return this.mAction;
    }

    public void addUriRelativeFilter(UriRelativeFilter uriRelativeFilter) {
        Objects.requireNonNull(uriRelativeFilter);
        if (!CollectionUtils.contains(this.mUriRelativeFilters, uriRelativeFilter)) {
            this.mUriRelativeFilters.add(uriRelativeFilter);
        }
    }

    public Collection<UriRelativeFilter> getUriRelativeFilters() {
        return Collections.unmodifiableCollection(this.mUriRelativeFilters);
    }

    public boolean matchData(Uri data) {
        if (this.mUriRelativeFilters.size() == 0) {
            return false;
        }
        Iterator<UriRelativeFilter> it = this.mUriRelativeFilters.iterator();
        while (it.hasNext()) {
            UriRelativeFilter filter = it.next();
            if (!filter.matchData(data)) {
                return false;
            }
        }
        return true;
    }

    public void dumpDebug(ProtoOutputStream proto, long fieldId) {
        long token = proto.start(fieldId);
        proto.write(1159641169921L, this.mAction);
        Iterator<UriRelativeFilter> it = this.mUriRelativeFilters.iterator();
        while (it.hasNext()) {
            it.next().dumpDebug(proto, 2246267895810L);
        }
        proto.end(token);
    }

    public void writeToXml(XmlSerializer serializer) throws IOException {
        serializer.startTag(null, URI_RELATIVE_FILTER_GROUP_STR);
        serializer.attribute(null, ALLOW_STR, Integer.toString(this.mAction));
        Iterator<UriRelativeFilter> it = this.mUriRelativeFilters.iterator();
        while (it.hasNext()) {
            UriRelativeFilter filter = it.next();
            filter.writeToXml(serializer);
        }
        serializer.endTag(null, URI_RELATIVE_FILTER_GROUP_STR);
    }

    public String toString() {
        return "UriRelativeFilterGroup { allow = "
                + this.mAction
                + ", uri_filters = "
                + this.mUriRelativeFilters
                + ",  }";
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mAction);
        int n = this.mUriRelativeFilters.size();
        if (n > 0) {
            dest.writeInt(n);
            Iterator<UriRelativeFilter> it = this.mUriRelativeFilters.iterator();
            while (it.hasNext()) {
                it.next().writeToParcel(dest, flags);
            }
            return;
        }
        dest.writeInt(0);
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        UriRelativeFilterGroup that = (UriRelativeFilterGroup) o;
        if (this.mAction != that.mAction) {
            return false;
        }
        return this.mUriRelativeFilters.equals(that.mUriRelativeFilters);
    }

    public int hashCode() {
        int _hash = (0 * 31) + this.mAction;
        return (_hash * 31) + Objects.hashCode(this.mUriRelativeFilters);
    }

    public UriRelativeFilterGroupParcel toParcel() {
        UriRelativeFilterGroupParcel parcel = new UriRelativeFilterGroupParcel();
        parcel.action = this.mAction;
        parcel.filters = new ArrayList();
        Iterator<UriRelativeFilter> it = this.mUriRelativeFilters.iterator();
        while (it.hasNext()) {
            UriRelativeFilter filter = it.next();
            parcel.filters.add(filter.toParcel());
        }
        return parcel;
    }

    UriRelativeFilterGroup(Parcel src) {
        this.mAction = src.readInt();
        int n = src.readInt();
        for (int i = 0; i < n; i++) {
            this.mUriRelativeFilters.add(new UriRelativeFilter(src));
        }
    }

    public UriRelativeFilterGroup(UriRelativeFilterGroupParcel parcel) {
        this.mAction = parcel.action;
        for (int i = 0; i < parcel.filters.size(); i++) {
            this.mUriRelativeFilters.add(new UriRelativeFilter(parcel.filters.get(i)));
        }
    }
}
