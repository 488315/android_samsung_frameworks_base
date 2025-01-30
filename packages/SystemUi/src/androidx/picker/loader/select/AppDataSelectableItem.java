package androidx.picker.loader.select;

import androidx.picker.features.observable.UpdateMutableState;
import androidx.picker.model.AppInfoData;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class AppDataSelectableItem extends SelectableItem {
    private final AppDataSelectedState mutableState;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AppDataSelectedState extends UpdateMutableState {
        public AppDataSelectedState(AppInfoData appInfoData) {
            super(appInfoData);
        }

        @Override // androidx.picker.features.observable.MutableState
        public final Object getValue() {
            return Boolean.valueOf(((AppInfoData) this.base).getSelected());
        }

        @Override // androidx.picker.features.observable.MutableState
        public final void setValue(Object obj) {
            ((AppInfoData) this.base).setSelected(((Boolean) obj).booleanValue());
        }
    }

    public /* synthetic */ AppDataSelectableItem(AppDataSelectedState appDataSelectedState, Function1 function1, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(appDataSelectedState, (i & 2) != 0 ? null : function1);
    }

    public final void updateBase(AppInfoData appInfoData) {
        this.mutableState.base = appInfoData;
    }

    private AppDataSelectableItem(AppDataSelectedState appDataSelectedState, Function1 function1) {
        super(appDataSelectedState, function1);
        this.mutableState = appDataSelectedState;
    }

    public AppDataSelectableItem(AppInfoData appInfoData, Function1 function1) {
        this(new AppDataSelectedState(appInfoData), function1);
    }
}
