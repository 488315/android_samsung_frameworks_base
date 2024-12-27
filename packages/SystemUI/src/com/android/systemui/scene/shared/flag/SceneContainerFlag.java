package com.android.systemui.scene.shared.flag;

import com.android.systemui.Flags;
import com.android.systemui.deviceentry.shared.DeviceEntryUdfpsRefactor;
import com.android.systemui.flags.FlagToken;
import com.android.systemui.flags.RefactorFlagUtils;
import com.android.systemui.keyguard.KeyguardBottomAreaRefactor;
import com.android.systemui.keyguard.KeyguardWmStateRefactor;
import com.android.systemui.keyguard.MigrateClocksToBlueprint;
import com.android.systemui.keyguard.shared.ComposeLockscreen;
import com.android.systemui.statusbar.notification.shared.NotificationsHeadsUpRefactor;
import com.android.systemui.statusbar.phone.PredictiveBackSysUiFlag;
import kotlin.sequences.FlatteningSequence$iterator$1;
import kotlin.sequences.Sequence;
import kotlin.sequences.SequencesKt__SequencesKt;

public final class SceneContainerFlag {
    public static final /* synthetic */ int $r8$clinit = 0;

    static {
        new SceneContainerFlag();
    }

    private SceneContainerFlag() {
    }

    public static final void assertInLegacyMode() {
        RefactorFlagUtils refactorFlagUtils = RefactorFlagUtils.INSTANCE;
        Flags.sceneContainer();
    }

    public static final String requirementDescription() {
        StringBuilder sb = new StringBuilder();
        Flags.sceneContainer();
        Sequence sequenceOf = SequencesKt__SequencesKt.sequenceOf(new FlagToken("com.android.systemui.scene_container", false));
        ComposeLockscreen.INSTANCE.getClass();
        Flags.composeLockscreen();
        FlagToken flagToken = new FlagToken("com.android.systemui.compose_lockscreen", false);
        KeyguardBottomAreaRefactor.INSTANCE.getClass();
        Flags.keyguardBottomAreaRefactor();
        FlagToken flagToken2 = new FlagToken("com.android.systemui.keyguard_bottom_area_refactor", false);
        KeyguardWmStateRefactor.INSTANCE.getClass();
        Flags.keyguardWmStateRefactor();
        FlagToken flagToken3 = new FlagToken("com.android.systemui.keyguard_wm_state_refactor", false);
        MigrateClocksToBlueprint.INSTANCE.getClass();
        Flags.migrateClocksToBlueprint();
        FlagToken flagToken4 = new FlagToken("com.android.systemui.migrate_clocks_to_blueprint", false);
        NotificationsHeadsUpRefactor.INSTANCE.getClass();
        Flags.notificationsHeadsUpRefactor();
        FlagToken flagToken5 = new FlagToken("com.android.systemui.notifications_heads_up_refactor", false);
        PredictiveBackSysUiFlag.INSTANCE.getClass();
        Flags.FEATURE_FLAGS.getClass();
        FlagToken flagToken6 = new FlagToken("com.android.systemui.predictive_back_sysui", true);
        DeviceEntryUdfpsRefactor.INSTANCE.getClass();
        Flags.deviceEntryUdfpsRefactor();
        FlatteningSequence$iterator$1 flatteningSequence$iterator$1 = new FlatteningSequence$iterator$1(SequencesKt__SequencesKt.flatten(SequencesKt__SequencesKt.sequenceOf(sequenceOf, SequencesKt__SequencesKt.sequenceOf(flagToken, flagToken2, flagToken3, flagToken4, flagToken5, flagToken6, new FlagToken("com.android.systemui.device_entry_udfps_refactor", true)))));
        while (flatteningSequence$iterator$1.ensureItemIterator()) {
            FlagToken flagToken7 = (FlagToken) flatteningSequence$iterator$1.next();
            sb.append('\n');
            sb.append(flagToken7.isEnabled ? "    [MET]" : "[NOT MET]");
            sb.append(" " + flagToken7.name);
        }
        return sb.toString();
    }
}
