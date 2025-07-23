package com.samsung.android.knox;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes6.dex */
public enum SemPersonaState implements Parcelable {
    INVALID(-1),
    CREATING(1),
    ACTIVE(0),
    LOCKED(2),
    SUPER_LOCKED(-1),
    LICENSE_LOCKED(9),
    ADMIN_LOCKED(8),
    ADMIN_LICENSE_LOCKED(-1),
    TERMINUS(-1),
    DELETING(3),
    TIMA_COMPROMISED(7),
    CONTAINER_APPS_URGENT_UPDATE(-1);

    public static final Parcelable.Creator<SemPersonaState> CREATOR = new Parcelable.Creator<SemPersonaState>() { // from class: com.samsung.android.knox.SemPersonaState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemPersonaState createFromParcel(Parcel parcel) {
            return SemPersonaState.valueOf(parcel.readString());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemPersonaState[] newArray(int i) {
            return new SemPersonaState[i];
        }
    };
    private int knox2_0_state_id;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    SemPersonaState(int i) {
        this.knox2_0_state_id = i;
    }

    public int getKnox2_0State() {
        return this.knox2_0_state_id;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name());
    }
}
