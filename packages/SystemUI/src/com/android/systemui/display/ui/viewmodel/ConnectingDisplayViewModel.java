package com.android.systemui.display.ui.viewmodel;

import android.content.Context;
import com.android.server.policy.feature.flags.Flags;
import com.android.systemui.CoreStartable;
import com.android.systemui.display.domain.interactor.ConnectedDisplayInteractor;
import com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl;
import com.android.systemui.display.domain.interactor.ConnectedDisplayInteractorImpl$special$$inlined$map$3;
import com.android.systemui.display.ui.view.MirroringConfirmationDialogDelegate$Factory;
import kotlin.time.Duration;
import kotlin.time.DurationKt;
import kotlin.time.DurationUnit;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.DelayKt;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.FlowKt__ZipKt$combine$$inlined$unsafeFlow$1;

public final class ConnectingDisplayViewModel implements CoreStartable {
    public final ConnectedDisplayInteractor connectedDisplayInteractor;
    public final CoroutineScope scope;

    public ConnectingDisplayViewModel(Context context, ConnectedDisplayInteractor connectedDisplayInteractor, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, MirroringConfirmationDialogDelegate$Factory mirroringConfirmationDialogDelegate$Factory) {
        this.connectedDisplayInteractor = connectedDisplayInteractor;
        this.scope = coroutineScope;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        ConnectedDisplayInteractorImpl connectedDisplayInteractorImpl = (ConnectedDisplayInteractorImpl) this.connectedDisplayInteractor;
        ConnectedDisplayInteractorImpl$special$$inlined$map$3 connectedDisplayInteractorImpl$special$$inlined$map$3 = connectedDisplayInteractorImpl.pendingDisplay;
        Flags.FEATURE_FLAGS.getClass();
        Flow flow = connectedDisplayInteractorImpl.concurrentDisplaysInProgress;
        Duration.Companion companion = Duration.Companion;
        FlowKt.launchIn(new FlowKt__ZipKt$combine$$inlined$unsafeFlow$1(FlowKt.debounce(connectedDisplayInteractorImpl$special$$inlined$map$3, DelayKt.m2547toDelayMillisLRDsOJo(DurationKt.toDuration(200, DurationUnit.MILLISECONDS))), flow, new ConnectingDisplayViewModel$start$1(this, null)), this.scope);
    }
}
