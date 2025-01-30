package androidx.picker.di;

import android.content.Context;
import androidx.picker.features.scs.AbstractAppDataListFactory;
import androidx.picker.features.scs.AppDataListSCSFactory;
import androidx.picker.helper.PackageManagerHelperImpl;
import androidx.picker.loader.DataLoader;
import androidx.picker.loader.DataLoaderImpl;
import androidx.picker.loader.select.SelectStateLoader;
import androidx.picker.repository.AppDataRepository;
import androidx.picker.repository.ViewDataRepository;
import kotlin.Lazy;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.jvm.functions.Function0;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public final class AppPickerContext {
    public final AppDataListSCSFactory appDataListFactory;
    public final Lazy appDataRepository$delegate;
    public final Lazy dataLoader$delegate;
    public final PackageManagerHelperImpl packageManagerHelper;
    public final Lazy selectStateLoader$delegate;
    public final Lazy viewDataRepository$delegate;

    public AppPickerContext(Context context) {
        this.packageManagerHelper = new PackageManagerHelperImpl(context);
        AbstractAppDataListFactory.C03561 c03561 = AbstractAppDataListFactory.EMPTY_FACTORY;
        this.appDataListFactory = new AppDataListSCSFactory(context);
        this.dataLoader$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: androidx.picker.di.AppPickerContext$dataLoader$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                DataLoader.Companion companion = DataLoader.Companion;
                AppPickerContext appPickerContext = AppPickerContext.this;
                AppDataListSCSFactory appDataListSCSFactory = appPickerContext.appDataListFactory;
                companion.getClass();
                return new DataLoaderImpl(appDataListSCSFactory, appPickerContext.packageManagerHelper);
            }
        });
        this.selectStateLoader$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: androidx.picker.di.AppPickerContext$selectStateLoader$2
            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return new SelectStateLoader();
            }
        });
        this.appDataRepository$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: androidx.picker.di.AppPickerContext$appDataRepository$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return new AppDataRepository(AppPickerContext.this.appDataListFactory);
            }
        });
        this.viewDataRepository$delegate = LazyKt__LazyJVMKt.lazy(new Function0() { // from class: androidx.picker.di.AppPickerContext$viewDataRepository$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                return new ViewDataRepository((DataLoader) AppPickerContext.this.dataLoader$delegate.getValue(), (SelectStateLoader) AppPickerContext.this.selectStateLoader$delegate.getValue());
            }
        });
    }
}
