package com.google.android.material.motion;

import android.view.View;
import android.window.BackEvent;
import android.window.OnBackAnimationCallback;
import android.window.OnBackInvokedCallback;
import android.window.OnBackInvokedDispatcher;
import androidx.activity.BackEventCompat;
import java.util.Objects;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class MaterialBackOrchestrator {
    public final Api34BackCallbackDelegate backCallbackDelegate;
    public final MaterialBackHandler backHandler;
    public final View view;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Api33BackCallbackDelegate {
        public OnBackInvokedCallback onBackInvokedCallback;

        private Api33BackCallbackDelegate() {
        }

        public OnBackInvokedCallback createOnBackInvokedCallback(final MaterialBackHandler materialBackHandler) {
            Objects.requireNonNull(materialBackHandler);
            return new OnBackInvokedCallback() { // from class: com.google.android.material.motion.MaterialBackOrchestrator$Api33BackCallbackDelegate$$ExternalSyntheticLambda0
                @Override // android.window.OnBackInvokedCallback
                public final void onBackInvoked() {
                    MaterialBackHandler.this.handleBackInvoked();
                }
            };
        }

        public void startListeningForBackCallbacks(MaterialBackHandler materialBackHandler, View view, boolean z) {
            OnBackInvokedDispatcher findOnBackInvokedDispatcher;
            if (this.onBackInvokedCallback == null && (findOnBackInvokedDispatcher = view.findOnBackInvokedDispatcher()) != null) {
                OnBackInvokedCallback createOnBackInvokedCallback = createOnBackInvokedCallback(materialBackHandler);
                this.onBackInvokedCallback = createOnBackInvokedCallback;
                findOnBackInvokedDispatcher.registerOnBackInvokedCallback(z ? 1000000 : 0, createOnBackInvokedCallback);
            }
        }

        public void stopListeningForBackCallbacks(View view) {
            OnBackInvokedDispatcher findOnBackInvokedDispatcher = view.findOnBackInvokedDispatcher();
            if (findOnBackInvokedDispatcher == null) {
                return;
            }
            findOnBackInvokedDispatcher.unregisterOnBackInvokedCallback(this.onBackInvokedCallback);
            this.onBackInvokedCallback = null;
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Api34BackCallbackDelegate extends Api33BackCallbackDelegate {
        private Api34BackCallbackDelegate() {
            super();
        }

        @Override // com.google.android.material.motion.MaterialBackOrchestrator.Api33BackCallbackDelegate
        public final OnBackInvokedCallback createOnBackInvokedCallback(final MaterialBackHandler materialBackHandler) {
            return new OnBackAnimationCallback() { // from class: com.google.android.material.motion.MaterialBackOrchestrator.Api34BackCallbackDelegate.1
                @Override // android.window.OnBackAnimationCallback
                public final void onBackCancelled() {
                    if (Api34BackCallbackDelegate.this.onBackInvokedCallback != null) {
                        materialBackHandler.cancelBackProgress();
                    }
                }

                @Override // android.window.OnBackInvokedCallback
                public final void onBackInvoked() {
                    materialBackHandler.handleBackInvoked();
                }

                @Override // android.window.OnBackAnimationCallback
                public final void onBackProgressed(BackEvent backEvent) {
                    if (Api34BackCallbackDelegate.this.onBackInvokedCallback != null) {
                        materialBackHandler.updateBackProgress(new BackEventCompat(backEvent));
                    }
                }

                @Override // android.window.OnBackAnimationCallback
                public final void onBackStarted(BackEvent backEvent) {
                    if (Api34BackCallbackDelegate.this.onBackInvokedCallback != null) {
                        materialBackHandler.startBackProgress(new BackEventCompat(backEvent));
                    }
                }
            };
        }
    }

    public <T extends View & MaterialBackHandler> MaterialBackOrchestrator(T t) {
        this(t, t);
    }

    public MaterialBackOrchestrator(MaterialBackHandler materialBackHandler, View view) {
        this.backCallbackDelegate = new Api34BackCallbackDelegate();
        this.backHandler = materialBackHandler;
        this.view = view;
    }
}
