package com.android.systemui.qs.tiles.impl.qr.domain.model;

import android.content.Intent;
import kotlin.jvm.internal.Intrinsics;

public interface QRCodeScannerTileModel {

    public final class Available implements QRCodeScannerTileModel {
        public final Intent intent;

        public Available(Intent intent) {
            this.intent = intent;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Available) && Intrinsics.areEqual(this.intent, ((Available) obj).intent);
        }

        public final int hashCode() {
            return this.intent.hashCode();
        }

        public final String toString() {
            return "Available(intent=" + this.intent + ")";
        }
    }

    public final class TemporarilyUnavailable implements QRCodeScannerTileModel {
        public static final TemporarilyUnavailable INSTANCE = new TemporarilyUnavailable();

        private TemporarilyUnavailable() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof TemporarilyUnavailable);
        }

        public final int hashCode() {
            return -684068981;
        }

        public final String toString() {
            return "TemporarilyUnavailable";
        }
    }
}
