package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextBounceShortMotion extends SContextEventContext {
    public static final Parcelable.Creator<SContextBounceShortMotion> CREATOR = new Parcelable.Creator<SContextBounceShortMotion>() { // from class: android.hardware.scontext.SContextBounceShortMotion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextBounceShortMotion createFromParcel(Parcel parcel) {
            return new SContextBounceShortMotion(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextBounceShortMotion[] newArray(int i) {
            return new SContextBounceShortMotion[i];
        }
    };
    private Bundle mContext;

    SContextBounceShortMotion() {
        this.mContext = new Bundle();
    }

    SContextBounceShortMotion(Parcel parcel) {
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
