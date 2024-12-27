package com.android.systemui.keyguard.shared.quickaffordance;

import kotlin.jvm.internal.DefaultConstructorMarker;

public abstract class ActivationState {

    public final class Active extends ActivationState {
        public static final Active INSTANCE = new Active();

        private Active() {
            super(null);
        }
    }

    public final class Inactive extends ActivationState {
        public static final Inactive INSTANCE = new Inactive();

        private Inactive() {
            super(null);
        }
    }

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
