package com.samsung.android.sdk.moneta.event.entity.event;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.samsung.android.knox.container.KnoxConfigurationType$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.moneta.basicdomain.entity.Person$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public final class Event implements Parcelable {
    public static final Parcelable.Creator<Event> CREATOR = new Creator();
    private final EventCategory eventCategory;
    private final Long id;
    private final List<What> what;
    private final When when;
    private final List<Where> where;
    private final List<Who> who;

    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            Long lValueOf = parcel.readInt() == 0 ? null : Long.valueOf(parcel.readLong());
            When whenCreateFromParcel = When.CREATOR.createFromParcel(parcel);
            int i = parcel.readInt();
            ArrayList arrayList = new ArrayList(i);
            for (int i2 = 0; i2 != i; i2++) {
                arrayList.add(What.CREATOR.createFromParcel(parcel));
            }
            int i3 = parcel.readInt();
            ArrayList arrayList2 = new ArrayList(i3);
            for (int i4 = 0; i4 != i3; i4++) {
                arrayList2.add(Where.CREATOR.createFromParcel(parcel));
            }
            int i5 = parcel.readInt();
            ArrayList arrayList3 = new ArrayList(i5);
            for (int i6 = 0; i6 != i5; i6++) {
                arrayList3.add(Who.CREATOR.createFromParcel(parcel));
            }
            return new Event(lValueOf, whenCreateFromParcel, arrayList, arrayList2, arrayList3, parcel.readInt() != 0 ? EventCategory.CREATOR.createFromParcel(parcel) : null);
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new Event[i];
        }
    }

    public Event(Long l, When when, List<What> list, List<Where> list2, List<Who> list3, EventCategory eventCategory) {
        this.id = l;
        this.when = when;
        this.what = list;
        this.where = list2;
        this.who = list3;
        this.eventCategory = eventCategory;
    }

    public static /* synthetic */ Event copy$default(Event event, Long l, When when, List list, List list2, List list3, EventCategory eventCategory, int i, Object obj) {
        if ((i & 1) != 0) {
            l = event.id;
        }
        if ((i & 2) != 0) {
            when = event.when;
        }
        if ((i & 4) != 0) {
            list = event.what;
        }
        if ((i & 8) != 0) {
            list2 = event.where;
        }
        if ((i & 16) != 0) {
            list3 = event.who;
        }
        if ((i & 32) != 0) {
            eventCategory = event.eventCategory;
        }
        List list4 = list3;
        EventCategory eventCategory2 = eventCategory;
        return event.copy(l, when, list, list2, list4, eventCategory2);
    }

    public final Long component1() {
        return this.id;
    }

    public final When component2() {
        return this.when;
    }

    public final List<What> component3() {
        return this.what;
    }

    public final List<Where> component4() {
        return this.where;
    }

    public final List<Who> component5() {
        return this.who;
    }

    public final EventCategory component6() {
        return this.eventCategory;
    }

    public final Event copy(Long l, When when, List<What> list, List<Where> list2, List<Who> list3, EventCategory eventCategory) {
        return new Event(l, when, list, list2, list3, eventCategory);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof Event)) {
            return false;
        }
        Event event = (Event) obj;
        return Intrinsics.areEqual(this.id, event.id) && Intrinsics.areEqual(this.when, event.when) && Intrinsics.areEqual(this.what, event.what) && Intrinsics.areEqual(this.where, event.where) && Intrinsics.areEqual(this.who, event.who) && Intrinsics.areEqual(this.eventCategory, event.eventCategory);
    }

    public final EventCategory getEventCategory() {
        return this.eventCategory;
    }

    public final Long getId() {
        return this.id;
    }

    public final List<What> getWhat() {
        return this.what;
    }

    public final When getWhen() {
        return this.when;
    }

    public final List<Where> getWhere() {
        return this.where;
    }

    public final List<Who> getWho() {
        return this.who;
    }

    public int hashCode() {
        Long l = this.id;
        int iM = PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.who, PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.where, PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.what, (this.when.hashCode() + ((l == null ? 0 : l.hashCode()) * 31)) * 31, 31), 31), 31);
        EventCategory eventCategory = this.eventCategory;
        return iM + (eventCategory != null ? eventCategory.hashCode() : 0);
    }

    public String toString() {
        return "Event(id=" + this.id + ", when=" + this.when + ", what=" + this.what + ", where=" + this.where + ", who=" + this.who + ", eventCategory=" + this.eventCategory + ')';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        Long l = this.id;
        if (l == null) {
            parcel.writeInt(0);
        } else {
            Person$$ExternalSyntheticOutline0.m(parcel, 1, l);
        }
        this.when.writeToParcel(parcel, i);
        Iterator itM = KnoxConfigurationType$$ExternalSyntheticOutline0.m(parcel, this.what);
        while (itM.hasNext()) {
            ((What) itM.next()).writeToParcel(parcel, i);
        }
        Iterator itM2 = KnoxConfigurationType$$ExternalSyntheticOutline0.m(parcel, this.where);
        while (itM2.hasNext()) {
            ((Where) itM2.next()).writeToParcel(parcel, i);
        }
        Iterator itM3 = KnoxConfigurationType$$ExternalSyntheticOutline0.m(parcel, this.who);
        while (itM3.hasNext()) {
            ((Who) itM3.next()).writeToParcel(parcel, i);
        }
        EventCategory eventCategory = this.eventCategory;
        if (eventCategory == null) {
            parcel.writeInt(0);
        } else {
            parcel.writeInt(1);
            eventCategory.writeToParcel(parcel, i);
        }
    }
}
