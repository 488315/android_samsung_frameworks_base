package androidx.picker.features.observable;

import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class UpdateObservableProperty<T, R> extends ObservableProperty<R> {
    private final UpdateMutableState mutableState;

    public /* synthetic */ UpdateObservableProperty(UpdateMutableState updateMutableState, Function1 function1, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(updateMutableState, (i & 2) != 0 ? null : function1);
    }

    public final void update(T t) {
        this.mutableState.base = t;
    }

    public UpdateObservableProperty(UpdateMutableState updateMutableState, Function1 function1) {
        super(updateMutableState, function1);
        this.mutableState = updateMutableState;
    }
}
