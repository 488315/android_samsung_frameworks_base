package com.android.systemui.settings.multisim;

import com.android.systemui.settings.multisim.ui.view.MultiSIMPreferredSlotView;
import com.android.systemui.settings.multisim.ui.viewmodel.MultiSIMViewModel;

/* loaded from: classes3.dex */
public class MultiSIMController {
    public final MultiSIMViewModel mMultiSIMViewModel;
    public final MultiSIMPreferredSlotView.SIMInfoIconManager.Factory mSIMInfoIconManagerFactory;

    public MultiSIMController(MultiSIMViewModel multiSIMViewModel, MultiSIMPreferredSlotView.SIMInfoIconManager.Factory factory) {
        this.mMultiSIMViewModel = multiSIMViewModel;
        this.mSIMInfoIconManagerFactory = factory;
    }
}
