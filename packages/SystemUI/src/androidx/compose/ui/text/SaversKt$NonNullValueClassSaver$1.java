package androidx.compose.ui.text;

import androidx.compose.runtime.saveable.SaverScope;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class SaversKt$NonNullValueClassSaver$1 implements NonNullValueClassSaver<Object, Object> {
    public final /* synthetic */ Function1 $restore;
    public final /* synthetic */ Function2 $save;

    public SaversKt$NonNullValueClassSaver$1(Function2 function2, Function1 function1) {
        this.$save = function2;
        this.$restore = function1;
    }

    @Override // androidx.compose.runtime.saveable.Saver
    public final Object restore(Object obj) {
        return this.$restore.mo779invoke(obj);
    }

    @Override // androidx.compose.runtime.saveable.Saver
    public final Object save(SaverScope saverScope, Object obj) {
        return this.$save.invoke(saverScope, obj);
    }
}
