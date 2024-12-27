package com.android.server.broadcastradio.hal2;

import android.hardware.broadcastradio.V2_0.ProgramInfo;
import android.hardware.broadcastradio.V2_0.ProgramListChunk;
import android.util.ArraySet;
import com.android.server.broadcastradio.hal2.RadioModule;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final /* synthetic */ class RadioModule$$ExternalSyntheticLambda3 implements Runnable {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;

    public /* synthetic */ RadioModule$$ExternalSyntheticLambda3(int i, Object obj, Object obj2) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
    }

    private final void run$com$android$server$broadcastradio$hal2$RadioModule$1$$ExternalSyntheticLambda2() {
        RadioModule.AnonymousClass1 anonymousClass1 = (RadioModule.AnonymousClass1) this.f$0;
        ArrayList arrayList = (ArrayList) this.f$1;
        anonymousClass1.getClass();
        Map vendorInfoFromHal = Convert.vendorInfoFromHal(arrayList);
        synchronized (((RadioModule) anonymousClass1.this$0).mLock) {
            ((RadioModule) anonymousClass1.this$0).fanoutAidlCallbackLocked(new RadioModule$$ExternalSyntheticLambda2(1, vendorInfoFromHal));
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
                ProgramInfo programInfo = (ProgramInfo) this.f$1;
                synchronized (((RadioModule) anonymousClass1.this$0).mLock) {
                    ((RadioModule) anonymousClass1.this$0).mCurrentProgramInfo = Convert.programInfoFromHal(programInfo);
                    RadioModule radioModule2 = (RadioModule) anonymousClass1.this$0;
                    radioModule2.fanoutAidlCallbackLocked(new RadioModule$$ExternalSyntheticLambda2(2, radioModule2.mCurrentProgramInfo));
                }
                return;
            case 2:
                run$com$android$server$broadcastradio$hal2$RadioModule$1$$ExternalSyntheticLambda2();
                return;
            default:
                RadioModule.AnonymousClass1 anonymousClass12 = (RadioModule.AnonymousClass1) this.f$0;
                ProgramListChunk programListChunk = (ProgramListChunk) this.f$1;
                synchronized (((RadioModule) anonymousClass12.this$0).mLock) {
                    try {
                        ((RadioModule) anonymousClass12.this$0).mProgramInfoCache.filterAndApplyChunkInternal(programListChunk, 100, 500);
                        Iterator it = ((ArraySet) ((RadioModule) anonymousClass12.this$0).mAidlTunerSessions).iterator();
                        while (it.hasNext()) {
                            TunerSession tunerSession = (TunerSession) it.next();
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
        }
    }
}
