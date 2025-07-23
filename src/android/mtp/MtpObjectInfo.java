package android.mtp;

import com.android.internal.util.Preconditions;
import dalvik.system.VMRuntime;

/* loaded from: classes3.dex */
public final class MtpObjectInfo {
    private int mAssociationDesc;
    private int mAssociationType;
    private int mCompressedSize;
    private long mDateCreated;
    private long mDateModified;
    private int mFormat;
    private int mHandle;
    private int mImagePixDepth;
    private int mImagePixHeight;
    private int mImagePixWidth;
    private String mKeywords;
    private String mName;
    private int mParent;
    private int mProtectionStatus;
    private int mSequenceNumber;
    private int mStorageId;
    private int mThumbCompressedSize;
    private int mThumbFormat;
    private int mThumbPixHeight;
    private int mThumbPixWidth;

    private static long uint32ToLong(int i) {
        return i < 0 ? i + 4294967296L : i;
    }

    private MtpObjectInfo() {
        this.mName = "";
        this.mKeywords = "";
    }

    public final int getObjectHandle() {
        return this.mHandle;
    }

    public final int getStorageId() {
        return this.mStorageId;
    }

    public final int getFormat() {
        return this.mFormat;
    }

    public final int getProtectionStatus() {
        return this.mProtectionStatus;
    }

    public final int getCompressedSize() {
        Preconditions.checkState(this.mCompressedSize >= 0);
        return this.mCompressedSize;
    }

    public final long getCompressedSizeLong() {
        return uint32ToLong(this.mCompressedSize);
    }

    public final int getThumbFormat() {
        return this.mThumbFormat;
    }

    public final int getThumbCompressedSize() {
        Preconditions.checkState(this.mThumbCompressedSize >= 0);
        return this.mThumbCompressedSize;
    }

    public final long getThumbCompressedSizeLong() {
        return uint32ToLong(this.mThumbCompressedSize);
    }

    public final int getThumbPixWidth() {
        Preconditions.checkState(this.mThumbPixWidth >= 0);
        return this.mThumbPixWidth;
    }

    public final long getThumbPixWidthLong() {
        return uint32ToLong(this.mThumbPixWidth);
    }

    public final int getThumbPixHeight() {
        Preconditions.checkState(this.mThumbPixHeight >= 0);
        return this.mThumbPixHeight;
    }

    public final long getThumbPixHeightLong() {
        return uint32ToLong(this.mThumbPixHeight);
    }

    public final int getImagePixWidth() {
        Preconditions.checkState(this.mImagePixWidth >= 0);
        return this.mImagePixWidth;
    }

    public final long getImagePixWidthLong() {
        return uint32ToLong(this.mImagePixWidth);
    }

    public final int getImagePixHeight() {
        Preconditions.checkState(this.mImagePixHeight >= 0);
        return this.mImagePixHeight;
    }

    public final long getImagePixHeightLong() {
        return uint32ToLong(this.mImagePixHeight);
    }

    public final int getImagePixDepth() {
        Preconditions.checkState(this.mImagePixDepth >= 0);
        return this.mImagePixDepth;
    }

    public final long getImagePixDepthLong() {
        return uint32ToLong(this.mImagePixDepth);
    }

    public final int getParent() {
        return this.mParent;
    }

    public final int getAssociationType() {
        return this.mAssociationType;
    }

    public final int getAssociationDesc() {
        return this.mAssociationDesc;
    }

    public final int getSequenceNumber() {
        Preconditions.checkState(this.mSequenceNumber >= 0);
        return this.mSequenceNumber;
    }

    public final long getSequenceNumberLong() {
        return uint32ToLong(this.mSequenceNumber);
    }

    public final String getName() {
        return this.mName;
    }

    public final long getDateCreated() {
        return this.mDateCreated;
    }

    public final long getDateModified() {
        return this.mDateModified;
    }

    public final String getKeywords() {
        return this.mKeywords;
    }

    public static class Builder {
        private MtpObjectInfo mObjectInfo;

        public Builder() {
            MtpObjectInfo mtpObjectInfo = new MtpObjectInfo();
            this.mObjectInfo = mtpObjectInfo;
            mtpObjectInfo.mHandle = -1;
        }

        public Builder(MtpObjectInfo mtpObjectInfo) {
            MtpObjectInfo mtpObjectInfo2 = new MtpObjectInfo();
            this.mObjectInfo = mtpObjectInfo2;
            mtpObjectInfo2.mHandle = -1;
            this.mObjectInfo.mAssociationDesc = mtpObjectInfo.mAssociationDesc;
            this.mObjectInfo.mAssociationType = mtpObjectInfo.mAssociationType;
            this.mObjectInfo.mCompressedSize = mtpObjectInfo.mCompressedSize;
            this.mObjectInfo.mDateCreated = mtpObjectInfo.mDateCreated;
            this.mObjectInfo.mDateModified = mtpObjectInfo.mDateModified;
            this.mObjectInfo.mFormat = mtpObjectInfo.mFormat;
            this.mObjectInfo.mImagePixDepth = mtpObjectInfo.mImagePixDepth;
            this.mObjectInfo.mImagePixHeight = mtpObjectInfo.mImagePixHeight;
            this.mObjectInfo.mImagePixWidth = mtpObjectInfo.mImagePixWidth;
            this.mObjectInfo.mKeywords = mtpObjectInfo.mKeywords;
            this.mObjectInfo.mName = mtpObjectInfo.mName;
            this.mObjectInfo.mParent = mtpObjectInfo.mParent;
            this.mObjectInfo.mProtectionStatus = mtpObjectInfo.mProtectionStatus;
            this.mObjectInfo.mSequenceNumber = mtpObjectInfo.mSequenceNumber;
            this.mObjectInfo.mStorageId = mtpObjectInfo.mStorageId;
            this.mObjectInfo.mThumbCompressedSize = mtpObjectInfo.mThumbCompressedSize;
            this.mObjectInfo.mThumbFormat = mtpObjectInfo.mThumbFormat;
            this.mObjectInfo.mThumbPixHeight = mtpObjectInfo.mThumbPixHeight;
            this.mObjectInfo.mThumbPixWidth = mtpObjectInfo.mThumbPixWidth;
        }

        public Builder setObjectHandle(int i) {
            this.mObjectInfo.mHandle = i;
            return this;
        }

        public Builder setAssociationDesc(int i) {
            this.mObjectInfo.mAssociationDesc = i;
            return this;
        }

        public Builder setAssociationType(int i) {
            this.mObjectInfo.mAssociationType = i;
            return this;
        }

        public Builder setCompressedSize(long j) {
            this.mObjectInfo.mCompressedSize = MtpObjectInfo.longToUint32(j, "value");
            return this;
        }

        public Builder setDateCreated(long j) {
            this.mObjectInfo.mDateCreated = j;
            return this;
        }

        public Builder setDateModified(long j) {
            this.mObjectInfo.mDateModified = j;
            return this;
        }

        public Builder setFormat(int i) {
            this.mObjectInfo.mFormat = i;
            return this;
        }

        public Builder setImagePixDepth(long j) {
            this.mObjectInfo.mImagePixDepth = MtpObjectInfo.longToUint32(j, "value");
            return this;
        }

        public Builder setImagePixHeight(long j) {
            this.mObjectInfo.mImagePixHeight = MtpObjectInfo.longToUint32(j, "value");
            return this;
        }

        public Builder setImagePixWidth(long j) {
            this.mObjectInfo.mImagePixWidth = MtpObjectInfo.longToUint32(j, "value");
            return this;
        }

        public Builder setKeywords(String str) {
            if (VMRuntime.getRuntime().getTargetSdkVersion() > 25) {
                Preconditions.checkNotNull(str);
            } else if (str == null) {
                str = "";
            }
            this.mObjectInfo.mKeywords = str;
            return this;
        }

        public Builder setName(String str) {
            Preconditions.checkNotNull(str);
            this.mObjectInfo.mName = str;
            return this;
        }

        public Builder setParent(int i) {
            this.mObjectInfo.mParent = i;
            return this;
        }

        public Builder setProtectionStatus(int i) {
            this.mObjectInfo.mProtectionStatus = i;
            return this;
        }

        public Builder setSequenceNumber(long j) {
            this.mObjectInfo.mSequenceNumber = MtpObjectInfo.longToUint32(j, "value");
            return this;
        }

        public Builder setStorageId(int i) {
            this.mObjectInfo.mStorageId = i;
            return this;
        }

        public Builder setThumbCompressedSize(long j) {
            this.mObjectInfo.mThumbCompressedSize = MtpObjectInfo.longToUint32(j, "value");
            return this;
        }

        public Builder setThumbFormat(int i) {
            this.mObjectInfo.mThumbFormat = i;
            return this;
        }

        public Builder setThumbPixHeight(long j) {
            this.mObjectInfo.mThumbPixHeight = MtpObjectInfo.longToUint32(j, "value");
            return this;
        }

        public Builder setThumbPixWidth(long j) {
            this.mObjectInfo.mThumbPixWidth = MtpObjectInfo.longToUint32(j, "value");
            return this;
        }

        public MtpObjectInfo build() {
            MtpObjectInfo mtpObjectInfo = this.mObjectInfo;
            this.mObjectInfo = null;
            return mtpObjectInfo;
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static int longToUint32(long j, String str) {
        Preconditions.checkArgumentInRange(j, 0L, 4294967295L, str);
        return (int) j;
    }
}
