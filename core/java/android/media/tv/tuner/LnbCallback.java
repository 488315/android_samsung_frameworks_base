package android.media.tv.tuner;

import android.annotation.SystemApi;

@SystemApi
public interface LnbCallback {
    void onDiseqcMessage(byte[] bArr);

    void onEvent(int i);
}
