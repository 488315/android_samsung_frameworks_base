package com.android.server.graphics.fonts;

import android.graphics.fonts.Font;
import android.graphics.fonts.FontFamily;
import android.graphics.fonts.FontUpdateRequest;
import android.graphics.fonts.FontVariationAxis;
import android.graphics.fonts.SystemFonts;
import android.os.Binder;
import android.os.Build;
import android.os.ParcelFileDescriptor;
import android.os.ShellCommand;
import android.text.FontConfig;
import android.util.IndentingPrintWriter;
import android.util.Slog;
import android.util.Xml;

import com.android.internal.util.DumpUtils;
import com.android.modules.utils.TypedXmlPullParser;

import com.samsung.android.knoxguard.service.utils.Constants;

import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class FontManagerShellCommand extends ShellCommand {
    public final FontManagerService mService;

    public FontManagerShellCommand(FontManagerService fontManagerService) {
        this.mService = fontManagerService;
    }

    public static void dumpFont(IndentingPrintWriter indentingPrintWriter, Font font) {
        File file = font.getFile();
        StringBuilder sb = new StringBuilder();
        sb.append(font.getStyle());
        sb.append(", path = ");
        sb.append(file == null ? "[Not a file]" : file.getAbsolutePath());
        if (font.getTtcIndex() != 0) {
            sb.append(", index = ");
            sb.append(font.getTtcIndex());
        }
        FontVariationAxis[] axes = font.getAxes();
        if (axes != null && axes.length != 0) {
            sb.append(", axes = \"");
            sb.append(FontVariationAxis.toFontVariationSettings(axes));
            sb.append("\"");
        }
        indentingPrintWriter.println(sb.toString());
    }

    public static void dumpFontConfig(
            IndentingPrintWriter indentingPrintWriter, FontConfig fontConfig) {
        List fontFamilies = fontConfig.getFontFamilies();
        indentingPrintWriter.println("Named Family List");
        indentingPrintWriter.increaseIndent();
        List namedFamilyLists = fontConfig.getNamedFamilyLists();
        for (int i = 0; i < namedFamilyLists.size(); i++) {
            FontConfig.NamedFamilyList namedFamilyList =
                    (FontConfig.NamedFamilyList) namedFamilyLists.get(i);
            indentingPrintWriter.println("Named Family (" + namedFamilyList.getName() + ")");
            indentingPrintWriter.increaseIndent();
            List families = namedFamilyList.getFamilies();
            for (int i2 = 0; i2 < families.size(); i2++) {
                FontConfig.FontFamily fontFamily = (FontConfig.FontFamily) families.get(i2);
                indentingPrintWriter.println("Family");
                List fontList = fontFamily.getFontList();
                indentingPrintWriter.increaseIndent();
                for (int i3 = 0; i3 < fontList.size(); i3++) {
                    dumpSingleFontConfig(indentingPrintWriter, (FontConfig.Font) fontList.get(i3));
                }
                indentingPrintWriter.decreaseIndent();
            }
            indentingPrintWriter.decreaseIndent();
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("Dump Fallback Families");
        indentingPrintWriter.increaseIndent();
        int i4 = 0;
        for (int i5 = 0; i5 < fontFamilies.size(); i5++) {
            FontConfig.FontFamily fontFamily2 = (FontConfig.FontFamily) fontFamilies.get(i5);
            if (fontFamily2.getName() == null) {
                StringBuilder sb = new StringBuilder("Fallback Family [");
                int i6 = i4 + 1;
                sb.append(i4);
                sb.append("]: lang=\"");
                sb.append(fontFamily2.getLocaleList().toLanguageTags());
                sb.append("\"");
                if (fontFamily2.getVariant() != 0) {
                    sb.append(", variant=");
                    int variant = fontFamily2.getVariant();
                    if (variant == 1) {
                        sb.append("Compact");
                    } else if (variant != 2) {
                        sb.append("Unknown");
                    } else {
                        sb.append("Elegant");
                    }
                }
                indentingPrintWriter.println(sb.toString());
                List fontList2 = fontFamily2.getFontList();
                indentingPrintWriter.increaseIndent();
                for (int i7 = 0; i7 < fontList2.size(); i7++) {
                    dumpSingleFontConfig(indentingPrintWriter, (FontConfig.Font) fontList2.get(i7));
                }
                indentingPrintWriter.decreaseIndent();
                i4 = i6;
            }
        }
        indentingPrintWriter.decreaseIndent();
        indentingPrintWriter.println("Dump Family Aliases");
        indentingPrintWriter.increaseIndent();
        List aliases = fontConfig.getAliases();
        for (int i8 = 0; i8 < aliases.size(); i8++) {
            FontConfig.Alias alias = (FontConfig.Alias) aliases.get(i8);
            indentingPrintWriter.println(
                    "alias = "
                            + alias.getName()
                            + ", reference = "
                            + alias.getOriginal()
                            + ", width = "
                            + alias.getWeight());
        }
        indentingPrintWriter.decreaseIndent();
    }

    public static void dumpSingleFontConfig(
            IndentingPrintWriter indentingPrintWriter, FontConfig.Font font) {
        StringBuilder sb = new StringBuilder("style = ");
        sb.append(font.getStyle());
        sb.append(", path = ");
        sb.append(font.getFile().getAbsolutePath());
        if (font.getTtcIndex() != 0) {
            sb.append(", index = ");
            sb.append(font.getTtcIndex());
        }
        if (!font.getFontVariationSettings().isEmpty()) {
            sb.append(", axes = ");
            sb.append(font.getFontVariationSettings());
        }
        if (font.getFontFamilyName() != null) {
            sb.append(", fallback = ");
            sb.append(font.getFontFamilyName());
        }
        indentingPrintWriter.println(sb.toString());
        if (font.getOriginalFile() != null) {
            indentingPrintWriter.increaseIndent();
            indentingPrintWriter.println("Font is updated from " + font.getOriginalFile());
            indentingPrintWriter.decreaseIndent();
        }
    }

    public static List parseFontFamilyUpdateXml(InputStream inputStream) {
        try {
            TypedXmlPullParser resolvePullParser = Xml.resolvePullParser(inputStream);
            ArrayList arrayList = new ArrayList();
            while (true) {
                int next = resolvePullParser.next();
                if (next == 1) {
                    return arrayList;
                }
                if (next == 2) {
                    int depth = resolvePullParser.getDepth();
                    String name = resolvePullParser.getName();
                    if (depth == 1) {
                        if (!"fontFamilyUpdateRequest".equals(name)) {
                            throw new FontManagerService.SystemFontException(
                                    -10007, "Expected <fontFamilyUpdateRequest> but got: " + name);
                        }
                    } else if (depth != 2) {
                        continue;
                    } else {
                        if (!"family".equals(name)) {
                            throw new FontManagerService.SystemFontException(
                                    -10007, "Expected <family> but got: " + name);
                        }
                        arrayList.add(
                                new FontUpdateRequest(
                                        FontUpdateRequest.Family.readFromXml(resolvePullParser)));
                    }
                }
            }
        } catch (IOException | XmlPullParserException e) {
            throw new FontManagerService.SystemFontException(0, "Failed to parse xml", e);
        }
    }

    public final int dump(ShellCommand shellCommand) {
        if (!DumpUtils.checkDumpPermission(
                this.mService.mContext,
                "FontManagerShellCommand",
                shellCommand.getErrPrintWriter())) {
            return 1;
        }
        IndentingPrintWriter indentingPrintWriter =
                new IndentingPrintWriter(shellCommand.getOutPrintWriter(), "  ");
        String nextArg = shellCommand.getNextArg();
        FontConfig systemFontConfig = this.mService.getSystemFontConfig();
        if (nextArg == null) {
            dumpFontConfig(indentingPrintWriter, systemFontConfig);
        } else {
            FontFamily[] fontFamilyArr =
                    (FontFamily[]) SystemFonts.buildSystemFallback(systemFontConfig).get(nextArg);
            if (fontFamilyArr == null) {
                indentingPrintWriter.println("Font Family \"" + nextArg + "\" not found");
            } else {
                for (FontFamily fontFamily : fontFamilyArr) {
                    StringBuilder sb = new StringBuilder("Family:");
                    if (fontFamily.getLangTags() != null) {
                        sb.append(" langTag = ");
                        sb.append(fontFamily.getLangTags());
                    }
                    if (fontFamily.getVariant() != 0) {
                        sb.append(" variant = ");
                        int variant = fontFamily.getVariant();
                        if (variant == 1) {
                            sb.append("Compact");
                        } else if (variant != 2) {
                            sb.append("UNKNOWN");
                        } else {
                            sb.append("Elegant");
                        }
                    }
                    indentingPrintWriter.println(sb.toString());
                    for (int i = 0; i < fontFamily.getSize(); i++) {
                        indentingPrintWriter.increaseIndent();
                        try {
                            dumpFont(indentingPrintWriter, fontFamily.getFont(i));
                            indentingPrintWriter.decreaseIndent();
                        } catch (Throwable th) {
                            indentingPrintWriter.decreaseIndent();
                            throw th;
                        }
                    }
                }
            }
        }
        return 0;
    }

    public final void installCert(ShellCommand shellCommand) {
        if (!Build.IS_DEBUGGABLE) {
            throw new SecurityException("Only debuggable device can add debug certificate");
        }
        if (Binder.getCallingUid() != 0) {
            throw new SecurityException("Only root can add debug certificate");
        }
        String nextArg = shellCommand.getNextArg();
        if (nextArg == null) {
            throw new FontManagerService.SystemFontException(
                    -10008, "Cert file path argument is required.");
        }
        File file = new File(nextArg);
        if (!file.isFile()) {
            throw new FontManagerService.SystemFontException(
                    -10008, "Cert file (" + file + ") is not found");
        }
        FontManagerService fontManagerService = this.mService;
        fontManagerService.mDebugCertFilePath = nextArg;
        fontManagerService.initialize();
        shellCommand.getOutPrintWriter().println("Success");
    }

    public final int onCommand(String str) {
        char c;
        int callingUid = Binder.getCallingUid();
        if (callingUid != 0 && callingUid != 2000) {
            getErrPrintWriter().println("Only shell or root user can execute font command.");
            return 1;
        }
        if (str == null) {
            return handleDefaultCommands((String) null);
        }
        try {
            switch (str.hashCode()) {
                case -2084349744:
                    if (str.equals("install-debug-cert")) {
                        c = 6;
                        break;
                    }
                    c = 65535;
                    break;
                case -892481550:
                    if (str.equals(Constants.JSON_CLIENT_DATA_STATUS)) {
                        c = 5;
                        break;
                    }
                    c = 65535;
                    break;
                case -838846263:
                    if (str.equals("update")) {
                        c = 1;
                        break;
                    }
                    c = 65535;
                    break;
                case 3095028:
                    if (str.equals("dump")) {
                        c = 0;
                        break;
                    }
                    c = 65535;
                    break;
                case 94746189:
                    if (str.equals("clear")) {
                        c = 3;
                        break;
                    }
                    c = 65535;
                    break;
                case 1097506319:
                    if (str.equals("restart")) {
                        c = 4;
                        break;
                    }
                    c = 65535;
                    break;
                case 1135462632:
                    if (str.equals("update-family")) {
                        c = 2;
                        break;
                    }
                    c = 65535;
                    break;
                default:
                    c = 65535;
                    break;
            }
            switch (c) {
                case 0:
                    return dump(this);
                case 1:
                    update(this);
                    break;
                case 2:
                    updateFamily(this);
                    break;
                case 3:
                    FontManagerService fontManagerService = this.mService;
                    fontManagerService.getClass();
                    UpdatableFontDir.deleteAllFiles(
                            new File("/data/fonts/files"),
                            new File("/data/fonts/config/config.xml"));
                    fontManagerService.initialize();
                    getOutPrintWriter().println("Success");
                    break;
                case 4:
                    this.mService.initialize();
                    getOutPrintWriter().println("Success");
                    break;
                case 5:
                    status(this);
                    break;
                case 6:
                    installCert(this);
                    break;
                default:
                    return handleDefaultCommands(str);
            }
            return 0;
        } catch (FontManagerService.SystemFontException e) {
            PrintWriter errPrintWriter = getErrPrintWriter();
            errPrintWriter.println(e.getErrorCode());
            errPrintWriter.println(e.getMessage());
            Slog.e(
                    "FontManagerShellCommand",
                    "Command failed: " + Arrays.toString(getAllArgs()),
                    e);
            return 1;
        }
    }

    public final void onHelp() {
        PrintWriter outPrintWriter = getOutPrintWriter();
        outPrintWriter.println("Font service (font) commands");
        outPrintWriter.println("help");
        outPrintWriter.println("    Print this help text.");
        outPrintWriter.println();
        outPrintWriter.println("dump [family name]");
        outPrintWriter.println("    Dump all font files in the specified family name.");
        outPrintWriter.println(
                "    Dump current system font configuration if no family name was specified.");
        outPrintWriter.println();
        outPrintWriter.println("update [font file path] [signature file path]");
        outPrintWriter.println("    Update installed font files with new font file.");
        outPrintWriter.println();
        outPrintWriter.println("update-family [family definition XML path]");
        outPrintWriter.println("    Update font families with the new definitions.");
        outPrintWriter.println();
        outPrintWriter.println("install-debug-cert [cert file path]");
        outPrintWriter.println(
                "    Install debug certificate file. This command can be used only on");
        outPrintWriter.println("    debuggable device with root user.");
        outPrintWriter.println();
        outPrintWriter.println("clear");
        outPrintWriter.println(
                "    Remove all installed font files and reset to the initial state.");
        outPrintWriter.println();
        outPrintWriter.println("restart");
        outPrintWriter.println("    Restart FontManagerService emulating device reboot.");
        outPrintWriter.println(
                "    WARNING: this is not a safe operation. Other processes may misbehave if");
        outPrintWriter.println("    they are using fonts updated by FontManagerService.");
        outPrintWriter.println("    This command exists merely for testing.");
        outPrintWriter.println();
        outPrintWriter.println(Constants.JSON_CLIENT_DATA_STATUS);
        outPrintWriter.println("    Prints status of current system font configuration.");
    }

    public final void status(ShellCommand shellCommand) {
        Map emptyMap;
        IndentingPrintWriter indentingPrintWriter =
                new IndentingPrintWriter(shellCommand.getOutPrintWriter(), "  ");
        FontConfig systemFontConfig = this.mService.getSystemFontConfig();
        indentingPrintWriter.println("Current Version: " + systemFontConfig.getConfigVersion());
        indentingPrintWriter.println(
                "Last Modified Date: "
                        + LocalDateTime.ofEpochSecond(
                                        systemFontConfig.getLastModifiedTimeMillis(),
                                        0,
                                        ZoneOffset.UTC)
                                .format(DateTimeFormatter.ISO_DATE_TIME));
        FontManagerService fontManagerService = this.mService;
        synchronized (fontManagerService.mUpdatableFontDirLock) {
            try {
                UpdatableFontDir updatableFontDir = fontManagerService.mUpdatableFontDir;
                emptyMap =
                        updatableFontDir == null
                                ? Collections.emptyMap()
                                : updatableFontDir.getPostScriptMap();
            } finally {
            }
        }
        indentingPrintWriter.println("Number of updated font files: " + emptyMap.size());
    }

    public final void update(ShellCommand shellCommand) {
        ParcelFileDescriptor openFileForSystem;
        String nextArg = shellCommand.getNextArg();
        if (nextArg == null) {
            throw new FontManagerService.SystemFontException(
                    -10003, "Font file path argument is required.");
        }
        String nextArg2 = shellCommand.getNextArg();
        if (nextArg2 == null) {
            throw new FontManagerService.SystemFontException(
                    -10003, "Signature file argument is required.");
        }
        try {
            openFileForSystem = shellCommand.openFileForSystem(nextArg, "r");
        } catch (IOException e) {
            Slog.w("FontManagerShellCommand", "Error while closing files", e);
        }
        try {
            ParcelFileDescriptor openFileForSystem2 = shellCommand.openFileForSystem(nextArg2, "r");
            try {
                if (openFileForSystem == null) {
                    throw new FontManagerService.SystemFontException(
                            -10001, "Failed to open font file");
                }
                if (openFileForSystem2 == null) {
                    throw new FontManagerService.SystemFontException(
                            -10002, "Failed to open signature file");
                }
                try {
                    FileInputStream fileInputStream =
                            new FileInputStream(openFileForSystem2.getFileDescriptor());
                    try {
                        int available = fileInputStream.available();
                        if (available > 8192) {
                            throw new FontManagerService.SystemFontException(
                                    -10005, "Signature file is too large");
                        }
                        byte[] bArr = new byte[available];
                        if (fileInputStream.read(bArr, 0, available) != available) {
                            throw new FontManagerService.SystemFontException(
                                    -10004, "Invalid read length");
                        }
                        fileInputStream.close();
                        this.mService.update(
                                -1,
                                Collections.singletonList(
                                        new FontUpdateRequest(openFileForSystem, bArr)));
                        openFileForSystem2.close();
                        openFileForSystem.close();
                        shellCommand.getOutPrintWriter().println("Success");
                    } catch (Throwable th) {
                        try {
                            fileInputStream.close();
                        } catch (Throwable th2) {
                            th.addSuppressed(th2);
                        }
                        throw th;
                    }
                } catch (IOException e2) {
                    throw new FontManagerService.SystemFontException(
                            -10004, "Failed to read signature file.", e2);
                }
            } finally {
            }
        } finally {
        }
    }

    public final void updateFamily(ShellCommand shellCommand) {
        String nextArg = shellCommand.getNextArg();
        if (nextArg == null) {
            throw new FontManagerService.SystemFontException(
                    -10003, "XML file path argument is required.");
        }
        try {
            ParcelFileDescriptor openFileForSystem = shellCommand.openFileForSystem(nextArg, "r");
            try {
                List parseFontFamilyUpdateXml =
                        parseFontFamilyUpdateXml(
                                new FileInputStream(openFileForSystem.getFileDescriptor()));
                openFileForSystem.close();
                this.mService.update(-1, parseFontFamilyUpdateXml);
                shellCommand.getOutPrintWriter().println("Success");
            } finally {
            }
        } catch (IOException e) {
            throw new FontManagerService.SystemFontException(-10006, "Failed to open XML file.", e);
        }
    }
}
