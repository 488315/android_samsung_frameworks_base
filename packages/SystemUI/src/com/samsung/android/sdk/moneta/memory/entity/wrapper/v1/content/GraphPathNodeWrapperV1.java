package com.samsung.android.sdk.moneta.memory.entity.wrapper.v1.content;

import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.knox.container.KnoxConfigurationType$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.moneta.basicdomain.entity.Person$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.moneta.memory.entity.content.GraphPathEdge;
import com.samsung.android.sdk.moneta.memory.entity.content.GraphPathNode;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class GraphPathNodeWrapperV1 implements Parcelable {
    private final Long endTimestamp;
    private final String iri;
    private final String literal;
    private final String name;
    private final List<GraphPathEdge> outboundEdges;
    private final Long startTimestamp;
    private final String type;
    private final String typeDescription;
    public static final Companion Companion = new Companion(null);
    public static final Parcelable.Creator<GraphPathNodeWrapperV1> CREATOR = new Creator();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

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
            return new GraphPathNodeWrapperV1(readString, readString2, readString3, readString4, readString5, valueOf, valueOf2, arrayList);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new GraphPathNodeWrapperV1[i];
        }
    }

    public GraphPathNodeWrapperV1(String str, String str2, String str3, String str4, String str5, Long l, Long l2, List<GraphPathEdge> list) {
        this.iri = str;
        this.literal = str2;
        this.name = str3;
        this.type = str4;
        this.typeDescription = str5;
        this.startTimestamp = l;
        this.endTimestamp = l2;
        this.outboundEdges = list;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
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

    public final GraphPathNode toContent() {
        return new GraphPathNode(this.iri, this.literal, this.name, this.type, this.typeDescription, this.startTimestamp, this.endTimestamp, this.outboundEdges);
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

    public /* synthetic */ GraphPathNodeWrapperV1(String str, String str2, String str3, String str4, String str5, Long l, Long l2, List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, (i & 2) != 0 ? null : str2, (i & 4) != 0 ? null : str3, (i & 8) != 0 ? null : str4, (i & 16) != 0 ? null : str5, (i & 32) != 0 ? null : l, (i & 64) != 0 ? null : l2, list);
    }
}
