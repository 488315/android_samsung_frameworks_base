package com.android.systemui.bouncer.ui.viewmodel;

import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import androidx.compose.runtime.collection.MutableVectorKt$$ExternalSyntheticOutline0;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public interface EntryToken extends Comparable {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ClearAll implements EntryToken {
        public final int sequenceNumber;

        public ClearAll() {
            this(0, 1, null);
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof ClearAll) && this.sequenceNumber == ((ClearAll) obj).sequenceNumber;
        }

        @Override // com.android.systemui.bouncer.ui.viewmodel.EntryToken
        public final int getSequenceNumber() {
            return this.sequenceNumber;
        }

        public final int hashCode() {
            return Integer.hashCode(this.sequenceNumber);
        }

        public final String toString() {
            return ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(this.sequenceNumber, "ClearAll(sequenceNumber=", ")");
        }

        public ClearAll(int i) {
            this.sequenceNumber = i;
        }

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public /* synthetic */ ClearAll(int r1, int r2, kotlin.jvm.internal.DefaultConstructorMarker r3) {
            /*
                r0 = this;
                r2 = r2 & 1
                if (r2 == 0) goto La
                int r1 = com.android.systemui.bouncer.ui.viewmodel.EntryToken.Companion.nextSequenceNumber
                int r2 = r1 + 1
                com.android.systemui.bouncer.ui.viewmodel.EntryToken.Companion.nextSequenceNumber = r2
            La:
                r0.<init>(r1)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.ui.viewmodel.EntryToken.ClearAll.<init>(int, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public static final /* synthetic */ Companion $$INSTANCE = null;
        public static int nextSequenceNumber;

        static {
            new Companion();
            nextSequenceNumber = 1;
        }

        private Companion() {
        }
    }

    static {
        Companion companion = Companion.$$INSTANCE;
    }

    @Override // java.lang.Comparable
    default int compareTo(Object obj) {
        return ComparisonsKt__ComparisonsKt.compareValues(Integer.valueOf(getSequenceNumber()), Integer.valueOf(((EntryToken) obj).getSequenceNumber()));
    }

    int getSequenceNumber();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Digit implements EntryToken {
        public final int input;
        public final int sequenceNumber;

        public Digit(int i, int i2) {
            this.input = i;
            this.sequenceNumber = i2;
            if (i < 0 || i >= 10) {
                throw new IllegalStateException("Check failed.");
            }
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (!(obj instanceof Digit)) {
                return false;
            }
            Digit digit = (Digit) obj;
            return this.input == digit.input && this.sequenceNumber == digit.sequenceNumber;
        }

        @Override // com.android.systemui.bouncer.ui.viewmodel.EntryToken
        public final int getSequenceNumber() {
            return this.sequenceNumber;
        }

        public final int hashCode() {
            return Integer.hashCode(this.sequenceNumber) + (Integer.hashCode(this.input) * 31);
        }

        public final String toString() {
            return MutableVectorKt$$ExternalSyntheticOutline0.m(this.input, this.sequenceNumber, "Digit(input=", ", sequenceNumber=", ")");
        }

        /* JADX WARN: Illegal instructions before constructor call */
        /*
            Code decompiled incorrectly, please refer to instructions dump.
            To view partially-correct code enable 'Show inconsistent code' option in preferences
        */
        public /* synthetic */ Digit(int r1, int r2, int r3, kotlin.jvm.internal.DefaultConstructorMarker r4) {
            /*
                r0 = this;
                r3 = r3 & 2
                if (r3 == 0) goto La
                int r2 = com.android.systemui.bouncer.ui.viewmodel.EntryToken.Companion.nextSequenceNumber
                int r3 = r2 + 1
                com.android.systemui.bouncer.ui.viewmodel.EntryToken.Companion.nextSequenceNumber = r3
            La:
                r0.<init>(r1, r2)
                return
            */
            throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.bouncer.ui.viewmodel.EntryToken.Digit.<init>(int, int, int, kotlin.jvm.internal.DefaultConstructorMarker):void");
        }
    }
}
