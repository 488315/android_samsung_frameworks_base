package com.android.systemui.keyguard.ui.view;

import android.content.Context;
import android.view.WindowManager;
import com.android.systemui.CoreStartable;
import com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerDependencies;
import com.android.systemui.keyguard.ui.viewmodel.AlternateBouncerViewModel;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class AlternateBouncerWindowViewBinder implements CoreStartable {
    public final Context context;
    public final AlternateBouncerDependencies dependencies;
    public final AlternateBouncerViewModel viewModel;
    public final WindowManager windowManager;

    public AlternateBouncerWindowViewBinder(CoroutineScope coroutineScope, Context context, AlternateBouncerViewModel alternateBouncerViewModel, AlternateBouncerDependencies alternateBouncerDependencies, WindowManager windowManager) {
        this.context = context;
        this.viewModel = alternateBouncerViewModel;
        this.dependencies = alternateBouncerDependencies;
        this.windowManager = windowManager;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
    }
}
