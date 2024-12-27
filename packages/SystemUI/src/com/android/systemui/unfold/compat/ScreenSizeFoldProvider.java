package com.android.systemui.unfold.compat;

import android.content.Context;
import android.content.res.Configuration;
import com.android.systemui.unfold.updates.FoldProvider;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Executor;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        this.callbacks.add(foldCallback);
        foldCallback.onFoldUpdated(this.isFolded);
    }

    @Override // com.android.systemui.unfold.updates.FoldProvider
    public final void unregisterCallback(FoldProvider.FoldCallback foldCallback) {
        this.callbacks.remove(foldCallback);
    }
}
