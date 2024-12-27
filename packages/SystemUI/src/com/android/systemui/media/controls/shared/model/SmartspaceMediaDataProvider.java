package com.android.systemui.media.controls.shared.model;

import android.util.Log;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.TypeIntrinsics;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class SmartspaceMediaDataProvider implements BcSmartspaceDataPlugin {
    public final List smartspaceMediaTargetListeners = new ArrayList();

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin
    public final void onTargetsAvailable(List list) {
        Log.d("SsMediaDataProvider", "Forwarding Smartspace updates " + list);
        Iterator it = this.smartspaceMediaTargetListeners.iterator();
        while (it.hasNext()) {
            ((BcSmartspaceDataPlugin.SmartspaceTargetListener) it.next()).onSmartspaceTargetsUpdated(list);
        }
    }

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin
    public final void registerListener(BcSmartspaceDataPlugin.SmartspaceTargetListener smartspaceTargetListener) {
        ((ArrayList) this.smartspaceMediaTargetListeners).add(smartspaceTargetListener);
    }

    @Override // com.android.systemui.plugins.BcSmartspaceDataPlugin
    public final void unregisterListener(BcSmartspaceDataPlugin.SmartspaceTargetListener smartspaceTargetListener) {
        TypeIntrinsics.asMutableCollection(this.smartspaceMediaTargetListeners).remove(smartspaceTargetListener);
    }
}
