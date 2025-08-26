package com.google.android.material.appbar.model;

import android.content.Context;
import android.content.res.Resources;
import com.google.android.material.appbar.model.AppBarModel;
import com.google.android.material.appbar.model.view.SuggestAppBarView;
import kotlin.collections.EmptyList;
import kotlin.reflect.KClass;

/* loaded from: classes4.dex */
public class SuggestAppBarModel<T extends SuggestAppBarView> extends AppBarModel<T> {
    private final ButtonListModel buttonListModel;
    private final AppBarModel.OnClickListener closeClickListener;
    private final String title;

    public final class Builder {
        public Builder(Context context) {
            EmptyList emptyList = EmptyList.INSTANCE;
        }
    }

    public SuggestAppBarModel(KClass kClass, Context context, String str, AppBarModel.OnClickListener onClickListener, ButtonListModel buttonListModel) {
        super(kClass, context);
        this.title = str;
        this.closeClickListener = onClickListener;
        this.buttonListModel = buttonListModel;
    }

    public final ButtonListModel getButtonListModel() {
        return this.buttonListModel;
    }

    public final AppBarModel.OnClickListener getCloseClickListener() {
        return this.closeClickListener;
    }

    public final String getTitle() {
        return this.title;
    }

    @Override // 
    public T init(T t) throws Resources.NotFoundException {
        t.setModel(this);
        t.setTitle(this.title);
        t.setCloseClickListener(this.closeClickListener);
        t.setButtonModules(this.buttonListModel);
        t.updateResource(t.getContext());
        return t;
    }
}
