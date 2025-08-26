package okio.internal;

import com.samsung.android.knox.net.nap.NetworkAnalyticsConstants;
import java.util.ArrayList;
import java.util.List;
import kotlin.jvm.internal.DefaultConstructorMarker;
import okio.Path;

/* loaded from: classes4.dex */
public final class ZipEntry {
    public final Path canonicalPath;
    public final List children;
    public final String comment;
    public final long compressedSize;
    public final int compressionMethod;
    public final long crc;
    public final int dosLastModifiedAtDate;
    public final int dosLastModifiedAtTime;
    public final Integer extendedCreatedAtSeconds;
    public final Integer extendedLastAccessedAtSeconds;
    public final Integer extendedLastModifiedAtSeconds;
    public final boolean isDirectory;
    public final Long ntfsCreatedAtFiletime;
    public final Long ntfsLastAccessedAtFiletime;
    public final Long ntfsLastModifiedAtFiletime;
    public final long offset;
    public final long size;

    public ZipEntry(Path path, boolean z, String str, long j, long j2, long j3, int i, long j4, int i2, int i3, Long l, Long l2, Long l3, Integer num, Integer num2, Integer num3) {
        this.canonicalPath = path;
        this.isDirectory = z;
        this.comment = str;
        this.crc = j;
        this.compressedSize = j2;
        this.size = j3;
        this.compressionMethod = i;
        this.offset = j4;
        this.dosLastModifiedAtDate = i2;
        this.dosLastModifiedAtTime = i3;
        this.ntfsLastModifiedAtFiletime = l;
        this.ntfsLastAccessedAtFiletime = l2;
        this.ntfsCreatedAtFiletime = l3;
        this.extendedLastModifiedAtSeconds = num;
        this.extendedLastAccessedAtSeconds = num2;
        this.extendedCreatedAtSeconds = num3;
        this.children = new ArrayList();
    }

    /* JADX WARN: Illegal instructions before constructor call */
    public /* synthetic */ ZipEntry(Path path, boolean z, String str, long j, long j2, long j3, int i, long j4, int i2, int i3, Long l, Long l2, Long l3, Integer num, Integer num2, Integer num3, int i4, DefaultConstructorMarker defaultConstructorMarker) {
        Integer num4;
        boolean z2;
        Integer num5;
        boolean z3 = (i4 & 2) != 0 ? false : z;
        String str2 = (i4 & 4) != 0 ? "" : str;
        long j5 = (i4 & 8) != 0 ? -1L : j;
        long j6 = (i4 & 16) != 0 ? -1L : j2;
        long j7 = (i4 & 32) != 0 ? -1L : j3;
        int i5 = (i4 & 64) != 0 ? -1 : i;
        long j8 = (i4 & 128) == 0 ? j4 : -1L;
        int i6 = (i4 & 256) != 0 ? -1 : i2;
        int i7 = (i4 & 512) == 0 ? i3 : -1;
        Long l4 = (i4 & 1024) != 0 ? null : l;
        Long l5 = (i4 & 2048) != 0 ? null : l2;
        boolean z4 = z3;
        Long l6 = (i4 & 4096) != 0 ? null : l3;
        Integer num6 = (i4 & 8192) != 0 ? null : num;
        Integer num7 = (i4 & NetworkAnalyticsConstants.DataPoints.FLAG_SOURCE_PORT) != 0 ? null : num2;
        if ((i4 & NetworkAnalyticsConstants.DataPoints.FLAG_UID) != 0) {
            num4 = num6;
            z2 = z4;
            num5 = null;
        } else {
            num4 = num6;
            z2 = z4;
            num5 = num3;
        }
        this(path, z2, str2, j5, j6, j7, i5, j8, i6, i7, l4, l5, l6, num4, num7, num5);
    }
}
