package com.android.wm.shell.shared.desktopmode;

import android.os.Parcel;
import android.os.Parcelable;
import kotlin.enums.EnumEntriesKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes3.dex */
public final class DesktopModeTransitionSource implements Parcelable {
    public static final /* synthetic */ DesktopModeTransitionSource[] $VALUES;
    public static final DesktopModeTransitionSource APP_FROM_OVERVIEW;
    public static final DesktopModeTransitionSource APP_HANDLE_MENU_BUTTON;
    public static final Parcelable.Creator<DesktopModeTransitionSource> CREATOR;
    public static final DesktopModeTransitionSource KEYBOARD_SHORTCUT;
    public static final DesktopModeTransitionSource TASK_DRAG;
    public static final DesktopModeTransitionSource UNKNOWN;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        DesktopModeTransitionSource desktopModeTransitionSource = new DesktopModeTransitionSource("TASK_DRAG", 0);
        TASK_DRAG = desktopModeTransitionSource;
        DesktopModeTransitionSource desktopModeTransitionSource2 = new DesktopModeTransitionSource("APP_FROM_OVERVIEW", 1);
        APP_FROM_OVERVIEW = desktopModeTransitionSource2;
        DesktopModeTransitionSource desktopModeTransitionSource3 = new DesktopModeTransitionSource("APP_HANDLE_MENU_BUTTON", 2);
        APP_HANDLE_MENU_BUTTON = desktopModeTransitionSource3;
        DesktopModeTransitionSource desktopModeTransitionSource4 = new DesktopModeTransitionSource("KEYBOARD_SHORTCUT", 3);
        KEYBOARD_SHORTCUT = desktopModeTransitionSource4;
        DesktopModeTransitionSource desktopModeTransitionSource5 = new DesktopModeTransitionSource("UNKNOWN", 4);
        UNKNOWN = desktopModeTransitionSource5;
        DesktopModeTransitionSource[] desktopModeTransitionSourceArr = {desktopModeTransitionSource, desktopModeTransitionSource2, desktopModeTransitionSource3, desktopModeTransitionSource4, desktopModeTransitionSource5};
        $VALUES = desktopModeTransitionSourceArr;
        EnumEntriesKt.enumEntries(desktopModeTransitionSourceArr);
        new Companion(null);
        CREATOR = new Parcelable.Creator() { // from class: com.android.wm.shell.shared.desktopmode.DesktopModeTransitionSource$Companion$CREATOR$1
            @Override // android.os.Parcelable.Creator
            public final Object createFromParcel(Parcel parcel) {
                DesktopModeTransitionSource desktopModeTransitionSourceValueOf;
                String string = parcel.readString();
                return (string == null || (desktopModeTransitionSourceValueOf = DesktopModeTransitionSource.valueOf(string)) == null) ? DesktopModeTransitionSource.UNKNOWN : desktopModeTransitionSourceValueOf;
            }

            @Override // android.os.Parcelable.Creator
            public final Object[] newArray(int i) {
                return new DesktopModeTransitionSource[i];
            }
        };
    }

    private DesktopModeTransitionSource(String str, int i) {
    }

    public static DesktopModeTransitionSource valueOf(String str) {
        return (DesktopModeTransitionSource) Enum.valueOf(DesktopModeTransitionSource.class, str);
    }

    public static DesktopModeTransitionSource[] values() {
        return (DesktopModeTransitionSource[]) $VALUES.clone();
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
