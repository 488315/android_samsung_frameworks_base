package com.android.systemui.display.domain.interactor;

import android.view.Display;
import com.samsung.android.knox.net.vpn.KnoxVpnPolicyConstants;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes2.dex */
public interface RearDisplayStateInteractor {

    public abstract class State {

        public final class Disabled extends State {
            public static final Disabled INSTANCE = new Disabled();

            private Disabled() {
                super(null);
            }

            public final boolean equals(Object obj) {
                return this == obj || (obj instanceof Disabled);
            }

            public final int hashCode() {
                return 1381614725;
            }

            public final String toString() {
                return KnoxVpnPolicyConstants.VPN_CERT_TYPE_DISABLED;
            }
        }

        public final class Enabled extends State {
            public final Display innerDisplay;

            public Enabled(Display display) {
                super(null);
                this.innerDisplay = display;
            }

            public final boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                return (obj instanceof Enabled) && Intrinsics.areEqual(this.innerDisplay, ((Enabled) obj).innerDisplay);
            }

            public final int hashCode() {
                return this.innerDisplay.hashCode();
            }

            public final String toString() {
                return "Enabled(innerDisplay=" + this.innerDisplay + ")";
            }
        }

        public /* synthetic */ State(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private State() {
        }
    }
}
