package com.android.systemui.shade.shared.model;

import com.android.systemui.bixby2.actionresult.ActionResults;
import com.android.systemui.log.table.Diffable;
import com.android.systemui.log.table.TableLogBuffer;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes3.dex */
public abstract class ShadeMode implements Diffable {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Dual extends ShadeMode {
        public static final Dual INSTANCE = new Dual();

        private Dual() {
            super(null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Dual);
        }

        public final int hashCode() {
            return 1185394120;
        }

        public final String toString() {
            return "Dual";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Single extends ShadeMode {
        public static final Single INSTANCE = new Single();

        private Single() {
            super(null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Single);
        }

        public final int hashCode() {
            return 1416156820;
        }

        public final String toString() {
            return "Single";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Split extends ShadeMode {
        public static final Split INSTANCE = new Split();

        private Split() {
            super(null);
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof Split);
        }

        public final int hashCode() {
            return -1893773490;
        }

        public final String toString() {
            return ActionResults.RESULT_SUPPORT_SPLIT;
        }
    }

    static {
        new Companion(null);
    }

    public /* synthetic */ ShadeMode(DefaultConstructorMarker defaultConstructorMarker) {
        this();
    }

    @Override // com.android.systemui.log.table.Diffable
    public final void logDiffs(Diffable diffable, TableLogBuffer.TableRowLoggerImpl tableRowLoggerImpl) {
        tableRowLoggerImpl.logChange("shadeMode", toString());
    }

    private ShadeMode() {
    }
}
