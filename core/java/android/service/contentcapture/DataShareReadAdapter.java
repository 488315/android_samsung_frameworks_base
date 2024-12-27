package android.service.contentcapture;

import android.annotation.SystemApi;
import android.os.ParcelFileDescriptor;

@SystemApi
public interface DataShareReadAdapter {
    void onError(int i);

    void onStart(ParcelFileDescriptor parcelFileDescriptor);
}
