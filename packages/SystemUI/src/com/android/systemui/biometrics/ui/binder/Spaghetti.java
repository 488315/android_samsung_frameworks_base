package com.android.systemui.biometrics.ui.binder;

import android.content.Context;
import android.hardware.face.FaceManager;
import android.hardware.face.FaceSensorPropertiesInternal;
import android.view.View;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.R;
import com.android.systemui.biometrics.shared.model.BiometricModalities;
import com.android.systemui.biometrics.shared.model.BiometricModality;
import com.android.systemui.biometrics.shared.model.BiometricModalityKt;
import com.android.systemui.biometrics.ui.viewmodel.FingerprintStartMode;
import com.android.systemui.biometrics.ui.viewmodel.PromptHistoryImpl;
import com.android.systemui.biometrics.ui.viewmodel.PromptMessage;
import com.android.systemui.biometrics.ui.viewmodel.PromptViewModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.jvm.functions.Function2;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.StateFlowImpl;

/* loaded from: classes.dex */
public final class Spaghetti {
    public final Context applicationContext;
    public final CoroutineScope applicationScope;
    public Callback legacyCallback;
    public final List lockoutErrorStrings;
    public BiometricModalities modalities = new BiometricModalities(null, null, 3, null);
    public final View view;
    public final PromptViewModel viewModel;

    public interface Callback {
        void onAuthenticated();

        void onAuthenticatedAndConfirmed();

        void onButtonNegative();

        void onButtonTryAgain();

        void onContentViewMoreOptionsButtonPressed();

        void onError();

        void onStartDelayedFingerprintSensor();

        void onUseDeviceCredential();

        void onUserCanceled();
    }

    /* renamed from: com.android.systemui.biometrics.ui.binder.Spaghetti$onAuthenticationFailed$1, reason: invalid class name */
    final class AnonymousClass1 extends SuspendLambda implements Function2 {
        final /* synthetic */ BiometricModality $failedModality;
        final /* synthetic */ String $failureReason;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(String str, BiometricModality biometricModality, Continuation continuation) {
            super(2, continuation);
            this.$failureReason = str;
            this.$failedModality = biometricModality;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return Spaghetti.this.new AnonymousClass1(this.$failureReason, this.$failedModality, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((AnonymousClass1) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Type inference failed for: r7v0, types: [com.android.systemui.biometrics.ui.binder.Spaghetti$onAuthenticationFailed$1$$ExternalSyntheticLambda0] */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Spaghetti spaghetti = Spaghetti.this;
                PromptViewModel promptViewModel = spaghetti.viewModel;
                String str = this.$failureReason;
                String strAccess$asDefaultHelpMessage = BiometricViewBinderKt.access$asDefaultHelpMessage(spaghetti.modalities, spaghetti.applicationContext);
                boolean hasFingerprint = Spaghetti.this.modalities.getHasFingerprint();
                final Spaghetti spaghetti2 = Spaghetti.this;
                final BiometricModality biometricModality = this.$failedModality;
                ?? r7 = new Function2() { // from class: com.android.systemui.biometrics.ui.binder.Spaghetti$onAuthenticationFailed$1$$ExternalSyntheticLambda0
                    /* JADX WARN: Removed duplicated region for block: B:11:0x0025  */
                    @Override // kotlin.jvm.functions.Function2
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final Object invoke(Object obj2, Object obj3) {
                        boolean z;
                        BiometricModality biometricModality2;
                        PromptMessage promptMessage = (PromptMessage) obj2;
                        PromptHistoryImpl promptHistoryImpl = (PromptHistoryImpl) obj3;
                        if (spaghetti2.modalities.getHasFaceAndFingerprint() && biometricModality == (biometricModality2 = BiometricModality.Face)) {
                            promptMessage.getClass();
                            if ((promptMessage instanceof PromptMessage.Error) || promptHistoryImpl.failures.contains(biometricModality2)) {
                                z = true;
                            }
                        } else {
                            z = false;
                        }
                        return Boolean.valueOf(z);
                    }
                };
                this.label = 1;
                if (PromptViewModel.showTemporaryError$default(promptViewModel, str, strAccess$asDefaultHelpMessage, hasFingerprint, r7, biometricModality, this, 16) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.biometrics.ui.binder.Spaghetti$onAuthenticationSucceeded$1, reason: invalid class name and case insensitive filesystem */
    final class C08081 extends SuspendLambda implements Function2 {
        final /* synthetic */ int $modality;
        int label;
        final /* synthetic */ Spaghetti this$0;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C08081(int i, Spaghetti spaghetti, Continuation continuation) {
            super(2, continuation);
            this.$modality = i;
            this.this$0 = spaghetti;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return new C08081(this.$modality, this.this$0, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C08081) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Removed duplicated region for block: B:17:0x0048  */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                BiometricModality biometricModalityAsBiometricModality = BiometricModalityKt.asBiometricModality(this.$modality);
                Spaghetti spaghetti = this.this$0;
                spaghetti.getClass();
                if (biometricModalityAsBiometricModality == BiometricModality.Face) {
                    Integer numValueOf = spaghetti.modalities.getHasUdfps() ? Integer.valueOf(R.string.biometric_dialog_tap_confirm_with_face_alt_1) : spaghetti.modalities.getHasSfps() ? Integer.valueOf(R.string.biometric_dialog_tap_confirm_with_face_sfps) : null;
                    Spaghetti spaghetti2 = this.this$0;
                    PromptViewModel promptViewModel = spaghetti2.viewModel;
                    String string = numValueOf != null ? spaghetti2.applicationContext.getString(numValueOf.intValue()) : "";
                    string.getClass();
                    this.label = 1;
                    if (promptViewModel.showAuthenticated(biometricModalityAsBiometricModality, 500L, string, this) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    /* renamed from: com.android.systemui.biometrics.ui.binder.Spaghetti$onError$1, reason: invalid class name and case insensitive filesystem */
    final class C08091 extends SuspendLambda implements Function2 {
        final /* synthetic */ String $error;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C08091(String str, Continuation continuation) {
            super(2, continuation);
            this.$error = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return Spaghetti.this.new C08091(this.$error, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C08091) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        /* JADX WARN: Code restructure failed: missing block: B:14:0x004d, code lost:
        
            if (kotlinx.coroutines.DelayKt.delay(com.android.systemui.util.DelayableMarqueeTextView.DEFAULT_MARQUEE_DELAY, r10) == r0) goto L15;
         */
        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final Object invokeSuspend(Object obj) {
            C08091 c08091;
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Spaghetti spaghetti = Spaghetti.this;
                PromptViewModel promptViewModel = spaghetti.viewModel;
                String str = this.$error;
                String strAccess$asDefaultHelpMessage = BiometricViewBinderKt.access$asDefaultHelpMessage(spaghetti.modalities, spaghetti.applicationContext);
                boolean hasFingerprint = Spaghetti.this.modalities.getHasFingerprint();
                this.label = 1;
                c08091 = this;
                if (PromptViewModel.showTemporaryError$default(promptViewModel, str, strAccess$asDefaultHelpMessage, hasFingerprint, null, null, c08091, 56) != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            if (i != 1) {
                if (i != 2) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
                c08091 = this;
                Callback callback = Spaghetti.this.legacyCallback;
                if (callback != null) {
                    callback.onError();
                }
                return Unit.INSTANCE;
            }
            ResultKt.throwOnFailure(obj);
            c08091 = this;
            c08091.label = 2;
        }
    }

    /* renamed from: com.android.systemui.biometrics.ui.binder.Spaghetti$onHelp$1, reason: invalid class name and case insensitive filesystem */
    final class C08101 extends SuspendLambda implements Function2 {
        final /* synthetic */ String $help;
        int label;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C08101(String str, Continuation continuation) {
            super(2, continuation);
            this.$help = str;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Continuation create(Object obj, Continuation continuation) {
            return Spaghetti.this.new C08101(this.$help, continuation);
        }

        @Override // kotlin.jvm.functions.Function2
        public final Object invoke(Object obj, Object obj2) {
            return ((C08101) create((CoroutineScope) obj, (Continuation) obj2)).invokeSuspend(Unit.INSTANCE);
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
            int i = this.label;
            if (i == 0) {
                ResultKt.throwOnFailure(obj);
                Spaghetti spaghetti = Spaghetti.this;
                PromptViewModel promptViewModel = spaghetti.viewModel;
                String str = this.$help;
                String strAccess$asDefaultHelpMessage = BiometricViewBinderKt.access$asDefaultHelpMessage(spaghetti.modalities, spaghetti.applicationContext);
                boolean hasFingerprint = Spaghetti.this.modalities.getHasFingerprint();
                this.label = 1;
                if (PromptViewModel.showTemporaryError$default(promptViewModel, str, strAccess$asDefaultHelpMessage, hasFingerprint, null, null, this, 40) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                ResultKt.throwOnFailure(obj);
            }
            return Unit.INSTANCE;
        }
    }

    public Spaghetti(View view, PromptViewModel promptViewModel, Context context, CoroutineScope coroutineScope) {
        this.view = view;
        this.viewModel = promptViewModel;
        this.applicationContext = context;
        this.applicationScope = coroutineScope;
        List listAsList = Arrays.asList(7, 9);
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(listAsList, 10));
        Iterator it = listAsList.iterator();
        while (it.hasNext()) {
            arrayList.add(FaceManager.getErrorString(this.applicationContext, ((Number) it.next()).intValue(), 0));
        }
        this.lockoutErrorStrings = arrayList;
    }

    public final boolean ignoreUnsuccessfulEventsFrom(BiometricModality biometricModality, String str) {
        FaceSensorPropertiesInternal faceSensorPropertiesInternal;
        return this.modalities.getHasFaceAndFingerprint() && biometricModality == BiometricModality.Face && !((faceSensorPropertiesInternal = this.modalities.faceProperties) != null && faceSensorPropertiesInternal.sensorStrength == 2 && ((ArrayList) this.lockoutErrorStrings).contains(str));
    }

    public final void onAuthenticationFailed(int i, String str) {
        BiometricModality biometricModalityAsBiometricModality = BiometricModalityKt.asBiometricModality(i);
        StateFlowImpl stateFlowImpl = this.viewModel._fingerprintStartMode;
        if (stateFlowImpl.getValue() == FingerprintStartMode.Pending) {
            stateFlowImpl.setValue(FingerprintStartMode.Delayed);
        }
        CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new AnonymousClass1(str, biometricModalityAsBiometricModality, null), 7);
    }

    public final void onAuthenticationSucceeded(int i) {
        CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new C08081(i, this, null), 7);
    }

    public final void onError(int i, String str) {
        if (ignoreUnsuccessfulEventsFrom(BiometricModalityKt.asBiometricModality(i), str)) {
            return;
        }
        CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new C08091(str, null), 7);
    }

    public final void onHelp(int i, String str) {
        if (ignoreUnsuccessfulEventsFrom(BiometricModalityKt.asBiometricModality(i), "")) {
            return;
        }
        CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new C08101(str, null), 7);
    }
}
