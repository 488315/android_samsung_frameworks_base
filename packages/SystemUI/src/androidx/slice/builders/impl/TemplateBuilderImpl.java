package androidx.slice.builders.impl;

import androidx.slice.Clock;
import androidx.slice.Slice;
import androidx.slice.SliceSpec;
import androidx.slice.SystemClock;

/* loaded from: classes.dex */
public abstract class TemplateBuilderImpl {
    public final Clock mClock;
    public Slice.Builder mSliceBuilder;
    public final SliceSpec mSpec;

    public TemplateBuilderImpl(Slice.Builder builder, SliceSpec sliceSpec) {
        this(builder, sliceSpec, new SystemClock());
    }

    public abstract void apply(Slice.Builder builder);

    public Slice build() {
        Slice.Builder builder = this.mSliceBuilder;
        builder.mSpec = this.mSpec;
        apply(builder);
        return this.mSliceBuilder.build();
    }

    public TemplateBuilderImpl(Slice.Builder builder, SliceSpec sliceSpec, Clock clock) {
        this.mSliceBuilder = builder;
        this.mSpec = sliceSpec;
        this.mClock = clock;
    }
}
