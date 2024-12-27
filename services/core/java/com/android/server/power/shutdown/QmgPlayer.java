package com.android.server.power.shutdown;

import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.display.DisplayManager;
import android.os.Handler;
import android.os.Message;
import android.util.DisplayMetrics;
import android.util.Pair;
import android.view.Display;
import com.android.server.power.LibQmg;
import com.android.server.power.Slog;
import com.samsung.android.view.SemWindowManager;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class QmgPlayer extends AnimationPlayer {
    public final Bitmap[] bitmapQ;
    public int bitmapQFront;
    public int bitmapQRear;
    public final Object drawBufferLock;
    public ImageLoader imageLoader;
    public DrawHandler mDrawHandler;
    public final List mainImages;
    public final LibQmg mainLoopImage;
    public final List subImages;
    public final LibQmg subLoopImage;
    public final Bitmap[] subbitmapQ;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class DrawHandler extends Handler {
        public boolean finished = false;
        public int maxSleep;

        public DrawHandler() {
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            int i = message.what;
            if (i > 100) {
                this.maxSleep = i - 100;
                return;
            }
            if (i == -1) {
                Slog.i("Shutdown-QmgPlayer", "image draw finish");
                this.finished = true;
                return;
            }
            QmgPlayer qmgPlayer = QmgPlayer.this;
            if (qmgPlayer.bitmapQRear == qmgPlayer.bitmapQFront) {
                if (this.finished) {
                    Slog.i("Shutdown-QmgPlayer", "image draw already finished");
                    return;
                } else {
                    Slog.e("Shutdown-QmgPlayer", "image buffer not ready");
                    QmgPlayer.this.mDrawHandler.sendEmptyMessageDelayed(0, this.maxSleep / 2);
                    return;
                }
            }
            qmgPlayer.mDrawHandler.sendEmptyMessageDelayed(0, this.maxSleep);
            QmgPlayer qmgPlayer2 = QmgPlayer.this;
            if (qmgPlayer2.hasSubResources) {
                int i2 = qmgPlayer2.context.getResources().getConfiguration().semDisplayDeviceType;
                boolean isFolded = SemWindowManager.getInstance().isFolded();
                Locale locale = Locale.ENGLISH;
                Slog.d("Shutdown-QmgPlayer", "image draw displayType[" + i2 + "] isFolded[" + isFolded + "]");
                if (i2 == 0) {
                    QmgPlayer qmgPlayer3 = QmgPlayer.this;
                    qmgPlayer3.mainImageView.setImageBitmap(qmgPlayer3.bitmapQ[qmgPlayer3.bitmapQRear]);
                } else if (i2 == 5) {
                    QmgPlayer qmgPlayer4 = QmgPlayer.this;
                    qmgPlayer4.mainImageView.setImageBitmap(qmgPlayer4.subbitmapQ[qmgPlayer4.bitmapQRear]);
                } else if (!isFolded) {
                    QmgPlayer qmgPlayer5 = QmgPlayer.this;
                    qmgPlayer5.mainImageView.setImageBitmap(qmgPlayer5.bitmapQ[qmgPlayer5.bitmapQRear]);
                } else if (isFolded) {
                    QmgPlayer qmgPlayer6 = QmgPlayer.this;
                    qmgPlayer6.subImageView.setImageBitmap(qmgPlayer6.subbitmapQ[qmgPlayer6.bitmapQRear]);
                    QmgPlayer qmgPlayer7 = QmgPlayer.this;
                    qmgPlayer7.subDialog.setContentView(qmgPlayer7.subImageView);
                    QmgPlayer.this.subDialog.show();
                }
            } else {
                qmgPlayer2.mainImageView.setImageBitmap(qmgPlayer2.bitmapQ[qmgPlayer2.bitmapQRear]);
            }
            synchronized (QmgPlayer.this.drawBufferLock) {
                QmgPlayer qmgPlayer8 = QmgPlayer.this;
                qmgPlayer8.bitmapQRear = (qmgPlayer8.bitmapQRear + 1) % 3;
                qmgPlayer8.drawBufferLock.notifyAll();
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ImageLoader implements Runnable {
        public boolean running = true;

        public ImageLoader() {
        }

        public final void frameLoadLoop(LibQmg libQmg) {
            int qmgLoadBitmap;
            libQmg.ensureQmgHandle();
            DrawHandler drawHandler = QmgPlayer.this.mDrawHandler;
            libQmg.ensureQmgHandle();
            int qmgGetDelayTime = LibQmg.qmgGetDelayTime(libQmg.handle);
            if (qmgGetDelayTime <= 0) {
                qmgGetDelayTime = 33;
            }
            drawHandler.sendEmptyMessage(qmgGetDelayTime + 100);
            Slog.i("Shutdown-QmgPlayer", "!@[frameLoadLoop] " + libQmg);
            int i = 0;
            do {
                try {
                    QmgPlayer qmgPlayer = QmgPlayer.this;
                    qmgLoadBitmap = LibQmg.qmgLoadBitmap(libQmg.handle, qmgPlayer.bitmapQ[qmgPlayer.bitmapQFront]);
                    if (qmgLoadBitmap > 0) {
                        i++;
                    }
                    if (qmgLoadBitmap >= 0) {
                        synchronized (QmgPlayer.this.drawBufferLock) {
                            QmgPlayer qmgPlayer2 = QmgPlayer.this;
                            int i2 = (qmgPlayer2.bitmapQFront + 1) % 3;
                            qmgPlayer2.bitmapQFront = i2;
                            if ((i2 + 1) % 3 == qmgPlayer2.bitmapQRear) {
                                try {
                                    qmgPlayer2.drawBufferLock.wait(5000L);
                                } catch (InterruptedException e) {
                                    Slog.e("Shutdown-QmgPlayer", "frameLoadLoop InterruptedException", e);
                                }
                            }
                        }
                    }
                } catch (IOException e2) {
                    Slog.e("Shutdown-QmgPlayer", "qmgLoadFrame return < 0", e2);
                }
            } while (qmgLoadBitmap > 0);
            long j = libQmg.handle;
            if (j != 0) {
                LibQmg.qmgClose(j);
            }
            Slog.i("Shutdown-QmgPlayer", "!@[frameLoadLoop] done " + i);
        }

        public final void multiFrameLoadLoop(LibQmg libQmg, LibQmg libQmg2) {
            int qmgLoadBitmap;
            libQmg.ensureQmgHandle();
            Slog.i("Shutdown-QmgPlayer", "!@[multiframeLoadLoop] startMain " + libQmg);
            libQmg2.ensureQmgHandle();
            Slog.i("Shutdown-QmgPlayer", "!@[multiframeLoadLoop] startSub " + libQmg2);
            DrawHandler drawHandler = QmgPlayer.this.mDrawHandler;
            libQmg.ensureQmgHandle();
            int qmgGetDelayTime = LibQmg.qmgGetDelayTime(libQmg.handle);
            if (qmgGetDelayTime <= 0) {
                qmgGetDelayTime = 33;
            }
            drawHandler.sendEmptyMessage(qmgGetDelayTime + 100);
            int i = 0;
            do {
                try {
                    QmgPlayer qmgPlayer = QmgPlayer.this;
                    qmgLoadBitmap = LibQmg.qmgLoadBitmap(libQmg.handle, qmgPlayer.bitmapQ[qmgPlayer.bitmapQFront]);
                    QmgPlayer qmgPlayer2 = QmgPlayer.this;
                    LibQmg.qmgLoadBitmap(libQmg2.handle, qmgPlayer2.subbitmapQ[qmgPlayer2.bitmapQFront]);
                    if (qmgLoadBitmap > 0) {
                        i++;
                    }
                    if (qmgLoadBitmap >= 0) {
                        synchronized (QmgPlayer.this.drawBufferLock) {
                            QmgPlayer qmgPlayer3 = QmgPlayer.this;
                            int i2 = (qmgPlayer3.bitmapQFront + 1) % 3;
                            qmgPlayer3.bitmapQFront = i2;
                            if ((i2 + 1) % 3 == qmgPlayer3.bitmapQRear) {
                                try {
                                    qmgPlayer3.drawBufferLock.wait(5000L);
                                } catch (InterruptedException e) {
                                    Slog.e("Shutdown-QmgPlayer", "InterruptedException", e);
                                }
                            }
                        }
                    }
                } catch (IOException e2) {
                    Slog.e("Shutdown-QmgPlayer", "multiframeLoadLoop qmgLoadFrame return < 0", e2);
                }
            } while (qmgLoadBitmap > 0);
            long j = libQmg.handle;
            if (j != 0) {
                LibQmg.qmgClose(j);
            }
            long j2 = libQmg2.handle;
            if (j2 != 0) {
                LibQmg.qmgClose(j2);
            }
            Slog.i("Shutdown-QmgPlayer", "!@[multiframeLoadLoop] done " + i);
        }

        @Override // java.lang.Runnable
        public final void run() {
            Slog.i("Shutdown-QmgPlayer", "!@ImageLoadThread.run(), qmgList.size = " + QmgPlayer.this.mainImages.size());
            QmgPlayer.this.mDrawHandler.sendEmptyMessage(0);
            while (QmgPlayer.this.mainImages.size() > 0) {
                try {
                    if (QmgPlayer.this.subImages.size() > 0) {
                        multiFrameLoadLoop((LibQmg) QmgPlayer.this.mainImages.get(0), (LibQmg) QmgPlayer.this.subImages.get(0));
                        QmgPlayer.this.mainImages.remove(0);
                        QmgPlayer.this.subImages.remove(0);
                    } else {
                        frameLoadLoop((LibQmg) QmgPlayer.this.mainImages.get(0));
                        QmgPlayer.this.mainImages.remove(0);
                    }
                } catch (IndexOutOfBoundsException e) {
                    Slog.e("Shutdown-QmgPlayer", "!@qmgList or subqmgList IndexOutOfBoundsException", e);
                }
            }
            this.running = false;
            QmgPlayer qmgPlayer = QmgPlayer.this;
            LibQmg libQmg = qmgPlayer.mainLoopImage;
            if (libQmg != null) {
                LibQmg libQmg2 = qmgPlayer.subLoopImage;
                if (libQmg2 != null) {
                    multiFrameLoadLoop(libQmg, libQmg2);
                } else {
                    frameLoadLoop(libQmg);
                }
            }
            QmgPlayer.this.mDrawHandler.sendEmptyMessage(-1);
        }
    }

    public QmgPlayer(Context context) {
        super(context, ".qmg");
        this.bitmapQ = new Bitmap[3];
        this.bitmapQFront = 0;
        this.bitmapQRear = 0;
        this.drawBufferLock = new Object();
        this.subbitmapQ = new Bitmap[3];
        this.imageLoader = null;
        this.mainImages = makeQmgList(this.resourceManager.mainImages);
        this.subImages = makeQmgList(this.resourceManager.subImages);
        File file = this.resourceManager.mainLoopImage;
        this.mainLoopImage = file != null ? new LibQmg(file.getAbsolutePath()) : null;
        File file2 = this.resourceManager.subLoopImage;
        this.subLoopImage = file2 != null ? new LibQmg(file2.getAbsolutePath()) : null;
    }

    public static List makeQmgList(List list) {
        int size = list.size();
        if (size <= 0) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(size);
        Iterator it = list.iterator();
        while (it.hasNext()) {
            arrayList.add(new LibQmg(((File) it.next()).getAbsolutePath()));
        }
        return arrayList;
    }

    @Override // com.android.server.power.shutdown.AnimationPlayer
    public final Pair getMainAnimationWidthHeight() {
        LibQmg libQmg = (LibQmg) this.mainImages.get(0);
        libQmg.ensureQmgHandle();
        Integer valueOf = Integer.valueOf(LibQmg.qmgGetWidth(libQmg.handle));
        libQmg.ensureQmgHandle();
        return Pair.create(valueOf, Integer.valueOf(LibQmg.qmgGetHeight(libQmg.handle)));
    }

    @Override // com.android.server.power.shutdown.PlayerInterface
    public final boolean isPlaying() {
        ImageLoader imageLoader = this.imageLoader;
        return imageLoader != null && imageLoader.running;
    }

    @Override // com.android.server.power.shutdown.PlayerInterface
    public final void prepare() {
        Slog.i("Shutdown-QmgPlayer", "prepare shutdown dialog image and sound");
        this.mDrawHandler = new DrawHandler();
        if (this.mainImages.size() > 0) {
            LibQmg libQmg = (LibQmg) this.mainImages.get(0);
            Slog.d("Shutdown-QmgPlayer", "!@[prepare] " + libQmg.toString());
            libQmg.ensureQmgHandle();
            int qmgGetWidth = LibQmg.qmgGetWidth(libQmg.handle);
            libQmg.ensureQmgHandle();
            int qmgGetHeight = LibQmg.qmgGetHeight(libQmg.handle);
            for (int i = 0; i < 3; i++) {
                Bitmap[] bitmapArr = this.bitmapQ;
                if (bitmapArr[i] == null) {
                    bitmapArr[i] = Bitmap.createBitmap(qmgGetWidth, qmgGetHeight, Bitmap.Config.RGB_565);
                }
            }
        } else {
            Slog.e("Shutdown-QmgPlayer", "qmglist error");
        }
        if (this.subImages.size() > 0) {
            this.hasSubResources = true;
            LibQmg libQmg2 = (LibQmg) this.subImages.get(0);
            Slog.d("Shutdown-QmgPlayer", "!@[prepare] subdisplay : " + libQmg2.toString());
            libQmg2.ensureQmgHandle();
            int qmgGetWidth2 = LibQmg.qmgGetWidth(libQmg2.handle);
            libQmg2.ensureQmgHandle();
            int qmgGetHeight2 = LibQmg.qmgGetHeight(libQmg2.handle);
            DisplayMetrics displayMetrics = new DisplayMetrics();
            Display display = ((DisplayManager) this.context.getSystemService("display")).getDisplay(1);
            if (display != null) {
                display.getMetrics(displayMetrics);
                for (int i2 = 0; i2 < 3; i2++) {
                    Bitmap[] bitmapArr2 = this.subbitmapQ;
                    if (bitmapArr2[i2] == null) {
                        bitmapArr2[i2] = Bitmap.createBitmap(displayMetrics, qmgGetWidth2, qmgGetHeight2, Bitmap.Config.RGB_565);
                    }
                }
            }
        } else {
            Slog.e("Shutdown-QmgPlayer", "no sub_shutdown.qmg");
        }
        if (isPlaying()) {
            Slog.w("Shutdown-QmgPlayer", "becareful prepare while draw");
        } else {
            this.mDrawHandler.finished = false;
        }
    }

    @Override // com.android.server.power.shutdown.AnimationPlayer
    public final void setView(ShutdownAnimatedImageView shutdownAnimatedImageView) {
        this.mainImageView = shutdownAnimatedImageView;
        this.subImageView = null;
        this.subDialog = null;
    }

    @Override // com.android.server.power.shutdown.PlayerInterface
    public final void start() {
        Slog.i("Shutdown-QmgPlayer", "!@StateDrawing.start()");
        this.imageLoader = new ImageLoader();
        new Thread(this.imageLoader).start();
    }
}
