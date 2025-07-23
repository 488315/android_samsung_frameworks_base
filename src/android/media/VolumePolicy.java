package android.media;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Objects;

/* loaded from: classes2.dex */
public final class VolumePolicy implements Parcelable {
    public static final int A11Y_MODE_INDEPENDENT_A11Y_VOLUME = 1;
    public static final int A11Y_MODE_MEDIA_A11Y_VOLUME = 0;
    public final boolean doNotDisturbWhenSilent;
    public final int vibrateToSilentDebounce;
    public final boolean volumeDownToEnterSilent;
    public final boolean volumeUpToExitSilent;
    public static final VolumePolicy DEFAULT = new VolumePolicy(false, false, false, 400);
    public static final Parcelable.Creator<VolumePolicy> CREATOR = new Parcelable.Creator<VolumePolicy>() { // from class: android.media.VolumePolicy.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VolumePolicy createFromParcel(Parcel parcel) {
            return new VolumePolicy(parcel.readInt() != 0, parcel.readInt() != 0, parcel.readInt() != 0, parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public VolumePolicy[] newArray(int i) {
            return new VolumePolicy[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public VolumePolicy(boolean z, boolean z2, boolean z3, int i) {
        this.volumeDownToEnterSilent = z;
        this.volumeUpToExitSilent = z2;
        this.doNotDisturbWhenSilent = z3;
        this.vibrateToSilentDebounce = i;
    }

    public String toString() {
        return "VolumePolicy[volumeDownToEnterSilent=" + this.volumeDownToEnterSilent + ",volumeUpToExitSilent=" + this.volumeUpToExitSilent + ",doNotDisturbWhenSilent=" + this.doNotDisturbWhenSilent + ",vibrateToSilentDebounce=" + this.vibrateToSilentDebounce + NavigationBarInflaterView.SIZE_MOD_END;
    }

    public int hashCode() {
        return Objects.hash(Boolean.valueOf(this.volumeDownToEnterSilent), Boolean.valueOf(this.volumeUpToExitSilent), Boolean.valueOf(this.doNotDisturbWhenSilent), Integer.valueOf(this.vibrateToSilentDebounce));
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof VolumePolicy)) {
            return false;
        }
        if (obj == this) {
            return true;
        }
        VolumePolicy volumePolicy = (VolumePolicy) obj;
        return volumePolicy.volumeDownToEnterSilent == this.volumeDownToEnterSilent && volumePolicy.volumeUpToExitSilent == this.volumeUpToExitSilent && volumePolicy.doNotDisturbWhenSilent == this.doNotDisturbWhenSilent && volumePolicy.vibrateToSilentDebounce == this.vibrateToSilentDebounce;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.volumeDownToEnterSilent ? 1 : 0);
        parcel.writeInt(this.volumeUpToExitSilent ? 1 : 0);
        parcel.writeInt(this.doNotDisturbWhenSilent ? 1 : 0);
        parcel.writeInt(this.vibrateToSilentDebounce);
    }
}
