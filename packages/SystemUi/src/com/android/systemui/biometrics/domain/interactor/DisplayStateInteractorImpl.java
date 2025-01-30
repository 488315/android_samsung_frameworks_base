package com.android.systemui.biometrics.domain.interactor;

import android.content.Context;
import com.android.systemui.biometrics.data.repository.RearDisplayStateRepository;
import com.android.systemui.biometrics.data.repository.RearDisplayStateRepositoryImpl;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.unfold.compat.ScreenSizeFoldProvider;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class DisplayStateInteractorImpl implements DisplayStateInteractor {
    public final ReadonlyStateFlow isFolded;
    public final ReadonlyStateFlow isInRearDisplayMode;
    public final ScreenSizeFoldProvider screenSizeFoldProvider;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public DisplayStateInteractorImpl(CoroutineScope coroutineScope, Context context, Executor executor, RearDisplayStateRepository rearDisplayStateRepository) {
        this.screenSizeFoldProvider = new ScreenSizeFoldProvider(context);
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        DisplayStateInteractorImpl$isFolded$1 displayStateInteractorImpl$isFolded$1 = new DisplayStateInteractorImpl$isFolded$1(this, executor, null);
        conflatedCallbackFlow.getClass();
        Flow conflatedCallbackFlow2 = ConflatedCallbackFlow.conflatedCallbackFlow(displayStateInteractorImpl$isFolded$1);
        SharingStarted.Companion.getClass();
        this.isFolded = FlowKt.stateIn(conflatedCallbackFlow2, coroutineScope, SharingStarted.Companion.Eagerly, Boolean.FALSE);
        this.isInRearDisplayMode = ((RearDisplayStateRepositoryImpl) rearDisplayStateRepository).isInRearDisplayMode;
    }
}
