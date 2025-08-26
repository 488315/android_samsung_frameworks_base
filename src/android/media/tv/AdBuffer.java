package android.media.tv;

import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SharedMemory;
import com.android.internal.util.AnnotationValidations;
import java.io.IOException;

/* loaded from: classes3.dex */
public final class AdBuffer implements Parcelable {
    public static final Parcelable.Creator<AdBuffer> CREATOR = new Parcelable.Creator<AdBuffer>() { // from class: android.media.tv.AdBuffer.1
        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AdBuffer[] newArray(int i) {
            return new AdBuffer[i];
        }

        /* JADX WARN: Can't rename method to resolve collision */
        @Override // android.os.Parcelable.Creator
        public AdBuffer createFromParcel(Parcel parcel) {
            return new AdBuffer(parcel);
        }
    };
    private final SharedMemory mBuffer;
    private final int mFlags;
    private final int mId;
    private final int mLength;
    private final String mMimeType;
    private final int mOffset;
    private final long mPresentationTimeUs;

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    public AdBuffer(int i, String str, SharedMemory sharedMemory, int i2, int i3, long j, int i4) {
        this.mId = i;
        this.mMimeType = str;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) str);
        this.mBuffer = sharedMemory;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) sharedMemory);
        this.mOffset = i2;
        this.mLength = i3;
        this.mPresentationTimeUs = j;
        this.mFlags = i4;
    }

    public static AdBuffer dupAdBuffer(AdBuffer adBuffer) throws IOException {
        if (adBuffer == null) {
            return null;
        }
        return new AdBuffer(adBuffer.mId, adBuffer.mMimeType, SharedMemory.fromFileDescriptor(adBuffer.mBuffer.getFdDup()), adBuffer.mOffset, adBuffer.mLength, adBuffer.mPresentationTimeUs, adBuffer.mFlags);
    }

    public int getId() {
        return this.mId;
    }

    public String getMimeType() {
        return this.mMimeType;
    }

    public SharedMemory getSharedMemory() {
        return this.mBuffer;
    }

    public int getOffset() {
        return this.mOffset;
    }

    public int getLength() {
        return this.mLength;
    }

    public long getPresentationTimeUs() {
        return this.mPresentationTimeUs;
    }

    public int getFlags() {
        return this.mFlags;
    }

    @Override // android.os.Parcelable
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeInt(this.mId);
        parcel.writeString(this.mMimeType);
        parcel.writeTypedObject(this.mBuffer, i);
        parcel.writeInt(this.mOffset);
        parcel.writeInt(this.mLength);
        parcel.writeLong(this.mPresentationTimeUs);
        parcel.writeInt(this.mFlags);
    }

    private AdBuffer(Parcel parcel) {
        int i = parcel.readInt();
        String string = parcel.readString();
        SharedMemory sharedMemory = (SharedMemory) parcel.readTypedObject(SharedMemory.CREATOR);
        int i2 = parcel.readInt();
        int i3 = parcel.readInt();
        long j = parcel.readLong();
        int i4 = parcel.readInt();
        this.mId = i;
        this.mMimeType = string;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) string);
        this.mBuffer = sharedMemory;
        AnnotationValidations.validate((Class<NonNull>) NonNull.class, (NonNull) null, (Object) sharedMemory);
        this.mOffset = i2;
        this.mLength = i3;
        this.mPresentationTimeUs = j;
        this.mFlags = i4;
    }
}
