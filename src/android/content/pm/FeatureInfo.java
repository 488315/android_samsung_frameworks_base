package android.content.pm;

import android.media.MediaMetrics;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.proto.ProtoOutputStream;

/* loaded from: classes.dex */
public class FeatureInfo implements Parcelable {
    public static final Parcelable.Creator<FeatureInfo> CREATOR = new Parcelable.Creator<FeatureInfo>() { // from class: android.content.pm.FeatureInfo.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FeatureInfo createFromParcel(Parcel parcel) {
            return new FeatureInfo(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public FeatureInfo[] newArray(int i) {
            return new FeatureInfo[i];
        }
    };
    public static final int FLAG_REQUIRED = 1;
    public static final int GL_ES_VERSION_UNDEFINED = 0;
    public int flags;
    public String name;
    public int reqGlEsVersion;
    public int version;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public FeatureInfo() {
    }

    public FeatureInfo(FeatureInfo featureInfo) {
        this.name = featureInfo.name;
        this.version = featureInfo.version;
        this.reqGlEsVersion = featureInfo.reqGlEsVersion;
        this.flags = featureInfo.flags;
    }

    public String toString() {
        if (this.name != null) {
            return "FeatureInfo{" + Integer.toHexString(System.identityHashCode(this)) + " " + this.name + " v=" + this.version + " fl=0x" + Integer.toHexString(this.flags) + "}";
        }
        return "FeatureInfo{" + Integer.toHexString(System.identityHashCode(this)) + " glEsVers=" + getGlEsVersion() + " fl=0x" + Integer.toHexString(this.flags) + "}";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString8(this.name);
        parcel.writeInt(this.version);
        parcel.writeInt(this.reqGlEsVersion);
        parcel.writeInt(this.flags);
    }

    public void dumpDebug(ProtoOutputStream protoOutputStream, long j) {
        long start = protoOutputStream.start(j);
        String str = this.name;
        if (str != null) {
            protoOutputStream.write(1138166333441L, str);
        }
        protoOutputStream.write(1120986464258L, this.version);
        protoOutputStream.write(1138166333443L, getGlEsVersion());
        protoOutputStream.write(1120986464260L, this.flags);
        protoOutputStream.end(start);
    }

    private FeatureInfo(Parcel parcel) {
        this.name = parcel.readString8();
        this.version = parcel.readInt();
        this.reqGlEsVersion = parcel.readInt();
        this.flags = parcel.readInt();
    }

    public String getGlEsVersion() {
        int i = this.reqGlEsVersion;
        return String.valueOf(((-65536) & i) >> 16) + MediaMetrics.SEPARATOR + String.valueOf(i & 65535);
    }
}
