package com.android.server.firewall;

import android.app.AppGlobals;
import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.IPackageManager;
import android.content.pm.PackageManagerInternal;
import android.net.ConnectivityModuleConnector$$ExternalSyntheticOutline0;
import android.net.INetd;
import android.os.Environment;
import android.os.FileObserver;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.RemoteException;
import android.util.ArrayMap;
import android.util.EventLog;
import android.util.Slog;
import android.util.Xml;

import com.android.internal.util.ArrayUtils;
import com.android.internal.util.XmlUtils;
import com.android.server.IntentResolver;
import com.android.server.LocalServices;
import com.android.server.am.ActivityManagerService;
import com.android.server.pm.Computer;
import com.android.server.pm.PackageManagerService;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public final class IntentFirewall {
    public static final File RULES_DIR = new File(Environment.getDataSystemDirectory(), "ifw");
    public static final HashMap factoryMap;
    public final ActivityManagerService.PidMap mAms;
    public final FirewallHandler mHandler;
    public PackageManagerInternal mPackageManager;
    public FirewallIntentResolver mActivityResolver = new FirewallIntentResolver();
    public FirewallIntentResolver mBroadcastResolver = new FirewallIntentResolver();
    public FirewallIntentResolver mServiceResolver = new FirewallIntentResolver();

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class FirewallHandler extends Handler {
        public FirewallHandler(Looper looper) {
            super(looper, null, true);
        }

        @Override // android.os.Handler
        public final void handleMessage(Message message) {
            IntentFirewall.this.readRulesDir(IntentFirewall.RULES_DIR);
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class FirewallIntentFilter extends IntentFilter {
        public final Rule rule;

        public FirewallIntentFilter(Rule rule) {
            this.rule = rule;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class FirewallIntentResolver extends IntentResolver {
        public final ArrayMap mRulesByComponent = new ArrayMap(0);

        @Override // com.android.server.IntentResolver
        public final boolean allowFilterResult(List list, Object obj) {
            return !((ArrayList) list).contains(((FirewallIntentFilter) obj).rule);
        }

        @Override // com.android.server.IntentResolver
        public final IntentFilter getIntentFilter(Object obj) {
            return (FirewallIntentFilter) obj;
        }

        @Override // com.android.server.IntentResolver
        public final /* bridge */ /* synthetic */ boolean isPackageForFilter(
                String str, Object obj) {
            return true;
        }

        @Override // com.android.server.IntentResolver
        public final Object[] newArray(int i) {
            return new FirewallIntentFilter[i];
        }

        @Override // com.android.server.IntentResolver
        public final Object newResult(Computer computer, Object obj, int i, int i2, long j) {
            return ((FirewallIntentFilter) obj).rule;
        }

        @Override // com.android.server.IntentResolver
        public final void sortResults(List list) {}
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class Rule extends AndFilter {
        public boolean block;
        public boolean log;
        public final ArrayList mIntentFilters = new ArrayList(1);
        public final ArrayList mComponentFilters = new ArrayList(0);

        @Override // com.android.server.firewall.FilterList
        public final void readChild(XmlPullParser xmlPullParser) {
            String name = xmlPullParser.getName();
            if (name.equals("intent-filter")) {
                FirewallIntentFilter firewallIntentFilter = new FirewallIntentFilter(this);
                firewallIntentFilter.readFromXml(xmlPullParser);
                this.mIntentFilters.add(firewallIntentFilter);
            } else {
                if (!name.equals("component-filter")) {
                    this.children.add(IntentFirewall.parseFilter(xmlPullParser));
                    return;
                }
                String attributeValue = xmlPullParser.getAttributeValue(null, "name");
                if (attributeValue == null) {
                    throw new XmlPullParserException(
                            "Component name must be specified.", xmlPullParser, null);
                }
                ComponentName unflattenFromString =
                        ComponentName.unflattenFromString(attributeValue);
                if (unflattenFromString == null) {
                    throw new XmlPullParserException(
                            "Invalid component name: ".concat(attributeValue));
                }
                this.mComponentFilters.add(unflattenFromString);
            }
        }

        @Override // com.android.server.firewall.FilterList
        public final /* bridge */ /* synthetic */ FilterList readFromXml(
                XmlPullParser xmlPullParser) {
            throw null;
        }

        @Override // com.android.server.firewall.FilterList
        public final Rule readFromXml(XmlPullParser xmlPullParser) {
            this.block = Boolean.parseBoolean(xmlPullParser.getAttributeValue(null, "block"));
            this.log = Boolean.parseBoolean(xmlPullParser.getAttributeValue(null, "log"));
            super.readFromXml(xmlPullParser);
            return this;
        }
    }

    /* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
    public final class RuleObserver extends FileObserver {
        public RuleObserver(File file) {
            super(file.getAbsolutePath(), 968);
        }

        @Override // android.os.FileObserver
        public final void onEvent(int i, String str) {
            if (str == null || !str.endsWith(".xml")) {
                return;
            }
            IntentFirewall.this.mHandler.removeMessages(0);
            IntentFirewall.this.mHandler.sendEmptyMessageDelayed(0, 250L);
        }
    }

    static {
        FilterFactory[] filterFactoryArr = {
            AndFilter.FACTORY,
            OrFilter.FACTORY,
            NotFilter.FACTORY,
            StringFilter.ACTION,
            StringFilter.COMPONENT,
            StringFilter.COMPONENT_NAME,
            StringFilter.COMPONENT_PACKAGE,
            StringFilter.DATA,
            StringFilter.HOST,
            StringFilter.MIME_TYPE,
            StringFilter.SCHEME,
            StringFilter.PATH,
            StringFilter.SSP,
            CategoryFilter.FACTORY,
            SenderFilter.FACTORY,
            SenderPackageFilter.FACTORY,
            SenderPermissionFilter.FACTORY,
            PortFilter.FACTORY
        };
        factoryMap = new HashMap(24);
        for (int i = 0; i < 18; i++) {
            FilterFactory filterFactory = filterFactoryArr[i];
            factoryMap.put(filterFactory.mTag, filterFactory);
        }
    }

    public IntentFirewall(
            ActivityManagerService.PidMap pidMap, ActivityManagerService.UiHandler uiHandler) {
        this.mAms = pidMap;
        this.mHandler = new FirewallHandler(uiHandler.getLooper());
        File file = RULES_DIR;
        file.mkdirs();
        readRulesDir(file);
        new RuleObserver(file).startWatching();
    }

    public static String joinPackages(String[] strArr) {
        StringBuilder sb = new StringBuilder();
        boolean z = true;
        for (String str : strArr) {
            if (str.length() + sb.length() + 1 < 150) {
                if (z) {
                    z = false;
                } else {
                    sb.append(',');
                }
                sb.append(str);
            } else if (sb.length() >= 125) {
                return sb.toString();
            }
        }
        if (sb.length() != 0 || strArr.length <= 0) {
            return null;
        }
        return strArr[0].substring(r8.length() - 149) + '-';
    }

    public static Filter parseFilter(XmlPullParser xmlPullParser) {
        String name = xmlPullParser.getName();
        FilterFactory filterFactory = (FilterFactory) factoryMap.get(name);
        if (filterFactory != null) {
            return filterFactory.newFilter(xmlPullParser);
        }
        throw new XmlPullParserException(
                ConnectivityModuleConnector$$ExternalSyntheticOutline0.m(
                        "Unknown element in filter list: ", name));
    }

    public final boolean checkIntent(
            FirewallIntentResolver firewallIntentResolver,
            ComponentName componentName,
            int i,
            Intent intent,
            int i2,
            int i3,
            String str,
            int i4) {
        List queryIntent =
                firewallIntentResolver.queryIntent(
                        ((PackageManagerService.PackageManagerInternalImpl) getPackageManager())
                                .mService.snapshotComputer(),
                        intent,
                        str,
                        false,
                        0,
                        0L);
        if (queryIntent == null) {
            queryIntent = new ArrayList();
        }
        Rule[] ruleArr = (Rule[]) firewallIntentResolver.mRulesByComponent.get(componentName);
        if (ruleArr != null) {
            queryIntent.addAll(Arrays.asList(ruleArr));
        }
        int i5 = 0;
        boolean z = false;
        boolean z2 = false;
        for (int i6 = 0; i6 < queryIntent.size(); i6++) {
            Rule rule = (Rule) queryIntent.get(i6);
            if (rule.matches(this, componentName, intent, i2, i3, str, i4)) {
                z |= rule.block;
                z2 |= rule.log;
                if (z && z2) {
                    break;
                }
            }
        }
        if (z2) {
            ComponentName component = intent.getComponent();
            String str2 = null;
            String flattenToShortString =
                    component != null ? component.flattenToShortString() : null;
            IPackageManager packageManager = AppGlobals.getPackageManager();
            if (packageManager != null) {
                try {
                    String[] packagesForUid = packageManager.getPackagesForUid(i2);
                    if (packagesForUid != null) {
                        i5 = packagesForUid.length;
                        str2 = joinPackages(packagesForUid);
                    }
                } catch (RemoteException e) {
                    Slog.e("IntentFirewall", "Remote exception while retrieving packages", e);
                }
            }
            EventLog.writeEvent(
                    51400,
                    Integer.valueOf(i),
                    flattenToShortString,
                    Integer.valueOf(i2),
                    Integer.valueOf(i5),
                    str2,
                    intent.getAction(),
                    str,
                    intent.getDataString(),
                    Integer.valueOf(intent.getFlags()));
        }
        return !z;
    }

    public final PackageManagerInternal getPackageManager() {
        if (this.mPackageManager == null) {
            this.mPackageManager =
                    (PackageManagerInternal) LocalServices.getService(PackageManagerInternal.class);
        }
        return this.mPackageManager;
    }

    public final void readRulesDir(File file) {
        IOException iOException;
        StringBuilder sb;
        int i = 3;
        FirewallIntentResolver[] firewallIntentResolverArr = new FirewallIntentResolver[3];
        for (int i2 = 0; i2 < 3; i2++) {
            firewallIntentResolverArr[i2] = new FirewallIntentResolver();
        }
        File[] listFiles = file.listFiles();
        if (listFiles != null) {
            int i3 = 0;
            while (i3 < listFiles.length) {
                File file2 = listFiles[i3];
                if (file2.getName().endsWith(".xml")) {
                    ArrayList arrayList = new ArrayList(i);
                    for (int i4 = 0; i4 < i; i4++) {
                        arrayList.add(new ArrayList());
                    }
                    try {
                        FileInputStream fileInputStream = new FileInputStream(file2);
                        try {
                            try {
                                try {
                                    XmlPullParser newPullParser = Xml.newPullParser();
                                    newPullParser.setInput(fileInputStream, null);
                                    XmlUtils.beginDocument(newPullParser, "rules");
                                    int depth = newPullParser.getDepth();
                                    while (XmlUtils.nextElementWithin(newPullParser, depth)) {
                                        String name = newPullParser.getName();
                                        int i5 =
                                                name.equals("activity")
                                                        ? 0
                                                        : name.equals(INetd.IF_FLAG_BROADCAST)
                                                                ? 1
                                                                : name.equals("service") ? 2 : -1;
                                        if (i5 != -1) {
                                            Rule rule = new Rule();
                                            List list = (List) arrayList.get(i5);
                                            try {
                                                rule.readFromXml(newPullParser);
                                                list.add(rule);
                                            } catch (XmlPullParserException e) {
                                                Slog.e(
                                                        "IntentFirewall",
                                                        "Error reading an intent firewall rule from"
                                                            + " "
                                                                + file2,
                                                        e);
                                            }
                                        }
                                    }
                                    try {
                                        fileInputStream.close();
                                    } catch (IOException e2) {
                                        Slog.e(
                                                "IntentFirewall",
                                                "Error while closing " + file2,
                                                e2);
                                    }
                                    for (int i6 = 0; i6 < arrayList.size(); i6++) {
                                        List list2 = (List) arrayList.get(i6);
                                        FirewallIntentResolver firewallIntentResolver =
                                                firewallIntentResolverArr[i6];
                                        for (int i7 = 0; i7 < list2.size(); i7++) {
                                            Rule rule2 = (Rule) list2.get(i7);
                                            for (int i8 = 0;
                                                    i8 < rule2.mIntentFilters.size();
                                                    i8++) {
                                                firewallIntentResolver.addFilter(
                                                        null,
                                                        (FirewallIntentFilter)
                                                                rule2.mIntentFilters.get(i8));
                                            }
                                            for (int i9 = 0;
                                                    i9 < rule2.mComponentFilters.size();
                                                    i9++) {
                                                ComponentName componentName =
                                                        (ComponentName)
                                                                rule2.mComponentFilters.get(i9);
                                                firewallIntentResolver.mRulesByComponent.put(
                                                        componentName,
                                                        (Rule[])
                                                                ArrayUtils.appendElement(
                                                                        Rule.class,
                                                                        (Rule[])
                                                                                firewallIntentResolver
                                                                                        .mRulesByComponent
                                                                                        .get(
                                                                                                componentName),
                                                                        rule2));
                                            }
                                        }
                                    }
                                } catch (IOException e3) {
                                    Slog.e(
                                            "IntentFirewall",
                                            "Error reading intent firewall rules from " + file2,
                                            e3);
                                    try {
                                        fileInputStream.close();
                                    } catch (IOException e4) {
                                        iOException = e4;
                                        sb = new StringBuilder("Error while closing ");
                                        sb.append(file2);
                                        Slog.e("IntentFirewall", sb.toString(), iOException);
                                        i3++;
                                        i = 3;
                                    }
                                }
                            } finally {
                            }
                        } catch (XmlPullParserException e5) {
                            Slog.e(
                                    "IntentFirewall",
                                    "Error reading intent firewall rules from " + file2,
                                    e5);
                            try {
                                fileInputStream.close();
                            } catch (IOException e6) {
                                iOException = e6;
                                sb = new StringBuilder("Error while closing ");
                                sb.append(file2);
                                Slog.e("IntentFirewall", sb.toString(), iOException);
                                i3++;
                                i = 3;
                            }
                        }
                    } catch (FileNotFoundException unused) {
                    }
                }
                i3++;
                i = 3;
            }
        }
        Slog.i(
                "IntentFirewall",
                "Read new rules (A:"
                        + Collections.unmodifiableSet(firewallIntentResolverArr[0].mFilters).size()
                        + " B:"
                        + Collections.unmodifiableSet(firewallIntentResolverArr[1].mFilters).size()
                        + " S:"
                        + Collections.unmodifiableSet(firewallIntentResolverArr[2].mFilters).size()
                        + ")");
        synchronized (((ActivityManagerService) this.mAms.mPidMap)) {
            this.mActivityResolver = firewallIntentResolverArr[0];
            this.mBroadcastResolver = firewallIntentResolverArr[1];
            this.mServiceResolver = firewallIntentResolverArr[2];
        }
    }
}
