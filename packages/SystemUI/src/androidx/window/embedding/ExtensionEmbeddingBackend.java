package androidx.window.embedding;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.util.Log;
import androidx.collection.ArraySet;
import androidx.core.util.Consumer;
import androidx.profileinstaller.ProfileInstallReceiver$$ExternalSyntheticLambda0;
import androidx.window.WindowSdkExtensions;
import androidx.window.core.BuildConfig;
import androidx.window.core.ConsumerAdapter;
import androidx.window.core.ExtensionsUtil;
import androidx.window.core.PredicateAdapter;
import androidx.window.core.VerificationMode;
import androidx.window.embedding.EmbeddingCompat;
import androidx.window.embedding.EmbeddingInterfaceCompat;
import androidx.window.embedding.ExtensionEmbeddingBackend;
import androidx.window.extensions.WindowExtensionsProvider;
import androidx.window.extensions.embedding.ActivityEmbeddingComponent;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Executor;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.LazyKt__LazyJVMKt;
import kotlin.Unit;
import kotlin.collections.CollectionsKt__IterablesKt;
import kotlin.collections.EmptyList;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.ClassReference;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class ExtensionEmbeddingBackend implements EmbeddingBackend {
    public static volatile ExtensionEmbeddingBackend globalInstance;
    public final Context applicationContext;
    public final EmbeddingInterfaceCompat embeddingExtension;
    public final CopyOnWriteArrayList splitChangeCallbacks;
    public static final Companion Companion = new Companion(null);
    public static final ReentrantLock globalLock = new ReentrantLock();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Api31Impl {
        public static final Api31Impl INSTANCE = new Api31Impl();

        private Api31Impl() {
        }

        public final SplitController$SplitSupportStatus isSplitPropertyEnabled(Context context) {
            try {
                PackageManager.Property property = context.getPackageManager().getProperty("android.window.PROPERTY_ACTIVITY_EMBEDDING_SPLITS_ENABLED", context.getPackageName());
                if (property.isBoolean()) {
                    return property.getBoolean() ? SplitController$SplitSupportStatus.SPLIT_AVAILABLE : SplitController$SplitSupportStatus.SPLIT_UNAVAILABLE;
                }
                BuildConfig.INSTANCE.getClass();
                if (BuildConfig.verificationMode == VerificationMode.LOG) {
                    Log.w("EmbeddingBackend", "android.window.PROPERTY_ACTIVITY_EMBEDDING_SPLITS_ENABLED must have a boolean value");
                }
                return SplitController$SplitSupportStatus.SPLIT_ERROR_PROPERTY_NOT_DECLARED;
            } catch (PackageManager.NameNotFoundException unused) {
                BuildConfig.INSTANCE.getClass();
                if (BuildConfig.verificationMode == VerificationMode.LOG) {
                    Log.w("EmbeddingBackend", "android.window.PROPERTY_ACTIVITY_EMBEDDING_SPLITS_ENABLED must be set and enabled in AndroidManifest.xml to use splits APIs.");
                }
                return SplitController$SplitSupportStatus.SPLIT_ERROR_PROPERTY_NOT_DECLARED;
            } catch (Exception e) {
                BuildConfig.INSTANCE.getClass();
                if (BuildConfig.verificationMode == VerificationMode.LOG) {
                    Log.e("EmbeddingBackend", "PackageManager.getProperty is not supported", e);
                }
                return SplitController$SplitSupportStatus.SPLIT_ERROR_PROPERTY_NOT_DECLARED;
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        public static EmbeddingCompat initAndVerifyEmbeddingExtension(Context context) {
            ClassLoader classLoader;
            ActivityEmbeddingComponent activityEmbeddingComponent;
            EmbeddingCompat embeddingCompat = null;
            try {
                ExtensionsUtil.INSTANCE.getClass();
                if (ExtensionsUtil.getSafeVendorApiLevel() >= 1) {
                    EmbeddingCompat.Companion.getClass();
                    if (EmbeddingCompat.Companion.isEmbeddingAvailable() && (classLoader = EmbeddingBackend.class.getClassLoader()) != null) {
                        if (EmbeddingCompat.Companion.isEmbeddingAvailable()) {
                            ClassLoader classLoader2 = EmbeddingCompat.class.getClassLoader();
                            if (classLoader2 != null) {
                                activityEmbeddingComponent = new SafeActivityEmbeddingComponentProvider(classLoader2, new ConsumerAdapter(classLoader2), WindowExtensionsProvider.getWindowExtensions()).getActivityEmbeddingComponent();
                                if (activityEmbeddingComponent == null) {
                                }
                            }
                            activityEmbeddingComponent = (ActivityEmbeddingComponent) Proxy.newProxyInstance(EmbeddingCompat.class.getClassLoader(), new Class[]{ActivityEmbeddingComponent.class}, new EmbeddingCompat$Companion$$ExternalSyntheticLambda0());
                        } else {
                            activityEmbeddingComponent = (ActivityEmbeddingComponent) Proxy.newProxyInstance(EmbeddingCompat.class.getClassLoader(), new Class[]{ActivityEmbeddingComponent.class}, new EmbeddingCompat$Companion$$ExternalSyntheticLambda0());
                        }
                        ActivityEmbeddingComponent activityEmbeddingComponent2 = activityEmbeddingComponent;
                        EmbeddingAdapter embeddingAdapter = new EmbeddingAdapter(new PredicateAdapter(classLoader));
                        ConsumerAdapter consumerAdapter = new ConsumerAdapter(classLoader);
                        WindowSdkExtensions.Companion.getClass();
                        embeddingCompat = new EmbeddingCompat(activityEmbeddingComponent2, embeddingAdapter, consumerAdapter, context, WindowSdkExtensions.Companion.getInstance().extensionVersion >= 6 ? new OverlayControllerImpl(activityEmbeddingComponent2, embeddingAdapter) : null, WindowSdkExtensions.Companion.getInstance().extensionVersion >= 6 ? new ActivityWindowInfoCallbackController(activityEmbeddingComponent2) : null);
                    }
                }
            } catch (Throwable th) {
                Log.d("EmbeddingBackend", "Failed to load embedding extension: " + th);
            }
            if (embeddingCompat == null) {
                Log.d("EmbeddingBackend", "No supported embedding extension found");
            }
            return embeddingCompat;
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class EmbeddingCallbackImpl implements EmbeddingInterfaceCompat.EmbeddingCallbackInterface {
        public EmbeddingCallbackImpl() {
            EmptyList emptyList = EmptyList.INSTANCE;
        }

        public final void onSplitInfoChanged(List list) {
            Iterator it = ExtensionEmbeddingBackend.this.splitChangeCallbacks.iterator();
            while (it.hasNext()) {
                final SplitListenerWrapper splitListenerWrapper = (SplitListenerWrapper) it.next();
                splitListenerWrapper.getClass();
                final ArrayList arrayList = new ArrayList();
                for (Object obj : list) {
                    SplitInfo splitInfo = (SplitInfo) obj;
                    List list2 = splitInfo.primaryActivityStack.activitiesInProcess;
                    Activity activity = splitListenerWrapper.activity;
                    if (list2.contains(activity) || splitInfo.secondaryActivityStack.activitiesInProcess.contains(activity)) {
                        arrayList.add(obj);
                    }
                }
                if (!arrayList.equals(splitListenerWrapper.lastValue)) {
                    splitListenerWrapper.lastValue = arrayList;
                    splitListenerWrapper.executor.execute(new Runnable() { // from class: androidx.window.embedding.ExtensionEmbeddingBackend$SplitListenerWrapper$$ExternalSyntheticLambda0
                        @Override // java.lang.Runnable
                        public final void run() {
                            ExtensionEmbeddingBackend.SplitListenerWrapper splitListenerWrapper2 = ExtensionEmbeddingBackend.SplitListenerWrapper.this;
                            splitListenerWrapper2.callback.accept(arrayList);
                        }
                    });
                }
            }
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class RuleTracker {
        public RuleTracker() {
            new ArraySet();
            new HashMap();
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class SplitListenerWrapper {
        public final Activity activity;
        public final Consumer callback;
        public final Executor executor;
        public List lastValue;

        public SplitListenerWrapper(Activity activity, Executor executor, Consumer consumer) {
            this.activity = activity;
            this.executor = executor;
            this.callback = consumer;
        }
    }

    public ExtensionEmbeddingBackend(Context context, EmbeddingInterfaceCompat embeddingInterfaceCompat) {
        this.applicationContext = context;
        this.embeddingExtension = embeddingInterfaceCompat;
        final EmbeddingCallbackImpl embeddingCallbackImpl = new EmbeddingCallbackImpl();
        this.splitChangeCallbacks = new CopyOnWriteArrayList();
        if (embeddingInterfaceCompat != null) {
            final EmbeddingCompat embeddingCompat = (EmbeddingCompat) embeddingInterfaceCompat;
            int i = embeddingCompat.windowSdkExtensions.extensionVersion;
            if (i == 1) {
                ActivityEmbeddingComponent activityEmbeddingComponent = embeddingCompat.embeddingExtension;
                ClassReference orCreateKotlinClass = Reflection.getOrCreateKotlinClass(List.class);
                Function1 function1 = new Function1() { // from class: androidx.window.embedding.EmbeddingCompat$setEmbeddingCallback$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo779invoke(Object obj) {
                        ArrayList arrayList = new ArrayList();
                        for (Object obj2 : (List) obj) {
                            if (obj2 instanceof androidx.window.extensions.embedding.SplitInfo) {
                                arrayList.add(obj2);
                            }
                        }
                        ((ExtensionEmbeddingBackend.EmbeddingCallbackImpl) EmbeddingInterfaceCompat.EmbeddingCallbackInterface.this).onSplitInfoChanged(embeddingCompat.adapter.translate(arrayList));
                        return Unit.INSTANCE;
                    }
                };
                ConsumerAdapter consumerAdapter = embeddingCompat.consumerAdapter;
                consumerAdapter.getClass();
                Method method = activityEmbeddingComponent.getClass().getMethod("setSplitInfoCallback", consumerAdapter.loader.loadClass("java.util.function.Consumer"));
                ConsumerAdapter.ConsumerHandler consumerHandler = new ConsumerAdapter.ConsumerHandler(orCreateKotlinClass, function1);
                ClassLoader classLoader = consumerAdapter.loader;
                method.invoke(activityEmbeddingComponent, Proxy.newProxyInstance(classLoader, new Class[]{classLoader.loadClass("java.util.function.Consumer")}, consumerHandler));
            } else if (2 <= i && i < 5) {
                final int i2 = 1;
                embeddingCompat.embeddingExtension.setSplitInfoCallback(new androidx.window.extensions.core.util.function.Consumer() { // from class: androidx.window.embedding.EmbeddingCompat$$ExternalSyntheticLambda0
                    public final void accept(Object obj) {
                        switch (i2) {
                            case 0:
                                EmbeddingAdapter embeddingAdapter = embeddingCompat.adapter;
                                embeddingAdapter.getClass();
                                List list = (List) obj;
                                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
                                Iterator it = list.iterator();
                                while (it.hasNext()) {
                                    arrayList.add(embeddingAdapter.translate$window_release((androidx.window.extensions.embedding.ActivityStack) it.next()));
                                }
                                break;
                            default:
                                embeddingCallbackImpl.onSplitInfoChanged(embeddingCompat.adapter.translate((List) obj));
                                break;
                        }
                    }
                });
            } else if (5 <= i && i <= Integer.MAX_VALUE) {
                final int i3 = 1;
                embeddingCompat.embeddingExtension.setSplitInfoCallback(new androidx.window.extensions.core.util.function.Consumer() { // from class: androidx.window.embedding.EmbeddingCompat$$ExternalSyntheticLambda0
                    public final void accept(Object obj) {
                        switch (i3) {
                            case 0:
                                EmbeddingAdapter embeddingAdapter = embeddingCompat.adapter;
                                embeddingAdapter.getClass();
                                List list = (List) obj;
                                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
                                Iterator it = list.iterator();
                                while (it.hasNext()) {
                                    arrayList.add(embeddingAdapter.translate$window_release((androidx.window.extensions.embedding.ActivityStack) it.next()));
                                }
                                break;
                            default:
                                embeddingCallbackImpl.onSplitInfoChanged(embeddingCompat.adapter.translate((List) obj));
                                break;
                        }
                    }
                });
                final int i4 = 0;
                embeddingCompat.embeddingExtension.registerActivityStackCallback(new ProfileInstallReceiver$$ExternalSyntheticLambda0(), new androidx.window.extensions.core.util.function.Consumer() { // from class: androidx.window.embedding.EmbeddingCompat$$ExternalSyntheticLambda0
                    public final void accept(Object obj) {
                        switch (i4) {
                            case 0:
                                EmbeddingAdapter embeddingAdapter = embeddingCompat.adapter;
                                embeddingAdapter.getClass();
                                List list = (List) obj;
                                ArrayList arrayList = new ArrayList(CollectionsKt__IterablesKt.collectionSizeOrDefault(list, 10));
                                Iterator it = list.iterator();
                                while (it.hasNext()) {
                                    arrayList.add(embeddingAdapter.translate$window_release((androidx.window.extensions.embedding.ActivityStack) it.next()));
                                }
                                break;
                            default:
                                embeddingCallbackImpl.onSplitInfoChanged(embeddingCompat.adapter.translate((List) obj));
                                break;
                        }
                    }
                });
            }
        }
        new RuleTracker();
        LazyKt__LazyJVMKt.lazy(new Function0() { // from class: androidx.window.embedding.ExtensionEmbeddingBackend$splitSupportStatus$2
            {
                super(0);
            }

            @Override // kotlin.jvm.functions.Function0
            public final Object invoke() {
                ExtensionEmbeddingBackend extensionEmbeddingBackend = ExtensionEmbeddingBackend.this;
                return extensionEmbeddingBackend.embeddingExtension != null ? ExtensionEmbeddingBackend.Api31Impl.INSTANCE.isSplitPropertyEnabled(extensionEmbeddingBackend.applicationContext) : SplitController$SplitSupportStatus.SPLIT_UNAVAILABLE;
            }
        });
    }
}
