package com.android.systemui.bouncer.domain.interactor;

import android.content.Context;
import android.content.res.Resources;
import android.telephony.TelephonyManager;
import android.telephony.euicc.EuiccManager;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.bouncer.data.model.SimPukInputModel;
import com.android.systemui.bouncer.data.repository.SimBouncerRepository;
import com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;

public final class SimBouncerInteractor {
    public final SharedFlowImpl _bouncerMessageChanged;
    public final Context applicationContext;
    public final CoroutineScope applicationScope;
    public final CoroutineDispatcher backgroundDispatcher;
    public final SharedFlowImpl bouncerMessageChanged;
    public final ReadonlyStateFlow errorDialogMessage;
    public final EuiccManager euiccManager;
    public final Flow isAnySimSecure;
    public final ReadonlyStateFlow isLockedEsim;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final SimBouncerRepository repository;
    public final Resources resources;
    public final ReadonlyStateFlow subId;
    public final TelephonyManager telephonyManager;

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

    public SimBouncerInteractor(Context context, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, SimBouncerRepository simBouncerRepository, TelephonyManager telephonyManager, Resources resources, KeyguardUpdateMonitor keyguardUpdateMonitor, EuiccManager euiccManager, MobileConnectionsRepository mobileConnectionsRepository) {
        this.applicationScope = coroutineScope;
        this.backgroundDispatcher = coroutineDispatcher;
        this.repository = simBouncerRepository;
        SimBouncerRepositoryImpl simBouncerRepositoryImpl = (SimBouncerRepositoryImpl) simBouncerRepository;
        this.subId = simBouncerRepositoryImpl.subscriptionId;
        mobileConnectionsRepository.isAnySimSecure();
        this.isLockedEsim = simBouncerRepositoryImpl.isLockedEsim;
        this.errorDialogMessage = simBouncerRepositoryImpl.errorDialogMessage;
        SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7);
    }

    public final void resetSimPukUserInput() {
        SimBouncerRepositoryImpl simBouncerRepositoryImpl = (SimBouncerRepositoryImpl) this.repository;
        simBouncerRepositoryImpl.getClass();
        new SimPukInputModel(null, null);
        simBouncerRepositoryImpl.getClass();
        BuildersKt.launch$default(this.applicationScope, this.backgroundDispatcher, null, new SimBouncerInteractor$resetSimPukUserInput$1(null), 2);
    }
}
