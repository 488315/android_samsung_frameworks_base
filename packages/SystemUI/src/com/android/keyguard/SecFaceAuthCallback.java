package com.android.keyguard;

import com.samsung.android.bio.face.SemBioFaceManager;
import java.lang.ref.WeakReference;
import java.util.Optional;
import java.util.function.Consumer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class SecFaceAuthCallback extends SemBioFaceManager.AuthenticationCallback {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final WeakReference mDispatcher;

    public SecFaceAuthCallback(Consumer<SecFaceMsg> consumer) {
        this.mDispatcher = new WeakReference(consumer);
    }

    public final void onAuthenticationAcquired(final int i) {
        Optional.ofNullable((Consumer) this.mDispatcher.get()).ifPresent(new Consumer() { // from class: com.android.keyguard.SecFaceAuthCallback$$ExternalSyntheticLambda2
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                int i2 = i;
                int i3 = SecFaceAuthCallback.$r8$clinit;
                ((Consumer) obj).accept(SecFaceMsg.obtain(4, i2, null, null));
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
                SemBioFaceManager.AuthenticationResult authenticationResult2 = authenticationResult;
                int i = SecFaceAuthCallback.$r8$clinit;
                ((Consumer) obj).accept(SecFaceMsg.obtain(2, -1, null, authenticationResult2));
            }
        });
    }
}
