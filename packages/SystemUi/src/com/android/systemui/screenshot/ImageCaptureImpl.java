package com.android.systemui.screenshot;

import android.app.IActivityTaskManager;
import android.view.IWindowManager;
import kotlinx.coroutines.CoroutineDispatcher;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class ImageCaptureImpl implements ImageCapture {
    public final IWindowManager windowManager;

    public ImageCaptureImpl(IWindowManager iWindowManager, IActivityTaskManager iActivityTaskManager, CoroutineDispatcher coroutineDispatcher) {
        this.windowManager = iWindowManager;
    }
}
