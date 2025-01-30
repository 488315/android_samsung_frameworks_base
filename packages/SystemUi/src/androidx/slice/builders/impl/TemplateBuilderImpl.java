package androidx.slice.builders.impl;

import androidx.slice.Clock;
import androidx.slice.Slice;
import androidx.slice.SliceSpec;
import androidx.slice.SystemClock;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
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
