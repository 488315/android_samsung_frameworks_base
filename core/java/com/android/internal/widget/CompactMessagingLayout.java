package com.android.internal.widget;

import android.app.Notification;
import android.app.Person;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.RemotableViewMethod;
import android.view.View;
import android.view.ViewStub;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RemoteViews;
import com.android.internal.R;
import com.android.internal.widget.PeopleHelper;
import java.util.ArrayList;
import java.util.List;

@RemoteViews.RemoteView
/* loaded from: classes5.dex */
public class CompactMessagingLayout extends FrameLayout {
    private ViewStub mConversationFacePileViewStub;
    private int mFacePileAvatarSize;
    private int mFacePileProtectionWidth;
    private int mFacePileSize;
    private int mLayoutColor;
    private int mNotificationBackgroundColor;
    private final PeopleHelper mPeopleHelper;

    public CompactMessagingLayout(Context context) {
        super(context);
        this.mPeopleHelper = new PeopleHelper();
    }

    public CompactMessagingLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        this.mPeopleHelper = new PeopleHelper();
    }

    public CompactMessagingLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        this.mPeopleHelper = new PeopleHelper();
    }

    public CompactMessagingLayout(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        this.mPeopleHelper = new PeopleHelper();
    }

    @Override // android.view.View
    protected void onFinishInflate() {
        super.onFinishInflate();
        this.mPeopleHelper.init(getContext());
        this.mConversationFacePileViewStub = (ViewStub) requireViewById(R.id.conversation_face_pile);
        this.mFacePileSize = getResources().getDimensionPixelSize(R.dimen.conversation_compact_face_pile_size);
        this.mFacePileAvatarSize = getResources().getDimensionPixelSize(R.dimen.conversation_compact_face_pile_avatar_size);
        this.mFacePileProtectionWidth = getResources().getDimensionPixelSize(R.dimen.conversation_compact_face_pile_protection_width);
    }

    @RemotableViewMethod(asyncImpl = "setGroupFacePileAsync")
    public void setGroupFacePile(Bundle extras) {
    }

    @RemotableViewMethod
    public Runnable setLayoutColorAsync(int color) {
        this.mLayoutColor = color;
        return NotificationRunnables.NOOP;
    }

    @RemotableViewMethod(asyncImpl = "setLayoutColorAsync")
    public void setLayoutColor(int color) {
        this.mLayoutColor = color;
    }

    @RemotableViewMethod
    public void setNotificationBackgroundColor(int color) {
        this.mNotificationBackgroundColor = color;
    }

    public Runnable setGroupFacePileAsync(Bundle extras) {
        Parcelable[] messages;
        Parcelable[] messages2 = extras.getParcelableArray(Notification.EXTRA_MESSAGES);
        List<Notification.MessagingStyle.Message> newMessages = Notification.MessagingStyle.Message.getMessagesFromBundleArray(messages2);
        Parcelable[] histMessages = extras.getParcelableArray(Notification.EXTRA_HISTORIC_MESSAGES);
        List<Notification.MessagingStyle.Message> newHistoricMessages = Notification.MessagingStyle.Message.getMessagesFromBundleArray(histMessages);
        Person user = (Person) extras.getParcelable(Notification.EXTRA_MESSAGING_PERSON, Person.class);
        List<List<Notification.MessagingStyle.Message>> groups = groupMessages(newMessages, newHistoricMessages);
        PeopleHelper.NameToPrefixMap nameToPrefixMap = this.mPeopleHelper.mapUniqueNamesToPrefixWithGroupList(groups);
        int layoutColor = this.mLayoutColor;
        Icon secondLastIcon = null;
        Icon lastIcon = null;
        CharSequence lastKey = null;
        CharSequence userKey = getPersonKey(user);
        int i = groups.size() - 1;
        while (true) {
            if (i < 0) {
                break;
            }
            Notification.MessagingStyle.Message message = groups.get(i).get(0);
            Person sender = message.getSenderPerson() != null ? message.getSenderPerson() : user;
            CharSequence senderKey = getPersonKey(sender);
            boolean notUser = senderKey != userKey;
            boolean notIncluded = senderKey != lastKey;
            if ((!notUser || !notIncluded) && (i != 0 || lastKey != null)) {
                messages = messages2;
            } else {
                messages = messages2;
                Icon icon = getSenderIcon(sender, nameToPrefixMap, layoutColor);
                if (lastIcon == null) {
                    lastIcon = icon;
                    lastKey = senderKey;
                } else {
                    secondLastIcon = icon;
                    break;
                }
            }
            i--;
            messages2 = messages;
        }
        if (lastIcon == null) {
            lastIcon = getSenderIcon(null, null, layoutColor);
        }
        if (secondLastIcon == null) {
            secondLastIcon = getSenderIcon(null, null, layoutColor);
        }
        final Drawable secondLastIconDrawable = secondLastIcon.loadDrawable(getContext());
        final Drawable lastIconDrawable = lastIcon.loadDrawable(getContext());
        return new Runnable() { // from class: com.android.internal.widget.CompactMessagingLayout$$ExternalSyntheticLambda0
            @Override // java.lang.Runnable
            public final void run() {
                CompactMessagingLayout.this.lambda$setGroupFacePileAsync$0(secondLastIconDrawable, lastIconDrawable);
            }
        };
    }

    /* JADX INFO: Access modifiers changed from: private */
    public /* synthetic */ void lambda$setGroupFacePileAsync$0(Drawable secondLastIconDrawable, Drawable lastIconDrawable) {
        View conversationFacePile = this.mConversationFacePileViewStub.inflate();
        conversationFacePile.setVisibility(0);
        ImageView facePileBottomBg = (ImageView) conversationFacePile.requireViewById(R.id.conversation_face_pile_bottom_background);
        ImageView facePileTop = (ImageView) conversationFacePile.requireViewById(R.id.conversation_face_pile_top);
        ImageView facePileBottom = (ImageView) conversationFacePile.requireViewById(R.id.conversation_face_pile_bottom);
        facePileTop.lambda$setImageURIAsync$0(secondLastIconDrawable);
        facePileBottom.lambda$setImageURIAsync$0(lastIconDrawable);
        facePileBottomBg.setImageTintList(ColorStateList.valueOf(this.mNotificationBackgroundColor));
        setSize(conversationFacePile, this.mFacePileSize);
        setSize(facePileBottom, this.mFacePileAvatarSize);
        setSize(facePileTop, this.mFacePileAvatarSize);
        setSize(facePileBottomBg, this.mFacePileAvatarSize + (this.mFacePileProtectionWidth * 2));
    }

    private Icon getSenderIcon(Person sender, PeopleHelper.NameToPrefixMap uniqueNames, int layoutColor) {
        if (sender == null) {
            return this.mPeopleHelper.createAvatarSymbol("", "", layoutColor);
        }
        if (sender.getIcon() != null) {
            return sender.getIcon();
        }
        CharSequence senderName = sender.getName();
        if (!TextUtils.isEmpty(senderName)) {
            String symbol = uniqueNames != null ? uniqueNames.getPrefix(senderName) : "";
            return this.mPeopleHelper.createAvatarSymbol(senderName, symbol, layoutColor);
        }
        return this.mPeopleHelper.createAvatarSymbol("", "", layoutColor);
    }

    private static List<List<Notification.MessagingStyle.Message>> groupMessages(List<Notification.MessagingStyle.Message> messages, List<Notification.MessagingStyle.Message> historicMessages) {
        if (messages.isEmpty() && historicMessages.isEmpty()) {
            return List.of();
        }
        ArrayList<Notification.MessagingStyle.Message> currentGroup = null;
        CharSequence currentSenderKey = null;
        ArrayList<List<Notification.MessagingStyle.Message>> groups = new ArrayList<>();
        int histSize = historicMessages.size();
        int i = 0;
        while (i < messages.size() + histSize) {
            Notification.MessagingStyle.Message message = i < histSize ? historicMessages.get(i) : messages.get(i - histSize);
            if (message != null) {
                CharSequence senderKey = getPersonKey(message.getSenderPerson());
                boolean isNewGroup = currentGroup == null || senderKey != currentSenderKey;
                if (isNewGroup) {
                    currentGroup = new ArrayList<>();
                    groups.add(currentGroup);
                    currentSenderKey = senderKey;
                }
                currentGroup.add(message);
            }
            i++;
        }
        return groups;
    }

    private static CharSequence getPersonKey(Person person) {
        if (person == null) {
            return null;
        }
        return person.getKey() == null ? person.getName() : person.getKey();
    }

    private static void setSize(View view, int size) {
        FrameLayout.LayoutParams lp = (FrameLayout.LayoutParams) view.getLayoutParams();
        lp.width = size;
        lp.height = size;
        view.setLayoutParams(lp);
    }
}
