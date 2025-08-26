package com.samsung.android.sdk.moneta.memory.option.wrapper.v1.query;

import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import com.samsung.android.sdk.moneta.basicdomain.entity.Person$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.moneta.memory.entity.activity.ActivityType;
import com.samsung.android.sdk.moneta.memory.option.EngramQueryOption;
import com.samsung.android.sdk.moneta.memory.option.EngramQueryType;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class EngramQueryOptionWrapperV1 implements Parcelable {
    private final boolean contentFill;
    private final String contentId;
    private final Long endTimestamp;
    private final String engramId;
    private final List<Integer> excludeActivityTypes;
    private final List<String> excludeTags;
    private final List<Integer> includeActivityTypes;
    private final List<String> includeTags;
    private final Double latitude;
    private final int limit;
    private final Double longitude;
    private final int offset;
    private final int queryType;
    private final Double radius;
    private final Long startTimestamp;
    public static final Companion Companion = new Companion(null);
    public static final Parcelable.Creator<EngramQueryOptionWrapperV1> CREATOR = new Creator();

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            ArrayList arrayList;
            ArrayList arrayList2;
            Long lValueOf = parcel.readInt() == 0 ? null : Long.valueOf(parcel.readLong());
            Long lValueOf2 = parcel.readInt() == 0 ? null : Long.valueOf(parcel.readLong());
            String string = parcel.readString();
            int i = parcel.readInt();
            ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
            ArrayList<String> arrayListCreateStringArrayList2 = parcel.createStringArrayList();
            if (parcel.readInt() == 0) {
                arrayList = null;
            } else {
                int i2 = parcel.readInt();
                arrayList = new ArrayList(i2);
                for (int i3 = 0; i3 != i2; i3++) {
                    arrayList.add(Integer.valueOf(parcel.readInt()));
                }
            }
            if (parcel.readInt() == 0) {
                arrayList2 = null;
            } else {
                int i4 = parcel.readInt();
                arrayList2 = new ArrayList(i4);
                for (int i5 = 0; i5 != i4; i5++) {
                    arrayList2.add(Integer.valueOf(parcel.readInt()));
                }
            }
            return new EngramQueryOptionWrapperV1(lValueOf, lValueOf2, string, i, arrayListCreateStringArrayList, arrayListCreateStringArrayList2, arrayList, arrayList2, parcel.readInt(), parcel.readInt(), parcel.readString(), parcel.readInt() == 0 ? null : Double.valueOf(parcel.readDouble()), parcel.readInt() == 0 ? null : Double.valueOf(parcel.readDouble()), parcel.readInt() != 0 ? Double.valueOf(parcel.readDouble()) : null, parcel.readInt() != 0);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new EngramQueryOptionWrapperV1[i];
        }
    }

    public EngramQueryOptionWrapperV1() {
        this(null, null, null, 0, null, null, null, null, 0, 0, null, null, null, null, false, 32767, null);
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

    public final List<Integer> getExcludeActivityTypes() {
        return this.excludeActivityTypes;
    }

    public final List<String> getExcludeTags() {
        return this.excludeTags;
    }

    public final List<Integer> getIncludeActivityTypes() {
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

    public final int getQueryType() {
        return this.queryType;
    }

    public final Double getRadius() {
        return this.radius;
    }

    public final Long getStartTimestamp() {
        return this.startTimestamp;
    }

    public final EngramQueryOption toOption() {
        ArrayList arrayList;
        Object next;
        ArrayList arrayList2;
        Long l = this.startTimestamp;
        Long l2 = this.endTimestamp;
        String str = this.contentId;
        EngramQueryType.Companion companion = EngramQueryType.Companion;
        int i = this.queryType;
        companion.getClass();
        Iterator<E> it = EngramQueryType.getEntries().iterator();
        while (true) {
            arrayList = null;
            if (!it.hasNext()) {
                next = null;
                break;
            }
            next = it.next();
            if (((EngramQueryType) next).getValue() == i) {
                break;
            }
        }
        EngramQueryType engramQueryType = (EngramQueryType) next;
        if (engramQueryType == null) {
            engramQueryType = EngramQueryType.BETWEEN_TIMESTAMP;
        }
        EngramQueryType engramQueryType2 = engramQueryType;
        List<String> list = this.includeTags;
        List<String> list2 = this.excludeTags;
        List<Integer> list3 = this.includeActivityTypes;
        if (list3 != null) {
            List<Integer> list4 = list3;
            arrayList2 = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list4, 10));
            Iterator<T> it2 = list4.iterator();
            while (it2.hasNext()) {
                int iIntValue = ((Number) it2.next()).intValue();
                ActivityType.Companion companion2 = ActivityType.Companion;
                Integer numValueOf = Integer.valueOf(iIntValue);
                companion2.getClass();
                ActivityType activityTypeFromInt = ActivityType.Companion.fromInt(numValueOf);
                activityTypeFromInt.getClass();
                arrayList2.add(activityTypeFromInt);
            }
        } else {
            arrayList2 = null;
        }
        List<Integer> list5 = this.excludeActivityTypes;
        if (list5 != null) {
            List<Integer> list6 = list5;
            arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list6, 10));
            Iterator<T> it3 = list6.iterator();
            while (it3.hasNext()) {
                int iIntValue2 = ((Number) it3.next()).intValue();
                ActivityType.Companion companion3 = ActivityType.Companion;
                Integer numValueOf2 = Integer.valueOf(iIntValue2);
                companion3.getClass();
                ActivityType activityTypeFromInt2 = ActivityType.Companion.fromInt(numValueOf2);
                activityTypeFromInt2.getClass();
                arrayList.add(activityTypeFromInt2);
            }
        }
        EngramQueryOption.WrapBuilder wrapBuilder = new EngramQueryOption.WrapBuilder(l, l2, str, engramQueryType2, list, list2, arrayList2, arrayList, this.limit, this.offset, this.engramId, this.latitude, this.longitude, this.radius, this.contentFill);
        return new EngramQueryOption(wrapBuilder.startTimestamp, wrapBuilder.endTimestamp, wrapBuilder.contentId, wrapBuilder.queryType, wrapBuilder.includeTags, wrapBuilder.excludeTags, wrapBuilder.includeActivityTypes, wrapBuilder.excludeActivityTypes, wrapBuilder.limit, wrapBuilder.offset, wrapBuilder.engramId, wrapBuilder.latitude, wrapBuilder.longitude, wrapBuilder.radius, wrapBuilder.contentFill, null);
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
        parcel.writeInt(this.queryType);
        parcel.writeStringList(this.includeTags);
        parcel.writeStringList(this.excludeTags);
        List<Integer> list = this.includeActivityTypes;
        if (list == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            parcel.writeInt(list.size());
            Iterator<Integer> it = list.iterator();
            while (it.hasNext()) {
                parcel.writeInt(it.next().intValue());
            }
        }
        List<Integer> list2 = this.excludeActivityTypes;
        if (list2 == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            parcel.writeInt(list2.size());
            Iterator<Integer> it2 = list2.iterator();
            while (it2.hasNext()) {
                parcel.writeInt(it2.next().intValue());
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

    public EngramQueryOptionWrapperV1(Long l, Long l2, String str, int i, List<String> list, List<String> list2, List<Integer> list3, List<Integer> list4, int i2, int i3, String str2, Double d, Double d2, Double d3, boolean z) {
        this.startTimestamp = l;
        this.endTimestamp = l2;
        this.contentId = str;
        this.queryType = i;
        this.includeTags = list;
        this.excludeTags = list2;
        this.includeActivityTypes = list3;
        this.excludeActivityTypes = list4;
        this.limit = i2;
        this.offset = i3;
        this.engramId = str2;
        this.latitude = d;
        this.longitude = d2;
        this.radius = d3;
        this.contentFill = z;
    }

    public /* synthetic */ EngramQueryOptionWrapperV1(Long l, Long l2, String str, int i, List list, List list2, List list3, List list4, int i2, int i3, String str2, Double d, Double d2, Double d3, boolean z, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        this((i4 & 1) != 0 ? null : l, (i4 & 2) != 0 ? null : l2, (i4 & 4) != 0 ? null : str, (i4 & 8) != 0 ? EngramQueryType.BETWEEN_TIMESTAMP.getValue() : i, (i4 & 16) != 0 ? null : list, (i4 & 32) != 0 ? null : list2, (i4 & 64) != 0 ? null : list3, (i4 & 128) != 0 ? null : list4, (i4 & 256) != 0 ? 20 : i2, (i4 & 512) != 0 ? 0 : i3, (i4 & 1024) != 0 ? null : str2, (i4 & 2048) != 0 ? null : d, (i4 & 4096) != 0 ? null : d2, (i4 & 8192) != 0 ? null : d3, (i4 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0 ? false : z);
    }
}
