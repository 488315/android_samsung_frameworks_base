package com.android.systemui.statusbar.notification.collection.render;

import com.android.systemui.statusbar.notification.collection.PipelineEntry;
import java.util.LinkedHashMap;
import java.util.Map;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class NotifViewBarn {
    public final Map rowMap = new LinkedHashMap();

    public final NotifViewController requireNodeController(PipelineEntry pipelineEntry) {
        NotifViewController notifViewController = (NotifViewController) ((LinkedHashMap) this.rowMap).get(pipelineEntry.getKey());
        if (notifViewController != null) {
            return notifViewController;
        }
        throw new IllegalStateException(("No view has been registered for entry: " + pipelineEntry.getKey()).toString());
    }
}
