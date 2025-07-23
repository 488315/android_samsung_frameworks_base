package android.window;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes5.dex */
public final class KeyguardState implements Parcelable {
    public static final Parcelable.Creator<KeyguardState> CREATOR = new Parcelable.Creator<KeyguardState>() { // from class: android.window.KeyguardState.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyguardState createFromParcel(Parcel parcel) {
            return new KeyguardState(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public KeyguardState[] newArray(int i) {
            return new KeyguardState[i];
        }
    };
    private final boolean mAodShowing;
    private final boolean mKeyguardShowing;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private KeyguardState(boolean z, boolean z2) {
        this.mKeyguardShowing = z;
        this.mAodShowing = z2;
    }

    private KeyguardState(Parcel parcel) {
        this.mKeyguardShowing = parcel.readBoolean();
        this.mAodShowing = parcel.readBoolean();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBoolean(this.mKeyguardShowing);
        parcel.writeBoolean(this.mAodShowing);
    }

    public boolean getKeyguardShowing() {
        return this.mKeyguardShowing;
    }

    public boolean getAodShowing() {
        return this.mAodShowing;
    }

    public String toString() {
        return "KeyguardState{ keyguardShowing=" + this.mKeyguardShowing + ", aodShowing=" + this.mAodShowing + '}';
    }

    public int hashCode() {
        return Objects.hash(Boolean.valueOf(this.mKeyguardShowing), Boolean.valueOf(this.mAodShowing));
    }

    public boolean equals(Object obj) {
        if (obj instanceof KeyguardState) {
            KeyguardState keyguardState = (KeyguardState) obj;
            if (this.mKeyguardShowing == keyguardState.mKeyguardShowing && this.mAodShowing == keyguardState.mAodShowing) {
                return true;
            }
        }
        return false;
    }

    public static final class Builder {
        private boolean mAodShowing;
        private boolean mKeyguardShowing;

        public Builder setKeyguardShowing(boolean z) {
            this.mKeyguardShowing = z;
            return this;
        }

        public Builder setAodShowing(boolean z) {
            this.mAodShowing = z;
            return this;
        }

        public KeyguardState build() {
            return new KeyguardState(this.mKeyguardShowing, this.mAodShowing);
        }
    }
}
