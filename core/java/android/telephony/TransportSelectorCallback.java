package android.telephony;

import android.annotation.SystemApi;

import java.util.function.Consumer;

@SystemApi
/* loaded from: classes4.dex */
public interface TransportSelectorCallback {
    void onCreated(DomainSelector domainSelector);

    void onSelectionTerminated(int i);

    void onWlanSelected(boolean z);

    void onWwanSelected(Consumer<WwanSelectorCallback> consumer);
}
