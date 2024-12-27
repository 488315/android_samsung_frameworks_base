package com.android.server.pm;

import android.app.appsearch.AppSearchSession;
import android.app.appsearch.GetByDocumentIdRequest;
import android.content.ComponentName;
import android.content.pm.ShortcutInfo;
import android.content.res.Resources;
import android.hardware.soundtrigger.V2_3.OptionalModelParameterRange$$ExternalSyntheticOutline0;
import android.util.Log;
import android.util.Slog;
import com.android.internal.util.jobs.DumpUtils$$ExternalSyntheticOutline0;
import com.android.server.pm.ShortcutPackage.AnonymousClass1;
import java.io.File;
import java.io.PrintWriter;
import java.util.List;
import java.util.Set;
import java.util.function.Consumer;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes2.dex */
public final /* synthetic */ class ShortcutPackage$$ExternalSyntheticLambda2 implements Consumer {
    public final /* synthetic */ int $r8$classId;
    public final /* synthetic */ Object f$0;
    public final /* synthetic */ Object f$1;
    public final /* synthetic */ Object f$2;

    public /* synthetic */ ShortcutPackage$$ExternalSyntheticLambda2(Resources resources, ShortcutService shortcutService, List list) {
        this.$r8$classId = 2;
        this.f$2 = resources;
        this.f$1 = shortcutService;
        this.f$0 = list;
    }

    public /* synthetic */ ShortcutPackage$$ExternalSyntheticLambda2(ShortcutPackage shortcutPackage, boolean[] zArr, ShortcutService shortcutService) {
        this.$r8$classId = 3;
        this.f$0 = shortcutPackage;
        this.f$2 = zArr;
        this.f$1 = shortcutService;
    }

    public /* synthetic */ ShortcutPackage$$ExternalSyntheticLambda2(Object obj, Object obj2, Object obj3, int i) {
        this.$r8$classId = i;
        this.f$0 = obj;
        this.f$1 = obj2;
        this.f$2 = obj3;
    }

    @Override // java.util.function.Consumer
    public final void accept(Object obj) {
        switch (this.$r8$classId) {
            case 0:
                ShortcutPackage shortcutPackage = (ShortcutPackage) this.f$0;
                ShortcutService shortcutService = (ShortcutService) this.f$1;
                Resources resources = (Resources) this.f$2;
                ShortcutInfo shortcutInfo = (ShortcutInfo) obj;
                shortcutPackage.getClass();
                if (shortcutInfo.isDynamic()) {
                    if (shortcutInfo.getActivity() == null) {
                        shortcutService.wtf("null activity detected.", null);
                    } else {
                        if (!shortcutService.injectIsMainActivity(shortcutPackage.mPackageUserId, shortcutInfo.getActivity())) {
                            Slog.w("ShortcutService", OptionalModelParameterRange$$ExternalSyntheticOutline0.m(new StringBuilder(), shortcutPackage.mPackageName, " is no longer main activity. Disabling shorcut ", shortcutInfo.getId(), "."));
                            if (shortcutPackage.deleteOrDisableWithId(shortcutInfo.getId(), true, false, false, 2, false) != null) {
                            }
                        }
                    }
                }
                if (shortcutInfo.hasAnyResources() && resources != null) {
                    if (!shortcutInfo.isOriginallyFromManifest()) {
                        shortcutInfo.lookupAndFillInResourceIds(resources);
                    }
                    shortcutInfo.setTimestamp(shortcutService.injectCurrentTimeMillis());
                    break;
                }
                break;
            case 1:
                PrintWriter printWriter = (PrintWriter) this.f$0;
                String str = (String) this.f$1;
                long[] jArr = (long[]) this.f$2;
                ShortcutInfo shortcutInfo2 = (ShortcutInfo) obj;
                printWriter.println(shortcutInfo2.toDumpString(str + "    "));
                if (shortcutInfo2.getBitmapPath() != null) {
                    long length = new File(shortcutInfo2.getBitmapPath()).length();
                    printWriter.print(str);
                    printWriter.print("      ");
                    printWriter.print("bitmap size=");
                    printWriter.println(length);
                    jArr[0] = jArr[0] + length;
                    break;
                }
                break;
            case 2:
                Resources resources2 = (Resources) this.f$2;
                ShortcutService shortcutService2 = (ShortcutService) this.f$1;
                List list = (List) this.f$0;
                ShortcutInfo shortcutInfo3 = (ShortcutInfo) obj;
                if (shortcutInfo3.hasStringResources()) {
                    shortcutInfo3.resolveResourceStrings(resources2);
                    shortcutInfo3.setTimestamp(shortcutService2.injectCurrentTimeMillis());
                    list.add(shortcutInfo3);
                    break;
                }
                break;
            case 3:
                ShortcutPackage shortcutPackage2 = (ShortcutPackage) this.f$0;
                boolean[] zArr = (boolean[]) this.f$2;
                ShortcutService shortcutService3 = (ShortcutService) this.f$1;
                ShortcutInfo shortcutInfo4 = (ShortcutInfo) obj;
                shortcutPackage2.getClass();
                boolean isDeclaredInManifest = shortcutInfo4.isDeclaredInManifest();
                String str2 = shortcutPackage2.mPackageName;
                if (!isDeclaredInManifest && !shortcutInfo4.isDynamic() && !shortcutInfo4.isPinned() && !shortcutInfo4.isCached()) {
                    zArr[0] = true;
                    StringBuilder m = DumpUtils$$ExternalSyntheticOutline0.m("Package ", str2, ": shortcut ");
                    m.append(shortcutInfo4.getId());
                    m.append(" is not manifest, dynamic or pinned.");
                    Log.e("ShortcutService.verify", m.toString());
                }
                if (shortcutInfo4.isDeclaredInManifest() && shortcutInfo4.isDynamic()) {
                    zArr[0] = true;
                    StringBuilder m2 = DumpUtils$$ExternalSyntheticOutline0.m("Package ", str2, ": shortcut ");
                    m2.append(shortcutInfo4.getId());
                    m2.append(" is both dynamic and manifest at the same time.");
                    Log.e("ShortcutService.verify", m2.toString());
                }
                if (shortcutInfo4.getActivity() == null && !shortcutInfo4.isFloating()) {
                    zArr[0] = true;
                    StringBuilder m3 = DumpUtils$$ExternalSyntheticOutline0.m("Package ", str2, ": shortcut ");
                    m3.append(shortcutInfo4.getId());
                    m3.append(" has null activity, but not floating.");
                    Log.e("ShortcutService.verify", m3.toString());
                }
                if ((shortcutInfo4.isDynamic() || shortcutInfo4.isManifestShortcut()) && !shortcutInfo4.isEnabled()) {
                    zArr[0] = true;
                    StringBuilder m4 = DumpUtils$$ExternalSyntheticOutline0.m("Package ", str2, ": shortcut ");
                    m4.append(shortcutInfo4.getId());
                    m4.append(" is not floating, but is disabled.");
                    Log.e("ShortcutService.verify", m4.toString());
                }
                if (shortcutInfo4.isFloating() && shortcutInfo4.getRank() != 0) {
                    zArr[0] = true;
                    StringBuilder m5 = DumpUtils$$ExternalSyntheticOutline0.m("Package ", str2, ": shortcut ");
                    m5.append(shortcutInfo4.getId());
                    m5.append(" is floating, but has rank=");
                    m5.append(shortcutInfo4.getRank());
                    Log.e("ShortcutService.verify", m5.toString());
                }
                if (shortcutInfo4.getIcon() != null) {
                    zArr[0] = true;
                    StringBuilder m6 = DumpUtils$$ExternalSyntheticOutline0.m("Package ", str2, ": shortcut ");
                    m6.append(shortcutInfo4.getId());
                    m6.append(" still has an icon");
                    Log.e("ShortcutService.verify", m6.toString());
                }
                if (shortcutInfo4.hasAdaptiveBitmap() && !shortcutInfo4.hasIconFile() && !shortcutInfo4.hasIconUri()) {
                    zArr[0] = true;
                    StringBuilder m7 = DumpUtils$$ExternalSyntheticOutline0.m("Package ", str2, ": shortcut ");
                    m7.append(shortcutInfo4.getId());
                    m7.append(" has adaptive bitmap but was not saved to a file nor has icon uri.");
                    Log.e("ShortcutService.verify", m7.toString());
                }
                if (shortcutInfo4.hasIconFile() && shortcutInfo4.hasIconResource()) {
                    zArr[0] = true;
                    StringBuilder m8 = DumpUtils$$ExternalSyntheticOutline0.m("Package ", str2, ": shortcut ");
                    m8.append(shortcutInfo4.getId());
                    m8.append(" has both resource and bitmap icons");
                    Log.e("ShortcutService.verify", m8.toString());
                }
                if (shortcutInfo4.hasIconFile() && shortcutInfo4.hasIconUri()) {
                    zArr[0] = true;
                    StringBuilder m9 = DumpUtils$$ExternalSyntheticOutline0.m("Package ", str2, ": shortcut ");
                    m9.append(shortcutInfo4.getId());
                    m9.append(" has both url and bitmap icons");
                    Log.e("ShortcutService.verify", m9.toString());
                }
                if (shortcutInfo4.hasIconUri() && shortcutInfo4.hasIconResource()) {
                    zArr[0] = true;
                    StringBuilder m10 = DumpUtils$$ExternalSyntheticOutline0.m("Package ", str2, ": shortcut ");
                    m10.append(shortcutInfo4.getId());
                    m10.append(" has both url and resource icons");
                    Log.e("ShortcutService.verify", m10.toString());
                }
                if (shortcutInfo4.isEnabled() != (shortcutInfo4.getDisabledReason() == 0)) {
                    zArr[0] = true;
                    StringBuilder m11 = DumpUtils$$ExternalSyntheticOutline0.m("Package ", str2, ": shortcut ");
                    m11.append(shortcutInfo4.getId());
                    m11.append(" isEnabled() and getDisabledReason() disagree: ");
                    m11.append(shortcutInfo4.isEnabled());
                    m11.append(" vs ");
                    m11.append(shortcutInfo4.getDisabledReason());
                    Log.e("ShortcutService.verify", m11.toString());
                }
                if (shortcutInfo4.getDisabledReason() == 100 && shortcutPackage2.mPackageInfo.mBackupSourceVersionCode == -1) {
                    zArr[0] = true;
                    StringBuilder m12 = DumpUtils$$ExternalSyntheticOutline0.m("Package ", str2, ": shortcut ");
                    m12.append(shortcutInfo4.getId());
                    m12.append(" RESTORED_VERSION_LOWER with no backup source version code.");
                    Log.e("ShortcutService.verify", m12.toString());
                }
                ComponentName activity = shortcutInfo4.getActivity();
                shortcutService3.getClass();
                if (activity != null && "android.__dummy__".equals(activity.getClassName())) {
                    zArr[0] = true;
                    StringBuilder m13 = DumpUtils$$ExternalSyntheticOutline0.m("Package ", str2, ": shortcut ");
                    m13.append(shortcutInfo4.getId());
                    m13.append(" has a dummy target activity");
                    Log.e("ShortcutService.verify", m13.toString());
                    break;
                }
                break;
            default:
                ShortcutPackage shortcutPackage3 = (ShortcutPackage) this.f$0;
                Set set = (Set) this.f$1;
                Consumer consumer = (Consumer) this.f$2;
                shortcutPackage3.getClass();
                ((AppSearchSession) obj).getByDocumentId(new GetByDocumentIdRequest.Builder(shortcutPackage3.mPackageName).addIds(set).build(), shortcutPackage3.mShortcutUser.mExecutor, shortcutPackage3.new AnonymousClass1(consumer));
                break;
        }
    }
}
