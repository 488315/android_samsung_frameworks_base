package com.android.internal.statusbar;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Pair;
import java.util.HashMap;
import java.util.Map;

/* loaded from: classes4.dex */
public class DisableStates implements Parcelable {
    public static final Parcelable.Creator<DisableStates> CREATOR = new Parcelable.Creator<DisableStates>() { // from class: com.android.internal.statusbar.DisableStates.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DisableStates createFromParcel(Parcel parcel) {
            int i = parcel.readInt();
            HashMap map = new HashMap(i);
            for (int i2 = 0; i2 < i; i2++) {
                map.put(Integer.valueOf(parcel.readInt()), new Pair(Integer.valueOf(parcel.readInt()), Integer.valueOf(parcel.readInt())));
            }
            return new DisableStates(map, parcel.readBoolean());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DisableStates[] newArray(int i) {
            return new DisableStates[i];
        }
    };
    public boolean animate;
    public Map<Integer, Pair<Integer, Integer>> displaysWithStates;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public DisableStates(Map<Integer, Pair<Integer, Integer>> map, boolean z) {
        this.displaysWithStates = map;
        this.animate = z;
    }

    public DisableStates(Map<Integer, Pair<Integer, Integer>> map) {
        this(map, true);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.displaysWithStates.size());
        for (Map.Entry<Integer, Pair<Integer, Integer>> entry : this.displaysWithStates.entrySet()) {
            parcel.writeInt(entry.getKey().intValue());
            parcel.writeInt(entry.getValue().first.intValue());
            parcel.writeInt(entry.getValue().second.intValue());
        }
        parcel.writeBoolean(this.animate);
    }
}
