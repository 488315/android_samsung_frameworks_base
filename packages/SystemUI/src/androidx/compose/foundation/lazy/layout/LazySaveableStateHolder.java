package androidx.compose.foundation.lazy.layout;

import androidx.collection.MutableScatterSet;
import androidx.collection.ScatterSetKt;
import androidx.compose.runtime.saveable.SaveableStateHolder;
import androidx.compose.runtime.saveable.SaveableStateRegistry;
import androidx.compose.runtime.saveable.SaveableStateRegistryKt;
import java.util.List;
import java.util.Map;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
final class LazySaveableStateHolder implements SaveableStateRegistry, SaveableStateHolder {
    public static final Companion Companion = new Companion(null);
    public final MutableScatterSet previouslyComposedKeys;
    public final SaveableStateHolder wrappedHolder;
    public final SaveableStateRegistry wrappedRegistry;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public LazySaveableStateHolder(SaveableStateRegistry saveableStateRegistry, SaveableStateHolder saveableStateHolder) {
        this.wrappedRegistry = saveableStateRegistry;
        this.wrappedHolder = saveableStateHolder;
        this.previouslyComposedKeys = ScatterSetKt.mutableScatterSetOf();
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x0030, code lost:
    
        if (r5 == androidx.compose.runtime.Composer.Companion.Empty) goto L9;
     */
    @Override // androidx.compose.runtime.saveable.SaveableStateHolder
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void SaveableStateProvider(final java.lang.Object r2, kotlin.jvm.functions.Function2 r3, androidx.compose.runtime.Composer r4, int r5) {
        /*
            r1 = this;
            androidx.compose.runtime.ComposerImpl r4 = (androidx.compose.runtime.ComposerImpl) r4
            r0 = -697180401(0xffffffffd671df0f, float:-6.6485083E13)
            r4.startReplaceGroup(r0)
            boolean r0 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r0 == 0) goto L13
            java.lang.String r0 = "androidx.compose.foundation.lazy.layout.LazySaveableStateHolder.SaveableStateProvider (LazySaveableStateHolder.kt:74)"
            androidx.compose.runtime.ComposerKt.traceEventStart(r0)
        L13:
            r5 = r5 & 126(0x7e, float:1.77E-43)
            androidx.compose.runtime.saveable.SaveableStateHolder r0 = r1.wrappedHolder
            r0.SaveableStateProvider(r2, r3, r4, r5)
            boolean r3 = r4.changedInstance(r1)
            boolean r5 = r4.changedInstance(r2)
            r3 = r3 | r5
            java.lang.Object r5 = r4.rememberedValue()
            if (r3 != 0) goto L32
            androidx.compose.runtime.Composer$Companion r3 = androidx.compose.runtime.Composer.Companion
            r3.getClass()
            androidx.compose.runtime.Composer$Companion$Empty$1 r3 = androidx.compose.runtime.Composer.Companion.Empty
            if (r5 != r3) goto L3a
        L32:
            androidx.compose.foundation.lazy.layout.LazySaveableStateHolder$SaveableStateProvider$1$1 r5 = new androidx.compose.foundation.lazy.layout.LazySaveableStateHolder$SaveableStateProvider$1$1
            r5.<init>()
            r4.updateRememberedValue(r5)
        L3a:
            kotlin.jvm.functions.Function1 r5 = (kotlin.jvm.functions.Function1) r5
            androidx.compose.runtime.EffectsKt.DisposableEffect(r2, r5, r4)
            boolean r1 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
            if (r1 == 0) goto L48
            androidx.compose.runtime.ComposerKt.traceEventEnd()
        L48:
            r1 = 0
            r4.end(r1)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.lazy.layout.LazySaveableStateHolder.SaveableStateProvider(java.lang.Object, kotlin.jvm.functions.Function2, androidx.compose.runtime.Composer, int):void");
    }

    @Override // androidx.compose.runtime.saveable.SaveableStateRegistry
    public final boolean canBeSaved(Object obj) {
        return this.wrappedRegistry.canBeSaved(obj);
    }

    @Override // androidx.compose.runtime.saveable.SaveableStateRegistry
    public final Object consumeRestored(String str) {
        return this.wrappedRegistry.consumeRestored(str);
    }

    @Override // androidx.compose.runtime.saveable.SaveableStateRegistry
    public final Map performSave() {
        MutableScatterSet mutableScatterSet = this.previouslyComposedKeys;
        Object[] objArr = mutableScatterSet.elements;
        long[] jArr = mutableScatterSet.metadata;
        int length = jArr.length - 2;
        if (length >= 0) {
            int i = 0;
            while (true) {
                long j = jArr[i];
                if ((((~j) << 7) & j & (-9187201950435737472L)) != -9187201950435737472L) {
                    int i2 = 8 - ((~(i - length)) >>> 31);
                    for (int i3 = 0; i3 < i2; i3++) {
                        if ((255 & j) < 128) {
                            this.wrappedHolder.removeState(objArr[(i << 3) + i3]);
                        }
                        j >>= 8;
                    }
                    if (i2 != 8) {
                        break;
                    }
                }
                if (i == length) {
                    break;
                }
                i++;
            }
        }
        return this.wrappedRegistry.performSave();
    }

    @Override // androidx.compose.runtime.saveable.SaveableStateRegistry
    public final SaveableStateRegistry.Entry registerProvider(String str, Function0 function0) {
        return this.wrappedRegistry.registerProvider(str, function0);
    }

    @Override // androidx.compose.runtime.saveable.SaveableStateHolder
    public final void removeState(Object obj) {
        this.wrappedHolder.removeState(obj);
    }

    public LazySaveableStateHolder(final SaveableStateRegistry saveableStateRegistry, Map<String, ? extends List<? extends Object>> map, SaveableStateHolder saveableStateHolder) {
        this(SaveableStateRegistryKt.SaveableStateRegistry(map, new Function1() { // from class: androidx.compose.foundation.lazy.layout.LazySaveableStateHolder.1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                SaveableStateRegistry saveableStateRegistry2 = SaveableStateRegistry.this;
                return Boolean.valueOf(saveableStateRegistry2 != null ? saveableStateRegistry2.canBeSaved(obj) : true);
            }
        }), saveableStateHolder);
    }
}
