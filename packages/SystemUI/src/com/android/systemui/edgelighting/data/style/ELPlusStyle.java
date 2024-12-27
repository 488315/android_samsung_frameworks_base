package com.android.systemui.edgelighting.data.style;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import com.android.systemui.edgelighting.interfaces.IEdgeLightingStyle;
import java.util.HashMap;

public final class ELPlusStyle implements IEdgeLightingStyle {
    public final String mDBName;
    public final String mEffectName;
    public final Drawable mIcon;
    public final Uri mSpecialEffect;
    public final HashMap mSupportMap = new HashMap();

    public ELPlusStyle(Context context, String str, Drawable drawable, Uri uri, Uri uri2, String str2, String str3) {
        String[] split;
        this.mEffectName = str;
        this.mIcon = drawable;
        this.mSpecialEffect = uri;
        String[] split2 = (str2 != null) & (str2.isEmpty() ^ true) ? str2.split("!") : null;
        if (split2 != null) {
            for (String str4 : split2) {
                if (str4.contains("centerPosition")) {
                    str4.endsWith("true");
                } else if (str4.contains("edgeSpecialEffect")) {
                    str4.endsWith("true");
                } else if (str4.contains("edgeFrameEffect")) {
                    str4.endsWith("true");
                } else if (str4.contains("repeatCount")) {
                    String[] split3 = str4.split(":");
                    if (split3 != null) {
                        Integer.parseInt(split3[1]);
                    }
                } else if (str4.contains("specialSize")) {
                    String[] split4 = str4.split(":");
                    if (split4 != null && (split = split4[1].split("x")) != null) {
                        Integer.parseInt(split[0]);
                        Integer.parseInt(split[1]);
                    }
                } else if (str4.contains("startAfterToastFinished")) {
                    str4.endsWith("true");
                }
            }
        }
        this.mSupportMap.put(EdgeLightingStyleOption.EFFECT, Boolean.valueOf(str3.contains("EFFECT")));
        this.mSupportMap.put(EdgeLightingStyleOption.COLOR, Boolean.valueOf(str3.contains("COLOR")));
        this.mSupportMap.put(EdgeLightingStyleOption.WIDTH, Boolean.valueOf(str3.contains("WIDTH")));
        this.mSupportMap.put(EdgeLightingStyleOption.TRANSPARENCY, Boolean.valueOf(str3.contains("TRANSPARENCY")));
        this.mSupportMap.put(EdgeLightingStyleOption.DURATION, Boolean.valueOf(str3.contains("DURATION")));
        this.mDBName = "el+oneui3.0/" + str;
    }

    @Override // com.android.systemui.edgelighting.interfaces.IEdgeLightingStyle
    public final String getKey() {
        return this.mDBName;
    }

    @Override // com.android.systemui.edgelighting.interfaces.IEdgeLightingStyle
    public final Drawable getRoundedIcon(Context context) {
        return this.mIcon;
    }

    @Override // com.android.systemui.edgelighting.interfaces.IEdgeLightingStyle
    public final CharSequence getTitle(Context context) {
        return this.mEffectName;
    }

    @Override // com.android.systemui.edgelighting.interfaces.IEdgeLightingStyle
    public final boolean isSupportEffect() {
        return true;
    }

    @Override // com.android.systemui.edgelighting.interfaces.IEdgeLightingStyle
    public final boolean isSupportOption(EdgeLightingStyleOption edgeLightingStyleOption) {
        return ((Boolean) this.mSupportMap.get(edgeLightingStyleOption)).booleanValue();
    }
}
