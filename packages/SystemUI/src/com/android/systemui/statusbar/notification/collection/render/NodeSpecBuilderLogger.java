package com.android.systemui.statusbar.notification.collection.render;

import com.android.systemui.flags.Flags;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class NodeSpecBuilderLogger {
    public NodeSpecBuilderLogger(final NotifPipelineFlags notifPipelineFlags, LogBuffer logBuffer) {
        LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.collection.render.NodeSpecBuilderLogger$devLoggingEnabled$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                NotifPipelineFlags notifPipelineFlags2 = NotifPipelineFlags.this;
                notifPipelineFlags2.getClass();
                Flags.INSTANCE.getClass();
                notifPipelineFlags2.featureFlags.getClass();
                return Boolean.FALSE;
            }
        });
    }
}
