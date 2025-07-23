package com.android.systemui.people;

import android.os.Bundle;
import androidx.activity.ComponentActivity;
import androidx.activity.EdgeToEdge;
import androidx.activity.compose.ComponentActivityKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.lifecycle.LifecycleKt;
import androidx.lifecycle.ViewModelProvider;
import com.android.app.tracing.coroutines.CoroutineTracingKt;
import com.android.compose.theme.PlatformThemeKt;
import com.android.systemui.people.ui.viewmodel.PeopleViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes2.dex */
public final class PeopleSpaceActivity extends ComponentActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final PeopleViewModel.Factory viewModelFactory;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    static {
        new Companion(null);
    }

    public PeopleSpaceActivity(PeopleViewModel.Factory factory) {
        this.viewModelFactory = factory;
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        EdgeToEdge.enable$default(this);
        setResult(0);
        final PeopleViewModel peopleViewModel = (PeopleViewModel) new ViewModelProvider(this, this.viewModelFactory).get(PeopleViewModel.class);
        peopleViewModel.onWidgetIdChanged.mo779invoke(Integer.valueOf(getIntent().getIntExtra("appWidgetId", 0)));
        CoroutineTracingKt.launchTraced$default(LifecycleKt.getCoroutineScope(this.lifecycleRegistry), null, null, new PeopleSpaceActivity$onCreate$1(this, peopleViewModel, null), 7);
        ComponentActivityKt.setContent$default(this, new ComposableLambdaImpl(1140881722, true, new Function2() { // from class: com.android.systemui.people.PeopleSpaceActivity$onCreate$2
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Composer composer = (Composer) obj;
                if ((((Number) obj2).intValue() & 3) == 2) {
                    ComposerImpl composerImpl = (ComposerImpl) composer;
                    if (composerImpl.getSkipping()) {
                        composerImpl.skipToGroupEnd();
                        return Unit.INSTANCE;
                    }
                }
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventStart("com.android.systemui.people.PeopleSpaceActivity.onCreate.<anonymous> (PeopleSpaceActivity.kt:64)");
                }
                final PeopleViewModel peopleViewModel2 = PeopleViewModel.this;
                final PeopleSpaceActivity peopleSpaceActivity = this;
                PlatformThemeKt.PlatformTheme(false, ComposableLambdaKt.rememberComposableLambda(-566938192, new Function2() { // from class: com.android.systemui.people.PeopleSpaceActivity$onCreate$2.1
                    /* JADX WARN: Code restructure failed: missing block: B:15:0x0041, code lost:
                    
                        if (r1 == androidx.compose.runtime.Composer.Companion.Empty) goto L15;
                     */
                    @Override // kotlin.jvm.functions.Function2
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                        To view partially-correct code enable 'Show inconsistent code' option in preferences
                    */
                    public final java.lang.Object invoke(java.lang.Object r3, java.lang.Object r4) {
                        /*
                            r2 = this;
                            androidx.compose.runtime.Composer r3 = (androidx.compose.runtime.Composer) r3
                            java.lang.Number r4 = (java.lang.Number) r4
                            int r4 = r4.intValue()
                            r4 = r4 & 3
                            r0 = 2
                            if (r4 != r0) goto L1b
                            r4 = r3
                            androidx.compose.runtime.ComposerImpl r4 = (androidx.compose.runtime.ComposerImpl) r4
                            boolean r0 = r4.getSkipping()
                            if (r0 != 0) goto L17
                            goto L1b
                        L17:
                            r4.skipToGroupEnd()
                            goto L60
                        L1b:
                            boolean r4 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                            if (r4 == 0) goto L26
                            java.lang.String r4 = "com.android.systemui.people.PeopleSpaceActivity.onCreate.<anonymous>.<anonymous> (PeopleSpaceActivity.kt:64)"
                            androidx.compose.runtime.ComposerKt.traceEventStart(r4)
                        L26:
                            androidx.compose.runtime.ComposerImpl r3 = (androidx.compose.runtime.ComposerImpl) r3
                            r4 = -620053449(0xffffffffdb0abc37, float:-3.905049E16)
                            r3.startReplaceGroup(r4)
                            com.android.systemui.people.PeopleSpaceActivity r4 = r2
                            boolean r0 = r3.changedInstance(r4)
                            java.lang.Object r1 = r3.rememberedValue()
                            if (r0 != 0) goto L43
                            androidx.compose.runtime.Composer$Companion r0 = androidx.compose.runtime.Composer.Companion
                            r0.getClass()
                            androidx.compose.runtime.Composer$Companion$Empty$1 r0 = androidx.compose.runtime.Composer.Companion.Empty
                            if (r1 != r0) goto L4b
                        L43:
                            com.android.systemui.people.PeopleSpaceActivity$onCreate$2$1$$ExternalSyntheticLambda0 r1 = new com.android.systemui.people.PeopleSpaceActivity$onCreate$2$1$$ExternalSyntheticLambda0
                            r1.<init>()
                            r3.updateRememberedValue(r1)
                        L4b:
                            kotlin.jvm.functions.Function1 r1 = (kotlin.jvm.functions.Function1) r1
                            r4 = 0
                            r3.end(r4)
                            com.android.systemui.people.ui.viewmodel.PeopleViewModel r2 = com.android.systemui.people.ui.viewmodel.PeopleViewModel.this
                            r0 = 0
                            com.android.systemui.people.ui.compose.PeopleScreenKt.PeopleScreen(r2, r1, r0, r3, r4)
                            boolean r2 = androidx.compose.runtime.ComposerKt.isTraceInProgress()
                            if (r2 == 0) goto L60
                            androidx.compose.runtime.ComposerKt.traceEventEnd()
                        L60:
                            kotlin.Unit r2 = kotlin.Unit.INSTANCE
                            return r2
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.android.systemui.people.PeopleSpaceActivity$onCreate$2.AnonymousClass1.invoke(java.lang.Object, java.lang.Object):java.lang.Object");
                    }
                }, composer), composer, 48, 1);
                if (ComposerKt.isTraceInProgress()) {
                    ComposerKt.traceEventEnd();
                }
                return Unit.INSTANCE;
            }
        }));
    }
}
