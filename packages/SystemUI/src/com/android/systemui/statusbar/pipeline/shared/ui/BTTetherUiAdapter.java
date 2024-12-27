package com.android.systemui.statusbar.pipeline.shared.ui;

import android.view.ViewGroup;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.statusbar.phone.ui.StatusBarIconController;
import com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepository;
import kotlin.coroutines.EmptyCoroutineContext;

public final class BTTetherUiAdapter {
    public final ConnectivityRepository connectivityRepository;
    public final StatusBarIconController iconController;

    public BTTetherUiAdapter(StatusBarIconController statusBarIconController, ConnectivityRepository connectivityRepository) {
        this.iconController = statusBarIconController;
        this.connectivityRepository = connectivityRepository;
    }

    public final void bindGroup(ViewGroup viewGroup) {
        RepeatWhenAttachedKt.repeatWhenAttached(viewGroup, EmptyCoroutineContext.INSTANCE, new BTTetherUiAdapter$bindGroup$1(this, null));
    }
}
