package com.android.systemui.settings.multisim;

import com.android.systemui.settings.multisim.ui.view.MultiSIMPreferredSlotView;
import com.android.systemui.settings.multisim.ui.viewmodel.MultiSIMViewModel;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public class MultiSIMController {
    public final MultiSIMViewModel mMultiSIMViewModel;
    public final MultiSIMPreferredSlotView.SIMInfoIconManager.Factory mSIMInfoIconManagerFactory;

    public MultiSIMController(MultiSIMViewModel multiSIMViewModel, MultiSIMPreferredSlotView.SIMInfoIconManager.Factory factory) {
        this.mMultiSIMViewModel = multiSIMViewModel;
        this.mSIMInfoIconManagerFactory = factory;
    }
}
