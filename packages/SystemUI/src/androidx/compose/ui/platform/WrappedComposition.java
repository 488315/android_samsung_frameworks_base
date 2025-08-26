package androidx.compose.ui.platform;

import android.view.View;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.Composition;
import androidx.compose.runtime.CompositionDataImpl;
import androidx.compose.runtime.CompositionLocalKt;
import androidx.compose.runtime.EffectsKt;
import androidx.compose.runtime.ProvidedValue;
import androidx.compose.runtime.SlotTable;
import androidx.compose.runtime.SlotWriter;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.compose.runtime.tooling.InspectionTablesKt;
import androidx.compose.ui.platform.AndroidComposeView;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;
import com.android.systemui.R;
import java.util.Set;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.Lambda;
import kotlin.jvm.internal.markers.KMappedMarker;
import kotlin.jvm.internal.markers.KMutableSet;

/* loaded from: classes.dex */
final class WrappedComposition implements Composition, LifecycleEventObserver {
    public Lifecycle addedToLifecycle;
    public boolean disposed;
    public Function2 lastContent;
    public final Composition original;
    public final AndroidComposeView owner;

    /* renamed from: androidx.compose.ui.platform.WrappedComposition$setContent$1, reason: invalid class name */
    final class AnonymousClass1 extends Lambda implements Function1 {
        final /* synthetic */ Function2 $content;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public AnonymousClass1(Function2 function2) {
            super(1);
            this.$content = function2;
        }

        @Override // kotlin.jvm.functions.Function1
        /* renamed from: invoke */
        public final Object mo781invoke(Object obj) {
            AndroidComposeView.ViewTreeOwners viewTreeOwners = (AndroidComposeView.ViewTreeOwners) obj;
            if (!WrappedComposition.this.disposed) {
                Lifecycle lifecycle = viewTreeOwners.lifecycleOwner.getLifecycle();
                WrappedComposition wrappedComposition = WrappedComposition.this;
                wrappedComposition.lastContent = this.$content;
                if (wrappedComposition.addedToLifecycle == null) {
                    wrappedComposition.addedToLifecycle = lifecycle;
                    lifecycle.addObserver(wrappedComposition);
                } else if (lifecycle.getCurrentState().isAtLeast(Lifecycle.State.CREATED)) {
                    final WrappedComposition wrappedComposition2 = WrappedComposition.this;
                    Composition composition = wrappedComposition2.original;
                    final Function2 function2 = this.$content;
                    composition.setContent(new ComposableLambdaImpl(-2000640158, true, new Function2() { // from class: androidx.compose.ui.platform.WrappedComposition.setContent.1.1
                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                        {
                            super(2);
                        }

                        /* JADX WARN: Removed duplicated region for block: B:47:0x00b4  */
                        /* JADX WARN: Removed duplicated region for block: B:52:0x00d8  */
                        @Override // kotlin.jvm.functions.Function2
                        /*
                            Code decompiled incorrectly, please refer to instructions dump.
                        */
                        public final Object invoke(Object obj2, Object obj3) {
                            Composer composer = (Composer) obj2;
                            int iIntValue = ((Number) obj3).intValue();
                            boolean z = false;
                            ComposerImpl composerImpl = (ComposerImpl) composer;
                            if (composerImpl.shouldExecute(iIntValue & 1, (iIntValue & 3) != 2)) {
                                if (ComposerKt.isTraceInProgress()) {
                                    ComposerKt.traceEventStart("androidx.compose.ui.platform.WrappedComposition.setContent.<anonymous>.<anonymous> (Wrapper.android.kt:123)");
                                }
                                Object tag = wrappedComposition2.owner.getTag(R.id.inspection_slot_table_set);
                                if ((tag instanceof Set) && (!(tag instanceof KMappedMarker) || (tag instanceof KMutableSet))) {
                                    z = true;
                                }
                                Set set = z ? (Set) tag : null;
                                if (set == null) {
                                    Object parent = wrappedComposition2.owner.getParent();
                                    View view = parent instanceof View ? (View) parent : null;
                                    Object tag2 = view != null ? view.getTag(R.id.inspection_slot_table_set) : null;
                                    set = (!(tag2 instanceof Set) || ((tag2 instanceof KMappedMarker) && !(tag2 instanceof KMutableSet))) ? null : (Set) tag2;
                                }
                                if (set != null) {
                                    CompositionDataImpl compositionDataImpl = composerImpl._compositionData;
                                    if (compositionDataImpl == null) {
                                        compositionDataImpl = new CompositionDataImpl(composerImpl.composition);
                                        composerImpl._compositionData = compositionDataImpl;
                                    }
                                    set.add(compositionDataImpl);
                                    composerImpl.forceRecomposeScopes = true;
                                    composerImpl.sourceMarkersEnabled = true;
                                    composerImpl.slotTable.collectSourceInformation();
                                    composerImpl.insertTable.collectSourceInformation();
                                    SlotWriter slotWriter = composerImpl.writer;
                                    SlotTable slotTable = slotWriter.table;
                                    slotWriter.sourceInformationMap = slotTable.sourceInformationMap;
                                    slotWriter.calledByMap = slotTable.calledByMap;
                                }
                                WrappedComposition wrappedComposition3 = wrappedComposition2;
                                AndroidComposeView androidComposeView = wrappedComposition3.owner;
                                boolean zChangedInstance = composerImpl.changedInstance(wrappedComposition3);
                                WrappedComposition wrappedComposition4 = wrappedComposition2;
                                Object objRememberedValue = composerImpl.rememberedValue();
                                Composer.Companion companion = Composer.Companion;
                                if (!zChangedInstance) {
                                    companion.getClass();
                                    if (objRememberedValue == Composer.Companion.Empty) {
                                        objRememberedValue = new WrappedComposition$setContent$1$1$1$1(wrappedComposition4, null);
                                        composerImpl.updateRememberedValue(objRememberedValue);
                                    }
                                    EffectsKt.LaunchedEffect(composerImpl, androidComposeView, (Function2) objRememberedValue);
                                    WrappedComposition wrappedComposition5 = wrappedComposition2;
                                    AndroidComposeView androidComposeView2 = wrappedComposition5.owner;
                                    boolean zChangedInstance2 = composerImpl.changedInstance(wrappedComposition5);
                                    WrappedComposition wrappedComposition6 = wrappedComposition2;
                                    Object objRememberedValue2 = composerImpl.rememberedValue();
                                    if (!zChangedInstance2) {
                                        companion.getClass();
                                        if (objRememberedValue2 == Composer.Companion.Empty) {
                                            objRememberedValue2 = new WrappedComposition$setContent$1$1$2$1(wrappedComposition6, null);
                                            composerImpl.updateRememberedValue(objRememberedValue2);
                                        }
                                        EffectsKt.LaunchedEffect(composerImpl, androidComposeView2, (Function2) objRememberedValue2);
                                        ProvidedValue providedValueDefaultProvidedValue$runtime_release = InspectionTablesKt.LocalInspectionTables.defaultProvidedValue$runtime_release(set);
                                        final WrappedComposition wrappedComposition7 = wrappedComposition2;
                                        final Function2 function22 = function2;
                                        CompositionLocalKt.CompositionLocalProvider(providedValueDefaultProvidedValue$runtime_release, ComposableLambdaKt.rememberComposableLambda(-1193460702, new Function2() { // from class: androidx.compose.ui.platform.WrappedComposition.setContent.1.1.3
                                            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                            {
                                                super(2);
                                            }

                                            @Override // kotlin.jvm.functions.Function2
                                            public final Object invoke(Object obj4, Object obj5) {
                                                Composer composer2 = (Composer) obj4;
                                                int iIntValue2 = ((Number) obj5).intValue();
                                                ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                                                if (composerImpl2.shouldExecute(iIntValue2 & 1, (iIntValue2 & 3) != 2)) {
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventStart("androidx.compose.ui.platform.WrappedComposition.setContent.<anonymous>.<anonymous>.<anonymous> (Wrapper.android.kt:139)");
                                                    }
                                                    AndroidCompositionLocals_androidKt.ProvideAndroidCompositionLocals(wrappedComposition7.owner, function22, composerImpl2, 0);
                                                    if (ComposerKt.isTraceInProgress()) {
                                                        ComposerKt.traceEventEnd();
                                                    }
                                                } else {
                                                    composerImpl2.skipToGroupEnd();
                                                }
                                                return Unit.INSTANCE;
                                            }
                                        }, composerImpl), composerImpl, 56);
                                        if (ComposerKt.isTraceInProgress()) {
                                            ComposerKt.traceEventEnd();
                                        }
                                    }
                                }
                            } else {
                                composerImpl.skipToGroupEnd();
                            }
                            return Unit.INSTANCE;
                        }
                    }));
                }
            }
            return Unit.INSTANCE;
        }
    }

    public WrappedComposition(AndroidComposeView androidComposeView, Composition composition) {
        this.owner = androidComposeView;
        this.original = composition;
        ComposableSingletons$Wrapper_androidKt.INSTANCE.getClass();
        this.lastContent = ComposableSingletons$Wrapper_androidKt.f20lambda1;
    }

    @Override // androidx.compose.runtime.Composition
    public final void dispose() {
        if (!this.disposed) {
            this.disposed = true;
            AndroidComposeView androidComposeView = this.owner;
            androidComposeView.getClass();
            androidComposeView.setTag(R.id.wrapped_composition_tag, null);
            Lifecycle lifecycle = this.addedToLifecycle;
            if (lifecycle != null) {
                lifecycle.removeObserver(this);
            }
        }
        this.original.dispose();
    }

    @Override // androidx.compose.runtime.Composition
    public final boolean isDisposed() {
        return this.original.isDisposed();
    }

    @Override // androidx.lifecycle.LifecycleEventObserver
    public final void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            dispose();
        } else {
            if (event != Lifecycle.Event.ON_CREATE || this.disposed) {
                return;
            }
            setContent(this.lastContent);
        }
    }

    @Override // androidx.compose.runtime.Composition
    public final void setContent(Function2 function2) {
        AnonymousClass1 anonymousClass1 = new AnonymousClass1(function2);
        AndroidComposeView androidComposeView = this.owner;
        AndroidComposeView.ViewTreeOwners viewTreeOwners = androidComposeView.getViewTreeOwners();
        if (viewTreeOwners != null) {
            anonymousClass1.mo781invoke(viewTreeOwners);
        }
        if (androidComposeView.isAttachedToWindow()) {
            return;
        }
        androidComposeView.onViewTreeOwnersAvailable = anonymousClass1;
    }
}
