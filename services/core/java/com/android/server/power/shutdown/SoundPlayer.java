package com.android.server.power.shutdown;

import android.content.Context;
import android.media.AudioSystem;
import android.media.MediaPlayer;
import android.util.Slog;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class SoundPlayer implements PlayerInterface {
    public final Context context;
    public boolean silentShutdown = false;
    public SoundThread soundThread;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.power.shutdown.SoundPlayer$1, reason: invalid class name */
    public final class AnonymousClass1 extends Thread {
        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                Slog.e("Shutdown-SoundPlayer", "InterruptedException", e);
            }
            AudioSystem.setParameters("g_shutdown_suspend=suspend");
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class SoundThread extends Thread implements MediaPlayer.OnCompletionListener {
        public final MediaPlayer mediaPlayer;
        public final CountDownLatch completeSignal = new CountDownLatch(1);
        public boolean running = true;

        public SoundThread(MediaPlayer mediaPlayer) {
            this.mediaPlayer = mediaPlayer;
        }

        @Override // android.media.MediaPlayer.OnCompletionListener
        public final void onCompletion(MediaPlayer mediaPlayer) {
            Slog.i("Shutdown-SoundPlayer", "!@onCompletion(MediaPlayer arg0) called !!");
            this.completeSignal.countDown();
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            MediaPlayer mediaPlayer = this.mediaPlayer;
            if (mediaPlayer == null) {
                Slog.i("Shutdown-SoundPlayer", "MediaPlayer is null");
                return;
            }
            mediaPlayer.setOnCompletionListener(this);
            Slog.i("Shutdown-SoundPlayer", "Start play sound file");
            try {
                this.mediaPlayer.start();
            } catch (IllegalStateException unused) {
                Slog.e("Shutdown-SoundPlayer", "sound thread IllegalStateException");
            } catch (Exception unused2) {
                Slog.e("Shutdown-SoundPlayer", "sound thread exception");
            }
            try {
                Slog.i(
                        "Shutdown-SoundPlayer",
                        "Set sound complete audioParam awaitResult = "
                                + this.completeSignal.await(
                                        this.mediaPlayer.getDuration() * 2, TimeUnit.MILLISECONDS));
                AudioSystem.setParameters("g_shutdown_suspend=suspend");
                Slog.i("Shutdown-SoundPlayer", "H/W workaround. wait 1000ms before power off");
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                Slog.e("Shutdown-SoundPlayer", "Wait fail", e);
            }
            this.running = false;
            Slog.i("Shutdown-SoundPlayer", "Shutdown sound done");
        }
    }

    public SoundPlayer(Context context) {
        this.context = context;
    }

    @Override // com.android.server.power.shutdown.PlayerInterface
    public final boolean isPlaying() {
        SoundThread soundThread = this.soundThread;
        return soundThread != null && soundThread.running;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:18:0x013e  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0143  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x01e6  */
    /* JADX WARN: Type inference failed for: r11v1, types: [boolean] */
    @Override // com.android.server.power.shutdown.PlayerInterface
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void prepare() {
        /*
            Method dump skipped, instructions count: 503
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.power.shutdown.SoundPlayer.prepare():void");
    }

    @Override // com.android.server.power.shutdown.PlayerInterface
    public final void start() {
        SoundThread soundThread = this.soundThread;
        if (soundThread != null) {
            soundThread.start();
        }
    }
}
