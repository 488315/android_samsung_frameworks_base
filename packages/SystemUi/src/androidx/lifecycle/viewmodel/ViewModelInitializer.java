package androidx.lifecycle.viewmodel;

import androidx.lifecycle.ViewModel;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class ViewModelInitializer {
    public final Class clazz;
    public final Function1 initializer;

    public ViewModelInitializer(Class<ViewModel> cls, Function1 function1) {
        this.clazz = cls;
        this.initializer = function1;
    }
}
