package com.android.systemui.complication;

import androidx.lifecycle.MediatorLiveData;
import androidx.lifecycle.Transformations;
import androidx.lifecycle.ViewModel;
import com.android.systemui.complication.ComplicationId;
import java.util.Collection;
import java.util.HashMap;
import java.util.function.Function;
import java.util.stream.Collectors;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Reflection;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class ComplicationCollectionViewModel extends ViewModel {
    public final MediatorLiveData mComplications;
    public final ComplicationViewModelTransformer mTransformer;

    public ComplicationCollectionViewModel(ComplicationCollectionLiveData complicationCollectionLiveData, ComplicationViewModelTransformer complicationViewModelTransformer) {
        this.mComplications = Transformations.map(complicationCollectionLiveData, new Function1() { // from class: com.android.systemui.complication.ComplicationCollectionViewModel$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                final ComplicationCollectionViewModel complicationCollectionViewModel = ComplicationCollectionViewModel.this;
                complicationCollectionViewModel.getClass();
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
                        ComplicationViewModelProvider viewModelProvider = complicationViewModelTransformer2.mViewModelComponentFactory.create(complication, complicationId).getViewModelProvider();
                        String complicationId2 = complicationId.toString();
                        viewModelProvider.getClass();
                        return (ComplicationViewModel) viewModelProvider.impl.getViewModel$lifecycle_viewmodel_release(Reflection.getOrCreateKotlinClass(ComplicationViewModel.class), complicationId2);
                    }
                }).collect(Collectors.toSet());
            }
        });
        this.mTransformer = complicationViewModelTransformer;
    }
}
