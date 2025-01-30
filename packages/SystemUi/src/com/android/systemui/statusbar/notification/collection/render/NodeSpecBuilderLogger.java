package com.android.systemui.statusbar.notification.collection.render;

import com.android.systemui.flags.Flags;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.statusbar.notification.NotifPipelineFlags;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
