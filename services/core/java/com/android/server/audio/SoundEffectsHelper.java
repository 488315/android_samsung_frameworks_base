package com.android.server.audio;

import android.content.Context;
import android.frameworks.vibrator.VibrationParam$1$$ExternalSyntheticOutline0;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.util.PrintWriterPrinter;

import com.android.server.accessibility.AccessibilityManagerService$$ExternalSyntheticOutline0;
import com.android.server.utils.EventLogger;

import com.samsung.android.audio.Rune;
import com.samsung.android.media.AudioFxHelper;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

public final class SoundEffectsHelper {
    public final Context mContext;
    public final List mDefaultResources;
    public final int[] mEffects;
    public final Consumer mPlayerAvailableCb;
    public String mPrevSystemSoundFromSoundTheme;
    public final int mSfxAttenuationDb;
    public SfxHandler mSfxHandler;
    public final SfxWorker mSfxWorker;
    public SoundPool mSoundPool;
    public SoundPoolLoader mSoundPoolLoader;
    public String mSystemSoundFromSoundTheme;
    public String mThemeTouchSoundPath;
    public final EventLogger mSfxLogger = new EventLogger(33, "Sound Effects Loading");
    public final List mResources = new ArrayList();
    public boolean mUpdateSystemSound = false;

    /* renamed from: com.android.server.audio.SoundEffectsHelper$1, reason: invalid class name */
    public final class AnonymousClass1 implements OnEffectsLoadCompleteHandler {
        public final /* synthetic */ int $r8$classId;
        public final /* synthetic */ SoundEffectsHelper this$0;

        public /* synthetic */ AnonymousClass1(SoundEffectsHelper soundEffectsHelper, int i) {
            this.$r8$classId = i;
            this.this$0 = soundEffectsHelper;
        }

        @Override // com.android.server.audio.SoundEffectsHelper.OnEffectsLoadCompleteHandler
        public final void run(boolean z) {
            switch (this.$r8$classId) {
                case 0:
                    SoundEffectsHelper soundEffectsHelper = this.this$0;
                    soundEffectsHelper.mSoundPoolLoader = null;
                    if (!z) {
                        Log.w("AS.SfxHelper", "onLoadSoundEffects(), Error while loading samples");
                        soundEffectsHelper.onUnloadSoundEffects();
                        break;
                    }
                    break;
                default:
                    this.this$0.onUnloadSoundEffects();
                    break;
            }
        }
    }

    /* renamed from: com.android.server.audio.SoundEffectsHelper$3, reason: invalid class name */
    public final class AnonymousClass3 implements MediaPlayer.OnCompletionListener {
        @Override // android.media.MediaPlayer.OnCompletionListener
        public final void onCompletion(MediaPlayer mediaPlayer) {
            SoundEffectsHelper.m289$$Nest$smcleanupPlayer(mediaPlayer);
        }
    }

    /* renamed from: com.android.server.audio.SoundEffectsHelper$4, reason: invalid class name */
    public final class AnonymousClass4 implements MediaPlayer.OnErrorListener {
        @Override // android.media.MediaPlayer.OnErrorListener
        public final boolean onError(MediaPlayer mediaPlayer, int i, int i2) {
            SoundEffectsHelper.m289$$Nest$smcleanupPlayer(mediaPlayer);
            return true;
        }
    }

    public interface OnEffectsLoadCompleteHandler {
        void run(boolean z);
    }

    public final class Resource {
        public final String mFileName;
        public boolean mHasSituationVolume;
        public boolean mLoaded;
        public int mSampleId = 0;

        public Resource(String str) {
            this.mFileName = str;
        }
    }

    public final class SfxHandler extends Handler {
        public SfxHandler() {}

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            SoundPoolLoader soundPoolLoader;
            int i = message.what;
            SoundEffectsHelper soundEffectsHelper = SoundEffectsHelper.this;
            if (i == 0) {
                SoundEffectsHelper.m288$$Nest$monLoadSoundEffects(
                        soundEffectsHelper, (OnEffectsLoadCompleteHandler) message.obj);
                return;
            }
            if (i == 1) {
                soundEffectsHelper.onUnloadSoundEffects();
                return;
            }
            if (i == 2) {
                SoundEffectsHelper.m288$$Nest$monLoadSoundEffects(
                        soundEffectsHelper,
                        new OnEffectsLoadCompleteHandler(
                                message.arg1, message.arg2) { // from class:
                            // com.android.server.audio.SoundEffectsHelper.SfxHandler.1
                            public final /* synthetic */ int val$effect;

                            @Override // com.android.server.audio.SoundEffectsHelper.OnEffectsLoadCompleteHandler
                            public final void run(boolean z) {
                                StringBuilder sb;
                                if (z) {
                                    SoundEffectsHelper soundEffectsHelper2 =
                                            SoundEffectsHelper.this;
                                    soundEffectsHelper2.getClass();
                                    int i2 = this.val$effect;
                                    float soundFxVolumeByType =
                                            AudioFxHelper.getSoundFxVolumeByType(i2);
                                    Resource resource =
                                            (Resource)
                                                    ((ArrayList) soundEffectsHelper2.mResources)
                                                            .get(soundEffectsHelper2.mEffects[i2]);
                                    SoundPool soundPool = soundEffectsHelper2.mSoundPool;
                                    if (soundPool != null
                                            && resource.mSampleId != 0
                                            && resource.mLoaded) {
                                        soundPool.semSetStreamType(1);
                                        if (i2 == AudioFxHelper.getPlaySoundTypeForSEP(101)) {
                                            soundEffectsHelper2.mSoundPool.semSetStreamType(
                                                    Rune.SEC_AUDIO_DEFAULT_STREAM_NOTIFICATION
                                                            ? 5
                                                            : 2);
                                        }
                                        soundEffectsHelper2.mSoundPool.play(
                                                resource.mSampleId,
                                                soundFxVolumeByType,
                                                soundFxVolumeByType,
                                                0,
                                                0,
                                                1.0f);
                                        return;
                                    }
                                    MediaPlayer mediaPlayer = new MediaPlayer();
                                    try {
                                        try {
                                            try {
                                                mediaPlayer.setDataSource(
                                                        soundEffectsHelper2.getResourceFilePath(
                                                                resource));
                                                mediaPlayer.setAudioStreamType(1);
                                                mediaPlayer.prepare();
                                                mediaPlayer.setVolume(soundFxVolumeByType);
                                                mediaPlayer.setOnCompletionListener(
                                                        new AnonymousClass3());
                                                mediaPlayer.setOnErrorListener(
                                                        new AnonymousClass4());
                                                mediaPlayer.start();
                                                try {
                                                    mediaPlayer.stop();
                                                    mediaPlayer.release();
                                                } catch (IllegalStateException e) {
                                                    e = e;
                                                    sb =
                                                            new StringBuilder(
                                                                    "MediaPlayer"
                                                                        + " IllegalStateException:"
                                                                        + " ");
                                                    sb.append(e);
                                                    Log.w("AS.SfxHelper", sb.toString());
                                                }
                                            } catch (IOException e2) {
                                                Log.w(
                                                        "AS.SfxHelper",
                                                        "MediaPlayer IOException: " + e2);
                                                try {
                                                    mediaPlayer.stop();
                                                    mediaPlayer.release();
                                                } catch (IllegalStateException e3) {
                                                    e = e3;
                                                    sb =
                                                            new StringBuilder(
                                                                    "MediaPlayer"
                                                                        + " IllegalStateException:"
                                                                        + " ");
                                                    sb.append(e);
                                                    Log.w("AS.SfxHelper", sb.toString());
                                                }
                                            } catch (IllegalArgumentException e4) {
                                                Log.w(
                                                        "AS.SfxHelper",
                                                        "MediaPlayer IllegalArgumentException: "
                                                                + e4);
                                                try {
                                                    mediaPlayer.stop();
                                                    mediaPlayer.release();
                                                } catch (IllegalStateException e5) {
                                                    e = e5;
                                                    sb =
                                                            new StringBuilder(
                                                                    "MediaPlayer"
                                                                        + " IllegalStateException:"
                                                                        + " ");
                                                    sb.append(e);
                                                    Log.w("AS.SfxHelper", sb.toString());
                                                }
                                            }
                                        } catch (IllegalStateException e6) {
                                            Log.w(
                                                    "AS.SfxHelper",
                                                    "MediaPlayer IllegalStateException: " + e6);
                                            try {
                                                mediaPlayer.stop();
                                                mediaPlayer.release();
                                            } catch (IllegalStateException e7) {
                                                e = e7;
                                                sb =
                                                        new StringBuilder(
                                                                "MediaPlayer IllegalStateException:"
                                                                    + " ");
                                                sb.append(e);
                                                Log.w("AS.SfxHelper", sb.toString());
                                            }
                                        }
                                    } catch (Throwable th) {
                                        try {
                                            mediaPlayer.stop();
                                            mediaPlayer.release();
                                        } catch (IllegalStateException e8) {
                                            Log.w(
                                                    "AS.SfxHelper",
                                                    "MediaPlayer IllegalStateException: " + e8);
                                        }
                                        throw th;
                                    }
                                }
                            }
                        });
            } else if (i == 3 && (soundPoolLoader = soundEffectsHelper.mSoundPoolLoader) != null) {
                soundPoolLoader.onComplete(false);
            }
        }
    }

    public final class SfxWorker extends Thread {
        public SfxWorker() {
            super("AS.SfxWorker");
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            Looper.prepare();
            synchronized (SoundEffectsHelper.this) {
                SoundEffectsHelper.this.mSfxHandler = SoundEffectsHelper.this.new SfxHandler();
                SoundEffectsHelper.this.notify();
            }
            Looper.loop();
        }
    }

    public final class SoundPoolLoader implements SoundPool.OnLoadCompleteListener {
        public final List mLoadCompleteHandlers = new ArrayList();

        public SoundPoolLoader() {
            SoundEffectsHelper.this.mSoundPool.setOnLoadCompleteListener(this);
        }

        public final void onComplete(boolean z) {
            SoundPool soundPool = SoundEffectsHelper.this.mSoundPool;
            if (soundPool != null) {
                soundPool.setOnLoadCompleteListener(null);
            }
            Iterator it = ((ArrayList) this.mLoadCompleteHandlers).iterator();
            while (it.hasNext()) {
                ((OnEffectsLoadCompleteHandler) it.next()).run(z);
            }
            SoundEffectsHelper.this.logEvent("effects loading ".concat(z ? "completed" : "failed"));
        }

        @Override // android.media.SoundPool.OnLoadCompleteListener
        public final void onLoadComplete(SoundPool soundPool, int i, int i2) {
            Resource resource;
            int i3 = 0;
            if (i2 == 0) {
                Iterator it = ((ArrayList) SoundEffectsHelper.this.mResources).iterator();
                while (it.hasNext()) {
                    Resource resource2 = (Resource) it.next();
                    if (resource2.mSampleId == i && !resource2.mLoaded) {
                        SoundEffectsHelper.this.logEvent(
                                "effect " + resource2.mFileName + " loaded");
                        resource2.mLoaded = true;
                        if (Rune.SEC_AUDIO_EXTENSION_SITUATION_VOLUME
                                && resource2.mHasSituationVolume) {
                            SoundEffectsHelper.this.mSoundPool.semSetSituationType(
                                    i, "stv_touch_tone");
                        }
                    }
                    if (resource2.mSampleId != 0 && !resource2.mLoaded) {
                        i3++;
                    }
                }
                if (i3 == 0) {
                    onComplete(true);
                    return;
                }
                return;
            }
            Iterator it2 = ((ArrayList) SoundEffectsHelper.this.mResources).iterator();
            while (true) {
                if (!it2.hasNext()) {
                    resource = null;
                    break;
                } else {
                    resource = (Resource) it2.next();
                    if (resource.mSampleId == i) {
                        break;
                    }
                }
            }
            String resourceFilePath =
                    resource != null
                            ? SoundEffectsHelper.this.getResourceFilePath(resource)
                            : VibrationParam$1$$ExternalSyntheticOutline0.m(
                                    i, "with unknown sample ID ");
            SoundEffectsHelper.this.logEvent(
                    "effect " + resourceFilePath + " loading failed, status " + i2);
            Log.w(
                    "AS.SfxHelper",
                    "onLoadSoundEffects(), Error "
                            + i2
                            + " while loading sample "
                            + resourceFilePath);
            onComplete(false);
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(11:(3:141|142|(11:144|25|26|(4:(2:28|(3:30|(3:71|72|(3:74|75|76)(1:77))(3:32|33|(8:35|36|37|39|40|(1:42)|43|(3:48|49|50))(1:56))|51)(2:78|79))|57|(1:59)(1:70)|(3:62|(1:65)|(1:68)))|80|81|(1:83)(1:114)|84|(6:87|(4:89|(1:106)(1:93)|94|(1:96))(1:107)|97|(3:103|104|105)(3:99|100|101)|102|85)|108|(2:110|111)(2:112|113)))|25|26|(0)|80|81|(0)(0)|84|(1:85)|108|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:119:0x0203, code lost:

       if (r5 != null) goto L81;
    */
    /* JADX WARN: Code restructure failed: missing block: B:124:0x01ef, code lost:

       if (r5 != null) goto L81;
    */
    /* JADX WARN: Code restructure failed: missing block: B:135:0x0121, code lost:

       r3 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:136:0x0122, code lost:

       r5 = r0;
       r0 = r3;
    */
    /* JADX WARN: Code restructure failed: missing block: B:137:0x0117, code lost:

       r3 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:138:0x0118, code lost:

       r5 = r0;
       r0 = r3;
    */
    /* JADX WARN: Code restructure failed: missing block: B:139:0x011c, code lost:

       r3 = move-exception;
    */
    /* JADX WARN: Code restructure failed: missing block: B:140:0x011d, code lost:

       r5 = r0;
       r0 = r3;
    */
    /* JADX WARN: Code restructure failed: missing block: B:148:0x0078, code lost:

       if (((java.util.ArrayList) r14.mResources).isEmpty() == false) goto L88;
    */
    /* JADX WARN: Removed duplicated region for block: B:110:0x02ea  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x02f5  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x0226  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x01fc  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x00da A[Catch: all -> 0x0113, IOException -> 0x0117, XmlPullParserException -> 0x011c, NotFoundException -> 0x0121, LOOP:0: B:28:0x00da->B:51:0x00da, LOOP_START, TryCatch #3 {all -> 0x0113, blocks: (B:26:0x00c0, B:28:0x00da, B:57:0x01b1, B:62:0x01ce, B:65:0x01dc, B:68:0x01e3, B:30:0x00e5, B:72:0x00ee, B:75:0x00fe, B:33:0x0126, B:35:0x012f, B:37:0x013d, B:40:0x014b, B:42:0x0167, B:43:0x017b, B:46:0x0185, B:49:0x018b, B:54:0x019b), top: B:25:0x00c0 }] */
    /* JADX WARN: Removed duplicated region for block: B:83:0x021e  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0238  */
    /* renamed from: -$$Nest$monLoadSoundEffects, reason: not valid java name */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public static void m288$$Nest$monLoadSoundEffects(
            com.android.server.audio.SoundEffectsHelper r14,
            com.android.server.audio.SoundEffectsHelper.OnEffectsLoadCompleteHandler r15) {
        /*
            Method dump skipped, instructions count: 775
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.audio.SoundEffectsHelper.m288$$Nest$monLoadSoundEffects(com.android.server.audio.SoundEffectsHelper,"
                    + " com.android.server.audio.SoundEffectsHelper$OnEffectsLoadCompleteHandler):void");
    }

    /* renamed from: -$$Nest$smcleanupPlayer, reason: not valid java name */
    public static void m289$$Nest$smcleanupPlayer(MediaPlayer mediaPlayer) {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.stop();
                mediaPlayer.release();
            } catch (IllegalStateException e) {
                Log.w("AS.SfxHelper", "MediaPlayer IllegalStateException: " + e);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:49:0x0141, code lost:

       if (r1 == null) goto L71;
    */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x012d, code lost:

       if (r1 == null) goto L71;
    */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0155 A[EXC_TOP_SPLITTER, LOOP:2: B:72:0x0155->B:79:0x0155, LOOP_START, SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public SoundEffectsHelper(
            android.content.Context r7,
            com.android.server.audio.AudioService$$ExternalSyntheticLambda16 r8) {
        /*
            Method dump skipped, instructions count: 389
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.audio.SoundEffectsHelper.<init>(android.content.Context,"
                    + " com.android.server.audio.AudioService$$ExternalSyntheticLambda16):void");
    }

    public static boolean allNavigationRepeatSoundsParsed(Map map) {
        HashMap hashMap = (HashMap) map;
        return ((Integer) hashMap.getOrDefault(15, 0)).intValue()
                        + (((Integer) hashMap.getOrDefault(14, 0)).intValue()
                                + (((Integer) hashMap.getOrDefault(13, 0)).intValue()
                                        + ((Integer) hashMap.getOrDefault(12, 0)).intValue()))
                == 4;
    }

    public final void dump(PrintWriter printWriter) {
        if (this.mSfxHandler != null) {
            printWriter.println("  Message handler (watch for unhandled messages):");
            this.mSfxHandler.dump(new PrintWriterPrinter(printWriter), "  ");
        } else {
            printWriter.println("  Message handler is null");
        }
        AccessibilityManagerService$$ExternalSyntheticOutline0.m(
                new StringBuilder("  Default attenuation (dB): "),
                this.mSfxAttenuationDb,
                printWriter);
        this.mSfxLogger.dump(printWriter);
    }

    public final int findOrAddResourceByFileName(String str) {
        for (int i = 0; i < ((ArrayList) this.mResources).size(); i++) {
            if (((Resource) ((ArrayList) this.mResources).get(i)).mFileName.equals(str)) {
                return i;
            }
        }
        int size = ((ArrayList) this.mResources).size();
        ((ArrayList) this.mResources).add(new Resource(str));
        return size;
    }

    public final String getResourceFilePath(Resource resource) {
        String str;
        if (Rune.SEC_AUDIO_SUPPORT_SOUND_THEME
                && TextUtils.equals(this.mSystemSoundFromSoundTheme, "Open_theme")) {
            str = this.mThemeTouchSoundPath + resource.mFileName;
        } else {
            str = Environment.getProductDirectory() + "/media/audio/ui/" + resource.mFileName;
        }
        if (new File(str).isFile()) {
            return str;
        }
        return Environment.getRootDirectory() + "/media/audio/ui/" + resource.mFileName;
    }

    /* JADX WARN: Code restructure failed: missing block: B:16:0x004e, code lost:

       if (android.text.TextUtils.equals(r8.mPrevSystemSoundFromSoundTheme, "Retro") != false) goto L10;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.content.res.XmlResourceParser getSoundThemeXmlParser() {
        /*
            r8 = this;
            java.lang.String r0 = r8.mSystemSoundFromSoundTheme
            java.lang.String r1 = "Calm"
            boolean r0 = android.text.TextUtils.equals(r0, r1)
            r2 = 18284546(0x1170002, float:2.7734325E-38)
            if (r0 == 0) goto Le
            goto L54
        Le:
            java.lang.String r0 = r8.mSystemSoundFromSoundTheme
            java.lang.String r3 = "Fun"
            boolean r0 = android.text.TextUtils.equals(r0, r3)
            r4 = 18284547(0x1170003, float:2.7734328E-38)
            if (r0 == 0) goto L1d
        L1b:
            r2 = r4
            goto L54
        L1d:
            java.lang.String r0 = r8.mSystemSoundFromSoundTheme
            java.lang.String r5 = "Retro"
            boolean r0 = android.text.TextUtils.equals(r0, r5)
            r6 = 18284548(0x1170004, float:2.773433E-38)
            if (r0 == 0) goto L2c
        L2a:
            r2 = r6
            goto L54
        L2c:
            java.lang.String r0 = r8.mSystemSoundFromSoundTheme
            java.lang.String r7 = "Open_theme"
            boolean r0 = android.text.TextUtils.equals(r0, r7)
            if (r0 == 0) goto L51
            java.lang.String r0 = r8.mPrevSystemSoundFromSoundTheme
            boolean r0 = android.text.TextUtils.equals(r0, r1)
            if (r0 == 0) goto L3f
            goto L54
        L3f:
            java.lang.String r0 = r8.mPrevSystemSoundFromSoundTheme
            boolean r0 = android.text.TextUtils.equals(r0, r3)
            if (r0 == 0) goto L48
            goto L1b
        L48:
            java.lang.String r0 = r8.mPrevSystemSoundFromSoundTheme
            boolean r0 = android.text.TextUtils.equals(r0, r5)
            if (r0 == 0) goto L51
            goto L2a
        L51:
            r2 = 18284545(0x1170001, float:2.7734323E-38)
        L54:
            android.content.Context r8 = r8.mContext
            android.content.res.Resources r8 = r8.getResources()
            android.content.res.XmlResourceParser r8 = r8.getXml(r2)
            return r8
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.audio.SoundEffectsHelper.getSoundThemeXmlParser():android.content.res.XmlResourceParser");
    }

    public final void loadSoundEffects(AudioService.LoadSoundEffectReply loadSoundEffectReply) {
        sendMsg(0, 0, 0, loadSoundEffectReply, 0);
    }

    public final void logEvent(String str) {
        this.mSfxLogger.enqueue(new EventLogger.StringEvent(str));
    }

    public final void onUnloadSoundEffects() {
        if (this.mSoundPool == null) {
            return;
        }
        SoundPoolLoader soundPoolLoader = this.mSoundPoolLoader;
        if (soundPoolLoader != null) {
            ((ArrayList) soundPoolLoader.mLoadCompleteHandlers).add(new AnonymousClass1(this, 1));
        }
        logEvent("effects unloading started");
        Iterator it = ((ArrayList) this.mResources).iterator();
        while (it.hasNext()) {
            Resource resource = (Resource) it.next();
            int i = resource.mSampleId;
            if (i != 0) {
                this.mSoundPool.unload(i);
                resource.mSampleId = 0;
                resource.mLoaded = false;
            }
        }
        this.mSoundPool.release();
        this.mSoundPool = null;
        logEvent("effects unloading completed");
    }

    public final void playSoundEffect(int i, int i2) {
        sendMsg(2, i, i2, null, 0);
    }

    public final void sendMsg(
            int i, int i2, int i3, AudioService.LoadSoundEffectReply loadSoundEffectReply, int i4) {
        SfxHandler sfxHandler = this.mSfxHandler;
        sfxHandler.sendMessageDelayed(
                sfxHandler.obtainMessage(i, i2, i3, loadSoundEffectReply), i4);
    }

    public final void unloadSoundEffects() {
        sendMsg(1, 0, 0, null, 0);
    }
}
