package androidx.navigation;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import androidx.navigation.Navigator;
import defpackage.ReorderTile$$ExternalSyntheticOutline0;
import java.util.Iterator;
import kotlin.jvm.functions.Function1;
import kotlin.jvm.internal.DefaultConstructorMarker;
import kotlin.sequences.SequencesKt__SequencesKt;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
@Navigator.Name("activity")
/* loaded from: classes.dex */
public class ActivityNavigator extends Navigator {
    public final Activity hostActivity;

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public class Destination extends NavDestination {
        public Destination(Navigator navigator) {
            super(navigator);
        }

        @Override // androidx.navigation.NavDestination
        public final boolean equals(Object obj) {
            if (this == obj) {
                return true;
            }
            if (obj == null || !(obj instanceof Destination) || !super.equals(obj)) {
                return false;
            }
            return true;
        }

        @Override // androidx.navigation.NavDestination
        public final int hashCode() {
            return super.hashCode() * 961;
        }

        @Override // androidx.navigation.NavDestination
        public final String toString() {
            return super.toString();
        }

        public Destination(NavigatorProvider navigatorProvider) {
            this(navigatorProvider.getNavigator(ActivityNavigator.class));
        }
    }

    static {
        new Companion(null);
    }

    public ActivityNavigator(Context context) {
        Object obj;
        Iterator it = SequencesKt__SequencesKt.generateSequence(context, new Function1() { // from class: androidx.navigation.ActivityNavigator$hostActivity$1
            @Override // kotlin.jvm.functions.Function1
            /* renamed from: invoke */
            public final Object mo779invoke(Object obj2) {
                Context context2 = (Context) obj2;
                if (context2 instanceof ContextWrapper) {
                    return ((ContextWrapper) context2).getBaseContext();
                }
                return null;
            }
        }).iterator();
        while (true) {
            if (!it.hasNext()) {
                obj = null;
                break;
            } else {
                obj = it.next();
                if (((Context) obj) instanceof Activity) {
                    break;
                }
            }
        }
        this.hostActivity = (Activity) obj;
    }

    @Override // androidx.navigation.Navigator
    public final NavDestination createDestination() {
        return new Destination(this);
    }

    @Override // androidx.navigation.Navigator
    public final NavDestination navigate(NavDestination navDestination) {
        throw new IllegalStateException(ReorderTile$$ExternalSyntheticOutline0.m(((Destination) navDestination).id, " does not have an Intent set.", new StringBuilder("Destination ")).toString());
    }

    @Override // androidx.navigation.Navigator
    public final boolean popBackStack() {
        Activity activity = this.hostActivity;
        if (activity == null) {
            return false;
        }
        activity.finish();
        return true;
    }
}
