package androidx.fragment.app;

import com.android.systemui.R;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public enum SeslFragmentTransactionAnimationSet {
    /* JADX INFO: Fake field, exist only in values array */
    Horizontal(R.anim.sesl_fragment_open_enter, R.anim.sesl_fragment_open_exit, R.anim.sesl_fragment_close_enter, R.anim.sesl_fragment_close_exit),
    /* JADX INFO: Fake field, exist only in values array */
    HorizontalForRTL(R.anim.sesl_fragment_open_enter_rtl, R.anim.sesl_fragment_open_exit_rtl, R.anim.sesl_fragment_close_enter_rtl, R.anim.sesl_fragment_close_exit_rtl);

    public static final Companion Companion = new Companion(null);
    private final int enter;
    private final int exit;
    private final int popEnter;
    private final int popExit;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    SeslFragmentTransactionAnimationSet(int i, int i2, int i3, int i4) {
        this.enter = i;
        this.exit = i2;
        this.popEnter = i3;
        this.popExit = i4;
    }

    public final int getEnter$fragment_release() {
        return this.enter;
    }

    public final int getExit$fragment_release() {
        return this.exit;
    }

    public final int getPopEnter$fragment_release() {
        return this.popEnter;
    }

    public final int getPopExit$fragment_release() {
        return this.popExit;
    }
}
