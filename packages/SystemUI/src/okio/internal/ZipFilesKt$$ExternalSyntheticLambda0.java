package okio.internal;

import java.io.IOException;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Ref$ObjectRef;
import okio.RealBufferedSource;

/* loaded from: classes4.dex */
public final /* synthetic */ class ZipFilesKt$$ExternalSyntheticLambda0 implements Function2 {
    public final /* synthetic */ int $r8$classId = 1;
    public final /* synthetic */ RealBufferedSource f$0;
    public final /* synthetic */ Ref$ObjectRef f$1;
    public final /* synthetic */ Ref$ObjectRef f$2;
    public final /* synthetic */ Ref$ObjectRef f$3;

    public /* synthetic */ ZipFilesKt$$ExternalSyntheticLambda0(Ref$ObjectRef ref$ObjectRef, RealBufferedSource realBufferedSource, Ref$ObjectRef ref$ObjectRef2, Ref$ObjectRef ref$ObjectRef3) {
        this.f$1 = ref$ObjectRef;
        this.f$0 = realBufferedSource;
        this.f$2 = ref$ObjectRef2;
        this.f$3 = ref$ObjectRef3;
    }

    /* JADX WARN: Type inference failed for: r0v4, types: [T, java.lang.Long] */
    /* JADX WARN: Type inference failed for: r12v11, types: [T, java.lang.Integer] */
    /* JADX WARN: Type inference failed for: r12v13, types: [T, java.lang.Integer] */
    /* JADX WARN: Type inference failed for: r12v20, types: [T, java.lang.Long] */
    /* JADX WARN: Type inference failed for: r12v22, types: [T, java.lang.Long] */
    /* JADX WARN: Type inference failed for: r12v9, types: [T, java.lang.Integer] */
    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) throws IOException {
        int i = this.$r8$classId;
        int iIntValue = ((Integer) obj).intValue();
        Long l = (Long) obj2;
        switch (i) {
            case 0:
                long jLongValue = l.longValue();
                if (iIntValue == 21589) {
                    if (jLongValue < 1) {
                        throw new IOException("bad zip: extended timestamp extra too short");
                    }
                    RealBufferedSource realBufferedSource = this.f$0;
                    byte b = realBufferedSource.readByte();
                    boolean z = (b & 1) == 1;
                    boolean z2 = (b & 2) == 2;
                    boolean z3 = (b & 4) == 4;
                    long j = z ? 5L : 1L;
                    if (z2) {
                        j += 4;
                    }
                    if (z3) {
                        j += 4;
                    }
                    if (jLongValue < j) {
                        throw new IOException("bad zip: extended timestamp extra too short");
                    }
                    if (z) {
                        this.f$1.element = Integer.valueOf(realBufferedSource.readIntLe());
                    }
                    if (z2) {
                        this.f$2.element = Integer.valueOf(realBufferedSource.readIntLe());
                    }
                    if (z3) {
                        this.f$3.element = Integer.valueOf(realBufferedSource.readIntLe());
                    }
                }
                return Unit.INSTANCE;
            default:
                long jLongValue2 = l.longValue();
                if (iIntValue == 1) {
                    Ref$ObjectRef ref$ObjectRef = this.f$1;
                    if (ref$ObjectRef.element != 0) {
                        throw new IOException("bad zip: NTFS extra attribute tag 0x0001 repeated");
                    }
                    if (jLongValue2 != 24) {
                        throw new IOException("bad zip: NTFS extra attribute tag 0x0001 size != 24");
                    }
                    RealBufferedSource realBufferedSource2 = this.f$0;
                    ref$ObjectRef.element = Long.valueOf(realBufferedSource2.readLongLe());
                    this.f$2.element = Long.valueOf(realBufferedSource2.readLongLe());
                    this.f$3.element = Long.valueOf(realBufferedSource2.readLongLe());
                }
                return Unit.INSTANCE;
        }
    }

    public /* synthetic */ ZipFilesKt$$ExternalSyntheticLambda0(RealBufferedSource realBufferedSource, Ref$ObjectRef ref$ObjectRef, Ref$ObjectRef ref$ObjectRef2, Ref$ObjectRef ref$ObjectRef3) {
        this.f$0 = realBufferedSource;
        this.f$1 = ref$ObjectRef;
        this.f$2 = ref$ObjectRef2;
        this.f$3 = ref$ObjectRef3;
    }
}
