package com.samsung.sesl.compose.component.tokens;

import android.content.res.Configuration;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes4.dex */
public interface SeslDpProducer {

    public final class Params {
        public final Configuration configuration;

        public Params(Configuration configuration) {
            this.configuration = configuration;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return (obj instanceof Params) && Intrinsics.areEqual(this.configuration, ((Params) obj).configuration);
        }

        public final int hashCode() {
            return this.configuration.hashCode();
        }

        public final String toString() {
            return "Params(configuration=" + this.configuration + ")";
        }
    }

    /* renamed from: produce-u2uoSUM */
    float mo3334produceu2uoSUM(Params params);
}
