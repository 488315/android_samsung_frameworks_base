package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextPutDownMotion extends SContextEventContext {
    public static final Parcelable.Creator<SContextPutDownMotion> CREATOR = new Parcelable.Creator<SContextPutDownMotion>() { // from class: android.hardware.scontext.SContextPutDownMotion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextPutDownMotion createFromParcel(Parcel parcel) {
            return new SContextPutDownMotion(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextPutDownMotion[] newArray(int i) {
            return new SContextPutDownMotion[i];
        }
    };
    private Bundle mContext;

    SContextPutDownMotion() {
        this.mContext = new Bundle();
    }

    SContextPutDownMotion(Parcel parcel) {
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
