package androidx.lifecycle;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.viewmodel.CreationExtras;
import kotlin.Lazy;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.reflect.KClass;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ViewModelLazy implements Lazy {
    public ViewModel cached;
    public final Function0 extrasProducer;
    public final Function0 factoryProducer;
    public final Function0 storeProducer;
    public final KClass viewModelClass;

    public ViewModelLazy(KClass kClass, Function0 function0, Function0 function02) {
        this(kClass, function0, function02, null, 8, null);
    }

    @Override // kotlin.Lazy
    public final Object getValue() {
        ViewModel viewModel = this.cached;
        if (viewModel != null) {
            return viewModel;
        }
        ViewModelStore viewModelStore = (ViewModelStore) this.storeProducer.invoke();
        ViewModelProvider.Factory factory = (ViewModelProvider.Factory) this.factoryProducer.invoke();
        CreationExtras creationExtras = (CreationExtras) this.extrasProducer.invoke();
        ViewModelProvider.Companion.getClass();
        ViewModel viewModel2 = new ViewModelProvider(viewModelStore, factory, creationExtras).get(this.viewModelClass);
        this.cached = viewModel2;
        return viewModel2;
    }

    @Override // kotlin.Lazy
    public final boolean isInitialized() {
        throw null;
    }

    public ViewModelLazy(KClass kClass, Function0 function0, Function0 function02, Function0 function03) {
        this.viewModelClass = kClass;
        this.storeProducer = function0;
        this.factoryProducer = function02;
        this.extrasProducer = function03;
    }

    public /* synthetic */ ViewModelLazy(KClass kClass, Function0 function0, Function0 function02, Function0 function03, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(kClass, function0, function02, (i & 8) != 0 ? new Function0() { // from class: androidx.lifecycle.ViewModelLazy.1
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return CreationExtras.Empty.INSTANCE;
            }
        } : function03);
    }
}
