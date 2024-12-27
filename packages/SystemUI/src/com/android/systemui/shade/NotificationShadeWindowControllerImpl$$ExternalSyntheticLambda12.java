package com.android.systemui.shade;

import com.android.systemui.statusbar.phone.StatusBarWindowCallback;
import java.lang.ref.WeakReference;
import java.util.function.Function;

public final /* synthetic */ class NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda12 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return (StatusBarWindowCallback) ((WeakReference) obj).get();
    }
}
