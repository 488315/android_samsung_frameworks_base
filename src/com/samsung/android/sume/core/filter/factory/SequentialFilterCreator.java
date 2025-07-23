package com.samsung.android.sume.core.filter.factory;

import com.samsung.android.sume.core.descriptor.MFDescriptor;
import com.samsung.android.sume.core.descriptor.SequentialDescriptor;
import com.samsung.android.sume.core.filter.MediaFilter;
import com.samsung.android.sume.core.filter.collection.BufferedConveyorFilter;
import com.samsung.android.sume.core.filter.collection.SequentialFilter;
import com.samsung.android.sume.core.filter.collection.SequentialPickerFilter;
import com.samsung.android.sume.core.filter.collection.SimpleConveyorFilter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/* loaded from: classes6.dex */
public class SequentialFilterCreator implements MediaFilterCreator {
    @Override // com.samsung.android.sume.core.filter.factory.MediaFilterCreator
    public MediaFilter newFilter(MediaFilterFactory mediaFilterFactory, MFDescriptor mFDescriptor, MediaFilter mediaFilter) {
        MediaFilter sequentialPickerFilter;
        SequentialDescriptor sequentialDescriptor = (SequentialDescriptor) mFDescriptor;
        Stream<MFDescriptor> stream = sequentialDescriptor.getDescriptors().stream();
        Objects.requireNonNull(mediaFilterFactory);
        List<MediaFilter> list = (List) stream.map(new ParallelFilterCreator$$ExternalSyntheticLambda0(mediaFilterFactory)).collect(Collectors.toList());
        int i = AnonymousClass1.$SwitchMap$com$samsung$android$sume$core$filter$collection$SequentialFilter$Type[sequentialDescriptor.getSequentialType().ordinal()];
        if (i == 1) {
            sequentialPickerFilter = new SequentialPickerFilter(sequentialDescriptor);
        } else if (i != 2) {
            sequentialPickerFilter = null;
        } else if (sequentialDescriptor.getSequentialMode() == SequentialFilter.Mode.BATCHED) {
            sequentialPickerFilter = new SimpleConveyorFilter(sequentialDescriptor);
        } else {
            sequentialPickerFilter = new BufferedConveyorFilter(sequentialDescriptor);
        }
        ((SequentialFilter) Objects.requireNonNull(sequentialPickerFilter)).addFilter(list);
        return sequentialPickerFilter;
    }

    /* renamed from: com.samsung.android.sume.core.filter.factory.SequentialFilterCreator$1, reason: invalid class name */
    static /* synthetic */ class AnonymousClass1 {
        static final /* synthetic */ int[] $SwitchMap$com$samsung$android$sume$core$filter$collection$SequentialFilter$Type;

        static {
            int[] iArr = new int[SequentialFilter.Type.values().length];
            $SwitchMap$com$samsung$android$sume$core$filter$collection$SequentialFilter$Type = iArr;
            try {
                iArr[SequentialFilter.Type.PICKER.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                $SwitchMap$com$samsung$android$sume$core$filter$collection$SequentialFilter$Type[SequentialFilter.Type.CONVEYOR.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
        }
    }
}
