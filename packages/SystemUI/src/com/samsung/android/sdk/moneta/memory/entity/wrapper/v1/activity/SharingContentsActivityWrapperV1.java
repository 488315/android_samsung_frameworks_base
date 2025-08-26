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
            String string = parcel.readString();
            int i = parcel.readInt();
            ArrayList arrayList2 = new ArrayList(i);
            int iM = 0;
            while (iM != i) {
                iM = Engram$Creator$$ExternalSyntheticOutline0.m(SharingContentsActivityWrapperV1.class, parcel, arrayList2, iM, 1);
            }
            long j = parcel.readLong();
            String string2 = parcel.readString();
            Parcelable.Creator<MobileApplication> creator = MobileApplication.CREATOR;
            MobileApplication mobileApplicationCreateFromParcel = creator.createFromParcel(parcel);
            MobileApplication mobileApplicationCreateFromParcel2 = creator.createFromParcel(parcel);
            if (parcel.readInt() == 0) {
                arrayList = null;
            } else {
                int i2 = parcel.readInt();
                arrayList = new ArrayList(i2);
                for (int i3 = 0; i3 != i2; i3++) {
                    arrayList.add(Person.CREATOR.createFromParcel(parcel));
                }
            }
            return new SharingContentsActivityWrapperV1(string, arrayList2, j, string2, mobileApplicationCreateFromParcel, mobileApplicationCreateFromParcel2, arrayList);
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
        Iterator itM = KnoxConfigurationType$$ExternalSyntheticOutline0.m(parcel, this.contents);
        while (itM.hasNext()) {
            parcel.writeParcelable((Parcelable) itM.next(), i);
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
