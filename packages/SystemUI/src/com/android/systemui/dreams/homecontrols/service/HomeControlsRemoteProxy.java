package com.android.systemui.dreams.homecontrols.service;

import com.android.systemui.dreams.homecontrols.shared.IHomeControlsRemoteProxy;
import com.android.systemui.dreams.homecontrols.shared.IHomeControlsRemoteProxyExtKt;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.util.kotlin.FlowDumperImpl;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1;
import kotlinx.coroutines.flow.SharingStarted;

/* loaded from: classes2.dex */
public final class HomeControlsRemoteProxy extends FlowDumperImpl {
    public final FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1 componentInfo;

    public interface Factory {
        HomeControlsRemoteProxy create(IHomeControlsRemoteProxy iHomeControlsRemoteProxy);
    }

    public HomeControlsRemoteProxy(CoroutineScope coroutineScope, DumpManager dumpManager, IHomeControlsRemoteProxy iHomeControlsRemoteProxy) {
        super(dumpManager, null, 2, null);
        this.componentInfo = new FlowKt__TransformKt$filterNotNull$$inlined$unsafeTransform$1(dumpValue(FlowKt.stateIn(FlowKt.distinctUntilChanged(IHomeControlsRemoteProxyExtKt.getControlsSettings(iHomeControlsRemoteProxy)), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), null), "componentInfo"));
    }
}
