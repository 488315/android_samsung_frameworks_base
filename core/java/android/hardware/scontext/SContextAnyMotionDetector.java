package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
public class SContextAnyMotionDetector extends SContextEventContext {
    public static final Parcelable.Creator<SContextAnyMotionDetector> CREATOR =
            new Parcelable.Creator<SContextAnyMotionDetector>() { // from class:
                // android.hardware.scontext.SContextAnyMotionDetector.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SContextAnyMotionDetector createFromParcel(Parcel in) {
                    return new SContextAnyMotionDetector(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SContextAnyMotionDetector[] newArray(int size) {
                    return new SContextAnyMotionDetector[size];
                }
            };
    private Bundle mContext;

    SContextAnyMotionDetector() {
        this.mContext = new Bundle();
    }

    SContextAnyMotionDetector(Parcel src) {
        readFromParcel(src);
    }

    public int getAction() {
        return this.mContext.getInt("Action");
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
