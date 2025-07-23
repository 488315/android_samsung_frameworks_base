package com.samsung.android.sume.core.format;

import com.samsung.android.sume.core.buffer.MutableMediaBuffer$$ExternalSyntheticLambda3;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

/* loaded from: classes6.dex */
class StapleUpdatableMediaFormat extends StapleMediaFormat implements UpdatableMediaFormat {
    protected int flags;
    MutableMediaFormat source;
    BiConsumer<MediaFormat, MutableMediaFormat> updater;

    public StapleUpdatableMediaFormat(MediaFormat mediaFormat) {
        super(((StapleMediaFormat) mediaFormat).impl);
        this.flags = 0;
    }

    @Override // com.samsung.android.sume.core.format.UpdatableMediaFormat
    public UpdatableMediaFormat set(String str) {
        ((StapleMutableMediaFormat) this.impl).attributes.put(str, null);
        return this;
    }

    @Override // com.samsung.android.sume.core.format.UpdatableMediaFormat
    public UpdatableMediaFormat with(MediaFormat mediaFormat) {
        if (mediaFormat instanceof MutableMediaFormat) {
            this.source = (MutableMediaFormat) mediaFormat;
            return this;
        }
        this.source = mediaFormat.toMutableFormat();
        return this;
    }

    @Override // com.samsung.android.sume.core.format.UpdatableMediaFormat
    public UpdatableMediaFormat setUpdater(BiConsumer<MediaFormat, MutableMediaFormat> biConsumer) {
        this.updater = biConsumer;
        return this;
    }

    @Override // com.samsung.android.sume.core.format.StapleMediaFormat, com.samsung.android.sume.core.format.MediaFormat
    public <T> T get(String str) {
        if (str.equals("crop-rect") && !contains(str) && containsAnyOf("crop", "center-crop")) {
            this.impl.set("crop-rect", getCroppedRect());
        }
        return (T) this.impl.get(str);
    }

    @Override // com.samsung.android.sume.core.format.UpdatableMediaFormat
    public MediaFormat update() {
        BiConsumer<MediaFormat, MutableMediaFormat> biConsumer = this.updater;
        if (biConsumer != null) {
            biConsumer.accept(this, this.source);
        }
        return this.source.toMediaFormat();
    }

    private int coerceAtMostRows(int i, int i2) {
        return Math.min(i2 + i, this.source.getRows()) - i;
    }

    private int coerceAtMostCols(int i, int i2) {
        return Math.min(i2 + i, this.source.getCols()) - i;
    }

    private int[] getCroppedRect() {
        return (int[]) Optional.ofNullable((int[]) get("crop")).map(new Function() { // from class: com.samsung.android.sume.core.format.StapleUpdatableMediaFormat$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return StapleUpdatableMediaFormat.this.m9569x7e198b7e((int[]) obj);
            }
        }).orElseGet(new Supplier() { // from class: com.samsung.android.sume.core.format.StapleUpdatableMediaFormat$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final Object get() {
                return StapleUpdatableMediaFormat.this.m9571x19987b80();
            }
        });
    }

    /* renamed from: lambda$getCroppedRect$0$com-samsung-android-sume-core-format-StapleUpdatableMediaFormat, reason: not valid java name */
    /* synthetic */ int[] m9569x7e198b7e(int[] iArr) {
        int i = iArr[0];
        return new int[]{i, iArr[1], coerceAtMostCols(i, iArr[2]), coerceAtMostRows(iArr[1], iArr[3])};
    }

    /* renamed from: lambda$getCroppedRect$1$com-samsung-android-sume-core-format-StapleUpdatableMediaFormat, reason: not valid java name */
    /* synthetic */ int[] m9570xcbd9037f(int[] iArr) {
        return new int[]{Math.max(0, (this.source.getCols() - iArr[0]) >> 1), Math.max(0, (this.source.getRows() - iArr[1]) >> 1), Math.min(iArr[0], this.source.getCols()), Math.min(iArr[1], this.source.getRows())};
    }

    /* renamed from: lambda$getCroppedRect$2$com-samsung-android-sume-core-format-StapleUpdatableMediaFormat, reason: not valid java name */
    /* synthetic */ int[] m9571x19987b80() {
        return (int[]) Optional.ofNullable((int[]) get("center-crop")).map(new Function() { // from class: com.samsung.android.sume.core.format.StapleUpdatableMediaFormat$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return StapleUpdatableMediaFormat.this.m9570xcbd9037f((int[]) obj);
            }
        }).orElseThrow(new MutableMediaBuffer$$ExternalSyntheticLambda3());
    }

    @Override // com.samsung.android.sume.core.format.UpdatableMediaFormat
    public Shape getCroppedShape() {
        return (Shape) Optional.ofNullable((int[]) get("crop")).map(new Function() { // from class: com.samsung.android.sume.core.format.StapleUpdatableMediaFormat$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return StapleUpdatableMediaFormat.this.m9572x3e8178f2((int[]) obj);
            }
        }).orElseGet(new Supplier() { // from class: com.samsung.android.sume.core.format.StapleUpdatableMediaFormat$$ExternalSyntheticLambda5
            @Override // java.util.function.Supplier
            public final Object get() {
                return StapleUpdatableMediaFormat.this.m9574xda0068f4();
            }
        });
    }

    /* renamed from: lambda$getCroppedShape$3$com-samsung-android-sume-core-format-StapleUpdatableMediaFormat, reason: not valid java name */
    /* synthetic */ Shape m9572x3e8178f2(int[] iArr) {
        return Shape.of(this.source.getBatch(), coerceAtMostRows(iArr[1], iArr[3]), coerceAtMostCols(iArr[0], iArr[2]), this.source.getChannels());
    }

    /* renamed from: lambda$getCroppedShape$4$com-samsung-android-sume-core-format-StapleUpdatableMediaFormat, reason: not valid java name */
    /* synthetic */ Shape m9573x8c40f0f3(int[] iArr) {
        return Shape.of(this.source.getBatch(), Math.min(iArr[1], this.source.getRows()), Math.min(iArr[0], this.source.getCols()), this.source.getChannels());
    }

    /* renamed from: lambda$getCroppedShape$5$com-samsung-android-sume-core-format-StapleUpdatableMediaFormat, reason: not valid java name */
    /* synthetic */ Shape m9574xda0068f4() {
        return (Shape) Optional.ofNullable((int[]) get("center-crop")).map(new Function() { // from class: com.samsung.android.sume.core.format.StapleUpdatableMediaFormat$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return StapleUpdatableMediaFormat.this.m9573x8c40f0f3((int[]) obj);
            }
        }).orElseThrow(new MutableMediaBuffer$$ExternalSyntheticLambda3());
    }

    @Override // com.samsung.android.sume.core.format.StapleMediaFormat
    public String toString() {
        return contentToString(this);
    }
}
