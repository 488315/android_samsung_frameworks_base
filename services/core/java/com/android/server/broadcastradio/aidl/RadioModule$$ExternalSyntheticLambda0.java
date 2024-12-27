package com.android.server.broadcastradio.aidl;

import android.hardware.broadcastradio.ProgramInfo;
import android.hardware.broadcastradio.ProgramListChunk;
import android.hardware.broadcastradio.VendorKeyValue;
import android.hardware.radio.RadioManager;
import com.android.server.broadcastradio.aidl.RadioModule;
import java.util.Objects;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class RadioModule$$ExternalSyntheticLambda0 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ RadioModule$$ExternalSyntheticLambda0(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    private final void run$com$android$server$broadcastradio$aidl$RadioModule$1$$ExternalSyntheticLambda1() {
        RadioModule.AnonymousClass1 anonymousClass1 = (RadioModule.AnonymousClass1) this.f$0;
        ProgramInfo programInfo = (ProgramInfo) this.f$1;
        anonymousClass1.getClass();
        RadioManager.ProgramInfo programInfoFromHalProgramInfo = ConversionUtils.programInfoFromHalProgramInfo(programInfo);
        Objects.requireNonNull(programInfoFromHalProgramInfo, "Program info from AIDL HAL is invalid");
        synchronized (RadioModule.this.mLock) {
            RadioModule radioModule = RadioModule.this;
            radioModule.mCurrentProgramInfo = programInfoFromHalProgramInfo;
            radioModule.fanoutAidlCallbackLocked(new RadioModule$1$$ExternalSyntheticLambda7(0, programInfoFromHalProgramInfo));
        }
    }

    @Override // java.lang.Runnable
    public final void run() {
        switch (this.$r8$classId) {
            case 0:
                RadioModule radioModule = (RadioModule) this.f$0;
                RadioModule.AidlCallbackRunnable aidlCallbackRunnable = (RadioModule.AidlCallbackRunnable) this.f$1;
                synchronized (radioModule.mLock) {
                    radioModule.fanoutAidlCallbackLocked(aidlCallbackRunnable);
                }
                return;
            case 1:
                RadioModule.AnonymousClass1 anonymousClass1 = (RadioModule.AnonymousClass1) this.f$0;
                ProgramListChunk programListChunk = (ProgramListChunk) this.f$1;
                synchronized (RadioModule.this.mLock) {
                    try {
                        RadioModule.this.mProgramInfoCache.filterAndApplyChunkInternal(programListChunk, 100, 500);
                        for (int i = 0; i < RadioModule.this.mAidlTunerSessions.size(); i++) {
                            TunerSession tunerSession = (TunerSession) RadioModule.this.mAidlTunerSessions.valueAt(i);
                            synchronized (tunerSession.mLock) {
                                try {
                                    ProgramInfoCache programInfoCache = tunerSession.mProgramInfoCache;
                                    if (programInfoCache != null) {
                                        tunerSession.dispatchClientUpdateChunks(programInfoCache.filterAndApplyChunkInternal(programListChunk, 100, 500));
                                    }
                                } finally {
                                }
                            }
                        }
                    } catch (Throwable th) {
                        throw th;
                    }
                }
                return;
            case 2:
                run$com$android$server$broadcastradio$aidl$RadioModule$1$$ExternalSyntheticLambda1();
                return;
            default:
                RadioModule.AnonymousClass1 anonymousClass12 = (RadioModule.AnonymousClass1) this.f$0;
                VendorKeyValue[] vendorKeyValueArr = (VendorKeyValue[]) this.f$1;
                synchronized (RadioModule.this.mLock) {
                    RadioModule.this.fanoutAidlCallbackLocked(new RadioModule$1$$ExternalSyntheticLambda7(1, ConversionUtils.vendorInfoFromHalVendorKeyValues(vendorKeyValueArr)));
                }
                return;
        }
    }
}
