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
            IInterface iInterfaceQueryLocalInterface = iBinder.queryLocalInterface(DESCRIPTOR);
            if (iInterfaceQueryLocalInterface != null && (iInterfaceQueryLocalInterface instanceof INotificationManager)) {
                return (INotificationManager) iInterfaceQueryLocalInterface;
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
                    String string = parcel.readString();
                    int i3 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cancelAllNotifications(string, i3);
                    parcel2.writeNoException();
                    return true;
                case 2:
                    String string2 = parcel.readString();
                    int i4 = parcel.readInt();
                    boolean z = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    clearData(string2, i4, z);
                    parcel2.writeNoException();
                    return true;
                case 3:
                    String string3 = parcel.readString();
                    IBinder strongBinder = parcel.readStrongBinder();
                    CharSequence charSequence = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    int i5 = parcel.readInt();
                    boolean z2 = parcel.readBoolean();
                    int i6 = parcel.readInt();
                    ITransientNotificationCallback iTransientNotificationCallbackAsInterface = ITransientNotificationCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    boolean zEnqueueTextToast = enqueueTextToast(string3, strongBinder, charSequence, i5, z2, i6, iTransientNotificationCallbackAsInterface);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zEnqueueTextToast);
                    return true;
                case 4:
                    String string4 = parcel.readString();
                    IBinder strongBinder2 = parcel.readStrongBinder();
                    ITransientNotification iTransientNotificationAsInterface = ITransientNotification.Stub.asInterface(parcel.readStrongBinder());
                    int i7 = parcel.readInt();
                    boolean z3 = parcel.readBoolean();
                    int i8 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zEnqueueToast = enqueueToast(string4, strongBinder2, iTransientNotificationAsInterface, i7, z3, i8);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zEnqueueToast);
                    return true;
                case 5:
                    String string5 = parcel.readString();
                    IBinder strongBinder3 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    cancelToast(string5, strongBinder3);
                    parcel2.writeNoException();
                    return true;
                case 6:
                    String string6 = parcel.readString();
                    IBinder strongBinder4 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    finishToken(string6, strongBinder4);
                    parcel2.writeNoException();
                    return true;
                case 7:
                    String string7 = parcel.readString();
                    String string8 = parcel.readString();
                    String string9 = parcel.readString();
                    int i9 = parcel.readInt();
                    Notification notification = (Notification) parcel.readTypedObject(Notification.CREATOR);
                    int i10 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enqueueNotificationWithTag(string7, string8, string9, i9, notification, i10);
                    parcel2.writeNoException();
                    return true;
                case 8:
                    String string10 = parcel.readString();
                    String string11 = parcel.readString();
                    String string12 = parcel.readString();
                    int i11 = parcel.readInt();
                    int i12 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cancelNotificationWithTag(string10, string11, string12, i11, i12);
                    parcel2.writeNoException();
                    return true;
                case 9:
                    String string13 = parcel.readString();
                    int i13 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsInCall = isInCall(string13, i13);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsInCall);
                    return true;
                case 10:
                    String string14 = parcel.readString();
                    int i14 = parcel.readInt();
                    boolean z4 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setShowBadge(string14, i14, z4);
                    parcel2.writeNoException();
                    return true;
                case 11:
                    String string15 = parcel.readString();
                    int i15 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zCanShowBadge = canShowBadge(string15, i15);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCanShowBadge);
                    return true;
                case 12:
                    String string16 = parcel.readString();
                    int i16 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zHasSentValidMsg = hasSentValidMsg(string16, i16);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasSentValidMsg);
                    return true;
                case 13:
                    String string17 = parcel.readString();
                    int i17 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsInInvalidMsgState = isInInvalidMsgState(string17, i17);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsInInvalidMsgState);
                    return true;
                case 14:
                    String string18 = parcel.readString();
                    int i18 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zHasUserDemotedInvalidMsgApp = hasUserDemotedInvalidMsgApp(string18, i18);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasUserDemotedInvalidMsgApp);
                    return true;
                case 15:
                    String string19 = parcel.readString();
                    int i19 = parcel.readInt();
                    boolean z5 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setInvalidMsgAppDemoted(string19, i19, z5);
                    parcel2.writeNoException();
                    return true;
                case 16:
                    String string20 = parcel.readString();
                    int i20 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zHasSentValidBubble = hasSentValidBubble(string20, i20);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasSentValidBubble);
                    return true;
                case 17:
                    String string21 = parcel.readString();
                    int i21 = parcel.readInt();
                    boolean z6 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNotificationsEnabledForPackage(string21, i21, z6);
                    parcel2.writeNoException();
                    return true;
                case 18:
                    String string22 = parcel.readString();
                    int i22 = parcel.readInt();
                    boolean z7 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNotificationsEnabledWithImportanceLockForPackage(string22, i22, z7);
                    parcel2.writeNoException();
                    return true;
                case 19:
                    String string23 = parcel.readString();
                    int i23 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zAreNotificationsEnabledForPackage = areNotificationsEnabledForPackage(string23, i23);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAreNotificationsEnabledForPackage);
                    return true;
                case 20:
                    String string24 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zAreNotificationsEnabled = areNotificationsEnabled(string24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAreNotificationsEnabled);
                    return true;
                case 21:
                    String string25 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int packageImportance = getPackageImportance(string25);
                    parcel2.writeNoException();
                    parcel2.writeInt(packageImportance);
                    return true;
                case 22:
                    String string26 = parcel.readString();
                    int i24 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsImportanceLocked = isImportanceLocked(string26, i24);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsImportanceLocked);
                    return true;
                case 23:
                    String string27 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> allowedAssistantAdjustments = getAllowedAssistantAdjustments(string27);
                    parcel2.writeNoException();
                    parcel2.writeStringList(allowedAssistantAdjustments);
                    return true;
                case 24:
                    String string28 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    allowAssistantAdjustment(string28);
                    parcel2.writeNoException();
                    return true;
                case 25:
                    String string29 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    disallowAssistantAdjustment(string29);
                    parcel2.writeNoException();
                    return true;
                case 26:
                    String string30 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zShouldHideSilentStatusIcons = shouldHideSilentStatusIcons(string30);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zShouldHideSilentStatusIcons);
                    return true;
                case 27:
                    boolean z8 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setHideSilentStatusIcons(z8);
                    parcel2.writeNoException();
                    return true;
                case 28:
                    String string31 = parcel.readString();
                    int i25 = parcel.readInt();
                    int i26 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setBubblesAllowed(string31, i25, i26);
                    parcel2.writeNoException();
                    return true;
                case 29:
                    String string32 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zAreBubblesAllowed = areBubblesAllowed(string32);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAreBubblesAllowed);
                    return true;
                case 30:
                    UserHandle userHandle = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zAreBubblesEnabled = areBubblesEnabled(userHandle);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAreBubblesEnabled);
                    return true;
                case 31:
                    String string33 = parcel.readString();
                    int i27 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int bubblePreferenceForPackage = getBubblePreferenceForPackage(string33, i27);
                    parcel2.writeNoException();
                    parcel2.writeInt(bubblePreferenceForPackage);
                    return true;
                case 32:
                    String string34 = parcel.readString();
                    ParceledListSlice parceledListSlice = (ParceledListSlice) parcel.readTypedObject(ParceledListSlice.CREATOR);
                    parcel.enforceNoDataAvail();
                    createNotificationChannelGroups(string34, parceledListSlice);
                    parcel2.writeNoException();
                    return true;
                case 33:
                    String string35 = parcel.readString();
                    ParceledListSlice parceledListSlice2 = (ParceledListSlice) parcel.readTypedObject(ParceledListSlice.CREATOR);
                    parcel.enforceNoDataAvail();
                    createNotificationChannels(string35, parceledListSlice2);
                    parcel2.writeNoException();
                    return true;
                case 34:
                    String string36 = parcel.readString();
                    int i28 = parcel.readInt();
                    ParceledListSlice parceledListSlice3 = (ParceledListSlice) parcel.readTypedObject(ParceledListSlice.CREATOR);
                    parcel.enforceNoDataAvail();
                    createNotificationChannelsForPackage(string36, i28, parceledListSlice3);
                    parcel2.writeNoException();
                    return true;
                case 35:
                    boolean z9 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice conversations = getConversations(z9);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(conversations, 1);
                    return true;
                case 36:
                    String string37 = parcel.readString();
                    int i29 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice conversationsForPackage = getConversationsForPackage(string37, i29);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(conversationsForPackage, 1);
                    return true;
                case 37:
                    String string38 = parcel.readString();
                    int i30 = parcel.readInt();
                    boolean z10 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice notificationChannelGroupsForPackage = getNotificationChannelGroupsForPackage(string38, i30, z10);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(notificationChannelGroupsForPackage, 1);
                    return true;
                case 38:
                    String string39 = parcel.readString();
                    String string40 = parcel.readString();
                    int i31 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    NotificationChannelGroup notificationChannelGroupForPackage = getNotificationChannelGroupForPackage(string39, string40, i31);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(notificationChannelGroupForPackage, 1);
                    return true;
                case 39:
                    String string41 = parcel.readString();
                    int i32 = parcel.readInt();
                    String string42 = parcel.readString();
                    boolean z11 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    NotificationChannelGroup populatedNotificationChannelGroupForPackage = getPopulatedNotificationChannelGroupForPackage(string41, i32, string42, z11);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(populatedNotificationChannelGroupForPackage, 1);
                    return true;
                case 40:
                    String string43 = parcel.readString();
                    int i33 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice recentBlockedNotificationChannelGroupsForPackage = getRecentBlockedNotificationChannelGroupsForPackage(string43, i33);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(recentBlockedNotificationChannelGroupsForPackage, 1);
                    return true;
                case 41:
                    String string44 = parcel.readString();
                    int i34 = parcel.readInt();
                    NotificationChannelGroup notificationChannelGroup = (NotificationChannelGroup) parcel.readTypedObject(NotificationChannelGroup.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateNotificationChannelGroupForPackage(string44, i34, notificationChannelGroup);
                    parcel2.writeNoException();
                    return true;
                case 42:
                    String string45 = parcel.readString();
                    int i35 = parcel.readInt();
                    NotificationChannel notificationChannel = (NotificationChannel) parcel.readTypedObject(NotificationChannel.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateNotificationChannelForPackage(string45, i35, notificationChannel);
                    parcel2.writeNoException();
                    return true;
                case 43:
                    String string46 = parcel.readString();
                    int i36 = parcel.readInt();
                    String string47 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unlockNotificationChannel(string46, i36, string47);
                    parcel2.writeNoException();
                    return true;
                case 44:
                    unlockAllNotificationChannels();
                    parcel2.writeNoException();
                    return true;
                case 45:
                    String string48 = parcel.readString();
                    int i37 = parcel.readInt();
                    String string49 = parcel.readString();
                    String string50 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    NotificationChannel notificationChannel2 = getNotificationChannel(string48, i37, string49, string50);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(notificationChannel2, 1);
                    return true;
                case 46:
                    String string51 = parcel.readString();
                    int i38 = parcel.readInt();
                    String string52 = parcel.readString();
                    String string53 = parcel.readString();
                    boolean z12 = parcel.readBoolean();
                    String string54 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    NotificationChannel conversationNotificationChannel = getConversationNotificationChannel(string51, i38, string52, string53, z12, string54);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(conversationNotificationChannel, 1);
                    return true;
                case 47:
                    String string55 = parcel.readString();
                    int i39 = parcel.readInt();
                    NotificationChannel notificationChannel3 = (NotificationChannel) parcel.readTypedObject(NotificationChannel.CREATOR);
                    String string56 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    createConversationNotificationChannelForPackage(string55, i39, notificationChannel3, string56);
                    parcel2.writeNoException();
                    return true;
                case 48:
                    String string57 = parcel.readString();
                    int i40 = parcel.readInt();
                    String string58 = parcel.readString();
                    String string59 = parcel.readString();
                    boolean z13 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    NotificationChannel notificationChannelForPackage = getNotificationChannelForPackage(string57, i40, string58, string59, z13);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(notificationChannelForPackage, 1);
                    return true;
                case 49:
                    String string60 = parcel.readString();
                    String string61 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    deleteNotificationChannel(string60, string61);
                    parcel2.writeNoException();
                    return true;
                case 50:
                    String string62 = parcel.readString();
                    String string63 = parcel.readString();
                    int i41 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice notificationChannels = getNotificationChannels(string62, string63, i41);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(notificationChannels, 1);
                    return true;
                case 51:
                    String string64 = parcel.readString();
                    int i42 = parcel.readInt();
                    boolean z14 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice notificationChannelsForPackage = getNotificationChannelsForPackage(string64, i42, z14);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(notificationChannelsForPackage, 1);
                    return true;
                case 52:
                    String string65 = parcel.readString();
                    int i43 = parcel.readInt();
                    boolean z15 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int numNotificationChannelsForPackage = getNumNotificationChannelsForPackage(string65, i43, z15);
                    parcel2.writeNoException();
                    parcel2.writeInt(numNotificationChannelsForPackage);
                    return true;
                case 53:
                    String string66 = parcel.readString();
                    int i44 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int deletedChannelCount = getDeletedChannelCount(string66, i44);
                    parcel2.writeNoException();
                    parcel2.writeInt(deletedChannelCount);
                    return true;
                case 54:
                    String string67 = parcel.readString();
                    int i45 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int blockedChannelCount = getBlockedChannelCount(string67, i45);
                    parcel2.writeNoException();
                    parcel2.writeInt(blockedChannelCount);
                    return true;
                case 55:
                    String string68 = parcel.readString();
                    String string69 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    deleteNotificationChannelGroup(string68, string69);
                    parcel2.writeNoException();
                    return true;
                case 56:
                    String string70 = parcel.readString();
                    String string71 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    NotificationChannelGroup notificationChannelGroup2 = getNotificationChannelGroup(string70, string71);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(notificationChannelGroup2, 1);
                    return true;
                case 57:
                    String string72 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice notificationChannelGroups = getNotificationChannelGroups(string72);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(notificationChannelGroups, 1);
                    return true;
                case 58:
                    String string73 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice notificationChannelGroupsWithoutChannels = getNotificationChannelGroupsWithoutChannels(string73);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(notificationChannelGroupsWithoutChannels, 1);
                    return true;
                case 59:
                    String string74 = parcel.readString();
                    int i46 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zOnlyHasDefaultChannel = onlyHasDefaultChannel(string74, i46);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zOnlyHasDefaultChannel);
                    return true;
                case 60:
                    boolean zAreChannelsBypassingDnd = areChannelsBypassingDnd();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAreChannelsBypassingDnd);
                    return true;
                case 61:
                    String string75 = parcel.readString();
                    int i47 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice notificationChannelsBypassingDnd = getNotificationChannelsBypassingDnd(string75, i47);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(notificationChannelsBypassingDnd, 1);
                    return true;
                case 62:
                    int i48 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice packagesBypassingDnd = getPackagesBypassingDnd(i48);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(packagesBypassingDnd, 1);
                    return true;
                case 63:
                    int i49 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> packagesWithAnyChannels = getPackagesWithAnyChannels(i49);
                    parcel2.writeNoException();
                    parcel2.writeStringList(packagesWithAnyChannels);
                    return true;
                case 64:
                    String string76 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsPackagePaused = isPackagePaused(string76);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPackagePaused);
                    return true;
                case 65:
                    String string77 = parcel.readString();
                    int i50 = parcel.readInt();
                    long j = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    deleteNotificationHistoryItem(string77, i50, j);
                    parcel2.writeNoException();
                    return true;
                case 66:
                    String string78 = parcel.readString();
                    int i51 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsPermissionFixed = isPermissionFixed(string78, i51);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPermissionFixed);
                    return true;
                case 67:
                    silenceNotificationSound();
                    parcel2.writeNoException();
                    return true;
                case 68:
                    String string79 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    StatusBarNotification[] activeNotifications = getActiveNotifications(string79);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(activeNotifications, 1);
                    return true;
                case 69:
                    String string80 = parcel.readString();
                    String string81 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    StatusBarNotification[] activeNotificationsWithAttribution = getActiveNotificationsWithAttribution(string80, string81);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(activeNotificationsWithAttribution, 1);
                    return true;
                case 70:
                    String string82 = parcel.readString();
                    int i52 = parcel.readInt();
                    boolean z16 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    StatusBarNotification[] historicalNotifications = getHistoricalNotifications(string82, i52, z16);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(historicalNotifications, 1);
                    return true;
                case 71:
                    String string83 = parcel.readString();
                    String string84 = parcel.readString();
                    int i53 = parcel.readInt();
                    boolean z17 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    StatusBarNotification[] historicalNotificationsWithAttribution = getHistoricalNotificationsWithAttribution(string83, string84, i53, z17);
                    parcel2.writeNoException();
                    parcel2.writeTypedArray(historicalNotificationsWithAttribution, 1);
                    return true;
                case 72:
                    String string85 = parcel.readString();
                    String string86 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    NotificationHistory notificationHistory = getNotificationHistory(string85, string86);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(notificationHistory, 1);
                    return true;
                case 73:
                    INotificationListener iNotificationListenerAsInterface = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    ComponentName componentName = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int i54 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    registerListener(iNotificationListenerAsInterface, componentName, i54);
                    parcel2.writeNoException();
                    return true;
                case 74:
                    INotificationListener iNotificationListenerAsInterface2 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    int i55 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    unregisterListener(iNotificationListenerAsInterface2, i55);
                    parcel2.writeNoException();
                    return true;
                case 75:
                    INotificationListener iNotificationListenerAsInterface3 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    String string87 = parcel.readString();
                    String string88 = parcel.readString();
                    int i56 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    cancelNotificationFromListener(iNotificationListenerAsInterface3, string87, string88, i56);
                    parcel2.writeNoException();
                    return true;
                case 76:
                    INotificationListener iNotificationListenerAsInterface4 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    String[] strArrCreateStringArray = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    cancelNotificationsFromListener(iNotificationListenerAsInterface4, strArrCreateStringArray);
                    parcel2.writeNoException();
                    return true;
                case 77:
                    INotificationListener iNotificationListenerAsInterface5 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    String string89 = parcel.readString();
                    String string90 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    snoozeNotificationUntilContextFromListener(iNotificationListenerAsInterface5, string89, string90);
                    parcel2.writeNoException();
                    return true;
                case 78:
                    INotificationListener iNotificationListenerAsInterface6 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    String string91 = parcel.readString();
                    long j2 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    snoozeNotificationUntilFromListener(iNotificationListenerAsInterface6, string91, j2);
                    parcel2.writeNoException();
                    return true;
                case 79:
                    ComponentName componentName2 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    requestBindListener(componentName2);
                    parcel2.writeNoException();
                    return true;
                case 80:
                    INotificationListener iNotificationListenerAsInterface7 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestUnbindListener(iNotificationListenerAsInterface7);
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
                    IConditionProvider iConditionProviderAsInterface = IConditionProvider.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    requestUnbindProvider(iConditionProviderAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 84:
                    INotificationListener iNotificationListenerAsInterface8 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    String[] strArrCreateStringArray2 = parcel.createStringArray();
                    parcel.enforceNoDataAvail();
                    setNotificationsShownFromListener(iNotificationListenerAsInterface8, strArrCreateStringArray2);
                    parcel2.writeNoException();
                    return true;
                case 85:
                    INotificationListener iNotificationListenerAsInterface9 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    String[] strArrCreateStringArray3 = parcel.createStringArray();
                    int i57 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice activeNotificationsFromListener = getActiveNotificationsFromListener(iNotificationListenerAsInterface9, strArrCreateStringArray3, i57);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(activeNotificationsFromListener, 1);
                    return true;
                case 86:
                    INotificationListener iNotificationListenerAsInterface10 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    int i58 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice snoozedNotificationsFromListener = getSnoozedNotificationsFromListener(iNotificationListenerAsInterface10, i58);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(snoozedNotificationsFromListener, 1);
                    return true;
                case 87:
                    INotificationListener iNotificationListenerAsInterface11 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    clearRequestedListenerHints(iNotificationListenerAsInterface11);
                    parcel2.writeNoException();
                    return true;
                case 88:
                    INotificationListener iNotificationListenerAsInterface12 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    int i59 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestHintsFromListener(iNotificationListenerAsInterface12, i59);
                    parcel2.writeNoException();
                    return true;
                case 89:
                    INotificationListener iNotificationListenerAsInterface13 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int hintsFromListener = getHintsFromListener(iNotificationListenerAsInterface13);
                    parcel2.writeNoException();
                    parcel2.writeInt(hintsFromListener);
                    return true;
                case 90:
                    int hintsFromListenerNoToken = getHintsFromListenerNoToken();
                    parcel2.writeNoException();
                    parcel2.writeInt(hintsFromListenerNoToken);
                    return true;
                case 91:
                    INotificationListener iNotificationListenerAsInterface14 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    int i60 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    requestInterruptionFilterFromListener(iNotificationListenerAsInterface14, i60);
                    parcel2.writeNoException();
                    return true;
                case 92:
                    INotificationListener iNotificationListenerAsInterface15 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    int interruptionFilterFromListener = getInterruptionFilterFromListener(iNotificationListenerAsInterface15);
                    parcel2.writeNoException();
                    parcel2.writeInt(interruptionFilterFromListener);
                    return true;
                case 93:
                    INotificationListener iNotificationListenerAsInterface16 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    int i61 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setOnNotificationPostedTrimFromListener(iNotificationListenerAsInterface16, i61);
                    parcel2.writeNoException();
                    return true;
                case 94:
                    String string92 = parcel.readString();
                    int i62 = parcel.readInt();
                    boolean z18 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setInterruptionFilter(string92, i62, z18);
                    parcel2.writeNoException();
                    return true;
                case 95:
                    INotificationListener iNotificationListenerAsInterface17 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    String string93 = parcel.readString();
                    UserHandle userHandle2 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    String string94 = parcel.readString();
                    String string95 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    NotificationChannel notificationChannelCreateConversationNotificationChannelForPackageFromPrivilegedListener = createConversationNotificationChannelForPackageFromPrivilegedListener(iNotificationListenerAsInterface17, string93, userHandle2, string94, string95);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(notificationChannelCreateConversationNotificationChannelForPackageFromPrivilegedListener, 1);
                    return true;
                case 96:
                    INotificationListener iNotificationListenerAsInterface18 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    String string96 = parcel.readString();
                    UserHandle userHandle3 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    NotificationChannelGroup notificationChannelGroup3 = (NotificationChannelGroup) parcel.readTypedObject(NotificationChannelGroup.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateNotificationChannelGroupFromPrivilegedListener(iNotificationListenerAsInterface18, string96, userHandle3, notificationChannelGroup3);
                    parcel2.writeNoException();
                    return true;
                case 97:
                    INotificationListener iNotificationListenerAsInterface19 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    String string97 = parcel.readString();
                    UserHandle userHandle4 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    NotificationChannel notificationChannel4 = (NotificationChannel) parcel.readTypedObject(NotificationChannel.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateNotificationChannelFromPrivilegedListener(iNotificationListenerAsInterface19, string97, userHandle4, notificationChannel4);
                    parcel2.writeNoException();
                    return true;
                case 98:
                    INotificationListener iNotificationListenerAsInterface20 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    String string98 = parcel.readString();
                    UserHandle userHandle5 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    ParceledListSlice notificationChannelsFromPrivilegedListener = getNotificationChannelsFromPrivilegedListener(iNotificationListenerAsInterface20, string98, userHandle5);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(notificationChannelsFromPrivilegedListener, 1);
                    return true;
                case 99:
                    INotificationListener iNotificationListenerAsInterface21 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    String string99 = parcel.readString();
                    UserHandle userHandle6 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    parcel.enforceNoDataAvail();
                    ParceledListSlice notificationChannelGroupsFromPrivilegedListener = getNotificationChannelGroupsFromPrivilegedListener(iNotificationListenerAsInterface21, string99, userHandle6);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(notificationChannelGroupsFromPrivilegedListener, 1);
                    return true;
                case 100:
                    INotificationListener iNotificationListenerAsInterface22 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    Adjustment adjustment = (Adjustment) parcel.readTypedObject(Adjustment.CREATOR);
                    parcel.enforceNoDataAvail();
                    applyEnqueuedAdjustmentFromAssistant(iNotificationListenerAsInterface22, adjustment);
                    parcel2.writeNoException();
                    return true;
                case 101:
                    INotificationListener iNotificationListenerAsInterface23 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    Adjustment adjustment2 = (Adjustment) parcel.readTypedObject(Adjustment.CREATOR);
                    parcel.enforceNoDataAvail();
                    applyAdjustmentFromAssistant(iNotificationListenerAsInterface23, adjustment2);
                    parcel2.writeNoException();
                    return true;
                case 102:
                    INotificationListener iNotificationListenerAsInterface24 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    ArrayList arrayListCreateTypedArrayList = parcel.createTypedArrayList(Adjustment.CREATOR);
                    parcel.enforceNoDataAvail();
                    applyAdjustmentsFromAssistant(iNotificationListenerAsInterface24, arrayListCreateTypedArrayList);
                    parcel2.writeNoException();
                    return true;
                case 103:
                    INotificationListener iNotificationListenerAsInterface25 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    String string100 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unsnoozeNotificationFromAssistant(iNotificationListenerAsInterface25, string100);
                    parcel2.writeNoException();
                    return true;
                case 104:
                    INotificationListener iNotificationListenerAsInterface26 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    String string101 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unsnoozeNotificationFromSystemListener(iNotificationListenerAsInterface26, string101);
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
                    boolean zMatchesCallFilter = matchesCallFilter(bundle);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zMatchesCallFilter);
                    return true;
                case 107:
                    long j3 = parcel.readLong();
                    parcel.enforceNoDataAvail();
                    cleanUpCallersAfter(j3);
                    parcel2.writeNoException();
                    return true;
                case 108:
                    String string102 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsSystemConditionProviderEnabled = isSystemConditionProviderEnabled(string102);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSystemConditionProviderEnabled);
                    return true;
                case 109:
                    ComponentName componentName5 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsNotificationListenerAccessGranted = isNotificationListenerAccessGranted(componentName5);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsNotificationListenerAccessGranted);
                    return true;
                case 110:
                    ComponentName componentName6 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int i63 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsNotificationListenerAccessGrantedForUser = isNotificationListenerAccessGrantedForUser(componentName6, i63);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsNotificationListenerAccessGrantedForUser);
                    return true;
                case 111:
                    ComponentName componentName7 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zIsNotificationAssistantAccessGranted = isNotificationAssistantAccessGranted(componentName7);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsNotificationAssistantAccessGranted);
                    return true;
                case 112:
                    ComponentName componentName8 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    boolean z19 = parcel.readBoolean();
                    boolean z20 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNotificationListenerAccessGranted(componentName8, z19, z20);
                    parcel2.writeNoException();
                    return true;
                case 113:
                    ComponentName componentName9 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    boolean z21 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNotificationAssistantAccessGranted(componentName9, z21);
                    parcel2.writeNoException();
                    return true;
                case 114:
                    ComponentName componentName10 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int i64 = parcel.readInt();
                    boolean z22 = parcel.readBoolean();
                    boolean z23 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNotificationListenerAccessGrantedForUser(componentName10, i64, z22, z23);
                    parcel2.writeNoException();
                    return true;
                case 115:
                    ComponentName componentName11 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int i65 = parcel.readInt();
                    boolean z24 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNotificationAssistantAccessGrantedForUser(componentName11, i65, z24);
                    parcel2.writeNoException();
                    return true;
                case 116:
                    List<String> enabledNotificationListenerPackages = getEnabledNotificationListenerPackages();
                    parcel2.writeNoException();
                    parcel2.writeStringList(enabledNotificationListenerPackages);
                    return true;
                case 117:
                    int i66 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<ComponentName> enabledNotificationListeners = getEnabledNotificationListeners(i66);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(enabledNotificationListeners, 1);
                    return true;
                case 118:
                    int i67 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ComponentName allowedNotificationAssistantForUser = getAllowedNotificationAssistantForUser(i67);
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
                    int i68 = parcel.readInt();
                    boolean z25 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNASMigrationDoneAndResetDefault(i68, z25);
                    parcel2.writeNoException();
                    return true;
                case 122:
                    String string103 = parcel.readString();
                    int i69 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zHasEnabledNotificationListener = hasEnabledNotificationListener(string103, i69);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zHasEnabledNotificationListener);
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
                    int i70 = parcel.readInt();
                    Uri uri = (Uri) parcel.readTypedObject(Uri.CREATOR);
                    String string104 = parcel.readString();
                    boolean z26 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setZenMode(i70, uri, string104, z26);
                    return true;
                case 127:
                    String string105 = parcel.readString();
                    IConditionProvider iConditionProviderAsInterface2 = IConditionProvider.Stub.asInterface(parcel.readStrongBinder());
                    Condition[] conditionArr = (Condition[]) parcel.createTypedArray(Condition.CREATOR);
                    parcel.enforceNoDataAvail();
                    notifyConditions(string105, iConditionProviderAsInterface2, conditionArr);
                    return true;
                case 128:
                    String string106 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsNotificationPolicyAccessGranted = isNotificationPolicyAccessGranted(string106);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsNotificationPolicyAccessGranted);
                    return true;
                case 129:
                    String string107 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    NotificationManager.Policy notificationPolicy = getNotificationPolicy(string107);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(notificationPolicy, 1);
                    return true;
                case 130:
                    String string108 = parcel.readString();
                    NotificationManager.Policy policy = (NotificationManager.Policy) parcel.readTypedObject(NotificationManager.Policy.CREATOR);
                    boolean z27 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNotificationPolicy(string108, policy, z27);
                    parcel2.writeNoException();
                    return true;
                case 131:
                    String string109 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsNotificationPolicyAccessGrantedForPackage = isNotificationPolicyAccessGrantedForPackage(string109);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsNotificationPolicyAccessGrantedForPackage);
                    return true;
                case 132:
                    String string110 = parcel.readString();
                    boolean z28 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNotificationPolicyAccessGranted(string110, z28);
                    parcel2.writeNoException();
                    return true;
                case 133:
                    String string111 = parcel.readString();
                    int i71 = parcel.readInt();
                    boolean z29 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNotificationPolicyAccessGrantedForUser(string111, i71, z29);
                    parcel2.writeNoException();
                    return true;
                case 134:
                    ZenPolicy defaultZenPolicy = getDefaultZenPolicy();
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(defaultZenPolicy, 1);
                    return true;
                case 135:
                    String string112 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    AutomaticZenRule automaticZenRule = getAutomaticZenRule(string112);
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
                    String string113 = parcel.readString();
                    boolean z30 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    String strAddAutomaticZenRule = addAutomaticZenRule(automaticZenRule2, string113, z30);
                    parcel2.writeNoException();
                    parcel2.writeString(strAddAutomaticZenRule);
                    return true;
                case 138:
                    String string114 = parcel.readString();
                    AutomaticZenRule automaticZenRule3 = (AutomaticZenRule) parcel.readTypedObject(AutomaticZenRule.CREATOR);
                    boolean z31 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zUpdateAutomaticZenRule = updateAutomaticZenRule(string114, automaticZenRule3, z31);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zUpdateAutomaticZenRule);
                    return true;
                case 139:
                    String string115 = parcel.readString();
                    boolean z32 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveAutomaticZenRule = removeAutomaticZenRule(string115, z32);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveAutomaticZenRule);
                    return true;
                case 140:
                    String string116 = parcel.readString();
                    boolean z33 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveAutomaticZenRules = removeAutomaticZenRules(string116, z33);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveAutomaticZenRules);
                    return true;
                case 141:
                    ComponentName componentName12 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    int ruleInstanceCount = getRuleInstanceCount(componentName12);
                    parcel2.writeNoException();
                    parcel2.writeInt(ruleInstanceCount);
                    return true;
                case 142:
                    String string117 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int automaticZenRuleState = getAutomaticZenRuleState(string117);
                    parcel2.writeNoException();
                    parcel2.writeInt(automaticZenRuleState);
                    return true;
                case 143:
                    String string118 = parcel.readString();
                    Condition condition = (Condition) parcel.readTypedObject(Condition.CREATOR);
                    parcel.enforceNoDataAvail();
                    setAutomaticZenRuleState(string118, condition);
                    parcel2.writeNoException();
                    return true;
                case 144:
                    ZenDeviceEffects zenDeviceEffects = (ZenDeviceEffects) parcel.readTypedObject(ZenDeviceEffects.CREATOR);
                    parcel.enforceNoDataAvail();
                    setManualZenRuleDeviceEffects(zenDeviceEffects);
                    parcel2.writeNoException();
                    return true;
                case 145:
                    int i72 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    byte[] backupPayload = getBackupPayload(i72);
                    parcel2.writeNoException();
                    parcel2.writeByteArray(backupPayload);
                    return true;
                case 146:
                    byte[] bArrCreateByteArray = parcel.createByteArray();
                    int i73 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    applyRestore(bArrCreateByteArray, i73);
                    parcel2.writeNoException();
                    return true;
                case 147:
                    String string119 = parcel.readString();
                    int i74 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    ParceledListSlice appActiveNotifications = getAppActiveNotifications(string119, i74);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(appActiveNotifications, 1);
                    return true;
                case 148:
                    String string120 = parcel.readString();
                    String string121 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    setNotificationDelegate(string120, string121);
                    parcel2.writeNoException();
                    return true;
                case 149:
                    String string122 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String notificationDelegate = getNotificationDelegate(string122);
                    parcel2.writeNoException();
                    parcel2.writeString(notificationDelegate);
                    return true;
                case 150:
                    String string123 = parcel.readString();
                    String string124 = parcel.readString();
                    int i75 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zCanNotifyAsPackage = canNotifyAsPackage(string123, string124, i75);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCanNotifyAsPackage);
                    return true;
                case 151:
                    AttributionSource attributionSource = (AttributionSource) parcel.readTypedObject(AttributionSource.CREATOR);
                    parcel.enforceNoDataAvail();
                    boolean zCanUseFullScreenIntent = canUseFullScreenIntent(attributionSource);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCanUseFullScreenIntent);
                    return true;
                case 152:
                    boolean z34 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setPrivateNotificationsAllowed(z34);
                    parcel2.writeNoException();
                    return true;
                case 153:
                    boolean privateNotificationsAllowed = getPrivateNotificationsAllowed();
                    parcel2.writeNoException();
                    parcel2.writeBoolean(privateNotificationsAllowed);
                    return true;
                case 154:
                    long j4 = parcel.readLong();
                    int i76 = parcel.readInt();
                    boolean z35 = parcel.readBoolean();
                    ArrayList arrayList = new ArrayList();
                    parcel.enforceNoDataAvail();
                    long jPullStats = pullStats(j4, i76, z35, arrayList);
                    parcel2.writeNoException();
                    parcel2.writeLong(jPullStats);
                    parcel2.writeTypedList(arrayList, 1);
                    return true;
                case 155:
                    ComponentName componentName13 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int i77 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    NotificationListenerFilter listenerFilter = getListenerFilter(componentName13, i77);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(listenerFilter, 1);
                    return true;
                case 156:
                    ComponentName componentName14 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int i78 = parcel.readInt();
                    NotificationListenerFilter notificationListenerFilter = (NotificationListenerFilter) parcel.readTypedObject(NotificationListenerFilter.CREATOR);
                    parcel.enforceNoDataAvail();
                    setListenerFilter(componentName14, i78, notificationListenerFilter);
                    parcel2.writeNoException();
                    return true;
                case 157:
                    INotificationListener iNotificationListenerAsInterface27 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    int i79 = parcel.readInt();
                    ArrayList<String> arrayListCreateStringArrayList = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    migrateNotificationFilter(iNotificationListenerAsInterface27, i79, arrayListCreateStringArrayList);
                    parcel2.writeNoException();
                    return true;
                case 158:
                    boolean z36 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setToastRateLimitingEnabled(z36);
                    parcel2.writeNoException();
                    return true;
                case 159:
                    String string125 = parcel.readString();
                    UserHandle userHandle7 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    ICallNotificationEventCallback iCallNotificationEventCallbackAsInterface = ICallNotificationEventCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    registerCallNotificationEventListener(string125, userHandle7, iCallNotificationEventCallbackAsInterface);
                    parcel2.writeNoException();
                    return true;
                case 160:
                    String string126 = parcel.readString();
                    UserHandle userHandle8 = (UserHandle) parcel.readTypedObject(UserHandle.CREATOR);
                    ICallNotificationEventCallback iCallNotificationEventCallbackAsInterface2 = ICallNotificationEventCallback.Stub.asInterface(parcel.readStrongBinder());
                    parcel.enforceNoDataAvail();
                    unregisterCallNotificationEventListener(string126, userHandle8, iCallNotificationEventCallbackAsInterface2);
                    parcel2.writeNoException();
                    return true;
                case 161:
                    String string127 = parcel.readString();
                    int i80 = parcel.readInt();
                    boolean z37 = parcel.readBoolean();
                    boolean z38 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setCanBePromoted(string127, i80, z37, z38);
                    parcel2.writeNoException();
                    return true;
                case 162:
                    String string128 = parcel.readString();
                    int i81 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zAppCanBePromoted = appCanBePromoted(string128, i81);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAppCanBePromoted);
                    return true;
                case 163:
                    String string129 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zCanBePromoted = canBePromoted(string129);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCanBePromoted);
                    return true;
                case 164:
                    INotificationListener iNotificationListenerAsInterface28 = INotificationListener.Stub.asInterface(parcel.readStrongBinder());
                    String string130 = parcel.readString();
                    boolean z39 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAdjustmentTypeSupportedState(iNotificationListenerAsInterface28, string130, z39);
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
                    int i82 = parcel.readInt();
                    boolean z40 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAssistantAdjustmentKeyTypeState(i82, z40);
                    parcel2.writeNoException();
                    return true;
                case 168:
                    String string131 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    String[] adjustmentDeniedPackages = getAdjustmentDeniedPackages(string131);
                    parcel2.writeNoException();
                    parcel2.writeStringArray(adjustmentDeniedPackages);
                    return true;
                case 169:
                    String string132 = parcel.readString();
                    String string133 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsAdjustmentSupportedForPackage = isAdjustmentSupportedForPackage(string132, string133);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAdjustmentSupportedForPackage);
                    return true;
                case 170:
                    String string134 = parcel.readString();
                    String string135 = parcel.readString();
                    boolean z41 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAdjustmentSupportedForPackage(string134, string135, z41);
                    parcel2.writeNoException();
                    return true;
                case 171:
                    String string136 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    incrementCounter(string136);
                    parcel2.writeNoException();
                    return true;
                case 172:
                    ComponentName componentName15 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    int i83 = parcel.readInt();
                    boolean z42 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    registerNotificationListener(componentName15, i83, z42);
                    parcel2.writeNoException();
                    return true;
                case 173:
                    String string137 = parcel.readString();
                    ParceledListSlice parceledListSlice4 = (ParceledListSlice) parcel.readTypedObject(ParceledListSlice.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateNotificationChannels(string137, parceledListSlice4);
                    parcel2.writeNoException();
                    return true;
                case 174:
                    IBinder strongBinder5 = parcel.readStrongBinder();
                    int i84 = parcel.readInt();
                    ComponentName componentName16 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    bindEdgeLightingService(strongBinder5, i84, componentName16);
                    parcel2.writeNoException();
                    return true;
                case 175:
                    IBinder strongBinder6 = parcel.readStrongBinder();
                    String string138 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unbindEdgeLightingService(strongBinder6, string138);
                    parcel2.writeNoException();
                    return true;
                case 176:
                    String string139 = parcel.readString();
                    ArrayList<String> arrayListCreateStringArrayList2 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    updateEdgeLightingPackageList(string139, arrayListCreateStringArrayList2);
                    parcel2.writeNoException();
                    return true;
                case 177:
                    String string140 = parcel.readString();
                    EdgeLightingPolicy edgeLightingPolicy = (EdgeLightingPolicy) parcel.readTypedObject(EdgeLightingPolicy.CREATOR);
                    parcel.enforceNoDataAvail();
                    updateEdgeLightingPolicy(string140, edgeLightingPolicy);
                    parcel2.writeNoException();
                    return true;
                case 178:
                    IBinder strongBinder7 = parcel.readStrongBinder();
                    ComponentName componentName17 = (ComponentName) parcel.readTypedObject(ComponentName.CREATOR);
                    parcel.enforceNoDataAvail();
                    registerEdgeLightingListener(strongBinder7, componentName17);
                    parcel2.writeNoException();
                    return true;
                case 179:
                    IBinder strongBinder8 = parcel.readStrongBinder();
                    String string141 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    unregisterEdgeLightingListener(strongBinder8, string141);
                    parcel2.writeNoException();
                    return true;
                case 180:
                    String string142 = parcel.readString();
                    SemEdgeLightingInfo semEdgeLightingInfo = (SemEdgeLightingInfo) parcel.readTypedObject(SemEdgeLightingInfo.CREATOR);
                    IBinder strongBinder9 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    startEdgeLighting(string142, semEdgeLightingInfo, strongBinder9);
                    parcel2.writeNoException();
                    return true;
                case 181:
                    String string143 = parcel.readString();
                    IBinder strongBinder10 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    stopEdgeLighting(string143, strongBinder10);
                    parcel2.writeNoException();
                    return true;
                case 182:
                    int edgeLightingState = getEdgeLightingState();
                    parcel2.writeNoException();
                    parcel2.writeInt(edgeLightingState);
                    return true;
                case 183:
                    String string144 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zIsEdgeLightingNotificationAllowed = isEdgeLightingNotificationAllowed(string144);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsEdgeLightingNotificationAllowed);
                    return true;
                case 184:
                    int i85 = parcel.readInt();
                    String string145 = parcel.readString();
                    IBinder strongBinder11 = parcel.readStrongBinder();
                    parcel.enforceNoDataAvail();
                    disable(i85, string145, strongBinder11);
                    parcel2.writeNoException();
                    return true;
                case 185:
                    String string146 = parcel.readString();
                    boolean z43 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    disableEdgeLightingNotification(string146, z43);
                    parcel2.writeNoException();
                    return true;
                case 186:
                    String string147 = parcel.readString();
                    int i86 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsPackageEnabled = isPackageEnabled(string147, i86);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsPackageEnabled);
                    return true;
                case 187:
                    String string148 = parcel.readString();
                    String string149 = parcel.readString();
                    int i87 = parcel.readInt();
                    int i88 = parcel.readInt();
                    String string150 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    cancelNotificationByEdge(string148, string149, i87, i88, string150);
                    parcel2.writeNoException();
                    return true;
                case 188:
                    String string151 = parcel.readString();
                    String string152 = parcel.readString();
                    int i89 = parcel.readInt();
                    int i90 = parcel.readInt();
                    String string153 = parcel.readString();
                    String string154 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    cancelNotificationByGroupKey(string151, string152, i89, i90, string153, string154);
                    parcel2.writeNoException();
                    return true;
                case 189:
                    String string155 = parcel.readString();
                    String string156 = parcel.readString();
                    int i91 = parcel.readInt();
                    Bundle bundle2 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i92 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enqueueEdgeNotification(string155, string156, i91, bundle2, i92);
                    parcel2.writeNoException();
                    return true;
                case 190:
                    String string157 = parcel.readString();
                    int i93 = parcel.readInt();
                    Bundle bundle3 = (Bundle) parcel.readTypedObject(Bundle.CREATOR);
                    int i94 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    removeEdgeNotification(string157, i93, bundle3, i94);
                    parcel2.writeNoException();
                    return true;
                case 191:
                    String string158 = parcel.readString();
                    int i95 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsEdgeLightingAllowed = isEdgeLightingAllowed(string158, i95);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsEdgeLightingAllowed);
                    return true;
                case 192:
                    String string159 = parcel.readString();
                    int i96 = parcel.readInt();
                    boolean z44 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAllowEdgeLighting(string159, i96, z44);
                    parcel2.writeNoException();
                    return true;
                case 193:
                    resetDefaultAllowEdgeLighting();
                    parcel2.writeNoException();
                    return true;
                case 194:
                    String string160 = parcel.readString();
                    int i97 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int edgeLightingSettingState = getEdgeLightingSettingState(string160, i97);
                    parcel2.writeNoException();
                    parcel2.writeInt(edgeLightingSettingState);
                    return true;
                case 195:
                    String string161 = parcel.readString();
                    int i98 = parcel.readInt();
                    int i99 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setEdgeLightingState(string161, i98, i99);
                    parcel2.writeNoException();
                    return true;
                case 196:
                    resetDefaultEdgeLightingState();
                    parcel2.writeNoException();
                    return true;
                case 197:
                    int i100 = parcel.readInt();
                    String string162 = parcel.readString();
                    String string163 = parcel.readString();
                    int i101 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zDispatchDelayedWakelockAndBlocked = dispatchDelayedWakelockAndBlocked(i100, string162, string163, i101);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDispatchDelayedWakelockAndBlocked);
                    return true;
                case 198:
                    int i102 = parcel.readInt();
                    String string164 = parcel.readString();
                    String string165 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zDispatchDelayedWakeUpAndBlocked = dispatchDelayedWakeUpAndBlocked(i102, string164, string165);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zDispatchDelayedWakeUpAndBlocked);
                    return true;
                case 199:
                    String string166 = parcel.readString();
                    int i103 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsSubDisplayNotificationAllowed = isSubDisplayNotificationAllowed(string166, i103);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsSubDisplayNotificationAllowed);
                    return true;
                case 200:
                    String string167 = parcel.readString();
                    int i104 = parcel.readInt();
                    boolean z45 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAllowSubDisplayNotification(string167, i104, z45);
                    parcel2.writeNoException();
                    return true;
                case 201:
                    String string168 = parcel.readString();
                    int i105 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsOngoingActivityAllowed = isOngoingActivityAllowed(string168, i105);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsOngoingActivityAllowed);
                    return true;
                case 202:
                    String string169 = parcel.readString();
                    int i106 = parcel.readInt();
                    boolean z46 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAllowOngoingActivity(string169, i106, z46);
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
                    String string170 = parcel.readString();
                    int i107 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean notificationAlertsEnabledForPackage = getNotificationAlertsEnabledForPackage(string170, i107);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(notificationAlertsEnabledForPackage);
                    return true;
                case 206:
                    String string171 = parcel.readString();
                    int i108 = parcel.readInt();
                    boolean z47 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setNotificationAlertsEnabledForPackage(string171, i108, z47);
                    parcel2.writeNoException();
                    return true;
                case 207:
                    int i109 = parcel.readInt();
                    ArrayList<String> arrayListCreateStringArrayList3 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    boolean wearableAppList = setWearableAppList(i109, arrayListCreateStringArrayList3);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(wearableAppList);
                    return true;
                case 208:
                    int i110 = parcel.readInt();
                    String string172 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zAddWearableAppToList = addWearableAppToList(i110, string172);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zAddWearableAppToList);
                    return true;
                case 209:
                    int i111 = parcel.readInt();
                    String string173 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    boolean zRemoveWearableAppFromList = removeWearableAppFromList(i111, string173);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRemoveWearableAppFromList);
                    return true;
                case 210:
                    int i112 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<String> wearableAppList2 = getWearableAppList(i112);
                    parcel2.writeNoException();
                    parcel2.writeStringList(wearableAppList2);
                    return true;
                case 211:
                    int i113 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zRequestListenerHintsForWearable = requestListenerHintsForWearable(i113);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zRequestListenerHintsForWearable);
                    return true;
                case 212:
                    String string174 = parcel.readString();
                    int i114 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int lockScreenNotificationVisibilityForPackage = getLockScreenNotificationVisibilityForPackage(string174, i114);
                    parcel2.writeNoException();
                    parcel2.writeInt(lockScreenNotificationVisibilityForPackage);
                    return true;
                case 213:
                    String string175 = parcel.readString();
                    int i115 = parcel.readInt();
                    int i116 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    setLockScreenNotificationVisibilityForPackage(string175, i115, i116);
                    parcel2.writeNoException();
                    return true;
                case 214:
                    String string176 = parcel.readString();
                    int i117 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsAllowNotificationPopUpForPackage = isAllowNotificationPopUpForPackage(string176, i117);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAllowNotificationPopUpForPackage);
                    return true;
                case 215:
                    String string177 = parcel.readString();
                    int i118 = parcel.readInt();
                    boolean z48 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAllowNotificationPopUpForPackage(string177, i118, z48);
                    parcel2.writeNoException();
                    return true;
                case 216:
                    String string178 = parcel.readString();
                    int i119 = parcel.readInt();
                    String string179 = parcel.readString();
                    int i120 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsAlertsAllowed = isAlertsAllowed(string178, i119, string179, i120);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsAlertsAllowed);
                    return true;
                case 217:
                    String string180 = parcel.readString();
                    int i121 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zIsReminderEnabled = isReminderEnabled(string180, i121);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zIsReminderEnabled);
                    return true;
                case 218:
                    String string181 = parcel.readString();
                    int i122 = parcel.readInt();
                    boolean z49 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setReminderEnabledForPackage(string181, i122, z49);
                    parcel2.writeNoException();
                    return true;
                case 219:
                    int i123 = parcel.readInt();
                    boolean z50 = parcel.readBoolean();
                    ArrayList<String> arrayListCreateStringArrayList4 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    setReminderEnabled(i123, z50, arrayListCreateStringArrayList4);
                    parcel2.writeNoException();
                    return true;
                case 220:
                    int i124 = parcel.readInt();
                    String string182 = parcel.readString();
                    String string183 = parcel.readString();
                    int i125 = parcel.readInt();
                    String string184 = parcel.readString();
                    String string185 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    addReplyHistory(i124, string182, string183, i125, string184, string185);
                    parcel2.writeNoException();
                    return true;
                case 221:
                    String string186 = parcel.readString();
                    String string187 = parcel.readString();
                    int i126 = parcel.readInt();
                    String string188 = parcel.readString();
                    String string189 = parcel.readString();
                    int i127 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    List<Bundle> notificationHistoryDataForPackage = getNotificationHistoryDataForPackage(string186, string187, i126, string188, string189, i127);
                    parcel2.writeNoException();
                    parcel2.writeTypedList(notificationHistoryDataForPackage, 1);
                    return true;
                case 222:
                    String string190 = parcel.readString();
                    String string191 = parcel.readString();
                    int i128 = parcel.readInt();
                    String string192 = parcel.readString();
                    String string193 = parcel.readString();
                    int i129 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    NotificationHistory notificationHistoryForPackage = getNotificationHistoryForPackage(string190, string191, i128, string192, string193, i129);
                    parcel2.writeNoException();
                    parcel2.writeTypedObject(notificationHistoryForPackage, 1);
                    return true;
                case 223:
                    int i130 = parcel.readInt();
                    String string194 = parcel.readString();
                    boolean z51 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    updateCancelEvent(i130, string194, z51);
                    parcel2.writeNoException();
                    return true;
                case 224:
                    ArrayList<String> arrayListCreateStringArrayList5 = parcel.createStringArrayList();
                    parcel.enforceNoDataAvail();
                    setRestoreBlockListForSS(arrayListCreateStringArrayList5);
                    parcel2.writeNoException();
                    return true;
                case 225:
                    int allNotificationListenersCount = getAllNotificationListenersCount();
                    parcel2.writeNoException();
                    parcel2.writeInt(allNotificationListenersCount);
                    return true;
                case 226:
                    int i131 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int blockedAppCount = getBlockedAppCount(i131);
                    parcel2.writeNoException();
                    parcel2.writeInt(blockedAppCount);
                    return true;
                case 227:
                    String string195 = parcel.readString();
                    int i132 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean zCanAppBypassDnd = canAppBypassDnd(string195, i132);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(zCanAppBypassDnd);
                    return true;
                case 228:
                    String string196 = parcel.readString();
                    int i133 = parcel.readInt();
                    boolean z52 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    setAppBypassDnd(string196, i133, z52);
                    parcel2.writeNoException();
                    return true;
                case 229:
                    int i134 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int appsBypassingDndCount = getAppsBypassingDndCount(i134);
                    parcel2.writeNoException();
                    parcel2.writeInt(appsBypassingDndCount);
                    return true;
                case 230:
                    String string197 = parcel.readString();
                    IBinder strongBinder12 = parcel.readStrongBinder();
                    CharSequence charSequence2 = (CharSequence) parcel.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
                    int i135 = parcel.readInt();
                    boolean z53 = parcel.readBoolean();
                    int i136 = parcel.readInt();
                    ITransientNotificationCallback iTransientNotificationCallbackAsInterface2 = ITransientNotificationCallback.Stub.asInterface(parcel.readStrongBinder());
                    String string198 = parcel.readString();
                    int i137 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enqueueTextToastForDex(string197, strongBinder12, charSequence2, i135, z53, i136, iTransientNotificationCallbackAsInterface2, string198, i137);
                    parcel2.writeNoException();
                    return true;
                case 231:
                    String string199 = parcel.readString();
                    IBinder strongBinder13 = parcel.readStrongBinder();
                    ITransientNotification iTransientNotificationAsInterface2 = ITransientNotification.Stub.asInterface(parcel.readStrongBinder());
                    int i138 = parcel.readInt();
                    boolean z54 = parcel.readBoolean();
                    int i139 = parcel.readInt();
                    String string200 = parcel.readString();
                    int i140 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    enqueueToastForDex(string199, strongBinder13, iTransientNotificationAsInterface2, i138, z54, i139, string200, i140);
                    parcel2.writeNoException();
                    return true;
                case 232:
                    String string201 = parcel.readString();
                    int i141 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    int iIsNotificationTurnedOff = isNotificationTurnedOff(string201, i141);
                    parcel2.writeNoException();
                    parcel2.writeInt(iIsNotificationTurnedOff);
                    return true;
                case 233:
                    String string202 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int notificationSoundStatus = getNotificationSoundStatus(string202);
                    parcel2.writeNoException();
                    parcel2.writeInt(notificationSoundStatus);
                    return true;
                case 234:
                    String string203 = parcel.readString();
                    int i142 = parcel.readInt();
                    parcel.enforceNoDataAvail();
                    boolean notificationTurnOff = setNotificationTurnOff(string203, i142);
                    parcel2.writeNoException();
                    parcel2.writeBoolean(notificationTurnOff);
                    return true;
                case 235:
                    boolean z55 = parcel.readBoolean();
                    parcel.enforceNoDataAvail();
                    int notificationSettingStatus = getNotificationSettingStatus(z55);
                    parcel2.writeNoException();
                    parcel2.writeInt(notificationSettingStatus);
                    return true;
                case 236:
                    String string204 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    int appNotificationSettingStatus = getAppNotificationSettingStatus(string204);
                    parcel2.writeNoException();
                    parcel2.writeInt(appNotificationSettingStatus);
                    return true;
                case 237:
                    String string205 = parcel.readString();
                    parcel.enforceNoDataAvail();
                    List<String> blockInfoOfNotificationsForOverflow = getBlockInfoOfNotificationsForOverflow(string205);
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
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(1, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void clearData(String str, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(2, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean enqueueTextToast(String str, IBinder iBinder, CharSequence charSequence, int i, boolean z, int i2, ITransientNotificationCallback iTransientNotificationCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongBinder(iBinder);
                    if (charSequence != null) {
                        parcelObtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iTransientNotificationCallback);
                    this.mRemote.transact(3, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean enqueueToast(String str, IBinder iBinder, ITransientNotification iTransientNotification, int i, boolean z, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeStrongInterface(iTransientNotification);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(4, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void cancelToast(String str, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(5, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void finishToken(String str, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(6, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void enqueueNotificationWithTag(String str, String str2, String str3, int i, Notification notification, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(notification, 0);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(7, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void cancelNotificationWithTag(String str, String str2, String str3, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(8, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean isInCall(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(9, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setShowBadge(String str, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(10, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean canShowBadge(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(11, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean hasSentValidMsg(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(12, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean isInInvalidMsgState(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(13, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean hasUserDemotedInvalidMsgApp(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(14, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setInvalidMsgAppDemoted(String str, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(15, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean hasSentValidBubble(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(16, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setNotificationsEnabledForPackage(String str, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(17, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setNotificationsEnabledWithImportanceLockForPackage(String str, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(18, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean areNotificationsEnabledForPackage(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(19, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean areNotificationsEnabled(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(20, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int getPackageImportance(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(21, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean isImportanceLocked(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(22, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public List<String> getAllowedAssistantAdjustments(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(23, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void allowAssistantAdjustment(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(24, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void disallowAssistantAdjustment(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(25, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean shouldHideSilentStatusIcons(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(26, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setHideSilentStatusIcons(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(27, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setBubblesAllowed(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(28, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean areBubblesAllowed(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(29, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean areBubblesEnabled(UserHandle userHandle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(30, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int getBubblePreferenceForPackage(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(31, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void createNotificationChannelGroups(String str, ParceledListSlice parceledListSlice) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(parceledListSlice, 0);
                    this.mRemote.transact(32, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void createNotificationChannels(String str, ParceledListSlice parceledListSlice) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(parceledListSlice, 0);
                    this.mRemote.transact(33, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void createNotificationChannelsForPackage(String str, int i, ParceledListSlice parceledListSlice) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(parceledListSlice, 0);
                    this.mRemote.transact(34, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public ParceledListSlice getConversations(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(35, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public ParceledListSlice getConversationsForPackage(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(36, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public ParceledListSlice getNotificationChannelGroupsForPackage(String str, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(37, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public NotificationChannelGroup getNotificationChannelGroupForPackage(String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(38, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (NotificationChannelGroup) parcelObtain2.readTypedObject(NotificationChannelGroup.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public NotificationChannelGroup getPopulatedNotificationChannelGroupForPackage(String str, int i, String str2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(39, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (NotificationChannelGroup) parcelObtain2.readTypedObject(NotificationChannelGroup.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public ParceledListSlice getRecentBlockedNotificationChannelGroupsForPackage(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(40, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void updateNotificationChannelGroupForPackage(String str, int i, NotificationChannelGroup notificationChannelGroup) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(notificationChannelGroup, 0);
                    this.mRemote.transact(41, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void updateNotificationChannelForPackage(String str, int i, NotificationChannel notificationChannel) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(notificationChannel, 0);
                    this.mRemote.transact(42, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void unlockNotificationChannel(String str, int i, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(43, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void unlockAllNotificationChannels() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(44, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public NotificationChannel getNotificationChannel(String str, int i, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(45, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (NotificationChannel) parcelObtain2.readTypedObject(NotificationChannel.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public NotificationChannel getConversationNotificationChannel(String str, int i, String str2, String str3, boolean z, String str4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeString(str4);
                    this.mRemote.transact(46, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (NotificationChannel) parcelObtain2.readTypedObject(NotificationChannel.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void createConversationNotificationChannelForPackage(String str, int i, NotificationChannel notificationChannel, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(notificationChannel, 0);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(47, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public NotificationChannel getNotificationChannelForPackage(String str, int i, String str2, String str3, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(48, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (NotificationChannel) parcelObtain2.readTypedObject(NotificationChannel.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void deleteNotificationChannel(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(49, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public ParceledListSlice getNotificationChannels(String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(50, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public ParceledListSlice getNotificationChannelsForPackage(String str, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(51, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int getNumNotificationChannelsForPackage(String str, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(52, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int getDeletedChannelCount(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(53, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int getBlockedChannelCount(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(54, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void deleteNotificationChannelGroup(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(55, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public NotificationChannelGroup getNotificationChannelGroup(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(56, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (NotificationChannelGroup) parcelObtain2.readTypedObject(NotificationChannelGroup.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public ParceledListSlice getNotificationChannelGroups(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(57, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public ParceledListSlice getNotificationChannelGroupsWithoutChannels(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(58, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean onlyHasDefaultChannel(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(59, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean areChannelsBypassingDnd() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(60, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public ParceledListSlice getNotificationChannelsBypassingDnd(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(61, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public ParceledListSlice getPackagesBypassingDnd(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(62, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public List<String> getPackagesWithAnyChannels(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(63, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean isPackagePaused(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(64, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void deleteNotificationHistoryItem(String str, int i, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(65, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean isPermissionFixed(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(66, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void silenceNotificationSound() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(67, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public StatusBarNotification[] getActiveNotifications(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(68, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (StatusBarNotification[]) parcelObtain2.createTypedArray(StatusBarNotification.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public StatusBarNotification[] getActiveNotificationsWithAttribution(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(69, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (StatusBarNotification[]) parcelObtain2.createTypedArray(StatusBarNotification.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public StatusBarNotification[] getHistoricalNotifications(String str, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(70, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (StatusBarNotification[]) parcelObtain2.createTypedArray(StatusBarNotification.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public StatusBarNotification[] getHistoricalNotificationsWithAttribution(String str, String str2, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(71, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (StatusBarNotification[]) parcelObtain2.createTypedArray(StatusBarNotification.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public NotificationHistory getNotificationHistory(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(72, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (NotificationHistory) parcelObtain2.readTypedObject(NotificationHistory.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void registerListener(INotificationListener iNotificationListener, ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iNotificationListener);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(73, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void unregisterListener(INotificationListener iNotificationListener, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iNotificationListener);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(74, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void cancelNotificationFromListener(INotificationListener iNotificationListener, String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iNotificationListener);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(75, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void cancelNotificationsFromListener(INotificationListener iNotificationListener, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iNotificationListener);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(76, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void snoozeNotificationUntilContextFromListener(INotificationListener iNotificationListener, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iNotificationListener);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(77, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void snoozeNotificationUntilFromListener(INotificationListener iNotificationListener, String str, long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iNotificationListener);
                    parcelObtain.writeString(str);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(78, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void requestBindListener(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(79, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void requestUnbindListener(INotificationListener iNotificationListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iNotificationListener);
                    this.mRemote.transact(80, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void requestUnbindListenerComponent(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(81, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void requestBindProvider(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(82, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void requestUnbindProvider(IConditionProvider iConditionProvider) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iConditionProvider);
                    this.mRemote.transact(83, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setNotificationsShownFromListener(INotificationListener iNotificationListener, String[] strArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iNotificationListener);
                    parcelObtain.writeStringArray(strArr);
                    this.mRemote.transact(84, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public ParceledListSlice getActiveNotificationsFromListener(INotificationListener iNotificationListener, String[] strArr, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iNotificationListener);
                    parcelObtain.writeStringArray(strArr);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(85, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public ParceledListSlice getSnoozedNotificationsFromListener(INotificationListener iNotificationListener, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iNotificationListener);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(86, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void clearRequestedListenerHints(INotificationListener iNotificationListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iNotificationListener);
                    this.mRemote.transact(87, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void requestHintsFromListener(INotificationListener iNotificationListener, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iNotificationListener);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(88, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int getHintsFromListener(INotificationListener iNotificationListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iNotificationListener);
                    this.mRemote.transact(89, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int getHintsFromListenerNoToken() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(90, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void requestInterruptionFilterFromListener(INotificationListener iNotificationListener, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iNotificationListener);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(91, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int getInterruptionFilterFromListener(INotificationListener iNotificationListener) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iNotificationListener);
                    this.mRemote.transact(92, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setOnNotificationPostedTrimFromListener(INotificationListener iNotificationListener, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iNotificationListener);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(93, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setInterruptionFilter(String str, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(94, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public NotificationChannel createConversationNotificationChannelForPackageFromPrivilegedListener(INotificationListener iNotificationListener, String str, UserHandle userHandle, String str2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iNotificationListener);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(95, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (NotificationChannel) parcelObtain2.readTypedObject(NotificationChannel.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void updateNotificationChannelGroupFromPrivilegedListener(INotificationListener iNotificationListener, String str, UserHandle userHandle, NotificationChannelGroup notificationChannelGroup) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iNotificationListener);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    parcelObtain.writeTypedObject(notificationChannelGroup, 0);
                    this.mRemote.transact(96, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void updateNotificationChannelFromPrivilegedListener(INotificationListener iNotificationListener, String str, UserHandle userHandle, NotificationChannel notificationChannel) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iNotificationListener);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    parcelObtain.writeTypedObject(notificationChannel, 0);
                    this.mRemote.transact(97, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public ParceledListSlice getNotificationChannelsFromPrivilegedListener(INotificationListener iNotificationListener, String str, UserHandle userHandle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iNotificationListener);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(98, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public ParceledListSlice getNotificationChannelGroupsFromPrivilegedListener(INotificationListener iNotificationListener, String str, UserHandle userHandle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iNotificationListener);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    this.mRemote.transact(99, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void applyEnqueuedAdjustmentFromAssistant(INotificationListener iNotificationListener, Adjustment adjustment) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iNotificationListener);
                    parcelObtain.writeTypedObject(adjustment, 0);
                    this.mRemote.transact(100, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void applyAdjustmentFromAssistant(INotificationListener iNotificationListener, Adjustment adjustment) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iNotificationListener);
                    parcelObtain.writeTypedObject(adjustment, 0);
                    this.mRemote.transact(101, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void applyAdjustmentsFromAssistant(INotificationListener iNotificationListener, List<Adjustment> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iNotificationListener);
                    parcelObtain.writeTypedList(list, 0);
                    this.mRemote.transact(102, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void unsnoozeNotificationFromAssistant(INotificationListener iNotificationListener, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iNotificationListener);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(103, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void unsnoozeNotificationFromSystemListener(INotificationListener iNotificationListener, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iNotificationListener);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(104, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public ComponentName getEffectsSuppressor() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(105, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ComponentName) parcelObtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean matchesCallFilter(Bundle bundle) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(bundle, 0);
                    this.mRemote.transact(106, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void cleanUpCallersAfter(long j) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    this.mRemote.transact(107, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean isSystemConditionProviderEnabled(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(108, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean isNotificationListenerAccessGranted(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(109, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean isNotificationListenerAccessGrantedForUser(ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(110, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean isNotificationAssistantAccessGranted(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(111, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setNotificationListenerAccessGranted(ComponentName componentName, boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(112, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setNotificationAssistantAccessGranted(ComponentName componentName, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(113, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setNotificationListenerAccessGrantedForUser(ComponentName componentName, int i, boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(114, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setNotificationAssistantAccessGrantedForUser(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(115, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public List<String> getEnabledNotificationListenerPackages() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(116, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public List<ComponentName> getEnabledNotificationListeners(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(117, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(ComponentName.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public ComponentName getAllowedNotificationAssistantForUser(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(118, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ComponentName) parcelObtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public ComponentName getAllowedNotificationAssistant() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(119, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ComponentName) parcelObtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public ComponentName getDefaultNotificationAssistant() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(120, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ComponentName) parcelObtain2.readTypedObject(ComponentName.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setNASMigrationDoneAndResetDefault(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(121, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean hasEnabledNotificationListener(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(122, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int getZenMode() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(123, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public ZenModeConfig getZenModeConfig() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(124, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ZenModeConfig) parcelObtain2.readTypedObject(ZenModeConfig.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public NotificationManager.Policy getConsolidatedNotificationPolicy() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(125, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (NotificationManager.Policy) parcelObtain2.readTypedObject(NotificationManager.Policy.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setZenMode(int i, Uri uri, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(uri, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(126, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void notifyConditions(String str, IConditionProvider iConditionProvider, Condition[] conditionArr) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongInterface(iConditionProvider);
                    parcelObtain.writeTypedArray(conditionArr, 0);
                    this.mRemote.transact(127, parcelObtain, null, 1);
                } finally {
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean isNotificationPolicyAccessGranted(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(128, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public NotificationManager.Policy getNotificationPolicy(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(129, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (NotificationManager.Policy) parcelObtain2.readTypedObject(NotificationManager.Policy.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setNotificationPolicy(String str, NotificationManager.Policy policy, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(policy, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(130, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean isNotificationPolicyAccessGrantedForPackage(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(131, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setNotificationPolicyAccessGranted(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(132, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setNotificationPolicyAccessGrantedForUser(String str, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(133, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public ZenPolicy getDefaultZenPolicy() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(134, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ZenPolicy) parcelObtain2.readTypedObject(ZenPolicy.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public AutomaticZenRule getAutomaticZenRule(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(135, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (AutomaticZenRule) parcelObtain2.readTypedObject(AutomaticZenRule.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public ParceledListSlice getAutomaticZenRules() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(136, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public String addAutomaticZenRule(AutomaticZenRule automaticZenRule, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(automaticZenRule, 0);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(137, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean updateAutomaticZenRule(String str, AutomaticZenRule automaticZenRule, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(automaticZenRule, 0);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(138, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean removeAutomaticZenRule(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(139, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean removeAutomaticZenRules(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(140, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int getRuleInstanceCount(ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(141, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int getAutomaticZenRuleState(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(142, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setAutomaticZenRuleState(String str, Condition condition) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(condition, 0);
                    this.mRemote.transact(143, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setManualZenRuleDeviceEffects(ZenDeviceEffects zenDeviceEffects) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(zenDeviceEffects, 0);
                    this.mRemote.transact(144, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public byte[] getBackupPayload(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(145, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createByteArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void applyRestore(byte[] bArr, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeByteArray(bArr);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(146, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public ParceledListSlice getAppActiveNotifications(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(147, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (ParceledListSlice) parcelObtain2.readTypedObject(ParceledListSlice.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setNotificationDelegate(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(148, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public String getNotificationDelegate(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(149, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readString();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean canNotifyAsPackage(String str, String str2, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(150, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean canUseFullScreenIntent(AttributionSource attributionSource) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(attributionSource, 0);
                    this.mRemote.transact(151, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setPrivateNotificationsAllowed(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(152, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean getPrivateNotificationsAllowed() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(153, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public long pullStats(long j, int i, boolean z, List<ParcelFileDescriptor> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeLong(j);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(154, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    long j2 = parcelObtain2.readLong();
                    parcelObtain2.readTypedList(list, ParcelFileDescriptor.CREATOR);
                    return j2;
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public NotificationListenerFilter getListenerFilter(ComponentName componentName, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(155, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (NotificationListenerFilter) parcelObtain2.readTypedObject(NotificationListenerFilter.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setListenerFilter(ComponentName componentName, int i, NotificationListenerFilter notificationListenerFilter) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(notificationListenerFilter, 0);
                    this.mRemote.transact(156, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void migrateNotificationFilter(INotificationListener iNotificationListener, int i, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iNotificationListener);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(157, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setToastRateLimitingEnabled(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(158, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void registerCallNotificationEventListener(String str, UserHandle userHandle, ICallNotificationEventCallback iCallNotificationEventCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    parcelObtain.writeStrongInterface(iCallNotificationEventCallback);
                    this.mRemote.transact(159, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void unregisterCallNotificationEventListener(String str, UserHandle userHandle, ICallNotificationEventCallback iCallNotificationEventCallback) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(userHandle, 0);
                    parcelObtain.writeStrongInterface(iCallNotificationEventCallback);
                    this.mRemote.transact(160, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setCanBePromoted(String str, int i, boolean z, boolean z2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeBoolean(z2);
                    this.mRemote.transact(161, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean appCanBePromoted(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(162, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean canBePromoted(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(163, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setAdjustmentTypeSupportedState(INotificationListener iNotificationListener, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongInterface(iNotificationListener);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(164, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public List<String> getUnsupportedAdjustmentTypes() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(165, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int[] getAllowedAdjustmentKeyTypes() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(166, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createIntArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setAssistantAdjustmentKeyTypeState(int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(167, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public String[] getAdjustmentDeniedPackages(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(168, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArray();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean isAdjustmentSupportedForPackage(String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(169, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setAdjustmentSupportedForPackage(String str, String str2, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(170, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void incrementCounter(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(171, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void registerNotificationListener(ComponentName componentName, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeTypedObject(componentName, 0);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(172, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void updateNotificationChannels(String str, ParceledListSlice parceledListSlice) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(parceledListSlice, 0);
                    this.mRemote.transact(173, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void bindEdgeLightingService(IBinder iBinder, int i, ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(174, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void unbindEdgeLightingService(IBinder iBinder, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(175, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void updateEdgeLightingPackageList(String str, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(176, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void updateEdgeLightingPolicy(String str, EdgeLightingPolicy edgeLightingPolicy) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(edgeLightingPolicy, 0);
                    this.mRemote.transact(177, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void registerEdgeLightingListener(IBinder iBinder, ComponentName componentName) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeTypedObject(componentName, 0);
                    this.mRemote.transact(178, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void unregisterEdgeLightingListener(IBinder iBinder, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(179, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void startEdgeLighting(String str, SemEdgeLightingInfo semEdgeLightingInfo, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeTypedObject(semEdgeLightingInfo, 0);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(180, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void stopEdgeLighting(String str, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(181, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int getEdgeLightingState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(182, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean isEdgeLightingNotificationAllowed(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(183, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void disable(int i, String str, IBinder iBinder) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongBinder(iBinder);
                    this.mRemote.transact(184, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void disableEdgeLightingNotification(String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(185, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean isPackageEnabled(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(186, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void cancelNotificationByEdge(String str, String str2, int i, int i2, String str3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str3);
                    this.mRemote.transact(187, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void cancelNotificationByGroupKey(String str, String str2, int i, int i2, String str3, String str4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    this.mRemote.transact(188, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void enqueueEdgeNotification(String str, String str2, int i, Bundle bundle, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(189, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void removeEdgeNotification(String str, int i, Bundle bundle, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeTypedObject(bundle, 0);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(190, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean isEdgeLightingAllowed(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(191, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setAllowEdgeLighting(String str, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(192, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void resetDefaultAllowEdgeLighting() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(193, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int getEdgeLightingSettingState(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(194, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setEdgeLightingState(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(195, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void resetDefaultEdgeLightingState() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(196, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean dispatchDelayedWakelockAndBlocked(int i, String str, String str2, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(197, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean dispatchDelayedWakeUpAndBlocked(int i, String str, String str2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    this.mRemote.transact(198, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean isSubDisplayNotificationAllowed(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(199, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setAllowSubDisplayNotification(String str, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(200, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean isOngoingActivityAllowed(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(201, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setAllowOngoingActivity(String str, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(202, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void resetDefaultAllowOngoingActivity() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(203, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public List<String> getAllowedOngoingActivityAppList() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(204, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean getNotificationAlertsEnabledForPackage(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(205, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setNotificationAlertsEnabledForPackage(String str, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(206, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean setWearableAppList(int i, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(207, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean addWearableAppToList(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(208, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean removeWearableAppFromList(int i, String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(209, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public List<String> getWearableAppList(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(210, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean requestListenerHintsForWearable(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(211, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int getLockScreenNotificationVisibilityForPackage(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(212, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setLockScreenNotificationVisibilityForPackage(String str, int i, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(213, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean isAllowNotificationPopUpForPackage(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(214, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setAllowNotificationPopUpForPackage(String str, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(215, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean isAlertsAllowed(String str, int i, String str2, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(216, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean isReminderEnabled(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(217, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setReminderEnabledForPackage(String str, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(218, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setReminderEnabled(int i, boolean z, List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(219, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void addReplyHistory(int i, String str, String str2, int i2, String str3, String str4) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    this.mRemote.transact(220, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public List<Bundle> getNotificationHistoryDataForPackage(String str, String str2, int i, String str3, String str4, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(221, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createTypedArrayList(Bundle.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public NotificationHistory getNotificationHistoryForPackage(String str, String str2, int i, String str3, String str4, int i2) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str3);
                    parcelObtain.writeString(str4);
                    parcelObtain.writeInt(i2);
                    this.mRemote.transact(222, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return (NotificationHistory) parcelObtain2.readTypedObject(NotificationHistory.CREATOR);
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void updateCancelEvent(int i, String str, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeString(str);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(223, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setRestoreBlockListForSS(List<String> list) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeStringList(list);
                    this.mRemote.transact(224, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int getAllNotificationListenersCount() throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(225, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int getBlockedAppCount(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(226, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean canAppBypassDnd(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(227, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void setAppBypassDnd(String str, int i, boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(228, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int getAppsBypassingDndCount(int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(229, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void enqueueTextToastForDex(String str, IBinder iBinder, CharSequence charSequence, int i, boolean z, int i2, ITransientNotificationCallback iTransientNotificationCallback, String str2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongBinder(iBinder);
                    if (charSequence != null) {
                        parcelObtain.writeInt(1);
                        TextUtils.writeToParcel(charSequence, parcelObtain, 0);
                    } else {
                        parcelObtain.writeInt(0);
                    }
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeStrongInterface(iTransientNotificationCallback);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(230, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public void enqueueToastForDex(String str, IBinder iBinder, ITransientNotification iTransientNotification, int i, boolean z, int i2, String str2, int i3) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeStrongBinder(iBinder);
                    parcelObtain.writeStrongInterface(iTransientNotification);
                    parcelObtain.writeInt(i);
                    parcelObtain.writeBoolean(z);
                    parcelObtain.writeInt(i2);
                    parcelObtain.writeString(str2);
                    parcelObtain.writeInt(i3);
                    this.mRemote.transact(231, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int isNotificationTurnedOff(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(232, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int getNotificationSoundStatus(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(233, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public boolean setNotificationTurnOff(String str, int i) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    parcelObtain.writeInt(i);
                    this.mRemote.transact(234, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readBoolean();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int getNotificationSettingStatus(boolean z) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeBoolean(z);
                    this.mRemote.transact(235, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public int getAppNotificationSettingStatus(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(236, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.readInt();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
                }
            }

            @Override // android.app.INotificationManager
            public List<String> getBlockInfoOfNotificationsForOverflow(String str) throws RemoteException {
                Parcel parcelObtain = Parcel.obtain(asBinder());
                Parcel parcelObtain2 = Parcel.obtain();
                try {
                    parcelObtain.writeInterfaceToken(Stub.DESCRIPTOR);
                    parcelObtain.writeString(str);
                    this.mRemote.transact(237, parcelObtain, parcelObtain2, 0);
                    parcelObtain2.readException();
                    return parcelObtain2.createStringArrayList();
                } finally {
                    parcelObtain2.recycle();
                    parcelObtain.recycle();
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
