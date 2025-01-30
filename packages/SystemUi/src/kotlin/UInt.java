package kotlin;

import com.samsung.android.nexus.video.VideoPlayer;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes3.dex */
public final class UInt implements Comparable {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final int data;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        return Intrinsics.compare(this.data ^ VideoPlayer.MEDIA_ERROR_SYSTEM, ((UInt) obj).data ^ VideoPlayer.MEDIA_ERROR_SYSTEM);
    }

    public final boolean equals(Object obj) {
        return (obj instanceof UInt) && this.data == ((UInt) obj).data;
    }

    public final int hashCode() {
        return Integer.hashCode(this.data);
    }

    public final String toString() {
        return String.valueOf(this.data & 4294967295L);
    }
}
