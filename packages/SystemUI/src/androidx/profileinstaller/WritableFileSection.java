package androidx.profileinstaller;

/* loaded from: classes.dex */
public class WritableFileSection {
    public final byte[] mContents;
    public final boolean mNeedsCompression;
    public final FileSectionType mType;

    public WritableFileSection(FileSectionType fileSectionType, int i, byte[] bArr, boolean z) {
        this.mType = fileSectionType;
        this.mContents = bArr;
        this.mNeedsCompression = z;
    }
}
