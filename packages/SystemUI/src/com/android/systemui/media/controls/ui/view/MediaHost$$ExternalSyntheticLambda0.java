package com.android.systemui.media.controls.ui.view;

import android.os.Trace;
import com.android.app.tracing.TraceUtilsKt;
import com.android.systemui.media.controls.ui.controller.MediaHostStatesManager;
import com.android.systemui.media.controls.ui.controller.MediaViewController;
import com.android.systemui.media.controls.ui.view.MediaHost;
import java.util.Iterator;
import java.util.LinkedHashMap;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final /* synthetic */ class MediaHost$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ MediaHost f$0;
    public final /* synthetic */ int f$1;

    public /* synthetic */ MediaHost$$ExternalSyntheticLambda0(MediaHost mediaHost, int i) {
        this.f$0 = mediaHost;
        this.f$1 = i;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        int i = this.f$1;
        MediaHost mediaHost = this.f$0;
        MediaHostStatesManager mediaHostStatesManager = mediaHost.mediaHostStatesManager;
        MediaHost.MediaHostStateHolder mediaHostStateHolder = mediaHost.state;
        mediaHostStatesManager.getClass();
        boolean isEnabled = Trace.isEnabled();
        if (isEnabled) {
            TraceUtilsKt.beginSlice("MediaHostStatesManager#updateHostState");
        }
        try {
            if (!mediaHostStateHolder.equals((MediaHostState) ((LinkedHashMap) mediaHostStatesManager.mediaHostStates).get(Integer.valueOf(i)))) {
                MediaHost.MediaHostStateHolder copy = mediaHostStateHolder.copy();
                mediaHostStatesManager.mediaHostStates.put(Integer.valueOf(i), copy);
                mediaHostStatesManager.updateCarouselDimensions(i, mediaHostStateHolder);
                Iterator it = mediaHostStatesManager.controllers.iterator();
                while (it.hasNext()) {
                    ((MediaViewController) it.next()).stateCallback.onHostStateChanged(i, copy);
                }
                Iterator it2 = mediaHostStatesManager.callbacks.iterator();
                while (it2.hasNext()) {
                    ((MediaHostStatesManager.Callback) it2.next()).onHostStateChanged(i, copy);
                }
            }
            Unit unit = Unit.INSTANCE;
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
            return Unit.INSTANCE;
        } catch (Throwable th) {
            if (isEnabled) {
                TraceUtilsKt.endSlice();
            }
            throw th;
        }
    }
}
