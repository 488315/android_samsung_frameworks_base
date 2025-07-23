package com.android.systemui.accessibility;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.view.Display;
import com.android.systemui.accessibility.MagnificationModeSwitch;
import com.android.systemui.utils.windowmanager.WindowManagerProvider;
import com.android.systemui.utils.windowmanager.WindowManagerProviderImpl;
import com.android.systemui.utils.windowmanager.WindowManagerUtils;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class ModeSwitchesController implements MagnificationModeSwitch.ClickListener {
    public MagnificationImpl$$ExternalSyntheticLambda2 mClickListenerDelegate;
    public final DisplayIdIndexSupplier mSwitchSupplier;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class SwitchSupplier extends DisplayIdIndexSupplier {
        public final MagnificationModeSwitch.ClickListener mClickListener;
        public final Context mContext;
        public final WindowManagerProvider mWindowManagerProvider;

        public SwitchSupplier(Context context, DisplayManager displayManager, MagnificationModeSwitch.ClickListener clickListener, WindowManagerProvider windowManagerProvider) {
            super(displayManager);
            this.mContext = context;
            this.mClickListener = clickListener;
            this.mWindowManagerProvider = windowManagerProvider;
        }

        @Override // com.android.systemui.accessibility.DisplayIdIndexSupplier
        public final Object createInstance(Display display) {
            Context createWindowContext = this.mContext.createWindowContext(display, 2039, null);
            ((WindowManagerProviderImpl) this.mWindowManagerProvider).getClass();
            return new MagnificationModeSwitch(createWindowContext, WindowManagerUtils.getWindowManager(createWindowContext), this.mClickListener);
        }
    }

    public ModeSwitchesController(Context context, DisplayManager displayManager, WindowManagerProvider windowManagerProvider) {
        this.mSwitchSupplier = new SwitchSupplier(context, displayManager, new MagnificationModeSwitch.ClickListener() { // from class: com.android.systemui.accessibility.ModeSwitchesController$$ExternalSyntheticLambda0
            @Override // com.android.systemui.accessibility.MagnificationModeSwitch.ClickListener
            public final void onClick(int i) {
                ModeSwitchesController.this.onClick(i);
            }
        }, windowManagerProvider);
    }

    @Override // com.android.systemui.accessibility.MagnificationModeSwitch.ClickListener
    public final void onClick(int i) {
        MagnificationImpl$$ExternalSyntheticLambda2 magnificationImpl$$ExternalSyntheticLambda2 = this.mClickListenerDelegate;
        if (magnificationImpl$$ExternalSyntheticLambda2 != null) {
            magnificationImpl$$ExternalSyntheticLambda2.onClick(i);
        }
    }

    public ModeSwitchesController(DisplayIdIndexSupplier displayIdIndexSupplier) {
        this.mSwitchSupplier = displayIdIndexSupplier;
    }
}
