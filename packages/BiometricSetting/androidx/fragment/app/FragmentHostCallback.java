package androidx.fragment.app;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.view.LayoutInflater;
import androidx.core.util.Preconditions;
import java.io.PrintWriter;

/* loaded from: classes.dex */
public abstract class FragmentHostCallback<E> extends FragmentContainer {
    private final Activity mActivity;
    private final Context mContext;
    final FragmentManager mFragmentManager;
    private final Handler mHandler;

    FragmentHostCallback(FragmentActivity fragmentActivity) {
        Handler handler = new Handler();
        this.mFragmentManager = new FragmentManagerImpl();
        this.mActivity = fragmentActivity;
        Preconditions.checkNotNull(fragmentActivity, "context == null");
        this.mContext = fragmentActivity;
        this.mHandler = handler;
    }

    final Activity getActivity() {
        return this.mActivity;
    }

    final Context getContext() {
        return this.mContext;
    }

    public final Handler getHandler() {
        return this.mHandler;
    }

    public abstract void onDump(PrintWriter printWriter, String[] strArr);

    public abstract FragmentActivity onGetHost$1();

    public abstract LayoutInflater onGetLayoutInflater$1();

    public abstract void onSupportInvalidateOptionsMenu();
}
