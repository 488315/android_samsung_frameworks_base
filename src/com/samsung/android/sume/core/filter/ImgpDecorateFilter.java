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

/* loaded from: classes6.dex */
public class ImgpDecorateFilter extends DecorateFilter {
    private static final String TAG = Def.tagOf((Class<?>) ImgpDecorateFilter.class);
    private MediaFilter postFilter;
    private ImgpDescriptor postImgpDescriptor;
    private MediaFilter preFilter;
    private ImgpDescriptor preImgpDescriptor;

    ImgpDecorateFilter(MediaFilter mediaFilter) {
        super(mediaFilter);
    }

    public void setPreFilter(MediaFilter mediaFilter) {
        this.preFilter = mediaFilter;
        if (mediaFilter != null) {
            this.preImgpDescriptor = (ImgpDescriptor) mediaFilter.getDescriptor();
        }
    }

    public MediaFilter getPreFilter() {
        return this.preFilter;
    }

    public MediaFilter getPostFilter() {
        return this.postFilter;
    }

    public void setPostFilter(MediaFilter mediaFilter) {
        this.postFilter = mediaFilter;
        if (mediaFilter != null) {
            this.postImgpDescriptor = (ImgpDescriptor) mediaFilter.getDescriptor();
        }
    }

    @Override // com.samsung.android.sume.core.filter.DecorateFilter, com.samsung.android.sume.core.functional.Operator
    public MutableMediaBuffer run(final MediaBuffer mediaBuffer, final MutableMediaBuffer mutableMediaBuffer) {
        String str = TAG;
        Log.d(str, "run: pre=" + this.preImgpDescriptor + ", post=" + this.postImgpDescriptor);
        SplitType splitType = (SplitType) Optional.ofNullable(this.preImgpDescriptor).map(new Function() { // from class: com.samsung.android.sume.core.filter.ImgpDecorateFilter$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((ImgpDescriptor) obj).getSplitType();
            }
        }).orElse(SplitType.NONE);
        MediaFormat format = mediaBuffer.getFormat();
        MediaBuffer mediaBuffer2 = (MediaBuffer) Optional.ofNullable(this.preFilter).map(new Function() { // from class: com.samsung.android.sume.core.filter.ImgpDecorateFilter$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ((MediaFilter) obj).run(mediaBuffer);
            }
        }).orElse(MediaBuffer.mutableOf(mediaBuffer));
        mediaBuffer2.addExtra(mediaBuffer.getExtra());
        super.run(mediaBuffer2, mutableMediaBuffer);
        if (splitType == SplitType.TILE) {
            Objects.requireNonNull(this.preImgpDescriptor);
            Objects.requireNonNull(this.postImgpDescriptor);
            this.postImgpDescriptor.getFormat().set("merge-type", SplitType.TILE);
            Shape shape = (Shape) Optional.ofNullable(this.preImgpDescriptor.getFormat()).map(new Function() { // from class: com.samsung.android.sume.core.filter.ImgpDecorateFilter$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((MutableMediaFormat) obj).getShape();
                }
            }).orElse(mediaBuffer2.getFormat().getShape());
            Shape shape2 = (Shape) Optional.ofNullable(this.postImgpDescriptor.getFormat()).map(new Function() { // from class: com.samsung.android.sume.core.filter.ImgpDecorateFilter$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ((MutableMediaFormat) obj).getShape();
                }
            }).orElse(mutableMediaBuffer.getFormat().getShape());
            final float rows = shape2.getRows() / shape.getRows();
            final float cols = shape2.getCols() / shape.getCols();
            MutableShape mutableShape = format.getShape().toMutableShape();
            mutableShape.setRows((int) (mutableShape.getRows() * rows));
            mutableShape.setCols((int) (mutableShape.getCols() * cols));
            if (mutableMediaBuffer.containsExtra("force-rotate") && Arrays.stream(new int[]{90, 270}).anyMatch(new IntPredicate() { // from class: com.samsung.android.sume.core.filter.ImgpDecorateFilter$$ExternalSyntheticLambda3
                @Override // java.util.function.IntPredicate
                public final boolean test(int i) {
                    return ImgpDecorateFilter.lambda$run$1(mutableMediaBuffer, i);
                }
            })) {
                int cols2 = mutableShape.getCols();
                mutableShape.setCols(mutableShape.getRows());
                mutableShape.setRows(cols2);
            }
            MutableMediaBuffer mutableMediaBufferMutableOf = MediaBuffer.mutableOf(MediaFormat.mutableImageOf(Optional.ofNullable(this.postImgpDescriptor.getFormat()).map(new Function() { // from class: com.samsung.android.sume.core.filter.ImgpDecorateFilter$$ExternalSyntheticLambda4
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ImgpDecorateFilter.lambda$run$2(mutableMediaBuffer, (MutableMediaFormat) obj);
                }
            }).orElse(mutableMediaBuffer.getFormat().getDataType()), mutableShape.toShape(), Optional.ofNullable(this.postImgpDescriptor.getFormat()).map(new Function() { // from class: com.samsung.android.sume.core.filter.ImgpDecorateFilter$$ExternalSyntheticLambda5
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return ImgpDecorateFilter.lambda$run$3(mutableMediaBuffer, (MutableMediaFormat) obj);
                }
            }).orElse(mutableMediaBuffer.getFormat().getColorFormat())));
            if (cols * rows != 1.0f) {
                mutableMediaBuffer.stream().forEach(new Consumer() { // from class: com.samsung.android.sume.core.filter.ImgpDecorateFilter$$ExternalSyntheticLambda6
                    @Override // java.util.function.Consumer
                    public final void accept(Object obj) {
                        ImgpDecorateFilter.lambda$run$4(rows, cols, (MediaBuffer) obj);
                    }
                });
            }
            MediaBuffer mediaBufferGroupOf = MediaBuffer.groupOf(mutableMediaBufferMutableOf, mutableMediaBuffer.asList());
            mediaBufferGroupOf.addExtra(mutableMediaBuffer.getExtra());
            if (this.postFilter != null) {
                mutableMediaBuffer.reset();
                this.postFilter.run(mediaBufferGroupOf, mutableMediaBuffer);
            }
            mediaBufferGroupOf.release();
        } else if (getDescriptor().getOption().isInputWithEvaluationValue()) {
            MediaBuffer mediaBufferGroupOf2 = MediaBuffer.groupOf(mediaBuffer2.asRef().copy(), mutableMediaBuffer.asList());
            mediaBufferGroupOf2.setFlags(2);
            mutableMediaBuffer.put(mediaBufferGroupOf2);
        } else {
            MediaFilter mediaFilter = this.postFilter;
            if (mediaFilter != null) {
                mediaFilter.run(mutableMediaBuffer.reset(), mutableMediaBuffer);
            }
        }
        if (!Stream.of((Object[]) new MFDescriptor[]{getDescriptor(), this.postImgpDescriptor}).anyMatch(new Predicate() { // from class: com.samsung.android.sume.core.filter.ImgpDecorateFilter$$ExternalSyntheticLambda7
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return ((MFDescriptor) obj).getOption().isKeepFilterDatatype();
            }
        })) {
            Log.d(str, "convert output data-type to one of input");
            mutableMediaBuffer.put((MediaBuffer) UniImgp.ofCvtData().run(mutableMediaBuffer.get(), MediaBuffer.mutableOf(MediaFormat.imageOf(format.getDataType()))));
        }
        mutableMediaBuffer.addExtra(mutableMediaBuffer.getExtra());
        mutableMediaBuffer.addExtra(mediaBuffer2.getExtra());
        if (mediaBuffer != mediaBuffer2) {
            mediaBuffer2.release();
        }
        Log.d(str, "ret: obuf=" + mutableMediaBuffer);
        return mutableMediaBuffer;
    }

    static /* synthetic */ boolean lambda$run$1(MutableMediaBuffer mutableMediaBuffer, int i) {
        return i == ((Integer) mutableMediaBuffer.getExtra("force-rotate")).intValue();
    }

    static /* synthetic */ DataType lambda$run$2(MutableMediaBuffer mutableMediaBuffer, MutableMediaFormat mutableMediaFormat) {
        DataType dataType = mutableMediaFormat.getDataType();
        return dataType == DataType.NONE ? mutableMediaBuffer.getFormat().getDataType() : dataType;
    }

    static /* synthetic */ ColorFormat lambda$run$3(MutableMediaBuffer mutableMediaBuffer, MutableMediaFormat mutableMediaFormat) {
        ColorFormat colorFormat = mutableMediaFormat.getColorFormat();
        return colorFormat == ColorFormat.NONE ? mutableMediaBuffer.getFormat().getColorFormat() : colorFormat;
    }

    static /* synthetic */ void lambda$run$4(float f, float f2, MediaBuffer mediaBuffer) {
        if (mediaBuffer.containsAllExtra("roi-on-block", "roi-on-image")) {
            Rect rect = (Rect) mediaBuffer.getExtra("roi-on-block");
            rect.top = (int) (rect.top * f);
            rect.bottom = (int) (rect.bottom * f);
            rect.right = (int) (rect.right * f2);
            rect.left = (int) (rect.left * f2);
            Rect rect2 = (Rect) mediaBuffer.getExtra("roi-on-image");
            rect2.top = (int) (rect2.top * f);
            rect2.bottom = (int) (rect2.bottom * f);
            rect2.right = (int) (rect2.right * f2);
            rect2.left = (int) (rect2.left * f2);
        }
    }
}
