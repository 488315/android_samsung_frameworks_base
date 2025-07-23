package android.content;

import android.accounts.Account;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes.dex */
public class PeriodicSync implements Parcelable {
    public static final Parcelable.Creator<PeriodicSync> CREATOR = new Parcelable.Creator<PeriodicSync>() { // from class: android.content.PeriodicSync.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PeriodicSync createFromParcel(Parcel parcel) {
            return new PeriodicSync(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public PeriodicSync[] newArray(int i) {
            return new PeriodicSync[i];
        }
    };
    public final Account account;
    public final String authority;
    public final Bundle extras;
    public final long flexTime;
    public final long period;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public PeriodicSync(Account account, String str, Bundle bundle, long j) {
        this.account = account;
        this.authority = str;
        if (bundle == null) {
            this.extras = new Bundle();
        } else {
            this.extras = new Bundle(bundle);
        }
        this.period = j;
        this.flexTime = 0L;
    }

    public PeriodicSync(PeriodicSync periodicSync) {
        this.account = periodicSync.account;
        this.authority = periodicSync.authority;
        this.extras = new Bundle(periodicSync.extras);
        this.period = periodicSync.period;
        this.flexTime = periodicSync.flexTime;
    }

    public PeriodicSync(Account account, String str, Bundle bundle, long j, long j2) {
        this.account = account;
        this.authority = str;
        this.extras = new Bundle(bundle);
        this.period = j;
        this.flexTime = j2;
    }

    private PeriodicSync(Parcel parcel) {
        this.account = (Account) parcel.readParcelable(null, Account.class);
        this.authority = parcel.readString();
        this.extras = parcel.readBundle();
        this.period = parcel.readLong();
        this.flexTime = parcel.readLong();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.account, i);
        parcel.writeString(this.authority);
        parcel.writeBundle(this.extras);
        parcel.writeLong(this.period);
        parcel.writeLong(this.flexTime);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof PeriodicSync)) {
            return false;
        }
        PeriodicSync periodicSync = (PeriodicSync) obj;
        return this.account.equals(periodicSync.account) && this.authority.equals(periodicSync.authority) && this.period == periodicSync.period && syncExtrasEquals(this.extras, periodicSync.extras);
    }

    public static boolean syncExtrasEquals(Bundle bundle, Bundle bundle2) {
        if (bundle.size() != bundle2.size()) {
            return false;
        }
        if (bundle.isEmpty()) {
            return true;
        }
        for (String str : bundle.keySet()) {
            if (!bundle2.containsKey(str) || !Objects.equals(bundle.get(str), bundle2.get(str))) {
                return false;
            }
        }
        return true;
    }

    public String toString() {
        return "account: " + this.account + ", authority: " + this.authority + ". period: " + this.period + "s , flex: " + this.flexTime;
    }
}
