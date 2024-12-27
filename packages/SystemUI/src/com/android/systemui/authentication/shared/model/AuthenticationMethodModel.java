package com.android.systemui.authentication.shared.model;

import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public abstract class AuthenticationMethodModel {
    public final boolean isSecure;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class AdminLock extends AuthenticationMethodModel {
        public static final AdminLock INSTANCE = new AdminLock();

        private AdminLock() {
            super(true, null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof AdminLock);
        }

        public final int hashCode() {
            return 152706117;
        }

        public final String toString() {
            return "AdminLock";
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class FMM extends AuthenticationMethodModel {
        public static final FMM INSTANCE = new FMM();

        private FMM() {
            super(true, null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof FMM);
        }

        public final int hashCode() {
            return 829891569;
        }

        public final String toString() {
            return "FMM";
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class ForgotPassword extends AuthenticationMethodModel {
        public static final ForgotPassword INSTANCE = new ForgotPassword();

        private ForgotPassword() {
            super(true, null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof ForgotPassword);
        }

        public final int hashCode() {
            return -1353198957;
        }

        public final String toString() {
            return "ForgotPassword";
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class KNOXGUARD extends AuthenticationMethodModel {
        public static final KNOXGUARD INSTANCE = new KNOXGUARD();

        private KNOXGUARD() {
            super(true, null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof KNOXGUARD);
        }

        public final int hashCode() {
            return -1806887228;
        }

        public final String toString() {
            return "KNOXGUARD";
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class None extends AuthenticationMethodModel {
        public static final None INSTANCE = new None();

        private None() {
            super(false, null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof None);
        }

        public final int hashCode() {
            return -42893011;
        }

        public final String toString() {
            return "None";
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Password extends AuthenticationMethodModel {
        public static final Password INSTANCE = new Password();

        private Password() {
            super(true, null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Password);
        }

        public final int hashCode() {
            return -302806512;
        }

        public final String toString() {
            return "Password";
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Pattern extends AuthenticationMethodModel {
        public static final Pattern INSTANCE = new Pattern();

        private Pattern() {
            super(true, null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Pattern);
        }

        public final int hashCode() {
            return -840115845;
        }

        public final String toString() {
            return "Pattern";
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Permanent extends AuthenticationMethodModel {
        public static final Permanent INSTANCE = new Permanent();

        private Permanent() {
            super(true, null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Permanent);
        }

        public final int hashCode() {
            return 799568857;
        }

        public final String toString() {
            return "Permanent";
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Pin extends AuthenticationMethodModel {
        public static final Pin INSTANCE = new Pin();

        private Pin() {
            super(true, null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Pin);
        }

        public final int hashCode() {
            return 829902080;
        }

        public final String toString() {
            return "Pin";
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class RMM extends AuthenticationMethodModel {
        public static final RMM INSTANCE = new RMM();

        private RMM() {
            super(true, null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof RMM);
        }

        public final int hashCode() {
            return 829903101;
        }

        public final String toString() {
            return "RMM";
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class SKTCarrierLock extends AuthenticationMethodModel {
        public static final SKTCarrierLock INSTANCE = new SKTCarrierLock();

        private SKTCarrierLock() {
            super(true, null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof SKTCarrierLock);
        }

        public final int hashCode() {
            return -1665473220;
        }

        public final String toString() {
            return "SKTCarrierLock";
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Sim extends AuthenticationMethodModel {
        public static final Sim INSTANCE = new Sim();

        private Sim() {
            super(true, null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Sim);
        }

        public final int hashCode() {
            return 829904962;
        }

        public final String toString() {
            return "Sim";
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class SmartcardPIN extends AuthenticationMethodModel {
        public static final SmartcardPIN INSTANCE = new SmartcardPIN();

        private SmartcardPIN() {
            super(true, null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof SmartcardPIN);
        }

        public final int hashCode() {
            return -673041551;
        }

        public final String toString() {
            return "SmartcardPIN";
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Swipe extends AuthenticationMethodModel {
        public static final Swipe INSTANCE = new Swipe();

        private Swipe() {
            super(false, null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Swipe);
        }

        public final int hashCode() {
            return -1324831771;
        }

        public final String toString() {
            return "Swipe";
        }
    }

    public /* synthetic */ AuthenticationMethodModel(boolean z, DefaultConstructorMarker defaultConstructorMarker) {
        this(z);
    }

    private AuthenticationMethodModel(boolean z) {
        this.isSecure = z;
    }
}
