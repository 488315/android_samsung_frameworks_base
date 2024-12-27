package com.android.systemui.statusbar.notification.row;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import kotlin.collections.builders.MapBuilder;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
/* loaded from: classes2.dex */
public final class NotifLayoutInflaterFactory implements LayoutInflater.Factory2 {
    public final int layoutType;
    public final NotifRemoteViewsFactoryContainer notifRemoteViewsFactoryContainer;
    public final ExpandableNotificationRow row;

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
    public final class Companion {
        private Companion() {
        }

        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }
    }

    /* compiled from: qb/89523975 427a50d40ec74a85ca352b86f77450b1c52ece5389e11158752b0d641a3a5098 */
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
            View instantiate = notifRemoteViewsFactory2.instantiate(this.row, this.layoutType, str, context, attributeSet);
            if (instantiate != null) {
                if (notifRemoteViewsFactory != null) {
                    throw new IllegalStateException((notifRemoteViewsFactory2 + " tries to produce name:" + str + " with type:" + this.layoutType + ". However, " + notifRemoteViewsFactory + " produced view for " + str + " before.").toString());
                }
                notifRemoteViewsFactory = notifRemoteViewsFactory2;
                view2 = instantiate;
            }
        }
        return view2;
    }

    @Override // android.view.LayoutInflater.Factory
    public final View onCreateView(String str, Context context, AttributeSet attributeSet) {
        return onCreateView(null, str, context, attributeSet);
    }
}
