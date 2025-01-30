package com.samsung.android.sume.core.functional;

import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer;
import com.samsung.android.sume.core.format.MediaFormat;
import com.samsung.android.sume.core.format.MutableMediaFormat;
import com.samsung.android.sume.core.format.Shape;
import com.samsung.android.sume.core.format.UpdatableMediaFormat;
import com.samsung.android.sume.core.functional.OperatorWrapper;
import com.samsung.android.sume.core.types.ColorFormat;
import com.samsung.android.sume.core.types.DataType;
import com.samsung.android.sume.core.types.ImgpType;
import com.samsung.android.sume.core.types.SplitType;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/* loaded from: classes4.dex */
public class OperatorWrapper extends OpPriorityComputable implements Operator {
    private static final String TAG = Def.tagOf((Class<?>) OperatorWrapper.class);
    private static final Map<ImgpType, MediaFormatUpdater> formatUpdaterMap = new C52861();
    protected MediaFormatUpdater formatUpdater;
    protected Operator processor;

    /* renamed from: com.samsung.android.sume.core.functional.OperatorWrapper$1 */
    class C52861 extends HashMap<ImgpType, MediaFormatUpdater> {
        C52861() {
            put(ImgpType.RESIZE, new MediaFormatUpdater() { // from class: com.samsung.android.sume.core.functional.OperatorWrapper$1$$ExternalSyntheticLambda0
                @Override // com.samsung.android.sume.core.functional.MediaFormatUpdater
                public final void update(MediaFormat mediaFormat, MutableMediaFormat mutableMediaFormat) {
                    mutableMediaFormat.setRows(mediaFormat.getRows()).setCols(mediaFormat.getCols());
                }
            });
            put(ImgpType.CROP, new MediaFormatUpdater() { // from class: com.samsung.android.sume.core.functional.OperatorWrapper$1$$ExternalSyntheticLambda1
                @Override // com.samsung.android.sume.core.functional.MediaFormatUpdater
                public final void update(MediaFormat mediaFormat, MutableMediaFormat mutableMediaFormat) {
                    mutableMediaFormat.setShape(((UpdatableMediaFormat) mediaFormat).getCroppedShape());
                }
            });
            put(ImgpType.ROTATE, new MediaFormatUpdater() { // from class: com.samsung.android.sume.core.functional.OperatorWrapper$1$$ExternalSyntheticLambda2
                @Override // com.samsung.android.sume.core.functional.MediaFormatUpdater
                public final void update(MediaFormat mediaFormat, MutableMediaFormat mutableMediaFormat) {
                    OperatorWrapper.C52861.lambda$new$2(mediaFormat, mutableMediaFormat);
                }
            });
            put(ImgpType.CVT_COLOR, new MediaFormatUpdater() { // from class: com.samsung.android.sume.core.functional.OperatorWrapper$1$$ExternalSyntheticLambda3
                @Override // com.samsung.android.sume.core.functional.MediaFormatUpdater
                public final void update(MediaFormat mediaFormat, MutableMediaFormat mutableMediaFormat) {
                    OperatorWrapper.C52861.lambda$new$3(mediaFormat, mutableMediaFormat);
                }
            });
            put(ImgpType.CVT_DATA, new MediaFormatUpdater() { // from class: com.samsung.android.sume.core.functional.OperatorWrapper$1$$ExternalSyntheticLambda4
                @Override // com.samsung.android.sume.core.functional.MediaFormatUpdater
                public final void update(MediaFormat mediaFormat, MutableMediaFormat mutableMediaFormat) {
                    mutableMediaFormat.setDataType(mediaFormat.getDataType());
                }
            });
            put(ImgpType.SPLIT, new MediaFormatUpdater() { // from class: com.samsung.android.sume.core.functional.OperatorWrapper$1$$ExternalSyntheticLambda5
                @Override // com.samsung.android.sume.core.functional.MediaFormatUpdater
                public final void update(MediaFormat mediaFormat, MutableMediaFormat mutableMediaFormat) {
                    OperatorWrapper.C52861.lambda$new$5(mediaFormat, mutableMediaFormat);
                }
            });
            put(ImgpType.MERGE, new MediaFormatUpdater() { // from class: com.samsung.android.sume.core.functional.OperatorWrapper$1$$ExternalSyntheticLambda6
                @Override // com.samsung.android.sume.core.functional.MediaFormatUpdater
                public final void update(MediaFormat mediaFormat, MutableMediaFormat mutableMediaFormat) {
                    mutableMediaFormat.setColorFormat(mediaFormat.getColorFormat());
                }
            });
        }

        static /* synthetic */ void lambda$new$2(MediaFormat ifmt, MutableMediaFormat ofmt) {
            Integer orientation = (Integer) ifmt.get("rotation-degrees");
            if (orientation != null) {
                if (orientation.intValue() == 90 || orientation.intValue() == 270) {
                    int tmp = ofmt.getCols();
                    ofmt.setCols(ofmt.getRows());
                    ofmt.setRows(tmp);
                }
            }
        }

        static /* synthetic */ void lambda$new$3(MediaFormat ifmt, MutableMediaFormat ofmt) {
            if (ifmt.getColorFormat().getChannels() != ofmt.getColorFormat().getChannels()) {
                ofmt.setDataType(DataType.m467of(ofmt.getDataType().depth(), ifmt.getColorFormat().getChannels()));
            }
            ofmt.setColorFormat(ifmt.getColorFormat());
        }

        static /* synthetic */ void lambda$new$5(MediaFormat ifmt, MutableMediaFormat ofmt) {
            int dim = ((Integer) Optional.ofNullable(ifmt.getShape()).map(new Function() { // from class: com.samsung.android.sume.core.functional.OperatorWrapper$1$$ExternalSyntheticLambda7
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Integer.valueOf(((Shape) obj).getDimension());
                }
            }).orElse(0)).intValue();
            if (dim != 0) {
                ofmt.setRows(ifmt.getRows()).setCols(ifmt.getCols());
            }
            SplitType splitType = (SplitType) ifmt.get("split-type");
            if (splitType == SplitType.ALPHA) {
                ofmt.setColorFormat(ColorFormat.GRAY);
            }
        }
    }

    OperatorWrapper(Enum<?> type, Operator processor) {
        super(type);
        this.processor = processor;
        this.formatUpdater = (MediaFormatUpdater) Optional.ofNullable(formatUpdaterMap.get(type)).orElseGet(new Supplier() { // from class: com.samsung.android.sume.core.functional.OperatorWrapper$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return OperatorWrapper.lambda$new$1();
            }
        });
    }

    static /* synthetic */ void lambda$new$0(MediaFormat ifmt, MutableMediaFormat ofmt) {
    }

    static /* synthetic */ MediaFormatUpdater lambda$new$1() {
        return new MediaFormatUpdater() { // from class: com.samsung.android.sume.core.functional.OperatorWrapper$$ExternalSyntheticLambda4
            @Override // com.samsung.android.sume.core.functional.MediaFormatUpdater
            public final void update(MediaFormat mediaFormat, MutableMediaFormat mutableMediaFormat) {
                OperatorWrapper.lambda$new$0(mediaFormat, mutableMediaFormat);
            }
        };
    }

    @Override // com.samsung.android.sume.core.functional.Operator
    public MutableMediaBuffer run(MediaBuffer ibuf, MutableMediaBuffer obuf) throws UnsupportedOperationException {
        return this.processor.run(ibuf, obuf);
    }

    /* renamed from: of */
    public static Operator m410of(final Enum<?> type, Operator processor) {
        if (processor instanceof OperatorChain) {
            return new OperatorChain(type, (List) ((OperatorChain) processor).stream().map(new Function() { // from class: com.samsung.android.sume.core.functional.OperatorWrapper$$ExternalSyntheticLambda3
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return OperatorWrapper.lambda$of$2(type, (Operator) obj);
                }
            }).collect(Collectors.toList()));
        }
        return new OperatorWrapper(type, processor);
    }

    static /* synthetic */ OperatorWrapper lambda$of$2(Enum type, Operator e) {
        return new OperatorWrapper(type, e);
    }

    /* renamed from: of */
    public static Operator m411of(Map<Enum<?>, Operator> map) {
        return new OperatorMap((Map) map.entrySet().stream().collect(Collectors.toMap(new OperatorWrapper$$ExternalSyntheticLambda1(), new Function() { // from class: com.samsung.android.sume.core.functional.OperatorWrapper$$ExternalSyntheticLambda2
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Operator m410of;
                m410of = OperatorWrapper.m410of((Enum) r1.getKey(), (Operator) ((Map.Entry) obj).getValue());
                return m410of;
            }
        })));
    }
}
