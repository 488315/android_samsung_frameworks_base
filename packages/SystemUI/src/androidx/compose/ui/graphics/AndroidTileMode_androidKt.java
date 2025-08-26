package androidx.compose.ui.graphics;

import android.graphics.Shader;
import androidx.compose.ui.graphics.TileMode;

/* loaded from: classes.dex */
public abstract class AndroidTileMode_androidKt {
    /* renamed from: toAndroidTileMode-0vamqd0, reason: not valid java name */
    public static final Shader.TileMode m449toAndroidTileMode0vamqd0(int i) {
        TileMode.Companion companion = TileMode.Companion;
        companion.getClass();
        if (i == 0) {
            return Shader.TileMode.CLAMP;
        }
        companion.getClass();
        if (i == TileMode.Repeated) {
            return Shader.TileMode.REPEAT;
        }
        companion.getClass();
        if (i == TileMode.Mirror) {
            return Shader.TileMode.MIRROR;
        }
        companion.getClass();
        if (i != TileMode.Decal) {
            return Shader.TileMode.CLAMP;
        }
        TileModeVerificationHelper.INSTANCE.getClass();
        return Shader.TileMode.DECAL;
    }
}
