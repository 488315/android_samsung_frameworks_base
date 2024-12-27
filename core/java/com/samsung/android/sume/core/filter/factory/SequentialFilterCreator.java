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

public class SequentialFilterCreator implements MediaFilterCreator {
    @Override // com.samsung.android.sume.core.filter.factory.MediaFilterCreator
    public MediaFilter newFilter(
            MediaFilterFactory factory, MFDescriptor descriptor, MediaFilter successor) {
        SequentialDescriptor desc = (SequentialDescriptor) descriptor;
        Stream<MFDescriptor> stream = desc.getDescriptors().stream();
        Objects.requireNonNull(factory);
        List<MediaFilter> successorFilters =
                (List)
                        stream.map(new ParallelFilterCreator$$ExternalSyntheticLambda0(factory))
                                .collect(Collectors.toList());
        SequentialFilter sequentialFilters = null;
        switch (desc.getSequentialType()) {
            case PICKER:
                sequentialFilters = new SequentialPickerFilter(desc);
                break;
            case CONVEYOR:
                if (desc.getSequentialMode() == SequentialFilter.Mode.BATCHED) {
                    sequentialFilters = new SimpleConveyorFilter(desc);
                    break;
                } else {
                    sequentialFilters = new BufferedConveyorFilter(desc);
                    break;
                }
        }
        ((SequentialFilter) Objects.requireNonNull(sequentialFilters)).addFilter(successorFilters);
        return sequentialFilters;
    }
}
