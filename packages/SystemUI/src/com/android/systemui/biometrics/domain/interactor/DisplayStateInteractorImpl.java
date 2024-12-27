package com.android.systemui.biometrics.domain.interactor;

import android.content.Context;
import com.android.systemui.biometrics.data.repository.DisplayStateRepository;
import com.android.systemui.biometrics.data.repository.DisplayStateRepositoryImpl;
import com.android.systemui.common.coroutine.ConflatedCallbackFlow;
import com.android.systemui.display.data.repository.DisplayRepository;
import com.android.systemui.display.data.repository.DisplayRepositoryImpl;
import com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$1;
import com.android.systemui.display.data.repository.DisplayRepositoryImpl$special$$inlined$map$9;
import com.android.systemui.unfold.compat.ScreenSizeFoldProvider;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class DisplayStateInteractorImpl implements DisplayStateInteractor {
    public final ReadonlyStateFlow currentRotation;
    public final DisplayRepositoryImpl$special$$inlined$map$1 displayChanges;
    public final DisplayRepositoryImpl$special$$inlined$map$9 isDefaultDisplayOff;
    public final ReadonlyStateFlow isFolded;
    public final ReadonlyStateFlow isInRearDisplayMode;
    public final ReadonlyStateFlow isLargeScreen;
    public final ScreenSizeFoldProvider screenSizeFoldProvider;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public DisplayStateInteractorImpl(CoroutineScope coroutineScope, Context context, Executor executor, DisplayStateRepository displayStateRepository, DisplayRepository displayRepository) {
        this.screenSizeFoldProvider = new ScreenSizeFoldProvider(context);
        DisplayRepositoryImpl displayRepositoryImpl = (DisplayRepositoryImpl) displayRepository;
        this.displayChanges = displayRepositoryImpl.displayChangeEvent;
        ConflatedCallbackFlow conflatedCallbackFlow = ConflatedCallbackFlow.INSTANCE;
        DisplayStateInteractorImpl$isFolded$1 displayStateInteractorImpl$isFolded$1 = new DisplayStateInteractorImpl$isFolded$1(this, executor, null);
        conflatedCallbackFlow.getClass();
        Flow conflatedCallbackFlow2 = FlowConflatedKt.conflatedCallbackFlow(displayStateInteractorImpl$isFolded$1);
        SharingStarted.Companion.getClass();
        this.isFolded = FlowKt.stateIn(conflatedCallbackFlow2, coroutineScope, SharingStarted.Companion.Eagerly, Boolean.FALSE);
        DisplayStateRepositoryImpl displayStateRepositoryImpl = (DisplayStateRepositoryImpl) displayStateRepository;
        this.isInRearDisplayMode = displayStateRepositoryImpl.isInRearDisplayMode;
        this.currentRotation = displayStateRepositoryImpl.currentRotation;
        this.isDefaultDisplayOff = displayRepositoryImpl.defaultDisplayOff;
        this.isLargeScreen = displayStateRepositoryImpl.isLargeScreen;
    }
}
