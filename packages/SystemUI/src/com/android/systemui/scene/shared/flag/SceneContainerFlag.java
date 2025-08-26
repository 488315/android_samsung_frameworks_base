package com.android.systemui.scene.shared.flag;

import com.android.systemui.flags.FlagToken;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.keyguard.KeyguardWmStateRefactor;
import com.android.systemui.statusbar.notification.shared.NotificationThrottleHun;
import kotlin.collections.ArraysKt___ArraysKt;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.sequences.FlatteningSequence;
import kotlin.sequences.FlatteningSequence.AnonymousClass1;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt__SequencesKt;

/* loaded from: classes2.dex */
public final class SceneContainerFlag {
    public static final /* synthetic */ int $r8$clinit = 0;

    static {
        new SceneContainerFlag();
    }

    private SceneContainerFlag() {
    }

    public static final void isUnexpectedlyInLegacyMode() {
        RefactorFlagUtils.INSTANCE.getClass();
        RefactorFlagUtils.assertOnEngBuild("New code path expects SceneContainerFlag to be enabled.");
    }

    public static final String requirementDescription() {
        StringBuilder sb = new StringBuilder();
        int i = 0;
        Sequence sequenceAsSequence = ArraysKt___ArraysKt.asSequence(new FlagToken[]{new FlagToken("com.android.systemui.scene_container", false)});
        KeyguardWmStateRefactor.INSTANCE.getClass();
        FlagToken flagToken = new FlagToken("com.android.systemui.keyguard_wm_state_refactor", false);
        NotificationThrottleHun.INSTANCE.getClass();
        FlatteningSequence.AnonymousClass1 anonymousClass1 = SequencesKt__SequencesKt.flatten(ArraysKt___ArraysKt.asSequence(new Sequence[]{sequenceAsSequence, ArraysKt___ArraysKt.asSequence(new FlagToken[]{flagToken, new FlagToken("com.android.systemui.notification_avalanche_throttle_hun", true)})})).new AnonymousClass1();
        while (anonymousClass1.hasNext()) {
            Object next = anonymousClass1.next();
            int i2 = i + 1;
            if (i < 0) {
                CollectionsKt__CollectionsKt.throwIndexOverflow();
                throw null;
            }
            FlagToken flagToken2 = (FlagToken) next;
            if (i > 0) {
                sb.append('\n');
            }
            sb.append(flagToken2.isEnabled ? "    [MET]" : "[NOT MET]");
            sb.append(" " + flagToken2.name);
            i = i2;
        }
        return sb.toString();
    }

    public static final void unsafeAssertInNewMode() {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        throw new IllegalStateException("New code path not supported when SceneContainerFlag is disabled.".toString());
    }
}
