uniform mat4 uMvpMatrix;

attribute vec4 aPosition;
attribute vec2 aTextureCoordOrig;

varying vec2 vTextureCoordOrig;

void main()
{
    gl_Position = uMvpMatrix * aPosition;
    vTextureCoordOrig = aTextureCoordOrig;
}
