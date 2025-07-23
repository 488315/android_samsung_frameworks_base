package com.android.wm.shell.shared.bubbles;

import android.os.Parcel;
import android.os.Parcelable;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class BubbleBarLocation implements Parcelable {
    public static final /* synthetic */ BubbleBarLocation[] $VALUES;
    public static final Parcelable.Creator<BubbleBarLocation> CREATOR;
    public static final BubbleBarLocation DEFAULT;
    public static final BubbleBarLocation LEFT;
    public static final BubbleBarLocation RIGHT;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        BubbleBarLocation bubbleBarLocation = new BubbleBarLocation("DEFAULT", 0);
        DEFAULT = bubbleBarLocation;
        BubbleBarLocation bubbleBarLocation2 = new BubbleBarLocation("LEFT", 1);
        LEFT = bubbleBarLocation2;
        BubbleBarLocation bubbleBarLocation3 = new BubbleBarLocation("RIGHT", 2);
        RIGHT = bubbleBarLocation3;
        BubbleBarLocation[] bubbleBarLocationArr = {bubbleBarLocation, bubbleBarLocation2, bubbleBarLocation3};
        $VALUES = bubbleBarLocationArr;
        EnumEntriesKt.enumEntries(bubbleBarLocationArr);
        new Companion(null);
        CREATOR = new Parcelable.Creator() { // from class: com.android.wm.shell.shared.bubbles.BubbleBarLocation$Companion$CREATOR$1
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                BubbleBarLocation valueOf;
                String readString = parcel.readString();
                return (readString == null || (valueOf = BubbleBarLocation.valueOf(readString)) == null) ? BubbleBarLocation.DEFAULT : valueOf;
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new BubbleBarLocation[i];
            }
        };
    }

    private BubbleBarLocation(String str, int i) {
    }

    public static BubbleBarLocation valueOf(String str) {
        return (BubbleBarLocation) Enum.valueOf(BubbleBarLocation.class, str);
    }

    public static BubbleBarLocation[] values() {
        return (BubbleBarLocation[]) $VALUES.clone();
    }

    @Override // android.os.Parcelable
    public final int describeContents() {
        return 0;
    }

    public final boolean isOnLeft(boolean z) {
        return this == DEFAULT ? z : this == LEFT;
    }

    @Override // android.os.Parcelable
    public final void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(name());
    }
}
