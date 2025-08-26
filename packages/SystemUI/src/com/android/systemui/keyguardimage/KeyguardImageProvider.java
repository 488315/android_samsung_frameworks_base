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
import com.android.systemui.keyguardimage.ImageOptionCreator;
import com.android.systemui.util.LogUtil;
import com.android.systemui.util.SettingsHelper;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

/* loaded from: classes2.dex */
public class KeyguardImageProvider extends ContentProvider {
    public static final /* synthetic */ int $r8$clinit = 0;
    public ImageCreator[] mClockImageCreator;
    public ImageCreator[] mCreatorsForFixedShortcut;
    public ImageCreator[] mCreatorsForWallpaper;
    public final Handler mHandler = new Handler(Looper.getMainLooper());
    public boolean mWasShortcutEnabled = false;

    public class MyWriter implements ContentProvider.PipeDataWriter {
        public /* synthetic */ MyWriter(int i) {
            this();
        }

        @Override // android.content.ContentProvider.PipeDataWriter
        public final void writeDataToPipe(ParcelFileDescriptor parcelFileDescriptor, Uri uri, String str, Bundle bundle, Object obj) {
            Bitmap bitmap = (Bitmap) obj;
            int iStartTime = LogUtil.startTime(-1);
            try {
                ParcelFileDescriptor.AutoCloseOutputStream autoCloseOutputStream = new ParcelFileDescriptor.AutoCloseOutputStream(parcelFileDescriptor);
                try {
                    Log.i("KeyguardImageProvider", "writer, mimeType: " + str);
                    bitmap.compress("image/jpeg".equals(str) ? Bitmap.CompressFormat.JPEG : Bitmap.CompressFormat.PNG, 100, autoCloseOutputStream);
                    autoCloseOutputStream.close();
                } catch (Throwable th) {
                    try {
                        autoCloseOutputStream.close();
                    } catch (Throwable th2) {
                        th.addSuppressed(th2);
                    }
                    throw th;
                }
            } catch (Exception e) {
                Log.w("KeyguardImageProvider", "MyWriter, fail to write to pipe", e);
            }
            LogUtil.endTime(iStartTime, "KeyguardImageProvider", "writing done", new Object[0]);
        }

        private MyWriter() {
        }
    }

    @Override // android.content.ContentProvider
    public final int delete(Uri uri, String str, String[] strArr) {
        return 0;
    }

    @Override // android.content.ContentProvider
    public final String getType(Uri uri) throws NumberFormatException {
        ImageOptionCreator.ImageOption imageOptionCreateImageOption = ImageOptionCreator.createImageOption(getContext(), uri, true);
        return (imageOptionCreateImageOption == null || imageOptionCreateImageOption.type != 1) ? "image/png" : "image/jpeg";
    }

    @Override // android.content.ContentProvider
    public final Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override // android.content.ContentProvider
    public final boolean onCreate() {
        return true;
    }

    /* JADX WARN: Removed duplicated region for block: B:103:0x01ae  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x01b3  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x01fa  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00b8  */
    @Override // android.content.ContentProvider
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final ParcelFileDescriptor openFile(Uri uri, String str) throws NoSuchMethodException, NumberFormatException, IOException, SecurityException {
        String str2;
        int i;
        Bitmap bitmapCreateImage;
        ImageCreator[] imageCreatorArr;
        final ImageCreator[] imageCreatorArr2;
        List<Pair> list;
        LogUtil.d("KeyguardImageProvider", "openFile() %s / pid: %d", uri.toSafeString(), Integer.valueOf(Binder.getCallingPid()));
        Context context = getContext();
        if (context == null) {
            Log.e("KeyguardImageProvider", "not prepared");
            throw new FileNotFoundException("illegal state");
        }
        final ImageOptionCreator.ImageOption imageOptionCreateImageOption = ImageOptionCreator.createImageOption(context, uri, false);
        if (imageOptionCreateImageOption == null) {
            Log.e("KeyguardImageProvider", "wrong uri");
            throw new FileNotFoundException("wrong uri");
        }
        Log.d("KeyguardImageProvider", "openFile() imageOption " + imageOptionCreateImageOption.toString());
        str2 = "image/png";
        int i2 = imageOptionCreateImageOption.type;
        Exception exc = null;
        if (i2 == 1 || i2 == 5) {
            i = 0;
            str2 = "image/jpeg";
            bitmapCreateImage = new WallpaperImageProviderCreator(getContext()).createImage(imageOptionCreateImageOption, null);
        } else if (i2 == 2 || i2 == 4) {
            i = 0;
            final LinkedBlockingDeque linkedBlockingDeque = new LinkedBlockingDeque(1);
            final int i3 = 0;
            this.mHandler.post(new Runnable() { // from class: com.android.systemui.keyguardimage.KeyguardImageProvider$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() throws InterruptedException {
                    switch (i3) {
                        case 0:
                            KeyguardImageProvider keyguardImageProvider = (KeyguardImageProvider) this;
                            ImageOptionCreator.ImageOption imageOption = imageOptionCreateImageOption;
                            BlockingDeque blockingDeque = linkedBlockingDeque;
                            int i4 = KeyguardImageProvider.$r8$clinit;
                            keyguardImageProvider.getClass();
                            try {
                                blockingDeque.put(new ClockImageCreator(keyguardImageProvider.getContext()).createImage(imageOption, null));
                                break;
                            } catch (Exception e) {
                                e.printStackTrace();
                                return;
                            }
                        default:
                            ImageCreator[] imageCreatorArr3 = (ImageCreator[]) this;
                            ImageOptionCreator.ImageOption imageOption2 = imageOptionCreateImageOption;
                            BlockingDeque blockingDeque2 = linkedBlockingDeque;
                            int i5 = KeyguardImageProvider.$r8$clinit;
                            int iStartTime = LogUtil.startTime(-1);
                            LinkedList linkedList = new LinkedList();
                            try {
                                for (ImageCreator imageCreator : imageCreatorArr3) {
                                    Point point = new Point();
                                    Bitmap bitmapCreateImage2 = imageCreator.createImage(imageOption2, point);
                                    if (bitmapCreateImage2 != null && !bitmapCreateImage2.isRecycled()) {
                                        linkedList.add(new Pair(imageCreator.createImage(imageOption2, point), point));
                                    }
                                }
                                blockingDeque2.put(linkedList);
                            } catch (InterruptedException e2) {
                                e2.printStackTrace();
                            }
                            LogUtil.endTime(iStartTime, "KeyguardImageProvider", "%d images were created", Integer.valueOf(linkedList.size()));
                            break;
                    }
                }
            });
            try {
                bitmapCreateImage = (Bitmap) linkedBlockingDeque.poll(3000L, TimeUnit.MILLISECONDS);
                if (bitmapCreateImage == null) {
                    try {
                        Log.w("KeyguardImageProvider", "openFile, clock bitmap is null");
                    } catch (Exception e) {
                        e = e;
                        exc = e;
                        exc.printStackTrace();
                        if (exc != null) {
                        }
                        return openPipeHelper(uri, str2, null, bitmapCreateImage, new MyWriter(i));
                    }
                }
            } catch (Exception e2) {
                e = e2;
                bitmapCreateImage = null;
            }
            if (exc != null) {
                throw new FileNotFoundException("operation failed");
            }
        } else {
            boolean z = i2 == 3;
            synchronized (this) {
                try {
                    if (this.mClockImageCreator == null) {
                        this.mClockImageCreator = new ImageCreator[]{new ClockImageCreator(context)};
                    }
                } finally {
                }
            }
            boolean z2 = Settings.System.getInt(context.getContentResolver(), SettingsHelper.INDEX_LOCK_SHORTCUT_MASTER_ENABLED, 1) == 1;
            ActionBarContextView$$ExternalSyntheticOutline0.m(RowView$$ExternalSyntheticOutline0.m("getImageCreator isShortcutEnabled= ", ", wasShortcutEnabled= ", z2), this.mWasShortcutEnabled, "KeyguardImageProvider");
            boolean z3 = this.mWasShortcutEnabled != z2;
            this.mWasShortcutEnabled = z2;
            if (z2 || z) {
                synchronized (this) {
                    if (z2) {
                        try {
                            ImageCreator[] imageCreatorArr3 = this.mCreatorsForFixedShortcut;
                            if (imageCreatorArr3 == null || imageCreatorArr3.length < 3) {
                                i = 0;
                                this.mCreatorsForFixedShortcut = new ImageCreator[]{this.mClockImageCreator[0], new LeftShortcutImageCreator(context), new RightShortcutImageCreator(context)};
                            } else {
                                i = 0;
                                if (!z2 && this.mCreatorsForFixedShortcut == null) {
                                    this.mCreatorsForFixedShortcut = this.mClockImageCreator;
                                }
                            }
                            imageCreatorArr = this.mCreatorsForFixedShortcut;
                        } finally {
                        }
                    }
                }
                if (z) {
                    synchronized (this) {
                        try {
                            if (this.mCreatorsForWallpaper == null || z3) {
                                this.mCreatorsForWallpaper = null;
                                if (z2) {
                                    WallpaperImageProviderCreator wallpaperImageProviderCreator = new WallpaperImageProviderCreator(context);
                                    ImageCreator imageCreator = this.mClockImageCreator[i];
                                    ImageCreator[] imageCreatorArr4 = this.mCreatorsForFixedShortcut;
                                    this.mCreatorsForWallpaper = new ImageCreator[]{wallpaperImageProviderCreator, imageCreator, imageCreatorArr4[1], imageCreatorArr4[2]};
                                } else {
                                    this.mCreatorsForWallpaper = new ImageCreator[]{new WallpaperImageProviderCreator(context), this.mClockImageCreator[i]};
                                }
                            }
                            imageCreatorArr2 = this.mCreatorsForWallpaper;
                        } finally {
                        }
                    }
                } else {
                    imageCreatorArr2 = imageCreatorArr;
                }
            } else {
                synchronized (this) {
                    imageCreatorArr2 = this.mClockImageCreator;
                }
                i = 0;
            }
            str2 = imageOptionCreateImageOption.type == 3 ? "image/jpeg" : "image/png";
            bitmapCreateImage = Bitmap.createBitmap(imageOptionCreateImageOption.width, imageOptionCreateImageOption.height, Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(bitmapCreateImage);
            final LinkedBlockingDeque linkedBlockingDeque2 = new LinkedBlockingDeque(1);
            final int i4 = 1;
            this.mHandler.post(new Runnable() { // from class: com.android.systemui.keyguardimage.KeyguardImageProvider$$ExternalSyntheticLambda0
                @Override // java.lang.Runnable
                public final void run() throws InterruptedException {
                    switch (i4) {
                        case 0:
                            KeyguardImageProvider keyguardImageProvider = (KeyguardImageProvider) imageCreatorArr2;
                            ImageOptionCreator.ImageOption imageOption = imageOptionCreateImageOption;
                            BlockingDeque blockingDeque = linkedBlockingDeque2;
                            int i42 = KeyguardImageProvider.$r8$clinit;
                            keyguardImageProvider.getClass();
                            try {
                                blockingDeque.put(new ClockImageCreator(keyguardImageProvider.getContext()).createImage(imageOption, null));
                                break;
                            } catch (Exception e3) {
                                e3.printStackTrace();
                                return;
                            }
                        default:
                            ImageCreator[] imageCreatorArr32 = (ImageCreator[]) imageCreatorArr2;
                            ImageOptionCreator.ImageOption imageOption2 = imageOptionCreateImageOption;
                            BlockingDeque blockingDeque2 = linkedBlockingDeque2;
                            int i5 = KeyguardImageProvider.$r8$clinit;
                            int iStartTime = LogUtil.startTime(-1);
                            LinkedList linkedList = new LinkedList();
                            try {
                                for (ImageCreator imageCreator2 : imageCreatorArr32) {
                                    Point point = new Point();
                                    Bitmap bitmapCreateImage2 = imageCreator2.createImage(imageOption2, point);
                                    if (bitmapCreateImage2 != null && !bitmapCreateImage2.isRecycled()) {
                                        linkedList.add(new Pair(imageCreator2.createImage(imageOption2, point), point));
                                    }
                                }
                                blockingDeque2.put(linkedList);
                            } catch (InterruptedException e22) {
                                e22.printStackTrace();
                            }
                            LogUtil.endTime(iStartTime, "KeyguardImageProvider", "%d images were created", Integer.valueOf(linkedList.size()));
                            break;
                    }
                }
            });
            try {
                list = (List) linkedBlockingDeque2.poll(3000L, TimeUnit.MILLISECONDS);
                if (list != null) {
                    try {
                        if (list.size() > 0) {
                            for (Pair pair : list) {
                                Bitmap bitmap = (Bitmap) pair.first;
                                Object obj = pair.second;
                                canvas.drawBitmap(bitmap, (Rect) null, new Rect(((Point) obj).x, ((Point) obj).y, ((Point) obj).x + bitmap.getWidth(), ((Point) pair.second).y + bitmap.getHeight()), (Paint) null);
                            }
                            list.clear();
                        }
                    } catch (Exception e3) {
                        e = e3;
                        exc = e;
                        if (exc == null) {
                        }
                        StringBuilder sb = new StringBuilder("openFile failed ");
                        sb.append(exc == null ? exc.getMessage() : "");
                        Log.d("KeyguardImageProvider", sb.toString());
                        throw new FileNotFoundException("operation failed");
                    }
                }
            } catch (Exception e4) {
                e = e4;
                list = null;
            }
            if (exc == null || list == null) {
                StringBuilder sb2 = new StringBuilder("openFile failed ");
                sb2.append(exc == null ? exc.getMessage() : "");
                Log.d("KeyguardImageProvider", sb2.toString());
                throw new FileNotFoundException("operation failed");
            }
        }
        return openPipeHelper(uri, str2, null, bitmapCreateImage, new MyWriter(i));
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
