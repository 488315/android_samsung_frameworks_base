package com.android.systemui.biometrics.shared.model;

import com.android.systemui.biometrics.domain.model.BiometricModalities;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public interface PromptKind {

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Biometric implements PromptKind {
        public final BiometricModalities activeModalities;

        /* JADX WARN: Multi-variable type inference failed */
        public Biometric() {
            this(null, 1, 0 == true ? 1 : 0);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Biometric) && Intrinsics.areEqual(this.activeModalities, ((Biometric) obj).activeModalities);
        }

        public final int hashCode() {
            return this.activeModalities.hashCode();
        }

        public final String toString() {
            return "Biometric(activeModalities=" + this.activeModalities + ")";
        }

        public Biometric(BiometricModalities biometricModalities) {
            this.activeModalities = biometricModalities;
        }

        public /* synthetic */ Biometric(BiometricModalities biometricModalities, int i, DefaultConstructorMarker defaultConstructorMarker) {
            this((i & 1) != 0 ? new BiometricModalities(null, null, 3, null) : biometricModalities);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Password implements PromptKind {
        public static final Password INSTANCE = new Password();

        private Password() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Pattern implements PromptKind {
        public static final Pattern INSTANCE = new Pattern();

        private Pattern() {
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Pin implements PromptKind {
        public static final Pin INSTANCE = new Pin();

        private Pin() {
        }
    }
}
