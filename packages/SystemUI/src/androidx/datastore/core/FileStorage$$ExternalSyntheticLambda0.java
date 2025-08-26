package androidx.datastore.core;

import java.io.File;
import java.util.Set;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public final /* synthetic */ class FileStorage$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        Set set = FileStorage.activeFiles;
        return new SingleProcessCoordinator(((File) obj).getCanonicalFile().getAbsolutePath());
    }
}
