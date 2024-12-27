package android.view.contentcapture;

import android.os.ParcelFileDescriptor;

public interface DataShareWriteAdapter {
    void onRejected();

    void onWrite(ParcelFileDescriptor parcelFileDescriptor);

    default void onError(int errorCode) {}
}
