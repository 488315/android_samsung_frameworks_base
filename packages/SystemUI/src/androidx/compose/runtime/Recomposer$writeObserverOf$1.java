package androidx.compose.runtime;

import androidx.collection.MutableScatterSet;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* loaded from: classes.dex */
final class Recomposer$writeObserverOf$1 extends Lambda implements Function1 {
    final /* synthetic */ ControlledComposition $composition;
    final /* synthetic */ MutableScatterSet $modifiedValues;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Recomposer$writeObserverOf$1(ControlledComposition controlledComposition, MutableScatterSet mutableScatterSet) {
        super(1);
        this.$composition = controlledComposition;
        this.$modifiedValues = mutableScatterSet;
    }

    @Override // kotlin.jvm.functions.Function1
    /* renamed from: invoke */
    public final Object mo781invoke(Object obj) {
        ((CompositionImpl) this.$composition).recordWriteOf(obj);
        MutableScatterSet mutableScatterSet = this.$modifiedValues;
        if (mutableScatterSet != null) {
            mutableScatterSet.add(obj);
        }
        return Unit.INSTANCE;
    }
}
