package com.samsung.android.graphics.imagefilter;

import android.graphics.RenderEffect;
import android.graphics.RuntimeShader;

/* loaded from: classes6.dex */
public abstract class FilterEffect {
    private int filterType = -1;

    public static class FilterParamType {
        public static final int PARAM_BLUR_RADIUS = 0;
        public static final int PARAM_CURVE_LEVEL = 2;
        public static final int PARAM_CURVE_MAXX = 3;
        public static final int PARAM_CURVE_MAXY = 5;
        public static final int PARAM_CURVE_MINX = 4;
        public static final int PARAM_CURVE_MINY = 6;
        public static final int PARAM_DITHER_ENABLE = 7;
        public static final int PARAM_PROSATURATION = 1;
        public static final int PARAM_SATURATION = 8;
    }

    public void clear() {
    }

    public abstract String getMainShaderCode();

    public abstract float getParam(int i);

    public abstract RuntimeShader getShader();

    public abstract void setParam(int i, float f);

    public abstract void updateShader(RuntimeShader runtimeShader);

    public boolean useShaderCode() {
        return true;
    }

    public static class FilterType {
        public static final int BLUR = 1;
        public static final int CURVE = 2;
        public static final int DITHER = 4;
        public static final int PROSATURATION = 8;
        public static final int SATURATION = 16;
        private final int filterMask;

        FilterType(int i) {
            this.filterMask = i;
        }

        public int getType() {
            return this.filterMask;
        }
    }

    public String getDeclareShaderCode() {
        return "";
    }

    public String getFunctionShaderCode() {
        return "";
    }

    public String assembleShaderCode(String str, String str2, String str3) {
        return ((((((("" + str) + str2) + "uniform shader viewImage;\n") + "vec4 main(vec2 fragCoord) {\n") + "    vec4 sampledColor = viewImage.eval(fragCoord);\n") + str3) + "    return sampledColor;\n") + ShaderAssembler.SHADER_MAIN_CODE_END;
    }

    public void setFilterType(int i) {
        this.filterType = i;
    }

    public int getFilterType() {
        return this.filterType;
    }

    public String getFilterName() {
        int i = this.filterType;
        if (i == 1) {
            return "BLUR";
        }
        if (i == 2) {
            return "CURVE";
        }
        if (i == 4) {
            return "DITHER";
        }
        if (i == 8) {
            return "PROSATURATION";
        }
        if (i == 16) {
            return "SATURATION";
        }
        return "No Filter";
    }

    public RenderEffect getRenderEffect() {
        return RenderEffect.createRuntimeShaderEffect(getShader(), "viewImage");
    }
}
