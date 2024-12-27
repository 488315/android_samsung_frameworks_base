package com.samsung.android.core.pm.runtimemanifest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RuntimeManifestPolicies {
    public static final String TAG = "RuntimeManifestPolicies";
    private final List<PolicyInfo> mApplications = new ArrayList();
    private final Map<String, List<PolicyInfo>> mActivities = new HashMap();
    private final Map<String, List<PolicyInfo>> mReceivers = new HashMap();
    private final Map<String, List<PolicyInfo>> mServices = new HashMap();
    private final Map<String, List<PolicyInfo>> mProviders = new HashMap();

    public void addApplicationPolicies(List<PolicyInfo> applications) {
        this.mApplications.addAll(applications);
    }

    static /* synthetic */ int lambda$getApplicationPolicies$0(PolicyInfo o1, PolicyInfo o2) {
        return o2.getPriority() - o1.getPriority();
    }

    public List<PolicyInfo> getApplicationPolicies() {
        Collections.sort(
                this.mApplications,
                new Comparator() { // from class:
                                   // com.samsung.android.core.pm.runtimemanifest.RuntimeManifestPolicies$$ExternalSyntheticLambda1
                    @Override // java.util.Comparator
                    public final int compare(Object obj, Object obj2) {
                        return RuntimeManifestPolicies.lambda$getApplicationPolicies$0(
                                (RuntimeManifestPolicies.PolicyInfo) obj,
                                (RuntimeManifestPolicies.PolicyInfo) obj2);
                    }
                });
        return this.mApplications;
    }

    public void addActivityPolicies(Map<String, List<PolicyInfo>> activities) {
        this.mActivities.putAll(activities);
    }

    public Map<String, List<PolicyInfo>> getActivityPolicies() {
        sortMap(this.mActivities);
        return this.mActivities;
    }

    public void addServicePolicies(Map<String, List<PolicyInfo>> services) {
        this.mServices.putAll(services);
    }

    public Map<String, List<PolicyInfo>> getServicePolicies() {
        sortMap(this.mServices);
        return this.mServices;
    }

    public void addProviderPolicies(Map<String, List<PolicyInfo>> providers) {
        this.mProviders.putAll(providers);
    }

    public Map<String, List<PolicyInfo>> getProviderPolicies() {
        sortMap(this.mProviders);
        return this.mProviders;
    }

    public void addReceiverPolicies(Map<String, List<PolicyInfo>> receivers) {
        this.mReceivers.putAll(receivers);
    }

    public Map<String, List<PolicyInfo>> getReceiverPolicies() {
        sortMap(this.mReceivers);
        return this.mReceivers;
    }

    private static List<PolicyInfo> getAndSortFromMap(
            Map<String, List<PolicyInfo>> map, String key) {
        List<PolicyInfo> policies = map.get(key);
        if (policies != null) {
            Collections.sort(
                    policies,
                    new Comparator() { // from class:
                                       // com.samsung.android.core.pm.runtimemanifest.RuntimeManifestPolicies$$ExternalSyntheticLambda2
                        @Override // java.util.Comparator
                        public final int compare(Object obj, Object obj2) {
                            return RuntimeManifestPolicies.lambda$getAndSortFromMap$1(
                                    (RuntimeManifestPolicies.PolicyInfo) obj,
                                    (RuntimeManifestPolicies.PolicyInfo) obj2);
                        }
                    });
        }
        return policies;
    }

    static /* synthetic */ int lambda$getAndSortFromMap$1(PolicyInfo o1, PolicyInfo o2) {
        return o2.getPriority() - o1.getPriority();
    }

    private static void sortMap(Map<String, List<PolicyInfo>> map) {
        for (List<PolicyInfo> policies : map.values()) {
            Collections.sort(
                    policies,
                    new Comparator() { // from class:
                                       // com.samsung.android.core.pm.runtimemanifest.RuntimeManifestPolicies$$ExternalSyntheticLambda0
                        @Override // java.util.Comparator
                        public final int compare(Object obj, Object obj2) {
                            return RuntimeManifestPolicies.lambda$sortMap$2(
                                    (RuntimeManifestPolicies.PolicyInfo) obj,
                                    (RuntimeManifestPolicies.PolicyInfo) obj2);
                        }
                    });
        }
    }

    static /* synthetic */ int lambda$sortMap$2(PolicyInfo o1, PolicyInfo o2) {
        return o2.getPriority() - o1.getPriority();
    }

    public static class PolicyInfo {
        private static final String COERCED_LABEL = "coerced_label";
        private static final String ENABLED = "enabled";
        private static final String ICON = "icon";
        private static final String LABEL = "label";
        private static final String MAX_VALUE = "max_value";
        private static final String MIN_VALUE = "min_value";
        private static final String PRIORITY = "priority";
        private static final String PROPERTY_NAME = "property_type";
        private static final String PROPERTY_VALUE = "property_value";
        private static final String TYPE = "type";
        private static final String VALUE = "value";
        private String mType = "";
        private String mValue = "";
        private String mMinValue = "";
        private String mMaxValue = "";
        private String mPropertyName = "";
        private String mPropertyValue = "";
        private int mLabel = 0;
        private CharSequence mCoercedLabel = "";
        private int mIcon = 0;
        private int mPriority = 0;
        private boolean mEnabled = false;
        private Set<String> mAddedItems = new HashSet();

        public int getLabelRes() {
            return this.mLabel;
        }

        public void setLabelRes(int label) {
            this.mLabel = label;
            this.mAddedItems.add("label");
        }

        public boolean hasLabel() {
            return this.mAddedItems.contains("label");
        }

        public CharSequence getCoercedLabel() {
            return this.mCoercedLabel;
        }

        public void setCoercedLabel(CharSequence coercedLabel) {
            this.mCoercedLabel = coercedLabel;
            this.mAddedItems.add(COERCED_LABEL);
        }

        public boolean hasCoercedLabel() {
            return this.mAddedItems.contains(COERCED_LABEL);
        }

        public int getIconRes() {
            return this.mIcon;
        }

        public void setIconRes(int icon) {
            this.mIcon = icon;
            this.mAddedItems.add("icon");
        }

        public boolean hasIcon() {
            return this.mAddedItems.contains("icon");
        }

        public boolean getEnabled() {
            return this.mEnabled;
        }

        public void setEnabled(boolean enabled) {
            this.mEnabled = enabled;
            this.mAddedItems.add("enabled");
        }

        public boolean hasEnabled() {
            return this.mAddedItems.contains("enabled");
        }

        public int getPriority() {
            return this.mPriority;
        }

        public void setPriority(int priority) {
            this.mPriority = priority;
            this.mAddedItems.add("priority");
        }

        public boolean hasPriority() {
            return this.mAddedItems.contains("priority");
        }

        public String getType() {
            return this.mType;
        }

        public void setType(String type) {
            this.mType = type;
            this.mAddedItems.add("type");
        }

        public boolean hasType() {
            return this.mAddedItems.contains("type");
        }

        public String getValue() {
            return this.mValue;
        }

        public void setValue(String value) {
            this.mValue = value;
            this.mAddedItems.add("value");
        }

        public boolean hasValue() {
            return this.mAddedItems.contains("value");
        }

        public long getMinValue() {
            try {
                return Long.parseLong(this.mMinValue);
            } catch (NumberFormatException e) {
                return -1L;
            }
        }

        public void setMinValue(String value) {
            this.mMinValue = value;
            this.mAddedItems.add(MIN_VALUE);
        }

        public boolean hasMinValue() {
            return this.mAddedItems.contains(MIN_VALUE);
        }

        public long getMaxValue() {
            try {
                return Long.parseLong(this.mMaxValue);
            } catch (NumberFormatException e) {
                return -1L;
            }
        }

        public void setMaxValue(String value) {
            this.mMaxValue = value;
            this.mAddedItems.add(MAX_VALUE);
        }

        public boolean hasMaxValue() {
            return this.mAddedItems.contains(MAX_VALUE);
        }

        public String getPropertyName() {
            return this.mPropertyName;
        }

        public void setPropertyName(String propertyName) {
            this.mPropertyName = propertyName;
            this.mAddedItems.add(PROPERTY_NAME);
        }

        public boolean hasPropertyName() {
            return this.mAddedItems.contains(PROPERTY_NAME);
        }

        public String getPropertyValue() {
            return this.mPropertyValue;
        }

        public void setPropertyValue(String propertyValue) {
            this.mPropertyValue = propertyValue;
            this.mAddedItems.add(PROPERTY_VALUE);
        }

        public boolean hasPropertyValue() {
            return this.mAddedItems.contains(PROPERTY_VALUE);
        }

        public String toString() {
            StringBuilder sb = new StringBuilder("PolicyInfo {");
            if (hasType()) {
                sb.append(" Type: ").append(this.mType);
            }
            if (hasValue()) {
                sb.append(" Value: ").append(this.mValue);
            }
            if (hasMinValue()) {
                sb.append(" MinValue: ").append(this.mMinValue);
            }
            if (hasMaxValue()) {
                sb.append(" MaxValue: ").append(this.mMaxValue);
            }
            if (hasPropertyName()) {
                sb.append(" PropertyName: ").append(this.mPropertyName);
            }
            if (hasPropertyValue()) {
                sb.append(" PropertyValue: ").append(this.mPropertyValue);
            }
            if (hasPriority()) {
                sb.append(" Priority: ").append(this.mPriority);
            }
            if (hasLabel()) {
                sb.append(" LabelRes: ").append(this.mLabel);
            }
            if (hasCoercedLabel()) {
                sb.append(" CoercedLabel: ").append(this.mCoercedLabel);
            }
            if (hasIcon()) {
                sb.append(" Icon: ").append(this.mIcon);
            }
            sb.append(" }");
            return sb.toString();
        }
    }
}
