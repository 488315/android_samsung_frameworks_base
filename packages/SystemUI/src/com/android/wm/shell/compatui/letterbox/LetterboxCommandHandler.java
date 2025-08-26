package com.android.wm.shell.compatui.letterbox;

import android.content.Context;
import android.graphics.Color;
import com.android.systemui.broadcast.ActionReceiver$$ExternalSyntheticOutline0;
import com.android.systemui.qs.tiles.base.ui.viewmodel.QSTileViewModelAdapter$$ExternalSyntheticOutline0;
import com.android.wm.shell.sysui.ShellCommandHandler;
import com.android.wm.shell.sysui.ShellInit;
import java.io.PrintWriter;
import kotlin.Unit;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.text.StringsKt__IndentKt;

/* loaded from: classes3.dex */
public final class LetterboxCommandHandler implements ShellCommandHandler.ShellCommandActionHandler {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final Context context;
    public final LetterboxConfiguration letterboxConfiguration;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public LetterboxCommandHandler(Context context, ShellInit shellInit, ShellCommandHandler shellCommandHandler, LetterboxConfiguration letterboxConfiguration) {
        this.context = context;
        this.letterboxConfiguration = letterboxConfiguration;
    }

    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue
    java.lang.NullPointerException: Cannot invoke "java.util.List.iterator()" because the return value of "jadx.core.dex.visitors.regions.SwitchOverStringVisitor$SwitchData.getNewCases()" is null
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.restoreSwitchOverString(SwitchOverStringVisitor.java:109)
    	at jadx.core.dex.visitors.regions.SwitchOverStringVisitor.visitRegion(SwitchOverStringVisitor.java:66)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:77)
    	at jadx.core.dex.visitors.regions.DepthRegionTraversal.traverseIterativeStepInternal(DepthRegionTraversal.java:82)
     */
    @Override // com.android.wm.shell.sysui.ShellCommandHandler.ShellCommandActionHandler
    public final boolean onShellCommand(PrintWriter printWriter, String[] strArr) throws NumberFormatException {
        if (strArr == null || printWriter == null) {
            printWriter.getClass();
            printWriter.println("Missing arguments.");
            return false;
        }
        int length = strArr.length;
        LetterboxConfiguration letterboxConfiguration = this.letterboxConfiguration;
        Integer numValueOf = null;
        if (length == 1) {
            String str = strArr[0];
            switch (str.hashCode()) {
                case 583595847:
                    if (str.equals("cornerRadius")) {
                        printWriter.println("    Rounded corners radius: " + Math.max(letterboxConfiguration.letterboxActivityCornersRadius, 0) + " px.");
                        return true;
                    }
                    break;
                case 1174221690:
                    if (str.equals("backgroundColorReset")) {
                        letterboxConfiguration.letterboxBackgroundColorOverride = null;
                        letterboxConfiguration.letterboxBackgroundColorResourceIdOverride = null;
                        return true;
                    }
                    break;
                case 1287124693:
                    if (str.equals("backgroundColor")) {
                        ActionReceiver$$ExternalSyntheticOutline0.m(printWriter, "    Background color: ", Integer.toHexString(letterboxConfiguration.getLetterboxBackgroundColor().toArgb()));
                        return true;
                    }
                    break;
                case 1427417672:
                    if (str.equals("cornerRadiusReset")) {
                        letterboxConfiguration.letterboxActivityCornersRadius = letterboxConfiguration.letterboxActivityDefaultCornersRadius;
                        return true;
                    }
                    break;
            }
            printWriter.println("Invalid command: ".concat(str));
            return false;
        }
        if (length != 2) {
            ActionReceiver$$ExternalSyntheticOutline0.m(printWriter, "Invalid command: ", strArr[0]);
            return false;
        }
        String str2 = strArr[0];
        String str3 = strArr[1];
        int iHashCode = str2.hashCode();
        if (iHashCode != -1260881405) {
            if (iHashCode != 583595847) {
                if (iHashCode == 1287124693 && str2.equals("backgroundColor")) {
                    Object objMo781invoke = new LetterboxCommandHandler$onSingleParamCommand$1(this).mo781invoke(str3);
                    if (objMo781invoke == null) {
                        QSTileViewModelAdapter$$ExternalSyntheticOutline0.m(printWriter, str3, " is not a valid color.");
                        return false;
                    }
                    letterboxConfiguration.letterboxBackgroundColorOverride = (Color) objMo781invoke;
                    Unit unit = Unit.INSTANCE;
                    return true;
                }
            } else if (str2.equals("cornerRadius")) {
                try {
                    int i = Integer.parseInt(str3);
                    if (i >= 0) {
                        numValueOf = Integer.valueOf(i);
                    }
                } catch (IllegalArgumentException unused) {
                }
                if (numValueOf == null) {
                    QSTileViewModelAdapter$$ExternalSyntheticOutline0.m(printWriter, str3, " is not a valid radius. It must be an integer >= 0.");
                    return false;
                }
                letterboxConfiguration.letterboxActivityCornersRadius = numValueOf.intValue();
                Unit unit2 = Unit.INSTANCE;
                return true;
            }
        } else if (str2.equals("backgroundColorResource")) {
            Object objMo781invoke2 = new LetterboxCommandHandler$onSingleParamCommand$4(this).mo781invoke(str3);
            if (objMo781invoke2 == null) {
                QSTileViewModelAdapter$$ExternalSyntheticOutline0.m(printWriter, str3, " is not a valid resource. Color in '@android:color/resource_name' format should be provided as an argument.");
                return false;
            }
            letterboxConfiguration.letterboxBackgroundColorResourceIdOverride = (Integer) objMo781invoke2;
            Unit unit3 = Unit.INSTANCE;
            return true;
        }
        ActionReceiver$$ExternalSyntheticOutline0.m(printWriter, "Invalid command: ", str3);
        return false;
    }

    @Override // com.android.wm.shell.sysui.ShellCommandHandler.ShellCommandActionHandler
    public final void printShellCommandHelp(PrintWriter printWriter, String str) {
        if (printWriter != null) {
            printWriter.println(StringsKt__IndentKt.trimIndent("\n                         backgroundColor color\"\n                              Color of letterbox which is to be used when letterbox background\n                              type is 'solid-color'. See Color#parseColor for allowed color\n                              formats (#RRGGBB and some colors by name, e.g. magenta or olive).\n                         backgroundColorResource resource_name\"\n                              Color resource name of letterbox background which is used when\n                              background type is 'solid-color'. Parameter is a color resource\n                              name, for example, @android:color/system_accent2_50.\n                         backgroundColorReset\"\n                              Resets the background color to the default value.\"\n                         cornerRadius\"\n                              Corners radius (in pixels) for activities in the letterbox mode.\"\n                              If cornerRadius < 0, it will be ignored and corners of the\"\n                              activity won't be rounded.\"\n                         cornerRadiusReset\"\n                              Resets the rounded corners radius to the default value.\"\n                "));
        }
    }
}
