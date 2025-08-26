package com.android.systemui.keyboard.docking.binder;

import android.content.Context;
import android.graphics.Paint;
import android.view.WindowManager;
import com.android.systemui.keyboard.docking.ui.KeyboardDockingIndicationView;
import com.android.systemui.keyboard.docking.ui.viewmodel.KeyboardDockingIndicationViewModel;
import com.android.systemui.surfaceeffects.PaintDrawCallback;
import kotlinx.coroutines.CoroutineScope;

/* loaded from: classes2.dex */
public final class KeyboardDockingIndicationViewBinder {
    public final KeyboardDockingIndicationViewBinder$drawCallback$1 drawCallback;
    public final KeyboardDockingIndicationView glowEffectView;
    public final KeyboardDockingIndicationViewBinder$stateChangedCallback$1 stateChangedCallback;
    public final KeyboardDockingIndicationViewModel viewModel;
    public final WindowManager.LayoutParams windowLayoutParams;
    public final WindowManager windowManager;

    /* JADX WARN: Type inference failed for: r1v1, types: [com.android.systemui.keyboard.docking.binder.KeyboardDockingIndicationViewBinder$drawCallback$1] */
    /* JADX WARN: Type inference failed for: r1v2, types: [com.android.systemui.keyboard.docking.binder.KeyboardDockingIndicationViewBinder$stateChangedCallback$1] */
    public KeyboardDockingIndicationViewBinder(Context context, CoroutineScope coroutineScope, KeyboardDockingIndicationViewModel keyboardDockingIndicationViewModel, WindowManager windowManager) {
        this.viewModel = keyboardDockingIndicationViewModel;
        this.windowManager = windowManager;
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        layoutParams.width = -1;
        layoutParams.height = -1;
        layoutParams.format = -3;
        layoutParams.type = 2009;
        layoutParams.setFitInsetsTypes(0);
        layoutParams.setTitle("Edge glow effect");
        layoutParams.flags = 24;
        layoutParams.setTrustedOverlay();
        this.windowLayoutParams = layoutParams;
        this.glowEffectView = new KeyboardDockingIndicationView(context, null);
        this.drawCallback = new PaintDrawCallback() { // from class: com.android.systemui.keyboard.docking.binder.KeyboardDockingIndicationViewBinder$drawCallback$1
            @Override // com.android.systemui.surfaceeffects.PaintDrawCallback
            public final void onDraw(Paint paint) {
                KeyboardDockingIndicationView keyboardDockingIndicationView = this.this$0.glowEffectView;
                keyboardDockingIndicationView.paint = paint;
                keyboardDockingIndicationView.invalidate();
            }
        };
        this.stateChangedCallback = new Object() { // from class: com.android.systemui.keyboard.docking.binder.KeyboardDockingIndicationViewBinder$stateChangedCallback$1
        };
    }
}
