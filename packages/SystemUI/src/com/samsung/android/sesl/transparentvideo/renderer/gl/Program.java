package com.samsung.android.sesl.transparentvideo.renderer.gl;

import android.opengl.GLES30;
import com.samsung.android.sesl.transparentvideo.renderer.gl.Debugger;
import java.nio.IntBuffer;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class Program implements IGLObject {
    public final Shader fShader;
    public int id = -1;
    public boolean isAlive;
    public final Shader vShader;

    public Program(Shader shader, Shader shader2) {
        this.vShader = shader;
        this.fShader = shader2;
    }

    @Override // com.samsung.android.sesl.transparentvideo.renderer.gl.IGLObject
    public final void bind() {
        Debugger.Companion.getClass();
        GLES30.glUseProgram(this.id);
    }

    @Override // com.samsung.android.sesl.transparentvideo.renderer.gl.IGLObject
    public final void create() {
        Shader shader = this.vShader;
        if (!shader.isAlive) {
            shader.create();
        }
        Shader shader2 = this.fShader;
        if (!shader2.isAlive) {
            shader2.create();
        }
        if (!shader.isAlive || !shader2.isAlive) {
            this.isAlive = false;
            return;
        }
        int glCreateProgram = GLES30.glCreateProgram();
        this.id = glCreateProgram;
        GLES30.glAttachShader(glCreateProgram, shader.id);
        GLES30.glAttachShader(this.id, shader2.id);
        GLES30.glLinkProgram(this.id);
        IntBuffer allocate = IntBuffer.allocate(1);
        GLES30.glGetProgramiv(this.id, 35714, allocate);
        if (allocate.get(0) == 0) {
            Debugger.Companion companion = Debugger.Companion;
            GLES30.glGetProgramInfoLog(this.id);
            companion.getClass();
        }
        GLES30.glUseProgram(this.id);
        this.isAlive = true;
    }

    @Override // com.samsung.android.sesl.transparentvideo.renderer.gl.IGLObject
    public final void dispose() {
        if (this.isAlive) {
            GLES30.glDeleteProgram(this.id);
            this.isAlive = false;
        }
    }
}
