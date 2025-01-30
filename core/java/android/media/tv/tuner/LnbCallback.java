package android.media.tv.tuner;

import android.annotation.SystemApi;

@SystemApi
/* loaded from: classes2.dex */
public interface LnbCallback {
  void onDiseqcMessage(byte[] bArr);

  void onEvent(int i);
}
