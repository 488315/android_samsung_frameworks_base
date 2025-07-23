package com.samsung.android.knox.accounts;

import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.knox.ControlInfo;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class AccountControlInfo extends ControlInfo {
    public static final Parcelable.Creator<AccountControlInfo> CREATOR = new Parcelable.Creator<AccountControlInfo>() { // from class: com.samsung.android.knox.accounts.AccountControlInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AccountControlInfo createFromParcel(Parcel parcel) {
            return new AccountControlInfo(parcel, 0);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AccountControlInfo[] newArray(int i) {
            return new AccountControlInfo[i];
        }
    };

    public /* synthetic */ AccountControlInfo(Parcel parcel, int i) {
        this(parcel);
    }

    public AccountControlInfo() {
    }

    private AccountControlInfo(Parcel parcel) {
        readFromParcel(parcel);
    }
}
