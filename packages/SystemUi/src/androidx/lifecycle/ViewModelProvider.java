package androidx.lifecycle;

import android.app.Application;
import androidx.lifecycle.viewmodel.CreationExtras;
import androidx.lifecycle.viewmodel.MutableCreationExtras;
import java.lang.reflect.InvocationTargetException;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class ViewModelProvider {
    public final CreationExtras defaultCreationExtras;
    public final Factory factory;
    public final ViewModelStore store;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public interface Factory {

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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

        default ViewModel create(Class cls) {
            throw new UnsupportedOperationException("Factory.create(String) is unsupported.  This Factory requires `CreationExtras` to be passed into `create` method.");
        }

        default ViewModel create(Class cls, MutableCreationExtras mutableCreationExtras) {
            return create(cls);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class NewInstanceFactory implements Factory {
        public static final Companion Companion = new Companion(null);
        public static final Companion.ViewModelKeyImpl VIEW_MODEL_KEY = Companion.ViewModelKeyImpl.INSTANCE;
        public static NewInstanceFactory sInstance;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class Companion {

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            public final class ViewModelKeyImpl implements CreationExtras.Key {
                public static final ViewModelKeyImpl INSTANCE = new ViewModelKeyImpl();

                private ViewModelKeyImpl() {
                }
            }

            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }

        @Override // androidx.lifecycle.ViewModelProvider.Factory
        public ViewModel create(Class cls) {
            try {
                return (ViewModel) cls.getDeclaredConstructor(new Class[0]).newInstance(new Object[0]);
            } catch (IllegalAccessException e) {
                throw new RuntimeException("Cannot create an instance of " + cls, e);
            } catch (InstantiationException e2) {
                throw new RuntimeException("Cannot create an instance of " + cls, e2);
            } catch (NoSuchMethodException e3) {
                throw new RuntimeException("Cannot create an instance of " + cls, e3);
            }
        }
    }

    public ViewModelProvider(ViewModelStore viewModelStore, Factory factory) {
        this(viewModelStore, factory, null, 4, null);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public final ViewModel get(Class cls, String str) {
        ViewModel create;
        ViewModelStore viewModelStore = this.store;
        ViewModel viewModel = (ViewModel) viewModelStore.mMap.get(str);
        boolean isInstance = cls.isInstance(viewModel);
        Factory factory = this.factory;
        if (isInstance) {
            OnRequeryFactory onRequeryFactory = factory instanceof OnRequeryFactory ? (OnRequeryFactory) factory : null;
            if (onRequeryFactory != null) {
                onRequeryFactory.onRequery(viewModel);
            }
            return viewModel;
        }
        MutableCreationExtras mutableCreationExtras = new MutableCreationExtras(this.defaultCreationExtras);
        mutableCreationExtras.set(NewInstanceFactory.VIEW_MODEL_KEY, str);
        try {
            create = factory.create(cls, mutableCreationExtras);
        } catch (AbstractMethodError unused) {
            create = factory.create(cls);
        }
        ViewModel viewModel2 = (ViewModel) viewModelStore.mMap.put(str, create);
        if (viewModel2 != null) {
            viewModel2.onCleared();
        }
        return create;
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class AndroidViewModelFactory extends NewInstanceFactory {
        public static AndroidViewModelFactory sInstance;
        public final Application application;
        public static final Companion Companion = new Companion(null);
        public static final Companion.ApplicationKeyImpl APPLICATION_KEY = Companion.ApplicationKeyImpl.INSTANCE;

        /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
        public final class Companion {

            /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
            public final class ApplicationKeyImpl implements CreationExtras.Key {
                public static final ApplicationKeyImpl INSTANCE = new ApplicationKeyImpl();

                private ApplicationKeyImpl() {
                }
            }

            private Companion() {
            }

            public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
                this();
            }
        }

        private AndroidViewModelFactory(Application application, int i) {
            this.application = application;
        }

        @Override // androidx.lifecycle.ViewModelProvider.Factory
        public final ViewModel create(Class cls, MutableCreationExtras mutableCreationExtras) {
            if (this.application != null) {
                return create(cls);
            }
            Application application = (Application) mutableCreationExtras.get(APPLICATION_KEY);
            if (application != null) {
                return create(cls, application);
            }
            if (AndroidViewModel.class.isAssignableFrom(cls)) {
                throw new IllegalArgumentException("CreationExtras must have an application by `APPLICATION_KEY`");
            }
            return super.create(cls);
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

        public final ViewModel create(Class cls, Application application) {
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
            return super.create(cls);
        }
    }

    public ViewModelProvider(ViewModelStore viewModelStore, Factory factory, CreationExtras creationExtras) {
        this.store = viewModelStore;
        this.factory = factory;
        this.defaultCreationExtras = creationExtras;
    }

    public /* synthetic */ ViewModelProvider(ViewModelStore viewModelStore, Factory factory, CreationExtras creationExtras, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(viewModelStore, factory, (i & 4) != 0 ? CreationExtras.Empty.INSTANCE : creationExtras);
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public ViewModelProvider(ViewModelStoreOwner viewModelStoreOwner) {
        this(r0, r2, r4);
        Factory factory;
        CreationExtras creationExtras;
        ViewModelStore viewModelStore = viewModelStoreOwner.getViewModelStore();
        AndroidViewModelFactory.Companion.getClass();
        boolean z = viewModelStoreOwner instanceof HasDefaultViewModelProviderFactory;
        if (z) {
            factory = ((HasDefaultViewModelProviderFactory) viewModelStoreOwner).getDefaultViewModelProviderFactory();
        } else {
            NewInstanceFactory.Companion.getClass();
            if (NewInstanceFactory.sInstance == null) {
                NewInstanceFactory.sInstance = new NewInstanceFactory();
            }
            factory = NewInstanceFactory.sInstance;
            Intrinsics.checkNotNull(factory);
        }
        if (z) {
            creationExtras = ((HasDefaultViewModelProviderFactory) viewModelStoreOwner).getDefaultViewModelCreationExtras();
        } else {
            creationExtras = CreationExtras.Empty.INSTANCE;
        }
    }

    public final ViewModel get(Class cls) {
        String canonicalName = cls.getCanonicalName();
        if (canonicalName != null) {
            return get(cls, "androidx.lifecycle.ViewModelProvider.DefaultKey:".concat(canonicalName));
        }
        throw new IllegalArgumentException("Local and anonymous classes can not be ViewModels");
    }

    /* JADX WARN: Illegal instructions before constructor call */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public ViewModelProvider(ViewModelStoreOwner viewModelStoreOwner, Factory factory) {
        this(r0, factory, r3);
        CreationExtras creationExtras;
        ViewModelStore viewModelStore = viewModelStoreOwner.getViewModelStore();
        if (viewModelStoreOwner instanceof HasDefaultViewModelProviderFactory) {
            creationExtras = ((HasDefaultViewModelProviderFactory) viewModelStoreOwner).getDefaultViewModelCreationExtras();
        } else {
            creationExtras = CreationExtras.Empty.INSTANCE;
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class OnRequeryFactory {
        public void onRequery(ViewModel viewModel) {
        }
    }
}
