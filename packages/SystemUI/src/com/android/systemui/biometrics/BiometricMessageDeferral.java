package com.android.systemui.biometrics;

import com.android.keyguard.logging.BiometricMessageDeferralLogger;
import com.android.keyguard.logging.BiometricMessageDeferralLogger$$ExternalSyntheticLambda1;
import com.android.systemui.Dumpable;
import com.android.systemui.deviceentry.shared.model.HelpFaceAuthenticationStatus;
import com.android.systemui.dump.DumpManager;
import com.android.systemui.log.LogBuffer;
import com.android.systemui.log.LogMessageImpl;
import com.android.systemui.log.core.LogLevel;
import com.android.systemui.log.core.LogMessage;
import com.android.systemui.util.time.SystemClock;
import com.samsung.android.knox.ucm.configurator.UniversalCredentialManager;
import dagger.Lazy;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public class BiometricMessageDeferral implements Dumpable {
    public final Map acquiredInfoToHelpString;
    public final Set acquiredInfoToIgnore;
    public final FaceHelpMessageDebouncer faceHelpMessageDebouncer;
    public final BiometricMessageDeferralLogger logBuffer;
    public final Set messagesToDefer;
    public final Lazy systemClock;
    public final float threshold;
    public int totalFrames;
    public final long windowToAnalyzeLastNFrames;

    public BiometricMessageDeferral(Set<Integer> set, Set<Integer> set2, float f, long j, BiometricMessageDeferralLogger biometricMessageDeferralLogger, DumpManager dumpManager, String str, Lazy lazy) {
        this.messagesToDefer = set;
        this.acquiredInfoToIgnore = set2;
        this.threshold = f;
        this.windowToAnalyzeLastNFrames = j;
        this.logBuffer = biometricMessageDeferralLogger;
        this.systemClock = lazy;
        this.faceHelpMessageDebouncer = new FaceHelpMessageDebouncer(j, 0L, 0, f);
        new HashMap();
        this.acquiredInfoToHelpString = new HashMap();
        dumpManager.registerNormalDumpable(getClass().getName() + "[" + str + "]", this);
    }

    @Override // com.android.systemui.Dumpable
    public final void dump(PrintWriter printWriter, String[] strArr) {
        printWriter.println("messagesToDefer=" + this.messagesToDefer);
        printWriter.println("totalFrames=" + this.totalFrames);
        printWriter.println("threshold=" + this.threshold);
        printWriter.println("faceMessageDeferUpdateFlagEnabled=true");
        printWriter.println("windowToAnalyzeLastNFrames(ms)=" + this.windowToAnalyzeLastNFrames);
    }

    public final CharSequence getDeferredMessage() {
        FaceHelpMessageDebouncer faceHelpMessageDebouncer = this.faceHelpMessageDebouncer;
        if (faceHelpMessageDebouncer == null) {
            return null;
        }
        HelpFaceAuthenticationStatus messageToShow = faceHelpMessageDebouncer.getMessageToShow(((SystemClock) this.systemClock.get()).elapsedRealtime());
        return (CharSequence) ((HashMap) this.acquiredInfoToHelpString).get(messageToShow != null ? Integer.valueOf(messageToShow.msgId) : null);
    }

    public final void processFrame(int i) {
        HelpFaceAuthenticationStatus messageToShow;
        if (this.messagesToDefer.isEmpty()) {
            return;
        }
        boolean contains = this.acquiredInfoToIgnore.contains(Integer.valueOf(i));
        BiometricMessageDeferralLogger biometricMessageDeferralLogger = this.logBuffer;
        if (contains) {
            biometricMessageDeferralLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            BiometricMessageDeferralLogger$$ExternalSyntheticLambda1 biometricMessageDeferralLogger$$ExternalSyntheticLambda1 = new BiometricMessageDeferralLogger$$ExternalSyntheticLambda1(1);
            LogBuffer logBuffer = biometricMessageDeferralLogger.logBuffer;
            LogMessage obtain = logBuffer.obtain(biometricMessageDeferralLogger.tag, logLevel, biometricMessageDeferralLogger$$ExternalSyntheticLambda1, null);
            ((LogMessageImpl) obtain).int1 = i;
            logBuffer.commit(obtain);
            return;
        }
        this.totalFrames++;
        Lazy lazy = this.systemClock;
        FaceHelpMessageDebouncer faceHelpMessageDebouncer = this.faceHelpMessageDebouncer;
        if (faceHelpMessageDebouncer != null) {
            HelpFaceAuthenticationStatus helpFaceAuthenticationStatus = new HelpFaceAuthenticationStatus(i, null, ((SystemClock) lazy.get()).elapsedRealtime());
            if (this.totalFrames == 1) {
                faceHelpMessageDebouncer.startNewFaceAuthSession(helpFaceAuthenticationStatus.createdAt);
            }
            ((ArrayList) faceHelpMessageDebouncer.helpFaceAuthStatuses).add(helpFaceAuthenticationStatus);
            Objects.toString(helpFaceAuthenticationStatus);
        }
        int i2 = this.totalFrames;
        String valueOf = String.valueOf((faceHelpMessageDebouncer == null || (messageToShow = faceHelpMessageDebouncer.getMessageToShow(((SystemClock) lazy.get()).elapsedRealtime())) == null) ? null : Integer.valueOf(messageToShow.msgId));
        biometricMessageDeferralLogger.getClass();
        LogLevel logLevel2 = LogLevel.DEBUG;
        BiometricMessageDeferralLogger$$ExternalSyntheticLambda1 biometricMessageDeferralLogger$$ExternalSyntheticLambda12 = new BiometricMessageDeferralLogger$$ExternalSyntheticLambda1(0);
        LogBuffer logBuffer2 = biometricMessageDeferralLogger.logBuffer;
        LogMessage obtain2 = logBuffer2.obtain(biometricMessageDeferralLogger.tag, logLevel2, biometricMessageDeferralLogger$$ExternalSyntheticLambda12, null);
        LogMessageImpl logMessageImpl = (LogMessageImpl) obtain2;
        logMessageImpl.int1 = i;
        logMessageImpl.int2 = i2;
        logMessageImpl.str1 = valueOf;
        logBuffer2.commit(obtain2);
    }

    public final void reset$1() {
        this.totalFrames = 0;
        ((HashMap) this.acquiredInfoToHelpString).clear();
        BiometricMessageDeferralLogger biometricMessageDeferralLogger = this.logBuffer;
        biometricMessageDeferralLogger.getClass();
        LogBuffer.log$default(biometricMessageDeferralLogger.logBuffer, biometricMessageDeferralLogger.tag, LogLevel.DEBUG, UniversalCredentialManager.RESET_APPLET_FORM_FACTOR);
    }

    public final void updateMessage(int i, String str) {
        if (this.messagesToDefer.contains(Integer.valueOf(i))) {
            if (Objects.equals(((HashMap) this.acquiredInfoToHelpString).get(Integer.valueOf(i)), str)) {
                return;
            }
            BiometricMessageDeferralLogger biometricMessageDeferralLogger = this.logBuffer;
            biometricMessageDeferralLogger.getClass();
            LogLevel logLevel = LogLevel.DEBUG;
            BiometricMessageDeferralLogger$$ExternalSyntheticLambda1 biometricMessageDeferralLogger$$ExternalSyntheticLambda1 = new BiometricMessageDeferralLogger$$ExternalSyntheticLambda1(2);
            String str2 = biometricMessageDeferralLogger.tag;
            LogBuffer logBuffer = biometricMessageDeferralLogger.logBuffer;
            LogMessage obtain = logBuffer.obtain(str2, logLevel, biometricMessageDeferralLogger$$ExternalSyntheticLambda1, null);
            LogMessageImpl logMessageImpl = (LogMessageImpl) obtain;
            logMessageImpl.int1 = i;
            logMessageImpl.str1 = str;
            logBuffer.commit(obtain);
            ((HashMap) this.acquiredInfoToHelpString).put(Integer.valueOf(i), str);
        }
    }
}
