package com.android.systemui.complication;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import com.android.systemui.complication.ComplicationId;
import com.android.systemui.dagger.DaggerReferenceGlobalRootComponent;
import java.util.Collection;
import java.util.HashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public class ComplicationCollectionViewModel extends ViewModel {
    public final MediatorLiveData mComplications;
    public final ComplicationViewModelTransformer mTransformer;

    public ComplicationCollectionViewModel(ComplicationCollectionLiveData complicationCollectionLiveData, ComplicationViewModelTransformer complicationViewModelTransformer) {
        this.mComplications = Transformations.map(complicationCollectionLiveData, new Function1() { // from class: com.android.systemui.complication.ComplicationCollectionViewModel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj) {
                final ComplicationCollectionViewModel complicationCollectionViewModel = ComplicationCollectionViewModel.this;
                return (Collection) ((Collection) obj).stream().map(new Function() { // from class: com.android.systemui.complication.ComplicationCollectionViewModel$$ExternalSyntheticLambda1
                    @Override // java.util.function.Function
                    public final Object apply(Object obj2) {
                        Complication complication = (Complication) obj2;
                        ComplicationViewModelTransformer complicationViewModelTransformer2 = ComplicationCollectionViewModel.this.mTransformer;
                        if (!complicationViewModelTransformer2.mComplicationIdMapping.containsKey(complication)) {
                            HashMap hashMap = complicationViewModelTransformer2.mComplicationIdMapping;
                            ComplicationId.Factory factory = complicationViewModelTransformer2.mComplicationIdFactory;
                            int i = factory.mNextId;
                            factory.mNextId = i + 1;
                            hashMap.put(complication, new ComplicationId(i, 0));
                        }
                        ComplicationId complicationId = (ComplicationId) complicationViewModelTransformer2.mComplicationIdMapping.get(complication);
                        ComplicationViewModelProvider viewModelProvider = ((DaggerReferenceGlobalRootComponent.ComplicationViewModelComponentImpl) complicationViewModelTransformer2.mViewModelComponentFactory.create(complication, complicationId)).getViewModelProvider();
                        String complicationId2 = complicationId.toString();
                        return (ComplicationViewModel) viewModelProvider.impl.getViewModel$lifecycle_viewmodel_release(Reflection.getOrCreateKotlinClass(ComplicationViewModel.class), complicationId2);
                    }
                }).collect(Collectors.toSet());
            }
        });
        this.mTransformer = complicationViewModelTransformer;
    }
}
