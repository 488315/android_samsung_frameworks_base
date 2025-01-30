package android.hardware.scontext;

import android.p009os.Bundle;
import android.p009os.Parcel;
import android.p009os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextShakeMotion extends SContextEventContext {
    public static final Parcelable.Creator<SContextShakeMotion> CREATOR = new Parcelable.Creator<SContextShakeMotion>() { // from class: android.hardware.scontext.SContextShakeMotion.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextShakeMotion createFromParcel(Parcel in) {
            return new SContextShakeMotion(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextShakeMotion[] newArray(int size) {
            return new SContextShakeMotion[size];
        }
    };
    private Bundle mContext;

    SContextShakeMotion() {
        this.mContext = new Bundle();
    }

    SContextShakeMotion(Parcel src) {
        readFromParcel(src);
    }

    public int getShakeStatus() {
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
