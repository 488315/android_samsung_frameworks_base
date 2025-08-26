package com.android.wm.shell.draganddrop;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.ResolveInfo;
import android.graphics.drawable.Icon;
import com.android.wm.shell.draganddrop.ExecutableAppHolder;
import com.samsung.android.multiwindow.MultiWindowManager;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/* loaded from: classes3.dex */
public class SingleIntentAppResult extends BaseAppResult {
    public final Icon mActionIcon;
    public final boolean mAlwaysUseOptions;
    public final Intent mIntent;
    public final List mResolveInfos;

    public SingleIntentAppResult(Intent intent, List<ResolveInfo> list, ExecutableAppHolder.MultiInstanceBlockList multiInstanceBlockList, ExecutableAppHolder.MultiInstanceAllowList multiInstanceAllowList, String str) {
        this(intent, list, multiInstanceBlockList, multiInstanceAllowList, str, false);
    }

    @Override // com.android.wm.shell.draganddrop.AppResult
    public final ActivityInfo getDragAppActivityInfo() {
        if (((ArrayList) this.mResolveInfos).isEmpty()) {
            return null;
        }
        return ((ResolveInfo) ((ArrayList) this.mResolveInfos).get(0)).activityInfo;
    }

    @Override // com.android.wm.shell.draganddrop.AppResult
    public final boolean hasResizableResolveInfo() {
        return this.mResolveInfos.stream().filter(new Predicate() { // from class: com.android.wm.shell.draganddrop.SingleIntentAppResult$$ExternalSyntheticLambda0
            @Override // java.util.function.Predicate
            public final boolean test(Object obj) {
                this.f$0.getClass();
                return (MultiWindowManager.getInstance().getSupportedMultiWindowModes(((ResolveInfo) obj).activityInfo) & 3) != 0;
            }
        }).findFirst().isPresent();
    }

    @Override // com.android.wm.shell.draganddrop.AppResult
    public final boolean hasResolveInfoInFullscreenOnly(VisibleTasks visibleTasks) {
        ArrayList arrayList = new ArrayList(this.mResolveInfos);
        arrayList.removeIf(new SingleIntentAppResult$$ExternalSyntheticLambda1(this, visibleTasks.getFullscreenTasks(), 0));
        return arrayList.isEmpty();
    }

    @Override // com.android.wm.shell.draganddrop.AppResult
    public final boolean isAlreadyRunningSingleInstanceTask(VisibleTasks visibleTasks) {
        ArrayList arrayList = new ArrayList(this.mResolveInfos);
        visibleTasks.getClass();
        List visibleTasks2 = MultiWindowManager.getInstance().getVisibleTasks();
        visibleTasks2.removeIf(new VisibleTasks$$ExternalSyntheticLambda0(visibleTasks));
        arrayList.removeIf(new SingleIntentAppResult$$ExternalSyntheticLambda1(this, visibleTasks2, 1));
        return arrayList.isEmpty();
    }

    @Override // com.android.wm.shell.draganddrop.AppResult
    public final AppInfo makeExecutableApp(Context context, int i, VisibleTasks visibleTasks) {
        ArrayList arrayList = new ArrayList(this.mResolveInfos);
        arrayList.removeIf(new SingleIntentAppResult$$ExternalSyntheticLambda1(this, visibleTasks.getTasksException(i), 2));
        if (arrayList.isEmpty()) {
            return null;
        }
        if (arrayList.size() > 1) {
            Intent intent = this.mIntent;
            Intent intent2 = new Intent(context, (Class<?>) DropResolverActivity.class);
            intent2.addFlags(402653184);
            intent2.putExtra("android.intent.extra.INTENT", intent);
            intent2.putParcelableArrayListExtra("dropResolverActivity.extra.rlist", new ArrayList<>(arrayList));
            intent2.putExtra("dropResolverActivity.extra.supportsAlwaysUseOption", this.mAlwaysUseOptions);
            return new AppInfo(intent2, null, true);
        }
        this.mIntent.addFlags(402653184);
        Intent intent3 = this.mIntent;
        ResolveInfo resolveInfo = (ResolveInfo) arrayList.get(0);
        Intent intent4 = new Intent(intent3);
        intent4.setComponent(resolveInfo.activityInfo.getComponentName());
        Icon icon = this.mActionIcon;
        return new AppInfo(intent4, icon != null ? icon.loadDrawable(context) : resolveInfo.activityInfo.loadIcon(context.getPackageManager()), false);
    }

    public SingleIntentAppResult(Intent intent, List<ResolveInfo> list, ExecutableAppHolder.MultiInstanceBlockList multiInstanceBlockList, ExecutableAppHolder.MultiInstanceAllowList multiInstanceAllowList, String str, boolean z) {
        this(intent, list, multiInstanceBlockList, multiInstanceAllowList, str, z, null);
    }

    public SingleIntentAppResult(Intent intent, List<ResolveInfo> list, ExecutableAppHolder.MultiInstanceBlockList multiInstanceBlockList, ExecutableAppHolder.MultiInstanceAllowList multiInstanceAllowList, String str, boolean z, Icon icon) {
        super(multiInstanceBlockList, multiInstanceAllowList, str);
        ArrayList arrayList = new ArrayList();
        this.mResolveInfos = arrayList;
        this.mIntent = intent;
        arrayList.addAll(list);
        this.mAlwaysUseOptions = z;
        this.mActionIcon = icon;
    }
}
