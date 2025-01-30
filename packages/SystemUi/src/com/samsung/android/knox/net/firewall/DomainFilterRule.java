package com.samsung.android.knox.net.firewall;

import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.knox.AppIdentity;
import java.util.ArrayList;
import java.util.List;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class DomainFilterRule implements Parcelable {
    public static final List<DomainFilterRule> CLEAR_ALL = null;
    public static final Parcelable.Creator<DomainFilterRule> CREATOR = new Parcelable.Creator<DomainFilterRule>() { // from class: com.samsung.android.knox.net.firewall.DomainFilterRule.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final DomainFilterRule[] newArray(int i) {
            return new DomainFilterRule[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public final DomainFilterRule createFromParcel(Parcel parcel) {
            return new DomainFilterRule(parcel, 0);
        }

        @Override // android.os.Parcelable.Creator
        public final DomainFilterRule[] newArray(int i) {
            return new DomainFilterRule[i];
        }
    };
    public List<String> mAllowDomains;
    public AppIdentity mAppIdentity;
    public List<String> mDenyDomains;
    public String mDns1;
    public String mDns2;
    public int mIpcToken;
    public int mNullCheck;

    public /* synthetic */ DomainFilterRule(Parcel parcel, int i) {
        this(parcel);
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final List<String> getAllowDomains() {
        return this.mAllowDomains;
    }

    public final AppIdentity getApplication() {
        return this.mAppIdentity;
    }

    public final List<String> getDenyDomains() {
        return this.mDenyDomains;
    }

    public final String getDns1() {
        return this.mDns1;
    }

    public final String getDns2() {
        return this.mDns2;
    }

    public final int getIpcToken() {
        return this.mIpcToken;
    }

    public final void setAllowDomains(List<String> list) {
        if (list != null) {
            this.mAllowDomains = new ArrayList(list);
        } else {
            this.mAllowDomains = null;
        }
    }

    public final void setApplication(AppIdentity appIdentity) {
        this.mAppIdentity = appIdentity;
    }

    public final void setDenyDomains(List<String> list) {
        if (list != null) {
            this.mDenyDomains = new ArrayList(list);
        } else {
            this.mDenyDomains = null;
        }
    }

    public final void setDns1(String str) {
        this.mDns1 = str;
    }

    public final void setDns2(String str) {
        this.mDns2 = str;
    }

    public final void setIpcToken(int i) {
        this.mIpcToken = i;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.mAppIdentity, i);
        if (this.mDenyDomains != null) {
            parcel.writeInt(1);
            parcel.writeStringList(this.mDenyDomains);
        } else {
            parcel.writeInt(0);
        }
        if (this.mAllowDomains != null) {
            parcel.writeInt(1);
            parcel.writeStringList(this.mAllowDomains);
        } else {
            parcel.writeInt(0);
        }
        parcel.writeString(this.mDns1);
        parcel.writeString(this.mDns2);
        parcel.writeInt(this.mIpcToken);
    }

    public DomainFilterRule(AppIdentity appIdentity, List<String> list, List<String> list2) {
        this.mAppIdentity = appIdentity;
        if (list != null) {
            this.mDenyDomains = new ArrayList(list);
        } else {
            this.mDenyDomains = null;
        }
        if (list2 != null) {
            this.mAllowDomains = new ArrayList(list2);
        } else {
            this.mAllowDomains = null;
        }
    }

    public DomainFilterRule(AppIdentity appIdentity, List<String> list, List<String> list2, String str, String str2) {
        this.mAppIdentity = appIdentity;
        if (list != null) {
            this.mDenyDomains = new ArrayList(list);
        } else {
            this.mDenyDomains = null;
        }
        if (list2 != null) {
            this.mAllowDomains = new ArrayList(list2);
        } else {
            this.mAllowDomains = null;
        }
        this.mDns1 = str;
        this.mDns2 = str2;
    }

    public DomainFilterRule(AppIdentity appIdentity) {
        this.mAppIdentity = appIdentity;
        this.mDenyDomains = new ArrayList();
        this.mAllowDomains = new ArrayList();
        this.mDns1 = null;
        this.mDns2 = null;
    }

    public DomainFilterRule() {
        this.mAppIdentity = new AppIdentity();
        this.mDenyDomains = new ArrayList();
        this.mAllowDomains = new ArrayList();
        this.mDns1 = null;
        this.mDns2 = null;
    }

    private DomainFilterRule(Parcel parcel) {
        this();
        this.mAppIdentity = (AppIdentity) parcel.readParcelable(AppIdentity.class.getClassLoader());
        int readInt = parcel.readInt();
        this.mNullCheck = readInt;
        if (readInt == 1) {
            parcel.readStringList(this.mDenyDomains);
        } else {
            this.mDenyDomains = null;
        }
        int readInt2 = parcel.readInt();
        this.mNullCheck = readInt2;
        if (readInt2 == 1) {
            parcel.readStringList(this.mAllowDomains);
        } else {
            this.mAllowDomains = null;
        }
        this.mDns1 = parcel.readString();
        this.mDns2 = parcel.readString();
        this.mIpcToken = parcel.readInt();
    }
}
