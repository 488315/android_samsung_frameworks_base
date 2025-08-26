package com.samsung.android.sdk.moneta.memory.entity.content;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.compose.animation.TransitionData$$ExternalSyntheticOutline0;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import androidx.compose.runtime.OpaqueKey$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public final class GraphPathEdge implements Parcelable {
    public static final Parcelable.Creator<GraphPathEdge> CREATOR = new Creator();
    private final String comment;
    private final boolean inverse;
    private final String iri;
    private final String label;
    private final String nextNodeIri;

    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            return new GraphPathEdge(parcel.readString(), parcel.readString(), parcel.readInt() != 0, parcel.readString(), parcel.readString());
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new GraphPathEdge[i];
        }
    }

    public GraphPathEdge(String str, String str2, boolean z, String str3, String str4) {
        this.iri = str;
        this.nextNodeIri = str2;
        this.inverse = z;
        this.label = str3;
        this.comment = str4;
    }

    public static /* synthetic */ GraphPathEdge copy$default(GraphPathEdge graphPathEdge, String str, String str2, boolean z, String str3, String str4, int i, Object obj) {
        if ((i & 1) != 0) {
            str = graphPathEdge.iri;
        }
        if ((i & 2) != 0) {
            str2 = graphPathEdge.nextNodeIri;
        }
        if ((i & 4) != 0) {
            z = graphPathEdge.inverse;
        }
        if ((i & 8) != 0) {
            str3 = graphPathEdge.label;
        }
        if ((i & 16) != 0) {
            str4 = graphPathEdge.comment;
        }
        String str5 = str4;
        boolean z2 = z;
        return graphPathEdge.copy(str, str2, z2, str3, str5);
    }

    public final String component1() {
        return this.iri;
    }

    public final String component2() {
        return this.nextNodeIri;
    }

    public final boolean component3() {
        return this.inverse;
    }

    public final String component4() {
        return this.label;
    }

    public final String component5() {
        return this.comment;
    }

    public final GraphPathEdge copy(String str, String str2, boolean z, String str3, String str4) {
        return new GraphPathEdge(str, str2, z, str3, str4);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof GraphPathEdge)) {
            return false;
        }
        GraphPathEdge graphPathEdge = (GraphPathEdge) obj;
        return Intrinsics.areEqual(this.iri, graphPathEdge.iri) && Intrinsics.areEqual(this.nextNodeIri, graphPathEdge.nextNodeIri) && this.inverse == graphPathEdge.inverse && Intrinsics.areEqual(this.label, graphPathEdge.label) && Intrinsics.areEqual(this.comment, graphPathEdge.comment);
    }

    public final String getComment() {
        return this.comment;
    }

    public final boolean getInverse() {
        return this.inverse;
    }

    public final String getIri() {
        return this.iri;
    }

    public final String getLabel() {
        return this.label;
    }

    public final String getNextNodeIri() {
        return this.nextNodeIri;
    }

    public int hashCode() {
        int iM = TransitionData$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.iri.hashCode() * 31, 31, this.nextNodeIri), 31, this.inverse);
        String str = this.label;
        int iHashCode = (iM + (str == null ? 0 : str.hashCode())) * 31;
        String str2 = this.comment;
        return iHashCode + (str2 != null ? str2.hashCode() : 0);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("GraphPathEdge(iri=");
        sb.append(this.iri);
        sb.append(", nextNodeIri=");
        sb.append(this.nextNodeIri);
        sb.append(", inverse=");
        sb.append(this.inverse);
        sb.append(", label=");
        sb.append(this.label);
        sb.append(", comment=");
        return OpaqueKey$$ExternalSyntheticOutline0.m(sb, this.comment, ')');
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.iri);
        parcel.writeString(this.nextNodeIri);
        parcel.writeInt(this.inverse ? 1 : 0);
        parcel.writeString(this.label);
        parcel.writeString(this.comment);
    }

    public /* synthetic */ GraphPathEdge(String str, String str2, boolean z, String str3, String str4, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, (i & 4) != 0 ? false : z, (i & 8) != 0 ? null : str3, (i & 16) != 0 ? null : str4);
    }
}
