package android.media;

import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;
import com.android.internal.notification.SystemNotificationChannels;

/* loaded from: classes2.dex */
public class AudioRoutesInfo implements Parcelable {
    public static final Parcelable.Creator<AudioRoutesInfo> CREATOR = new Parcelable.Creator<AudioRoutesInfo>() { // from class: android.media.AudioRoutesInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioRoutesInfo createFromParcel(Parcel parcel) {
            return new AudioRoutesInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioRoutesInfo[] newArray(int i) {
            return new AudioRoutesInfo[i];
        }
    };
    public static final int MAIN_DOCK_SPEAKERS = 4;
    public static final int MAIN_HDMI = 8;
    public static final int MAIN_HEADPHONES = 2;
    public static final int MAIN_HEADSET = 1;
    public static final int MAIN_SPEAKER = 0;
    public static final int MAIN_USB = 16;
    public CharSequence bluetoothName;
    private String mSetForcePath;
    public int mainType;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AudioRoutesInfo() {
        this.mainType = 0;
        this.mSetForcePath = "";
    }

    public AudioRoutesInfo(AudioRoutesInfo audioRoutesInfo) {
        this.mainType = 0;
        this.mSetForcePath = "";
        this.bluetoothName = audioRoutesInfo.bluetoothName;
        this.mainType = audioRoutesInfo.mainType;
    }

    AudioRoutesInfo(Parcel parcel) {
        this.mainType = 0;
        this.mSetForcePath = "";
        this.bluetoothName = TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mainType = parcel.readInt();
    }

    public String toString() {
        String str;
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append("{ type=");
        sb.append(typeToString(this.mainType));
        if (TextUtils.isEmpty(this.bluetoothName)) {
            str = "";
        } else {
            str = ", bluetoothName=" + ((Object) this.bluetoothName);
        }
        sb.append(str);
        sb.append(" }");
        return sb.toString();
    }

    private static String typeToString(int i) {
        if (i == 0) {
            return "SPEAKER";
        }
        if ((i & 1) != 0) {
            return "HEADSET";
        }
        if ((i & 2) != 0) {
            return "HEADPHONES";
        }
        if ((i & 4) != 0) {
            return "DOCK_SPEAKERS";
        }
        if ((i & 8) != 0) {
            return "HDMI";
        }
        if ((i & 16) != 0) {
            return SystemNotificationChannels.USB;
        }
        return Integer.toHexString(i);
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        TextUtils.writeToParcel(this.bluetoothName, parcel, i);
        parcel.writeInt(this.mainType);
    }

    public String getSetForcePath() {
        return this.mSetForcePath;
    }

    public void setForcePath(String str) {
        this.mSetForcePath = str;
    }
}
