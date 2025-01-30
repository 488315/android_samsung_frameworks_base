package androidx.fragment.app;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import androidx.core.util.Preconditions;
import java.io.PrintWriter;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes.dex */
public abstract class FragmentHostCallback extends FragmentContainer {
    public final Activity mActivity;
    public final Context mContext;
    public final FragmentManagerImpl mFragmentManager;
    public final Handler mHandler;

    public FragmentHostCallback(Context context, Handler handler, int i) {
        this(context instanceof Activity ? (Activity) context : null, context, handler, i);
    }

    @Override // androidx.fragment.app.FragmentContainer
    public View onFindViewById(int i) {
        return null;
    }

    public abstract FragmentActivity onGetHost$1();

    public LayoutInflater onGetLayoutInflater() {
        return LayoutInflater.from(this.mContext);
    }

    @Override // androidx.fragment.app.FragmentContainer
    public boolean onHasView() {
        return true;
    }

    public FragmentHostCallback(FragmentActivity fragmentActivity) {
        this(fragmentActivity, fragmentActivity, new Handler(), 0);
    }

    public FragmentHostCallback(Activity activity, Context context, Handler handler, int i) {
        this.mFragmentManager = new FragmentManagerImpl();
        this.mActivity = activity;
        Preconditions.checkNotNull(context, "context == null");
        this.mContext = context;
        Preconditions.checkNotNull(handler, "handler == null");
        this.mHandler = handler;
    }

    public void onSupportInvalidateOptionsMenu() {
    }

    public void onDump(PrintWriter printWriter, String[] strArr) {
    }
}
