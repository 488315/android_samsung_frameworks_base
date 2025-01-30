package android.app;

import android.Manifest;
import android.content.AttributionSource;
import android.content.ComponentName;
import android.content.p002pm.ParceledListSlice;
import android.hardware.display.SemWifiDisplayParameter;
import android.net.Uri;
import android.p009os.Binder;
import android.p009os.Bundle;
import android.p009os.IBinder;
import android.p009os.IInterface;
import android.p009os.Parcel;
import android.p009os.ParcelFileDescriptor;
import android.p009os.PermissionEnforcer;
import android.p009os.RemoteException;
import android.p009os.UserHandle;
import android.sec.enterprise.content.SecContentProviderURI;
import android.service.notification.Adjustment;
import android.service.notification.Condition;
import android.service.notification.IConditionProvider;
import android.service.notification.INotificationListener;
import android.service.notification.NotificationListenerFilter;
import android.service.notification.StatusBarNotification;
import android.service.notification.ZenModeConfig;
import android.service.notification.ZenModeDiff;
import android.text.TextUtils;
import com.samsung.android.edge.EdgeLightingPolicy;
import com.samsung.android.edge.SemEdgeLightingInfo;
import java.util.ArrayList;
import java.util.List;

/* loaded from: classes.dex */
public interface INotificationManager extends IInterface {
  String addAutomaticZenRule(AutomaticZenRule automaticZenRule, String str) throws RemoteException;

  void addReplyHistory(int i, String str, String str2, int i2, String str3, String str4)
      throws RemoteException;

  boolean addWearableAppToList(int i, String str) throws RemoteException;

  void applyAdjustmentFromAssistant(
      INotificationListener iNotificationListener, Adjustment adjustment) throws RemoteException;

  void applyAdjustmentsFromAssistant(
      INotificationListener iNotificationListener, List<Adjustment> list) throws RemoteException;

  void applyEnqueuedAdjustmentFromAssistant(
      INotificationListener iNotificationListener, Adjustment adjustment) throws RemoteException;

  void applyRestore(byte[] bArr, int i) throws RemoteException;

  boolean areBubblesAllowed(String str) throws RemoteException;

  boolean areBubblesEnabled(UserHandle userHandle) throws RemoteException;

  boolean areChannelsBypassingDnd() throws RemoteException;

  boolean areNotificationsEnabled(String str) throws RemoteException;

  boolean areNotificationsEnabledForPackage(String str, int i) throws RemoteException;

  void bindEdgeLightingService(IBinder iBinder, int i, ComponentName componentName)
      throws RemoteException;

  boolean canAppBypassDnd(String str, int i) throws RemoteException;

  boolean canNotifyAsPackage(String str, String str2, int i) throws RemoteException;

  boolean canShowBadge(String str, int i) throws RemoteException;

  boolean canUseFullScreenIntent(AttributionSource attributionSource) throws RemoteException;

  void cancelAllNotifications(String str, int i) throws RemoteException;

  void cancelNotificationByEdge(String str, String str2, int i, int i2, String str3)
      throws RemoteException;

  void cancelNotificationByGroupKey(
      String str, String str2, int i, int i2, String str3, String str4) throws RemoteException;

  void cancelNotificationFromListener(
      INotificationListener iNotificationListener, String str, String str2, int i)
      throws RemoteException;

  void cancelNotificationWithTag(String str, String str2, String str3, int i, int i2)
      throws RemoteException;

  void cancelNotificationsFromListener(INotificationListener iNotificationListener, String[] strArr)
      throws RemoteException;

  void cancelToast(String str, IBinder iBinder) throws RemoteException;

  void cleanUpCallersAfter(long j) throws RemoteException;

  void clearData(String str, int i, boolean z) throws RemoteException;

  void clearRequestedListenerHints(INotificationListener iNotificationListener)
      throws RemoteException;

  void createConversationNotificationChannelForPackage(
      String str, int i, NotificationChannel notificationChannel, String str2)
      throws RemoteException;

  void createNotificationChannelGroups(String str, ParceledListSlice parceledListSlice)
      throws RemoteException;

  void createNotificationChannels(String str, ParceledListSlice parceledListSlice)
      throws RemoteException;

  void createNotificationChannelsForPackage(String str, int i, ParceledListSlice parceledListSlice)
      throws RemoteException;

  void deleteNotificationChannel(String str, String str2) throws RemoteException;

  void deleteNotificationChannelGroup(String str, String str2) throws RemoteException;

  void deleteNotificationHistoryItem(String str, int i, long j) throws RemoteException;

  void disable(int i, String str, IBinder iBinder) throws RemoteException;

  void disableEdgeLightingNotification(String str, boolean z) throws RemoteException;

  boolean dispatchDelayedWakeUpAndBlocked(int i, String str, String str2) throws RemoteException;

  boolean dispatchDelayedWakelockAndBlocked(int i, String str, String str2, int i2)
      throws RemoteException;

  void enqueueEdgeNotification(String str, String str2, int i, Bundle bundle, int i2)
      throws RemoteException;

  void enqueueNotificationWithTag(
      String str, String str2, String str3, int i, Notification notification, int i2)
      throws RemoteException;

  void enqueueTextToast(
      String str,
      IBinder iBinder,
      CharSequence charSequence,
      int i,
      boolean z,
      int i2,
      ITransientNotificationCallback iTransientNotificationCallback)
      throws RemoteException;

  void enqueueTextToastForDex(
      String str,
      IBinder iBinder,
      CharSequence charSequence,
      int i,
      boolean z,
      int i2,
      ITransientNotificationCallback iTransientNotificationCallback,
      String str2,
      int i3)
      throws RemoteException;

  void enqueueToast(
      String str,
      IBinder iBinder,
      ITransientNotification iTransientNotification,
      int i,
      boolean z,
      int i2)
      throws RemoteException;

  void enqueueToastForDex(
      String str,
      IBinder iBinder,
      ITransientNotification iTransientNotification,
      int i,
      boolean z,
      int i2,
      String str2,
      int i3)
      throws RemoteException;

  void finishToken(String str, IBinder iBinder) throws RemoteException;

  StatusBarNotification[] getActiveNotifications(String str) throws RemoteException;

  ParceledListSlice getActiveNotificationsFromListener(
      INotificationListener iNotificationListener, String[] strArr, int i) throws RemoteException;

  StatusBarNotification[] getActiveNotificationsWithAttribution(String str, String str2)
      throws RemoteException;

  int getAllNotificationListenersCount() throws RemoteException;

  List<String> getAllowedAssistantAdjustments(String str) throws RemoteException;

  ComponentName getAllowedNotificationAssistant() throws RemoteException;

  ComponentName getAllowedNotificationAssistantForUser(int i) throws RemoteException;

  ParceledListSlice getAppActiveNotifications(String str, int i) throws RemoteException;

  int getAppsBypassingDndCount(int i) throws RemoteException;

  AutomaticZenRule getAutomaticZenRule(String str) throws RemoteException;

  byte[] getBackupPayload(int i) throws RemoteException;

  int getBlockedAppCount(int i) throws RemoteException;

  int getBlockedChannelCount(String str, int i) throws RemoteException;

  int getBubblePreferenceForPackage(String str, int i) throws RemoteException;

  NotificationManager.Policy getConsolidatedNotificationPolicy() throws RemoteException;

  NotificationChannel getConversationNotificationChannel(
      String str, int i, String str2, String str3, boolean z, String str4) throws RemoteException;

  ParceledListSlice getConversations(boolean z) throws RemoteException;

  ParceledListSlice getConversationsForPackage(String str, int i) throws RemoteException;

  ComponentName getDefaultNotificationAssistant() throws RemoteException;

  int getDeletedChannelCount(String str, int i) throws RemoteException;

  int getEdgeLightingState() throws RemoteException;

  ComponentName getEffectsSuppressor() throws RemoteException;

  List<String> getEnabledNotificationListenerPackages() throws RemoteException;

  List<ComponentName> getEnabledNotificationListeners(int i) throws RemoteException;

  int getHintsFromListener(INotificationListener iNotificationListener) throws RemoteException;

  int getHintsFromListenerNoToken() throws RemoteException;

  StatusBarNotification[] getHistoricalNotifications(String str, int i, boolean z)
      throws RemoteException;

  StatusBarNotification[] getHistoricalNotificationsWithAttribution(
      String str, String str2, int i, boolean z) throws RemoteException;

  int getInterruptionFilterFromListener(INotificationListener iNotificationListener)
      throws RemoteException;

  NotificationListenerFilter getListenerFilter(ComponentName componentName, int i)
      throws RemoteException;

  int getLockScreenNotificationVisibilityForPackage(String str, int i) throws RemoteException;

  boolean getNotificationAlertsEnabledForPackage(String str, int i) throws RemoteException;

  NotificationChannel getNotificationChannel(String str, int i, String str2, String str3)
      throws RemoteException;

  NotificationChannel getNotificationChannelForPackage(
      String str, int i, String str2, String str3, boolean z) throws RemoteException;

  NotificationChannelGroup getNotificationChannelGroup(String str, String str2)
      throws RemoteException;

  NotificationChannelGroup getNotificationChannelGroupForPackage(String str, String str2, int i)
      throws RemoteException;

  ParceledListSlice getNotificationChannelGroups(String str) throws RemoteException;

  ParceledListSlice getNotificationChannelGroupsForPackage(String str, int i, boolean z)
      throws RemoteException;

  ParceledListSlice getNotificationChannelGroupsFromPrivilegedListener(
      INotificationListener iNotificationListener, String str, UserHandle userHandle)
      throws RemoteException;

  ParceledListSlice getNotificationChannels(String str, String str2, int i) throws RemoteException;

  ParceledListSlice getNotificationChannelsBypassingDnd(String str, int i) throws RemoteException;

  ParceledListSlice getNotificationChannelsForPackage(String str, int i, boolean z)
      throws RemoteException;

  ParceledListSlice getNotificationChannelsFromPrivilegedListener(
      INotificationListener iNotificationListener, String str, UserHandle userHandle)
      throws RemoteException;

  String getNotificationDelegate(String str) throws RemoteException;

  NotificationHistory getNotificationHistory(String str, String str2) throws RemoteException;

  List<Bundle> getNotificationHistoryDataForPackage(
      String str, String str2, int i, String str3, String str4, int i2) throws RemoteException;

  NotificationHistory getNotificationHistoryForPackage(
      String str, String str2, int i, String str3, String str4, int i2) throws RemoteException;

  NotificationManager.Policy getNotificationPolicy(String str) throws RemoteException;

  int getNotificationSoundStatus(String str) throws RemoteException;

  int getNumNotificationChannelsForPackage(String str, int i, boolean z) throws RemoteException;

  int getPackageImportance(String str) throws RemoteException;

  NotificationChannelGroup getPopulatedNotificationChannelGroupForPackage(
      String str, int i, String str2, boolean z) throws RemoteException;

  boolean getPrivateNotificationsAllowed() throws RemoteException;

  int getRuleInstanceCount(ComponentName componentName) throws RemoteException;

  ParceledListSlice getSnoozedNotificationsFromListener(
      INotificationListener iNotificationListener, int i) throws RemoteException;

  List<String> getWearableAppList(int i) throws RemoteException;

  int getZenMode() throws RemoteException;

  ZenModeConfig getZenModeConfig() throws RemoteException;

  List<ZenModeConfig.ZenRule> getZenRules() throws RemoteException;

  boolean hasEnabledNotificationListener(String str, int i) throws RemoteException;

  boolean hasSentValidBubble(String str, int i) throws RemoteException;

  boolean hasSentValidMsg(String str, int i) throws RemoteException;

  boolean hasUserDemotedInvalidMsgApp(String str, int i) throws RemoteException;

  boolean isAlertsAllowed(String str, int i, String str2, int i2) throws RemoteException;

  boolean isAllowNotificationPopUpForPackage(String str, int i) throws RemoteException;

  boolean isEdgeLightingAllowed(String str, int i) throws RemoteException;

  boolean isEdgeLightingNotificationAllowed(String str) throws RemoteException;

  boolean isImportanceLocked(String str, int i) throws RemoteException;

  boolean isInCall(String str, int i) throws RemoteException;

  boolean isInInvalidMsgState(String str, int i) throws RemoteException;

  boolean isNotificationAssistantAccessGranted(ComponentName componentName) throws RemoteException;

  boolean isNotificationListenerAccessGranted(ComponentName componentName) throws RemoteException;

  boolean isNotificationListenerAccessGrantedForUser(ComponentName componentName, int i)
      throws RemoteException;

  boolean isNotificationPolicyAccessGranted(String str) throws RemoteException;

  boolean isNotificationPolicyAccessGrantedForPackage(String str) throws RemoteException;

  int isNotificationTurnedOff(String str, int i) throws RemoteException;

  boolean isPackageEnabled(String str, int i) throws RemoteException;

  boolean isPackagePaused(String str) throws RemoteException;

  boolean isPermissionFixed(String str, int i) throws RemoteException;

  boolean isReminderEnabled(String str, int i) throws RemoteException;

  boolean isSubDisplayNotificationAllowed(String str, int i) throws RemoteException;

  boolean isSystemConditionProviderEnabled(String str) throws RemoteException;

  boolean matchesCallFilter(Bundle bundle) throws RemoteException;

  void migrateNotificationFilter(
      INotificationListener iNotificationListener, int i, List<String> list) throws RemoteException;

  void notifyConditions(String str, IConditionProvider iConditionProvider, Condition[] conditionArr)
      throws RemoteException;

  boolean onlyHasDefaultChannel(String str, int i) throws RemoteException;

  long pullStats(long j, int i, boolean z, List<ParcelFileDescriptor> list) throws RemoteException;

  void registerEdgeLightingListener(IBinder iBinder, ComponentName componentName)
      throws RemoteException;

  void registerListener(
      INotificationListener iNotificationListener, ComponentName componentName, int i)
      throws RemoteException;

  void registerNotificationListener(ComponentName componentName, int i, boolean z)
      throws RemoteException;

  boolean removeAutomaticZenRule(String str) throws RemoteException;

  boolean removeAutomaticZenRules(String str) throws RemoteException;

  void removeEdgeNotification(String str, int i, Bundle bundle, int i2) throws RemoteException;

  boolean removeWearableAppFromList(int i, String str) throws RemoteException;

  void requestBindListener(ComponentName componentName) throws RemoteException;

  void requestBindProvider(ComponentName componentName) throws RemoteException;

  void requestHintsFromListener(INotificationListener iNotificationListener, int i)
      throws RemoteException;

  void requestInterruptionFilterFromListener(INotificationListener iNotificationListener, int i)
      throws RemoteException;

  boolean requestListenerHintsForWearable(int i) throws RemoteException;

  void requestUnbindListener(INotificationListener iNotificationListener) throws RemoteException;

  void requestUnbindListenerComponent(ComponentName componentName) throws RemoteException;

  void requestUnbindProvider(IConditionProvider iConditionProvider) throws RemoteException;

  void resetDefaultAllowEdgeLighting() throws RemoteException;

  void setAllowEdgeLighting(String str, int i, boolean z) throws RemoteException;

  void setAllowNotificationPopUpForPackage(String str, int i, boolean z) throws RemoteException;

  void setAllowSubDisplayNotification(String str, int i, boolean z) throws RemoteException;

  void setAppBypassDnd(String str, int i, boolean z) throws RemoteException;

  void setAutomaticZenRuleState(String str, Condition condition) throws RemoteException;

  void setBubblesAllowed(String str, int i, int i2) throws RemoteException;

  void setHideSilentStatusIcons(boolean z) throws RemoteException;

  void setInterruptionFilter(String str, int i) throws RemoteException;

  void setInvalidMsgAppDemoted(String str, int i, boolean z) throws RemoteException;

  void setListenerFilter(
      ComponentName componentName, int i, NotificationListenerFilter notificationListenerFilter)
      throws RemoteException;

  void setLockScreenNotificationVisibilityForPackage(String str, int i, int i2)
      throws RemoteException;

  void setNASMigrationDoneAndResetDefault(int i, boolean z) throws RemoteException;

  void setNotificationAlertsEnabledForPackage(String str, int i, boolean z) throws RemoteException;

  void setNotificationAssistantAccessGranted(ComponentName componentName, boolean z)
      throws RemoteException;

  void setNotificationAssistantAccessGrantedForUser(ComponentName componentName, int i, boolean z)
      throws RemoteException;

  void setNotificationDelegate(String str, String str2) throws RemoteException;

  void setNotificationListenerAccessGranted(ComponentName componentName, boolean z, boolean z2)
      throws RemoteException;

  void setNotificationListenerAccessGrantedForUser(
      ComponentName componentName, int i, boolean z, boolean z2) throws RemoteException;

  void setNotificationPolicy(String str, NotificationManager.Policy policy) throws RemoteException;

  void setNotificationPolicyAccessGranted(String str, boolean z) throws RemoteException;

  void setNotificationPolicyAccessGrantedForUser(String str, int i, boolean z)
      throws RemoteException;

  boolean setNotificationTurnOff(String str, int i) throws RemoteException;

  void setNotificationsEnabledForPackage(String str, int i, boolean z) throws RemoteException;

  void setNotificationsEnabledWithImportanceLockForPackage(String str, int i, boolean z)
      throws RemoteException;

  void setNotificationsShownFromListener(
      INotificationListener iNotificationListener, String[] strArr) throws RemoteException;

  void setOnNotificationPostedTrimFromListener(INotificationListener iNotificationListener, int i)
      throws RemoteException;

  void setPrivateNotificationsAllowed(boolean z) throws RemoteException;

  void setReminderEnabled(int i, boolean z, List<String> list) throws RemoteException;

  void setReminderEnabledForPackage(String str, int i, boolean z) throws RemoteException;

  void setRestoreBlockListForSS(List<String> list) throws RemoteException;

  void setShowBadge(String str, int i, boolean z) throws RemoteException;

  void setToastRateLimitingEnabled(boolean z) throws RemoteException;

  boolean setWearableAppList(int i, List<String> list) throws RemoteException;

  void setZenMode(int i, Uri uri, String str) throws RemoteException;

  boolean shouldHideSilentStatusIcons(String str) throws RemoteException;

  void silenceNotificationSound() throws RemoteException;

  void snoozeNotificationUntilContextFromListener(
      INotificationListener iNotificationListener, String str, String str2) throws RemoteException;

  void snoozeNotificationUntilFromListener(
      INotificationListener iNotificationListener, String str, long j) throws RemoteException;

  void startEdgeLighting(String str, SemEdgeLightingInfo semEdgeLightingInfo, IBinder iBinder)
      throws RemoteException;

  void stopEdgeLighting(String str, IBinder iBinder) throws RemoteException;

  void unbindEdgeLightingService(IBinder iBinder, String str) throws RemoteException;

  void unlockAllNotificationChannels() throws RemoteException;

  void unlockNotificationChannel(String str, int i, String str2) throws RemoteException;

  void unregisterEdgeLightingListener(IBinder iBinder, String str) throws RemoteException;

  void unregisterListener(INotificationListener iNotificationListener, int i)
      throws RemoteException;

  void unsnoozeNotificationFromAssistant(INotificationListener iNotificationListener, String str)
      throws RemoteException;

  void unsnoozeNotificationFromSystemListener(
      INotificationListener iNotificationListener, String str) throws RemoteException;

  boolean updateAutomaticZenRule(String str, AutomaticZenRule automaticZenRule)
      throws RemoteException;

  void updateCancelEvent(int i, String str, boolean z) throws RemoteException;

  void updateEdgeLightingPackageList(String str, List<String> list) throws RemoteException;

  void updateEdgeLightingPolicy(String str, EdgeLightingPolicy edgeLightingPolicy)
      throws RemoteException;

  void updateNotificationChannelForPackage(
      String str, int i, NotificationChannel notificationChannel) throws RemoteException;

  void updateNotificationChannelFromPrivilegedListener(
      INotificationListener iNotificationListener,
      String str,
      UserHandle userHandle,
      NotificationChannel notificationChannel)
      throws RemoteException;

  void updateNotificationChannelGroupForPackage(
      String str, int i, NotificationChannelGroup notificationChannelGroup) throws RemoteException;

  void updateNotificationChannelGroupFromPrivilegedListener(
      INotificationListener iNotificationListener,
      String str,
      UserHandle userHandle,
      NotificationChannelGroup notificationChannelGroup)
      throws RemoteException;

  void updateNotificationChannels(String str, ParceledListSlice parceledListSlice)
      throws RemoteException;

  public static class Default implements INotificationManager {
    @Override // android.app.INotificationManager
    public void cancelAllNotifications(String pkg, int userId) throws RemoteException {}

    @Override // android.app.INotificationManager
    public void clearData(String pkg, int uid, boolean fromApp) throws RemoteException {}

    @Override // android.app.INotificationManager
    public void enqueueTextToast(
        String pkg,
        IBinder token,
        CharSequence text,
        int duration,
        boolean isUiContext,
        int displayId,
        ITransientNotificationCallback callback)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public void enqueueToast(
        String pkg,
        IBinder token,
        ITransientNotification callback,
        int duration,
        boolean isUiContext,
        int displayId)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public void cancelToast(String pkg, IBinder token) throws RemoteException {}

    @Override // android.app.INotificationManager
    public void finishToken(String pkg, IBinder token) throws RemoteException {}

    @Override // android.app.INotificationManager
    public void enqueueNotificationWithTag(
        String pkg, String opPkg, String tag, int id, Notification notification, int userId)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public void cancelNotificationWithTag(String pkg, String opPkg, String tag, int id, int userId)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public boolean isInCall(String pkg, int uid) throws RemoteException {
      return false;
    }

    @Override // android.app.INotificationManager
    public void setShowBadge(String pkg, int uid, boolean showBadge) throws RemoteException {}

    @Override // android.app.INotificationManager
    public boolean canShowBadge(String pkg, int uid) throws RemoteException {
      return false;
    }

    @Override // android.app.INotificationManager
    public boolean hasSentValidMsg(String pkg, int uid) throws RemoteException {
      return false;
    }

    @Override // android.app.INotificationManager
    public boolean isInInvalidMsgState(String pkg, int uid) throws RemoteException {
      return false;
    }

    @Override // android.app.INotificationManager
    public boolean hasUserDemotedInvalidMsgApp(String pkg, int uid) throws RemoteException {
      return false;
    }

    @Override // android.app.INotificationManager
    public void setInvalidMsgAppDemoted(String pkg, int uid, boolean isDemoted)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public boolean hasSentValidBubble(String pkg, int uid) throws RemoteException {
      return false;
    }

    @Override // android.app.INotificationManager
    public void setNotificationsEnabledForPackage(String pkg, int uid, boolean enabled)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public void setNotificationsEnabledWithImportanceLockForPackage(
        String pkg, int uid, boolean enabled) throws RemoteException {}

    @Override // android.app.INotificationManager
    public boolean areNotificationsEnabledForPackage(String pkg, int uid) throws RemoteException {
      return false;
    }

    @Override // android.app.INotificationManager
    public boolean areNotificationsEnabled(String pkg) throws RemoteException {
      return false;
    }

    @Override // android.app.INotificationManager
    public int getPackageImportance(String pkg) throws RemoteException {
      return 0;
    }

    @Override // android.app.INotificationManager
    public boolean isImportanceLocked(String pkg, int uid) throws RemoteException {
      return false;
    }

    @Override // android.app.INotificationManager
    public List<String> getAllowedAssistantAdjustments(String pkg) throws RemoteException {
      return null;
    }

    @Override // android.app.INotificationManager
    public boolean shouldHideSilentStatusIcons(String callingPkg) throws RemoteException {
      return false;
    }

    @Override // android.app.INotificationManager
    public void setHideSilentStatusIcons(boolean hide) throws RemoteException {}

    @Override // android.app.INotificationManager
    public void setBubblesAllowed(String pkg, int uid, int bubblePreference)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public boolean areBubblesAllowed(String pkg) throws RemoteException {
      return false;
    }

    @Override // android.app.INotificationManager
    public boolean areBubblesEnabled(UserHandle user) throws RemoteException {
      return false;
    }

    @Override // android.app.INotificationManager
    public int getBubblePreferenceForPackage(String pkg, int uid) throws RemoteException {
      return 0;
    }

    @Override // android.app.INotificationManager
    public void createNotificationChannelGroups(String pkg, ParceledListSlice channelGroupList)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public void createNotificationChannels(String pkg, ParceledListSlice channelsList)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public void createNotificationChannelsForPackage(
        String pkg, int uid, ParceledListSlice channelsList) throws RemoteException {}

    @Override // android.app.INotificationManager
    public ParceledListSlice getConversations(boolean onlyImportant) throws RemoteException {
      return null;
    }

    @Override // android.app.INotificationManager
    public ParceledListSlice getConversationsForPackage(String pkg, int uid)
        throws RemoteException {
      return null;
    }

    @Override // android.app.INotificationManager
    public ParceledListSlice getNotificationChannelGroupsForPackage(
        String pkg, int uid, boolean includeDeleted) throws RemoteException {
      return null;
    }

    @Override // android.app.INotificationManager
    public NotificationChannelGroup getNotificationChannelGroupForPackage(
        String groupId, String pkg, int uid) throws RemoteException {
      return null;
    }

    @Override // android.app.INotificationManager
    public NotificationChannelGroup getPopulatedNotificationChannelGroupForPackage(
        String pkg, int uid, String groupId, boolean includeDeleted) throws RemoteException {
      return null;
    }

    @Override // android.app.INotificationManager
    public void updateNotificationChannelGroupForPackage(
        String pkg, int uid, NotificationChannelGroup group) throws RemoteException {}

    @Override // android.app.INotificationManager
    public void updateNotificationChannelForPackage(
        String pkg, int uid, NotificationChannel channel) throws RemoteException {}

    @Override // android.app.INotificationManager
    public void unlockNotificationChannel(String pkg, int uid, String channelId)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public void unlockAllNotificationChannels() throws RemoteException {}

    @Override // android.app.INotificationManager
    public NotificationChannel getNotificationChannel(
        String callingPkg, int userId, String pkg, String channelId) throws RemoteException {
      return null;
    }

    @Override // android.app.INotificationManager
    public NotificationChannel getConversationNotificationChannel(
        String callingPkg,
        int userId,
        String pkg,
        String channelId,
        boolean returnParentIfNoConversationChannel,
        String conversationId)
        throws RemoteException {
      return null;
    }

    @Override // android.app.INotificationManager
    public void createConversationNotificationChannelForPackage(
        String pkg, int uid, NotificationChannel parentChannel, String conversationId)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public NotificationChannel getNotificationChannelForPackage(
        String pkg, int uid, String channelId, String conversationId, boolean includeDeleted)
        throws RemoteException {
      return null;
    }

    @Override // android.app.INotificationManager
    public void deleteNotificationChannel(String pkg, String channelId) throws RemoteException {}

    @Override // android.app.INotificationManager
    public ParceledListSlice getNotificationChannels(
        String callingPkg, String targetPkg, int userId) throws RemoteException {
      return null;
    }

    @Override // android.app.INotificationManager
    public ParceledListSlice getNotificationChannelsForPackage(
        String pkg, int uid, boolean includeDeleted) throws RemoteException {
      return null;
    }

    @Override // android.app.INotificationManager
    public int getNumNotificationChannelsForPackage(String pkg, int uid, boolean includeDeleted)
        throws RemoteException {
      return 0;
    }

    @Override // android.app.INotificationManager
    public int getDeletedChannelCount(String pkg, int uid) throws RemoteException {
      return 0;
    }

    @Override // android.app.INotificationManager
    public int getBlockedChannelCount(String pkg, int uid) throws RemoteException {
      return 0;
    }

    @Override // android.app.INotificationManager
    public void deleteNotificationChannelGroup(String pkg, String channelGroupId)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public NotificationChannelGroup getNotificationChannelGroup(String pkg, String channelGroupId)
        throws RemoteException {
      return null;
    }

    @Override // android.app.INotificationManager
    public ParceledListSlice getNotificationChannelGroups(String pkg) throws RemoteException {
      return null;
    }

    @Override // android.app.INotificationManager
    public boolean onlyHasDefaultChannel(String pkg, int uid) throws RemoteException {
      return false;
    }

    @Override // android.app.INotificationManager
    public boolean areChannelsBypassingDnd() throws RemoteException {
      return false;
    }

    @Override // android.app.INotificationManager
    public ParceledListSlice getNotificationChannelsBypassingDnd(String pkg, int uid)
        throws RemoteException {
      return null;
    }

    @Override // android.app.INotificationManager
    public boolean isPackagePaused(String pkg) throws RemoteException {
      return false;
    }

    @Override // android.app.INotificationManager
    public void deleteNotificationHistoryItem(String pkg, int uid, long postedTime)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public boolean isPermissionFixed(String pkg, int userId) throws RemoteException {
      return false;
    }

    @Override // android.app.INotificationManager
    public void silenceNotificationSound() throws RemoteException {}

    @Override // android.app.INotificationManager
    public StatusBarNotification[] getActiveNotifications(String callingPkg)
        throws RemoteException {
      return null;
    }

    @Override // android.app.INotificationManager
    public StatusBarNotification[] getActiveNotificationsWithAttribution(
        String callingPkg, String callingAttributionTag) throws RemoteException {
      return null;
    }

    @Override // android.app.INotificationManager
    public StatusBarNotification[] getHistoricalNotifications(
        String callingPkg, int count, boolean includeSnoozed) throws RemoteException {
      return null;
    }

    @Override // android.app.INotificationManager
    public StatusBarNotification[] getHistoricalNotificationsWithAttribution(
        String callingPkg, String callingAttributionTag, int count, boolean includeSnoozed)
        throws RemoteException {
      return null;
    }

    @Override // android.app.INotificationManager
    public NotificationHistory getNotificationHistory(
        String callingPkg, String callingAttributionTag) throws RemoteException {
      return null;
    }

    @Override // android.app.INotificationManager
    public void registerListener(
        INotificationListener listener, ComponentName component, int userid)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public void unregisterListener(INotificationListener listener, int userid)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public void cancelNotificationFromListener(
        INotificationListener token, String pkg, String tag, int id) throws RemoteException {}

    @Override // android.app.INotificationManager
    public void cancelNotificationsFromListener(INotificationListener token, String[] keys)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public void snoozeNotificationUntilContextFromListener(
        INotificationListener token, String key, String snoozeCriterionId) throws RemoteException {}

    @Override // android.app.INotificationManager
    public void snoozeNotificationUntilFromListener(
        INotificationListener token, String key, long until) throws RemoteException {}

    @Override // android.app.INotificationManager
    public void requestBindListener(ComponentName component) throws RemoteException {}

    @Override // android.app.INotificationManager
    public void requestUnbindListener(INotificationListener token) throws RemoteException {}

    @Override // android.app.INotificationManager
    public void requestUnbindListenerComponent(ComponentName component) throws RemoteException {}

    @Override // android.app.INotificationManager
    public void requestBindProvider(ComponentName component) throws RemoteException {}

    @Override // android.app.INotificationManager
    public void requestUnbindProvider(IConditionProvider token) throws RemoteException {}

    @Override // android.app.INotificationManager
    public void setNotificationsShownFromListener(INotificationListener token, String[] keys)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public ParceledListSlice getActiveNotificationsFromListener(
        INotificationListener token, String[] keys, int trim) throws RemoteException {
      return null;
    }

    @Override // android.app.INotificationManager
    public ParceledListSlice getSnoozedNotificationsFromListener(
        INotificationListener token, int trim) throws RemoteException {
      return null;
    }

    @Override // android.app.INotificationManager
    public void clearRequestedListenerHints(INotificationListener token) throws RemoteException {}

    @Override // android.app.INotificationManager
    public void requestHintsFromListener(INotificationListener token, int hints)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public int getHintsFromListener(INotificationListener token) throws RemoteException {
      return 0;
    }

    @Override // android.app.INotificationManager
    public int getHintsFromListenerNoToken() throws RemoteException {
      return 0;
    }

    @Override // android.app.INotificationManager
    public void requestInterruptionFilterFromListener(
        INotificationListener token, int interruptionFilter) throws RemoteException {}

    @Override // android.app.INotificationManager
    public int getInterruptionFilterFromListener(INotificationListener token)
        throws RemoteException {
      return 0;
    }

    @Override // android.app.INotificationManager
    public void setOnNotificationPostedTrimFromListener(INotificationListener token, int trim)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public void setInterruptionFilter(String pkg, int interruptionFilter) throws RemoteException {}

    @Override // android.app.INotificationManager
    public void updateNotificationChannelGroupFromPrivilegedListener(
        INotificationListener token, String pkg, UserHandle user, NotificationChannelGroup group)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public void updateNotificationChannelFromPrivilegedListener(
        INotificationListener token, String pkg, UserHandle user, NotificationChannel channel)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public ParceledListSlice getNotificationChannelsFromPrivilegedListener(
        INotificationListener token, String pkg, UserHandle user) throws RemoteException {
      return null;
    }

    @Override // android.app.INotificationManager
    public ParceledListSlice getNotificationChannelGroupsFromPrivilegedListener(
        INotificationListener token, String pkg, UserHandle user) throws RemoteException {
      return null;
    }

    @Override // android.app.INotificationManager
    public void applyEnqueuedAdjustmentFromAssistant(
        INotificationListener token, Adjustment adjustment) throws RemoteException {}

    @Override // android.app.INotificationManager
    public void applyAdjustmentFromAssistant(INotificationListener token, Adjustment adjustment)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public void applyAdjustmentsFromAssistant(
        INotificationListener token, List<Adjustment> adjustments) throws RemoteException {}

    @Override // android.app.INotificationManager
    public void unsnoozeNotificationFromAssistant(INotificationListener token, String key)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public void unsnoozeNotificationFromSystemListener(INotificationListener token, String key)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public ComponentName getEffectsSuppressor() throws RemoteException {
      return null;
    }

    @Override // android.app.INotificationManager
    public boolean matchesCallFilter(Bundle extras) throws RemoteException {
      return false;
    }

    @Override // android.app.INotificationManager
    public void cleanUpCallersAfter(long timeThreshold) throws RemoteException {}

    @Override // android.app.INotificationManager
    public boolean isSystemConditionProviderEnabled(String path) throws RemoteException {
      return false;
    }

    @Override // android.app.INotificationManager
    public boolean isNotificationListenerAccessGranted(ComponentName listener)
        throws RemoteException {
      return false;
    }

    @Override // android.app.INotificationManager
    public boolean isNotificationListenerAccessGrantedForUser(ComponentName listener, int userId)
        throws RemoteException {
      return false;
    }

    @Override // android.app.INotificationManager
    public boolean isNotificationAssistantAccessGranted(ComponentName assistant)
        throws RemoteException {
      return false;
    }

    @Override // android.app.INotificationManager
    public void setNotificationListenerAccessGranted(
        ComponentName listener, boolean enabled, boolean userSet) throws RemoteException {}

    @Override // android.app.INotificationManager
    public void setNotificationAssistantAccessGranted(ComponentName assistant, boolean enabled)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public void setNotificationListenerAccessGrantedForUser(
        ComponentName listener, int userId, boolean enabled, boolean userSet)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public void setNotificationAssistantAccessGrantedForUser(
        ComponentName assistant, int userId, boolean enabled) throws RemoteException {}

    @Override // android.app.INotificationManager
    public List<String> getEnabledNotificationListenerPackages() throws RemoteException {
      return null;
    }

    @Override // android.app.INotificationManager
    public List<ComponentName> getEnabledNotificationListeners(int userId) throws RemoteException {
      return null;
    }

    @Override // android.app.INotificationManager
    public ComponentName getAllowedNotificationAssistantForUser(int userId) throws RemoteException {
      return null;
    }

    @Override // android.app.INotificationManager
    public ComponentName getAllowedNotificationAssistant() throws RemoteException {
      return null;
    }

    @Override // android.app.INotificationManager
    public ComponentName getDefaultNotificationAssistant() throws RemoteException {
      return null;
    }

    @Override // android.app.INotificationManager
    public void setNASMigrationDoneAndResetDefault(int userId, boolean loadFromConfig)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public boolean hasEnabledNotificationListener(String packageName, int userId)
        throws RemoteException {
      return false;
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
    public NotificationManager.Policy getConsolidatedNotificationPolicy() throws RemoteException {
      return null;
    }

    @Override // android.app.INotificationManager
    public void setZenMode(int mode, Uri conditionId, String reason) throws RemoteException {}

    @Override // android.app.INotificationManager
    public void notifyConditions(String pkg, IConditionProvider provider, Condition[] conditions)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public boolean isNotificationPolicyAccessGranted(String pkg) throws RemoteException {
      return false;
    }

    @Override // android.app.INotificationManager
    public NotificationManager.Policy getNotificationPolicy(String pkg) throws RemoteException {
      return null;
    }

    @Override // android.app.INotificationManager
    public void setNotificationPolicy(String pkg, NotificationManager.Policy policy)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public boolean isNotificationPolicyAccessGrantedForPackage(String pkg) throws RemoteException {
      return false;
    }

    @Override // android.app.INotificationManager
    public void setNotificationPolicyAccessGranted(String pkg, boolean granted)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public void setNotificationPolicyAccessGrantedForUser(String pkg, int userId, boolean granted)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public AutomaticZenRule getAutomaticZenRule(String id) throws RemoteException {
      return null;
    }

    @Override // android.app.INotificationManager
    public List<ZenModeConfig.ZenRule> getZenRules() throws RemoteException {
      return null;
    }

    @Override // android.app.INotificationManager
    public String addAutomaticZenRule(AutomaticZenRule automaticZenRule, String pkg)
        throws RemoteException {
      return null;
    }

    @Override // android.app.INotificationManager
    public boolean updateAutomaticZenRule(String id, AutomaticZenRule automaticZenRule)
        throws RemoteException {
      return false;
    }

    @Override // android.app.INotificationManager
    public boolean removeAutomaticZenRule(String id) throws RemoteException {
      return false;
    }

    @Override // android.app.INotificationManager
    public boolean removeAutomaticZenRules(String packageName) throws RemoteException {
      return false;
    }

    @Override // android.app.INotificationManager
    public int getRuleInstanceCount(ComponentName owner) throws RemoteException {
      return 0;
    }

    @Override // android.app.INotificationManager
    public void setAutomaticZenRuleState(String id, Condition condition) throws RemoteException {}

    @Override // android.app.INotificationManager
    public byte[] getBackupPayload(int user) throws RemoteException {
      return null;
    }

    @Override // android.app.INotificationManager
    public void applyRestore(byte[] payload, int user) throws RemoteException {}

    @Override // android.app.INotificationManager
    public ParceledListSlice getAppActiveNotifications(String callingPkg, int userId)
        throws RemoteException {
      return null;
    }

    @Override // android.app.INotificationManager
    public void setNotificationDelegate(String callingPkg, String delegate)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public String getNotificationDelegate(String callingPkg) throws RemoteException {
      return null;
    }

    @Override // android.app.INotificationManager
    public boolean canNotifyAsPackage(String callingPkg, String targetPkg, int userId)
        throws RemoteException {
      return false;
    }

    @Override // android.app.INotificationManager
    public boolean canUseFullScreenIntent(AttributionSource attributionSource)
        throws RemoteException {
      return false;
    }

    @Override // android.app.INotificationManager
    public void setPrivateNotificationsAllowed(boolean allow) throws RemoteException {}

    @Override // android.app.INotificationManager
    public boolean getPrivateNotificationsAllowed() throws RemoteException {
      return false;
    }

    @Override // android.app.INotificationManager
    public long pullStats(long startNs, int report, boolean doAgg, List<ParcelFileDescriptor> stats)
        throws RemoteException {
      return 0L;
    }

    @Override // android.app.INotificationManager
    public NotificationListenerFilter getListenerFilter(ComponentName cn, int userId)
        throws RemoteException {
      return null;
    }

    @Override // android.app.INotificationManager
    public void setListenerFilter(ComponentName cn, int userId, NotificationListenerFilter nlf)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public void migrateNotificationFilter(
        INotificationListener token, int defaultTypes, List<String> disallowedPkgs)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public void setToastRateLimitingEnabled(boolean enable) throws RemoteException {}

    @Override // android.app.INotificationManager
    public void registerNotificationListener(ComponentName listener, int userId, boolean enabled)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public void updateNotificationChannels(String pkg, ParceledListSlice channelsList)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public void bindEdgeLightingService(IBinder binder, int condition, ComponentName component)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public void unbindEdgeLightingService(IBinder binder, String packageName)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public void updateEdgeLightingPackageList(String callingPackage, List<String> list)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public void updateEdgeLightingPolicy(String callingPackage, EdgeLightingPolicy policy)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public void registerEdgeLightingListener(IBinder binder, ComponentName component)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public void unregisterEdgeLightingListener(IBinder binder, String packageName)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public void startEdgeLighting(String packageName, SemEdgeLightingInfo info, IBinder token)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public void stopEdgeLighting(String packageName, IBinder token) throws RemoteException {}

    @Override // android.app.INotificationManager
    public int getEdgeLightingState() throws RemoteException {
      return 0;
    }

    @Override // android.app.INotificationManager
    public boolean isEdgeLightingNotificationAllowed(String packageName) throws RemoteException {
      return false;
    }

    @Override // android.app.INotificationManager
    public void disable(int what, String callingPackage, IBinder binder) throws RemoteException {}

    @Override // android.app.INotificationManager
    public void disableEdgeLightingNotification(String callingPackage, boolean disable)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public boolean isPackageEnabled(String packageName, int userId) throws RemoteException {
      return false;
    }

    @Override // android.app.INotificationManager
    public void cancelNotificationByEdge(String pkg, String tag, int id, int userId, String key)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public void cancelNotificationByGroupKey(
        String pkg, String tag, int id, int userId, String key, String groupKey)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public void enqueueEdgeNotification(String pkg, String opPkg, int id, Bundle extras, int userId)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public void removeEdgeNotification(String pkg, int id, Bundle extras, int userId)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public boolean isEdgeLightingAllowed(String pkg, int uid) throws RemoteException {
      return false;
    }

    @Override // android.app.INotificationManager
    public void setAllowEdgeLighting(String pkg, int uid, boolean allowEdgeLighting)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public void resetDefaultAllowEdgeLighting() throws RemoteException {}

    @Override // android.app.INotificationManager
    public boolean dispatchDelayedWakelockAndBlocked(
        int flags, String tag, String packageName, int uid) throws RemoteException {
      return false;
    }

    @Override // android.app.INotificationManager
    public boolean dispatchDelayedWakeUpAndBlocked(int reason, String details, String packageName)
        throws RemoteException {
      return false;
    }

    @Override // android.app.INotificationManager
    public boolean isSubDisplayNotificationAllowed(String pkg, int uid) throws RemoteException {
      return false;
    }

    @Override // android.app.INotificationManager
    public void setAllowSubDisplayNotification(String pkg, int uid, boolean allowSubDisplayNoti)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public boolean getNotificationAlertsEnabledForPackage(String pkg, int uid)
        throws RemoteException {
      return false;
    }

    @Override // android.app.INotificationManager
    public void setNotificationAlertsEnabledForPackage(String pkg, int uid, boolean enabled)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public boolean setWearableAppList(int userId, List<String> packages) throws RemoteException {
      return false;
    }

    @Override // android.app.INotificationManager
    public boolean addWearableAppToList(int userId, String PackageName) throws RemoteException {
      return false;
    }

    @Override // android.app.INotificationManager
    public boolean removeWearableAppFromList(int userId, String PackageName)
        throws RemoteException {
      return false;
    }

    @Override // android.app.INotificationManager
    public List<String> getWearableAppList(int userId) throws RemoteException {
      return null;
    }

    @Override // android.app.INotificationManager
    public boolean requestListenerHintsForWearable(int state) throws RemoteException {
      return false;
    }

    @Override // android.app.INotificationManager
    public int getLockScreenNotificationVisibilityForPackage(String pkg, int uid)
        throws RemoteException {
      return 0;
    }

    @Override // android.app.INotificationManager
    public void setLockScreenNotificationVisibilityForPackage(
        String pkg, int uid, int lockscreenVisibility) throws RemoteException {}

    @Override // android.app.INotificationManager
    public boolean isAllowNotificationPopUpForPackage(String pkg, int uid) throws RemoteException {
      return false;
    }

    @Override // android.app.INotificationManager
    public void setAllowNotificationPopUpForPackage(String pkg, int uid, boolean allow)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public boolean isAlertsAllowed(String pkg, int uid, String tags, int flags)
        throws RemoteException {
      return false;
    }

    @Override // android.app.INotificationManager
    public boolean isReminderEnabled(String pkg, int uid) throws RemoteException {
      return false;
    }

    @Override // android.app.INotificationManager
    public void setReminderEnabledForPackage(String pkg, int uid, boolean enabled)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public void setReminderEnabled(int userId, boolean enabled, List<String> packages)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public int getBlockedAppCount(int userId) throws RemoteException {
      return 0;
    }

    @Override // android.app.INotificationManager
    public boolean canAppBypassDnd(String pkg, int uid) throws RemoteException {
      return false;
    }

    @Override // android.app.INotificationManager
    public void setAppBypassDnd(String pkg, int uid, boolean allow) throws RemoteException {}

    @Override // android.app.INotificationManager
    public int getAppsBypassingDndCount(int uid) throws RemoteException {
      return 0;
    }

    @Override // android.app.INotificationManager
    public void enqueueTextToastForDex(
        String pkg,
        IBinder token,
        CharSequence text,
        int duration,
        boolean isUiContext,
        int displayId,
        ITransientNotificationCallback callback,
        String message,
        int uid)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public void enqueueToastForDex(
        String pkg,
        IBinder token,
        ITransientNotification callback,
        int duration,
        boolean isUiContext,
        int displayId,
        String message,
        int uid)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public void addReplyHistory(
        int type, String key, String pkg, int userId, String title, String text)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public List<Bundle> getNotificationHistoryDataForPackage(
        String callingPkg,
        String callingAttributionTag,
        int userId,
        String pkg,
        String key,
        int maxNotifications)
        throws RemoteException {
      return null;
    }

    @Override // android.app.INotificationManager
    public NotificationHistory getNotificationHistoryForPackage(
        String callingPkg,
        String callingAttributionTag,
        int userId,
        String pkg,
        String key,
        int maxNotifications)
        throws RemoteException {
      return null;
    }

    @Override // android.app.INotificationManager
    public void updateCancelEvent(int userId, String key, boolean isPackage)
        throws RemoteException {}

    @Override // android.app.INotificationManager
    public void setRestoreBlockListForSS(List<String> list) throws RemoteException {}

    @Override // android.app.INotificationManager
    public int getAllNotificationListenersCount() throws RemoteException {
      return 0;
    }

    @Override // android.app.INotificationManager
    public int isNotificationTurnedOff(String pkg, int uid) throws RemoteException {
      return 0;
    }

    @Override // android.app.INotificationManager
    public int getNotificationSoundStatus(String pkg) throws RemoteException {
      return 0;
    }

    @Override // android.app.INotificationManager
    public boolean setNotificationTurnOff(String pkg, int uid) throws RemoteException {
      return false;
    }

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return null;
    }
  }

  public abstract static class Stub extends Binder implements INotificationManager {
    public static final String DESCRIPTOR = "android.app.INotificationManager";
    static final int TRANSACTION_addAutomaticZenRule = 129;
    static final int TRANSACTION_addReplyHistory = 196;
    static final int TRANSACTION_addWearableAppToList = 178;
    static final int TRANSACTION_applyAdjustmentFromAssistant = 94;
    static final int TRANSACTION_applyAdjustmentsFromAssistant = 95;
    static final int TRANSACTION_applyEnqueuedAdjustmentFromAssistant = 93;
    static final int TRANSACTION_applyRestore = 136;
    static final int TRANSACTION_areBubblesAllowed = 27;
    static final int TRANSACTION_areBubblesEnabled = 28;
    static final int TRANSACTION_areChannelsBypassingDnd = 56;
    static final int TRANSACTION_areNotificationsEnabled = 20;
    static final int TRANSACTION_areNotificationsEnabledForPackage = 19;
    static final int TRANSACTION_bindEdgeLightingService = 151;
    static final int TRANSACTION_canAppBypassDnd = 191;
    static final int TRANSACTION_canNotifyAsPackage = 140;
    static final int TRANSACTION_canShowBadge = 11;
    static final int TRANSACTION_canUseFullScreenIntent = 141;
    static final int TRANSACTION_cancelAllNotifications = 1;
    static final int TRANSACTION_cancelNotificationByEdge = 164;
    static final int TRANSACTION_cancelNotificationByGroupKey = 165;
    static final int TRANSACTION_cancelNotificationFromListener = 69;
    static final int TRANSACTION_cancelNotificationWithTag = 8;
    static final int TRANSACTION_cancelNotificationsFromListener = 70;
    static final int TRANSACTION_cancelToast = 5;
    static final int TRANSACTION_cleanUpCallersAfter = 100;
    static final int TRANSACTION_clearData = 2;
    static final int TRANSACTION_clearRequestedListenerHints = 81;
    static final int TRANSACTION_createConversationNotificationChannelForPackage = 44;
    static final int TRANSACTION_createNotificationChannelGroups = 30;
    static final int TRANSACTION_createNotificationChannels = 31;
    static final int TRANSACTION_createNotificationChannelsForPackage = 32;
    static final int TRANSACTION_deleteNotificationChannel = 46;
    static final int TRANSACTION_deleteNotificationChannelGroup = 52;
    static final int TRANSACTION_deleteNotificationHistoryItem = 59;
    static final int TRANSACTION_disable = 161;
    static final int TRANSACTION_disableEdgeLightingNotification = 162;
    static final int TRANSACTION_dispatchDelayedWakeUpAndBlocked = 172;
    static final int TRANSACTION_dispatchDelayedWakelockAndBlocked = 171;
    static final int TRANSACTION_enqueueEdgeNotification = 166;
    static final int TRANSACTION_enqueueNotificationWithTag = 7;
    static final int TRANSACTION_enqueueTextToast = 3;
    static final int TRANSACTION_enqueueTextToastForDex = 194;
    static final int TRANSACTION_enqueueToast = 4;
    static final int TRANSACTION_enqueueToastForDex = 195;
    static final int TRANSACTION_finishToken = 6;
    static final int TRANSACTION_getActiveNotifications = 62;
    static final int TRANSACTION_getActiveNotificationsFromListener = 79;
    static final int TRANSACTION_getActiveNotificationsWithAttribution = 63;
    static final int TRANSACTION_getAllNotificationListenersCount = 201;
    static final int TRANSACTION_getAllowedAssistantAdjustments = 23;
    static final int TRANSACTION_getAllowedNotificationAssistant = 112;
    static final int TRANSACTION_getAllowedNotificationAssistantForUser = 111;
    static final int TRANSACTION_getAppActiveNotifications = 137;
    static final int TRANSACTION_getAppsBypassingDndCount = 193;
    static final int TRANSACTION_getAutomaticZenRule = 127;
    static final int TRANSACTION_getBackupPayload = 135;
    static final int TRANSACTION_getBlockedAppCount = 190;
    static final int TRANSACTION_getBlockedChannelCount = 51;
    static final int TRANSACTION_getBubblePreferenceForPackage = 29;
    static final int TRANSACTION_getConsolidatedNotificationPolicy = 118;
    static final int TRANSACTION_getConversationNotificationChannel = 43;
    static final int TRANSACTION_getConversations = 33;
    static final int TRANSACTION_getConversationsForPackage = 34;
    static final int TRANSACTION_getDefaultNotificationAssistant = 113;
    static final int TRANSACTION_getDeletedChannelCount = 50;
    static final int TRANSACTION_getEdgeLightingState = 159;
    static final int TRANSACTION_getEffectsSuppressor = 98;
    static final int TRANSACTION_getEnabledNotificationListenerPackages = 109;
    static final int TRANSACTION_getEnabledNotificationListeners = 110;
    static final int TRANSACTION_getHintsFromListener = 83;
    static final int TRANSACTION_getHintsFromListenerNoToken = 84;
    static final int TRANSACTION_getHistoricalNotifications = 64;
    static final int TRANSACTION_getHistoricalNotificationsWithAttribution = 65;
    static final int TRANSACTION_getInterruptionFilterFromListener = 86;
    static final int TRANSACTION_getListenerFilter = 145;
    static final int TRANSACTION_getLockScreenNotificationVisibilityForPackage = 182;
    static final int TRANSACTION_getNotificationAlertsEnabledForPackage = 175;
    static final int TRANSACTION_getNotificationChannel = 42;
    static final int TRANSACTION_getNotificationChannelForPackage = 45;
    static final int TRANSACTION_getNotificationChannelGroup = 53;
    static final int TRANSACTION_getNotificationChannelGroupForPackage = 36;
    static final int TRANSACTION_getNotificationChannelGroups = 54;
    static final int TRANSACTION_getNotificationChannelGroupsForPackage = 35;
    static final int TRANSACTION_getNotificationChannelGroupsFromPrivilegedListener = 92;
    static final int TRANSACTION_getNotificationChannels = 47;
    static final int TRANSACTION_getNotificationChannelsBypassingDnd = 57;
    static final int TRANSACTION_getNotificationChannelsForPackage = 48;
    static final int TRANSACTION_getNotificationChannelsFromPrivilegedListener = 91;
    static final int TRANSACTION_getNotificationDelegate = 139;
    static final int TRANSACTION_getNotificationHistory = 66;
    static final int TRANSACTION_getNotificationHistoryDataForPackage = 197;
    static final int TRANSACTION_getNotificationHistoryForPackage = 198;
    static final int TRANSACTION_getNotificationPolicy = 122;
    static final int TRANSACTION_getNotificationSoundStatus = 203;
    static final int TRANSACTION_getNumNotificationChannelsForPackage = 49;
    static final int TRANSACTION_getPackageImportance = 21;
    static final int TRANSACTION_getPopulatedNotificationChannelGroupForPackage = 37;
    static final int TRANSACTION_getPrivateNotificationsAllowed = 143;
    static final int TRANSACTION_getRuleInstanceCount = 133;
    static final int TRANSACTION_getSnoozedNotificationsFromListener = 80;
    static final int TRANSACTION_getWearableAppList = 180;
    static final int TRANSACTION_getZenMode = 116;
    static final int TRANSACTION_getZenModeConfig = 117;
    static final int TRANSACTION_getZenRules = 128;
    static final int TRANSACTION_hasEnabledNotificationListener = 115;
    static final int TRANSACTION_hasSentValidBubble = 16;
    static final int TRANSACTION_hasSentValidMsg = 12;
    static final int TRANSACTION_hasUserDemotedInvalidMsgApp = 14;
    static final int TRANSACTION_isAlertsAllowed = 186;
    static final int TRANSACTION_isAllowNotificationPopUpForPackage = 184;
    static final int TRANSACTION_isEdgeLightingAllowed = 168;
    static final int TRANSACTION_isEdgeLightingNotificationAllowed = 160;
    static final int TRANSACTION_isImportanceLocked = 22;
    static final int TRANSACTION_isInCall = 9;
    static final int TRANSACTION_isInInvalidMsgState = 13;
    static final int TRANSACTION_isNotificationAssistantAccessGranted = 104;
    static final int TRANSACTION_isNotificationListenerAccessGranted = 102;
    static final int TRANSACTION_isNotificationListenerAccessGrantedForUser = 103;
    static final int TRANSACTION_isNotificationPolicyAccessGranted = 121;
    static final int TRANSACTION_isNotificationPolicyAccessGrantedForPackage = 124;
    static final int TRANSACTION_isNotificationTurnedOff = 202;
    static final int TRANSACTION_isPackageEnabled = 163;
    static final int TRANSACTION_isPackagePaused = 58;
    static final int TRANSACTION_isPermissionFixed = 60;
    static final int TRANSACTION_isReminderEnabled = 187;
    static final int TRANSACTION_isSubDisplayNotificationAllowed = 173;
    static final int TRANSACTION_isSystemConditionProviderEnabled = 101;
    static final int TRANSACTION_matchesCallFilter = 99;
    static final int TRANSACTION_migrateNotificationFilter = 147;
    static final int TRANSACTION_notifyConditions = 120;
    static final int TRANSACTION_onlyHasDefaultChannel = 55;
    static final int TRANSACTION_pullStats = 144;
    static final int TRANSACTION_registerEdgeLightingListener = 155;
    static final int TRANSACTION_registerListener = 67;
    static final int TRANSACTION_registerNotificationListener = 149;
    static final int TRANSACTION_removeAutomaticZenRule = 131;
    static final int TRANSACTION_removeAutomaticZenRules = 132;
    static final int TRANSACTION_removeEdgeNotification = 167;
    static final int TRANSACTION_removeWearableAppFromList = 179;
    static final int TRANSACTION_requestBindListener = 73;
    static final int TRANSACTION_requestBindProvider = 76;
    static final int TRANSACTION_requestHintsFromListener = 82;
    static final int TRANSACTION_requestInterruptionFilterFromListener = 85;
    static final int TRANSACTION_requestListenerHintsForWearable = 181;
    static final int TRANSACTION_requestUnbindListener = 74;
    static final int TRANSACTION_requestUnbindListenerComponent = 75;
    static final int TRANSACTION_requestUnbindProvider = 77;
    static final int TRANSACTION_resetDefaultAllowEdgeLighting = 170;
    static final int TRANSACTION_setAllowEdgeLighting = 169;
    static final int TRANSACTION_setAllowNotificationPopUpForPackage = 185;
    static final int TRANSACTION_setAllowSubDisplayNotification = 174;
    static final int TRANSACTION_setAppBypassDnd = 192;
    static final int TRANSACTION_setAutomaticZenRuleState = 134;
    static final int TRANSACTION_setBubblesAllowed = 26;
    static final int TRANSACTION_setHideSilentStatusIcons = 25;
    static final int TRANSACTION_setInterruptionFilter = 88;
    static final int TRANSACTION_setInvalidMsgAppDemoted = 15;
    static final int TRANSACTION_setListenerFilter = 146;
    static final int TRANSACTION_setLockScreenNotificationVisibilityForPackage = 183;
    static final int TRANSACTION_setNASMigrationDoneAndResetDefault = 114;
    static final int TRANSACTION_setNotificationAlertsEnabledForPackage = 176;
    static final int TRANSACTION_setNotificationAssistantAccessGranted = 106;
    static final int TRANSACTION_setNotificationAssistantAccessGrantedForUser = 108;
    static final int TRANSACTION_setNotificationDelegate = 138;
    static final int TRANSACTION_setNotificationListenerAccessGranted = 105;
    static final int TRANSACTION_setNotificationListenerAccessGrantedForUser = 107;
    static final int TRANSACTION_setNotificationPolicy = 123;
    static final int TRANSACTION_setNotificationPolicyAccessGranted = 125;
    static final int TRANSACTION_setNotificationPolicyAccessGrantedForUser = 126;
    static final int TRANSACTION_setNotificationTurnOff = 204;
    static final int TRANSACTION_setNotificationsEnabledForPackage = 17;
    static final int TRANSACTION_setNotificationsEnabledWithImportanceLockForPackage = 18;
    static final int TRANSACTION_setNotificationsShownFromListener = 78;
    static final int TRANSACTION_setOnNotificationPostedTrimFromListener = 87;
    static final int TRANSACTION_setPrivateNotificationsAllowed = 142;
    static final int TRANSACTION_setReminderEnabled = 189;
    static final int TRANSACTION_setReminderEnabledForPackage = 188;
    static final int TRANSACTION_setRestoreBlockListForSS = 200;
    static final int TRANSACTION_setShowBadge = 10;
    static final int TRANSACTION_setToastRateLimitingEnabled = 148;
    static final int TRANSACTION_setWearableAppList = 177;
    static final int TRANSACTION_setZenMode = 119;
    static final int TRANSACTION_shouldHideSilentStatusIcons = 24;
    static final int TRANSACTION_silenceNotificationSound = 61;
    static final int TRANSACTION_snoozeNotificationUntilContextFromListener = 71;
    static final int TRANSACTION_snoozeNotificationUntilFromListener = 72;
    static final int TRANSACTION_startEdgeLighting = 157;
    static final int TRANSACTION_stopEdgeLighting = 158;
    static final int TRANSACTION_unbindEdgeLightingService = 152;
    static final int TRANSACTION_unlockAllNotificationChannels = 41;
    static final int TRANSACTION_unlockNotificationChannel = 40;
    static final int TRANSACTION_unregisterEdgeLightingListener = 156;
    static final int TRANSACTION_unregisterListener = 68;
    static final int TRANSACTION_unsnoozeNotificationFromAssistant = 96;
    static final int TRANSACTION_unsnoozeNotificationFromSystemListener = 97;
    static final int TRANSACTION_updateAutomaticZenRule = 130;
    static final int TRANSACTION_updateCancelEvent = 199;
    static final int TRANSACTION_updateEdgeLightingPackageList = 153;
    static final int TRANSACTION_updateEdgeLightingPolicy = 154;
    static final int TRANSACTION_updateNotificationChannelForPackage = 39;
    static final int TRANSACTION_updateNotificationChannelFromPrivilegedListener = 90;
    static final int TRANSACTION_updateNotificationChannelGroupForPackage = 38;
    static final int TRANSACTION_updateNotificationChannelGroupFromPrivilegedListener = 89;
    static final int TRANSACTION_updateNotificationChannels = 150;
    private final PermissionEnforcer mEnforcer;

    public Stub(PermissionEnforcer enforcer) {
      attachInterface(this, DESCRIPTOR);
      if (enforcer == null) {
        throw new IllegalArgumentException("enforcer cannot be null");
      }
      this.mEnforcer = enforcer;
    }

    @Deprecated
    public Stub() {
      this(
          PermissionEnforcer.fromContext(
              ActivityThread.currentActivityThread().getSystemContext()));
    }

    public static INotificationManager asInterface(IBinder obj) {
      if (obj == null) {
        return null;
      }
      IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
      if (iin != null && (iin instanceof INotificationManager)) {
        return (INotificationManager) iin;
      }
      return new Proxy(obj);
    }

    @Override // android.p009os.IInterface
    public IBinder asBinder() {
      return this;
    }

    public static String getDefaultTransactionName(int transactionCode) {
      switch (transactionCode) {
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
          return "shouldHideSilentStatusIcons";
        case 25:
          return "setHideSilentStatusIcons";
        case 26:
          return "setBubblesAllowed";
        case 27:
          return "areBubblesAllowed";
        case 28:
          return "areBubblesEnabled";
        case 29:
          return "getBubblePreferenceForPackage";
        case 30:
          return "createNotificationChannelGroups";
        case 31:
          return "createNotificationChannels";
        case 32:
          return "createNotificationChannelsForPackage";
        case 33:
          return "getConversations";
        case 34:
          return "getConversationsForPackage";
        case 35:
          return "getNotificationChannelGroupsForPackage";
        case 36:
          return "getNotificationChannelGroupForPackage";
        case 37:
          return "getPopulatedNotificationChannelGroupForPackage";
        case 38:
          return "updateNotificationChannelGroupForPackage";
        case 39:
          return "updateNotificationChannelForPackage";
        case 40:
          return "unlockNotificationChannel";
        case 41:
          return "unlockAllNotificationChannels";
        case 42:
          return "getNotificationChannel";
        case 43:
          return "getConversationNotificationChannel";
        case 44:
          return "createConversationNotificationChannelForPackage";
        case 45:
          return "getNotificationChannelForPackage";
        case 46:
          return "deleteNotificationChannel";
        case 47:
          return "getNotificationChannels";
        case 48:
          return "getNotificationChannelsForPackage";
        case 49:
          return "getNumNotificationChannelsForPackage";
        case 50:
          return "getDeletedChannelCount";
        case 51:
          return "getBlockedChannelCount";
        case 52:
          return "deleteNotificationChannelGroup";
        case 53:
          return "getNotificationChannelGroup";
        case 54:
          return "getNotificationChannelGroups";
        case 55:
          return "onlyHasDefaultChannel";
        case 56:
          return ZenModeDiff.ConfigDiff.FIELD_ARE_CHANNELS_BYPASSING_DND;
        case 57:
          return "getNotificationChannelsBypassingDnd";
        case 58:
          return "isPackagePaused";
        case 59:
          return "deleteNotificationHistoryItem";
        case 60:
          return "isPermissionFixed";
        case 61:
          return "silenceNotificationSound";
        case 62:
          return "getActiveNotifications";
        case 63:
          return "getActiveNotificationsWithAttribution";
        case 64:
          return "getHistoricalNotifications";
        case 65:
          return "getHistoricalNotificationsWithAttribution";
        case 66:
          return "getNotificationHistory";
        case 67:
          return "registerListener";
        case 68:
          return "unregisterListener";
        case 69:
          return "cancelNotificationFromListener";
        case 70:
          return "cancelNotificationsFromListener";
        case 71:
          return "snoozeNotificationUntilContextFromListener";
        case 72:
          return "snoozeNotificationUntilFromListener";
        case 73:
          return "requestBindListener";
        case 74:
          return "requestUnbindListener";
        case 75:
          return "requestUnbindListenerComponent";
        case 76:
          return "requestBindProvider";
        case 77:
          return "requestUnbindProvider";
        case 78:
          return "setNotificationsShownFromListener";
        case 79:
          return "getActiveNotificationsFromListener";
        case 80:
          return "getSnoozedNotificationsFromListener";
        case 81:
          return "clearRequestedListenerHints";
        case 82:
          return "requestHintsFromListener";
        case 83:
          return "getHintsFromListener";
        case 84:
          return "getHintsFromListenerNoToken";
        case 85:
          return "requestInterruptionFilterFromListener";
        case 86:
          return "getInterruptionFilterFromListener";
        case 87:
          return "setOnNotificationPostedTrimFromListener";
        case 88:
          return "setInterruptionFilter";
        case 89:
          return "updateNotificationChannelGroupFromPrivilegedListener";
        case 90:
          return "updateNotificationChannelFromPrivilegedListener";
        case 91:
          return "getNotificationChannelsFromPrivilegedListener";
        case 92:
          return "getNotificationChannelGroupsFromPrivilegedListener";
        case 93:
          return "applyEnqueuedAdjustmentFromAssistant";
        case 94:
          return "applyAdjustmentFromAssistant";
        case 95:
          return "applyAdjustmentsFromAssistant";
        case 96:
          return "unsnoozeNotificationFromAssistant";
        case 97:
          return "unsnoozeNotificationFromSystemListener";
        case 98:
          return "getEffectsSuppressor";
        case 99:
          return "matchesCallFilter";
        case 100:
          return "cleanUpCallersAfter";
        case 101:
          return "isSystemConditionProviderEnabled";
        case 102:
          return "isNotificationListenerAccessGranted";
        case 103:
          return "isNotificationListenerAccessGrantedForUser";
        case 104:
          return "isNotificationAssistantAccessGranted";
        case 105:
          return "setNotificationListenerAccessGranted";
        case 106:
          return "setNotificationAssistantAccessGranted";
        case 107:
          return "setNotificationListenerAccessGrantedForUser";
        case 108:
          return "setNotificationAssistantAccessGrantedForUser";
        case 109:
          return "getEnabledNotificationListenerPackages";
        case 110:
          return "getEnabledNotificationListeners";
        case 111:
          return "getAllowedNotificationAssistantForUser";
        case 112:
          return "getAllowedNotificationAssistant";
        case 113:
          return "getDefaultNotificationAssistant";
        case 114:
          return "setNASMigrationDoneAndResetDefault";
        case 115:
          return "hasEnabledNotificationListener";
        case 116:
          return "getZenMode";
        case 117:
          return "getZenModeConfig";
        case 118:
          return "getConsolidatedNotificationPolicy";
        case 119:
          return "setZenMode";
        case 120:
          return "notifyConditions";
        case 121:
          return "isNotificationPolicyAccessGranted";
        case 122:
          return "getNotificationPolicy";
        case 123:
          return "setNotificationPolicy";
        case 124:
          return "isNotificationPolicyAccessGrantedForPackage";
        case 125:
          return "setNotificationPolicyAccessGranted";
        case 126:
          return "setNotificationPolicyAccessGrantedForUser";
        case 127:
          return "getAutomaticZenRule";
        case 128:
          return "getZenRules";
        case 129:
          return "addAutomaticZenRule";
        case 130:
          return "updateAutomaticZenRule";
        case 131:
          return "removeAutomaticZenRule";
        case 132:
          return "removeAutomaticZenRules";
        case 133:
          return "getRuleInstanceCount";
        case 134:
          return "setAutomaticZenRuleState";
        case 135:
          return "getBackupPayload";
        case 136:
          return "applyRestore";
        case 137:
          return "getAppActiveNotifications";
        case 138:
          return "setNotificationDelegate";
        case 139:
          return "getNotificationDelegate";
        case 140:
          return "canNotifyAsPackage";
        case 141:
          return "canUseFullScreenIntent";
        case 142:
          return "setPrivateNotificationsAllowed";
        case 143:
          return "getPrivateNotificationsAllowed";
        case 144:
          return "pullStats";
        case 145:
          return "getListenerFilter";
        case 146:
          return "setListenerFilter";
        case 147:
          return "migrateNotificationFilter";
        case 148:
          return "setToastRateLimitingEnabled";
        case 149:
          return "registerNotificationListener";
        case 150:
          return "updateNotificationChannels";
        case 151:
          return "bindEdgeLightingService";
        case 152:
          return "unbindEdgeLightingService";
        case 153:
          return "updateEdgeLightingPackageList";
        case 154:
          return "updateEdgeLightingPolicy";
        case 155:
          return "registerEdgeLightingListener";
        case 156:
          return "unregisterEdgeLightingListener";
        case 157:
          return "startEdgeLighting";
        case 158:
          return "stopEdgeLighting";
        case 159:
          return "getEdgeLightingState";
        case 160:
          return "isEdgeLightingNotificationAllowed";
        case 161:
          return SemWifiDisplayParameter.VALUE_DISABLE;
        case 162:
          return "disableEdgeLightingNotification";
        case 163:
          return "isPackageEnabled";
        case 164:
          return "cancelNotificationByEdge";
        case 165:
          return "cancelNotificationByGroupKey";
        case 166:
          return "enqueueEdgeNotification";
        case 167:
          return "removeEdgeNotification";
        case 168:
          return SecContentProviderURI.KIOSKMODE_EDGELIGHTINGALLOWED_METHOD;
        case 169:
          return "setAllowEdgeLighting";
        case 170:
          return "resetDefaultAllowEdgeLighting";
        case 171:
          return "dispatchDelayedWakelockAndBlocked";
        case 172:
          return "dispatchDelayedWakeUpAndBlocked";
        case 173:
          return "isSubDisplayNotificationAllowed";
        case 174:
          return "setAllowSubDisplayNotification";
        case 175:
          return "getNotificationAlertsEnabledForPackage";
        case 176:
          return "setNotificationAlertsEnabledForPackage";
        case 177:
          return "setWearableAppList";
        case 178:
          return "addWearableAppToList";
        case 179:
          return "removeWearableAppFromList";
        case 180:
          return "getWearableAppList";
        case 181:
          return "requestListenerHintsForWearable";
        case 182:
          return "getLockScreenNotificationVisibilityForPackage";
        case 183:
          return "setLockScreenNotificationVisibilityForPackage";
        case 184:
          return "isAllowNotificationPopUpForPackage";
        case 185:
          return "setAllowNotificationPopUpForPackage";
        case 186:
          return "isAlertsAllowed";
        case 187:
          return "isReminderEnabled";
        case 188:
          return "setReminderEnabledForPackage";
        case 189:
          return "setReminderEnabled";
        case 190:
          return "getBlockedAppCount";
        case 191:
          return "canAppBypassDnd";
        case 192:
          return "setAppBypassDnd";
        case 193:
          return "getAppsBypassingDndCount";
        case 194:
          return "enqueueTextToastForDex";
        case 195:
          return "enqueueToastForDex";
        case 196:
          return "addReplyHistory";
        case 197:
          return "getNotificationHistoryDataForPackage";
        case 198:
          return "getNotificationHistoryForPackage";
        case 199:
          return "updateCancelEvent";
        case 200:
          return "setRestoreBlockListForSS";
        case 201:
          return "getAllNotificationListenersCount";
        case 202:
          return "isNotificationTurnedOff";
        case 203:
          return "getNotificationSoundStatus";
        case 204:
          return "setNotificationTurnOff";
        default:
          return null;
      }
    }

    @Override // android.p009os.Binder
    public String getTransactionName(int transactionCode) {
      return getDefaultTransactionName(transactionCode);
    }

    @Override // android.p009os.Binder
    public boolean onTransact(int code, Parcel data, Parcel reply, int flags)
        throws RemoteException {
      if (code >= 1 && code <= 16777215) {
        data.enforceInterface(DESCRIPTOR);
      }
      switch (code) {
        case IBinder.INTERFACE_TRANSACTION /* 1598968902 */:
          reply.writeString(DESCRIPTOR);
          return true;
        default:
          switch (code) {
            case 1:
              String _arg0 = data.readString();
              int _arg1 = data.readInt();
              data.enforceNoDataAvail();
              cancelAllNotifications(_arg0, _arg1);
              reply.writeNoException();
              return true;
            case 2:
              String _arg02 = data.readString();
              int _arg12 = data.readInt();
              boolean _arg2 = data.readBoolean();
              data.enforceNoDataAvail();
              clearData(_arg02, _arg12, _arg2);
              reply.writeNoException();
              return true;
            case 3:
              String _arg03 = data.readString();
              IBinder _arg13 = data.readStrongBinder();
              CharSequence _arg22 =
                  (CharSequence) data.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
              int _arg3 = data.readInt();
              boolean _arg4 = data.readBoolean();
              int _arg5 = data.readInt();
              ITransientNotificationCallback _arg6 =
                  ITransientNotificationCallback.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              enqueueTextToast(_arg03, _arg13, _arg22, _arg3, _arg4, _arg5, _arg6);
              reply.writeNoException();
              return true;
            case 4:
              String _arg04 = data.readString();
              IBinder _arg14 = data.readStrongBinder();
              ITransientNotification _arg23 =
                  ITransientNotification.Stub.asInterface(data.readStrongBinder());
              int _arg32 = data.readInt();
              boolean _arg42 = data.readBoolean();
              int _arg52 = data.readInt();
              data.enforceNoDataAvail();
              enqueueToast(_arg04, _arg14, _arg23, _arg32, _arg42, _arg52);
              reply.writeNoException();
              return true;
            case 5:
              String _arg05 = data.readString();
              IBinder _arg15 = data.readStrongBinder();
              data.enforceNoDataAvail();
              cancelToast(_arg05, _arg15);
              reply.writeNoException();
              return true;
            case 6:
              String _arg06 = data.readString();
              IBinder _arg16 = data.readStrongBinder();
              data.enforceNoDataAvail();
              finishToken(_arg06, _arg16);
              reply.writeNoException();
              return true;
            case 7:
              String _arg07 = data.readString();
              String _arg17 = data.readString();
              String _arg24 = data.readString();
              int _arg33 = data.readInt();
              Notification _arg43 = (Notification) data.readTypedObject(Notification.CREATOR);
              int _arg53 = data.readInt();
              data.enforceNoDataAvail();
              enqueueNotificationWithTag(_arg07, _arg17, _arg24, _arg33, _arg43, _arg53);
              reply.writeNoException();
              return true;
            case 8:
              String _arg08 = data.readString();
              String _arg18 = data.readString();
              String _arg25 = data.readString();
              int _arg34 = data.readInt();
              int _arg44 = data.readInt();
              data.enforceNoDataAvail();
              cancelNotificationWithTag(_arg08, _arg18, _arg25, _arg34, _arg44);
              reply.writeNoException();
              return true;
            case 9:
              String _arg09 = data.readString();
              int _arg19 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result = isInCall(_arg09, _arg19);
              reply.writeNoException();
              reply.writeBoolean(_result);
              return true;
            case 10:
              String _arg010 = data.readString();
              int _arg110 = data.readInt();
              boolean _arg26 = data.readBoolean();
              data.enforceNoDataAvail();
              setShowBadge(_arg010, _arg110, _arg26);
              reply.writeNoException();
              return true;
            case 11:
              String _arg011 = data.readString();
              int _arg111 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result2 = canShowBadge(_arg011, _arg111);
              reply.writeNoException();
              reply.writeBoolean(_result2);
              return true;
            case 12:
              String _arg012 = data.readString();
              int _arg112 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result3 = hasSentValidMsg(_arg012, _arg112);
              reply.writeNoException();
              reply.writeBoolean(_result3);
              return true;
            case 13:
              String _arg013 = data.readString();
              int _arg113 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result4 = isInInvalidMsgState(_arg013, _arg113);
              reply.writeNoException();
              reply.writeBoolean(_result4);
              return true;
            case 14:
              String _arg014 = data.readString();
              int _arg114 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result5 = hasUserDemotedInvalidMsgApp(_arg014, _arg114);
              reply.writeNoException();
              reply.writeBoolean(_result5);
              return true;
            case 15:
              String _arg015 = data.readString();
              int _arg115 = data.readInt();
              boolean _arg27 = data.readBoolean();
              data.enforceNoDataAvail();
              setInvalidMsgAppDemoted(_arg015, _arg115, _arg27);
              reply.writeNoException();
              return true;
            case 16:
              String _arg016 = data.readString();
              int _arg116 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result6 = hasSentValidBubble(_arg016, _arg116);
              reply.writeNoException();
              reply.writeBoolean(_result6);
              return true;
            case 17:
              String _arg017 = data.readString();
              int _arg117 = data.readInt();
              boolean _arg28 = data.readBoolean();
              data.enforceNoDataAvail();
              setNotificationsEnabledForPackage(_arg017, _arg117, _arg28);
              reply.writeNoException();
              return true;
            case 18:
              String _arg018 = data.readString();
              int _arg118 = data.readInt();
              boolean _arg29 = data.readBoolean();
              data.enforceNoDataAvail();
              setNotificationsEnabledWithImportanceLockForPackage(_arg018, _arg118, _arg29);
              reply.writeNoException();
              return true;
            case 19:
              String _arg019 = data.readString();
              int _arg119 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result7 = areNotificationsEnabledForPackage(_arg019, _arg119);
              reply.writeNoException();
              reply.writeBoolean(_result7);
              return true;
            case 20:
              String _arg020 = data.readString();
              data.enforceNoDataAvail();
              boolean _result8 = areNotificationsEnabled(_arg020);
              reply.writeNoException();
              reply.writeBoolean(_result8);
              return true;
            case 21:
              String _arg021 = data.readString();
              data.enforceNoDataAvail();
              int _result9 = getPackageImportance(_arg021);
              reply.writeNoException();
              reply.writeInt(_result9);
              return true;
            case 22:
              String _arg022 = data.readString();
              int _arg120 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result10 = isImportanceLocked(_arg022, _arg120);
              reply.writeNoException();
              reply.writeBoolean(_result10);
              return true;
            case 23:
              String _arg023 = data.readString();
              data.enforceNoDataAvail();
              List<String> _result11 = getAllowedAssistantAdjustments(_arg023);
              reply.writeNoException();
              reply.writeStringList(_result11);
              return true;
            case 24:
              String _arg024 = data.readString();
              data.enforceNoDataAvail();
              boolean _result12 = shouldHideSilentStatusIcons(_arg024);
              reply.writeNoException();
              reply.writeBoolean(_result12);
              return true;
            case 25:
              boolean _arg025 = data.readBoolean();
              data.enforceNoDataAvail();
              setHideSilentStatusIcons(_arg025);
              reply.writeNoException();
              return true;
            case 26:
              String _arg026 = data.readString();
              int _arg121 = data.readInt();
              int _arg210 = data.readInt();
              data.enforceNoDataAvail();
              setBubblesAllowed(_arg026, _arg121, _arg210);
              reply.writeNoException();
              return true;
            case 27:
              String _arg027 = data.readString();
              data.enforceNoDataAvail();
              boolean _result13 = areBubblesAllowed(_arg027);
              reply.writeNoException();
              reply.writeBoolean(_result13);
              return true;
            case 28:
              UserHandle _arg028 = (UserHandle) data.readTypedObject(UserHandle.CREATOR);
              data.enforceNoDataAvail();
              boolean _result14 = areBubblesEnabled(_arg028);
              reply.writeNoException();
              reply.writeBoolean(_result14);
              return true;
            case 29:
              String _arg029 = data.readString();
              int _arg122 = data.readInt();
              data.enforceNoDataAvail();
              int _result15 = getBubblePreferenceForPackage(_arg029, _arg122);
              reply.writeNoException();
              reply.writeInt(_result15);
              return true;
            case 30:
              String _arg030 = data.readString();
              ParceledListSlice _arg123 =
                  (ParceledListSlice) data.readTypedObject(ParceledListSlice.CREATOR);
              data.enforceNoDataAvail();
              createNotificationChannelGroups(_arg030, _arg123);
              reply.writeNoException();
              return true;
            case 31:
              String _arg031 = data.readString();
              ParceledListSlice _arg124 =
                  (ParceledListSlice) data.readTypedObject(ParceledListSlice.CREATOR);
              data.enforceNoDataAvail();
              createNotificationChannels(_arg031, _arg124);
              reply.writeNoException();
              return true;
            case 32:
              String _arg032 = data.readString();
              int _arg125 = data.readInt();
              ParceledListSlice _arg211 =
                  (ParceledListSlice) data.readTypedObject(ParceledListSlice.CREATOR);
              data.enforceNoDataAvail();
              createNotificationChannelsForPackage(_arg032, _arg125, _arg211);
              reply.writeNoException();
              return true;
            case 33:
              boolean _arg033 = data.readBoolean();
              data.enforceNoDataAvail();
              ParceledListSlice _result16 = getConversations(_arg033);
              reply.writeNoException();
              reply.writeTypedObject(_result16, 1);
              return true;
            case 34:
              String _arg034 = data.readString();
              int _arg126 = data.readInt();
              data.enforceNoDataAvail();
              ParceledListSlice _result17 = getConversationsForPackage(_arg034, _arg126);
              reply.writeNoException();
              reply.writeTypedObject(_result17, 1);
              return true;
            case 35:
              String _arg035 = data.readString();
              int _arg127 = data.readInt();
              boolean _arg212 = data.readBoolean();
              data.enforceNoDataAvail();
              ParceledListSlice _result18 =
                  getNotificationChannelGroupsForPackage(_arg035, _arg127, _arg212);
              reply.writeNoException();
              reply.writeTypedObject(_result18, 1);
              return true;
            case 36:
              String _arg036 = data.readString();
              String _arg128 = data.readString();
              int _arg213 = data.readInt();
              data.enforceNoDataAvail();
              NotificationChannelGroup _result19 =
                  getNotificationChannelGroupForPackage(_arg036, _arg128, _arg213);
              reply.writeNoException();
              reply.writeTypedObject(_result19, 1);
              return true;
            case 37:
              String _arg037 = data.readString();
              int _arg129 = data.readInt();
              String _arg214 = data.readString();
              boolean _arg35 = data.readBoolean();
              data.enforceNoDataAvail();
              NotificationChannelGroup _result20 =
                  getPopulatedNotificationChannelGroupForPackage(_arg037, _arg129, _arg214, _arg35);
              reply.writeNoException();
              reply.writeTypedObject(_result20, 1);
              return true;
            case 38:
              String _arg038 = data.readString();
              int _arg130 = data.readInt();
              NotificationChannelGroup _arg215 =
                  (NotificationChannelGroup) data.readTypedObject(NotificationChannelGroup.CREATOR);
              data.enforceNoDataAvail();
              updateNotificationChannelGroupForPackage(_arg038, _arg130, _arg215);
              reply.writeNoException();
              return true;
            case 39:
              String _arg039 = data.readString();
              int _arg131 = data.readInt();
              NotificationChannel _arg216 =
                  (NotificationChannel) data.readTypedObject(NotificationChannel.CREATOR);
              data.enforceNoDataAvail();
              updateNotificationChannelForPackage(_arg039, _arg131, _arg216);
              reply.writeNoException();
              return true;
            case 40:
              String _arg040 = data.readString();
              int _arg132 = data.readInt();
              String _arg217 = data.readString();
              data.enforceNoDataAvail();
              unlockNotificationChannel(_arg040, _arg132, _arg217);
              reply.writeNoException();
              return true;
            case 41:
              unlockAllNotificationChannels();
              reply.writeNoException();
              return true;
            case 42:
              String _arg041 = data.readString();
              int _arg133 = data.readInt();
              String _arg218 = data.readString();
              String _arg36 = data.readString();
              data.enforceNoDataAvail();
              NotificationChannel _result21 =
                  getNotificationChannel(_arg041, _arg133, _arg218, _arg36);
              reply.writeNoException();
              reply.writeTypedObject(_result21, 1);
              return true;
            case 43:
              String _arg042 = data.readString();
              int _arg134 = data.readInt();
              String _arg219 = data.readString();
              String _arg37 = data.readString();
              boolean _arg45 = data.readBoolean();
              String _arg54 = data.readString();
              data.enforceNoDataAvail();
              NotificationChannel _result22 =
                  getConversationNotificationChannel(
                      _arg042, _arg134, _arg219, _arg37, _arg45, _arg54);
              reply.writeNoException();
              reply.writeTypedObject(_result22, 1);
              return true;
            case 44:
              String _arg043 = data.readString();
              int _arg135 = data.readInt();
              NotificationChannel _arg220 =
                  (NotificationChannel) data.readTypedObject(NotificationChannel.CREATOR);
              String _arg38 = data.readString();
              data.enforceNoDataAvail();
              createConversationNotificationChannelForPackage(_arg043, _arg135, _arg220, _arg38);
              reply.writeNoException();
              return true;
            case 45:
              String _arg044 = data.readString();
              int _arg136 = data.readInt();
              String _arg221 = data.readString();
              String _arg39 = data.readString();
              boolean _arg46 = data.readBoolean();
              data.enforceNoDataAvail();
              NotificationChannel _result23 =
                  getNotificationChannelForPackage(_arg044, _arg136, _arg221, _arg39, _arg46);
              reply.writeNoException();
              reply.writeTypedObject(_result23, 1);
              return true;
            case 46:
              String _arg045 = data.readString();
              String _arg137 = data.readString();
              data.enforceNoDataAvail();
              deleteNotificationChannel(_arg045, _arg137);
              reply.writeNoException();
              return true;
            case 47:
              String _arg046 = data.readString();
              String _arg138 = data.readString();
              int _arg222 = data.readInt();
              data.enforceNoDataAvail();
              ParceledListSlice _result24 = getNotificationChannels(_arg046, _arg138, _arg222);
              reply.writeNoException();
              reply.writeTypedObject(_result24, 1);
              return true;
            case 48:
              String _arg047 = data.readString();
              int _arg139 = data.readInt();
              boolean _arg223 = data.readBoolean();
              data.enforceNoDataAvail();
              ParceledListSlice _result25 =
                  getNotificationChannelsForPackage(_arg047, _arg139, _arg223);
              reply.writeNoException();
              reply.writeTypedObject(_result25, 1);
              return true;
            case 49:
              String _arg048 = data.readString();
              int _arg140 = data.readInt();
              boolean _arg224 = data.readBoolean();
              data.enforceNoDataAvail();
              int _result26 = getNumNotificationChannelsForPackage(_arg048, _arg140, _arg224);
              reply.writeNoException();
              reply.writeInt(_result26);
              return true;
            case 50:
              String _arg049 = data.readString();
              int _arg141 = data.readInt();
              data.enforceNoDataAvail();
              int _result27 = getDeletedChannelCount(_arg049, _arg141);
              reply.writeNoException();
              reply.writeInt(_result27);
              return true;
            case 51:
              String _arg050 = data.readString();
              int _arg142 = data.readInt();
              data.enforceNoDataAvail();
              int _result28 = getBlockedChannelCount(_arg050, _arg142);
              reply.writeNoException();
              reply.writeInt(_result28);
              return true;
            case 52:
              String _arg051 = data.readString();
              String _arg143 = data.readString();
              data.enforceNoDataAvail();
              deleteNotificationChannelGroup(_arg051, _arg143);
              reply.writeNoException();
              return true;
            case 53:
              String _arg052 = data.readString();
              String _arg144 = data.readString();
              data.enforceNoDataAvail();
              NotificationChannelGroup _result29 = getNotificationChannelGroup(_arg052, _arg144);
              reply.writeNoException();
              reply.writeTypedObject(_result29, 1);
              return true;
            case 54:
              String _arg053 = data.readString();
              data.enforceNoDataAvail();
              ParceledListSlice _result30 = getNotificationChannelGroups(_arg053);
              reply.writeNoException();
              reply.writeTypedObject(_result30, 1);
              return true;
            case 55:
              String _arg054 = data.readString();
              int _arg145 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result31 = onlyHasDefaultChannel(_arg054, _arg145);
              reply.writeNoException();
              reply.writeBoolean(_result31);
              return true;
            case 56:
              boolean _result32 = areChannelsBypassingDnd();
              reply.writeNoException();
              reply.writeBoolean(_result32);
              return true;
            case 57:
              String _arg055 = data.readString();
              int _arg146 = data.readInt();
              data.enforceNoDataAvail();
              ParceledListSlice _result33 = getNotificationChannelsBypassingDnd(_arg055, _arg146);
              reply.writeNoException();
              reply.writeTypedObject(_result33, 1);
              return true;
            case 58:
              String _arg056 = data.readString();
              data.enforceNoDataAvail();
              boolean _result34 = isPackagePaused(_arg056);
              reply.writeNoException();
              reply.writeBoolean(_result34);
              return true;
            case 59:
              String _arg057 = data.readString();
              int _arg147 = data.readInt();
              long _arg225 = data.readLong();
              data.enforceNoDataAvail();
              deleteNotificationHistoryItem(_arg057, _arg147, _arg225);
              reply.writeNoException();
              return true;
            case 60:
              String _arg058 = data.readString();
              int _arg148 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result35 = isPermissionFixed(_arg058, _arg148);
              reply.writeNoException();
              reply.writeBoolean(_result35);
              return true;
            case 61:
              silenceNotificationSound();
              reply.writeNoException();
              return true;
            case 62:
              String _arg059 = data.readString();
              data.enforceNoDataAvail();
              StatusBarNotification[] _result36 = getActiveNotifications(_arg059);
              reply.writeNoException();
              reply.writeTypedArray(_result36, 1);
              return true;
            case 63:
              String _arg060 = data.readString();
              String _arg149 = data.readString();
              data.enforceNoDataAvail();
              StatusBarNotification[] _result37 =
                  getActiveNotificationsWithAttribution(_arg060, _arg149);
              reply.writeNoException();
              reply.writeTypedArray(_result37, 1);
              return true;
            case 64:
              String _arg061 = data.readString();
              int _arg150 = data.readInt();
              boolean _arg226 = data.readBoolean();
              data.enforceNoDataAvail();
              StatusBarNotification[] _result38 =
                  getHistoricalNotifications(_arg061, _arg150, _arg226);
              reply.writeNoException();
              reply.writeTypedArray(_result38, 1);
              return true;
            case 65:
              String _arg062 = data.readString();
              String _arg151 = data.readString();
              int _arg227 = data.readInt();
              boolean _arg310 = data.readBoolean();
              data.enforceNoDataAvail();
              StatusBarNotification[] _result39 =
                  getHistoricalNotificationsWithAttribution(_arg062, _arg151, _arg227, _arg310);
              reply.writeNoException();
              reply.writeTypedArray(_result39, 1);
              return true;
            case 66:
              String _arg063 = data.readString();
              String _arg152 = data.readString();
              data.enforceNoDataAvail();
              NotificationHistory _result40 = getNotificationHistory(_arg063, _arg152);
              reply.writeNoException();
              reply.writeTypedObject(_result40, 1);
              return true;
            case 67:
              INotificationListener _arg064 =
                  INotificationListener.Stub.asInterface(data.readStrongBinder());
              ComponentName _arg153 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
              int _arg228 = data.readInt();
              data.enforceNoDataAvail();
              registerListener(_arg064, _arg153, _arg228);
              reply.writeNoException();
              return true;
            case 68:
              INotificationListener _arg065 =
                  INotificationListener.Stub.asInterface(data.readStrongBinder());
              int _arg154 = data.readInt();
              data.enforceNoDataAvail();
              unregisterListener(_arg065, _arg154);
              reply.writeNoException();
              return true;
            case 69:
              INotificationListener _arg066 =
                  INotificationListener.Stub.asInterface(data.readStrongBinder());
              String _arg155 = data.readString();
              String _arg229 = data.readString();
              int _arg311 = data.readInt();
              data.enforceNoDataAvail();
              cancelNotificationFromListener(_arg066, _arg155, _arg229, _arg311);
              reply.writeNoException();
              return true;
            case 70:
              INotificationListener _arg067 =
                  INotificationListener.Stub.asInterface(data.readStrongBinder());
              String[] _arg156 = data.createStringArray();
              data.enforceNoDataAvail();
              cancelNotificationsFromListener(_arg067, _arg156);
              reply.writeNoException();
              return true;
            case 71:
              INotificationListener _arg068 =
                  INotificationListener.Stub.asInterface(data.readStrongBinder());
              String _arg157 = data.readString();
              String _arg230 = data.readString();
              data.enforceNoDataAvail();
              snoozeNotificationUntilContextFromListener(_arg068, _arg157, _arg230);
              reply.writeNoException();
              return true;
            case 72:
              INotificationListener _arg069 =
                  INotificationListener.Stub.asInterface(data.readStrongBinder());
              String _arg158 = data.readString();
              long _arg231 = data.readLong();
              data.enforceNoDataAvail();
              snoozeNotificationUntilFromListener(_arg069, _arg158, _arg231);
              reply.writeNoException();
              return true;
            case 73:
              ComponentName _arg070 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
              data.enforceNoDataAvail();
              requestBindListener(_arg070);
              reply.writeNoException();
              return true;
            case 74:
              INotificationListener _arg071 =
                  INotificationListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              requestUnbindListener(_arg071);
              reply.writeNoException();
              return true;
            case 75:
              ComponentName _arg072 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
              data.enforceNoDataAvail();
              requestUnbindListenerComponent(_arg072);
              reply.writeNoException();
              return true;
            case 76:
              ComponentName _arg073 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
              data.enforceNoDataAvail();
              requestBindProvider(_arg073);
              reply.writeNoException();
              return true;
            case 77:
              IConditionProvider _arg074 =
                  IConditionProvider.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              requestUnbindProvider(_arg074);
              reply.writeNoException();
              return true;
            case 78:
              INotificationListener _arg075 =
                  INotificationListener.Stub.asInterface(data.readStrongBinder());
              String[] _arg159 = data.createStringArray();
              data.enforceNoDataAvail();
              setNotificationsShownFromListener(_arg075, _arg159);
              reply.writeNoException();
              return true;
            case 79:
              INotificationListener _arg076 =
                  INotificationListener.Stub.asInterface(data.readStrongBinder());
              String[] _arg160 = data.createStringArray();
              int _arg232 = data.readInt();
              data.enforceNoDataAvail();
              ParceledListSlice _result41 =
                  getActiveNotificationsFromListener(_arg076, _arg160, _arg232);
              reply.writeNoException();
              reply.writeTypedObject(_result41, 1);
              return true;
            case 80:
              INotificationListener _arg077 =
                  INotificationListener.Stub.asInterface(data.readStrongBinder());
              int _arg161 = data.readInt();
              data.enforceNoDataAvail();
              ParceledListSlice _result42 = getSnoozedNotificationsFromListener(_arg077, _arg161);
              reply.writeNoException();
              reply.writeTypedObject(_result42, 1);
              return true;
            case 81:
              INotificationListener _arg078 =
                  INotificationListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              clearRequestedListenerHints(_arg078);
              reply.writeNoException();
              return true;
            case 82:
              INotificationListener _arg079 =
                  INotificationListener.Stub.asInterface(data.readStrongBinder());
              int _arg162 = data.readInt();
              data.enforceNoDataAvail();
              requestHintsFromListener(_arg079, _arg162);
              reply.writeNoException();
              return true;
            case 83:
              INotificationListener _arg080 =
                  INotificationListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              int _result43 = getHintsFromListener(_arg080);
              reply.writeNoException();
              reply.writeInt(_result43);
              return true;
            case 84:
              int _result44 = getHintsFromListenerNoToken();
              reply.writeNoException();
              reply.writeInt(_result44);
              return true;
            case 85:
              INotificationListener _arg081 =
                  INotificationListener.Stub.asInterface(data.readStrongBinder());
              int _arg163 = data.readInt();
              data.enforceNoDataAvail();
              requestInterruptionFilterFromListener(_arg081, _arg163);
              reply.writeNoException();
              return true;
            case 86:
              INotificationListener _arg082 =
                  INotificationListener.Stub.asInterface(data.readStrongBinder());
              data.enforceNoDataAvail();
              int _result45 = getInterruptionFilterFromListener(_arg082);
              reply.writeNoException();
              reply.writeInt(_result45);
              return true;
            case 87:
              INotificationListener _arg083 =
                  INotificationListener.Stub.asInterface(data.readStrongBinder());
              int _arg164 = data.readInt();
              data.enforceNoDataAvail();
              setOnNotificationPostedTrimFromListener(_arg083, _arg164);
              reply.writeNoException();
              return true;
            case 88:
              String _arg084 = data.readString();
              int _arg165 = data.readInt();
              data.enforceNoDataAvail();
              setInterruptionFilter(_arg084, _arg165);
              reply.writeNoException();
              return true;
            case 89:
              INotificationListener _arg085 =
                  INotificationListener.Stub.asInterface(data.readStrongBinder());
              String _arg166 = data.readString();
              UserHandle _arg233 = (UserHandle) data.readTypedObject(UserHandle.CREATOR);
              NotificationChannelGroup _arg312 =
                  (NotificationChannelGroup) data.readTypedObject(NotificationChannelGroup.CREATOR);
              data.enforceNoDataAvail();
              updateNotificationChannelGroupFromPrivilegedListener(
                  _arg085, _arg166, _arg233, _arg312);
              reply.writeNoException();
              return true;
            case 90:
              INotificationListener _arg086 =
                  INotificationListener.Stub.asInterface(data.readStrongBinder());
              String _arg167 = data.readString();
              UserHandle _arg234 = (UserHandle) data.readTypedObject(UserHandle.CREATOR);
              NotificationChannel _arg313 =
                  (NotificationChannel) data.readTypedObject(NotificationChannel.CREATOR);
              data.enforceNoDataAvail();
              updateNotificationChannelFromPrivilegedListener(_arg086, _arg167, _arg234, _arg313);
              reply.writeNoException();
              return true;
            case 91:
              INotificationListener _arg087 =
                  INotificationListener.Stub.asInterface(data.readStrongBinder());
              String _arg168 = data.readString();
              UserHandle _arg235 = (UserHandle) data.readTypedObject(UserHandle.CREATOR);
              data.enforceNoDataAvail();
              ParceledListSlice _result46 =
                  getNotificationChannelsFromPrivilegedListener(_arg087, _arg168, _arg235);
              reply.writeNoException();
              reply.writeTypedObject(_result46, 1);
              return true;
            case 92:
              INotificationListener _arg088 =
                  INotificationListener.Stub.asInterface(data.readStrongBinder());
              String _arg169 = data.readString();
              UserHandle _arg236 = (UserHandle) data.readTypedObject(UserHandle.CREATOR);
              data.enforceNoDataAvail();
              ParceledListSlice _result47 =
                  getNotificationChannelGroupsFromPrivilegedListener(_arg088, _arg169, _arg236);
              reply.writeNoException();
              reply.writeTypedObject(_result47, 1);
              return true;
            case 93:
              INotificationListener _arg089 =
                  INotificationListener.Stub.asInterface(data.readStrongBinder());
              Adjustment _arg170 = (Adjustment) data.readTypedObject(Adjustment.CREATOR);
              data.enforceNoDataAvail();
              applyEnqueuedAdjustmentFromAssistant(_arg089, _arg170);
              reply.writeNoException();
              return true;
            case 94:
              INotificationListener _arg090 =
                  INotificationListener.Stub.asInterface(data.readStrongBinder());
              Adjustment _arg171 = (Adjustment) data.readTypedObject(Adjustment.CREATOR);
              data.enforceNoDataAvail();
              applyAdjustmentFromAssistant(_arg090, _arg171);
              reply.writeNoException();
              return true;
            case 95:
              INotificationListener _arg091 =
                  INotificationListener.Stub.asInterface(data.readStrongBinder());
              List<Adjustment> _arg172 = data.createTypedArrayList(Adjustment.CREATOR);
              data.enforceNoDataAvail();
              applyAdjustmentsFromAssistant(_arg091, _arg172);
              reply.writeNoException();
              return true;
            case 96:
              INotificationListener _arg092 =
                  INotificationListener.Stub.asInterface(data.readStrongBinder());
              String _arg173 = data.readString();
              data.enforceNoDataAvail();
              unsnoozeNotificationFromAssistant(_arg092, _arg173);
              reply.writeNoException();
              return true;
            case 97:
              INotificationListener _arg093 =
                  INotificationListener.Stub.asInterface(data.readStrongBinder());
              String _arg174 = data.readString();
              data.enforceNoDataAvail();
              unsnoozeNotificationFromSystemListener(_arg093, _arg174);
              reply.writeNoException();
              return true;
            case 98:
              ComponentName _result48 = getEffectsSuppressor();
              reply.writeNoException();
              reply.writeTypedObject(_result48, 1);
              return true;
            case 99:
              Bundle _arg094 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              data.enforceNoDataAvail();
              boolean _result49 = matchesCallFilter(_arg094);
              reply.writeNoException();
              reply.writeBoolean(_result49);
              return true;
            case 100:
              long _arg095 = data.readLong();
              data.enforceNoDataAvail();
              cleanUpCallersAfter(_arg095);
              reply.writeNoException();
              return true;
            case 101:
              String _arg096 = data.readString();
              data.enforceNoDataAvail();
              boolean _result50 = isSystemConditionProviderEnabled(_arg096);
              reply.writeNoException();
              reply.writeBoolean(_result50);
              return true;
            case 102:
              ComponentName _arg097 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
              data.enforceNoDataAvail();
              boolean _result51 = isNotificationListenerAccessGranted(_arg097);
              reply.writeNoException();
              reply.writeBoolean(_result51);
              return true;
            case 103:
              ComponentName _arg098 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
              int _arg175 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result52 = isNotificationListenerAccessGrantedForUser(_arg098, _arg175);
              reply.writeNoException();
              reply.writeBoolean(_result52);
              return true;
            case 104:
              ComponentName _arg099 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
              data.enforceNoDataAvail();
              boolean _result53 = isNotificationAssistantAccessGranted(_arg099);
              reply.writeNoException();
              reply.writeBoolean(_result53);
              return true;
            case 105:
              ComponentName _arg0100 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
              boolean _arg176 = data.readBoolean();
              boolean _arg237 = data.readBoolean();
              data.enforceNoDataAvail();
              setNotificationListenerAccessGranted(_arg0100, _arg176, _arg237);
              reply.writeNoException();
              return true;
            case 106:
              ComponentName _arg0101 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
              boolean _arg177 = data.readBoolean();
              data.enforceNoDataAvail();
              setNotificationAssistantAccessGranted(_arg0101, _arg177);
              reply.writeNoException();
              return true;
            case 107:
              ComponentName _arg0102 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
              int _arg178 = data.readInt();
              boolean _arg238 = data.readBoolean();
              boolean _arg314 = data.readBoolean();
              data.enforceNoDataAvail();
              setNotificationListenerAccessGrantedForUser(_arg0102, _arg178, _arg238, _arg314);
              reply.writeNoException();
              return true;
            case 108:
              ComponentName _arg0103 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
              int _arg179 = data.readInt();
              boolean _arg239 = data.readBoolean();
              data.enforceNoDataAvail();
              setNotificationAssistantAccessGrantedForUser(_arg0103, _arg179, _arg239);
              reply.writeNoException();
              return true;
            case 109:
              List<String> _result54 = getEnabledNotificationListenerPackages();
              reply.writeNoException();
              reply.writeStringList(_result54);
              return true;
            case 110:
              int _arg0104 = data.readInt();
              data.enforceNoDataAvail();
              List<ComponentName> _result55 = getEnabledNotificationListeners(_arg0104);
              reply.writeNoException();
              reply.writeTypedList(_result55, 1);
              return true;
            case 111:
              int _arg0105 = data.readInt();
              data.enforceNoDataAvail();
              ComponentName _result56 = getAllowedNotificationAssistantForUser(_arg0105);
              reply.writeNoException();
              reply.writeTypedObject(_result56, 1);
              return true;
            case 112:
              ComponentName _result57 = getAllowedNotificationAssistant();
              reply.writeNoException();
              reply.writeTypedObject(_result57, 1);
              return true;
            case 113:
              ComponentName _result58 = getDefaultNotificationAssistant();
              reply.writeNoException();
              reply.writeTypedObject(_result58, 1);
              return true;
            case 114:
              int _arg0106 = data.readInt();
              boolean _arg180 = data.readBoolean();
              data.enforceNoDataAvail();
              setNASMigrationDoneAndResetDefault(_arg0106, _arg180);
              reply.writeNoException();
              return true;
            case 115:
              String _arg0107 = data.readString();
              int _arg181 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result59 = hasEnabledNotificationListener(_arg0107, _arg181);
              reply.writeNoException();
              reply.writeBoolean(_result59);
              return true;
            case 116:
              int _result60 = getZenMode();
              reply.writeNoException();
              reply.writeInt(_result60);
              return true;
            case 117:
              ZenModeConfig _result61 = getZenModeConfig();
              reply.writeNoException();
              reply.writeTypedObject(_result61, 1);
              return true;
            case 118:
              NotificationManager.Policy _result62 = getConsolidatedNotificationPolicy();
              reply.writeNoException();
              reply.writeTypedObject(_result62, 1);
              return true;
            case 119:
              int _arg0108 = data.readInt();
              Uri _arg182 = (Uri) data.readTypedObject(Uri.CREATOR);
              String _arg240 = data.readString();
              data.enforceNoDataAvail();
              setZenMode(_arg0108, _arg182, _arg240);
              return true;
            case 120:
              String _arg0109 = data.readString();
              IConditionProvider _arg183 =
                  IConditionProvider.Stub.asInterface(data.readStrongBinder());
              Condition[] _arg241 = (Condition[]) data.createTypedArray(Condition.CREATOR);
              data.enforceNoDataAvail();
              notifyConditions(_arg0109, _arg183, _arg241);
              return true;
            case 121:
              String _arg0110 = data.readString();
              data.enforceNoDataAvail();
              boolean _result63 = isNotificationPolicyAccessGranted(_arg0110);
              reply.writeNoException();
              reply.writeBoolean(_result63);
              return true;
            case 122:
              String _arg0111 = data.readString();
              data.enforceNoDataAvail();
              NotificationManager.Policy _result64 = getNotificationPolicy(_arg0111);
              reply.writeNoException();
              reply.writeTypedObject(_result64, 1);
              return true;
            case 123:
              String _arg0112 = data.readString();
              NotificationManager.Policy _arg184 =
                  (NotificationManager.Policy)
                      data.readTypedObject(NotificationManager.Policy.CREATOR);
              data.enforceNoDataAvail();
              setNotificationPolicy(_arg0112, _arg184);
              reply.writeNoException();
              return true;
            case 124:
              String _arg0113 = data.readString();
              data.enforceNoDataAvail();
              boolean _result65 = isNotificationPolicyAccessGrantedForPackage(_arg0113);
              reply.writeNoException();
              reply.writeBoolean(_result65);
              return true;
            case 125:
              String _arg0114 = data.readString();
              boolean _arg185 = data.readBoolean();
              data.enforceNoDataAvail();
              setNotificationPolicyAccessGranted(_arg0114, _arg185);
              reply.writeNoException();
              return true;
            case 126:
              String _arg0115 = data.readString();
              int _arg186 = data.readInt();
              boolean _arg242 = data.readBoolean();
              data.enforceNoDataAvail();
              setNotificationPolicyAccessGrantedForUser(_arg0115, _arg186, _arg242);
              reply.writeNoException();
              return true;
            case 127:
              String _arg0116 = data.readString();
              data.enforceNoDataAvail();
              AutomaticZenRule _result66 = getAutomaticZenRule(_arg0116);
              reply.writeNoException();
              reply.writeTypedObject(_result66, 1);
              return true;
            case 128:
              List<ZenModeConfig.ZenRule> _result67 = getZenRules();
              reply.writeNoException();
              reply.writeTypedList(_result67, 1);
              return true;
            case 129:
              AutomaticZenRule _arg0117 =
                  (AutomaticZenRule) data.readTypedObject(AutomaticZenRule.CREATOR);
              String _arg187 = data.readString();
              data.enforceNoDataAvail();
              String _result68 = addAutomaticZenRule(_arg0117, _arg187);
              reply.writeNoException();
              reply.writeString(_result68);
              return true;
            case 130:
              String _arg0118 = data.readString();
              AutomaticZenRule _arg188 =
                  (AutomaticZenRule) data.readTypedObject(AutomaticZenRule.CREATOR);
              data.enforceNoDataAvail();
              boolean _result69 = updateAutomaticZenRule(_arg0118, _arg188);
              reply.writeNoException();
              reply.writeBoolean(_result69);
              return true;
            case 131:
              String _arg0119 = data.readString();
              data.enforceNoDataAvail();
              boolean _result70 = removeAutomaticZenRule(_arg0119);
              reply.writeNoException();
              reply.writeBoolean(_result70);
              return true;
            case 132:
              String _arg0120 = data.readString();
              data.enforceNoDataAvail();
              boolean _result71 = removeAutomaticZenRules(_arg0120);
              reply.writeNoException();
              reply.writeBoolean(_result71);
              return true;
            case 133:
              ComponentName _arg0121 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
              data.enforceNoDataAvail();
              int _result72 = getRuleInstanceCount(_arg0121);
              reply.writeNoException();
              reply.writeInt(_result72);
              return true;
            case 134:
              String _arg0122 = data.readString();
              Condition _arg189 = (Condition) data.readTypedObject(Condition.CREATOR);
              data.enforceNoDataAvail();
              setAutomaticZenRuleState(_arg0122, _arg189);
              reply.writeNoException();
              return true;
            case 135:
              int _arg0123 = data.readInt();
              data.enforceNoDataAvail();
              byte[] _result73 = getBackupPayload(_arg0123);
              reply.writeNoException();
              reply.writeByteArray(_result73);
              return true;
            case 136:
              byte[] _arg0124 = data.createByteArray();
              int _arg190 = data.readInt();
              data.enforceNoDataAvail();
              applyRestore(_arg0124, _arg190);
              reply.writeNoException();
              return true;
            case 137:
              String _arg0125 = data.readString();
              int _arg191 = data.readInt();
              data.enforceNoDataAvail();
              ParceledListSlice _result74 = getAppActiveNotifications(_arg0125, _arg191);
              reply.writeNoException();
              reply.writeTypedObject(_result74, 1);
              return true;
            case 138:
              String _arg0126 = data.readString();
              String _arg192 = data.readString();
              data.enforceNoDataAvail();
              setNotificationDelegate(_arg0126, _arg192);
              reply.writeNoException();
              return true;
            case 139:
              String _arg0127 = data.readString();
              data.enforceNoDataAvail();
              String _result75 = getNotificationDelegate(_arg0127);
              reply.writeNoException();
              reply.writeString(_result75);
              return true;
            case 140:
              String _arg0128 = data.readString();
              String _arg193 = data.readString();
              int _arg243 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result76 = canNotifyAsPackage(_arg0128, _arg193, _arg243);
              reply.writeNoException();
              reply.writeBoolean(_result76);
              return true;
            case 141:
              AttributionSource _arg0129 =
                  (AttributionSource) data.readTypedObject(AttributionSource.CREATOR);
              data.enforceNoDataAvail();
              boolean _result77 = canUseFullScreenIntent(_arg0129);
              reply.writeNoException();
              reply.writeBoolean(_result77);
              return true;
            case 142:
              boolean _arg0130 = data.readBoolean();
              data.enforceNoDataAvail();
              setPrivateNotificationsAllowed(_arg0130);
              reply.writeNoException();
              return true;
            case 143:
              boolean _result78 = getPrivateNotificationsAllowed();
              reply.writeNoException();
              reply.writeBoolean(_result78);
              return true;
            case 144:
              long _arg0131 = data.readLong();
              int _arg194 = data.readInt();
              boolean _arg244 = data.readBoolean();
              ArrayList arrayList = new ArrayList();
              data.enforceNoDataAvail();
              long _result79 = pullStats(_arg0131, _arg194, _arg244, arrayList);
              reply.writeNoException();
              reply.writeLong(_result79);
              reply.writeTypedList(arrayList, 1);
              return true;
            case 145:
              ComponentName _arg0132 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
              int _arg195 = data.readInt();
              data.enforceNoDataAvail();
              NotificationListenerFilter _result80 = getListenerFilter(_arg0132, _arg195);
              reply.writeNoException();
              reply.writeTypedObject(_result80, 1);
              return true;
            case 146:
              ComponentName _arg0133 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
              int _arg196 = data.readInt();
              NotificationListenerFilter _arg245 =
                  (NotificationListenerFilter)
                      data.readTypedObject(NotificationListenerFilter.CREATOR);
              data.enforceNoDataAvail();
              setListenerFilter(_arg0133, _arg196, _arg245);
              reply.writeNoException();
              return true;
            case 147:
              INotificationListener _arg0134 =
                  INotificationListener.Stub.asInterface(data.readStrongBinder());
              int _arg197 = data.readInt();
              List<String> _arg246 = data.createStringArrayList();
              data.enforceNoDataAvail();
              migrateNotificationFilter(_arg0134, _arg197, _arg246);
              reply.writeNoException();
              return true;
            case 148:
              boolean _arg0135 = data.readBoolean();
              data.enforceNoDataAvail();
              setToastRateLimitingEnabled(_arg0135);
              reply.writeNoException();
              return true;
            case 149:
              ComponentName _arg0136 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
              int _arg198 = data.readInt();
              boolean _arg247 = data.readBoolean();
              data.enforceNoDataAvail();
              registerNotificationListener(_arg0136, _arg198, _arg247);
              reply.writeNoException();
              return true;
            case 150:
              String _arg0137 = data.readString();
              ParceledListSlice _arg199 =
                  (ParceledListSlice) data.readTypedObject(ParceledListSlice.CREATOR);
              data.enforceNoDataAvail();
              updateNotificationChannels(_arg0137, _arg199);
              reply.writeNoException();
              return true;
            case 151:
              IBinder _arg0138 = data.readStrongBinder();
              int _arg1100 = data.readInt();
              ComponentName _arg248 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
              data.enforceNoDataAvail();
              bindEdgeLightingService(_arg0138, _arg1100, _arg248);
              reply.writeNoException();
              return true;
            case 152:
              IBinder _arg0139 = data.readStrongBinder();
              String _arg1101 = data.readString();
              data.enforceNoDataAvail();
              unbindEdgeLightingService(_arg0139, _arg1101);
              reply.writeNoException();
              return true;
            case 153:
              String _arg0140 = data.readString();
              List<String> _arg1102 = data.createStringArrayList();
              data.enforceNoDataAvail();
              updateEdgeLightingPackageList(_arg0140, _arg1102);
              reply.writeNoException();
              return true;
            case 154:
              String _arg0141 = data.readString();
              EdgeLightingPolicy _arg1103 =
                  (EdgeLightingPolicy) data.readTypedObject(EdgeLightingPolicy.CREATOR);
              data.enforceNoDataAvail();
              updateEdgeLightingPolicy(_arg0141, _arg1103);
              reply.writeNoException();
              return true;
            case 155:
              IBinder _arg0142 = data.readStrongBinder();
              ComponentName _arg1104 = (ComponentName) data.readTypedObject(ComponentName.CREATOR);
              data.enforceNoDataAvail();
              registerEdgeLightingListener(_arg0142, _arg1104);
              reply.writeNoException();
              return true;
            case 156:
              IBinder _arg0143 = data.readStrongBinder();
              String _arg1105 = data.readString();
              data.enforceNoDataAvail();
              unregisterEdgeLightingListener(_arg0143, _arg1105);
              reply.writeNoException();
              return true;
            case 157:
              String _arg0144 = data.readString();
              SemEdgeLightingInfo _arg1106 =
                  (SemEdgeLightingInfo) data.readTypedObject(SemEdgeLightingInfo.CREATOR);
              IBinder _arg249 = data.readStrongBinder();
              data.enforceNoDataAvail();
              startEdgeLighting(_arg0144, _arg1106, _arg249);
              reply.writeNoException();
              return true;
            case 158:
              String _arg0145 = data.readString();
              IBinder _arg1107 = data.readStrongBinder();
              data.enforceNoDataAvail();
              stopEdgeLighting(_arg0145, _arg1107);
              reply.writeNoException();
              return true;
            case 159:
              int _result81 = getEdgeLightingState();
              reply.writeNoException();
              reply.writeInt(_result81);
              return true;
            case 160:
              String _arg0146 = data.readString();
              data.enforceNoDataAvail();
              boolean _result82 = isEdgeLightingNotificationAllowed(_arg0146);
              reply.writeNoException();
              reply.writeBoolean(_result82);
              return true;
            case 161:
              int _arg0147 = data.readInt();
              String _arg1108 = data.readString();
              IBinder _arg250 = data.readStrongBinder();
              data.enforceNoDataAvail();
              disable(_arg0147, _arg1108, _arg250);
              reply.writeNoException();
              return true;
            case 162:
              String _arg0148 = data.readString();
              boolean _arg1109 = data.readBoolean();
              data.enforceNoDataAvail();
              disableEdgeLightingNotification(_arg0148, _arg1109);
              reply.writeNoException();
              return true;
            case 163:
              String _arg0149 = data.readString();
              int _arg1110 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result83 = isPackageEnabled(_arg0149, _arg1110);
              reply.writeNoException();
              reply.writeBoolean(_result83);
              return true;
            case 164:
              String _arg0150 = data.readString();
              String _arg1111 = data.readString();
              int _arg251 = data.readInt();
              int _arg315 = data.readInt();
              String _arg47 = data.readString();
              data.enforceNoDataAvail();
              cancelNotificationByEdge(_arg0150, _arg1111, _arg251, _arg315, _arg47);
              reply.writeNoException();
              return true;
            case 165:
              String _arg0151 = data.readString();
              String _arg1112 = data.readString();
              int _arg252 = data.readInt();
              int _arg316 = data.readInt();
              String _arg48 = data.readString();
              String _arg55 = data.readString();
              data.enforceNoDataAvail();
              cancelNotificationByGroupKey(_arg0151, _arg1112, _arg252, _arg316, _arg48, _arg55);
              reply.writeNoException();
              return true;
            case 166:
              String _arg0152 = data.readString();
              String _arg1113 = data.readString();
              int _arg253 = data.readInt();
              Bundle _arg317 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              int _arg49 = data.readInt();
              data.enforceNoDataAvail();
              enqueueEdgeNotification(_arg0152, _arg1113, _arg253, _arg317, _arg49);
              reply.writeNoException();
              return true;
            case 167:
              String _arg0153 = data.readString();
              int _arg1114 = data.readInt();
              Bundle _arg254 = (Bundle) data.readTypedObject(Bundle.CREATOR);
              int _arg318 = data.readInt();
              data.enforceNoDataAvail();
              removeEdgeNotification(_arg0153, _arg1114, _arg254, _arg318);
              reply.writeNoException();
              return true;
            case 168:
              String _arg0154 = data.readString();
              int _arg1115 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result84 = isEdgeLightingAllowed(_arg0154, _arg1115);
              reply.writeNoException();
              reply.writeBoolean(_result84);
              return true;
            case 169:
              String _arg0155 = data.readString();
              int _arg1116 = data.readInt();
              boolean _arg255 = data.readBoolean();
              data.enforceNoDataAvail();
              setAllowEdgeLighting(_arg0155, _arg1116, _arg255);
              reply.writeNoException();
              return true;
            case 170:
              resetDefaultAllowEdgeLighting();
              reply.writeNoException();
              return true;
            case 171:
              int _arg0156 = data.readInt();
              String _arg1117 = data.readString();
              String _arg256 = data.readString();
              int _arg319 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result85 =
                  dispatchDelayedWakelockAndBlocked(_arg0156, _arg1117, _arg256, _arg319);
              reply.writeNoException();
              reply.writeBoolean(_result85);
              return true;
            case 172:
              int _arg0157 = data.readInt();
              String _arg1118 = data.readString();
              String _arg257 = data.readString();
              data.enforceNoDataAvail();
              boolean _result86 = dispatchDelayedWakeUpAndBlocked(_arg0157, _arg1118, _arg257);
              reply.writeNoException();
              reply.writeBoolean(_result86);
              return true;
            case 173:
              String _arg0158 = data.readString();
              int _arg1119 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result87 = isSubDisplayNotificationAllowed(_arg0158, _arg1119);
              reply.writeNoException();
              reply.writeBoolean(_result87);
              return true;
            case 174:
              String _arg0159 = data.readString();
              int _arg1120 = data.readInt();
              boolean _arg258 = data.readBoolean();
              data.enforceNoDataAvail();
              setAllowSubDisplayNotification(_arg0159, _arg1120, _arg258);
              reply.writeNoException();
              return true;
            case 175:
              String _arg0160 = data.readString();
              int _arg1121 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result88 = getNotificationAlertsEnabledForPackage(_arg0160, _arg1121);
              reply.writeNoException();
              reply.writeBoolean(_result88);
              return true;
            case 176:
              String _arg0161 = data.readString();
              int _arg1122 = data.readInt();
              boolean _arg259 = data.readBoolean();
              data.enforceNoDataAvail();
              setNotificationAlertsEnabledForPackage(_arg0161, _arg1122, _arg259);
              reply.writeNoException();
              return true;
            case 177:
              int _arg0162 = data.readInt();
              List<String> _arg1123 = data.createStringArrayList();
              data.enforceNoDataAvail();
              boolean _result89 = setWearableAppList(_arg0162, _arg1123);
              reply.writeNoException();
              reply.writeBoolean(_result89);
              return true;
            case 178:
              int _arg0163 = data.readInt();
              String _arg1124 = data.readString();
              data.enforceNoDataAvail();
              boolean _result90 = addWearableAppToList(_arg0163, _arg1124);
              reply.writeNoException();
              reply.writeBoolean(_result90);
              return true;
            case 179:
              int _arg0164 = data.readInt();
              String _arg1125 = data.readString();
              data.enforceNoDataAvail();
              boolean _result91 = removeWearableAppFromList(_arg0164, _arg1125);
              reply.writeNoException();
              reply.writeBoolean(_result91);
              return true;
            case 180:
              int _arg0165 = data.readInt();
              data.enforceNoDataAvail();
              List<String> _result92 = getWearableAppList(_arg0165);
              reply.writeNoException();
              reply.writeStringList(_result92);
              return true;
            case 181:
              int _arg0166 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result93 = requestListenerHintsForWearable(_arg0166);
              reply.writeNoException();
              reply.writeBoolean(_result93);
              return true;
            case 182:
              String _arg0167 = data.readString();
              int _arg1126 = data.readInt();
              data.enforceNoDataAvail();
              int _result94 = getLockScreenNotificationVisibilityForPackage(_arg0167, _arg1126);
              reply.writeNoException();
              reply.writeInt(_result94);
              return true;
            case 183:
              String _arg0168 = data.readString();
              int _arg1127 = data.readInt();
              int _arg260 = data.readInt();
              data.enforceNoDataAvail();
              setLockScreenNotificationVisibilityForPackage(_arg0168, _arg1127, _arg260);
              reply.writeNoException();
              return true;
            case 184:
              String _arg0169 = data.readString();
              int _arg1128 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result95 = isAllowNotificationPopUpForPackage(_arg0169, _arg1128);
              reply.writeNoException();
              reply.writeBoolean(_result95);
              return true;
            case 185:
              String _arg0170 = data.readString();
              int _arg1129 = data.readInt();
              boolean _arg261 = data.readBoolean();
              data.enforceNoDataAvail();
              setAllowNotificationPopUpForPackage(_arg0170, _arg1129, _arg261);
              reply.writeNoException();
              return true;
            case 186:
              String _arg0171 = data.readString();
              int _arg1130 = data.readInt();
              String _arg262 = data.readString();
              int _arg320 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result96 = isAlertsAllowed(_arg0171, _arg1130, _arg262, _arg320);
              reply.writeNoException();
              reply.writeBoolean(_result96);
              return true;
            case 187:
              String _arg0172 = data.readString();
              int _arg1131 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result97 = isReminderEnabled(_arg0172, _arg1131);
              reply.writeNoException();
              reply.writeBoolean(_result97);
              return true;
            case 188:
              String _arg0173 = data.readString();
              int _arg1132 = data.readInt();
              boolean _arg263 = data.readBoolean();
              data.enforceNoDataAvail();
              setReminderEnabledForPackage(_arg0173, _arg1132, _arg263);
              reply.writeNoException();
              return true;
            case 189:
              int _arg0174 = data.readInt();
              boolean _arg1133 = data.readBoolean();
              List<String> _arg264 = data.createStringArrayList();
              data.enforceNoDataAvail();
              setReminderEnabled(_arg0174, _arg1133, _arg264);
              reply.writeNoException();
              return true;
            case 190:
              int _arg0175 = data.readInt();
              data.enforceNoDataAvail();
              int _result98 = getBlockedAppCount(_arg0175);
              reply.writeNoException();
              reply.writeInt(_result98);
              return true;
            case 191:
              String _arg0176 = data.readString();
              int _arg1134 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result99 = canAppBypassDnd(_arg0176, _arg1134);
              reply.writeNoException();
              reply.writeBoolean(_result99);
              return true;
            case 192:
              String _arg0177 = data.readString();
              int _arg1135 = data.readInt();
              boolean _arg265 = data.readBoolean();
              data.enforceNoDataAvail();
              setAppBypassDnd(_arg0177, _arg1135, _arg265);
              reply.writeNoException();
              return true;
            case 193:
              int _arg0178 = data.readInt();
              data.enforceNoDataAvail();
              int _result100 = getAppsBypassingDndCount(_arg0178);
              reply.writeNoException();
              reply.writeInt(_result100);
              return true;
            case 194:
              String _arg0179 = data.readString();
              IBinder _arg1136 = data.readStrongBinder();
              CharSequence _arg266 =
                  (CharSequence) data.readTypedObject(TextUtils.CHAR_SEQUENCE_CREATOR);
              int _arg321 = data.readInt();
              boolean _arg410 = data.readBoolean();
              int _arg56 = data.readInt();
              ITransientNotificationCallback _arg62 =
                  ITransientNotificationCallback.Stub.asInterface(data.readStrongBinder());
              String _arg7 = data.readString();
              int _arg8 = data.readInt();
              data.enforceNoDataAvail();
              enqueueTextToastForDex(
                  _arg0179, _arg1136, _arg266, _arg321, _arg410, _arg56, _arg62, _arg7, _arg8);
              reply.writeNoException();
              return true;
            case 195:
              String _arg0180 = data.readString();
              IBinder _arg1137 = data.readStrongBinder();
              ITransientNotification _arg267 =
                  ITransientNotification.Stub.asInterface(data.readStrongBinder());
              int _arg322 = data.readInt();
              boolean _arg411 = data.readBoolean();
              int _arg57 = data.readInt();
              String _arg63 = data.readString();
              int _arg72 = data.readInt();
              data.enforceNoDataAvail();
              enqueueToastForDex(
                  _arg0180, _arg1137, _arg267, _arg322, _arg411, _arg57, _arg63, _arg72);
              reply.writeNoException();
              return true;
            case 196:
              int _arg0181 = data.readInt();
              String _arg1138 = data.readString();
              String _arg268 = data.readString();
              int _arg323 = data.readInt();
              String _arg412 = data.readString();
              String _arg58 = data.readString();
              data.enforceNoDataAvail();
              addReplyHistory(_arg0181, _arg1138, _arg268, _arg323, _arg412, _arg58);
              reply.writeNoException();
              return true;
            case 197:
              String _arg0182 = data.readString();
              String _arg1139 = data.readString();
              int _arg269 = data.readInt();
              String _arg324 = data.readString();
              String _arg413 = data.readString();
              int _arg59 = data.readInt();
              data.enforceNoDataAvail();
              List<Bundle> _result101 =
                  getNotificationHistoryDataForPackage(
                      _arg0182, _arg1139, _arg269, _arg324, _arg413, _arg59);
              reply.writeNoException();
              reply.writeTypedList(_result101, 1);
              return true;
            case 198:
              String _arg0183 = data.readString();
              String _arg1140 = data.readString();
              int _arg270 = data.readInt();
              String _arg325 = data.readString();
              String _arg414 = data.readString();
              int _arg510 = data.readInt();
              data.enforceNoDataAvail();
              NotificationHistory _result102 =
                  getNotificationHistoryForPackage(
                      _arg0183, _arg1140, _arg270, _arg325, _arg414, _arg510);
              reply.writeNoException();
              reply.writeTypedObject(_result102, 1);
              return true;
            case 199:
              int _arg0184 = data.readInt();
              String _arg1141 = data.readString();
              boolean _arg271 = data.readBoolean();
              data.enforceNoDataAvail();
              updateCancelEvent(_arg0184, _arg1141, _arg271);
              reply.writeNoException();
              return true;
            case 200:
              List<String> _arg0185 = data.createStringArrayList();
              data.enforceNoDataAvail();
              setRestoreBlockListForSS(_arg0185);
              reply.writeNoException();
              return true;
            case 201:
              int _result103 = getAllNotificationListenersCount();
              reply.writeNoException();
              reply.writeInt(_result103);
              return true;
            case 202:
              String _arg0186 = data.readString();
              int _arg1142 = data.readInt();
              data.enforceNoDataAvail();
              int _result104 = isNotificationTurnedOff(_arg0186, _arg1142);
              reply.writeNoException();
              reply.writeInt(_result104);
              return true;
            case 203:
              String _arg0187 = data.readString();
              data.enforceNoDataAvail();
              int _result105 = getNotificationSoundStatus(_arg0187);
              reply.writeNoException();
              reply.writeInt(_result105);
              return true;
            case 204:
              String _arg0188 = data.readString();
              int _arg1143 = data.readInt();
              data.enforceNoDataAvail();
              boolean _result106 = setNotificationTurnOff(_arg0188, _arg1143);
              reply.writeNoException();
              reply.writeBoolean(_result106);
              return true;
            default:
              return super.onTransact(code, data, reply, flags);
          }
      }
    }

    private static class Proxy implements INotificationManager {
      private IBinder mRemote;

      Proxy(IBinder remote) {
        this.mRemote = remote;
      }

      @Override // android.p009os.IInterface
      public IBinder asBinder() {
        return this.mRemote;
      }

      public String getInterfaceDescriptor() {
        return Stub.DESCRIPTOR;
      }

      @Override // android.app.INotificationManager
      public void cancelAllNotifications(String pkg, int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(userId);
          this.mRemote.transact(1, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void clearData(String pkg, int uid, boolean fromApp) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(uid);
          _data.writeBoolean(fromApp);
          this.mRemote.transact(2, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void enqueueTextToast(
          String pkg,
          IBinder token,
          CharSequence text,
          int duration,
          boolean isUiContext,
          int displayId,
          ITransientNotificationCallback callback)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeStrongBinder(token);
          if (text != null) {
            _data.writeInt(1);
            TextUtils.writeToParcel(text, _data, 0);
          } else {
            _data.writeInt(0);
          }
          _data.writeInt(duration);
          _data.writeBoolean(isUiContext);
          _data.writeInt(displayId);
          _data.writeStrongInterface(callback);
          this.mRemote.transact(3, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void enqueueToast(
          String pkg,
          IBinder token,
          ITransientNotification callback,
          int duration,
          boolean isUiContext,
          int displayId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeStrongBinder(token);
          _data.writeStrongInterface(callback);
          _data.writeInt(duration);
          _data.writeBoolean(isUiContext);
          _data.writeInt(displayId);
          this.mRemote.transact(4, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void cancelToast(String pkg, IBinder token) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeStrongBinder(token);
          this.mRemote.transact(5, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void finishToken(String pkg, IBinder token) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeStrongBinder(token);
          this.mRemote.transact(6, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void enqueueNotificationWithTag(
          String pkg, String opPkg, String tag, int id, Notification notification, int userId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeString(opPkg);
          _data.writeString(tag);
          _data.writeInt(id);
          _data.writeTypedObject(notification, 0);
          _data.writeInt(userId);
          this.mRemote.transact(7, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void cancelNotificationWithTag(
          String pkg, String opPkg, String tag, int id, int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeString(opPkg);
          _data.writeString(tag);
          _data.writeInt(id);
          _data.writeInt(userId);
          this.mRemote.transact(8, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public boolean isInCall(String pkg, int uid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(uid);
          this.mRemote.transact(9, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void setShowBadge(String pkg, int uid, boolean showBadge) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(uid);
          _data.writeBoolean(showBadge);
          this.mRemote.transact(10, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public boolean canShowBadge(String pkg, int uid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(uid);
          this.mRemote.transact(11, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public boolean hasSentValidMsg(String pkg, int uid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(uid);
          this.mRemote.transact(12, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public boolean isInInvalidMsgState(String pkg, int uid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(uid);
          this.mRemote.transact(13, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public boolean hasUserDemotedInvalidMsgApp(String pkg, int uid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(uid);
          this.mRemote.transact(14, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void setInvalidMsgAppDemoted(String pkg, int uid, boolean isDemoted)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(uid);
          _data.writeBoolean(isDemoted);
          this.mRemote.transact(15, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public boolean hasSentValidBubble(String pkg, int uid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(uid);
          this.mRemote.transact(16, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void setNotificationsEnabledForPackage(String pkg, int uid, boolean enabled)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(uid);
          _data.writeBoolean(enabled);
          this.mRemote.transact(17, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void setNotificationsEnabledWithImportanceLockForPackage(
          String pkg, int uid, boolean enabled) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(uid);
          _data.writeBoolean(enabled);
          this.mRemote.transact(18, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public boolean areNotificationsEnabledForPackage(String pkg, int uid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(uid);
          this.mRemote.transact(19, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public boolean areNotificationsEnabled(String pkg) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          this.mRemote.transact(20, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public int getPackageImportance(String pkg) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          this.mRemote.transact(21, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public boolean isImportanceLocked(String pkg, int uid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(uid);
          this.mRemote.transact(22, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public List<String> getAllowedAssistantAdjustments(String pkg) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          this.mRemote.transact(23, _data, _reply, 0);
          _reply.readException();
          List<String> _result = _reply.createStringArrayList();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public boolean shouldHideSilentStatusIcons(String callingPkg) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(callingPkg);
          this.mRemote.transact(24, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void setHideSilentStatusIcons(boolean hide) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeBoolean(hide);
          this.mRemote.transact(25, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void setBubblesAllowed(String pkg, int uid, int bubblePreference)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(uid);
          _data.writeInt(bubblePreference);
          this.mRemote.transact(26, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public boolean areBubblesAllowed(String pkg) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          this.mRemote.transact(27, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public boolean areBubblesEnabled(UserHandle user) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(user, 0);
          this.mRemote.transact(28, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public int getBubblePreferenceForPackage(String pkg, int uid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(uid);
          this.mRemote.transact(29, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void createNotificationChannelGroups(String pkg, ParceledListSlice channelGroupList)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeTypedObject(channelGroupList, 0);
          this.mRemote.transact(30, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void createNotificationChannels(String pkg, ParceledListSlice channelsList)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeTypedObject(channelsList, 0);
          this.mRemote.transact(31, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void createNotificationChannelsForPackage(
          String pkg, int uid, ParceledListSlice channelsList) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(uid);
          _data.writeTypedObject(channelsList, 0);
          this.mRemote.transact(32, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public ParceledListSlice getConversations(boolean onlyImportant) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeBoolean(onlyImportant);
          this.mRemote.transact(33, _data, _reply, 0);
          _reply.readException();
          ParceledListSlice _result =
              (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public ParceledListSlice getConversationsForPackage(String pkg, int uid)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(uid);
          this.mRemote.transact(34, _data, _reply, 0);
          _reply.readException();
          ParceledListSlice _result =
              (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public ParceledListSlice getNotificationChannelGroupsForPackage(
          String pkg, int uid, boolean includeDeleted) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(uid);
          _data.writeBoolean(includeDeleted);
          this.mRemote.transact(35, _data, _reply, 0);
          _reply.readException();
          ParceledListSlice _result =
              (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public NotificationChannelGroup getNotificationChannelGroupForPackage(
          String groupId, String pkg, int uid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(groupId);
          _data.writeString(pkg);
          _data.writeInt(uid);
          this.mRemote.transact(36, _data, _reply, 0);
          _reply.readException();
          NotificationChannelGroup _result =
              (NotificationChannelGroup) _reply.readTypedObject(NotificationChannelGroup.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public NotificationChannelGroup getPopulatedNotificationChannelGroupForPackage(
          String pkg, int uid, String groupId, boolean includeDeleted) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(uid);
          _data.writeString(groupId);
          _data.writeBoolean(includeDeleted);
          this.mRemote.transact(37, _data, _reply, 0);
          _reply.readException();
          NotificationChannelGroup _result =
              (NotificationChannelGroup) _reply.readTypedObject(NotificationChannelGroup.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void updateNotificationChannelGroupForPackage(
          String pkg, int uid, NotificationChannelGroup group) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(uid);
          _data.writeTypedObject(group, 0);
          this.mRemote.transact(38, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void updateNotificationChannelForPackage(
          String pkg, int uid, NotificationChannel channel) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(uid);
          _data.writeTypedObject(channel, 0);
          this.mRemote.transact(39, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void unlockNotificationChannel(String pkg, int uid, String channelId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(uid);
          _data.writeString(channelId);
          this.mRemote.transact(40, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void unlockAllNotificationChannels() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(41, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public NotificationChannel getNotificationChannel(
          String callingPkg, int userId, String pkg, String channelId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(callingPkg);
          _data.writeInt(userId);
          _data.writeString(pkg);
          _data.writeString(channelId);
          this.mRemote.transact(42, _data, _reply, 0);
          _reply.readException();
          NotificationChannel _result =
              (NotificationChannel) _reply.readTypedObject(NotificationChannel.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public NotificationChannel getConversationNotificationChannel(
          String callingPkg,
          int userId,
          String pkg,
          String channelId,
          boolean returnParentIfNoConversationChannel,
          String conversationId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(callingPkg);
          _data.writeInt(userId);
          _data.writeString(pkg);
          _data.writeString(channelId);
          _data.writeBoolean(returnParentIfNoConversationChannel);
          _data.writeString(conversationId);
          this.mRemote.transact(43, _data, _reply, 0);
          _reply.readException();
          NotificationChannel _result =
              (NotificationChannel) _reply.readTypedObject(NotificationChannel.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void createConversationNotificationChannelForPackage(
          String pkg, int uid, NotificationChannel parentChannel, String conversationId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(uid);
          _data.writeTypedObject(parentChannel, 0);
          _data.writeString(conversationId);
          this.mRemote.transact(44, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public NotificationChannel getNotificationChannelForPackage(
          String pkg, int uid, String channelId, String conversationId, boolean includeDeleted)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(uid);
          _data.writeString(channelId);
          _data.writeString(conversationId);
          _data.writeBoolean(includeDeleted);
          this.mRemote.transact(45, _data, _reply, 0);
          _reply.readException();
          NotificationChannel _result =
              (NotificationChannel) _reply.readTypedObject(NotificationChannel.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void deleteNotificationChannel(String pkg, String channelId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeString(channelId);
          this.mRemote.transact(46, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public ParceledListSlice getNotificationChannels(
          String callingPkg, String targetPkg, int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(callingPkg);
          _data.writeString(targetPkg);
          _data.writeInt(userId);
          this.mRemote.transact(47, _data, _reply, 0);
          _reply.readException();
          ParceledListSlice _result =
              (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public ParceledListSlice getNotificationChannelsForPackage(
          String pkg, int uid, boolean includeDeleted) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(uid);
          _data.writeBoolean(includeDeleted);
          this.mRemote.transact(48, _data, _reply, 0);
          _reply.readException();
          ParceledListSlice _result =
              (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public int getNumNotificationChannelsForPackage(String pkg, int uid, boolean includeDeleted)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(uid);
          _data.writeBoolean(includeDeleted);
          this.mRemote.transact(49, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public int getDeletedChannelCount(String pkg, int uid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(uid);
          this.mRemote.transact(50, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public int getBlockedChannelCount(String pkg, int uid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(uid);
          this.mRemote.transact(51, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void deleteNotificationChannelGroup(String pkg, String channelGroupId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeString(channelGroupId);
          this.mRemote.transact(52, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public NotificationChannelGroup getNotificationChannelGroup(String pkg, String channelGroupId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeString(channelGroupId);
          this.mRemote.transact(53, _data, _reply, 0);
          _reply.readException();
          NotificationChannelGroup _result =
              (NotificationChannelGroup) _reply.readTypedObject(NotificationChannelGroup.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public ParceledListSlice getNotificationChannelGroups(String pkg) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          this.mRemote.transact(54, _data, _reply, 0);
          _reply.readException();
          ParceledListSlice _result =
              (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public boolean onlyHasDefaultChannel(String pkg, int uid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(uid);
          this.mRemote.transact(55, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public boolean areChannelsBypassingDnd() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(56, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public ParceledListSlice getNotificationChannelsBypassingDnd(String pkg, int uid)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(uid);
          this.mRemote.transact(57, _data, _reply, 0);
          _reply.readException();
          ParceledListSlice _result =
              (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public boolean isPackagePaused(String pkg) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          this.mRemote.transact(58, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void deleteNotificationHistoryItem(String pkg, int uid, long postedTime)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(uid);
          _data.writeLong(postedTime);
          this.mRemote.transact(59, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public boolean isPermissionFixed(String pkg, int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(userId);
          this.mRemote.transact(60, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void silenceNotificationSound() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(61, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public StatusBarNotification[] getActiveNotifications(String callingPkg)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(callingPkg);
          this.mRemote.transact(62, _data, _reply, 0);
          _reply.readException();
          StatusBarNotification[] _result =
              (StatusBarNotification[]) _reply.createTypedArray(StatusBarNotification.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public StatusBarNotification[] getActiveNotificationsWithAttribution(
          String callingPkg, String callingAttributionTag) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(callingPkg);
          _data.writeString(callingAttributionTag);
          this.mRemote.transact(63, _data, _reply, 0);
          _reply.readException();
          StatusBarNotification[] _result =
              (StatusBarNotification[]) _reply.createTypedArray(StatusBarNotification.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public StatusBarNotification[] getHistoricalNotifications(
          String callingPkg, int count, boolean includeSnoozed) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(callingPkg);
          _data.writeInt(count);
          _data.writeBoolean(includeSnoozed);
          this.mRemote.transact(64, _data, _reply, 0);
          _reply.readException();
          StatusBarNotification[] _result =
              (StatusBarNotification[]) _reply.createTypedArray(StatusBarNotification.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public StatusBarNotification[] getHistoricalNotificationsWithAttribution(
          String callingPkg, String callingAttributionTag, int count, boolean includeSnoozed)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(callingPkg);
          _data.writeString(callingAttributionTag);
          _data.writeInt(count);
          _data.writeBoolean(includeSnoozed);
          this.mRemote.transact(65, _data, _reply, 0);
          _reply.readException();
          StatusBarNotification[] _result =
              (StatusBarNotification[]) _reply.createTypedArray(StatusBarNotification.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public NotificationHistory getNotificationHistory(
          String callingPkg, String callingAttributionTag) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(callingPkg);
          _data.writeString(callingAttributionTag);
          this.mRemote.transact(66, _data, _reply, 0);
          _reply.readException();
          NotificationHistory _result =
              (NotificationHistory) _reply.readTypedObject(NotificationHistory.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void registerListener(
          INotificationListener listener, ComponentName component, int userid)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(listener);
          _data.writeTypedObject(component, 0);
          _data.writeInt(userid);
          this.mRemote.transact(67, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void unregisterListener(INotificationListener listener, int userid)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(listener);
          _data.writeInt(userid);
          this.mRemote.transact(68, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void cancelNotificationFromListener(
          INotificationListener token, String pkg, String tag, int id) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(token);
          _data.writeString(pkg);
          _data.writeString(tag);
          _data.writeInt(id);
          this.mRemote.transact(69, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void cancelNotificationsFromListener(INotificationListener token, String[] keys)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(token);
          _data.writeStringArray(keys);
          this.mRemote.transact(70, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void snoozeNotificationUntilContextFromListener(
          INotificationListener token, String key, String snoozeCriterionId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(token);
          _data.writeString(key);
          _data.writeString(snoozeCriterionId);
          this.mRemote.transact(71, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void snoozeNotificationUntilFromListener(
          INotificationListener token, String key, long until) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(token);
          _data.writeString(key);
          _data.writeLong(until);
          this.mRemote.transact(72, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void requestBindListener(ComponentName component) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(component, 0);
          this.mRemote.transact(73, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void requestUnbindListener(INotificationListener token) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(token);
          this.mRemote.transact(74, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void requestUnbindListenerComponent(ComponentName component) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(component, 0);
          this.mRemote.transact(75, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void requestBindProvider(ComponentName component) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(component, 0);
          this.mRemote.transact(76, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void requestUnbindProvider(IConditionProvider token) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(token);
          this.mRemote.transact(77, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void setNotificationsShownFromListener(INotificationListener token, String[] keys)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(token);
          _data.writeStringArray(keys);
          this.mRemote.transact(78, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public ParceledListSlice getActiveNotificationsFromListener(
          INotificationListener token, String[] keys, int trim) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(token);
          _data.writeStringArray(keys);
          _data.writeInt(trim);
          this.mRemote.transact(79, _data, _reply, 0);
          _reply.readException();
          ParceledListSlice _result =
              (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public ParceledListSlice getSnoozedNotificationsFromListener(
          INotificationListener token, int trim) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(token);
          _data.writeInt(trim);
          this.mRemote.transact(80, _data, _reply, 0);
          _reply.readException();
          ParceledListSlice _result =
              (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void clearRequestedListenerHints(INotificationListener token) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(token);
          this.mRemote.transact(81, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void requestHintsFromListener(INotificationListener token, int hints)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(token);
          _data.writeInt(hints);
          this.mRemote.transact(82, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public int getHintsFromListener(INotificationListener token) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(token);
          this.mRemote.transact(83, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public int getHintsFromListenerNoToken() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(84, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void requestInterruptionFilterFromListener(
          INotificationListener token, int interruptionFilter) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(token);
          _data.writeInt(interruptionFilter);
          this.mRemote.transact(85, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public int getInterruptionFilterFromListener(INotificationListener token)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(token);
          this.mRemote.transact(86, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void setOnNotificationPostedTrimFromListener(INotificationListener token, int trim)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(token);
          _data.writeInt(trim);
          this.mRemote.transact(87, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void setInterruptionFilter(String pkg, int interruptionFilter) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(interruptionFilter);
          this.mRemote.transact(88, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void updateNotificationChannelGroupFromPrivilegedListener(
          INotificationListener token, String pkg, UserHandle user, NotificationChannelGroup group)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(token);
          _data.writeString(pkg);
          _data.writeTypedObject(user, 0);
          _data.writeTypedObject(group, 0);
          this.mRemote.transact(89, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void updateNotificationChannelFromPrivilegedListener(
          INotificationListener token, String pkg, UserHandle user, NotificationChannel channel)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(token);
          _data.writeString(pkg);
          _data.writeTypedObject(user, 0);
          _data.writeTypedObject(channel, 0);
          this.mRemote.transact(90, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public ParceledListSlice getNotificationChannelsFromPrivilegedListener(
          INotificationListener token, String pkg, UserHandle user) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(token);
          _data.writeString(pkg);
          _data.writeTypedObject(user, 0);
          this.mRemote.transact(91, _data, _reply, 0);
          _reply.readException();
          ParceledListSlice _result =
              (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public ParceledListSlice getNotificationChannelGroupsFromPrivilegedListener(
          INotificationListener token, String pkg, UserHandle user) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(token);
          _data.writeString(pkg);
          _data.writeTypedObject(user, 0);
          this.mRemote.transact(92, _data, _reply, 0);
          _reply.readException();
          ParceledListSlice _result =
              (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void applyEnqueuedAdjustmentFromAssistant(
          INotificationListener token, Adjustment adjustment) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(token);
          _data.writeTypedObject(adjustment, 0);
          this.mRemote.transact(93, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void applyAdjustmentFromAssistant(INotificationListener token, Adjustment adjustment)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(token);
          _data.writeTypedObject(adjustment, 0);
          this.mRemote.transact(94, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void applyAdjustmentsFromAssistant(
          INotificationListener token, List<Adjustment> adjustments) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(token);
          _data.writeTypedList(adjustments, 0);
          this.mRemote.transact(95, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void unsnoozeNotificationFromAssistant(INotificationListener token, String key)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(token);
          _data.writeString(key);
          this.mRemote.transact(96, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void unsnoozeNotificationFromSystemListener(INotificationListener token, String key)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(token);
          _data.writeString(key);
          this.mRemote.transact(97, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public ComponentName getEffectsSuppressor() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(98, _data, _reply, 0);
          _reply.readException();
          ComponentName _result = (ComponentName) _reply.readTypedObject(ComponentName.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public boolean matchesCallFilter(Bundle extras) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(extras, 0);
          this.mRemote.transact(99, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void cleanUpCallersAfter(long timeThreshold) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeLong(timeThreshold);
          this.mRemote.transact(100, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public boolean isSystemConditionProviderEnabled(String path) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(path);
          this.mRemote.transact(101, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public boolean isNotificationListenerAccessGranted(ComponentName listener)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(listener, 0);
          this.mRemote.transact(102, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public boolean isNotificationListenerAccessGrantedForUser(ComponentName listener, int userId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(listener, 0);
          _data.writeInt(userId);
          this.mRemote.transact(103, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public boolean isNotificationAssistantAccessGranted(ComponentName assistant)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(assistant, 0);
          this.mRemote.transact(104, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void setNotificationListenerAccessGranted(
          ComponentName listener, boolean enabled, boolean userSet) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(listener, 0);
          _data.writeBoolean(enabled);
          _data.writeBoolean(userSet);
          this.mRemote.transact(105, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void setNotificationAssistantAccessGranted(ComponentName assistant, boolean enabled)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(assistant, 0);
          _data.writeBoolean(enabled);
          this.mRemote.transact(106, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void setNotificationListenerAccessGrantedForUser(
          ComponentName listener, int userId, boolean enabled, boolean userSet)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(listener, 0);
          _data.writeInt(userId);
          _data.writeBoolean(enabled);
          _data.writeBoolean(userSet);
          this.mRemote.transact(107, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void setNotificationAssistantAccessGrantedForUser(
          ComponentName assistant, int userId, boolean enabled) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(assistant, 0);
          _data.writeInt(userId);
          _data.writeBoolean(enabled);
          this.mRemote.transact(108, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public List<String> getEnabledNotificationListenerPackages() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(109, _data, _reply, 0);
          _reply.readException();
          List<String> _result = _reply.createStringArrayList();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public List<ComponentName> getEnabledNotificationListeners(int userId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userId);
          this.mRemote.transact(110, _data, _reply, 0);
          _reply.readException();
          List<ComponentName> _result = _reply.createTypedArrayList(ComponentName.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public ComponentName getAllowedNotificationAssistantForUser(int userId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userId);
          this.mRemote.transact(111, _data, _reply, 0);
          _reply.readException();
          ComponentName _result = (ComponentName) _reply.readTypedObject(ComponentName.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public ComponentName getAllowedNotificationAssistant() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(112, _data, _reply, 0);
          _reply.readException();
          ComponentName _result = (ComponentName) _reply.readTypedObject(ComponentName.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public ComponentName getDefaultNotificationAssistant() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(113, _data, _reply, 0);
          _reply.readException();
          ComponentName _result = (ComponentName) _reply.readTypedObject(ComponentName.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void setNASMigrationDoneAndResetDefault(int userId, boolean loadFromConfig)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeBoolean(loadFromConfig);
          this.mRemote.transact(114, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public boolean hasEnabledNotificationListener(String packageName, int userId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(packageName);
          _data.writeInt(userId);
          this.mRemote.transact(115, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public int getZenMode() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(116, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public ZenModeConfig getZenModeConfig() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(117, _data, _reply, 0);
          _reply.readException();
          ZenModeConfig _result = (ZenModeConfig) _reply.readTypedObject(ZenModeConfig.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public NotificationManager.Policy getConsolidatedNotificationPolicy() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(118, _data, _reply, 0);
          _reply.readException();
          NotificationManager.Policy _result =
              (NotificationManager.Policy)
                  _reply.readTypedObject(NotificationManager.Policy.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void setZenMode(int mode, Uri conditionId, String reason) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(mode);
          _data.writeTypedObject(conditionId, 0);
          _data.writeString(reason);
          this.mRemote.transact(119, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void notifyConditions(String pkg, IConditionProvider provider, Condition[] conditions)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeStrongInterface(provider);
          _data.writeTypedArray(conditions, 0);
          this.mRemote.transact(120, _data, null, 1);
        } finally {
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public boolean isNotificationPolicyAccessGranted(String pkg) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          this.mRemote.transact(121, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public NotificationManager.Policy getNotificationPolicy(String pkg) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          this.mRemote.transact(122, _data, _reply, 0);
          _reply.readException();
          NotificationManager.Policy _result =
              (NotificationManager.Policy)
                  _reply.readTypedObject(NotificationManager.Policy.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void setNotificationPolicy(String pkg, NotificationManager.Policy policy)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeTypedObject(policy, 0);
          this.mRemote.transact(123, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public boolean isNotificationPolicyAccessGrantedForPackage(String pkg)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          this.mRemote.transact(124, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void setNotificationPolicyAccessGranted(String pkg, boolean granted)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeBoolean(granted);
          this.mRemote.transact(125, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void setNotificationPolicyAccessGrantedForUser(String pkg, int userId, boolean granted)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(userId);
          _data.writeBoolean(granted);
          this.mRemote.transact(126, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public AutomaticZenRule getAutomaticZenRule(String id) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(id);
          this.mRemote.transact(127, _data, _reply, 0);
          _reply.readException();
          AutomaticZenRule _result =
              (AutomaticZenRule) _reply.readTypedObject(AutomaticZenRule.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public List<ZenModeConfig.ZenRule> getZenRules() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(128, _data, _reply, 0);
          _reply.readException();
          List<ZenModeConfig.ZenRule> _result =
              _reply.createTypedArrayList(ZenModeConfig.ZenRule.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public String addAutomaticZenRule(AutomaticZenRule automaticZenRule, String pkg)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(automaticZenRule, 0);
          _data.writeString(pkg);
          this.mRemote.transact(129, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public boolean updateAutomaticZenRule(String id, AutomaticZenRule automaticZenRule)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(id);
          _data.writeTypedObject(automaticZenRule, 0);
          this.mRemote.transact(130, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public boolean removeAutomaticZenRule(String id) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(id);
          this.mRemote.transact(131, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public boolean removeAutomaticZenRules(String packageName) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(packageName);
          this.mRemote.transact(132, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public int getRuleInstanceCount(ComponentName owner) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(owner, 0);
          this.mRemote.transact(133, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void setAutomaticZenRuleState(String id, Condition condition) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(id);
          _data.writeTypedObject(condition, 0);
          this.mRemote.transact(134, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public byte[] getBackupPayload(int user) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(user);
          this.mRemote.transact(135, _data, _reply, 0);
          _reply.readException();
          byte[] _result = _reply.createByteArray();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void applyRestore(byte[] payload, int user) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeByteArray(payload);
          _data.writeInt(user);
          this.mRemote.transact(136, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public ParceledListSlice getAppActiveNotifications(String callingPkg, int userId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(callingPkg);
          _data.writeInt(userId);
          this.mRemote.transact(137, _data, _reply, 0);
          _reply.readException();
          ParceledListSlice _result =
              (ParceledListSlice) _reply.readTypedObject(ParceledListSlice.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void setNotificationDelegate(String callingPkg, String delegate)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(callingPkg);
          _data.writeString(delegate);
          this.mRemote.transact(138, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public String getNotificationDelegate(String callingPkg) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(callingPkg);
          this.mRemote.transact(139, _data, _reply, 0);
          _reply.readException();
          String _result = _reply.readString();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public boolean canNotifyAsPackage(String callingPkg, String targetPkg, int userId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(callingPkg);
          _data.writeString(targetPkg);
          _data.writeInt(userId);
          this.mRemote.transact(140, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public boolean canUseFullScreenIntent(AttributionSource attributionSource)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(attributionSource, 0);
          this.mRemote.transact(141, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void setPrivateNotificationsAllowed(boolean allow) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeBoolean(allow);
          this.mRemote.transact(142, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public boolean getPrivateNotificationsAllowed() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(143, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public long pullStats(
          long startNs, int report, boolean doAgg, List<ParcelFileDescriptor> stats)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeLong(startNs);
          _data.writeInt(report);
          _data.writeBoolean(doAgg);
          this.mRemote.transact(144, _data, _reply, 0);
          _reply.readException();
          long _result = _reply.readLong();
          _reply.readTypedList(stats, ParcelFileDescriptor.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public NotificationListenerFilter getListenerFilter(ComponentName cn, int userId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(cn, 0);
          _data.writeInt(userId);
          this.mRemote.transact(145, _data, _reply, 0);
          _reply.readException();
          NotificationListenerFilter _result =
              (NotificationListenerFilter)
                  _reply.readTypedObject(NotificationListenerFilter.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void setListenerFilter(ComponentName cn, int userId, NotificationListenerFilter nlf)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(cn, 0);
          _data.writeInt(userId);
          _data.writeTypedObject(nlf, 0);
          this.mRemote.transact(146, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void migrateNotificationFilter(
          INotificationListener token, int defaultTypes, List<String> disallowedPkgs)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongInterface(token);
          _data.writeInt(defaultTypes);
          _data.writeStringList(disallowedPkgs);
          this.mRemote.transact(147, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void setToastRateLimitingEnabled(boolean enable) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeBoolean(enable);
          this.mRemote.transact(148, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void registerNotificationListener(ComponentName listener, int userId, boolean enabled)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeTypedObject(listener, 0);
          _data.writeInt(userId);
          _data.writeBoolean(enabled);
          this.mRemote.transact(149, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void updateNotificationChannels(String pkg, ParceledListSlice channelsList)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeTypedObject(channelsList, 0);
          this.mRemote.transact(150, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void bindEdgeLightingService(IBinder binder, int condition, ComponentName component)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongBinder(binder);
          _data.writeInt(condition);
          _data.writeTypedObject(component, 0);
          this.mRemote.transact(151, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void unbindEdgeLightingService(IBinder binder, String packageName)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongBinder(binder);
          _data.writeString(packageName);
          this.mRemote.transact(152, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void updateEdgeLightingPackageList(String callingPackage, List<String> list)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(callingPackage);
          _data.writeStringList(list);
          this.mRemote.transact(153, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void updateEdgeLightingPolicy(String callingPackage, EdgeLightingPolicy policy)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(callingPackage);
          _data.writeTypedObject(policy, 0);
          this.mRemote.transact(154, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void registerEdgeLightingListener(IBinder binder, ComponentName component)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongBinder(binder);
          _data.writeTypedObject(component, 0);
          this.mRemote.transact(155, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void unregisterEdgeLightingListener(IBinder binder, String packageName)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStrongBinder(binder);
          _data.writeString(packageName);
          this.mRemote.transact(156, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void startEdgeLighting(String packageName, SemEdgeLightingInfo info, IBinder token)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(packageName);
          _data.writeTypedObject(info, 0);
          _data.writeStrongBinder(token);
          this.mRemote.transact(157, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void stopEdgeLighting(String packageName, IBinder token) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(packageName);
          _data.writeStrongBinder(token);
          this.mRemote.transact(158, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public int getEdgeLightingState() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(159, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public boolean isEdgeLightingNotificationAllowed(String packageName) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(packageName);
          this.mRemote.transact(160, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void disable(int what, String callingPackage, IBinder binder) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(what);
          _data.writeString(callingPackage);
          _data.writeStrongBinder(binder);
          this.mRemote.transact(161, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void disableEdgeLightingNotification(String callingPackage, boolean disable)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(callingPackage);
          _data.writeBoolean(disable);
          this.mRemote.transact(162, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public boolean isPackageEnabled(String packageName, int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(packageName);
          _data.writeInt(userId);
          this.mRemote.transact(163, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void cancelNotificationByEdge(String pkg, String tag, int id, int userId, String key)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeString(tag);
          _data.writeInt(id);
          _data.writeInt(userId);
          _data.writeString(key);
          this.mRemote.transact(164, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void cancelNotificationByGroupKey(
          String pkg, String tag, int id, int userId, String key, String groupKey)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeString(tag);
          _data.writeInt(id);
          _data.writeInt(userId);
          _data.writeString(key);
          _data.writeString(groupKey);
          this.mRemote.transact(165, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void enqueueEdgeNotification(
          String pkg, String opPkg, int id, Bundle extras, int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeString(opPkg);
          _data.writeInt(id);
          _data.writeTypedObject(extras, 0);
          _data.writeInt(userId);
          this.mRemote.transact(166, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void removeEdgeNotification(String pkg, int id, Bundle extras, int userId)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(id);
          _data.writeTypedObject(extras, 0);
          _data.writeInt(userId);
          this.mRemote.transact(167, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public boolean isEdgeLightingAllowed(String pkg, int uid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(uid);
          this.mRemote.transact(168, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void setAllowEdgeLighting(String pkg, int uid, boolean allowEdgeLighting)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(uid);
          _data.writeBoolean(allowEdgeLighting);
          this.mRemote.transact(169, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void resetDefaultAllowEdgeLighting() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(170, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public boolean dispatchDelayedWakelockAndBlocked(
          int flags, String tag, String packageName, int uid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(flags);
          _data.writeString(tag);
          _data.writeString(packageName);
          _data.writeInt(uid);
          this.mRemote.transact(171, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public boolean dispatchDelayedWakeUpAndBlocked(int reason, String details, String packageName)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(reason);
          _data.writeString(details);
          _data.writeString(packageName);
          this.mRemote.transact(172, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public boolean isSubDisplayNotificationAllowed(String pkg, int uid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(uid);
          this.mRemote.transact(173, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void setAllowSubDisplayNotification(String pkg, int uid, boolean allowSubDisplayNoti)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(uid);
          _data.writeBoolean(allowSubDisplayNoti);
          this.mRemote.transact(174, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public boolean getNotificationAlertsEnabledForPackage(String pkg, int uid)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(uid);
          this.mRemote.transact(175, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void setNotificationAlertsEnabledForPackage(String pkg, int uid, boolean enabled)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(uid);
          _data.writeBoolean(enabled);
          this.mRemote.transact(176, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public boolean setWearableAppList(int userId, List<String> packages) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeStringList(packages);
          this.mRemote.transact(177, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public boolean addWearableAppToList(int userId, String PackageName) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeString(PackageName);
          this.mRemote.transact(178, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public boolean removeWearableAppFromList(int userId, String PackageName)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeString(PackageName);
          this.mRemote.transact(179, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public List<String> getWearableAppList(int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userId);
          this.mRemote.transact(180, _data, _reply, 0);
          _reply.readException();
          List<String> _result = _reply.createStringArrayList();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public boolean requestListenerHintsForWearable(int state) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(state);
          this.mRemote.transact(181, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public int getLockScreenNotificationVisibilityForPackage(String pkg, int uid)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(uid);
          this.mRemote.transact(182, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void setLockScreenNotificationVisibilityForPackage(
          String pkg, int uid, int lockscreenVisibility) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(uid);
          _data.writeInt(lockscreenVisibility);
          this.mRemote.transact(183, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public boolean isAllowNotificationPopUpForPackage(String pkg, int uid)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(uid);
          this.mRemote.transact(184, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void setAllowNotificationPopUpForPackage(String pkg, int uid, boolean allow)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(uid);
          _data.writeBoolean(allow);
          this.mRemote.transact(185, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public boolean isAlertsAllowed(String pkg, int uid, String tags, int flags)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(uid);
          _data.writeString(tags);
          _data.writeInt(flags);
          this.mRemote.transact(186, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public boolean isReminderEnabled(String pkg, int uid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(uid);
          this.mRemote.transact(187, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void setReminderEnabledForPackage(String pkg, int uid, boolean enabled)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(uid);
          _data.writeBoolean(enabled);
          this.mRemote.transact(188, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void setReminderEnabled(int userId, boolean enabled, List<String> packages)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeBoolean(enabled);
          _data.writeStringList(packages);
          this.mRemote.transact(189, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public int getBlockedAppCount(int userId) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userId);
          this.mRemote.transact(190, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public boolean canAppBypassDnd(String pkg, int uid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(uid);
          this.mRemote.transact(191, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void setAppBypassDnd(String pkg, int uid, boolean allow) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(uid);
          _data.writeBoolean(allow);
          this.mRemote.transact(192, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public int getAppsBypassingDndCount(int uid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(uid);
          this.mRemote.transact(193, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void enqueueTextToastForDex(
          String pkg,
          IBinder token,
          CharSequence text,
          int duration,
          boolean isUiContext,
          int displayId,
          ITransientNotificationCallback callback,
          String message,
          int uid)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeStrongBinder(token);
          if (text != null) {
            _data.writeInt(1);
            TextUtils.writeToParcel(text, _data, 0);
          } else {
            _data.writeInt(0);
          }
          _data.writeInt(duration);
          _data.writeBoolean(isUiContext);
          _data.writeInt(displayId);
          _data.writeStrongInterface(callback);
          _data.writeString(message);
          _data.writeInt(uid);
          this.mRemote.transact(194, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void enqueueToastForDex(
          String pkg,
          IBinder token,
          ITransientNotification callback,
          int duration,
          boolean isUiContext,
          int displayId,
          String message,
          int uid)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeStrongBinder(token);
          _data.writeStrongInterface(callback);
          _data.writeInt(duration);
          _data.writeBoolean(isUiContext);
          _data.writeInt(displayId);
          _data.writeString(message);
          _data.writeInt(uid);
          this.mRemote.transact(195, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void addReplyHistory(
          int type, String key, String pkg, int userId, String title, String text)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(type);
          _data.writeString(key);
          _data.writeString(pkg);
          _data.writeInt(userId);
          _data.writeString(title);
          _data.writeString(text);
          this.mRemote.transact(196, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public List<Bundle> getNotificationHistoryDataForPackage(
          String callingPkg,
          String callingAttributionTag,
          int userId,
          String pkg,
          String key,
          int maxNotifications)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(callingPkg);
          _data.writeString(callingAttributionTag);
          _data.writeInt(userId);
          _data.writeString(pkg);
          _data.writeString(key);
          _data.writeInt(maxNotifications);
          this.mRemote.transact(197, _data, _reply, 0);
          _reply.readException();
          List<Bundle> _result = _reply.createTypedArrayList(Bundle.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public NotificationHistory getNotificationHistoryForPackage(
          String callingPkg,
          String callingAttributionTag,
          int userId,
          String pkg,
          String key,
          int maxNotifications)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(callingPkg);
          _data.writeString(callingAttributionTag);
          _data.writeInt(userId);
          _data.writeString(pkg);
          _data.writeString(key);
          _data.writeInt(maxNotifications);
          this.mRemote.transact(198, _data, _reply, 0);
          _reply.readException();
          NotificationHistory _result =
              (NotificationHistory) _reply.readTypedObject(NotificationHistory.CREATOR);
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void updateCancelEvent(int userId, String key, boolean isPackage)
          throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeInt(userId);
          _data.writeString(key);
          _data.writeBoolean(isPackage);
          this.mRemote.transact(199, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public void setRestoreBlockListForSS(List<String> list) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeStringList(list);
          this.mRemote.transact(200, _data, _reply, 0);
          _reply.readException();
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public int getAllNotificationListenersCount() throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          this.mRemote.transact(201, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public int isNotificationTurnedOff(String pkg, int uid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(uid);
          this.mRemote.transact(202, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public int getNotificationSoundStatus(String pkg) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          this.mRemote.transact(203, _data, _reply, 0);
          _reply.readException();
          int _result = _reply.readInt();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }

      @Override // android.app.INotificationManager
      public boolean setNotificationTurnOff(String pkg, int uid) throws RemoteException {
        Parcel _data = Parcel.obtain(asBinder());
        Parcel _reply = Parcel.obtain();
        try {
          _data.writeInterfaceToken(Stub.DESCRIPTOR);
          _data.writeString(pkg);
          _data.writeInt(uid);
          this.mRemote.transact(204, _data, _reply, 0);
          _reply.readException();
          boolean _result = _reply.readBoolean();
          return _result;
        } finally {
          _reply.recycle();
          _data.recycle();
        }
      }
    }

    protected void setToastRateLimitingEnabled_enforcePermission() throws SecurityException {
      this.mEnforcer.enforcePermission(
          Manifest.permission.MANAGE_TOAST_RATE_LIMITING, getCallingPid(), getCallingUid());
    }

    @Override // android.p009os.Binder
    public int getMaxTransactionId() {
      return 203;
    }
  }
}
