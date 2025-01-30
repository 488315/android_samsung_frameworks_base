package com.samsung.android.sume.core.format;

import com.samsung.android.sume.core.buffer.MutableMediaBuffer$$ExternalSyntheticLambda12;
import java.util.Optional;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

/* loaded from: classes4.dex */
class StapleUpdatableMediaFormat extends StapleMediaFormat implements UpdatableMediaFormat {
    protected int flags;
    MutableMediaFormat source;
    BiConsumer<MediaFormat, MutableMediaFormat> updater;

    public StapleUpdatableMediaFormat(MediaFormat source) {
        super(((StapleMediaFormat) source).impl);
        this.flags = 0;
    }

    @Override // com.samsung.android.sume.core.format.UpdatableMediaFormat
    public UpdatableMediaFormat set(String key) {
        ((StapleMutableMediaFormat) this.impl).attributes.put(key, null);
        return this;
    }

    @Override // com.samsung.android.sume.core.format.UpdatableMediaFormat
    public UpdatableMediaFormat with(MediaFormat source) {
        if (source instanceof MutableMediaFormat) {
            this.source = (MutableMediaFormat) source;
        } else {
            this.source = source.toMutableFormat();
        }
        return this;
    }

    @Override // com.samsung.android.sume.core.format.UpdatableMediaFormat
    public UpdatableMediaFormat setUpdater(BiConsumer<MediaFormat, MutableMediaFormat> updater) {
        this.updater = updater;
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

    private int coerceAtMostRows(int top, int rows) {
        return Math.min(top + rows, this.source.getRows()) - top;
    }

    private int coerceAtMostCols(int left, int cols) {
        return Math.min(left + cols, this.source.getCols()) - left;
    }

    private int[] getCroppedRect() {
        return (int[]) Optional.ofNullable((int[]) get("crop")).map(new Function() { // from class: com.samsung.android.sume.core.format.StapleUpdatableMediaFormat$$ExternalSyntheticLambda4
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return StapleUpdatableMediaFormat.this.m402x7e198b7e((int[]) obj);
            }
        }).orElseGet(new Supplier() { // from class: com.samsung.android.sume.core.format.StapleUpdatableMediaFormat$$ExternalSyntheticLambda5
            @Override // java.util.function.Supplier
            public final Object get() {
                return StapleUpdatableMediaFormat.this.m404x19987b80();
            }
        });
    }

    /* renamed from: lambda$getCroppedRect$0$com-samsung-android-sume-core-format-StapleUpdatableMediaFormat */
    /* synthetic */ int[] m402x7e198b7e(int[] e) {
        return new int[]{e[0], e[1], coerceAtMostCols(e[0], e[2]), coerceAtMostRows(e[1], e[3])};
    }

    /* renamed from: lambda$getCroppedRect$1$com-samsung-android-sume-core-format-StapleUpdatableMediaFormat */
    /* synthetic */ int[] m403xcbd9037f(int[] e) {
        return new int[]{Math.max(0, (this.source.getCols() - e[0]) >> 1), Math.max(0, (this.source.getRows() - e[1]) >> 1), Math.min(e[0], this.source.getCols()), Math.min(e[1], this.source.getRows())};
    }

    /* renamed from: lambda$getCroppedRect$2$com-samsung-android-sume-core-format-StapleUpdatableMediaFormat */
    /* synthetic */ int[] m404x19987b80() {
        return (int[]) Optional.ofNullable((int[]) get("center-crop")).map(new Function() { // from class: com.samsung.android.sume.core.format.StapleUpdatableMediaFormat$$ExternalSyntheticLambda3
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return StapleUpdatableMediaFormat.this.m403xcbd9037f((int[]) obj);
            }
        }).orElseThrow(new MutableMediaBuffer$$ExternalSyntheticLambda12());
    }

    @Override // com.samsung.android.sume.core.format.UpdatableMediaFormat
    public Shape getCroppedShape() {
        return (Shape) Optional.ofNullable((int[]) get("crop")).map(new Function() { // from class: com.samsung.android.sume.core.format.StapleUpdatableMediaFormat$$ExternalSyntheticLambda1
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return StapleUpdatableMediaFormat.this.m405x3e8178f2((int[]) obj);
            }
        }).orElseGet(new Supplier() { // from class: com.samsung.android.sume.core.format.StapleUpdatableMediaFormat$$ExternalSyntheticLambda2
            @Override // java.util.function.Supplier
            public final Object get() {
                return StapleUpdatableMediaFormat.this.m407xda0068f4();
            }
        });
    }

    /* renamed from: lambda$getCroppedShape$3$com-samsung-android-sume-core-format-StapleUpdatableMediaFormat */
    /* synthetic */ Shape m405x3e8178f2(int[] e) {
        return Shape.m398of(this.source.getBatch(), coerceAtMostRows(e[1], e[3]), coerceAtMostCols(e[0], e[2]), this.source.getChannels());
    }

    /* renamed from: lambda$getCroppedShape$4$com-samsung-android-sume-core-format-StapleUpdatableMediaFormat */
    /* synthetic */ Shape m406x8c40f0f3(int[] e) {
        return Shape.m398of(this.source.getBatch(), Math.min(e[1], this.source.getRows()), Math.min(e[0], this.source.getCols()), this.source.getChannels());
    }

    /* renamed from: lambda$getCroppedShape$5$com-samsung-android-sume-core-format-StapleUpdatableMediaFormat */
    /* synthetic */ Shape m407xda0068f4() {
        return (Shape) Optional.ofNullable((int[]) get("center-crop")).map(new Function() { // from class: com.samsung.android.sume.core.format.StapleUpdatableMediaFormat$$ExternalSyntheticLambda0
            @Override // java.util.function.Function
            public final Object apply(Object obj) {
                return StapleUpdatableMediaFormat.this.m406x8c40f0f3((int[]) obj);
            }
        }).orElseThrow(new MutableMediaBuffer$$ExternalSyntheticLambda12());
    }

    @Override // com.samsung.android.sume.core.format.StapleMediaFormat
    public String toString() {
        return contentToString(this);
    }
}
