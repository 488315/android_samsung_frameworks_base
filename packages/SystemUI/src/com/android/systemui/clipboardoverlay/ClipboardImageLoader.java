package com.android.systemui.clipboardoverlay;

import android.content.Context;
import com.android.systemui.settings.UserTracker;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ClipboardImageLoader {
    public final CoroutineDispatcher bgDispatcher;
    public final Context context;

    public ClipboardImageLoader(Context context, UserTracker userTracker, CoroutineDispatcher coroutineDispatcher, CoroutineScope coroutineScope) {
        this.context = context;
        this.bgDispatcher = coroutineDispatcher;
    }
}
