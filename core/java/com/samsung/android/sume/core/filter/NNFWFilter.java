package com.samsung.android.sume.core.filter;

import android.inputmethodservice.navigationbar.NavigationBarInflaterView;
import android.util.Log;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MediaBufferGroup;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer;
import com.samsung.android.sume.core.descriptor.MFDescriptor;
import com.samsung.android.sume.core.descriptor.p044nn.NNFWDescriptor;
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

/* loaded from: classes4.dex */
public abstract class NNFWFilter implements MediaFilter {
    private static final String TAG = Def.tagOf((Class<?>) NNFWFilter.class);
    protected NNFWDescriptor descriptor;
    protected ExecuteDelegator executeDelegator;
    private MediaFormat targetFormat;

    public abstract Status runAdapter(MediaBuffer mediaBuffer, MediaBuffer mediaBuffer2);

    public NNFWFilter(NNFWDescriptor descriptor) {
        this.descriptor = descriptor;
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilter
    public MFDescriptor getDescriptor() {
        return this.descriptor;
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilter
    public Stream<MediaFilter> stream() {
        return Stream.of(this);
    }

    public void setExecuteDelegator(ExecuteDelegator delegator) {
        this.executeDelegator = delegator;
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilter
    public void prepare() {
        if (this.descriptor.getTargetFormat() != null) {
            this.targetFormat = this.descriptor.getTargetFormat().toMediaFormat();
        }
    }

    @Override // com.samsung.android.sume.core.functional.Operator
    public MutableMediaBuffer run(MediaBuffer ibuf, MutableMediaBuffer obuf) {
        if (obuf.isEmpty()) {
            MediaFormat iFmt = ibuf.getFormat();
            MutableMediaFormat oFmt = (MutableMediaFormat) Optional.ofNullable(this.descriptor.getOutputFormat()).map(new Function() { // from class: com.samsung.android.sume.core.filter.NNFWFilter$$ExternalSyntheticLambda0
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((MutableMediaFormat) obj).copy();
                }
            }).orElse(null);
            Def.require((iFmt == null && oFmt == null) ? false : true);
            if (iFmt != null && oFmt != null) {
                if (oFmt.getShape() == null || oFmt.getShape().isEmpty()) {
                    oFmt.setShape(iFmt.getShape());
                }
            } else if (oFmt == null) {
                oFmt = ((MediaFormat) Objects.requireNonNull(iFmt)).toMutableFormat();
            }
            obuf.setFormat(oFmt.toMediaFormat());
        }
        long triggerTs = System.currentTimeMillis();
        try {
            ExecuteDelegator executeDelegator = this.executeDelegator;
            if (executeDelegator != null) {
                executeDelegator.execute(ibuf, obuf, new BiBufferProcessor() { // from class: com.samsung.android.sume.core.filter.NNFWFilter$$ExternalSyntheticLambda1
                    @Override // com.samsung.android.sume.core.functional.BiBufferProcessor
                    public final void process(MediaBuffer mediaBuffer, MediaBuffer mediaBuffer2) {
                        NNFWFilter.this.runAdapter(mediaBuffer, mediaBuffer2);
                    }
                });
            } else {
                obuf.put(MediaBuffer.m323of(obuf.getFormat()));
                runAdapter(ibuf, obuf);
            }
            obuf.addExtra(ibuf.getExtra());
            if (ibuf instanceof MediaBufferGroup) {
                MediaBuffer primaryBuffer = ibuf;
                while (primaryBuffer instanceof MediaBufferGroup) {
                    primaryBuffer = ((MediaBufferGroup) primaryBuffer).getPrimaryBuffer();
                }
                obuf.addExtra(((MediaBuffer) Objects.requireNonNull(primaryBuffer)).getExtra());
            }
            if (this.targetFormat != null) {
                Log.m94d(TAG, "convert to target-format: " + this.targetFormat);
                obuf.put((MediaBuffer) UniImgp.ofCvtData().run((MediaBuffer) obuf, MediaBuffer.mutableOf(this.targetFormat)));
            }
            Log.m94d(TAG, NavigationBarInflaterView.SIZE_MOD_START + this.descriptor.getFw() + "] processing nn ts: " + (System.currentTimeMillis() - triggerTs) + "ms");
            return obuf;
        } catch (Exception e) {
            e.printStackTrace();
            throw new IllegalStateException(Def.makeExceptionTag(e, this), e);
        }
    }
}
