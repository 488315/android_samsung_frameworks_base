package com.android.systemui.keyguard.shared.model;

import kotlin.jvm.functions.Function0;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public interface DismissAction {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class None implements DismissAction {
        public static final None INSTANCE = new None();
        public static final Function0 onDismissAction = new Function0() { // from class: com.android.systemui.keyguard.shared.model.DismissAction$None$onDismissAction$1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return KeyguardDone.IMMEDIATE;
            }
        };
        public static final DismissAction$None$onCancelAction$1 onCancelAction = DismissAction$None$onCancelAction$1.INSTANCE;
        public static final String message = "";

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
        public final Runnable getOnCancelAction() {
            return onCancelAction;
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

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
        public final Runnable getOnCancelAction() {
            return this.onCancelAction;
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

    Runnable getOnCancelAction();

    Function0 getOnDismissAction();

    boolean getWillAnimateOnLockscreen();
}
