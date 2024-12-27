package com.android.server.graphics.fonts;

import android.graphics.fonts.FontFileUtil;
import android.graphics.fonts.FontUpdateRequest;
import android.os.FileUtils;
import android.os.LocaleList;
import android.system.ErrnoException;
import android.system.Os;
import android.text.FontConfig;
import android.util.ArrayMap;
import android.util.ArraySet;
import android.util.AtomicFile;
import android.util.Base64;
import android.util.Slog;
import com.android.internal.security.VerityUtils;
import com.android.internal.util.FrameworkStatsLog;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.accounts.AccountManagerService$$ExternalSyntheticOutline0;
import com.android.server.graphics.fonts.FontManagerService;
import com.android.server.graphics.fonts.PersistentSystemFontConfig;
import com.android.text.flags.Flags;
import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;
import org.xmlpull.v1.XmlPullParserException;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class UpdatableFontDir {
    public final AtomicFile mConfigFile;
    public final Function mConfigSupplier;
    public int mConfigVersion;
    public final Supplier mCurrentTimeSupplier;
    public final File mFilesDir;
    public final ArrayMap mFontFileInfoMap;
    public final FontManagerService.FsverityUtilImpl mFsverityUtil;
    public long mLastModifiedMillis;
    public final OtfFontFileParser mParser;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class FontFileInfo {
        public final File mFile;
        public final String mPsName;
        public final long mRevision;

        public FontFileInfo(File file, String str, long j) {
            this.mFile = file;
            this.mPsName = str;
            this.mRevision = j;
        }

        public final String toString() {
            return "FontFileInfo{mFile=" + this.mFile + ", psName=" + this.mPsName + ", mRevision=" + this.mRevision + '}';
        }
    }

    public UpdatableFontDir(File file, OtfFontFileParser otfFontFileParser, FontManagerService.FsverityUtilImpl fsverityUtilImpl, File file2) {
        UpdatableFontDir$$ExternalSyntheticLambda0 updatableFontDir$$ExternalSyntheticLambda0 = new UpdatableFontDir$$ExternalSyntheticLambda0();
        UpdatableFontDir$$ExternalSyntheticLambda1 updatableFontDir$$ExternalSyntheticLambda1 = new UpdatableFontDir$$ExternalSyntheticLambda1();
        this.mFontFileInfoMap = new ArrayMap();
        this.mFilesDir = file;
        this.mParser = otfFontFileParser;
        this.mFsverityUtil = fsverityUtilImpl;
        this.mConfigFile = new AtomicFile(file2);
        this.mCurrentTimeSupplier = updatableFontDir$$ExternalSyntheticLambda0;
        this.mConfigSupplier = updatableFontDir$$ExternalSyntheticLambda1;
    }

    public static void deleteAllFiles(File file, File file2) {
        try {
            new AtomicFile(file2).delete();
        } catch (Throwable unused) {
            Slog.w("UpdatableFontDir", "Failed to delete " + file2);
        }
        try {
            FileUtils.deleteContents(file);
        } catch (Throwable unused2) {
            Slog.w("UpdatableFontDir", "Failed to delete " + file);
        }
    }

    public static FontConfig.Font getFontByPostScriptName(String str, FontConfig fontConfig) {
        FontConfig.Font font = null;
        for (int i = 0; i < fontConfig.getFontFamilies().size(); i++) {
            FontConfig.FontFamily fontFamily = (FontConfig.FontFamily) fontConfig.getFontFamilies().get(i);
            int i2 = 0;
            while (true) {
                if (i2 < fontFamily.getFontList().size()) {
                    FontConfig.Font font2 = (FontConfig.Font) fontFamily.getFontList().get(i2);
                    if (font2.getPostScriptName().equals(str)) {
                        font = font2;
                        break;
                    }
                    i2++;
                }
            }
        }
        for (int i3 = 0; i3 < fontConfig.getNamedFamilyLists().size(); i3++) {
            FontConfig.NamedFamilyList namedFamilyList = (FontConfig.NamedFamilyList) fontConfig.getNamedFamilyLists().get(i3);
            for (int i4 = 0; i4 < namedFamilyList.getFamilies().size(); i4++) {
                FontConfig.FontFamily fontFamily2 = (FontConfig.FontFamily) namedFamilyList.getFamilies().get(i4);
                int i5 = 0;
                while (true) {
                    if (i5 < fontFamily2.getFontList().size()) {
                        FontConfig.Font font3 = (FontConfig.Font) fontFamily2.getFontList().get(i5);
                        if (font3.getPostScriptName().equals(str)) {
                            font = font3;
                            break;
                        }
                        i5++;
                    }
                }
            }
        }
        return font;
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x0041, code lost:
    
        if (r6 <= r4) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:17:0x0043, code lost:
    
        r2 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0049, code lost:
    
        if (r0.mRevision <= r4) goto L20;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean addFileToMapIfSameOrNewer(com.android.server.graphics.fonts.UpdatableFontDir.FontFileInfo r11, android.text.FontConfig r12, boolean r13) {
        /*
            r10 = this;
            android.util.ArrayMap r0 = r10.mFontFileInfoMap
            java.lang.String r1 = r11.mPsName
            java.lang.Object r0 = r0.get(r1)
            com.android.server.graphics.fonts.UpdatableFontDir$FontFileInfo r0 = (com.android.server.graphics.fonts.UpdatableFontDir.FontFileInfo) r0
            r2 = 0
            r3 = 1
            long r4 = r11.mRevision
            if (r0 != 0) goto L45
            android.text.FontConfig$Font r12 = getFontByPostScriptName(r1, r12)
            r6 = -1
            if (r12 != 0) goto L19
            goto L3f
        L19:
            java.io.File r8 = r12.getOriginalFile()
            if (r8 == 0) goto L24
            java.io.File r12 = r12.getOriginalFile()
            goto L28
        L24:
            java.io.File r12 = r12.getFile()
        L28:
            boolean r8 = r12.exists()
            if (r8 != 0) goto L2f
            goto L3f
        L2f:
            long r8 = r10.getFontRevision(r12)
            int r12 = (r8 > r6 ? 1 : (r8 == r6 ? 0 : -1))
            if (r12 != 0) goto L3e
            java.lang.String r12 = "UpdatableFontDir"
            java.lang.String r6 = "Invalid preinstalled font file"
            android.util.Slog.w(r12, r6)
        L3e:
            r6 = r8
        L3f:
            int r12 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r12 > 0) goto L4c
        L43:
            r2 = r3
            goto L4c
        L45:
            long r6 = r0.mRevision
            int r12 = (r6 > r4 ? 1 : (r6 == r4 ? 0 : -1))
            if (r12 > 0) goto L4c
            goto L43
        L4c:
            if (r2 == 0) goto L61
            if (r13 == 0) goto L5b
            if (r0 == 0) goto L5b
            java.io.File r12 = r0.mFile
            java.io.File r12 = r12.getParentFile()
            android.os.FileUtils.deleteContentsAndDir(r12)
        L5b:
            android.util.ArrayMap r10 = r10.mFontFileInfoMap
            r10.put(r1, r11)
            goto L6c
        L61:
            if (r13 == 0) goto L6c
            java.io.File r10 = r11.mFile
            java.io.File r10 = r10.getParentFile()
            android.os.FileUtils.deleteContentsAndDir(r10)
        L6c:
            return r2
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.graphics.fonts.UpdatableFontDir.addFileToMapIfSameOrNewer(com.android.server.graphics.fonts.UpdatableFontDir$FontFileInfo, android.text.FontConfig, boolean):boolean");
    }

    public final long getFontRevision(File file) {
        try {
            this.mParser.getClass();
            ByteBuffer mmap = OtfFontFileParser.mmap(file);
            try {
                return FontFileUtil.getRevision(mmap, 0);
            } finally {
                OtfFontFileParser.unmap(mmap);
            }
        } catch (IOException e) {
            Slog.e("UpdatableFontDir", "Failed to read font file", e);
            return -1L;
        }
    }

    public final Map getPostScriptMap() {
        ArrayMap arrayMap = new ArrayMap();
        for (int i = 0; i < this.mFontFileInfoMap.size(); i++) {
            FontFileInfo fontFileInfo = (FontFileInfo) this.mFontFileInfoMap.valueAt(i);
            arrayMap.put(fontFileInfo.mPsName, fontFileInfo.mFile);
        }
        return arrayMap;
    }

    public final FontConfig getSystemFontConfig() {
        FontConfig fontConfig = (FontConfig) this.mConfigSupplier.apply(getPostScriptMap());
        List list = readPersistentConfig().fontFamilies;
        ArrayList arrayList = (ArrayList) list;
        ArrayList arrayList2 = new ArrayList(arrayList.size() + fontConfig.getNamedFamilyLists().size());
        arrayList2.addAll(fontConfig.getNamedFamilyLists());
        for (int i = 0; i < arrayList.size(); i++) {
            FontConfig.NamedFamilyList resolveFontFilesForNamedFamily = resolveFontFilesForNamedFamily((FontUpdateRequest.Family) arrayList.get(i));
            if (resolveFontFilesForNamedFamily != null) {
                arrayList2.add(resolveFontFilesForNamedFamily);
            }
        }
        return new FontConfig(fontConfig.getFontFamilies(), fontConfig.getAliases(), arrayList2, fontConfig.getLocaleFallbackCustomizations(), this.mLastModifiedMillis, this.mConfigVersion);
    }

    public final void installFontFile(FileDescriptor fileDescriptor, byte[] bArr) {
        File file;
        OtfFontFileParser otfFontFileParser = this.mParser;
        FontManagerService.FsverityUtilImpl fsverityUtilImpl = this.mFsverityUtil;
        File file2 = this.mFilesDir;
        SecureRandom secureRandom = new SecureRandom();
        byte[] bArr2 = new byte[16];
        do {
            secureRandom.nextBytes(bArr2);
            file = new File(file2, "~~" + Base64.encodeToString(bArr2, 10));
        } while (file.exists());
        if (!file.mkdir()) {
            throw new FontManagerService.SystemFontException(-1, "Failed to create font directory.");
        }
        try {
            Os.chmod(file.getAbsolutePath(), 457);
            try {
                File file3 = new File(file, "font.ttf");
                try {
                    FileOutputStream fileOutputStream = new FileOutputStream(file3);
                    try {
                        FileUtils.copy(fileDescriptor, fileOutputStream.getFD());
                        fileOutputStream.close();
                        try {
                            String absolutePath = file3.getAbsolutePath();
                            fsverityUtilImpl.getClass();
                            VerityUtils.setUpFsverity(absolutePath);
                            try {
                                otfFontFileParser.getClass();
                                String buildFontFileName = OtfFontFileParser.buildFontFileName(file3);
                                if (buildFontFileName == null) {
                                    throw new FontManagerService.SystemFontException(-4, "Failed to read PostScript name from font file");
                                }
                                File file4 = new File(file, buildFontFileName);
                                if (!file3.renameTo(file4)) {
                                    throw new FontManagerService.SystemFontException(-1, "Failed to move verified font file.");
                                }
                                try {
                                    Os.chmod(file4.getAbsolutePath(), FrameworkStatsLog.VBMETA_DIGEST_REPORTED);
                                    File file5 = new File(file, "font.fsv_sig");
                                    try {
                                        FileOutputStream fileOutputStream2 = new FileOutputStream(file5);
                                        try {
                                            fileOutputStream2.write(bArr);
                                            fileOutputStream2.close();
                                            try {
                                                Os.chmod(file5.getAbsolutePath(), FrameworkStatsLog.NON_A11Y_TOOL_SERVICE_WARNING_REPORT);
                                                FontFileInfo validateFontFile = validateFontFile(file4, bArr);
                                                try {
                                                    OtfFontFileParser.tryToCreateTypeface(validateFontFile.mFile);
                                                    if (!addFileToMapIfSameOrNewer(validateFontFile, getSystemFontConfig(), false)) {
                                                        throw new FontManagerService.SystemFontException(-5, "Downgrading font file is forbidden.");
                                                    }
                                                } catch (Throwable th) {
                                                    throw new FontManagerService.SystemFontException(-3, "Failed to create Typeface from file", th);
                                                }
                                            } catch (ErrnoException e) {
                                                throw new FontManagerService.SystemFontException(-1, "Failed to change the signature file mode to 600", e);
                                            }
                                        } catch (Throwable th2) {
                                            try {
                                                fileOutputStream2.close();
                                            } catch (Throwable th3) {
                                                th2.addSuppressed(th3);
                                            }
                                            throw th2;
                                        }
                                    } catch (IOException e2) {
                                        throw new FontManagerService.SystemFontException(-1, "Failed to write font signature file to storage.", e2);
                                    }
                                } catch (ErrnoException e3) {
                                    throw new FontManagerService.SystemFontException(-1, "Failed to change font file mode to 644", e3);
                                }
                            } catch (IOException e4) {
                                throw new FontManagerService.SystemFontException(-3, "Failed to read PostScript name from font file", e4);
                            }
                        } catch (IOException e5) {
                            throw new FontManagerService.SystemFontException(-2, "Failed to setup fs-verity.", e5);
                        }
                    } catch (Throwable th4) {
                        try {
                            fileOutputStream.close();
                        } catch (Throwable th5) {
                            th4.addSuppressed(th5);
                        }
                        throw th4;
                    }
                } catch (IOException e6) {
                    throw new FontManagerService.SystemFontException(-1, "Failed to write font file to storage.", e6);
                }
            } catch (Throwable th6) {
                FileUtils.deleteContentsAndDir(file);
                throw th6;
            }
        } catch (ErrnoException e7) {
            throw new FontManagerService.SystemFontException(-1, "Failed to change mode to 711", e7);
        }
    }

    public final void loadFontFileMap() {
        boolean fixFontUpdateFailure;
        this.mFontFileInfoMap.clear();
        long j = 0;
        this.mLastModifiedMillis = 0L;
        this.mConfigVersion = 1;
        try {
            PersistentSystemFontConfig.Config readPersistentConfig = readPersistentConfig();
            this.mLastModifiedMillis = readPersistentConfig.lastModifiedMillis;
            File[] listFiles = this.mFilesDir.listFiles();
            if (listFiles == null) {
                Slog.e("UpdatableFontDir", "Could not read: " + this.mFilesDir);
                this.mFontFileInfoMap.clear();
                this.mLastModifiedMillis = 0L;
                FileUtils.deleteContents(this.mFilesDir);
                if (Flags.fixFontUpdateFailure()) {
                    this.mConfigFile.delete();
                    return;
                }
                return;
            }
            int length = listFiles.length;
            FontConfig fontConfig = null;
            int i = 0;
            while (i < length) {
                File file = listFiles[i];
                if (!file.getName().startsWith("~~")) {
                    Slog.e("UpdatableFontDir", "Unexpected dir found: " + file);
                    this.mFontFileInfoMap.clear();
                    this.mLastModifiedMillis = j;
                    FileUtils.deleteContents(this.mFilesDir);
                    if (Flags.fixFontUpdateFailure()) {
                        this.mConfigFile.delete();
                        return;
                    }
                    return;
                }
                if (((ArraySet) readPersistentConfig.updatedFontDirs).contains(file.getName())) {
                    File file2 = new File(file, "font.fsv_sig");
                    if (file2.exists()) {
                        try {
                            byte[] readAllBytes = Files.readAllBytes(Paths.get(file2.getAbsolutePath(), new String[0]));
                            File[] listFiles2 = file.listFiles();
                            if (listFiles2 != null && listFiles2.length == 2) {
                                FontFileInfo validateFontFile = validateFontFile(listFiles2[0].equals(file2) ? listFiles2[1] : listFiles2[0], readAllBytes);
                                if (fontConfig == null) {
                                    fontConfig = Flags.fixFontUpdateFailure() ? (FontConfig) this.mConfigSupplier.apply(Collections.emptyMap()) : getSystemFontConfig();
                                }
                                addFileToMapIfSameOrNewer(validateFontFile, fontConfig, true);
                            }
                            Slog.e("UpdatableFontDir", "Unexpected files in dir: " + file);
                            if (fixFontUpdateFailure) {
                                return;
                            } else {
                                return;
                            }
                        } catch (IOException unused) {
                            Slog.e("UpdatableFontDir", "Failed to read signature file.");
                            this.mFontFileInfoMap.clear();
                            this.mLastModifiedMillis = 0L;
                            FileUtils.deleteContents(this.mFilesDir);
                            if (Flags.fixFontUpdateFailure()) {
                                this.mConfigFile.delete();
                                return;
                            }
                            return;
                        }
                    }
                    Slog.i("UpdatableFontDir", "The signature file is missing.");
                    if (Flags.fixFontUpdateFailure()) {
                        this.mFontFileInfoMap.clear();
                        this.mLastModifiedMillis = j;
                        FileUtils.deleteContents(this.mFilesDir);
                        if (Flags.fixFontUpdateFailure()) {
                            this.mConfigFile.delete();
                            return;
                        }
                        return;
                    }
                    FileUtils.deleteContentsAndDir(file);
                } else {
                    Slog.i("UpdatableFontDir", "Deleting obsolete dir: " + file);
                    FileUtils.deleteContentsAndDir(file);
                }
                i++;
                j = 0;
            }
            if (Flags.fixFontUpdateFailure()) {
                for (int i2 = 0; i2 < ((ArrayList) readPersistentConfig.fontFamilies).size(); i2++) {
                    FontUpdateRequest.Family family = (FontUpdateRequest.Family) ((ArrayList) readPersistentConfig.fontFamilies).get(i2);
                    for (int i3 = 0; i3 < family.getFonts().size(); i3++) {
                        FontUpdateRequest.Font font = (FontUpdateRequest.Font) family.getFonts().get(i3);
                        if (!this.mFontFileInfoMap.containsKey(font.getPostScriptName())) {
                            if (fontConfig == null) {
                                fontConfig = (FontConfig) this.mConfigSupplier.apply(Collections.emptyMap());
                            }
                            if (getFontByPostScriptName(font.getPostScriptName(), fontConfig) == null) {
                                Slog.e("UpdatableFontDir", "Unknown font that has PostScript name " + font.getPostScriptName() + " is requested in FontFamily " + family.getName());
                                this.mFontFileInfoMap.clear();
                                this.mLastModifiedMillis = 0L;
                                FileUtils.deleteContents(this.mFilesDir);
                                if (Flags.fixFontUpdateFailure()) {
                                    this.mConfigFile.delete();
                                    return;
                                }
                                return;
                            }
                        }
                    }
                }
            }
        } catch (Throwable th) {
            try {
                Slog.e("UpdatableFontDir", "Failed to load font mappings.", th);
                this.mFontFileInfoMap.clear();
                this.mLastModifiedMillis = 0L;
                FileUtils.deleteContents(this.mFilesDir);
                if (Flags.fixFontUpdateFailure()) {
                    this.mConfigFile.delete();
                }
            } finally {
                this.mFontFileInfoMap.clear();
                this.mLastModifiedMillis = 0L;
                FileUtils.deleteContents(this.mFilesDir);
                if (Flags.fixFontUpdateFailure()) {
                    this.mConfigFile.delete();
                }
            }
        }
    }

    public final PersistentSystemFontConfig.Config readPersistentConfig() {
        FileInputStream openRead;
        PersistentSystemFontConfig.Config config = new PersistentSystemFontConfig.Config();
        try {
            openRead = this.mConfigFile.openRead();
        } catch (IOException | XmlPullParserException | Exception unused) {
        }
        try {
            PersistentSystemFontConfig.loadFromXml(openRead, config);
            if (openRead != null) {
                openRead.close();
            }
            return config;
        } catch (Throwable th) {
            if (openRead != null) {
                try {
                    openRead.close();
                } catch (Throwable th2) {
                    th.addSuppressed(th2);
                }
            }
            throw th;
        }
    }

    public final FontConfig.NamedFamilyList resolveFontFilesForNamedFamily(FontUpdateRequest.Family family) {
        List fonts = family.getFonts();
        ArrayList arrayList = new ArrayList(fonts.size());
        for (int i = 0; i < fonts.size(); i++) {
            FontUpdateRequest.Font font = (FontUpdateRequest.Font) fonts.get(i);
            FontFileInfo fontFileInfo = (FontFileInfo) this.mFontFileInfoMap.get(font.getPostScriptName());
            if (fontFileInfo == null) {
                Slog.e("UpdatableFontDir", "Failed to lookup font file that has " + font.getPostScriptName());
                return null;
            }
            arrayList.add(new FontConfig.Font(fontFileInfo.mFile, (File) null, fontFileInfo.mPsName, font.getFontStyle(), font.getIndex(), font.getFontVariationSettings(), (String) null, 0));
        }
        return new FontConfig.NamedFamilyList(Collections.singletonList(new FontConfig.FontFamily(arrayList, LocaleList.getEmptyLocaleList(), 0)), family.getName());
    }

    public final void update(List list) {
        Iterator it = list.iterator();
        while (it.hasNext()) {
            FontUpdateRequest fontUpdateRequest = (FontUpdateRequest) it.next();
            int type = fontUpdateRequest.getType();
            if (type == 0) {
                Objects.requireNonNull(fontUpdateRequest.getFd());
                Objects.requireNonNull(fontUpdateRequest.getSignature());
            } else if (type == 1) {
                Objects.requireNonNull(fontUpdateRequest.getFontFamily());
                Objects.requireNonNull(fontUpdateRequest.getFontFamily().getName());
            }
        }
        ArrayMap arrayMap = new ArrayMap(this.mFontFileInfoMap);
        PersistentSystemFontConfig.Config readPersistentConfig = readPersistentConfig();
        HashMap hashMap = new HashMap();
        for (int i = 0; i < ((ArrayList) readPersistentConfig.fontFamilies).size(); i++) {
            FontUpdateRequest.Family family = (FontUpdateRequest.Family) ((ArrayList) readPersistentConfig.fontFamilies).get(i);
            hashMap.put(family.getName(), family);
        }
        long j = this.mLastModifiedMillis;
        try {
            Iterator it2 = list.iterator();
            while (it2.hasNext()) {
                FontUpdateRequest fontUpdateRequest2 = (FontUpdateRequest) it2.next();
                int type2 = fontUpdateRequest2.getType();
                if (type2 == 0) {
                    installFontFile(fontUpdateRequest2.getFd().getFileDescriptor(), fontUpdateRequest2.getSignature());
                } else if (type2 == 1) {
                    FontUpdateRequest.Family fontFamily = fontUpdateRequest2.getFontFamily();
                    hashMap.put(fontFamily.getName(), fontFamily);
                }
            }
            Iterator it3 = hashMap.values().iterator();
            while (it3.hasNext()) {
                if (resolveFontFilesForNamedFamily((FontUpdateRequest.Family) it3.next()) == null) {
                    throw new FontManagerService.SystemFontException(-9, "Required fonts are not available");
                }
            }
            this.mLastModifiedMillis = ((Long) this.mCurrentTimeSupplier.get()).longValue();
            PersistentSystemFontConfig.Config config = new PersistentSystemFontConfig.Config();
            config.lastModifiedMillis = this.mLastModifiedMillis;
            Iterator it4 = this.mFontFileInfoMap.values().iterator();
            while (it4.hasNext()) {
                ((ArraySet) config.updatedFontDirs).add(((FontFileInfo) it4.next()).mFile.getParentFile().getName());
            }
            ((ArrayList) config.fontFamilies).addAll(hashMap.values());
            FileOutputStream fileOutputStream = null;
            try {
                fileOutputStream = this.mConfigFile.startWrite();
                PersistentSystemFontConfig.writeToXml(fileOutputStream, config);
                this.mConfigFile.finishWrite(fileOutputStream);
                this.mConfigVersion++;
            } catch (IOException e) {
                if (fileOutputStream != null) {
                    this.mConfigFile.failWrite(fileOutputStream);
                }
                throw new FontManagerService.SystemFontException(-6, "Failed to write config XML.", e);
            }
        } catch (Throwable th) {
            this.mFontFileInfoMap.clear();
            this.mFontFileInfoMap.putAll(arrayMap);
            this.mLastModifiedMillis = j;
            throw th;
        }
    }

    public final FontFileInfo validateFontFile(File file, byte[] bArr) {
        FileInputStream fileInputStream;
        String absolutePath = file.getAbsolutePath();
        FontManagerService.FsverityUtilImpl fsverityUtilImpl = this.mFsverityUtil;
        fsverityUtilImpl.getClass();
        byte[] fsverityDigest = VerityUtils.getFsverityDigest(absolutePath);
        if (fsverityDigest != null) {
            for (String str : fsverityUtilImpl.mDerCertPaths) {
                try {
                    fileInputStream = new FileInputStream(str);
                    try {
                    } finally {
                    }
                } catch (IOException unused) {
                    NetworkScorerAppManager$$ExternalSyntheticOutline0.m("Failed to read certificate file: ", str, "FontManagerService");
                }
                if (VerityUtils.verifyPkcs7DetachedSignature(bArr, fsverityDigest, fileInputStream)) {
                    fileInputStream.close();
                    try {
                        this.mParser.getClass();
                        ByteBuffer mmap = OtfFontFileParser.mmap(file);
                        try {
                            String postScriptName = FontFileUtil.getPostScriptName(mmap, 0);
                            long fontRevision = getFontRevision(file);
                            if (fontRevision != -1) {
                                return new FontFileInfo(file, postScriptName, fontRevision);
                            }
                            throw new FontManagerService.SystemFontException(-3, AccountManagerService$$ExternalSyntheticOutline0.m(file, "Font validation failed. Could not read font revision: "));
                        } finally {
                            OtfFontFileParser.unmap(mmap);
                        }
                    } catch (IOException unused2) {
                        throw new FontManagerService.SystemFontException(-4, AccountManagerService$$ExternalSyntheticOutline0.m(file, "Font validation failed. Could not read PostScript name name: "));
                    }
                }
                fileInputStream.close();
            }
        } else {
            NetworkScorerAppManager$$ExternalSyntheticOutline0.m("Failed to get fs-verity digest for ", absolutePath, "FontManagerService");
        }
        throw new FontManagerService.SystemFontException(-2, AccountManagerService$$ExternalSyntheticOutline0.m(file, "Font validation failed. Fs-verity is not enabled: "));
    }
}
