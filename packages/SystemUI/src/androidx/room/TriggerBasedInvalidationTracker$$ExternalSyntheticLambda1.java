package androidx.room;

import androidx.room.TriggerBasedInvalidationTracker;
import androidx.sqlite.SQLiteStatement;
import kotlin.collections.builders.SetBuilder;
import kotlin.jvm.functions.Function1;

/* loaded from: classes.dex */
public final /* synthetic */ class TriggerBasedInvalidationTracker$$ExternalSyntheticLambda1 implements Function1 {
    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        SQLiteStatement sQLiteStatement = (SQLiteStatement) obj;
        TriggerBasedInvalidationTracker.Companion companion = TriggerBasedInvalidationTracker.Companion;
        SetBuilder setBuilder = new SetBuilder();
        while (sQLiteStatement.step()) {
            setBuilder.add(Integer.valueOf((int) sQLiteStatement.getLong(0)));
        }
        return setBuilder.build();
    }
}
