package com.android.internal.content.om;

import android.content.pm.PackagePartitions;
import android.os.Trace;
import android.util.ArrayMap;
import android.util.IndentingPrintWriter;
import android.util.Log;

import com.android.apex.ApexInfo;
import com.android.apex.XmlParser;
import com.android.internal.util.Preconditions;
import com.android.internal.util.function.TriConsumer;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class OverlayConfig {
    public static final int DEFAULT_PRIORITY = Integer.MAX_VALUE;
    public static final String PARTITION_ORDER_FILE_PATH = "/product/overlay/partition_order.xml";
    static final String TAG = "OverlayConfig";
    private static OverlayConfig sInstance;
    private static final Comparator<OverlayConfigParser.ParsedConfiguration>
            sStaticOverlayComparator =
                    new Comparator() { // from class:
                                       // com.android.internal.content.om.OverlayConfig$$ExternalSyntheticLambda4
                        @Override // java.util.Comparator
                        public final int compare(Object obj, Object obj2) {
                            return OverlayConfig.lambda$static$0(
                                    (OverlayConfigParser.ParsedConfiguration) obj,
                                    (OverlayConfigParser.ParsedConfiguration) obj2);
                        }
                    };
    private final ArrayMap<String, Configuration> mConfigurations = new ArrayMap<>();
    private final boolean mIsDefaultPartitionOrder;
    private final String mPartitionOrder;

    public interface PackageProvider {

        public interface Package {
            String getBaseApkPath();

            int getOverlayPriority();

            String getOverlayTarget();

            String getPackageName();

            int getTargetSdkVersion();

            boolean isOverlayIsStatic();
        }

        void forEachPackage(TriConsumer<Package, Boolean, File> triConsumer);
    }

    private static native String[] createIdmap(
            String str, String[] strArr, String[] strArr2, boolean z);

    public static final class Configuration {
        public final int configIndex;
        public final OverlayConfigParser.ParsedConfiguration parsedConfig;

        public Configuration(
                OverlayConfigParser.ParsedConfiguration parsedConfig, int configIndex) {
            this.parsedConfig = parsedConfig;
            this.configIndex = configIndex;
        }
    }

    static /* synthetic */ int lambda$static$0(
            OverlayConfigParser.ParsedConfiguration c1,
            OverlayConfigParser.ParsedConfiguration c2) {
        OverlayScanner.ParsedOverlayInfo o1 = c1.parsedInfo;
        OverlayScanner.ParsedOverlayInfo o2 = c2.parsedInfo;
        Preconditions.checkArgument(
                o1.isStatic && o2.isStatic, "attempted to sort non-static overlay");
        if (!o1.targetPackageName.equals(o2.targetPackageName)) {
            return o1.targetPackageName.compareTo(o2.targetPackageName);
        }
        int comparedPriority = o1.priority - o2.priority;
        return comparedPriority == 0 ? o1.path.compareTo(o2.path) : comparedPriority;
    }

    public OverlayConfig(
            final File rootDirectory,
            Supplier<OverlayScanner> scannerFactory,
            PackageProvider packageProvider) {
        ArrayList<OverlayConfigParser.OverlayPartition> partitions;
        ArrayList<OverlayScanner.ParsedOverlayInfo> partitionOverlayInfos;
        ArrayList<OverlayConfigParser.OverlayPartition> partitions2;
        ArrayList<OverlayConfigParser.OverlayPartition> partitions3;
        int m;
        int i = 1;
        Preconditions.checkArgument(
                (scannerFactory == null) != (packageProvider == null),
                "scannerFactory and packageProvider cannot be both null or both non-null");
        if (rootDirectory == null) {
            partitions =
                    new ArrayList<>(
                            PackagePartitions.getOrderedPartitions(
                                    new Function() { // from class:
                                                     // com.android.internal.content.om.OverlayConfig$$ExternalSyntheticLambda2
                                        @Override // java.util.function.Function
                                        public final Object apply(Object obj) {
                                            return new OverlayConfigParser.OverlayPartition(
                                                    (PackagePartitions.SystemPartition) obj);
                                        }
                                    }));
        } else {
            partitions =
                    new ArrayList<>(
                            PackagePartitions.getOrderedPartitions(
                                    new Function() { // from class:
                                                     // com.android.internal.content.om.OverlayConfig$$ExternalSyntheticLambda3
                                        @Override // java.util.function.Function
                                        public final Object apply(Object obj) {
                                            return OverlayConfig.lambda$new$1(
                                                    rootDirectory,
                                                    (PackagePartitions.SystemPartition) obj);
                                        }
                                    }));
        }
        this.mIsDefaultPartitionOrder = !sortPartitions(PARTITION_ORDER_FILE_PATH, partitions);
        this.mPartitionOrder = generatePartitionOrderString(partitions);
        ArrayMap<Integer, List<String>> activeApexesPerPartition = getActiveApexes(partitions);
        Map<String, OverlayScanner.ParsedOverlayInfo> packageManagerOverlayInfos =
                packageProvider == null ? null : getOverlayPackageInfos(packageProvider);
        ArrayList<OverlayConfigParser.ParsedConfiguration> overlays = new ArrayList<>();
        int i2 = 0;
        int n = partitions.size();
        while (i2 < n) {
            OverlayConfigParser.OverlayPartition partition = partitions.get(i2);
            OverlayScanner scanner = scannerFactory == null ? null : scannerFactory.get();
            ArrayList<OverlayConfigParser.ParsedConfiguration> partitionOverlays =
                    OverlayConfigParser.getConfigurations(
                            partition,
                            scanner,
                            packageManagerOverlayInfos,
                            activeApexesPerPartition.getOrDefault(
                                    Integer.valueOf(partition.type), Collections.emptyList()));
            if (partitionOverlays != null) {
                overlays.addAll(partitionOverlays);
                partitions2 = partitions;
            } else {
                if (scannerFactory != null) {
                    partitionOverlayInfos = new ArrayList<>(scanner.getAllParsedInfos());
                } else {
                    partitionOverlayInfos = new ArrayList<>(packageManagerOverlayInfos.values());
                    for (int j = partitionOverlayInfos.size() - i; j >= 0; j--) {
                        if (!partition.containsFile(
                                partitionOverlayInfos.get(j).getOriginalPartitionPath())) {
                            partitionOverlayInfos.remove(j);
                        }
                    }
                }
                ArrayList<OverlayConfigParser.ParsedConfiguration> partitionConfigs =
                        new ArrayList<>();
                int j2 = 0;
                int m2 = partitionOverlayInfos.size();
                while (j2 < m2) {
                    OverlayScanner.ParsedOverlayInfo p = partitionOverlayInfos.get(j2);
                    if (!p.isStatic) {
                        partitions3 = partitions;
                        m = m2;
                    } else {
                        partitions3 = partitions;
                        m = m2;
                        partitionConfigs.add(
                                new OverlayConfigParser.ParsedConfiguration(
                                        p.packageName, true, false, partition.policy, p, null));
                    }
                    j2++;
                    partitions = partitions3;
                    m2 = m;
                }
                partitions2 = partitions;
                partitionConfigs.sort(sStaticOverlayComparator);
                overlays.addAll(partitionConfigs);
            }
            i2++;
            partitions = partitions2;
            i = 1;
        }
        int n2 = overlays.size();
        for (int i3 = 0; i3 < n2; i3++) {
            OverlayConfigParser.ParsedConfiguration config = overlays.get(i3);
            this.mConfigurations.put(config.packageName, new Configuration(config, i3));
        }
    }

    static /* synthetic */ OverlayConfigParser.OverlayPartition lambda$new$1(
            File rootDirectory, PackagePartitions.SystemPartition p) {
        return new OverlayConfigParser.OverlayPartition(
                new File(rootDirectory, p.getNonConicalFolder().getPath()), p);
    }

    private static String generatePartitionOrderString(
            List<OverlayConfigParser.OverlayPartition> partitions) {
        if (partitions == null || partitions.size() == 0) {
            return "";
        }
        StringBuilder partitionOrder = new StringBuilder();
        partitionOrder.append(partitions.get(0).getName());
        for (int i = 1; i < partitions.size(); i++) {
            partitionOrder.append(", ").append(partitions.get(i).getName());
        }
        return partitionOrder.toString();
    }

    private static boolean parseAndValidatePartitionsOrderXml(
            String partitionOrderFilePath,
            Map<String, Integer> orderMap,
            List<OverlayConfigParser.OverlayPartition> partitions) {
        try {
        } catch (IOException | ParserConfigurationException | SAXException e) {
            e = e;
        }
        try {
            File file = new File(partitionOrderFilePath);
            if (!file.exists()) {
                Log.w(TAG, "partition_order.xml does not exist.");
                return false;
            }
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(file);
            doc.getDocumentElement().normalize();
            Element root = doc.getDocumentElement();
            if (!root.getNodeName().equals("partition-order")) {
                Log.w(TAG, "Invalid partition_order.xml, xml root element is not partition-order");
                return false;
            }
            NodeList partitionList = doc.getElementsByTagName("partition");
            for (int order = 0; order < partitionList.getLength(); order++) {
                Node partitionNode = partitionList.item(order);
                if (partitionNode.getNodeType() == 1) {
                    Element partitionElement = (Element) partitionNode;
                    String partitionName = partitionElement.getAttribute("name");
                    if (!orderMap.containsKey(partitionName)) {
                        orderMap.put(partitionName, Integer.valueOf(order));
                    } else {
                        Log.w(
                                TAG,
                                "Invalid partition_order.xml, it has duplicate partition: "
                                        + partitionName);
                        return false;
                    }
                }
            }
            if (orderMap.keySet().size() != partitions.size()) {
                Log.w(
                        TAG,
                        "Invalid partition_order.xml, partition_order.xml has "
                                + orderMap.keySet().size()
                                + " partitions, which is different from SYSTEM_PARTITIONS");
                return false;
            }
            for (int i = 0; i < partitions.size(); i++) {
                if (!orderMap.keySet().contains(partitions.get(i).getName())) {
                    Log.w(
                            TAG,
                            "Invalid Parsing partition_order.xml, partition_order.xml does not have"
                                + " partition: "
                                    + partitions.get(i).getName());
                    return false;
                }
            }
            Log.i(TAG, "Sorting partitions in the specified order from partitions_order.xml");
            return true;
        } catch (IOException | ParserConfigurationException | SAXException e2) {
            e = e2;
            Log.w(
                    TAG,
                    "Parsing or validating partition_order.xml failed, exception thrown: "
                            + e.getMessage());
            return false;
        }
    }

    public static boolean sortPartitions(
            String partitionOrderFilePath, List<OverlayConfigParser.OverlayPartition> partitions) {
        final Map<String, Integer> orderMap = new HashMap<>();
        if (!parseAndValidatePartitionsOrderXml(partitionOrderFilePath, orderMap, partitions)) {
            return false;
        }
        Comparator<OverlayConfigParser.OverlayPartition> partitionComparator =
                Comparator.comparingInt(
                        new ToIntFunction() { // from class:
                                              // com.android.internal.content.om.OverlayConfig$$ExternalSyntheticLambda0
                            @Override // java.util.function.ToIntFunction
                            public final int applyAsInt(Object obj) {
                                int intValue;
                                intValue =
                                        ((Integer)
                                                        orderMap.get(
                                                                ((OverlayConfigParser
                                                                                        .OverlayPartition)
                                                                                obj)
                                                                        .getName()))
                                                .intValue();
                                return intValue;
                            }
                        });
        Collections.sort(partitions, partitionComparator);
        return true;
    }

    public static OverlayConfig getZygoteInstance() {
        Trace.traceBegin(67108864L, "OverlayConfig#getZygoteInstance");
        try {
            return new OverlayConfig(
                    null,
                    new Supplier() { // from class:
                                     // com.android.internal.content.om.OverlayConfig$$ExternalSyntheticLambda7
                        @Override // java.util.function.Supplier
                        public final Object get() {
                            return new OverlayScanner();
                        }
                    },
                    null);
        } finally {
            Trace.traceEnd(67108864L);
        }
    }

    public static OverlayConfig initializeSystemInstance(PackageProvider packageProvider) {
        Trace.traceBegin(67108864L, "OverlayConfig#initializeSystemInstance");
        try {
            sInstance = new OverlayConfig(null, null, packageProvider);
            Trace.traceEnd(67108864L);
            return sInstance;
        } catch (Throwable th) {
            Trace.traceEnd(67108864L);
            throw th;
        }
    }

    public static OverlayConfig getSystemInstance() {
        if (sInstance == null) {
            throw new IllegalStateException("System instance not initialized");
        }
        return sInstance;
    }

    public Configuration getConfiguration(String packageName) {
        return this.mConfigurations.get(packageName);
    }

    public boolean isEnabled(String packageName) {
        Configuration config = this.mConfigurations.get(packageName);
        if (config == null) {
            return false;
        }
        return config.parsedConfig.enabled;
    }

    public boolean isMutable(String packageName) {
        Configuration config = this.mConfigurations.get(packageName);
        if (config == null) {
            return true;
        }
        return config.parsedConfig.mutable;
    }

    public int getPriority(String packageName) {
        Configuration config = this.mConfigurations.get(packageName);
        if (config == null) {
            return Integer.MAX_VALUE;
        }
        return config.configIndex;
    }

    private ArrayList<Configuration> getSortedOverlays() {
        ArrayList<Configuration> sortedOverlays = new ArrayList<>();
        int n = this.mConfigurations.size();
        for (int i = 0; i < n; i++) {
            sortedOverlays.add(this.mConfigurations.valueAt(i));
        }
        sortedOverlays.sort(
                Comparator.comparingInt(
                        new ToIntFunction() { // from class:
                                              // com.android.internal.content.om.OverlayConfig$$ExternalSyntheticLambda5
                            @Override // java.util.function.ToIntFunction
                            public final int applyAsInt(Object obj) {
                                int i2;
                                i2 = ((OverlayConfig.Configuration) obj).configIndex;
                                return i2;
                            }
                        }));
        return sortedOverlays;
    }

    private static Map<String, OverlayScanner.ParsedOverlayInfo> getOverlayPackageInfos(
            PackageProvider packageManager) {
        final HashMap<String, OverlayScanner.ParsedOverlayInfo> overlays = new HashMap<>();
        packageManager.forEachPackage(
                new TriConsumer() { // from class:
                                    // com.android.internal.content.om.OverlayConfig$$ExternalSyntheticLambda6
                    @Override // com.android.internal.util.function.TriConsumer
                    public final void accept(Object obj, Object obj2, Object obj3) {
                        OverlayConfig.lambda$getOverlayPackageInfos$4(
                                overlays,
                                (OverlayConfig.PackageProvider.Package) obj,
                                (Boolean) obj2,
                                (File) obj3);
                    }
                });
        return overlays;
    }

    static /* synthetic */ void lambda$getOverlayPackageInfos$4(
            HashMap overlays,
            PackageProvider.Package p,
            Boolean isSystem,
            File preInstalledApexPath) {
        if (p.getOverlayTarget() != null && isSystem.booleanValue()) {
            overlays.put(
                    p.getPackageName(),
                    new OverlayScanner.ParsedOverlayInfo(
                            p.getPackageName(),
                            p.getOverlayTarget(),
                            p.getTargetSdkVersion(),
                            p.isOverlayIsStatic(),
                            p.getOverlayPriority(),
                            new File(p.getBaseApkPath()),
                            preInstalledApexPath));
        }
    }

    private static ArrayMap<Integer, List<String>> getActiveApexes(
            List<OverlayConfigParser.OverlayPartition> partitions) {
        ArrayMap<Integer, List<String>> result = new ArrayMap<>();
        Iterator<OverlayConfigParser.OverlayPartition> it = partitions.iterator();
        while (it.hasNext()) {
            result.put(Integer.valueOf(it.next().type), new ArrayList());
        }
        File apexInfoList = new File("/apex/apex-info-list.xml");
        if (apexInfoList.exists() && apexInfoList.canRead()) {
            try {
                FileInputStream stream = new FileInputStream(apexInfoList);
                try {
                    List<ApexInfo> apexInfos = XmlParser.readApexInfoList(stream).getApexInfo();
                    for (ApexInfo info : apexInfos) {
                        if (info.getIsActive()) {
                            Iterator<OverlayConfigParser.OverlayPartition> it2 =
                                    partitions.iterator();
                            while (true) {
                                if (it2.hasNext()) {
                                    OverlayConfigParser.OverlayPartition partition = it2.next();
                                    if (partition.containsPath(info.getPreinstalledModulePath())) {
                                        result.get(Integer.valueOf(partition.type))
                                                .add(info.getModuleName());
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    stream.close();
                } finally {
                }
            } catch (Exception e) {
                Log.w(TAG, "Error reading apex-info-list: " + e);
            }
        }
        return result;
    }

    public static class IdmapInvocation {
        public final boolean enforceOverlayable;
        public final ArrayList<String> overlayPaths = new ArrayList<>();
        public final String policy;

        IdmapInvocation(boolean enforceOverlayable, String policy) {
            this.enforceOverlayable = enforceOverlayable;
            this.policy = policy;
        }

        public String toString() {
            return getClass().getSimpleName()
                    + String.format(
                            "{enforceOverlayable=%s, policy=%s, overlayPaths=[%s]}",
                            Boolean.valueOf(this.enforceOverlayable),
                            this.policy,
                            String.join(", ", this.overlayPaths));
        }
    }

    public ArrayList<IdmapInvocation> getImmutableFrameworkOverlayIdmapInvocations() {
        ArrayList<IdmapInvocation> idmapInvocations = new ArrayList<>();
        ArrayList<Configuration> sortedConfigs = getSortedOverlays();
        int n = sortedConfigs.size();
        for (int i = 0; i < n; i++) {
            Configuration overlay = sortedConfigs.get(i);
            if (!overlay.parsedConfig.mutable
                    && overlay.parsedConfig.enabled
                    && "android".equals(overlay.parsedConfig.parsedInfo.targetPackageName)) {
                boolean enforceOverlayable = overlay.parsedConfig.parsedInfo.targetSdkVersion >= 29;
                IdmapInvocation invocation = null;
                if (!idmapInvocations.isEmpty()) {
                    IdmapInvocation last = idmapInvocations.get(idmapInvocations.size() - 1);
                    if (last.enforceOverlayable == enforceOverlayable
                            && last.policy.equals(overlay.parsedConfig.policy)) {
                        invocation = last;
                    }
                }
                if (invocation == null) {
                    invocation =
                            new IdmapInvocation(enforceOverlayable, overlay.parsedConfig.policy);
                    idmapInvocations.add(invocation);
                }
                invocation.overlayPaths.add(overlay.parsedConfig.parsedInfo.path.getAbsolutePath());
            }
        }
        return idmapInvocations;
    }

    public String[] createImmutableFrameworkIdmapsInZygote() {
        ArrayList<String> idmapPaths = new ArrayList<>();
        ArrayList<IdmapInvocation> idmapInvocations =
                getImmutableFrameworkOverlayIdmapInvocations();
        int n = idmapInvocations.size();
        for (int i = 0; i < n; i++) {
            IdmapInvocation invocation = idmapInvocations.get(i);
            String[] idmaps =
                    createIdmap(
                            "/system/framework/framework-res.apk",
                            (String[]) invocation.overlayPaths.toArray(new String[0]),
                            new String[] {"public", invocation.policy},
                            invocation.enforceOverlayable);
            if (idmaps == null) {
                Log.w(
                        TAG,
                        "'idmap2 create-multiple' failed: no mutable=\"false\" overlays targeting"
                            + " \"android\" will be loaded");
                return new String[0];
            }
            idmapPaths.addAll(Arrays.asList(idmaps));
        }
        return (String[]) idmapPaths.toArray(new String[0]);
    }

    public void dump(PrintWriter writer) {
        IndentingPrintWriter ipw = new IndentingPrintWriter(writer);
        ipw.println("Overlay configurations:");
        ipw.increaseIndent();
        ArrayList<Configuration> configurations = new ArrayList<>(this.mConfigurations.values());
        configurations.sort(
                Comparator.comparingInt(
                        new ToIntFunction() { // from class:
                                              // com.android.internal.content.om.OverlayConfig$$ExternalSyntheticLambda1
                            @Override // java.util.function.ToIntFunction
                            public final int applyAsInt(Object obj) {
                                int i;
                                i = ((OverlayConfig.Configuration) obj).configIndex;
                                return i;
                            }
                        }));
        for (int i = 0; i < configurations.size(); i++) {
            Configuration configuration = configurations.get(i);
            ipw.print(configuration.configIndex);
            ipw.print(", ");
            ipw.print(configuration.parsedConfig);
            ipw.println();
        }
        ipw.decreaseIndent();
        ipw.println();
    }

    public boolean isDefaultPartitionOrder() {
        return this.mIsDefaultPartitionOrder;
    }

    public String getPartitionOrder() {
        return this.mPartitionOrder;
    }
}
