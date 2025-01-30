package com.samsung.android.sivs.ai.sdkcommon.asr;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public class DialogInfo implements Parcelable {
    public static final Parcelable.Creator<DialogInfo> CREATOR = new Parcelable.Creator<DialogInfo>() { // from class: com.samsung.android.sivs.ai.sdkcommon.asr.DialogInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DialogInfo createFromParcel(Parcel parcel) {
            return new DialogInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DialogInfo[] newArray(int i) {
            return new DialogInfo[i];
        }
    };
    private final List<SpeechInfo> speakerInfos;
    private final List<Integer> speakerList;

    public DialogInfo() {
        this((Set<Integer>) Collections.emptySet());
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public List<SpeechInfo> getSpeakerInfos() {
        return this.speakerInfos;
    }

    public List<Integer> getSpeakerList() {
        return this.speakerList;
    }

    public void setSpeechInfos(List<SpeechInfo> list) {
        this.speakerInfos.clear();
        this.speakerInfos.addAll(list);
    }

    public String toString() {
        return "DialogInfo{speakerList=" + this.speakerList + ", speakerInfos=" + this.speakerInfos + '}';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(this.speakerList);
        parcel.writeList(this.speakerInfos);
    }

    public DialogInfo(Set<Integer> set) {
        LinkedList linkedList = new LinkedList();
        this.speakerList = linkedList;
        this.speakerInfos = new LinkedList();
        linkedList.addAll(set);
    }

    public DialogInfo(Parcel parcel) {
        LinkedList linkedList = new LinkedList();
        this.speakerList = linkedList;
        LinkedList linkedList2 = new LinkedList();
        this.speakerInfos = linkedList2;
        parcel.readList(linkedList, Integer.class.getClassLoader());
        parcel.readList(linkedList2, SpeechInfo.class.getClassLoader());
    }
}
