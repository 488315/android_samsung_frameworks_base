package com.samsung.android.sume.core.descriptor;

import com.samsung.android.sume.core.evaluate.Evaluator;
import com.samsung.android.sume.core.filter.collection.SequentialFilter;

import java.util.List;

public class SequentialDescriptor extends MFDescriptorBase {
    private final List<MFDescriptor> descriptors;
    private List<Evaluator> evaluators;
    private final SequentialFilter.Mode sequentialMode;
    private final SequentialFilter.Type sequentialType;

    public SequentialDescriptor(SequentialFilter.Type type, List<MFDescriptor> descriptors) {
        this.sequentialType = type;
        this.sequentialMode = SequentialFilter.Mode.BATCHED;
        this.descriptors = descriptors;
        setFilterId(type.name());
    }

    public SequentialDescriptor(
            SequentialFilter.Type type,
            SequentialFilter.Mode mode,
            List<MFDescriptor> descriptors) {
        this.sequentialType = type;
        this.sequentialMode = mode;
        this.descriptors = descriptors;
        setFilterId(type.name());
    }

    public List<MFDescriptor> getDescriptors() {
        return this.descriptors;
    }

    @Override // com.samsung.android.sume.core.descriptor.MFDescriptorBase,
              // com.samsung.android.sume.core.descriptor.MFDescriptor
    public Class<?> getFilterType() {
        return SequentialFilter.class;
    }

    public SequentialFilter.Type getSequentialType() {
        return this.sequentialType;
    }

    public SequentialFilter.Mode getSequentialMode() {
        return this.sequentialMode;
    }

    public List<Evaluator> getEvaluators() {
        return this.evaluators;
    }

    public void setEvaluators(List<Evaluator> evaluators) {
        this.evaluators = evaluators;
    }
}
