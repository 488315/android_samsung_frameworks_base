package com.android.systemui.keyguard.shared.quickaffordance;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public abstract class ActivationState {

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Active extends ActivationState {
        public static final Active INSTANCE = new Active();

        private Active() {
            super(null);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Inactive extends ActivationState {
        public static final Inactive INSTANCE = new Inactive();

        private Inactive() {
            super(null);
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class NotSupported extends ActivationState {
        public static final NotSupported INSTANCE = new NotSupported();

        private NotSupported() {
            super(null);
        }
    }

    private ActivationState() {
    }

    public /* synthetic */ ActivationState(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }
}
