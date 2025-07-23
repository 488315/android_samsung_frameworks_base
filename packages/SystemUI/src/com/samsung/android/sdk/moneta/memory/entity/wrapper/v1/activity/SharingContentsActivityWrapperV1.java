package com.samsung.android.sdk.moneta.memory.entity.wrapper.v1.activity;

import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.knox.container.KnoxConfigurationType$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.moneta.memory.entity.Engram$Creator$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.moneta.memory.entity.activity.SharingContentsActivity;
import com.samsung.android.sdk.moneta.memory.entity.content.Content;
import com.samsung.android.sdk.moneta.memory.entity.content.MobileApplication;
import com.samsung.android.sdk.moneta.memory.entity.context.Person;
import com.samsung.android.sdk.moneta.memory.entity.wrapper.ActivityWrapper;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SharingContentsActivityWrapperV1 extends ActivityWrapper {
    private final List<Content> contents;
    private final String id;
    private final String recipientName;
    private final MobileApplication sharedFromApp;
    private final List<Person> sharedPerson;
    private final long sharedTime;
    private final MobileApplication sharedToApp;
    public static final Companion Companion = new Companion(null);
    public static final Parcelable.Creator<SharingContentsActivityWrapperV1> CREATOR = new Creator();

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
            ArrayList arrayList;
            String readString = parcel.readString();
            int readInt = parcel.readInt();
            ArrayList arrayList2 = new ArrayList(readInt);
            int i = 0;
            while (i != readInt) {
                i = Engram$Creator$$ExternalSyntheticOutline0.m(SharingContentsActivityWrapperV1.class, parcel, arrayList2, i, 1);
            }
            long readLong = parcel.readLong();
            String readString2 = parcel.readString();
            Parcelable.Creator<MobileApplication> creator = MobileApplication.CREATOR;
            MobileApplication createFromParcel = creator.createFromParcel(parcel);
            MobileApplication createFromParcel2 = creator.createFromParcel(parcel);
            if (parcel.readInt() == 0) {
                arrayList = null;
            } else {
                int readInt2 = parcel.readInt();
                arrayList = new ArrayList(readInt2);
                for (int i2 = 0; i2 != readInt2; i2++) {
                    arrayList.add(Person.CREATOR.createFromParcel(parcel));
                }
            }
            return new SharingContentsActivityWrapperV1(readString, arrayList2, readLong, readString2, createFromParcel, createFromParcel2, arrayList);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new SharingContentsActivityWrapperV1[i];
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SharingContentsActivityWrapperV1(String str, List<? extends Content> list, long j, String str2, MobileApplication mobileApplication, MobileApplication mobileApplication2, List<Person> list2) {
        this.id = str;
        this.contents = list;
        this.sharedTime = j;
        this.recipientName = str2;
        this.sharedToApp = mobileApplication;
        this.sharedFromApp = mobileApplication2;
        this.sharedPerson = list2;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public final List<Content> getContents() {
        return this.contents;
    }

    public final String getId() {
        return this.id;
    }

    public final String getRecipientName() {
        return this.recipientName;
    }

    public final MobileApplication getSharedFromApp() {
        return this.sharedFromApp;
    }

    public final List<Person> getSharedPerson() {
        return this.sharedPerson;
    }

    public final long getSharedTime() {
        return this.sharedTime;
    }

    public final MobileApplication getSharedToApp() {
        return this.sharedToApp;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        Iterator m = KnoxConfigurationType$$ExternalSyntheticOutline0.m(parcel, this.contents);
        while (m.hasNext()) {
            parcel.writeParcelable((Parcelable) m.next(), i);
        }
        parcel.writeLong(this.sharedTime);
        parcel.writeString(this.recipientName);
        this.sharedToApp.writeToParcel(parcel, i);
        this.sharedFromApp.writeToParcel(parcel, i);
        List<Person> list = this.sharedPerson;
        if (list == null) {
            parcel.writeInt(0);
            return;
        }
        parcel.writeInt(1);
        parcel.writeInt(list.size());
        Iterator<Person> it = list.iterator();
        while (it.hasNext()) {
            it.next().writeToParcel(parcel, i);
        }
    }

    @Override // com.samsung.android.sdk.moneta.memory.entity.wrapper.ActivityWrapper
    public SharingContentsActivity toActivity() {
        return new SharingContentsActivity(this.id, this.contents, this.sharedTime, this.recipientName, this.sharedToApp, this.sharedFromApp, this.sharedPerson);
    }
}
