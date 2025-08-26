package com.android.systemui.clipboardoverlay;

import android.content.Context;
import com.android.systemui.settings.UserTracker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes.dex */
public final class ClipboardImageLoader {
    public final CoroutineDispatcher bgDispatcher;
    public final Context context;

    public ClipboardImageLoader(Context context, UserTracker userTracker, CoroutineDispatcher coroutineDispatcher, CoroutineScope coroutineScope) {
        this.context = context;
        this.bgDispatcher = coroutineDispatcher;
    }
}
