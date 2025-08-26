package com.samsung.android.scs.ai.sdkcommon.asr;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* loaded from: classes4.dex */
public class DialogInfo implements Parcelable {
    public static final Parcelable.Creator<DialogInfo> CREATOR = new Parcelable.Creator<DialogInfo>() { // from class: com.samsung.android.scs.ai.sdkcommon.asr.DialogInfo.1
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
    private final List<Integer> speakerList;
    private final List<SpeechInfo> speechInfos;

    public DialogInfo() {
        this((Set<Integer>) Collections.EMPTY_SET);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static /* synthetic */ boolean lambda$getSpeechInfosById$0(int i, SpeechInfo speechInfo) {
        return speechInfo.getSpeaker() == i;
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public List<Integer> getSpeakerList() {
        return this.speakerList;
    }

    public List<SpeechInfo> getSpeechInfos() {
        return this.speechInfos;
    }

    public List<SpeechInfo> getSpeechInfosById(final int i) {
        return (List) this.speechInfos.stream().filter(new Predicate() { // from class: com.samsung.android.scs.ai.sdkcommon.asr.DialogInfo$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return DialogInfo.lambda$getSpeechInfosById$0(i, (SpeechInfo) obj);
            }
        }).collect(Collectors.toList());
    }

    public void setSpeechInfos(List<SpeechInfo> list) {
        this.speechInfos.clear();
        this.speechInfos.addAll(list);
    }

    public String toString() {
        return "DialogInfo{speakerList=" + this.speakerList + ", speechInfos=" + this.speechInfos + '}';
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeList(this.speakerList);
        parcel.writeList(this.speechInfos);
    }

    public DialogInfo(Set<Integer> set) {
        LinkedList linkedList = new LinkedList();
        this.speakerList = linkedList;
        this.speechInfos = new LinkedList();
        linkedList.addAll(set);
    }

    public DialogInfo(Parcel parcel) {
        LinkedList linkedList = new LinkedList();
        this.speakerList = linkedList;
        LinkedList linkedList2 = new LinkedList();
        this.speechInfos = linkedList2;
        parcel.readList(linkedList, Integer.class.getClassLoader());
        parcel.readList(linkedList2, SpeechInfo.class.getClassLoader());
    }
}
