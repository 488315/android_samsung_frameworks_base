package androidx.core.provider;

import android.net.Uri;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class FontsContractCompat$FontInfo {
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
