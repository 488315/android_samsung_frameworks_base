package com.android.systemui.user.ui.binder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.constraintlayout.helper.widget.Flow;
import com.android.systemui.Gefingerpoken;
import com.android.systemui.R;
import com.android.systemui.classifier.FalsingCollector;
import com.android.systemui.classifier.FalsingCollectorImpl;
import com.android.systemui.lifecycle.RepeatWhenAttachedKt;
import com.android.systemui.user.UserSwitcherRootView;
import com.android.systemui.user.ui.viewmodel.UserActionViewModel;
import com.android.systemui.user.ui.viewmodel.UserSwitcherViewModel;
import java.util.List;
import kotlin.collections.CollectionsKt__CollectionsKt;
import kotlin.collections.EmptyList;
import kotlin.coroutines.EmptyCoroutineContext;
import kotlin.jvm.functions.Function0;
import kotlin.jvm.internal.Ref$ObjectRef;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class UserSwitcherViewBinder {
    public static final UserSwitcherViewBinder INSTANCE = new UserSwitcherViewBinder();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MenuAdapter extends BaseAdapter {
        public final LayoutInflater layoutInflater;
        public List sections = EmptyList.INSTANCE;

        public MenuAdapter(LayoutInflater layoutInflater) {
            this.layoutInflater = layoutInflater;
        }

        @Override // android.widget.Adapter
        public final int getCount() {
            return this.sections.size();
        }

        @Override // android.widget.Adapter
        public final Object getItem(int i) {
            return (List) this.sections.get(i);
        }

        @Override // android.widget.Adapter
        public final long getItemId(int i) {
            return i;
        }

        @Override // android.widget.Adapter
        public final View getView(int i, View view, ViewGroup viewGroup) {
            List list = (List) this.sections.get(i);
            Context context = viewGroup.getContext();
            LinearLayout linearLayout = view instanceof LinearLayout ? (LinearLayout) view : null;
            if (linearLayout == null) {
                linearLayout = new LinearLayout(context, null);
                linearLayout.setOrientation(1);
                linearLayout.setBackground(viewGroup.getResources().getDrawable(R.drawable.bouncer_user_switcher_popup_bg, context.getTheme()));
                linearLayout.setShowDividers(2);
                linearLayout.setDividerDrawable(context.getDrawable(R.drawable.fullscreen_userswitcher_menu_item_divider));
            }
            linearLayout.removeAllViewsInLayout();
            int i2 = 0;
            for (Object obj : list) {
                int i3 = i2 + 1;
                if (i2 < 0) {
                    CollectionsKt__CollectionsKt.throwIndexOverflow();
                    throw null;
                }
                final UserActionViewModel userActionViewModel = (UserActionViewModel) obj;
                final View inflate = this.layoutInflater.inflate(R.layout.user_switcher_fullscreen_popup_item, (ViewGroup) null);
                ((ImageView) inflate.requireViewById(R.id.icon)).setImageResource(userActionViewModel.iconResourceId);
                ((TextView) inflate.requireViewById(R.id.text)).setText(inflate.getResources().getString(userActionViewModel.textResourceId));
                inflate.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.user.ui.binder.UserSwitcherViewBinder$MenuAdapter$getView$1$1
                    @Override // android.view.View.OnClickListener
                    public final void onClick(View view2) {
                        UserActionViewModel.this.onClicked.invoke();
                    }
                });
                linearLayout.addView(inflate);
                if (i2 == 0 && i == 0) {
                    inflate.postDelayed(new Runnable() { // from class: com.android.systemui.user.ui.binder.UserSwitcherViewBinder$MenuAdapter$getView$1$2
                        @Override // java.lang.Runnable
                        public final void run() {
                            inflate.requestAccessibilityFocus();
                        }
                    }, 200L);
                }
                i2 = i3;
            }
            return linearLayout;
        }
    }

    private UserSwitcherViewBinder() {
    }

    public static void bind(ViewGroup viewGroup, final UserSwitcherViewModel userSwitcherViewModel, LayoutInflater layoutInflater, final FalsingCollector falsingCollector, Function0 function0) {
        UserSwitcherRootView userSwitcherRootView = (UserSwitcherRootView) viewGroup.requireViewById(R.id.user_switcher_grid_container);
        Flow flow = (Flow) userSwitcherRootView.requireViewById(R.id.flow);
        View requireViewById = viewGroup.requireViewById(R.id.add);
        View requireViewById2 = viewGroup.requireViewById(R.id.cancel);
        MenuAdapter menuAdapter = new MenuAdapter(layoutInflater);
        Ref$ObjectRef ref$ObjectRef = new Ref$ObjectRef();
        userSwitcherRootView.touchHandler = new Gefingerpoken() { // from class: com.android.systemui.user.ui.binder.UserSwitcherViewBinder$bind$1
            @Override // com.android.systemui.Gefingerpoken
            public final boolean onTouchEvent(MotionEvent motionEvent) {
                ((FalsingCollectorImpl) FalsingCollector.this).onTouchEvent(motionEvent);
                return false;
            }
        };
        requireViewById.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.user.ui.binder.UserSwitcherViewBinder$bind$2
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                UserSwitcherViewModel.this._isMenuVisible.setValue(Boolean.TRUE);
            }
        });
        requireViewById2.setOnClickListener(new View.OnClickListener() { // from class: com.android.systemui.user.ui.binder.UserSwitcherViewBinder$bind$3
            @Override // android.view.View.OnClickListener
            public final void onClick(View view) {
                UserSwitcherViewModel.this.hasCancelButtonBeenClicked.setValue(Boolean.TRUE);
            }
        });
        RepeatWhenAttachedKt.repeatWhenAttached(viewGroup, EmptyCoroutineContext.INSTANCE, new UserSwitcherViewBinder$bind$4(userSwitcherViewModel, ref$ObjectRef, function0, requireViewById, viewGroup, menuAdapter, flow, userSwitcherRootView, layoutInflater, null));
    }
}
