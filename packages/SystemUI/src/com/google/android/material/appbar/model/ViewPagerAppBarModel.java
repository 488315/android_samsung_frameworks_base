package com.google.android.material.appbar.model;

import android.content.Context;
import com.google.android.material.appbar.model.view.AppBarView;
import com.google.android.material.appbar.model.view.ViewPagerAppBarView;
import java.util.List;
import kotlin.collections.EmptyList;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.reflect.KClass;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes4.dex */
public class ViewPagerAppBarModel<T extends ViewPagerAppBarView> extends AppBarModel<T> {
    private final List<AppBarModel<? extends AppBarView>> appBarModels;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Builder {
        public Builder(Context context) {
            EmptyList emptyList = EmptyList.INSTANCE;
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    public ViewPagerAppBarModel(KClass kClass, Context context, List<? extends AppBarModel<? extends AppBarView>> list) {
        super(kClass, context);
        this.appBarModels = list;
    }

    public T init(T t) {
        return t;
    }

    public ViewPagerAppBarModel(KClass kClass, Context context, List list, int i, DefaultConstructorMarker defaultConstructorMarker) {
        this(kClass, context, (i & 4) != 0 ? EmptyList.INSTANCE : list);
    }
}
