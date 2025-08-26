package com.samsung.android.sdk.moneta.memory.entity.activity;

import android.os.Parcel;
import android.os.Parcelable;
import androidx.compose.animation.graphics.vector.PropertyValuesHolder2D$$ExternalSyntheticOutline0;
import com.samsung.android.knox.container.KnoxConfigurationType$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.moneta.basicdomain.entity.Person$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.moneta.memory.entity.Engram$Creator$$ExternalSyntheticOutline0;
import com.samsung.android.sdk.moneta.memory.entity.content.Content;
import com.samsung.android.sdk.moneta.memory.entity.content.MediaSession;
import defpackage.MoveResult$$ExternalSyntheticOutline0;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public final class ListeningToMusicActivity extends Activity {
    public static final Parcelable.Creator<ListeningToMusicActivity> CREATOR = new Creator();
    private final List<Content> contents;
    private final Long endTimestamp;
    private final MediaSession iconic;
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
                iM = Engram$Creator$$ExternalSyntheticOutline0.m(ListeningToMusicActivity.class, parcel, arrayList, iM, 1);
            }
            return new ListeningToMusicActivity(string, arrayList, MediaSession.CREATOR.createFromParcel(parcel), parcel.readLong(), parcel.readInt() == 0 ? null : Long.valueOf(parcel.readLong()));
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new ListeningToMusicActivity[i];
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ListeningToMusicActivity(String str, List<? extends Content> list, MediaSession mediaSession, long j, Long l) {
        super(ActivityType.ListeningToMusic);
        this.id = str;
        this.contents = list;
        this.iconic = mediaSession;
        this.startTimestamp = j;
        this.endTimestamp = l;
    }

    public static /* synthetic */ ListeningToMusicActivity copy$default(ListeningToMusicActivity listeningToMusicActivity, String str, List list, MediaSession mediaSession, long j, Long l, int i, Object obj) {
        if ((i & 1) != 0) {
            str = listeningToMusicActivity.id;
        }
        if ((i & 2) != 0) {
            list = listeningToMusicActivity.contents;
        }
        if ((i & 4) != 0) {
            mediaSession = listeningToMusicActivity.iconic;
        }
        if ((i & 8) != 0) {
            j = listeningToMusicActivity.startTimestamp;
        }
        if ((i & 16) != 0) {
            l = listeningToMusicActivity.endTimestamp;
        }
        Long l2 = l;
        MediaSession mediaSession2 = mediaSession;
        return listeningToMusicActivity.copy(str, list, mediaSession2, j, l2);
    }

    public final String component1() {
        return this.id;
    }

    public final List<Content> component2() {
        return this.contents;
    }

    public final MediaSession component3() {
        return this.iconic;
    }

    public final long component4() {
        return this.startTimestamp;
    }

    public final Long component5() {
        return this.endTimestamp;
    }

    public final ListeningToMusicActivity copy(String str, List<? extends Content> list, MediaSession mediaSession, long j, Long l) {
        return new ListeningToMusicActivity(str, list, mediaSession, j, l);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ListeningToMusicActivity)) {
            return false;
        }
        ListeningToMusicActivity listeningToMusicActivity = (ListeningToMusicActivity) obj;
        return Intrinsics.areEqual(this.id, listeningToMusicActivity.id) && Intrinsics.areEqual(this.contents, listeningToMusicActivity.contents) && Intrinsics.areEqual(this.iconic, listeningToMusicActivity.iconic) && this.startTimestamp == listeningToMusicActivity.startTimestamp && Intrinsics.areEqual(this.endTimestamp, listeningToMusicActivity.endTimestamp);
    }

    @Override // com.samsung.android.sdk.moneta.memory.entity.activity.Activity
    public List<Content> getContents() {
        return this.contents;
    }

    public final Long getEndTimestamp() {
        return this.endTimestamp;
    }

    public final MediaSession getIconic() {
        return this.iconic;
    }

    @Override // com.samsung.android.sdk.moneta.memory.entity.activity.Activity
    public String getId() {
        return this.id;
    }

    public final long getStartTimestamp() {
        return this.startTimestamp;
    }

    public int hashCode() {
        int iM = MoveResult$$ExternalSyntheticOutline0.m((this.iconic.hashCode() + PropertyValuesHolder2D$$ExternalSyntheticOutline0.m(this.contents, this.id.hashCode() * 31, 31)) * 31, 31, this.startTimestamp);
        Long l = this.endTimestamp;
        return iM + (l == null ? 0 : l.hashCode());
    }

    public String toString() {
        return "ListeningToMusicActivity(id=" + this.id + ", contents=" + this.contents + ", iconic=" + this.iconic + ", startTimestamp=" + this.startTimestamp + ", endTimestamp=" + this.endTimestamp + ')';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.id);
        Iterator itM = KnoxConfigurationType$$ExternalSyntheticOutline0.m(parcel, this.contents);
        while (itM.hasNext()) {
            parcel.writeParcelable((Parcelable) itM.next(), i);
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
}
