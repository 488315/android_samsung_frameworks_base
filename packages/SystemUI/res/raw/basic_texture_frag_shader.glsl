precision mediump float;
uniform mediump sampler2D uTextureOrig;
uniform mediump float uGlobalAlpha;

varying mediump vec2 vTextureCoordOrig;

void main()
{
    mediump vec4 baseColor = texture2D(uTextureOrig, vTextureCoordOrig);
    gl_FragColor = baseColor * uGlobalAlpha;
}