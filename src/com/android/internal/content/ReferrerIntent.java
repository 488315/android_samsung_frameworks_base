package com.android.internal.content;

import android.content.Intent;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes5.dex */
public class ReferrerIntent extends Intent {
    public static final Parcelable.Creator<ReferrerIntent> CREATOR = new Parcelable.Creator<ReferrerIntent>() { // from class: com.android.internal.content.ReferrerIntent.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ReferrerIntent createFromParcel(Parcel parcel) {
            return new ReferrerIntent(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ReferrerIntent[] newArray(int i) {
            return new ReferrerIntent[i];
        }
    };
    public final IBinder mCallerToken;
    public final String mReferrer;

    public ReferrerIntent(Intent intent, String str) {
        this(intent, str, null);
    }

    public ReferrerIntent(Intent intent, String str, IBinder iBinder) {
        super(intent);
        this.mReferrer = str;
        this.mCallerToken = iBinder;
    }

    @Override // android.content.Intent, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        super.writeToParcel(parcel, i);
        parcel.writeString(this.mReferrer);
        parcel.writeStrongBinder(this.mCallerToken);
    }

    ReferrerIntent(Parcel parcel) {
        readFromParcel(parcel);
        this.mReferrer = parcel.readString();
        this.mCallerToken = parcel.readStrongBinder();
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof ReferrerIntent)) {
            ReferrerIntent referrerIntent = (ReferrerIntent) obj;
            if (filterEquals(referrerIntent) && Objects.equals(this.mReferrer, referrerIntent.mReferrer) && Objects.equals(this.mCallerToken, referrerIntent.mCallerToken)) {
                return true;
            }
        }
        return false;
    }

    public int hashCode() {
        return ((((527 + filterHashCode()) * 31) + Objects.hashCode(this.mReferrer)) * 31) + Objects.hashCode(this.mCallerToken);
    }
}
