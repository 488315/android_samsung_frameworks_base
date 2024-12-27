package android.media.tv;

import android.annotation.NonNull;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.SharedMemory;

import com.android.internal.util.AnnotationValidations;

import java.io.IOException;

public final class AdBuffer implements Parcelable {
    public static final Parcelable.Creator<AdBuffer> CREATOR =
            new Parcelable.Creator<AdBuffer>() { // from class: android.media.tv.AdBuffer.1
                @Override // android.os.Parcelable.Creator
                public AdBuffer[] newArray(int size) {
                    return new AdBuffer[size];
                }

                @Override // android.os.Parcelable.Creator
                public AdBuffer createFromParcel(Parcel in) {
                    return new AdBuffer(in);
                }
            };
    private final SharedMemory mBuffer;
    private final int mFlags;
    private final int mId;
    private final int mLength;
    private final String mMimeType;
    private final int mOffset;
    private final long mPresentationTimeUs;

    public AdBuffer(
            int id,
            String mimeType,
            SharedMemory buffer,
            int offset,
            int length,
            long presentationTimeUs,
            int flags) {
        this.mId = id;
        this.mMimeType = mimeType;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) mimeType);
        this.mBuffer = buffer;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) buffer);
        this.mOffset = offset;
        this.mLength = length;
        this.mPresentationTimeUs = presentationTimeUs;
        this.mFlags = flags;
    }

    public static AdBuffer dupAdBuffer(AdBuffer buffer) throws IOException {
        if (buffer == null) {
            return null;
        }
        return new AdBuffer(
                buffer.mId,
                buffer.mMimeType,
                SharedMemory.fromFileDescriptor(buffer.mBuffer.getFdDup()),
                buffer.mOffset,
                buffer.mLength,
                buffer.mPresentationTimeUs,
                buffer.mFlags);
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
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mId);
        dest.writeString(this.mMimeType);
        dest.writeTypedObject(this.mBuffer, flags);
        dest.writeInt(this.mOffset);
        dest.writeInt(this.mLength);
        dest.writeLong(this.mPresentationTimeUs);
        dest.writeInt(this.mFlags);
    }

    @Override // android.os.Parcelable
    public int describeContents() {
        return 0;
    }

    private AdBuffer(Parcel in) {
        int id = in.readInt();
        String mimeType = in.readString();
        SharedMemory buffer = (SharedMemory) in.readTypedObject(SharedMemory.CREATOR);
        int offset = in.readInt();
        int length = in.readInt();
        long presentationTimeUs = in.readLong();
        int flags = in.readInt();
        this.mId = id;
        this.mMimeType = mimeType;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mMimeType);
        this.mBuffer = buffer;
        AnnotationValidations.validate(
                (Class<NonNull>) NonNull.class, (NonNull) null, (Object) this.mBuffer);
        this.mOffset = offset;
        this.mLength = length;
        this.mPresentationTimeUs = presentationTimeUs;
        this.mFlags = flags;
    }
}
