package android.telephony.ims;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;

@SystemApi
/* loaded from: classes4.dex */
public final class ImsSuppServiceNotification implements Parcelable {
    public static final Parcelable.Creator<ImsSuppServiceNotification> CREATOR = new Parcelable.Creator<ImsSuppServiceNotification>() { // from class: android.telephony.ims.ImsSuppServiceNotification.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImsSuppServiceNotification createFromParcel(Parcel parcel) {
            return new ImsSuppServiceNotification(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public ImsSuppServiceNotification[] newArray(int i) {
            return new ImsSuppServiceNotification[i];
        }
    };
    private static final String TAG = "ImsSuppServiceNotification";
    public final int code;
    public final String[] history;
    public final int index;
    public final int notificationType;
    public final String number;
    public final int type;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public ImsSuppServiceNotification(int i, int i2, int i3, int i4, String str, String[] strArr) {
        this.notificationType = i;
        this.code = i2;
        this.index = i3;
        this.type = i4;
        this.number = str;
        this.history = strArr;
    }

    public ImsSuppServiceNotification(Parcel parcel) {
        this.notificationType = parcel.readInt();
        this.code = parcel.readInt();
        this.index = parcel.readInt();
        this.type = parcel.readInt();
        this.number = parcel.readString();
        this.history = parcel.createStringArray();
    }

    public String toString() {
        return "{ notificationType=" + this.notificationType + ", code=" + this.code + ", index=" + this.index + ", type=" + this.type + ", number=" + this.number + ", history=" + Arrays.toString(this.history) + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.notificationType);
        parcel.writeInt(this.code);
        parcel.writeInt(this.index);
        parcel.writeInt(this.type);
        parcel.writeString(this.number);
        parcel.writeStringArray(this.history);
    }
}
