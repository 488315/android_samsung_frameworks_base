package com.samsung.android.sivs.p049ai.sdkcommon.tts;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.concurrent.futures.AbstractResolvableFuture$$ExternalSyntheticOutline0;
import java.util.Objects;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class TtsSpeakerInfo implements Parcelable {
    public static final Parcelable.Creator<TtsSpeakerInfo> CREATOR = new Parcelable.Creator<TtsSpeakerInfo>() { // from class: com.samsung.android.sivs.ai.sdkcommon.tts.TtsSpeakerInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TtsSpeakerInfo createFromParcel(Parcel parcel) {
            return new TtsSpeakerInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public TtsSpeakerInfo[] newArray(int i) {
            return new TtsSpeakerInfo[i];
        }
    };
    private final String displayName;

    /* renamed from: id */
    private final String f631id;

    public TtsSpeakerInfo(String str, String str2) {
        this.f631id = str;
        this.displayName = str2;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof TtsSpeakerInfo)) {
            return false;
        }
        return this.f631id.equals(((TtsSpeakerInfo) obj).f631id);
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public String getId() {
        return this.f631id;
    }

    public int hashCode() {
        return Objects.hash(this.f631id);
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("Speaker {id='");
        sb.append(this.f631id);
        sb.append("', Name='");
        return AbstractResolvableFuture$$ExternalSyntheticOutline0.m16m(sb, this.displayName, "'}");
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.f631id);
        parcel.writeString(this.displayName);
    }

    public TtsSpeakerInfo(Parcel parcel) {
        this.f631id = parcel.readString();
        this.displayName = parcel.readString();
    }
}
