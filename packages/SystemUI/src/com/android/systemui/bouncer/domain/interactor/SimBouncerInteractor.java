package com.android.systemui.bouncer.domain.interactor;

import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.UserHandle;
import android.telephony.SubscriptionInfo;
import android.telephony.TelephonyManager;
import android.telephony.euicc.EuiccManager;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.keyguard.ClockEventController$$ExternalSyntheticOutline0;
import com.android.keyguard.KeyguardUpdateMonitor;
import com.android.systemui.R;
import com.android.systemui.bouncer.data.repository.SimBouncerRepository;
import com.android.systemui.bouncer.data.repository.SimBouncerRepositoryImpl;
import com.android.systemui.statusbar.pipeline.mobile.data.repository.MobileConnectionsRepository;
import com.android.systemui.util.PluralMessageFormaterKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineDispatcher;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.SharingStarted;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SimBouncerInteractor {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final SharedFlowImpl _bouncerMessageChanged;
    public final Context applicationContext;
    public final CoroutineScope applicationScope;
    public final CoroutineDispatcher backgroundDispatcher;
    public final SharedFlowImpl bouncerMessageChanged;
    public final ReadonlyStateFlow errorDialogMessage;
    public final EuiccManager euiccManager;
    public final ReadonlyStateFlow isAnySimSecure;
    public final ReadonlyStateFlow isLockedEsim;
    public final KeyguardUpdateMonitor keyguardUpdateMonitor;
    public final SimBouncerRepository repository;
    public final Resources resources;
    public final ReadonlyStateFlow subId;
    public final TelephonyManager telephonyManager;

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

    public SimBouncerInteractor(Context context, CoroutineScope coroutineScope, CoroutineDispatcher coroutineDispatcher, SimBouncerRepository simBouncerRepository, TelephonyManager telephonyManager, Resources resources, KeyguardUpdateMonitor keyguardUpdateMonitor, EuiccManager euiccManager, MobileConnectionsRepository mobileConnectionsRepository) {
        this.applicationContext = context;
        this.applicationScope = coroutineScope;
        this.backgroundDispatcher = coroutineDispatcher;
        this.repository = simBouncerRepository;
        this.telephonyManager = telephonyManager;
        this.resources = resources;
        this.keyguardUpdateMonitor = keyguardUpdateMonitor;
        this.euiccManager = euiccManager;
        SimBouncerRepositoryImpl simBouncerRepositoryImpl = (SimBouncerRepositoryImpl) simBouncerRepository;
        this.subId = simBouncerRepositoryImpl.subscriptionId;
        this.isAnySimSecure = FlowKt.stateIn(mobileConnectionsRepository.isAnySimSecure(), coroutineScope, SharingStarted.Companion.WhileSubscribed$default(SharingStarted.Companion, 3), Boolean.valueOf(mobileConnectionsRepository.getIsAnySimSecure()));
        this.isLockedEsim = simBouncerRepositoryImpl.isLockedEsim;
        this.errorDialogMessage = simBouncerRepositoryImpl.errorDialogMessage;
        SharedFlowImpl MutableSharedFlow$default = SharedFlowKt.MutableSharedFlow$default(0, 0, null, 7);
        this._bouncerMessageChanged = MutableSharedFlow$default;
        this.bouncerMessageChanged = MutableSharedFlow$default;
    }

    public final void disableEsim() {
        SimBouncerRepositoryImpl simBouncerRepositoryImpl = (SimBouncerRepositoryImpl) this.repository;
        SubscriptionInfo subscriptionInfo = (SubscriptionInfo) simBouncerRepositoryImpl.activeSubscriptionInfo.$$delegate_0.getValue();
        if (subscriptionInfo == null) {
            ClockEventController$$ExternalSyntheticOutline0.m(((Number) simBouncerRepositoryImpl.subscriptionId.$$delegate_0.getValue()).intValue(), "No active subscription with subscriptionId: ", "BouncerSimInteractor");
            return;
        }
        Intent intent = new Intent("com.android.keyguard.disable_esim");
        intent.setPackage(this.applicationContext.getPackageName());
        CoroutineTracingKt.launchTraced$default(this.applicationScope, this.backgroundDispatcher, null, new SimBouncerInteractor$disableEsim$1(this, subscriptionInfo, PendingIntent.getBroadcastAsUser(this.applicationContext, 0, intent, 167772160, UserHandle.SYSTEM), null), 5);
    }

    public final String getPinPasswordErrorMessage(int i) {
        String string;
        if (i == 0) {
            string = this.resources.getString(R.string.kg_password_wrong_pin_code_pukked);
            string.getClass();
        } else if (i > 0) {
            string = PluralMessageFormaterKt.icuMessageFormat(this.resources, R.string.kg_password_default_pin_message, i);
        } else {
            string = this.resources.getString(R.string.kg_sim_pin_instructions);
            string.getClass();
        }
        return Intrinsics.areEqual(((SimBouncerRepositoryImpl) this.repository).isLockedEsim.$$delegate_0.getValue(), Boolean.TRUE) ? this.resources.getString(R.string.kg_sim_lock_esim_instructions, string) : string;
    }

    public final String getPukPasswordErrorMessage(int i, boolean z) {
        String string;
        if (i == 0) {
            string = this.resources.getString(R.string.kg_password_wrong_puk_code_dead);
            string.getClass();
        } else if (i > 0) {
            string = PluralMessageFormaterKt.icuMessageFormat(this.resources, R.string.kg_password_wrong_puk_code, i);
        } else {
            string = this.resources.getString(R.string.kg_password_puk_failed);
            string.getClass();
        }
        return z ? this.resources.getString(R.string.kg_sim_lock_esim_instructions, string) : string;
    }

    public final void resetSimPukUserInput() {
        SimBouncerRepository.setSimPukUserInput$default(this.repository, null, 3);
        CoroutineTracingKt.launchTraced$default(this.applicationScope, this.backgroundDispatcher, null, new SimBouncerInteractor$resetSimPukUserInput$1(null), 5);
    }

    /* JADX WARN: Code restructure failed: missing block: B:34:0x00c2, code lost:
    
        if (r11.emit(null, r0) == r1) goto L54;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00da, code lost:
    
        if (r13.emit(r11, r0) == r1) goto L54;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00ef, code lost:
    
        if (r11.emit(null, r0) == r1) goto L54;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0090, code lost:
    
        if (r12 == r1) goto L54;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0106, code lost:
    
        if (r12.emit(r11, r0) == r1) goto L54;
     */
    /* JADX WARN: Removed duplicated region for block: B:43:0x005a  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0026  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object verifySimPin(java.lang.String r12, kotlin.coroutines.jvm.internal.ContinuationImpl r13) {
        /*
            Method dump skipped, instructions count: 268
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.domain.interactor.SimBouncerInteractor.verifySimPin(java.lang.String, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:29:0x0165, code lost:
    
        if (r4.emit(r0, r6) == r7) goto L79;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x0198, code lost:
    
        if (r4.emit(null, r6) == r7) goto L79;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x01be, code lost:
    
        if (r4.emit(r0, r6) == r7) goto L79;
     */
    /* JADX WARN: Code restructure failed: missing block: B:38:0x01d2, code lost:
    
        if (r4.emit(null, r6) == r7) goto L79;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x009a, code lost:
    
        if (r13.emit(r0, r6) == r7) goto L79;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x00ad, code lost:
    
        if (r13.emit(r0, r6) == r7) goto L79;
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x00db, code lost:
    
        if (r13.emit(r0, r6) == r7) goto L79;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x00ee, code lost:
    
        if (r13.emit(r0, r6) == r7) goto L79;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x011d, code lost:
    
        if (r13.emit(r0, r6) == r7) goto L79;
     */
    /* JADX WARN: Removed duplicated region for block: B:12:0x0037  */
    /* JADX WARN: Removed duplicated region for block: B:15:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0041  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x004b  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0150  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x01c4  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0056  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x005b  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x0060  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:9:0x002f  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object verifySimPuk(java.lang.String r18, kotlin.coroutines.jvm.internal.ContinuationImpl r19) {
        /*
            Method dump skipped, instructions count: 498
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.domain.interactor.SimBouncerInteractor.verifySimPuk(java.lang.String, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }
}
