package com.samsung.android.sesl.transparentvideo.renderer.gl.utils;

import com.samsung.android.sesl.transparentvideo.renderer.gl.Debugger;
import java.nio.Buffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Objects;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;

/* loaded from: classes4.dex */
public final class GLBuffer {
    public final Buffer dataBuffer;
    public final Function1 dataUpdater;

    public GLBuffer(final Object obj) {
        Buffer bufferAllocate;
        Function1 function1;
        boolean z = obj instanceof Integer;
        if (z) {
            bufferAllocate = IntBuffer.allocate(1);
        } else if (obj instanceof Float) {
            bufferAllocate = FloatBuffer.allocate(1);
        } else if (obj instanceof int[]) {
            bufferAllocate = IntBuffer.allocate(((int[]) obj).length);
        } else if (obj instanceof float[]) {
            bufferAllocate = FloatBuffer.allocate(((float[]) obj).length);
        } else {
            Debugger.Companion companion = Debugger.Companion;
            Objects.toString(obj);
            companion.getClass();
            bufferAllocate = IntBuffer.allocate(1);
            bufferAllocate.getClass();
        }
        this.dataBuffer = bufferAllocate;
        if (z) {
            final int i = 0;
            function1 = new Function1() { // from class: com.samsung.android.sesl.transparentvideo.renderer.gl.utils.GLBuffer$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj2) {
                    switch (i) {
                        case 0:
                            ((IntBuffer) ((GLBuffer) this).dataBuffer).put(((Integer) obj2).intValue());
                            break;
                        case 1:
                            ((FloatBuffer) ((GLBuffer) this).dataBuffer).put(((Float) obj2).floatValue());
                            break;
                        case 2:
                            ((IntBuffer) ((GLBuffer) this).dataBuffer).put((int[]) obj2);
                            break;
                        case 3:
                            ((FloatBuffer) ((GLBuffer) this).dataBuffer).put((float[]) obj2);
                            break;
                        default:
                            Debugger.Companion companion2 = Debugger.Companion;
                            Objects.toString(this);
                            companion2.getClass();
                            break;
                    }
                    return Unit.INSTANCE;
                }
            };
        } else if (obj instanceof Float) {
            final int i2 = 1;
            function1 = new Function1() { // from class: com.samsung.android.sesl.transparentvideo.renderer.gl.utils.GLBuffer$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj2) {
                    switch (i2) {
                        case 0:
                            ((IntBuffer) ((GLBuffer) this).dataBuffer).put(((Integer) obj2).intValue());
                            break;
                        case 1:
                            ((FloatBuffer) ((GLBuffer) this).dataBuffer).put(((Float) obj2).floatValue());
                            break;
                        case 2:
                            ((IntBuffer) ((GLBuffer) this).dataBuffer).put((int[]) obj2);
                            break;
                        case 3:
                            ((FloatBuffer) ((GLBuffer) this).dataBuffer).put((float[]) obj2);
                            break;
                        default:
                            Debugger.Companion companion2 = Debugger.Companion;
                            Objects.toString(this);
                            companion2.getClass();
                            break;
                    }
                    return Unit.INSTANCE;
                }
            };
        } else if (obj instanceof int[]) {
            final int i3 = 2;
            function1 = new Function1() { // from class: com.samsung.android.sesl.transparentvideo.renderer.gl.utils.GLBuffer$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj2) {
                    switch (i3) {
                        case 0:
                            ((IntBuffer) ((GLBuffer) this).dataBuffer).put(((Integer) obj2).intValue());
                            break;
                        case 1:
                            ((FloatBuffer) ((GLBuffer) this).dataBuffer).put(((Float) obj2).floatValue());
                            break;
                        case 2:
                            ((IntBuffer) ((GLBuffer) this).dataBuffer).put((int[]) obj2);
                            break;
                        case 3:
                            ((FloatBuffer) ((GLBuffer) this).dataBuffer).put((float[]) obj2);
                            break;
                        default:
                            Debugger.Companion companion2 = Debugger.Companion;
                            Objects.toString(this);
                            companion2.getClass();
                            break;
                    }
                    return Unit.INSTANCE;
                }
            };
        } else if (obj instanceof float[]) {
            final int i4 = 3;
            function1 = new Function1() { // from class: com.samsung.android.sesl.transparentvideo.renderer.gl.utils.GLBuffer$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj2) {
                    switch (i4) {
                        case 0:
                            ((IntBuffer) ((GLBuffer) this).dataBuffer).put(((Integer) obj2).intValue());
                            break;
                        case 1:
                            ((FloatBuffer) ((GLBuffer) this).dataBuffer).put(((Float) obj2).floatValue());
                            break;
                        case 2:
                            ((IntBuffer) ((GLBuffer) this).dataBuffer).put((int[]) obj2);
                            break;
                        case 3:
                            ((FloatBuffer) ((GLBuffer) this).dataBuffer).put((float[]) obj2);
                            break;
                        default:
                            Debugger.Companion companion2 = Debugger.Companion;
                            Objects.toString(this);
                            companion2.getClass();
                            break;
                    }
                    return Unit.INSTANCE;
                }
            };
        } else {
            final int i5 = 4;
            function1 = new Function1() { // from class: com.samsung.android.sesl.transparentvideo.renderer.gl.utils.GLBuffer$$ExternalSyntheticLambda0
                @Override // kotlin.jvm.functions.Function1
                /* renamed from: invoke */
                public final Object mo781invoke(Object obj2) {
                    switch (i5) {
                        case 0:
                            ((IntBuffer) ((GLBuffer) obj).dataBuffer).put(((Integer) obj2).intValue());
                            break;
                        case 1:
                            ((FloatBuffer) ((GLBuffer) obj).dataBuffer).put(((Float) obj2).floatValue());
                            break;
                        case 2:
                            ((IntBuffer) ((GLBuffer) obj).dataBuffer).put((int[]) obj2);
                            break;
                        case 3:
                            ((FloatBuffer) ((GLBuffer) obj).dataBuffer).put((float[]) obj2);
                            break;
                        default:
                            Debugger.Companion companion2 = Debugger.Companion;
                            Objects.toString(obj);
                            companion2.getClass();
                            break;
                    }
                    return Unit.INSTANCE;
                }
            };
        }
        this.dataUpdater = function1;
        function1.mo781invoke(obj);
        bufferAllocate.rewind();
    }
}
