package com.android.systemui.volume.view.context;

import android.content.Context;
import com.android.systemui.volume.VolumeDependencyBase;
import com.android.systemui.volume.store.VolumePanelStore;
import com.samsung.systemui.splugins.volume.VolumePanelState;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public interface ViewContext {
    Context getContext();

    VolumePanelState getPanelState$1();

    VolumePanelStore getStore$1();

    VolumeDependencyBase getVolDeps();
}
