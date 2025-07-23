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

/* loaded from: classes6.dex */
public class OperatorWrapper extends OpPriorityComputable implements Operator {
    private static final String TAG = Def.tagOf((Class<?>) OperatorWrapper.class);
    private static final Map<ImgpType, MediaFormatUpdater> formatUpdaterMap = new AnonymousClass1();
    protected MediaFormatUpdater formatUpdater;
    protected Operator processor;

    static /* synthetic */ void lambda$new$0(MediaFormat mediaFormat, MutableMediaFormat mutableMediaFormat) {
    }

    /* renamed from: com.samsung.android.sume.core.functional.OperatorWrapper$1, reason: invalid class name */
    class AnonymousClass1 extends HashMap<ImgpType, MediaFormatUpdater> {
        AnonymousClass1() {
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
                    OperatorWrapper.AnonymousClass1.lambda$new$2(mediaFormat, mutableMediaFormat);
                }
            });
            put(ImgpType.CVT_COLOR, new MediaFormatUpdater() { // from class: com.samsung.android.sume.core.functional.OperatorWrapper$1$$ExternalSyntheticLambda3
                @Override // com.samsung.android.sume.core.functional.MediaFormatUpdater
                public final void update(MediaFormat mediaFormat, MutableMediaFormat mutableMediaFormat) {
                    OperatorWrapper.AnonymousClass1.lambda$new$3(mediaFormat, mutableMediaFormat);
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
                    OperatorWrapper.AnonymousClass1.lambda$new$5(mediaFormat, mutableMediaFormat);
                }
            });
            put(ImgpType.MERGE, new MediaFormatUpdater() { // from class: com.samsung.android.sume.core.functional.OperatorWrapper$1$$ExternalSyntheticLambda6
                @Override // com.samsung.android.sume.core.functional.MediaFormatUpdater
                public final void update(MediaFormat mediaFormat, MutableMediaFormat mutableMediaFormat) {
                    mutableMediaFormat.setColorFormat(mediaFormat.getColorFormat());
                }
            });
        }

        static /* synthetic */ void lambda$new$2(MediaFormat mediaFormat, MutableMediaFormat mutableMediaFormat) {
            Integer num = (Integer) mediaFormat.get("rotation-degrees");
            if (num != null) {
                if (num.intValue() == 90 || num.intValue() == 270) {
                    int cols = mutableMediaFormat.getCols();
                    mutableMediaFormat.setCols(mutableMediaFormat.getRows());
                    mutableMediaFormat.setRows(cols);
                }
            }
        }

        static /* synthetic */ void lambda$new$3(MediaFormat mediaFormat, MutableMediaFormat mutableMediaFormat) {
            if (mediaFormat.getColorFormat().getChannels() != mutableMediaFormat.getColorFormat().getChannels()) {
                mutableMediaFormat.setDataType(DataType.of(mutableMediaFormat.getDataType().depth(), mediaFormat.getColorFormat().getChannels()));
            }
            mutableMediaFormat.setColorFormat(mediaFormat.getColorFormat());
        }

        static /* synthetic */ void lambda$new$5(MediaFormat mediaFormat, MutableMediaFormat mutableMediaFormat) {
            if (((Integer) Optional.ofNullable(mediaFormat.getShape()).map(new Function() { // from class: com.samsung.android.sume.core.functional.OperatorWrapper$1$$ExternalSyntheticLambda7
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return Integer.valueOf(((Shape) obj).getDimension());
                }
            }).orElse(0)).intValue() != 0) {
                mutableMediaFormat.setRows(mediaFormat.getRows()).setCols(mediaFormat.getCols());
            }
            if (((SplitType) mediaFormat.get("split-type")) == SplitType.ALPHA) {
                mutableMediaFormat.setColorFormat(ColorFormat.GRAY);
            }
        }
    }

    OperatorWrapper(Enum<?> r1, Operator operator) {
        super(r1);
        this.processor = operator;
        this.formatUpdater = (MediaFormatUpdater) Optional.ofNullable(formatUpdaterMap.get(r1)).orElseGet(new Supplier() { // from class: com.samsung.android.sume.core.functional.OperatorWrapper$$ExternalSyntheticLambda0
            @Override // java.util.function.Supplier
            public final Object get() {
                return OperatorWrapper.lambda$new$1();
            }
        });
    }

    static /* synthetic */ MediaFormatUpdater lambda$new$1() {
        return new MediaFormatUpdater() { // from class: com.samsung.android.sume.core.functional.OperatorWrapper$$ExternalSyntheticLambda1
            @Override // com.samsung.android.sume.core.functional.MediaFormatUpdater
            public final void update(MediaFormat mediaFormat, MutableMediaFormat mutableMediaFormat) {
                OperatorWrapper.lambda$new$0(mediaFormat, mutableMediaFormat);
            }
        };
    }

    @Override // com.samsung.android.sume.core.functional.Operator
    public MutableMediaBuffer run(MediaBuffer mediaBuffer, MutableMediaBuffer mutableMediaBuffer) throws UnsupportedOperationException {
        return this.processor.run(mediaBuffer, mutableMediaBuffer);
    }

    public static Operator of(final Enum<?> r2, Operator operator) {
        if (operator instanceof OperatorChain) {
            return new OperatorChain(r2, (List) ((OperatorChain) operator).stream().map(new Function() { // from class: com.samsung.android.sume.core.functional.OperatorWrapper$$ExternalSyntheticLambda2
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return OperatorWrapper.lambda$of$2(r2, (Operator) obj);
                }
            }).collect(Collectors.toList()));
        }
        return new OperatorWrapper(r2, operator);
    }

    static /* synthetic */ OperatorWrapper lambda$of$2(Enum r1, Operator operator) {
        return new OperatorWrapper(r1, operator);
    }

    public static Operator of(Map<Enum<?>, Operator> map) {
        return new OperatorMap((Map) map.entrySet().stream().collect(Collectors.toMap(new OperatorWrapper$$ExternalSyntheticLambda3(), new Function() { // from class: com.samsung.android.sume.core.functional.OperatorWrapper$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Operator of;
                of = OperatorWrapper.of((Enum) r1.getKey(), (Operator) ((Map.Entry) obj).getValue());
                return of;
            }
        })));
    }
}
