package com.samsung.android.knox.mtd;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/* loaded from: classes6.dex */
public class FrameBuffersInfo implements Parcelable {
    public static final Parcelable.Creator<FrameBuffersInfo> CREATOR =
            new Parcelable.Creator<
                    FrameBuffersInfo>() { // from class:
                                          // com.samsung.android.knox.mtd.FrameBuffersInfo.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public FrameBuffersInfo createFromParcel(Parcel in) {
                    return new FrameBuffersInfo(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public FrameBuffersInfo[] newArray(int size) {
                    return new FrameBuffersInfo[size];
                }
            };
    List<String> Content;
    List<String> URLs;

    public List<String> getURLList() {
        return this.URLs;
    }

    public void setURLList(List<String> URLs) {
        this.URLs = URLs;
    }

    public List<String> getContentList() {
        return this.Content;
    }

    public void setContentList(List<String> Content) {
        this.Content = Content;
    }

    public FrameBuffersInfo(List<String> URLs, List<String> Content) {
        this.URLs = URLs;
        this.Content = Content;
    }

    public FrameBuffersInfo(Parcel in) {
        this.URLs = in.readArrayList(List.class.getClassLoader());
        this.Content = in.readArrayList(List.class.getClassLoader());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeList(this.URLs);
        dest.writeList(this.Content);
    }
}
