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

/* loaded from: classes4.dex */
public final class StoringContentsActivity extends Activity {
    public static final Parcelable.Creator<StoringContentsActivity> CREATOR = new Creator();
    private final List<Content> contents;
    private final String id;
    private final MobileApplication receivedFromApp;
    private final Person receivedFromContact;
    private final long storedTime;

    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            String string = parcel.readString();
            int i = parcel.readInt();
            ArrayList arrayList = new ArrayList(i);
            int iM = 0;
            while (iM != i) {
                iM = Engram$Creator$$ExternalSyntheticOutline0.m(StoringContentsActivity.class, parcel, arrayList, iM, 1);
            }
            return new StoringContentsActivity(string, arrayList, parcel.readLong(), MobileApplication.CREATOR.createFromParcel(parcel), parcel.readInt() == 0 ? null : Person.CREATOR.createFromParcel(parcel));
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new StoringContentsActivity[i];
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public StoringContentsActivity(String str, List<? extends Content> list, long j, MobileApplication mobileApplication, Person person) {
        super(ActivityType.StoringContents);
        this.id = str;
        this.contents = list;
        this.storedTime = j;
        this.receivedFromApp = mobileApplication;
        this.receivedFromContact = person;
    }

    public static /* synthetic */ StoringContentsActivity copy$default(StoringContentsActivity storingContentsActivity, String str, List list, long j, MobileApplication mobileApplication, Person person, int i, Object obj) {
        if ((i & 1) != 0) {
            str = storingContentsActivity.id;
        }
        if ((i & 2) != 0) {
            list = storingContentsActivity.contents;
        }
        if ((i & 4) != 0) {
            j = storingContentsActivity.storedTime;
        }
        if ((i & 8) != 0) {
            mobileApplication = storingContentsActivity.receivedFromApp;
        }
        if ((i & 16) != 0) {
            person = storingContentsActivity.receivedFromContact;
        }
        long j2 = j;
        return storingContentsActivity.copy(str, list, j2, mobileApplication, person);
    }

    public final String component1() {
        return this.id;
    }

    public final List<Content> component2() {
        return this.contents;
    }

    public final long component3() {
        return this.storedTime;
    }

    public final MobileApplication component4() {
        return this.receivedFromApp;
    }

    public final Person component5() {
        return this.receivedFromContact;
    }

    public final StoringContentsActivity copy(String str, List<? extends Content> list, long j, MobileApplication mobileApplication, Person person) {
        return new StoringContentsActivity(str, list, j, mobileApplication, person);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof StoringContentsActivity)) {
            return false;
        }
        StoringContentsActivity storingContentsActivity = (StoringContentsActivity) obj;
        return Intrinsics.areEqual(this.id, storingContentsActivity.id) && Intrinsics.areEqual(this.contents, storingContentsActivity.contents) && this.storedTime == storingContentsActivity.storedTime && Intrinsics.areEqual(this.receivedFromApp, storingContentsActivity.receivedFromApp) && Intrinsics.areEqual(this.receivedFromContact, storingContentsActivity.receivedFromContact);
    }

    @Override // com.samsung.android.sdk.moneta.memory.entity.activity.Activity
    public List<Content> getContents() {
        return this.contents;
    }

    @Override // com.samsung.android.sdk.moneta.memory.entity.activity.Activity
    public String getId() {
        return this.id;
    }

    public final MobileApplication getReceivedFromApp() {
        return this.receivedFromApp;
    }

    public final Person getReceivedFromContact() {
        return this.receivedFromContact;
    }

    public final long getStoredTime() {
        return this.storedTime;
    }

    public int hashCode() {
        int iHashCode = (this.receivedFromApp.hashCode() + MoveResult$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.contents, this.id.hashCode() * 31, 31), 31, this.storedTime)) * 31;
        Person person = this.receivedFromContact;
        return iHashCode + (person == null ? 0 : person.hashCode());
    }

    public String toString() {
        return "StoringContentsActivity(id=" + this.id + ", contents=" + this.contents + ", storedTime=" + this.storedTime + ", receivedFromApp=" + this.receivedFromApp + ", receivedFromContact=" + this.receivedFromContact + ')';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        Iterator itM = KnoxConfigurationType$$ExternalSyntheticOutline0.m(parcel, this.contents);
        while (itM.hasNext()) {
            parcel.writeParcelable((Parcelable) itM.next(), i);
        }
        parcel.writeLong(this.storedTime);
        this.receivedFromApp.writeToParcel(parcel, i);
        Person person = this.receivedFromContact;
        if (person == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            person.writeToParcel(parcel, i);
        }
    }
}
