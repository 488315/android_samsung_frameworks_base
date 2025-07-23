package com.samsung.android.media;

/* loaded from: classes6.dex */
public class SemQuramDngJavaMetadata {
    SemQuramDngOrientation mBaseOrientation;
    SemQuramDngJavaPoint mCropOrigin;
    SemQuramDngJavaPoint mCropSize;
    SemQuramDngFingerPrint mEmbeddedXMPDigest;
    boolean mHasBaseOrientation;
    byte[] mIPTCBlock;
    long mIPTCBlockSize;
    long mIPTCOffset;
    int mImageHeight;
    int mImageWidth;
    boolean mIsMakerNoteSafe;
    byte[] mMakerNote;
    long mMakerNoteSize;
    SemQuramDngJavaExif mOriginalExif;
    String mSourceMIME;
    boolean mXMPinSidecar;
    boolean mXMPisNewer;

    public void buildMetadata(SemQuramDngJavaMetadataPrimitive semQuramDngJavaMetadataPrimitive) {
        this.mHasBaseOrientation = semQuramDngJavaMetadataPrimitive.mHasBaseOrientation;
        this.mBaseOrientation = new SemQuramDngOrientation(semQuramDngJavaMetadataPrimitive.mBaseOrientation);
        this.mIsMakerNoteSafe = semQuramDngJavaMetadataPrimitive.mIsMakerNoteSafe;
        this.mImageWidth = semQuramDngJavaMetadataPrimitive.mImageWidth;
        this.mImageHeight = semQuramDngJavaMetadataPrimitive.mImageHeight;
        this.mCropOrigin = new SemQuramDngJavaPoint(semQuramDngJavaMetadataPrimitive.mCropOriginX, semQuramDngJavaMetadataPrimitive.mCropOriginY);
        this.mCropSize = new SemQuramDngJavaPoint(semQuramDngJavaMetadataPrimitive.mCropWidth, semQuramDngJavaMetadataPrimitive.mCropHeight);
        this.mMakerNote = semQuramDngJavaMetadataPrimitive.mMakerNote;
        this.mMakerNoteSize = semQuramDngJavaMetadataPrimitive.mMakerNoteSize;
        this.mOriginalExif = new SemQuramDngJavaExif(semQuramDngJavaMetadataPrimitive.mOriginalExif);
        this.mIPTCBlock = semQuramDngJavaMetadataPrimitive.mIPTCBlock;
        this.mIPTCBlockSize = semQuramDngJavaMetadataPrimitive.mIPTCBlockSize;
        this.mIPTCOffset = semQuramDngJavaMetadataPrimitive.mIPTCOffset;
        this.mEmbeddedXMPDigest = new SemQuramDngFingerPrint(semQuramDngJavaMetadataPrimitive.mEmbeddedXMPDigest);
        this.mXMPinSidecar = semQuramDngJavaMetadataPrimitive.mXMPinSidecar;
        this.mXMPisNewer = semQuramDngJavaMetadataPrimitive.mXMPisNewer;
        this.mSourceMIME = semQuramDngJavaMetadataPrimitive.mSourceMIME;
    }

    public boolean getHasBaseOrientation() {
        return this.mHasBaseOrientation;
    }

    public SemQuramDngOrientation getBaseOrientation() {
        return this.mBaseOrientation;
    }

    public boolean getIsMakerNoteSafe() {
        return this.mIsMakerNoteSafe;
    }

    public byte getMakerNote(int i) {
        return this.mMakerNote[i];
    }

    public long getMakerNoteSize() {
        return this.mMakerNoteSize;
    }

    public SemQuramDngJavaExif getOriginalExif() {
        return this.mOriginalExif;
    }

    public byte getIPTCBlock(int i) {
        return this.mIPTCBlock[i];
    }

    public long getIPTCBlockSize() {
        return this.mIPTCBlockSize;
    }

    public long getIPTCOffset() {
        return this.mIPTCOffset;
    }

    public SemQuramDngFingerPrint getEmbeddedXMPDigest() {
        return this.mEmbeddedXMPDigest;
    }

    public boolean getXMPinSidecar() {
        return this.mXMPinSidecar;
    }

    public boolean getXMPisNewer() {
        return this.mXMPisNewer;
    }

    public String getSourceMIME() {
        return this.mSourceMIME;
    }

    public int getImageWidth() {
        return this.mImageWidth;
    }

    public int getImageHeight() {
        return this.mImageHeight;
    }

    public SemQuramDngJavaPoint getCropOrigin() {
        return this.mCropOrigin;
    }

    public SemQuramDngJavaPoint getCropSize() {
        return this.mCropSize;
    }
}
