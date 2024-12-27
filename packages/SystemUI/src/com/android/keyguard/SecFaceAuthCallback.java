package com.android.keyguard;

import com.samsung.android.bio.face.SemBioFaceManager;
import java.lang.ref.WeakReference;
import java.util.Optional;
import java.util.function.Consumer;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public final class SecFaceAuthCallback extends SemBioFaceManager.AuthenticationCallback {
    public final WeakReference mDispatcher;

    public SecFaceAuthCallback(Consumer<SecFaceMsg> consumer) {
        this.mDispatcher = new WeakReference(consumer);
    }

    public final void onAuthenticationAcquired(final int i) {
        Optional.ofNullable((Consumer) this.mDispatcher.get()).ifPresent(new Consumer() { // from class: com.android.keyguard.SecFaceAuthCallback$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((Consumer) obj).accept(SecFaceMsg.obtain(4, i, null, null));
            }
        });
    }

    public final void onAuthenticationError(int i, CharSequence charSequence) {
        Optional.ofNullable((Consumer) this.mDispatcher.get()).ifPresent(new SecFaceAuthCallback$$ExternalSyntheticLambda1(i, 0, charSequence));
    }

    public final void onAuthenticationFailed() {
        Optional.ofNullable((Consumer) this.mDispatcher.get()).ifPresent(new SecFaceAuthCallback$$ExternalSyntheticLambda3());
    }

    public final void onAuthenticationHelp(int i, CharSequence charSequence) {
        Optional.ofNullable((Consumer) this.mDispatcher.get()).ifPresent(new SecFaceAuthCallback$$ExternalSyntheticLambda1(i, 1, charSequence));
    }

    public final void onAuthenticationSucceeded(final SemBioFaceManager.AuthenticationResult authenticationResult) {
        Optional.ofNullable((Consumer) this.mDispatcher.get()).ifPresent(new Consumer() { // from class: com.android.keyguard.SecFaceAuthCallback$$ExternalSyntheticLambda0
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((Consumer) obj).accept(SecFaceMsg.obtain(2, -1, null, authenticationResult));
            }
        });
    }
}
