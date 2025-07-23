package androidx.datastore.core;

import java.io.File;
import java.util.Set;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final /* synthetic */ class FileStorage$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        Set set = FileStorage.activeFiles;
        return new SingleProcessCoordinator(((File) obj).getCanonicalFile().getAbsolutePath());
    }
}
