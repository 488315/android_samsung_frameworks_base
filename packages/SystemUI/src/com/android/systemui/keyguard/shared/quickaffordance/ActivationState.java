package com.android.systemui.keyguard.shared.quickaffordance;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public abstract class ActivationState {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Active extends ActivationState {
        public static final Active INSTANCE = new Active();

        private Active() {
            super(null);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Inactive extends ActivationState {
        public static final Inactive INSTANCE = new Inactive();

        private Inactive() {
            super(null);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class NotSupported extends ActivationState {
        public static final NotSupported INSTANCE = new NotSupported();

        private NotSupported() {
            super(null);
        }
    }

    public /* synthetic */ ActivationState(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    private ActivationState() {
    }
}
