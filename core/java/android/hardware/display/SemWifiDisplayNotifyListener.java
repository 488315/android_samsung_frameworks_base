package android.hardware.display;

import android.os.Bundle;

public interface SemWifiDisplayNotifyListener {
    void onDmrSupportChanged(boolean z);

    void onNotify(Bundle bundle);

    void onPlayStateChanged(int i);
}
