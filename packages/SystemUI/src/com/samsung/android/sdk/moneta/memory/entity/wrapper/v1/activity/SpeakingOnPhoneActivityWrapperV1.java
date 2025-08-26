package com.samsung.android.sdk.moneta.memory.entity.wrapper.v1.activity;

import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.knox.container.KnoxConfigurationType$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.moneta.basicdomain.entity.Person$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.moneta.memory.entity.Engram$Creator$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.moneta.memory.entity.activity.SpeakingOnPhoneActivity;
import com.samsung.android.sdk.moneta.memory.entity.content.Content;
import com.samsung.android.sdk.moneta.memory.entity.wrapper.ActivityWrapper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class SpeakingOnPhoneActivityWrapperV1 extends ActivityWrapper {
    private final List<Content> contents;
    private final Long endTimestamp;
    private final String id;
    private final long startTimestamp;
    public static final Companion Companion = new Companion(null);
    public static final Parcelable.Creator<SpeakingOnPhoneActivityWrapperV1> CREATOR = new Creator();

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
            String string = parcel.readString();
            int i = parcel.readInt();
            ArrayList arrayList = new ArrayList(i);
            int iM = 0;
            while (iM != i) {
                iM = Engram$Creator$$ExternalSyntheticOutline0.m(SpeakingOnPhoneActivityWrapperV1.class, parcel, arrayList, iM, 1);
            }
            return new SpeakingOnPhoneActivityWrapperV1(string, arrayList, parcel.readLong(), parcel.readInt() == 0 ? null : Long.valueOf(parcel.readLong()));
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new SpeakingOnPhoneActivityWrapperV1[i];
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SpeakingOnPhoneActivityWrapperV1(String str, List<? extends Content> list, long j, Long l) {
        this.id = str;
        this.contents = list;
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

    public final String getId() {
        return this.id;
    }

    public final long getStartTimestamp() {
        return this.startTimestamp;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        Iterator itM = KnoxConfigurationType$$ExternalSyntheticOutline0.m(parcel, this.contents);
        while (itM.hasNext()) {
            parcel.writeParcelable((Parcelable) itM.next(), i);
        }
        parcel.writeLong(this.startTimestamp);
        Long l = this.endTimestamp;
        if (l == null) {
            parcel.writeInt(0);
        } else {
            Person$$ExternalSyntheticOutline0.m(parcel, 1, l);
        }
    }

    @Override // com.samsung.android.sdk.moneta.memory.entity.wrapper.ActivityWrapper
    public SpeakingOnPhoneActivity toActivity() {
        return new SpeakingOnPhoneActivity(this.id, this.contents, this.startTimestamp, this.endTimestamp);
    }
}
