package com.android.systemui.keyguard.shared.model;

import kotlin.jvm.functions.Function0;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface DismissAction {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class None implements DismissAction {
        public static final String message;
        public static final DismissAction$None$onCancelAction$1 onCancelAction = null;
        public static final None INSTANCE = new None();
        public static final DismissAction$None$$ExternalSyntheticLambda0 onDismissAction = new DismissAction$None$$ExternalSyntheticLambda0();

        static {
            DismissAction$None$onCancelAction$1 dismissAction$None$onCancelAction$1 = new Runnable() { // from class: com.android.systemui.keyguard.shared.model.DismissAction$None$onCancelAction$1
                @Override // java.lang.Runnable
                public final void run() {
                }
            };
            message = "";
        }

        private None() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof None);
        }

        @Override // com.android.systemui.keyguard.shared.model.DismissAction
        public final String getMessage() {
            return message;
        }

        @Override // com.android.systemui.keyguard.shared.model.DismissAction
        public final Function0 getOnDismissAction() {
            return onDismissAction;
        }

        @Override // com.android.systemui.keyguard.shared.model.DismissAction
        public final boolean getWillAnimateOnLockscreen() {
            return false;
        }

        public final int hashCode() {
            return 1219445227;
        }

        public final String toString() {
            return "None";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class RunImmediately implements DismissAction {
        public final String message;
        public final Runnable onCancelAction;
        public final Function0 onDismissAction;
        public final boolean willAnimateOnLockscreen;

        public RunImmediately(Function0 function0, Runnable runnable, String str, boolean z) {
            this.onDismissAction = function0;
            this.onCancelAction = runnable;
            this.message = str;
            this.willAnimateOnLockscreen = z;
        }

        @Override // com.android.systemui.keyguard.shared.model.DismissAction
        public final String getMessage() {
            return this.message;
        }

        @Override // com.android.systemui.keyguard.shared.model.DismissAction
        public final Function0 getOnDismissAction() {
            return this.onDismissAction;
        }

        @Override // com.android.systemui.keyguard.shared.model.DismissAction
        public final boolean getWillAnimateOnLockscreen() {
            return this.willAnimateOnLockscreen;
        }
    }

    String getMessage();

    Function0 getOnDismissAction();

    boolean getWillAnimateOnLockscreen();
}
