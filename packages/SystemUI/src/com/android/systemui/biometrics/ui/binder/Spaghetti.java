package com.android.systemui.biometrics.ui.binder;

import android.content.Context;
import android.hardware.face.FaceManager;
import android.hardware.face.FaceSensorPropertiesInternal;
import android.view.View;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.systemui.biometrics.shared.model.BiometricModalities;
import com.android.systemui.biometrics.shared.model.BiometricModality;
import com.android.systemui.biometrics.shared.model.BiometricModalityKt;
import com.android.systemui.biometrics.ui.viewmodel.FingerprintStartMode;
import com.android.systemui.biometrics.ui.viewmodel.PromptViewModel;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlinx.coroutines.CoroutineScope;
import kotlinx.coroutines.flow.StateFlowImpl;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class Spaghetti {
    public final Context applicationContext;
    public final CoroutineScope applicationScope;
    public Callback legacyCallback;
    public final List lockoutErrorStrings;
    public BiometricModalities modalities = new BiometricModalities(null, null, 3, null);
    public final View view;
    public final PromptViewModel viewModel;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    public Spaghetti(View view, PromptViewModel promptViewModel, Context context, CoroutineScope coroutineScope) {
        this.view = view;
        this.viewModel = promptViewModel;
        this.applicationContext = context;
        this.applicationScope = coroutineScope;
        List asList = Arrays.asList(7, 9);
        ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(asList, 10));
        Iterator it = asList.iterator();
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
        BiometricModality asBiometricModality = BiometricModalityKt.asBiometricModality(i);
        StateFlowImpl stateFlowImpl = this.viewModel._fingerprintStartMode;
        if (stateFlowImpl.getValue() == FingerprintStartMode.Pending) {
            stateFlowImpl.setValue(FingerprintStartMode.Delayed);
        }
        CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new Spaghetti$onAuthenticationFailed$1(this, str, asBiometricModality, null), 7);
    }

    public final void onAuthenticationSucceeded(int i) {
        CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new Spaghetti$onAuthenticationSucceeded$1(i, this, null), 7);
    }

    public final void onError(int i, String str) {
        if (ignoreUnsuccessfulEventsFrom(BiometricModalityKt.asBiometricModality(i), str)) {
            return;
        }
        CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new Spaghetti$onError$1(this, str, null), 7);
    }

    public final void onHelp(int i, String str) {
        if (ignoreUnsuccessfulEventsFrom(BiometricModalityKt.asBiometricModality(i), "")) {
            return;
        }
        CoroutineTracingKt.launchTraced$default(this.applicationScope, null, null, new Spaghetti$onHelp$1(this, str, null), 7);
    }
}
