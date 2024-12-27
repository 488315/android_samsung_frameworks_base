package com.android.systemui.lifecycle;

import android.view.View;
import com.android.systemui.util.Assert;
import kotlin.jvm.internal.Ref$ObjectRef;
import kotlinx.coroutines.DisposableHandle;

public final class RepeatWhenAttachedKt$repeatWhenAttached$1 implements DisposableHandle {
    public final /* synthetic */ Ref$ObjectRef $lifecycleOwner;
    public final /* synthetic */ RepeatWhenAttachedKt$repeatWhenAttached$onAttachListener$1 $onAttachListener;
    public final /* synthetic */ View $view;

    public RepeatWhenAttachedKt$repeatWhenAttached$1(Ref$ObjectRef<ViewLifecycleOwner> ref$ObjectRef, View view, RepeatWhenAttachedKt$repeatWhenAttached$onAttachListener$1 repeatWhenAttachedKt$repeatWhenAttached$onAttachListener$1) {
        this.$lifecycleOwner = ref$ObjectRef;
        this.$view = view;
        this.$onAttachListener = repeatWhenAttachedKt$repeatWhenAttached$onAttachListener$1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // kotlinx.coroutines.DisposableHandle
    public final void dispose() {
        Assert.isMainThread();
        Ref$ObjectRef ref$ObjectRef = this.$lifecycleOwner;
        ViewLifecycleOwner viewLifecycleOwner = (ViewLifecycleOwner) ref$ObjectRef.element;
        if (viewLifecycleOwner != null) {
            viewLifecycleOwner.onDestroy();
        }
        ref$ObjectRef.element = null;
        this.$view.removeOnAttachStateChangeListener(this.$onAttachListener);
    }
}
