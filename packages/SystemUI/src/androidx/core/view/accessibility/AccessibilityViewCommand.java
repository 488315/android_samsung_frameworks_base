package androidx.core.view.accessibility;

import android.view.View;

/* loaded from: classes.dex */
public interface AccessibilityViewCommand {

    public abstract class CommandArguments {
    }

    public final class MoveAtGranularityArguments extends CommandArguments {
    }

    public final class MoveHtmlArguments extends CommandArguments {
    }

    public final class MoveWindowArguments extends CommandArguments {
    }

    public final class ScrollToPositionArguments extends CommandArguments {
    }

    public final class SetProgressArguments extends CommandArguments {
    }

    public final class SetSelectionArguments extends CommandArguments {
    }

    public final class SetTextArguments extends CommandArguments {
    }

    boolean perform(View view);
}
