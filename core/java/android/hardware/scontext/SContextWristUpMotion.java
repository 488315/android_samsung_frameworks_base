package android.hardware.scontext;

import android.p009os.Bundle;
import android.p009os.Parcel;
import android.p009os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextWristUpMotion extends SContextEventContext {
    public static final Parcelable.Creator<SContextWristUpMotion> CREATOR = new Parcelable.Creator<SContextWristUpMotion>() { // from class: android.hardware.scontext.SContextWristUpMotion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextWristUpMotion createFromParcel(Parcel in) {
            return new SContextWristUpMotion(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextWristUpMotion[] newArray(int size) {
            return new SContextWristUpMotion[size];
        }
    };
    private Bundle mContext;

    SContextWristUpMotion() {
        this.mContext = new Bundle();
    }

    SContextWristUpMotion(Parcel src) {
        readFromParcel(src);
    }

    public int getAction() {
        return this.mContext.getInt("Action");
    }

    @Override // android.hardware.scontext.SContextEventContext, com.samsung.android.hardware.context.SemContextEventContext
    public void setValues(Bundle context) {
        this.mContext = context;
    }

    @Override // com.samsung.android.hardware.context.SemContextEventContext, android.p009os.Parcelable
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeBundle(this.mContext);
    }

    private void readFromParcel(Parcel src) {
        this.mContext = src.readBundle();
    }
}
