package androidx.compose.ui.platform;

import android.content.ComponentCallbacks2;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalAccessorScope;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.CompositionLocalMapKt;
import androidx.compose.runtime.ComputedProvidableCompositionLocal;
import androidx.compose.runtime.DisposableEffectResult;
import androidx.compose.runtime.DynamicProvidableCompositionLocal;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.MutableState;
import androidx.compose.runtime.PersistentCompositionLocalMap;
import androidx.compose.runtime.ProvidableCompositionLocal;
import androidx.compose.runtime.ProvidedValue;
import androidx.compose.runtime.RecomposeScopeImpl;
import androidx.compose.runtime.RecomposeScopeImplKt;
import androidx.compose.runtime.SnapshotMutableStateImpl;
import androidx.compose.runtime.SnapshotStateKt;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.runtime.saveable.SaveableStateRegistry;
import androidx.compose.runtime.saveable.SaveableStateRegistryKt;
import androidx.compose.ui.hapticfeedback.HapticFeedback;
import androidx.compose.ui.platform.AndroidComposeView;
import androidx.compose.ui.res.ImageVectorCache;
import androidx.compose.ui.res.ResourceIdCache;
import androidx.compose.ui.scrollcapture.ScrollCapture;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.compose.LocalLifecycleOwnerKt;
import androidx.savedstate.SavedStateRegistry;
import androidx.savedstate.SavedStateRegistryOwner;
import com.android.systemui.R;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;

/* loaded from: classes.dex */
public final class AndroidCompositionLocals_androidKt {
    public static final DynamicProvidableCompositionLocal LocalConfiguration = CompositionLocalKt.compositionLocalOf$default(new Function0() { // from class: androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$LocalConfiguration$1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            AndroidCompositionLocals_androidKt.access$noLocalProvidedFor("LocalConfiguration");
            throw null;
        }
    });
    public static final StaticProvidableCompositionLocal LocalContext = new StaticProvidableCompositionLocal(new Function0() { // from class: androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$LocalContext$1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            AndroidCompositionLocals_androidKt.access$noLocalProvidedFor("LocalContext");
            throw null;
        }
    });
    public static final ComputedProvidableCompositionLocal LocalResources = new ComputedProvidableCompositionLocal(new Function1() { // from class: androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$LocalResources$1
        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            DynamicProvidableCompositionLocal dynamicProvidableCompositionLocal = AndroidCompositionLocals_androidKt.LocalConfiguration;
            PersistentCompositionLocalMap persistentCompositionLocalMap = (PersistentCompositionLocalMap) ((CompositionLocalAccessorScope) obj);
            persistentCompositionLocalMap.getClass();
            CompositionLocalMapKt.read(persistentCompositionLocalMap, dynamicProvidableCompositionLocal);
            return ((Context) CompositionLocalMapKt.read(persistentCompositionLocalMap, AndroidCompositionLocals_androidKt.LocalContext)).getResources();
        }
    });
    public static final StaticProvidableCompositionLocal LocalImageVectorCache = new StaticProvidableCompositionLocal(new Function0() { // from class: androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$LocalImageVectorCache$1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            AndroidCompositionLocals_androidKt.access$noLocalProvidedFor("LocalImageVectorCache");
            throw null;
        }
    });
    public static final StaticProvidableCompositionLocal LocalResourceIdCache = new StaticProvidableCompositionLocal(new Function0() { // from class: androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$LocalResourceIdCache$1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            AndroidCompositionLocals_androidKt.access$noLocalProvidedFor("LocalResourceIdCache");
            throw null;
        }
    });
    public static final StaticProvidableCompositionLocal LocalSavedStateRegistryOwner = new StaticProvidableCompositionLocal(new Function0() { // from class: androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$LocalSavedStateRegistryOwner$1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            AndroidCompositionLocals_androidKt.access$noLocalProvidedFor("LocalSavedStateRegistryOwner");
            throw null;
        }
    });
    public static final StaticProvidableCompositionLocal LocalView = new StaticProvidableCompositionLocal(new Function0() { // from class: androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$LocalView$1
        @Override // kotlin.jvm.functions.Function0
        public final Object invoke() {
            AndroidCompositionLocals_androidKt.access$noLocalProvidedFor("LocalView");
            throw null;
        }
    });

    public static final void ProvideAndroidCompositionLocals(final AndroidComposeView androidComposeView, final Function2 function2, Composer composer, final int i) {
        final boolean z;
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startRestartGroup(1396852028);
        int i2 = (i & 6) == 0 ? (composerImpl.changedInstance(androidComposeView) ? 4 : 2) | i : i;
        if ((i & 48) == 0) {
            i2 |= composerImpl.changedInstance(function2) ? 32 : 16;
        }
        if (composerImpl.shouldExecute(i2 & 1, (i2 & 19) != 18)) {
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.ui.platform.ProvideAndroidCompositionLocals (AndroidCompositionLocals.android.kt:96)");
            }
            final Context context = androidComposeView.getContext();
            Object objRememberedValue = composerImpl.rememberedValue();
            Composer.Companion.getClass();
            Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
            if (objRememberedValue == composer$Companion$Empty$1) {
                objRememberedValue = SnapshotStateKt.mutableStateOf$default(new Configuration(context.getResources().getConfiguration()));
                composerImpl.updateRememberedValue(objRememberedValue);
            }
            final MutableState mutableState = (MutableState) objRememberedValue;
            Object objRememberedValue2 = composerImpl.rememberedValue();
            if (objRememberedValue2 == composer$Companion$Empty$1) {
                objRememberedValue2 = new Function1() { // from class: androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$ProvideAndroidCompositionLocals$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        MutableState<Configuration> mutableState2 = mutableState;
                        Configuration configuration = new Configuration((Configuration) obj);
                        DynamicProvidableCompositionLocal dynamicProvidableCompositionLocal = AndroidCompositionLocals_androidKt.LocalConfiguration;
                        mutableState2.setValue(configuration);
                        return Unit.INSTANCE;
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue2);
            }
            androidComposeView.configurationChangeObserver = (Function1) objRememberedValue2;
            Object objRememberedValue3 = composerImpl.rememberedValue();
            if (objRememberedValue3 == composer$Companion$Empty$1) {
                objRememberedValue3 = new AndroidUriHandler(context);
                composerImpl.updateRememberedValue(objRememberedValue3);
            }
            final AndroidUriHandler androidUriHandler = (AndroidUriHandler) objRememberedValue3;
            AndroidComposeView.ViewTreeOwners viewTreeOwners = androidComposeView.getViewTreeOwners();
            if (viewTreeOwners == null) {
                throw new IllegalStateException("Called when the ViewTreeOwnersAvailability is not yet in Available state");
            }
            Object objRememberedValue4 = composerImpl.rememberedValue();
            SavedStateRegistryOwner savedStateRegistryOwner = viewTreeOwners.savedStateRegistryOwner;
            if (objRememberedValue4 == composer$Companion$Empty$1) {
                Class[] clsArr = DisposableSaveableStateRegistry_androidKt.AcceptableClasses;
                View view = (View) androidComposeView.getParent();
                Object tag = view.getTag(R.id.compose_view_saveable_id_tag);
                LinkedHashMap linkedHashMap = null;
                String strValueOf = tag instanceof String ? (String) tag : null;
                if (strValueOf == null) {
                    strValueOf = String.valueOf(view.getId());
                }
                final String strM = AndroidCompositionLocals_androidKt$$ExternalSyntheticOutline0.m("SaveableStateRegistry:", strValueOf);
                final SavedStateRegistry savedStateRegistry = savedStateRegistryOwner.getSavedStateRegistry();
                Bundle bundleConsumeRestoredStateForKey = savedStateRegistry.consumeRestoredStateForKey(strM);
                if (bundleConsumeRestoredStateForKey != null) {
                    linkedHashMap = new LinkedHashMap();
                    for (String str : bundleConsumeRestoredStateForKey.keySet()) {
                        linkedHashMap.put(str, bundleConsumeRestoredStateForKey.getParcelableArrayList(str));
                    }
                }
                final SaveableStateRegistry SaveableStateRegistry = SaveableStateRegistryKt.SaveableStateRegistry(linkedHashMap, new Function1() { // from class: androidx.compose.ui.platform.DisposableSaveableStateRegistry_androidKt$DisposableSaveableStateRegistry$saveableStateRegistry$1
                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        return Boolean.valueOf(DisposableSaveableStateRegistry_androidKt.canBeSavedToBundle(obj));
                    }
                });
                try {
                    savedStateRegistry.registerSavedStateProvider(strM, new SavedStateRegistry.SavedStateProvider() { // from class: androidx.compose.ui.platform.DisposableSaveableStateRegistry_androidKt$$ExternalSyntheticLambda0
                        @Override // androidx.savedstate.SavedStateRegistry.SavedStateProvider
                        public final Bundle saveState() {
                            Class[] clsArr2 = DisposableSaveableStateRegistry_androidKt.AcceptableClasses;
                            Map mapPerformSave = SaveableStateRegistry.performSave();
                            Bundle bundle = new Bundle();
                            for (Map.Entry entry : mapPerformSave.entrySet()) {
                                String str2 = (String) entry.getKey();
                                List list = (List) entry.getValue();
                                bundle.putParcelableArrayList(str2, list instanceof ArrayList ? (ArrayList) list : new ArrayList<>(list));
                            }
                            return bundle;
                        }
                    });
                    z = true;
                } catch (IllegalArgumentException unused) {
                    z = false;
                }
                DisposableSaveableStateRegistry disposableSaveableStateRegistry = new DisposableSaveableStateRegistry(SaveableStateRegistry, new Function0() { // from class: androidx.compose.ui.platform.DisposableSaveableStateRegistry_androidKt$DisposableSaveableStateRegistry$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // kotlin.jvm.functions.Function0
                    public final Object invoke() {
                        if (z) {
                            SavedStateRegistry savedStateRegistry2 = savedStateRegistry;
                            savedStateRegistry2.components.remove(strM);
                        }
                        return Unit.INSTANCE;
                    }
                });
                composerImpl.updateRememberedValue(disposableSaveableStateRegistry);
                objRememberedValue4 = disposableSaveableStateRegistry;
            }
            final DisposableSaveableStateRegistry disposableSaveableStateRegistry2 = (DisposableSaveableStateRegistry) objRememberedValue4;
            Unit unit = Unit.INSTANCE;
            boolean zChangedInstance = composerImpl.changedInstance(disposableSaveableStateRegistry2);
            Object objRememberedValue5 = composerImpl.rememberedValue();
            if (zChangedInstance || objRememberedValue5 == Composer.Companion.Empty) {
                objRememberedValue5 = new Function1() { // from class: androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$ProvideAndroidCompositionLocals$2$1
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj) {
                        final DisposableSaveableStateRegistry disposableSaveableStateRegistry3 = disposableSaveableStateRegistry2;
                        return new DisposableEffectResult() { // from class: androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$ProvideAndroidCompositionLocals$2$1$invoke$$inlined$onDispose$1
                            @Override // androidx.compose.runtime.DisposableEffectResult
                            public final void dispose() {
                                disposableSaveableStateRegistry3.onDispose.invoke();
                            }
                        };
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue5);
            }
            EffectsKt.DisposableEffect(unit, (Function1) objRememberedValue5, composerImpl);
            Object objRememberedValue6 = composerImpl.rememberedValue();
            Composer$Companion$Empty$1 composer$Companion$Empty$12 = Composer.Companion.Empty;
            if (objRememberedValue6 == composer$Companion$Empty$12) {
                HapticDefaults.INSTANCE.getClass();
                objRememberedValue6 = ((Vibrator) context.getSystemService(Vibrator.class)).areAllPrimitivesSupported(1, 7, 2) ? new DefaultHapticFeedback(androidComposeView) : new NoHapticFeedback();
                composerImpl.updateRememberedValue(objRememberedValue6);
            }
            HapticFeedback hapticFeedback = (HapticFeedback) objRememberedValue6;
            Configuration configuration = (Configuration) mutableState.getValue();
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.ui.platform.obtainImageVectorCache (AndroidCompositionLocals.android.kt:177)");
            }
            Object objRememberedValue7 = composerImpl.rememberedValue();
            if (objRememberedValue7 == composer$Companion$Empty$12) {
                objRememberedValue7 = new ImageVectorCache();
                composerImpl.updateRememberedValue(objRememberedValue7);
            }
            final ImageVectorCache imageVectorCache = (ImageVectorCache) objRememberedValue7;
            Object objRememberedValue8 = composerImpl.rememberedValue();
            Object obj = objRememberedValue8;
            if (objRememberedValue8 == composer$Companion$Empty$12) {
                Configuration configuration2 = new Configuration();
                if (configuration != null) {
                    configuration2.setTo(configuration);
                }
                composerImpl.updateRememberedValue(configuration2);
                obj = configuration2;
            }
            final Configuration configuration3 = (Configuration) obj;
            Object objRememberedValue9 = composerImpl.rememberedValue();
            if (objRememberedValue9 == composer$Companion$Empty$12) {
                objRememberedValue9 = new ComponentCallbacks2() { // from class: androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$obtainImageVectorCache$callbacks$1$1
                    @Override // android.content.ComponentCallbacks
                    public final void onConfigurationChanged(Configuration configuration4) {
                        int iUpdateFrom = configuration3.updateFrom(configuration4);
                        Iterator it = imageVectorCache.map.entrySet().iterator();
                        while (it.hasNext()) {
                            ImageVectorCache.ImageVectorEntry imageVectorEntry = (ImageVectorCache.ImageVectorEntry) ((WeakReference) ((Map.Entry) it.next()).getValue()).get();
                            if (imageVectorEntry == null || Configuration.needNewResources(iUpdateFrom, imageVectorEntry.configFlags)) {
                                it.remove();
                            }
                        }
                        configuration3.setTo(configuration4);
                    }

                    @Override // android.content.ComponentCallbacks
                    public final void onLowMemory() {
                        imageVectorCache.map.clear();
                    }

                    @Override // android.content.ComponentCallbacks2
                    public final void onTrimMemory(int i3) {
                        imageVectorCache.map.clear();
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue9);
            }
            final AndroidCompositionLocals_androidKt$obtainImageVectorCache$callbacks$1$1 androidCompositionLocals_androidKt$obtainImageVectorCache$callbacks$1$1 = (AndroidCompositionLocals_androidKt$obtainImageVectorCache$callbacks$1$1) objRememberedValue9;
            boolean zChangedInstance2 = composerImpl.changedInstance(context);
            Object objRememberedValue10 = composerImpl.rememberedValue();
            if (zChangedInstance2 || objRememberedValue10 == composer$Companion$Empty$12) {
                objRememberedValue10 = new Function1() { // from class: androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$obtainImageVectorCache$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj2) {
                        context.getApplicationContext().registerComponentCallbacks(androidCompositionLocals_androidKt$obtainImageVectorCache$callbacks$1$1);
                        final Context context2 = context;
                        final AndroidCompositionLocals_androidKt$obtainImageVectorCache$callbacks$1$1 androidCompositionLocals_androidKt$obtainImageVectorCache$callbacks$1$12 = androidCompositionLocals_androidKt$obtainImageVectorCache$callbacks$1$1;
                        return new DisposableEffectResult() { // from class: androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$obtainImageVectorCache$1$1$invoke$$inlined$onDispose$1
                            @Override // androidx.compose.runtime.DisposableEffectResult
                            public final void dispose() {
                                context2.getApplicationContext().unregisterComponentCallbacks(androidCompositionLocals_androidKt$obtainImageVectorCache$callbacks$1$12);
                            }
                        };
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue10);
            }
            EffectsKt.DisposableEffect(imageVectorCache, (Function1) objRememberedValue10, composerImpl);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventStart("androidx.compose.ui.platform.obtainResourceIdCache (AndroidCompositionLocals.android.kt:147)");
            }
            Object objRememberedValue11 = composerImpl.rememberedValue();
            if (objRememberedValue11 == composer$Companion$Empty$12) {
                objRememberedValue11 = new ResourceIdCache();
                composerImpl.updateRememberedValue(objRememberedValue11);
            }
            final ResourceIdCache resourceIdCache = (ResourceIdCache) objRememberedValue11;
            Object objRememberedValue12 = composerImpl.rememberedValue();
            if (objRememberedValue12 == composer$Companion$Empty$12) {
                objRememberedValue12 = new ComponentCallbacks2() { // from class: androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$obtainResourceIdCache$callbacks$1$1
                    @Override // android.content.ComponentCallbacks
                    public final void onConfigurationChanged(Configuration configuration4) {
                        resourceIdCache.clear();
                    }

                    @Override // android.content.ComponentCallbacks
                    public final void onLowMemory() {
                        resourceIdCache.clear();
                    }

                    @Override // android.content.ComponentCallbacks2
                    public final void onTrimMemory(int i3) {
                        resourceIdCache.clear();
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue12);
            }
            final AndroidCompositionLocals_androidKt$obtainResourceIdCache$callbacks$1$1 androidCompositionLocals_androidKt$obtainResourceIdCache$callbacks$1$1 = (AndroidCompositionLocals_androidKt$obtainResourceIdCache$callbacks$1$1) objRememberedValue12;
            boolean zChangedInstance3 = composerImpl.changedInstance(context);
            Object objRememberedValue13 = composerImpl.rememberedValue();
            if (zChangedInstance3 || objRememberedValue13 == composer$Companion$Empty$12) {
                objRememberedValue13 = new Function1() { // from class: androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$obtainResourceIdCache$1$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(1);
                    }

                    @Override // kotlin.jvm.functions.Function1
                    /* renamed from: invoke */
                    public final Object mo781invoke(Object obj2) {
                        context.getApplicationContext().registerComponentCallbacks(androidCompositionLocals_androidKt$obtainResourceIdCache$callbacks$1$1);
                        final Context context2 = context;
                        final AndroidCompositionLocals_androidKt$obtainResourceIdCache$callbacks$1$1 androidCompositionLocals_androidKt$obtainResourceIdCache$callbacks$1$12 = androidCompositionLocals_androidKt$obtainResourceIdCache$callbacks$1$1;
                        return new DisposableEffectResult() { // from class: androidx.compose.ui.platform.AndroidCompositionLocals_androidKt$obtainResourceIdCache$1$1$invoke$$inlined$onDispose$1
                            @Override // androidx.compose.runtime.DisposableEffectResult
                            public final void dispose() {
                                context2.getApplicationContext().unregisterComponentCallbacks(androidCompositionLocals_androidKt$obtainResourceIdCache$callbacks$1$12);
                            }
                        };
                    }
                };
                composerImpl.updateRememberedValue(objRememberedValue13);
            }
            EffectsKt.DisposableEffect(resourceIdCache, (Function1) objRememberedValue13, composerImpl);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
            DynamicProvidableCompositionLocal dynamicProvidableCompositionLocal = CompositionLocalsKt.LocalProvidableScrollCaptureInProgress;
            boolean zBooleanValue = ((Boolean) composerImpl.consume(dynamicProvidableCompositionLocal)).booleanValue();
            ScrollCapture scrollCapture = androidComposeView.scrollCapture;
            CompositionLocalKt.CompositionLocalProvider(new ProvidedValue[]{LocalConfiguration.defaultProvidedValue$runtime_release((Configuration) mutableState.getValue()), LocalContext.defaultProvidedValue$runtime_release(context), LocalLifecycleOwnerKt.LocalLifecycleOwner.defaultProvidedValue$runtime_release(viewTreeOwners.lifecycleOwner), LocalSavedStateRegistryOwner.defaultProvidedValue$runtime_release(savedStateRegistryOwner), SaveableStateRegistryKt.LocalSaveableStateRegistry.defaultProvidedValue$runtime_release(disposableSaveableStateRegistry2), LocalView.defaultProvidedValue$runtime_release(androidComposeView), LocalImageVectorCache.defaultProvidedValue$runtime_release(imageVectorCache), LocalResourceIdCache.defaultProvidedValue$runtime_release(resourceIdCache), dynamicProvidableCompositionLocal.defaultProvidedValue$runtime_release(Boolean.valueOf(zBooleanValue | (scrollCapture != null ? ((Boolean) ((SnapshotMutableStateImpl) scrollCapture.scrollCaptureInProgress$delegate).getValue()).booleanValue() : false))), CompositionLocalsKt.LocalHapticFeedback.defaultProvidedValue$runtime_release(hapticFeedback)}, ComposableLambdaKt.rememberComposableLambda(1471621628, new Function2() { // from class: androidx.compose.ui.platform.AndroidCompositionLocals_androidKt.ProvideAndroidCompositionLocals.3
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    Composer composer2 = (Composer) obj2;
                    int iIntValue = ((Number) obj3).intValue();
                    ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                    if (composerImpl2.shouldExecute(iIntValue & 1, (iIntValue & 3) != 2)) {
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("androidx.compose.ui.platform.ProvideAndroidCompositionLocals.<anonymous> (AndroidCompositionLocals.android.kt:141)");
                        }
                        CompositionLocalsKt.ProvideCommonCompositionLocals(androidComposeView, androidUriHandler, function2, composerImpl2, 0);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                    } else {
                        composerImpl2.skipToGroupEnd();
                    }
                    return Unit.INSTANCE;
                }
            }, composerImpl), composerImpl, 56);
            if (ComposerKt.isTraceInProgress()) {
                ComposerKt.traceEventEnd();
            }
        } else {
            composerImpl.skipToGroupEnd();
        }
        RecomposeScopeImpl recomposeScopeImplEndRestartGroup = composerImpl.endRestartGroup();
        if (recomposeScopeImplEndRestartGroup != null) {
            recomposeScopeImplEndRestartGroup.block = new Function2() { // from class: androidx.compose.ui.platform.AndroidCompositionLocals_androidKt.ProvideAndroidCompositionLocals.4
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                @Override // kotlin.jvm.functions.Function2
                public final Object invoke(Object obj2, Object obj3) {
                    ((Number) obj3).intValue();
                    AndroidCompositionLocals_androidKt.ProvideAndroidCompositionLocals(androidComposeView, function2, (Composer) obj2, RecomposeScopeImplKt.updateChangedFlags(i | 1));
                    return Unit.INSTANCE;
                }
            };
        }
    }

    public static final void access$noLocalProvidedFor(String str) {
        throw new IllegalStateException(("CompositionLocal " + str + " not present").toString());
    }

    public static final ProvidableCompositionLocal<LifecycleOwner> getLocalLifecycleOwner() {
        return LocalLifecycleOwnerKt.LocalLifecycleOwner;
    }
}
