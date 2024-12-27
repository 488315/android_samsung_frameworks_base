package com.android.systemui.statusbar.phone;

import android.content.Context;
import android.view.InsetsFlags;
import android.view.ViewDebug;
import com.android.systemui.Dumpable;
import com.android.systemui.R;
import com.android.systemui.dump.DumpManager;
import java.io.PrintWriter;
import java.util.List;
import kotlin.text.StringsKt__IndentKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes3.dex */
public final class LetterboxAppearanceCalculator implements Dumpable {
    public final int darkAppearanceIconColor;
    public Integer lastAppearance;
    public List lastAppearanceRegions;
    public LetterboxAppearance lastLetterboxAppearance;
    public List lastLetterboxes;
    public final LetterboxBackgroundProvider letterboxBackgroundProvider;
    public final int lightAppearanceIconColor;

    public LetterboxAppearanceCalculator(Context context, DumpManager dumpManager, LetterboxBackgroundProvider letterboxBackgroundProvider) {
        this.letterboxBackgroundProvider = letterboxBackgroundProvider;
        this.darkAppearanceIconColor = context.getColor(R.color.light_mode_icon_color_single_tone);
        this.lightAppearanceIconColor = context.getColor(R.color.dark_mode_icon_color_single_tone);
        dumpManager.getClass();
        dumpManager.registerCriticalDumpable(LetterboxAppearanceCalculator.class.getCanonicalName(), this);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        Integer num = this.lastAppearance;
        String flagsToString = num != null ? ViewDebug.flagsToString(InsetsFlags.class, "appearance", num.intValue()) : null;
        printWriter.println(StringsKt__IndentKt.trimIndent("\n           lastAppearance: " + flagsToString + "\n           lastAppearanceRegion: " + this.lastAppearanceRegions + ",\n           lastLetterboxes: " + this.lastLetterboxes + ",\n           lastLetterboxAppearance: " + this.lastLetterboxAppearance + "\n       "));
    }
}
