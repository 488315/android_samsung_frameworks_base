#extension GL_OES_EGL_image_external : require

precision highp float;
uniform samplerExternalOES texUnit;
uniform float opacity;
uniform float vignetteAlpha;
uniform float radius;
uniform float width;
uniform float height;
varying vec2 UV;

void main() {
    vec3 rgb = texture2D(texUnit, UV).xyz;
    float r = clamp(length((UV - 0.5) * vec2(width, height))/radius, 0.0, 1.0);
    float vignetteFactor = 1.0 - vignetteAlpha*r;
    gl_FragColor = vec4(rgb * opacity * vignetteFactor, 1.0);
}