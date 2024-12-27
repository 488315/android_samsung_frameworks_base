package com.android.systemui.biometrics;

import com.android.keyguard.logging.BiometricMessageDeferralLogger;
import com.android.systemui.Dumpable;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.log.core.LogLevel;
import com.samsung.android.knox.ucm.configurator.UniversalCredentialManager;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes.dex */
public class BiometricMessageDeferral implements Dumpable {
    public final Map acquiredInfoToFrequency = new HashMap();
    public final Map acquiredInfoToHelpString = new HashMap();
    public final Set acquiredInfoToIgnore;
    public final BiometricMessageDeferralLogger logBuffer;
    public final Set messagesToDefer;
    public Integer mostFrequentAcquiredInfoToDefer;
    public final float threshold;
    public int totalFrames;

    public BiometricMessageDeferral(Set<Integer> set, Set<Integer> set2, float f, BiometricMessageDeferralLogger biometricMessageDeferralLogger, DumpManager dumpManager, String str) {
        this.messagesToDefer = set;
        this.acquiredInfoToIgnore = set2;
        this.threshold = f;
        this.logBuffer = biometricMessageDeferralLogger;
        dumpManager.registerNormalDumpable(getClass().getName() + "[" + str + "]", this);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("messagesToDefer=" + this.messagesToDefer);
        printWriter.println("totalFrames=" + this.totalFrames);
        printWriter.println("threshold=" + this.threshold);
    }

    public final CharSequence getDeferredMessage() {
        Integer num = this.mostFrequentAcquiredInfoToDefer;
        if (num == null) {
            return null;
        }
        int intValue = num.intValue();
        if (((Number) ((HashMap) this.acquiredInfoToFrequency).getOrDefault(Integer.valueOf(intValue), 0)).intValue() <= this.threshold * this.totalFrames) {
            return null;
        }
        return (CharSequence) ((HashMap) this.acquiredInfoToHelpString).get(Integer.valueOf(intValue));
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x006e, code lost:
    
        if (r0 > ((java.lang.Number) ((java.util.HashMap) r4).getOrDefault(r2, 0)).intValue()) goto L15;
     */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
        To view partially-correct code enable 'Show inconsistent code' option in preferences
    */
    public final void processFrame(int r7) {
        /*
            r6 = this;
            java.util.Set r0 = r6.messagesToDefer
            boolean r0 = r0.isEmpty()
            if (r0 == 0) goto L9
            return
        L9:
            java.util.Set r0 = r6.acquiredInfoToIgnore
            java.lang.Integer r1 = java.lang.Integer.valueOf(r7)
            boolean r0 = r0.contains(r1)
            com.android.keyguard.logging.BiometricMessageDeferralLogger r1 = r6.logBuffer
            if (r0 == 0) goto L1b
            r1.logFrameIgnored(r7)
            return
        L1b:
            int r0 = r6.totalFrames
            int r0 = r0 + 1
            r6.totalFrames = r0
            java.util.Map r0 = r6.acquiredInfoToFrequency
            java.lang.Integer r2 = java.lang.Integer.valueOf(r7)
            r3 = 0
            java.lang.Integer r4 = java.lang.Integer.valueOf(r3)
            java.util.HashMap r0 = (java.util.HashMap) r0
            java.lang.Object r0 = r0.getOrDefault(r2, r4)
            java.lang.Number r0 = (java.lang.Number) r0
            int r0 = r0.intValue()
            int r0 = r0 + 1
            java.lang.Integer r2 = java.lang.Integer.valueOf(r7)
            java.lang.Integer r4 = java.lang.Integer.valueOf(r0)
            java.util.Map r5 = r6.acquiredInfoToFrequency
            java.util.HashMap r5 = (java.util.HashMap) r5
            r5.put(r2, r4)
            java.util.Set r2 = r6.messagesToDefer
            java.lang.Integer r4 = java.lang.Integer.valueOf(r7)
            boolean r2 = r2.contains(r4)
            if (r2 == 0) goto L76
            java.lang.Integer r2 = r6.mostFrequentAcquiredInfoToDefer
            if (r2 == 0) goto L70
            java.util.Map r4 = r6.acquiredInfoToFrequency
            kotlin.jvm.internal.Intrinsics.checkNotNull(r2)
            java.lang.Integer r3 = java.lang.Integer.valueOf(r3)
            java.util.HashMap r4 = (java.util.HashMap) r4
            java.lang.Object r2 = r4.getOrDefault(r2, r3)
            java.lang.Number r2 = (java.lang.Number) r2
            int r2 = r2.intValue()
            if (r0 <= r2) goto L76
        L70:
            java.lang.Integer r0 = java.lang.Integer.valueOf(r7)
            r6.mostFrequentAcquiredInfoToDefer = r0
        L76:
            int r0 = r6.totalFrames
            java.lang.Integer r6 = r6.mostFrequentAcquiredInfoToDefer
            if (r6 == 0) goto L81
            java.lang.String r6 = r6.toString()
            goto L82
        L81:
            r6 = 0
        L82:
            r1.logFrameProcessed(r7, r0, r6)
            return
        */
        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.biometrics.BiometricMessageDeferral.processFrame(int):void");
    }

    public final void reset$1() {
        this.totalFrames = 0;
        this.mostFrequentAcquiredInfoToDefer = null;
        ((HashMap) this.acquiredInfoToFrequency).clear();
        ((HashMap) this.acquiredInfoToHelpString).clear();
        BiometricMessageDeferralLogger biometricMessageDeferralLogger = this.logBuffer;
        biometricMessageDeferralLogger.getClass();
        biometricMessageDeferralLogger.logBuffer.log(biometricMessageDeferralLogger.tag, LogLevel.DEBUG, UniversalCredentialManager.RESET_APPLET_FORM_FACTOR, null);
    }

    public final void updateMessage(int i, String str) {
        if (this.messagesToDefer.contains(Integer.valueOf(i))) {
            if (Objects.equals(((HashMap) this.acquiredInfoToHelpString).get(Integer.valueOf(i)), str)) {
                return;
            }
            this.logBuffer.logUpdateMessage(i, str);
            ((HashMap) this.acquiredInfoToHelpString).put(Integer.valueOf(i), str);
        }
    }
}
