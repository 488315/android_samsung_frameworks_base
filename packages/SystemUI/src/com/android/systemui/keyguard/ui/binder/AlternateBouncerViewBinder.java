package com.android.systemui.keyguard.ui.binder;

import androidx.constraintlayout.widget.ConstraintLayout;
import com.android.systemui.CoreStartable;
import com.android.systemui.Flags;
import dagger.Lazy;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;
import kotlinx.coroutines.CoroutineScope;

public final class AlternateBouncerViewBinder implements CoreStartable {
    public final Lazy alternateBouncerDependencies;
    public ConstraintLayout alternateBouncerView;
    public final Lazy alternateBouncerWindowViewModel;
    public final CoroutineScope applicationScope;
    public final Lazy layoutInflater;
    public final AlternateBouncerViewBinder$onAttachAddBackGestureHandler$1 onAttachAddBackGestureHandler = new AlternateBouncerViewBinder$onAttachAddBackGestureHandler$1(this);
    public final Lazy windowManager;

    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    static {
        new Companion(null);
    }

    public AlternateBouncerViewBinder(CoroutineScope coroutineScope, Lazy lazy, Lazy lazy2, Lazy lazy3, Lazy lazy4) {
        this.applicationScope = coroutineScope;
        this.alternateBouncerWindowViewModel = lazy;
        this.alternateBouncerDependencies = lazy2;
        this.windowManager = lazy3;
        this.layoutInflater = lazy4;
    }

    @Override // com.android.systemui.CoreStartable
    public final void start() {
        Flags.deviceEntryUdfpsRefactor();
        BuildersKt.launch$default(this.applicationScope, EmptyCoroutineContext.INSTANCE, null, new AlternateBouncerViewBinder$start$$inlined$launch$default$1("AlternateBouncerViewBinder#alternateBouncerWindowViewModel", null, this), 2);
    }
}
