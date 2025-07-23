package com.samsung.android.sdk.moneta.memory.entity.wrapper.v1.activity;

import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.knox.container.KnoxConfigurationType$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.moneta.basicdomain.entity.Person$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.moneta.memory.entity.Engram$Creator$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.moneta.memory.entity.activity.WatchingVideoActivity;
import com.samsung.android.sdk.moneta.memory.entity.content.Content;
import com.samsung.android.sdk.moneta.memory.entity.content.MediaSession;
import com.samsung.android.sdk.moneta.memory.entity.wrapper.ActivityWrapper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class WatchingVideoActivityWrapperV1 extends ActivityWrapper {
    private final List<Content> contents;
    private final Long endTimestamp;
    private final MediaSession iconic;
    private final String id;
    private final long startTimestamp;
    public static final Companion Companion = new Companion(null);
    public static final Parcelable.Creator<WatchingVideoActivityWrapperV1> CREATOR = new Creator();

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
            int readInt = parcel.readInt();
            ArrayList arrayList = new ArrayList(readInt);
            int i = 0;
            while (i != readInt) {
                i = Engram$Creator$$ExternalSyntheticOutline0.m(WatchingVideoActivityWrapperV1.class, parcel, arrayList, i, 1);
            }
            return new WatchingVideoActivityWrapperV1(readString, arrayList, MediaSession.CREATOR.createFromParcel(parcel), parcel.readLong(), parcel.readInt() == 0 ? null : Long.valueOf(parcel.readLong()));
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new WatchingVideoActivityWrapperV1[i];
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public WatchingVideoActivityWrapperV1(String str, List<? extends Content> list, MediaSession mediaSession, long j, Long l) {
        this.id = str;
        this.contents = list;
        this.iconic = mediaSession;
        this.startTimestamp = j;
        this.endTimestamp = l;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final List<Content> getContents() {
        return this.contents;
    }

    public final Long getEndTimestamp() {
        return this.endTimestamp;
    }

    public final MediaSession getIconic() {
        return this.iconic;
    }

    public final String getId() {
        return this.id;
    }

    public final long getStartTimestamp() {
        return this.startTimestamp;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        Iterator m = KnoxConfigurationType$$ExternalSyntheticOutline0.m(parcel, this.contents);
        while (m.hasNext()) {
            parcel.writeParcelable((Parcelable) m.next(), i);
        }
        this.iconic.writeToParcel(parcel, i);
        parcel.writeLong(this.startTimestamp);
        Long l = this.endTimestamp;
        if (l == null) {
            parcel.writeInt(0);
        } else {
            Person$$ExternalSyntheticOutline0.m(parcel, 1, l);
        }
    }

    @Override // com.samsung.android.sdk.moneta.memory.entity.wrapper.ActivityWrapper
    public WatchingVideoActivity toActivity() {
        return new WatchingVideoActivity(this.id, this.contents, this.iconic, this.startTimestamp, this.endTimestamp);
    }
}
