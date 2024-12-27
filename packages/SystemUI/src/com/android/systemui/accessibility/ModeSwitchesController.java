package com.android.systemui.accessibility;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.view.Display;
import com.android.systemui.accessibility.MagnificationModeSwitch;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ModeSwitchesController implements MagnificationModeSwitch.ClickListener {
    public MagnificationModeSwitch.ClickListener mClickListenerDelegate;
    public final DisplayIdIndexSupplier mSwitchSupplier;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class SwitchSupplier extends DisplayIdIndexSupplier {
        public final MagnificationModeSwitch.ClickListener mClickListener;
        public final Context mContext;

        public SwitchSupplier(Context context, DisplayManager displayManager, MagnificationModeSwitch.ClickListener clickListener) {
            super(displayManager);
            this.mContext = context;
            this.mClickListener = clickListener;
        }

        @Override // com.android.systemui.accessibility.DisplayIdIndexSupplier
        public final Object createInstance(Display display) {
            return new MagnificationModeSwitch(this.mContext.createWindowContext(display, 2039, null), this.mClickListener);
        }
    }

    public ModeSwitchesController(Context context, DisplayManager displayManager) {
        this.mSwitchSupplier = new SwitchSupplier(context, displayManager, new MagnificationModeSwitch.ClickListener() { // from class: com.android.systemui.accessibility.ModeSwitchesController$$ExternalSyntheticLambda0
            @Override // com.android.systemui.accessibility.MagnificationModeSwitch.ClickListener
            public final void onClick(int i) {
                ModeSwitchesController.this.onClick(i);
            }
        });
    }

    @Override // com.android.systemui.accessibility.MagnificationModeSwitch.ClickListener
    public final void onClick(int i) {
        MagnificationModeSwitch.ClickListener clickListener = this.mClickListenerDelegate;
        if (clickListener != null) {
            clickListener.onClick(i);
        }
    }

    public ModeSwitchesController(DisplayIdIndexSupplier displayIdIndexSupplier) {
        this.mSwitchSupplier = displayIdIndexSupplier;
    }
}
