precision mediump float;

// The actual wallpaper texture.
uniform sampler2D uTexture;

varying vec2 vTextureCoordinates;
// { LsRune.WALLPAPER_NIGHT_MODE_FILTER
uniform float uNightFilter;
uniform vec3 uFilterColor;
// } LsRune.WALLPAPER_NIGHT_MODE_FILTER
void main() {

    // GED code - Adjust wallpaper position based on the offsets.
    // Original Code :
    // gets the pixel value of the wallpaper for this uv coordinates on screen.
    // gl_FragColor = texture2D(uTexture, vTextureCoordinates);
    //
    // Revised Code :
    // { LsRune.WALLPAPER_NIGHT_MODE_FILTER
    vec4 fragColor = texture2D(uTexture, vTextureCoordinates);
    vec4 filterColor = vec4(uFilterColor.x,uFilterColor.y,uFilterColor.z ,1.0);
    vec4 textureColor = vec4(fragColor.r, fragColor.g, fragColor.b, 1.);
    gl_FragColor = textureColor * (1.0 - uNightFilter) + filterColor * uNightFilter;
    // } LsRune.WALLPAPER_NIGHT_MODE_FILTER
}
