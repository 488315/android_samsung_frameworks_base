package com.android.systemui.smartspace.ui.binder;

import android.view.View;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.plugins.BcSmartspaceDataPlugin;
import com.android.systemui.smartspace.ui.viewmodel.SmartspaceViewModel;
import kotlin.coroutines.EmptyCoroutineContext;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class SmartspaceViewBinder {
    public static final SmartspaceViewBinder INSTANCE = new SmartspaceViewBinder();

    private SmartspaceViewBinder() {
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static void bind(BcSmartspaceDataPlugin.SmartspaceView smartspaceView, SmartspaceViewModel smartspaceViewModel) {
        RepeatWhenAttachedKt.repeatWhenAttached((View) smartspaceView, EmptyCoroutineContext.INSTANCE, new SmartspaceViewBinder$bind$1(smartspaceViewModel, smartspaceView, null));
    }
}
