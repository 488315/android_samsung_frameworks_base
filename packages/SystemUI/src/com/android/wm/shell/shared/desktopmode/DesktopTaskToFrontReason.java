package com.android.wm.shell.shared.desktopmode;

import android.os.Parcel;
import android.os.Parcelable;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes3.dex */
public final class DesktopTaskToFrontReason implements Parcelable {
    public static final /* synthetic */ DesktopTaskToFrontReason[] $VALUES;
    public static final DesktopTaskToFrontReason ALT_TAB;
    public static final Parcelable.Creator<DesktopTaskToFrontReason> CREATOR;
    public static final DesktopTaskToFrontReason TASKBAR_MANAGE_WINDOW;
    public static final DesktopTaskToFrontReason TASKBAR_TAP;
    public static final DesktopTaskToFrontReason UNKNOWN;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        DesktopTaskToFrontReason desktopTaskToFrontReason = new DesktopTaskToFrontReason("UNKNOWN", 0);
        UNKNOWN = desktopTaskToFrontReason;
        DesktopTaskToFrontReason desktopTaskToFrontReason2 = new DesktopTaskToFrontReason("TASKBAR_TAP", 1);
        TASKBAR_TAP = desktopTaskToFrontReason2;
        DesktopTaskToFrontReason desktopTaskToFrontReason3 = new DesktopTaskToFrontReason("ALT_TAB", 2);
        ALT_TAB = desktopTaskToFrontReason3;
        DesktopTaskToFrontReason desktopTaskToFrontReason4 = new DesktopTaskToFrontReason("TASKBAR_MANAGE_WINDOW", 3);
        TASKBAR_MANAGE_WINDOW = desktopTaskToFrontReason4;
        DesktopTaskToFrontReason[] desktopTaskToFrontReasonArr = {desktopTaskToFrontReason, desktopTaskToFrontReason2, desktopTaskToFrontReason3, desktopTaskToFrontReason4};
        $VALUES = desktopTaskToFrontReasonArr;
        EnumEntriesKt.enumEntries(desktopTaskToFrontReasonArr);
        new Companion(null);
        CREATOR = new Parcelable.Creator() { // from class: com.android.wm.shell.shared.desktopmode.DesktopTaskToFrontReason$Companion$CREATOR$1
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                DesktopTaskToFrontReason desktopTaskToFrontReasonValueOf;
                String string = parcel.readString();
                return (string == null || (desktopTaskToFrontReasonValueOf = DesktopTaskToFrontReason.valueOf(string)) == null) ? DesktopTaskToFrontReason.UNKNOWN : desktopTaskToFrontReasonValueOf;
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new DesktopTaskToFrontReason[i];
            }
        };
    }

    private DesktopTaskToFrontReason(String str, int i) {
    }

    public static DesktopTaskToFrontReason valueOf(String str) {
        return (DesktopTaskToFrontReason) Enum.valueOf(DesktopTaskToFrontReason.class, str);
    }

    public static DesktopTaskToFrontReason[] values() {
        return (DesktopTaskToFrontReason[]) $VALUES.clone();
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
