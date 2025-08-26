package com.samsung.android.sdk.moneta.memory.option;

import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.sdk.moneta.basicdomain.entity.Person$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.moneta.memory.entity.activity.ActivityType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class EngramQueryOption implements Parcelable {
    public static final Parcelable.Creator<EngramQueryOption> CREATOR = new Creator();
    private final boolean contentFill;
    private final String contentId;
    private final Long endTimestamp;
    private final String engramId;
    private final List<ActivityType> excludeActivityTypes;
    private final List<String> excludeTags;
    private final List<ActivityType> includeActivityTypes;
    private final List<String> includeTags;
    private final Double latitude;
    private final int limit;
    private final Double longitude;
    private final int offset;
    private final EngramQueryType queryType;
    private final Double radius;
    private final Long startTimestamp;

    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            ArrayList arrayList;
            ArrayList arrayList2;
            Long lValueOf = parcel.readInt() == 0 ? null : Long.valueOf(parcel.readLong());
            Long lValueOf2 = parcel.readInt() == 0 ? null : Long.valueOf(parcel.readLong());
            String string = parcel.readString();
            EngramQueryType engramQueryTypeValueOf = EngramQueryType.valueOf(parcel.readString());
            ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
            ArrayList<String> arrayListCreateStringArrayList2 = parcel.createStringArrayList();
            if (parcel.readInt() == 0) {
                arrayList = null;
            } else {
                int i = parcel.readInt();
                arrayList = new ArrayList(i);
                for (int i2 = 0; i2 != i; i2++) {
                    arrayList.add(ActivityType.valueOf(parcel.readString()));
                }
            }
            if (parcel.readInt() == 0) {
                arrayList2 = null;
            } else {
                int i3 = parcel.readInt();
                arrayList2 = new ArrayList(i3);
                for (int i4 = 0; i4 != i3; i4++) {
                    arrayList2.add(ActivityType.valueOf(parcel.readString()));
                }
            }
            return new EngramQueryOption(lValueOf, lValueOf2, string, engramQueryTypeValueOf, arrayListCreateStringArrayList, arrayListCreateStringArrayList2, arrayList, arrayList2, parcel.readInt(), parcel.readInt(), parcel.readString(), parcel.readInt() == 0 ? null : Double.valueOf(parcel.readDouble()), parcel.readInt() == 0 ? null : Double.valueOf(parcel.readDouble()), parcel.readInt() != 0 ? Double.valueOf(parcel.readDouble()) : null, parcel.readInt() != 0, null);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new EngramQueryOption[i];
        }
    }

    public final class WrapBuilder {
        public final boolean contentFill;
        public final String contentId;
        public final Long endTimestamp;
        public final String engramId;
        public final List excludeActivityTypes;
        public final List excludeTags;
        public final List includeActivityTypes;
        public final List includeTags;
        public final Double latitude;
        public final int limit;
        public final Double longitude;
        public final int offset;
        public final EngramQueryType queryType;
        public final Double radius;
        public final Long startTimestamp;

        public WrapBuilder() {
            this(null, null, null, null, null, null, null, null, 0, 0, null, null, null, null, false, 32767, null);
        }

        public WrapBuilder(Long l, Long l2, String str, EngramQueryType engramQueryType, List<String> list, List<String> list2, List<? extends ActivityType> list3, List<? extends ActivityType> list4, int i, int i2, String str2, Double d, Double d2, Double d3, boolean z) {
            this.startTimestamp = l;
            this.endTimestamp = l2;
            this.contentId = str;
            this.queryType = engramQueryType;
            this.includeTags = list;
            this.excludeTags = list2;
            this.includeActivityTypes = list3;
            this.excludeActivityTypes = list4;
            this.limit = i;
            this.offset = i2;
            this.engramId = str2;
            this.latitude = d;
            this.longitude = d2;
            this.radius = d3;
            this.contentFill = z;
        }

        public /* synthetic */ WrapBuilder(Long l, Long l2, String str, EngramQueryType engramQueryType, List list, List list2, List list3, List list4, int i, int i2, String str2, Double d, Double d2, Double d3, boolean z, int i3, DefaultConstructorMarker defaultConstructorMarker) {
            this((i3 & 1) != 0 ? null : l, (i3 & 2) != 0 ? null : l2, (i3 & 4) != 0 ? null : str, (i3 & 8) != 0 ? EngramQueryType.BETWEEN_TIMESTAMP : engramQueryType, (i3 & 16) != 0 ? null : list, (i3 & 32) != 0 ? null : list2, (i3 & 64) != 0 ? null : list3, (i3 & 128) != 0 ? null : list4, (i3 & 256) != 0 ? 20 : i, (i3 & 512) != 0 ? 0 : i2, (i3 & 1024) != 0 ? null : str2, (i3 & 2048) != 0 ? null : d, (i3 & 4096) != 0 ? null : d2, (i3 & 8192) != 0 ? null : d3, (i3 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0 ? false : z);
        }
    }

    public /* synthetic */ EngramQueryOption(Long l, Long l2, String str, EngramQueryType engramQueryType, List list, List list2, List list3, List list4, int i, int i2, String str2, Double d, Double d2, Double d3, boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(l, l2, str, engramQueryType, list, list2, list3, list4, i, i2, str2, d, d2, d3, z);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final boolean getContentFill() {
        return this.contentFill;
    }

    public final String getContentId() {
        return this.contentId;
    }

    public final Long getEndTimestamp() {
        return this.endTimestamp;
    }

    public final String getEngramId() {
        return this.engramId;
    }

    public final List<ActivityType> getExcludeActivityTypes() {
        return this.excludeActivityTypes;
    }

    public final List<String> getExcludeTags() {
        return this.excludeTags;
    }

    public final List<ActivityType> getIncludeActivityTypes() {
        return this.includeActivityTypes;
    }

    public final List<String> getIncludeTags() {
        return this.includeTags;
    }

    public final Double getLatitude() {
        return this.latitude;
    }

    public final int getLimit() {
        return this.limit;
    }

    public final Double getLongitude() {
        return this.longitude;
    }

    public final int getOffset() {
        return this.offset;
    }

    public final EngramQueryType getQueryType() {
        return this.queryType;
    }

    public final Double getRadius() {
        return this.radius;
    }

    public final Long getStartTimestamp() {
        return this.startTimestamp;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        Long l = this.startTimestamp;
        if (l == null) {
            parcel.writeInt(0);
        } else {
            Person$$ExternalSyntheticOutline0.m(parcel, 1, l);
        }
        Long l2 = this.endTimestamp;
        if (l2 == null) {
            parcel.writeInt(0);
        } else {
            Person$$ExternalSyntheticOutline0.m(parcel, 1, l2);
        }
        parcel.writeString(this.contentId);
        parcel.writeString(this.queryType.name());
        parcel.writeStringList(this.includeTags);
        parcel.writeStringList(this.excludeTags);
        List<ActivityType> list = this.includeActivityTypes;
        if (list == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            parcel.writeInt(list.size());
            Iterator<ActivityType> it = list.iterator();
            while (it.hasNext()) {
                parcel.writeString(it.next().name());
            }
        }
        List<ActivityType> list2 = this.excludeActivityTypes;
        if (list2 == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            parcel.writeInt(list2.size());
            Iterator<ActivityType> it2 = list2.iterator();
            while (it2.hasNext()) {
                parcel.writeString(it2.next().name());
            }
        }
        parcel.writeInt(this.limit);
        parcel.writeInt(this.offset);
        parcel.writeString(this.engramId);
        Double d = this.latitude;
        if (d == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            parcel.writeDouble(d.doubleValue());
        }
        Double d2 = this.longitude;
        if (d2 == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            parcel.writeDouble(d2.doubleValue());
        }
        Double d3 = this.radius;
        if (d3 == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            parcel.writeDouble(d3.doubleValue());
        }
        parcel.writeInt(this.contentFill ? 1 : 0);
    }

    /* JADX WARN: Multi-variable type inference failed */
    private EngramQueryOption(Long l, Long l2, String str, EngramQueryType engramQueryType, List<String> list, List<String> list2, List<? extends ActivityType> list3, List<? extends ActivityType> list4, int i, int i2, String str2, Double d, Double d2, Double d3, boolean z) {
        this.startTimestamp = l;
        this.endTimestamp = l2;
        this.contentId = str;
        this.queryType = engramQueryType;
        this.includeTags = list;
        this.excludeTags = list2;
        this.includeActivityTypes = list3;
        this.excludeActivityTypes = list4;
        this.limit = i;
        this.offset = i2;
        this.engramId = str2;
        this.latitude = d;
        this.longitude = d2;
        this.radius = d3;
        this.contentFill = z;
    }

    public /* synthetic */ EngramQueryOption(Long l, Long l2, String str, EngramQueryType engramQueryType, List list, List list2, List list3, List list4, int i, int i2, String str2, Double d, Double d2, Double d3, boolean z, int i3, DefaultConstructorMarker defaultConstructorMarker) {
        this((i3 & 1) != 0 ? null : l, (i3 & 2) != 0 ? null : l2, (i3 & 4) != 0 ? null : str, (i3 & 8) != 0 ? EngramQueryType.BETWEEN_TIMESTAMP : engramQueryType, (i3 & 16) != 0 ? null : list, (i3 & 32) != 0 ? null : list2, (i3 & 64) != 0 ? null : list3, (i3 & 128) != 0 ? null : list4, (i3 & 256) != 0 ? 20 : i, (i3 & 512) != 0 ? 0 : i2, (i3 & 1024) != 0 ? null : str2, (i3 & 2048) != 0 ? null : d, (i3 & 4096) != 0 ? null : d2, (i3 & 8192) != 0 ? null : d3, (i3 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0 ? false : z);
    }
}
