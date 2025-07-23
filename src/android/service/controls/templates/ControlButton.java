package android.service.controls.templates;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;

/* loaded from: classes3.dex */
public final class ControlButton implements Parcelable {
    public static final Parcelable.Creator<ControlButton> CREATOR = new Parcelable.Creator<ControlButton>() { // from class: android.service.controls.templates.ControlButton.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ControlButton createFromParcel(Parcel parcel) {
            return new ControlButton(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ControlButton[] newArray(int i) {
            return new ControlButton[i];
        }
    };
    private final CharSequence mActionDescription;
    private final boolean mChecked;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ControlButton(boolean z, CharSequence charSequence) {
        Preconditions.checkNotNull(charSequence);
        this.mChecked = z;
        this.mActionDescription = charSequence;
    }

    public boolean isChecked() {
        return this.mChecked;
    }

    public CharSequence getActionDescription() {
        return this.mActionDescription;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeByte(this.mChecked ? (byte) 1 : (byte) 0);
        parcel.writeCharSequence(this.mActionDescription);
    }

    ControlButton(Parcel parcel) {
        this.mChecked = parcel.readByte() != 0;
        this.mActionDescription = parcel.readCharSequence();
    }
}
