package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextAnyMotionDetector extends SContextEventContext {
    public static final Parcelable.Creator<SContextAnyMotionDetector> CREATOR = new Parcelable.Creator<SContextAnyMotionDetector>() { // from class: android.hardware.scontext.SContextAnyMotionDetector.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextAnyMotionDetector createFromParcel(Parcel parcel) {
            return new SContextAnyMotionDetector(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextAnyMotionDetector[] newArray(int i) {
            return new SContextAnyMotionDetector[i];
        }
    };
    private Bundle mContext;

    SContextAnyMotionDetector() {
        this.mContext = new Bundle();
    }

    SContextAnyMotionDetector(Parcel parcel) {
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
