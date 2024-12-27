package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextMovementAlert extends SContextEventContext {
    public static final Parcelable.Creator<SContextMovementAlert> CREATOR = new Parcelable.Creator<SContextMovementAlert>() { // from class: android.hardware.scontext.SContextMovementAlert.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextMovementAlert createFromParcel(Parcel in) {
            return new SContextMovementAlert(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextMovementAlert[] newArray(int size) {
            return new SContextMovementAlert[size];
        }
    };
    private Bundle mContext;

    SContextMovementAlert() {
        this.mContext = new Bundle();
    }

    SContextMovementAlert(Parcel src) {
        readFromParcel(src);
    }

    public int getAction() {
        return this.mContext.getInt("Action");
    }

    @Override // com.samsung.android.hardware.context.SemContextEventContext, android.os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBundle(this.mContext);
    }

    private void readFromParcel(Parcel src) {
        this.mContext = src.readBundle();
    }
}
