package com.android.systemui.util.kotlin;

import android.view.View;
import androidx.lifecycle.Lifecycle;
import com.android.systemui.keyguard.data.repository.C1633x38e7c866;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.shade.NotificationShadeWindowViewController$$ExternalSyntheticLambda0;
import java.util.function.Consumer;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class JavaAdapterKt {
    public static final void collectFlow(View view, C1633x38e7c866 c1633x38e7c866, NotificationShadeWindowViewController$$ExternalSyntheticLambda0 notificationShadeWindowViewController$$ExternalSyntheticLambda0) {
        RepeatWhenAttachedKt.repeatWhenAttached(view, EmptyCoroutineContext.INSTANCE, new JavaAdapterKt$collectFlow$1(Lifecycle.State.CREATED, c1633x38e7c866, notificationShadeWindowViewController$$ExternalSyntheticLambda0, null));
    }

    public static final void collectFlow(View view, Flow flow, Consumer consumer, CoroutineDispatcher coroutineDispatcher) {
        RepeatWhenAttachedKt.repeatWhenAttached(view, coroutineDispatcher, new JavaAdapterKt$collectFlow$1(Lifecycle.State.CREATED, flow, consumer, null));
    }
}
