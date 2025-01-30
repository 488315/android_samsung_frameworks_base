package com.samsung.android.sume.core.filter;

import android.util.Log;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.DeriveBufferGroup;
import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer$$ExternalSyntheticLambda12;
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

/* loaded from: classes4.dex */
public class ImgpFilter extends PluginFilter<ImgpPlugin> {
    private static final String TAG = Def.tagOf((Class<?>) ImgpFilter.class);
    private final ImgpDescriptor descriptor;
    private Operator imgp;

    /* renamed from: of */
    public static MediaFilter m369of(MediaFilter successor, MediaFilter preFilter, MediaFilter postFilter) {
        ImgpDecorateFilter filter = new ImgpDecorateFilter(successor);
        filter.setPreFilter(preFilter);
        filter.setPostFilter(postFilter);
        return filter;
    }

    public ImgpFilter(ImgpDescriptor descriptor, ImgpPlugin plugin) {
        super(plugin);
        this.descriptor = descriptor;
        init();
    }

    private void init() {
        if (this.descriptor.getFormat() != null) {
            this.descriptor.getFormat().set(this.descriptor.getOption().asInputOption());
        }
        Operator operator = (Operator) Stream.of(this.descriptor.getImgpTypeName(), this.descriptor.getImgpType()).filter(new Predicate() { // from class: com.samsung.android.sume.core.filter.ImgpFilter$$ExternalSyntheticLambda1
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return Objects.nonNull(obj);
            }
        }).findFirst().map(new Function() { // from class: com.samsung.android.sume.core.filter.ImgpFilter$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return ImgpFilter.this.m9208lambda$init$0$comsamsungandroidsumecorefilterImgpFilter(obj);
            }
        }).orElseThrow(new MutableMediaBuffer$$ExternalSyntheticLambda12());
        this.imgp = operator;
        if (operator instanceof OperatorMap) {
            ((OperatorMap) operator).config(this.descriptor);
        } else if (operator instanceof OperatorChain) {
            ((OperatorChain) operator).config(this.descriptor);
        }
    }

    /* renamed from: lambda$init$0$com-samsung-android-sume-core-filter-ImgpFilter, reason: not valid java name */
    /* synthetic */ Operator m9208lambda$init$0$comsamsungandroidsumecorefilterImgpFilter(Object it) {
        if (it instanceof String) {
            return ((ImgpPlugin) this.plugin).getImgProcessor((String) it);
        }
        return ((ImgpPlugin) this.plugin).getImgProcessor((Enum<?>) it);
    }

    @Override // com.samsung.android.sume.core.functional.Operator
    public MutableMediaBuffer run(MediaBuffer ibuf, MutableMediaBuffer obuf) {
        MediaFormat format;
        String str = TAG;
        Log.m94d(str, "run: ibuf=" + ibuf + " -> " + obuf);
        if (obuf.isNotEmpty() || this.descriptor.getFormat() == null) {
            format = obuf.getFormat();
        } else {
            format = this.descriptor.getFormat();
        }
        if (format instanceof MutableMediaFormat) {
            format = ((MutableMediaFormat) format).toMediaFormat();
        }
        Def.check(format != null, "designate format is not given, one of output buffer or descriptor should be given", new Object[0]);
        if (((Boolean) format.get("keep-org-ratio", false)).booleanValue()) {
            MutableMediaFormat fmt = format.toMutableFormat();
            fmt.setCols((int) (fmt.getCols() / ((Float) ibuf.getExtra("scale-cols", Float.valueOf(1.0f))).floatValue()));
            fmt.setRows((int) (fmt.getRows() / ((Float) ibuf.getExtra("scale-rows", Float.valueOf(1.0f))).floatValue()));
            format = fmt.toMediaFormat();
        }
        if (ibuf.containsExtra("force-rotate")) {
            MutableMediaFormat fmt2 = format.toMutableFormat();
            fmt2.set("rotation-degrees", ibuf.getExtra("force-rotate"));
            format = fmt2.toMediaFormat();
            ibuf.removeExtra("force-rotate");
        }
        if (((Boolean) format.get("rotate-ifnot-fit", false)).booleanValue()) {
            MediaFormat src = ibuf.getFormat();
            MediaFormat dst = format;
            boolean requestForceRotate = (src.getCols() > dst.getCols() && src.getRows() < dst.getRows()) ^ (src.getCols() < dst.getCols() && src.getRows() > dst.getRows());
            if (requestForceRotate) {
                MutableMediaFormat fmt3 = format.toMutableFormat();
                fmt3.set("rotation-degrees", 90);
                format = fmt3.toMediaFormat();
                ibuf.setExtra("force-rotate", 270);
            }
        }
        if (obuf.getData() != null && obuf.getData().getClass() == FileDescriptor.class) {
            MutableMediaFormat fmt4 = format.toMutableFormat();
            fmt4.set(Message.KEY_FILE_DESCRIPTOR, obuf.getTypedData(FileDescriptor.class));
            format = fmt4.toMediaFormat();
        }
        if (obuf.containsExtra(Message.KEY_OUT_FILE)) {
            format.toMutableFormat().set(Message.KEY_OUT_FILE, (String) obuf.getExtra(Message.KEY_OUT_FILE));
        }
        if (ibuf.getFormat() != null) {
            format = UpdatableMediaFormat.m408of(format).with(ibuf.getFormat()).set(UpdatableMediaFormat.UPDATE_AT_ALLOC);
        }
        MediaBuffer outMutableBuffer = MediaBuffer.mutableOf(format);
        outMutableBuffer.setExtra(obuf.getExtra());
        obuf.put((MediaBuffer) this.imgp.run(ibuf, outMutableBuffer));
        obuf.addExtra(ibuf.getExtra());
        if (ibuf != obuf.get() && !(ibuf instanceof DeriveBufferGroup) && (obuf.get() instanceof DeriveBufferGroup)) {
            final int contentId = ((Integer) obuf.getExtra(Message.KEY_CONTENTS_ID, -1)).intValue();
            final int numBlocks = (int) obuf.size();
            obuf.stream().forEach(new Consumer() { // from class: com.samsung.android.sume.core.filter.ImgpFilter$$ExternalSyntheticLambda0
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    ImgpFilter.lambda$run$1(contentId, numBlocks, (MediaBuffer) obj);
                }
            });
        }
        Log.m94d(str, "obuf: " + obuf);
        return obuf;
    }

    static /* synthetic */ void lambda$run$1(int contentId, int numBlocks, MediaBuffer it) {
        if (contentId != -1) {
            it.setExtra(Message.KEY_CONTENTS_ID, Integer.valueOf(contentId));
        }
        it.setExtra(Message.KEY_NUM_BLOCKS, Integer.valueOf(numBlocks));
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
