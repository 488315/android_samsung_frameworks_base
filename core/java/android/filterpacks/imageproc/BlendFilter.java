package android.filterpacks.imageproc;

import android.filterfw.core.FilterContext;
import android.filterfw.core.Program;
import android.filterfw.core.ShaderProgram;

/* loaded from: classes.dex */
public class BlendFilter extends ImageCombineFilter {
    private final String mBlendShader;

    public BlendFilter(String name) {
        super(name, new String[] {"left", "right"}, "blended", "blend");
        this.mBlendShader =
                "precision mediump float;\n"
                        + "uniform sampler2D tex_sampler_0;\n"
                        + "uniform sampler2D tex_sampler_1;\n"
                        + "uniform float blend;\n"
                        + "varying vec2 v_texcoord;\n"
                        + "void main() {\n"
                        + "  vec4 colorL = texture2D(tex_sampler_0, v_texcoord);\n"
                        + "  vec4 colorR = texture2D(tex_sampler_1, v_texcoord);\n"
                        + "  float weight = colorR.a * blend;\n"
                        + "  gl_FragColor = mix(colorL, colorR, weight);\n"
                        + "}\n";
    }

    @Override // android.filterpacks.imageproc.ImageCombineFilter
    protected Program getNativeProgram(FilterContext context) {
        throw new RuntimeException("TODO: Write native implementation for Blend!");
    }

    @Override // android.filterpacks.imageproc.ImageCombineFilter
    protected Program getShaderProgram(FilterContext context) {
        return new ShaderProgram(
                context,
                "precision mediump float;\n"
                        + "uniform sampler2D tex_sampler_0;\n"
                        + "uniform sampler2D tex_sampler_1;\n"
                        + "uniform float blend;\n"
                        + "varying vec2 v_texcoord;\n"
                        + "void main() {\n"
                        + "  vec4 colorL = texture2D(tex_sampler_0, v_texcoord);\n"
                        + "  vec4 colorR = texture2D(tex_sampler_1, v_texcoord);\n"
                        + "  float weight = colorR.a * blend;\n"
                        + "  gl_FragColor = mix(colorL, colorR, weight);\n"
                        + "}\n");
    }
}
