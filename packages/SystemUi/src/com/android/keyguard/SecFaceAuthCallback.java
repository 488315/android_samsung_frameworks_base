package com.android.keyguard;

import com.samsung.android.bio.face.SemBioFaceManager;
import java.lang.ref.WeakReference;
import java.util.Optional;
import java.util.function.Consumer;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
        Optional.ofNullable((Consumer) this.mDispatcher.get()).ifPresent(new SecFaceAuthCallback$$ExternalSyntheticLambda0(i, 0, charSequence));
    }

    public final void onAuthenticationFailed() {
        Optional.ofNullable((Consumer) this.mDispatcher.get()).ifPresent(new SecFaceAuthCallback$$ExternalSyntheticLambda3());
    }

    public final void onAuthenticationHelp(int i, CharSequence charSequence) {
        Optional.ofNullable((Consumer) this.mDispatcher.get()).ifPresent(new SecFaceAuthCallback$$ExternalSyntheticLambda0(i, 1, charSequence));
    }

    public final void onAuthenticationSucceeded(final SemBioFaceManager.AuthenticationResult authenticationResult) {
        Optional.ofNullable((Consumer) this.mDispatcher.get()).ifPresent(new Consumer() { // from class: com.android.keyguard.SecFaceAuthCallback$$ExternalSyntheticLambda1
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                ((Consumer) obj).accept(SecFaceMsg.obtain(2, -1, null, authenticationResult));
            }
        });
    }
}
