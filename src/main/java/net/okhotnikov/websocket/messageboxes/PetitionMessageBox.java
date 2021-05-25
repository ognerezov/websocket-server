package net.okhotnikov.websocket.messageboxes;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.okhotnikov.websocket.model.GenericMessage;
import net.okhotnikov.websocket.model.Participant;
import net.okhotnikov.websocket.model.Room;
import net.okhotnikov.websocket.security.PermitAllFilter;
import net.okhotnikov.websocket.service.RoomService;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;

import static net.okhotnikov.websocket.util.CommonMessages.sent;
import static net.okhotnikov.websocket.util.CommonMessages.text;
import static net.okhotnikov.websocket.util.CommonUtils.messageOrError;
import static net.okhotnikov.websocket.util.Literals.ADMINS_NOT_FOUND;

public class PetitionMessageBox extends RoomMessageBox{
    public PetitionMessageBox(RoomService roomService, ObjectMapper mapper) {
        super(roomService, new PermitAllFilter(), mapper);
    }

    @Override
    public <T> void receive(GenericMessage<T> message, WebSocketSession sender) throws IOException {
        Room room = roomService.getRoom(roomService.getParticipant(sender.getId()));

        int count = 0;

        for(Participant participant: room.participants){
            if (!participant.doesAcceptPetitions())
                continue;

            participant.session.sendMessage(new TextMessage(messageOrError(mapper,message)));
            count ++;
        }
        if (count >0)
            sender.sendMessage(new TextMessage(sent(mapper)));
        else
            sender.sendMessage(new TextMessage(text(mapper,ADMINS_NOT_FOUND,false)));
    }
}
