package com.android.systemui.keyguard.shared.quickaffordance;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class ActivationState {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Active extends ActivationState {
        public static final Active INSTANCE = new Active();

        private Active() {
            super(null);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Inactive extends ActivationState {
        public static final Inactive INSTANCE = new Inactive();

        private Inactive() {
            super(null);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
