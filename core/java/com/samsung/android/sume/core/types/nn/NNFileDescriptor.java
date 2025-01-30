package com.samsung.android.sume.core.types.nn;

import android.net.Uri;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import java.io.IOException;

/* loaded from: classes4.dex */
public class NNFileDescriptor implements Parcelable, Cloneable {
  public static final Parcelable.Creator<NNFileDescriptor> CREATOR =
      new Parcelable.Creator<
          NNFileDescriptor>() { // from class:
                                // com.samsung.android.sume.core.types.nn.NNFileDescriptor.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NNFileDescriptor createFromParcel(Parcel in) {
          return new NNFileDescriptor(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NNFileDescriptor[] newArray(int size) {
          return new NNFileDescriptor[size];
        }
      };

  /* renamed from: fd */
  private ParcelFileDescriptor f3070fd;
  private long length;
  private String name;
  private long offset;
  private Uri pathUri;
  private String realPath;

  public NNFileDescriptor(String path) {
    this.realPath = path;
  }

  public NNFileDescriptor(String path, ParcelFileDescriptor fd) {
    this.realPath = path;
    this.f3070fd = fd;
  }

  public NNFileDescriptor(ParcelFileDescriptor fd) {
    this.f3070fd = fd;
  }

  public NNFileDescriptor(Uri uri, String path, ParcelFileDescriptor fd) {
    this.pathUri = uri;
    this.realPath = path;
    this.f3070fd = fd;
  }

  public NNFileDescriptor(Parcel in) {
    this.pathUri = (Uri) in.readParcelable(Uri.class.getClassLoader());
    this.realPath = in.readString();
    this.offset = in.readLong();
    this.length = in.readLong();
    this.f3070fd =
        (ParcelFileDescriptor) in.readParcelable(ParcelFileDescriptor.class.getClassLoader());
  }

  @Override // android.os.Parcelable
  public int describeContents() {
    return 0;
  }

  @Override // android.os.Parcelable
  public void writeToParcel(Parcel dest, int flags) {
    dest.writeParcelable(this.pathUri, flags);
    dest.writeString(this.realPath);
    dest.writeLong(this.offset);
    dest.writeLong(this.length);
    dest.writeParcelable(this.f3070fd, flags);
  }

  public NNFileDescriptor toNNFileDescriptor() throws CloneNotSupportedException {
    return (NNFileDescriptor) clone();
  }

  public Object clone() throws CloneNotSupportedException {
    Object obj = super.clone();
    try {
      ParcelFileDescriptor parcelFileDescriptor = this.f3070fd;
      if (parcelFileDescriptor != null) {
        ((NNFileDescriptor) obj).f3070fd = parcelFileDescriptor.dup();
      }
    } catch (IOException e) {
      e.printStackTrace();
      ((NNFileDescriptor) obj).f3070fd = null;
    }
    return obj;
  }

  public Uri getPathUri() {
    return this.pathUri;
  }

  public void setPathUri(Uri pathUri) {
    this.pathUri = pathUri;
  }

  public String getRealPath() {
    return this.realPath;
  }

  public void setRealPath(String realPath) {
    this.realPath = realPath;
  }

  public String getName() {
    return this.name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public long getOffset() {
    return this.offset;
  }

  public void setOffset(long offset) {
    this.offset = offset;
  }

  public long getLength() {
    return this.length;
  }

  public void setLength(long length) {
    this.length = length;
  }

  public ParcelFileDescriptor getFd() {
    return this.f3070fd;
  }

  public void setFd(ParcelFileDescriptor fd) {
    this.f3070fd = fd;
  }
}
