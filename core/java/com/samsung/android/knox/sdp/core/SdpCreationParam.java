package com.samsung.android.knox.sdp.core;

import android.p009os.Parcel;
import android.p009os.Parcelable;
import java.util.ArrayList;
import java.util.Iterator;

/* loaded from: classes5.dex */
public class SdpCreationParam implements Parcelable {
  public static final Parcelable.Creator<SdpCreationParam> CREATOR =
      new Parcelable.Creator<
          SdpCreationParam>() { // from class: com.samsung.android.knox.sdp.core.SdpCreationParam.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SdpCreationParam createFromParcel(Parcel source) {
          return new SdpCreationParam(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SdpCreationParam[] newArray(int size) {
          return new SdpCreationParam[size];
        }
      };
  private String mAlias;
  private int mFlags;
  private ArrayList<SdpDomain> mPrivilegedApps;

  public SdpCreationParam(String alias, int flags, ArrayList<SdpDomain> privilegedApps) {
    this.mFlags = 0;
    this.mAlias = alias == null ? "" : alias;
    this.mFlags = validateFlags(flags);
    this.mPrivilegedApps = validatePrivilegedApps(privilegedApps);
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
    StringBuilder sb = new StringBuilder();
    sb.append("\nSdpCreationParam { ");
    sb.append("\n");
    sb.append("alias:" + this.mAlias);
    sb.append("\n");
    Iterator<SdpDomain> it = this.mPrivilegedApps.iterator();
    while (it.hasNext()) {
      SdpDomain element = it.next();
      sb.append(element.toString());
      sb.append("\n");
    }
    sb.append("\n}");
    return sb.toString();
  }

  @Override // android.p009os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.p009os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeString(this.mAlias);
    dest.writeInt(this.mFlags);
    dest.writeSerializable(this.mPrivilegedApps);
  }

  private SdpCreationParam(Parcel source) {
    this.mFlags = 0;
    this.mAlias = source.readString();
    this.mFlags = source.readInt();
    this.mPrivilegedApps = (ArrayList) source.readSerializable();
  }

  private int validateFlags(int flags) {
    if (flags < 0 || flags > 1) {
      return 0;
    }
    return flags;
  }

  private ArrayList<SdpDomain> validatePrivilegedApps(ArrayList<SdpDomain> privilegedApps) {
    ArrayList<SdpDomain> ret = new ArrayList<>();
    if (privilegedApps != null) {
      Iterator<SdpDomain> it = privilegedApps.iterator();
      while (it.hasNext()) {
        SdpDomain domain = it.next();
        if (domain.getPackageName() != null && !domain.getPackageName().trim().isEmpty()) {
          ret.add(domain);
        }
      }
    }
    return ret;
  }
}
