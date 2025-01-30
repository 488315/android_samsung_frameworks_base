package com.samsung.android.knox.accounts;

import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.knox.ControlInfo;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class AccountControlInfo extends ControlInfo {
    public static final Parcelable.Creator<AccountControlInfo> CREATOR = new Parcelable.Creator<AccountControlInfo>() { // from class: com.samsung.android.knox.accounts.AccountControlInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final AccountControlInfo[] newArray(int i) {
            return new AccountControlInfo[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final AccountControlInfo createFromParcel(Parcel parcel) {
            return new AccountControlInfo(parcel, 0);
        }

        @Override // android.os.Parcelable.Creator
        public final AccountControlInfo[] newArray(int i) {
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
