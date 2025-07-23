package androidx.navigation;

import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import kotlin.jvm.internal.DefaultConstructorMarker;

/* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
/* loaded from: classes.dex */
public final class NavControllerViewModel extends ViewModel implements NavViewModelStoreProvider {
    public static final Companion Companion = new Companion(null);
    public static final NavControllerViewModel$Companion$FACTORY$1 FACTORY = new ViewModelProvider.Factory() { // from class: androidx.navigation.NavControllerViewModel$Companion$FACTORY$1
        @Override // androidx.lifecycle.ViewModelProvider.Factory
        public final ViewModel create(Class cls) {
            return new NavControllerViewModel();
        }
    };
    public final Map viewModelStores = new LinkedHashMap();

    /* compiled from: qb/97869455 e70885ee4e20e40425471e4b47759369a50273352e1b7033cea52247075b3cbb */
    public final class Companion {
        public /* synthetic */ Companion(DefaultConstructorMarker defaultConstructorMarker) {
            this();
        }

        private Companion() {
        }
    }

    @Override // androidx.lifecycle.ViewModel
    public final void onCleared() {
        Iterator it = ((LinkedHashMap) this.viewModelStores).values().iterator();
        while (it.hasNext()) {
            ((ViewModelStore) it.next()).clear();
        }
        ((LinkedHashMap) this.viewModelStores).clear();
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder("NavControllerViewModel{");
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append("} ViewModelStores (");
        Iterator it = ((LinkedHashMap) this.viewModelStores).keySet().iterator();
        while (it.hasNext()) {
            sb.append((String) it.next());
            if (it.hasNext()) {
                sb.append(", ");
            }
        }
        sb.append(')');
        return sb.toString();
    }
}
