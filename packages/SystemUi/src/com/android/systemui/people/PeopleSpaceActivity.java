package com.android.systemui.people;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.activity.ComponentActivity;
import androidx.lifecycle.ViewModelProvider;
import com.android.systemui.R;
import com.android.systemui.compose.ComposeFacade;
import com.android.systemui.people.p012ui.view.PeopleViewBinder;
import com.android.systemui.people.p012ui.viewmodel.PeopleViewModel;
import kotlin.jvm.functions.Function1;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public class PeopleSpaceActivity extends ComponentActivity {
    public static final /* synthetic */ int $r8$clinit = 0;
    public final PeopleViewModel.Factory mViewModelFactory;

    public PeopleSpaceActivity(PeopleViewModel.Factory factory) {
        this.mViewModelFactory = factory;
    }

    /* JADX WARN: Type inference failed for: r5v4, types: [com.android.systemui.people.PeopleSpaceActivity$$ExternalSyntheticLambda0] */
    @Override // androidx.activity.ComponentActivity, androidx.core.app.ComponentActivity, android.app.Activity
    public final void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        setResult(0);
        PeopleViewModel peopleViewModel = (PeopleViewModel) new ViewModelProvider(this, this.mViewModelFactory).get(PeopleViewModel.class);
        peopleViewModel._appWidgetId.setValue(Integer.valueOf(getIntent().getIntExtra("appWidgetId", 0)));
        ?? r5 = new Function1() { // from class: com.android.systemui.people.PeopleSpaceActivity$$ExternalSyntheticLambda0
            @Override // kotlin.jvm.functions.Function1
            public final Object invoke(Object obj) {
                PeopleViewModel.Result result = (PeopleViewModel.Result) obj;
                int i = PeopleSpaceActivity.$r8$clinit;
                PeopleSpaceActivity peopleSpaceActivity = PeopleSpaceActivity.this;
                peopleSpaceActivity.getClass();
                if (result instanceof PeopleViewModel.Result.Success) {
                    peopleSpaceActivity.setResult(-1, ((PeopleViewModel.Result.Success) result).data);
                } else {
                    peopleSpaceActivity.setResult(0);
                }
                peopleSpaceActivity.finish();
                return null;
            }
        };
        ComposeFacade.INSTANCE.getClass();
        Log.d("PeopleSpaceActivity", "Using the View implementation of the PeopleSpaceActivity");
        PeopleViewBinder peopleViewBinder = PeopleViewBinder.INSTANCE;
        ViewGroup viewGroup = (ViewGroup) LayoutInflater.from(this).inflate(R.layout.people_space_activity, (ViewGroup) null);
        PeopleViewBinder.bind(viewGroup, peopleViewModel, this, r5);
        setContentView(viewGroup);
    }
}
