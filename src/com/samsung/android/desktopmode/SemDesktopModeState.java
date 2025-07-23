package com.samsung.android.desktopmode;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes6.dex */
public class SemDesktopModeState implements Parcelable {
    public static final Parcelable.Creator<SemDesktopModeState> CREATOR = new Parcelable.Creator<SemDesktopModeState>() { // from class: com.samsung.android.desktopmode.SemDesktopModeState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemDesktopModeState createFromParcel(Parcel parcel) {
            return new SemDesktopModeState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SemDesktopModeState[] newArray(int i) {
            return new SemDesktopModeState[i];
        }
    };
    public static final int DISABLED = 2;
    public static final int DISABLING = 1;
    public static final int DISPLAY_TYPE_DUAL = 102;
    public static final int DISPLAY_TYPE_NONE = 0;
    public static final int DISPLAY_TYPE_STANDALONE = 101;
    public static final int ENABLED = 4;
    public static final int ENABLING = 3;
    public static final int STATE_BEFORE_CONFIG_CHANGE = 30;
    public static final int STATE_CONFIG_CHANGE_FINISHED = 50;
    public static final int STATE_CONFIG_CHANGE_STARTED = 40;
    public static final int STATE_LOADING_SCREEN_SHOWN = 20;
    public static final int STATE_UNDEFINED = 0;
    public static final int STATE_WELCOME_DIALOG_SHOWN = 10;
    private int displayType;
    public int enabled;
    public int state;

    @Retention(RetentionPolicy.SOURCE)
    public @interface DisplayType {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface Enabled {
    }

    @Retention(RetentionPolicy.SOURCE)
    public @interface State {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static String enabledToString(int i) {
        if (i == 1) {
            return "DISABLING";
        }
        if (i == 2) {
            return "DISABLED";
        }
        if (i == 3) {
            return "ENABLING";
        }
        if (i == 4) {
            return "ENABLED";
        }
        return "Unknown=" + i;
    }

    public static String stateToString(int i) {
        if (i == 0) {
            return "STATE_UNDEFINED";
        }
        if (i == 10) {
            return "STATE_WELCOME_DIALOG_SHOWN";
        }
        if (i == 20) {
            return "STATE_LOADING_SCREEN_SHOWN";
        }
        if (i == 30) {
            return "STATE_BEFORE_CONFIG_CHANGE";
        }
        if (i == 40) {
            return "STATE_CONFIG_CHANGE_STARTED";
        }
        if (i == 50) {
            return "STATE_CONFIG_CHANGE_FINISHED";
        }
        return "Unknown=" + i;
    }

    public static String displayTypeToString(int i) {
        if (i == 0) {
            return "DISPLAY_TYPE_NONE";
        }
        if (i == 101) {
            return "DISPLAY_TYPE_STANDALONE";
        }
        if (i == 102) {
            return "DISPLAY_TYPE_DUAL";
        }
        return "Unknown=" + i;
    }

    public SemDesktopModeState() {
        update(2, 0, 0);
    }

    public SemDesktopModeState(SemDesktopModeState semDesktopModeState) {
        update(semDesktopModeState.enabled, semDesktopModeState.state, semDesktopModeState.displayType);
    }

    public SemDesktopModeState(int i, int i2) {
        update(i, i2, (i == 2 && i2 == 0) ? 0 : 101);
    }

    public SemDesktopModeState(int i, int i2, int i3) {
        update(i, i2, i3);
    }

    public void update(int i, int i2, int i3) {
        this.enabled = i;
        this.state = i2;
        this.displayType = i3;
    }

    public int getEnabled() {
        return this.enabled;
    }

    public void setEnabled(int i) {
        if (i != 1 && i != 2 && i != 3 && i != 4) {
            throw new IllegalArgumentException("Unknown enabled=" + i);
        }
        this.enabled = i;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int i) {
        if (i != 0 && i != 10 && i != 20 && i != 30 && i != 40 && i != 50) {
            throw new IllegalArgumentException("Unknown state=" + i);
        }
        this.state = i;
    }

    public int getDisplayType() {
        return this.displayType;
    }

    public void setDisplayType(int i) {
        if (i != 0 && i != 101 && i != 102) {
            throw new IllegalArgumentException("Unknown displayType=" + i);
        }
        this.displayType = i;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        SemDesktopModeState semDesktopModeState = (SemDesktopModeState) obj;
        return compareTo(semDesktopModeState.enabled, semDesktopModeState.state, semDesktopModeState.displayType);
    }

    public int hashCode() {
        return Objects.hash(Integer.valueOf(this.enabled), Integer.valueOf(this.state), Integer.valueOf(this.displayType));
    }

    public boolean compareTo(int i, int i2) {
        return this.enabled == i && this.state == i2;
    }

    public boolean compareTo(int i, int i2, int i3) {
        return this.enabled == i && this.state == i2 && this.displayType == i3;
    }

    public String toString() {
        return "SemDesktopModeState(" + enabledToString(this.enabled) + ", " + stateToString(this.state) + ", " + displayTypeToString(this.displayType) + NavigationBarInflaterView.KEY_CODE_END;
    }

    public SemDesktopModeState(Parcel parcel) {
        readFromParcel(parcel);
    }

    private void readFromParcel(Parcel parcel) {
        this.enabled = parcel.readInt();
        this.state = parcel.readInt();
        this.displayType = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.enabled);
        parcel.writeInt(this.state);
        parcel.writeInt(this.displayType);
    }
}
