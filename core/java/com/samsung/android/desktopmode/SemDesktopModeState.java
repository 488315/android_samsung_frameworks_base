package com.samsung.android.desktopmode;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.util.Objects;

/* loaded from: classes6.dex */
public class SemDesktopModeState implements Parcelable {
    public static final Parcelable.Creator<SemDesktopModeState> CREATOR =
            new Parcelable.Creator<
                    SemDesktopModeState>() { // from class:
                                             // com.samsung.android.desktopmode.SemDesktopModeState.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemDesktopModeState createFromParcel(Parcel parcel) {
                    return new SemDesktopModeState(parcel);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SemDesktopModeState[] newArray(int size) {
                    return new SemDesktopModeState[size];
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
    public @interface DisplayType {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface Enabled {}

    @Retention(RetentionPolicy.SOURCE)
    public @interface State {}

    public static String enabledToString(int enabled) {
        switch (enabled) {
            case 1:
                return "DISABLING";
            case 2:
                return "DISABLED";
            case 3:
                return "ENABLING";
            case 4:
                return "ENABLED";
            default:
                return "Unknown=" + enabled;
        }
    }

    public static String stateToString(int state) {
        switch (state) {
            case 0:
                return "STATE_UNDEFINED";
            case 10:
                return "STATE_WELCOME_DIALOG_SHOWN";
            case 20:
                return "STATE_LOADING_SCREEN_SHOWN";
            case 30:
                return "STATE_BEFORE_CONFIG_CHANGE";
            case 40:
                return "STATE_CONFIG_CHANGE_STARTED";
            case 50:
                return "STATE_CONFIG_CHANGE_FINISHED";
            default:
                return "Unknown=" + state;
        }
    }

    public static String displayTypeToString(int type) {
        switch (type) {
            case 0:
                return "DISPLAY_TYPE_NONE";
            case 101:
                return "DISPLAY_TYPE_STANDALONE";
            case 102:
                return "DISPLAY_TYPE_DUAL";
            default:
                return "Unknown=" + type;
        }
    }

    public SemDesktopModeState() {
        update(2, 0, 0);
    }

    public SemDesktopModeState(SemDesktopModeState desktopModeState) {
        update(desktopModeState.enabled, desktopModeState.state, desktopModeState.displayType);
    }

    public SemDesktopModeState(int enabled, int state) {
        update(enabled, state, (enabled == 2 && state == 0) ? 0 : 101);
    }

    public SemDesktopModeState(int enabled, int state, int displayType) {
        update(enabled, state, displayType);
    }

    public void update(int enabled, int state, int type) {
        this.enabled = enabled;
        this.state = state;
        this.displayType = type;
    }

    public int getEnabled() {
        return this.enabled;
    }

    public void setEnabled(int enabled) {
        if (enabled != 1 && enabled != 2 && enabled != 3 && enabled != 4) {
            throw new IllegalArgumentException("Unknown enabled=" + enabled);
        }
        this.enabled = enabled;
    }

    public int getState() {
        return this.state;
    }

    public void setState(int state) {
        if (state != 0 && state != 10 && state != 20 && state != 30 && state != 40 && state != 50) {
            throw new IllegalArgumentException("Unknown state=" + state);
        }
        this.state = state;
    }

    public int getDisplayType() {
        return this.displayType;
    }

    public void setDisplayType(int displayType) {
        if (displayType != 0 && displayType != 101 && displayType != 102) {
            throw new IllegalArgumentException("Unknown displayType=" + displayType);
        }
        this.displayType = displayType;
    }

    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        SemDesktopModeState state = (SemDesktopModeState) o;
        return compareTo(state.enabled, state.state, state.displayType);
    }

    public int hashCode() {
        return Objects.hash(
                Integer.valueOf(this.enabled),
                Integer.valueOf(this.state),
                Integer.valueOf(this.displayType));
    }

    public boolean compareTo(int enabled, int state) {
        return this.enabled == enabled && this.state == state;
    }

    public boolean compareTo(int enabled, int state, int type) {
        return this.enabled == enabled && this.state == state && this.displayType == type;
    }

    public String toString() {
        return "SemDesktopModeState("
                + enabledToString(this.enabled)
                + ", "
                + stateToString(this.state)
                + ", "
                + displayTypeToString(this.displayType)
                + NavigationBarInflaterView.KEY_CODE_END;
    }

    public SemDesktopModeState(Parcel src) {
        readFromParcel(src);
    }

    private void readFromParcel(Parcel src) {
        this.enabled = src.readInt();
        this.state = src.readInt();
        this.displayType = src.readInt();
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.enabled);
        dest.writeInt(this.state);
        dest.writeInt(this.displayType);
    }
}
