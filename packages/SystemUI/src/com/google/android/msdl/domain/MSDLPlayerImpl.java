package com.google.android.msdl.domain;

import android.os.VibrationAttributes;
import android.os.VibrationEffect;
import android.os.Vibrator;
import com.google.android.msdl.data.model.HapticComposition;
import com.google.android.msdl.data.model.HapticToken;
import com.google.android.msdl.data.model.MSDLToken;
import com.google.android.msdl.data.repository.MSDLHapticData;
import com.google.android.msdl.data.repository.MSDLRepository;
import com.google.android.msdl.data.repository.MSDLRepositoryImpl;
import com.google.android.msdl.domain.InteractionProperties;
import com.google.android.msdl.domain.MSDLPlayer;
import com.google.android.msdl.logging.MSDLEvent;
import com.google.android.msdl.logging.MSDLHistoryLoggerImpl;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executor;
import kotlin.collections.CollectionsKt___CollectionsKt;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.jvm.internal.Intrinsics;
import kotlin.text.StringsKt__IndentKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public final class MSDLPlayerImpl implements MSDLPlayer {
    public static final Companion Companion = new Companion(null);
    public static final List REQUIRED_PRIMITIVES = Arrays.asList(3, 2, 7, 1, 8);
    public final Executor executor;
    public final MSDLHistoryLoggerImpl historyLogger = new MSDLHistoryLoggerImpl(20);
    public final MSDLRepository repository;
    public final Map useHapticFallbackForToken;
    public final Vibrator vibrator;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public MSDLPlayerImpl(MSDLRepository mSDLRepository, Vibrator vibrator, Executor executor, Map<MSDLToken, Boolean> map) {
        this.repository = mSDLRepository;
        this.vibrator = vibrator;
        this.executor = executor;
        this.useHapticFallbackForToken = map;
    }

    @Override // com.google.android.msdl.domain.MSDLPlayer
    public final List getHistory() {
        return CollectionsKt___CollectionsKt.toList(this.historyLogger.history);
    }

    @Override // com.google.android.msdl.domain.MSDLPlayer
    public final void playToken(MSDLToken mSDLToken, InteractionProperties interactionProperties) {
        final VibrationAttributes build;
        MSDLPlayer.Companion.getClass();
        if (MSDLPlayer.Companion.SYSTEM_FEEDBACK_LEVEL.compareTo(mSDLToken.getMinimumFeedbackLevel()) < 0) {
            return;
        }
        HapticToken hapticToken = mSDLToken.getHapticToken();
        MSDLRepository mSDLRepository = this.repository;
        ((MSDLRepositoryImpl) mSDLRepository).getClass();
        MSDLHapticData mSDLHapticData = (MSDLHapticData) MSDLRepositoryImpl.HAPTIC_DATA.get(hapticToken);
        mSDLRepository.getClass();
        if (mSDLHapticData == null) {
            return;
        }
        HapticComposition hapticComposition = mSDLHapticData.get();
        final VibrationEffect composeIntoVibrationEffect$default = Intrinsics.areEqual(this.useHapticFallbackForToken.get(mSDLToken), Boolean.TRUE) ? hapticComposition.fallbackEffect : interactionProperties instanceof InteractionProperties.DynamicVibrationScale ? MSDLPlayerImplKt.composeIntoVibrationEffect$default(hapticComposition, Float.valueOf(((InteractionProperties.DynamicVibrationScale) interactionProperties).scale), 2) : MSDLPlayerImplKt.composeIntoVibrationEffect$default(hapticComposition, null, 3);
        if (composeIntoVibrationEffect$default == null || !this.vibrator.hasVibrator()) {
            return;
        }
        if ((interactionProperties != null ? interactionProperties.getVibrationAttributes() : null) != null) {
            build = interactionProperties.getVibrationAttributes();
        } else {
            build = new VibrationAttributes.Builder().setUsage(18).build();
            build.getClass();
        }
        this.executor.execute(new Runnable() { // from class: com.google.android.msdl.domain.MSDLPlayerImpl$playData$1
            @Override // java.lang.Runnable
            public final void run() {
                MSDLPlayerImpl.this.vibrator.vibrate(composeIntoVibrationEffect$default, build);
            }
        });
        MSDLEvent mSDLEvent = new MSDLEvent(mSDLToken, interactionProperties);
        MSDLHistoryLoggerImpl mSDLHistoryLoggerImpl = this.historyLogger;
        if (((ArrayDeque) mSDLHistoryLoggerImpl.history).size() == mSDLHistoryLoggerImpl.maxHistorySize) {
            ((ArrayDeque) mSDLHistoryLoggerImpl.history).removeFirst();
        }
        ((ArrayDeque) mSDLHistoryLoggerImpl.history).addLast(mSDLEvent);
    }

    public final String toString() {
        return StringsKt__IndentKt.trimIndent("\n            Default MSDL player implementation.\n            Vibrator: " + this.vibrator + "\n            Repository: " + this.repository + "\n        ");
    }
}
