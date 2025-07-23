package com.android.systemui.plugins;

import android.os.IBinder;
import android.view.View;
import com.android.systemui.plugins.annotations.ProvidesInterface;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
@ProvidesInterface(action = "com.android.systemui.action.PLUGIN_AUTH_CONTEXT", version = 1)
/* loaded from: classes2.dex */
public interface AuthContextPlugin extends Plugin {
    public static final String ACTION = "com.android.systemui.action.PLUGIN_AUTH_CONTEXT";
    public static final Companion Companion = Companion.$$INSTANCE;
    public static final int VERSION = 1;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        static final /* synthetic */ Companion $$INSTANCE = new Companion();
        public static final String ACTION = "com.android.systemui.action.PLUGIN_AUTH_CONTEXT";
        public static final int VERSION = 1;

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Saucier {
        IBinder getSauce(String str);
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface SensitiveSurface {

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class BiometricPrompt implements SensitiveSurface {
            public static final int $stable = 8;
            private final boolean isCredential;
            private final View view;

            public BiometricPrompt() {
                this(null, false, 3, 0 == true ? 1 : 0);
            }

            public static /* synthetic */ BiometricPrompt copy$default(BiometricPrompt biometricPrompt, View view, boolean z, int i, Object obj) {
                if ((i & 1) != 0) {
                    view = biometricPrompt.view;
                }
                if ((i & 2) != 0) {
                    z = biometricPrompt.isCredential;
                }
                return biometricPrompt.copy(view, z);
            }

            public final View component1() {
                return this.view;
            }

            public final boolean component2() {
                return this.isCredential;
            }

            public final BiometricPrompt copy(View view, boolean z) {
                return new BiometricPrompt(view, z);
            }

            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (!(obj instanceof BiometricPrompt)) {
                    return false;
                }
                BiometricPrompt biometricPrompt = (BiometricPrompt) obj;
                return Intrinsics.areEqual(this.view, biometricPrompt.view) && this.isCredential == biometricPrompt.isCredential;
            }

            public final View getView() {
                return this.view;
            }

            public int hashCode() {
                View view = this.view;
                return Boolean.hashCode(this.isCredential) + ((view == null ? 0 : view.hashCode()) * 31);
            }

            public final boolean isCredential() {
                return this.isCredential;
            }

            public String toString() {
                return "BiometricPrompt(view=" + this.view + ", isCredential=" + this.isCredential + ")";
            }

            public BiometricPrompt(View view, boolean z) {
                this.view = view;
                this.isCredential = z;
            }

            public /* synthetic */ BiometricPrompt(View view, boolean z, int i, DefaultConstructorMarker defaultConstructorMarker) {
                this((i & 1) != 0 ? null : view, (i & 2) != 0 ? false : z);
            }
        }

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class LockscreenBouncer implements SensitiveSurface {
            public static final int $stable = 8;
            private final View view;

            public LockscreenBouncer() {
                this(null, 1, 0 == true ? 1 : 0);
            }

            public static /* synthetic */ LockscreenBouncer copy$default(LockscreenBouncer lockscreenBouncer, View view, int i, Object obj) {
                if ((i & 1) != 0) {
                    view = lockscreenBouncer.view;
                }
                return lockscreenBouncer.copy(view);
            }

            public final View component1() {
                return this.view;
            }

            public final LockscreenBouncer copy(View view) {
                return new LockscreenBouncer(view);
            }

            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof LockscreenBouncer) && Intrinsics.areEqual(this.view, ((LockscreenBouncer) obj).view);
            }

            public final View getView() {
                return this.view;
            }

            public int hashCode() {
                View view = this.view;
                if (view == null) {
                    return 0;
                }
                return view.hashCode();
            }

            public String toString() {
                return "LockscreenBouncer(view=" + this.view + ")";
            }

            public LockscreenBouncer(View view) {
                this.view = view;
            }

            public /* synthetic */ LockscreenBouncer(View view, int i, DefaultConstructorMarker defaultConstructorMarker) {
                this((i & 1) != 0 ? null : view);
            }
        }
    }

    void activated(Saucier saucier);

    void onHidingSensitiveSurface(SensitiveSurface sensitiveSurface);

    void onShowingSensitiveSurface(SensitiveSurface sensitiveSurface);
}
