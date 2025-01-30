package com.samsung.android.sume.core.filter;

import android.util.Log;
import com.samsung.android.sume.core.Def;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

/* loaded from: classes4.dex */
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

  public MediaFilterRetriever addPredicateHandler(
      Predictor predictor, PredicateHandler predicateHandler) {
    this.predictorMap.put(predictor, predicateHandler);
    return this;
  }

  public void retrieve(MediaFilter mediaFilter) {
    Log.m94d(TAG, "retrieve root mediaFilter: " + mediaFilter);
    retrieve(mediaFilter, (MediaFilter) null);
  }

  public void retrieve(final DecorateFilter decorateFilter, final MediaFilter parent) {
    Log.m94d(TAG, "retrieve DecorateFilter: " + decorateFilter);
    this.predictorMap.forEach(
        new BiConsumer() { // from class:
                           // com.samsung.android.sume.core.filter.MediaFilterRetriever$$ExternalSyntheticLambda0
          @Override // java.util.function.BiConsumer
          public final void accept(Object obj, Object obj2) {
            MediaFilterRetriever.lambda$retrieve$0(
                DecorateFilter.this,
                parent,
                (MediaFilterRetriever.Predictor) obj,
                (MediaFilterRetriever.PredicateHandler) obj2);
          }
        });
    if (decorateFilter.getSuccessorFilter() != null) {
      decorateFilter.getSuccessorFilter().accept(this, decorateFilter);
    }
  }

  static /* synthetic */ void lambda$retrieve$0(
      DecorateFilter decorateFilter,
      MediaFilter parent,
      Predictor predictor,
      PredicateHandler predicateHandler) {
    if (predictor.predicate(decorateFilter)) {
      predicateHandler.onPredicate(decorateFilter, parent);
    }
  }

  public void retrieve(final ImgpDecorateFilter imgpDecorateFilter, final MediaFilter parent) {
    Log.m94d(TAG, "retrieve ImgpDecorateFilter: " + imgpDecorateFilter);
    this.predictorMap.forEach(
        new BiConsumer() { // from class:
                           // com.samsung.android.sume.core.filter.MediaFilterRetriever$$ExternalSyntheticLambda4
          @Override // java.util.function.BiConsumer
          public final void accept(Object obj, Object obj2) {
            MediaFilterRetriever.lambda$retrieve$1(
                ImgpDecorateFilter.this,
                parent,
                (MediaFilterRetriever.Predictor) obj,
                (MediaFilterRetriever.PredicateHandler) obj2);
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

  static /* synthetic */ void lambda$retrieve$1(
      ImgpDecorateFilter imgpDecorateFilter,
      MediaFilter parent,
      Predictor predictor,
      PredicateHandler predicateHandler) {
    if (predictor.predicate(imgpDecorateFilter)) {
      predicateHandler.onPredicate(imgpDecorateFilter, parent);
    }
  }

  public void retrieve(final MediaFilterGroup filterGroup, MediaFilter parent) {
    Log.m94d(TAG, "retrieve MediaFilterGroup: " + filterGroup);
    filterGroup.stream()
        .forEach(
            new Consumer() { // from class:
                             // com.samsung.android.sume.core.filter.MediaFilterRetriever$$ExternalSyntheticLambda2
              @Override // java.util.function.Consumer
              public final void accept(Object obj) {
                MediaFilterRetriever.this.m371xba46685a(filterGroup, (MediaFilter) obj);
              }
            });
  }

  /* renamed from: lambda$retrieve$2$com-samsung-android-sume-core-filter-MediaFilterRetriever */
  /* synthetic */ void m371xba46685a(MediaFilterGroup filterGroup, MediaFilter it) {
    it.accept(this, filterGroup);
  }

  public void retrieve(
      final MediaFilterPlaceHolder mediaFilterPlaceHolder, final MediaFilter parent) {
    Log.m94d(TAG, "retrieve MediaFilterPlaceHolder: " + mediaFilterPlaceHolder);
    this.predictorMap.forEach(
        new BiConsumer() { // from class:
                           // com.samsung.android.sume.core.filter.MediaFilterRetriever$$ExternalSyntheticLambda3
          @Override // java.util.function.BiConsumer
          public final void accept(Object obj, Object obj2) {
            MediaFilterRetriever.lambda$retrieve$3(
                MediaFilterPlaceHolder.this,
                parent,
                (MediaFilterRetriever.Predictor) obj,
                (MediaFilterRetriever.PredicateHandler) obj2);
          }
        });
  }

  static /* synthetic */ void lambda$retrieve$3(
      MediaFilterPlaceHolder mediaFilterPlaceHolder,
      MediaFilter parent,
      Predictor predictor,
      PredicateHandler predicateHandler) {
    if (predictor.predicate(mediaFilterPlaceHolder)) {
      predicateHandler.onPredicate(mediaFilterPlaceHolder, parent);
    }
  }

  public void retrieve(final MediaFilter mediaFilter, final MediaFilter parent) {
    if (mediaFilter instanceof ImgpDecorateFilter) {
      retrieve((ImgpDecorateFilter) mediaFilter, parent);
      return;
    }
    if (mediaFilter instanceof DecorateFilter) {
      retrieve((DecorateFilter) mediaFilter, parent);
      return;
    }
    if (mediaFilter instanceof MediaFilterGroup) {
      retrieve((MediaFilterGroup) mediaFilter, parent);
    } else if (mediaFilter instanceof MediaFilterPlaceHolder) {
      retrieve((MediaFilterPlaceHolder) mediaFilter, parent);
    } else {
      Log.m94d(TAG, "retrieve MediaFilter: " + mediaFilter);
      this.predictorMap.forEach(
          new BiConsumer() { // from class:
                             // com.samsung.android.sume.core.filter.MediaFilterRetriever$$ExternalSyntheticLambda1
            @Override // java.util.function.BiConsumer
            public final void accept(Object obj, Object obj2) {
              MediaFilterRetriever.lambda$retrieve$4(
                  MediaFilter.this,
                  parent,
                  (MediaFilterRetriever.Predictor) obj,
                  (MediaFilterRetriever.PredicateHandler) obj2);
            }
          });
    }
  }

  static /* synthetic */ void lambda$retrieve$4(
      MediaFilter mediaFilter,
      MediaFilter parent,
      Predictor predictor,
      PredicateHandler predicateHandler) {
    if (predictor.predicate(mediaFilter)) {
      predicateHandler.onPredicate(mediaFilter, parent);
    }
  }
}
