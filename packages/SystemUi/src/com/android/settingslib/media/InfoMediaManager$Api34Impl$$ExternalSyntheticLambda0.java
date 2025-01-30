package com.android.settingslib.media;

import android.media.RouteListingPreference;
import java.util.Map;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final /* synthetic */ class InfoMediaManager$Api34Impl$$ExternalSyntheticLambda0 implements Consumer {
    public final /* synthetic */ Map f$0;

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        RouteListingPreference.Item item = (RouteListingPreference.Item) obj;
        this.f$0.put(item.getRouteId(), item);
    }
}
