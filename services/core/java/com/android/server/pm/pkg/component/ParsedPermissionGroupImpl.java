package com.android.server.pm.pkg.component;

import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes3.dex */
public class ParsedPermissionGroupImpl extends ParsedComponentImpl
    implements ParsedPermissionGroup {
  public static final Parcelable.Creator CREATOR =
      new Parcelable
          .Creator() { // from class:
                       // com.android.server.pm.pkg.component.ParsedPermissionGroupImpl.1
        @Override // android.os.Parcelable.Creator
        public ParsedPermissionGroupImpl[] newArray(int i) {
          return new ParsedPermissionGroupImpl[i];
        }

        @Override // android.os.Parcelable.Creator
        public ParsedPermissionGroupImpl createFromParcel(Parcel parcel) {
          return new ParsedPermissionGroupImpl(parcel);
        }
      };
  public int backgroundRequestDetailRes;
  public int backgroundRequestRes;
  public int priority;
  public int requestDetailRes;
  public int requestRes;

  @Override // com.android.server.pm.pkg.component.ParsedComponentImpl, android.os.Parcelable
  public int describeContents() {
    return 0;
  }

  public String toString() {
    return "PermissionGroup{"
        + Integer.toHexString(System.identityHashCode(this))
        + " "
        + getName()
        + "}";
  }

  public ParsedPermissionGroupImpl() {}

  @Override // com.android.server.pm.pkg.component.ParsedComponentImpl, android.os.Parcelable
  public void writeToParcel(Parcel parcel, int i) {
    super.writeToParcel(parcel, i);
    parcel.writeInt(this.requestDetailRes);
    parcel.writeInt(this.backgroundRequestRes);
    parcel.writeInt(this.backgroundRequestDetailRes);
    parcel.writeInt(this.requestRes);
    parcel.writeInt(this.priority);
  }

  public ParsedPermissionGroupImpl(Parcel parcel) {
    super(parcel);
    this.requestDetailRes = parcel.readInt();
    this.backgroundRequestRes = parcel.readInt();
    this.backgroundRequestDetailRes = parcel.readInt();
    this.requestRes = parcel.readInt();
    this.priority = parcel.readInt();
  }

  @Override // com.android.server.pm.pkg.component.ParsedPermissionGroup
  public int getRequestDetailRes() {
    return this.requestDetailRes;
  }

  @Override // com.android.server.pm.pkg.component.ParsedPermissionGroup
  public int getBackgroundRequestRes() {
    return this.backgroundRequestRes;
  }

  @Override // com.android.server.pm.pkg.component.ParsedPermissionGroup
  public int getBackgroundRequestDetailRes() {
    return this.backgroundRequestDetailRes;
  }

  @Override // com.android.server.pm.pkg.component.ParsedPermissionGroup
  public int getRequestRes() {
    return this.requestRes;
  }

  @Override // com.android.server.pm.pkg.component.ParsedPermissionGroup
  public int getPriority() {
    return this.priority;
  }

  public ParsedPermissionGroupImpl setRequestDetailRes(int i) {
    this.requestDetailRes = i;
    return this;
  }

  public ParsedPermissionGroupImpl setBackgroundRequestRes(int i) {
    this.backgroundRequestRes = i;
    return this;
  }

  public ParsedPermissionGroupImpl setBackgroundRequestDetailRes(int i) {
    this.backgroundRequestDetailRes = i;
    return this;
  }

  public ParsedPermissionGroupImpl setRequestRes(int i) {
    this.requestRes = i;
    return this;
  }

  public ParsedPermissionGroupImpl setPriority(int i) {
    this.priority = i;
    return this;
  }
}
