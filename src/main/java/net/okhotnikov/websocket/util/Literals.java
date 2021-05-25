package net.okhotnikov.websocket.util;

/**
 * Created by Sergey Okhotnikov.
 */
public interface Literals {
    String TOKEN = "token";
    String REFRESH_TOKEN = "refreshToken";
    String ROLES = "roles";
    String REGISTERED = "registered";
    String DATE_FORMAT = "yyyy-MM-dd";
    String EMAIL_STATUS_FIELD = "emailStatus";

    String EMAIL_SENT_STATUS = "sent";
    String EMAIL_NOT_SENT_STATUS = "not sent";
    String EMAIL_STATUS_DELIVERED = "delivered";
    String EMAIL_STATUS_PROCESSED = "processed";
    String EMAIL_STATUS_BOUNCE = "bounce";
    String EMAIL_STATUS_DEFERRED = "deferred";

    String SUPPORT_EMAILS_THEME_PREFIX = "Sent from everything ";

    String USERNAME = "username";
    String NAME = "name";
    String ROOM = "room";
    String ADMIN = "admin";
    String TEAM = "team";
    String Authorization = "Authorization";
    String ENTER_MESSAGE =" enters room ";
    String ENTER_MESSAGE_ANONYMOUS ="Anonymous enters room ";
    String NO_PARTICIPANT_ON_DISCONNECT = "Connection was closed but no Participant found";
    String NO_ROOM_PARTICIPANT_ON_DISCONNECT = "Connection was closed participant room not found: ";
    String ROOM_IS_EMPTY = "Room become empty and is been closed";
    String EXIT_MESSAGE =" Exits room ";
    String FAIL = "fail";

    String SENT = "sent";
    String FORBIDDEN = "forbidden";
    String PARTICIPANTS_ARE_MISSING = "participants are missing";
    String PARSING_ERROR = "parsing error";
    String INVALID_CONTROL_MESSAGE ="invalid control message";
    String NOT_SUPPORTED_MESSAGE_TYPE = "message type not supported";
    String ADMINS_NOT_FOUND = "admins not found";
    String ROOM_DISCONNECTED = "disconnected";
}
