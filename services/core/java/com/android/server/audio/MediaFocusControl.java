package com.android.server.audio;

import android.content.ContentResolver;
import android.content.Context;
import android.hardware.audio.common.V2_0.AudioOffloadInfo$$ExternalSyntheticOutline0;
import android.media.AudioAttributes;
import android.media.AudioFocusInfo;
import android.media.AudioManager;
import android.media.AudioSystem;
import android.media.IAudioFocusDispatcher;
import android.media.MediaMetrics;
import android.media.audiopolicy.Flags;
import android.media.audiopolicy.IAudioPolicyCallback;
import android.os.Binder;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.provider.Settings;
import android.text.TextUtils;
import android.util.Log;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.BinaryTransparencyService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptServiceHelper$$ExternalSyntheticOutline0;
import com.android.server.ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0;
import com.android.server.KnoxCaptureInputFilter$$ExternalSyntheticOutline0;
import com.android.server.NetworkScorerAppManager$$ExternalSyntheticOutline0;
import com.android.server.VpnManagerService$$ExternalSyntheticOutline0;
import com.android.server.utils.EventLogger;
import com.samsung.android.audio.AudioManagerHelper;
import com.samsung.android.audio.Rune;
import com.samsung.android.knoxguard.service.utils.Constants;
import com.samsung.android.media.MySpaceManager;
import com.samsung.android.media.SemAudioSystem;
import com.samsung.android.server.audio.AudioSettingsHelper;
import com.samsung.android.server.audio.ScreenSharingHelper;
import com.samsung.android.server.audio.utils.AudioUtils;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class MediaFocusControl implements PlayerFocusEnforcer {
    public final AudioService mAudioService;
    public final AudioSettingsHelper mAudioSettingsHelper;
    public int mDevice;
    public long mExtFocusChangeCounter;
    public final PlayerFocusEnforcer mFocusEnforcer;
    public final AnonymousClass4 mFocusHandler;
    public Stack mFocusStack;
    public final HandlerThread mFocusThread;
    public boolean mMultiAudioFocusEnabled;
    public final MultiFocusStack mMultiFocusStack;
    public final Handler mMySpaceHandler;
    public static final Object mAudioFocusLock = new Object();
    public static final EventLogger mEventLogger = new EventLogger(50, "focus commands as seen by MediaFocusControl");
    public static final int[] USAGES_TO_MUTE_IN_RING_OR_CALL = {1, 14};
    public boolean mRingOrCallActive = false;
    public final Object mExtFocusChangeLock = new Object();
    public ArrayList mMultiAudioFocusList = new ArrayList();
    public boolean mNotifyFocusOwnerOnDuck = true;
    public final ArrayList mFocusFollowers = new ArrayList();
    public IAudioPolicyCallback mFocusPolicy = null;
    public IAudioPolicyCallback mPreviousFocusPolicy = null;
    public final HashMap mFocusOwnersForFocusPolicy = new HashMap();
    public IBinder mFocusFreezerForTest = null;
    public AnonymousClass2 mFocusFreezerDeathHandler = null;
    public int[] mFocusFreezeExemptUids = null;
    public boolean mIsMySpaceEffectFocus = false;
    public final MediaFocusControl$$ExternalSyntheticLambda0 mMySpaceRunnable = new MediaFocusControl$$ExternalSyntheticLambda0();
    public String mCallClientId = null;
    public FocusRequester mIgnoredFocus = null;
    public int mIgnoredUid = -1;
    public FocusRequester mSplitSoundFR = null;
    public IBinder mSplitSoundCb = null;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    /* renamed from: com.android.server.audio.MediaFocusControl$3, reason: invalid class name */
    public final class AnonymousClass3 extends Thread {
        public final /* synthetic */ boolean val$enteringRingOrCall;

        public AnonymousClass3(boolean z) {
            this.val$enteringRingOrCall = z;
        }

        @Override // java.lang.Thread, java.lang.Runnable
        public final void run() {
            if (this.val$enteringRingOrCall) {
                try {
                    Thread.sleep(100L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            synchronized (MediaFocusControl.mAudioFocusLock) {
                try {
                    MediaFocusControl mediaFocusControl = MediaFocusControl.this;
                    if (mediaFocusControl.mRingOrCallActive) {
                        mediaFocusControl.mFocusEnforcer.mutePlayersForCall(MediaFocusControl.USAGES_TO_MUTE_IN_RING_OR_CALL);
                    } else {
                        mediaFocusControl.mFocusEnforcer.unmutePlayersForCall();
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class AudioFocusDeathHandler implements IBinder.DeathRecipient {
        public final IBinder mCb;

        public AudioFocusDeathHandler(IBinder iBinder) {
            this.mCb = iBinder;
        }

        @Override // android.os.IBinder.DeathRecipient
        public final void binderDied() {
            synchronized (MediaFocusControl.mAudioFocusLock) {
                try {
                    MediaFocusControl mediaFocusControl = MediaFocusControl.this;
                    if (mediaFocusControl.mFocusPolicy != null) {
                        MediaFocusControl.m284$$Nest$mremoveFocusEntryForExtPolicyOnDeath(mediaFocusControl, this.mCb);
                    } else {
                        for (int i = 0; i < MediaFocusControl.this.mMultiFocusStack.size(); i++) {
                            MediaFocusControl mediaFocusControl2 = MediaFocusControl.this;
                            mediaFocusControl2.mFocusStack = (Stack) mediaFocusControl2.mMultiFocusStack.valueAt(i);
                            MediaFocusControl.m285$$Nest$mremoveFocusStackEntryOnDeath(MediaFocusControl.this, this.mCb);
                        }
                        MediaFocusControl mediaFocusControl3 = MediaFocusControl.this;
                        mediaFocusControl3.mFocusStack = mediaFocusControl3.mMultiFocusStack.getStackForDevice(0);
                        if (!MediaFocusControl.this.mMultiAudioFocusList.isEmpty()) {
                            Iterator it = MediaFocusControl.this.mMultiAudioFocusList.iterator();
                            while (it.hasNext()) {
                                FocusRequester focusRequester = (FocusRequester) it.next();
                                if (focusRequester.hasSameBinder(this.mCb)) {
                                    it.remove();
                                    focusRequester.release();
                                }
                            }
                        }
                        FocusRequester focusRequester2 = MediaFocusControl.this.mIgnoredFocus;
                        if (focusRequester2 != null && focusRequester2.hasSameBinder(this.mCb)) {
                            MediaFocusControl.this.mIgnoredFocus.release();
                            MediaFocusControl.this.mIgnoredFocus = null;
                        }
                    }
                } catch (Throwable th) {
                    throw th;
                }
            }
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class ForgetFadeUidInfo {
        public final int mUid;

        public ForgetFadeUidInfo(int i) {
            this.mUid = i;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return obj != null && ForgetFadeUidInfo.class == obj.getClass() && ((ForgetFadeUidInfo) obj).mUid == this.mUid;
        }

        public final int hashCode() {
            return this.mUid;
        }
    }

    /* renamed from: -$$Nest$mremoveFocusEntryForExtPolicyOnDeath, reason: not valid java name */
    public static void m284$$Nest$mremoveFocusEntryForExtPolicyOnDeath(MediaFocusControl mediaFocusControl, IBinder iBinder) {
        if (mediaFocusControl.mFocusOwnersForFocusPolicy.isEmpty()) {
            return;
        }
        Iterator it = mediaFocusControl.mFocusOwnersForFocusPolicy.entrySet().iterator();
        while (it.hasNext()) {
            FocusRequester focusRequester = (FocusRequester) ((Map.Entry) it.next()).getValue();
            if (focusRequester.hasSameBinder(iBinder)) {
                it.remove();
                StringBuilder sb = new StringBuilder("focus requester:");
                sb.append(focusRequester.mClientId);
                sb.append(" in uid:");
                sb.append(focusRequester.mCallingUid);
                sb.append(" pack:");
                mEventLogger.enqueue(new EventLogger.StringEvent(AudioOffloadInfo$$ExternalSyntheticOutline0.m(sb, focusRequester.mPackageName, " died")));
                focusRequester.release();
                mediaFocusControl.notifyExtFocusPolicyFocusAbandon_syncAf(focusRequester.toAudioFocusInfo());
                return;
            }
        }
    }

    /* renamed from: -$$Nest$mremoveFocusStackEntryOnDeath, reason: not valid java name */
    public static void m285$$Nest$mremoveFocusStackEntryOnDeath(MediaFocusControl mediaFocusControl, IBinder iBinder) {
        boolean z = !mediaFocusControl.mFocusStack.isEmpty() && ((FocusRequester) mediaFocusControl.mFocusStack.peek()).hasSameBinder(iBinder);
        Iterator it = mediaFocusControl.mFocusStack.iterator();
        while (it.hasNext()) {
            FocusRequester focusRequester = (FocusRequester) it.next();
            if (focusRequester.hasSameBinder(iBinder)) {
                Log.i("MediaFocusControl", "AudioFocus  removeFocusStackEntryOnDeath(): removing entry for " + iBinder);
                String str = focusRequester.mPackageName;
                if ("com.sec.android.app.voicenote".equals(str) || "com.sec.android.app.voicerecorder".equals(str)) {
                    Log.i("MediaFocusControl", "FORCE_NONE for Media");
                    AudioSystemAdapter.getDefaultAdapter().setForceUse(1, 0);
                } else if ("com.sec.android.app.dmb".equals(str)) {
                    SemAudioSystem.setPolicyParameters("g_dmb_spk_enable=off");
                }
                mEventLogger.enqueue(new EventLogger.StringEvent("focus requester:" + focusRequester.mClientId + " in uid:" + focusRequester.mCallingUid + " pack:" + str + " died"));
                mediaFocusControl.notifyExtPolicyFocusLoss_syncAf(focusRequester.toAudioFocusInfo(), false);
                it.remove();
                focusRequester.release();
            }
        }
        if (z) {
            mediaFocusControl.notifyTopOfAudioFocusStack();
        }
    }

    /* JADX WARN: Type inference failed for: r6v4, types: [com.android.server.audio.MediaFocusControl$4] */
    public MediaFocusControl(Context context, PlaybackActivityMonitor playbackActivityMonitor, AudioService audioService) {
        this.mMultiAudioFocusEnabled = false;
        this.mFocusEnforcer = playbackActivityMonitor;
        ContentResolver contentResolver = context.getContentResolver();
        this.mMultiAudioFocusEnabled = Settings.System.getIntForUser(contentResolver, "multi_audio_focus_enabled", 0, contentResolver.getUserId()) != 0;
        HandlerThread handlerThread = new HandlerThread("MediaFocusControl");
        this.mFocusThread = handlerThread;
        handlerThread.start();
        this.mFocusHandler = new Handler(this.mFocusThread.getLooper()) { // from class: com.android.server.audio.MediaFocusControl.4
            @Override // android.os.Handler
            public final void handleMessage(Message message) {
                int i = message.what;
                if (i == 1) {
                    VpnManagerService$$ExternalSyntheticOutline0.m(new StringBuilder("MSG_L_FOCUS_LOSS_AFTER_FADE loser="), ((FocusRequester) message.obj).mPackageName, "MediaFocusControl");
                    synchronized (MediaFocusControl.mAudioFocusLock) {
                        try {
                            FocusRequester focusRequester = (FocusRequester) message.obj;
                            if (focusRequester.mFocusLossFadeLimbo) {
                                focusRequester.dispatchFocusChange(-1);
                                focusRequester.release();
                                MediaFocusControl mediaFocusControl = MediaFocusControl.this;
                                AnonymousClass4 anonymousClass4 = mediaFocusControl.mFocusHandler;
                                anonymousClass4.sendMessageDelayed(anonymousClass4.obtainMessage(2, new ForgetFadeUidInfo(focusRequester.mCallingUid)), mediaFocusControl.getFadeInDelayForOffendersMillis(focusRequester.mAttributes));
                            }
                        } finally {
                        }
                    }
                    return;
                }
                if (i == 2) {
                    int i2 = ((ForgetFadeUidInfo) message.obj).mUid;
                    NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i2, "MSL_L_FORGET_UID uid=", "MediaFocusControl");
                    MediaFocusControl.this.mFocusEnforcer.forgetUid(i2);
                    return;
                }
                if (i != 3) {
                    if (i != 4) {
                        return;
                    }
                    synchronized (MediaFocusControl.mAudioFocusLock) {
                        try {
                            FocusRequester focusRequester2 = (FocusRequester) message.obj;
                            if (!MediaFocusControl.this.mFocusStack.empty() && ((FocusRequester) MediaFocusControl.this.mFocusStack.peek()).hasSameClient(focusRequester2.mClientId)) {
                                Log.d("MediaFocusControl", "handleMessage: MSG_DELAY_GAIN_AUDIO_FOCUS clientId = " + focusRequester2.mClientId);
                                ((FocusRequester) MediaFocusControl.this.mFocusStack.peek()).handleFocusGain();
                            }
                        } finally {
                        }
                    }
                    return;
                }
                synchronized (MediaFocusControl.mAudioFocusLock) {
                    try {
                        FocusRequester focusRequester3 = (FocusRequester) message.obj;
                        if (!MediaFocusControl.this.mFocusStack.empty() && !((FocusRequester) MediaFocusControl.this.mFocusStack.peek()).hasSameClient(focusRequester3.mClientId)) {
                            Iterator it = MediaFocusControl.this.mFocusStack.iterator();
                            while (it.hasNext()) {
                                FocusRequester focusRequester4 = (FocusRequester) it.next();
                                if (focusRequester4.hasSameClient(focusRequester3.mClientId)) {
                                    Log.d("MediaFocusControl", "handleMessage: MSG_DELAY_LOSS_AUDIO_FOCUS clientId = " + focusRequester3.mClientId);
                                    it.remove();
                                    focusRequester4.handleFocusLoss(-1, null, false);
                                    focusRequester4.release();
                                }
                            }
                        }
                    } finally {
                    }
                }
            }
        };
        this.mAudioService = audioService;
        this.mMySpaceHandler = new Handler(KnoxCaptureInputFilter$$ExternalSyntheticOutline0.m("MediaFocusControl").getLooper());
        int devicesForStream = AudioSystem.getDevicesForStream(3);
        this.mDevice = devicesForStream;
        if (AudioUtils.isWiredDeviceType(devicesForStream)) {
            AudioService$$ExternalSyntheticOutline0.m(new StringBuilder("force change device "), this.mDevice, " to 2", "MediaFocusControl");
            this.mDevice = 2;
        }
        MultiFocusStack multiFocusStack = new MultiFocusStack();
        multiFocusStack.append(0, new Stack());
        this.mMultiFocusStack = multiFocusStack;
        this.mFocusStack = multiFocusStack.getStackForDevice(this.mDevice);
        this.mAudioSettingsHelper = AudioSettingsHelper.getInstance(context);
    }

    public static int getFocusRampTimeMs(AudioAttributes audioAttributes) {
        int usage = audioAttributes.getUsage();
        if (usage == 16) {
            return 700;
        }
        if (usage == 1002) {
            return 500;
        }
        if (usage == 1003) {
            return 700;
        }
        switch (usage) {
            case 1:
            case 14:
                return 1000;
            case 2:
            case 3:
            case 5:
            case 7:
            case 8:
            case 9:
            case 10:
            case 13:
                return 500;
            case 4:
            case 6:
            case 11:
            case 12:
                return 700;
            default:
                return 0;
        }
    }

    public static boolean isLockedFocusOwner(FocusRequester focusRequester) {
        return focusRequester.hasSameClient("AudioFocus_For_Phone_Ring_And_Calls") || (focusRequester.mGrantFlags & 4) != 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:89:0x023a A[Catch: all -> 0x008b, TryCatch #1 {all -> 0x008b, blocks: (B:6:0x0070, B:9:0x0084, B:11:0x0088, B:13:0x0090, B:15:0x009c, B:16:0x00a1, B:19:0x00ab, B:21:0x00b1, B:23:0x00ef, B:26:0x00f5, B:27:0x0103, B:29:0x0109, B:32:0x0115, B:36:0x011c, B:38:0x012f, B:39:0x0131, B:41:0x0135, B:43:0x013d, B:45:0x0145, B:47:0x0153, B:48:0x015f, B:50:0x0163, B:52:0x0187, B:55:0x018b, B:57:0x0190, B:59:0x0194, B:62:0x01a0, B:63:0x01a6, B:65:0x01ac, B:76:0x01b8, B:68:0x01dc, B:71:0x01e0, B:79:0x01fc, B:80:0x021b, B:83:0x0221, B:84:0x0226, B:86:0x022c, B:87:0x0234, B:89:0x023a, B:90:0x0243, B:100:0x00b8, B:101:0x00c2, B:103:0x00ca, B:105:0x00da, B:109:0x00ec, B:110:0x00e9), top: B:5:0x0070, outer: #0 }] */
    /* JADX WARN: Type inference failed for: r1v13 */
    /* JADX WARN: Type inference failed for: r1v14, types: [android.os.IBinder, com.android.server.audio.FocusRequester] */
    /* JADX WARN: Type inference failed for: r1v20 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void abandonAudioFocus(java.lang.String r21, android.media.AudioAttributes r22, java.lang.String r23) {
        /*
            Method dump skipped, instructions count: 607
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.audio.MediaFocusControl.abandonAudioFocus(java.lang.String, android.media.AudioAttributes, java.lang.String):void");
    }

    public final boolean canReassignAudioFocus() {
        return this.mFocusStack.isEmpty() || !isLockedFocusOwner((FocusRequester) this.mFocusStack.peek());
    }

    public final void clearMultiAudiofocusfromAndroidAuto() {
        Log.d("MediaFocusControl", "[Android Auto] clear MultiAudiofocus from AndroidAuto ");
        if (!this.mMultiAudioFocusList.isEmpty()) {
            Iterator it = this.mMultiAudioFocusList.iterator();
            while (it.hasNext()) {
                ((FocusRequester) it.next()).handleFocusLoss(-1, null, false);
                Log.d("MediaFocusControl", "[Android Auto] Loss Multi Audiofocus");
            }
            this.mMultiAudioFocusList.clear();
        }
        FocusRequester focusRequester = this.mIgnoredFocus;
        if (focusRequester != null) {
            focusRequester.handleFocusLoss(-1, null, false);
            this.mIgnoredFocus.release();
            this.mIgnoredFocus = null;
            Log.d("MediaFocusControl", "[Android Auto] Loss Ignored Focus");
        }
    }

    public final int dispatchFocusChange(AudioFocusInfo audioFocusInfo, int i) {
        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(i, "dispatchFocusChange ", " to afi client=");
        m.append(audioFocusInfo.getClientId());
        Log.v("MediaFocusControl", m.toString());
        synchronized (mAudioFocusLock) {
            try {
                FocusRequester focusRequesterLocked = getFocusRequesterLocked(audioFocusInfo.getClientId(), i == -1);
                if (focusRequesterLocked != null) {
                    return focusRequesterLocked.dispatchFocusChange(i);
                }
                Log.v("MediaFocusControl", "> failed: no such focus requester known");
                return 0;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final int dispatchFocusChangeWithFade(AudioFocusInfo audioFocusInfo, int i, List list) {
        Log.v("MediaFocusControl", "dispatchFocusChangeWithFade " + AudioManager.audioFocusToString(i) + " to afi client=" + audioFocusInfo.getClientId() + " other active afis=" + list);
        synchronized (mAudioFocusLock) {
            try {
                String clientId = audioFocusInfo.getClientId();
                FocusRequester focusRequesterLocked = getFocusRequesterLocked(clientId, false);
                if (focusRequesterLocked == null) {
                    Log.v("MediaFocusControl", "> failed: no such focus requester known");
                    return 0;
                }
                ArrayList arrayList = new ArrayList();
                for (int i2 = 0; i2 < list.size(); i2++) {
                    FocusRequester focusRequesterLocked2 = getFocusRequesterLocked(((AudioFocusInfo) list.get(i2)).getClientId(), false);
                    if (focusRequesterLocked2 != null) {
                        arrayList.add(focusRequesterLocked2);
                    }
                }
                int dispatchFocusChangeWithFadeLocked = focusRequesterLocked.dispatchFocusChangeWithFadeLocked(i, arrayList);
                if (dispatchFocusChangeWithFadeLocked != 2 && i == -1) {
                    this.mFocusOwnersForFocusPolicy.remove(clientId);
                }
                return dispatchFocusChangeWithFadeLocked;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.audio.PlayerFocusEnforcer
    public final boolean duckPlayers(FocusRequester focusRequester, FocusRequester focusRequester2, boolean z) {
        return this.mFocusEnforcer.duckPlayers(focusRequester, focusRequester2, z);
    }

    public final void dumpMultiSoundStack(PrintWriter printWriter) {
        StringBuilder m$1 = BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(printWriter, "\nMultiFocusStack:", "size:");
        MultiFocusStack multiFocusStack = this.mMultiFocusStack;
        m$1.append(multiFocusStack.size());
        printWriter.println(m$1.toString());
        printWriter.println("device:" + Integer.toHexString(this.mDevice));
        for (int i = 0; i < multiFocusStack.size(); i++) {
            Stack stack = (Stack) multiFocusStack.valueAt(i);
            StringBuilder m$12 = BinaryTransparencyService$$ExternalSyntheticOutline0.m$1(printWriter, "------------------------------", "device = ");
            m$12.append(Integer.toHexString(multiFocusStack.keyAt(i)));
            printWriter.println(m$12.toString());
            Iterator it = stack.iterator();
            while (it.hasNext()) {
                ((FocusRequester) it.next()).dump(printWriter);
            }
        }
        printWriter.println("------------------------------");
        if (this.mIgnoredFocus != null) {
            printWriter.println("Ignored focus");
            this.mIgnoredFocus.dump(printWriter);
        }
    }

    @Override // com.android.server.audio.PlayerFocusEnforcer
    public final boolean fadeOutPlayers(FocusRequester focusRequester, FocusRequester focusRequester2) {
        return this.mFocusEnforcer.fadeOutPlayers(focusRequester, focusRequester2);
    }

    @Override // com.android.server.audio.PlayerFocusEnforcer
    public final void forgetUid(int i) {
        this.mFocusEnforcer.forgetUid(i);
    }

    public final int getAppDevice(int i) {
        int appDevice = this.mAudioService.mMultiSoundManager.getAppDevice(i);
        if (AudioUtils.isWiredDeviceType(appDevice)) {
            return 2;
        }
        return appDevice;
    }

    @Override // com.android.server.audio.PlayerFocusEnforcer
    public final long getFadeInDelayForOffendersMillis(AudioAttributes audioAttributes) {
        if (audioAttributes == null) {
            return 0L;
        }
        return this.mFocusEnforcer.getFadeInDelayForOffendersMillis(audioAttributes);
    }

    @Override // com.android.server.audio.PlayerFocusEnforcer
    public final long getFadeOutDurationMillis(AudioAttributes audioAttributes) {
        if (audioAttributes == null) {
            return 0L;
        }
        return this.mFocusEnforcer.getFadeOutDurationMillis(audioAttributes);
    }

    public final FocusRequester getFocusRequesterLocked(String str, boolean z) {
        if (this.mFocusPolicy == null) {
            Log.v("MediaFocusControl", "> failed: no focus policy");
            return null;
        }
        FocusRequester focusRequester = z ? (FocusRequester) this.mFocusOwnersForFocusPolicy.remove(str) : (FocusRequester) this.mFocusOwnersForFocusPolicy.get(str);
        if (focusRequester == null) {
            Log.v("MediaFocusControl", "> failed: no such focus requester known");
        }
        return focusRequester;
    }

    @Override // com.android.server.audio.PlayerFocusEnforcer
    public final void mutePlayersForCall(int[] iArr) {
        this.mFocusEnforcer.mutePlayersForCall(iArr);
    }

    public final boolean notifyExtFocusPolicyFocusAbandon_syncAf(AudioFocusInfo audioFocusInfo) {
        if (this.mFocusPolicy == null) {
            return false;
        }
        FocusRequester focusRequester = (FocusRequester) this.mFocusOwnersForFocusPolicy.remove(audioFocusInfo.getClientId());
        if (focusRequester != null) {
            focusRequester.release();
        }
        try {
            this.mFocusPolicy.notifyAudioFocusAbandon(audioFocusInfo);
            return true;
        } catch (RemoteException e) {
            Log.e("MediaFocusControl", "Can't call notifyAudioFocusAbandon() on IAudioPolicyCallback " + this.mFocusPolicy.asBinder(), e);
            return true;
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(10:0|1|22|6|(4:(5:8|(1:20)|12|13|14)|12|13|14)|21|22|23|24|(1:(0))) */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final boolean notifyExtFocusPolicyFocusRequest_syncAf(android.media.AudioFocusInfo r11, android.media.IAudioFocusDispatcher r12, android.os.IBinder r13) {
        /*
            r10 = this;
            java.lang.String r0 = "MediaFocusControl"
            java.lang.StringBuilder r1 = new java.lang.StringBuilder
            java.lang.String r2 = "notifyExtFocusPolicyFocusRequest client="
            r1.<init>(r2)
            java.lang.String r2 = r11.getClientId()
            r1.append(r2)
            java.lang.String r2 = " dispatcher="
            r1.append(r2)
            r1.append(r12)
            java.lang.String r1 = r1.toString()
            android.util.Log.v(r0, r1)
            java.lang.Object r0 = r10.mExtFocusChangeLock
            monitor-enter(r0)
            long r1 = r10.mExtFocusChangeCounter     // Catch: java.lang.Throwable -> L88
            r3 = 1
            long r3 = r3 + r1
            r10.mExtFocusChangeCounter = r3     // Catch: java.lang.Throwable -> L88
            r11.setGen(r1)     // Catch: java.lang.Throwable -> L88
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L88
            java.util.HashMap r0 = r10.mFocusOwnersForFocusPolicy
            java.lang.String r1 = r11.getClientId()
            java.lang.Object r0 = r0.get(r1)
            com.android.server.audio.FocusRequester r0 = (com.android.server.audio.FocusRequester) r0
            r1 = 0
            if (r0 == 0) goto L4b
            android.media.IAudioFocusDispatcher r2 = r0.mFocusDispatcher
            if (r2 == 0) goto L48
            boolean r2 = r2.equals(r12)
            if (r2 == 0) goto L48
            goto L66
        L48:
            r0.release()
        L4b:
            com.android.server.audio.MediaFocusControl$AudioFocusDeathHandler r7 = new com.android.server.audio.MediaFocusControl$AudioFocusDeathHandler
            r7.<init>(r13)
            r13.linkToDeath(r7, r1)     // Catch: android.os.RemoteException -> L87
            java.util.HashMap r0 = r10.mFocusOwnersForFocusPolicy
            java.lang.String r2 = r11.getClientId()
            com.android.server.audio.FocusRequester r9 = new com.android.server.audio.FocusRequester
            r3 = r9
            r4 = r11
            r5 = r12
            r6 = r13
            r8 = r10
            r3.<init>(r4, r5, r6, r7, r8)
            r0.put(r2, r9)
        L66:
            android.media.audiopolicy.IAudioPolicyCallback r12 = r10.mFocusPolicy     // Catch: android.os.RemoteException -> L6d
            r13 = 1
            r12.notifyAudioFocusRequest(r11, r13)     // Catch: android.os.RemoteException -> L6d
            return r13
        L6d:
            r11 = move-exception
            java.lang.String r12 = "MediaFocusControl"
            java.lang.StringBuilder r13 = new java.lang.StringBuilder
            java.lang.String r0 = "Can't call notifyAudioFocusRequest() on IAudioPolicyCallback "
            r13.<init>(r0)
            android.media.audiopolicy.IAudioPolicyCallback r10 = r10.mFocusPolicy
            android.os.IBinder r10 = r10.asBinder()
            r13.append(r10)
            java.lang.String r10 = r13.toString()
            android.util.Log.e(r12, r10, r11)
        L87:
            return r1
        L88:
            r10 = move-exception
            monitor-exit(r0)     // Catch: java.lang.Throwable -> L88
            throw r10
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.server.audio.MediaFocusControl.notifyExtFocusPolicyFocusRequest_syncAf(android.media.AudioFocusInfo, android.media.IAudioFocusDispatcher, android.os.IBinder):boolean");
    }

    public final void notifyExtPolicyFocusGrant_syncAf(AudioFocusInfo audioFocusInfo, int i) {
        AudioService audioService;
        Iterator it = this.mFocusFollowers.iterator();
        while (it.hasNext()) {
            IAudioPolicyCallback iAudioPolicyCallback = (IAudioPolicyCallback) it.next();
            try {
                iAudioPolicyCallback.notifyAudioFocusGrant(audioFocusInfo, i);
            } catch (RemoteException e) {
                Log.e("MediaFocusControl", "Can't call notifyAudioFocusGrant() on IAudioPolicyCallback " + iAudioPolicyCallback.asBinder(), e);
            }
        }
        if (Rune.SEC_AUDIO_CARLIFE && (audioService = this.mAudioService) != null && i == 1 && audioFocusInfo.getGainRequest() == 3) {
            audioService.sendBroadcastToSoundEventReceiver(512, 1, audioFocusInfo.getPackageName());
        }
    }

    public final void notifyExtPolicyFocusLoss_syncAf(AudioFocusInfo audioFocusInfo, boolean z) {
        AudioService audioService;
        Iterator it = this.mFocusFollowers.iterator();
        while (it.hasNext()) {
            IAudioPolicyCallback iAudioPolicyCallback = (IAudioPolicyCallback) it.next();
            try {
                iAudioPolicyCallback.notifyAudioFocusLoss(audioFocusInfo, z);
            } catch (RemoteException e) {
                Log.e("MediaFocusControl", "Can't call notifyAudioFocusLoss() on IAudioPolicyCallback " + iAudioPolicyCallback.asBinder(), e);
            }
        }
        if (Rune.SEC_AUDIO_CARLIFE && (audioService = this.mAudioService) != null && audioFocusInfo.getGainRequest() == 3) {
            audioService.sendBroadcastToSoundEventReceiver(512, 2, audioFocusInfo.getPackageName());
        }
    }

    public final void notifyTopOfAudioFocusStack() {
        if (!this.mFocusStack.empty() && canReassignAudioFocus() && !this.mRingOrCallActive) {
            if (this.mAudioSettingsHelper.checkAppCategory(((FocusRequester) this.mFocusStack.peek()).mPackageName, "delay_loss_audio_focus")) {
                VpnManagerService$$ExternalSyntheticOutline0.m(new StringBuilder("notifyTopOfAudioFocusStack: "), ((FocusRequester) this.mFocusStack.peek()).mClientId, "MediaFocusControl");
                removeMessages(3, this.mFocusStack.peek());
                AnonymousClass4 anonymousClass4 = this.mFocusHandler;
                anonymousClass4.sendMessageDelayed(anonymousClass4.obtainMessage(4, this.mFocusStack.peek()), 500L);
            } else {
                ((FocusRequester) this.mFocusStack.peek()).handleFocusGain();
            }
        }
        if (!this.mMultiAudioFocusEnabled || this.mMultiAudioFocusList.isEmpty()) {
            return;
        }
        Iterator it = this.mMultiAudioFocusList.iterator();
        while (it.hasNext()) {
            FocusRequester focusRequester = (FocusRequester) it.next();
            if (isLockedFocusOwner(focusRequester)) {
                focusRequester.handleFocusGain();
            }
        }
    }

    public final void propagateFocusLossFromGain_syncAf(int i, FocusRequester focusRequester, boolean z) {
        Log.i("MediaFocusControl", "propagateFocusLossFromGain_syncAf gain:" + i);
        LinkedList linkedList = new LinkedList();
        if (this.mFocusStack.empty()) {
            Log.i("MediaFocusControl", "propagateFocusLossFromGain_syncAf empty stack");
        } else {
            Iterator it = this.mFocusStack.iterator();
            while (it.hasNext()) {
                FocusRequester focusRequester2 = (FocusRequester) it.next();
                DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(new StringBuilder("propagateFocusLossFromGain_syncAf checking client:"), focusRequester2.mClientId, "MediaFocusControl");
                if (focusRequester2.handleFocusLossFromGain(i, focusRequester, z)) {
                    linkedList.add(focusRequester2.mClientId);
                }
            }
        }
        if (this.mMultiAudioFocusEnabled && !this.mMultiAudioFocusList.isEmpty()) {
            Iterator it2 = this.mMultiAudioFocusList.iterator();
            while (it2.hasNext()) {
                FocusRequester focusRequester3 = (FocusRequester) it2.next();
                if (focusRequester != null) {
                    if (TextUtils.equals(focusRequester.mClientId, focusRequester3.mClientId)) {
                    }
                }
                if (focusRequester3.handleFocusLossFromGain(i, focusRequester, z)) {
                    linkedList.add(focusRequester3.mClientId);
                }
            }
        }
        Iterator it3 = linkedList.iterator();
        while (it3.hasNext()) {
            removeFocusStackEntry((String) it3.next(), false, true);
        }
    }

    public final int pushBelowLockedFocusOwnersAndPropagate(FocusRequester focusRequester) {
        Log.v("MediaFocusControl", "pushBelowLockedFocusOwnersAndPropagate client=" + focusRequester.mClientId);
        int size = this.mFocusStack.size();
        for (int size2 = this.mFocusStack.size() - 1; size2 >= 0; size2--) {
            if (isLockedFocusOwner((FocusRequester) this.mFocusStack.elementAt(size2))) {
                size = size2;
            }
        }
        int size3 = this.mFocusStack.size();
        int i = focusRequester.mFocusGainRequest;
        if (size == size3) {
            Log.e("MediaFocusControl", "No exclusive focus owner found in propagateFocusLossFromGain_syncAf()", new Exception());
            propagateFocusLossFromGain_syncAf(i, focusRequester, false);
            this.mFocusStack.push(focusRequester);
            return 1;
        }
        Log.v("MediaFocusControl", "> lastLockedFocusOwnerIndex=" + size);
        this.mFocusStack.insertElementAt(focusRequester, size);
        LinkedList<String> linkedList = new LinkedList();
        for (int i2 = size - 1; i2 >= 0; i2--) {
            if (((FocusRequester) this.mFocusStack.elementAt(i2)).handleFocusLossFromGain(i, focusRequester, false)) {
                linkedList.add(((FocusRequester) this.mFocusStack.elementAt(i2)).mClientId);
            }
        }
        for (String str : linkedList) {
            Log.v("MediaFocusControl", "> removing focus client " + str);
            removeFocusStackEntry(str, false, true);
        }
        return 2;
    }

    public final void removeFocusFollower(IAudioPolicyCallback iAudioPolicyCallback) {
        if (iAudioPolicyCallback == null) {
            return;
        }
        synchronized (mAudioFocusLock) {
            try {
                Iterator it = this.mFocusFollowers.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    IAudioPolicyCallback iAudioPolicyCallback2 = (IAudioPolicyCallback) it.next();
                    if (iAudioPolicyCallback2.asBinder().equals(iAudioPolicyCallback.asBinder())) {
                        this.mFocusFollowers.remove(iAudioPolicyCallback2);
                        break;
                    }
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    public final void removeFocusStackEntry(String str, boolean z, boolean z2) {
        ExtendedEthernetServiceImpl$1$$ExternalSyntheticOutline0.m("removeFocusStackEntry client:", str, "MediaFocusControl");
        if (this.mFocusStack.empty() || !((FocusRequester) this.mFocusStack.peek()).hasSameClient(str)) {
            Iterator it = this.mFocusStack.iterator();
            while (it.hasNext()) {
                FocusRequester focusRequester = (FocusRequester) it.next();
                if (focusRequester.hasSameClient(str)) {
                    Log.i("MediaFocusControl", "AudioFocus  removeFocusStackEntry(): removing entry for " + str);
                    it.remove();
                    if (z2) {
                        r2 = focusRequester.toAudioFocusInfo();
                    }
                    if (!focusRequester.mFocusLossFadeLimbo) {
                        focusRequester.release();
                    }
                }
            }
        } else {
            FocusRequester focusRequester2 = (FocusRequester) this.mFocusStack.pop();
            if (!focusRequester2.mFocusLossFadeLimbo) {
                focusRequester2.release();
            }
            r2 = z2 ? focusRequester2.toAudioFocusInfo() : null;
            if (z) {
                notifyTopOfAudioFocusStack();
            }
        }
        if (r2 != null) {
            r2.clearLossReceived();
            notifyExtPolicyFocusLoss_syncAf(r2, false);
        }
        if (!this.mMultiAudioFocusEnabled || this.mMultiAudioFocusList.isEmpty()) {
            return;
        }
        Iterator it2 = this.mMultiAudioFocusList.iterator();
        while (it2.hasNext()) {
            FocusRequester focusRequester3 = (FocusRequester) it2.next();
            if (focusRequester3.hasSameClient(str)) {
                it2.remove();
                focusRequester3.release();
            }
        }
        if (z) {
            notifyTopOfAudioFocusStack();
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r10v1 */
    /* JADX WARN: Type inference failed for: r13v1 */
    /* JADX WARN: Type inference failed for: r13v2, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r13v6 */
    /* JADX WARN: Type inference failed for: r14v1 */
    /* JADX WARN: Type inference failed for: r14v2, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r14v3 */
    /* JADX WARN: Type inference failed for: r19v0 */
    /* JADX WARN: Type inference failed for: r19v1 */
    /* JADX WARN: Type inference failed for: r19v2 */
    /* JADX WARN: Type inference failed for: r23v0, types: [com.android.server.audio.MediaFocusControl] */
    /* JADX WARN: Type inference failed for: r26v0, types: [android.os.IBinder, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r2v23, types: [com.android.server.audio.FocusRequester] */
    /* JADX WARN: Type inference failed for: r9v2 */
    /* JADX WARN: Type inference failed for: r9v3, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r9v5 */
    public final int requestAudioFocus(AudioAttributes audioAttributes, int i, IBinder iBinder, IAudioFocusDispatcher iAudioFocusDispatcher, String str, String str2, int i2, int i3, boolean z, int i4) {
        int i5;
        ?? r13;
        int i6;
        AudioFocusInfo audioFocusInfo;
        ?? r9;
        ?? r19;
        boolean z2;
        int i7;
        int i8;
        ?? r14;
        boolean z3;
        String str3;
        ?? r2;
        boolean z4;
        int usage;
        new MediaMetrics.Item("audio.focus").setUid(Binder.getCallingUid()).set(MediaMetrics.Property.CALLING_PACKAGE, str2).set(MediaMetrics.Property.CLIENT_NAME, str).set(MediaMetrics.Property.EVENT, "requestAudioFocus").set(MediaMetrics.Property.FLAGS, Integer.valueOf(i2)).set(MediaMetrics.Property.FOCUS_CHANGE_HINT, AudioManager.audioFocusToString(i)).record();
        int callingUid = i2 == 8 ? i4 : Binder.getCallingUid();
        EventLogger eventLogger = mEventLogger;
        StringBuilder m = BatteryService$$ExternalSyntheticOutline0.m(callingUid, "requestAudioFocus() from uid/pid ", "/");
        m.append(Binder.getCallingPid());
        m.append(" AA=");
        m.append(audioAttributes.usageToString());
        m.append("/");
        m.append(audioAttributes.contentTypeToString());
        m.append(" clientId=");
        DirEncryptServiceHelper$$ExternalSyntheticOutline0.m(m, str, " callingPack=", str2, " req=");
        m.append(i);
        m.append(" flags=0x");
        m.append(Integer.toHexString(i2));
        m.append(" sdk=");
        m.append(i3);
        EventLogger.StringEvent stringEvent = new EventLogger.StringEvent(m.toString());
        stringEvent.printLog(0, "MediaFocusControl");
        eventLogger.enqueue(stringEvent);
        if (!iBinder.pingBinder()) {
            Log.e("MediaFocusControl", " AudioFocus DOA client for requestAudioFocus(), aborting.");
            return 0;
        }
        if (Rune.SEC_AUDIO_SUPPORT_ACH_RINGTONE && TextUtils.equals(str2, Constants.SYSTEMUI_PACKAGE_NAME) && i != 1 && this.mAudioService.getStreamVolume(AudioAttributes.toLegacyStreamType(audioAttributes)) == 0) {
            Log.d("MediaFocusControl", "Notification volume is 0,  failing requestAudioFocus()");
            return 0;
        }
        boolean isConnectedAndroidAuto = this.mAudioService.isConnectedAndroidAuto();
        synchronized (mAudioFocusLock) {
            try {
                if (this.mFocusFreezerForTest != null) {
                    int callingUid2 = (i2 & 8) == 8 ? i4 : Binder.getCallingUid();
                    if (this.mFocusFreezerForTest == null) {
                        for (int i9 : this.mFocusFreezeExemptUids) {
                            if (i9 != callingUid2) {
                            }
                        }
                        Log.i("MediaFocusControl", "requestAudioFocus: focus frozen for test for uid:" + callingUid2);
                        return 0;
                    }
                    Log.i("MediaFocusControl", "requestAudioFocus: focus frozen for test but uid:" + callingUid2 + " is exempt");
                }
                if (this.mFocusStack.size() > 100) {
                    Log.e("MediaFocusControl", "Max AudioFocus stack size reached, failing requestAudioFocus()");
                    return 0;
                }
                if (this.mCallClientId != null) {
                    if ((i2 & 1) == 0) {
                        Log.d("MediaFocusControl", "requestAudioFocus failed while call");
                        return 0;
                    }
                    Log.d("MediaFocusControl", "requestAudioFocus NOT failed while call - FLAG_DELAY_OK ");
                }
                this.mFocusStack = selectFocusStack(Binder.getCallingUid());
                if (this.mMultiAudioFocusEnabled && !isConnectedAndroidAuto) {
                    this.mMultiAudioFocusList = selectFocusList(Binder.getCallingUid());
                }
                boolean z5 = (!this.mRingOrCallActive) & ("AudioFocus_For_Phone_Ring_And_Calls".compareTo(str) == 0);
                if (z5) {
                    this.mRingOrCallActive = true;
                }
                if (this.mFocusPolicy != null) {
                    i5 = 100;
                    r13 = 1;
                    i6 = callingUid;
                    audioFocusInfo = new AudioFocusInfo(audioAttributes, callingUid, str, str2, i, 0, i2, i3);
                } else {
                    i5 = 100;
                    r13 = 1;
                    i6 = callingUid;
                    audioFocusInfo = null;
                }
                if (ScreenSharingHelper.sSplitSoundEnabled && ("com.android.server.telecom".equals(str2) || (usage = audioAttributes.getUsage()) == 4 || usage == 5 || usage == 6)) {
                    if ("AudioFocus_For_Phone_Ring_And_Calls".equals(str)) {
                        this.mSplitSoundFR = new FocusRequester(audioAttributes, i, i2, iAudioFocusDispatcher, iBinder, str, null, str2, Binder.getCallingUid(), this, i3);
                        this.mSplitSoundCb = iBinder;
                    }
                    return r13;
                }
                if ("com.android.server.telecom".equals(str2)) {
                    this.mCallClientId = str;
                }
                if (canReassignAudioFocus()) {
                    r9 = 0;
                    r19 = false;
                } else {
                    if ((i2 & 1) == 0) {
                        return 0;
                    }
                    r9 = 0;
                    r19 = r13;
                }
                if (this.mFocusPolicy != null) {
                    return notifyExtFocusPolicyFocusRequest_syncAf(audioFocusInfo, iAudioFocusDispatcher, iBinder) ? i5 : r9;
                }
                AudioFocusDeathHandler audioFocusDeathHandler = new AudioFocusDeathHandler(iBinder);
                try {
                    iBinder.linkToDeath(audioFocusDeathHandler, r9);
                    if ("AudioFocus_For_Phone_Ring_And_Calls".compareTo(str) == 0 && !AudioManagerHelper.isFMPlayerActive() && !this.mFocusStack.empty() && this.mAudioService.isPlaybackActiveForUid(((FocusRequester) this.mFocusStack.peek()).mCallingUid)) {
                        if ((AudioSystem.getDeviceMaskFromSet(this.mAudioService.mStreamStates[3].mObservedDeviceSet) & 12) != 0) {
                            MySpaceManager.playMySpaceEffect((int) r13);
                            Handler handler = this.mMySpaceHandler;
                            handler.removeCallbacks(this.mMySpaceRunnable);
                            handler.postDelayed(this.mMySpaceRunnable, 1000L);
                            try {
                                mAudioFocusLock.wait(1500L);
                            } catch (InterruptedException unused) {
                            }
                            Log.v("MediaFocusControl", "requestAudioFocus: playMySpaceEffect MUSIC FADE OUT");
                        }
                        this.mIsMySpaceEffectFocus = r13;
                    }
                    if (this.mFocusStack.empty() || !((FocusRequester) this.mFocusStack.peek()).hasSameClient(str)) {
                        z2 = r13;
                        i7 = i;
                    } else {
                        FocusRequester focusRequester = (FocusRequester) this.mFocusStack.peek();
                        z2 = r13;
                        i7 = i;
                        if (focusRequester.mFocusGainRequest == i7 && focusRequester.mGrantFlags == i2) {
                            iBinder.unlinkToDeath(audioFocusDeathHandler, r9);
                            notifyExtPolicyFocusGrant_syncAf(focusRequester.toAudioFocusInfo(), z2 ? 1 : 0);
                            if (i7 == z2 && !this.mFocusStack.empty() && canReassignAudioFocus()) {
                                ((FocusRequester) this.mFocusStack.peek()).mFocusLossReceived = r9;
                            }
                            return z2 ? 1 : 0;
                        }
                        if (r19 == false) {
                            this.mFocusStack.pop();
                            focusRequester.release();
                        }
                    }
                    removeFocusStackEntry(str, r9, r9);
                    int i10 = i6;
                    FocusRequester focusRequester2 = this.mMultiFocusStack.getFocusRequester(i10, str, z2);
                    if (focusRequester2 != null) {
                        focusRequester2.mFocusLossReceived = r9;
                    }
                    if (focusRequester2 == null) {
                        i8 = i10;
                        r14 = z2;
                        focusRequester2 = new FocusRequester(audioAttributes, i, i2, iAudioFocusDispatcher, iBinder, str, audioFocusDeathHandler, str2, i8, this, i3);
                    } else {
                        i8 = i10;
                        r14 = z2;
                    }
                    focusRequester2.mDevice = getAppDevice(focusRequester2.mCallingUid);
                    if (isConnectedAndroidAuto) {
                        clearMultiAudiofocusfromAndroidAuto();
                        str3 = str2;
                        z3 = z;
                    } else {
                        if (this.mMultiAudioFocusEnabled && i7 == r14 && (!Rune.SEC_AUDIO_REMOTE_MIC || (!audioAttributes.getTags().contains("AUDIO_REMOTE_MIC") && !this.mAudioService.mRemoteMic))) {
                            if (!z5) {
                                if (!this.mMultiAudioFocusList.isEmpty()) {
                                    Iterator it = this.mMultiAudioFocusList.iterator();
                                    while (it.hasNext()) {
                                        if (((FocusRequester) it.next()).mCallingUid == Binder.getCallingUid()) {
                                            break;
                                        }
                                    }
                                }
                                this.mMultiAudioFocusList.add(focusRequester2);
                                this.mMultiFocusStack.getStackForDevice(focusRequester2.mDevice).push(focusRequester2);
                                focusRequester2.mFocusController.restoreVShapedPlayers(focusRequester2);
                                notifyExtPolicyFocusGrant_syncAf(focusRequester2.toAudioFocusInfo(), r14);
                                return r14;
                            }
                            if (!this.mMultiAudioFocusList.isEmpty()) {
                                Iterator it2 = this.mMultiAudioFocusList.iterator();
                                while (it2.hasNext()) {
                                    ((FocusRequester) it2.next()).handleFocusLossFromGain(i7, focusRequester2, z);
                                }
                            }
                        }
                        z3 = z;
                        if (this.mIgnoredUid == i8) {
                            Log.i("MediaFocusControl", "Ignore AudioFocus for " + str2);
                            FocusRequester focusRequester3 = this.mIgnoredFocus;
                            if (focusRequester3 != null) {
                                if (focusRequester3.hasSameClient(str)) {
                                    z4 = false;
                                } else {
                                    z4 = false;
                                    this.mIgnoredFocus.handleFocusLossFromGain(i7, focusRequester2, false);
                                }
                                this.mIgnoredFocus.release();
                                this.mIgnoredFocus = null;
                            } else {
                                z4 = false;
                            }
                            this.mIgnoredFocus = focusRequester2;
                            if (!this.mFocusStack.empty() && "com.google.android.projection.gearhead".equals(((FocusRequester) this.mFocusStack.peek()).mPackageName)) {
                                Log.d("MediaFocusControl", "Loss to Android Auto");
                                ((FocusRequester) this.mFocusStack.peek()).handleFocusLossFromGain(i7, focusRequester2, z4);
                            }
                            focusRequester2.mFocusController.restoreVShapedPlayers(focusRequester2);
                            notifyExtPolicyFocusGrant_syncAf(focusRequester2.toAudioFocusInfo(), r14);
                            return r14;
                        }
                        str3 = str2;
                        if (z5 && (r2 = this.mIgnoredFocus) != 0) {
                            r2.handleFocusLossFromGain(i7, focusRequester2, r14);
                        }
                    }
                    if (r19 == true) {
                        int pushBelowLockedFocusOwnersAndPropagate = pushBelowLockedFocusOwnersAndPropagate(focusRequester2);
                        if (pushBelowLockedFocusOwnersAndPropagate != 0) {
                            notifyExtPolicyFocusGrant_syncAf(focusRequester2.toAudioFocusInfo(), pushBelowLockedFocusOwnersAndPropagate);
                        }
                        return pushBelowLockedFocusOwnersAndPropagate;
                    }
                    propagateFocusLossFromGain_syncAf(i7, focusRequester2, z3);
                    if ("com.android.server.telecom".equals(str3) || audioAttributes.getContentType() == r14) {
                        NetworkScorerAppManager$$ExternalSyntheticOutline0.m(i7, "propagateFocusLossFromGainToAll, ", "MediaFocusControl");
                        int i11 = 0;
                        while (true) {
                            MultiFocusStack multiFocusStack = this.mMultiFocusStack;
                            if (i11 >= multiFocusStack.size()) {
                                break;
                            }
                            Iterator it3 = ((Stack) multiFocusStack.valueAt(i11)).iterator();
                            while (it3.hasNext()) {
                                FocusRequester focusRequester4 = (FocusRequester) it3.next();
                                focusRequester4.handleFocusLossFromGain(i7, focusRequester4, true);
                            }
                            i11++;
                        }
                    }
                    this.mFocusStack.push(focusRequester2);
                    focusRequester2.mFocusController.restoreVShapedPlayers(focusRequester2);
                    notifyExtPolicyFocusGrant_syncAf(focusRequester2.toAudioFocusInfo(), r14);
                    if (z5) {
                        new AnonymousClass3(r14).start();
                    }
                    return r14;
                } catch (RemoteException unused2) {
                    int i12 = r9;
                    Log.w("MediaFocusControl", "AudioFocus  requestAudioFocus() could not link to " + ((Object) iBinder) + " binder death");
                    return i12;
                }
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.audio.PlayerFocusEnforcer
    public final void restoreVShapedPlayers(FocusRequester focusRequester) {
        this.mFocusEnforcer.restoreVShapedPlayers(focusRequester);
        removeEqualMessages(2, new ForgetFadeUidInfo(focusRequester.mCallingUid));
    }

    public final ArrayList selectFocusList(int i) {
        int appDevice = getAppDevice(i);
        Log.d("MediaFocusControl", "selectFocusStack, uid = " + Binder.getCallingUid() + ", appDevice = " + Integer.toHexString(appDevice) + ", device = " + Integer.toHexString(this.mDevice));
        if (appDevice == 0 || appDevice == this.mDevice) {
            appDevice = 0;
        }
        MultiFocusStack multiFocusStack = this.mMultiFocusStack;
        multiFocusStack.getClass();
        return new ArrayList(multiFocusStack.getStackForDevice(appDevice));
    }

    public final Stack selectFocusStack(int i) {
        int appDevice = getAppDevice(i);
        Log.d("MediaFocusControl", "selectFocusStack, uid = " + Binder.getCallingUid() + ", appDevice = " + Integer.toHexString(appDevice) + ", device = " + Integer.toHexString(this.mDevice));
        if (appDevice == 0 || appDevice == this.mDevice) {
            appDevice = 0;
        }
        return this.mMultiFocusStack.getStackForDevice(appDevice);
    }

    public final void setFocusRequestResultFromExtPolicy(AudioFocusInfo audioFocusInfo, int i) {
        synchronized (this.mExtFocusChangeLock) {
            try {
                if (audioFocusInfo.getGen() > this.mExtFocusChangeCounter) {
                    return;
                }
                synchronized (mAudioFocusLock) {
                    try {
                        FocusRequester focusRequesterLocked = getFocusRequesterLocked(audioFocusInfo.getClientId(), i == 0);
                        if (focusRequesterLocked != null) {
                            String str = focusRequesterLocked.mClientId;
                            IAudioFocusDispatcher iAudioFocusDispatcher = focusRequesterLocked.mFocusDispatcher;
                            if (iAudioFocusDispatcher == null) {
                                Log.e("FocusRequester", "dispatchFocusResultFromExtPolicy: no focus dispatcher");
                            } else {
                                try {
                                    iAudioFocusDispatcher.dispatchFocusResultFromExtPolicy(i, str);
                                } catch (RemoteException e) {
                                    Log.e("FocusRequester", "dispatchFocusResultFromExtPolicy: error talking to focus listener" + str, e);
                                }
                            }
                            if (Flags.enableFadeManagerConfiguration() && i == 1) {
                                focusRequesterLocked.mFocusController.restoreVShapedPlayers(focusRequesterLocked);
                            }
                        }
                    } finally {
                    }
                }
            } finally {
            }
        }
    }

    public final void setIgnoreAudioFocus(int i, boolean z) {
        synchronized (mAudioFocusLock) {
            try {
                FocusRequester focusRequester = this.mIgnoredFocus;
                if (focusRequester != null) {
                    if (!focusRequester.hasSameUid(i) || !z) {
                        this.mIgnoredFocus.handleFocusLoss(-1, null, false);
                    }
                    this.mIgnoredFocus.release();
                    this.mIgnoredFocus = null;
                }
                if (!z) {
                    this.mIgnoredUid = -1;
                    return;
                }
                this.mIgnoredUid = i;
                ArrayList focusRequester2 = this.mMultiFocusStack.getFocusRequester(i, true);
                if (focusRequester2.isEmpty()) {
                    return;
                }
                this.mIgnoredFocus = (FocusRequester) focusRequester2.get(focusRequester2.size() - 1);
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // com.android.server.audio.PlayerFocusEnforcer
    public final boolean shouldEnforceFade() {
        if (Flags.enableFadeManagerConfiguration()) {
            return this.mFocusEnforcer.shouldEnforceFade();
        }
        return false;
    }

    @Override // com.android.server.audio.PlayerFocusEnforcer
    public final void unmutePlayersForCall() {
        this.mFocusEnforcer.unmutePlayersForCall();
    }

    public final void updateFocusRequester(int i, final boolean z) {
        Log.d("MediaFocusControl", "updateFocusRequester, uid:" + i);
        final int appDevice = getAppDevice(i);
        final boolean isPlaybackActiveForUid = this.mAudioService.isPlaybackActiveForUid(i);
        synchronized (mAudioFocusLock) {
            this.mMultiFocusStack.getFocusRequester(i, true).forEach(new Consumer() { // from class: com.android.server.audio.MediaFocusControl$$ExternalSyntheticLambda1
                /* JADX WARN: Removed duplicated region for block: B:19:0x0082 A[Catch: all -> 0x002f, TryCatch #0 {all -> 0x002f, blocks: (B:32:0x0023, B:35:0x0028, B:5:0x0038, B:10:0x0044, B:11:0x0056, B:14:0x006f, B:16:0x0074, B:17:0x007d, B:19:0x0082, B:21:0x0087, B:22:0x008a, B:4:0x0031), top: B:31:0x0023 }] */
                /* JADX WARN: Removed duplicated region for block: B:21:0x0087 A[Catch: all -> 0x002f, TryCatch #0 {all -> 0x002f, blocks: (B:32:0x0023, B:35:0x0028, B:5:0x0038, B:10:0x0044, B:11:0x0056, B:14:0x006f, B:16:0x0074, B:17:0x007d, B:19:0x0082, B:21:0x0087, B:22:0x008a, B:4:0x0031), top: B:31:0x0023 }] */
                /* JADX WARN: Removed duplicated region for block: B:7:0x003f  */
                @Override // java.util.function.Consumer
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                    To view partially-correct code enable 'Show inconsistent code' option in preferences
                */
                public final void accept(java.lang.Object r7) {
                    /*
                        r6 = this;
                        com.android.server.audio.MediaFocusControl r0 = com.android.server.audio.MediaFocusControl.this
                        int r1 = r2
                        boolean r2 = r3
                        boolean r6 = r4
                        com.android.server.audio.FocusRequester r7 = (com.android.server.audio.FocusRequester) r7
                        r0.getClass()
                        java.lang.String r3 = "MediaFocusControl"
                        java.lang.StringBuilder r4 = new java.lang.StringBuilder
                        java.lang.String r5 = "focusRequester.getClientId()="
                        r4.<init>(r5)
                        java.lang.String r5 = r7.mClientId
                        com.android.server.VpnManagerService$$ExternalSyntheticOutline0.m(r4, r5, r3)
                        r7.mDevice = r1
                        java.lang.Object r3 = com.android.server.audio.MediaFocusControl.mAudioFocusLock
                        monitor-enter(r3)
                        if (r1 == 0) goto L31
                        int r4 = r0.mDevice     // Catch: java.lang.Throwable -> L2f
                        if (r1 != r4) goto L28
                        goto L31
                    L28:
                        com.android.server.audio.MultiFocusStack r0 = r0.mMultiFocusStack     // Catch: java.lang.Throwable -> L2f
                        java.util.Stack r0 = r0.getStackForDevice(r1)     // Catch: java.lang.Throwable -> L2f
                        goto L38
                    L2f:
                        r6 = move-exception
                        goto L8c
                    L31:
                        com.android.server.audio.MultiFocusStack r0 = r0.mMultiFocusStack     // Catch: java.lang.Throwable -> L2f
                        r1 = 0
                        java.util.Stack r0 = r0.getStackForDevice(r1)     // Catch: java.lang.Throwable -> L2f
                    L38:
                        boolean r1 = r0.isEmpty()     // Catch: java.lang.Throwable -> L2f
                        r4 = 0
                        if (r1 != 0) goto L7c
                        r1 = 1
                        if (r2 == 0) goto L56
                        if (r6 != 0) goto L56
                        java.lang.String r6 = "MediaFocusControl"
                        java.lang.String r2 = "pin app playing"
                        android.util.Log.d(r6, r2)     // Catch: java.lang.Throwable -> L2f
                        java.lang.Object r6 = r0.peek()     // Catch: java.lang.Throwable -> L2f
                        com.android.server.audio.FocusRequester r6 = (com.android.server.audio.FocusRequester) r6     // Catch: java.lang.Throwable -> L2f
                        r6.handleFocusLossFromGain(r1, r7, r1)     // Catch: java.lang.Throwable -> L2f
                        goto L7c
                    L56:
                        java.lang.String r6 = "MediaFocusControl"
                        java.lang.String r2 = "pin app paused"
                        android.util.Log.d(r6, r2)     // Catch: java.lang.Throwable -> L2f
                        r7.handleFocusLossFromGain(r1, r7, r1)     // Catch: java.lang.Throwable -> L2f
                        java.lang.Object r6 = r0.pop()     // Catch: java.lang.Throwable -> L2f
                        com.android.server.audio.FocusRequester r6 = (com.android.server.audio.FocusRequester) r6     // Catch: java.lang.Throwable -> L2f
                        boolean r1 = r0.isEmpty()     // Catch: java.lang.Throwable -> L2f
                        if (r1 != 0) goto L7d
                        if (r6 == 0) goto L7d
                        int r1 = r6.mFocusGainRequest     // Catch: java.lang.Throwable -> L2f
                        r2 = 3
                        if (r1 != r2) goto L7d
                        java.lang.Object r1 = r0.pop()     // Catch: java.lang.Throwable -> L2f
                        r4 = r1
                        com.android.server.audio.FocusRequester r4 = (com.android.server.audio.FocusRequester) r4     // Catch: java.lang.Throwable -> L2f
                        goto L7d
                    L7c:
                        r6 = r4
                    L7d:
                        r0.push(r7)     // Catch: java.lang.Throwable -> L2f
                        if (r4 == 0) goto L85
                        r0.push(r4)     // Catch: java.lang.Throwable -> L2f
                    L85:
                        if (r6 == 0) goto L8a
                        r0.push(r6)     // Catch: java.lang.Throwable -> L2f
                    L8a:
                        monitor-exit(r3)     // Catch: java.lang.Throwable -> L2f
                        return
                    L8c:
                        monitor-exit(r3)     // Catch: java.lang.Throwable -> L2f
                        throw r6
                    */
                    throw new UnsupportedOperationException("Method not decompiled: com.android.server.audio.MediaFocusControl$$ExternalSyntheticLambda1.accept(java.lang.Object):void");
                }
            });
        }
    }
}
