package com.samsung.android.sdk.moneta.memory.entity.content;

import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.knox.container.KnoxConfigurationType$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.moneta.basicdomain.entity.Person$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class GraphPathNode implements Parcelable {
    public static final Parcelable.Creator<GraphPathNode> CREATOR = new Creator();
    private final Long endTimestamp;
    private final String iri;
    private final String literal;
    private final String name;
    private final List<GraphPathEdge> outboundEdges;
    private final Long startTimestamp;
    private final String type;
    private final String typeDescription;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            String readString = parcel.readString();
            String readString2 = parcel.readString();
            String readString3 = parcel.readString();
            String readString4 = parcel.readString();
            String readString5 = parcel.readString();
            Long valueOf = parcel.readInt() == 0 ? null : Long.valueOf(parcel.readLong());
            Long valueOf2 = parcel.readInt() != 0 ? Long.valueOf(parcel.readLong()) : null;
            int readInt = parcel.readInt();
            ArrayList arrayList = new ArrayList(readInt);
            for (int i = 0; i != readInt; i++) {
                arrayList.add(GraphPathEdge.CREATOR.createFromParcel(parcel));
            }
            return new GraphPathNode(readString, readString2, readString3, readString4, readString5, valueOf, valueOf2, arrayList);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new GraphPathNode[i];
        }
    }

    public GraphPathNode(String str, String str2, String str3, String str4, String str5, Long l, Long l2, List<GraphPathEdge> list) {
        this.iri = str;
        this.literal = str2;
        this.name = str3;
        this.type = str4;
        this.typeDescription = str5;
        this.startTimestamp = l;
        this.endTimestamp = l2;
        this.outboundEdges = list;
    }

    public static /* synthetic */ GraphPathNode copy$default(GraphPathNode graphPathNode, String str, String str2, String str3, String str4, String str5, Long l, Long l2, List list, int i, Object obj) {
        if ((i & 1) != 0) {
            str = graphPathNode.iri;
        }
        if ((i & 2) != 0) {
            str2 = graphPathNode.literal;
        }
        if ((i & 4) != 0) {
            str3 = graphPathNode.name;
        }
        if ((i & 8) != 0) {
            str4 = graphPathNode.type;
        }
        if ((i & 16) != 0) {
            str5 = graphPathNode.typeDescription;
        }
        if ((i & 32) != 0) {
            l = graphPathNode.startTimestamp;
        }
        if ((i & 64) != 0) {
            l2 = graphPathNode.endTimestamp;
        }
        if ((i & 128) != 0) {
            list = graphPathNode.outboundEdges;
        }
        Long l3 = l2;
        List list2 = list;
        String str6 = str5;
        Long l4 = l;
        return graphPathNode.copy(str, str2, str3, str4, str6, l4, l3, list2);
    }

    public final String component1() {
        return this.iri;
    }

    public final String component2() {
        return this.literal;
    }

    public final String component3() {
        return this.name;
    }

    public final String component4() {
        return this.type;
    }

    public final String component5() {
        return this.typeDescription;
    }

    public final Long component6() {
        return this.startTimestamp;
    }

    public final Long component7() {
        return this.endTimestamp;
    }

    public final List<GraphPathEdge> component8() {
        return this.outboundEdges;
    }

    public final GraphPathNode copy(String str, String str2, String str3, String str4, String str5, Long l, Long l2, List<GraphPathEdge> list) {
        return new GraphPathNode(str, str2, str3, str4, str5, l, l2, list);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GraphPathNode)) {
            return false;
        }
        GraphPathNode graphPathNode = (GraphPathNode) obj;
        return Intrinsics.areEqual(this.iri, graphPathNode.iri) && Intrinsics.areEqual(this.literal, graphPathNode.literal) && Intrinsics.areEqual(this.name, graphPathNode.name) && Intrinsics.areEqual(this.type, graphPathNode.type) && Intrinsics.areEqual(this.typeDescription, graphPathNode.typeDescription) && Intrinsics.areEqual(this.startTimestamp, graphPathNode.startTimestamp) && Intrinsics.areEqual(this.endTimestamp, graphPathNode.endTimestamp) && Intrinsics.areEqual(this.outboundEdges, graphPathNode.outboundEdges);
    }

    public final Long getEndTimestamp() {
        return this.endTimestamp;
    }

    public final String getIri() {
        return this.iri;
    }

    public final String getLiteral() {
        return this.literal;
    }

    public final String getName() {
        return this.name;
    }

    public final List<GraphPathEdge> getOutboundEdges() {
        return this.outboundEdges;
    }

    public final Long getStartTimestamp() {
        return this.startTimestamp;
    }

    public final String getType() {
        return this.type;
    }

    public final String getTypeDescription() {
        return this.typeDescription;
    }

    public int hashCode() {
        int hashCode = this.iri.hashCode() * 31;
        String str = this.literal;
        int hashCode2 = (hashCode + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.name;
        int hashCode3 = (hashCode2 + (str2 == null ? 0 : str2.hashCode())) * 31;
        String str3 = this.type;
        int hashCode4 = (hashCode3 + (str3 == null ? 0 : str3.hashCode())) * 31;
        String str4 = this.typeDescription;
        int hashCode5 = (hashCode4 + (str4 == null ? 0 : str4.hashCode())) * 31;
        Long l = this.startTimestamp;
        int hashCode6 = (hashCode5 + (l == null ? 0 : l.hashCode())) * 31;
        Long l2 = this.endTimestamp;
        return this.outboundEdges.hashCode() + ((hashCode6 + (l2 != null ? l2.hashCode() : 0)) * 31);
    }

    public String toString() {
        return "GraphPathNode(iri=" + this.iri + ", literal=" + this.literal + ", name=" + this.name + ", type=" + this.type + ", typeDescription=" + this.typeDescription + ", startTimestamp=" + this.startTimestamp + ", endTimestamp=" + this.endTimestamp + ", outboundEdges=" + this.outboundEdges + ')';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.iri);
        parcel.writeString(this.literal);
        parcel.writeString(this.name);
        parcel.writeString(this.type);
        parcel.writeString(this.typeDescription);
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
        Iterator m = KnoxConfigurationType$$ExternalSyntheticOutline0.m(parcel, this.outboundEdges);
        while (m.hasNext()) {
            ((GraphPathEdge) m.next()).writeToParcel(parcel, i);
        }
    }

    public /* synthetic */ GraphPathNode(String str, String str2, String str3, String str4, String str5, Long l, Long l2, List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : str2, (i & 4) != 0 ? null : str3, (i & 8) != 0 ? null : str4, (i & 16) != 0 ? null : str5, (i & 32) != 0 ? null : l, (i & 64) != 0 ? null : l2, list);
    }
}
