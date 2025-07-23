package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextBounceLongMotion extends SContextEventContext {
    public static final Parcelable.Creator<SContextBounceLongMotion> CREATOR = new Parcelable.Creator<SContextBounceLongMotion>() { // from class: android.hardware.scontext.SContextBounceLongMotion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextBounceLongMotion createFromParcel(Parcel parcel) {
            return new SContextBounceLongMotion(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextBounceLongMotion[] newArray(int i) {
            return new SContextBounceLongMotion[i];
        }
    };
    private Bundle mContext;

    SContextBounceLongMotion() {
        this.mContext = new Bundle();
    }

    SContextBounceLongMotion(Parcel parcel) {
        readFromParcel(parcel);
    }

    public int getAction() {
        return this.mContext.getInt("Action");
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
