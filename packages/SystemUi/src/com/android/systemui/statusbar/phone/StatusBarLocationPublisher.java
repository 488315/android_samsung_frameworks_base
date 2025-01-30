package com.android.systemui.statusbar.phone;

import androidx.appcompat.app.ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0;
import com.android.systemui.statusbar.policy.CallbackController;
import java.lang.ref.WeakReference;
import java.util.LinkedHashSet;
import java.util.Set;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class StatusBarLocationPublisher implements CallbackController {
    public final Set listeners = new LinkedHashSet();

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void addCallback(Object obj) {
        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m7m(obj);
        this.listeners.add(new WeakReference(null));
    }

    @Override // com.android.systemui.statusbar.policy.CallbackController
    public final void removeCallback(Object obj) {
        ToolbarActionBar$$ExternalSyntheticThrowCCEIfNotNull0.m7m(obj);
        Set<WeakReference> set = this.listeners;
        WeakReference weakReference = null;
        for (WeakReference weakReference2 : set) {
            if (Intrinsics.areEqual(weakReference2.get(), null)) {
                weakReference = weakReference2;
            }
        }
        if (weakReference != null) {
            set.remove(weakReference);
        }
    }
}
