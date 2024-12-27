package com.android.systemui.privacy;

import com.android.systemui.privacy.PrivacyConfig;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class PrivacyConfig$removeCallback$1 implements Runnable {
    public final /* synthetic */ WeakReference $callback;
    public final /* synthetic */ PrivacyConfig this$0;

    public PrivacyConfig$removeCallback$1(PrivacyConfig privacyConfig, WeakReference<PrivacyConfig.Callback> weakReference) {
        this.this$0 = privacyConfig;
        this.$callback = weakReference;
    }

    @Override // java.lang.Runnable
    public final void run() {
        List list = this.this$0.callbacks;
        final WeakReference weakReference = this.$callback;
        ((ArrayList) list).removeIf(new Predicate() { // from class: com.android.systemui.privacy.PrivacyConfig$removeCallback$1.1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                PrivacyConfig.Callback callback = (PrivacyConfig.Callback) ((WeakReference) obj).get();
                if (callback != null) {
                    return callback.equals(weakReference.get());
                }
                return true;
            }
        });
    }
}
