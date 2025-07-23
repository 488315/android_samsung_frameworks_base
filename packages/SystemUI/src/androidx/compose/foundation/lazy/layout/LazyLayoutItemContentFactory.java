package androidx.compose.foundation.lazy.layout;

import androidx.collection.MutableScatterMap;
import androidx.collection.ScatterMapKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.saveable.SaveableStateHolder;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class LazyLayoutItemContentFactory {
    public final Function0 itemProvider;
    public final MutableScatterMap lambdasCache = ScatterMapKt.mutableScatterMapOf();
    public final SaveableStateHolder saveableStateHolder;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    final class CachedItemContent {
        public ComposableLambdaImpl _content;
        public final Object contentType;
        public int index;
        public final Object key;

        public CachedItemContent(int i, Object obj, Object obj2) {
            this.key = obj;
            this.contentType = obj2;
            this.index = i;
        }
    }

    public LazyLayoutItemContentFactory(SaveableStateHolder saveableStateHolder, Function0 function0) {
        this.saveableStateHolder = saveableStateHolder;
        this.itemProvider = function0;
    }

    public final Function2 getContent(int i, Object obj, Object obj2) {
        MutableScatterMap mutableScatterMap = this.lambdasCache;
        final CachedItemContent cachedItemContent = (CachedItemContent) mutableScatterMap.get(obj);
        if (cachedItemContent != null && cachedItemContent.index == i && Intrinsics.areEqual(cachedItemContent.contentType, obj2)) {
            ComposableLambdaImpl composableLambdaImpl = cachedItemContent._content;
            if (composableLambdaImpl != null) {
                return composableLambdaImpl;
            }
            final LazyLayoutItemContentFactory lazyLayoutItemContentFactory = LazyLayoutItemContentFactory.this;
            ComposableLambdaImpl composableLambdaImpl2 = new ComposableLambdaImpl(1403994769, true, new Function2() { // from class: androidx.compose.foundation.lazy.layout.LazyLayoutItemContentFactory$CachedItemContent$createContentLambda$1
                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                {
                    super(2);
                }

                /* JADX WARN: Code restructure failed: missing block: B:19:0x0095, code lost:
                
                    if (r0 == androidx.compose.runtime.Composer.Companion.Empty) goto L27;
                 */
                @Override // kotlin.jvm.functions.Function2
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final java.lang.Object invoke(java.lang.Object r11, java.lang.Object r12) {
                    /*
                        r10 = this;
                        androidx.compose.runtime.Composer r11 = (androidx.compose.runtime.Composer) r11
                        java.lang.Number r12 = (java.lang.Number) r12
                        int r12 = r12.intValue()
                        r0 = r12 & 3
                        r1 = 1
                        r2 = 2
                        r3 = 0
                        if (r0 == r2) goto L11
                        r0 = r1
                        goto L12
                    L11:
                        r0 = r3
                    L12:
                        r12 = r12 & r1
                        r8 = r11
                        androidx.compose.runtime.ComposerImpl r8 = (androidx.compose.runtime.ComposerImpl) r8
                        boolean r11 = r8.shouldExecute(r12, r0)
                        if (r11 == 0) goto Lae
                        boolean r11 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                        if (r11 == 0) goto L27
                        java.lang.String r11 = "androidx.compose.foundation.lazy.layout.LazyLayoutItemContentFactory.CachedItemContent.createContentLambda.<anonymous> (LazyLayoutItemContentFactory.kt:87)"
                        androidx.compose.runtime.ComposerKt.traceEventStart(r11)
                    L27:
                        androidx.compose.foundation.lazy.layout.LazyLayoutItemContentFactory r11 = androidx.compose.foundation.lazy.layout.LazyLayoutItemContentFactory.this
                        kotlin.jvm.functions.Function0 r11 = r11.itemProvider
                        java.lang.Object r11 = r11.invoke()
                        r4 = r11
                        androidx.compose.foundation.lazy.layout.LazyLayoutItemProvider r4 = (androidx.compose.foundation.lazy.layout.LazyLayoutItemProvider) r4
                        androidx.compose.foundation.lazy.layout.LazyLayoutItemContentFactory$CachedItemContent r11 = r2
                        int r11 = r11.index
                        int r12 = r4.getItemCount()
                        r0 = -1
                        if (r11 >= r12) goto L4e
                        java.lang.Object r12 = r4.getKey(r11)
                        androidx.compose.foundation.lazy.layout.LazyLayoutItemContentFactory$CachedItemContent r1 = r2
                        java.lang.Object r1 = r1.key
                        boolean r12 = r12.equals(r1)
                        if (r12 != 0) goto L4c
                        goto L4e
                    L4c:
                        r6 = r11
                        goto L5d
                    L4e:
                        androidx.compose.foundation.lazy.layout.LazyLayoutItemContentFactory$CachedItemContent r11 = r2
                        java.lang.Object r11 = r11.key
                        int r11 = r4.getIndex(r11)
                        if (r11 == r0) goto L4c
                        androidx.compose.foundation.lazy.layout.LazyLayoutItemContentFactory$CachedItemContent r12 = r2
                        r12.index = r11
                        goto L4c
                    L5d:
                        if (r6 == r0) goto L75
                        r11 = -660404355(0xffffffffd8a3077d, float:-1.4340205E15)
                        r8.startReplaceGroup(r11)
                        androidx.compose.foundation.lazy.layout.LazyLayoutItemContentFactory r11 = androidx.compose.foundation.lazy.layout.LazyLayoutItemContentFactory.this
                        androidx.compose.runtime.saveable.SaveableStateHolder r5 = r11.saveableStateHolder
                        androidx.compose.foundation.lazy.layout.LazyLayoutItemContentFactory$CachedItemContent r11 = r2
                        java.lang.Object r7 = r11.key
                        r9 = 0
                        androidx.compose.foundation.lazy.layout.LazyLayoutItemContentFactoryKt.m168access$SkippableItemJVlU9Rs(r4, r5, r6, r7, r8, r9)
                        r8.end(r3)
                        goto L7e
                    L75:
                        r11 = -660169871(0xffffffffd8a69b71, float:-1.4654924E15)
                        r8.startReplaceGroup(r11)
                        r8.end(r3)
                    L7e:
                        androidx.compose.foundation.lazy.layout.LazyLayoutItemContentFactory$CachedItemContent r11 = r2
                        java.lang.Object r12 = r11.key
                        boolean r11 = r8.changedInstance(r11)
                        androidx.compose.foundation.lazy.layout.LazyLayoutItemContentFactory$CachedItemContent r10 = r2
                        java.lang.Object r0 = r8.rememberedValue()
                        if (r11 != 0) goto L97
                        androidx.compose.runtime.Composer$Companion r11 = androidx.compose.runtime.Composer.Companion
                        r11.getClass()
                        androidx.compose.runtime.Composer$Companion$Empty$1 r11 = androidx.compose.runtime.Composer.Companion.Empty
                        if (r0 != r11) goto L9f
                    L97:
                        androidx.compose.foundation.lazy.layout.LazyLayoutItemContentFactory$CachedItemContent$createContentLambda$1$1$1 r0 = new androidx.compose.foundation.lazy.layout.LazyLayoutItemContentFactory$CachedItemContent$createContentLambda$1$1$1
                        r0.<init>()
                        r8.updateRememberedValue(r0)
                    L9f:
                        kotlin.jvm.functions.Function1 r0 = (kotlin.jvm.functions.Function1) r0
                        androidx.compose.runtime.EffectsKt.DisposableEffect(r12, r0, r8)
                        boolean r10 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                        if (r10 == 0) goto Lb1
                        androidx.compose.runtime.ComposerKt.traceEventEnd()
                        goto Lb1
                    Lae:
                        r8.skipToGroupEnd()
                    Lb1:
                        kotlin.Unit r10 = kotlin.Unit.INSTANCE
                        return r10
                    */
                    throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.lazy.layout.LazyLayoutItemContentFactory$CachedItemContent$createContentLambda$1.invoke(java.lang.Object, java.lang.Object):java.lang.Object");
                }
            });
            cachedItemContent._content = composableLambdaImpl2;
            return composableLambdaImpl2;
        }
        final CachedItemContent cachedItemContent2 = new CachedItemContent(i, obj, obj2);
        mutableScatterMap.set(obj, cachedItemContent2);
        ComposableLambdaImpl composableLambdaImpl3 = cachedItemContent2._content;
        if (composableLambdaImpl3 != null) {
            return composableLambdaImpl3;
        }
        final LazyLayoutItemContentFactory lazyLayoutItemContentFactory2 = LazyLayoutItemContentFactory.this;
        ComposableLambdaImpl composableLambdaImpl4 = new ComposableLambdaImpl(1403994769, true, new Function2() { // from class: androidx.compose.foundation.lazy.layout.LazyLayoutItemContentFactory$CachedItemContent$createContentLambda$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj3, Object obj4) {
                /*
                    this = this;
                    androidx.compose.runtime.Composer r11 = (androidx.compose.runtime.Composer) r11
                    java.lang.Number r12 = (java.lang.Number) r12
                    int r12 = r12.intValue()
                    r0 = r12 & 3
                    r1 = 1
                    r2 = 2
                    r3 = 0
                    if (r0 == r2) goto L11
                    r0 = r1
                    goto L12
                L11:
                    r0 = r3
                L12:
                    r12 = r12 & r1
                    r8 = r11
                    androidx.compose.runtime.ComposerImpl r8 = (androidx.compose.runtime.ComposerImpl) r8
                    boolean r11 = r8.shouldExecute(r12, r0)
                    if (r11 == 0) goto Lae
                    boolean r11 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                    if (r11 == 0) goto L27
                    java.lang.String r11 = "androidx.compose.foundation.lazy.layout.LazyLayoutItemContentFactory.CachedItemContent.createContentLambda.<anonymous> (LazyLayoutItemContentFactory.kt:87)"
                    androidx.compose.runtime.ComposerKt.traceEventStart(r11)
                L27:
                    androidx.compose.foundation.lazy.layout.LazyLayoutItemContentFactory r11 = androidx.compose.foundation.lazy.layout.LazyLayoutItemContentFactory.this
                    kotlin.jvm.functions.Function0 r11 = r11.itemProvider
                    java.lang.Object r11 = r11.invoke()
                    r4 = r11
                    androidx.compose.foundation.lazy.layout.LazyLayoutItemProvider r4 = (androidx.compose.foundation.lazy.layout.LazyLayoutItemProvider) r4
                    androidx.compose.foundation.lazy.layout.LazyLayoutItemContentFactory$CachedItemContent r11 = r2
                    int r11 = r11.index
                    int r12 = r4.getItemCount()
                    r0 = -1
                    if (r11 >= r12) goto L4e
                    java.lang.Object r12 = r4.getKey(r11)
                    androidx.compose.foundation.lazy.layout.LazyLayoutItemContentFactory$CachedItemContent r1 = r2
                    java.lang.Object r1 = r1.key
                    boolean r12 = r12.equals(r1)
                    if (r12 != 0) goto L4c
                    goto L4e
                L4c:
                    r6 = r11
                    goto L5d
                L4e:
                    androidx.compose.foundation.lazy.layout.LazyLayoutItemContentFactory$CachedItemContent r11 = r2
                    java.lang.Object r11 = r11.key
                    int r11 = r4.getIndex(r11)
                    if (r11 == r0) goto L4c
                    androidx.compose.foundation.lazy.layout.LazyLayoutItemContentFactory$CachedItemContent r12 = r2
                    r12.index = r11
                    goto L4c
                L5d:
                    if (r6 == r0) goto L75
                    r11 = -660404355(0xffffffffd8a3077d, float:-1.4340205E15)
                    r8.startReplaceGroup(r11)
                    androidx.compose.foundation.lazy.layout.LazyLayoutItemContentFactory r11 = androidx.compose.foundation.lazy.layout.LazyLayoutItemContentFactory.this
                    androidx.compose.runtime.saveable.SaveableStateHolder r5 = r11.saveableStateHolder
                    androidx.compose.foundation.lazy.layout.LazyLayoutItemContentFactory$CachedItemContent r11 = r2
                    java.lang.Object r7 = r11.key
                    r9 = 0
                    androidx.compose.foundation.lazy.layout.LazyLayoutItemContentFactoryKt.m168access$SkippableItemJVlU9Rs(r4, r5, r6, r7, r8, r9)
                    r8.end(r3)
                    goto L7e
                L75:
                    r11 = -660169871(0xffffffffd8a69b71, float:-1.4654924E15)
                    r8.startReplaceGroup(r11)
                    r8.end(r3)
                L7e:
                    androidx.compose.foundation.lazy.layout.LazyLayoutItemContentFactory$CachedItemContent r11 = r2
                    java.lang.Object r12 = r11.key
                    boolean r11 = r8.changedInstance(r11)
                    androidx.compose.foundation.lazy.layout.LazyLayoutItemContentFactory$CachedItemContent r10 = r2
                    java.lang.Object r0 = r8.rememberedValue()
                    if (r11 != 0) goto L97
                    androidx.compose.runtime.Composer$Companion r11 = androidx.compose.runtime.Composer.Companion
                    r11.getClass()
                    androidx.compose.runtime.Composer$Companion$Empty$1 r11 = androidx.compose.runtime.Composer.Companion.Empty
                    if (r0 != r11) goto L9f
                L97:
                    androidx.compose.foundation.lazy.layout.LazyLayoutItemContentFactory$CachedItemContent$createContentLambda$1$1$1 r0 = new androidx.compose.foundation.lazy.layout.LazyLayoutItemContentFactory$CachedItemContent$createContentLambda$1$1$1
                    r0.<init>()
                    r8.updateRememberedValue(r0)
                L9f:
                    kotlin.jvm.functions.Function1 r0 = (kotlin.jvm.functions.Function1) r0
                    androidx.compose.runtime.EffectsKt.DisposableEffect(r12, r0, r8)
                    boolean r10 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                    if (r10 == 0) goto Lb1
                    androidx.compose.runtime.ComposerKt.traceEventEnd()
                    goto Lb1
                Lae:
                    r8.skipToGroupEnd()
                Lb1:
                    kotlin.Unit r10 = kotlin.Unit.INSTANCE
                    return r10
                */
                throw new UnsupportedOperationException("Method not decompiled: androidx.compose.foundation.lazy.layout.LazyLayoutItemContentFactory$CachedItemContent$createContentLambda$1.invoke(java.lang.Object, java.lang.Object):java.lang.Object");
            }
        });
        cachedItemContent2._content = composableLambdaImpl4;
        return composableLambdaImpl4;
    }

    public final Object getContentType(Object obj) {
        if (obj == null) {
            return null;
        }
        CachedItemContent cachedItemContent = (CachedItemContent) this.lambdasCache.get(obj);
        if (cachedItemContent != null) {
            return cachedItemContent.contentType;
        }
        LazyLayoutItemProvider lazyLayoutItemProvider = (LazyLayoutItemProvider) this.itemProvider.invoke();
        int index = lazyLayoutItemProvider.getIndex(obj);
        if (index != -1) {
            return lazyLayoutItemProvider.getContentType(index);
        }
        return null;
    }
}
