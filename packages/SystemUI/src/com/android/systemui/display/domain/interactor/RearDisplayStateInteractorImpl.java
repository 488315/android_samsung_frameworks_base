package com.android.systemui.display.domain.interactor;

import com.android.systemui.display.data.repository.DeviceStateRepository;
import com.android.systemui.display.data.repository.DeviceStateRepositoryImpl;
import com.android.systemui.display.data.repository.DisplayRepository;
import com.android.systemui.display.data.repository.DisplayRepositoryImpl;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combineTransform$$inlined$combineTransformUnsafe$FlowKt__ZipKt$1;
import kotlinx.coroutines.flow.SafeFlow;

/* loaded from: classes2.dex */
public final class RearDisplayStateInteractorImpl implements RearDisplayStateInteractor {
    public final Flow state;

    public RearDisplayStateInteractorImpl(DisplayRepository displayRepository, DeviceStateRepository deviceStateRepository, CoroutineDispatcher coroutineDispatcher) {
        this.state = FlowKt.flowOn(FlowKt.distinctUntilChanged(new SafeFlow(new FlowKt__ZipKt$combineTransform$$inlined$combineTransformUnsafe$FlowKt__ZipKt$1(new Flow[]{((DeviceStateRepositoryImpl) deviceStateRepository).state, ((DisplayRepositoryImpl) displayRepository).displayRepositoryFromLib.getDisplays()}, null, new RearDisplayStateInteractorImpl$state$1(null)))), coroutineDispatcher);
    }
}
