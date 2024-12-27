package com.samsung.android.sume.core.functional;

import android.util.Log;
import com.samsung.android.sume.core.Def;
import com.samsung.android.sume.core.buffer.MediaBuffer;
import com.samsung.android.sume.core.buffer.MutableMediaBuffer;
import com.samsung.android.sume.core.descriptor.ImgpDescriptor;
import com.samsung.android.sume.core.format.MediaFormat;
import com.samsung.android.sume.core.format.MutableMediaFormat;
import com.samsung.android.sume.core.functional.OpPriorityComputable;
import com.samsung.android.sume.core.functional.OperatorMap;
import com.samsung.android.sume.core.message.Message;
import com.samsung.android.sume.core.types.ColorFormat;
import com.samsung.android.sume.core.types.DataType;
import com.samsung.android.sume.core.types.ImgpType;
import com.samsung.android.sume.core.types.SplitType;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/* loaded from: classes6.dex */
public class OperatorMap implements Operator {
    private static final String TAG = Def.tagOf((Class<?>) OperatorMap.class);
    private static final Map<ImgpType, OpPriorityComputable.ComputeBridge> priorityCheckMap = new AnonymousClass1();
    private OpPriorityCompute priorityCompute;
    private List<Operator> processorList;
    private final Map<Enum<?>, Operator> processorMap;
    private boolean usePersistentFormat = false;

    /* renamed from: com.samsung.android.sume.core.functional.OperatorMap$1, reason: invalid class name */
    class AnonymousClass1 extends HashMap<ImgpType, OpPriorityComputable.ComputeBridge> {
        AnonymousClass1() {
            put(ImgpType.RESIZE, new OpPriorityComputable.ComputeBridge() { // from class: com.samsung.android.sume.core.functional.OperatorMap$1$$ExternalSyntheticLambda0
                @Override // com.samsung.android.sume.core.functional.OpPriorityComputable.ComputeBridge
                public final float compute(MutableMediaFormat mutableMediaFormat, MediaFormat mediaFormat, OpPriorityCompute opPriorityCompute) {
                    return OperatorMap.AnonymousClass1.lambda$new$2(mutableMediaFormat, mediaFormat, opPriorityCompute);
                }
            });
            put(ImgpType.DECODE, new OpPriorityComputable.ComputeBridge() { // from class: com.samsung.android.sume.core.functional.OperatorMap$1$$ExternalSyntheticLambda1
                @Override // com.samsung.android.sume.core.functional.OpPriorityComputable.ComputeBridge
                public final float compute(MutableMediaFormat mutableMediaFormat, MediaFormat mediaFormat, OpPriorityCompute opPriorityCompute) {
                    return OperatorMap.AnonymousClass1.lambda$new$3(mutableMediaFormat, mediaFormat, opPriorityCompute);
                }
            });
            put(ImgpType.ENCODE, new OpPriorityComputable.ComputeBridge() { // from class: com.samsung.android.sume.core.functional.OperatorMap$1$$ExternalSyntheticLambda2
                @Override // com.samsung.android.sume.core.functional.OpPriorityComputable.ComputeBridge
                public final float compute(MutableMediaFormat mutableMediaFormat, MediaFormat mediaFormat, OpPriorityCompute opPriorityCompute) {
                    return OperatorMap.AnonymousClass1.lambda$new$4(mutableMediaFormat, mediaFormat, opPriorityCompute);
                }
            });
            put(ImgpType.CVT_COLOR, new OpPriorityComputable.ComputeBridge() { // from class: com.samsung.android.sume.core.functional.OperatorMap$1$$ExternalSyntheticLambda3
                @Override // com.samsung.android.sume.core.functional.OpPriorityComputable.ComputeBridge
                public final float compute(MutableMediaFormat mutableMediaFormat, MediaFormat mediaFormat, OpPriorityCompute opPriorityCompute) {
                    return OperatorMap.AnonymousClass1.lambda$new$5(mutableMediaFormat, mediaFormat, opPriorityCompute);
                }
            });
            put(ImgpType.CVT_DATA, new OpPriorityComputable.ComputeBridge() { // from class: com.samsung.android.sume.core.functional.OperatorMap$1$$ExternalSyntheticLambda4
                @Override // com.samsung.android.sume.core.functional.OpPriorityComputable.ComputeBridge
                public final float compute(MutableMediaFormat mutableMediaFormat, MediaFormat mediaFormat, OpPriorityCompute opPriorityCompute) {
                    return OperatorMap.AnonymousClass1.lambda$new$6(mutableMediaFormat, mediaFormat, opPriorityCompute);
                }
            });
            put(ImgpType.ROTATE, new OpPriorityComputable.ComputeBridge() { // from class: com.samsung.android.sume.core.functional.OperatorMap$1$$ExternalSyntheticLambda5
                @Override // com.samsung.android.sume.core.functional.OpPriorityComputable.ComputeBridge
                public final float compute(MutableMediaFormat mutableMediaFormat, MediaFormat mediaFormat, OpPriorityCompute opPriorityCompute) {
                    return OperatorMap.AnonymousClass1.lambda$new$7(mutableMediaFormat, mediaFormat, opPriorityCompute);
                }
            });
            put(ImgpType.CROP, new OpPriorityComputable.ComputeBridge() { // from class: com.samsung.android.sume.core.functional.OperatorMap$1$$ExternalSyntheticLambda6
                @Override // com.samsung.android.sume.core.functional.OpPriorityComputable.ComputeBridge
                public final float compute(MutableMediaFormat mutableMediaFormat, MediaFormat mediaFormat, OpPriorityCompute opPriorityCompute) {
                    return OperatorMap.AnonymousClass1.lambda$new$8(mutableMediaFormat, mediaFormat, opPriorityCompute);
                }
            });
            put(ImgpType.SPLIT, new OpPriorityComputable.ComputeBridge() { // from class: com.samsung.android.sume.core.functional.OperatorMap$1$$ExternalSyntheticLambda7
                @Override // com.samsung.android.sume.core.functional.OpPriorityComputable.ComputeBridge
                public final float compute(MutableMediaFormat mutableMediaFormat, MediaFormat mediaFormat, OpPriorityCompute opPriorityCompute) {
                    return OperatorMap.AnonymousClass1.lambda$new$9(mutableMediaFormat, mediaFormat, opPriorityCompute);
                }
            });
            put(ImgpType.MERGE, new OpPriorityComputable.ComputeBridge() { // from class: com.samsung.android.sume.core.functional.OperatorMap$1$$ExternalSyntheticLambda8
                @Override // com.samsung.android.sume.core.functional.OpPriorityComputable.ComputeBridge
                public final float compute(MutableMediaFormat mutableMediaFormat, MediaFormat mediaFormat, OpPriorityCompute opPriorityCompute) {
                    return OperatorMap.AnonymousClass1.lambda$new$10(mutableMediaFormat, mediaFormat, opPriorityCompute);
                }
            });
        }

        static /* synthetic */ float lambda$new$2(MutableMediaFormat input, final MediaFormat output, OpPriorityCompute priorityCompute) {
            boolean isNotTiled = Arrays.stream(new String[]{"split-type", "merge-type"}).map(new Function() { // from class: com.samsung.android.sume.core.functional.OperatorMap$1$$ExternalSyntheticLambda9
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return OperatorMap.AnonymousClass1.lambda$new$0(MediaFormat.this, (String) obj);
                }
            }).allMatch(new Predicate() { // from class: com.samsung.android.sume.core.functional.OperatorMap$1$$ExternalSyntheticLambda10
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return OperatorMap.AnonymousClass1.lambda$new$1((SplitType) obj);
                }
            });
            boolean inShapeIsGiven = input.size() != 0;
            boolean outShapeIsGiven = output.size() != 0;
            if (!isNotTiled || !inShapeIsGiven || !outShapeIsGiven) {
                return -1.0f;
            }
            if (input.getCols() != output.getCols() || input.getRows() != output.getRows()) {
                return priorityCompute.compute(input.getShape(), output.getShape());
            }
            return -1.0f;
        }

        static /* synthetic */ SplitType lambda$new$0(MediaFormat output, String e) {
            return (SplitType) output.get(e, SplitType.NONE);
        }

        static /* synthetic */ boolean lambda$new$1(SplitType e) {
            return e != SplitType.TILE;
        }

        static /* synthetic */ float lambda$new$3(MutableMediaFormat input, MediaFormat output, OpPriorityCompute priorityCompute) {
            if ((input.contains(Message.KEY_FILE_DESCRIPTOR) || input.contains(Message.KEY_IN_FILE)) && output.getColorFormat() != ColorFormat.NONE) {
                return 0.0f;
            }
            return -1.0f;
        }

        static /* synthetic */ float lambda$new$4(MutableMediaFormat input, MediaFormat output, OpPriorityCompute priorityCompute) {
            if ((output.contains(Message.KEY_FILE_DESCRIPTOR) || output.contains(Message.KEY_OUT_FILE)) && input.getColorFormat() != ColorFormat.NONE) {
                return Float.MAX_VALUE;
            }
            return -1.0f;
        }

        static /* synthetic */ float lambda$new$5(MutableMediaFormat input, MediaFormat output, OpPriorityCompute priorityCompute) {
            if (output.getColorFormat() != ColorFormat.NONE && input.getColorFormat() != output.getColorFormat()) {
                return priorityCompute.compute(input.getColorFormat(), output.getColorFormat());
            }
            return -1.0f;
        }

        static /* synthetic */ float lambda$new$6(MutableMediaFormat input, MediaFormat output, OpPriorityCompute priorityCompute) {
            if (output.getDataType() != DataType.NONE && input.getDataType() != output.getDataType()) {
                return priorityCompute.compute(input.getDataType(), output.getDataType());
            }
            return -1.0f;
        }

        static /* synthetic */ float lambda$new$7(MutableMediaFormat input, MediaFormat output, OpPriorityCompute priorityCompute) {
            boolean inShapeIsGiven = input.size() != 0;
            if (inShapeIsGiven && output.contains("rotation-degrees")) {
                return priorityCompute.compute(input.getShape(), output.getShape());
            }
            return -1.0f;
        }

        static /* synthetic */ float lambda$new$8(MutableMediaFormat input, MediaFormat output, OpPriorityCompute priorityCompute) {
            if (output.containsAnyOf("crop", "center-crop")) {
                return Float.MIN_VALUE;
            }
            return -1.0f;
        }

        static /* synthetic */ float lambda$new$9(MutableMediaFormat input, MediaFormat output, OpPriorityCompute priorityCompute) {
            if (output.contains("split-type")) {
                return Float.MAX_VALUE;
            }
            return -1.0f;
        }

        static /* synthetic */ float lambda$new$10(MutableMediaFormat input, MediaFormat output, OpPriorityCompute priorityCompute) {
            if (output.contains("merge-type")) {
                return 0.0f;
            }
            return -1.0f;
        }
    }

    public OperatorMap(Map<Enum<?>, Operator> processorMap) {
        this.processorMap = processorMap;
    }

    @Override // com.samsung.android.sume.core.functional.Operator
    public MutableMediaBuffer run(MediaBuffer ibuf, final MutableMediaBuffer obuf) throws UnsupportedOperationException {
        final MutableMediaFormat ifmt = ibuf.getFormat().toMutableFormat();
        if (!this.usePersistentFormat || this.processorList == null) {
            final Map<Float, Operator> processorPriorityMap = new HashMap<>();
            this.processorMap.values().forEach(new Consumer() { // from class: com.samsung.android.sume.core.functional.OperatorMap$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    OperatorMap.lambda$run$0(MutableMediaFormat.this, obuf, processorPriorityMap, (Operator) obj);
                }
            });
            this.processorList = (List) processorPriorityMap.entrySet().stream().sorted(Map.Entry.comparingByKey()).map(new OperatorMap$$ExternalSyntheticLambda4()).collect(Collectors.toList());
        }
        MutableMediaBuffer input = MediaBuffer.mutableOf(ibuf);
        for (Operator proc : this.processorList) {
            try {
                proc.run((MediaBuffer) input, obuf);
                MediaBuffer used = obuf.moveTo(input);
                if (used != ibuf && used != obuf.get()) {
                    used.release();
                }
            } catch (UnsupportedOperationException e) {
                Log.d(TAG, "restore format:\nformat=" + obuf + "\nibuf=" + ibuf);
            }
        }
        if (obuf.isEmpty()) {
            obuf.put((MediaBuffer) input);
        }
        return obuf;
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ void lambda$run$0(MutableMediaFormat ifmt, MutableMediaBuffer obuf, Map processorPriorityMap, Operator operator) {
        float priority = ((OpPriorityComputable) operator).compute(ifmt, obuf.getFormat());
        if (priority != -1.0f) {
            processorPriorityMap.put(Float.valueOf(priority), operator);
        }
    }

    public void config(ImgpDescriptor descriptor) {
        this.usePersistentFormat = descriptor.isUsePersistentFormat();
        this.priorityCompute = new OpPriorityByDataSize();
        this.processorMap.values().stream().map(new Function() { // from class: com.samsung.android.sume.core.functional.OperatorMap$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return OperatorMap.lambda$config$1((Operator) obj);
            }
        }).forEach(new Consumer() { // from class: com.samsung.android.sume.core.functional.OperatorMap$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                OperatorMap.this.m9174x20aa76dd((OpPriorityComputable) obj);
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ OpPriorityComputable lambda$config$1(Operator operator) {
        return (OpPriorityComputable) operator;
    }

    /* renamed from: lambda$config$2$com-samsung-android-sume-core-functional-OperatorMap, reason: not valid java name */
    /* synthetic */ void m9174x20aa76dd(OpPriorityComputable it) {
        it.setComputeBridge(priorityCheckMap.get(it.getType()), this.priorityCompute);
    }

    public static List<ImgpType> inferOperations(final MutableMediaFormat inputFormat, final MediaFormat outputFormat) {
        final OpPriorityByDataSize priorityCompute = new OpPriorityByDataSize();
        Map<ImgpType, Float> priorityMap = (Map) priorityCheckMap.entrySet().stream().collect(Collectors.toMap(new Function() { // from class: com.samsung.android.sume.core.functional.OperatorMap$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return (ImgpType) ((Map.Entry) obj).getKey();
            }
        }, new Function() { // from class: com.samsung.android.sume.core.functional.OperatorMap$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                Float valueOf;
                valueOf = Float.valueOf(((OpPriorityComputable.ComputeBridge) ((Map.Entry) obj).getValue()).compute(MutableMediaFormat.this, outputFormat, priorityCompute));
                return valueOf;
            }
        }));
        return (List) priorityMap.entrySet().stream().filter(new Predicate() { // from class: com.samsung.android.sume.core.functional.OperatorMap$$ExternalSyntheticLambda2
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                return OperatorMap.lambda$inferOperations$4((Map.Entry) obj);
            }
        }).sorted(Map.Entry.comparingByValue()).map(new Function() { // from class: com.samsung.android.sume.core.functional.OperatorMap$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return (ImgpType) ((Map.Entry) obj).getKey();
            }
        }).collect(Collectors.toList());
    }

    static /* synthetic */ boolean lambda$inferOperations$4(Map.Entry e) {
        return ((Float) e.getValue()).floatValue() >= 0.0f;
    }
}
