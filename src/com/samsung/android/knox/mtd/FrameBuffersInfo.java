package com.samsung.android.knox.mtd;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.List;

/* loaded from: classes6.dex */
public class FrameBuffersInfo implements Parcelable {
    public static final Parcelable.Creator<FrameBuffersInfo> CREATOR = new Parcelable.Creator<FrameBuffersInfo>() { // from class: com.samsung.android.knox.mtd.FrameBuffersInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrameBuffersInfo createFromParcel(Parcel parcel) {
            return new FrameBuffersInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FrameBuffersInfo[] newArray(int i) {
            return new FrameBuffersInfo[i];
        }
    };
    List<String> Content;
    List<String> URLs;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public List<String> getURLList() {
        return this.URLs;
    }

    public void setURLList(List<String> list) {
        this.URLs = list;
    }

    public List<String> getContentList() {
        return this.Content;
    }

    public void setContentList(List<String> list) {
        this.Content = list;
    }

    public FrameBuffersInfo(List<String> list, List<String> list2) {
        this.URLs = list;
        this.Content = list2;
    }

    public FrameBuffersInfo(Parcel parcel) {
        this.URLs = parcel.readArrayList(List.class.getClassLoader());
        this.Content = parcel.readArrayList(List.class.getClassLoader());
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(this.URLs);
        parcel.writeList(this.Content);
    }
}
