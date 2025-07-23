package androidx.profileinstaller;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
