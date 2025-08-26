package com.samsung.android.sume.core.filter;

import android.util.Log;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.DeriveBufferGroup;
import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer$$ExternalSyntheticLambda3;
import com.samsung.android.sume.core.descriptor.ImgpDescriptor;
import com.samsung.android.sume.core.descriptor.MFDescriptor;
import com.samsung.android.sume.core.format.MediaFormat;
import com.samsung.android.sume.core.format.MutableMediaFormat;
import com.samsung.android.sume.core.format.UpdatableMediaFormat;
import com.samsung.android.sume.core.functional.Operator;
import com.samsung.android.sume.core.functional.OperatorChain;
import com.samsung.android.sume.core.functional.OperatorMap;
import com.samsung.android.sume.core.message.Message;
import com.samsung.android.sume.core.plugin.ImgpPlugin;
import java.io.FileDescriptor;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

/* loaded from: classes6.dex */
public class ImgpFilter extends PluginFilter<ImgpPlugin> {
    private static final String TAG = Def.tagOf((Class<?>) ImgpFilter.class);
    private final ImgpDescriptor descriptor;
    private Operator imgp;

    public static MediaFilter of(MediaFilter mediaFilter, MediaFilter mediaFilter2, MediaFilter mediaFilter3) {
        ImgpDecorateFilter imgpDecorateFilter = new ImgpDecorateFilter(mediaFilter);
        imgpDecorateFilter.setPreFilter(mediaFilter2);
        imgpDecorateFilter.setPostFilter(mediaFilter3);
        return imgpDecorateFilter;
    }

    public ImgpFilter(ImgpDescriptor imgpDescriptor, ImgpPlugin imgpPlugin) {
        super(imgpPlugin);
        this.descriptor = imgpDescriptor;
        init();
    }

    private void init() {
        if (this.descriptor.getFormat() != null) {
            this.descriptor.getFormat().set(this.descriptor.getOption().asInputOption());
        }
        Operator operator = (Operator) Stream.of(this.descriptor.getImgpTypeName(), this.descriptor.getImgpType()).filter(new Predicate() { // from class: com.samsung.android.sume.core.filter.ImgpFilter$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Objects.nonNull(obj);
            }
        }).findFirst().map(new Function() { // from class: com.samsung.android.sume.core.filter.ImgpFilter$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return this.f$0.m9551lambda$init$0$comsamsungandroidsumecorefilterImgpFilter(obj);
            }
        }).orElseThrow(new MutableMediaBuffer$$ExternalSyntheticLambda3());
        this.imgp = operator;
        if (operator instanceof OperatorMap) {
            ((OperatorMap) operator).config(this.descriptor);
        } else if (operator instanceof OperatorChain) {
            ((OperatorChain) operator).config(this.descriptor);
        }
    }

    /* renamed from: lambda$init$0$com-samsung-android-sume-core-filter-ImgpFilter, reason: not valid java name */
    /* synthetic */ Operator m9551lambda$init$0$comsamsungandroidsumecorefilterImgpFilter(Object obj) {
        if (obj instanceof String) {
            return ((ImgpPlugin) this.plugin).getImgProcessor((String) obj);
        }
        return ((ImgpPlugin) this.plugin).getImgProcessor((Enum<?>) obj);
    }

    @Override // com.samsung.android.sume.core.functional.Operator
    public MutableMediaBuffer run(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) {
        MediaFormat format;
        String str = TAG;
        Log.d(str, "run: ibuf=" + mediaBuffer + " -> " + mutableMediaBuffer);
        if (mutableMediaBuffer.isNotEmpty() || this.descriptor.getFormat() == null) {
            format = mutableMediaBuffer.getFormat();
        } else {
            format = this.descriptor.getFormat();
        }
        if (format instanceof MutableMediaFormat) {
            format = ((MutableMediaFormat) format).toMediaFormat();
        }
        Def.check(format != null, "designate format is not given, one of output buffer or descriptor should be given", new Object[0]);
        if (((Boolean) format.get("keep-org-ratio", false)).booleanValue()) {
            MutableMediaFormat mutableFormat = format.toMutableFormat();
            mutableFormat.setCols((int) (mutableFormat.getCols() / ((Float) mediaBuffer.getExtra("scale-cols", Float.valueOf(1.0f))).floatValue()));
            mutableFormat.setRows((int) (mutableFormat.getRows() / ((Float) mediaBuffer.getExtra("scale-rows", Float.valueOf(1.0f))).floatValue()));
            format = mutableFormat.toMediaFormat();
        }
        if (mediaBuffer.containsExtra("force-rotate")) {
            MutableMediaFormat mutableFormat2 = format.toMutableFormat();
            mutableFormat2.set("rotation-degrees", mediaBuffer.getExtra("force-rotate"));
            format = mutableFormat2.toMediaFormat();
            mediaBuffer.removeExtra("force-rotate");
        }
        if (((Boolean) format.get("rotate-ifnot-fit", false)).booleanValue()) {
            MediaFormat format2 = mediaBuffer.getFormat();
            if ((format2.getCols() > format.getCols() && format2.getRows() < format.getRows()) ^ (format2.getCols() < format.getCols() && format2.getRows() > format.getRows())) {
                MutableMediaFormat mutableFormat3 = format.toMutableFormat();
                mutableFormat3.set("rotation-degrees", 90);
                format = mutableFormat3.toMediaFormat();
                mediaBuffer.setExtra("force-rotate", 270);
            }
        }
        if (mutableMediaBuffer.getData() != null && mutableMediaBuffer.getData().getClass() == FileDescriptor.class) {
            MutableMediaFormat mutableFormat4 = format.toMutableFormat();
            mutableFormat4.set(Message.KEY_FILE_DESCRIPTOR, mutableMediaBuffer.getTypedData(FileDescriptor.class));
            format = mutableFormat4.toMediaFormat();
        }
        if (mutableMediaBuffer.containsExtra(Message.KEY_OUT_FILE)) {
            format.toMutableFormat().set(Message.KEY_OUT_FILE, (String) mutableMediaBuffer.getExtra(Message.KEY_OUT_FILE));
        }
        if (mediaBuffer.getFormat() != null) {
            format = UpdatableMediaFormat.of(format).with(mediaBuffer.getFormat()).set(UpdatableMediaFormat.UPDATE_AT_ALLOC);
        }
        MutableMediaBuffer mutableMediaBufferMutableOf = MediaBuffer.mutableOf(format);
        mutableMediaBufferMutableOf.setExtra(mutableMediaBuffer.getExtra());
        mutableMediaBuffer.put((MediaBuffer) this.imgp.run(mediaBuffer, (MediaBuffer) mutableMediaBufferMutableOf));
        mutableMediaBuffer.addExtra(mediaBuffer.getExtra());
        if (mediaBuffer != mutableMediaBuffer.get() && !(mediaBuffer instanceof DeriveBufferGroup) && (mutableMediaBuffer.get() instanceof DeriveBufferGroup)) {
            final int iIntValue = ((Integer) mutableMediaBuffer.getExtra(Message.KEY_CONTENTS_ID, -1)).intValue();
            final int size = (int) mutableMediaBuffer.size();
            mutableMediaBuffer.stream().forEach(new Consumer() { // from class: com.samsung.android.sume.core.filter.ImgpFilter$$ExternalSyntheticLambda2
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ImgpFilter.lambda$run$1(iIntValue, size, (MediaBuffer) obj);
                }
            });
        }
        Log.d(str, "obuf: " + mutableMediaBuffer);
        return mutableMediaBuffer;
    }

    static /* synthetic */ void lambda$run$1(int i, int i2, MediaBuffer mediaBuffer) {
        if (i != -1) {
            mediaBuffer.setExtra(Message.KEY_CONTENTS_ID, Integer.valueOf(i));
        }
        mediaBuffer.setExtra(Message.KEY_NUM_BLOCKS, Integer.valueOf(i2));
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilter
    public MFDescriptor getDescriptor() {
        return this.descriptor;
    }

    @Override // com.samsung.android.sume.core.filter.MediaFilter
    public Stream<MediaFilter> stream() {
        return Stream.of(this);
    }
}
