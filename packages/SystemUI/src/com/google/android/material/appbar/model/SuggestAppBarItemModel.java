package com.google.android.material.appbar.model;

import android.content.Context;
import android.content.res.Resources;
import com.google.android.material.appbar.model.AppBarModel;
import com.google.android.material.appbar.model.view.SuggestAppBarItemView;
import kotlin.collections.EmptyList;
import kotlin.reflect.KClass;

/* loaded from: classes4.dex */
public class SuggestAppBarItemModel<T extends SuggestAppBarItemView> extends SuggestAppBarModel<T> {

    public final class Builder {
        public Builder(Context context) {
            EmptyList emptyList = EmptyList.INSTANCE;
        }
    }

    public SuggestAppBarItemModel(KClass kClass, Context context, String str, AppBarModel.OnClickListener onClickListener, ButtonListModel buttonListModel) {
        super(kClass, context, str, onClickListener, buttonListModel);
    }

    @Override // com.google.android.material.appbar.model.SuggestAppBarModel
    public T init(T t) throws Resources.NotFoundException {
        t.setModel(this);
        t.setTitle(getTitle());
        t.setCloseClickListener(getCloseClickListener());
        t.setButtonModules(getButtonListModel());
        t.updateResource(t.getContext());
        return t;
    }
}
