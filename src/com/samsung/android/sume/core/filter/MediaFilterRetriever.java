package com.samsung.android.sume.core.filter;

import android.util.Log;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.filter.MediaFilterRetriever;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* loaded from: classes6.dex */
public final class MediaFilterRetriever {
    private static final String TAG = Def.tagOf((Class<?>) MediaFilterRetriever.class);
    private final Map<Predictor, PredicateHandler> predictorMap = new HashMap();

    @FunctionalInterface
    public interface PredicateHandler {
        void onPredicate(MediaFilter mediaFilter, MediaFilter mediaFilter2);
    }

    @FunctionalInterface
    public interface Predictor {
        boolean predicate(MediaFilter mediaFilter);
    }

    public MediaFilterRetriever addPredicateHandler(Predictor predictor, PredicateHandler predicateHandler) {
        this.predictorMap.put(predictor, predicateHandler);
        return this;
    }

    public void retrieve(MediaFilter mediaFilter) {
        Log.d(TAG, "retrieve root mediaFilter: " + mediaFilter);
        retrieve(mediaFilter, (MediaFilter) null);
    }

    public void retrieve(final DecorateFilter decorateFilter, final MediaFilter mediaFilter) {
        Log.d(TAG, "retrieve DecorateFilter: " + decorateFilter);
        this.predictorMap.forEach(new BiConsumer() { // from class: com.samsung.android.sume.core.filter.MediaFilterRetriever$$ExternalSyntheticLambda0
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                MediaFilterRetriever.lambda$retrieve$0(decorateFilter, mediaFilter, (MediaFilterRetriever.Predictor) obj, (MediaFilterRetriever.PredicateHandler) obj2);
            }
        });
        if (decorateFilter.getSuccessorFilter() != null) {
            decorateFilter.getSuccessorFilter().accept(this, decorateFilter);
        }
    }

    static /* synthetic */ void lambda$retrieve$0(DecorateFilter decorateFilter, MediaFilter mediaFilter, Predictor predictor, PredicateHandler predicateHandler) {
        if (predictor.predicate(decorateFilter)) {
            predicateHandler.onPredicate(decorateFilter, mediaFilter);
        }
    }

    public void retrieve(final ImgpDecorateFilter imgpDecorateFilter, final MediaFilter mediaFilter) {
        Log.d(TAG, "retrieve ImgpDecorateFilter: " + imgpDecorateFilter);
        this.predictorMap.forEach(new BiConsumer() { // from class: com.samsung.android.sume.core.filter.MediaFilterRetriever$$ExternalSyntheticLambda4
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                MediaFilterRetriever.lambda$retrieve$1(imgpDecorateFilter, mediaFilter, (MediaFilterRetriever.Predictor) obj, (MediaFilterRetriever.PredicateHandler) obj2);
            }
        });
        if (imgpDecorateFilter.getPreFilter() != null) {
            imgpDecorateFilter.getPreFilter().accept(this, imgpDecorateFilter);
        }
        if (imgpDecorateFilter.getPostFilter() != null) {
            imgpDecorateFilter.getPostFilter().accept(this, imgpDecorateFilter);
        }
        if (imgpDecorateFilter.getSuccessorFilter() != null) {
            imgpDecorateFilter.getSuccessorFilter().accept(this, imgpDecorateFilter);
        }
    }

    static /* synthetic */ void lambda$retrieve$1(ImgpDecorateFilter imgpDecorateFilter, MediaFilter mediaFilter, Predictor predictor, PredicateHandler predicateHandler) {
        if (predictor.predicate(imgpDecorateFilter)) {
            predicateHandler.onPredicate(imgpDecorateFilter, mediaFilter);
        }
    }

    public void retrieve(final MediaFilterGroup mediaFilterGroup, MediaFilter mediaFilter) {
        Log.d(TAG, "retrieve MediaFilterGroup: " + mediaFilterGroup);
        mediaFilterGroup.stream().forEach(new Consumer() { // from class: com.samsung.android.sume.core.filter.MediaFilterRetriever$$ExternalSyntheticLambda3
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.m9553xba46685a(mediaFilterGroup, (MediaFilter) obj);
            }
        });
    }

    /* renamed from: lambda$retrieve$2$com-samsung-android-sume-core-filter-MediaFilterRetriever, reason: not valid java name */
    /* synthetic */ void m9553xba46685a(MediaFilterGroup mediaFilterGroup, MediaFilter mediaFilter) {
        mediaFilter.accept(this, mediaFilterGroup);
    }

    public void retrieve(final MediaFilterPlaceHolder mediaFilterPlaceHolder, final MediaFilter mediaFilter) {
        Log.d(TAG, "retrieve MediaFilterPlaceHolder: " + mediaFilterPlaceHolder);
        this.predictorMap.forEach(new BiConsumer() { // from class: com.samsung.android.sume.core.filter.MediaFilterRetriever$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                MediaFilterRetriever.lambda$retrieve$3(mediaFilterPlaceHolder, mediaFilter, (MediaFilterRetriever.Predictor) obj, (MediaFilterRetriever.PredicateHandler) obj2);
            }
        });
    }

    static /* synthetic */ void lambda$retrieve$3(MediaFilterPlaceHolder mediaFilterPlaceHolder, MediaFilter mediaFilter, Predictor predictor, PredicateHandler predicateHandler) {
        if (predictor.predicate(mediaFilterPlaceHolder)) {
            predicateHandler.onPredicate(mediaFilterPlaceHolder, mediaFilter);
        }
    }

    public void retrieve(final MediaFilter mediaFilter, final MediaFilter mediaFilter2) {
        if (mediaFilter instanceof ImgpDecorateFilter) {
            retrieve((ImgpDecorateFilter) mediaFilter, mediaFilter2);
            return;
        }
        if (mediaFilter instanceof DecorateFilter) {
            retrieve((DecorateFilter) mediaFilter, mediaFilter2);
            return;
        }
        if (mediaFilter instanceof MediaFilterGroup) {
            retrieve((MediaFilterGroup) mediaFilter, mediaFilter2);
            return;
        }
        if (mediaFilter instanceof MediaFilterPlaceHolder) {
            retrieve((MediaFilterPlaceHolder) mediaFilter, mediaFilter2);
            return;
        }
        Log.d(TAG, "retrieve MediaFilter: " + mediaFilter);
        this.predictorMap.forEach(new BiConsumer() { // from class: com.samsung.android.sume.core.filter.MediaFilterRetriever$$ExternalSyntheticLambda2
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
                MediaFilterRetriever.lambda$retrieve$4(mediaFilter, mediaFilter2, (MediaFilterRetriever.Predictor) obj, (MediaFilterRetriever.PredicateHandler) obj2);
            }
        });
    }

    static /* synthetic */ void lambda$retrieve$4(MediaFilter mediaFilter, MediaFilter mediaFilter2, Predictor predictor, PredicateHandler predicateHandler) {
        if (predictor.predicate(mediaFilter)) {
            predicateHandler.onPredicate(mediaFilter, mediaFilter2);
        }
    }
}
