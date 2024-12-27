package com.android.systemui.shade;

import com.android.systemui.statusbar.phone.StatusBarWindowCallback;
import java.lang.ref.WeakReference;
import java.util.function.Function;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final /* synthetic */ class NotificationShadeWindowControllerImpl$$ExternalSyntheticLambda12 implements Function {
    @Override // java.util.function.Function
    public final Object apply(Object obj) {
        return (StatusBarWindowCallback) ((WeakReference) obj).get();
    }
}
