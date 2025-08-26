package com.android.settingslib.volume.shared.model;

import android.media.AudioSystem;
import android.support.v4.media.MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0;
import java.util.Set;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
public final class AudioStream {
    public static final Companion Companion = new Companion(null);
    public static final Set supportedStreamTypes = ArraysKt___ArraysKt.toSet(new Integer[]{0, 1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11});
    public final int value;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    private /* synthetic */ AudioStream(int i) {
        this.value = i;
    }

    /* renamed from: box-impl, reason: not valid java name */
    public static final /* synthetic */ AudioStream m990boximpl(int i) {
        return new AudioStream(i);
    }

    /* renamed from: constructor-impl, reason: not valid java name */
    public static void m991constructorimpl(int i) {
        if (!supportedStreamTypes.contains(Integer.valueOf(i))) {
            throw new IllegalArgumentException(MediaBrowserCompat$MediaBrowserImplBase$$ExternalSyntheticOutline0.m(i, "Unsupported stream=").toString());
        }
    }

    public final boolean equals(Object obj) {
        if (obj instanceof AudioStream) {
            return this.value == ((AudioStream) obj).value;
        }
        return false;
    }

    public final int hashCode() {
        return Integer.hashCode(this.value);
    }

    public final String toString() {
        return AudioSystem.streamToString(this.value);
    }
}
