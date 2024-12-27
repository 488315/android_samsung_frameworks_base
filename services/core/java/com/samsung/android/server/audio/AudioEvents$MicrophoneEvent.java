package com.samsung.android.server.audio;

import com.android.server.utils.EventLogger;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final class AudioEvents$MicrophoneEvent extends EventLogger.Event {
    public final /* synthetic */ int $r8$classId = 1;
    public boolean mIsEnableMute;
    public String mPackage;
    public int mRequesterPid;

    public /* synthetic */ AudioEvents$MicrophoneEvent() {}

    public AudioEvents$MicrophoneEvent(int i, String str, boolean z) {
        this.mPackage = str;
        this.mRequesterPid = i;
        this.mIsEnableMute = z;
    }

    @Override // com.android.server.utils.EventLogger.Event
    public final String eventToString() {
        switch (this.$r8$classId) {
            case 0:
                return "setMicrophoneMute() from package="
                        + this.mPackage
                        + " pid="
                        + this.mRequesterPid
                        + " isisEnableMute="
                        + this.mIsEnableMute;
            default:
                return "setRingerMode(mode:"
                        + this.mRequesterPid
                        + " external:"
                        + this.mIsEnableMute
                        + ") from "
                        + this.mPackage;
        }
    }
}
