package com.android.systemui.biometrics.p003ui.binder;

import android.content.Context;
import android.hardware.face.FaceManager;
import android.hardware.face.FaceSensorPropertiesInternal;
import android.os.Bundle;
import android.view.View;
import android.view.ViewPropertyAnimator;
import com.android.systemui.R;
import com.android.systemui.biometrics.AuthBiometricView;
import com.android.systemui.biometrics.AuthBiometricViewAdapter;
import com.android.systemui.biometrics.domain.model.BiometricModalities;
import com.android.systemui.biometrics.domain.model.BiometricModality;
import com.android.systemui.biometrics.domain.model.BiometricModalityKt;
import com.android.systemui.biometrics.p003ui.viewmodel.FingerprintStartMode;
import com.android.systemui.biometrics.p003ui.viewmodel.PromptViewModel;
import com.samsung.android.nexus.video.VideoPlayer;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.ResultKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.coroutines.Continuation;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.FlowKt;
import kotlinx.coroutines.flow.ReadonlyStateFlow;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class Spaghetti implements AuthBiometricViewAdapter {
    public final Context applicationContext;
    public final CoroutineScope applicationScope;
    public boolean faceFailedAtLeastOnce;
    public AuthBiometricView.Callback legacyCallback;
    public final List lockoutErrorStrings;
    public BiometricModalities modalities = new BiometricModalities(null, null, 3, null);
    public final View view;
    public final PromptViewModel viewModel;

    public Spaghetti(View view, PromptViewModel promptViewModel, Context context, CoroutineScope coroutineScope) {
        this.view = view;
        this.viewModel = promptViewModel;
        this.applicationContext = context;
        this.applicationScope = coroutineScope;
        List listOf = CollectionsKt__CollectionsKt.listOf(7, 9);
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(listOf, 10));
        Iterator it = listOf.iterator();
        while (it.hasNext()) {
            arrayList.add(FaceManager.getErrorString(this.applicationContext, ((Number) it.next()).intValue(), 0));
        }
        this.lockoutErrorStrings = arrayList;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x0037  */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0024  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object access$getHelpForSuccessfulAuthentication(Spaghetti spaghetti, BiometricModality biometricModality, Continuation continuation) {
        Spaghetti$getHelpForSuccessfulAuthentication$1 spaghetti$getHelpForSuccessfulAuthentication$1;
        Object obj;
        int i;
        Integer num;
        spaghetti.getClass();
        if (continuation instanceof Spaghetti$getHelpForSuccessfulAuthentication$1) {
            spaghetti$getHelpForSuccessfulAuthentication$1 = (Spaghetti$getHelpForSuccessfulAuthentication$1) continuation;
            int i2 = spaghetti$getHelpForSuccessfulAuthentication$1.label;
            if ((i2 & VideoPlayer.MEDIA_ERROR_SYSTEM) != 0) {
                spaghetti$getHelpForSuccessfulAuthentication$1.label = i2 - VideoPlayer.MEDIA_ERROR_SYSTEM;
                obj = spaghetti$getHelpForSuccessfulAuthentication$1.result;
                CoroutineSingletons coroutineSingletons = CoroutineSingletons.COROUTINE_SUSPENDED;
                i = spaghetti$getHelpForSuccessfulAuthentication$1.label;
                if (i != 0) {
                    ResultKt.throwOnFailure(obj);
                    if (spaghetti.modalities.getHasFaceAndFingerprint()) {
                        ReadonlyStateFlow readonlyStateFlow = spaghetti.viewModel.fingerprintStartMode;
                        spaghetti$getHelpForSuccessfulAuthentication$1.L$0 = biometricModality;
                        spaghetti$getHelpForSuccessfulAuthentication$1.label = 1;
                        obj = FlowKt.first(readonlyStateFlow, spaghetti$getHelpForSuccessfulAuthentication$1);
                        if (obj == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    }
                    num = null;
                    return num;
                }
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                biometricModality = (BiometricModality) spaghetti$getHelpForSuccessfulAuthentication$1.L$0;
                ResultKt.throwOnFailure(obj);
                if (obj != FingerprintStartMode.Pending && biometricModality == BiometricModality.Face) {
                    num = new Integer(R.string.biometric_dialog_tap_confirm_with_face);
                    return num;
                }
                num = null;
                return num;
            }
        }
        spaghetti$getHelpForSuccessfulAuthentication$1 = new Spaghetti$getHelpForSuccessfulAuthentication$1(spaghetti, continuation);
        obj = spaghetti$getHelpForSuccessfulAuthentication$1.result;
        CoroutineSingletons coroutineSingletons2 = CoroutineSingletons.COROUTINE_SUSPENDED;
        i = spaghetti$getHelpForSuccessfulAuthentication$1.label;
        if (i != 0) {
        }
        if (obj != FingerprintStartMode.Pending) {
            num = new Integer(R.string.biometric_dialog_tap_confirm_with_face);
            return num;
        }
        num = null;
        return num;
    }

    @Override // com.android.systemui.biometrics.AuthBiometricViewAdapter
    public final View asView() {
        return this.view;
    }

    @Override // com.android.systemui.biometrics.AuthBiometricViewAdapter
    public final void cancelAnimation() {
        ViewPropertyAnimator animate = this.view.animate();
        if (animate != null) {
            animate.cancel();
        }
    }

    public final boolean ignoreUnsuccessfulEventsFrom(BiometricModality biometricModality, String str) {
        if (!this.modalities.getHasFaceAndFingerprint() || biometricModality != BiometricModality.Face) {
            return false;
        }
        FaceSensorPropertiesInternal faceSensorPropertiesInternal = this.modalities.faceProperties;
        return ((faceSensorPropertiesInternal != null && faceSensorPropertiesInternal.sensorStrength == 2) && ((ArrayList) this.lockoutErrorStrings).contains(str)) ? false : true;
    }

    @Override // com.android.systemui.biometrics.AuthBiometricViewAdapter
    public final boolean isCoex() {
        return this.modalities.getHasFaceAndFingerprint();
    }

    @Override // com.android.systemui.biometrics.AuthBiometricViewAdapter
    public final void onAuthenticationFailed(int i, String str) {
        BiometricModality asBiometricModality = BiometricModalityKt.asBiometricModality(i);
        StateFlowImpl stateFlowImpl = this.viewModel._fingerprintStartMode;
        if (stateFlowImpl.getValue() == FingerprintStartMode.Pending) {
            stateFlowImpl.setValue(FingerprintStartMode.Delayed);
        }
        BuildersKt.launch$default(this.applicationScope, null, null, new Spaghetti$onAuthenticationFailed$1(this, asBiometricModality, str, null), 3);
    }

    @Override // com.android.systemui.biometrics.AuthBiometricViewAdapter
    public final void onAuthenticationSucceeded(int i) {
        BuildersKt.launch$default(this.applicationScope, null, null, new Spaghetti$onAuthenticationSucceeded$1(i, this, null), 3);
    }

    @Override // com.android.systemui.biometrics.AuthBiometricViewAdapter
    public final void onDialogAnimatedIn(boolean z) {
        PromptViewModel promptViewModel = this.viewModel;
        if (!z) {
            PromptViewModel.showAuthenticating$default(promptViewModel, null, false, 3);
            return;
        }
        StateFlowImpl stateFlowImpl = promptViewModel._fingerprintStartMode;
        if (stateFlowImpl.getValue() == FingerprintStartMode.Pending) {
            stateFlowImpl.setValue(FingerprintStartMode.Normal);
        }
        PromptViewModel.showAuthenticating$default(promptViewModel, BiometricViewBinderKt.access$asDefaultHelpMessage(this.modalities, this.applicationContext), false, 2);
    }

    @Override // com.android.systemui.biometrics.AuthBiometricViewAdapter
    public final void onError(int i, String str) {
        BiometricModality asBiometricModality = BiometricModalityKt.asBiometricModality(i);
        if (ignoreUnsuccessfulEventsFrom(asBiometricModality, str)) {
            return;
        }
        BuildersKt.launch$default(this.applicationScope, null, null, new Spaghetti$onError$1(this, asBiometricModality, str, null), 3);
    }

    @Override // com.android.systemui.biometrics.AuthBiometricViewAdapter
    public final void onHelp(int i, String str) {
        if (ignoreUnsuccessfulEventsFrom(BiometricModalityKt.asBiometricModality(i), "")) {
            return;
        }
        BuildersKt.launch$default(this.applicationScope, null, null, new Spaghetti$onHelp$1(this, str, null), 3);
    }

    @Override // com.android.systemui.biometrics.AuthBiometricViewAdapter
    public final void startTransitionToCredentialUI() {
        BuildersKt.launch$default(this.applicationScope, null, null, new Spaghetti$startTransitionToCredentialUI$1(this, null), 3);
    }

    @Override // com.android.systemui.biometrics.AuthBiometricViewAdapter
    public final void onSaveState(Bundle bundle) {
    }

    @Override // com.android.systemui.biometrics.AuthBiometricViewAdapter
    public final void restoreState(Bundle bundle) {
    }

    @Override // com.android.systemui.biometrics.AuthBiometricViewAdapter
    public final void onOrientationChanged() {
    }

    @Override // com.android.systemui.biometrics.AuthBiometricViewAdapter
    public final void requestLayout() {
    }
}
