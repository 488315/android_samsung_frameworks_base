package androidx.datastore.core.okio;

import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import okio.Path;

/* loaded from: classes.dex */
public final /* synthetic */ class OkioStorage$$ExternalSyntheticLambda0 implements Function0 {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ OkioStorage f$0;

    public /* synthetic */ OkioStorage$$ExternalSyntheticLambda0(OkioStorage okioStorage, int i) {
        this.$r8$classId = i;
        this.f$0 = okioStorage;
    }

    @Override // kotlin.jvm.functions.Function0
    public final Object invoke() {
        switch (this.$r8$classId) {
            case 0:
                OkioStorage okioStorage = this.f$0;
                synchronized (OkioStorage.activeFilesLock) {
                    OkioStorage.activeFiles.remove(((Path) okioStorage.canonicalPath$delegate.getValue()).bytes.utf8());
                }
                return Unit.INSTANCE;
            default:
                Function0 function0 = this.f$0.producePath;
                Path path = (Path) function0.invoke();
                path.getClass();
                if (okio.internal.Path.access$rootLength(path) != -1) {
                    Path.Companion companion = Path.Companion;
                    String strUtf8 = path.bytes.utf8();
                    companion.getClass();
                    return Path.Companion.get(strUtf8, true);
                }
                throw new IllegalStateException(("OkioStorage requires absolute paths, but did not get an absolute path from producePath = " + function0 + ", instead got " + path).toString());
        }
    }
}
