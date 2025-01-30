package com.android.systemui.keyguardimage;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.ParcelFileDescriptor;
import android.provider.Settings;
import android.util.Log;
import android.util.Pair;
import androidx.appcompat.widget.ActionBarContextView$$ExternalSyntheticOutline0;
import androidx.slice.widget.RowView$$ExternalSyntheticOutline0;
import com.android.systemui.Dependency;
import com.android.systemui.keyguardimage.ImageOptionCreator;
import com.android.systemui.util.LogUtil;
import com.android.systemui.util.SettingsHelper;
import com.android.systemui.wallpaper.WallpaperUtils;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public class KeyguardImageProvider extends ContentProvider {
    public static final /* synthetic */ int $r8$clinit = 0;
    public ImageCreator[] mClockImageCreator;
    public ImageCreator[] mCreatorsForFixedShortcut;
    public ImageCreator[] mCreatorsForWallpaper;
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    public boolean mWasShortcutEnabled = false;

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MyWriter implements ContentProvider.PipeDataWriter {
        private MyWriter() {
        }

        public /* synthetic */ MyWriter(int i) {
            this();
        }

        /* JADX WARN: Code restructure failed: missing block: B:18:0x0058, code lost:
        
            if (r6.isLiveWallpaperEnabled() == false) goto L42;
         */
        /* JADX WARN: Code restructure failed: missing block: B:19:0x008d, code lost:
        
            android.util.Log.w("KeyguardImageProvider", "writer, recycled");
            r10.recycle();
         */
        /* JADX WARN: Code restructure failed: missing block: B:56:0x008b, code lost:
        
            if (r6.isLiveWallpaperEnabled() == false) goto L42;
         */
        @Override // android.content.ContentProvider.PipeDataWriter
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public final void writeDataToPipe(ParcelFileDescriptor parcelFileDescriptor, Uri uri, String str, Bundle bundle, Object obj) {
            boolean z;
            ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream;
            Bitmap bitmap = (Bitmap) obj;
            int startTime = LogUtil.startTime(-1);
            try {
                try {
                    autoCloseOutputStream = new ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptor);
                } catch (Exception e) {
                    Log.w("KeyguardImageProvider", "MyWriter, fail to write to pipe", e);
                    z = WallpaperUtils.getCachedWallpaper(WallpaperUtils.sCurrentWhich) == bitmap;
                    SettingsHelper settingsHelper = (SettingsHelper) Dependency.get(SettingsHelper.class);
                    if (!z) {
                        if (bitmap != null) {
                            if (!bitmap.isRecycled()) {
                            }
                        }
                    }
                }
                try {
                    Log.i("KeyguardImageProvider", "writer, mimeType: " + str);
                    bitmap.compress("image/jpeg".equals(str) ? Bitmap.CompressFormat.JPEG : Bitmap.CompressFormat.PNG, 100, autoCloseOutputStream);
                    autoCloseOutputStream.close();
                    z = WallpaperUtils.getCachedWallpaper(WallpaperUtils.sCurrentWhich) == bitmap;
                    SettingsHelper settingsHelper2 = (SettingsHelper) Dependency.get(SettingsHelper.class);
                    if (!z) {
                        if (!bitmap.isRecycled()) {
                        }
                    }
                    LogUtil.endTime(startTime, "KeyguardImageProvider", "writing done", new Object[0]);
                } catch (Throwable th) {
                    try {
                        autoCloseOutputStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } catch (Throwable th3) {
                z = WallpaperUtils.getCachedWallpaper(WallpaperUtils.sCurrentWhich) == bitmap;
                SettingsHelper settingsHelper3 = (SettingsHelper) Dependency.get(SettingsHelper.class);
                if (!z && bitmap != null && !bitmap.isRecycled() && !settingsHelper3.isLiveWallpaperEnabled()) {
                    Log.w("KeyguardImageProvider", "writer, recycled");
                    bitmap.recycle();
                }
                throw th3;
            }
        }
    }

    @Override // android.content.ContentProvider
    public final int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    @Override // android.content.ContentProvider
    public final String getType(Uri uri) {
        ImageOptionCreator.ImageOption createImageOption = ImageOptionCreator.createImageOption(getContext(), uri, true);
        return (createImageOption == null || createImageOption.type != 1) ? "image/png" : "image/jpeg";
    }

    @Override // android.content.ContentProvider
    public final Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final boolean onCreate() {
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:122:0x01f2  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x01a3  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x01a8  */
    @Override // android.content.ContentProvider
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final ParcelFileDescriptor openFile(Uri uri, String str) {
        String str2;
        Bitmap createImage;
        Bitmap bitmap;
        ImageCreator[] imageCreatorArr;
        final ImageCreator[] imageCreatorArr2;
        List<Pair> list;
        LogUtil.m223d("KeyguardImageProvider", "openFile() %s / pid: %d", uri.toSafeString(), Integer.valueOf(Binder.getCallingPid()));
        Context context = getContext();
        if (context == null) {
            Log.e("KeyguardImageProvider", "not prepared");
            throw new FileNotFoundException("illegal state");
        }
        final int i = 0;
        final ImageOptionCreator.ImageOption createImageOption = ImageOptionCreator.createImageOption(context, uri, false);
        if (createImageOption == null) {
            Log.e("KeyguardImageProvider", "wrong uri");
            throw new FileNotFoundException("wrong uri");
        }
        Log.d("KeyguardImageProvider", "openFile() imageOption " + createImageOption.toString());
        str2 = "image/png";
        int i2 = createImageOption.type;
        final int i3 = 1;
        Exception exc = null;
        if (i2 == 1 || i2 == 5) {
            str2 = "image/jpeg";
            createImage = new WallpaperImageProviderCreator(getContext()).createImage(createImageOption, null);
        } else {
            if (i2 != 2 && i2 != 4) {
                boolean z = i2 == 3;
                synchronized (this) {
                    if (this.mClockImageCreator == null) {
                        this.mClockImageCreator = new ImageCreator[]{new ClockImageCreator(context)};
                    }
                }
                boolean z2 = Settings.System.getInt(context.getContentResolver(), "lockscreen_show_shortcut", 1) == 1;
                ActionBarContextView$$ExternalSyntheticOutline0.m9m(RowView$$ExternalSyntheticOutline0.m49m("getImageCreator isShortcutEnabled= ", z2, ", wasShortcutEnabled= "), this.mWasShortcutEnabled, "KeyguardImageProvider");
                boolean z3 = this.mWasShortcutEnabled != z2;
                this.mWasShortcutEnabled = z2;
                if (z2 || z) {
                    synchronized (this) {
                        if (z2) {
                            try {
                                ImageCreator[] imageCreatorArr3 = this.mCreatorsForFixedShortcut;
                                if (imageCreatorArr3 == null || imageCreatorArr3.length < 3) {
                                    this.mCreatorsForFixedShortcut = new ImageCreator[]{this.mClockImageCreator[0], new LeftShortcutImageCreator(context), new RightShortcutImageCreator(context)};
                                    imageCreatorArr = this.mCreatorsForFixedShortcut;
                                }
                            } finally {
                            }
                        }
                        if (!z2 && this.mCreatorsForFixedShortcut == null) {
                            this.mCreatorsForFixedShortcut = this.mClockImageCreator;
                        }
                        imageCreatorArr = this.mCreatorsForFixedShortcut;
                    }
                    if (z) {
                        synchronized (this) {
                            if (this.mCreatorsForWallpaper == null || z3) {
                                this.mCreatorsForWallpaper = null;
                                if (z2) {
                                    ImageCreator[] imageCreatorArr4 = this.mCreatorsForFixedShortcut;
                                    this.mCreatorsForWallpaper = new ImageCreator[]{new WallpaperImageProviderCreator(context), this.mClockImageCreator[0], imageCreatorArr4[1], imageCreatorArr4[2]};
                                } else {
                                    this.mCreatorsForWallpaper = new ImageCreator[]{new WallpaperImageProviderCreator(context), this.mClockImageCreator[0]};
                                }
                            }
                            imageCreatorArr2 = this.mCreatorsForWallpaper;
                        }
                    } else {
                        imageCreatorArr2 = imageCreatorArr;
                    }
                } else {
                    synchronized (this) {
                        imageCreatorArr2 = this.mClockImageCreator;
                    }
                }
                str2 = createImageOption.type == 3 ? "image/jpeg" : "image/png";
                bitmap = Bitmap.createBitmap(createImageOption.width, createImageOption.height, Bitmap.Config.ARGB_8888);
                Canvas canvas = new Canvas(bitmap);
                final LinkedBlockingDeque linkedBlockingDeque = new LinkedBlockingDeque(1);
                this.mHandler.post(new Runnable() { // from class: com.android.systemui.keyguardimage.KeyguardImageProvider$$ExternalSyntheticLambda0
                    @Override // java.lang.Runnable
                    public final void run() {
                        switch (i3) {
                            case 0:
                                KeyguardImageProvider keyguardImageProvider = (KeyguardImageProvider) imageCreatorArr2;
                                ImageOptionCreator.ImageOption imageOption = createImageOption;
                                BlockingDeque blockingDeque = linkedBlockingDeque;
                                int i4 = KeyguardImageProvider.$r8$clinit;
                                keyguardImageProvider.getClass();
                                try {
                                    blockingDeque.put(new ClockImageCreator(keyguardImageProvider.getContext()).createImage(imageOption, null));
                                    break;
                                } catch (Exception e) {
                                    e.printStackTrace();
                                }
                            default:
                                ImageCreator[] imageCreatorArr5 = (ImageCreator[]) imageCreatorArr2;
                                ImageOptionCreator.ImageOption imageOption2 = createImageOption;
                                BlockingDeque blockingDeque2 = linkedBlockingDeque;
                                int i5 = KeyguardImageProvider.$r8$clinit;
                                int startTime = LogUtil.startTime(-1);
                                LinkedList linkedList = new LinkedList();
                                try {
                                    for (ImageCreator imageCreator : imageCreatorArr5) {
                                        Point point = new Point();
                                        Bitmap createImage2 = imageCreator.createImage(imageOption2, point);
                                        if (createImage2 != null && !createImage2.isRecycled()) {
                                            linkedList.add(new Pair(imageCreator.createImage(imageOption2, point), point));
                                        }
                                    }
                                    blockingDeque2.put(linkedList);
                                } catch (InterruptedException e2) {
                                    e2.printStackTrace();
                                }
                                LogUtil.endTime(startTime, "KeyguardImageProvider", "%d images were created", Integer.valueOf(linkedList.size()));
                                break;
                        }
                    }
                });
                try {
                    list = (List) linkedBlockingDeque.poll(3000L, TimeUnit.MILLISECONDS);
                    if (list != null) {
                        try {
                            if (list.size() > 0) {
                                for (Pair pair : list) {
                                    Bitmap bitmap2 = (Bitmap) pair.first;
                                    Object obj = pair.second;
                                    canvas.drawBitmap(bitmap2, (Rect) null, new Rect(((Point) obj).x, ((Point) obj).y, ((Point) obj).x + bitmap2.getWidth(), ((Point) pair.second).y + bitmap2.getHeight()), (Paint) null);
                                }
                                list.clear();
                            }
                        } catch (Exception e) {
                            e = e;
                            exc = e;
                            if (exc == null) {
                            }
                            StringBuilder sb = new StringBuilder("openFile failed ");
                            sb.append(exc == null ? exc.getMessage() : "");
                            Log.d("KeyguardImageProvider", sb.toString());
                            throw new FileNotFoundException("operation failed");
                        }
                    }
                } catch (Exception e2) {
                    e = e2;
                    list = null;
                }
                if (exc == null || list == null) {
                    StringBuilder sb2 = new StringBuilder("openFile failed ");
                    sb2.append(exc == null ? exc.getMessage() : "");
                    Log.d("KeyguardImageProvider", sb2.toString());
                    throw new FileNotFoundException("operation failed");
                }
                return openPipeHelper(uri, str2, null, bitmap, new MyWriter(i));
            }
            final LinkedBlockingDeque linkedBlockingDeque2 = new LinkedBlockingDeque(1);
            this.mHandler.post(new Runnable() { // from class: com.android.systemui.keyguardimage.KeyguardImageProvider$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() {
                    switch (i) {
                        case 0:
                            KeyguardImageProvider keyguardImageProvider = (KeyguardImageProvider) this;
                            ImageOptionCreator.ImageOption imageOption = createImageOption;
                            BlockingDeque blockingDeque = linkedBlockingDeque2;
                            int i4 = KeyguardImageProvider.$r8$clinit;
                            keyguardImageProvider.getClass();
                            try {
                                blockingDeque.put(new ClockImageCreator(keyguardImageProvider.getContext()).createImage(imageOption, null));
                                break;
                            } catch (Exception e3) {
                                e3.printStackTrace();
                            }
                        default:
                            ImageCreator[] imageCreatorArr5 = (ImageCreator[]) this;
                            ImageOptionCreator.ImageOption imageOption2 = createImageOption;
                            BlockingDeque blockingDeque2 = linkedBlockingDeque2;
                            int i5 = KeyguardImageProvider.$r8$clinit;
                            int startTime = LogUtil.startTime(-1);
                            LinkedList linkedList = new LinkedList();
                            try {
                                for (ImageCreator imageCreator : imageCreatorArr5) {
                                    Point point = new Point();
                                    Bitmap createImage2 = imageCreator.createImage(imageOption2, point);
                                    if (createImage2 != null && !createImage2.isRecycled()) {
                                        linkedList.add(new Pair(imageCreator.createImage(imageOption2, point), point));
                                    }
                                }
                                blockingDeque2.put(linkedList);
                            } catch (InterruptedException e22) {
                                e22.printStackTrace();
                            }
                            LogUtil.endTime(startTime, "KeyguardImageProvider", "%d images were created", Integer.valueOf(linkedList.size()));
                            break;
                    }
                }
            });
            try {
                createImage = (Bitmap) linkedBlockingDeque2.poll(3000L, TimeUnit.MILLISECONDS);
                if (createImage == null) {
                    try {
                        Log.w("KeyguardImageProvider", "openFile, clock bitmap is null");
                    } catch (Exception e3) {
                        e = e3;
                        exc = e;
                        exc.printStackTrace();
                        if (exc != null) {
                        }
                        bitmap = createImage;
                        return openPipeHelper(uri, str2, null, bitmap, new MyWriter(i));
                    }
                }
            } catch (Exception e4) {
                e = e4;
                createImage = null;
            }
            if (exc != null) {
                throw new FileNotFoundException("operation failed");
            }
        }
        bitmap = createImage;
        return openPipeHelper(uri, str2, null, bitmap, new MyWriter(i));
    }

    @Override // android.content.ContentProvider
    public final Cursor query(Uri uri, String[] strArr, String str, String[] strArr2, String str2) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final int update(Uri uri, ContentValues contentValues, String str, String[] strArr) {
        return 0;
    }
}
