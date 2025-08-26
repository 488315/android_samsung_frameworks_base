package com.android.wm.shell.draganddrop;

import android.content.Context;
import com.android.systemui.R;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/* loaded from: classes3.dex */
public class ExecutableAppHolder {
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

    public class MimeTypeBlockList extends PolicyExceptionList {
        public MimeTypeBlockList(Context context) {
            super(context, R.array.drag_and_split_mime_type_block_list, false);
        }
    }

    public class MultiInstanceAllowList extends PolicyExceptionList {
        public MultiInstanceAllowList(Context context) {
            super(context, R.array.drag_and_drop_multi_instance_allow_list, true);
        }
    }

    public class MultiInstanceBlockList extends PolicyExceptionList {
        public MultiInstanceBlockList(Context context) {
            super(context, R.array.drag_and_split_multi_instance_block_list_until_support_aosp_multi_instance, true);
        }
    }

    public class PolicyExceptionList {
        public final Set mBlockList;

        public PolicyExceptionList(Context context, int i, boolean z) {
            if (z) {
                this.mBlockList = (Set) Arrays.stream(context.getResources().getStringArray(i)).map(new ExecutableAppHolder$PolicyExceptionList$$ExternalSyntheticLambda0()).collect(Collectors.toSet());
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
