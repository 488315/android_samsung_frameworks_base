package androidx.compose.runtime.saveable;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class SaveableStateHolderImpl$canBeSaved$1 extends Lambda implements Function1 {
    final /* synthetic */ SaveableStateHolderImpl this$0;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SaveableStateHolderImpl$canBeSaved$1(SaveableStateHolderImpl saveableStateHolderImpl) {
        super(1);
        this.this$0 = saveableStateHolderImpl;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo779invoke(Object obj) {
        SaveableStateRegistry saveableStateRegistry = this.this$0.parentSaveableStateRegistry;
        return Boolean.valueOf(saveableStateRegistry != null ? saveableStateRegistry.canBeSaved(obj) : true);
    }
}
