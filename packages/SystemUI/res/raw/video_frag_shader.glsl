#extension GL_OES_EGL_image_external : require

precision mediump float;
varying vec2 vTextureCoord;
uniform samplerExternalOES sTexture;
uniform float uGlobalAlpha;
uniform float uContrast;

uniform float uNightFilter;
uniform vec3 uRgbFilterColor;
uniform vec3 uHsvFilterColor;

varying vec2 vAlphaCoord;
varying float vTransparency;

vec3 rgb2hsv(vec3 c) {
    vec4 K = vec4(0.0, -1.0 / 3.0, 2.0 / 3.0, -1.0);
    vec4 p = mix(vec4(c.bg, K.wz), vec4(c.gb, K.xy), step(c.b, c.g));
    vec4 q = mix(vec4(p.xyw, c.r), vec4(c.r, p.yzx), step(p.x, c.r));

    float d = q.x - min(q.w, q.y);
    float e = 1.0e-10;
    return vec3(abs(q.z + (q.w - q.y) / (6.0 * d + e)), d / (q.x + e), q.x);
}

vec3 hsv2rgb(vec3 c) {
    vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
    vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);
    return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);
}

void main() {
    vec4 fragColor = texture2D(sTexture, vTextureCoord);

    vec3 hsvColor = rgb2hsv(fragColor.rgb);
    hsvColor.x += uHsvFilterColor.x;
    hsvColor.yz *= uHsvFilterColor.yz;
    fragColor = vec4(hsv2rgb(hsvColor), fragColor.a);

    vec4 filterColor = vec4(uRgbFilterColor.x, uRgbFilterColor.y, uRgbFilterColor.z, 1.0);
    fragColor = (fragColor * (1.0 - uNightFilter) + filterColor * uNightFilter) * uGlobalAlpha;
    if(vTransparency == 1.0) {
        vec4 alpha = texture2D(sTexture, vAlphaCoord);
        fragColor.a = alpha.g;
    }
    gl_FragColor = fragColor;
}