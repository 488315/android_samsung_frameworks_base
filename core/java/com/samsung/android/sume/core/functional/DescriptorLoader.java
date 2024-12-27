package com.samsung.android.sume.core.functional;

import com.samsung.android.sume.core.descriptor.MFDescriptor;

@FunctionalInterface
public interface DescriptorLoader {
    MFDescriptor load();
}
