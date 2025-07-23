package com.samsung.android.sdk.moneta.memory.entity.wrapper.v1.content;

import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.sdk.moneta.memory.entity.content.GraphPathEdge;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class GraphPathEdgeWrapperV1 implements Parcelable {
    private final String comment;
    private final boolean inverse;
    private final String iri;
    private final String label;
    private final String nextNodeIri;
    public static final Companion Companion = new Companion(null);
    public static final Parcelable.Creator<GraphPathEdgeWrapperV1> CREATOR = new Creator();

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
            return new GraphPathEdgeWrapperV1(parcel.readString(), parcel.readString(), parcel.readInt() != 0, parcel.readString(), parcel.readString());
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new GraphPathEdgeWrapperV1[i];
        }
    }

    public GraphPathEdgeWrapperV1(String str, String str2, boolean z, String str3, String str4) {
        this.iri = str;
        this.nextNodeIri = str2;
        this.inverse = z;
        this.label = str3;
        this.comment = str4;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
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

    public final GraphPathEdge toContent() {
        return new GraphPathEdge(this.iri, this.nextNodeIri, this.inverse, this.label, this.comment);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.iri);
        parcel.writeString(this.nextNodeIri);
        parcel.writeInt(this.inverse ? 1 : 0);
        parcel.writeString(this.label);
        parcel.writeString(this.comment);
    }

    public /* synthetic */ GraphPathEdgeWrapperV1(String str, String str2, boolean z, String str3, String str4, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(str, str2, (i & 4) != 0 ? false : z, (i & 8) != 0 ? null : str3, (i & 16) != 0 ? null : str4);
    }
}
