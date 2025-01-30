package android.window;

import android.p009os.Parcel;
import android.p009os.Parcelable;
import android.view.SurfaceControl;

/* loaded from: classes4.dex */
public final class DisplayAreaAppearedInfo implements Parcelable {
    public static final Parcelable.Creator<DisplayAreaAppearedInfo> CREATOR = new Parcelable.Creator<DisplayAreaAppearedInfo>() { // from class: android.window.DisplayAreaAppearedInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DisplayAreaAppearedInfo createFromParcel(Parcel source) {
            DisplayAreaInfo displayAreaInfo = (DisplayAreaInfo) source.readTypedObject(DisplayAreaInfo.CREATOR);
            SurfaceControl leash = (SurfaceControl) source.readTypedObject(SurfaceControl.CREATOR);
            return new DisplayAreaAppearedInfo(displayAreaInfo, leash);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DisplayAreaAppearedInfo[] newArray(int size) {
            return new DisplayAreaAppearedInfo[size];
        }
    };
    private final DisplayAreaInfo mDisplayAreaInfo;
    private final SurfaceControl mLeash;

    public DisplayAreaAppearedInfo(DisplayAreaInfo displayAreaInfo, SurfaceControl leash) {
        this.mDisplayAreaInfo = displayAreaInfo;
        this.mLeash = leash;
    }

    @Override // android.p009os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedObject(this.mDisplayAreaInfo, flags);
        dest.writeTypedObject(this.mLeash, flags);
    }

    @Override // android.p009os.Parcelable
    public int describeContents() {
        return 0;
    }

    public DisplayAreaInfo getDisplayAreaInfo() {
        return this.mDisplayAreaInfo;
    }

    public SurfaceControl getLeash() {
        return this.mLeash;
    }
}
