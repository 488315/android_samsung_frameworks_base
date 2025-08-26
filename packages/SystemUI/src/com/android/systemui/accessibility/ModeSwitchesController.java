package com.android.systemui.accessibility;

import android.content.Context;
import android.hardware.display.DisplayManager;
import android.view.Display;
import com.android.systemui.accessibility.MagnificationModeSwitch;
import com.android.systemui.utils.windowmanager.WindowManagerProvider;
import com.android.systemui.utils.windowmanager.WindowManagerProviderImpl;
import com.android.systemui.utils.windowmanager.WindowManagerUtils;

/* loaded from: classes.dex */
public class ModeSwitchesController implements MagnificationModeSwitch.ClickListener {
    public MagnificationImpl$$ExternalSyntheticLambda2 mClickListenerDelegate;
    public final DisplayIdIndexSupplier mSwitchSupplier;

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
            Context contextCreateWindowContext = this.mContext.createWindowContext(display, 2039, null);
            ((WindowManagerProviderImpl) this.mWindowManagerProvider).getClass();
            return new MagnificationModeSwitch(contextCreateWindowContext, WindowManagerUtils.getWindowManager(contextCreateWindowContext), this.mClickListener);
        }
    }

    public ModeSwitchesController(Context context, DisplayManager displayManager, WindowManagerProvider windowManagerProvider) {
        this.mSwitchSupplier = new SwitchSupplier(context, displayManager, new MagnificationModeSwitch.ClickListener() { // from class: com.android.systemui.accessibility.ModeSwitchesController$$ExternalSyntheticLambda0
            @Override // com.android.systemui.accessibility.MagnificationModeSwitch.ClickListener
            public final void onClick(int i) {
                this.f$0.onClick(i);
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
