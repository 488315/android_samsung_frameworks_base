package android.text.style;

import android.app.PendingIntent;
import android.os.Parcel;
import android.text.ParcelableSpan;

/* loaded from: classes4.dex */
public class EasyEditSpan implements ParcelableSpan {
    public static final String EXTRA_TEXT_CHANGED_TYPE = "android.text.style.EXTRA_TEXT_CHANGED_TYPE";
    public static final int TEXT_DELETED = 1;
    public static final int TEXT_MODIFIED = 2;
    private boolean mDeleteEnabled;
    private final PendingIntent mPendingIntent;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeIdInternal() {
        return 22;
    }

    public EasyEditSpan() {
        this.mPendingIntent = null;
        this.mDeleteEnabled = true;
    }

    public EasyEditSpan(PendingIntent pendingIntent) {
        this.mPendingIntent = pendingIntent;
        this.mDeleteEnabled = true;
    }

    public EasyEditSpan(Parcel parcel) {
        this.mPendingIntent = (PendingIntent) parcel.readParcelable(null, PendingIntent.class);
        this.mDeleteEnabled = parcel.readByte() == 1;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        writeToParcelInternal(parcel, i);
    }

    @Override // android.text.ParcelableSpan
    public void writeToParcelInternal(Parcel parcel, int i) {
        parcel.writeParcelable(this.mPendingIntent, 0);
        parcel.writeByte(this.mDeleteEnabled ? (byte) 1 : (byte) 0);
    }

    @Override // android.text.ParcelableSpan
    public int getSpanTypeId() {
        return getSpanTypeIdInternal();
    }

    public boolean isDeleteEnabled() {
        return this.mDeleteEnabled;
    }

    public void setDeleteEnabled(boolean z) {
        this.mDeleteEnabled = z;
    }

    public PendingIntent getPendingIntent() {
        return this.mPendingIntent;
    }
}
