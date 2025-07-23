package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextAirMotion extends SContextEventContext {
    public static final Parcelable.Creator<SContextAirMotion> CREATOR = new Parcelable.Creator<SContextAirMotion>() { // from class: android.hardware.scontext.SContextAirMotion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextAirMotion createFromParcel(Parcel parcel) {
            return new SContextAirMotion(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextAirMotion[] newArray(int i) {
            return new SContextAirMotion[i];
        }
    };
    private Bundle mContext;

    SContextAirMotion() {
        this.mContext = new Bundle();
    }

    SContextAirMotion(Parcel parcel) {
        readFromParcel(parcel);
    }

    public int getDirection() {
        return this.mContext.getInt("Direction");
    }

    public int getAngle() {
        return this.mContext.getInt("Angle");
    }

    public int getSpeed() {
        return this.mContext.getInt("Speed");
    }

    @Override // android.hardware.scontext.SContextEventContext, com.samsung.android.hardware.context.SemContextEventContext
    public void setValues(Bundle bundle) {
        this.mContext = bundle;
    }

    @Override // com.samsung.android.hardware.context.SemContextEventContext, android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeBundle(this.mContext);
    }

    private void readFromParcel(Parcel parcel) {
        this.mContext = parcel.readBundle();
    }
}
