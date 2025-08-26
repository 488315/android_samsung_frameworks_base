package androidx.datastore.core.okio;

import androidx.datastore.core.SingleProcessCoordinator;
import java.util.Set;
import kotlin.jvm.functions.Function2;
import okio.Path;

/* loaded from: classes.dex */
public final /* synthetic */ class OkioStorage$$ExternalSyntheticLambda1 implements Function2 {
    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Set set = OkioStorage.activeFiles;
        Path.Companion companion = Path.Companion;
        String strUtf8 = ((Path) obj).bytes.utf8();
        companion.getClass();
        return new SingleProcessCoordinator(Path.Companion.get(strUtf8, true).bytes.utf8());
    }
}
