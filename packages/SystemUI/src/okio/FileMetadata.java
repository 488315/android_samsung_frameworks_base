package okio;

import java.util.ArrayList;
import java.util.Map;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.collections.MapsKt__MapsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.reflect.KClass;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class FileMetadata {
    public final Long createdAtMillis;
    public final Map extras;
    public final boolean isDirectory;
    public final boolean isRegularFile;
    public final Long lastAccessedAtMillis;
    public final Long lastModifiedAtMillis;
    public final Long size;

    public FileMetadata() {
        this(false, false, null, null, null, null, null, null, 255, null);
    }

    public final String toString() {
        ArrayList arrayList = new ArrayList();
        if (this.isRegularFile) {
            arrayList.add("isRegularFile");
        }
        if (this.isDirectory) {
            arrayList.add("isDirectory");
        }
        Long l = this.size;
        if (l != null) {
            arrayList.add("byteCount=" + l);
        }
        Long l2 = this.createdAtMillis;
        if (l2 != null) {
            arrayList.add("createdAt=" + l2);
        }
        Long l3 = this.lastModifiedAtMillis;
        if (l3 != null) {
            arrayList.add("lastModifiedAt=" + l3);
        }
        Long l4 = this.lastAccessedAtMillis;
        if (l4 != null) {
            arrayList.add("lastAccessedAt=" + l4);
        }
        if (!this.extras.isEmpty()) {
            arrayList.add("extras=" + this.extras);
        }
        return CollectionsKt___CollectionsKt.joinToString$default(arrayList, ", ", "FileMetadata(", ")", null, 56);
    }

    public FileMetadata(boolean z, boolean z2, Path path, Long l, Long l2, Long l3, Long l4, Map<KClass, ? extends Object> map) {
        this.isRegularFile = z;
        this.isDirectory = z2;
        this.size = l;
        this.createdAtMillis = l2;
        this.lastModifiedAtMillis = l3;
        this.lastAccessedAtMillis = l4;
        this.extras = MapsKt__MapsKt.toMap(map);
    }

    public /* synthetic */ FileMetadata(boolean z, boolean z2, Path path, Long l, Long l2, Long l3, Long l4, Map map, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? false : z, (i & 2) != 0 ? false : z2, (i & 4) != 0 ? null : path, (i & 8) != 0 ? null : l, (i & 16) != 0 ? null : l2, (i & 32) != 0 ? null : l3, (i & 64) != 0 ? null : l4, (i & 128) != 0 ? MapsKt__MapsKt.emptyMap() : map);
    }
}
