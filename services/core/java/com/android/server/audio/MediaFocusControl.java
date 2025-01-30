package com.android.server.audio;

import android.app.AppOpsManager;
import android.content.ContentResolver;
import android.content.Context;
import android.media.AudioAttributes;
import android.media.AudioFocusInfo;
import android.media.AudioManager;
import android.media.AudioSystem;
import android.media.IAudioFocusDispatcher;
import android.media.MediaMetrics;
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
import com.android.server.enterprise.vpn.knoxvpn.KnoxVpnFirewallHelper;
import com.android.server.utils.EventLogger;
import com.samsung.android.audio.AudioManagerHelper;
import com.samsung.android.audio.Rune;
import com.samsung.android.media.MySpaceManager;
import com.samsung.android.media.SemAudioSystem;
import com.samsung.android.server.audio.AudioSettingsHelper;
import com.samsung.android.server.audio.ScreenSharingHelper;
import com.samsung.android.server.audio.utils.AudioUtils;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import java.util.function.Consumer;

/* loaded from: classes.dex */
public class MediaFocusControl implements PlayerFocusEnforcer {
    public final AppOpsManager mAppOps;
    public AudioService mAudioService;
    public AudioSettingsHelper mAudioSettingsHelper;
    public String mCallClientId;
    public final Context mContext;
    public int mDevice;
    public long mExtFocusChangeCounter;
    public final Object mExtFocusChangeLock;
    public PlayerFocusEnforcer mFocusEnforcer;
    public ArrayList mFocusFollowers;
    public Handler mFocusHandler;
    public HashMap mFocusOwnersForFocusPolicy;
    public IAudioPolicyCallback mFocusPolicy;
    public Stack mFocusStack;
    public HandlerThread mFocusThread;
    public FocusRequester mIgnoredFocus;
    public int mIgnoredUid;
    public boolean mIsMySpaceEffectFocus;
    public boolean mMultiAudioFocusEnabled;
    public ArrayList mMultiAudioFocusList;
    public MultiFocusStack mMultiFocusStack;
    public Handler mMySpaceHandler;
    public final Runnable mMySpaceRunnable;
    public boolean mNotifyFocusOwnerOnDuck;
    public IAudioPolicyCallback mPreviousFocusPolicy;
    public boolean mRingOrCallActive;
    public IBinder mSplitSoundCb;
    public FocusRequester mSplitSoundFR;
    public static final Object mAudioFocusLock = new Object();
    public static final EventLogger mEventLogger = new EventLogger(50, "focus commands as seen by MediaFocusControl");
    public static final int[] USAGES_TO_MUTE_IN_RING_OR_CALL = {1, 14};

    public long getFadeOutDurationOnFocusLossMillis(AudioAttributes audioAttributes) {
        return 0L;
    }

    public MediaFocusControl(Context context, PlayerFocusEnforcer playerFocusEnforcer) {
        this.mMultiAudioFocusEnabled = false;
        this.mRingOrCallActive = false;
        this.mExtFocusChangeLock = new Object();
        this.mMultiAudioFocusList = new ArrayList();
        this.mNotifyFocusOwnerOnDuck = true;
        this.mFocusFollowers = new ArrayList();
        this.mFocusPolicy = null;
        this.mPreviousFocusPolicy = null;
        this.mFocusOwnersForFocusPolicy = new HashMap();
        this.mIsMySpaceEffectFocus = false;
        this.mMySpaceRunnable = new Runnable() { // from class: com.android.server.audio.MediaFocusControl$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                MediaFocusControl.lambda$new$0();
            }
        };
        this.mCallClientId = null;
        this.mIgnoredFocus = null;
        this.mIgnoredUid = -1;
        this.mSplitSoundFR = null;
        this.mSplitSoundCb = null;
        this.mContext = context;
        this.mAppOps = (AppOpsManager) context.getSystemService("appops");
        this.mFocusEnforcer = playerFocusEnforcer;
        ContentResolver contentResolver = context.getContentResolver();
        this.mMultiAudioFocusEnabled = Settings.System.getIntForUser(contentResolver, "multi_audio_focus_enabled", 0, contentResolver.getUserId()) != 0;
        initFocusThreading();
    }

    public MediaFocusControl(Context context, PlayerFocusEnforcer playerFocusEnforcer, AudioService audioService) {
        this(context, playerFocusEnforcer);
        this.mAudioService = audioService;
        HandlerThread handlerThread = new HandlerThread("MediaFocusControl");
        handlerThread.start();
        this.mMySpaceHandler = new Handler(handlerThread.getLooper());
        int devicesForStream = AudioSystem.getDevicesForStream(3);
        this.mDevice = devicesForStream;
        if (AudioUtils.isWiredDeviceType(devicesForStream)) {
            Log.d("MediaFocusControl", "force change device " + this.mDevice + " to 2");
            this.mDevice = 2;
        }
        MultiFocusStack multiFocusStack = new MultiFocusStack();
        this.mMultiFocusStack = multiFocusStack;
        this.mFocusStack = multiFocusStack.getFocusStack(this.mDevice);
        this.mAudioSettingsHelper = AudioSettingsHelper.getInstance(context);
    }

    public void dump(PrintWriter printWriter) {
        printWriter.println("\nMediaFocusControl dump time: " + DateFormat.getTimeInstance().format(new Date()));
        dumpFocusStack(printWriter);
        printWriter.println(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        mEventLogger.dump(printWriter);
        dumpMultiAudioFocus(printWriter);
        printWriter.println("mIgnoredAudioFocusUid:" + this.mIgnoredUid);
    }

    @Override // com.android.server.audio.PlayerFocusEnforcer
    public boolean duckPlayers(FocusRequester focusRequester, FocusRequester focusRequester2, boolean z) {
        return this.mFocusEnforcer.duckPlayers(focusRequester, focusRequester2, z);
    }

    @Override // com.android.server.audio.PlayerFocusEnforcer
    public void restoreVShapedPlayers(FocusRequester focusRequester) {
        this.mFocusEnforcer.restoreVShapedPlayers(focusRequester);
        this.mFocusHandler.removeEqualMessages(2, new ForgetFadeUidInfo(focusRequester.getClientUid()));
    }

    @Override // com.android.server.audio.PlayerFocusEnforcer
    public void mutePlayersForCall(int[] iArr) {
        this.mFocusEnforcer.mutePlayersForCall(iArr);
    }

    @Override // com.android.server.audio.PlayerFocusEnforcer
    public void unmutePlayersForCall() {
        this.mFocusEnforcer.unmutePlayersForCall();
    }

    @Override // com.android.server.audio.PlayerFocusEnforcer
    public void forgetUid(int i) {
        this.mFocusEnforcer.forgetUid(i);
    }

    public void noFocusForSuspendedApp(String str, int i) {
        synchronized (mAudioFocusLock) {
            Iterator it = this.mFocusStack.iterator();
            ArrayList arrayList = new ArrayList();
            while (it.hasNext()) {
                FocusRequester focusRequester = (FocusRequester) it.next();
                if (focusRequester.hasSameUid(i) && focusRequester.hasSamePackage(str)) {
                    arrayList.add(focusRequester.getClientId());
                    mEventLogger.enqueue(new EventLogger.StringEvent("focus owner:" + focusRequester.getClientId() + " in uid:" + i + " pack: " + str + " getting AUDIOFOCUS_LOSS due to app suspension").printLog("MediaFocusControl"));
                    focusRequester.dispatchFocusChange(-1);
                }
            }
            Iterator it2 = arrayList.iterator();
            while (it2.hasNext()) {
                removeFocusStackEntry((String) it2.next(), false, true);
            }
        }
    }

    public boolean hasAudioFocusUsers() {
        boolean z;
        synchronized (mAudioFocusLock) {
            z = !this.mFocusStack.empty();
        }
        return z;
    }

    public void discardAudioFocusOwner() {
        synchronized (mAudioFocusLock) {
            if (!this.mFocusStack.empty()) {
                FocusRequester focusRequester = (FocusRequester) this.mFocusStack.pop();
                focusRequester.handleFocusLoss(-1, null, false);
                focusRequester.release();
            }
        }
    }

    public List getFocusStack() {
        ArrayList arrayList;
        synchronized (mAudioFocusLock) {
            arrayList = new ArrayList(this.mFocusStack.size());
            Iterator it = this.mFocusStack.iterator();
            while (it.hasNext()) {
                arrayList.add(((FocusRequester) it.next()).toAudioFocusInfo());
            }
        }
        return arrayList;
    }

    public boolean sendFocusLoss(AudioFocusInfo audioFocusInfo) {
        FocusRequester focusRequester;
        synchronized (mAudioFocusLock) {
            Iterator it = this.mFocusStack.iterator();
            while (true) {
                focusRequester = null;
                if (!it.hasNext()) {
                    break;
                }
                FocusRequester focusRequester2 = (FocusRequester) it.next();
                if (focusRequester2.getClientId().equals(audioFocusInfo.getClientId())) {
                    focusRequester2.handleFocusLoss(-1, null, false);
                    focusRequester = focusRequester2;
                    break;
                }
            }
            if (focusRequester == null) {
                return false;
            }
            this.mFocusStack.remove(focusRequester);
            focusRequester.release();
            return true;
        }
    }

    public final void notifyTopOfAudioFocusStack() {
        if (!this.mFocusStack.empty() && canReassignAudioFocus() && !this.mRingOrCallActive) {
            if (isDelayLossApp(((FocusRequester) this.mFocusStack.peek()).getPackageName())) {
                Log.d("MediaFocusControl", "notifyTopOfAudioFocusStack: " + ((FocusRequester) this.mFocusStack.peek()).getClientId());
                this.mFocusHandler.removeMessages(3, this.mFocusStack.peek());
                Handler handler = this.mFocusHandler;
                handler.sendMessageDelayed(handler.obtainMessage(4, this.mFocusStack.peek()), 500L);
            } else {
                ((FocusRequester) this.mFocusStack.peek()).handleFocusGain(1);
            }
        }
        if (!this.mMultiAudioFocusEnabled || this.mMultiAudioFocusList.isEmpty()) {
            return;
        }
        Iterator it = this.mMultiAudioFocusList.iterator();
        while (it.hasNext()) {
            FocusRequester focusRequester = (FocusRequester) it.next();
            if (isLockedFocusOwner(focusRequester)) {
                focusRequester.handleFocusGain(1);
            }
        }
    }

    public final void propagateFocusLossFromGain_syncAf(int i, FocusRequester focusRequester, boolean z) {
        LinkedList linkedList = new LinkedList();
        if (!this.mFocusStack.empty()) {
            Iterator it = this.mFocusStack.iterator();
            while (it.hasNext()) {
                FocusRequester focusRequester2 = (FocusRequester) it.next();
                if (focusRequester2.handleFocusLossFromGain(i, focusRequester, z)) {
                    linkedList.add(focusRequester2.getClientId());
                }
            }
        }
        if (this.mMultiAudioFocusEnabled && !this.mMultiAudioFocusList.isEmpty()) {
            Iterator it2 = this.mMultiAudioFocusList.iterator();
            while (it2.hasNext()) {
                FocusRequester focusRequester3 = (FocusRequester) it2.next();
                if (focusRequester == null || !TextUtils.equals(focusRequester.getClientId(), focusRequester3.getClientId())) {
                    if (focusRequester3.handleFocusLossFromGain(i, focusRequester, z)) {
                        linkedList.add(focusRequester3.getClientId());
                    }
                }
            }
        }
        Iterator it3 = linkedList.iterator();
        while (it3.hasNext()) {
            removeFocusStackEntry((String) it3.next(), false, true);
        }
    }

    public final void dumpFocusStack(PrintWriter printWriter) {
        printWriter.println("\nAudio Focus stack entries (last is top of stack):");
        synchronized (mAudioFocusLock) {
            dumpMultiSoundStack(printWriter);
            printWriter.println(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
            if (this.mFocusPolicy == null) {
                printWriter.println("No external focus policy\n");
            } else {
                printWriter.println("External focus policy: " + this.mFocusPolicy + ", focus owners:\n");
                dumpExtFocusPolicyFocusOwners(printWriter);
            }
        }
        printWriter.println(KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        printWriter.println(" Notify on duck:  " + this.mNotifyFocusOwnerOnDuck + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
        printWriter.println(" In ring or call: " + this.mRingOrCallActive + KnoxVpnFirewallHelper.DELIMITER_IP_RESTORE);
    }

    public final void removeFocusStackEntry(String str, boolean z, boolean z2) {
        if (!this.mFocusStack.empty() && ((FocusRequester) this.mFocusStack.peek()).hasSameClient(str)) {
            FocusRequester focusRequester = (FocusRequester) this.mFocusStack.pop();
            focusRequester.maybeRelease();
            r1 = z2 ? focusRequester.toAudioFocusInfo() : null;
            if (z) {
                notifyTopOfAudioFocusStack();
            }
        } else {
            Iterator it = this.mFocusStack.iterator();
            while (it.hasNext()) {
                FocusRequester focusRequester2 = (FocusRequester) it.next();
                if (focusRequester2.hasSameClient(str)) {
                    Log.i("MediaFocusControl", "AudioFocus  removeFocusStackEntry(): removing entry for " + str);
                    it.remove();
                    if (z2) {
                        r1 = focusRequester2.toAudioFocusInfo();
                    }
                    focusRequester2.maybeRelease();
                }
            }
        }
        if (r1 != null) {
            r1.clearLossReceived();
            notifyExtPolicyFocusLoss_syncAf(r1, false);
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

    public final void removeFocusStackEntryOnDeath(IBinder iBinder) {
        boolean z = !this.mFocusStack.isEmpty() && ((FocusRequester) this.mFocusStack.peek()).hasSameBinder(iBinder);
        Iterator it = this.mFocusStack.iterator();
        while (it.hasNext()) {
            FocusRequester focusRequester = (FocusRequester) it.next();
            if (focusRequester.hasSameBinder(iBinder)) {
                Log.i("MediaFocusControl", "AudioFocus  removeFocusStackEntryOnDeath(): removing entry for " + iBinder);
                if ("com.sec.android.app.voicenote".equals(focusRequester.getPackageName()) || "com.sec.android.app.voicerecorder".equals(focusRequester.getPackageName())) {
                    Log.i("MediaFocusControl", "FORCE_NONE for Media");
                    AudioSystemAdapter.getDefaultAdapter().setForceUse(1, 0);
                } else if ("com.sec.android.app.dmb".equals(focusRequester.getPackageName())) {
                    SemAudioSystem.setPolicyParameters("g_dmb_spk_enable=off");
                }
                mEventLogger.enqueue(new EventLogger.StringEvent("focus requester:" + focusRequester.getClientId() + " in uid:" + focusRequester.getClientUid() + " pack:" + focusRequester.getPackageName() + " died"));
                notifyExtPolicyFocusLoss_syncAf(focusRequester.toAudioFocusInfo(), false);
                it.remove();
                focusRequester.release();
            }
        }
        if (z) {
            notifyTopOfAudioFocusStack();
        }
    }

    public final void removeFocusEntryForExtPolicyOnDeath(IBinder iBinder) {
        if (this.mFocusOwnersForFocusPolicy.isEmpty()) {
            return;
        }
        Iterator it = this.mFocusOwnersForFocusPolicy.entrySet().iterator();
        while (it.hasNext()) {
            FocusRequester focusRequester = (FocusRequester) ((Map.Entry) it.next()).getValue();
            if (focusRequester.hasSameBinder(iBinder)) {
                it.remove();
                mEventLogger.enqueue(new EventLogger.StringEvent("focus requester:" + focusRequester.getClientId() + " in uid:" + focusRequester.getClientUid() + " pack:" + focusRequester.getPackageName() + " died"));
                focusRequester.release();
                notifyExtFocusPolicyFocusAbandon_syncAf(focusRequester.toAudioFocusInfo());
                return;
            }
        }
    }

    public final boolean canReassignAudioFocus() {
        return this.mFocusStack.isEmpty() || !isLockedFocusOwner((FocusRequester) this.mFocusStack.peek());
    }

    public final boolean isLockedFocusOwner(FocusRequester focusRequester) {
        return focusRequester.hasSameClient("AudioFocus_For_Phone_Ring_And_Calls") || focusRequester.isLockedFocusOwner();
    }

    public final int pushBelowLockedFocusOwnersAndPropagate(FocusRequester focusRequester) {
        int size = this.mFocusStack.size();
        for (int size2 = this.mFocusStack.size() - 1; size2 >= 0; size2--) {
            if (isLockedFocusOwner((FocusRequester) this.mFocusStack.elementAt(size2))) {
                size = size2;
            }
        }
        if (size == this.mFocusStack.size()) {
            Log.e("MediaFocusControl", "No exclusive focus owner found in propagateFocusLossFromGain_syncAf()", new Exception());
            propagateFocusLossFromGain_syncAf(focusRequester.getGainRequest(), focusRequester, false);
            this.mFocusStack.push(focusRequester);
            return 1;
        }
        this.mFocusStack.insertElementAt(focusRequester, size);
        LinkedList linkedList = new LinkedList();
        for (int i = size - 1; i >= 0; i--) {
            if (((FocusRequester) this.mFocusStack.elementAt(i)).handleFocusLossFromGain(focusRequester.getGainRequest(), focusRequester, false)) {
                linkedList.add(((FocusRequester) this.mFocusStack.elementAt(i)).getClientId());
            }
        }
        Iterator it = linkedList.iterator();
        while (it.hasNext()) {
            removeFocusStackEntry((String) it.next(), false, true);
        }
        return 2;
    }

    public class AudioFocusDeathHandler implements IBinder.DeathRecipient {
        public IBinder mCb;

        public AudioFocusDeathHandler(IBinder iBinder) {
            this.mCb = iBinder;
        }

        @Override // android.os.IBinder.DeathRecipient
        public void binderDied() {
            synchronized (MediaFocusControl.mAudioFocusLock) {
                if (MediaFocusControl.this.mFocusPolicy != null) {
                    MediaFocusControl.this.removeFocusEntryForExtPolicyOnDeath(this.mCb);
                } else {
                    for (int i = 0; i < MediaFocusControl.this.mMultiFocusStack.size(); i++) {
                        MediaFocusControl mediaFocusControl = MediaFocusControl.this;
                        mediaFocusControl.mFocusStack = (Stack) mediaFocusControl.mMultiFocusStack.valueAt(i);
                        MediaFocusControl.this.removeFocusStackEntryOnDeath(this.mCb);
                    }
                    MediaFocusControl mediaFocusControl2 = MediaFocusControl.this;
                    mediaFocusControl2.mFocusStack = mediaFocusControl2.mMultiFocusStack.getFocusStack(0);
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
                    if (MediaFocusControl.this.mIgnoredFocus != null && MediaFocusControl.this.mIgnoredFocus.hasSameBinder(this.mCb)) {
                        MediaFocusControl.this.mIgnoredFocus.release();
                        MediaFocusControl.this.mIgnoredFocus = null;
                    }
                }
            }
        }
    }

    public void setDuckingInExtPolicyAvailable(boolean z) {
        this.mNotifyFocusOwnerOnDuck = !z;
    }

    public boolean mustNotifyFocusOwnerOnDuck() {
        return this.mNotifyFocusOwnerOnDuck;
    }

    public void addFocusFollower(IAudioPolicyCallback iAudioPolicyCallback) {
        boolean z;
        if (iAudioPolicyCallback == null) {
            return;
        }
        synchronized (mAudioFocusLock) {
            Iterator it = this.mFocusFollowers.iterator();
            while (true) {
                if (!it.hasNext()) {
                    z = false;
                    break;
                } else if (((IAudioPolicyCallback) it.next()).asBinder().equals(iAudioPolicyCallback.asBinder())) {
                    z = true;
                    break;
                }
            }
            if (z) {
                return;
            }
            this.mFocusFollowers.add(iAudioPolicyCallback);
            notifyExtPolicyCurrentFocusAsync(iAudioPolicyCallback);
        }
    }

    public void removeFocusFollower(IAudioPolicyCallback iAudioPolicyCallback) {
        if (iAudioPolicyCallback == null) {
            return;
        }
        synchronized (mAudioFocusLock) {
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
        }
    }

    public void setFocusPolicy(IAudioPolicyCallback iAudioPolicyCallback, boolean z) {
        if (iAudioPolicyCallback == null) {
            return;
        }
        synchronized (mAudioFocusLock) {
            if (z) {
                this.mPreviousFocusPolicy = this.mFocusPolicy;
            }
            this.mFocusPolicy = iAudioPolicyCallback;
        }
    }

    public void unsetFocusPolicy(IAudioPolicyCallback iAudioPolicyCallback, boolean z) {
        if (iAudioPolicyCallback == null) {
            return;
        }
        synchronized (mAudioFocusLock) {
            if (this.mFocusPolicy == iAudioPolicyCallback) {
                if (z) {
                    this.mFocusPolicy = this.mPreviousFocusPolicy;
                } else {
                    this.mFocusPolicy = null;
                }
            }
        }
    }

    public void notifyExtPolicyCurrentFocusAsync(final IAudioPolicyCallback iAudioPolicyCallback) {
        new Thread() { // from class: com.android.server.audio.MediaFocusControl.1
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                synchronized (MediaFocusControl.mAudioFocusLock) {
                    if (MediaFocusControl.this.mFocusStack.isEmpty()) {
                        return;
                    }
                    try {
                        iAudioPolicyCallback.notifyAudioFocusGrant(((FocusRequester) MediaFocusControl.this.mFocusStack.peek()).toAudioFocusInfo(), 1);
                    } catch (RemoteException e) {
                        Log.e("MediaFocusControl", "Can't call notifyAudioFocusGrant() on IAudioPolicyCallback " + iAudioPolicyCallback.asBinder(), e);
                    }
                }
            }
        }.start();
    }

    public void notifyExtPolicyFocusGrant_syncAf(AudioFocusInfo audioFocusInfo, int i) {
        Iterator it = this.mFocusFollowers.iterator();
        while (it.hasNext()) {
            IAudioPolicyCallback iAudioPolicyCallback = (IAudioPolicyCallback) it.next();
            try {
                iAudioPolicyCallback.notifyAudioFocusGrant(audioFocusInfo, i);
            } catch (RemoteException e) {
                Log.e("MediaFocusControl", "Can't call notifyAudioFocusGrant() on IAudioPolicyCallback " + iAudioPolicyCallback.asBinder(), e);
            }
        }
        if (Rune.SEC_AUDIO_CARLIFE && this.mAudioService != null && i == 1 && audioFocusInfo.getGainRequest() == 3) {
            this.mAudioService.notifyCarLifeEvent(audioFocusInfo.getPackageName(), 1);
        }
    }

    public void notifyExtPolicyFocusLoss_syncAf(AudioFocusInfo audioFocusInfo, boolean z) {
        Iterator it = this.mFocusFollowers.iterator();
        while (it.hasNext()) {
            IAudioPolicyCallback iAudioPolicyCallback = (IAudioPolicyCallback) it.next();
            try {
                iAudioPolicyCallback.notifyAudioFocusLoss(audioFocusInfo, z);
            } catch (RemoteException e) {
                Log.e("MediaFocusControl", "Can't call notifyAudioFocusLoss() on IAudioPolicyCallback " + iAudioPolicyCallback.asBinder(), e);
            }
        }
        if (Rune.SEC_AUDIO_CARLIFE && this.mAudioService != null && audioFocusInfo.getGainRequest() == 3) {
            this.mAudioService.notifyCarLifeEvent(audioFocusInfo.getPackageName(), 2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x002d  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean notifyExtFocusPolicyFocusRequest_syncAf(AudioFocusInfo audioFocusInfo, IAudioFocusDispatcher iAudioFocusDispatcher, IBinder iBinder) {
        boolean z;
        synchronized (this.mExtFocusChangeLock) {
            long j = this.mExtFocusChangeCounter;
            this.mExtFocusChangeCounter = 1 + j;
            audioFocusInfo.setGen(j);
        }
        FocusRequester focusRequester = (FocusRequester) this.mFocusOwnersForFocusPolicy.get(audioFocusInfo.getClientId());
        try {
            if (focusRequester != null) {
                if (!focusRequester.hasSameDispatcher(iAudioFocusDispatcher)) {
                    focusRequester.release();
                } else {
                    z = false;
                    if (z) {
                        AudioFocusDeathHandler audioFocusDeathHandler = new AudioFocusDeathHandler(iBinder);
                        try {
                            iBinder.linkToDeath(audioFocusDeathHandler, 0);
                            this.mFocusOwnersForFocusPolicy.put(audioFocusInfo.getClientId(), new FocusRequester(audioFocusInfo, iAudioFocusDispatcher, iBinder, audioFocusDeathHandler, this));
                        } catch (RemoteException unused) {
                            return false;
                        }
                    }
                    this.mFocusPolicy.notifyAudioFocusRequest(audioFocusInfo, 1);
                    return true;
                }
            }
            this.mFocusPolicy.notifyAudioFocusRequest(audioFocusInfo, 1);
            return true;
        } catch (RemoteException e) {
            Log.e("MediaFocusControl", "Can't call notifyAudioFocusRequest() on IAudioPolicyCallback " + this.mFocusPolicy.asBinder(), e);
            return false;
        }
        z = true;
        if (z) {
        }
    }

    public void setFocusRequestResultFromExtPolicy(AudioFocusInfo audioFocusInfo, int i) {
        FocusRequester focusRequester;
        synchronized (this.mExtFocusChangeLock) {
            if (audioFocusInfo.getGen() > this.mExtFocusChangeCounter) {
                return;
            }
            if (i == 0) {
                focusRequester = (FocusRequester) this.mFocusOwnersForFocusPolicy.remove(audioFocusInfo.getClientId());
            } else {
                focusRequester = (FocusRequester) this.mFocusOwnersForFocusPolicy.get(audioFocusInfo.getClientId());
            }
            if (focusRequester != null) {
                focusRequester.dispatchFocusResultFromExtPolicy(i);
            }
        }
    }

    public boolean notifyExtFocusPolicyFocusAbandon_syncAf(AudioFocusInfo audioFocusInfo) {
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

    public int dispatchFocusChange(AudioFocusInfo audioFocusInfo, int i) {
        FocusRequester focusRequester;
        synchronized (mAudioFocusLock) {
            if (this.mFocusPolicy == null) {
                return 0;
            }
            if (i == -1) {
                focusRequester = (FocusRequester) this.mFocusOwnersForFocusPolicy.remove(audioFocusInfo.getClientId());
            } else {
                focusRequester = (FocusRequester) this.mFocusOwnersForFocusPolicy.get(audioFocusInfo.getClientId());
            }
            if (focusRequester == null) {
                return 0;
            }
            return focusRequester.dispatchFocusChange(i);
        }
    }

    public final void dumpExtFocusPolicyFocusOwners(PrintWriter printWriter) {
        Iterator it = this.mFocusOwnersForFocusPolicy.entrySet().iterator();
        while (it.hasNext()) {
            ((FocusRequester) ((Map.Entry) it.next()).getValue()).dump(printWriter);
        }
    }

    public int getCurrentAudioFocus() {
        synchronized (mAudioFocusLock) {
            if (this.mFocusStack.empty()) {
                return 0;
            }
            return ((FocusRequester) this.mFocusStack.peek()).getGainRequest();
        }
    }

    public static int getFocusRampTimeMs(int i, AudioAttributes audioAttributes) {
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

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r13v1 */
    /* JADX WARN: Type inference failed for: r13v2, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r13v6 */
    /* JADX WARN: Type inference failed for: r14v1 */
    /* JADX WARN: Type inference failed for: r14v2, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r14v3 */
    /* JADX WARN: Type inference failed for: r9v2 */
    /* JADX WARN: Type inference failed for: r9v3, types: [boolean, int] */
    /* JADX WARN: Type inference failed for: r9v5 */
    public int requestAudioFocus(AudioAttributes audioAttributes, int i, IBinder iBinder, IAudioFocusDispatcher iAudioFocusDispatcher, String str, String str2, String str3, int i2, int i3, boolean z, int i4) {
        int i5;
        ?? r13;
        int i6;
        AudioFocusInfo audioFocusInfo;
        ?? r9;
        boolean z2;
        boolean z3;
        int i7;
        int i8;
        ?? r14;
        FocusRequester focusRequester;
        boolean z4;
        String str4;
        FocusRequester focusRequester2;
        boolean z5;
        boolean z6;
        new MediaMetrics.Item("audio.focus").setUid(Binder.getCallingUid()).set(MediaMetrics.Property.CALLING_PACKAGE, str2).set(MediaMetrics.Property.CLIENT_NAME, str).set(MediaMetrics.Property.EVENT, "requestAudioFocus").set(MediaMetrics.Property.FLAGS, Integer.valueOf(i2)).set(MediaMetrics.Property.FOCUS_CHANGE_HINT, AudioManager.audioFocusToString(i)).record();
        int callingUid = i2 == 8 ? i4 : Binder.getCallingUid();
        mEventLogger.enqueue(new EventLogger.StringEvent("requestAudioFocus() from uid/pid " + callingUid + "/" + Binder.getCallingPid() + " AA=" + audioAttributes.usageToString() + "/" + audioAttributes.contentTypeToString() + " clientId=" + str + " callingPack=" + str2 + " req=" + i + " flags=0x" + Integer.toHexString(i2) + " sdk=" + i3).printLog("MediaFocusControl"));
        if (!iBinder.pingBinder()) {
            Log.e("MediaFocusControl", " AudioFocus DOA client for requestAudioFocus(), aborting.");
            return 0;
        }
        if (i2 != 8 && this.mAppOps.noteOp(32, Binder.getCallingUid(), str2, str3, (String) null) != 0) {
            return 0;
        }
        if (Rune.SEC_AUDIO_SUPPORT_ACH_RINGTONE && TextUtils.equals(str2, "com.android.systemui") && i != 1 && this.mAudioService.getStreamVolume(AudioAttributes.toLegacyStreamType(audioAttributes)) == 0) {
            Log.d("MediaFocusControl", "Notification volume is 0,  failing requestAudioFocus()");
            return 0;
        }
        boolean isConnectedAndroidAuto = this.mAudioService.isConnectedAndroidAuto();
        synchronized (mAudioFocusLock) {
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
            boolean z7 = (!this.mRingOrCallActive) & ("AudioFocus_For_Phone_Ring_And_Calls".compareTo(str) == 0);
            if (z7) {
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
            if (ScreenSharingHelper.isSplitSoundEnabled() && ("com.android.server.telecom".equals(str2) || ScreenSharingHelper.isAllowed(audioAttributes))) {
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
                z2 = false;
            } else {
                if ((i2 & 1) == 0) {
                    return 0;
                }
                r9 = 0;
                z2 = r13;
            }
            if (this.mFocusPolicy != null) {
                return notifyExtFocusPolicyFocusRequest_syncAf(audioFocusInfo, iAudioFocusDispatcher, iBinder) ? i5 : r9;
            }
            AudioFocusDeathHandler audioFocusDeathHandler = new AudioFocusDeathHandler(iBinder);
            try {
                iBinder.linkToDeath(audioFocusDeathHandler, r9);
                if (fromPhoneCall(str) && !AudioManagerHelper.isFMPlayerActive() && !this.mFocusStack.empty() && this.mAudioService.isPlaybackActiveForUid(((FocusRequester) this.mFocusStack.peek()).getClientUid())) {
                    if (isWiredHeadsetOrHeadphonePlugged()) {
                        MySpaceManager.playMySpaceEffect((int) r13);
                        addMySpaceEffectDelayLocked();
                        Log.v("MediaFocusControl", "requestAudioFocus: playMySpaceEffect MUSIC FADE OUT");
                    }
                    this.mIsMySpaceEffectFocus = r13;
                }
                if (this.mFocusStack.empty() || !((FocusRequester) this.mFocusStack.peek()).hasSameClient(str)) {
                    z3 = r13;
                    i7 = i;
                } else {
                    FocusRequester focusRequester3 = (FocusRequester) this.mFocusStack.peek();
                    z3 = r13;
                    i7 = i;
                    if (focusRequester3.getGainRequest() == i7 && focusRequester3.getGrantFlags() == i2) {
                        iBinder.unlinkToDeath(audioFocusDeathHandler, r9);
                        notifyExtPolicyFocusGrant_syncAf(focusRequester3.toAudioFocusInfo(), z3 ? 1 : 0);
                        if (i7 == z3) {
                            resetFocusLossReceived();
                        }
                        return z3 ? 1 : 0;
                    }
                    if (!z2) {
                        this.mFocusStack.pop();
                        focusRequester3.release();
                    }
                }
                removeFocusStackEntry(str, r9, r9);
                int i9 = i6;
                FocusRequester focusRequester4 = this.mMultiFocusStack.getFocusRequester(i9, str, z3);
                if (focusRequester4 != null) {
                    focusRequester4.resetFocusLossReceived();
                }
                if (focusRequester4 == null) {
                    i8 = i9;
                    r14 = z3;
                    focusRequester = new FocusRequester(audioAttributes, i, i2, iAudioFocusDispatcher, iBinder, str, audioFocusDeathHandler, str2, i8, this, i3);
                } else {
                    i8 = i9;
                    r14 = z3;
                    focusRequester = focusRequester4;
                }
                focusRequester.setDevice(getAppDevice(focusRequester.getCallingUid()));
                if (isConnectedAndroidAuto) {
                    clearMultiAudiofocusfromAndroidAuto();
                    str4 = str2;
                    z4 = z;
                } else {
                    if (this.mMultiAudioFocusEnabled && i7 == r14 && !isRemoteMicState(audioAttributes)) {
                        if (z7) {
                            if (!this.mMultiAudioFocusList.isEmpty()) {
                                Iterator it = this.mMultiAudioFocusList.iterator();
                                while (it.hasNext()) {
                                    ((FocusRequester) it.next()).handleFocusLossFromGain(i7, focusRequester, z);
                                }
                            }
                        } else {
                            if (!this.mMultiAudioFocusList.isEmpty()) {
                                Iterator it2 = this.mMultiAudioFocusList.iterator();
                                while (it2.hasNext()) {
                                    if (((FocusRequester) it2.next()).getClientUid() == Binder.getCallingUid()) {
                                        z6 = false;
                                        break;
                                    }
                                }
                            }
                            z6 = r14;
                            if (z6) {
                                this.mMultiAudioFocusList.add(focusRequester);
                                this.mMultiFocusStack.pushFocusRequester(focusRequester.getDevice(), focusRequester);
                            }
                            focusRequester.handleFocusGainFromRequest(r14);
                            notifyExtPolicyFocusGrant_syncAf(focusRequester.toAudioFocusInfo(), r14);
                            return r14;
                        }
                    }
                    z4 = z;
                    if (this.mIgnoredUid == i8) {
                        Log.i("MediaFocusControl", "Ignore AudioFocus for " + str2);
                        FocusRequester focusRequester5 = this.mIgnoredFocus;
                        if (focusRequester5 != null) {
                            if (focusRequester5.hasSameClient(str)) {
                                z5 = false;
                            } else {
                                z5 = false;
                                this.mIgnoredFocus.handleFocusLossFromGain(i7, focusRequester, false);
                            }
                            this.mIgnoredFocus.release();
                            this.mIgnoredFocus = null;
                        } else {
                            z5 = false;
                        }
                        this.mIgnoredFocus = focusRequester;
                        if (!this.mFocusStack.empty() && "com.google.android.projection.gearhead".equals(((FocusRequester) this.mFocusStack.peek()).getPackageName())) {
                            Log.d("MediaFocusControl", "Loss to Android Auto");
                            ((FocusRequester) this.mFocusStack.peek()).handleFocusLossFromGain(i7, focusRequester, z5);
                        }
                        focusRequester.handleFocusGainFromRequest(r14);
                        notifyExtPolicyFocusGrant_syncAf(focusRequester.toAudioFocusInfo(), r14);
                        return r14;
                    }
                    str4 = str2;
                    if (z7 && (focusRequester2 = this.mIgnoredFocus) != 0) {
                        focusRequester2.handleFocusLossFromGain(i7, focusRequester, r14);
                    }
                }
                if (z2) {
                    int pushBelowLockedFocusOwnersAndPropagate = pushBelowLockedFocusOwnersAndPropagate(focusRequester);
                    if (pushBelowLockedFocusOwnersAndPropagate != 0) {
                        notifyExtPolicyFocusGrant_syncAf(focusRequester.toAudioFocusInfo(), pushBelowLockedFocusOwnersAndPropagate);
                    }
                    return pushBelowLockedFocusOwnersAndPropagate;
                }
                propagateFocusLossFromGain_syncAf(i7, focusRequester, z4);
                if ("com.android.server.telecom".equals(str4) || audioAttributes.getContentType() == r14) {
                    propagateFocusLossFromGainToAll(i7);
                }
                this.mFocusStack.push(focusRequester);
                focusRequester.handleFocusGainFromRequest(r14);
                notifyExtPolicyFocusGrant_syncAf(focusRequester.toAudioFocusInfo(), r14);
                if (z7 & true) {
                    runAudioCheckerForRingOrCallAsync(r14);
                }
                return r14;
            } catch (RemoteException unused) {
                int i10 = r9;
                Log.w("MediaFocusControl", "AudioFocus  requestAudioFocus() could not link to " + iBinder + " binder death");
                return i10;
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:89:0x0246 A[Catch: all -> 0x024c, TryCatch #0 {, blocks: (B:6:0x0069, B:9:0x007d, B:11:0x0081, B:13:0x0085, B:15:0x0091, B:16:0x0099, B:19:0x00a3, B:21:0x00a9, B:23:0x00f4, B:26:0x00fa, B:27:0x0108, B:29:0x010e, B:32:0x011a, B:36:0x0123, B:38:0x0136, B:39:0x0138, B:41:0x013c, B:43:0x0142, B:45:0x014a, B:47:0x0158, B:48:0x0164, B:50:0x0168, B:52:0x018c, B:55:0x0190, B:57:0x0195, B:59:0x0199, B:62:0x01a5, B:63:0x01ab, B:65:0x01b1, B:76:0x01bd, B:68:0x01e3, B:71:0x01e7, B:79:0x0205, B:80:0x0224, B:83:0x022a, B:84:0x022e, B:86:0x0234, B:87:0x023e, B:89:0x0246, B:90:0x024a, B:100:0x00b0, B:101:0x00c7, B:103:0x00cf, B:105:0x00df, B:109:0x00f1, B:110:0x00ee), top: B:5:0x0069, outer: #1 }] */
    /* JADX WARN: Type inference failed for: r1v22 */
    /* JADX WARN: Type inference failed for: r1v23, types: [android.os.IBinder, com.android.server.audio.FocusRequester] */
    /* JADX WARN: Type inference failed for: r1v30 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int abandonAudioFocus(IAudioFocusDispatcher iAudioFocusDispatcher, String str, AudioAttributes audioAttributes, String str2) {
        boolean z;
        boolean z2;
        ?? r1;
        FocusRequester focusRequester;
        new MediaMetrics.Item("audio.focus").setUid(Binder.getCallingUid()).set(MediaMetrics.Property.CALLING_PACKAGE, str2).set(MediaMetrics.Property.CLIENT_NAME, str).set(MediaMetrics.Property.EVENT, "abandonAudioFocus").record();
        mEventLogger.enqueue(new EventLogger.StringEvent("abandonAudioFocus() from uid/pid " + Binder.getCallingUid() + "/" + Binder.getCallingPid() + " clientId=" + str).printLog("MediaFocusControl"));
        try {
        } catch (ConcurrentModificationException e) {
            Log.e("MediaFocusControl", "FATAL EXCEPTION AudioFocus  abandonAudioFocus() caused " + e);
            e.printStackTrace();
        }
        synchronized (mAudioFocusLock) {
            boolean isConnectedAndroidAuto = this.mAudioService.isConnectedAndroidAuto();
            boolean z3 = this.mRingOrCallActive & ("AudioFocus_For_Phone_Ring_And_Calls".compareTo(str) == 0);
            if (z3) {
                this.mRingOrCallActive = false;
            }
            AudioAttributes audioAttributes2 = (audioAttributes != null || (focusRequester = this.mMultiFocusStack.getFocusRequester(Binder.getCallingUid(), str, false)) == null) ? audioAttributes : focusRequester.getAudioAttributes();
            if (str.equals(this.mCallClientId) || (audioAttributes2 != null && (audioAttributes2.getContentType() == 1 || audioAttributes2.getContentType() == 4))) {
                Log.d("MediaFocusControl", "abandonAudioFocus, clientId = " + str);
                for (int i = 0; i < this.mMultiFocusStack.size(); i++) {
                    Stack stack = (Stack) this.mMultiFocusStack.valueAt(i);
                    this.mFocusStack = stack;
                    if (stack.empty() || !((FocusRequester) this.mFocusStack.peek()).hasSameClient(str)) {
                        notifyTopOfAudioFocusStack();
                    }
                }
            }
            if (this.mMultiAudioFocusEnabled && !isConnectedAndroidAuto) {
                ArrayList selectFocusList = selectFocusList(Binder.getCallingUid());
                this.mMultiAudioFocusList = selectFocusList;
                Iterator it = selectFocusList.iterator();
                while (it.hasNext()) {
                    FocusRequester focusRequester2 = (FocusRequester) it.next();
                    if (focusRequester2.hasSameClient(str) && focusRequester2.getGainRequest() > 0) {
                        z = true;
                        break;
                    }
                }
            }
            z = false;
            this.mFocusStack = selectFocusStack(Binder.getCallingUid());
            if (str.equals(this.mCallClientId)) {
                this.mCallClientId = null;
            }
            if (this.mIsMySpaceEffectFocus && fromPhoneCall(str) && !this.mFocusStack.empty() && ((FocusRequester) this.mFocusStack.peek()).hasSameClient(str)) {
                MySpaceManager.playMySpaceEffect(0);
                this.mIsMySpaceEffectFocus = false;
                Log.v("MediaFocusControl", "abandonAudioFocus: playMySpaceEffect MUSIC FADE IN");
            }
            if (this.mFocusPolicy != null) {
                z2 = z;
                if (notifyExtFocusPolicyFocusAbandon_syncAf(new AudioFocusInfo(audioAttributes2, Binder.getCallingUid(), str, str2, 0, 0, 0, 0))) {
                    return 1;
                }
            } else {
                z2 = z;
            }
            removeFocusStackEntry(str, true, true);
            if (!isConnectedAndroidAuto) {
                if (this.mMultiAudioFocusEnabled) {
                    boolean z4 = z2;
                    if (!this.mMultiAudioFocusList.isEmpty() && z4) {
                        Iterator it2 = this.mMultiAudioFocusList.iterator();
                        while (it2.hasNext()) {
                            FocusRequester focusRequester3 = (FocusRequester) it2.next();
                            if (focusRequester3.hasSameClient(str)) {
                                Log.d("MediaFocusControl", "abandonAudioFocus( remove " + focusRequester3.getPackageName() + " )");
                                focusRequester3.release();
                                it2.remove();
                            } else if (!this.mRingOrCallActive) {
                                Log.d("MediaFocusControl", "abandonAudioFocus( AUDIOFOCUS_GAIN :: packagename = " + focusRequester3.getPackageName());
                                focusRequester3.handleFocusGain(1);
                            }
                        }
                    } else {
                        Log.d("MediaFocusControl", "skip abandonAudioFocus because focus granted " + z4 + ", " + str);
                    }
                }
                FocusRequester focusRequester4 = this.mIgnoredFocus;
                if (focusRequester4 != null) {
                    if (z3) {
                        focusRequester4.handleFocusGain(1);
                    } else if (focusRequester4.hasSameClient(str)) {
                        this.mIgnoredFocus.release();
                        r1 = 0;
                        this.mIgnoredFocus = null;
                        this.mSplitSoundFR = r1;
                        this.mSplitSoundCb = r1;
                        if (z3 & true) {
                            runAudioCheckerForRingOrCallAsync(false);
                        }
                        return 1;
                    }
                }
            }
            r1 = 0;
            this.mSplitSoundFR = r1;
            this.mSplitSoundCb = r1;
            if (z3 & true) {
            }
            return 1;
        }
    }

    public void unregisterAudioFocusClient(String str) {
        synchronized (mAudioFocusLock) {
            removeFocusStackEntry(str, false, true);
        }
    }

    public final void runAudioCheckerForRingOrCallAsync(final boolean z) {
        new Thread() { // from class: com.android.server.audio.MediaFocusControl.2
            @Override // java.lang.Thread, java.lang.Runnable
            public void run() {
                if (z) {
                    try {
                        Thread.sleep(100L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                synchronized (MediaFocusControl.mAudioFocusLock) {
                    if (MediaFocusControl.this.mRingOrCallActive) {
                        MediaFocusControl.this.mFocusEnforcer.mutePlayersForCall(MediaFocusControl.USAGES_TO_MUTE_IN_RING_OR_CALL);
                    } else {
                        MediaFocusControl.this.mFocusEnforcer.unmutePlayersForCall();
                    }
                }
            }
        }.start();
    }

    public void updateMultiAudioFocus(boolean z) {
        Log.d("MediaFocusControl", "updateMultiAudioFocus( " + z + " )");
        this.mMultiAudioFocusEnabled = z;
        handleUpdateMultiAudioFocus();
    }

    public boolean getMultiAudioFocusEnabled() {
        return this.mMultiAudioFocusEnabled;
    }

    public final void dumpMultiAudioFocus(PrintWriter printWriter) {
        printWriter.println("Multi Audio Focus enabled :" + this.mMultiAudioFocusEnabled);
        if (this.mMultiAudioFocusList.isEmpty()) {
            return;
        }
        printWriter.println("Multi Audio Focus List:");
        printWriter.println("device:" + Integer.toHexString(this.mDevice));
        printWriter.println("size:" + this.mMultiAudioFocusList.size());
        printWriter.println("------------------------------");
        Iterator it = this.mMultiAudioFocusList.iterator();
        while (it.hasNext()) {
            ((FocusRequester) it.next()).dump(printWriter);
        }
        printWriter.println("------------------------------");
    }

    public final void postForgetUidLater(int i) {
        Handler handler = this.mFocusHandler;
        handler.sendMessageDelayed(handler.obtainMessage(2, new ForgetFadeUidInfo(i)), 2000L);
    }

    public final void initFocusThreading() {
        HandlerThread handlerThread = new HandlerThread("MediaFocusControl");
        this.mFocusThread = handlerThread;
        handlerThread.start();
        this.mFocusHandler = new Handler(this.mFocusThread.getLooper()) { // from class: com.android.server.audio.MediaFocusControl.3
            @Override // android.os.Handler
            public void handleMessage(Message message) {
                int i = message.what;
                if (i == 1) {
                    synchronized (MediaFocusControl.mAudioFocusLock) {
                        FocusRequester focusRequester = (FocusRequester) message.obj;
                        if (focusRequester.isInFocusLossLimbo()) {
                            focusRequester.dispatchFocusChange(-1);
                            focusRequester.release();
                            MediaFocusControl.this.postForgetUidLater(focusRequester.getClientUid());
                        }
                    }
                    return;
                }
                if (i == 2) {
                    MediaFocusControl.this.mFocusEnforcer.forgetUid(((ForgetFadeUidInfo) message.obj).mUid);
                    return;
                }
                if (i != 3) {
                    if (i != 4) {
                        return;
                    }
                    synchronized (MediaFocusControl.mAudioFocusLock) {
                        FocusRequester focusRequester2 = (FocusRequester) message.obj;
                        if (!MediaFocusControl.this.mFocusStack.empty() && ((FocusRequester) MediaFocusControl.this.mFocusStack.peek()).hasSameClient(focusRequester2.getClientId())) {
                            Log.d("MediaFocusControl", "handleMessage: MSG_DELAY_GAIN_AUDIO_FOCUS clientId = " + focusRequester2.getClientId());
                            ((FocusRequester) MediaFocusControl.this.mFocusStack.peek()).handleFocusGain(1);
                        }
                    }
                    return;
                }
                synchronized (MediaFocusControl.mAudioFocusLock) {
                    FocusRequester focusRequester3 = (FocusRequester) message.obj;
                    if (!MediaFocusControl.this.mFocusStack.empty() && !((FocusRequester) MediaFocusControl.this.mFocusStack.peek()).hasSameClient(focusRequester3.getClientId())) {
                        Iterator it = MediaFocusControl.this.mFocusStack.iterator();
                        while (it.hasNext()) {
                            FocusRequester focusRequester4 = (FocusRequester) it.next();
                            if (focusRequester4.hasSameClient(focusRequester3.getClientId())) {
                                Log.d("MediaFocusControl", "handleMessage: MSG_DELAY_LOSS_AUDIO_FOCUS clientId = " + focusRequester3.getClientId());
                                it.remove();
                                focusRequester4.handleFocusLoss(-1, null, false);
                                focusRequester4.release();
                            }
                        }
                    }
                }
            }
        };
    }

    public final class ForgetFadeUidInfo {
        public final int mUid;

        public ForgetFadeUidInfo(int i) {
            this.mUid = i;
        }

        public boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            return obj != null && ForgetFadeUidInfo.class == obj.getClass() && ((ForgetFadeUidInfo) obj).mUid == this.mUid;
        }

        public int hashCode() {
            return this.mUid;
        }
    }

    public final boolean fromPhoneCall(String str) {
        return "AudioFocus_For_Phone_Ring_And_Calls".compareTo(str) == 0;
    }

    public static /* synthetic */ void lambda$new$0() {
        Object obj = mAudioFocusLock;
        synchronized (obj) {
            obj.notify();
        }
    }

    public final boolean isWiredHeadsetOrHeadphonePlugged() {
        return (this.mAudioService.getObservedDevicesForMedia() & 12) != 0;
    }

    public final void addMySpaceEffectDelayLocked() {
        this.mMySpaceHandler.removeCallbacks(this.mMySpaceRunnable);
        this.mMySpaceHandler.postDelayed(this.mMySpaceRunnable, 1000L);
        try {
            mAudioFocusLock.wait(1500L);
        } catch (InterruptedException unused) {
        }
    }

    public final void dumpMultiSoundStack(PrintWriter printWriter) {
        printWriter.println("\nMultiFocusStack:");
        printWriter.println("size:" + this.mMultiFocusStack.size());
        printWriter.println("device:" + Integer.toHexString(this.mDevice));
        for (int i = 0; i < this.mMultiFocusStack.size(); i++) {
            Stack stack = (Stack) this.mMultiFocusStack.valueAt(i);
            printWriter.println("------------------------------");
            printWriter.println("device = " + Integer.toHexString(this.mMultiFocusStack.keyAt(i)));
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

    public String getCurrentAudioFocusPackageName() {
        synchronized (mAudioFocusLock) {
            if (!this.mFocusStack.empty()) {
                return ((FocusRequester) this.mFocusStack.peek()).getPackageName();
            }
            for (int i = 0; i < this.mMultiFocusStack.size(); i++) {
                Stack stack = (Stack) this.mMultiFocusStack.valueAt(i);
                if (!stack.isEmpty()) {
                    return ((FocusRequester) stack.peek()).getPackageName();
                }
            }
            return null;
        }
    }

    public void resetFocusLossReceived() {
        if (this.mFocusStack.empty() || !canReassignAudioFocus()) {
            return;
        }
        ((FocusRequester) this.mFocusStack.peek()).resetFocusLossReceived();
    }

    public final Stack selectFocusStack(int i) {
        int appDevice = getAppDevice(i);
        Log.d("MediaFocusControl", "selectFocusStack, uid = " + Binder.getCallingUid() + ", appDevice = " + Integer.toHexString(appDevice) + ", device = " + Integer.toHexString(this.mDevice));
        if (appDevice == 0 || appDevice == this.mDevice) {
            appDevice = 0;
        }
        return this.mMultiFocusStack.getFocusStack(appDevice);
    }

    public final ArrayList selectFocusList(int i) {
        int appDevice = getAppDevice(i);
        Log.d("MediaFocusControl", "selectFocusStack, uid = " + Binder.getCallingUid() + ", appDevice = " + Integer.toHexString(appDevice) + ", device = " + Integer.toHexString(this.mDevice));
        if (appDevice == 0 || appDevice == this.mDevice) {
            appDevice = 0;
        }
        return this.mMultiFocusStack.getFocusList(appDevice);
    }

    public final void propagateFocusLossFromGainToAll(int i) {
        Log.d("MediaFocusControl", "propagateFocusLossFromGainToAll, " + i);
        for (int i2 = 0; i2 < this.mMultiFocusStack.size(); i2++) {
            Iterator it = ((Stack) this.mMultiFocusStack.valueAt(i2)).iterator();
            while (it.hasNext()) {
                FocusRequester focusRequester = (FocusRequester) it.next();
                focusRequester.handleFocusLossFromGain(i, focusRequester, true);
            }
        }
    }

    public final int getAppDevice(int i) {
        int appDevice = this.mAudioService.getAppDevice(i);
        if (AudioUtils.isWiredDeviceType(appDevice)) {
            return 2;
        }
        return appDevice;
    }

    public void updateFocusRequester(int i) {
        updateFocusRequester(i, false);
    }

    public void updateFocusRequester(int i, final boolean z) {
        Log.d("MediaFocusControl", "updateFocusRequester, uid:" + i);
        final int appDevice = getAppDevice(i);
        final boolean isPlaybackActiveForUid = this.mAudioService.isPlaybackActiveForUid(i);
        synchronized (mAudioFocusLock) {
            this.mMultiFocusStack.getFocusRequester(i, true).forEach(new Consumer() { // from class: com.android.server.audio.MediaFocusControl$$ExternalSyntheticLambda1
                @Override // java.util.function.Consumer
                public final void accept(Object obj) {
                    MediaFocusControl.this.lambda$updateFocusRequester$1(appDevice, isPlaybackActiveForUid, z, (FocusRequester) obj);
                }
            });
        }
    }

    /* JADX INFO: Access modifiers changed from: private */
    /* JADX WARN: Removed duplicated region for block: B:19:0x0081 A[Catch: all -> 0x008b, TryCatch #0 {, blocks: (B:31:0x0022, B:34:0x0027, B:5:0x0035, B:10:0x0041, B:11:0x0053, B:14:0x006c, B:16:0x0073, B:17:0x007c, B:19:0x0081, B:21:0x0086, B:22:0x0089, B:4:0x002e), top: B:30:0x0022 }] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0086 A[Catch: all -> 0x008b, TryCatch #0 {, blocks: (B:31:0x0022, B:34:0x0027, B:5:0x0035, B:10:0x0041, B:11:0x0053, B:14:0x006c, B:16:0x0073, B:17:0x007c, B:19:0x0081, B:21:0x0086, B:22:0x0089, B:4:0x002e), top: B:30:0x0022 }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x003c  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public /* synthetic */ void lambda$updateFocusRequester$1(int i, boolean z, boolean z2, FocusRequester focusRequester) {
        Stack focusStack;
        FocusRequester focusRequester2;
        FocusRequester focusRequester3;
        Log.d("MediaFocusControl", "focusRequester.getClientId()=" + focusRequester.getClientId());
        focusRequester.setDevice(i);
        synchronized (mAudioFocusLock) {
            if (i != 0) {
                if (i != this.mDevice) {
                    focusStack = this.mMultiFocusStack.getFocusStack(i);
                    focusRequester2 = null;
                    if (!focusStack.isEmpty()) {
                        if (z && !z2) {
                            Log.d("MediaFocusControl", "pin app playing");
                            ((FocusRequester) focusStack.peek()).handleFocusLossFromGain(1, focusRequester, true);
                        } else {
                            Log.d("MediaFocusControl", "pin app paused");
                            focusRequester.handleFocusLossFromGain(1, focusRequester, true);
                            focusRequester3 = (FocusRequester) focusStack.pop();
                            if (!focusStack.isEmpty() && focusRequester3 != null && focusRequester3.getGainRequest() == 3) {
                                focusRequester2 = (FocusRequester) focusStack.pop();
                            }
                            focusStack.push(focusRequester);
                            if (focusRequester2 != null) {
                                focusStack.push(focusRequester2);
                            }
                            if (focusRequester3 != null) {
                                focusStack.push(focusRequester3);
                            }
                        }
                    }
                    focusRequester3 = null;
                    focusStack.push(focusRequester);
                    if (focusRequester2 != null) {
                    }
                    if (focusRequester3 != null) {
                    }
                }
            }
            focusStack = this.mMultiFocusStack.getFocusStack(0);
            focusRequester2 = null;
            if (!focusStack.isEmpty()) {
            }
            focusRequester3 = null;
            focusStack.push(focusRequester);
            if (focusRequester2 != null) {
            }
            if (focusRequester3 != null) {
            }
        }
    }

    public void setDevice(int i) {
        FocusRequester focusRequester;
        if (i == 0) {
            Log.d("MediaFocusControl", "incorrect parameter");
            return;
        }
        synchronized (mAudioFocusLock) {
            if ((67125261 & i) != 0) {
                Log.d("MediaFocusControl", "force change device " + i + " to 2");
                i = 2;
            } else if (AudioSystem.DEVICE_OUT_ALL_SCO_SET.contains(Integer.valueOf(i)) || AudioSystem.DEVICE_OUT_ALL_A2DP_SET.contains(Integer.valueOf(i))) {
                i = 128;
            }
            int i2 = this.mDevice;
            if (i2 == i) {
                Log.d("MediaFocusControl", "setDevice, device doesn't change");
                return;
            }
            Log.d("MediaFocusControl", "setDevice, " + Integer.toHexString(i));
            this.mFocusStack = this.mMultiFocusStack.getFocusStack(0);
            Log.d("MediaFocusControl", "move from default to " + Integer.toHexString(i2));
            Stack focusStack = this.mMultiFocusStack.getFocusStack(i2);
            Iterator it = this.mFocusStack.iterator();
            while (it.hasNext()) {
                FocusRequester focusRequester2 = (FocusRequester) it.next();
                focusRequester2.setDevice(getAppDevice(focusRequester2.getCallingUid()));
                if (focusRequester2.getDevice() == i2) {
                    it.remove();
                    focusStack.push(focusRequester2);
                }
            }
            Log.d("MediaFocusControl", "move from " + Integer.toHexString(i) + " to default");
            Stack focusStack2 = this.mMultiFocusStack.getFocusStack(i);
            if (this.mFocusStack.isEmpty() ? false : this.mAudioService.isPlaybackActiveForUid(((FocusRequester) this.mFocusStack.peek()).getCallingUid())) {
                focusRequester = (FocusRequester) this.mFocusStack.pop();
            } else {
                if (!focusStack2.isEmpty()) {
                    propagateFocusLossFromGain_syncAf(1, null, true);
                }
                focusRequester = null;
            }
            Iterator it2 = focusStack2.iterator();
            while (it2.hasNext()) {
                FocusRequester focusRequester3 = (FocusRequester) it2.next();
                it2.remove();
                this.mFocusStack.push(focusRequester3);
            }
            if (focusRequester != null) {
                int gainRequest = focusRequester.getGainRequest();
                if (gainRequest != 2) {
                    propagateFocusLossFromGain_syncAf(gainRequest, focusRequester, true);
                }
                this.mFocusStack.push(focusRequester);
            }
            this.mDevice = i;
        }
    }

    public int getUidForDevice(int i) {
        Stack focusStack = this.mMultiFocusStack.getFocusStack(i);
        int callingUid = !focusStack.isEmpty() ? ((FocusRequester) focusStack.peek()).getCallingUid() : -1;
        Log.d("MediaFocusControl", "getUidForDevice, uid:" + callingUid);
        return callingUid;
    }

    public void handleExternalFocusGain(int i) {
        Log.d("MediaFocusControl", "handleExternalFocusGain, " + i);
        ArrayList focusRequester = this.mMultiFocusStack.getFocusRequester(i, false);
        if (focusRequester.size() == 0) {
            return;
        }
        Iterator it = focusRequester.iterator();
        while (it.hasNext()) {
            ((FocusRequester) it.next()).handleFocusLossFromGain(1, null, true);
        }
    }

    public void setIgnoreAudioFocus(int i, boolean z) {
        synchronized (mAudioFocusLock) {
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
        }
    }

    public int getIgnoredUid() {
        return this.mIgnoredUid;
    }

    public void checkSplitSoundAudioFocus() {
        FocusRequester focusRequester = this.mSplitSoundFR;
        if (focusRequester != null) {
            requestAudioFocus(focusRequester.getAudioAttributes(), this.mSplitSoundFR.getGainRequest(), this.mSplitSoundCb, null, this.mSplitSoundFR.getClientId(), this.mSplitSoundFR.getPackageName(), null, this.mSplitSoundFR.getGrantFlags(), this.mSplitSoundFR.getSdkTarget(), true, -1);
            this.mSplitSoundFR = null;
            this.mSplitSoundCb = null;
        }
    }

    public final boolean isRemoteMicState(AudioAttributes audioAttributes) {
        if (Rune.SEC_AUDIO_REMOTE_MIC) {
            return audioAttributes.getTags().contains("AUDIO_REMOTE_MIC") || this.mAudioService.getRemoteMic();
        }
        return false;
    }

    public void handleUpdateMultiAudioFocus() {
        if (this.mMultiAudioFocusEnabled) {
            if (!this.mFocusStack.isEmpty()) {
                FocusRequester focusRequester = (FocusRequester) this.mFocusStack.peek();
                if (AudioUtils.checkRunningBackground(focusRequester.getPackageName())) {
                    Log.d("MediaFocusControl", "updateMultiAudioFocus( send AUDIOFOCUS_LOSS [ " + focusRequester.getPackageName() + " ] )");
                    focusRequester.handleFocusLoss(-1, null, false);
                }
            }
            FocusRequester focusRequester2 = this.mIgnoredFocus;
            if (focusRequester2 == null || !AudioUtils.checkRunningBackground(focusRequester2.getPackageName())) {
                return;
            }
            Log.d("MediaFocusControl", "updateMultiAudioFocus( send AUDIOFOCUS_LOSS mIgnoredFocus [ " + this.mIgnoredFocus.getPackageName() + " ] )");
            this.mIgnoredFocus.handleFocusLoss(-1, null, false);
            return;
        }
        if (!this.mFocusStack.isEmpty()) {
            FocusRequester focusRequester3 = (FocusRequester) this.mFocusStack.peek();
            Log.d("MediaFocusControl", "updateMultiAudioFocus[mFocusStack] : send Loss :: Tasks =" + focusRequester3.getPackageName());
            focusRequester3.handleFocusLoss(-1, null, false);
        }
        if (!this.mMultiAudioFocusList.isEmpty()) {
            Iterator it = this.mMultiAudioFocusList.iterator();
            while (it.hasNext()) {
                ((FocusRequester) it.next()).handleFocusLoss(-1, null, false);
            }
            this.mMultiAudioFocusList.clear();
        }
        for (int i = 0; i < this.mMultiFocusStack.size(); i++) {
            Iterator it2 = ((Stack) this.mMultiFocusStack.valueAt(i)).iterator();
            while (it2.hasNext()) {
                FocusRequester focusRequester4 = (FocusRequester) it2.next();
                focusRequester4.handleFocusLoss(-1, null, false);
                focusRequester4.release();
                it2.remove();
            }
        }
    }

    public void clearMultiAudiofocusfromAndroidAuto() {
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

    public void postDelayedForLossAudioFocus(FocusRequester focusRequester) {
        Log.d("MediaFocusControl", "postDelayedForLossAudioFocus: clientId = " + focusRequester.getClientId());
        this.mFocusHandler.removeMessages(4, focusRequester);
        Handler handler = this.mFocusHandler;
        handler.sendMessageDelayed(handler.obtainMessage(3, focusRequester), 15000L);
    }

    public boolean isDelayLossApp(String str) {
        return this.mAudioSettingsHelper.checkAppCategory(str, "delay_loss_audio_focus");
    }
}
