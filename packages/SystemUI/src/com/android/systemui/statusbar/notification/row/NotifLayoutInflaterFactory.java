package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import kotlin.collections.builders.MapBuilder;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* loaded from: classes3.dex */
public final class NotifLayoutInflaterFactory implements LayoutInflater.Factory2 {
    public final int layoutType;
    public final NotifRemoteViewsFactoryContainer notifRemoteViewsFactoryContainer;
    public final ExpandableNotificationRow row;

    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    public interface Provider {
        NotifLayoutInflaterFactory provide(ExpandableNotificationRow expandableNotificationRow, int i);
    }

    static {
        new Companion(null);
    }

    public NotifLayoutInflaterFactory(ExpandableNotificationRow expandableNotificationRow, int i, NotifRemoteViewsFactoryContainer notifRemoteViewsFactoryContainer) {
        this.row = expandableNotificationRow;
        this.layoutType = i;
        this.notifRemoteViewsFactoryContainer = notifRemoteViewsFactoryContainer;
    }

    @Override // android.view.LayoutInflater.Factory2
    public final View onCreateView(View view, String str, Context context, AttributeSet attributeSet) {
        Object it = ((NotifRemoteViewsFactoryContainerImpl) this.notifRemoteViewsFactoryContainer).factories.iterator();
        View view2 = null;
        NotifRemoteViewsFactory notifRemoteViewsFactory = null;
        while (((MapBuilder.Itr) it).hasNext()) {
            NotifRemoteViewsFactory notifRemoteViewsFactory2 = (NotifRemoteViewsFactory) ((MapBuilder.KeysItr) it).next();
            String str2 = str;
            Context context2 = context;
            AttributeSet attributeSet2 = attributeSet;
            View viewInstantiate = notifRemoteViewsFactory2.instantiate(this.row, this.layoutType, str2, context2, attributeSet2);
            if (viewInstantiate != null) {
                if (notifRemoteViewsFactory != null) {
                    throw new IllegalStateException((notifRemoteViewsFactory2 + " tries to produce name:" + str2 + " with type:" + this.layoutType + ". However, " + notifRemoteViewsFactory + " produced view for " + str2 + " before.").toString());
                }
                view2 = viewInstantiate;
                notifRemoteViewsFactory = notifRemoteViewsFactory2;
            }
            str = str2;
            context = context2;
            attributeSet = attributeSet2;
        }
        return view2;
    }

    @Override // android.view.LayoutInflater.Factory
    public final View onCreateView(String str, Context context, AttributeSet attributeSet) {
        return onCreateView(null, str, context, attributeSet);
    }
}
