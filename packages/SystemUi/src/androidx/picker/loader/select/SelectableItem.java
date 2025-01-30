package androidx.picker.loader.select;

import androidx.picker.features.observable.MutableState;
import androidx.picker.features.observable.ObservableProperty;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.DisposableHandle;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class SelectableItem extends ObservableProperty<Boolean> {
    public /* synthetic */ SelectableItem(MutableState mutableState, Function1 function1, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(mutableState, (i & 2) != 0 ? null : function1);
    }

    public final boolean isSelected() {
        return getState().booleanValue();
    }

    public final DisposableHandle registerAfterChangeUpdateListener(final Function1 function1) {
        return super.registerAfterChangeUpdateListener(new Function2() { // from class: androidx.picker.loader.select.SelectableItem$registerAfterChangeUpdateListener$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                ((Boolean) obj).booleanValue();
                Function1.this.invoke(Boolean.valueOf(((Boolean) obj2).booleanValue()));
                return Unit.INSTANCE;
            }
        });
    }

    public final DisposableHandle registerBeforeChangeUpdateListener(final Function1 function1) {
        return super.registerBeforeChangeUpdateListener(new Function2() { // from class: androidx.picker.loader.select.SelectableItem$registerBeforeChangeUpdateListener$1
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                ((Boolean) obj).booleanValue();
                return (Boolean) Function1.this.invoke(Boolean.valueOf(((Boolean) obj2).booleanValue()));
            }
        });
    }

    public SelectableItem(MutableState mutableState, Function1 function1) {
        super(mutableState, function1);
    }
}
