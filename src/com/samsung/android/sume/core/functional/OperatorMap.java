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
import java.util.Iterator;
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

        static /* synthetic */ float lambda$new$2(MutableMediaFormat mutableMediaFormat, final MediaFormat mediaFormat, OpPriorityCompute opPriorityCompute) {
            boolean zAllMatch = Arrays.stream(new String[]{"split-type", "merge-type"}).map(new Function() { // from class: com.samsung.android.sume.core.functional.OperatorMap$1$$ExternalSyntheticLambda9
                @Override // java.util.function.Function
                public final Object apply(Object obj) {
                    return OperatorMap.AnonymousClass1.lambda$new$0(mediaFormat, (String) obj);
                }
            }).allMatch(new Predicate() { // from class: com.samsung.android.sume.core.functional.OperatorMap$1$$ExternalSyntheticLambda10
                @Override // java.util.function.Predicate
                public final boolean test(Object obj) {
                    return OperatorMap.AnonymousClass1.lambda$new$1((SplitType) obj);
                }
            });
            boolean z = mutableMediaFormat.size() != 0;
            boolean z2 = mediaFormat.size() != 0;
            if (!zAllMatch || !z || !z2) {
                return -1.0f;
            }
            if (mutableMediaFormat.getCols() == mediaFormat.getCols() && mutableMediaFormat.getRows() == mediaFormat.getRows()) {
                return -1.0f;
            }
            return opPriorityCompute.compute(mutableMediaFormat.getShape(), mediaFormat.getShape());
        }

        static /* synthetic */ SplitType lambda$new$0(MediaFormat mediaFormat, String str) {
            return (SplitType) mediaFormat.get(str, SplitType.NONE);
        }

        static /* synthetic */ boolean lambda$new$1(SplitType splitType) {
            return splitType != SplitType.TILE;
        }

        static /* synthetic */ float lambda$new$3(MutableMediaFormat mutableMediaFormat, MediaFormat mediaFormat, OpPriorityCompute opPriorityCompute) {
            return ((mutableMediaFormat.contains(Message.KEY_FILE_DESCRIPTOR) || mutableMediaFormat.contains(Message.KEY_IN_FILE)) && mediaFormat.getColorFormat() != ColorFormat.NONE) ? 0.0f : -1.0f;
        }

        static /* synthetic */ float lambda$new$4(MutableMediaFormat mutableMediaFormat, MediaFormat mediaFormat, OpPriorityCompute opPriorityCompute) {
            return ((mediaFormat.contains(Message.KEY_FILE_DESCRIPTOR) || mediaFormat.contains(Message.KEY_OUT_FILE)) && mutableMediaFormat.getColorFormat() != ColorFormat.NONE) ? Float.MAX_VALUE : -1.0f;
        }

        static /* synthetic */ float lambda$new$5(MutableMediaFormat mutableMediaFormat, MediaFormat mediaFormat, OpPriorityCompute opPriorityCompute) {
            if (mediaFormat.getColorFormat() == ColorFormat.NONE || mutableMediaFormat.getColorFormat() == mediaFormat.getColorFormat()) {
                return -1.0f;
            }
            return opPriorityCompute.compute(mutableMediaFormat.getColorFormat(), mediaFormat.getColorFormat());
        }

        static /* synthetic */ float lambda$new$6(MutableMediaFormat mutableMediaFormat, MediaFormat mediaFormat, OpPriorityCompute opPriorityCompute) {
            if (mediaFormat.getDataType() == DataType.NONE || mutableMediaFormat.getDataType() == mediaFormat.getDataType()) {
                return -1.0f;
            }
            return opPriorityCompute.compute(mutableMediaFormat.getDataType(), mediaFormat.getDataType());
        }

        static /* synthetic */ float lambda$new$7(MutableMediaFormat mutableMediaFormat, MediaFormat mediaFormat, OpPriorityCompute opPriorityCompute) {
            if (mutableMediaFormat.size() == 0 || !mediaFormat.contains("rotation-degrees")) {
                return -1.0f;
            }
            return opPriorityCompute.compute(mutableMediaFormat.getShape(), mediaFormat.getShape());
        }

        static /* synthetic */ float lambda$new$8(MutableMediaFormat mutableMediaFormat, MediaFormat mediaFormat, OpPriorityCompute opPriorityCompute) {
            return mediaFormat.containsAnyOf("crop", "center-crop") ? Float.MIN_VALUE : -1.0f;
        }

        static /* synthetic */ float lambda$new$9(MutableMediaFormat mutableMediaFormat, MediaFormat mediaFormat, OpPriorityCompute opPriorityCompute) {
            return mediaFormat.contains("split-type") ? Float.MAX_VALUE : -1.0f;
        }

        static /* synthetic */ float lambda$new$10(MutableMediaFormat mutableMediaFormat, MediaFormat mediaFormat, OpPriorityCompute opPriorityCompute) {
            return mediaFormat.contains("merge-type") ? 0.0f : -1.0f;
        }
    }

    public OperatorMap(Map<Enum<?>, Operator> map) {
        this.processorMap = map;
    }

    @Override // com.samsung.android.sume.core.functional.Operator
    public MutableMediaBuffer run(MediaBuffer mediaBuffer, final MutableMediaBuffer mutableMediaBuffer) throws UnsupportedOperationException {
        final MutableMediaFormat mutableFormat = mediaBuffer.getFormat().toMutableFormat();
        if (!this.usePersistentFormat || this.processorList == null) {
            final HashMap map = new HashMap();
            this.processorMap.values().forEach(new Consumer() { // from class: com.samsung.android.sume.core.functional.OperatorMap$$ExternalSyntheticLambda3
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    OperatorMap.lambda$run$0(mutableFormat, mutableMediaBuffer, map, (Operator) obj);
                }
            });
            this.processorList = (List) map.entrySet().stream().sorted(Map.Entry.comparingByKey()).map(new OperatorMap$$ExternalSyntheticLambda4()).collect(Collectors.toList());
        }
        MutableMediaBuffer mutableMediaBufferMutableOf = MediaBuffer.mutableOf(mediaBuffer);
        Iterator<Operator> it = this.processorList.iterator();
        while (it.hasNext()) {
            try {
                it.next().run((MediaBuffer) mutableMediaBufferMutableOf, mutableMediaBuffer);
                MediaBuffer mediaBufferMoveTo = mutableMediaBuffer.moveTo(mutableMediaBufferMutableOf);
                if (mediaBufferMoveTo != mediaBuffer && mediaBufferMoveTo != mutableMediaBuffer.get()) {
                    mediaBufferMoveTo.release();
                }
            } catch (UnsupportedOperationException unused) {
                Log.d(TAG, "restore format:\nformat=" + mutableMediaBuffer + "\nibuf=" + mediaBuffer);
            }
        }
        if (mutableMediaBuffer.isEmpty()) {
            mutableMediaBuffer.put((MediaBuffer) mutableMediaBufferMutableOf);
        }
        return mutableMediaBuffer;
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ void lambda$run$0(MutableMediaFormat mutableMediaFormat, MutableMediaBuffer mutableMediaBuffer, Map map, Operator operator) {
        float fCompute = ((OpPriorityComputable) operator).compute(mutableMediaFormat, mutableMediaBuffer.getFormat());
        if (fCompute != -1.0f) {
            map.put(Float.valueOf(fCompute), operator);
        }
    }

    public void config(ImgpDescriptor imgpDescriptor) {
        this.usePersistentFormat = imgpDescriptor.isUsePersistentFormat();
        this.priorityCompute = new OpPriorityByDataSize();
        this.processorMap.values().stream().map(new Function() { // from class: com.samsung.android.sume.core.functional.OperatorMap$$ExternalSyntheticLambda5
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return OperatorMap.lambda$config$1((Operator) obj);
            }
        }).forEach(new Consumer() { // from class: com.samsung.android.sume.core.functional.OperatorMap$$ExternalSyntheticLambda6
            @Override // java.util.function.Consumer
            public final void accept(Object obj) {
                this.f$0.m9588x20aa76dd((OpPriorityComputable) obj);
            }
        });
    }

    /* JADX WARN: Multi-variable type inference failed */
    static /* synthetic */ OpPriorityComputable lambda$config$1(Operator operator) {
        return (OpPriorityComputable) operator;
    }

    /* renamed from: lambda$config$2$com-samsung-android-sume-core-functional-OperatorMap, reason: not valid java name */
    /* synthetic */ void m9588x20aa76dd(OpPriorityComputable opPriorityComputable) {
        opPriorityComputable.setComputeBridge(priorityCheckMap.get(opPriorityComputable.getType()), this.priorityCompute);
    }

    public static List<ImgpType> inferOperations(final MutableMediaFormat mutableMediaFormat, final MediaFormat mediaFormat) {
        final OpPriorityByDataSize opPriorityByDataSize = new OpPriorityByDataSize();
        return (List) ((Map) priorityCheckMap.entrySet().stream().collect(Collectors.toMap(new Function() { // from class: com.samsung.android.sume.core.functional.OperatorMap$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return (ImgpType) ((Map.Entry) obj).getKey();
            }
        }, new Function() { // from class: com.samsung.android.sume.core.functional.OperatorMap$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return Float.valueOf(((OpPriorityComputable.ComputeBridge) ((Map.Entry) obj).getValue()).compute(mutableMediaFormat, mediaFormat, opPriorityByDataSize));
            }
        }))).entrySet().stream().filter(new Predicate() { // from class: com.samsung.android.sume.core.functional.OperatorMap$$ExternalSyntheticLambda2
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

    static /* synthetic */ boolean lambda$inferOperations$4(Map.Entry entry) {
        return ((Float) entry.getValue()).floatValue() >= 0.0f;
    }
}
