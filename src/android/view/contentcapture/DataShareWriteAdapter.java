package android.view.contentcapture;

import android.os.ParcelFileDescriptor;

/* loaded from: classes4.dex */
public interface DataShareWriteAdapter {
    default void onError(int i) {
    }

    void onRejected();

    void onWrite(ParcelFileDescriptor parcelFileDescriptor);
}
