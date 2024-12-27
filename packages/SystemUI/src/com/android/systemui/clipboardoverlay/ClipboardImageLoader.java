package com.android.systemui.clipboardoverlay;

import android.content.Context;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

public final class ClipboardImageLoader {
    public final String TAG;
    public final CoroutineDispatcher bgDispatcher;
    public final Context context;

    public ClipboardImageLoader(Context context, CoroutineDispatcher coroutineDispatcher, CoroutineScope coroutineScope) {
    }
}
