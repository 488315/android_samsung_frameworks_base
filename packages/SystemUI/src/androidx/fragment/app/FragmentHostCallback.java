package androidx.fragment.app;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import java.io.PrintWriter;

/* loaded from: classes.dex */
public abstract class FragmentHostCallback extends FragmentContainer {
    public final Activity activity;
    public final Context context;
    public final FragmentManagerImpl fragmentManager;
    public final Handler handler;

    public FragmentHostCallback(Activity activity, Context context, Handler handler, int i) {
        this.activity = activity;
        this.context = context;
        this.handler = handler;
        this.fragmentManager = new FragmentManagerImpl();
    }

    @Override // androidx.fragment.app.FragmentContainer
    public View onFindViewById(int i) {
        return null;
    }

    public abstract FragmentActivity onGetHost$1();

    public LayoutInflater onGetLayoutInflater() {
        return LayoutInflater.from(this.context);
    }

    @Override // androidx.fragment.app.FragmentContainer
    public boolean onHasView() {
        return true;
    }

    public FragmentHostCallback(Context context, Handler handler, int i) {
        this(context instanceof Activity ? (Activity) context : null, context, handler, i);
    }

    public FragmentHostCallback(FragmentActivity fragmentActivity) {
        this(fragmentActivity, fragmentActivity, new Handler(), 0);
    }

    public void onSupportInvalidateOptionsMenu() {
    }

    public void onDump(PrintWriter printWriter, String[] strArr) {
    }
}
