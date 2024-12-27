package com.android.systemui.bouncer.ui.viewmodel;

import androidx.compose.foundation.lazy.LazyListMeasuredItem$$ExternalSyntheticOutline0;
import kotlin.comparisons.ComparisonsKt__ComparisonsKt;

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
            return LazyListMeasuredItem$$ExternalSyntheticOutline0.m(this.sequenceNumber, "ClearAll(sequenceNumber=", ")");
        }

        public ClearAll(int i) {
            this.sequenceNumber = i;
        }

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
}
