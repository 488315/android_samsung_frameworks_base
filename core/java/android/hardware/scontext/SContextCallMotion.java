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
        public SContextCallMotion createFromParcel(Parcel in) {
            return new SContextCallMotion(in);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextCallMotion[] newArray(int size) {
            return new SContextCallMotion[size];
        }
    };
    private Bundle mContext;

    SContextCallMotion() {
        this.mContext = new Bundle();
    }

    SContextCallMotion(Parcel src) {
        readFromParcel(src);
    }

    public int getAction() {
        return this.mContext.getInt("Action");
    }

    @Override // android.hardware.scontext.SContextEventContext, com.samsung.android.hardware.context.SemContextEventContext
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
