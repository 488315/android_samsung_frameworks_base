package com.samsung.android.knox.sdp.core;

import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.graphics.imagefilter.ShaderAssembler;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes6.dex */
public class SdpCreationParam implements Parcelable {
    public static final Parcelable.Creator<SdpCreationParam> CREATOR = new Parcelable.Creator<SdpCreationParam>() { // from class: com.samsung.android.knox.sdp.core.SdpCreationParam.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SdpCreationParam createFromParcel(Parcel parcel) {
            return new SdpCreationParam(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SdpCreationParam[] newArray(int i) {
            return new SdpCreationParam[i];
        }
    };
    private String mAlias;
    private int mFlags;
    private ArrayList<SdpDomain> mPrivilegedApps;

    private int validateFlags(int i) {
        if (i < 0 || i > 1) {
            return 0;
        }
        return i;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SdpCreationParam(String str, int i, ArrayList<SdpDomain> arrayList) {
        this.mFlags = 0;
        this.mAlias = str == null ? "" : str;
        this.mFlags = validateFlags(i);
        this.mPrivilegedApps = validatePrivilegedApps(arrayList);
    }

    public String getAlias() {
        return this.mAlias;
    }

    public int getFlags() {
        return this.mFlags;
    }

    public ArrayList<SdpDomain> getPrivilegedApps() {
        return this.mPrivilegedApps;
    }

    public String toString() {
        StringBuilder sb = new StringBuilder("\nSdpCreationParam { \n");
        sb.append("alias:" + this.mAlias);
        sb.append(ShaderAssembler.NEWLINE);
        Iterator<SdpDomain> it = this.mPrivilegedApps.iterator();
        while (it.hasNext()) {
            sb.append(it.next().toString());
            sb.append(ShaderAssembler.NEWLINE);
        }
        sb.append("\n}");
        return sb.toString();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.mAlias);
        parcel.writeInt(this.mFlags);
        parcel.writeSerializable(this.mPrivilegedApps);
    }

    private SdpCreationParam(Parcel parcel) {
        this.mFlags = 0;
        this.mAlias = parcel.readString();
        this.mFlags = parcel.readInt();
        this.mPrivilegedApps = (ArrayList) parcel.readSerializable();
    }

    private ArrayList<SdpDomain> validatePrivilegedApps(ArrayList<SdpDomain> arrayList) {
        ArrayList<SdpDomain> arrayList2 = new ArrayList<>();
        if (arrayList != null) {
            Iterator<SdpDomain> it = arrayList.iterator();
            while (it.hasNext()) {
                SdpDomain next = it.next();
                if (next.getPackageName() != null && !next.getPackageName().trim().isEmpty()) {
                    arrayList2.add(next);
                }
            }
        }
        return arrayList2;
    }
}
