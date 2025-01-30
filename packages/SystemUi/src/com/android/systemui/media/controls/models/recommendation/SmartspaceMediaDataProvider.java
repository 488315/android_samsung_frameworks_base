package com.android.systemui.media.controls.models.recommendation;

import android.util.Log;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.jvm.internal.TypeIntrinsics;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.jvm.internal.markers.KMutableCollection;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
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
        List list = this.smartspaceMediaTargetListeners;
        if (!(list instanceof KMappedMarker) || (list instanceof KMutableCollection)) {
            list.remove(smartspaceTargetListener);
        } else {
            TypeIntrinsics.throwCce(list, "kotlin.collections.MutableCollection");
            throw null;
        }
    }
}
