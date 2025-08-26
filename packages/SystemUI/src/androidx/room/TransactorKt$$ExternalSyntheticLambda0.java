package androidx.room;

import androidx.sqlite.SQLiteStatement;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public final /* synthetic */ class TransactorKt$$ExternalSyntheticLambda0 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        return Boolean.valueOf(((SQLiteStatement) obj).step());
    }
}
