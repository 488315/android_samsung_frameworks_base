package android.hardware.scontext;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

@Deprecated
/* loaded from: classes2.dex */
public class SContextActivityNotification extends SContextEventContext {
    public static final Parcelable.Creator<SContextActivityNotification> CREATOR = new Parcelable.Creator<SContextActivityNotification>() { // from class: android.hardware.scontext.SContextActivityNotification.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextActivityNotification createFromParcel(Parcel parcel) {
            return new SContextActivityNotification(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public SContextActivityNotification[] newArray(int i) {
            return new SContextActivityNotification[i];
        }
    };
    private Bundle mContext;

    SContextActivityNotification() {
        this.mContext = new Bundle();
    }

    SContextActivityNotification(Parcel parcel) {
        readFromParcel(parcel);
    }

    public long getTimeStamp() {
        return this.mContext.getLong("TimeStamp");
    }

    public int getStatus() {
        return this.mContext.getInt("ActivityType");
    }

    public int getAccuracy() {
        return this.mContext.getInt("Accuracy");
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
