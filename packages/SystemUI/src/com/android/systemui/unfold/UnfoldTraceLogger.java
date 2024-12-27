package com.android.systemui.unfold;

import android.R;
import android.content.Context;
import com.android.systemui.CoreStartable;
import com.android.systemui.unfold.data.repository.FoldStateRepository;
import com.android.systemui.unfold.system.DeviceStateRepository;
import kotlin.coroutines.CoroutineContext;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.internal.ContextScope;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class UnfoldTraceLogger implements CoreStartable {
    public final ContextScope bgScope;
    public final Context context;
    public final DeviceStateRepository deviceStateRepository;
    public final FoldStateRepository foldStateRepository;

    public UnfoldTraceLogger(Context context, FoldStateRepository foldStateRepository, CoroutineScope coroutineScope, CoroutineContext coroutineContext, DeviceStateRepository deviceStateRepository) {
        this.context = context;
        this.foldStateRepository = foldStateRepository;
        this.deviceStateRepository = deviceStateRepository;
        this.bgScope = new ContextScope(coroutineScope.getCoroutineContext().plus(coroutineContext));
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        if (!(this.context.getResources().getIntArray(R.array.preloaded_freeform_multi_window_drawables).length == 0)) {
            UnfoldTraceLogger$start$1 unfoldTraceLogger$start$1 = new UnfoldTraceLogger$start$1(this, null);
            ContextScope contextScope = this.bgScope;
            BuildersKt.launch$default(contextScope, null, null, unfoldTraceLogger$start$1, 3);
            BuildersKt.launch$default(contextScope, null, null, new UnfoldTraceLogger$start$2(this, null), 3);
            BuildersKt.launch$default(contextScope, null, null, new UnfoldTraceLogger$start$3(this, null), 3);
        }
    }
}
