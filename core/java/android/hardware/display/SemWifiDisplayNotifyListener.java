package android.hardware.display;

import android.os.Bundle;

/* loaded from: classes2.dex */
public interface SemWifiDisplayNotifyListener {
    void onDmrSupportChanged(boolean z);

    void onNotify(Bundle bundle);

    void onPlayStateChanged(int i);
}
