package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
public class SContextFlipCoverAction extends SContextEventContext {
    public static final Parcelable.Creator<SContextFlipCoverAction> CREATOR =
            new Parcelable.Creator<SContextFlipCoverAction>() { // from class:
                // android.hardware.scontext.SContextFlipCoverAction.1
                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SContextFlipCoverAction createFromParcel(Parcel in) {
                    return new SContextFlipCoverAction(in);
                }

                /* JADX WARN: Can't rename method to resolve collision */
                @Override // android.os.Parcelable.Creator
                public SContextFlipCoverAction[] newArray(int size) {
                    return new SContextFlipCoverAction[size];
                }
            };
    private Bundle mContext;

    SContextFlipCoverAction() {
        this.mContext = new Bundle();
    }

    SContextFlipCoverAction(Parcel src) {
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
