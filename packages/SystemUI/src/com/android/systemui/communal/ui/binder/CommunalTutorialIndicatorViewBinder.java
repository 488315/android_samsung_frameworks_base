package com.android.systemui.communal.ui.binder;

import android.widget.TextView;
import com.android.systemui.communal.ui.viewmodel.CommunalTutorialIndicatorViewModel;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt$repeatWhenAttached$1;
import kotlin.coroutines.CoroutineContext;
import kotlin.coroutines.EmptyCoroutineContext;

public final class CommunalTutorialIndicatorViewBinder {
    public static final CommunalTutorialIndicatorViewBinder INSTANCE = new CommunalTutorialIndicatorViewBinder();

    private CommunalTutorialIndicatorViewBinder() {
    }

    public static RepeatWhenAttachedKt$repeatWhenAttached$1 bind(TextView textView, CommunalTutorialIndicatorViewModel communalTutorialIndicatorViewModel, boolean z) {
        CommunalTutorialIndicatorViewBinder$bind$disposableHandle$1 communalTutorialIndicatorViewBinder$bind$disposableHandle$1 = new CommunalTutorialIndicatorViewBinder$bind$disposableHandle$1(communalTutorialIndicatorViewModel, z, textView, null);
        CoroutineContext coroutineContext = RepeatWhenAttachedKt.MAIN_DISPATCHER_SINGLETON;
        return RepeatWhenAttachedKt.repeatWhenAttached(textView, EmptyCoroutineContext.INSTANCE, communalTutorialIndicatorViewBinder$bind$disposableHandle$1);
    }
}
