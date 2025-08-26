package com.samsung.android.sume.core.types.nn;

import android.net.Uri;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.Parcelable;
import java.io.IOException;

/* loaded from: classes6.dex */
public class NNFileDescriptor implements Parcelable, Cloneable {
    public static final Parcelable.Creator<NNFileDescriptor> CREATOR = new Parcelable.Creator<NNFileDescriptor>() { // from class: com.samsung.android.sume.core.types.nn.NNFileDescriptor.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NNFileDescriptor createFromParcel(Parcel parcel) {
            return new NNFileDescriptor(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public NNFileDescriptor[] newArray(int i) {
            return new NNFileDescriptor[i];
        }
    };
    private ParcelFileDescriptor fd;
    private long length;
    private String name;
    private long offset;
    private Uri pathUri;
    private String realPath;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public NNFileDescriptor(String str) {
        this.realPath = str;
    }

    public NNFileDescriptor(String str, ParcelFileDescriptor parcelFileDescriptor) {
        this.realPath = str;
        this.fd = parcelFileDescriptor;
    }

    public NNFileDescriptor(ParcelFileDescriptor parcelFileDescriptor) {
        this.fd = parcelFileDescriptor;
    }

    public NNFileDescriptor(Uri uri, String str, ParcelFileDescriptor parcelFileDescriptor) {
        this.pathUri = uri;
        this.realPath = str;
        this.fd = parcelFileDescriptor;
    }

    public NNFileDescriptor(Parcel parcel) {
        this.pathUri = (Uri) parcel.readParcelable(Uri.class.getClassLoader());
        this.realPath = parcel.readString();
        this.offset = parcel.readLong();
        this.length = parcel.readLong();
        this.fd = (ParcelFileDescriptor) parcel.readParcelable(ParcelFileDescriptor.class.getClassLoader());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeParcelable(this.pathUri, i);
        parcel.writeString(this.realPath);
        parcel.writeLong(this.offset);
        parcel.writeLong(this.length);
        parcel.writeParcelable(this.fd, i);
    }

    public NNFileDescriptor toNNFileDescriptor() throws CloneNotSupportedException {
        return (NNFileDescriptor) clone();
    }

    public Object clone() throws CloneNotSupportedException {
        Object objClone = super.clone();
        try {
            ParcelFileDescriptor parcelFileDescriptor = this.fd;
            if (parcelFileDescriptor != null) {
                ((NNFileDescriptor) objClone).fd = parcelFileDescriptor.dup();
                return objClone;
            }
        } catch (IOException e) {
            e.printStackTrace();
            ((NNFileDescriptor) objClone).fd = null;
        }
        return objClone;
    }

    public Uri getPathUri() {
        return this.pathUri;
    }

    public void setPathUri(Uri uri) {
        this.pathUri = uri;
    }

    public String getRealPath() {
        return this.realPath;
    }

    public void setRealPath(String str) {
        this.realPath = str;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String str) {
        this.name = str;
    }

    public long getOffset() {
        return this.offset;
    }

    public void setOffset(long j) {
        this.offset = j;
    }

    public long getLength() {
        return this.length;
    }

    public void setLength(long j) {
        this.length = j;
    }

    public ParcelFileDescriptor getFd() {
        return this.fd;
    }

    public void setFd(ParcelFileDescriptor parcelFileDescriptor) {
        this.fd = parcelFileDescriptor;
    }
}
