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
