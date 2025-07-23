package android.hardware.biometrics;

import android.graphics.Rect;
import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;

/* loaded from: classes2.dex */
public class SensorLocationInternal implements Parcelable {
    public final String displayId;
    public final int sensorLocationX;
    public final int sensorLocationY;
    public final int sensorRadius;
    public static final SensorLocationInternal DEFAULT = new SensorLocationInternal("", 0, 0, 0);
    public static final Parcelable.Creator<SensorLocationInternal> CREATOR = new Parcelable.Creator<SensorLocationInternal>() { // from class: android.hardware.biometrics.SensorLocationInternal.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SensorLocationInternal createFromParcel(Parcel parcel) {
            return new SensorLocationInternal(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SensorLocationInternal[] newArray(int i) {
            return new SensorLocationInternal[i];
        }
    };

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public SensorLocationInternal(String str, int i, int i2, int i3) {
        this.displayId = str == null ? "" : str;
        this.sensorLocationX = i;
        this.sensorLocationY = i2;
        this.sensorRadius = i3;
    }

    protected SensorLocationInternal(Parcel parcel) {
        this.displayId = parcel.readString16NoHelper();
        this.sensorLocationX = parcel.readInt();
        this.sensorLocationY = parcel.readInt();
        this.sensorRadius = parcel.readInt();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(this.displayId);
        parcel.writeInt(this.sensorLocationX);
        parcel.writeInt(this.sensorLocationY);
        parcel.writeInt(this.sensorRadius);
    }

    public String toString() {
        return "[id: " + this.displayId + ", x: " + this.sensorLocationX + ", y: " + this.sensorLocationY + ", r: " + this.sensorRadius + NavigationBarInflaterView.SIZE_MOD_END;
    }

    public Rect getRect() {
        int i = this.sensorLocationX;
        int i2 = this.sensorRadius;
        int i3 = this.sensorLocationY;
        return new Rect(i - i2, i3 - i2, i + i2, i3 + i2);
    }
}
