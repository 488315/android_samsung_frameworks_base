package android.graphics.fonts;

import android.annotation.SystemApi;
import android.os.ParcelFileDescriptor;
import java.util.Objects;

@SystemApi
/* loaded from: classes.dex */
public final class FontFileUpdateRequest {
    private final ParcelFileDescriptor mParcelFileDescriptor;
    private final byte[] mSignature;

    public FontFileUpdateRequest(ParcelFileDescriptor parcelFileDescriptor, byte[] bArr) {
        Objects.requireNonNull(parcelFileDescriptor);
        Objects.requireNonNull(bArr);
        this.mParcelFileDescriptor = parcelFileDescriptor;
        this.mSignature = bArr;
    }

    public ParcelFileDescriptor getParcelFileDescriptor() {
        return this.mParcelFileDescriptor;
    }

    public byte[] getSignature() {
        return this.mSignature;
    }
}
