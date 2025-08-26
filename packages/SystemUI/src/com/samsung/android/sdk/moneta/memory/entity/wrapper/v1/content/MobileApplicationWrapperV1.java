package com.samsung.android.sdk.moneta.memory.entity.wrapper.v1.content;

import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.sdk.moneta.memory.entity.content.MobileApplication;
import com.samsung.android.sdk.moneta.memory.entity.wrapper.ContentWrapper;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class MobileApplicationWrapperV1 extends ContentWrapper {
    private final List<String> altNames;
    private final String id;
    private final String name;
    private final String packageId;
    public static final Companion Companion = new Companion(null);
    public static final Parcelable.Creator<MobileApplicationWrapperV1> CREATOR = new Creator();

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
            return new MobileApplicationWrapperV1(parcel.readString(), parcel.readString(), parcel.readString(), parcel.createStringArrayList());
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new MobileApplicationWrapperV1[i];
        }
    }

    public MobileApplicationWrapperV1(String str, String str2, String str3, List<String> list) {
        this.id = str;
        this.packageId = str2;
        this.name = str3;
        this.altNames = list;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final List<String> getAltNames() {
        return this.altNames;
    }

    public final String getId() {
        return this.id;
    }

    public final String getName() {
        return this.name;
    }

    public final String getPackageId() {
        return this.packageId;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        parcel.writeString(this.packageId);
        parcel.writeString(this.name);
        parcel.writeStringList(this.altNames);
    }

    @Override // com.samsung.android.sdk.moneta.memory.entity.wrapper.ContentWrapper
    public MobileApplication toContent() {
        return new MobileApplication(this.id, this.packageId, this.name, this.altNames);
    }
}
