package android.view;

import android.view.ViewDebug;

/* loaded from: classes4.dex */
public class InsetsFlags {

    @ViewDebug.ExportedProperty(flagMapping = {@ViewDebug.FlagToString(equals = 1, mask = 1, name = "OPAQUE_STATUS_BARS"), @ViewDebug.FlagToString(equals = 2, mask = 2, name = "OPAQUE_NAVIGATION_BARS"), @ViewDebug.FlagToString(equals = 4, mask = 4, name = "LOW_PROFILE_BARS"), @ViewDebug.FlagToString(equals = 8, mask = 8, name = "LIGHT_STATUS_BARS"), @ViewDebug.FlagToString(equals = 16, mask = 16, name = "LIGHT_NAVIGATION_BARS"), @ViewDebug.FlagToString(equals = 32, mask = 32, name = "SEMI_TRANSPARENT_STATUS_BARS"), @ViewDebug.FlagToString(equals = 64, mask = 64, name = "SEMI_TRANSPARENT_NAVIGATION_BARS"), @ViewDebug.FlagToString(equals = 512, mask = 512, name = "FORCE_LIGHT_NAVIGATION_BARS"), @ViewDebug.FlagToString(equals = 128, mask = 128, name = "APPEARANCE_TRANSPARENT_CAPTION_BAR_BACKGROUND"), @ViewDebug.FlagToString(equals = 256, mask = 256, name = "APPEARANCE_LIGHT_CAPTION_BARS"), @ViewDebug.FlagToString(equals = 1048576, mask = 1048576, name = "LIGHT_SEMI_TRANSPARENT_NAVIGATION_BARS"), @ViewDebug.FlagToString(equals = 2097152, mask = 2097152, name = "EMBED_ACTIVITY_NAVIGATION_BARS")})
    public int appearance;

    @ViewDebug.ExportedProperty(flagMapping = {@ViewDebug.FlagToString(equals = 1, mask = 1, name = "DEFAULT"), @ViewDebug.FlagToString(equals = 2, mask = 2, name = "SHOW_TRANSIENT_BARS_BY_SWIPE")})
    public int behavior = 1;
}
