package com.samsung.android.sume.core.filter;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.util.Log;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MediaBufferGroup;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer;
import com.samsung.android.sume.core.descriptor.MFDescriptor;
import com.samsung.android.sume.core.descriptor.nn.NNFWDescriptor;
import com.samsung.android.sume.core.format.MediaFormat;
import com.samsung.android.sume.core.format.MutableMediaFormat;
import com.samsung.android.sume.core.functional.BiBufferProcessor;
import com.samsung.android.sume.core.functional.ExecuteDelegator;
import com.samsung.android.sume.core.types.Status;
import com.samsung.android.sume.solution.filter.UniImgp;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Stream;

/* loaded from: classes6.dex */
public abstract class NNFWFilter implements MediaFilter {
    private static final String TAG = Def.tagOf((Class<?>) NNFWFilter.class);
    protected NNFWDescriptor descriptor;
    protected ExecuteDelegator executeDelegator;
    private MediaFormat targetFormat;

    public abstract Status runAdapter(MediaBuffer mediaBuffer, MediaBuffer mediaBuffer2);

    public NNFWFilter(NNFWDescriptor nNFWDescriptor) {
        this.descriptor = nNFWDescriptor;
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilter
    public MFDescriptor getDescriptor() {
        return this.descriptor;
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilter
    public Stream<MediaFilter> stream() {
        return Stream.of(this);
    }

    public void setExecuteDelegator(ExecuteDelegator executeDelegator) {
        this.executeDelegator = executeDelegator;
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilter
    public void prepare() {
        if (this.descriptor.getTargetFormat() != null) {
            this.targetFormat = this.descriptor.getTargetFormat().toMediaFormat();
        }
    }

    @Override // com.samsung.android.sume.core.functional.Operator
    public MutableMediaBuffer run(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
        if (mutableMediaBuffer.isEmpty()) {
            MediaFormat format = mediaBuffer.getFormat();
            MutableMediaFormat mutableMediaFormat = (MutableMediaFormat) Optional.ofNullable(this.descriptor.getOutputFormat()).map(new Function() { // from class: com.samsung.android.sume.core.filter.NNFWFilter$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((MutableMediaFormat) obj).copy();
                }
            }).orElse(null);
            Def.require((format == null && mutableMediaFormat == null) ? false : true);
            if (format == null || mutableMediaFormat == null) {
                if (mutableMediaFormat == null) {
                    mutableMediaFormat = ((MediaFormat) Objects.requireNonNull(format)).toMutableFormat();
                }
            } else if (mutableMediaFormat.getShape() == null || mutableMediaFormat.getShape().isEmpty()) {
                mutableMediaFormat.setShape(format.getShape());
            }
            mutableMediaBuffer.setFormat(mutableMediaFormat.toMediaFormat());
        }
        long currentTimeMillis = System.currentTimeMillis();
        try {
            ExecuteDelegator executeDelegator = this.executeDelegator;
            if (executeDelegator != null) {
                executeDelegator.execute(mediaBuffer, mutableMediaBuffer, new BiBufferProcessor() { // from class: com.samsung.android.sume.core.filter.NNFWFilter$$ExternalSyntheticLambda1
                    @Override // com.samsung.android.sume.core.functional.BiBufferProcessor
                    public final void process(MediaBuffer mediaBuffer2, MediaBuffer mediaBuffer3) {
                        NNFWFilter.this.runAdapter(mediaBuffer2, mediaBuffer3);
                    }
                });
            } else {
                mutableMediaBuffer.put(MediaBuffer.of(mutableMediaBuffer.getFormat()));
                runAdapter(mediaBuffer, mutableMediaBuffer);
            }
            mutableMediaBuffer.addExtra(mediaBuffer.getExtra());
            if (mediaBuffer instanceof MediaBufferGroup) {
                while (mediaBuffer instanceof MediaBufferGroup) {
                    mediaBuffer = ((MediaBufferGroup) mediaBuffer).getPrimaryBuffer();
                }
                mutableMediaBuffer.addExtra(((MediaBuffer) Objects.requireNonNull(mediaBuffer)).getExtra());
            }
            if (this.targetFormat != null) {
                Log.d(TAG, "convert to target-format: " + this.targetFormat);
                mutableMediaBuffer.put((MediaBuffer) UniImgp.ofCvtData().run((MediaBuffer) mutableMediaBuffer, MediaBuffer.mutableOf(this.targetFormat)));
            }
            Log.d(TAG, NavigationBarInflaterView.SIZE_MOD_START + this.descriptor.getFw() + "] processing nn ts: " + (System.currentTimeMillis() - currentTimeMillis) + "ms");
            return mutableMediaBuffer;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(Def.makeExceptionTag(e, this), e);
        }
    }
}
