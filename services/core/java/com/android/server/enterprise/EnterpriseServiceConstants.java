package com.android.server.enterprise;

import com.android.internal.util.FrameworkStatsLog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/* compiled from: qb/89523975 b19e8d3036bb0bb04c0b123e55579fdc5d41bbd9c06260ba21f1b25f8ce00bef */
public abstract class EnterpriseServiceConstants {
    public static final List KEY_CUSTOMIZE_KEYCODE =
            Collections.unmodifiableList(
                    new ArrayList() { // from class:
                                      // com.android.server.enterprise.EnterpriseServiceConstants.1
                        {
                            add(1015);
                            add(1079);
                            add(
                                    Integer.valueOf(
                                            FrameworkStatsLog
                                                    .DEVICE_POLICY_EVENT__EVENT_ID__CREDENTIAL_MANAGEMENT_APP_REMOVED));
                            add(3);
                            add(4);
                            add(26);
                            add(24);
                            add(25);
                            add(79);
                        }
                    });
    public static final List KEY_CUSTOMIZE_KEYPRESS =
            Collections.unmodifiableList(
                    new ArrayList() { // from class:
                                      // com.android.server.enterprise.EnterpriseServiceConstants.2
                        {
                            add(Integer.toString(1));
                            add(Integer.toString(4));
                            add(Integer.toString(8));
                        }
                    });
    public static final List KEY_CUSTOMIZE_INTENT_KEYPRESS =
            Collections.unmodifiableList(
                    new ArrayList() { // from class:
                                      // com.android.server.enterprise.EnterpriseServiceConstants.3
                        {
                            add(3);
                            add(4);
                            add(8);
                        }
                    });
}
