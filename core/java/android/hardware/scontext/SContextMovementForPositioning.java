package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextMovementForPositioning extends SContextEventContext {
    public static final Parcelable.Creator<SContextMovementForPositioning> CREATOR =
            new Parcelable.Creator<SContextMovementForPositioning>() { // from class:
                // android.hardware.scontext.SContextMovementForPositioning.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SContextMovementForPositioning createFromParcel(Parcel in) {
                    return new SContextMovementForPositioning(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SContextMovementForPositioning[] newArray(int size) {
                    return new SContextMovementForPositioning[size];
                }
            };
    private Bundle mContext;

    SContextMovementForPositioning() {
        this.mContext = new Bundle();
    }

    SContextMovementForPositioning(Parcel src) {
        readFromParcel(src);
    }

    public int getAlert() {
        return this.mContext.getInt("Alert");
    }

    @Override // android.hardware.scontext.SContextEventContext,
    // com.samsung.android.hardware.context.SemContextEventContext
    public void setValues(Bundle context) {
        this.mContext = context;
    }

    @Override // com.samsung.android.hardware.context.SemContextEventContext, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBundle(this.mContext);
    }

    private void readFromParcel(Parcel src) {
        this.mContext = src.readBundle();
    }
}
