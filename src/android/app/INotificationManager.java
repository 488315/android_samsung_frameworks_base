package android.app;

import android.Manifest;
import android.app.ICallNotificationEventCallback;
import android.app.ITransientNotification;
import android.app.ITransientNotificationCallback;
import android.app.NotificationManager;
import android.content.AttributionSource;
import android.content.ComponentName;
import android.content.pm.ParceledListSlice;
import android.hardware.display.SemWifiDisplayParameter;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.ParcelFileDescriptor;
import android.os.PermissionEnforcer;
import android.os.RemoteException;
import android.os.UserHandle;
import android.sec.enterprise.content.SecContentProviderURI;
import android.service.notification.Adjustment;
import android.service.notification.Condition;
import android.service.notification.IConditionProvider;
import android.service.notification.INotificationListener;
import android.service.notification.NotificationListenerFilter;
import android.service.notification.StatusBarNotification;
import android.service.notification.ZenDeviceEffects;
import android.service.notification.ZenModeConfig;
import android.service.notification.ZenPolicy;
import android.text.TextUtils;
import com.samsung.android.edge.EdgeLightingPolicy;
import com.samsung.android.edge.SemEdgeLightingInfo;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public interface INotificationManager extends IInterface {

    public static class Default implements INotificationManager {
        @Override // android.app.INotificationManager
        public String addAutomaticZenRule(AutomaticZenRule automaticZenRule, String str, boolean z) throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public void addReplyHistory(int i, String str, String str2, int i2, String str3, String str4) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public boolean addWearableAppToList(int i, String str) throws RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public void allowAssistantAdjustment(String str) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public boolean appCanBePromoted(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public void applyAdjustmentFromAssistant(INotificationListener iNotificationListener, Adjustment adjustment) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void applyAdjustmentsFromAssistant(INotificationListener iNotificationListener, List<Adjustment> list) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void applyEnqueuedAdjustmentFromAssistant(INotificationListener iNotificationListener, Adjustment adjustment) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void applyRestore(byte[] bArr, int i) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public boolean areBubblesAllowed(String str) throws RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public boolean areBubblesEnabled(UserHandle userHandle) throws RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public boolean areChannelsBypassingDnd() throws RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public boolean areNotificationsEnabled(String str) throws RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public boolean areNotificationsEnabledForPackage(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return null;
        }

        @Override // android.app.INotificationManager
        public void bindEdgeLightingService(IBinder iBinder, int i, ComponentName componentName) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public boolean canAppBypassDnd(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public boolean canBePromoted(String str) throws RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public boolean canNotifyAsPackage(String str, String str2, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public boolean canShowBadge(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public boolean canUseFullScreenIntent(AttributionSource attributionSource) throws RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public void cancelAllNotifications(String str, int i) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void cancelNotificationByEdge(String str, String str2, int i, int i2, String str3) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void cancelNotificationByGroupKey(String str, String str2, int i, int i2, String str3, String str4) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void cancelNotificationFromListener(INotificationListener iNotificationListener, String str, String str2, int i) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void cancelNotificationWithTag(String str, String str2, String str3, int i, int i2) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void cancelNotificationsFromListener(INotificationListener iNotificationListener, String[] strArr) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void cancelToast(String str, IBinder iBinder) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void cleanUpCallersAfter(long j) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void clearData(String str, int i, boolean z) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void clearRequestedListenerHints(INotificationListener iNotificationListener) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void createConversationNotificationChannelForPackage(String str, int i, NotificationChannel notificationChannel, String str2) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public NotificationChannel createConversationNotificationChannelForPackageFromPrivilegedListener(INotificationListener iNotificationListener, String str, UserHandle userHandle, String str2, String str3) throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public void createNotificationChannelGroups(String str, ParceledListSlice parceledListSlice) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void createNotificationChannels(String str, ParceledListSlice parceledListSlice) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void createNotificationChannelsForPackage(String str, int i, ParceledListSlice parceledListSlice) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void deleteNotificationChannel(String str, String str2) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void deleteNotificationChannelGroup(String str, String str2) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void deleteNotificationHistoryItem(String str, int i, long j) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void disable(int i, String str, IBinder iBinder) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void disableEdgeLightingNotification(String str, boolean z) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void disallowAssistantAdjustment(String str) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public boolean dispatchDelayedWakeUpAndBlocked(int i, String str, String str2) throws RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public boolean dispatchDelayedWakelockAndBlocked(int i, String str, String str2, int i2) throws RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public void enqueueEdgeNotification(String str, String str2, int i, Bundle bundle, int i2) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void enqueueNotificationWithTag(String str, String str2, String str3, int i, Notification notification, int i2) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public boolean enqueueTextToast(String str, IBinder iBinder, CharSequence charSequence, int i, boolean z, int i2, ITransientNotificationCallback iTransientNotificationCallback) throws RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public void enqueueTextToastForDex(String str, IBinder iBinder, CharSequence charSequence, int i, boolean z, int i2, ITransientNotificationCallback iTransientNotificationCallback, String str2, int i3) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public boolean enqueueToast(String str, IBinder iBinder, ITransientNotification iTransientNotification, int i, boolean z, int i2) throws RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public void enqueueToastForDex(String str, IBinder iBinder, ITransientNotification iTransientNotification, int i, boolean z, int i2, String str2, int i3) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void finishToken(String str, IBinder iBinder) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public StatusBarNotification[] getActiveNotifications(String str) throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public ParceledListSlice getActiveNotificationsFromListener(INotificationListener iNotificationListener, String[] strArr, int i) throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public StatusBarNotification[] getActiveNotificationsWithAttribution(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public String[] getAdjustmentDeniedPackages(String str) throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public int getAllNotificationListenersCount() throws RemoteException {
            return 0;
        }

        @Override // android.app.INotificationManager
        public int[] getAllowedAdjustmentKeyTypes() throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public List<String> getAllowedAssistantAdjustments(String str) throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public ComponentName getAllowedNotificationAssistant() throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public ComponentName getAllowedNotificationAssistantForUser(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public List<String> getAllowedOngoingActivityAppList() throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public ParceledListSlice getAppActiveNotifications(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public int getAppNotificationSettingStatus(String str) throws RemoteException {
            return 0;
        }

        @Override // android.app.INotificationManager
        public int getAppsBypassingDndCount(int i) throws RemoteException {
            return 0;
        }

        @Override // android.app.INotificationManager
        public AutomaticZenRule getAutomaticZenRule(String str) throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public int getAutomaticZenRuleState(String str) throws RemoteException {
            return 0;
        }

        @Override // android.app.INotificationManager
        public ParceledListSlice getAutomaticZenRules() throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public byte[] getBackupPayload(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public List<String> getBlockInfoOfNotificationsForOverflow(String str) throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public int getBlockedAppCount(int i) throws RemoteException {
            return 0;
        }

        @Override // android.app.INotificationManager
        public int getBlockedChannelCount(String str, int i) throws RemoteException {
            return 0;
        }

        @Override // android.app.INotificationManager
        public int getBubblePreferenceForPackage(String str, int i) throws RemoteException {
            return 0;
        }

        @Override // android.app.INotificationManager
        public NotificationManager.Policy getConsolidatedNotificationPolicy() throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public NotificationChannel getConversationNotificationChannel(String str, int i, String str2, String str3, boolean z, String str4) throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public ParceledListSlice getConversations(boolean z) throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public ParceledListSlice getConversationsForPackage(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public ComponentName getDefaultNotificationAssistant() throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public ZenPolicy getDefaultZenPolicy() throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public int getDeletedChannelCount(String str, int i) throws RemoteException {
            return 0;
        }

        @Override // android.app.INotificationManager
        public int getEdgeLightingSettingState(String str, int i) throws RemoteException {
            return 0;
        }

        @Override // android.app.INotificationManager
        public int getEdgeLightingState() throws RemoteException {
            return 0;
        }

        @Override // android.app.INotificationManager
        public ComponentName getEffectsSuppressor() throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public List<String> getEnabledNotificationListenerPackages() throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public List<ComponentName> getEnabledNotificationListeners(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public int getHintsFromListener(INotificationListener iNotificationListener) throws RemoteException {
            return 0;
        }

        @Override // android.app.INotificationManager
        public int getHintsFromListenerNoToken() throws RemoteException {
            return 0;
        }

        @Override // android.app.INotificationManager
        public StatusBarNotification[] getHistoricalNotifications(String str, int i, boolean z) throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public StatusBarNotification[] getHistoricalNotificationsWithAttribution(String str, String str2, int i, boolean z) throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public int getInterruptionFilterFromListener(INotificationListener iNotificationListener) throws RemoteException {
            return 0;
        }

        @Override // android.app.INotificationManager
        public NotificationListenerFilter getListenerFilter(ComponentName componentName, int i) throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public int getLockScreenNotificationVisibilityForPackage(String str, int i) throws RemoteException {
            return 0;
        }

        @Override // android.app.INotificationManager
        public boolean getNotificationAlertsEnabledForPackage(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public NotificationChannel getNotificationChannel(String str, int i, String str2, String str3) throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public NotificationChannel getNotificationChannelForPackage(String str, int i, String str2, String str3, boolean z) throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public NotificationChannelGroup getNotificationChannelGroup(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public NotificationChannelGroup getNotificationChannelGroupForPackage(String str, String str2, int i) throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public ParceledListSlice getNotificationChannelGroups(String str) throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public ParceledListSlice getNotificationChannelGroupsForPackage(String str, int i, boolean z) throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public ParceledListSlice getNotificationChannelGroupsFromPrivilegedListener(INotificationListener iNotificationListener, String str, UserHandle userHandle) throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public ParceledListSlice getNotificationChannelGroupsWithoutChannels(String str) throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public ParceledListSlice getNotificationChannels(String str, String str2, int i) throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public ParceledListSlice getNotificationChannelsBypassingDnd(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public ParceledListSlice getNotificationChannelsForPackage(String str, int i, boolean z) throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public ParceledListSlice getNotificationChannelsFromPrivilegedListener(INotificationListener iNotificationListener, String str, UserHandle userHandle) throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public String getNotificationDelegate(String str) throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public NotificationHistory getNotificationHistory(String str, String str2) throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public List<Bundle> getNotificationHistoryDataForPackage(String str, String str2, int i, String str3, String str4, int i2) throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public NotificationHistory getNotificationHistoryForPackage(String str, String str2, int i, String str3, String str4, int i2) throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public NotificationManager.Policy getNotificationPolicy(String str) throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public int getNotificationSettingStatus(boolean z) throws RemoteException {
            return 0;
        }

        @Override // android.app.INotificationManager
        public int getNotificationSoundStatus(String str) throws RemoteException {
            return 0;
        }

        @Override // android.app.INotificationManager
        public int getNumNotificationChannelsForPackage(String str, int i, boolean z) throws RemoteException {
            return 0;
        }

        @Override // android.app.INotificationManager
        public int getPackageImportance(String str) throws RemoteException {
            return 0;
        }

        @Override // android.app.INotificationManager
        public ParceledListSlice getPackagesBypassingDnd(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public List<String> getPackagesWithAnyChannels(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public NotificationChannelGroup getPopulatedNotificationChannelGroupForPackage(String str, int i, String str2, boolean z) throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public boolean getPrivateNotificationsAllowed() throws RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public ParceledListSlice getRecentBlockedNotificationChannelGroupsForPackage(String str, int i) throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public int getRuleInstanceCount(ComponentName componentName) throws RemoteException {
            return 0;
        }

        @Override // android.app.INotificationManager
        public ParceledListSlice getSnoozedNotificationsFromListener(INotificationListener iNotificationListener, int i) throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public List<String> getUnsupportedAdjustmentTypes() throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public List<String> getWearableAppList(int i) throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public int getZenMode() throws RemoteException {
            return 0;
        }

        @Override // android.app.INotificationManager
        public ZenModeConfig getZenModeConfig() throws RemoteException {
            return null;
        }

        @Override // android.app.INotificationManager
        public boolean hasEnabledNotificationListener(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public boolean hasSentValidBubble(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public boolean hasSentValidMsg(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public boolean hasUserDemotedInvalidMsgApp(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public void incrementCounter(String str) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public boolean isAdjustmentSupportedForPackage(String str, String str2) throws RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public boolean isAlertsAllowed(String str, int i, String str2, int i2) throws RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public boolean isAllowNotificationPopUpForPackage(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public boolean isEdgeLightingAllowed(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public boolean isEdgeLightingNotificationAllowed(String str) throws RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public boolean isImportanceLocked(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public boolean isInCall(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public boolean isInInvalidMsgState(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public boolean isNotificationAssistantAccessGranted(ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public boolean isNotificationListenerAccessGranted(ComponentName componentName) throws RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public boolean isNotificationListenerAccessGrantedForUser(ComponentName componentName, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public boolean isNotificationPolicyAccessGranted(String str) throws RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public boolean isNotificationPolicyAccessGrantedForPackage(String str) throws RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public int isNotificationTurnedOff(String str, int i) throws RemoteException {
            return 0;
        }

        @Override // android.app.INotificationManager
        public boolean isOngoingActivityAllowed(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public boolean isPackageEnabled(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public boolean isPackagePaused(String str) throws RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public boolean isPermissionFixed(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public boolean isReminderEnabled(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public boolean isSubDisplayNotificationAllowed(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public boolean isSystemConditionProviderEnabled(String str) throws RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public boolean matchesCallFilter(Bundle bundle) throws RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public void migrateNotificationFilter(INotificationListener iNotificationListener, int i, List<String> list) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void notifyConditions(String str, IConditionProvider iConditionProvider, Condition[] conditionArr) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public boolean onlyHasDefaultChannel(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public long pullStats(long j, int i, boolean z, List<ParcelFileDescriptor> list) throws RemoteException {
            return 0L;
        }

        @Override // android.app.INotificationManager
        public void registerCallNotificationEventListener(String str, UserHandle userHandle, ICallNotificationEventCallback iCallNotificationEventCallback) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void registerEdgeLightingListener(IBinder iBinder, ComponentName componentName) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void registerListener(INotificationListener iNotificationListener, ComponentName componentName, int i) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void registerNotificationListener(ComponentName componentName, int i, boolean z) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public boolean removeAutomaticZenRule(String str, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public boolean removeAutomaticZenRules(String str, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public void removeEdgeNotification(String str, int i, Bundle bundle, int i2) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public boolean removeWearableAppFromList(int i, String str) throws RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public void requestBindListener(ComponentName componentName) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void requestBindProvider(ComponentName componentName) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void requestHintsFromListener(INotificationListener iNotificationListener, int i) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void requestInterruptionFilterFromListener(INotificationListener iNotificationListener, int i) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public boolean requestListenerHintsForWearable(int i) throws RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public void requestUnbindListener(INotificationListener iNotificationListener) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void requestUnbindListenerComponent(ComponentName componentName) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void requestUnbindProvider(IConditionProvider iConditionProvider) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void resetDefaultAllowEdgeLighting() throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void resetDefaultAllowOngoingActivity() throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void resetDefaultEdgeLightingState() throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void setAdjustmentSupportedForPackage(String str, String str2, boolean z) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void setAdjustmentTypeSupportedState(INotificationListener iNotificationListener, String str, boolean z) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void setAllowEdgeLighting(String str, int i, boolean z) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void setAllowNotificationPopUpForPackage(String str, int i, boolean z) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void setAllowOngoingActivity(String str, int i, boolean z) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void setAllowSubDisplayNotification(String str, int i, boolean z) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void setAppBypassDnd(String str, int i, boolean z) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void setAssistantAdjustmentKeyTypeState(int i, boolean z) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void setAutomaticZenRuleState(String str, Condition condition) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void setBubblesAllowed(String str, int i, int i2) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void setCanBePromoted(String str, int i, boolean z, boolean z2) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void setEdgeLightingState(String str, int i, int i2) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void setHideSilentStatusIcons(boolean z) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void setInterruptionFilter(String str, int i, boolean z) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void setInvalidMsgAppDemoted(String str, int i, boolean z) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void setListenerFilter(ComponentName componentName, int i, NotificationListenerFilter notificationListenerFilter) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void setLockScreenNotificationVisibilityForPackage(String str, int i, int i2) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void setManualZenRuleDeviceEffects(ZenDeviceEffects zenDeviceEffects) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void setNASMigrationDoneAndResetDefault(int i, boolean z) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void setNotificationAlertsEnabledForPackage(String str, int i, boolean z) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void setNotificationAssistantAccessGranted(ComponentName componentName, boolean z) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void setNotificationAssistantAccessGrantedForUser(ComponentName componentName, int i, boolean z) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void setNotificationDelegate(String str, String str2) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void setNotificationListenerAccessGranted(ComponentName componentName, boolean z, boolean z2) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void setNotificationListenerAccessGrantedForUser(ComponentName componentName, int i, boolean z, boolean z2) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void setNotificationPolicy(String str, NotificationManager.Policy policy, boolean z) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void setNotificationPolicyAccessGranted(String str, boolean z) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void setNotificationPolicyAccessGrantedForUser(String str, int i, boolean z) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public boolean setNotificationTurnOff(String str, int i) throws RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public void setNotificationsEnabledForPackage(String str, int i, boolean z) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void setNotificationsEnabledWithImportanceLockForPackage(String str, int i, boolean z) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void setNotificationsShownFromListener(INotificationListener iNotificationListener, String[] strArr) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void setOnNotificationPostedTrimFromListener(INotificationListener iNotificationListener, int i) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void setPrivateNotificationsAllowed(boolean z) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void setReminderEnabled(int i, boolean z, List<String> list) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void setReminderEnabledForPackage(String str, int i, boolean z) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void setRestoreBlockListForSS(List<String> list) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void setShowBadge(String str, int i, boolean z) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void setToastRateLimitingEnabled(boolean z) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public boolean setWearableAppList(int i, List<String> list) throws RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public void setZenMode(int i, Uri uri, String str, boolean z) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public boolean shouldHideSilentStatusIcons(String str) throws RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public void silenceNotificationSound() throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void snoozeNotificationUntilContextFromListener(INotificationListener iNotificationListener, String str, String str2) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void snoozeNotificationUntilFromListener(INotificationListener iNotificationListener, String str, long j) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void startEdgeLighting(String str, SemEdgeLightingInfo semEdgeLightingInfo, IBinder iBinder) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void stopEdgeLighting(String str, IBinder iBinder) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void unbindEdgeLightingService(IBinder iBinder, String str) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void unlockAllNotificationChannels() throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void unlockNotificationChannel(String str, int i, String str2) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void unregisterCallNotificationEventListener(String str, UserHandle userHandle, ICallNotificationEventCallback iCallNotificationEventCallback) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void unregisterEdgeLightingListener(IBinder iBinder, String str) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void unregisterListener(INotificationListener iNotificationListener, int i) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void unsnoozeNotificationFromAssistant(INotificationListener iNotificationListener, String str) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void unsnoozeNotificationFromSystemListener(INotificationListener iNotificationListener, String str) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public boolean updateAutomaticZenRule(String str, AutomaticZenRule automaticZenRule, boolean z) throws RemoteException {
            return false;
        }

        @Override // android.app.INotificationManager
        public void updateCancelEvent(int i, String str, boolean z) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void updateEdgeLightingPackageList(String str, List<String> list) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void updateEdgeLightingPolicy(String str, EdgeLightingPolicy edgeLightingPolicy) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void updateNotificationChannelForPackage(String str, int i, NotificationChannel notificationChannel) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void updateNotificationChannelFromPrivilegedListener(INotificationListener iNotificationListener, String str, UserHandle userHandle, NotificationChannel notificationChannel) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void updateNotificationChannelGroupForPackage(String str, int i, NotificationChannelGroup notificationChannelGroup) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void updateNotificationChannelGroupFromPrivilegedListener(INotificationListener iNotificationListener, String str, UserHandle userHandle, NotificationChannelGroup notificationChannelGroup) throws RemoteException {
        }

        @Override // android.app.INotificationManager
        public void updateNotificationChannels(String str, ParceledListSlice parceledListSlice) throws RemoteException {
        }
    }

    String addAutomaticZenRule(AutomaticZenRule automaticZenRule, String str, boolean z) throws RemoteException;

    void addReplyHistory(int i, String str, String str2, int i2, String str3, String str4) throws RemoteException;

    boolean addWearableAppToList(int i, String str) throws RemoteException;

    void allowAssistantAdjustment(String str) throws RemoteException;

    boolean appCanBePromoted(String str, int i) throws RemoteException;

    void applyAdjustmentFromAssistant(INotificationListener iNotificationListener, Adjustment adjustment) throws RemoteException;

    void applyAdjustmentsFromAssistant(INotificationListener iNotificationListener, List<Adjustment> list) throws RemoteException;

    void applyEnqueuedAdjustmentFromAssistant(INotificationListener iNotificationListener, Adjustment adjustment) throws RemoteException;

    void applyRestore(byte[] bArr, int i) throws RemoteException;

    boolean areBubblesAllowed(String str) throws RemoteException;

    boolean areBubblesEnabled(UserHandle userHandle) throws RemoteException;

    boolean areChannelsBypassingDnd() throws RemoteException;

    boolean areNotificationsEnabled(String str) throws RemoteException;

    boolean areNotificationsEnabledForPackage(String str, int i) throws RemoteException;

    void bindEdgeLightingService(IBinder iBinder, int i, ComponentName componentName) throws RemoteException;

    boolean canAppBypassDnd(String str, int i) throws RemoteException;

    boolean canBePromoted(String str) throws RemoteException;

    boolean canNotifyAsPackage(String str, String str2, int i) throws RemoteException;

    boolean canShowBadge(String str, int i) throws RemoteException;

    boolean canUseFullScreenIntent(AttributionSource attributionSource) throws RemoteException;

    void cancelAllNotifications(String str, int i) throws RemoteException;

    void cancelNotificationByEdge(String str, String str2, int i, int i2, String str3) throws RemoteException;

    void cancelNotificationByGroupKey(String str, String str2, int i, int i2, String str3, String str4) throws RemoteException;

    void cancelNotificationFromListener(INotificationListener iNotificationListener, String str, String str2, int i) throws RemoteException;

    void cancelNotificationWithTag(String str, String str2, String str3, int i, int i2) throws RemoteException;

    void cancelNotificationsFromListener(INotificationListener iNotificationListener, String[] strArr) throws RemoteException;

    void cancelToast(String str, IBinder iBinder) throws RemoteException;

    void cleanUpCallersAfter(long j) throws RemoteException;

    void clearData(String str, int i, boolean z) throws RemoteException;

    void clearRequestedListenerHints(INotificationListener iNotificationListener) throws RemoteException;

    void createConversationNotificationChannelForPackage(String str, int i, NotificationChannel notificationChannel, String str2) throws RemoteException;

    NotificationChannel createConversationNotificationChannelForPackageFromPrivilegedListener(INotificationListener iNotificationListener, String str, UserHandle userHandle, String str2, String str3) throws RemoteException;

    void createNotificationChannelGroups(String str, ParceledListSlice parceledListSlice) throws RemoteException;

    void createNotificationChannels(String str, ParceledListSlice parceledListSlice) throws RemoteException;

    void createNotificationChannelsForPackage(String str, int i, ParceledListSlice parceledListSlice) throws RemoteException;

    void deleteNotificationChannel(String str, String str2) throws RemoteException;

    void deleteNotificationChannelGroup(String str, String str2) throws RemoteException;

    void deleteNotificationHistoryItem(String str, int i, long j) throws RemoteException;

    void disable(int i, String str, IBinder iBinder) throws RemoteException;

    void disableEdgeLightingNotification(String str, boolean z) throws RemoteException;

    void disallowAssistantAdjustment(String str) throws RemoteException;

    boolean dispatchDelayedWakeUpAndBlocked(int i, String str, String str2) throws RemoteException;

    boolean dispatchDelayedWakelockAndBlocked(int i, String str, String str2, int i2) throws RemoteException;

    void enqueueEdgeNotification(String str, String str2, int i, Bundle bundle, int i2) throws RemoteException;

    void enqueueNotificationWithTag(String str, String str2, String str3, int i, Notification notification, int i2) throws RemoteException;

    boolean enqueueTextToast(String str, IBinder iBinder, CharSequence charSequence, int i, boolean z, int i2, ITransientNotificationCallback iTransientNotificationCallback) throws RemoteException;

    void enqueueTextToastForDex(String str, IBinder iBinder, CharSequence charSequence, int i, boolean z, int i2, ITransientNotificationCallback iTransientNotificationCallback, String str2, int i3) throws RemoteException;

    boolean enqueueToast(String str, IBinder iBinder, ITransientNotification iTransientNotification, int i, boolean z, int i2) throws RemoteException;

    void enqueueToastForDex(String str, IBinder iBinder, ITransientNotification iTransientNotification, int i, boolean z, int i2, String str2, int i3) throws RemoteException;

    void finishToken(String str, IBinder iBinder) throws RemoteException;

    StatusBarNotification[] getActiveNotifications(String str) throws RemoteException;

    ParceledListSlice getActiveNotificationsFromListener(INotificationListener iNotificationListener, String[] strArr, int i) throws RemoteException;

    StatusBarNotification[] getActiveNotificationsWithAttribution(String str, String str2) throws RemoteException;

    String[] getAdjustmentDeniedPackages(String str) throws RemoteException;

    int getAllNotificationListenersCount() throws RemoteException;

    int[] getAllowedAdjustmentKeyTypes() throws RemoteException;

    List<String> getAllowedAssistantAdjustments(String str) throws RemoteException;

    ComponentName getAllowedNotificationAssistant() throws RemoteException;

    ComponentName getAllowedNotificationAssistantForUser(int i) throws RemoteException;

    List<String> getAllowedOngoingActivityAppList() throws RemoteException;

    ParceledListSlice getAppActiveNotifications(String str, int i) throws RemoteException;

    int getAppNotificationSettingStatus(String str) throws RemoteException;

    int getAppsBypassingDndCount(int i) throws RemoteException;

    AutomaticZenRule getAutomaticZenRule(String str) throws RemoteException;

    int getAutomaticZenRuleState(String str) throws RemoteException;

    ParceledListSlice getAutomaticZenRules() throws RemoteException;

    byte[] getBackupPayload(int i) throws RemoteException;

    List<String> getBlockInfoOfNotificationsForOverflow(String str) throws RemoteException;

    int getBlockedAppCount(int i) throws RemoteException;

    int getBlockedChannelCount(String str, int i) throws RemoteException;

    int getBubblePreferenceForPackage(String str, int i) throws RemoteException;

    NotificationManager.Policy getConsolidatedNotificationPolicy() throws RemoteException;

    NotificationChannel getConversationNotificationChannel(String str, int i, String str2, String str3, boolean z, String str4) throws RemoteException;

    ParceledListSlice getConversations(boolean z) throws RemoteException;

    ParceledListSlice getConversationsForPackage(String str, int i) throws RemoteException;

    ComponentName getDefaultNotificationAssistant() throws RemoteException;

    ZenPolicy getDefaultZenPolicy() throws RemoteException;

    int getDeletedChannelCount(String str, int i) throws RemoteException;

    int getEdgeLightingSettingState(String str, int i) throws RemoteException;

    int getEdgeLightingState() throws RemoteException;

    ComponentName getEffectsSuppressor() throws RemoteException;

    List<String> getEnabledNotificationListenerPackages() throws RemoteException;

    List<ComponentName> getEnabledNotificationListeners(int i) throws RemoteException;

    int getHintsFromListener(INotificationListener iNotificationListener) throws RemoteException;

    int getHintsFromListenerNoToken() throws RemoteException;

    StatusBarNotification[] getHistoricalNotifications(String str, int i, boolean z) throws RemoteException;

    StatusBarNotification[] getHistoricalNotificationsWithAttribution(String str, String str2, int i, boolean z) throws RemoteException;

    int getInterruptionFilterFromListener(INotificationListener iNotificationListener) throws RemoteException;

    NotificationListenerFilter getListenerFilter(ComponentName componentName, int i) throws RemoteException;

    int getLockScreenNotificationVisibilityForPackage(String str, int i) throws RemoteException;

    boolean getNotificationAlertsEnabledForPackage(String str, int i) throws RemoteException;

    NotificationChannel getNotificationChannel(String str, int i, String str2, String str3) throws RemoteException;

    NotificationChannel getNotificationChannelForPackage(String str, int i, String str2, String str3, boolean z) throws RemoteException;

    NotificationChannelGroup getNotificationChannelGroup(String str, String str2) throws RemoteException;

    NotificationChannelGroup getNotificationChannelGroupForPackage(String str, String str2, int i) throws RemoteException;

    ParceledListSlice getNotificationChannelGroups(String str) throws RemoteException;

    ParceledListSlice getNotificationChannelGroupsForPackage(String str, int i, boolean z) throws RemoteException;

    ParceledListSlice getNotificationChannelGroupsFromPrivilegedListener(INotificationListener iNotificationListener, String str, UserHandle userHandle) throws RemoteException;

    ParceledListSlice getNotificationChannelGroupsWithoutChannels(String str) throws RemoteException;

    ParceledListSlice getNotificationChannels(String str, String str2, int i) throws RemoteException;

    ParceledListSlice getNotificationChannelsBypassingDnd(String str, int i) throws RemoteException;

    ParceledListSlice getNotificationChannelsForPackage(String str, int i, boolean z) throws RemoteException;

    ParceledListSlice getNotificationChannelsFromPrivilegedListener(INotificationListener iNotificationListener, String str, UserHandle userHandle) throws RemoteException;

    String getNotificationDelegate(String str) throws RemoteException;

    NotificationHistory getNotificationHistory(String str, String str2) throws RemoteException;

    List<Bundle> getNotificationHistoryDataForPackage(String str, String str2, int i, String str3, String str4, int i2) throws RemoteException;

    NotificationHistory getNotificationHistoryForPackage(String str, String str2, int i, String str3, String str4, int i2) throws RemoteException;

    NotificationManager.Policy getNotificationPolicy(String str) throws RemoteException;

    int getNotificationSettingStatus(boolean z) throws RemoteException;

    int getNotificationSoundStatus(String str) throws RemoteException;

    int getNumNotificationChannelsForPackage(String str, int i, boolean z) throws RemoteException;

    int getPackageImportance(String str) throws RemoteException;

    ParceledListSlice getPackagesBypassingDnd(int i) throws RemoteException;

    List<String> getPackagesWithAnyChannels(int i) throws RemoteException;

    NotificationChannelGroup getPopulatedNotificationChannelGroupForPackage(String str, int i, String str2, boolean z) throws RemoteException;

    boolean getPrivateNotificationsAllowed() throws RemoteException;

    ParceledListSlice getRecentBlockedNotificationChannelGroupsForPackage(String str, int i) throws RemoteException;

    int getRuleInstanceCount(ComponentName componentName) throws RemoteException;

    ParceledListSlice getSnoozedNotificationsFromListener(INotificationListener iNotificationListener, int i) throws RemoteException;

    List<String> getUnsupportedAdjustmentTypes() throws RemoteException;

    List<String> getWearableAppList(int i) throws RemoteException;

    int getZenMode() throws RemoteException;

    ZenModeConfig getZenModeConfig() throws RemoteException;

    boolean hasEnabledNotificationListener(String str, int i) throws RemoteException;

    boolean hasSentValidBubble(String str, int i) throws RemoteException;

    boolean hasSentValidMsg(String str, int i) throws RemoteException;

    boolean hasUserDemotedInvalidMsgApp(String str, int i) throws RemoteException;

    void incrementCounter(String str) throws RemoteException;

    boolean isAdjustmentSupportedForPackage(String str, String str2) throws RemoteException;

    boolean isAlertsAllowed(String str, int i, String str2, int i2) throws RemoteException;

    boolean isAllowNotificationPopUpForPackage(String str, int i) throws RemoteException;

    boolean isEdgeLightingAllowed(String str, int i) throws RemoteException;

    boolean isEdgeLightingNotificationAllowed(String str) throws RemoteException;

    boolean isImportanceLocked(String str, int i) throws RemoteException;

    boolean isInCall(String str, int i) throws RemoteException;

    boolean isInInvalidMsgState(String str, int i) throws RemoteException;

    boolean isNotificationAssistantAccessGranted(ComponentName componentName) throws RemoteException;

    boolean isNotificationListenerAccessGranted(ComponentName componentName) throws RemoteException;

    boolean isNotificationListenerAccessGrantedForUser(ComponentName componentName, int i) throws RemoteException;

    boolean isNotificationPolicyAccessGranted(String str) throws RemoteException;

    boolean isNotificationPolicyAccessGrantedForPackage(String str) throws RemoteException;

    int isNotificationTurnedOff(String str, int i) throws RemoteException;

    boolean isOngoingActivityAllowed(String str, int i) throws RemoteException;

    boolean isPackageEnabled(String str, int i) throws RemoteException;

    boolean isPackagePaused(String str) throws RemoteException;

    boolean isPermissionFixed(String str, int i) throws RemoteException;

    boolean isReminderEnabled(String str, int i) throws RemoteException;

    boolean isSubDisplayNotificationAllowed(String str, int i) throws RemoteException;

    boolean isSystemConditionProviderEnabled(String str) throws RemoteException;

    boolean matchesCallFilter(Bundle bundle) throws RemoteException;

    void migrateNotificationFilter(INotificationListener iNotificationListener, int i, List<String> list) throws RemoteException;

    void notifyConditions(String str, IConditionProvider iConditionProvider, Condition[] conditionArr) throws RemoteException;

    boolean onlyHasDefaultChannel(String str, int i) throws RemoteException;

    long pullStats(long j, int i, boolean z, List<ParcelFileDescriptor> list) throws RemoteException;

    void registerCallNotificationEventListener(String str, UserHandle userHandle, ICallNotificationEventCallback iCallNotificationEventCallback) throws RemoteException;

    void registerEdgeLightingListener(IBinder iBinder, ComponentName componentName) throws RemoteException;

    void registerListener(INotificationListener iNotificationListener, ComponentName componentName, int i) throws RemoteException;

    void registerNotificationListener(ComponentName componentName, int i, boolean z) throws RemoteException;

    boolean removeAutomaticZenRule(String str, boolean z) throws RemoteException;

    boolean removeAutomaticZenRules(String str, boolean z) throws RemoteException;

    void removeEdgeNotification(String str, int i, Bundle bundle, int i2) throws RemoteException;

    boolean removeWearableAppFromList(int i, String str) throws RemoteException;

    void requestBindListener(ComponentName componentName) throws RemoteException;

    void requestBindProvider(ComponentName componentName) throws RemoteException;

    void requestHintsFromListener(INotificationListener iNotificationListener, int i) throws RemoteException;

    void requestInterruptionFilterFromListener(INotificationListener iNotificationListener, int i) throws RemoteException;

    boolean requestListenerHintsForWearable(int i) throws RemoteException;

    void requestUnbindListener(INotificationListener iNotificationListener) throws RemoteException;

    void requestUnbindListenerComponent(ComponentName componentName) throws RemoteException;

    void requestUnbindProvider(IConditionProvider iConditionProvider) throws RemoteException;

    void resetDefaultAllowEdgeLighting() throws RemoteException;

    void resetDefaultAllowOngoingActivity() throws RemoteException;

    void resetDefaultEdgeLightingState() throws RemoteException;

    void setAdjustmentSupportedForPackage(String str, String str2, boolean z) throws RemoteException;

    void setAdjustmentTypeSupportedState(INotificationListener iNotificationListener, String str, boolean z) throws RemoteException;

    void setAllowEdgeLighting(String str, int i, boolean z) throws RemoteException;

    void setAllowNotificationPopUpForPackage(String str, int i, boolean z) throws RemoteException;

    void setAllowOngoingActivity(String str, int i, boolean z) throws RemoteException;

    void setAllowSubDisplayNotification(String str, int i, boolean z) throws RemoteException;

    void setAppBypassDnd(String str, int i, boolean z) throws RemoteException;

    void setAssistantAdjustmentKeyTypeState(int i, boolean z) throws RemoteException;

    void setAutomaticZenRuleState(String str, Condition condition) throws RemoteException;

    void setBubblesAllowed(String str, int i, int i2) throws RemoteException;

    void setCanBePromoted(String str, int i, boolean z, boolean z2) throws RemoteException;

    void setEdgeLightingState(String str, int i, int i2) throws RemoteException;

    void setHideSilentStatusIcons(boolean z) throws RemoteException;

    void setInterruptionFilter(String str, int i, boolean z) throws RemoteException;

    void setInvalidMsgAppDemoted(String str, int i, boolean z) throws RemoteException;

    void setListenerFilter(ComponentName componentName, int i, NotificationListenerFilter notificationListenerFilter) throws RemoteException;

    void setLockScreenNotificationVisibilityForPackage(String str, int i, int i2) throws RemoteException;

    void setManualZenRuleDeviceEffects(ZenDeviceEffects zenDeviceEffects) throws RemoteException;

    void setNASMigrationDoneAndResetDefault(int i, boolean z) throws RemoteException;

    void setNotificationAlertsEnabledForPackage(String str, int i, boolean z) throws RemoteException;

    void setNotificationAssistantAccessGranted(ComponentName componentName, boolean z) throws RemoteException;

    void setNotificationAssistantAccessGrantedForUser(ComponentName componentName, int i, boolean z) throws RemoteException;

    void setNotificationDelegate(String str, String str2) throws RemoteException;

    void setNotificationListenerAccessGranted(ComponentName componentName, boolean z, boolean z2) throws RemoteException;

    void setNotificationListenerAccessGrantedForUser(ComponentName componentName, int i, boolean z, boolean z2) throws RemoteException;

    void setNotificationPolicy(String str, NotificationManager.Policy policy, boolean z) throws RemoteException;

    void setNotificationPolicyAccessGranted(String str, boolean z) throws RemoteException;

    void setNotificationPolicyAccessGrantedForUser(String str, int i, boolean z) throws RemoteException;

    boolean setNotificationTurnOff(String str, int i) throws RemoteException;

    void setNotificationsEnabledForPackage(String str, int i, boolean z) throws RemoteException;

    void setNotificationsEnabledWithImportanceLockForPackage(String str, int i, boolean z) throws RemoteException;

    void setNotificationsShownFromListener(INotificationListener iNotificationListener, String[] strArr) throws RemoteException;

    void setOnNotificationPostedTrimFromListener(INotificationListener iNotificationListener, int i) throws RemoteException;

    void setPrivateNotificationsAllowed(boolean z) throws RemoteException;

    void setReminderEnabled(int i, boolean z, List<String> list) throws RemoteException;

    void setReminderEnabledForPackage(String str, int i, boolean z) throws RemoteException;

    void setRestoreBlockListForSS(List<String> list) throws RemoteException;

    void setShowBadge(String str, int i, boolean z) throws RemoteException;

    void setToastRateLimitingEnabled(boolean z) throws RemoteException;

    boolean setWearableAppList(int i, List<String> list) throws RemoteException;

    void setZenMode(int i, Uri uri, String str, boolean z) throws RemoteException;

    boolean shouldHideSilentStatusIcons(String str) throws RemoteException;

    void silenceNotificationSound() throws RemoteException;

    void snoozeNotificationUntilContextFromListener(INotificationListener iNotificationListener, String str, String str2) throws RemoteException;

    void snoozeNotificationUntilFromListener(INotificationListener iNotificationListener, String str, long j) throws RemoteException;

    void startEdgeLighting(String str, SemEdgeLightingInfo semEdgeLightingInfo, IBinder iBinder) throws RemoteException;

    void stopEdgeLighting(String str, IBinder iBinder) throws RemoteException;

    void unbindEdgeLightingService(IBinder iBinder, String str) throws RemoteException;

    void unlockAllNotificationChannels() throws RemoteException;

    void unlockNotificationChannel(String str, int i, String str2) throws RemoteException;

    void unregisterCallNotificationEventListener(String str, UserHandle userHandle, ICallNotificationEventCallback iCallNotificationEventCallback) throws RemoteException;

    void unregisterEdgeLightingListener(IBinder iBinder, String str) throws RemoteException;

    void unregisterListener(INotificationListener iNotificationListener, int i) throws RemoteException;

    void unsnoozeNotificationFromAssistant(INotificationListener iNotificationListener, String str) throws RemoteException;

    void unsnoozeNotificationFromSystemListener(INotificationListener iNotificationListener, String str) throws RemoteException;

    boolean updateAutomaticZenRule(String str, AutomaticZenRule automaticZenRule, boolean z) throws RemoteException;

    void updateCancelEvent(int i, String str, boolean z) throws RemoteException;

    void updateEdgeLightingPackageList(String str, List<String> list) throws RemoteException;

    void updateEdgeLightingPolicy(String str, EdgeLightingPolicy edgeLightingPolicy) throws RemoteException;

    void updateNotificationChannelForPackage(String str, int i, NotificationChannel notificationChannel) throws RemoteException;

    void updateNotificationChannelFromPrivilegedListener(INotificationListener iNotificationListener, String str, UserHandle userHandle, NotificationChannel notificationChannel) throws RemoteException;

    void updateNotificationChannelGroupForPackage(String str, int i, NotificationChannelGroup notificationChannelGroup) throws RemoteException;

    void updateNotificationChannelGroupFromPrivilegedListener(INotificationListener iNotificationListener, String str, UserHandle userHandle, NotificationChannelGroup notificationChannelGroup) throws RemoteException;

    void updateNotificationChannels(String str, ParceledListSlice parceledListSlice) throws RemoteException;

    public static abstract class Stub extends Binder implements INotificationManager {
        public static final String DESCRIPTOR = "android.app.INotificationManager";
        static final String[] PERMISSIONS_registerCallNotificationEventListener = {Manifest.permission.INTERACT_ACROSS_USERS, Manifest.permission.ACCESS_NOTIFICATIONS};
        static final String[] PERMISSIONS_unregisterCallNotificationEventListener = {Manifest.permission.INTERACT_ACROSS_USERS, Manifest.permission.ACCESS_NOTIFICATIONS};
        static final int TRANSACTION_addAutomaticZenRule = 137;
        static final int TRANSACTION_addReplyHistory = 220;
        static final int TRANSACTION_addWearableAppToList = 208;
        static final int TRANSACTION_allowAssistantAdjustment = 24;
        static final int TRANSACTION_appCanBePromoted = 162;
        static final int TRANSACTION_applyAdjustmentFromAssistant = 101;
        static final int TRANSACTION_applyAdjustmentsFromAssistant = 102;
        static final int TRANSACTION_applyEnqueuedAdjustmentFromAssistant = 100;
        static final int TRANSACTION_applyRestore = 146;
        static final int TRANSACTION_areBubblesAllowed = 29;
        static final int TRANSACTION_areBubblesEnabled = 30;
        static final int TRANSACTION_areChannelsBypassingDnd = 60;
        static final int TRANSACTION_areNotificationsEnabled = 20;
        static final int TRANSACTION_areNotificationsEnabledForPackage = 19;
        static final int TRANSACTION_bindEdgeLightingService = 174;
        static final int TRANSACTION_canAppBypassDnd = 227;
        static final int TRANSACTION_canBePromoted = 163;
        static final int TRANSACTION_canNotifyAsPackage = 150;
        static final int TRANSACTION_canShowBadge = 11;
        static final int TRANSACTION_canUseFullScreenIntent = 151;
        static final int TRANSACTION_cancelAllNotifications = 1;
        static final int TRANSACTION_cancelNotificationByEdge = 187;
        static final int TRANSACTION_cancelNotificationByGroupKey = 188;
        static final int TRANSACTION_cancelNotificationFromListener = 75;
        static final int TRANSACTION_cancelNotificationWithTag = 8;
        static final int TRANSACTION_cancelNotificationsFromListener = 76;
        static final int TRANSACTION_cancelToast = 5;
        static final int TRANSACTION_cleanUpCallersAfter = 107;
        static final int TRANSACTION_clearData = 2;
        static final int TRANSACTION_clearRequestedListenerHints = 87;
        static final int TRANSACTION_createConversationNotificationChannelForPackage = 47;
        static final int TRANSACTION_createConversationNotificationChannelForPackageFromPrivilegedListener = 95;
        static final int TRANSACTION_createNotificationChannelGroups = 32;
        static final int TRANSACTION_createNotificationChannels = 33;
        static final int TRANSACTION_createNotificationChannelsForPackage = 34;
        static final int TRANSACTION_deleteNotificationChannel = 49;
        static final int TRANSACTION_deleteNotificationChannelGroup = 55;
        static final int TRANSACTION_deleteNotificationHistoryItem = 65;
        static final int TRANSACTION_disable = 184;
        static final int TRANSACTION_disableEdgeLightingNotification = 185;
        static final int TRANSACTION_disallowAssistantAdjustment = 25;
        static final int TRANSACTION_dispatchDelayedWakeUpAndBlocked = 198;
        static final int TRANSACTION_dispatchDelayedWakelockAndBlocked = 197;
        static final int TRANSACTION_enqueueEdgeNotification = 189;
        static final int TRANSACTION_enqueueNotificationWithTag = 7;
        static final int TRANSACTION_enqueueTextToast = 3;
        static final int TRANSACTION_enqueueTextToastForDex = 230;
        static final int TRANSACTION_enqueueToast = 4;
        static final int TRANSACTION_enqueueToastForDex = 231;
        static final int TRANSACTION_finishToken = 6;
        static final int TRANSACTION_getActiveNotifications = 68;
        static final int TRANSACTION_getActiveNotificationsFromListener = 85;
        static final int TRANSACTION_getActiveNotificationsWithAttribution = 69;
        static final int TRANSACTION_getAdjustmentDeniedPackages = 168;
        static final int TRANSACTION_getAllNotificationListenersCount = 225;
        static final int TRANSACTION_getAllowedAdjustmentKeyTypes = 166;
        static final int TRANSACTION_getAllowedAssistantAdjustments = 23;
        static final int TRANSACTION_getAllowedNotificationAssistant = 119;
        static final int TRANSACTION_getAllowedNotificationAssistantForUser = 118;
        static final int TRANSACTION_getAllowedOngoingActivityAppList = 204;
        static final int TRANSACTION_getAppActiveNotifications = 147;
        static final int TRANSACTION_getAppNotificationSettingStatus = 236;
        static final int TRANSACTION_getAppsBypassingDndCount = 229;
        static final int TRANSACTION_getAutomaticZenRule = 135;
        static final int TRANSACTION_getAutomaticZenRuleState = 142;
        static final int TRANSACTION_getAutomaticZenRules = 136;
        static final int TRANSACTION_getBackupPayload = 145;
        static final int TRANSACTION_getBlockInfoOfNotificationsForOverflow = 237;
        static final int TRANSACTION_getBlockedAppCount = 226;
        static final int TRANSACTION_getBlockedChannelCount = 54;
        static final int TRANSACTION_getBubblePreferenceForPackage = 31;
        static final int TRANSACTION_getConsolidatedNotificationPolicy = 125;
        static final int TRANSACTION_getConversationNotificationChannel = 46;
        static final int TRANSACTION_getConversations = 35;
        static final int TRANSACTION_getConversationsForPackage = 36;
        static final int TRANSACTION_getDefaultNotificationAssistant = 120;
        static final int TRANSACTION_getDefaultZenPolicy = 134;
        static final int TRANSACTION_getDeletedChannelCount = 53;
        static final int TRANSACTION_getEdgeLightingSettingState = 194;
        static final int TRANSACTION_getEdgeLightingState = 182;
        static final int TRANSACTION_getEffectsSuppressor = 105;
        static final int TRANSACTION_getEnabledNotificationListenerPackages = 116;
        static final int TRANSACTION_getEnabledNotificationListeners = 117;
        static final int TRANSACTION_getHintsFromListener = 89;
        static final int TRANSACTION_getHintsFromListenerNoToken = 90;
        static final int TRANSACTION_getHistoricalNotifications = 70;
        static final int TRANSACTION_getHistoricalNotificationsWithAttribution = 71;
        static final int TRANSACTION_getInterruptionFilterFromListener = 92;
        static final int TRANSACTION_getListenerFilter = 155;
        static final int TRANSACTION_getLockScreenNotificationVisibilityForPackage = 212;
        static final int TRANSACTION_getNotificationAlertsEnabledForPackage = 205;
        static final int TRANSACTION_getNotificationChannel = 45;
        static final int TRANSACTION_getNotificationChannelForPackage = 48;
        static final int TRANSACTION_getNotificationChannelGroup = 56;
        static final int TRANSACTION_getNotificationChannelGroupForPackage = 38;
        static final int TRANSACTION_getNotificationChannelGroups = 57;
        static final int TRANSACTION_getNotificationChannelGroupsForPackage = 37;
        static final int TRANSACTION_getNotificationChannelGroupsFromPrivilegedListener = 99;
        static final int TRANSACTION_getNotificationChannelGroupsWithoutChannels = 58;
        static final int TRANSACTION_getNotificationChannels = 50;
        static final int TRANSACTION_getNotificationChannelsBypassingDnd = 61;
        static final int TRANSACTION_getNotificationChannelsForPackage = 51;
        static final int TRANSACTION_getNotificationChannelsFromPrivilegedListener = 98;
        static final int TRANSACTION_getNotificationDelegate = 149;
        static final int TRANSACTION_getNotificationHistory = 72;
        static final int TRANSACTION_getNotificationHistoryDataForPackage = 221;
        static final int TRANSACTION_getNotificationHistoryForPackage = 222;
        static final int TRANSACTION_getNotificationPolicy = 129;
        static final int TRANSACTION_getNotificationSettingStatus = 235;
        static final int TRANSACTION_getNotificationSoundStatus = 233;
        static final int TRANSACTION_getNumNotificationChannelsForPackage = 52;
        static final int TRANSACTION_getPackageImportance = 21;
        static final int TRANSACTION_getPackagesBypassingDnd = 62;
        static final int TRANSACTION_getPackagesWithAnyChannels = 63;
        static final int TRANSACTION_getPopulatedNotificationChannelGroupForPackage = 39;
        static final int TRANSACTION_getPrivateNotificationsAllowed = 153;
        static final int TRANSACTION_getRecentBlockedNotificationChannelGroupsForPackage = 40;
        static final int TRANSACTION_getRuleInstanceCount = 141;
        static final int TRANSACTION_getSnoozedNotificationsFromListener = 86;
        static final int TRANSACTION_getUnsupportedAdjustmentTypes = 165;
        static final int TRANSACTION_getWearableAppList = 210;
        static final int TRANSACTION_getZenMode = 123;
        static final int TRANSACTION_getZenModeConfig = 124;
        static final int TRANSACTION_hasEnabledNotificationListener = 122;
        static final int TRANSACTION_hasSentValidBubble = 16;
        static final int TRANSACTION_hasSentValidMsg = 12;
        static final int TRANSACTION_hasUserDemotedInvalidMsgApp = 14;
        static final int TRANSACTION_incrementCounter = 171;
        static final int TRANSACTION_isAdjustmentSupportedForPackage = 169;
        static final int TRANSACTION_isAlertsAllowed = 216;
        static final int TRANSACTION_isAllowNotificationPopUpForPackage = 214;
        static final int TRANSACTION_isEdgeLightingAllowed = 191;
        static final int TRANSACTION_isEdgeLightingNotificationAllowed = 183;
        static final int TRANSACTION_isImportanceLocked = 22;
        static final int TRANSACTION_isInCall = 9;
        static final int TRANSACTION_isInInvalidMsgState = 13;
        static final int TRANSACTION_isNotificationAssistantAccessGranted = 111;
        static final int TRANSACTION_isNotificationListenerAccessGranted = 109;
        static final int TRANSACTION_isNotificationListenerAccessGrantedForUser = 110;
        static final int TRANSACTION_isNotificationPolicyAccessGranted = 128;
        static final int TRANSACTION_isNotificationPolicyAccessGrantedForPackage = 131;
        static final int TRANSACTION_isNotificationTurnedOff = 232;
        static final int TRANSACTION_isOngoingActivityAllowed = 201;
        static final int TRANSACTION_isPackageEnabled = 186;
        static final int TRANSACTION_isPackagePaused = 64;
        static final int TRANSACTION_isPermissionFixed = 66;
        static final int TRANSACTION_isReminderEnabled = 217;
        static final int TRANSACTION_isSubDisplayNotificationAllowed = 199;
        static final int TRANSACTION_isSystemConditionProviderEnabled = 108;
        static final int TRANSACTION_matchesCallFilter = 106;
        static final int TRANSACTION_migrateNotificationFilter = 157;
        static final int TRANSACTION_notifyConditions = 127;
        static final int TRANSACTION_onlyHasDefaultChannel = 59;
        static final int TRANSACTION_pullStats = 154;
        static final int TRANSACTION_registerCallNotificationEventListener = 159;
        static final int TRANSACTION_registerEdgeLightingListener = 178;
        static final int TRANSACTION_registerListener = 73;
        static final int TRANSACTION_registerNotificationListener = 172;
        static final int TRANSACTION_removeAutomaticZenRule = 139;
        static final int TRANSACTION_removeAutomaticZenRules = 140;
        static final int TRANSACTION_removeEdgeNotification = 190;
        static final int TRANSACTION_removeWearableAppFromList = 209;
        static final int TRANSACTION_requestBindListener = 79;
        static final int TRANSACTION_requestBindProvider = 82;
        static final int TRANSACTION_requestHintsFromListener = 88;
        static final int TRANSACTION_requestInterruptionFilterFromListener = 91;
        static final int TRANSACTION_requestListenerHintsForWearable = 211;
        static final int TRANSACTION_requestUnbindListener = 80;
        static final int TRANSACTION_requestUnbindListenerComponent = 81;
        static final int TRANSACTION_requestUnbindProvider = 83;
        static final int TRANSACTION_resetDefaultAllowEdgeLighting = 193;
        static final int TRANSACTION_resetDefaultAllowOngoingActivity = 203;
        static final int TRANSACTION_resetDefaultEdgeLightingState = 196;
        static final int TRANSACTION_setAdjustmentSupportedForPackage = 170;
        static final int TRANSACTION_setAdjustmentTypeSupportedState = 164;
        static final int TRANSACTION_setAllowEdgeLighting = 192;
        static final int TRANSACTION_setAllowNotificationPopUpForPackage = 215;
        static final int TRANSACTION_setAllowOngoingActivity = 202;
        static final int TRANSACTION_setAllowSubDisplayNotification = 200;
        static final int TRANSACTION_setAppBypassDnd = 228;
        static final int TRANSACTION_setAssistantAdjustmentKeyTypeState = 167;
        static final int TRANSACTION_setAutomaticZenRuleState = 143;
        static final int TRANSACTION_setBubblesAllowed = 28;
        static final int TRANSACTION_setCanBePromoted = 161;
        static final int TRANSACTION_setEdgeLightingState = 195;
        static final int TRANSACTION_setHideSilentStatusIcons = 27;
        static final int TRANSACTION_setInterruptionFilter = 94;
        static final int TRANSACTION_setInvalidMsgAppDemoted = 15;
        static final int TRANSACTION_setListenerFilter = 156;
        static final int TRANSACTION_setLockScreenNotificationVisibilityForPackage = 213;
        static final int TRANSACTION_setManualZenRuleDeviceEffects = 144;
        static final int TRANSACTION_setNASMigrationDoneAndResetDefault = 121;
        static final int TRANSACTION_setNotificationAlertsEnabledForPackage = 206;
        static final int TRANSACTION_setNotificationAssistantAccessGranted = 113;
        static final int TRANSACTION_setNotificationAssistantAccessGrantedForUser = 115;
        static final int TRANSACTION_setNotificationDelegate = 148;
        static final int TRANSACTION_setNotificationListenerAccessGranted = 112;
        static final int TRANSACTION_setNotificationListenerAccessGrantedForUser = 114;
        static final int TRANSACTION_setNotificationPolicy = 130;
        static final int TRANSACTION_setNotificationPolicyAccessGranted = 132;
        static final int TRANSACTION_setNotificationPolicyAccessGrantedForUser = 133;
        static final int TRANSACTION_setNotificationTurnOff = 234;
        static final int TRANSACTION_setNotificationsEnabledForPackage = 17;
        static final int TRANSACTION_setNotificationsEnabledWithImportanceLockForPackage = 18;
        static final int TRANSACTION_setNotificationsShownFromListener = 84;
        static final int TRANSACTION_setOnNotificationPostedTrimFromListener = 93;
        static final int TRANSACTION_setPrivateNotificationsAllowed = 152;
        static final int TRANSACTION_setReminderEnabled = 219;
        static final int TRANSACTION_setReminderEnabledForPackage = 218;
        static final int TRANSACTION_setRestoreBlockListForSS = 224;
        static final int TRANSACTION_setShowBadge = 10;
        static final int TRANSACTION_setToastRateLimitingEnabled = 158;
        static final int TRANSACTION_setWearableAppList = 207;
        static final int TRANSACTION_setZenMode = 126;
        static final int TRANSACTION_shouldHideSilentStatusIcons = 26;
        static final int TRANSACTION_silenceNotificationSound = 67;
        static final int TRANSACTION_snoozeNotificationUntilContextFromListener = 77;
        static final int TRANSACTION_snoozeNotificationUntilFromListener = 78;
        static final int TRANSACTION_startEdgeLighting = 180;
        static final int TRANSACTION_stopEdgeLighting = 181;
        static final int TRANSACTION_unbindEdgeLightingService = 175;
        static final int TRANSACTION_unlockAllNotificationChannels = 44;
        static final int TRANSACTION_unlockNotificationChannel = 43;
        static final int TRANSACTION_unregisterCallNotificationEventListener = 160;
        static final int TRANSACTION_unregisterEdgeLightingListener = 179;
        static final int TRANSACTION_unregisterListener = 74;
        static final int TRANSACTION_unsnoozeNotificationFromAssistant = 103;
        static final int TRANSACTION_unsnoozeNotificationFromSystemListener = 104;
        static final int TRANSACTION_updateAutomaticZenRule = 138;
        static final int TRANSACTION_updateCancelEvent = 223;
        static final int TRANSACTION_updateEdgeLightingPackageList = 176;
        static final int TRANSACTION_updateEdgeLightingPolicy = 177;
        static final int TRANSACTION_updateNotificationChannelForPackage = 42;
        static final int TRANSACTION_updateNotificationChannelFromPrivilegedListener = 97;
        static final int TRANSACTION_updateNotificationChannelGroupForPackage = 41;
        static final int TRANSACTION_updateNotificationChannelGroupFromPrivilegedListener = 96;
        static final int TRANSACTION_updateNotificationChannels = 173;
        private final PermissionEnforcer mEnforcer;

        @Override // android.os.IInterface
        public IBinder asBinder() {
            return this;
        }

        @Override // android.os.Binder
        public int getMaxTransactionId() {
            return 236;
        }

        public Stub(PermissionEnforcer permissionEnforcer) {
            attachInterface(this, DESCRIPTOR);
            if (permissionEnforcer == null) {
                throw new IllegalArgumentException("enforcer cannot be null");
            }
            this.mEnforcer = permissionEnforcer;
        }

        @Deprecated
        public Stub() {
            this(PermissionEnforcer.fromContext(ActivityThread.currentActivityThread().getSystemContext()));
        }

        public static INotificationManager asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface queryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (queryLocalInterface != null && (queryLocalInterface instanceof INotificationManager)) {
                return (INotificationManager) queryLocalInterface;
            }
            return new Proxy(iBinder);
        }

        public static String getDefaultTransactionName(int i) {
            switch (i) {
                case 1:
                    return "cancelAllNotifications";
                case 2:
                    return "clearData";
                case 3:
                    return "enqueueTextToast";
                case 4:
                    return "enqueueToast";
                case 5:
                    return "cancelToast";
                case 6:
                    return "finishToken";
                case 7:
                    return "enqueueNotificationWithTag";
                case 8:
                    return "cancelNotificationWithTag";
                case 9:
                    return "isInCall";
                case 10:
                    return "setShowBadge";
                case 11:
                    return "canShowBadge";
                case 12:
                    return "hasSentValidMsg";
                case 13:
                    return "isInInvalidMsgState";
                case 14:
                    return "hasUserDemotedInvalidMsgApp";
                case 15:
                    return "setInvalidMsgAppDemoted";
                case 16:
                    return "hasSentValidBubble";
                case 17:
                    return "setNotificationsEnabledForPackage";
                case 18:
                    return "setNotificationsEnabledWithImportanceLockForPackage";
                case 19:
                    return "areNotificationsEnabledForPackage";
                case 20:
                    return "areNotificationsEnabled";
                case 21:
                    return "getPackageImportance";
                case 22:
                    return "isImportanceLocked";
                case 23:
                    return "getAllowedAssistantAdjustments";
                case 24:
                    return "allowAssistantAdjustment";
                case 25:
                    return "disallowAssistantAdjustment";
                case 26:
                    return "shouldHideSilentStatusIcons";
                case 27:
                    return "setHideSilentStatusIcons";
                case 28:
                    return "setBubblesAllowed";
                case 29:
                    return "areBubblesAllowed";
                case 30:
                    return "areBubblesEnabled";
                case 31:
                    return "getBubblePreferenceForPackage";
                case 32:
                    return "createNotificationChannelGroups";
                case 33:
                    return "createNotificationChannels";
                case 34:
                    return "createNotificationChannelsForPackage";
                case 35:
                    return "getConversations";
                case 36:
                    return "getConversationsForPackage";
                case 37:
                    return "getNotificationChannelGroupsForPackage";
                case 38:
                    return "getNotificationChannelGroupForPackage";
                case 39:
                    return "getPopulatedNotificationChannelGroupForPackage";
                case 40:
                    return "getRecentBlockedNotificationChannelGroupsForPackage";
                case 41:
                    return "updateNotificationChannelGroupForPackage";
                case 42:
                    return "updateNotificationChannelForPackage";
                case 43:
                    return "unlockNotificationChannel";
                case 44:
                    return "unlockAllNotificationChannels";
                case 45:
                    return "getNotificationChannel";
                case 46:
                    return "getConversationNotificationChannel";
                case 47:
                    return "createConversationNotificationChannelForPackage";
                case 48:
                    return "getNotificationChannelForPackage";
                case 49:
                    return "deleteNotificationChannel";
                case 50:
                    return "getNotificationChannels";
                case 51:
                    return "getNotificationChannelsForPackage";
                case 52:
                    return "getNumNotificationChannelsForPackage";
                case 53:
                    return "getDeletedChannelCount";
                case 54:
                    return "getBlockedChannelCount";
                case 55:
                    return "deleteNotificationChannelGroup";
                case 56:
                    return "getNotificationChannelGroup";
                case 57:
                    return "getNotificationChannelGroups";
                case 58:
                    return "getNotificationChannelGroupsWithoutChannels";
                case 59:
                    return "onlyHasDefaultChannel";
                case 60:
                    return "areChannelsBypassingDnd";
                case 61:
                    return "getNotificationChannelsBypassingDnd";
                case 62:
                    return "getPackagesBypassingDnd";
                case 63:
                    return "getPackagesWithAnyChannels";
                case 64:
                    return "isPackagePaused";
                case 65:
                    return "deleteNotificationHistoryItem";
                case 66:
                    return "isPermissionFixed";
                case 67:
                    return "silenceNotificationSound";
                case 68:
                    return "getActiveNotifications";
                case 69:
                    return "getActiveNotificationsWithAttribution";
                case 70:
                    return "getHistoricalNotifications";
                case 71:
                    return "getHistoricalNotificationsWithAttribution";
                case 72:
                    return "getNotificationHistory";
                case 73:
                    return "registerListener";
                case 74:
                    return "unregisterListener";
                case 75:
                    return "cancelNotificationFromListener";
                case 76:
                    return "cancelNotificationsFromListener";
                case 77:
                    return "snoozeNotificationUntilContextFromListener";
                case 78:
                    return "snoozeNotificationUntilFromListener";
                case 79:
                    return "requestBindListener";
                case 80:
                    return "requestUnbindListener";
                case 81:
                    return "requestUnbindListenerComponent";
                case 82:
                    return "requestBindProvider";
                case 83:
                    return "requestUnbindProvider";
                case 84:
                    return "setNotificationsShownFromListener";
                case 85:
                    return "getActiveNotificationsFromListener";
                case 86:
                    return "getSnoozedNotificationsFromListener";
                case 87:
                    return "clearRequestedListenerHints";
                case 88:
                    return "requestHintsFromListener";
                case 89:
                    return "getHintsFromListener";
                case 90:
                    return "getHintsFromListenerNoToken";
                case 91:
                    return "requestInterruptionFilterFromListener";
                case 92:
                    return "getInterruptionFilterFromListener";
                case 93:
                    return "setOnNotificationPostedTrimFromListener";
                case 94:
                    return "setInterruptionFilter";
                case 95:
                    return "createConversationNotificationChannelForPackageFromPrivilegedListener";
                case 96:
                    return "updateNotificationChannelGroupFromPrivilegedListener";
                case 97:
                    return "updateNotificationChannelFromPrivilegedListener";
                case 98:
                    return "getNotificationChannelsFromPrivilegedListener";
                case 99:
                    return "getNotificationChannelGroupsFromPrivilegedListener";
                case 100:
                    return "applyEnqueuedAdjustmentFromAssistant";
                case 101:
                    return "applyAdjustmentFromAssistant";
                case 102:
                    return "applyAdjustmentsFromAssistant";
                case 103:
                    return "unsnoozeNotificationFromAssistant";
                case 104:
                    return "unsnoozeNotificationFromSystemListener";
                case 105:
                    return "getEffectsSuppressor";
                case 106:
                    return "matchesCallFilter";
                case 107:
                    return "cleanUpCallersAfter";
                case 108:
                    return "isSystemConditionProviderEnabled";
                case 109:
                    return "isNotificationListenerAccessGranted";
                case 110:
                    return "isNotificationListenerAccessGrantedForUser";
                case 111:
                    return "isNotificationAssistantAccessGranted";
                case 112:
                    return "setNotificationListenerAccessGranted";
                case 113:
                    return "setNotificationAssistantAccessGranted";
                case 114:
                    return "setNotificationListenerAccessGrantedForUser";
                case 115:
                    return "setNotificationAssistantAccessGrantedForUser";
                case 116:
                    return "getEnabledNotificationListenerPackages";
                case 117:
                    return "getEnabledNotificationListeners";
                case 118:
                    return "getAllowedNotificationAssistantForUser";
                case 119:
                    return "getAllowedNotificationAssistant";
                case 120:
                    return "getDefaultNotificationAssistant";
                case 121:
                    return "setNASMigrationDoneAndResetDefault";
                case 122:
                    return "hasEnabledNotificationListener";
                case 123:
                    return "getZenMode";
                case 124:
                    return "getZenModeConfig";
                case 125:
                    return "getConsolidatedNotificationPolicy";
                case 126:
                    return "setZenMode";
                case 127:
                    return "notifyConditions";
                case 128:
                    return "isNotificationPolicyAccessGranted";
                case 129:
                    return "getNotificationPolicy";
                case 130:
                    return "setNotificationPolicy";
                case 131:
                    return "isNotificationPolicyAccessGrantedForPackage";
                case 132:
                    return "setNotificationPolicyAccessGranted";
                case 133:
                    return "setNotificationPolicyAccessGrantedForUser";
                case 134:
                    return "getDefaultZenPolicy";
                case 135:
                    return "getAutomaticZenRule";
                case 136:
                    return "getAutomaticZenRules";
                case 137:
                    return "addAutomaticZenRule";
                case 138:
                    return "updateAutomaticZenRule";
                case 139:
                    return "removeAutomaticZenRule";
                case 140:
                    return "removeAutomaticZenRules";
                case 141:
                    return "getRuleInstanceCount";
                case 142:
                    return "getAutomaticZenRuleState";
                case 143:
                    return "setAutomaticZenRuleState";
                case 144:
                    return "setManualZenRuleDeviceEffects";
                case 145:
                    return "getBackupPayload";
                case 146:
                    return "applyRestore";
                case 147:
                    return "getAppActiveNotifications";
                case 148:
                    return "setNotificationDelegate";
                case 149:
                    return "getNotificationDelegate";
                case 150:
                    return "canNotifyAsPackage";
                case 151:
                    return "canUseFullScreenIntent";
                case 152:
                    return "setPrivateNotificationsAllowed";
                case 153:
                    return "getPrivateNotificationsAllowed";
                case 154:
                    return "pullStats";
                case 155:
                    return "getListenerFilter";
                case 156:
                    return "setListenerFilter";
                case 157:
                    return "migrateNotificationFilter";
                case 158:
                    return "setToastRateLimitingEnabled";
                case 159:
                    return "registerCallNotificationEventListener";
                case 160:
                    return "unregisterCallNotificationEventListener";
                case 161:
                    return "setCanBePromoted";
                case 162:
                    return "appCanBePromoted";
                case 163:
                    return "canBePromoted";
                case 164:
                    return "setAdjustmentTypeSupportedState";
                case 165:
                    return "getUnsupportedAdjustmentTypes";
                case 166:
                    return "getAllowedAdjustmentKeyTypes";
                case 167:
                    return "setAssistantAdjustmentKeyTypeState";
                case 168:
                    return "getAdjustmentDeniedPackages";
                case 169:
                    return "isAdjustmentSupportedForPackage";
                case 170:
                    return "setAdjustmentSupportedForPackage";
                case 171:
                    return "incrementCounter";
                case 172:
                    return "registerNotificationListener";
                case 173:
                    return "updateNotificationChannels";
                case 174:
                    return "bindEdgeLightingService";
                case 175:
                    return "unbindEdgeLightingService";
                case 176:
                    return "updateEdgeLightingPackageList";
                case 177:
                    return "updateEdgeLightingPolicy";
                case 178:
                    return "registerEdgeLightingListener";
                case 179:
                    return "unregisterEdgeLightingListener";
                case 180:
                    return "startEdgeLighting";
                case 181:
                    return "stopEdgeLighting";
                case 182:
                    return "getEdgeLightingState";
                case 183:
                    return "isEdgeLightingNotificationAllowed";
                case 184:
                    return SemWifiDisplayParameter.VALUE_DISABLE;
                case 185:
                    return "disableEdgeLightingNotification";
                case 186:
                    return "isPackageEnabled";
                case 187:
                    return "cancelNotificationByEdge";
                case 188:
                    return "cancelNotificationByGroupKey";
                case 189:
                    return "enqueueEdgeNotification";
                case 190:
                    return "removeEdgeNotification";
                case 191:
                    return SecContentProviderURI.KIOSKMODE_EDGELIGHTINGALLOWED_METHOD;
                case 192:
                    return "setAllowEdgeLighting";
                case 193:
                    return "resetDefaultAllowEdgeLighting";
                case 194:
                    return "getEdgeLightingSettingState";
                case 195:
                    return "setEdgeLightingState";
                case 196:
                    return "resetDefaultEdgeLightingState";
                case 197:
                    return "dispatchDelayedWakelockAndBlocked";
                case 198:
                    return "dispatchDelayedWakeUpAndBlocked";
                case 199:
                    return "isSubDisplayNotificationAllowed";
                case 200:
                    return "setAllowSubDisplayNotification";
                case 201:
                    return "isOngoingActivityAllowed";
                case 202:
                    return "setAllowOngoingActivity";
                case 203:
                    return "resetDefaultAllowOngoingActivity";
                case 204:
                    return "getAllowedOngoingActivityAppList";
                case 205:
                    return "getNotificationAlertsEnabledForPackage";
                case 206:
                    return "setNotificationAlertsEnabledForPackage";
                case 207:
                    return "setWearableAppList";
                case 208:
                    return "addWearableAppToList";
                case 209:
                    return "removeWearableAppFromList";
                case 210:
                    return "getWearableAppList";
                case 211:
                    return "requestListenerHintsForWearable";
                case 212:
                    return "getLockScreenNotificationVisibilityForPackage";
                case 213:
                    return "setLockScreenNotificationVisibilityForPackage";
                case 214:
                    return "isAllowNotificationPopUpForPackage";
                case 215:
                    return "setAllowNotificationPopUpForPackage";
                case 216:
                    return "isAlertsAllowed";
                case 217:
                    return "isReminderEnabled";
                case 218:
                    return "setReminderEnabledForPackage";
                case 219:
                    return "setReminderEnabled";
                case 220:
                    return "addReplyHistory";
                case 221:
                    return "getNotificationHistoryDataForPackage";
                case 222:
                    return "getNotificationHistoryForPackage";
                case 223:
                    return "updateCancelEvent";
                case 224:
                    return "setRestoreBlockListForSS";
                case 225:
                    return "getAllNotificationListenersCount";
                case 226:
                    return "getBlockedAppCount";
                case 227:
                    return "canAppBypassDnd";
                case 228:
                    return "setAppBypassDnd";
                case 229:
                    return "getAppsBypassingDndCount";
                case 230:
                    return "enqueueTextToastForDex";
                case 231:
                    return "enqueueToastForDex";
                case 232:
                    return "isNotificationTurnedOff";
                case 233:
                    return "getNotificationSoundStatus";
                case 234:
                    return "setNotificationTurnOff";
                case 235:
                    return "getNotificationSettingStatus";
                case 236:
                    return "getAppNotificationSettingStatus";
                case 237:
                    return "getBlockInfoOfNotificationsForOverflow";
                default:
                    return null;
            }
        }

        @Override // android.os.Binder
        public String getTransactionName(int i) {
            return getDefaultTransactionName(i);
        }

        @Override // android.os.Binder
        public boolean onTransact(int i, Parcel parcel, Parcel parcel2, int i2) throws RemoteException {
            if (i >= 1 && i <= 16777215) {
                parcel.enforceInterface(DESCRIPTOR);
            }
            if (i == 1598968902) {
                parcel2.writeString(DESCRIPTOR);
                return true;
            }
            switch (i) {
                case 1:
                    String readString = parcel.readString();
                    int readInt = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cancelAllNotifications(readString, readInt);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    String readString2 = parcel.readString();
                    int readInt2 = parcel.readInt();
                    boolean readBoolean = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    clearData(readString2, readInt2, readBoolean);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    String readString3 = parcel.readString();
                    IBinder readStrongBinder = parcel.readStrongBinder();
                    CharSequence charSequence = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    int readInt3 = parcel.readInt();
                    boolean readBoolean2 = parcel.readBoolean();
                    int readInt4 = parcel.readInt();
                    ITransientNotificationCallback asInterface = ITransientNotificationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean enqueueTextToast = enqueueTextToast(readString3, readStrongBinder, charSequence, readInt3, readBoolean2, readInt4, asInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enqueueTextToast);
                    return true;
                case 4:
                    String readString4 = parcel.readString();
                    IBinder readStrongBinder2 = parcel.readStrongBinder();
                    ITransientNotification asInterface2 = ITransientNotification.Stub.asInterface(parcel.readStrongBinder());
                    int readInt5 = parcel.readInt();
                    boolean readBoolean3 = parcel.readBoolean();
                    int readInt6 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean enqueueToast = enqueueToast(readString4, readStrongBinder2, asInterface2, readInt5, readBoolean3, readInt6);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(enqueueToast);
                    return true;
                case 5:
                    String readString5 = parcel.readString();
                    IBinder readStrongBinder3 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    cancelToast(readString5, readStrongBinder3);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    String readString6 = parcel.readString();
                    IBinder readStrongBinder4 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    finishToken(readString6, readStrongBinder4);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    String readString7 = parcel.readString();
                    String readString8 = parcel.readString();
                    String readString9 = parcel.readString();
                    int readInt7 = parcel.readInt();
                    Notification notification = (Notification) parcel.readTypedObject(Notification.CREATOR);
                    int readInt8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enqueueNotificationWithTag(readString7, readString8, readString9, readInt7, notification, readInt8);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    String readString10 = parcel.readString();
                    String readString11 = parcel.readString();
                    String readString12 = parcel.readString();
                    int readInt9 = parcel.readInt();
                    int readInt10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cancelNotificationWithTag(readString10, readString11, readString12, readInt9, readInt10);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    String readString13 = parcel.readString();
                    int readInt11 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isInCall = isInCall(readString13, readInt11);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isInCall);
                    return true;
                case 10:
                    String readString14 = parcel.readString();
                    int readInt12 = parcel.readInt();
                    boolean readBoolean4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setShowBadge(readString14, readInt12, readBoolean4);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    String readString15 = parcel.readString();
                    int readInt13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean canShowBadge = canShowBadge(readString15, readInt13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canShowBadge);
                    return true;
                case 12:
                    String readString16 = parcel.readString();
                    int readInt14 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasSentValidMsg = hasSentValidMsg(readString16, readInt14);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasSentValidMsg);
                    return true;
                case 13:
                    String readString17 = parcel.readString();
                    int readInt15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isInInvalidMsgState = isInInvalidMsgState(readString17, readInt15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isInInvalidMsgState);
                    return true;
                case 14:
                    String readString18 = parcel.readString();
                    int readInt16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasUserDemotedInvalidMsgApp = hasUserDemotedInvalidMsgApp(readString18, readInt16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasUserDemotedInvalidMsgApp);
                    return true;
                case 15:
                    String readString19 = parcel.readString();
                    int readInt17 = parcel.readInt();
                    boolean readBoolean5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setInvalidMsgAppDemoted(readString19, readInt17, readBoolean5);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    String readString20 = parcel.readString();
                    int readInt18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasSentValidBubble = hasSentValidBubble(readString20, readInt18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasSentValidBubble);
                    return true;
                case 17:
                    String readString21 = parcel.readString();
                    int readInt19 = parcel.readInt();
                    boolean readBoolean6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNotificationsEnabledForPackage(readString21, readInt19, readBoolean6);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    String readString22 = parcel.readString();
                    int readInt20 = parcel.readInt();
                    boolean readBoolean7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNotificationsEnabledWithImportanceLockForPackage(readString22, readInt20, readBoolean7);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    String readString23 = parcel.readString();
                    int readInt21 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean areNotificationsEnabledForPackage = areNotificationsEnabledForPackage(readString23, readInt21);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(areNotificationsEnabledForPackage);
                    return true;
                case 20:
                    String readString24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean areNotificationsEnabled = areNotificationsEnabled(readString24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(areNotificationsEnabled);
                    return true;
                case 21:
                    String readString25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int packageImportance = getPackageImportance(readString25);
                    parcel2.writeNoException();
                    parcel2.writeInt(packageImportance);
                    return true;
                case 22:
                    String readString26 = parcel.readString();
                    int readInt22 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isImportanceLocked = isImportanceLocked(readString26, readInt22);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isImportanceLocked);
                    return true;
                case 23:
                    String readString27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> allowedAssistantAdjustments = getAllowedAssistantAdjustments(readString27);
                    parcel2.writeNoException();
                    parcel2.writeStringList(allowedAssistantAdjustments);
                    return true;
                case 24:
                    String readString28 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    allowAssistantAdjustment(readString28);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    String readString29 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    disallowAssistantAdjustment(readString29);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    String readString30 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean shouldHideSilentStatusIcons = shouldHideSilentStatusIcons(readString30);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(shouldHideSilentStatusIcons);
                    return true;
                case 27:
                    boolean readBoolean8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setHideSilentStatusIcons(readBoolean8);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    String readString31 = parcel.readString();
                    int readInt23 = parcel.readInt();
                    int readInt24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setBubblesAllowed(readString31, readInt23, readInt24);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    String readString32 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean areBubblesAllowed = areBubblesAllowed(readString32);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(areBubblesAllowed);
                    return true;
                case 30:
                    UserHandle userHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean areBubblesEnabled = areBubblesEnabled(userHandle);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(areBubblesEnabled);
                    return true;
                case 31:
                    String readString33 = parcel.readString();
                    int readInt25 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int bubblePreferenceForPackage = getBubblePreferenceForPackage(readString33, readInt25);
                    parcel2.writeNoException();
                    parcel2.writeInt(bubblePreferenceForPackage);
                    return true;
                case 32:
                    String readString34 = parcel.readString();
                    ParceledListSlice parceledListSlice = (ParceledListSlice) parcel.readTypedObject(ParceledListSlice.CREATOR);
                    parcel.enforceNoDataAvail();
                    createNotificationChannelGroups(readString34, parceledListSlice);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    String readString35 = parcel.readString();
                    ParceledListSlice parceledListSlice2 = (ParceledListSlice) parcel.readTypedObject(ParceledListSlice.CREATOR);
                    parcel.enforceNoDataAvail();
                    createNotificationChannels(readString35, parceledListSlice2);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    String readString36 = parcel.readString();
                    int readInt26 = parcel.readInt();
                    ParceledListSlice parceledListSlice3 = (ParceledListSlice) parcel.readTypedObject(ParceledListSlice.CREATOR);
                    parcel.enforceNoDataAvail();
                    createNotificationChannelsForPackage(readString36, readInt26, parceledListSlice3);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    boolean readBoolean9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice conversations = getConversations(readBoolean9);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(conversations, 1);
                    return true;
                case 36:
                    String readString37 = parcel.readString();
                    int readInt27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice conversationsForPackage = getConversationsForPackage(readString37, readInt27);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(conversationsForPackage, 1);
                    return true;
                case 37:
                    String readString38 = parcel.readString();
                    int readInt28 = parcel.readInt();
                    boolean readBoolean10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice notificationChannelGroupsForPackage = getNotificationChannelGroupsForPackage(readString38, readInt28, readBoolean10);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(notificationChannelGroupsForPackage, 1);
                    return true;
                case 38:
                    String readString39 = parcel.readString();
                    String readString40 = parcel.readString();
                    int readInt29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    NotificationChannelGroup notificationChannelGroupForPackage = getNotificationChannelGroupForPackage(readString39, readString40, readInt29);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(notificationChannelGroupForPackage, 1);
                    return true;
                case 39:
                    String readString41 = parcel.readString();
                    int readInt30 = parcel.readInt();
                    String readString42 = parcel.readString();
                    boolean readBoolean11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    NotificationChannelGroup populatedNotificationChannelGroupForPackage = getPopulatedNotificationChannelGroupForPackage(readString41, readInt30, readString42, readBoolean11);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(populatedNotificationChannelGroupForPackage, 1);
                    return true;
                case 40:
                    String readString43 = parcel.readString();
                    int readInt31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice recentBlockedNotificationChannelGroupsForPackage = getRecentBlockedNotificationChannelGroupsForPackage(readString43, readInt31);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(recentBlockedNotificationChannelGroupsForPackage, 1);
                    return true;
                case 41:
                    String readString44 = parcel.readString();
                    int readInt32 = parcel.readInt();
                    NotificationChannelGroup notificationChannelGroup = (NotificationChannelGroup) parcel.readTypedObject(NotificationChannelGroup.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateNotificationChannelGroupForPackage(readString44, readInt32, notificationChannelGroup);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    String readString45 = parcel.readString();
                    int readInt33 = parcel.readInt();
                    NotificationChannel notificationChannel = (NotificationChannel) parcel.readTypedObject(NotificationChannel.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateNotificationChannelForPackage(readString45, readInt33, notificationChannel);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    String readString46 = parcel.readString();
                    int readInt34 = parcel.readInt();
                    String readString47 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unlockNotificationChannel(readString46, readInt34, readString47);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    unlockAllNotificationChannels();
                    parcel2.writeNoException();
                    return true;
                case 45:
                    String readString48 = parcel.readString();
                    int readInt35 = parcel.readInt();
                    String readString49 = parcel.readString();
                    String readString50 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    NotificationChannel notificationChannel2 = getNotificationChannel(readString48, readInt35, readString49, readString50);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(notificationChannel2, 1);
                    return true;
                case 46:
                    String readString51 = parcel.readString();
                    int readInt36 = parcel.readInt();
                    String readString52 = parcel.readString();
                    String readString53 = parcel.readString();
                    boolean readBoolean12 = parcel.readBoolean();
                    String readString54 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    NotificationChannel conversationNotificationChannel = getConversationNotificationChannel(readString51, readInt36, readString52, readString53, readBoolean12, readString54);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(conversationNotificationChannel, 1);
                    return true;
                case 47:
                    String readString55 = parcel.readString();
                    int readInt37 = parcel.readInt();
                    NotificationChannel notificationChannel3 = (NotificationChannel) parcel.readTypedObject(NotificationChannel.CREATOR);
                    String readString56 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    createConversationNotificationChannelForPackage(readString55, readInt37, notificationChannel3, readString56);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    String readString57 = parcel.readString();
                    int readInt38 = parcel.readInt();
                    String readString58 = parcel.readString();
                    String readString59 = parcel.readString();
                    boolean readBoolean13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    NotificationChannel notificationChannelForPackage = getNotificationChannelForPackage(readString57, readInt38, readString58, readString59, readBoolean13);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(notificationChannelForPackage, 1);
                    return true;
                case 49:
                    String readString60 = parcel.readString();
                    String readString61 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    deleteNotificationChannel(readString60, readString61);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    String readString62 = parcel.readString();
                    String readString63 = parcel.readString();
                    int readInt39 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice notificationChannels = getNotificationChannels(readString62, readString63, readInt39);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(notificationChannels, 1);
                    return true;
                case 51:
                    String readString64 = parcel.readString();
                    int readInt40 = parcel.readInt();
                    boolean readBoolean14 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice notificationChannelsForPackage = getNotificationChannelsForPackage(readString64, readInt40, readBoolean14);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(notificationChannelsForPackage, 1);
                    return true;
                case 52:
                    String readString65 = parcel.readString();
                    int readInt41 = parcel.readInt();
                    boolean readBoolean15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int numNotificationChannelsForPackage = getNumNotificationChannelsForPackage(readString65, readInt41, readBoolean15);
                    parcel2.writeNoException();
                    parcel2.writeInt(numNotificationChannelsForPackage);
                    return true;
                case 53:
                    String readString66 = parcel.readString();
                    int readInt42 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int deletedChannelCount = getDeletedChannelCount(readString66, readInt42);
                    parcel2.writeNoException();
                    parcel2.writeInt(deletedChannelCount);
                    return true;
                case 54:
                    String readString67 = parcel.readString();
                    int readInt43 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int blockedChannelCount = getBlockedChannelCount(readString67, readInt43);
                    parcel2.writeNoException();
                    parcel2.writeInt(blockedChannelCount);
                    return true;
                case 55:
                    String readString68 = parcel.readString();
                    String readString69 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    deleteNotificationChannelGroup(readString68, readString69);
                    parcel2.writeNoException();
                    return true;
                case 56:
                    String readString70 = parcel.readString();
                    String readString71 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    NotificationChannelGroup notificationChannelGroup2 = getNotificationChannelGroup(readString70, readString71);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(notificationChannelGroup2, 1);
                    return true;
                case 57:
                    String readString72 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice notificationChannelGroups = getNotificationChannelGroups(readString72);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(notificationChannelGroups, 1);
                    return true;
                case 58:
                    String readString73 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice notificationChannelGroupsWithoutChannels = getNotificationChannelGroupsWithoutChannels(readString73);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(notificationChannelGroupsWithoutChannels, 1);
                    return true;
                case 59:
                    String readString74 = parcel.readString();
                    int readInt44 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean onlyHasDefaultChannel = onlyHasDefaultChannel(readString74, readInt44);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(onlyHasDefaultChannel);
                    return true;
                case 60:
                    boolean areChannelsBypassingDnd = areChannelsBypassingDnd();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(areChannelsBypassingDnd);
                    return true;
                case 61:
                    String readString75 = parcel.readString();
                    int readInt45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice notificationChannelsBypassingDnd = getNotificationChannelsBypassingDnd(readString75, readInt45);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(notificationChannelsBypassingDnd, 1);
                    return true;
                case 62:
                    int readInt46 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice packagesBypassingDnd = getPackagesBypassingDnd(readInt46);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(packagesBypassingDnd, 1);
                    return true;
                case 63:
                    int readInt47 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> packagesWithAnyChannels = getPackagesWithAnyChannels(readInt47);
                    parcel2.writeNoException();
                    parcel2.writeStringList(packagesWithAnyChannels);
                    return true;
                case 64:
                    String readString76 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isPackagePaused = isPackagePaused(readString76);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPackagePaused);
                    return true;
                case 65:
                    String readString77 = parcel.readString();
                    int readInt48 = parcel.readInt();
                    long readLong = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    deleteNotificationHistoryItem(readString77, readInt48, readLong);
                    parcel2.writeNoException();
                    return true;
                case 66:
                    String readString78 = parcel.readString();
                    int readInt49 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isPermissionFixed = isPermissionFixed(readString78, readInt49);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPermissionFixed);
                    return true;
                case 67:
                    silenceNotificationSound();
                    parcel2.writeNoException();
                    return true;
                case 68:
                    String readString79 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    StatusBarNotification[] activeNotifications = getActiveNotifications(readString79);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(activeNotifications, 1);
                    return true;
                case 69:
                    String readString80 = parcel.readString();
                    String readString81 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    StatusBarNotification[] activeNotificationsWithAttribution = getActiveNotificationsWithAttribution(readString80, readString81);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(activeNotificationsWithAttribution, 1);
                    return true;
                case 70:
                    String readString82 = parcel.readString();
                    int readInt50 = parcel.readInt();
                    boolean readBoolean16 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    StatusBarNotification[] historicalNotifications = getHistoricalNotifications(readString82, readInt50, readBoolean16);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(historicalNotifications, 1);
                    return true;
                case 71:
                    String readString83 = parcel.readString();
                    String readString84 = parcel.readString();
                    int readInt51 = parcel.readInt();
                    boolean readBoolean17 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    StatusBarNotification[] historicalNotificationsWithAttribution = getHistoricalNotificationsWithAttribution(readString83, readString84, readInt51, readBoolean17);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(historicalNotificationsWithAttribution, 1);
                    return true;
                case 72:
                    String readString85 = parcel.readString();
                    String readString86 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    NotificationHistory notificationHistory = getNotificationHistory(readString85, readString86);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(notificationHistory, 1);
                    return true;
                case 73:
                    INotificationListener asInterface3 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt52 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerListener(asInterface3, componentName, readInt52);
                    parcel2.writeNoException();
                    return true;
                case 74:
                    INotificationListener asInterface4 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt53 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterListener(asInterface4, readInt53);
                    parcel2.writeNoException();
                    return true;
                case 75:
                    INotificationListener asInterface5 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    String readString87 = parcel.readString();
                    String readString88 = parcel.readString();
                    int readInt54 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cancelNotificationFromListener(asInterface5, readString87, readString88, readInt54);
                    parcel2.writeNoException();
                    return true;
                case 76:
                    INotificationListener asInterface6 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    String[] createStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    cancelNotificationsFromListener(asInterface6, createStringArray);
                    parcel2.writeNoException();
                    return true;
                case 77:
                    INotificationListener asInterface7 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    String readString89 = parcel.readString();
                    String readString90 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    snoozeNotificationUntilContextFromListener(asInterface7, readString89, readString90);
                    parcel2.writeNoException();
                    return true;
                case 78:
                    INotificationListener asInterface8 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    String readString91 = parcel.readString();
                    long readLong2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    snoozeNotificationUntilFromListener(asInterface8, readString91, readLong2);
                    parcel2.writeNoException();
                    return true;
                case 79:
                    ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestBindListener(componentName2);
                    parcel2.writeNoException();
                    return true;
                case 80:
                    INotificationListener asInterface9 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestUnbindListener(asInterface9);
                    parcel2.writeNoException();
                    return true;
                case 81:
                    ComponentName componentName3 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestUnbindListenerComponent(componentName3);
                    parcel2.writeNoException();
                    return true;
                case 82:
                    ComponentName componentName4 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestBindProvider(componentName4);
                    parcel2.writeNoException();
                    return true;
                case 83:
                    IConditionProvider asInterface10 = IConditionProvider.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestUnbindProvider(asInterface10);
                    parcel2.writeNoException();
                    return true;
                case 84:
                    INotificationListener asInterface11 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    String[] createStringArray2 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    setNotificationsShownFromListener(asInterface11, createStringArray2);
                    parcel2.writeNoException();
                    return true;
                case 85:
                    INotificationListener asInterface12 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    String[] createStringArray3 = parcel.createStringArray();
                    int readInt55 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice activeNotificationsFromListener = getActiveNotificationsFromListener(asInterface12, createStringArray3, readInt55);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(activeNotificationsFromListener, 1);
                    return true;
                case 86:
                    INotificationListener asInterface13 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt56 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice snoozedNotificationsFromListener = getSnoozedNotificationsFromListener(asInterface13, readInt56);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(snoozedNotificationsFromListener, 1);
                    return true;
                case 87:
                    INotificationListener asInterface14 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    clearRequestedListenerHints(asInterface14);
                    parcel2.writeNoException();
                    return true;
                case 88:
                    INotificationListener asInterface15 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt57 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestHintsFromListener(asInterface15, readInt57);
                    parcel2.writeNoException();
                    return true;
                case 89:
                    INotificationListener asInterface16 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int hintsFromListener = getHintsFromListener(asInterface16);
                    parcel2.writeNoException();
                    parcel2.writeInt(hintsFromListener);
                    return true;
                case 90:
                    int hintsFromListenerNoToken = getHintsFromListenerNoToken();
                    parcel2.writeNoException();
                    parcel2.writeInt(hintsFromListenerNoToken);
                    return true;
                case 91:
                    INotificationListener asInterface17 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt58 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestInterruptionFilterFromListener(asInterface17, readInt58);
                    parcel2.writeNoException();
                    return true;
                case 92:
                    INotificationListener asInterface18 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int interruptionFilterFromListener = getInterruptionFilterFromListener(asInterface18);
                    parcel2.writeNoException();
                    parcel2.writeInt(interruptionFilterFromListener);
                    return true;
                case 93:
                    INotificationListener asInterface19 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt59 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setOnNotificationPostedTrimFromListener(asInterface19, readInt59);
                    parcel2.writeNoException();
                    return true;
                case 94:
                    String readString92 = parcel.readString();
                    int readInt60 = parcel.readInt();
                    boolean readBoolean18 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setInterruptionFilter(readString92, readInt60, readBoolean18);
                    parcel2.writeNoException();
                    return true;
                case 95:
                    INotificationListener asInterface20 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    String readString93 = parcel.readString();
                    UserHandle userHandle2 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    String readString94 = parcel.readString();
                    String readString95 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    NotificationChannel createConversationNotificationChannelForPackageFromPrivilegedListener = createConversationNotificationChannelForPackageFromPrivilegedListener(asInterface20, readString93, userHandle2, readString94, readString95);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(createConversationNotificationChannelForPackageFromPrivilegedListener, 1);
                    return true;
                case 96:
                    INotificationListener asInterface21 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    String readString96 = parcel.readString();
                    UserHandle userHandle3 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    NotificationChannelGroup notificationChannelGroup3 = (NotificationChannelGroup) parcel.readTypedObject(NotificationChannelGroup.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateNotificationChannelGroupFromPrivilegedListener(asInterface21, readString96, userHandle3, notificationChannelGroup3);
                    parcel2.writeNoException();
                    return true;
                case 97:
                    INotificationListener asInterface22 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    String readString97 = parcel.readString();
                    UserHandle userHandle4 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    NotificationChannel notificationChannel4 = (NotificationChannel) parcel.readTypedObject(NotificationChannel.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateNotificationChannelFromPrivilegedListener(asInterface22, readString97, userHandle4, notificationChannel4);
                    parcel2.writeNoException();
                    return true;
                case 98:
                    INotificationListener asInterface23 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    String readString98 = parcel.readString();
                    UserHandle userHandle5 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    ParceledListSlice notificationChannelsFromPrivilegedListener = getNotificationChannelsFromPrivilegedListener(asInterface23, readString98, userHandle5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(notificationChannelsFromPrivilegedListener, 1);
                    return true;
                case 99:
                    INotificationListener asInterface24 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    String readString99 = parcel.readString();
                    UserHandle userHandle6 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    ParceledListSlice notificationChannelGroupsFromPrivilegedListener = getNotificationChannelGroupsFromPrivilegedListener(asInterface24, readString99, userHandle6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(notificationChannelGroupsFromPrivilegedListener, 1);
                    return true;
                case 100:
                    INotificationListener asInterface25 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    Adjustment adjustment = (Adjustment) parcel.readTypedObject(Adjustment.CREATOR);
                    parcel.enforceNoDataAvail();
                    applyEnqueuedAdjustmentFromAssistant(asInterface25, adjustment);
                    parcel2.writeNoException();
                    return true;
                case 101:
                    INotificationListener asInterface26 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    Adjustment adjustment2 = (Adjustment) parcel.readTypedObject(Adjustment.CREATOR);
                    parcel.enforceNoDataAvail();
                    applyAdjustmentFromAssistant(asInterface26, adjustment2);
                    parcel2.writeNoException();
                    return true;
                case 102:
                    INotificationListener asInterface27 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    ArrayList createTypedArrayList = parcel.createTypedArrayList(Adjustment.CREATOR);
                    parcel.enforceNoDataAvail();
                    applyAdjustmentsFromAssistant(asInterface27, createTypedArrayList);
                    parcel2.writeNoException();
                    return true;
                case 103:
                    INotificationListener asInterface28 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    String readString100 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unsnoozeNotificationFromAssistant(asInterface28, readString100);
                    parcel2.writeNoException();
                    return true;
                case 104:
                    INotificationListener asInterface29 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    String readString101 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unsnoozeNotificationFromSystemListener(asInterface29, readString101);
                    parcel2.writeNoException();
                    return true;
                case 105:
                    ComponentName effectsSuppressor = getEffectsSuppressor();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(effectsSuppressor, 1);
                    return true;
                case 106:
                    Bundle bundle = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean matchesCallFilter = matchesCallFilter(bundle);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(matchesCallFilter);
                    return true;
                case 107:
                    long readLong3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    cleanUpCallersAfter(readLong3);
                    parcel2.writeNoException();
                    return true;
                case 108:
                    String readString102 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isSystemConditionProviderEnabled = isSystemConditionProviderEnabled(readString102);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSystemConditionProviderEnabled);
                    return true;
                case 109:
                    ComponentName componentName5 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isNotificationListenerAccessGranted = isNotificationListenerAccessGranted(componentName5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isNotificationListenerAccessGranted);
                    return true;
                case 110:
                    ComponentName componentName6 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt61 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isNotificationListenerAccessGrantedForUser = isNotificationListenerAccessGrantedForUser(componentName6, readInt61);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isNotificationListenerAccessGrantedForUser);
                    return true;
                case 111:
                    ComponentName componentName7 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean isNotificationAssistantAccessGranted = isNotificationAssistantAccessGranted(componentName7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isNotificationAssistantAccessGranted);
                    return true;
                case 112:
                    ComponentName componentName8 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    boolean readBoolean19 = parcel.readBoolean();
                    boolean readBoolean20 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNotificationListenerAccessGranted(componentName8, readBoolean19, readBoolean20);
                    parcel2.writeNoException();
                    return true;
                case 113:
                    ComponentName componentName9 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    boolean readBoolean21 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNotificationAssistantAccessGranted(componentName9, readBoolean21);
                    parcel2.writeNoException();
                    return true;
                case 114:
                    ComponentName componentName10 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt62 = parcel.readInt();
                    boolean readBoolean22 = parcel.readBoolean();
                    boolean readBoolean23 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNotificationListenerAccessGrantedForUser(componentName10, readInt62, readBoolean22, readBoolean23);
                    parcel2.writeNoException();
                    return true;
                case 115:
                    ComponentName componentName11 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt63 = parcel.readInt();
                    boolean readBoolean24 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNotificationAssistantAccessGrantedForUser(componentName11, readInt63, readBoolean24);
                    parcel2.writeNoException();
                    return true;
                case 116:
                    List<String> enabledNotificationListenerPackages = getEnabledNotificationListenerPackages();
                    parcel2.writeNoException();
                    parcel2.writeStringList(enabledNotificationListenerPackages);
                    return true;
                case 117:
                    int readInt64 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<ComponentName> enabledNotificationListeners = getEnabledNotificationListeners(readInt64);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(enabledNotificationListeners, 1);
                    return true;
                case 118:
                    int readInt65 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ComponentName allowedNotificationAssistantForUser = getAllowedNotificationAssistantForUser(readInt65);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(allowedNotificationAssistantForUser, 1);
                    return true;
                case 119:
                    ComponentName allowedNotificationAssistant = getAllowedNotificationAssistant();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(allowedNotificationAssistant, 1);
                    return true;
                case 120:
                    ComponentName defaultNotificationAssistant = getDefaultNotificationAssistant();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(defaultNotificationAssistant, 1);
                    return true;
                case 121:
                    int readInt66 = parcel.readInt();
                    boolean readBoolean25 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNASMigrationDoneAndResetDefault(readInt66, readBoolean25);
                    parcel2.writeNoException();
                    return true;
                case 122:
                    String readString103 = parcel.readString();
                    int readInt67 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean hasEnabledNotificationListener = hasEnabledNotificationListener(readString103, readInt67);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(hasEnabledNotificationListener);
                    return true;
                case 123:
                    int zenMode = getZenMode();
                    parcel2.writeNoException();
                    parcel2.writeInt(zenMode);
                    return true;
                case 124:
                    ZenModeConfig zenModeConfig = getZenModeConfig();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(zenModeConfig, 1);
                    return true;
                case 125:
                    NotificationManager.Policy consolidatedNotificationPolicy = getConsolidatedNotificationPolicy();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(consolidatedNotificationPolicy, 1);
                    return true;
                case 126:
                    int readInt68 = parcel.readInt();
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    String readString104 = parcel.readString();
                    boolean readBoolean26 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setZenMode(readInt68, uri, readString104, readBoolean26);
                    return true;
                case 127:
                    String readString105 = parcel.readString();
                    IConditionProvider asInterface30 = IConditionProvider.Stub.asInterface(parcel.readStrongBinder());
                    Condition[] conditionArr = (Condition[]) parcel.createTypedArray(Condition.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyConditions(readString105, asInterface30, conditionArr);
                    return true;
                case 128:
                    String readString106 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isNotificationPolicyAccessGranted = isNotificationPolicyAccessGranted(readString106);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isNotificationPolicyAccessGranted);
                    return true;
                case 129:
                    String readString107 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    NotificationManager.Policy notificationPolicy = getNotificationPolicy(readString107);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(notificationPolicy, 1);
                    return true;
                case 130:
                    String readString108 = parcel.readString();
                    NotificationManager.Policy policy = (NotificationManager.Policy) parcel.readTypedObject(NotificationManager.Policy.CREATOR);
                    boolean readBoolean27 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNotificationPolicy(readString108, policy, readBoolean27);
                    parcel2.writeNoException();
                    return true;
                case 131:
                    String readString109 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isNotificationPolicyAccessGrantedForPackage = isNotificationPolicyAccessGrantedForPackage(readString109);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isNotificationPolicyAccessGrantedForPackage);
                    return true;
                case 132:
                    String readString110 = parcel.readString();
                    boolean readBoolean28 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNotificationPolicyAccessGranted(readString110, readBoolean28);
                    parcel2.writeNoException();
                    return true;
                case 133:
                    String readString111 = parcel.readString();
                    int readInt69 = parcel.readInt();
                    boolean readBoolean29 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNotificationPolicyAccessGrantedForUser(readString111, readInt69, readBoolean29);
                    parcel2.writeNoException();
                    return true;
                case 134:
                    ZenPolicy defaultZenPolicy = getDefaultZenPolicy();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(defaultZenPolicy, 1);
                    return true;
                case 135:
                    String readString112 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    AutomaticZenRule automaticZenRule = getAutomaticZenRule(readString112);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(automaticZenRule, 1);
                    return true;
                case 136:
                    ParceledListSlice automaticZenRules = getAutomaticZenRules();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(automaticZenRules, 1);
                    return true;
                case 137:
                    AutomaticZenRule automaticZenRule2 = (AutomaticZenRule) parcel.readTypedObject(AutomaticZenRule.CREATOR);
                    String readString113 = parcel.readString();
                    boolean readBoolean30 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    String addAutomaticZenRule = addAutomaticZenRule(automaticZenRule2, readString113, readBoolean30);
                    parcel2.writeNoException();
                    parcel2.writeString(addAutomaticZenRule);
                    return true;
                case 138:
                    String readString114 = parcel.readString();
                    AutomaticZenRule automaticZenRule3 = (AutomaticZenRule) parcel.readTypedObject(AutomaticZenRule.CREATOR);
                    boolean readBoolean31 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean updateAutomaticZenRule = updateAutomaticZenRule(readString114, automaticZenRule3, readBoolean31);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(updateAutomaticZenRule);
                    return true;
                case 139:
                    String readString115 = parcel.readString();
                    boolean readBoolean32 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean removeAutomaticZenRule = removeAutomaticZenRule(readString115, readBoolean32);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeAutomaticZenRule);
                    return true;
                case 140:
                    String readString116 = parcel.readString();
                    boolean readBoolean33 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean removeAutomaticZenRules = removeAutomaticZenRules(readString116, readBoolean33);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeAutomaticZenRules);
                    return true;
                case 141:
                    ComponentName componentName12 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    int ruleInstanceCount = getRuleInstanceCount(componentName12);
                    parcel2.writeNoException();
                    parcel2.writeInt(ruleInstanceCount);
                    return true;
                case 142:
                    String readString117 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int automaticZenRuleState = getAutomaticZenRuleState(readString117);
                    parcel2.writeNoException();
                    parcel2.writeInt(automaticZenRuleState);
                    return true;
                case 143:
                    String readString118 = parcel.readString();
                    Condition condition = (Condition) parcel.readTypedObject(Condition.CREATOR);
                    parcel.enforceNoDataAvail();
                    setAutomaticZenRuleState(readString118, condition);
                    parcel2.writeNoException();
                    return true;
                case 144:
                    ZenDeviceEffects zenDeviceEffects = (ZenDeviceEffects) parcel.readTypedObject(ZenDeviceEffects.CREATOR);
                    parcel.enforceNoDataAvail();
                    setManualZenRuleDeviceEffects(zenDeviceEffects);
                    parcel2.writeNoException();
                    return true;
                case 145:
                    int readInt70 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] backupPayload = getBackupPayload(readInt70);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(backupPayload);
                    return true;
                case 146:
                    byte[] createByteArray = parcel.createByteArray();
                    int readInt71 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    applyRestore(createByteArray, readInt71);
                    parcel2.writeNoException();
                    return true;
                case 147:
                    String readString119 = parcel.readString();
                    int readInt72 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice appActiveNotifications = getAppActiveNotifications(readString119, readInt72);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(appActiveNotifications, 1);
                    return true;
                case 148:
                    String readString120 = parcel.readString();
                    String readString121 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setNotificationDelegate(readString120, readString121);
                    parcel2.writeNoException();
                    return true;
                case 149:
                    String readString122 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String notificationDelegate = getNotificationDelegate(readString122);
                    parcel2.writeNoException();
                    parcel2.writeString(notificationDelegate);
                    return true;
                case 150:
                    String readString123 = parcel.readString();
                    String readString124 = parcel.readString();
                    int readInt73 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean canNotifyAsPackage = canNotifyAsPackage(readString123, readString124, readInt73);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canNotifyAsPackage);
                    return true;
                case 151:
                    AttributionSource attributionSource = (AttributionSource) parcel.readTypedObject(AttributionSource.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean canUseFullScreenIntent = canUseFullScreenIntent(attributionSource);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canUseFullScreenIntent);
                    return true;
                case 152:
                    boolean readBoolean34 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setPrivateNotificationsAllowed(readBoolean34);
                    parcel2.writeNoException();
                    return true;
                case 153:
                    boolean privateNotificationsAllowed = getPrivateNotificationsAllowed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(privateNotificationsAllowed);
                    return true;
                case 154:
                    long readLong4 = parcel.readLong();
                    int readInt74 = parcel.readInt();
                    boolean readBoolean35 = parcel.readBoolean();
                    ArrayList arrayList = new ArrayList();
                    parcel.enforceNoDataAvail();
                    long pullStats = pullStats(readLong4, readInt74, readBoolean35, arrayList);
                    parcel2.writeNoException();
                    parcel2.writeLong(pullStats);
                    parcel2.writeTypedList(arrayList, 1);
                    return true;
                case 155:
                    ComponentName componentName13 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt75 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    NotificationListenerFilter listenerFilter = getListenerFilter(componentName13, readInt75);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(listenerFilter, 1);
                    return true;
                case 156:
                    ComponentName componentName14 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt76 = parcel.readInt();
                    NotificationListenerFilter notificationListenerFilter = (NotificationListenerFilter) parcel.readTypedObject(NotificationListenerFilter.CREATOR);
                    parcel.enforceNoDataAvail();
                    setListenerFilter(componentName14, readInt76, notificationListenerFilter);
                    parcel2.writeNoException();
                    return true;
                case 157:
                    INotificationListener asInterface31 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    int readInt77 = parcel.readInt();
                    ArrayList<String> createStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    migrateNotificationFilter(asInterface31, readInt77, createStringArrayList);
                    parcel2.writeNoException();
                    return true;
                case 158:
                    boolean readBoolean36 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setToastRateLimitingEnabled(readBoolean36);
                    parcel2.writeNoException();
                    return true;
                case 159:
                    String readString125 = parcel.readString();
                    UserHandle userHandle7 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    ICallNotificationEventCallback asInterface32 = ICallNotificationEventCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerCallNotificationEventListener(readString125, userHandle7, asInterface32);
                    parcel2.writeNoException();
                    return true;
                case 160:
                    String readString126 = parcel.readString();
                    UserHandle userHandle8 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    ICallNotificationEventCallback asInterface33 = ICallNotificationEventCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterCallNotificationEventListener(readString126, userHandle8, asInterface33);
                    parcel2.writeNoException();
                    return true;
                case 161:
                    String readString127 = parcel.readString();
                    int readInt78 = parcel.readInt();
                    boolean readBoolean37 = parcel.readBoolean();
                    boolean readBoolean38 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setCanBePromoted(readString127, readInt78, readBoolean37, readBoolean38);
                    parcel2.writeNoException();
                    return true;
                case 162:
                    String readString128 = parcel.readString();
                    int readInt79 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean appCanBePromoted = appCanBePromoted(readString128, readInt79);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(appCanBePromoted);
                    return true;
                case 163:
                    String readString129 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean canBePromoted = canBePromoted(readString129);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canBePromoted);
                    return true;
                case 164:
                    INotificationListener asInterface34 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    String readString130 = parcel.readString();
                    boolean readBoolean39 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAdjustmentTypeSupportedState(asInterface34, readString130, readBoolean39);
                    parcel2.writeNoException();
                    return true;
                case 165:
                    List<String> unsupportedAdjustmentTypes = getUnsupportedAdjustmentTypes();
                    parcel2.writeNoException();
                    parcel2.writeStringList(unsupportedAdjustmentTypes);
                    return true;
                case 166:
                    int[] allowedAdjustmentKeyTypes = getAllowedAdjustmentKeyTypes();
                    parcel2.writeNoException();
                    parcel2.writeIntArray(allowedAdjustmentKeyTypes);
                    return true;
                case 167:
                    int readInt80 = parcel.readInt();
                    boolean readBoolean40 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAssistantAdjustmentKeyTypeState(readInt80, readBoolean40);
                    parcel2.writeNoException();
                    return true;
                case 168:
                    String readString131 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String[] adjustmentDeniedPackages = getAdjustmentDeniedPackages(readString131);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(adjustmentDeniedPackages);
                    return true;
                case 169:
                    String readString132 = parcel.readString();
                    String readString133 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isAdjustmentSupportedForPackage = isAdjustmentSupportedForPackage(readString132, readString133);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAdjustmentSupportedForPackage);
                    return true;
                case 170:
                    String readString134 = parcel.readString();
                    String readString135 = parcel.readString();
                    boolean readBoolean41 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAdjustmentSupportedForPackage(readString134, readString135, readBoolean41);
                    parcel2.writeNoException();
                    return true;
                case 171:
                    String readString136 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    incrementCounter(readString136);
                    parcel2.writeNoException();
                    return true;
                case 172:
                    ComponentName componentName15 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int readInt81 = parcel.readInt();
                    boolean readBoolean42 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    registerNotificationListener(componentName15, readInt81, readBoolean42);
                    parcel2.writeNoException();
                    return true;
                case 173:
                    String readString137 = parcel.readString();
                    ParceledListSlice parceledListSlice4 = (ParceledListSlice) parcel.readTypedObject(ParceledListSlice.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateNotificationChannels(readString137, parceledListSlice4);
                    parcel2.writeNoException();
                    return true;
                case 174:
                    IBinder readStrongBinder5 = parcel.readStrongBinder();
                    int readInt82 = parcel.readInt();
                    ComponentName componentName16 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    bindEdgeLightingService(readStrongBinder5, readInt82, componentName16);
                    parcel2.writeNoException();
                    return true;
                case 175:
                    IBinder readStrongBinder6 = parcel.readStrongBinder();
                    String readString138 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unbindEdgeLightingService(readStrongBinder6, readString138);
                    parcel2.writeNoException();
                    return true;
                case 176:
                    String readString139 = parcel.readString();
                    ArrayList<String> createStringArrayList2 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    updateEdgeLightingPackageList(readString139, createStringArrayList2);
                    parcel2.writeNoException();
                    return true;
                case 177:
                    String readString140 = parcel.readString();
                    EdgeLightingPolicy edgeLightingPolicy = (EdgeLightingPolicy) parcel.readTypedObject(EdgeLightingPolicy.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateEdgeLightingPolicy(readString140, edgeLightingPolicy);
                    parcel2.writeNoException();
                    return true;
                case 178:
                    IBinder readStrongBinder7 = parcel.readStrongBinder();
                    ComponentName componentName17 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerEdgeLightingListener(readStrongBinder7, componentName17);
                    parcel2.writeNoException();
                    return true;
                case 179:
                    IBinder readStrongBinder8 = parcel.readStrongBinder();
                    String readString141 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unregisterEdgeLightingListener(readStrongBinder8, readString141);
                    parcel2.writeNoException();
                    return true;
                case 180:
                    String readString142 = parcel.readString();
                    SemEdgeLightingInfo semEdgeLightingInfo = (SemEdgeLightingInfo) parcel.readTypedObject(SemEdgeLightingInfo.CREATOR);
                    IBinder readStrongBinder9 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    startEdgeLighting(readString142, semEdgeLightingInfo, readStrongBinder9);
                    parcel2.writeNoException();
                    return true;
                case 181:
                    String readString143 = parcel.readString();
                    IBinder readStrongBinder10 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    stopEdgeLighting(readString143, readStrongBinder10);
                    parcel2.writeNoException();
                    return true;
                case 182:
                    int edgeLightingState = getEdgeLightingState();
                    parcel2.writeNoException();
                    parcel2.writeInt(edgeLightingState);
                    return true;
                case 183:
                    String readString144 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean isEdgeLightingNotificationAllowed = isEdgeLightingNotificationAllowed(readString144);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isEdgeLightingNotificationAllowed);
                    return true;
                case 184:
                    int readInt83 = parcel.readInt();
                    String readString145 = parcel.readString();
                    IBinder readStrongBinder11 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    disable(readInt83, readString145, readStrongBinder11);
                    parcel2.writeNoException();
                    return true;
                case 185:
                    String readString146 = parcel.readString();
                    boolean readBoolean43 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    disableEdgeLightingNotification(readString146, readBoolean43);
                    parcel2.writeNoException();
                    return true;
                case 186:
                    String readString147 = parcel.readString();
                    int readInt84 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isPackageEnabled = isPackageEnabled(readString147, readInt84);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isPackageEnabled);
                    return true;
                case 187:
                    String readString148 = parcel.readString();
                    String readString149 = parcel.readString();
                    int readInt85 = parcel.readInt();
                    int readInt86 = parcel.readInt();
                    String readString150 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    cancelNotificationByEdge(readString148, readString149, readInt85, readInt86, readString150);
                    parcel2.writeNoException();
                    return true;
                case 188:
                    String readString151 = parcel.readString();
                    String readString152 = parcel.readString();
                    int readInt87 = parcel.readInt();
                    int readInt88 = parcel.readInt();
                    String readString153 = parcel.readString();
                    String readString154 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    cancelNotificationByGroupKey(readString151, readString152, readInt87, readInt88, readString153, readString154);
                    parcel2.writeNoException();
                    return true;
                case 189:
                    String readString155 = parcel.readString();
                    String readString156 = parcel.readString();
                    int readInt89 = parcel.readInt();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int readInt90 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enqueueEdgeNotification(readString155, readString156, readInt89, bundle2, readInt90);
                    parcel2.writeNoException();
                    return true;
                case 190:
                    String readString157 = parcel.readString();
                    int readInt91 = parcel.readInt();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int readInt92 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeEdgeNotification(readString157, readInt91, bundle3, readInt92);
                    parcel2.writeNoException();
                    return true;
                case 191:
                    String readString158 = parcel.readString();
                    int readInt93 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isEdgeLightingAllowed = isEdgeLightingAllowed(readString158, readInt93);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isEdgeLightingAllowed);
                    return true;
                case 192:
                    String readString159 = parcel.readString();
                    int readInt94 = parcel.readInt();
                    boolean readBoolean44 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAllowEdgeLighting(readString159, readInt94, readBoolean44);
                    parcel2.writeNoException();
                    return true;
                case 193:
                    resetDefaultAllowEdgeLighting();
                    parcel2.writeNoException();
                    return true;
                case 194:
                    String readString160 = parcel.readString();
                    int readInt95 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int edgeLightingSettingState = getEdgeLightingSettingState(readString160, readInt95);
                    parcel2.writeNoException();
                    parcel2.writeInt(edgeLightingSettingState);
                    return true;
                case 195:
                    String readString161 = parcel.readString();
                    int readInt96 = parcel.readInt();
                    int readInt97 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setEdgeLightingState(readString161, readInt96, readInt97);
                    parcel2.writeNoException();
                    return true;
                case 196:
                    resetDefaultEdgeLightingState();
                    parcel2.writeNoException();
                    return true;
                case 197:
                    int readInt98 = parcel.readInt();
                    String readString162 = parcel.readString();
                    String readString163 = parcel.readString();
                    int readInt99 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean dispatchDelayedWakelockAndBlocked = dispatchDelayedWakelockAndBlocked(readInt98, readString162, readString163, readInt99);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dispatchDelayedWakelockAndBlocked);
                    return true;
                case 198:
                    int readInt100 = parcel.readInt();
                    String readString164 = parcel.readString();
                    String readString165 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean dispatchDelayedWakeUpAndBlocked = dispatchDelayedWakeUpAndBlocked(readInt100, readString164, readString165);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(dispatchDelayedWakeUpAndBlocked);
                    return true;
                case 199:
                    String readString166 = parcel.readString();
                    int readInt101 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isSubDisplayNotificationAllowed = isSubDisplayNotificationAllowed(readString166, readInt101);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isSubDisplayNotificationAllowed);
                    return true;
                case 200:
                    String readString167 = parcel.readString();
                    int readInt102 = parcel.readInt();
                    boolean readBoolean45 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAllowSubDisplayNotification(readString167, readInt102, readBoolean45);
                    parcel2.writeNoException();
                    return true;
                case 201:
                    String readString168 = parcel.readString();
                    int readInt103 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isOngoingActivityAllowed = isOngoingActivityAllowed(readString168, readInt103);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isOngoingActivityAllowed);
                    return true;
                case 202:
                    String readString169 = parcel.readString();
                    int readInt104 = parcel.readInt();
                    boolean readBoolean46 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAllowOngoingActivity(readString169, readInt104, readBoolean46);
                    parcel2.writeNoException();
                    return true;
                case 203:
                    resetDefaultAllowOngoingActivity();
                    parcel2.writeNoException();
                    return true;
                case 204:
                    List<String> allowedOngoingActivityAppList = getAllowedOngoingActivityAppList();
                    parcel2.writeNoException();
                    parcel2.writeStringList(allowedOngoingActivityAppList);
                    return true;
                case 205:
                    String readString170 = parcel.readString();
                    int readInt105 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean notificationAlertsEnabledForPackage = getNotificationAlertsEnabledForPackage(readString170, readInt105);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(notificationAlertsEnabledForPackage);
                    return true;
                case 206:
                    String readString171 = parcel.readString();
                    int readInt106 = parcel.readInt();
                    boolean readBoolean47 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNotificationAlertsEnabledForPackage(readString171, readInt106, readBoolean47);
                    parcel2.writeNoException();
                    return true;
                case 207:
                    int readInt107 = parcel.readInt();
                    ArrayList<String> createStringArrayList3 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean wearableAppList = setWearableAppList(readInt107, createStringArrayList3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(wearableAppList);
                    return true;
                case 208:
                    int readInt108 = parcel.readInt();
                    String readString172 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean addWearableAppToList = addWearableAppToList(readInt108, readString172);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(addWearableAppToList);
                    return true;
                case 209:
                    int readInt109 = parcel.readInt();
                    String readString173 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean removeWearableAppFromList = removeWearableAppFromList(readInt109, readString173);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(removeWearableAppFromList);
                    return true;
                case 210:
                    int readInt110 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> wearableAppList2 = getWearableAppList(readInt110);
                    parcel2.writeNoException();
                    parcel2.writeStringList(wearableAppList2);
                    return true;
                case 211:
                    int readInt111 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean requestListenerHintsForWearable = requestListenerHintsForWearable(readInt111);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(requestListenerHintsForWearable);
                    return true;
                case 212:
                    String readString174 = parcel.readString();
                    int readInt112 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int lockScreenNotificationVisibilityForPackage = getLockScreenNotificationVisibilityForPackage(readString174, readInt112);
                    parcel2.writeNoException();
                    parcel2.writeInt(lockScreenNotificationVisibilityForPackage);
                    return true;
                case 213:
                    String readString175 = parcel.readString();
                    int readInt113 = parcel.readInt();
                    int readInt114 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setLockScreenNotificationVisibilityForPackage(readString175, readInt113, readInt114);
                    parcel2.writeNoException();
                    return true;
                case 214:
                    String readString176 = parcel.readString();
                    int readInt115 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isAllowNotificationPopUpForPackage = isAllowNotificationPopUpForPackage(readString176, readInt115);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAllowNotificationPopUpForPackage);
                    return true;
                case 215:
                    String readString177 = parcel.readString();
                    int readInt116 = parcel.readInt();
                    boolean readBoolean48 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAllowNotificationPopUpForPackage(readString177, readInt116, readBoolean48);
                    parcel2.writeNoException();
                    return true;
                case 216:
                    String readString178 = parcel.readString();
                    int readInt117 = parcel.readInt();
                    String readString179 = parcel.readString();
                    int readInt118 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isAlertsAllowed = isAlertsAllowed(readString178, readInt117, readString179, readInt118);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isAlertsAllowed);
                    return true;
                case 217:
                    String readString180 = parcel.readString();
                    int readInt119 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean isReminderEnabled = isReminderEnabled(readString180, readInt119);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(isReminderEnabled);
                    return true;
                case 218:
                    String readString181 = parcel.readString();
                    int readInt120 = parcel.readInt();
                    boolean readBoolean49 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setReminderEnabledForPackage(readString181, readInt120, readBoolean49);
                    parcel2.writeNoException();
                    return true;
                case 219:
                    int readInt121 = parcel.readInt();
                    boolean readBoolean50 = parcel.readBoolean();
                    ArrayList<String> createStringArrayList4 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    setReminderEnabled(readInt121, readBoolean50, createStringArrayList4);
                    parcel2.writeNoException();
                    return true;
                case 220:
                    int readInt122 = parcel.readInt();
                    String readString182 = parcel.readString();
                    String readString183 = parcel.readString();
                    int readInt123 = parcel.readInt();
                    String readString184 = parcel.readString();
                    String readString185 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addReplyHistory(readInt122, readString182, readString183, readInt123, readString184, readString185);
                    parcel2.writeNoException();
                    return true;
                case 221:
                    String readString186 = parcel.readString();
                    String readString187 = parcel.readString();
                    int readInt124 = parcel.readInt();
                    String readString188 = parcel.readString();
                    String readString189 = parcel.readString();
                    int readInt125 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<Bundle> notificationHistoryDataForPackage = getNotificationHistoryDataForPackage(readString186, readString187, readInt124, readString188, readString189, readInt125);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(notificationHistoryDataForPackage, 1);
                    return true;
                case 222:
                    String readString190 = parcel.readString();
                    String readString191 = parcel.readString();
                    int readInt126 = parcel.readInt();
                    String readString192 = parcel.readString();
                    String readString193 = parcel.readString();
                    int readInt127 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    NotificationHistory notificationHistoryForPackage = getNotificationHistoryForPackage(readString190, readString191, readInt126, readString192, readString193, readInt127);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(notificationHistoryForPackage, 1);
                    return true;
                case 223:
                    int readInt128 = parcel.readInt();
                    String readString194 = parcel.readString();
                    boolean readBoolean51 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    updateCancelEvent(readInt128, readString194, readBoolean51);
                    parcel2.writeNoException();
                    return true;
                case 224:
                    ArrayList<String> createStringArrayList5 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    setRestoreBlockListForSS(createStringArrayList5);
                    parcel2.writeNoException();
                    return true;
                case 225:
                    int allNotificationListenersCount = getAllNotificationListenersCount();
                    parcel2.writeNoException();
                    parcel2.writeInt(allNotificationListenersCount);
                    return true;
                case 226:
                    int readInt129 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int blockedAppCount = getBlockedAppCount(readInt129);
                    parcel2.writeNoException();
                    parcel2.writeInt(blockedAppCount);
                    return true;
                case 227:
                    String readString195 = parcel.readString();
                    int readInt130 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean canAppBypassDnd = canAppBypassDnd(readString195, readInt130);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(canAppBypassDnd);
                    return true;
                case 228:
                    String readString196 = parcel.readString();
                    int readInt131 = parcel.readInt();
                    boolean readBoolean52 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAppBypassDnd(readString196, readInt131, readBoolean52);
                    parcel2.writeNoException();
                    return true;
                case 229:
                    int readInt132 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int appsBypassingDndCount = getAppsBypassingDndCount(readInt132);
                    parcel2.writeNoException();
                    parcel2.writeInt(appsBypassingDndCount);
                    return true;
                case 230:
                    String readString197 = parcel.readString();
                    IBinder readStrongBinder12 = parcel.readStrongBinder();
                    CharSequence charSequence2 = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    int readInt133 = parcel.readInt();
                    boolean readBoolean53 = parcel.readBoolean();
                    int readInt134 = parcel.readInt();
                    ITransientNotificationCallback asInterface35 = ITransientNotificationCallback.Stub.asInterface(parcel.readStrongBinder());
                    String readString198 = parcel.readString();
                    int readInt135 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enqueueTextToastForDex(readString197, readStrongBinder12, charSequence2, readInt133, readBoolean53, readInt134, asInterface35, readString198, readInt135);
                    parcel2.writeNoException();
                    return true;
                case 231:
                    String readString199 = parcel.readString();
                    IBinder readStrongBinder13 = parcel.readStrongBinder();
                    ITransientNotification asInterface36 = ITransientNotification.Stub.asInterface(parcel.readStrongBinder());
                    int readInt136 = parcel.readInt();
                    boolean readBoolean54 = parcel.readBoolean();
                    int readInt137 = parcel.readInt();
                    String readString200 = parcel.readString();
                    int readInt138 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enqueueToastForDex(readString199, readStrongBinder13, asInterface36, readInt136, readBoolean54, readInt137, readString200, readInt138);
                    parcel2.writeNoException();
                    return true;
                case 232:
                    String readString201 = parcel.readString();
                    int readInt139 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int isNotificationTurnedOff = isNotificationTurnedOff(readString201, readInt139);
                    parcel2.writeNoException();
                    parcel2.writeInt(isNotificationTurnedOff);
                    return true;
                case 233:
                    String readString202 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int notificationSoundStatus = getNotificationSoundStatus(readString202);
                    parcel2.writeNoException();
                    parcel2.writeInt(notificationSoundStatus);
                    return true;
                case 234:
                    String readString203 = parcel.readString();
                    int readInt140 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean notificationTurnOff = setNotificationTurnOff(readString203, readInt140);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(notificationTurnOff);
                    return true;
                case 235:
                    boolean readBoolean55 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int notificationSettingStatus = getNotificationSettingStatus(readBoolean55);
                    parcel2.writeNoException();
                    parcel2.writeInt(notificationSettingStatus);
                    return true;
                case 236:
                    String readString204 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int appNotificationSettingStatus = getAppNotificationSettingStatus(readString204);
                    parcel2.writeNoException();
                    parcel2.writeInt(appNotificationSettingStatus);
                    return true;
                case 237:
                    String readString205 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> blockInfoOfNotificationsForOverflow = getBlockInfoOfNotificationsForOverflow(readString205);
                    parcel2.writeNoException();
                    parcel2.writeStringList(blockInfoOfNotificationsForOverflow);
                    return true;
                default:
                    return super.onTransact(i, parcel, parcel2, i2);
            }
        }

        private static class Proxy implements INotificationManager {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            @Override // android.os.IInterface
            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            @Override // android.app.INotificationManager
            public void cancelAllNotifications(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(1, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void clearData(String str, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(2, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean enqueueTextToast(String str, IBinder iBinder, CharSequence charSequence, int i, boolean z, int i2, ITransientNotificationCallback iTransientNotificationCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iTransientNotificationCallback);
                    this.mRemote.transact(3, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean enqueueToast(String str, IBinder iBinder, ITransientNotification iTransientNotification, int i, boolean z, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iTransientNotification);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i2);
                    this.mRemote.transact(4, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void cancelToast(String str, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(5, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void finishToken(String str, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(6, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void enqueueNotificationWithTag(String str, String str2, String str3, int i, Notification notification, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(notification, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(7, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void cancelNotificationWithTag(String str, String str2, String str3, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(8, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean isInCall(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(9, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setShowBadge(String str, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(10, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean canShowBadge(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(11, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean hasSentValidMsg(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(12, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean isInInvalidMsgState(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(13, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean hasUserDemotedInvalidMsgApp(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(14, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setInvalidMsgAppDemoted(String str, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(15, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean hasSentValidBubble(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(16, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setNotificationsEnabledForPackage(String str, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(17, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setNotificationsEnabledWithImportanceLockForPackage(String str, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(18, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean areNotificationsEnabledForPackage(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(19, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean areNotificationsEnabled(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(20, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int getPackageImportance(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(21, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean isImportanceLocked(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(22, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public List<String> getAllowedAssistantAdjustments(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(23, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void allowAssistantAdjustment(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(24, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void disallowAssistantAdjustment(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(25, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean shouldHideSilentStatusIcons(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(26, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setHideSilentStatusIcons(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(27, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setBubblesAllowed(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(28, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean areBubblesAllowed(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(29, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean areBubblesEnabled(UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(30, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int getBubblePreferenceForPackage(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(31, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void createNotificationChannelGroups(String str, ParceledListSlice parceledListSlice) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(parceledListSlice, 0);
                    this.mRemote.transact(32, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void createNotificationChannels(String str, ParceledListSlice parceledListSlice) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(parceledListSlice, 0);
                    this.mRemote.transact(33, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void createNotificationChannelsForPackage(String str, int i, ParceledListSlice parceledListSlice) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(parceledListSlice, 0);
                    this.mRemote.transact(34, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public ParceledListSlice getConversations(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(35, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public ParceledListSlice getConversationsForPackage(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(36, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public ParceledListSlice getNotificationChannelGroupsForPackage(String str, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(37, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public NotificationChannelGroup getNotificationChannelGroupForPackage(String str, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(38, obtain, obtain2, 0);
                    obtain2.readException();
                    return (NotificationChannelGroup) obtain2.readTypedObject(NotificationChannelGroup.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public NotificationChannelGroup getPopulatedNotificationChannelGroupForPackage(String str, int i, String str2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(39, obtain, obtain2, 0);
                    obtain2.readException();
                    return (NotificationChannelGroup) obtain2.readTypedObject(NotificationChannelGroup.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public ParceledListSlice getRecentBlockedNotificationChannelGroupsForPackage(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(40, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void updateNotificationChannelGroupForPackage(String str, int i, NotificationChannelGroup notificationChannelGroup) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(notificationChannelGroup, 0);
                    this.mRemote.transact(41, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void updateNotificationChannelForPackage(String str, int i, NotificationChannel notificationChannel) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(notificationChannel, 0);
                    this.mRemote.transact(42, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void unlockNotificationChannel(String str, int i, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    this.mRemote.transact(43, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void unlockAllNotificationChannels() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(44, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public NotificationChannel getNotificationChannel(String str, int i, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(45, obtain, obtain2, 0);
                    obtain2.readException();
                    return (NotificationChannel) obtain2.readTypedObject(NotificationChannel.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public NotificationChannel getConversationNotificationChannel(String str, int i, String str2, String str3, boolean z, String str4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeBoolean(z);
                    obtain.writeString(str4);
                    this.mRemote.transact(46, obtain, obtain2, 0);
                    obtain2.readException();
                    return (NotificationChannel) obtain2.readTypedObject(NotificationChannel.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void createConversationNotificationChannelForPackage(String str, int i, NotificationChannel notificationChannel, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(notificationChannel, 0);
                    obtain.writeString(str2);
                    this.mRemote.transact(47, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public NotificationChannel getNotificationChannelForPackage(String str, int i, String str2, String str3, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(48, obtain, obtain2, 0);
                    obtain2.readException();
                    return (NotificationChannel) obtain2.readTypedObject(NotificationChannel.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void deleteNotificationChannel(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(49, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public ParceledListSlice getNotificationChannels(String str, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(50, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public ParceledListSlice getNotificationChannelsForPackage(String str, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(51, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int getNumNotificationChannelsForPackage(String str, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(52, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int getDeletedChannelCount(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(53, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int getBlockedChannelCount(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(54, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void deleteNotificationChannelGroup(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(55, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public NotificationChannelGroup getNotificationChannelGroup(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(56, obtain, obtain2, 0);
                    obtain2.readException();
                    return (NotificationChannelGroup) obtain2.readTypedObject(NotificationChannelGroup.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public ParceledListSlice getNotificationChannelGroups(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(57, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public ParceledListSlice getNotificationChannelGroupsWithoutChannels(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(58, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean onlyHasDefaultChannel(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(59, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean areChannelsBypassingDnd() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(60, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public ParceledListSlice getNotificationChannelsBypassingDnd(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(61, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public ParceledListSlice getPackagesBypassingDnd(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(62, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public List<String> getPackagesWithAnyChannels(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(63, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean isPackagePaused(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(64, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void deleteNotificationHistoryItem(String str, int i, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeLong(j);
                    this.mRemote.transact(65, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean isPermissionFixed(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(66, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void silenceNotificationSound() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(67, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public StatusBarNotification[] getActiveNotifications(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(68, obtain, obtain2, 0);
                    obtain2.readException();
                    return (StatusBarNotification[]) obtain2.createTypedArray(StatusBarNotification.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public StatusBarNotification[] getActiveNotificationsWithAttribution(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(69, obtain, obtain2, 0);
                    obtain2.readException();
                    return (StatusBarNotification[]) obtain2.createTypedArray(StatusBarNotification.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public StatusBarNotification[] getHistoricalNotifications(String str, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(70, obtain, obtain2, 0);
                    obtain2.readException();
                    return (StatusBarNotification[]) obtain2.createTypedArray(StatusBarNotification.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public StatusBarNotification[] getHistoricalNotificationsWithAttribution(String str, String str2, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(71, obtain, obtain2, 0);
                    obtain2.readException();
                    return (StatusBarNotification[]) obtain2.createTypedArray(StatusBarNotification.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public NotificationHistory getNotificationHistory(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(72, obtain, obtain2, 0);
                    obtain2.readException();
                    return (NotificationHistory) obtain2.readTypedObject(NotificationHistory.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void registerListener(INotificationListener iNotificationListener, ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(73, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void unregisterListener(INotificationListener iNotificationListener, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(74, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void cancelNotificationFromListener(INotificationListener iNotificationListener, String str, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(75, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void cancelNotificationsFromListener(INotificationListener iNotificationListener, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(76, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void snoozeNotificationUntilContextFromListener(INotificationListener iNotificationListener, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(77, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void snoozeNotificationUntilFromListener(INotificationListener iNotificationListener, String str, long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    obtain.writeString(str);
                    obtain.writeLong(j);
                    this.mRemote.transact(78, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void requestBindListener(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(79, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void requestUnbindListener(INotificationListener iNotificationListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    this.mRemote.transact(80, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void requestUnbindListenerComponent(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(81, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void requestBindProvider(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(82, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void requestUnbindProvider(IConditionProvider iConditionProvider) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iConditionProvider);
                    this.mRemote.transact(83, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setNotificationsShownFromListener(INotificationListener iNotificationListener, String[] strArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    obtain.writeStringArray(strArr);
                    this.mRemote.transact(84, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public ParceledListSlice getActiveNotificationsFromListener(INotificationListener iNotificationListener, String[] strArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    obtain.writeStringArray(strArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(85, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public ParceledListSlice getSnoozedNotificationsFromListener(INotificationListener iNotificationListener, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(86, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void clearRequestedListenerHints(INotificationListener iNotificationListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    this.mRemote.transact(87, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void requestHintsFromListener(INotificationListener iNotificationListener, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(88, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int getHintsFromListener(INotificationListener iNotificationListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    this.mRemote.transact(89, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int getHintsFromListenerNoToken() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(90, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void requestInterruptionFilterFromListener(INotificationListener iNotificationListener, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(91, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int getInterruptionFilterFromListener(INotificationListener iNotificationListener) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    this.mRemote.transact(92, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setOnNotificationPostedTrimFromListener(INotificationListener iNotificationListener, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    obtain.writeInt(i);
                    this.mRemote.transact(93, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setInterruptionFilter(String str, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(94, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public NotificationChannel createConversationNotificationChannelForPackageFromPrivilegedListener(INotificationListener iNotificationListener, String str, UserHandle userHandle, String str2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    obtain.writeString(str);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeString(str2);
                    obtain.writeString(str3);
                    this.mRemote.transact(95, obtain, obtain2, 0);
                    obtain2.readException();
                    return (NotificationChannel) obtain2.readTypedObject(NotificationChannel.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void updateNotificationChannelGroupFromPrivilegedListener(INotificationListener iNotificationListener, String str, UserHandle userHandle, NotificationChannelGroup notificationChannelGroup) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    obtain.writeString(str);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeTypedObject(notificationChannelGroup, 0);
                    this.mRemote.transact(96, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void updateNotificationChannelFromPrivilegedListener(INotificationListener iNotificationListener, String str, UserHandle userHandle, NotificationChannel notificationChannel) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    obtain.writeString(str);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeTypedObject(notificationChannel, 0);
                    this.mRemote.transact(97, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public ParceledListSlice getNotificationChannelsFromPrivilegedListener(INotificationListener iNotificationListener, String str, UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    obtain.writeString(str);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(98, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public ParceledListSlice getNotificationChannelGroupsFromPrivilegedListener(INotificationListener iNotificationListener, String str, UserHandle userHandle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    obtain.writeString(str);
                    obtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(99, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void applyEnqueuedAdjustmentFromAssistant(INotificationListener iNotificationListener, Adjustment adjustment) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    obtain.writeTypedObject(adjustment, 0);
                    this.mRemote.transact(100, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void applyAdjustmentFromAssistant(INotificationListener iNotificationListener, Adjustment adjustment) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    obtain.writeTypedObject(adjustment, 0);
                    this.mRemote.transact(101, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void applyAdjustmentsFromAssistant(INotificationListener iNotificationListener, List<Adjustment> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    obtain.writeTypedList(list, 0);
                    this.mRemote.transact(102, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void unsnoozeNotificationFromAssistant(INotificationListener iNotificationListener, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    obtain.writeString(str);
                    this.mRemote.transact(103, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void unsnoozeNotificationFromSystemListener(INotificationListener iNotificationListener, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    obtain.writeString(str);
                    this.mRemote.transact(104, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public ComponentName getEffectsSuppressor() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(105, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ComponentName) obtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean matchesCallFilter(Bundle bundle) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(106, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void cleanUpCallersAfter(long j) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    this.mRemote.transact(107, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean isSystemConditionProviderEnabled(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(108, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean isNotificationListenerAccessGranted(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(109, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean isNotificationListenerAccessGrantedForUser(ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(110, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean isNotificationAssistantAccessGranted(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(111, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setNotificationListenerAccessGranted(ComponentName componentName, boolean z, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(112, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setNotificationAssistantAccessGranted(ComponentName componentName, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(113, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setNotificationListenerAccessGrantedForUser(ComponentName componentName, int i, boolean z, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(114, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setNotificationAssistantAccessGrantedForUser(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(115, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public List<String> getEnabledNotificationListenerPackages() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(116, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public List<ComponentName> getEnabledNotificationListeners(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(117, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public ComponentName getAllowedNotificationAssistantForUser(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(118, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ComponentName) obtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public ComponentName getAllowedNotificationAssistant() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(119, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ComponentName) obtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public ComponentName getDefaultNotificationAssistant() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(120, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ComponentName) obtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setNASMigrationDoneAndResetDefault(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(121, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean hasEnabledNotificationListener(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(122, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int getZenMode() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(123, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public ZenModeConfig getZenModeConfig() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(124, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ZenModeConfig) obtain2.readTypedObject(ZenModeConfig.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public NotificationManager.Policy getConsolidatedNotificationPolicy() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(125, obtain, obtain2, 0);
                    obtain2.readException();
                    return (NotificationManager.Policy) obtain2.readTypedObject(NotificationManager.Policy.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setZenMode(int i, Uri uri, String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(uri, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(126, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void notifyConditions(String str, IConditionProvider iConditionProvider, Condition[] conditionArr) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongInterface(iConditionProvider);
                    obtain.writeTypedArray(conditionArr, 0);
                    this.mRemote.transact(127, obtain, null, 1);
                } finally {
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean isNotificationPolicyAccessGranted(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(128, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public NotificationManager.Policy getNotificationPolicy(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(129, obtain, obtain2, 0);
                    obtain2.readException();
                    return (NotificationManager.Policy) obtain2.readTypedObject(NotificationManager.Policy.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setNotificationPolicy(String str, NotificationManager.Policy policy, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(policy, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(130, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean isNotificationPolicyAccessGrantedForPackage(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(131, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setNotificationPolicyAccessGranted(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(132, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setNotificationPolicyAccessGrantedForUser(String str, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(133, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public ZenPolicy getDefaultZenPolicy() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(134, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ZenPolicy) obtain2.readTypedObject(ZenPolicy.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public AutomaticZenRule getAutomaticZenRule(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(135, obtain, obtain2, 0);
                    obtain2.readException();
                    return (AutomaticZenRule) obtain2.readTypedObject(AutomaticZenRule.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public ParceledListSlice getAutomaticZenRules() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(136, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public String addAutomaticZenRule(AutomaticZenRule automaticZenRule, String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(automaticZenRule, 0);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(137, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean updateAutomaticZenRule(String str, AutomaticZenRule automaticZenRule, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(automaticZenRule, 0);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(138, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean removeAutomaticZenRule(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(139, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean removeAutomaticZenRules(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(140, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int getRuleInstanceCount(ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(141, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int getAutomaticZenRuleState(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(142, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setAutomaticZenRuleState(String str, Condition condition) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(condition, 0);
                    this.mRemote.transact(143, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setManualZenRuleDeviceEffects(ZenDeviceEffects zenDeviceEffects) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(zenDeviceEffects, 0);
                    this.mRemote.transact(144, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public byte[] getBackupPayload(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(145, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createByteArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void applyRestore(byte[] bArr, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeByteArray(bArr);
                    obtain.writeInt(i);
                    this.mRemote.transact(146, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public ParceledListSlice getAppActiveNotifications(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(147, obtain, obtain2, 0);
                    obtain2.readException();
                    return (ParceledListSlice) obtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setNotificationDelegate(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(148, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public String getNotificationDelegate(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(149, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readString();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean canNotifyAsPackage(String str, String str2, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    this.mRemote.transact(150, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean canUseFullScreenIntent(AttributionSource attributionSource) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(attributionSource, 0);
                    this.mRemote.transact(151, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setPrivateNotificationsAllowed(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(152, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean getPrivateNotificationsAllowed() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(153, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public long pullStats(long j, int i, boolean z, List<ParcelFileDescriptor> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeLong(j);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(154, obtain, obtain2, 0);
                    obtain2.readException();
                    long readLong = obtain2.readLong();
                    obtain2.readTypedList(list, ParcelFileDescriptor.CREATOR);
                    return readLong;
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public NotificationListenerFilter getListenerFilter(ComponentName componentName, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    this.mRemote.transact(155, obtain, obtain2, 0);
                    obtain2.readException();
                    return (NotificationListenerFilter) obtain2.readTypedObject(NotificationListenerFilter.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setListenerFilter(ComponentName componentName, int i, NotificationListenerFilter notificationListenerFilter) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(notificationListenerFilter, 0);
                    this.mRemote.transact(156, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void migrateNotificationFilter(INotificationListener iNotificationListener, int i, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    obtain.writeInt(i);
                    obtain.writeStringList(list);
                    this.mRemote.transact(157, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setToastRateLimitingEnabled(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(158, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void registerCallNotificationEventListener(String str, UserHandle userHandle, ICallNotificationEventCallback iCallNotificationEventCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeStrongInterface(iCallNotificationEventCallback);
                    this.mRemote.transact(159, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void unregisterCallNotificationEventListener(String str, UserHandle userHandle, ICallNotificationEventCallback iCallNotificationEventCallback) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(userHandle, 0);
                    obtain.writeStrongInterface(iCallNotificationEventCallback);
                    this.mRemote.transact(160, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setCanBePromoted(String str, int i, boolean z, boolean z2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeBoolean(z2);
                    this.mRemote.transact(161, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean appCanBePromoted(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(162, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean canBePromoted(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(163, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setAdjustmentTypeSupportedState(INotificationListener iNotificationListener, String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongInterface(iNotificationListener);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(164, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public List<String> getUnsupportedAdjustmentTypes() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(165, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int[] getAllowedAdjustmentKeyTypes() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(166, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createIntArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setAssistantAdjustmentKeyTypeState(int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(167, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public String[] getAdjustmentDeniedPackages(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(168, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArray();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean isAdjustmentSupportedForPackage(String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(169, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setAdjustmentSupportedForPackage(String str, String str2, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(170, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void incrementCounter(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(171, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void registerNotificationListener(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeTypedObject(componentName, 0);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(172, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void updateNotificationChannels(String str, ParceledListSlice parceledListSlice) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(parceledListSlice, 0);
                    this.mRemote.transact(173, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void bindEdgeLightingService(IBinder iBinder, int i, ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(174, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void unbindEdgeLightingService(IBinder iBinder, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    this.mRemote.transact(175, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void updateEdgeLightingPackageList(String str, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStringList(list);
                    this.mRemote.transact(176, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void updateEdgeLightingPolicy(String str, EdgeLightingPolicy edgeLightingPolicy) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(edgeLightingPolicy, 0);
                    this.mRemote.transact(177, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void registerEdgeLightingListener(IBinder iBinder, ComponentName componentName) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(178, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void unregisterEdgeLightingListener(IBinder iBinder, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeString(str);
                    this.mRemote.transact(179, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void startEdgeLighting(String str, SemEdgeLightingInfo semEdgeLightingInfo, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeTypedObject(semEdgeLightingInfo, 0);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(180, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void stopEdgeLighting(String str, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(181, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int getEdgeLightingState() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(182, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean isEdgeLightingNotificationAllowed(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(183, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void disable(int i, String str, IBinder iBinder) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(184, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void disableEdgeLightingNotification(String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(185, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean isPackageEnabled(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(186, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void cancelNotificationByEdge(String str, String str2, int i, int i2, String str3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str3);
                    this.mRemote.transact(187, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void cancelNotificationByGroupKey(String str, String str2, int i, int i2, String str3, String str4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    this.mRemote.transact(188, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void enqueueEdgeNotification(String str, String str2, int i, Bundle bundle, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(189, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void removeEdgeNotification(String str, int i, Bundle bundle, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeTypedObject(bundle, 0);
                    obtain.writeInt(i2);
                    this.mRemote.transact(190, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean isEdgeLightingAllowed(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(191, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setAllowEdgeLighting(String str, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(192, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void resetDefaultAllowEdgeLighting() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(193, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int getEdgeLightingSettingState(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(194, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setEdgeLightingState(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(195, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void resetDefaultEdgeLightingState() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(196, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean dispatchDelayedWakelockAndBlocked(int i, String str, String str2, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i2);
                    this.mRemote.transact(197, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean dispatchDelayedWakeUpAndBlocked(int i, String str, String str2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    this.mRemote.transact(198, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean isSubDisplayNotificationAllowed(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(199, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setAllowSubDisplayNotification(String str, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(200, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean isOngoingActivityAllowed(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(201, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setAllowOngoingActivity(String str, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(202, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void resetDefaultAllowOngoingActivity() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(203, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public List<String> getAllowedOngoingActivityAppList() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(204, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean getNotificationAlertsEnabledForPackage(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(205, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setNotificationAlertsEnabledForPackage(String str, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(206, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean setWearableAppList(int i, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeStringList(list);
                    this.mRemote.transact(207, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean addWearableAppToList(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(208, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean removeWearableAppFromList(int i, String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    this.mRemote.transact(209, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public List<String> getWearableAppList(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(210, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean requestListenerHintsForWearable(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(211, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int getLockScreenNotificationVisibilityForPackage(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(212, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setLockScreenNotificationVisibilityForPackage(String str, int i, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeInt(i2);
                    this.mRemote.transact(213, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean isAllowNotificationPopUpForPackage(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(214, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setAllowNotificationPopUpForPackage(String str, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(215, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean isAlertsAllowed(String str, int i, String str2, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeString(str2);
                    obtain.writeInt(i2);
                    this.mRemote.transact(216, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean isReminderEnabled(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(217, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setReminderEnabledForPackage(String str, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(218, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setReminderEnabled(int i, boolean z, List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeStringList(list);
                    this.mRemote.transact(219, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void addReplyHistory(int i, String str, String str2, int i2, String str3, String str4) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i2);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    this.mRemote.transact(220, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public List<Bundle> getNotificationHistoryDataForPackage(String str, String str2, int i, String str3, String str4, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeInt(i2);
                    this.mRemote.transact(221, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createTypedArrayList(Bundle.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public NotificationHistory getNotificationHistoryForPackage(String str, String str2, int i, String str3, String str4, int i2) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeString(str2);
                    obtain.writeInt(i);
                    obtain.writeString(str3);
                    obtain.writeString(str4);
                    obtain.writeInt(i2);
                    this.mRemote.transact(222, obtain, obtain2, 0);
                    obtain2.readException();
                    return (NotificationHistory) obtain2.readTypedObject(NotificationHistory.CREATOR);
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void updateCancelEvent(int i, String str, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    obtain.writeString(str);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(223, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setRestoreBlockListForSS(List<String> list) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeStringList(list);
                    this.mRemote.transact(224, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int getAllNotificationListenersCount() throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(225, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int getBlockedAppCount(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(226, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean canAppBypassDnd(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(227, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setAppBypassDnd(String str, int i, boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(228, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int getAppsBypassingDndCount(int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeInt(i);
                    this.mRemote.transact(229, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void enqueueTextToastForDex(String str, IBinder iBinder, CharSequence charSequence, int i, boolean z, int i2, ITransientNotificationCallback iTransientNotificationCallback, String str2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder);
                    if (charSequence != null) {
                        obtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, obtain, 0);
                    } else {
                        obtain.writeInt(0);
                    }
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i2);
                    obtain.writeStrongInterface(iTransientNotificationCallback);
                    obtain.writeString(str2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(230, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void enqueueToastForDex(String str, IBinder iBinder, ITransientNotification iTransientNotification, int i, boolean z, int i2, String str2, int i3) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeStrongBinder(iBinder);
                    obtain.writeStrongInterface(iTransientNotification);
                    obtain.writeInt(i);
                    obtain.writeBoolean(z);
                    obtain.writeInt(i2);
                    obtain.writeString(str2);
                    obtain.writeInt(i3);
                    this.mRemote.transact(231, obtain, obtain2, 0);
                    obtain2.readException();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int isNotificationTurnedOff(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(232, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int getNotificationSoundStatus(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(233, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean setNotificationTurnOff(String str, int i) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    obtain.writeInt(i);
                    this.mRemote.transact(234, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readBoolean();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int getNotificationSettingStatus(boolean z) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeBoolean(z);
                    this.mRemote.transact(235, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int getAppNotificationSettingStatus(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(236, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.readInt();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public List<String> getBlockInfoOfNotificationsForOverflow(String str) throws RemoteException {
                Parcel obtain = Parcel.obtain(asBinder());
                Parcel obtain2 = Parcel.obtain();
                try {
                    obtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    obtain.writeString(str);
                    this.mRemote.transact(237, obtain, obtain2, 0);
                    obtain2.readException();
                    return obtain2.createStringArrayList();
                } finally {
                    obtain2.recycle();
                    obtain.recycle();
                }
            }
        }

        protected void getActiveNotificationsWithAttribution_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_NOTIFICATIONS, getCallingPid(), getCallingUid());
        }

        protected void getHistoricalNotificationsWithAttribution_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_NOTIFICATIONS, getCallingPid(), getCallingUid());
        }

        protected void getNotificationHistory_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.ACCESS_NOTIFICATIONS, getCallingPid(), getCallingUid());
        }

        protected void setToastRateLimitingEnabled_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermission(Manifest.permission.MANAGE_TOAST_RATE_LIMITING, getCallingPid(), getCallingUid());
        }

        protected void registerCallNotificationEventListener_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermissionAllOf(PERMISSIONS_registerCallNotificationEventListener, getCallingPid(), getCallingUid());
        }

        protected void unregisterCallNotificationEventListener_enforcePermission() throws SecurityException {
            this.mEnforcer.enforcePermissionAllOf(PERMISSIONS_unregisterCallNotificationEventListener, getCallingPid(), getCallingUid());
        }
    }
}
