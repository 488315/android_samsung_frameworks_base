package com.android.systemui.notetask;

import android.os.Parcel;
import android.os.Parcelable;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes2.dex */
public final class NoteTaskBubbleExpandBehavior implements Parcelable {
    public static final /* synthetic */ NoteTaskBubbleExpandBehavior[] $VALUES;
    public static final CREATOR CREATOR;
    public static final NoteTaskBubbleExpandBehavior DEFAULT;
    public static final NoteTaskBubbleExpandBehavior KEEP_IF_EXPANDED;

    public final class CREATOR implements Parcelable.Creator {
        public /* synthetic */ CREATOR(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        @Override // android.os.Parcelable.Creator
        public final Object createFromParcel(Parcel parcel) {
            String string;
            NoteTaskBubbleExpandBehavior noteTaskBubbleExpandBehaviorValueOf;
            return (parcel == null || (string = parcel.readString()) == null || (noteTaskBubbleExpandBehaviorValueOf = NoteTaskBubbleExpandBehavior.valueOf(string)) == null) ? NoteTaskBubbleExpandBehavior.DEFAULT : noteTaskBubbleExpandBehaviorValueOf;
        }

        @Override // android.os.Parcelable.Creator
        public final Object[] newArray(int i) {
            return new NoteTaskBubbleExpandBehavior[i];
        }

        private CREATOR() {
        }
    }

    static {
        NoteTaskBubbleExpandBehavior noteTaskBubbleExpandBehavior = new NoteTaskBubbleExpandBehavior("DEFAULT", 0);
        DEFAULT = noteTaskBubbleExpandBehavior;
        NoteTaskBubbleExpandBehavior noteTaskBubbleExpandBehavior2 = new NoteTaskBubbleExpandBehavior("KEEP_IF_EXPANDED", 1);
        KEEP_IF_EXPANDED = noteTaskBubbleExpandBehavior2;
        NoteTaskBubbleExpandBehavior[] noteTaskBubbleExpandBehaviorArr = {noteTaskBubbleExpandBehavior, noteTaskBubbleExpandBehavior2};
        $VALUES = noteTaskBubbleExpandBehaviorArr;
        EnumEntriesKt.enumEntries(noteTaskBubbleExpandBehaviorArr);
        CREATOR = new CREATOR(null);
    }

    private NoteTaskBubbleExpandBehavior(String str, int i) {
    }

    public static NoteTaskBubbleExpandBehavior valueOf(String str) {
        return (NoteTaskBubbleExpandBehavior) Enum.valueOf(NoteTaskBubbleExpandBehavior.class, str);
    }

    public static NoteTaskBubbleExpandBehavior[] values() {
        return (NoteTaskBubbleExpandBehavior[]) $VALUES.clone();
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name());
    }
}
