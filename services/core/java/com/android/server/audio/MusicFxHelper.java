package com.android.server.audio;

import android.app.ActivityManager;
import android.app.IActivityManager;
import android.app.UidObserver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.util.NetdService$$ExternalSyntheticOutline0;
import android.os.Binder;
import android.os.IBinder;
import android.os.RemoteException;
import android.os.UserHandle;
import android.util.Log;
import android.util.SparseArray;

import com.android.internal.util.jobs.ArrayUtils$$ExternalSyntheticOutline0;
import com.android.server.BatteryService$$ExternalSyntheticOutline0;
import com.android.server.DirEncryptService$$ExternalSyntheticOutline0;

import java.util.ArrayList;
import java.util.List;

public final class MusicFxHelper {
    public final AudioService.AudioHandler mAudioHandler;
    public final Context mContext;
    public boolean mIsBound;
    public final Object mClientUidMapLock = new Object();
    public final String mPackageName = MusicFxHelper.class.getPackage().getName();
    public IBinder mUidObserverToken = null;
    public final MySparseArray mClientUidSessionMap = new MySparseArray();
    public final AnonymousClass1 mEffectUidObserver =
            new UidObserver() { // from class: com.android.server.audio.MusicFxHelper.1
                public final void onUidGone(int i, boolean z) {
                    Log.w("AS.MusicFxHelper", " send MSG_EFFECT_CLIENT_GONE");
                    AudioService.AudioHandler audioHandler = MusicFxHelper.this.mAudioHandler;
                    audioHandler.sendMessageAtTime(
                            audioHandler.obtainMessage(1101, i, 0, null), 0L);
                }
            };
    public final AnonymousClass2 mMusicFxBindConnection = new AnonymousClass2();

    /* renamed from: com.android.server.audio.MusicFxHelper$2, reason: invalid class name */
    public final class AnonymousClass2 implements ServiceConnection {
        @Override // android.content.ServiceConnection
        public final void onServiceConnected(ComponentName componentName, IBinder iBinder) {
            Log.d("AS.MusicFxHelper", " service connected to " + componentName);
        }

        @Override // android.content.ServiceConnection
        public final void onServiceDisconnected(ComponentName componentName) {
            Log.d("AS.MusicFxHelper", " service disconnected from " + componentName);
        }
    }

    public final class MySparseArray extends SparseArray {
        public MySparseArray() {}

        @Override // android.util.SparseArray
        public final void put(int i, PackageSessions packageSessions) {
            int i2;
            if (size() == 0) {
                try {
                    i2 =
                            ActivityManager.getService()
                                    .getPackageProcessState(
                                            "com.android.musicfx", MusicFxHelper.this.mPackageName);
                } catch (RemoteException e) {
                    NetdService$$ExternalSyntheticOutline0.m(
                            "RemoteException with getPackageProcessState: ", e, "AS.MusicFxHelper");
                    i2 = 20;
                }
                if (i2 > 6) {
                    Intent className =
                            new Intent()
                                    .setClassName(
                                            "com.android.musicfx",
                                            "com.android.musicfx.KeepAliveService");
                    MusicFxHelper musicFxHelper = MusicFxHelper.this;
                    musicFxHelper.mIsBound = true;
                    musicFxHelper.mContext.bindServiceAsUser(
                            className,
                            musicFxHelper.mMusicFxBindConnection,
                            1,
                            UserHandle.of(MusicFxHelper.getCurrentUserId()));
                    Log.i("AS.MusicFxHelper", "bindService to com.android.musicfx");
                }
                DirEncryptService$$ExternalSyntheticOutline0.m(
                        i2, "com.android.musicfx procState ", "AS.MusicFxHelper");
            }
            try {
                MusicFxHelper musicFxHelper2 = MusicFxHelper.this;
                if (musicFxHelper2.mUidObserverToken == null) {
                    IActivityManager service = ActivityManager.getService();
                    MusicFxHelper musicFxHelper3 = MusicFxHelper.this;
                    musicFxHelper2.mUidObserverToken =
                            service.registerUidObserverForUids(
                                    musicFxHelper3.mEffectUidObserver,
                                    2,
                                    -1,
                                    musicFxHelper3.mPackageName,
                                    new int[] {i});
                    Log.i("AS.MusicFxHelper", "registered to observer with UID " + i);
                } else if (get(i) == null) {
                    IActivityManager service2 = ActivityManager.getService();
                    MusicFxHelper musicFxHelper4 = MusicFxHelper.this;
                    service2.addUidToObserver(
                            musicFxHelper4.mUidObserverToken, musicFxHelper4.mPackageName, i);
                    Log.i("AS.MusicFxHelper", " UID " + i + " add to observer");
                }
            } catch (RemoteException e2) {
                NetdService$$ExternalSyntheticOutline0.m(
                        "RemoteException with UID observer add/register: ", e2, "AS.MusicFxHelper");
            }
            super.put(i, (int) packageSessions);
        }

        @Override // android.util.SparseArray
        public final void remove(int i) {
            if (get(i) != null) {
                try {
                    IActivityManager service = ActivityManager.getService();
                    MusicFxHelper musicFxHelper = MusicFxHelper.this;
                    service.removeUidFromObserver(
                            musicFxHelper.mUidObserverToken, musicFxHelper.mPackageName, i);
                } catch (RemoteException e) {
                    NetdService$$ExternalSyntheticOutline0.m(
                            "RemoteException with removeUidFromObserver: ", e, "AS.MusicFxHelper");
                }
            }
            super.remove(i);
            if (size() == 0) {
                try {
                    ActivityManager.getService()
                            .unregisterUidObserver(MusicFxHelper.this.mEffectUidObserver);
                } catch (RemoteException e2) {
                    NetdService$$ExternalSyntheticOutline0.m(
                            "RemoteException with unregisterUidObserver: ", e2, "AS.MusicFxHelper");
                }
                MusicFxHelper musicFxHelper2 = MusicFxHelper.this;
                musicFxHelper2.mUidObserverToken = null;
                if (musicFxHelper2.mIsBound) {
                    musicFxHelper2.mContext.unbindService(musicFxHelper2.mMusicFxBindConnection);
                    MusicFxHelper.this.mIsBound = false;
                    Log.i(
                            "AS.MusicFxHelper",
                            "last session closed, unregister UID observer, and unbind"
                                + " com.android.musicfx");
                }
            }
        }
    }

    public final class PackageSessions {
        public String mPackageName;
        public List mSessions;
    }

    public MusicFxHelper(Context context, AudioService.AudioHandler audioHandler) {
        this.mContext = context;
        this.mAudioHandler = audioHandler;
    }

    public static int getCurrentUserId() {
        long clearCallingIdentity = Binder.clearCallingIdentity();
        try {
            int i = ActivityManager.getService().getCurrentUser().id;
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return i;
        } catch (RemoteException unused) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            return 0;
        } catch (Throwable th) {
            Binder.restoreCallingIdentity(clearCallingIdentity);
            throw th;
        }
    }

    public final boolean handleAudioEffectSessionClose(int i, int i2, String str) {
        Log.d("AS.MusicFxHelper", str + " UID " + i + " close MusicFx session " + i2);
        MySparseArray mySparseArray = this.mClientUidSessionMap;
        PackageSessions packageSessions = (PackageSessions) mySparseArray.get(i);
        if (packageSessions == null) {
            Log.e("AS.MusicFxHelper", str + " UID " + i + " does not exist in map, abort");
            return false;
        }
        if (!packageSessions.mPackageName.equals(str)) {
            StringBuilder m =
                    BatteryService$$ExternalSyntheticOutline0.m(
                            i, "Inconsistency package names for UID ", " close, prev: ");
            m.append(packageSessions.mPackageName);
            m.append(", now: ");
            m.append(str);
            Log.w("AS.MusicFxHelper", m.toString());
            return false;
        }
        List list = packageSessions.mSessions;
        if (list != null && ((ArrayList) list).size() != 0) {
            if (!((ArrayList) packageSessions.mSessions).contains(Integer.valueOf(i2))) {
                Log.e(
                        "AS.MusicFxHelper",
                        str + " UID " + i + " session " + i2 + " does not exist in map, abort");
                return false;
            }
            ((ArrayList) packageSessions.mSessions).remove(Integer.valueOf(i2));
        }
        List list2 = packageSessions.mSessions;
        if (list2 == null || ((ArrayList) list2).size() == 0) {
            mySparseArray.remove(i);
            return true;
        }
        mySparseArray.put(i, packageSessions);
        return true;
    }

    public final boolean handleAudioEffectSessionOpen(int i, int i2, String str) {
        List list;
        Log.d("AS.MusicFxHelper", str + " UID " + i + " open MusicFx session " + i2);
        MySparseArray mySparseArray = this.mClientUidSessionMap;
        PackageSessions packageSessions = (PackageSessions) mySparseArray.get(i);
        if (packageSessions == null || (list = packageSessions.mSessions) == null) {
            packageSessions = new PackageSessions();
            packageSessions.mSessions = new ArrayList();
            packageSessions.mPackageName = str;
        } else {
            if (((ArrayList) list).contains(Integer.valueOf(i2))) {
                StringBuilder m =
                        ArrayUtils$$ExternalSyntheticOutline0.m(
                                i2, i, "Audio session ", " already open for UID: ", ", package: ");
                m.append(str);
                m.append(", abort");
                Log.e("AS.MusicFxHelper", m.toString());
                return false;
            }
            if (!packageSessions.mPackageName.equals(str)) {
                StringBuilder m2 =
                        BatteryService$$ExternalSyntheticOutline0.m(
                                i, "Inconsistency package names for UID open: ", " prev: ");
                m2.append(packageSessions.mPackageName);
                m2.append(", now: ");
                m2.append(str);
                Log.w("AS.MusicFxHelper", m2.toString());
                return false;
            }
        }
        ((ArrayList) packageSessions.mSessions).add(Integer.valueOf(i2));
        mySparseArray.put(i, packageSessions);
        return true;
    }

    public final boolean setMusicFxServiceWithObserver(Intent intent, String str, int i) {
        int intExtra = intent.getIntExtra("android.media.extra.AUDIO_SESSION", 0);
        if (intExtra == 0) {
            Log.e("AS.MusicFxHelper", str.concat(" intent have no invalid audio session"));
            return false;
        }
        synchronized (this.mClientUidMapLock) {
            try {
                if (intent.getAction()
                        .equals("android.media.action.OPEN_AUDIO_EFFECT_CONTROL_SESSION")) {
                    return handleAudioEffectSessionOpen(i, intExtra, str);
                }
                return handleAudioEffectSessionClose(i, intExtra, str);
            } catch (Throwable th) {
                throw th;
            }
        }
    }
}
