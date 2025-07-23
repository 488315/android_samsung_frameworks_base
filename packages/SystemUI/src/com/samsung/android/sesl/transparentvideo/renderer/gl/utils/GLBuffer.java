package com.samsung.android.sesl.transparentvideo.renderer.gl.utils;

import com.samsung.android.sesl.transparentvideo.renderer.gl.Debugger;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Objects;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class GLBuffer {
    public final Buffer dataBuffer;
    public final Lambda dataUpdater;

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r0v10 */
    /* JADX WARN: Type inference failed for: r0v11 */
    /* JADX WARN: Type inference failed for: r0v12 */
    /* JADX WARN: Type inference failed for: r0v13 */
    /* JADX WARN: Type inference failed for: r0v14 */
    /* JADX WARN: Type inference failed for: r0v8, types: [kotlin.jvm.functions.Function1, kotlin.jvm.internal.Lambda] */
    public GLBuffer(final Object obj) {
        Buffer allocate;
        boolean z = obj instanceof Integer;
        if (z) {
            allocate = IntBuffer.allocate(1);
        } else if (obj instanceof Float) {
            allocate = FloatBuffer.allocate(1);
        } else if (obj instanceof int[]) {
            allocate = IntBuffer.allocate(((int[]) obj).length);
        } else if (obj instanceof float[]) {
            allocate = FloatBuffer.allocate(((float[]) obj).length);
        } else {
            Debugger.Companion companion = Debugger.Companion;
            Objects.toString(obj);
            companion.getClass();
            allocate = IntBuffer.allocate(1);
            allocate.getClass();
        }
        this.dataBuffer = allocate;
        ?? r0 = z ? new Function1() { // from class: com.samsung.android.sesl.transparentvideo.renderer.gl.utils.GLBuffer$dataUpdater$1
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj2) {
                ((IntBuffer) GLBuffer.this.dataBuffer).put(((Integer) obj2).intValue());
                return Unit.INSTANCE;
            }
        } : obj instanceof Float ? new Function1() { // from class: com.samsung.android.sesl.transparentvideo.renderer.gl.utils.GLBuffer$dataUpdater$2
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj2) {
                ((FloatBuffer) GLBuffer.this.dataBuffer).put(((Float) obj2).floatValue());
                return Unit.INSTANCE;
            }
        } : obj instanceof int[] ? new Function1() { // from class: com.samsung.android.sesl.transparentvideo.renderer.gl.utils.GLBuffer$dataUpdater$3
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj2) {
                ((IntBuffer) GLBuffer.this.dataBuffer).put((int[]) obj2);
                return Unit.INSTANCE;
            }
        } : obj instanceof float[] ? new Function1() { // from class: com.samsung.android.sesl.transparentvideo.renderer.gl.utils.GLBuffer$dataUpdater$4
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj2) {
                ((FloatBuffer) GLBuffer.this.dataBuffer).put((float[]) obj2);
                return Unit.INSTANCE;
            }
        } : new Function1() { // from class: com.samsung.android.sesl.transparentvideo.renderer.gl.utils.GLBuffer$dataUpdater$5
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(1);
            }

            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj2) {
                Debugger.Companion companion2 = Debugger.Companion;
                Objects.toString(obj);
                companion2.getClass();
                return Unit.INSTANCE;
            }
        };
        this.dataUpdater = r0;
        r0.mo779invoke(obj);
        allocate.rewind();
    }
}
