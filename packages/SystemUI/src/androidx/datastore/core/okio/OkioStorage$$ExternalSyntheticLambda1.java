package androidx.datastore.core.okio;

import androidx.datastore.core.SingleProcessCoordinator;
import java.util.Set;
import kotlin.jvm.functions.Function2;
import okio.Path;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class OkioStorage$$ExternalSyntheticLambda1 implements Function2 {
    @Override // kotlin.jvm.functions.Function2
    public final Object invoke(Object obj, Object obj2) {
        Set set = OkioStorage.activeFiles;
        Path.Companion companion = Path.Companion;
        String utf8 = ((Path) obj).bytes.utf8();
        companion.getClass();
        return new SingleProcessCoordinator(Path.Companion.get(utf8, true).bytes.utf8());
    }
}
