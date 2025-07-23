package com.android.settingslib.volume.shared.model;

import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import androidx.compose.runtime.ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0;
import java.util.Set;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class RingerMode {
    public static final Set supportedRingerModes;
    public final int value;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
        supportedRingerModes = ArraysKt___ArraysKt.toSet(new Integer[]{0, 1, 2, 2});
    }

    private /* synthetic */ RingerMode(int i) {
        this.value = i;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ RingerMode m990boximpl(int i) {
        return new RingerMode(i);
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static void m991constructorimpl(int i) {
        if (!supportedRingerModes.contains(Integer.valueOf(i))) {
            throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Unsupported stream=").toString());
        }
    }

    /* renamed from: toString-impl, reason: not valid java name */
    public static String m992toStringimpl(int i) {
        return ParcelableSnapshotMutableState$Companion$CREATOR$1$$ExternalSyntheticOutline0.m(i, "RingerMode(value=", ")");
    }

    public final boolean equals(Object obj) {
        if (obj instanceof RingerMode) {
            return this.value == ((RingerMode) obj).value;
        }
        return false;
    }

    public final int hashCode() {
        return Integer.hashCode(this.value);
    }

    public final String toString() {
        return m992toStringimpl(this.value);
    }
}
