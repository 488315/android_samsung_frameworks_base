package androidx.compose.runtime.saveable;

import androidx.collection.MutableScatterMap;
import androidx.collection.ScatterMapKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.Composer$Companion$Empty$1;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.DisposableEffectResult;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.StaticProvidableCompositionLocal;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes.dex */
final class SaveableStateHolderImpl implements SaveableStateHolder {
    public static final Companion Companion = new Companion(null);
    public static final SaverKt$Saver$1 Saver;
    public final Function1 canBeSaved;
    public SaveableStateRegistry parentSaveableStateRegistry;
    public final MutableScatterMap registries;
    public final Map savedStates;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        SaveableStateHolderImpl$Companion$Saver$1 saveableStateHolderImpl$Companion$Saver$1 = new Function2() { // from class: androidx.compose.runtime.saveable.SaveableStateHolderImpl$Companion$Saver$1
            /* JADX WARN: Removed duplicated region for block: B:17:0x005a  */
            @Override // kotlin.jvm.functions.Function2
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object invoke(Object obj, Object obj2) {
                SaveableStateHolderImpl saveableStateHolderImpl = (SaveableStateHolderImpl) obj2;
                Map map = saveableStateHolderImpl.savedStates;
                MutableScatterMap mutableScatterMap = saveableStateHolderImpl.registries;
                Object[] objArr = mutableScatterMap.keys;
                Object[] objArr2 = mutableScatterMap.values;
                long[] jArr = mutableScatterMap.metadata;
                int length = jArr.length - 2;
                if (length >= 0) {
                    int i = 0;
                    while (true) {
                        long j = jArr[i];
                        if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                            int i2 = 8 - ((~(i - length)) >>> 31);
                            for (int i3 = 0; i3 < i2; i3++) {
                                if ((255 & j) < 128) {
                                    int i4 = (i << 3) + i3;
                                    Object obj3 = objArr[i4];
                                    Map mapPerformSave = ((SaveableStateRegistry) objArr2[i4]).performSave();
                                    if (mapPerformSave.isEmpty()) {
                                        map.remove(obj3);
                                    } else {
                                        map.put(obj3, mapPerformSave);
                                    }
                                }
                                j >>= 8;
                            }
                            if (i2 != 8) {
                                break;
                            }
                            if (i == length) {
                                break;
                            }
                            i++;
                        }
                    }
                }
                if (map.isEmpty()) {
                    return null;
                }
                return map;
            }
        };
        SaveableStateHolderImpl$Companion$Saver$2 saveableStateHolderImpl$Companion$Saver$2 = new Function1() { // from class: androidx.compose.runtime.saveable.SaveableStateHolderImpl$Companion$Saver$2
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo781invoke(Object obj) {
                return new SaveableStateHolderImpl((Map) obj);
            }
        };
        SaverKt$Saver$1 saverKt$Saver$1 = SaverKt.AutoSaver;
        Saver = new SaverKt$Saver$1(saveableStateHolderImpl$Companion$Saver$1, saveableStateHolderImpl$Companion$Saver$2);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public SaveableStateHolderImpl() {
        this(null, 1, 0 == true ? 1 : 0);
    }

    @Override // androidx.compose.runtime.saveable.SaveableStateHolder
    public final void SaveableStateProvider(final Object obj, Function2 function2, Composer composer, int i) {
        ComposerImpl composerImpl = (ComposerImpl) composer;
        composerImpl.startReplaceGroup(-1198538093);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventStart("androidx.compose.runtime.saveable.SaveableStateHolderImpl.SaveableStateProvider (SaveableStateHolder.kt:69)");
        }
        composerImpl.startReusableGroup(obj);
        Object objRememberedValue = composerImpl.rememberedValue();
        Composer.Companion.getClass();
        Composer$Companion$Empty$1 composer$Companion$Empty$1 = Composer.Companion.Empty;
        if (objRememberedValue == composer$Companion$Empty$1) {
            Function1 function1 = this.canBeSaved;
            if (!((Boolean) ((SaveableStateHolderImpl$canBeSaved$1) function1).mo781invoke(obj)).booleanValue()) {
                throw new IllegalArgumentException(("Type of the key " + obj + " is not supported. On Android you can only use types which can be stored inside the Bundle.").toString());
            }
            Map map = (Map) this.savedStates.get(obj);
            StaticProvidableCompositionLocal staticProvidableCompositionLocal = SaveableStateRegistryKt.LocalSaveableStateRegistry;
            SaveableStateRegistryImpl saveableStateRegistryImpl = new SaveableStateRegistryImpl(map, function1);
            composerImpl.updateRememberedValue(saveableStateRegistryImpl);
            objRememberedValue = saveableStateRegistryImpl;
        }
        final SaveableStateRegistry saveableStateRegistry = (SaveableStateRegistry) objRememberedValue;
        CompositionLocalKt.CompositionLocalProvider(SaveableStateRegistryKt.LocalSaveableStateRegistry.defaultProvidedValue$runtime_release(saveableStateRegistry), function2, composerImpl, (i & 112) | 8);
        Unit unit = Unit.INSTANCE;
        boolean zChangedInstance = composerImpl.changedInstance(this) | composerImpl.changedInstance(obj) | composerImpl.changedInstance(saveableStateRegistry);
        Object objRememberedValue2 = composerImpl.rememberedValue();
        if (zChangedInstance || objRememberedValue2 == composer$Companion$Empty$1) {
            objRememberedValue2 = new Function1() { // from class: androidx.compose.runtime.saveable.SaveableStateHolderImpl$SaveableStateProvider$1$1$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(1);
                }

                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj2) {
                    boolean zContains = this.this$0.registries.contains(obj);
                    Object obj3 = obj;
                    if (zContains) {
                        throw new IllegalArgumentException(("Key " + obj3 + " was used multiple times ").toString());
                    }
                    this.this$0.savedStates.remove(obj3);
                    this.this$0.registries.set(obj, saveableStateRegistry);
                    final SaveableStateHolderImpl saveableStateHolderImpl = this.this$0;
                    final Object obj4 = obj;
                    final SaveableStateRegistry saveableStateRegistry2 = saveableStateRegistry;
                    return new DisposableEffectResult() { // from class: androidx.compose.runtime.saveable.SaveableStateHolderImpl$SaveableStateProvider$1$1$1$invoke$$inlined$onDispose$1
                        @Override // androidx.compose.runtime.DisposableEffectResult
                        public final void dispose() {
                            SaveableStateHolderImpl saveableStateHolderImpl2 = saveableStateHolderImpl;
                            MutableScatterMap mutableScatterMap = saveableStateHolderImpl2.registries;
                            Object obj5 = obj4;
                            Object objRemove = mutableScatterMap.remove(obj5);
                            SaveableStateRegistry saveableStateRegistry3 = saveableStateRegistry2;
                            if (objRemove == saveableStateRegistry3) {
                                Map map2 = saveableStateHolderImpl2.savedStates;
                                Map mapPerformSave = saveableStateRegistry3.performSave();
                                if (mapPerformSave.isEmpty()) {
                                    map2.remove(obj5);
                                } else {
                                    map2.put(obj5, mapPerformSave);
                                }
                            }
                        }
                    };
                }
            };
            composerImpl.updateRememberedValue(objRememberedValue2);
        }
        EffectsKt.DisposableEffect(unit, (Function1) objRememberedValue2, composerImpl);
        if (composerImpl.reusing && composerImpl.reader.parent == composerImpl.reusingGroup) {
            composerImpl.reusingGroup = -1;
            composerImpl.reusing = false;
        }
        composerImpl.end(false);
        if (ComposerKt.isTraceInProgress()) {
            ComposerKt.traceEventEnd();
        }
        composerImpl.end(false);
    }

    @Override // androidx.compose.runtime.saveable.SaveableStateHolder
    public final void removeState(Object obj) {
        if (this.registries.remove(obj) == null) {
            this.savedStates.remove(obj);
        }
    }

    public SaveableStateHolderImpl(Map<Object, Map<String, List<Object>>> map) {
        this.savedStates = map;
        this.registries = ScatterMapKt.mutableScatterMapOf();
        this.canBeSaved = new SaveableStateHolderImpl$canBeSaved$1(this);
    }

    public /* synthetic */ SaveableStateHolderImpl(Map map, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this((i & 1) != 0 ? new LinkedHashMap() : map);
    }
}
