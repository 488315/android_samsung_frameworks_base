package com.android.systemui.statusbar.phone;

import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import com.android.systemui.statusbar.policy.CallbackController;
import java.lang.ref.WeakReference;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.jvm.internal.Intrinsics;

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
