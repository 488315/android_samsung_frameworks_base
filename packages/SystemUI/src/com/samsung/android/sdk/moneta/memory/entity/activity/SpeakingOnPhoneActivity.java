package com.samsung.android.sdk.moneta.memory.entity.activity;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.samsung.android.knox.container.KnoxConfigurationType$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.moneta.basicdomain.entity.Person$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.moneta.memory.entity.Engram$Creator$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.moneta.memory.entity.content.Content;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public final class SpeakingOnPhoneActivity extends Activity {
    public static final Parcelable.Creator<SpeakingOnPhoneActivity> CREATOR = new Creator();
    private final List<Content> contents;
    private final Long endTimestamp;
    private final String id;
    private final long startTimestamp;

    public final class Creator implements Parcelable.Creator {
        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            String string = parcel.readString();
            int i = parcel.readInt();
            ArrayList arrayList = new ArrayList(i);
            int iM = 0;
            while (iM != i) {
                iM = Engram$Creator$$ExternalSyntheticOutline0.m(SpeakingOnPhoneActivity.class, parcel, arrayList, iM, 1);
            }
            return new SpeakingOnPhoneActivity(string, arrayList, parcel.readLong(), parcel.readInt() == 0 ? null : Long.valueOf(parcel.readLong()));
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new SpeakingOnPhoneActivity[i];
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SpeakingOnPhoneActivity(String str, List<? extends Content> list, long j, Long l) {
        super(ActivityType.SpeakingOnPhone);
        this.id = str;
        this.contents = list;
        this.startTimestamp = j;
        this.endTimestamp = l;
    }

    public static /* synthetic */ SpeakingOnPhoneActivity copy$default(SpeakingOnPhoneActivity speakingOnPhoneActivity, String str, List list, long j, Long l, int i, Object obj) {
        if ((i & 1) != 0) {
            str = speakingOnPhoneActivity.id;
        }
        if ((i & 2) != 0) {
            list = speakingOnPhoneActivity.contents;
        }
        if ((i & 4) != 0) {
            j = speakingOnPhoneActivity.startTimestamp;
        }
        if ((i & 8) != 0) {
            l = speakingOnPhoneActivity.endTimestamp;
        }
        Long l2 = l;
        return speakingOnPhoneActivity.copy(str, list, j, l2);
    }

    public final String component1() {
        return this.id;
    }

    public final List<Content> component2() {
        return this.contents;
    }

    public final long component3() {
        return this.startTimestamp;
    }

    public final Long component4() {
        return this.endTimestamp;
    }

    public final SpeakingOnPhoneActivity copy(String str, List<? extends Content> list, long j, Long l) {
        return new SpeakingOnPhoneActivity(str, list, j, l);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof SpeakingOnPhoneActivity)) {
            return false;
        }
        SpeakingOnPhoneActivity speakingOnPhoneActivity = (SpeakingOnPhoneActivity) obj;
        return Intrinsics.areEqual(this.id, speakingOnPhoneActivity.id) && Intrinsics.areEqual(this.contents, speakingOnPhoneActivity.contents) && this.startTimestamp == speakingOnPhoneActivity.startTimestamp && Intrinsics.areEqual(this.endTimestamp, speakingOnPhoneActivity.endTimestamp);
    }

    @Override // com.samsung.android.sdk.moneta.memory.entity.activity.Activity
    public List<Content> getContents() {
        return this.contents;
    }

    public final Long getEndTimestamp() {
        return this.endTimestamp;
    }

    @Override // com.samsung.android.sdk.moneta.memory.entity.activity.Activity
    public String getId() {
        return this.id;
    }

    public final long getStartTimestamp() {
        return this.startTimestamp;
    }

    public int hashCode() {
        int iM = MoveResult$$ExternalSyntheticOutline0.m(PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.contents, this.id.hashCode() * 31, 31), 31, this.startTimestamp);
        Long l = this.endTimestamp;
        return iM + (l == null ? 0 : l.hashCode());
    }

    public String toString() {
        return "SpeakingOnPhoneActivity(id=" + this.id + ", contents=" + this.contents + ", startTimestamp=" + this.startTimestamp + ", endTimestamp=" + this.endTimestamp + ')';
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
}
