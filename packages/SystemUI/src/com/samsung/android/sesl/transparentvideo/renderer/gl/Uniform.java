package com.samsung.android.sesl.transparentvideo.renderer.gl;

import android.opengl.GLES30;
import com.samsung.android.sesl.transparentvideo.renderer.gl.Debugger;
import com.samsung.android.sesl.transparentvideo.renderer.gl.utils.DataType;
import com.samsung.android.sesl.transparentvideo.renderer.gl.utils.GLBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.util.Objects;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes4.dex */
public final class Uniform implements IGLObject {
    public final GLBuffer buffer;
    public int location;
    public final String name;
    public final Program program;
    public final UniformUpdater uniformUpdater;

    public interface UniformUpdater {
        void update();
    }

    public Uniform(Program program, DataType dataType, String str, Object obj, final int i) {
        UniformUpdater uniformUpdater;
        this.program = program;
        this.name = str;
        this.location = -1;
        int dataLength = dataType.getDataLength();
        if (obj instanceof Integer) {
            final int i2 = 0;
            uniformUpdater = new UniformUpdater(this) { // from class: com.samsung.android.sesl.transparentvideo.renderer.gl.Uniform$$ExternalSyntheticLambda0
                public final /* synthetic */ Uniform f$0;

                {
                    this.f$0 = this;
                }

                @Override // com.samsung.android.sesl.transparentvideo.renderer.gl.Uniform.UniformUpdater
                public final void update() {
                    switch (i2) {
                        case 0:
                            Uniform uniform = this.f$0;
                            GLES30.glUniform1iv(uniform.location, 1, (IntBuffer) uniform.buffer.dataBuffer);
                            break;
                        case 1:
                            Uniform uniform2 = this.f$0;
                            GLES30.glUniform1iv(uniform2.location, 1, (IntBuffer) uniform2.buffer.dataBuffer);
                            break;
                        default:
                            Uniform uniform3 = this.f$0;
                            GLES30.glUniform1fv(uniform3.location, 1, (FloatBuffer) uniform3.buffer.dataBuffer);
                            break;
                    }
                }
            };
        } else if (obj instanceof Float) {
            final int i3 = 2;
            uniformUpdater = new UniformUpdater(this) { // from class: com.samsung.android.sesl.transparentvideo.renderer.gl.Uniform$$ExternalSyntheticLambda0
                public final /* synthetic */ Uniform f$0;

                {
                    this.f$0 = this;
                }

                @Override // com.samsung.android.sesl.transparentvideo.renderer.gl.Uniform.UniformUpdater
                public final void update() {
                    switch (i3) {
                        case 0:
                            Uniform uniform = this.f$0;
                            GLES30.glUniform1iv(uniform.location, 1, (IntBuffer) uniform.buffer.dataBuffer);
                            break;
                        case 1:
                            Uniform uniform2 = this.f$0;
                            GLES30.glUniform1iv(uniform2.location, 1, (IntBuffer) uniform2.buffer.dataBuffer);
                            break;
                        default:
                            Uniform uniform3 = this.f$0;
                            GLES30.glUniform1fv(uniform3.location, 1, (FloatBuffer) uniform3.buffer.dataBuffer);
                            break;
                    }
                }
            };
        } else if (obj instanceof int[]) {
            final int i4 = 0;
            uniformUpdater = new UniformUpdater(this) { // from class: com.samsung.android.sesl.transparentvideo.renderer.gl.Uniform$$ExternalSyntheticLambda3
                public final /* synthetic */ Uniform f$0;

                {
                    this.f$0 = this;
                }

                @Override // com.samsung.android.sesl.transparentvideo.renderer.gl.Uniform.UniformUpdater
                public final void update() {
                    switch (i4) {
                        case 0:
                            Uniform uniform = this.f$0;
                            GLES30.glUniform1iv(uniform.location, i, (IntBuffer) uniform.buffer.dataBuffer);
                            break;
                        case 1:
                            Uniform uniform2 = this.f$0;
                            GLES30.glUniform1fv(uniform2.location, i, (FloatBuffer) uniform2.buffer.dataBuffer);
                            break;
                        case 2:
                            Uniform uniform3 = this.f$0;
                            GLES30.glUniform1fv(uniform3.location, i, (FloatBuffer) uniform3.buffer.dataBuffer);
                            break;
                        case 3:
                            Uniform uniform4 = this.f$0;
                            GLES30.glUniform2fv(uniform4.location, i, (FloatBuffer) uniform4.buffer.dataBuffer);
                            break;
                        case 4:
                            Uniform uniform5 = this.f$0;
                            GLES30.glUniform3fv(uniform5.location, i, (FloatBuffer) uniform5.buffer.dataBuffer);
                            break;
                        case 5:
                            Uniform uniform6 = this.f$0;
                            GLES30.glUniform4fv(uniform6.location, i, (FloatBuffer) uniform6.buffer.dataBuffer);
                            break;
                        case 6:
                            Uniform uniform7 = this.f$0;
                            GLES30.glUniformMatrix3fv(uniform7.location, i, false, (FloatBuffer) uniform7.buffer.dataBuffer);
                            break;
                        default:
                            Uniform uniform8 = this.f$0;
                            GLES30.glUniformMatrix4fv(uniform8.location, i, false, (FloatBuffer) uniform8.buffer.dataBuffer);
                            break;
                    }
                }
            };
        } else if (!(obj instanceof float[])) {
            Debugger.Companion.getClass();
            final int i5 = 1;
            uniformUpdater = new UniformUpdater(this) { // from class: com.samsung.android.sesl.transparentvideo.renderer.gl.Uniform$$ExternalSyntheticLambda0
                public final /* synthetic */ Uniform f$0;

                {
                    this.f$0 = this;
                }

                @Override // com.samsung.android.sesl.transparentvideo.renderer.gl.Uniform.UniformUpdater
                public final void update() {
                    switch (i5) {
                        case 0:
                            Uniform uniform = this.f$0;
                            GLES30.glUniform1iv(uniform.location, 1, (IntBuffer) uniform.buffer.dataBuffer);
                            break;
                        case 1:
                            Uniform uniform2 = this.f$0;
                            GLES30.glUniform1iv(uniform2.location, 1, (IntBuffer) uniform2.buffer.dataBuffer);
                            break;
                        default:
                            Uniform uniform3 = this.f$0;
                            GLES30.glUniform1fv(uniform3.location, 1, (FloatBuffer) uniform3.buffer.dataBuffer);
                            break;
                    }
                }
            };
        } else if (dataLength == 1) {
            final int i6 = 2;
            uniformUpdater = new UniformUpdater(this) { // from class: com.samsung.android.sesl.transparentvideo.renderer.gl.Uniform$$ExternalSyntheticLambda3
                public final /* synthetic */ Uniform f$0;

                {
                    this.f$0 = this;
                }

                @Override // com.samsung.android.sesl.transparentvideo.renderer.gl.Uniform.UniformUpdater
                public final void update() {
                    switch (i6) {
                        case 0:
                            Uniform uniform = this.f$0;
                            GLES30.glUniform1iv(uniform.location, i, (IntBuffer) uniform.buffer.dataBuffer);
                            break;
                        case 1:
                            Uniform uniform2 = this.f$0;
                            GLES30.glUniform1fv(uniform2.location, i, (FloatBuffer) uniform2.buffer.dataBuffer);
                            break;
                        case 2:
                            Uniform uniform3 = this.f$0;
                            GLES30.glUniform1fv(uniform3.location, i, (FloatBuffer) uniform3.buffer.dataBuffer);
                            break;
                        case 3:
                            Uniform uniform4 = this.f$0;
                            GLES30.glUniform2fv(uniform4.location, i, (FloatBuffer) uniform4.buffer.dataBuffer);
                            break;
                        case 4:
                            Uniform uniform5 = this.f$0;
                            GLES30.glUniform3fv(uniform5.location, i, (FloatBuffer) uniform5.buffer.dataBuffer);
                            break;
                        case 5:
                            Uniform uniform6 = this.f$0;
                            GLES30.glUniform4fv(uniform6.location, i, (FloatBuffer) uniform6.buffer.dataBuffer);
                            break;
                        case 6:
                            Uniform uniform7 = this.f$0;
                            GLES30.glUniformMatrix3fv(uniform7.location, i, false, (FloatBuffer) uniform7.buffer.dataBuffer);
                            break;
                        default:
                            Uniform uniform8 = this.f$0;
                            GLES30.glUniformMatrix4fv(uniform8.location, i, false, (FloatBuffer) uniform8.buffer.dataBuffer);
                            break;
                    }
                }
            };
        } else if (dataLength == 2) {
            final int i7 = 3;
            uniformUpdater = new UniformUpdater(this) { // from class: com.samsung.android.sesl.transparentvideo.renderer.gl.Uniform$$ExternalSyntheticLambda3
                public final /* synthetic */ Uniform f$0;

                {
                    this.f$0 = this;
                }

                @Override // com.samsung.android.sesl.transparentvideo.renderer.gl.Uniform.UniformUpdater
                public final void update() {
                    switch (i7) {
                        case 0:
                            Uniform uniform = this.f$0;
                            GLES30.glUniform1iv(uniform.location, i, (IntBuffer) uniform.buffer.dataBuffer);
                            break;
                        case 1:
                            Uniform uniform2 = this.f$0;
                            GLES30.glUniform1fv(uniform2.location, i, (FloatBuffer) uniform2.buffer.dataBuffer);
                            break;
                        case 2:
                            Uniform uniform3 = this.f$0;
                            GLES30.glUniform1fv(uniform3.location, i, (FloatBuffer) uniform3.buffer.dataBuffer);
                            break;
                        case 3:
                            Uniform uniform4 = this.f$0;
                            GLES30.glUniform2fv(uniform4.location, i, (FloatBuffer) uniform4.buffer.dataBuffer);
                            break;
                        case 4:
                            Uniform uniform5 = this.f$0;
                            GLES30.glUniform3fv(uniform5.location, i, (FloatBuffer) uniform5.buffer.dataBuffer);
                            break;
                        case 5:
                            Uniform uniform6 = this.f$0;
                            GLES30.glUniform4fv(uniform6.location, i, (FloatBuffer) uniform6.buffer.dataBuffer);
                            break;
                        case 6:
                            Uniform uniform7 = this.f$0;
                            GLES30.glUniformMatrix3fv(uniform7.location, i, false, (FloatBuffer) uniform7.buffer.dataBuffer);
                            break;
                        default:
                            Uniform uniform8 = this.f$0;
                            GLES30.glUniformMatrix4fv(uniform8.location, i, false, (FloatBuffer) uniform8.buffer.dataBuffer);
                            break;
                    }
                }
            };
        } else if (dataLength == 3) {
            final int i8 = 4;
            uniformUpdater = new UniformUpdater(this) { // from class: com.samsung.android.sesl.transparentvideo.renderer.gl.Uniform$$ExternalSyntheticLambda3
                public final /* synthetic */ Uniform f$0;

                {
                    this.f$0 = this;
                }

                @Override // com.samsung.android.sesl.transparentvideo.renderer.gl.Uniform.UniformUpdater
                public final void update() {
                    switch (i8) {
                        case 0:
                            Uniform uniform = this.f$0;
                            GLES30.glUniform1iv(uniform.location, i, (IntBuffer) uniform.buffer.dataBuffer);
                            break;
                        case 1:
                            Uniform uniform2 = this.f$0;
                            GLES30.glUniform1fv(uniform2.location, i, (FloatBuffer) uniform2.buffer.dataBuffer);
                            break;
                        case 2:
                            Uniform uniform3 = this.f$0;
                            GLES30.glUniform1fv(uniform3.location, i, (FloatBuffer) uniform3.buffer.dataBuffer);
                            break;
                        case 3:
                            Uniform uniform4 = this.f$0;
                            GLES30.glUniform2fv(uniform4.location, i, (FloatBuffer) uniform4.buffer.dataBuffer);
                            break;
                        case 4:
                            Uniform uniform5 = this.f$0;
                            GLES30.glUniform3fv(uniform5.location, i, (FloatBuffer) uniform5.buffer.dataBuffer);
                            break;
                        case 5:
                            Uniform uniform6 = this.f$0;
                            GLES30.glUniform4fv(uniform6.location, i, (FloatBuffer) uniform6.buffer.dataBuffer);
                            break;
                        case 6:
                            Uniform uniform7 = this.f$0;
                            GLES30.glUniformMatrix3fv(uniform7.location, i, false, (FloatBuffer) uniform7.buffer.dataBuffer);
                            break;
                        default:
                            Uniform uniform8 = this.f$0;
                            GLES30.glUniformMatrix4fv(uniform8.location, i, false, (FloatBuffer) uniform8.buffer.dataBuffer);
                            break;
                    }
                }
            };
        } else if (dataLength == 4) {
            final int i9 = 5;
            uniformUpdater = new UniformUpdater(this) { // from class: com.samsung.android.sesl.transparentvideo.renderer.gl.Uniform$$ExternalSyntheticLambda3
                public final /* synthetic */ Uniform f$0;

                {
                    this.f$0 = this;
                }

                @Override // com.samsung.android.sesl.transparentvideo.renderer.gl.Uniform.UniformUpdater
                public final void update() {
                    switch (i9) {
                        case 0:
                            Uniform uniform = this.f$0;
                            GLES30.glUniform1iv(uniform.location, i, (IntBuffer) uniform.buffer.dataBuffer);
                            break;
                        case 1:
                            Uniform uniform2 = this.f$0;
                            GLES30.glUniform1fv(uniform2.location, i, (FloatBuffer) uniform2.buffer.dataBuffer);
                            break;
                        case 2:
                            Uniform uniform3 = this.f$0;
                            GLES30.glUniform1fv(uniform3.location, i, (FloatBuffer) uniform3.buffer.dataBuffer);
                            break;
                        case 3:
                            Uniform uniform4 = this.f$0;
                            GLES30.glUniform2fv(uniform4.location, i, (FloatBuffer) uniform4.buffer.dataBuffer);
                            break;
                        case 4:
                            Uniform uniform5 = this.f$0;
                            GLES30.glUniform3fv(uniform5.location, i, (FloatBuffer) uniform5.buffer.dataBuffer);
                            break;
                        case 5:
                            Uniform uniform6 = this.f$0;
                            GLES30.glUniform4fv(uniform6.location, i, (FloatBuffer) uniform6.buffer.dataBuffer);
                            break;
                        case 6:
                            Uniform uniform7 = this.f$0;
                            GLES30.glUniformMatrix3fv(uniform7.location, i, false, (FloatBuffer) uniform7.buffer.dataBuffer);
                            break;
                        default:
                            Uniform uniform8 = this.f$0;
                            GLES30.glUniformMatrix4fv(uniform8.location, i, false, (FloatBuffer) uniform8.buffer.dataBuffer);
                            break;
                    }
                }
            };
        } else if (dataLength == 9) {
            final int i10 = 6;
            uniformUpdater = new UniformUpdater(this) { // from class: com.samsung.android.sesl.transparentvideo.renderer.gl.Uniform$$ExternalSyntheticLambda3
                public final /* synthetic */ Uniform f$0;

                {
                    this.f$0 = this;
                }

                @Override // com.samsung.android.sesl.transparentvideo.renderer.gl.Uniform.UniformUpdater
                public final void update() {
                    switch (i10) {
                        case 0:
                            Uniform uniform = this.f$0;
                            GLES30.glUniform1iv(uniform.location, i, (IntBuffer) uniform.buffer.dataBuffer);
                            break;
                        case 1:
                            Uniform uniform2 = this.f$0;
                            GLES30.glUniform1fv(uniform2.location, i, (FloatBuffer) uniform2.buffer.dataBuffer);
                            break;
                        case 2:
                            Uniform uniform3 = this.f$0;
                            GLES30.glUniform1fv(uniform3.location, i, (FloatBuffer) uniform3.buffer.dataBuffer);
                            break;
                        case 3:
                            Uniform uniform4 = this.f$0;
                            GLES30.glUniform2fv(uniform4.location, i, (FloatBuffer) uniform4.buffer.dataBuffer);
                            break;
                        case 4:
                            Uniform uniform5 = this.f$0;
                            GLES30.glUniform3fv(uniform5.location, i, (FloatBuffer) uniform5.buffer.dataBuffer);
                            break;
                        case 5:
                            Uniform uniform6 = this.f$0;
                            GLES30.glUniform4fv(uniform6.location, i, (FloatBuffer) uniform6.buffer.dataBuffer);
                            break;
                        case 6:
                            Uniform uniform7 = this.f$0;
                            GLES30.glUniformMatrix3fv(uniform7.location, i, false, (FloatBuffer) uniform7.buffer.dataBuffer);
                            break;
                        default:
                            Uniform uniform8 = this.f$0;
                            GLES30.glUniformMatrix4fv(uniform8.location, i, false, (FloatBuffer) uniform8.buffer.dataBuffer);
                            break;
                    }
                }
            };
        } else if (dataLength != 16) {
            Debugger.Companion companion = Debugger.Companion;
            Objects.toString(obj);
            companion.getClass();
            final int i11 = 1;
            uniformUpdater = new UniformUpdater(this) { // from class: com.samsung.android.sesl.transparentvideo.renderer.gl.Uniform$$ExternalSyntheticLambda3
                public final /* synthetic */ Uniform f$0;

                {
                    this.f$0 = this;
                }

                @Override // com.samsung.android.sesl.transparentvideo.renderer.gl.Uniform.UniformUpdater
                public final void update() {
                    switch (i11) {
                        case 0:
                            Uniform uniform = this.f$0;
                            GLES30.glUniform1iv(uniform.location, i, (IntBuffer) uniform.buffer.dataBuffer);
                            break;
                        case 1:
                            Uniform uniform2 = this.f$0;
                            GLES30.glUniform1fv(uniform2.location, i, (FloatBuffer) uniform2.buffer.dataBuffer);
                            break;
                        case 2:
                            Uniform uniform3 = this.f$0;
                            GLES30.glUniform1fv(uniform3.location, i, (FloatBuffer) uniform3.buffer.dataBuffer);
                            break;
                        case 3:
                            Uniform uniform4 = this.f$0;
                            GLES30.glUniform2fv(uniform4.location, i, (FloatBuffer) uniform4.buffer.dataBuffer);
                            break;
                        case 4:
                            Uniform uniform5 = this.f$0;
                            GLES30.glUniform3fv(uniform5.location, i, (FloatBuffer) uniform5.buffer.dataBuffer);
                            break;
                        case 5:
                            Uniform uniform6 = this.f$0;
                            GLES30.glUniform4fv(uniform6.location, i, (FloatBuffer) uniform6.buffer.dataBuffer);
                            break;
                        case 6:
                            Uniform uniform7 = this.f$0;
                            GLES30.glUniformMatrix3fv(uniform7.location, i, false, (FloatBuffer) uniform7.buffer.dataBuffer);
                            break;
                        default:
                            Uniform uniform8 = this.f$0;
                            GLES30.glUniformMatrix4fv(uniform8.location, i, false, (FloatBuffer) uniform8.buffer.dataBuffer);
                            break;
                    }
                }
            };
        } else {
            final int i12 = 7;
            uniformUpdater = new UniformUpdater(this) { // from class: com.samsung.android.sesl.transparentvideo.renderer.gl.Uniform$$ExternalSyntheticLambda3
                public final /* synthetic */ Uniform f$0;

                {
                    this.f$0 = this;
                }

                @Override // com.samsung.android.sesl.transparentvideo.renderer.gl.Uniform.UniformUpdater
                public final void update() {
                    switch (i12) {
                        case 0:
                            Uniform uniform = this.f$0;
                            GLES30.glUniform1iv(uniform.location, i, (IntBuffer) uniform.buffer.dataBuffer);
                            break;
                        case 1:
                            Uniform uniform2 = this.f$0;
                            GLES30.glUniform1fv(uniform2.location, i, (FloatBuffer) uniform2.buffer.dataBuffer);
                            break;
                        case 2:
                            Uniform uniform3 = this.f$0;
                            GLES30.glUniform1fv(uniform3.location, i, (FloatBuffer) uniform3.buffer.dataBuffer);
                            break;
                        case 3:
                            Uniform uniform4 = this.f$0;
                            GLES30.glUniform2fv(uniform4.location, i, (FloatBuffer) uniform4.buffer.dataBuffer);
                            break;
                        case 4:
                            Uniform uniform5 = this.f$0;
                            GLES30.glUniform3fv(uniform5.location, i, (FloatBuffer) uniform5.buffer.dataBuffer);
                            break;
                        case 5:
                            Uniform uniform6 = this.f$0;
                            GLES30.glUniform4fv(uniform6.location, i, (FloatBuffer) uniform6.buffer.dataBuffer);
                            break;
                        case 6:
                            Uniform uniform7 = this.f$0;
                            GLES30.glUniformMatrix3fv(uniform7.location, i, false, (FloatBuffer) uniform7.buffer.dataBuffer);
                            break;
                        default:
                            Uniform uniform8 = this.f$0;
                            GLES30.glUniformMatrix4fv(uniform8.location, i, false, (FloatBuffer) uniform8.buffer.dataBuffer);
                            break;
                    }
                }
            };
        }
        this.uniformUpdater = uniformUpdater;
        dataType.check(obj);
        this.buffer = new GLBuffer(obj);
    }

    @Override // com.samsung.android.sesl.transparentvideo.renderer.gl.IGLObject
    public final void bind() {
        this.uniformUpdater.update();
    }

    @Override // com.samsung.android.sesl.transparentvideo.renderer.gl.IGLObject
    public final void create() {
        this.location = GLES30.glGetUniformLocation(this.program.id, this.name);
    }

    @Override // com.samsung.android.sesl.transparentvideo.renderer.gl.IGLObject
    public final void dispose() {
    }

    public /* synthetic */ Uniform(Program program, DataType dataType, String str, Object obj, int i, int i2, DefaultConstructorMarker defaultConstructorMarker) {
        this(program, dataType, str, obj, (i2 & 16) != 0 ? 1 : i);
    }
}
