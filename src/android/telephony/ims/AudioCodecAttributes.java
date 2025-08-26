package android.telephony.ims;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Range;

@SystemApi
/* loaded from: classes4.dex */
public final class AudioCodecAttributes implements Parcelable {
    public static final Parcelable.Creator<AudioCodecAttributes> CREATOR = new Parcelable.Creator<AudioCodecAttributes>() { // from class: android.telephony.ims.AudioCodecAttributes.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioCodecAttributes createFromParcel(Parcel parcel) {
            return new AudioCodecAttributes(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AudioCodecAttributes[] newArray(int i) {
            return new AudioCodecAttributes[i];
        }
    };
    private float mBandwidthKhz;
    private Range<Float> mBandwidthRangeKhz;
    private float mBitrateKbps;
    private Range<Float> mBitrateRangeKbps;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AudioCodecAttributes(float f, Range<Float> range, float f2, Range<Float> range2) {
        this.mBitrateKbps = f;
        this.mBitrateRangeKbps = range;
        this.mBandwidthKhz = f2;
        this.mBandwidthRangeKhz = range2;
    }

    private AudioCodecAttributes(Parcel parcel) {
        this.mBitrateKbps = parcel.readFloat();
        this.mBitrateRangeKbps = new Range<>(Float.valueOf(parcel.readFloat()), Float.valueOf(parcel.readFloat()));
        this.mBandwidthKhz = parcel.readFloat();
        this.mBandwidthRangeKhz = new Range<>(Float.valueOf(parcel.readFloat()), Float.valueOf(parcel.readFloat()));
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeFloat(this.mBitrateKbps);
        parcel.writeFloat(((Float) this.mBitrateRangeKbps.getLower()).floatValue());
        parcel.writeFloat(((Float) this.mBitrateRangeKbps.getUpper()).floatValue());
        parcel.writeFloat(this.mBandwidthKhz);
        parcel.writeFloat(((Float) this.mBandwidthRangeKhz.getLower()).floatValue());
        parcel.writeFloat(((Float) this.mBandwidthRangeKhz.getUpper()).floatValue());
    }

    public float getBitrateKbps() {
        return this.mBitrateKbps;
    }

    public Range<Float> getBitrateRangeKbps() {
        return this.mBitrateRangeKbps;
    }

    public float getBandwidthKhz() {
        return this.mBandwidthKhz;
    }

    public Range<Float> getBandwidthRangeKhz() {
        return this.mBandwidthRangeKhz;
    }

    public String toString() {
        return "{ bitrateKbps=" + this.mBitrateKbps + ", bitrateRangeKbps=" + this.mBitrateRangeKbps + ", bandwidthKhz=" + this.mBandwidthKhz + ", bandwidthRangeKhz=" + this.mBandwidthRangeKhz + " }";
    }
}
