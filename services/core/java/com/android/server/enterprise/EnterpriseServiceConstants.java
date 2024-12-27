package com.android.server.enterprise;

import com.android.internal.util.FrameworkStatsLog;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
