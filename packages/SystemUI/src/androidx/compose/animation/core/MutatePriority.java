package androidx.compose.animation.core;

import com.android.systemui.util.SystemUIAnalytics;
import kotlin.enums.EnumEntriesKt;

/* JADX WARN: Failed to restore enum class, 'enum' modifier and super class removed */
/* JADX WARN: Unknown enum class pattern. Please report as an issue! */
/* loaded from: classes.dex */
public final class MutatePriority {
    public static final /* synthetic */ MutatePriority[] $VALUES;
    public static final MutatePriority Default;

    static {
        MutatePriority mutatePriority = new MutatePriority(SystemUIAnalytics.DID_NOTI_SELECT_DEFAULT, 0);
        Default = mutatePriority;
        MutatePriority[] mutatePriorityArr = {mutatePriority, new MutatePriority("UserInput", 1), new MutatePriority("PreventUserInput", 2)};
        $VALUES = mutatePriorityArr;
        EnumEntriesKt.enumEntries(mutatePriorityArr);
    }

    private MutatePriority(String str, int i) {
    }

    public static MutatePriority valueOf(String str) {
        return (MutatePriority) Enum.valueOf(MutatePriority.class, str);
    }

    public static MutatePriority[] values() {
        return (MutatePriority[]) $VALUES.clone();
    }
}
