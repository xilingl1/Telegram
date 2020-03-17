import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.telegram.messenger.ChatObject;
import org.telegram.messenger.MessagesController;
import org.telegram.tgnet.TLRPC;

import java.util.Arrays;
import java.util.Collection;

import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class ChatObjectTest {

    private ChatObject chatObject;
    private static TLRPC.TL_chat chat;

    @BeforeEach
    public void setup(){
        chatObject = new ChatObject();
        chat = new TLRPC.TL_chat();
        chat.admin_rights = new TLRPC.TL_chatAdminRights();
        chat.banned_rights = new TLRPC.TL_chatBannedRights();
    }

    @Test
    public void canUserDoAdminActionWhenChatIsNull(){
        assertFalse(chatObject.canUserDoAdminAction(null, 0));
    }

    /**
     * Creators should have all the permissions
     */
    @Test
    public void canUserDoAdminActionWhenIsChatCreator(){
        chat.creator = true;
        for(int i = 0; i <= 14; i++){
            assertTrue(chatObject.canUserDoAdminAction(chat, i));
        }
    }

    @Test
    public void canUserDoAdminActionForCheckedActions(){
        chat.admin_rights.ban_users = true;
        chat.admin_rights.add_admins = true;
        chat.admin_rights.invite_users = true;

        assertTrue(chatObject.canUserDoAdminAction(chat, ChatObject.ACTION_BLOCK_USERS));
        assertTrue(chatObject.canUserDoAdminAction(chat, ChatObject.ACTION_ADD_ADMINS));
        assertTrue(chatObject.canUserDoAdminAction(chat, ChatObject.ACTION_INVITE));

        assertFalse(chatObject.canUserDoAdminAction(chat, ChatObject.ACTION_POST));
        assertFalse(chatObject.canUserDoAdminAction(chat, ChatObject.ACTION_DELETE_MESSAGES));
    }

    @Test
    public void canUserDoAdminActionForUnCheckedActions(){
        assertFalse(chatObject.canUserDoAdminAction(chat, ChatObject.ACTION_VIEW));
        assertFalse(chatObject.canUserDoAdminAction(chat, ChatObject.ACTION_EMBED_LINKS));
    }

    /**
     * Creators should have all the permissions
     */
    @Test
    public void canUserDoActionWhenIsChatCreator(){
        chat.creator = true;
        for(int i = 0; i <= 14; i++){
            assertTrue(chatObject.canUserDoAction(chat, i));
        }
    }


    @Test
    public void canUserDoActionForCheckedActions(){
        chat.admin_rights.ban_users = true;
        chat.admin_rights.add_admins = true;
        chat.admin_rights.invite_users = true;

        assertTrue(chatObject.canUserDoAction(chat, ChatObject.ACTION_BLOCK_USERS));
        assertTrue(chatObject.canUserDoAction(chat, ChatObject.ACTION_ADD_ADMINS));
        assertTrue(chatObject.canUserDoAction(chat, ChatObject.ACTION_INVITE));

        assertFalse(chatObject.canUserDoAction(chat, ChatObject.ACTION_POST));
        assertFalse(chatObject.canUserDoAction(chat, ChatObject.ACTION_DELETE_MESSAGES));
    }

    @Test
    public void canUserDoBannedActionIfHasAdminRight(){
        chat.admin_rights.change_info = true;
        chat.admin_rights.pin_messages = true;

        chat.banned_rights.change_info = true;
        chat.banned_rights.pin_messages = true;

        assertTrue(chatObject.canUserDoAction(chat, ChatObject.ACTION_PIN));
        assertTrue(chatObject.canUserDoAction(chat, ChatObject.ACTION_CHANGE_INFO));
    }

    @Test
    public void canUserDoBannedAction(){
        chat.banned_rights.change_info = true;
        chat.banned_rights.pin_messages = true;

        assertFalse(chatObject.canUserDoAction(chat, ChatObject.ACTION_PIN));
        assertFalse(chatObject.canUserDoAction(chat, ChatObject.ACTION_ADD_ADMINS));
    }

    @Test
    public void canUserDoBannableNonAdminAction(){
        assertTrue(chatObject.canUserDoAction(chat, ChatObject.ACTION_SEND));
        assertTrue(chatObject.canUserDoAction(chat, ChatObject.ACTION_VIEW));
        assertTrue(chatObject.canUserDoAction(chat, ChatObject.ACTION_SEND_POLLS));
    }

    @Test
    public void canUserDoBannableActionIntypesOfChats(){
        TLRPC.TL_chat_layer92 chat_layer92 = new TLRPC.TL_chat_layer92();
        assertTrue(chatObject.canUserDoAction(chat_layer92, ChatObject.ACTION_SEND));   //non-admin action, bannable
        assertTrue(chatObject.canUserDoAction(chat_layer92, ChatObject.ACTION_CHANGE_INFO)); //admin action, bannable

        TLRPC.TL_chat_old tl_chat_old = new TLRPC.TL_chat_old();
        assertTrue(chatObject.canUserDoAction(tl_chat_old, ChatObject.ACTION_SEND));
        assertTrue(chatObject.canUserDoAction(chat_layer92, ChatObject.ACTION_CHANGE_INFO));

    }

}
