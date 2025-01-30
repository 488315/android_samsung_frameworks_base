package androidx.core.provider;

import android.net.Uri;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class FontsContractCompat$FontInfo {
    public final boolean mItalic;
    public final int mResultCode;
    public final int mTtcIndex;
    public final Uri mUri;
    public final int mWeight;

    @Deprecated
    public FontsContractCompat$FontInfo(Uri uri, int i, int i2, boolean z, int i3) {
        uri.getClass();
        this.mUri = uri;
        this.mTtcIndex = i;
        this.mWeight = i2;
        this.mItalic = z;
        this.mResultCode = i3;
    }
}
