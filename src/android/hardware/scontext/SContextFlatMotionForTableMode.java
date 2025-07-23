package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextFlatMotionForTableMode extends SContextEventContext {
    public static final Parcelable.Creator<SContextFlatMotionForTableMode> CREATOR = new Parcelable.Creator<SContextFlatMotionForTableMode>() { // from class: android.hardware.scontext.SContextFlatMotionForTableMode.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextFlatMotionForTableMode createFromParcel(Parcel parcel) {
            return new SContextFlatMotionForTableMode(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextFlatMotionForTableMode[] newArray(int i) {
            return new SContextFlatMotionForTableMode[i];
        }
    };
    private Bundle mContext;

    SContextFlatMotionForTableMode() {
        this.mContext = new Bundle();
    }

    SContextFlatMotionForTableMode(Parcel parcel) {
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
