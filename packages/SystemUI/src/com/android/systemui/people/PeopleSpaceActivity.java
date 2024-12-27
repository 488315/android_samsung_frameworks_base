package com.android.systemui.people;

import android.os.Bundle;
import androidx.activity.ComponentActivity;
import androidx.activity.compose.ComponentActivityKt;
import androidx.compose.runtime.Composer;
import androidx.compose.runtime.ComposerImpl;
import androidx.compose.runtime.ComposerKt;
import androidx.compose.runtime.OpaqueKey;
import androidx.compose.runtime.internal.ComposableLambdaImpl;
import androidx.compose.runtime.internal.ComposableLambdaKt;
import androidx.lifecycle.LifecycleKt;
import androidx.lifecycle.ViewModelProvider;
import com.android.compose.theme.PlatformThemeKt;
import com.android.systemui.people.ui.compose.PeopleScreenKt;
import com.android.systemui.people.ui.viewmodel.PeopleViewModel;
import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.functions.Function2;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlinx.coroutines.BuildersKt;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
public final class PeopleSpaceActivity extends ComponentActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final PeopleViewModel.Factory viewModelFactory;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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

    public PeopleSpaceActivity(PeopleViewModel.Factory factory) {
        this.viewModelFactory = factory;
    }

    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setResult(0);
        final PeopleViewModel peopleViewModel = (PeopleViewModel) new ViewModelProvider(this, this.viewModelFactory).get(PeopleViewModel.class);
        peopleViewModel.onWidgetIdChanged.invoke(Integer.valueOf(getIntent().getIntExtra("appWidgetId", 0)));
        BuildersKt.launch$default(LifecycleKt.getCoroutineScope(getLifecycle()), null, null, new PeopleSpaceActivity$onCreate$1(this, peopleViewModel, null), 3);
        ComponentActivityKt.setContent$default(this, new ComposableLambdaImpl(1140881722, true, new Function2() { // from class: com.android.systemui.people.PeopleSpaceActivity$onCreate$2
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(2);
            }

            /* JADX WARN: Type inference failed for: r4v5, types: [com.android.systemui.people.PeopleSpaceActivity$onCreate$2$1, kotlin.jvm.internal.Lambda] */
            @Override // kotlin.jvm.functions.Function2
            public final Object invoke(Object obj, Object obj2) {
                Composer composer = (Composer) obj;
                if ((((Number) obj2).intValue() & 11) == 2) {
                    ComposerImpl composerImpl = (ComposerImpl) composer;
                    if (composerImpl.getSkipping()) {
                        composerImpl.skipToGroupEnd();
                        return Unit.INSTANCE;
                    }
                }
                OpaqueKey opaqueKey = ComposerKt.invocation;
                final PeopleViewModel peopleViewModel2 = PeopleViewModel.this;
                final PeopleSpaceActivity peopleSpaceActivity = this;
                PlatformThemeKt.PlatformTheme(false, ComposableLambdaKt.rememberComposableLambda(-566938192, composer, new Function2() { // from class: com.android.systemui.people.PeopleSpaceActivity$onCreate$2.1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(2);
                    }

                    @Override // kotlin.jvm.functions.Function2
                    public final Object invoke(Object obj3, Object obj4) {
                        Composer composer2 = (Composer) obj3;
                        if ((((Number) obj4).intValue() & 11) == 2) {
                            ComposerImpl composerImpl2 = (ComposerImpl) composer2;
                            if (composerImpl2.getSkipping()) {
                                composerImpl2.skipToGroupEnd();
                                return Unit.INSTANCE;
                            }
                        }
                        OpaqueKey opaqueKey2 = ComposerKt.invocation;
                        PeopleViewModel peopleViewModel3 = PeopleViewModel.this;
                        final PeopleSpaceActivity peopleSpaceActivity2 = peopleSpaceActivity;
                        PeopleScreenKt.PeopleScreen(peopleViewModel3, new Function1() { // from class: com.android.systemui.people.PeopleSpaceActivity.onCreate.2.1.1
                            {
                                super(1);
                            }

                            @Override // kotlin.jvm.functions.Function1
                            public final Object invoke(Object obj5) {
                                PeopleViewModel.Result result = (PeopleViewModel.Result) obj5;
                                PeopleSpaceActivity peopleSpaceActivity3 = PeopleSpaceActivity.this;
                                int i = PeopleSpaceActivity.$r8$clinit;
                                peopleSpaceActivity3.getClass();
                                if (result instanceof PeopleViewModel.Result.Success) {
                                    peopleSpaceActivity3.setResult(-1, ((PeopleViewModel.Result.Success) result).data);
                                } else {
                                    peopleSpaceActivity3.setResult(0);
                                }
                                peopleSpaceActivity3.finish();
                                return Unit.INSTANCE;
                            }
                        }, composer2, 8);
                        return Unit.INSTANCE;
                    }
                }), composer, 48, 1);
                return Unit.INSTANCE;
            }
        }));
    }
}
