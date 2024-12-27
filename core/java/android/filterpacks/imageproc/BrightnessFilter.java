package android.filterpacks.imageproc;

import android.filterfw.core.FilterContext;
import android.filterfw.core.NativeProgram;
import android.filterfw.core.Program;
import android.filterfw.core.ShaderProgram;

import com.samsung.android.biometrics.SemBiometricConstants;

public class BrightnessFilter extends SimpleImageFilter {
    private static final String mBrightnessShader =
            "precision mediump float;\n"
                    + "uniform sampler2D tex_sampler_0;\n"
                    + "uniform float brightness;\n"
                    + "varying vec2 v_texcoord;\n"
                    + "void main() {\n"
                    + "  vec4 color = texture2D(tex_sampler_0, v_texcoord);\n"
                    + "  gl_FragColor = brightness * color;\n"
                    + "}\n";

    public BrightnessFilter(String name) {
        super(name, SemBiometricConstants.KEY_INDISPLAY_SENSOR_OPTICAL_BRIGHTNESS);
    }

    @Override // android.filterpacks.imageproc.SimpleImageFilter
    protected Program getNativeProgram(FilterContext context) {
        return new NativeProgram(
                "filterpack_imageproc",
                SemBiometricConstants.KEY_INDISPLAY_SENSOR_OPTICAL_BRIGHTNESS);
    }

    @Override // android.filterpacks.imageproc.SimpleImageFilter
    protected Program getShaderProgram(FilterContext context) {
        return new ShaderProgram(context, mBrightnessShader);
    }
}
