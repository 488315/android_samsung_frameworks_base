package com.android.systemui.clipboardoverlay;

import android.content.Context;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ClipboardImageLoader {
    public final String TAG = "ClipboardImageLoader";
    public final CoroutineDispatcher bgDispatcher;
    public final Context context;

    public ClipboardImageLoader(Context context, CoroutineDispatcher coroutineDispatcher, CoroutineScope coroutineScope) {
        this.context = context;
        this.bgDispatcher = coroutineDispatcher;
    }
}
