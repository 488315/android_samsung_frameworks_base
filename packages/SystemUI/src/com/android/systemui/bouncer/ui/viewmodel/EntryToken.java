package com.android.systemui.bouncer.ui.viewmodel;

import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import androidx.compose.runtime.collection.MutableVectorKt$$ExternalSyntheticOutline0;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public interface EntryToken extends Comparable {

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
        public /* synthetic */ ClearAll(int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
            if ((i2 & 1) != 0) {
                i = Companion.nextSequenceNumber;
                Companion.nextSequenceNumber = i + 1;
            }
            this(i);
        }
    }

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
        public /* synthetic */ Digit(int i, int i2, int i3, DefaultConstructorMarker defaultConstructorMarker) {
            if ((i3 & 2) != 0) {
                i2 = Companion.nextSequenceNumber;
                Companion.nextSequenceNumber = i2 + 1;
            }
            this(i, i2);
        }
    }
}
