package android.telephony.satellite;

import android.annotation.SystemApi;

@SystemApi
public interface NtnSignalStrengthCallback {
    void onNtnSignalStrengthChanged(NtnSignalStrength ntnSignalStrength);
}
