package com.android.p038wm.shell.draganddrop;

import android.content.Context;
import com.android.systemui.R;
import com.samsung.android.rune.CoreRune;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class ExecutableAppHolder {
    public static final boolean DEBUG = CoreRune.SAFE_DEBUG;
    public final AppResultFactory mAppResultFactory;
    public final PolicyExceptionList mCallingPackageBlockList;
    public String mCallingPackageName;
    public int mCallingUserId;
    public final Context mContext;
    public AppInfo mExecutableApp;
    public boolean mIsMimeType;
    public final MultiInstanceAllowList mMultiInstanceAllowList;
    public final MultiInstanceBlockList mMultiInstanceBlockList;
    public AppResult mResult;
    public final Map mExecutableAppMap = new HashMap();
    public final List mCallbacks = new ArrayList();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MimeTypeBlockList extends PolicyExceptionList {
        public MimeTypeBlockList(Context context) {
            super(context, R.array.drag_and_split_mime_type_block_list, false);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MultiInstanceAllowList extends PolicyExceptionList {
        public MultiInstanceAllowList(Context context) {
            super(context, R.array.drag_and_drop_multi_instance_allow_list, true);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class MultiInstanceBlockList extends PolicyExceptionList {
        public MultiInstanceBlockList(Context context) {
            super(context, R.array.f683x20510d5b, true);
        }
    }

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public class PolicyExceptionList {
        public final Set mBlockList;

        public PolicyExceptionList(Context context, int i, boolean z) {
            if (z) {
                this.mBlockList = (Set) Arrays.stream(context.getResources().getStringArray(i)).map(new C3968x3b842969()).collect(Collectors.toSet());
            } else {
                this.mBlockList = (Set) Arrays.stream(context.getResources().getStringArray(i)).collect(Collectors.toSet());
            }
        }
    }

    public ExecutableAppHolder(Context context) {
        this.mContext = context;
        context.getPackageManager();
        MultiInstanceBlockList multiInstanceBlockList = new MultiInstanceBlockList(context);
        this.mMultiInstanceBlockList = multiInstanceBlockList;
        MultiInstanceAllowList multiInstanceAllowList = new MultiInstanceAllowList(context);
        this.mMultiInstanceAllowList = multiInstanceAllowList;
        this.mCallingPackageBlockList = new PolicyExceptionList(context, R.array.drag_and_split_calling_package_block_list, false);
        this.mAppResultFactory = new AppResultFactory(context, multiInstanceBlockList, multiInstanceAllowList);
    }
}
