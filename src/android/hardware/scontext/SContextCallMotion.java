package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextCallMotion extends SContextEventContext {
    public static final Parcelable.Creator<SContextCallMotion> CREATOR = new Parcelable.Creator<SContextCallMotion>() { // from class: android.hardware.scontext.SContextCallMotion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextCallMotion createFromParcel(Parcel parcel) {
            return new SContextCallMotion(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextCallMotion[] newArray(int i) {
            return new SContextCallMotion[i];
        }
    };
    private Bundle mContext;

    SContextCallMotion() {
        this.mContext = new Bundle();
    }

    SContextCallMotion(Parcel parcel) {
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
