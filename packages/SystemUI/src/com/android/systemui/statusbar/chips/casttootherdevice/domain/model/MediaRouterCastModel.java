package com.android.systemui.statusbar.chips.casttootherdevice.domain.model;

import androidx.compose.animation.core.TransitionKt$$ExternalSyntheticOutline0;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes3.dex */
public interface MediaRouterCastModel {

    public final class Casting implements MediaRouterCastModel {
        public final String deviceName;

        public Casting(String str) {
            this.deviceName = str;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Casting) && Intrinsics.areEqual(this.deviceName, ((Casting) obj).deviceName);
        }

        public final int hashCode() {
            String str = this.deviceName;
            if (str == null) {
                return 0;
            }
            return str.hashCode();
        }

        public final String toString() {
            return TransitionKt$$ExternalSyntheticOutline0.m(new StringBuilder("Casting(deviceName="), this.deviceName, ")");
        }
    }

    public final class DoingNothing implements MediaRouterCastModel {
        public static final DoingNothing INSTANCE = new DoingNothing();

        private DoingNothing() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof DoingNothing);
        }

        public final int hashCode() {
            return 1316565069;
        }

        public final String toString() {
            return "DoingNothing";
        }
    }
}
