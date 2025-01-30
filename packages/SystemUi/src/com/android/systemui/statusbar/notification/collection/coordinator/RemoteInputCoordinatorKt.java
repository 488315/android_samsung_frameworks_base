package com.android.systemui.statusbar.notification.collection.coordinator;

import android.util.Log;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public abstract class RemoteInputCoordinatorKt {
    public static final Lazy DEBUG$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: com.android.systemui.statusbar.notification.collection.coordinator.RemoteInputCoordinatorKt$DEBUG$2
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            return Boolean.valueOf(Log.isLoggable("RemoteInputCoordinator", 3));
        }
    });
}
