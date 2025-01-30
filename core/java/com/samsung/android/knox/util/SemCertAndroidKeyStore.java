package com.samsung.android.knox.util;

import android.os.Parcel;
import android.os.Parcelable;
import java.security.cert.Certificate;

/* loaded from: classes5.dex */
public class SemCertAndroidKeyStore implements Parcelable {
  public static final Parcelable.Creator<SemCertAndroidKeyStore> CREATOR =
      new Parcelable.Creator<
          SemCertAndroidKeyStore>() { // from class:
                                      // com.samsung.android.knox.util.SemCertAndroidKeyStore.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemCertAndroidKeyStore createFromParcel(Parcel source) {
          return new SemCertAndroidKeyStore(source);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemCertAndroidKeyStore[] newArray(int size) {
          return null;
        }
      };
  public Certificate[] certs;

  public SemCertAndroidKeyStore() {}

  public SemCertAndroidKeyStore(Parcel source) {
    this.certs = (Certificate[]) source.readSerializable();
  }

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }

  /* JADX WARN: Multi-variable type inference failed */
  /* JADX WARN: Type inference failed for: r0v0, types: [java.io.Serializable, java.security.cert.Certificate[]] */
  @Override // android.os.Parcelable
  public void writeToParcel(Parcel parcel, int flags) {
    parcel.writeSerializable(this.certs);
  }
}
