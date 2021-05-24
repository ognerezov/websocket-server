package net.okhotnikov.websocket.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public enum MessageType {
    @JsonProperty("ParticipantEnter")ParticipantEnter,
    @JsonProperty("ParticipantExit")ParticipantExit,
    @JsonProperty("RolesApplied")RolesApplied,
    @JsonProperty("ServerResponse")ServerResponse,
    @JsonProperty("Broadcast")Broadcast,
    @JsonProperty("Direct")Direct,
    @JsonProperty("Control")Control
}
