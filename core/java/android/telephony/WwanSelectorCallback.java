package android.telephony;

import android.annotation.SystemApi;
import android.os.CancellationSignal;

import java.util.List;
import java.util.function.Consumer;

@SystemApi
public interface WwanSelectorCallback {
    void onDomainSelected(int i, boolean z);

    void onRequestEmergencyNetworkScan(
            List<Integer> list,
            int i,
            boolean z,
            CancellationSignal cancellationSignal,
            Consumer<EmergencyRegistrationResult> consumer);
}
