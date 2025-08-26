package android.service.displayhash;

import android.annotation.SystemApi;
import android.os.Parcel;
import android.os.Parcelable;
import android.util.Size;

@SystemApi
/* loaded from: classes3.dex */
public final class DisplayHashParams implements Parcelable {
    public static final Parcelable.Creator<DisplayHashParams> CREATOR = new Parcelable.Creator<DisplayHashParams>() { // from class: android.service.displayhash.DisplayHashParams.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DisplayHashParams[] newArray(int i) {
            return new DisplayHashParams[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public DisplayHashParams createFromParcel(Parcel parcel) {
            return new DisplayHashParams(parcel);
        }
    };
    private final Size mBufferSize;
    private final boolean mGrayscaleBuffer;

    @Deprecated
    private void __metadata() {
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public static final class Builder {
        private Size mBufferSize;
        private boolean mGrayscaleBuffer;

        public Builder setBufferSize(int i, int i2) {
            this.mBufferSize = new Size(i, i2);
            return this;
        }

        public Builder setGrayscaleBuffer(boolean z) {
            this.mGrayscaleBuffer = z;
            return this;
        }

        public DisplayHashParams build() {
            return new DisplayHashParams(this.mBufferSize, this.mGrayscaleBuffer);
        }
    }

    public DisplayHashParams(Size size, boolean z) {
        this.mBufferSize = size;
        this.mGrayscaleBuffer = z;
    }

    public Size getBufferSize() {
        return this.mBufferSize;
    }

    public boolean isGrayscaleBuffer() {
        return this.mGrayscaleBuffer;
    }

    public String toString() {
        return "DisplayHashParams { bufferSize = " + this.mBufferSize + ", grayscaleBuffer = " + this.mGrayscaleBuffer + " }";
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        byte b = this.mGrayscaleBuffer ? (byte) 2 : (byte) 0;
        if (this.mBufferSize != null) {
            b = (byte) (b | 1);
        }
        parcel.writeByte(b);
        Size size = this.mBufferSize;
        if (size != null) {
            parcel.writeSize(size);
        }
    }

    DisplayHashParams(Parcel parcel) {
        byte b = parcel.readByte();
        boolean z = (b & 2) != 0;
        this.mBufferSize = (b & 1) == 0 ? null : parcel.readSize();
        this.mGrayscaleBuffer = z;
    }
}
