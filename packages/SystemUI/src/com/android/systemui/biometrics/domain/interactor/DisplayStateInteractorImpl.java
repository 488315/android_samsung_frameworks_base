package com.android.systemui.biometrics.domain.interactor;

import android.content.Context;
import com.android.systemui.biometrics.data.repository.DisplayStateRepository;
import com.android.systemui.biometrics.data.repository.DisplayStateRepositoryImpl;
import com.android.systemui.display.data.repository.DisplayRepository;
import com.android.systemui.display.data.repository.DisplayRepositoryImpl;
import com.android.systemui.unfold.compat.ScreenSizeFoldProvider;
import com.android.systemui.utils.coroutines.flow.FlowConflatedKt;
import java.util.concurrent.Executor;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class DisplayStateInteractorImpl implements DisplayStateInteractor {
    public final ReadonlyStateFlow currentRotation;
    public final Flow displayChanges;
    public final Flow isDefaultDisplayOff;
    public final ReadonlyStateFlow isInRearDisplayMode;
    public final ReadonlyStateFlow isLargeScreen;
    public final ScreenSizeFoldProvider screenSizeFoldProvider;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public DisplayStateInteractorImpl(CoroutineScope coroutineScope, Context context, Executor executor, DisplayStateRepository displayStateRepository, DisplayRepository displayRepository) {
        this.screenSizeFoldProvider = new ScreenSizeFoldProvider(context);
        DisplayRepositoryImpl displayRepositoryImpl = (DisplayRepositoryImpl) displayRepository;
        this.displayChanges = displayRepositoryImpl.displayRepositoryFromLib.getDisplayChangeEvent();
        Flow conflatedCallbackFlow = FlowConflatedKt.conflatedCallbackFlow(new DisplayStateInteractorImpl$isFolded$1(this, executor, null));
        SharingStarted.Companion.getClass();
        FlowKt.stateIn(conflatedCallbackFlow, coroutineScope, SharingStarted.Companion.Eagerly, Boolean.FALSE);
        DisplayStateRepositoryImpl displayStateRepositoryImpl = (DisplayStateRepositoryImpl) displayStateRepository;
        this.isInRearDisplayMode = displayStateRepositoryImpl.isInRearDisplayMode;
        this.currentRotation = displayStateRepositoryImpl.currentRotation;
        this.isDefaultDisplayOff = displayRepositoryImpl.displayRepositoryFromLib.getDefaultDisplayOff();
        this.isLargeScreen = displayStateRepositoryImpl.isLargeScreen;
    }
}
