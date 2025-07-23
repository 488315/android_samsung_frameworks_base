package android.telephony;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.os.Parcel;
import android.os.Parcelable;
import android.telephony.AccessNetworkConstants;
import java.util.Arrays;

/* loaded from: classes4.dex */
public final class RadioAccessSpecifier implements Parcelable {
    public static final Parcelable.Creator<RadioAccessSpecifier> CREATOR = new Parcelable.Creator<RadioAccessSpecifier>() { // from class: android.telephony.RadioAccessSpecifier.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RadioAccessSpecifier createFromParcel(Parcel parcel) {
            return new RadioAccessSpecifier(parcel);
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public RadioAccessSpecifier[] newArray(int i) {
            return new RadioAccessSpecifier[i];
        }
    };
    private int[] mBands;
    private int[] mChannels;
    private int mRadioAccessNetwork;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public RadioAccessSpecifier(int i, int[] iArr, int[] iArr2) {
        this.mRadioAccessNetwork = i;
        if (iArr != null) {
            this.mBands = (int[]) iArr.clone();
        } else {
            this.mBands = null;
        }
        if (iArr2 != null) {
            this.mChannels = (int[]) iArr2.clone();
        } else {
            this.mChannels = null;
        }
    }

    public int getRadioAccessNetwork() {
        return this.mRadioAccessNetwork;
    }

    public int[] getBands() {
        int[] iArr = this.mBands;
        if (iArr == null) {
            return null;
        }
        return (int[]) iArr.clone();
    }

    public int[] getChannels() {
        int[] iArr = this.mChannels;
        if (iArr == null) {
            return null;
        }
        return (int[]) iArr.clone();
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mRadioAccessNetwork);
        parcel.writeIntArray(this.mBands);
        parcel.writeIntArray(this.mChannels);
    }

    private RadioAccessSpecifier(Parcel parcel) {
        this.mRadioAccessNetwork = parcel.readInt();
        this.mBands = parcel.createIntArray();
        this.mChannels = parcel.createIntArray();
    }

    public boolean equals(Object obj) {
        RadioAccessSpecifier radioAccessSpecifier;
        try {
            radioAccessSpecifier = (RadioAccessSpecifier) obj;
        } catch (ClassCastException unused) {
        }
        return obj != null && this.mRadioAccessNetwork == radioAccessSpecifier.mRadioAccessNetwork && Arrays.equals(this.mBands, radioAccessSpecifier.mBands) && Arrays.equals(this.mChannels, radioAccessSpecifier.mChannels);
    }

    public int hashCode() {
        return (this.mRadioAccessNetwork * 31) + (Arrays.hashCode(this.mBands) * 37) + (Arrays.hashCode(this.mChannels) * 39);
    }

    public String toString() {
        return "RadioAccessSpecifier[mRadioAccessNetwork=" + AccessNetworkConstants.AccessNetworkType.toString(this.mRadioAccessNetwork) + ", mBands=" + Arrays.toString(this.mBands) + ", mChannels=" + Arrays.toString(this.mChannels) + NavigationBarInflaterView.SIZE_MOD_END;
    }
}
