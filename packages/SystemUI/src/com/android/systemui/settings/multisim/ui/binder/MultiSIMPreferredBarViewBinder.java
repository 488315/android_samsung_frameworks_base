package com.android.systemui.settings.multisim.ui.binder;

import android.view.View;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.qs.bar.MultiSIMPreferredSlotBar;
import com.android.systemui.settings.multisim.ui.view.MultiSIMPreferredSlotView;
import com.android.systemui.settings.multisim.ui.viewmodel.Button;
import com.android.systemui.settings.multisim.ui.viewmodel.ButtonType;
import com.android.systemui.settings.multisim.ui.viewmodel.MultiSIMViewModel;
import com.android.systemui.settings.multisim.ui.viewmodel.MultiSIMViewModelKt;
import com.android.systemui.settings.multisim.ui.viewmodel.SlotsView;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public final class MultiSIMPreferredBarViewBinder {
    public static final MultiSIMPreferredBarViewBinder INSTANCE = new MultiSIMPreferredBarViewBinder();
    public static final String TAG = "MULTISIM-BIND";

    private MultiSIMPreferredBarViewBinder() {
    }

    public static final void access$bindButton(MultiSIMPreferredBarViewBinder multiSIMPreferredBarViewBinder, CoroutineScope coroutineScope, MultiSIMViewModel multiSIMViewModel, Button button) {
        multiSIMPreferredBarViewBinder.getClass();
        ButtonType buttonType = ((MultiSIMPreferredSlotView.PrefferedSlotButton) button).mType;
        if (buttonType == ButtonType.VOICE || buttonType == ButtonType.DATA) {
            BuildersKt.launch$default(coroutineScope, null, null, new MultiSIMPreferredBarViewBinder$bindButton$1(multiSIMViewModel, buttonType, button, null), 3);
        }
        BuildersKt.launch$default(coroutineScope, null, null, new MultiSIMPreferredBarViewBinder$bindButton$2(multiSIMViewModel, buttonType, button, null), 3);
        if (MultiSIMViewModelKt.isSIMINFO(buttonType)) {
            BuildersKt.launch$default(coroutineScope, null, null, new MultiSIMPreferredBarViewBinder$bindButton$3(multiSIMViewModel, buttonType, button, null), 3);
        } else {
            BuildersKt.launch$default(coroutineScope, null, null, new MultiSIMPreferredBarViewBinder$bindButton$4(multiSIMViewModel, buttonType, button, null), 3);
        }
        BuildersKt.launch$default(coroutineScope, null, null, new MultiSIMPreferredBarViewBinder$bindButton$5(multiSIMViewModel, buttonType, button, null), 3);
        if (MultiSIMViewModelKt.isSIMINFO(buttonType)) {
            BuildersKt.launch$default(coroutineScope, null, null, new MultiSIMPreferredBarViewBinder$bindButton$6(multiSIMViewModel, buttonType, button, null), 3);
        } else {
            BuildersKt.launch$default(coroutineScope, null, null, new MultiSIMPreferredBarViewBinder$bindButton$7(multiSIMViewModel, buttonType, button, null), 3);
        }
        if (MultiSIMViewModelKt.isSIMINFO(buttonType)) {
            BuildersKt.launch$default(coroutineScope, null, null, new MultiSIMPreferredBarViewBinder$bindButton$8(multiSIMViewModel, button, null), 3);
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final void bind(MultiSIMPreferredSlotBar multiSIMPreferredSlotBar, MultiSIMViewModel multiSIMViewModel) {
        View view = multiSIMPreferredSlotBar.mBarRootView;
        if (view == 0) {
            return;
        }
        RepeatWhenAttachedKt.repeatWhenAttached(view, EmptyCoroutineContext.INSTANCE, new MultiSIMPreferredBarViewBinder$bind$1(multiSIMViewModel, multiSIMPreferredSlotBar, (SlotsView) view, null));
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static final void bindClone(MultiSIMPreferredSlotBar multiSIMPreferredSlotBar, MultiSIMViewModel multiSIMViewModel) {
        View view = multiSIMPreferredSlotBar.mClonedBarView;
        if (view == 0) {
            return;
        }
        RepeatWhenAttachedKt.repeatWhenAttached(view, EmptyCoroutineContext.INSTANCE, new MultiSIMPreferredBarViewBinder$bindClone$1(multiSIMViewModel, multiSIMPreferredSlotBar, (SlotsView) view, null));
    }
}
