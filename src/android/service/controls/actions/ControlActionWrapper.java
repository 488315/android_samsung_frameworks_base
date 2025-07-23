package android.service.controls.actions;

import android.os.Parcel;
import android.os.Parcelable;
import com.android.internal.util.Preconditions;

/* loaded from: classes3.dex */
public final class ControlActionWrapper implements Parcelable {
    public static final Parcelable.Creator<ControlActionWrapper> CREATOR = new Parcelable.Creator<ControlActionWrapper>() { // from class: android.service.controls.actions.ControlActionWrapper.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ControlActionWrapper createFromParcel(Parcel parcel) {
            return new ControlActionWrapper(ControlAction.createActionFromBundle(parcel.readBundle()));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ControlActionWrapper[] newArray(int i) {
            return new ControlActionWrapper[i];
        }
    };
    private final ControlAction mControlAction;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ControlActionWrapper(ControlAction controlAction) {
        Preconditions.checkNotNull(controlAction);
        this.mControlAction = controlAction;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBundle(this.mControlAction.getDataBundle());
    }

    public ControlAction getWrappedAction() {
        return this.mControlAction;
    }
}
