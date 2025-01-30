package com.android.systemui.statusbar.phone;

import android.view.InsetsFlags;
import android.view.ViewDebug;
import com.android.internal.statusbar.LetterboxDetails;
import com.android.internal.view.AppearanceRegion;
import com.android.keyguard.logging.AbstractC0866xb1ce8deb;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import java.io.PrintWriter;
import java.util.Arrays;
import kotlin.text.StringsKt__IndentKt;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class LetterboxAppearanceCalculator implements Dumpable {
    public Integer lastAppearance;
    public AppearanceRegion[] lastAppearanceRegions;
    public LetterboxAppearance lastLetterboxAppearance;
    public LetterboxDetails[] lastLetterboxes;
    public final LetterboxBackgroundProvider letterboxBackgroundProvider;
    public final LightBarController lightBarController;
    public StatusBarBoundsProvider statusBarBoundsProvider;

    public LetterboxAppearanceCalculator(LightBarController lightBarController, DumpManager dumpManager, LetterboxBackgroundProvider letterboxBackgroundProvider) {
        this.lightBarController = lightBarController;
        this.letterboxBackgroundProvider = letterboxBackgroundProvider;
        dumpManager.getClass();
        dumpManager.registerCriticalDumpable("LetterboxAppearanceCalculator", this);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        Integer num = this.lastAppearance;
        String flagsToString = num != null ? ViewDebug.flagsToString(InsetsFlags.class, "appearance", num.intValue()) : null;
        String arrays = Arrays.toString(this.lastAppearanceRegions);
        String arrays2 = Arrays.toString(this.lastLetterboxes);
        LetterboxAppearance letterboxAppearance = this.lastLetterboxAppearance;
        StringBuilder m87m = AbstractC0866xb1ce8deb.m87m("\n           lastAppearance: ", flagsToString, "\n           lastAppearanceRegion: ", arrays, ",\n           lastLetterboxes: ");
        m87m.append(arrays2);
        m87m.append(",\n           lastLetterboxAppearance: ");
        m87m.append(letterboxAppearance);
        m87m.append("\n       ");
        printWriter.println(StringsKt__IndentKt.trimIndent(m87m.toString()));
    }
}
