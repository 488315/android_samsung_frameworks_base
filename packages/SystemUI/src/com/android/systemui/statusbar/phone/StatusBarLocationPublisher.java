package com.android.systemui.statusbar.phone;

import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import com.android.systemui.statusbar.policy.CallbackController;
import java.lang.ref.WeakReference;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class StatusBarLocationPublisher implements CallbackController {
    public final Set listeners = new LinkedHashSet();

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
        this.listeners.add(new WeakReference(null));
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m(obj);
        WeakReference weakReference = null;
        for (WeakReference weakReference2 : this.listeners) {
            if (Intrinsics.areEqual(weakReference2.get(), (Object) null)) {
                weakReference = weakReference2;
            }
        }
        if (weakReference != null) {
            this.listeners.remove(weakReference);
        }
    }
}
