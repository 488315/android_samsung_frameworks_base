package com.android.server.am.mars.filter;

import android.content.Context;
import android.net.Uri;

import com.android.server.am.mars.MARsUtils;
import com.android.server.am.mars.database.MARsVersionManager;
import com.android.server.am.mars.filter.filter.AODClockFilter;
import com.android.server.am.mars.filter.filter.AccessibilityAppFilter;
import com.android.server.am.mars.filter.filter.ActiveMusicRecordFilter;
import com.android.server.am.mars.filter.filter.ActiveSensorFilter;
import com.android.server.am.mars.filter.filter.ActiveTrafficFilter;
import com.android.server.am.mars.filter.filter.AllowListFilter;
import com.android.server.am.mars.filter.filter.AppCastFilter;
import com.android.server.am.mars.filter.filter.BackupServiceFilter;
import com.android.server.am.mars.filter.filter.BlueToothConnectedFilter;
import com.android.server.am.mars.filter.filter.CameraInFgsFilter;
import com.android.server.am.mars.filter.filter.CarConnectedFilter;
import com.android.server.am.mars.filter.filter.DefaultAppFilter;
import com.android.server.am.mars.filter.filter.DeviceAdminPackageFilter;
import com.android.server.am.mars.filter.filter.DisableForceStopFilter;
import com.android.server.am.mars.filter.filter.EdgeAppFilter;
import com.android.server.am.mars.filter.filter.ImportantRoleFilter;
import com.android.server.am.mars.filter.filter.JobSchedulerPackageFilter;
import com.android.server.am.mars.filter.filter.LatestProtectedPackageFilter;
import com.android.server.am.mars.filter.filter.LockScreenFilter;
import com.android.server.am.mars.filter.filter.NFCPackageFilter;
import com.android.server.am.mars.filter.filter.NoAppIconFilter;
import com.android.server.am.mars.filter.filter.OngoingNotiPackageFilter;
import com.android.server.am.mars.filter.filter.PredownloadFilter;
import com.android.server.am.mars.filter.filter.ProtectedPackagesFilter;
import com.android.server.am.mars.filter.filter.QuickTilePackageFilter;
import com.android.server.am.mars.filter.filter.RecentUsedPackageFilter;
import com.android.server.am.mars.filter.filter.RunningBroadcastFilter;
import com.android.server.am.mars.filter.filter.RunningLocationFilter;
import com.android.server.am.mars.filter.filter.SystemFilter;
import com.android.server.am.mars.filter.filter.TopPackageFilter;
import com.android.server.am.mars.filter.filter.VPNPackageFilter;
import com.android.server.am.mars.filter.filter.WallPaperFilter;
import com.android.server.am.mars.filter.filter.WidgetPkgFilter;

import java.util.HashMap;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
/* loaded from: classes.dex */
public final class FilterManager {
    public final FilterFactory mFF = FilterFactory.FilterFactoryHolder.INSTANCE;
    public final FilterChainFactory mFCF = FilterChainFactory.FilterChainFactoryHolder.INSTANCE;

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public abstract class FilterManagerHolder {
        public static final FilterManager INSTANCE = new FilterManager();
    }

    public static int filterForSpecificPolicy(int i, int i2, int i3, String str) {
        if (MARsUtils.isChinaPolicyEnabled() && i == 1) {
            String[][] strArr = MARsVersionManager.mMARsSettingsInfoDefault;
            if (MARsVersionManager.MARsVersionManagerHolder.INSTANCE.isAdjustRestrictionMatch(
                    10, str, null, null)) {
                return 0;
            }
        }
        FilterChain filterChain =
                (FilterChain)
                        FilterChainFactory.FilterChainFactoryHolder.INSTANCE.filterHashMap.get(
                                Integer.valueOf(i));
        if (filterChain != null) {
            return filterChain.filter(i2, i3, i, str);
        }
        return 0;
    }

    public final void init(Context context) {
        FilterFactory filterFactory = this.mFF;
        filterFactory.mContext = context;
        filterFactory.filterHashMap.put(
                1, RecentUsedPackageFilter.RecentUsedPackageFilterHolder.INSTANCE);
        filterFactory.filterHashMap.put(
                2, LatestProtectedPackageFilter.LatestProtectedPackageFilterHolder.INSTANCE);
        filterFactory.filterHashMap.put(
                3, OngoingNotiPackageFilter.OngoingNotiPackageFilterHolder.INSTANCE);
        filterFactory.filterHashMap.put(4, WidgetPkgFilter.WidgetPkgFilterHolder.INSTANCE);
        filterFactory.filterHashMap.put(5, NoAppIconFilter.NoAppIconFilterHolder.INSTANCE);
        filterFactory.filterHashMap.put(6, VPNPackageFilter.VPNPackageFilterHolder.INSTANCE);
        filterFactory.filterHashMap.put(
                7, ActiveMusicRecordFilter.ActiveMusicRecordFilterHolder.INSTANCE);
        filterFactory.filterHashMap.put(8, ActiveTrafficFilter.ActiveTrafficFilterHolder.INSTANCE);
        filterFactory.filterHashMap.put(
                9, DeviceAdminPackageFilter.DeviceAdminPackageFilterHolder.INSTANCE);
        filterFactory.filterHashMap.put(10, WallPaperFilter.WallPaperFilterHolder.INSTANCE);
        filterFactory.filterHashMap.put(11, DefaultAppFilter.DefaultAppFilterHolder.INSTANCE);
        filterFactory.filterHashMap.put(12, TopPackageFilter.TopPackageFilterHolder.INSTANCE);
        filterFactory.filterHashMap.put(13, LockScreenFilter.LockScreenFilterHolder.INSTANCE);
        filterFactory.filterHashMap.put(14, SystemFilter.SystemFilterHolder.INSTANCE);
        filterFactory.filterHashMap.put(
                15, RunningLocationFilter.RunningLocationFilterHolder.INSTANCE);
        filterFactory.filterHashMap.put(
                16, DisableForceStopFilter.DisableForceStopFilterHolder.INSTANCE);
        filterFactory.filterHashMap.put(17, EdgeAppFilter.EdgeAppFilterHolder.INSTANCE);
        filterFactory.filterHashMap.put(
                18, JobSchedulerPackageFilter.JobSchedulerPackageFilterHolder.INSTANCE);
        filterFactory.filterHashMap.put(
                19, AccessibilityAppFilter.AccessibilityAppFilterHolder.INSTANCE);
        filterFactory.filterHashMap.put(20, AllowListFilter.AllowListFilterHolder.INSTANCE);
        filterFactory.filterHashMap.put(
                21, QuickTilePackageFilter.QuickTilePackageFilterHolder.INSTANCE);
        filterFactory.filterHashMap.put(22, ImportantRoleFilter.ImportantRoleFilterHolder.INSTANCE);
        filterFactory.filterHashMap.put(23, ActiveSensorFilter.ActiveSensorFilterHolder.INSTANCE);
        HashMap hashMap = filterFactory.filterHashMap;
        Uri uri = AppCastFilter.URI_APP_CAST_ENABLED;
        hashMap.put(24, AppCastFilter.AppCastFilterHolder.INSTANCE);
        filterFactory.filterHashMap.put(25, AODClockFilter.AODClockFilterHolder.INSTANCE);
        filterFactory.filterHashMap.put(26, BackupServiceFilter.BackupServiceFilterHolder.INSTANCE);
        filterFactory.filterHashMap.put(
                27, BlueToothConnectedFilter.BlueToothConnectedFilterHolder.INSTANCE);
        filterFactory.filterHashMap.put(28, PredownloadFilter.PredownloadFilterHolder.INSTANCE);
        filterFactory.filterHashMap.put(29, CameraInFgsFilter.CameraInFgsFilterHolder.INSTANCE);
        filterFactory.filterHashMap.put(
                30, ProtectedPackagesFilter.ProtectedPackagesFilterHolder.INSTANCE);
        filterFactory.filterHashMap.put(31, NFCPackageFilter.NFCPackageFilterHolder.INSTANCE);
        filterFactory.filterHashMap.put(32, CarConnectedFilter.AndroidAutoFilterHolder.INSTANCE);
        filterFactory.filterHashMap.put(
                33, RunningBroadcastFilter.RunningBroadcastFilterHolder.INSTANCE);
        for (int i = 1; i < 34; i++) {
            ((IFilter) filterFactory.filterHashMap.get(Integer.valueOf(i)))
                    .init(filterFactory.mContext);
        }
        FilterChainFactory filterChainFactory = this.mFCF;
        HashMap hashMap2 = filterChainFactory.filterHashMap;
        FilterFactory filterFactory2 = FilterFactory.FilterFactoryHolder.INSTANCE;
        IFilter filter = filterFactory2.getFilter(9);
        FilterChain filterChain = new FilterChain();
        filterChain.mFilter = filter;
        filterChain.nextFilterChain = null;
        IFilter filter2 = filterFactory2.getFilter(10);
        FilterChain filterChain2 = new FilterChain();
        filterChain2.mFilter = filter2;
        filterChain2.nextFilterChain = filterChain;
        IFilter filter3 = filterFactory2.getFilter(11);
        FilterChain filterChain3 = new FilterChain();
        filterChain3.mFilter = filter3;
        filterChain3.nextFilterChain = filterChain2;
        IFilter filter4 = filterFactory2.getFilter(12);
        FilterChain filterChain4 = new FilterChain();
        filterChain4.mFilter = filter4;
        filterChain4.nextFilterChain = filterChain3;
        IFilter filter5 = filterFactory2.getFilter(17);
        FilterChain filterChain5 = new FilterChain();
        filterChain5.mFilter = filter5;
        filterChain5.nextFilterChain = filterChain4;
        hashMap2.put(1, filterChain5);
        HashMap hashMap3 = filterChainFactory.filterHashMap;
        FilterChainBuilder filterChainBuilder = new FilterChainBuilder();
        FilterManager$$ExternalSyntheticOutline0.m(filterFactory2, 3, filterChainBuilder, 4, 6);
        FilterManager$$ExternalSyntheticOutline0.m(filterFactory2, 7, filterChainBuilder, 9, 10);
        FilterManager$$ExternalSyntheticOutline0.m(filterFactory2, 11, filterChainBuilder, 14, 16);
        filterChainBuilder.add(filterFactory2.getFilter(17));
        hashMap3.put(2, filterChainBuilder.filterChain);
        HashMap hashMap4 = filterChainFactory.filterHashMap;
        FilterChainBuilder filterChainBuilder2 = new FilterChainBuilder();
        FilterManager$$ExternalSyntheticOutline0.m(filterFactory2, 1, filterChainBuilder2, 4, 5);
        FilterManager$$ExternalSyntheticOutline0.m(filterFactory2, 6, filterChainBuilder2, 7, 8);
        FilterManager$$ExternalSyntheticOutline0.m(filterFactory2, 9, filterChainBuilder2, 10, 11);
        FilterManager$$ExternalSyntheticOutline0.m(filterFactory2, 12, filterChainBuilder2, 13, 14);
        FilterManager$$ExternalSyntheticOutline0.m(filterFactory2, 15, filterChainBuilder2, 16, 17);
        filterChainBuilder2.add(filterFactory2.getFilter(27));
        filterChainBuilder2.add(filterFactory2.getFilter(28));
        hashMap4.put(3, filterChainBuilder2.filterChain);
        HashMap hashMap5 = filterChainFactory.filterHashMap;
        FilterChainBuilder filterChainBuilder3 = new FilterChainBuilder();
        FilterManager$$ExternalSyntheticOutline0.m(filterFactory2, 1, filterChainBuilder3, 6, 7);
        FilterManager$$ExternalSyntheticOutline0.m(filterFactory2, 8, filterChainBuilder3, 9, 11);
        FilterManager$$ExternalSyntheticOutline0.m(filterFactory2, 12, filterChainBuilder3, 14, 17);
        FilterManager$$ExternalSyntheticOutline0.m(filterFactory2, 18, filterChainBuilder3, 26, 27);
        FilterManager$$ExternalSyntheticOutline0.m(filterFactory2, 28, filterChainBuilder3, 31, 32);
        filterChainBuilder3.add(filterFactory2.getFilter(33));
        hashMap5.put(4, filterChainBuilder3.filterChain);
        HashMap hashMap6 = filterChainFactory.filterHashMap;
        FilterChainBuilder filterChainBuilder4 = new FilterChainBuilder();
        FilterManager$$ExternalSyntheticOutline0.m(filterFactory2, 3, filterChainBuilder4, 4, 6);
        FilterManager$$ExternalSyntheticOutline0.m(filterFactory2, 7, filterChainBuilder4, 9, 10);
        FilterManager$$ExternalSyntheticOutline0.m(filterFactory2, 11, filterChainBuilder4, 14, 17);
        hashMap6.put(5, filterChainBuilder4.filterChain);
        HashMap hashMap7 = filterChainFactory.filterHashMap;
        FilterChainBuilder filterChainBuilder5 = new FilterChainBuilder();
        FilterManager$$ExternalSyntheticOutline0.m(filterFactory2, 3, filterChainBuilder5, 4, 6);
        FilterManager$$ExternalSyntheticOutline0.m(filterFactory2, 7, filterChainBuilder5, 9, 10);
        FilterManager$$ExternalSyntheticOutline0.m(filterFactory2, 11, filterChainBuilder5, 14, 17);
        hashMap7.put(6, filterChainBuilder5.filterChain);
        HashMap hashMap8 = filterChainFactory.filterHashMap;
        FilterChainBuilder filterChainBuilder6 = new FilterChainBuilder();
        FilterManager$$ExternalSyntheticOutline0.m(filterFactory2, 4, filterChainBuilder6, 6, 7);
        FilterManager$$ExternalSyntheticOutline0.m(filterFactory2, 9, filterChainBuilder6, 10, 11);
        FilterManager$$ExternalSyntheticOutline0.m(filterFactory2, 14, filterChainBuilder6, 12, 21);
        FilterManager$$ExternalSyntheticOutline0.m(filterFactory2, 22, filterChainBuilder6, 25, 27);
        filterChainBuilder6.add(filterFactory2.getFilter(30));
        hashMap8.put(7, filterChainBuilder6.filterChain);
        HashMap hashMap9 = filterChainFactory.filterHashMap;
        FilterChainBuilder filterChainBuilder7 = new FilterChainBuilder();
        FilterManager$$ExternalSyntheticOutline0.m(filterFactory2, 4, filterChainBuilder7, 9, 10);
        FilterManager$$ExternalSyntheticOutline0.m(filterFactory2, 11, filterChainBuilder7, 14, 21);
        FilterManager$$ExternalSyntheticOutline0.m(filterFactory2, 22, filterChainBuilder7, 25, 30);
        hashMap9.put(8, filterChainBuilder7.filterChain);
        HashMap hashMap10 = filterChainFactory.filterHashMap;
        FilterChainBuilder filterChainBuilder8 = new FilterChainBuilder();
        FilterManager$$ExternalSyntheticOutline0.m(filterFactory2, 3, filterChainBuilder8, 2, 4);
        FilterManager$$ExternalSyntheticOutline0.m(filterFactory2, 5, filterChainBuilder8, 6, 7);
        FilterManager$$ExternalSyntheticOutline0.m(filterFactory2, 9, filterChainBuilder8, 10, 11);
        FilterManager$$ExternalSyntheticOutline0.m(filterFactory2, 12, filterChainBuilder8, 14, 17);
        filterChainBuilder8.add(filterFactory2.getFilter(19));
        filterChainBuilder8.add(filterFactory2.getFilter(24));
        hashMap10.put(9, filterChainBuilder8.filterChain);
        HashMap hashMap11 = filterChainFactory.filterHashMap;
        FilterChainBuilder filterChainBuilder9 = new FilterChainBuilder();
        FilterManager$$ExternalSyntheticOutline0.m(filterFactory2, 6, filterChainBuilder9, 2, 7);
        FilterManager$$ExternalSyntheticOutline0.m(filterFactory2, 9, filterChainBuilder9, 11, 12);
        FilterManager$$ExternalSyntheticOutline0.m(filterFactory2, 18, filterChainBuilder9, 19, 24);
        filterChainBuilder9.add(filterFactory2.getFilter(26));
        filterChainBuilder9.add(filterFactory2.getFilter(10));
        hashMap11.put(18, filterChainBuilder9.filterChain);
        HashMap hashMap12 = filterChainFactory.filterHashMap;
        IFilter filter6 = filterFactory2.getFilter(3);
        FilterChain filterChain6 = new FilterChain();
        filterChain6.mFilter = filter6;
        filterChain6.nextFilterChain = null;
        IFilter filter7 = filterFactory2.getFilter(4);
        FilterChain filterChain7 = new FilterChain();
        filterChain7.mFilter = filter7;
        filterChain7.nextFilterChain = filterChain6;
        IFilter filter8 = filterFactory2.getFilter(6);
        FilterChain filterChain8 = new FilterChain();
        filterChain8.mFilter = filter8;
        filterChain8.nextFilterChain = filterChain7;
        IFilter filter9 = filterFactory2.getFilter(9);
        FilterChain filterChain9 = new FilterChain();
        filterChain9.mFilter = filter9;
        filterChain9.nextFilterChain = filterChain8;
        IFilter filter10 = filterFactory2.getFilter(10);
        FilterChain filterChain10 = new FilterChain();
        filterChain10.mFilter = filter10;
        filterChain10.nextFilterChain = filterChain9;
        IFilter filter11 = filterFactory2.getFilter(11);
        FilterChain filterChain11 = new FilterChain();
        filterChain11.mFilter = filter11;
        filterChain11.nextFilterChain = filterChain10;
        hashMap12.put(10, filterChain11);
        HashMap hashMap13 = filterChainFactory.filterHashMap;
        IFilter filter12 = filterFactory2.getFilter(6);
        FilterChain filterChain12 = new FilterChain();
        filterChain12.mFilter = filter12;
        filterChain12.nextFilterChain = null;
        IFilter filter13 = filterFactory2.getFilter(7);
        FilterChain filterChain13 = new FilterChain();
        filterChain13.mFilter = filter13;
        filterChain13.nextFilterChain = filterChain12;
        IFilter filter14 = filterFactory2.getFilter(11);
        FilterChain filterChain14 = new FilterChain();
        filterChain14.mFilter = filter14;
        filterChain14.nextFilterChain = filterChain13;
        IFilter filter15 = filterFactory2.getFilter(2);
        FilterChain filterChain15 = new FilterChain();
        filterChain15.mFilter = filter15;
        filterChain15.nextFilterChain = filterChain14;
        IFilter filter16 = filterFactory2.getFilter(32);
        FilterChain filterChain16 = new FilterChain();
        filterChain16.mFilter = filter16;
        filterChain16.nextFilterChain = filterChain15;
        hashMap13.put(11, filterChain16);
        HashMap hashMap14 = filterChainFactory.filterHashMap;
        FilterChainBuilder filterChainBuilder10 = new FilterChainBuilder();
        FilterManager$$ExternalSyntheticOutline0.m(filterFactory2, 1, filterChainBuilder10, 2, 7);
        FilterManager$$ExternalSyntheticOutline0.m(filterFactory2, 15, filterChainBuilder10, 4, 6);
        FilterManager$$ExternalSyntheticOutline0.m(filterFactory2, 9, filterChainBuilder10, 11, 12);
        filterChainBuilder10.add(filterFactory2.getFilter(14));
        filterChainBuilder10.add(filterFactory2.getFilter(17));
        hashMap14.put(12, filterChainBuilder10.filterChain);
        HashMap hashMap15 = filterChainFactory.filterHashMap;
        IFilter filter17 = filterFactory2.getFilter(14);
        FilterChain filterChain17 = new FilterChain();
        filterChain17.mFilter = filter17;
        filterChain17.nextFilterChain = null;
        IFilter filter18 = filterFactory2.getFilter(5);
        FilterChain filterChain18 = new FilterChain();
        filterChain18.mFilter = filter18;
        filterChain18.nextFilterChain = filterChain17;
        hashMap15.put(13, filterChain18);
        HashMap hashMap16 = filterChainFactory.filterHashMap;
        IFilter filter19 = filterFactory2.getFilter(1);
        FilterChain filterChain19 = new FilterChain();
        filterChain19.mFilter = filter19;
        filterChain19.nextFilterChain = null;
        hashMap16.put(14, filterChain19);
        HashMap hashMap17 = filterChainFactory.filterHashMap;
        FilterChainBuilder filterChainBuilder11 = new FilterChainBuilder();
        FilterManager$$ExternalSyntheticOutline0.m(filterFactory2, 2, filterChainBuilder11, 8, 6);
        FilterManager$$ExternalSyntheticOutline0.m(filterFactory2, 7, filterChainBuilder11, 9, 11);
        FilterManager$$ExternalSyntheticOutline0.m(
                filterFactory2, 12, filterChainBuilder11, 14, 15);
        FilterManager$$ExternalSyntheticOutline0.m(
                filterFactory2, 17, filterChainBuilder11, 18, 23);
        FilterManager$$ExternalSyntheticOutline0.m(
                filterFactory2, 26, filterChainBuilder11, 27, 10);
        FilterManager$$ExternalSyntheticOutline0.m(
                filterFactory2, 28, filterChainBuilder11, 29, 31);
        filterChainBuilder11.add(filterFactory2.getFilter(32));
        filterChainBuilder11.add(filterFactory2.getFilter(33));
        hashMap17.put(15, filterChainBuilder11.filterChain);
        HashMap hashMap18 = filterChainFactory.filterHashMap;
        IFilter filter20 = filterFactory2.getFilter(6);
        FilterChain filterChain20 = new FilterChain();
        filterChain20.mFilter = filter20;
        filterChain20.nextFilterChain = null;
        IFilter filter21 = filterFactory2.getFilter(9);
        FilterChain filterChain21 = new FilterChain();
        filterChain21.mFilter = filter21;
        filterChain21.nextFilterChain = filterChain20;
        IFilter filter22 = filterFactory2.getFilter(10);
        FilterChain filterChain22 = new FilterChain();
        filterChain22.mFilter = filter22;
        filterChain22.nextFilterChain = filterChain21;
        IFilter filter23 = filterFactory2.getFilter(11);
        FilterChain filterChain23 = new FilterChain();
        filterChain23.mFilter = filter23;
        filterChain23.nextFilterChain = filterChain22;
        IFilter filter24 = filterFactory2.getFilter(13);
        FilterChain filterChain24 = new FilterChain();
        filterChain24.mFilter = filter24;
        filterChain24.nextFilterChain = filterChain23;
        IFilter filter25 = filterFactory2.getFilter(12);
        FilterChain filterChain25 = new FilterChain();
        filterChain25.mFilter = filter25;
        filterChain25.nextFilterChain = filterChain24;
        hashMap18.put(16, filterChain25);
        HashMap hashMap19 = filterChainFactory.filterHashMap;
        IFilter filter26 = filterFactory2.getFilter(9);
        FilterChain filterChain26 = new FilterChain();
        filterChain26.mFilter = filter26;
        filterChain26.nextFilterChain = null;
        IFilter filter27 = filterFactory2.getFilter(10);
        FilterChain filterChain27 = new FilterChain();
        filterChain27.mFilter = filter27;
        filterChain27.nextFilterChain = filterChain26;
        IFilter filter28 = filterFactory2.getFilter(11);
        FilterChain filterChain28 = new FilterChain();
        filterChain28.mFilter = filter28;
        filterChain28.nextFilterChain = filterChain27;
        IFilter filter29 = filterFactory2.getFilter(12);
        FilterChain filterChain29 = new FilterChain();
        filterChain29.mFilter = filter29;
        filterChain29.nextFilterChain = filterChain28;
        IFilter filter30 = filterFactory2.getFilter(17);
        FilterChain filterChain30 = new FilterChain();
        filterChain30.mFilter = filter30;
        filterChain30.nextFilterChain = filterChain29;
        hashMap19.put(19, filterChain30);
    }
}
