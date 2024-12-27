package com.android.server.wallpaper;

import android.R;
import android.app.SemWallpaperResourcesInfo;
import android.content.ComponentName;
import android.content.Context;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.os.Environment;
import android.util.Pair;

import com.android.internal.util.JournaledFile;

import com.samsung.server.wallpaper.SemWallpaperManagerService;

import java.io.File;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class WallpaperDataParser {
    public final Context mContext;
    public final ComponentName mImageWallpaper;
    public final SemWallpaperManagerService mSemService;
    public final SemWallpaperResourcesInfo mSemWallpaperResourcesInfo;
    public final WallpaperCropper mWallpaperCropper;
    public final WallpaperDisplayHelper mWallpaperDisplayHelper;

    public WallpaperDataParser(
            Context context,
            WallpaperDisplayHelper wallpaperDisplayHelper,
            WallpaperCropper wallpaperCropper,
            SemWallpaperManagerService semWallpaperManagerService,
            SemWallpaperResourcesInfo semWallpaperResourcesInfo) {
        this.mContext = context;
        this.mWallpaperDisplayHelper = wallpaperDisplayHelper;
        this.mWallpaperCropper = wallpaperCropper;
        this.mImageWallpaper =
                ComponentName.unflattenFromString(
                        context.getResources()
                                .getString(R.string.network_logging_notification_text));
        this.mSemService = semWallpaperManagerService;
        this.mSemWallpaperResourcesInfo = semWallpaperResourcesInfo;
    }

    public static void ensureSaneWallpaperData(WallpaperData wallpaperData) {
        if (wallpaperData.cropHint.width() < 0 || wallpaperData.cropHint.height() < 0) {
            wallpaperData.cropHint.set(0, 0, 0, 0);
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:71:0x009b, code lost:

       if ("kwp".equals(r6) != false) goto L32;
    */
    /* JADX WARN: Removed duplicated region for block: B:23:0x019b  */
    /* JADX WARN: Removed duplicated region for block: B:26:0x01a6  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0204  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x0221  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x019d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static boolean isSameWithPreviousWallpaper(
            int r17, com.android.server.wallpaper.WallpaperData r18) {
        /*
            Method dump skipped, instructions count: 595
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wallpaper.WallpaperDataParser.isSameWithPreviousWallpaper(int,"
                    + " com.android.server.wallpaper.WallpaperData):boolean");
    }

    public static JournaledFile makeJournaledFile(int i, int i2) {
        String absolutePath =
                new File(Environment.getUserSystemDirectory(i), WallpaperUtils.getInfoFileName(i2))
                        .getAbsolutePath();
        return new JournaledFile(
                new File(absolutePath),
                new File(
                        ConnectivityModuleConnector$$ExternalSyntheticOutline0.m$1(
                                absolutePath, ".tmp")));
    }

    public static List screenDimensionPairs() {
        return List.of(
                new Pair(0, "Portrait"),
                new Pair(1, "Landscape"),
                new Pair(2, "SquarePortrait"),
                new Pair(3, "SquareLandscape"));
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x037b  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x02c3  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x02e0  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0321  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0329  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x033a  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x034b  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x03a7  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x03d3 A[ORIG_RETURN, RETURN] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void parseWallpaperAttributes(
            com.android.modules.utils.TypedXmlPullParser r19,
            com.android.server.wallpaper.WallpaperData r20,
            boolean r21)
            throws org.xmlpull.v1.XmlPullParserException {
        /*
            Method dump skipped, instructions count: 980
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wallpaper.WallpaperDataParser.parseWallpaperAttributes(com.android.modules.utils.TypedXmlPullParser,"
                    + " com.android.server.wallpaper.WallpaperData, boolean):void");
    }

    /* JADX WARN: Code restructure failed: missing block: B:54:0x018a, code lost:

       if (r4 != null) goto L75;
    */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0141, code lost:

       libcore.io.IoUtils.closeQuietly(r1);
       libcore.io.IoUtils.closeQuietly(r4);
    */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x013e, code lost:

       android.os.FileUtils.sync(r4);
    */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0166, code lost:

       if (r4 != null) goto L75;
    */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x013c, code lost:

       if (r4 != null) goto L75;
    */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r1v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v11 */
    /* JADX WARN: Type inference failed for: r1v15 */
    /* JADX WARN: Type inference failed for: r1v29 */
    /* JADX WARN: Type inference failed for: r1v30 */
    /* JADX WARN: Type inference failed for: r1v5, types: [java.io.FileOutputStream, java.lang.AutoCloseable] */
    /* JADX WARN: Type inference failed for: r4v0 */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v11 */
    /* JADX WARN: Type inference failed for: r4v15 */
    /* JADX WARN: Type inference failed for: r4v20 */
    /* JADX WARN: Type inference failed for: r4v29 */
    /* JADX WARN: Type inference failed for: r4v5, types: [java.io.FileOutputStream, java.lang.AutoCloseable] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean restoreNamedResourceLocked(
            com.android.server.wallpaper.WallpaperData r13) {
        /*
            Method dump skipped, instructions count: 418
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wallpaper.WallpaperDataParser.restoreNamedResourceLocked(com.android.server.wallpaper.WallpaperData):boolean");
    }

    /* JADX WARN: Removed duplicated region for block: B:103:0x036c  */
    /* JADX WARN: Removed duplicated region for block: B:133:0x03d1  */
    /* JADX WARN: Removed duplicated region for block: B:149:0x0285  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x01b5  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x01ec  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x02a9  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x02b4  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x02c2  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x02cd  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x02d8  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x02e3  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0311  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x035b  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public void writeWallpaperAttributes(
            com.android.modules.utils.TypedXmlSerializer r17,
            java.lang.String r18,
            com.android.server.wallpaper.WallpaperData r19)
            throws java.lang.IllegalArgumentException,
                    java.lang.IllegalStateException,
                    java.io.IOException {
        /*
            Method dump skipped, instructions count: 1027
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.wallpaper.WallpaperDataParser.writeWallpaperAttributes(com.android.modules.utils.TypedXmlSerializer,"
                    + " java.lang.String, com.android.server.wallpaper.WallpaperData):void");
    }
}
