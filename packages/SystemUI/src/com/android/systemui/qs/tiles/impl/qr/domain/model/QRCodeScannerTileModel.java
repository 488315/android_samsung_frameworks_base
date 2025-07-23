package com.android.systemui.qs.tiles.impl.qr.domain.model;

import android.content.Intent;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface QRCodeScannerTileModel {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
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
