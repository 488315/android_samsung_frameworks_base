package android.content;

import android.net.Uri;
import android.os.Parcel;
import android.util.ArraySet;
import android.util.Log;
import android.util.proto.ProtoOutputStream;
import com.android.internal.util.CollectionUtils;
import com.android.internal.util.XmlUtils;
import java.io.IOException;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlSerializer;

/* loaded from: classes.dex */
public final class UriRelativeFilterGroup {
    public static final int ACTION_ALLOW = 0;
    public static final int ACTION_BLOCK = 1;
    private static final String ALLOW_STR = "allow";
    private static final String TAG = "UriRelativeFilterGroup";
    private static final String URI_RELATIVE_FILTER_GROUP_STR = "uriRelativeFilterGroup";
    private final int mAction;
    private final ArraySet<UriRelativeFilter> mUriRelativeFilters = new ArraySet<>();

    @Retention(RetentionPolicy.SOURCE)
    public @interface Action {
    }

    public static boolean matchGroupsToUri(List<UriRelativeFilterGroup> list, Uri uri) {
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).matchData(uri)) {
                return list.get(i).getAction() == 0;
            }
        }
        return false;
    }

    public static List<UriRelativeFilterGroup> parcelsToGroups(List<UriRelativeFilterGroupParcel> list) {
        ArrayList arrayList = new ArrayList();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                arrayList.add(new UriRelativeFilterGroup(list.get(i)));
            }
        }
        return arrayList;
    }

    public static List<UriRelativeFilterGroupParcel> groupsToParcels(List<UriRelativeFilterGroup> list) {
        ArrayList arrayList = new ArrayList();
        if (list != null) {
            for (int i = 0; i < list.size(); i++) {
                arrayList.add(list.get(i).toParcel());
            }
        }
        return arrayList;
    }

    public UriRelativeFilterGroup(int i) {
        this.mAction = i;
    }

    public UriRelativeFilterGroup(XmlPullParser xmlPullParser) throws XmlPullParserException, IOException {
        this.mAction = Integer.parseInt(xmlPullParser.getAttributeValue(null, ALLOW_STR));
        int depth = xmlPullParser.getDepth();
        while (true) {
            int next = xmlPullParser.next();
            if (next == 1) {
                return;
            }
            if (next == 3 && xmlPullParser.getDepth() <= depth) {
                return;
            }
            if (next != 3 && next != 4) {
                String name = xmlPullParser.getName();
                if (name.equals("uriRelativeFilter")) {
                    addUriRelativeFilter(new UriRelativeFilter(xmlPullParser));
                } else {
                    Log.w("IntentFilter", "Unknown tag parsing IntentFilter: " + name);
                }
                XmlUtils.skipCurrentTag(xmlPullParser);
            }
        }
    }

    public int getAction() {
        return this.mAction;
    }

    public void addUriRelativeFilter(UriRelativeFilter uriRelativeFilter) {
        Objects.requireNonNull(uriRelativeFilter);
        if (CollectionUtils.contains(this.mUriRelativeFilters, uriRelativeFilter)) {
            return;
        }
        this.mUriRelativeFilters.add(uriRelativeFilter);
    }

    public Collection<UriRelativeFilter> getUriRelativeFilters() {
        return Collections.unmodifiableCollection(this.mUriRelativeFilters);
    }

    public boolean matchData(Uri uri) {
        if (this.mUriRelativeFilters.size() == 0) {
            return false;
        }
        Iterator<UriRelativeFilter> it = this.mUriRelativeFilters.iterator();
        while (it.hasNext()) {
            if (!it.next().matchData(uri)) {
                return false;
            }
        }
        return true;
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        protoOutputStream.write(1159641169921L, this.mAction);
        Iterator<UriRelativeFilter> it = this.mUriRelativeFilters.iterator();
        while (it.hasNext()) {
            it.next().dumpDebug(protoOutputStream, 2246267895810L);
        }
        protoOutputStream.end(start);
    }

    public void writeToXml(XmlSerializer xmlSerializer) throws IOException {
        xmlSerializer.startTag(null, URI_RELATIVE_FILTER_GROUP_STR);
        xmlSerializer.attribute(null, ALLOW_STR, Integer.toString(this.mAction));
        Iterator<UriRelativeFilter> it = this.mUriRelativeFilters.iterator();
        while (it.hasNext()) {
            it.next().writeToXml(xmlSerializer);
        }
        xmlSerializer.endTag(null, URI_RELATIVE_FILTER_GROUP_STR);
    }

    public String toString() {
        return "UriRelativeFilterGroup { allow = " + this.mAction + ", uri_filters = " + this.mUriRelativeFilters + ",  }";
    }

    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mAction);
        int size = this.mUriRelativeFilters.size();
        int i2 = 0;
        if (size > 0) {
            parcel.writeInt(size);
            Iterator<UriRelativeFilter> it = this.mUriRelativeFilters.iterator();
            while (it.hasNext()) {
                it.next().writeToParcel(parcel, i);
                i2++;
            }
            if (i2 != size) {
                Log.e(TAG, "UriRelativeFilters was unexpectedly modified while writing to parcel. Expected " + size + " but found " + i2 + " filters", new Exception());
                return;
            }
            return;
        }
        parcel.writeInt(0);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        UriRelativeFilterGroup uriRelativeFilterGroup = (UriRelativeFilterGroup) obj;
        if (this.mAction != uriRelativeFilterGroup.mAction) {
            return false;
        }
        return this.mUriRelativeFilters.equals(uriRelativeFilterGroup.mUriRelativeFilters);
    }

    public int hashCode() {
        return (this.mAction * 31) + Objects.hashCode(this.mUriRelativeFilters);
    }

    public UriRelativeFilterGroupParcel toParcel() {
        UriRelativeFilterGroupParcel uriRelativeFilterGroupParcel = new UriRelativeFilterGroupParcel();
        uriRelativeFilterGroupParcel.action = this.mAction;
        uriRelativeFilterGroupParcel.filters = new ArrayList();
        Iterator<UriRelativeFilter> it = this.mUriRelativeFilters.iterator();
        while (it.hasNext()) {
            uriRelativeFilterGroupParcel.filters.add(it.next().toParcel());
        }
        return uriRelativeFilterGroupParcel;
    }

    UriRelativeFilterGroup(Parcel parcel) {
        this.mAction = parcel.readInt();
        int readInt = parcel.readInt();
        for (int i = 0; i < readInt; i++) {
            this.mUriRelativeFilters.add(new UriRelativeFilter(parcel));
        }
    }

    public UriRelativeFilterGroup(UriRelativeFilterGroupParcel uriRelativeFilterGroupParcel) {
        this.mAction = uriRelativeFilterGroupParcel.action;
        for (int i = 0; i < uriRelativeFilterGroupParcel.filters.size(); i++) {
            this.mUriRelativeFilters.add(new UriRelativeFilter(uriRelativeFilterGroupParcel.filters.get(i)));
        }
    }
}
