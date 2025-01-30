package com.samsung.android.sume.core.filter;

import android.graphics.Rect;
import android.util.Log;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer;
import com.samsung.android.sume.core.descriptor.ImgpDescriptor;
import com.samsung.android.sume.core.descriptor.MFDescriptor;
import com.samsung.android.sume.core.format.MediaFormat;
import com.samsung.android.sume.core.format.MutableMediaFormat;
import com.samsung.android.sume.core.format.MutableShape;
import com.samsung.android.sume.core.format.Shape;
import com.samsung.android.sume.core.types.ColorFormat;
import com.samsung.android.sume.core.types.DataType;
import com.samsung.android.sume.core.types.SplitType;
import com.samsung.android.sume.solution.filter.UniImgp;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.IntPredicate;
import java.util.function.Predicate;
import java.util.stream.Stream;

/* loaded from: classes4.dex */
public class ImgpDecorateFilter extends DecorateFilter {
    private static final String TAG = Def.tagOf((Class<?>) ImgpDecorateFilter.class);
    private MediaFilter postFilter;
    private ImgpDescriptor postImgpDescriptor;
    private MediaFilter preFilter;
    private ImgpDescriptor preImgpDescriptor;

    ImgpDecorateFilter(MediaFilter filter) {
        super(filter);
    }

    public void setPreFilter(MediaFilter preFilter) {
        this.preFilter = preFilter;
        if (preFilter != null) {
            this.preImgpDescriptor = (ImgpDescriptor) preFilter.getDescriptor();
        }
    }

    public MediaFilter getPreFilter() {
        return this.preFilter;
    }

    public MediaFilter getPostFilter() {
        return this.postFilter;
    }

    public void setPostFilter(MediaFilter postFilter) {
        this.postFilter = postFilter;
        if (postFilter != null) {
            this.postImgpDescriptor = (ImgpDescriptor) postFilter.getDescriptor();
        }
    }

    @Override // com.samsung.android.sume.core.filter.DecorateFilter, com.samsung.android.sume.core.functional.Operator
    public MutableMediaBuffer run(final MediaBuffer ibuf, final MutableMediaBuffer obuf) {
        String str = TAG;
        Log.m94d(str, "run: pre=" + this.preImgpDescriptor + ", post=" + this.postImgpDescriptor);
        SplitType splitType = (SplitType) Optional.ofNullable(this.preImgpDescriptor).map(new Function() { // from class: com.samsung.android.sume.core.filter.ImgpDecorateFilter$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((ImgpDescriptor) obj).getSplitType();
            }
        }).orElse(SplitType.NONE);
        MediaFormat orgFormat = ibuf.getFormat();
        MediaBuffer input = (MediaBuffer) Optional.ofNullable(this.preFilter).map(new Function() { // from class: com.samsung.android.sume.core.filter.ImgpDecorateFilter$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                MutableMediaBuffer run;
                run = ((MediaFilter) obj).run(MediaBuffer.this);
                return run;
            }
        }).orElse(MediaBuffer.mutableOf(ibuf));
        input.addExtra(ibuf.getExtra());
        super.run(input, obuf);
        if (splitType == SplitType.TILE) {
            Objects.requireNonNull(this.preImgpDescriptor);
            Objects.requireNonNull(this.postImgpDescriptor);
            this.postImgpDescriptor.getFormat().set("merge-type", SplitType.TILE);
            Shape iFilterShape = (Shape) Optional.ofNullable(this.preImgpDescriptor.getFormat()).map(new Function() { // from class: com.samsung.android.sume.core.filter.ImgpDecorateFilter$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((MutableMediaFormat) obj).getShape();
                }
            }).orElse(input.getFormat().getShape());
            Shape oFilterShape = (Shape) Optional.ofNullable(this.postImgpDescriptor.getFormat()).map(new Function() { // from class: com.samsung.android.sume.core.filter.ImgpDecorateFilter$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((MutableMediaFormat) obj).getShape();
                }
            }).orElse(obuf.getFormat().getShape());
            final float scaleY = oFilterShape.getRows() / iFilterShape.getRows();
            final float scaleX = oFilterShape.getCols() / iFilterShape.getCols();
            MutableShape shape = orgFormat.getShape().toMutableShape();
            shape.setRows((int) (shape.getRows() * scaleY));
            shape.setCols((int) (shape.getCols() * scaleX));
            if (obuf.containsExtra("force-rotate") && Arrays.stream(new int[]{90, 270}).anyMatch(new IntPredicate() { // from class: com.samsung.android.sume.core.filter.ImgpDecorateFilter$$ExternalSyntheticLambda3
                @Override // java.util.function.IntPredicate
                public final boolean test(int i) {
                    return ImgpDecorateFilter.lambda$run$1(MutableMediaBuffer.this, i);
                }
            })) {
                int cols = shape.getCols();
                shape.setCols(shape.getRows());
                shape.setRows(cols);
            }
            MediaBuffer oRefBuf = MediaBuffer.mutableOf(MediaFormat.mutableImageOf(Optional.ofNullable(this.postImgpDescriptor.getFormat()).map(new Function() { // from class: com.samsung.android.sume.core.filter.ImgpDecorateFilter$$ExternalSyntheticLambda4
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ImgpDecorateFilter.lambda$run$2(MutableMediaBuffer.this, (MutableMediaFormat) obj);
                }
            }).orElse(obuf.getFormat().getDataType()), shape.toShape(), Optional.ofNullable(this.postImgpDescriptor.getFormat()).map(new Function() { // from class: com.samsung.android.sume.core.filter.ImgpDecorateFilter$$ExternalSyntheticLambda5
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ImgpDecorateFilter.lambda$run$3(MutableMediaBuffer.this, (MutableMediaFormat) obj);
                }
            }).orElse(obuf.getFormat().getColorFormat())));
            if (scaleX * scaleY != 1.0f) {
                obuf.stream().forEach(new Consumer() { // from class: com.samsung.android.sume.core.filter.ImgpDecorateFilter$$ExternalSyntheticLambda6
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ImgpDecorateFilter.lambda$run$4(scaleY, scaleX, (MediaBuffer) obj);
                    }
                });
            }
            MediaBuffer buffers = MediaBuffer.groupOf(oRefBuf, obuf.asList());
            buffers.addExtra(obuf.getExtra());
            if (this.postFilter != null) {
                obuf.reset();
                this.postFilter.run(buffers, obuf);
            }
            buffers.release();
        } else if (getDescriptor().getOption().isInputWithEvaluationValue()) {
            MediaBuffer buffer = MediaBuffer.groupOf(input.asRef().copy(), obuf.asList());
            buffer.setFlags(2);
            obuf.put(buffer);
        } else {
            MediaFilter mediaFilter = this.postFilter;
            if (mediaFilter != null) {
                mediaFilter.run(obuf.reset(), obuf);
            }
        }
        boolean keepFilterDataType = Stream.of((Object[]) new MFDescriptor[]{getDescriptor(), this.postImgpDescriptor}).anyMatch(new Predicate() { // from class: com.samsung.android.sume.core.filter.ImgpDecorateFilter$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                boolean isKeepFilterDatatype;
                isKeepFilterDatatype = ((MFDescriptor) obj).getOption().isKeepFilterDatatype();
                return isKeepFilterDatatype;
            }
        });
        if (!keepFilterDataType) {
            Log.m94d(str, "convert output data-type to one of input");
            obuf.put((MediaBuffer) UniImgp.ofCvtData().run(obuf.get(), MediaBuffer.mutableOf(MediaFormat.imageOf(orgFormat.getDataType()))));
        }
        obuf.addExtra(obuf.getExtra());
        obuf.addExtra(input.getExtra());
        if (ibuf != input) {
            input.release();
        }
        Log.m94d(str, "ret: obuf=" + obuf);
        return obuf;
    }

    static /* synthetic */ boolean lambda$run$1(MutableMediaBuffer output, int it) {
        return it == ((Integer) output.getExtra("force-rotate")).intValue();
    }

    static /* synthetic */ DataType lambda$run$2(MutableMediaBuffer output, MutableMediaFormat it) {
        DataType dataType = it.getDataType();
        return dataType == DataType.NONE ? output.getFormat().getDataType() : dataType;
    }

    static /* synthetic */ ColorFormat lambda$run$3(MutableMediaBuffer output, MutableMediaFormat it) {
        ColorFormat cf = it.getColorFormat();
        return cf == ColorFormat.NONE ? output.getFormat().getColorFormat() : cf;
    }

    static /* synthetic */ void lambda$run$4(float scaleY, float scaleX, MediaBuffer e) {
        if (e.containsAllExtra("roi-on-block", "roi-on-image")) {
            Rect roiOnBlock = (Rect) e.getExtra("roi-on-block");
            roiOnBlock.top = (int) (roiOnBlock.top * scaleY);
            roiOnBlock.bottom = (int) (roiOnBlock.bottom * scaleY);
            roiOnBlock.right = (int) (roiOnBlock.right * scaleX);
            roiOnBlock.left = (int) (roiOnBlock.left * scaleX);
            Rect roiOnImage = (Rect) e.getExtra("roi-on-image");
            roiOnImage.top = (int) (roiOnImage.top * scaleY);
            roiOnImage.bottom = (int) (roiOnImage.bottom * scaleY);
            roiOnImage.right = (int) (roiOnImage.right * scaleX);
            roiOnImage.left = (int) (roiOnImage.left * scaleX);
        }
    }
}
