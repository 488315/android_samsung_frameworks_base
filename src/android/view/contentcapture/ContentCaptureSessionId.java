package android.view.contentcapture;

import android.os.Parcel;
import android.os.Parcelable;
import java.io.PrintWriter;

/* loaded from: classes4.dex */
public final class ContentCaptureSessionId implements Parcelable {
    public static final Parcelable.Creator<ContentCaptureSessionId> CREATOR = new Parcelable.Creator<ContentCaptureSessionId>() { // from class: android.view.contentcapture.ContentCaptureSessionId.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContentCaptureSessionId createFromParcel(Parcel parcel) {
            return new ContentCaptureSessionId(parcel.readInt());
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ContentCaptureSessionId[] newArray(int i) {
            return new ContentCaptureSessionId[i];
        }
    };
    private final int mValue;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ContentCaptureSessionId(int i) {
        this.mValue = i;
    }

    public int getValue() {
        return this.mValue;
    }

    public int hashCode() {
        return 31 + this.mValue;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        return obj != null && getClass() == obj.getClass() && this.mValue == ((ContentCaptureSessionId) obj).mValue;
    }

    public String toString() {
        return Integer.toString(this.mValue);
    }

    public void dump(PrintWriter printWriter) {
        printWriter.print(this.mValue);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mValue);
    }
}
