package com.android.server.audio;

import android.media.AudioAttributes;
import android.media.AudioDeviceAttributes;
import android.media.ILoudnessCodecUpdatesDispatcher;
import android.media.LoudnessCodecInfo;
import android.os.IInterface;
import android.os.PersistableBundle;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;
import android.util.SparseIntArray;

import com.android.server.utils.EventLogger;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public final class LoudnessCodecHelper {
    static final int SPL_RANGE_LARGE = 3;
    static final int SPL_RANGE_MEDIUM = 2;
    static final int SPL_RANGE_SMALL = 1;
    static final int SPL_RANGE_UNKNOWN = 0;
    public static final EventLogger sLogger = new EventLogger(30, "Loudness updates");
    public final AudioService mAudioService;
    public final LoudnessRemoteCallbackList mLoudnessUpdateDispatchers =
            new LoudnessRemoteCallbackList(this);
    public final Object mLock = new Object();
    public final HashMap mStartedConfigPiids = new HashMap();
    public final HashMap mStartedConfigInfo = new HashMap();
    public final SparseIntArray mPiidToDeviceIdCache = new SparseIntArray();
    public final SparseIntArray mPiidToPidCache = new SparseIntArray();
    public final HashMap mCachedProperties = new HashMap();

    final class LoudnessCodecInputProperties {
        public final int mDeviceSplRange;
        public final boolean mIsDownmixing;
        public final int mMetadataType;

        public LoudnessCodecInputProperties(int i, int i2, boolean z) {
            this.mMetadataType = i;
            this.mIsDownmixing = z;
            this.mDeviceSplRange = i2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || LoudnessCodecInputProperties.class != obj.getClass()) {
                return false;
            }
            LoudnessCodecInputProperties loudnessCodecInputProperties =
                    (LoudnessCodecInputProperties) obj;
            return this.mMetadataType == loudnessCodecInputProperties.mMetadataType
                    && this.mIsDownmixing == loudnessCodecInputProperties.mIsDownmixing
                    && this.mDeviceSplRange == loudnessCodecInputProperties.mDeviceSplRange;
        }

        public final int hashCode() {
            return Objects.hash(
                    Integer.valueOf(this.mMetadataType),
                    Boolean.valueOf(this.mIsDownmixing),
                    Integer.valueOf(this.mDeviceSplRange));
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder("Loudness properties: device SPL range: ");
            EventLogger eventLogger = LoudnessCodecHelper.sLogger;
            int i = this.mDeviceSplRange;
            sb.append(i != 1 ? i != 2 ? i != 3 ? "unknown" : "large" : "medium" : "small");
            sb.append(" down-mixing: ");
            sb.append(this.mIsDownmixing);
            sb.append(" metadata type: ");
            sb.append(this.mMetadataType);
            return sb.toString();
        }
    }

    public final class LoudnessRemoteCallbackList extends RemoteCallbackList {
        public final LoudnessCodecHelper mLoudnessCodecHelper;

        public LoudnessRemoteCallbackList(LoudnessCodecHelper loudnessCodecHelper) {
            this.mLoudnessCodecHelper = loudnessCodecHelper;
        }

        @Override // android.os.RemoteCallbackList
        public final void onCallbackDied(IInterface iInterface, Object obj) {
            ILoudnessCodecUpdatesDispatcher iLoudnessCodecUpdatesDispatcher =
                    (ILoudnessCodecUpdatesDispatcher) iInterface;
            Integer num = obj instanceof Integer ? (Integer) obj : null;
            if (num != null) {
                LoudnessCodecHelper.sLogger.enqueue(
                        new AudioServiceEvents$LoudnessEvent(2, 0, num.intValue()));
                LoudnessCodecHelper loudnessCodecHelper = this.mLoudnessCodecHelper;
                final int intValue = num.intValue();
                synchronized (loudnessCodecHelper.mLock) {
                    for (int i = 0; i < loudnessCodecHelper.mPiidToPidCache.size(); i++) {
                        try {
                            int keyAt = loudnessCodecHelper.mPiidToPidCache.keyAt(i);
                            if (loudnessCodecHelper.mPiidToPidCache.get(keyAt) == intValue) {
                                loudnessCodecHelper.mPiidToDeviceIdCache.delete(keyAt);
                            }
                        } finally {
                        }
                    }
                    final int i2 = 0;
                    loudnessCodecHelper
                            .mStartedConfigPiids
                            .entrySet()
                            .removeIf(
                                    new Predicate() { // from class:
                                                      // com.android.server.audio.LoudnessCodecHelper$$ExternalSyntheticLambda5
                                        @Override // java.util.function.Predicate
                                        public final boolean test(Object obj2) {
                                            int i3 = i2;
                                            int i4 = intValue;
                                            Map.Entry entry = (Map.Entry) obj2;
                                            switch (i3) {
                                                case 0:
                                                    if (((LoudnessCodecHelper.LoudnessTrackId)
                                                                            entry.getKey())
                                                                    .mPid
                                                            == i4) {}
                                                    break;
                                                default:
                                                    if (((LoudnessCodecHelper.LoudnessTrackId)
                                                                            entry.getKey())
                                                                    .mPid
                                                            == i4) {}
                                                    break;
                                            }
                                            return false;
                                        }
                                    });
                    final int i3 = 1;
                    loudnessCodecHelper
                            .mStartedConfigInfo
                            .entrySet()
                            .removeIf(
                                    new Predicate() { // from class:
                                                      // com.android.server.audio.LoudnessCodecHelper$$ExternalSyntheticLambda5
                                        @Override // java.util.function.Predicate
                                        public final boolean test(Object obj2) {
                                            int i32 = i3;
                                            int i4 = intValue;
                                            Map.Entry entry = (Map.Entry) obj2;
                                            switch (i32) {
                                                case 0:
                                                    if (((LoudnessCodecHelper.LoudnessTrackId)
                                                                            entry.getKey())
                                                                    .mPid
                                                            == i4) {}
                                                    break;
                                                default:
                                                    if (((LoudnessCodecHelper.LoudnessTrackId)
                                                                            entry.getKey())
                                                                    .mPid
                                                            == i4) {}
                                                    break;
                                            }
                                            return false;
                                        }
                                    });
                }
            }
            super.onCallbackDied(iLoudnessCodecUpdatesDispatcher, obj);
        }
    }

    final class LoudnessTrackId {
        public final int mPid;
        public final int mSessionId;

        public LoudnessTrackId(int i, int i2) {
            this.mSessionId = i;
            this.mPid = i2;
        }

        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || LoudnessTrackId.class != obj.getClass()) {
                return false;
            }
            LoudnessTrackId loudnessTrackId = (LoudnessTrackId) obj;
            return this.mSessionId == loudnessTrackId.mSessionId
                    && this.mPid == loudnessTrackId.mPid;
        }

        public final int hashCode() {
            return Objects.hash(Integer.valueOf(this.mSessionId), Integer.valueOf(this.mPid));
        }

        public final String toString() {
            return "Loudness track id: session ID: " + this.mSessionId + " pid: " + this.mPid;
        }
    }

    public LoudnessCodecHelper(AudioService audioService) {
        Objects.requireNonNull(audioService);
        this.mAudioService = audioService;
    }

    public final void dispatchNewLoudnessParameters(int i, PersistableBundle persistableBundle) {
        LoudnessRemoteCallbackList loudnessRemoteCallbackList = this.mLoudnessUpdateDispatchers;
        int beginBroadcast = loudnessRemoteCallbackList.beginBroadcast();
        for (int i2 = 0; i2 < beginBroadcast; i2++) {
            try {
                loudnessRemoteCallbackList
                        .getBroadcastItem(i2)
                        .dispatchLoudnessCodecParameterChange(i, persistableBundle);
            } catch (RemoteException e) {
                Log.e(
                        "AS.LoudnessCodecHelper",
                        "Error dispatching for sessionId " + i + " bundle: " + persistableBundle,
                        e);
            }
        }
        loudnessRemoteCallbackList.finishBroadcast();
    }

    public final void dump(PrintWriter printWriter) {
        printWriter.println("\nRegistered clients:\n");
        synchronized (this.mLock) {
            try {
                for (Map.Entry entry : this.mStartedConfigPiids.entrySet()) {
                    for (Integer num : (Set) entry.getValue()) {
                        int i = this.mPiidToPidCache.get(num.intValue(), -1);
                        Set set = (Set) this.mStartedConfigInfo.get(entry.getKey());
                        if (set != null) {
                            printWriter.println(
                                    String.format(
                                            "Player piid %d pid %d active codec types %s\n",
                                            num,
                                            Integer.valueOf(i),
                                            set.stream()
                                                    .map(
                                                            new LoudnessCodecHelper$$ExternalSyntheticLambda0())
                                                    .collect(Collectors.joining(", "))));
                        }
                    }
                }
                printWriter.println();
            } catch (Throwable th) {
                throw th;
            }
        }
        sLogger.dump(printWriter);
        printWriter.println();
    }

    /* JADX WARN: Code restructure failed: missing block: B:63:0x006d, code lost:

       if (r12 == 2) goto L51;
    */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x0093, code lost:

       if (r13 == 6) goto L54;
    */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final android.os.PersistableBundle getCodecBundle_l(
            int r12, java.lang.String r13, android.media.LoudnessCodecInfo r14) {
        /*
            Method dump skipped, instructions count: 302
            To view this dump change 'Code comments level' option to 'DEBUG'
        */
        throw new UnsupportedOperationException(
                "Method not decompiled:"
                    + " com.android.server.audio.LoudnessCodecHelper.getCodecBundle_l(int,"
                    + " java.lang.String,"
                    + " android.media.LoudnessCodecInfo):android.os.PersistableBundle");
    }

    public final PersistableBundle getLoudnessParams(LoudnessCodecInfo loudnessCodecInfo) {
        PersistableBundle codecBundle_l;
        ArrayList devicesForAttributesInt =
                this.mAudioService.getDevicesForAttributesInt(
                        new AudioAttributes.Builder().setUsage(1).setContentType(2).build(), false);
        if (devicesForAttributesInt.isEmpty()) {
            return new PersistableBundle();
        }
        AudioDeviceAttributes audioDeviceAttributes =
                (AudioDeviceAttributes) devicesForAttributesInt.get(0);
        synchronized (this.mLock) {
            codecBundle_l =
                    getCodecBundle_l(
                            audioDeviceAttributes.getInternalType(),
                            audioDeviceAttributes.getAddress(),
                            loudnessCodecInfo);
        }
        return codecBundle_l;
    }
}
