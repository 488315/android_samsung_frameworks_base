package com.android.systemui.kairos.util;

import com.android.systemui.statusbar.pipeline.wifi.data.repository.demo.model.FakeWifiEventModel;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface Either {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class First implements Either {
        public final Object value;

        private /* synthetic */ First(Object obj) {
            this.value = obj;
        }

        /* renamed from: box-impl, reason: not valid java name */
        public static final /* synthetic */ First m2571boximpl(Object obj) {
            return new First(obj);
        }

        public final boolean equals(Object obj) {
            if (obj instanceof First) {
                return Intrinsics.areEqual(this.value, ((First) obj).value);
            }
            return false;
        }

        public final int hashCode() {
            Object obj = this.value;
            if (obj == null) {
                return 0;
            }
            return obj.hashCode();
        }

        public final String toString() {
            return "First(value=" + this.value + ")";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Second implements Either {
        public final Object value;

        private /* synthetic */ Second(Object obj) {
            this.value = obj;
        }

        /* renamed from: box-impl, reason: not valid java name */
        public static final /* synthetic */ Second m2572boximpl(FakeWifiEventModel.CarrierMerged carrierMerged) {
            return new Second(carrierMerged);
        }

        public final boolean equals(Object obj) {
            if (obj instanceof Second) {
                return Intrinsics.areEqual(this.value, ((Second) obj).value);
            }
            return false;
        }

        public final int hashCode() {
            Object obj = this.value;
            if (obj == null) {
                return 0;
            }
            return obj.hashCode();
        }

        public final String toString() {
            return "Second(value=" + this.value + ")";
        }
    }
}
