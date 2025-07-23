package com.android.systemui.unfold.compat;

import android.content.Context;
import android.content.res.Configuration;
import com.android.systemui.unfold.updates.FoldProvider;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class ScreenSizeFoldProvider implements FoldProvider {
    public final List callbacks = new ArrayList();
    public boolean isFolded;

    public ScreenSizeFoldProvider(Context context) {
        onConfigurationChange(context.getResources().getConfiguration());
    }

    public final void onConfigurationChange(Configuration configuration) {
        boolean z = configuration.smallestScreenWidthDp < 600;
        if (z == this.isFolded) {
            return;
        }
        this.isFolded = z;
        Iterator it = this.callbacks.iterator();
        while (it.hasNext()) {
            ((FoldProvider.FoldCallback) it.next()).onFoldUpdated(this.isFolded);
        }
    }

    @Override // com.android.systemui.unfold.updates.FoldProvider
    public final void registerCallback(FoldProvider.FoldCallback foldCallback, Executor executor) {
        ((ArrayList) this.callbacks).add(foldCallback);
        foldCallback.onFoldUpdated(this.isFolded);
    }

    @Override // com.android.systemui.unfold.updates.FoldProvider
    public final void unregisterCallback(FoldProvider.FoldCallback foldCallback) {
        ((ArrayList) this.callbacks).remove(foldCallback);
    }
}
