package com.android.systemui.statusbar.pipeline.shared.ui;

import android.view.ViewGroup;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.statusbar.phone.ui.StatusBarIconController;
import com.android.systemui.statusbar.pipeline.shared.data.repository.ConnectivityRepository;
import kotlin.coroutines.EmptyCoroutineContext;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
