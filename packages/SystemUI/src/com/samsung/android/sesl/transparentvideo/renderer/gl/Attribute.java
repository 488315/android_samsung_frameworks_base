package com.samsung.android.sesl.transparentvideo.renderer.gl;

import android.opengl.GLES30;
import android.util.Log;
import com.samsung.android.sesl.transparentvideo.renderer.gl.Debugger;
import com.samsung.android.sesl.transparentvideo.renderer.gl.utils.DataType;
import com.samsung.android.sesl.transparentvideo.renderer.gl.utils.GLBuffer;
import java.nio.IntBuffer;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class Attribute implements IGLObject {
    public final GLBuffer buffer;
    public int location = -1;
    public final String name;
    public final Program program;
    public final DataType type;
    public int vboId;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public abstract /* synthetic */ class WhenMappings {
        public static final /* synthetic */ int[] $EnumSwitchMapping$0;

        static {
            int[] iArr = new int[DataType.values().length];
            try {
                iArr[DataType.INT.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            $EnumSwitchMapping$0 = iArr;
        }
    }

    static {
        new Companion(null);
    }

    public Attribute(Program program, DataType dataType, String str, Object obj) {
        this.program = program;
        this.type = dataType;
        this.name = str;
        dataType.check(obj);
        this.buffer = new GLBuffer(obj);
    }

    @Override // com.samsung.android.sesl.transparentvideo.renderer.gl.IGLObject
    public final void bind() {
        int i = this.location;
        if (i == -1) {
            return;
        }
        DataType dataType = this.type;
        GLES30.glVertexAttribPointer(i, dataType.getDataLength(), WhenMappings.$EnumSwitchMapping$0[dataType.ordinal()] == 1 ? 5124 : 5126, false, 0, 0);
        GLES30.glEnableVertexAttribArray(this.location);
    }

    @Override // com.samsung.android.sesl.transparentvideo.renderer.gl.IGLObject
    public final void create() {
        Program program = this.program;
        int i = program.id;
        String str = this.name;
        int glGetAttribLocation = GLES30.glGetAttribLocation(i, str);
        this.location = glGetAttribLocation;
        if (glGetAttribLocation == -1) {
            Debugger.Companion companion = Debugger.Companion;
            String str2 = "Attribute:" + str + " doesn't exist in current program" + program.id;
            companion.getClass();
            Log.d("Attribute", str2);
            return;
        }
        IntBuffer allocate = IntBuffer.allocate(1);
        GLES30.glGenBuffers(1, allocate);
        this.vboId = allocate.get(0);
        allocate.rewind();
        GLES30.glBindBuffer(34962, this.vboId);
        GLBuffer gLBuffer = this.buffer;
        GLES30.glBufferData(34962, gLBuffer.dataBuffer.capacity() * 4, gLBuffer.dataBuffer, 35044);
        int i2 = this.location;
        DataType dataType = this.type;
        GLES30.glVertexAttribPointer(i2, dataType.getDataLength(), WhenMappings.$EnumSwitchMapping$0[dataType.ordinal()] == 1 ? 5124 : 5126, false, 0, 0);
        GLES30.glEnableVertexAttribArray(this.location);
    }

    @Override // com.samsung.android.sesl.transparentvideo.renderer.gl.IGLObject
    public final void dispose() {
        IntBuffer allocate = IntBuffer.allocate(1);
        allocate.put(this.vboId);
        allocate.rewind();
        GLES30.glDeleteBuffers(1, allocate);
    }
}
