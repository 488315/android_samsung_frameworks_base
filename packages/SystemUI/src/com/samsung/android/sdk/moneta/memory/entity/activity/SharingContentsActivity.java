package com.samsung.android.sdk.moneta.memory.entity.activity;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.samsung.android.knox.container.KnoxConfigurationType$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.moneta.memory.entity.Engram$Creator$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.moneta.memory.entity.content.Content;
import com.samsung.android.sdk.moneta.memory.entity.content.MobileApplication;
import com.samsung.android.sdk.moneta.memory.entity.context.Person;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class SharingContentsActivity extends Activity {
    public static final Parcelable.Creator<SharingContentsActivity> CREATOR = new Creator();
    private final List<Content> contents;
    private final String id;
    private final String recipientName;
    private final MobileApplication sharedFromApp;
    private final List<Person> sharedPerson;
    private final long sharedTime;
    private final MobileApplication sharedToApp;

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
                i = Engram$Creator$$ExternalSyntheticOutline0.m(SharingContentsActivity.class, parcel, arrayList2, i, 1);
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
            return new SharingContentsActivity(readString, arrayList2, readLong, readString2, createFromParcel, createFromParcel2, arrayList);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new SharingContentsActivity[i];
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SharingContentsActivity(String str, List<? extends Content> list, long j, String str2, MobileApplication mobileApplication, MobileApplication mobileApplication2, List<Person> list2) {
        super(ActivityType.SharingContents);
        this.id = str;
        this.contents = list;
        this.sharedTime = j;
        this.recipientName = str2;
        this.sharedToApp = mobileApplication;
        this.sharedFromApp = mobileApplication2;
        this.sharedPerson = list2;
    }

    public static /* synthetic */ SharingContentsActivity copy$default(SharingContentsActivity sharingContentsActivity, String str, List list, long j, String str2, MobileApplication mobileApplication, MobileApplication mobileApplication2, List list2, int i, Object obj) {
        if ((i & 1) != 0) {
            str = sharingContentsActivity.id;
        }
        if ((i & 2) != 0) {
            list = sharingContentsActivity.contents;
        }
        if ((i & 4) != 0) {
            j = sharingContentsActivity.sharedTime;
        }
        if ((i & 8) != 0) {
            str2 = sharingContentsActivity.recipientName;
        }
        if ((i & 16) != 0) {
            mobileApplication = sharingContentsActivity.sharedToApp;
        }
        if ((i & 32) != 0) {
            mobileApplication2 = sharingContentsActivity.sharedFromApp;
        }
        if ((i & 64) != 0) {
            list2 = sharingContentsActivity.sharedPerson;
        }
        long j2 = j;
        return sharingContentsActivity.copy(str, list, j2, str2, mobileApplication, mobileApplication2, list2);
    }

    public final String component1() {
        return this.id;
    }

    public final List<Content> component2() {
        return this.contents;
    }

    public final long component3() {
        return this.sharedTime;
    }

    public final String component4() {
        return this.recipientName;
    }

    public final MobileApplication component5() {
        return this.sharedToApp;
    }

    public final MobileApplication component6() {
        return this.sharedFromApp;
    }

    public final List<Person> component7() {
        return this.sharedPerson;
    }

    public final SharingContentsActivity copy(String str, List<? extends Content> list, long j, String str2, MobileApplication mobileApplication, MobileApplication mobileApplication2, List<Person> list2) {
        return new SharingContentsActivity(str, list, j, str2, mobileApplication, mobileApplication2, list2);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SharingContentsActivity)) {
            return false;
        }
        SharingContentsActivity sharingContentsActivity = (SharingContentsActivity) obj;
        return Intrinsics.areEqual(this.id, sharingContentsActivity.id) && Intrinsics.areEqual(this.contents, sharingContentsActivity.contents) && this.sharedTime == sharingContentsActivity.sharedTime && Intrinsics.areEqual(this.recipientName, sharingContentsActivity.recipientName) && Intrinsics.areEqual(this.sharedToApp, sharingContentsActivity.sharedToApp) && Intrinsics.areEqual(this.sharedFromApp, sharingContentsActivity.sharedFromApp) && Intrinsics.areEqual(this.sharedPerson, sharingContentsActivity.sharedPerson);
    }

    @Override // com.samsung.android.sdk.moneta.memory.entity.activity.Activity
    public List<Content> getContents() {
        return this.contents;
    }

    @Override // com.samsung.android.sdk.moneta.memory.entity.activity.Activity
    public String getId() {
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

    public int hashCode() {
        int hashCode = (this.sharedFromApp.hashCode() + ((this.sharedToApp.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(MoveResult$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.contents, this.id.hashCode() * 31, 31), 31, this.sharedTime), 31, this.recipientName)) * 31)) * 31;
        List<Person> list = this.sharedPerson;
        return hashCode + (list == null ? 0 : list.hashCode());
    }

    public String toString() {
        return "SharingContentsActivity(id=" + this.id + ", contents=" + this.contents + ", sharedTime=" + this.sharedTime + ", recipientName=" + this.recipientName + ", sharedToApp=" + this.sharedToApp + ", sharedFromApp=" + this.sharedFromApp + ", sharedPerson=" + this.sharedPerson + ')';
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
}
