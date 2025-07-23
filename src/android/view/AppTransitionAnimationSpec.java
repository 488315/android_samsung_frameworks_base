package android.view;

import android.graphics.Rect;
import android.hardware.HardwareBuffer;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes4.dex */
public class AppTransitionAnimationSpec implements Parcelable {
    public static final Parcelable.Creator<AppTransitionAnimationSpec> CREATOR = new Parcelable.Creator<AppTransitionAnimationSpec>() { // from class: android.view.AppTransitionAnimationSpec.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AppTransitionAnimationSpec createFromParcel(Parcel parcel) {
            return new AppTransitionAnimationSpec(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AppTransitionAnimationSpec[] newArray(int i) {
            return new AppTransitionAnimationSpec[i];
        }
    };
    public final HardwareBuffer buffer;
    public final Rect rect;
    public final int taskId;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AppTransitionAnimationSpec(int i, HardwareBuffer hardwareBuffer, Rect rect) {
        this.taskId = i;
        this.rect = rect;
        this.buffer = hardwareBuffer;
    }

    public AppTransitionAnimationSpec(Parcel parcel) {
        this.taskId = parcel.readInt();
        this.rect = (Rect) parcel.readTypedObject(Rect.CREATOR);
        this.buffer = (HardwareBuffer) parcel.readTypedObject(HardwareBuffer.CREATOR);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.taskId);
        parcel.writeTypedObject(this.rect, 0);
        parcel.writeTypedObject(this.buffer, 0);
    }

    public String toString() {
        return "{taskId: " + this.taskId + ", buffer: " + this.buffer + ", rect: " + this.rect + "}";
    }
}
