package com.android.systemui.statusbar.notification;

import android.app.KeyguardManager;
import android.app.Notification;
import android.app.RemoteInput;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.graphics.drawable.LayerDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.service.notification.StatusBarNotification;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import com.android.internal.util.ContrastColorUtil;
import com.android.systemui.Dependency;
import com.android.systemui.NotiRune;
import com.android.systemui.R;
import com.android.systemui.keyguard.DisplayLifecycle;
import com.android.systemui.plugins.ActivityStarter;
import com.android.systemui.statusbar.LockscreenNotificationInfo;
import com.android.systemui.statusbar.notification.SubscreenNotificationInfo;
import com.android.systemui.statusbar.notification.collection.NotifCollection;
import com.android.systemui.statusbar.notification.collection.NotificationEntry;
import com.android.systemui.statusbar.notification.row.ExpandableNotificationRow;
import com.android.systemui.statusbar.notification.row.NotificationContentView;
import com.android.systemui.statusbar.notification.stack.NotificationChildrenContainer;
import com.android.systemui.statusbar.phone.ConfigurationControllerImpl;
import com.android.systemui.statusbar.policy.ConfigurationController;
import com.android.systemui.util.SettingsHelper;
import com.samsung.android.knox.accounts.Account;
import com.samsung.android.knox.ucm.core.UniversalCredentialUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;

/* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
/* loaded from: classes2.dex */
public final class SubscreenNotificationInfoManager implements ConfigurationController.ConfigurationListener {
    public static final ArrayList mLockscreenNotificationInfoArray = new ArrayList();
    public Context mContext;
    public boolean mIsShownGroup;
    public final NotifCollection mNotifCollection;
    public final SubscreenNotificationDetailAdapter mNotificationDetailAdapter;
    public final SubscreenNotificationGroupAdapter mNotificationGroupAdapter;
    public final SubscreenNotificationListAdapter mNotificationListAdapter;
    public final SettingsHelper mSettingsHelper;
    public final SubscreenNotificationController mSubscreenNotificationController;
    public final ArrayList mRecyclerViewItemHolderArray = new ArrayList();
    public final ArrayList mGroupDataArray = new ArrayList();
    public final Handler mUiHandler = new Handler(Looper.getMainLooper());
    public final ArrayList mReplyWordList = new ArrayList();

    /* compiled from: qb/89794335 06599c810852d30e4467fa5f916efb8291776d5f5b22da1b00b853844284f76c */
    public final class DisplayLifecycleObserver implements DisplayLifecycle.Observer {
        public /* synthetic */ DisplayLifecycleObserver(SubscreenNotificationInfoManager subscreenNotificationInfoManager, int i) {
            this();
        }

        @Override // com.android.systemui.keyguard.DisplayLifecycle.Observer
        public final void onFolderStateChanged(boolean z) {
            SubscreenNotificationInfoManager subscreenNotificationInfoManager = SubscreenNotificationInfoManager.this;
            SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter = subscreenNotificationInfoManager.mNotificationDetailAdapter;
            int i = 1;
            if (subscreenNotificationDetailAdapter.mNeedToUnlock) {
                Log.e("SubscreenNotificationDetailAdapter", "needToUnlock");
                KeyguardManager keyguardManager = (KeyguardManager) subscreenNotificationDetailAdapter.mContext.getSystemService("keyguard");
                Intent intent = new Intent();
                intent.putExtra("ignoreKeyguardState", true);
                keyguardManager.semSetPendingIntentAfterUnlock(null, intent);
                subscreenNotificationDetailAdapter.cleanAdapter();
            } else if (subscreenNotificationDetailAdapter.mReplyclicked && subscreenNotificationDetailAdapter.mSelectHolder != null) {
                Log.e("SubscreenNotificationDetailAdapter", "showRemoteInput");
                ((ActivityStarter) Dependency.get(ActivityStarter.class)).executeRunnableDismissingKeyguard(new SubscreenNotificationDetailAdapter$$ExternalSyntheticLambda0(subscreenNotificationDetailAdapter.mSelectHolder.mInfo.mKey, i), null, false, true, false);
                subscreenNotificationDetailAdapter.cleanAdapter();
            }
            if (z) {
                return;
            }
            subscreenNotificationInfoManager.setReplyWordList();
        }

        private DisplayLifecycleObserver() {
        }
    }

    public SubscreenNotificationInfoManager(Context context, SubscreenNotificationListAdapter subscreenNotificationListAdapter, SubscreenNotificationDetailAdapter subscreenNotificationDetailAdapter, SubscreenNotificationGroupAdapter subscreenNotificationGroupAdapter) {
        this.mContext = context;
        this.mNotificationListAdapter = subscreenNotificationListAdapter;
        this.mNotificationDetailAdapter = subscreenNotificationDetailAdapter;
        this.mNotificationGroupAdapter = subscreenNotificationGroupAdapter;
        ((DisplayLifecycle) Dependency.get(DisplayLifecycle.class)).addObserver(new DisplayLifecycleObserver(this, 0));
        this.mNotifCollection = (NotifCollection) Dependency.get(NotifCollection.class);
        this.mSubscreenNotificationController = (SubscreenNotificationController) Dependency.get(SubscreenNotificationController.class);
        this.mSettingsHelper = (SettingsHelper) Dependency.get(SettingsHelper.class);
        ((ConfigurationControllerImpl) ((ConfigurationController) Dependency.get(ConfigurationController.class))).addCallback(this);
        setReplyWordList();
    }

    public static boolean canViewBeCleared(ExpandableNotificationRow expandableNotificationRow) {
        if (expandableNotificationRow == null) {
            return true;
        }
        return expandableNotificationRow.mEntry.isClearable() && !(expandableNotificationRow.shouldShowPublic() && expandableNotificationRow.mSensitiveHiddenInGeneral);
    }

    public static boolean checkRemoveNotification() {
        int notificationInfoArraySize = getNotificationInfoArraySize();
        for (int i = 0; i < notificationInfoArraySize; i++) {
            if (canViewBeCleared(((LockscreenNotificationInfo) mLockscreenNotificationInfoArray.get(i)).mRow)) {
                return true;
            }
        }
        return false;
    }

    public static int getNotificationInfoArraySize() {
        ArrayList arrayList = mLockscreenNotificationInfoArray;
        if (arrayList != null) {
            return arrayList.size();
        }
        return 0;
    }

    public static int removeLockscreenNotificationInfoItem(NotificationEntry notificationEntry) {
        Log.d("SubscreenNotificationInfoManager", "removeLockscreenNotificationInfoItem entry : " + notificationEntry + " >>>>> currentThread : " + Thread.currentThread());
        ArrayList arrayList = mLockscreenNotificationInfoArray;
        if (arrayList == null) {
            return -1;
        }
        for (int i = 0; i < getNotificationInfoArraySize(); i++) {
            if (notificationEntry.mKey.equals(((LockscreenNotificationInfo) arrayList.get(i)).mRow.mEntry.mKey)) {
                arrayList.remove(i);
                return i;
            }
        }
        return -1;
    }

    public static void setEntryDismissState(NotificationEntry notificationEntry) {
        NotificationChildrenContainer notificationChildrenContainer;
        int notificationChildCount;
        notificationEntry.setDismissState(NotificationEntry.DismissState.DISMISSED);
        if (!notificationEntry.mSbn.getNotification().isGroupSummary() || (notificationChildrenContainer = notificationEntry.row.mChildrenContainer) == null || (notificationChildCount = notificationChildrenContainer.getNotificationChildCount()) <= 0) {
            return;
        }
        for (int i = 0; i < notificationChildCount; i++) {
            ((ExpandableNotificationRow) ((ArrayList) notificationChildrenContainer.mAttachedChildren).get(i)).mEntry.setDismissState(NotificationEntry.DismissState.DISMISSED);
        }
    }

    public final void addRecyclerViewItemView(SubscreenParentItemViewHolder subscreenParentItemViewHolder) {
        ArrayList arrayList = this.mRecyclerViewItemHolderArray;
        int size = arrayList.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                break;
            }
            if (((SubscreenParentItemViewHolder) arrayList.get(i)).mInfo.mKey.equals(subscreenParentItemViewHolder.mInfo.mKey)) {
                arrayList.remove(i);
                break;
            }
            i++;
        }
        arrayList.add(subscreenParentItemViewHolder);
    }

    public final void clearAllRecyclerViewItem() {
        this.mRecyclerViewItemHolderArray.clear();
    }

    /* JADX WARN: Can't wrap try/catch for region: R(39:0|1|(5:200|(1:202)(1:210)|203|(1:205)(1:209)|(1:207)(1:208))|5|(1:9)|10|(3:12|(1:18)(1:16)|17)|19|(3:21|(3:23|(4:25|(3:27|(3:29|(2:31|32)(1:34)|33)|35)|36|(2:38|39)(1:41))(1:42)|40)|43)|44|(1:46)(4:177|(4:179|(1:181)|182|183)|184|(4:186|(1:199)(1:192)|(1:194)(2:196|(1:198))|195))|47|(1:49)(1:176)|50|(3:52|(1:56)(1:172)|(24:58|(5:130|(1:171)(1:134)|135|(1:137)|(4:139|(6:142|(2:144|(1:166)(9:146|(1:148)|149|(1:151)|152|(3:157|158|(1:160))|154|155|156))(1:168)|161|162|156|140)|169|167)(1:170))(1:61)|62|(1:(1:65)(1:66))|67|(1:(1:70)(1:71))|72|(1:74)(1:129)|75|(1:77)(1:128)|78|(1:80)|81|82|83|(1:85)(1:125)|86|(1:88)(1:124)|89|90|(3:92|(4:95|(2:97|98)(2:100|101)|99|93)|102)|103|(1:(3:105|(1:121)(3:107|(1:109)|(3:111|112|(1:116)(0))(1:119))|120)(1:122))|117))|173|(1:175)|62|(0)|67|(0)|72|(0)(0)|75|(0)(0)|78|(0)|81|82|83|(0)(0)|86|(0)(0)|89|90|(0)|103|(2:(0)(0)|120)|117) */
    /* JADX WARN: Code restructure failed: missing block: B:126:0x0426, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:127:0x0427, code lost:
    
        r0.printStackTrace();
     */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0476  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x04a9 A[EDGE_INSN: B:122:0x04a9->B:117:0x04a9 BREAK  A[LOOP:3: B:104:0x0474->B:120:0x04a6], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:124:0x0415 A[Catch: NameNotFoundException -> 0x0426, TryCatch #0 {NameNotFoundException -> 0x0426, blocks: (B:83:0x03db, B:86:0x0408, B:88:0x040c, B:89:0x0419, B:124:0x0415), top: B:82:0x03db }] */
    /* JADX WARN: Removed duplicated region for block: B:125:0x0407  */
    /* JADX WARN: Removed duplicated region for block: B:128:0x03b9  */
    /* JADX WARN: Removed duplicated region for block: B:129:0x03a9  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0381  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0395  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x03a7  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x03b7  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x03c8  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0405  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x040c A[Catch: NameNotFoundException -> 0x0426, TryCatch #0 {NameNotFoundException -> 0x0426, blocks: (B:83:0x03db, B:86:0x0408, B:88:0x040c, B:89:0x0419, B:124:0x0415), top: B:82:0x03db }] */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0432  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final SubscreenNotificationInfo createItemsData(ExpandableNotificationRow expandableNotificationRow) {
        int HSVToColor;
        CharSequence charSequence;
        Notification notification2;
        String str;
        String str2;
        CharSequence charSequence2;
        CharSequence[] charSequenceArray;
        int childCount;
        int i;
        int i2;
        Notification notification3;
        char c;
        SubscreenNotificationInfo subscreenNotificationInfo = new SubscreenNotificationInfo(this.mContext);
        subscreenNotificationInfo.mRow = expandableNotificationRow;
        StatusBarNotification statusBarNotification = expandableNotificationRow.mEntry.mSbn;
        subscreenNotificationInfo.mSbn = statusBarNotification;
        subscreenNotificationInfo.mKey = statusBarNotification.getKey();
        subscreenNotificationInfo.mPkg = subscreenNotificationInfo.mSbn.getPackageName();
        subscreenNotificationInfo.mSbn.isOngoing();
        Notification notification4 = subscreenNotificationInfo.mSbn.getNotification();
        Bundle bundle = notification4.extras;
        SubscreenNotificationController subscreenNotificationController = (SubscreenNotificationController) Dependency.get(SubscreenNotificationController.class);
        int i3 = 0;
        subscreenNotificationInfo.mNeedsOnePhoneIcon = false;
        subscreenNotificationInfo.mNeedsTwoPhoneIcon = false;
        boolean z = true;
        if ("MESSAGE_KT_TWO_PHONE_OPPOSITE_RECEIVED".equals(notification4.getGroup()) || "two_phone_missed_call_group".equals(notification4.getGroup())) {
            SubscreenDeviceModelParent subscreenDeviceModelParent = subscreenNotificationController.mDeviceModel;
            Integer valueOf = subscreenDeviceModelParent != null ? Integer.valueOf(subscreenDeviceModelParent.currentUserId) : null;
            SubscreenDeviceModelParent subscreenDeviceModelParent2 = subscreenNotificationController.mDeviceModel;
            if (valueOf == (subscreenDeviceModelParent2 != null ? Integer.valueOf(subscreenDeviceModelParent2.bModeUserId) : null)) {
                subscreenNotificationInfo.mNeedsOnePhoneIcon = true;
            } else {
                subscreenNotificationInfo.mNeedsTwoPhoneIcon = true;
            }
        }
        boolean isGroupSummary = notification4.isGroupSummary();
        subscreenNotificationInfo.mGroupSummary = isGroupSummary;
        if (isGroupSummary && expandableNotificationRow.mIsSummaryWithChildren) {
            subscreenNotificationInfo.mChildCount = expandableNotificationRow.mChildrenContainer.mUntruncatedChildCount;
        }
        String str3 = notification4.category;
        if (str3 != null) {
            subscreenNotificationInfo.mIsMissedCall = "missed_call".equals(str3);
            subscreenNotificationInfo.mIsCall = "call".equals(notification4.category) && notification4.isStyle(Notification.CallStyle.class);
        }
        subscreenNotificationInfo.mRemoteinput = false;
        subscreenNotificationInfo.mHasSemanticCall = false;
        Notification.Action[] actionArr = notification4.actions;
        String str4 = "";
        if (actionArr != null) {
            int length = actionArr.length;
            int i4 = 0;
            while (i4 < length) {
                Notification.Action action = notification4.actions[i4];
                if (action != null) {
                    RemoteInput[] remoteInputs = action.getRemoteInputs();
                    if (remoteInputs != null) {
                        subscreenNotificationInfo.mRemoteinput = z;
                        int length2 = remoteInputs.length;
                        for (int i5 = i3; i5 < length2; i5++) {
                            RemoteInput remoteInput = remoteInputs[i5];
                            if (remoteInput.getAllowFreeFormInput()) {
                                subscreenNotificationInfo.mRemoteInputMaxLength = remoteInput.getExtras().getInt("maxLength", 200);
                                subscreenNotificationInfo.mRemoteInputIsSms = remoteInput.getExtras().getBoolean("isSms", false);
                                subscreenNotificationInfo.mRemoteInputSignature = remoteInput.getExtras().getString(Account.SIGNATURE, "");
                                subscreenNotificationInfo.mRemoteInputActionIntent = action.actionIntent;
                            }
                        }
                    }
                    if (action.getSemanticAction() == 10) {
                        subscreenNotificationInfo.mHasSemanticCall = true;
                        subscreenNotificationInfo.mSemanticCallPendingIntent = action.actionIntent;
                    }
                }
                i4++;
                i3 = 0;
                z = true;
            }
        }
        Drawable loadDrawable = notification4.getSmallIcon().loadDrawable(subscreenNotificationInfo.mContext);
        subscreenNotificationInfo.mIcon = loadDrawable;
        int i6 = subscreenNotificationInfo.mSbn.getNotification().color;
        if (loadDrawable == null) {
            loadDrawable = null;
        } else {
            loadDrawable.mutate();
            if (loadDrawable instanceof AnimationDrawable) {
                AnimationDrawable animationDrawable = (AnimationDrawable) loadDrawable;
                int numberOfFrames = animationDrawable.getNumberOfFrames();
                Drawable[] drawableArr = new Drawable[numberOfFrames];
                for (int i7 = 0; i7 < numberOfFrames; i7++) {
                    drawableArr[i7] = animationDrawable.getFrame(i7);
                }
                loadDrawable = new LayerDrawable(drawableArr);
            }
            if (ContrastColorUtil.getInstance(subscreenNotificationInfo.mContext).isGrayscaleIcon(loadDrawable)) {
                int red = Color.red(i6);
                int green = Color.green(i6);
                int blue = Color.blue(i6);
                if (Color.red(i6) == 0 && Color.green(i6) == 0 && Color.blue(i6) == 0) {
                    HSVToColor = subscreenNotificationInfo.mAppPrimaryDefaultColor;
                } else {
                    float[] fArr = {0.0f, r10, 0.0f};
                    Color.RGBToHSV(red, green, blue, fArr);
                    float f = fArr[1] - 0.15f;
                    fArr[1] = Math.max(0.0f, Math.min(1.0f, f));
                    float f2 = fArr[2] + 0.2f;
                    fArr[2] = f2;
                    fArr[2] = Math.max(0.0f, Math.min(1.0f, f2));
                    HSVToColor = Color.HSVToColor(255, fArr);
                    if (Math.round((255 / 255.0f) * Math.max(Color.red(HSVToColor), Math.max(Color.green(HSVToColor), Color.blue(HSVToColor)))) > 204) {
                        float round = 204 / Math.round((r8 / 255.0f) * Math.max(r12, Math.max(r13, r6)));
                        HSVToColor = Color.argb(Color.alpha(HSVToColor), Math.round(Color.red(HSVToColor) * round), Math.round(Color.green(HSVToColor) * round), Math.round(round * Color.blue(HSVToColor)));
                    }
                }
                loadDrawable.setColorFilter(HSVToColor, PorterDuff.Mode.SRC_ATOP);
            }
        }
        subscreenNotificationInfo.mIcon = loadDrawable;
        subscreenNotificationInfo.mConversationIcon = (Icon) bundle.getParcelable("android.conversationIcon");
        subscreenNotificationInfo.mLargeIcon = (Icon) bundle.getParcelable("android.largeIcon");
        boolean equals = Notification.MessagingStyle.class.equals(notification4.getNotificationStyle());
        subscreenNotificationInfo.mIsMessagingStyle = equals;
        subscreenNotificationInfo.mUnreadMessageCnt = 0;
        if (equals) {
            subscreenNotificationInfo.mUnreadMessageCnt = subscreenNotificationController.getUnreadCount(expandableNotificationRow.mEntry);
            subscreenNotificationInfo.mIsGroupConversation = bundle.getBoolean("android.isGroupConversation");
            charSequence = subscreenNotificationInfo.findConversationTitle(subscreenNotificationInfo.mSbn);
        } else {
            charSequence = bundle.getCharSequence("android.title");
        }
        if (NotiRune.NOTI_SUBSCREEN_SUPPORT_NOTIFICATION_HISTORY) {
            SettingsHelper settingsHelper = (SettingsHelper) Dependency.get(SettingsHelper.class);
            settingsHelper.getClass();
            if (NotiRune.NOTI_SUBSCREEN_NOTIFICATION_FIFTH && settingsHelper.mItemLists.get("notification_history_enabled").getIntValue() == 1) {
                if (subscreenNotificationInfo.mRemoteinput || equals) {
                    List histories = subscreenNotificationInfo.getHistories(50);
                    SubscreenDeviceModelParent subscreenDeviceModelParent3 = subscreenNotificationController.mDeviceModel;
                    subscreenDeviceModelParent3.getClass();
                    boolean z2 = (subscreenDeviceModelParent3 instanceof SubscreenDeviceModelB5) && ((ArrayList) histories).size() > 0;
                    ArrayList arrayList = (ArrayList) histories;
                    if (arrayList.size() > 0) {
                        subscreenNotificationInfo.mIsMessagingStyle |= subscreenNotificationInfo.mRemoteinput;
                    }
                    if (z2) {
                        Iterator it = arrayList.iterator();
                        int i8 = 0;
                        while (it.hasNext()) {
                            Bundle bundle2 = (Bundle) it.next();
                            int i9 = bundle2.getInt("type", 0);
                            String string = bundle2.getString(UniversalCredentialUtil.AGENT_TITLE, str4);
                            String string2 = bundle2.getString("text", str4);
                            String string3 = bundle2.getString("uri", str4);
                            String string4 = bundle2.getString("sbnKey", str4);
                            int i10 = i8;
                            long j = bundle2.getLong("postedTime", 0L);
                            Iterator it2 = it;
                            long j2 = bundle2.getLong("when", 0L);
                            String str5 = str4;
                            boolean z3 = bundle2.getBoolean("isChecked", false);
                            if (subscreenNotificationInfo.mKey.equals(string4)) {
                                i2 = i10;
                                if (i2 >= 25) {
                                    break;
                                }
                                boolean z4 = i9 == 1;
                                SubscreenNotificationInfo.MessagingStyleInfo messagingStyleInfo = new SubscreenNotificationInfo.MessagingStyleInfo();
                                messagingStyleInfo.mContentText = string2;
                                notification3 = notification4;
                                if (subscreenNotificationInfo.mContent == null) {
                                    subscreenNotificationInfo.mContent = string2;
                                }
                                messagingStyleInfo.mSender = string;
                                messagingStyleInfo.mTimeStamp = j2;
                                messagingStyleInfo.mPostedTime = j;
                                messagingStyleInfo.mIsReply = z4;
                                messagingStyleInfo.mIsChecked = z3;
                                c = 2;
                                if (i9 == 2) {
                                    try {
                                        Drawable queryContentUriInternal = SubscreenNotificationInfo.queryContentUriInternal(subscreenNotificationInfo.mContext, string3);
                                        if (queryContentUriInternal != null) {
                                            messagingStyleInfo.mUriImage = queryContentUriInternal;
                                        }
                                    } catch (SecurityException e) {
                                        e.printStackTrace();
                                    }
                                }
                                subscreenNotificationInfo.mMessageingStyleInfoArray.add(messagingStyleInfo);
                                i8 = i2 + 1;
                                it = it2;
                                str4 = str5;
                                notification4 = notification3;
                            } else {
                                i2 = i10;
                                notification3 = notification4;
                                c = 2;
                            }
                            i8 = i2;
                            it = it2;
                            str4 = str5;
                            notification4 = notification3;
                        }
                        notification2 = notification4;
                        subscreenNotificationInfo.mMessageingStyleInfoArray.sort(new Comparator(subscreenNotificationInfo) { // from class: com.android.systemui.statusbar.notification.SubscreenNotificationInfo.2
                            public C27552(SubscreenNotificationInfo subscreenNotificationInfo2) {
                            }

                            @Override // java.util.Comparator
                            public final int compare(Object obj, Object obj2) {
                                return (int) (((MessagingStyleInfo) obj).mPostedTime - ((MessagingStyleInfo) obj2).mPostedTime);
                            }
                        });
                    } else {
                        notification2 = notification4;
                        subscreenNotificationInfo2.makeConversation(expandableNotificationRow);
                    }
                } else {
                    notification2 = notification4;
                }
                str = subscreenNotificationInfo2.mTitle;
                if (str == null) {
                    str = charSequence == null ? null : charSequence.toString();
                }
                subscreenNotificationInfo2.mTitle = str;
                CharSequence charSequence3 = bundle.getCharSequence("android.text");
                str2 = subscreenNotificationInfo2.mContent;
                if (str2 == null) {
                    str2 = charSequence3 == null ? null : charSequence3.toString();
                }
                subscreenNotificationInfo2.mContent = str2;
                CharSequence charSequence4 = bundle.getCharSequence("android.bigText");
                subscreenNotificationInfo2.mBigText = charSequence4 != null ? null : charSequence4.toString();
                CharSequence charSequence5 = bundle.getCharSequence("android.title.big");
                subscreenNotificationInfo2.mBigTitle = charSequence5 != null ? null : charSequence5.toString();
                charSequence2 = bundle.getCharSequence("android.subText");
                if (charSequence2 != null) {
                    charSequence2.toString();
                }
                subscreenNotificationInfo2.mBitmap = (Bitmap) bundle.getParcelable("android.picture");
                PackageManager packageManager = subscreenNotificationInfo2.mContext.getPackageManager();
                CharSequence charSequence6 = bundle.getCharSequence("android.substName");
                ApplicationInfo applicationInfo = packageManager.getApplicationInfo(subscreenNotificationInfo2.mSbn.getPackageName(), 4202624);
                subscreenNotificationInfo2.mAppPrimaryDefaultColor = subscreenNotificationInfo2.mContext.getColor(R.color.subscreen_notification_primary_default_for_common);
                subscreenNotificationInfo2.mAppIcon = packageManager.semGetApplicationIconForIconTray(applicationInfo, 33);
                subscreenNotificationInfo2.mUseSmallIcon = applicationInfo.icon != 0;
                String charSequence7 = charSequence6 != null ? packageManager.getApplicationLabel(applicationInfo).toString() : charSequence6.toString();
                subscreenNotificationInfo2.mAppName = charSequence7;
                subscreenNotificationInfo2.mAppName = charSequence7.replace("\n", " ");
                charSequenceArray = bundle.getCharSequenceArray("android.textLines");
                if (charSequenceArray != null) {
                    for (int i11 = 0; i11 < Math.min(subscreenNotificationInfo2.mInBox.length, charSequenceArray.length); i11++) {
                        String[] strArr = subscreenNotificationInfo2.mInBox;
                        CharSequence charSequence8 = charSequenceArray[i11];
                        strArr[i11] = charSequence8 == null ? null : charSequence8.toString();
                    }
                }
                Notification notification5 = notification2;
                subscreenNotificationInfo2.mContentIntent = notification5.contentIntent;
                subscreenNotificationInfo2.mContentView = subscreenNotificationInfo2.mSbn.getNotification().contentView;
                subscreenNotificationInfo2.mWhen = notification5.when;
                subscreenNotificationInfo2.mShowWhen = bundle.getBoolean("android.showWhen");
                subscreenNotificationInfo2.mKnoxBadgeDrawable = null;
                childCount = subscreenNotificationInfo2.mRow.getChildCount();
                i = 0;
                while (true) {
                    if (i < childCount) {
                        break;
                    }
                    View childAt = subscreenNotificationInfo2.mRow.getChildAt(i);
                    if (childAt instanceof NotificationContentView) {
                        NotificationContentView notificationContentView = (NotificationContentView) childAt;
                        View view = notificationContentView.mExpandedChild;
                        if (view == null) {
                            view = notificationContentView.mContractedChild;
                        }
                        if (view != null) {
                            ImageView imageView = (ImageView) view.findViewById(android.R.id.search_go_btn);
                            if (imageView != null && imageView.getDrawable() != null) {
                                subscreenNotificationInfo2.mKnoxBadgeDrawable = imageView.getDrawable().mutate();
                            }
                        }
                    }
                    i++;
                }
                return subscreenNotificationInfo2;
            }
        }
        notification2 = notification4;
        if (equals) {
            subscreenNotificationInfo2.makeConversation(expandableNotificationRow);
        }
        str = subscreenNotificationInfo2.mTitle;
        if (str == null) {
        }
        subscreenNotificationInfo2.mTitle = str;
        CharSequence charSequence32 = bundle.getCharSequence("android.text");
        str2 = subscreenNotificationInfo2.mContent;
        if (str2 == null) {
        }
        subscreenNotificationInfo2.mContent = str2;
        CharSequence charSequence42 = bundle.getCharSequence("android.bigText");
        subscreenNotificationInfo2.mBigText = charSequence42 != null ? null : charSequence42.toString();
        CharSequence charSequence52 = bundle.getCharSequence("android.title.big");
        subscreenNotificationInfo2.mBigTitle = charSequence52 != null ? null : charSequence52.toString();
        charSequence2 = bundle.getCharSequence("android.subText");
        if (charSequence2 != null) {
        }
        subscreenNotificationInfo2.mBitmap = (Bitmap) bundle.getParcelable("android.picture");
        PackageManager packageManager2 = subscreenNotificationInfo2.mContext.getPackageManager();
        CharSequence charSequence62 = bundle.getCharSequence("android.substName");
        ApplicationInfo applicationInfo2 = packageManager2.getApplicationInfo(subscreenNotificationInfo2.mSbn.getPackageName(), 4202624);
        subscreenNotificationInfo2.mAppPrimaryDefaultColor = subscreenNotificationInfo2.mContext.getColor(R.color.subscreen_notification_primary_default_for_common);
        subscreenNotificationInfo2.mAppIcon = packageManager2.semGetApplicationIconForIconTray(applicationInfo2, 33);
        subscreenNotificationInfo2.mUseSmallIcon = applicationInfo2.icon != 0;
        if (charSequence62 != null) {
        }
        subscreenNotificationInfo2.mAppName = charSequence7;
        subscreenNotificationInfo2.mAppName = charSequence7.replace("\n", " ");
        charSequenceArray = bundle.getCharSequenceArray("android.textLines");
        if (charSequenceArray != null) {
        }
        Notification notification52 = notification2;
        subscreenNotificationInfo2.mContentIntent = notification52.contentIntent;
        subscreenNotificationInfo2.mContentView = subscreenNotificationInfo2.mSbn.getNotification().contentView;
        subscreenNotificationInfo2.mWhen = notification52.when;
        subscreenNotificationInfo2.mShowWhen = bundle.getBoolean("android.showWhen");
        subscreenNotificationInfo2.mKnoxBadgeDrawable = null;
        childCount = subscreenNotificationInfo2.mRow.getChildCount();
        i = 0;
        while (true) {
            if (i < childCount) {
            }
            i++;
        }
        return subscreenNotificationInfo2;
    }

    public final int getGroupDataArraySize() {
        return this.mGroupDataArray.size();
    }

    @Override // com.android.systemui.statusbar.policy.ConfigurationController.ConfigurationListener
    public final void onConfigChanged(Configuration configuration) {
        setReplyWordList();
    }

    public final int removeGroupDataArrayItem(NotificationEntry notificationEntry) {
        if (this.mIsShownGroup) {
            int i = 0;
            while (true) {
                ArrayList arrayList = this.mGroupDataArray;
                if (i >= arrayList.size()) {
                    break;
                }
                if (((SubscreenNotificationInfo) arrayList.get(i)).mKey.equals(notificationEntry.mKey)) {
                    arrayList.remove(i);
                    SubscreenDeviceModelParent subscreenDeviceModelParent = this.mSubscreenNotificationController.mDeviceModel;
                    subscreenDeviceModelParent.getClass();
                    return !(subscreenDeviceModelParent instanceof SubscreenDeviceModelB5) ? i + 1 : i;
                }
                i++;
            }
        }
        return -1;
    }

    public final void removeNotification(NotificationEntry notificationEntry) {
        NotificationChildrenContainer notificationChildrenContainer;
        int notificationChildCount;
        ExpandableNotificationRow expandableNotificationRow = notificationEntry.row;
        if (expandableNotificationRow != null) {
            expandableNotificationRow.performDismiss(false);
        }
        boolean isGroupSummary = notificationEntry.mSbn.getNotification().isGroupSummary();
        SubscreenNotificationController subscreenNotificationController = this.mSubscreenNotificationController;
        if (isGroupSummary && (notificationChildrenContainer = notificationEntry.row.mChildrenContainer) != null && (notificationChildCount = notificationChildrenContainer.getNotificationChildCount()) > 0) {
            boolean z = false;
            for (int i = 0; i < notificationChildCount; i++) {
                NotificationEntry notificationEntry2 = ((ExpandableNotificationRow) ((ArrayList) notificationChildrenContainer.mAttachedChildren).get(i)).mEntry;
                if (notificationEntry2.canBubble()) {
                    subscreenNotificationController.mDeviceModel.removeMainHashItem(notificationEntry2);
                    z = true;
                }
            }
            if (z) {
                subscreenNotificationController.mDeviceModel.removeMainHashItem(notificationEntry);
            }
        }
        if (notificationEntry.canBubble()) {
            subscreenNotificationController.mDeviceModel.removeMainHashItem(notificationEntry);
        }
        int removeLockscreenNotificationInfoItem = removeLockscreenNotificationInfoItem(notificationEntry);
        if (removeLockscreenNotificationInfoItem >= 0) {
            this.mNotificationListAdapter.notifyItemRemoved(removeLockscreenNotificationInfoItem);
        }
        int removeGroupDataArrayItem = removeGroupDataArrayItem(notificationEntry);
        if (removeGroupDataArrayItem >= 0) {
            this.mNotificationGroupAdapter.notifyItemRemoved(removeGroupDataArrayItem);
        }
        setEntryDismissState(notificationEntry);
    }

    public final void setReplyWordList() {
        ArrayList arrayList = this.mReplyWordList;
        if (arrayList.size() > 0) {
            arrayList.clear();
        }
        String[] stringArray = this.mContext.getResources().getStringArray(R.array.subscreen_quick_reply_list);
        SettingsHelper settingsHelper = this.mSettingsHelper;
        settingsHelper.getClass();
        boolean z = NotiRune.NOTI_SUBSCREEN_NOTIFICATION_COMMON;
        String stringValue = !z ? null : settingsHelper.mItemLists.get("cover_screen_quick_reply_text").getStringValue();
        settingsHelper.getClass();
        String stringValue2 = z ? settingsHelper.mItemLists.get("cover_screen_quick_reply_text_pos_for_translation").getStringValue() : null;
        if (stringValue2 == null) {
            arrayList.addAll(Arrays.asList(stringArray));
            return;
        }
        try {
            JSONArray jSONArray = new JSONArray(stringValue);
            JSONArray jSONArray2 = new JSONArray(stringValue2);
            for (int i = 0; i < jSONArray.length(); i++) {
                String string = jSONArray2.getString(i);
                if (TextUtils.isEmpty(string) || "-1".equals(string)) {
                    arrayList.add(jSONArray.getString(i));
                } else {
                    arrayList.add(stringArray[Integer.parseInt(string)]);
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public final void setShownGroup(boolean z) {
        Log.e("SubscreenNotificationInfoManager", "setShownGroup : " + z);
        this.mIsShownGroup = z;
    }
}
