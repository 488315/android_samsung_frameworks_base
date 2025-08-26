package androidx.compose.foundation.lazy.layout;

import androidx.collection.MutableScatterMap;
import androidx.collection.ScatterMapKt;
import androidx.compose.foundation.lazy.layout.LazyLayoutItemContentFactory;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.DisposableEffectResult;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.saveable.SaveableStateHolder;
import kotlin.Unit;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Intrinsics;

/* loaded from: classes.dex */
public final class LazyLayoutItemContentFactory {
    public final Function0 itemProvider;
    public final MutableScatterMap lambdasCache = ScatterMapKt.mutableScatterMapOf();
    public final SaveableStateHolder saveableStateHolder;

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

                /* JADX WARN: Removed duplicated region for block: B:27:0x0097  */
                @Override // kotlin.jvm.functions.Function2
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final Object invoke(Object obj3, Object obj4) {
                    Composer composer = (Composer) obj3;
                    int iIntValue = ((Number) obj4).intValue();
                    ComposerImpl composerImpl = (ComposerImpl) composer;
                    if (composerImpl.shouldExecute(iIntValue & 1, (iIntValue & 3) != 2)) {
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventStart("androidx.compose.foundation.lazy.layout.LazyLayoutItemContentFactory.CachedItemContent.createContentLambda.<anonymous> (LazyLayoutItemContentFactory.kt:87)");
                        }
                        LazyLayoutItemProvider lazyLayoutItemProvider = (LazyLayoutItemProvider) this.this$0.itemProvider.invoke();
                        int index = cachedItemContent.index;
                        if ((index >= lazyLayoutItemProvider.getItemCount() || !lazyLayoutItemProvider.getKey(index).equals(cachedItemContent.key)) && (index = lazyLayoutItemProvider.getIndex(cachedItemContent.key)) != -1) {
                            cachedItemContent.index = index;
                        }
                        int i2 = index;
                        if (i2 != -1) {
                            composerImpl.startReplaceGroup(-660404355);
                            LazyLayoutItemContentFactoryKt.m169access$SkippableItemJVlU9Rs(lazyLayoutItemProvider, this.this$0.saveableStateHolder, i2, cachedItemContent.key, composerImpl, 0);
                            composerImpl.end(false);
                        } else {
                            composerImpl.startReplaceGroup(-660169871);
                            composerImpl.end(false);
                        }
                        LazyLayoutItemContentFactory.CachedItemContent cachedItemContent2 = cachedItemContent;
                        Object obj5 = cachedItemContent2.key;
                        boolean zChangedInstance = composerImpl.changedInstance(cachedItemContent2);
                        final LazyLayoutItemContentFactory.CachedItemContent cachedItemContent3 = cachedItemContent;
                        Object objRememberedValue = composerImpl.rememberedValue();
                        if (!zChangedInstance) {
                            Composer.Companion.getClass();
                            if (objRememberedValue == Composer.Companion.Empty) {
                                objRememberedValue = new Function1() { // from class: androidx.compose.foundation.lazy.layout.LazyLayoutItemContentFactory$CachedItemContent$createContentLambda$1$1$1
                                    {
                                        super(1);
                                    }

                                    @Override // kotlin.jvm.functions.Function1
                                    /* renamed from: invoke */
                                    public final Object mo781invoke(Object obj6) {
                                        final LazyLayoutItemContentFactory.CachedItemContent cachedItemContent4 = cachedItemContent3;
                                        return new DisposableEffectResult() { // from class: androidx.compose.foundation.lazy.layout.LazyLayoutItemContentFactory$CachedItemContent$createContentLambda$1$1$1$invoke$$inlined$onDispose$1
                                            @Override // androidx.compose.runtime.DisposableEffectResult
                                            public final void dispose() {
                                                cachedItemContent4._content = null;
                                            }
                                        };
                                    }
                                };
                                composerImpl.updateRememberedValue(objRememberedValue);
                            }
                            EffectsKt.DisposableEffect(obj5, (Function1) objRememberedValue, composerImpl);
                            if (ComposerKt.isTraceInProgress()) {
                                ComposerKt.traceEventEnd();
                            }
                        }
                    } else {
                        composerImpl.skipToGroupEnd();
                    }
                    return Unit.INSTANCE;
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

            /* JADX WARN: Removed duplicated region for block: B:27:0x0097  */
            @Override // kotlin.jvm.functions.Function2
            /*
                Code decompiled incorrectly, please refer to instructions dump.
            */
            public final Object invoke(Object obj3, Object obj4) {
                Composer composer = (Composer) obj3;
                int iIntValue = ((Number) obj4).intValue();
                ComposerImpl composerImpl = (ComposerImpl) composer;
                if (composerImpl.shouldExecute(iIntValue & 1, (iIntValue & 3) != 2)) {
                    if (ComposerKt.isTraceInProgress()) {
                        ComposerKt.traceEventStart("androidx.compose.foundation.lazy.layout.LazyLayoutItemContentFactory.CachedItemContent.createContentLambda.<anonymous> (LazyLayoutItemContentFactory.kt:87)");
                    }
                    LazyLayoutItemProvider lazyLayoutItemProvider = (LazyLayoutItemProvider) this.this$0.itemProvider.invoke();
                    int index = cachedItemContent2.index;
                    if ((index >= lazyLayoutItemProvider.getItemCount() || !lazyLayoutItemProvider.getKey(index).equals(cachedItemContent2.key)) && (index = lazyLayoutItemProvider.getIndex(cachedItemContent2.key)) != -1) {
                        cachedItemContent2.index = index;
                    }
                    int i2 = index;
                    if (i2 != -1) {
                        composerImpl.startReplaceGroup(-660404355);
                        LazyLayoutItemContentFactoryKt.m169access$SkippableItemJVlU9Rs(lazyLayoutItemProvider, this.this$0.saveableStateHolder, i2, cachedItemContent2.key, composerImpl, 0);
                        composerImpl.end(false);
                    } else {
                        composerImpl.startReplaceGroup(-660169871);
                        composerImpl.end(false);
                    }
                    LazyLayoutItemContentFactory.CachedItemContent cachedItemContent22 = cachedItemContent2;
                    Object obj5 = cachedItemContent22.key;
                    boolean zChangedInstance = composerImpl.changedInstance(cachedItemContent22);
                    final LazyLayoutItemContentFactory.CachedItemContent cachedItemContent3 = cachedItemContent2;
                    Object objRememberedValue = composerImpl.rememberedValue();
                    if (!zChangedInstance) {
                        Composer.Companion.getClass();
                        if (objRememberedValue == Composer.Companion.Empty) {
                            objRememberedValue = new Function1() { // from class: androidx.compose.foundation.lazy.layout.LazyLayoutItemContentFactory$CachedItemContent$createContentLambda$1$1$1
                                {
                                    super(1);
                                }

                                @Override // kotlin.jvm.functions.Function1
                                /* renamed from: invoke */
                                public final Object mo781invoke(Object obj6) {
                                    final LazyLayoutItemContentFactory.CachedItemContent cachedItemContent4 = cachedItemContent3;
                                    return new DisposableEffectResult() { // from class: androidx.compose.foundation.lazy.layout.LazyLayoutItemContentFactory$CachedItemContent$createContentLambda$1$1$1$invoke$$inlined$onDispose$1
                                        @Override // androidx.compose.runtime.DisposableEffectResult
                                        public final void dispose() {
                                            cachedItemContent4._content = null;
                                        }
                                    };
                                }
                            };
                            composerImpl.updateRememberedValue(objRememberedValue);
                        }
                        EffectsKt.DisposableEffect(obj5, (Function1) objRememberedValue, composerImpl);
                        if (ComposerKt.isTraceInProgress()) {
                            ComposerKt.traceEventEnd();
                        }
                    }
                } else {
                    composerImpl.skipToGroupEnd();
                }
                return Unit.INSTANCE;
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
