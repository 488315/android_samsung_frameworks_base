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

/* loaded from: classes4.dex */
public class SequentialFilterCreator implements MediaFilterCreator {
  @Override // com.samsung.android.sume.core.filter.factory.MediaFilterCreator
  public MediaFilter newFilter(
      MediaFilterFactory factory, MFDescriptor descriptor, MediaFilter successor) {
    SequentialDescriptor desc = (SequentialDescriptor) descriptor;
    Stream<MFDescriptor> stream = desc.getDescriptors().stream();
    Objects.requireNonNull(factory);
    List<MediaFilter> successorFilters =
        (List)
            stream
                .map(new ParallelFilterCreator$$ExternalSyntheticLambda0(factory))
                .collect(Collectors.toList());
    SequentialFilter sequentialFilters = null;
    switch (C52801.f3059xf798ed10[desc.getSequentialType().ordinal()]) {
      case 1:
        sequentialFilters = new SequentialPickerFilter(desc);
        break;
      case 2:
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

  /* renamed from: com.samsung.android.sume.core.filter.factory.SequentialFilterCreator$1 */
  static /* synthetic */ class C52801 {

    /* renamed from: $SwitchMap$com$samsung$android$sume$core$filter$collection$SequentialFilter$Type */
    static final /* synthetic */ int[] f3059xf798ed10;

    static {
      int[] iArr = new int[SequentialFilter.Type.values().length];
      f3059xf798ed10 = iArr;
      try {
        iArr[SequentialFilter.Type.PICKER.ordinal()] = 1;
      } catch (NoSuchFieldError e) {
      }
      try {
        f3059xf798ed10[SequentialFilter.Type.CONVEYOR.ordinal()] = 2;
      } catch (NoSuchFieldError e2) {
      }
    }
  }
}
