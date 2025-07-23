package com.android.systemui.qs.pipeline.shared;

import java.util.Set;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public interface TilesUpgradePath {

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class DefaultSet implements TilesUpgradePath {
        public static final DefaultSet INSTANCE = new DefaultSet();

        private DefaultSet() {
        }

        public final boolean equals(Object obj) {
            return this == obj || (obj instanceof DefaultSet);
        }

        public final int hashCode() {
            return 316940361;
        }

        public final String toString() {
            return "DefaultSet";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class ReadFromSettings implements TilesUpgradePath {
        public final Set value;

        private /* synthetic */ ReadFromSettings(Set set) {
            this.value = set;
        }

        /* renamed from: box-impl, reason: not valid java name */
        public static final /* synthetic */ ReadFromSettings m2896boximpl(Set set) {
            return new ReadFromSettings(set);
        }

        public final boolean equals(Object obj) {
            return (obj instanceof ReadFromSettings) && Intrinsics.areEqual(this.value, ((ReadFromSettings) obj).value);
        }

        public final int hashCode() {
            return this.value.hashCode();
        }

        public final String toString() {
            return "ReadFromSettings(value=" + this.value + ")";
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class RestoreFromBackup implements TilesUpgradePath {
        public final Set value;

        private /* synthetic */ RestoreFromBackup(Set set) {
            this.value = set;
        }

        /* renamed from: box-impl, reason: not valid java name */
        public static final /* synthetic */ RestoreFromBackup m2897boximpl(Set set) {
            return new RestoreFromBackup(set);
        }

        public final boolean equals(Object obj) {
            return (obj instanceof RestoreFromBackup) && Intrinsics.areEqual(this.value, ((RestoreFromBackup) obj).value);
        }

        public final int hashCode() {
            return this.value.hashCode();
        }

        public final String toString() {
            return "RestoreFromBackup(value=" + this.value + ")";
        }
    }
}
