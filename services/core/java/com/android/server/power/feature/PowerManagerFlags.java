package com.android.server.power.feature;

import android.text.TextUtils;

import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.power.feature.flags.Flags;

import java.io.PrintWriter;
import java.util.function.Supplier;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class PowerManagerFlags {
    public final FlagState mEarlyScreenTimeoutDetectorFlagState;
    public final FlagState mImproveWakelockLatency;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class FlagState {
        public boolean mEnabled;
        public boolean mEnabledSet;
        public final Supplier mFlagFunction;
        public final String mName;

        public FlagState(String str, Supplier supplier) {
            this.mName = str;
            this.mFlagFunction = supplier;
        }

        public final boolean isEnabled() {
            if (this.mEnabledSet) {
                return this.mEnabled;
            }
            boolean booleanValue = ((Boolean) this.mFlagFunction.get()).booleanValue();
            this.mEnabled = booleanValue;
            this.mEnabledSet = true;
            return booleanValue;
        }

        public final String toString() {
            String str = this.mName;
            int length = str.length();
            return TextUtils.substring(str, 39, length)
                    + ": "
                    + TextUtils.formatSimple(
                            "%" + (91 - length) + "s%s",
                            new Object[] {" ", Boolean.valueOf(isEnabled())})
                    + " (def:"
                    + this.mFlagFunction.get()
                    + ")";
        }
    }

    public PowerManagerFlags() {
        final int i = 0;
        this.mEarlyScreenTimeoutDetectorFlagState =
                new FlagState(
                        "com.android.server.power.feature.flags.enable_early_screen_timeout_detector",
                        new Supplier() { // from class:
                            // com.android.server.power.feature.PowerManagerFlags$$ExternalSyntheticLambda0
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                switch (i) {
                                    case 0:
                                        return Boolean.valueOf(
                                                Flags.enableEarlyScreenTimeoutDetector());
                                    default:
                                        return Boolean.valueOf(Flags.improveWakelockLatency());
                                }
                            }
                        });
        final int i2 = 1;
        this.mImproveWakelockLatency =
                new FlagState(
                        "com.android.server.power.feature.flags.improve_wakelock_latency",
                        new Supplier() { // from class:
                                         // com.android.server.power.feature.PowerManagerFlags$$ExternalSyntheticLambda0
                            @Override // java.util.function.Supplier
                            public final Object get() {
                                switch (i2) {
                                    case 0:
                                        return Boolean.valueOf(
                                                Flags.enableEarlyScreenTimeoutDetector());
                                    default:
                                        return Boolean.valueOf(Flags.improveWakelockLatency());
                                }
                            }
                        });
    }

    public final void dump(PrintWriter printWriter) {
        StringBuilder m$1 =
                BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(
                        printWriter, "PowerManagerFlags:", " ");
        m$1.append(this.mEarlyScreenTimeoutDetectorFlagState);
        printWriter.println(m$1.toString());
        printWriter.println(" " + this.mImproveWakelockLatency);
    }
}
