package android.view.contentcapture;

import android.p009os.ParcelFileDescriptor;

/* loaded from: classes4.dex */
public interface DataShareWriteAdapter {
    void onRejected();

    void onWrite(ParcelFileDescriptor parcelFileDescriptor);

    default void onError(int errorCode) {
    }
}
