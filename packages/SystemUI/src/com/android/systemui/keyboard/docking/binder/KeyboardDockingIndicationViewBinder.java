package com.android.systemui.keyboard.docking.binder;

import android.content.Context;
import android.view.WindowManager;
import com.android.systemui.keyboard.docking.ui.KeyboardDockingIndicationView;
import com.android.systemui.keyboard.docking.ui.viewmodel.KeyboardDockingIndicationViewModel;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class KeyboardDockingIndicationViewBinder {
    public final KeyboardDockingIndicationViewBinder$drawCallback$1 drawCallback;
    public final KeyboardDockingIndicationView glowEffectView;
    public final KeyboardDockingIndicationViewBinder$stateChangedCallback$1 stateChangedCallback;
    public final KeyboardDockingIndicationViewModel viewModel;
    public final WindowManager.LayoutParams windowLayoutParams;
    public final WindowManager windowManager;

    public KeyboardDockingIndicationViewBinder(Context context, CoroutineScope coroutineScope, KeyboardDockingIndicationViewModel keyboardDockingIndicationViewModel, WindowManager windowManager) {
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.width = -1;
        layoutParams.height = -1;
        layoutParams.format = -3;
        layoutParams.type = 2009;
        layoutParams.setFitInsetsTypes(0);
        layoutParams.setTitle("Edge glow effect");
        layoutParams.flags = 24;
        layoutParams.setTrustedOverlay();
        new KeyboardDockingIndicationView(context, null);
        new Object(this) { // from class: com.android.systemui.keyboard.docking.binder.KeyboardDockingIndicationViewBinder$drawCallback$1
            public final /* synthetic */ KeyboardDockingIndicationViewBinder this$0;
        };
        new Object(this) { // from class: com.android.systemui.keyboard.docking.binder.KeyboardDockingIndicationViewBinder$stateChangedCallback$1
            public final /* synthetic */ KeyboardDockingIndicationViewBinder this$0;
        };
    }
}
