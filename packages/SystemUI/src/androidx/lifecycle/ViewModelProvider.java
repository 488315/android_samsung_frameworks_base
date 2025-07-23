package androidx.lifecycle;

import android.app.Application;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.ViewModelProviderImpl;
import androidx.lifecycle.viewmodel.internal.DefaultViewModelProviderFactory;
import androidx.lifecycle.viewmodel.internal.JvmViewModelProviders;
import androidx.lifecycle.viewmodel.internal.ViewModelProviders;
import java.lang.reflect.InvocationTargetException;
import kotlin.jvm.internal.ClassBasedDeclarationContainer;
import kotlin.jvm.internal.ClassReference;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;
import kotlin.reflect.KClass;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class ViewModelProvider {
    public static final Companion Companion = new Companion(null);
    public final ViewModelProviderImpl impl;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static ViewModelProvider create$default(Companion companion, ViewModelStoreOwner viewModelStoreOwner) {
            ViewModelProviders viewModelProviders = ViewModelProviders.INSTANCE;
            viewModelProviders.getClass();
            Factory defaultViewModelProviderFactory = viewModelStoreOwner instanceof HasDefaultViewModelProviderFactory ? ((HasDefaultViewModelProviderFactory) viewModelStoreOwner).getDefaultViewModelProviderFactory() : DefaultViewModelProviderFactory.INSTANCE;
            viewModelProviders.getClass();
            CreationExtras defaultViewModelCreationExtras = viewModelStoreOwner instanceof HasDefaultViewModelProviderFactory ? ((HasDefaultViewModelProviderFactory) viewModelStoreOwner).getDefaultViewModelCreationExtras() : CreationExtras.Empty.INSTANCE;
            companion.getClass();
            return new ViewModelProvider(viewModelStoreOwner.getViewModelStore(), defaultViewModelProviderFactory, defaultViewModelCreationExtras);
        }

        private Companion() {
        }
    }

    static {
        ViewModelProviders.ViewModelKey viewModelKey = ViewModelProviders.ViewModelKey.INSTANCE;
    }

    public ViewModelProvider(ViewModelStore viewModelStore, Factory factory) {
        this(viewModelStore, factory, null, 4, null);
    }

    public final ViewModel get(KClass kClass) {
        ViewModelProviders.INSTANCE.getClass();
        String qualifiedName = ((ClassReference) kClass).getQualifiedName();
        if (qualifiedName == null) {
            throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
        }
        return this.impl.getViewModel$lifecycle_viewmodel_release(kClass, "androidx.lifecycle.ViewModelProvider.DefaultKey:".concat(qualifiedName));
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class AndroidViewModelFactory extends NewInstanceFactory {
        public static AndroidViewModelFactory _instance;
        public final Application application;
        public static final Companion Companion = new Companion(null);
        public static final ViewModelProvider$AndroidViewModelFactory$Companion$APPLICATION_KEY$1 APPLICATION_KEY = new CreationExtras.Key() { // from class: androidx.lifecycle.ViewModelProvider$AndroidViewModelFactory$Companion$APPLICATION_KEY$1
        };

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

        private AndroidViewModelFactory(Application application, int i) {
            this.application = application;
        }

        @Override // androidx.lifecycle.ViewModelProvider.NewInstanceFactory, androidx.lifecycle.ViewModelProvider.Factory
        public final ViewModel create(Class cls, CreationExtras creationExtras) {
            if (this.application != null) {
                return create(cls);
            }
            Application application = (Application) creationExtras.get(APPLICATION_KEY);
            if (application != null) {
                return create(cls, application);
            }
            if (AndroidViewModel.class.isAssignableFrom(cls)) {
                throw new IllegalArgumentException("CreationExtras must have an application by `APPLICATION_KEY`");
            }
            JvmViewModelProviders.INSTANCE.getClass();
            return JvmViewModelProviders.createViewModel(cls);
        }

        public AndroidViewModelFactory() {
            this(null, 0);
        }

        public AndroidViewModelFactory(Application application) {
            this(application, 0);
        }

        @Override // androidx.lifecycle.ViewModelProvider.NewInstanceFactory, androidx.lifecycle.ViewModelProvider.Factory
        public final ViewModel create(Class cls) {
            Application application = this.application;
            if (application != null) {
                return create(cls, application);
            }
            throw new UnsupportedOperationException("AndroidViewModelFactory constructed with empty constructor works only with create(modelClass: Class<T>, extras: CreationExtras).");
        }

        public static ViewModel create(Class cls, Application application) {
            if (AndroidViewModel.class.isAssignableFrom(cls)) {
                try {
                    return (ViewModel) cls.getConstructor(Application.class).newInstance(application);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException("Cannot create an instance of " + cls, e);
                } catch (InstantiationException e2) {
                    throw new RuntimeException("Cannot create an instance of " + cls, e2);
                } catch (NoSuchMethodException e3) {
                    throw new RuntimeException("Cannot create an instance of " + cls, e3);
                } catch (InvocationTargetException e4) {
                    throw new RuntimeException("Cannot create an instance of " + cls, e4);
                }
            }
            JvmViewModelProviders.INSTANCE.getClass();
            return JvmViewModelProviders.createViewModel(cls);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public interface Factory {

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Companion {
            public static final /* synthetic */ int $r8$clinit = 0;

            static {
                new Companion();
            }

            private Companion() {
            }
        }

        static {
            int i = Companion.$r8$clinit;
        }

        default ViewModel create(KClass kClass, CreationExtras creationExtras) {
            return create(((ClassBasedDeclarationContainer) kClass).getJClass(), creationExtras);
        }

        default ViewModel create(Class cls) {
            ViewModelProviders.INSTANCE.getClass();
            throw new UnsupportedOperationException("`Factory.create(String, CreationExtras)` is not implemented. You may need to override the method and provide a custom implementation. Note that using `Factory.create(String)` is not supported and considered an error.");
        }

        default ViewModel create(Class cls, CreationExtras creationExtras) {
            return create(cls);
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class NewInstanceFactory implements Factory {
        public static final Companion Companion = new Companion(null);
        public static final ViewModelProviders.ViewModelKey VIEW_MODEL_KEY = ViewModelProviders.ViewModelKey.INSTANCE;
        public static NewInstanceFactory _instance;

        /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
        public final class Companion {
            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }

            private Companion() {
            }
        }

        @Override // androidx.lifecycle.ViewModelProvider.Factory
        public final ViewModel create(KClass kClass, CreationExtras creationExtras) {
            return create(((ClassBasedDeclarationContainer) kClass).getJClass(), creationExtras);
        }

        @Override // androidx.lifecycle.ViewModelProvider.Factory
        public ViewModel create(Class cls) {
            JvmViewModelProviders.INSTANCE.getClass();
            return JvmViewModelProviders.createViewModel(cls);
        }

        @Override // androidx.lifecycle.ViewModelProvider.Factory
        public ViewModel create(Class cls, CreationExtras creationExtras) {
            return create(cls);
        }
    }

    private ViewModelProvider(ViewModelProviderImpl viewModelProviderImpl) {
        this.impl = viewModelProviderImpl;
    }

    public /* synthetic */ ViewModelProvider(ViewModelStore viewModelStore, Factory factory, CreationExtras creationExtras, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(viewModelStore, factory, (i & 4) != 0 ? CreationExtras.Empty.INSTANCE : creationExtras);
    }

    public ViewModelProvider(ViewModelStore viewModelStore, Factory factory, CreationExtras creationExtras) {
        this(new ViewModelProviderImpl(viewModelStore, factory, creationExtras));
    }

    public final ViewModel get(Class cls) {
        return get(Reflection.getOrCreateKotlinClass(cls));
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ViewModelProvider(androidx.lifecycle.ViewModelStoreOwner r4) {
        /*
            r3 = this;
            androidx.lifecycle.ViewModelStore r0 = r4.getViewModelStore()
            androidx.lifecycle.viewmodel.internal.ViewModelProviders r1 = androidx.lifecycle.viewmodel.internal.ViewModelProviders.INSTANCE
            r1.getClass()
            boolean r1 = r4 instanceof androidx.lifecycle.HasDefaultViewModelProviderFactory
            if (r1 == 0) goto L15
            r2 = r4
            androidx.lifecycle.HasDefaultViewModelProviderFactory r2 = (androidx.lifecycle.HasDefaultViewModelProviderFactory) r2
            androidx.lifecycle.ViewModelProvider$Factory r2 = r2.getDefaultViewModelProviderFactory()
            goto L17
        L15:
            androidx.lifecycle.viewmodel.internal.DefaultViewModelProviderFactory r2 = androidx.lifecycle.viewmodel.internal.DefaultViewModelProviderFactory.INSTANCE
        L17:
            if (r1 == 0) goto L20
            androidx.lifecycle.HasDefaultViewModelProviderFactory r4 = (androidx.lifecycle.HasDefaultViewModelProviderFactory) r4
            androidx.lifecycle.viewmodel.CreationExtras r4 = r4.getDefaultViewModelCreationExtras()
            goto L22
        L20:
            androidx.lifecycle.viewmodel.CreationExtras$Empty r4 = androidx.lifecycle.viewmodel.CreationExtras.Empty.INSTANCE
        L22:
            r3.<init>(r0, r2, r4)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.lifecycle.ViewModelProvider.<init>(androidx.lifecycle.ViewModelStoreOwner):void");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public ViewModelProvider(androidx.lifecycle.ViewModelStoreOwner r3, androidx.lifecycle.ViewModelProvider.Factory r4) {
        /*
            r2 = this;
            androidx.lifecycle.ViewModelStore r0 = r3.getViewModelStore()
            androidx.lifecycle.viewmodel.internal.ViewModelProviders r1 = androidx.lifecycle.viewmodel.internal.ViewModelProviders.INSTANCE
            r1.getClass()
            boolean r1 = r3 instanceof androidx.lifecycle.HasDefaultViewModelProviderFactory
            if (r1 == 0) goto L14
            androidx.lifecycle.HasDefaultViewModelProviderFactory r3 = (androidx.lifecycle.HasDefaultViewModelProviderFactory) r3
            androidx.lifecycle.viewmodel.CreationExtras r3 = r3.getDefaultViewModelCreationExtras()
            goto L16
        L14:
            androidx.lifecycle.viewmodel.CreationExtras$Empty r3 = androidx.lifecycle.viewmodel.CreationExtras.Empty.INSTANCE
        L16:
            r2.<init>(r0, r4, r3)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.lifecycle.ViewModelProvider.<init>(androidx.lifecycle.ViewModelStoreOwner, androidx.lifecycle.ViewModelProvider$Factory):void");
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class OnRequeryFactory {
        public void onRequery(ViewModel viewModel) {
        }
    }
}
