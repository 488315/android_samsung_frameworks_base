package androidx.compose.ui.focus;

import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.Lambda;

/* loaded from: classes.dex */
public final class FocusPropertiesImpl implements FocusProperties {
    public boolean canFocus = true;
    public final FocusRequester down;
    public final FocusRequester end;
    public final FocusRequester left;
    public final FocusRequester next;
    public Lambda onEnter;
    public Lambda onExit;
    public final FocusRequester previous;
    public final FocusRequester right;
    public final FocusRequester start;
    public final FocusRequester up;

    public FocusPropertiesImpl() {
        FocusRequester.Companion.getClass();
        FocusRequester focusRequester = FocusRequester.Default;
        this.next = focusRequester;
        this.previous = focusRequester;
        this.up = focusRequester;
        this.down = focusRequester;
        this.left = focusRequester;
        this.right = focusRequester;
        this.start = focusRequester;
        this.end = focusRequester;
        this.onEnter = new Function1() { // from class: androidx.compose.ui.focus.FocusPropertiesImpl$onEnter$1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj) {
                return Unit.INSTANCE;
            }
        };
        this.onExit = new Function1() { // from class: androidx.compose.ui.focus.FocusPropertiesImpl$onExit$1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final /* bridge */ /* synthetic */ Object mo781invoke(Object obj) {
                return Unit.INSTANCE;
            }
        };
    }

    @Override // androidx.compose.ui.focus.FocusProperties
    public final boolean getCanFocus() {
        return this.canFocus;
    }

    @Override // androidx.compose.ui.focus.FocusProperties
    public final void setCanFocus(boolean z) {
        this.canFocus = z;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.compose.ui.focus.FocusProperties
    public final void setOnEnter(Function1 function1) {
        this.onEnter = (Lambda) function1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    @Override // androidx.compose.ui.focus.FocusProperties
    public final void setOnExit(Function1 function1) {
        this.onExit = (Lambda) function1;
    }
}
