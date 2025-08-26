package com.samsung.android.sesl.transparentvideo.renderer.gl;

import android.opengl.GLES30;
import android.util.Log;
import com.android.keyguard.KeyguardPluginControllerImpl$$ExternalSyntheticOutline0;
import com.samsung.android.sesl.transparentvideo.renderer.gl.Debugger;
import java.nio.IntBuffer;
import kotlin.enums.EnumEntriesKt;

/* loaded from: classes4.dex */
public final class Shader implements IGLObject {
    public int id = -1;
    public boolean isAlive;
    public final String shaderString;
    public final TYPE type;

    /* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
    /* JADX WARN: Unknown enum class pattern. Please report as an issue! */
    public final class TYPE {
        public static final /* synthetic */ TYPE[] $VALUES;
        public static final TYPE FRAGMENT;
        public static final TYPE VERTEX;

        static {
            TYPE type = new TYPE("VERTEX", 0);
            VERTEX = type;
            TYPE type2 = new TYPE("FRAGMENT", 1);
            FRAGMENT = type2;
            TYPE[] typeArr = {type, type2};
            $VALUES = typeArr;
            EnumEntriesKt.enumEntries(typeArr);
        }

        private TYPE(String str, int i) {
        }

        public static TYPE valueOf(String str) {
            return (TYPE) Enum.valueOf(TYPE.class, str);
        }

        public static TYPE[] values() {
            return (TYPE[]) $VALUES.clone();
        }
    }

    public Shader(TYPE type, String str) {
        this.type = type;
        this.shaderString = str;
    }

    @Override // com.samsung.android.sesl.transparentvideo.renderer.gl.IGLObject
    public final void create() {
        if (this.isAlive) {
            return;
        }
        TYPE type = TYPE.VERTEX;
        TYPE type2 = this.type;
        if (type2 == type) {
            this.id = GLES30.glCreateShader(35633);
        } else if (type2 == TYPE.FRAGMENT) {
            this.id = GLES30.glCreateShader(35632);
        }
        GLES30.glShaderSource(this.id, this.shaderString);
        GLES30.glCompileShader(this.id);
        IntBuffer intBufferAllocate = IntBuffer.allocate(1);
        GLES30.glGetShaderiv(this.id, 35713, intBufferAllocate);
        if (intBufferAllocate.get(0) == 1) {
            Log.i("tvShader", "GL Shader compiled successfully id: " + this.id + ".");
        } else {
            KeyguardPluginControllerImpl$$ExternalSyntheticOutline0.m("GL Shader not compiled: ", GLES30.glGetShaderInfoLog(this.id), "tvShader");
            Debugger.Companion companion = Debugger.Companion;
            GLES30.glGetShaderInfoLog(this.id);
            companion.getClass();
        }
        this.isAlive = true;
    }

    @Override // com.samsung.android.sesl.transparentvideo.renderer.gl.IGLObject
    public final void dispose() {
        if (this.isAlive) {
            GLES30.glDeleteShader(this.id);
            this.isAlive = false;
        }
    }

    @Override // com.samsung.android.sesl.transparentvideo.renderer.gl.IGLObject
    public final void bind() {
    }
}
