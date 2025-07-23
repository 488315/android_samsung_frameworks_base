uniform mat4 uMvpMatrix;
uniform mat4 uSTMatrix;
attribute vec4 aPosition;
attribute vec4 aTextureCoord;
varying vec2 vTextureCoord;
varying vec2 vAlphaCoord;
varying float vTransparency;
uniform float uTransparency;

void main() {
    gl_Position = uMvpMatrix * aPosition;
    if(uTransparency == 1.0) {
        vec4 texCoord = uSTMatrix * aTextureCoord;
        vTextureCoord = vec2(texCoord.x * 0.5, texCoord.y );
        vAlphaCoord = vec2(texCoord.x * 0.5 + 0.5, texCoord.y);
    } else {
        vTextureCoord = (uSTMatrix * aTextureCoord).xy;
    }
    vTransparency = uTransparency;
}