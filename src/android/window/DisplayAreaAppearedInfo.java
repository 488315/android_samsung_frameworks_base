package android.window;

import android.os.Parcel;
import android.os.Parcelable;
import android.view.SurfaceControl;

/* loaded from: classes5.dex */
public final class DisplayAreaAppearedInfo implements Parcelable {
    public static final Parcelable.Creator<DisplayAreaAppearedInfo> CREATOR = new Parcelable.Creator<DisplayAreaAppearedInfo>() { // from class: android.window.DisplayAreaAppearedInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DisplayAreaAppearedInfo createFromParcel(Parcel parcel) {
            return new DisplayAreaAppearedInfo((DisplayAreaInfo) parcel.readTypedObject(DisplayAreaInfo.CREATOR), (SurfaceControl) parcel.readTypedObject(SurfaceControl.CREATOR));
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DisplayAreaAppearedInfo[] newArray(int i) {
            return new DisplayAreaAppearedInfo[i];
        }
    };
    private final DisplayAreaInfo mDisplayAreaInfo;
    private final SurfaceControl mLeash;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public DisplayAreaAppearedInfo(DisplayAreaInfo displayAreaInfo, SurfaceControl surfaceControl) {
        this.mDisplayAreaInfo = displayAreaInfo;
        this.mLeash = surfaceControl;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeTypedObject(this.mDisplayAreaInfo, i);
        parcel.writeTypedObject(this.mLeash, i);
    }

    public DisplayAreaInfo getDisplayAreaInfo() {
        return this.mDisplayAreaInfo;
    }

    public SurfaceControl getLeash() {
        return this.mLeash;
    }
}
