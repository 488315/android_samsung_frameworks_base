package com.android.systemui.bouncer.ui.viewmodel;

import android.app.WallpaperManager;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import androidx.compose.ui.graphics.Color;
import androidx.compose.ui.graphics.ColorKt;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.bouncer.ui.HintInteractor;
import com.android.bouncer.ui.UpdateInteractor;
import com.android.systemui.R;
import com.android.systemui.authentication.domain.interactor.AuthenticationInteractor;
import com.android.systemui.bouncer.domain.interactor.BouncerInteractor;
import com.android.systemui.bouncer.domain.interactor.SimBouncerInteractor;
import com.android.systemui.deviceentry.domain.interactor.BiometricMessageInteractor;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryBiometricsAllowedInteractor;
import com.android.systemui.deviceentry.domain.interactor.DeviceEntryFaceAuthInteractor;
import com.android.systemui.deviceentry.domain.interactor.DeviceUnlockedInteractor;
import com.android.systemui.deviceentry.shared.model.DeviceEntryRestrictionReason;
import com.android.systemui.lifecycle.ExclusiveActivatable;
import com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel;
import com.android.systemui.util.time.SystemClock;
import kotlin.Pair;
import kotlin.Unit;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlinx.coroutines.CoroutineScopeKt;
import kotlinx.coroutines.Dispatchers;
import kotlinx.coroutines.flow.Flow;
import kotlinx.coroutines.flow.FlowCollector;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.SharedFlowImpl;
import kotlinx.coroutines.flow.SharedFlowKt;
import kotlinx.coroutines.flow.StateFlowImpl;
import kotlinx.coroutines.flow.StateFlowKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class BouncerMessageViewModel extends ExclusiveActivatable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final StateFlowImpl _isWhiteBg;
    public final StateFlowImpl _messageColor;
    public final Context applicationContext;
    public final AuthenticationInteractor authenticationInteractor;
    public final BiometricMessageInteractor biometricMessageInteractor;
    public final BouncerInteractor bouncerInteractor;
    public final SystemClock clock;
    public final DeviceEntryBiometricsAllowedInteractor deviceEntryBiometricsAllowedInteractor;
    public final DeviceUnlockedInteractor deviceUnlockedInteractor;
    public final DeviceEntryFaceAuthInteractor faceAuthInteractor;
    public final HintInteractor hintInteractor;
    public final StateFlowImpl hintMessage;
    public final BouncerMessageViewModel$special$$inlined$map$1 isLockoutMessagePresent;
    public final ReadonlyStateFlow isWhiteBg;
    public final StateFlowImpl lockoutMessage;
    public final StateFlowImpl message;
    public final ReadonlyStateFlow messageColor;
    public final SharedFlowImpl resetToDefault;
    public final SimBouncerInteractor simBouncerInteractor;
    public final UserSwitcherViewModel userSwitcherViewModel;
    public final WallpaperManager wallpaperManager;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {
        BouncerMessageViewModel create();
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[DeviceEntryRestrictionReason.values().length];
            try {
                iArr[DeviceEntryRestrictionReason.UserLockdown.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                iArr[DeviceEntryRestrictionReason.DeviceNotUnlockedSinceReboot.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                iArr[DeviceEntryRestrictionReason.PolicyLockdown.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                iArr[DeviceEntryRestrictionReason.UnattendedUpdate.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                iArr[DeviceEntryRestrictionReason.DeviceNotUnlockedSinceMainlineUpdate.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                iArr[DeviceEntryRestrictionReason.SecurityTimeout.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
            try {
                iArr[DeviceEntryRestrictionReason.StrongBiometricsLockedOut.ordinal()] = 7;
            } catch (NoSuchFieldError unused7) {
            }
            try {
                iArr[DeviceEntryRestrictionReason.NonStrongFaceLockedOut.ordinal()] = 8;
            } catch (NoSuchFieldError unused8) {
            }
            try {
                iArr[DeviceEntryRestrictionReason.NonStrongBiometricsSecurityTimeout.ordinal()] = 9;
            } catch (NoSuchFieldError unused9) {
            }
            try {
                iArr[DeviceEntryRestrictionReason.TrustAgentDisabled.ordinal()] = 10;
            } catch (NoSuchFieldError unused10) {
            }
            try {
                iArr[DeviceEntryRestrictionReason.AdaptiveAuthRequest.ordinal()] = 11;
            } catch (NoSuchFieldError unused11) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
    }

    /* JADX WARN: Type inference failed for: r4v1, types: [com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$special$$inlined$map$1] */
    public BouncerMessageViewModel(Context context, BouncerInteractor bouncerInteractor, SimBouncerInteractor simBouncerInteractor, AuthenticationInteractor authenticationInteractor, UserSwitcherViewModel userSwitcherViewModel, SystemClock systemClock, BiometricMessageInteractor biometricMessageInteractor, DeviceEntryFaceAuthInteractor deviceEntryFaceAuthInteractor, DeviceUnlockedInteractor deviceUnlockedInteractor, DeviceEntryBiometricsAllowedInteractor deviceEntryBiometricsAllowedInteractor, HintInteractor hintInteractor, UpdateInteractor updateInteractor, WallpaperManager wallpaperManager) {
        this.applicationContext = context;
        this.bouncerInteractor = bouncerInteractor;
        this.simBouncerInteractor = simBouncerInteractor;
        this.authenticationInteractor = authenticationInteractor;
        this.userSwitcherViewModel = userSwitcherViewModel;
        this.clock = systemClock;
        this.biometricMessageInteractor = biometricMessageInteractor;
        this.faceAuthInteractor = deviceEntryFaceAuthInteractor;
        this.deviceUnlockedInteractor = deviceUnlockedInteractor;
        this.deviceEntryBiometricsAllowedInteractor = deviceEntryBiometricsAllowedInteractor;
        this.hintInteractor = hintInteractor;
        this.wallpaperManager = wallpaperManager;
        final StateFlowImpl MutableStateFlow = StateFlowKt.MutableStateFlow(null);
        this.lockoutMessage = MutableStateFlow;
        this.isLockoutMessagePresent = new Flow() { // from class: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$special$$inlined$map$1

            /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
            /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$special$$inlined$map$1$2, reason: invalid class name */
            public final class AnonymousClass2 implements FlowCollector {
                public final /* synthetic */ FlowCollector $this_unsafeFlow;

                /* renamed from: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$special$$inlined$map$1$2$1, reason: invalid class name */
                public final class AnonymousClass1 extends ContinuationImpl {
                    Object L$0;
                    int label;
                    /* synthetic */ Object result;

                    public AnonymousClass1(Continuation continuation) {
                        super(continuation);
                    }

                    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                    public final Object invokeSuspend(Object obj) {
                        this.result = obj;
                        this.label |= Integer.MIN_VALUE;
                        return AnonymousClass2.this.emit(null, this);
                    }
                }

                public AnonymousClass2(FlowCollector flowCollector) {
                    this.$this_unsafeFlow = flowCollector;
                }

                /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
                /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
                @Override // kotlinx.coroutines.flow.FlowCollector
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object emit(java.lang.Object r5, kotlin.coroutines.Continuation r6) {
                    /*
                        r4 = this;
                        boolean r0 = r6 instanceof com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1
                        if (r0 == 0) goto L13
                        r0 = r6
                        com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$special$$inlined$map$1$2$1 r0 = (com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$special$$inlined$map$1.AnonymousClass2.AnonymousClass1) r0
                        int r1 = r0.label
                        r2 = -2147483648(0xffffffff80000000, float:-0.0)
                        r3 = r1 & r2
                        if (r3 == 0) goto L13
                        int r1 = r1 - r2
                        r0.label = r1
                        goto L18
                    L13:
                        com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$special$$inlined$map$1$2$1 r0 = new com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$special$$inlined$map$1$2$1
                        r0.<init>(r6)
                    L18:
                        java.lang.Object r6 = r0.result
                        kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
                        int r2 = r0.label
                        r3 = 1
                        if (r2 == 0) goto L2f
                        if (r2 != r3) goto L27
                        kotlin.ResultKt.throwOnFailure(r6)
                        goto L48
                    L27:
                        java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
                        java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
                        r4.<init>(r5)
                        throw r4
                    L2f:
                        kotlin.ResultKt.throwOnFailure(r6)
                        com.android.systemui.bouncer.ui.viewmodel.MessageViewModel r5 = (com.android.systemui.bouncer.ui.viewmodel.MessageViewModel) r5
                        if (r5 == 0) goto L38
                        r5 = r3
                        goto L39
                    L38:
                        r5 = 0
                    L39:
                        java.lang.Boolean r5 = java.lang.Boolean.valueOf(r5)
                        r0.label = r3
                        kotlinx.coroutines.flow.FlowCollector r4 = r4.$this_unsafeFlow
                        java.lang.Object r4 = r4.emit(r5, r0)
                        if (r4 != r1) goto L48
                        return r1
                    L48:
                        kotlin.Unit r4 = kotlin.Unit.INSTANCE
                        return r4
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$special$$inlined$map$1.AnonymousClass2.emit(java.lang.Object, kotlin.coroutines.Continuation):java.lang.Object");
                }
            }

            @Override // kotlinx.coroutines.flow.Flow
            public final Object collect(FlowCollector flowCollector, Continuation continuation) {
                Object collect = Flow.this.collect(new AnonymousClass2(flowCollector), continuation);
                return collect == CoroutineSingletons.COROUTINE_SUSPENDED ? collect : Unit.INSTANCE;
            }
        };
        this.message = StateFlowKt.MutableStateFlow(null);
        this.hintMessage = StateFlowKt.MutableStateFlow(null);
        StateFlowImpl MutableStateFlow2 = StateFlowKt.MutableStateFlow(Boolean.FALSE);
        this._isWhiteBg = MutableStateFlow2;
        this.isWhiteBg = FlowKt.asStateFlow(MutableStateFlow2);
        StateFlowImpl MutableStateFlow3 = StateFlowKt.MutableStateFlow(Color.m454boximpl(ColorKt.Color(context.getColor(R.color.kg_compose_pattern_dot_color))));
        this._messageColor = MutableStateFlow3;
        this.messageColor = FlowKt.asStateFlow(MutableStateFlow3);
        this.resetToDefault = SharedFlowKt.MutableSharedFlow$default(1, 0, null, 6);
    }

    /* JADX WARN: Code restructure failed: missing block: B:18:0x0071, code lost:
    
        if (r6.collect(r2, r0) != r1) goto L22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x0073, code lost:
    
        return r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:21:0x0053, code lost:
    
        if (r6.emit(r2, r0) == r1) goto L21;
     */
    /* JADX WARN: Removed duplicated region for block: B:20:0x003d  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0025  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static final java.lang.Object access$defaultBouncerMessageInitializer(final com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel r5, kotlin.coroutines.jvm.internal.ContinuationImpl r6) {
        /*
            r5.getClass()
            boolean r0 = r6 instanceof com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$defaultBouncerMessageInitializer$1
            if (r0 == 0) goto L16
            r0 = r6
            com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$defaultBouncerMessageInitializer$1 r0 = (com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$defaultBouncerMessageInitializer$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L16
            int r1 = r1 - r2
            r0.label = r1
            goto L1b
        L16:
            com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$defaultBouncerMessageInitializer$1 r0 = new com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$defaultBouncerMessageInitializer$1
            r0.<init>(r5, r6)
        L1b:
            java.lang.Object r6 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 2
            r4 = 1
            if (r2 == 0) goto L3d
            if (r2 == r4) goto L35
            if (r2 != r3) goto L2d
            kotlin.ResultKt.throwOnFailure(r6)
            goto L74
        L2d:
            java.lang.IllegalStateException r5 = new java.lang.IllegalStateException
            java.lang.String r6 = "call to 'resume' before 'invoke' with coroutine"
            r5.<init>(r6)
            throw r5
        L35:
            java.lang.Object r5 = r0.L$0
            com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel r5 = (com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel) r5
            kotlin.ResultKt.throwOnFailure(r6)
            goto L56
        L3d:
            kotlin.ResultKt.throwOnFailure(r6)
            java.lang.String r6 = "BouncerMessageViewModel"
            java.lang.String r2 = "defaultBouncerMessageInitializer()"
            android.util.Log.d(r6, r2)
            kotlinx.coroutines.flow.SharedFlowImpl r6 = r5.resetToDefault
            java.lang.Boolean r2 = java.lang.Boolean.TRUE
            r0.L$0 = r5
            r0.label = r4
            java.lang.Object r6 = r6.emit(r2, r0)
            if (r6 != r1) goto L56
            goto L73
        L56:
            com.android.systemui.authentication.domain.interactor.AuthenticationInteractor r6 = r5.authenticationInteractor
            kotlinx.coroutines.flow.Flow r6 = r6.authenticationMethod
            com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$defaultBouncerMessageInitializer$$inlined$flatMapLatest$1 r2 = new com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$defaultBouncerMessageInitializer$$inlined$flatMapLatest$1
            r4 = 0
            r2.<init>(r4, r5)
            kotlinx.coroutines.flow.internal.ChannelFlowTransformLatest r6 = kotlinx.coroutines.flow.FlowKt.transformLatest(r6, r2)
            com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$defaultBouncerMessageInitializer$3 r2 = new com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$defaultBouncerMessageInitializer$3
            r2.<init>()
            r0.L$0 = r4
            r0.label = r3
            java.lang.Object r5 = r6.collect(r2, r0)
            if (r5 != r1) goto L74
        L73:
            return r1
        L74:
            kotlin.Unit r5 = kotlin.Unit.INSTANCE
            return r5
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel.access$defaultBouncerMessageInitializer(com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel, kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    public static final Unit access$startLockoutCountdown(BouncerMessageViewModel bouncerMessageViewModel) {
        bouncerMessageViewModel.getClass();
        Log.d("BouncerMessageViewModel", "startLockoutCountdown()");
        CoroutineTracingKt.launchTraced$default(CoroutineScopeKt.CoroutineScope(Dispatchers.Default), null, null, new BouncerMessageViewModel$startLockoutCountdown$2(bouncerMessageViewModel, null), 7);
        return Unit.INSTANCE;
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final kotlin.coroutines.intrinsics.CoroutineSingletons listenForHintEvents(kotlin.coroutines.jvm.internal.ContinuationImpl r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$listenForHintEvents$1
            if (r0 == 0) goto L13
            r0 = r5
            com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$listenForHintEvents$1 r0 = (com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$listenForHintEvents$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$listenForHintEvents$1 r0 = new com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$listenForHintEvents$1
            r0.<init>(r4, r5)
        L18:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 == r3) goto L2b
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2b:
            kotlin.ResultKt.throwOnFailure(r5)
            goto L46
        L2f:
            kotlin.ResultKt.throwOnFailure(r5)
            com.android.systemui.authentication.domain.interactor.AuthenticationInteractor r5 = r4.authenticationInteractor
            kotlinx.coroutines.flow.ReadonlySharedFlow r5 = r5.onAuthenticationResult
            com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$listenForHintEvents$2 r2 = new com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$listenForHintEvents$2
            r2.<init>(r4)
            r0.label = r3
            kotlinx.coroutines.flow.SharedFlow r4 = r5.$$delegate_0
            java.lang.Object r4 = r4.collect(r2, r0)
            if (r4 != r1) goto L46
            return r1
        L46:
            kotlin.KotlinNothingValueException r4 = new kotlin.KotlinNothingValueException
            r4.<init>()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel.listenForHintEvents(kotlin.coroutines.jvm.internal.ContinuationImpl):kotlin.coroutines.intrinsics.CoroutineSingletons");
    }

    /* JADX WARN: Removed duplicated region for block: B:15:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0021  */
    @Override // com.android.systemui.lifecycle.ExclusiveActivatable
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final java.lang.Object onActivated(kotlin.coroutines.Continuation r5) {
        /*
            r4 = this;
            boolean r0 = r5 instanceof com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$onActivated$1
            if (r0 == 0) goto L13
            r0 = r5
            com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$onActivated$1 r0 = (com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$onActivated$1) r0
            int r1 = r0.label
            r2 = -2147483648(0xffffffff80000000, float:-0.0)
            r3 = r1 & r2
            if (r3 == 0) goto L13
            int r1 = r1 - r2
            r0.label = r1
            goto L18
        L13:
            com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$onActivated$1 r0 = new com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$onActivated$1
            r0.<init>(r4, r5)
        L18:
            java.lang.Object r5 = r0.result
            kotlin.coroutines.intrinsics.CoroutineSingletons r1 = kotlin.coroutines.intrinsics.CoroutineSingletons.COROUTINE_SUSPENDED
            int r2 = r0.label
            r3 = 1
            if (r2 == 0) goto L2f
            if (r2 == r3) goto L2b
            java.lang.IllegalStateException r4 = new java.lang.IllegalStateException
            java.lang.String r5 = "call to 'resume' before 'invoke' with coroutine"
            r4.<init>(r5)
            throw r4
        L2b:
            kotlin.ResultKt.throwOnFailure(r5)
            goto L41
        L2f:
            kotlin.ResultKt.throwOnFailure(r5)
            com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$onActivated$2 r5 = new com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel$onActivated$2
            r2 = 0
            r5.<init>(r4, r2)
            r0.label = r3
            java.lang.Object r4 = kotlinx.coroutines.CoroutineScopeKt.coroutineScope(r5, r0)
            if (r4 != r1) goto L41
            return r1
        L41:
            kotlin.KotlinNothingValueException r4 = new kotlin.KotlinNothingValueException
            r4.<init>()
            throw r4
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.ui.viewmodel.BouncerMessageViewModel.onActivated(kotlin.coroutines.Continuation):java.lang.Object");
    }

    public final String setSubMessage(Object obj) {
        boolean z = obj instanceof Integer;
        if (z && !Intrinsics.areEqual(obj, (Object) 0)) {
            return this.applicationContext.getString(((Integer) obj).intValue());
        }
        if (z) {
            if (Intrinsics.areEqual(obj, (Object) 0)) {
                return "";
            }
            return this.applicationContext.getString(((Number) obj).intValue());
        }
        if (!(obj instanceof Bundle)) {
            return "";
        }
        Bundle bundle = (Bundle) obj;
        return this.applicationContext.getString(bundle.getInt("SubMessageResId"), Integer.valueOf(bundle.getInt("SubMessageFormatArgs")));
    }

    public final MessageViewModel toMessage(Pair pair) {
        String string;
        Log.d("BouncerMessageViewModel", "defaultMessageBouncerMessagePair.toMessage()");
        if (((Number) pair.getFirst()).intValue() == 0) {
            string = "";
        } else {
            string = this.applicationContext.getString(((Number) pair.getFirst()).intValue());
        }
        return new MessageViewModel(string, setSubMessage(pair.getSecond()), true);
    }
}
